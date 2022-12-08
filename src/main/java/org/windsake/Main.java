package org.windsake;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.windsake.DataManipulation.DataManipulation;
import org.windsake.FieldTypes.Formula.FormulaField;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String sheetName = "cucc.xlsx";
        XSSFWorkbook workbook = null;
        XSSFSheet sheet = null;

        Object[] returned = DataManipulation.initData(workbook, sheet, sheetName);
        workbook = (XSSFWorkbook) returned[0];
        sheet = (XSSFSheet) returned[1];


        assert sheet != null;


        FormulaField formulaField = new FormulaField("Account", "cuccApi__c", "cucc",
                "Formula(checkbox)", "testdesc", "testex",
                "testcomm", 25, "No");
        FormulaField formulaField2 = new FormulaField("Account", "cuccApi2__c", "cucc2",
                "Formula(Text)", "testdesc2", "testex2",
                "testcomm2", 25, "No");
        FormulaField formulaField3 = new FormulaField("Account", "cuccApi23__c", "cucc32",
                "Formula(Date)", "testdesc32", "teste3x2",
                "testcomm3", 25, "Yes");

        ArrayList<Object[]> bookData = new ArrayList<>() {
        };
        bookData.add(formulaField.getFieldData());
        bookData.add(formulaField2.getFieldData());
        bookData.add(formulaField3.getFieldData());


        DataManipulation.insertData(sheet, bookData);
        DataManipulation.writeToFile(workbook, sheetName);
    }

}