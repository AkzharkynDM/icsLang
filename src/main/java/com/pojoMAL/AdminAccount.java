package com.pojoMAL;


import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.RelationshipCodes;

import java.util.List;

public class AdminAccount extends AssetSCADA {

    public AdminAccount(String name, String tag, RelationshipCodes type, String nameForAttackTag, List<Field> fields){
        super(name, tag, type, AttacksDatabaseWorker.getAssetsAttackTags(nameForAttackTag), fields);
    }
}
