package com.company.IntelligentPlatform.platform.controller;

import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;
import com.company.IntelligentPlatform.platform.model.FlowRouterExtendClass;

public class FlowRouterExtendClassServiceUIModel extends ServiceUIModule {

    @IServiceUIModuleFieldConfig(nodeName = FlowRouterExtendClass.NODENAME, nodeInstId = FlowRouterExtendClass.NODENAME)
    protected FlowRouterExtendClassUIModel flowRouterExtendClassUIModel;

    public FlowRouterExtendClassUIModel getFlowRouterExtendClassUIModel() {
        return flowRouterExtendClassUIModel;
    }

    public void setFlowRouterExtendClassUIModel(FlowRouterExtendClassUIModel flowRouterExtendClassUIModel) {
        this.flowRouterExtendClassUIModel = flowRouterExtendClassUIModel;
    }
}
