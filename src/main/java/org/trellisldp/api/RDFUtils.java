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

import java.util.ServiceLoader;

import org.apache.commons.rdf.api.RDF;

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
     * Get the Commons RDF instance in use
     * @return the RDF instance
     */
    public static RDF getInstance() {
        return rdf;
    }

    private RDFUtils() {
        // prevent instantiation
    }
}
