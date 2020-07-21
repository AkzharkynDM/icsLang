package com.pojoJSONParsing;
public class ConfdbMMI extends ParsedConfDBElement {
    private String account;
    private String appl_servers;
    private AudibleAlarmInfo audible_alarm;
    private String console;
    private String display_node;
    private String dynamic_console_number;
    private String groupname;
    private String min_rdb_vdus;
    private String normal_nodes;
    private String userid;
    private String vdus;

    public ConfdbMMI(String category_name,
                     String account,
                     String appl_servers,
                     AudibleAlarmInfo audible_alarm,
                     String console,
                     String display_node,
                     String dynamic_console_number,
                     String groupname,
                     String min_rdb_vdus,
                     String normal_nodes,
                     String userid,
                     String vdus) {
        super(category_name);
        this.account = account;
        this.appl_servers = appl_servers;
        this.audible_alarm = audible_alarm;
        this.console = console;
        this.display_node = display_node;
        this.dynamic_console_number = dynamic_console_number;
        this.groupname = groupname;
        this.min_rdb_vdus = min_rdb_vdus;
        this.normal_nodes = normal_nodes;
        this.userid = userid;
        this.vdus = vdus;
    }

    public String getAccount() {
        return account;
    }

    public String getAppl_servers() {
        return appl_servers;
    }
}
