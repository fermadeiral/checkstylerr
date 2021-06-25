package com.amihaiemil.charles.github;

import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import java.io.IOException;

/**
 * Unit tests for {@link IndexExistsCheck}
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id: 85e2996bbe1ccc8dbf0912db5e139bf9e758189c $
 * @since 1.0.0
 *
 */
public final class IndexExistsCheckTestCase {

    /**
     * IndexExistsCheck can tell if an index.
     */
    @Test
    public void indexExists() throws IOException {
        IndexExistsCheck iec = new IndexExistsCheck(
            new AwsEsRepository.Fake(true),
            new Step.Fake(true), new Step.Fake(false)
        );
        iec.perform(Mockito.mock(Command.class), Mockito.mock(Logger.class));
    }

    /**
     * IndexExistsCheck can tell if an index does not exist.
     */
    @Test
    public void indexDoesntExist() throws IOException {
        IndexExistsCheck iec = new IndexExistsCheck(
            new AwsEsRepository.Fake(false),
            new Step.Fake(false), new Step.Fake(true)
        );
        iec.perform(Mockito.mock(Command.class), Mockito.mock(Logger.class));
    }
}
