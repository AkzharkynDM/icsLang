package com.pojoJSONParsing;
import java.util.List;

/*
*
* Not all fields from Confdb file are used*/
public class ConfdbDeServer extends ParsedConfDBElement {
    private String account;
    private String appl_servers;
    private String normal_nodes;
    private String portNumber;
    private List<String> products;

    public ConfdbDeServer(String name,
                          String account,
                          String appl_servers,
                          String normal_nodes,
                          String portNumber,
                          List<String> products) {
        super(name);
        this.account = account;
        this.appl_servers = appl_servers;
        this.normal_nodes = normal_nodes;
        this.portNumber= portNumber;
        this.products = products;
    }

    public String getAccount() {
        return account;
    }

    public String getAppl_servers() {
        return appl_servers;
    }

    public List<String> getProducts() {
        return products;
    }

    public String getNormal_nodes() {
        return normal_nodes;
    }
    /*
    de_server{
  DATAENG{
    PRODUCTS{
      AGCBAS="NO"
      DAH="NO"
      DMS="NO"
      DNC="NO"
      ELCOM="NO"
      EOP="NO"
      EQS="NO"
      GRAD="NO"
      GSC="NO"
      ICCP="NO"
      LCKBAS="NO"
      LCKSA="NO"
      MIMIC="NO"
      NETBAS="NO"
      NETNPU="NO"
      NETNS="NO"
      NETOPF="NO"
      NETSA="NO"
      NETSCA="NO"
      NETSCD="NO"
      NETVVC="NO"
      NSO="NO"
      OTS="NO"
      PHASE="NO"
      PROT="NO"
      RCS="NO"
      TSW="NO"
    } %Global
    account="dataeng"
    appl_servers="NM_SCADA"
    databaseId="MDB"
    emergency_center="no"
    groupid="31"
    groupname="dba"
    mdb_type=""
    mmi_servers=
    normal_nodes="bldde01"
    oracle_server=""
    oradbid="MDBE"
    portNumber="1521"
    userid="48"
  } %Global
} %Global
     */
}
