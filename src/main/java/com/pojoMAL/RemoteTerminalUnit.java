package com.pojoMAL;

import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.AssetTagName;
import com.constants.AssetsConstants;
import com.constants.RelationshipCodes;

import java.util.List;

public class RemoteTerminalUnit extends AssetSCADA{
    public RemoteTerminalUnit(RelationshipCodes type, List<Field> fields){
        super(AssetsConstants.REMOTE_TERMINAL_UNIT,
                AssetTagName.REMOTE_TERMINAL_UNIT,
                type,
                AttacksDatabaseWorker.getAssetsAttackTags("rtu"),
                fields);
    }
}
