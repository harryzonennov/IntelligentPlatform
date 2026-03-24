package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.service.IDeliveryI18nPackage;
import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.controller.DocUIModelExtensionBuilder;
import com.company.IntelligentPlatform.common.controller.DocUIModelExtensionFactory;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
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
public class InboundDeliverySpecifier extends DocumentContentSpecifier<InboundDeliveryServiceModel, InboundDelivery,
        InboundItem> {

    @Autowired
    protected InboundDeliveryServiceUIModelExtension inboundDeliveryServiceUIModelExtension;

    @Autowired
    protected InboundDeliveryManager inboundDeliveryManager;

    @Autowired
    protected InboundItemManager inboundItemManager;

    @Autowired
    protected InboundDeliveryIdHelper inboundDeliveryIdHelper;

    @Autowired
    protected WarehouseManager warehouseManager;

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY;
    }

    @Override
    public Integer getDocumentStatus(InboundDelivery inboundDelivery) {
        return inboundDelivery.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return inboundDeliveryManager;
    }

    @Override
    public InboundDelivery setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        InboundDelivery inboundDelivery = (InboundDelivery) serviceEntityTargetStatus.getServiceEntityNode();
        inboundDelivery.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return inboundDelivery;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return inboundDeliveryIdHelper;
    }

    @Override
    public DocUIModelExtensionBuilder getDocUIModelExtensionBuilder() throws DocActionException {
        // Doc root node: `InboundDelivery`
        DocUIModelExtensionBuilder docUIModelExtensionBuilder = docUIModelExtensionFactory.getBuilder(InboundDelivery.class, InboundDeliveryUIModel.class, getDocumentManager());
        docUIModelExtensionBuilder.convDocToUIMethod(InboundDeliveryManager.METHOD_ConvInboundDeliveryToUI).convDocUIToMethod(InboundDeliveryManager.METHOD_ConvUIToInboundDelivery);
        // Root flat submodules: `InboundDeliveryAttachment`, `InboundDeliveryActionNode`, `InboundDeliveryParty`
        docUIModelExtensionBuilder.docAttachmentClass(InboundDeliveryAttachment.class).docActionClass(InboundDeliveryActionNode.class).docInvolvePartyClass(InboundDeliveryParty.class);
        docUIModelExtensionFactory.addWarehouseMapConfigureList(docUIModelExtensionBuilder, DocUIModelExtensionFactory.genWarehouseConfig(InboundDelivery.SENAME)
                .convWarehouseToUIMethod(InboundDeliveryManager.METHOD_ConvWarehouseToUI).convWarehouseAreaToUIMethod(InboundDeliveryManager.METHOD_ConvWarehouseAreaToUI));
        docUIModelExtensionBuilder.docInvolvePartyBuilderInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(InboundDeliveryParty.PARTY_ROLE_SUPPLIER,
                                InboundDeliveryParty.PARTY_NODEINST_PUR_SUPPLIER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(InboundDeliveryParty.PARTY_ROLE_PURORG,
                                InboundDeliveryParty.PARTY_NODEINST_PUR_ORG,
                                Organization.class,
                                Employee.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(InboundDeliveryParty.PARTY_ROLE_CUSTOMER,
                                InboundDeliveryParty.PARTY_NODEINST_SOLD_CUSTOMER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(InboundDeliveryParty.PARTY_ROLE_SALESORG,
                                InboundDeliveryParty.PARTY_NODEINST_SOLD_ORG,
                                Organization.class,
                                Employee.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(InboundDeliveryParty.PARTY_ROLE_PRODORG,
                                InboundDeliveryParty.PARTY_NODEINST_PROD_ORG,
                                Organization.class,
                                Employee.class)
                )
        ).docActionNodeBuildInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(InboundDeliveryActionNode.DOC_ACTION_APPROVE,
                                InboundDeliveryActionNode.NODEINST_ACTION_APPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(InboundDeliveryActionNode.DOC_ACTION_COUNTAPPROVE,
                                InboundDeliveryActionNode.NODEINST_ACTION_COUNTAPPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(InboundDeliveryActionNode.DOC_ACTION_REJECT_APPROVE,
                                InboundDeliveryActionNode.NODEINST_ACTION_REJECT_APPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(InboundDeliveryActionNode.DOC_ACTION_RECORD_DONE,
                                InboundDeliveryActionNode.NODEINST_ACTION_DELIVERY_DONE)
                )
        );
        // Doc item node: `InboundItem`
        docUIModelExtensionBuilder.itemModelClass(InboundItem.class).itemUiModelClass(InboundItemUIModel.class)
                .itemAttachmentClass(InboundItemAttachment.class).itemInvolvePartyClass(InboundItemParty.class)
                .convItemUIToMethod(InboundItemManager.METHOD_ConvUIToInboundItem).itemToParentDocMethod(InboundItemManager.METHOD_ConvParentDocToItemUI).
                convItemToUIMethod(InboundItemManager.METHOD_ConvInboundItemToUI).itemLogicManager(inboundItemManager);
        docUIModelExtensionBuilder.itemInvolvePartyBuilderInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(InboundItemParty.PARTY_ROLE_SUPPLIER,
                                InboundItemParty.PARTY_NODEINST_PUR_SUPPLIER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(InboundItemParty.PARTY_ROLE_PURORG,
                                InboundItemParty.PARTY_NODEINST_PUR_ORG,
                                Organization.class,
                                Employee.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(InboundItemParty.PARTY_ROLE_CUSTOMER,
                                InboundItemParty.PARTY_NODEINST_SOLD_CUSTOMER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(InboundItemParty.PARTY_ROLE_SALESORG,
                                InboundItemParty.PARTY_NODEINST_SOLD_ORG,
                                Organization.class,
                                Employee.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(InboundItemParty.PARTY_ROLE_PRODORG,
                                InboundItemParty.PARTY_NODEINST_PROD_ORG,
                                Organization.class,
                                Employee.class)
                )
        );
        docUIModelExtensionFactory.addWarehouseMapConfigureList(docUIModelExtensionBuilder, DocUIModelExtensionFactory.genWarehouseConfig(InboundItem.NODENAME)
                .convWarehouseToUIMethod(InboundItemManager.METHOD_ConvWarehouseToItemUI).baseAreaNodeInstId(InboundItem.NODENAME)
                .logicManager(inboundItemManager).baseUIModelClass(InboundItemUIModel.class).baseWarehouseNodeInstId(InboundDelivery.SENAME)
                .convWarehouseAreaToUIMethod(InboundItemManager.METHOD_ConvWarehouseAreaToItemUI));
        return docUIModelExtensionBuilder;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, InboundDeliveryServiceModel inboundDeliveryServiceModel) {
        if (partyRole == InboundDeliveryParty.PARTY_ROLE_CUSTOMER) {
            return inboundDeliveryServiceModel.getSoldToCustomer();
        }
        if (partyRole == InboundDeliveryParty.PARTY_ROLE_PRODORG) {
            return inboundDeliveryServiceModel.getProductionOrg();
        }
        if (partyRole == InboundDeliveryParty.PARTY_ROLE_SUPPLIER) {
            return inboundDeliveryServiceModel.getPurchaseFromSupplier();
        }
        if (partyRole == InboundDeliveryParty.PARTY_ROLE_SALESORG) {
            return inboundDeliveryServiceModel.getSoldFromOrg();
        }
        if (partyRole == InboundDeliveryParty.PARTY_ROLE_PURORG) {
            return inboundDeliveryServiceModel.getPurchaseToOrg();
        }
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule serviceModule) {
        InboundItemServiceModel inboundItemServiceModel = (InboundItemServiceModel) serviceModule;
        if (partyRole == InboundItemParty.PARTY_ROLE_CUSTOMER) {
            return inboundItemServiceModel.getSoldToCustomer();
        }
        if (partyRole == InboundItemParty.PARTY_ROLE_PRODORG) {
            return inboundItemServiceModel.getProductionOrg();
        }
        if (partyRole == InboundItemParty.PARTY_ROLE_SUPPLIER) {
            return inboundItemServiceModel.getPurchaseFromSupplier();
        }
        if (partyRole == InboundItemParty.PARTY_ROLE_SALESORG) {
            return inboundItemServiceModel.getSoldFromOrg();
        }
        if (partyRole == InboundItemParty.PARTY_ROLE_PURORG) {
            return inboundItemServiceModel.getPurchaseToOrg();
        }
        return null;
    }

    @Override
    public void setDocInvolveParty(InboundDeliveryServiceModel inboundDeliveryServiceModel, DocInvolveParty docInvolveParty) {
        InboundDeliveryParty inboundDeliveryParty = (InboundDeliveryParty) docInvolveParty;
        if (inboundDeliveryParty.getPartyRole() == InboundItemParty.PARTY_ROLE_CUSTOMER) {
            inboundDeliveryServiceModel.setSoldToCustomer(inboundDeliveryParty);
        }
        if (inboundDeliveryParty.getPartyRole() == InboundItemParty.PARTY_ROLE_PRODORG) {
            inboundDeliveryServiceModel.setProductionOrg(inboundDeliveryParty);
        }
        if (inboundDeliveryParty.getPartyRole() == InboundItemParty.PARTY_ROLE_SUPPLIER) {
            inboundDeliveryServiceModel.setPurchaseFromSupplier(inboundDeliveryParty);
        }
        if (inboundDeliveryParty.getPartyRole() == InboundItemParty.PARTY_ROLE_SALESORG) {
            inboundDeliveryServiceModel.setSoldFromOrg(inboundDeliveryParty);
        }
        if (inboundDeliveryParty.getPartyRole() == InboundItemParty.PARTY_ROLE_PURORG) {
            inboundDeliveryServiceModel.setSoldToCustomer(inboundDeliveryParty);
        }
    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {
        InboundItemParty inboundItemParty = (InboundItemParty) docInvolveParty;
        InboundItemServiceModel inboundItemServiceModel = (InboundItemServiceModel) itemServiceModule;
        if (inboundItemParty.getPartyRole() == InboundItemParty.PARTY_ROLE_CUSTOMER) {
            inboundItemServiceModel.setSoldToCustomer(inboundItemParty);
        }
        if (inboundItemParty.getPartyRole() == InboundItemParty.PARTY_ROLE_PRODORG) {
            inboundItemServiceModel.setProductionOrg(inboundItemParty);
        }
        if (inboundItemParty.getPartyRole() == InboundItemParty.PARTY_ROLE_SUPPLIER) {
            inboundItemServiceModel.setPurchaseFromSupplier(inboundItemParty);
        }
        if (inboundItemParty.getPartyRole() == InboundItemParty.PARTY_ROLE_SALESORG) {
            inboundItemServiceModel.setSoldFromOrg(inboundItemParty);
        }
        if (inboundItemParty.getPartyRole() == InboundItemParty.PARTY_ROLE_PURORG) {
            inboundItemServiceModel.setPurchaseToOrg(inboundItemParty);
        }
    }

    @Override
    public List<?> getDocMatItemServiceModuleList(InboundDeliveryServiceModel inboundDeliveryServiceModel) {
        return inboundDeliveryServiceModel.getInboundItemList();
    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return inboundDeliveryManager.initStatusMap(lanCode);
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return super.getInvolvePartyMapWrapper(lanCode, OutboundDeliveryUIModel.class, "InboundDelivery_involveParty");
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(InboundDelivery.class, InboundDeliveryServiceModel.class, InboundDeliveryActionNode.class,
                InboundItem.class, InboundItemServiceModel.class, null);
    }

    //TODO remove in the future
    @Override
    public ServiceUIModelExtension getDocServiceUIModelExtension() {
        return inboundDeliveryServiceUIModelExtension;
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(InboundDelivery.SENAME, InboundDeliveryUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(InboundItem.NODENAME,
                InboundItemUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = IDeliveryI18nPackage.class.getResource("").getPath();
        propertyMapList.add(new PropertyMap(InboundDelivery.SENAME, basePath + "InboundDelivery"));
        propertyMapList.add(new PropertyMap(InboundItem.NODENAME,
                basePath + "InboundItem"));
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

    @Override
    public void traverseMatItemNode(InboundDeliveryServiceModel inboundDeliveryServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution<InboundItem> docItemActionCallback,
                                            SerialLogonInfo serialLogonInfo) throws DocActionException {
        if (!ServiceCollectionsHelper
                .checkNullList(inboundDeliveryServiceModel.getInboundItemList())) {
            for (InboundItemServiceModel inboundItemServiceModel :
                    inboundDeliveryServiceModel
                            .getInboundItemList()) {
                if (docItemActionCallback != null) {
                    docItemActionCallback.execute(inboundItemServiceModel.getInboundItem(), new DocActionExecutionProxy.ItemSelectionExecutionContext());
                }
            }
        }
    }

}
