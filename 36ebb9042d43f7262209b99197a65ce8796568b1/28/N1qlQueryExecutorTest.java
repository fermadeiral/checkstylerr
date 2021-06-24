/*
 * Copyright (C) 2015 Couchbase, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALING
 * IN THE SOFTWARE.
 */
package com.couchbase.client.java.query.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import com.couchbase.client.core.CouchbaseCore;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.error.QueryExecutionException;
import com.couchbase.client.java.query.AsyncN1qlQueryResult;
import com.couchbase.client.java.query.AsyncN1qlQueryRow;
import com.couchbase.client.java.query.DefaultAsyncN1qlQueryResult;
import com.couchbase.client.java.query.N1qlMetrics;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.PreparedN1qlQuery;
import com.couchbase.client.java.query.PreparedPayload;
import com.couchbase.client.java.query.N1qlParams;
import com.couchbase.client.java.query.Select;
import com.couchbase.client.java.query.Statement;
import com.couchbase.client.java.util.LRUCache;
import org.junit.Test;
import org.mockito.internal.stubbing.answers.ReturnsElementsOf;
import rx.Observable;

/**
 * Tests the functionality of {@link N1qlQueryExecutor}.
 *
 * @author Simon Baslé
 * @since 2.2
 */
public class N1qlQueryExecutorTest {

    @Test
    public void testPreparedStatementInCacheBypassesPreparation() throws Exception {
        LRUCache<String, PreparedPayload> cache = new LRUCache<String, PreparedPayload>(3);
        CouchbaseCore mockFacade = mock(CouchbaseCore.class);
        N1qlQueryExecutor executor = spy(new N1qlQueryExecutor(mockFacade, "default", "", cache));

        Statement st = Select.select("*");
        N1qlQuery q = N1qlQuery.simple(st, N1qlParams.build().adhoc(false));
        PreparedPayload payloadFromServer = new PreparedPayload(st, "server", "encodedPlan");
        PreparedPayload payloadInCache = new PreparedPayload(st, "cached", "encodedPlan");
        //put the statement in cache
        cache.put(st.toString(), payloadInCache);

        doReturn(Observable.empty()).when(executor).executeQuery(any(N1qlQuery.class));
        doReturn(Observable.just(payloadFromServer)).when(executor).prepare(any(Statement.class));
        doReturn(Observable.<AsyncN1qlQueryResult>empty()).when(executor)
                                                      .executePrepared(any(N1qlQuery.class), any(PreparedPayload.class));

        executor.execute(q).toBlocking().firstOrDefault(null);

        verify(executor).dispatchPrepared(any(N1qlQuery.class));
        verify(executor, never()).prepare(any(Statement.class));
        verify(executor).executePrepared(q, payloadInCache);
        verify(executor, never()).executePrepared(q, payloadFromServer);
        verify(executor, never()).prepareAndExecute(any(N1qlQuery.class));
        verify(executor, never()).retryPrepareAndExecuteOnce(any(QueryExecutionException.class), any(N1qlQuery.class));
        assertEquals(1, cache.size());
    }

    @Test
    public void testPreparedStatementNotInCacheTriggersPreparation() throws Exception {
        LRUCache<String, PreparedPayload> cache = new LRUCache<String, PreparedPayload>(3);
        CouchbaseCore mockFacade = mock(CouchbaseCore.class);
        N1qlQueryExecutor executor = spy(new N1qlQueryExecutor(mockFacade, "default", "", cache));

        Statement st = Select.select("*");
        N1qlQuery q = N1qlQuery.simple(st, N1qlParams.build().adhoc(false));
        PreparedPayload payloadFromServer = new PreparedPayload(st, "server", "encodedPlan");

        doReturn(Observable.just(payloadFromServer)).when(executor).prepare(any(Statement.class));
        doReturn(Observable.<AsyncN1qlQueryResult>empty()).when(executor)
                                                      .executePrepared(any(N1qlQuery.class), any(PreparedPayload.class));

        assertEquals(0, cache.size());
        executor.execute(q).toBlocking().firstOrDefault(null);

        verify(executor).dispatchPrepared(any(N1qlQuery.class));
        verify(executor).prepare(any(Statement.class));
        verify(executor).executePrepared(q, payloadFromServer);
        verify(executor).prepareAndExecute(any(N1qlQuery.class));
        verify(executor, never()).retryPrepareAndExecuteOnce(any(QueryExecutionException.class), any(N1qlQuery.class));
        assertEquals(1, cache.size());
    }

    @Test
    public void testCachedPlanExecutionErrorTriggersRetry() throws Exception {
        LRUCache<String, PreparedPayload> cache = new LRUCache<String, PreparedPayload>(3);
        CouchbaseCore mockFacade = mock(CouchbaseCore.class);
        N1qlQueryExecutor executor = spy(new N1qlQueryExecutor(mockFacade, "default", "", cache));

        Statement st = Select.select("*");
        N1qlQuery q = N1qlQuery.simple(st, N1qlParams.build().adhoc(false));
        PreparedPayload payloadFromServer = new PreparedPayload(st, "server", "encodedPlan");
        PreparedPayload payloadFromCache = new PreparedPayload(st, "cache", "encodedPlan");
        AsyncN1qlQueryResult result4050 = new DefaultAsyncN1qlQueryResult(Observable.<AsyncN1qlQueryRow>empty(),
                Observable.empty(),
                Observable.<N1qlMetrics>empty(),
                Observable.just(JsonObject.create().put("code", 4050).put("msg", "notRelevant")),
                Observable.just(false),
                true, "req", "");

        cache.put(st.toString(), payloadFromCache);
        doReturn(Observable.just(payloadFromServer)).when(executor).prepare(any(Statement.class));
        doReturn(Observable.just(result4050)).when(executor).executeQuery(any(PreparedN1qlQuery.class));

        assertEquals(1, cache.size());
        assertEquals(payloadFromCache, cache.values().iterator().next());

        executor.execute(q).toBlocking().firstOrDefault(null);

        verify(executor).dispatchPrepared(any(N1qlQuery.class));
        verify(executor, times(1)).executePrepared(q, payloadFromCache);
        verify(executor, times(1)).executePrepared(q, payloadFromServer);
        verify(executor, times(1)).retryPrepareAndExecuteOnce(any(Throwable.class), any(N1qlQuery.class));
        verify(executor, times(1)).prepare(any(Statement.class));
        assertEquals(1, cache.size());
        assertEquals(payloadFromServer, cache.values().iterator().next());
    }

    @Test
    public void testUncachedPlanExecutionErrorTriggersRetry() throws Exception {
        LRUCache<String, PreparedPayload> cache = new LRUCache<String, PreparedPayload>(3);
        CouchbaseCore mockFacade = mock(CouchbaseCore.class);
        N1qlQueryExecutor executor = spy(new N1qlQueryExecutor(mockFacade, "default", "", cache));

        Statement st = Select.select("*");
        N1qlQuery q = N1qlQuery.simple(st, N1qlParams.build().adhoc(false));
        PreparedPayload payloadFromServer1 = new PreparedPayload(st, "badserver", "encodedPlan");
        PreparedPayload payloadFromServer2 = new PreparedPayload(st, "goodserver", "encodedPlan");
        List<Observable<PreparedPayload>> payloads = Arrays.asList(
                Observable.just(payloadFromServer1),
                Observable.just(payloadFromServer2));
        Observable<DefaultAsyncN1qlQueryResult> result4050 = Observable.just(new DefaultAsyncN1qlQueryResult(
                Observable.<AsyncN1qlQueryRow>empty(),
                Observable.empty(),
                Observable.<N1qlMetrics>empty(),
                Observable.just(JsonObject.create().put("code", 4050).put("msg", "notRelevant")),
                Observable.just(false),
                true, "req", ""));
        Observable<DefaultAsyncN1qlQueryResult> resultOk = Observable.just(new DefaultAsyncN1qlQueryResult(
                Observable.<AsyncN1qlQueryRow>empty(),
                Observable.empty(),
                Observable.<N1qlMetrics>empty(),
                Observable.<JsonObject>empty(),
                Observable.just(true),
                true, "req", ""));

        doAnswer(new ReturnsElementsOf(payloads)).when(executor).prepare(any(Statement.class));
        doAnswer(new ReturnsElementsOf(Arrays.asList(result4050, resultOk))).when(executor).executeQuery(
                any(PreparedN1qlQuery.class));

        assertEquals(0, cache.size());

        AsyncN1qlQueryResult result = executor.execute(q).toBlocking().firstOrDefault(null);
        List<JsonObject> errors = result.errors().toList().toBlocking().first();
        boolean success = result.finalSuccess().toBlocking().first();

        verify(executor).dispatchPrepared(any(N1qlQuery.class));
        verify(executor, times(1)).executePrepared(q, payloadFromServer1);
        verify(executor, times(1)).executePrepared(q, payloadFromServer2);
        verify(executor, times(1)).retryPrepareAndExecuteOnce(any(Throwable.class), any(N1qlQuery.class));
        verify(executor, times(2)).prepare(any(Statement.class));
        assertEquals(1, cache.size());
        assertEquals(payloadFromServer2, cache.values().iterator().next());
        assertTrue(success);
        assertEquals(0, errors.size());
    }


    @Test
    public void testUncachedPlanExecutionDoubleErrorTriggersRetryThenFails() throws Exception {
        LRUCache<String, PreparedPayload> cache = new LRUCache<String, PreparedPayload>(3);
        CouchbaseCore mockFacade = mock(CouchbaseCore.class);
        N1qlQueryExecutor executor = spy(new N1qlQueryExecutor(mockFacade, "default", "", cache));

        Statement st = Select.select("*");
        N1qlQuery q = N1qlQuery.simple(st, N1qlParams.build().adhoc(false));
        PreparedPayload payloadFromServer1 = new PreparedPayload(st, "badserver", "encodedPlan");
        PreparedPayload payloadFromServer2 = new PreparedPayload(st, "goodserver", "encodedPlan");
        List<Observable<PreparedPayload>> payloads = Arrays.asList(
                Observable.just(payloadFromServer1),
                Observable.just(payloadFromServer2));
        Observable<DefaultAsyncN1qlQueryResult> result4050 = Observable.just(new DefaultAsyncN1qlQueryResult(
                Observable.<AsyncN1qlQueryRow>empty(),
                Observable.empty(),
                Observable.<N1qlMetrics>empty(),
                Observable.just(JsonObject.create().put("code", 4050).put("msg", "notRelevant")),
                Observable.just(false),
                true, "req", ""));
        Observable<DefaultAsyncN1qlQueryResult> resultOk = Observable.just(new DefaultAsyncN1qlQueryResult(
                Observable.<AsyncN1qlQueryRow>empty(),
                Observable.empty(),
                Observable.<N1qlMetrics>empty(),
                Observable.<JsonObject>empty(),
                Observable.just(true),
                true, "req", ""));

        doAnswer(new ReturnsElementsOf(
                payloads
        )).when(executor).prepare(any(Statement.class));
        doAnswer(new ReturnsElementsOf(Arrays.asList(
                result4050,
                result4050,
                resultOk
        ))).when(executor).executeQuery(any(PreparedN1qlQuery.class));

        assertEquals(0, cache.size());

        AsyncN1qlQueryResult result = executor.execute(q).toBlocking().firstOrDefault(null);
        List<JsonObject> errors = result.errors().toList().toBlocking().first();
        boolean success = result.finalSuccess().toBlocking().first();

        verify(executor).dispatchPrepared(any(N1qlQuery.class));
        verify(executor, times(1)).executePrepared(q, payloadFromServer1);
        verify(executor, times(1)).executePrepared(q, payloadFromServer2);
        verify(executor, times(1)).retryPrepareAndExecuteOnce(any(Throwable.class), any(N1qlQuery.class));
        verify(executor, times(2)).prepare(any(Statement.class));
        assertEquals(1, cache.size());
        assertEquals(payloadFromServer2, cache.values().iterator().next());
        assertFalse(success);
        assertEquals(1, errors.size());
        assertEquals(4050, errors.get(0).getInt("code").intValue());
    }

    private void testRetryCondition(int code, String msg, boolean retryExpected) throws Exception {
        LRUCache<String, PreparedPayload> cache = new LRUCache<String, PreparedPayload>(3);
        CouchbaseCore mockFacade = mock(CouchbaseCore.class);
        N1qlQueryExecutor executor = spy(new N1qlQueryExecutor(mockFacade, "default", "", cache));

        Statement st = Select.select("*");
        N1qlQuery q = N1qlQuery.simple(st, N1qlParams.build().adhoc(false));

        PreparedPayload payloadFromServer1 = new PreparedPayload(st, "badserver", "encodedPlan");
        PreparedPayload payloadFromServer2 = new PreparedPayload(st, "goodserver", "encodedPlan");
        List<Observable<PreparedPayload>> payloads = Arrays.asList(
                Observable.just(payloadFromServer1),
                Observable.just(payloadFromServer2));
        Observable<DefaultAsyncN1qlQueryResult> resultRetry = Observable.just(new DefaultAsyncN1qlQueryResult(
                Observable.<AsyncN1qlQueryRow>empty(),
                Observable.empty(),
                Observable.<N1qlMetrics>empty(),
                Observable.just(JsonObject.create().put("code", code).put("msg", msg)),
                Observable.just(false),
                true, "req", ""));
        Observable<DefaultAsyncN1qlQueryResult> resultOk = Observable.just(new DefaultAsyncN1qlQueryResult(
                Observable.<AsyncN1qlQueryRow>empty(),
                Observable.empty(),
                Observable.<N1qlMetrics>empty(),
                Observable.<JsonObject>empty(),
                Observable.just(true),
                true, "req", ""));

        doAnswer(new ReturnsElementsOf(
                payloads
        )).when(executor).prepare(any(Statement.class));

        doAnswer(new ReturnsElementsOf(Arrays.asList(
                resultRetry,
                resultOk
        ))).when(executor).executeQuery(any(PreparedN1qlQuery.class));

        assertEquals(0, cache.size());

        AsyncN1qlQueryResult result = executor.execute(q).toBlocking().firstOrDefault(null); //ok
        List<JsonObject> errors = result.errors().toList().toBlocking().first();
        boolean success = result.finalSuccess().toBlocking().first();

        if (retryExpected) {
            verify(executor, times(1)).retryPrepareAndExecuteOnce(any(Throwable.class), any(N1qlQuery.class));
            assertTrue(success);
            assertEquals(0, errors.size());
            assertEquals(1, cache.size());
            assertEquals(payloadFromServer2, cache.values().iterator().next());
        } else {
            verify(executor, never()).retryPrepareAndExecuteOnce(any(Throwable.class), any(N1qlQuery.class));
            assertFalse(success);
            assertEquals(1, errors.size());
            assertEquals(new Integer(code), errors.get(0).getInt("code"));
            assertEquals(msg, errors.get(0).getString("msg"));
            assertEquals(1, cache.size());
            assertEquals(payloadFromServer1, cache.values().iterator().next());
        }
    }

    @Test
    public void testRetryOn4050() throws Exception {
        testRetryCondition(4050, "notRelevant", true);
    }

    @Test
    public void testRetryOn4070() throws Exception {
        testRetryCondition(4070, "notRelevant", true);
    }

    @Test
    public void testRetryOn5000WithSpecificMessage() throws Exception {
        testRetryCondition(5000, "toto" + N1qlQueryExecutor.ERROR_5000_SPECIFIC_MESSAGE, true);
    }

    @Test
    public void testNoRetryOn5000WithRandomMessage() throws Exception {
        testRetryCondition(5000, "notRelevant", false);
    }
}
