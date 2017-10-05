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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.trellisldp.api.RDFUtils.getInstance;

import org.apache.commons.rdf.api.RDF;
import org.junit.Test;
import org.trellisldp.vocabulary.LDP;

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
    @SuppressWarnings("deprecation")
    public void testLdpResourceTypes() {
        assertTrue(RDFUtils.ldpResourceTypes(LDP.BasicContainer).anyMatch(LDP.Resource::equals));
        assertTrue(RDFUtils.ldpResourceTypes(LDP.BasicContainer).anyMatch(LDP.RDFSource::equals));
        assertTrue(RDFUtils.ldpResourceTypes(LDP.BasicContainer).anyMatch(LDP.Container::equals));

        assertTrue(RDFUtils.ldpResourceTypes(LDP.NonRDFSource).anyMatch(LDP.Resource::equals));
        assertFalse(RDFUtils.ldpResourceTypes(LDP.NonRDFSource).anyMatch(LDP.RDFSource::equals));
    }
}
