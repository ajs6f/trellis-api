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

import static java.util.Collections.singleton;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Triple;

/**
 * @author acoburn
 */
public interface Resource {

    /**
     * Get an identifier for this resource
     * @return the identifier
     */
    IRI getIdentifier();

    /**
     * Get the IRI for the original resource
     * @return the IRI for the original resource
     */
    default IRI getOriginalResource() {
        return getIdentifier();
    }

    /**
     * If a separate description exists for this resource, retrieve the IRI for it
     * @return the IRI for the description
     */
    Optional<IRI> getDescription();

    /**
     * Get the IRI for the described resource, if one exists
     * @return the IRI for the described resource
     */
    Optional<IRI> getDescribedResource();

    /**
     * Retrieve the IRI for this resource's timemap, if one exists
     * @return the IRI for the resource's timemap
     */
    Optional<IRI> getTimeMapResource();

    /**
     * Retrieve the parent resource, if it exists
     * @return the IRI for the parent resource
     */
    Optional<IRI> getParent();

    /**
     * Retrieve a stream of child resources
     * @return a stream of child resources
     */
    Stream<IRI> getChildren();

    /**
     * Retrieve a stream of Mementos for this resource
     * @return a stream of known Mementos
     */
    Stream<Memento> getTimeMap();

    /**
     * Retrieve the RDF Triples for a resource
     * @param category The category of triples to retrieve
     * @param <T> the triple category
     * @return the RDF triples
     */
    <T extends TripleCategory> Stream<Triple> getTriples(Collection<T> category);

    /**
     * Retrieve the RDF Triples for a resource
     * @param category The category of triples to retrieve
     * @param <T> the triple category
     * @return the RDF triples
     */
    default <T extends TripleCategory> Stream<Triple> getTriples(T category) {
        return getTriples(singleton(category));
    }

    /**
     * Test whether this resource is a Memento resource
     * @return whether the resource is a Memento
     */
    default Boolean isMemento() {
        return false;
    }

    /**
     * Test whether this resource is an LDP Container
     * @return whether the resource is a Container
     */
    default Boolean isContainer() {
        return false;
    }

    /**
     * Test whether this resource is an LDP RdfSource
     * @return whether the resource is a RDF resource
     */
    default Boolean isRdfSource() {
        return false;
    }

    /**
     * Test whether this resource is a Fixity resource
     * @return whether the resource is a Fixity resource
     */
    default Boolean isFixityResource() {
        return false;
    }

    /**
     * Test whether this resource is a Memento Time Map
     * @return whether the resource is a Memento Time Map
     */
    default Boolean isTimeMap() {
        return false;
    }

    /**
     * Get the ldp:inbox for this resource, if one exists
     * @return the ldp:inbox IRI
     */
    Optional<IRI> getInbox();

    /**
     * Set the ldp:inbox value for this resource
     * @param inbox an IRI for this resource's inbox
     */
    void setInbox(IRI inbox);

    /**
     * Get the acl:accessControl IRI, if one exists
     * @return an IRI used for access control
     */
    Optional<IRI> getAccessControl();

    /**
     * Set an acl:accessControl IRI
     * @param acl an IRI used for access control
     */
    void setAccessControl(IRI acl);

    /**
     * Get the rdf:type(s) for this resource
     * @return a stream of RDF Types
     */
    Stream<IRI> getTypes();

    /**
     * Set the rdf:type(s) for this resource
     * @param types the RDF Types of this resource
     */
    void setTypes(Collection<IRI> types);

    /**
     * Get the created-by value
     * @return the created-by value
     */
    Optional<IRI> getCreatedBy();

    /**
     * Set the created-by value
     * @param createdBy the value of created-by
     */
    void setCreatedBy(IRI createdBy);

    /**
     * Get the modified-by value, if one exists
     * @return the modified-by value
     */
    Optional<IRI> getModifiedBy();

    /**
     * Set the modified-by value
     * @param modifiedBy the value of last-modified-by
     */
    void setModifiedBy(IRI modifiedBy);

    /**
     * Get the created date
     * @return the created date
     */
    Instant getCreated();

    /**
     * Set the created date
     * @param created the created date
     */
    void setCreated(Instant created);

    /**
     * Get the last modified date
     * @return the last-modified date
     */
    Instant getModified();

    /**
     * Set the last modified date
     * @param modified the last-modified date
     */
    void setModified(Instant modified);

    /**
     * Update the resource triples with the provided graph
     * @param graph the updated triples to persist
     */
    void updateTriples(Graph graph);

    /**
     * Delete the resource
     */
    void delete();
}
