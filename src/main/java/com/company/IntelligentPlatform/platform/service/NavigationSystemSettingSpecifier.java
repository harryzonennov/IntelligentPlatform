package com.company.IntelligentPlatform.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.platform.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.platform.dto.NavigationGroupSettingUIModel;
import com.company.IntelligentPlatform.platform.dto.NavigationSystemSettingServiceUIModelExtension;
import com.company.IntelligentPlatform.platform.dto.NavigationSystemSettingUIModel;
import com.company.IntelligentPlatform.platform.service.*;
import com.company.IntelligentPlatform.platform.service.ServiceEntityManager;
import com.company.IntelligentPlatform.platform.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.platform.service.ServiceDocInitConfigureManager;
import com.company.IntelligentPlatform.platform.model.NavigationGroupSetting;
import com.company.IntelligentPlatform.platform.model.NavigationSystemSetting;
import com.company.IntelligentPlatform.platform.model.NavigationSystemSettingActionNode;
import com.company.IntelligentPlatform.platform.model.SerialLogonInfo;
import com.company.IntelligentPlatform.platform.model.DocInvolveParty;
import com.company.IntelligentPlatform.platform.model.DocMatItemNode;
import com.company.IntelligentPlatform.platform.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NavigationSystemSettingSpecifier extends
        DocumentContentSpecifier<NavigationSystemSettingServiceModel, NavigationSystemSetting, DocMatItemNode> {

    public static final String INIT_CONFIGID_newModuleFromType = "newModuleFromType";

    @Autowired
    protected NavigationSystemSettingServiceUIModelExtension navigationSystemSettingServiceUIModelExtension;

    @Autowired
    protected NavigationSystemSettingManager navigationSystemSettingManager;

    @Override
    public int getDocumentType() {
        return 0;
    }

    @Override
    public Integer getDocumentStatus(NavigationSystemSetting navigationSystemSetting) {
        return navigationSystemSetting.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return navigationSystemSettingManager;
    }

    @Override
    public NavigationSystemSetting setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        NavigationSystemSetting navigationSystemSetting = (NavigationSystemSetting) serviceEntityTargetStatus.getServiceEntityNode();
        navigationSystemSetting.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return navigationSystemSetting;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return null;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, NavigationSystemSettingServiceModel serviceModule) {
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(NavigationSystemSettingServiceModel serviceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public List<?> getDocMatItemServiceModuleList(NavigationSystemSettingServiceModel navigationSystemSettingServiceModel) {
        return null;
    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return navigationSystemSettingManager.initStatusMap(lanCode);
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return null;
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(NavigationSystemSetting.class, NavigationSystemSettingServiceModel.class, NavigationSystemSettingActionNode.class,
                null, null, null);
    }

    @Override
    public ServiceUIModelExtension getDocServiceUIModelExtension() {
        return navigationSystemSettingServiceUIModelExtension;
    }

    //TODO check usage
    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(NavigationSystemSetting.SENAME, NavigationSystemSettingUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(NavigationGroupSetting.NODENAME, NavigationGroupSettingUIModel.class));
        return uiModelClassMap;
    }

    //TODO check usage
    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        return propertyMapList;
    }

    @Override
    public List<String> getConfiguredInitIdList() {
        return ServiceCollectionsHelper.asList(INIT_CONFIGID_newModuleFromType);
    }

    @Override
    public Map<String, List<ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta>> getDefAllInitConfigureMap() {
        Map<String, List<ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta>> serviceDocInitConfigureMap = new HashMap<>();
        serviceDocInitConfigureMap.put(ServiceEntityStringHelper.getDefModelId(NavigationSystemSetting.SENAME, NavigationSystemSetting.NODENAME), null);
        serviceDocInitConfigureMap.put(INIT_CONFIGID_newModuleFromType,
                ServiceCollectionsHelper.asList(new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta("refNavigationSystemSettingType",
                        "baseUUID", null)));
        return serviceDocInitConfigureMap;
    }

    @Override
    public void traverseMatItemNode(NavigationSystemSettingServiceModel navigationSystemSettingServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution docItemActionCallback,
                                            SerialLogonInfo serialLogonInfo) throws DocActionException {

    }

}
