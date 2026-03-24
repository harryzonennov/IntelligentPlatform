package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.model.CalendarTempWorkSchedule;
import com.company.IntelligentPlatform.common.model.CalendarTemplate;
import com.company.IntelligentPlatform.common.model.CalendarTemplateItem;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;


public class CalendarTemplateServiceModel extends ServiceModule{

@IServiceModuleFieldConfig(nodeName=CalendarTemplate.NODENAME, nodeInstId=CalendarTemplate.SENAME )
protected CalendarTemplate calendarTemplate;


@IServiceModuleFieldConfig(nodeName=CalendarTempWorkSchedule.NODENAME, nodeInstId=CalendarTempWorkSchedule.NODENAME)
protected List<ServiceEntityNode> calendarTempWorkScheduleList = new ArrayList<>();


@IServiceModuleFieldConfig(nodeName=CalendarTemplateItem.NODENAME, nodeInstId=CalendarTemplateItem.NODENAME)
protected List<ServiceEntityNode> calendarTemplateItemList = new ArrayList<>();


public CalendarTemplate getCalendarTemplate( ){
return this.calendarTemplate;
}


public void setCalendarTemplate(CalendarTemplate calendarTemplate){
this.calendarTemplate = calendarTemplate;
}


public List<ServiceEntityNode> getCalendarTempWorkScheduleList( ){
return this.calendarTempWorkScheduleList;
}


public void setCalendarTempWorkScheduleList(List<ServiceEntityNode> calendarTempWorkScheduleList){
this.calendarTempWorkScheduleList = calendarTempWorkScheduleList;
}


public List<ServiceEntityNode> getCalendarTemplateItemList( ){
return this.calendarTemplateItemList;
}


public void setCalendarTemplateItemList(List<ServiceEntityNode> calendarTemplateItemList){
this.calendarTemplateItemList = calendarTemplateItemList;
}



}
