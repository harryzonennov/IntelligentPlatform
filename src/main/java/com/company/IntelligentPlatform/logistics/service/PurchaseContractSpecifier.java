package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.service.ISupplyI18nPackage;
import com.company.IntelligentPlatform.logistics.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.DocUIModelExtensionBuilder;
import com.company.IntelligentPlatform.common.controller.DocUIModelExtensionFactory;
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
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseContractSpecifier extends DocumentContentSpecifier<PurchaseContractServiceModel, PurchaseContract,
 PurchaseContractMaterialItem> {

    @Autowired
    protected PurchaseContractManager purchaseContractManager;

    @Autowired
    protected PurchaseContractIdHelper purchaseContractIdHelper;

    @Autowired
    protected ServiceItemIdGenerator serviceItemIdGenerator;

    @Autowired
    protected PurchaseContractMaterialItemManager purchaseContractMaterialItemManager;

    @Autowired
    protected DocUIModelExtensionFactory docUIModelExtensionFactory;

    protected Logger logger = LoggerFactory.getLogger(PurchaseContractSpecifier.class);

    private Map<String, Map<Integer, String>> involvePartyLan = new HashMap<>();

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT;
    }

    @Override
    public Integer getDocumentStatus(PurchaseContract purchaseContract) {
        return purchaseContract.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return purchaseContractManager;
    }

    @Override
    public PurchaseContract setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        PurchaseContract purchaseContract = (PurchaseContract) serviceEntityTargetStatus.getServiceEntityNode();
        purchaseContract.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return purchaseContract;
    }
    
    @Override
    public Map<String, List<ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta >> getDefAllInitConfigureMap() {
        Map<String, List<ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta >> initDocConfigureMap = new HashMap<>();
        initDocConfigureMap.put(ServiceEntityStringHelper.getDefModelId(PurchaseContract.SENAME, PurchaseContract.NODENAME),
                ServiceCollectionsHelper.asList(
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta ("purchaseToOrg.partyRole",null, PurchaseContractParty.ROLE_PARTYA),
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta ("purchaseToOrg.refUUID",null, StandardSystemVariableProxy.varLogonOrganizationUUID()),
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta ("purchaseToOrg.refContactUUID",null, StandardSystemVariableProxy.varLogonUserUUID()),
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta ("purchaseFromSupplier.partyRole",null,
                                PurchaseContractParty.ROLE_PARTYB)));
        return initDocConfigureMap;
    }

    @Override
    public DocUIModelExtensionBuilder getDocUIModelExtensionBuilder() {
        // Doc root node: `PurchaseContract`
        DocUIModelExtensionBuilder docUIModelExtensionBuilder = docUIModelExtensionFactory.getBuilder(PurchaseContract.class, PurchaseContractUIModel.class, getDocumentManager());
        docUIModelExtensionBuilder.convDocToUIMethod(PurchaseContractManager.METHOD_ConvPurchaseContractToUI).convDocUIToMethod(PurchaseContractManager.METHOD_ConvUIToPurchaseContract);
        // Root flat submodules: `PurchaseContractAttachment`, `PurchaseContractActionNode`, `PurchaseContractParty`
        docUIModelExtensionBuilder.docAttachmentClass(PurchaseContractAttachment.class).docActionClass(PurchaseContractActionNode.class).docInvolvePartyClass(PurchaseContractParty.class);
        docUIModelExtensionBuilder.docInvolvePartyBuilderInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara( PurchaseContractParty.ROLE_PARTYB,
                                PurchaseContractParty.PARTY_NODEINST_PUR_SUPPLIER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(PurchaseContractParty.ROLE_PARTYA,
                                PurchaseContractParty.PARTY_NODEINST_PUR_ORG,
                                Organization.class,
                                Employee.class)
                )
        );
        docUIModelExtensionBuilder.docActionNodeBuildInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(PurchaseContractActionNode.DOC_ACTION_APPROVE,
                                PurchaseContractActionNode.NODEINST_ACTION_APPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(PurchaseContractActionNode.DOC_ACTION_REJECT_APPROVE,
                                PurchaseContractActionNode.NODEINST_ACTION_REJECT_APPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(PurchaseContractActionNode.DOC_ACTION_DELIVERY_DONE,
                                PurchaseContractActionNode.NODEINST_ACTION_DELIVERY_DONE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(PurchaseContractActionNode.DOC_ACTION_SUBMIT,
                                PurchaseContractActionNode.NODEINST_ACTION_SUBMIT)
                )
        );
        // Doc item node: `PurchaseContractMaterialItem`
        docUIModelExtensionBuilder.itemModelClass(PurchaseContractMaterialItem.class).itemUiModelClass(PurchaseContractMaterialItemUIModel.class)
                .itemAttachmentClass(PurchaseContractMaterialItemAttachment.class)
                .convItemUIToMethod(PurchaseContractMaterialItemManager.METHOD_ConvUIToPurchaseContractMaterialItem).itemToParentDocMethod(PurchaseContractMaterialItemManager.METHOD_ConvParentDocToItemUI).
                convItemToUIMethod(PurchaseContractMaterialItemManager.METHOD_ConvPurchaseContractMaterialItemToUI).itemLogicManager(purchaseContractMaterialItemManager);
        return docUIModelExtensionBuilder;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return purchaseContractIdHelper;
    }

    @Override
    public ServiceModule createItemServiceModule(PurchaseContractServiceModel purchaseContractServiceModel)
            throws DocActionException {
        PurchaseContract purchaseContract = purchaseContractServiceModel.getPurchaseContract();
        PurchaseContractMaterialItem purchaseContractMaterialItem =
                createItem(purchaseContract);
        purchaseContractManager.initialCopyPurchaseContractToMaterialItem(
                purchaseContract, purchaseContractServiceModel.getPurchaseFromSupplier(), purchaseContractMaterialItem, 0);
        PurchaseContractMaterialItemServiceModel purchaseContractMaterialItemServiceModel = new PurchaseContractMaterialItemServiceModel();
        purchaseContractMaterialItemServiceModel
                .setPurchaseContractMaterialItem(purchaseContractMaterialItem);
        return purchaseContractMaterialItemServiceModel;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, PurchaseContractServiceModel purchaseContractServiceModel) {
        if(partyRole == PurchaseContractParty.ROLE_PARTYA){
            return purchaseContractServiceModel.getPurchaseToOrg();
        }
        if(partyRole == PurchaseContractParty.ROLE_PARTYB){
            return purchaseContractServiceModel.getPurchaseFromSupplier();
        }
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(PurchaseContractServiceModel purchaseContractServiceModel, DocInvolveParty docInvolveParty) {
        PurchaseContractParty purchaseContractParty = (PurchaseContractParty) docInvolveParty;
        if(purchaseContractParty.getPartyRole() == InquiryParty.ROLE_PARTYA){
            purchaseContractServiceModel.setPurchaseToOrg(purchaseContractParty);
        }
        if(purchaseContractParty.getPartyRole() == InquiryParty.ROLE_PARTYB){
            purchaseContractServiceModel.setPurchaseFromSupplier(purchaseContractParty);
        }
    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public List<?> getDocMatItemServiceModuleList(PurchaseContractServiceModel purchaseContractServiceModel) {
        return purchaseContractServiceModel.getPurchaseContractMaterialItemList();
    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return purchaseContractManager.initStatus(lanCode);
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return super.getInvolvePartyMapWrapper(lanCode, PurchaseContractUIModel.class, "PurchaseContract_InvolveParty");
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(PurchaseContract.class, PurchaseContractServiceModel.class, PurchaseContractActionNode.class,
                PurchaseContractMaterialItem.class, PurchaseContractMaterialItemServiceModel.class, null);
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(PurchaseContract.SENAME, PurchaseContractUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(PurchaseContractMaterialItem.NODENAME,
                PurchaseContractMaterialItemUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = ISupplyI18nPackage.class.getResource("").getPath();
        propertyMapList.addAll(DocumentContentSpecifier.getCorePropertyMap(PurchaseContract.SENAME, basePath +
                "PurchaseContract"));
        propertyMapList.addAll(DocumentContentSpecifier.getDocMatItemPropertyMap(PurchaseContractMaterialItem.NODENAME, basePath + "PurchaseContractMaterialItem"));
        propertyMapList.add(DocumentContentSpecifier.getActionNodePropertyMap("actionNode"));
        propertyMapList.addAll(DocumentContentSpecifier.getInvolvePartyPropertyMap("PurchaseToOrg", basePath +
                "PurchaseToOrg"));
        propertyMapList.addAll(DocumentContentSpecifier.getInvolvePartyPropertyMap("PurchaseFromSupplier", basePath +
                "PurchaseFromSupplier"));
        return propertyMapList;
    }

    @Override
    public void traverseMatItemNode(PurchaseContractServiceModel purchaseContractServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution<PurchaseContractMaterialItem> docItemActionCallback,
                                            SerialLogonInfo serialLogonInfo) throws DocActionException {
        if (!ServiceCollectionsHelper
                .checkNullList(purchaseContractServiceModel.getPurchaseContractMaterialItemList())) {
            for (PurchaseContractMaterialItemServiceModel purchaseContractMaterialItemServiceModel :
                    purchaseContractServiceModel
                            .getPurchaseContractMaterialItemList()) {
                if (docItemActionCallback != null) {
                    docItemActionCallback.execute(
                            purchaseContractMaterialItemServiceModel.getPurchaseContractMaterialItem(), new DocActionExecutionProxy.ItemSelectionExecutionContext());
                }
            }
        }
    }

}
