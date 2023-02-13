package org.windsake.FieldTypes;

public class LicensePlate {
    private final String plateId;
    private final String frequency;

    public LicensePlate(String plateId,String frequency){

        this.plateId = plateId;
        this.frequency = frequency;
    }

    public Object[] getFieldData(){
        return new Object[]{plateId,frequency};
    }
}
