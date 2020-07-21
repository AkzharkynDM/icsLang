/*
 * Copyright 2019 Foreseeti AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mallang.scadalang.test;

import core.Attacker;
import org.junit.jupiter.api.Test;
import org.mal_lang.scadalang.test.*;

public class TestOtherServices extends SCADALangTest {
    private static class ServicesModel {
        public final DNSServer dnsServer = new DNSServer("testDNS");
        public final NISServer nisServer = new NISServer("testNIS");
        public final NTPServer ntpServer = new NTPServer("testNTS");

        public ServicesModel() {
        }
    }

    @Test
    public void testDNSCompromise() {
        var model = new ServicesModel();

        var attacker = new Attacker();
        attacker.addAttackPoint(model.dnsServer.serverCompromise);
        attacker.attack();

        model.dnsServer.shutDownDNSServiceOnPort.assertCompromisedInstantaneously();
        model.dnsServer.dnsPoisoning.assertCompromisedInstantaneously();
    }

    @Test
    public void testNISCompromise() {
        var model = new ServicesModel();

        var attacker = new Attacker();
        attacker.addAttackPoint(model.nisServer.serverCompromise);
        attacker.attack();
    }

    @Test
    public void testNTPCompromise() {
        var model = new ServicesModel();

        var attacker = new Attacker();
        attacker.addAttackPoint(model.ntpServer.serverCompromise);
        attacker.attack();

        model.ntpServer.alterClockConfigurations.assertCompromisedInstantaneously();
        model.ntpServer.serviceStop.assertCompromisedInstantaneously();

    }
}

