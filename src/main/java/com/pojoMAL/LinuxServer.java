package com.pojoMAL;

import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.RelationshipCodes;

import java.util.List;

public class LinuxServer extends AbstractHost {
    public LinuxServer(RelationshipCodes type, List<Field> fields) {
        super("LinuxServer", "LinuxServer", type, AttacksDatabaseWorker.getAssetsAttackTags("LinuxHost"), fields);
    }
}
