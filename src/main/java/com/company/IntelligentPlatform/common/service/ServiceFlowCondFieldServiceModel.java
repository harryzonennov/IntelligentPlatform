package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceFlowCondField;
import com.company.IntelligentPlatform.common.model.ServiceFlowCondGroup;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;


public class ServiceFlowCondFieldServiceModel extends ServiceModule {

    @IServiceModuleFieldConfig(nodeName = ServiceFlowCondField.NODENAME, nodeInstId = ServiceFlowCondField.NODENAME)
    protected ServiceFlowCondField serviceFlowCondField;

    public ServiceFlowCondField getServiceFlowCondField() {
        return serviceFlowCondField;
    }

    public void setServiceFlowCondField(ServiceFlowCondField serviceFlowCondField) {
        this.serviceFlowCondField = serviceFlowCondField;
    }
}
