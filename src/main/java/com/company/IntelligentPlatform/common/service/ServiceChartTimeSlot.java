package com.company.IntelligentPlatform.common.service;

import java.util.Date;

public class ServiceChartTimeSlot {

    protected Date startDate;

    protected Date endDate;

    protected double value;

    protected String xTimeSlotLabel;

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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getxTimeSlotLabel() {
        return xTimeSlotLabel;
    }

    public void setxTimeSlotLabel(String xTimeSlotLabel) {
        this.xTimeSlotLabel = xTimeSlotLabel;
    }

}
