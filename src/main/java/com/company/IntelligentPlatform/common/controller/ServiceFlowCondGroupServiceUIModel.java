package com.company.IntelligentPlatform.common.controller;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.ServiceFlowCondField;
import com.company.IntelligentPlatform.common.model.ServiceFlowCondGroup;

public class ServiceFlowCondGroupServiceUIModel extends ServiceUIModule {

    @IServiceUIModuleFieldConfig(nodeName = ServiceFlowCondGroup.NODENAME, nodeInstId = ServiceFlowCondGroup.NODENAME)
    protected ServiceFlowCondGroupUIModel serviceFlowCondGroupUIModel;

    @IServiceUIModuleFieldConfig(nodeName = ServiceFlowCondField.NODENAME, nodeInstId = ServiceFlowCondField.NODENAME)
    protected List<ServiceFlowCondFieldServiceUIModel> serviceFlowCondFieldUIModelList =
            new ArrayList<>();

    public ServiceFlowCondGroupUIModel getServiceFlowCondGroupUIModel() {
        return this.serviceFlowCondGroupUIModel;
    }

    public void setServiceFlowCondGroupUIModel(ServiceFlowCondGroupUIModel serviceFlowCondGroupUIModel) {
        this.serviceFlowCondGroupUIModel = serviceFlowCondGroupUIModel;
    }

    public List<ServiceFlowCondFieldServiceUIModel> getServiceFlowCondFieldUIModelList() {
        return this.serviceFlowCondFieldUIModelList;
    }

    public void setServiceFlowCondFieldUIModelList(List<ServiceFlowCondFieldServiceUIModel> serviceFlowCondFieldUIModelList) {
        this.serviceFlowCondFieldUIModelList = serviceFlowCondFieldUIModelList;
    }

}
