package com.pojoMAL;

import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.RelationshipCodes;

import java.util.List;

public class RedhatServer extends AbstractHost {
    public RedhatServer(RelationshipCodes type, List<Field> fields) {
        super("RedhatServer", "RedhatServer", type, AttacksDatabaseWorker.getAssetsAttackTags("RedhatHost"), fields);
    }
}
