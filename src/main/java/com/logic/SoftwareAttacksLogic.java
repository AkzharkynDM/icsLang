package com.logic;

import com.constants.*;
import com.helpers.*;
import com.pojoJSONParsing.ConfdbHost;
import com.pojoMAL.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//ICS software or configuration settings modified, or ICS software infected with malware, which could have broad negative effects.
//https://collaborate.mitre.org/attackics/index.php/Overview
public class SoftwareAttacksLogic extends BaseLogic {
    private static List<AssetSCADA> allProvidedAssetsContainerNotIncluded = new ArrayList<>();

    private static void createAssetClasses(){
        List<Field> fieldsWindows = new ArrayList<>();
        List<Field> fieldsLinux = new ArrayList<>();
        List<Field> fieldsRedhat = new ArrayList<>();

        for (ConfdbHost confdbHost : confdbHosts){
            String platformName = confdbHost.getPlatform();
            switch (platformName){
                case ConfdbElementNames.WINDOWS:
                    fieldsWindows.add(new Field(MALConventionChecker.checkForMalConvention(confdbHost.getName())));
                case ConfdbElementNames.LINUX:
                    fieldsLinux.add(new Field(MALConventionChecker.checkForMalConvention(confdbHost.getName())));
                case ConfdbElementNames.REDHAT:
                    fieldsRedhat.add(new Field(MALConventionChecker.checkForMalConvention(confdbHost.getName())));
                case JSONParsingConstants.NOVALUE:
                    fieldsLinux.add(new Field(MALConventionChecker.checkForMalConvention(confdbHost.getName())));
            }
        }

        if (fieldsWindows.size() != 0) {
            WindowsServer windowsServer = new WindowsServer(RelationshipCodes.OneToOne, fieldsWindows);
            allProvidedAssets.add(windowsServer);
        }
        if (fieldsLinux.size() != 0) {
            LinuxServer linuxServer = new LinuxServer(RelationshipCodes.OneToOne, fieldsLinux);
            allProvidedAssets.add(linuxServer);
        }
        if (fieldsRedhat.size() !=0) {
            RedhatServer redhatServer = new RedhatServer(RelationshipCodes.OneToOne, fieldsRedhat);
            allProvidedAssets.add(redhatServer);
        }
    }

    private static void addAssociations() {
        for (AssetSCADA assetFirst : allProvidedAssetsContainerNotIncluded) {
            for (AssetSCADA assetSecond : allProvidedAssets) {
                if (!assetFirst.equals(assetSecond)) {
                }
            }
        }
    }


    public static void createExtendedMALFromConfdb(){
        initAll();
        createAssetClasses();
        addAssociations();
        buildAttackTree();

        Category categoryContainer = new Category(MAL_Syntax.CATEGORY_CONTAINER, allProvidedAssets);

        try {
            MALGenerator.generateMAlFile(Stream.of(categoryContainer).collect(Collectors.toList()),
                    listOfAssociations,
                    Filenames.pathToSoftwaresMalFile);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
