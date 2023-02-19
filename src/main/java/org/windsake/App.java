package org.windsake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.windsake.DataManipulation.DataManipulation;
import org.windsake.DataManipulation.InitializeData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class App extends Application {
    private static Stage primarystage;
    private static String filePath;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setstage(primaryStage);
    }

    private void setstage(Stage primaryStage) throws IOException {
        final int app_stage_width = 600;
        final int app_stage_height = 400;

        primarystage = primaryStage;

        Parent root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("base.fxml")));
        primarystage.setScene(new Scene(root, app_stage_width, app_stage_height));
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double x = (bounds.getMaxX() / 2) - 300;
        double y = (bounds.getMaxY() / 2) - 200;
        primaryStage.setX(x);
        primaryStage.setY(y);
        primaryStage.show();
    }

    public static Stage getstage() {
        return primarystage;
    }

    public static String getFilePath() {
        return filePath;
    }

    public static void setFilePath(String path) {
        filePath = path;
    }

    private void DataManipulation(){
        String sheetName = "cucc.xlsx";
        XSSFWorkbook workbook = null;
        XSSFSheet sheet = null;
        ArrayList<Object[]> bookData = new ArrayList<>();

        Object[] returned = DataManipulation.initData(workbook, sheet, sheetName);
        workbook = (XSSFWorkbook) returned[0];
        sheet = (XSSFSheet) returned[1];

        assert sheet != null;

        ArrayList<Object[]> initDatas =InitializeData.initData(9,sheet);

        bookData.addAll(initDatas);
        DataManipulation.insertData(sheet, bookData);
        DataManipulation.writeToFile(workbook, sheetName);
    }
}