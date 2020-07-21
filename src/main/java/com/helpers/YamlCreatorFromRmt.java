package com.helpers;
import com.esotericsoftware.yamlbeans.YamlWriter;
import org.apache.commons.io.LineIterator;
import com.pojoJSONParsing.ServerRmt;
import com.constants.Filenames;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class YamlCreatorFromRmt {
    private BufferedReader in = null;
    //private FileReader fileReader = null;
    private List<ServerRmt> serversRmt = new ArrayList<>();

    public void readRmtToList() throws IOException {
        in = new BufferedReader (new FileReader(Filenames.pathToServerRmt));
        final LineIterator it = new LineIterator(in);
        try {
            while (it.hasNext()) {
                String line = it.nextLine();
                if (line.charAt(0) == '#'){
                    continue; //Skip the comment # lines
                }
                else {
                    String[] fields = line.split("\\s+");
                    ServerRmt serverRmt = new ServerRmt(fields[0],
                                fields[1],
                                fields[2],
                                fields[3],
                                fields[4],
                                fields[5],
                                fields[6]);
                    //System.out.println(serverRmt.toString());
                    serversRmt.add(serverRmt);
                }
            }
        } finally {
            it.close();
        }
    }

    public void createYamlFile() throws IOException{
        boolean ifYAMLFileIsDeleted = Files.deleteIfExists(Paths.get(Filenames.pathToServerRmtYaml));
        if (ifYAMLFileIsDeleted) {
            YamlWriter writer = new YamlWriter(new FileWriter(Filenames.pathToServerRmtYaml));
            writer.getConfig().writeConfig.setIndentSize(2);
            writer.getConfig().writeConfig.setAutoAnchor(false);
            writer.getConfig().writeConfig.setWriteDefaultValues(true);
            writer.getConfig().writeConfig.setWriteRootTags(false);
            //writer.getConfig().writeConfig.setWriteClassname(YamlConfig.WriteClassName.NEVER);
            writer.write(serversRmt);
            writer.close();
        }
    }

}