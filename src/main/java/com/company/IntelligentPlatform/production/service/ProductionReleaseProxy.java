package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.company.IntelligentPlatform.logistics.dto.PurchaseContractServiceUIModelExtension;
import com.company.IntelligentPlatform.logistics.service.*;
import com.company.IntelligentPlatform.logistics.service.PurchaseContractIdHelper;
import com.company.IntelligentPlatform.logistics.service.PurchaseContractManager;
import com.company.IntelligentPlatform.logistics.service.PurchaseContractMaterialItemServiceModel;
import com.company.IntelligentPlatform.logistics.service.PurchaseContractServiceModel;
import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.production.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MatDecisionValueSettingManager;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MatDecisionValueSetting;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxy;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityDateHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Compound Proxy for Release Production Plan / order to relative sub resources,
 * as well as generating the picking order resources.
 *
 * @author Zhang, Hang
 */
@Service
public class ProductionReleaseProxy {

    @Autowired
    protected ProductionPlanManager productionPlanManager;

    @Autowired
    protected ProductionOrderManager productionOrderManager;

    @Autowired
    protected ProdPickingOrderManager prodPickingOrderManager;

    @Autowired
    protected ProdOrderTargetMatItemManager prodOrderTargetMatItemManager;

    @Autowired
    protected PurchaseContractManager purchaseContractManager;

    @Autowired
    protected BillOfMaterialOrderManager billOfMaterialOrderManager;

    @Autowired
    protected OutboundDeliveryManager outboundDeliveryManager;

    @Autowired
    protected OutboundDeliveryIdHelper outboundDeliveryIdHelper;

    @Autowired
    protected InboundDeliveryManager inboundDeliveryManager;

    @Autowired
    protected ProductionDocRequestService productionDocRequestService;

    @Autowired
    protected InboundDeliveryIdHelper inboundDeliveryIdHelper;

    @Autowired
    protected PurchaseContractIdHelper purchaseContractIdHelper;

    @Autowired
    protected ProductionOrderIdHelper productionOrderIdHelper;

    @Autowired
    protected ProdPickingOrderIdHelper prodPickingOrderIdHelper;

    @Autowired
    protected ProductionOrderItemManager productionOrderItemManager;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected MatDecisionValueSettingManager matDecisionValueSettingManager;

    @Autowired
    protected PurchaseContractServiceUIModelExtension purchaseContractServiceUIModelExtension;

    @Autowired
    protected ProductionOrderActionExecutionProxy productionOrderActionExecutionProxy;

    @Autowired
    protected ServiceModuleProxy serviceModuleProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    final Logger logger = LoggerFactory.getLogger(ProductionReleaseProxy.class);

    protected int prodOrderIndex = 0;

    protected int pickingOrderIndex = 0;

    protected int outboundIndex = 0;

    protected int inboundIndex = 0;

    protected int purchaseIndex = 0;
    @Autowired
    private LogonInfoManager logonInfoManager;

    /**
     * Logic to generate default production order name
     *
     * @param materialStockKeepUnit
     * @return
     */
    public String genDefaultOrderName(MaterialStockKeepUnit materialStockKeepUnit) {
        String orderName = ServiceEntityStringHelper.EMPTYSTRING;
        if (materialStockKeepUnit != null) {
            orderName = materialStockKeepUnit.getName();
        }
        orderName = orderName + productionOrderIdHelper.genDefaultTimeStamp();
        return orderName;
    }

    /**
     * Generate Main production order from Plan, copying the necessary
     * information, re-build special logic for lead time.
     *
     * @param productionPlanServiceModel
     * @return
     * @throws ServiceEntityConfigureException
     */
    public ProductionOrder generateProductionOrderFromPlan(
            ProductionPlanServiceModel productionPlanServiceModel)
            throws ServiceEntityConfigureException {
        /*
         * [Step1] New instance of production order,copying necessary
         * information
         */
        ProductionPlan productionPlan = productionPlanServiceModel
                .getProductionPlan();
        ProductionOrder productionOrder = (ProductionOrder) productionOrderManager
                .newRootEntityNode(productionPlan.getClient());
        productionOrder.setRefPlanUUID(productionPlan.getUuid());
        productionOrder.setId(productionOrderIdHelper.genDefaultId(
                productionPlan.getClient(), this.prodOrderIndex++));
        productionOrder.setProductionBatchNumber(productionPlan
                .getProductionBatchNumber());
        MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
                .getEntityNodeByKey(productionPlan.getRefMaterialSKUUUID(),
                        IServiceEntityNodeFieldConstant.UUID,
                        MaterialStockKeepUnit.NODENAME,
                        productionPlan.getClient(), null);
        productionOrder.setName(genDefaultOrderName(materialStockKeepUnit));
        productionOrder.setRefMaterialSKUUUID(productionPlan
                .getRefMaterialSKUUUID());
        productionOrder.setRefBillOfMaterialUUID(productionPlan
                .getRefBillOfMaterialUUID());
        productionOrder.setAmount(productionPlan.getAmount());
        productionOrder.setRefUnitUUID(productionPlan.getRefUnitUUID());
        /*
         * [Step2] Logic to calculate the com lead time for production order
         */
        double selfLeadTime = productionPlan.getSelfLeadTime();
        double maxLeadTime = this
                ._getMaxSelfLeadTime(productionPlanServiceModel
                        .getProductionPlanItemList());
        productionOrder.setSelfLeadTime(selfLeadTime);
        productionOrder.setComLeadTime(selfLeadTime + maxLeadTime);
        /*
         * [Step3] Deduce the plan start time and end time, and prepare time
         */
        java.time.LocalDateTime planStartDate = productionPlan.getPlanStartTime();
        java.time.LocalDateTime planEndDate = ServiceEntityDateHelper.adjustDays(planStartDate,
                selfLeadTime);
        java.time.LocalDateTime planStartPrepareDate = ServiceEntityDateHelper.adjustDays(
                planStartDate, -maxLeadTime);
        productionOrder.setPlanStartTime(planStartDate);
        productionOrder.setPlanEndTime(planEndDate);
        productionOrder.setPlanStartPrepareDate(planStartPrepareDate);
        productionOrder.setRefPlanUUID(productionPlan.getUuid());
        /*
         * [Step4] Should decide default WOC assigned for this order
         */
        BillOfMaterialOrder billOfMaterialOrder = (BillOfMaterialOrder) billOfMaterialOrderManager
                .getEntityNodeByKey(productionPlan.getRefBillOfMaterialUUID(),
                        IServiceEntityNodeFieldConstant.UUID,
                        BillOfMaterialOrder.NODENAME, null);
        if (billOfMaterialOrder != null) {
            productionOrder.setRefWocUUID(billOfMaterialOrder.getRefWocUUID());
        }
        return productionOrder;
    }

    public List<ServiceEntityNode> batchCopyWarehouse(
            ProductionOrder productionOrder,
            List<ServiceEntityNode> prodPlanSupplyWarehouseList)
            throws ServiceEntityConfigureException {
        if (!ServiceCollectionsHelper
                .checkNullList(prodPlanSupplyWarehouseList)) {
            List<ServiceEntityNode> resultList = new ArrayList<>();
            for (ServiceEntityNode seNode : prodPlanSupplyWarehouseList) {
                ProdPlanSupplyWarehouse prodPlanSupplyWarehouse = (ProdPlanSupplyWarehouse) seNode;
                ProdOrderSupplyWarehouse prodOrderSupplyWarehouse = (ProdOrderSupplyWarehouse) productionOrderManager
                        .newEntityNode(productionOrder,
                                ProdOrderSupplyWarehouse.NODENAME);
                prodOrderSupplyWarehouse.setRefUUID(prodPlanSupplyWarehouse
                        .getRefUUID());
                prodOrderSupplyWarehouse.setRefNodeName(prodPlanSupplyWarehouse
                        .getRefNodeName());
                prodOrderSupplyWarehouse
                        .setRefPackageName(prodPlanSupplyWarehouse
                                .getRefPackageName());
                prodOrderSupplyWarehouse.setRefSEName(prodPlanSupplyWarehouse
                        .getRefPackageName());
                resultList.add(prodOrderSupplyWarehouse);
            }
            return resultList;
        }
        return null;
    }

    /**
     * [Internal method] Generate Production Order instance from Plan Proposal
     *
     * @param prodPlanItemReqProposalServiceModel
     * @return
     * @throws ServiceEntityConfigureException
     */
    private ProductionOrder genUnionProductionOrderFromPlanProposal(
            ProdPlanItemReqProposalServiceModel prodPlanItemReqProposalServiceModel)
            throws ServiceEntityConfigureException {
        ProdPlanItemReqProposal prodPlanItemReqProposal = prodPlanItemReqProposalServiceModel
                .getProdPlanItemReqProposal();
        /*
         * [Step1] pure
         */
        if (prodPlanItemReqProposal.getDocumentType() != IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER) {
            return null;
        }
        ProductionOrder productionOrder = (ProductionOrder) productionOrderManager
                .newRootEntityNode(prodPlanItemReqProposal.getClient());
        productionOrder.setId(productionOrderIdHelper.genDefaultId(
                prodPlanItemReqProposal.getClient(), this.prodOrderIndex++));
        MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
                .getEntityNodeByKey(
                        prodPlanItemReqProposal.getRefMaterialSKUUUID(),
                        IServiceEntityNodeFieldConstant.UUID,
                        MaterialStockKeepUnit.NODENAME,
                        prodPlanItemReqProposal.getClient(), null);
        productionOrder.setName(genDefaultOrderName(materialStockKeepUnit));
        productionOrder.setRefMaterialSKUUUID(prodPlanItemReqProposal
                .getRefMaterialSKUUUID());
        productionOrder.setProductionBatchNumber(prodPlanItemReqProposal
                .getProductionBatchNumber());
        productionOrder.setRefBillOfMaterialUUID(prodPlanItemReqProposal
                .getRefBOMItemUUID());
        productionOrder.setAmount(prodPlanItemReqProposal.getAmount());
        productionOrder
                .setRefUnitUUID(prodPlanItemReqProposal.getRefUnitUUID());
        productionOrder.setProductionBatchNumber(prodPlanItemReqProposal
                .getProductionBatchNumber());
        prodPlanItemReqProposal.setRefUUID(productionOrder.getUuid());
        prodPlanItemReqProposal.setItemStatus(ProductionPlanItem.STATUS_INPROCESS);
        double selfLeadTime = prodPlanItemReqProposal.getSelfLeadTime();
        double maxLeadTime = this
                ._getMaxSelfLeadTime(prodPlanItemReqProposalServiceModel
                        .getSubProductionPlanItemList());
        productionOrder.setSelfLeadTime(selfLeadTime);
        productionOrder.setComLeadTime(selfLeadTime + maxLeadTime);
        java.time.LocalDateTime planStartDate = prodPlanItemReqProposal.getPlanStartDate();
        java.time.LocalDateTime planEndDate = ServiceEntityDateHelper.adjustDays(planStartDate,
                selfLeadTime);
        java.time.LocalDateTime planStartPrepareDate = ServiceEntityDateHelper.adjustDays(
                planStartDate, -maxLeadTime);
        productionOrder.setPlanStartTime(prodPlanItemReqProposal
                .getPlanStartDate());
        productionOrder.setPlanEndTime(planEndDate);
        productionOrder.setPlanStartPrepareDate(planStartPrepareDate);

        /*
         * [Step2] Should decide default WOC assigned for this order
         */
        BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) billOfMaterialOrderManager
                .getEntityNodeByKey(
                        prodPlanItemReqProposal.getRefBOMItemUUID(),
                        IServiceEntityNodeFieldConstant.UUID,
                        BillOfMaterialItem.NODENAME, null);
        if (billOfMaterialItem != null) {
            // firstly set value from BOM item
            productionOrder.setRefBillOfMaterialUUID(billOfMaterialItem
                    .getUuid());
            productionOrder.setRefWocUUID(billOfMaterialItem.getRefWocUUID());
            if (!ServiceEntityStringHelper.checkNullString(billOfMaterialItem
                    .getRefSubBOMUUID())) {
                BillOfMaterialOrder subBOMOrder = (BillOfMaterialOrder) billOfMaterialOrderManager
                        .getEntityNodeByKey(
                                billOfMaterialItem.getRefSubBOMUUID(),
                                IServiceEntityNodeFieldConstant.UUID,
                                BillOfMaterialOrder.NODENAME, null);
                if (subBOMOrder != null) {
                    productionOrder.setRefWocUUID(subBOMOrder.getRefWocUUID());
                    productionOrder.setRefBillOfMaterialUUID(subBOMOrder
                            .getUuid());
                }
            }
            if (ServiceEntityStringHelper.checkNullString(productionOrder
                    .getRefWocUUID())) {
                productionOrder.setRefWocUUID(billOfMaterialItem
                        .getRefWocUUID());
            }
        }
        return productionOrder;
    }

    /**
     * [Internal method] Generate Production Order Service model, with deep
     * construct deep proposal, from current picking material item.
     *
     * @param prodPickingRefMaterialItem
     * @return
     * @throws ServiceEntityConfigureException
     * @throws ServiceEntityInstallationException
     * @throws NodeNotFoundException
     * @throws SearchConfigureException
     * @throws ProductionOrderException
     * @throws MaterialException
     * @throws BillOfMaterialException
     */
    public ProductionOrderServiceModel genUnionProductionOrder(
            ProdPickingRefMaterialItem prodPickingRefMaterialItem, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, BillOfMaterialException,
            MaterialException, ProductionOrderException,
            SearchConfigureException, NodeNotFoundException,
            ServiceEntityInstallationException, AuthorizationException, LogonInfoException {
        /*
         * [Step1] Copy and set necessary information from current picking
         * material item
         */
        ProductionOrder productionOrder = (ProductionOrder) productionOrderManager
                .newRootEntityNode(prodPickingRefMaterialItem.getClient());
        productionOrder.setId(productionOrderIdHelper.genDefaultId(
                prodPickingRefMaterialItem.getClient(), this.prodOrderIndex++));
        MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
                .getEntityNodeByKey(
                        prodPickingRefMaterialItem.getRefMaterialSKUUUID(),
                        IServiceEntityNodeFieldConstant.UUID,
                        MaterialStockKeepUnit.NODENAME,
                        prodPickingRefMaterialItem.getClient(), null);
        productionOrder.setName(genDefaultOrderName(materialStockKeepUnit));
        productionOrder.setRefMaterialSKUUUID(prodPickingRefMaterialItem
                .getRefMaterialSKUUUID());
        productionOrder.setRefBillOfMaterialUUID(prodPickingRefMaterialItem
                .getRefBillOfMaterialUUID());
        productionOrder.setAmount(prodPickingRefMaterialItem.getAmount());
        productionOrder.setPlanStartPrepareDate(prodPickingRefMaterialItem
                .getPlanStartPrepareDate());
        productionOrder.setPlanStartTime(prodPickingRefMaterialItem
                .getPlanStartTime());
        productionOrder.setPlanEndTime(prodPickingRefMaterialItem
                .getPlanEndTime());
        productionOrder.setRefUnitUUID(prodPickingRefMaterialItem
                .getRefUnitUUID());
        // Set this production order reserved for this picking order
        productionOrder
                .setReservedDocType(IDefDocumentResource.DOCUMENT_TYPE_PRODPICKINGORDER);
        productionOrder.setReservedMatItemUUID(prodPickingRefMaterialItem
                .getUuid());
        productionOrder.setProductionBatchNumber(prodPickingRefMaterialItem.getProductionBatchNumber());
        prodPickingRefMaterialItem.setNextDocMatItemUUID(productionOrder
                .getUuid());
        // Important set ref next order UUID point to next production order
        prodPickingRefMaterialItem.setRefNextOrderUUID(productionOrder
                .getUuid());
        prodPickingRefMaterialItem
                .setNextDocType(IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER);
        prodPickingRefMaterialItem.setRefUUID(productionOrder.getUuid());
        /*
         * [Step2] generate deep proposal and production order from BOM order
         * structure.
         */
        List<ServiceEntityNode> rawProdOrderItemList = productionOrderManager
                .generateProductItemListEntry(productionOrder, logonInfo, true);
        // Convert into deep construct production order service model
        ProductionOrderServiceModel productionOrderServiceModel = (ProductionOrderServiceModel) productionOrderManager
                .convertToProductionOrderServiceModel(productionOrder,
                        rawProdOrderItemList);
        return productionOrderServiceModel;
    }

    /**
     * [Internal method] Generate Purchase Contract Service Model from Plan
     * proposal
     *
     * @param prodPickingRefMaterialItem  :carry all the necessary source information
     * @param prodItemReqProposalTemplate : optional, in case need to write docItemUUID back to plan
     *                                    proposal.
     * @return
     * @throws ServiceEntityConfigureException
     */
    public OutboundDeliveryServiceModel genOutboundFromRefMaterialItem(
            ProdPickingRefMaterialItem prodPickingRefMaterialItem,
            ProdItemReqProposalTemplate prodItemReqProposalTemplate, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, DocActionException {
        OutboundDeliveryServiceModel outboundDeliveryServiceModel = new OutboundDeliveryServiceModel();
        OutboundDelivery outboundDelivery = (OutboundDelivery) outboundDeliveryManager
                .newRootEntityNode(prodPickingRefMaterialItem.getClient());
        outboundDelivery.setId(outboundDeliveryIdHelper.genDefaultId(
                prodPickingRefMaterialItem.getClient(), this.outboundIndex++));
        outboundDelivery.setRefWarehouseUUID(prodPickingRefMaterialItem
                .getRefWarehouseUUID());
        outboundDelivery
                .setNextProfDocType(IDefDocumentResource.DOCUMENT_TYPE_PRODPICKINGORDER);
        outboundDelivery.setNextProfDocUUID(prodPickingRefMaterialItem
                .getRootNodeUUID());
        outboundDelivery.setProductionBatchNumber(prodPickingRefMaterialItem
                .getProductionBatchNumber());
        outboundDelivery.setPurchaseBatchNumber(prodPickingRefMaterialItem
                .getPurchaseBatchNumber());
        OutboundItem outboundItem = (OutboundItem) outboundDeliveryManager
                .newEntityNode(outboundDelivery, OutboundItem.NODENAME);
        if (prodItemReqProposalTemplate != null) {
            // In case need to write target document information back to plan
            // proposal
            prodItemReqProposalTemplate.setRefUUID(outboundItem
                    .getUuid());
            prodItemReqProposalTemplate
                    .setItemStatus(ProductionPlanItem.STATUS_AVAILABLE);
        }
        // let picking refMaterialitem pointing to out-bound
        outboundItem.setRefStoreItemUUID(prodItemReqProposalTemplate
                .getRefStoreItemUUID());
        docFlowProxy.buildItemPrevNextRelationship(prodPickingRefMaterialItem,
                outboundItem, null,serialLogonInfo);
        outboundItem.setActualAmount(prodPickingRefMaterialItem
                .getAmount());
        outboundItem.setNextProfDocMatItemUUID(prodPickingRefMaterialItem
                .getUuid());
        outboundItem.setNextProfDocType(prodPickingRefMaterialItem
                .getHomeDocumentType());
        outboundItem
                .setReservedDocType(IDefDocumentResource.DOCUMENT_TYPE_PRODPICKINGORDER);
        outboundItem.setUnitPriceNoTax(prodPickingRefMaterialItem
                .getUnitPriceNoTax());
        outboundItem.setItemPriceNoTax(prodPickingRefMaterialItem
                .getItemPriceNoTax());
        outboundItem.setTaxRate(prodPickingRefMaterialItem
                .getTaxRate());
        outboundItem.setReservedMatItemUUID(prodPickingRefMaterialItem
                .getUuid());
        prodPickingRefMaterialItem.setRefOutboundItemUUID(outboundItem
                .getUuid());
        prodPickingRefMaterialItem.setRefNextOrderUUID(outboundDelivery
                .getUuid());
        // Also set document info to document type and refUUID
        prodPickingRefMaterialItem.setDocumentType(prodPickingRefMaterialItem
                .getNextDocType());
        prodPickingRefMaterialItem.setRefUUID(prodPickingRefMaterialItem
                .getNextDocMatItemUUID());

        prodPickingRefMaterialItem.setRefUUID(outboundDelivery.getUuid());
        List<ServiceEntityNode> outboundItemList = new ArrayList<>();
        outboundItemList.add(outboundItem);
        outboundDeliveryServiceModel.setOutboundDelivery(outboundDelivery);
        List<OutboundItemServiceModel> outboundItemServiceModelList =
                ServiceCollectionsHelper.buildServiceModelList(outboundItemList, serviceEntityNode -> {
                    OutboundItem tmpOutItemReference = (OutboundItem) serviceEntityNode;
                    OutboundItemServiceModel outboundItemServiceModel = new OutboundItemServiceModel();
                    outboundItemServiceModel.setOutboundItem(tmpOutItemReference);
                    return outboundItemServiceModel;
                });
        outboundDeliveryServiceModel
                .setOutboundItemList(outboundItemServiceModelList);
        return outboundDeliveryServiceModel;

    }

    /**
     * [Internal method] Generate Purchase Contract Service Model from Plan
     * proposal
     *
     * @param prodPickingRefMaterialItem :carry all the necessary source information
     * @param prodPlanItemReqProposal    : optional, in case need to write docItemUUID back to plan
     *                                   proposal.
     * @return
     * @throws ServiceEntityConfigureException
     */
    public InboundDeliveryServiceModel genInboundFromRefMaterialItem(
            ProdPickingRefMaterialItem prodPickingRefMaterialItem,
            ProdPlanItemReqProposal prodPlanItemReqProposal, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, DocActionException {
        InboundDeliveryServiceModel inboundDeliveryServiceModel = new InboundDeliveryServiceModel();
        InboundDelivery inboundDelivery = (InboundDelivery) inboundDeliveryManager
                .newRootEntityNode(prodPickingRefMaterialItem.getClient());
        inboundDelivery.setId(inboundDeliveryIdHelper.genDefaultId(
                prodPickingRefMaterialItem.getClient(), this.inboundIndex++));
        inboundDelivery
                .setPrevProfDocType(IDefDocumentResource.DOCUMENT_TYPE_PRODRETURNORDER);
        inboundDelivery.setPrevProfDocUUID(prodPickingRefMaterialItem
                .getRootNodeUUID());
        inboundDelivery.setRefWarehouseUUID(prodPickingRefMaterialItem
                .getRefWarehouseUUID());
        InboundItem inboundItem = (InboundItem) inboundDeliveryManager
                .newEntityNode(inboundDelivery, InboundItem.NODENAME);
        if (prodPlanItemReqProposal != null) {
            // In case need to write target document information back to plan
            // proposal
            prodPlanItemReqProposal.setRefUUID(inboundItem.getUuid());
        }
        inboundItem.setAmount(prodPickingRefMaterialItem.getAmount());
        inboundItem.setActualAmount(prodPickingRefMaterialItem
                .getAmount());
        inboundItem.setRefMaterialSKUUUID(prodPickingRefMaterialItem
                .getRefMaterialSKUUUID());
        inboundItem.setRefUUID(prodPickingRefMaterialItem
                .getRefMaterialSKUUUID());
        inboundItem.setRefUnitUUID(prodPickingRefMaterialItem
                .getRefUnitUUID());
        docFlowProxy.buildItemPrevNextRelationship(prodPickingRefMaterialItem, inboundItem, null,serialLogonInfo);
        //TODO maybe the inbound should not be the nextProf for picking item
        docFlowProxy.copyPrevProfDocMatItemToNextProfDoc(prodPickingRefMaterialItem, prodPickingRefMaterialItem.getHomeDocumentType(),
                inboundItem, inboundItem.getHomeDocumentType());
        inboundItem.setUnitPrice(prodPickingRefMaterialItem
                .getUnitPrice());
        inboundItem.setUnitPriceNoTax(prodPickingRefMaterialItem
                .getUnitPriceNoTax());
        inboundItem.setItemPrice(prodPickingRefMaterialItem
                .getItemPrice());
        inboundItem.setItemPriceNoTax(prodPickingRefMaterialItem
                .getItemPriceNoTax());
        inboundItem
                .setTaxRate(prodPickingRefMaterialItem.getTaxRate());
//		prodPickingRefMaterialItem
//				.setPrevDocMatItemUUID(prodPickingRefMaterialItem
//						.getRefProdOrderItemUUID());
        prodPickingRefMaterialItem
                .setPrevDocType(IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER);
        prodPickingRefMaterialItem.setRefPrevOrderUUID(inboundDelivery
                .getUuid());
        prodPickingRefMaterialItem.setNextDocMatItemUUID(inboundItem
                .getUuid());
        prodPickingRefMaterialItem.setRefInboundItemUUID(inboundItem
                .getUuid());
        prodPickingRefMaterialItem
                .setNextDocType(IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY);
        prodPickingRefMaterialItem.setRefInboundItemUUID(inboundItem
                .getUuid());
        List<ServiceEntityNode> inboundItemList = new ArrayList<>();
        inboundItemList.add(inboundItem);
        inboundDeliveryServiceModel.setInboundDelivery(inboundDelivery);
        List<InboundItemServiceModel> inboundItemServiceModelList =
                ServiceCollectionsHelper.buildServiceModelList(inboundItemList,
                        serviceEntityNode -> {
                            InboundItem tmpInboundItem = (InboundItem) serviceEntityNode;
                            InboundItemServiceModel inboundItemServiceModel = new InboundItemServiceModel();
                            inboundItemServiceModel.setInboundItem(tmpInboundItem);
                            return inboundItemServiceModel;
                        });
        inboundDeliveryServiceModel
                .setInboundItemList(inboundItemServiceModelList);
        return inboundDeliveryServiceModel;

    }

    /**
     * [Internal method] Generate Purchase Contract Service Model from Plan
     * proposal
     *
     * @param prodPickingRefMaterialItem  :carry all the necessary source information
     * @param prodItemReqProposalTemplate : optional, in case need to write docItemUUID back to plan
     *                                    proposal.
     * @return
     * @throws ServiceEntityConfigureException
     */
    public PurchaseContractServiceModel genUnionPurchaseContractFromRefMaterialItem(
            ProdPickingRefMaterialItem prodPickingRefMaterialItem,
            ProdItemReqProposalTemplate prodItemReqProposalTemplate,
            String productionOrderUUID, LogonInfo logonInfo) throws ServiceEntityConfigureException, DocActionException {
        /*
         * [Step1] Try to get the possible supplier
         */
        MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
                .getEntityNodeByKey(
                        prodPickingRefMaterialItem.getRefMaterialSKUUUID(),
                        IServiceEntityNodeFieldConstant.UUID,
                        MaterialStockKeepUnit.NODENAME,
                        prodPickingRefMaterialItem.getClient(), null);
        String supplierUUID = ServiceEntityStringHelper.EMPTYSTRING;
        try {
            MatDecisionValueSetting purchaseSupplier = matDecisionValueSettingManager
                    .getDecisionValue(
                            materialStockKeepUnit,
                            MatDecisionValueSettingManager.VAUSAGE_PURCHASE_SUPPLIER);
            if (purchaseSupplier != null) {
                supplierUUID = purchaseSupplier.getRawValue();
            }
        } catch (MaterialException e) {
            // just ignore
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e,
                    materialStockKeepUnit.getName()));
        }
        PurchaseContractServiceModel purchaseContractServiceModel = null;
        //TODO to use new product contract service model logic
//        try {
//            purchaseContractServiceModel = purchaseContractManager
//                    .initPurchaseContract(logonInfo,
//                            IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER, supplierUUID);
//        } catch (SearchConfigureException e) {
//            throw new RuntimeException(e);
//        }
        PurchaseContract purchaseContract = purchaseContractServiceModel.getPurchaseContract();
        purchaseContract.setProductionBatchNumber(prodPickingRefMaterialItem
                .getProductionBatchNumber());
        purchaseContract.setId(purchaseContractIdHelper.genDefaultId(
                prodPickingRefMaterialItem.getClient(), this.purchaseIndex++));
        // TODO how to decide the customer's contact person
        PurchaseContractMaterialItemServiceModel purchaseContractMaterialItemServiceModel =
                new PurchaseContractMaterialItemServiceModel();

        purchaseContract.setName(materialStockKeepUnit.getName());

        // Set previous doc for this purchase contract
        if (productionOrderUUID != null) {
            purchaseContract.setPrevProfDocUUID(productionOrderUUID);
        }
        // purchaseContract
        // .setPrevDocType(IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER);
        PurchaseContractMaterialItem purchaseContractMaterialItem = purchaseContractManager
                .newItemFromMaterialSKU(purchaseContract, purchaseContractServiceModel.getPurchaseFromSupplier(),
                        materialStockKeepUnit, 0);
        if (prodItemReqProposalTemplate != null) {
            // In case need to write target document information back to plan
            // proposal
            prodItemReqProposalTemplate.setRefUUID(purchaseContractMaterialItem
                    .getUuid());
            prodItemReqProposalTemplate
                    .setItemStatus(ProductionPlanItem.STATUS_INPROCESS);
        }
        // copy unit cost and item cost from ref material item to purchase
        // contract
        docFlowProxy.buildItemPrevNextRelationship(prodPickingRefMaterialItem,
                purchaseContractMaterialItem, null, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
        // Also set document info to document type and refUUID
        prodPickingRefMaterialItem.setDocumentType(prodPickingRefMaterialItem
                .getNextDocType());
        prodPickingRefMaterialItem.setRefUUID(prodPickingRefMaterialItem
                .getNextDocMatItemUUID());
        /*
         * [Step2] set reserved/previous material item
         */
        purchaseContractMaterialItem
                .setReservedDocType(IDefDocumentResource.DOCUMENT_TYPE_PRODPICKINGORDER);
        purchaseContractMaterialItem
                .setReservedMatItemUUID(prodPickingRefMaterialItem.getUuid());
        purchaseContractMaterialItemServiceModel
                .setPurchaseContractMaterialItem(purchaseContractMaterialItem);
        List<PurchaseContractMaterialItemServiceModel> purchaseContractMaterialItemList = new ArrayList<>();
        purchaseContractMaterialItemList
                .add(purchaseContractMaterialItemServiceModel);
        purchaseContractServiceModel.setPurchaseContract(purchaseContract);
        purchaseContractServiceModel
                .setPurchaseContractMaterialItemList(purchaseContractMaterialItemList);
        return purchaseContractServiceModel;

    }

    /**
     * Main Entrance Logic to release Production Plan to production orders,
     * picking orders, as well as sub orders
     *
     * @param productionPlanServiceModel
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     * @throws MaterialException
     * @throws ProductionOrderException
     * @throws BillOfMaterialException
     */
    public void releaseProductionPlanWrapper(
            ProductionPlanServiceModel productionPlanServiceModel,
            LogonInfo logonInfo) throws ServiceEntityConfigureException, ServiceModuleProxyException, MaterialException, BillOfMaterialException, ProductionOrderException, ServiceComExecuteException, DocActionException {
        /*
         * [Step1] Initialize main production order and picking order
         */
        this.prodOrderIndex = 0;
        this.pickingOrderIndex = 0;
        this.outboundIndex = 0;
        this.purchaseIndex = 0;
        ProductionPlan productionPlan = productionPlanServiceModel
                .getProductionPlan();
        // generate main production order service model
        ProductionOrder productionOrder = generateProductionOrderFromPlan(productionPlanServiceModel);
        productionOrder.setStatus(ProductionOrder.STATUS_APPROVED);
        productionOrder.setId(productionOrderIdHelper.genDefaultId(
                productionPlan.getClient(), this.prodOrderIndex++));
        // Set Plan's main production order
        productionPlan.setRefMainProdOrderUUID(productionOrder.getUuid());
        productionOrder.setName(productionPlan.getName());
        // copy the warehouse configuration
        List<ServiceEntityNode> prodOrderSupplyWarehouseList = this
                .batchCopyWarehouse(productionOrder, productionPlanServiceModel
                        .getProdPlanSupplyWarehouseList());
        ProductionOrderServiceModel productionOrderServiceModel = new ProductionOrderServiceModel();
        productionOrderServiceModel.setProductionOrder(productionOrder);
        productionOrderServiceModel
                .setProdOrderSupplyWarehouseList(prodOrderSupplyWarehouseList);
        // generate picking order service model
        ProdPickingOrderServiceModel prodPickingOrderServiceModel = prodPickingOrderManager
                .newPickingOrderByProdWrapper(productionOrder,
                        this.pickingOrderIndex++,
                        ProdPickingOrder.STATUS_APPROVED,
                        ProdPickingOrder.PROCESSTYPE_INPLAN);
        ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel = ProdPickingOrderManager
                .filterRefOrderItemByOrder(prodPickingOrderServiceModel
                        .getProdPickingRefOrderItemList(), productionOrder
                        .getUuid());
        List<ServiceModule> resultList = new ArrayList<>();
        resultList.add(prodPickingOrderServiceModel);
        resultList.add(productionOrderServiceModel);
        List<ServiceModule> subResultList = this.releaseProductionPlanItem(
                productionPlanServiceModel, productionOrderServiceModel,
                prodPickingRefOrderItemServiceModel,
                productionPlanServiceModel.getProductionPlanItemList(),
               logonInfo);
        if (!ServiceCollectionsHelper.checkNullList(subResultList)) {
            resultList.addAll(subResultList);
        }
        /*
         * [Step3] Generate the resource of Target Mat Item and sub item
         */
        List<ServiceEntityNode> prevDocMatItemList =
                productionDocRequestService.getAllPrevRequestMatItemByPlan(productionPlan.getUuid(),
                 productionPlan.getClient());
        prodOrderTargetMatItemManager.newProdTargetMatItemWrapper(
                productionOrderServiceModel, null, prevDocMatItemList, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));

        /*
         * [Step4] Update sub picking order and sub production order
         */
        if (!ServiceCollectionsHelper.checkNullList(resultList)) {
            postProcessServiceModelList(
                    resultList,
                    serviceModule -> {
                        try {
                            updateServiceModuleWrapper(serviceModule,
                                    logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
                        } catch (Exception e) {
                            logger.error("Failed to update service module during production release", e);
                        }
                        return serviceModule;
                    });
        }
    }

    private void postProcessServiceModelList(List<ServiceModule> resultList,
                                             Function<ServiceModule, ServiceModule> updateCallBack) {
        Map<String, ServiceModule> outboundServiceModelArray = new HashMap<>();
        Map<String, ServiceModule> purchaseContractServiceModelArray = new HashMap<>();
        if (ServiceCollectionsHelper.checkNullList(resultList)) {
            return;
        }
        for (ServiceModule serviceModule : resultList) {
            /*
             * [Case1] In case Production order service model, direct update
             */
            if (ProductionOrderServiceModel.class.getName().equals(
                    serviceModule.getClass().getName())) {
                // in case production order service
                if (updateCallBack != null) {
                    updateCallBack.apply(serviceModule);
                }
                continue;
            }
            /*
             * [Case2] In case ProdPicking order service model, direct update
             */
            if (ProdPickingOrderServiceModel.class.getName().equals(
                    serviceModule.getClass().getName())) {
                // in case picking order service
                if (updateCallBack != null) {
                    updateCallBack.apply(serviceModule);
                }
                continue;
            }
            /*
             * [Case3] In case Purchase contract service model, Merge by
             * supplier's uuid
             */
            if (PurchaseContractServiceModel.class.getName().equals(
                    serviceModule.getClass().getName())) {
                PurchaseContractServiceModel purchaseContractServiceModel =
                 (PurchaseContractServiceModel) serviceModule;
                PurchaseContract purchaseContract = purchaseContractServiceModel
                        .getPurchaseContract();
                PurchaseContractParty purchaseContractPartyB = purchaseContractServiceModel.getPurchaseFromSupplier();
                // In case purchase contract service model with empty contract
                // B, Save instantly, don't need to merge
                if (ServiceEntityStringHelper.checkNullString(purchaseContractPartyB.getRefUUID())) {
                    if (updateCallBack != null) {
                        updateCallBack.apply(purchaseContractServiceModel);
                    }
                    continue;
                }
                if (purchaseContractServiceModelArray
                        .containsKey(purchaseContractPartyB.getRefUUID())) {
                    // merge purchase
                    PurchaseContractServiceModel existPurchaseServiceModel =
                     (PurchaseContractServiceModel) purchaseContractServiceModelArray
                            .get(purchaseContractPartyB.getRefUUID());
                    try {
                        mergePurchaseContractServiceModel(
                                        existPurchaseServiceModel,
                                        purchaseContractServiceModel);
                    } catch (ServiceModuleProxyException e) {
                        continue;
                    }
                } else {
                    purchaseContractServiceModelArray.put(
                            purchaseContractPartyB.getRefUUID(),
                            purchaseContractServiceModel);
                }
            }
            /*
             * [Case4] In case Outbound delivery service model, Merge by
             * Warehouse or by prodpicking order
             */
            int mergeOutFlag = 1; // merge by warehouse
            if (OutboundDeliveryServiceModel.class.getName().equals(
                    serviceModule.getClass().getName())) {
                OutboundDeliveryServiceModel outboundDeliveryServiceModel =
                        (OutboundDeliveryServiceModel) serviceModule;
                // in case outbound delivery service
                OutboundDelivery outboundDelivery = outboundDeliveryServiceModel
                        .getOutboundDelivery();
                List<ServiceEntityNode> outboundItemList = ServiceCollectionsHelper.parseToSENodeList(outboundDeliveryServiceModel
                        .getOutboundItemList(), OutboundItemServiceModel::getOutboundItem);
                if (ServiceCollectionsHelper.checkNullList(outboundItemList)) {
                    continue;
                }
                // TODO read this from real system configure
                String key = ServiceEntityStringHelper.EMPTYSTRING;
                if (mergeOutFlag == 1) {
                    key = outboundDelivery.getRefWarehouseUUID();
                } else {
                    OutboundItem outboundItem = (OutboundItem) outboundItemList
                            .get(0);
                    key = outboundItem.getReservedMatItemUUID();
                }
                if (outboundServiceModelArray.containsKey(outboundDelivery
                        .getRefWarehouseUUID())) {
                    // merge outbound delivery
                    OutboundDeliveryServiceModel existOutServiceModel =
                     (OutboundDeliveryServiceModel) outboundServiceModelArray
                            .get(outboundDelivery.getRefWarehouseUUID());
                    try {mergeOutboundDeliveryServiceModel(
                                        existOutServiceModel,
                                        outboundDeliveryServiceModel);
                    } catch (ServiceModuleProxyException ignored) {
                    }
                } else {
                    outboundServiceModelArray.put(key,
                            outboundDeliveryServiceModel);
                }
            }
        }
        // After all process is done
        this.postProcessArrayUnion(outboundServiceModelArray, updateCallBack);
        this.postProcessArrayUnion(purchaseContractServiceModelArray,
                updateCallBack);

    }

    /**
     * Logic to merge outbound delivery service model
     *
     * @param outboundDeliveryServiceModel
     * @param outServiceModelToMerge
     * @throws ServiceModuleProxyException
     */
    public void mergeOutboundDeliveryServiceModel(OutboundDeliveryServiceModel outboundDeliveryServiceModel,
                                                  OutboundDeliveryServiceModel outServiceModelToMerge)
            throws ServiceModuleProxyException {
        OutboundDelivery outboundDelivery = outboundDeliveryServiceModel.getOutboundDelivery();
        OutboundDelivery outboundToMerge = outServiceModelToMerge.getOutboundDelivery();
        if (!outboundDelivery.getRefWarehouseUUID().equals(outboundToMerge.getRefWarehouseUUID())) {
            return;
        }
        if (ServiceCollectionsHelper.checkNullList(
                ServiceCollectionsHelper.parseToSENodeList(outServiceModelToMerge.getOutboundItemList(),
                        OutboundItemServiceModel::getOutboundItem))) {
            return;
        }
        serviceModuleProxy.defaultMergeServiceModule(outboundDeliveryManager, outboundDeliveryServiceModel, outServiceModelToMerge);
    }

    /**
     * Logic to merge purchase service model in run-time, and from production
     *
     * generation request
     *
     * @param purchaseContractServiceModel
     * @param purchaseServiceModelToMerge
     * @throws ServiceModuleProxyException
     */
    // Usage only in production
    @Deprecated
    public void mergePurchaseContractServiceModel(PurchaseContractServiceModel purchaseContractServiceModel,
                                                  PurchaseContractServiceModel purchaseServiceModelToMerge)
            throws ServiceModuleProxyException {
        PurchaseContractParty purchaseContractPartyB = purchaseContractServiceModel.getPurchaseFromSupplier();
        PurchaseContractParty purchaseToMergePartyB = purchaseServiceModelToMerge.getPurchaseFromSupplier();
        String partyBUUID = purchaseContractPartyB.getRefUUID();
        String partyBToMergeUUID = purchaseToMergePartyB.getRefUUID();
        if (!ServiceEntityStringHelper.checkNullString(partyBUUID) ||
                !ServiceEntityStringHelper.checkNullString(partyBToMergeUUID)) {
            if (ServiceEntityStringHelper.checkNullString(partyBUUID)) {
                return;
            }
            if (ServiceEntityStringHelper.checkNullString(partyBToMergeUUID)) {
                return;
            }
            if (!partyBUUID.equals(partyBToMergeUUID)) {
                return;
            }
        }
        if (ServiceCollectionsHelper.checkNullList(purchaseServiceModelToMerge.getPurchaseContractMaterialItemList())) {
            return;
        }
        serviceModuleProxy.defaultMergeServiceModule(purchaseContractManager, purchaseContractServiceModel, purchaseServiceModelToMerge);
    }

    private void postProcessArrayUnion(
            Map<String, ServiceModule> serviceModelArray,
            Function<ServiceModule, ServiceModule> updateCallBack) {
        Set<String> keySet = serviceModelArray.keySet();
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
            String key = it.next();
            ServiceModule serviceModule = serviceModelArray.get(key);
            if (updateCallBack != null) {
                updateCallBack.apply(serviceModule);
            }
        }
    }

    /**
     * Main Entrance to release deep-constructed production order service model
     * to deep layers with sub production order, purchase contract, outbound,
     * and finally update to DB persistence
     *
     * @param productionOrderServiceModel
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     * @throws MaterialException
     * @throws BillOfMaterialException
     * @throws ProductionOrderException
     * @throws SearchConfigureException
     * @throws NodeNotFoundException
     * @throws ServiceEntityInstallationException
     * @throws ProdOrderReportException
     */
    public void releaseProductionOrderWrapper(
            ProductionOrderServiceModel parentProductionOrderServiceModel,
            ProductionOrderServiceModel productionOrderServiceModel,
            ProdPickingRefMaterialItem prodPickingRefMaterialItem,
            LogonInfo logonInfo)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, MaterialException,
            BillOfMaterialException, ProductionOrderException, SearchConfigureException, NodeNotFoundException,
            ServiceEntityInstallationException, ServiceComExecuteException, DocActionException, AuthorizationException, LogonInfoException {
        /*
         * [Step1] Initialize main production order and picking order
         */
        ProductionOrder productionOrder = productionOrderServiceModel
                .getProductionOrder();

        // Approve this newly created production order
        productionOrderActionExecutionProxy.executeActionCore(productionOrderServiceModel,
                ProductionOrderActionNode.DOC_ACTION_APPROVE, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
        // productionOrderManager.approveOrderCore(productionOrderServiceModel, serialLogonInfo);
        // generate picking order service model
        ProdPickingOrderServiceModel prodPickingOrderServiceModel = prodPickingOrderManager
                .newPickingOrderByProdWrapper(productionOrder,
                        this.pickingOrderIndex++,
                        ProdPickingOrder.STATUS_INITIAL,
                        ProdPickingOrder.PROCESSTYPE_INPLAN);
        ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel = ProdPickingOrderManager
                .filterRefOrderItemByOrder(prodPickingOrderServiceModel
                        .getProdPickingRefOrderItemList(), productionOrder
                        .getUuid());
        /*
         * [Step2] Deep release the current deep-construct production order
         * service model into deep sub Production order, Purchase, and Out-bound
         * Delivery
         */
        List<ServiceModule> resultList = this
                .deepReleaseProductionServiceModel(
                        parentProductionOrderServiceModel,
                        productionOrderServiceModel
                                .getProductionOrderItemList(),
                        prodPickingRefOrderItemServiceModel, logonInfo);
        /*
         * [Step3] Generate the resource of Target Mat Item and sub item
         */
        List<ServiceEntityNode> prevDocMatItemList = new ArrayList<>();
        ProductionPlan productionPlan = productionPlanManager.getProductionPlanByMainOrder(productionOrder.getUuid(),
                productionOrder.getClient());
        if (productionPlan != null) {
            prevDocMatItemList =
                    productionDocRequestService.getAllPrevRequestMatItemByPlan(productionPlan.getUuid(),
                            productionPlan.getClient());
        }
        List<ProdOrderTargetMatItemServiceModel> prodOrderTargetMatItemList = prodOrderTargetMatItemManager
                .newProdTargetMatItemWrapper(productionOrderServiceModel, prodPickingRefMaterialItem,
                        prevDocMatItemList,LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
        /*
         * [Step4] Update sub picking order and sub production order
         */
        if (!ServiceCollectionsHelper.checkNullList(resultList)) {
            resultList.add(prodPickingOrderServiceModel);
            resultList.add(productionOrderServiceModel);
            for (ServiceModule serviceModule : resultList) {
                updateServiceModuleWrapper(serviceModule, logonInfo.getRefUserUUID(),
                        logonInfo.getResOrgUUID());
            }
        }
    }

    private void updateServiceModuleWrapper(ServiceModule serviceModule,
                                            String logonUserUUID, String organizationUUID)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        if (ProductionOrderServiceModel.class.getName().equals(
                serviceModule.getClass().getName())) {
            ProductionOrderServiceModel productionOrderServiceModel = (ProductionOrderServiceModel) serviceModule;
            // in case production order service
            productionOrderManager.updateServiceModuleWithDelete(
                    ProductionOrderServiceModel.class,
                    productionOrderServiceModel, logonUserUUID,
                    organizationUUID);
        }
        if (ProdPickingOrderServiceModel.class.getName().equals(
                serviceModule.getClass().getName())) {
            ProdPickingOrderServiceModel prodPickingOrderServiceModel = (ProdPickingOrderServiceModel) serviceModule;
            ProdPickingOrder prodPickingOrder = prodPickingOrderServiceModel
                    .getProdPickingOrder();
            // Deprecate code
            prodPickingOrder.setStatus(ProdPickingOrder.STATUS_APPROVED);
            prodPickingOrder.setApproveBy(logonUserUUID);
            if (prodPickingOrder.getApproveDate() == null) {
                prodPickingOrder.setApproveDate(java.time.LocalDate.now());
            }
            // in case picking order service
            prodPickingOrderManager.updateServiceModuleWithDelete(
                    ProdPickingOrderServiceModel.class,
                    prodPickingOrderServiceModel, logonUserUUID,
                    organizationUUID);
        }
        if (PurchaseContractServiceModel.class.getName().equals(
                serviceModule.getClass().getName())) {
            PurchaseContractServiceModel purchaseContractServiceModel = (PurchaseContractServiceModel) serviceModule;
            // in case purchase contract service model
            purchaseContractManager.updateServiceModuleWithDelete(
                    PurchaseContractServiceModel.class,
                    purchaseContractServiceModel, logonUserUUID,
                    organizationUUID, PurchaseContract.SENAME, purchaseContractServiceUIModelExtension);
        }
        if (OutboundDeliveryServiceModel.class.getName().equals(
                serviceModule.getClass().getName())) {
            OutboundDeliveryServiceModel outboundDeliveryServiceModel = (OutboundDeliveryServiceModel) serviceModule;
            // in case outbound delivery service
            outboundDeliveryManager.updateServiceModuleWithDelete(
                    OutboundDeliveryServiceModel.class,
                    outboundDeliveryServiceModel, logonUserUUID,
                    organizationUUID);
        }
        printServiceModuleWrapper(serviceModule);
    }

    private void printServiceModuleWrapper(ServiceModule serviceModule)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        if (ProductionOrderServiceModel.class.getName().equals(
                serviceModule.getClass().getName())) {
            ProductionOrderServiceModel productionOrderServiceModel = (ProductionOrderServiceModel) serviceModule;
            ProductionOrder productionOrder = productionOrderServiceModel
                    .getProductionOrder();
            // in case production order service
            String text = "[ProductionOrder]:[" + productionOrder.getId()
                    + "]-[" + productionOrder.getRefMaterialSKUUUID() + "]-"
                    + productionOrder.getAmount();
            logger.info(text);
        }
        if (ProdPickingOrderServiceModel.class.getName().equals(
                serviceModule.getClass().getName())) {
            ProdPickingOrderServiceModel prodPickingOrderServiceModel = (ProdPickingOrderServiceModel) serviceModule;
            // in case production order service
            List<ProdPickingRefOrderItemServiceModel> orderItemList = prodPickingOrderServiceModel
                    .getProdPickingRefOrderItemList();
            for (ProdPickingRefOrderItemServiceModel orderItemServiceModel : orderItemList) {
                List<ServiceEntityNode> seNodeList =
                        ProdPickingOrderManager.pluckToRefMaterialItemList(orderItemServiceModel.getProdPickingRefMaterialItemList());
                if (!ServiceCollectionsHelper.checkNullList(seNodeList)) {
                    for (ServiceEntityNode seNode : seNodeList) {
                        ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) seNode;
                        String text = "[ProdPickingOrder]:["
                                + prodPickingOrderServiceModel
                                .getProdPickingOrder().getId() + "]-["
                                + prodPickingRefMaterialItem.getRefUUID()
                                + "]-" + prodPickingRefMaterialItem.getAmount();
                        logger.info(text);
                    }
                }
            }
        }
        if (PurchaseContractServiceModel.class.getName().equals(
                serviceModule.getClass().getName())) {
            PurchaseContractServiceModel purchaseContractServiceModel = (PurchaseContractServiceModel) serviceModule;
            // in case production order service
            List<PurchaseContractMaterialItemServiceModel> purchaseContractMaterialItemList =
                    purchaseContractServiceModel
                    .getPurchaseContractMaterialItemList();
            if (!ServiceCollectionsHelper
                    .checkNullList(purchaseContractMaterialItemList)) {
                for (PurchaseContractMaterialItemServiceModel purchaseContractMaterialItemServiceModel :
                 purchaseContractMaterialItemList) {
                    PurchaseContractMaterialItem purchaseContractMaterialItem = purchaseContractMaterialItemServiceModel
                            .getPurchaseContractMaterialItem();
                    String text = "[Purchase Contract]:["
                            + purchaseContractServiceModel
                            .getPurchaseContract().getId() + "]-["
                            + purchaseContractMaterialItem.getRefUUID() + "]-"
                            + purchaseContractMaterialItem.getAmount();
                    logger.info(text);
                }
            }
        }
        if (OutboundDeliveryServiceModel.class.getName().equals(
                serviceModule.getClass().getName())) {
            OutboundDeliveryServiceModel outboundDeliveryServiceModel = (OutboundDeliveryServiceModel) serviceModule;
            // in case production order service
            if (!ServiceCollectionsHelper
                    .checkNullList(outboundDeliveryServiceModel
                            .getOutboundItemList())) {
                for (OutboundItemServiceModel outboundItemServiceModel : outboundDeliveryServiceModel
                        .getOutboundItemList()) {
                    OutboundItem outboundItem = outboundItemServiceModel.getOutboundItem();
                    String text = "[Purchase Contract]:["
                            + outboundDeliveryServiceModel
                            .getOutboundDelivery().getId() + "]-["
                            + outboundItem.getRefUUID() + "]-"
                            + outboundItem.getAmount();
                    logger.info(text);
                }
            }
        }
    }

    /**
     * Release To Purchase Contract with reference to reference Material item,
     * from production Plan item proposal. Generate Sub Picking order as well as
     * sub resources recursively to this production order
     *
     * @param prodPlanItemReqProposal
     * @param parentPickingRefOrderItemServiceModel : reference order item reference to parent production order
     * @param productionOrderItemServiceModel       : parent production order item
     *                                              service model, new reference material item will be generated and
     *                                              attach to this service model
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     * @throws MaterialException
     */
    private List<ServiceModule> releaseToOutDeliveryWrapper(
            ProdPlanItemReqProposal prodPlanItemReqProposal,
            ProdPickingRefOrderItemServiceModel parentPickingRefOrderItemServiceModel,
            ProductionOrderItemServiceModel productionOrderItemServiceModel, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, MaterialException, ServiceComExecuteException, DocActionException {
        /*
         * [Step1] generate prodOrderItemProposal from plan proposal
         */
        ProductionOrderItem productionOrderItem = productionOrderItemServiceModel
                .getProductionOrderItem();
        ProdOrderItemReqProposal prodOrderItemReqProposal = productionOrderItemManager
                .generateProdOrderItemProposalFromPlan(prodPlanItemReqProposal,
                        productionOrderItem, serialLogonInfo);
        // update to input production order item
        ProductionOrderItemManager.mergeProposalIntoItemServiceModel(
                productionOrderItemServiceModel, prodOrderItemReqProposal);
        /*
         * [Step2] new picking mat item and point to order item proposal
         */
        ProdPickingRefMaterialItem prodPickingRefMaterialItem = prodPickingOrderManager
                .newRefMaterialItem(prodOrderItemReqProposal,
                        parentPickingRefOrderItemServiceModel
                                .getProdPickingRefOrderItem(),
                        ProdPickingRefMaterialItem.ITEMSTATUS_APPROVED, serialLogonInfo);
        /*
         * [Step2]Generate Out-bound Delivery and material item based on picking
         * reference material item
         */
        OutboundDeliveryServiceModel outboundDeliveryServiceModel = this
                .genOutboundFromRefMaterialItem(prodPickingRefMaterialItem,
                        prodOrderItemReqProposal, serialLogonInfo);
        /*
         * [Step2.2] Also copy refUUID information back to plan item
         */
        prodPlanItemReqProposal.setRefUUID(prodOrderItemReqProposal
                .getRefUUID());
        prodPlanItemReqProposal.setItemStatus(prodOrderItemReqProposal.getItemStatus());
        prodPickingRefMaterialItem.setRefProdOrderItemUUID(productionOrderItem
                .getUuid());
        List<ProdPickingRefMaterialItemServiceModel> prodPickingRefMaterialItemList = parentPickingRefOrderItemServiceModel
                .getProdPickingRefMaterialItemList();
        ProdPickingRefMaterialItemServiceModel prodPickingRefMaterialItemServiceModel =
                new ProdPickingRefMaterialItemServiceModel();
        prodPickingRefMaterialItemServiceModel.setProdPickingRefMaterialItem(prodPickingRefMaterialItem);
        prodPickingRefMaterialItemList.add(prodPickingRefMaterialItemServiceModel);
        parentPickingRefOrderItemServiceModel
                .setProdPickingRefMaterialItemList(prodPickingRefMaterialItemList);
        /*
         * [Step3] Update sub picking order and sub production order
         */
        List<ServiceModule> resultList = new ArrayList<>();
        resultList.add(outboundDeliveryServiceModel);
        return resultList;
    }

    /**
     * Release To Purchase Contract with reference to reference Material item,
     * from production Order item proposal. Generate Sub Picking order as well
     * as sub resources recursively to this production order
     *
     * @param prodOrderItemReqProposal
     * @param parentPickingRefOrderItemServiceModel : reference order item reference to parent production order
     * @param productionOrderItem                   : parent picking order
     *                                              service model, new reference material item will be generated and
     *
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     * @throws MaterialException
     */
    private List<ServiceModule> releaseToOutDeliveryWrapper(
            ProdOrderItemReqProposal prodOrderItemReqProposal,
            ProdPickingRefOrderItemServiceModel parentPickingRefOrderItemServiceModel,
            ProductionOrderItem productionOrderItem, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, MaterialException,
            ServiceComExecuteException, DocActionException {
        /*
         * [Step1] Generate sub production order from proposal's information
         */
        // new reference material item and bind to newly create production order
        // instance
        ProdPickingRefMaterialItem prodPickingRefMaterialItem = prodPickingOrderManager
                .newRefMaterialItem(prodOrderItemReqProposal,
                        parentPickingRefOrderItemServiceModel
                                .getProdPickingRefOrderItem(),
                        ProdPickingRefMaterialItem.ITEMSTATUS_APPROVED, serialLogonInfo);
        /*
         * [Step2]Generate out-bound Delivery and material item based on picking
         * ref material item
         */
        OutboundDeliveryServiceModel outboundDeliveryServiceModel = this
                .genOutboundFromRefMaterialItem(prodPickingRefMaterialItem,
                        prodOrderItemReqProposal, serialLogonInfo);
        prodPickingRefMaterialItem.setRefProdOrderItemUUID(productionOrderItem
                .getUuid());
        List<ProdPickingRefMaterialItemServiceModel> prodPickingRefMaterialItemList = parentPickingRefOrderItemServiceModel
                .getProdPickingRefMaterialItemList();
        ProdPickingRefMaterialItemServiceModel prodPickingRefMaterialItemServiceModel =
                new ProdPickingRefMaterialItemServiceModel();
        prodPickingRefMaterialItemServiceModel.setProdPickingRefMaterialItem(prodPickingRefMaterialItem);
        prodPickingRefMaterialItemList.add(prodPickingRefMaterialItemServiceModel);
        parentPickingRefOrderItemServiceModel
                .setProdPickingRefMaterialItemList(prodPickingRefMaterialItemList);
        /*
         * [Step3] Update sub picking order and sub production order
         */
        List<ServiceModule> resultList = new ArrayList<>();
        resultList.add(outboundDeliveryServiceModel);
        return resultList;
    }

    /**
     * Release To Purchase Contract with reference to reference Material item,
     * from production plan item proposal. Generate Sub Picking order as well as
     * sub resources recursively to this production order
     *
     * @param prodPlanItemReqProposal
     * @param parentPickingRefOrderItemServiceModel : reference order item reference to parent production order
     * @param productionOrderItemServiceModel       : parent picking order service model, new reference material
     *                                              item will be generated and attach to this service model
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     * @throws MaterialException
     */
    private List<ServiceModule> releaseToPurchaseContractWrapper(
            ProdPlanItemReqProposal prodPlanItemReqProposal,
            ProdPickingRefOrderItemServiceModel parentPickingRefOrderItemServiceModel,
            ProductionOrderItemServiceModel productionOrderItemServiceModel, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, MaterialException, ServiceComExecuteException, DocActionException {

        /*
         * [Step1] generate prodOrderItemProposal from plan proposal
         */
        if (prodPlanItemReqProposal.getDocumentType() != IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT
                && prodPlanItemReqProposal.getDocumentType() != IDefDocumentResource.DOCUMENT_TYPE_PURCHASE) {
            return null;
        }
        ProductionOrderItem productionOrderItem = productionOrderItemServiceModel
                .getProductionOrderItem();
        ProdOrderItemReqProposal prodOrderItemReqProposal = productionOrderItemManager
                .generateProdOrderItemProposalFromPlan(prodPlanItemReqProposal,
                        productionOrderItem, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
        // update to input production order item
        ProductionOrderItemManager.mergeProposalIntoItemServiceModel(
                productionOrderItemServiceModel, prodOrderItemReqProposal);
        int documentType = IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT;

        // new reference material item and bind to newly create production order
        // instance
        ProdPickingRefMaterialItem prodPickingRefMaterialItem = prodPickingOrderManager
                .newRefMaterialItem(prodOrderItemReqProposal,
                        parentPickingRefOrderItemServiceModel
                                .getProdPickingRefOrderItem(),
                        ProdPickingRefMaterialItem.ITEMSTATUS_APPROVED, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));

        /*
         * [Step2] Generate purchase contract and material item from ref
         * material item
         */
        PurchaseContractServiceModel purchaseContractServiceModel = this
                .genUnionPurchaseContractFromRefMaterialItem(
                        prodPickingRefMaterialItem, prodOrderItemReqProposal,
                        productionOrderItem.getUuid(), logonInfo);
        /*
         * [Step2.2] Also copy refUUID information back to plan item
         */
        prodPlanItemReqProposal.setRefUUID(prodOrderItemReqProposal
                .getRefUUID());
        prodPlanItemReqProposal.setItemStatus(prodOrderItemReqProposal.getItemStatus());
        prodPickingRefMaterialItem.setRefPrevOrderUUID(productionOrderItem
                .getRootNodeUUID());
        prodPickingRefMaterialItem.setNextDocType(documentType);
        prodPickingRefMaterialItem
                .setRefNextOrderUUID(purchaseContractServiceModel
                        .getPurchaseContract().getUuid());
        List<ProdPickingRefMaterialItemServiceModel> prodPickingRefMaterialItemList = parentPickingRefOrderItemServiceModel
                .getProdPickingRefMaterialItemList();
        ProdPickingRefMaterialItemServiceModel prodPickingRefMaterialItemServiceModel =
                new ProdPickingRefMaterialItemServiceModel();
        prodPickingRefMaterialItemServiceModel.setProdPickingRefMaterialItem(prodPickingRefMaterialItem);
        prodPickingRefMaterialItemList.add(prodPickingRefMaterialItemServiceModel);
        parentPickingRefOrderItemServiceModel
                .setProdPickingRefMaterialItemList(prodPickingRefMaterialItemList);
        List<ServiceModule> resultList = new ArrayList<>();
        resultList.add(purchaseContractServiceModel);
        return resultList;
    }

    /**
     * Release To Purchase Contract with reference to reference Material item,
     * from production order item proposal. Generate Sub Picking order as well
     * as sub resources recursively to this production order
     *
     * @param prodOrderItemReqProposal
     * @param parentPickingRefOrderItemServiceModel : reference order item reference to parent production order
     * @param productionOrderItem                   : parent picking order service model, new reference material
     *                                              item will be generated and attach to this service model

     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     * @throws MaterialException
     */
    private List<ServiceModule> releaseToPurchaseContractWrapper(
            ProdOrderItemReqProposal prodOrderItemReqProposal,
            ProdPickingRefOrderItemServiceModel parentPickingRefOrderItemServiceModel,
            ProductionOrderItem productionOrderItem, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, MaterialException,
            ServiceComExecuteException, DocActionException {
        /*
         * [Step1] Generate sub production order from proposal's information
         */

        // new reference material item and bind to newly create production order
        // instance
        ProdPickingRefMaterialItem prodPickingRefMaterialItem = prodPickingOrderManager
                .newRefMaterialItem(prodOrderItemReqProposal,
                        parentPickingRefOrderItemServiceModel
                                .getProdPickingRefOrderItem(),
                        ProdPickingRefMaterialItem.ITEMSTATUS_APPROVED, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
        if (prodOrderItemReqProposal.getDocumentType() != IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT
                && prodOrderItemReqProposal.getDocumentType() != IDefDocumentResource.DOCUMENT_TYPE_PURCHASE) {
            return null;
        }
        /*
         * [Step2] Generate purchase contract and material item from ref
         * material item
         */
        PurchaseContractServiceModel purchaseContractServiceModel = this
                .genUnionPurchaseContractFromRefMaterialItem(
                        prodPickingRefMaterialItem, null,
                        productionOrderItem.getRootNodeUUID(), logonInfo);
        prodPickingRefMaterialItem.setRefProdOrderItemUUID(productionOrderItem
                .getUuid());
        List<ProdPickingRefMaterialItemServiceModel> prodPickingRefMaterialItemList = parentPickingRefOrderItemServiceModel
                .getProdPickingRefMaterialItemList();
        ProdPickingRefMaterialItemServiceModel prodPickingRefMaterialItemServiceModel =
                new ProdPickingRefMaterialItemServiceModel();
        prodPickingRefMaterialItemServiceModel.setProdPickingRefMaterialItem(prodPickingRefMaterialItem);
        prodPickingRefMaterialItemList.add(prodPickingRefMaterialItemServiceModel);
        parentPickingRefOrderItemServiceModel
                .setProdPickingRefMaterialItemList(prodPickingRefMaterialItemList);
        List<ServiceModule> resultList = new ArrayList<>();
        resultList.add(purchaseContractServiceModel);
        return resultList;
    }

    /**
     * Release To Sub Production order with reference to reference Material
     * item, from production plan item proposal. Generate Sub Picking order as
     * well as sub resources recursively to this production order
     *
     * @param productionPlanItemList
     * @param prodPlanItemReqProposalServiceModel
     * @param parentPickingRefOrderItemServiceModel : reference order item reference to parent production order
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     * @throws MaterialException
     * @throws ProductionOrderException
     * @throws BillOfMaterialException
     */
    private List<ServiceModule> releaseToSubProductionOrderRecursive(
            ProductionPlanServiceModel productionPlanServiceModel,
            List<ProductionPlanItemServiceModel> productionPlanItemList,
            ProdPlanItemReqProposalServiceModel prodPlanItemReqProposalServiceModel,
            ProdPickingRefOrderItemServiceModel parentPickingRefOrderItemServiceModel,
            ProductionOrderItemServiceModel productionOrderItemServiceModel,
            LogonInfo logonInfo) throws ServiceEntityConfigureException, ServiceModuleProxyException, MaterialException, BillOfMaterialException, ProductionOrderException, ServiceComExecuteException, DocActionException {
        /*
         * [Step1] Generate and copy to prodOrderItemReqProposal from plan
         * proposal, and link plan proposal to order proposal
         */
        ProductionOrderItem productionOrderItem = productionOrderItemServiceModel
                .getProductionOrderItem();
        ProdPlanItemReqProposal prodPlanItemReqProposal = prodPlanItemReqProposalServiceModel
                .getProdPlanItemReqProposal();
        // Also generate prodOrderItemProposal from plan proposal
        ProdOrderItemReqProposal prodOrderItemReqProposal = productionOrderItemManager
                .generateProdOrderItemProposalFromPlan(prodPlanItemReqProposal,
                        productionOrderItem, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
        // update to input production order item
        ProductionOrderItemManager.mergeProposalIntoItemServiceModel(
                productionOrderItemServiceModel, prodOrderItemReqProposal);

        /*
         * [Step2] Generate sub production order from plan proposal
         */
        ProductionOrder subProductionOrder = this
                .genUnionProductionOrderFromPlanProposal(prodPlanItemReqProposalServiceModel);
        ProductionPlan productionPlan = productionPlanServiceModel
                .getProductionPlan();
        if (productionPlan != null) {
            subProductionOrder.setRefPlanUUID(productionPlan.getUuid());
            subProductionOrder.setProductionBatchNumber(productionPlan
                    .getProductionBatchNumber());
        }
        // Copy warehouse setting to sub production order
        List<ServiceEntityNode> prodOrderSupplyWarehouseList = this
                .batchCopyWarehouse(subProductionOrder,
                        productionPlanServiceModel
                                .getProdPlanSupplyWarehouseList());
        subProductionOrder.setStatus(ProductionOrder.STATUS_APPROVED);
        ProductionOrderServiceModel subProductionOrderServiceModel = new ProductionOrderServiceModel();
        subProductionOrderServiceModel.setProductionOrder(subProductionOrder);
        subProductionOrderServiceModel
                .setProdOrderSupplyWarehouseList(prodOrderSupplyWarehouseList);
        /*
         * [Step2.5] Copy production order info to proposals
         */
        prodOrderItemReqProposal.setRefUUID(subProductionOrder.getUuid());
        prodOrderItemReqProposal.setItemStatus(ProductionPlanItem.STATUS_INPROCESS);
        prodPlanItemReqProposal.setRefUUID(subProductionOrder.getUuid());
        prodPlanItemReqProposal.setItemStatus(ProductionOrderItem.STATUS_INPROCESS);

        /*
         * [Step3] Generate Picking material item from proposal, binding to
         */
        // new reference material item
        ProdPickingRefMaterialItem prodPickingRefMaterialItem = prodPickingOrderManager
                .newRefMaterialItem(prodOrderItemReqProposal,
                        IDefDocumentResource.DOCUMENT_TYPE_PRODUCTORDERITEM,
                        parentPickingRefOrderItemServiceModel
                                .getProdPickingRefOrderItem(),
                        ProdPickingRefMaterialItem.ITEMSTATUS_APPROVED, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));

        /*
         * [Step3.5] Adjust generated picking mat item, and link to sub
         * production order
         */
        subProductionOrder
                .setReservedDocType(IDefDocumentResource.DOCUMENT_TYPE_PRODPICKINGORDER);
        subProductionOrder.setReservedMatItemUUID(prodPickingRefMaterialItem
                .getUuid());
        prodPickingRefMaterialItem.setRefProdOrderItemUUID(productionOrderItem
                .getUuid());

        // Pointing picking material to sub production order
        prodPickingRefMaterialItem.setRefNextOrderUUID(subProductionOrder
                .getUuid());
        prodPickingRefMaterialItem
                .setNextDocType(IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER);
        prodPickingRefMaterialItem.setNextDocMatItemUUID(subProductionOrder
                .getUuid());
        // Also set document info to document type and refUUID
        prodPickingRefMaterialItem.setDocumentType(prodPickingRefMaterialItem
                .getNextDocType());
        prodPickingRefMaterialItem.setRefUUID(prodPickingRefMaterialItem
                .getNextDocMatItemUUID());
        List<ProdPickingRefMaterialItemServiceModel> prodPickingRefMaterialItemList = parentPickingRefOrderItemServiceModel
                .getProdPickingRefMaterialItemList();
        ProdPickingRefMaterialItemServiceModel prodPickingRefMaterialItemServiceModel =
                new ProdPickingRefMaterialItemServiceModel();
        prodPickingRefMaterialItemServiceModel.setProdPickingRefMaterialItem(prodPickingRefMaterialItem);
        prodPickingRefMaterialItemList.add(prodPickingRefMaterialItemServiceModel);
        parentPickingRefOrderItemServiceModel
                .setProdPickingRefMaterialItemList(prodPickingRefMaterialItemList);

        /*
         * [Step3] Generate new sub picking order, and assign for sub
         * ProductionPlanItem List
         */
        ProdPickingOrderServiceModel subProdPickingOrderServiceModel = prodPickingOrderManager
                .newPickingOrderByProdWrapper(subProductionOrder,
                        this.pickingOrderIndex++,
                        ProdPickingOrder.STATUS_APPROVED,
                        ProdPickingOrder.PROCESSTYPE_REPLENISH);
        ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel = ProdPickingOrderManager
                .filterRefOrderItemByOrder(subProdPickingOrderServiceModel
                        .getProdPickingRefOrderItemList(), subProductionOrder
                        .getUuid());
        List<ProductionPlanItemServiceModel> subProductionPlanItemList = prodPlanItemReqProposalServiceModel
                .getSubProductionPlanItemList();
        List<ServiceModule> resultList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(subProductionPlanItemList)) {
            /*
             * [Step4] Release plan item to sub production order, generate sub
             * production order item list
             */
            List<ServiceModule> subResultList = releaseProductionPlanItem(
                    productionPlanServiceModel, subProductionOrderServiceModel,
                    prodPickingRefOrderItemServiceModel,
                    subProductionPlanItemList, logonInfo);
            if (!ServiceCollectionsHelper.checkNullList(subResultList)) {
                resultList.addAll(subResultList);
            }
        }
        /*
         * [Step5] Also need to generate target Mat item instance for sub
         * production order
         */
        List<ProdOrderTargetMatItemServiceModel> prodOrderTargetMatItemList = prodOrderTargetMatItemManager
                .newProdTargetMatItemWrapper(
                        subProductionOrderServiceModel, prodPickingRefMaterialItem, null, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));

        resultList.add(subProductionOrderServiceModel);
        resultList.add(subProdPickingOrderServiceModel);
        return resultList;
    }

    /**
     * Release To Sub Production order with reference to reference Material
     * item, from production order item proposal. Generate Sub Picking order as
     * well as sub resources recursively to this production order
     *
     * @param parentProdOrderServiceModel
     * @param prodOrderItemReqProposalServiceModel
     * @param parentPickingRefOrderItemServiceModel : reference order item reference to parent production order
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     * @throws MaterialException
     * @throws ServiceEntityInstallationException
     * @throws NodeNotFoundException
     * @throws SearchConfigureException
     * @throws ProductionOrderException
     * @throws BillOfMaterialException
     */
    private List<ServiceModule> releaseToSubProductionOrderRecursive(
            ProductionOrderServiceModel parentProdOrderServiceModel,
            ProdOrderItemReqProposalServiceModel prodOrderItemReqProposalServiceModel,
            ProdPickingRefOrderItemServiceModel parentPickingRefOrderItemServiceModel,
            ProductionOrderItem productionOrderItem, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, MaterialException,
            BillOfMaterialException, ProductionOrderException, SearchConfigureException, NodeNotFoundException,
            ServiceEntityInstallationException, ServiceComExecuteException, AuthorizationException, LogonInfoException, DocActionException {
        /*
         * [Step1] Generate sub production order from proposal's information
         */
        int documentType = IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER;
        // new reference material item and bind to newly create production order
        // instance
        ProdPickingRefMaterialItem prodPickingRefMaterialItem = prodPickingOrderManager
                .newRefMaterialItem(prodOrderItemReqProposalServiceModel
                                .getProdOrderItemReqProposal(),
                        parentPickingRefOrderItemServiceModel
                                .getProdPickingRefOrderItem(),
                        ProdPickingRefMaterialItem.ITEMSTATUS_APPROVED, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
        // Get deep construct production order service model
        ProductionOrderServiceModel subProductionOrderServiceModel = this
                .genUnionProductionOrder(prodPickingRefMaterialItem, logonInfo);
        ProductionOrder subProductionOrder = subProductionOrderServiceModel
                .getProductionOrder();
        if (parentProdOrderServiceModel != null) {
            if (parentProdOrderServiceModel.getProductionOrder() != null) {
                subProductionOrder.setRefPlanUUID(parentProdOrderServiceModel
                        .getProductionOrder().getRefPlanUUID());
            }
        }
        List<ServiceEntityNode> prodOrderSupplyWarehouseList = parentProdOrderServiceModel
                .getProdOrderSupplyWarehouseList();
        subProductionOrder.setStatus(ProductionOrder.STATUS_APPROVED);
        subProductionOrderServiceModel
                .setProdOrderSupplyWarehouseList(prodOrderSupplyWarehouseList);
        prodPickingRefMaterialItem.setRefProdOrderItemUUID(productionOrderItem
                .getUuid());
        prodPickingRefMaterialItem.setRefNextOrderUUID(subProductionOrder
                .getUuid());
        prodPickingRefMaterialItem.setNextDocType(documentType);
        prodPickingRefMaterialItem.setNextDocMatItemUUID(subProductionOrder
                .getUuid());
        // Also set document info to document type and refUUID
        prodPickingRefMaterialItem.setDocumentType(prodPickingRefMaterialItem
                .getNextDocType());
        prodPickingRefMaterialItem.setRefUUID(prodPickingRefMaterialItem
                .getNextDocMatItemUUID());
        List<ProdPickingRefMaterialItemServiceModel> prodPickingRefMaterialItemList = parentPickingRefOrderItemServiceModel
                .getProdPickingRefMaterialItemList();
        ProdPickingRefMaterialItemServiceModel prodPickingRefMaterialItemServiceModel =
                new ProdPickingRefMaterialItemServiceModel();
        prodPickingRefMaterialItemServiceModel.setProdPickingRefMaterialItem(prodPickingRefMaterialItem);
        prodPickingRefMaterialItemList.add(prodPickingRefMaterialItemServiceModel);
        parentPickingRefOrderItemServiceModel
                .setProdPickingRefMaterialItemList(prodPickingRefMaterialItemList);

        /*
         * [Step2] Generate new sub picking order, and assign for sub
         * ProductionPlanItem List
         */
        ProdPickingOrderServiceModel subProdPickingOrderServiceModel = prodPickingOrderManager
                .newPickingOrderByProdWrapper(subProductionOrder,
                        this.pickingOrderIndex++,
                        ProdPickingOrder.STATUS_INITIAL,
                        ProdPickingOrder.PROCESSTYPE_REPLENISH);
        ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel = ProdPickingOrderManager
                .filterRefOrderItemByOrder(subProdPickingOrderServiceModel
                        .getProdPickingRefOrderItemList(), subProductionOrder
                        .getUuid());
        List<ProductionOrderItemServiceModel> subProductionOrderItemList = prodOrderItemReqProposalServiceModel
                .getProductionOrderItemList();
        List<ServiceModule> resultList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(subProductionOrderItemList)) {
            List<ServiceModule> subResultList = deepReleaseProductionServiceModel(
                    parentProdOrderServiceModel, subProductionOrderItemList,
                    prodPickingRefOrderItemServiceModel, logonInfo);
            if (!ServiceCollectionsHelper.checkNullList(subResultList)) {
                resultList.addAll(subResultList);
            }
        }
        List<ProdOrderTargetMatItemServiceModel> prodOrderTargetMatItemList = prodOrderTargetMatItemManager
                .newProdTargetMatItemWrapper(
                        subProductionOrderServiceModel, prodPickingRefMaterialItem, null, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
        resultList.add(subProductionOrderServiceModel);
        resultList.add(subProdPickingOrderServiceModel);
        return resultList;
    }

    /**
     * Logic to get max self LeadTime
     *
     * @param productionPlanItemList
     * @return
     */
    public double _getMaxSelfLeadTime(
            List<ProductionPlanItemServiceModel> productionPlanItemList) {
        double maxLeadTime = 0;
        if (!ServiceCollectionsHelper.checkNullList(productionPlanItemList)) {
            for (ProductionPlanItemServiceModel productionPlanItemServiceModel : productionPlanItemList) {
                if (maxLeadTime < productionPlanItemServiceModel
                        .getProductionPlanItem().getSelfLeadTime()) {
                    maxLeadTime = productionPlanItemServiceModel
                            .getProductionPlanItem().getSelfLeadTime();
                }
            }
        }
        return maxLeadTime;
    }

    public List<ServiceModule> releaseProductionPlanItem(
            ProductionPlanServiceModel productionPlanServiceModel,
            ProductionOrderServiceModel productionOrderServiceModel,
            ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel,
            List<ProductionPlanItemServiceModel> productionPlanItemList,
            LogonInfo logonInfo) throws ServiceEntityConfigureException, ServiceModuleProxyException, MaterialException, BillOfMaterialException, ProductionOrderException, ServiceComExecuteException, DocActionException {
        /*
         * [Step1] register production order to picking order reference order
         * item and to service model
         */
        List<ProductionOrderItemServiceModel> productionOrderItemList = new ArrayList<>();
        /*
         * [Step2] traverse each plan item: generate order item, and picking
         * order from sub proposal
         */
        List<ServiceModule> resultList = new ArrayList<>();
        int itemIndex = 1;
        if (!ServiceCollectionsHelper.checkNullList(productionPlanItemList)) {
            for (ProductionPlanItemServiceModel productionPlanItemServiceModel : productionPlanItemList) {
                /*
                 * [Step 2.1] generate production order item
                 */
                ProductionOrderItemServiceModel productionOrderItemServiceModel = new ProductionOrderItemServiceModel();
                ProductionOrderItem productionOrderItem = productionOrderItemManager
                        .generateProductionOrderItemFromPlanItem(
                                productionPlanItemServiceModel
                                        .getProductionPlanItem(),
                                productionOrderServiceModel
                                        .getProductionOrder(), itemIndex++, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
                productionOrderItemServiceModel
                        .setProductionOrderItem(productionOrderItem);
                productionOrderItemList.add(productionOrderItemServiceModel);
                productionOrderServiceModel
                        .setProductionOrderItemList(productionOrderItemList);

                /*
                 * [Step 2.5] traverse each plan proposal, generate picking
                 */
                List<ProdPlanItemReqProposalServiceModel> proposalServiceModelList = productionPlanItemServiceModel
                        .getProdPlanItemReqProposalList();
                for (ProdPlanItemReqProposalServiceModel prodPlanItemReqProposalServiceModel : proposalServiceModelList) {
                    ProdPlanItemReqProposal prodPlanItemReqProposal = prodPlanItemReqProposalServiceModel
                            .getProdPlanItemReqProposal();
                    int documentType = prodPlanItemReqProposalServiceModel
                            .getProdPlanItemReqProposal().getDocumentType();
                    if (documentType == IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY) {
                        // In case out bound directly from warehouse
                        List<ServiceModule> tmpResultList = this
                                .releaseToOutDeliveryWrapper(
                                        prodPlanItemReqProposal,
                                        prodPickingRefOrderItemServiceModel,
                                        productionOrderItemServiceModel, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
                        if (!ServiceCollectionsHelper
                                .checkNullList(tmpResultList)) {
                            resultList.addAll(tmpResultList);
                        }
                    }
                    if (documentType == IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT
                            || documentType == IDefDocumentResource.DOCUMENT_TYPE_PURCHASE) {
                        // In case generate purchase contract
                        List<ServiceModule> tmpResultList = this
                                .releaseToPurchaseContractWrapper(
                                        prodPlanItemReqProposal,
                                        prodPickingRefOrderItemServiceModel,
                                        productionOrderItemServiceModel, logonInfo);
                        if (!ServiceCollectionsHelper
                                .checkNullList(tmpResultList)) {
                            resultList.addAll(tmpResultList);
                        }
                    }
                    if (documentType == IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER) {
                        // In case generate sub production order
                        List<ServiceModule> tmpResultList = this
                                .releaseToSubProductionOrderRecursive(
                                        productionPlanServiceModel,
                                        productionPlanItemList,
                                        prodPlanItemReqProposalServiceModel,
                                        prodPickingRefOrderItemServiceModel,
                                        productionOrderItemServiceModel,
                                        logonInfo);
                        if (!ServiceCollectionsHelper
                                .checkNullList(tmpResultList)) {
                            resultList.addAll(tmpResultList);
                        }
                    }
                }
            }
        }
        return resultList;
    }

    /**
     * Batch release the deep prodOrderItem Proposal into sub production order,
     * purchase order and out bound delivery as well as generate relative
     * picking material item
     *
     * @param productionOrderItemServiceModelList
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     * @throws ServiceModuleProxyException
     * @throws ServiceEntityInstallationException
     * @throws NodeNotFoundException
     * @throws SearchConfigureException
     * @throws ProductionOrderException
     * @throws BillOfMaterialException
     */
    public List<ServiceModule> deepReleaseProductionServiceModel(
            ProductionOrderServiceModel parentProdOrderServiceModel,
            List<ProductionOrderItemServiceModel> productionOrderItemServiceModelList,
            ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel,
            LogonInfo logonInfo)
            throws ServiceModuleProxyException, MaterialException, ServiceEntityConfigureException,
            BillOfMaterialException, ProductionOrderException, ServiceComExecuteException, NodeNotFoundException, AuthorizationException, ServiceEntityInstallationException, SearchConfigureException, LogonInfoException, DocActionException {
        if (!ServiceCollectionsHelper
                .checkNullList(productionOrderItemServiceModelList)) {
            List<ServiceModule> resultList = new ArrayList<>();
            for (ProductionOrderItemServiceModel productionOrderItemServiceModel : productionOrderItemServiceModelList) {
                ProductionOrderItem productionOrderItem = productionOrderItemServiceModel
                        .getProductionOrderItem();
                if (!ServiceCollectionsHelper
                        .checkNullList(productionOrderItemServiceModel
                                .getProdOrderItemReqProposalList())) {
                    for (ProdOrderItemReqProposalServiceModel prodOrderItemReqProposaServiceModel : productionOrderItemServiceModel
                            .getProdOrderItemReqProposalList()) {
                        int documentType = prodOrderItemReqProposaServiceModel
                                .getProdOrderItemReqProposal()
                                .getDocumentType();
                        if (documentType == IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY) {
                            // In case out bound directly from warehouse
                            List<ServiceModule> tmpResultList = this
                                    .releaseToOutDeliveryWrapper(
                                            prodOrderItemReqProposaServiceModel
                                                    .getProdOrderItemReqProposal(),
                                            prodPickingRefOrderItemServiceModel,
                                            productionOrderItemServiceModel
                                                    .getProductionOrderItem(), LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
                            if (!ServiceCollectionsHelper
                                    .checkNullList(tmpResultList)) {
                                resultList.addAll(tmpResultList);
                            }
                        }
                        if (documentType == IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT
                                || documentType == IDefDocumentResource.DOCUMENT_TYPE_PURCHASE) {
                            // In case generate purchase contract
                            List<ServiceModule> tmpResultList = this
                                    .releaseToPurchaseContractWrapper(
                                            prodOrderItemReqProposaServiceModel
                                                    .getProdOrderItemReqProposal(),
                                            prodPickingRefOrderItemServiceModel,
                                            productionOrderItemServiceModel
                                                    .getProductionOrderItem(),
                                            logonInfo);
                            if (!ServiceCollectionsHelper
                                    .checkNullList(tmpResultList)) {
                                resultList.addAll(tmpResultList);
                            }

                        }
                        if (documentType == IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER) {
                            // In case generate sub production order
                            List<ServiceModule> tmpResultList = this
                                    .releaseToSubProductionOrderRecursive(
                                            parentProdOrderServiceModel,
                                            prodOrderItemReqProposaServiceModel,
                                            prodPickingRefOrderItemServiceModel,
                                            productionOrderItem, logonInfo);
                            if (!ServiceCollectionsHelper
                                    .checkNullList(tmpResultList)) {
                                resultList.addAll(tmpResultList);
                            }
                        }
                    }
                }
            }
            return resultList;
        }
        return null;
    }

}
