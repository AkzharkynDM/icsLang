package com.pojoMAL;


import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.RelationshipCodes;

import java.util.List;

public class DNSServer extends AbstractHost {

    /*public DNSServer(String name, RelationshipCodes type){
        super("DNSServer",
                type,
                TypeOfHost.DNSServer,
                name);
    }*/
    public DNSServer(RelationshipCodes type, List<Field> fields){
        super("DNSServer", "DNSServer", type, AttacksDatabaseWorker.getAssetsAttackTags("DNS"), fields);
    }
}

