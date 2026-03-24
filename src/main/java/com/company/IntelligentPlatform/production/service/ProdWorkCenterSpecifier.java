package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.dto.ProdWorkCenterServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProdWorkCenterUIModel;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.CorporateContactPersonUIModel;
import com.company.IntelligentPlatform.common.service.IAccountI18nPackage;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.CorporateContactPerson;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocInvolveParty;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProdWorkCenterSpecifier extends
        DocumentContentSpecifier<ProdWorkCenterServiceModel, ProdWorkCenter, DocMatItemNode> {

    @Autowired
    protected ProdWorkCenterServiceUIModelExtension prodWorkCenterServiceUIModelExtension;

    @Autowired
    protected ProdWorkCenterManager prodWorkCenterManager;

    @Autowired
    protected ProdWorkCenterIdHelper prodWorkCenterIdHelper;

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT;
    }

    @Override
    public Integer getDocumentStatus(ProdWorkCenter prodWorkCenter) {
        return 0;
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return prodWorkCenterManager;
    }

    @Override
    public ProdWorkCenter setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        return (ProdWorkCenter) serviceEntityTargetStatus.getServiceEntityNode();
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return prodWorkCenterIdHelper;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, ProdWorkCenterServiceModel serviceModule) {
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(ProdWorkCenterServiceModel serviceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public List<?> getDocMatItemServiceModuleList(ProdWorkCenterServiceModel prodWorkCenterServiceModel) {
        return null;
    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return null;
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return null;
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(ProdWorkCenter.class, ProdWorkCenterServiceModel.class, null,
                null, null, null);
    }

    @Override
    public ServiceUIModelExtension getDocServiceUIModelExtension() {
        return prodWorkCenterServiceUIModelExtension;
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(ProdWorkCenter.SENAME, ProdWorkCenterUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(CorporateContactPerson.NODENAME, CorporateContactPersonUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = IAccountI18nPackage.class.getResource("").getPath();
        propertyMapList.add(new PropertyMap(ProdWorkCenter.SENAME, basePath + "ProdWorkCenter"));
        propertyMapList.add(new PropertyMap("CorporateContactPerson",
                basePath + "CorporateContactPerson"));
        return propertyMapList;
    }

    @Override
    public void traverseMatItemNode(ProdWorkCenterServiceModel prodWorkCenterServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution docItemActionCallback,
                                            SerialLogonInfo serialLogonInfo) throws DocActionException {

    }

}
