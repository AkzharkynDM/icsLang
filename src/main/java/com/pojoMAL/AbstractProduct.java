package com.pojoMAL;


import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.RelationshipCodes;

import java.util.List;

public class AbstractProduct extends AssetSCADA {
    private String name;
    private RelationshipCodes type;

    public AbstractProduct(String name, RelationshipCodes type, List<Field> fields){
        super(name, name, type, AttacksDatabaseWorker.getAssetsAttackTags("product"), fields);
    }
}
