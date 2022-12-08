package org.windsake.DataManipulation;

import org.windsake.FieldTypes.Formula.FormulaField;

import java.util.ArrayList;

public class InitializeData {

    FormulaField formulaField = new FormulaField("Account", "cuccApi__c", "cucc",
            "Formula(checkbox)", "testdesc", "testex",
            "testcomm", 25, "No");

    public static ArrayList<Object[]> initData(int count){
        ArrayList<Object[]> formulaFields = new ArrayList<>();
        for (int i =0;i<count;i++){
            FormulaField dummy = new FormulaField("Account","cuccApi"+i+"__c",
                    "cucc"+i,"Formula(Text)","testdesc"+i,
                    "testex"+i,"testcomm"+i,25,"No");
            formulaFields.add(dummy.getFieldData());
        }
        return formulaFields;
    }
}
