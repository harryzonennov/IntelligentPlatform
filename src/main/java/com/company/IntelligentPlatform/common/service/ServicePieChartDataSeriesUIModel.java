package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

public class ServicePieChartDataSeriesUIModel {

    protected String name;

    protected String type;

    protected List<ServicePieChartDataUnion> data = new ArrayList<ServicePieChartDataUnion>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ServicePieChartDataUnion> getData() {
        return data;
    }

    public void setData(List<ServicePieChartDataUnion> data) {
        this.data = data;
    }

}
