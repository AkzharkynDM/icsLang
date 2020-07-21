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

public class TestAppservers extends SCADALangTest {
  private static class AppserversModel {
      public final Product product = new Product("testProduct");
      public final HumanMachineInterface humanMachineInterface = new HumanMachineInterface("testMMI");
      public final DataEngineeringServer dataEngineeringServer = new DataEngineeringServer("testDE");
      public final Antivirus antivirus = new Antivirus("testAntivirus");
      public final AppServer appServer = new AppServer("testAppServer");
      public final ProcessCommunicationUnit powerControlUnit = new ProcessCommunicationUnit("testPCU");
      public final DNSServer dnsServer = new DNSServer("testDNS");
      public final NISServer nisServer = new NISServer("testNIS");
      public final NTPServer ntpServer = new NTPServer("testNTS");
      public final ActiveDirectory activeDirectory = new ActiveDirectory("testAD");
      public final BackupServer backupServer = new BackupServer("testBackupServer");
      public final IccpServer iccpServer = new IccpServer("testIccpServer");

      public AppserversModel() {
          appServer.addIccpserver(iccpServer);
          iccpServer.addAppservers(appServer);
    }
  }

  @Test
  public void testAppserverCompromiseServer() {
    var model = new AppserversModel();

    var attacker = new Attacker();
    attacker.addAttackPoint(model.appServer.serverCompromise);
    attacker.attack();

      model.appServer.obtainValidAccount.assertCompromisedInstantaneously();
      model.appServer.useDefaultCredentials.assertCompromisedInstantaneously();
      model.appServer.accessingLogsToCollectInformation.assertCompromisedInstantaneously();
      model.appServer.serviceDiscovery.assertCompromisedInstantaneously();
      model.appServer.administrativeAccessToAppServer.assertCompromisedInstantaneously();
      model.appServer.serviceStop.assertCompromisedInstantaneously();
      model.appServer.remoteSystemDiscovery.assertCompromisedInstantaneously();
      model.appServer.installRogueMasterDevice.assertCompromisedInstantaneously();
      model.appServer.dataExfiltration.assertCompromisedInstantaneously();
      model.appServer.manInTheMiddle.assertCompromisedInstantaneously();
      model.appServer.masquerade.assertCompromisedInstantaneously();
  }
}
