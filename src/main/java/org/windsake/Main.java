package org.windsake;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.windsake.DataManipulation.DataManipulation;
import org.windsake.DataManipulation.InitializeData;
import org.windsake.FieldTypes.Formula.FormulaField;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
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