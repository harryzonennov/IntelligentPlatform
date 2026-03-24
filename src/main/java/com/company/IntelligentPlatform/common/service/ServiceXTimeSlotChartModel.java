package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ServiceXTimeSlotChartModel extends SEUIComModel {

    public static final String FIELD_STARTTIME = "startTime";

    public static final String FIELD_ENDTIME = "endTime";

    public static final String FIELD_TIMEUNIT = "timeUnit";

    public static final String FIELD_OBJECTNAME = "objectNameField";

    public static final String FIELD_OBJECTKEY = "objectKeyField";

    protected Date startTime;

    protected Date endTime;

    protected int timeUnit;

    protected String objectNameField;

    protected String objectKeyField;

    protected String axisTitle;

    protected String objectAmountField;

    protected String timeField;

    protected List<SEUIComModel> refSeUIModelList = new ArrayList<SEUIComModel>();

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(int timeUnit) {
        this.timeUnit = timeUnit;
    }

    public String getObjectNameField() {
        return objectNameField;
    }

    public void setObjectNameField(String objectNameField) {
        this.objectNameField = objectNameField;
    }

    public String getObjectKeyField() {
        return objectKeyField;
    }

    public void setObjectKeyField(String objectKeyField) {
        this.objectKeyField = objectKeyField;
    }

    public String getAxisTitle() {
        return axisTitle;
    }

    public void setAxisTitle(String axisTitle) {
        this.axisTitle = axisTitle;
    }

    public List<SEUIComModel> getRefSeUIModelList() {
        return refSeUIModelList;
    }

    public void setRefSeUIModelList(List<SEUIComModel> refSeUIModelList) {
        this.refSeUIModelList = refSeUIModelList;
    }

    public String getObjectAmountField() {
        return objectAmountField;
    }

    public void setObjectAmountField(String objectAmountField) {
        this.objectAmountField = objectAmountField;
    }

    public String getTimeField() {
        return timeField;
    }

    public void setTimeField(String timeField) {
        this.timeField = timeField;
    }

}
