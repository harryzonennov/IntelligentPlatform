package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Internal Helper Class: for chart item Union
 *
 * @author Zhang, Hang
 */
public class ServiceChartItemUnion {

    protected String objectKey;

    protected String objectLabel;

    protected double allAmount;

    protected double averageAmount;

    protected int arriveIndex;

    protected int timeSlotIndex;

    protected Date startDate;

    protected Date endDate;

    protected List<SEUIComModel> refSeUIModelList = new ArrayList<SEUIComModel>();

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getObjectLabel() {
        return objectLabel;
    }

    public void setObjectLabel(String objectLabel) {
        this.objectLabel = objectLabel;
    }

    public double getAllAmount() {
        return allAmount;
    }

    public void setAllAmount(double allAmount) {
        this.allAmount = allAmount;
    }

    public double getAverageAmount() {
        return averageAmount;
    }

    public void setAverageAmount(double averageAmount) {
        this.averageAmount = averageAmount;
    }

    public int getArriveIndex() {
        return arriveIndex;
    }

    public void setArriveIndex(int arriveIndex) {
        this.arriveIndex = arriveIndex;
    }

    public int getTimeSlotIndex() {
        return timeSlotIndex;
    }

    public void setTimeSlotIndex(int timeSlotIndex) {
        this.timeSlotIndex = timeSlotIndex;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<SEUIComModel> getRefSeUIModelList() {
        return refSeUIModelList;
    }

    public void setRefSeUIModelList(List<SEUIComModel> refSeUIModelList) {
        this.refSeUIModelList = refSeUIModelList;
    }

}
