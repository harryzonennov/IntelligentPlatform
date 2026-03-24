package com.company.IntelligentPlatform.production.service;
import com.company.IntelligentPlatform.production.service.ProdPickingExtendAmountModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.WarehouseLogisticsManager;
import com.company.IntelligentPlatform.production.dto.ProdOrderItemReqProposalServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProdOrderItemReqProposalUIModel;
import com.company.IntelligentPlatform.production.dto.ProductionOrderItemServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProductionOrderItemUIModel;
import com.company.IntelligentPlatform.production.model.ProdItemReqProposalTemplate;
import com.company.IntelligentPlatform.production.model.ProdItemRequestUnionTemplate;
import com.company.IntelligentPlatform.production.model.ProdOrderItemReqProposal;
import com.company.IntelligentPlatform.production.model.ProdPickingOrder;
import com.company.IntelligentPlatform.production.model.ProdPickingRefMaterialItem;
import com.company.IntelligentPlatform.production.model.ProdPlanItemReqProposal;
import com.company.IntelligentPlatform.production.model.ProductionOrder;
import com.company.IntelligentPlatform.production.model.ProductionOrderConfigureProxy;
import com.company.IntelligentPlatform.production.model.ProductionOrderItem;
import com.company.IntelligentPlatform.production.model.ProductionPlanItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceAsyncExecuteProxy;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.IDocumentNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityDoubleHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;


@Service
public class ProductionOrderItemManager {

    public static final String METHOD_ConvProductionOrderItemToUI = "convProductionOrderItemToUI";

    public static final String METHOD_ConvUIToProductionOrderItem = "convUIToProductionOrderItem";

    public static final String METHOD_ConvProductionOrderToItemUI = "convProductionOrderToItemUI";

    public static final String METHOD_ConvOrderMaterialSKUToItemUI = "convOrderMaterialSKUToItemUI";

    public static final String METHOD_ConvItemMaterialSKUToUI = "convItemMaterialSKUToUI";

    public static final String METHOD_ConvDocumentToItemReqProposalUI = "convDocumentToItemReqProposalUI";

    public static final String METHOD_ConvProdOrderItemReqProposalToUI = "convProdOrderItemReqProposalToUI";

    public static final String METHOD_ConvUIToProdOrderItemReqProposal = "convUIToProdOrderItemReqProposal";

    public static final String METHOD_ConvProductionOrderToProposalUI = "convProductionOrderToProposalUI";

    public static final String METHOD_ConvProductionOrderItemToProposalUI = "convProductionOrderItemToProposalUI";

    public static final String METHOD_ConvWarehouseToItemReqProposalUI = "convWarehouseToItemReqProposalUI";

    @Autowired
    protected ProductionOrderConfigureProxy productionOrderConfigureProxy;

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    @Autowired
    protected ProductionOrderManager productionOrderManager;

    @Autowired
    protected ProdPickingOrderManager prodPickingOrderManager;

    @Autowired
    protected ProdOrderItemReqProposalManager prodOrderItemReqProposalManager;

    @Autowired
    protected ProdPickingRefMaterialItemManager prodPickingRefMaterialItemManager;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected BillOfMaterialOrderManager billOfMaterialOrderManager;

    @Autowired
    protected ProductionOrderItemServiceUIModelExtension productionOrderItemServiceUIModelExtension;

    @Autowired
    protected ProdOrderItemReqProposalServiceUIModelExtension prodOrderItemReqProposalServiceUIModelExtension;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected WarehouseLogisticsManager warehouseLogisticsManager;

    @Autowired
    protected WarehouseManager warehouseManager;

    @Autowired
    protected OutboundDeliveryManager outboundDeliveryManager;

    @Autowired
    protected ServiceModuleProxy serviceModuleProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    protected Logger logger = LoggerFactory.getLogger(ProductionOrderItemManager.class);

    private Map<String, Map<Integer, String>> itemStatusMapLan = new HashMap<String, Map<Integer, String>>();

    public Map<Integer, String> initItemStatusMap(String languageCode) throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode, this.itemStatusMapLan, ProductionOrderItemUIModel.class, "itemStatus");
    }

    public Map<Integer, String> initDocumentTypeMap(String languageCode) throws ServiceEntityInstallationException {
        return serviceDocumentComProxy.getDocumentTypeMap(false, languageCode);
    }

    public List<PageHeaderModel> getPageHeaderModelList(ProductionOrderItem productionOrderItem, String client) throws ServiceEntityConfigureException {
        ProductionOrder productionOrder = (ProductionOrder) productionOrderManager.getEntityNodeByKey(productionOrderItem.getParentNodeUUID(), IServiceEntityNodeFieldConstant.UUID, ProductionOrder.NODENAME, client, null);
        int index = 0;
        List<PageHeaderModel> resultList = new ArrayList<PageHeaderModel>();
        if (productionOrder != null) {
            // In case current production order item is the direct production
            // item for order
            PageHeaderModel orderHeaderModel = productionOrderManager.getPageHeaderModel(productionOrder, index);
            if (orderHeaderModel != null) {
                index++;
                resultList.add(orderHeaderModel);
            }
            PageHeaderModel itemHeaderModel = getPageHeaderModel(productionOrderItem, index);
            if (itemHeaderModel != null) {
                resultList.add(itemHeaderModel);
            }
        } else {
            ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) productionOrderManager.getEntityNodeByKey(productionOrderItem.getParentNodeUUID(), IServiceEntityNodeFieldConstant.UUID, ProdOrderItemReqProposal.NODENAME, client, null);
            // In case current production order item is the sub item for
            // proposal
            if (prodOrderItemReqProposal != null) {
                List<PageHeaderModel> pageHeaderModelList = prodOrderItemReqProposalManager.getPageHeaderModelList(prodOrderItemReqProposal, client);
                if (!ServiceCollectionsHelper.checkNullList(pageHeaderModelList)) {
                    resultList.addAll(pageHeaderModelList);
                    index = pageHeaderModelList.size();
                }
                PageHeaderModel itemHeaderModel = getPageHeaderModel(productionOrderItem, index);
                if (itemHeaderModel != null) {
                    resultList.add(itemHeaderModel);
                }
            }
        }
        return resultList;
    }

    protected PageHeaderModel getPageHeaderModel(ProductionOrderItem productionOrderItem, int index) throws ServiceEntityConfigureException {
        if (productionOrderItem == null) {
            return null;
        }
        PageHeaderModel pageHeaderModel = new PageHeaderModel();
        pageHeaderModel.setPageTitle("productionOrderItemTitle");
        pageHeaderModel.setNodeInstId(ProductionOrderItem.NODENAME);
        pageHeaderModel.setUuid(productionOrderItem.getUuid());
        MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager.getEntityNodeByKey(productionOrderItem.getRefMaterialSKUUUID(), IServiceEntityNodeFieldConstant.UUID, MaterialStockKeepUnit.NODENAME, productionOrderItem.getClient(), null);
        if (materialStockKeepUnit != null) {
            pageHeaderModel.setHeaderName(materialStockKeepUnit.getName());
        }
        pageHeaderModel.setIndex(index);
        return pageHeaderModel;
    }

    /**
     * Core Logic to update production order item Item Status when picking
     * material item is set to done
     *
     * @param toFinishPickingMaterialItem
     * @throws ServiceEntityConfigureException
     */
    public void updateItemStatusFromPicking(ProdPickingRefMaterialItem toFinishPickingMaterialItem, String logonUserUUID, String organizationUUID) throws ServiceEntityConfigureException {
        ProductionOrderItem productionOrderItem = (ProductionOrderItem) productionOrderManager.getEntityNodeByKey(toFinishPickingMaterialItem.getRefProdOrderItemUUID(), IServiceEntityNodeFieldConstant.UUID, ProductionOrderItem.NODENAME, toFinishPickingMaterialItem.getClient(), null);
        if (productionOrderItem == null) {
            return;
        }
        /*
         * Check if all the picking material item has finished pick or not
         */
        List<ServiceEntityNode> prodPickingRefMaterialItemList = prodPickingRefMaterialItemManager.getSubRefMaterialItemListByOrderItem(productionOrderItem.getUuid(), toFinishPickingMaterialItem.getClient());
        boolean finishAllFlag = true;
        if (!ServiceCollectionsHelper.checkNullList(prodPickingRefMaterialItemList)) {
            for (ServiceEntityNode seNode : prodPickingRefMaterialItemList) {
                ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) seNode;
                if (prodPickingRefMaterialItem.getItemStatus() != ProdPickingRefMaterialItem.ITEMSTATUS_FINISHED) {
                    finishAllFlag = false;
                    break;
                }
            }
        }
        if (finishAllFlag) {
            finishProdOrderItem(productionOrderItem, logonUserUUID, organizationUUID);
        } else {
            inProcessProdOrderItem(productionOrderItem, logonUserUUID, organizationUUID);
        }
    }

    /**
     * Core Logic to set production order item status: Available, when all
     * picking item finished picked.
     *
     * @param productionOrderItem
     * @param logonUserUUID
     * @param organizationUUID
     */
    public void finishProdOrderItem(ProductionOrderItem productionOrderItem, String logonUserUUID, String organizationUUID) {
        productionOrderItem.setItemStatus(ProductionPlanItem.STATUS_AVAILABLE);
        productionOrderManager.updateSENode(productionOrderItem, logonUserUUID, organizationUUID);
    }

    /**
     * Core Logic to set production order item status: In process, when some
     * picking item finished picked.
     *
     * @param productionOrderItem
     * @param logonUserUUID
     * @param organizationUUID
     */
    public void inProcessProdOrderItem(ProductionOrderItem productionOrderItem, String logonUserUUID, String organizationUUID) {
        productionOrderItem.setItemStatus(ProductionPlanItem.STATUS_INPROCESS);
        productionOrderManager.updateSENode(productionOrderItem, logonUserUUID, organizationUUID);
    }

    public void convProductionOrderItemToUI(ProductionOrderItem productionOrderItem, ProductionOrderItemUIModel productionOrderItemUIModel) throws ServiceEntityInstallationException, ServiceEntityConfigureException {
        convProductionOrderItemToUI(productionOrderItem, productionOrderItemUIModel, null);
    }

    /**
     * Logic to calculate not in-plan amount, this method should be invoked
     * after the availableAmount and in-process amount is calculated
     *
     * @param productionOrderItem
     * @return
     */
    public static void updateItemStatus(ProductionOrderItem productionOrderItem) {
        /*
         * [Case1] In Process Amount != 0: default in process
         */
        if (productionOrderItem.getInProcessAmount() != 0) {
            productionOrderItem.setItemStatus(ProductionOrderItem.STATUS_INPROCESS);
            /*
             * [Case1.1] If there is already some picked, set part done
             */
            if (productionOrderItem.getPickedAmount() != 0) {
                productionOrderItem.setItemStatus(ProductionOrderItem.STATUS_PART_DONE);
            }
            /*
             * [Case1.2] If there is some available in stock at the same time, set part available
             * available is higher priority than picked
             */
            if (productionOrderItem.getInStockAmount() != 0 || productionOrderItem.getToPickAmount() != 0) {
                productionOrderItem.setItemStatus(ProductionOrderItem.STATUS_PART_AVAILABLE);
            }
            // [Case1.3] Check if not in plan amount
            checkSetNotInPlanStatus(productionOrderItem);
            return;
        }
        /*
         * [Case2] In Stock Or To Pick != 0: default in available
         */
        if (productionOrderItem.getInStockAmount() != 0 || productionOrderItem.getToPickAmount() != 0) {
            // Default Available: avilable status is with highest priority
            productionOrderItem.setItemStatus(ProductionOrderItem.STATUS_AVAILABLE);
            // [Case2.2] Check if not in plan amount
            checkSetNotInPlanStatus(productionOrderItem);
            return;
        }
        /*
         * [Case3] Picked Amount != 0
         */
        if (productionOrderItem.getPickedAmount() != 0) {
            if (productionOrderItem.getInProcessAmount() == 0 && productionOrderItem.getInStockAmount() == 0 && productionOrderItem.getToPickAmount() == 0) {
                productionOrderItem.setItemStatus(ProductionOrderItem.STATUS_ALL_DONE);
                checkSetNotInPlanStatus(productionOrderItem);
                return;
            }
            /*
             * [Case3.2] If there is some in process, set as part done
             * picked and done is higher priority than in process
             */
            if (productionOrderItem.getInProcessAmount() != 0) {
                productionOrderItem.setItemStatus(ProductionOrderItem.STATUS_PART_DONE);
            }
            /*
             * [Case3.2] If there is some available in stock at the same time, set part available
             * available is higher priority than picked/done
             */
            if (productionOrderItem.getInStockAmount() != 0 || productionOrderItem.getToPickAmount() != 0) {
                // Default Available
                productionOrderItem.setItemStatus(ProductionOrderItem.STATUS_AVAILABLE);
            }
        }
        checkSetNotInPlanStatus(productionOrderItem);
    }

    /**
     * Logic to calculate not in-plan amount, this method should be invoked
     * after the availableAmount and in-process amount is calculated
     *
     * @param productionOrderItem
     * @return
     */
    public static void checkSetNotInPlanStatus(ProductionOrderItem productionOrderItem) {
        double lackInPlanAmount = calculateNotInPlanAmount(productionOrderItem);
        productionOrderItem.setLackInPlanAmount(lackInPlanAmount);
        if (lackInPlanAmount > 0) {
            productionOrderItem.setItemStatus(ProductionOrderItem.STATUS_LACKINPLAN);
        }
    }

    /**
     * Logic to calculate not in-plan amount, this method should be invoked
     * after the availableAmount and in-process amount is calculated
     *
     * @param productionOrderItem
     * @return
     */
    public static double calculateNotInPlanAmount(ProductionOrderItem productionOrderItem) {
        // double lackInPlanAmount = productionOrderItem.getAmountWithLossRate()
        // - productionOrderItem.getInStockAmount()
        // - productionOrderItem.getInProcessAmount()
        // - productionOrderItem.getPickedAmount()
        // - productionOrderItem.getToPickAmount();
        double lackInPlanAmount = productionOrderItem.getAmountWithLossRate() - productionOrderItem.getSuppliedAmount();
        return lackInPlanAmount;
    }

    /**
     * Using Async way to update production order post tasks.
     *
     * @param productionOrderItemServiceModel
     * @param logonUserUUID
     * @param organizationUUID
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     * @throws ServiceModuleProxyException
     */
    public void postUpdateProductionOrderItemAsyncWrapper(ProductionOrderItemServiceModel productionOrderItemServiceModel, String logonUserUUID, String organizationUUID)
            throws ServiceEntityConfigureException, MaterialException, ServiceModuleProxyException, ServiceComExecuteException {
        try {
            ServiceAsyncExecuteProxy.executeAsyncWrapper(productionOrderItemServiceModel, (prodOrderItemServiceModel, logonInfo) -> {
                try {
                    postUpdateProductionOrderItem(prodOrderItemServiceModel, logonUserUUID, organizationUUID);
                    productionOrderManager.updateServiceModule(ProductionOrderItemServiceModel.class, prodOrderItemServiceModel, logonUserUUID, organizationUUID);
                } catch (ServiceModuleProxyException | ServiceComExecuteException | MaterialException |
                         DocActionException e) {
                    throw new ServiceEntityExceptionContainer(e);
                } catch (ServiceEntityConfigureException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                }
                return productionOrderItemServiceModel;
            }, null);
        } catch (ServiceEntityExceptionContainer serviceEntityExceptionContainer) {
            ServiceEntityException coreException = serviceEntityExceptionContainer.getCoreException();
            if (coreException != null) {
                if (coreException instanceof ServiceComExecuteException) {
                    throw (ServiceComExecuteException) coreException;
                }
                throw new ServiceComExecuteException(ServiceComExecuteException.PARA_SYSTEM_ERROR, coreException.getErrorMessage());
            } else {
                throw new ServiceComExecuteException(ServiceComExecuteException.TYPE_SYSTEM_WRONG);
            }
        }
    }

    /**
     * Entrance method to update production order item
     *
     * @param productionOrderItemServiceModel
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    public void postUpdateProductionOrderItem(ProductionOrderItemServiceModel productionOrderItemServiceModel, String logonUserUUID, String organizationUUID) throws ServiceEntityConfigureException, MaterialException, ServiceComExecuteException, DocActionException {
        /*
         * [Step1] Update Info for Production Order Item
         */
        ProductionOrderItem productionOrderItem = productionOrderItemServiceModel.getProductionOrderItem();
        List<String> warehouseUUIDList = productionOrderManager.getWarehouseUUIDList(productionOrderItem.getRootNodeUUID(), productionOrderItem.getClient());
        refreshOrderItemFromPickingItem(productionOrderItemServiceModel, warehouseUUIDList);
        /*
         * [Step2] Update each prodOrderItemReqProposal
         */
        //		if (!ServiceCollectionsHelper.checkNullList(productionOrderItemServiceModel.getProdOrderItemReqProposalList())) {
        //			for (ProdOrderItemReqProposalServiceModel prodOrderItemReqProposalServiceModel : productionOrderItemServiceModel
        //					.getProdOrderItemReqProposalList()) {
        //				ProdOrderItemReqProposal prodOrderItemReqProposal = prodOrderItemReqProposalServiceModel
        //						.getProdOrderItemReqProposal();
        //				// Update reference document
        //				prodOrderItemReqProposalManager.updateProposalRefDocInfo(prodOrderItemReqProposal, null);
        //			}
        //		}
        /*
         * [Step3] update production order's actual amount
         */
        //		ProductionOrder productionOrder = (ProductionOrder) productionOrderManager
        //				.getEntityNodeByKey(productionOrderItem.getRootNodeUUID(), IServiceEntityNodeFieldConstant.UUID,
        //						ProductionOrder.NODENAME, productionOrderItem.getClient(), null);
        //		List<ProductionOrderItemServiceModel> requestList = ServiceCollectionsHelper.asList(productionOrderItemServiceModel);
        //		productionOrderManager.setActualAmountProdItem(requestList, productionOrder, logonUserUUID, organizationUUID);
    }

    /**
     * Core Logic to calculate production order item or req item pick status by calculating its sub picking material item
     *
     * @param prodPickingMaterialItemList: all the sub picking material item list needes to be considered.
     * @return picking status
     */
    public static int calItemPickStatus(List<ServiceEntityNode> prodPickingMaterialItemList) {
        if (ServiceCollectionsHelper.checkNullList(prodPickingMaterialItemList)) {
            return ProdPickingOrder.STATUS_INITIAL;
        }
        int totalApprove = 0, totalInProcess = 0, totalFinished = 0;
        int length = prodPickingMaterialItemList.size();
        for (ServiceEntityNode seNode : prodPickingMaterialItemList) {
            ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) seNode;
            if (prodPickingRefMaterialItem.getItemStatus() == ProdPickingOrder.STATUS_INITIAL) {
                return ProdPickingOrder.STATUS_INITIAL;
            }
            if (prodPickingRefMaterialItem.getItemStatus() == ProdPickingOrder.STATUS_APPROVED) {
                totalApprove++;
            }
            if (prodPickingRefMaterialItem.getItemStatus() == ProdPickingOrder.STATUS_INPROCESS) {
                totalInProcess++;
            }
            if (prodPickingRefMaterialItem.getItemStatus() == ProdPickingOrder.STATUS_DELIVERYDONE) {
                totalFinished++;
            }
            if (prodPickingRefMaterialItem.getItemStatus() == ProdPickingOrder.STATUS_PROCESSDONE) {
                totalFinished++;
            }
        }
        if (totalFinished == length) {
            // In case all items is finished, then return finished
            return ProdPickingOrder.STATUS_DELIVERYDONE;
        }
        if (totalInProcess != 0 || totalFinished != 0) {
            // In case both in process and finished exists, then return in process
            return ProdPickingOrder.STATUS_INPROCESS;
        }
        if (totalApprove == length) {
            return ProdPickingOrder.STATUS_APPROVED;
        }
        return ProdPickingOrder.STATUS_APPROVED;

    }

    /**
     * Core Logic to calculate order item pick status based on all sub proposal
     *
     * @return
     */
    @Deprecated
    public static int calculateOrderItemPickStatus(List<ServiceEntityNode> prodOrderItemReqProposalList) {
        if (ServiceCollectionsHelper.checkNullList(prodOrderItemReqProposalList)) {
            return ProdPickingOrder.STATUS_INITIAL;
        }
        int totalInit = 0, totalApprove = 0, totalInProcess = 0, totalFinished = 0;
        int length = prodOrderItemReqProposalList.size();
        for (ServiceEntityNode seNode : prodOrderItemReqProposalList) {
            ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) seNode;
            if (prodOrderItemReqProposal.getPickStatus() == ProdPickingOrder.STATUS_INITIAL) {
                totalInit++;
                return ProdPickingOrder.STATUS_INITIAL;
            }
            if (prodOrderItemReqProposal.getPickStatus() == ProdPickingOrder.STATUS_APPROVED) {
                totalApprove++;
            }
            if (prodOrderItemReqProposal.getPickStatus() == ProdPickingOrder.STATUS_INPROCESS) {
                totalInProcess++;
            }
            if (prodOrderItemReqProposal.getPickStatus() == ProdPickingOrder.STATUS_DELIVERYDONE) {
                totalFinished++;
            }
            if (prodOrderItemReqProposal.getPickStatus() == ProdPickingOrder.STATUS_PROCESSDONE) {
                totalFinished++;
            }
        }
        if (totalFinished == length) {
            return ProdPickingOrder.STATUS_DELIVERYDONE;
        }
        if (totalInProcess != 0 || totalFinished != 0) {
            return ProdPickingOrder.STATUS_INPROCESS;
        }
        if (totalApprove == length) {
            return ProdPickingOrder.STATUS_APPROVED;
        }
        return ProdPickingOrder.STATUS_APPROVED;
    }


    /**
     * Get all relative picking material item list by item UUID
     *
     * @param itemUUID
     * @param client
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<ServiceEntityNode> getRefPickingMatItemList(String itemUUID, String client) throws ServiceEntityConfigureException {
        return prodPickingOrderManager.getEntityNodeListByKey(itemUUID, ProdPickingRefMaterialItem.FIELD_PRODORDER_ITEMUUID, ProdPickingRefMaterialItem.NODENAME, client, null);
    }

    /**
     * Get all the reserved and active stock list with [in-stock] status and not out-bound has been generated
     *
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<ServiceEntityNode> getReservedInStockStoreItemList(String parentNodeUUID, String client) throws ServiceEntityConfigureException {
        List<ServiceEntityNode> prodPickingRefMatItemList = prodPickingOrderManager.getEntityNodeListByKey(parentNodeUUID, IServiceEntityNodeFieldConstant.ROOTNODEUUID, ProdPickingRefMaterialItem.NODENAME, client, null);
        List<ServiceEntityNode> resultStoreItemList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(prodPickingRefMatItemList)) {
            for (ServiceEntityNode serviceEntityNode : prodPickingRefMatItemList) {
                ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) serviceEntityNode;
                List<ServiceEntityNode> tmpStoreItemList = prodPickingRefMaterialItemManager.getReservedInStockStoreItemList(prodPickingRefMaterialItem);
                ServiceCollectionsHelper.mergeToList(resultStoreItemList, tmpStoreItemList);
            }
        }
        return resultStoreItemList;
    }


    /**
     * Entrance Method to get all the available reserved stock list
     *
     * @return
     */
    public List<ServiceEntityNode> getInStockItemListBatch(String baseUUID, String client) throws ServiceEntityConfigureException {
        List<ServiceEntityNode> allInStockItemList = new ArrayList<>();
        List<ServiceEntityNode> prodPickingMatItemList = productionOrderManager.getAllRelativePickingMatItemList(baseUUID, client);
        for (ServiceEntityNode seNode : prodPickingMatItemList) {
            ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) seNode;
            List<ServiceEntityNode> inStockStoreItemList = prodPickingRefMaterialItemManager.getReservedInStockStoreItemList(prodPickingRefMaterialItem);
            ServiceCollectionsHelper.mergeToList(allInStockItemList, inStockStoreItemList);
        }
        return allInStockItemList;
    }

    /**
     * Logic to return all the picking material item list, which could be used for picking.
     *
     * @return
     */
    public List<ServiceEntityNode> getToPickListBatch(String baseUUID, String client) throws ServiceEntityConfigureException, MaterialException, DocActionException {
        List<ServiceEntityNode> prodPickingMatItemList = productionOrderManager.getAllRelativePickingMatItemList(baseUUID, client);
        return prodPickingRefMaterialItemManager.getToPickMaterialItemList(prodPickingMatItemList);
    }


    /**
     * Core Logic to refresh / update key information for production order item or req item models by calculating the sub picking material item
     *
     * @param prodItemRequestUnionTemplate:    raw input item model
     * @param prodPickingRefMaterialItemList:  all the sub picking materia item list needs to be considered
     * @param allPickingExtendAmountModelList: all the possible picking extend amount model
     * @return refreshed item model
     */
    public ProdItemRequestUnionTemplate refreshItemInfoByPickingItemCore(ProdItemRequestUnionTemplate prodItemRequestUnionTemplate, List<ProdPickingExtendAmountModel> allPickingExtendAmountModelList, List<ServiceEntityNode> prodPickingRefMaterialItemList) throws MaterialException, ServiceEntityConfigureException, ServiceComExecuteException {
        List<StorageCoreUnit> inStockCoreUnitList = new ArrayList<>();
        List<StorageCoreUnit> inProcessStorageCoreUnitList = new ArrayList<>();
        List<StorageCoreUnit> toPickStorageCoreUnitList = new ArrayList<>();
        List<StorageCoreUnit> pickedStorageCoreUnitList = new ArrayList<>();
        List<StorageCoreUnit> suppliedStorageCoreUnitList = new ArrayList<>();
        MaterialStockKeepUnit materialStockKeepUnit = materialStockKeepUnitManager.getMaterialSKUWrapper(prodItemRequestUnionTemplate.getRefMaterialSKUUUID(), prodItemRequestUnionTemplate.getClient(), null);
        /*
         * [Step2] Traverse each sub picking material item
         */
        if (!ServiceCollectionsHelper.checkNullList(prodPickingRefMaterialItemList)) {
            for (ServiceEntityNode seNode : prodPickingRefMaterialItemList) {
                ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) seNode;
                ProdPickingExtendAmountModel prodPickingExtendAmountModel = prodPickingRefMaterialItemManager.filterPickingExtendModelOnline(prodPickingRefMaterialItem.getUuid(), tempPickingMaterialItem -> {
                    return tempPickingMaterialItem.getUuid();
                }, allPickingExtendAmountModelList);
                if (prodPickingExtendAmountModel == null) {
                    // log this system issue and continue;
                    logger.error("empty picking item filterd:" + prodPickingRefMaterialItem.getUuid());
                    continue;
                }
                StorageCoreUnit suppliedStorageUnit = prodPickingExtendAmountModel.getSuppliedAmount();
                if (suppliedStorageUnit != null && suppliedStorageUnit.getAmount() != 0) {
                    suppliedStorageCoreUnitList.add(suppliedStorageUnit);
                }
                StorageCoreUnit pickedStorageUnit = prodPickingExtendAmountModel.getPickedAmount();
                if (pickedStorageUnit != null && pickedStorageUnit.getAmount() != 0) {
                    pickedStorageCoreUnitList.add(pickedStorageUnit);
                }
                StorageCoreUnit inStockCoreUnit = prodPickingExtendAmountModel.getInStockAmount();
                if (inStockCoreUnit != null && inStockCoreUnit.getAmount() != 0) {
                    inStockCoreUnitList.add(inStockCoreUnit);
                }
                StorageCoreUnit inProcessStorageCoreUnit = prodPickingExtendAmountModel.getInProcessAmount();
                if (inProcessStorageCoreUnit != null && inProcessStorageCoreUnit.getAmount() != 0) {
                    inProcessStorageCoreUnitList.add(inProcessStorageCoreUnit);
                }
                StorageCoreUnit toPickStorageUnit = prodPickingExtendAmountModel.getToPickAmount();
                if (toPickStorageUnit != null && toPickStorageUnit.getAmount() != 0) {
                    toPickStorageCoreUnitList.add(toPickStorageUnit);
                }
            }
        }
        /*
         * [Step3] Merge all the sub picking amount to production order item
         */
        StorageCoreUnit toPickStorageUnit = materialStockKeepUnitManager.mergeStorageUnitCore(toPickStorageCoreUnitList, prodItemRequestUnionTemplate.getClient());
        if (toPickStorageUnit != null) {
            prodItemRequestUnionTemplate.setToPickAmount(toPickStorageUnit.getAmount());
        } else {
            prodItemRequestUnionTemplate.setToPickAmount(0);
        }
        // [Step3.2] Calculate: picked amount
        StorageCoreUnit pickedStorageUnit = materialStockKeepUnitManager.mergeStorageUnitCore(pickedStorageCoreUnitList, prodItemRequestUnionTemplate.getClient());
        if (pickedStorageUnit != null) {
            prodItemRequestUnionTemplate.setPickedAmount(pickedStorageUnit.getAmount());
        } else {
            prodItemRequestUnionTemplate.setPickedAmount(0);
        }
        // [Step3.3] Calculate: reserved storage
        StorageCoreUnit inStockStorageCoreUnit = materialStockKeepUnitManager.mergeStorageUnitCore(inStockCoreUnitList, prodItemRequestUnionTemplate.getClient());
        if (inStockStorageCoreUnit != null) {
            prodItemRequestUnionTemplate.setInStockAmount(inStockStorageCoreUnit.getAmount());
        } else {
            prodItemRequestUnionTemplate.setInStockAmount(0);
        }
        // [Step3.4] Calculate in process
        StorageCoreUnit inprocessStorageCoreUnit = materialStockKeepUnitManager.mergeStorageUnitCore(inProcessStorageCoreUnitList, prodItemRequestUnionTemplate.getClient());
        if (inprocessStorageCoreUnit != null) {
            prodItemRequestUnionTemplate.setInProcessAmount(inprocessStorageCoreUnit.getAmount());
        } else {
            prodItemRequestUnionTemplate.setInProcessAmount(0);
        }
        // [Step3.5] Calculate all supplied Amount
        StorageCoreUnit suppliedStorageCoreUnit = materialStockKeepUnitManager.mergeStorageUnitCore(suppliedStorageCoreUnitList, prodItemRequestUnionTemplate.getClient());
        if (suppliedStorageCoreUnit != null) {
            prodItemRequestUnionTemplate.setSuppliedAmount(suppliedStorageCoreUnit.getAmount());
        } else {
            prodItemRequestUnionTemplate.setSuppliedAmount(0);
        }
        /*
         * [Step4] Important: post-process if mulitiple units
         */
        List<StorageCoreUnit> storageCoreUnitList = new ArrayList<>();
        if (toPickStorageUnit != null) {
            storageCoreUnitList.add(toPickStorageUnit);
        }
        if (pickedStorageUnit != null) {
            storageCoreUnitList.add(pickedStorageUnit);
        }
        if (inStockStorageCoreUnit != null) {
            storageCoreUnitList.add(inStockStorageCoreUnit);
        }
        if (inprocessStorageCoreUnit != null) {
            storageCoreUnitList.add(inprocessStorageCoreUnit);
        }
        if (suppliedStorageCoreUnit != null) {
            storageCoreUnitList.add(suppliedStorageCoreUnit);
        }
        String refUnitUUID = unifyStorageCoreUnit(storageCoreUnitList, materialStockKeepUnit);
        if (refUnitUUID != null && !refUnitUUID.equals(prodItemRequestUnionTemplate.getRefUnitUUID())) {
            prodItemRequestUnionTemplate.setRefUnitUUID(refUnitUUID);
        }
        /*
         * [Step5] Calculate picking status by sub picking mat item
         */
        int pickStatus = calItemPickStatus(prodPickingRefMaterialItemList);
        prodItemRequestUnionTemplate.setPickStatus(pickStatus);
        return prodItemRequestUnionTemplate;
    }

    /**
     * Unify all the storage core unit list to same unit
     *
     * @param storageCoreUnitList
     * @param refTemplateMaterialSKU
     * @return result unify refUnit UUID
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    public String unifyStorageCoreUnit(List<StorageCoreUnit> storageCoreUnitList, MaterialStockKeepUnit refTemplateMaterialSKU) throws ServiceEntityConfigureException, MaterialException {
        if (ServiceCollectionsHelper.checkNullList(storageCoreUnitList)) {
            return null;
        }
        /*
         * [Step1] Check
         */
        int length = storageCoreUnitList.size();
        String refUnitUUID = storageCoreUnitList.get(0).getRefUnitUUID();
        String mainUnitUUID = materialStockKeepUnitManager.getMainUnitUUID(refTemplateMaterialSKU);
        boolean convertFlag = false, maintUnitFlag = false;
        for (int i = 1; i < length; i++) {
            if (storageCoreUnitList.get(i) == null) {
                continue;
            }
            if (!refUnitUUID.equals(storageCoreUnitList.get(i).getRefUnitUUID())) {
                convertFlag = true;
            }
            if (mainUnitUUID.equals(storageCoreUnitList.get(i).getRefUnitUUID())) {
                maintUnitFlag = true;
            }
        }
        if (!convertFlag) {
            return refUnitUUID;
        }
        for (int i = 0; i < length; i++) {
            if (storageCoreUnitList.get(i) == null) {
                continue;
            }
            if (maintUnitFlag) {
                materialStockKeepUnitManager.convertUnit(storageCoreUnitList.get(i), mainUnitUUID, refTemplateMaterialSKU.getClient());
            } else {
                if (i != 0) {
                    materialStockKeepUnitManager.convertUnit(storageCoreUnitList.get(i), refUnitUUID, refTemplateMaterialSKU.getClient());
                }
            }
        }
        return storageCoreUnitList.get(0).getRefUnitUUID();
    }

    public ProductionOrderItem copyUnionTemplateToProductionOrderItem(ProdItemRequestUnionTemplate prodItemRequestUnionTemplate, ProductionOrderItem productionOrderItem) {
        if (productionOrderItem == null || prodItemRequestUnionTemplate == null) {
            return productionOrderItem;
        }
        docFlowProxy.copyDocMatItemMutual(prodItemRequestUnionTemplate, productionOrderItem, true);
        productionOrderItem.setPickStatus(prodItemRequestUnionTemplate.getPickStatus());
        productionOrderItem.setPickedAmount(prodItemRequestUnionTemplate.getPickedAmount());
        productionOrderItem.setToPickAmount(prodItemRequestUnionTemplate.getToPickAmount());
        productionOrderItem.setInProcessAmount(prodItemRequestUnionTemplate.getInProcessAmount());
        productionOrderItem.setSuppliedAmount(prodItemRequestUnionTemplate.getSuppliedAmount());
        productionOrderItem.setInStockAmount(prodItemRequestUnionTemplate.getInStockAmount());
        productionOrderItem.setAvailableAmount(prodItemRequestUnionTemplate.getAvailableAmount());
        return productionOrderItem;
    }

    public ProdItemRequestUnionTemplate copyProductionOrderItemToUnionTemplate(ProductionOrderItem productionOrderItem, ProdItemRequestUnionTemplate prodItemRequestUnionTemplate) {
        if (productionOrderItem == null || prodItemRequestUnionTemplate == null) {
            return prodItemRequestUnionTemplate;
        }
        DocFlowProxy.copyDocMatItemMutual(productionOrderItem, prodItemRequestUnionTemplate, true);
        prodItemRequestUnionTemplate.setPickStatus(productionOrderItem.getPickStatus());
        prodItemRequestUnionTemplate.setPickedAmount(productionOrderItem.getPickedAmount());
        prodItemRequestUnionTemplate.setToPickAmount(productionOrderItem.getToPickAmount());
        prodItemRequestUnionTemplate.setSuppliedAmount(productionOrderItem.getSuppliedAmount());
        prodItemRequestUnionTemplate.setInProcessAmount(productionOrderItem.getInProcessAmount());
        prodItemRequestUnionTemplate.setInStockAmount(productionOrderItem.getInStockAmount());
        prodItemRequestUnionTemplate.setAvailableAmount(productionOrderItem.getAvailableAmount());
        return prodItemRequestUnionTemplate;
    }

    public ProdItemReqProposalTemplate copyUnionTemplateToOrderItemReqProposal(ProdItemRequestUnionTemplate prodItemRequestUnionTemplate, ProdItemReqProposalTemplate prodItemReqProposalTemplate) {
        if (prodItemReqProposalTemplate == null || prodItemRequestUnionTemplate == null) {
            return prodItemReqProposalTemplate;
        }
        DocFlowProxy.copyDocMatItemMutual(prodItemRequestUnionTemplate, prodItemReqProposalTemplate, true);
        prodItemReqProposalTemplate.setPickStatus(prodItemRequestUnionTemplate.getPickStatus());
        prodItemReqProposalTemplate.setPickedAmount(prodItemRequestUnionTemplate.getPickedAmount());
        prodItemReqProposalTemplate.setToPickAmount(prodItemRequestUnionTemplate.getToPickAmount());
        prodItemReqProposalTemplate.setSuppliedAmount(prodItemRequestUnionTemplate.getSuppliedAmount());
        prodItemReqProposalTemplate.setInProcessAmount(prodItemRequestUnionTemplate.getInProcessAmount());
        prodItemReqProposalTemplate.setInStockAmount(prodItemRequestUnionTemplate.getInStockAmount());
        prodItemReqProposalTemplate.setAvailableAmount(prodItemRequestUnionTemplate.getAvailableAmount());
        return prodItemReqProposalTemplate;
    }

    public ProdItemRequestUnionTemplate copyOrderItemReqProposalToUnionTemplate(ProdItemReqProposalTemplate prodItemReqProposalTemplate, ProdItemRequestUnionTemplate prodItemRequestUnionTemplate) {
        if (prodItemReqProposalTemplate == null || prodItemRequestUnionTemplate == null) {
            return prodItemRequestUnionTemplate;
        }
        DocFlowProxy.copyDocMatItemMutual(prodItemReqProposalTemplate, prodItemRequestUnionTemplate, true);
        prodItemRequestUnionTemplate.setPickStatus(prodItemRequestUnionTemplate.getPickStatus());
        prodItemRequestUnionTemplate.setPickedAmount(prodItemRequestUnionTemplate.getPickedAmount());
        prodItemRequestUnionTemplate.setToPickAmount(prodItemRequestUnionTemplate.getToPickAmount());
        prodItemRequestUnionTemplate.setSuppliedAmount(prodItemRequestUnionTemplate.getSuppliedAmount());
        prodItemRequestUnionTemplate.setInProcessAmount(prodItemRequestUnionTemplate.getInProcessAmount());
        prodItemRequestUnionTemplate.setInStockAmount(prodItemRequestUnionTemplate.getInStockAmount());
        prodItemRequestUnionTemplate.setAvailableAmount(prodItemRequestUnionTemplate.getAvailableAmount());
        return prodItemRequestUnionTemplate;
    }

    /**
     * Refresh Production order item key amount and status information by calculating all the sub picking item
     * In this way, all the picking item status should be calculated.
     *
     * @param productionOrderItemServiceModel
     * @param warehouseUUIDList               : all warehouse UUID list
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    public List<ProdPickingExtendAmountModel> refreshOrderItemFromPickingItem(ProductionOrderItemServiceModel productionOrderItemServiceModel, List<String> warehouseUUIDList) throws ServiceEntityConfigureException, MaterialException, ServiceComExecuteException, DocActionException {
        ProductionOrderItem productionOrderItem = productionOrderItemServiceModel.getProductionOrderItem();
        /*
         * [Step1] Get all the possible picking material item and get the accurate amount information
         */
        List<ServiceEntityNode> prodPickingRefMaterialItemList = prodPickingRefMaterialItemManager.getSubRefMaterialItemListByOrderItem(productionOrderItem.getUuid(), productionOrderItem.getClient());
        List<ProdPickingExtendAmountModel> allPickingExtendAmountModelList = prodPickingRefMaterialItemManager.getPickingItemAmountListWrapper(prodPickingRefMaterialItemList);
        ProdItemRequestUnionTemplate prodItemRequestUnionTemplate = new ProdItemRequestUnionTemplate();
        prodItemRequestUnionTemplate = copyProductionOrderItemToUnionTemplate(productionOrderItem, prodItemRequestUnionTemplate);
        /*
         * [Step2] Call logic to refresh amount / status key information by all sub picking material item
         */
        refreshItemInfoByPickingItemCore(prodItemRequestUnionTemplate, allPickingExtendAmountModelList, prodPickingRefMaterialItemList);
        productionOrderItem = copyUnionTemplateToProductionOrderItem(prodItemRequestUnionTemplate, productionOrderItem);
        // [Step 2.1] Calculate actual amount, and set price information
        productionOrderItem.setActualAmount(productionOrderItem.getSuppliedAmount());
        setProdItemPrice(productionOrderItem);
        // [Step 2.2] Calculate free storage
        StorageCoreUnit freeStorageCoreUnit = prodPickingRefMaterialItemManager.getFreeStoreAmount(productionOrderItem.getRefMaterialSKUUUID(), productionOrderItem.getClient(), warehouseUUIDList);
        if (freeStorageCoreUnit != null) {
            productionOrderItem.setAvailableAmount(freeStorageCoreUnit.getAmount());
        } else {
            productionOrderItem.setAvailableAmount(0);
        }
        /*
         * [Step3] Try to check and set not-in-plan amount and status
         */
        ProductionOrderItemManager.updateItemStatus(productionOrderItem);

        /*
         * [Step4] Update all sub proposal material item list
         */
        List<ProdOrderItemReqProposalServiceModel> prodOrderItemReqProposalList = productionOrderItemServiceModel.getProdOrderItemReqProposalList();
        if (!ServiceCollectionsHelper.checkNullList(prodOrderItemReqProposalList)) {
            for (ProdOrderItemReqProposalServiceModel prodOrderItemReqProposalServiceModel : prodOrderItemReqProposalList) {
                ProdOrderItemReqProposal prodOrderItemReqProposal = prodOrderItemReqProposalServiceModel.getProdOrderItemReqProposal();
                prodOrderItemReqProposalManager.refreshItemStatus(prodOrderItemReqProposal, allPickingExtendAmountModelList);
            }
        }
        return allPickingExtendAmountModelList;
    }

    /**
     * [Internal method] calculate / update item price information to production order item
     *
     * @param productionOrderItem
     */
    private void setProdItemPrice(ProductionOrderItem productionOrderItem) throws ServiceEntityConfigureException, MaterialException, ServiceComExecuteException {
        /*
         * [Step1] Add all the store unit to actualCoreUnit, also calculate
         * actual item cost
         */
        MaterialStockKeepUnit materialStockKeepUnit = materialStockKeepUnitManager.getMaterialSKUWrapper(productionOrderItem.getRefMaterialSKUUUID(), productionOrderItem.getClient(), null);
        if (materialStockKeepUnit == null) {
            logger.error("No MaterialItem inside production order item: " + productionOrderItem.getUuid());
            return;
        }
        StorageCoreUnit actualCoreUnit = new StorageCoreUnit(productionOrderItem.getRefMaterialSKUUUID(), productionOrderItem.getRefUnitUUID(), productionOrderItem.getActualAmount());
        double itemCostActual = materialStockKeepUnitManager.calculatePrice(actualCoreUnit, materialStockKeepUnit, materialStockKeepUnit.getUnitCost());
        productionOrderItem.setItemCostActual(itemCostActual);
        /*
         * [Step1.5] Also calculate item cost loss rate and pure item cost
         */
        StorageCoreUnit pureCoreUnit = new StorageCoreUnit(productionOrderItem.getRefMaterialSKUUUID(), productionOrderItem.getRefUnitUUID(), productionOrderItem.getAmount());
        double itemCost = materialStockKeepUnitManager.calculatePrice(pureCoreUnit, materialStockKeepUnit, materialStockKeepUnit.getUnitCost());
        productionOrderItem.setItemPrice(itemCost);

        StorageCoreUnit storageCoreUnitLossRate = new StorageCoreUnit(productionOrderItem.getRefMaterialSKUUUID(), productionOrderItem.getRefUnitUUID(), productionOrderItem.getAmountWithLossRate());
        double itemCostLossRate = materialStockKeepUnitManager.calculatePrice(storageCoreUnitLossRate, materialStockKeepUnit, materialStockKeepUnit.getUnitCost());
        productionOrderItem.setItemCostLossRate(itemCostLossRate);
        /*
         * [Step2] Convert Unit to the same as productionOrderItem if necessary
         */
        if (!productionOrderItem.getRefUnitUUID().equals(actualCoreUnit.getRefUnitUUID())) {
            StorageCoreUnit tmpCoreUnit = new StorageCoreUnit(productionOrderItem.getRefMaterialSKUUUID(), productionOrderItem.getRefUnitUUID(), 1);
            materialStockKeepUnitManager.mergeStorageUnitCore(actualCoreUnit, tmpCoreUnit, StorageCoreUnit.OPERATOR_ADD, productionOrderItem.getClient());
            materialStockKeepUnitManager.mergeStorageUnitCore(actualCoreUnit, tmpCoreUnit, StorageCoreUnit.OPERATOR_MINUS, productionOrderItem.getClient());
        }
    }


    /**
     * Refresh Production order item amount & information status by calculate sub proposal list
     *
     * @param productionOrderItemServiceModel
     * @param warehouseUUIDList               : all warehouse UUID list
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    @Deprecated
    public void refreshOrderItemStatus(ProductionOrderItemServiceModel productionOrderItemServiceModel, List<String> warehouseUUIDList) throws ServiceEntityConfigureException, MaterialException, DocActionException {
        ProductionOrderItem productionOrderItem = productionOrderItemServiceModel.getProductionOrderItem();
        List<ProdOrderItemReqProposalServiceModel> prodOrderItemReqProposalList = productionOrderItemServiceModel.getProdOrderItemReqProposalList();
        List<ServiceEntityNode> seProdOrderItemReqProposalList = new ArrayList<>();
        List<StorageCoreUnit> inStockCoreUnitList = new ArrayList<StorageCoreUnit>();
        List<StorageCoreUnit> freeStorageCoreUnitList = new ArrayList<StorageCoreUnit>();
        List<StorageCoreUnit> inProcessStorageCoreUnitList = new ArrayList<StorageCoreUnit>();
        List<StorageCoreUnit> toPickStorageCoreUnitList = new ArrayList<StorageCoreUnit>();
        List<StorageCoreUnit> pickedStorageCoreUnitList = new ArrayList<StorageCoreUnit>();
        List<StorageCoreUnit> suppliedStorageCoreUnitList = new ArrayList<StorageCoreUnit>();
        if (!ServiceCollectionsHelper.checkNullList(prodOrderItemReqProposalList)) {
            for (ProdOrderItemReqProposalServiceModel prodOrderItemReqProposalServiceModel : prodOrderItemReqProposalList) {
                ProdOrderItemReqProposal prodOrderItemReqProposal = prodOrderItemReqProposalServiceModel.getProdOrderItemReqProposal();
                seProdOrderItemReqProposalList.add(prodOrderItemReqProposal);
                /*
                 * [Step1] Refresh each proposal's item status and amount
                 */
                prodOrderItemReqProposalManager.refreshItemStatus(prodOrderItemReqProposal);
                StorageCoreUnit inStockCoreUnit = new StorageCoreUnit(prodOrderItemReqProposal.getRefMaterialSKUUUID(), prodOrderItemReqProposal.getRefUnitUUID(), prodOrderItemReqProposal.getInStockAmount());
                if (inStockCoreUnit != null && inStockCoreUnit.getAmount() != 0) {
                    inStockCoreUnitList.add(inStockCoreUnit);
                }
                // [Step2] Calculate free storage
                StorageCoreUnit freeStorageCoreUnit = prodPickingRefMaterialItemManager.getFreeStoreAmount(prodOrderItemReqProposal.getRefMaterialSKUUUID(), prodOrderItemReqProposal.getClient(), warehouseUUIDList);
                if (freeStorageCoreUnit != null && freeStorageCoreUnit.getAmount() != 0) {
                    freeStorageCoreUnitList.add(freeStorageCoreUnit);
                }
                // [Step2.1] Merge in process
                StorageCoreUnit inProcessStorageCoreUnit = new StorageCoreUnit(prodOrderItemReqProposal.getRefMaterialSKUUUID(), prodOrderItemReqProposal.getRefUnitUUID(), prodOrderItemReqProposal.getInProcessAmount());
                if (inProcessStorageCoreUnit != null && inProcessStorageCoreUnit.getAmount() != 0) {
                    inProcessStorageCoreUnitList.add(inProcessStorageCoreUnit);
                }
                // [Step2.2] Merge to pick amount
                StorageCoreUnit toPickStorageUnit = new StorageCoreUnit(prodOrderItemReqProposal.getRefMaterialSKUUUID(), prodOrderItemReqProposal.getRefUnitUUID(), prodOrderItemReqProposal.getToPickAmount());
                if (toPickStorageUnit != null && toPickStorageUnit.getAmount() != 0) {
                    toPickStorageCoreUnitList.add(toPickStorageUnit);
                }
                // [Step2.3] Merge picked amount
                StorageCoreUnit pickedStorageUnit = new StorageCoreUnit(prodOrderItemReqProposal.getRefMaterialSKUUUID(), prodOrderItemReqProposal.getRefUnitUUID(), prodOrderItemReqProposal.getPickedAmount());
                if (pickedStorageUnit != null && pickedStorageUnit.getAmount() != 0) {
                    pickedStorageCoreUnitList.add(pickedStorageUnit);
                }
                // [Step2.4] Merge picked amount
                StorageCoreUnit suppliedStorageUnit = new StorageCoreUnit(prodOrderItemReqProposal.getRefMaterialSKUUUID(), prodOrderItemReqProposal.getRefUnitUUID(), prodOrderItemReqProposal.getSuppliedAmount());
                if (suppliedStorageUnit != null && suppliedStorageUnit.getAmount() != 0) {
                    suppliedStorageCoreUnitList.add(suppliedStorageUnit);
                }
            }
            /*
             * [Step2.5] logic to calculate order item pick status
             */
            int pickStatus = calculateOrderItemPickStatus(seProdOrderItemReqProposalList);
            productionOrderItem.setPickStatus(pickStatus);
        }
        /*
         * [Step3.1] Calculate: to Pick amount
         */
        StorageCoreUnit toPickStorageUnit = materialStockKeepUnitManager.mergeStorageUnitCore(toPickStorageCoreUnitList, productionOrderItem.getClient());
        if (toPickStorageUnit != null) {
            productionOrderItem.setToPickAmount(toPickStorageUnit.getAmount());
        } else {
            productionOrderItem.setToPickAmount(0);
        }
        // [Step3.2] Calculate: picked amount
        StorageCoreUnit pickedStorageUnit = materialStockKeepUnitManager.mergeStorageUnitCore(pickedStorageCoreUnitList, productionOrderItem.getClient());
        if (pickedStorageUnit != null) {
            productionOrderItem.setPickedAmount(pickedStorageUnit.getAmount());
        } else {
            productionOrderItem.setPickedAmount(0);
        }
        // [Step3.3] Calculate: reserved storage
        StorageCoreUnit inStockStorageCoreUnit = materialStockKeepUnitManager.mergeStorageUnitCore(inStockCoreUnitList, productionOrderItem.getClient());
        if (inStockStorageCoreUnit != null) {
            productionOrderItem.setInStockAmount(inStockStorageCoreUnit.getAmount());
        } else {
            productionOrderItem.setInStockAmount(0);
        }
        // [Step3.4] Calculate in process
        StorageCoreUnit inprocessStorageCoreUnit = materialStockKeepUnitManager.mergeStorageUnitCore(inProcessStorageCoreUnitList, productionOrderItem.getClient());
        if (inprocessStorageCoreUnit != null) {
            productionOrderItem.setInProcessAmount(inprocessStorageCoreUnit.getAmount());
        } else {
            productionOrderItem.setInProcessAmount(0);
        }
        // [Step3.5] Calculate all supplied as well as actual Amount
        StorageCoreUnit suppliedStorageCoreUnit = materialStockKeepUnitManager.mergeStorageUnitCore(suppliedStorageCoreUnitList, productionOrderItem.getClient());
        if (suppliedStorageCoreUnit != null) {
            productionOrderItem.setSuppliedAmount(suppliedStorageCoreUnit.getAmount());
            productionOrderItem.setActualAmount(suppliedStorageCoreUnit.getAmount());
        } else {
            productionOrderItem.setSuppliedAmount(0);
            productionOrderItem.setActualAmount(0);
        }
        // [Step 3.6] Calculate free storage
        StorageCoreUnit freeStorageCoreUnit = materialStockKeepUnitManager.mergeStorageUnitCore(freeStorageCoreUnitList, productionOrderItem.getClient());
        if (freeStorageCoreUnit != null) {
            productionOrderItem.setAvailableAmount(freeStorageCoreUnit.getAmount());
        } else {
            productionOrderItem.setAvailableAmount(0);
        }
        /*
         * [Step4] Try to check and set not-in-plan amount and status
         */
        ProductionOrderItemManager.updateItemStatus(productionOrderItem);
    }

    /**
     * Merge and summary accountable amount from multiple doc mat item instances
     *
     * @param rawDocMatItemList
     * @return
     * @throws MaterialException
     * @throws ServiceEntityConfigureException
     */
    public StorageCoreUnit mergeDocItemMaterialSKU(List<ServiceEntityNode> rawDocMatItemList) throws MaterialException, ServiceEntityConfigureException {
        if (ServiceCollectionsHelper.checkNullList(rawDocMatItemList)) {
            return null;
        }
        DocMatItemNode docMatItemNode = (DocMatItemNode) rawDocMatItemList.get(0);
        StorageCoreUnit sumStorageUnit = new StorageCoreUnit(docMatItemNode.getRefMaterialSKUUUID(), docMatItemNode.getRefUnitUUID(), docMatItemNode.getAmount());
        for (int i = 1; i < rawDocMatItemList.size(); i++) {
            DocMatItemNode tempMatItemNode = (DocMatItemNode) rawDocMatItemList.get(i);
            // Have to check if material in valid status
            if (!DocFlowProxy.checkAccountableMaterialStatus(tempMatItemNode.getMaterialStatus())) {
                continue;
            }
            StorageCoreUnit tmpStorageUnit = new StorageCoreUnit(tempMatItemNode.getRefMaterialSKUUUID(), tempMatItemNode.getRefUnitUUID(), tempMatItemNode.getAmount());
            sumStorageUnit = materialStockKeepUnitManager.mergeStorageUnitCore(sumStorageUnit, tmpStorageUnit, StorageCoreUnit.OPERATOR_ADD, docMatItemNode.getClient());
        }
        return sumStorageUnit;
    }

    /**
     * [Internal method] Convert from UI model to se model:productionOrderItem
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToProductionOrderItem(ProductionOrderItemUIModel productionOrderItemUIModel, ProductionOrderItem rawEntity) {
        if (!ServiceEntityStringHelper.checkNullString(productionOrderItemUIModel.getUuid())) {
            rawEntity.setUuid(productionOrderItemUIModel.getUuid());
        }
        if (!ServiceEntityStringHelper.checkNullString(productionOrderItemUIModel.getParentNodeUUID())) {
            rawEntity.setParentNodeUUID(productionOrderItemUIModel.getParentNodeUUID());
        }
        if (!ServiceEntityStringHelper.checkNullString(productionOrderItemUIModel.getRootNodeUUID())) {
            rawEntity.setRootNodeUUID(productionOrderItemUIModel.getRootNodeUUID());
        }
        if (!ServiceEntityStringHelper.checkNullString(productionOrderItemUIModel.getClient())) {
            rawEntity.setClient(productionOrderItemUIModel.getClient());
        }
        rawEntity.setActualAmount(productionOrderItemUIModel.getActualAmount());
        if (!ServiceEntityStringHelper.checkNullString(productionOrderItemUIModel.getPlanStartPrepareDate())) {
            try {
                rawEntity.setPlanStartPrepareDate(DefaultDateFormatConstant.DATE_FORMAT.parse(productionOrderItemUIModel.getPlanStartPrepareDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
        rawEntity.setAmountWithLossRate(productionOrderItemUIModel.getAmountWithLossRate());
        rawEntity.setComLeadTime(productionOrderItemUIModel.getComLeadTime());
        if (!ServiceEntityStringHelper.checkNullString(productionOrderItemUIModel.getPlanStartDate())) {
            try {
                rawEntity.setPlanStartDate(DefaultDateFormatConstant.DATE_FORMAT.parse(productionOrderItemUIModel.getPlanStartDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
        if (!ServiceEntityStringHelper.checkNullString(productionOrderItemUIModel.getActualStartDate())) {
            try {
                rawEntity.setActualStartDate(DefaultDateFormatConstant.DATE_FORMAT.parse(productionOrderItemUIModel.getActualStartDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
        rawEntity.setRefBOMItemUUID(productionOrderItemUIModel.getRefBOMItemUUID());
        rawEntity.setRefMaterialSKUUUID(productionOrderItemUIModel.getRefMaterialSKUUUID());
        rawEntity.setNote(productionOrderItemUIModel.getNote());
        rawEntity.setId(productionOrderItemUIModel.getId());
        rawEntity.setRefUnitUUID(productionOrderItemUIModel.getRefUnitUUID());
        if (!ServiceEntityStringHelper.checkNullString(productionOrderItemUIModel.getActualEndDate())) {
            try {
                rawEntity.setActualEndDate(DefaultDateFormatConstant.DATE_FORMAT.parse(productionOrderItemUIModel.getActualEndDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
        rawEntity.setSelfLeadTime(productionOrderItemUIModel.getSelfLeadTime());
        rawEntity.setClient(productionOrderItemUIModel.getClient());
        rawEntity.setAmount(productionOrderItemUIModel.getAmount());
        if (!ServiceEntityStringHelper.checkNullString(productionOrderItemUIModel.getPlanEndDate())) {
            try {
                rawEntity.setPlanEndDate(DefaultDateFormatConstant.DATE_FORMAT.parse(productionOrderItemUIModel.getPlanEndDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
    }

    public void convProductionOrderItemToUI(ProductionOrderItem productionOrderItem, ProductionOrderItemUIModel productionOrderItemUIModel, LogonInfo logonInfo) throws ServiceEntityInstallationException, ServiceEntityConfigureException {
        if (productionOrderItem != null) {
            docFlowProxy.convDocMatItemToUI(productionOrderItem, productionOrderItemUIModel, logonInfo);
            productionOrderItemUIModel.setNote(productionOrderItem.getNote());
            productionOrderItemUIModel.setAmountWithLossRate(ServiceEntityDoubleHelper.trancateDoubleScale4(productionOrderItem.getAmountWithLossRate()));
            productionOrderItemUIModel.setActualAmount(ServiceEntityDoubleHelper.trancateDoubleScale4(productionOrderItem.getActualAmount()));
            productionOrderItemUIModel.setRefUnitName(productionOrderItem.getRefUnitUUID());
            productionOrderItemUIModel.setSelfLeadTime(productionOrderItem.getSelfLeadTime());
            productionOrderItemUIModel.setComLeadTime(productionOrderItem.getComLeadTime());
            productionOrderItemUIModel.setItemCostNoTax(productionOrderItem.getItemCostNoTax());
            productionOrderItemUIModel.setUnitCostNoTax(productionOrderItem.getUnitCostNoTax());
            productionOrderItemUIModel.setTaxRate(productionOrderItem.getTaxRate());
            productionOrderItemUIModel.setItemCostActual(productionOrderItem.getItemCostActual());
            productionOrderItemUIModel.setItemCostLossRate(productionOrderItem.getItemCostLossRate());
            if (productionOrderItem.getPlanStartPrepareDate() != null) {
                productionOrderItemUIModel.setPlanStartPrepareDate(DefaultDateFormatConstant.DATE_MIN_FORMAT.format(java.util.Date.from(productionOrderItem.getPlanStartPrepareDate().atZone(java.time.ZoneId.systemDefault()).toInstant())));
            }
            if (productionOrderItem.getPlanStartDate() != null) {
                productionOrderItemUIModel.setPlanStartDate(DefaultDateFormatConstant.DATE_MIN_FORMAT.format(java.util.Date.from(productionOrderItem.getPlanStartDate().atZone(java.time.ZoneId.systemDefault()).toInstant())));
            }
            if (productionOrderItem.getPlanEndDate() != null) {
                productionOrderItemUIModel.setPlanEndDate(DefaultDateFormatConstant.DATE_MIN_FORMAT.format(java.util.Date.from(productionOrderItem.getPlanEndDate().atZone(java.time.ZoneId.systemDefault()).toInstant())));
            }
            if (logonInfo != null) {
                Map<Integer, String> itemStatusMap = initItemStatusMap(logonInfo.getLanguageCode());
                productionOrderItemUIModel.setItemStatusValue(itemStatusMap.get(productionOrderItem.getItemStatus()));
            }
            productionOrderItemUIModel.setItemStatus(productionOrderItem.getItemStatus());
            productionOrderItemUIModel.setAvailableAmount(productionOrderItem.getAvailableAmount());
            productionOrderItemUIModel.setInStockAmount(productionOrderItem.getInStockAmount());
            productionOrderItemUIModel.setToPickAmount(productionOrderItem.getToPickAmount());
            productionOrderItemUIModel.setInProcessAmount(productionOrderItem.getInProcessAmount());
            productionOrderItemUIModel.setLackInPlanAmount(productionOrderItem.getLackInPlanAmount());
            productionOrderItemUIModel.setSuppliedAmount(productionOrderItem.getSuppliedAmount());
            productionOrderItemUIModel.setPickedAmount(productionOrderItem.getPickedAmount());
            productionOrderItemUIModel.setPickStatus(productionOrderItem.getPickStatus());
            if (logonInfo != null) {
                Map<Integer, String> statusMap = prodPickingOrderManager.initStatusMap(logonInfo.getLanguageCode());
                productionOrderItemUIModel.setPickStatusValue(statusMap.get(productionOrderItem.getPickStatus()));
            }
            try {
                String amountLabel = materialStockKeepUnitManager.getAmountLabel(productionOrderItem.getRefMaterialSKUUUID(), productionOrderItem.getRefUnitUUID(), productionOrderItem.getAmount(), productionOrderItem.getClient());
                productionOrderItemUIModel.setAmountLabel(amountLabel);

                String actualAmountLabel = materialStockKeepUnitManager.getAmountLabel(productionOrderItem.getRefMaterialSKUUUID(), productionOrderItem.getRefUnitUUID(), productionOrderItem.getActualAmount(), productionOrderItem.getClient());
                productionOrderItemUIModel.setActualAmountLabel(actualAmountLabel);
                String amountLossRateLabel = materialStockKeepUnitManager.getAmountLabel(productionOrderItem.getRefMaterialSKUUUID(), productionOrderItem.getRefUnitUUID(), productionOrderItem.getAmountWithLossRate(), productionOrderItem.getClient());
                productionOrderItemUIModel.setAmountWithLossRateLabel(amountLossRateLabel);
                String inProcessAmountLabel = materialStockKeepUnitManager.getAmountLabel(productionOrderItem.getRefMaterialSKUUUID(), productionOrderItem.getRefUnitUUID(), productionOrderItem.getInProcessAmount(), productionOrderItem.getClient());
                productionOrderItemUIModel.setInProcessAmountLabel(inProcessAmountLabel);

                String inStockAmountLabel = materialStockKeepUnitManager.getAmountLabel(productionOrderItem.getRefMaterialSKUUUID(), productionOrderItem.getRefUnitUUID(), productionOrderItem.getInStockAmount(), productionOrderItem.getClient());
                productionOrderItemUIModel.setInStockAmountLabel(inStockAmountLabel);

                String toPickAmountLabel = materialStockKeepUnitManager.getAmountLabel(productionOrderItem.getRefMaterialSKUUUID(), productionOrderItem.getRefUnitUUID(), productionOrderItem.getToPickAmount(), productionOrderItem.getClient());
                productionOrderItemUIModel.setToPickAmountLabel(toPickAmountLabel);

                String pickedAmountLabel = materialStockKeepUnitManager.getAmountLabel(productionOrderItem.getRefMaterialSKUUUID(), productionOrderItem.getRefUnitUUID(), productionOrderItem.getPickedAmount(), productionOrderItem.getClient());
                productionOrderItemUIModel.setPickedAmountLabel(pickedAmountLabel);

                String lackInPlanLabel = materialStockKeepUnitManager.getAmountLabel(productionOrderItem.getRefMaterialSKUUUID(), productionOrderItem.getRefUnitUUID(), productionOrderItem.getLackInPlanAmount(), productionOrderItem.getClient());
                productionOrderItemUIModel.setLackInPlanAmountLabel(lackInPlanLabel);

                String availableAmountLabel = materialStockKeepUnitManager.getAmountLabel(productionOrderItem.getRefMaterialSKUUUID(), productionOrderItem.getRefUnitUUID(), productionOrderItem.getAvailableAmount(), productionOrderItem.getClient());
                productionOrderItemUIModel.setAvailableAmountLabel(availableAmountLabel);

                String suppliedAmountLabel = materialStockKeepUnitManager.getAmountLabel(productionOrderItem.getRefMaterialSKUUUID(), productionOrderItem.getRefUnitUUID(), productionOrderItem.getSuppliedAmount(), productionOrderItem.getClient());
                productionOrderItemUIModel.setSuppliedAmountLabel(suppliedAmountLabel);

            } catch (MaterialException e) {
                // log error and continue
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, productionOrderItem.getUuid()), e);
            }
            productionOrderItemUIModel.setRefBOMItemUUID(productionOrderItem.getRefBOMItemUUID());
        }
    }

    public void convProductionOrderToItemUI(ProductionOrder productionOrder, ProductionOrderItemUIModel productionOrderItemUIModel) throws MaterialException, ServiceEntityConfigureException {
        convProductionOrderToItemUI(productionOrder, productionOrderItemUIModel, null);
    }


    public void convDocumentToItemReqProposalUI(ServiceEntityNode documentContent, ProdOrderItemReqProposalUIModel prodOrderItemReqProposalUIModel) {
        if (documentContent != null) {
            prodOrderItemReqProposalUIModel.setRefDocumentId(documentContent.getId());
        }
    }

    public void convProductionOrderToItemUI(ProductionOrder productionOrder, ProductionOrderItemUIModel productionOrderItemUIModel, LogonInfo logonInfo) throws MaterialException, ServiceEntityConfigureException {
        if (productionOrder != null) {
            docFlowProxy.convParentDocToItemUI(productionOrder, productionOrderItemUIModel, logonInfo);
            productionOrderItemUIModel.setParentDocPlanStartTime(productionOrder.getPlanStartTime() != null ? DefaultDateFormatConstant.DATE_FORMAT.format(java.util.Date.from(productionOrder.getPlanStartTime().atZone(java.time.ZoneId.systemDefault()).toInstant())) : null);
            productionOrderItemUIModel.setParentDocPlanEndTime(productionOrder.getPlanEndTime() != null ? DefaultDateFormatConstant.DATE_FORMAT.format(java.util.Date.from(productionOrder.getPlanEndTime().atZone(java.time.ZoneId.systemDefault()).toInstant())) : null);
            String amountLabel = materialStockKeepUnitManager.getAmountLabel(productionOrder.getRefMaterialSKUUUID(), productionOrder.getRefUnitUUID(), productionOrder.getAmount(), productionOrder.getClient());
            productionOrderItemUIModel.setParentDocAmountLabel(amountLabel);
            productionOrderItemUIModel.setParentDocMaterialSKUUUID(productionOrder.getRefMaterialSKUUUID());
        }
    }

    public void convOrderMaterialSKUToItemUI(MaterialStockKeepUnit materialStockKeepUnit, ProductionOrderItemUIModel productionOrderItemUIModel) {
        if (materialStockKeepUnit != null) {
            productionOrderItemUIModel.setParentDocMaterialSKUId(materialStockKeepUnit.getId());
            productionOrderItemUIModel.setParentDocMaterialSKUName(materialStockKeepUnit.getName());
        }
    }

    public void convItemMaterialSKUToUI(MaterialStockKeepUnit itemMaterialSKU, ProductionOrderItemUIModel productionOrderItemUIModel) {
        convItemMaterialSKUToUI(itemMaterialSKU, productionOrderItemUIModel, null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convItemMaterialSKUToUI(MaterialStockKeepUnit itemMaterialSKU, ProductionOrderItemUIModel productionOrderItemUIModel, LogonInfo logonInfo) {
        if (itemMaterialSKU != null) {
            productionOrderItemUIModel.setRefMaterialSKUName(itemMaterialSKU.getName());
            productionOrderItemUIModel.setRefMaterialSKUId(itemMaterialSKU.getId());

            productionOrderItemUIModel.setPackageStandard(itemMaterialSKU.getPackageStandard());
            productionOrderItemUIModel.setSupplyType(itemMaterialSKU.getSupplyType());
            if (logonInfo != null) {
                try {
                    Map<Integer, String> supplyTypeMap = materialStockKeepUnitManager.initSupplyTypeMap(logonInfo.getLanguageCode());
                    productionOrderItemUIModel.setSupplyTypeValue(supplyTypeMap.get(itemMaterialSKU.getSupplyType()));
                } catch (ServiceEntityInstallationException e) {
                    // log error and continue
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                }
            }
        }
    }


    public void convProductionOrderToProposalUI(ProductionOrder productionOrder, ProdOrderItemReqProposalUIModel prodOrderItemReqProposalUIModel, LogonInfo logonInfo) throws ServiceEntityInstallationException, ServiceEntityConfigureException {
        prodOrderItemReqProposalUIModel.setOrderId(productionOrder.getId());
        prodOrderItemReqProposalUIModel.setOrderStatus(productionOrder.getStatus());
        try {
            if (logonInfo != null) {
                Map<Integer, String> statusMap = productionOrderManager.initStatusMap(logonInfo.getLanguageCode());
                prodOrderItemReqProposalUIModel.setOrderStatusValue(statusMap.get(productionOrder.getStatus()));
            }
        } catch (ServiceEntityInstallationException e) {
            // log error and continue
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, IDocumentNodeFieldConstant.STATUS), e);
        }
    }

    public void convProductionOrderItemToProposalUI(ProductionOrderItem productionOrderItem, ProdOrderItemReqProposalUIModel prodOrderItemReqProposalUIModel) throws ServiceEntityInstallationException, ServiceEntityConfigureException {
        prodOrderItemReqProposalUIModel.setParentItemId(productionOrderItem.getId());
    }


    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convWarehouseToItemReqProposalUI(Warehouse warehouse, ProdOrderItemReqProposalUIModel prodOrderItemReqProposalUIModel) {
        if (warehouse != null) {
            prodOrderItemReqProposalUIModel.setRefWarehouseId(warehouse.getId());
            prodOrderItemReqProposalUIModel.setRefWarehouseName(warehouse.getName());
        }
    }

    public void convProdOrderItemReqProposalToUI(ProdOrderItemReqProposal prodOrderItemReqProposal, ProdOrderItemReqProposalUIModel prodOrderItemReqProposalUIModel) throws ServiceEntityInstallationException, ServiceEntityConfigureException {
        convProdOrderItemReqProposalToUI(prodOrderItemReqProposal, prodOrderItemReqProposalUIModel, null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     * @throws ServiceEntityConfigureException
     */
    public void convProdOrderItemReqProposalToUI(ProdOrderItemReqProposal prodOrderItemReqProposal, ProdOrderItemReqProposalUIModel prodOrderItemReqProposalUIModel, LogonInfo logonInfo) throws ServiceEntityInstallationException, ServiceEntityConfigureException {
        if (prodOrderItemReqProposal != null) {
            docFlowProxy.convDocMatItemToUI(prodOrderItemReqProposal, prodOrderItemReqProposalUIModel, logonInfo);
            prodOrderItemReqProposalUIModel.setRefUUID(prodOrderItemReqProposal.getRefUUID());
            if (prodOrderItemReqProposal.getPlanStartPrepareDate() != null) {
                prodOrderItemReqProposalUIModel.setPlanStartPrepareDate(DefaultDateFormatConstant.DATE_MIN_FORMAT.format(java.util.Date.from(prodOrderItemReqProposal.getPlanStartPrepareDate().atZone(java.time.ZoneId.systemDefault()).toInstant())));
            }
            if (prodOrderItemReqProposal.getPlanStartDate() != null) {
                prodOrderItemReqProposalUIModel.setPlanStartDate(DefaultDateFormatConstant.DATE_MIN_FORMAT.format(java.util.Date.from(prodOrderItemReqProposal.getPlanStartDate().atZone(java.time.ZoneId.systemDefault()).toInstant())));
            }
            if (prodOrderItemReqProposal.getPlanEndDate() != null) {
                prodOrderItemReqProposalUIModel.setPlanEndDate(DefaultDateFormatConstant.DATE_MIN_FORMAT.format(java.util.Date.from(prodOrderItemReqProposal.getPlanEndDate().atZone(java.time.ZoneId.systemDefault()).toInstant())));
            }
            if (prodOrderItemReqProposal.getActualStartDate() != null) {
                prodOrderItemReqProposalUIModel.setActualStartDate(DefaultDateFormatConstant.DATE_MIN_FORMAT.format(java.util.Date.from(prodOrderItemReqProposal.getActualStartDate().atZone(java.time.ZoneId.systemDefault()).toInstant())));
            }

            prodOrderItemReqProposalUIModel.setSelfLeadTime(prodOrderItemReqProposal.getSelfLeadTime());
            prodOrderItemReqProposal.setComLeadTime(prodOrderItemReqProposal.getComLeadTime());
            prodOrderItemReqProposalUIModel.setItemStatus(prodOrderItemReqProposal.getItemStatus());
            prodOrderItemReqProposalUIModel.setRefUnitUUID(prodOrderItemReqProposal.getRefUnitUUID());
            prodOrderItemReqProposalUIModel.setRefMaterialSKUUUID(prodOrderItemReqProposal.getRefMaterialSKUUUID());
            prodOrderItemReqProposalUIModel.setRefWarehouseUUID(prodOrderItemReqProposal.getRefWarehouseUUID());
            prodOrderItemReqProposalUIModel.setRefBOMItemUUID(prodOrderItemReqProposal.getRefBOMItemUUID());
            if (prodOrderItemReqProposal.getPlanStartDate() != null) {
                prodOrderItemReqProposalUIModel.setPlanStartDate(DefaultDateFormatConstant.DATE_FORMAT.format(prodOrderItemReqProposal.getPlanStartDate()));
            }
            prodOrderItemReqProposalUIModel.setAmount(ServiceEntityDoubleHelper.trancateDoubleScale4(prodOrderItemReqProposal.getAmount()));
            if (prodOrderItemReqProposal.getActualStartPrepareDate() != null) {
                prodOrderItemReqProposalUIModel.setActualStartPrepareDate(DefaultDateFormatConstant.DATE_FORMAT.format(prodOrderItemReqProposal.getActualStartPrepareDate()));
            }
            if (logonInfo != null) {
                Map<Integer, String> itemStatusMap = this.initItemStatusMap(logonInfo.getLanguageCode());
                prodOrderItemReqProposalUIModel.setItemStatusValue(itemStatusMap.get(prodOrderItemReqProposal.getItemStatus()));
            }
            prodOrderItemReqProposalUIModel.setItemStatus(prodOrderItemReqProposal.getItemStatus());
            prodOrderItemReqProposalUIModel.setDocumentType(prodOrderItemReqProposal.getDocumentType());
            if (logonInfo != null) {
                Map<Integer, String> documentTypeMap = initDocumentTypeMap(logonInfo.getLanguageCode());
                prodOrderItemReqProposalUIModel.setDocumentTypeValue(documentTypeMap.get(prodOrderItemReqProposal.getDocumentType()));
            }
            prodOrderItemReqProposalUIModel.setComLeadTime(prodOrderItemReqProposal.getComLeadTime());
            prodOrderItemReqProposalUIModel.setItemIndex(prodOrderItemReqProposal.getItemIndex());

            prodOrderItemReqProposalUIModel.setInProcessAmount(ServiceEntityDoubleHelper.trancateDoubleScale4(prodOrderItemReqProposal.getInProcessAmount()));
            prodOrderItemReqProposalUIModel.setInStockAmount(ServiceEntityDoubleHelper.trancateDoubleScale4(prodOrderItemReqProposal.getInStockAmount()));
            prodOrderItemReqProposalUIModel.setPickStatus(prodOrderItemReqProposal.getPickStatus());
            prodOrderItemReqProposalUIModel.setPickedAmount(ServiceEntityDoubleHelper.trancateDoubleScale4(prodOrderItemReqProposal.getPickedAmount()));
            prodOrderItemReqProposalUIModel.setToPickAmount(ServiceEntityDoubleHelper.trancateDoubleScale4(prodOrderItemReqProposal.getToPickAmount()));
            prodOrderItemReqProposalUIModel.setSuppliedAmount(ServiceEntityDoubleHelper.trancateDoubleScale4(prodOrderItemReqProposal.getSuppliedAmount()));
            if (logonInfo != null) {
                Map<Integer, String> statusMap = prodPickingOrderManager.initStatusMap(logonInfo.getLanguageCode());
                prodOrderItemReqProposalUIModel.setPickStatusValue(statusMap.get(prodOrderItemReqProposal.getPickStatus()));
            }
            /*
             * [Step5] Area to calculate all kinds of amount label
             */
            try {
                String inProcessAmountLabel = materialStockKeepUnitManager.getAmountLabel(prodOrderItemReqProposal.getRefMaterialSKUUUID(), prodOrderItemReqProposal.getRefUnitUUID(), prodOrderItemReqProposal.getInProcessAmount(), prodOrderItemReqProposal.getClient());
                prodOrderItemReqProposalUIModel.setInProcessAmountLabel(inProcessAmountLabel);

                String inStockAmountLabel = materialStockKeepUnitManager.getAmountLabel(prodOrderItemReqProposal.getRefMaterialSKUUUID(), prodOrderItemReqProposal.getRefUnitUUID(), prodOrderItemReqProposal.getInStockAmount(), prodOrderItemReqProposal.getClient());
                prodOrderItemReqProposalUIModel.setInStockAmountLabel(inStockAmountLabel);

                String toPickAmountLabel = materialStockKeepUnitManager.getAmountLabel(prodOrderItemReqProposal.getRefMaterialSKUUUID(), prodOrderItemReqProposal.getRefUnitUUID(), prodOrderItemReqProposal.getToPickAmount(), prodOrderItemReqProposal.getClient());
                prodOrderItemReqProposalUIModel.setToPickAmountLabel(toPickAmountLabel);

                String pickedAmountLabel = materialStockKeepUnitManager.getAmountLabel(prodOrderItemReqProposal.getRefMaterialSKUUUID(), prodOrderItemReqProposal.getRefUnitUUID(), prodOrderItemReqProposal.getPickedAmount(), prodOrderItemReqProposal.getClient());
                prodOrderItemReqProposalUIModel.setPickedAmountLabel(pickedAmountLabel);

                String suppliedAmountLabel = materialStockKeepUnitManager.getAmountLabel(prodOrderItemReqProposal.getRefMaterialSKUUUID(), prodOrderItemReqProposal.getRefUnitUUID(), prodOrderItemReqProposal.getSuppliedAmount(), prodOrderItemReqProposal.getClient());
                prodOrderItemReqProposalUIModel.setSuppliedAmountLabel(suppliedAmountLabel);

            } catch (MaterialException e) {
                // log error and continue
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, prodOrderItemReqProposal.getUuid()), e);
            }
        }
    }

    /**
     * [Internal method] Convert from UI model to SE
     * model:prodOrderItemRequirement
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToProdOrderItemReqProposal(ProdOrderItemReqProposalUIModel prodOrderItemReqProposalUIModel, ProdOrderItemReqProposal rawEntity) {
        if (!ServiceEntityStringHelper.checkNullString(prodOrderItemReqProposalUIModel.getUuid())) {
            rawEntity.setUuid(prodOrderItemReqProposalUIModel.getUuid());
        }
        if (!ServiceEntityStringHelper.checkNullString(prodOrderItemReqProposalUIModel.getParentNodeUUID())) {
            rawEntity.setParentNodeUUID(prodOrderItemReqProposalUIModel.getParentNodeUUID());
        }
        if (!ServiceEntityStringHelper.checkNullString(prodOrderItemReqProposalUIModel.getRootNodeUUID())) {
            rawEntity.setRootNodeUUID(prodOrderItemReqProposalUIModel.getRootNodeUUID());
        }
        if (!ServiceEntityStringHelper.checkNullString(prodOrderItemReqProposalUIModel.getClient())) {
            rawEntity.setClient(prodOrderItemReqProposalUIModel.getClient());
        }
        rawEntity.setRefUUID(prodOrderItemReqProposalUIModel.getRefUUID());
        if (!ServiceEntityStringHelper.checkNullString(prodOrderItemReqProposalUIModel.getPlanStartPrepareDate())) {
            try {
                rawEntity.setPlanStartPrepareDate(DefaultDateFormatConstant.DATE_FORMAT.parse(prodOrderItemReqProposalUIModel.getPlanStartPrepareDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
        if (!ServiceEntityStringHelper.checkNullString(prodOrderItemReqProposalUIModel.getActualStartDate())) {
            try {
                rawEntity.setActualStartDate(DefaultDateFormatConstant.DATE_FORMAT.parse(prodOrderItemReqProposalUIModel.getActualStartDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
        rawEntity.setSelfLeadTime(prodOrderItemReqProposalUIModel.getSelfLeadTime());
        rawEntity.setRefUnitUUID(prodOrderItemReqProposalUIModel.getRefUnitUUID());
        rawEntity.setRefWarehouseUUID(prodOrderItemReqProposalUIModel.getRefWarehouseUUID());
        rawEntity.setRefMaterialSKUUUID(prodOrderItemReqProposalUIModel.getRefMaterialSKUUUID());
        rawEntity.setRefBOMItemUUID(prodOrderItemReqProposalUIModel.getRefBOMItemUUID());
        rawEntity.setRefUUID(prodOrderItemReqProposalUIModel.getRefUUID());
        if (prodOrderItemReqProposalUIModel.getItemStatus() > 0) {
            rawEntity.setItemStatus(prodOrderItemReqProposalUIModel.getItemStatus());
        }
        if (!ServiceEntityStringHelper.checkNullString(prodOrderItemReqProposalUIModel.getPlanStartDate())) {
            try {
                rawEntity.setPlanStartDate(DefaultDateFormatConstant.DATE_FORMAT.parse(prodOrderItemReqProposalUIModel.getPlanStartDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
        rawEntity.setAmount(prodOrderItemReqProposalUIModel.getAmount());
        if (!ServiceEntityStringHelper.checkNullString(prodOrderItemReqProposalUIModel.getActualStartPrepareDate())) {
            try {
                rawEntity.setActualStartPrepareDate(DefaultDateFormatConstant.DATE_FORMAT.parse(prodOrderItemReqProposalUIModel.getActualStartPrepareDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
        rawEntity.setComLeadTime(prodOrderItemReqProposalUIModel.getComLeadTime());
        rawEntity.setItemIndex(prodOrderItemReqProposalUIModel.getItemIndex());
        if (prodOrderItemReqProposalUIModel.getDocumentType() > 0) {
            rawEntity.setDocumentType(prodOrderItemReqProposalUIModel.getDocumentType());
        }
    }

    public void convMaterialStockKeepUnitToUI(MaterialStockKeepUnit materialStockKeepUnit, ProductionOrderItemUIModel productionOrderItemUIModel) {
        if (materialStockKeepUnit != null) {
            productionOrderItemUIModel.setRefMaterialSKUId(materialStockKeepUnit.getId());
            productionOrderItemUIModel.setRefMaterialSKUName(materialStockKeepUnit.getName());
        }
    }

    public ServiceDocumentExtendUIModel convProdOrderItemReqProposalToDocExtUIModel(ProdOrderItemReqProposalUIModel prodOrderItemReqProposalUIModel, LogonInfo logonInfo) throws ServiceEntityInstallationException {
        ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
        serviceDocumentExtendUIModel.setRefUIModel(prodOrderItemReqProposalUIModel);
        docFlowProxy.convDocMatItemUIToDocExtUIModel(prodOrderItemReqProposalUIModel, serviceDocumentExtendUIModel, logonInfo, IDefDocumentResource.DOCUMENT_TYPE_PRODUCTORDERITEM);
        serviceDocumentExtendUIModel.setId(prodOrderItemReqProposalUIModel.getId());
        serviceDocumentExtendUIModel.setName(prodOrderItemReqProposalUIModel.getRefMaterialSKUName());
        serviceDocumentExtendUIModel.setStatus(prodOrderItemReqProposalUIModel.getItemStatus());
        serviceDocumentExtendUIModel.setStatusValue(prodOrderItemReqProposalUIModel.getItemStatusValue());
        String referenceDate = prodOrderItemReqProposalUIModel.getPlanStartDate();
        serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
        return serviceDocumentExtendUIModel;
    }

    public ServiceDocumentExtendUIModel convProductionOrderItemToDocExtUIModel(ProductionOrderItemUIModel productionOrderItemUIModel, LogonInfo logonInfo) throws ServiceEntityInstallationException {
        ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
        serviceDocumentExtendUIModel.setRefUIModel(productionOrderItemUIModel);
        docFlowProxy.convDocMatItemUIToDocExtUIModel(productionOrderItemUIModel, serviceDocumentExtendUIModel, logonInfo, IDefDocumentResource.DOCUMENT_TYPE_PRODUCTORDERITEM);
        serviceDocumentExtendUIModel.setId(productionOrderItemUIModel.getParentDocId());
        if (!ServiceEntityStringHelper.checkNullString(productionOrderItemUIModel.getId())) {
            serviceDocumentExtendUIModel.setId(productionOrderItemUIModel.getId());
        }
        serviceDocumentExtendUIModel.setName(productionOrderItemUIModel.getRefMaterialSKUName());
        serviceDocumentExtendUIModel.setStatus(productionOrderItemUIModel.getItemStatus());
        serviceDocumentExtendUIModel.setStatusValue(productionOrderItemUIModel.getItemStatusValue());
        String referenceDate = productionOrderItemUIModel.getPlanStartDate();
        serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
        return serviceDocumentExtendUIModel;
    }

    /**
     * Utility method: filter productionOrderItemServiceModel by reference BOM
     * Item UUID
     *
     * @param productionOrderItemServiceModelList
     * @return
     */
    public static ProductionOrderItemServiceModel filterProductionOrderItemByBOMItemUUID(String refBOMItemUUID, List<ProductionOrderItemServiceModel> productionOrderItemServiceModelList) {
        if (ServiceCollectionsHelper.checkNullList(productionOrderItemServiceModelList)) {
            return null;
        }
        if (ServiceEntityStringHelper.checkNullString(refBOMItemUUID)) {
            return null;
        }
        for (ProductionOrderItemServiceModel productionOrderItemServiceModel : productionOrderItemServiceModelList) {
            if (refBOMItemUUID.equals(productionOrderItemServiceModel.getProductionOrderItem().getRefBOMItemUUID())) {
                return productionOrderItemServiceModel;
            }
        }
        return null;
    }

    /**
     * Utility method to merge proposal into parent item service model
     *
     * @param productionOrderItemServiceModel
     * @param prodOrderItemReqProposal
     */
    public static void mergeProposalIntoItemServiceModel(ProductionOrderItemServiceModel productionOrderItemServiceModel, ProdOrderItemReqProposal prodOrderItemReqProposal) {
        List<ProdOrderItemReqProposalServiceModel> prodOrderItemReqProposalList = productionOrderItemServiceModel.getProdOrderItemReqProposalList();
        if (prodOrderItemReqProposalList == null) {
            prodOrderItemReqProposalList = new ArrayList<>();
            productionOrderItemServiceModel.setProdOrderItemReqProposalList(prodOrderItemReqProposalList);
        }
        if (ServiceCollectionsHelper.checkNullList(prodOrderItemReqProposalList)) {
            ProdOrderItemReqProposalServiceModel prodOrderItemReqProposalServiceModel = new ProdOrderItemReqProposalServiceModel();
            prodOrderItemReqProposalServiceModel.setProdOrderItemReqProposal(prodOrderItemReqProposal);
            prodOrderItemReqProposalList.add(prodOrderItemReqProposalServiceModel);
            return;
        }
        // In case already has proposal, have to avoid duplicate add
        for (ProdOrderItemReqProposalServiceModel prodOrderItemReqProposalServiceModel : prodOrderItemReqProposalList) {
            ProdOrderItemReqProposal tempItemReqProposal = prodOrderItemReqProposalServiceModel.getProdOrderItemReqProposal();
            if (tempItemReqProposal.getUuid().equals(prodOrderItemReqProposal.getUuid())) {
                return;
            }
        }
        ProdOrderItemReqProposalServiceModel prodOrderItemReqProposalServiceModel = new ProdOrderItemReqProposalServiceModel();
        prodOrderItemReqProposalServiceModel.setProdOrderItemReqProposal(prodOrderItemReqProposal);
        prodOrderItemReqProposalList.add(prodOrderItemReqProposalServiceModel);
    }

    /**
     * Generate production order item proposal from Plan item proposal, copying
     * the necessary information, re-build special logic for lead time.
     *
     * @param prodPlanItemReqProposal
     * @param productionOrderItem
     * @return
     * @throws ServiceEntityConfigureException
     */
    public ProdOrderItemReqProposal generateProdOrderItemProposalFromPlan(ProdPlanItemReqProposal prodPlanItemReqProposal, ProductionOrderItem productionOrderItem, SerialLogonInfo serialLogonInfo) throws ServiceEntityConfigureException, DocActionException {
        /*
         * [Step1] copy the
         */
        ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) productionOrderManager.newEntityNode(productionOrderItem, ProdOrderItemReqProposal.NODENAME);
        /*
         * [Important] Copy next doc information to this new order item proposal
         */
        prodOrderItemReqProposal.setNextDocMatItemUUID(prodPlanItemReqProposal.getNextDocMatItemUUID());
        prodOrderItemReqProposal.setNextDocMatItemArrayUUID(prodPlanItemReqProposal.getNextDocMatItemArrayUUID());
        prodOrderItemReqProposal.setNextDocType(prodPlanItemReqProposal.getNextDocType());
        docFlowProxy.buildItemPrevNextRelationship(prodPlanItemReqProposal,  prodOrderItemReqProposal, null,serialLogonInfo);
        prodOrderItemReqProposal.setItemIndex(prodPlanItemReqProposal.getItemIndex());
        prodOrderItemReqProposal.setItemStatus(prodPlanItemReqProposal.getItemStatus());
        prodOrderItemReqProposal.setRefWarehouseUUID(prodPlanItemReqProposal.getRefWarehouseUUID());
        prodOrderItemReqProposal.setRefBOMItemUUID(prodPlanItemReqProposal.getRefBOMItemUUID());
        prodOrderItemReqProposal.setDocumentType(prodPlanItemReqProposal.getDocumentType());
        prodOrderItemReqProposal.setStoreAmount(prodPlanItemReqProposal.getStoreAmount());
        prodOrderItemReqProposal.setStoreUnitUUID(prodPlanItemReqProposal.getStoreUnitUUID());
        prodOrderItemReqProposal.setRefStoreItemUUID(prodPlanItemReqProposal.getRefStoreItemUUID());
        prodOrderItemReqProposal.setPlanStartDate(prodPlanItemReqProposal.getPlanStartDate());
        prodOrderItemReqProposal.setPlanEndDate(prodPlanItemReqProposal.getPlanEndDate());
        prodOrderItemReqProposal.setActualStartDate(prodPlanItemReqProposal.getActualStartDate());
        prodOrderItemReqProposal.setActualEndDate(prodPlanItemReqProposal.getActualEndDate());
        prodOrderItemReqProposal.setSelfLeadTime(prodPlanItemReqProposal.getSelfLeadTime());
        prodOrderItemReqProposal.setComLeadTime(prodPlanItemReqProposal.getComLeadTime());
        return prodOrderItemReqProposal;
    }

    /**
     * Generate production order item from Plan item, copying the necessary
     * information, re-build special logic for lead time.
     *
     * @param productionPlanItem
     * @param productionOrder
     * @return
     * @throws ServiceEntityConfigureException
     */
    public ProductionOrderItem generateProductionOrderItemFromPlanItem(ProductionPlanItem productionPlanItem, ProductionOrder productionOrder,
                                                                       int index, SerialLogonInfo serialLogonInfo) throws ServiceEntityConfigureException, DocActionException {
        /*
         * [Step1] copy the
         */
        ProductionOrderItem productionOrderItem = (ProductionOrderItem) productionOrderManager.newEntityNode(productionOrder, ProductionOrderItem.NODENAME);
        docFlowProxy.buildItemPrevNextRelationship(productionPlanItem, productionOrderItem, null,serialLogonInfo);

        // set default production order item id
        productionOrderItem.setId(productionOrder.getId() + "-" + index);
        productionOrderItem.setRefBOMItemUUID(productionPlanItem.getRefBOMItemUUID());
        productionOrderItem.setAmount(productionPlanItem.getAmount());
        productionOrderItem.setAmountWithLossRate(productionPlanItem.getAmountWithLossRate());
        // set initial actual amount = amountWithLossRate
        productionOrderItem.setActualAmount(productionPlanItem.getAmountWithLossRate());
        productionOrderItem.setPlanStartDate(productionPlanItem.getPlanStartDate());
        productionOrderItem.setPlanEndDate(productionPlanItem.getPlanEndDate());
        productionOrderItem.setActualStartDate(productionPlanItem.getActualStartDate());
        productionOrderItem.setActualEndDate(productionPlanItem.getActualEndDate());
        productionOrderItem.setSelfLeadTime(productionPlanItem.getSelfLeadTime());
        // TODO decide com lead time later??
        productionOrderItem.setComLeadTime(productionPlanItem.getComLeadTime());
        /*
         * [Step2] Should decide which woc
         */
        return productionOrderItem;
    }

    public static List<ProdOrderItemReqProposalServiceModel> optimizeProposalListByStore(List<ProdOrderItemReqProposalServiceModel> prodOrderItemReqProposalServiceModelList) {
        if (ServiceCollectionsHelper.checkNullList(prodOrderItemReqProposalServiceModelList)) {
            return null;
        }
        List<ProdOrderItemReqProposalServiceModel> headerList = new ArrayList<>();
        List<ProdOrderItemReqProposalServiceModel> rearList = new ArrayList<>();
        for (ProdOrderItemReqProposalServiceModel prodOrderItemReqProposalServiceModel : prodOrderItemReqProposalServiceModelList) {
            int documentType = prodOrderItemReqProposalServiceModel.getProdOrderItemReqProposal().getDocumentType();
            if (documentType == IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM || documentType == IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY) {
                headerList.add(prodOrderItemReqProposalServiceModel);
            } else {
                rearList.add(prodOrderItemReqProposalServiceModel);
            }
        }
        if (!ServiceCollectionsHelper.checkNullList(rearList)) {
            headerList.addAll(rearList);
        }
        return headerList;
    }

    public ServiceDocumentExtendUIModel convToDocumentExtendUIModel(ServiceEntityNode seNode, LogonInfo logonInfo) {
        if (seNode == null) {
            return null;
        }
        if (ProductionOrderItem.NODENAME.equals(seNode.getNodeName())) {
            ProductionOrderItem productionOrderItem = (ProductionOrderItem) seNode;
            try {
                ProductionOrderItemUIModel productionOrderItemUIModel = (ProductionOrderItemUIModel) productionOrderManager.genUIModelFromUIModelExtension(ProductionOrderItemUIModel.class, productionOrderItemServiceUIModelExtension.genUIModelExtensionUnion().get(0), productionOrderItem, logonInfo, null);
                ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = convProductionOrderItemToDocExtUIModel(productionOrderItemUIModel, logonInfo);
                return serviceDocumentExtendUIModel;
            } catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ProductionOrderItem.NODENAME));
            }
        }
        if (ProdOrderItemReqProposal.NODENAME.equals(seNode.getNodeName())) {
            ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) seNode;
            try {
                ProdOrderItemReqProposalUIModel prodOrderItemReqProposalUIModel = (ProdOrderItemReqProposalUIModel) productionOrderManager.genUIModelFromUIModelExtension(ProdOrderItemReqProposalUIModel.class, prodOrderItemReqProposalServiceUIModelExtension.genUIModelExtensionUnion().get(0), prodOrderItemReqProposal, logonInfo, null);
                ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = convProdOrderItemReqProposalToDocExtUIModel(prodOrderItemReqProposalUIModel, logonInfo);
                return serviceDocumentExtendUIModel;
            } catch (ServiceModuleProxyException | ServiceEntityConfigureException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ProductionOrderItem.NODENAME));
            } catch (ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ProductionOrderItem.NODENAME));
            }
        }
        return null;
    }

}
