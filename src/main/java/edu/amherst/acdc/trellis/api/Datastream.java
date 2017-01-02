/*
 * Copyright 2016 Amherst College
 *
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
package edu.amherst.acdc.trellis.api;

import java.time.Instant;
import java.util.Optional;

import org.apache.commons.rdf.api.IRI;

/**
 * The LDP specification divides resources into two categories: RDF resources and
 * non-RDF resources. Non-RDF resources also have a corresponding RDF description.
 *
 * For those resources that are non-RDF resources (LDP-NR), the base Resource interface
 * will make a Datastream object available. The datastream content is not accessed directly
 * through the Datastream interface, but rather an identifier is returned, which may
 * be resolved by an external system.
 *
 * The Datastream interface also provides access methods for the MimeType, original filename,
 * and checksum digest values.
 *
 * @author acoburn
 */
public interface Datastream {

    /**
     * Retrieve an IRI identifying the location of the datastream
     * @return the resource content
     */
    IRI getIdentifier();

    /**
     * Retrieve the mime-type of the resource, if one was specified
     * @return the mime-type
     */
    Optional<String> getMimeType();

    /**
     * Retrieve the original filename for the resource, if one was specified
     * @return the filename
     */
    Optional<String> getFileName();

    /**
     * Get the checksum digest for the given algorithm
     * @param algorithm the algorithm
     * @return a digest value if one exists for the provided algorithm
     */
    Optional<String> getDigest(String algorithm);

    /**
     * Retrieve the created date of the datastream
     * @return the created date
     */
    Instant getCreated();

    /**
     * Retrieve the last-modified date of the datastream
     * @return the last-modified date
     */
    Instant getModified();
}
