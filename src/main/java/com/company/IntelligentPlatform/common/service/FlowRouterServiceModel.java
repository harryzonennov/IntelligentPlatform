package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.FlowRouter;
import com.company.IntelligentPlatform.common.model.FlowRouterExtendClass;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class FlowRouterServiceModel extends ServiceModule {

    @IServiceModuleFieldConfig(nodeName = FlowRouter.NODENAME, nodeInstId = FlowRouter.SENAME)
    protected FlowRouter flowRouter;

    @IServiceModuleFieldConfig(nodeName = FlowRouterExtendClass.NODENAME, nodeInstId = FlowRouterExtendClass.NODENAME)
    protected List<FlowRouterExtendClassServiceModel> flowRouterExtendClassList = new ArrayList<>();

    public FlowRouter getFlowRouter() {
        return this.flowRouter;
    }

    public void setFlowRouter(FlowRouter flowRouter) {
        this.flowRouter = flowRouter;
    }

    public List<FlowRouterExtendClassServiceModel> getFlowRouterExtendClassList() {
        return this.flowRouterExtendClassList;
    }

    public void setFlowRouterExtendClassList(List<FlowRouterExtendClassServiceModel> flowRouterExtendClassList) {
        this.flowRouterExtendClassList = flowRouterExtendClassList;
    }

}
