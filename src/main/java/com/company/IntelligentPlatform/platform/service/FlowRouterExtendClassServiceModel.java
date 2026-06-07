package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.FlowRouterExtendClass;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

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
