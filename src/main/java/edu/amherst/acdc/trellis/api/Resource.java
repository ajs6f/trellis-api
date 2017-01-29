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

import static java.util.Collections.singleton;
import static java.util.Optional.empty;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Triple;

/**
 * The central abstraction for a trellis-based repository.
 *
 * @see <a href="http://fedora.info/spec/">Fedora API Specification</a>
 *
 * @author acoburn
 */
public interface Resource {

    /**
     * The category of triples used when producing RDF.
     *
     * Repository resources are described using RDF triples, which are organized into
     * distinct categories. In an LDP context, the category of RDF triples returned
     * may be controlled with Prefer headers.
     * */
    interface TripleCategory {}

    /**
     * A minimal set of TripleCategories supported by an implementation.
     */
    enum TripleContext implements TripleCategory {
        /* User-managed properties */
        USER_MANAGED,

        /* Server-managed properties */
        SERVER_MANAGED,

        /* LDP Containment triples */
        LDP_CONTAINMENT,

        /* LDP Membership triples */
        LDP_MEMBERSHIP,

        /* Fedora Inbound References */
        FEDORA_INBOUND_REFERENCES,

        /* Fedora Embed Resources */
        FEDORA_EMBED_RESOURCES,

        /* Memento Timemap properties */
        MEMENTO_TIMEMAP
    }

    /**
     * Get an identifier for this resource
     * @return the identifier
     */
    IRI getIdentifier();

    /**
     * Get the LDP interaction model for this resource
     * @return the interaction model
     */
    IRI getInteractionModel();

    /**
     * Get the IRI for the original resource
     * @return the IRI for the original resource
     */
    default IRI getOriginal() {
        return getIdentifier();
    }

    /**
     * Get the IRI for the canonical resource
     * @return the IRI for the canonical resource
     */
    default IRI getCanonical() {
        return getOriginal();
    }

    /**
     * If a separate description exists for this resource, retrieve the IRI for it
     * @return the IRI for the description
     */
    default Optional<IRI> getDescribedBy() {
        return empty();
    }

    /**
     * Get the IRI for the described resource, if one exists
     * @return the IRI for the described resource
     */
    default Optional<IRI> getDescribes() {
        return empty();
    }

    /**
     * Retrieve the IRI for this resource's timemap, if one exists
     * @return the IRI for the resource's timemap
     */
    default Optional<IRI> getTimeMap() {
        return empty();
    }

    /**
     * Retrieve the resource that contains this resource, if it exists
     * @return the IRI for the resource that contains this resource
     */
    Optional<IRI> getContainedBy();

    /**
     * Retrieve a stream of resources contained by this resource
     * @return a stream of contained resources
     */
    default Stream<IRI> getContains() {
        return Stream.empty();
    }

    /**
     * Retrieve the membership resource if this is an LDP Direct or Indirect container
     * @return the membership resource
     */
    default Optional<IRI> getMembershipResource() {
        return empty();
    }

    /**
     * Retrieve the member relation if this is an LDP Direct or Indirect container
     * @return the ldp:hasMemberRelation IRI
     */
    default Optional<IRI> getMemberRelation() {
        return empty();
    }

    /**
     * Retrieve the member of relation IRI
     * @return the ldp:isMemberOfRelation IRI
     */
    default Optional<IRI> getMemberOfRelation() {
        return empty();
    }

    /**
     * Retrieve the inserted content relation if this is an LDP Indirect container
     * @return the inserted content relation
     */
    default Optional<IRI> getInsertedContentRelation() {
        return empty();
    }

    /**
     * Retrieve a stream of Mementos for this resource
     * @return a stream of known Mementos
     */
    Stream<MementoLink> getMementos();

    /**
     * Retrieve the RDF Triples for a resource
     * @param category The category of triples to retrieve
     * @param <T> the triple category
     * @return the RDF triples
     */
    <T extends TripleCategory> Stream<Triple> stream(Collection<T> category);

    /**
     * Retrieve the RDF Triples for a resource
     * @param category The category of triples to retrieve
     * @param <T> the triple category
     * @return the RDF triples
     */
    default <T extends TripleCategory> Stream<Triple> stream(T category) {
        return stream(singleton(category));
    }

    /**
     * Retrieve a datastream for this resouce, if it is a LDP-NR
     * @return the datastream
     */
    default Optional<Datastream> getDatastream() {
        return empty();
    }

    /**
     * Test whether this resource is a Memento resource
     * @return whether the resource is a Memento
     */
    default Boolean isMemento() {
        return false;
    }

    /**
     * Test whether this resource is an LDP Page
     * @return whether the resource is an LDP Page
     */
    default Boolean isPage() {
        return false;
    }

    /**
     * Get the IRI for the next LDP Page
     * @return the IRI for the next page
     */
    default Optional<IRI> getNext() {
        return empty();
    }

    /**
     * Get the ldp:inbox for this resource, if one exists
     * @return the ldp:inbox IRI
     */
    default Optional<IRI> getInbox() {
        return empty();
    }

    /**
     * Get the acl:accessControl IRI, if one exists
     * @return an IRI used for access control
     */
    default Optional<IRI> getAcl() {
        return empty();
    }

    /**
     * Get the rdf:type(s) for this resource
     * @return a stream of RDF Types
     */
    Stream<IRI> getTypes();

    /**
     * Get the creator value
     * @return the creator value
     */
    default Optional<IRI> getCreator() {
        return empty();
    }

    /**
     * Get the created date
     * @return the created date
     */
    Instant getCreated();

    /**
     * Get the last modified date
     * @return the last-modified date
     */
    Instant getModified();
}
