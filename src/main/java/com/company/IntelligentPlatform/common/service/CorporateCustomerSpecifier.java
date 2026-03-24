package com.company.IntelligentPlatform.common.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.CorporateContactPersonUIModel;
import com.company.IntelligentPlatform.common.dto.CorporateCustomerServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.CorporateCustomerUIModel;
import com.company.IntelligentPlatform.common.service.IAccountI18nPackage;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.CorporateContactPerson;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.CorporateCustomerActionNode;
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
public class CorporateCustomerSpecifier extends
        DocumentContentSpecifier<CorporateCustomerServiceModel, CorporateCustomer, DocMatItemNode> {

    @Autowired
    protected CorporateCustomerServiceUIModelExtension corporateCustomerServiceUIModelExtension;

    @Autowired
    protected CorporateCustomerManager corporateCustomerManager;

    @Autowired
    protected CorporateCustomerIdHelper corporateCustomerIdHelper;

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT;
    }

    @Override
    public Integer getDocumentStatus(CorporateCustomer corporateCustomer) {
        return corporateCustomer.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return corporateCustomerManager;
    }

    @Override
    public CorporateCustomer setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        CorporateCustomer corporateCustomer = (CorporateCustomer) serviceEntityTargetStatus.getServiceEntityNode();
        corporateCustomer.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return corporateCustomer;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return corporateCustomerIdHelper;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, CorporateCustomerServiceModel serviceModule) {
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(CorporateCustomerServiceModel serviceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public List<?> getDocMatItemServiceModuleList(CorporateCustomerServiceModel corporateCustomerServiceModel) {
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
        return new DocMetadata(CorporateCustomer.class, CorporateCustomerServiceModel.class, CorporateCustomerActionNode.class,
                null, null, null);
    }

    //TODO remove in the future
    @Override
    public ServiceUIModelExtension getDocServiceUIModelExtension() {
        return corporateCustomerServiceUIModelExtension;
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(CorporateCustomer.SENAME, CorporateCustomerUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(CorporateContactPerson.NODENAME, CorporateContactPersonUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = IAccountI18nPackage.class.getResource("").getPath();
        propertyMapList.add(new PropertyMap(CorporateCustomer.SENAME, basePath + "CorporateCustomer"));
        propertyMapList.add(new PropertyMap("CorporateContactPerson",
                basePath + "CorporateContactPerson"));
        return propertyMapList;
    }

    @Override
    public void traverseMatItemNode(CorporateCustomerServiceModel corporateCustomerServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution docItemActionCallback,
                                            SerialLogonInfo serialLogonInfo) throws DocActionException {

    }

}
