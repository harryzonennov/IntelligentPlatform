package com.company.IntelligentPlatform.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.ServiceFlowCondFieldManager;
import com.company.IntelligentPlatform.common.model.ServiceFlowCondField;
import com.company.IntelligentPlatform.common.model.ServiceFlowCondGroup;
import com.company.IntelligentPlatform.common.model.ServiceFlowModel;


@Service
public class ServiceFlowCondFieldServiceUIModelExtension extends ServiceUIModelExtension {

    @Autowired
    protected ServiceFlowCondFieldManager serviceFlowCondFieldManager;

    public List<ServiceUIModelExtension> getChildUIModelExtensions() {
        List<ServiceUIModelExtension> resultList = new ArrayList<>();
        return resultList;
    }


    @Override
    public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
        List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
        List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
        ServiceUIModelExtensionUnion serviceFlowCondFieldExtensionUnion = new ServiceUIModelExtensionUnion();
        serviceFlowCondFieldExtensionUnion.setNodeInstId(ServiceFlowCondField.NODENAME);
        serviceFlowCondFieldExtensionUnion.setNodeName(ServiceFlowCondField.NODENAME);

// UI Model Configure of node:[ServiceFlowCondField]
        UIModelNodeMapConfigure serviceFlowCondFieldMap = new UIModelNodeMapConfigure();
        serviceFlowCondFieldMap.setSeName(ServiceFlowCondField.SENAME);
        serviceFlowCondFieldMap.setNodeName(ServiceFlowCondField.NODENAME);
        serviceFlowCondFieldMap.setNodeInstID(ServiceFlowCondField.NODENAME);
        serviceFlowCondFieldMap.setHostNodeFlag(true);
        Class<?>[] serviceFlowCondFieldConvToUIParas = {ServiceFlowCondField.class, ServiceFlowCondFieldUIModel.class};
        serviceFlowCondFieldMap.setConvToUIMethodParas(serviceFlowCondFieldConvToUIParas);
        serviceFlowCondFieldMap.setLogicManager(serviceFlowCondFieldManager);
        serviceFlowCondFieldMap.setConvToUIMethod(ServiceFlowCondFieldManager.METHOD_ConvServiceFlowCondFieldToUI);
        Class<?>[] ServiceFlowCondFieldConvUIToParas = {ServiceFlowCondFieldUIModel.class, ServiceFlowCondField.class};
        serviceFlowCondFieldMap.setConvUIToMethodParas(ServiceFlowCondFieldConvUIToParas);
        serviceFlowCondFieldMap.setConvUIToMethod(ServiceFlowCondFieldManager.METHOD_ConvUIToServiceFlowCondField);
        uiModelNodeMapList.add(serviceFlowCondFieldMap);

        UIModelNodeMapConfigure serviceFlowCondGroupMap = new UIModelNodeMapConfigure();
        serviceFlowCondGroupMap.setSeName(ServiceFlowCondGroup.SENAME);
        serviceFlowCondGroupMap.setNodeName(ServiceFlowCondGroup.NODENAME);
        serviceFlowCondGroupMap.setNodeInstID(ServiceFlowCondGroup.NODENAME);
        serviceFlowCondGroupMap.setHostNodeFlag(false);
        serviceFlowCondGroupMap.setBaseNodeInstID(ServiceFlowCondField.NODENAME);
        serviceFlowCondGroupMap.setLogicManager(serviceFlowCondFieldManager);
        serviceFlowCondGroupMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
        Class<?>[] serviceFlowCondGroupConvToUIParas = {ServiceFlowCondGroup.class, ServiceFlowCondFieldUIModel.class};
        serviceFlowCondGroupMap.setConvToUIMethodParas(serviceFlowCondGroupConvToUIParas);
        serviceFlowCondGroupMap.setConvToUIMethod(ServiceFlowCondFieldManager.METHOD_ConvServiceFlowCondGroupToFieldUI);


        UIModelNodeMapConfigure serviceFlowModelMap = new UIModelNodeMapConfigure();
        serviceFlowModelMap.setSeName(ServiceFlowModel.SENAME);
        serviceFlowModelMap.setNodeName(ServiceFlowModel.NODENAME);
        serviceFlowModelMap.setNodeInstID(ServiceFlowModel.SENAME);
        serviceFlowModelMap.setHostNodeFlag(false);
        serviceFlowModelMap.setBaseNodeInstID(ServiceFlowCondField.NODENAME);
        serviceFlowModelMap.setLogicManager(serviceFlowCondFieldManager);
        serviceFlowModelMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_ROOT_TO_CHILD);
        Class<?>[] serviceFlowModelConvToUIParas = {ServiceFlowModel.class, ServiceFlowCondFieldUIModel.class};
        serviceFlowModelMap.setConvToUIMethodParas(serviceFlowModelConvToUIParas);
        serviceFlowModelMap.setConvToUIMethod(ServiceFlowCondFieldManager.METHOD_ConvRootDocToFieldUI);

        uiModelNodeMapList.add(serviceFlowModelMap);

        serviceFlowCondFieldExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
        resultList.add(serviceFlowCondFieldExtensionUnion);
        return resultList;
    }


}
