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

import static java.util.Collections.singleton;
import static java.util.stream.Stream.empty;
import static java.util.stream.Stream.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;
import static org.trellisldp.vocabulary.Trellis.PreferUserManaged;

import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.simple.SimpleRDF;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.trellisldp.vocabulary.DC;

/**
 * @author acoburn
 */
@RunWith(MockitoJUnitRunner.class)
public class ResourceTest {

    private static final RDF rdf = new SimpleRDF();

    private final IRI identifier = rdf.createIRI("trellis:repository/resource");
    private final IRI prefer = rdf.createIRI("http://example.org/prefer/Custom");

    @Mock
    private Resource mockResource;

    @Before
    public void setUp() {
        doCallRealMethod().when(mockResource).getMembershipResource();
        doCallRealMethod().when(mockResource).getMemberRelation();
        doCallRealMethod().when(mockResource).getMemberOfRelation();
        doCallRealMethod().when(mockResource).getInsertedContentRelation();
        doCallRealMethod().when(mockResource).stream(any(IRI.class));
        doCallRealMethod().when(mockResource).stream(anyCollection());
        doCallRealMethod().when(mockResource).getBinary();
        doCallRealMethod().when(mockResource).isMemento();
        doCallRealMethod().when(mockResource).getInbox();
        doCallRealMethod().when(mockResource).getAnnotationService();

        when(mockResource.stream()).thenAnswer((x) -> empty());
    }

    @Test
    public void testResource() {
        assertEquals(0L, mockResource.stream(prefer).count());
        assertEquals(0L, mockResource.stream(singleton(prefer)).count());
        assertFalse(mockResource.getMembershipResource().isPresent());
        assertFalse(mockResource.getMemberRelation().isPresent());
        assertFalse(mockResource.getMemberOfRelation().isPresent());
        assertFalse(mockResource.getInsertedContentRelation().isPresent());
        assertFalse(mockResource.getMemberRelation().isPresent());
        assertFalse(mockResource.getBinary().isPresent());
        assertFalse(mockResource.isMemento());
        assertFalse(mockResource.getInbox().isPresent());
        assertFalse(mockResource.getAnnotationService().isPresent());
    }

    @Test
    public void testResource2() {
        final IRI subject = rdf.createIRI("ex:subject");
        when(mockResource.stream()).thenAnswer((x) -> of(
                    rdf.createQuad(prefer, subject, DC.title, rdf.createLiteral("A title")),
                    rdf.createQuad(PreferUserManaged, subject, DC.title, rdf.createLiteral("Other title"))));

        assertEquals(1L, mockResource.stream(prefer).count());
        assertEquals(1L, mockResource.stream(singleton(prefer)).count());
        assertFalse(mockResource.getMembershipResource().isPresent());
        assertFalse(mockResource.getMemberRelation().isPresent());
        assertFalse(mockResource.getMemberOfRelation().isPresent());
        assertFalse(mockResource.getInsertedContentRelation().isPresent());
        assertFalse(mockResource.getMemberRelation().isPresent());
        assertFalse(mockResource.getBinary().isPresent());
        assertFalse(mockResource.isMemento());
        assertFalse(mockResource.getInbox().isPresent());
        assertFalse(mockResource.getAnnotationService().isPresent());
    }
}
