package com.pojoMAL;


import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.RelationshipCodes;

import java.util.List;

public class AbstractNetwork extends AssetSCADA {

    public AbstractNetwork(String name, String tag, RelationshipCodes type, List<Field> fields){
        //super(name, tag, type, AttacksDatabaseWorker.getAssetsAttackTags("network"), fields);
        super(name, tag, type, AttacksDatabaseWorker.getAssetsAttackTags(tag), fields);
    }
}
