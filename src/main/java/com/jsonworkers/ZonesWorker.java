package com.jsonworkers;

import com.constants.Filenames;
import com.constants.JSONParsingConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pojoJSONParsing.ParsedConfDBElement;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZonesWorker {
    private static Map<String, String> zonesMap = new HashMap<>();
    private static Map<String, String> tagMap = new HashMap<>();
    private static Map<String, String> firewallsMap = new HashMap<>();

    public static void readFromJSONAttacks() {
        try {
            JSONObject jsonObjectAttacks = new ObjectMapper().readValue(new File(Filenames.pathToZonesFile), JSONObject.class);
            zonesMap = (HashMap<String, String>) jsonObjectAttacks.get(JSONParsingConstants.FIELDS);
            tagMap = (HashMap<String, String>) jsonObjectAttacks.get(JSONParsingConstants.TAGS);
            firewallsMap = (HashMap<String, String>) jsonObjectAttacks.get(JSONParsingConstants.FIREWALLS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getTag(String zoneName){
        return tagMap.get(zoneName);
    }

    public static String getZoneName(String zoneName){
        String result = null;
        for (Map.Entry<String,String> entry : zonesMap.entrySet()){
            if (zoneName.toUpperCase().contains(entry.getKey())){
                result = entry.getValue();
            }
        }
        return result;
    }

    public static String getFirewallName(String zoneName){
        String result = null;
        for (Map.Entry<String,String> entry : firewallsMap.entrySet()){
            if (zoneName.toUpperCase().contains(entry.getKey())){
                result = entry.getValue();
            }
        }
        return result;
    }
}
