package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.dto.*;
import com.company.IntelligentPlatform.production.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.DocUIModelExtensionBuilder;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocInvolveParty;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BillOfMaterialTemplateSpecifier extends DocumentContentSpecifier<BillOfMaterialTemplateServiceModel,
        BillOfMaterialTemplate, BillOfMaterialTemplateItem> {

    @Autowired
    protected BillOfMaterialTemplateManager billOfMaterialTemplateManager;

    @Autowired
    protected BillOfMaterialTemplateItemManager billOfMaterialMaterialTemplateItemManager;

    @Autowired
    protected BillOfMaterialTemplateIdHelper billOfMaterialTemplateIdHelper;

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_BILLOFMATERIALTEMPLATE;
    }

    @Override
    public Integer getDocumentStatus(BillOfMaterialTemplate billOfMaterialTemplate) {
        return billOfMaterialTemplate.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return billOfMaterialTemplateManager;
    }

    @Override
    public BillOfMaterialTemplate setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        BillOfMaterialTemplate billOfMaterialTemplate = (BillOfMaterialTemplate) serviceEntityTargetStatus.getServiceEntityNode();
        billOfMaterialTemplate.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return billOfMaterialTemplate;
    }

    @Override
    public DocUIModelExtensionBuilder getDocUIModelExtensionBuilder() {
        // Doc root node: `BillOfMaterialTemplate`
        DocUIModelExtensionBuilder docUIModelExtensionBuilder = docUIModelExtensionFactory.getBuilder(BillOfMaterialTemplate.class, BillOfMaterialTemplateUIModel.class, getDocumentManager());
        docUIModelExtensionBuilder.convDocToUIMethod(BillOfMaterialTemplateManager.METHOD_ConvBillOfMaterialTemplateToUI).convDocUIToMethod(BillOfMaterialTemplateManager.METHOD_ConvUIToBillOfMaterialTemplate);
        // Root flat submodules: `BillOfMaterialTemplateAttachment`, `BillOfMaterialTemplateActionNode`, `BillOfMaterialTemplateParty`
        docUIModelExtensionBuilder.docAttachmentClass(BillOfMaterialAttachment.class).docActionClass(BillOfMaterialTemplateActionNode.class);
        docUIModelExtensionBuilder.docActionNodeBuildInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(BillOfMaterialTemplateActionNode.DOC_ACTION_APPROVE,
                                BillOfMaterialTemplateActionNode.NODEINST_ACTION_APPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(BillOfMaterialTemplateActionNode.DOC_ACTION_ACTIVE,
                                BillOfMaterialTemplateActionNode.NODEINST_ACTION_ACTIVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(BillOfMaterialTemplateActionNode.DOC_ACTION_REVOKE_SUBMIT,
                                BillOfMaterialTemplateActionNode.NODEINST_ACTION_REVOKE_SUBMIT),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(BillOfMaterialTemplateActionNode.DOC_ACTION_REINIT,
                                BillOfMaterialTemplateActionNode.NODEINST_ACTION_REINIT)
                )
        );
        // Doc item node: `BillOfMaterialTemplateItem`
        docUIModelExtensionBuilder.itemModelClass(BillOfMaterialTemplateItem.class).itemUiModelClass(BillOfMaterialTemplateItemUIModel.class)
                .convItemUIToMethod(BillOfMaterialTemplateItemManager.METHOD_ConvUIToBillOfMaterialTemplateItem).itemToParentDocMethod(BillOfMaterialTemplateItemManager.MEMTHOD_ConvParentBOMTemplateToUI).
                convItemToUIMethod(BillOfMaterialTemplateItemManager.METHOD_ConvBillOfMaterialTemplateItemToUI).itemLogicManager(billOfMaterialMaterialTemplateItemManager);
        return docUIModelExtensionBuilder;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return billOfMaterialTemplateIdHelper;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, BillOfMaterialTemplateServiceModel serviceModule) {
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule serviceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(BillOfMaterialTemplateServiceModel serviceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public List<?> getDocMatItemServiceModuleList(BillOfMaterialTemplateServiceModel billOfMaterialTemplateServiceModel) {
        return billOfMaterialTemplateServiceModel.getBillOfMaterialTemplateItemList();
    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return billOfMaterialTemplateManager.initStatusMap(lanCode);
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return null;
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(BillOfMaterialTemplate.class, BillOfMaterialTemplateServiceModel.class, BillOfMaterialTemplateActionNode.class,
                BillOfMaterialTemplateItem.class, BillOfMaterialTemplateItemServiceModel.class, null);
    }

    @Override
    public void traverseMatItemNode(BillOfMaterialTemplateServiceModel billOfMaterialTemplateServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution<BillOfMaterialTemplateItem> docItemActionCallback,
                                            SerialLogonInfo serialLogonInfo) throws DocActionException {
        if (!ServiceCollectionsHelper
                .checkNullList(billOfMaterialTemplateServiceModel.getBillOfMaterialTemplateItemList())) {
            for (BillOfMaterialTemplateItemServiceModel billOfMaterialTemplateItemServiceModel :
                    billOfMaterialTemplateServiceModel
                            .getBillOfMaterialTemplateItemList()) {
                if (docItemActionCallback != null) {
                    docItemActionCallback.execute(billOfMaterialTemplateItemServiceModel.getBillOfMaterialTemplateItem(), new DocActionExecutionProxy.ItemSelectionExecutionContext());
                }
            }
        }
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(BillOfMaterialTemplate.SENAME, BillOfMaterialTemplateUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(BillOfMaterialTemplateItem.NODENAME, BillOfMaterialTemplateItemUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        return null;
    }

}
