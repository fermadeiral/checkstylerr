package com.amihaiemil.charles.github;

import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import com.amihaiemil.charles.aws.ElasticSearch;

import java.io.IOException;

/**
 * Unit tests for {@link IndexExistsCheck}
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id: 88ced0b091fc0f96e706a726bc29507ec38549f4 $
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
            new ElasticSearch.Fake(true),
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
            new ElasticSearch.Fake(false),
            new Step.Fake(false), new Step.Fake(true)
        );
        iec.perform(Mockito.mock(Command.class), Mockito.mock(Logger.class));
    }
}
