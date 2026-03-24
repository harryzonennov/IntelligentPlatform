package com.company.IntelligentPlatform.common.controller;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.ServiceFlowCondGroup;
import com.company.IntelligentPlatform.common.model.ServiceFlowModel;


public class ServiceFlowModelServiceUIModel extends ServiceUIModule {

    @IServiceUIModuleFieldConfig(nodeName = ServiceFlowModel.NODENAME, nodeInstId = ServiceFlowModel.SENAME)
    protected ServiceFlowModelUIModel serviceFlowModelUIModel;

    @IServiceUIModuleFieldConfig(nodeName = ServiceFlowCondGroup.NODENAME, nodeInstId = ServiceFlowCondGroup.NODENAME)
    protected List<ServiceFlowCondGroupServiceUIModel> serviceFlowCondGroupUIModelList = new ArrayList<>();

    public ServiceFlowModelUIModel getServiceFlowModelUIModel() {
        return serviceFlowModelUIModel;
    }

    public void setServiceFlowModelUIModel(ServiceFlowModelUIModel serviceFlowModelUIModel) {
        this.serviceFlowModelUIModel = serviceFlowModelUIModel;
    }

    public List<ServiceFlowCondGroupServiceUIModel> getServiceFlowCondGroupUIModelList() {
        return serviceFlowCondGroupUIModelList;
    }

    public void setServiceFlowCondGroupUIModelList(List<ServiceFlowCondGroupServiceUIModel> serviceFlowCondGroupUIModelList) {
        this.serviceFlowCondGroupUIModelList = serviceFlowCondGroupUIModelList;
    }
}
