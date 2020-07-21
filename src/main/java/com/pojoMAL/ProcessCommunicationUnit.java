package com.pojoMAL;

import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.AssetTagName;
import com.constants.AssetsConstants;
import com.constants.RelationshipCodes;

import java.util.List;

public class ProcessCommunicationUnit extends AbstractHost {
    public ProcessCommunicationUnit(RelationshipCodes type, List<Field> fields){
        super(AssetsConstants.PROCESS_COMMUNICATION_UNIT,
                AssetTagName.PROCESS_COMMUNICATION_UNIT,
                type,
                AttacksDatabaseWorker.getAssetsAttackTags("pcu"),
                fields);
    }
}
