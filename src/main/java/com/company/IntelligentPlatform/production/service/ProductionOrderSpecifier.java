package com.company.IntelligentPlatform.production.service;


import com.company.IntelligentPlatform.production.dto.ProductionOrderItemUIModel;
import com.company.IntelligentPlatform.production.service.i18n.IProductionI18nPackage;
import com.company.IntelligentPlatform.production.dto.ProdOrderTargetMatItemUIModel;
import com.company.IntelligentPlatform.production.dto.ProductionOrderServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProductionOrderUIModel;
import com.company.IntelligentPlatform.production.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.controller.DocUIModelExtensionBuilder;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionHelper;
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
public class ProductionOrderSpecifier extends DocumentContentSpecifier<ProductionOrderServiceModel, ProductionOrder,
        ProdOrderTargetMatItem> {

    @Autowired
    protected ProductionOrderServiceUIModelExtension productionOrderServiceUIModelExtension;

    @Autowired
    protected ProductionOrderManager productionOrderManager;

    @Autowired
    protected ProductionOrderItemManager productionOrderItemManager;

    @Autowired
    protected ProductionOrderIdHelper productionOrderIdHelper;

    @Autowired
    protected BillOfMaterialOrderManager billOfMaterialOrderManager;

    @Autowired
    protected ProdWorkCenterManager prodWorkCenterManager;

    @Autowired
    protected ProductionPlanManager productionPlanManager;

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER;
    }

    @Override
    public Integer getDocumentStatus(ProductionOrder productionOrder) {
        return productionOrder.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return productionOrderManager;
    }

    @Override
    public ProductionOrder setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        ProductionOrder productionOrder = (ProductionOrder) serviceEntityTargetStatus.getServiceEntityNode();
        productionOrder.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return productionOrder;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return productionOrderIdHelper;
    }

    @Override
    public DocUIModelExtensionBuilder getDocUIModelExtensionBuilder() throws DocActionException {
        // Doc root node: `ProductionOrder`
        DocUIModelExtensionBuilder docUIModelExtensionBuilder = docUIModelExtensionFactory.getBuilder(ProductionOrder.class, ProductionOrderUIModel.class, getDocumentManager());
        docUIModelExtensionBuilder.convDocToUIMethod(ProductionOrderManager.METHOD_ConvProductionOrderToUI).convDocUIToMethod(ProductionOrderManager.METHOD_ConvUIToProductionOrder);
        // Root flat submodules: `ProductionOrderAttachment`, `ProductionOrderActionNode`, `ProductionOrderParty`
        docUIModelExtensionBuilder.docAttachmentClass(ProductionOrderAttachment.class).docActionClass(ProductionOrderActionNode.class).docInvolvePartyClass(ProductionOrderParty.class);
        docUIModelExtensionBuilder.addMapConfigureBuilder(ProductionOrder.SENAME, ServiceUIModelExtensionHelper.genUIConfBuilder(BillOfMaterialOrder.class, ProductionOrderUIModel.class)
                .serviceEntityManager(billOfMaterialOrderManager)
                .addConnectionCondition("refBillOfMaterialUUID").convToUIMethod(ProductionOrderManager.METHOD_ConvOutBillOfMaterialOrderToUI).baseNodeInstId(ProductionOrder.SENAME));
        docUIModelExtensionBuilder.addMapConfigureBuilder(ProductionOrder.SENAME, ServiceUIModelExtensionHelper.genUIConfBuilder(BillOfMaterialItem.class, ProductionOrderUIModel.class)
                .serviceEntityManager(billOfMaterialOrderManager)
                .addConnectionCondition("outBillOfMaterialItem").convToUIMethod(ProductionOrderManager.METHOD_ConvOutBillOfMaterialItemToUI).
                baseNodeInstId(ProductionOrder.SENAME));
        docUIModelExtensionBuilder.addMapConfigureBuilder(ProductionPlan.SENAME, ServiceUIModelExtensionHelper.genUIConfBuilder(ProductionPlan.class, ProductionOrderUIModel.class)
                .serviceEntityManager(productionPlanManager)
                .addConnectionCondition("refPlanUUID").convToUIMethod(ProductionOrderManager.METHOD_ConvProductionPlanToUI).
                baseNodeInstId(ProductionOrder.SENAME));
        docUIModelExtensionBuilder.addMapConfigureBuilder(ProductionOrder.SENAME, ServiceUIModelExtensionHelper.genUIConfBuilder(ProdWorkCenter.class, ProductionOrderUIModel.class)
                .serviceEntityManager(prodWorkCenterManager)
                .addConnectionCondition("refWocUUID").convToUIMethod(ProductionOrderManager.METHOD_ConvProdWorkCenterToUI).
                baseNodeInstId(ProductionOrder.SENAME));
        docUIModelExtensionBuilder.docInvolvePartyBuilderInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(ProductionOrderParty.PARTY_ROLE_SUPPLIER,
                                ProductionOrderParty.PARTY_NODEINST_PUR_SUPPLIER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(ProductionOrderParty.PARTY_ROLE_PURORG,
                                ProductionOrderParty.PARTY_NODEINST_PUR_ORG,
                                Organization.class,
                                Employee.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(ProductionOrderParty.PARTY_ROLE_CUSTOMER,
                                ProductionOrderParty.PARTY_NODEINST_SOLD_CUSTOMER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(ProductionOrderParty.PARTY_ROLE_SALESORG,
                                ProductionOrderParty.PARTY_NODEINST_SOLD_ORG,
                                Organization.class,
                                Employee.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(ProductionOrderParty.PARTY_ROLE_PRODORG,
                                ProductionOrderParty.PARTY_NODEINST_PROD_ORG,
                                Organization.class,
                                Employee.class)
                )
        ).docActionNodeBuildInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(ProductionOrderActionNode.DOC_ACTION_APPROVE,
                                ProductionOrderActionNode.NODEINST_ACTION_APPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(ProductionOrderActionNode.DOC_ACTION_COUNTAPPROVE,
                                ProductionOrderActionNode.NODEINST_ACTION_COUNTAPPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(ProductionOrderActionNode.DOC_ACTION_REJECT_APPROVE,
                                ProductionOrderActionNode.NODEINST_ACTION_REJECT_APPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(ProductionOrderActionNode.DOC_ACTION_SUBMIT,
                                ProductionOrderActionNode.NODEINST_ACTION_SUBMIT),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(ProductionOrderActionNode.DOC_ACTION_INPRODUCTION,
                                ProductionOrderActionNode.NODEINST_ACTION_INPRODUCTION),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(ProductionOrderActionNode.DOC_ACTION_FINISHED,
                                ProductionOrderActionNode.NODEINST_ACTION_FINISHED)
                )
        );
        // Doc item node: `ProductionOrderItem`
        docUIModelExtensionBuilder.itemModelClass(ProductionOrderItem.class).itemUiModelClass(ProductionOrderItemUIModel.class)
                .itemInvolvePartyClass(ProductionOrderItemParty.class)
                .convItemUIToMethod(ProductionOrderItemManager.METHOD_ConvUIToProductionOrderItem).itemToParentDocMethod(ProductionOrderItemManager.METHOD_ConvProductionOrderToItemUI).
                convItemToUIMethod(ProductionOrderItemManager.METHOD_ConvProductionOrderItemToUI).itemLogicManager(productionOrderItemManager);
        docUIModelExtensionBuilder.itemInvolvePartyBuilderInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(ProductionOrderItemParty.PARTY_ROLE_SUPPLIER,
                                ProductionOrderItemParty.PARTY_NODEINST_PUR_SUPPLIER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(ProductionOrderItemParty.PARTY_ROLE_PURORG,
                                ProductionOrderItemParty.PARTY_NODEINST_PUR_ORG,
                                Organization.class,
                                Employee.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(ProductionOrderItemParty.PARTY_ROLE_CUSTOMER,
                                ProductionOrderItemParty.PARTY_NODEINST_SOLD_CUSTOMER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(ProductionOrderItemParty.PARTY_ROLE_SALESORG,
                                ProductionOrderItemParty.PARTY_NODEINST_SOLD_ORG,
                                Organization.class,
                                Employee.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(ProductionOrderItemParty.PARTY_ROLE_PRODORG,
                                ProductionOrderItemParty.PARTY_NODEINST_PROD_ORG,
                                Organization.class,
                                Employee.class)
                )
        );
//        try {
//            docUIModelExtensionBuilder.addMapConfigureBuilder(ProductionOrderItem.NODENAME, ServiceUIModelExtensionHelper.genUIConfBuilder(Warehouse.class, ProductionOrderItemUIModel.class)
//                    .serviceEntityManager(warehouseManager)
//                    .addConnectionCondition("refWarehouseUUID").convToUIMethod(ProductionOrderItemManager.METHOD_ConvWarehouseToItemUI).baseNodeInstId(ProductionOrderItem.NODENAME));
//            docUIModelExtensionBuilder.addMapConfigureBuilder(ProductionOrderItem.NODENAME, ServiceUIModelExtensionHelper.genUIConfBuilder(WarehouseArea.class, ProductionOrderItemUIModel.class)
//                    .serviceEntityManager(warehouseManager)
//                    .addConnectionCondition("refWarehouseAreaUUID").convToUIMethod(ProductionOrderItemManager.METHOD_ConvWarehouseAreaToItemUI).
//                    baseNodeInstId(ProductionOrderItem.NODENAME));
//        } catch (ServiceEntityConfigureException e) {
//            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
//        }
        return docUIModelExtensionBuilder;
    }


    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, ProductionOrderServiceModel productionOrderServiceModel) {
        if(partyRole == ProductionOrderParty.PARTY_ROLE_CUSTOMER){
            return productionOrderServiceModel.getCorporateCustomerParty();
        }
        if(partyRole == ProductionOrderParty.PARTY_ROLE_PRODORG){
            return productionOrderServiceModel.getProductionOrgParty();
        }
        if(partyRole == ProductionOrderParty.PARTY_ROLE_SALESORG){
            return productionOrderServiceModel.getSalesOrganizationParty();
        }
        if(partyRole == ProductionOrderParty.PARTY_ROLE_PURORG){
            return productionOrderServiceModel.getPurchaseOrgParty();
        }
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        return null;
    }


    @Override
    public void setDocInvolveParty(ProductionOrderServiceModel productionOrderServiceModel,
                                   DocInvolveParty docInvolveParty) {
        ProductionOrderParty productionOrderParty = (ProductionOrderParty) docInvolveParty;
        if(productionOrderParty.getPartyRole() == ProductionOrderParty.PARTY_ROLE_CUSTOMER){
            productionOrderServiceModel.setCorporateCustomerParty(productionOrderParty);
        }
        if(productionOrderParty.getPartyRole() == ProductionOrderParty.PARTY_ROLE_PRODORG){
            productionOrderServiceModel.setProductionOrgParty(productionOrderParty);
        }
        if(productionOrderParty.getPartyRole() == ProductionOrderParty.PARTY_ROLE_SALESORG){
            productionOrderServiceModel.setSalesOrganizationParty(productionOrderParty);
        }
        if(productionOrderParty.getPartyRole() == ProductionOrderParty.PARTY_ROLE_PURORG){
            productionOrderServiceModel.setPurchaseOrgParty(productionOrderParty);
        }
    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public List<?> getDocMatItemServiceModuleList(ProductionOrderServiceModel productionOrderServiceModel) {
        return productionOrderServiceModel.getProdOrderTargetMatItemList();
    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return productionOrderManager.initStatusMap(lanCode);
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return null;
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(ProductionOrder.class, ProductionOrderServiceModel.class, ProductionOrderActionNode.class,
                ProdOrderTargetMatItem.class, ProdOrderTargetMatItemServiceModel.class, null);
    }

    @Override
    public ServiceUIModelExtension getDocServiceUIModelExtension() {
        return productionOrderServiceUIModelExtension;
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(ProductionOrder.SENAME, ProductionOrderUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(ProdOrderTargetMatItem.NODENAME,
                ProdOrderTargetMatItemUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = IProductionI18nPackage.class.getResource("").getPath();
        propertyMapList.add(new PropertyMap(ProductionOrder.SENAME, basePath + "ProductionOrder"));
        propertyMapList.add(new PropertyMap(ProdOrderTargetMatItem.NODENAME,
                basePath + "ProdOrderTargetMatItem"));
        propertyMapList.add(DocumentContentSpecifier.getActionNodePropertyMap("actionNode"));
        return propertyMapList;
    }

    @Override
    public void traverseMatItemNode(ProductionOrderServiceModel productionOrderServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution<ProdOrderTargetMatItem> docItemActionCallback,
                                    SerialLogonInfo serialLogonInfo) throws DocActionException {
        if (!ServiceCollectionsHelper
                .checkNullList(productionOrderServiceModel.getProdOrderTargetMatItemList())) {
            for (ProdOrderTargetMatItemServiceModel prodOrderTargetMatItemServiceModel :
                    productionOrderServiceModel
                            .getProdOrderTargetMatItemList()) {
                if (docItemActionCallback != null) {
                    docItemActionCallback.execute(prodOrderTargetMatItemServiceModel.getProdOrderTargetMatItem(), new DocActionExecutionProxy.ItemSelectionExecutionContext());
                }
            }
        }
    }

}
