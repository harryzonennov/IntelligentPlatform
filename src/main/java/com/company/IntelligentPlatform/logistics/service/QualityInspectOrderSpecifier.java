package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.model.QualityInspectOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.controller.DocUIModelExtensionBuilder;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionHelper;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceDocInitConfigureManager;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocInvolveParty;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QualityInspectOrderSpecifier extends DocumentContentSpecifier<QualityInspectOrderServiceModel,
        QualityInspectOrder,
        QualityInspectMatItem> {

    @Autowired
    protected QualityInspectOrderManager qualityInspectOrderManager;

    @Autowired
    protected QualityInspectOrderIdHelper qualityInspectOrderIdHelper;

    @Autowired
    protected QualityInspectMatItemManager qualityInspectMatItemManager;

    @Autowired
    protected QualityInspectPropertyItemManager qualityInspectPropertyItemManager;

    @Autowired
    protected WarehouseManager warehouseManager;

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER;
    }

    @Override
    public Integer getDocumentStatus(QualityInspectOrder qualityInspectOrder) {
        return qualityInspectOrder.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return qualityInspectOrderManager;
    }

    @Override
    public QualityInspectOrder setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        QualityInspectOrder qualityInspectOrder = (QualityInspectOrder) serviceEntityTargetStatus.getServiceEntityNode();
        qualityInspectOrder.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return qualityInspectOrder;
    }

    @Override
    public Map<String, List<ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta >> getDefAllInitConfigureMap() {
        Map<String, List<ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta >> initDocConfigureMap = new HashMap<>();
        initDocConfigureMap.put(ServiceEntityStringHelper.getDefModelId(QualityInspectOrder.SENAME, QualityInspectOrder.NODENAME),
                ServiceCollectionsHelper.asList(
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta ("category","category",
                                "")));
        return initDocConfigureMap;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return qualityInspectOrderIdHelper;
    }

    @Override
    public DocUIModelExtensionBuilder getDocUIModelExtensionBuilder() throws DocActionException {
        // Doc root node: `QualityInspectOrder`
        DocUIModelExtensionBuilder docUIModelExtensionBuilder = docUIModelExtensionFactory.getBuilder(QualityInspectOrder.class, QualityInspectOrderUIModel.class, getDocumentManager());
        docUIModelExtensionBuilder.convDocToUIMethod(QualityInspectOrderManager.METHOD_ConvQualityInspectOrderToUI).convDocUIToMethod(QualityInspectOrderManager.METHOD_ConvUIToQualityInspectOrder);
        // UI Model Configure of node:[refWasteWarehouse]
        docUIModelExtensionBuilder.addMapConfigureBuilder(QualityInspectOrder.SENAME, ServiceUIModelExtensionHelper.genUIConfBuilder(Warehouse.class, QualityInspectOrderUIModel.class)
                .serviceEntityManager(warehouseManager)
                .addConnectionCondition("refWarehouseUUID").convToUIMethod(QualityInspectOrderManager.METHOD_ConvWarehouseToUI).
                baseNodeInstId(QualityInspectMatItem.NODENAME));
        docUIModelExtensionBuilder.addMapConfigureBuilder(QualityInspectOrder.SENAME, ServiceUIModelExtensionHelper.genUIConfBuilder(WarehouseArea.class, QualityInspectOrderUIModel.class)
                .serviceEntityManager(warehouseManager)
                .addConnectionCondition("refWarehouseAreaUUID").convToUIMethod(QualityInspectOrderManager.METHOD_ConvWarehouseAreaToUI).
                baseNodeInstId(QualityInspectMatItem.NODENAME));
        // Root flat submodules: `QualityInsOrderAttachment`, `QualityInsOrderActionNode`, `QualityInspectOrderParty`
        docUIModelExtensionBuilder.docAttachmentClass(QualityInsOrderAttachment.class).docActionClass(QualityInsOrderActionNode.class).docInvolvePartyClass(QualityInspectOrderParty.class);
        docUIModelExtensionBuilder.docInvolvePartyBuilderInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(QualityInspectOrderParty.PARTY_ROLE_SUPPLIER,
                                QualityInspectOrderParty.PARTY_NODEINST_PUR_SUPPLIER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(QualityInspectOrderParty.PARTY_ROLE_PURORG,
                                QualityInspectOrderParty.PARTY_NODEINST_PUR_ORG,
                                Organization.class,
                                Employee.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(QualityInspectOrderParty.PARTY_ROLE_CUSTOMER,
                                QualityInspectOrderParty.PARTY_NODEINST_SOLD_CUSTOMER,
                                Organization.class,
                                Employee.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(QualityInspectOrderParty.PARTY_ROLE_SALESORG,
                                QualityInspectOrderParty.PARTY_NODEINST_SOLD_ORG,
                                Organization.class,
                                Employee.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(QualityInspectOrderParty.PARTY_ROLE_PRODORG,
                                QualityInspectOrderParty.PARTY_NODEINST_PROD_ORG,
                                Organization.class,
                                Employee.class)
                )
        );
        docUIModelExtensionBuilder.docActionNodeBuildInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(QualityInsOrderActionNode.DOC_ACTION_START_TEST,
                                QualityInsOrderActionNode.NODEINST_ACTION_STRATTEST),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(QualityInsOrderActionNode.DOC_ACTION_TESTDONE,
                                QualityInsOrderActionNode.NODEINST_ACTION_TEST_DONE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(QualityInsOrderActionNode.DOC_ACTION_PROCESS_DONE,
                                QualityInsOrderActionNode.NODEINST_ACTION_PROCESS_DONE)
                )
        );

        // Doc item node: `QualityInspectMatItem`
        docUIModelExtensionBuilder.itemModelClass(QualityInspectMatItem.class).itemUiModelClass(QualityInspectMatItemUIModel.class)
                .itemAttachmentClass(QualityInsMatItemAttachment.class)
                .convItemUIToMethod(QualityInspectMatItemManager.METHOD_ConvUIToQualityInspectMatItem).itemToParentDocMethod(QualityInspectMatItemManager.METHOD_ConvParentDocToItemUI).
                convItemToUIMethod(QualityInspectMatItemManager.METHOD_ConvQualityInspectMatItemToUI).itemLogicManager(qualityInspectMatItemManager);

        // UI Model Configure of node:[refWasteWarehouse]
        docUIModelExtensionBuilder.addMapConfigureBuilder(QualityInspectMatItem.NODENAME, ServiceUIModelExtensionHelper.genUIConfBuilder(Warehouse.class, QualityInspectMatItemUIModel.class)
                .serviceEntityManager(warehouseManager).nodeInstId("refWasteWarehouse")
                .addConnectionCondition("refWasteWarehouseUUID").convToUIMethod(QualityInspectOrderManager.METHOD_ConvRefWasteWarehouseToItemUI).
                baseNodeInstId(QualityInspectMatItem.NODENAME));
        docUIModelExtensionBuilder.addMapConfigureBuilder(QualityInspectMatItem.NODENAME, ServiceUIModelExtensionHelper.genUIConfBuilder(WarehouseArea.class, QualityInspectMatItemUIModel.class)
                .serviceEntityManager(warehouseManager).nodeInstId("refWasteWarehouseArea")
                .addConnectionCondition("refWasteWareAreaUUID").convToUIMethod(QualityInspectOrderManager.METHOD_ConvWarehouseAreaToItemUI).
                baseNodeInstId(QualityInspectMatItem.NODENAME));

        // Doc item node: `QualityInspectPropertyItem`
        docUIModelExtensionBuilder.childUIModelExtensionBuilderList(ServiceCollectionsHelper.asList(getQualityPropertyItemBuilder()));
        return docUIModelExtensionBuilder;
    }

    private DocUIModelExtensionBuilder getQualityPropertyItemBuilder()  {
        DocUIModelExtensionBuilder docUIModelExtensionBuilder = docUIModelExtensionFactory.getBuilder(QualityInspectPropertyItem.class, QualityInspectPropertyItemUIModel.class, getDocumentManager());
        docUIModelExtensionBuilder.convDocToUIMethod(QualityInspectPropertyItemManager.METHOD_ConvQualityInspectPropertyItemToUI)
                .convDocUIToMethod(QualityInspectPropertyItemManager.METHOD_ConvUIToQualityInspectPropertyItem).logicManager(qualityInspectPropertyItemManager);
        docUIModelExtensionBuilder.addMapConfigureBuilder(QualityInspectPropertyItem.NODENAME,
                ServiceUIModelExtensionHelper.genUIConfBuilder(QualityInspectMatItem.class,QualityInspectPropertyItemUIModel.class)
                .toBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD).baseNodeInstId(QualityInspectPropertyItem.NODENAME));
        docUIModelExtensionBuilder.addMapConfigureBuilder(QualityInspectPropertyItem.NODENAME,
                ServiceUIModelExtensionHelper.genUIConfBuilder(QualityInspectOrder.class,QualityInspectPropertyItemUIModel.class)
                .logicManager(qualityInspectPropertyItemManager)
                .toBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_ROOT_TO_CHILD).
                convToUIMethod(QualityInspectPropertyItemManager.METHOD_ConvQualityInspectOrderToPropertyUI).baseNodeInstId(QualityInspectPropertyItem.NODENAME));
        docUIModelExtensionBuilder.addMapConfigureBuilder(QualityInspectPropertyItem.NODENAME,
                ServiceUIModelExtensionHelper.genUIConfBuilder(RegisteredProduct.class,QualityInspectPropertyItemUIModel.class)
                .logicManager(qualityInspectPropertyItemManager).serviceEntityManager(registeredProductManager).baseNodeInstId(QualityInspectMatItem.NODENAME)
                .addConnectionCondition("refMaterialSKUUUID").
                convToUIMethod(QualityInspectPropertyItemManager.METHOD_ConvRefRegisteredProductToPropertyItemUI));
        docUIModelExtensionBuilder.addMapConfigureBuilder(QualityInspectPropertyItem.NODENAME,
                ServiceUIModelExtensionHelper.genUIConfBuilder(RegisteredProductExtendProperty.class,QualityInspectPropertyItemUIModel.class)
                .logicManager(qualityInspectPropertyItemManager).serviceEntityManager(registeredProductManager)
                .addConnectionCondition("refPropertyUUID").baseNodeInstId(QualityInspectPropertyItem.NODENAME)
                .convToUIMethod(QualityInspectPropertyItemManager.METHOD_ConvRegisteredProductPropertyItemToUI));
        return docUIModelExtensionBuilder;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole,
                                              QualityInspectOrderServiceModel qualityInspectOrderServiceModel) {
        if(partyRole == QualityInspectOrderParty.PARTY_ROLE_CUSTOMER){
            return qualityInspectOrderServiceModel.getCorporateCustomerParty();
        }
        if(partyRole == QualityInspectOrderParty.PARTY_ROLE_PRODORG){
            return qualityInspectOrderServiceModel.getProductionOrgParty();
        }
        if(partyRole == QualityInspectOrderParty.PARTY_ROLE_SUPPLIER){
            return qualityInspectOrderServiceModel.getCorporateSupplierParty();
        }
        if(partyRole == QualityInspectOrderParty.PARTY_ROLE_SALESORG){
            return qualityInspectOrderServiceModel.getSalesOrganizationParty();
        }
        if(partyRole == QualityInspectOrderParty.PARTY_ROLE_PURORG){
            return qualityInspectOrderServiceModel.getPurchaseOrgParty();
        }
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        QualityInspectMatItemServiceModel qualityInspectMatItemServiceModel =
                (QualityInspectMatItemServiceModel) itemServiceModule;
        if(partyRole == QualityInspectMatItemParty.PARTY_ROLE_CUSTOMER){
            return qualityInspectMatItemServiceModel.getCorporateCustomerParty();
        }
        if(partyRole == QualityInspectMatItemParty.PARTY_ROLE_PRODORG){
            return qualityInspectMatItemServiceModel.getProductionOrgParty();
        }
        if(partyRole == QualityInspectMatItemParty.PARTY_ROLE_SUPPLIER){
            return qualityInspectMatItemServiceModel.getCorporateSupplierParty();
        }
        if(partyRole == QualityInspectMatItemParty.PARTY_ROLE_SALESORG){
            return qualityInspectMatItemServiceModel.getSalesOrganizationParty();
        }
        if(partyRole == QualityInspectMatItemParty.PARTY_ROLE_PURORG){
            return qualityInspectMatItemServiceModel.getPurchaseOrgParty();
        }
        return null;
    }

    @Override
    public void setDocInvolveParty(QualityInspectOrderServiceModel qualityInspectOrderServiceModel,
                                   DocInvolveParty docInvolveParty) {
        QualityInspectOrderParty qualityInspectOrderParty = (QualityInspectOrderParty) docInvolveParty;
        if(qualityInspectOrderParty.getPartyRole() == QualityInspectOrderParty.PARTY_ROLE_CUSTOMER){
            qualityInspectOrderServiceModel.setCorporateCustomerParty(qualityInspectOrderParty);
        }
        if(qualityInspectOrderParty.getPartyRole() == QualityInspectOrderParty.PARTY_ROLE_PRODORG){
            qualityInspectOrderServiceModel.setProductionOrgParty(qualityInspectOrderParty);
        }
        if(qualityInspectOrderParty.getPartyRole() == QualityInspectOrderParty.PARTY_ROLE_SUPPLIER){
            qualityInspectOrderServiceModel.setCorporateSupplierParty(qualityInspectOrderParty);
        }
        if(qualityInspectOrderParty.getPartyRole() == QualityInspectOrderParty.PARTY_ROLE_SALESORG){
            qualityInspectOrderServiceModel.setSalesOrganizationParty(qualityInspectOrderParty);
        }
        if(qualityInspectOrderParty.getPartyRole() == QualityInspectOrderParty.PARTY_ROLE_PURORG){
            qualityInspectOrderServiceModel.setPurchaseOrgParty(qualityInspectOrderParty);
        }
    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {
        QualityInspectMatItemParty qualityInspectMatItemParty = (QualityInspectMatItemParty) docInvolveParty;
        QualityInspectMatItemServiceModel qualityInspectMatItemServiceModel =
                (QualityInspectMatItemServiceModel) itemServiceModule;
        if(qualityInspectMatItemParty.getPartyRole() == QualityInspectMatItemParty.PARTY_ROLE_CUSTOMER){
            qualityInspectMatItemServiceModel.setCorporateCustomerParty(qualityInspectMatItemParty);
        }
        if(qualityInspectMatItemParty.getPartyRole() == QualityInspectMatItemParty.PARTY_ROLE_PRODORG){
            qualityInspectMatItemServiceModel.setProductionOrgParty(qualityInspectMatItemParty);
        }
        if(qualityInspectMatItemParty.getPartyRole() == QualityInspectMatItemParty.PARTY_ROLE_SUPPLIER){
            qualityInspectMatItemServiceModel.setCorporateSupplierParty(qualityInspectMatItemParty);
        }
        if(qualityInspectMatItemParty.getPartyRole() == QualityInspectMatItemParty.PARTY_ROLE_SALESORG){
            qualityInspectMatItemServiceModel.setSalesOrganizationParty(qualityInspectMatItemParty);
        }
        if(qualityInspectMatItemParty.getPartyRole() == QualityInspectMatItemParty.PARTY_ROLE_PURORG){
            qualityInspectMatItemServiceModel.setPurchaseOrgParty(qualityInspectMatItemParty);
        }
    }

    @Override
    public List<?> getDocMatItemServiceModuleList(QualityInspectOrderServiceModel qualityInspectOrderServiceModel) {
        return qualityInspectOrderServiceModel.getQualityInspectMatItemList();
    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return qualityInspectOrderManager.initStatusMap(lanCode);
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return null;
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(QualityInspectOrder.class, QualityInspectOrderServiceModel.class, QualityInsOrderActionNode.class,
                QualityInspectMatItem.class, QualityInspectMatItemServiceModel.class, null);
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(QualityInspectOrder.SENAME, QualityInspectOrderUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(QualityInspectMatItem.NODENAME,
                QualityInspectMatItemUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        return null;
    }

    @Override
    public void traverseMatItemNode(QualityInspectOrderServiceModel qualityInspectOrderServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution<QualityInspectMatItem> docItemActionCallback,
                                            SerialLogonInfo serialLogonInfo) throws DocActionException {
        if (!ServiceCollectionsHelper
                .checkNullList(qualityInspectOrderServiceModel.getQualityInspectMatItemList())) {
            for (QualityInspectMatItemServiceModel qualityInspectOrderMaterialItemServiceModel :
                    qualityInspectOrderServiceModel
                            .getQualityInspectMatItemList()) {
                if (docItemActionCallback != null) {
                    docItemActionCallback.execute(qualityInspectOrderMaterialItemServiceModel.getQualityInspectMatItem(), new DocActionExecutionProxy.ItemSelectionExecutionContext());
                }
            }
        }
    }

}
