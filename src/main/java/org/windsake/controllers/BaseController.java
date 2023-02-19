package org.windsake.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.windsake.App;
import org.windsake.DataManipulation.DataManipulation;
import org.windsake.DataManipulation.InitializeData;

import java.io.File;
import java.util.ArrayList;


public class BaseController {
    static Scene thescene;
    String excelPath;
    File excelFile;
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    ArrayList<Object[]> bookData = new ArrayList<>();

    @FXML
    Pane version_pane;
    @FXML
    ChoiceBox<String> langSelector;
    @FXML
    ChoiceBox<String> sheetNamePicker;

    //Labels
    @FXML
    Label errorMsg;
    @FXML
    Label versionLabel;
    @FXML
    Label languageLabel;
    @FXML
    Label sheetNameLabel;

    //buttons
    @FXML
    Button openFileButton;
    @FXML
    Button exitButton;
    @FXML
    Button runButton;

    @FXML
    void initialize() {
        langSelector.getItems().addAll("HU", "ENG");
        langSelector.setValue("ENG");
        langSelector.setOnAction((event) -> {
                    onCboxChoice(langSelector.getValue());
                }
        );
    }

    @FXML
    void open_file() {
        FileChooser fileChooser = new FileChooser();
        if (langSelector.getValue().equals("HU")) {
            fileChooser.setTitle("Példa: cucc.xlsx");
        } else {
            fileChooser.setTitle("Open cucc.xlsx");
        }

        try {
            excelFile = fileChooser.showOpenDialog(App.getstage());
            App.setFilePath(excelFile.getPath());
            dataManipulation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void run(){
        if (sheetNamePicker.getItems().isEmpty()) {
            errorMsg.setVisible(true);
            return;
        }
        errorMsg.setVisible(false);
        assert sheet != null;

        ArrayList<Object[]> initDatas = InitializeData.initData(9, sheet);

        bookData.addAll(initDatas);
        DataManipulation.insertData(sheet, bookData);
        workbook.setForceFormulaRecalculation(true);
        DataManipulation.writeToFile(workbook, excelPath);
    }

    @FXML
    public void exit_button_processing() {

        System.exit(0);
    }

    /*private static void setTheScene(Stage stage) {
        thescene = stage.getScene();
    }

    @FXML
    public void open_edit_player_data(){
        try{
            Stage primarystage = App.getstage();
            setTheScene(primarystage);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().
                    getResource("player_data_stage.fxml")));
            primarystage.setScene(new Scene(root, 600, 460));
            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
            double x = (bounds.getMaxX() / 2) - 300;
            double y = (bounds.getMaxY() / 2) - 200;
            primarystage.setX(x);
            primarystage.setY(y);
            primarystage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }*/

    @FXML
    public void on_hover_version_num() {
        version_pane.setVisible(true);
    }

    @FXML
    public void on_exit_version_num() {
        version_pane.setVisible(false);
    }

    public static Scene getScene() {
        return thescene;
    }

    private void onCboxChoice(String lang) {
        switch (lang) {
            case "HU":
                setDataToHungarian();
                break;
            case "ENG":
                setDataToEnglish();
                break;
            default:
                break;
        }
    }

    private void setDataToHungarian() {
        sheetNameLabel.setText("Lap neve*");
        languageLabel.setText("Nyelv választás");
        runButton.setText("Program futtatása");
        errorMsg.setText("A lapnév hiányzik, nyiss meg egy excel fájlt!");
        String dummyVersion = versionLabel.getText();
        versionLabel.setText(dummyVersion.replace("Version", "Verzió szám"));

        openFileButton.setText("excel fájl megnyitása");
        exitButton.setText("Kilépés");
    }

    private void setDataToEnglish() {
        sheetNameLabel.setText("Sheet Name*");
        languageLabel.setText("Language");
        runButton.setText("Run the program");
        errorMsg.setText("sheet name is missing, open an excel file!");
        String dummyVersion = versionLabel.getText();
        versionLabel.setText(dummyVersion.replace("Verzió szám", "Version"));

        openFileButton.setText("Open excel file");
        exitButton.setText("Exit");
    }

    private void initSheetPicker(XSSFWorkbook workbook){
        int numOfSheets=workbook.getNumberOfSheets();
        for (int i=0;i<numOfSheets;i++){
            sheetNamePicker.getItems().add(workbook.getSheetName(i));
        }
        sheetNamePicker.setValue(workbook.getSheetName(0));
    }

    private void dataManipulation() {
        excelPath = excelFile.getPath();
        workbook = DataManipulation.initData(excelPath);
        initSheetPicker(workbook);
        sheet = workbook.getSheet(sheetNamePicker.getValue());

    }
}
