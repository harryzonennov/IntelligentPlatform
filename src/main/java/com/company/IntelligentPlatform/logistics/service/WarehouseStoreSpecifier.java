package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.service.IDeliveryI18nPackage;
import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.DocUIModelExtensionBuilder;
import com.company.IntelligentPlatform.common.controller.DocUIModelExtensionFactory;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionHelper;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WarehouseStoreSpecifier extends
        DocumentContentSpecifier<WarehouseStoreServiceModel, WarehouseStore, WarehouseStoreItem> {

    @Autowired
    protected WarehouseStoreManager warehouseStoreManager;

    @Autowired
    protected WarehouseStoreItemManager warehouseStoreItemManager;

    @Autowired
    protected WarehouseManager warehouseManager;

    @Autowired
    protected WarehouseStoreIdHelper warehouseStoreIdHelper;

    @Autowired
    private DocFlowProxy docFlowProxy;

    @Autowired
    protected WarehouseStoreItemLogManager warehouseStoreItemLogManager;

    @Autowired
    private DocUIModelExtensionFactory docUIModelExtensionFactory;

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM;
    }

    @Override
    public Integer getDocumentStatus(WarehouseStore warehouseStore) {
        return warehouseStore.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return warehouseStoreManager;
    }

    @Override
    public WarehouseStore setDocumentStatus(DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        return null;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return warehouseStoreIdHelper;
    }

    @Override
    public DocUIModelExtensionBuilder getDocUIModelExtensionBuilder() throws DocActionException {
        // Doc root node: `WarehouseStore`
        DocUIModelExtensionBuilder docUIModelExtensionBuilder = docUIModelExtensionFactory.getBuilder(WarehouseStore.class, WarehouseStoreUIModel.class, getDocumentManager());
        docUIModelExtensionBuilder.convDocToUIMethod(WarehouseStoreManager.METHOD_ConvWarehouseStoreToUI).convDocUIToMethod(WarehouseStoreManager.METHOD_ConvUIToWarehouseStore);
        // Root flat submodules: `WarehouseStoreAttachment`, `WarehouseStoreActionNode`, `WarehouseStoreParty`
        docUIModelExtensionBuilder.docAttachmentClass(WarehouseStoreAttachment.class).docActionClass(WarehouseStoreActionNode.class).docInvolvePartyClass(WarehouseStoreParty.class);
        docUIModelExtensionFactory.addWarehouseMapConfigureList(docUIModelExtensionBuilder, DocUIModelExtensionFactory.genWarehouseConfig(WarehouseStore.SENAME)
                .convWarehouseToUIMethod(WarehouseStoreManager.METHOD_ConvWarehouseToUI));
        docUIModelExtensionBuilder.docInvolvePartyBuilderInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(WarehouseStoreParty.PARTY_ROLE_SUPPLIER,
                                WarehouseStoreParty.PARTY_NODEINST_PUR_SUPPLIER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(WarehouseStoreParty.PARTY_ROLE_PURORG,
                                WarehouseStoreParty.PARTY_NODEINST_PUR_ORG,
                                Organization.class,
                                Employee.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(WarehouseStoreParty.PARTY_ROLE_CUSTOMER,
                                WarehouseStoreParty.PARTY_NODEINST_SOLD_CUSTOMER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(WarehouseStoreParty.PARTY_ROLE_SALESORG,
                                WarehouseStoreParty.PARTY_NODEINST_SOLD_ORG,
                                Organization.class,
                                Employee.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(WarehouseStoreParty.PARTY_ROLE_PRODORG,
                                WarehouseStoreParty.PARTY_NODEINST_PROD_ORG,
                                Organization.class,
                                Employee.class)
                )
        ).docActionNodeBuildInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(WarehouseStoreActionNode.DOC_ACTION_APPROVE,
                                WarehouseStoreActionNode.NODEINST_ACTION_APPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(WarehouseStoreActionNode.DOC_ACTION_COUNTAPPROVE,
                                WarehouseStoreActionNode.NODEINST_ACTION_COUNTAPPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(WarehouseStoreActionNode.DOC_ACTION_REJECT_APPROVE,
                                WarehouseStoreActionNode.NODEINST_ACTION_REJECT_APPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(WarehouseStoreActionNode.DOC_ACTION_RECORD_DONE,
                                WarehouseStoreActionNode.NODEINST_ACTION_DELIVERY_DONE)
                )
        );
        // Doc item node: `WarehouseStoreItem`
        docUIModelExtensionBuilder.itemModelClass(WarehouseStoreItem.class).itemUiModelClass(WarehouseStoreItemUIModel.class)
                .itemAttachmentClass(WarehouseStoreItemAttachment.class).itemInvolvePartyClass(WarehouseStoreItemParty.class)
                .convItemUIToMethod(WarehouseStoreItemManager.METHOD_ConvUIToWarehouseStoreItem).defaultItemToParentDoc(true).
                convItemToUIMethod(WarehouseStoreItemManager.METHOD_ConvWarehouseStoreItemToUI).itemLogicManager(warehouseStoreItemManager);
        docUIModelExtensionBuilder.itemInvolvePartyBuilderInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(WarehouseStoreItemParty.PARTY_ROLE_SUPPLIER,
                                WarehouseStoreItemParty.PARTY_NODEINST_PUR_SUPPLIER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(WarehouseStoreItemParty.PARTY_ROLE_PURORG,
                                WarehouseStoreItemParty.PARTY_NODEINST_PUR_ORG,
                                Organization.class,
                                Employee.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(WarehouseStoreItemParty.PARTY_ROLE_CUSTOMER,
                                WarehouseStoreItemParty.PARTY_NODEINST_SOLD_CUSTOMER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(WarehouseStoreItemParty.PARTY_ROLE_SALESORG,
                                WarehouseStoreItemParty.PARTY_NODEINST_SOLD_ORG,
                                Organization.class,
                                Employee.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(WarehouseStoreItemParty.PARTY_ROLE_PRODORG,
                                WarehouseStoreItemParty.PARTY_NODEINST_PROD_ORG,
                                Organization.class,
                                Employee.class)
                )
        );
        docUIModelExtensionFactory.addWarehouseMapConfigureList(docUIModelExtensionBuilder, DocUIModelExtensionFactory.genWarehouseConfig(WarehouseStoreItem.NODENAME)
                .convWarehouseToUIMethod(WarehouseStoreItemManager.METHOD_ConvWarehouseToStoreItemUI).logicManager(warehouseStoreItemManager).baseUIModelClass(WarehouseStoreItemUIModel.class).
                convWarehouseAreaToUIMethod(WarehouseStoreItemManager.METHOD_ConvWarehouseAreaToStoreItemUI));

        docUIModelExtensionFactory.addSpecDocNodeMapConfigureList(docUIModelExtensionBuilder, DocUIModelExtensionFactory.genSpecDocConfig(
                        IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY, WarehouseStoreItem.NODENAME).sourceFieldName(IServiceEntityCommonFieldConstant.PREVDOCMATITEMUUID).
                convToUIMethod(WarehouseStoreItemManager.METHOD_ConvInboundDeliveryToStoreItemUI).logicManager(warehouseStoreItemManager));

        docUIModelExtensionFactory.addSpecDocNodeMapConfigureList(docUIModelExtensionBuilder, DocUIModelExtensionFactory.genSpecDocConfig(
                IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY, WarehouseStoreItem.NODENAME).sourceFieldName(IServiceEntityCommonFieldConstant.NEXTDOCMATITEMUUID).
                convToUIMethod(WarehouseStoreItemManager.METHOD_ConvOutboundDeliveryToStoreItemUI).logicManager(warehouseStoreItemManager));
        // Doc item node: `WarehouseStoreItemLog`
        // docUIModelExtensionBuilder.childUIModelExtensionBuilderList(ServiceCollectionsHelper.asList(getWarehouseStoreItemLogBuilder()));
        return docUIModelExtensionBuilder;
    }

    private DocUIModelExtensionBuilder getWarehouseStoreItemLogBuilder() throws DocActionException {
        DocUIModelExtensionBuilder docUIModelExtensionBuilder = docUIModelExtensionFactory.getBuilder(WarehouseStoreItemLog.class, WarehouseStoreItemLogUIModel.class, getDocumentManager());
        docUIModelExtensionBuilder.convDocToUIMethod(WarehouseStoreItemLogManager.METHOD_ConvWarehouseStoreItemLogToUI).logicManager(warehouseStoreItemLogManager);
        docUIModelExtensionBuilder.addMapConfigureBuilder(WarehouseStoreItemLog.NODENAME,
                ServiceUIModelExtensionHelper.genUIConfBuilder(WarehouseStoreItem.class,WarehouseStoreItemLogUIModel.class)
                        .toBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD).baseNodeInstId(WarehouseStoreItemLog.NODENAME));
        docUIModelExtensionBuilder.addMapConfigureBuilder(WarehouseStoreItemLog.NODENAME,
                ServiceUIModelExtensionHelper.genUIConfBuilder(MaterialStockKeepUnit.class,WarehouseStoreItemLogUIModel.class)
                        .serviceEntityManager(materialStockKeepUnitManager)
                        .addConnectionCondition("refMaterialSKUUUID")
                        .convToUIMethod(WarehouseStoreItemLogManager.METHOD_ConvMaterialStockKeepUnitToUI).baseNodeInstId(WarehouseStoreItemLog.NODENAME));
        docUIModelExtensionFactory.addSpecDocNodeMapConfigureList(docUIModelExtensionBuilder, DocUIModelExtensionFactory.genSpecDocConfig(
                        WarehouseStoreItemLog.NODENAME).
                convToUIMethod(WarehouseStoreItemLogManager.METHOD_ConvDocumentToStoreItemLogUI).docMatItemGetCallback(rawSENode -> {
                    WarehouseStoreItemLog warehouseStoreItemLog = (WarehouseStoreItemLog) rawSENode;
                    int documentType = warehouseStoreItemLog.getDocumentType();
                    return docFlowProxy
                            .getDefDocItemNode(documentType,
                                    warehouseStoreItemLog.getDocumentUUID(),
                                    warehouseStoreItemLog.getClient());
                }));
        return docUIModelExtensionBuilder;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, WarehouseStoreServiceModel warehouseStoreServiceModel) {
        if(partyRole == WarehouseStoreParty.PARTY_ROLE_CUSTOMER){
            return warehouseStoreServiceModel.getCorporateCustomerParty();
        }
        if(partyRole == WarehouseStoreParty.PARTY_ROLE_PRODORG){
            return warehouseStoreServiceModel.getProductionOrgParty();
        }
        if(partyRole == WarehouseStoreParty.PARTY_ROLE_SUPPLIER){
            return warehouseStoreServiceModel.getCorporateSupplierParty();
        }
        if(partyRole == WarehouseStoreParty.PARTY_ROLE_SALESORG){
            return warehouseStoreServiceModel.getSalesOrganizationParty();
        }
        if(partyRole == WarehouseStoreParty.PARTY_ROLE_PURORG){
            return warehouseStoreServiceModel.getPurchaseOrgParty();
        }
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        WarehouseStoreItemServiceModel warehouseStoreItemServiceModel =
                (WarehouseStoreItemServiceModel) itemServiceModule;
        if(partyRole == WarehouseStoreItemParty.PARTY_ROLE_CUSTOMER){
            return warehouseStoreItemServiceModel.getCorporateCustomerParty();
        }
        if(partyRole == WarehouseStoreItemParty.PARTY_ROLE_PRODORG){
            return warehouseStoreItemServiceModel.getProductionOrgParty();
        }
        if(partyRole == WarehouseStoreItemParty.PARTY_ROLE_SUPPLIER){
            return warehouseStoreItemServiceModel.getCorporateSupplierParty();
        }
        if(partyRole == WarehouseStoreItemParty.PARTY_ROLE_SALESORG){
            return warehouseStoreItemServiceModel.getSalesOrganizationParty();
        }
        if(partyRole == WarehouseStoreItemParty.PARTY_ROLE_PURORG){
            return warehouseStoreItemServiceModel.getPurchaseOrgParty();
        }
        return null;
    }

    @Override
    public void setDocInvolveParty(WarehouseStoreServiceModel warehouseStoreServiceModel,
                                   DocInvolveParty docInvolveParty) {
        WarehouseStoreParty warehouseStoreParty = (WarehouseStoreParty) docInvolveParty;
        if(warehouseStoreParty.getPartyRole() == WarehouseStoreParty.PARTY_ROLE_CUSTOMER){
            warehouseStoreServiceModel.setCorporateCustomerParty(warehouseStoreParty);
        }
        if(warehouseStoreParty.getPartyRole() == WarehouseStoreParty.PARTY_ROLE_PRODORG){
            warehouseStoreServiceModel.setProductionOrgParty(warehouseStoreParty);
        }
        if(warehouseStoreParty.getPartyRole() == WarehouseStoreParty.PARTY_ROLE_SUPPLIER){
            warehouseStoreServiceModel.setCorporateSupplierParty(warehouseStoreParty);
        }
        if(warehouseStoreParty.getPartyRole() == WarehouseStoreParty.PARTY_ROLE_SALESORG){
            warehouseStoreServiceModel.setSalesOrganizationParty(warehouseStoreParty);
        }
        if(warehouseStoreParty.getPartyRole() == WarehouseStoreParty.PARTY_ROLE_PURORG){
            warehouseStoreServiceModel.setPurchaseOrgParty(warehouseStoreParty);
        }
    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {
        WarehouseStoreItemParty warehouseStoreItemParty = (WarehouseStoreItemParty) docInvolveParty;
        WarehouseStoreItemServiceModel warehouseStoreItemServiceModel = (WarehouseStoreItemServiceModel) itemServiceModule;
        if(warehouseStoreItemParty.getPartyRole() == WarehouseStoreItemParty.PARTY_ROLE_CUSTOMER){
            warehouseStoreItemServiceModel.setCorporateCustomerParty(warehouseStoreItemParty);
        }
        if(warehouseStoreItemParty.getPartyRole() == WarehouseStoreItemParty.PARTY_ROLE_PRODORG){
            warehouseStoreItemServiceModel.setProductionOrgParty(warehouseStoreItemParty);
        }
        if(warehouseStoreItemParty.getPartyRole() == WarehouseStoreItemParty.PARTY_ROLE_SUPPLIER){
            warehouseStoreItemServiceModel.setCorporateSupplierParty(warehouseStoreItemParty);
        }
        if(warehouseStoreItemParty.getPartyRole() == WarehouseStoreItemParty.PARTY_ROLE_SALESORG){
            warehouseStoreItemServiceModel.setSalesOrganizationParty(warehouseStoreItemParty);
        }
        if(warehouseStoreItemParty.getPartyRole() == WarehouseStoreItemParty.PARTY_ROLE_PURORG){
            warehouseStoreItemServiceModel.setPurchaseOrgParty(warehouseStoreItemParty);
        }
    }

    @Override
    public List<?> getDocMatItemServiceModuleList(WarehouseStoreServiceModel warehouseStoreServiceModel) {
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
        return new DocMetadata(WarehouseStore.class, WarehouseStoreServiceModel.class, WarehouseStoreActionNode.class,
                WarehouseStoreItem.class, WarehouseStoreItemServiceModel.class, null);
    }

    @Override
    public void traverseMatItemNode(WarehouseStoreServiceModel serviceModule,
                                    DocActionExecutionProxy.DocItemActionExecution<WarehouseStoreItem> docItemActionCallback,
                                    SerialLogonInfo serialLogonInfo) throws DocActionException {

    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(WarehouseStore.SENAME, WarehouseStoreUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(WarehouseStoreItem.NODENAME, WarehouseStoreItemUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = IDeliveryI18nPackage.class.getResource("").getPath();
        propertyMapList.addAll(DocumentContentSpecifier.getCorePropertyMap(WarehouseStore.SENAME, basePath +
                "WarehouseStore"));
        propertyMapList.addAll(DocumentContentSpecifier.getDocMatItemPropertyMap(WarehouseStoreItem.NODENAME,
                basePath + "WarehouseStoreItem"));
        propertyMapList.add(DocumentContentSpecifier.getActionNodePropertyMap("actionNode"));
        propertyMapList.addAll(DocumentContentSpecifier.getInvolvePartyPropertyMap("CorporateSupplierParty", basePath +
                "CorporateSupplierParty"));
        propertyMapList.addAll(DocumentContentSpecifier.getInvolvePartyPropertyMap("CorporateCustomerParty", basePath +
                "CorporateCustomerParty"));
        propertyMapList.addAll(DocumentContentSpecifier.getInvolvePartyPropertyMap("ProductionOrgParty", basePath +
                "ProductionOrgParty"));
        propertyMapList.addAll(DocumentContentSpecifier.getInvolvePartyPropertyMap("PurchaseOrgParty", basePath +
                "PurchaseOrgParty"));
        propertyMapList.addAll(DocumentContentSpecifier.getInvolvePartyPropertyMap("SalesOrganizationParty", basePath +
                "SalesOrganizationParty"));
        return propertyMapList;
    }

}
