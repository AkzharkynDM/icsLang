package com.pojoMAL;


import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.RelationshipCodes;

import java.util.List;

public class ActiveDirectory extends AbstractHost {

    /*public ActiveDirectory(String name, RelationshipCodes type){
        super("ActiveDirectory", type, TypeOfHost.ActiveDirectory, name);
    }*/

    public ActiveDirectory(RelationshipCodes type, List<Field> fields){
        super("ActiveDirectory", "ActiveDirectory", type, AttacksDatabaseWorker.getAssetsAttackTags("AD"), fields);
    }
}
