package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.model.MaterialConfigureTemplate;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;

@Component
public class MaterialConfigureTemplateSearchModel extends SEUIComModel{

@BSearchFieldConfig(fieldName = "id", nodeName = MaterialConfigureTemplate.NODENAME, seName = MaterialConfigureTemplate.SENAME, nodeInstID = MaterialConfigureTemplate.SENAME,  showOnUI = true)
protected String id;

@BSearchFieldConfig(fieldName = "note", nodeName = MaterialConfigureTemplate.NODENAME, seName = MaterialConfigureTemplate.SENAME, nodeInstID = MaterialConfigureTemplate.SENAME,  showOnUI = true)
protected String note;

@BSearchFieldConfig(fieldName = "uuid", nodeName = MaterialConfigureTemplate.NODENAME, seName = MaterialConfigureTemplate.SENAME, nodeInstID = MaterialConfigureTemplate.SENAME,  showOnUI = true)
protected String uuid;

@BSearchFieldConfig(fieldName = "client", nodeName = MaterialConfigureTemplate.NODENAME, seName = MaterialConfigureTemplate.SENAME, nodeInstID = MaterialConfigureTemplate.SENAME,  showOnUI = true)
protected String client;

@BSearchFieldConfig(fieldName = "name", nodeName = MaterialConfigureTemplate.NODENAME, seName = MaterialConfigureTemplate.SENAME, nodeInstID = MaterialConfigureTemplate.SENAME,  showOnUI = true)
protected String name;

public String getId( ){
return this.id;
}

public void setId(String id){
this.id = id;
}

public String getNote( ){
return this.note;
}

public void setNote(String note){
this.note = note;
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

public String getName( ){
return this.name;
}

public void setName(String name){
this.name = name;
}

}
