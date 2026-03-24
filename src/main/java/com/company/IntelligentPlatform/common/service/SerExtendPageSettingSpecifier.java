package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.SerExtendPageMetadataServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.SerExtendPageMetadataUIModel;
import com.company.IntelligentPlatform.common.dto.SerExtendPageSettingServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.SerExtendPageSettingUIModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocInvolveParty;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.SerExtendPageMetadata;
import com.company.IntelligentPlatform.common.model.SerExtendPageSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SerExtendPageSettingSpecifier extends
        DocumentContentSpecifier<SerExtendPageSettingServiceModel, SerExtendPageSetting, SerExtendPageMetadata> {

    @Autowired
    protected SerExtendPageSettingServiceUIModelExtension serExtendPageSettingServiceUIModelExtension;

    @Autowired
    protected SerExtendPageMetadataServiceUIModelExtension serExtendPageMetadataServiceUIModelExtension;

    @Autowired
    protected SerExtendPageSettingManager serExtendPageSettingManager;

    @Autowired
    protected ServiceExtensionSettingManager serviceExtensionSettingManager;

    @Override
    public int getDocumentType() {
        return 0;
    }

    @Override
    public Integer getDocumentStatus(SerExtendPageSetting serExtendPageSetting) {
        return 0;
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return serviceExtensionSettingManager;
    }

    @Override
    public SerExtendPageSetting setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        SerExtendPageSetting serExtendPageSetting = (SerExtendPageSetting) serviceEntityTargetStatus.getServiceEntityNode();
        // serExtendPageSetting.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return serExtendPageSetting;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return null;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, SerExtendPageSettingServiceModel serviceModule) {
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(SerExtendPageSettingServiceModel serviceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public List<?> getDocMatItemServiceModuleList(SerExtendPageSettingServiceModel serExtendPageSettingServiceModel) {
        return null;
    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        // return serExtendPageSettingManager.initStatusMap(lanCode);
        return null;
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return null;
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(SerExtendPageSetting.class, SerExtendPageSettingServiceModel.class, null,
                null, null, null);
    }

    @Override
    public ServiceUIModelExtension getDocServiceUIModelExtension() {
        return serExtendPageSettingServiceUIModelExtension;
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(SerExtendPageSetting.NODENAME, SerExtendPageSettingUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(SerExtendPageMetadata.NODENAME, SerExtendPageMetadataUIModel.class));
        return uiModelClassMap;
    }


    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
//        String basePath = ISerExtendPageSettingI18nPackage.class.getResource("").getPath();
//        propertyMapList.add(new PropertyMap(SerExtendPageSetting.SENAME, basePath + "SerExtendPageSetting"));
//        propertyMapList.add(new PropertyMap("SerExtendPageSettingUnit",
//                basePath + "SerExtendPageSettingUnit"));
        return propertyMapList;
    }

    @Override
    public void traverseMatItemNode(SerExtendPageSettingServiceModel serExtendPageSettingServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution docItemActionCallback,
                                            SerialLogonInfo serialLogonInfo) throws DocActionException {

    }


}
