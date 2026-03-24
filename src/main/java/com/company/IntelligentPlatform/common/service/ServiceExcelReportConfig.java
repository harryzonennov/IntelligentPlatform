package com.company.IntelligentPlatform.common.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;

public class ServiceExcelReportConfig {

    protected String reportName;

    protected int timeStampFlag;

    protected String filePath;

    public static final int TIMESTAMP_FLAG_NEED = 1;

    public static final int TIMESTAMP_FLAG_NONEED = 2;

    protected List<ServiceExcelCellConfig> serviceExcelCellConfigs = new ArrayList<>();

    protected List<?> excelContent;

    protected List<Field> fieldList;

    protected HSSFSheet curSheet;

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public int getTimeStampFlag() {
        return timeStampFlag;
    }

    public void setTimeStampFlag(int timeStampFlag) {
        this.timeStampFlag = timeStampFlag;
    }

    public List<ServiceExcelCellConfig> getServiceExcelCellConfigs() {
        return serviceExcelCellConfigs;
    }

    public void setServiceExcelCellConfigs(List<ServiceExcelCellConfig> serviceExcelCellConfigs) {
        this.serviceExcelCellConfigs = serviceExcelCellConfigs;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<?> getExcelContent() {
        return excelContent;
    }

    public void setExcelContent(List<?> excelContent) {
        this.excelContent = excelContent;
    }

    public List<Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }

    public HSSFSheet getCurSheet() {
        return curSheet;
    }

    public void setCurSheet(HSSFSheet curSheet) {
        this.curSheet = curSheet;
    }

}
