package com.helpers;
import com.helpers.JSONParserFromConfdbFile;

import java.io.*;

//TODO: OUTDATED
public class JsonCreatorFromConfdb {

    public void printToJSONFile(String pathToConfdbFile) throws IOException{
        JSONParserFromConfdbFile.parseIntoJSON(pathToConfdbFile);
    }

    /*private List<ConfdbInfo> confdbInfos = new ArrayList();
    static final int root_depth = 0; // assuming  n number of whitespaces precede the tree root

    private int countLeadingSpaces(String word)
    {
        int length=word.length();
        int count=0;
        for(int i=0;i<length;i++)
        {
            char first = word.charAt(i);
            if(Character.isWhitespace(first))
            {
                count++;
            }
            else
            {
                return count;
            }
        }
        return count;
    }

    public void parseIntoTree() throws IOException {
        ConfdbInfo prev = null;
        in = new BufferedReader(new FileReader(Filenames.pathToConfdb));
        final LineIterator it = new LineIterator(in);
        //for (String line; (line = it.nextLine()) != null; it.hasNext()) {
        while (it.hasNext()) {
            String line = it.nextLine();
            if (line.charAt(0) == '#') {
                continue; //Skip the comment # lines
            }
            line = line.replace("\"", "");
            line = line.replace("{", "");
            if (line.charAt(line.length() - 1) == '=') {
                line += " "; //To fix the incorrect lines with empty values after =
            }
            if (line.contains("} %Global")){
                continue;
            }
            if (prev == null && countLeadingSpaces(line) == root_depth){
                ConfdbInfo root = new ConfdbInfo(line.trim(), root_depth);
                prev = root;
            }
            else  if (prev != null && countLeadingSpaces(line) == root_depth){
                ConfdbInfo root = new ConfdbInfo(line.trim(), root_depth);
                prev = root;
            }
            else{
                int t_depth = countLeadingSpaces(line);
                if (t_depth > prev.getDepth()) {
                    // assuming that empty sections are not allowed
                    ConfdbInfo t_section = new ConfdbInfo(line.trim(), t_depth, prev);
                    prev.addChild(t_section);
                } else if (t_depth == prev.getDepth()) {
                    ConfdbInfo t_section = new ConfdbInfo(line.trim(), t_depth, prev.getParent());
                    prev.getParent().addChild(t_section);
                } else {
                    while (t_depth < prev.getDepth()) {
                        prev = prev.getParent();
                    }
                    // at this point, (t_depth == prev.getDepth()) = true
                    ConfdbInfo t_section = new ConfdbInfo(line.trim(), t_depth, prev.getParent());
                    prev.getParent().addChild(t_section);
                }
            }
            confdbInfos.add(prev);
        }
    }*/
}



