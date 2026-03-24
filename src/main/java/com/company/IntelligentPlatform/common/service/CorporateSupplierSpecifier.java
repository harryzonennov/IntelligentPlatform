package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.CorporateContactPersonUIModel;
import com.company.IntelligentPlatform.common.dto.CorporateSupplierServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.CorporateSupplierUIModel;
import com.company.IntelligentPlatform.common.service.IAccountI18nPackage;
import com.company.IntelligentPlatform.common.controller.DocUIModelExtensionBuilder;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionHelper;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocInvolveParty;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CorporateSupplierSpecifier extends
        DocumentContentSpecifier<CorporateCustomerServiceModel, CorporateCustomer, DocMatItemNode> {

    @Autowired
    protected CorporateSupplierServiceUIModelExtension corporateSupplierServiceUIModelExtension;

    @Autowired
    protected CorporateSupplierManager corporateSupplierManager;

    @Autowired
    protected CorporateSupplierIdHelper corporateSupplierIdHelper;

    @Override
    public int getDocumentType() {
        return 0;
    }

    @Override
    public Integer getDocumentStatus(CorporateCustomer corporateCustomer) {
        return corporateCustomer.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return corporateSupplierManager;
    }

    @Override
    public CorporateCustomer setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        CorporateCustomer corporateCustomer = (CorporateCustomer) serviceEntityTargetStatus.getServiceEntityNode();
        corporateCustomer.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return corporateCustomer;
    }

    @Override
    public DocUIModelExtensionBuilder getDocUIModelExtensionBuilder() throws DocActionException {
        // Doc root node: `CorporateCustomer`
        DocUIModelExtensionBuilder docUIModelExtensionBuilder = docUIModelExtensionFactory.getBuilder(CorporateCustomer.class, CorporateSupplierUIModel.class, getDocumentManager());
        docUIModelExtensionBuilder.convDocToUIMethod(CorporateSupplierManager.METHOD_ConvCorporateCustomerToSupplierUI).convDocUIToMethod(
                CorporateSupplierManager.METHOD_ConvSupplierUIToCorporateCustomer).logicManager(corporateSupplierManager);
        // Root flat submodules: `CorporateCustomerAttachment`, `CorporateCustomerActionNode`, `CorporateCustomerParty`
        docUIModelExtensionBuilder.docAttachmentClass(CorporateCustomerAttachment.class).docActionClass(CorporateCustomerActionNode.class);
        docUIModelExtensionBuilder.docActionNodeBuildInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(CorporateCustomerActionNode.DOC_ACTION_ACTIVE,
                                CorporateCustomerActionNode.NODEINST_ACTION_ACTIVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(CorporateCustomerActionNode.DOC_ACTION_REINIT,
                                CorporateCustomerActionNode.NODEINST_ACTION_REINIT),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(CorporateCustomerActionNode.DOC_ACTION_ARCHIVE,
                                CorporateCustomerActionNode.NODEINST_ACTION_ARCHIVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(CorporateCustomerActionNode.DOC_ACTION_SUBMIT,
                                CorporateCustomerActionNode.NODEINST_ACTION_SUBMIT),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(CorporateCustomerActionNode.DOC_ACTION_APPROVE,
                                CorporateCustomerActionNode.NODEINST_ACTION_APPROVE)
                )
        );
        // Doc item node: `CorporateCustomerMaterialItem`
        docUIModelExtensionBuilder.childUIModelExtensionBuilderList(ServiceCollectionsHelper.asList(getContactPersonBuilder()));
        return docUIModelExtensionBuilder;
    }

    private DocUIModelExtensionBuilder getContactPersonBuilder() {
        DocUIModelExtensionBuilder docUIModelExtensionBuilder = docUIModelExtensionFactory.getBuilder(CorporateContactPerson.class, CorporateContactPersonUIModel.class, getDocumentManager());
        docUIModelExtensionBuilder.convDocToUIMethod(CorporateCustomerManager.METHOD_ConvCorporateContactPersonToUI)
                .convDocUIToMethod(CorporateCustomerManager.METHOD_ConvUIToCorporateContactPerson);
        docUIModelExtensionBuilder.addMapConfigureBuilder(CorporateContactPerson.NODENAME,
                ServiceUIModelExtensionHelper.genUIConfBuilder(IndividualCustomer.class, CorporateContactPersonUIModel.class)
                        .toBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE).convUIToMethod(CorporateCustomerManager.METHOD_ConvUIToCorporateContactPerson)
                        .convToUIMethod(CorporateCustomerManager.METHOD_ConvContactPersonToUI).baseNodeInstId(CorporateContactPerson.NODENAME));
        return docUIModelExtensionBuilder;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return corporateSupplierIdHelper;
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
    public List<?> getDocMatItemServiceModuleList(CorporateCustomerServiceModel corporateSupplierServiceModel) {
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

    // TODO to remove in the future
    @Override
    public ServiceUIModelExtension getDocServiceUIModelExtension() {
        return corporateSupplierServiceUIModelExtension;
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap("CorporateSupplier", CorporateSupplierUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(CorporateContactPerson.NODENAME, CorporateContactPersonUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = IAccountI18nPackage.class.getResource("").getPath();
        propertyMapList.add(new PropertyMap("CorporateSupplier", basePath + "CorporateSupplier"));
        propertyMapList.add(new PropertyMap("CorporateContactPerson", basePath + "CorporateContactPerson"));
        return propertyMapList;
    }

    @Override
    public void traverseMatItemNode(CorporateCustomerServiceModel corporateCustomerServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution docItemActionCallback,
                                            SerialLogonInfo serialLogonInfo) throws DocActionException {

    }

}
