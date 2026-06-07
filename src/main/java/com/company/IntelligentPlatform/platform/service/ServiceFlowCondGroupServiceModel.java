package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.FlowRouter;
import com.company.IntelligentPlatform.platform.model.FlowRouterExtendClass;
import com.company.IntelligentPlatform.platform.model.ServiceFlowCondField;
import com.company.IntelligentPlatform.platform.model.ServiceFlowCondGroup;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class ServiceFlowCondGroupServiceModel extends ServiceModule {

    @IServiceModuleFieldConfig(nodeName = ServiceFlowCondGroup.NODENAME, nodeInstId = ServiceFlowCondGroup.NODENAME)
    protected ServiceFlowCondGroup serviceFlowCondGroup;

    @IServiceModuleFieldConfig(nodeName = ServiceFlowCondField.NODENAME, nodeInstId = ServiceFlowCondField.NODENAME)
    protected List<ServiceFlowCondFieldServiceModel> serviceFlowCondFieldList = new ArrayList<>();

    public ServiceFlowCondGroup getServiceFlowCondGroup() {
        return serviceFlowCondGroup;
    }

    public void setServiceFlowCondGroup(ServiceFlowCondGroup serviceFlowCondGroup) {
        this.serviceFlowCondGroup = serviceFlowCondGroup;
    }

    public List<ServiceFlowCondFieldServiceModel> getServiceFlowCondFieldList() {
        return serviceFlowCondFieldList;
    }

    public void setServiceFlowCondFieldList(List<ServiceFlowCondFieldServiceModel> serviceFlowCondFieldList) {
        this.serviceFlowCondFieldList = serviceFlowCondFieldList;
    }
}
