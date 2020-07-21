package com.pojoMAL;

import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.AssetsConstants;
import com.constants.RelationshipCodes;

import java.util.List;

public class RealTimeDatabase extends AbstractDatabase {
    public RealTimeDatabase(RelationshipCodes type, List<Field> fields){
        super(AssetsConstants.REALTIME_DATABASE, type, AttacksDatabaseWorker.getAssetsAttackTags("Avanti"), fields);
    }
}
