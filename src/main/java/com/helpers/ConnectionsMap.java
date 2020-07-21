package com.helpers;
import com.pojoMAL.AssetSCADA;
import com.pojoMAL.Association;
import com.pojoMAL.Attack;
import com.pojoMAL.Field;

import java.lang.reflect.Array;
import java.util.*;
public class ConnectionsMap {
    private static Set<Association> globalListOfAssociations = new HashSet();
    private static List<AssetSCADA> globalAllProvidedAssets = new ArrayList();

    public static void addToListOfAssets(AssetSCADA asset){
        globalAllProvidedAssets.add(asset);
    }
    public static void addToListOfAssociations(Set<Association> listOfAssociation){
        globalListOfAssociations.addAll(listOfAssociation);
    }

    public static List<AssetSCADA> getGlobalAllProvidedAssets() {
        return globalAllProvidedAssets;
    }

    public static Set<Association> getGlobalListOfAssociation(){
        return globalListOfAssociations;
    }

}
