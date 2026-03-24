package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.OutboundDeliveryUIModel;
import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.service.ISupplyI18nPackage;
import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.DocUIModelExtensionBuilder;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.StandardSystemVariableProxy;
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
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseRequestSpecifier extends DocumentContentSpecifier<PurchaseRequestServiceModel, PurchaseRequest,
        PurchaseRequestMaterialItem> {

    @Autowired
    protected PurchaseRequestManager purchaseRequestManager;

    @Autowired
    protected PurchaseRequestIdHelper purchaseRequestIdHelper;

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    @Autowired
    protected ServiceItemIdGenerator serviceItemIdGenerator;

    @Autowired
    protected PurchaseRequestMaterialItemManager purchaseRequestMaterialItemManager;

    private Map<String, Map<Integer, String>> involvePartyLan = new HashMap<>();

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_PURCHASEREQUEST;
    }

    @Override
    public Integer getDocumentStatus(PurchaseRequest purchaseRequest) {
        return purchaseRequest.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return purchaseRequestManager;
    }

    @Override
    public PurchaseRequest setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        PurchaseRequest purchaseRequest = (PurchaseRequest) serviceEntityTargetStatus.getServiceEntityNode();
        purchaseRequest.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return purchaseRequest;
    }

    @Override
    public PurchaseRequestMaterialItem setItemStatus(DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        PurchaseRequestMaterialItem purchaseRequestMaterialItem = (PurchaseRequestMaterialItem) serviceEntityTargetStatus.getServiceEntityNode();
        purchaseRequestMaterialItem.setItemStatus(serviceEntityTargetStatus.getTargetStatus());
        return purchaseRequestMaterialItem;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return purchaseRequestIdHelper;
    }

    @Override
    public DocUIModelExtensionBuilder getDocUIModelExtensionBuilder() {
        // Doc root node: `PurchaseRequest`
        DocUIModelExtensionBuilder docUIModelExtensionBuilder = docUIModelExtensionFactory.getBuilder(PurchaseRequest.class, PurchaseRequestUIModel.class, getDocumentManager());
        docUIModelExtensionBuilder.convDocToUIMethod(PurchaseRequestManager.METHOD_ConvPurchaseRequestToUI).convDocUIToMethod(PurchaseRequestManager.METHOD_ConvUIToPurchaseRequest);
        // Root flat submodules: `PurchaseRequestAttachment`, `PurchaseRequestActionNode`, `PurchaseRequestParty`
        docUIModelExtensionBuilder.docAttachmentClass(PurchaseRequestAttachment.class).docActionClass(PurchaseRequestActionNode.class).docInvolvePartyClass(PurchaseRequestParty.class);
        docUIModelExtensionBuilder.docInvolvePartyBuilderInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(PurchaseRequestParty.ROLE_PARTYB,
                                PurchaseRequestParty.PARTY_NODEINST_PUR_SUPPLIER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(PurchaseRequestParty.ROLE_PARTYA,
                                PurchaseRequestParty.PARTY_NODEINST_PUR_ORG,
                                Organization.class,
                                Employee.class)
                )
        );
        docUIModelExtensionBuilder.docActionNodeBuildInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(PurchaseRequestActionNode.DOC_ACTION_APPROVE,
                                PurchaseRequestActionNode.NODEINST_ACTION_APPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(PurchaseRequestActionNode.DOC_ACTION_INPROCESS,
                                PurchaseRequestActionNode.NODEINST_ACTION_INPROCESS),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(PurchaseRequestActionNode.DOC_ACTION_SUBMIT,
                                PurchaseRequestActionNode.NODEINST_ACTION_SUBMIT)
                )
        );
        // Doc item node: `PurchaseRequestMaterialItem`
        docUIModelExtensionBuilder.itemModelClass(PurchaseRequestMaterialItem.class).itemUiModelClass(PurchaseRequestMaterialItemUIModel.class)
                .itemAttachmentClass(PurchaseRequestMaterialItemAttachment.class)
                .convItemUIToMethod(PurchaseRequestMaterialItemManager.METHOD_ConvUIToPurchaseRequestMaterialItem).itemToParentDocMethod(PurchaseRequestMaterialItemManager.METHOD_ConvParentDocToItemUI).
                convItemToUIMethod(PurchaseRequestMaterialItemManager.METHOD_ConvPurchaseRequestMaterialItemToUI).itemLogicManager(purchaseRequestMaterialItemManager);
        return docUIModelExtensionBuilder;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, PurchaseRequestServiceModel purchaseRequestServiceModel) {
        if(partyRole == PurchaseRequestParty.ROLE_PARTYA){
            return purchaseRequestServiceModel.getPurchaseToOrg();
        }
        if(partyRole == PurchaseRequestParty.ROLE_PARTYB){
            return purchaseRequestServiceModel.getPurchaseFromSupplier();
        }
        return null;
    }

    @Override
    public Map<String, List<ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta >> getDefAllInitConfigureMap() {
        Map<String, List<ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta >> initDocConfigureMap = new HashMap<>();
        initDocConfigureMap.put(ServiceEntityStringHelper.getDefModelId(PurchaseRequest.SENAME, PurchaseRequest.NODENAME),
                ServiceCollectionsHelper.asList(
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta ("purchaseToOrg.partyRole",null, PurchaseRequestParty.ROLE_PARTYA),
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta ("purchaseToOrg.refUUID",null, StandardSystemVariableProxy.varLogonOrganizationUUID()),
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta ("purchaseToOrg.refContactUUID",null, StandardSystemVariableProxy.varLogonUserUUID()),
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta ("purchaseFromSupplier.partyRole",null,
                                PurchaseRequestParty.ROLE_PARTYB)));
        return initDocConfigureMap;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(PurchaseRequestServiceModel purchaseRequestServiceModel, DocInvolveParty docInvolveParty) {
        PurchaseRequestParty purchaseRequestParty = (PurchaseRequestParty) docInvolveParty;
        if(purchaseRequestParty.getPartyRole() == PurchaseRequestParty.ROLE_PARTYA){
            purchaseRequestServiceModel.setPurchaseToOrg(purchaseRequestParty);
        }
        if(purchaseRequestParty.getPartyRole() == PurchaseRequestParty.ROLE_PARTYB){
            purchaseRequestServiceModel.setPurchaseFromSupplier(purchaseRequestParty);
        }
    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public List<?> getDocMatItemServiceModuleList(PurchaseRequestServiceModel purchaseRequestServiceModel) {
        return purchaseRequestServiceModel.getPurchaseRequestMaterialItemList();
    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return purchaseRequestManager.initStatus(lanCode);
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return super.getInvolvePartyMapWrapper(lanCode, OutboundDeliveryUIModel.class, "purchaseRequest_InvolveParty");
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(PurchaseRequest.class, PurchaseRequestServiceModel.class, PurchaseRequestActionNode.class,
                PurchaseRequestMaterialItem.class, PurchaseRequestMaterialItemServiceModel.class, null);
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(PurchaseRequest.SENAME, PurchaseRequestUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(PurchaseRequestMaterialItem.NODENAME,
                PurchaseRequestMaterialItemUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = ISupplyI18nPackage.class.getResource("").getPath();
        propertyMapList.addAll(DocumentContentSpecifier.getCorePropertyMap(PurchaseRequest.SENAME, basePath +
                "PurchaseRequest"));
        propertyMapList.addAll(DocumentContentSpecifier.getDocMatItemPropertyMap(PurchaseRequestMaterialItem.NODENAME,
                basePath + "PurchaseRequestMaterialItem"));
        propertyMapList.add(DocumentContentSpecifier.getActionNodePropertyMap("actionNode"));
        propertyMapList.addAll(DocumentContentSpecifier.getInvolvePartyPropertyMap("PurchaseToOrg", basePath +
                "PurchaseToOrg"));
        propertyMapList.addAll(DocumentContentSpecifier.getInvolvePartyPropertyMap("PurchaseFromSupplier", basePath +
                "PurchaseFromSupplier"));
        return propertyMapList;
    }

    @Override
    public void traverseMatItemNode(PurchaseRequestServiceModel purchaseRequestServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution<PurchaseRequestMaterialItem> docItemActionCallback,
                                            SerialLogonInfo serialLogonInfo) throws DocActionException {
        if (!ServiceCollectionsHelper
                .checkNullList(purchaseRequestServiceModel.getPurchaseRequestMaterialItemList())) {
            for (PurchaseRequestMaterialItemServiceModel purchaseRequestMaterialItemServiceModel :
                    purchaseRequestServiceModel
                            .getPurchaseRequestMaterialItemList()) {
                if (docItemActionCallback != null) {
                    docItemActionCallback.execute(purchaseRequestMaterialItemServiceModel.getPurchaseRequestMaterialItem(), new DocActionExecutionProxy.ItemSelectionExecutionContext());
                }
            }
        }
    }

}
