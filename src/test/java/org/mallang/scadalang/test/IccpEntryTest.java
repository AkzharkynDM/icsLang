package org.mallang.scadalang.test;

import org.junit.jupiter.api.Test;
import core.Attacker;
import org.mal_lang.scadalang.test.*;

public class IccpEntryTest extends SCADALangTest {
    private static class IccpEntryModel {
        public final AppServer appServer = new AppServer("testAppServer");
        public final IccpServer iccpServer = new IccpServer("testIccpServer");

        public IccpEntryModel() {
            appServer.addIccpserver(iccpServer);
            iccpServer.addAppservers(appServer);
        }
    }


    @Test
    public void testIccpServerServerCompromiseVulnerability() {
        var model = new IccpEntryTest.IccpEntryModel();

        var attacker = new Attacker();
        attacker.addAttackPoint(model.iccpServer.serverCompromise);
        attacker.attack();

        model.iccpServer.existsVulnerability.isEnabled();

        model.iccpServer.accessingLogsToCollectInformation.assertCompromisedInstantaneously();
        model.iccpServer.obtainValidAccount.assertCompromisedInstantaneously();
        model.iccpServer.remoteSystemDiscovery.assertCompromisedInstantaneously();
        model.iccpServer.remoteFileCopy.assertCompromisedInstantaneously();
        model.iccpServer.stealingPasswordsFromMemory.assertCompromisedInstantaneously();
    }

    @Test
    public void testIccpServerServerCompromise() {
        var model = new IccpEntryTest.IccpEntryModel();

        var attacker = new Attacker();
        attacker.addAttackPoint(model.iccpServer.serverCompromise);
        attacker.attack();

        model.iccpServer.accessingLogsToCollectInformation.assertCompromisedWithEffort();
        model.iccpServer.obtainValidAccount.assertCompromisedWithEffort();
        model.iccpServer.remoteSystemDiscovery.assertCompromisedWithEffort();
        model.iccpServer.remoteFileCopy.assertCompromisedWithEffort();
        model.iccpServer.stealingPasswordsFromMemory.assertCompromisedWithEffort();
    }

    @Test
    public void testIccpEntryPointToAppServer() {
        var model = new IccpEntryTest.IccpEntryModel();

        var attacker = new Attacker();
        attacker.addAttackPoint(model.iccpServer.remoteSystemDiscovery);
        attacker.attack();

        model.appServer.serverCompromise.assertCompromisedInstantaneously();
    }
}


