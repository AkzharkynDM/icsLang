package com.helpers;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import com.constants.ConfdbElementNames;
import com.constants.TypeOfHost;
import com.pojoJSONParsing.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BasicMALUtils {
    private static BidiMap<List<String>, String> bidiConfdbNetworkServices;

    public static BidiMap<List<String>, String> reverseKeysAndValues(Map<String, Object> confdbNetworkServices) {
        bidiConfdbNetworkServices = new DualHashBidiMap<>();

        Iterator<Map.Entry<String, Object>> itr = confdbNetworkServices.entrySet().iterator();

        while (itr.hasNext()) {
            Map.Entry<String, Object> entry = itr.next();
            if (entry.getKey().equals(ConfdbElementNames.KERBEROS_REALMS)) { // TODO: kerberos Realms I just skipped
                continue;
            }

            if (entry.getValue().getClass().getName().equals("java.util.ArrayList")) {
                List<String> valuesAsList = (List) entry.getValue();
                bidiConfdbNetworkServices.put(valuesAsList, entry.getKey());
            }

            if (entry.getValue().getClass().getName().equals("java.lang.String")) {
                String valueAsString = (String) entry.getValue();
                List<String> valuesAsList = new ArrayList<>();
                valuesAsList.add(valueAsString);
                bidiConfdbNetworkServices.put(valuesAsList, entry.getKey());
            }

        }

        return bidiConfdbNetworkServices;
    }
}
