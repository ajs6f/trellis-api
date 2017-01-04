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

import java.util.Collection;

import org.apache.commons.rdf.api.IRI;

/**
 * This interface provides access to data defined in an WebAC Authorization graph. Access to a resource can be
 * controlled via WebAccessControl, an RDF-based access control system. A resource can define an ACL resource
 * via the Link header, using rel=acl. It can also point to an ACL resource using a triple in the resource's own
 * RDF graph via acl:accessControl. Absent an acl:accessControl triple, the parent resource is checked, up to the
 * server's root resource.
 *
 * An ACL resource may contain multiple acl:Authorization sections. In an LDP context, this may be represented with
 * ldp:contains triples. Another common pattern is to refer to the acl:Authorization sections with blank nodes.
 *
 * @see <a href="https://www.w3.org/wiki/WebAccessControl">W3C WebAccessControl</a>
 * and <a href="https://github.com/solid/web-access-control-spec">Solid WebAC specification</a>
 *
 * @author acoburn
 */
public interface Authorization {

    /**
     * Retrieve the identifier for this Authorization
     * @return the identifier
     */
    IRI getIdentifier();

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
