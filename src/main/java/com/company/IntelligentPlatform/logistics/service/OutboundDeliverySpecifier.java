package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.service.IDeliveryI18nPackage;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.controller.DocUIModelExtensionBuilder;
import com.company.IntelligentPlatform.common.controller.DocUIModelExtensionFactory;
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
public class OutboundDeliverySpecifier extends DocumentContentSpecifier<OutboundDeliveryServiceModel, OutboundDelivery,
        OutboundItem> {

    @Autowired
    protected OutboundDeliveryServiceUIModelExtension outboundDeliveryServiceUIModelExtension;

    @Autowired
    protected OutboundDeliveryManager outboundDeliveryManager;

    @Autowired
    protected OutboundItemManager outboundItemManager;

    @Autowired
    protected OutboundDeliveryIdHelper outboundDeliveryIdHelper;

    @Autowired
    protected WarehouseManager warehouseManager;

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY;
    }

    @Override
    public Integer getDocumentStatus(OutboundDelivery outboundDelivery) {
        return outboundDelivery.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return outboundDeliveryManager;
    }

    @Override
    public OutboundDelivery setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        OutboundDelivery outboundDelivery = (OutboundDelivery) serviceEntityTargetStatus.getServiceEntityNode();
        outboundDelivery.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return outboundDelivery;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return outboundDeliveryIdHelper;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, OutboundDeliveryServiceModel outboundDeliveryServiceModel) {
        if (partyRole == OutboundDeliveryParty.PARTY_ROLE_CUSTOMER) {
            return outboundDeliveryServiceModel.getSoldToCustomer();
        }
        if (partyRole == OutboundDeliveryParty.PARTY_ROLE_PRODORG) {
            return outboundDeliveryServiceModel.getProductionOrg();
        }
        if (partyRole == OutboundDeliveryParty.PARTY_ROLE_SUPPLIER) {
            return outboundDeliveryServiceModel.getPurchaseFromSupplier();
        }
        if (partyRole == OutboundDeliveryParty.PARTY_ROLE_SALESORG) {
            return outboundDeliveryServiceModel.getSoldFromOrg();
        }
        if (partyRole == OutboundDeliveryParty.PARTY_ROLE_PURORG) {
            return outboundDeliveryServiceModel.getPurchaseToOrg();
        }
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        OutboundItemServiceModel outboundItemServiceModel = (OutboundItemServiceModel) itemServiceModule;
        if (partyRole == OutboundItemParty.PARTY_ROLE_CUSTOMER) {
            return outboundItemServiceModel.getSoldToCustomer();
        }
        if (partyRole == OutboundItemParty.PARTY_ROLE_PRODORG) {
            return outboundItemServiceModel.getProductionOrg();
        }
        if (partyRole == OutboundItemParty.PARTY_ROLE_SUPPLIER) {
            return outboundItemServiceModel.getPurchaseFromSupplier();
        }
        if (partyRole == OutboundItemParty.PARTY_ROLE_SALESORG) {
            return outboundItemServiceModel.getSoldFromOrg();
        }
        if (partyRole == OutboundItemParty.PARTY_ROLE_PURORG) {
            return outboundItemServiceModel.getPurchaseToOrg();
        }
        return null;
    }

    @Override
    public void setDocInvolveParty(OutboundDeliveryServiceModel outboundDeliveryServiceModel, DocInvolveParty docInvolveParty) {
        OutboundDeliveryParty outboundDeliveryParty = (OutboundDeliveryParty) docInvolveParty;
        if (outboundDeliveryParty.getPartyRole() == OutboundItemParty.PARTY_ROLE_CUSTOMER) {
            outboundDeliveryServiceModel.setSoldToCustomer(outboundDeliveryParty);
        }
        if (outboundDeliveryParty.getPartyRole() == OutboundItemParty.PARTY_ROLE_PRODORG) {
            outboundDeliveryServiceModel.setProductionOrg(outboundDeliveryParty);
        }
        if (outboundDeliveryParty.getPartyRole() == OutboundItemParty.PARTY_ROLE_SUPPLIER) {
            outboundDeliveryServiceModel.setPurchaseFromSupplier(outboundDeliveryParty);
        }
        if (outboundDeliveryParty.getPartyRole() == OutboundItemParty.PARTY_ROLE_SALESORG) {
            outboundDeliveryServiceModel.setSoldFromOrg(outboundDeliveryParty);
        }
        if (outboundDeliveryParty.getPartyRole() == OutboundItemParty.PARTY_ROLE_PURORG) {
            outboundDeliveryServiceModel.setPurchaseToOrg(outboundDeliveryParty);
        }
    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {
        OutboundItemParty outboundItemParty = (OutboundItemParty) docInvolveParty;
        OutboundItemServiceModel outboundItemServiceModel = (OutboundItemServiceModel) itemServiceModule;
        if (outboundItemParty.getPartyRole() == OutboundItemParty.PARTY_ROLE_CUSTOMER) {
            outboundItemServiceModel.setSoldToCustomer(outboundItemParty);
        }
        if (outboundItemParty.getPartyRole() == OutboundItemParty.PARTY_ROLE_PRODORG) {
            outboundItemServiceModel.setProductionOrg(outboundItemParty);
        }
        if (outboundItemParty.getPartyRole() == OutboundItemParty.PARTY_ROLE_SUPPLIER) {
            outboundItemServiceModel.setPurchaseFromSupplier(outboundItemParty);
        }
        if (outboundItemParty.getPartyRole() == OutboundItemParty.PARTY_ROLE_SALESORG) {
            outboundItemServiceModel.setSoldFromOrg(outboundItemParty);
        }
        if (outboundItemParty.getPartyRole() == OutboundItemParty.PARTY_ROLE_PURORG) {
            outboundItemServiceModel.setPurchaseToOrg(outboundItemParty);
        }
    }

    @Override
    public List<?> getDocMatItemServiceModuleList(OutboundDeliveryServiceModel outboundDeliveryServiceModel) {
        return outboundDeliveryServiceModel.getOutboundItemList();
    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return outboundDeliveryManager.initStatusMap(lanCode);
    }

    @Override
    public DocUIModelExtensionBuilder getDocUIModelExtensionBuilder() throws DocActionException {
        // Doc root node: `OutboundDelivery`
        DocUIModelExtensionBuilder docUIModelExtensionBuilder = docUIModelExtensionFactory.getBuilder(OutboundDelivery.class, OutboundDeliveryUIModel.class, getDocumentManager());
        docUIModelExtensionBuilder.convDocToUIMethod(OutboundDeliveryManager.METHOD_ConvOutboundDeliveryToUI).convDocUIToMethod(OutboundDeliveryManager.METHOD_ConvUIToOutboundDelivery);
        // Root flat submodules: `OutboundDeliveryAttachment`, `OutboundDeliveryActionNode`, `OutboundDeliveryParty`
        docUIModelExtensionBuilder.docAttachmentClass(OutboundDeliveryAttachment.class).docActionClass(OutboundDeliveryActionNode.class).docInvolvePartyClass(OutboundDeliveryParty.class);
        docUIModelExtensionFactory.addWarehouseMapConfigureList(docUIModelExtensionBuilder, DocUIModelExtensionFactory.genWarehouseConfig(OutboundDelivery.SENAME)
                .convWarehouseToUIMethod(OutboundDeliveryManager.METHOD_ConvWarehouseToUI).convWarehouseAreaToUIMethod(OutboundDeliveryManager.METHOD_ConvWarehouseAreaToUI));
        docUIModelExtensionBuilder.docInvolvePartyBuilderInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(OutboundDeliveryParty.PARTY_ROLE_SUPPLIER,
                                OutboundDeliveryParty.PARTY_NODEINST_PUR_SUPPLIER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(OutboundDeliveryParty.PARTY_ROLE_PURORG,
                                OutboundDeliveryParty.PARTY_NODEINST_PUR_ORG,
                                Organization.class,
                                Employee.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(OutboundDeliveryParty.PARTY_ROLE_CUSTOMER,
                                OutboundDeliveryParty.PARTY_NODEINST_SOLD_CUSTOMER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(OutboundDeliveryParty.PARTY_ROLE_SALESORG,
                                OutboundDeliveryParty.PARTY_NODEINST_SOLD_ORG,
                                Organization.class,
                                Employee.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(OutboundDeliveryParty.PARTY_ROLE_PRODORG,
                                OutboundDeliveryParty.PARTY_NODEINST_PROD_ORG,
                                Organization.class,
                                Employee.class)
                )
        ).docActionNodeBuildInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(OutboundDeliveryActionNode.DOC_ACTION_APPROVE,
                                OutboundDeliveryActionNode.NODEINST_ACTION_APPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(OutboundDeliveryActionNode.DOC_ACTION_COUNTAPPROVE,
                                OutboundDeliveryActionNode.NODEINST_ACTION_COUNTAPPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(OutboundDeliveryActionNode.DOC_ACTION_REJECT_APPROVE,
                                OutboundDeliveryActionNode.NODEINST_ACTION_REJECT_APPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(OutboundDeliveryActionNode.DOC_ACTION_RECORD_DONE,
                                OutboundDeliveryActionNode.NODEINST_ACTION_DELIVERY_DONE)
                )
        );
        // Doc item node: `OutboundItem`
        docUIModelExtensionBuilder.itemModelClass(OutboundItem.class).itemUiModelClass(OutboundItemUIModel.class)
                .itemAttachmentClass(OutboundItemAttachment.class).itemInvolvePartyClass(OutboundItemParty.class)
                .convItemUIToMethod(OutboundItemManager.METHOD_ConvUIToOutboundItem).itemToParentDocMethod(OutboundItemManager.METHOD_ConvParentDocToItemUI).
                convItemToUIMethod(OutboundItemManager.METHOD_ConvOutboundItemToUI).itemLogicManager(outboundItemManager);
        docUIModelExtensionBuilder.itemInvolvePartyBuilderInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(OutboundItemParty.PARTY_ROLE_SUPPLIER,
                                OutboundItemParty.PARTY_NODEINST_PUR_SUPPLIER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(OutboundItemParty.PARTY_ROLE_PURORG,
                                OutboundItemParty.PARTY_NODEINST_PUR_ORG,
                                Organization.class,
                                Employee.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(OutboundItemParty.PARTY_ROLE_CUSTOMER,
                                OutboundItemParty.PARTY_NODEINST_SOLD_CUSTOMER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(OutboundItemParty.PARTY_ROLE_SALESORG,
                                OutboundItemParty.PARTY_NODEINST_SOLD_ORG,
                                Organization.class,
                                Employee.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(OutboundItemParty.PARTY_ROLE_PRODORG,
                                OutboundItemParty.PARTY_NODEINST_PROD_ORG,
                                Organization.class,
                                Employee.class)
                )
        );
        docUIModelExtensionFactory.addWarehouseMapConfigureList(docUIModelExtensionBuilder, DocUIModelExtensionFactory.genWarehouseConfig(OutboundItem.NODENAME)
                .convWarehouseToUIMethod(OutboundItemManager.METHOD_ConvWarehouseToItemUI).baseAreaNodeInstId(OutboundItem.NODENAME)
                .logicManager(outboundItemManager).baseUIModelClass(OutboundItemUIModel.class).baseWarehouseNodeInstId(OutboundDelivery.SENAME)
                .convWarehouseAreaToUIMethod(OutboundItemManager.METHOD_ConvWarehouseAreaToItemUI));
        return docUIModelExtensionBuilder;
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return super.getInvolvePartyMapWrapper(lanCode, OutboundDeliveryUIModel.class, "OutboundDelivery_involveParty");
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(OutboundDelivery.class, OutboundDeliveryServiceModel.class, OutboundDeliveryActionNode.class,
                OutboundItem.class, OutboundItemServiceModel.class, null);
    }

    @Override
    public ServiceUIModelExtension getDocServiceUIModelExtension() {
        return outboundDeliveryServiceUIModelExtension;
    }

    @Override
    public void traverseMatItemNode(OutboundDeliveryServiceModel outboundDeliveryServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution<OutboundItem> docItemActionCallback,
                                    SerialLogonInfo serialLogonInfo) throws DocActionException {
        if (!ServiceCollectionsHelper
                .checkNullList(outboundDeliveryServiceModel.getOutboundItemList())) {
            for (OutboundItemServiceModel outboundItemServiceModel :
                    outboundDeliveryServiceModel
                            .getOutboundItemList()) {
                if (docItemActionCallback != null) {
                    docItemActionCallback.execute(outboundItemServiceModel.getOutboundItem(), new DocActionExecutionProxy.ItemSelectionExecutionContext());
                }
            }
        }
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(OutboundDelivery.SENAME, OutboundDeliveryUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(OutboundItem.NODENAME,
                OutboundItemUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = IDeliveryI18nPackage.class.getResource("").getPath();
        propertyMapList.add(new PropertyMap(OutboundDelivery.SENAME, basePath + "OutboundDelivery"));
        propertyMapList.add(new PropertyMap(OutboundItem.NODENAME,
                basePath + "OutboundItem"));
        propertyMapList.add(DocumentContentSpecifier.getActionNodePropertyMap("actionNode"));
        propertyMapList.addAll(DocumentContentSpecifier.getInvolvePartyPropertyMap("PurchaseFromSupplier", basePath +
                "PurchaseFromSupplier"));
        propertyMapList.addAll(DocumentContentSpecifier.getInvolvePartyPropertyMap("SoldToCustomer", basePath +
                "SoldToCustomer"));
        propertyMapList.addAll(DocumentContentSpecifier.getInvolvePartyPropertyMap("ProductionOrg", basePath +
                "ProductionOrg"));
        propertyMapList.addAll(DocumentContentSpecifier.getInvolvePartyPropertyMap("PurchaseToOrg", basePath +
                "PurchaseToOrg"));
        propertyMapList.addAll(DocumentContentSpecifier.getInvolvePartyPropertyMap("SoldFromOrg", basePath +
                "SoldFromOrg"));
        return propertyMapList;
    }

}
