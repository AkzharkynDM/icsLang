package com.pojoJSONParsing;

import java.util.List;

public class ConfdbBackupServer extends ParsedConfDBElement {
    private List<String> nodes;

    public ConfdbBackupServer(String name, List<String> nodes){
        super(name);
        this.nodes = nodes;
    }
}
