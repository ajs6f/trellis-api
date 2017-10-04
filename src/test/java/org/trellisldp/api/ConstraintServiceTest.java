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

import static java.util.stream.Collectors.toSet;
import static org.trellisldp.api.RDFUtils.ldpResourceTypes;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.trellisldp.vocabulary.LDP;
import org.trellisldp.vocabulary.RDFS;
import org.apache.commons.rdf.api.IRI;
import org.junit.Test;

/**
 * @author acoburn
 */
public class ConstraintServiceTest {

    @Test
    public void testResource() {
        assertEquals(1, ldpResourceTypes(LDP.Resource).count());
        final Set<IRI> types = ldpResourceTypes(LDP.Resource).collect(toSet());
        assertEquals(1, types.size());
        assertTrue(types.contains(LDP.Resource));
    }

    @Test
    public void testNonLDP() {
        assertEquals(0, ldpResourceTypes(RDFS.label).count());
    }

    @Test
    public void testRDFSource() {
        assertEquals(2, ldpResourceTypes(LDP.RDFSource).count());
        final Set<IRI> types = ldpResourceTypes(LDP.RDFSource).collect(toSet());
        assertEquals(2, types.size());
        assertTrue(types.contains(LDP.Resource));
        assertTrue(types.contains(LDP.RDFSource));
    }

    @Test
    public void testNonRDFSource() {
        assertEquals(2, ldpResourceTypes(LDP.NonRDFSource).count());
        final Set<IRI> types = ldpResourceTypes(LDP.NonRDFSource).collect(toSet());
        assertEquals(2, types.size());
        assertTrue(types.contains(LDP.Resource));
        assertTrue(types.contains(LDP.NonRDFSource));
    }

    @Test
    public void testContainer() {
        assertEquals(3, ldpResourceTypes(LDP.Container).count());
        final Set<IRI> types = ldpResourceTypes(LDP.Container).collect(toSet());
        assertEquals(3, types.size());
        assertTrue(types.contains(LDP.Resource));
        assertTrue(types.contains(LDP.RDFSource));
        assertTrue(types.contains(LDP.Container));
    }

    @Test
    public void testBasicContainer() {
        assertEquals(4, ldpResourceTypes(LDP.BasicContainer).count());
        final Set<IRI> types = ldpResourceTypes(LDP.BasicContainer).collect(toSet());
        assertEquals(4, types.size());
        assertTrue(types.contains(LDP.Resource));
        assertTrue(types.contains(LDP.RDFSource));
        assertTrue(types.contains(LDP.Container));
        assertTrue(types.contains(LDP.BasicContainer));
    }

    @Test
    public void testDirectContainer() {
        assertEquals(4, ldpResourceTypes(LDP.DirectContainer).count());
        final Set<IRI> types = ldpResourceTypes(LDP.DirectContainer).collect(toSet());
        assertEquals(4, types.size());
        assertTrue(types.contains(LDP.Resource));
        assertTrue(types.contains(LDP.RDFSource));
        assertTrue(types.contains(LDP.Container));
        assertTrue(types.contains(LDP.DirectContainer));
    }

    @Test
    public void testIndirectContainer() {
        assertEquals(4, ldpResourceTypes(LDP.IndirectContainer).count());
        final Set<IRI> types = ldpResourceTypes(LDP.IndirectContainer).collect(toSet());
        assertEquals(4, types.size());
        assertTrue(types.contains(LDP.Resource));
        assertTrue(types.contains(LDP.RDFSource));
        assertTrue(types.contains(LDP.Container));
        assertTrue(types.contains(LDP.IndirectContainer));
    }
}
