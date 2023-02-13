package org.windsake.DataManipulation;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.windsake.FieldTypes.LicensePlate;
import org.windsake.FieldTypes.Formula.FormulaField;

import java.util.ArrayList;

public class InitializeData {

    FormulaField formulaField = new FormulaField("Account", "cuccApi__c", "cucc",
            "Formula(checkbox)", "testdesc", "testex",
            "testcomm", 25, "No");

    public static ArrayList<Object[]> initData(int count,XSSFSheet sheet){
        return initPlates(count,sheet);
        /*ArrayList<Object[]> formulaFields = new ArrayList<>();
        for (int i =0;i<count;i++){
            FormulaField dummy = new FormulaField("Account","cuccApi"+i+"__c",
                    "cucc"+i,"Formula(Text)","testdesc"+i,
                    "testex"+i,"testcomm"+i,25,"No");
            formulaFields.add(dummy.getFieldData());
        }
        return formulaFields;*/
    }
    //COUNTIF(A:A;A7) - COUNTIF(A4;A7)
    private static ArrayList<Object[]> initPlates(int count,XSSFSheet sheet){
        int rowCount = sheet.getLastRowNum()+2;
        ArrayList<Object[]> plates = new ArrayList<>();
        for (int i =0;i<count;i++){
            LicensePlate dummy = new LicensePlate("ABC-12"+i,"SUM(COUNTIF(A:A;A"+rowCount+"), -COUNTIF(A2;A"+rowCount+"))");
                    plates.add(dummy.getFieldData());
            rowCount++;
        }
        return plates;
    }
}
