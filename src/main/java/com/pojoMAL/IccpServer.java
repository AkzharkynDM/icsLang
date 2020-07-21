package com.pojoMAL;

import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.RelationshipCodes;

import java.util.List;

public class IccpServer extends AbstractHost {
    public IccpServer(String name, RelationshipCodes type, List<Field> fields){
        super(name, name, type, AttacksDatabaseWorker.getAssetsAttackTags("iccpserver"), fields);
        this.userInfo = "ICCP has an access to Internet and is entry point to Internet";
    }
}
