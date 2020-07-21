package com.logic;

import com.pojoMAL.*;

import java.util.ArrayList;
import java.util.List;

//TODO I need to delete this file
public class SuggestedAssetsLogic extends BaseLogic {
    private static List<AssetSCADA> allProvidedAssetsNotIncluded = new ArrayList<>();

    public static void createMAlFromConfdb(){
        /*initAll();
        createAssetClasses();
        attachDefaultProtection();
        findAssetsConnectionsFromConfdb();
        buildAttackTree();

        Category categorySystem = new Category(MAL_Syntax.CATEGORY_ADDITIONAL, allProvidedAssets);

        try {
            MALGenerator.generateMAlFile(Stream.of(categorySystem).collect(Collectors.toList()),
                    listOfAssociations,
                    Filenames.pathToSuggestedMalFile);
        } catch (IOException e){
            e.printStackTrace();
        }*/
    }

    private static void createAssetClasses(){
    }

    private static void findAssetsConnectionsFromConfdb(){

    }
}
