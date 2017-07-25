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
import static java.util.Optional.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;

import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.simple.SimpleRDF;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.trellisldp.vocabulary.Trellis;

/**
 * @author acoburn
 */
@RunWith(MockitoJUnitRunner.class)
public class ResourceTest {

    private static final RDF rdf = new SimpleRDF();

    private final IRI identifier = rdf.createIRI("trellis:repository/resource");

    @Mock
    private Resource mockResource;

    @Before
    public void setUp() {
        doCallRealMethod().when(mockResource).getContains();
        doCallRealMethod().when(mockResource).getMembershipResource();
        doCallRealMethod().when(mockResource).getMemberRelation();
        doCallRealMethod().when(mockResource).getMemberOfRelation();
        doCallRealMethod().when(mockResource).getInsertedContentRelation();
        doCallRealMethod().when(mockResource).stream(any(IRI.class));
        doCallRealMethod().when(mockResource).stream(anyCollection());
        doCallRealMethod().when(mockResource).getBinary();
        doCallRealMethod().when(mockResource).isMemento();
        doCallRealMethod().when(mockResource).isPage();
        doCallRealMethod().when(mockResource).getNext();
        doCallRealMethod().when(mockResource).getInbox();
        doCallRealMethod().when(mockResource).getAcl();
        doCallRealMethod().when(mockResource).getAnnotationService();

        when(mockResource.stream()).thenAnswer((x) -> Stream.empty());
    }

    @Test
    public void testResource() {
        assertEquals(0L, mockResource.getContains().count());
        assertEquals(0L, mockResource.stream(Trellis.PreferUserManaged).count());
        assertEquals(0L, mockResource.stream(singleton(Trellis.PreferAudit)).count());
        assertEquals(empty(), mockResource.getMembershipResource());
        assertEquals(empty(), mockResource.getMemberRelation());
        assertEquals(empty(), mockResource.getMemberOfRelation());
        assertEquals(empty(), mockResource.getInsertedContentRelation());
        assertEquals(empty(), mockResource.getMemberRelation());
        assertEquals(empty(), mockResource.getBinary());
        assertFalse(mockResource.isMemento());
        assertFalse(mockResource.isPage());
        assertEquals(empty(), mockResource.getNext());
        assertEquals(empty(), mockResource.getInbox());
        assertEquals(empty(), mockResource.getAcl());
        assertEquals(empty(), mockResource.getAnnotationService());
    }
}
