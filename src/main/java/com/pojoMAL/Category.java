package com.pojoMAL;

import com.constants.MAL_Syntax;
import java.util.List;

public class Category {
    private List<AssetSCADA> assetSCADAs;
    private String name;

    public Category(String name, List<AssetSCADA> assetSCADAS) {
        this.name = name;
        this.assetSCADAs = assetSCADAS;
    }

    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append(name + "{\n");
        if (assetSCADAs != null) {
            for (AssetSCADA assetSCADA : assetSCADAs) {
                result.append(assetSCADA.toString());
            }
        }
        result.append("}\n");
        return result.toString();
    }
}
