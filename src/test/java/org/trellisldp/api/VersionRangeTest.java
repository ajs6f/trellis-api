/*
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
package org.trellisldp.api;

import static java.time.Instant.parse;
import static org.junit.Assert.assertEquals;

import java.time.Instant;

import org.junit.Test;

/**
 * @author acoburn
 */
public class VersionRangeTest {

    private final Instant from = parse("2015-04-18T10:30:00.00Z");
    private final Instant until = parse("2016-10-15T11:10:00.00Z");

    @Test
    public void testVersionRange() {
        final VersionRange range = new VersionRange(from, until);
        assertEquals(from, range.getFrom());
        assertEquals(until, range.getUntil());
    }

    @Test(expected = NullPointerException.class)
    public void testInvalidArguments1() {
        new VersionRange(null, until);
    }

    @Test(expected = NullPointerException.class)
    public void testInvalidArguments2() {
        new VersionRange(from, null);
    }
}
