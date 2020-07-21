package com.pojoJSONParsing;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConfdbAuthentication {
    private String kerberosString;
    private String mode;

    /*public ConfdbAuthentication(JSONObject kerberosObject, String mode) {
        String[] parts = kerberosObject.get("kerberos").split(" ");
        for (int i=0; i<parts.length;i++) {
            this.kerberos.add(parts[i]);
        }
        this.mode = mode;
    }*/

    public ConfdbAuthentication(String kerberosString, String mode) {
        this.kerberosString = kerberosString;
        this.mode = mode;
    }

    public String getKerberosString() {
        return kerberosString;
    }

    public String getMode() {
        return mode;
    }
}
