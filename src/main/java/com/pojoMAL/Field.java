package com.pojoMAL;

import com.constants.RelationshipCodes;
import com.helpers.MALConventionChecker;
import org.atteo.evo.inflector.English;

public class Field {
    private String name;

    public Field(String name, RelationshipCodes relationshipCodes){
        this.name = MALConventionChecker.determineFieldAmount(relationshipCodes,
                //MALConventionChecker.checkForMalConvention(name),
                name);
    }

    public Field(String name){
        this.name = name;
    }

    public String getName(){
        return this.name.toLowerCase();
    }

    @Override
    public String toString(){
        return " [" + this.name.toLowerCase() + "] ";
    }

}
