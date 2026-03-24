package com.company.IntelligentPlatform.common.controller;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.service.FlowRouterManager;
import com.company.IntelligentPlatform.common.model.FlowRouter;
import com.company.IntelligentPlatform.common.model.FlowRouterExtendClass;


public class FlowRouterServiceUIModel extends ServiceUIModule {

    @IServiceUIModuleFieldConfig(nodeName = FlowRouter.NODENAME, nodeInstId = FlowRouter.SENAME)
    protected FlowRouterUIModel flowRouterUIModel;

    @IServiceUIModuleFieldConfig(nodeName = FlowRouterExtendClass.NODENAME, nodeInstId =
            FlowRouterExtendClass.NODENAME)
    protected List<FlowRouterExtendClassServiceUIModel> flowRouterExtendClassUIModelList =
            new ArrayList<>();


    public FlowRouterUIModel getFlowRouterUIModel() {
        return this.flowRouterUIModel;
    }


    public void setFlowRouterUIModel(FlowRouterUIModel flowRouterUIModel) {
        this.flowRouterUIModel = flowRouterUIModel;
    }


    public List<FlowRouterExtendClassServiceUIModel> getFlowRouterExtendClassUIModelList() {
        return this.flowRouterExtendClassUIModelList;
    }


    public void setFlowRouterExtendClassUIModelList(List<FlowRouterExtendClassServiceUIModel> flowRouterExtendClassUIModelList) {
        this.flowRouterExtendClassUIModelList = flowRouterExtendClassUIModelList;
    }


}
