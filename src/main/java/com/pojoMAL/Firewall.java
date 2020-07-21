package com.pojoMAL;


import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.AssetTagName;
import com.constants.RelationshipCodes;

import java.util.List;

public class Firewall extends AbstractHost {
    public Firewall(String name, RelationshipCodes type, List<Field> fields){
        super(name, AssetTagName.FIREWALL, type, AttacksDatabaseWorker.getAssetsAttackTags("Firewall"), fields);
        this.modelerInfo = "An organisation that cannot afford a hardware firewall device uses an alternative i.e implementing firewall features on Cisco IOS router by using CBAC or by using Zone-based firewall.CBAC is a predecessor to Zone-based firewall";
        this.userInfo = "A firewall is a network security system which monitors and takes actions on the ingoing or outgoing packets based on the defined rules. It can be a hardware device or a software";
    }
}
