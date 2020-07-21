package org.mallang.scadalang.test;

import core.Attacker;
import core.Defense;
import org.junit.jupiter.api.Test;
import org.mal_lang.scadalang.test.*;

public class TestDatabases extends SCADALangTest {
    private static class DatabasesModel {
        public final OracleDatabase oracleDatabase = new OracleDatabase("testOracleDB");
        public final PostgreDatabase postgreDatabase = new PostgreDatabase("testPostgreDB");
        public final RealTimeDatabase realTimeDatabase = new RealTimeDatabase("testRDB");
        public final AppServer appServer = new AppServer("testAppServer");

        public DatabasesModel() {
            oracleDatabase.addAppservers(appServer);
            postgreDatabase.addAppservers(appServer);
            realTimeDatabase.addAppservers(appServer);
            appServer.addOracledatabases(oracleDatabase);
            appServer.addPostgredatabases(postgreDatabase);
            appServer.addRealtimedatabases(realTimeDatabase);
        }
    }

    @Test
    public void testOracleDatabasesBypassInputValidation() {
        var model = new DatabasesModel();

        var attacker = new Attacker();
        attacker.addAttackPoint(model.oracleDatabase.bypassInputValidation);

        attacker.attack();
        model.oracleDatabase.sqlInjection.assertUncompromised();
        model.oracleDatabase.commandLineExecuationThroughSQLInjection.assertUncompromised();
        model.oracleDatabase.dataExfiltration.assertUncompromised();
        model.oracleDatabase.accessingLogsToCollectInformation.assertUncompromised();
        model.oracleDatabase.obtainValidAccount.assertUncompromised();
        model.oracleDatabase.administrativeAccessToDatabase.assertUncompromised();
        model.oracleDatabase.dataCorruption.assertUncompromised();

    }

    @Test
    public void testOracleDatabasesBypassInputValidationWithDefense() {
        var model = new DatabasesModel();

        var attacker = new Attacker();
        attacker.addAttackPoint(model.oracleDatabase.bypassInputValidation);

        attacker.attack();

        var defense1 = new Defense("sanitizeClientSideInput");
        var defense2 = new Defense("validateClientSideInput");
        defense1.isEnabled();
        defense2.isEnabled();
        model.oracleDatabase.bypassInputValidation.assertUncompromised();
        model.oracleDatabase.sqlInjection.assertUncompromised();
        model.oracleDatabase.commandLineExecuationThroughSQLInjection.assertUncompromised();
        model.oracleDatabase.dataExfiltration.assertUncompromised();
        model.oracleDatabase.accessingLogsToCollectInformation.assertUncompromised();
        model.oracleDatabase.obtainValidAccount.assertUncompromised();
        model.oracleDatabase.administrativeAccessToDatabase.assertUncompromised();
        model.oracleDatabase.dataCorruption.assertUncompromised();
    }

    @Test
    public void testPostgreDatabasesBypassInputValidation() {
        var model = new DatabasesModel();

        var attacker = new Attacker();
        attacker.addAttackPoint(model.postgreDatabase.bypassInputValidation);

        attacker.attack();
        model.postgreDatabase.sqlInjection.assertUncompromised();
        model.postgreDatabase.commandLineExecuationThroughSQLInjection.assertUncompromised();
        model.postgreDatabase.dataExfiltration.assertUncompromised();
        model.postgreDatabase.accessingLogsToCollectInformation.assertUncompromised();
        model.postgreDatabase.obtainValidAccount.assertUncompromised();
        model.postgreDatabase.administrativeAccessToDatabase.assertUncompromised();
        model.postgreDatabase.dataCorruption.assertUncompromised();
    }

    @Test
    public void testPostgreDatabasesBypassInputValidationWithDefense() {
        var model = new DatabasesModel();

        var attacker = new Attacker();
        attacker.addAttackPoint(model.postgreDatabase.bypassInputValidation);

        attacker.attack();

        var defense1 = new Defense("sanitizeClientSideInput");
        var defense2 = new Defense("validateClientSideInput");
        defense1.isEnabled();
        defense2.isEnabled();

        model.postgreDatabase.sqlInjection.assertUncompromised();
        model.postgreDatabase.commandLineExecuationThroughSQLInjection.assertUncompromised();
        model.postgreDatabase.dataExfiltration.assertUncompromised();
        model.postgreDatabase.accessingLogsToCollectInformation.assertUncompromised();
        model.postgreDatabase.obtainValidAccount.assertUncompromised();
        model.postgreDatabase.administrativeAccessToDatabase.assertUncompromised();
        model.postgreDatabase.dataCorruption.assertUncompromised();
    }

    @Test
    public void testRealTimeDatabasesBypassInputValidation() {
        var model = new DatabasesModel();

        var attacker = new Attacker();
        attacker.addAttackPoint(model.realTimeDatabase.bypassInputValidation);

        attacker.attack();
        model.realTimeDatabase.sqlInjection.assertUncompromised();
        model.realTimeDatabase.commandLineExecuationThroughSQLInjection.assertUncompromised();
        model.realTimeDatabase.dataExfiltration.assertUncompromised();
        model.realTimeDatabase.accessingLogsToCollectInformation.assertUncompromised();
        model.realTimeDatabase.obtainValidAccount.assertUncompromised();
        model.realTimeDatabase.administrativeAccessToDatabase.assertUncompromised();
        model.realTimeDatabase.dataCorruption.assertUncompromised();
    }

    @Test
    public void testRealTimeDatabasesBypassInputValidationWithDefense() {
        var model = new DatabasesModel();

        var attacker = new Attacker();
        attacker.addAttackPoint(model.realTimeDatabase.bypassInputValidation);

        attacker.attack();

        var defense1 = new Defense("sanitizeClientSideInput");
        var defense2 = new Defense("validateClientSideInput");
        defense1.isEnabled();
        defense2.isEnabled();

        model.realTimeDatabase.sqlInjection.assertUncompromised();
        model.realTimeDatabase.commandLineExecuationThroughSQLInjection.assertUncompromised();
        model.realTimeDatabase.dataExfiltration.assertUncompromised();
        model.realTimeDatabase.accessingLogsToCollectInformation.assertUncompromised();
        model.realTimeDatabase.obtainValidAccount.assertUncompromised();
        model.realTimeDatabase.administrativeAccessToDatabase.assertUncompromised();
        model.realTimeDatabase.dataCorruption.assertUncompromised();
    }
}


