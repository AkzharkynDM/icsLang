package com.pojoMAL;

import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.RelationshipCodes;

import java.util.List;

public class WindowsServer extends AbstractHost {
    public WindowsServer(RelationshipCodes type, List<Field> fields) {
        super("WindowsServer", "WindowsServer", type, AttacksDatabaseWorker.getAssetsAttackTags("WindowsHost"), fields);
    }
}
