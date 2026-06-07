package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceFlowCondField;
import com.company.IntelligentPlatform.platform.model.ServiceFlowCondGroup;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

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
