package com.company.IntelligentPlatform.common.dto;

import java.sql.Time;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;


public class CalendarTempWorkScheduleUIModel extends SEUIComModel {

    protected Time endTime;

    protected Time startTime;


    public Time getEndTime() {
        return this.endTime;
    }


    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }


    public Time getStartTime() {
        return this.startTime;
    }


    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }


    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }


}
