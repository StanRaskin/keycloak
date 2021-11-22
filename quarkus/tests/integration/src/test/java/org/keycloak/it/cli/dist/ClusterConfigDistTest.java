/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
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

package org.keycloak.it.cli.dist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.keycloak.it.cli.StartCommandTest;
import org.keycloak.it.junit5.extension.DistributionTest;

import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;

@DistributionTest
public class ClusterConfigDistTest {

    @Test
    @Launch({ "start-dev", "--cluster=default" })
    void changeClusterSetting(LaunchResult result) {
        assertTrue(result.getOutput().contains("Received new cluster view"));
    }

    @Test
    @Launch({ "start-dev", "--cluster=invalid" })
    void failInvalidClusterConfig(LaunchResult result) {
        assertTrue(result.getErrorOutput().contains("ERROR: Could not load cluster configuration file"));
    }

    @Test
    @Launch({ "start-dev", "--cluster=default", "--cluster-stack=kubernetes" })
    void failMisConfiguredClusterStack(LaunchResult result) {
        assertTrue(result.getOutput().contains("ERROR: dns_query can not be null or empty"));
    }

    @Test
    @Launch({ "start-dev", "--cluster-stack=invalid" })
    void failInvalidClusterStack(LaunchResult result) {
        assertTrue(result.getErrorOutput().contains("Invalid value for option '--cluster-stack': invalid. Expected values are: tcp, udp, kubernetes, ec2, azure, google"));
    }
}