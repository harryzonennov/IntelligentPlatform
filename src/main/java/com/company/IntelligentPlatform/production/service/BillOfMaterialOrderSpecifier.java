package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.dto.BillOfMaterialItemServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialItemUIModel;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialOrderServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialOrderUIModel;
import com.company.IntelligentPlatform.production.model.BillOfMaterialAttachment;
import com.company.IntelligentPlatform.production.model.BillOfMaterialOrder;
import com.company.IntelligentPlatform.production.model.BillOfMaterialOrderActionNode;
import com.company.IntelligentPlatform.production.model.BillOfMaterialItem;
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
public class BillOfMaterialOrderSpecifier extends DocumentContentSpecifier<BillOfMaterialOrderServiceModel,
        BillOfMaterialOrder, BillOfMaterialItem> {

    @Autowired
    protected BillOfMaterialOrderServiceUIModelExtension billOfMaterialOrderServiceUIModelExtension;

    @Autowired
    protected BillOfMaterialItemServiceUIModelExtension billOfMaterialItemServiceUIModelExtension;

    @Autowired
    protected BillOfMaterialOrderManager billOfMaterialOrderManager;

    @Autowired
    protected BillOfMaterialItemManager billOfMaterialMaterialItemManager;

    @Autowired
    protected BillOfMaterialOrderIdHelper billOfMaterialOrderIdHelper;

    @Override
    public int getDocumentType() {
        return IDefDocumentResource.DOCUMENT_TYPE_BILLOFMATERIALORDER;
    }

    @Override
    public Integer getDocumentStatus(BillOfMaterialOrder billOfMaterialOrder) {
        return billOfMaterialOrder.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return billOfMaterialOrderManager;
    }

    @Override
    public BillOfMaterialOrder setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        BillOfMaterialOrder billOfMaterialOrder = (BillOfMaterialOrder) serviceEntityTargetStatus.getServiceEntityNode();
        billOfMaterialOrder.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return billOfMaterialOrder;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return billOfMaterialOrderIdHelper;
    }

    @Override
    public DocUIModelExtensionBuilder getDocUIModelExtensionBuilder() {
        // Doc root node: `BillOfMaterialOrder`
        DocUIModelExtensionBuilder docUIModelExtensionBuilder = docUIModelExtensionFactory.getBuilder(BillOfMaterialOrder.class, BillOfMaterialOrderUIModel.class, getDocumentManager());
        docUIModelExtensionBuilder.convDocToUIMethod(BillOfMaterialOrderManager.METHOD_ConvBillOfMaterialOrderToUI).convDocUIToMethod(BillOfMaterialOrderManager.METHOD_ConvUIToBillOfMaterialOrder);
        // Root flat submodules: `BillOfMaterialOrderAttachment`, `BillOfMaterialOrderActionNode`, `BillOfMaterialOrderParty`
        docUIModelExtensionBuilder.docAttachmentClass(BillOfMaterialAttachment.class).docActionClass(BillOfMaterialOrderActionNode.class);
        docUIModelExtensionBuilder.docActionNodeBuildInputParaList(
                ServiceCollectionsHelper.asList(
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(BillOfMaterialOrderActionNode.DOC_ACTION_APPROVE,
                                BillOfMaterialOrderActionNode.NODEINST_ACTION_APPROVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(BillOfMaterialOrderActionNode.DOC_ACTION_ACTIVE,
                                BillOfMaterialOrderActionNode.NODEINST_ACTION_ACTIVE),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(BillOfMaterialOrderActionNode.DOC_ACTION_REVOKE_SUBMIT,
                                BillOfMaterialOrderActionNode.NODEINST_ACTION_REVOKE_SUBMIT),
                        new DocUIModelExtensionBuilder.DocActionNodeBuilderInputPara(BillOfMaterialOrderActionNode.DOC_ACTION_REINIT,
                                BillOfMaterialOrderActionNode.NODEINST_ACTION_REINIT)
                )
        );
        // Doc item node: `BillOfMaterialItem`
        docUIModelExtensionBuilder.itemModelClass(BillOfMaterialItem.class).itemUiModelClass(BillOfMaterialItemUIModel.class)
                .convItemUIToMethod(BillOfMaterialItemManager.METHOD_ConvUIToBillOfMaterialItem).itemToParentDocMethod(BillOfMaterialItemManager.MEMTHOD_ConvParentBOMOrderToUI).
                convItemToUIMethod(BillOfMaterialItemManager.METHOD_ConvBillOfMaterialItemToUI).itemLogicManager(billOfMaterialMaterialItemManager);
        return docUIModelExtensionBuilder;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, BillOfMaterialOrderServiceModel serviceModule) {
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule serviceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(BillOfMaterialOrderServiceModel serviceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return billOfMaterialOrderManager.initStatusMap(lanCode);
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return null;
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(BillOfMaterialOrder.class, BillOfMaterialOrderServiceModel.class, BillOfMaterialOrderActionNode.class,
                BillOfMaterialItem.class, BillOfMaterialItemServiceModel.class, null);
    }

    //TODO - to remove in the future
    @Override
    public ServiceUIModelExtension getDocServiceUIModelExtension() {
        return billOfMaterialOrderServiceUIModelExtension;
    }

    @Override
    public void traverseMatItemNode(BillOfMaterialOrderServiceModel billOfMaterialOrderServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution<BillOfMaterialItem> docItemActionCallback,
                                            SerialLogonInfo serialLogonInfo) throws DocActionException {
        if (!ServiceCollectionsHelper
                .checkNullList(billOfMaterialOrderServiceModel.getBillOfMaterialItemList())) {
            for (BillOfMaterialItemServiceModel billOfMaterialOrderItemServiceModel :
                    billOfMaterialOrderServiceModel
                            .getBillOfMaterialItemList()) {
                if (docItemActionCallback != null) {
                    docItemActionCallback.execute(billOfMaterialOrderItemServiceModel.getBillOfMaterialItem(), new DocActionExecutionProxy.ItemSelectionExecutionContext());
                }
            }
        }
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(BillOfMaterialOrder.SENAME, BillOfMaterialOrderUIModel.class));
        uiModelClassMap.add(new UIModelClassMap(BillOfMaterialItem.NODENAME, BillOfMaterialItemUIModel.class));
        return uiModelClassMap;
    }

    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        return null;
    }

}
