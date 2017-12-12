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

import static java.util.Collections.emptyMap;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.InputStream;
import java.util.Map;

import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.simple.SimpleRDF;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;

/**
 * @author acoburn
 */
@RunWith(JUnitPlatform.class)
public class BinaryServiceTest {

    private static final RDF rdf = new SimpleRDF();

    private final IRI identifier = rdf.createIRI("trellis:repository/resource");
    private final IRI other = rdf.createIRI("trellis:repository/other");
    private final String checksum = "blahblahblah";

    @Mock
    private BinaryService mockBinaryService;

    @Mock
    private InputStream mockInputStream;

    @Mock
    private BinaryService.Resolver mockResolver;

    @Mock
    private Session mockSession;

    @Mock
    private Binary mockBinary;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        doCallRealMethod().when(mockBinaryService).getContent(any());
        doCallRealMethod().when(mockBinaryService).setContent(any(), any(), any());
        doCallRealMethod().when(mockBinaryService).setContent(any(), any());
        doCallRealMethod().when(mockBinaryService).exists(any());
        doCallRealMethod().when(mockBinaryService).calculateDigest(any(), any());
        doCallRealMethod().when(mockBinaryService).purgeContent(any());
        doCallRealMethod().when(mockResolver).setContent(any(), any());
        when(mockResolver.getContent(any())).thenReturn(of(mockInputStream));
        when(mockResolver.exists(eq(identifier))).thenReturn(true);
        when(mockBinaryService.getResolver(any())).thenReturn(of(mockResolver));
        when(mockBinaryService.digest(any(), any())).thenReturn(of(checksum));
        doNothing().when(mockResolver).setContent(any(), any(), any());
        doNothing().when(mockResolver).purgeContent(any());
    }

    @Test
    public void testDefaultMethods() {
        final Map<String, String> data = emptyMap();
        assertEquals(of(mockResolver), mockBinaryService.getResolver(identifier));
        assertEquals(of(mockInputStream), mockBinaryService.getContent(identifier));
        assertTrue(mockBinaryService.exists(identifier));
        assertFalse(mockBinaryService.exists(other));
        mockBinaryService.setContent(identifier, mockInputStream, data);
        mockBinaryService.setContent(identifier, mockInputStream);
        mockBinaryService.purgeContent(identifier);
        assertTrue(mockBinaryService.getContent(other).isPresent());
        assertEquals(of(checksum), mockBinaryService.calculateDigest(other, "md5"));
        verify(mockResolver, times(2)).setContent(eq(identifier), eq(mockInputStream), eq(data));
        verify(mockResolver).purgeContent(eq(identifier));
    }

    @Test
    public void testMultipartUpload() {
        final String baseUrl = "baseurl";
        final String path = "path";
        final BinaryService.MultipartUpload upload = new BinaryService.MultipartUpload(baseUrl, path, mockSession,
                mockBinary);
        assertEquals(baseUrl, upload.getBaseUrl());
        assertEquals(path, upload.getPath());
        assertEquals(mockSession, upload.getSession());
        assertEquals(mockBinary, upload.getBinary());
    }
}
