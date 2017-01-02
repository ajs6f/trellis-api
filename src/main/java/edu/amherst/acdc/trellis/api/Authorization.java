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

import java.util.Collection;

import org.apache.commons.rdf.api.IRI;

/**
 * @author acoburn
 */
public interface Authorization {

    /**
     * Retrieve the agents that are associated with this Authorization
     * @return the Agent values
     */
    Collection<String> getAgents();

    /**
     * Retrieve the agent classes that are associated with this Authorization
     * @return the Agent class values
     */
    Collection<IRI> getAgentClasses();

    /**
     * Retrieve the agent groups that are associated with this Authorization
     * @return the Agent groups values
     */
    Collection<IRI> getAgentGroups();

    /**
     * Retrieve the access modes that are associated with this Authorization
     * @return the access mode values
     */
    Collection<IRI> getModes();

    /**
     * Retrieve the resource identifiers to which this Authorization applies
     * @return the accessTo values
     */
    Collection<IRI> getAccessTo();

    /**
     * Retrieve the accessToClass values that are associated with this Authorization
     * @return the accessToClass values
     */
    Collection<IRI> getAccessToClasses();

    /**
     * Retrieve the directories for which this authorization is used for new resources in the container
     * @return the resource identifiers
     */
    Collection<IRI> getDefaultForNew();
}
