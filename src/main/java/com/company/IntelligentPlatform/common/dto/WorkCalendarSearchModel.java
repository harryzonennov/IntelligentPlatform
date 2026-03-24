package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.model.CalendarTemplate;
import com.company.IntelligentPlatform.common.model.WorkCalendar;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;



@Component
public class WorkCalendarSearchModel extends SEUIComModel{


@BSearchFieldConfig(fieldName = "id", nodeName = WorkCalendar.NODENAME, seName = WorkCalendar.SENAME, nodeInstID = WorkCalendar.SENAME,  showOnUI = true)
protected String id;


@BSearchFieldConfig(fieldName = "defaultFlag", nodeName = WorkCalendar.NODENAME, seName = WorkCalendar.SENAME, nodeInstID = WorkCalendar.SENAME,  showOnUI = true)
protected int defaultFlag;


@BSearchFieldConfig(fieldName = "year", nodeName = WorkCalendar.NODENAME, seName = WorkCalendar.SENAME, nodeInstID = WorkCalendar.SENAME,  showOnUI = true)
protected int year;


@BSearchFieldConfig(fieldName = "note", nodeName = WorkCalendar.NODENAME, seName = WorkCalendar.SENAME, nodeInstID = WorkCalendar.SENAME,  showOnUI = true)
protected String note;


@BSearchFieldConfig(fieldName = "name", nodeName = WorkCalendar.NODENAME, seName = WorkCalendar.SENAME, nodeInstID = WorkCalendar.SENAME,  showOnUI = true)
protected String name;


@BSearchFieldConfig(fieldName = "refTemplateUUID", nodeName = WorkCalendar.NODENAME, seName = WorkCalendar.SENAME, nodeInstID = WorkCalendar.SENAME,  showOnUI = true)
protected String refTemplateUUID;


@BSearchFieldConfig(fieldName = "uuid", nodeName = WorkCalendar.NODENAME, seName = WorkCalendar.SENAME, nodeInstID = WorkCalendar.SENAME,  showOnUI = true)
protected String uuid;


@BSearchFieldConfig(fieldName = "client", nodeName = WorkCalendar.NODENAME, seName = WorkCalendar.SENAME, nodeInstID = WorkCalendar.SENAME,  showOnUI = true)
protected String client;


@BSearchFieldConfig(fieldName = "name", nodeName = CalendarTemplate.NODENAME, seName = CalendarTemplate.SENAME, nodeInstID = CalendarTemplate.SENAME,  showOnUI = true)
protected String calendarTemplateName;


@BSearchFieldConfig(fieldName = "note", nodeName = CalendarTemplate.NODENAME, seName = CalendarTemplate.SENAME, nodeInstID = CalendarTemplate.SENAME,  showOnUI = true)
protected String calendarTemplateNote;


@BSearchFieldConfig(fieldName = "id", nodeName = CalendarTemplate.NODENAME, seName = CalendarTemplate.SENAME, nodeInstID = CalendarTemplate.SENAME,  showOnUI = true)
protected String calendarTemplateId;


@BSearchFieldConfig(fieldName = "year", nodeName = CalendarTemplate.NODENAME, seName = CalendarTemplate.SENAME, nodeInstID = CalendarTemplate.SENAME,  showOnUI = true)
protected int calendarTemplateYear;

public String getId( ){
return this.id;
}

public void setId(String id){
this.id = id;
}

public int getDefaultFlag( ){
return this.defaultFlag;
}

public void setDefaultFlag(int defaultFlag){
this.defaultFlag = defaultFlag;
}

public int getYear( ){
return this.year;
}

public void setYear(int year){
this.year = year;
}

public String getNote( ){
return this.note;
}

public void setNote(String note){
this.note = note;
}

public String getName( ){
return this.name;
}

public void setName(String name){
this.name = name;
}

public String getRefTemplateUUID( ){
return this.refTemplateUUID;
}

public void setRefTemplateUUID(String refTemplateUUID){
this.refTemplateUUID = refTemplateUUID;
}

public String getUuid( ){
return this.uuid;
}

public void setUuid(String uuid){
this.uuid = uuid;
}

public String getClient( ){
return this.client;
}

public void setClient(String client){
this.client = client;
}

public String getCalendarTemplateName( ){
return this.calendarTemplateName;
}

public void setCalendarTemplateName(String calendarTemplateName){
this.calendarTemplateName = calendarTemplateName;
}

public String getCalendarTemplateNote( ){
return this.calendarTemplateNote;
}

public void setCalendarTemplateNote(String calendarTemplateNote){
this.calendarTemplateNote = calendarTemplateNote;
}

public String getCalendarTemplateId( ){
return this.calendarTemplateId;
}

public void setCalendarTemplateId(String calendarTemplateId){
this.calendarTemplateId = calendarTemplateId;
}

public int getCalendarTemplateYear( ){
return this.calendarTemplateYear;
}

public void setCalendarTemplateYear(int calendarTemplateYear){
this.calendarTemplateYear = calendarTemplateYear;
}


}
