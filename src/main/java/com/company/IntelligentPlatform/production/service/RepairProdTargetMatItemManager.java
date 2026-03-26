package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.finance.service.FinanceAccountValueProxyException;
import com.company.IntelligentPlatform.finance.service.SystemResourceException;
import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.service.InboundDeliveryException;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryException;
import com.company.IntelligentPlatform.production.dto.*;
import com.company.IntelligentPlatform.production.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.logistics.model.StoreAvailableStoreItemResponse;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreItemManager;
import com.company.IntelligentPlatform.common.model.MatDecisionValueSetting;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceModuleCloneService;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.util.*;

@Service
public class RepairProdTargetMatItemManager {

	@Autowired
	protected RepairProdOrderManager repairProdOrderManager;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	@Autowired
	protected ProdOrderTargetMatItemManager prodOrderTargetMatItemManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected RepairProdTargetMatItemServiceUIModelExtension repairProdTargetMatItemServiceUIModelExtension;

	@Autowired
	protected MatDecisionValueSettingManager matDecisionValueSettingManager;

	@Autowired
	protected OrganizationManager organizationManager;

	@Autowired
	protected ProdPickingOrderManager prodPickingOrderManager;

	@Autowired
	protected RegisteredProductManager registeredProductManager;

	@Autowired
	protected ProductionPlanManager productionPlanManager;

	@Autowired
	protected WarehouseStoreItemManager warehouseStoreItemManager;

	@Autowired
	protected RepairProdTargetItemToCrossInboundProxy repairProdTargetItemToCrossInboundProxy;

	@Autowired
	protected RepairProdTargetItemToCrossQuaInspectProxy repairProdTargetItemToCrossQuaInspectProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	private final Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

	protected Logger logger = LoggerFactory.getLogger(RepairProdTargetMatItemManager.class);

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected SplitMatItemProxy splitMatItemProxy;

	public static final String METHOD_ConvRepairProdTargetMatItemToUI = "convRepairProdTargetMatItemToUI";

	public static final String METHOD_ConvUIToRepairProdTargetMatItem = "convUIToRepairProdTargetMatItem";

	public static final String METHOD_ConvRepairProdOrderToTargetItemUI = "convRepairProdOrderToTargetItemUI";

	public static final String METHOD_ConvRepairProdTarSubItemToUI = "convRepairProdTarSubItemToUI";

	public static final String METHOD_ConvUIToRepairProdTarSubItem = "convUIToRepairProdTarSubItem";

	public static final String METHOD_ConvBillOfOrderItemToUI = "convBillOfOrderItemToUI";

	public static final String METHOD_ConvMaterialSKUToTargetItemUI = "convMaterialSKUToTargetItemUI";

	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), RepairProdOrder.NODENAME,
						request.getUuid(), RepairProdTargetMatItem.NODENAME, repairProdOrderManager);
		docPageHeaderInputPara.setGenBaseModelList(new DocPageHeaderModelProxy.GenBaseModelList<RepairProdOrder>() {
			@Override
			public List<PageHeaderModel> execute(RepairProdOrder repairProdOrder) throws ServiceEntityConfigureException {
				// How to get the base page header model list
				return repairProdOrderManager.getPageHeaderModelList(repairProdOrder, client);
			}
		});
		docPageHeaderInputPara.setGenHomePageModel(new DocPageHeaderModelProxy.GenHomePageModel<RepairProdTargetMatItem>() {
			@Override
			public PageHeaderModel execute(RepairProdTargetMatItem repairProdTargetMatItem, PageHeaderModel pageHeaderModel) throws ServiceEntityConfigureException {
				// How to render current page header
				MaterialStockKeepUnit materialStockKeepUnit = null;
				try {
					materialStockKeepUnit = materialStockKeepUnitManager
							.getMaterialSKUWrapper(repairProdTargetMatItem.getRefMaterialSKUUUID(), repairProdTargetMatItem.getClient(), null);
				} catch (ServiceComExecuteException e) {
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
				}
				if (materialStockKeepUnit != null) {
					pageHeaderModel.setHeaderName(MaterialStockKeepUnitManager.getMaterialIdentifier(materialStockKeepUnit, false));
				}
				return pageHeaderModel;
			}
		});
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);

	}

	public Map<Integer, String> initStatusMap(String languageCode) throws ServiceEntityInstallationException {
		String resourcePath = RepairProdTargetMatItemUIModel.class.getResource("").getPath() +
				"RepairProdTargetMatItem_status";
		return ServiceLanHelper
				.initDefLanguageMapResource(languageCode, this.statusMapLan, resourcePath);
	}

	public String getTargetMatItemIndentifier(RepairProdTargetMatItem repairProdTargetMatItem, LogonInfo logonInfo)
			throws ServiceEntityConfigureException, ServiceModuleProxyException {
		return prodOrderTargetMatItemManager.getTargetMatItemIndentifier(repairProdTargetMatItem, logonInfo);
	}

	public void convRepairProdOrderToTargetItemUI(RepairProdOrder repairProdOrder,
			RepairProdTargetMatItemUIModel repairProdTargetMatItemUIModel) {
		convRepairProdOrderToTargetItemUI(repairProdOrder, repairProdTargetMatItemUIModel, null);
	}

	public void convRepairProdOrderToTargetItemUI(RepairProdOrder repairProdOrder,
			RepairProdTargetMatItemUIModel repairProdTargetMatItemUIModel, LogonInfo logonInfo) {
		if (repairProdOrder != null && repairProdTargetMatItemUIModel != null) {
			repairProdTargetMatItemUIModel.setParentDocId(repairProdOrder.getId());
			repairProdTargetMatItemUIModel.setParentDocStatus(repairProdOrder.getStatus());
		}
	}

	/**
	 * Core Logic to set target item status to [Production Done]
	 *
	 * @param repairProdTargetMatItem
	 * @param logonUserUUID
	 * @param organizationUUID
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 * @throws ProdOrderTargetItemException
	 */
	public synchronized void setRepairDone(RepairProdTargetMatItem repairProdTargetMatItem, String logonUserUUID,
						  String organizationUUID)
			throws MaterialException, ServiceEntityConfigureException, ProdOrderTargetItemException {
		repairProdTargetMatItem.setItemStatus(RepairProdTargetMatItem.STATUS_DONE_PRODUCTION);
		repairProdOrderManager.updateSENode(repairProdTargetMatItem, logonUserUUID, organizationUUID);
	}

	/**
	 * Core Logic to split target item and set part production done status
	 *
	 * @param splitMatItemModel
	 * @param repairProdTargetMatItem
	 * @param logonUserUUID
	 * @param organizationUUID
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 * @throws ProdOrderTargetItemException
	 */
	public RepairProdTargetMatItem splitProductionDoneService(SplitMatItemModel splitMatItemModel,
			RepairProdTargetMatItem repairProdTargetMatItem, String logonUserUUID, String organizationUUID)
			throws ServiceEntityConfigureException, MaterialException, ProdOrderTargetItemException, ServiceModuleProxyException,
			SplitMatItemException {
		/*
		 * [Step1] Pre check split amount: if 0, then return
		 */
		if (splitMatItemModel.getSplitAmount() == 0) {
			throw new ProdOrderTargetItemException(ProdOrderTargetItemException.TYPE_NO_ITEMPRODDONE);
		}
		if (splitMatItemModel.getSplitAmount() < 0) {
			throw new SplitMatItemException(SplitMatItemException.PARA_SPLIT_NOMINUS, splitMatItemModel.getSplitAmount());
		}
		/*
		 * [Step2] Split top target mat item instance, and update the target amount, set done new splited target mat item
		 */
		RepairProdTargetMatItem repairProdTargetMatItemBack = (RepairProdTargetMatItem) repairProdTargetMatItem.clone();
		SplitMatItemProxy.SplitResult splitResult = splitMatItemProxy.splitDefMatItemService(splitMatItemModel,repairProdTargetMatItem);
		if(splitResult.getLeftRatio() > 0){
			// In case stll something left
			List<ServiceEntityNode> splitResultList = splitResult.getMergeResult();
			splitResultList.remove(repairProdTargetMatItem);
			RepairProdTargetMatItem newDoneTargetMatItem = (RepairProdTargetMatItem) splitResultList.get(0);
			if (newDoneTargetMatItem != null) {
				//setProductionDone(newDoneTargetMatItem, logonUserUUID, organizationUUID);
				newDoneTargetMatItem.setItemStatus(RepairProdTargetMatItem.STATUS_DONE_PRODUCTION);
				repairProdOrderManager.updateSENode(newDoneTargetMatItem, logonUserUUID, organizationUUID);
			}
			RepairProdTargetMatItemServiceModel repairProdTargetMatItemServiceModel = (RepairProdTargetMatItemServiceModel) repairProdOrderManager
					.loadServiceModule(RepairProdTargetMatItemServiceModel.class, repairProdTargetMatItem,
							repairProdTargetMatItemServiceUIModelExtension);
			/*
			 * [Step3] Deep clone the sub mat items and changed by split ratio
			 */
			RepairProdTargetMatItemServiceModel newTargetMatItemServiceModel = (RepairProdTargetMatItemServiceModel) ServiceModuleCloneService
					.deepCloneServiceModule(repairProdTargetMatItemServiceModel, true);
			newTargetMatItemServiceModel.setRepairProdTargetMatItem(newDoneTargetMatItem);
			this.updateSubItemArrayWithRatio(newTargetMatItemServiceModel, splitResult.getSplitRatio());
			repairProdOrderManager.updateServiceModuleWithDelete(RepairProdTargetMatItemServiceModel.class, newTargetMatItemServiceModel, logonUserUUID, organizationUUID);
			// update left target mat item
			this.updateSubItemArrayWithRatio(repairProdTargetMatItemServiceModel, splitResult.getLeftRatio());
			repairProdOrderManager.updateServiceModuleWithDelete(RepairProdTargetMatItemServiceModel.class, repairProdTargetMatItemServiceModel, logonUserUUID, organizationUUID);
			return newDoneTargetMatItem;
		}else{
			// In case all is done
			repairProdTargetMatItem.setItemStatus(RepairProdTargetMatItem.STATUS_DONE_PRODUCTION);
			repairProdTargetMatItem.setAmount(repairProdTargetMatItemBack.getAmount());
			repairProdTargetMatItem.setRefUnitUUID(repairProdTargetMatItemBack.getRefUnitUUID());
			repairProdOrderManager.updateSENode(repairProdTargetMatItem, logonUserUUID, organizationUUID);
			return repairProdTargetMatItem;
		}
	}

	private void updateSubItemArrayWithRatio(RepairProdTargetMatItemServiceModel repairProdTargetMatItemServiceModel, double ratio){
		List<ServiceEntityNode> repairProdTarSubItemList = repairProdTargetMatItemServiceModel.getRepairProdTarSubItemList();
		if(ServiceCollectionsHelper.checkNullList(repairProdTarSubItemList)){
			return;
		}
		for(ServiceEntityNode serviceEntityNode:repairProdTarSubItemList){
			RepairProdTarSubItem repairProdTarSubItem = (RepairProdTarSubItem) serviceEntityNode;
			repairProdTarSubItem.setAmount(repairProdTarSubItem.getAmount() * ratio);
		}
	}

	/**
	 * Logic to pre-check before standard update/save action
	 *
	 * @param repairProdTargetMatItem
	 * @throws ServiceEntityConfigureException
	 * @throws ProdOrderTargetItemException
	 * @throws RegisteredProductException
	 */
	public void preCheckUpdate(RepairProdTargetMatItem repairProdTargetMatItem)
			throws ServiceEntityConfigureException, ProdOrderTargetItemException, RegisteredProductException {
		prodOrderTargetMatItemManager.preCheckUpdate(repairProdTargetMatItem);
	}

	/**
	 * Core Logic to set target item status to [in process]
	 *
	 * @param repairProdTargetMatItem
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 * @throws ProdOrderTargetItemException
	 */
	public void setInProcessStatus(RepairProdTargetMatItem repairProdTargetMatItem){
		prodOrderTargetMatItemManager.setInProcessStatus(repairProdTargetMatItem);
	}

	public static void sortTargetMatItemListByProcessIndex(List<RepairProdTargetMatItemServiceUIModel> repairProdTargetMatItemServiceUIModelList){
		if (!ServiceCollectionsHelper.checkNullList(repairProdTargetMatItemServiceUIModelList)){
			Collections.sort(repairProdTargetMatItemServiceUIModelList, new Comparator<RepairProdTargetMatItemServiceUIModel>() {
				public int compare(RepairProdTargetMatItemServiceUIModel target1, RepairProdTargetMatItemServiceUIModel target2) {
					int process1 = target1.getRepairProdTargetMatItemUIModel().getProcessIndex();
					int process2 = target2.getRepairProdTargetMatItemUIModel().getProcessIndex();
					return process1 - process2;
				}
			});
		}
	}

	/**
	 * Core Logic to set target item status to [in process]
	 *
	 * @param repairProdTargetMatItemList
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 * @throws ProdOrderTargetItemException
	 */
	public void setInProcessStatusBatch(List<ServiceEntityNode> repairProdTargetMatItemList, String logonUserUUID, String organizationUUID)
			throws ServiceEntityConfigureException {
		/*
		 * [Step1] Update status
		 */
		if(!ServiceCollectionsHelper.checkNullList(repairProdTargetMatItemList)){
			for(ServiceEntityNode seNode:repairProdTargetMatItemList){
				RepairProdTargetMatItem repairProdTargetMatItem = (RepairProdTargetMatItem) seNode;
				setInProcessStatus(repairProdTargetMatItem);
			}
		}
		repairProdOrderManager.updateSENodeList(repairProdTargetMatItemList, logonUserUUID, organizationUUID);
	}

	/**
	 * Core Logic to set target item status to [Cancel]
	 *
	 * @param repairProdTargetMatItem
	 * @param logonUserUUID
	 * @param organizationUUID
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 * @throws ProdOrderTargetItemException
	 */
	public void setCancelStatus(RepairProdTargetMatItem repairProdTargetMatItem, String logonUserUUID, String organizationUUID)
			throws MaterialException, ServiceEntityConfigureException, ProdOrderTargetItemException {
		/*
		 * [Step1] Update status
		 */
		repairProdTargetMatItem.setItemStatus(RepairProdTargetMatItem.STATUS_CANCELED);
		/*
		 * [Step2] Generate Registered Product and update
		 */
		repairProdOrderManager.updateSENode(repairProdTargetMatItem, logonUserUUID, organizationUUID);
	}

	/**
	 * Logic to check if the pre-set serial id is unique for this template SKU
	 *
	 * @param repairProdTargetMatItem
	 * @param tempMaterialSKU
	 */
	public boolean checkDuplicateSerialId(RepairProdTargetMatItem repairProdTargetMatItem, MaterialStockKeepUnit tempMaterialSKU)
			throws RegisteredProductException, ServiceEntityConfigureException, ProdOrderTargetItemException {
	    return prodOrderTargetMatItemManager.checkDuplicateSerialId(repairProdTargetMatItem, tempMaterialSKU);
	}

	public List<ServiceEntityNode> getTargetItemListByRefSerialId(String serialId, String tempMaterialUUID, String client)
			throws ServiceEntityConfigureException {
		List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
		ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure(serialId, RepairProdTargetMatItem.FEILD_REF_SERIALID);
		keyList.add(key1);
		if (!ServiceEntityStringHelper.checkNullString(tempMaterialUUID)) {
			ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure(tempMaterialUUID, "refMaterialSKUUUID");
			keyList.add(key2);
		}
		List<ServiceEntityNode> rawList = repairProdOrderManager
				.getEntityNodeListByKeyList(keyList, RepairProdTargetMatItem.NODENAME, client, null);
		if (ServiceCollectionsHelper.checkNullList(rawList)) {
			return null;
		}
		return rawList;
	}

	/**
	 * Entrance to create inbound delivery & inbound item list for purchase
	 * contract and its' material item list
	 *
	 * @param repairProdTargetMatItemList
	 * @throws ServiceEntityConfigureException
	 * @throws InboundDeliveryException
	 * @throws LogonInfoException
	 * @throws OutboundDeliveryException
	 * @throws FinanceAccountValueProxyException
	 * @throws SystemResourceException
	 * @throws MaterialException
	 * @throws ServiceEntityInstallationException
	 * @throws NodeNotFoundException
	 * @throws SearchConfigureException
	 * @throws ServiceModuleProxyException
	 */
	@Transactional
	public synchronized void createInboundDeliveryBatch(RepairProdOrderServiceModel repairProdOrderServiceModel,
			List<ServiceEntityNode> repairProdTargetMatItemList, DeliveryMatItemBatchGenRequest genRequest, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, SearchConfigureException, NodeNotFoundException,
            ServiceEntityInstallationException, ServiceModuleProxyException, ProductionOrderException, AuthorizationException, LogonInfoException, DocActionException {
		String organizationUUID = logonInfo.getResOrgUUID();
		String logonUserUUID = logonInfo.getRefUserUUID();
		if (ServiceCollectionsHelper.checkNullList(repairProdOrderServiceModel.getRepairProdTargetMatItemList())) {
			throw new ProductionOrderException(ProductionOrderException.PARA_NO_TARGETITEM);
		}
		if (ServiceCollectionsHelper.checkNullList(repairProdTargetMatItemList)) {
			throw new ProductionOrderException(ProductionOrderException.PARA_NO_TARGETITEM);
		}
		/*
		 * [Step1] Raw data preparation, such as prepare all material S list
		 */
		List<ServiceEntityNode> allRepairProdTargetMatItemList = new ArrayList<>();
		repairProdOrderServiceModel.getRepairProdTargetMatItemList().forEach(repairProdOrderMaItemServiceModel -> {
			allRepairProdTargetMatItemList.add(repairProdOrderMaItemServiceModel.getRepairProdTargetMatItem());
		});
		List<ServiceEntityNode> rawMaterialSKUList = docFlowProxy
				.getRefMaterialSKUList(repairProdTargetMatItemList, docMatItemNode -> {
					RepairProdTargetMatItem repairProdOrderMaterialItem = (RepairProdTargetMatItem) docMatItemNode;
					if (repairProdOrderMaterialItem.getItemStatus() != RepairProdTargetMatItem.STATUS_DONE_PRODUCTION) {
						return false;
					}
					if (repairProdOrderMaterialItem.getNextDocType() != 0 && !ServiceEntityStringHelper
							.checkNullString(repairProdOrderMaterialItem.getNextDocMatItemUUID())) {
						return false;
					} else {
						return true;
					}
				}, logonInfo.getClient());
		if (ServiceCollectionsHelper.checkNullList(rawMaterialSKUList)) {
			return;
		}
		/*
		 * [Step2] Filter each Material, check if need quality check or can be
		 * generate in-bound directly
		 */
		List<ServiceEntityNode> prodTargetItemForInboundList = docFlowProxy
				.splitDocItemListByMaterialQualityFlag(repairProdTargetMatItemList, rawMaterialSKUList,
						StandardSwitchProxy.SWITCH_OFF);
		List<ServiceEntityNode> prodTargetItemForQualityList = docFlowProxy
				.splitDocItemListByMaterialQualityFlag(repairProdTargetMatItemList, rawMaterialSKUList, StandardSwitchProxy.SWITCH_ON);

		/*
		 * [Step3]: trying to get the Existed & Proper in-bound delivery item
		 * lists for this purchase contract.
		 */
		RepairProdOrder repairProdOrder = repairProdOrderServiceModel.getRepairProdOrder();
		if (!ServiceCollectionsHelper.checkNullList(prodTargetItemForInboundList)) {
			repairProdTargetItemToCrossInboundProxy
					.createDirectInboundBatch(repairProdOrder, allRepairProdTargetMatItemList, prodTargetItemForInboundList,
							rawMaterialSKUList, genRequest, logonInfo);
			// Important: replace items back to allRepairProdTargetMatItemList
			for(ServiceEntityNode seNode: prodTargetItemForInboundList){
				ServiceEntityNode oldSeNode = ServiceCollectionsHelper.filterSENodeOnline(seNode.getUuid(), allRepairProdTargetMatItemList);
				if(oldSeNode != null){
					allRepairProdTargetMatItemList.remove(oldSeNode);
					allRepairProdTargetMatItemList.add(seNode);
				}
			}
		}
		if (!ServiceCollectionsHelper.checkNullList(prodTargetItemForQualityList)) {
			repairProdTargetItemToCrossQuaInspectProxy
					.createQualityInspectOrderBatch(repairProdOrder, allRepairProdTargetMatItemList, prodTargetItemForQualityList,
							rawMaterialSKUList, genRequest, logonInfo);
			// Important: replace items back to allRepairProdTargetMatItemList
			for(ServiceEntityNode seNode: prodTargetItemForQualityList){
				ServiceEntityNode oldSeNode = ServiceCollectionsHelper.filterSENodeOnline(seNode.getUuid(), allRepairProdTargetMatItemList);
				if(oldSeNode != null){
					allRepairProdTargetMatItemList.remove(oldSeNode);
					allRepairProdTargetMatItemList.add(seNode);
				}
			}
		}

		/*
		 * [Step4]: trying to set order complete automatically if all the target mat item is set to done
		 */
		if(checkAllProdTargetItemCompleted(allRepairProdTargetMatItemList)){
			// complete order automatically
			repairProdOrderManager.setOrderCompleteCore(repairProdOrder, logonUserUUID, organizationUUID);
		}
	}

	public boolean checkAllProdTargetItemCompleted(List<ServiceEntityNode> allRepairProdTargetMatItemList){
		return prodOrderTargetMatItemManager.checkAllProdTargetItemCompleted(allRepairProdTargetMatItemList);
	}

	public void convMaterialSKUToTargetItemUI(MaterialStockKeepUnit materialStockKeepUnit, RepairProdTargetMatItemUIModel repairProdTargetMatItemUIModel, LogonInfo logonInfo){
		prodOrderTargetMatItemManager.convMaterialSKUToTargetItemUI(materialStockKeepUnit,
				repairProdTargetMatItemUIModel, logonInfo);

	}

	/**
	 * @param repairProdTargetMatItem
	 * @param repairProdTargetMatItemUIModel
	 * @param logonInfo
	 */
	public void convRepairProdTargetMatItemToUI(RepairProdTargetMatItem repairProdTargetMatItem,
			RepairProdTargetMatItemUIModel repairProdTargetMatItemUIModel, LogonInfo logonInfo) {
		prodOrderTargetMatItemManager.convProdOrderTargetMatItemToUI(repairProdTargetMatItem,
				repairProdTargetMatItemUIModel,logonInfo);
		if (logonInfo != null) {
			try {
				Map<Integer, String> statusMap = initStatusMap(logonInfo.getLanguageCode());
				repairProdTargetMatItemUIModel.setItemStatusValue(statusMap.get(repairProdTargetMatItem.getItemStatus()));
			} catch (ServiceEntityInstallationException e) {
				// log error and continue
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, IDocItemNodeFieldConstant.itemStatus), e);
			}
		}
	}

	public void convUIToRepairProdTargetMatItem(RepairProdTargetMatItemUIModel repairProdTargetMatItemUIModel,
			RepairProdTargetMatItem repairProdTargetMatItem) {
		prodOrderTargetMatItemManager.convUIToProdOrderTargetMatItem(repairProdTargetMatItemUIModel,
				repairProdTargetMatItem);
	}

	public void convRepairProdTarSubItemToUI(RepairProdTarSubItem repairProdTarSubItem,
			RepairProdTarSubItemUIModel repairProdTarSubItemUIModel, LogonInfo logonInfo) {
		docFlowProxy.convDocMatItemToUI(repairProdTarSubItem, repairProdTarSubItemUIModel, logonInfo);
		repairProdTarSubItemUIModel.setLayer(repairProdTarSubItem.getLayer());
		repairProdTarSubItemUIModel.setRefParentItemUUID(repairProdTarSubItem.getRefParentItemUUID());
		repairProdTarSubItemUIModel.setRefBOMItemUUID(repairProdTarSubItem.getRefBOMItemUUID());
	}

	public void convBillOfOrderItemToUI(BillOfMaterialItem billOfMaterialItem,
			RepairProdTarSubItemUIModel repairProdTarSubItemUIModel, LogonInfo logonInfo) {
		if (billOfMaterialItem != null && repairProdTarSubItemUIModel != null) {
			repairProdTarSubItemUIModel.setRefBOMItemId(billOfMaterialItem.getId());
		}
	}

	public void convUIToRepairProdTarSubItem(RepairProdTarSubItemUIModel repairProdTarSubItemUIModel,
			RepairProdTarSubItem repairProdTarSubItem) {
		docFlowProxy.convUIToDocMatItem(repairProdTarSubItemUIModel, repairProdTarSubItem);
		repairProdTarSubItemUIModel.setLayer(repairProdTarSubItem.getLayer());
		repairProdTarSubItemUIModel.setRefParentItemUUID(repairProdTarSubItem.getRefParentItemUUID());
		repairProdTarSubItemUIModel.setRefBOMItemUUID(repairProdTarSubItem.getRefBOMItemUUID());
	}

//	/**
//	 * Wrapper method to generate the Production target material item list batch
//	 * as well as clear the previous ones
//	 * [Important] Need to update into persistence outside
//	 *
//	 * @param repairProdOrderServiceModel
//	 * @param logonUserUUID
//	 * @param organizationUUID
//	 * @return
//	 * @throws ServiceEntityConfigureException
//	 * @throws MaterialException
//	 * @throws ProductionOrderException
//	 * @throws BillOfMaterialException
//	 * @throws ServiceModuleProxyException
//	 * @throws ProdOrderReportException
//	 */
//	public List<RepairProdTargetMatItemServiceModel> newProdTargetMatItemWrapper(
//			RepairProdOrderServiceModel repairProdOrderServiceModel,
//			ProdPickingRefMaterialItem prodPickingRefMaterialItem, List<ServiceEntityNode> prevDocMatItemList, String logonUserUUID, String organizationUUID)
//			throws ServiceEntityConfigureException, MaterialException, BillOfMaterialException, ProductionOrderException,
//			ServiceModuleProxyException {
//		/*
//		 * [Step1] New Production Order Report
//		 */
//		RepairProdOrder repairProdOrder = repairProdOrderServiceModel.getRepairProdOrder();
//		MaterialStockKeepUnit tempMaterialSKU = (MaterialStockKeepUnit) materialStockKeepUnitManager
//				.getEntityNodeByKey(repairProdOrder.getRefMaterialSKUUUID(), IServiceEntityNodeFieldConstant.UUID,
//						MaterialStockKeepUnit.NODENAME, repairProdOrder.getClient(), null);
//		// Remove the previous old target mat item list
//		if (!ServiceCollectionsHelper.checkNullList(repairProdOrderServiceModel.getRepairProdTargetMatItemList())){
//			repairProdOrderServiceModel.setRepairProdTargetMatItemList(new ArrayList<>());
//		}
//
//		/*
//		 * [Step2] Get Material SKU temp serial number
//		 */
//		MatDecisionValueSetting matDecisionSerialFormat = matDecisionValueSettingManager
//				.getDecisionValue(tempMaterialSKU, MatDecisionValueSettingManager.VAUSAGE_SERIALNUM_FORMAT);
//		/*
//		 * [Step3] Generate Material SKU item list
//		 */
//		StorageCoreUnit requestCoreUnit = new StorageCoreUnit(repairProdOrder.getRefMaterialSKUUUID(),
//				repairProdOrder.getRefUnitUUID(), repairProdOrder.getAmount());
//		List<RepairProdTargetMatItemServiceModel> repairProdTargetMatItemList = newTargetMatItem(requestCoreUnit,
//				repairProdOrderServiceModel, matDecisionSerialFormat, prevDocMatItemList, tempMaterialSKU,
//				logonUserUUID, organizationUUID);
//		// Also merge into parent repairProdOrderServiceModel
//		if (!ServiceCollectionsHelper.checkNullList(repairProdTargetMatItemList)) {
//			repairProdOrderServiceModel.setRepairProdTargetMatItemList(repairProdTargetMatItemList);
//		}
//		/*
//		 * [Step4] Binding to previous picking material item if possible
//		 */
//		// Try to find the prev picking material item
//		if (prodPickingRefMaterialItem != null){
//			bindingToPickingItemWrapper(
//					repairProdTargetMatItemList, prodPickingRefMaterialItem);
//		} else {
//			prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) prodPickingOrderManager
//					.getEntityNodeByKey(repairProdOrder.getUuid(), "refNextOrderUUID", ProdPickingRefMaterialItem.NODENAME,
//							repairProdOrder.getClient(), null);
//			if (prodPickingRefMaterialItem != null){
//				bindingToPickingItemWrapper(
//						repairProdTargetMatItemList, prodPickingRefMaterialItem);
//			}
//		}
//
//		return repairProdTargetMatItemList;
//	}

	public void bindToPickingItem(List<RepairProdTargetMatItemServiceModel> repairProdTargetMatItemList,
													 ProdPickingRefMaterialItem prodPickingRefMaterialItem,
													 String orderUUID) throws ServiceEntityConfigureException {
		// Try to find the prev picking material item
		if (prodPickingRefMaterialItem != null){
			bindingToPickingItemWrapper(
					repairProdTargetMatItemList, prodPickingRefMaterialItem);
		} else {
			prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) prodPickingOrderManager
					.getEntityNodeByKey(orderUUID, "refNextOrderUUID", ProdPickingRefMaterialItem.NODENAME,
							prodPickingRefMaterialItem.getClient(), null);
			if (prodPickingRefMaterialItem != null){
				bindingToPickingItemWrapper(
						repairProdTargetMatItemList, prodPickingRefMaterialItem);
			}
		}
	}

	/**
	 * Utility method: Logic to binding the newly generated target mat item list
	 * to picking material item
	 *
	 * @param repairProdTargetMatItemList
	 * @param prodPickingRefMaterialItem
	 */
	public void bindingToPickingItemWrapper(List<RepairProdTargetMatItemServiceModel> repairProdTargetMatItemList,
			ProdPickingRefMaterialItem prodPickingRefMaterialItem) {
		if (!ServiceCollectionsHelper.checkNullList(repairProdTargetMatItemList)) {
			// Point target item prev to picking material item
			for (RepairProdTargetMatItemServiceModel repairProdTargetMatItemServiceModel : repairProdTargetMatItemList) {
				RepairProdTargetMatItem repairProdTargetMatItem = repairProdTargetMatItemServiceModel.getRepairProdTargetMatItem();
				repairProdTargetMatItem.setPrevDocMatItemUUID(prodPickingRefMaterialItem.getUuid());
				repairProdTargetMatItem.setPrevDocType(IDefDocumentResource.DOCUMENT_TYPE_PRODPICKINGORDER);
				prodPickingRefMaterialItem.setNextDocMatItemUUID(repairProdTargetMatItem.getUuid());
				prodPickingRefMaterialItem.setNextDocType(IDefDocumentResource.DOCUMENT_TYPE_REPAIRPRODORDER);
				// Also set document info to document type and refUUID
				prodPickingRefMaterialItem.setDocumentType(prodPickingRefMaterialItem.getNextDocType());
				prodPickingRefMaterialItem.setRefUUID(prodPickingRefMaterialItem.getNextDocMatItemUUID());
			}
		}
	}

	public List<RepairProdTargetMatItemServiceModel> newTargetMatItem(List<StoreAvailableStoreItemResponse> storeAvailableStoreItemResponseList,
			RepairProdOrderServiceModel repairProdOrderServiceModel)
			throws ServiceEntityConfigureException, ProductionOrderException,
			ServiceModuleProxyException {
		// Clone the productionServiceModel, during dispatching resources.
		// amount of prodProposal amount will be changed.
		RepairProdOrderServiceModel repairProdOrderServiceModelBack = (RepairProdOrderServiceModel) ServiceModuleCloneService
				.deepCloneServiceModule(repairProdOrderServiceModel, false);
		RepairProdOrder repairProdOrder = repairProdOrderServiceModel.getRepairProdOrder();

		if(ServiceCollectionsHelper.checkNullList(storeAvailableStoreItemResponseList)){
			// In case no store item list could be selected.
			throw new ProductionOrderException(ProductionOrderException.PARA_NO_MATERIALSKU, "");
		}
		List<RepairProdTargetMatItemServiceModel> resultList = new ArrayList<>();
		for(StoreAvailableStoreItemResponse storeAvailableStoreItemResponse: storeAvailableStoreItemResponseList){
			RepairProdTargetMatItemServiceModel repairProdTargetMatItemServiceModel = new RepairProdTargetMatItemServiceModel();
			WarehouseStoreItem warehouseStoreItem = storeAvailableStoreItemResponse.getWarehouseStoreItem();
			RepairProdTargetMatItem repairProdTargetMatItem = (RepairProdTargetMatItem) repairProdOrderManager
					.newEntityNode(repairProdOrder, RepairProdTargetMatItem.NODENAME);
			// Also set RefMaterialSKUUUID's value in case for batch Material
			repairProdTargetMatItem.setRefMaterialSKUUUID(warehouseStoreItem.getRefMaterialSKUUUID());
			repairProdTargetMatItem.setRefUnitUUID(storeAvailableStoreItemResponse.getAvailableAmount().getRefUnitUUID());
			repairProdTargetMatItem.setAmount(storeAvailableStoreItemResponse.getAvailableAmount().getAmount());
			reserveStoreItem(warehouseStoreItem, repairProdTargetMatItem);
			repairProdTargetMatItem.setProductionBatchNumber(warehouseStoreItem.getProductionBatchNumber());
			repairProdTargetMatItemServiceModel.setRepairProdTargetMatItem(repairProdTargetMatItem);
			resultList.add(repairProdTargetMatItemServiceModel);
		}
		return resultList;
	}

	private void reserveStoreItem(WarehouseStoreItem warehouseStoreItem, RepairProdTargetMatItem repairProdTargetMatItem){
		warehouseStoreItem.setReservedMatItemUUID(repairProdTargetMatItem.getUuid());
		warehouseStoreItem.setReservedDocType(IDefDocumentResource.DOCUMENT_TYPE_REPAIRPRODORDER);
	}

	public void deleteModel(RepairProdTargetMatItem repairProdTargetMatItem, String logonUserUUID,
							String organizationUUID) throws ServiceEntityConfigureException {
		if (repairProdTargetMatItem != null) {
			// Clear store item reserved doc
			//warehouseStoreItemManager.batchClearReservedStoreItem(ServiceCollectionsHelper.asList
			// (repairProdTargetMatItem), logonUserUUID, organizationUUID);
			repairProdOrderManager.admDeleteEntityByKey(repairProdTargetMatItem.getUuid(),
					IServiceEntityNodeFieldConstant.UUID, RepairProdTargetMatItem.NODENAME);
		}
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
	public List<ServiceEntityNode> dispatchProposalToSubItem(RepairProdOrderServiceModel repairProdOrderServiceModel,
			BillOfMaterialOrder billOfMaterialOrder, RepairProdTargetMatItem repairProdTargetMatItem,
			List<ServiceEntityNode> productiveBOMList, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, MaterialException, ProductionOrderException, DocActionException {
		if (productiveBOMList == null || productiveBOMList.size() == 0) {
			return null;
		}
		/*
		 * [Step1] Calculate the base ratio from request to BOM
		 */
		RepairProdOrder repairProdOrder = repairProdOrderServiceModel.getRepairProdOrder();
		StorageCoreUnit requestStorageCoreUnit = new StorageCoreUnit();
		requestStorageCoreUnit.setAmount(repairProdTargetMatItem.getAmount());
		requestStorageCoreUnit.setRefMaterialSKUUUID(repairProdTargetMatItem.getRefMaterialSKUUUID());
		requestStorageCoreUnit.setRefUnitUUID(repairProdTargetMatItem.getRefUnitUUID());
		double ratio = repairProdOrderManager
				.getRatioFromProductionToBOMOrder(requestStorageCoreUnit, repairProdOrder.getRefBillOfMaterialUUID(),
						billOfMaterialOrder, productiveBOMList, repairProdTargetMatItem.getClient());
		List<ServiceEntityNode> repairProdTarSubItemList = new ArrayList<>();
		/*
		 * [Step2] Traverse from first BOM layer into the footer layer to
		 * calculate each item required amount
		 */
		List<ServiceEntityNode> firstBomLayerList = new ArrayList<>();
		if (!billOfMaterialOrder.getUuid().equals(repairProdOrder.getRefBillOfMaterialUUID())) {
			// In case repairProdOrder's BOM not point to BOM top level
			firstBomLayerList = billOfMaterialOrderManager
					.filterSubBOMItemList(repairProdOrder.getRefBillOfMaterialUUID(), productiveBOMList);
		} else {
			firstBomLayerList = billOfMaterialOrderManager.filterBOMItemListByLayer(1, productiveBOMList);
		}
		repairProdTarSubItemList = dispatchProposalToSubItemByBOM(repairProdOrderServiceModel.getRepairProdOrderItemList(),
				repairProdTargetMatItem, null, ratio, firstBomLayerList, productiveBOMList, serialLogonInfo);
		return repairProdTarSubItemList;

	}

	public List<ServiceEntityNode> dispatchProposalToSubItemByBOM(
			List<RepairProdOrderItemServiceModel> repairProdOrderItemServiceModelList, RepairProdTargetMatItem parentTargetMatItem,
			RepairProdTarSubItem parentTarSubItem, double ratio, List<ServiceEntityNode> curBOMList,
			List<ServiceEntityNode> productiveBOMList, SerialLogonInfo serialLogonInfo)
            throws ProductionOrderException, MaterialException, ServiceEntityConfigureException, DocActionException {
		if (ServiceCollectionsHelper.checkNullList(curBOMList)) {
			return null;
		}
		if (ServiceCollectionsHelper.checkNullList(repairProdOrderItemServiceModelList)) {
			return null;
		}
		List<ServiceEntityNode> repairProdTarSubItemList = new ArrayList<>();
		for (ServiceEntityNode seNode : curBOMList) {
			ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
			double amount = productiveBOMItem.getAmount() * ratio;
			RepairProdOrderItemServiceModel repairProdOrderItemServiceModel = RepairProdOrderItemManager
					.filterRepairProdOrderItemByBOMItemUUID(productiveBOMItem.getUuid(), repairProdOrderItemServiceModelList);
			if (repairProdOrderItemServiceModel == null) {
				throw new ProductionOrderException(ProductionOrderException.PARA2_NO_PRODITEM_BYBOM, productiveBOMItem.getUuid(),
						productiveBOMItem.getRefMaterialSKUUUID());
			}
			// Calculate the amount with loss rate
			double amountWithLossRate = amount / (1 - productiveBOMItem.getTheoLossRate() / 100);
			amountWithLossRate = Math.ceil(amountWithLossRate);
			productiveBOMItem.setAmountWithLossRate(amountWithLossRate);
			List<RepairProdItemReqProposalServiceModel> repairProdItemReqProposalList = RepairProdOrderItemManager
					.optimizeProposalListByStore(repairProdOrderItemServiceModel.getRepairProdItemReqProposalList());
			if (ServiceCollectionsHelper.checkNullList(repairProdItemReqProposalList)) {
				throw new ProductionOrderException(ProductionOrderException.PARA_NO_PROPOSAL,
						repairProdOrderItemServiceModel.getRepairProdOrderItem().getUuid());
			}

			for (RepairProdItemReqProposalServiceModel prodProposalServiceModel : repairProdItemReqProposalList) {
				/*
				 * [3.1] Generate Sub item instance by calculate resources from
				 * proposal
				 */
				RepairProdItemReqProposal repairProdItemReqProposal = prodProposalServiceModel.getRepairProdItemReqProposal();
				if (repairProdItemReqProposal.getAmount() <= 0) {
					continue;
				}
				RepairProdTarSubItem repairProdTarSubItem = genBatchTarSubItemCore(parentTargetMatItem, productiveBOMItem,
						repairProdItemReqProposal, serialLogonInfo);
				if (parentTarSubItem != null) {
					repairProdTarSubItem.setRefParentItemUUID(parentTarSubItem.getUuid());
				}
				repairProdTarSubItem.setLayer(productiveBOMItem.getLayer());
				repairProdTarSubItemList.add(repairProdTarSubItem);

				/*
				 * [Step3.5] Important! Navigate to sub item
				 */
				List<ServiceEntityNode> subProductiveBOMList = billOfMaterialOrderManager
						.filterAllSubBOMItemList(productiveBOMItem.getUuid(), productiveBOMList);
				if (!ServiceCollectionsHelper.checkNullList(subProductiveBOMList)) {
					StorageCoreUnit requestStorageCoreUnit = new StorageCoreUnit();
					requestStorageCoreUnit.setAmount(repairProdTarSubItem.getAmount());
					requestStorageCoreUnit.setRefMaterialSKUUUID(repairProdTarSubItem.getRefMaterialSKUUUID());
					requestStorageCoreUnit.setRefUnitUUID(repairProdTarSubItem.getRefUnitUUID());
					double nextRatio = repairProdOrderManager
							.getRatioFromProductionToBOMOrder(requestStorageCoreUnit, productiveBOMItem.getUuid(), null,
									productiveBOMList, repairProdTarSubItem.getClient());
					List<ServiceEntityNode> childTarSubItemList = dispatchProposalToSubItemByBOM(
							prodProposalServiceModel.getRepairProdOrderItemList(), parentTargetMatItem, repairProdTarSubItem, nextRatio,
							subProductiveBOMList, productiveBOMList, serialLogonInfo);
					if (!ServiceCollectionsHelper.checkNullList(childTarSubItemList)) {
						repairProdTarSubItemList.addAll(childTarSubItemList);
					}
				}
				if (repairProdItemReqProposal.getAmount() > 0) {
					// In case request is already meet from proposal, just break
					break;
				}
				if (productiveBOMItem.getAmountWithLossRate() <= 0) {
					// In case request already been meet
					break;
				}
			}
		}
		return repairProdTarSubItemList;
	}

	/**
	 * [Internal method] Core Logic to create ProdTarSubItem List by
	 * distribution the resources from proposal, each proposal will equal to at
	 * least one Sub Item.
	 *
	 * @param parentTargetMatItem
	 * @param repairProdItemReqProposal
	 * @return
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 */
	private RepairProdTarSubItem genBatchTarSubItemCore(RepairProdTargetMatItem parentTargetMatItem,
														ProductiveBOMItem productiveBOMItem, RepairProdItemReqProposal repairProdItemReqProposal, SerialLogonInfo serialLogonInfo)
            throws MaterialException, ServiceEntityConfigureException, DocActionException {
		/*
		 * [Step1] Compare current request and provided amount from proposal
		 */
		StorageCoreUnit subRequestStoreUnit = new StorageCoreUnit(productiveBOMItem.getRefMaterialSKUUUID(),
				productiveBOMItem.getRefUnitUUID(), productiveBOMItem.getAmountWithLossRate());
		StorageCoreUnit storeFromProposal = new StorageCoreUnit(repairProdItemReqProposal.getRefMaterialSKUUUID(),
				repairProdItemReqProposal.getRefUnitUUID(), repairProdItemReqProposal.getAmount());
		StorageCoreUnit minusResult = materialStockKeepUnitManager
				.mergeStorageUnitCore(subRequestStoreUnit, storeFromProposal, StorageCoreUnit.OPERATOR_MINUS,
						parentTargetMatItem.getClient());
		List<ServiceEntityNode> resultList = new ArrayList<>();
		if (minusResult.getAmount() <= 0) {
			/*
			 * [Step2] In case proposal has larger provided amount than current
			 * request.
			 */
			RepairProdTarSubItem repairProdTarSubItem = (RepairProdTarSubItem) repairProdOrderManager
					.newEntityNode(parentTargetMatItem, RepairProdTarSubItem.NODENAME);
			docFlowProxy.buildItemPrevNextRelationship(repairProdItemReqProposal,
					 repairProdTarSubItem,null, serialLogonInfo);
			repairProdTarSubItem.setAmount(subRequestStoreUnit.getAmount());
			repairProdTarSubItem.setRefUnitUUID(subRequestStoreUnit.getRefUnitUUID());
			repairProdTarSubItem.setRefMaterialSKUUUID(repairProdItemReqProposal.getRefMaterialSKUUUID());
			repairProdTarSubItem.setRefBOMItemUUID(productiveBOMItem.getUuid());
			repairProdTarSubItem.setLayer(productiveBOMItem.getLayer());
			resultList.add(repairProdTarSubItem);
			// minus from proposal and continue
			productiveBOMItem.setAmountWithLossRate(0);
			repairProdItemReqProposal.setAmount(0 - minusResult.getAmount());
			return repairProdTarSubItem;

		} else {
			/*
			 * [Step3] In case proposal provided amount can not meet current
			 * request
			 */
			RepairProdTarSubItem repairProdTarSubItem = (RepairProdTarSubItem) repairProdOrderManager
					.newEntityNode(parentTargetMatItem, RepairProdTarSubItem.NODENAME);
			docFlowProxy.buildItemPrevNextRelationship(repairProdItemReqProposal,
					repairProdTarSubItem, null, serialLogonInfo);
			repairProdItemReqProposal.setAmount(0);
			repairProdTarSubItem.setAmount(storeFromProposal.getAmount());
			repairProdTarSubItem.setRefUnitUUID(storeFromProposal.getRefUnitUUID());
			productiveBOMItem.setAmountWithLossRate(minusResult.getAmount());
			productiveBOMItem.setRefUnitUUID(minusResult.getRefUnitUUID());
			repairProdTarSubItem.setRefMaterialSKUUUID(repairProdItemReqProposal.getRefMaterialSKUUUID());
			repairProdTarSubItem.setRefBOMItemUUID(productiveBOMItem.getUuid());
			repairProdTarSubItem.setLayer(productiveBOMItem.getLayer());
			resultList.add(repairProdTarSubItem);
			return repairProdTarSubItem;
		}
	}

	/**
	 * Core Logic to generate Target Material Item for Trace [Batch] Material
	 * SKU
	 *
	 * @param requestUnit
	 * @param tempMaterialSKU
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 * @throws BillOfMaterialException
	 * @throws ProductionOrderException
	 */
	@Deprecated
	private List<RepairProdTargetMatItemServiceModel> newTargetMatItemBatch(StorageCoreUnit requestUnit,
			RepairProdOrderServiceModel repairProdOrderServiceModel, MatDecisionValueSetting matDecisionSerialFormat,
			List<ServiceEntityNode> prevDocMatItemList, MaterialStockKeepUnit tempMaterialSKU, SerialLogonInfo serialLogonInfo)
            throws MaterialException, ServiceEntityConfigureException, BillOfMaterialException, ProductionOrderException, DocActionException {

		int index = 0, step = 1;
		StorageCoreUnit storageCoreUnit1 = new StorageCoreUnit();
		storageCoreUnit1.setRefMaterialSKUUUID(tempMaterialSKU.getUuid());
		storageCoreUnit1.setAmount(requestUnit.getAmount());
		storageCoreUnit1.setRefUnitUUID(requestUnit.getRefUnitUUID());

		StorageCoreUnit storageCoreUnit2 = new StorageCoreUnit();
		storageCoreUnit2.setRefMaterialSKUUUID(tempMaterialSKU.getUuid());
		storageCoreUnit2.setAmount(step);
		storageCoreUnit2.setRefUnitUUID(materialStockKeepUnitManager.getMainUnitUUID(tempMaterialSKU));

		RepairProdOrder repairProdOrder = repairProdOrderServiceModel.getRepairProdOrder();
		BillOfMaterialOrder billOfMaterialOrder = billOfMaterialOrderManager
				.getRefBillOfMaterialOrderWrapper(repairProdOrder.getRefBillOfMaterialUUID(), repairProdOrder.getClient());
		List<ServiceEntityNode> productiveBOMList = billOfMaterialOrderManager.genProductiveBOMModel(billOfMaterialOrder);
		if (productiveBOMList == null || productiveBOMList.size() == 0) {
			return null;
		}

		List<RepairProdTargetMatItemServiceModel> resultList = new ArrayList<>();
		RepairProdTargetMatItemServiceModel repairProdTargetMatItemServiceModel = new RepairProdTargetMatItemServiceModel();
		RepairProdTargetMatItem repairProdTargetMatItem = (RepairProdTargetMatItem) repairProdOrderManager
				.newEntityNode(repairProdOrder, RepairProdTargetMatItem.NODENAME);
		// Also set RefMaterialSKUUUID's value in case for batch Material
		repairProdTargetMatItem.setRefMaterialSKUUUID(tempMaterialSKU.getUuid());
		repairProdTargetMatItem.setRefUnitUUID(requestUnit.getRefUnitUUID());
		repairProdTargetMatItem.setAmount(requestUnit.getAmount());
		repairProdTargetMatItem.setReservedMatItemUUID(repairProdOrder.getReservedMatItemUUID());
		repairProdTargetMatItem.setReservedDocType(repairProdOrder.getReservedDocType());
		initCopyRepairProdOrderToTargetItem(repairProdOrder, repairProdTargetMatItem, serialLogonInfo);
		/*
		 * [Step2] Binding to previous doc mat item
		 */
		if(!ServiceCollectionsHelper.checkNullList(prevDocMatItemList)){
			DocMatItemNode prevMatItemNode = (DocMatItemNode) prevDocMatItemList.get(0);
			docFlowProxy.buildItemPrevNextRelationship(prevMatItemNode, repairProdTargetMatItem, null, serialLogonInfo);
		}
		/*
		 * [Step3] Generate Trace Material SKU instance
		 */
		repairProdTargetMatItem.setProcessIndex(index + 1);

		/*
		 * [Step3] Generate relative sub item list
		 */
		List<ServiceEntityNode> repairProdTarSubItemList = dispatchProposalToSubItem(repairProdOrderServiceModel,
				billOfMaterialOrder, repairProdTargetMatItem, productiveBOMList, serialLogonInfo);
		repairProdTargetMatItemServiceModel.setRepairProdTargetMatItem(repairProdTargetMatItem);
		repairProdTargetMatItemServiceModel.setRepairProdTarSubItemList(repairProdTarSubItemList);
		resultList.add(repairProdTargetMatItemServiceModel);
		return resultList;
	}

	/**
	 * [Internal method] init & copy information from production order to report
	 * item
	 */
	@Deprecated
	private void initCopyRepairProdOrderToTargetItem(RepairProdOrder repairProdOrder,
			RepairProdTargetMatItem repairProdTargetMatItem, SerialLogonInfo serialLogonInfo) {
		if (repairProdTargetMatItem != null) {
			repairProdTargetMatItem.setReservedDocType(repairProdOrder.getReservedDocType());
			repairProdTargetMatItem.setReservedMatItemUUID(repairProdOrder.getReservedMatItemUUID());
			repairProdTargetMatItem.setPrevDocMatItemUUID(repairProdOrder.getReservedMatItemUUID());
			repairProdTargetMatItem.setPrevDocType(repairProdOrder.getReservedDocType());
			repairProdTargetMatItem.setProductionBatchNumber(repairProdOrder.getProductionBatchNumber());
			// initially, also set refMaterialSKUUUID as template SKU from order
			repairProdTargetMatItem.setRefMaterialSKUUUID(repairProdOrder.getRefMaterialSKUUUID());
		}
	}

	private RepairProdTargetMatItemServiceModel genTargetItemCore(int processIndex,
			RepairProdOrderServiceModel repairProdOrderServiceModel, MatDecisionValueSetting matDecisionSerialFormat,
			List<ServiceEntityNode> prevDocMatItemList,
			MaterialStockKeepUnit tempMaterialSKU, double amount, String refUnitUUID, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, MaterialException, ProductionOrderException, BillOfMaterialException, DocActionException {
		/*
		 * [Step1] New ProdOrderReportItem
		 */

		RepairProdOrder repairProdOrder = repairProdOrderServiceModel.getRepairProdOrder();
		BillOfMaterialOrder billOfMaterialOrder = billOfMaterialOrderManager
				.getRefBillOfMaterialOrderWrapper(repairProdOrder.getRefBillOfMaterialUUID(), repairProdOrder.getClient());
		List<ServiceEntityNode> productiveBOMList = billOfMaterialOrderManager.genProductiveBOMModel(billOfMaterialOrder);
		if (productiveBOMList == null || productiveBOMList.size() == 0) {
			return null;
		}
		RepairProdTargetMatItemServiceModel repairProdTargetMatItemServiceModel = new RepairProdTargetMatItemServiceModel();
		RepairProdTargetMatItem repairProdTargetMatItem = (RepairProdTargetMatItem) repairProdOrderManager
				.newEntityNode(repairProdOrder, RepairProdTargetMatItem.NODENAME);
		// TODO default target item internal id
		repairProdTargetMatItem.setId(repairProdOrder.getId() + "-" + processIndex);
		repairProdTargetMatItem.setRefUnitUUID(refUnitUUID);
		repairProdTargetMatItem.setAmount(amount);
		initCopyRepairProdOrderToTargetItem(repairProdOrder, repairProdTargetMatItem, serialLogonInfo);
		/*
		 * [Step2] Binding to previous doc mat item
		 */
		if(!ServiceCollectionsHelper.checkNullList(prevDocMatItemList)){
			DocMatItemNode prevMatItemNode = (DocMatItemNode) prevDocMatItemList.get(0);
			docFlowProxy.buildItemPrevNextRelationship(prevMatItemNode, repairProdTargetMatItem, null, serialLogonInfo);
		}
		repairProdTargetMatItem.setUnitPrice(tempMaterialSKU.getUnitCost());
		StorageCoreUnit storageCoreUnit1 = new StorageCoreUnit();
		storageCoreUnit1.setRefMaterialSKUUUID(tempMaterialSKU.getUuid());
		storageCoreUnit1.setAmount(amount);
		storageCoreUnit1.setRefUnitUUID(refUnitUUID);
		StorageCoreUnit storageCoreUnit2 = new StorageCoreUnit();
		storageCoreUnit2.setRefMaterialSKUUUID(tempMaterialSKU.getUuid());
		storageCoreUnit2.setAmount(1);
		storageCoreUnit2.setRefUnitUUID(materialStockKeepUnitManager.getMainUnitUUID(tempMaterialSKU));
		double itemAmount = materialStockKeepUnitManager
				.getStorageUnitRatio(storageCoreUnit1, storageCoreUnit2, repairProdOrder.getClient());
		repairProdTargetMatItem.setItemPrice(itemAmount * tempMaterialSKU.getUnitCost());
		/*
		 * [Step2] Generate Trace Material SKU instance
		 */
		// Initial processIndex is zero
		repairProdTargetMatItem.setProductionBatchNumber(repairProdOrder.getProductionBatchNumber());
		repairProdTargetMatItem.setReservedDocType(repairProdOrder.getReservedDocType());
		repairProdTargetMatItem.setReservedMatItemUUID(repairProdOrder.getReservedMatItemUUID());
		repairProdTargetMatItem.setProcessIndex(processIndex + 1);
		String serialNumber = processIndex + "";

		repairProdTargetMatItem.setRefSerialId(serialNumber);
		/*
		 * [Step3] Generate relative sub item list
		 */
		List<ServiceEntityNode> repairProdTarSubItemList = dispatchProposalToSubItem(repairProdOrderServiceModel,
				billOfMaterialOrder, repairProdTargetMatItem, productiveBOMList, serialLogonInfo);
		repairProdTargetMatItemServiceModel.setRepairProdTargetMatItem(repairProdTargetMatItem);
		repairProdTargetMatItemServiceModel.setRepairProdTarSubItemList(repairProdTarSubItemList);
		return repairProdTargetMatItemServiceModel;
	}

	public ServiceDocumentExtendUIModel convRepairProdTargetMatItemToDocExtUIModel(
			RepairProdTargetMatItemUIModel repairProdTargetMatItemUIModel, LogonInfo logonInfo) throws ServiceEntityInstallationException {
		ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
		serviceDocumentExtendUIModel.setRefUIModel(repairProdTargetMatItemUIModel);
		serviceDocumentExtendUIModel.setUuid(repairProdTargetMatItemUIModel.getUuid());
		serviceDocumentExtendUIModel.setParentNodeUUID(repairProdTargetMatItemUIModel.getParentNodeUUID());
		serviceDocumentExtendUIModel.setRootNodeUUID(repairProdTargetMatItemUIModel.getRootNodeUUID());
		serviceDocumentExtendUIModel.setId(repairProdTargetMatItemUIModel.getId());
		serviceDocumentExtendUIModel.setName(repairProdTargetMatItemUIModel.getRefMaterialSKUName());
		serviceDocumentExtendUIModel.setStatus(repairProdTargetMatItemUIModel.getItemStatus());
		serviceDocumentExtendUIModel.setStatusValue(repairProdTargetMatItemUIModel.getItemStatusValue());
		serviceDocumentExtendUIModel.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_REPAIRPRODORDER);
		if(logonInfo != null){
			serviceDocumentExtendUIModel
					.setDocumentTypeValue(serviceDocumentComProxy
							.getDocumentTypeValue(IDefDocumentResource.DOCUMENT_TYPE_REPAIRPRODORDER,
									logonInfo.getLanguageCode()));
		}
		serviceDocumentExtendUIModel.setRefMaterialSKUUUID(repairProdTargetMatItemUIModel.getRefMaterialSKUUUID());
		serviceDocumentExtendUIModel.setRefMaterialSKUId(repairProdTargetMatItemUIModel.getRefMaterialSKUId());
		serviceDocumentExtendUIModel.setRefMaterialSKUName(repairProdTargetMatItemUIModel.getRefMaterialSKUName());
		String referenceDate = repairProdTargetMatItemUIModel.getCreatedDate();
		serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
		return serviceDocumentExtendUIModel;
	}

	public ServiceDocumentExtendUIModel convToDocumentExtendUIModel(ServiceEntityNode seNode, LogonInfo logonInfo) {
		if (seNode == null) {
			return null;
		}
		if (RepairProdTargetMatItem.NODENAME.equals(seNode.getNodeName())) {
			RepairProdTargetMatItem repairProdTargetMatItem = (RepairProdTargetMatItem) seNode;
			try {
				RepairProdTargetMatItemUIModel repairProdTargetMatItemUIModel = (RepairProdTargetMatItemUIModel) repairProdOrderManager
						.genUIModelFromUIModelExtension(RepairProdTargetMatItemUIModel.class,
								repairProdTargetMatItemServiceUIModelExtension.genUIModelExtensionUnion().get(0), repairProdTargetMatItem,
								logonInfo, null);
				ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = convRepairProdTargetMatItemToDocExtUIModel(
						repairProdTargetMatItemUIModel, logonInfo);
				return serviceDocumentExtendUIModel;
			} catch (ServiceModuleProxyException | ServiceEntityConfigureException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, RepairProdOrderItem.NODENAME));
			} catch (ServiceEntityInstallationException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, RepairProdOrderItem.NODENAME));
			}
		}
		return null;

	}

}
