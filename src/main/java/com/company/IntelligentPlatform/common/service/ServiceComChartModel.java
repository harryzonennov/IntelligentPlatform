package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

public class ServiceComChartModel {

    protected List<ServiceChartDataSeries> dataSeries = new ArrayList<ServiceChartDataSeries>();

    protected List<String> categories = new ArrayList<String>();

    protected static final String DEF_SUB_TITLE = "Intelligence Report";

    protected String title;

    protected String subTitle = DEF_SUB_TITLE;

    protected String yAxisTitle;

    public List<ServiceChartDataSeries> getDataSeries() {
        return dataSeries;
    }

    public void setDataSeries(List<ServiceChartDataSeries> dataSeries) {
        this.dataSeries = dataSeries;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getyAxisTitle() {
        return yAxisTitle;
    }

    public void setyAxisTitle(String yAxisTitle) {
        this.yAxisTitle = yAxisTitle;
    }

}
