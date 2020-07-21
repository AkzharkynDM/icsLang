package com.pojoMAL;

import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.AssetsConstants;
import com.constants.RelationshipCodes;

import java.util.List;

public class OracleDatabase extends AbstractDatabase {
    public OracleDatabase(RelationshipCodes type, List<Field> fields){
        super(AssetsConstants.ORACLE_DATABASE, type, AttacksDatabaseWorker.getAssetsAttackTags("Oracle"), fields);
    }

}
