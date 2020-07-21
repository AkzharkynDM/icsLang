package com.pojoJSONParsing;
// TODO: This is outdated
public class DevSupportInfo {
    private String CorePath;
    private String On;
    private String UsePid;
    private String UseProcName;

    public DevSupportInfo(String corePath, String on, String usePid, String useProcName) {
        this.CorePath = corePath;
        this.On = on;
        this.UsePid = usePid;
        this.UseProcName = useProcName;
    }

    public DevSupportInfo(){}

    @Override
    public String toString() {
        return "DevSupportInfo{" +
                "CorePath='" + CorePath + '\'' +
                ", On='" + On + '\'' +
                ", UsePid='" + UsePid + '\'' +
                ", UseProcName='" + UseProcName + '\'' +
                '}';
    }
}
