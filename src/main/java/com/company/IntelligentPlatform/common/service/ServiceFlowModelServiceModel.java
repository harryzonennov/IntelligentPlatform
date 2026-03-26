package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.FlowRouter;
import com.company.IntelligentPlatform.common.model.FlowRouterExtendClass;
import com.company.IntelligentPlatform.common.model.ServiceFlowCondGroup;
import com.company.IntelligentPlatform.common.model.ServiceFlowModel;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class ServiceFlowModelServiceModel extends ServiceModule {

    @IServiceModuleFieldConfig(nodeName = ServiceFlowModel.NODENAME, nodeInstId = ServiceFlowModel.SENAME)
    protected ServiceFlowModel serviceFlowModel;

    @IServiceModuleFieldConfig(nodeName = ServiceFlowCondGroup.NODENAME, nodeInstId = ServiceFlowCondGroup.NODENAME)
    protected List<ServiceFlowCondGroupServiceModel> serviceFlowCondGroupList =
            new ArrayList<>();

    public ServiceFlowModel getServiceFlowModel() {
        return serviceFlowModel;
    }

    public void setServiceFlowModel(ServiceFlowModel serviceFlowModel) {
        this.serviceFlowModel = serviceFlowModel;
    }

    public List<ServiceFlowCondGroupServiceModel> getServiceFlowCondGroupList() {
        return serviceFlowCondGroupList;
    }

    public void setServiceFlowCondGroupList(List<ServiceFlowCondGroupServiceModel> serviceFlowCondGroupList) {
        this.serviceFlowCondGroupList = serviceFlowCondGroupList;
    }
}
