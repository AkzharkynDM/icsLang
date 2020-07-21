package com.helpers;
import org.apache.commons.io.LineIterator;
import com.constants.Filenames;
import com.constants.JSONParsingConstants;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class JSONParserFromConfdbFile {

    public static void parseIntoJSON(String pathToConfdbFile) throws IOException{
        BufferedWriter out = null;
        FileWriter fstream = null;
        BufferedReader in = null;

        boolean ifJSONFileIsDeleted = Files.deleteIfExists(Paths.get(Filenames.pathToConfdbJsonFile));
        if (ifJSONFileIsDeleted) {
            try {
                StringBuilder contentJSON = new StringBuilder();
                fstream = new FileWriter(Filenames.pathToConfdbJsonFile, true);
                out = new BufferedWriter(fstream);
                //out.write("{\"array\": [\n");
                contentJSON.append("{\"array\": {\n");
                in = new BufferedReader(new FileReader(pathToConfdbFile));
                final LineIterator it = new LineIterator(in);
                List<String> lines = new ArrayList<>();

                while (it.hasNext()) {
                    String line = it.nextLine();
                    lines.add(line);
                }
                for (int i=0;i<lines.size();i++){
                    if (lines.get(i).charAt(0) == '#') {
                        continue; //Skip the comment # lines
                    }
                    lines.set(i, lines.get(i).replace("%Global", "")); // Ignoring %Global for now
                    if (lines.get(i).charAt(lines.get(i).length() - 1) == '=') {
                        lines.set(i, lines.get(i)+ "\"" + JSONParsingConstants.NOVALUE + "\""); //To fix the incorrect lines with empty values after =
                    }

                    String regexAnyLetters = "[a-zA-Z0-9]";
                    if (lines.get(i).contains("=")) {
                        String[] parts = lines.get(i).split("=");
                        boolean matchesAnyLetters = Pattern.matches(regexAnyLetters, parts[0]);
                        if (!parts[1].startsWith("\"")){
                            parts[1] = "\"" + parts[1] + "\"";
                        }
                        if (!matchesAnyLetters) {
                            if (parts[1].contains("\" \"")){
                                String subpartsAsArray = "";
                                String[] subparts = parts[1].split(" ");
                                for (int j=0; j<subparts.length; j++){
                                    subpartsAsArray+=subparts[j]+",";
                                }
                                lines.set(i, "\"" + parts[0].trim() + "\":[" + subpartsAsArray + "],");
                            } else {
                                lines.set(i, "\"" + parts[0].trim() + "\":" + parts[1]+",\n");
                            }
                        }
                        //"localhost":nmas201 -> This is to avoid this case, instead it should be "localhost":"nmas201"
                        //String regexAnyLettersWithQuotes = "\"" + regexAnyLetters + "\"";
                        //boolean matchesAnyLettersWithQuotes = Pattern.matches(regexAnyLettersWithQuotes, parts[1]);
                        //if (!(matchesAnyLettersWithQuotes && (parts[1].contains("{") && parts[1].contains("}")))){
                    }
                    else if (!lines.get(i).contains("=") && !lines.get(i).contains("}")) { //This is for all line like Host{ or DevSupport{
                        boolean matchesAnyLetters = Pattern.matches(regexAnyLetters, lines.get(i).substring(0, lines.get(i).indexOf("{")));
                        if (!matchesAnyLetters) {
                            lines.set(i, "\"" + lines.get(i).substring(0, lines.get(i).indexOf("{")).trim() + "\":{");
                        }
                    }
                    if (lines.get(i).trim().equals("}")) {
                        lines.set(i, lines.get(i).replace("}", "},"));
                    }
                    lines.set(i, lines.get(i).replace("=", ":"));

                    //out.write(lines.get(i).trim() + "\n");
                    contentJSON.append(lines.get(i).trim() + "\n");
                }
                //out.write("]}");
                contentJSON.append("}}");

                //String trailingComas = "\\,(?=\\s*?[\\}\\]])";
                String contentJSONAsString = contentJSON.toString().replace(",\n}", "\n}"); //to remove trailing commas
                //String contentJSONAsString = contentJSON.toString().replace(trailingComas, ""); //to remove trailing commas
                contentJSONAsString = contentJSONAsString.replace(",}", "}"); //to remove trailing commas
                contentJSONAsString = contentJSONAsString.replace(",]", "]"); //to remove trailing commas

            out.write(contentJSONAsString);

            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            System.out.println("Successfully Copied JSON Object to File...");
            //}
        }



 }



        /*public void readConfdbToList() throws IOException {
        in = new BufferedReader(new FileReader(pathToConfdb));
        final LineIterator it = new LineIterator(in);
        while (it.hasNext()) {
            String line = it.nextLine();
            if (line.charAt(0) == '#') {
                continue; //Skip the comment # lines
            }
            line = line.replace("\"", "");
            if (line.charAt(line.length() - 1) == '=') {
                line += " "; //To fix the incorrect lines wuth empty values after =
            }
            originalConfdb.add(line);
        }
        int i = 0;
        while (i < originalConfdb.size()) {
            readConfdbToJson(i);
            i++;
        }
        try {
            fstream = new FileWriter(pathToConfdbJsonFile, true);
            out = new BufferedWriter(fstream);
            out.write(jsonObject.toJSONString());
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
        System.out.println("Successfully Copied JSON Object to File...");
        System.out.println("\nJSON Object: " + jsonObject.toJSONString());
    }

    private void readConfdbToJson(int k) {
        String line = originalConfdb.get(k);
        JSONArray componentsArray = new JSONArray();
        if (line.contains("{")) {
            line = line.replace("{", "");
            JSONObject component = new JSONObject();
            readConfdbToJson(k + 1);
            componentsArray.add(component);
            k++;
        }
        if (line.contains("} %Global")) {
            jsonObject.put(originalConfdb.get(k).trim(), componentsArray);
        }
        if (line.contains("=")) {
            String[] parts = line.split("=");
            jsonObject.put(parts[0].trim(), parts[1].trim());
        }
    }*/
}
