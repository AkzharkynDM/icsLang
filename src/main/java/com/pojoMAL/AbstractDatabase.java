package com.pojoMAL;


import com.constants.RelationshipCodes;

import java.util.List;

//This should have children: OracleDatabase, PostgreDatabase, AvantiDatabase
public abstract class AbstractDatabase extends AssetSCADA {
    //private String appserver;

    public AbstractDatabase(String name, RelationshipCodes type, List<String> attackTagKeywords, List<Field> fields){
       super(name, name, type, attackTagKeywords, fields);
    }

    /*public String getAppserver() {
        return appserver;
    }

    public void setAppserver(String appserver) {
        this.appserver = appserver;
    }*/
}
