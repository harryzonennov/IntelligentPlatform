package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.LogonUserServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.LogonUserUIModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.IFoundAccountI18nPackage;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.LogonUserActionNode;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocInvolveParty;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LogonUserSpecifier extends DocumentContentSpecifier<LogonUserServiceModel, LogonUser, DocMatItemNode> {

    @Autowired
    protected LogonUserServiceUIModelExtension logonUserServiceUIModelExtension;

    @Autowired
    protected LogonUserManager logonUserManager;

    @Autowired
    protected LogonUserIdHelper logonUserIdHelper;

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT;
    }

    @Override
    public Integer getDocumentStatus(LogonUser logonUser) {
        return logonUser.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return logonUserManager;
    }

    @Override
    public LogonUser setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        LogonUser logonUser = (LogonUser) serviceEntityTargetStatus.getServiceEntityNode();
        logonUser.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return logonUser;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return logonUserIdHelper;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, LogonUserServiceModel serviceModule) {
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(LogonUserServiceModel serviceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public List<?> getDocMatItemServiceModuleList(LogonUserServiceModel logonUserServiceModel) {
        return null;
    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return logonUserManager.initStatusMap(lanCode);
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return null;
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(LogonUser.class, LogonUserServiceModel.class,LogonUserActionNode.class,
                null, null, null);
    }

    @Override
    public ServiceUIModelExtension getDocServiceUIModelExtension() {
        return logonUserServiceUIModelExtension;
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(LogonUser.SENAME, LogonUserUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = IFoundAccountI18nPackage.class.getResource("").getPath();
        propertyMapList.add(new PropertyMap(LogonUser.SENAME, basePath + "LogonUser"));
        propertyMapList.add(new PropertyMap("LogonUserOrg",
                basePath + "LogonUserOrg"));
        return propertyMapList;
    }

    @Override
    public void traverseMatItemNode(LogonUserServiceModel logonUserServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution docItemActionCallback,
                                            SerialLogonInfo serialLogonInfo) throws DocActionException {

    }


}
