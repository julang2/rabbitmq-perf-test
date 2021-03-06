// Copyright (c) 2019 Pivotal Software, Inc.  All rights reserved.
//
// This software, the RabbitMQ Java client library, is triple-licensed under the
// Mozilla Public License 1.1 ("MPL"), the GNU General Public License version 2
// ("GPL") and the Apache License version 2 ("ASL"). For the MPL, please see
// LICENSE-MPL-RabbitMQ. For the GPL, please see LICENSE-GPL2.  For the ASL,
// please see LICENSE-APACHE2.
//
// This software is distributed on an "AS IS" basis, WITHOUT WARRANTY OF ANY KIND,
// either express or implied. See the LICENSE file for specific language governing
// rights and limitations of this software.
//
// If you have any questions regarding licensing, please contact us at
// info@rabbitmq.com.

package com.rabbitmq.perf;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ScenarioFactoryTest {

    @Test
    @SuppressWarnings("unchecked")
    public void paramsFromJSON() {
        String spec = "[{'name': 'consume', 'type': 'simple', 'params':" +
                "[{'time-limit': 30, 'producer-count': 4, 'consumer-count': 2, " +
                "  'rate': 10, 'exclusive': true, " +
                "  'confirm': 10, " +
                "  'body': ['file1.json','file2.json'], 'body-content-type' : 'application/json'}]}]";
        List<Map> scenariosJson = new Gson().fromJson(spec, List.class);
        Map scenario = scenariosJson.get(0);
        MulticastParams params = ScenarioFactory.paramsFromJSON((Map) ((List) scenario.get("params")).get(0));
        assertThat(params.getTimeLimit()).isEqualTo(30);
        assertThat(params.getProducerCount()).isEqualTo(4);
        assertThat(params.getConsumerCount()).isEqualTo(2);
        assertThat(params.getProducerRateLimit()).isEqualTo(10.0f);
        assertThat(params.isExclusive()).isTrue();
        assertThat(params.getConfirm()).isEqualTo(10L);
        assertThat(params.getBodyFiles()).hasSize(2);
        assertThat(params.getBodyFiles()).contains("file1.json", "file2.json");
        assertThat(params.getBodyContentType()).isEqualTo("application/json");
    }

}
