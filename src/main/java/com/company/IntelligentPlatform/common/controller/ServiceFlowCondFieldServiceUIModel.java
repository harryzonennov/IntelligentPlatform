package com.company.IntelligentPlatform.common.controller;

import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.ServiceFlowCondField;

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
