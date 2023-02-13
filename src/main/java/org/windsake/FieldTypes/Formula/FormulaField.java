package org.windsake.FieldTypes.Formula;

import org.windsake.FieldTypes.BaseTypeI;

public class FormulaField implements BaseTypeI {
    private final String objectName;
    private final String apiName;
    private final String label;
    private final String dataType;
    private final String description;
    private final String example;
    private final String comment;
    private final int len;
    private final String required;

    public FormulaField(String objectName, String apiName, String label,
                        String dataType, String description, String example,
                        String comment, int len, String required){

        this.objectName = objectName;
        this.apiName = apiName;
        this.label = label;
        this.dataType = dataType;
        this.description = description;
        this.example = example;
        this.comment = comment;
        this.len = len;
        this.required = required;
    }

    @Override
    public Object[] getFieldData(){
        return new Object[]{objectName,apiName,label,dataType,len,required,description,example,comment};
    }
}
