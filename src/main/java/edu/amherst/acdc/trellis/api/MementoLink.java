/*
 * Copyright Amherst College
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

import org.apache.commons.rdf.api.IRI;

/**
 * This interface provides links to Memento resources, which could be
 * included in a TimeMap or in Link headers.
 *
 * @see <a href="http://tools.ietf.org/html/rfc7089">IETF RFC 7089</a>
 *
 * @author acoburn
 */
public interface MementoLink {

    /**
     * Get the IRI for this Memento
     * @return the IRI for this Memento
     */
    IRI getIdentifier();

    /**
     * Get the datetime corresponding to when the temporal interval covered by this Memento begins
     * @return the from value for this Memento
     */
    Instant getFrom();

    /**
     * Get the datetime corresponding to when the temporal interval covered by this Memento ends
     * @return the until value for this Memento
     */
    Instant getUntil();
}
