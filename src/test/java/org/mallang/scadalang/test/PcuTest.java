package org.mallang.scadalang.test;

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

import core.Attacker;
import org.junit.jupiter.api.Test;
import org.mal_lang.scadalang.test.*;

public class PcuTest extends SCADALangTest {
    private static class PcuModel {
        public final AppServer appServer = new AppServer("testAppServer");
        public final ProcessCommunicationUnit pcu = new ProcessCommunicationUnit("testPCU");
        public final ProcessCommunicationUnit pcu2 = new ProcessCommunicationUnit("testPCU");

        public PcuModel() {
            appServer.addPcus(pcu);
            appServer.addPcus(pcu2);
            pcu.addAppservers(appServer);
        }
    }

    @Test
    public void testPCUServerServerCompromise() {
        var model = new PcuModel();

        var attacker = new Attacker();
        attacker.addAttackPoint(model.pcu.serverCompromise);
        attacker.attack();

        model.pcu.obtainValidAccount.assertCompromisedInstantaneously();
        model.pcu.useDefaultCredentials.assertCompromisedInstantaneously();
        model.pcu.accessingLogsToCollectInformation.assertCompromisedInstantaneously();
        model.pcu.serviceDiscovery.assertCompromisedInstantaneously();
        model.pcu.administrativeAccessToPCU.assertCompromisedInstantaneously();
        model.pcu.serviceStop.assertCompromisedInstantaneously();
        model.pcu.shutDownPCU.assertCompromisedInstantaneously();
        model.pcu.remoteSystemDiscovery.assertCompromisedInstantaneously();
        model.pcu.remoteFileCopy.assertCompromisedInstantaneously();
        model.pcu.stealingPasswordsFromMemory.assertCompromisedInstantaneously();
    }

    @Test
    public void testPCUEntryPointToAppserver() {
        var model = new PcuModel();
        var attacker = new Attacker();
        attacker.addAttackPoint(model.pcu.remoteSystemDiscovery);
        attacker.attack();

        model.appServer.serverCompromise.assertCompromisedInstantaneously();
    }
}

