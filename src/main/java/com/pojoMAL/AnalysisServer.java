package com.pojoMAL;


import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.RelationshipCodes;

import java.util.List;

public class AnalysisServer extends AbstractHost{
    //private List<String> products;

    public AnalysisServer(String name, RelationshipCodes type, List<Field> fields){
        super(name, name, type, AttacksDatabaseWorker.getAssetsAttackTags(name), fields);
        //products = new ArrayList<>();
    }

    /*public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }*/
}
