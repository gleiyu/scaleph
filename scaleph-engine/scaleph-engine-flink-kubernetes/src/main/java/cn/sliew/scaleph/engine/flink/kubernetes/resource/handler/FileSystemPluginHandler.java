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

import cn.sliew.scaleph.config.resource.ResourceNames;
import cn.sliew.scaleph.config.storage.S3FileSystemProperties;
import cn.sliew.scaleph.engine.flink.kubernetes.resource.definition.job.FlinkDeploymentJob;
import cn.sliew.scaleph.engine.flink.kubernetes.service.dto.WsFlinkKubernetesJobDTO;
import io.fabric8.kubernetes.api.model.EnvVar;
import io.fabric8.kubernetes.api.model.EnvVarBuilder;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.api.model.PodFluent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FileSystemPluginHandler {

    private static final String S3_ENDPOINT = "s3.endpoint";
    private static final String S3_ACCESS_KEY = "s3.access-key";
    private static final String S3_SECRET_KEY = "s3.secret-key";
    private static final String S3_PATH_STYLE_ACCESS = "s3.path.style.access";

    @Autowired(required = false)
    private S3FileSystemProperties s3FileSystemProperties;

    public void customize(WsFlinkKubernetesJobDTO jobDTO, FlinkDeploymentJob job) throws Exception {
        PodBuilder podBuilder = Optional.ofNullable(job.getSpec().getPodTemplate()).map(pod -> new PodBuilder(pod)).orElse(new PodBuilder());
        cusomizePodTemplate(jobDTO, podBuilder);
        job.getSpec().setPodTemplate(podBuilder.build());

        Map<String, String> flinkConfiguration = Optional.ofNullable(job.getSpec().getFlinkConfiguration()).orElse(new HashMap<>());
        addFileSystemConfigOption(flinkConfiguration);
    }

    private void cusomizePodTemplate(WsFlinkKubernetesJobDTO jobDTO, PodBuilder builder) {
        builder.editOrNewMetadata().withName(ResourceNames.POD_TEMPLATE_NAME)
                .endMetadata();
        PodFluent.SpecNested<PodBuilder> spec = builder.editOrNewSpec();

        ContainerUtil.findFlinkMainContainer(spec)
                .addAllToEnv(buildEnableFileSystemEnv(jobDTO))
                .endContainer();

        spec.endSpec();
    }

    private void addFileSystemConfigOption(Map<String, String> flinkConfiguration) {
        if (s3FileSystemProperties != null) {
            flinkConfiguration.put(S3_ENDPOINT, MinioUtil.replaceLocalhost(s3FileSystemProperties.getEndpoint()));
            flinkConfiguration.put(S3_ACCESS_KEY, s3FileSystemProperties.getAccessKey());
            flinkConfiguration.put(S3_SECRET_KEY, s3FileSystemProperties.getSecretKey());
            flinkConfiguration.put(S3_PATH_STYLE_ACCESS, "true"); // container
        }
    }

    private List<EnvVar> buildEnableFileSystemEnv(WsFlinkKubernetesJobDTO jobDTO) {
        EnvVarBuilder builder = new EnvVarBuilder();
        builder.withName("ENABLE_BUILT_IN_PLUGINS");
        if (jobDTO.getWsDiJob() != null) {
            builder.withValue("flink-s3-fs-hadoop-1.15.4.jar");
        } else {
            builder.withValue("flink-s3-fs-hadoop-1.17.1.jar");
        }
        return Collections.singletonList(builder.build());
    }

}
