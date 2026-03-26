package com.company.IntelligentPlatform.production.service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.production.service.ProdPickingExtendAmountModel;

import com.company.IntelligentPlatform.finance.service.FinanceAccountValueProxyException;
import com.company.IntelligentPlatform.finance.service.SystemResourceException;
import com.company.IntelligentPlatform.finance.service.SystemResourceFinanceAccountProxy;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.logistics.model.StoreAvailableStoreItemRequest;
import com.company.IntelligentPlatform.logistics.model.StoreAvailableStoreItemResponse;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreItemManager;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryException;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryServiceModel;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryWarehouseItemManager;
import com.company.IntelligentPlatform.production.dto.*;
import com.company.IntelligentPlatform.production.repository.RepairProdOrderRepository;
import com.company.IntelligentPlatform.production.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.MaterialStockKeepUnitSearchModel;
import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreItemSearchModel;
import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.model.MatDecisionValueSetting;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.RegisteredProduct;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.controller.DefFinanceControllerResource;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceFlowRuntimeEngine;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.ServiceJSONRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEMessageResponse;
import com.company.IntelligentPlatform.common.model.ServiceFlowRuntimeException;
import com.company.IntelligentPlatform.common.model.*;

import jakarta.annotation.PostConstruct;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Logic Manager CLASS FOR Service Entity [RepairProdOrder]
 *
 * @author
 * @date Sun Jan 03 21:00:34 CST 2016
 * <p>
 * This class is generated automatically by platform automation register
 * tool
 */
@Service
public class RepairProdOrderManager extends ServiceEntityManager {

    public static final String METHOD_ConvReportItemMaterialSKUToUI = "convReportItemMaterialSKUToUI";

    public static final String METHOD_ConvRegisteredProductToReportItemUI = "convRegisteredProductToReportItemUI";

    public static final String METHOD_ConvItemBillOfMaterialItemToUI = "convItemBillOfMaterialItemToUI";

    public static final String METHOD_ConvRepairProdSupplyWarehouseToUI = "convRepairProdSupplyWarehouseToUI";

    public static final String METHOD_ConvUIToRepairProdSupplyWarehouse = "convUIToRepairProdSupplyWarehouse";

    public static final String METHOD_ConvWarehouseToUI = "convWarehouseToUI";

    public static final String METHOD_ConvRepairProdOrderToUI = "convRepairProdOrderToUI";

    public static final String METHOD_ConvUIToRepairProdOrder = "convUIToRepairProdOrder";

    public static final String METHOD_ConvOutBillOfMaterialOrderToUI = "convOutBillOfMaterialOrderToUI";

    public static final String METHOD_ConvOutBillOfMaterialItemToUI = "convOutBillOfMaterialItemToUI";

    public static final String METHOD_ConvOutMaterialStockKeepUnitToUI = "convOutMaterialStockKeepUnitToUI";

    public static final String METHOD_ConvProdWorkCenterToUI = "convProdWorkCenterToUI";

    public static final String METHOD_ConvProductionPlanToUI = "convProductionPlanToUI";

    public static final String METHOD_ConvProdOrderReportToUI = "convProdOrderReportToUI";

    public static final String METHOD_ConvUIToProdOrderReport = "convUIToProdOrderReport";

    public static final String METHOD_ConvReportByToUI = "convReportByToUI";

    public static final String METHOD_ConvOrderToReportUI = "convOrderToReportUI";

    public static final String METHOD_ConvMaterialSKUToReportUI = "convMaterialSKUToReportUI";

    public static final String METHOD_ConvProdOrderReportItemToUI = "convProdOrderReportItemToUI";

    public static final String METHOD_ConvProdOrderReportToItemUI = "convProdOrderReportToItemUI";

    public static final String METHOD_ConvUIToProdOrderReportItem = "convUIToProdOrderReportItem";

    public static final String METHOD_ConvReservedDocToReportItemUI = "convReservedDocToReportItemUI";

    public static final String METHOD_ConvReservedDocToOrderUI = "convReservedDocToOrderUI";

    public static final String METHOD_ConvPrevDocToReportItemUI = "convPrevDocToReportItemUI";

    public static final String METHOD_ConvNextDocToReportItemUI = "convNextDocToReportItemUI";

    public static final String METHOD_ConvApproveByToUI = "convApproveByToUI";

    public static final String METHOD_ConvCountApproveByToUI = "convCountApproveByToUI";

    private static final String PROPERTIES_RES_ACTIONCODE = "RepairProdOrder_actionCode";

    private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

    private Map<String, Map<Integer, String>> genOrderItemModeLan = new HashMap<>();

    private Map<String, Map<Integer, String>> doneStatusMapLan = new HashMap<>();

    private Map<String, Map<Integer, String>> categoryMapLan = new HashMap<>();

    private Map<String, Map<Integer, String>> orderTypeMapLan = new HashMap<>();

    private Map<String, Map<Integer, String>> reportStatusMapLan = new HashMap<>();

    private Map<String, Map<Integer, String>> reportCategoryMapLan = new HashMap<>();

    private Map<String, RepairProdOrder> repairProdOrderMap = new HashMap<>();
    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    protected RepairProdOrderRepository repairProdOrderDAO;

    @Autowired
    protected StandardPriorityProxy standardPriorityProxy;

    @Autowired
    protected ProductionOrderManager productionOrderManager;

    @Autowired
    protected RepairProdTargetMatItemManager repairProdTargetMatItemManager;

    @Autowired
    protected ProdPickingRefMaterialItemManager prodPickingRefMaterialItemManager;

    @Autowired
    protected WarehouseStoreManager warehouseStoreManager;

    @Autowired
    protected OutboundDeliveryManager outboundDeliveryManager;

    // TODO-LEGACY: @Autowired

    // TODO-LEGACY:     protected ProductionPackageProxy productionPackageProxy;

    @Autowired
    protected SerialNumberSettingManager serialNumberSettingManager;

    @Autowired
    protected ServiceDefaultIdGenerateHelper serviceDefaultIdGenerateHelper;

    @Autowired
    protected WarehouseStoreItemManager warehouseStoreItemManager;

    @Autowired
    protected BillOfMaterialOrderManager billOfMaterialOrderManager;

    @Autowired
    protected RepairProdOrderItemManager repairProdOrderItemManager;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected MaterialConfigureTemplateManager materialConfigureTemplateManager;

    @Autowired
    protected MatDecisionValueSettingManager matDecisionValueSettingManager;

    @Autowired
    protected RepairProdOrderConfigureProxy repairProdOrderConfigureProxy;

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    @Autowired
    protected RepairProdOrderIdHelper repairProdOrderIdHelper;

    @Autowired
    protected SystemResourceFinanceAccountProxy systemResourceFinanceAccountProxy;

    @Autowired
    protected ProcessRouteOrderManager processRouteOrderManager;

    @Autowired
    protected ProdJobOrderManager prodJobOrderManager;

    @Autowired
    protected BsearchService bsearchService;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected ProductiveBOMOrderManager productiveBOMOrderManager;

    @Autowired
    protected ProdPickingOrderManager prodPickingOrderManager;

    @Autowired
    protected ProdPickingRefOrderltemManager prodPickingRefOrderltemManager;

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected OrganizationManager organizationManager;

    @Autowired
    protected SystemDefDocActionCodeProxy systemDefDocActionCodeProxy;

    @Autowired
    protected ProdOrderWithPickingOrderProxy prodOrderWithPickingOrderProxy;

    @Autowired
    protected RepairProdOrderItemServiceUIModelExtension repairProdOrderItemServiceUIModelExtension;

    @Autowired
    protected RepairProdOrderSearchProxy repairProdOrderSearchProxy;

    @Autowired
    protected OutboundDeliveryWarehouseItemManager outboundDeliveryWarehouseItemManager;

    @Autowired
    protected ServiceFlowRuntimeEngine serviceFlowRuntimeEngine;

    @Autowired
    protected RepairProdOrderServiceUIModelExtension repairProdOrderServiceUIModelExtension;

    protected Map<String, Map<Integer, String>> privateActionCodeMapLan = new HashMap<>();

    @Autowired
    protected DocActionNodeProxy docActionNodeProxy;

    @Autowired
    protected ProductionOutboundDeliveryManager productionOutboundDeliveryManager;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    final Logger logger = LoggerFactory.getLogger(RepairProdOrderManager.class);
    @Autowired
    private LogonInfoManager logonInfoManager;

    public List<PageHeaderModel> getPageHeaderModelList(RepairProdOrder repairProdOrder, String client) throws ServiceEntityConfigureException {
        List<PageHeaderModel> resultList = new ArrayList<PageHeaderModel>();
        if (repairProdOrder != null) {
            PageHeaderModel itemHeaderModel = getPageHeaderModel(repairProdOrder, 0);
            if (itemHeaderModel != null) {
                resultList.add(itemHeaderModel);
            }
        }
        return resultList;
    }

    public PageHeaderModel getPageHeaderModel(RepairProdOrder repairProdOrder, int index) throws ServiceEntityConfigureException {
        PageHeaderModel pageHeaderModel = new PageHeaderModel();
        pageHeaderModel.setPageTitle("repairProdOrderTitle");
        pageHeaderModel.setHeaderName(repairProdOrder.getId());
        pageHeaderModel.setNodeInstId(RepairProdOrder.SENAME);
        pageHeaderModel.setUuid(repairProdOrder.getUuid());
        pageHeaderModel.setIndex(index);
        return pageHeaderModel;
    }

    public ProductiveBOMOrderServiceModel generateProductiveBOMData(RepairProdOrder repairProdOrder) throws ServiceEntityConfigureException, BillOfMaterialException, MaterialException {
        return productionOrderManager.generateProductiveBOMData(repairProdOrder);
    }

    @Override
    public ServiceEntityNode getEntityNodeByKey(Object keyValue, String keyName, String nodeName, String client, List<ServiceEntityNode> rawSEList) throws ServiceEntityConfigureException {
        if (IServiceEntityNodeFieldConstant.UUID.equals(keyName) && RepairProdOrder.NODENAME.equals(nodeName)) {
            if (this.repairProdOrderMap.containsKey(keyValue)) {
                return this.repairProdOrderMap.get(keyValue);
            }
            // In case not find, then find from persistence
            RepairProdOrder repairProdOrder = (RepairProdOrder) super.getEntityNodeByKey(keyValue, keyName, nodeName, client, rawSEList);
            if (repairProdOrder != null) {
                this.repairProdOrderMap.put(keyName, repairProdOrder);
            }
            return repairProdOrder;
        } else {
            return super.getEntityNodeByKey(keyValue, keyName, nodeName, client, rawSEList);
        }
    }

    @Override
    public void updateBuffer(ServiceEntityNode serviceEntityNode) {
        if (serviceEntityNode != null && LogonUser.SENAME.equals(serviceEntityNode.getServiceEntityName())) {
            RepairProdOrder repairProdOrder = (RepairProdOrder) serviceEntityNode;
            this.repairProdOrderMap.put(repairProdOrder.getUuid(), repairProdOrder);
        }
    }

    /**
     * Using Async way to update production order post tasks.
     *
     * @param repairProdOrderServiceModel
     * @param logonUserUUID
     * @param organizationUUID
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     * @throws ServiceModuleProxyException
     */
    public void postUpdateRepairProdOrderAsyncWrapper(RepairProdOrderServiceModel repairProdOrderServiceModel,
                                                   String logonUserUUID, String organizationUUID) throws ServiceComExecuteException {
        try {
            ServiceAsyncExecuteProxy.executeAsyncWrapper(repairProdOrderServiceModel, (repairOrderServiceModel, logonInfo) -> {
                try {
                    postUpdateRepairProdOrder(repairOrderServiceModel, logonUserUUID, organizationUUID);
                    updateServiceModule(RepairProdOrderServiceModel.class, repairOrderServiceModel, logonUserUUID, organizationUUID);
                    return repairOrderServiceModel;
                } catch (ServiceEntityConfigureException | ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                } catch (MaterialException | ServiceModuleProxyException | ServiceComExecuteException |
                         DocActionException e) {
                    throw new ServiceEntityExceptionContainer(e);
                }
                return repairOrderServiceModel;
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
     * Entrance method to post update production order service model
     *
     * @param repairProdOrderServiceModel
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    public void postUpdateRepairProdOrder(RepairProdOrderServiceModel repairProdOrderServiceModel, String logonUserUUID, String organizationUUID) throws ServiceEntityConfigureException, MaterialException, ServiceEntityInstallationException, ServiceModuleProxyException, ServiceComExecuteException, DocActionException {
        /*
         * [Step1] Update Info for Production Order Item
         */
        RepairProdOrder repairProdOrder = repairProdOrderServiceModel.getRepairProdOrder();
        List<String> warehouseUUIDList = getWarehouseUUIDList(repairProdOrder.getUuid(), repairProdOrder.getClient());
        // [Step 1.5] update gross to repair amount to order
        updateSumRepairAmountToOrder(repairProdOrderServiceModel);
        List<RepairProdOrderItemServiceModel> repairProdOrderItemList = repairProdOrderServiceModel.getRepairProdOrderItemList();
        if (ServiceCollectionsHelper.checkNullList(repairProdOrderItemList)) {
            return;
        }
        double grossCost = 0;
        double grossCostLossRate = 0;
        double grossCostActual = 0;
        List<ProdPickingExtendAmountModel> allPickingExtendAmountModelList = new ArrayList<>();
        for (RepairProdOrderItemServiceModel repairProdOrderItemServiceModel : repairProdOrderItemList) {
            // Core Logic to update order item key information
            List<ProdPickingExtendAmountModel> pickingExtendAmountModelList = repairProdOrderItemManager.refreshOrderItemFromPickingItem(repairProdOrderItemServiceModel, warehouseUUIDList);
            if (!ServiceCollectionsHelper.checkNullList(pickingExtendAmountModelList)) {
                allPickingExtendAmountModelList.addAll(pickingExtendAmountModelList);
            }
            RepairProdOrderItem repairProdOrderItem = repairProdOrderItemServiceModel.getRepairProdOrderItem();
            grossCost += repairProdOrderItem.getItemPrice();
            grossCostLossRate += repairProdOrderItem.getItemCostLossRate();
            grossCostActual += repairProdOrderItem.getItemCostActual();
        }
        repairProdOrder.setGrossCost(grossCost);
        repairProdOrder.setGrossCostLossRate(grossCostLossRate);
        repairProdOrder.setGrossCostActual(grossCostActual);

        /*
         * [Step2] Update actual amount for each production order item
         */
        postUpdateProdPickingOrderWrapper(repairProdOrder.getUuid(), null, logonUserUUID, organizationUUID, repairProdOrder.getClient());
        //		this.setActualAmountProdItem(repairProdOrderServiceModel.getRepairProdOrderItemList(), repairProdOrder, logonUserUUID,
        //						organizationUUID);
    }

    /**
     * [Internal method] Timely update gross amount to order by summarize from all the sub prod target
     *
     * @param repairProdOrderServiceModel
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    private void updateSumRepairAmountToOrder(RepairProdOrderServiceModel repairProdOrderServiceModel) throws ServiceEntityConfigureException, MaterialException {
        List<RepairProdTargetMatItemServiceModel> repairProdTargetMatItemList = repairProdOrderServiceModel.getRepairProdTargetMatItemList();
        StorageCoreUnit sumStorageCoreUnit = getSumToRepairAmount(repairProdTargetMatItemList);
        RepairProdOrder repairProdOrder = repairProdOrderServiceModel.getRepairProdOrder();
        if (sumStorageCoreUnit != null) {
            repairProdOrder.setAmount(sumStorageCoreUnit.getAmount());
            repairProdOrder.setRefUnitUUID(sumStorageCoreUnit.getRefUnitUUID());
        } else {
            repairProdOrder.setAmount(0);
        }
    }

    private StorageCoreUnit getSumToRepairAmount(List<RepairProdTargetMatItemServiceModel> repairProdTargetMatItemList) throws MaterialException, ServiceEntityConfigureException {
        if (!ServiceCollectionsHelper.checkNullList(repairProdTargetMatItemList)) {
            String client = repairProdTargetMatItemList.get(0).getRepairProdTargetMatItem().getClient();
            List<StorageCoreUnit> storageCoreUnitList = repairProdTargetMatItemList.stream().map(repairProdTargetMatItemServiceModel -> {
                RepairProdTargetMatItem repairProdTargetMatItem = repairProdTargetMatItemServiceModel.getRepairProdTargetMatItem();
                return new StorageCoreUnit(repairProdTargetMatItem.getRefMaterialSKUUUID(), repairProdTargetMatItem.getRefUnitUUID(), repairProdTargetMatItem.getAmount());
            }).collect(Collectors.toList());
            return materialStockKeepUnitManager.mergeStorageUnitCore(storageCoreUnitList, client);
        }
        return null;
    }

    /**
     * [Internal] Wrapper method to get all the picking ref order model and update
     */
    private void postUpdateProdPickingOrderWrapper(String orderUUID, List<ProdPickingExtendAmountModel> allPickingExtendAmountModelList, String logonUserUUID, String organizationUUID, String client) throws ServiceEntityConfigureException, ServiceModuleProxyException, MaterialException, DocActionException {
        ProdPickingRefOrderItem prodPickingRefOrderItem = (ProdPickingRefOrderItem) prodPickingOrderManager.getEntityNodeByKey(orderUUID, IServiceEntityNodeFieldConstant.UUID, ProdPickingRefOrderItem.NODENAME, client, null);
        if (prodPickingRefOrderItem != null) {
            ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel = (ProdPickingRefOrderItemServiceModel) prodPickingOrderManager.loadServiceModule(ProdPickingRefOrderItemServiceModel.class, prodPickingRefOrderItem);
            prodPickingRefOrderltemManager.refreshPickingRefOrderItem(prodPickingRefOrderItemServiceModel, allPickingExtendAmountModelList);
            prodPickingOrderManager.updateServiceModule(ProdPickingRefOrderItemServiceModel.class, prodPickingRefOrderItemServiceModel, logonUserUUID, organizationUUID);
        }
    }

    private List<StoreAvailableStoreItemResponse> filterCheckStoreItemList(List<ServiceEntityNode> warehouseStoreItemList, String refMaterialTemplateUUID) throws MaterialException, ServiceEntityConfigureException {
        if (ServiceCollectionsHelper.checkNullList(warehouseStoreItemList)) {
            return null;
        }
        List<StoreAvailableStoreItemResponse> resultList = new ArrayList<>();
        for (ServiceEntityNode serviceEntityNode : warehouseStoreItemList) {
            WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) serviceEntityNode;
            if (!refMaterialTemplateUUID.equals(warehouseStoreItem.getRefMaterialTemplateUUID())) {
                continue;
            }
            StoreAvailableStoreItemRequest
                    storeAvailableStoreItemRequest = new StoreAvailableStoreItemRequest(warehouseStoreItem, null, false);
            StorageCoreUnit availableAmount = outboundDeliveryWarehouseItemManager.getAvailableStoreItemAmountUnion(storeAvailableStoreItemRequest);
            if (availableAmount.getAmount() <= 0) {
                continue;
            }
            StoreAvailableStoreItemResponse storeAvailableStoreItemResponse = new StoreAvailableStoreItemResponse(warehouseStoreItem, availableAmount, false, null);
            resultList.add(storeAvailableStoreItemResponse);
        }
        return resultList;
    }

    public RepairProdOrderServiceModel newRepairProdOrderServiceModel(RepairProdOrderInitModel repairProdOrderInitModel, int category, LogonInfo logonInfo) throws ServiceEntityConfigureException, MaterialException, ParseException, ServiceModuleProxyException, ProductionOrderException {
        /*
         * [Step1] Get & Check all selected store item list && New Repair Order Root node
         */
        if (ServiceEntityStringHelper.checkNullString(repairProdOrderInitModel.getRefMaterialTemplateUUID())) {
            throw new ProductionOrderException(ProductionOrderException.PARA_NO_MATERIALSKU, "");
        }
        RepairProdOrder repairProdOrder = (RepairProdOrder) newRootEntityNode(logonInfo.getClient());
        repairProdOrder.setCategory(category);
        repairProdOrder.setRefMaterialSKUUUID(repairProdOrderInitModel.getRefMaterialTemplateUUID());
        MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager.getEntityNodeByKey(repairProdOrderInitModel.getRefMaterialTemplateUUID(), IServiceEntityNodeFieldConstant.UUID, MaterialStockKeepUnit.NODENAME, logonInfo.getClient(), null);
        BillOfMaterialOrder billOfMaterialOrder = billOfMaterialOrderManager.getDefaultBOMBySKU(repairProdOrderInitModel.getRefMaterialTemplateUUID(), logonInfo.getClient());
        if (billOfMaterialOrder == null) {
            throw new ProductionOrderException(ProductionOrderException.PARA_NO_BOM, materialStockKeepUnit.getId());
        }
        repairProdOrder.setRefBillOfMaterialUUID(billOfMaterialOrder.getUuid());
        RepairProdOrderServiceModel repairProdOrderServiceModel = new RepairProdOrderServiceModel();
        repairProdOrderServiceModel.setRepairProdOrder(repairProdOrder);
        // repairProdOrder.setProductionBatchNumber(repairProdOrderInitModel.getProductionBatchNumber());
        /*
         * [Step2] Get store item information and create target repair prod item
         */
        WarehouseStoreItemSearchModel warehouseStoreItemSearchModel = new WarehouseStoreItemSearchModel();
        warehouseStoreItemSearchModel.setUuid(ServiceEntityStringHelper.convStringListIntoMultiStringValue(repairProdOrderInitModel.getUuidList()));
        warehouseStoreItemSearchModel.setRefMaterialTemplateUUID(repairProdOrderInitModel.getRefMaterialTemplateUUID());
        List<ServiceEntityNode> warehouseStoreItemList = null;
        try {
            warehouseStoreItemList = warehouseStoreManager.searchStoreItemInternal(warehouseStoreItemSearchModel, logonInfo);
        } catch (SearchConfigureException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new ProductionOrderException(ProductionOrderException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        } catch (NodeNotFoundException | ServiceEntityInstallationException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new ProductionOrderException(ProductionOrderException.PARA_SYSTEM_ERROR, e.getMessage());
        } catch (AuthorizationException | LogonInfoException e) {
            throw new ProductionOrderException(ProductionOrderException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
        List<StoreAvailableStoreItemResponse> availableStoreItemResponseList = filterCheckStoreItemList(warehouseStoreItemList, repairProdOrderInitModel.getRefMaterialTemplateUUID());
        if (ServiceCollectionsHelper.checkNullList(availableStoreItemResponseList)) {
            // in case no warehouse store item list selected
            throw new ProductionOrderException(ProductionOrderException.PARA_NO_MATERIALSKU, "");
        }
        List<RepairProdTargetMatItemServiceModel> repairProdTargetMatItemList = repairProdTargetMatItemManager.newTargetMatItem(availableStoreItemResponseList, repairProdOrderServiceModel);
        // Also merge into parent repairProdOrderServiceModel
        if (!ServiceCollectionsHelper.checkNullList(repairProdTargetMatItemList)) {
            repairProdOrderServiceModel.setRepairProdTargetMatItemList(repairProdTargetMatItemList);
        }
        // Update gross to repair amount to repair order
        updateSumRepairAmountToOrder(repairProdOrderServiceModel);

        /*
         * [Step4] Assign supply warehouse list
         */
        if (materialStockKeepUnit != null) {
            repairProdOrder.setName(materialStockKeepUnit.getName());
            List<ServiceEntityNode> rawWarehouseDecisonList = matDecisionValueSettingManager.getDecisionValueList(materialStockKeepUnit, MatDecisionValueSettingManager.VAUSAGE_RAWMAT_WAREHOUSE);
            if (!ServiceCollectionsHelper.checkNullList(rawWarehouseDecisonList)) {
                List<ServiceEntityNode> repairProdSupplyWarehouseList = new ArrayList<>();
                for (ServiceEntityNode seNode : rawWarehouseDecisonList) {
                    MatDecisionValueSetting matDecisionValueSetting = (MatDecisionValueSetting) seNode;
                    RepairProdSupplyWarehouse repairProdSupplyWarehouse = (RepairProdSupplyWarehouse) newEntityNode(repairProdOrder, RepairProdSupplyWarehouse.NODENAME);
                    repairProdSupplyWarehouse.setRefUUID(matDecisionValueSetting.getRawValue());
                    repairProdSupplyWarehouse.setRefNodeName(Warehouse.NODENAME);
                    repairProdSupplyWarehouse.setRefSEName(Warehouse.SENAME);
                    repairProdSupplyWarehouseList.add(repairProdSupplyWarehouse);
                }
                repairProdOrderServiceModel.setRepairProdSupplyWarehouseList(repairProdSupplyWarehouseList);
            }
        }
        if (!ServiceEntityStringHelper.checkNullString(repairProdOrderInitModel.getPlanStartTime())) {
            repairProdOrder.setPlanStartTime(DefaultDateFormatConstant.DATE_FORMAT.parse(repairProdOrderInitModel.getPlanStartTime()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
        }
        if (!ServiceEntityStringHelper.checkNullString(repairProdOrderInitModel.getPlanEndTime())) {
            repairProdOrder.setPlanEndTime(DefaultDateFormatConstant.DATE_FORMAT.parse(repairProdOrderInitModel.getPlanEndTime()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
        }
        if (!ServiceEntityStringHelper.checkNullString(repairProdOrderInitModel.getPlanStartPrepareTime())) {
            repairProdOrder.setPlanStartPrepareDate(DefaultDateFormatConstant.DATE_FORMAT.parse(repairProdOrderInitModel.getPlanStartPrepareTime()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
        }
        updateServiceModule(RepairProdOrderServiceModel.class, repairProdOrderServiceModel, logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
        return repairProdOrderServiceModel;
    }

    private List<ProductiveBOMItemServiceModel> genSubProdBOMItemSerModel(ProductiveBOMItem parentProdBOMItem, List<ServiceEntityNode> productiveBOMList) {
        List<ServiceEntityNode> subBOMItemList = productiveBOMOrderManager.filterSubBOMItemList(parentProdBOMItem.getUuid(), productiveBOMList);
        if (ServiceCollectionsHelper.checkNullList(subBOMItemList)) {
            return null;
        }
        List<ProductiveBOMItemServiceModel> result = new ArrayList<>();
        /*
         * Ratio: means the real amount in production / amount set in standard
         * BOM item amount
         */
        double ratio = parentProdBOMItem.getAmountWithLossRate() / parentProdBOMItem.getAmount();
        for (ServiceEntityNode seNode : subBOMItemList) {
            ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
            ProductiveBOMItemServiceModel productiveBOMItemServiceModel = new ProductiveBOMItemServiceModel();
            productiveBOMItemServiceModel.setProductiveBOMItem(productiveBOMItem);
            double amount = ratio * productiveBOMItem.getAmount();
            // theory amount with loss rate
            double amountWithLossRate = amount / (1 - productiveBOMItem.getTheoLossRate() / 100);
            amountWithLossRate = Math.ceil(amountWithLossRate);
            productiveBOMItem.setAmountWithLossRate(amountWithLossRate);
            List<ProductiveBOMItemServiceModel> subProductiveBOMItemList = genSubProdBOMItemSerModel(productiveBOMItem, productiveBOMList);
            if (!ServiceCollectionsHelper.checkNullList(subProductiveBOMItemList)) {
                productiveBOMItemServiceModel.setSubProductiveBOMItemList(subProductiveBOMItemList);
            }
            result.add(productiveBOMItemServiceModel);
        }
        return result;
    }

    public void rejectApproveService(RepairProdOrderServiceModel repairProdOrderServiceModel, String logonUserUUID, String organizationUUID) throws ServiceModuleProxyException, ServiceEntityConfigureException {
        this.executeActionCore(repairProdOrderServiceModel, ServiceCollectionsHelper.asList(RepairProdOrder.STATUS_APPROVED), ProductionPlan.STATUS_REJECT_APPROVAL, ProductionPlanActionNode.DOC_ACTION_REJECT_APPROVE, logonUserUUID, organizationUUID);
    }

    /**
     * Core Logic to approve repairProdOrder and update to DB
     *
     * @param repairProdOrderServiceModel
     * @param logonUserUUID
     * @param organizationUUID
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     */
    public void submitService(RepairProdOrderServiceModel repairProdOrderServiceModel, String logonUserUUID, String organizationUUID) throws ServiceModuleProxyException, ServiceEntityConfigureException {
        try {
            this.checkSubmit(repairProdOrderServiceModel);
        } catch (ProductionOrderException e) {
            throw new ServiceModuleProxyException(ServiceModuleProxyException.PARA_SYSTEM_WRONG, e.getErrorMessage());
        }
        this.executeActionCore(repairProdOrderServiceModel, ServiceCollectionsHelper.asList(RepairProdOrder.STATUS_INITIAL, RepairProdOrder.STATUS_REJECT_APPROVAL), RepairProdOrder.STATUS_SUBMITTED, RepairProdOrderActionNode.DOC_ACTION_SUBMIT, logonUserUUID, organizationUUID);
    }

    public void checkSubmit(RepairProdOrderServiceModel repairProdOrderServiceModel) throws ProductionOrderException {
        RepairProdOrder repairProdOrder = repairProdOrderServiceModel.getRepairProdOrder();
        if (repairProdOrder.getGenOrderItemMode() != RepairProdOrder.GENITEM_MODE_NO_NEED) {
            // in case need to add order item list
            List<RepairProdOrderItemServiceModel> repairProdOrderItemList = repairProdOrderServiceModel.getRepairProdOrderItemList();
            if (ServiceCollectionsHelper.checkNullList(repairProdOrderItemList)) {
                throw new ProductionOrderException(ProductionOrderException.PARA_NO_ORDERITEM, repairProdOrder.getId());
            }
        }
        if (ServiceCollectionsHelper.checkNullList(repairProdOrderServiceModel.getRepairProdTargetMatItemList())) {
            throw new ProductionOrderException(ProductionOrderException.PARA_NO_TARGETITEM, repairProdOrder.getId());
        }
    }

    /**
     * Core Logic to approve repairProdOrder and update to DB
     *
     * @param repairProdOrderServiceModel
     * @param logonUserUUID
     * @param organizationUUID
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     */
    public void revokeSubmitService(RepairProdOrderServiceModel repairProdOrderServiceModel, String logonUserUUID, String organizationUUID) throws ServiceModuleProxyException, ServiceEntityConfigureException {
        this.executeActionCore(repairProdOrderServiceModel, ServiceCollectionsHelper.asList(RepairProdOrder.STATUS_SUBMITTED), RepairProdOrder.STATUS_INITIAL, RepairProdOrderActionNode.DOC_ACTION_REVOKE_SUBMIT, logonUserUUID, organizationUUID);
    }

    /**
     * Very Core Logic to approve production order
     *
     * @param repairProdOrderServiceModel
     * @param logonUserUUID
     */
    public void approveOrderCore(RepairProdOrderServiceModel repairProdOrderServiceModel, String logonUserUUID, String organizationUUID) throws ServiceEntityConfigureException, ServiceModuleProxyException {
        this.executeActionCore(repairProdOrderServiceModel, ServiceCollectionsHelper.asList(RepairProdOrder.STATUS_SUBMITTED), RepairProdOrder.STATUS_APPROVED, RepairProdOrderActionNode.DOC_ACTION_COUNTAPPROVE, logonUserUUID, organizationUUID);
    }

    /**
     * Core Logic to approve production order and update to DB
     *
     * @param repairProdOrderServiceModel
     * @throws ServiceEntityConfigureException
     * @throws ServiceEntityInstallationException
     * @throws NodeNotFoundException
     * @throws SearchConfigureException
     * @throws ProductionOrderException
     * @throws MaterialException
     * @throws BillOfMaterialException
     * @throws ServiceModuleProxyException
     */
    public synchronized void approveOrder(RepairProdOrderServiceModel repairProdOrderServiceModel,
                                          boolean refreshOrderFlag, LogonInfo logonInfo) throws ServiceEntityConfigureException,
            BillOfMaterialException, MaterialException, ProductionOrderException, SearchConfigureException, NodeNotFoundException, ServiceEntityInstallationException, ServiceModuleProxyException, AuthorizationException, LogonInfoException {
        RepairProdOrder repairProdOrder = repairProdOrderServiceModel.getRepairProdOrder();
        approveOrderCore(repairProdOrderServiceModel, logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
        boolean localRefreshFlag = refreshOrderFlag;
        if (!localRefreshFlag) {
            // In case no production plan exist, then have to generate resources
            // in mandatory way
            if (ServiceCollectionsHelper.checkNullList(repairProdOrderServiceModel.getRepairProdOrderItemList())) {
                localRefreshFlag = true;
            }
        }
        if (localRefreshFlag) {
            /*
             * [Step 2] Delete the old resources and generate new production item service list resources
             */
            deleteOrderSubResource(repairProdOrder.getUuid(), logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID(), repairProdOrder.getClient());

        }
        updateRecentBOMOrderToRepairProdOrder(repairProdOrder);
        // In case need to refresh order resource
        List<ServiceEntityNode> rawProdOrderItemList = generateProductItemListEntry(repairProdOrderServiceModel, logonInfo, true);
        repairProdOrderServiceModel = (RepairProdOrderServiceModel) this.convertToRepairProdOrderServiceModel(repairProdOrderServiceModel, rawProdOrderItemList);
        updateServiceModuleWithDelete(RepairProdOrderServiceModel.class, repairProdOrderServiceModel, logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
        /*
         * [Step3] Batch release production order item list to picking order
         */
        releaseRepairProdOrderToPickingBatch(repairProdOrderServiceModel, logonInfo);
        /*
         * [Step4] Generate out bound delivery for target
         */
        genOutboundForRepairTargetItem(repairProdOrderServiceModel, logonInfo);
    }

    /**
     * [Internal method] Generate Outbound Delivery for target item
     * proposal
     *
     * @param repairProdOrderServiceModel repair prod order service model, should contains target item list
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<OutboundDeliveryServiceModel> genOutboundForRepairTargetItem(RepairProdOrderServiceModel repairProdOrderServiceModel, LogonInfo logonInfo) throws ServiceEntityConfigureException, ProductionOrderException {
        OutboundDeliveryServiceModel outboundDeliveryServiceModel = new OutboundDeliveryServiceModel();
        // check target item list
        List<RepairProdTargetMatItemServiceModel> repairProdTargetMatItemServiceModelList = repairProdOrderServiceModel.getRepairProdTargetMatItemList();
        if (ServiceCollectionsHelper.checkNullList(repairProdTargetMatItemServiceModelList)) {
            return null;
        }
        List<ServiceEntityNode> repairProdTargetItemList = repairProdTargetMatItemServiceModelList.stream().map(RepairProdTargetMatItemServiceModel::getRepairProdTargetMatItem).collect(Collectors.toList());
        List<ServiceEntityNode> warehouseStoreItemList = docFlowProxy.getReservedDocMatItemList(repairProdTargetItemList, warehouseStoreManager, WarehouseStoreItem.NODENAME);
        RepairProdOrder repairProdOrder = repairProdOrderServiceModel.getRepairProdOrder();
        try {
            List<OutboundDeliveryServiceModel> outboundDeliveryServiceModelList = productionOutboundDeliveryManager.genOutboundFromStoreList(null, warehouseStoreItemList, null, logonInfo, null, outboundItem -> {
                String targetItemUUID = outboundItem.getReservedMatItemUUID();
                RepairProdTargetMatItem repairProdTargetMatItem = (RepairProdTargetMatItem) ServiceCollectionsHelper.filterSENodeOnline(targetItemUUID, repairProdTargetItemList);
                if (repairProdTargetMatItem != null) {
                    /*
                     * [Step3] Binding prev-next relation ship to gen outbound
                     */
                    try {
                        docFlowProxy.buildItemPrevNextRelationship(outboundItem,  repairProdTargetMatItem, null,LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
                    } catch (DocActionException e) {
                        throw new RuntimeException(e);
                    }
                }
                return outboundItem;
            });
            // Also update target mat item with prev-next relation
            this.updateSENodeList(repairProdTargetItemList, logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
            return outboundDeliveryServiceModelList;
        } catch (MaterialException | ServiceModuleProxyException | OutboundDeliveryException |
                 SearchConfigureException | DocActionException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new ProductionOrderException(ProductionOrderException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        } catch (NodeNotFoundException | ServiceEntityInstallationException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new ProductionOrderException(ProductionOrderException.PARA_SYSTEM_ERROR, e.getMessage());
        } catch (AuthorizationException | LogonInfoException e) {
            throw new ProductionOrderException(ProductionOrderException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
    }

    /**
     * Core Logic to count-approve production order and update to DB
     *
     * @param repairProdOrderServiceModel
     * @param logonUserUUID
     * @param organizationUUID
     * @throws ServiceEntityConfigureException
     */
    public void countApproveOrder(RepairProdOrderServiceModel repairProdOrderServiceModel, String logonUserUUID, String organizationUUID) throws ServiceEntityConfigureException, ServiceModuleProxyException {
        this.executeActionCore(repairProdOrderServiceModel, ServiceCollectionsHelper.asList(RepairProdOrder.STATUS_APPROVED), RepairProdOrder.STATUS_INITIAL, RepairProdOrderActionNode.DOC_ACTION_COUNTAPPROVE, logonUserUUID, organizationUUID);
    }

    /**
     * Core Logic start inproduction
     *
     * @param repairProdOrderServiceModel
     * @param logonUserUUID
     * @param organizationUUID
     * @throws ServiceEntityConfigureException
     * @throws ServiceEntityInstallationException
     * @throws NodeNotFoundException
     * @throws SearchConfigureException
     */
    public void inProduction(RepairProdOrderServiceModel repairProdOrderServiceModel, String logonUserUUID, String organizationUUID) throws ServiceEntityConfigureException, SearchConfigureException, NodeNotFoundException, ServiceEntityInstallationException, ServiceModuleProxyException {
        /*
         * [Step1] Set status of production order itself.
         */

        RepairProdOrder repairProdOrder = repairProdOrderServiceModel.getRepairProdOrder();
        this.executeActionCore(repairProdOrderServiceModel, ServiceCollectionsHelper.asList(RepairProdOrder.STATUS_APPROVED), RepairProdOrder.STATUS_INPRODUCTION, RepairProdOrderActionNode.DOC_ACTION_INPRODUCTION, logonUserUUID, organizationUUID);

        /*
         * [Step2] Try to find all the target item, and set into production
         */
        List<ServiceEntityNode> repairProdTargetMatItemList = this.getEntityNodeListByKey(repairProdOrder.getUuid(), IServiceEntityNodeFieldConstant.ROOTNODEUUID, RepairProdTargetMatItem.NODENAME, repairProdOrder.getClient(), null);
        if (!ServiceCollectionsHelper.checkNullList(repairProdTargetMatItemList)) {
            repairProdTargetMatItemManager.setInProcessStatusBatch(repairProdTargetMatItemList, logonUserUUID, organizationUUID);
        }
    }

    /**
     * Core Logic to approve repairProdOrder and update to DB
     *
     * @param repairProdOrderServiceModel
     * @param logonUserUUID
     * @param organizationUUID
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     */
    public void executeActionCore(RepairProdOrderServiceModel repairProdOrderServiceModel, List<Integer> curStatusList, int targetStatus, int actionCode, String logonUserUUID, String organizationUUID) throws ServiceModuleProxyException, ServiceEntityConfigureException {
        RepairProdOrder repairProdOrder = repairProdOrderServiceModel.getRepairProdOrder();
        if (!DocActionNodeProxy.checkCurStatus(curStatusList, repairProdOrder.getStatus())) {
            return;
        }
        repairProdOrder.setStatus(targetStatus);
        docActionNodeProxy.updateDocActionWrapper(actionCode, RepairProdOrderActionNode.NODENAME, null, IDefDocumentResource.DOCUMENT_TYPE_REPAIRPRODORDER, this, repairProdOrderServiceModel, repairProdOrder, logonUserUUID, organizationUUID);
        updateServiceModuleWithDelete(RepairProdOrderServiceModel.class, repairProdOrderServiceModel, logonUserUUID, organizationUUID, RepairProdOrder.SENAME, repairProdOrderServiceUIModelExtension);
    }

    public Map<Integer, String> initActionCodeMap(String languageCode) throws ServiceEntityInstallationException {
        String path = this.getClass().getResource("").getPath();
        return systemDefDocActionCodeProxy.getActionCodeMergeWithPrivateMap(languageCode, path + PROPERTIES_RES_ACTIONCODE, this.privateActionCodeMapLan);
    }

    /**
     * Core Logic to pre-check the validate if could set to complete
     *
     * @param repairProdOrderServiceModel
     * @throws ServiceModuleProxyException
     * @throws ServiceEntityConfigureException
     * @throws ProductionOrderException
     */
    public List<SimpleSEMessageResponse> preCheckSetComplete(RepairProdOrderServiceModel repairProdOrderServiceModel, LogonInfo logonInfo) throws ProductionOrderException, ServiceModuleProxyException, ServiceEntityConfigureException {
        if (ServiceCollectionsHelper.checkNullList(repairProdOrderServiceModel.getRepairProdTargetMatItemList())) {
            throw new ProductionOrderException(ProductionOrderException.PARA_NO_TARGETITEM);
        }
        List<RepairProdTargetMatItemServiceModel> repairProdTargetMatItemList = repairProdOrderServiceModel.getRepairProdTargetMatItemList();
        List<SimpleSEMessageResponse> resultList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(repairProdTargetMatItemList)) {
            for (RepairProdTargetMatItemServiceModel repairProdTargetMatItemServiceModel : repairProdTargetMatItemList) {
                RepairProdTargetMatItem repairProdTargetMatItem = repairProdTargetMatItemServiceModel.getRepairProdTargetMatItem();
                SimpleSEMessageResponse simpleSEMessageResponse = new SimpleSEMessageResponse();
                simpleSEMessageResponse.setMessageLevel(SimpleSEMessageResponse.MESSAGELEVEL_ERROR);
                simpleSEMessageResponse.setRefException(new ProductionOrderException(ProductionOrderException.PARA_NODONE_TAEGET));
                simpleSEMessageResponse.setErrorCode(ProductionOrderException.PARA_NODONE_TAEGET);
                String indentifier = repairProdTargetMatItemManager.getTargetMatItemIndentifier(repairProdTargetMatItem, logonInfo);
                simpleSEMessageResponse.setErrorParas(new String[]{indentifier});
                resultList.add(simpleSEMessageResponse);
            }
        }
        return resultList;
    }

    /**
     * Core Logic to set production order status:[Complete]
     *
     * @param repairProdOrderServiceModel
     * @param logonInfo
     * @throws ServiceEntityConfigureException
     * @throws ServiceEntityInstallationException
     * @throws NodeNotFoundException
     * @throws SearchConfigureException
     */
    public void setOrderComplete(RepairProdOrderServiceModel repairProdOrderServiceModel, LogonInfo logonInfo) throws ServiceEntityConfigureException, SearchConfigureException, NodeNotFoundException, ServiceEntityInstallationException, ProductionOrderException, ServiceModuleProxyException {
        /*
         * [Step1] Set status of production order itself.
         */
        String logonUserUUID = logonInfo.getRefUserUUID();
        String organizationUUID = logonInfo.getResOrgUUID();
        if (ServiceCollectionsHelper.checkNullList(repairProdOrderServiceModel.getRepairProdTargetMatItemList())) {
            throw new ProductionOrderException(ProductionOrderException.PARA_NO_TARGETITEM);
        }

        List<ServiceEntityNode> allRepairProdTargetMatItemList = new ArrayList<>();
        repairProdOrderServiceModel.getRepairProdTargetMatItemList().forEach(repairProdOrderMaItemServiceModel -> {
            allRepairProdTargetMatItemList.add(repairProdOrderMaItemServiceModel.getRepairProdTargetMatItem());
        });
        List<SimpleSEMessageResponse> rawMessageList = preCheckSetComplete(repairProdOrderServiceModel, logonInfo);
        List<SimpleSEMessageResponse> errorMessageList = ServiceMessageResponseHelper.filerSEMessageResponseByLevel(SimpleSEMessageResponse.MESSAGELEVEL_ERROR, rawMessageList);
        if (!ServiceCollectionsHelper.checkNullList(errorMessageList)) {
            throw new ProductionOrderException(ProductionOrderException.PARA_NODONE_TAEGET, errorMessageList);
        }
        /*
         * [Step2] Set to complete core
         */
        RepairProdOrder repairProdOrder = repairProdOrderServiceModel.getRepairProdOrder();
        setOrderCompleteCore(repairProdOrder, logonUserUUID, organizationUUID);

    }

    @Deprecated
    public void setOrderCompleteCore(RepairProdOrder repairProdOrder, String logonUserUUID, String organizationUUID) {
        repairProdOrder.setStatus(RepairProdOrder.STATUS_FINISHED);
        repairProdOrder.setActualEndTime(java.time.LocalDateTime.now());
        this.updateSENode(repairProdOrder, logonUserUUID, organizationUUID);
    }

    public static List<ServiceEntityNode> convertRepairProdItemProposalList(List<RepairProdItemReqProposalServiceModel> rawRepairProdItemReqProposalList) {
        List<ServiceEntityNode> prodOrderItemReqProposalList = new ArrayList<>();
        if (ServiceCollectionsHelper.checkNullList(rawRepairProdItemReqProposalList)) {
            return null;
        }
        for (RepairProdItemReqProposalServiceModel repairProdItemReqProposalServiceModel : rawRepairProdItemReqProposalList) {
            prodOrderItemReqProposalList.add(repairProdItemReqProposalServiceModel.getRepairProdItemReqProposal());
        }
        return prodOrderItemReqProposalList;
    }

    /**
     * Batch release production order's sub proposal to picking order resource
     *
     * @throws MaterialException
     * @throws BillOfMaterialException
     * @throws ServiceEntityConfigureException
     */
    public void releaseRepairProdOrderToPickingBatch(RepairProdOrderServiceModel repairProdOrderServiceModel,
                                                     LogonInfo logonInfo) throws MaterialException,
            BillOfMaterialException, ServiceEntityConfigureException {
        /*
         * [Step1] Batch generate picking order
         */
        List<String> rootNodeUUIDList = new ArrayList<>();
        for (RepairProdOrderItemServiceModel repairProdOrderItemServiceModel : repairProdOrderServiceModel.getRepairProdOrderItemList()) {
            RepairProdOrderItem repairProdOrderItem = repairProdOrderItemServiceModel.getRepairProdOrderItem();
            StorageCoreUnit requestCoreUnit = new StorageCoreUnit(repairProdOrderItem.getRefMaterialSKUUUID(), repairProdOrderItem.getRefUnitUUID(), repairProdOrderItem.getActualAmount());
            try {
                List<ServiceEntityNode> inPlanRefMaterialItemList = prodOrderWithPickingOrderProxy.updateRequestToPickingOrderWrapper(
                        requestCoreUnit, repairProdOrderItem, convertRepairProdItemProposalList(repairProdOrderItemServiceModel.getRepairProdItemReqProposalList()),
                        ProdPickingOrder.PROCESSTYPE_INPLAN, false, logonInfo);
                if (!ServiceCollectionsHelper.checkNullList(inPlanRefMaterialItemList)) {
                    for (ServiceEntityNode tmpSENode : inPlanRefMaterialItemList) {
                        ServiceCollectionsHelper.mergeToList(rootNodeUUIDList, tmpSENode.getRootNodeUUID());
                    }
                }
            } catch (SearchConfigureException | ServiceModuleProxyException | ServiceEntityConfigureException |
                     NodeNotFoundException | ServiceEntityInstallationException | ServiceComExecuteException |
                     DocActionException e) {
                // log error and continue
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, repairProdOrderItem.getId()), e);
            } catch (AuthorizationException | LogonInfoException e) {
                // log error and continue
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, repairProdOrderItem.getId()), e);
            }
        }
        /*
         * [Step2] Approve this picking Order' resource and generate all
         * previous documents
         */
        if (!ServiceCollectionsHelper.checkNullList(rootNodeUUIDList)) {
            for (String rootNodeUUID : rootNodeUUIDList) {
                ProdPickingOrder prodPickingOrder = (ProdPickingOrder) prodPickingOrderManager.getEntityNodeByKey(rootNodeUUID, IServiceEntityNodeFieldConstant.UUID, ProdPickingOrder.NODENAME, null);
                ProdPickingOrderServiceModel prodPickingOrderServiceModel;
                try {
                    prodPickingOrderServiceModel = (ProdPickingOrderServiceModel) prodPickingOrderManager.loadServiceModule(ProdPickingOrderServiceModel.class, prodPickingOrder);
                    if (prodPickingOrderServiceModel != null) {
                        prodPickingOrderManager.approvePickingOrder(prodPickingOrderServiceModel, logonInfo);
                    }
                } catch (ServiceModuleProxyException e) {
                    // log error and continue
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, prodPickingOrder.getId()), e);
                }
            }
        }

    }

    private void updateRecentBOMOrderToRepairProdOrder(RepairProdOrder repairProdOrder) throws BillOfMaterialException, ServiceEntityConfigureException {
        BillOfMaterialOrder billOfMaterialOrder = billOfMaterialOrderManager.switchToRecentActiveBOMOrder(repairProdOrder.getRefBillOfMaterialUUID(), repairProdOrder.getClient(), false);
        if (billOfMaterialOrder.getUuid().equals(repairProdOrder.getRefBillOfMaterialUUID())) {
            // in case need to be updated
            repairProdOrder.setRefBillOfMaterialUUID(billOfMaterialOrder.getUuid());
        }
    }

    /**
     * Delete all the generated plan sub resources, keep plan basic information
     *
     * @param baseUUID
     * @param logonUserUUID
     * @param organizationUUID
     * @param client
     * @throws ServiceEntityConfigureException
     */
    public void deleteOrderSubResource(String baseUUID, String logonUserUUID, String organizationUUID, String client) throws ServiceEntityConfigureException {
        List<ServiceEntityNode> rawProdOrderItemList = this.getEntityNodeListByKey(baseUUID, IServiceEntityNodeFieldConstant.ROOTNODEUUID, RepairProdOrderItem.NODENAME, client, null);
        if (!ServiceCollectionsHelper.checkNullList(rawProdOrderItemList)) {
            this.deleteSENode(rawProdOrderItemList, logonUserUUID, organizationUUID);
        }
        List<ServiceEntityNode> rawOrderItemReqProposal = this.getEntityNodeListByKey(baseUUID, IServiceEntityNodeFieldConstant.ROOTNODEUUID, RepairProdItemReqProposal.NODENAME, client, null);
        if (!ServiceCollectionsHelper.checkNullList(rawOrderItemReqProposal)) {
            this.deleteSENode(rawOrderItemReqProposal, logonUserUUID, organizationUUID);
        }
        //		List<ServiceEntityNode> rawOrderTargetMatItemList = this
        //				.getEntityNodeListByKey(baseUUID, IServiceEntityNodeFieldConstant.ROOTNODEUUID, RepairProdTargetMatItem.NODENAME,
        //						client, null);
        //		if (!ServiceCollectionsHelper.checkNullList(rawOrderTargetMatItemList)) {
        //			this.deleteSENode(rawOrderTargetMatItemList, logonUserUUID, organizationUUID);
        //		}
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convRepairProdSupplyWarehouseToUI(RepairProdSupplyWarehouse repairProdSupplyWarehouse, RepairProdSupplyWarehouseUIModel repairProdSupplyWarehouseUIModel) {
        productionOrderManager.convProdOrderSupplyWarehouseToUI(repairProdSupplyWarehouse, repairProdSupplyWarehouseUIModel);
    }

    /**
     * [Internal method] Convert from UI model to se
     * model:repairProdSupplyWarehouse
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToRepairProdSupplyWarehouse(RepairProdSupplyWarehouseUIModel repairProdSupplyWarehouseUIModel, RepairProdSupplyWarehouse rawEntity) {
        productionOrderManager.convUIToProdOrderSupplyWarehouse(repairProdSupplyWarehouseUIModel, rawEntity);
    }

    /**
     * [Internal method] Convert from UI model to se model:repairProdOrder
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToRepairProdOrder(RepairProdOrderUIModel repairProdOrderUIModel, RepairProdOrder rawEntity) {
        productionOrderManager.convUIToProductionOrder(repairProdOrderUIModel, rawEntity);
    }

    public Map<Integer, String> initPriorityCodeMap(String languageCode) throws ServiceEntityInstallationException {
        return standardPriorityProxy.getPriorityMap(languageCode);
    }

    public Map<Integer, String> initStatusMap(String languageCode) throws ServiceEntityInstallationException {
        String resourcePath = RepairProdOrderUIModel.class.getResource("").getPath() + "RepairProdOrder_status";
        return ServiceLanHelper.initDefLanguageMapResource(languageCode, this.statusMapLan, resourcePath);
    }

    public Map<Integer, String> initGenOrderItemModeMap(String languageCode) throws ServiceEntityInstallationException {
        String resourcePath = RepairProdOrderUIModel.class.getResource("").getPath() + "RepairProdOrder_genOrderItemMode";
        return ServiceLanHelper.initDefLanguageMapResource(languageCode, this.genOrderItemModeLan, resourcePath);
    }

    public Map<Integer, String> initDoneStatusMap(String languageCode) throws ServiceEntityInstallationException {
        String resourcePath = RepairProdOrderUIModel.class.getResource("").getPath() + "RepairProdOrder_doneStatus";
        return ServiceLanHelper.initDefLanguageMapResource(languageCode, this.doneStatusMapLan, resourcePath);
    }

    public Map<Integer, String> initItemStatusMap(String languageCode) throws ServiceEntityInstallationException {
        return repairProdOrderItemManager.initItemStatusMap(languageCode);
    }

    public Map<Integer, String> initCategoryMap(String languageCode) throws ServiceEntityInstallationException {
        String resourcePath = RepairProdOrderUIModel.class.getResource("").getPath() + "RepairProdOrder_category";
        return ServiceLanHelper.initDefLanguageMapResource(languageCode, this.categoryMapLan, resourcePath);
    }

    public Map<Integer, String> initOrderTypeMap(String languageCode) throws ServiceEntityInstallationException {
        String resourcePath = RepairProdOrderUIModel.class.getResource("").getPath() + "RepairProdOrder_orderType";
        return ServiceLanHelper.initDefLanguageMapResource(languageCode, this.orderTypeMapLan, resourcePath);
    }

    public Map<Integer, String> initDocumentTypeMap(String languageCode) throws ServiceEntityInstallationException {
        return serviceDocumentComProxy.getDocumentTypeMap(false, languageCode);
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, repairProdOrderDAO));
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(repairProdOrderConfigureProxy);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convReportItemMaterialSKUToUI(MaterialStockKeepUnit itemMaterialSKU, ProdOrderReportItemUIModel prodOrderReportItemUIModel) {
        if (itemMaterialSKU != null) {
            prodOrderReportItemUIModel.setRefMaterialSKUName(itemMaterialSKU.getName());
            prodOrderReportItemUIModel.setRefMaterialSKUId(itemMaterialSKU.getId());
            prodOrderReportItemUIModel.setPackageStandard(itemMaterialSKU.getPackageStandard());

        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convRegisteredProductToReportItemUI(RegisteredProduct registeredProduct, ProdOrderReportItemUIModel prodOrderReportItemUIModel) {
        if (registeredProduct != null) {
            prodOrderReportItemUIModel.setRefMaterialSKUName(registeredProduct.getName());
            prodOrderReportItemUIModel.setRefMaterialSKUId(registeredProduct.getId());

        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convOutBillOfMaterialOrderToUI(BillOfMaterialOrder outBillOfMaterialOrder, RepairProdOrderUIModel repairProdOrderUIModel) {
        if (outBillOfMaterialOrder != null) {
            repairProdOrderUIModel.setRefBillOfMaterialId(outBillOfMaterialOrder.getId());
            repairProdOrderUIModel.setRefBillOfMaterialNodeName(BillOfMaterialOrder.NODENAME);
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convOutBillOfMaterialItemToUI(BillOfMaterialItem outBillOfMaterialItem, RepairProdOrderUIModel repairProdOrderUIModel) {
        if (outBillOfMaterialItem != null) {
            repairProdOrderUIModel.setRefBillOfMaterialId(outBillOfMaterialItem.getId());
            repairProdOrderUIModel.setRefBillOfMaterialNodeName(BillOfMaterialItem.NODENAME);
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convItemBillOfMaterialItemToUI(BillOfMaterialItem itemBillOfMaterialItem, RepairProdOrderItemUIModel repairProdOrderItemUIModel) {
        if (itemBillOfMaterialItem != null) {
            repairProdOrderItemUIModel.setRefBOMItemUUID(itemBillOfMaterialItem.getUuid());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convWarehouseToUI(Warehouse warehouse, RepairProdSupplyWarehouseUIModel repairProdSupplyWarehouseUIModel) {
        if (warehouse != null) {
            repairProdSupplyWarehouseUIModel.setRefWarehouseId(warehouse.getId());
            repairProdSupplyWarehouseUIModel.setRefWarehouseName(warehouse.getName());
        }
    }

    public void convOutMaterialStockKeepUnitToUI(MaterialStockKeepUnit outMaterialStockKeepUnit, RepairProdOrderUIModel repairProdOrderUIModel) {
        convOutMaterialStockKeepUnitToUI(outMaterialStockKeepUnit, repairProdOrderUIModel, null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convOutMaterialStockKeepUnitToUI(MaterialStockKeepUnit outMaterialStockKeepUnit, RepairProdOrderUIModel repairProdOrderUIModel, LogonInfo logonInfo) {
        productionOrderManager.convOutMaterialStockKeepUnitToUI(outMaterialStockKeepUnit, repairProdOrderUIModel, logonInfo);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convProdWorkCenterToUI(ProdWorkCenter prodWorkCenter, RepairProdOrderUIModel repairProdOrderUIModel) {
        if (prodWorkCenter != null) {
            repairProdOrderUIModel.setRefWocId(prodWorkCenter.getId());
            repairProdOrderUIModel.setRefWocName(prodWorkCenter.getName());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convProductionPlanToUI(ProductionPlan productionPlan, RepairProdOrderUIModel repairProdOrderUIModel) {
        if (productionPlan != null) {
            repairProdOrderUIModel.setRefPlanId(productionPlan.getId());
            repairProdOrderUIModel.setRefPlanName(productionPlan.getName());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convApproveByToUI(LogonUser logonUser, RepairProdOrderUIModel repairProdOrderUIModel) {
        if (logonUser != null) {
            repairProdOrderUIModel.setApproveById(logonUser.getId());
            repairProdOrderUIModel.setApproveByName(logonUser.getName());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convCountApproveByToUI(LogonUser logonUser, RepairProdOrderUIModel repairProdOrderUIModel) {
        if (logonUser != null) {
            repairProdOrderUIModel.setCountApproveById(logonUser.getId());
            repairProdOrderUIModel.setCountApproveByName(logonUser.getName());
        }
    }

    public RepairProdOrderManager() {
        super.seConfigureProxy = new RepairProdOrderConfigureProxy();
    }

    @Override
    public ServiceEntityNode newRootEntityNode(String client) throws ServiceEntityConfigureException {
        RepairProdOrder repairProdOrder = (RepairProdOrder) super.newRootEntityNode(client);
        String repairProdOrderID = repairProdOrderIdHelper.genDefaultId(client);
        repairProdOrder.setId(repairProdOrderID);
        return repairProdOrder;
    }

    /**
     * Logic of cancel this document
     */
    public void cancelDocument(RepairProdOrder repairProdOrder, String logonUserUUID, String organizationUUID) throws ServiceEntityConfigureException, SearchConfigureException, NodeNotFoundException, ServiceEntityInstallationException {
        /**
         * [Step1] Set the RepairProdOrder status as [cancel]
         */
        repairProdOrder.setStatus(DocumentContent.STATUS_CANCELED);
        this.updateSENode(repairProdOrder, logonUserUUID, organizationUUID);
    }

    public void genFinAccountCore(String resourceID, RepairProdOrder repairProdOrder, LogonUser logonUser, Organization organization) throws SystemResourceException, FinanceAccountValueProxyException, ServiceEntityConfigureException {
        /*
         * [Step1] set the status into [in-settle]
         */
        repairProdOrder.setStatus(RepairProdOrder.STATUS_APPROVED);
        updateSENode(repairProdOrder, logonUser.getUuid(), organization.getUuid());
        /*
         * [Step2] update the finance account
         */
        systemResourceFinanceAccountProxy.updateFinAccByResourceID(repairProdOrder, resourceID, repairProdOrder.getUuid(), DefFinanceControllerResource.PROCESS_CODE_SAVE, logonUser.getUuid(), organization.getRefFinOrgUUID(), organization.getUuid(), logonUser.getClient());
    }

    private ServiceModule convertToRepairProdOrderServiceModel(RepairProdOrderServiceModel repairProdOrderServiceModel, List<ServiceEntityNode> rawProdOrderItemList) {
        RepairProdOrder repairProdOrder = repairProdOrderServiceModel.getRepairProdOrder();
        List<ServiceEntityNode> subProdItemList = ServiceCollectionsHelper.filterSENodeByParentUUID(repairProdOrder.getUuid(), rawProdOrderItemList);
        List<RepairProdOrderItemServiceModel> repairProdOrderItemList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(subProdItemList)) {
            for (ServiceEntityNode seNode : subProdItemList) {
                if (IServiceModelConstants.RepairProdOrderItem.equals(seNode.getNodeName())) {
                    RepairProdOrderItem repairProdOrderItem = (RepairProdOrderItem) seNode;
                    RepairProdOrderItemServiceModel subRepairProdOrderItemServiceModel = generateSubProdutionOrderItem(repairProdOrderItem, rawProdOrderItemList);
                    repairProdOrderItemList.add(subRepairProdOrderItemServiceModel);
                }
            }
            repairProdOrderServiceModel.setRepairProdOrderItemList(repairProdOrderItemList);
        }
        return repairProdOrderServiceModel;
    }

    private RepairProdOrderItemServiceModel generateSubProdutionOrderItem(RepairProdOrderItem repairProdOrderItem, List<ServiceEntityNode> rawProdOrderItemList) {
        List<RepairProdItemReqProposalServiceModel> prodOrderItemReqProposalList = new ArrayList<>();
        List<ServiceEntityNode> subProdItemList = ServiceCollectionsHelper.filterSENodeByParentUUID(repairProdOrderItem.getUuid(), rawProdOrderItemList);
        RepairProdOrderItemServiceModel repairProdOrderItemServiceModel = new RepairProdOrderItemServiceModel();
        repairProdOrderItemServiceModel.setRepairProdOrderItem(repairProdOrderItem);
        if (!ServiceCollectionsHelper.checkNullList(subProdItemList)) {
            for (ServiceEntityNode seNode : subProdItemList) {
                //				if (IServiceModelConstants.RepairProdOrderItem.equals(seNode.getNodeName())) {
                //					// In case this node is 'RepairProdOrderItem'
                //					RepairProdOrderItem subRepairProdOrderItem = (RepairProdOrderItem) seNode;
                //					RepairProdOrderItemServiceModel subRepairProdOrderItemServiceModel = generateSubProdutionOrderItem(
                //							subRepairProdOrderItem, rawProdOrderItemList);
                //					subRepairProdOrderItemList.add(subRepairProdOrderItemServiceModel);
                //				}
                if (RepairProdItemReqProposal.NODENAME.equals(seNode.getNodeName())) {
                    assert seNode instanceof RepairProdItemReqProposal;
                    RepairProdItemReqProposal prodOrderItemReqProposal = (RepairProdItemReqProposal) seNode;
                    RepairProdItemReqProposalServiceModel repairProdItemReqProposalServiceModel = generateSubRepairProdItemReqProposalServiceModel(prodOrderItemReqProposal, rawProdOrderItemList);
                    prodOrderItemReqProposalList.add(repairProdItemReqProposalServiceModel);
                }
            }
            repairProdOrderItemServiceModel.setRepairProdItemReqProposalList(prodOrderItemReqProposalList);
        }
        return repairProdOrderItemServiceModel;
    }

    protected RepairProdItemReqProposalServiceModel generateSubRepairProdItemReqProposalServiceModel(RepairProdItemReqProposal repairProdItemReqProposal, List<ServiceEntityNode> rawProdOrderItemList) {
        List<RepairProdOrderItemServiceModel> subRepairProdOrderItemList = new ArrayList<>();
        List<ServiceEntityNode> subProdItemList = ServiceCollectionsHelper.filterSENodeByParentUUID(repairProdItemReqProposal.getUuid(), rawProdOrderItemList);
        RepairProdItemReqProposalServiceModel repairProdItemReqProposalServiceModel = new RepairProdItemReqProposalServiceModel();
        repairProdItemReqProposalServiceModel.setRepairProdItemReqProposal(repairProdItemReqProposal);
        if (!ServiceCollectionsHelper.checkNullList(subProdItemList)) {
            for (ServiceEntityNode seNode : subProdItemList) {
                if (IServiceModelConstants.RepairProdOrderItem.equals(seNode.getNodeName())) {
                    // In case this node is 'RepairProdOrderItem'
                    RepairProdOrderItem subRepairProdOrderItem = (RepairProdOrderItem) seNode;
                    RepairProdOrderItemServiceModel subRepairProdOrderItemServiceModel = generateSubProdutionOrderItem(subRepairProdOrderItem, rawProdOrderItemList);
                    subRepairProdOrderItemList.add(subRepairProdOrderItemServiceModel);
                }
            }
            repairProdItemReqProposalServiceModel.setRepairProdOrderItemList(subRepairProdOrderItemList);
        }
        return repairProdItemReqProposalServiceModel;
    }

    /**
     * Short way to production order's warehouse UUID List
     *
     * @param baseUUID
     * @param client
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<String> getWarehouseUUIDList(String baseUUID, String client) throws ServiceEntityConfigureException {
        List<ServiceEntityNode> prodSupplyWarehouseList = getEntityNodeListByKey(baseUUID, IServiceEntityNodeFieldConstant.ROOTNODEUUID, RepairProdSupplyWarehouse.NODENAME, client, null);
        if (ServiceCollectionsHelper.checkNullList(prodSupplyWarehouseList)) {
            return null;
        }
        List<String> warehouseUUIDList = new ArrayList<String>();
        prodSupplyWarehouseList.forEach(seNode -> {
            RepairProdSupplyWarehouse repairProdSupplyWarehouse = (RepairProdSupplyWarehouse) seNode;
            warehouseUUIDList.add(repairProdSupplyWarehouse.getRefUUID());
        });
        return warehouseUUIDList;
    }

    /**
     * Main Entry method to generate all production order item list as well as
     * out-bound delivery and sub production order list by Root instance of
     * production order
     *
     * @return
     * @throws ServiceEntityConfigureException
     * @throws BillOfMaterialException
     * @throws MaterialException
     * @throws ProductionOrderException
     * @throws ServiceEntityInstallationException
     * @throws NodeNotFoundException
     * @throws SearchConfigureException
     */
    public List<ServiceEntityNode> generateProductItemListEntry(RepairProdOrderServiceModel repairProdOrderServiceModel, LogonInfo logonInfo,
                                                                boolean executeFlag) throws ServiceEntityConfigureException, BillOfMaterialException, MaterialException, ProductionOrderException, SearchConfigureException, NodeNotFoundException, ServiceEntityInstallationException, AuthorizationException, LogonInfoException {
        /*
         * [Step1] Data prepare: compound productive BOM list
         */
        RepairProdOrder repairProdOrder = repairProdOrderServiceModel.getRepairProdOrder();
        BillOfMaterialOrder billOfMaterialOrder = billOfMaterialOrderManager.getRefBillOfMaterialOrderWrapper(repairProdOrder.getRefBillOfMaterialUUID(), repairProdOrder.getClient());
        List<ServiceEntityNode> productiveBOMList = billOfMaterialOrderManager.genProductiveBOMModel(billOfMaterialOrder);
        if (productiveBOMList == null || productiveBOMList.size() == 0) {
            return null;
        }
        // All Material list
        List<String> materialUUIDList = new ArrayList<>();
        materialUUIDList.add(repairProdOrder.getRefMaterialSKUUUID());
        for (ServiceEntityNode seNode : productiveBOMList) {
            ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
            materialUUIDList.add(productiveBOMItem.getRefMaterialSKUUUID());
        }
        Map<String, List<?>> materialUUIDMap = new HashMap<>();
        materialUUIDMap.put(IServiceEntityNodeFieldConstant.UUID, materialUUIDList);
        MaterialStockKeepUnitSearchModel searchModel = new MaterialStockKeepUnitSearchModel();
        searchModel.setUuid(ServiceEntityStringHelper.convStringListIntoMultiStringValue(materialUUIDList));
        SearchContext searchContext = SearchContextBuilder.genBuilder(logonInfo).searchModel(searchModel).build();
        searchContext.setFieldNameArray(new String[]{IServiceEntityNodeFieldConstant.UUID});
        List<ServiceEntityNode> rawMaterialSKUList = materialStockKeepUnitManager.getSearchProxy().searchDocList(searchContext).getResultList();
        /*
         * [Step2] Data prepare: rawStoreItemList
         */
        List<ServiceEntityNode> rawStoreItemList = new ArrayList<>();
        List<ServiceEntityNode> prodSupplyWarehouseList = getEntityNodeListByKey(repairProdOrder.getUuid(), IServiceEntityNodeFieldConstant.ROOTNODEUUID, RepairProdSupplyWarehouse.NODENAME, repairProdOrder.getClient(), null);
        /*
         * [Step3] Generate the production list and proposal
         */
        List<String> warehouseUUIDList = null;
        try {
            warehouseUUIDList = ServiceCollectionsHelper.pluckList(prodSupplyWarehouseList, IReferenceNodeFieldConstant.REFUUID);
            rawStoreItemList = warehouseStoreItemManager.getInStockStoreItemList(warehouseUUIDList, repairProdOrder.getClient());
            List<ServiceEntityNode> rawStoreItemListBack = ServiceCollectionsHelper.cloneSEList(rawStoreItemList);
            return generateProductItemListProposal(repairProdOrderServiceModel, billOfMaterialOrder, prodSupplyWarehouseList, productiveBOMList, rawStoreItemList, rawMaterialSKUList);
        } catch (NoSuchFieldException | ServiceComExecuteException e) {
            throw new ProductionOrderException(ProductionOrderException.PARA_SYSTEM_ERROR, e.getMessage());
        }
    }

    /**
     * Calculate the ratio from Production Order
     *
     * @param repairProdOrder
     * @param billOfMaterialOrder
     * @param productiveBOMList
     * @return
     * @throws MaterialException
     * @throws ServiceEntityConfigureException
     */
    private double getRatioFromProductionToBOMOrder(RepairProdOrder repairProdOrder, BillOfMaterialOrder billOfMaterialOrder, List<ServiceEntityNode> productiveBOMList) throws MaterialException, ServiceEntityConfigureException {
        StorageCoreUnit prodOrderStorageCoreUnit = new StorageCoreUnit();
        prodOrderStorageCoreUnit.setAmount(repairProdOrder.getAmount());
        prodOrderStorageCoreUnit.setRefMaterialSKUUUID(repairProdOrder.getRefMaterialSKUUUID());
        prodOrderStorageCoreUnit.setRefUnitUUID(repairProdOrder.getRefUnitUUID());
        return getRatioFromProductionToBOMOrder(prodOrderStorageCoreUnit, repairProdOrder.getRefBillOfMaterialUUID(), billOfMaterialOrder, productiveBOMList, repairProdOrder.getClient());
    }

    /**
     * Calculate the ratio from Production Order to BOM/BOM item
     *
     * @param requestStorageCoreUnit
     * @param billOfMaterialOrder
     * @param productiveBOMList
     * @return
     * @throws MaterialException
     * @throws ServiceEntityConfigureException
     */
    public double getRatioFromProductionToBOMOrder(StorageCoreUnit requestStorageCoreUnit, String refBOMUUID, BillOfMaterialOrder billOfMaterialOrder, List<ServiceEntityNode> productiveBOMList, String client) throws MaterialException, ServiceEntityConfigureException {
        return productionOrderManager.getRatioFromProductionToBOMOrder(requestStorageCoreUnit, refBOMUUID, billOfMaterialOrder, productiveBOMList, client);
    }

    /**
     * Logic for generate the production item proposal including the sub
     * requirement for out-bound delivery and production or purchase requirement
     *
     * @param repairProdOrderServiceModel
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     * @throws ProductionOrderException
     */
    public List<ServiceEntityNode> generateProductItemListProposal(RepairProdOrderServiceModel repairProdOrderServiceModel, BillOfMaterialOrder billOfMaterialOrder, List<ServiceEntityNode> prodSupplyWarehouseList, List<ServiceEntityNode> productiveBOMList, List<ServiceEntityNode> rawStoreItemList, List<ServiceEntityNode> rawMaterialSKUList) throws ServiceEntityConfigureException, MaterialException, ProductionOrderException, ServiceComExecuteException {
        if (productiveBOMList == null || productiveBOMList.size() == 0) {
            return null;
        }
        RepairProdOrder repairProdOrder = repairProdOrderServiceModel.getRepairProdOrder();
        //double ratio = getRatioFromProductionToBOMOrder(repairProdOrder, billOfMaterialOrder, productiveBOMList);

        /*
         * [Step1] Calculate the base ratio from production order to BOM
         */

        List<RepairProdOrderItemServiceModel> repairProdOrderItemServiceModelList = repairProdOrderServiceModel.getRepairProdOrderItemList();
        if (ServiceCollectionsHelper.checkNullList(repairProdOrderItemServiceModelList)) {
            return null;
        }
        List<ServiceEntityNode> repairProdProposalItemList = new ArrayList<>();
        for (RepairProdOrderItemServiceModel repairProdOrderItemServiceModel : repairProdOrderItemServiceModelList) {
            RepairProdOrderItem repairProdOrderItem = repairProdOrderItemServiceModel.getRepairProdOrderItem();
            ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) billOfMaterialOrderManager.filterBOMItemByUUID(repairProdOrderItem.getRefBOMItemUUID(), productiveBOMList);
            //double amount = productiveBOMItem.getAmount() * ratio;
            // Calculate the amount with loss rate
            //double amountWithLossRate = amount / (1 - productiveBOMItem.getTheoLossRate() / 100);
            //amountWithLossRate = Math.ceil(amountWithLossRate);
            // Update the amount of Productive BOM item
            productiveBOMItem.setAmountWithLossRate(repairProdOrderItem.getAmountWithLossRate());
            //repairProdOrderItem.setAmount(amount);
            repairProdOrderItem.setActualAmount(repairProdOrderItem.getAmountWithLossRate());
            //repairProdOrderItem.setAmountWithLossRate(amountWithLossRate);
            repairProdOrderItem.setRefMaterialSKUUUID(productiveBOMItem.getRefMaterialSKUUUID());
            repairProdOrderItem.setRefUnitUUID(productiveBOMItem.getRefUnitUUID());
            // repairProdOrderItem.setProductionBatchNumber(productionOrder.getProductionBatchNumber());
            repairProdOrderItem.setRefBOMItemUUID(productiveBOMItem.getUuid());
            StorageCoreUnit requestCoreUnit = new StorageCoreUnit(repairProdOrderItem.getRefMaterialSKUUUID(), repairProdOrderItemServiceModel.getRepairProdOrderItem().getRefUnitUUID(), repairProdOrderItem.getActualAmount());
            repairProdProposalItemList.add(repairProdOrderItem);
            List<ServiceEntityNode> subProposalItemList = genProposalToProdOrderItemCore(repairProdOrder, prodSupplyWarehouseList, productiveBOMList, rawStoreItemList, rawMaterialSKUList, requestCoreUnit, repairProdOrderItemServiceModel.getRepairProdOrderItem());
            if (!ServiceCollectionsHelper.checkNullList(subProposalItemList)) {
                // Should convert all the prodItemReqProposal to repairProdItemReqProposal
                subProposalItemList.forEach(serviceEntityNode -> {
                    if (serviceEntityNode instanceof ProdOrderItemReqProposal) {
                        ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) serviceEntityNode;
                        try {
                            RepairProdItemReqProposal prodItemReqProposal = ServiceReflectiveHelper.castCopyModel(prodOrderItemReqProposal, RepairProdItemReqProposal.class);

                        } catch (IllegalAccessException | InstantiationException e) {
                            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                        }
                    }
                });
                repairProdProposalItemList.addAll(subProposalItemList);
            }
        }
        return repairProdProposalItemList;
    }

    /**
     * Core Logic to generate production proposal list for specified production
     * order item
     *
     * @param repairProdOrderItem
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    public List<ServiceEntityNode> genProposalToProdOrderItemCore(RepairProdOrder rootRepairProdOrder, List<ServiceEntityNode> prodSupplyWarehouseList, List<ServiceEntityNode> productiveBOMList, List<ServiceEntityNode> rawStoreItemList, List<ServiceEntityNode> rawMaterialList, StorageCoreUnit requestCoreUnit, RepairProdOrderItem repairProdOrderItem) throws MaterialException, ServiceEntityConfigureException, ProductionOrderException, ServiceComExecuteException {
        /*
         * [Step1] Calculate the current storage, and generate direct out-bound
         * proposal firstly.
         */
        ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) billOfMaterialOrderManager.filterBOMItemByUUID(repairProdOrderItem.getRefBOMItemUUID(), productiveBOMList);
        // Default request core unit is generated from production order item,
        // Or else it could be special request, such as additional picking request
        if (requestCoreUnit == null) {
            requestCoreUnit = new StorageCoreUnit(repairProdOrderItem.getRefMaterialSKUUUID(), repairProdOrderItem.getRefUnitUUID(), repairProdOrderItem.getActualAmount());
        }
        productiveBOMItem.setAmountWithLossRate(requestCoreUnit.getAmount());
        List<ServiceEntityNode> productionProposalItemList = new ArrayList<>();
        if (prodSupplyWarehouseList != null && prodSupplyWarehouseList.size() > 0) {
            for (ServiceEntityNode tmpSENode : prodSupplyWarehouseList) {
                ProdOrderSupplyWarehouse prodOrderSupplyWarehouse = (ProdOrderSupplyWarehouse) tmpSENode;
                List<ServiceEntityNode> outReqProposalList = productionOrderManager.generateOutboundDeliveryProposal(rootRepairProdOrder, repairProdOrderItem, requestCoreUnit, productiveBOMItem, prodOrderSupplyWarehouse.getRefUUID(), rawStoreItemList);
                if (!ServiceCollectionsHelper.checkNullList(outReqProposalList)) {
                    productionProposalItemList.addAll(outReqProposalList);
                }
                /*
                 * [Step1.5] Calculate Available amount for production order
                 * item
                 */
                //TODO bug:amount can't be direct added
                double availableAmount = ProductionPlanManager.calculateSumProposalAmount(outReqProposalList);
                repairProdOrderItem.setAvailableAmount(availableAmount);
                if (repairProdOrderItem.getItemStatus() == ProductionPlanItem.STATUS_AVAILABLE) {
                    break;
                }
            }
        }
        /*
         * [Step2] In case the request amount is not satisfied with warehouse
         * store, then trying to generate purchase or sub production order
         */
        if (productiveBOMItem.getAmountWithLossRate() > 0) {
            // generate the purchase or productive order item
            MaterialStockKeepUnit materialStockKeepUnit = materialStockKeepUnitManager.getMaterialSKUWrapper(productiveBOMItem.getRefMaterialSKUUUID(), productiveBOMItem.getClient(), rawMaterialList);
            ProdOrderItemReqProposal prodOrderItemReqProposal = productionOrderManager.generateProdPurchaseProposal(rootRepairProdOrder, repairProdOrderItem, productiveBOMItem, materialStockKeepUnit);
            try {
                RepairProdItemReqProposal repairProdItemReqProposal = ServiceReflectiveHelper.castCopyModel(prodOrderItemReqProposal, RepairProdItemReqProposal.class);
                /*
                 * [Step2.4] Calculate in process amount
                 */
                repairProdOrderItem.setItemStatus(ProductionPlanItem.STATUS_INPROCESS);
                if (repairProdItemReqProposal != null) {
                    productionProposalItemList.add(repairProdItemReqProposal);
                }
                /*
                 * [Step2.5] Process the sub Productive BOM list
                 */
                if (needFurtherBOMPlan(repairProdItemReqProposal)) {
                    List<ServiceEntityNode> subProdItemList = generateSubRepairItemUnion(rootRepairProdOrder, repairProdItemReqProposal, productiveBOMItem, productiveBOMList, prodSupplyWarehouseList, rawStoreItemList, rawMaterialList);
                    if (subProdItemList != null && subProdItemList.size() > 0) {
                        productionProposalItemList.addAll(subProdItemList);
                    }
                }
            } catch (IllegalAccessException | InstantiationException e) {
                throw new ProductionOrderException(ProductionOrderException.PARA_SYSTEM_ERROR, e.getMessage());
            }

        }
        return productionProposalItemList;
    }

    protected List<ServiceEntityNode> generateSubRepairItemUnion(RepairProdOrder rootRepairProdOrder, RepairProdItemReqProposal repairProdItemReqProposal, ProductiveBOMItem parentBOMItem, List<ServiceEntityNode> productiveBOMList, List<ServiceEntityNode> prodSupplyWarehouseList, List<ServiceEntityNode> rawStoreItemList, List<ServiceEntityNode> rawMterialSKUList) throws ServiceEntityConfigureException, MaterialException, ProductionOrderException, ServiceComExecuteException {
        List<ServiceEntityNode> subBOMItemList = billOfMaterialOrderManager.filterSubBOMItemList(parentBOMItem.getUuid(), productiveBOMList);
        if (subBOMItemList == null || subBOMItemList.size() == 0) {
            return null;
        }
        List<ServiceEntityNode> repairProdOrderItemList = new ArrayList<>();
        /*
         * [Step1] calculate the ratioWithLossRate by comparing parent
         * production item amountWithLossRate to BOM item amount it could
         * calculate the amoutWithLossRate in parent layer
         */
        double ratio = repairProdItemReqProposal.getAmount() / parentBOMItem.getAmount();
        for (ServiceEntityNode seNode : subBOMItemList) {
            ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
            RepairProdOrderItem repairProdOrderItem = (RepairProdOrderItem) newEntityNode(repairProdItemReqProposal, RepairProdOrderItem.NODENAME);
            repairProdOrderItem.setAmount(ratio * productiveBOMItem.getAmount());
            // theory amount with loss rate
            double amountWithLossRate = repairProdOrderItem.getAmount() / (1 - productiveBOMItem.getTheoLossRate() / 100);
            amountWithLossRate = Math.ceil(amountWithLossRate);
            repairProdOrderItem.setAmountWithLossRate(amountWithLossRate);
            repairProdOrderItem.setActualAmount(amountWithLossRate);
            productiveBOMItem.setAmountWithLossRate(amountWithLossRate);
            repairProdOrderItem.setRefMaterialSKUUUID(productiveBOMItem.getRefMaterialSKUUUID());
            repairProdOrderItem.setRefUnitUUID(productiveBOMItem.getRefUnitUUID());
            repairProdOrderItem.setRefBOMItemUUID(productiveBOMItem.getUuid());
            repairProdOrderItemList.add(repairProdOrderItem);

            List<ServiceEntityNode> productionProposalItemList = genProposalToProdOrderItemCore(rootRepairProdOrder, prodSupplyWarehouseList, productiveBOMList, rawStoreItemList, rawMterialSKUList, null, repairProdOrderItem);
            if (!ServiceCollectionsHelper.checkNullList(productionProposalItemList)) {
                repairProdOrderItemList.addAll(productionProposalItemList);
            }
        }
        return repairProdOrderItemList;
    }

    /**
     * Logic to calculate if current Production proposal need further BOM plan
     *
     * @param prodReqProposal
     * @return
     */
    public static boolean needFurtherBOMPlan(RepairProdItemReqProposal prodReqProposal) {
        return ProductionOrderManager.needFurtherBOMPlan(prodReqProposal);
    }

    protected List<ServiceEntityNode> filterItemProposalListByItemUUID(List<ServiceEntityNode> productionProposalItemList, String itemUUID) {
        if (ServiceCollectionsHelper.checkNullList(productionProposalItemList)) {
            return null;
        }
        if (ServiceEntityStringHelper.checkNullString(itemUUID)) {
            return null;
        }
        List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
        for (ServiceEntityNode seNode : productionProposalItemList) {
            if (seNode.getNodeName().equals(RepairProdItemReqProposal.NODENAME)) {
                RepairProdItemReqProposal prodOrderItemReqProposal = (RepairProdItemReqProposal) seNode;
                if (itemUUID.equals(prodOrderItemReqProposal.getRefBOMItemUUID())) {
                    resultList.add(prodOrderItemReqProposal);
                }
            }
        }
        return resultList;
    }

    protected List<ServiceEntityNode> filterRepairProdOrderItemListByItemUUID(List<ServiceEntityNode> productionProposalItemList, String itemUUID) {
        if (ServiceCollectionsHelper.checkNullList(productionProposalItemList)) {
            return null;
        }
        if (ServiceEntityStringHelper.checkNullString(itemUUID)) {
            return null;
        }
        List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
        for (ServiceEntityNode seNode : productionProposalItemList) {
            if (seNode.getNodeName().equals(RepairProdOrderItem.NODENAME)) {
                RepairProdOrderItem repairProdOrderItem = (RepairProdOrderItem) seNode;
                if (itemUUID.equals(repairProdOrderItem.getRefBOMItemUUID())) {
                    resultList.add(repairProdOrderItem);
                }
            }
        }
        return resultList;
    }

    public List<ServiceEntityNode> filterProductionItemList(List<ServiceEntityNode> rawList) {
        if (rawList == null || rawList.size() == 0) {
            return null;
        }
        List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
        for (ServiceEntityNode seNode : rawList) {
            if (RepairProdOrderItem.NODENAME.equals(seNode.getNodeName())) {
                resultList.add(seNode);
            }
        }
        return resultList;
    }

    public List<ServiceEntityNode> filterSubProdItemReqList(RepairProdOrderItem repairProdOrderItem, List<ServiceEntityNode> rawList) {
        if (rawList == null || rawList.size() == 0) {
            return null;
        }
        List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
        for (ServiceEntityNode seNode : rawList) {
            if (RepairProdItemReqProposal.NODENAME.equals(seNode.getNodeName()) && repairProdOrderItem.getUuid().equals(seNode.getParentNodeUUID())) {
                resultList.add(seNode);
            }
        }
        return resultList;
    }

    public void convReservedDocToOrderUI(DocumentContent reservedDocument, RepairProdOrderUIModel repairProdOrderUIModel) {
        if (reservedDocument != null) {
            repairProdOrderUIModel.setReservedDocId(reservedDocument.getId());
            repairProdOrderUIModel.setReservedDocName(reservedDocument.getName());
        }
    }

    public void convRepairProdOrderToUI(RepairProdOrder repairProdOrder, RepairProdOrderUIModel repairProdOrderUIModel) throws ServiceEntityInstallationException {
        convRepairProdOrderToUI(repairProdOrder, repairProdOrderUIModel, null);
    }

    public void convRepairProdOrderToUI(RepairProdOrder repairProdOrder, RepairProdOrderUIModel repairProdOrderUIModel, LogonInfo logonInfo) throws ServiceEntityInstallationException {
        productionOrderManager.convProductionOrderToUI(repairProdOrder, repairProdOrderUIModel, logonInfo);
    }

    public void convMaterialStockKeepUnitToUI(MaterialStockKeepUnit materialStockKeepUnit, RepairProdOrderUIModel repairProdOrderUIModel) {
        convMaterialStockKeepUnitToUI(materialStockKeepUnit, repairProdOrderUIModel, null);
    }

    public void convMaterialStockKeepUnitToUI(MaterialStockKeepUnit materialStockKeepUnit, RepairProdOrderUIModel repairProdOrderUIModel, LogonInfo logonInfo) {
        productionOrderManager.convMaterialStockKeepUnitToUI(materialStockKeepUnit, repairProdOrderUIModel, logonInfo);
    }

    public void convBillOfMaterialOrderToUI(BillOfMaterialOrder billOfMaterialOrder, RepairProdOrderUIModel repairProdOrderUIModel) {
        if (billOfMaterialOrder != null) {
            repairProdOrderUIModel.setRefBillOfMaterialId(billOfMaterialOrder.getId());
            repairProdOrderUIModel.setRefBillOfMaterialNodeName(BillOfMaterialOrder.NODENAME);
        }
    }

    @Override
    public ServiceDocumentExtendUIModel convToDocumentExtendUIModel(ServiceEntityNode seNode, LogonInfo logonInfo) {
        if (seNode == null) {
            return null;
        }
        if (RepairProdOrderItem.NODENAME.equals(seNode.getNodeName())) {
            return repairProdOrderItemManager.convToDocumentExtendUIModel(seNode, logonInfo);
        }
        if (RepairProdItemReqProposal.NODENAME.equals(seNode.getNodeName())) {
            return repairProdOrderItemManager.convToDocumentExtendUIModel(seNode, logonInfo);
        }
        if (RepairProdTargetMatItem.NODENAME.equals(seNode.getNodeName())) {
            return repairProdTargetMatItemManager.convToDocumentExtendUIModel(seNode, logonInfo);
        }
        return null;
    }

    @Override
    public String getAuthorizationResource() {
        return IServiceModelConstants.RepairProdOrder;
    }

    @Override
    public ServiceSearchProxy getSearchProxy() {
        return repairProdOrderSearchProxy;
    }

    public boolean checkBlockExecutionByDocflow(int actionCode, String uuid, ServiceJSONRequest serviceJSONRequest, SerialLogonInfo serialLogonInfo) {
        if (actionCode == RepairProdOrderActionNode.DOC_ACTION_APPROVE) {
            return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER, uuid, serviceJSONRequest, serialLogonInfo, actionCode);
        }
        if (actionCode == RepairProdOrderActionNode.DOC_ACTION_REJECT_APPROVE) {
            return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER, uuid, serviceJSONRequest, serialLogonInfo, actionCode);
        }
        if (actionCode == RepairProdOrderActionNode.DOC_ACTION_REVOKE_SUBMIT) {
            serviceFlowRuntimeEngine.clearInvolveProcessIns(IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER, uuid);
            return true;
        }
        return true;
    }

    public void submitFlow(RepairProdOrderServiceUIModel repairProdOrderServiceUIModel, SerialLogonInfo serialLogonInfo) throws ServiceFlowRuntimeException {
        String uuid = repairProdOrderServiceUIModel.getRepairProdOrderUIModel().getUuid();
        ServiceFlowRuntimeEngine.ServiceFlowInputPara serviceFlowInputPara = new ServiceFlowRuntimeEngine.ServiceFlowInputPara(repairProdOrderServiceUIModel, IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER, uuid, RepairProdOrderActionNode.DOC_ACTION_APPROVE, serialLogonInfo);
        serviceFlowRuntimeEngine.submitFlow(serviceFlowInputPara);
    }

    @Override
    public void exeFlowActionEnd(int documentType, String uuid, int actionCode, ServiceJSONRequest serviceJSONRequest, SerialLogonInfo serialLogonInfo) {
        try {
            RepairProdOrder repairProdOrder = (RepairProdOrder) getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, RepairProdOrder.NODENAME, serialLogonInfo.getClient(), null);
            RepairProdOrderServiceModel repairProdOrderServiceModel = (RepairProdOrderServiceModel) loadServiceModule(RepairProdOrderServiceModel.class, repairProdOrder, repairProdOrderServiceUIModelExtension);
            repairProdOrderServiceModel.setServiceJSONRequest(serviceJSONRequest);
            LogonInfo logonInfo = logonInfoManager.genLogonInfo(serialLogonInfo, false);
            if (actionCode == SystemDefDocActionCodeProxy.DOC_ACTION_APPROVE) {
                try {
                    this.approveOrder(repairProdOrderServiceModel, true, logonInfo);
                } catch (BillOfMaterialException | MaterialException | ProductionOrderException |
                         SearchConfigureException | LogonInfoException | AuthorizationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new ServiceFlowRuntimeException(ServiceFlowRuntimeException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                } catch (NodeNotFoundException | ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    throw new ServiceFlowRuntimeException(ServiceFlowRuntimeException.PARA_SYSTEM_ERROR, e.getMessage());
                }
            }
            if (actionCode == SystemDefDocActionCodeProxy.DOC_ACTION_REJECT_APPROVE) {
                this.rejectApproveService(repairProdOrderServiceModel, serialLogonInfo.getRefUserUUID(), serialLogonInfo.getHomeOrganizationUUID());
            }
        } catch (ServiceEntityConfigureException | ServiceModuleProxyException e) {
            logger.error("Failed during repair production order processing", e);
        }
    }

}
