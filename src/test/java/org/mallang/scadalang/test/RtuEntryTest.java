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
import core.Defense;
import org.junit.jupiter.api.Test;
import org.mal_lang.scadalang.test.*;

public class RtuEntryTest extends SCADALangTest {
    private static class RtuModel {
        public final RemoteTerminalUnit rtu = new RemoteTerminalUnit("testRtuServer");
        public final ProcessCommunicationUnit pcu = new ProcessCommunicationUnit("testPCU");

        public RtuModel() {
            rtu.addPcus(pcu);
            pcu.addRtus(rtu);
        }
    }

    @Test
    public void testRTUEntryPointToPCU() {
        var model = new RtuModel();
        var attacker = new Attacker();
        attacker.addAttackPoint(model.rtu.remoteSystemDiscovery);
        attacker.attack();

        model.pcu.serverCompromise.assertCompromisedInstantaneously();
    }
}


