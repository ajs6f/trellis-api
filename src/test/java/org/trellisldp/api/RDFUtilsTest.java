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

import static java.time.Instant.now;
import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.trellisldp.api.RDFUtils.auditCreation;
import static org.trellisldp.api.RDFUtils.auditDeletion;
import static org.trellisldp.api.RDFUtils.auditUpdate;
import static org.trellisldp.api.RDFUtils.getInstance;
import static org.trellisldp.vocabulary.RDF.type;

import java.time.Instant;

import org.apache.commons.rdf.api.Dataset;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Literal;
import org.apache.commons.rdf.api.RDF;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.trellisldp.vocabulary.AS;
import org.trellisldp.vocabulary.PROV;
import org.trellisldp.vocabulary.Trellis;
import org.trellisldp.vocabulary.XSD;

/**
 * @author acoburn
 */
@RunWith(MockitoJUnitRunner.class)
public class RDFUtilsTest {

    private static RDF rdf = getInstance();

    @Mock
    private Session mockSession;

    private final Instant created = now();

    private final IRI subject = rdf.createIRI("trellis:repository/resource");

    @Before
    public void setUp() {
        when(mockSession.getAgent()).thenReturn(Trellis.AnonymousUser);
        when(mockSession.getCreated()).thenReturn(created);
        when(mockSession.getDelegatedBy()).thenReturn(of(Trellis.RepositoryAdministrator));
    }

    @Test
    public void testGetInstance() {
        assertNotNull(rdf);
    }

    @Test
    public void testAuditCreation() {
        final Dataset dataset = rdf.createDataset();
        auditCreation(subject, mockSession).forEach(dataset::add);
        assertTrue(dataset.getGraph(Trellis.PreferAudit).filter(graph -> graph.size() == dataset.size()).isPresent());
        assertTrue(dataset.contains(null, null, type, PROV.Activity));
        assertTrue(dataset.contains(null, null, type, AS.Create));
        assertTrue(dataset.contains(null, subject, PROV.wasGeneratedBy, null));
        assertTrue(dataset.contains(null, null, PROV.wasAssociatedWith, Trellis.AnonymousUser));
        assertTrue(dataset.contains(null, null, PROV.actedOnBehalfOf, Trellis.RepositoryAdministrator));
        assertTrue(dataset.contains(null, null, PROV.startedAtTime,
                    rdf.createLiteral(created.toString(), XSD.dateTime)));
        assertEquals(6L, dataset.size());
    }

    @Test
    public void testAuditDeletion() {
        final Dataset dataset = rdf.createDataset();
        auditDeletion(subject, mockSession).forEach(dataset::add);
        assertTrue(dataset.getGraph(Trellis.PreferAudit).filter(graph -> graph.size() == dataset.size()).isPresent());
        assertTrue(dataset.contains(null, null, type, PROV.Activity));
        assertTrue(dataset.contains(null, null, type, AS.Delete));
        assertTrue(dataset.contains(null, subject, PROV.wasGeneratedBy, null));
        assertTrue(dataset.contains(null, null, PROV.wasAssociatedWith, Trellis.AnonymousUser));
        assertTrue(dataset.contains(null, null, PROV.actedOnBehalfOf, Trellis.RepositoryAdministrator));
        assertTrue(dataset.contains(null, null, PROV.startedAtTime,
                    rdf.createLiteral(created.toString(), XSD.dateTime)));
        assertEquals(6L, dataset.size());
    }

    @Test
    public void testAuditUpdate() {
        final Dataset dataset = rdf.createDataset();
        auditUpdate(subject, mockSession).forEach(dataset::add);
        assertTrue(dataset.getGraph(Trellis.PreferAudit).filter(graph -> graph.size() == dataset.size()).isPresent());
        assertTrue(dataset.contains(null, null, type, PROV.Activity));
        assertTrue(dataset.contains(null, null, type, AS.Update));
        assertTrue(dataset.contains(null, subject, PROV.wasGeneratedBy, null));
        assertTrue(dataset.contains(null, null, PROV.wasAssociatedWith, Trellis.AnonymousUser));
        assertTrue(dataset.contains(null, null, PROV.actedOnBehalfOf, Trellis.RepositoryAdministrator));
        assertTrue(dataset.contains(null, null, PROV.startedAtTime,
                    rdf.createLiteral(created.toString(), XSD.dateTime)));
        assertEquals(6L, dataset.size());
    }

    @Test
    public void testToInternalExternalTerm() {
        final String baseUrl = "http://example.org/";
        final String path = "repo/resource";
        final IRI internalIRI = rdf.createIRI(RDFUtils.TRELLIS_PREFIX + path);
        final IRI externalIRI = rdf.createIRI(baseUrl + path);
        final IRI otherIRI = rdf.createIRI("http://example.com/foo/bar");
        final Literal literal = rdf.createLiteral("some value");

        assertEquals(externalIRI, RDFUtils.toExternalTerm(internalIRI, baseUrl));
        assertEquals(internalIRI, RDFUtils.toInternalTerm(externalIRI, baseUrl));
        assertEquals(otherIRI, RDFUtils.toExternalTerm(otherIRI, baseUrl));
        assertEquals(otherIRI, RDFUtils.toInternalTerm(otherIRI, baseUrl));
        assertEquals(literal, RDFUtils.toExternalTerm(literal, baseUrl));
        assertEquals(literal, RDFUtils.toInternalTerm(literal, baseUrl));
    }

    @Test
    public void testCleanIdentifier() {
        assertEquals("trellis:repository", RDFUtils.cleanIdentifier("trellis:repository/"));
        assertEquals("trellis:repository", RDFUtils.cleanIdentifier("trellis:repository/#hash"));
        assertEquals("trellis:repository", RDFUtils.cleanIdentifier("trellis:repository/?foo=bar#hash"));
        assertEquals("trellis:repository", RDFUtils.cleanIdentifier("trellis:repository#hash"));
        assertEquals("trellis:repository", RDFUtils.cleanIdentifier("trellis:repository?foo=bar"));
        assertEquals("trellis:repository", RDFUtils.cleanIdentifier("trellis:repository?foo=bar#hash"));
        final IRI identifier = rdf.createIRI("trellis:repository");
        assertEquals(identifier, RDFUtils.cleanIdentifier(rdf.createIRI("trellis:repository/")));
        assertEquals(identifier, RDFUtils.cleanIdentifier(rdf.createIRI("trellis:repository/#hash")));
        assertEquals(identifier, RDFUtils.cleanIdentifier(rdf.createIRI("trellis:repository/?foo=bar#hash")));
        assertEquals(identifier, RDFUtils.cleanIdentifier(rdf.createIRI("trellis:repository#hash")));
        assertEquals(identifier, RDFUtils.cleanIdentifier(rdf.createIRI("trellis:repository?foo=bar#hash")));
    }

}
