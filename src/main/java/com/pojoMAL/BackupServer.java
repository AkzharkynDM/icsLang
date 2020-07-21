package com.pojoMAL;

import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.RelationshipCodes;

import java.util.List;

public class BackupServer extends AssetSCADA {

    public BackupServer(String name, RelationshipCodes type, List<Field> fields) {
        super(name, name, type, AttacksDatabaseWorker.getAssetsAttackTags("backup_server"), fields);
    }
}

