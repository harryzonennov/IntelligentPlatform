package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

public class ServiceComChartArrayModel {

    protected List<ServiceComChartUIModel> chartList = new ArrayList<>();

    public List<ServiceComChartUIModel> getChartList() {
        return chartList;
    }

    public void setChartList(List<ServiceComChartUIModel> chartList) {
        this.chartList = chartList;
    }

    public void addToCharList(ServiceComChartUIModel serviceComChartUIModel) {
        this.chartList.add(serviceComChartUIModel);
    }

}
