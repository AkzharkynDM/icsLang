package com.pojoMAL;

import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.AssetTagName;
import com.constants.AssetsConstants;
import com.constants.RelationshipCodes;

import java.util.List;

public class AccessServer extends AssetSCADA {
    public AccessServer(RelationshipCodes type, List<Field> fields){
        super(AssetsConstants.ACCESS_SERVER,
                AssetTagName.ACCESS_SERVER,
                type,
                AttacksDatabaseWorker.getAssetsAttackTags("accessServer"),
                fields);
    }
}
