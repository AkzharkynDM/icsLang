package com.pojoMAL;

import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.RelationshipCodes;
import java.util.List;

public class NTPServer extends AbstractHost {

    /*public NTPServer(String name, RelationshipCodes type){
        super("NTPServer", type, TypeOfHost.NTPServer, name);
    }*/

    public NTPServer(RelationshipCodes type, List<Field> fields){
        super("NTPServer", "NTPServer", type, AttacksDatabaseWorker.getAssetsAttackTags("NTP"), fields);
    }
}

