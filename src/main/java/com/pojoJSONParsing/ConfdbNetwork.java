package com.pojoJSONParsing;
public class ConfdbNetwork extends ParsedConfDBElement {
    private String IPAddr;
    private String domain;
    private String netmask;
    private String split_site;
    private String suffix;

    public ConfdbNetwork(String name, String IPAddr, String domain, String netmask, String split_site, String suffix) {
        super(name);
        this.IPAddr = IPAddr;
        this.domain = domain;
        this.netmask = netmask;
        this.split_site = split_site;
        this.suffix = suffix;
    }
}
