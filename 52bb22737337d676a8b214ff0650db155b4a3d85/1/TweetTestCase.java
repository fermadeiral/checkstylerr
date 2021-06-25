/**
 * Copyright (c) 2016-2017, Mihai Emil Andronache
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  1)Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  2)Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *  3)Neither the name of charles-rest nor the names of its
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
package com.amihaiemil.charles.github;

import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

/**
 * Unit tests for {@link Tweet}
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id: e5a2a3f7153db8393f2f3b48281d1871f6d64936 $
 * @since 1.0.1
 */
public final class TweetTestCase {

	/**
	 * Tweet will log a message informing the commander that tweeting is off
	 * and can be enabled via .charles.yml
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void logsWhenTweetingIsOff() throws Exception {
		final Command com = Mockito.mock(Command.class);
		final CachedRepo repo = Mockito.mock(CachedRepo.class);
		Mockito.when(repo.charlesYml()).thenReturn(new CharlesYml.Default());
		Mockito.when(com.repo()).thenReturn(repo);
		
		final Logger logger = Mockito.mock(Logger.class);
		
		final Step tweet = new Tweet(new Step.Fake(true));
		tweet.perform(com, logger);
		Mockito.verify(logger).info("Tweeting is disabled, won't tweet. You can enable tweeting via .charles.yml file.");
	}
	
	/**
	 * Tweet will log a message informing the commander that tweeting is not possible because some
	 * mandatory system properties are not set,
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void logsWhenSysPropsAreMissing() throws Exception {
		final Command com = Mockito.mock(Command.class);
		final CachedRepo repo = Mockito.mock(CachedRepo.class);
		final CharlesYml yml = Mockito.mock(CharlesYml.class);
		Mockito.when(yml.tweet()).thenReturn(true);
		Mockito.when(repo.charlesYml()).thenReturn(yml);
		Mockito.when(com.repo()).thenReturn(repo);
		
		final Logger logger = Mockito.mock(Logger.class);
		
		final Step tweet = new Tweet(new Step.Fake(true));
		tweet.perform(com, logger);
		Mockito.verify(logger).warn("One of the 4 tweeter system properties is missing! Cannot tweet!");
	}
	
}
