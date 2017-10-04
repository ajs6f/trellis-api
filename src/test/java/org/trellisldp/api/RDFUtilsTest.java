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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.trellisldp.api.RDFUtils.getInstance;

import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.junit.Test;

/**
 * @author acoburn
 */
public class RDFUtilsTest {

    private static RDF rdf = getInstance();

    @Test
    public void testGetInstance() {
        assertNotNull(rdf);
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
