package com.company.IntelligentPlatform.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.FlowRouterExtendClassManager;
import com.company.IntelligentPlatform.common.service.FlowRouterManager;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.FlowRouter;
import com.company.IntelligentPlatform.common.model.FlowRouterExtendClass;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

@Service
public class FlowRouterExtendClassServiceUIModelExtension extends ServiceUIModelExtension {

    @Autowired
    protected FlowRouterExtendClassManager flowRouterExtendClassManager;

    @Autowired
    protected LogonUserManager logonUserManager;

    public List<ServiceUIModelExtension> getChildUIModelExtensions() {
        List<ServiceUIModelExtension> resultList = new ArrayList<>();
        return resultList;
    }

    @Override
    public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
        List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
        List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
        ServiceUIModelExtensionUnion flowRouterExtendClassExtensionUnion = new ServiceUIModelExtensionUnion();
        flowRouterExtendClassExtensionUnion.setNodeInstId(FlowRouterExtendClass.NODENAME);
        flowRouterExtendClassExtensionUnion.setNodeName(FlowRouterExtendClass.NODENAME);

// UI Model Configure of node:[FlowRouterExtendClass]
        UIModelNodeMapConfigure flowRouterExtendClassMap = new UIModelNodeMapConfigure();
        flowRouterExtendClassMap.setSeName(FlowRouterExtendClass.SENAME);
        flowRouterExtendClassMap.setNodeName(FlowRouterExtendClass.NODENAME);
        flowRouterExtendClassMap.setNodeInstID(FlowRouterExtendClass.NODENAME);
        flowRouterExtendClassMap.setHostNodeFlag(true);
        flowRouterExtendClassMap.setLogicManager(flowRouterExtendClassManager);
        Class<?>[] flowRouterExtendClassConvToUIParas = {FlowRouterExtendClass.class,
                FlowRouterExtendClassUIModel.class};
        flowRouterExtendClassMap.setConvToUIMethodParas(flowRouterExtendClassConvToUIParas);
        flowRouterExtendClassMap.setConvToUIMethod(FlowRouterExtendClassManager.METHOD_ConvFlowRouterExtendClassToUI);
        Class<?>[] FlowRouterExtendClassConvUIToParas = {FlowRouterExtendClassUIModel.class,
                FlowRouterExtendClass.class};
        flowRouterExtendClassMap.setConvUIToMethodParas(FlowRouterExtendClassConvUIToParas);
        flowRouterExtendClassMap.setConvUIToMethod(FlowRouterExtendClassManager.METHOD_ConvUIToFlowRouterExtendClass);
        uiModelNodeMapList.add(flowRouterExtendClassMap);

        UIModelNodeMapConfigure flowRouterMap = new UIModelNodeMapConfigure();
        flowRouterMap.setSeName(FlowRouter.SENAME);
        flowRouterMap.setNodeName(FlowRouter.NODENAME);
        flowRouterMap.setNodeInstID(FlowRouter.SENAME);
        flowRouterMap.setHostNodeFlag(false);
        flowRouterMap.setLogicManager(flowRouterExtendClassManager);
        flowRouterMap.setBaseNodeInstID(FlowRouterExtendClass.NODENAME);
        flowRouterMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_ROOT_TO_CHILD);
        Class<?>[] flowRouterConvToUIParas = {FlowRouter.class,
                FlowRouterExtendClassUIModel.class};
        flowRouterMap.setConvToUIMethodParas(flowRouterConvToUIParas);
        flowRouterMap.setConvToUIMethod(FlowRouterExtendClassManager.METHOD_ConvFlowRouterToExtendUI);
        uiModelNodeMapList.add(flowRouterMap);

        // UI Model Configure of node:[LogonUser]
        UIModelNodeMapConfigure logonUserMap = new UIModelNodeMapConfigure();
        logonUserMap.setSeName(LogonUser.SENAME);
        logonUserMap.setNodeName(LogonUser.NODENAME);
        logonUserMap.setNodeInstID(LogonUser.SENAME);
        logonUserMap.setBaseNodeInstID(FlowRouterExtendClass.NODENAME);
        logonUserMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
        logonUserMap.setServiceEntityManager(logonUserManager);
        List<SearchConfigConnectCondition> logonUserConditionList = new ArrayList<>();
        SearchConfigConnectCondition logonUserCondition0 = new SearchConfigConnectCondition();
        logonUserCondition0.setSourceFieldName("refDirectAssigneeUUID");
        logonUserCondition0.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
        logonUserConditionList.add(logonUserCondition0);
        logonUserMap.setConnectionConditions(logonUserConditionList);
        logonUserMap.setLogicManager(flowRouterExtendClassManager);
        Class<?>[] logonUserConvToUIParas = {LogonUser.class, FlowRouterExtendClassUIModel.class};
        logonUserMap.setConvToUIMethodParas(logonUserConvToUIParas);
        logonUserMap.setConvToUIMethod(FlowRouterExtendClassManager.METHOD_ConvLogonUserToUI);
        uiModelNodeMapList.add(logonUserMap);
        flowRouterExtendClassExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
        resultList.add(flowRouterExtendClassExtensionUnion);
        return resultList;
    }

}
