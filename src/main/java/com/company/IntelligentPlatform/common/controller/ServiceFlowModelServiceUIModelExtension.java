package com.company.IntelligentPlatform.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.FlowRouterManager;
import com.company.IntelligentPlatform.common.service.ServiceFlowModelManager;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.FlowRouter;
import com.company.IntelligentPlatform.common.model.ServiceFlowModel;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

@Service
public class ServiceFlowModelServiceUIModelExtension extends ServiceUIModelExtension {

    @Autowired
    protected ServiceFlowCondGroupServiceUIModelExtension serviceFlowCondGroupServiceUIModelExtension;

    @Autowired
    protected ServiceFlowModelManager serviceFlowModelManager;

    @Autowired
    protected FlowRouterManager flowRouterManager;

    public List<ServiceUIModelExtension> getChildUIModelExtensions() {
        List<ServiceUIModelExtension> resultList = new ArrayList<>();
        resultList.add(serviceFlowCondGroupServiceUIModelExtension);
        return resultList;
    }

    @Override
    public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
        List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
        List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
        ServiceUIModelExtensionUnion serviceFlowModelExtensionUnion = new ServiceUIModelExtensionUnion();
        serviceFlowModelExtensionUnion.setNodeInstId(ServiceFlowModel.SENAME);
        serviceFlowModelExtensionUnion.setNodeName(ServiceFlowModel.NODENAME);

// UI Model Configure of node:[ServiceFlowModel]
        UIModelNodeMapConfigure serviceFlowModelMap = new UIModelNodeMapConfigure();
        serviceFlowModelMap.setSeName(ServiceFlowModel.SENAME);
        serviceFlowModelMap.setNodeName(ServiceFlowModel.NODENAME);
        serviceFlowModelMap.setNodeInstID(ServiceFlowModel.SENAME);
        serviceFlowModelMap.setHostNodeFlag(true);
        Class<?>[] serviceFlowModelConvToUIParas = {ServiceFlowModel.class, ServiceFlowModelUIModel.class};
        serviceFlowModelMap.setConvToUIMethodParas(serviceFlowModelConvToUIParas);
        serviceFlowModelMap.setConvToUIMethod(ServiceFlowModelManager.METHOD_ConvServiceFlowModelToUI);
        Class<?>[] ServiceFlowModelConvUIToParas = {ServiceFlowModelUIModel.class, ServiceFlowModel.class};
        serviceFlowModelMap.setConvUIToMethodParas(ServiceFlowModelConvUIToParas);
        serviceFlowModelMap.setConvUIToMethod(ServiceFlowModelManager.METHOD_ConvUIToServiceFlowModel);
        uiModelNodeMapList.add(serviceFlowModelMap);

// UI Model Configure of node:[FlowRouter]
        UIModelNodeMapConfigure flowRouterMap = new UIModelNodeMapConfigure();
        flowRouterMap.setSeName(FlowRouter.SENAME);
        flowRouterMap.setNodeName(FlowRouter.NODENAME);
        flowRouterMap.setNodeInstID(FlowRouter.SENAME);
        flowRouterMap.setBaseNodeInstID(ServiceFlowModel.SENAME);
        flowRouterMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
        flowRouterMap.setServiceEntityManager(flowRouterManager);
        List<SearchConfigConnectCondition> flowRouterConditionList = new ArrayList<>();
        SearchConfigConnectCondition flowRouterCondition0 = new SearchConfigConnectCondition();
        flowRouterCondition0.setSourceFieldName("refRouterUUID");
        flowRouterCondition0.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
        flowRouterConditionList.add(flowRouterCondition0);
        flowRouterMap.setConnectionConditions(flowRouterConditionList);
        Class<?>[] flowRouterConvToUIParas = {FlowRouter.class, ServiceFlowModelUIModel.class};
        flowRouterMap.setConvToUIMethodParas(flowRouterConvToUIParas);
        flowRouterMap.setConvToUIMethod(ServiceFlowModelManager.METHOD_ConvFlowRouterToUI);
        uiModelNodeMapList.add(flowRouterMap);
        serviceFlowModelExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
        resultList.add(serviceFlowModelExtensionUnion);
        return resultList;
    }

}
