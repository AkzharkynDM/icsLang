package com.jsonworkers;

import com.constants.DistributionsConstants;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pojoJSONParsing.ParsedConfDBElement;
import com.pojoMAL.AssetSCADA;
import org.json.simple.JSONObject;
import com.constants.ConfdbElementNames;
import com.constants.Filenames;
import com.constants.JSONParsingConstants;

import javax.print.DocFlavor;
import java.io.File;
import java.util.*;

public class AttacksDatabaseWorker {
    private static Map<String, List<String>> attacksMap = new HashMap<>();
    //https://www.us-cert.gov/ics/Secure-Architecture-Design-Definitions
    //I can get protection scenarios from here
    private static Map<String, Map> protectionsMap = new HashMap<>();
    private static Map<String, List<JSONObject>> potentialAttacksInAssociation = new HashMap<>();
    private static Map<String, List<String>> assetsAttackTags = new HashMap<>();
    private static Map<String, Map> attacksChain = new HashMap<>();
    private static Map<String, Double> cvssScores = new HashMap<>();
    private static Map<String, String> risks = new HashMap<>();
    private static Map<String, List> comments = new HashMap<>();
    private static Map<String, List<String>> existsInfo = new HashMap<>();

    public static void readFromJSONAttacks() {
        try {
            JSONObject jsonObjectAttacks = new ObjectMapper().readValue(new File(Filenames.pathToAttacksFile), JSONObject.class);
            JSONObject jsonObjectCVSS = new ObjectMapper().readValue(new File(Filenames.pathToCVSSFile), JSONObject.class);
            JSONObject jsonObjectProtection = new ObjectMapper().readValue(new File(Filenames.pathToProtectionFile), JSONObject.class);
            JSONObject jsonObjectRisks = new ObjectMapper().readValue(new File(Filenames.pathToRisksFile), JSONObject.class);
            JSONObject jsonObjectComments = new ObjectMapper().readValue(new File(Filenames.pathToCommentsFile), JSONObject.class);
            JSONObject jsonObjectExists = new ObjectMapper().readValue(new File(Filenames.pathToExistsFile), JSONObject.class);

            attacksMap = (HashMap<String, List<String>>) jsonObjectAttacks.get(JSONParsingConstants.ATTACKS);
            protectionsMap = (HashMap<String, Map>) jsonObjectProtection;
            potentialAttacksInAssociation = (HashMap<String, List<JSONObject>>) jsonObjectAttacks.get(JSONParsingConstants.ASSOCIATIONS_ATTACKS);
            assetsAttackTags = (HashMap<String, List<String>>) jsonObjectAttacks.get(JSONParsingConstants.ASSETS_ATTACK_TAGS);
            attacksChain = (HashMap<String, Map>) jsonObjectAttacks.get(JSONParsingConstants.ATTACKS_CHAIN);
            cvssScores = (HashMap<String, Double>) jsonObjectCVSS;
            risks = (HashMap<String, String>) jsonObjectRisks;
            comments = (HashMap<String, List>) jsonObjectComments;
            existsInfo = (HashMap<String, List<String>>) jsonObjectExists;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getDistribution (String attackName){ //Time to Compromise related
        //CVSS is impact and severity, not easiness
        String probabilityDistribution = "";
        /*0.1 - 3.9 Low
        4.0 - 6.9 Medium
        7.0 - 8.9 High
        9.0 - 10.0 Critical*/
        if (cvssScores.get(attackName) !=null  && cvssScores.get(attackName) < 2.0){
            probabilityDistribution = DistributionsConstants.EasyAndCertain;
        } else if (cvssScores.get(attackName) !=null  && cvssScores.get(attackName) < 4.0){
            probabilityDistribution = DistributionsConstants.EasyAndUncertian;
        } else if (cvssScores.get(attackName) !=null  && cvssScores.get(attackName) < 6.0){
            probabilityDistribution = DistributionsConstants.HardAndCertain;
        } else if (cvssScores.get(attackName) !=null  && cvssScores.get(attackName) < 8.0){
            probabilityDistribution = DistributionsConstants.HardAndUncertain;
        } else if (cvssScores.get(attackName) !=null  && cvssScores.get(attackName) < 9.0){
            probabilityDistribution = DistributionsConstants.VeryHardAndCertain;
        } else if (cvssScores.get(attackName) !=null)  {
            probabilityDistribution = DistributionsConstants.EasyAndCertain;
        }
        return probabilityDistribution;
    }

    public static List<String> lookupForAttack(List<String> attackTagKeyword){
        List<String> resultList = new ArrayList<>();
        if (attackTagKeyword != null) {
            for (int i = 0; i < attackTagKeyword.size(); i++) {
                //System.out.println(attacksMap.get(attackTagKeyword.get(i)));
                if (attacksMap.get(attackTagKeyword.get(i))!= null ) {
                    for (String attackName : attacksMap.get(attackTagKeyword.get(i))){
                        //resultList.add(attackName + " " + getDistribution(attackName));
                        resultList.add(attackName);

                    }
                }
            }
        }
        return resultList;
    }

    public static Map lookupProtections(AssetSCADA assetSCADA) {
        Map protectionAttackPairs = protectionsMap.get(assetSCADA.getName());
        return  protectionAttackPairs;
    }

    public static String lookupRisks(String attackName){
        return risks.get(attackName);
    }

    //This is to understand which action leads to which action within the same asset
    public static Set<String> lookupAssetAttackChain(String assetName, String attackName){
        //List<String> list = new ArrayList<>();
        Set<String> result = new HashSet<>();
        if (attacksChain.get(assetName) != null) {
            Map<String, List<String>> attackTagsPerAsset = (HashMap) attacksChain.get(assetName);
            if (attackTagsPerAsset.get(attackName) != null) {
                result.addAll(attackTagsPerAsset.get(attackName));
            }
        }
        return result;
    }

    //this is to get potential attacks in an association
    //This function is called when we create a new association with and setPotentialAttacks()
    /*public static List<String> getPotentialAttacks(String associationCode){
        return potentialAttacksInAssociation.get(associationCode);
    }*/
    public static List<JSONObject> getPotentialAttacksInAssociation(String associationCode){
        return potentialAttacksInAssociation.get(associationCode);
    }

    public static List<String> getAssetsAttackTags(String assetName){
        return assetsAttackTags.get(assetName);
    }

    public static List<String> getExistsInfo(String assetName){
        return existsInfo.get(assetName);
    }
}
