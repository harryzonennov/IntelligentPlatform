package com.company.IntelligentPlatform.production.service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.production.service.ProdPickingExtendAmountModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryWarehouseItemManager;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.production.dto.*;
import com.company.IntelligentPlatform.production.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.finance.service.FinanceAccountValueProxyException;
import com.company.IntelligentPlatform.finance.service.SystemResourceException;
import com.company.IntelligentPlatform.finance.service.SystemResourceFinanceAccountProxy;
import com.company.IntelligentPlatform.production.repository.ProductionOrderRepository;
import com.company.IntelligentPlatform.common.dto.MaterialStockKeepUnitSearchModel;
import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.service.MatDecisionValueSettingManager;
import com.company.IntelligentPlatform.common.service.MaterialConfigureTemplateManager;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.logistics.model.StoreAvailableStoreItemRequest;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreItemException;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreItemManager;
import com.company.IntelligentPlatform.common.model.MatDecisionValueSetting;
import com.company.IntelligentPlatform.common.model.Material;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.RegisteredProduct;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.controller.DefFinanceControllerResource;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceFlowRuntimeEngine;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.ServiceJSONRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEMessageResponse;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.ServiceFlowRuntimeException;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityDateHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityDoubleHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import java.time.ZoneId;
import java.time.LocalDateTime;

/**
 * Logic Manager CLASS FOR Service Entity [ProductionOrder]
 *
 * @author
 * @date Sun Jan 03 21:00:34 CST 2016
 * <p>
 * This class is generated automatically by platform automation register
 * tool
 */
@Service
public class ProductionOrderManager extends ServiceEntityManager {

	public static final String METHOD_ConvItemBillOfMaterialItemToUI = "convItemBillOfMaterialItemToUI";

	public static final String METHOD_ConvProdOrderSupplyWarehouseToUI = "convProdOrderSupplyWarehouseToUI";

	public static final String METHOD_ConvUIToProdOrderSupplyWarehouse = "convUIToProdOrderSupplyWarehouse";

	public static final String METHOD_ConvWarehouseToUI = "convWarehouseToUI";

	public static final String METHOD_ConvProductionOrderToUI = "convProductionOrderToUI";

	public static final String METHOD_ConvUIToProductionOrder = "convUIToProductionOrder";

	public static final String METHOD_ConvOutBillOfMaterialOrderToUI = "convOutBillOfMaterialOrderToUI";

	public static final String METHOD_ConvOutBillOfMaterialItemToUI = "convOutBillOfMaterialItemToUI";

	public static final String METHOD_ConvOutMaterialStockKeepUnitToUI = "convOutMaterialStockKeepUnitToUI";

	public static final String METHOD_ConvProdWorkCenterToUI = "convProdWorkCenterToUI";

	public static final String METHOD_ConvProductionPlanToUI = "convProductionPlanToUI";

	public static final String METHOD_ConvProdOrderReportToUI = "convProdOrderReportToUI";

	public static final String METHOD_ConvUIToProdOrderReport = "convUIToProdOrderReport";

	public static final String METHOD_ConvReportByToUI = "convReportByToUI";

	public static final String METHOD_ConvReservedDocToOrderUI = "convReservedDocToOrderUI";

	public static final String METHOD_ConvApproveByToUI = "convApproveByToUI";

	public static final String METHOD_ConvCountApproveByToUI = "convCountApproveByToUI";

	private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

	private Map<String, Map<Integer, String>> doneStatusMapLan = new HashMap<>();

	private Map<String, Map<Integer, String>> categoryMapLan = new HashMap<>();

	private Map<String, Map<Integer, String>> orderTypeMapLan = new HashMap<>();

	private Map<String, Map<Integer, String>> reportStatusMapLan = new HashMap<>();

	private Map<String, Map<Integer, String>> reportCategoryMapLan = new HashMap<>();

	private Map<String, ProductionOrder> productionOrderMap = new HashMap<>();
    @PersistenceContext
    private EntityManager entityManager;


	@Autowired
	protected ProductionOrderRepository productionOrderDAO;

	@Autowired
	protected StandardPriorityProxy standardPriorityProxy;

	@Autowired
	protected ProductionPlanManager productionPlanManager;

	@Autowired
	protected ProdOrderTargetMatItemManager prodOrderTargetMatItemManager;

	@Autowired
	protected ProdPickingRefMaterialItemManager prodPickingRefMaterialItemManager;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected WarehouseStoreManager warehouseStoreManager;

	// TODO-LEGACY: @Autowired

	// TODO-LEGACY: 	protected ProductionPackageProxy productionPackageProxy;

	@Autowired
	protected SerialNumberSettingManager serialNumberSettingManager;

	@Autowired
	protected ServiceDefaultIdGenerateHelper serviceDefaultIdGenerateHelper;

	@Autowired
	protected WarehouseStoreItemManager warehouseStoreItemManager;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	@Autowired
	protected ProductionOrderItemManager productionOrderItemManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected MaterialConfigureTemplateManager materialConfigureTemplateManager;

	@Autowired
	protected MatDecisionValueSettingManager matDecisionValueSettingManager;

	@Autowired
	protected ProductionOrderConfigureProxy productionOrderConfigureProxy;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected ProductionOrderIdHelper productionOrderIdHelper;

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
	protected ProductionOrderTimeComsumeProxy productionOrderTimeComsumeProxy;

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
	protected ProductionOrderItemServiceUIModelExtension productionOrderItemServiceUIModelExtension;

	@Autowired
	protected ProductionOrderSearchProxy productionOrderSearchProxy;

	@Autowired
	protected OutboundDeliveryWarehouseItemManager outboundDeliveryWarehouseItemManager;

	@Autowired
	protected ServiceFlowRuntimeEngine serviceFlowRuntimeEngine;

	@Autowired
	protected ProductionOrderServiceUIModelExtension productionOrderServiceUIModelExtension;

	@Autowired
	protected ProductionOrderActionExecutionProxy productionOrderActionExecutionProxy;

	@Autowired
	protected DocActionNodeProxy docActionNodeProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	final Logger logger = LoggerFactory.getLogger(ProductionOrderManager.class);
    @Autowired
    private LogonInfoManager logonInfoManager;

	public List<PageHeaderModel> getPageHeaderModelList(ProductionOrder productionOrder, String client)
			throws ServiceEntityConfigureException {
		List<PageHeaderModel> resultList = new ArrayList<>();
		if (productionOrder != null) {
			PageHeaderModel itemHeaderModel = getPageHeaderModel(productionOrder, 0);
			if (itemHeaderModel != null) {
				resultList.add(itemHeaderModel);
			}
		}
		return resultList;
	}

	public PageHeaderModel getPageHeaderModel(ProductionOrder productionOrder, int index) throws ServiceEntityConfigureException {
		PageHeaderModel pageHeaderModel = new PageHeaderModel();
		pageHeaderModel.setPageTitle("productionOrderTitle");
		pageHeaderModel.setHeaderName(productionOrder.getId());
		pageHeaderModel.setNodeInstId(ProductionOrder.SENAME);
		pageHeaderModel.setUuid(productionOrder.getUuid());
		pageHeaderModel.setIndex(index);
		return pageHeaderModel;
	}

	/**
	 * Get all relative picking material item list
	 * @param baseUUID
	 * @param client
	 * @return
	 */
	public List<ServiceEntityNode> getAllRelativePickingMatItemList(String baseUUID, String client) throws ServiceEntityConfigureException {
		List<ServiceEntityNode> productionOrderItemList = getEntityNodeListByKey(baseUUID, IServiceEntityNodeFieldConstant.ROOTNODEUUID, ProductionOrderItem.NODENAME, client,
						null);
		if (ServiceCollectionsHelper.checkNullList(productionOrderItemList)) {
			return null;
		}
		List<String> itemUUIDList = new ArrayList<>();
		productionOrderItemList.forEach(seNode -> {
			ServiceCollectionsHelper.mergeToList(itemUUIDList, seNode.getUuid());
		});
		return prodPickingOrderManager
				.getEntityNodeListByMultipleKey(itemUUIDList, ProdPickingRefMaterialItem.FIELD_PRODORDER_ITEMUUID,
						ProdPickingRefMaterialItem.NODENAME, client, null);
	}

	public ProductiveBOMOrderServiceModel generateProductiveBOMData(ProductionOrder productionOrder)
			throws ServiceEntityConfigureException, BillOfMaterialException, MaterialException {
		BillOfMaterialOrder billOfMaterialOrder = billOfMaterialOrderManager
				.getRefBillOfMaterialOrderWrapper(productionOrder.getRefBillOfMaterialUUID(), productionOrder.getClient());
		List<ServiceEntityNode> productiveBOMList = billOfMaterialOrderManager.genProductiveBOMModel(billOfMaterialOrder);
		ProductiveBOMOrder productiveBOMOrder = billOfMaterialOrderManager.genInitProductiveBomOrder(billOfMaterialOrder);
		double ratio = getRatioFromProductionToBOMOrder(productionOrder, billOfMaterialOrder, productiveBOMList);
		double amountInProd = productiveBOMOrder.getAmount() * ratio;
		productiveBOMOrder.setAmountWithLossRate(amountInProd);
		/*
		 * [Step2] Traverse from first BOM layer into the footer layer to
		 * calculate each item required amount
		 */
		List<ServiceEntityNode> firstBomLayerList = productiveBOMOrderManager
				.filterSubBOMItemList(productiveBOMOrder.getUuid(), productiveBOMList);
		List<ProductiveBOMItemServiceModel> productiveBOMItemList = new ArrayList<>();
		for (ServiceEntityNode seNode : firstBomLayerList) {
			/*
			 * [Step2] Calculate the first BOM layer and generate the relative
			 * production　order item
			 */
			ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
			double amount = productiveBOMItem.getAmount() * ratio;
			// Calculate the amount with loss rate
			double amountWithLossRate = amount / (1 - productiveBOMItem.getTheoLossRate() / 100);
			amountWithLossRate = Math.ceil(amountWithLossRate);
			// Update the amount of Productive BOM item
			productiveBOMItem.setAmountWithLossRate(amountWithLossRate);
			ProductiveBOMItemServiceModel productiveBOMItemServiceModel = new ProductiveBOMItemServiceModel();
			productiveBOMItemServiceModel.setProductiveBOMItem(productiveBOMItem);
			List<ProductiveBOMItemServiceModel> subProductiveBOMItemList = genSubProdBOMItemSerModel(productiveBOMItem,
					productiveBOMList);
			if (!ServiceCollectionsHelper.checkNullList(subProductiveBOMItemList)) {
				productiveBOMItemServiceModel.setSubProductiveBOMItemList(subProductiveBOMItemList);
			}
			productiveBOMItemList.add(productiveBOMItemServiceModel);
		}
		ProductiveBOMOrderServiceModel productiveBOMOrderServiceModel = new ProductiveBOMOrderServiceModel();
		productiveBOMOrderServiceModel.setProductiveBOMOrder(productiveBOMOrder);
		productiveBOMOrderServiceModel.setProductiveBOMItemList(productiveBOMItemList);
		return productiveBOMOrderServiceModel;
	}

	@Override
	public ServiceEntityNode getEntityNodeByKey(Object keyValue, String keyName, String nodeName, String client,
			List<ServiceEntityNode> rawSEList) throws ServiceEntityConfigureException {
		if (IServiceEntityNodeFieldConstant.UUID.equals(keyName) && ProductionOrder.NODENAME.equals(nodeName)) {
			if (this.productionOrderMap.containsKey(keyValue)) {
				return this.productionOrderMap.get(keyValue);
			}
			// In case not find, then find from persistence
			ProductionOrder productionOrder = (ProductionOrder) super
					.getEntityNodeByKey(keyValue, keyName, nodeName, client, rawSEList);
			if (productionOrder != null) {
				this.productionOrderMap.put(keyName, productionOrder);
			}
			return productionOrder;
		} else {
			return super.getEntityNodeByKey(keyValue, keyName, nodeName, client, rawSEList);
		}
	}

	@Override
	public void updateBuffer(ServiceEntityNode serviceEntityNode) {
		if (serviceEntityNode != null && LogonUser.SENAME.equals(serviceEntityNode.getServiceEntityName())) {
			ProductionOrder productionOrder = (ProductionOrder) serviceEntityNode;
			this.productionOrderMap.put(productionOrder.getUuid(), productionOrder);
		}
	}

	/**
	 * Entrance method to post update production order service model
	 *
	 * @param productionOrderServiceModel
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public void postUpdateProductionOrder(ProductionOrderServiceModel productionOrderServiceModel, String logonUserUUID,
			String organizationUUID) throws ServiceEntityConfigureException, MaterialException, ServiceEntityInstallationException, ServiceModuleProxyException, ServiceComExecuteException, DocActionException {
		/*
		 * [Step1] Update Info for Production Order Item
		 */
		ProductionOrder productionOrder = productionOrderServiceModel.getProductionOrder();
		List<String> warehouseUUIDList = getWarehouseUUIDList(productionOrder.getUuid(), productionOrder.getClient());
		List<ProductionOrderItemServiceModel> productionOrderItemList = productionOrderServiceModel.getProductionOrderItemList();
		if (ServiceCollectionsHelper.checkNullList(productionOrderItemList)) {
			return;
		}
		double grossCost = 0;
		double grossCostLossRate = 0;
		double grossCostActual = 0;
		List<ProdPickingExtendAmountModel> allPickingExtendAmountModelList = new ArrayList<>();
		for (ProductionOrderItemServiceModel productionOrderItemServiceModel : productionOrderItemList) {
			// Core Logic to update order item key information
			List<ProdPickingExtendAmountModel> pickingExtendAmountModelList = productionOrderItemManager
					.refreshOrderItemFromPickingItem(productionOrderItemServiceModel, warehouseUUIDList);
			if(!ServiceCollectionsHelper.checkNullList(pickingExtendAmountModelList)){
				allPickingExtendAmountModelList.addAll(pickingExtendAmountModelList);
			}
			ProductionOrderItem productionOrderItem = productionOrderItemServiceModel.getProductionOrderItem();
			grossCost += productionOrderItem.getItemPrice();
			grossCostLossRate += productionOrderItem.getItemCostLossRate();
			grossCostActual += productionOrderItem.getItemCostActual();
		}
		productionOrder.setGrossCost(grossCost);
		productionOrder.setGrossCostLossRate(grossCostLossRate);
		productionOrder.setGrossCostActual(grossCostActual);
		/*
		 * [Step2] Update actual amount for each production order item
		 */
		postUpdateProdPickingOrderWrapper(productionOrder.getUuid(), null, logonUserUUID, organizationUUID, productionOrder.getClient());
//		this.setActualAmountProdItem(productionOrderServiceModel.getProductionOrderItemList(), productionOrder, logonUserUUID,
//						organizationUUID);
	}

	/**
	 * [Internal] Wrapper method to get all the picking ref order model and update
	 */
	private void postUpdateProdPickingOrderWrapper(String orderUUID,
			List<ProdPickingExtendAmountModel> allPickingExtendAmountModelList,
			String logonUserUUID, String organizationUUID, String client)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, MaterialException, DocActionException {
		ProdPickingRefOrderItem prodPickingRefOrderItem = (ProdPickingRefOrderItem) prodPickingOrderManager
				.getEntityNodeByKey(orderUUID, IServiceEntityNodeFieldConstant.UUID, ProdPickingRefOrderItem.NODENAME, client, null);
		if (prodPickingRefOrderItem != null) {
			ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel = (ProdPickingRefOrderItemServiceModel) prodPickingOrderManager
					.loadServiceModule(ProdPickingRefOrderItemServiceModel.class, prodPickingRefOrderItem);
			prodPickingRefOrderltemManager.refreshPickingRefOrderItem(prodPickingRefOrderItemServiceModel, allPickingExtendAmountModelList);
			prodPickingOrderManager
					.updateServiceModule(ProdPickingRefOrderItemServiceModel.class, prodPickingRefOrderItemServiceModel, logonUserUUID,
							organizationUUID);
		}
	}

	public ProductionOrderServiceModel newProductionOrderServiceModel(ProductionOrderInitModel productionOrderInitModel,
			int category, LogonUser logonUser, String organizationUUID)
			throws ServiceEntityConfigureException, MaterialException, ParseException, ServiceModuleProxyException {
		/*
		 * [Step1] new module of production plan from init model
		 */
		ProductionOrder productionOrder = (ProductionOrder) newRootEntityNode(logonUser.getClient());
		productionOrder.setCategory(category);
		productionOrder.setRefMaterialSKUUUID(productionOrderInitModel.getRefMaterialSKUUUID());
		productionOrder.setRefBillOfMaterialUUID(productionOrderInitModel.getRefBillOfMaterialUUID());
		productionOrder.setProductionBatchNumber(productionOrderInitModel.getProductionBatchNumber());
		productionOrder.setAmount(productionOrderInitModel.getAmount());
		productionOrder.setRefUnitUUID(productionOrderInitModel.getRefUnitUUID());
		MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
				.getEntityNodeByKey(productionOrderInitModel.getRefMaterialSKUUUID(), IServiceEntityNodeFieldConstant.UUID,
						MaterialStockKeepUnit.NODENAME, logonUser.getClient(), null);
		ProductionOrderServiceModel productionOrderServiceModel = new ProductionOrderServiceModel();
		productionOrderServiceModel.setProductionOrder(productionOrder);
		if (materialStockKeepUnit != null) {
			productionOrder.setName(materialStockKeepUnit.getName());
			List<ServiceEntityNode> rawWarehouseDecisonList = matDecisionValueSettingManager
					.getDecisionValueList(materialStockKeepUnit, MatDecisionValueSettingManager.VAUSAGE_RAWMAT_WAREHOUSE);
			if (!ServiceCollectionsHelper.checkNullList(rawWarehouseDecisonList)) {
				List<ServiceEntityNode> prodOrderSupplyWarehouseList = new ArrayList<>();
				for (ServiceEntityNode seNode : rawWarehouseDecisonList) {
					MatDecisionValueSetting matDecisionValueSetting = (MatDecisionValueSetting) seNode;
					ProdOrderSupplyWarehouse prodOrderSupplyWarehouse = (ProdOrderSupplyWarehouse) newEntityNode(productionOrder,
							ProdOrderSupplyWarehouse.NODENAME);
					prodOrderSupplyWarehouse.setRefUUID(matDecisionValueSetting.getRawValue());
					prodOrderSupplyWarehouse.setRefNodeName(Warehouse.NODENAME);
					prodOrderSupplyWarehouse.setRefSEName(Warehouse.SENAME);
					prodOrderSupplyWarehouseList.add(prodOrderSupplyWarehouse);
				}
				productionOrderServiceModel.setProdOrderSupplyWarehouseList(prodOrderSupplyWarehouseList);
			}
		}
		if (!ServiceEntityStringHelper.checkNullString(productionOrderInitModel.getPlanStartTime())) {
			productionOrder
					.setPlanStartTime(DefaultDateFormatConstant.DATE_FORMAT.parse(productionOrderInitModel.getPlanStartTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		}
		if (!ServiceEntityStringHelper.checkNullString(productionOrderInitModel.getPlanEndTime())) {
			productionOrder.setPlanEndTime(DefaultDateFormatConstant.DATE_FORMAT.parse(productionOrderInitModel.getPlanEndTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		}
		if (!ServiceEntityStringHelper.checkNullString(productionOrderInitModel.getPlanStartPrepareDate())) {
			productionOrder.setPlanStartPrepareDate(
					DefaultDateFormatConstant.DATE_FORMAT.parse(productionOrderInitModel.getPlanStartPrepareDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		}
		updateServiceModule(ProductionOrderServiceModel.class, productionOrderServiceModel, logonUser.getUuid(), organizationUUID);
		return productionOrderServiceModel;
	}

	private List<ProductiveBOMItemServiceModel> genSubProdBOMItemSerModel(ProductiveBOMItem parentProdBOMItem,
			List<ServiceEntityNode> productiveBOMList) {
		List<ServiceEntityNode> subBOMItemList = productiveBOMOrderManager
				.filterSubBOMItemList(parentProdBOMItem.getUuid(), productiveBOMList);
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
			List<ProductiveBOMItemServiceModel> subProductiveBOMItemList = genSubProdBOMItemSerModel(productiveBOMItem,
					productiveBOMList);
			if (!ServiceCollectionsHelper.checkNullList(subProductiveBOMItemList)) {
				productiveBOMItemServiceModel.setSubProductiveBOMItemList(subProductiveBOMItemList);
			}
			result.add(productiveBOMItemServiceModel);
		}
		return result;
	}

	public void checkSubmit(ProductionOrderServiceModel productionOrderServiceModel) throws ProductionOrderException {
		ProductionOrder productionOrder = productionOrderServiceModel.getProductionOrder();
		if(productionOrder.getAmount() <= 0){
			throw new ProductionOrderException(ProductionOrderException.PARA_ZERO_REQUEST, productionOrder.getId());
		}
	}
	/**
	 * Core Logic to approve production order and update to DB
	 *
	 * @param productionOrderServiceModel
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceEntityInstallationException
	 * @throws NodeNotFoundException
	 * @throws SearchConfigureException
	 * @throws ProductionOrderException
	 * @throws MaterialException
	 * @throws BillOfMaterialException
	 * @throws ServiceModuleProxyException
	 */
	public synchronized void approveOrder(ProductionOrderServiceModel productionOrderServiceModel,
										   boolean refreshOrderFlag,
			LogonInfo logonInfo)
            throws ServiceEntityConfigureException, BillOfMaterialException, MaterialException, ProductionOrderException,
            SearchConfigureException, NodeNotFoundException, ServiceEntityInstallationException, ServiceModuleProxyException, AuthorizationException, LogonInfoException {
		ProductionOrder productionOrder = productionOrderServiceModel.getProductionOrder();
		boolean localRefreshFlag = refreshOrderFlag;
		if (!localRefreshFlag) {
			// In case no production plan exist, then have to generate resources
			// in mandatory way
			if (ServiceCollectionsHelper.checkNullList(productionOrderServiceModel.getProductionOrderItemList())) {
				localRefreshFlag = true;
			}
		}
		if (localRefreshFlag) {
			/*
			 * [Step 2] Delete the old resources and generate new production item service list resources
			 */
			deleteOrderSubResource(productionOrder.getUuid(), logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID(), productionOrder.getClient());
			updateRecentBOMOrderToProductionOrder(productionOrder);
			// In case need to refresh order resource
			List<ServiceEntityNode> rawProdOrderItemList = generateProductItemListEntry(productionOrder,  logonInfo, true);
			productionOrderServiceModel = (ProductionOrderServiceModel) this
					.convertToProductionOrderServiceModel(productionOrder, rawProdOrderItemList);
			updateServiceModuleWithDelete(ProductionOrderServiceModel.class, productionOrderServiceModel, logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
		} else {
			updateSENode(productionOrder, logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
		}
		/*
		 * [Step3] Batch release production order item list to picking order
		 */
		releaseProductionOrderToPickingBatch(productionOrderServiceModel, logonInfo);

	}

	/**
	 * Core Logic start inproduction
	 *
	 * @param productionOrderServiceModel
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceEntityInstallationException
	 * @throws NodeNotFoundException
	 * @throws SearchConfigureException
	 */
	// TODO to be replaced by framework
	public void inProduction(ProductionOrderServiceModel productionOrderServiceModel, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, SearchConfigureException, NodeNotFoundException,
            ServiceEntityInstallationException, ServiceModuleProxyException, AuthorizationException, LogonInfoException {
		/*
		 * [Step1] Set status of production order itself.
		 */
		ProductionOrder productionOrder = productionOrderServiceModel.getProductionOrder();
		/*
		 * [Step2] Try to find all the target item, and set into production
		 */
		List<ServiceEntityNode> prodOrderTargetMatItemList = this
				.getEntityNodeListByKey(productionOrder.getUuid(), IServiceEntityNodeFieldConstant.ROOTNODEUUID,
						ProdOrderTargetMatItem.NODENAME, productionOrder.getClient(), null);
		if (!ServiceCollectionsHelper.checkNullList(prodOrderTargetMatItemList)) {
			prodOrderTargetMatItemManager.setInProcessStatusBatch(prodOrderTargetMatItemList, serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
		}

		/*
		 * [Step3] Try to find the reference production plan, in case this order
		 * is its main production order
		 */
		ProductionPlanSearchModel productionPlanSearchModel = new ProductionPlanSearchModel();
		productionPlanSearchModel.setRefMainProdOrderUUID(productionOrder.getUuid());
		LogonInfo logonInfo = logonInfoManager.genLogonInfo(serialLogonInfo, false);
		SearchContextBuilder searchContextBuilder = SearchContextBuilder.genBuilder(logonInfo).searchModel(productionPlanSearchModel);

		List<ServiceEntityNode> rawPlanList = productionPlanManager.getSearchProxy()
				.searchDocList(searchContextBuilder.build()).getResultList();
		if (!ServiceCollectionsHelper.checkNullList(rawPlanList)) {
			for (ServiceEntityNode serNode : rawPlanList) {
				ProductionPlan productionPlan = (ProductionPlan) serNode;
				productionPlan.setActualStartTime(LocalDateTime.now());
			}
		}
		productionPlanManager.updateSENodeList(rawPlanList, serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
	}

	/**
	 * Core Logic to pre-check the validate if could set to complete
	 *
	 * @param productionOrderServiceModel
	 * @throws ServiceModuleProxyException
	 * @throws ServiceEntityConfigureException
	 * @throws ProductionOrderException
	 */
	public List<SimpleSEMessageResponse> preCheckSetComplete(
			ProductionOrderServiceModel productionOrderServiceModel, LogonInfo logonInfo)
			throws ProductionOrderException, ServiceModuleProxyException, ServiceEntityConfigureException {
		if (ServiceCollectionsHelper.checkNullList(productionOrderServiceModel.getProdOrderTargetMatItemList())) {
			throw new ProductionOrderException(ProductionOrderException.PARA_NO_TARGETITEM);
		}
		List<ProdOrderTargetMatItemServiceModel> prodOrderTargetMatItemList = productionOrderServiceModel.getProdOrderTargetMatItemList();
		List<SimpleSEMessageResponse> resultList = new ArrayList<>();
		if (!ServiceCollectionsHelper.checkNullList(prodOrderTargetMatItemList)) {
			for (ProdOrderTargetMatItemServiceModel prodOrderTargetMatItemServiceModel : prodOrderTargetMatItemList) {
				ProdOrderTargetMatItem prodOrderTargetMatItem = prodOrderTargetMatItemServiceModel.getProdOrderTargetMatItem();
				SimpleSEMessageResponse simpleSEMessageResponse = new SimpleSEMessageResponse();
				simpleSEMessageResponse
						.setMessageLevel(SimpleSEMessageResponse.MESSAGELEVEL_ERROR);
				simpleSEMessageResponse
						.setRefException(new ProductionOrderException(
								ProductionOrderException.PARA_NODONE_TAEGET));
				simpleSEMessageResponse
						.setErrorCode(ProductionOrderException.PARA_NODONE_TAEGET);
				String indentifier = prodOrderTargetMatItemManager.getTargetMatItemIndentifier(prodOrderTargetMatItem, logonInfo);
				simpleSEMessageResponse
						.setErrorParas(new String[] { indentifier });
				resultList.add(simpleSEMessageResponse);
			}
		}
		return resultList;
	}

	/**
	 * Core Logic to set production order status:[Complete]
	 *
	 * @param productionOrderServiceModel
	 * @param logonInfo
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceEntityInstallationException
	 * @throws NodeNotFoundException
	 * @throws SearchConfigureException
	 */
	public void setOrderComplete(ProductionOrderServiceModel productionOrderServiceModel, LogonInfo logonInfo)
			throws ServiceEntityConfigureException, SearchConfigureException, NodeNotFoundException,
			ServiceEntityInstallationException, ProductionOrderException, ServiceModuleProxyException {
		/*
		 * [Step1] Set status of production order itself.
		 */
		String logonUserUUID = logonInfo.getRefUserUUID();
		String organizationUUID = logonInfo.getResOrgUUID();
		if (ServiceCollectionsHelper.checkNullList(productionOrderServiceModel.getProdOrderTargetMatItemList())) {
			throw new ProductionOrderException(ProductionOrderException.PARA_NO_TARGETITEM);
		}
		List<ServiceEntityNode> allProdOrderTargetMatItemList = new ArrayList<>();
		productionOrderServiceModel.getProdOrderTargetMatItemList().forEach(productionOrderMaItemServiceModel -> {
			allProdOrderTargetMatItemList.add(productionOrderMaItemServiceModel.getProdOrderTargetMatItem());
		});
		List<SimpleSEMessageResponse> rawMessageList = preCheckSetComplete(productionOrderServiceModel, logonInfo);
		List<SimpleSEMessageResponse> errorMessageList = ServiceMessageResponseHelper
				.filerSEMessageResponseByLevel(
						SimpleSEMessageResponse.MESSAGELEVEL_ERROR,
						rawMessageList);
		if(!ServiceCollectionsHelper.checkNullList(errorMessageList)){
			throw new ProductionOrderException(ProductionOrderException.PARA_NODONE_TAEGET, errorMessageList);
		}
		/*
		 * [Step2] Set to complete core
		 */
		ProductionOrder productionOrder = productionOrderServiceModel.getProductionOrder();
		setOrderCompleteCore(productionOrder, logonUserUUID, organizationUUID);
		ProductionPlanSearchModel productionPlanSearchModel = new ProductionPlanSearchModel();
		productionPlanSearchModel.setRefMainProdOrderUUID(productionOrder.getUuid());
		/*
		 * [Step3] Try to find the reference production plan, in case this order
		 * is its main production order
		 */
//		List<ServiceEntityNode> rawPlanList = productionPlanManager
//				.searchInternal(productionPlanSearchModel, productionOrder.getClient());
//		if (!ServiceCollectionsHelper.checkNullList(rawPlanList)) {
//			for (ServiceEntityNode serNode : rawPlanList) {
//				ProductionPlan productionPlan = (ProductionPlan) serNode;
//				productionPlanManager.setPlanComplete(productionPlan, logonUserUUID, organizationUUID);
//			}
//		}
	}

	@Deprecated
	public void setOrderCompleteCore(ProductionOrder productionOrder, String logonUserUUID, String organizationUUID){
		productionOrder.setStatus(ProductionOrder.STATUS_FINISHED);
		productionOrder.setActualEndTime(LocalDateTime.now());
		this.updateSENode(productionOrder, logonUserUUID, organizationUUID);
	}

	public String genSerialNumber(MatDecisionValueSetting matDecisionSerialFormat, ProductionOrder productionOrder,
			MaterialStockKeepUnit templateMaterialSKU, int offset) throws ServiceEntityConfigureException {
		if (matDecisionSerialFormat != null) {
			SerialNumberSetting serialNumberSetting = (SerialNumberSetting) serialNumberSettingManager
					.getEntityNodeByKey(matDecisionSerialFormat.getRawValue(), IServiceEntityNodeFieldConstant.UUID,
							SerialNumberSetting.NODENAME, productionOrder.getClient(), null, true);
			String serialNumberId = ServiceEntityStringHelper.EMPTYSTRING;
			if (serialNumberSetting != null) {
				try {
					int lastIndex = serialNumberSetting.getCoreStartNumber();
					if (serialNumberSetting.getTimeCodeFormat() == DefaultDateFormatConstant.FORT_NONE) {
						lastIndex = serviceDefaultIdGenerateHelper
								.getLastIndex(RegisteredProduct.SENAME, serialNumberSetting.getCoreNumberLength(),
										serialNumberSetting.getPrefixCode1(), RegisteredProduct.FEILD_SERIALID,
										productionOrder.getClient());
					} else {
						lastIndex = serviceDefaultIdGenerateHelper
								.getLastIndexToday(productionOrder.getClient(), RegisteredProduct.SENAME,
										RegisteredProduct.FEILD_SERIALID, serialNumberSetting.getCoreNumberLength());
					}
					lastIndex += offset;
					List<ServiceEntityNode> rawModelList = ServiceCollectionsHelper.asList(productionOrder, templateMaterialSKU);
					serialNumberId = serviceDefaultIdGenerateHelper.genSerialNumberIdCore(lastIndex, serialNumberSetting, rawModelList);
					return serialNumberId;
				} catch (SearchConfigureException | ServiceEntityInstallationException e) {
					// do nothing
				}
			}
		}
		return null;
	}

	public List<ServiceEntityNode> convertProdItemProposalList(
			List<ProdOrderItemReqProposalServiceModel> rawProdOrderItemReqProposalList) {
		List<ServiceEntityNode> prodOrderItemReqProposalList = new ArrayList<>();
		if (ServiceCollectionsHelper.checkNullList(rawProdOrderItemReqProposalList)) {
			return null;
		}
		for (ProdOrderItemReqProposalServiceModel prodOrderItemReqProposalServiceModel : rawProdOrderItemReqProposalList) {
			prodOrderItemReqProposalList.add(prodOrderItemReqProposalServiceModel.getProdOrderItemReqProposal());
		}
		return prodOrderItemReqProposalList;
	}

	/**
	 * Batch release production order's sub proposal to picking order resource
	 *
	 * @param productionOrderServiceModel
	 * @throws MaterialException
	 * @throws BillOfMaterialException
	 * @throws ServiceEntityConfigureException
	 */
	public void releaseProductionOrderToPickingBatch(ProductionOrderServiceModel productionOrderServiceModel,
													 LogonInfo logonInfo) throws MaterialException,
			BillOfMaterialException,
ServiceEntityConfigureException {
		/*
		 * [Step1] Batch generate picking order
		 */
		List<String> rootNodeUUIDList = new ArrayList<>();
		for (ProductionOrderItemServiceModel productionOrderItemServiceModel : productionOrderServiceModel
				.getProductionOrderItemList()) {
			ProductionOrderItem productionOrderItem = productionOrderItemServiceModel.getProductionOrderItem();
			StorageCoreUnit requestCoreUnit = new StorageCoreUnit(productionOrderItem.getRefMaterialSKUUUID(),
					productionOrderItem.getRefUnitUUID(), productionOrderItem.getActualAmount());
			try {
				List<ServiceEntityNode> inPlanRefMaterialItemList = prodOrderWithPickingOrderProxy
						.updateRequestToPickingOrderWrapper(requestCoreUnit, productionOrderItem,
								convertProdItemProposalList(productionOrderItemServiceModel.getProdOrderItemReqProposalList()),
								ProdPickingOrder.PROCESSTYPE_INPLAN, false, logonInfo);
				if (!ServiceCollectionsHelper.checkNullList(inPlanRefMaterialItemList)) {
					for (ServiceEntityNode tmpSENode : inPlanRefMaterialItemList) {
						ServiceCollectionsHelper.mergeToList(rootNodeUUIDList, tmpSENode.getRootNodeUUID());
					}
				}
			} catch (SearchConfigureException | ServiceModuleProxyException | ServiceEntityConfigureException |
					NodeNotFoundException | ServiceEntityInstallationException | ServiceComExecuteException e) {
				// log error and continue
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, productionOrderItem.getId()), e);
			} catch (AuthorizationException | LogonInfoException e) {
				// log error and continue
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, productionOrderItem.getId()), e);
            } catch (DocActionException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, productionOrderItem.getId()), e);
            }
        }
		/*
		 * [Step2] Approve this picking Order' resource and generate all
		 * previous documents
		 */
		if (!ServiceCollectionsHelper.checkNullList(rootNodeUUIDList)) {
			for (String rootNodeUUID : rootNodeUUIDList) {
				ProdPickingOrder prodPickingOrder = (ProdPickingOrder) prodPickingOrderManager
						.getEntityNodeByKey(rootNodeUUID, IServiceEntityNodeFieldConstant.UUID, ProdPickingOrder.NODENAME, null);
				ProdPickingOrderServiceModel prodPickingOrderServiceModel;
				try {
					prodPickingOrderServiceModel = (ProdPickingOrderServiceModel) prodPickingOrderManager
							.loadServiceModule(ProdPickingOrderServiceModel.class, prodPickingOrder);
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

	private void updateRecentBOMOrderToProductionOrder(ProductionOrder productionOrder) throws BillOfMaterialException,
ServiceEntityConfigureException {
		BillOfMaterialOrder billOfMaterialOrder =
				billOfMaterialOrderManager.switchToRecentActiveBOMOrder(productionOrder.getRefBillOfMaterialUUID(),
						productionOrder.getClient(), false);
		if(!billOfMaterialOrder.getUuid().equals(productionOrder.getRefBillOfMaterialUUID())){
			// in case need to be updated
			productionOrder.setRefBillOfMaterialUUID(billOfMaterialOrder.getUuid());
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
	public void deleteOrderSubResource(String baseUUID, String logonUserUUID, String organizationUUID, String client)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> rawProdOrderItemList = this
				.getEntityNodeListByKey(baseUUID, IServiceEntityNodeFieldConstant.ROOTNODEUUID, ProductionOrderItem.NODENAME, client,
						null);
		if (!ServiceCollectionsHelper.checkNullList(rawProdOrderItemList)) {
			this.deleteSENode(rawProdOrderItemList, logonUserUUID, organizationUUID);
		}
		List<ServiceEntityNode> rawOrderItemReqProposal = this
				.getEntityNodeListByKey(baseUUID, IServiceEntityNodeFieldConstant.ROOTNODEUUID, ProdOrderItemReqProposal.NODENAME,
						client, null);
		if (!ServiceCollectionsHelper.checkNullList(rawOrderItemReqProposal)) {
			this.deleteSENode(rawOrderItemReqProposal, logonUserUUID, organizationUUID);
		}
		List<ServiceEntityNode> rawOrderTargetMatItemList = this
				.getEntityNodeListByKey(baseUUID, IServiceEntityNodeFieldConstant.ROOTNODEUUID, ProdOrderTargetMatItem.NODENAME,
						client, null);
		if (!ServiceCollectionsHelper.checkNullList(rawOrderTargetMatItemList)) {
			this.deleteSENode(rawOrderTargetMatItemList, logonUserUUID, organizationUUID);
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convProdOrderSupplyWarehouseToUI(ProdOrderSupplyWarehouse prodOrderSupplyWarehouse,
			ProdOrderSupplyWarehouseUIModel prodOrderSupplyWarehouseUIModel) {
		if (prodOrderSupplyWarehouse != null) {
			if (!ServiceEntityStringHelper.checkNullString(prodOrderSupplyWarehouse.getUuid())) {
				prodOrderSupplyWarehouseUIModel.setUuid(prodOrderSupplyWarehouse.getUuid());
			}
			if (!ServiceEntityStringHelper.checkNullString(prodOrderSupplyWarehouse.getParentNodeUUID())) {
				prodOrderSupplyWarehouseUIModel.setParentNodeUUID(prodOrderSupplyWarehouse.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(prodOrderSupplyWarehouse.getRootNodeUUID())) {
				prodOrderSupplyWarehouseUIModel.setRootNodeUUID(prodOrderSupplyWarehouse.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(prodOrderSupplyWarehouse.getClient())) {
				prodOrderSupplyWarehouseUIModel.setClient(prodOrderSupplyWarehouse.getClient());
			}
			prodOrderSupplyWarehouseUIModel.setRefUUID(prodOrderSupplyWarehouse.getRefUUID());
		}
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:prodOrderSupplyWarehouse
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToProdOrderSupplyWarehouse(ProdOrderSupplyWarehouseUIModel prodOrderSupplyWarehouseUIModel,
			ProdOrderSupplyWarehouse rawEntity) {
		if (!ServiceEntityStringHelper.checkNullString(prodOrderSupplyWarehouseUIModel.getUuid())) {
			rawEntity.setUuid(prodOrderSupplyWarehouseUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper.checkNullString(prodOrderSupplyWarehouseUIModel.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(prodOrderSupplyWarehouseUIModel.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(prodOrderSupplyWarehouseUIModel.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(prodOrderSupplyWarehouseUIModel.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(prodOrderSupplyWarehouseUIModel.getClient())) {
			rawEntity.setClient(prodOrderSupplyWarehouseUIModel.getClient());
		}
		rawEntity.setRefUUID(prodOrderSupplyWarehouseUIModel.getRefUUID());
	}

	/**
	 * [Internal method] Convert from UI model to se model:productionOrder
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToProductionOrder(ProductionOrderUIModel productionOrderUIModel, ProductionOrder rawEntity) {
		if (!ServiceEntityStringHelper.checkNullString(productionOrderUIModel.getUuid())) {
			rawEntity.setUuid(productionOrderUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper.checkNullString(productionOrderUIModel.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(productionOrderUIModel.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(productionOrderUIModel.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(productionOrderUIModel.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(productionOrderUIModel.getClient())) {
			rawEntity.setClient(productionOrderUIModel.getClient());
		}
		if (!ServiceEntityStringHelper.checkNullString(productionOrderUIModel.getRefWocUUID())) {
			rawEntity.setRefWocUUID(productionOrderUIModel.getRefWocUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(productionOrderUIModel.getRefPlanUUID())) {
			rawEntity.setRefPlanUUID(productionOrderUIModel.getRefPlanUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(productionOrderUIModel.getPlanStartPrepareDate())) {
			try {
				rawEntity.setPlanStartPrepareDate(
						DefaultDateFormatConstant.DATE_FORMAT.parse(productionOrderUIModel.getPlanStartPrepareDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		if (!ServiceEntityStringHelper.checkNullString(productionOrderUIModel.getPlanEndTime())) {
			try {
				rawEntity.setPlanEndTime(DefaultDateFormatConstant.DATE_FORMAT.parse(productionOrderUIModel.getPlanEndTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		if (productionOrderUIModel.getReservedDocType() != 0) {
			rawEntity.setReservedDocType(productionOrderUIModel.getReservedDocType());
		}
		if (!ServiceEntityStringHelper.checkNullString(productionOrderUIModel.getReservedMatItemUUID())) {
			rawEntity.setReservedMatItemUUID(productionOrderUIModel.getReservedMatItemUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(productionOrderUIModel.getRefBillOfMaterialUUID())) {
			rawEntity.setRefBillOfMaterialUUID(productionOrderUIModel.getRefBillOfMaterialUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(productionOrderUIModel.getApproveBy())) {
			rawEntity.setApproveBy(productionOrderUIModel.getApproveBy());
		}
		if (!ServiceEntityStringHelper.checkNullString(productionOrderUIModel.getApproveTime())) {
			try {
				rawEntity.setApproveTime(DefaultDateFormatConstant.DATE_FORMAT.parse(productionOrderUIModel.getApproveTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		if (!ServiceEntityStringHelper.checkNullString(productionOrderUIModel.getCountApproveBy())) {
			rawEntity.setCountApproveBy(productionOrderUIModel.getCountApproveBy());
		}
		if (!ServiceEntityStringHelper.checkNullString(productionOrderUIModel.getCountApproveTime())) {
			try {
				rawEntity
						.setCountApproveTime(DefaultDateFormatConstant.DATE_FORMAT.parse(productionOrderUIModel.getCountApproveTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setPriorityCode(productionOrderUIModel.getPriorityCode());
		rawEntity.setGenOrderItemMode(productionOrderUIModel.getGenOrderItemMode());
		rawEntity.setRefUnitUUID(productionOrderUIModel.getRefUnitUUID());
		rawEntity.setRefMaterialSKUUUID(productionOrderUIModel.getRefMaterialSKUUUID());
		rawEntity.setName(productionOrderUIModel.getName());
		rawEntity.setUuid(productionOrderUIModel.getUuid());
		rawEntity.setRefBillOfMaterialUUID(productionOrderUIModel.getRefBillOfMaterialUUID());
		rawEntity.setId(productionOrderUIModel.getId());
		rawEntity.setComLeadTime(productionOrderUIModel.getComLeadTime());
		rawEntity.setCategory(productionOrderUIModel.getCategory());
		rawEntity.setProductionBatchNumber(productionOrderUIModel.getProductionBatchNumber());
		rawEntity.setSelfLeadTime(productionOrderUIModel.getSelfLeadTime());
		rawEntity.setNote(productionOrderUIModel.getNote());
		rawEntity.setGrossCost(productionOrderUIModel.getGrossCost());
		rawEntity.setGrossCostActual(productionOrderUIModel.getGrossCostActual());
		rawEntity.setGrossCostLossRate(productionOrderUIModel.getGrossCostLossRate());
		if (!ServiceEntityStringHelper.checkNullString(productionOrderUIModel.getPlanStartTime())) {
			try {
				rawEntity.setPlanStartTime(DefaultDateFormatConstant.DATE_FORMAT.parse(productionOrderUIModel.getPlanStartTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		if (!ServiceEntityStringHelper.checkNullString(productionOrderUIModel.getActualEndTime())) {
			try {
				rawEntity.setActualEndTime(DefaultDateFormatConstant.DATE_FORMAT.parse(productionOrderUIModel.getActualEndTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setClient(productionOrderUIModel.getClient());
		rawEntity.setAmount(productionOrderUIModel.getAmount());
		if (productionOrderUIModel.getDoneStatus() != 0) {
			rawEntity.setDoneStatus(productionOrderUIModel.getDoneStatus());
		}
		if (!ServiceEntityStringHelper.checkNullString(productionOrderUIModel.getActualStartTime())) {
			try {
				rawEntity
						.setActualStartTime(DefaultDateFormatConstant.DATE_FORMAT.parse(productionOrderUIModel.getActualStartTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
	}

	public Map<Integer, String> initPriorityCodeMap(String languageCode) throws ServiceEntityInstallationException {
		return standardPriorityProxy.getPriorityMap(languageCode);
	}

	public Map<Integer, String> initStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode, this.statusMapLan, ProductionOrderUIModel.class, IDocumentNodeFieldConstant.STATUS);
	}

	public Map<Integer, String> initDoneStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return ServiceLanHelper
				.initDefLanguageMapUIModel(languageCode, this.doneStatusMapLan, ProductionOrderUIModel.class, "doneStatus");
	}

	public Map<Integer, String> initItemStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return productionOrderItemManager.initItemStatusMap(languageCode);
	}

	public Map<Integer, String> initCategoryMap(String languageCode) throws ServiceEntityInstallationException {
		return ServiceLanHelper
				.initDefLanguageMapUIModel(languageCode, this.categoryMapLan, ProductionOrderUIModel.class, "category");
	}

	public Map<Integer, String> initOrderTypeMap(String languageCode) throws ServiceEntityInstallationException {
		return ServiceLanHelper
				.initDefLanguageMapUIModel(languageCode, this.orderTypeMapLan, ProductionOrderUIModel.class, "orderType");
	}

	public Map<Integer, String> initDocumentTypeMap(String languageCode) throws ServiceEntityInstallationException {
		return serviceDocumentComProxy.getDocumentTypeMap(false, languageCode);
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, productionOrderDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(productionOrderConfigureProxy);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convOutBillOfMaterialOrderToUI(BillOfMaterialOrder outBillOfMaterialOrder,
			ProductionOrderUIModel productionOrderUIModel) {
		if (outBillOfMaterialOrder != null) {
			productionOrderUIModel.setRefBillOfMaterialId(outBillOfMaterialOrder.getId());
			productionOrderUIModel.setRefBillOfMaterialNodeName(BillOfMaterialOrder.NODENAME);
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convOutBillOfMaterialItemToUI(BillOfMaterialItem outBillOfMaterialItem,
			ProductionOrderUIModel productionOrderUIModel) {
		if (outBillOfMaterialItem != null) {
			productionOrderUIModel.setRefBillOfMaterialId(outBillOfMaterialItem.getId());
			productionOrderUIModel.setRefBillOfMaterialNodeName(BillOfMaterialItem.NODENAME);
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convItemBillOfMaterialItemToUI(BillOfMaterialItem itemBillOfMaterialItem,
			ProductionOrderItemUIModel productionOrderItemUIModel) {
		if (itemBillOfMaterialItem != null) {
			productionOrderItemUIModel.setRefBOMItemUUID(itemBillOfMaterialItem.getUuid());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convWarehouseToUI(Warehouse warehouse, ProdOrderSupplyWarehouseUIModel prodOrderSupplyWarehouseUIModel) {
		if (warehouse != null) {
			prodOrderSupplyWarehouseUIModel.setRefWarehouseId(warehouse.getId());
			prodOrderSupplyWarehouseUIModel.setRefWarehouseName(warehouse.getName());
		}
	}

	public void convOutMaterialStockKeepUnitToUI(MaterialStockKeepUnit outMaterialStockKeepUnit,
			ProductionOrderUIModel productionOrderUIModel) {
		convOutMaterialStockKeepUnitToUI(outMaterialStockKeepUnit, productionOrderUIModel, null);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convOutMaterialStockKeepUnitToUI(MaterialStockKeepUnit outMaterialStockKeepUnit,
			ProductionOrderUIModel productionOrderUIModel, LogonInfo logonInfo) {
		if (outMaterialStockKeepUnit != null) {
			productionOrderUIModel.setRefMaterialSKUId(outMaterialStockKeepUnit.getId());
			productionOrderUIModel.setRefMaterialSKUName(outMaterialStockKeepUnit.getName());
			productionOrderUIModel.setPackageStandard(outMaterialStockKeepUnit.getPackageStandard());
			productionOrderUIModel.setPackageStandard(outMaterialStockKeepUnit.getPackageStandard());
			productionOrderUIModel.setSupplyType(outMaterialStockKeepUnit.getSupplyType());
			if (logonInfo != null) {
				try {
					Map<Integer, String> supplyTypeMap = materialStockKeepUnitManager.initSupplyTypeMap(logonInfo.getLanguageCode());
					productionOrderUIModel.setSupplyTypeValue(supplyTypeMap.get(outMaterialStockKeepUnit.getSupplyType()));
				} catch (ServiceEntityInstallationException e) {
					// log error and continue
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
				}
			}
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convProdWorkCenterToUI(ProdWorkCenter prodWorkCenter, ProductionOrderUIModel productionOrderUIModel) {
		if (prodWorkCenter != null) {
			productionOrderUIModel.setRefWocId(prodWorkCenter.getId());
			productionOrderUIModel.setRefWocName(prodWorkCenter.getName());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convProductionPlanToUI(ProductionPlan productionPlan, ProductionOrderUIModel productionOrderUIModel) {
		if (productionPlan != null) {
			productionOrderUIModel.setRefPlanId(productionPlan.getId());
			productionOrderUIModel.setRefPlanName(productionPlan.getName());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convApproveByToUI(LogonUser logonUser, ProductionOrderUIModel productionOrderUIModel) {
		if (logonUser != null) {
			productionOrderUIModel.setApproveById(logonUser.getId());
			productionOrderUIModel.setApproveByName(logonUser.getName());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convCountApproveByToUI(LogonUser logonUser, ProductionOrderUIModel productionOrderUIModel) {
		if (logonUser != null) {
			productionOrderUIModel.setCountApproveById(logonUser.getId());
			productionOrderUIModel.setCountApproveByName(logonUser.getName());
		}
	}

	public ProductionOrderManager() {
		super.seConfigureProxy = new ProductionOrderConfigureProxy();
	}

	@Override
	public ServiceEntityNode newRootEntityNode(String client) throws ServiceEntityConfigureException {
		ProductionOrder productionOrder = (ProductionOrder) super.newRootEntityNode(client);
		String productionOrderId = productionOrderIdHelper.genDefaultId(client);
		productionOrder.setId(productionOrderId);
		return productionOrder;
	}

	/**
	 * Logic of cancel this document
	 */
	public void cancelDocument(ProductionOrder productionOrder, String logonUserUUID, String organizationUUID)
			throws ServiceEntityConfigureException, SearchConfigureException, NodeNotFoundException,
			ServiceEntityInstallationException {
		/**
		 * [Step1] Set the ProductionOrder status as [cancel]
		 */
		productionOrder.setStatus(DocumentContent.STATUS_CANCELED);
		this.updateSENode(productionOrder, logonUserUUID, organizationUUID);
	}

	public void genFinAccountCore(String resourceID, ProductionOrder productionOrder, LogonUser logonUser,
			Organization organization)
			throws SystemResourceException, FinanceAccountValueProxyException, ServiceEntityConfigureException {
		/*
		 * [Step1] set the status into [in-settle]
		 */
		productionOrder.setStatus(ProductionOrder.STATUS_APPROVED);
		updateSENode(productionOrder, logonUser.getUuid(), organization.getUuid());
		/*
		 * [Step2] update the finance account
		 */
		systemResourceFinanceAccountProxy.updateFinAccByResourceID(productionOrder, resourceID, productionOrder.getUuid(),
				DefFinanceControllerResource.PROCESS_CODE_SAVE, logonUser.getUuid(), organization.getRefFinOrgUUID(),
				organization.getUuid(), logonUser.getClient());
	}

	public List<ServiceEntityNode> generateProdJobOrderArray(ProductionOrder productionOrder, boolean executeFlag)
			throws ServiceEntityConfigureException, BillOfMaterialException, ProductionDataException, MaterialException {
		/*
		 * [Step1] Data prepare: BOM order, route order
		 */
		BillOfMaterialOrder billOfMaterialOrder = billOfMaterialOrderManager
				.getRefBillOfMaterialOrderWrapper(productionOrder.getRefBillOfMaterialUUID(), productionOrder.getClient());
		List<ServiceEntityNode> firstBOMItemList = billOfMaterialOrderManager
				.getFirstLayerSubItemList(billOfMaterialOrder.getUuid(), productionOrder.getClient());
		ProcessRouteOrder processRouteOrder = (ProcessRouteOrder) processRouteOrderManager
				.getEntityNodeByKey(billOfMaterialOrder.getRefRouteOrderUUID(), IServiceEntityNodeFieldConstant.UUID,
						ProcessRouteOrder.NODENAME, productionOrder.getClient(), null);
		if (processRouteOrder == null) {
			throw new ProductionDataException(ProductionDataException.PARA_NO_PROCESSROUTE, billOfMaterialOrder.getId());
		}
		List<ServiceEntityNode> processRouteProcessItemList = processRouteOrderManager
				.getEntityNodeListByKey(processRouteOrder.getUuid(), IServiceEntityNodeFieldConstant.ROOTNODEUUID,
						ProcessRouteProcessItem.NODENAME, productionOrder.getClient(), null);
		/*
		 * [Step2] Calculate the ratio value
		 */
		StorageCoreUnit prodOrderStorageCoreUnit = new StorageCoreUnit();
		prodOrderStorageCoreUnit.setAmount(productionOrder.getAmount());
		prodOrderStorageCoreUnit.setRefMaterialSKUUUID(productionOrder.getRefMaterialSKUUUID());
		prodOrderStorageCoreUnit.setRefUnitUUID(productionOrder.getRefUnitUUID());
		StorageCoreUnit tmpBomStorageUnit = new StorageCoreUnit();
		tmpBomStorageUnit.setRefUnitUUID(billOfMaterialOrder.getRefUnitUUID());
		tmpBomStorageUnit.setAmount(billOfMaterialOrder.getAmount());
		tmpBomStorageUnit.setRefMaterialSKUUUID(billOfMaterialOrder.getRefMaterialSKUUUID());
		double ratio = materialStockKeepUnitManager
				.getStorageUnitRatio(prodOrderStorageCoreUnit, tmpBomStorageUnit, productionOrder.getClient());
		List<ServiceEntityNode> rawProdJobOrderArray = prodJobOrderManager
				.generateProdJobOrderArray(productionOrder, firstBOMItemList, ratio, processRouteOrder, processRouteProcessItemList);
		return rawProdJobOrderArray;
	}

	public ServiceModule convertToProductionOrderServiceModel(ProductionOrder productionOrder,
			List<ServiceEntityNode> rawProdOrderItemList) {

		ProductionOrderServiceModel productionOrderServiceModel = new ProductionOrderServiceModel();
		productionOrderServiceModel.setProductionOrder(productionOrder);
		List<ServiceEntityNode> subProdItemList = ServiceCollectionsHelper
				.filterSENodeByParentUUID(productionOrder.getUuid(), rawProdOrderItemList);
		List<ProductionOrderItemServiceModel> productionOrderItemList = new ArrayList<>();
		if (!ServiceCollectionsHelper.checkNullList(subProdItemList)) {
			for (ServiceEntityNode seNode : subProdItemList) {
				if (IServiceModelConstants.ProductionOrderItem.equals(seNode.getNodeName())) {
					ProductionOrderItem productionOrderItem = (ProductionOrderItem) seNode;
					ProductionOrderItemServiceModel subProductionOrderItemServiceModel = generateSubProdutionOrderItem(
							productionOrderItem, rawProdOrderItemList);
					productionOrderItemList.add(subProductionOrderItemServiceModel);
				}
			}
			productionOrderServiceModel.setProductionOrderItemList(productionOrderItemList);
		}
		return productionOrderServiceModel;
	}

	protected ProductionOrderItemServiceModel generateSubProdutionOrderItem(ProductionOrderItem productionOrderItem,
			List<ServiceEntityNode> rawProdOrderItemList) {
		List<ProductionOrderItemServiceModel> subProductionOrderItemList = new ArrayList<>();
		List<ProdOrderItemReqProposalServiceModel> prodOrderItemReqProposalList = new ArrayList<>();
		List<ServiceEntityNode> prodOrderItemRequirementList = new ArrayList<>();
		List<ServiceEntityNode> subProdItemList = ServiceCollectionsHelper
				.filterSENodeByParentUUID(productionOrderItem.getUuid(), rawProdOrderItemList);
		ProductionOrderItemServiceModel productionOrderItemServiceModel = new ProductionOrderItemServiceModel();
		productionOrderItemServiceModel.setProductionOrderItem(productionOrderItem);
		if (!ServiceCollectionsHelper.checkNullList(subProdItemList)) {
			for (ServiceEntityNode seNode : subProdItemList) {
				if (IServiceModelConstants.ProductionOrderItem.equals(seNode.getNodeName())) {
					// In case this node is 'ProductionOrderItem'
					ProductionOrderItem subProductionOrderItem = (ProductionOrderItem) seNode;
					ProductionOrderItemServiceModel subProductionOrderItemServiceModel = generateSubProdutionOrderItem(
							subProductionOrderItem, rawProdOrderItemList);
					subProductionOrderItemList.add(subProductionOrderItemServiceModel);
				}
				if (ProdOrderItemReqProposal.NODENAME.equals(seNode.getNodeName())) {
					ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) seNode;
					ProdOrderItemReqProposalServiceModel prodOrderItemReqProposalServiceModel = generateSubProdOrderItemReqProposalServiceModel(
							prodOrderItemReqProposal, rawProdOrderItemList);
					prodOrderItemReqProposalList.add(prodOrderItemReqProposalServiceModel);
				}
			}
			productionOrderItemServiceModel.setProdOrderItemReqProposalList(prodOrderItemReqProposalList);
		}
		return productionOrderItemServiceModel;
	}

	protected ProdOrderItemReqProposalServiceModel generateSubProdOrderItemReqProposalServiceModel(
			ProdOrderItemReqProposal prodOrderItemReqProposal, List<ServiceEntityNode> rawProdOrderItemList) {
		List<ProductionOrderItemServiceModel> subProductionOrderItemList = new ArrayList<>();
		List<ServiceEntityNode> subProdItemList = ServiceCollectionsHelper
				.filterSENodeByParentUUID(prodOrderItemReqProposal.getUuid(), rawProdOrderItemList);
		ProdOrderItemReqProposalServiceModel prodOrderItemReqProposalServiceModel = new ProdOrderItemReqProposalServiceModel();
		prodOrderItemReqProposalServiceModel.setProdOrderItemReqProposal(prodOrderItemReqProposal);
		if (!ServiceCollectionsHelper.checkNullList(subProdItemList)) {
			for (ServiceEntityNode seNode : subProdItemList) {
				if (IServiceModelConstants.ProductionOrderItem.equals(seNode.getNodeName())) {
					// In case this node is 'ProductionOrderItem'
					ProductionOrderItem subProductionOrderItem = (ProductionOrderItem) seNode;
					ProductionOrderItemServiceModel subProductionOrderItemServiceModel = generateSubProdutionOrderItem(
							subProductionOrderItem, rawProdOrderItemList);
					subProductionOrderItemList.add(subProductionOrderItemServiceModel);
				}
			}
			prodOrderItemReqProposalServiceModel.setProductionOrderItemList(subProductionOrderItemList);
		}
		return prodOrderItemReqProposalServiceModel;
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
		List<ServiceEntityNode> prodSupplyWarehouseList = getEntityNodeListByKey(baseUUID,
				IServiceEntityNodeFieldConstant.ROOTNODEUUID, ProdOrderSupplyWarehouse.NODENAME, client, null);
		if (ServiceCollectionsHelper.checkNullList(prodSupplyWarehouseList)) {
			return null;
		}
		List<String> warehouseUUIDList = new ArrayList<String>();
		prodSupplyWarehouseList.forEach(seNode -> {
			ProdOrderSupplyWarehouse prodOrderSupplyWarehouse = (ProdOrderSupplyWarehouse) seNode;
			warehouseUUIDList.add(prodOrderSupplyWarehouse.getRefUUID());
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
	public List<ServiceEntityNode> generateProductItemListEntry(ProductionOrder productionOrder, LogonInfo logonInfo, boolean executeFlag)
            throws ServiceEntityConfigureException, BillOfMaterialException, MaterialException, ProductionOrderException,
            SearchConfigureException, NodeNotFoundException, ServiceEntityInstallationException, AuthorizationException, LogonInfoException {
		/*
		 * [Step1] Data prepare: compound productive BOM list
		 */
		BillOfMaterialOrder billOfMaterialOrder = billOfMaterialOrderManager
				.getRefBillOfMaterialOrderWrapper(productionOrder.getRefBillOfMaterialUUID(), productionOrder.getClient());
		List<ServiceEntityNode> productiveBOMList = billOfMaterialOrderManager.genProductiveBOMModel(billOfMaterialOrder);
		if (productiveBOMList == null || productiveBOMList.size() == 0) {
			return null;
		}
		// All Material list
		List<String> materialUUIDList = new ArrayList<>();
		materialUUIDList.add(productionOrder.getRefMaterialSKUUUID());
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
		List<ServiceEntityNode> rawMaterialSKUList = materialStockKeepUnitManager
				.getSearchProxy().searchDocList(searchContext).getResultList();
		/*
		 * [Step2] Data prepare: rawStoreItemList
		 */
		List<ServiceEntityNode> rawStoreItemList = new ArrayList<ServiceEntityNode>();
		List<ServiceEntityNode> prodSupplyWarehouseList = getEntityNodeListByKey(productionOrder.getUuid(),
				IServiceEntityNodeFieldConstant.ROOTNODEUUID, ProdOrderSupplyWarehouse.NODENAME, productionOrder.getClient(), null);
		/*
		 * [Step3] Generate the production list and proposal
		 */
		List<String> warehouseUUIDList = null;
		try {
			warehouseUUIDList = ServiceCollectionsHelper.pluckList(prodSupplyWarehouseList, IReferenceNodeFieldConstant.REFUUID);
			rawStoreItemList = warehouseStoreItemManager.getInStockStoreItemList(warehouseUUIDList, productionOrder.getClient());
			List<ServiceEntityNode> rawStoreItemListBack = ServiceCollectionsHelper.cloneSEList(rawStoreItemList);
			return generateProductItemListProposal(productionOrder, billOfMaterialOrder, prodSupplyWarehouseList, productiveBOMList,
					rawStoreItemList, rawMaterialSKUList);
		} catch (NoSuchFieldException | ServiceComExecuteException e) {
			throw new ProductionOrderException(ProductionOrderException.PARA_SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * Calculate the ratio from Production Order
	 *
	 * @param productionOrder
	 * @param billOfMaterialOrder
	 * @param productiveBOMList
	 * @return
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 */
	private double getRatioFromProductionToBOMOrder(ProductionOrder productionOrder, BillOfMaterialOrder billOfMaterialOrder,
			List<ServiceEntityNode> productiveBOMList) throws MaterialException, ServiceEntityConfigureException {
		StorageCoreUnit prodOrderStorageCoreUnit = new StorageCoreUnit();
		prodOrderStorageCoreUnit.setAmount(productionOrder.getAmount());
		prodOrderStorageCoreUnit.setRefMaterialSKUUUID(productionOrder.getRefMaterialSKUUUID());
		prodOrderStorageCoreUnit.setRefUnitUUID(productionOrder.getRefUnitUUID());
		return getRatioFromProductionToBOMOrder(prodOrderStorageCoreUnit, productionOrder.getRefBillOfMaterialUUID(),
				billOfMaterialOrder, productiveBOMList, productionOrder.getClient());
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
	public double getRatioFromProductionToBOMOrder(StorageCoreUnit requestStorageCoreUnit, String refBOMUUID,
			BillOfMaterialOrder billOfMaterialOrder, List<ServiceEntityNode> productiveBOMList, String client)
			throws MaterialException, ServiceEntityConfigureException {
		StorageCoreUnit bomRequestStorageUnit = new StorageCoreUnit();
		if (billOfMaterialOrder != null && billOfMaterialOrder.getUuid().equals(refBOMUUID)) {
			// In case refBOMUUID matches BOM Order
			bomRequestStorageUnit.setRefUnitUUID(billOfMaterialOrder.getRefUnitUUID());
			bomRequestStorageUnit.setAmount(billOfMaterialOrder.getAmount());
			bomRequestStorageUnit.setRefMaterialSKUUUID(billOfMaterialOrder.getRefMaterialSKUUUID());
		} else {
			// Try to match refBOM UUID to BOM item
			ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) billOfMaterialOrderManager
					.filterBOMItemByUUID(refBOMUUID, productiveBOMList);
			bomRequestStorageUnit.setRefUnitUUID(productiveBOMItem.getRefUnitUUID());
			bomRequestStorageUnit.setAmount(productiveBOMItem.getAmount());
			bomRequestStorageUnit.setRefMaterialSKUUUID(productiveBOMItem.getRefMaterialSKUUUID());
		}
		return materialStockKeepUnitManager.getStorageUnitRatio(requestStorageCoreUnit, bomRequestStorageUnit, client);
	}

	/**
	 * Logic for generate the production item proposal including the sub
	 * requirement for out-bound delivery and production or purchase requirement
	 *
	 * @param productionOrder
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 * @throws ProductionOrderException
	 */
	public List<ServiceEntityNode> generateProductItemListProposal(ProductionOrder productionOrder,
			BillOfMaterialOrder billOfMaterialOrder, List<ServiceEntityNode> prodSupplyWarehouseList,
			List<ServiceEntityNode> productiveBOMList, List<ServiceEntityNode> rawStoreItemList,
			List<ServiceEntityNode> rawMaterialList) throws ServiceEntityConfigureException, MaterialException, ProductionOrderException, ServiceComExecuteException {
		if (productiveBOMList == null || productiveBOMList.size() == 0) {
			return null;
		}
		/*
		 * [Step1] Calculate the base ratio from production order to BOM
		 */

		double ratio = getRatioFromProductionToBOMOrder(productionOrder, billOfMaterialOrder, productiveBOMList);
		List<ServiceEntityNode> productionProposalItemList = new ArrayList<>();
		/*
		 * [Step2] Traverse from first BOM layer into the footer layer to
		 * calculate each item required amount
		 */
		List<ServiceEntityNode> firstBomLayerList = new ArrayList<>();
		if (!billOfMaterialOrder.getUuid().equals(productionOrder.getRefBillOfMaterialUUID())) {
			// In case productionOrder's BOM not point to BOM top level
			firstBomLayerList = billOfMaterialOrderManager
					.filterSubBOMItemList(productionOrder.getRefBillOfMaterialUUID(), productiveBOMList);
		} else {
			firstBomLayerList = billOfMaterialOrderManager.filterBOMItemListByLayer(1, productiveBOMList);
		}
		for (ServiceEntityNode seNode : firstBomLayerList) {
			/*
			 * [Step2] Calculate the first BOM layer and generate the relative
			 * production　order item
			 */
			ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
			double amount = productiveBOMItem.getAmount() * ratio;
			// Calculate the amount with loss rate
			double amountWithLossRate = amount / (1 - productiveBOMItem.getTheoLossRate() / 100);
			amountWithLossRate = Math.ceil(amountWithLossRate);
			// Update the amount of Productive BOM item
			productiveBOMItem.setAmountWithLossRate(amountWithLossRate);
			ProductionOrderItem productionOrderItem = (ProductionOrderItem) newEntityNode(productionOrder,
					ProductionOrderItem.NODENAME);
			productionOrderItem.setAmount(amount);
			productionOrderItem.setActualAmount(amountWithLossRate);
			productionOrderItem.setAmountWithLossRate(amountWithLossRate);
			productionOrderItem.setRefMaterialSKUUUID(productiveBOMItem.getRefMaterialSKUUUID());
			productionOrderItem.setRefUnitUUID(productiveBOMItem.getRefUnitUUID());
			productionOrderItem.setProductionBatchNumber(productionOrder.getProductionBatchNumber());
			productionOrderItem.setRefBOMItemUUID(productiveBOMItem.getUuid());
			productionProposalItemList.add(productionOrderItem);
			List<ServiceEntityNode> subProposalItemList = genProposalToProdOrderItemCore(productionOrder, prodSupplyWarehouseList,
					productiveBOMList, rawStoreItemList, rawMaterialList, null, productionOrderItem);
			if (!ServiceCollectionsHelper.checkNullList(subProposalItemList)) {
				productionProposalItemList.addAll(subProposalItemList);
			}
		}
		/*
		 * [Step5] Process to calculate lead time for each productive BOM Item
		 */
		productionOrderTimeComsumeProxy.calculateProdBOMItemLeadTime(productiveBOMList, rawMaterialList);
		// Also setting the lead time to BOM order
		ProductiveBOMOrder productiveBOMOrder = billOfMaterialOrderManager.genInitProductiveBomOrder(billOfMaterialOrder);
		productionOrderTimeComsumeProxy
				.processSetProductionOrderLeadTime(billOfMaterialOrder, productionOrder, productiveBOMList, rawMaterialList);
		autoCalculatePlanTimeByLeadTime(productionOrder, billOfMaterialOrder, productiveBOMList, productionProposalItemList);
		productionProposalItemList.add(productiveBOMOrder);
		/*
		 * [Step5] Process to set the time for each BOM list
		 */
		return productionProposalItemList;
	}

	/**
	 * [Internal method] After self lead time and com lead time is
	 *
	 * @param productionOrder
	 * @param billOfMaterialOrder
	 * @param productiveBOMList
	 * @param productionProposalItemList
	 * @return
	 */
	private void autoCalculatePlanTimeByLeadTime(ProductionOrder productionOrder, BillOfMaterialOrder billOfMaterialOrder,
			List<ServiceEntityNode> productiveBOMList, List<ServiceEntityNode> productionProposalItemList) {
		/*
		 * [Step1] Initialize productive BOM order
		 */
		ProductiveBOMOrder productiveBOMOrder = billOfMaterialOrderManager.genInitProductiveBomOrder(billOfMaterialOrder);

		if (productionOrder.getPlanStartPrepareDate() != null) {
			// In case plan start time is not null, then calculate and adjust
			// start prepare date
			LocalDateTime planStartTime = ServiceEntityDateHelper
					.adjustDays(productionOrder.getPlanStartPrepareDate(), productionOrder.getComLeadTime());
			productionOrder.setPlanStartTime(planStartTime);
			LocalDateTime planEndTime = ServiceEntityDateHelper
					.adjustDays(productionOrder.getPlanStartTime(), productionOrder.getSelfLeadTime());
			productionOrder.setPlanEndTime(planEndTime);
		}
		if (productionOrder.getPlanStartTime() != null) {
			// In case plan start time is not null, then calculate and adjust
			// start prepare date
			LocalDateTime planStartPrepareDate = ServiceEntityDateHelper
					.adjustDays(productionOrder.getPlanStartTime(), -productionOrder.getComLeadTime());
			productionOrder.setPlanStartPrepareDate(planStartPrepareDate);
			LocalDateTime planEndTime = ServiceEntityDateHelper
					.adjustDays(productionOrder.getPlanStartTime(), productionOrder.getSelfLeadTime());
			productionOrder.setPlanEndTime(planEndTime);
		}
		if (productionOrder.getPlanEndTime() != null) {
			// In case plan start time is not null, then calculate and adjust
			// start prepare date
			LocalDateTime planStartTime = ServiceEntityDateHelper
					.adjustDays(productionOrder.getPlanEndTime(), -productionOrder.getSelfLeadTime());
			productionOrder.setPlanStartTime(planStartTime);
			LocalDateTime planStartPrepareDate = ServiceEntityDateHelper
					.adjustDays(productionOrder.getPlanStartTime(), -productionOrder.getComLeadTime());
			productionOrder.setPlanStartPrepareDate(planStartPrepareDate);
		}

		/*
		 * [Step1.6] After 1st-round date pre-processing, special handling in
		 * case plan start date is still empty
		 */
		if (productionOrder.getPlanStartPrepareDate() == null) {
			productionOrder.setPlanStartPrepareDate(LocalDateTime.now());
			LocalDateTime planStartTime = ServiceEntityDateHelper
					.adjustDays(productionOrder.getPlanStartPrepareDate(), productionOrder.getComLeadTime());
			productionOrder.setPlanStartTime(planStartTime);
			LocalDateTime planEndTime = ServiceEntityDateHelper
					.adjustDays(productionOrder.getPlanStartTime(), productionOrder.getSelfLeadTime());
			productionOrder.setPlanEndTime(planEndTime);
		}

		productionProposalItemList.add(productiveBOMOrder);
		List<ServiceEntityNode> firstBomLayerList = billOfMaterialOrderManager.filterBOMItemListByLayer(1, productiveBOMList);
		/*
		 * [Step2] Process to set the time for each BOM list
		 */
		for (ServiceEntityNode seNode : firstBomLayerList) {
			ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
			// Set plan start time
			if (productiveBOMItem.getAmountWithLossRate() == 0) {
				continue;
			}
			LocalDateTime endPointDate = productionOrder.getPlanStartTime();
			LocalDateTime planStartPrepareDate = ServiceEntityDateHelper.adjustDays(endPointDate, -productiveBOMItem.getComLeadTime());
			productiveBOMItem.setPlanStartPrepareDate(planStartPrepareDate);
			LocalDateTime planStartDate = ServiceEntityDateHelper.adjustDays(endPointDate, -productiveBOMItem.getSelfLeadTime());
			productiveBOMItem.setPlanStartDate(planStartDate);
			productiveBOMItem.setPlanEndDate(endPointDate);
			List<ServiceEntityNode> subBOMItemList = billOfMaterialOrderManager
					.filterSubBOMItemList(productiveBOMItem.getUuid(), productiveBOMList);
			if (subBOMItemList != null && subBOMItemList.size() > 0) {
				setStartDateForBOMItem(productiveBOMItem, subBOMItemList, productiveBOMList, productionProposalItemList);
			}
			/*
			 * Find the update the staring time information in proposal
			 */
			List<ServiceEntityNode> prodOrderItemReqProposalList = filterItemProposalListByItemUUID(productionProposalItemList,
					productiveBOMItem.getUuid());
			printProposalList(prodOrderItemReqProposalList);
			if (prodOrderItemReqProposalList != null && prodOrderItemReqProposalList.size() > 0) {
				for (ServiceEntityNode tmpSENode : prodOrderItemReqProposalList) {
					ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) tmpSENode;
					// TODO check this logic
					if (prodOrderItemReqProposal.getDocumentType() == IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY) {
						// In case proposal is just outbound delivery, then
						// don't need time
						prodOrderItemReqProposal.setPlanStartDate(endPointDate);
						prodOrderItemReqProposal.setPlanEndDate(endPointDate);
						prodOrderItemReqProposal.setPlanStartPrepareDate(endPointDate);
						prodOrderItemReqProposal.setSelfLeadTime(0);
						prodOrderItemReqProposal.setComLeadTime(0);
					} else {
						prodOrderItemReqProposal.setPlanStartDate(planStartDate);
						prodOrderItemReqProposal.setPlanEndDate(endPointDate);
						prodOrderItemReqProposal.setPlanStartPrepareDate(planStartPrepareDate);
						prodOrderItemReqProposal.setSelfLeadTime(productiveBOMItem.getSelfLeadTime());
						prodOrderItemReqProposal.setComLeadTime(productiveBOMItem.getComLeadTime());
					}

				}
			}
			List<ServiceEntityNode> productionOrderItemList = filterProductionOrderItemListByItemUUID(productionProposalItemList,
					productiveBOMItem.getUuid());
			if (productionOrderItemList != null && productionOrderItemList.size() > 0) {
				for (ServiceEntityNode tmpSENode : productionOrderItemList) {
					ProductionOrderItem productionOrderItem = (ProductionOrderItem) tmpSENode;
					productionOrderItem.setPlanStartDate(planStartDate);
					productionOrderItem.setPlanEndDate(endPointDate);
					productionOrderItem.setPlanStartPrepareDate(planStartPrepareDate);
					productionOrderItem.setSelfLeadTime(productiveBOMItem.getSelfLeadTime());
					productionOrderItem.setComLeadTime(productiveBOMItem.getComLeadTime());
				}
			}
		}
	}

	/**
	 * Core Logic to generate production proposal list for specified production
	 * order item
	 *
	 * @param productionOrderItem
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public List<ServiceEntityNode> genProposalToProdOrderItemCore(ProductionOrder rootProductionOrder,
			List<ServiceEntityNode> prodSupplyWarehouseList, List<ServiceEntityNode> productiveBOMList,
			List<ServiceEntityNode> rawStoreItemList, List<ServiceEntityNode> rawMaterialList, StorageCoreUnit requestCoreUnit,
			ProductionOrderItem productionOrderItem) throws MaterialException, ServiceEntityConfigureException, ServiceComExecuteException {
		/*
		 * [Step1] Calculate the current storage, and generate direct out-bound
		 * proposal firstly.
		 */
		ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) billOfMaterialOrderManager
				.filterBOMItemByUUID(productionOrderItem.getRefBOMItemUUID(), productiveBOMList);
		// Default request core unit is generated from production order item,
		// Or else it could be special request, such as additional picking request
		if(requestCoreUnit == null){
			requestCoreUnit = new StorageCoreUnit(productionOrderItem.getRefMaterialSKUUUID(),
					productionOrderItem.getRefUnitUUID(), productionOrderItem.getActualAmount());
		}
		productiveBOMItem.setAmountWithLossRate(requestCoreUnit.getAmount());
		List<ServiceEntityNode> productionProposalItemList = new ArrayList<ServiceEntityNode>();
		if (prodSupplyWarehouseList != null && prodSupplyWarehouseList.size() > 0) {
			for (ServiceEntityNode tmpSENode : prodSupplyWarehouseList) {
				ProdOrderSupplyWarehouse prodOrderSupplyWarehouse = (ProdOrderSupplyWarehouse) tmpSENode;
				List<ServiceEntityNode> outReqProposalList = generateOutboundDeliveryProposal(rootProductionOrder,
						productionOrderItem, requestCoreUnit, productiveBOMItem, prodOrderSupplyWarehouse.getRefUUID(), rawStoreItemList);
				if (!ServiceCollectionsHelper.checkNullList(outReqProposalList)) {
					productionProposalItemList.addAll(outReqProposalList);
				}
				/*
				 * [Step1.5] Calculate Available amount for production order
				 * item
				 */
				//TODO bug:amount can't be direct added
				double availableAmount = ProductionPlanManager.calculateSumProposalAmount(outReqProposalList);
				productionOrderItem.setAvailableAmount(availableAmount);
				if (productionOrderItem.getItemStatus() == ProductionPlanItem.STATUS_AVAILABLE) {
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
			MaterialStockKeepUnit materialStockKeepUnit = materialStockKeepUnitManager
					.getMaterialSKUWrapper(productiveBOMItem.getRefMaterialSKUUUID(), productiveBOMItem.getClient(), rawMaterialList);
			ProdOrderItemReqProposal prodReqProposal = generateProdPurchaseProposal(rootProductionOrder, productionOrderItem,
					productiveBOMItem, materialStockKeepUnit);
			/*
			 * [Step2.4] Calculate in process amount
			 */
			productionOrderItem.setItemStatus(ProductionPlanItem.STATUS_INPROCESS);
			if (prodReqProposal != null) {
				productionProposalItemList.add(prodReqProposal);
			}
			/*
			 * [Step2.5] Process the sub Productive BOM list
			 */
			if (needFurtherBOMPlan(prodReqProposal)) {
				List<ServiceEntityNode> subProdItemList = generateSubProductionItemUnion(rootProductionOrder, prodReqProposal,
						productiveBOMItem, productiveBOMList, prodSupplyWarehouseList, rawStoreItemList, rawMaterialList);
				if (subProdItemList != null && subProdItemList.size() > 0) {
					productionProposalItemList.addAll(subProdItemList);
				}
			}
		}
		return productionProposalItemList;
	}

	/**
	 * Logic to calculate if current Production proposal need further BOM plan
	 *
	 * @param prodReqProposal
	 * @return
	 */
	public static boolean needFurtherBOMPlan(ProdOrderItemReqProposal prodReqProposal) {
		if (prodReqProposal != null) {
			// In case prod proposal is [Production Order] Need further BOM Plan
			if (prodReqProposal.getDocumentType() == IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER) {
				return true;
			}
		}
		return false;
	}

	protected void printProposalList(List<ServiceEntityNode> productionProposalItemList) {

		for (ServiceEntityNode seNode : productionProposalItemList) {
			if (ProdOrderItemReqProposal.NODENAME.equals(seNode.getNodeName())) {
				ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) seNode;
				logger.info("Proposal: UUID:" + prodOrderItemReqProposal.getUuid());
				logger.info("   ItemUUID:" + prodOrderItemReqProposal.getRefBOMItemUUID());
			}
			if (ProductionOrderItem.NODENAME.equals(seNode.getNodeName())) {
				ProductionOrderItem productionOrderItem = (ProductionOrderItem) seNode;

				logger.info("ProductionOrderItem: UUID:" + productionOrderItem.getUuid());
				logger.info("   ItemUUID:" + productionOrderItem.getRefBOMItemUUID());
			}
		}
	}

	protected List<ServiceEntityNode> filterItemProposalListByItemUUID(List<ServiceEntityNode> productionProposalItemList,
			String itemUUID) {
		if (ServiceCollectionsHelper.checkNullList(productionProposalItemList)) {
			return null;
		}
		if (ServiceEntityStringHelper.checkNullString(itemUUID)) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
		for (ServiceEntityNode seNode : productionProposalItemList) {
			if (seNode.getNodeName().equals(ProdOrderItemReqProposal.NODENAME)) {
				ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) seNode;
				if (itemUUID.equals(prodOrderItemReqProposal.getRefBOMItemUUID())) {
					resultList.add(prodOrderItemReqProposal);
				}
			}
		}
		return resultList;
	}

	protected List<ServiceEntityNode> filterProductionOrderItemListByItemUUID(List<ServiceEntityNode> productionProposalItemList,
			String itemUUID) {
		if (ServiceCollectionsHelper.checkNullList(productionProposalItemList)) {
			return null;
		}
		if (ServiceEntityStringHelper.checkNullString(itemUUID)) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
		for (ServiceEntityNode seNode : productionProposalItemList) {
			if (seNode.getNodeName().equals(ProductionOrderItem.NODENAME)) {
				ProductionOrderItem productionOrderItem = (ProductionOrderItem) seNode;
				if (itemUUID.equals(productionOrderItem.getRefBOMItemUUID())) {
					resultList.add(productionOrderItem);
				}
			}
		}
		return resultList;
	}

	protected void setStartDateForBOMItem(ProductiveBOMItem parentBOMItem, List<ServiceEntityNode> curBOMItemList,
			List<ServiceEntityNode> productiveBOMList, List<ServiceEntityNode> productionOrderItemList) {
		LocalDateTime endPointDate = parentBOMItem.getPlanStartDate();
		if (ServiceCollectionsHelper.checkNullList(productiveBOMList)) {
			return;
		}
		for (ServiceEntityNode seNode : curBOMItemList) {
			ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
			if (productiveBOMItem.getAmountWithLossRate() == 0) {
				continue;
			}
			LocalDateTime planStartPrepareDate = ServiceEntityDateHelper.adjustDays(endPointDate, -productiveBOMItem.getComLeadTime());
			productiveBOMItem.setPlanStartPrepareDate(planStartPrepareDate);
			LocalDateTime planStartDate = ServiceEntityDateHelper.adjustDays(endPointDate, -productiveBOMItem.getSelfLeadTime());
			productiveBOMItem.setPlanStartDate(planStartDate);
			productiveBOMItem.setPlanEndDate(parentBOMItem.getPlanStartDate());
			List<ServiceEntityNode> subBOMItemList = billOfMaterialOrderManager
					.filterSubBOMItemList(productiveBOMItem.getUuid(), productiveBOMList);
			if (subBOMItemList != null && subBOMItemList.size() > 0) {
				setStartDateForBOMItem(productiveBOMItem, subBOMItemList, productiveBOMList, productionOrderItemList);
			}
			// Find the update the staring time information in proposal
			List<ServiceEntityNode> prodOrderItemReqProposalList = filterItemProposalListByItemUUID(productionOrderItemList,
					productiveBOMItem.getUuid());
			if (prodOrderItemReqProposalList != null && prodOrderItemReqProposalList.size() > 0) {
				for (ServiceEntityNode tmpSENode : prodOrderItemReqProposalList) {
					ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) tmpSENode;
					prodOrderItemReqProposal.setPlanStartDate(planStartDate);
					prodOrderItemReqProposal.setPlanEndDate(endPointDate);
					prodOrderItemReqProposal.setPlanStartPrepareDate(planStartPrepareDate);
					prodOrderItemReqProposal.setSelfLeadTime(productiveBOMItem.getSelfLeadTime());
					prodOrderItemReqProposal.setComLeadTime(productiveBOMItem.getComLeadTime());
				}
			}
		}
	}

	protected List<ServiceEntityNode> generateSubProductionItemUnion(ProductionOrder rootProductionOrder,
			ProdOrderItemReqProposal parentReqProposal, ProductiveBOMItem parentBOMItem, List<ServiceEntityNode> productiveBOMList,
			List<ServiceEntityNode> prodSupplyWarehouseList, List<ServiceEntityNode> rawStoreItemList,
			List<ServiceEntityNode> rawMterialSKUList) throws ServiceEntityConfigureException, MaterialException, ServiceComExecuteException {
		List<ServiceEntityNode> subBOMItemList = billOfMaterialOrderManager
				.filterSubBOMItemList(parentBOMItem.getUuid(), productiveBOMList);
		if (subBOMItemList == null || subBOMItemList.size() == 0) {
			return null;
		}
		List<ServiceEntityNode> productionOrderItemList = new ArrayList<>();
		/*
		 * [Step1] calculate the ratioWithLossRate by comparing parent
		 * production item amountWithLossRate to BOM item amount it could
		 * calculate the amoutWithLossRate in parent layer
		 */
		double ratio = parentReqProposal.getAmount() / parentBOMItem.getAmount();
		for (ServiceEntityNode seNode : subBOMItemList) {
			ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
			ProductionOrderItem productionOrderItem = (ProductionOrderItem) newEntityNode(parentReqProposal,
					ProductionOrderItem.NODENAME);
			productionOrderItem.setAmount(ratio * productiveBOMItem.getAmount());
			// theory amount with loss rate
			double amountWithLossRate = productionOrderItem.getAmount() / (1 - productiveBOMItem.getTheoLossRate() / 100);
			amountWithLossRate = Math.ceil(amountWithLossRate);
			productionOrderItem.setAmountWithLossRate(amountWithLossRate);
			productionOrderItem.setActualAmount(amountWithLossRate);
			productiveBOMItem.setAmountWithLossRate(amountWithLossRate);
			productionOrderItem.setRefMaterialSKUUUID(productiveBOMItem.getRefMaterialSKUUUID());
			productionOrderItem.setRefUnitUUID(productiveBOMItem.getRefUnitUUID());
			productionOrderItem.setRefBOMItemUUID(productiveBOMItem.getUuid());
			productionOrderItemList.add(productionOrderItem);

			List<ServiceEntityNode> productionProposalItemList = genProposalToProdOrderItemCore(rootProductionOrder,
					prodSupplyWarehouseList, productiveBOMList, rawStoreItemList, rawMterialSKUList, null, productionOrderItem);
			if (!ServiceCollectionsHelper.checkNullList(productionProposalItemList)) {
				productionOrderItemList.addAll(productionProposalItemList);
			}
		}
		return productionOrderItemList;
	}

	protected List<ServiceEntityNode> generateSubProductionItemUnion(ProductionOrderItem parentProdItem,
			BillOfMaterialItem parentBOMItem, double ratio, List<ServiceEntityNode> rawBomItemList)
			throws ServiceEntityConfigureException, MaterialException {
		List<ServiceEntityNode> subBOMItemList = billOfMaterialOrderManager
				.filterSubBOMItemList(parentBOMItem.getUuid(), rawBomItemList);
		if (subBOMItemList == null || subBOMItemList.size() == 0) {
			return null;
		}
		List<ServiceEntityNode> productionOrderItemList = new ArrayList<ServiceEntityNode>();
		/*
		 * [Step1] calculate the ratioWithLossRate by comparing parent
		 * production item amountWithLossRate to BOM item amount it could
		 * calculate the amoutWithLossRate in parent layer
		 */
		StorageCoreUnit prodStorageCoreUnit = new StorageCoreUnit();
		prodStorageCoreUnit.setAmount(parentProdItem.getAmountWithLossRate());
		prodStorageCoreUnit.setRefMaterialSKUUUID(parentProdItem.getRefMaterialSKUUUID());
		prodStorageCoreUnit.setRefUnitUUID(parentProdItem.getRefUnitUUID());
		double ratioWithLossRate = getRatioUnion(prodStorageCoreUnit, parentBOMItem);
		for (ServiceEntityNode seNode : subBOMItemList) {
			BillOfMaterialItem billOfMaterialItem = (BillOfMaterialItem) seNode;
			ProductionOrderItem productionOrderItem = (ProductionOrderItem) newEntityNode(parentProdItem,
					ProductionOrderItem.NODENAME);
			productionOrderItem.setAmount(billOfMaterialItem.getAmount() * ratio);
			productionOrderItem.setRefMaterialSKUUUID(billOfMaterialItem.getRefMaterialSKUUUID());
			productionOrderItem.setRefUnitUUID(billOfMaterialItem.getRefUnitUUID());
			/*
			 * [Step2] Calculate the key local variable:[pureAmountLossRate]
			 * [pureAmountLossRate] is current production item theory amount
			 * calculated by amountWithLossRate from last layer
			 *
			 */
			double pureAmountLossRate = billOfMaterialItem.getAmount() * ratioWithLossRate;
			// Calculate the amount with loss rate by merging this layer's loss
			// rate
			double amountWithLossRate = pureAmountLossRate / (1 - billOfMaterialItem.getTheoLossRate() / 100);
			amountWithLossRate = Math.ceil(amountWithLossRate);
			productionOrderItem.setAmountWithLossRate(amountWithLossRate);
			// Set initial actual amount
			productionOrderItem.setActualAmount(amountWithLossRate);
			productionOrderItemList.add(productionOrderItem);
			/*
			 * [Step3] recursive call this method
			 */
			List<ServiceEntityNode> subProdItemList = generateSubProductionItemUnion(productionOrderItem, billOfMaterialItem, ratio,
					rawBomItemList);
			if (subProdItemList != null && subProdItemList.size() > 0) {
				productionOrderItemList.addAll(subProdItemList);
			}
		}
		return productionOrderItemList;
	}

	/**
	 * Generate the out-bound delivery proposal from each warehouse
	 *
	 * @param productionOrderItem
	 * @param requestCoreUnit     : real request for material, amount, unit
	 * @param productiveBOMItem
	 * @param warehouseUUID
	 * @param rawStoreItemList
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	protected List<ServiceEntityNode> generateOutboundDeliveryProposal(ProductionOrder rootProductionOrder,
			ProductionOrderItem productionOrderItem, StorageCoreUnit requestCoreUnit, ProductiveBOMItem productiveBOMItem,
			String warehouseUUID, List<ServiceEntityNode> rawStoreItemList)
			throws ServiceEntityConfigureException, MaterialException {
		/*
		 * [Step1] check the available store item
		 */
		List<WarehouseStoreItem> storeItemList = new ArrayList<>();
		if (rawStoreItemList != null && rawStoreItemList.size() > 0) {
			storeItemList = warehouseStoreManager
					.getStoreItemBySKUWarehouseOnline(productionOrderItem.getRefMaterialSKUUUID(), warehouseUUID, rawStoreItemList);
		} else {
			storeItemList = warehouseStoreItemManager.getInStockStoreItemBySKUWarehouse(productionOrderItem.getRefMaterialSKUUUID(), warehouseUUID);
		}
		if (ServiceCollectionsHelper.checkNullList(storeItemList)) {
			return null;
		}
		List<ServiceEntityNode> prodReqProposalList = new ArrayList<>();
		StorageCoreUnit storageCoreUnit = new StorageCoreUnit(requestCoreUnit.getRefMaterialSKUUUID(),
				requestCoreUnit.getRefUnitUUID(), requestCoreUnit.getAmount());
		/*
		 * [Step2] traverse each possible store item list from current warehouse
		 */
		for (ServiceEntityNode seNode : storeItemList) {
			WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) seNode;
			if(warehouseStoreItem.getItemStatus() == WarehouseStoreItem.STATUS_ARCHIVE){
				continue;
			}
			StorageCoreUnit storageCoreUnitBack = (StorageCoreUnit) storageCoreUnit.clone();
			WarehouseStoreItem warehouseStoreItemBack = (WarehouseStoreItem) warehouseStoreItem.clone();
			StoreAvailableStoreItemRequest storeAvailableStoreItemRequest = new StoreAvailableStoreItemRequest(warehouseStoreItemBack, null, true);
			StorageCoreUnit availableStoreCoreUnit = outboundDeliveryWarehouseItemManager.getAvailableStoreItemAmountUnion(
					storeAvailableStoreItemRequest);
			if(availableStoreCoreUnit.getAmount() <= 0){
				// In case this store item already been fully occupied with other documents, just skip
				continue;
			}
			try {
				/*
				 * Backup each store unit before save
				 */
				outboundDeliveryWarehouseItemManager.checkAndUpdateWarehouseStoreItemAmountCore(storageCoreUnit,
						true,storeAvailableStoreItemRequest, availableStoreCoreUnit);
				if (storageCoreUnit.getAmount() == 0) {
					/*
					 * In case current warehouseStore meet requirement,
					 */
					ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) this
							.newEntityNode(productionOrderItem, ProdOrderItemReqProposal.NODENAME);
					if (storageCoreUnitBack.getAmount() <= 0) {
						// If available amount is 0, just continue;
						continue;
					}
					prodOrderItemReqProposal.setAmount(storageCoreUnitBack.getAmount());
					prodOrderItemReqProposal.setRefUnitUUID(storageCoreUnitBack.getRefUnitUUID());
					prodOrderItemReqProposal.setRefMaterialSKUUUID(storageCoreUnitBack.getRefMaterialSKUUUID());
					prodOrderItemReqProposal.setRefWarehouseUUID(warehouseUUID);
					prodOrderItemReqProposal.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY);
					prodOrderItemReqProposal.setStoreAmount(availableStoreCoreUnit.getAmount());
					prodOrderItemReqProposal.setStoreUnitUUID(availableStoreCoreUnit.getRefUnitUUID());
					prodOrderItemReqProposal.setProductionBatchNumber(rootProductionOrder.getProductionBatchNumber());
					// Set this proposal as available status
					prodOrderItemReqProposal.setItemStatus(ProductionPlanItem.STATUS_AVAILABLE);
					prodOrderItemReqProposal.setNextDocMatItemUUID(warehouseStoreItem.getUuid());
					prodOrderItemReqProposal.setRefStoreItemUUID(warehouseStoreItem.getUuid());
					prodOrderItemReqProposal.setNextDocType(IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM);
					productionOrderItem.setItemStatus(ProductionPlanItem.STATUS_AVAILABLE);
					prodOrderItemReqProposal.setRefBOMItemUUID(productiveBOMItem.getUuid());
					// Set produtive BOM item amount 0, already meet production order item request
					productiveBOMItem.setAmountWithLossRate(storageCoreUnit.getAmount());
					productiveBOMItem.setRefUnitUUID(storageCoreUnit.getRefUnitUUID());
					prodReqProposalList.add(prodOrderItemReqProposal);
					return prodReqProposalList;
				}
			} catch (WarehouseStoreItemException e) {
				// In case current warehouse store item can't meet requirement
				ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) this
						.newEntityNode(productionOrderItem, ProdOrderItemReqProposal.NODENAME);
				// update storeCoreUnit
				storageCoreUnit = materialStockKeepUnitManager.mergeStorageUnitCore(storageCoreUnit, availableStoreCoreUnit, StorageCoreUnit.OPERATOR_MINUS, warehouseStoreItem.getClient());
				// set plan proposal amount all possible maxinum amount from
				prodOrderItemReqProposal.setAmount(availableStoreCoreUnit.getAmount());
				prodOrderItemReqProposal.setRefUnitUUID(availableStoreCoreUnit.getRefUnitUUID());
				// Reduce the amount for current store item
				warehouseStoreItemManager.minusWarehouseStoreItemLoc(warehouseStoreItem, availableStoreCoreUnit);
				prodOrderItemReqProposal.setAmount(availableStoreCoreUnit.getAmount());
				prodOrderItemReqProposal.setRefUnitUUID(availableStoreCoreUnit.getRefUnitUUID());
				prodOrderItemReqProposal.setRefMaterialSKUUUID(warehouseStoreItemBack.getRefMaterialSKUUUID());
				prodOrderItemReqProposal.setRefWarehouseUUID(warehouseUUID);
				prodOrderItemReqProposal.setStoreAmount(availableStoreCoreUnit.getAmount());
				// Set this proposal as available status
				prodOrderItemReqProposal.setItemStatus(ProductionPlanItem.STATUS_AVAILABLE);
				prodOrderItemReqProposal.setStoreUnitUUID(availableStoreCoreUnit.getRefUnitUUID());
				prodOrderItemReqProposal.setProductionBatchNumber(rootProductionOrder.getProductionBatchNumber());
				prodOrderItemReqProposal.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY);
				prodOrderItemReqProposal.setNextDocMatItemUUID(warehouseStoreItem.getUuid());
				prodOrderItemReqProposal.setRefStoreItemUUID(warehouseStoreItem.getUuid());
				prodOrderItemReqProposal.setNextDocType(IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM);
				prodOrderItemReqProposal.setRefBOMItemUUID(productiveBOMItem.getUuid());
				productiveBOMItem.setAmountWithLossRate(storageCoreUnit.getAmount());
				productiveBOMItem.setRefUnitUUID(storageCoreUnit.getRefUnitUUID());
				prodReqProposalList.add(prodOrderItemReqProposal);
			}
		}
		return prodReqProposalList;
	}

	/**
	 * [Internal method] After the out-bound delivery proposal, generate the
	 * production or purchase proposal for production order Item
	 *
	 * @param productionOrderItem
	 * @param productiveBOMItem
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	protected ProdOrderItemReqProposal generateProdPurchaseProposal(ProductionOrder rootProductionOrder,
			ProductionOrderItem productionOrderItem, ProductiveBOMItem productiveBOMItem,
			MaterialStockKeepUnit materialStockKeepUnit) throws ServiceEntityConfigureException, MaterialException {
		ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) this
				.newEntityNode(productionOrderItem, ProdOrderItemReqProposal.NODENAME);
		prodOrderItemReqProposal.setRefBOMItemUUID(productiveBOMItem.getUuid());
		prodOrderItemReqProposal.setAmount(productiveBOMItem.getAmountWithLossRate());
		prodOrderItemReqProposal.setRefUnitUUID(productiveBOMItem.getRefUnitUUID());
		prodOrderItemReqProposal.setRefMaterialSKUUUID(productiveBOMItem.getRefMaterialSKUUUID());
		prodOrderItemReqProposal.setProductionBatchNumber(rootProductionOrder.getProductionBatchNumber());
		prodOrderItemReqProposal.setItemStatus(ProductionPlanItem.STATUS_INPROCESS);
		int documentType = calculateMaterialReqDocumentType(materialStockKeepUnit);
		prodOrderItemReqProposal.setDocumentType(documentType);
		return prodOrderItemReqProposal;
	}

	/**
	 * Core Logic to decide material supply type during production
	 *
	 * @return
	 */
	public static int calculateMaterialReqDocumentType(MaterialStockKeepUnit materialStockKeepUnit) {
		if (materialStockKeepUnit == null) {
			return IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT;
		}
		if (materialStockKeepUnit.getSupplyType() == Material.SUPPLYTYPE_SELF_PROD) {
			return IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER;
		}
		if (materialStockKeepUnit.getSupplyType() == Material.SUPPLYTYPE_SUBCONTRACT) {
			return IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT;
		}
		if (materialStockKeepUnit.getSupplyType() == Material.SUPPLYTYPE_MIXED) {
			return IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER;
		}
		if (materialStockKeepUnit.getSupplyType() == Material.SUPPLYTYPE_PURCHASE) {
			return IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT;
		}
		return IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT;
	}

	/**
	 * [Internal method] Comparing the production order item and BOM item,
	 * calculate the theorical Ratio Comparing production item amount to BOM
	 * model item amount
	 *
	 * @param prodStoreUnit
	 * @param billOfMaterialItem
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	protected double getRatioUnion(StorageCoreUnit prodStoreUnit, BillOfMaterialItem billOfMaterialItem)
			throws ServiceEntityConfigureException, MaterialException {
		StorageCoreUnit prodStorageCoreUnit = new StorageCoreUnit();
		prodStorageCoreUnit.setAmount(prodStoreUnit.getAmount());
		prodStorageCoreUnit.setRefMaterialSKUUUID(prodStoreUnit.getRefMaterialSKUUUID());
		prodStorageCoreUnit.setRefUnitUUID(prodStoreUnit.getRefUnitUUID());
		StorageCoreUnit tmpBomStorageUnit = new StorageCoreUnit();
		tmpBomStorageUnit.setRefMaterialSKUUUID(billOfMaterialItem.getRefMaterialSKUUUID());
		tmpBomStorageUnit.setRefUnitUUID(billOfMaterialItem.getRefUnitUUID());
		tmpBomStorageUnit.setAmount(billOfMaterialItem.getAmount());
		double ratio = materialStockKeepUnitManager
				.getStorageUnitRatio(prodStorageCoreUnit, tmpBomStorageUnit, billOfMaterialItem.getClient());
		return ratio;
	}

	public List<ServiceEntityNode> filterProductionItemList(List<ServiceEntityNode> rawList) {
		if (rawList == null || rawList.size() == 0) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
		for (ServiceEntityNode seNode : rawList) {
			if (ProductionOrderItem.NODENAME.equals(seNode.getNodeName())) {
				resultList.add(seNode);
			}
		}
		return resultList;
	}

	public List<ServiceEntityNode> filterSubProdItemReqList(ProductionOrderItem productionOrderItem,
			List<ServiceEntityNode> rawList) {
		if (rawList == null || rawList.size() == 0) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
		for (ServiceEntityNode seNode : rawList) {
			if (ProdOrderItemReqProposal.NODENAME.equals(seNode.getNodeName()) && productionOrderItem.getUuid()
					.equals(seNode.getParentNodeUUID())) {
				resultList.add(seNode);
			}
		}
		return resultList;
	}

	public void convReservedDocToOrderUI(DocumentContent reservedDocument, ProductionOrderUIModel productionOrderUIModel) {
		if (reservedDocument != null) {
			productionOrderUIModel.setReservedDocId(reservedDocument.getId());
			productionOrderUIModel.setReservedDocName(reservedDocument.getName());
		}
	}

	public void convProductionOrderToUI(ProductionOrder productionOrder, ProductionOrderUIModel productionOrderUIModel)
			throws ServiceEntityInstallationException {
		convProductionOrderToUI(productionOrder, productionOrderUIModel, null);
	}

	public void convProductionOrderToUI(ProductionOrder productionOrder, ProductionOrderUIModel productionOrderUIModel,
			LogonInfo logonInfo) throws ServiceEntityInstallationException {
		if (productionOrder != null) {
			docFlowProxy.convDocumentToUI(productionOrder, productionOrderUIModel, logonInfo);
			if (productionOrder.getPlanStartPrepareDate() != null) {
				productionOrderUIModel.setPlanStartPrepareDate(
						DefaultDateFormatConstant.DATE_MIN_FORMAT.format(productionOrder.getPlanStartPrepareDate()));
			}
			if (productionOrder.getPlanStartTime() != null) {
				productionOrderUIModel
						.setPlanStartTime(DefaultDateFormatConstant.DATE_MIN_FORMAT.format(productionOrder.getPlanStartTime()));
			}
			if (productionOrder.getPlanEndTime() != null) {
				productionOrderUIModel
						.setPlanEndTime(DefaultDateFormatConstant.DATE_MIN_FORMAT.format(productionOrder.getPlanEndTime()));
			}
			productionOrderUIModel.setApproveBy(productionOrder.getApproveBy());
			if (productionOrder.getApproveTime() != null) {
				productionOrderUIModel.setApproveTime(DefaultDateFormatConstant.DATE_FORMAT.format(productionOrder.getApproveTime()));
			}
			productionOrderUIModel.setCountApproveBy(productionOrder.getCountApproveBy());
			if (productionOrder.getCountApproveTime() != null) {
				productionOrderUIModel
						.setCountApproveTime(DefaultDateFormatConstant.DATE_FORMAT.format(productionOrder.getCountApproveTime()));
			}
			productionOrderUIModel.setReservedDocType(productionOrder.getReservedDocType());
			productionOrderUIModel.setGrossCost(productionOrder.getGrossCost());
			productionOrderUIModel.setGrossCostActual(productionOrder.getGrossCostActual());
			productionOrderUIModel.setGrossCostLossRate(productionOrder.getGrossCostLossRate());
			productionOrderUIModel.setReservedMatItemUUID(productionOrder.getReservedMatItemUUID());
			productionOrderUIModel.setRefUnitUUID(productionOrder.getRefUnitUUID());
			productionOrderUIModel.setRefMaterialSKUUUID(productionOrder.getRefMaterialSKUUUID());
			productionOrderUIModel.setName(productionOrder.getName());
			productionOrderUIModel.setStatus(productionOrder.getStatus());
			productionOrderUIModel.setRefBillOfMaterialUUID(productionOrder.getRefBillOfMaterialUUID());
			productionOrderUIModel.setId(productionOrder.getId());
			productionOrderUIModel.setComLeadTime(productionOrder.getComLeadTime());
			productionOrderUIModel.setSelfLeadTime(productionOrder.getSelfLeadTime());
			productionOrderUIModel.setNote(productionOrder.getNote());
			productionOrderUIModel.setRefWocUUID(productionOrder.getRefWocUUID());
			productionOrderUIModel.setRefPlanUUID(productionOrder.getRefPlanUUID());
			if (productionOrder.getActualEndTime() != null) {
				productionOrderUIModel
						.setActualEndTime(DefaultDateFormatConstant.DATE_MIN_FORMAT.format(productionOrder.getActualEndTime()));
			}
			productionOrderUIModel.setAmount(ServiceEntityDoubleHelper.trancateDoubleScale4(productionOrder.getAmount()));
			String amountLabel = productionOrder.getAmount() + "";
			try {
				Map<String, String> materialUnitMap = materialStockKeepUnitManager
						.initMaterialUnitMap(productionOrder.getRefMaterialSKUUUID(), productionOrder.getClient());
				if (!ServiceEntityStringHelper.checkNullString(productionOrder.getRefUnitUUID())) {
					productionOrderUIModel
							.setAmountLabel(productionOrder.getAmount() + materialUnitMap.get(productionOrder.getRefUnitUUID()));
					productionOrderUIModel.setRefUnitName(materialUnitMap.get(productionOrder.getRefUnitUUID()));
				} else {
					productionOrderUIModel.setAmountLabel(amountLabel);
				}
			} catch (ServiceEntityConfigureException e) {
				// just continue;
			} catch (MaterialException e) {
				// just continue;
			}
			if (productionOrder.getActualStartTime() != null) {
				productionOrderUIModel
						.setActualStartTime(DefaultDateFormatConstant.DATE_MIN_FORMAT.format(productionOrder.getActualStartTime()));
			}
			productionOrderUIModel.setId(productionOrder.getId());
			if (logonInfo != null) {
				Map<Integer, String> statusMap = this.initStatusMap(logonInfo.getLanguageCode());
				productionOrderUIModel.setStatusValue(statusMap.get(productionOrder.getStatus()));
			}
			productionOrderUIModel.setStatus(productionOrder.getStatus());
			productionOrderUIModel.setPriorityCode(productionOrder.getPriorityCode());
			productionOrderUIModel.setGenOrderItemMode(productionOrder.getGenOrderItemMode());
			if (logonInfo != null) {
				Map<Integer, String> doneStatusMap = initDoneStatusMap(logonInfo.getLanguageCode());
				productionOrderUIModel.setDoneStatusValue(doneStatusMap.get(productionOrder.getDoneStatus()));
				Map<Integer, String> priorityCodeMap = initPriorityCodeMap(logonInfo.getLanguageCode());
				productionOrderUIModel.setPriorityCodeValue(priorityCodeMap.get(productionOrder.getPriorityCode()));
			}
			productionOrderUIModel.setDoneStatus(productionOrder.getDoneStatus());

			if (logonInfo != null) {
				Map<Integer, String> categoryMap = initCategoryMap(logonInfo.getLanguageCode());
				productionOrderUIModel.setCategoryValue(categoryMap.get(productionOrder.getCategory()));
			}
			productionOrderUIModel.setCategory(productionOrder.getCategory());
			productionOrderUIModel.setOrderType(productionOrder.getOrderType());
			if (logonInfo != null) {
				Map<Integer, String> orderTypeMap = initOrderTypeMap(logonInfo.getLanguageCode());
				productionOrderUIModel.setOrderTypeValue(orderTypeMap.get(productionOrder.getOrderType()));
			}
			productionOrderUIModel.setProductionBatchNumber(productionOrder.getProductionBatchNumber());
			productionOrderUIModel.setNote(productionOrder.getNote());
			productionOrderUIModel.setSelfLeadTime(productionOrder.getSelfLeadTime());
			productionOrderUIModel.setComLeadTime(productionOrder.getComLeadTime());
			productionOrderUIModel.setRefMaterialSKUUUID(productionOrder.getRefMaterialSKUUUID());
			productionOrderUIModel.setRefBillOfMaterialUUID(productionOrder.getRefBillOfMaterialUUID());
			productionOrderUIModel.setAmount(ServiceEntityDoubleHelper.trancateDoubleScale4(productionOrder.getAmount()));
			productionOrderUIModel.setRefUnitUUID(productionOrder.getRefUnitUUID());
		}
	}

	public void convMaterialStockKeepUnitToUI(MaterialStockKeepUnit materialStockKeepUnit,
			ProductionOrderUIModel productionOrderUIModel) {
		convMaterialStockKeepUnitToUI(materialStockKeepUnit, productionOrderUIModel, null);
	}

	public void convMaterialStockKeepUnitToUI(MaterialStockKeepUnit materialStockKeepUnit,
			ProductionOrderUIModel productionOrderUIModel, LogonInfo logonInfo) {
		if (materialStockKeepUnit != null) {
			productionOrderUIModel.setRefMaterialSKUId(materialStockKeepUnit.getId());
			productionOrderUIModel.setRefMaterialSKUName(materialStockKeepUnit.getName());
			productionOrderUIModel.setPackageStandard(materialStockKeepUnit.getPackageStandard());
			productionOrderUIModel.setSupplyType(materialStockKeepUnit.getSupplyType());
			if (logonInfo != null) {
				try {
					Map<Integer, String> supplyTypeMap = materialStockKeepUnitManager.initSupplyTypeMap(logonInfo.getLanguageCode());
					productionOrderUIModel.setSupplyTypeValue(supplyTypeMap.get(materialStockKeepUnit.getSupplyType()));
				} catch (ServiceEntityInstallationException e) {
					// log error and continue
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
				}
			}

		}
	}

	public void convBillOfMaterialOrderToUI(BillOfMaterialOrder billOfMaterialOrder,
			ProductionOrderUIModel productionOrderUIModel) {
		if (billOfMaterialOrder != null) {
			productionOrderUIModel.setRefBillOfMaterialId(billOfMaterialOrder.getId());
			productionOrderUIModel.setRefBillOfMaterialNodeName(BillOfMaterialOrder.NODENAME);
		}
	}

	@Override
	public ServiceDocumentExtendUIModel convToDocumentExtendUIModel(ServiceEntityNode seNode, LogonInfo logonInfo) {
		if (seNode == null) {
			return null;
		}
		if (ProductionOrderItem.NODENAME.equals(seNode.getNodeName())) {
			return productionOrderItemManager.convToDocumentExtendUIModel(seNode, logonInfo);
		}
		if (ProdOrderItemReqProposal.NODENAME.equals(seNode.getNodeName())) {
			return productionOrderItemManager.convToDocumentExtendUIModel(seNode, logonInfo);
		}
		if (ProdOrderTargetMatItem.NODENAME.equals(seNode.getNodeName())) {
			return prodOrderTargetMatItemManager.convToDocumentExtendUIModel(seNode, logonInfo);
		}
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return IServiceModelConstants.ProductionOrder;
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return productionOrderSearchProxy;
	}

	public boolean checkBlockExecutionByDocflow(int actionCode, String uuid, ServiceJSONRequest serviceJSONRequest,
												SerialLogonInfo serialLogonInfo){
		if(actionCode == ProductionOrderActionNode.DOC_ACTION_APPROVE){
			return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(
					IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER, uuid, serviceJSONRequest, serialLogonInfo,
					actionCode);
		}
		if(actionCode == ProductionOrderActionNode.DOC_ACTION_REJECT_APPROVE){
			return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(
					IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER, uuid,serviceJSONRequest, serialLogonInfo,
					actionCode);
		}
		if(actionCode == ProductionOrderActionNode.DOC_ACTION_REVOKE_SUBMIT){
			serviceFlowRuntimeEngine.clearInvolveProcessIns(
					IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER,uuid);
			return true;
		}
		return true;
	}

	public void submitFlow(ProductionOrderServiceUIModel productionOrderServiceUIModel,
						   SerialLogonInfo serialLogonInfo) throws ServiceFlowRuntimeException{
		String uuid = productionOrderServiceUIModel.getProductionOrderUIModel().getUuid();
		ServiceFlowRuntimeEngine.ServiceFlowInputPara serviceFlowInputPara =
				new ServiceFlowRuntimeEngine.ServiceFlowInputPara(productionOrderServiceUIModel,
						IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER, uuid,
						ProductionOrderActionNode.DOC_ACTION_APPROVE, serialLogonInfo);
		serviceFlowRuntimeEngine.submitFlow(serviceFlowInputPara);
	}

	@Override
	public void exeFlowActionEnd(int documentType, String uuid, int actionCode,
								 ServiceJSONRequest serviceJSONRequest, SerialLogonInfo serialLogonInfo){
		try {
			ProductionOrder productionOrder = (ProductionOrder) getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID,
					ProductionOrder.NODENAME, serialLogonInfo.getClient(), null);
			ProductionOrderServiceModel productionOrderServiceModel = (ProductionOrderServiceModel) loadServiceModule(ProductionOrderServiceModel.class,
					productionOrder, productionOrderServiceUIModelExtension);
			productionOrderServiceModel.setServiceJSONRequest(serviceJSONRequest);
			if(actionCode == SystemDefDocActionCodeProxy.DOC_ACTION_APPROVE || actionCode == SystemDefDocActionCodeProxy.DOC_ACTION_REJECT_APPROVE){
				productionOrderActionExecutionProxy.executeActionCore(productionOrderServiceModel,
						actionCode, serialLogonInfo);
			}
		} catch (ServiceEntityConfigureException | ServiceModuleProxyException | DocActionException e) {
			logger.error("Failed during production order processing", e);
		}
	}

}
