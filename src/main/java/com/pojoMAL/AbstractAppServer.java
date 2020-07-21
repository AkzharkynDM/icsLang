package com.pojoMAL;


import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.RelationshipCodes;

import java.util.List;

public class AbstractAppServer extends AssetSCADA {
    //private Map<String, String> accountAppserverRelation = new HashMap<>();
    //private Map<String, List<String>> appserverProductRelation = new HashMap<>();
    //private Map<String, String> iccpAppserverRelation = new HashMap<>();

    public AbstractAppServer(String name,
                             RelationshipCodes type,
                             List<Field> fields/*,
                             Map<String, String> accountAppserverRelation,
                             Map<String, List<String>> appserverProductRelation,
                             Map<String, String> iccpAppserverRelation*/){
        super(name, name, type, AttacksDatabaseWorker.getAssetsAttackTags("appServer"), fields);
        //this.accountAppserverRelation = accountAppserverRelation;
        //this.appserverProductRelation = appserverProductRelation;
        //this.iccpAppserverRelation = iccpAppserverRelation;
    }

    /*public String getAccount(String appServerName) {
        return accountAppserverRelation.get(appServerName);
    }

    public String getIccpopc_nodes(String appServerName) {
        return iccpAppserverRelation.get(appServerName);
    }

    public List<String> getApplications(String appServerName) {
        return appserverProductRelation.get(appServerName);
    }*/
}
