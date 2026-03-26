package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.FlowRouter;
import com.company.IntelligentPlatform.common.model.FlowRouterExtendClass;
import com.company.IntelligentPlatform.common.model.ServiceFlowCondField;
import com.company.IntelligentPlatform.common.model.ServiceFlowCondGroup;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

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
