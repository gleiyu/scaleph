/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.sliew.scaleph.engine.flink.kubernetes.resource.handler;

import cn.sliew.scaleph.config.storage.FileSystemType;
import cn.sliew.scaleph.config.storage.S3FileSystemProperties;
import cn.sliew.scaleph.engine.flink.kubernetes.resource.definition.job.FlinkDeploymentJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class FlinkStateStorageHandler {

    @Autowired(required = false)
    private S3FileSystemProperties s3FileSystemProperties;

    public void customize(FlinkDeploymentJob job) throws Exception {
        Map<String, String> flinkConfiguration = Optional.ofNullable(job.getSpec().getFlinkConfiguration()).orElse(new HashMap<>());
        addStateStorageConfigOption(job.getMetadata().getName(), flinkConfiguration);
    }

    private void addStateStorageConfigOption(String jobId, Map<String, String> flinkConfiguration) {
        String schemaAndPath = getSchemaAndPath();
        flinkConfiguration.put("state.checkpoints.dir", getCheckpointPath(schemaAndPath, jobId));
        flinkConfiguration.put("state.savepoints.dir", getSavepointPath(schemaAndPath, jobId));
        flinkConfiguration.put("high-availability.storageDir", getHaPath(schemaAndPath, jobId));
        flinkConfiguration.put("high-availability", "org.apache.flink.kubernetes.highavailability.KubernetesHaServicesFactory");
    }

    private String getSchemaAndPath() {
        FileSystemType fileSystemType = FileSystemType.of(s3FileSystemProperties.getType());
        return String.format("%s%s", fileSystemType.getSchema(), s3FileSystemProperties.getBucket());
    }

    private static String getCheckpointPath(String schema, String jobId) {
        return String.format("%s/flink/jobs/%s/checkpoints/", schema, jobId);
    }

    private static String getSavepointPath(String schema, String jobId) {
        return String.format("%s/flink/jobs/%s/savepoints/", schema, jobId);
    }

    private static String getHaPath(String schema, String jobId) {
        return String.format("%s/flink/jobs/%s/ha/", schema, jobId);
    }

}
