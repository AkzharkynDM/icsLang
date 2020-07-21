package com.logic;

import com.constants.*;
import com.helpers.MALGenerator;
import com.pojoMAL.*;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataMALLogic extends BaseLogic {
    //private static List<AssetSCADA> allProvidedAssetsNotIncluded = new ArrayList<>();

    public static void createMAlFromConfdb() {
        initAll();
        //defaultAttacks();
        attachDefaultProtection();
        findAssetsConnectionsFromConfdb();
        buildAttackTree();

        Category categorySystem = new Category(MAL_Syntax.CATEGORY_DATA, allProvidedAssets);

        try {
            MALGenerator.generateMAlFile(Stream.of(categorySystem).collect(Collectors.toList()),
                    listOfAssociations,
                    Filenames.pathToDatabasesMalFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void findAssetsConnectionsFromConfdb() {
        for (AssetSCADA assetFirst : allProvidedAssets) {
            for (AssetSCADA assetSecond : allProvidedAssets) {
                if (!assetFirst.equals(assetSecond)) {

                }
            }
        }
    }
}