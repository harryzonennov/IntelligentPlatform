package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.dto.ServiceUIMeta;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceExtensionSetting;

import java.util.List;

public class ServiceExtensionSimModel {

    protected String modelId;

    protected ServiceExtensionSetting serviceExtensionSetting;

    protected List<ServiceEntityNode> serviceExtendFieldList;

    protected ServiceUIMeta serviceUIMeta;

    public ServiceExtensionSimModel(){

    }

    public ServiceExtensionSimModel(String modelId, ServiceExtensionSetting serviceExtensionSetting,
                                    List<ServiceEntityNode> serviceExtendFieldList) {
        this.modelId = modelId;
        this.serviceExtensionSetting = serviceExtensionSetting;
        this.serviceExtendFieldList = serviceExtendFieldList;
    }

    public ServiceExtensionSimModel(String modelId, ServiceExtensionSetting serviceExtensionSetting,
                                    List<ServiceEntityNode> serviceExtendFieldList, ServiceUIMeta serviceUIMeta) {
        this.modelId = modelId;
        this.serviceExtensionSetting = serviceExtensionSetting;
        this.serviceExtendFieldList = serviceExtendFieldList;
        this.serviceUIMeta = serviceUIMeta;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public ServiceExtensionSetting getServiceExtensionSetting() {
        return serviceExtensionSetting;
    }

    public void setServiceExtensionSetting(ServiceExtensionSetting serviceExtensionSetting) {
        this.serviceExtensionSetting = serviceExtensionSetting;
    }

    public List<ServiceEntityNode> getServiceExtendFieldList() {
        return serviceExtendFieldList;
    }

    public void setServiceExtendFieldList(List<ServiceEntityNode> serviceExtendFieldList) {
        this.serviceExtendFieldList = serviceExtendFieldList;
    }

    public ServiceUIMeta getServiceUIMeta() {
        return serviceUIMeta;
    }

    public void setServiceUIMeta(ServiceUIMeta serviceUIMeta) {
        this.serviceUIMeta = serviceUIMeta;
    }
}
