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

public class TestAccounts extends SCADALangTest {
  private static class AccountsModel {
    //public final Password passwordWindows = new Password("Vasteras!");
    //public final Password passwordLinux = new Password("Vasteras");
    //public final Host server = new Host("server");
    public final ActiveDirectory ad = new ActiveDirectory("testAD");
    public final ADLinuxAccount accountAdLinux = new ADLinuxAccount("sadmin");
    public final ADWindowsAccount accountAdWindows = new ADWindowsAccount("Administrator");
    public final HumanMachineInterface humanMachineInterface = new HumanMachineInterface("testHMI");

    public AccountsModel() {
      ad.addAdministrator(accountAdWindows);
      ad.addSadmin(accountAdLinux);
      accountAdLinux.addActivedirectory(ad);
      accountAdWindows.addActivedirectory(ad);
    }
  }

  @Test
  public void testADAccess() {
    var model = new AccountsModel();

    var attacker = new Attacker();
    attacker.addAttackPoint(model.accountAdLinux.bruteForce);
    attacker.addAttackPoint(model.accountAdWindows.bruteForce);

    attacker.attack();

    model.accountAdLinux.phishSCADAEngineer.assertCompromisedInstantaneously();
  }

  @Test
  public void testHMIAccess() {
    var model = new AccountsModel();

    var attacker = new Attacker();
    attacker.addAttackPoint(model.humanMachineInterface.serverCompromise);

    attacker.attack();
  }
}
