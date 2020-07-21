package com.oldjsons;

import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.*;
import com.helpers.*;
import com.logic.BaseLogic;
import com.pojoJSONParsing.ConfdbHost;
import com.pojoJSONParsing.ConfdbNetwork;
import com.pojoMAL.*;
import com.suggestedassets.Diod;
import com.suggestedassets.NextGenerationFirewall;
import org.apache.commons.collections4.MapIterator;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//Blocked or delayed flow of information through ICS networks, which could disrupt ICS operation.
//Inaccurate information sent to system operators, either to disguise unauthorized changes, or to cause the operators to initiate inappropriate actions, which could have broad negative effects.
//https://collaborate.mitre.org/attackics/index.php/Overview
public class CommunicationFlowAttacksLogic extends BaseLogic {
    private static Firewall firewall; //TODO!!!
    private static List<Field> fieldsNetwork = new ArrayList<>();
    private static List<Field> fieldsFirewall = new ArrayList<>();
    private static List<Field> fieldsGeneralizedFirewall = new ArrayList<>();
    private static AbstractNetwork networkProd;
    private static AbstractNetwork networkDMZ;
    private static AbstractNetwork networkICCP;
    private static List<Field> fieldsProdZone;
    private static List<Field> fieldsDmzZone;
    private static List<Field> fieldsIccpZone;
    private static List<AssetSCADA> allProvideAssetsNotIncluded = new ArrayList<>();

    private static void addAdditionalDiod(){
        List<Field> fieldsDiod = new ArrayList<>();
        fieldsDiod.add(new Field(FieldsConstants.DIOD));
        Diod diod = new Diod(AddiotionalAssetsName.DIOD, com.constants.RelationshipCodes.OneToOne, fieldsDiod);
        allProvidedAssets.add(diod);
        //
        for (Field fieldNetwork : fieldsDmzZone) {
            for (Field fieldDiod : fieldsDiod) {
                Association newAssociation = new Association(diod,
                        fieldDiod.getName(),
                        networkDMZ,
                        fieldNetwork.getName(),
                        RelationshipCodes.OneToOne,
                        diod.getName() + "Access");
                //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.DIOD_DMZZONE_ASSOCIATION);
                List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.DIOD_DMZZONE_ASSOCIATION);
                newAssociation.setPotentialAttacks(potentialAttackNames);
                listOfAssociations.add(newAssociation);
                break;
            }
        }
        //
        for (Field fieldNetwork : fieldsProdZone) {
            for (Field fieldDiod : fieldsDiod) {
                Association newAssociation = new Association(diod,
                        fieldDiod.getName(),
                        networkProd,
                        fieldNetwork.getName(),
                        RelationshipCodes.OneToOne,
                        diod.getName() + "Access");
                //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.DIOD_PRODZONE_ASSOCIATION);
                List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.DIOD_PRODZONE_ASSOCIATION);
                newAssociation.setPotentialAttacks(potentialAttackNames);
                listOfAssociations.add(newAssociation);
                break;
            }
        }
    }

    private static void addRouter() {
        List<Field> fieldsRouter = new ArrayList<>();
        fieldsRouter.add(new Field(FieldsConstants.ROUTER));
        Router router = new Router( RelationshipCodes.OneToOne, fieldsRouter);
        allProvidedAssets.add(router);
        //
        for (Field fieldNetwork : fieldsDmzZone) {
            for (Field fieldRouter : fieldsRouter) {
                Association newAssociation = new Association(router,
                        fieldRouter.getName(),
                        networkDMZ,
                        fieldNetwork.getName(),
                        RelationshipCodes.OneToOne,
                        fieldRouter.getName() + "Access");
                //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.UNIDIRECTIONALGATEWAY_DMZZONE_ASSOCIATION);
                List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.UNIDIRECTIONALGATEWAY_DMZZONE_ASSOCIATION);
                newAssociation.setPotentialAttacks(potentialAttackNames);
                listOfAssociations.add(newAssociation);
                break;
            }
        }

        for (Field fieldNetwork : fieldsProdZone) {
            for (Field fieldRouter : fieldsRouter) {
                Association newAssociation = new Association(router,
                        fieldRouter.getName(),
                        networkProd,
                        fieldNetwork.getName(),
                        RelationshipCodes.OneToOne,
                        fieldRouter.getName() + "Access");
                //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.UNIDIRECTIONALGATEWAY_PRODZONE_ASSOCIATION);
                List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.UNIDIRECTIONALGATEWAY_PRODZONE_ASSOCIATION);
                newAssociation.setPotentialAttacks(potentialAttackNames);
                listOfAssociations.add(newAssociation);
                break;
            }
        }
    }

    private static void addAdditionalNGFW(){
        List<Field> fieldsNGFW = new ArrayList<>();
        fieldsNGFW.add(new Field(FieldsConstants.NGFW));
        NextGenerationFirewall nextGenerationFirewall = new NextGenerationFirewall( RelationshipCodes.OneToOne, fieldsNGFW);
        allProvidedAssets.add(nextGenerationFirewall);
        //
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void addAssets(){
        for (ConfdbNetwork confdbNetwork : confdbNetworks){
            List<String> relatedHosts = new ArrayList<>();
            for (ConfdbHost confdbHost : confdbHosts) {
                if (confdbNetwork.getName().equals(confdbHost.getLAN())){
                    relatedHosts.add(confdbHost.getName());
                }
                networkHostRelation.put(confdbNetwork.getName().toLowerCase(), relatedHosts);
            }
        }

        for (ConfdbNetwork confdbNetwork : confdbNetworks) {
            fieldsNetwork.add(new Field(confdbNetwork.getName()));
        }
        if (fieldsNetwork.size() == 1){
            List<Field> fieldsGeneralisedNetwork = new ArrayList<>();
            fieldsGeneralisedNetwork.add(new Field(FieldsConstants.LAN));
            AbstractNetwork internalNetwork = new AbstractNetwork(AssetsConstants.INTERNAL_NETWORK,
                    AssetTagName.NETWORK,
                    RelationshipCodes.OneToOne,
                    fieldsGeneralisedNetwork);
            allProvidedAssets.add(internalNetwork);
            //
            //AppserverMALLogic.createAssetClasses();
            //AbstractAppServer appServer = AppserverMALLogic.getAbstractAppServer();
            List<Field> fieldsAppserver = new ArrayList<>();
            fieldsAppserver.add(new Field(FieldsConstants.APPSERVER));
            AbstractAppServer appServer = new AbstractAppServer(AssetsConstants.APPSERVER,
                    RelationshipCodes.OneToOne,
                    fieldsAppserver);
            allProvidedAssets.add(appServer);
            allProvideAssetsNotIncluded.add(appServer);
            //
            Association newAssociation = new Association(
                    appServer,
                    FieldsConstants.APPSERVER,
                    internalNetwork,
                    FieldsConstants.INTERNAL_NETWORK,
                    RelationshipCodes.OneToOne,
                    AssociationsConstants.APPSERVER_ACCESS);
            //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.APPSERVER_INTERNAL_ASSOCIATION);
            List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.APPSERVER_INTERNAL_ASSOCIATION);
            newAssociation.setPotentialAttacks(potentialAttackNames);
            listOfAssociations.add(newAssociation);
        }
        if (fieldsNetwork.size() > 1){
            List<Field> fieldsGeneralisedNetwork = new ArrayList<>();
            fieldsGeneralisedNetwork.add(new Field(FieldsConstants.LANS));
            AbstractNetwork internalNetwork = new AbstractNetwork(AssetsConstants.INTERNAL_NETWORK,
                    AssetTagName.NETWORK,
                    RelationshipCodes.ManyToMany,
                    fieldsGeneralisedNetwork);
            allProvidedAssets.add(internalNetwork);
            //
            //AppserverMALLogic.createAssetClasses();
            //AbstractAppServer appServer = AppserverMALLogic.getAbstractAppServer();
            List<Field> fieldsAppserver = new ArrayList<>();
            fieldsAppserver.add(new Field(FieldsConstants.APPSERVERS));
            AbstractAppServer appServer = new AbstractAppServer(AssetsConstants.APPSERVER,
                    RelationshipCodes.OneToOne,
                    fieldsAppserver);
            allProvidedAssets.add(appServer);
            allProvideAssetsNotIncluded.add(appServer);
            //
            Association newAssociation = new Association(
                    appServer,
                    FieldsConstants.APPSERVERS,
                    internalNetwork,
                    FieldsConstants.INTERNAL_NETWORK,
                    RelationshipCodes.ManyToMany,
                    AssociationsConstants.APPSERVER_ACCESS);
            //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.APPSERVER_INTERNAL_ASSOCIATION);
            List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.APPSERVER_INTERNAL_ASSOCIATION);
            newAssociation.setPotentialAttacks(potentialAttackNames);
            listOfAssociations.add(newAssociation);
        }

        //
        String developerInfoFirewall = "";
        for (ConfdbHost confdbHost : confdbHosts) {
            String nameOfFirewall = "";
            for (ConfdbNetwork confdbNetwork : confdbNetworks) {
                if (confdbNetwork.getName().equals(confdbHost.getLAN())) {
                    if (confdbHost.getName().equals(routingInfo.get(confdbHost.getLAN()))) {
                        nameOfFirewall = confdbHost.getName();
                        fieldsFirewall.add(new Field(nameOfFirewall));
                        developerInfoFirewall += nameOfFirewall;
                    }
                }
            }
        }
        if (fieldsFirewall.size() == 1) {
            fieldsGeneralizedFirewall.add(new Field(FieldsConstants.FIREWALL));
            firewall = new Firewall("Firewall", RelationshipCodes.OneToOne, fieldsGeneralizedFirewall);
            firewall.setDeveloperInfo("Firewall: " + developerInfoFirewall);
            allProvidedAssets.add(firewall);
        }
        if (fieldsFirewall.size() > 1) {
            fieldsGeneralizedFirewall.add(new Field(FieldsConstants.FIREWALLS));
            firewall = new Firewall("Firewall", RelationshipCodes.ManyToMany, fieldsGeneralizedFirewall);
            firewall.setDeveloperInfo("Firewalls: " + developerInfoFirewall);
            allProvidedAssets.add(firewall);
        }
        //

        List<Field> fieldsDNS = new ArrayList<>();
        List<Field> fieldsNIS = new ArrayList<>();
        List<Field> fieldsNTP = new ArrayList<>();
        List<Field> fieldsAD = new ArrayList<>();

        String developerInfoDNS = "";
        String developerInfoNIS = "";
        String developerInfoNTP = "";
        String developerInfoAD = "";

        for (ConfdbHost confdbHost: confdbHosts) {
            MapIterator<List<String>, String> it = bidiConfdbNetworkServices.mapIterator();
            while (it.hasNext()) {
                // this is to systemise the host into Kerberos AD, DNS, NTP, NIS
                // check network properties
                List<String> possibleTypesOfHost = it.next();
                if (possibleTypesOfHost.contains(confdbHost.getName())) {
                    switch (it.getValue()){
                        case "DNSServers":
                            fieldsDNS.add(new Field(confdbHost.getName()));
                            developerInfoDNS += confdbHost.getName();
                            break;
                        case "NISServers":
                            fieldsNIS.add(new Field(confdbHost.getName()));
                            developerInfoNIS += confdbHost.getName();
                            break;
                        case "NTPServers":
                            fieldsNTP.add(new Field(confdbHost.getName()));
                            developerInfoNTP += confdbHost.getName();
                            break;
                        case "KerberosServers":
                            fieldsAD.add(new Field(confdbHost.getName()));
                            developerInfoAD += confdbHost.getName();
                            break;
                    }
                }
            }
        }

        List<Field> fieldsGeneralisedDNS = new ArrayList<>();
        if (fieldsDNS.size() == 1) {
            fieldsGeneralisedDNS.add(new Field(FieldsConstants.DNS_SERVER));
        }
        else {
            fieldsGeneralisedDNS.add(new Field(FieldsConstants.DNS_SERVER));
        }
        if (fieldsGeneralisedDNS.size() != 0) {
            DNSServer dnsServer = new DNSServer(RelationshipCodes.OneToOne, fieldsGeneralisedDNS);
            dnsServer.setDeveloperInfo(developerInfoDNS);
            allProvidedAssets.add(dnsServer);
        }

        List<Field> fieldsGeneralisedNIS = new ArrayList<>();
        if (fieldsNIS.size() == 1) {
            fieldsGeneralisedNIS.add(new Field(FieldsConstants.NIS_SERVER));
        }
        else {
            fieldsGeneralisedNIS.add(new Field(FieldsConstants.NIS_SERVER));
        }
        if (fieldsGeneralisedNIS.size() != 0) {
            NISServer nisServer = new NISServer(RelationshipCodes.OneToOne, fieldsGeneralisedNIS);
            nisServer.setDeveloperInfo(developerInfoNIS);
            allProvidedAssets.add(nisServer);
        }


        List<Field> fieldsGeneralisedNTP = new ArrayList<>();
        if (fieldsNTP.size() == 1) {
            fieldsGeneralisedNTP.add(new Field(FieldsConstants.NTP_SERVER));
        }
        else {
            fieldsGeneralisedNTP.add(new Field(FieldsConstants.NTP_SERVER));
        }
        if (fieldsGeneralisedNTP.size() != 0) {
            NTPServer ntpServer = new NTPServer(RelationshipCodes.OneToOne, fieldsGeneralisedNTP);
            ntpServer.setDeveloperInfo(developerInfoNTP);
            allProvidedAssets.add(ntpServer);
        }

        List<Field> fieldsGeneralisedAD = new ArrayList<>();
        if (fieldsAD.size() == 1) {
            fieldsGeneralisedAD.add(new Field(FieldsConstants.ACTIVE_DIRECTORY));
        }
        else {
            fieldsGeneralisedAD.add(new Field(FieldsConstants.ACTIVE_DIRECTORY));
        }
        if (fieldsGeneralisedAD.size() != 0) {
            ActiveDirectory activeDirectory = new ActiveDirectory(RelationshipCodes.OneToOne, fieldsGeneralisedAD);
            activeDirectory.setDeveloperInfo(developerInfoAD);
            allProvidedAssets.add(activeDirectory);
        }
    }

    private static void addProdAndDMZ() {
        fieldsProdZone = new ArrayList<>();
        fieldsProdZone.add(new Field(FieldsConstants.PROD));
        networkProd = new AbstractNetwork(AssetsConstants.PRODZONE,
                AssetTagName.PRODZONE,
                RelationshipCodes.OneToOne,
                fieldsProdZone);
        networkProd.setDeveloperInfo("Prod Zone cannot be reached form DMZ and any other zones");
        allProvidedAssets.add(networkProd);
        //
        fieldsDmzZone = new ArrayList<>();
        fieldsDmzZone.add(new Field(FieldsConstants.DMZ));
        networkDMZ = new AbstractNetwork(AssetsConstants.DEMILITARIZED_ZONE,
                AssetTagName.DMZ,
                RelationshipCodes.OneToOne,
                fieldsDmzZone);
        networkDMZ.setDeveloperInfo("DMZ is a physical or logical subnet that separates an internal local area network (LAN) from other untrusted networks usually the public internet.");
        allProvidedAssets.add(networkDMZ);
        //
        fieldsIccpZone = new ArrayList<>();
        fieldsIccpZone.add(new Field(FieldsConstants.ICCPZONE));
        networkICCP = new AbstractNetwork(AssetsConstants.ICCPZONE,
                AssetTagName.ICCPZONE,
                RelationshipCodes.OneToOne,
                fieldsIccpZone);
        networkICCP.setDeveloperInfo(" Inter-control Center Communications Protocol (ICCP) was developed to enable data" +
                "exchange over WANs between utility control centers");
        allProvidedAssets.add(networkICCP);
        //
        List<Field> fieldsRouter = new ArrayList<>();
        fieldsRouter.add(new Field(FieldsConstants.ROUTER));
        Router iccpRouter = new Router(RelationshipCodes.OneToOne, fieldsRouter);
        allProvidedAssets.add(iccpRouter);
        //
        for (Field fieldIccpZone : fieldsIccpZone) {
            Association newAssociation = new Association(iccpRouter,
                    FieldsConstants.ROUTER,
                    networkICCP,
                    fieldIccpZone.getName(),
                    RelationshipCodes.OneToOne,
                    //assetHost.getName() + "Access",
                    AssociationsConstants.ROUTER_ACCESS);
            //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.ICCPZONE_ROUTER_ASSOCIATION);
            List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.ICCPZONE_ROUTER_ASSOCIATION);
            newAssociation.setPotentialAttacks(potentialAttackNames);
            listOfAssociations.add(newAssociation);
        }
        //
        for (Field fieldFirewall : fieldsGeneralizedFirewall) {
            for (Field fieldProdZone : fieldsProdZone) {
                Association newAssociation = new Association(firewall,
                        //hostFieldName,
                        fieldFirewall.getName(),
                        networkProd,
                        fieldProdZone.getName(),
                        RelationshipCodes.OneToOne,
                        //assetHost.getName() + "Access",
                        AssociationsConstants.FIREWALL_ACCESS);
                //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.PRODZONE_FIREWALL_ASSOCIATION);
                List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.PRODZONE_FIREWALL_ASSOCIATION);
                newAssociation.setPotentialAttacks(potentialAttackNames);
                listOfAssociations.add(newAssociation);
            }
            //
            for (Field fieldDmzZone : fieldsDmzZone) {
                Association newAssociation = new Association(firewall,
                        //hostFieldName,
                        fieldFirewall.getName(),
                        networkDMZ,
                        fieldDmzZone.getName(),
                        RelationshipCodes.OneToOne,
                        //assetHost.getName() + "Access",
                        AssociationsConstants.FIREWALL_ACCESS);
                //List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.DMZ_FIREWALL_ASSOCIATION);
                List<JSONObject> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.DMZ_FIREWALL_ASSOCIATION);
                newAssociation.setPotentialAttacks(potentialAttackNames);
                listOfAssociations.add(newAssociation);
            }
            //
            Association routerFirewallAssociation = new Association(
                        firewall,
                        fieldFirewall.getName(),
                        iccpRouter,
                        FieldsConstants.ROUTER,
                        RelationshipCodes.OneToOne,
                        //assetHost.getName() + "Access",
                        AssociationsConstants.FIREWALL_ACCESS);
            //List<String> potentialAttackNamesRouterFirewall = AttacksDatabaseWorker.getPotentialAttacks(AssociationsConstants.FIREWALL_ROUTER_ASSOCIATION);
            List<JSONObject> potentialAttackNamesRouterFirewall = AttacksDatabaseWorker.getPotentialAttacksInAssociation(AssociationsConstants.FIREWALL_ROUTER_ASSOCIATION);
            routerFirewallAssociation.setPotentialAttacks(potentialAttackNamesRouterFirewall);
            listOfAssociations.add(routerFirewallAssociation);
        }
        //
        /*for (Field fieldProdZone : fieldsProdZone) {
            for (Field fieldDmzZone : fieldsDmzZone) {
                Association newWindowsAssociation = new Association(networkDMZ,
                        fieldDmzZone.getName(),
                        networkProd,
                        fieldProdZone.getName(),
                        RelationshipCodes.OneToOne,
                        "ProdZoneAccess");
                List<String> potentialAttackNamesWindows = AttacksDatabaseWorker.getPotentialAttacks("zone-zone-association");
                newWindowsAssociation.setPotentialAttacks(potentialAttackNamesWindows);
                listOfAssociations.add(newWindowsAssociation);
            }
        }*/
    }

    private static void addHostNetworkAssetsAndAssociations() {
        for (AssetSCADA assetFirst : allProvidedAssets) {
            for (AssetSCADA assetSecond : allProvidedAssets) {
                if (!assetFirst.equals(assetSecond)) {
                    if (assetFirst.getClass().getSuperclass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_HOST)
                            && assetSecond.getClass().getName().equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_NETWORK)) {
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

                        /*for (Field fieldNetwork : assetNetwork.getFields()) {
                            for (Field fieldHost : assetHost.getFields()) {
                                String networkFieldName = fieldNetwork.getName();
                                List<String> relatedHostsForNetwork = networkHostRelation.get(networkFieldName);
                                if (relatedHostsForNetwork != null) {
                                    if (relatedHostsForNetwork.contains(fieldHost.getName())) {
                                        String hostFieldName = fieldHost.getName();
                                        Association newAssociation = new Association(assetHost,
                                                hostFieldName,
                                                assetNetwork,
                                                networkFieldName,
                                                RelationshipCodes.OneToOne,
                                                assetHost.getName() + "Access");
                                        List<String> potentialAttackNames = AttacksDatabaseWorker.getPotentialAttacks("host-network-association");
                                        newAssociation.setPotentialAttacks(potentialAttackNames);
                                        listOfAssociations.add(newAssociation);
                                        break;
                                    }
                                }
                            }
                        }*/
                    }
                }
            }
        }
    }

    public static void createExtendedMALFromConfdb(boolean ifToAddAdditionalDiod,
                                                   boolean ifToAddNGFW){
    initAll();
    addAssets();
    addHostNetworkAssetsAndAssociations();
    addProdAndDMZ();
    if (ifToAddAdditionalDiod) {
        addAdditionalDiod();
    }

    addRouter();

    if (ifToAddNGFW){
        addAdditionalNGFW();
    }
    buildAttackTree();

    allProvidedAssets.removeAll(allProvideAssetsNotIncluded);
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
