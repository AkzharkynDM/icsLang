package com.pojoMAL;


import com.constants.ClassesInProject;
import com.constants.MAL_Syntax;
import com.constants.RelationshipCodes;
import com.helpers.MALConventionChecker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AssetSCADA {
    //private ConnectionsMap connectionsMap;
    private List<Attack> attacks;
    private List<Protection> protections;
    private String name;
    private String tag;
    private List<Field> fields = new ArrayList<>();
    private List<String> attackTagKeywords;
    protected String userInfo;
    protected String developerInfo;
    protected String modelerInfo;

   /*public AssetSCADA(String name, RelationshipCodes type, List<String> attackTagKeywords, String fieldName){
        this.fields.add(new Field(fieldName, type));
        this.name = name;
        this.attacks = new ArrayList<>();
        this.attackTagKeywords = attackTagKeywords;
    }*/

    public AssetSCADA(String name, String tag, RelationshipCodes type, List<String> attackTagKeywords, List<Field> fields){
        this.fields = fields;
        this.name = name;
        this.tag = tag;
        this.attacks = new ArrayList<>();
        this.protections = new ArrayList<>();
        this.attackTagKeywords = attackTagKeywords;
    }

    /*public AssetSCADA(String name, RelationshipCodes type, List<String> attackTagKeywords, Set<Field> fields){
        this.fields.addAll(fields);
        this.name = name;
        this.attacks = new ArrayList<>();
        this.attackTagKeywords = attackTagKeywords;
    }*/

    public void setDeveloperInfo(String developerInfo) {
        this.developerInfo = developerInfo;
    }

    public String getName(){
        return name;
    }

    public String getTag(){
        return tag;
    }

    public List<String> getAttackTagKeywords() {
        return attackTagKeywords;
    }

    public void addProtection(Protection newProtection){
        protections.add(newProtection);
    }

    public void addAttack(Attack newAttacks){
        this.attacks.add(newAttacks);
        List<String> attacksString = new ArrayList<>();
        for (Attack attack: attacks) {
            attacksString.add(attack.getName());
        }
        //this.connectionsMap.updateNewAttack(field, attacksString);
    }

    public List<Attack> getAttacks(){
        return attacks;
    }

    public List<String> getAttacksString(){
        List<String> attacksString = new ArrayList<>();
        for (Attack attack: attacks){
            //AND attacks don't lead to anywhere
            //if (attack.getLogicType() != MAL_Syntax.MAL_AND) {
                attacksString.add(attack.getName());
            //}
        }
        return attacksString;
    }


    public List<Field> getFields(){
        return fields;
    }

    public void setFields(List<Field> fields){
        this.fields = fields;
    }

    public void addField(Field field){
        fields.add(field);
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        String extendString = "";
        Class a = this.getClass().getSuperclass();
        Class b = this.getClass();
        String getInheritenceA = a.getName();
        String getInheritenceB = b.getName();

        switch (getInheritenceB) {
            case ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ADMIN_ACCOUNT:
                extendString = MAL_Syntax.EXTENDS_ADMIN_ACCOUNT;
                break;
            case ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_SERVICE_ACCOUNT:
                extendString = MAL_Syntax.EXTENDS_SERVICE_ACCOUNT;
                break;
            case ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_USER_ACCOUNT:
                extendString = MAL_Syntax.EXTENDS_USER_ACCOUNT;
                break;
            case ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_NETWORK:
                extendString = MAL_Syntax.EXTENDS_ZONE;
                break;
        }

        switch (getInheritenceA) {
            case ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_HOST:
                extendString = MAL_Syntax.EXTENDS_HOST;
                break;
            case ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ADMIN_ACCOUNT:
                extendString = MAL_Syntax.EXTENDS_ADMIN_ACCOUNT;
                break;
            case ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_NETWORK:
                extendString = MAL_Syntax.EXTENDS_ZONE;
                break;
            case ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_PRODUCT:
                extendString = MAL_Syntax.EXTENDS_PRODUCT;
                break;
            case ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_MMI:
                extendString = MAL_Syntax.EXTENDS_MMI;
                break;
            case ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_DATABASE:
                extendString = MAL_Syntax.EXTENDS_DATABASE;
                break;
            case ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_APP_SERVER:
                extendString = MAL_Syntax.EXTENDS_APPSERVER;
                break;
            case ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_FIREWALL:
                extendString = MAL_Syntax.EXTENDS_FIREWALL;
                break;
            case ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ACTIVE_DIRECTORY:
                extendString = MAL_Syntax.EXTENDS_ACTIVEDIRECTORY;
                break;
            case ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_PASSWORD:
                extendString = MAL_Syntax.EXTENDS_PASSWORD;
                break;
            case ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ANALYSIS_SERVER:
                extendString = MAL_Syntax.EXTENDS_HOST;
                break;
        }

        //result.append(MAL_Syntax.ASSET + MALConventionChecker.checkForMalConvention(name) + extendString + "{\n");
        if (getInheritenceB.equals(ClassesInProject.ORG_MALLANG_SCADALANG_TEST_POJO_MAL_ABSTRACT_HOST)){
            result.append(MAL_Syntax.ABSTRACT_ASSET + name + extendString + "\n");
        } else {
            result.append(MAL_Syntax.ASSET + name + extendString + "\n");
        }
//        if (userInfo !=null) {
//            result.append("\t" + "user info: \"" + userInfo + "\"\n");
//        }
//        if (modelerInfo !=null) {
//            result.append("\t" + "modeler info: \"" + modelerInfo + "\"\n");
//        }
//        if (developerInfo !=null) {
//            result.append("\t" + "developer info: \"" + developerInfo + "\"\n");
//        }
        result.append("{");
        if (attacks!=null) {
            if (attacks.size() != 0) {
                for (Attack attack : attacks) {
                    result.append(attack);
                }
            }
        }
        //if (protections!=null) {
        //    if (protections.size() != 0) {
                for (Protection protection : protections) {
                    result.append(protection);
        //        }
        //    }
        }
        result.append("}\n");
        return result.toString();
    }
}
