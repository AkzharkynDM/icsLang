package com.pojoMAL;

import com.constants.RelationshipCodes;
import com.constants.TypeOfHost;

import java.util.List;

public class AbstractHost extends AssetSCADA {
    private TypeOfHost typeOfHost;
    private String networkConnection;
    private String fieldName;

    /*public AbstractHost(String name, RelationshipCodes type, TypeOfHost typeOfHost, String fieldName){
        super(name, type, AttacksDatabaseWorker.getAssetsAttackTags("host"), fieldName);
        this.typeOfHost = typeOfHost;
        this.fieldName = fieldName;
    }*/

    public AbstractHost(String name, String tagName, RelationshipCodes type, List<String> attackTags, List<Field> fields) {
        super(name, tagName, type, attackTags, fields);
        this.typeOfHost = typeOfHost;
        this.fieldName = fieldName;
    }
    /*public TypeOfHost getTypeOfHost(){
        return typeOfHost;
    }

    public String getNetworkConnection() {
        return networkConnection;
    }

    public void setNetworkConnection(String networkConnection) {
        this.networkConnection = networkConnection;
    }*/
}
