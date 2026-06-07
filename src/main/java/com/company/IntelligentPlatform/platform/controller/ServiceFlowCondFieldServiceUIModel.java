package com.company.IntelligentPlatform.platform.controller;

import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;
import com.company.IntelligentPlatform.platform.model.ServiceFlowCondField;

public class ServiceFlowCondFieldServiceUIModel extends ServiceUIModule {

    @IServiceUIModuleFieldConfig(nodeName = ServiceFlowCondField.NODENAME, nodeInstId = ServiceFlowCondField.NODENAME)
    protected ServiceFlowCondFieldUIModel serviceFlowCondFieldUIModel;

    public ServiceFlowCondFieldUIModel getServiceFlowCondFieldUIModel() {
        return serviceFlowCondFieldUIModel;
    }

    public void setServiceFlowCondFieldUIModel(ServiceFlowCondFieldUIModel serviceFlowCondFieldUIModel) {
        this.serviceFlowCondFieldUIModel = serviceFlowCondFieldUIModel;
    }
}
