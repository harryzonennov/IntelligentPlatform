package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.service.IDeliveryI18nPackage;
import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.controller.DocUIModelExtensionBuilder;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionHelper;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
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
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class InventoryTransferOrderSpecifier extends DocumentContentSpecifier<InventoryTransferOrderServiceModel,
        InventoryTransferOrder,
        InventoryTransferItem> {

    @Autowired
    protected InventoryTransferOrderServiceUIModelExtension inventoryTransferOrderServiceUIModelExtension;

    @Autowired
    protected InventoryTransferItemServiceUIModelExtension inventoryTransferItemServiceUIModelExtension;

    @Autowired
    protected InventoryTransferOrderManager inventoryTransferOrderManager;

    @Autowired
    protected InventoryTransferItemManager inventoryTransferItemManager;

    @Autowired
    protected OutboundDeliveryManager outboundDeliveryManager;

    @Autowired
    protected InboundDeliveryManager inboundDeliveryManager;

    @Autowired
    protected InventoryTransferOrderIdHelper inventoryTransferOrderIdHelper;

    @Autowired
    protected WarehouseManager warehouseManager;

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_TRANSFER;
    }

    @Override
    public Integer getDocumentStatus(InventoryTransferOrder inventoryTransferOrder) {
        return inventoryTransferOrder.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return inventoryTransferOrderManager;
    }

    @Override
    public InventoryTransferOrder setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        InventoryTransferOrder inventoryTransferOrder = (InventoryTransferOrder) serviceEntityTargetStatus.getServiceEntityNode();
        inventoryTransferOrder.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return inventoryTransferOrder;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return inventoryTransferOrderIdHelper;
    }

    @Override
    public DocUIModelExtensionBuilder getDocUIModelExtensionBuilder() throws DocActionException {
        // Doc root node: `InventoryTransferOrder`
        DocUIModelExtensionBuilder docUIModelExtensionBuilder = docUIModelExtensionFactory.getBuilder(InventoryTransferOrder.class, InventoryTransferOrderUIModel.class, getDocumentManager());
        docUIModelExtensionBuilder.convDocToUIMethod(InventoryTransferOrderManager.METHOD_ConvInventoryTransferOrderToUI).convDocUIToMethod(InventoryTransferOrderManager.METHOD_ConvUIToInventoryTransferOrder);
        // Root flat submodules: `InventoryTransferOrderAttachment`, `InventoryTransferOrderActionNode`, `InventoryTransferOrderParty`
        docUIModelExtensionBuilder.docAttachmentClass(InventoryTransferOrderAttachment.class).docActionClass(InventoryTransferOrderActionNode.class).docInvolvePartyClass(InventoryTransferOrderParty.class);
        docUIModelExtensionBuilder.addMapConfigureBuilder(InventoryTransferOrder.SENAME, ServiceUIModelExtensionHelper.genUIConfBuilder(Warehouse.class, InventoryTransferOrderUIModel.class)
                .serviceEntityManager(warehouseManager)
                .addConnectionCondition("refWarehouseUUID").convToUIMethod(InventoryTransferOrderManager.METHOD_ConvOutboundWarehouseToUI).baseNodeInstId(InventoryTransferOrder.SENAME));
        docUIModelExtensionBuilder.addMapConfigureBuilder(InventoryTransferOrder.SENAME, ServiceUIModelExtensionHelper.genUIConfBuilder(WarehouseArea.class, InventoryTransferOrderUIModel.class)
                .serviceEntityManager(warehouseManager)
                .addConnectionCondition("refWarehouseAreaUUID").convToUIMethod(InventoryTransferOrderManager.METHOD_ConvOutboundWarehouseAreaToUI).
                baseNodeInstId(InventoryTransferOrder.SENAME));
        docUIModelExtensionBuilder.addMapConfigureBuilder(InventoryTransferOrder.SENAME, ServiceUIModelExtensionHelper.genUIConfBuilder(Warehouse.class, InventoryTransferOrderUIModel.class)
                .serviceEntityManager(warehouseManager)
                .addConnectionCondition("refInboundWarehouseUUID").convToUIMethod(InventoryTransferOrderManager.METHOD_ConvInboundWarehouseToUI).baseNodeInstId(InventoryTransferOrder.SENAME));
        docUIModelExtensionBuilder.addMapConfigureBuilder(InventoryTransferOrder.SENAME, ServiceUIModelExtensionHelper.genUIConfBuilder(WarehouseArea.class, InventoryTransferOrderUIModel.class)
                .serviceEntityManager(warehouseManager)
                .addConnectionCondition("refInboundWarehouseAreaUUID").convToUIMethod(InventoryTransferOrderManager.METHOD_ConvInboundWarehouseAreaToUI).
                baseNodeInstId(InventoryTransferOrder.SENAME));
        docUIModelExtensionBuilder.docInvolvePartyBuilderInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(InventoryTransferOrderParty.PARTY_ROLE_SUPPLIER,
                                InventoryTransferOrderParty.PARTY_NODEINST_PUR_SUPPLIER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(InventoryTransferOrderParty.PARTY_ROLE_PURORG,
                                InventoryTransferOrderParty.PARTY_NODEINST_PUR_ORG,
                                Organization.class,
                                Employee.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(InventoryTransferOrderParty.PARTY_ROLE_CUSTOMER,
                                InventoryTransferOrderParty.PARTY_NODEINST_SOLD_CUSTOMER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(InventoryTransferOrderParty.PARTY_ROLE_SALESORG,
                                InventoryTransferOrderParty.PARTY_NODEINST_SOLD_ORG,
                                Organization.class,
                                Employee.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(InventoryTransferOrderParty.PARTY_ROLE_PRODORG,
                                InventoryTransferOrderParty.PARTY_NODEINST_PROD_ORG,
                                Organization.class,
                                Employee.class)
                )
        ).docActionNodeBuildInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(InventoryTransferOrderActionNode.DOC_ACTION_APPROVE,
                                InventoryTransferOrderActionNode.NODEINST_ACTION_APPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(InventoryTransferOrderActionNode.DOC_ACTION_COUNTAPPROVE,
                                InventoryTransferOrderActionNode.NODEINST_ACTION_COUNTAPPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(InventoryTransferOrderActionNode.DOC_ACTION_REJECT_APPROVE,
                                InventoryTransferOrderActionNode.NODEINST_ACTION_REJECT_APPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(InventoryTransferOrderActionNode.DOC_ACTION_TRANSFER_DONE,
                                InventoryTransferOrderActionNode.NODEINST_ACTION_DELIVERY_DONE)
                )
        );
        // Doc item node: `InventoryTransferItem`
        docUIModelExtensionBuilder.itemModelClass(InventoryTransferItem.class).itemUiModelClass(InventoryTransferItemUIModel.class)
                .itemAttachmentClass(InventoryTransferItemAttachment.class).itemInvolvePartyClass(InventoryTransferItemParty.class)
                .convItemUIToMethod(InventoryTransferItemManager.METHOD_ConvUIToInventoryTransferItem).itemToParentDocMethod(InventoryTransferItemManager.METHOD_ConvParentDocToItemUI).
                convItemToUIMethod(InventoryTransferItemManager.METHOD_ConvInventoryTransferItemToUI).itemLogicManager(inventoryTransferItemManager);
        docUIModelExtensionBuilder.itemInvolvePartyBuilderInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(InventoryTransferItemParty.PARTY_ROLE_SUPPLIER,
                                InventoryTransferItemParty.PARTY_NODEINST_PUR_SUPPLIER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(InventoryTransferItemParty.PARTY_ROLE_PURORG,
                                InventoryTransferItemParty.PARTY_NODEINST_PUR_ORG,
                                Organization.class,
                                Employee.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(InventoryTransferItemParty.PARTY_ROLE_CUSTOMER,
                                InventoryTransferItemParty.PARTY_NODEINST_SOLD_CUSTOMER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(InventoryTransferItemParty.PARTY_ROLE_SALESORG,
                                InventoryTransferItemParty.PARTY_NODEINST_SOLD_ORG,
                                Organization.class,
                                Employee.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(InventoryTransferItemParty.PARTY_ROLE_PRODORG,
                                InventoryTransferItemParty.PARTY_NODEINST_PROD_ORG,
                                Organization.class,
                                Employee.class)
                )
        );
        docUIModelExtensionBuilder.addMapConfigureBuilder(InventoryTransferItem.NODENAME, ServiceUIModelExtensionHelper.genUIConfBuilder(OutboundItem.class, InventoryTransferItemUIModel.class)
                .serviceEntityManager(outboundDeliveryManager)
                .addConnectionCondition("refOutboundItemUUID").baseNodeInstId(InventoryTransferItem.NODENAME));
        docUIModelExtensionBuilder.addMapConfigureBuilder(InventoryTransferItem.NODENAME, ServiceUIModelExtensionHelper.genUIConfBuilder(OutboundDelivery.class, InventoryTransferItemUIModel.class)
                .serviceEntityManager(outboundDeliveryManager).convUIToMethod(InventoryTransferItemManager.METHOD_ConvOutboundDeliveryToItemUI)
                .toBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_ROOT_TO_CHILD).baseNodeInstId(OutboundItem.NODENAME));
        docUIModelExtensionBuilder.addMapConfigureBuilder(InventoryTransferItem.NODENAME, ServiceUIModelExtensionHelper.genUIConfBuilder(InboundItem.class, InventoryTransferItemUIModel.class)
                .serviceEntityManager(inboundDeliveryManager)
                .addConnectionCondition("refInboundItemUUID").baseNodeInstId(InventoryTransferItem.NODENAME));
        docUIModelExtensionBuilder.addMapConfigureBuilder(InventoryTransferItem.NODENAME, ServiceUIModelExtensionHelper.genUIConfBuilder(InboundDelivery.class, InventoryTransferItemUIModel.class)
                .serviceEntityManager(inboundDeliveryManager).convUIToMethod(InventoryTransferItemManager.METHOD_ConvInboundDeliveryToItemUI)
                .toBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_ROOT_TO_CHILD).baseNodeInstId(InboundItem.NODENAME));
        docUIModelExtensionBuilder.addMapConfigureBuilder(InventoryTransferItem.NODENAME, ServiceUIModelExtensionHelper.genUIConfBuilder(Warehouse.class, InventoryTransferItemUIModel.class)
                .serviceEntityManager(warehouseManager)
                .addConnectionCondition("refInboundWarehouseUUID").convToUIMethod(InventoryTransferItemManager.METHOD_ConvInboundWarehouseToItemUI).baseNodeInstId(InventoryTransferItem.NODENAME));
        docUIModelExtensionBuilder.addMapConfigureBuilder(InventoryTransferItem.NODENAME, ServiceUIModelExtensionHelper.genUIConfBuilder(WarehouseArea.class, InventoryTransferItemUIModel.class)
                .serviceEntityManager(warehouseManager)
                .addConnectionCondition("refInboundWarehouseAreaUUID").convToUIMethod(InventoryTransferItemManager.METHOD_ConvInboundWarehouseAreaToItemUI).
                baseNodeInstId(InventoryTransferItem.NODENAME));
        docUIModelExtensionBuilder.addMapConfigureBuilder(InventoryTransferItem.NODENAME, ServiceUIModelExtensionHelper.genUIConfBuilder(Warehouse.class, InventoryTransferItemUIModel.class)
                .serviceEntityManager(warehouseManager)
                .addConnectionCondition("refWarehouseUUID").convToUIMethod(InventoryTransferItemManager.METHOD_ConvOutboundWarehouseToItemUI).baseNodeInstId(InventoryTransferItem.NODENAME));
        docUIModelExtensionBuilder.addMapConfigureBuilder(InventoryTransferItem.NODENAME, ServiceUIModelExtensionHelper.genUIConfBuilder(WarehouseArea.class, InventoryTransferItemUIModel.class)
                .serviceEntityManager(warehouseManager)
                .addConnectionCondition("refWarehouseAreaUUID").convToUIMethod(InventoryTransferItemManager.METHOD_ConvOutboundWarehouseAreaToItemUI).
                baseNodeInstId(InventoryTransferItem.NODENAME));
        return docUIModelExtensionBuilder;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, InventoryTransferOrderServiceModel inventoryTransferOrderServiceModel) {
        if(partyRole == InventoryTransferOrderParty.PARTY_ROLE_CUSTOMER){
            return inventoryTransferOrderServiceModel.getCorporateCustomerParty();
        }
        if(partyRole == InventoryTransferOrderParty.PARTY_ROLE_PRODORG){
            return inventoryTransferOrderServiceModel.getProductionOrgParty();
        }
        if(partyRole == InventoryTransferOrderParty.PARTY_ROLE_SUPPLIER){
            return inventoryTransferOrderServiceModel.getCorporateSupplierParty();
        }
        if(partyRole == InventoryTransferOrderParty.PARTY_ROLE_SALESORG){
            return inventoryTransferOrderServiceModel.getSalesOrganizationParty();
        }
        if(partyRole == InventoryTransferOrderParty.PARTY_ROLE_PURORG){
            return inventoryTransferOrderServiceModel.getPurchaseOrgParty();
        }
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {

        InventoryTransferItemServiceModel inventoryTransferItemServiceModel = (InventoryTransferItemServiceModel) itemServiceModule;
        if(partyRole == InventoryTransferItemParty.PARTY_ROLE_CUSTOMER){
            return inventoryTransferItemServiceModel.getCorporateCustomerParty();
        }
        if(partyRole == InventoryTransferItemParty.PARTY_ROLE_PRODORG){
            return inventoryTransferItemServiceModel.getProductionOrgParty();
        }
        if(partyRole == InventoryTransferItemParty.PARTY_ROLE_SUPPLIER){
            return inventoryTransferItemServiceModel.getCorporateSupplierParty();
        }
        if(partyRole == InventoryTransferItemParty.PARTY_ROLE_SALESORG){
            return inventoryTransferItemServiceModel.getSalesOrganizationParty();
        }
        if(partyRole == InventoryTransferItemParty.PARTY_ROLE_PURORG){
            return inventoryTransferItemServiceModel.getPurchaseOrgParty();
        }
        return null;
    }

    @Override
    public void setDocInvolveParty(InventoryTransferOrderServiceModel inventoryTransferOrderServiceModel,
                                   DocInvolveParty docInvolveParty) {
        InventoryTransferOrderParty inventoryTransferOrderParty = (InventoryTransferOrderParty) docInvolveParty;
        if(inventoryTransferOrderParty.getPartyRole() == InventoryTransferItemParty.PARTY_ROLE_CUSTOMER){
            inventoryTransferOrderServiceModel.setCorporateCustomerParty(inventoryTransferOrderParty);
        }
        if(inventoryTransferOrderParty.getPartyRole() == InventoryTransferItemParty.PARTY_ROLE_PRODORG){
            inventoryTransferOrderServiceModel.setProductionOrgParty(inventoryTransferOrderParty);
        }
        if(inventoryTransferOrderParty.getPartyRole() == InventoryTransferItemParty.PARTY_ROLE_SUPPLIER){
            inventoryTransferOrderServiceModel.setCorporateSupplierParty(inventoryTransferOrderParty);
        }
        if(inventoryTransferOrderParty.getPartyRole() == InventoryTransferItemParty.PARTY_ROLE_SALESORG){
            inventoryTransferOrderServiceModel.setSalesOrganizationParty(inventoryTransferOrderParty);
        }
        if(inventoryTransferOrderParty.getPartyRole() == InventoryTransferItemParty.PARTY_ROLE_PURORG){
            inventoryTransferOrderServiceModel.setPurchaseOrgParty(inventoryTransferOrderParty);
        }
    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {
        InventoryTransferItemParty inventoryTransferItemParty = (InventoryTransferItemParty) docInvolveParty;
        InventoryTransferItemServiceModel inventoryTransferItemServiceModel = (InventoryTransferItemServiceModel) itemServiceModule;
        if(inventoryTransferItemParty.getPartyRole() == InventoryTransferItemParty.PARTY_ROLE_CUSTOMER){
            inventoryTransferItemServiceModel.setCorporateCustomerParty(inventoryTransferItemParty);
        }
        if(inventoryTransferItemParty.getPartyRole() == InventoryTransferItemParty.PARTY_ROLE_PRODORG){
            inventoryTransferItemServiceModel.setProductionOrgParty(inventoryTransferItemParty);
        }
        if(inventoryTransferItemParty.getPartyRole() == InventoryTransferItemParty.PARTY_ROLE_SUPPLIER){
            inventoryTransferItemServiceModel.setCorporateSupplierParty(inventoryTransferItemParty);
        }
        if(inventoryTransferItemParty.getPartyRole() == InventoryTransferItemParty.PARTY_ROLE_SALESORG){
            inventoryTransferItemServiceModel.setSalesOrganizationParty(inventoryTransferItemParty);
        }
        if(inventoryTransferItemParty.getPartyRole() == InventoryTransferItemParty.PARTY_ROLE_PURORG){
            inventoryTransferItemServiceModel.setPurchaseOrgParty(inventoryTransferItemParty);
        }
    }

    @Override
    public List<?> getDocMatItemServiceModuleList(InventoryTransferOrderServiceModel inventoryTransferOrderServiceModel) {
        return inventoryTransferOrderServiceModel.getInventoryTransferItemList();
    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return inventoryTransferOrderManager.initStatusMap(lanCode);
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return null;
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(InventoryTransferOrder.class, InventoryTransferOrderServiceModel.class, InventoryTransferOrderActionNode.class,
                InventoryTransferItem.class, InventoryTransferItemServiceModel.class, null);
    }

    @Override
    public ServiceUIModelExtension getDocServiceUIModelExtension() {
        return inventoryTransferOrderServiceUIModelExtension;
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(InventoryTransferOrder.SENAME, InventoryTransferOrderUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(InventoryTransferItem.NODENAME,
                InventoryTransferItemUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {

        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = IDeliveryI18nPackage.class.getResource("").getPath();
        propertyMapList.add(new PropertyMap(InventoryTransferOrder.SENAME, basePath + "InventoryTransferOrder"));
        propertyMapList.add(new PropertyMap(InventoryTransferItem.NODENAME,
                basePath + "InventoryTransferItem"));
        propertyMapList.add(DocumentContentSpecifier.getActionNodePropertyMap("actionNode"));
        return propertyMapList;
    }

    @Override
    public void traverseMatItemNode(InventoryTransferOrderServiceModel inventoryTransferOrderServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution<InventoryTransferItem> docItemActionCallback,
                                    SerialLogonInfo serialLogonInfo) throws DocActionException {
        if (!ServiceCollectionsHelper
                .checkNullList(inventoryTransferOrderServiceModel.getInventoryTransferItemList())) {
            for (InventoryTransferItemServiceModel inventoryTransferItemServiceModel :
                    inventoryTransferOrderServiceModel
                            .getInventoryTransferItemList()) {
                if (docItemActionCallback != null) {
                    docItemActionCallback.execute(inventoryTransferItemServiceModel.getInventoryTransferItem(), new DocActionExecutionProxy.ItemSelectionExecutionContext());
                }
            }
        }
    }

}
