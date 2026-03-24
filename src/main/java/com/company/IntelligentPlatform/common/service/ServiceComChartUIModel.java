package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;


public class ServiceComChartUIModel extends SEUIComModel {

    @ISEUIModelMapping(exportParaFlag = true)
    protected List<ServiceChartDataSeriesUIModel> series = new ArrayList<ServiceChartDataSeriesUIModel>();

    @ISEUIModelMapping(exportParaFlag = true)
    protected List<ServicePieChartDataSeriesUIModel> pieSeries = new ArrayList<ServicePieChartDataSeriesUIModel>();

    @ISEUIModelMapping(exportParaFlag = true)
    protected List<String> categories = new ArrayList<String>();

    @ISEUIModelMapping(exportParaFlag = true)
    protected String title;

    @ISEUIModelMapping(exportParaFlag = true)
    protected String subTitle;

    @ISEUIModelMapping(exportParaFlag = true)
    protected String yAxisTitle;


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

    public List<ServiceChartDataSeriesUIModel> getSeries() {
        return series;
    }

    public void setSeries(List<ServiceChartDataSeriesUIModel> series) {
        this.series = series;
    }

    public List<ServicePieChartDataSeriesUIModel> getPieSeries() {
        return pieSeries;
    }

    public void setPieSeries(List<ServicePieChartDataSeriesUIModel> pieSeries) {
        this.pieSeries = pieSeries;
    }

}
