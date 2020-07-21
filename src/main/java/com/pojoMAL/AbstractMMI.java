package com.pojoMAL;

/*
* A Human-Machine Interface (HMI) is a user interface or dashboard that connects a person to a machine, system, or device.
* While the term can technically be applied to any screen that allows a user to interact with a device,
* HMI is most commonly used in the context of an industrial process.

Although HMI is the most common term for this technology, it is sometimes referred to as
* Man-Machine Interface (MMI), Operator Interface Terminal (OIT),
* Local Operator Interface (LOI), or Operator Terminal (OT).
* HMI and Graphical User Interface (GUI) are similar but not synonymous:
* GUIs are often leveraged within HMIs for visualization capabilities.*/



import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.RelationshipCodes;

import java.util.List;

public class AbstractMMI extends AssetSCADA {
    //private Map<String, String> accountMMIRelation = new HashMap<>();
    //private Map<String, String> appServersMMIRelation = new HashMap<>();

    public AbstractMMI(String name,
                       RelationshipCodes type,
                       List<Field> fields /*,
                       Map<String, String> accountMMIRelation,
                       Map<String, String> appServersMMIRelation*/){
        super(name,
                name,
                type,
                AttacksDatabaseWorker.getAssetsAttackTags("MMI"),
                fields/*,
                (Set) accountMMIRelation.keySet()*/);
        //this.accountMMIRelation = accountMMIRelation;
        //this.appServersMMIRelation = appServersMMIRelation;
    }

    /*public AbstractMMI(String name, RelationshipCodes type, String fieldName){
        super(name, type, AttacksDatabaseWorker.getAssetsAttackTags("MMI"), fieldName);
    }

    public AbstractMMI(String name, RelationshipCodes type, List<Field> fields){
        super(name, type, AttacksDatabaseWorker.getAssetsAttackTags("MMI"), fields);
    }

    public AbstractMMI(String name, RelationshipCodes type, String newFieldName, String relatedAccount){
        super(name, type, AttacksDatabaseWorker.getAssetsAttackTags("MMI"), newFieldName);
        accountMMIRelation.put(newFieldName, relatedAccount);
    }

    public String getRelatedAccount(String fieldname) {
        return accountMMIRelation.get(fieldname);
    }

    public String getRelatedAppServers(String fieldname) {
        return appServersMMIRelation.get(fieldname);
    }*/

}
