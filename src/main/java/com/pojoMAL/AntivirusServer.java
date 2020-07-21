package com.pojoMAL;

import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.RelationshipCodes;

import java.util.List;

public class AntivirusServer extends AbstractHost {

    public AntivirusServer(RelationshipCodes type, List<Field> fields){
        super("Antivirus", "Antivirus",type, AttacksDatabaseWorker.getAssetsAttackTags("antivirus"), fields);
    }
}

