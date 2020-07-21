package com.pojoMAL;

import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.AssetTagName;
import com.constants.AssetsConstants;
import com.constants.RelationshipCodes;

import java.util.List;

public class ExternalSCADA extends AssetSCADA{
    public ExternalSCADA(RelationshipCodes type, List<Field> fields) {
        super(AssetsConstants.EXTERNAL_SCADA,
                AssetTagName.EXTERNAL_SCADA,
                type,
                AttacksDatabaseWorker.getAssetsAttackTags("externalSCADA"),
                fields);
    }
}
