package org.windsake.DataManipulation;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.windsake.FieldTypes.LicensePlate;

import java.util.ArrayList;

public class InitializeData {

    public static ArrayList<Object[]> initData(int count,XSSFSheet sheet){
        return initPlates(count,sheet);
    }
    //COUNTIF(A:A;A7) - COUNTIF(A4;A7)
    private static ArrayList<Object[]> initPlates(int count,XSSFSheet sheet){
        int rowCount = sheet.getLastRowNum()+2;
        ArrayList<Object[]> plates = new ArrayList<>();
        for (int i =0;i<count;i++){
            LicensePlate dummy = new LicensePlate("ABC-12"+i,"(COUNTIF(A:A,A"+rowCount+")-COUNTIF(A2,A"+rowCount+"))");
                    plates.add(dummy.getFieldData());
            rowCount++;
        }
        return plates;
    }
}
