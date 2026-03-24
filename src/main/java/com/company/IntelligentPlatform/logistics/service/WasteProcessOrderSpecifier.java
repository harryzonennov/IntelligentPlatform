package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.service.ISupplyI18nPackage;
import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.model.WasteProcessOrder;
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
import java.util.List;
import java.util.Map;

@Service
public class WasteProcessOrderSpecifier extends DocumentContentSpecifier<WasteProcessOrderServiceModel,
        WasteProcessOrder, WasteProcessMaterialItem> {

    @Autowired
    protected WasteProcessOrderManager wasteProcessOrderManager;

    @Autowired
    protected WasteProcessOrderIdHelper wasteProcessOrderIdHelper;

    @Autowired
    protected WasteProcessMaterialItemManager wasteProcessMaterialItemManager;

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_WASTEPROCESSORDER;
    }

    @Override
    public Integer getDocumentStatus(WasteProcessOrder wasteProcessOrder) {
        return wasteProcessOrder.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return wasteProcessOrderManager;
    }

    @Override
    public WasteProcessOrder setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        WasteProcessOrder wasteProcessOrder = (WasteProcessOrder) serviceEntityTargetStatus.getServiceEntityNode();
        wasteProcessOrder.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return wasteProcessOrder;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return wasteProcessOrderIdHelper;
    }


    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole,
                                              WasteProcessOrderServiceModel wasteProcessOrderServiceModel) {
        if(partyRole == WasteProcessOrderParty.ROLE_SOLD_TO_PARTY){
            return wasteProcessOrderServiceModel.getSoldToCustomer();
        }
        if(partyRole == WasteProcessOrderParty.ROLE_SOLD_FROM_PARTY){
            return wasteProcessOrderServiceModel.getSoldFromOrg();
        }
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(WasteProcessOrderServiceModel wasteProcessOrderServiceModel, DocInvolveParty docInvolveParty) {
        WasteProcessOrderParty wasteProcessOrderParty = (WasteProcessOrderParty) docInvolveParty;
        if(wasteProcessOrderParty.getPartyRole() == WasteProcessOrderParty.ROLE_SOLD_TO_PARTY){
            wasteProcessOrderServiceModel.setSoldToCustomer(wasteProcessOrderParty);
        }
        if(wasteProcessOrderParty.getPartyRole() == WasteProcessOrderParty.ROLE_SOLD_FROM_PARTY){
            wasteProcessOrderServiceModel.setSoldFromOrg(wasteProcessOrderParty);
        }
    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public List<?> getDocMatItemServiceModuleList(WasteProcessOrderServiceModel wasteProcessOrderServiceModel) {
        return wasteProcessOrderServiceModel.getWasteProcessMaterialItemList();
    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return wasteProcessOrderManager.initStatus(lanCode);
    }

    @Override
    public DocUIModelExtensionBuilder getDocUIModelExtensionBuilder() {
        // Doc root node: `WasteProcessOrder`
        DocUIModelExtensionBuilder docUIModelExtensionBuilder = docUIModelExtensionFactory.getBuilder(WasteProcessOrder.class, WasteProcessOrderUIModel.class, getDocumentManager());
        docUIModelExtensionBuilder.convDocToUIMethod(WasteProcessOrderManager.METHOD_ConvWasteProcessOrderToUI).convDocUIToMethod(WasteProcessOrderManager.METHOD_ConvUIToWasteProcessOrder);
        // Root flat submodules: `WasteProcessOrderAttachment`, `WasteProcessOrderActionNode`, `WasteProcessOrderParty`
        docUIModelExtensionBuilder.docAttachmentClass(WasteProcessOrderAttachment.class).docActionClass(WasteProcessOrderActionNode.class).docInvolvePartyClass(WasteProcessOrderParty.class);
        docUIModelExtensionBuilder.docInvolvePartyBuilderInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(WasteProcessOrderParty.ROLE_SOLD_TO_PARTY,
                                WasteProcessOrderParty.PARTY_NODEINST_SOLD_CUSTOMER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(WasteProcessOrderParty.ROLE_SOLD_FROM_PARTY,
                                WasteProcessOrderParty.PARTY_NODEINST_SOLD_ORG,
                                Organization.class,
                                Employee.class)
                )
        );
        docUIModelExtensionBuilder.docActionNodeBuildInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(WasteProcessOrderActionNode.DOC_ACTION_APPROVE,
                                WasteProcessOrderActionNode.NODEINST_ACTION_APPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(WasteProcessOrderActionNode.DOC_ACTION_REJECT_APPROVE,
                                WasteProcessOrderActionNode.NODEINST_ACTION_REJECT_APPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(WasteProcessOrderActionNode.DOC_ACTION_SUBMIT,
                                WasteProcessOrderActionNode.NODEINST_ACTION_SUBMIT),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(WasteProcessOrderActionNode.DOC_ACTION_DELIVERY_DONE,
                                WasteProcessOrderActionNode.NODEINST_ACTION_DELIVERY_DONE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(WasteProcessOrderActionNode.DOC_ACTION_PROCESS_DONE,
                                WasteProcessOrderActionNode.NODEINST_ACTION_PROCESS_DONE)
                )
        );
        // Doc item node: `WasteProcessMaterialItem`
        docUIModelExtensionBuilder.itemModelClass(WasteProcessMaterialItem.class).itemUiModelClass(WasteProcessMaterialItemUIModel.class)
                .itemAttachmentClass(WasteProcessMaterialItemAttachment.class)
                .convItemUIToMethod(WasteProcessMaterialItemManager.METHOD_ConvUIToWasteProcessMaterialItem).itemToParentDocMethod(WasteProcessMaterialItemManager.METHOD_ConvParentDocToItemUI).
                convItemToUIMethod(WasteProcessMaterialItemManager.METHOD_ConvWasteProcessMaterialItemToUI).itemLogicManager(wasteProcessMaterialItemManager);
        return docUIModelExtensionBuilder;
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return super.getInvolvePartyMapWrapper(lanCode, WasteProcessOrderUIModel.class, "WasteProcessOrder_InvolveParty");
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(WasteProcessOrder.class, WasteProcessOrderServiceModel.class, WasteProcessOrderActionNode.class,
                WasteProcessMaterialItem.class, WasteProcessMaterialItemServiceModel.class, null);
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(WasteProcessOrder.SENAME, WasteProcessOrderUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(WasteProcessMaterialItem.NODENAME,
                WasteProcessMaterialItemUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = ISupplyI18nPackage.class.getResource("").getPath();
        propertyMapList.addAll(DocumentContentSpecifier.getCorePropertyMap(WasteProcessOrder.SENAME, basePath +
                "WasteProcessOrder"));
        propertyMapList.addAll(DocumentContentSpecifier.getDocMatItemPropertyMap(WasteProcessMaterialItem.NODENAME, basePath +
                "WasteProcessMaterialItem"));
        propertyMapList.add(DocumentContentSpecifier.getActionNodePropertyMap("actionNode"));
        propertyMapList.addAll(DocumentContentSpecifier.getInvolvePartyPropertyMap("SoldToCustomer",
                basePath +
                "SoldToCustomer"));
        propertyMapList.addAll(DocumentContentSpecifier.getInvolvePartyPropertyMap("SoldFromOrg",
                basePath +
                "SoldFromOrg"));
        return propertyMapList;
    }

    @Override
    public void traverseMatItemNode(WasteProcessOrderServiceModel wasteProcessOrderServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution<WasteProcessMaterialItem> docItemActionCallback,
                                            SerialLogonInfo serialLogonInfo) throws DocActionException {
        if (!ServiceCollectionsHelper
                .checkNullList(wasteProcessOrderServiceModel.getWasteProcessMaterialItemList())) {
            for (WasteProcessMaterialItemServiceModel wasteProcessOrderMaterialItemServiceModel :
                    wasteProcessOrderServiceModel
                            .getWasteProcessMaterialItemList()) {
                if (docItemActionCallback != null) {
                    docItemActionCallback.execute(wasteProcessOrderMaterialItemServiceModel.getWasteProcessMaterialItem(), new DocActionExecutionProxy.ItemSelectionExecutionContext());
                }
            }
        }
    }

}
