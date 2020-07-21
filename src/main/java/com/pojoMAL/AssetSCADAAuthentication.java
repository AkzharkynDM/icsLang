package com.pojoMAL;


import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.RelationshipCodes;

import java.util.ArrayList;
import java.util.List;

public class AssetSCADAAuthentication extends AssetSCADA {
    private List<String> kerberos;
    private String mode;

    public AssetSCADAAuthentication(String mode,
                                    RelationshipCodes relationshipCodes,
                                    String kerberosString,
                                    List<Field> fields){
        super(mode, mode, relationshipCodes, AttacksDatabaseWorker.getAssetsAttackTags("kerberos"), fields);
        this.kerberos = new ArrayList();
        kerberosString = kerberosString.replace("]", "");
        kerberosString = kerberosString.replace("[", "");
        String[] parts = kerberosString.split(" ");
        for (int i=0; i<parts.length; i++) {
            this.kerberos.add(parts[i]);
        }
        this.mode = mode;
    }

    public List<String> getKerberos() {
        return kerberos;
    }

    public String getMode() {
        return mode;
    }
}
