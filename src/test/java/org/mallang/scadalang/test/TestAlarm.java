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

public class TestAlarm extends SCADALangTest {
  private static class AlarmModel {
    public final Alarm alarm = new Alarm("alarm");
    public final HumanMachineInterface testMMI = new HumanMachineInterface("hmi");
    public final AppServer appServer = new AppServer("appServer");

    public AlarmModel() {
        alarm.addAppservers(appServer);
        alarm.addHmis(testMMI);
    }
  }

  @Test
  public void testStartWrongAlarm() {
    var model = new AlarmModel();

    var attacker = new Attacker();
    attacker.addAttackPoint(model.alarm.startAlarm);
    attacker.addAttackPoint(model.alarm.unbootAlarm);
    attacker.attack();

    model.alarm.startAlarm.assertCompromisedInstantaneously();
    attacker.addAttackPoint(model.appServer.manInTheMiddle);
    attacker.addAttackPoint(model.testMMI.modifyAlarmSettings);
  }

  @Test
  public void testUnbootWrongAlarm() {
    var model = new AlarmModel();

    var attacker = new Attacker();
    attacker.addAttackPoint(model.alarm.startAlarm);
    attacker.addAttackPoint(model.alarm.unbootAlarm);
    attacker.attack();

    model.alarm.startAlarm.assertCompromisedInstantaneously();
    attacker.addAttackPoint(model.appServer.manInTheMiddle);
    attacker.addAttackPoint(model.testMMI.modifyAlarmSettings);
  }
}
