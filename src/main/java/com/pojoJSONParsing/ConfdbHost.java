package com.pojoJSONParsing;

public class ConfdbHost extends ParsedConfDBElement {
    //private DevSupportInfo DEVSUPPORT;
    private String EnetAddr;
    private String IFCONFIG;
    private String IPAddr;
    private String LAN;
    private String NETDEV;
    private String alias;
    private String split_site;
    private String platform;

    public ConfdbHost(String name, /*DevSupportInfo DEVSUPPORT,*/
                      String enetAddr,
                      String IFCONFIG,
                      String IPAddr,
                      String LAN,
                      String NETDEV,
                      String alias,
                      String split_site,
                      String platform) {
        super(name);
        //this.DEVSUPPORT = DEVSUPPORT;
        this.EnetAddr = enetAddr;
        this.IFCONFIG = IFCONFIG;
        this.IPAddr = IPAddr;
        this.LAN = LAN;
        this.NETDEV = NETDEV;
        this.alias = alias;
        this.split_site = split_site;
        this.platform = platform;
    }

    public String getLAN() {
        return LAN;
    }

    public String getPlatform() {
        return platform;
    }

    @Override
    public String toString() {
        return "ConfdbHost{" +
                "name='" + super.getName() + '\'' +
                //", DEVSUPPORT=" + DEVSUPPORT +
                ", EnetAddr='" + EnetAddr + '\'' +
                ", IFCONFIG='" + IFCONFIG + '\'' +
                ", IPAddr='" + IPAddr + '\'' +
                ", LAN='" + LAN + '\'' +
                ", NETDEV='" + NETDEV + '\'' +
                ", alias='" + alias + '\'' +
                ", split_site='" + split_site + '\'' +
                '}';
    }
}
