package com.logic;

import com.jsonworkers.CoreLangWorker;
import com.constants.*;
import com.helpers.*;
import com.pojoJSONParsing.*;
import com.pojoMAL.*;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.MapIterator;

import java.util.*;

/* This is a base mal file that contains the class that is the same for all other MAL logics
 *
 */
 public class BaseLogic {
     protected static List<AssetSCADA> allProvidedAssets;
     protected static Set<Association> listOfAssociations;
    protected static JSONParserToConfDBElement jsonParserFromConfdb;
    protected static List<ConfdbHost> confdbHosts;
    protected static List<ConfdbNetwork> confdbNetworks;
    protected static List<ConfdbUserAccount> confdbUserAccounts;
    protected static List<ConfdbUserGroup> confdbUserGroups;
    protected static List<ConfdbProduct> confdbProducts;
    protected static List<ConfdbAppServer> confdbAppServers;
    protected static List<ConfdbDeServer> confdbDeServers;
    protected static List<ConfdbPseServer> confdbPseServers;
    protected static List<ConfdbPCUServer> confdbPCUServers;
    protected static List<ConfdbMMI> confdbMMIs;
    protected static List<ConfdbAntivirus> confdbAntiviri;
    protected static Map<String, String> routingInfo;
    protected static Map<String, Object> confdbNetworkServices;
    protected static BidiMap<List<String>, String> bidiConfdbNetworkServices;
    protected static List<ConfdbBackupServer> confdbBackupServers;

    //Maps of relevance for fields to fields
    protected static Map<String, List<String>> networkHostRelation = new HashMap<>();
    protected static Map<String, String> accountAppserverRelation = new HashMap<>();
    protected static Map<String, List<String>> appserverProductRelation = new HashMap<>();
    protected static Map<String, String> iccpAppserverRelation = new HashMap<>();
    protected static Map<String, String> accountMMIRelation = new HashMap<>();
    protected static Map<String, String> appServersMMIRelation = new HashMap<>();
    protected static Map<String, List<String>> analysisServerProductRelation = new HashMap<>();
    protected static Map<String, String> appServersDatabaseRelation = new HashMap<>();

    //Not included in MAL files
    protected static List<AssetSCADA> allProvidedAssetsNotIncludedComm = new ArrayList<>();
    protected static List<AssetSCADA> allProvidedAssetsNotIncludedApp = new ArrayList<>();
    protected static List<AssetSCADA> allProvidedAssetsNotIncludedAccounts = new ArrayList<>();

    //For app server MAL
    protected static List<Field> fieldsAppserver = new ArrayList<>();
    protected static AbstractAppServer abstractAppServer = null;
    protected static ActiveDirectory activeDirectory = null;
    protected static List<Field> fieldsAD = null;

    protected static void initAll(){
         listOfAssociations = new HashSet<>();
         jsonParserFromConfdb = new JSONParserToConfDBElement();
         jsonParserFromConfdb.readFromConfdbJson();

         allProvidedAssets = new ArrayList<>();

         confdbHosts = jsonParserFromConfdb.getConfdbHosts();
         confdbNetworks = jsonParserFromConfdb.getConfdbNetworks();
         confdbUserAccounts = jsonParserFromConfdb.getConfdbUserAccounts();
         confdbUserGroups = jsonParserFromConfdb.getConfdbUserGroups();
         confdbProducts = jsonParserFromConfdb.getConfdbProducts();
         confdbAppServers = jsonParserFromConfdb.getConfdbAppServers();
         confdbDeServers = jsonParserFromConfdb.getConfdbDeServers();
         confdbPseServers = jsonParserFromConfdb.getConfdbPseServers();
         confdbPCUServers = jsonParserFromConfdb.getConfdbPCUServers();
         confdbMMIs = jsonParserFromConfdb.getConfdbMMIs();
         routingInfo = jsonParserFromConfdb.getRoutingInfo();
         confdbNetworkServices = jsonParserFromConfdb.getNetworkServices();
         confdbAntiviri = jsonParserFromConfdb.getConfdbAntiviruses();
         confdbBackupServers = jsonParserFromConfdb.getConfdbBackupServers();
         bidiConfdbNetworkServices = BasicMALUtils.reverseKeysAndValues(jsonParserFromConfdb.getNetworkServices());
     }
     protected static void attachDefaultProtection(){
         for (AssetSCADA assetSCADA : allProvidedAssets){
             AttacksDetector.addProtection(assetSCADA);
         }
     }

     protected static void addBaseAssets(){
         List<Field> fieldsDNS = new ArrayList<>();
         List<Field> fieldsNIS = new ArrayList<>();
         List<Field> fieldsNTP = new ArrayList<>();
         fieldsAD = new ArrayList<>();

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
                         case ConfdbElementNames.DNSSERVERS:
                             fieldsDNS.add(new Field(confdbHost.getName()));
                             developerInfoDNS += confdbHost.getName();
                             break;
                         case ConfdbElementNames.NISSERVERS:
                             fieldsNIS.add(new Field(confdbHost.getName()));
                             developerInfoNIS += confdbHost.getName();
                             break;
                         case ConfdbElementNames.NTPSERVERS:
                             fieldsNTP.add(new Field(confdbHost.getName()));
                             developerInfoNTP += confdbHost.getName();
                             break;
                         case ConfdbElementNames.KERBEROSSERVERS:
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
             allProvidedAssetsNotIncludedApp.add(dnsServer);
             allProvidedAssetsNotIncludedAccounts.add(dnsServer);
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
             allProvidedAssetsNotIncludedApp.add(nisServer);
             allProvidedAssetsNotIncludedAccounts.add(nisServer);
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
             allProvidedAssetsNotIncludedApp.add(ntpServer);
             allProvidedAssetsNotIncludedAccounts.add(ntpServer);
         }

         List<Field> fieldsGeneralisedAD = new ArrayList<>();
         if (fieldsAD.size() == 1) {
             fieldsGeneralisedAD.add(new Field(FieldsConstants.ACTIVE_DIRECTORY));
         }
         else {
             fieldsGeneralisedAD.add(new Field(FieldsConstants.ACTIVE_DIRECTORY));
         }
         if (fieldsGeneralisedAD.size() != 0) {
             activeDirectory = new ActiveDirectory(RelationshipCodes.OneToOne, fieldsGeneralisedAD);
             activeDirectory.setDeveloperInfo(developerInfoAD);
             allProvidedAssets.add(activeDirectory);
             allProvidedAssetsNotIncludedComm.add(activeDirectory);
             allProvidedAssetsNotIncludedAccounts.add(activeDirectory);
             //allProvidedAssetsNotIncludedApp.add(activeDirectory);
         }
         //
         for (ConfdbAppServer confdbAppServer: confdbAppServers) {
             fieldsAppserver.add(new Field(confdbAppServer.getName()));
         }
         if (fieldsAppserver.size()==1) {
             List<Field> fieldsGeneralisedAppserver = new ArrayList<>();
             fieldsGeneralisedAppserver.add(new Field(FieldsConstants.APPSERVER));
             abstractAppServer = new AbstractAppServer(AssetsConstants.APPSERVER,
                     RelationshipCodes.OneToOne,
                     fieldsGeneralisedAppserver);
             allProvidedAssets.add(abstractAppServer);
             allProvidedAssetsNotIncludedComm.add(abstractAppServer);
             allProvidedAssetsNotIncludedAccounts.add(abstractAppServer);
         }
         if (fieldsAppserver.size() > 1) {
             List<Field> fieldsGeneralisedAppserver = new ArrayList<>();
             fieldsGeneralisedAppserver.add(new Field(FieldsConstants.APPSERVERS));
             abstractAppServer = new AbstractAppServer(AssetsConstants.APPSERVER,
                     RelationshipCodes.ManyToOne,
                     fieldsGeneralisedAppserver);
             allProvidedAssets.add(abstractAppServer);
             allProvidedAssetsNotIncludedComm.add(abstractAppServer);
             allProvidedAssetsNotIncludedAccounts.add(abstractAppServer);
         }
     }

    /*public static void addCoreLangAssociations(){
        listOfAssociations.addAll(CoreLangWorker.getAssociation());
    }*/

    protected static void buildAttackTree(){
        //Add to the global list to keep a track of all associaions within all MAL logic files
        ConnectionsMap.addToListOfAssociations(listOfAssociations);
        // iteration one attacks
        for (AssetSCADA assetSCADA: allProvidedAssets){
            AttacksDetector.createAttacksPerAsset(assetSCADA, MAL_Syntax.MAL_OR);
        }
        // iteration two attacks
        for (AssetSCADA assetSCADA: allProvidedAssets){
            List<Association> thisAssetAssociation = new ArrayList<>();
            //for (Association association: listOfAssociations) {
            for (Association association: ConnectionsMap.getGlobalListOfAssociation()) {
                if (association.getLeftAssetSCADA().equals(assetSCADA) || association.getRightAssetSCADA().equals(assetSCADA)){
                    thisAssetAssociation.add(association);
                }
            }
            AttacksDetector.createAttacksPerAsset(assetSCADA, thisAssetAssociation, MAL_Syntax.MAL_OR);
        }
    }
}

