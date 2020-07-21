package com.helpers;
import com.constants.*;
import com.pojoMAL.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public class MALGenerator {

    private static String introduceAbstractAssets(){
        //TODO: This is added with all categories, but should be only once
        String result = MAL_Syntax.CATEGORY_CONTAINER + " {\n" +
                MAL_Syntax.ABSTRACT_ASSET_ZONE + "{\n \t" + "}\n" +
                MAL_Syntax.ABSTRACT_ASSET_DATABASE + "{\n \t" + "}\n" +
                MAL_Syntax.ABSTRACT_ASSET_ADMIN_ACCOUNT + "{\n \t" + "}\n" +
                MAL_Syntax.ABSTRACT_ASSET_SERVICE_ACCOUNT + "{\n \t" + "}\n" +
                MAL_Syntax.ABSTRACT_ASSET_USER_ACCOUNT + "{\n \t" + "}\n" +
                "} ";
        return result;
    }

    public static void generateFinalScadalangMal(Set<String> malsToInclude) throws IOException {
        BufferedWriter out = null;
        FileWriter fstream = null;
        boolean ifMALFileIsDeleted = Files.deleteIfExists(Paths.get(Filenames.pathToScadaLangMal));
        if (ifMALFileIsDeleted) {
            try {
                fstream = new FileWriter(Filenames.pathToScadaLangMal, true);
                out = new BufferedWriter(fstream);
                out.write(MAL_Syntax.version + "\n");
                out.write(MAL_Syntax.id + "\n");
                for (String malToInclude : malsToInclude) {
                    out.write(MAL_Syntax.INCLUDE + "\"" + malToInclude + "\"\n");
                }
                out.write(introduceAbstractAssets());
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }
    }

    public static void generateMAlFile(List<Category> categories,
                                       Set<Association> associations,
                                       String filename) throws IOException {
        BufferedWriter out = null;
        FileWriter fstream = null;
        boolean ifMALFileIsDeleted = Files.deleteIfExists(Paths.get(filename));
        if (ifMALFileIsDeleted) {
            try {
                fstream = new FileWriter(filename, true);
                out = new BufferedWriter(fstream);
                //if (categories != null) {
                    //if (categories.size()>0) {
                        for (int i = 0; i < categories.size(); i++) {
                            out.write(categories.get(i).toString());
                        }
                    //}
                //}
                out.write("\n" + MAL_Syntax.ASSOCIATIONS + "{\n");
                //if (associations != null) {
                    for (Association association : associations) {
                        out.write(association.toString());
                    }
                //}
                //End of associations
                out.write("\n}");
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }
    }
}
