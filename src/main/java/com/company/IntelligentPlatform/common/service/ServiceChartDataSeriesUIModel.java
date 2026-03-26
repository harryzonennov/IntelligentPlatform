package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

public class ServiceChartDataSeriesUIModel {

    protected String name;

    protected String type;

    protected List<Double> data = new ArrayList<Double>();

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

    public List<Double> getData() {
        return data;
    }

    public void setData(List<Double> data) {
        this.data = data;
    }

}
