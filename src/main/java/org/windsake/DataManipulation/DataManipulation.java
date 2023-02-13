package org.windsake.DataManipulation;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class DataManipulation {

    public static Object[] initData(XSSFWorkbook workbook,XSSFSheet sheet, String sheetName){
        try {
            FileInputStream inputStream = new FileInputStream("./"+sheetName);
            workbook = new XSSFWorkbook(inputStream);
            sheet = workbook.getSheet("Sheet1");
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Object[]{workbook,sheet};
    }

    public static void insertData(XSSFSheet sheet, ArrayList<Object[]> bookData) {
        int rowCount = sheet.getLastRowNum()+1;

        for (Object[] aBook : bookData) {
            Row row = sheet.createRow(rowCount++);

            int columnCount = 0;

            for (Object field : aBook) {
                Cell cell = row.createCell(columnCount++);
                if (field instanceof String) {
                    if(columnCount > 1){
                        cell.setCellFormula((String) field);
                    }else{
                        cell.setCellValue((String) field);
                    }
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }

        }
    }

    public static void writeToFile(XSSFWorkbook workbook, String sheetName){
        try{
            FileOutputStream outputStream = new FileOutputStream("./"+sheetName);
            workbook.write(outputStream);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
