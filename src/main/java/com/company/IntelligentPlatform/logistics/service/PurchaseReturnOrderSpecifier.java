package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.service.ISupplyI18nPackage;
import com.company.IntelligentPlatform.logistics.model.*;
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
public class PurchaseReturnOrderSpecifier extends DocumentContentSpecifier<PurchaseReturnOrderServiceModel,
        PurchaseReturnOrder,
        PurchaseReturnMaterialItem> {

    @Autowired
    protected PurchaseReturnOrderManager purchaseReturnOrderManager;

    @Autowired
    protected PurchaseReturnMaterialItemManager purchaseReturnMaterialItemManager;

    @Autowired
    protected PurchaseReturnOrderIdHelper purchaseReturnOrderIdHelper;

    private Map<String, Map<Integer, String>> involvePartyLan = new HashMap<>();

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER;
    }

    @Override
    public Integer getDocumentStatus(PurchaseReturnOrder purchaseReturnOrder) {
        return purchaseReturnOrder.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return purchaseReturnOrderManager;
    }

    @Override
    public PurchaseReturnOrder setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        PurchaseReturnOrder purchaseReturnOrder = (PurchaseReturnOrder) serviceEntityTargetStatus.getServiceEntityNode();
        purchaseReturnOrder.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return purchaseReturnOrder;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return purchaseReturnOrderIdHelper;
    }

    @Override
    public DocUIModelExtensionBuilder getDocUIModelExtensionBuilder() {
        // Doc root node: `PurchaseReturnOrder`
        DocUIModelExtensionBuilder docUIModelExtensionBuilder = docUIModelExtensionFactory.getBuilder(PurchaseReturnOrder.class, PurchaseReturnOrderUIModel.class, getDocumentManager());
        docUIModelExtensionBuilder.convDocToUIMethod(PurchaseReturnOrderManager.METHOD_ConvPurchaseReturnOrderToUI).convDocUIToMethod(PurchaseReturnOrderManager.METHOD_ConvUIToPurchaseReturnOrder);
        // Root flat submodules: `PurchaseReturnOrderAttachment`, `PurchaseReturnOrderActionNode`, `PurchaseReturnOrderParty`
        docUIModelExtensionBuilder.docAttachmentClass(PurchaseReturnOrderAttachment.class).docActionClass(PurchaseReturnOrderActionNode.class).docInvolvePartyClass(PurchaseReturnOrderParty.class);
        docUIModelExtensionBuilder.docInvolvePartyBuilderInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(PurchaseReturnOrderParty.ROLE_PARTYB,
                                PurchaseReturnOrderParty.PARTY_NODEINST_PUR_SUPPLIER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(PurchaseReturnOrderParty.ROLE_PARTYA,
                                PurchaseReturnOrderParty.PARTY_NODEINST_PUR_ORG,
                                Organization.class,
                                Employee.class)
                )
        );
        docUIModelExtensionBuilder.docActionNodeBuildInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(PurchaseReturnOrderActionNode.DOC_ACTION_APPROVE,
                                PurchaseReturnOrderActionNode.NODEINST_ACTION_APPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(PurchaseReturnOrderActionNode.DOC_ACTION_REJECT_APPROVE,
                                PurchaseReturnOrderActionNode.NODEINST_ACTION_REJECT_APPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(PurchaseReturnOrderActionNode.DOC_ACTION_SUBMIT,
                                PurchaseReturnOrderActionNode.NODEINST_ACTION_SUBMIT)
                )
        );
        // Doc item node: `PurchaseReturnMaterialItem`
        docUIModelExtensionBuilder.itemModelClass(PurchaseReturnMaterialItem.class).itemUiModelClass(PurchaseReturnMaterialItemUIModel.class)
                .itemAttachmentClass(PurchaseReturnMaterialItemAttachment.class).defaultItemToParentDoc(true)
                .convItemUIToMethod(PurchaseReturnMaterialItemManager.METHOD_ConvUIToPurchaseReturnMaterialItem).
                convItemToUIMethod(PurchaseReturnMaterialItemManager.METHOD_ConvPurchaseReturnMaterialItemToUI).itemLogicManager(purchaseReturnMaterialItemManager);
        return docUIModelExtensionBuilder;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, PurchaseReturnOrderServiceModel purchaseReturnOrderServiceModel) {
        if(partyRole == PurchaseReturnOrderParty.ROLE_PARTYA){
            return purchaseReturnOrderServiceModel.getPurchaseToOrg();
        }
        if(partyRole == PurchaseReturnOrderParty.ROLE_PARTYB){
            return purchaseReturnOrderServiceModel.getPurchaseFromSupplier();
        }
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(PurchaseReturnOrderServiceModel purchaseReturnOrderServiceModel,
                                   DocInvolveParty docInvolveParty) {
        PurchaseReturnOrderParty purchaseReturnOrderParty = (PurchaseReturnOrderParty) docInvolveParty;
        if(purchaseReturnOrderParty.getPartyRole() == PurchaseReturnOrderParty.ROLE_PARTYA){
            purchaseReturnOrderServiceModel.setPurchaseToOrg(purchaseReturnOrderParty);
        }
        if(purchaseReturnOrderParty.getPartyRole() == PurchaseReturnOrderParty.ROLE_PARTYB){
            purchaseReturnOrderServiceModel.setPurchaseFromSupplier(purchaseReturnOrderParty);
        }
    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public List<?> getDocMatItemServiceModuleList(PurchaseReturnOrderServiceModel purchaseReturnOrderServiceModel) {
        return purchaseReturnOrderServiceModel.getPurchaseReturnMaterialItemList();
    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return purchaseReturnOrderManager.initStatus(lanCode);
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return super.getInvolvePartyMapWrapper(lanCode, PurchaseReturnOrderUIModel.class,
                "purchaseReturnOrder_InvolveParty");
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(PurchaseReturnOrder.class, PurchaseReturnOrderServiceModel.class, PurchaseReturnOrderActionNode.class,
                PurchaseReturnMaterialItem.class, PurchaseReturnMaterialItemServiceModel.class, null);
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(PurchaseReturnOrder.SENAME, PurchaseReturnOrderUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(PurchaseReturnMaterialItem.NODENAME,
                PurchaseReturnMaterialItemUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = ISupplyI18nPackage.class.getResource("").getPath();
        propertyMapList.addAll(DocumentContentSpecifier.getCorePropertyMap(PurchaseReturnOrder.SENAME, basePath +
                "PurchaseReturnOrder"));
        propertyMapList.addAll(DocumentContentSpecifier.getDocMatItemPropertyMap(PurchaseReturnMaterialItem.NODENAME,
                basePath + "PurchaseReturnMaterialItem"));
        propertyMapList.add(DocumentContentSpecifier.getActionNodePropertyMap("actionNode"));
        propertyMapList.addAll(DocumentContentSpecifier.getInvolvePartyPropertyMap("PurchaseToOrg", basePath +
                "PurchaseToOrg"));
        propertyMapList.addAll(DocumentContentSpecifier.getInvolvePartyPropertyMap("PurchaseFromSupplier", basePath +
                "PurchaseFromSupplier"));
        return propertyMapList;
    }

    @Override
    public void traverseMatItemNode(PurchaseReturnOrderServiceModel purchaseReturnOrderServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution<PurchaseReturnMaterialItem> docItemActionCallback,
                                            SerialLogonInfo serialLogonInfo) throws DocActionException {
        if (!ServiceCollectionsHelper
                .checkNullList(purchaseReturnOrderServiceModel.getPurchaseReturnMaterialItemList())) {
            for (PurchaseReturnMaterialItemServiceModel purchaseReturnMaterialItemServiceModel :
                    purchaseReturnOrderServiceModel
                            .getPurchaseReturnMaterialItemList()) {
                if (docItemActionCallback != null) {
                    docItemActionCallback.execute(purchaseReturnMaterialItemServiceModel.getPurchaseReturnMaterialItem(), new DocActionExecutionProxy.ItemSelectionExecutionContext());
                }
            }
        }
    }

}
