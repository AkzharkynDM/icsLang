package com.pojoJSONParsing;
import java.util.List;

public class ConfdbPseServer extends ParsedConfDBElement {
    private String account;
    private String appl_servers;
    private String nodes;
    private String workplace_ports;

    public ConfdbPseServer(String name,
                           String account,
                           String appl_servers,
                           String nodes,
                           String workplace_ports){
        super(name);
        this.account = account;
        this.appl_servers = appl_servers;
        this.nodes = nodes;
        this.workplace_ports= workplace_ports;
    }
}
