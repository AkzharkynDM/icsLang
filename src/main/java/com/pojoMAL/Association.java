package com.pojoMAL;


import com.constants.AssociationsConstants;
import com.constants.MAL_Syntax;
import com.constants.RelationshipCodes;
import com.helpers.MALConventionChecker;
import org.json.simple.JSONObject;

import java.util.List;

public class Association {
    private AssetSCADA rightAssetSCADA;
    private AssetSCADA leftAssetSCADA;
    private Field rightField;
    private Field leftField;
    private String rightFieldName;
    private String leftFieldName;
    private String leftAssetName;
    private String rightAssetName;
    private RelationshipCodes type;
    private String linkName;
    //private List<String> potentialAttacks;
    private List<JSONObject> potentialAttacks;

    /*public Association(AssetSCADA rightAssetSCADA, AssetSCADA leftAssetSCADA, RelationshipCodes type, String linkName) {
        this.rightAssetSCADA = rightAssetSCADA;
        this.leftAssetSCADA = leftAssetSCADA;
        this.type=type;
        this.linkName = linkName;
    }*/

   /*public Association(AssetSCADA rightAssetSCADA,
                       Field rightField,
                       AssetSCADA leftAssetSCADA,
                       Field leftField,
                       RelationshipCodes type,
                       String linkName) {
        this.rightAssetSCADA = rightAssetSCADA;
        this.rightField = rightField;
        this.leftAssetSCADA = leftAssetSCADA;
        this.leftField = leftField;
        this.type=type;
        this.linkName = linkName;
    }*/

    public Association(AssetSCADA rightAssetSCADA,
                       String rightFieldName,
                       AssetSCADA leftAssetSCADA,
                       String leftFieldName,
                       RelationshipCodes type,
                       String linkName) {
        this.rightAssetSCADA = rightAssetSCADA;
        this.rightAssetName = rightAssetSCADA.getName();
        this.rightFieldName = rightFieldName;
        this.leftAssetSCADA = leftAssetSCADA;
        this.leftAssetName = leftAssetSCADA.getName();
        this.leftFieldName = leftFieldName;
        this.type=type;
        this.linkName = linkName;
    }

    public Association(String assetFirstName, String assetSecondName, RelationshipCodes type, String linkName){
        this.leftFieldName = MALConventionChecker.determineFieldAmount(RelationshipCodes.ManyToMany,
                assetFirstName);
        this.rightFieldName = MALConventionChecker.determineFieldAmount(RelationshipCodes.ManyToMany,
                assetSecondName);
        this.linkName = linkName;
        this.type=type;
        this.leftAssetName = assetFirstName;
        this.rightAssetName = assetSecondName;
    }

    public AssetSCADA getAnotherTargetAsset(AssetSCADA firstAssetSCADA){
        if (leftAssetSCADA.equals(firstAssetSCADA)) return rightAssetSCADA;
        else return leftAssetSCADA;
    }

    /*public void setPotentialAttacks(List<String> potentialAttacks) {
        this.potentialAttacks = potentialAttacks;
    }*/

    public void setPotentialAttacks(List<JSONObject> potentialAttacks) {
        this.potentialAttacks = potentialAttacks;
    }

    /*public List<String> getPotentialAttacks() {
        return potentialAttacks;
    }*/
    public List<JSONObject> getPotentialAttacks() {
        return potentialAttacks;
    }

    public AssetSCADA getRightAssetSCADA() {
        return rightAssetSCADA;
    }

    public AssetSCADA getLeftAssetSCADA() {
        return leftAssetSCADA;
    }

    //TODO: this is too simplified model, needs working
    /*private String getLinkName(){
        Class leftAssetClass = leftAssetSCADA.getClass();
        String getLeftAssetName = leftAssetClass.getName();
        Class rightAssetClass = rightAssetSCADA.getClass();
        String getRightAssetName = rightAssetClass.getName();
        switch (getLeftAssetName) {
            case "org.mallang.scadalang.test.pojoMAL.AbstractUser":
                this.linkName = AssociationsConstants.NETWORK_ACCESS;
                break;
        }
        return this.linkName;
    }*/

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        //result.append(MALConventionChecker.checkForMalConvention(leftAssetSCADA.getName()) +"\t");
        result.append(this.leftAssetName +"\t");
        result.append("[" + MALConventionChecker.checkForMalConvention(leftFieldName).toLowerCase() + "]\t");
        if (type.equals(RelationshipCodes.ManyToMany)) {
            result.append(MAL_Syntax.ASSOCIATION_LEFT_MANY +  this.linkName + MAL_Syntax.ASSOCIATION_RIGHT_MANY + "\t");
        }
        if (type.equals(RelationshipCodes.ManyToOne)) {
            result.append(MAL_Syntax.ASSOCIATION_LEFT_MANY + this.linkName + MAL_Syntax.ASSOCIATION_RIGHT_ONE + "\t");
        }
        if (type.equals(RelationshipCodes.OneToMany)) {
            result.append(MAL_Syntax.ASSOCIATION_LEFT_ONE + this.linkName  + MAL_Syntax.ASSOCIATION_RIGHT_MANY + "\t");
        }
        if (type.equals(RelationshipCodes.OneToOne)) {
            result.append(MAL_Syntax.ASSOCIATION_LEFT_ONE + this.linkName  + MAL_Syntax.ASSOCIATION_RIGHT_ONE + "\t");
        }
        result.append("[" + MALConventionChecker.checkForMalConvention(rightFieldName).toLowerCase() + "]\t");
        //result.append(MALConventionChecker.checkForMalConvention(rightAssetSCADA.getName()) +"\n");
        result.append(this.rightAssetName +"\n");
        return result.toString();
    }
}
