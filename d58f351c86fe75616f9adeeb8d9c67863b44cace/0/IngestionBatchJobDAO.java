/*
 * Copyright 2012-2013 inBloom, Inc. and its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.slc.sli.api.ingestion.DAO;

import java.util.List;

import org.slc.sli.api.ingestion.model.IngestionBatchJob;
import org.springframework.data.mongodb.core.query.Order;

/**
 * A dao interface for the newBatchJob collection in Mongo.
 * 
 * All queries to this collection need to be tenant specific so that a user cannot see items
 * from another tenant. This has various methods for getting data to support pagination as
 * well as sortability
 * 
 * @author mbrush
 */

public interface IngestionBatchJobDAO {

	public List<IngestionBatchJob> find(String tenantId);
	public List<IngestionBatchJob> find(String tenantId, Integer limit);
	public List<IngestionBatchJob> find(String tenantId, Integer limit, Integer offset);
	public List<IngestionBatchJob> find(String tenantId, Integer limit, Integer offset, String sortOn);
	public List<IngestionBatchJob> find(String tenantId, Integer limit, Integer offset, String sortOn, Order order);

	public IngestionBatchJob findOne(String tenantId, String id);

}
