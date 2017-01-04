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

/**
 * A class representing an RDF constraint violation
 *
 * @author acoburn
 */
public class ConstraintViolationException extends InvalidRdfException {

    private static final long serialVersionUID = 8046489554418284260L;

    /**
     * Create a new ConstraintViolationException
     */
    public ConstraintViolationException() {
    }

    /**
     * Create a new ConstraintViolationException with a custom message
     * @param message the message
     */
    public ConstraintViolationException(final String message) {
        super(message);
    }

    /**
     * Create a new ConstraintViolationException with a custom message and known cause
     * @param message the message
     * @param cause the cause
     */
    public ConstraintViolationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a new ConstraintViolationException with a known cause
     * @param cause the cause
     */
    public ConstraintViolationException(final Throwable cause) {
        super(cause);
    }
}
