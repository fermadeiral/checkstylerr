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

import java.io.File;
import javax.sql.DataSource;
import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
import org.apache.shardingsphere.example.core.api.ExampleExecuteTemplate;
import org.apache.shardingsphere.example.core.api.service.ExampleService;

public class ExampleMain {
    
    public static void main(final String[] args) throws Exception {
        DataSource dataSource = YamlShardingSphereDataSourceFactory.createDataSource(getFile("/META-INF/sharding-databases-tables.yaml"));
        ExampleExecuteTemplate.run(getExampleService(dataSource));
        ExampleExecuteTemplate.runFailure(getExampleService(dataSource));
    }
    
    private static File getFile(final String fileName) {
        return new File(ExampleMain.class.getResource(fileName).getFile());
    }
    
    private static ExampleService getExampleService(DataSource dataSource) {
        return new OrderServiceImpl(dataSource);
    }
}
