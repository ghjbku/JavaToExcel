package org.windsake.DataManipulation;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.windsake.FieldTypes.LicensePlate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class DataManipulation {

    public static XSSFWorkbook initWorkbook(String excelName) {
        XSSFWorkbook workbook=null;
        try {
            FileInputStream inputStream = new FileInputStream(excelName);
            workbook = new XSSFWorkbook(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }

    public static Object[] createPlate(String plateNumber, XSSFSheet sheet){
        int rowCount = sheet.getLastRowNum()+2;
            return (new LicensePlate(plateNumber,"(COUNTIF(A:A,A"+rowCount+")-COUNTIF(A2,A"+rowCount+"))")).getFieldData();
    }

    public static void insertAllData(XSSFSheet sheet, ArrayList<Object[]> bookData) {
        int rowCount = sheet.getLastRowNum() + 1;

        for (Object[] aBook : bookData) {
            Row row = sheet.createRow(rowCount++);

            int columnCount = 0;

            for (Object field : aBook) {
                Cell cell = row.createCell(columnCount++);
                if (field instanceof String) {
                    if (columnCount > 1) {
                        cell.setCellFormula((String) field);
                    } else {
                        cell.setCellValue((String) field);
                    }
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }

        }
    }

    public static void writeToFile(XSSFWorkbook workbook, String sheetName) {
        try {
            FileOutputStream outputStream = new FileOutputStream(sheetName);
            workbook.write(outputStream);
            //workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
