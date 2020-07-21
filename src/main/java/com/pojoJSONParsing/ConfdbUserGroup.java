package com.pojoJSONParsing;
public class ConfdbUserGroup extends ParsedConfDBElement {
    private String label;
    private String gid;

    public ConfdbUserGroup(String name, String label, String gid){
        super(name);
        this.label = label;
        this.gid = gid;
    }

    @Override
    public String toString() {
        return "ConfdbUserGroup{" +
                "name='" + super.getName() + '\'' +
                "label='" + label + '\'' +
                ", gid='" + gid + '\'' +
                '}';
    }

    public String getLabel() {
        return label;
    }
}
