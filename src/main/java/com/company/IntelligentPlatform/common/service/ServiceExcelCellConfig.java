package com.company.IntelligentPlatform.common.service;

public class ServiceExcelCellConfig {

    public static final int COLOR_NORMAL = 1;

    public static final int COLOR_RED = 2;

    public static final int DEF_WEIGHT_MID = 25;

    public static final int DEF_WEIGHT_SHORT = 15;

    public static final int DEF_WEIGHT_LONG = 40;

    public static final int SIZE_FACTOR = 256;

    protected String fieldName;

    protected String fieldLabel;

    protected int fieldWeight;

    protected int color;

    protected int colIndex;

    protected int foregroundColor;

    protected boolean idFlag;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public int getFieldWeight() {
        return fieldWeight;
    }

    public void setFieldWeight(int fieldWeight) {
        this.fieldWeight = fieldWeight;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColIndex() {
        return colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    public int getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(int foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public boolean isIdFlag() {
        return idFlag;
    }

    public void setIdFlag(boolean idFlag) {
        this.idFlag = idFlag;
    }

}
