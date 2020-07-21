package com.pojoJSONParsing;

import com.constants.JSONParsingConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfdbAppServer extends ParsedConfDBElement {
    private String account;
    private Object additional_nodes;
    private List<String> applications;
    private ConfdbAuthentication confdbAuthentication;
    private ConfdbProduct confdbProduct;
    private String dbi;
    private String iccpopc_nodes;
    private List<String> normal_nodes;
    private List<String> port_numbers;

    public ConfdbAppServer(String category_name,
                           String account,
                           Object additional_nodes,
                           Object applications,
                           ConfdbAuthentication confdbAuthentication,
                           String iccpopc_nodes,
                           String normal_nodesString,
                           String dbi) {
        super(category_name);
        this.account = account;
        this.additional_nodes = additional_nodes;
        this.applications = new ArrayList<>();
        if (((String) applications).equals(JSONParsingConstants.NOVALUE)){

        }
        if (((String) applications).length() == 0){

        }
            else {
            String[] partsApplications = ((String) applications).substring(1, ((String) applications).length()-1).split(",");
            for (int i = 0; i < partsApplications.length; i++) {
                this.applications.add(partsApplications[i]);
            }
        }
        this.confdbAuthentication = confdbAuthentication;
        this.dbi = dbi;
        this.iccpopc_nodes = iccpopc_nodes;
        this.normal_nodes = new ArrayList<>();
        String[] partsNormalNodes = normal_nodesString.substring(1, normal_nodesString.length()-1).split(", ");
        for (int i=0; i<partsNormalNodes.length; i++) {
            this.normal_nodes.add(partsNormalNodes[i]);
        }
    }

    public String getAccount() {
        return account;
    }

    public List<String> getApplications() {
        return applications;
    }

    public List<String> getNormal_nodes() {
        return normal_nodes;
    }

    public void setConfdbProduct (ConfdbProduct confdbProduct){
        this.confdbProduct = confdbProduct;
    }

    public ConfdbAuthentication getConfdbAuthentication(){
        return confdbAuthentication;
    }

    public String getIccpopc_nodes() {
        return iccpopc_nodes;
    }

    public String getDbi() {
        return dbi;
    }
//I consider this information useless for now
    /*avanti_subscription_class{
        NM_SCADA="1" "persistent" "unicast"
    }


    dbi="PG"
    emergency_center="no"
    event_handling="default"
    ext_clock_device="NL"
    ext_clock_dst="1"
    ext_clock_port_number="10001"
    ext_clock_subtype="0"
    ext_clock_time_source="0"
    ext_clock_type="Meinberg"
    external_oracle="0"
    iaa_timeout="10"
    iccpopc_account=""
    iccpopc_nodes=""
    iccpopc_seczone_account="IFEUser"
    kit_install="no"
    locale="en_US.utf8"
    master="PG_DB"
    normal_nodes="bldas01" "bldas02"
    options="boot_start" "start_alarm"
    ora_port="1521"
    ora_service="PRIMARY"
    oracle_edition="Enterprise"
    port_numbers="6700" "-" "6949"
    redundancy="dual"
    report_cycle="10"
    report_timeout="120"
    server_type="ORACLE"
    site_connect_timeout="5"
    site_reply_timeout="5"
    study_database_directory=""
    study_db_templates=
    supervised_by="NM_SCADA"
    supervised_by_event_handling="default"
    udwexplorer_nodes=""*/
}
