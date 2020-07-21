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

public class TestActiveDirectory extends SCADALangTest {
    private static class ADModel {
        public final HumanMachineInterface humanMachineInterface = new HumanMachineInterface("testMMI");
        public final AppServer appServer = new AppServer("testAppServer");
        public final ActiveDirectory activeDirectory = new ActiveDirectory("testAD");
        public final ProcessCommunicationUnit pcu = new ProcessCommunicationUnit("testPCU");

        public ADModel() {
            humanMachineInterface.addActivedirectory(activeDirectory);
            activeDirectory.addHmis(humanMachineInterface);
            pcu.addActivedirectory(activeDirectory);
            activeDirectory.addPcus(pcu);
        }
    }

    @Test
    public void testAppserverConnectionAD() {
        var model = new ADModel();

        var attacker = new Attacker();
        attacker.addAttackPoint(model.appServer.remoteSystemDiscovery);
        attacker.attack();

        model.appServer.remoteSystemDiscovery.assertCompromisedInstantaneously();
    }

    @Test
    public void testHMIConnectionAD() {
        var model = new ADModel();

        var attacker = new Attacker();
        attacker.addAttackPoint(model.appServer.remoteSystemDiscovery);
        attacker.attack();

        model.appServer.remoteSystemDiscovery.assertCompromisedInstantaneously();
    }

    @Test
    public void testPCUConnectionAD() {
        var model = new ADModel();

        var attacker = new Attacker();
        attacker.addAttackPoint(model.appServer.remoteSystemDiscovery);
        attacker.attack();

        model.appServer.remoteSystemDiscovery.assertCompromisedInstantaneously();
    }
}
