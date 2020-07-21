package com.helpers;

import com.constants.Filenames;
import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class InfoRmtFile {
    private static List listAssetsRmt = null;

    public static void readFromServerRmtYaml() throws IOException {
        YamlReader reader = new YamlReader(new FileReader(Filenames.pathToServerRmtYaml));
        while (true) {
            Object object = reader.read();
            if (object == null) break;
            listAssetsRmt = (List) object;
        }
    }
}
