package com.helpers;
import com.constants.ClassesInProject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import com.constants.ConfdbElementNames;
import com.constants.Filenames;
import com.constants.JSONParsingConstants;
import com.pojoJSONParsing.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
//import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JSONParserToConfDBElement {
    private List<ConfdbHost> confdbHosts;
    private List<ConfdbNetwork> confdbNetworks;
    private List<ConfdbUserAccount> confdbUserAccounts;
    private List<ConfdbUserGroup> confdbUserGroups;
    private List<ConfdbProduct> confdbProducts;
    private List<ConfdbAppServer> confdbAppServers;
    private List<ConfdbDeServer> confdbDeServers;
    private List<ConfdbPseServer> confdbPseServers;
    private List<ConfdbMMI> confdbMMIs;
    private List<ConfdbPCUServer> confdbPCUServers;
    private Map<String, Object> confdbNetworkServices;
    private List<ConfdbAntivirus> confdbAntiviri;
    private List<ConfdbBackupServer> confdbBackupServers;
    private Map<String, String> routingInfo;

    public JSONParserToConfDBElement() {
        confdbHosts = new ArrayList();
        confdbNetworks = new ArrayList();
        confdbUserAccounts = new ArrayList();
        confdbUserGroups = new ArrayList<>();
        confdbProducts = new ArrayList<>();
        confdbAppServers = new ArrayList<>();
        confdbDeServers = new ArrayList<>();
        confdbPseServers = new ArrayList<>();
        confdbMMIs = new ArrayList<>();
        confdbPCUServers = new ArrayList<>();
        confdbNetworkServices = new HashMap<>();
        confdbAntiviri = new ArrayList<>();
        confdbBackupServers = new ArrayList<>();
        routingInfo = new HashMap<>();
    }

    public void readFromConfdbJson() {
        try {
            JSONObject jsonObject = new ObjectMapper().readValue(new File(Filenames.pathToConfdbJsonFile), JSONObject.class);
            LinkedHashMap<String, Object> jsonArray = (LinkedHashMap<String, Object>) jsonObject.get(JSONParsingConstants.ARRAY);
            if (jsonArray.containsKey(ConfdbElementNames.HOST)) {
                Map rawConfdbHosts = (Map) jsonArray.get(ConfdbElementNames.HOST);
                Iterator<Map.Entry<String, Map>> itr = rawConfdbHosts.entrySet().iterator();
                while (itr.hasNext()) {
                    Map.Entry<String, Map> entry = itr.next();
                    //assertNotNull(entry);
                    //System.out.println("Key = " + entry.getKey() +
                    //        ", Value as another Map = " + entry.getValue().toString());
                    //System.out.println(entry.getValue().get(ConfdbElementNames.DEVSUPPORT));
                    /*String corePath = entry.getValue().get(ConfdbElementNames.DEVSUPPORT).toString();
                    String on = entry.getValue().get(ConfdbElementNames.DEVSUPPORT).toString();
                    String usePid = entry.getValue().get(ConfdbElementNames.DEVSUPPORT).toString();
                    String useProcName = entry.getValue().get(ConfdbElementNames.DEVSUPPORT).toString();
                    DevSupportInfo devSupportInfo = new DevSupportInfo(corePath, on, usePid, useProcName);*/
                    //System.out.println(devSupportInfo);
                    String EnetAddr = entry.getValue().get(ConfdbElementNames.ENETADDR).toString();
                    String IFCONFIG = entry.getValue().get(ConfdbElementNames.IFCONFIG).toString();
                    String IPAddr = entry.getValue().get(ConfdbElementNames.IPADDR).toString();
                    String LAN = entry.getValue().get(ConfdbElementNames.LAN).toString();
                    String NETDEV = entry.getValue().get(ConfdbElementNames.NETDEV).toString();
                    String alias = entry.getValue().get(ConfdbElementNames.ALIAS).toString();
                    String split_site = entry.getValue().get(ConfdbElementNames.SPLIT_SIDE).toString();
                    String platform = JSONParsingConstants.NOVALUE;
                    if (entry.getValue().get(ConfdbElementNames.PLATFORM) != null) {
                        platform = entry.getValue().get(ConfdbElementNames.PLATFORM).toString();
                    }
                    ConfdbHost confdbHost = new ConfdbHost(entry.getKey(),
                            //devSupportInfo,
                            EnetAddr,
                            IFCONFIG,
                            IPAddr,
                            LAN,
                            NETDEV,
                            alias,
                            split_site,
                            platform);
                    this.confdbHosts.add(confdbHost);
                }
            }

            if (jsonArray.containsKey(ConfdbElementNames.NETWORK)) {
                Map rawConfdbNetworks = (Map) jsonArray.get(ConfdbElementNames.NETWORK);
                Iterator<Map.Entry<String, Map>> itr = rawConfdbNetworks.entrySet().iterator();
                while (itr.hasNext()) {
                    Map.Entry<String, Map> entry = itr.next();
                    //assertNotNull(entry);
                    String IPAddr = entry.getValue().get(ConfdbElementNames.IPADDR).toString();
                    String domain = entry.getValue().get(ConfdbElementNames.DOMAIN).toString();
                    String netmask = entry.getValue().get(ConfdbElementNames.NETMASK).toString();
                    String split_site = entry.getValue().get(ConfdbElementNames.SPLIT_SIDE).toString();
                    String suffix = entry.getValue().get(ConfdbElementNames.SUFFIX).toString();

                    ConfdbNetwork confdbNetwork = new ConfdbNetwork(entry.getKey(),
                            IPAddr,
                            domain,
                            netmask,
                            split_site,
                            suffix);
                    this.confdbNetworks.add(confdbNetwork);
                }
            }

            if (jsonArray.containsKey(ConfdbElementNames.USERACCOUNTS)) {
                Map rawConfdbUserAccounts = (Map) jsonArray.get(ConfdbElementNames.USERACCOUNTS);
                Iterator<Map.Entry<String, Map>> itr = rawConfdbUserAccounts.entrySet().iterator();
                while (itr.hasNext()) {
                    Map.Entry<String, Map> entry = itr.next();
                    /*if (entry.getKey().equals(ConfdbElementNames.UID)) { //TODO: I skipped the UID
                        continue;
                    }*/
                    //System.out.println(entry.getValue().entrySet());
                    //System.out.println(entry.getValue().getClass());
                    //System.out.println(entry.getValue().getClass().getName());
                    /*if (entry.getValue().entrySet().getClass().getName().equals(ClassesInProject.STRING_CLASS)){
                        continue;
                    }*/
                    /*if (((String) entry.getValue().toString()).startsWith("\"")){
                        continue;
                    }*/
                    try {
                        ArrayList groups = null;
                        if (entry.getValue().get(ConfdbElementNames.GROUPS) != null) {
                            groups = (ArrayList) entry.getValue().get(ConfdbElementNames.GROUPS);
                        }
                        String home = null;
                        if (entry.getValue().get(ConfdbElementNames.HOME) != null) {
                            home = entry.getValue().get(ConfdbElementNames.HOME).toString();
                        }
                        String primaryGroup = null;
                        if (entry.getValue().get(ConfdbElementNames.PRIMARYGROUP) != null) {
                            primaryGroup = entry.getValue().get(ConfdbElementNames.PRIMARYGROUP).toString();
                        }
                        String shell = null;
                        if (entry.getValue().get(ConfdbElementNames.SHELL) != null) {
                            shell = entry.getValue().get(ConfdbElementNames.SHELL).toString();
                        }
                        String uid = null;
                        if (entry.getValue().get(ConfdbElementNames.UID) != null) {
                            uid = entry.getValue().get(ConfdbElementNames.UID).toString();
                        }

                        if (groups != null && home != null && primaryGroup != null && shell != null && uid != null) {
                            ConfdbUserAccount confdbUserAccount = new ConfdbUserAccount(entry.getKey(),
                                    groups,
                                    home,
                                    primaryGroup,
                                    shell,
                                    uid);
                            this.confdbUserAccounts.add(confdbUserAccount);
                        }
                    } catch (ClassCastException e) {
                    }
                }
            }

            if (jsonArray.containsKey(ConfdbElementNames.USERGROUPS)) {
                String label = "";
                String gid = "";
                String name = "";
                Map rawConfdbUserGroups = (Map) jsonArray.get(ConfdbElementNames.USERGROUPS);
                Iterator<Map.Entry<String, String>> itr1 = rawConfdbUserGroups.entrySet().iterator();
                Iterator<Map.Entry<String, Map>> itr2 = rawConfdbUserGroups.entrySet().iterator();
                while (itr1.hasNext()) {
                    Map.Entry<String, String> entry = itr1.next();
                    if (entry.getKey().contains(ConfdbElementNames.GROUP)) {
                        label = entry.getValue();
                        name = entry.getKey();

                        while (itr2.hasNext()) {
                            Map.Entry<String, Map> childEntry = itr2.next();
                            if (!childEntry.getKey().contains(ConfdbElementNames.GROUP)
                                    && childEntry.getKey().equals(label)) {
                                gid = childEntry.getValue().get(ConfdbElementNames.GID).toString();
                            }
                        }
                        ConfdbUserGroup confdbUserGroup = new ConfdbUserGroup(name,
                                label,
                                gid);
                        this.confdbUserGroups.add(confdbUserGroup);
                    }
                }


                if (jsonArray.containsKey(ConfdbElementNames.APPL_SERVER)) {
                    Map rawConfdbAppServers = (Map) jsonArray.get(ConfdbElementNames.APPL_SERVER);
                    Iterator<Map.Entry<String, Map>> itr = rawConfdbAppServers.entrySet().iterator();
                    while (itr.hasNext()) {
                        Map.Entry<String, Map> entry = itr.next();
                        //assertNotNull(entry);

                        String account = entry.getValue().get(ConfdbElementNames.ACCOUNT).toString();
                        String additional_nodes = entry.getValue().get(ConfdbElementNames.ADDITIONAL_NODES).toString();
                        Object applications = entry.getValue().get(ConfdbElementNames.APPLICATIONS).toString();
                        Map kerberosTypes = (Map) entry.getValue().get(ConfdbElementNames.AUTHENTICATION);
                        Map defaultTypes = (Map) kerberosTypes.get(ConfdbElementNames.KERBEROS);
                        String kerberos = defaultTypes.get(ConfdbElementNames.DEFAULT).toString();
                        Map modeTypes = (Map) entry.getValue().get(ConfdbElementNames.AUTHENTICATION);
                        String mode = modeTypes.get(ConfdbElementNames.MODE).toString();
                        String iccpopc_nodes = entry.getValue().get(ConfdbElementNames.ICCPOPC_NODES).toString();
                        String normal_nodes = entry.getValue().get(ConfdbElementNames.NORMAL_NODES).toString();
                        String dbi = entry.getValue().get(ConfdbElementNames.DBI).toString();

                        ConfdbAuthentication confdbAuthentication = new ConfdbAuthentication(kerberos, mode);

                        ConfdbAppServer confdbAppServer = new ConfdbAppServer(entry.getKey(),
                                account,
                                additional_nodes,
                                applications,
                                confdbAuthentication,
                                iccpopc_nodes,
                                normal_nodes,
                                dbi);
                        this.confdbAppServers.add(confdbAppServer);
                    }
                }

                if (jsonArray.containsKey(ConfdbElementNames.DE_SERVER)) {
                    Map rawConfdbDeServers = (Map) jsonArray.get(ConfdbElementNames.DE_SERVER);
                    if (rawConfdbDeServers == null){
                        rawConfdbDeServers = (Map) jsonArray.get(ConfdbElementNames.DATAENG);
                    }
                    Iterator<Map.Entry<String, Map>> itr = rawConfdbDeServers.entrySet().iterator();
                    while (itr.hasNext()) {
                        Map.Entry<String, Map> entry = itr.next();
                        //assertNotNull(entry);
                        Map oneConfdbDeServer = entry.getValue();
                        String account = oneConfdbDeServer.get(ConfdbElementNames.ACCOUNT).toString();
                        String appl_servers = oneConfdbDeServer.get(ConfdbElementNames.APPL_SERVERS).toString();
                        String normal_nodes = oneConfdbDeServer.get(ConfdbElementNames.NORMAL_NODES).toString();
                        String portNumber = oneConfdbDeServer.get(ConfdbElementNames.PORTNUMBER).toString();
                        //List<String> products = (List) oneConfdbDeServer.get(ConfdbElementNames.PRODUCTS);
                        List<String> products = new ArrayList<>();
                        Map<String, String> productsMap =  (Map<String, String>) oneConfdbDeServer.get(ConfdbElementNames.PRODUCTS);
                        if (productsMap != null) {
                                Iterator<Map.Entry<String, String>> itrProductMap = productsMap.entrySet().iterator();
                                while (itrProductMap.hasNext()) {
                                    Map.Entry<String, String> entryProductMap = itrProductMap.next();
                                    if (entryProductMap.getValue().equals(ConfdbElementNames.NO)) {
                                        products.add(entryProductMap.getKey());
                                    }
                                }

                        }
                        /*if (products == null) {
                            products = new ArrayList<>();
                            Map productsMap = (Map) oneConfdbDeServer.get("PRODUCTS");
                            Iterator<Map.Entry<String, Map>> itrProductMap = productsMap.entrySet().iterator();
                            while (itr.hasNext()) {
                                Map.Entry<String, Map> entryProductMap = itrProductMap.next();
                                //assertNotNull(entry);
                                products.add(entryProductMap.getKey());
                            }
                        }*/
                        ConfdbDeServer confdbDeServer = new ConfdbDeServer(entry.getKey(),
                                account,
                                appl_servers,
                                normal_nodes,
                                portNumber,
                                products);
                        this.confdbDeServers.add(confdbDeServer);
                    }
                }

                if (jsonArray.containsKey(ConfdbElementNames.BACKUP_SERVER)) {
                    Map rawConfdbAppServers = (Map) jsonArray.get(ConfdbElementNames.BACKUP_SERVER);
                    Iterator<Map.Entry<String, Map>> itr = rawConfdbAppServers.entrySet().iterator();
                    while (itr.hasNext()) {
                        Map.Entry<String, Map> entry = itr.next();
                        //assertNotNull(entry);
                        List<String> nodes = new ArrayList<>();
                        if (entry.getKey().contains(ConfdbElementNames.NODES.toLowerCase())) {
                            if (entry.getValue().equals(JSONParsingConstants.NOVALUE)) {
                                continue;
                            } else if (entry.getValue().getClass().getName().equals("java.util.ArrayList")) {
                                nodes = (List) entry.getValue();
                            } else if (entry.getValue().getClass().getName().equals("java.lang.String")) {
                                //String valueAsString = (String) entry.getValue();
                                //nodes.add(valueAsString);
                                //TODO, don't forget this
                            }
                        }
                        ConfdbBackupServer confdbBackupServer = new ConfdbBackupServer(entry.getKey(), nodes);
                        this.confdbBackupServers.add(confdbBackupServer);
                    }
                }

                if (jsonArray.containsKey(ConfdbElementNames.PSE_SERVER)) {
                    Map rawConfdbPseServers = (Map) jsonArray.get(ConfdbElementNames.PSE_SERVER);
                    Iterator<Map.Entry<String, Map>> itr = rawConfdbPseServers.entrySet().iterator();
                    while (itr.hasNext()) {
                        Map.Entry<String, Map> entry = itr.next();
                        //assertNotNull(entry);
                        Map oneConfdbPseServer = entry.getValue();
                        String account = oneConfdbPseServer.get(ConfdbElementNames.ACCOUNT).toString();
                        String appl_server = oneConfdbPseServer.get(ConfdbElementNames.APPL_SERVER).toString();
                        String normal_nodes = oneConfdbPseServer.get(ConfdbElementNames.NODES).toString();
                        String workplace_ports = oneConfdbPseServer.get(ConfdbElementNames.WORKPLACE_PORTS).toString();

                        ConfdbPseServer confdbPseServer = new ConfdbPseServer(entry.getKey(),
                                account,
                                appl_server,
                                normal_nodes,
                                workplace_ports);
                        this.confdbPseServers.add(confdbPseServer);
                    }
                }
                if (jsonArray.containsKey(ConfdbElementNames.MMI_SERVER)) {
                    Map rawConfdbMMIs = (Map) jsonArray.get(ConfdbElementNames.MMI_SERVER);
                    Iterator<Map.Entry<String, Map>> itr = rawConfdbMMIs.entrySet().iterator();
                    while (itr.hasNext()) {
                        Map.Entry<String, Map> entry = itr.next();
                        //assertNotNull(entry);
                        if (entry.getKey().equals(ConfdbElementNames.MAX_CONSOLE_NUMBER)
                                || entry.getKey().equals(ConfdbElementNames.POSSIBLE_VALUES)) { //TODO: I skipped the UID
                            continue;
                        }
                        String account = entry.getValue().get(ConfdbElementNames.ACCOUNT).toString();
                        String appl_servers = entry.getValue().get(ConfdbElementNames.APPL_SERVERS).toString();
                        String devfile = entry.getValue().get(ConfdbElementNames.AUDIBLE_ALARM).toString();
                        String type = entry.getValue().get(ConfdbElementNames.AUDIBLE_ALARM).toString();
                        AudibleAlarmInfo audible_alarm = new AudibleAlarmInfo(devfile, type);
                        String console = entry.getValue().get(ConfdbElementNames.CONSOLE).toString();
                        String display_node = entry.getValue().get(ConfdbElementNames.DISPLAY_NODE).toString();
                        String dynamic_console_number = entry.getValue().get(ConfdbElementNames.DYNAMIC_CONSOLE_NUMBER).toString();
                        String groupname = entry.getValue().get(ConfdbElementNames.GROUPNAME).toString();
                        String min_rdb_vdus = entry.getValue().get(ConfdbElementNames.MIN_RDB_VDUS).toString();
                        String normal_nodes = entry.getValue().get(ConfdbElementNames.NORMAL_NODES).toString();
                        String userid = entry.getValue().get(ConfdbElementNames.USERID).toString();
                        String vdus = entry.getValue().get(ConfdbElementNames.VDUS).toString();

                        ConfdbMMI confdbMMI = new ConfdbMMI(entry.getKey(),
                                account,
                                appl_servers,
                                audible_alarm,
                                console,
                                display_node,
                                dynamic_console_number,
                                groupname,
                                min_rdb_vdus,
                                normal_nodes,
                                userid,
                                vdus);
                        this.confdbMMIs.add(confdbMMI);
                    }
                }

                if (jsonArray.containsKey(ConfdbElementNames.PRODUCTS)) {
                    Map rawConfdbProducts = (Map) jsonArray.get(ConfdbElementNames.PRODUCTS);
                    for (ConfdbAppServer confdbAppServer : confdbAppServers) {
                        if (rawConfdbProducts.get(confdbAppServer.getName()) != null) {
                            Map confdbProductsPerApp = (Map) rawConfdbProducts.get(confdbAppServer.getName());
                            ConfdbProduct confdbProduct = null;
                            Iterator<Map.Entry<String, Map>> itr = confdbProductsPerApp.entrySet().iterator();
                            while (itr.hasNext()) {
                                Map.Entry<String, Map> entry = itr.next();
                                //assertNotNull(entry);

                             //   String build_version = entry.getValue().get(ConfdbElementNames.BUILD_VERSION).toString();
                                String dependent_of_the_products = entry.getValue().get(ConfdbElementNames.DEPENDENT_OF_THE_PRODUCT).toString();
                                String install_topdir = entry.getValue().get(ConfdbElementNames.INSTALL_TOPDIR).toString();
                                String installation_priority = entry.getValue().get(ConfdbElementNames.INSTALLATION_PRIORITY).toString();
                                String installed = entry.getValue().get(ConfdbElementNames.INSTALLED).toString();
                              //  String kit_version = entry.getValue().get(ConfdbElementNames.KIT_VERSION).toString();
                                String option_name = entry.getValue().get(ConfdbElementNames.OPTION_NAME).toString();
                                String populated = entry.getValue().get(ConfdbElementNames.POPULATED).toString();
                                String product_file_system = entry.getValue().get(ConfdbElementNames.PRODUCT_FILE_SYSTEM).toString();
                                String product_file_system_owner = entry.getValue().get(ConfdbElementNames.PRODUCT_FILE_SYSTEM_OWNER).toString();
                                String product_file_system_specific = entry.getValue().get(ConfdbElementNames.PRODUCT_FILE_SYSTEM_SPECIFIC).toString();
                                String product_kit_name = entry.getValue().get(ConfdbElementNames.PRODUCT_KIT_NAME).toString();
                                String server_type = entry.getValue().get(ConfdbElementNames.SERVER_TYPE).toString();

                                confdbProduct = new ConfdbProduct(entry.getKey(),
                                       // build_version,
                                        dependent_of_the_products,
                                        install_topdir,
                                        installation_priority,
                                        installed,
                                     //   kit_version,
                                        option_name,
                                        populated,
                                        product_file_system,
                                        product_file_system_owner,
                                        product_file_system_specific,
                                        product_kit_name,
                                        server_type);
                                this.confdbProducts.add(confdbProduct);
                            }
                            confdbAppServer.setConfdbProduct(confdbProduct);
                        }
                    }
                }

                if (jsonArray.containsKey(ConfdbElementNames.PRODUCTS.toUpperCase())) {
                    Map rawConfdbProducts = (Map) jsonArray.get(ConfdbElementNames.PRODUCTS.toUpperCase());
                    for (ConfdbAppServer confdbAppServer : confdbAppServers) {
                        if (rawConfdbProducts.get(confdbAppServer.getName()) != null) {
                            Map confdbProductsPerApp = (Map) rawConfdbProducts.get(confdbAppServer.getName());
                            ConfdbProduct confdbProduct = null;
                            Iterator<Map.Entry<String, Map>> itr = confdbProductsPerApp.entrySet().iterator();
                            while (itr.hasNext()) {
                                Map.Entry<String, Map> entry = itr.next();
                                //assertNotNull(entry);

                                //   String build_version = entry.getValue().get(ConfdbElementNames.BUILD_VERSION).toString();
                                String dependent_of_the_products = entry.getValue().get(ConfdbElementNames.DEPENDENT_OF_THE_PRODUCT).toString();
                                String install_topdir = entry.getValue().get(ConfdbElementNames.INSTALL_TOPDIR).toString();
                                String installation_priority = entry.getValue().get(ConfdbElementNames.INSTALLATION_PRIORITY).toString();
                                String installed = entry.getValue().get(ConfdbElementNames.INSTALLED).toString();
                                //  String kit_version = entry.getValue().get(ConfdbElementNames.KIT_VERSION).toString();
                                String option_name = entry.getValue().get(ConfdbElementNames.OPTION_NAME).toString();
                                String populated = entry.getValue().get(ConfdbElementNames.POPULATED).toString();
                                String product_file_system = entry.getValue().get(ConfdbElementNames.PRODUCT_FILE_SYSTEM).toString();
                                String product_file_system_owner = entry.getValue().get(ConfdbElementNames.PRODUCT_FILE_SYSTEM_OWNER).toString();
                                String product_file_system_specific = entry.getValue().get(ConfdbElementNames.PRODUCT_FILE_SYSTEM_SPECIFIC).toString();
                                String product_kit_name = entry.getValue().get(ConfdbElementNames.PRODUCT_KIT_NAME).toString();
                                String server_type = entry.getValue().get(ConfdbElementNames.SERVER_TYPE).toString();

                                confdbProduct = new ConfdbProduct(entry.getKey(),
                                        // build_version,
                                        dependent_of_the_products,
                                        install_topdir,
                                        installation_priority,
                                        installed,
                                        //   kit_version,
                                        option_name,
                                        populated,
                                        product_file_system,
                                        product_file_system_owner,
                                        product_file_system_specific,
                                        product_kit_name,
                                        server_type);
                                this.confdbProducts.add(confdbProduct);
                            }
                            confdbAppServer.setConfdbProduct(confdbProduct);
                        }
                    }
                }

                if (jsonArray.containsKey(ConfdbElementNames.NETWORKSERVICES)) {
                    Map rawConfdbNetworkServices = (Map) jsonArray.get(ConfdbElementNames.NETWORKSERVICES);
                    confdbNetworkServices = (Map) rawConfdbNetworkServices.get(ConfdbElementNames.INHOUSE);
                }

                if (jsonArray.containsKey(ConfdbElementNames.EPO_SERVER)) {
                    Map<String, Map> rawConfdbEpoServers = (Map) jsonArray.get(ConfdbElementNames.EPO_SERVER);
                    if (rawConfdbEpoServers.containsKey(ConfdbElementNames.MCAFEE_EPO)) {
                        Map antiviruses = (Map) rawConfdbEpoServers.get(ConfdbElementNames.MCAFEE_EPO);
                        Iterator<Map.Entry<String, Object>> itr = antiviruses.entrySet().iterator();
                        while (itr.hasNext()) {
                            Map.Entry<String, Object> entry = itr.next();
                            List<String> nodes = new ArrayList<>();
                            if (entry.getKey().contains(ConfdbElementNames.NODES.toLowerCase())) {
                                if (entry.getValue().equals(JSONParsingConstants.NOVALUE)) {
                                    continue;
                                } else if (entry.getValue().getClass().getName().equals("java.util.ArrayList")) {
                                    nodes = (List) entry.getValue();
                                } else if (entry.getValue().getClass().getName().equals("java.lang.String")) {
                                    String valueAsString = (String) entry.getValue();
                                    nodes.add(valueAsString);
                                }
                            }
                        ConfdbAntivirus confdbAntivirus = new ConfdbAntivirus(entry.getKey(), nodes);
                        confdbAntiviri.add(confdbAntivirus);
                        }
                    }
                }

                if (jsonArray.containsKey(ConfdbElementNames.PCU_SERVER)) {
                    Map rawConfdbPCUServer = (Map) jsonArray.get(ConfdbElementNames.PCU_SERVER);
                    Map rawConfdbPCU = (Map) rawConfdbPCUServer.get(ConfdbElementNames.PCU);
                    Iterator<Map.Entry<String, Object>> itr = rawConfdbPCU.entrySet().iterator();
                    while (itr.hasNext()) {
                        Map.Entry<String, Object> entry = itr.next();
                        String account = "";
                        String group = "";
                        //List<String> nodes = null;
                        List<String> nodes = null;
                        if (entry.getKey().contains(ConfdbElementNames.ACCOUNT.toLowerCase())) {
                            account = (String) entry.getValue();
                        }
                        if (entry.getKey().contains(ConfdbElementNames.GROUP.toLowerCase())) {
                            group = (String) entry.getValue();
                        }
                        if (entry.getKey().contains(ConfdbElementNames.NODES)) {
                            if (entry.getValue().equals(JSONParsingConstants.NOVALUE)){
                                nodes = new ArrayList<>();
                            }
                            /*if (entry.getValue().toString().startsWith("\"")){
                                nodes = new ArrayList<>();
                                nodes.add((String) entry.getValue());
                            }*/
                            else{
                                try {
                                    nodes = (ArrayList) entry.getValue();
                                } catch (ClassCastException e){
                                    nodes = new ArrayList<>();
                                    nodes.add((String) entry.getValue());
                                }
                            }
                        }

                        ConfdbPCUServer confdbPCUServer = new ConfdbPCUServer(entry.getKey(),
                                account,
                                group,
                                nodes);
                        this.confdbPCUServers.add(confdbPCUServer);
                    }
                }

                if (jsonArray.containsKey(ConfdbElementNames.ROUTING)) {
                    Map rawConfdbRouting = (Map) jsonArray.get(ConfdbElementNames.ROUTING);
                    Map inhouseConfdbRouting = (Map) rawConfdbRouting.get(ConfdbElementNames.INHOUSE);
                    routingInfo = (Map) inhouseConfdbRouting.get(ConfdbElementNames.DEFAULT);
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Reading in Mal Creator side");

    }

    public List<ConfdbHost> getConfdbHosts() {
        return confdbHosts;
    }

    public List<ConfdbNetwork> getConfdbNetworks() {
        return confdbNetworks;
    }

    public List<ConfdbUserAccount> getConfdbUserAccounts() {
        return confdbUserAccounts;
    }

    public List<ConfdbUserGroup> getConfdbUserGroups() {
        return confdbUserGroups;
    }

    public List<ConfdbAppServer> getConfdbAppServers() {
        return confdbAppServers;
    }

    public List<ConfdbDeServer> getConfdbDeServers() {
        return confdbDeServers;
    }

    public List<ConfdbPseServer> getConfdbPseServers() {
        return confdbPseServers;
    }

    public List<ConfdbMMI> getConfdbMMIs() {
        return confdbMMIs;
    }

    public List<ConfdbProduct> getConfdbProducts() {
        return confdbProducts;
    }
    public List<ConfdbPCUServer> getConfdbPCUServers() {
        return confdbPCUServers;
    }
    public Map<String, Object> getNetworkServices(){
        return confdbNetworkServices;
    }
    public Map<String, String> getRoutingInfo(){ return routingInfo; }

    public List<ConfdbAntivirus> getConfdbAntiviruses() {
        return confdbAntiviri;
    }
    public List<ConfdbBackupServer> getConfdbBackupServers(){
        return confdbBackupServers;
    }
}
