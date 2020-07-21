package com.logic;
import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.*;
import com.helpers.*;
import com.pojoJSONParsing.*;
import com.pojoMAL.*;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppserverMALLogic extends BaseLogic {

    public static void createMAlFromConfdb(){
        initAll();
        addBaseAssets();
        createAbstractClasses();
        createAssetClasses();
        findAssetsConnectionsFromConfdb();
        buildAttackTree();
        //addCoreLangAssociations();
        attachDefaultProtection();

        allProvidedAssets.removeAll(allProvidedAssetsNotIncludedApp);
        Category categorySystem = new Category(MAL_Syntax.CATEGORY_SYSTEM, allProvidedAssets);

        try {
            MALGenerator.generateMAlFile(Stream.of(categorySystem).collect(Collectors.toList()),
                    listOfAssociations,
                    Filenames.pathToAppserversMalFile);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void createAbstractClasses(){
        List< Field > fieldsAbstractHost = new ArrayList<>();
        AbstractHost abstractHost = new AbstractHost(AssetsConstants.HOST,
                AssetTagName.HOST,
                RelationshipCodes.OneToOne,
                AttacksDatabaseWorker.getAssetsAttackTags("host"),
                fieldsAbstractHost);
        allProvidedAssets.add(abstractHost);
    }

    private static void createAssetClasses() {
        //List<Field> fieldsAppserver = new ArrayList<>();
        List<Field> fieldsBackup = new ArrayList<>();
        List<Field> fieldsPostgre = new ArrayList<>();
        List<Field> fieldsRDB = new ArrayList<>();
        List<Field> fieldsOracle = new ArrayList<>();
        for (ConfdbAppServer confdbAppServer: confdbAppServers) {
            appserverProductRelation.put(confdbAppServer.getName().toLowerCase(), confdbAppServer.getApplications());
            iccpAppserverRelation.put(confdbAppServer.getName().toLowerCase(), confdbAppServer.getIccpopc_nodes().toLowerCase());

            if (confdbAppServer.getDbi().contains(ConfdbElementNames.ORA)) {
                //fieldsOracle.add(new Field(confdbAppServer.getDbi()));
                fieldsRDB.add(new Field(FieldsConstants.ORAs));
                appServersDatabaseRelation.put(confdbAppServer.getDbi().toLowerCase(), confdbAppServer.getName().toLowerCase());
                //
            }
            if (confdbAppServer.getDbi().contains(ConfdbElementNames.PG)) {
                //fieldsPostgre.add(new Field(confdbAppServer.getDbi()));
                fieldsRDB.add(new Field(FieldsConstants.PGs));
                appServersDatabaseRelation.put(confdbAppServer.getDbi().toLowerCase(), confdbAppServer.getName().toLowerCase());
                //
            }
            if (confdbAppServer.getDbi().contains(ConfdbElementNames.RDB)) {
                //fieldsRDB.add(new Field(confdbAppServer.getDbi()));
                fieldsRDB.add(new Field(FieldsConstants.RDBs));
                appServersDatabaseRelation.put(confdbAppServer.getDbi().toLowerCase(), confdbAppServer.getName().toLowerCase());
                //
            }
        }
        OracleDatabase oracleDatabase = new OracleDatabase(RelationshipCodes.OneToOne,
                fieldsOracle);
        allProvidedAssets.add(oracleDatabase);
        //
        PostgreDatabase postgreDatabase = new PostgreDatabase(RelationshipCodes.OneToOne,
                fieldsPostgre);
        allProvidedAssets.add(postgreDatabase);
        //
        RealTimeDatabase realTimeDatabase = new RealTimeDatabase(RelationshipCodes.OneToOne,
                fieldsRDB);
        allProvidedAssets.add(realTimeDatabase);
        //
        for (ConfdbBackupServer confdbBackupServer: confdbBackupServers){
            fieldsBackup.add(new Field(confdbBackupServer.getName()));
        }
        if (fieldsBackup.size() == 1) {
            List<Field> fieldsGeneralisedBackupServer = new ArrayList<>();
            fieldsGeneralisedBackupServer.add(new Field(FieldsConstants.BACKUP_SERVER));
            BackupServer backupServer = new BackupServer(AssetsConstants.BACKUP_SERVER,
                    RelationshipCodes.OneToOne,
                    fieldsGeneralisedBackupServer);
            allProvidedAssets.add(backupServer);
        }
        if (fieldsBackup.size() > 1) {
            List<Field> fieldsGeneralisedBackupServer = new ArrayList<>();
            fieldsGeneralisedBackupServer.add(new Field(FieldsConstants.BACKUP_SERVERS));
            BackupServer backupServer = new BackupServer(AssetsConstants.BACKUP_SERVER,
                    RelationshipCodes.OneToOne,
                    fieldsGeneralisedBackupServer);
            allProvidedAssets.add(backupServer);
        }
        //
        List<Field> fieldsIccp = new ArrayList<>();
        fieldsIccp.add(new Field(FieldsConstants.ICCPSERVER));
        IccpServer iccpServer = new IccpServer(AssetsConstants.ICCP_SERVER, RelationshipCodes.OneToOne, fieldsIccp);
        allProvidedAssets.add(iccpServer);
        //
        List<Field> fieldsDeServer = new ArrayList<>();
        for (ConfdbDeServer confdbDeServer: confdbDeServers) {
            analysisServerProductRelation.put(confdbDeServer.getName().toLowerCase(), confdbDeServer.getProducts());
            fieldsDeServer.add(new Field(confdbDeServer.getName()));
        }
        AnalysisServer dataEngineeringServer = new AnalysisServer(
                AssetsConstants.DATA_ENGINEERING_SERVER,
                RelationshipCodes.OneToOne,
                fieldsDeServer);
        allProvidedAssets.add(dataEngineeringServer);
        //
        List<Field> fieldsProduct = new ArrayList<>();
        for (ConfdbProduct confdbProduct: confdbProducts) {
            fieldsProduct.add(new Field(confdbProduct.getName()));
        }
        if (fieldsProduct.size() == 1) {
            List<Field> fieldsGeneralizedProduct = new ArrayList<>();
            fieldsGeneralizedProduct.add(new Field(FieldsConstants.PRODUCT));
            AbstractProduct abstractProduct = new AbstractProduct(AssetsConstants.PRODUCT,
                    RelationshipCodes.OneToOne,
                    fieldsGeneralizedProduct);
            allProvidedAssets.add(abstractProduct);
        }
        if (fieldsProduct.size() > 1) {
            List<Field> fieldsGeneralizedProduct = new ArrayList<>();
            fieldsGeneralizedProduct.add(new Field(FieldsConstants.PRODUCTS));
            AbstractProduct abstractProduct = new AbstractProduct(AssetsConstants.PRODUCT,
                    RelationshipCodes.OneToOne,
                    fieldsGeneralizedProduct);
            allProvidedAssets.add(abstractProduct);
        }
        //
        List< Field > fieldsAlarm = new ArrayList<>();
        fieldsAlarm.add(new Field(FieldsConstants.AUDIBLEALARM));
        AlarmSCADA alarm = new AlarmSCADA(AssetsConstants.ALARM, RelationshipCodes.OneToOne, fieldsAlarm);
        allProvidedAssets.add(alarm);
        //
        List<Field> fieldsMMI = new ArrayList<>();
        for (ConfdbMMI confdbMMI: confdbMMIs) {
            fieldsMMI.add(new Field(confdbMMI.getName()));
            appServersMMIRelation.put(confdbMMI.getName().toLowerCase(), confdbMMI.getAppl_servers().toLowerCase());
        }
        if (fieldsMMI.size()==1) {
            List<Field> fieldsGeneralisedMMI = new ArrayList<>();
            fieldsGeneralisedMMI.add(new Field(FieldsConstants.HMI));
            AbstractMMI abstractMMI = new AbstractMMI(AssetsConstants.HMI,
                    RelationshipCodes.OneToOne,
                    fieldsGeneralisedMMI);
            allProvidedAssets.add(abstractMMI);
        }
        if (fieldsMMI.size() > 1) {
            List<Field> fieldsGeneralisedMMI = new ArrayList<>();
            fieldsGeneralisedMMI.add(new Field(FieldsConstants.HMIS));
            AbstractMMI abstractMMI = new AbstractMMI(AssetsConstants.HMI,
                    RelationshipCodes.ManyToOne,
                    fieldsGeneralisedMMI);
            allProvidedAssets.add(abstractMMI);
        }

        List<Field> fieldsAntiviri = new ArrayList<>();
        for (ConfdbAntivirus confdbAntivirus : confdbAntiviri){
            for (ConfdbHost confdbHost : confdbHosts) {
                if (confdbAntivirus.getNodes().contains(confdbHost.getName())) {
                    fieldsAntiviri.add(new Field(confdbHost.getName()));
                }
            }
        }
        //
        if (fieldsAntiviri.size() == 1) {
            List<Field> fieldsGeneralisedAntiviri = new ArrayList<>();
            fieldsGeneralisedAntiviri.add(new Field(FieldsConstants.ANTIVIRUS));
            AntivirusServer antivirusServer = new AntivirusServer(RelationshipCodes.OneToOne, fieldsGeneralisedAntiviri);
            allProvidedAssets.add(antivirusServer);
            //
            Association associationAntivirus = new Association(
                    antivirusServer,
                    FieldsConstants.ANTIVIRUS,
                    abstractAppServer,
                    FieldsConstants.APPSERVERS,
                    RelationshipCodes.OneToMany,
                    AssociationsConstants.ANTIVIRUS_ACCESS);
            //List<String> potentialAttackNamesAntivirus = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.APPSERVER_ANTIVIRUS_ASSOCIATION);
            List<JSONObject> potentialAttackNamesAntivirus = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.APPSERVER_ANTIVIRUS_ASSOCIATION);
            associationAntivirus.setPotentialAttacks(potentialAttackNamesAntivirus);
            listOfAssociations.add(associationAntivirus);
        }
        if (fieldsAntiviri.size() > 1) {
            List<Field> fieldsGeneralisedAntiviri = new ArrayList<>();
            fieldsGeneralisedAntiviri.add(new Field(FieldsConstants.ANTIVIRUS));
            AntivirusServer antivirusServer = new AntivirusServer(RelationshipCodes.ManyToMany, fieldsGeneralisedAntiviri);
            allProvidedAssets.add(antivirusServer);
            //
            Association associationAntivirus = new Association(
                    antivirusServer,
                    FieldsConstants.ANTIVIRI,
                    abstractAppServer,
                    FieldsConstants.APPSERVERS,
                    RelationshipCodes.ManyToMany,
                    AssociationsConstants.ANTIVIRUS_ACCESS);
            //List<String> potentialAttackNamesAntivirus = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.APPSERVER_ANTIVIRUS_ASSOCIATION);
            List<JSONObject> potentialAttackNamesAntivirus = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.APPSERVER_ANTIVIRUS_ASSOCIATION);
            associationAntivirus.setPotentialAttacks(potentialAttackNamesAntivirus);
            listOfAssociations.add(associationAntivirus);
        }
        //
        List<Field> fieldsPCU = new ArrayList<>();
        for (ConfdbPCUServer confdbPCUServer : confdbPCUServers){
            List<Field> fieldsPCUAccount = new ArrayList<>();
            fieldsPCUAccount.add(new Field(FieldsConstants.PCU_ACCOUNT));
            if (!confdbPCUServer.getGroup().equals("")) {
                AdminAccount pcuAccount = new AdminAccount(confdbPCUServer.getGroup(),
                        AssetTagName.PCU_ACCOUNT,
                        RelationshipCodes.OneToOne,
                        "PCUAccount",
                        fieldsPCUAccount);
                allProvidedAssets.add(pcuAccount);
            }
            for (ConfdbHost confdbHost : confdbHosts) {
                if (confdbPCUServer.getNodes() != null) {
                    if (confdbPCUServer.getNodes().contains(confdbHost.getName())) {
                        fieldsPCU.add(new Field(confdbHost.getName()));
                    }
                }
            }
        }
        List<Field> fieldsGeneralizedPCU = new ArrayList<>();
        List<Field> fieldsGeneralisedRTU = new ArrayList<>();
        if (fieldsPCU.size() == 1) {
            fieldsGeneralizedPCU.add(new Field(FieldsConstants.PCU));
            ProcessCommunicationUnit processCommunicationUnit = new ProcessCommunicationUnit(RelationshipCodes.OneToOne, fieldsGeneralizedPCU);
            allProvidedAssets.add(processCommunicationUnit);
            //
            Association associationPcu = new Association(
                    abstractAppServer,
                    FieldsConstants.APPSERVER,
                    processCommunicationUnit,
                    FieldsConstants.PCU,
                    RelationshipCodes.OneToOne,
                    AssociationsConstants.PCU_ACCESS);
            //List<String> potentialAttackNamesPcu = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.PCU_APPSERVER_ASSOCIATION);
            List<JSONObject> potentialAttackNamesPcu = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.PCU_APPSERVER_ASSOCIATION);
            associationPcu.setPotentialAttacks(potentialAttackNamesPcu);
            listOfAssociations.add(associationPcu);
            //
            fieldsGeneralisedRTU.add(new Field(FieldsConstants.RTU));
            RemoteTerminalUnit remoteTerminalUnit = new RemoteTerminalUnit(RelationshipCodes.OneToOne, fieldsGeneralisedRTU);
            allProvidedAssets.add(remoteTerminalUnit);
            //
            Association associationRtu = new Association(
                    remoteTerminalUnit,
                    FieldsConstants.RTU,
                    processCommunicationUnit,
                    FieldsConstants.PCU,
                    RelationshipCodes.OneToOne,
                    AssociationsConstants.PCU_ACCESS);
            //List<String> potentialAttackNamesPcu = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.PCU_APPSERVER_ASSOCIATION);
            List<JSONObject> potentialAttackNamesRtu = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.PCU_RTU_ASSOCIATION);
            associationRtu.setPotentialAttacks(potentialAttackNamesRtu);
            listOfAssociations.add(associationRtu);
        }
        if (fieldsPCU.size() > 1) {
            fieldsGeneralizedPCU.add(new Field(FieldsConstants.PCUS));
            ProcessCommunicationUnit processCommunicationUnit = new ProcessCommunicationUnit(RelationshipCodes.OneToOne, fieldsGeneralizedPCU);
            allProvidedAssets.add(processCommunicationUnit);
            //
            Association associationPcu = new Association(
                    abstractAppServer,
                    FieldsConstants.APPSERVERS,
                    processCommunicationUnit,
                    FieldsConstants.PCUS,
                    RelationshipCodes.ManyToOne,
                    AssociationsConstants.RTU_ACCESS);
            //List<String> potentialAttackNamesPcu = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.PCU_APPSERVER_ASSOCIATION);
            List<JSONObject> potentialAttackNamesPcu = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.PCU_APPSERVER_ASSOCIATION);
            associationPcu.setPotentialAttacks(potentialAttackNamesPcu);
            listOfAssociations.add(associationPcu);
            //
            fieldsGeneralisedRTU.add(new Field(FieldsConstants.RTUS));
            RemoteTerminalUnit remoteTerminalUnit = new RemoteTerminalUnit(RelationshipCodes.OneToOne, fieldsGeneralisedRTU);
            allProvidedAssets.add(remoteTerminalUnit);
            //
            Association associationRtu = new Association(
                    remoteTerminalUnit,
                    FieldsConstants.RTUS,
                    processCommunicationUnit,
                    FieldsConstants.PCUS,
                    RelationshipCodes.ManyToMany,
                    AssociationsConstants.RTU_ACCESS);
            //List<String> potentialAttackNamesPcu = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.PCU_APPSERVER_ASSOCIATION);
            List<JSONObject> potentialAttackNamesRtu = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.PCU_RTU_ASSOCIATION);
            associationRtu.setPotentialAttacks(potentialAttackNamesRtu);
            listOfAssociations.add(associationRtu);
        }
    }

    //////////////////////////////////////////////////////////////////
    private static void findAssetsConnectionsFromConfdb() {
        for (AssetSCADA assetFirst : allProvidedAssets) {
            for (AssetSCADA assetSecond : allProvidedAssets) {
                if (!assetFirst.equals(assetSecond)) {
                    if (assetFirst.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_APP_SERVER)
                            && assetSecond.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_MMI)) {
                        AbstractAppServer assetAppServer = (AbstractAppServer) assetFirst;
                        AbstractMMI assetMMI = (AbstractMMI) assetSecond;
                        /*for (Field fieldAppServer : assetAppServer.getFields()) {
                            for (Field fieldMMI : assetMMI.getFields()) {
                                String mmiFieldName = fieldMMI.getName();
                                String relatedAppServerForMMI = appServersMMIRelation.get(mmiFieldName);
                                if (relatedAppServerForMMI != null) {
                                    if (relatedAppServerForMMI.contains(fieldAppServer.getName())) {
                                        String appserverFieldName = fieldAppServer.getName();
                                        Association newAssociation = new Association(assetMMI,
                                                mmiFieldName,
                                                assetAppServer,
                                                appserverFieldName,
                                                RelationshipCodes.OneToOne,
                                                assetAppServer.getName() + "Access");
                                        List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks("mmi-appserver-association");
                                        newAssociation.setPotentialAttacks(potentialAttackNames);
                                        listOfAssociations.add(newAssociation);
                                        break;
                                    }
                                }
                            }
                        }*/
                        if (appServersMMIRelation.size() == 1) {
                            Association newAssociation = new Association(assetMMI,
                                    FieldsConstants.HMI,
                                    assetAppServer,
                                    FieldsConstants.APPSERVER,
                                    RelationshipCodes.OneToOne,
                                    assetAppServer.getName() + "Access");
                            //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.MMI_APPSERVER_ASSOCIATION);
                            List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.MMI_APPSERVER_ASSOCIATION);
                            newAssociation.setPotentialAttacks(potentialAttackNames);
                            listOfAssociations.add(newAssociation);
                            break;
                        }
                        if (appServersMMIRelation.size() > 1) {
                            Association newAssociation = new Association(assetMMI,
                                    FieldsConstants.HMIS,
                                    assetAppServer,
                                    FieldsConstants.APPSERVERS,
                                    RelationshipCodes.ManyToMany,
                                    assetAppServer.getName() + "Access");
                            //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.MMI_APPSERVER_ASSOCIATION);
                            List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.MMI_APPSERVER_ASSOCIATION);
                            newAssociation.setPotentialAttacks(potentialAttackNames);
                            listOfAssociations.add(newAssociation);
                            break;
                        }

                    }
                    if (assetFirst.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ICCP_SERVER)
                            && assetSecond.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_APP_SERVER)) {
                        IccpServer iccpServer = (IccpServer) assetFirst;
                        AbstractAppServer assetAppServer = (AbstractAppServer) assetSecond;
                        for (Field fieldIccp : iccpServer.getFields()) {
                            //for (Field fieldAppServer : assetAppServer.getFields()) {
                            for (Field fieldAppServer : fieldsAppserver) {
                                String appserverFieldName = fieldAppServer.getName();
                                String relatedIccpForAccount = iccpAppserverRelation.get(appserverFieldName);
                                /*if (relatedIccpForAccount != null) {
                                    if (relatedIccpForAccount.contains(fieldAppServer.getName())) {
                                        String hostFieldname = fieldHost.getName();
                                        Association newAssociation = new Association(assetAppServer,
                                                appserverFieldName,
                                                assetHost,
                                                hostFieldname,
                                                RelationshipCodes.OneToOne,
                                                assetAppServer.getName() + AssociationsConstants.ICCP_ACCESS);
                                        List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.APPSERVER_ICCP_ASSOCIATION);
                                        newAssociation.setPotentialAttacks(potentialAttackNames);
                                        listOfAssociations.add(newAssociation);
                                        break;
                                    }
                                }*/
                                if (relatedIccpForAccount != null) {
                                    Association newAssociation = new Association(
                                            assetAppServer,
                                            FieldsConstants.APPSERVERS,
                                            iccpServer,
                                            fieldIccp.getName(),
                                            RelationshipCodes.ManyToMany,
                                            assetAppServer.getName() + "Access");
                                    //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.APPSERVER_ICCP_ASSOCIATION);
                                    List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.APPSERVER_ICCP_ASSOCIATION);
                                    newAssociation.setPotentialAttacks(potentialAttackNames);
                                    listOfAssociations.add(newAssociation);
                                    break;
                                }
                            }
                        }

                    }
                    if (assetFirst.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ANALYSIS_SERVER)
                            && assetSecond.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_PRODUCT)) {
                        AnalysisServer analysisServer = (AnalysisServer) assetFirst;
                        AbstractProduct assetProduct = (AbstractProduct) assetSecond;
                        for (Field fieldProduct : assetProduct.getFields()) {
                            for (Field fieldAnalysisServer : analysisServer.getFields()) {
                                String analysisServerFieldName = fieldAnalysisServer.getName();
                                List<String> relatedProductsPerAnalysisServer = analysisServerProductRelation.get(analysisServerFieldName.toLowerCase());
                                if (relatedProductsPerAnalysisServer != null) {
                                    String productFieldName = fieldProduct.getName();
                                    if (relatedProductsPerAnalysisServer.contains(productFieldName.toUpperCase())) {
                                        Association newAssociation = new Association(analysisServer,
                                                analysisServerFieldName,
                                                assetProduct,
                                                productFieldName,
                                                RelationshipCodes.OneToOne,
                                                productFieldName + "Access");
                                        //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.ANALYSISSERVER_PRODUCT_ASSOCIATION);
                                        List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.ANALYSISSERVER_PRODUCT_ASSOCIATION);
                                        newAssociation.setPotentialAttacks(potentialAttackNames);
                                        listOfAssociations.add(newAssociation);
                                        break;
                                    }
                                }
                            }
                        }

                    }
                    if (assetFirst.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_PRODUCT)
                            && assetSecond.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_APP_SERVER)) {
                        AbstractProduct assetProduct = (AbstractProduct) assetFirst;
                        AbstractAppServer assetAppServer = (AbstractAppServer) assetSecond;
                        for (Field fieldProduct : assetProduct.getFields()) {
                            for (Field fieldAppServer : assetAppServer.getFields()) {
                                /*String appserverFieldName = fieldAppServer.getName();
                                List<String> relatedProductsPerAppserver = appserverProductRelation.get(appserverFieldName);
                                if (relatedProductsPerAppserver != null) {
                                    String productFieldName = fieldProduct.getName();
                                    if (relatedProductsPerAppserver.contains(productFieldName.toUpperCase())) {
                                        Association newAssociation = new Association(assetAppServer,
                                                appserverFieldName,
                                                assetProduct,
                                                productFieldName,
                                                RelationshipCodes.OneToOne,
                                                productFieldName + "Access");
                                        //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.APPSERVER_PRODUCT_ASSOCIATION);
                                        List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.APPSERVER_PRODUCT_ASSOCIATION);
                                        newAssociation.setPotentialAttacks(potentialAttackNames);
                                        listOfAssociations.add(newAssociation);
                                        break;
                                    }
                                }*/
                                Association newAssociation = new Association(assetAppServer,
                                        fieldAppServer.getName(),
                                        assetProduct,
                                        fieldProduct.getName(),
                                        RelationshipCodes.OneToOne,
                                        AssociationsConstants.PRODUCT_ACCESS);
                                //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.APPSERVER_PRODUCT_ASSOCIATION);
                                List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.APPSERVER_PRODUCT_ASSOCIATION);
                                newAssociation.setPotentialAttacks(potentialAttackNames);
                                listOfAssociations.add(newAssociation);
                                break;
                            }
                        }

                    }
                    if (assetFirst.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ALARM)
                            && assetSecond.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_MMI)) {
                        AlarmSCADA alarm = (AlarmSCADA) assetFirst;
                        AbstractMMI abstractMMI = (AbstractMMI) assetSecond;
                        for (Field alarmField : alarm.getFields()) {
                            for (Field mmiField : abstractMMI.getFields()) {
                                Association newAssociation = new Association(alarm,
                                        MALConventionChecker.checkForMalConvention(alarmField.getName()),
                                        abstractMMI,
                                        mmiField.getName(),
                                        RelationshipCodes.ManyToOne,
                                        AssociationsConstants.ALARM_ACCESS_FROM_MMI);
                                //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.ALARM_MMI_ASSOCIATION);
                                List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.ALARM_MMI_ASSOCIATION);
                                newAssociation.setPotentialAttacks(potentialAttackNames);
                                listOfAssociations.add(newAssociation);
                            }
                        }
                    }
                    if (assetFirst.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ALARM)
                            && assetSecond.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_APP_SERVER)) {
                        AlarmSCADA alarm = (AlarmSCADA) assetFirst;
                        AbstractAppServer abstractAppServer = (AbstractAppServer) assetSecond;
                        for (Field appServerField : abstractAppServer.getFields()) {
                            for (Field alarmField : alarm.getFields()) {
                                Association newAssociation = new Association(alarm,
                                        MALConventionChecker.checkForMalConvention(alarmField.getName()),
                                        abstractAppServer,
                                        appServerField.getName(),
                                        RelationshipCodes.ManyToOne,
                                        AssociationsConstants.ALARM_ACCESS_FROM_APPSERVER);
                                //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.ALARM_APPSERVER_ASSOCIATION);
                                List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.ALARM_APPSERVER_ASSOCIATION);
                                newAssociation.setPotentialAttacks(potentialAttackNames);
                                listOfAssociations.add(newAssociation);
                            }
                        }
                    }
                    if (assetFirst.getClass().getSuperclass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_DATABASE)
                            && assetSecond.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_APP_SERVER)) {
                        AbstractDatabase abstractDatabase = (AbstractDatabase) assetFirst;
                        AbstractAppServer assetAppserver = (AbstractAppServer) assetSecond;
                        /*for (Field fieldDatabase : abstractDatabase.getFields()) {
                            for (Field fieldAppserver : assetAppserver.getFields()) {
                                String databaseFieldName = fieldDatabase.getName();
                                String relatedAppserverPerDB = appServersDatabaseRelation.get(databaseFieldName);
                                if (relatedAppserverPerDB != null) {
                                    String appserverFieldName = fieldAppserver.getName();
                                    if (relatedAppserverPerDB.contains(appserverFieldName)) {
                                        Association newAssociation = new Association(assetAppserver,
                                                appserverFieldName,
                                                abstractDatabase,
                                                databaseFieldName,
                                                RelationshipCodes.OneToOne,
                                                appserverFieldName + "Access");
                                        List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks("appserver-database-association");
                                        newAssociation.setPotentialAttacks(potentialAttackNames);
                                        listOfAssociations.add(newAssociation);
                                        break;
                                    }
                                }
                            }
                        }*/

                        if(appServersDatabaseRelation.size()==1) {
                            Association newAssociation = new Association(
                                    abstractDatabase,
                                    abstractDatabase.getName()+"s",
                                    assetAppserver,
                                    FieldsConstants.APPSERVER,
                                    RelationshipCodes.OneToOne,
                                    AssociationsConstants.DATABASE_ACCESS);
                            //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks("appserver_" + abstractDatabase.getName() + "_association");
                            List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation("appserver-" + abstractDatabase.getName() + "-association");
                            //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.APPSERVER_DATABASE_ASSOCIATION);
                            newAssociation.setPotentialAttacks(potentialAttackNames);
                            listOfAssociations.add(newAssociation);
                            break;
                        }
                        if(appServersDatabaseRelation.size()>1)  {
                            Association newAssociation = new Association(abstractDatabase,
                                    abstractDatabase.getName()+"s",
                                    assetAppserver,
                                    FieldsConstants.APPSERVERS,
                                    RelationshipCodes.ManyToMany,
                                    AssociationsConstants.DATABASE_ACCESS);
                            //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks("appserver_" + abstractDatabase.getName() + "_association");
                            List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation("appserver-" + abstractDatabase.getName() + "-association");
                            //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.APPSERVER_DATABASE_ASSOCIATION);
                            newAssociation.setPotentialAttacks(potentialAttackNames);
                            listOfAssociations.add(newAssociation);
                            break;
                        }
                    }
                    if (assetFirst.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_APP_SERVER)
                            && assetSecond.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_BACKUP_SERVER)) {
                        AbstractAppServer assetAppserver = (AbstractAppServer) assetFirst;
                        BackupServer backupServer = (BackupServer) assetSecond;
                        for (Field fieldBackupServer: backupServer.getFields()){
                            for (Field fieldAppServer: assetAppserver.getFields()){
                                Association newAssociation = new Association(
                                        assetAppserver,
                                        fieldAppServer.getName(),
                                        backupServer,
                                        fieldBackupServer.getName(),
                                        RelationshipCodes.OneToOne,
                                        AssociationsConstants.BACKUP_SERVER_ACCESS);
                                //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks("appserver_" + abstractDatabase.getName() + "_association");
                                List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.BACKUPSERVER_APPSERVER_ASSOCIATION);
                                //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.APPSERVER_DATABASE_ASSOCIATION);
                                newAssociation.setPotentialAttacks(potentialAttackNames);
                                listOfAssociations.add(newAssociation);
                                break;
                            }
                        }
                    }
                    if (assetFirst.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ACTIVE_DIRECTORY)
                            && (assetSecond.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_MMI)
                            || assetSecond.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_PCU))) {
                        for (Field fieldAD: assetFirst.getFields()){
                            for (Field fieldSecond: assetSecond.getFields()){
                                Association newAssociation = new Association(
                                        assetFirst,
                                        fieldAD.getName(),
                                        assetSecond,
                                        fieldSecond.getName(),
                                        RelationshipCodes.OneToOne,
                                        AssociationsConstants.ACTIVE_DIRECTORY_ACCESS);
                                List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.SERVER_ACTIVEDIRECTORY_ASSOCIATION);
                                newAssociation.setPotentialAttacks(potentialAttackNames);
                                listOfAssociations.add(newAssociation);
                                break;
                            }
                        }

                    }
                    if (assetFirst.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ADMIN_ACCOUNT)
                            && assetSecond.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_PCU)){
                        for (Field fieldFirst: assetFirst.getFields()){
                            for (Field fieldSecond: assetSecond.getFields()){
                                Association newAssociation = new Association(
                                        assetFirst,
                                        fieldFirst.getName(),
                                        assetSecond,
                                        fieldSecond.getName(),
                                        RelationshipCodes.OneToOne,
                                        AssociationsConstants.PCU_ACCESS);
                                List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.PCU_ACCOUNT_ASSOCIATION);
                                newAssociation.setPotentialAttacks(potentialAttackNames);
                                listOfAssociations.add(newAssociation);
                                break;
                            }
                        }

                    }
                }
            }
        }
    }
}
