package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

public class ServiceChartDataSeries {

    protected String objectName;

    protected String objectUUID;

    protected String chartType;

    protected List<Double> valueList = new ArrayList<Double>();

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectUUID() {
        return objectUUID;
    }

    public void setObjectUUID(String objectUUID) {
        this.objectUUID = objectUUID;
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public List<Double> getValueList() {
        return valueList;
    }

    public void setValueList(List<Double> valueList) {
        this.valueList = valueList;
    }

}
