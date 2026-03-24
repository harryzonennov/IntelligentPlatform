package com.company.IntelligentPlatform.common.controller;

import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.FlowRouterExtendClass;

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
