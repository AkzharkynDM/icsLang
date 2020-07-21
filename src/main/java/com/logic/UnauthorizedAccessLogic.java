package com.logic;
import com.constants.*;
import com.jsonworkers.AttacksDatabaseWorker;
import com.helpers.*;
import com.pojoJSONParsing.*;
import com.pojoMAL.*;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//Unauthorized changes to instructions, commands, or alarm thresholds, which could damage, disable, or shut down equipment, create environmental impacts, and/or endanger human life.
//https://collaborate.mitre.org/attackics/index.php/Overview
//This will look at all accounts that are not written in Confdb file
public class UnauthorizedAccessLogic extends BaseLogic{
    private static List<Field> fieldsAccounts = new ArrayList<>();

    public static void createExtendedMALFromConfdb(){
        initAll();
        addBaseAssets();
        addAssets();
        addDefaultAccounts();
        findAssociations();
        buildAttackTree();
        attachDefaultProtection();

        allProvidedAssets.removeAll(allProvidedAssetsNotIncludedAccounts);
        Category categorySecurity = new Category(MAL_Syntax.CATEGORY_SECURITY, allProvidedAssets);

        try {
            MALGenerator.generateMAlFile(Stream.of(categorySecurity).collect(Collectors.toList()),
                    listOfAssociations,
                    Filenames.pathToAccountsMalFile);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void addAssets() {
        String name = "";
        for (ConfdbUserGroup confdbUserGroup : confdbUserGroups) {
            for (ConfdbUserAccount confdbUserAccount : confdbUserAccounts) {
                if (confdbUserAccount.getPrimaryGroup().contains(confdbUserGroup.getLabel())) {
                    name = confdbUserGroup.getLabel();
                    fieldsAccounts.add(new Field(confdbUserAccount.getName()));
                }
            }
        }
        //
        if (fieldsAccounts.size() == 1) {
            List<Field> fieldsGeneralisedAccounts = new ArrayList<>();
            fieldsGeneralisedAccounts.add(new Field(FieldsConstants.APPSERVER_ACCOUNTS));
            AdminAccount adminAccount = new AdminAccount(MALConventionChecker.checkForMalConventionAssetName(name) + "Account",
                    AssetTagName.ACCOUNT,
                    RelationshipCodes.OneToOne,
                    "Account",
                    fieldsGeneralisedAccounts);
            //abstractAccount.addField(new Field(confdbUserAccount.getName()));
            allProvidedAssets.add(adminAccount);
        }
        if (fieldsAccounts.size() > 1) {
            List<Field> fieldsGeneralisedAccounts = new ArrayList<>();
            fieldsGeneralisedAccounts.add(new Field(FieldsConstants.APPSERVER_ACCOUNTS));
            AdminAccount adminAccount = new AdminAccount(MALConventionChecker.checkForMalConventionAssetName(name) + "Account",
                    AssetTagName.ACCOUNT,
                    RelationshipCodes.ManyToOne,
                    "Account",
                    fieldsGeneralisedAccounts);
            //abstractAccount.addField(new Field(confdbUserAccount.getName()));
            allProvidedAssets.add(adminAccount);
        }

        List<Field> fieldsMMI = new ArrayList<>();
        for (ConfdbMMI confdbMMI : confdbMMIs) {
            fieldsMMI.add(new Field(confdbMMI.getName()));
            accountMMIRelation.put(confdbMMI.getName().toLowerCase(), confdbMMI.getAccount().toLowerCase());
        }

        if (fieldsMMI.size() == 1) {
            List<Field> fieldsGeneralisedMMI = new ArrayList<>();
            fieldsGeneralisedMMI.add(new Field(FieldsConstants.HUMAN_MACHINE_INTERFACE));
            AbstractMMI abstractMMI = new AbstractMMI(AssetsConstants.HMI,
                    RelationshipCodes.OneToOne,
                    fieldsGeneralisedMMI);
            allProvidedAssets.add(abstractMMI);
            allProvidedAssetsNotIncludedAccounts.add(abstractMMI);
        }
        if (fieldsMMI.size() > 1) {
            List<Field> fieldsGeneralisedMMI = new ArrayList<>();
            fieldsGeneralisedMMI.add(new Field(FieldsConstants.HUMAN_MACHINE_INTERFACES));
            AbstractMMI abstractMMI = new AbstractMMI(AssetsConstants.HMI,
                    RelationshipCodes.ManyToMany,
                    fieldsGeneralisedMMI);
            allProvidedAssets.add(abstractMMI);
            allProvidedAssetsNotIncludedAccounts.add(abstractMMI);
        }
    }

    private static void addDefaultAccounts() {
        List<Field> fieldsAccountDe = new ArrayList<>();
        fieldsAccountDe.add(new Field(AssetsConstants.fieldNameDeAccount));
        List<Field> fieldsAccountPse = new ArrayList<>();
        fieldsAccountPse.add(new Field(AssetsConstants.fieldNamePSEAccount));
        List<Field> fieldsAccountADLinux = new ArrayList<>();
        fieldsAccountADLinux.add(new Field(AssetsConstants.fieldNameADLinuxAccount));
        List<Field> fieldsAccountsADWindows = new ArrayList<>();
        fieldsAccountsADWindows.add(new Field(AssetsConstants.fieldNameADWindowsAccount));

        ///////////////////////////////////////////////
        ServiceAccount deAccount = new ServiceAccount(AssetsConstants.DE_ACCOUNT, AssetTagName.DE_ACCOUNT, RelationshipCodes.OneToOne, "DEAccount", fieldsAccountDe);
        allProvidedAssets.add(deAccount);

        List<Field> fieldsDeServer = new ArrayList<>();
        for (ConfdbDeServer confdbDeServer : confdbDeServers) {
            fieldsDeServer.add(new Field(confdbDeServer.getName()));
        }
        if (fieldsDeServer.size() ==1){
            List<Field> fieldsGeneralisedDeServer = new ArrayList<>();
            fieldsGeneralisedDeServer.add(new Field(FieldsConstants.DATAENG));
            AnalysisServer dataEngineeringServer = new AnalysisServer(
                    AssetsConstants.DATA_ENGINEERING_SERVER,
                    RelationshipCodes.OneToOne,
                    fieldsGeneralisedDeServer);
            allProvidedAssets.add(dataEngineeringServer); //TODO
            allProvidedAssetsNotIncludedAccounts.add(dataEngineeringServer);
            //
            //Add association between De Server and de400 account
            Association newAssociation = new Association(deAccount,
                    MALConventionChecker.checkForMalConvention(AssetsConstants.fieldNameDeAccount),
                    dataEngineeringServer,
                    //MALConventionChecker.checkForMalConvention(fieldDeServer.getName()),
                    FieldsConstants.DATAENG,
                    RelationshipCodes.OneToOne,
                    AssociationsConstants.DATA_ENGINEERING_ACCESS);
            //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.DE_ACCOUNT_ASSOCIATION);
            List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.DE_ACCOUNT_ASSOCIATION);
            newAssociation.setPotentialAttacks(potentialAttackNames);
            listOfAssociations.add(newAssociation);
        }
        if (fieldsDeServer.size() > 1){
            List<Field> fieldsGeneralisedDeServer = new ArrayList<>();
            fieldsGeneralisedDeServer.add(new Field(FieldsConstants.DATAENGS));
            AnalysisServer dataEngineeringServer = new AnalysisServer(
                    AssetsConstants.DATA_ENGINEERING_SERVER,
                    RelationshipCodes.ManyToOne,
                    fieldsGeneralisedDeServer);
            allProvidedAssets.add(dataEngineeringServer); //TODO
            allProvidedAssetsNotIncludedAccounts.add(dataEngineeringServer); //TODO
            //
            //Add association between De Server and de400 account
            Association newAssociation = new Association(deAccount,
                    MALConventionChecker.checkForMalConvention(AssetsConstants.fieldNameDeAccount),
                    dataEngineeringServer,
                    //MALConventionChecker.checkForMalConvention(fieldDeServer.getName()),
                    FieldsConstants.DATAENGS,
                    RelationshipCodes.ManyToOne,
                    AssociationsConstants.DATA_ENGINEERING_ACCESS);
            //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.DE_ACCOUNT_ASSOCIATION);
            List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.DE_ACCOUNT_ASSOCIATION);
            newAssociation.setPotentialAttacks(potentialAttackNames);
            listOfAssociations.add(newAssociation);
        }

        ///////////////////////////////////////////////

        ///////////////////////////////////////////////
        UserAccount adLinuxAccount = new UserAccount(AssetsConstants.AD_LINUX_ACCOUNT,
                AssetTagName.AD_LINUX_ACCOUNT,
                RelationshipCodes.OneToOne,
                "ADLinuxAccount",
                fieldsAccountADLinux);
        allProvidedAssets.add(adLinuxAccount);
        AdminAccount adWindowsAccount = new AdminAccount(AssetsConstants.AD_WINDOWS_ACCOUNT,
                AssetTagName.AD_WINDOWS_ACCOUNT,
                RelationshipCodes.OneToOne,
                "ADWindowsAccount",
                fieldsAccountsADWindows);
        allProvidedAssets.add(adWindowsAccount);
        //
        if (fieldsAD.size() == 1) {
            for (Field fieldAD: activeDirectory.getFields()) {
                //Add association between AD server and sadmin account for linux
                Association newLinuxAssociation = new Association(adLinuxAccount,
                        MALConventionChecker.checkForMalConvention(AssetsConstants.fieldNameADLinuxAccount),
                        activeDirectory,
                        //"linux" + MALConventionChecker.checkForMalConvention(fieldAD.getName()),
                        fieldAD.getName(),
                        RelationshipCodes.OneToOne,
                        AssociationsConstants.LINUX_ACTIVE_DIRECTORY_ACCESS);
                //List<String> potentialAttackNamesLinux = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.AD_ACCOUNT_ASSOCIATION);
                List<JSONObject> potentialAttackNamesLinux = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.AD_ACCOUNT_ASSOCIATION);
                newLinuxAssociation.setPotentialAttacks(potentialAttackNamesLinux);
                listOfAssociations.add(newLinuxAssociation);
                //Add association between AD server and Administator account for Windows
                Association newWindowsAssociation = new Association(adWindowsAccount,
                        MALConventionChecker.checkForMalConvention(AssetsConstants.fieldNameADWindowsAccount),
                        activeDirectory,
                        //"windows" + MALConventionChecker.checkForMalConvention(fieldAD.getName()),
                        fieldAD.getName(),
                        RelationshipCodes.OneToOne,
                        AssociationsConstants.WINDOWS_ACTIVE_DIRECTORY_ACCESS);
                //List<String> potentialAttackNamesWindows = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.AD_ACCOUNT_ASSOCIATION);
                List<JSONObject> potentialAttackNamesWindows = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.AD_ACCOUNT_ASSOCIATION);
                newWindowsAssociation.setPotentialAttacks(potentialAttackNamesWindows);
                listOfAssociations.add(newWindowsAssociation);
            }
        }
        if (activeDirectory.getFields().size() > 1) {
            //Add association between AD server and sadmin account for linux
            for (Field fieldAD: activeDirectory.getFields()) {
                Association newLinuxAssociation = new Association(adLinuxAccount,
                        MALConventionChecker.checkForMalConvention(AssetsConstants.fieldNameADLinuxAccount),
                        activeDirectory,
                        fieldAD.getName(),
                        RelationshipCodes.ManyToOne,
                        AssociationsConstants.ACTIVE_DIRECTORY_ACCESS);
                //List<String> potentialAttackNamesLinux = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.AD_ACCOUNT_ASSOCIATION);
                List<JSONObject> potentialAttackNamesLinux = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.AD_ACCOUNT_ASSOCIATION);
                newLinuxAssociation.setPotentialAttacks(potentialAttackNamesLinux);
                listOfAssociations.add(newLinuxAssociation);
                //Add association between AD server and Administator account for Windows

                Association newWindowsAssociation = new Association(adWindowsAccount,
                        MALConventionChecker.checkForMalConvention(AssetsConstants.fieldNameADWindowsAccount),
                        activeDirectory,
                        fieldAD.getName(),
                        RelationshipCodes.ManyToOne,
                        AssociationsConstants.ACTIVE_DIRECTORY_ACCESS);
                //List<String> potentialAttackNamesWindows = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.AD_ACCOUNT_ASSOCIATION);
                List<JSONObject> potentialAttackNamesWindows = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.AD_ACCOUNT_ASSOCIATION);
                newWindowsAssociation.setPotentialAttacks(potentialAttackNamesWindows);
                listOfAssociations.add(newWindowsAssociation);
            }
        }

        List<Field> fieldsPse = new ArrayList<>();
        for (ConfdbPseServer confdbPseServer : confdbPseServers) {
            fieldsPse.add(new Field(confdbPseServer.getName()));
        }
        if (fieldsPse.size() == 1) {
            ServiceAccount pseAccount = new ServiceAccount(AssetsConstants.PSE_ACCOUNT, AssetTagName.PSE_ACCOUNT, RelationshipCodes.OneToOne, "PSEAccount", fieldsAccountPse);
            allProvidedAssets.add(pseAccount);
            ///////////////////////////////////////////////
            List<Field> fieldsGeneralisedPse = new ArrayList<>();
            fieldsGeneralisedPse.add(new Field(FieldsConstants.PSE_SERVER));
            AnalysisServer analysisServerPse = new AnalysisServer(
                    AssetsConstants.PSE_SERVER,
                    RelationshipCodes.OneToOne,
                    fieldsGeneralisedPse);
            allProvidedAssets.add(analysisServerPse);

            //Add association between PSE server and pse account
            Association newAssociation = new Association(pseAccount,
                    MALConventionChecker.checkForMalConvention(AssetsConstants.fieldNamePSEAccount),
                    analysisServerPse,
                    //MALConventionChecker.checkForMalConvention(pseField.getName()),
                    FieldsConstants.PSE_SERVER,
                    RelationshipCodes.OneToOne,
                    AssociationsConstants.PSE_ACCESS);
            //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.PSE_ACCOUNT_ASSOCIATION);
            List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.PSE_ACCOUNT_ASSOCIATION);
            newAssociation.setPotentialAttacks(potentialAttackNames);
            listOfAssociations.add(newAssociation);
        }
        if (fieldsPse.size() > 1) {
            AdminAccount pseAccount = new AdminAccount(AssetsConstants.PSE_ACCOUNT, AssetTagName.PSE_ACCOUNT, RelationshipCodes.OneToOne, "PSEAccount", fieldsAccountPse);
            allProvidedAssets.add(pseAccount);
            ///////////////////////////////////////////////
            List<Field> fieldsGeneralisedPse = new ArrayList<>();
            fieldsGeneralisedPse.add(new Field(FieldsConstants.PSE_SERVERS));
            AnalysisServer analysisServerPse = new AnalysisServer(
                    AssetsConstants.PSE_SERVER,
                    RelationshipCodes.ManyToMany,
                    fieldsGeneralisedPse);
            allProvidedAssets.add(analysisServerPse);

            //Add association between PSE server and pse account
            Association newAssociation = new Association(pseAccount,
                    MALConventionChecker.checkForMalConvention(AssetsConstants.fieldNamePSEAccount),
                    analysisServerPse,
                    //MALConventionChecker.checkForMalConvention(pseField.getName()),
                    FieldsConstants.PSE_SERVERS,
                    RelationshipCodes.ManyToMany,
                    AssociationsConstants.PSE_ACCESS);
            //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.PSE_ACCOUNT_ASSOCIATION);
            List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.PSE_ACCOUNT_ASSOCIATION);
            newAssociation.setPotentialAttacks(potentialAttackNames);
            listOfAssociations.add(newAssociation);
        }
    }

    private static void findAssociations() {
        for (AssetSCADA assetFirst : allProvidedAssets) {
            for (AssetSCADA assetSecond : allProvidedAssets) {
                if (!assetFirst.equals(assetSecond)) {
                    if (assetFirst.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_MMI)
                            && assetSecond.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ADMIN_ACCOUNT)) {
                        AdminAccount assetUser = (AdminAccount) assetSecond;
                        AbstractMMI assetMMI = (AbstractMMI) assetFirst;
                        /*for (Field fieldMMI : assetMMI.getFields()) {
                        for (Field fieldUser : assetUser.getFields()) {
                            String mmiFieldName = fieldMMI.getName();
                            String relatedUserForMMI = accountMMIRelation.get(mmiFieldName);
                            if (relatedUserForMMI != null) {
                            if (relatedUserForMMI.contains(fieldUser.getName())) {
                            String userFieldName = fieldUser.getName();
                                        Association newAssociation = new Association(assetUser,
                                                userFieldName,
                                                assetMMI,
                                                mmiFieldName,
                                                RelationshipCodes.OneToOne,
                                                mmiFieldName + "Access");
                                        List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.USER_MMI_ASSOCIATION);
                                        newAssociation.setPotentialAttacks(potentialAttackNames);
                                        listOfAssociations.add(newAssociation);*/

                        Association newAssociation = new Association(assetUser,
                                FieldsConstants.APPSERVER_ACCOUNTS,
                                assetMMI,
                                FieldsConstants.HUMAN_MACHINE_INTERFACES,
                                RelationshipCodes.ManyToOne,
                                AssociationsConstants.MMI_ACCESS);
                        //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.USER_MMI_ASSOCIATION);
                        List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.USER_MMI_ASSOCIATION);
                        newAssociation.setPotentialAttacks(potentialAttackNames);
                        listOfAssociations.add(newAssociation);
                        break;
                    }

                    if (assetFirst.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_APP_SERVER)
                            && assetSecond.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ADMIN_ACCOUNT)) {
                        AbstractAppServer assetAppServer = (AbstractAppServer) assetFirst;
                        AdminAccount assetUser = (AdminAccount) assetSecond;
                        String relatedAccountPerAppserver = null;
                        //for (Field fieldUser : assetUser.getFields()) {
                            for (Field fieldUser : fieldsAccounts){
                            //for (Field fieldAppServer : assetAppServer.getFields()) {
                            for (Field fieldAppServer: fieldsAppserver){
                                String fieldUserName = fieldUser.getName();
                                String fieldAppServerName = fieldAppServer.getName().toLowerCase();
                                relatedAccountPerAppserver = accountAppserverRelation.get(fieldAppServerName);
                                /*if (relatedAccountPerAppserver != null) {
                                    if (relatedAccountPerAppserver.contains(fieldUserName)) {
                                        //String appserverFieldName = fieldAppServer.getName();
                                        Association newAssociation = new Association(assetAppServer,
                                                //appserverFieldName,
                                                "appservers",
                                                assetUser,
                                                //fieldUserName,
                                                "appserverAccounts",
                                                //RelationshipCodes.OneToOne,
                                                RelationshipCodes.ManyToMany,
                                                assetAppServer.getName() + "Access");
                                        List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.USER_APPSERVER_ASSOCIATION);
                                        newAssociation.setPotentialAttacks(potentialAttackNames);
                                        listOfAssociations.add(newAssociation);
                                        break;
                                    }
                                }*/
                            }
                        }
                            //
                        if (relatedAccountPerAppserver != null){
                            Association newAssociation = new Association(assetAppServer,
                                    //appserverFieldName,
                                    FieldsConstants.APPSERVERS,
                                    assetUser,
                                    //fieldUserName,
                                    FieldsConstants.APPSERVER_ACCOUNTS,
                                    //RelationshipCodes.OneToOne,
                                    RelationshipCodes.ManyToMany,
                                    assetAppServer.getName() + "Access");
                            //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.USER_APPSERVER_ASSOCIATION);
                            List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.USER_APPSERVER_ASSOCIATION);
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
