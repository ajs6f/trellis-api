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

/**
 * Repository resources are described using RDF triples, which are organized into
 * distinct categories. In an LDP context, the category of RDF triples returned
 * may be controlled with Prefer headers. The RDF Contexts defined here may be
 * extended, but these are the minimal contexts used to support the Fedora API
 * specification.
 *
 * @author acoburn
 */
public enum Context implements Resource.TripleCategory {
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

    /* Premis Fixity properties */
    PREMIS_FIXITY,

    /* Memento Timemap properties */
    MEMENTO_TIMEMAP
}
