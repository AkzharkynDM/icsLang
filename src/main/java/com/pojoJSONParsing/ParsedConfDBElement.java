package com.pojoJSONParsing;
public abstract class ParsedConfDBElement {
    private String category_name;

    public ParsedConfDBElement(String category_name){
        this.category_name=category_name;
    }

    public String getName() {
        return category_name;
    }

    public void setName(String category_name) {
        this.category_name = category_name;
    }
}
