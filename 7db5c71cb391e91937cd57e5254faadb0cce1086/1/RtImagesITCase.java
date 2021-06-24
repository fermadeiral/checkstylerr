/**
 * Copyright (c) 2018-2019, Mihai Emil Andronache
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1)Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 2)Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 3)Neither the name of docker-java-api nor the names of its
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
package com.amihaiemil.docker;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Integration tests for {@link RtImages}.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id$
 * @since 0.0.1
 * @todo #153:30min Edit:Add integration tests for filters.
 */
public final class RtImagesITCase {

    /**
     * {@link RtImages} can iterate over the Images, with the default filters.
     * @throws Exception If an error occurs.
     */
    @Test
    public void iteratesImages() throws Exception {
        final Images images = new LocalDocker(
                new File("/var/run/docker.sock")
        ).images();
        for(final Image img : images) {
            MatcherAssert.assertThat(
                    img.getInt("Created"), Matchers.notNullValue()
            );
            MatcherAssert.assertThat(
                    img.getString("Id"), Matchers.startsWith("sha256:")
            );
        }
    }


    /**
     * {@link RtImages} can filter Images and return filtered Images.
     * @throws Exception If an error occurs.
     * @todo #187:30min Edit:To have multiple controlled images for filtering and
     *  not the ubuntu image dependency for this test will be nice to have
     *  the build Images implemented as described here:
     *  https://docs.docker.com/engine/api/v1.37/#operation/ImageBuild.
     */
    @Test
    public void filterImage() throws Exception {
        final Map<String, Iterable<String>> filters = new HashMap<>();
        filters.put(
                "reference",
                Arrays.asList(
                        "ubuntu"
                )
        );
        final Images images = new LocalDocker(
                new File("/var/run/docker.sock")
        ).images();
        Images filtered = images.filter(filters);
        for(final Image img : filtered) {
            MatcherAssert.assertThat(
                    img.getJsonArray("RepoTags").getString(0),
                    Matchers.is("ubuntu:latest")
            );
        }
    }
}
