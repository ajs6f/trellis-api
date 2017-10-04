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

import static java.util.Collections.unmodifiableMap;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;
import static org.trellisldp.vocabulary.RDF.type;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.Stream;

import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;

import org.trellisldp.vocabulary.LDP;

/**
 * The RDFUtils class provides a set of convenience methods related to
 * generating and processing RDF objects.
 *
 * @author acoburn
 */
public final class RDFUtils {

    // TODO - JDK9 ServiceLoader::findFirst
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
     * Get all of the LDP resource (super) types for the given LDP interaction model
     * @param interactionModel the interaction model
     * @return a stream of types
     */
    public static Stream<IRI> ldpResourceTypes(final IRI interactionModel) {
        return of(interactionModel).filter(type -> RDFUtils.superClassOf.containsKey(type) || LDP.Resource.equals(type))
            .flatMap(type -> concat(ldpResourceTypes(RDFUtils.superClassOf.get(type)), of(type)));
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
