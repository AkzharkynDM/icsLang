package com.pojoMAL;

import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.AssetsConstants;
import com.constants.RelationshipCodes;

import java.util.List;

public class PostgreDatabase extends AbstractDatabase {
    public PostgreDatabase(RelationshipCodes type, List<Field> fields){
        super(AssetsConstants.POSTGRE_DATABASE, type, AttacksDatabaseWorker.getAssetsAttackTags("Postgre"), fields);
    }
}
