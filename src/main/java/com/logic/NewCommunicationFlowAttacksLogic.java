package com.logic;

import com.jsonworkers.AttacksDatabaseWorker;
import com.jsonworkers.ZonesWorker;
import com.constants.*;
import com.helpers.MALConventionChecker;
import com.helpers.MALGenerator;
import com.pojoMAL.*;
import com.suggestedassets.Diod;
import com.suggestedassets.NextGenerationFirewall;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NewCommunicationFlowAttacksLogic extends BaseLogic  {
    private static boolean ifToAddAdditionalDiod = true;
    //private static boolean ifToAddAdditionalUnidirectionalGateway = true;
    private static boolean ifToAddNGFW = true;

    //////////////////////////////////////////////////////////////
    private static void addAssets(){
        for (int i=0; i<confdbNetworks.size(); i++){
            List<Field> fieldsZones = new ArrayList<>();
            String nameField = MALConventionChecker.checkForMalConvention(
                    ZonesWorker.getZoneName(confdbNetworks.get(i).getName()));
            if (nameField !=null) {
                boolean alreadyExistsField = false;
                for (int j=0; j<fieldsZones.size(); j++) {
                    if (fieldsZones.get(j).getName().equals(nameField)) {
                        alreadyExistsField = true;
                    }
                }
                if (!alreadyExistsField) {
                    fieldsZones.add(new Field(nameField));
                }

                String nameNetwork = ZonesWorker.getZoneName(confdbNetworks.get(i).getName());
                AbstractNetwork abstractNetwork = new AbstractNetwork(nameNetwork,
                        ZonesWorker.getTag(nameNetwork),
                        RelationshipCodes.OneToOne,
                        fieldsZones);

                boolean alreadyExistsZone = false;
                for (int j=0; j<allProvidedAssets.size(); j++) {
                    if (allProvidedAssets.get(j).getName().equals(abstractNetwork.getName())) {
                        alreadyExistsZone = true;
                    }
                    if (allProvidedAssets.get(j).getName().equals(AssetsConstants.ICCPZONE)){
                        List<Field> fieldsExternalScada = new ArrayList<>();
                        fieldsExternalScada.add(new Field(FieldsConstants.EXTERNAL_SCADA));
                        ExternalSCADA externalSCADA = new ExternalSCADA(RelationshipCodes.OneToOne, fieldsExternalScada);
                        allProvidedAssets.add(externalSCADA);
                        //
                        Association newAssociation = new Association(externalSCADA,
                                MALConventionChecker.checkForMalConvention(externalSCADA.getName()),
                                abstractNetwork,
                                abstractNetwork.getName(),
                                RelationshipCodes.ManyToMany,
                                externalSCADA.getName() + "Access");
                        //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.HOST_NETWORK_ASSOCIATION);
                        List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.HOST_NETWORK_ASSOCIATION);
                        newAssociation.setPotentialAttacks(potentialAttackNames);
                        listOfAssociations.add(newAssociation);
                    }
                }
                if (!alreadyExistsZone) {
                    allProvidedAssets.add(abstractNetwork);
                    //fieldsZones.clear();
                }
            }
        }
        //
        List<Field> fieldsGeneralizedFirewalls = new ArrayList<>();
        List<Field> fieldsFirewall = new ArrayList<>();
        for (Map.Entry<String,String> entry : routingInfo.entrySet()) {
            //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            fieldsFirewall.add(new Field(entry.getValue()));
        }
        fieldsGeneralizedFirewalls.add(new Field(FieldsConstants.FIREWALL));
        Firewall firewall = new Firewall(
                //ZonesWorker.getFirewallName(entry.getKey()),
                AssetsConstants.FIREWALL,
                RelationshipCodes.OneToOne,
                fieldsGeneralizedFirewalls);
        allProvidedAssets.add(firewall);
        //
        if (ifToAddAdditionalDiod == true){
            List<Field> fieldsDiod = new ArrayList<>();
            fieldsDiod.add(new Field(FieldsConstants.DIOD));
            Diod diod = new Diod(AddiotionalAssetsName.DIOD, com.constants.RelationshipCodes.OneToOne, fieldsDiod);
            allProvidedAssets.add(diod);
        }
        List<Field> fieldsRouter = new ArrayList<>();
        fieldsRouter.add(new Field(FieldsConstants.ROUTER));
        Router router = new Router( RelationshipCodes.OneToOne, fieldsRouter);
        allProvidedAssets.add(router);

        if (ifToAddNGFW == true){
            List<Field> fieldsNGFW = new ArrayList<>();
            fieldsNGFW.add(new Field(FieldsConstants.NGFW));
            NextGenerationFirewall nextGenerationFirewall = new NextGenerationFirewall( RelationshipCodes.OneToOne, fieldsNGFW);
            allProvidedAssets.add(nextGenerationFirewall);
        }
    }

    //////////////////////////////////////////////////////////////
    private static void addAssociations(){
        for (AssetSCADA assetFirst : allProvidedAssets) {
            for (AssetSCADA assetSecond : allProvidedAssets) {
                if (!assetFirst.equals(assetSecond)) {
                    //System.out.println(assetFirst.getClass().getName());
                    //System.out.println(assetSecond.getClass().getName());
                    if (assetFirst.getClass().getSuperclass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_HOST)
                            && assetSecond.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_NETWORK)
                            && (!assetFirst.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_FIREWALL))) {
                        AbstractNetwork assetNetwork = (AbstractNetwork) assetSecond;
                        AbstractHost assetHost = (AbstractHost) assetFirst;
                        Association newAssociation = new Association(assetHost,
                                //hostFieldName,
                                MALConventionChecker.checkForMalConvention(assetHost.getName()),
                                assetNetwork,
                                FieldsConstants.LANS,
                                RelationshipCodes.ManyToMany,
                                assetHost.getName() + "Access");
                        //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.HOST_NETWORK_ASSOCIATION);
                        List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.HOST_NETWORK_ASSOCIATION);
                        newAssociation.setPotentialAttacks(potentialAttackNames);
                        listOfAssociations.add(newAssociation);
                        break;
                    }
                    if (assetFirst.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_NETWORK)
                            && assetSecond.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_FIREWALL)){
                        AbstractNetwork assetNetwork = (AbstractNetwork) assetFirst;
                        Firewall assetFirewall = (Firewall) assetSecond;
                        Association newAssociation = new Association(assetNetwork,
                                MALConventionChecker.checkForMalConvention(assetNetwork.getName()),
                                assetFirewall,
                                FieldsConstants.FIREWALL,
                                RelationshipCodes.ManyToMany,
                                assetNetwork.getName() + "Access");
                        //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.HOST_NETWORK_ASSOCIATION);
                        List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.FIREWALL_ZONE_ASSOCIATION);
                        newAssociation.setPotentialAttacks(potentialAttackNames);
                        listOfAssociations.add(newAssociation);
                        break;
                    }
                    if (assetFirst.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_DIOD)
                            && assetSecond.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_NETWORK)){
                        Diod assetDiod = (Diod) assetFirst;
                        AbstractNetwork assetNetwork = (AbstractNetwork) assetSecond;
                        for (Field fieldNetwork : assetNetwork.getFields()) {
                            for (Field fieldDiod : assetDiod.getFields()) {
                                Association newAssociation = new Association(
                                        assetNetwork,
                                        fieldNetwork.getName(),
                                        assetDiod,
                                        fieldDiod.getName(),
                                        RelationshipCodes.OneToOne,
                                        assetDiod.getName() + "Access");
                                //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.DIOD_DMZZONE_ASSOCIATION);
                                List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.DIOD_DMZZONE_ASSOCIATION);
                                newAssociation.setPotentialAttacks(potentialAttackNames);
                                listOfAssociations.add(newAssociation);
                                break;
                            }
                        }
                    }
                    if (assetFirst.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ROUTER)
                            && assetSecond.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_FIREWALL)){
                        Router router = (Router) assetFirst;
                        Firewall firewall = (Firewall) assetSecond;
                        for (Field fieldRouter : router.getFields()) {
                            for (Field fieldFirewall : firewall.getFields()) {
                                Association newAssociation = new Association(
                                        router,
                                        fieldRouter.getName(),
                                        firewall,
                                        fieldFirewall.getName(),
                                        RelationshipCodes.OneToOne,
                                        router.getName() + "Access");
                                List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.FIREWALL_ROUTER_ASSOCIATION);
                                newAssociation.setPotentialAttacks(potentialAttackNames);
                                listOfAssociations.add(newAssociation);
                                break;
                            }
                        }
                    }
                    if (assetFirst.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_APP_SERVER)
                            && assetSecond.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_NETWORK)){
                        AbstractAppServer abstractAppServer = (AbstractAppServer) assetFirst;
                        AbstractNetwork assetNetwork = (AbstractNetwork) assetSecond;
                        for (Field fieldNetwork : assetNetwork.getFields()) {
                            for (Field fieldAppserver : abstractAppServer.getFields()) {
                                Association newAssociation = new Association(
                                        assetNetwork,
                                        fieldNetwork.getName(),
                                        abstractAppServer,
                                        fieldAppserver.getName(),
                                        RelationshipCodes.OneToOne,
                                        abstractAppServer.getName() + "Access");
                                //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.DIOD_DMZZONE_ASSOCIATION);
                                List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.APPSERVER_INTERNAL_ASSOCIATION);
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

    public static void createExtendedMALFromConfdb(boolean ifToAddAdditionalDiod,
                                                   boolean ifToAddNGFW){
        initAll();
        addAssets();
        addBaseAssets();
        addAssociations();
        ifToAddAdditionalDiod = ifToAddAdditionalDiod;
        ifToAddNGFW = ifToAddNGFW;

        buildAttackTree();
        attachDefaultProtection();

        allProvidedAssets.removeAll(allProvidedAssetsNotIncludedComm);
        Category categoryNetwork = new Category(MAL_Syntax.CATEGORY_NETWORKING, allProvidedAssets);
        try {
            MALGenerator.generateMAlFile(Stream.of(categoryNetwork).collect(Collectors.toList()),
                    listOfAssociations,
                    Filenames.pathToCommunicationMalFile);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
