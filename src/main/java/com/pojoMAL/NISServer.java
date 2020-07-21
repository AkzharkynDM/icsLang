package com.pojoMAL;

import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.RelationshipCodes;
import java.util.List;

public class NISServer extends AbstractHost {

    /*public NISServer(String name, RelationshipCodes type){
        super("NISServer", type, TypeOfHost.NISServer, name);
    }*/

    public NISServer(RelationshipCodes type, List<Field> fields){
        super("NISServer", "NISServer", type, AttacksDatabaseWorker.getAssetsAttackTags("NIS"), fields);
    }
}

