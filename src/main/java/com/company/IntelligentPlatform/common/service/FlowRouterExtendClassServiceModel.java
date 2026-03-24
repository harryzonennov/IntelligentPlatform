package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.FlowRouterExtendClass;
import com.company.IntelligentPlatform.common.model.ServiceModule;


public class FlowRouterExtendClassServiceModel extends ServiceModule {

    @IServiceModuleFieldConfig(nodeName = FlowRouterExtendClass.NODENAME, nodeInstId = FlowRouterExtendClass.NODENAME)
    protected FlowRouterExtendClass flowRouterExtendClass;

    public FlowRouterExtendClass getFlowRouterExtendClass() {
        return flowRouterExtendClass;
    }

    public void setFlowRouterExtendClass(FlowRouterExtendClass flowRouterExtendClass) {
        this.flowRouterExtendClass = flowRouterExtendClass;
    }

}
