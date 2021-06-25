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

package org.apache.shardingsphere.shardingproxy.backend.schema.impl;

import com.google.common.eventbus.Subscribe;
import lombok.Getter;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.core.log.ConfigurationLogger;
import org.apache.shardingsphere.core.metadata.ShardingTableMetaDataDecorator;
import org.apache.shardingsphere.core.rule.MasterSlaveRule;
import org.apache.shardingsphere.core.rule.ShardingRule;
import org.apache.shardingsphere.encrypt.metadata.EncryptTableMetaDataDecorator;
import org.apache.shardingsphere.orchestration.core.common.event.ShardingRuleChangedEvent;
import org.apache.shardingsphere.orchestration.core.common.rule.OrchestrationMasterSlaveRule;
import org.apache.shardingsphere.orchestration.core.common.rule.OrchestrationShardingRule;
import org.apache.shardingsphere.orchestration.core.registrycenter.event.DisabledStateChangedEvent;
import org.apache.shardingsphere.orchestration.core.registrycenter.schema.OrchestrationShardingSchema;
import org.apache.shardingsphere.shardingproxy.backend.schema.LogicSchema;
import org.apache.shardingsphere.shardingproxy.backend.schema.LogicSchemas;
import org.apache.shardingsphere.shardingproxy.config.yaml.YamlDataSourceParameter;
import org.apache.shardingsphere.shardingproxy.context.ShardingProxyContext;
import org.apache.shardingsphere.sql.parser.binder.metadata.index.IndexMetaData;
import org.apache.shardingsphere.sql.parser.binder.metadata.schema.SchemaMetaData;
import org.apache.shardingsphere.sql.parser.binder.metadata.schema.SchemaMetaDataLoader;
import org.apache.shardingsphere.sql.parser.binder.metadata.table.TableMetaData;
import org.apache.shardingsphere.sql.parser.binder.statement.SQLStatementContext;
import org.apache.shardingsphere.sql.parser.binder.statement.ddl.AlterTableStatementContext;
import org.apache.shardingsphere.sql.parser.binder.statement.ddl.CreateIndexStatementContext;
import org.apache.shardingsphere.sql.parser.binder.statement.ddl.CreateTableStatementContext;
import org.apache.shardingsphere.sql.parser.binder.statement.ddl.DropIndexStatementContext;
import org.apache.shardingsphere.sql.parser.binder.statement.ddl.DropTableStatementContext;
import org.apache.shardingsphere.sql.parser.sql.segment.ddl.index.IndexSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.generic.table.SimpleTableSegment;
import org.apache.shardingsphere.sql.parser.sql.statement.ddl.AlterTableStatement;
import org.apache.shardingsphere.sql.parser.sql.statement.ddl.CreateIndexStatement;
import org.apache.shardingsphere.sql.parser.sql.statement.ddl.CreateTableStatement;
import org.apache.shardingsphere.sql.parser.sql.statement.ddl.DropIndexStatement;
import org.apache.shardingsphere.sql.parser.sql.statement.ddl.DropTableStatement;
import org.apache.shardingsphere.underlying.common.config.properties.ConfigurationPropertyKey;
import org.apache.shardingsphere.underlying.common.database.type.DatabaseType;
import org.apache.shardingsphere.underlying.common.metadata.ShardingSphereMetaData;
import org.apache.shardingsphere.underlying.common.metadata.datasource.DataSourceMetas;
import org.apache.shardingsphere.underlying.common.metadata.schema.RuleSchemaMetaDataLoader;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

/**
 * Sharding schema.
 */
@Getter
public final class ShardingSchema extends LogicSchema {
    
    private ShardingRule shardingRule;
    
    private final ShardingSphereMetaData physicalMetaData;
    
    private final ShardingSphereMetaData metaData;
    
    public ShardingSchema(final String name, final Map<String, YamlDataSourceParameter> dataSources,
                          final ShardingRuleConfiguration shardingRuleConfig, final boolean isUsingRegistry) throws SQLException {
        super(name, dataSources);
        shardingRule = createShardingRule(shardingRuleConfig, dataSources.keySet(), isUsingRegistry);
        int maxConnectionsSizePerQuery = ShardingProxyContext.getInstance().getProperties().<Integer>getValue(ConfigurationPropertyKey.MAX_CONNECTIONS_SIZE_PER_QUERY);
        physicalMetaData = createPhysicalMetaData(maxConnectionsSizePerQuery);
        metaData = createAndMergeShardingMetaData(physicalMetaData.getSchema());
    }
    
    private ShardingRule createShardingRule(final ShardingRuleConfiguration shardingRuleConfig, final Collection<String> dataSourceNames, final boolean isUsingRegistry) {
        return isUsingRegistry ? new OrchestrationShardingRule(shardingRuleConfig, dataSourceNames) : new ShardingRule(shardingRuleConfig, dataSourceNames);
    }
    
    private ShardingSphereMetaData createPhysicalMetaData(final int maxConnectionsSizePerQuery) throws SQLException {
        Optional<String> actualDefaultDataSourceName = shardingRule.findActualDefaultDataSourceName();
        DatabaseType databaseType = LogicSchemas.getInstance().getDatabaseType();
        Map<String, DataSource> dataSourceMap = getBackendDataSource().getDataSources();
        SchemaMetaData schemaMetaData = actualDefaultDataSourceName.isPresent()
                ? SchemaMetaDataLoader.load(dataSourceMap.get(actualDefaultDataSourceName.get()), maxConnectionsSizePerQuery, databaseType.getName())
                : new SchemaMetaData(Collections.emptyMap());
        DataSourceMetas dataSourceMetas = new DataSourceMetas(LogicSchemas.getInstance().getDatabaseType(), getDatabaseAccessConfigurationMap());
        return new ShardingSphereMetaData(dataSourceMetas, schemaMetaData);
    }
    
    private ShardingSphereMetaData createAndMergeShardingMetaData(final SchemaMetaData physical) throws SQLException {
        DataSourceMetas dataSourceMetas = new DataSourceMetas(LogicSchemas.getInstance().getDatabaseType(), getDatabaseAccessConfigurationMap());
        RuleSchemaMetaDataLoader loader = new RuleSchemaMetaDataLoader(shardingRule.toRules());
        SchemaMetaData schemaMetaData = loader.load(LogicSchemas.getInstance().getDatabaseType(), getBackendDataSource().getDataSources(), ShardingProxyContext.getInstance().getProperties());
        schemaMetaData.merge(physical);
        return new ShardingSphereMetaData(dataSourceMetas, schemaMetaData);
    }
    
    /**
     * Renew sharding rule.
     *
     * @param shardingRuleChangedEvent sharding rule changed event.
     */
    @Subscribe
    public synchronized void renew(final ShardingRuleChangedEvent shardingRuleChangedEvent) {
        if (getName().equals(shardingRuleChangedEvent.getShardingSchemaName())) {
            ConfigurationLogger.log(shardingRuleChangedEvent.getShardingRuleConfiguration());
            shardingRule = new OrchestrationShardingRule(shardingRuleChangedEvent.getShardingRuleConfiguration(), getDataSources().keySet());
        }
    }
    
    /**
     * Renew disabled data source names.
     *
     * @param disabledStateChangedEvent disabled state changed event
     */
    @Subscribe
    public synchronized void renew(final DisabledStateChangedEvent disabledStateChangedEvent) {
        OrchestrationShardingSchema shardingSchema = disabledStateChangedEvent.getShardingSchema();
        if (getName().equals(shardingSchema.getSchemaName())) {
            for (MasterSlaveRule each : shardingRule.getMasterSlaveRules()) {
                ((OrchestrationMasterSlaveRule) each).updateDisabledDataSourceNames(shardingSchema.getDataSourceName(), disabledStateChangedEvent.isDisabled());
            }
        }
    }
    
    @Override
    public void refreshTableMetaData(final SQLStatementContext sqlStatementContext) throws SQLException {
        if (null == sqlStatementContext) {
            return;
        }
        if (sqlStatementContext instanceof CreateTableStatementContext) {
            refreshTableMetaData(((CreateTableStatementContext) sqlStatementContext).getSqlStatement());
        } else if (sqlStatementContext instanceof AlterTableStatementContext) {
            refreshTableMetaData(((AlterTableStatementContext) sqlStatementContext).getSqlStatement());
        } else if (sqlStatementContext instanceof DropTableStatementContext) {
            refreshTableMetaData(((DropTableStatementContext) sqlStatementContext).getSqlStatement());
        } else if (sqlStatementContext instanceof CreateIndexStatementContext) {
            refreshTableMetaData(((CreateIndexStatementContext) sqlStatementContext).getSqlStatement());
        } else if (sqlStatementContext instanceof DropIndexStatementContext) {
            refreshTableMetaData(((DropIndexStatementContext) sqlStatementContext).getSqlStatement());
        }
    }
    
    private void refreshTableMetaData(final CreateTableStatement createTableStatement) throws SQLException {
        String tableName = createTableStatement.getTable().getTableName().getIdentifier().getValue();
        loadTableMeta(tableName).ifPresent(tableMetaData -> getMetaData().getSchema().put(tableName, tableMetaData));
    }
    
    private void refreshTableMetaData(final AlterTableStatement alterTableStatement) throws SQLException {
        String tableName = alterTableStatement.getTable().getTableName().getIdentifier().getValue();
        loadTableMeta(tableName).ifPresent(tableMetaData -> getMetaData().getSchema().put(tableName, tableMetaData));
    }
    
    private void refreshTableMetaData(final DropTableStatement dropTableStatement) {
        for (SimpleTableSegment each : dropTableStatement.getTables()) {
            getMetaData().getSchema().remove(each.getTableName().getIdentifier().getValue());
        }
    }
    
    private void refreshTableMetaData(final CreateIndexStatement createIndexStatement) {
        if (null != createIndexStatement.getIndex()) {
            String indexName = createIndexStatement.getIndex().getIdentifier().getValue();
            getMetaData().getSchema().get(createIndexStatement.getTable().getTableName().getIdentifier().getValue()).getIndexes().put(indexName, new IndexMetaData(indexName));
        }
    }
    
    private void refreshTableMetaData(final DropIndexStatement dropIndexStatement) {
        Collection<String> indexNames = getIndexNames(dropIndexStatement);
        if (null != dropIndexStatement.getTable()) {
            for (String each : indexNames) {
                getMetaData().getSchema().get(dropIndexStatement.getTable().getTableName().getIdentifier().getValue()).getIndexes().remove(each);
            }
        }
        for (String each : indexNames) {
            if (findLogicTableName(getMetaData().getSchema(), each).isPresent()) {
                getMetaData().getSchema().get(dropIndexStatement.getTable().getTableName().getIdentifier().getValue()).getIndexes().remove(each);
            }
        }
    }
    
    private Optional<TableMetaData> loadTableMeta(final String tableName) throws SQLException {
        RuleSchemaMetaDataLoader loader = new RuleSchemaMetaDataLoader(shardingRule.toRules());
        Optional<TableMetaData> tableMetaData = loader.load(
                LogicSchemas.getInstance().getDatabaseType(), getBackendDataSource().getDataSources(), tableName, ShardingProxyContext.getInstance().getProperties());
        if (tableMetaData.isPresent()) {
            TableMetaData result = tableMetaData.get();
            result = new ShardingTableMetaDataDecorator().decorate(tableName, result, shardingRule);
            if (!shardingRule.getEncryptRule().getEncryptTableNames().isEmpty()) {
                result = new EncryptTableMetaDataDecorator().decorate(tableName, result, shardingRule.getEncryptRule());
            }
            return Optional.of(result);
        }
        return Optional.empty();
    }
    
    private Collection<String> getIndexNames(final DropIndexStatement dropIndexStatement) {
        Collection<String> result = new LinkedList<>();
        for (IndexSegment each : dropIndexStatement.getIndexes()) {
            result.add(each.getIdentifier().getValue());
        }
        return result;
    }
    
    private Optional<String> findLogicTableName(final SchemaMetaData schemaMetaData, final String logicIndexName) {
        for (String each : schemaMetaData.getAllTableNames()) {
            if (schemaMetaData.get(each).getIndexes().containsKey(logicIndexName)) {
                return Optional.of(each);
            }
        }
        return Optional.empty();
    }
}
