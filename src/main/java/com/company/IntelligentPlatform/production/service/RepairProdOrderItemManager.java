package com.company.IntelligentPlatform.production.service;
import com.company.IntelligentPlatform.production.service.ProdPickingExtendAmountModel;

import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.WarehouseLogisticsManager;
import com.company.IntelligentPlatform.production.dto.*;
import com.company.IntelligentPlatform.production.model.*;
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
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.ServiceReflectiveHelper;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxy;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IDocumentNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class RepairProdOrderItemManager {

	public static final String METHOD_ConvRepairProdOrderItemToUI = "convRepairProdOrderItemToUI";

	public static final String METHOD_ConvUIToRepairProdOrderItem = "convUIToRepairProdOrderItem";

	public static final String METHOD_ConvRepairProdOrderToItemUI = "convRepairProdOrderToItemUI";

	public static final String METHOD_ConvOrderMaterialSKUToItemUI = "convOrderMaterialSKUToItemUI";

	public static final String METHOD_ConvItemMaterialSKUToUI = "convItemMaterialSKUToUI";

	public static final String METHOD_ConvRepairProdItemReqProposalToUI = "convRepairProdItemReqProposalToUI";

	public static final String METHOD_ConvUIToRepairProdItemReqProposal = "convUIToRepairProdItemReqProposal";

	public static final String METHOD_ConvDocumentToItemReqProposalUI = "convDocumentToItemReqProposalUI";

	public static final String METHOD_ConvRepairProdOrderToProposalUI = "convRepairProdOrderToProposalUI";

	public static final String METHOD_ConvRepairProdOrderItemToProposalUI = "convRepairProdOrderItemToProposalUI";

	public static final String METHOD_ConvWarehouseToItemReqProposalUI = "convWarehouseToItemReqProposalUI";

	@Autowired
	protected RepairProdOrderConfigureProxy productionOrderConfigureProxy;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected RepairProdOrderManager repairProdOrderManager;

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
	protected ProductionOrderItemManager productionOrderItemManager;

	@Autowired
	protected RepairProdItemReqProposalServiceUIModelExtension repairProdItemReqProposalServiceUIModelExtension;

	@Autowired
	protected RepairProdOrderItemServiceUIModelExtension repairProdOrderItemServiceUIModelExtension;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected WarehouseLogisticsManager warehouseLogisticsManager;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected RepairProdItemReqProposalManager repairProdItemReqProposalManager;

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected ServiceModuleProxy serviceModuleProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	protected Logger logger = LoggerFactory.getLogger(RepairProdOrderItemManager.class);

	private Map<String, Map<Integer, String>> itemStatusMapLan = new HashMap<>();

	public Map<Integer, String> initItemStatusMap(String languageCode) throws ServiceEntityInstallationException {
		String resourcePath = RepairProdOrderItemUIModel.class.getResource("").getPath() +
				"RepairProdOrderItem_itemStatus";
		return ServiceLanHelper
				.initDefLanguageMapResource(languageCode, this.itemStatusMapLan, resourcePath);
	}

	public Map<Integer, String> initDocumentTypeMap(String languageCode) throws ServiceEntityInstallationException {
		return serviceDocumentComProxy.getDocumentTypeMap(false, languageCode);
	}

	public List<PageHeaderModel> getPageHeaderModelList(RepairProdOrderItem repairProdOrderItem, String client)
			throws ServiceEntityConfigureException {
		RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
				.getEntityNodeByKey(repairProdOrderItem.getParentNodeUUID(), IServiceEntityNodeFieldConstant.UUID,
						RepairProdOrder.NODENAME, client, null);
		int index = 0;
		List<PageHeaderModel> resultList = new ArrayList<>();
		if (repairProdOrder != null) {
			// In case current production order item is the direct production
			// item for order
			PageHeaderModel orderHeaderModel = repairProdOrderManager.getPageHeaderModel(repairProdOrder, index);
			if (orderHeaderModel != null) {
				index++;
				resultList.add(orderHeaderModel);
			}
			PageHeaderModel itemHeaderModel = getPageHeaderModel(repairProdOrderItem, index);
			if (itemHeaderModel != null) {
				resultList.add(itemHeaderModel);
			}
		} else {
			RepairProdItemReqProposal repairProdItemReqProposal = (RepairProdItemReqProposal) repairProdOrderManager
					.getEntityNodeByKey(repairProdOrderItem.getParentNodeUUID(), IServiceEntityNodeFieldConstant.UUID,
							RepairProdItemReqProposal.NODENAME, client, null);
			// In case current production order item is the sub item for
			// proposal
			if (repairProdItemReqProposal != null) {
				List<PageHeaderModel> pageHeaderModelList = repairProdItemReqProposalManager
						.getPageHeaderModelList(repairProdItemReqProposal, client);
				if (!ServiceCollectionsHelper.checkNullList(pageHeaderModelList)) {
					resultList.addAll(pageHeaderModelList);
					index = pageHeaderModelList.size();
				}
				PageHeaderModel itemHeaderModel = getPageHeaderModel(repairProdOrderItem, index);
				if (itemHeaderModel != null) {
					resultList.add(itemHeaderModel);
				}
			}
		}
		return resultList;
	}

	protected PageHeaderModel getPageHeaderModel(RepairProdOrderItem repairProdOrderItem, int index)
			throws ServiceEntityConfigureException {
		if (repairProdOrderItem == null) {
			return null;
		}
		PageHeaderModel pageHeaderModel = new PageHeaderModel();
		pageHeaderModel.setPageTitle("repairProdOrderItemTitle");
		pageHeaderModel.setNodeInstId(RepairProdOrderItem.NODENAME);
		pageHeaderModel.setUuid(repairProdOrderItem.getUuid());
		MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
				.getEntityNodeByKey(repairProdOrderItem.getRefMaterialSKUUUID(), IServiceEntityNodeFieldConstant.UUID,
						MaterialStockKeepUnit.NODENAME, repairProdOrderItem.getClient(), null);
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
	public void updateItemStatusFromPicking(ProdPickingRefMaterialItem toFinishPickingMaterialItem, String logonUserUUID,
			String organizationUUID) throws ServiceEntityConfigureException {
		productionOrderItemManager.updateItemStatusFromPicking(toFinishPickingMaterialItem, logonUserUUID, organizationUUID);
	}

	/**
	 * Core Logic to create new Repair prod item from init model
	 * @param prodOrderItemInitModel
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public RepairProdOrderItemServiceModel newRepairProdItem(ProdOrderItemInitModel prodOrderItemInitModel,
															 String client) throws ServiceEntityConfigureException, ProductionOrderException {
		RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
				.getEntityNodeByKey(prodOrderItemInitModel.getParentNodeUUID(), IServiceEntityNodeFieldConstant.UUID,
						RepairProdOrder.NODENAME,
						client, null);
		/*
		 * [Step1] Pre check
		 */
		List<ServiceEntityNode> repairProdOrderItemList =
				repairProdOrderManager.getEntityNodeListByKey(prodOrderItemInitModel.getParentNodeUUID(),
						IServiceEntityNodeFieldConstant.UUID, RepairProdOrderItem.NODENAME, client, null);
		if(!ServiceCollectionsHelper.checkNullList(repairProdOrderItemList)){
			List<ServiceEntityNode> duplicateOrderItemList =
					repairProdOrderItemList.stream().filter(serviceEntityNode -> {
						RepairProdOrderItem repairProdOrderItem = (RepairProdOrderItem) serviceEntityNode;
						return repairProdOrderItem.getRefBOMItemUUID().equals(prodOrderItemInitModel.getBaseUUID());
					}).collect(Collectors.toList());
			if(!ServiceCollectionsHelper.checkNullList(duplicateOrderItemList)){
				RepairProdOrderItem repairProdOrderItem = (RepairProdOrderItem)duplicateOrderItemList.get(0);
				throw new ProductionOrderException(ProductionOrderException.PARA_SYSTEM_ERROR,
						repairProdOrderItem.getId());
			}
		}
		/*
		 * [Step2] Conversion and Creation
		 */
		RepairProdOrderItem repairProdOrderItem = (RepairProdOrderItem) repairProdOrderManager
				.newEntityNode(repairProdOrder, RepairProdOrderItem.NODENAME);
		repairProdOrderItem.setRefBOMItemUUID(prodOrderItemInitModel.getBaseUUID());
		repairProdOrderItem.setRefMaterialSKUUUID(prodOrderItemInitModel.getRefMaterialSKUUUID());
		repairProdOrderItem.setAmount(prodOrderItemInitModel.getAmount());
		repairProdOrderItem.setAmountWithLossRate(prodOrderItemInitModel.getAmount());
		repairProdOrderItem.setRefUnitUUID(prodOrderItemInitModel.getRefUnitUUID());
		RepairProdOrderItemServiceModel repairProdOrderItemServiceModel = new RepairProdOrderItemServiceModel();
		repairProdOrderItemServiceModel.setRepairProdOrderItem(repairProdOrderItem);
		return repairProdOrderItemServiceModel;
	}

	/**
	 * Core Logic to create new Repair prod item from init model
	 * @param prodOrderItemInitModel
	 * @param logonInfo
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public RepairProdOrderItemServiceModel newRepairProdItemWrapper(ProdOrderItemInitModel prodOrderItemInitModel,
															 LogonInfo logonInfo) throws ServiceEntityConfigureException, ServiceModuleProxyException, ProductionOrderException {
		RepairProdOrderItemServiceModel repairProdOrderItemServiceModel = newRepairProdItem(prodOrderItemInitModel,
				logonInfo.getClient());
		repairProdOrderManager.updateServiceModuleWithDelete(RepairProdOrderItemServiceModel.class,
				repairProdOrderItemServiceModel,
				logonInfo.getResOrgUUID(), logonInfo.getResOrgUUID(), RepairProdOrderItem.NODENAME,
				repairProdOrderItemServiceUIModelExtension);
		return repairProdOrderItemServiceModel;
	}

	/**
	 * Core Logic to set production order item status: Available, when all
	 * picking item finished picked.
	 *
	 * @param repairProdOrderItem
	 * @param logonUserUUID
	 * @param organizationUUID
	 */
	public void finishProdOrderItem(RepairProdOrderItem repairProdOrderItem, String logonUserUUID,
									String organizationUUID) {
		productionOrderItemManager.finishProdOrderItem(repairProdOrderItem, logonUserUUID, organizationUUID);
	}

	/**
	 * Core Logic to set production order item status: In process, when some
	 * picking item finished picked.
	 *
	 * @param repairProdOrderItem
	 * @param logonUserUUID
	 * @param organizationUUID
	 */
	public void inProcessProdOrderItem(RepairProdOrderItem repairProdOrderItem, String logonUserUUID, String organizationUUID) {
		repairProdOrderItem.setItemStatus(ProductionPlanItem.STATUS_INPROCESS);
		repairProdOrderManager.updateSENode(repairProdOrderItem, logonUserUUID, organizationUUID);
	}

	public void convRepairProdOrderItemToUI(RepairProdOrderItem repairProdOrderItem,
			RepairProdOrderItemUIModel repairProdOrderItemUIModel)
			throws ServiceEntityInstallationException, ServiceEntityConfigureException {
		convRepairProdOrderItemToUI(repairProdOrderItem, repairProdOrderItemUIModel, null);
	}

	/**
	 * Logic to calculate not in-plan amount, this method should be invoked
	 * after the availableAmount and in-process amount is calculated
	 *
	 * @param repairProdOrderItem
	 * @return
	 */
	public static void updateItemStatus(RepairProdOrderItem repairProdOrderItem) {
		ProductionOrderItemManager.updateItemStatus(repairProdOrderItem);
	}

	/**
	 * Logic to calculate not in-plan amount, this method should be invoked
	 * after the availableAmount and in-process amount is calculated
	 *
	 * @param repairProdOrderItem
	 * @return
	 */
	public static void checkSetNotInPlanStatus(RepairProdOrderItem repairProdOrderItem) {
		ProductionOrderItemManager.updateItemStatus(repairProdOrderItem);
	}

	/**
	 * Logic to calculate not in-plan amount, this method should be invoked
	 * after the availableAmount and in-process amount is calculated
	 *
	 * @param repairProdOrderItem
	 * @return
	 */
	public static double calculateNotInPlanAmount(RepairProdOrderItem repairProdOrderItem) {
		return ProductionOrderItemManager.calculateNotInPlanAmount(repairProdOrderItem);
	}

	public static ProductionOrderItemServiceModel copyToProductionServiceModel(RepairProdOrderItemServiceModel repairProdOrderItemServiceModel){
		ProductionOrderItemServiceModel productionOrderItemServiceModel = new ProductionOrderItemServiceModel();
		productionOrderItemServiceModel.setProductionOrderItem(repairProdOrderItemServiceModel.getRepairProdOrderItem());
		if(!ServiceCollectionsHelper.checkNullList(repairProdOrderItemServiceModel.getRepairProdItemReqProposalList())){
			List<ProdOrderItemReqProposalServiceModel> prodOrderItemReqProposalServiceModelList = new ArrayList<>();
			for(RepairProdItemReqProposalServiceModel repairProdItemReqProposalServiceModel: repairProdOrderItemServiceModel.getRepairProdItemReqProposalList()){
				ProdOrderItemReqProposalServiceModel prodOrderItemReqProposalServiceModel =
						RepairProdItemReqProposalManager.copyToProductionServiceModel(repairProdItemReqProposalServiceModel);
				prodOrderItemReqProposalServiceModelList.add(prodOrderItemReqProposalServiceModel);
			}
			productionOrderItemServiceModel.setProdOrderItemReqProposalList(prodOrderItemReqProposalServiceModelList);
		}
		return productionOrderItemServiceModel;
	}

	public static RepairProdOrderItemServiceModel copyToRepairServiceModel(ProductionOrderItemServiceModel productionOrderItemServiceModel) throws InstantiationException, IllegalAccessException {
		RepairProdOrderItemServiceModel repairProdOrderItemServiceModel = new RepairProdOrderItemServiceModel();
		repairProdOrderItemServiceModel.setRepairProdOrderItem(ServiceReflectiveHelper.castCopyModel(productionOrderItemServiceModel.getProductionOrderItem(), RepairProdOrderItem.class));
		if(!ServiceCollectionsHelper.checkNullList(productionOrderItemServiceModel.getProdOrderItemReqProposalList())){
			List<RepairProdItemReqProposalServiceModel> repairProdItemReqProposalServiceModelList = new ArrayList<>();
			for(ProdOrderItemReqProposalServiceModel prodOrderItemReqProposalServiceModel:
					productionOrderItemServiceModel.getProdOrderItemReqProposalList()){
				RepairProdItemReqProposalServiceModel repairProdItemReqProposalServiceModel =
						RepairProdItemReqProposalManager.copyToRepairServiceModel(prodOrderItemReqProposalServiceModel);
				repairProdItemReqProposalServiceModelList.add(repairProdItemReqProposalServiceModel);
			}
			repairProdOrderItemServiceModel.setRepairProdItemReqProposalList(repairProdItemReqProposalServiceModelList);
		}
		return repairProdOrderItemServiceModel;
	}

	/**
	 * Using Async way to update production order post tasks.
	 *
	 * @param repairProdOrderItemServiceModel
	 * @param logonUserUUID
	 * @param organizationUUID
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 * @throws ServiceModuleProxyException
	 */
	public RepairProdOrderItemServiceModel postUpdateRepairProdOrderItemAsyncWrapper(
			RepairProdOrderItemServiceModel repairProdOrderItemServiceModel, String logonUserUUID,
			String organizationUUID)
			throws ServiceEntityConfigureException, MaterialException, ServiceModuleProxyException,
			ServiceComExecuteException {
		ProductionOrderItemServiceModel productionOrderItemServiceModel =
				copyToProductionServiceModel(repairProdOrderItemServiceModel);
		productionOrderItemManager.postUpdateProductionOrderItemAsyncWrapper(productionOrderItemServiceModel,
				logonUserUUID, organizationUUID);
		try {
			repairProdOrderItemServiceModel =
					copyToRepairServiceModel(productionOrderItemServiceModel);
			return repairProdOrderItemServiceModel;
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
			throw new ServiceModuleProxyException(ServiceModuleProxyException.PARA_SYSTEM_WRONG, e.getMessage());
		}
	}

	/**
	 * Entrance method to update production order item
	 *
	 * @param repairProdOrderItemServiceModel
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public void postUpdateRepairProdOrderItem(RepairProdOrderItemServiceModel repairProdOrderItemServiceModel,
			String logonUserUUID, String organizationUUID) throws ServiceEntityConfigureException, MaterialException, ServiceComExecuteException, DocActionException {
		ProductionOrderItemServiceModel productionOrderItemServiceModel =
				copyToProductionServiceModel(repairProdOrderItemServiceModel);
		productionOrderItemManager.postUpdateProductionOrderItem(productionOrderItemServiceModel,
				logonUserUUID, organizationUUID);
	}

	/**
	 * Core Logic to calculate production order item or req item pick status by calculating its sub picking material item
	 *
	 * @param prodPickingMaterialItemList: all the sub picking material item list needes to be considered.
	 * @return picking status
	 */
	public static int calItemPickStatus(List<ServiceEntityNode> prodPickingMaterialItemList) {
		return ProductionOrderItemManager.calItemPickStatus(prodPickingMaterialItemList);
	}

	/**
	 * Get all relative picking material item list by item UUID
	 *
	 * @param itemUUID
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getRefPickingMatItemList(String itemUUID, String client)
			throws ServiceEntityConfigureException {
		return productionOrderItemManager.getRefPickingMatItemList(itemUUID, client);
	}

	/**
	 * Entrance Method to get all the available reserved stock list
	 *
	 * @return
	 */
	public List<ServiceEntityNode> getInStockItemListBatch(String baseUUID, String client) throws ServiceEntityConfigureException {
		return productionOrderItemManager.getInStockItemListBatch(baseUUID, client);
	}

	/**
	 * Core Logic to refresh / update key information for production order item or req item models by calculating the sub picking material item
	 *
	 * @param prodItemRequestUnionTemplate:    raw input item model
	 * @param prodPickingRefMaterialItemList:  all the sub picking materia item list needs to be considered
	 * @param allPickingExtendAmountModelList: all the possible picking extend amount model
	 * @return refreshed item model
	 */
	public ProdItemRequestUnionTemplate refreshItemInfoByPickingItemCore(ProdItemRequestUnionTemplate prodItemRequestUnionTemplate,
			List<ProdPickingExtendAmountModel> allPickingExtendAmountModelList,
			List<ServiceEntityNode> prodPickingRefMaterialItemList) throws MaterialException, ServiceEntityConfigureException, ServiceComExecuteException {
		return productionOrderItemManager.refreshItemInfoByPickingItemCore(prodItemRequestUnionTemplate,
				allPickingExtendAmountModelList, prodPickingRefMaterialItemList);
	}

	/**
	 * Unify all the storage core unit list to same unit
	 * @param storageCoreUnitList
	 * @param refTemplateMaterialSKU
	 * @return result unify refUnit UUID
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public String unifyStorageCoreUnit(List<StorageCoreUnit> storageCoreUnitList, MaterialStockKeepUnit refTemplateMaterialSKU)
			throws ServiceEntityConfigureException, MaterialException {
		return productionOrderItemManager.unifyStorageCoreUnit(storageCoreUnitList, refTemplateMaterialSKU);
	}

	public RepairProdOrderItem copyUnionTemplateToRepairProdOrderItem(ProdItemRequestUnionTemplate prodItemRequestUnionTemplate, RepairProdOrderItem repairProdOrderItem) {
		if (repairProdOrderItem == null || prodItemRequestUnionTemplate == null) {
			return repairProdOrderItem;
		}
		docFlowProxy.copyDocMatItemMutual(prodItemRequestUnionTemplate, repairProdOrderItem, true);
		repairProdOrderItem.setPickStatus(prodItemRequestUnionTemplate.getPickStatus());
		repairProdOrderItem.setPickedAmount(prodItemRequestUnionTemplate.getPickedAmount());
		repairProdOrderItem.setToPickAmount(prodItemRequestUnionTemplate.getToPickAmount());
		repairProdOrderItem.setInProcessAmount(prodItemRequestUnionTemplate.getInProcessAmount());
		repairProdOrderItem.setSuppliedAmount(prodItemRequestUnionTemplate.getSuppliedAmount());
		repairProdOrderItem.setInStockAmount(prodItemRequestUnionTemplate.getInStockAmount());
		repairProdOrderItem.setAvailableAmount(prodItemRequestUnionTemplate.getAvailableAmount());
		return repairProdOrderItem;
	}

	public ProdItemRequestUnionTemplate copyRepairProdOrderItemToUnionTemplate(RepairProdOrderItem repairProdOrderItem,
			ProdItemRequestUnionTemplate prodItemRequestUnionTemplate) {
		return productionOrderItemManager.copyProductionOrderItemToUnionTemplate(repairProdOrderItem,
				prodItemRequestUnionTemplate);

	}

	public ProdItemReqProposalTemplate copyUnionTemplateToOrderItemReqProposal(
			ProdItemRequestUnionTemplate prodItemRequestUnionTemplate, ProdItemReqProposalTemplate prodItemReqProposalTemplate) {
		return productionOrderItemManager.copyUnionTemplateToOrderItemReqProposal(prodItemRequestUnionTemplate,
				prodItemReqProposalTemplate);
	}

	public ProdItemRequestUnionTemplate copyOrderItemReqProposalToUnionTemplate(
			ProdItemReqProposalTemplate prodItemReqProposalTemplate, ProdItemRequestUnionTemplate prodItemRequestUnionTemplate) {
		return productionOrderItemManager.copyOrderItemReqProposalToUnionTemplate(prodItemReqProposalTemplate,
				prodItemRequestUnionTemplate);
	}

	/**
	 * Refresh Production order item key amount and status information by calculating all the sub picking item
	 * In this way, all the picking item status should be calculated.
	 *
	 * @param repairProdOrderItemServiceModel
	 * @param warehouseUUIDList               : all warehouse UUID list
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public List<ProdPickingExtendAmountModel> refreshOrderItemFromPickingItem(RepairProdOrderItemServiceModel repairProdOrderItemServiceModel,
			List<String> warehouseUUIDList) throws ServiceEntityConfigureException, MaterialException, ServiceComExecuteException, DocActionException {
		ProductionOrderItemServiceModel productionOrderItemServiceModel =
				copyToProductionServiceModel(repairProdOrderItemServiceModel);
		return productionOrderItemManager.refreshOrderItemFromPickingItem(productionOrderItemServiceModel,
				warehouseUUIDList);
	}

	/**
	 * Merge and summary accountable amount from multiple doc mat item instances
	 *
	 * @param rawDocMatItemList
	 * @return
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 */
	public StorageCoreUnit mergeDocItemMaterialSKU(List<ServiceEntityNode> rawDocMatItemList)
			throws MaterialException, ServiceEntityConfigureException {
		return productionOrderItemManager.mergeDocItemMaterialSKU(rawDocMatItemList);
	}

	/**
	 * [Internal method] Convert from UI model to se model:repairProdOrderItem
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToRepairProdOrderItem(RepairProdOrderItemUIModel repairProdOrderItemUIModel, RepairProdOrderItem rawEntity) {
		productionOrderItemManager.convUIToProductionOrderItem(repairProdOrderItemUIModel, rawEntity);
	}

	public void convRepairProdOrderItemToUI(RepairProdOrderItem repairProdOrderItem,
			RepairProdOrderItemUIModel repairProdOrderItemUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException, ServiceEntityConfigureException {
		productionOrderItemManager.convProductionOrderItemToUI(repairProdOrderItem, repairProdOrderItemUIModel, logonInfo);
	}

	public void convRepairProdOrderToItemUI(RepairProdOrder productionOrder, RepairProdOrderItemUIModel repairProdOrderItemUIModel)
			throws MaterialException, ServiceEntityConfigureException {
		convRepairProdOrderToItemUI(productionOrder, repairProdOrderItemUIModel, null);
	}

	public void convDocumentToItemReqProposalUI(ServiceEntityNode documentContent,
			RepairProdItemReqProposalUIModel repairProdItemReqProposalUIModel) {
		if (documentContent != null) {
			repairProdItemReqProposalUIModel.setRefDocumentId(documentContent.getId());
		}
	}

	public void convRepairProdOrderToItemUI(RepairProdOrder repairProdOrder, RepairProdOrderItemUIModel repairProdOrderItemUIModel,
			LogonInfo logonInfo) throws MaterialException, ServiceEntityConfigureException {
		if (repairProdOrder != null) {
			productionOrderItemManager.convProductionOrderToItemUI(repairProdOrder, repairProdOrderItemUIModel);
		}
	}

	public void convOrderMaterialSKUToItemUI(MaterialStockKeepUnit materialStockKeepUnit,
			RepairProdOrderItemUIModel repairProdOrderItemUIModel) {
		if (materialStockKeepUnit != null) {
			repairProdOrderItemUIModel.setParentDocMaterialSKUId(materialStockKeepUnit.getId());
			repairProdOrderItemUIModel.setParentDocMaterialSKUName(materialStockKeepUnit.getName());
		}
	}

	public void convItemMaterialSKUToUI(MaterialStockKeepUnit itemMaterialSKU,
			RepairProdOrderItemUIModel repairProdOrderItemUIModel) {
		convItemMaterialSKUToUI(itemMaterialSKU, repairProdOrderItemUIModel, null);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convItemMaterialSKUToUI(MaterialStockKeepUnit itemMaterialSKU,
			RepairProdOrderItemUIModel repairProdOrderItemUIModel, LogonInfo logonInfo) {
		if (itemMaterialSKU != null) {
			repairProdOrderItemUIModel.setRefMaterialSKUName(itemMaterialSKU.getName());
			repairProdOrderItemUIModel.setRefMaterialSKUId(itemMaterialSKU.getId());

			repairProdOrderItemUIModel.setPackageStandard(itemMaterialSKU.getPackageStandard());
			repairProdOrderItemUIModel.setSupplyType(itemMaterialSKU.getSupplyType());
			if (logonInfo != null) {
				try {
					Map<Integer, String> supplyTypeMap = materialStockKeepUnitManager.initSupplyTypeMap(logonInfo.getLanguageCode());
					repairProdOrderItemUIModel.setSupplyTypeValue(supplyTypeMap.get(itemMaterialSKU.getSupplyType()));
				} catch (ServiceEntityInstallationException e) {
					// log error and continue
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
				}
			}
		}
	}

	public void convRepairProdOrderToProposalUI(RepairProdOrder productionOrder,
			RepairProdItemReqProposalUIModel repairProdItemReqProposalUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException, ServiceEntityConfigureException {
		repairProdItemReqProposalUIModel.setOrderId(productionOrder.getId());
		repairProdItemReqProposalUIModel.setOrderStatus(productionOrder.getStatus());
		try {
			if (logonInfo != null) {
				Map<Integer, String> statusMap = repairProdOrderManager.initStatusMap(logonInfo.getLanguageCode());
				repairProdItemReqProposalUIModel.setOrderStatusValue(statusMap.get(productionOrder.getStatus()));
			}
		} catch (ServiceEntityInstallationException e) {
			// log error and continue
			logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, IDocumentNodeFieldConstant.STATUS), e);
		}
	}

	public void convRepairProdOrderItemToProposalUI(RepairProdOrderItem repairProdOrderItemUIModel,
													RepairProdItemReqProposalUIModel repairProdItemReqProposalUIModel)
			throws ServiceEntityInstallationException, ServiceEntityConfigureException {
		repairProdItemReqProposalUIModel.setParentItemId(repairProdOrderItemUIModel.getId());
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convWarehouseToItemReqProposalUI(Warehouse warehouse,
			RepairProdItemReqProposalUIModel repairProdItemReqProposalUIModel) {
		if (warehouse != null) {
			repairProdItemReqProposalUIModel.setRefWarehouseId(warehouse.getId());
			repairProdItemReqProposalUIModel.setRefWarehouseName(warehouse.getName());
		}
	}

	public void convRepairProdItemReqProposalToUI(RepairProdItemReqProposal repairProdItemReqProposal,
			RepairProdItemReqProposalUIModel repairProdItemReqProposalUIModel)
			throws ServiceEntityInstallationException, ServiceEntityConfigureException {
		convRepairProdItemReqProposalToUI(repairProdItemReqProposal, repairProdItemReqProposalUIModel, null);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 * @throws ServiceEntityConfigureException
	 */
	public void convRepairProdItemReqProposalToUI(RepairProdItemReqProposal repairProdItemReqProposal,
												  RepairProdItemReqProposalUIModel repairProdItemReqProposalUIModel,
												  LogonInfo logonInfo)
			throws ServiceEntityInstallationException, ServiceEntityConfigureException {
		productionOrderItemManager.convProdOrderItemReqProposalToUI(repairProdItemReqProposal,
				repairProdItemReqProposalUIModel, logonInfo);
	}

	/**
	 * [Internal method] Convert from UI model to SE
	 * model:repairProdItemRequirement
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToRepairProdItemReqProposal(RepairProdItemReqProposalUIModel repairProdItemReqProposalUIModel,
												  RepairProdItemReqProposal rawEntity) {
		productionOrderItemManager.convUIToProdOrderItemReqProposal(repairProdItemReqProposalUIModel, rawEntity);
	}

	public void convMaterialStockKeepUnitToUI(MaterialStockKeepUnit materialStockKeepUnit,
			RepairProdOrderItemUIModel repairProdOrderItemUIModel) {
		if (materialStockKeepUnit != null) {
			repairProdOrderItemUIModel.setRefMaterialSKUId(materialStockKeepUnit.getId());
			repairProdOrderItemUIModel.setRefMaterialSKUName(materialStockKeepUnit.getName());
		}
	}

	public ServiceDocumentExtendUIModel convRepairProdItemReqProposalToDocExtUIModel(
			RepairProdItemReqProposalUIModel repairProdItemReqProposalUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
		serviceDocumentExtendUIModel.setRefUIModel(repairProdItemReqProposalUIModel);
		docFlowProxy.convDocMatItemUIToDocExtUIModel(repairProdItemReqProposalUIModel, serviceDocumentExtendUIModel, logonInfo,
				IDefDocumentResource.DOCUMENT_TYPE_REPAIRPRODORDERITEM);
		serviceDocumentExtendUIModel.setId(repairProdItemReqProposalUIModel.getId());
		serviceDocumentExtendUIModel.setName(repairProdItemReqProposalUIModel.getRefMaterialSKUName());
		serviceDocumentExtendUIModel.setStatus(repairProdItemReqProposalUIModel.getItemStatus());
		serviceDocumentExtendUIModel.setStatusValue(repairProdItemReqProposalUIModel.getItemStatusValue());
		String referenceDate = repairProdItemReqProposalUIModel.getPlanStartDate();
		serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
		return serviceDocumentExtendUIModel;
	}

	public ServiceDocumentExtendUIModel convRepairProdOrderItemToDocExtUIModel(
			RepairProdOrderItemUIModel repairProdOrderItemUIModel, LogonInfo logonInfo) throws ServiceEntityInstallationException {
		ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
		serviceDocumentExtendUIModel.setRefUIModel(repairProdOrderItemUIModel);
		docFlowProxy.convDocMatItemUIToDocExtUIModel(repairProdOrderItemUIModel, serviceDocumentExtendUIModel, logonInfo,
				IDefDocumentResource.DOCUMENT_TYPE_REPAIRPRODORDERITEM);
		serviceDocumentExtendUIModel.setId(repairProdOrderItemUIModel.getId());
		serviceDocumentExtendUIModel.setName(repairProdOrderItemUIModel.getRefMaterialSKUName());
		serviceDocumentExtendUIModel.setStatus(repairProdOrderItemUIModel.getItemStatus());
		serviceDocumentExtendUIModel.setStatusValue(repairProdOrderItemUIModel.getItemStatusValue());
		String referenceDate = repairProdOrderItemUIModel.getPlanStartDate();
		serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
		return serviceDocumentExtendUIModel;
	}

	/**
	 * Utility method: filter repairProdOrderItemUIModelServiceModel by reference BOM
	 * Item UUID
	 *
	 * @param repairProdOrderItemServiceModelList
	 * @return
	 */
	public static RepairProdOrderItemServiceModel filterRepairProdOrderItemByBOMItemUUID(String refBOMItemUUID,
			List<RepairProdOrderItemServiceModel> repairProdOrderItemServiceModelList) {
		if (ServiceCollectionsHelper.checkNullList(repairProdOrderItemServiceModelList)) {
			return null;
		}
		if (ServiceEntityStringHelper.checkNullString(refBOMItemUUID)) {
			return null;
		}
		for (RepairProdOrderItemServiceModel repairProdOrderItemServiceModel : repairProdOrderItemServiceModelList) {
			if (refBOMItemUUID.equals(repairProdOrderItemServiceModel.getRepairProdOrderItem().getRefBOMItemUUID())) {
				return repairProdOrderItemServiceModel;
			}
		}
		return null;
	}

	/**
	 * Utility method to merge proposal into parent item service model
	 *
	 * @param repairProdOrderItemServiceModel
	 * @param repairProdItemReqProposal
	 */
	public static void mergeProposalIntoItemServiceModel(RepairProdOrderItemServiceModel repairProdOrderItemServiceModel,
			RepairProdItemReqProposal repairProdItemReqProposal) {
		ProductionOrderItemServiceModel productionOrderItemServiceModel =
				copyToProductionServiceModel(repairProdOrderItemServiceModel);
		ProductionOrderItemManager.mergeProposalIntoItemServiceModel(productionOrderItemServiceModel, repairProdItemReqProposal);
	}

	public static List<RepairProdItemReqProposalServiceModel> optimizeProposalListByStore(
			List<RepairProdItemReqProposalServiceModel> repairProdItemReqProposalServiceModelList) {
		if (ServiceCollectionsHelper.checkNullList(repairProdItemReqProposalServiceModelList)) {
			return null;
		}
		List<RepairProdItemReqProposalServiceModel> headerList = new ArrayList<>();
		List<RepairProdItemReqProposalServiceModel> rearList = new ArrayList<>();
		for (RepairProdItemReqProposalServiceModel repairProdItemReqProposalServiceModel :
				repairProdItemReqProposalServiceModelList) {
			int documentType = repairProdItemReqProposalServiceModel.getRepairProdItemReqProposal().getDocumentType();
			if (documentType == IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM
					|| documentType == IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY) {
				headerList.add(repairProdItemReqProposalServiceModel);
			} else {
				rearList.add(repairProdItemReqProposalServiceModel);
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
		if (RepairProdOrderItem.NODENAME.equals(seNode.getNodeName())) {
			RepairProdOrderItem repairProdOrderItem = (RepairProdOrderItem) seNode;
			try {
				RepairProdOrderItemUIModel repairProdOrderItemUIModel = (RepairProdOrderItemUIModel) repairProdOrderManager
						.genUIModelFromUIModelExtension(RepairProdOrderItemUIModel.class,
								repairProdOrderItemServiceUIModelExtension.genUIModelExtensionUnion().get(0), repairProdOrderItem,
								logonInfo, null);
				ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = convRepairProdOrderItemToDocExtUIModel(
						repairProdOrderItemUIModel, logonInfo);
				return serviceDocumentExtendUIModel;
			} catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, RepairProdOrderItem.NODENAME));
			}
		}
		if (RepairProdItemReqProposal.NODENAME.equals(seNode.getNodeName())) {
			RepairProdItemReqProposal repairProdItemReqProposal = (RepairProdItemReqProposal) seNode;
			try {
				RepairProdItemReqProposalUIModel repairProdItemReqProposalUIModel = (RepairProdItemReqProposalUIModel) repairProdOrderManager
						.genUIModelFromUIModelExtension(RepairProdItemReqProposalUIModel.class,
								repairProdItemReqProposalServiceUIModelExtension.genUIModelExtensionUnion().get(0),
								repairProdItemReqProposal, logonInfo, null);
				ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = convRepairProdItemReqProposalToDocExtUIModel(
						repairProdItemReqProposalUIModel, logonInfo);
				return serviceDocumentExtendUIModel;
			} catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, RepairProdOrderItem.NODENAME));
			}
		}
		return null;
	}

}
