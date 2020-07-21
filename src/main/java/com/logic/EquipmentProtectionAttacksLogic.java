package com.logic;

//"Unauthorized changes to instructions, commands, or alarm thresholds, which could damage, disable, or shut down equipment, create environmental impacts, and/or endanger human life"
//https://collaborate.mitre.org/attackics/index.php/Overview
public class EquipmentProtectionAttacksLogic extends BaseLogic{
    //Operators?
    //Category Attackers
    //misconfigure alarms: "boot alarm" and "start alarm"

    private static void addAssetsAndAssociations() {
    }

    /*public static void createExtendedMALFromConfdb(){
        initAll();
        addAssetsAndAssociations();
        buildAttackTree();

        Category categoryNetwork = new Category(MAL_Syntax.CATEGORY_HARDWARE, allProvidedAssets);

        try {
            MALGenerator.generateMAlFile(Stream.of(categoryNetwork).collect(Collectors.toList()),
                    listOfAssociations,
                    Filenames.pathToEquipmentMalFile);
        } catch (IOException e){
            e.printStackTrace();
        }
    }*/
}

