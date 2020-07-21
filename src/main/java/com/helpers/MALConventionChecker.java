package com.helpers;

import com.constants.RelationshipCodes;
import org.atteo.evo.inflector.English;

import static com.constants.RelationshipCodes.*;

public class MALConventionChecker {
// TODO remove the comments
    public static String checkForMalConvention(String word){
        word = word.replace("-", "");
        word = word.replace("_", "");
        char[] chArr = word.toCharArray();
        for (int i=0; i<chArr.length; i++){
            if (chArr[i] == '_' && i+1< chArr.length) {
                chArr[i+1] = Character.toUpperCase(chArr[i+1]);
            }
            if (Character.isUpperCase(chArr[i])){
                chArr[i] = Character.toLowerCase(chArr[i]);
            }
        }
        return new String(chArr);
        //return word;
    }

    public static String checkForMalConventionAssetName(String word){
        word = word.replace("-", "");
        word = word.replace("_", "");
        char[] chArr = word.toCharArray();
        if (Character.isLowerCase(chArr[0])){
            chArr[0] = Character.toUpperCase(chArr[0]);
        }
        for (int i=1; i<chArr.length; i++){
            if (chArr[i] == '_' && i+1< chArr.length) {
                chArr[i+1] = Character.toUpperCase(chArr[i+1]);
            }
            if (Character.isUpperCase(chArr[i])){
                chArr[i] = Character.toLowerCase(chArr[i]);
            }
        }
        return new String(chArr);
        //return word;
    }

    public static String determineFieldAmount(RelationshipCodes relationshipCodes, String name){
        String result = "";
        switch (relationshipCodes){
            case ManyToMany:
                result = English.plural(name);
                break;
            case ManyToOne:
                result = English.plural(name);
                break;
            case OneToMany:
                result = English.plural(name);
                break;
            case OneToOne:
                result = name;
                break;
        }
        return result;
    }
}
