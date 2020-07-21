package com.pojoJSONParsing;
public class ServerRmt{
    private String server_name;
    private String node;
    private String port;
    private String transportprotocol;
    private String server;
    private String client;
    private String product;

    public ServerRmt(){};

    public ServerRmt(String server_name,
                     String node,
                     String port,
                     String transportprotocol,
                     String server,
                     String client,
                     String product){
        this.server_name = server_name;
        this.node = node;
        this.port = port;
        this.transportprotocol = transportprotocol;
        this.server = server;
        this.client = client;
        this.product = product;
    }

    public String getServer_name() {
        return server_name;
    }

    public void setServer_name(String server_name) {
        this.server_name = server_name;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getTransportprotocol() {
        return transportprotocol;
    }

    public void setTransportprotocol(String transportprotocol) {
        this.transportprotocol = transportprotocol;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ServerRmt{" +
                "server_name='" + server_name + '\'' +
                ", node='" + node + '\'' +
                ", port='" + port + '\'' +
                ", transportprotocol='" + transportprotocol + '\'' +
                ", server='" + server + '\'' +
                ", client='" + client + '\'' +
                ", product='" + product + '\'' +
                '}';
    }
}