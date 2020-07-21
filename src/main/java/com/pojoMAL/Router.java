package com.pojoMAL;

import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.RelationshipCodes;

import java.util.List;

public class Router extends AssetSCADA {

    public Router(RelationshipCodes type, List<Field> fields){
        super("Router", "Router", type, AttacksDatabaseWorker.getAssetsAttackTags("router"), fields);

    }
}
