package com.visualapp;

import com.jsonworkers.AttacksDatabaseWorker;
import com.jsonworkers.CommentsWorker;
import com.jsonworkers.CoreLangWorker;
import com.jsonworkers.ZonesWorker;
import com.constants.FileCreatorConstant;
import com.constants.Filenames;
import com.constants.AddiotionalAssetsName;
import com.helpers.JSONParserFromConfdbFile;
import com.helpers.MALGenerator;
import com.logic.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.maven.cli.MavenCli;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Desktop;
import java.awt.Button;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FileCreatorController {
    private static final Logger log = LoggerFactory.getLogger(FileCreatorController.class);
    @FXML
    private Label statusMessageLabel, pathToJarLabel;
    @FXML
    private Button openButton;
    @FXML
    private ListView<String> listView, listViewSuggestedAssets1, listViewSuggestedAssets2;
    final Clipboard clipboard = Clipboard.getSystemClipboard();
    final ClipboardContent content = new ClipboardContent();
    private final FileChooser fileChooser = new FileChooser();
    private Desktop desktop = Desktop.getDesktop();
    private String pathToSelectedConfdbFile;
    private Set<String> malsToInclude = new HashSet<>();
    private boolean ifToAddDiodCommunications = false;
    //private boolean ifToAddUnidirectionalGatewayCommunications = false;
    private boolean ifToAddNGFW = false;

    //////////////////////////////////////////
    public void selectMultipleThreatModels() {
        ObservableList<String> selectedItems = listView.getSelectionModel().getSelectedItems();
        for(String s : selectedItems){
            System.out.println("selected item " + s);
            switch (s){
                case FileCreatorConstant.COMMUNICATION:
                    listViewSuggestedAssets1.setVisible(true);
                case FileCreatorConstant.SOFTWARE:
                    listViewSuggestedAssets2.setVisible(true);
            }
        }
        malsToInclude.add(Filenames.nameAccountsMalFile);
        malsToInclude.add(Filenames.nameAppserversMalFile);
        malsToInclude.add(Filenames.nameCommunicationMalFile);
        //malsToInclude.add(Filenames.nameCoreMalFile);
    }
    //////////////////////////////////////////
    public void selectMultipleSuggestedAssets1(){
        ObservableList<String> selectedItems = listViewSuggestedAssets1.getSelectionModel().getSelectedItems();
        for(String s : selectedItems){
            log.debug("selected item to add to this model " + s);
            switch (s){
                case AddiotionalAssetsName.DIOD:
                    ifToAddDiodCommunications = true;
                case AddiotionalAssetsName.NGFW:
                    ifToAddNGFW = true;
            }
        }
    }
    //////////////////////////////////////////
    public void selectMultipleSuggestedAssets2(){
        ObservableList<String> selectedItems = listViewSuggestedAssets2.getSelectionModel().getSelectedItems();
        for(String s : selectedItems){
            log.debug("selected item to add to this model " + s);
            switch (s){
                case AddiotionalAssetsName.PSE:
                    ifToAddDiodCommunications = true;
            }
        }
    }
    //////////////////////////////////////////
    public void chooseFile() {
        selectMultipleThreatModels();
        log.debug("Reached here in file chooser");
        configureFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(new Stage());
        log.debug("Reached here in event handler");
        if (file != null) {
            //openFile(file);
            pathToSelectedConfdbFile = file.getAbsolutePath();
            log.debug("Path To Selected File is " + pathToSelectedConfdbFile);
            statusMessageLabel.setText("Path To Selected File is " + pathToSelectedConfdbFile);
            //makeMalFiles(pathToSelectedConfdbFile);
            makeMalFiles(Filenames.pathToConfdbPostgre); //TODO: don't forget to change this
        }
    }

    //////////////////////////////////////////
    private void makeMalFiles(String pathToConfdbFile){
        try {
        AttacksDatabaseWorker.readFromJSONAttacks();
        ZonesWorker.readFromJSONAttacks();
        CoreLangWorker.readFromJSONAttacks();
        CommentsWorker.readFromJSONAttacks();

        JSONParserFromConfdbFile.parseIntoJSON(pathToConfdbFile);

        AppserverMALLogic.createMAlFromConfdb();
        UnauthorizedAccessLogic.createExtendedMALFromConfdb();
        NewCommunicationFlowAttacksLogic.createExtendedMALFromConfdb(ifToAddDiodCommunications, /*ifToAddUnidirectionalGatewayCommunications,*/ ifToAddNGFW);
        //EquipmentProtectionAttacksLogic.createExtendedMALFromConfdb(); //TODO: delete this
        //SoftwareAttacksLogic.createExtendedMALFromConfdb(); //TODO: delete this
        //DataMALLogic.createMAlFromConfdb(); //TODO: delete this
        // CommunicationFlowAttacksLogic.createExtendedMALFromConfdb(ifToAddDiodCommunications, ifToAddUnidirectionalGatewayCommunications, ifToAddNGFW); //TODO: delete this


         //SuggestedAssetsLogic.createMAlFromConfdb(); //TODO: delete this
        MALGenerator.generateFinalScadalangMal(malsToInclude);
        log.debug("End of creating the MAL");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //////////////////////////////////////////
    public void executeMavenGoal(){
        MavenCli cli = new MavenCli();
        try {
            cli.doMain(new String[]{"clean", "package", "-PsecuriCAD", "-Dmaven.test.skip=true"}, Filenames.workingDirToExecuteMvnCmd, System.out, System.out);
            pathToJarLabel.setText(Filenames.pathToJarFile);
        } catch (Exception e){
            statusMessageLabel.setText("There were some errors with the packaging");
        }
    }

    public void runOldMAL(){
        String line = "java -jar " +
                Filenames.pathToOldMALCompiler + " "; //+
                //Filenames.pathToOldMALCompilerMainFile +
                //" -i lala -o lala -p lala -t lala";
        CommandLine cmdLine = CommandLine.parse(line);
        DefaultExecutor executor = new DefaultExecutor();
        try {
            int exitValue = executor.execute(cmdLine);
        } catch (Exception e){
            statusMessageLabel.setText("There are some problems with the old compiler right now");
        }
    }

    //////////////////////////////////////////
    private void openFile(File file) {
        try {
            desktop.open(file);
            log.debug("Reached here in openFile");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //////////////////////////////////////////
    public void copyPathToJar(){
        String copyText = pathToJarLabel.getText();
        content.putString(copyText);
        clipboard.setContent(content);
        log.debug("copied");
    }

    //////////////////////////////////////////
    private static void configureFileChooser(final FileChooser fileChooser) {
        log.debug("Reached here in chooser config");
        fileChooser.setTitle("View Files");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }
}
