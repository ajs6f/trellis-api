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

import java.io.InputStream;
import java.util.Optional;

/**
 * @author acoburn
 */
public interface NonRdfSource extends Resource {

    /**
     * Retrieve the content of the NonRdfSource
     * @return the resource content
     */
    InputStream getContent();

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
     * Set the content of the resource
     * @param content the resource content
     * @param mimeType the mime type of the resource
     */
    void setContent(final InputStream content, final String mimeType);

    /**
     * Set the original filename for the resource
     * @param filename the original filename
     */
    void setFileName(final String filename);

    /**
     * Get the checksum digest for the given algorithm
     * @param algorithm the algorithm
     * @return a digest value if one exists for the provided algorithm
     */
    Optional<String> getDigest(final String algorithm);
}
