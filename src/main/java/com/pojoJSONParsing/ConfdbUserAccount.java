package com.pojoJSONParsing;
import java.util.ArrayList;

public class ConfdbUserAccount extends ParsedConfDBElement {
    private ArrayList groups;
    private String home;
    private String primaryGroup;
    private String shell;
    private String uid;

    public ConfdbUserAccount(String name, ArrayList groups, String home, String primaryGroup, String shell, String uid) {
        super(name);
        this.groups = groups;
        this.home = home;
        this.primaryGroup = primaryGroup;
        this.shell = shell;
        this.uid = uid;
    }

    public String getPrimaryGroup() {
        return primaryGroup;
    }
}
