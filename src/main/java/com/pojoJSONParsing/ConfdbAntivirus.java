package com.pojoJSONParsing;
import java.util.List;

public class ConfdbAntivirus extends ParsedConfDBElement {
    private List<String> nodes;

    public ConfdbAntivirus(String name, List<String> nodes) {
        super(name);
        this.nodes = nodes;
    }

    public List<String> getNodes() {
        return nodes;
    }
}
