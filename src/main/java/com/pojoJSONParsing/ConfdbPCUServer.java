package com.pojoJSONParsing;
import com.constants.ClassesInProject;
import com.constants.JSONParsingConstants;

import java.util.ArrayList;
import java.util.List;

public class ConfdbPCUServer extends ParsedConfDBElement {
    private String account; //="run400"
    private String group; //="PCUUsers"
    private List<String> nodes; //nodes="bsite-cpcu01" "bsite-cpcu02" "bsite-cpcu03" "bsite-cpcu04" "asite-pcu01" "asite-pcu02" "asite-pcu03" "asite-pcu04" "asite-qpcu01"

   /* public ConfdbPCUServer(String name, List<String> nodes){
        super(name);
        this.nodes = nodes;
    }*/

    public ConfdbPCUServer(String name,  String account, String group, List<String> nodes){
        super(name);
        this.account = account;
        this.group = group;
        this.nodes = nodes;
    }

    /*public ConfdbPCUServer(String name,  String account, String group, Object nodesObject){
        super(name);
        this.account = account;
        this.group = group;
        if (nodes.getClass().getName().equals(ClassesInProject.STRING_CLASS)){
            this.nodes = new ArrayList<>();
            this.nodes.add((String) nodesObject);
        }
        if (nodes.getClass().getSuperclass().getName().equals(ClassesInProject.LIST_CLASS)){
            this.nodes = (ArrayList) nodesObject;
        }
    }*/

    public List<String> getNodes() {
        return nodes;
    }

    public String getAccount() {
        return account;
    }

    public String getGroup() {
        return group;
    }
}
/*
pcu_server{ in confdb
  PCU{
    account="run400"
    group="PCUUsers"
    nodes=
  } %Global
} %Global

pcu_server{ in confdb_postgre and confdb_oracle
  PCU{
    nodes="bsite-cpcu01" "bsite-cpcu02" "bsite-cpcu03" "bsite-cpcu04" "asite-pcu01" "asite-pcu02" "asite-pcu03" "asite-pcu04" "asite-qpcu01"
  } %Global
} %Global
 */