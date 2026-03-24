package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.NavigationSystemSettingManager;
import com.company.IntelligentPlatform.common.model.NavigationSystemSettingActionNode;
import com.company.IntelligentPlatform.common.model.NavigationSystemSetting;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class NavigationSystemSettingServiceUIModelExtension extends ServiceUIModelExtension {

    @Autowired
    protected NavigationGroupSettingServiceUIModelExtension navigationGroupSettingServiceUIModelExtension;

    @Autowired
    protected NavigationSystemSettingManager navigationSystemSettingManager;

    @Autowired
    protected DocActionNodeProxy docActionNodeProxy;


    public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
        List<ServiceUIModelExtension> resultList = new ArrayList<>();
        resultList.add(navigationGroupSettingServiceUIModelExtension);

        resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
                NavigationSystemSettingActionNode.SENAME,
                NavigationSystemSettingActionNode.NODENAME,
                NavigationSystemSettingActionNode.NODEINST_ACTION_ARCHIVE,
                navigationSystemSettingManager, NavigationSystemSettingActionNode.DOC_ACTION_ARCHIVE
        )));
        resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
                NavigationSystemSettingActionNode.SENAME,
                NavigationSystemSettingActionNode.NODENAME,
                NavigationSystemSettingActionNode.NODEINST_ACTION_SUBMIT,
                navigationSystemSettingManager, NavigationSystemSettingActionNode.DOC_ACTION_SUBMIT
        )));
        resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
                NavigationSystemSettingActionNode.SENAME,
                NavigationSystemSettingActionNode.NODENAME,
                NavigationSystemSettingActionNode.NODEINST_ACTION_ACTIVE,
                navigationSystemSettingManager, NavigationSystemSettingActionNode.DOC_ACTION_ACTIVE
        )));
        resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
                NavigationSystemSettingActionNode.SENAME,
                NavigationSystemSettingActionNode.NODENAME,
                NavigationSystemSettingActionNode.NODEINST_ACTION_ARCHIVE,
                navigationSystemSettingManager, NavigationSystemSettingActionNode.DOC_ACTION_ARCHIVE
        )));
        resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
                NavigationSystemSettingActionNode.SENAME,
                NavigationSystemSettingActionNode.NODENAME,
                NavigationSystemSettingActionNode.NODEINST_ACTION_REINIT,
                navigationSystemSettingManager, NavigationSystemSettingActionNode.DOC_ACTION_REINIT
        )));
        resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
                NavigationSystemSettingActionNode.SENAME,
                NavigationSystemSettingActionNode.NODENAME,
                NavigationSystemSettingActionNode.NODEINST_ACTION_APPROVE,
                navigationSystemSettingManager, NavigationSystemSettingActionNode.DOC_ACTION_APPROVE
        )));
        return resultList;
    }


    @Override
    public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
        List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
        List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
        ServiceUIModelExtensionUnion navigationSystemSettingExtensionUnion = new ServiceUIModelExtensionUnion();
        navigationSystemSettingExtensionUnion.setNodeInstId(NavigationSystemSetting.SENAME);
        navigationSystemSettingExtensionUnion.setNodeName(NavigationSystemSetting.NODENAME);

// UI Model Configure of node:[NavigationSystemSetting]
        UIModelNodeMapConfigure navigationSystemSettingMap = new UIModelNodeMapConfigure();
        navigationSystemSettingMap.setSeName(NavigationSystemSetting.SENAME);
        navigationSystemSettingMap.setNodeName(NavigationSystemSetting.NODENAME);
        navigationSystemSettingMap.setNodeInstID(NavigationSystemSetting.SENAME);
        navigationSystemSettingMap.setHostNodeFlag(true);
        Class<?>[] navigationSystemSettingConvToUIParas = {NavigationSystemSetting.class, NavigationSystemSettingUIModel.class};
        navigationSystemSettingMap.setConvToUIMethodParas(navigationSystemSettingConvToUIParas);
        navigationSystemSettingMap.setConvToUIMethod(NavigationSystemSettingManager.METHOD_ConvNavigationSystemSettingToUI);
        Class<?>[] NavigationSystemSettingConvUIToParas = {NavigationSystemSettingUIModel.class, NavigationSystemSetting.class};
        navigationSystemSettingMap.setConvUIToMethodParas(NavigationSystemSettingConvUIToParas);
        navigationSystemSettingMap.setConvUIToMethod(NavigationSystemSettingManager.METHOD_ConvUIToNavigationSystemSetting);
        uiModelNodeMapList.add(navigationSystemSettingMap);
        navigationSystemSettingExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
        resultList.add(navigationSystemSettingExtensionUnion);
        return resultList;
    }


}
