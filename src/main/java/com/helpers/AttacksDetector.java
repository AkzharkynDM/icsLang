package com.helpers;

import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.MAL_Syntax;
import com.pojoMAL.AssetSCADA;
import com.pojoMAL.Association;
import com.pojoMAL.Attack;
import com.pojoMAL.Protection;
import org.json.simple.JSONObject;

import java.util.*;

public class AttacksDetector {

    public static void addProtection(AssetSCADA assetSCADA) {
        Map protectionAttackPairs = AttacksDatabaseWorker.lookupProtections(assetSCADA);

        if (protectionAttackPairs == null){
            System.out.println("We did not make any protections for " + assetSCADA.getName());
        }
        if (protectionAttackPairs != null) {
            Set<Map.Entry<String, List<String>>> protectionAttackPair = protectionAttackPairs.entrySet();
            for (Map.Entry<String, List<String>> it : protectionAttackPair) {
                Protection protection = new Protection(it.getKey(), it.getValue());
                assetSCADA.addProtection(protection);
            }
        }
    }

    public static void createAttacksPerAsset(AssetSCADA assetSCADA, String logicType){
        for (String attackName: AttacksDatabaseWorker.lookupForAttack(assetSCADA.getAttackTagKeywords())){
            Attack possibleAttack = new Attack(attackName, logicType, assetSCADA.getName());
            //This is for attacks that remain within the border of attack and lead to another action within the asset
            Set<String> results = AttacksDatabaseWorker.lookupAssetAttackChain(assetSCADA.getTag(), attackName);
            possibleAttack.setResultsOfAttack(results);
            if (!assetSCADA.getAttacksString().contains(possibleAttack.getName())) {
                assetSCADA.addAttack(possibleAttack);
            }
            if (assetSCADA.getAttacksString().contains(possibleAttack.getName())) {
               System.out.println(attackName);
            }
            //This is to add the results of attacks within the same asset to attacks as well with AND
            for (String resultingAttackName : results){
                Attack possibleResultingAttack = new Attack(resultingAttackName, MAL_Syntax.MAL_OR, assetSCADA.getName());
                if (!assetSCADA.getAttacksString().contains(resultingAttackName)) {
                    assetSCADA.addAttack(possibleResultingAttack);
                }
                if (assetSCADA.getAttacksString().contains(resultingAttackName)) {
                    System.out.println(assetSCADA.getAttacksString());
                }
            }
        }
    }

    public static void createAttacksPerAsset(AssetSCADA assetSCADA, List<Association> associationList, String logicType){
        for (Attack attack: assetSCADA.getAttacks()){
            //This is for action that lead to another asset
            AssetSCADA otherAsset = null;
            List<JSONObject> potentialAttacksPairList = null;

            for (Association association: associationList) {
                    otherAsset = association.getAnotherTargetAsset(assetSCADA);
                    potentialAttacksPairList = association.getPotentialAttacks();
                    for (int k=0; k<potentialAttacksPairList.size(); k++) {
                        Iterator<Map.Entry<String, String>> itr = ((Map) potentialAttacksPairList.get(k)).entrySet().iterator();
                        while (itr.hasNext()) {
                            Map.Entry<String, String> entry = itr.next();
                            //assertNotNull(entry);
                            String potentialAttackFirstAsset = entry.getKey();
                            String potentialAttackSecondAsset = entry.getValue();

                            //otherAsset.getAttacksString().contains(potentialAttackFirstAsset);
                            if (attack.getName().equals(potentialAttackFirstAsset)) {
                                for (int i = 0; i < otherAsset.getFields().size(); i++) {
                                    //for (String potentialAttackName : potentialAttacks) {
                                    attack.addResultOfAttack(MALConventionChecker.checkForMalConvention(otherAsset.getFields().get(i).getName())
                                            //+ MAL_Syntax.PARTOF + otherAsset.getAttacksString().get(0));
                                            + MAL_Syntax.PARTOF + potentialAttackSecondAsset);
                                    //}
                                }
                            }
                        }
                    // }
                }
            }
        }

    }
}
