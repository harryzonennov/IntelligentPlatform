package com.company.IntelligentPlatform.sales.service;

import com.company.IntelligentPlatform.sales.dto.*;
import com.company.IntelligentPlatform.sales.service.i18n.ISalesI18nPackage;
import com.company.IntelligentPlatform.sales.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.DocUIModelExtensionBuilder;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocInvolveParty;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SalesReturnOrderSpecifier extends DocumentContentSpecifier<SalesReturnOrderServiceModel, SalesReturnOrder,
        SalesReturnMaterialItem> {

    @Autowired
    protected SalesReturnOrderManager salesReturnOrderManager;

    @Autowired
    protected SalesReturnMaterialItemManager salesReturnMaterialItemManager;

    @Autowired
    protected SalesReturnOrderIdHelper salesReturnOrderIdHelper;

    private Map<String, Map<Integer, String>> involvePartyLan = new HashMap<>();
    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_SALESRETURNORDER;
    }

    @Override
    public Integer getDocumentStatus(SalesReturnOrder salesReturnOrder) {
        return salesReturnOrder.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return salesReturnOrderManager;
    }

    @Override
    public SalesReturnOrder setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        SalesReturnOrder salesReturnOrder = (SalesReturnOrder) serviceEntityTargetStatus.getServiceEntityNode();
        salesReturnOrder.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return salesReturnOrder;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return salesReturnOrderIdHelper;
    }

    @Override
    public DocUIModelExtensionBuilder getDocUIModelExtensionBuilder() {
        // Doc root node: `SalesReturnOrder`
        DocUIModelExtensionBuilder docUIModelExtensionBuilder = docUIModelExtensionFactory.getBuilder(SalesReturnOrder.class, SalesReturnOrderUIModel.class, getDocumentManager());
        docUIModelExtensionBuilder.convDocToUIMethod(SalesReturnOrderManager.METHOD_ConvSalesReturnOrderToUI).convDocUIToMethod(SalesReturnOrderManager.METHOD_ConvUIToSalesReturnOrder);
        // Root flat submodules: `SalesReturnOrderAttachment`, `SalesReturnOrderActionNode`, `SalesReturnOrderParty`
        docUIModelExtensionBuilder.docAttachmentClass(SalesReturnOrderAttachment.class).docActionClass(SalesReturnOrderActionNode.class).docInvolvePartyClass(SalesReturnOrderParty.class);
        docUIModelExtensionBuilder.docInvolvePartyBuilderInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara( SalesReturnOrderParty.ROLE_SOLD_TO_PARTY,
                                SalesReturnOrderParty.PARTY_NODEINST_SOLD_CUSTOMER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(SalesReturnOrderParty.ROLE_SOLD_FROM_PARTY,
                                SalesReturnOrderParty.PARTY_NODEINST_SOLD_ORG,
                                Organization.class,
                                Employee.class)
                )
        );
        docUIModelExtensionBuilder.docActionNodeBuildInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(SalesReturnOrderActionNode.DOC_ACTION_APPROVE,
                                SalesReturnOrderActionNode.NODEINST_ACTION_APPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(SalesReturnOrderActionNode.DOC_ACTION_SUBMIT,
                                SalesReturnOrderActionNode.NODEINST_ACTION_SUBMIT),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(SalesReturnOrderActionNode.DOC_ACTION_DELIVERY_DONE,
                                SalesReturnOrderActionNode.NODEINST_ACTION_DELIVERY_DONE)
                )
        );
        // Doc item node: `SalesReturnMaterialItem`
        docUIModelExtensionBuilder.itemModelClass(SalesReturnMaterialItem.class).itemUiModelClass(SalesReturnMaterialItemUIModel.class)
                .itemAttachmentClass(SalesReturnMatItemAttachment.class)
                .convItemUIToMethod(SalesReturnMaterialItemManager.METHOD_ConvUIToSalesReturnMaterialItem).itemToParentDocMethod(SalesReturnMaterialItemManager.METHOD_ConvParentDocToItemUI).
                convItemToUIMethod(SalesReturnMaterialItemManager.METHOD_ConvSalesReturnMaterialItemToUI).itemLogicManager(salesReturnMaterialItemManager);
        return docUIModelExtensionBuilder;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, SalesReturnOrderServiceModel salesReturnOrderServiceModel) {
        if(partyRole == SalesReturnOrderParty.ROLE_SOLD_TO_PARTY){
            return salesReturnOrderServiceModel.getSoldToCustomer();
        }
        if(partyRole == SalesReturnOrderParty.ROLE_SOLD_FROM_PARTY){
            return salesReturnOrderServiceModel.getSoldFromOrg();
        }
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(SalesReturnOrderServiceModel salesReturnOrderServiceModel, DocInvolveParty docInvolveParty) {
        SalesReturnOrderParty salesReturnOrderParty = (SalesReturnOrderParty) docInvolveParty;
        if(salesReturnOrderParty.getPartyRole() == SalesReturnOrderParty.ROLE_SOLD_TO_PARTY){
            salesReturnOrderServiceModel.setSoldToCustomer(salesReturnOrderParty);
        }
        if(salesReturnOrderParty.getPartyRole() == SalesReturnOrderParty.ROLE_SOLD_FROM_PARTY){
            salesReturnOrderServiceModel.setSoldFromOrg(salesReturnOrderParty);
        }
    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return salesReturnOrderManager.initStatus(lanCode);
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return super.getInvolvePartyMapWrapper(lanCode, SalesReturnOrderUIModel.class, "SalesReturnOrder_InvolveParty");
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(SalesReturnOrder.class, SalesReturnOrderServiceModel.class, SalesReturnOrderActionNode.class,
                SalesReturnMaterialItem.class, SalesReturnMaterialItemServiceModel.class, null);
    }

    @Override
    public void traverseMatItemNode(SalesReturnOrderServiceModel salesReturnOrderServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution<SalesReturnMaterialItem> docItemActionCallback,
                                            SerialLogonInfo serialLogonInfo) throws DocActionException {
        if (!ServiceCollectionsHelper
                .checkNullList(salesReturnOrderServiceModel.getSalesReturnMaterialItemList())) {
            for (SalesReturnMaterialItemServiceModel salesReturnMaterialItemServiceModel :
                    salesReturnOrderServiceModel
                            .getSalesReturnMaterialItemList()) {
                if (docItemActionCallback != null) {
                    docItemActionCallback.execute(salesReturnMaterialItemServiceModel.getSalesReturnMaterialItem(), new DocActionExecutionProxy.ItemSelectionExecutionContext());
                }
            }
        }
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(SalesReturnOrder.SENAME, SalesReturnOrderUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(SalesReturnMaterialItem.NODENAME, SalesReturnMaterialItemUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = ISalesI18nPackage.class.getResource("").getPath();
        propertyMapList.add(new PropertyMap(SalesReturnOrder.SENAME, basePath + "SalesReturnOrder"));
        propertyMapList.add(new PropertyMap(SalesReturnMaterialItem.NODENAME,
                basePath + "SalesReturnMaterialItem"));
        propertyMapList.add(DocumentContentSpecifier.getActionNodePropertyMap("actionNode"));
        propertyMapList.add(new PropertyMap("SalesReturnSoldToParty",
                basePath + "SalesReturnSoldToParty"));
        propertyMapList.add(new PropertyMap("SalesReturnSoldFromParty",
                basePath + "SalesReturnSoldFromParty"));
        return propertyMapList;
    }

}