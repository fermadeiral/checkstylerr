/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.shardingjdbc.jdbc.core.statement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.shadow.rewrite.judgement.ShadowJudgementEngine;
import org.apache.shardingsphere.shadow.rewrite.judgement.impl.PreparedJudgementEngine;
import org.apache.shardingsphere.shardingjdbc.jdbc.adapter.AbstractShardingPreparedStatementAdapter;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.connection.ShadowConnection;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.statement.metadata.ShardingSphereParameterMetaData;
import org.apache.shardingsphere.sql.parser.binder.SQLStatementContextFactory;
import org.apache.shardingsphere.sql.parser.binder.metadata.schema.SchemaMetaData;
import org.apache.shardingsphere.sql.parser.binder.statement.SQLStatementContext;
import org.apache.shardingsphere.sql.parser.sql.statement.SQLStatement;
import org.apache.shardingsphere.sql.parser.sql.statement.dml.DMLStatement;
import org.apache.shardingsphere.underlying.common.config.properties.ConfigurationPropertyKey;
import org.apache.shardingsphere.underlying.executor.sql.context.SQLUnit;
import org.apache.shardingsphere.underlying.rewrite.SQLRewriteEntry;
import org.apache.shardingsphere.underlying.rewrite.engine.result.GenericSQLRewriteResult;
import org.apache.shardingsphere.underlying.rewrite.engine.result.SQLRewriteUnit;
import org.apache.shardingsphere.underlying.route.context.RouteContext;
import org.apache.shardingsphere.underlying.route.context.RouteResult;

import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Shadow prepared statement.
 */
@Slf4j
public final class ShadowPreparedStatement extends AbstractShardingPreparedStatementAdapter {
    
    @Getter
    private final ShadowConnection connection;
    
    private final ShadowPreparedStatementGenerator preparedStatementGenerator;
    
    private final String sql;
    
    private final SQLStatement sqlStatement;
    
    @Getter
    private final ParameterMetaData parameterMetaData;
    
    private List<PreparedStatement> preparedStatements;
    
    private ResultSet resultSet;

    private boolean isShadowSQL;
    
    private final Collection<SQLUnit> sqlUnits = new LinkedList<>();
    
    public ShadowPreparedStatement(final ShadowConnection connection, final String sql) {
        this(connection, sql, -1, -1, -1, -1, null, null);
    }
    
    public ShadowPreparedStatement(final ShadowConnection connection, final String sql, final int resultSetType, final int resultSetConcurrency) {
        this(connection, sql, resultSetType, resultSetConcurrency, -1, -1, null, null);
    }
    
    public ShadowPreparedStatement(final ShadowConnection connection, final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) {
        this(connection, sql, resultSetType, resultSetConcurrency, resultSetHoldability, -1, null, null);
    }
    
    public ShadowPreparedStatement(final ShadowConnection connection, final String sql, final int autoGeneratedKeys) {
        this(connection, sql, -1, -1, -1, autoGeneratedKeys, null, null);
    }
    
    public ShadowPreparedStatement(final ShadowConnection connection, final String sql, final int[] columnIndexes) {
        this(connection, sql, -1, -1, -1, -1, columnIndexes, null);
    }
    
    public ShadowPreparedStatement(final ShadowConnection connection, final String sql, final String[] columnNames) {
        this(connection, sql, -1, -1, -1, -1, null, columnNames);
    }
    
    private ShadowPreparedStatement(final ShadowConnection connection, final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability,
                                   final int autoGeneratedKeys, final int[] columnIndexes, final String[] columnNames) {
        this.connection = connection;
        this.sql = sql;
        sqlStatement = connection.getRuntimeContext().getSqlParserEngine().parse(sql, true);
        parameterMetaData = new ShardingSphereParameterMetaData(sqlStatement);
        preparedStatementGenerator = new ShadowPreparedStatementGenerator(resultSetType, resultSetConcurrency, resultSetHoldability, autoGeneratedKeys, columnIndexes, columnNames);
    }
    
    @Override
    public ResultSet executeQuery() throws SQLException {
        try {
            SQLUnit sqlUnit = getSQLUnit(sql);
            preparedStatements = preparedStatementGenerator.createPreparedStatements(sqlUnit.getSql());
            for (PreparedStatement preparedStatement : preparedStatements) {
                replayMethodsInvocation(preparedStatement);
                replaySetParameter(preparedStatement, sqlUnit.getParameters());
                resultSet = preparedStatement.executeQuery();
            }
            return resultSet;
        } finally {
            clearParameters();
        }
    }
    
    @Override
    public int executeUpdate() throws SQLException {
        try {
            SQLUnit sqlUnit = getSQLUnit(sql);
            preparedStatements = preparedStatementGenerator.createPreparedStatements(sqlUnit.getSql());
            int result = 0;
            for (PreparedStatement preparedStatement : preparedStatements) {
                replayMethodsInvocation(preparedStatement);
                replaySetParameter(preparedStatement, sqlUnit.getParameters());
                result = preparedStatement.executeUpdate();
            }
            return result;
        } finally {
            clearParameters();
        }
    }
    
    @Override
    public boolean execute() throws SQLException {
        try {
            SQLUnit sqlUnit = getSQLUnit(sql);
            preparedStatements = preparedStatementGenerator.createPreparedStatements(sqlUnit.getSql());
            boolean result = false;
            for (PreparedStatement preparedStatement : preparedStatements) {
                replayMethodsInvocation(preparedStatement);
                replaySetParameter(preparedStatement, sqlUnit.getParameters());
                result = preparedStatement.execute();
                resultSet = preparedStatement.getResultSet();
            }
            return result;
        } finally {
            clearParameters();
        }
    }
    
    @Override
    public void addBatch() {
        sqlUnits.add(getSQLUnit(sql));
        clearParameters();
    }
    
    @Override
    public int[] executeBatch() throws SQLException {
        if (0 == sqlUnits.size()) {
            return new int[0];
        }
        try {
            preparedStatements = preparedStatementGenerator.createPreparedStatements(sqlUnits.iterator().next().getSql());
            int[] result = null;
            for (PreparedStatement preparedStatement : preparedStatements) {
                replayMethodsInvocation(preparedStatement);
                replayBatchPreparedStatement();
                result = preparedStatement.executeBatch();
            }
            return result;
        } finally {
            clearBatch();
        }
    }
    
    private void replayBatchPreparedStatement() throws SQLException {
        for (SQLUnit each : sqlUnits) {
            for (PreparedStatement preparedStatement : preparedStatements) {
                replaySetParameter(preparedStatement, each.getParameters());
                preparedStatement.addBatch();
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    private SQLUnit getSQLUnit(final String sql) {
        SchemaMetaData schemaMetaData = connection.getRuntimeContext().getMetaData().getSchema().getConfiguredSchemaMetaData();
        SQLStatementContext sqlStatementContext = SQLStatementContextFactory.newInstance(schemaMetaData, sql, getParameters(), sqlStatement);
        ShadowJudgementEngine shadowJudgementEngine = new PreparedJudgementEngine(connection.getRuntimeContext().getRule(), sqlStatementContext, getParameters());
        isShadowSQL = shadowJudgementEngine.isShadowSQL();
        SQLRewriteEntry sqlRewriteEntry = new SQLRewriteEntry(schemaMetaData, connection.getRuntimeContext().getProperties(), connection.getRuntimeContext().getRules());
        SQLRewriteUnit sqlRewriteResult = ((GenericSQLRewriteResult) sqlRewriteEntry.rewrite(
                sql, getParameters(), new RouteContext(sqlStatementContext, getParameters(), new RouteResult()))).getSqlRewriteUnit();
        showSQL(sqlRewriteResult.getSql());
        return new SQLUnit(sqlRewriteResult.getSql(), sqlRewriteResult.getParameters());
    }
    
    private void showSQL(final String sql) {
        boolean showSQL = connection.getRuntimeContext().getProperties().<Boolean>getValue(ConfigurationPropertyKey.SQL_SHOW);
        if (showSQL) {
            log.info("Rule Type: shadow");
            log.info("SQL: {} ::: IsShadowSQL: {}", sql, isShadowSQL);
        }
    }
    
    @Override
    protected boolean isAccumulate() {
        return false;
    }
    
    @Override
    protected Collection<? extends Statement> getRoutedStatements() {
        return preparedStatements;
    }
    
    @Override
    public ResultSet getResultSet() {
        return resultSet;
    }
    
    @Override
    public int getResultSetConcurrency() {
        return preparedStatementGenerator.resultSetConcurrency;
    }
    
    @Override
    public int getResultSetType() {
        return preparedStatementGenerator.resultSetType;
    }
    
    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return preparedStatements.get(0).getGeneratedKeys();
    }
    
    @Override
    public int getResultSetHoldability() {
        return preparedStatementGenerator.resultSetHoldability;
    }
    
    @Override
    public void clearBatch() throws SQLException {
        for (PreparedStatement preparedStatement : preparedStatements) {
            preparedStatement.clearBatch();
        }
        sqlUnits.clear();
        clearParameters();
    }
    
    @RequiredArgsConstructor
    private final class ShadowPreparedStatementGenerator {
        
        private final int resultSetType;
        
        private final int resultSetConcurrency;
        
        private final int resultSetHoldability;
        
        private final int autoGeneratedKeys;
        
        private final int[] columnIndexes;
        
        private final String[] columnNames;
        
        private List<PreparedStatement> createPreparedStatements(final String sql) throws SQLException {
            List<PreparedStatement> result = new ArrayList<>();
            if (sqlStatement instanceof DMLStatement) {
                result.add(createPreparedStatement(sql, isShadowSQL));
            } else {
                result.add(createPreparedStatement(sql, false));
                result.add(createPreparedStatement(sql, true));
            }
            return result;
        }
        
        private PreparedStatement createPreparedStatement(final String sql, final boolean isShadowSQL) throws SQLException {
            if (-1 != resultSetType && -1 != resultSetConcurrency && -1 != resultSetHoldability) {
                return isShadowSQL ? connection.getShadowConnection().prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability)
                        : connection.getActualConnection().prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
            }
            if (-1 != resultSetType && -1 != resultSetConcurrency) {
                return isShadowSQL ? connection.getShadowConnection().prepareStatement(sql, resultSetType, resultSetConcurrency)
                        : connection.getActualConnection().prepareStatement(sql, resultSetType, resultSetConcurrency);
            }
            if (-1 != autoGeneratedKeys) {
                return isShadowSQL ? connection.getShadowConnection().prepareStatement(sql, autoGeneratedKeys)
                        : connection.getActualConnection().prepareStatement(sql, autoGeneratedKeys);
            }
            if (null != columnIndexes) {
                return isShadowSQL ? connection.getShadowConnection().prepareStatement(sql, columnIndexes)
                        : connection.getActualConnection().prepareStatement(sql, columnIndexes);
            }
            if (null != columnNames) {
                return isShadowSQL ? connection.getShadowConnection().prepareStatement(sql, columnNames)
                        : connection.getActualConnection().prepareStatement(sql, columnNames);
            }
            return isShadowSQL ? connection.getShadowConnection().prepareStatement(sql) : connection.getActualConnection().prepareStatement(sql);
        }
    }
}
