package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.service.ISupplyI18nPackage;
import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.model.Inquiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.DocUIModelExtensionBuilder;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.StandardSystemVariableProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceDocInitConfigureManager;
import com.company.IntelligentPlatform.common.model.Account;
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
public class InquirySpecifier extends DocumentContentSpecifier<InquiryServiceModel, Inquiry, InquiryMaterialItem> {

    @Autowired
    protected InquiryManager inquiryManager;

    @Autowired
    protected InquiryMaterialItemManager inquiryMaterialItemManager;

    @Autowired
    protected InquiryIdHelper inquiryIdHelper;

    private Map<String, Map<Integer, String>> involvePartyLan = new HashMap<>();

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_INQUIRY;
    }

    @Override
    public Integer getDocumentStatus(Inquiry inquiry) {
        return inquiry.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return inquiryManager;
    }

    @Override
    public Inquiry setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        Inquiry inquiry = (Inquiry) serviceEntityTargetStatus.getServiceEntityNode();
        inquiry.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return inquiry;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return inquiryIdHelper;
    }

    @Override
    public DocUIModelExtensionBuilder getDocUIModelExtensionBuilder() {
        // Doc root node: `Inquiry`
        DocUIModelExtensionBuilder docUIModelExtensionBuilder = docUIModelExtensionFactory.getBuilder(Inquiry.class, InquiryUIModel.class, getDocumentManager());
        docUIModelExtensionBuilder.convDocToUIMethod(InquiryManager.METHOD_ConvInquiryToUI).convDocUIToMethod(InquiryManager.METHOD_ConvUIToInquiry);
        // Root flat submodules: `InquiryAttachment`, `InquiryActionNode`, `InquiryParty`
        docUIModelExtensionBuilder.docAttachmentClass(InquiryAttachment.class).docActionClass(InquiryActionNode.class).docInvolvePartyClass(InquiryParty.class);
        docUIModelExtensionBuilder.docInvolvePartyBuilderInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(InquiryParty.ROLE_PARTYB,
                                InquiryParty.PARTY_NODEINST_PUR_SUPPLIER,
                                CorporateCustomer.class,
                                IndividualCustomer.class),
                        new DocUIModelExtensionBuilder.InvolvePartyBuilderInputPara(InquiryParty.ROLE_PARTYA,
                                InquiryParty.PARTY_NODEINST_PUR_ORG,
                                Organization.class,
                                Employee.class)
                )
        );
        docUIModelExtensionBuilder.docActionNodeBuildInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(InquiryActionNode.DOC_ACTION_APPROVE,
                                InquiryActionNode.NODEINST_ACTION_APPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(InquiryActionNode.DOC_ACTION_INPROCESS,
                                InquiryActionNode.NODEINST_ACTION_INPROCESS),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(InquiryActionNode.DOC_ACTION_SUBMIT,
                                InquiryActionNode.NODEINST_ACTION_SUBMIT)
                )
        );
        // Doc item node: `InquiryMaterialItem`
        docUIModelExtensionBuilder.itemModelClass(InquiryMaterialItem.class).itemUiModelClass(InquiryMaterialItemUIModel.class)
                .itemAttachmentClass(InquiryMaterialItemAttachment.class)
                .convItemUIToMethod(InquiryMaterialItemManager.METHOD_ConvUIToInquiryMaterialItem).itemToParentDocMethod(InquiryMaterialItemManager.METHOD_ConvParentDocToItemUI).
                convItemToUIMethod(InquiryMaterialItemManager.METHOD_ConvInquiryMaterialItemToUI).itemLogicManager(inquiryMaterialItemManager);
        return docUIModelExtensionBuilder;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, InquiryServiceModel inquiryServiceModel) {
        if(partyRole == InquiryParty.ROLE_PARTYA){
            return inquiryServiceModel.getPurchaseToOrg();
        }
        if(partyRole == InquiryParty.ROLE_PARTYB){
            return inquiryServiceModel.getPurchaseFromSupplier();
        }
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule serviceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(InquiryServiceModel inquiryServiceModel, DocInvolveParty docInvolveParty) {
        InquiryParty inquiryParty = (InquiryParty) docInvolveParty;
        if(inquiryParty.getPartyRole() == InquiryParty.ROLE_PARTYA){
            inquiryServiceModel.setPurchaseToOrg(inquiryParty);
        }
        if(inquiryParty.getPartyRole() == InquiryParty.ROLE_PARTYB){
            inquiryServiceModel.setPurchaseFromSupplier(inquiryParty);
        }
    }

    @Override
    public Map<String, List<ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta>> getDefAllInitConfigureMap() {
        Map<String, List<ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta>> initDocConfigureMap = new HashMap<>();
        initDocConfigureMap.put(ServiceEntityStringHelper.getDefModelId(Inquiry.SENAME, Inquiry.NODENAME),
                ServiceCollectionsHelper.asList(
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta("purchaseToOrg.partyRole",null, InquiryParty.ROLE_PARTYA),
                        ServiceDocInitConfigureManager.createMetaWithDiffFieldList("purchaseToOrg.*",
                                StandardSystemVariableProxy.varLogonOrganization(), ServiceCollectionsHelper.asList(
                                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureFieldMap("address",
                                                Account.FIELD_address),
                                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureFieldMap("telephone",
                                                Account.FIELD_telephone)
                        )),
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta("purchaseToOrg.refUUID",null, StandardSystemVariableProxy.varLogonOrganizationUUID()),
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta("purchaseToOrg.refContactUUID",null, StandardSystemVariableProxy.varLogonUserUUID()),
                        new ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta("purchaseFromSupplier.partyRole",null,
                                InquiryParty.ROLE_PARTYB)));
        return initDocConfigureMap;
    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public List<?> getDocMatItemServiceModuleList(InquiryServiceModel inquiryServiceModel) {
        return inquiryServiceModel.getInquiryMaterialItemList();
    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return inquiryManager.initStatus(lanCode);
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return super.getInvolvePartyMapWrapper(lanCode, InquiryUIModel.class, "Inquiry_InvolveParty");
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(Inquiry.class, InquiryServiceModel.class, InquiryActionNode.class,
                InquiryMaterialItem.class, InquiryMaterialItemServiceModel.class, null);
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(Inquiry.SENAME, InquiryUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(InquiryMaterialItem.NODENAME,
                InquiryMaterialItemUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = ISupplyI18nPackage.class.getResource("").getPath();
        propertyMapList.addAll(DocumentContentSpecifier.getCorePropertyMap(Inquiry.SENAME, basePath +
                "Inquiry"));
        propertyMapList.addAll(DocumentContentSpecifier.getDocMatItemPropertyMap(InquiryMaterialItem.NODENAME, basePath +
                "InquiryMaterialItem"));
        propertyMapList.add(DocumentContentSpecifier.getActionNodePropertyMap("actionNode"));
        propertyMapList.addAll(DocumentContentSpecifier.getInvolvePartyPropertyMap("PurchaseToOrg", basePath +
                "PurchaseToOrg"));
        propertyMapList.addAll(DocumentContentSpecifier.getInvolvePartyPropertyMap("PurchaseFromSupplier", basePath +
                "PurchaseFromSupplier"));
        return propertyMapList;
    }

    @Override
    public void traverseMatItemNode(InquiryServiceModel inquiryServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution<InquiryMaterialItem> docItemActionCallback,
                                            SerialLogonInfo serialLogonInfo) throws DocActionException {
        if (!ServiceCollectionsHelper
                .checkNullList(inquiryServiceModel.getInquiryMaterialItemList())) {
            for (InquiryMaterialItemServiceModel inquiryMaterialItemServiceModel :
                    inquiryServiceModel
                            .getInquiryMaterialItemList()) {
                if (docItemActionCallback != null) {
                    docItemActionCallback.execute(inquiryMaterialItemServiceModel.getInquiryMaterialItem(), new DocActionExecutionProxy.ItemSelectionExecutionContext());
                }
            }
        }
    }

}
