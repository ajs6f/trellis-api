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
 * The required serialization formats supported by the repository.
 *
 * @author acoburn
 */
public enum Format implements Serialization {
    /* JSON-LD: https://www.w3.org/TR/json-ld/ */
    JSON_LD,

    /* RDF/XML: https://www.w3.org/TR/rdf-syntax-grammar/ */
    RDF_XML,

    /* Turtle: https://www.w3.org/TR/turtle/ */
    TURTLE,

    /* N-Triples: https://www.w3.org/TR/n-triples/ */
    N_TRIPLES
}
