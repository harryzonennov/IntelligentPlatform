package com.company.IntelligentPlatform.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.FlowRouterManager;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.FlowRouter;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;


@Service
public class FlowRouterServiceUIModelExtension extends ServiceUIModelExtension {

    @Autowired
    protected FlowRouterExtendClassServiceUIModelExtension flowRouterExtendClassServiceUIModelExtension;


    @Autowired
    protected FlowRouterManager flowRouterManager;


    @Autowired
    protected LogonUserManager logonUserManager;


    public List<ServiceUIModelExtension> getChildUIModelExtensions() {
        List<ServiceUIModelExtension> resultList = new ArrayList<>();
        resultList.add(flowRouterExtendClassServiceUIModelExtension);
        return resultList;
    }


    @Override
    public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
        List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
        List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
        ServiceUIModelExtensionUnion flowRouterExtensionUnion = new ServiceUIModelExtensionUnion();
        flowRouterExtensionUnion.setNodeInstId(FlowRouter.SENAME);
        flowRouterExtensionUnion.setNodeName(FlowRouter.NODENAME);

// UI Model Configure of node:[FlowRouter]
        UIModelNodeMapConfigure flowRouterMap = new UIModelNodeMapConfigure();
        flowRouterMap.setSeName(FlowRouter.SENAME);
        flowRouterMap.setNodeName(FlowRouter.NODENAME);
        flowRouterMap.setNodeInstID(FlowRouter.SENAME);
        flowRouterMap.setHostNodeFlag(true);
        Class<?>[] flowRouterConvToUIParas = {FlowRouter.class, FlowRouterUIModel.class};
        flowRouterMap.setConvToUIMethodParas(flowRouterConvToUIParas);
        flowRouterMap.setConvToUIMethod(FlowRouterManager.METHOD_ConvFlowRouterToUI);
        Class<?>[] FlowRouterConvUIToParas = {FlowRouterUIModel.class, FlowRouter.class};
        flowRouterMap.setConvUIToMethodParas(FlowRouterConvUIToParas);
        flowRouterMap.setConvUIToMethod(FlowRouterManager.METHOD_ConvUIToFlowRouter);
        uiModelNodeMapList.add(flowRouterMap);



        flowRouterExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
        resultList.add(flowRouterExtensionUnion);
        return resultList;
    }


}
