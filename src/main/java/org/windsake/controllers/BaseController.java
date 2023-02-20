package org.windsake.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.windsake.App;
import org.windsake.DataManipulation.DataManipulation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class BaseController {
    static Scene thescene;
    String excelPath;
    File excelFile;
    XSSFWorkbook workbook;
    XSSFSheet sheet;


    @FXML
    Pane versionPane;
    @FXML
    Pane dataPane;
    @FXML
    ChoiceBox<String> langSelector;
    @FXML
    ChoiceBox<String> sheetNamePicker;
    @FXML
    TextField plateField;

    @FXML
    Label errorMsg;
    @FXML
    Label versionLabel;
    @FXML
    Label languageLabel;
    @FXML
    Label sheetNameLabel;
    @FXML
    Label countLabel;
    @FXML
    Label plateNameLabel;
    @FXML
    Label countAmount;
    @FXML
    Label latestLabel;
    @FXML
    Label newsTitleLabel;


    //buttons
    @FXML
    Button openFileButton;
    @FXML
    Button exitButton;
    @FXML
    Button runButton;
    @FXML
    Button insertPlateButton;

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
    public void insert_plate(){
        ArrayList<Object[]> bookData = new ArrayList<>();

        if (plateField.getText().isEmpty()) {
            errorMsg.setVisible(true);
            return;
        }
        errorMsg.setVisible(false);
        assert sheet != null;

        Object[] createdPlate = DataManipulation.createPlate(plateField.getText(),sheet);
        bookData.add(createdPlate);

        DataManipulation.insertAllData(sheet, bookData);
        workbook.setForceFormulaRecalculation(true);
        DataManipulation.writeToFile(workbook, excelPath);
    }

    @FXML
    public void run() {
        if (plateField.getText().isEmpty()) {
            errorMsg.setVisible(true);
            return;
        }
        errorMsg.setVisible(false);
        assert sheet != null;

        int rowNum = DataManipulation.findRow(sheet,plateField.getText());
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();


        XSSFCell formulaCell = (sheet.getRow(rowNum).getCell(sheet.getRow(rowNum).getLastCellNum()-1));
        DataFormatter formatter = new DataFormatter();

        countAmount.setText(formatter.formatCellValue(formulaCell, evaluator));
    }

    @FXML
    public void exit_button_processing() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        versionPane.setVisible(true);
    }

    @FXML
    public void on_exit_version_num() {
        versionPane.setVisible(false);
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
        runButton.setText("Gyakoriság számlálás");
        errorMsg.setText("a rendszám mező üres!");
        newsTitleLabel.setText("Újdonság:");
        latestLabel.setText("Alap GUI létrehozva és működik.");
        String dummyVersion = versionLabel.getText();
        versionLabel.setText(dummyVersion.replace("Version", "Verzió szám"));
        countLabel.setText((countLabel.getText()).replace("count", "gyakoriság"));
        plateNameLabel.setText("Rendszám");
        plateField.setPromptText("A2-es cella");

        openFileButton.setText("excel fájl megnyitása");
        insertPlateButton.setText("Rendszám beillesztés");
        exitButton.setText("Kilépés");
    }

    private void setDataToEnglish() {
        sheetNameLabel.setText("Sheet Name*");
        languageLabel.setText("Language");
        runButton.setText("Get Plate count");
        errorMsg.setText("Plate Name is missing, nothing to insert!");
        newsTitleLabel.setText("What's new:");
        latestLabel.setText("Base GUI added and is working.");
        String dummyVersion = versionLabel.getText();
        versionLabel.setText(dummyVersion.replace("Verzió szám", "Version"));
        countLabel.setText((countLabel.getText()).replace("gyakoriság", "count"));
        plateNameLabel.setText("Plate Name");
        plateField.setPromptText("A2 cell");


        openFileButton.setText("Open excel file");
        insertPlateButton.setText("Insert new Plate");
        exitButton.setText("Exit");
    }

    private void initSheetPicker(XSSFWorkbook workbook) {
        int numOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numOfSheets; i++) {
            sheetNamePicker.getItems().add(workbook.getSheetName(i));
        }
        sheetNamePicker.setValue(workbook.getSheetName(0));
    }

    private void dataManipulation() {
        excelPath = excelFile.getPath();
        workbook = DataManipulation.initWorkbook(excelPath);
        initSheetPicker(workbook);
        sheet = workbook.getSheet(sheetNamePicker.getValue());
        dataPane.setVisible(true);
    }
}
