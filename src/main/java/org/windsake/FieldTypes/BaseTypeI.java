package org.windsake.FieldTypes;

public interface BaseTypeI {
     String objectName = null,APIName = null,label=null,dataType=null,description=null,example=null,comment=null;
     int len=0;
     boolean required=false;

     Object[] getFieldData();
}
