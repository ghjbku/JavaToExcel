package org.windsake.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
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
    File excelFile;

    @FXML
    ToolBar first_tab;
    @FXML
    Pane version_pane;

    @FXML
    void initialize() {
    }

    @FXML
    void open_folder() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open xsave.dat");
        try {
            excelFile = fileChooser.showOpenDialog(App.getstage());
            App.setFilePath(excelFile.getPath());
            DataManipulation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exit_button_processing(){

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
    public void on_hover_version_num(){
        version_pane.setVisible(true);
    }
    @FXML
    public void on_exit_version_num(){
        version_pane.setVisible(false);
    }

    public static Scene getScene() {
        return thescene;
    }

    private void DataManipulation(){
        String sheetName = excelFile.getPath();
        XSSFWorkbook workbook = null;
        XSSFSheet sheet = null;
        ArrayList<Object[]> bookData = new ArrayList<>();

        Object[] returned = DataManipulation.initData(workbook, sheet, sheetName);
        workbook = (XSSFWorkbook) returned[0];
        sheet = (XSSFSheet) returned[1];

        assert sheet != null;

        ArrayList<Object[]> initDatas = InitializeData.initData(9,sheet);

        bookData.addAll(initDatas);
        DataManipulation.insertData(sheet, bookData);
        DataManipulation.writeToFile(workbook, sheetName);
    }
}
