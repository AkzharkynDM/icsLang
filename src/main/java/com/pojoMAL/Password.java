package com.pojoMAL;

import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.RelationshipCodes;

import java.util.List;

public class Password extends AssetSCADA {
    public Password(String name, RelationshipCodes type, List<Field> fields){
        super(name, name, type, AttacksDatabaseWorker.getAssetsAttackTags("password"), fields);
    }
}
