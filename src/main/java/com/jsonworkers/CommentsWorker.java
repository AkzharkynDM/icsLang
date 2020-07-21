package com.jsonworkers;

import com.constants.Filenames;
import com.constants.JSONParsingConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pojoMAL.AssetSCADA;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CommentsWorker {
    private static Map<String, Map> commentsMap = new HashMap<>();

    public static void readFromJSONAttacks() {
        try {
            JSONObject jsonObjectComments = new ObjectMapper().readValue(new File(Filenames.pathToCommentsFile), JSONObject.class);
            commentsMap = (HashMap<String, Map>) jsonObjectComments;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String lookupDeveloperCommentsPerAttack(String assetName, String attackName) {
        if (commentsMap.get(assetName) == null){
            System.out.println("No comment for asset " + assetName);
        }
        Map mapCommentsAttacks = new HashMap();
        if (commentsMap.get(assetName) != null) {
            mapCommentsAttacks = commentsMap.get(assetName);
        }
        if (mapCommentsAttacks.get(attackName) == null){
            System.out.println("No comment for attack " + attackName);
        }
        String commentsPerAttack = "";
        if (mapCommentsAttacks.get(attackName) != null) {
            commentsPerAttack = (String) mapCommentsAttacks.get(attackName);
        }
        return commentsPerAttack;
    }

    public static String lookupUserCommentsPerAttack(String assetName, String attackName) {
      return "";
    }

    public static String lookupModelerCommentsPerAttack(String assetName, String attackName) {
        return "";
    }
}
