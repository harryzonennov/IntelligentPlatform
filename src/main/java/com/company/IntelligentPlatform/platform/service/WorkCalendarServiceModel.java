package com.company.IntelligentPlatform.platform.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.platform.model.WorkCalendar;
import com.company.IntelligentPlatform.platform.model.WorkCalendarDayItem;
import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

public class WorkCalendarServiceModel extends ServiceModule{

@IServiceModuleFieldConfig(nodeName=WorkCalendar.NODENAME, nodeInstId=WorkCalendar.SENAME )
protected WorkCalendar workCalendar;

@IServiceModuleFieldConfig(nodeName=WorkCalendarDayItem.NODENAME, nodeInstId=WorkCalendarDayItem.NODENAME)
protected List<ServiceEntityNode> workCalendarDayItemList = new ArrayList<>();

public List<ServiceEntityNode> getWorkCalendarDayItemList( ){
return this.workCalendarDayItemList;
}

public void setWorkCalendarDayItemList(List<ServiceEntityNode> workCalendarDayItemList){
this.workCalendarDayItemList = workCalendarDayItemList;
}

public WorkCalendar getWorkCalendar( ){
return this.workCalendar;
}

public void setWorkCalendar(WorkCalendar workCalendar){
this.workCalendar = workCalendar;
}

}
