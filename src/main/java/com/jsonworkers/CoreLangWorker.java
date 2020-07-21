package com.jsonworkers;

import com.constants.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpers.MALConventionChecker;
import com.pojoMAL.Association;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoreLangWorker {
    private static Map<String, List<String>> coreLangMap = new HashMap<>();
    public static void readFromJSONAttacks() {
        try {
            JSONObject jsonObjectCoreLang = new ObjectMapper().readValue(
                    new File(Filenames.pathToCoreLangAssociationsFile),
                    JSONObject.class);
            coreLangMap = (Map<String, List<String>>) jsonObjectCoreLang;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Association> getAssociation(){
        List<Association> newAssociations = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : coreLangMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
            for (String assetSecondName : entry.getValue()) {
                Association newAssociation = new Association(entry.getKey(),
                        assetSecondName,
                        RelationshipCodes.ManyToMany,
                        AssociationsConstants.HAS_VULNERABILITY);
                newAssociations.add(newAssociation);
            }
        }
        return newAssociations;
    }
}
