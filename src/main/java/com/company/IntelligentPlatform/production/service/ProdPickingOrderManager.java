package com.company.IntelligentPlatform.production.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.service.*;
import com.company.IntelligentPlatform.logistics.service.PurchaseContractManager;
import com.company.IntelligentPlatform.logistics.service.PurchaseContractServiceModel;
import com.company.IntelligentPlatform.logistics.model.InboundDelivery;
import com.company.IntelligentPlatform.logistics.model.OutboundDelivery;
import com.company.IntelligentPlatform.production.dto.*;
import com.company.IntelligentPlatform.production.repository.ProdPickingOrderRepository;
import com.company.IntelligentPlatform.production.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
@Transactional
public class ProdPickingOrderManager extends ServiceEntityManager {

    public static final String METHOD_ConvProdPickingOrderToUI = "convProdPickingOrderToUI";

    public static final String METHOD_ConvUIToProdPickingOrder = "convUIToProdPickingOrder";

    public static final String METHOD_ConvApproveByToOrder = "convApproveByToOrder";

    public static final String METHOD_ConvProcessByToOrder = "convProcessByToOrder";

    public static final String METHOD_ConvPrevDocToMatItemUI = "convPrevDocToMatItemUI";

    public static final String METHOD_ConvNextDocToMatItemUI = "convNextDocToMatItemUI";

    public static final String METHOD_ConvOutboundToMatItemUI = "convOutboundToMatItemUI";

    public static final String METHOD_ConvInboundToMatItemUI = "convInboundToMatItemUI";

    public static final String METHOD_ConvPrevMatItemToUI = "convPrevMatItemToUI";

    public static final String METHOD_ConvProdPickingRefOrderItemToUI = "convProdPickingRefOrderItemToUI";

    public static final String METHOD_ConvUIToProdPickingRefOrderItem = "convUIToProdPickingRefOrderItem";

    public static final String METHOD_ConvProductionOrderToUI = "convProductionOrderToUI";

    public static final String METHOD_ConvOrderMaterialStockKeepUnitToUI = "convOrderMaterialStockKeepUnitToUI";

    private Map<String, Map<Integer, String>> categoryMapLan = new HashMap<>();

    private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

    private Map<String, Map<Integer, String>> priorityCodeMapLan = new HashMap<>();

    private Map<String, Map<Integer, String>> itemStatusMapLan = new HashMap<>();

    private Map<String, Map<Integer, String>> processTypeMapLan = new HashMap<>();

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected BsearchService bsearchService;
    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    protected ProdPickingOrderRepository prodPickingOrderDAO;

    @Autowired
    protected ProdPickingOrderConfigureProxy prodPickingOrderConfigureProxy;

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected ProductionOrderManager productionOrderManager;

    @Autowired
    protected ProductionOrderItemManager productionOrderItemManager;

    @Autowired
    protected ProdPickingRefOrderltemManager prodPickingRefOrderltemManager;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected WarehouseManager warehouseManager;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ProdPickingOrderIdHelper prodPickingOrderIdHelper;

    @Autowired
    protected ProdPickingRefMaterialItemManager prodPickingRefMaterialItemManager;

    @Autowired
    protected BillOfMaterialOrderManager billOfMaterialOrderManager;

    @Autowired
    protected ProductionReleaseProxy productionReleaseProxy;

    @Autowired
    protected OutboundDeliveryManager outboundDeliveryManager;

    @Autowired
    protected InboundDeliveryManager inboundDeliveryManager;

    @Autowired
    protected PurchaseContractManager purchaseContractManager;

    @Autowired
    protected StandardPriorityProxy standardPriorityProxy;

    @Autowired
    protected ProdPickingOrderServiceUIModelExtension prodPickingOrderServiceUIModelExtension;

    @Autowired
    protected ProdPickingRefMaterialItemServiceUIModelExtension prodPickingRefMaterialItemServiceUIModelExtension;

    @Autowired
    protected ProdPickingOrderSearchProxy prodPickingOrderSearchProxy;

    @Autowired
    protected DocActionNodeProxy docActionNodeProxy;
    @Autowired
    private LogonInfoManager logonInfoManager;
    @Autowired
    private SerialIdDocumentProxy serialIdDocumentProxy;

    @Override
    public ServiceEntityNode newRootEntityNode(String client) throws ServiceEntityConfigureException {
        ProdPickingOrder prodPickingOrder = (ProdPickingOrder) super.newRootEntityNode(client);
        String pickingOrderID = prodPickingOrderIdHelper.genDefaultId(client);
        prodPickingOrder.setId(pickingOrderID);
        return prodPickingOrder;
    }

    /**
     * New Root Entity (Picking Order) instance for generated from production
     * order template
     */
    public ProdPickingOrder newRootEntityForProdOrder(String client) throws ServiceEntityConfigureException {
        ProdPickingOrder prodPickingOrder = (ProdPickingOrder) newRootEntityNode(client);
        prodPickingOrder.setCategory(ProdPickingOrder.CATEGORY_PRODORDER);
        prodPickingOrder.setProcessType(ProdPickingOrder.PROCESSTYPE_INPLAN);
        return prodPickingOrder;
    }

    public List<PageHeaderModel> getPageHeaderModelList(ProdPickingOrder prodPickingOrder, String client)
            throws ServiceEntityConfigureException {
        int index = 0;
        List<PageHeaderModel> resultList = new ArrayList<>();
        if (prodPickingOrder != null) {
            PageHeaderModel itemHeaderModel = getPageHeaderModel(prodPickingOrder, index);
            if (itemHeaderModel != null) {
                resultList.add(itemHeaderModel);
            }
        }
        return resultList;
    }

    protected PageHeaderModel getPageHeaderModel(ProdPickingOrder prodPickingOrder, int index)
            throws ServiceEntityConfigureException {
        if (prodPickingOrder == null) {
            return null;
        }
        PageHeaderModel pageHeaderModel = new PageHeaderModel();
        pageHeaderModel.setPageTitle("prodPickingOrderPageTitle");
        pageHeaderModel.setNodeInstId(ProdPickingOrder.SENAME);
        pageHeaderModel.setUuid(prodPickingOrder.getUuid());
        pageHeaderModel.setHeaderName(prodPickingOrder.getId());
        pageHeaderModel.setIndex(index);
        return pageHeaderModel;
    }

    /**
     * Core Logic to approve salesReturnOrder and update to DB
     *
     */
    @Deprecated
    public void rejectApproveService(
            ProdPickingOrderServiceModel prodPickingOrderServiceModel,
            String logonUserUUID, String organizationUUID)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        this.executeActionCore(prodPickingOrderServiceModel,
                ServiceCollectionsHelper.asList(ProdPickingOrder.STATUS_INITIAL), ProdPickingOrder.STATUS_REJECT_APPROVAL,
                ProdPickingOrderActionNode.DOC_ACTION_REJECT_APPROVE, null, logonUserUUID, organizationUUID);
    }

    /**
     * Logic for approve approve PickingOrder
     *
     */
    public void approvePickingOrder(ProdPickingOrderServiceModel prodPickingOrderServiceModel, LogonInfo logonInfo) throws ServiceModuleProxyException, ServiceEntityConfigureException {
        this.executeActionCore(prodPickingOrderServiceModel,
                ServiceCollectionsHelper.asList(ProdPickingOrder.STATUS_INITIAL), ProdPickingOrder.STATUS_APPROVED,
                ProdPickingOrderActionNode.DOC_ACTION_APPROVE, prodPickingRefMaterialItemExecuteUnion -> {
                    approvePickingRefMaterialItem(prodPickingRefMaterialItemExecuteUnion, logonInfo);
                    return prodPickingRefMaterialItemExecuteUnion.getProdPickingRefMaterialItem();
                }, logonInfo.getRefUserUUID(),
                logonInfo.getResOrgUUID());
    }

    /**
     * Logic for approve approve PickingOrder
     *
     */
    @Deprecated
    public void inProcessPickingOrder(ProdPickingOrderServiceModel prodPickingOrderServiceModel, String logonUserUUID,
                                      String organizationUUID) throws ServiceModuleProxyException, ServiceEntityConfigureException {
        this.executeActionCore(prodPickingOrderServiceModel,
                ServiceCollectionsHelper.asList(ProdPickingOrder.STATUS_APPROVED), ProdPickingOrder.STATUS_INPROCESS,
                ProdPickingOrderActionNode.DOC_ACTION_INPROCESS, prodPickingRefMaterialItemExecuteUnion -> {
                    inProcessPickingRefMaterialItem(prodPickingRefMaterialItemExecuteUnion);
                    return prodPickingRefMaterialItemExecuteUnion.getProdPickingRefMaterialItem();
                }, logonUserUUID,
                organizationUUID);

    }

    /**
     * Logic for approve approve PickingOrder
     *
     */
    @Deprecated
    public void setFinishPickingOrder(ProdPickingOrderServiceModel prodPickingOrderServiceModel, String logonUserUUID,
                                      String organizationUUID) throws ServiceEntityConfigureException, ServiceModuleProxyException {
        this.executeActionCore(prodPickingOrderServiceModel,
                ServiceCollectionsHelper.asList(ProdPickingOrder.STATUS_APPROVED, ProdPickingOrder.STATUS_INPROCESS),
                ProdPickingOrder.STATUS_DELIVERYDONE,
                ProdPickingOrderActionNode.DOC_ACTION_DELIVERY_DONE, prodPickingRefMaterialItemExecuteUnion -> {
                    try {
                        setFinishPickingRefMaterialItem(prodPickingRefMaterialItemExecuteUnion, true);
                    } catch (ServiceEntityConfigureException e) {
                        logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    }
                    return prodPickingRefMaterialItemExecuteUnion.getProdPickingRefMaterialItem();
                }, logonUserUUID,
                organizationUUID);
    }

    private void approvePickingRefMaterialItem(ProdPickingRefMaterialItemExecuteUnion prodPickingRefMaterialItemExecuteUnion, LogonInfo logonInfo) {
        approvePickingRefMaterialItem(prodPickingRefMaterialItemExecuteUnion.getProdPickingRefMaterialItem(),
                prodPickingRefMaterialItemExecuteUnion.getProdPickingRefOrderItemServiceModel(),
                logonInfo);
    }

    public void approvePickingRefMaterialItem(ProdPickingRefMaterialItem prodPickingRefMaterialItem,
                                              ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel, LogonInfo logonInfo) {
        /*
         * Avoid the duplicate generate previous resources.
         */
        ProdPickingRefOrderItem prodPickingRefOrderItem = prodPickingRefOrderItemServiceModel.getProdPickingRefOrderItem();
        if (prodPickingRefMaterialItem.getNextDocType() > 0 && ServiceEntityStringHelper
                .checkNullString(prodPickingRefMaterialItem.getNextDocMatItemUUID())) {
            if (prodPickingRefMaterialItem.getNextDocType() == IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER) {
                try {
                    ProductionOrder parentProductionOrder = (ProductionOrder) productionOrderManager
                            .getEntityNodeByKey(prodPickingRefOrderItem.getRefProdOrderUUID(), IServiceEntityNodeFieldConstant.UUID,
                                    ProductionOrder.NODENAME, null);
                    ProductionOrderServiceModel parentProductionOrderServiceModel = (ProductionOrderServiceModel) productionOrderManager
                            .loadServiceModule(ProductionOrderServiceModel.class, parentProductionOrder);
                    /*
                     * [Step2] Generate sub production order
                     */
                    ProductionOrderServiceModel subProductionOrderServiceModel = productionReleaseProxy
                            .genUnionProductionOrder(prodPickingRefMaterialItem, logonInfo);
                    /*
                     * [Step3] Release resources
                     */
                    productionReleaseProxy
                            .releaseProductionOrderWrapper(parentProductionOrderServiceModel, subProductionOrderServiceModel,
                                    prodPickingRefMaterialItem, logonInfo);
                } catch (BillOfMaterialException | MaterialException | ProductionOrderException | SearchConfigureException | ServiceEntityConfigureException | NodeNotFoundException | ServiceEntityInstallationException | ServiceComExecuteException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""), e);
                } catch (ServiceModuleProxyException | DocActionException | AuthorizationException |
                         LogonInfoException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""), e);
                }
            }
            if (prodPickingRefMaterialItem.getNextDocType() == IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY) {
                try {
                    ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) productionOrderManager
                            .getEntityNodeByKey(prodPickingRefMaterialItem.getPrevDocMatItemUUID(),
                                    IServiceEntityNodeFieldConstant.UUID, ProdOrderItemReqProposal.NODENAME, prodPickingRefMaterialItem.getClient(), null);
                    OutboundDeliveryServiceModel outboundDeliveryServiceModel = productionReleaseProxy
                            .genOutboundFromRefMaterialItem(prodPickingRefMaterialItem, prodOrderItemReqProposal, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
                    outboundDeliveryManager
                            .updateServiceModuleWithDelete(OutboundDeliveryServiceModel.class, outboundDeliveryServiceModel,
                                    logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
                } catch (ServiceEntityConfigureException | ServiceModuleProxyException | DocActionException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""), e);
                }
            }
            if (prodPickingRefMaterialItem.getNextDocType() == IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY) {
                try {
                    InboundDeliveryServiceModel inboundDeliveryServiceModel = productionReleaseProxy
                            .genInboundFromRefMaterialItem(prodPickingRefMaterialItem, null, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
                    inboundDeliveryManager
                            .updateServiceModuleWithDelete(InboundDeliveryServiceModel.class,
                                    inboundDeliveryServiceModel, logonInfo.getRefUserUUID(),
                                    logonInfo.getResOrgUUID());
                } catch (ServiceEntityConfigureException | ServiceModuleProxyException | DocActionException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""), e);
                }
            }
            if (prodPickingRefMaterialItem.getNextDocType() == IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT
                    || prodPickingRefMaterialItem.getNextDocType() == IDefDocumentResource.DOCUMENT_TYPE_PURCHASE) {
                try {
                    ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) productionOrderManager
                            .getEntityNodeByKey(prodPickingRefMaterialItem.getPrevDocMatItemUUID(),
                                    IServiceEntityNodeFieldConstant.UUID, ProdOrderItemReqProposal.NODENAME, prodPickingRefMaterialItem.getClient(), null);
                    ProductionOrder productionOrder = getRefProductionOrder(prodPickingRefOrderItem);
                    PurchaseContractServiceModel purchaseContractServiceModel = productionReleaseProxy
                            .genUnionPurchaseContractFromRefMaterialItem(prodPickingRefMaterialItem, null,
                                    productionOrder.getUuid(), new LogonInfo(logonInfo.getRefUserUUID(),
                                            productionOrder.getClient()));
                    purchaseContractManager
                            .updateServiceModuleWithDelete(PurchaseContractServiceModel.class, purchaseContractServiceModel,
                                    logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
                } catch (ServiceEntityConfigureException | ServiceModuleProxyException | DocActionException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                }
            }
        }
        ProdPickingRefMaterialItem prodPickingRefMaterialItemBack = (ProdPickingRefMaterialItem) prodPickingRefMaterialItem.clone();
        prodPickingRefMaterialItem.setItemStatus(ProdPickingOrder.STATUS_APPROVED);
        updateSENode(prodPickingRefMaterialItem, prodPickingRefMaterialItemBack, logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
    }

    @Deprecated
    public void inProcessPickingRefMaterialItem(ProdPickingRefMaterialItemExecuteUnion prodPickingRefMaterialItemExecuteUnion) {
        inProcessPickingRefMaterialItem(prodPickingRefMaterialItemExecuteUnion.getProdPickingRefMaterialItem(),
                prodPickingRefMaterialItemExecuteUnion.getProdPickingRefOrderItemServiceModel().getProdPickingRefOrderItem(),
                prodPickingRefMaterialItemExecuteUnion.getLogonUserUUID(),
                prodPickingRefMaterialItemExecuteUnion.getOrganizationUUID());
    }

    /**
     * Logic to set picking order in process state
     *
     */

    @Deprecated
    public void inProcessPickingRefMaterialItem(ProdPickingRefMaterialItem prodPickingRefMaterialItem,
                                                ProdPickingRefOrderItem prodPickingRefOrderItem, String logonUserUUID, String organizationUUID) {
        ProdPickingRefMaterialItem prodPickingRefMaterialItemBack = (ProdPickingRefMaterialItem) prodPickingRefMaterialItem.clone();
        prodPickingRefMaterialItem.setItemStatus(ProdPickingOrder.STATUS_INPROCESS);
        updateSENode(prodPickingRefMaterialItem, prodPickingRefMaterialItemBack, logonUserUUID, organizationUUID);
    }

    public void setFinishPickingRefMaterialItem(ProdPickingRefMaterialItemExecuteUnion prodPickingRefMaterialItemExecuteUnion, boolean batchFlag) throws ServiceEntityConfigureException {
        setFinishPickingRefMaterialItem(prodPickingRefMaterialItemExecuteUnion.getProdPickingRefMaterialItem(),
                prodPickingRefMaterialItemExecuteUnion.getProdPickingOrder(), batchFlag,
                prodPickingRefMaterialItemExecuteUnion.getLogonUserUUID(),
                prodPickingRefMaterialItemExecuteUnion.getOrganizationUUID());
    }

    /**
     * Core Logic to set one Picking material item status as [finished]
     *
     * @param batchFlag                  : Indicator weather this logic is triggered from order level:
     *                                   If the value is: <code>true</code>, means this logic is
     *                                   triggered from order level; If the value is:
     *                                   <code>false</code>, means this logic is triggered from item
     *                                   level;
     */
    public void setFinishPickingRefMaterialItem(ProdPickingRefMaterialItem prodPickingRefMaterialItem,
                                                ProdPickingOrder prodPickingOrder, boolean batchFlag, String logonUserUUID, String organizationUUID)
            throws ServiceEntityConfigureException {
        /*
         * [Step1] Update status of item itself.
         */
        ProdPickingRefMaterialItem prodPickingRefMaterialItemBack = (ProdPickingRefMaterialItem) prodPickingRefMaterialItem.clone();
        prodPickingRefMaterialItem.setItemStatus(ProdPickingOrder.STATUS_DELIVERYDONE);
        updateSENode(prodPickingRefMaterialItem, prodPickingRefMaterialItemBack, logonUserUUID, organizationUUID);
        /*
         * [Step2] Update production order item status
         */
        productionOrderItemManager.updateItemStatusFromPicking(prodPickingRefMaterialItem, logonUserUUID, organizationUUID);
        /*
         * [Step2] update order status if triggered from item level
         */
        if (!batchFlag) {
            if (prodPickingOrder.getStatus() == ProdPickingOrder.STATUS_APPROVED) {
                prodPickingOrder.setStatus(ProdPickingOrder.STATUS_INPROCESS);
                updateSENode(prodPickingOrder, logonUserUUID, organizationUUID);
            }
            checkSetFinishOrder(prodPickingOrder, logonUserUUID, organizationUUID);
        }
    }

    /**
     * Wrapper method to auto check and set finish for whole picking order
     *
     */
    public void checkSetFinishOrder(ProdPickingOrder prodPickingOrder, String logonUserUUID, String organizationUUID) throws ServiceEntityConfigureException {
        if (prodPickingOrder == null) {
            return;
        }
        List<ServiceEntityNode> allProdPickingMaterialItemList = this.getEntityNodeListByKey(prodPickingOrder.getUuid(),
                IServiceEntityNodeFieldConstant.ROOTNODEUUID, ProdPickingRefMaterialItem.NODENAME,
                prodPickingOrder.getClient(), null);
        if (ServiceCollectionsHelper.checkNullList(allProdPickingMaterialItemList)) {
            return;
        }
        for (ServiceEntityNode serviceEntityNode : allProdPickingMaterialItemList) {
            ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) serviceEntityNode;
            if (prodPickingRefMaterialItem.getItemStatus() != ProdPickingOrder.STATUS_DELIVERYDONE) {
                return;
            }
        }
        // in case all the item is marked as done
//        prodPickingOrder.setStatus(ProdPickingOrder.STATUS_FINISHED);
//        updateSENode(prodPickingOrder, logonUserUUID, organizationUUID);
        // Create new service model only contains root node
        ProdPickingOrderServiceModel prodPickingOrderServiceModel = new ProdPickingOrderServiceModel();
        prodPickingOrderServiceModel.setProdPickingOrder(prodPickingOrder);
        _executeActionCore(prodPickingOrderServiceModel, ProdPickingOrder.STATUS_DELIVERYDONE, ProdPickingOrderActionNode.DOC_ACTION_DELIVERY_DONE, logonUserUUID,
                organizationUUID);
    }

    public List<ServiceEntityNode> batchSetItemFinishList(DeliveryMatItemBatchGenRequest genRequest, String client,
                                                          LogonInfo logonInfo)
            throws ServiceEntityConfigureException,
            SearchConfigureException, ProductionOrderException, NodeNotFoundException, ServiceEntityInstallationException, AuthorizationException, LogonInfoException {
        /*
         * [Step1] prepare raw data
         */
        ProdPickingRefMaterialItemSearchModel searchModel = new ProdPickingRefMaterialItemSearchModel();
        searchModel.setUuid(ServiceEntityStringHelper.convStringListIntoMultiStringValue(genRequest.getUuidList()));
        if (ServiceCollectionsHelper.checkNullList(genRequest.getUuidList())) {
            throw new ProductionOrderException(ProductionOrderException.PARA_NO_PICKITEM, "");
        }
        SearchContextBuilder searchContextBuilder = SearchContextBuilder.genBuilder(logonInfo).searchModel(searchModel);
        List<ServiceEntityNode> prodPickingMatItemList = this.getSearchProxy().searchItemList(searchContextBuilder.build()).getResultList();
        if (ServiceCollectionsHelper.checkNullList(prodPickingMatItemList)) {
            throw new ProductionOrderException(ProductionOrderException.PARA_NO_PICKITEM, "");
        }
        /*
         * [Step2] Group by picking order
         */
        Map<String, List<ServiceEntityNode>> pickingMatItemMap =
                DocFlowProxy.mapMaterialItemListByRoot(prodPickingMatItemList);
        if(pickingMatItemMap == null){
            return null;
        }
        Set<String> keySets = pickingMatItemMap.keySet();
        List<ServiceEntityNode> resultList = new ArrayList<>();
        for (String rootNodeUUID : keySets) {
            List<ServiceEntityNode> subPickingMatItemList = pickingMatItemMap.get(rootNodeUUID);
            if (ServiceCollectionsHelper.checkNullList(subPickingMatItemList)) {
                continue;
            }
            ProdPickingOrder prodPickingOrder = (ProdPickingOrder) this.getEntityNodeByKey(rootNodeUUID,
                    IServiceEntityNodeFieldConstant.UUID, ProdPickingOrder.NODENAME, client, null);
            for (ServiceEntityNode serviceEntityNode : subPickingMatItemList) {
                ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) serviceEntityNode;
                setFinishPickingRefMaterialItem(prodPickingRefMaterialItem, prodPickingOrder, true, logonInfo.getRefUserUUID(),
                        logonInfo.getResOrgUUID());
                ServiceCollectionsHelper.mergeToList(resultList, prodPickingRefMaterialItem);
            }
            checkSetFinishOrder(prodPickingOrder, logonInfo.getRefUserUUID(),
                    logonInfo.getResOrgUUID());
        }
        return resultList;

    }

    private static class ProdPickingRefMaterialItemExecuteUnion {

        private ProdPickingRefMaterialItem prodPickingRefMaterialItem;

        private ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel;

        private ProdPickingOrder prodPickingOrder;

        private String logonUserUUID;

        private String organizationUUID;

        public ProdPickingRefMaterialItemExecuteUnion() {

        }

        public ProdPickingRefMaterialItemExecuteUnion(ProdPickingRefMaterialItem prodPickingRefMaterialItem,
                                                      ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel,
                                                      ProdPickingOrder prodPickingOrder,
                                                      String logonUserUUID, String organizationUUID) {
            this.prodPickingRefMaterialItem = prodPickingRefMaterialItem;
            this.prodPickingRefOrderItemServiceModel = prodPickingRefOrderItemServiceModel;
            this.prodPickingOrder = prodPickingOrder;
            this.logonUserUUID = logonUserUUID;
            this.organizationUUID = organizationUUID;
        }

        public ProdPickingRefMaterialItem getProdPickingRefMaterialItem() {
            return prodPickingRefMaterialItem;
        }

        public void setProdPickingRefMaterialItem(ProdPickingRefMaterialItem prodPickingRefMaterialItem) {
            this.prodPickingRefMaterialItem = prodPickingRefMaterialItem;
        }

        public String getLogonUserUUID() {
            return logonUserUUID;
        }

        public void setLogonUserUUID(String logonUserUUID) {
            this.logonUserUUID = logonUserUUID;
        }

        public ProdPickingRefOrderItemServiceModel getProdPickingRefOrderItemServiceModel() {
            return prodPickingRefOrderItemServiceModel;
        }

        public ProdPickingOrder getProdPickingOrder() {
            return prodPickingOrder;
        }

        public void setProdPickingOrder(ProdPickingOrder prodPickingOrder) {
            this.prodPickingOrder = prodPickingOrder;
        }

        public void setProdPickingRefOrderItemServiceModel(ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel) {
            this.prodPickingRefOrderItemServiceModel = prodPickingRefOrderItemServiceModel;
        }

        public String getOrganizationUUID() {
            return organizationUUID;
        }

        public void setOrganizationUUID(String organizationUUID) {
            this.organizationUUID = organizationUUID;
        }
    }

    /**
     * [Internal method] Core Logic to update root status and update action code
     */
    private void _executeActionCore(ProdPickingOrderServiceModel prodPickingOrderServiceModel, int targetStatus, int actionCode, String logonUserUUID, String organizationUUID) throws ServiceEntityConfigureException {
        ProdPickingOrder prodPickingOrder = prodPickingOrderServiceModel
                .getProdPickingOrder();
        prodPickingOrder.setStatus(targetStatus);
        docActionNodeProxy.updateDocActionWrapper(actionCode,
                ProdPickingOrderActionNode.NODENAME, null, IDefDocumentResource.DOCUMENT_TYPE_PRODPICKINGORDER, this,
                prodPickingOrderServiceModel,
                prodPickingOrder,
                logonUserUUID,
                organizationUUID);
    }

    /**
     * Core Logic to approve prodPickingOrder and update to DB
     *
     */
    @Deprecated
    public void executeActionCore(
            ProdPickingOrderServiceModel prodPickingOrderServiceModel, List<Integer> curStatusList,
            int targetStatus,
            int actionCode,
            Function<ProdPickingRefMaterialItemExecuteUnion, ProdPickingRefMaterialItem> updateItemCallback,
            String logonUserUUID, String organizationUUID)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        ProdPickingOrder prodPickingOrder = prodPickingOrderServiceModel
                .getProdPickingOrder();
        if (!DocActionNodeProxy.checkCurStatus(curStatusList, prodPickingOrder.getStatus())) {
            return;
        }
        if (ServiceCollectionsHelper.checkNullList(prodPickingOrderServiceModel.getProdPickingRefOrderItemList())) {
            return;
        }
        _executeActionCore(prodPickingOrderServiceModel, targetStatus, actionCode, logonUserUUID, organizationUUID);

        /*
         * [Step2] execute each picking item
         */
        for (ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel : prodPickingOrderServiceModel
                .getProdPickingRefOrderItemList()) {
            if (ServiceCollectionsHelper.checkNullList(prodPickingRefOrderItemServiceModel.getProdPickingRefMaterialItemList())) {
                continue;
            }
            if (updateItemCallback != null) {
                for (ProdPickingRefMaterialItemServiceModel prodPickingRefMaterialItemServiceModel :
                        prodPickingRefOrderItemServiceModel.getProdPickingRefMaterialItemList()) {
                    ProdPickingRefMaterialItem prodPickingRefMaterialItem =
                            (ProdPickingRefMaterialItem) prodPickingRefMaterialItemServiceModel.getProdPickingRefMaterialItem();
                    ProdPickingRefMaterialItemExecuteUnion prodPickingRefMaterialItemExecuteUnion =
                            new ProdPickingRefMaterialItemExecuteUnion(prodPickingRefMaterialItem,
                                    prodPickingRefOrderItemServiceModel,
                                    prodPickingOrderServiceModel.getProdPickingOrder(), logonUserUUID,
                                    organizationUUID);
                    updateItemCallback.apply(prodPickingRefMaterialItemExecuteUnion);
                }
            }
        }

        updateServiceModuleWithDelete(
                ProdPickingOrderServiceModel.class,
                prodPickingOrderServiceModel, logonUserUUID, organizationUUID, ProdPickingOrder.SENAME,
                prodPickingOrderServiceUIModelExtension);
    }

    public ProductionOrder getRefProductionOrder(ProdPickingRefOrderItem prodPickingRefOrderItem)
            throws ServiceEntityConfigureException {
        return (ProductionOrder) productionOrderManager
                .getEntityNodeByKey(prodPickingRefOrderItem.getRefProdOrderUUID(), IServiceEntityNodeFieldConstant.UUID,
                        ProductionOrder.NODENAME, null);
    }

    /**
     * Calculate and update the cross order cost for each ProdPickingOrder
     *
     */
    public double setGrossCost(ProdPickingOrderServiceModel prodPickingOrderServiceModel) {
        double grossCost = 0;
        if (!ServiceCollectionsHelper.checkNullList(prodPickingOrderServiceModel.getProdPickingRefOrderItemList())) {
            for (ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel : prodPickingOrderServiceModel
                    .getProdPickingRefOrderItemList()) {
                double grossOrderCost = setGrossOrderCost(prodPickingRefOrderItemServiceModel);
                grossCost += grossOrderCost;
            }
        }
        prodPickingOrderServiceModel.getProdPickingOrder().setGrossCost(grossCost);
        return grossCost;
    }

    /**
     * Calculate and update the cross order cost for each
     * ProdPickingRefOrderItemServiceModel
     *
     */
    public double setGrossOrderCost(ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel) {
        double grossOrderCost =
                this.calculateGrossCost(pluckToRefMaterialItemList(prodPickingRefOrderItemServiceModel.getProdPickingRefMaterialItemList()));
        prodPickingRefOrderItemServiceModel.getProdPickingRefOrderItem().setOrderCost(grossOrderCost);
        return grossOrderCost;
    }

    public static List<ServiceEntityNode> pluckToRefMaterialItemList(List<ProdPickingRefMaterialItemServiceModel> pickingRefMaterialItemList){
        List<ServiceEntityNode> flatRefMaterialItemList = new ArrayList<>();
        ServiceCollectionsHelper.forEach(pickingRefMaterialItemList, prodPickingRefMaterialItemServiceModel -> {
            flatRefMaterialItemList.add(prodPickingRefMaterialItemServiceModel.getProdPickingRefMaterialItem());
            return prodPickingRefMaterialItemServiceModel;
        });
        return flatRefMaterialItemList;
    }

    /**
     * Calculate gross cost by adding all the sub ref material item's item cost,
     * as well as considering the process type.
     *
     */
    public double calculateGrossCost(List<ServiceEntityNode> pickingRefMaterialItemList) {
        double grossCost = 0;
        if (!ServiceCollectionsHelper.checkNullList(pickingRefMaterialItemList)) {
            for (ServiceEntityNode serviceEntityNode :
                    pickingRefMaterialItemList) {
                ProdPickingRefMaterialItem prodPickingRefMaterialItem =
                        (ProdPickingRefMaterialItem) serviceEntityNode;
                if (prodPickingRefMaterialItem.getItemProcessType() == ProdPickingOrder.PROCESSTYPE_REPLENISH
                        || prodPickingRefMaterialItem.getItemProcessType() == ProdPickingOrder.PROCESSTYPE_INPLAN) {
                    grossCost += prodPickingRefMaterialItem.getItemPrice();
                }
                if (prodPickingRefMaterialItem.getItemProcessType() == ProdPickingOrder.PROCESSTYPE_RETURN) {
                    grossCost -= prodPickingRefMaterialItem.getItemPrice();
                }
            }
        }
        return grossCost;
    }

    /**
     * Get all the reserved and active stock list with [in-stock] status and not out-bound has been generated
     *
     */
    public List<ServiceEntityNode> getReservedInStockStoreItemList(String key, String keyName, String client)
            throws ServiceEntityConfigureException {
        List<ServiceEntityNode> prodPickingRefMatItemList = getEntityNodeListByKey(key,
                keyName, ProdPickingRefMaterialItem.NODENAME, client, null);
        List<ServiceEntityNode> resultStoreItemList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(prodPickingRefMatItemList)) {
            for (ServiceEntityNode serviceEntityNode : prodPickingRefMatItemList) {
                ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) serviceEntityNode;
                List<ServiceEntityNode> tmpStoreItemList =
                        prodPickingRefMaterialItemManager.getReservedInStockStoreItemList(prodPickingRefMaterialItem);
                ServiceCollectionsHelper.mergeToList(resultStoreItemList, tmpStoreItemList);
            }
        }
        return resultStoreItemList;
    }

    /**
     * Using Async way to update production order post tasks.
     *
     */
    public ProdPickingOrderServiceModel postUpdateProdPickingOrderServiceModelAsyncWrapper(
            ProdPickingOrderServiceModel prodPickingOrderServiceModel, String logonUserUUID, String organizationUUID)
            throws ServiceEntityConfigureException, MaterialException, ServiceModuleProxyException {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        CompletableFuture<ServiceEntityConfigureException> configureExceptionFuture = new CompletableFuture<>();
        CompletableFuture<ServiceModuleProxyException> serviceModuleExceptionFuture = new CompletableFuture<>();
        CompletableFuture<MaterialException> materialExceptionFuture = new CompletableFuture<>();
        CompletableFuture<DocActionException> docActionExceptionFuture = new CompletableFuture<>();
        CompletableFuture<ProdPickingOrderServiceModel> future = CompletableFuture.supplyAsync(() -> {
            try {
                refreshPickingOrder(prodPickingOrderServiceModel);
                updateServiceModule(ProdPickingOrderServiceModel.class, prodPickingOrderServiceModel, logonUserUUID,
                        organizationUUID);
                return prodPickingOrderServiceModel;
            } catch (ServiceEntityConfigureException e) {
                configureExceptionFuture.complete(e);
                throw new CompletionException(e);
            } catch (MaterialException e) {
                materialExceptionFuture.complete(e);
                throw new CompletionException(e);
            } catch (ServiceModuleProxyException e) {
                serviceModuleExceptionFuture.complete(e);
                throw new CompletionException(e);
            } catch (DocActionException e) {
                docActionExceptionFuture.complete(e);
                throw new CompletionException(e);
            }
        }, executor);
        ProdPickingOrderServiceModel resultServiceModel;
        try {
            resultServiceModel = future.join();
        } catch (CompletionException ex) {
            if (configureExceptionFuture.isDone()) {
                throw configureExceptionFuture.join();
            }
            if (materialExceptionFuture.isDone()) {
                throw materialExceptionFuture.join();
            }
            if (serviceModuleExceptionFuture.isDone()) {
                throw serviceModuleExceptionFuture.join();
            }
            throw ex;
        }
        return resultServiceModel;
    }

    /**
     * Logic to refresh picking order
     *
     */
    public void refreshPickingOrder(ProdPickingOrderServiceModel prodPickingOrderServiceModel)
            throws ServiceEntityConfigureException, MaterialException, DocActionException {
        List<ProdPickingRefOrderItemServiceModel> prodPickingRefOrderItemList = prodPickingOrderServiceModel
                .getProdPickingRefOrderItemList();
        if (!ServiceCollectionsHelper.checkNullList(prodPickingRefOrderItemList)) {
            for (ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel : prodPickingRefOrderItemList) {
                prodPickingRefOrderltemManager.postUpdateRefOrderItem(prodPickingRefOrderItemServiceModel);
            }
        }
    }

    /**
     * Calculate gross amount by adding all the sub ref material item's amount
     * and unit, as well as considering the process type.
     *
     */
    public StorageCoreUnit calculateSuppliedGrossAmount(List<ServiceEntityNode> pickingRefMaterialItemList,
                                                        String refTemplateSKUUUID) throws MaterialException, ServiceEntityConfigureException, DocActionException {
        if (!ServiceCollectionsHelper.checkNullList(pickingRefMaterialItemList)) {
            ProdPickingRefMaterialItem fristPickingRefMaterialItem = (ProdPickingRefMaterialItem) pickingRefMaterialItemList.get(0);
            StorageCoreUnit grossRequest = new StorageCoreUnit(refTemplateSKUUUID, fristPickingRefMaterialItem.getRefUnitUUID(), 0);
            for (ServiceEntityNode seNode : pickingRefMaterialItemList) {
                ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) seNode;
                if (prodPickingRefMaterialItem.getItemProcessType() == ProdPickingOrder.PROCESSTYPE_REPLENISH
                        || prodPickingRefMaterialItem.getItemProcessType() == ProdPickingOrder.PROCESSTYPE_INPLAN) {
                    StorageCoreUnit curSuppliedCoreUnit = prodPickingRefMaterialItemManager
                            .getSuppliedStoreAmount(prodPickingRefMaterialItem);
                    if (curSuppliedCoreUnit != null) {
                        grossRequest = materialStockKeepUnitManager
                                .mergeStorageUnitCore(grossRequest, curSuppliedCoreUnit, StorageCoreUnit.OPERATOR_ADD,
                                        fristPickingRefMaterialItem.getClient());
                    }
                }
                if (prodPickingRefMaterialItem.getItemProcessType() == ProdPickingOrder.PROCESSTYPE_RETURN) {
                    StorageCoreUnit tmpCoreUnit = new StorageCoreUnit(refTemplateSKUUUID, prodPickingRefMaterialItem.getRefUnitUUID(),
                            prodPickingRefMaterialItem.getAmount());
                    grossRequest = materialStockKeepUnitManager
                            .mergeStorageUnitCore(grossRequest, tmpCoreUnit, StorageCoreUnit.OPERATOR_MINUS,
                                    fristPickingRefMaterialItem.getClient());
                }
            }
            return grossRequest;
        }
        return null;
    }

    /**
     * Logic to check if current picking order could be regarded as 'COMPLETE'
     * status, by checking all the sub material item list
     *
     */
    public boolean checkPickingOrderComplete(List<ServiceEntityNode> rawRefMaterialItemList) {
        if (!ServiceCollectionsHelper.checkNullList(rawRefMaterialItemList)) {
            for (ServiceEntityNode seNode : rawRefMaterialItemList) {
                ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) seNode;
                if (prodPickingRefMaterialItem.getItemStatus() != ProdPickingRefMaterialItem.ITEM_STATUS_FINISHED) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    public void convApproveByToOrder(LogonUser logonUser, ProdPickingOrderUIModel prodPickingOrderUIModel) {
        if (logonUser != null) {
            prodPickingOrderUIModel.setApproveById(logonUser.getId());
            prodPickingOrderUIModel.setApproveByName(logonUser.getName());
        }
    }

    public void convProcessByToOrder(LogonUser logonUser, ProdPickingOrderUIModel prodPickingOrderUIModel) {
        if (logonUser != null) {
            prodPickingOrderUIModel.setProcessById(logonUser.getId());
            prodPickingOrderUIModel.setProcessByName(logonUser.getName());
        }
    }

    public void convProdPickingOrderToUI(ProdPickingOrder prodPickingOrder, ProdPickingOrderUIModel prodPickingOrderUIModel)
            throws ServiceEntityInstallationException {
        convProdPickingOrderToUI(prodPickingOrder, prodPickingOrderUIModel, null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     */
    public void convProdPickingOrderToUI(ProdPickingOrder prodPickingOrder, ProdPickingOrderUIModel prodPickingOrderUIModel,
                                         LogonInfo logonInfo) throws ServiceEntityInstallationException {
        if (prodPickingOrder != null) {
            docFlowProxy.convDocumentToUI(prodPickingOrder, prodPickingOrderUIModel, logonInfo);
            prodPickingOrderUIModel.setCategory(prodPickingOrder.getCategory());
            prodPickingOrderUIModel.setPriorityCode(prodPickingOrder.getPriorityCode());
            prodPickingOrderUIModel.setStatus(prodPickingOrder.getStatus());
            prodPickingOrderUIModel.setProcessType(prodPickingOrder.getProcessType());
            if (logonInfo != null) {
                Map<Integer, String> categoryMap = initCategoryMap(logonInfo.getLanguageCode());
                prodPickingOrderUIModel.setCategoryValue(categoryMap.get(prodPickingOrder.getCategory()));
                Map<Integer, String> priorityCodeMap = initProrityMap(logonInfo.getLanguageCode());
                prodPickingOrderUIModel.setPriorityCodeValue(priorityCodeMap.get(prodPickingOrder.getPriorityCode()));
                Map<Integer, String> statusMap = this.initStatusMap(logonInfo.getLanguageCode());
                prodPickingOrderUIModel.setStatusValue(statusMap.get(prodPickingOrder.getStatus()));
                Map<Integer, String> processTypeMap = this.initProcessTypeMap(logonInfo.getLanguageCode());
                prodPickingOrderUIModel.setProcessTypeValue(processTypeMap.get(prodPickingOrder.getProcessType()));
            }
            prodPickingOrderUIModel.setNote(prodPickingOrder.getNote());
            prodPickingOrderUIModel.setApproveBy(prodPickingOrder.getApproveBy());
            prodPickingOrderUIModel.setApproveType(prodPickingOrder.getApproveType());
            prodPickingOrderUIModel.setGrossCost(prodPickingOrder.getGrossCost());
            if (prodPickingOrder.getApproveDate() != null) {
                prodPickingOrderUIModel
                        .setApproveDate(DefaultDateFormatConstant.DATE_MIN_FORMAT.format(prodPickingOrder.getApproveDate()));
            }
            prodPickingOrderUIModel.setProcessBy(prodPickingOrder.getProcessBy());
            if (prodPickingOrder.getProcessDate() != null) {
                prodPickingOrderUIModel
                        .setProcessDate(DefaultDateFormatConstant.DATE_MIN_FORMAT.format(prodPickingOrder.getProcessDate()));
            }
        }
    }

    public void convPrevDocToMatItemUI(DocumentContent prevOrder,
                                       ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel) {
        prodPickingRefMaterialItemUIModel.setPrevDocId(prevOrder.getId());
        prodPickingRefMaterialItemUIModel.setPrevDocName(prevOrder.getName());
        prodPickingRefMaterialItemUIModel.setRefPrevOrderUUID(prevOrder.getUuid());
        prodPickingRefMaterialItemUIModel.setRefPrevOrderStatus(prevOrder.getStatus());
    }

    public void convNextDocToMatItemUI(DocumentContent nextOrder,
                                       ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel) {
        prodPickingRefMaterialItemUIModel.setNextDocId(nextOrder.getId());
        prodPickingRefMaterialItemUIModel.setNextDocName(nextOrder.getName());
        prodPickingRefMaterialItemUIModel.setRefNextOrderUUID(nextOrder.getUuid());
        prodPickingRefMaterialItemUIModel.setRefNextOrderStatus(nextOrder.getStatus());
    }

    public void convOutboundToMatItemUI(OutboundDelivery outboundDelivery,
                                        ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel) {
        convOutboundToMatItemUI(outboundDelivery, prodPickingRefMaterialItemUIModel, null);
    }

    public void convOutboundToMatItemUI(OutboundDelivery outboundDelivery,
                                        ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel, LogonInfo logonInfo) {
        prodPickingRefMaterialItemUIModel.setRefOutboundDeliveryId(outboundDelivery.getId());
        prodPickingRefMaterialItemUIModel.setRefOutboundDeliveryStatus(outboundDelivery.getStatus());
        if (logonInfo != null) {
            try {
                Map<Integer, String> statusMap = outboundDeliveryManager.initStatusMap(logonInfo.getLanguageCode());
                prodPickingRefMaterialItemUIModel.setRefOutboundDeliveryStatusValue(statusMap.get(outboundDelivery.getStatus()));
            } catch (ServiceEntityInstallationException e) {
                // log error and continue
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "status"));
            }
        }

    }

    public void convInboundToMatItemUI(InboundDelivery inboundDelivery,
                                       ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel) {

    }

    public void convInboundToMatItemUI(InboundDelivery inboundDelivery,
                                       ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel, LogonInfo logonInfo) {
        prodPickingRefMaterialItemUIModel.setRefInboundDeliveryId(inboundDelivery.getId());
        prodPickingRefMaterialItemUIModel.setRefInboundDeliveryStatus(inboundDelivery.getStatus());
        try {
            if (logonInfo != null) {
                Map<Integer, String> statusMap = inboundDeliveryManager.initStatusMap(logonInfo.getLanguageCode());
                prodPickingRefMaterialItemUIModel.setRefInboundDeliveryStatusValue(statusMap.get(inboundDelivery.getStatus()));
            }
        } catch (ServiceEntityInstallationException e) {
            // do nothing
        }
    }

    /**
     * [Internal method] Convert from UI model to se model:prodPickingOrder
     *
     */
    public void convUIToProdPickingOrder(ProdPickingOrderUIModel prodPickingOrderUIModel, ProdPickingOrder rawEntity) {
        if (!ServiceEntityStringHelper.checkNullString(prodPickingOrderUIModel.getUuid())) {
            rawEntity.setUuid(prodPickingOrderUIModel.getUuid());
        }
        if (!ServiceEntityStringHelper.checkNullString(prodPickingOrderUIModel.getParentNodeUUID())) {
            rawEntity.setParentNodeUUID(prodPickingOrderUIModel.getParentNodeUUID());
        }
        if (!ServiceEntityStringHelper.checkNullString(prodPickingOrderUIModel.getRootNodeUUID())) {
            rawEntity.setRootNodeUUID(prodPickingOrderUIModel.getRootNodeUUID());
        }
        if (!ServiceEntityStringHelper.checkNullString(prodPickingOrderUIModel.getClient())) {
            rawEntity.setClient(prodPickingOrderUIModel.getClient());
        }
        rawEntity.setCategory(prodPickingOrderUIModel.getCategory());
        rawEntity.setName(prodPickingOrderUIModel.getName());
        rawEntity.setId(prodPickingOrderUIModel.getId());
        rawEntity.setPriorityCode(prodPickingOrderUIModel.getPriorityCode());
        rawEntity.setUuid(prodPickingOrderUIModel.getUuid());
        rawEntity.setProcessType(prodPickingOrderUIModel.getProcessType());
        rawEntity.setNote(prodPickingOrderUIModel.getNote());
        rawEntity.setClient(prodPickingOrderUIModel.getClient());
        rawEntity.setApproveBy(prodPickingOrderUIModel.getApproveBy());
        rawEntity.setApproveType(prodPickingOrderUIModel.getApproveType());
        if (prodPickingOrderUIModel.getGrossCost() > 0) {
            rawEntity.setGrossCost(prodPickingOrderUIModel.getGrossCost());
        }
        if (!ServiceEntityStringHelper.checkNullString(prodPickingOrderUIModel.getApproveDate())) {
            try {
                rawEntity.setApproveDate(DefaultDateFormatConstant.DATE_MIN_FORMAT.parse(prodPickingOrderUIModel.getApproveDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
            } catch (ParseException e) {
                // do nothing
            }
        }
        rawEntity.setProcessBy(prodPickingOrderUIModel.getProcessBy());
        if (!ServiceEntityStringHelper.checkNullString(prodPickingOrderUIModel.getProcessDate())) {
            try {
                rawEntity.setProcessDate(DefaultDateFormatConstant.DATE_MIN_FORMAT.parse(prodPickingOrderUIModel.getProcessDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
            } catch (ParseException e) {
                // do nothing
            }
        }
    }

    public Map<Integer, String> initCategoryMap(String languageCode) throws ServiceEntityInstallationException {
        return ServiceLanHelper
                .initDefLanguageMapUIModel(languageCode, this.categoryMapLan, ProdPickingOrderUIModel.class, "category");
    }

    public Map<Integer, String> initStatusMap(String languageCode) throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode, this.statusMapLan, ProdPickingOrderUIModel.class, "status");
    }

    public Map<Integer, String> initProrityMap(String languageCode) throws ServiceEntityInstallationException {
        return standardPriorityProxy.getPriorityMap(languageCode);
    }

    public Map<Integer, String> initItemStatusMap(String languageCode) throws ServiceEntityInstallationException {
        return ServiceLanHelper
                .initDefLanguageMapUIModel(languageCode, this.itemStatusMapLan, ProdPickingRefMaterialItemUIModel.class, "itemStatus");
    }

    public Map<Integer, String> initProcessTypeMap(String languageCode) throws ServiceEntityInstallationException {
        return ServiceLanHelper
                .initDefLanguageMapUIModel(languageCode, this.processTypeMapLan, ProdPickingOrderUIModel.class, "processType");

    }

    /**
     * Core Logic to get process type array for picking order
     *
     */
    public static int[] getProcessType() {
        return new int[]{ProdPickingOrder.PROCESSTYPE_INPLAN, ProdPickingOrder.PROCESSTYPE_REPLENISH};
    }

    /**
     * Core Logic to get process type array for return order
     *
     */
    public static int[] getReturnType() {
        return new int[]{ProdPickingOrder.PROCESSTYPE_RETURN};
    }

    public static Map<String, List<?>> genProcessTypeMapSearch(int[] typeArray) {
        Map<String, List<?>> processTypeMap = new HashMap<>();
        List<Integer> result = new ArrayList<>();
        for (int processType : typeArray) {
            result.add(processType);
        }
        processTypeMap.put("processType", result);
        return processTypeMap;
    }

    /**
     * Logic to get standard process type for normal picking order
     *
     */
    public Map<Integer, String> getProcessTypeMap(int[] typeArray, String languageCode) throws ServiceEntityInstallationException {
        Map<Integer, String> processTypeMap = initProcessTypeMap(languageCode);
        Map<Integer, String> resultMap = new HashMap<>();
        for (int j : typeArray) {
            resultMap.put(j, processTypeMap.get(j));
        }
        return resultMap;
    }

    public Map<Integer, String> getReturnTypeMap(String languageCode) throws ServiceEntityInstallationException {
        return this.initProcessTypeMap(languageCode);
    }

    /**
     * New PickingRefOrderItem instance refer to this production order, and
     * update to service model if PickingRefOrderItem refer to this production
     * order already exist, then do nothing.
     *
     */
    public ProdPickingRefOrderItemServiceModel updateProdOrderToPickingOrder(ProductionOrder productionOrder,
                                                                             List<ServiceEntityNode> productionOrderItemList, ProdPickingOrderServiceModel prodPickingOrderServiceModel)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, MaterialException {
        ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel = updateOrderToOrderItem(productionOrder,
                prodPickingOrderServiceModel);
        for (ServiceEntityNode seNode : productionOrderItemList) {
            ProductionOrderItem productionOrderItem = (ProductionOrderItem) seNode;
            registerManualRefMaterialItem(productionOrderItem, prodPickingRefOrderItemServiceModel);
        }
        return prodPickingRefOrderItemServiceModel;
    }

    /**
     * Register manually production item to new Ref material item
     *
     */
    public void registerManualRefMaterialItem(ProductionOrderItem productionOrderItem,
                                              ProdPickingRefOrderItemServiceModel parentPickingRefOrderItemServiceModel)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, MaterialException {
        /*
         * [Step1] Generate sub production order from proposal's information
         */
        int documentType = IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY;

        // new reference material item and bind to newly create production order
        // instance
        ProdPickingRefMaterialItem prodPickingRefMaterialItem = newRefMaterialItem(productionOrderItem,
                parentPickingRefOrderItemServiceModel.getProdPickingRefOrderItem());
        ProdPickingRefMaterialItemServiceModel prodPickingRefMaterialItemServiceModel =
                new ProdPickingRefMaterialItemServiceModel();
        prodPickingRefMaterialItemServiceModel.setProdPickingRefMaterialItem(prodPickingRefMaterialItem);
        List<ProdPickingRefMaterialItemServiceModel> prodPickingRefMaterialItemList =
                parentPickingRefOrderItemServiceModel
                .getProdPickingRefMaterialItemList();
        prodPickingRefMaterialItem.setNextDocType(documentType);
        prodPickingRefMaterialItemList.add(prodPickingRefMaterialItemServiceModel);
        parentPickingRefOrderItemServiceModel.setProdPickingRefMaterialItemList(prodPickingRefMaterialItemList);
    }

    /**
     * Wrapper method to New Picking Order Service model from production order
     * instance, and offset id function
     *
     */
    public ProdPickingOrderServiceModel newPickingOrderByProdWrapper(ProductionOrder productionOrder, int offsetId, int status,
                                                                     int processType) throws ServiceEntityConfigureException {
        ProdPickingOrder prodPickingOrder = newRootEntityForProdOrder(productionOrder.getClient());
        prodPickingOrder.setId(prodPickingOrderIdHelper.genDefaultId(productionOrder.getClient(), offsetId));
        prodPickingOrder.setStatus(status);
        prodPickingOrder.setProcessType(processType);
        ProdPickingOrderServiceModel prodPickingOrderServiceModel = new ProdPickingOrderServiceModel();
        prodPickingOrderServiceModel.setProdPickingOrder(prodPickingOrder);
        updateOrderToOrderItem(productionOrder, prodPickingOrderServiceModel);
        return prodPickingOrderServiceModel;
    }

    /**
     * New PickingRefOrderItem instance refer to this production order, and
     * update to service model. If PickingRefOrderItem refer to this production
     * order already exist, then do nothing.
     *
     */
    public ProdPickingRefOrderItemServiceModel updateOrderToOrderItem(ProductionOrder productionOrder,
                                                                      ProdPickingOrderServiceModel prodPickingOrderServiceModel) throws ServiceEntityConfigureException {
        List<ProdPickingRefOrderItemServiceModel> prodPickingRefOrderItemList = prodPickingOrderServiceModel
                .getProdPickingRefOrderItemList();
        ProdPickingRefOrderItem prodPickingRefOrderItem = newRefOrderItemFromOrder(productionOrder,
                prodPickingOrderServiceModel.getProdPickingOrder());
        ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel = new ProdPickingRefOrderItemServiceModel();
        prodPickingRefOrderItemServiceModel.setProdPickingRefOrderItem(prodPickingRefOrderItem);
        if (ServiceCollectionsHelper.checkNullList(prodPickingRefOrderItemList)) {
            prodPickingRefOrderItemList = new ArrayList<>();
            prodPickingRefOrderItemList.add(prodPickingRefOrderItemServiceModel);
            prodPickingOrderServiceModel.setProdPickingRefOrderItemList(prodPickingRefOrderItemList);
        } else {
            ProdPickingRefOrderItemServiceModel existedPickingRefOrderItemServiceModel = filterRefOrderItemByOrder(
                    prodPickingRefOrderItemList, productionOrder.getUuid());
            if (existedPickingRefOrderItemServiceModel == null) {
                prodPickingRefOrderItemList.add(prodPickingRefOrderItemServiceModel);
                prodPickingOrderServiceModel.setProdPickingRefOrderItemList(prodPickingRefOrderItemList);
            }
        }
        return prodPickingRefOrderItemServiceModel;
    }

    public static ProdPickingRefOrderItemServiceModel filterRefOrderItemByOrder(
            List<ProdPickingRefOrderItemServiceModel> prodPickingRefOrderItemList, String orderUUID) {
        if (ServiceEntityStringHelper.checkNullString(orderUUID)) {
            return null;
        }
        for (ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel : prodPickingRefOrderItemList) {
            ProdPickingRefOrderItem tmpPickingRefOrderItem = prodPickingRefOrderItemServiceModel.getProdPickingRefOrderItem();
            if (orderUUID.equals(tmpPickingRefOrderItem.getRefProdOrderUUID())) {
                return prodPickingRefOrderItemServiceModel;
            }
        }
        return null;
    }

    public ProdPickingRefOrderItem newRefOrderItemFromOrder(ProductionOrder productionOrder, ProdPickingOrder prodPickingOrder)
            throws ServiceEntityConfigureException {
        ProdPickingRefOrderItem prodPickingRefOrderItem = (ProdPickingRefOrderItem) newEntityNode(prodPickingOrder,
                ProdPickingRefOrderItem.NODENAME);
        prodPickingRefOrderItem.setAmount(productionOrder.getAmount());
        prodPickingRefOrderItem.setRefUnitUUID(productionOrder.getRefUnitUUID());
        prodPickingRefOrderItem.setRefProdOrderUUID(productionOrder.getUuid());
        return prodPickingRefOrderItem;
    }

    /**
     * Common method of new and initial RefMaterialItem from plan item proposal
     *
     * @param itemStatus                  : Target item status set for newly created ref material item
     */
    public ProdPickingRefMaterialItem newRefMaterialItem(ProdItemReqProposalTemplate prodItemReqProposalTemplate,
                                                         int proposalDocType, ProdPickingRefOrderItem prodPickingRefOrderItem, int itemStatus, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, MaterialException, ServiceComExecuteException, DocActionException {
        ProdPickingRefMaterialItem prodPickingRefMaterialItem = prodPickingRefMaterialItemManager
                .newPickingRefMaterialItem(prodPickingRefOrderItem, ProdPickingOrder.PROCESSTYPE_INPLAN);
        docFlowProxy.buildItemPrevNextRelationship(prodItemReqProposalTemplate, prodPickingRefMaterialItem, null,
                serialLogonInfo );
        prodPickingRefMaterialItem.setItemStatus(itemStatus);
        prodPickingRefMaterialItem.setRefProdOrderItemUUID(prodItemReqProposalTemplate.getParentNodeUUID());
        MaterialStockKeepUnit materialStockKeepUnit = materialStockKeepUnitManager
                .getMaterialSKUWrapper(prodItemReqProposalTemplate.getRefMaterialSKUUUID(), prodItemReqProposalTemplate.getClient(),
                        null);
        if (materialStockKeepUnit != null) {
            // Logic to set UnitCost, ItemCost to RefMaterialItem
            prodPickingRefMaterialItem.setUnitPrice(materialStockKeepUnit.getUnitCost());
            // TODO: how to calculate tax rate and no tax cost here
            prodPickingRefMaterialItem.setUnitPriceNoTax(materialStockKeepUnit.getUnitCost());
            StorageCoreUnit storageCoreUnit = new StorageCoreUnit(materialStockKeepUnit.getUuid(),
                    prodPickingRefMaterialItem.getRefUnitUUID(), prodPickingRefMaterialItem.getAmount());
            double itemCost = materialStockKeepUnitManager
                    .calculatePrice(storageCoreUnit, materialStockKeepUnit, materialStockKeepUnit.getUnitCost());
            prodPickingRefMaterialItem.setItemPrice(itemCost);
            double itemCostNoTax = materialStockKeepUnitManager
                    .calculatePrice(storageCoreUnit, materialStockKeepUnit, materialStockKeepUnit.getUnitCost());
            prodPickingRefMaterialItem.setUnitPriceNoTax(itemCostNoTax);
        }
        prodPickingRefMaterialItem.setRefWarehouseUUID(prodItemReqProposalTemplate.getRefWarehouseUUID());
        prodPickingRefMaterialItem.setRefBillOfMaterialUUID(prodItemReqProposalTemplate.getRefBOMItemUUID());
        prodPickingRefMaterialItem.setPlanStartPrepareDate(prodItemReqProposalTemplate.getPlanStartPrepareDate());
        prodPickingRefMaterialItem.setPlanStartTime(prodItemReqProposalTemplate.getPlanStartDate());
        prodPickingRefMaterialItem.setPlanEndTime(prodItemReqProposalTemplate.getPlanEndDate());
        /*
         * [Step2] Fill information of BOM item
         */
        if (!ServiceEntityStringHelper.checkNullString(prodItemReqProposalTemplate.getRefBOMItemUUID())) {
            BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) billOfMaterialOrderManager
                    .getEntityNodeByKey(prodItemReqProposalTemplate.getRefBOMItemUUID(), IServiceEntityNodeFieldConstant.UUID,
                            BillOfMaterialItem.NODENAME, prodItemReqProposalTemplate.getClient(), null);
            if (billOfMaterialItem != null) {
                if (!ServiceEntityStringHelper.checkNullString(billOfMaterialItem.getRefSubBOMUUID())) {
                    BillOfMaterialOrder subBOMOrder = (BillOfMaterialOrder) billOfMaterialOrderManager
                            .getEntityNodeByKey(billOfMaterialItem.getRefSubBOMUUID(), IServiceEntityNodeFieldConstant.UUID,
                                    BillOfMaterialOrder.NODENAME, prodItemReqProposalTemplate.getClient(), null);
                    if (subBOMOrder != null) {
                        prodPickingRefMaterialItem.setRefBillOfMaterialUUID(subBOMOrder.getUuid());
                    }
                }
            } else {
                BillOfMaterialOrder subBOMOrder = (BillOfMaterialOrder) billOfMaterialOrderManager
                        .getEntityNodeByKey(prodItemReqProposalTemplate.getRefBOMItemUUID(), IServiceEntityNodeFieldConstant.UUID,
                                BillOfMaterialOrder.NODENAME, prodItemReqProposalTemplate.getClient(), null);
                if (subBOMOrder != null) {
                    prodPickingRefMaterialItem.setRefBillOfMaterialUUID(subBOMOrder.getUuid());
                }
            }
        }
        return prodPickingRefMaterialItem;
    }

    /**
     * Common method of new and initial RefMaterialItem from plan item proposal
     *
     * @param itemStatus               : Target item status set for newly created ref material item
     */
    public ProdPickingRefMaterialItem newRefMaterialItem(ProdOrderItemReqProposal prodOrderItemReqProposal,
                                                         ProdPickingRefOrderItem prodPickingRefOrderItem, int itemStatus, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, MaterialException, ServiceComExecuteException, DocActionException {
        ProdPickingRefMaterialItem prodPickingRefMaterialItem = prodPickingRefMaterialItemManager
                .newPickingRefMaterialItem(prodPickingRefOrderItem, ProdPickingOrder.PROCESSTYPE_INPLAN);
        // Initial set next mat UUID from proposal, and make proposal next point
        // to picking mat item
        // This value only works for when there is existed store item uuid
        prodPickingRefMaterialItem.setNextDocMatItemUUID(prodOrderItemReqProposal.getNextDocMatItemUUID());
        prodPickingRefMaterialItem.setNextDocType(prodOrderItemReqProposal.getNextDocType());
        // Important: should also double maintain these 2 fields
        prodPickingRefMaterialItem.setDocumentType(prodOrderItemReqProposal.getNextDocType());
        prodPickingRefMaterialItem.setRefUUID(prodPickingRefMaterialItem.getNextDocMatItemUUID());
        docFlowProxy.buildItemPrevNextRelationship(prodOrderItemReqProposal,
                prodPickingRefMaterialItem, null,serialLogonInfo);

        prodPickingRefMaterialItem.setItemStatus(itemStatus);
        MaterialStockKeepUnit materialStockKeepUnit = materialStockKeepUnitManager
                .getMaterialSKUWrapper(prodOrderItemReqProposal.getRefMaterialSKUUUID(), prodOrderItemReqProposal.getClient(), null);
        if (materialStockKeepUnit != null) {
            // Logic to set UnitCost, ItemCost to RefMaterialItem
            prodPickingRefMaterialItem.setUnitPrice(materialStockKeepUnit.getUnitCost());
            // TODO: how to calculate tax rate and no tax cost here
            prodPickingRefMaterialItem.setUnitPriceNoTax(materialStockKeepUnit.getUnitCost());
            StorageCoreUnit storageCoreUnit = new StorageCoreUnit(materialStockKeepUnit.getUuid(),
                    prodPickingRefMaterialItem.getRefUnitUUID(), prodPickingRefMaterialItem.getAmount());
            double itemCost = materialStockKeepUnitManager
                    .calculatePrice(storageCoreUnit, materialStockKeepUnit, materialStockKeepUnit.getUnitCost());
            prodPickingRefMaterialItem.setItemPrice(itemCost);
            double itemCostNoTax = materialStockKeepUnitManager
                    .calculatePrice(storageCoreUnit, materialStockKeepUnit, materialStockKeepUnit.getUnitCost());
            prodPickingRefMaterialItem.setUnitPriceNoTax(itemCostNoTax);
        }
        prodPickingRefMaterialItem.setRefWarehouseUUID(prodOrderItemReqProposal.getRefWarehouseUUID());
        prodPickingRefMaterialItem.setRefProdOrderItemUUID(prodOrderItemReqProposal.getParentNodeUUID());
        prodPickingRefMaterialItem.setRefBillOfMaterialUUID(prodOrderItemReqProposal.getRefBOMItemUUID());
        prodPickingRefMaterialItem.setPlanStartPrepareDate(prodOrderItemReqProposal.getPlanStartPrepareDate());
        prodPickingRefMaterialItem.setPlanEndTime(prodOrderItemReqProposal.getPlanEndDate());
        prodPickingRefMaterialItem.setPlanStartTime(prodOrderItemReqProposal.getPlanStartDate());
        if (!ServiceEntityStringHelper.checkNullString(prodOrderItemReqProposal.getRefBOMItemUUID())) {
            BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) billOfMaterialOrderManager
                    .getEntityNodeByKey(prodOrderItemReqProposal.getRefBOMItemUUID(), IServiceEntityNodeFieldConstant.UUID,
                            BillOfMaterialItem.NODENAME, prodOrderItemReqProposal.getClient(), null);
            if (billOfMaterialItem != null) {
                if (!ServiceEntityStringHelper.checkNullString(billOfMaterialItem.getRefSubBOMUUID())) {
                    BillOfMaterialOrder subBOMOrder = (BillOfMaterialOrder) billOfMaterialOrderManager
                            .getEntityNodeByKey(billOfMaterialItem.getRefSubBOMUUID(), IServiceEntityNodeFieldConstant.UUID,
                                    BillOfMaterialOrder.NODENAME, prodOrderItemReqProposal.getClient(), null);
                    if (subBOMOrder != null) {
                        prodPickingRefMaterialItem.setRefBillOfMaterialUUID(subBOMOrder.getUuid());
                    }
                }
            } else {
                BillOfMaterialOrder subBOMOrder = (BillOfMaterialOrder) billOfMaterialOrderManager
                        .getEntityNodeByKey(prodOrderItemReqProposal.getRefBOMItemUUID(), IServiceEntityNodeFieldConstant.UUID,
                                BillOfMaterialOrder.NODENAME, prodOrderItemReqProposal.getClient(), null);
                if (subBOMOrder != null) {
                    prodPickingRefMaterialItem.setRefBillOfMaterialUUID(subBOMOrder.getUuid());
                }
            }
        }
        return prodPickingRefMaterialItem;
    }

    /**
     * Common method of new and initial RefMaterialItem from plan item proposal
     *
     */
    public ProdPickingRefMaterialItem newReturnMaterialItem(ProdOrderItemReqProposal prodOrderItemReqProposal,
                                                            ProdPickingRefOrderItem prodPickingRefOrderItem, int itemStatus, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, MaterialException, ServiceComExecuteException, DocActionException {
        ProdPickingRefMaterialItem prodPickingRefMaterialItem = prodPickingRefMaterialItemManager
                .newPickingRefMaterialItem(prodPickingRefOrderItem, ProdPickingOrder.PROCESSTYPE_RETURN);

        prodPickingRefMaterialItem.setNextDocMatItemUUID(prodOrderItemReqProposal.getNextDocMatItemUUID());
        prodPickingRefMaterialItem.setNextDocType(prodOrderItemReqProposal.getNextDocType());
        // Important: should also double maintain these 2 fields
        prodPickingRefMaterialItem.setDocumentType(prodOrderItemReqProposal.getNextDocType());
        prodPickingRefMaterialItem.setRefUUID(prodPickingRefMaterialItem.getNextDocMatItemUUID());
        docFlowProxy.buildItemPrevNextRelationship(prodOrderItemReqProposal,
                prodPickingRefMaterialItem, null,serialLogonInfo);

        prodPickingRefMaterialItem.setItemStatus(itemStatus);
        MaterialStockKeepUnit materialStockKeepUnit = materialStockKeepUnitManager
                .getMaterialSKUWrapper(prodOrderItemReqProposal.getRefMaterialSKUUUID(), prodOrderItemReqProposal.getClient(), null);

        if (materialStockKeepUnit != null) {
            // Logic to set UnitCost, ItemCost to RefMaterialItem
            prodPickingRefMaterialItem.setUnitPrice(materialStockKeepUnit.getUnitCost());
            // TODO: how to calculate tax rate and no tax cost here
            prodPickingRefMaterialItem.setUnitPriceNoTax(materialStockKeepUnit.getUnitCost());
            StorageCoreUnit storageCoreUnit = new StorageCoreUnit(materialStockKeepUnit.getUuid(),
                    prodPickingRefMaterialItem.getRefUnitUUID(), prodPickingRefMaterialItem.getAmount());
            double itemCost = materialStockKeepUnitManager
                    .calculatePrice(storageCoreUnit, materialStockKeepUnit, materialStockKeepUnit.getUnitCost());
            prodPickingRefMaterialItem.setItemPrice(itemCost);
            double itemCostNoTax = materialStockKeepUnitManager
                    .calculatePrice(storageCoreUnit, materialStockKeepUnit, materialStockKeepUnit.getUnitCost());
            prodPickingRefMaterialItem.setUnitPriceNoTax(itemCostNoTax);
        }
        prodPickingRefMaterialItem.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY);
        prodPickingRefMaterialItem.setNextDocType(IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY);
        return prodPickingRefMaterialItem;
    }

    /**
     * get ProdPickingRefMaterialItem list already assign to this production
     * order item
     *
     * @param baseUUID : production order item uuid
     */
    public List<ServiceEntityNode> getRefMaterialItemListFromItem(String baseUUID, String client)
            throws ServiceEntityConfigureException {
        List<ServiceEntityNode> extProdPickingRefMaterialItemList = this
                .getEntityNodeListByKey(baseUUID, "refProdOrderItemUUID", ProdPickingRefMaterialItem.NODENAME, client, null);
        return extProdPickingRefMaterialItemList;
    }

    /**
     * Common method of new and initial RefMaterialItem from plan item proposal
     *
     */
    public ProdPickingRefMaterialItem newRefMaterialItem(ProductionOrderItem productionOrderItem,
                                                         ProdPickingRefOrderItem prodPickingRefOrderItem) throws ServiceEntityConfigureException, MaterialException {
        ProdPickingRefMaterialItem prodPickingRefMaterialItem = prodPickingRefMaterialItemManager
                .newPickingRefMaterialItem(prodPickingRefOrderItem, ProdPickingOrder.PROCESSTYPE_INPLAN);
        /*
         * trying to get ProdPickingRefMaterialItem list already assign to this
         * production order item
         */
        List<ServiceEntityNode> extProdPickingRefMaterialItemList = getRefMaterialItemListFromItem(productionOrderItem.getUuid(),
                productionOrderItem.getClient());
        if (!ServiceCollectionsHelper.checkNullList(extProdPickingRefMaterialItemList)) {
            ProdPickingRefMaterialItem refMaterialItem0 = (ProdPickingRefMaterialItem) extProdPickingRefMaterialItemList.get(0);
            StorageCoreUnit storageCoreUnit0 = new StorageCoreUnit();
            storageCoreUnit0.setRefMaterialSKUUUID(productionOrderItem.getRefMaterialSKUUUID());
            storageCoreUnit0.setAmount(refMaterialItem0.getAmount());
            storageCoreUnit0.setRefUnitUUID(refMaterialItem0.getRefUnitUUID());
            if (extProdPickingRefMaterialItemList.size() > 1) {
                for (int i = 1; i < extProdPickingRefMaterialItemList.size(); i++) {
                    ProdPickingRefMaterialItem tempProdPickingRefMaterialItem = (ProdPickingRefMaterialItem) extProdPickingRefMaterialItemList
                            .get(i);
                    StorageCoreUnit tmpCoreUnit = new StorageCoreUnit();
                    tmpCoreUnit.setRefMaterialSKUUUID(productionOrderItem.getRefMaterialSKUUUID());
                    tmpCoreUnit.setAmount(tempProdPickingRefMaterialItem.getAmount());
                    tmpCoreUnit.setRefUnitUUID(tempProdPickingRefMaterialItem.getRefUnitUUID());
                    materialStockKeepUnitManager.mergeStorageUnitCore(storageCoreUnit0, tmpCoreUnit, StorageCoreUnit.OPERATOR_ADD,
                            productionOrderItem.getClient());
                }
            }
            // Amount and Unit from production order
            StorageCoreUnit sumStorageCoreUnit = new StorageCoreUnit();
            sumStorageCoreUnit.setRefMaterialSKUUUID(productionOrderItem.getRefMaterialSKUUUID());
            sumStorageCoreUnit.setRefUnitUUID(productionOrderItem.getRefUnitUUID());
            sumStorageCoreUnit.setAmount(productionOrderItem.getActualAmount());
            // deduce amount for existed refMaterial item list
            materialStockKeepUnitManager.mergeStorageUnitCore(sumStorageCoreUnit, storageCoreUnit0, StorageCoreUnit.OPERATOR_MINUS,
                    productionOrderItem.getClient());
            prodPickingRefMaterialItem.setRefUnitUUID(sumStorageCoreUnit.getRefUnitUUID());
            if (sumStorageCoreUnit.getAmount() > 0) {
                prodPickingRefMaterialItem.setAmount(sumStorageCoreUnit.getAmount());
            } else {
                prodPickingRefMaterialItem.setAmount(0);
            }
        } else {
            prodPickingRefMaterialItem.setAmount(productionOrderItem.getActualAmount());
            prodPickingRefMaterialItem.setRefUnitUUID(productionOrderItem.getRefUnitUUID());
        }
        prodPickingRefMaterialItem.setRefUUID(productionOrderItem.getRefMaterialSKUUUID());
        prodPickingRefMaterialItem.setRefMaterialSKUUUID(productionOrderItem.getRefMaterialSKUUUID());
        prodPickingRefMaterialItem.setRefProdOrderItemUUID(productionOrderItem.getUuid());
        prodPickingRefMaterialItem.setPrevDocType(IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER);
        // By default next order is 'outbound-delivery'
        prodPickingRefMaterialItem.setNextDocType(IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY);
        return prodPickingRefMaterialItem;
    }

    /**
     * Logic to return all the picking material item list, which could be used for picking.
     *
     */
    public List<ServiceEntityNode> getReturnToPickListBatch(String baseUUID, String client) throws ServiceEntityConfigureException, MaterialException {
        List<ServiceEntityNode> prodPickingMatItemList = this.getEntityNodeListByKey(baseUUID,
                IServiceEntityNodeFieldConstant.ROOTNODEUUID, ProdPickingRefMaterialItem.NODENAME, client, null);
        return prodPickingRefMaterialItemManager.getReturnToPickMaterialItemList(prodPickingMatItemList);
    }

    /**
     * Logic to return all the picking material item list, which could be used for picking.
     *
     */
    public List<ServiceEntityNode> getToPickListBatch(String baseUUID, String client) throws ServiceEntityConfigureException, MaterialException, DocActionException {
        List<ServiceEntityNode> prodPickingMatItemList = this.getEntityNodeListByKey(baseUUID,
                IServiceEntityNodeFieldConstant.ROOTNODEUUID, ProdPickingRefMaterialItem.NODENAME, client, null);
        return prodPickingRefMaterialItemManager.getToPickMaterialItemList(prodPickingMatItemList);
    }

    public List<ServiceEntityNode> searchProdPickingOrder(ProdPickingOrderSearchModel searchModel,
                                                          String client) throws SearchConfigureException, ServiceEntityConfigureException, NodeNotFoundException,
            ServiceEntityInstallationException {
        return prodPickingOrderSearchProxy.searchPickingOrderList(searchModel, client);
    }

    public List<ServiceEntityNode> searchProdReturnOrder(ProdPickingOrderSearchModel searchModel,
                                                         String client) throws SearchConfigureException, ServiceEntityConfigureException, NodeNotFoundException,
            ServiceEntityInstallationException {
        return prodPickingOrderSearchProxy.searchReturnOrderList(searchModel, client);
    }

    public List<ServiceEntityNode> searchPickingItemList(ProdPickingRefMaterialItemSearchModel searchModel,
                                                         String client)
            throws SearchConfigureException, ServiceEntityConfigureException, NodeNotFoundException,
            ServiceEntityInstallationException {
        return prodPickingOrderSearchProxy.searchPickingItemList(searchModel, client);
    }

    public List<ServiceEntityNode> searchReturnItemList(ProdPickingRefMaterialItemSearchModel searchModel,
                                                        String client)
            throws SearchConfigureException, ServiceEntityConfigureException, NodeNotFoundException,
            ServiceEntityInstallationException {
        return prodPickingOrderSearchProxy.searchPickingItemList(searchModel, client);
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, prodPickingOrderDAO));
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(prodPickingOrderConfigureProxy);
    }

    /**
     * Logic to list all the possible picking order's next order types
     *
     */
    public static int[] getNextDocType() {
        return new int[]{IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY, IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT,
                IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER};
    }

    /**
     * Logic to list all the possible picking order's prev order types
     *
     */
    public static int[] getPrevDocType() {
        return new int[]{IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY, IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT,
                IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER};
    }

    /**
     * Logic to list all the possible return order's next order types
     *
     */
    public static int[] getReturnNextOrderType() {
        return new int[]{IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY};
    }

    /**
     * Logic to list all the possible return order's prev order types
     *
     */
    public static int[] getReturnPrevOrderType() {
        return new int[]{IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY};
    }

    /**
     * Logic to get standard process type for normal picking order
     *
     */
    public Map<Integer, String> getOrderTypeMap(int[] typeArray, String languageCode) throws ServiceEntityInstallationException {
        Map<Integer, String> documentType = serviceDocumentComProxy.getDocumentTypeMap(false, languageCode);
        Map<Integer, String> resultMap = new HashMap<>();
        for (int j : typeArray) {
            resultMap.put(j, documentType.get(j));
        }
        return resultMap;
    }

    public Map<Integer, String> initDocumentTypeMap(String languageCode) throws ServiceEntityInstallationException {
        return serviceDocumentComProxy.getDocumentTypeMap(false, languageCode);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     */
    public void convProdPickingRefOrderItemToUI(ProdPickingRefOrderItem prodPickingRefOrderItem,
                                                ProdPickingRefOrderItemUIModel prodPickingRefOrderItemUIModel) throws ServiceEntityConfigureException {
        if (prodPickingRefOrderItem != null) {
            if (!ServiceEntityStringHelper.checkNullString(prodPickingRefOrderItem.getUuid())) {
                prodPickingRefOrderItemUIModel.setUuid(prodPickingRefOrderItem.getUuid());
            }
            if (!ServiceEntityStringHelper.checkNullString(prodPickingRefOrderItem.getParentNodeUUID())) {
                prodPickingRefOrderItemUIModel.setParentNodeUUID(prodPickingRefOrderItem.getParentNodeUUID());
            }
            if (!ServiceEntityStringHelper.checkNullString(prodPickingRefOrderItem.getRootNodeUUID())) {
                prodPickingRefOrderItemUIModel.setRootNodeUUID(prodPickingRefOrderItem.getRootNodeUUID());
            }
            if (!ServiceEntityStringHelper.checkNullString(prodPickingRefOrderItem.getClient())) {
                prodPickingRefOrderItemUIModel.setClient(prodPickingRefOrderItem.getClient());
            }
            prodPickingRefOrderItemUIModel.setProcessIndex(prodPickingRefOrderItem.getProcessIndex());
            prodPickingRefOrderItemUIModel.setRefProdOrderUUID(prodPickingRefOrderItem.getRefProdOrderUUID());
            prodPickingRefOrderItemUIModel.setRefUnitUUID(prodPickingRefOrderItem.getRefUnitUUID());
            prodPickingRefOrderItemUIModel.setAmount(prodPickingRefOrderItem.getAmount());
            prodPickingRefOrderItemUIModel.setOrderCost(prodPickingRefOrderItem.getOrderCost());
            ProductionOrder productionOrder = (ProductionOrder) productionOrderManager
                    .getEntityNodeByKey(prodPickingRefOrderItem.getRefProdOrderUUID(), IServiceEntityNodeFieldConstant.UUID,
                            ProductionOrder.NODENAME, prodPickingRefOrderItem.getClient(), null);
            if (productionOrder != null) {
                try {
                    Map<String, String> materialUnitMap = materialStockKeepUnitManager
                            .initMaterialUnitMap(productionOrder.getRefMaterialSKUUUID(), productionOrder.getClient());
                    prodPickingRefOrderItemUIModel.setRefUnitName(materialUnitMap.get(prodPickingRefOrderItem.getRefUnitUUID()));
                } catch (MaterialException e) {
                    // just skip
                }
            }
            prodPickingRefOrderItemUIModel.setRefUnitUUID(prodPickingRefOrderItem.getRefUnitUUID());

        }
    }

    /**
     * [Internal method] Convert from UI model to se
     * model:prodPickingRefOrderItem
     *
     */
    public void convUIToProdPickingRefOrderItem(ProdPickingRefOrderItemUIModel prodPickingRefOrderItemUIModel,
                                                ProdPickingRefOrderItem rawEntity) {
        if (!ServiceEntityStringHelper.checkNullString(prodPickingRefOrderItemUIModel.getUuid())) {
            rawEntity.setUuid(prodPickingRefOrderItemUIModel.getUuid());
        }
        if (!ServiceEntityStringHelper.checkNullString(prodPickingRefOrderItemUIModel.getParentNodeUUID())) {
            rawEntity.setParentNodeUUID(prodPickingRefOrderItemUIModel.getParentNodeUUID());
        }
        if (!ServiceEntityStringHelper.checkNullString(prodPickingRefOrderItemUIModel.getRootNodeUUID())) {
            rawEntity.setRootNodeUUID(prodPickingRefOrderItemUIModel.getRootNodeUUID());
        }
        if (!ServiceEntityStringHelper.checkNullString(prodPickingRefOrderItemUIModel.getClient())) {
            rawEntity.setClient(prodPickingRefOrderItemUIModel.getClient());
        }
        if (prodPickingRefOrderItemUIModel.getOrderCost() > 0) {
            rawEntity.setOrderCost(prodPickingRefOrderItemUIModel.getOrderCost());
        }
        rawEntity.setProcessIndex(prodPickingRefOrderItemUIModel.getProcessIndex());
        rawEntity.setClient(prodPickingRefOrderItemUIModel.getClient());
        rawEntity.setUuid(prodPickingRefOrderItemUIModel.getUuid());
        rawEntity.setRefProdOrderUUID(prodPickingRefOrderItemUIModel.getRefProdOrderUUID());
        rawEntity.setRefUnitUUID(prodPickingRefOrderItemUIModel.getRefUnitUUID());
        rawEntity.setAmount(prodPickingRefOrderItemUIModel.getAmount());
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     */
    public void convOrderMaterialStockKeepUnitToUI(MaterialStockKeepUnit orderMaterialStockKeepUnit,
                                                   ProdPickingRefOrderItemUIModel prodPickingRefOrderItemUIModel) {
        if (orderMaterialStockKeepUnit != null) {
            prodPickingRefOrderItemUIModel.setOrderMaterialSKUName(orderMaterialStockKeepUnit.getName());
            prodPickingRefOrderItemUIModel.setOrderMaterialSKUId(orderMaterialStockKeepUnit.getId());
        }
    }

    public void convProductionOrderToUI(ProductionOrder productionOrder,
                                        ProdPickingOrderUIModel prodPickingOrderUIModel) {
        if (productionOrder != null) {
            prodPickingOrderUIModel.setDefProdOrderId(productionOrder.getId());
            prodPickingOrderUIModel.setDefProdOrderName(productionOrder.getName());
            prodPickingOrderUIModel.setDefProdOrderUUID(productionOrder.getUuid());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     */
    public void convProductionOrderToUI(ProductionOrder productionOrder,
                                        ProdPickingRefOrderItemUIModel prodPickingRefOrderItemUIModel) {
        if (productionOrder != null) {
            prodPickingRefOrderItemUIModel.setRefOrderId(productionOrder.getId());
            prodPickingRefOrderItemUIModel.setRefOrderName(productionOrder.getName());
            prodPickingRefOrderItemUIModel.setOrderMaterialSKUUUID(productionOrder.getRefMaterialSKUUUID());
        }
    }

    @Override
    public ServiceSearchProxy getSearchProxy() {
        return prodPickingOrderSearchProxy;
    }

    /**
     * When relative document update, also update some info in reserved doc
     */
    @Override
    public void reservedDocUpdateToMatItem(int sourceDocType, DocumentContent sourceDocument, ServiceEntityNode docMaterialItem,
                                           String targetMatItemUUID) {
        try {
            ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) this
                    .getEntityNodeByKey(targetMatItemUUID, IServiceEntityNodeFieldConstant.UUID, ProdPickingRefMaterialItem.NODENAME,
                            docMaterialItem.getClient(), null);
            if (prodPickingRefMaterialItem != null) {
                if (sourceDocType != 0) {
                    if (sourceDocType == IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY) {
                        // In case source doc is out-bound
                        prodPickingRefMaterialItem.setRefOutboundItemUUID(docMaterialItem.getUuid());
                    }
                }
                updateSENode(prodPickingRefMaterialItem, prodPickingRefMaterialItem.getLastUpdateBy(),
                        prodPickingRefMaterialItem.getResOrgUUID());
            }
        } catch (ServiceEntityConfigureException e) {
            // just do nothing
        }
    }

    public ServiceDocumentExtendUIModel convPickingRefMaterialItemToDocExtUIModel(
            ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
        serviceDocumentExtendUIModel.setRefUIModel(prodPickingRefMaterialItemUIModel);
        serviceDocumentExtendUIModel.setId(prodPickingRefMaterialItemUIModel.getRefPickingOrderId());
        serviceDocumentExtendUIModel.setStatus(prodPickingRefMaterialItemUIModel.getItemStatus());
        serviceDocumentExtendUIModel.setStatusValue(prodPickingRefMaterialItemUIModel.getItemStatusValue());
        int documentType = IDefDocumentResource.DOCUMENT_TYPE_PRODPICKINGORDER;
        if (prodPickingRefMaterialItemUIModel.getItemProcessType() == ProdPickingOrder.PROCESSTYPE_RETURN) {
            documentType = IDefDocumentResource.DOCUMENT_TYPE_PRODRETURNORDER;
        }
        docFlowProxy.convDocMatItemUIToDocExtUIModel(prodPickingRefMaterialItemUIModel, serviceDocumentExtendUIModel, logonInfo,
                documentType);
        // Logic of reference Date
        String referenceDate = prodPickingRefMaterialItemUIModel.getCreatedTime();
        serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
        return serviceDocumentExtendUIModel;
    }

    @Override
    public ServiceDocumentExtendUIModel convToDocumentExtendUIModel(ServiceEntityNode seNode, LogonInfo logonInfo) {
        if (seNode == null) {
            return null;
        }
        if (ProdPickingRefMaterialItem.NODENAME.equals(seNode.getNodeName())) {
            ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) seNode;
            try {
                ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel = (ProdPickingRefMaterialItemUIModel) genUIModelFromUIModelExtension(
                        ProdPickingRefMaterialItemUIModel.class,
                        prodPickingRefMaterialItemServiceUIModelExtension.genUIModelExtensionUnion().get(0), prodPickingRefMaterialItem,
                        logonInfo, null);
                return convPickingRefMaterialItemToDocExtUIModel(
                        prodPickingRefMaterialItemUIModel, logonInfo);
            } catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ProdPickingRefMaterialItem.NODENAME));
            }
        }
        return null;
    }

}
