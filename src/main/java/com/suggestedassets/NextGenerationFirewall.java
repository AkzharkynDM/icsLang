package com.suggestedassets;

import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.RelationshipCodes;
import com.pojoMAL.AssetSCADA;
import com.pojoMAL.Field;

import java.util.List;

public class NextGenerationFirewall extends AssetSCADA {
    public NextGenerationFirewall(RelationshipCodes type, List<Field> fields){
        super("NextGenerationFirewall", "NextGenerationFirewall", type, AttacksDatabaseWorker.getAssetsAttackTags("NGFW"), fields);
        this.userInfo = " Maintaining features of stateful firewalls such as packet filtering, VPN support, network monitoring, and IP mapping features, NGFWs also possess deeper inspection capabilities that give them a superior ability to identify attacks, malware, and other threats. ";

    }
}
