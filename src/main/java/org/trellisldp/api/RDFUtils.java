/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.trellisldp.api;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableMap;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;
import static org.trellisldp.vocabulary.RDF.type;
import static org.trellisldp.vocabulary.Trellis.PreferAudit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.Stream;

import org.apache.commons.rdf.api.BlankNode;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Quad;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.RDFTerm;

import org.trellisldp.vocabulary.AS;
import org.trellisldp.vocabulary.LDP;
import org.trellisldp.vocabulary.PROV;
import org.trellisldp.vocabulary.XSD;

/**
 * The RDFUtils class provides a set of convenience methods related to
 * generating and processing RDF objects.
 *
 * @author acoburn
 */
public final class RDFUtils {

    private static RDF rdf = ServiceLoader.load(RDF.class).iterator().next();

    /**
     * The internal trellis prefix
     */
    public static final String TRELLIS_PREFIX = "trellis:";

    /**
     * The internal blank node prefix
     */
    public static final String TRELLIS_BNODE_PREFIX = "trellis:bnode/";

    /**
     * A mapping of LDP types to their supertype
     */
    public static final Map<IRI, IRI> superClassOf;

    static {
        final Map<IRI, IRI> data = new HashMap<>();
        data.put(LDP.NonRDFSource, LDP.Resource);
        data.put(LDP.RDFSource, LDP.Resource);
        data.put(LDP.Container, LDP.RDFSource);
        data.put(LDP.BasicContainer, LDP.Container);
        data.put(LDP.DirectContainer, LDP.Container);
        data.put(LDP.IndirectContainer, LDP.Container);
        superClassOf = unmodifiableMap(data);
    }

    /**
     * Get the Commons RDF instance in use
     * @return the RDF instance
     */
    public static RDF getInstance() {
        return rdf;
    }

    /**
     * Create audit-related creation data
     * @param subject the subject
     * @param session the session
     * @return the quads
     */
    public static List<Quad> auditCreation(final IRI subject, final Session session) {
        return auditData(subject, session, asList(PROV.Activity, AS.Create));
    }

    /**
     * Create audit-related deletion data
     * @param subject the subject
     * @param session the session
     * @return the quads
     */
    public static List<Quad> auditDeletion(final IRI subject, final Session session) {
        return auditData(subject, session, asList(PROV.Activity, AS.Delete));
    }

    /**
     * Create audit-related update data
     * @param subject the subject
     * @param session the session
     * @return the quads
     */
    public static List<Quad> auditUpdate(final IRI subject, final Session session) {
        return auditData(subject, session, asList(PROV.Activity, AS.Update));
    }

    private static List<Quad> auditData(final IRI subject, final Session session, final List<IRI> types) {
        final List<Quad> data = new ArrayList<>();
        final BlankNode bnode = rdf.createBlankNode();
        data.add(rdf.createQuad(PreferAudit, subject, PROV.wasGeneratedBy, bnode));
        types.forEach(t -> data.add(rdf.createQuad(PreferAudit, bnode, type, t)));
        data.add(rdf.createQuad(PreferAudit, bnode, PROV.wasAssociatedWith, session.getAgent()));
        data.add(rdf.createQuad(PreferAudit, bnode, PROV.startedAtTime,
                    rdf.createLiteral(session.getCreated().toString(), XSD.dateTime)));
        session.getDelegatedBy().ifPresent(delegate ->
                data.add(rdf.createQuad(PreferAudit, bnode, PROV.actedOnBehalfOf, delegate)));
        return data;
    }

    /**
     * Get all of the LDP resource (super) types for the given LDP interaction model
     * @param interactionModel the interaction model
     * @return a stream of types
     */
    public static Stream<IRI> ldpResourceTypes(final IRI interactionModel) {
        return of(interactionModel).filter(type -> RDFUtils.superClassOf.containsKey(type) || LDP.Resource.equals(type))
            .flatMap(type -> concat(ldpResourceTypes(RDFUtils.superClassOf.get(type)), of(type)));
    }

    /**
     * Convert an internal term to an external term
     * @param <T> the RDF term type
     * @param term the RDF term
     * @param baseUrl the base URL
     * @return a converted RDF term
     */
    @SuppressWarnings("unchecked")
    public static <T extends RDFTerm> T toExternalTerm(final T term, final String baseUrl) {
        if (term instanceof IRI) {
            final String iri = ((IRI) term).getIRIString();
            if (iri.startsWith(TRELLIS_PREFIX)) {
                return (T) rdf.createIRI(baseUrl + iri.substring(TRELLIS_PREFIX.length()));
            }
        }
        return term;
    }

    /**
     * Convert an external term to an internal term
     * @param <T> the RDF term type
     * @param term the RDF term
     * @param baseUrl the base URL
     * @return a converted RDF term
     */
    @SuppressWarnings("unchecked")
    public static <T extends RDFTerm> T toInternalTerm(final T term, final String baseUrl) {
        if (term instanceof IRI) {
            final String iri = ((IRI) term).getIRIString();
            if (iri.startsWith(baseUrl)) {
                return (T) rdf.createIRI(TRELLIS_PREFIX + iri.substring(baseUrl.length()));
            }
        }
        return term;
    }

    /**
     * Clean the identifier
     * @param identifier the identifier
     * @return the cleaned identifier
     */
    public static String cleanIdentifier(final String identifier) {
        final String id = identifier.split("#")[0].split("\\?")[0];
        if (id.endsWith("/")) {
            return id.substring(0, id.length() - 1);
        }
        return id;
    }

    /**
     * Clean the identifier
     * @param identifier the identifier
     * @return the cleaned identifier
     */
    public static IRI cleanIdentifier(final IRI identifier) {
        return rdf.createIRI(cleanIdentifier(identifier.getIRIString()));
    }

    private RDFUtils() {
        // prevent instantiation
    }
}
