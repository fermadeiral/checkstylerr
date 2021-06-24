/**
 * Copyright (c) 2019, Mihai Emil Andronache
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1)Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 2)Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 3)Neither the name of zold-java-client nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.amihaiemil.zold.mock;

import java.io.IOException;

import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

/**
 * String payload of an HttpRequest.
 *
 * @author Boris Kuzmic (boris.kuzmic@gmail.com)
 * @version $Id: 1db86f61f6fedf3c284b924f607be751a25c06b6 $
 * @since 0.0.1
 */
public final class StringPayloadOf {

    /**
     * Payload as String.
     */
    private final String stringPayload;

    /**
     * Ctor.
     *
     * @param request The http request
     * @throws IllegalStateException if the request's payload cannot be read
     * @throws ParseException Parsing error of payload.
     */
    public StringPayloadOf(
        final ClassicHttpRequest request
    ) throws ParseException {
        try {
            final HttpEntity entity = request.getEntity();
            if (entity != null) {
                this.stringPayload = EntityUtils.toString(entity);
            } else {
                this.stringPayload = "";
            }
        } catch (final IOException ex) {
            throw new IllegalStateException(
                "Cannot read request payload", ex
            );
        }
    }

    /**
     * Returns payload value as String.
     * @return Payload as String.
     */
    public String value() {
        return this.stringPayload;
    }
}
