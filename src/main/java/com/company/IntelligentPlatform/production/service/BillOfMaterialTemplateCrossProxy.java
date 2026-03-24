package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceModuleCloneService;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for migration BOM Template to BOM Order
 */
@Service
public class BillOfMaterialTemplateCrossProxy {

    @Autowired
    protected BillOfMaterialOrderManager billOfMaterialOrderManager;

    @Autowired
    protected BillOfMaterialTemplateManager billOfMaterialTemplateManager;

    public BillOfMaterialOrderServiceModel cloneToBOMOrder(BillOfMaterialTemplateServiceModel billOfMaterialTemplateServiceModel) throws ServiceModuleProxyException, ServiceEntityConfigureException {
        /*
         * [Step1] Clone new template and preparing for conversion into BOM Order
         */
        ServiceModuleCloneService.ServiceModuleCloneResult serviceModuleCloneResult = ServiceModuleCloneService
                .deepCloneServiceModuleResult(billOfMaterialTemplateServiceModel, true);
        BillOfMaterialTemplateServiceModel newBOMTemplateServiceModel =
                (BillOfMaterialTemplateServiceModel) serviceModuleCloneResult.getServiceModule();
        List<ServiceModuleCloneService.UUIDUnion> uuidUnionList = serviceModuleCloneResult.getUuidUnionList();
        BillOfMaterialOrderServiceModel billOfMaterialOrderServiceModel = new BillOfMaterialOrderServiceModel();
        BillOfMaterialOrder billOfMaterialOrder =
                cloneToBOMOrder(newBOMTemplateServiceModel.getBillOfMaterialTemplate());
        billOfMaterialOrderServiceModel.setBillOfMaterialOrder(billOfMaterialOrder);
        // Set ref template UUID
        billOfMaterialOrder.setRefTemplateUUID(billOfMaterialTemplateServiceModel.getBillOfMaterialTemplate().getUuid());

        billOfMaterialOrderServiceModel.setSubmittedBy(cloneToBOMOrderActionNode(newBOMTemplateServiceModel.getSubmittedBy(), billOfMaterialOrder));
        billOfMaterialOrderServiceModel.setRevokeSubmittedBy(cloneToBOMOrderActionNode(newBOMTemplateServiceModel.getRevokeSubmittedBy(), billOfMaterialOrder));
        billOfMaterialOrderServiceModel.setApprovedBy(cloneToBOMOrderActionNode(newBOMTemplateServiceModel.getApprovedBy(), billOfMaterialOrder));
        //billOfMaterialOrderServiceModel.setCountApprovedBy(cloneToBOMOrderActionNode(newBOMTemplateServiceModel
        // .getCountApprovedBy(), billOfMaterialOrder));
        billOfMaterialOrderServiceModel.setRejectApproveBy(cloneToBOMOrderActionNode(newBOMTemplateServiceModel.getRejectApproveBy(), billOfMaterialOrder));
        if (!ServiceCollectionsHelper.checkNullList(newBOMTemplateServiceModel.getBillOfMaterialTemplateItemList())) {
            List<BillOfMaterialItemServiceModel> billOfMaterialItemList = new ArrayList<>();
            for (BillOfMaterialTemplateItemServiceModel billOfMaterialTemplateItemServiceModel :
                    newBOMTemplateServiceModel.getBillOfMaterialTemplateItemList()) {
                cloneToBOMItemServiceModel(billOfMaterialTemplateItemServiceModel, billOfMaterialItemList,
                        billOfMaterialOrder, uuidUnionList);
            }
            billOfMaterialOrderServiceModel.setBillOfMaterialItemList(billOfMaterialItemList);
        }
        if (!ServiceCollectionsHelper.checkNullList(newBOMTemplateServiceModel.getBillOfMaterialTemplateAttachmentList())) {
            List<ServiceEntityNode> billOfMaterialAttachmentList = new ArrayList<>();
            for (ServiceEntityNode serviceEntityNode :
                    newBOMTemplateServiceModel.getBillOfMaterialTemplateAttachmentList()) {
                BillOfMaterialTemplateAttachment billOfMaterialTemplateAttachment =
                        (BillOfMaterialTemplateAttachment) serviceEntityNode;
                copyOrderAttachmentToTemplateAttachment(billOfMaterialTemplateAttachment, billOfMaterialOrder,
                        billOfMaterialAttachmentList);
            }
            billOfMaterialOrderServiceModel.setBillOfMaterialAttachmentList(billOfMaterialAttachmentList);
        }
        return billOfMaterialOrderServiceModel;
    }

    /**
     * Copy one specific BOM Order information to existed BOM Template, all the sub modules of this BOM template will
     * be deleted firstly.
     *
     * @param billOfMaterialOrderServiceModel
     * @param billOfMaterialTemplateServiceModel
     * @return
     * @throws ServiceModuleProxyException
     * @throws ServiceEntityConfigureException
     */
    public BillOfMaterialTemplateServiceModel cloneToBOMTemplateServiceModel(BillOfMaterialOrderServiceModel billOfMaterialOrderServiceModel,
                                                                 BillOfMaterialTemplateServiceModel billOfMaterialTemplateServiceModel) throws ServiceModuleProxyException, ServiceEntityConfigureException {
        /*
         * [Step1] Clone new BOM Order with updated new UUID
         */
        ServiceModuleCloneService.ServiceModuleCloneResult serviceModuleCloneResult = ServiceModuleCloneService
                .deepCloneServiceModuleResult(billOfMaterialOrderServiceModel, true);
        BillOfMaterialOrderServiceModel newBOMOrderServiceModel =
                (BillOfMaterialOrderServiceModel) serviceModuleCloneResult.getServiceModule();
        List<ServiceModuleCloneService.UUIDUnion> uuidUnionList = serviceModuleCloneResult.getUuidUnionList();
        // Also need to adjust new BOM Order Service Model ref Parent UUID

        BillOfMaterialTemplate billOfMaterialTemplate = billOfMaterialTemplateServiceModel.getBillOfMaterialTemplate();
        BillOfMaterialOrder newBOMOrder = newBOMOrderServiceModel.getBillOfMaterialOrder();
        List<ServiceModuleCloneService.UUIDUnion> newUUIDUnionList =
                ServiceCollectionsHelper.asList(new ServiceModuleCloneService.UUIDUnion(newBOMOrder.getUuid(),
                        billOfMaterialTemplate.getUuid()));
        copyFromOrderToTemplate(newBOMOrder, billOfMaterialTemplate);
        // Delete current bom template item list
        billOfMaterialTemplateManager.admDeleteEntityByKey(billOfMaterialTemplate.getUuid(),
                IServiceEntityNodeFieldConstant.ROOTNODEUUID, BillOfMaterialTemplateItem.NODENAME);

        if (!ServiceCollectionsHelper.checkNullList(newBOMOrderServiceModel.getBillOfMaterialItemList())) {
            List<BillOfMaterialTemplateItemServiceModel> billOfMaterialTemplateItemList = new ArrayList<>();
            for (BillOfMaterialItemServiceModel billOfMaterialItemServiceModel :
                    newBOMOrderServiceModel.getBillOfMaterialItemList()) {
                // Manually update ref parent item uuid of billOfMaterialItem after clone
                BillOfMaterialItem billOfMaterialItem = billOfMaterialItemServiceModel.getBillOfMaterialItem();
                String newRefParentItemUUID =
                        ServiceModuleCloneService.findNewUUID(billOfMaterialItem.getRefParentItemUUID(), uuidUnionList);
                if (newRefParentItemUUID != null) {
                    billOfMaterialItem.setRefParentItemUUID(newRefParentItemUUID);
                }
                cloneToBOMTemplateItemServiceModel(billOfMaterialItemServiceModel, billOfMaterialTemplateItemList,
                        billOfMaterialTemplate, newUUIDUnionList);
            }
            billOfMaterialTemplateServiceModel.setBillOfMaterialTemplateItemList(billOfMaterialTemplateItemList);
        }
        if (!ServiceCollectionsHelper.checkNullList(newBOMOrderServiceModel.getBillOfMaterialAttachmentList())) {
            List<ServiceEntityNode> billOfMaterialTemplateAttachmentList = new ArrayList<>();
            for (ServiceEntityNode serviceEntityNode :
                    newBOMOrderServiceModel.getBillOfMaterialAttachmentList()) {
                copyTemplateAttachmentToOrderAttachment((BillOfMaterialAttachment)serviceEntityNode, billOfMaterialTemplate,
                        billOfMaterialTemplateAttachmentList);
            }
            billOfMaterialTemplateServiceModel.setBillOfMaterialTemplateAttachmentList(billOfMaterialTemplateAttachmentList);
        }
        return billOfMaterialTemplateServiceModel;
    }

    public void copyFromTemplateToOrder(BillOfMaterialTemplate billOfMaterialTemplate,
                                        BillOfMaterialOrder billOfMaterialOrder) {
        if (billOfMaterialOrder == null || billOfMaterialTemplate == null) {
            return;
        }
        DocFlowProxy.copyDocumentContentMutual(billOfMaterialTemplate, billOfMaterialOrder, true);
        billOfMaterialOrder.setServiceEntityName(BillOfMaterialOrder.SENAME);
        billOfMaterialOrder.setRefMaterialSKUUUID(billOfMaterialTemplate.getRefMaterialSKUUUID());
        billOfMaterialOrder.setAmount(billOfMaterialTemplate.getAmount());
        billOfMaterialOrder.setRefUnitUUID(billOfMaterialTemplate.getRefUnitUUID());
        billOfMaterialOrder.setRefRouteOrderUUID(billOfMaterialTemplate.getRefRouteOrderUUID());
        billOfMaterialOrder.setRefWocUUID(billOfMaterialTemplate.getRefWocUUID());
        billOfMaterialOrder.setLeadTimeCalMode(billOfMaterialTemplate.getLeadTimeCalMode());
        billOfMaterialOrder.setPatchNumber(billOfMaterialTemplate.getPatchNumber());
        billOfMaterialOrder.setVersionNumber(billOfMaterialTemplate.getVersionNumber());
        billOfMaterialOrder.setDocumentCategoryType(billOfMaterialTemplate.getDocumentCategoryType());
    }

    public void copyFromOrderToTemplate(BillOfMaterialOrder billOfMaterialOrder,
                                        BillOfMaterialTemplate billOfMaterialTemplate) {
        if (billOfMaterialOrder == null || billOfMaterialTemplate == null) {
            return;
        }
        // Keep template status
        int curStatus = billOfMaterialTemplate.getStatus();
        DocFlowProxy.copyDocumentContentMutual(billOfMaterialOrder, billOfMaterialTemplate, false);
        billOfMaterialTemplate.setRefMaterialSKUUUID(billOfMaterialOrder.getRefMaterialSKUUUID());
        billOfMaterialTemplate.setAmount(billOfMaterialOrder.getAmount());
        billOfMaterialTemplate.setRefUnitUUID(billOfMaterialOrder.getRefUnitUUID());
        billOfMaterialTemplate.setRefRouteOrderUUID(billOfMaterialOrder.getRefRouteOrderUUID());
        billOfMaterialTemplate.setRefWocUUID(billOfMaterialOrder.getRefWocUUID());
        billOfMaterialTemplate.setLeadTimeCalMode(billOfMaterialOrder.getLeadTimeCalMode());
        // Keep template status
        billOfMaterialTemplate.setStatus(curStatus);
        // don't copy version information
//        billOfMaterialTemplate.setPatchNumber(billOfMaterialOrder.getPatchNumber());
//        billOfMaterialTemplate.setVersionNumber(billOfMaterialOrder.getVersionNumber());
        billOfMaterialTemplate.setDocumentCategoryType(billOfMaterialOrder.getDocumentCategoryType());
    }

    public void copyFromTemplateItemToOrderItem(BillOfMaterialTemplateItem billOfMaterialTemplateItem,
                                                BillOfMaterialItem billOfMaterialItem) {
        if (billOfMaterialTemplateItem == null || billOfMaterialItem == null) {
            return;
        }
        DocFlowProxy.copyDocMatItemMutual(billOfMaterialTemplateItem, billOfMaterialItem, true);
        billOfMaterialItem.setId(billOfMaterialTemplateItem.getId());
        billOfMaterialItem.setName(billOfMaterialTemplateItem.getName());
        billOfMaterialItem.setLayer(billOfMaterialTemplateItem.getLayer());
        billOfMaterialItem.setItemCategory(billOfMaterialTemplateItem.getItemCategory());
        billOfMaterialItem.setRefSubBOMUUID(billOfMaterialTemplateItem.getRefSubBOMUUID());
        billOfMaterialItem.setRefRouteProcessItemUUID(billOfMaterialTemplateItem.getRefRouteProcessItemUUID());
        billOfMaterialItem.setRefParentItemUUID(billOfMaterialTemplateItem.getRefParentItemUUID());
        billOfMaterialItem.setLeadTimeOffset(billOfMaterialTemplateItem.getLeadTimeOffset());
        billOfMaterialItem.setRefRouteProcessItemUUID(billOfMaterialTemplateItem.getRefRouteProcessItemUUID());
        billOfMaterialItem.setTheoLossRate(billOfMaterialTemplateItem.getTheoLossRate());
        billOfMaterialItem.setRefSubBOMUUID(billOfMaterialTemplateItem.getRefSubBOMUUID());
        billOfMaterialItem.setRefWocUUID(billOfMaterialTemplateItem.getRefWocUUID());
        billOfMaterialItem.setNodeName(BillOfMaterialItem.NODENAME);
        billOfMaterialItem.setServiceEntityName(BillOfMaterialItem.SENAME);
        billOfMaterialItem.setHomeDocumentType(IDefDocumentResource.DOCUMENT_TYPE_BILLOFMATERIALORDER);
    }

    public void copyFromOrderItemToTemplateItem(BillOfMaterialItem billOfMaterialItem,
                                                BillOfMaterialTemplateItem billOfMaterialTemplateItem) {
        if (billOfMaterialTemplateItem == null || billOfMaterialItem == null) {
            return;
        }
        DocFlowProxy.copyDocMatItemMutual(billOfMaterialItem, billOfMaterialTemplateItem, false);
        billOfMaterialTemplateItem.setId(billOfMaterialItem.getId());
        billOfMaterialTemplateItem.setName(billOfMaterialItem.getName());
        billOfMaterialTemplateItem.setLayer(billOfMaterialItem.getLayer());
        billOfMaterialTemplateItem.setItemCategory(billOfMaterialItem.getItemCategory());
        billOfMaterialTemplateItem.setRefSubBOMUUID(billOfMaterialItem.getRefSubBOMUUID());
        billOfMaterialTemplateItem.setRefRouteProcessItemUUID(billOfMaterialItem.getRefRouteProcessItemUUID());
        // important: also need to copy uuid from origin item to
        billOfMaterialTemplateItem.setUuid(billOfMaterialItem.getUuid());
        billOfMaterialTemplateItem.setRefParentItemUUID(billOfMaterialItem.getRefParentItemUUID());
        billOfMaterialTemplateItem.setLeadTimeOffset(billOfMaterialItem.getLeadTimeOffset());
        billOfMaterialTemplateItem.setRefRouteProcessItemUUID(billOfMaterialItem.getRefRouteProcessItemUUID());
        billOfMaterialTemplateItem.setTheoLossRate(billOfMaterialItem.getTheoLossRate());
        billOfMaterialTemplateItem.setRefSubBOMUUID(billOfMaterialItem.getRefSubBOMUUID());
        billOfMaterialTemplateItem.setRefWocUUID(billOfMaterialItem.getRefWocUUID());
    }

    private BillOfMaterialOrderActionNode cloneToBOMOrderActionNode(BillOfMaterialTemplateActionNode billOfMaterialTemplateActionNode, BillOfMaterialOrder billOfMaterialOrder) throws ServiceEntityConfigureException {
        if (billOfMaterialTemplateActionNode == null) {
            return null;
        }
        BillOfMaterialOrderActionNode billOfMaterialOrderActionNode =
                (BillOfMaterialOrderActionNode) billOfMaterialOrderManager.newEntityNode(billOfMaterialOrder,
                        BillOfMaterialOrderActionNode.NODENAME);
        billOfMaterialOrderActionNode.setDocActionCode(billOfMaterialTemplateActionNode.getDocActionCode());
        billOfMaterialOrderActionNode.setExecutedByUUID(billOfMaterialTemplateActionNode.getExecutedByUUID());
        billOfMaterialOrderActionNode.setExecutionTime(billOfMaterialTemplateActionNode.getExecutionTime());
        billOfMaterialOrderActionNode.setFlatNodeSwitch(billOfMaterialTemplateActionNode.getFlatNodeSwitch());
        billOfMaterialOrderActionNode.setProcessIndex(billOfMaterialTemplateActionNode.getProcessIndex());
        billOfMaterialOrderActionNode.setServiceEntityName(BillOfMaterialOrderActionNode.SENAME);
        billOfMaterialOrderActionNode.setNodeName(BillOfMaterialOrderActionNode.NODENAME);
        return billOfMaterialTemplateActionNode;
    }


    private BillOfMaterialOrder cloneToBOMOrder(BillOfMaterialTemplate billOfMaterialTemplate) throws ServiceEntityConfigureException {
        if (billOfMaterialTemplate == null) {
            return null;
        }
        billOfMaterialTemplate.setServiceEntityName(BillOfMaterialOrder.SENAME);
        billOfMaterialTemplate.setNodeName(BillOfMaterialOrder.NODENAME);
        BillOfMaterialOrder billOfMaterialOrder =
                (BillOfMaterialOrder) billOfMaterialOrderManager.newRootEntityNode(billOfMaterialTemplate.getClient());
        copyFromTemplateToOrder(billOfMaterialTemplate, billOfMaterialOrder);
        return billOfMaterialOrder;
    }

    private void cloneToBOMItemServiceModel(BillOfMaterialTemplateItemServiceModel billOfMaterialTemplateItemServiceModel,
                                            List<BillOfMaterialItemServiceModel> billOfMaterialItemList,
                                            BillOfMaterialOrder billOfMaterialOrder,
                                            List<ServiceModuleCloneService.UUIDUnion> uuidUnionList) throws ServiceEntityConfigureException {
        if (billOfMaterialTemplateItemServiceModel == null) {
            return;
        }
        BillOfMaterialItemServiceModel billOfMaterialItemServiceModel = new BillOfMaterialItemServiceModel();
        if (billOfMaterialTemplateItemServiceModel.getBillOfMaterialTemplateItem() != null) {
            BillOfMaterialTemplateItem billOfMaterialTemplateItem =
                    billOfMaterialTemplateItemServiceModel.getBillOfMaterialTemplateItem();
            billOfMaterialTemplateItem.setNodeName(BillOfMaterialItem.NODENAME);
            billOfMaterialTemplateItem.setServiceEntityName(BillOfMaterialItem.SENAME);
            String newRefParentItemUUID =
                    ServiceModuleCloneService.findNewUUID(billOfMaterialTemplateItem.getRefParentItemUUID(),
                            uuidUnionList);
            billOfMaterialTemplateItem.setRefParentItemUUID(newRefParentItemUUID);
            BillOfMaterialItem billOfMaterialItem =
                    (BillOfMaterialItem) billOfMaterialOrderManager.newEntityNode(billOfMaterialOrder,
                            BillOfMaterialItem.NODENAME);
            copyFromTemplateItemToOrderItem(billOfMaterialTemplateItem, billOfMaterialItem);
            billOfMaterialItemServiceModel.setBillOfMaterialItem(billOfMaterialItem);
            billOfMaterialItemList.add(billOfMaterialItemServiceModel);
        }
    }


    private void cloneToBOMTemplateItemServiceModel(BillOfMaterialItemServiceModel billOfMaterialItemServiceModel,
                                                    List<BillOfMaterialTemplateItemServiceModel> billOfMaterialTemplateItemList,
                                                    BillOfMaterialTemplate billOfMaterialTemplate,
                                                    List<ServiceModuleCloneService.UUIDUnion> uuidUnionList) throws ServiceEntityConfigureException {
        if (billOfMaterialItemServiceModel == null) {
            return;
        }
        BillOfMaterialTemplateItemServiceModel billOfMaterialTemplateItemServiceModel =
                new BillOfMaterialTemplateItemServiceModel();
        if (billOfMaterialItemServiceModel.getBillOfMaterialItem() != null) {
            BillOfMaterialItem billOfMaterialItem =
                    billOfMaterialItemServiceModel.getBillOfMaterialItem();
            String newRefParentItemUUID =
                    ServiceModuleCloneService.findNewUUID(billOfMaterialItem.getRefParentItemUUID(), uuidUnionList);
            if (newRefParentItemUUID != null) {
                billOfMaterialItem.setRefParentItemUUID(newRefParentItemUUID);
            }
            BillOfMaterialTemplateItem billOfMaterialTemplateItem =
                    (BillOfMaterialTemplateItem) billOfMaterialTemplateManager.newEntityNode(billOfMaterialTemplate,
                            BillOfMaterialTemplateItem.NODENAME);
            copyFromOrderItemToTemplateItem(billOfMaterialItem, billOfMaterialTemplateItem);

            billOfMaterialTemplateItemServiceModel.setBillOfMaterialTemplateItem(billOfMaterialTemplateItem);
            billOfMaterialTemplateItemList.add(billOfMaterialTemplateItemServiceModel);
        }
    }

    public BillOfMaterialTemplateAttachment copyTemplateAttachmentToOrderAttachment(BillOfMaterialAttachment billOfMaterialAttachment, BillOfMaterialTemplate billOfMaterialTemplate, List<ServiceEntityNode> billOfMaterialTemplateAttachmentList) throws ServiceEntityConfigureException {
        BillOfMaterialTemplateAttachment billOfMaterialTemplateAttachment =
                (BillOfMaterialTemplateAttachment) billOfMaterialTemplateManager.newEntityNode(billOfMaterialTemplate,
                        BillOfMaterialTemplateAttachment.NODENAME);
        billOfMaterialTemplateAttachment.setNodeName(BillOfMaterialTemplateAttachment.NODENAME);
        billOfMaterialTemplateAttachment.setServiceEntityName(BillOfMaterialTemplateAttachment.SENAME);
        billOfMaterialTemplateAttachment.setFileType(billOfMaterialAttachment.getFileType());
        billOfMaterialTemplateAttachment.setContent(billOfMaterialAttachment.getContent());
        billOfMaterialTemplateAttachmentList.add(billOfMaterialTemplateAttachment);
        return billOfMaterialTemplateAttachment;
    }


    public BillOfMaterialAttachment copyOrderAttachmentToTemplateAttachment(
            BillOfMaterialTemplateAttachment billOfMaterialTemplateAttachment, BillOfMaterialOrder billOfMaterialOrder,
            List<ServiceEntityNode> billOfMaterialAttachmentList) throws ServiceEntityConfigureException {
        BillOfMaterialAttachment billOfMaterialAttachment =
                (BillOfMaterialAttachment) billOfMaterialOrderManager.newEntityNode(billOfMaterialOrder,
                        BillOfMaterialAttachment.NODENAME);
        billOfMaterialAttachment.setNodeName(BillOfMaterialAttachment.NODENAME);
        billOfMaterialAttachment.setServiceEntityName(BillOfMaterialAttachment.SENAME);
        billOfMaterialAttachment.setFileType(billOfMaterialTemplateAttachment.getFileType());
        billOfMaterialAttachment.setContent(billOfMaterialTemplateAttachment.getContent());
        billOfMaterialAttachmentList.add(billOfMaterialAttachment);
        return billOfMaterialAttachment;
    }

}
