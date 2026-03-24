package com.company.IntelligentPlatform.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.ServiceFlowCondFieldManager;
import com.company.IntelligentPlatform.common.service.ServiceFlowCondGroupManager;
import com.company.IntelligentPlatform.common.service.ServiceFlowModelManager;
import com.company.IntelligentPlatform.common.model.ServiceFlowCondField;
import com.company.IntelligentPlatform.common.model.ServiceFlowCondGroup;
import com.company.IntelligentPlatform.common.model.ServiceFlowModel;


@Service
public class ServiceFlowCondGroupServiceUIModelExtension extends ServiceUIModelExtension {

    @Autowired
    protected ServiceFlowCondGroupManager serviceFlowCondGroupManager;

    @Autowired
    protected ServiceFlowCondFieldServiceUIModelExtension serviceFlowCondFieldServiceUIModelExtension;

    @Autowired
    protected ServiceFlowModelManager serviceFlowModelManager;


    public List<ServiceUIModelExtension> getChildUIModelExtensions() {
        List<ServiceUIModelExtension> resultList = new ArrayList<>();
        resultList.add(serviceFlowCondFieldServiceUIModelExtension);
        return resultList;
    }


    @Override
    public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
        List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
        List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
        ServiceUIModelExtensionUnion serviceFlowCondGroupExtensionUnion = new ServiceUIModelExtensionUnion();
        serviceFlowCondGroupExtensionUnion.setNodeInstId(ServiceFlowCondGroup.NODENAME);
        serviceFlowCondGroupExtensionUnion.setNodeName(ServiceFlowCondGroup.NODENAME);

// UI Model Configure of node:[ServiceFlowCondGroup]
        UIModelNodeMapConfigure serviceFlowCondGroupMap = new UIModelNodeMapConfigure();
        serviceFlowCondGroupMap.setSeName(ServiceFlowCondGroup.SENAME);
        serviceFlowCondGroupMap.setNodeName(ServiceFlowCondGroup.NODENAME);
        serviceFlowCondGroupMap.setNodeInstID(ServiceFlowCondGroup.NODENAME);
        serviceFlowCondGroupMap.setLogicManager(serviceFlowCondGroupManager);
        serviceFlowCondGroupMap.setHostNodeFlag(true);
        Class<?>[] serviceFlowCondGroupConvToUIParas = {ServiceFlowCondGroup.class, ServiceFlowCondGroupUIModel.class};
        serviceFlowCondGroupMap.setConvToUIMethodParas(serviceFlowCondGroupConvToUIParas);
        serviceFlowCondGroupMap.setConvToUIMethod(ServiceFlowCondGroupManager.METHOD_ConvServiceFlowCondGroupToUI);
        Class<?>[] ServiceFlowCondGroupConvUIToParas = {ServiceFlowCondGroupUIModel.class, ServiceFlowCondGroup.class};
        serviceFlowCondGroupMap.setConvUIToMethodParas(ServiceFlowCondGroupConvUIToParas);
        serviceFlowCondGroupMap.setConvUIToMethod(ServiceFlowCondGroupManager.METHOD_ConvUIToServiceFlowCondGroup);
        uiModelNodeMapList.add(serviceFlowCondGroupMap);

        UIModelNodeMapConfigure serviceFlowModelMap = new UIModelNodeMapConfigure();
        serviceFlowModelMap.setSeName(ServiceFlowModel.SENAME);
        serviceFlowModelMap.setNodeName(ServiceFlowModel.NODENAME);
        serviceFlowModelMap.setNodeInstID(ServiceFlowModel.SENAME);
        serviceFlowModelMap.setHostNodeFlag(false);
        serviceFlowModelMap.setBaseNodeInstID(ServiceFlowCondGroup.NODENAME);
        serviceFlowModelMap.setLogicManager(serviceFlowCondGroupManager);
        serviceFlowModelMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_ROOT_TO_CHILD);
        Class<?>[] serviceFlowModelConvToUIParas = {ServiceFlowModel.class, ServiceFlowCondGroupUIModel.class};
        serviceFlowModelMap.setConvToUIMethodParas(serviceFlowModelConvToUIParas);
        serviceFlowModelMap.setConvToUIMethod(ServiceFlowCondGroupManager.METHOD_ConvRootDocToFieldUI);

        uiModelNodeMapList.add(serviceFlowModelMap);

        serviceFlowCondGroupExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
        resultList.add(serviceFlowCondGroupExtensionUnion);
        return resultList;
    }


}
