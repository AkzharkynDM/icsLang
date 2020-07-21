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

public class HmiEntryTest extends SCADALangTest {
    private static class HmiEntryModel {
        public final AppServer appServer = new AppServer("testAppServer");
        public final HumanMachineInterface hmi = new HumanMachineInterface("testHMI");
        public final HumanMachineInterface hmi2 = new HumanMachineInterface("testHMI");

        public HmiEntryModel() {
            appServer.addHmis(hmi);
            appServer.addHmis(hmi2);
            hmi.addAppservers(appServer);
        }
    }

    @Test
    public void testHMIServerCompromised() {
        var model = new HmiEntryTest.HmiEntryModel();

        var attacker = new Attacker();
        attacker.addAttackPoint(model.hmi.serverCompromise);
        attacker.attack();

        model.hmi.accessingLogsToCollectInformation.assertCompromisedInstantaneously();
        model.hmi.obtainValidAccount.assertCompromisedInstantaneously();
        model.hmi.manInTheMiddle.assertCompromisedInstantaneously();
        model.hmi.modifyAlarmSettings.assertCompromisedInstantaneously();
        model.hmi.administrativeAccessToHMI.assertCompromisedInstantaneously();
        model.hmi.remoteSystemDiscovery.assertCompromisedInstantaneously();
        model.hmi.placeRansomwareUsingLockerGoga.assertCompromisedInstantaneously();

    }

   /* @Test
    public void testHMIEntryPointToAppServer() {
        var model = new HmiEntryModel();
        var attacker = new Attacker();
        attacker.addAttackPoint(model.appServer.remoteSystemDiscovery);
        attacker.attack();

        model.appServer.serverCompromise.assertCompromisedInstantaneously();
    }*/
}

