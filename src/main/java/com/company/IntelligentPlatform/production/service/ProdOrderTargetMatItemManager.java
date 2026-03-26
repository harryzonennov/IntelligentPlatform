package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.finance.service.FinanceAccountValueProxyException;
import com.company.IntelligentPlatform.finance.service.SystemResourceException;
import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.service.InboundDeliveryException;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryException;
import com.company.IntelligentPlatform.production.dto.ProdOrderTargetMatItemServiceUIModel;
import com.company.IntelligentPlatform.production.dto.ProdOrderTargetMatItemServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProdOrderTargetMatItemUIModel;
import com.company.IntelligentPlatform.production.dto.ProdOrderTarSubItemUIModel;
import com.company.IntelligentPlatform.production.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.service.MatDecisionValueSettingManager;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductException;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.model.MatDecisionValueSetting;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.RegisteredProduct;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceModuleCloneService;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class ProdOrderTargetMatItemManager {

	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ProdOrderTargetMatItemServiceUIModelExtension prodOrderTargetMatItemServiceUIModelExtension;

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
	protected ProdOrderTargetItemToCrossInboundProxy prodOrderTargetItemToCrossInboundProxy;

	@Autowired
	protected ProdOrderTargetItemToCrossQuaInspectProxy prodOrderTargetItemToCrossQuaInspectProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

	protected Logger logger = LoggerFactory.getLogger(ProdOrderTargetMatItemManager.class);

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	@Autowired
	protected SplitMatItemProxy splitMatItemProxy;

	public static final String METHOD_ConvProdOrderTargetMatItemToUI = "convProdOrderTargetMatItemToUI";

	public static final String METHOD_ConvUIToProdOrderTargetMatItem = "convUIToProdOrderTargetMatItem";

	public static final String METHOD_ConvProductionOrderToTargetItemUI = "convProductionOrderToTargetItemUI";

	public static final String METHOD_ConvProdOrderTarSubItemToUI = "convProdOrderTarSubItemToUI";

	public static final String METHOD_ConvUIToProdOrderTarSubItem = "convUIToProdOrderTarSubItem";

	public static final String METHOD_ConvBillOfOrderItemToUI = "convBillOfOrderItemToUI";

	public static final String METHOD_ConvMaterialSKUToTargetItemUI = "convMaterialSKUToTargetItemUI";

	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), ProductionOrder.NODENAME,
						request.getUuid(), ProdOrderTargetMatItem.NODENAME, productionOrderManager);
		docPageHeaderInputPara.setGenBaseModelList(new DocPageHeaderModelProxy.GenBaseModelList<ProductionOrder>() {
			@Override
			public List<PageHeaderModel> execute(ProductionOrder productionOrder) throws ServiceEntityConfigureException {
				// How to get the base page header model list
				return productionOrderManager.getPageHeaderModelList(productionOrder, client);
			}
		});
		docPageHeaderInputPara.setGenHomePageModel(new DocPageHeaderModelProxy.GenHomePageModel<ProdOrderTargetMatItem>() {
			@Override
			public PageHeaderModel execute(ProdOrderTargetMatItem prodOrderTargetMatItem, PageHeaderModel pageHeaderModel) throws ServiceEntityConfigureException {
				// How to render current page header
				MaterialStockKeepUnit materialStockKeepUnit = null;
				try {
					materialStockKeepUnit = materialStockKeepUnitManager
							.getMaterialSKUWrapper(prodOrderTargetMatItem.getRefMaterialSKUUUID(), prodOrderTargetMatItem.getClient(), null);
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
		return ServiceLanHelper
				.initDefLanguageMapUIModel(languageCode, this.statusMapLan, ProdOrderTargetMatItemUIModel.class,
						IDocItemNodeFieldConstant.itemStatus);
	}

	public String getTargetMatItemIndentifier(ProdOrderTargetMatItem prodOrderTargetMatItem, LogonInfo logonInfo)
			throws ServiceEntityConfigureException, ServiceModuleProxyException {
		if(!ServiceEntityStringHelper.checkNullString(prodOrderTargetMatItem.getRefSerialId())){
			return prodOrderTargetMatItem.getRefSerialId();
		}
		if(!ServiceEntityStringHelper.checkNullString(prodOrderTargetMatItem.getId())){
			return prodOrderTargetMatItem.getId();
		}
		ProdOrderTargetMatItemUIModel prodOrderTargetMatItemUIModel = (ProdOrderTargetMatItemUIModel) productionOrderManager
				.genUIModelFromUIModelExtension(
						ProdOrderTargetMatItemUIModel.class,
						prodOrderTargetMatItemServiceUIModelExtension
								.genUIModelExtensionUnion().get(0),
						prodOrderTargetMatItem, logonInfo, null);
		if(!ServiceEntityStringHelper.checkNullString(prodOrderTargetMatItemUIModel.getId())){
			return prodOrderTargetMatItemUIModel.getId();
		}
		if(!ServiceEntityStringHelper.checkNullString(prodOrderTargetMatItemUIModel.getSerialId())){
			return prodOrderTargetMatItemUIModel.getSerialId();
		}
		if(!ServiceEntityStringHelper.checkNullString(prodOrderTargetMatItemUIModel.getRefMaterialSKUId())){
			return prodOrderTargetMatItemUIModel.getRefMaterialSKUId();
		}
		return prodOrderTargetMatItemUIModel.getRefMaterialSKUName();
	}

	public void convProductionOrderToTargetItemUI(ProductionOrder productionOrder,
			ProdOrderTargetMatItemUIModel prodOrderTargetMatItemUIModel) {
		convProductionOrderToTargetItemUI(productionOrder, prodOrderTargetMatItemUIModel, null);
	}

	public void convProductionOrderToTargetItemUI(ProductionOrder productionOrder,
			ProdOrderTargetMatItemUIModel prodOrderTargetMatItemUIModel, LogonInfo logonInfo) {
		if (productionOrder != null && prodOrderTargetMatItemUIModel != null) {
			prodOrderTargetMatItemUIModel.setParentDocId(productionOrder.getId());
			prodOrderTargetMatItemUIModel.setParentDocStatus(productionOrder.getStatus());
		}
	}

	/**
	 * Core Logic to set target item status to [Production Done]
	 *
	 * @param prodOrderTargetMatItem
	 * @param logonUserUUID
	 * @param organizationUUID
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 * @throws ProdOrderTargetItemException
	 */
	public void setProductionDone(ProdOrderTargetMatItem prodOrderTargetMatItem, String logonUserUUID, String organizationUUID)
			throws MaterialException, ServiceEntityConfigureException, ProdOrderTargetItemException {
		setProductionDoneCore(prodOrderTargetMatItem, logonUserUUID, organizationUUID);
		productionOrderManager.updateSENode(prodOrderTargetMatItem, logonUserUUID, organizationUUID);
	}

	/**
	 * Core Logic to set target item status to [Production Done]
	 *
	 * @param prodOrderTargetMatItem
	 * @param logonUserUUID
	 * @param organizationUUID
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 * @throws ProdOrderTargetItemException
	 */
	public synchronized void setProductionDoneCore(ProdOrderTargetMatItem prodOrderTargetMatItem, String logonUserUUID,
								   String organizationUUID)
			throws MaterialException, ServiceEntityConfigureException, ProdOrderTargetItemException {
		/*
		 * [Step1] Pre-check duplicate of serial Id
		 */
		ProductionOrder productionOrder = (ProductionOrder) productionOrderManager
				.getEntityNodeByKey(prodOrderTargetMatItem.getRootNodeUUID(), IServiceEntityNodeFieldConstant.UUID,
						ProductionOrder.NODENAME, prodOrderTargetMatItem.getClient(), null);
		MaterialStockKeepUnit tempMaterialSKU = (MaterialStockKeepUnit) materialStockKeepUnitManager
				.getEntityNodeByKey(productionOrder.getRefMaterialSKUUUID(), IServiceEntityNodeFieldConstant.UUID,
						MaterialStockKeepUnit.NODENAME, prodOrderTargetMatItem.getClient(), null);
		if(tempMaterialSKU.getTraceMode() == MaterialStockKeepUnit.TRACEMODE_SINGLE){
			boolean duplicateFlag = this.checkDuplicateSerialId(prodOrderTargetMatItem, tempMaterialSKU);
			if (duplicateFlag) {
				throw new ProdOrderTargetItemException(ProdOrderTargetItemException.PARA_DUPLICATE_SERIAL_SET,
						prodOrderTargetMatItem.getRefSerialId());
			}
		}
		/*
		 * [Step2] Update status
		 */
		prodOrderTargetMatItem.setItemStatus(ProdOrderTargetMatItem.STATUS_DONE_PRODUCTION);
		/*
		 * [Step3] Generate Registered Product and update
		 */
		if(tempMaterialSKU.getTraceMode() == MaterialStockKeepUnit.TRACEMODE_SINGLE){
			genRegisteredProductCore(productionOrder, prodOrderTargetMatItem, tempMaterialSKU, logonUserUUID, organizationUUID);
		}
	}

	/**
	 * Core Logic to split target item and set part production done status
	 *
	 * @param splitMatItemModel
	 * @param prodOrderTargetMatItem
	 * @param logonUserUUID
	 * @param organizationUUID
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 * @throws ProdOrderTargetItemException
	 */
	public ProdOrderTargetMatItem splitProductionDoneService(SplitMatItemModel splitMatItemModel,
			ProdOrderTargetMatItem prodOrderTargetMatItem, String logonUserUUID, String organizationUUID)
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
		ProdOrderTargetMatItem prodOrderTargetMatItemBack = (ProdOrderTargetMatItem) prodOrderTargetMatItem.clone();
		SplitMatItemProxy.SplitResult splitResult = splitMatItemProxy.splitDefMatItemService(splitMatItemModel,prodOrderTargetMatItem);
		if(splitResult.getLeftRatio() > 0){
			// In case stll something left
			List<ServiceEntityNode> splitResultList = splitResult.getMergeResult();
			splitResultList.remove(prodOrderTargetMatItem);
			ProdOrderTargetMatItem newDoneTargetMatItem = (ProdOrderTargetMatItem) splitResultList.get(0);
			if (newDoneTargetMatItem != null) {
				//setProductionDone(newDoneTargetMatItem, logonUserUUID, organizationUUID);
				newDoneTargetMatItem.setItemStatus(ProdOrderTargetMatItem.STATUS_DONE_PRODUCTION);
				productionOrderManager.updateSENode(newDoneTargetMatItem, logonUserUUID, organizationUUID);
			}
			ProdOrderTargetMatItemServiceModel prodOrderTargetMatItemServiceModel = (ProdOrderTargetMatItemServiceModel) productionOrderManager
					.loadServiceModule(ProdOrderTargetMatItemServiceModel.class, prodOrderTargetMatItem,
							prodOrderTargetMatItemServiceUIModelExtension);
			/*
			 * [Step3] Deep clone the sub mat items and changed by split ratio
			 */
			ProdOrderTargetMatItemServiceModel newTargetMatItemServiceModel = (ProdOrderTargetMatItemServiceModel) ServiceModuleCloneService
					.deepCloneServiceModule(prodOrderTargetMatItemServiceModel, true);
			newTargetMatItemServiceModel.setProdOrderTargetMatItem(newDoneTargetMatItem);
			this.updateSubItemArrayWithRatio(newTargetMatItemServiceModel, splitResult.getSplitRatio());
			productionOrderManager.updateServiceModuleWithDelete(ProdOrderTargetMatItemServiceModel.class, newTargetMatItemServiceModel, logonUserUUID, organizationUUID);
			// update left target mat item
			this.updateSubItemArrayWithRatio(prodOrderTargetMatItemServiceModel, splitResult.getLeftRatio());
			productionOrderManager.updateServiceModuleWithDelete(ProdOrderTargetMatItemServiceModel.class, prodOrderTargetMatItemServiceModel, logonUserUUID, organizationUUID);
			return newDoneTargetMatItem;
		}else{
			// In case all is done
			prodOrderTargetMatItem.setItemStatus(ProdOrderTargetMatItem.STATUS_DONE_PRODUCTION);
			prodOrderTargetMatItem.setAmount(prodOrderTargetMatItemBack.getAmount());
			prodOrderTargetMatItem.setRefUnitUUID(prodOrderTargetMatItemBack.getRefUnitUUID());
			productionOrderManager.updateSENode(prodOrderTargetMatItem, logonUserUUID, organizationUUID);
			return prodOrderTargetMatItem;
		}
	}

	private void updateSubItemArrayWithRatio(ProdOrderTargetMatItemServiceModel prodOrderTargetMatItemServiceModel, double ratio){
		List<ProdOrderTarSubItemServiceModel> prodOrderTarSubItemList = prodOrderTargetMatItemServiceModel.getProdOrderTarSubItemList();
		if(ServiceCollectionsHelper.checkNullList(prodOrderTarSubItemList)){
			return;
		}
		for(ProdOrderTarSubItemServiceModel prodOrderTarSubItemServiceModel:prodOrderTarSubItemList){
			ProdOrderTarSubItem prodOrderTarSubItem = prodOrderTarSubItemServiceModel.getProdOrderTarSubItem();
			prodOrderTarSubItem.setAmount(prodOrderTarSubItem.getAmount() * ratio);
		}
	}

	/**
	 * Logic to pre-check before standard update/save action
	 *
	 * @param prodOrderTargetMatItem
	 * @throws ServiceEntityConfigureException
	 * @throws ProdOrderTargetItemException
	 * @throws RegisteredProductException
	 */
	public void preCheckUpdate(ProdOrderTargetMatItem prodOrderTargetMatItem)
			throws ServiceEntityConfigureException, ProdOrderTargetItemException, RegisteredProductException {
		/*
		 * [Step1] Check serial id duplicate
		 */
		ProductionOrder productionOrder = (ProductionOrder) productionOrderManager
				.getEntityNodeByKey(prodOrderTargetMatItem.getRootNodeUUID(), IServiceEntityNodeFieldConstant.UUID,
						ProductionOrder.NODENAME, prodOrderTargetMatItem.getClient(), null);
		MaterialStockKeepUnit tempMaterialSKU = (MaterialStockKeepUnit) materialStockKeepUnitManager
				.getEntityNodeByKey(productionOrder.getRefMaterialSKUUUID(), IServiceEntityNodeFieldConstant.UUID,
						MaterialStockKeepUnit.NODENAME, prodOrderTargetMatItem.getClient(), null);
		boolean duplicateFlag = this.checkDuplicateSerialId(prodOrderTargetMatItem, tempMaterialSKU);
		if (duplicateFlag) {
			throw new ProdOrderTargetItemException(ProdOrderTargetItemException.PARA_DUPLICATE_SERIAL_SET,
					prodOrderTargetMatItem.getRefSerialId());
		}
	}

	/**
	 * Core Logic to set target item status to [in process]
	 *
	 * @param prodOrderTargetMatItem
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 * @throws ProdOrderTargetItemException
	 */
	public void setInProcessStatus(ProdOrderTargetMatItem prodOrderTargetMatItem){
		/*
		 * [Step1] Update status
		 */
		if(prodOrderTargetMatItem.getItemStatus() != ProdOrderTargetMatItem.STATUS_DONE_PRODUCTION && prodOrderTargetMatItem.getItemStatus() != ProdOrderTargetMatItem.STATUS_CANCELED){
			prodOrderTargetMatItem.setItemStatus(ProdOrderTargetMatItem.STATUS_INPROCESS);
		}
	}

	public static void sortTargetMatItemListByProcessIndex(List<ProdOrderTargetMatItemServiceUIModel> prodOrderTargetMatItemServiceUIModelList){
		if (!ServiceCollectionsHelper.checkNullList(prodOrderTargetMatItemServiceUIModelList)){
			Collections.sort(prodOrderTargetMatItemServiceUIModelList, new Comparator<ProdOrderTargetMatItemServiceUIModel>() {
				public int compare(ProdOrderTargetMatItemServiceUIModel target1, ProdOrderTargetMatItemServiceUIModel target2) {
					int process1 = target1.getProdOrderTargetMatItemUIModel().getProcessIndex();
					int process2 = target2.getProdOrderTargetMatItemUIModel().getProcessIndex();
					return process1 - process2;
				}
			});
		}
	}

	/**
	 * Core Logic to set target item status to [in process]
	 *
	 * @param prodOrderTargetMatItemList
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 * @throws ProdOrderTargetItemException
	 */
	public void setInProcessStatusBatch(List<ServiceEntityNode> prodOrderTargetMatItemList, String logonUserUUID, String organizationUUID)
			throws ServiceEntityConfigureException {
		/*
		 * [Step1] Update status
		 */
		if(!ServiceCollectionsHelper.checkNullList(prodOrderTargetMatItemList)){
			for(ServiceEntityNode seNode:prodOrderTargetMatItemList){
				ProdOrderTargetMatItem prodOrderTargetMatItem = (ProdOrderTargetMatItem) seNode;
				setInProcessStatus(prodOrderTargetMatItem);
			}
		}
		productionOrderManager.updateSENodeList(prodOrderTargetMatItemList, logonUserUUID, organizationUUID);
	}

	/**
	 * Core Logic to set target item status to [Cancel]
	 *
	 * @param prodOrderTargetMatItem
	 * @param logonUserUUID
	 * @param organizationUUID
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 * @throws ProdOrderTargetItemException
	 */
	public void setCancelStatus(ProdOrderTargetMatItem prodOrderTargetMatItem, String logonUserUUID, String organizationUUID)
			throws MaterialException, ServiceEntityConfigureException, ProdOrderTargetItemException {
		/*
		 * [Step1] Update status
		 */
		prodOrderTargetMatItem.setItemStatus(ProdOrderTargetMatItem.STATUS_CANCELED);
		/*
		 * [Step2] Generate Registered Product and update
		 */
		productionOrderManager.updateSENode(prodOrderTargetMatItem, logonUserUUID, organizationUUID);
	}

	/**
	 * Logic to check if the pre-set serial id is unique for this template SKU
	 *
	 * @param prodOrderTargetMatItem
	 * @param tempMaterialSKU
	 */
	public boolean checkDuplicateSerialId(ProdOrderTargetMatItem prodOrderTargetMatItem, MaterialStockKeepUnit tempMaterialSKU)
			throws RegisteredProductException, ServiceEntityConfigureException, ProdOrderTargetItemException {
		/*
		 * [Step1] Check if duplicate serial Id already exist in exsited registered product
		 */
		// Check Empty serial Id firstly
		if (ServiceEntityStringHelper.checkNullString(prodOrderTargetMatItem.getRefSerialId())) {
			throw new ProdOrderTargetItemException(ProdOrderTargetItemException.PARA_NO_SERIAL_SET, tempMaterialSKU.getId());
		}
		RegisteredProduct existedRegisteredProduct = registeredProductManager
				.getRegisteredProductBySerialId(prodOrderTargetMatItem.getRefSerialId(), tempMaterialSKU.getUuid(),
						tempMaterialSKU.getClient());
		if (existedRegisteredProduct != null) {
			throw new RegisteredProductException(RegisteredProductException.PARA2_DUPLICATE_SERIALID,
					tempMaterialSKU.getId(), prodOrderTargetMatItem.getRefSerialId());
		}
		/*
		 * [Step2] Check if duplicate serial Id already exist in other pre-set targetMatItem
		 */
		List<ServiceEntityNode> existedTargetItemList = getTargetItemListByRefSerialId(prodOrderTargetMatItem.getRefSerialId(),
				prodOrderTargetMatItem.getRefMaterialSKUUUID(), prodOrderTargetMatItem.getClient());
		if (ServiceCollectionsHelper.checkNullList(existedTargetItemList)) {
			return false;
		}
		if (existedTargetItemList.size() > 1) {
			return true;
		}
		if (existedTargetItemList.get(0).getUuid().equals(prodOrderTargetMatItem.getUuid())) {
			return false;
		} else {
			return true;
		}
	}

	public List<ServiceEntityNode> getTargetItemListByRefSerialId(String serialId, String tempMaterialUUID, String client)
			throws ServiceEntityConfigureException {
		List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
		ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure(serialId, ProdOrderTargetMatItem.FEILD_REF_SERIALID);
		keyList.add(key1);
		if (!ServiceEntityStringHelper.checkNullString(tempMaterialUUID)) {
			ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure(tempMaterialUUID, "refMaterialSKUUUID");
			keyList.add(key2);
		}
		List<ServiceEntityNode> rawList = productionOrderManager
				.getEntityNodeListByKeyList(keyList, ProdOrderTargetMatItem.NODENAME, client, null);
		if (ServiceCollectionsHelper.checkNullList(rawList)) {
			return null;
		}
		return rawList;
	}

	private void genRegisteredProductCore(ProductionOrder productionOrder,
										   ProdOrderTargetMatItem prodOrderTargetMatItem,
			MaterialStockKeepUnit tempMaterialSKU, String logonUserUUID, String organizationUUID)
			throws ServiceEntityConfigureException, MaterialException {
		/*
		 * [Step1] Generate back-end registered product
		 */
		Organization targetOrganization = (Organization) organizationManager
				.getEntityNodeByKey(productionOrder.getRefWocUUID(), IServiceEntityNodeFieldConstant.UUID, Organization.NODENAME,
						null);
		// Generate register product instance and update into persistence
		RegisteredProductManager.RegisteredProductInvolvePartyMatrix registeredProductInvolvePartyMatrix = new RegisteredProductManager.RegisteredProductInvolvePartyMatrix();
		registeredProductInvolvePartyMatrix.productOrganization(targetOrganization).supportOrganization(targetOrganization);
		List<ServiceEntityNode> registeredProductList = registeredProductManager
				.createRegisteredProductWrapper(tempMaterialSKU,
						prodOrderTargetMatItem.getRefSerialId(),prodOrderTargetMatItem,
						registeredProductInvolvePartyMatrix, logonUserUUID, organizationUUID);
		if (!ServiceCollectionsHelper.checkNullList(registeredProductList)) {
			for (ServiceEntityNode seNode : registeredProductList) {
				if (RegisteredProduct.NODENAME.equals(seNode.getNodeName())) {
					RegisteredProduct registeredProduct = (RegisteredProduct) seNode;
					prodOrderTargetMatItem.setRefMaterialSKUUUID(seNode.getUuid());
					// Init unit price
					prodOrderTargetMatItem.setUnitPrice(registeredProduct.getUnitCost());
				}
			}
		}
		StorageCoreUnit storageCoreUnit = new StorageCoreUnit(tempMaterialSKU.getUuid(), prodOrderTargetMatItem.getRefUnitUUID(),
				prodOrderTargetMatItem.getAmount());
		double itemPrice = materialStockKeepUnitManager
				.calculatePrice(storageCoreUnit, tempMaterialSKU, tempMaterialSKU.getUnitCost());
		prodOrderTargetMatItem.setItemPrice(itemPrice);
	}

	/**
	 * Entrance to create inbound delivery & inbound item list for purchase
	 * contract and its' material item list
	 *
	 * @param prodOrderTargetMatItemList
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
	public synchronized void createInboundDeliveryBatch(ProductionOrderServiceModel productionOrderServiceModel,
			List<ServiceEntityNode> prodOrderTargetMatItemList, DeliveryMatItemBatchGenRequest genRequest, LogonInfo logonInfo) throws ServiceEntityConfigureException, SearchConfigureException, NodeNotFoundException,
            ServiceEntityInstallationException, ServiceModuleProxyException, ProductionOrderException, AuthorizationException, LogonInfoException, DocActionException {
		String organizationUUID = logonInfo.getResOrgUUID();
		String logonUserUUID = logonInfo.getRefUserUUID();
		if (ServiceCollectionsHelper.checkNullList(productionOrderServiceModel.getProdOrderTargetMatItemList())) {
			throw new ProductionOrderException(ProductionOrderException.PARA_NO_TARGETITEM);
		}
		if (ServiceCollectionsHelper.checkNullList(prodOrderTargetMatItemList)) {
			throw new ProductionOrderException(ProductionOrderException.PARA_NO_TARGETITEM);
		}
		/*
		 * [Step1] Raw data preparation, such as prepare all material S list
		 */
		List<ServiceEntityNode> allProdOrderTargetMatItemList = new ArrayList<>();
		productionOrderServiceModel.getProdOrderTargetMatItemList().forEach(productionOrderMaItemServiceModel -> {
			allProdOrderTargetMatItemList.add(productionOrderMaItemServiceModel.getProdOrderTargetMatItem());
		});
		List<ServiceEntityNode> rawMaterialSKUList = docFlowProxy
				.getRefMaterialSKUList(prodOrderTargetMatItemList, docMatItemNode -> {
					ProdOrderTargetMatItem productionOrderMaterialItem = (ProdOrderTargetMatItem) docMatItemNode;
					if (productionOrderMaterialItem.getItemStatus() != ProdOrderTargetMatItem.STATUS_DONE_PRODUCTION) {
						return false;
					}
					if (productionOrderMaterialItem.getNextDocType() != 0 && !ServiceEntityStringHelper
							.checkNullString(productionOrderMaterialItem.getNextDocMatItemUUID())) {
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
				.splitDocItemListByMaterialQualityFlag(prodOrderTargetMatItemList, rawMaterialSKUList,
						StandardSwitchProxy.SWITCH_OFF);
		List<ServiceEntityNode> prodTargetItemForQualityList = docFlowProxy
				.splitDocItemListByMaterialQualityFlag(prodOrderTargetMatItemList, rawMaterialSKUList, StandardSwitchProxy.SWITCH_ON);

		/*
		 * [Step3]: trying to get the Existed & Proper in-bound delivery item
		 * lists for this purchase contract.
		 */
		ProductionOrder productionOrder = productionOrderServiceModel.getProductionOrder();
		if (!ServiceCollectionsHelper.checkNullList(prodTargetItemForInboundList)) {
			prodOrderTargetItemToCrossInboundProxy
					.createDirectInboundBatch(productionOrder, allProdOrderTargetMatItemList, prodTargetItemForInboundList,
							rawMaterialSKUList, genRequest, logonInfo);
			// Important: replace items back to allProdOrderTargetMatItemList
			for(ServiceEntityNode seNode: prodTargetItemForInboundList){
				ServiceEntityNode oldSeNode = ServiceCollectionsHelper.filterSENodeOnline(seNode.getUuid(), allProdOrderTargetMatItemList);
				if(oldSeNode != null){
					allProdOrderTargetMatItemList.remove(oldSeNode);
					allProdOrderTargetMatItemList.add(seNode);
				}
			}
		}
		if (!ServiceCollectionsHelper.checkNullList(prodTargetItemForQualityList)) {
			prodOrderTargetItemToCrossQuaInspectProxy
					.createQualityInspectOrderBatch(productionOrder, allProdOrderTargetMatItemList, prodTargetItemForQualityList,
							rawMaterialSKUList, genRequest, logonInfo);
			// Important: replace items back to allProdOrderTargetMatItemList
			for(ServiceEntityNode seNode: prodTargetItemForQualityList){
				ServiceEntityNode oldSeNode = ServiceCollectionsHelper.filterSENodeOnline(seNode.getUuid(), allProdOrderTargetMatItemList);
				if(oldSeNode != null){
					allProdOrderTargetMatItemList.remove(oldSeNode);
					allProdOrderTargetMatItemList.add(seNode);
				}
			}
		}

		/*
		 * [Step4]: trying to set order complete automatically if all the target mat item is set to done
		 */
		if(checkAllProdTargetItemCompleted(allProdOrderTargetMatItemList)){
			// complete order automatically
			productionOrderManager.setOrderCompleteCore(productionOrder, logonUserUUID, organizationUUID);
		}
	}

	public boolean checkAllProdTargetItemCompleted(List<ServiceEntityNode> allProdOrderTargetMatItemList){
		if(ServiceCollectionsHelper.checkNullList(allProdOrderTargetMatItemList)){
			return true;
		}
		List<ServiceEntityNode> notDoneList = ServiceCollectionsHelper.filterSENodeListOnline(seNode->{
			ProdOrderTargetMatItem prodOrderTargetMatItem = (ProdOrderTargetMatItem) seNode;
			return prodOrderTargetMatItem.getItemStatus() != ProdOrderTargetMatItem.STATUS_DONE_PRODUCTION || ServiceEntityStringHelper
					.checkNullString(prodOrderTargetMatItem.getNextDocMatItemUUID());
		}, allProdOrderTargetMatItemList,true);
		if(!ServiceCollectionsHelper.checkNullList(notDoneList)){
			return false;
		}
		for(ServiceEntityNode seNode:allProdOrderTargetMatItemList){
			ProdOrderTargetMatItem prodOrderTargetMatItem = (ProdOrderTargetMatItem) seNode;
			ServiceEntityNode nextDoc = serviceDocumentComProxy.getNextDocItem(prodOrderTargetMatItem);
			if(nextDoc == null){
				return false;
			}
		}
		return true;
	}

	public void convMaterialSKUToTargetItemUI(MaterialStockKeepUnit materialStockKeepUnit, ProdOrderTargetMatItemUIModel prodOrderTargetMatItemUIModel, LogonInfo logonInfo){
		docFlowProxy.convMaterialSKUToItemUI(materialStockKeepUnit, prodOrderTargetMatItemUIModel);
		prodOrderTargetMatItemUIModel.setTraceMode(materialStockKeepUnit.getTraceMode());
		if(logonInfo != null){
			try {
				Map<Integer, String> traceModeMap = materialStockKeepUnitManager.initTraceModeMap(logonInfo.getLanguageCode());
				prodOrderTargetMatItemUIModel.setTraceModeValue(traceModeMap.get(materialStockKeepUnit.getTraceMode()));
			} catch (ServiceEntityInstallationException e) {
				// continue
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, materialStockKeepUnit.getId()));
			}
		}

	}

	/**
	 * @param prodOrderTargetMatItem
	 * @param prodOrderTargetMatItemUIModel
	 * @param logonInfo
	 */
	public void convProdOrderTargetMatItemToUI(ProdOrderTargetMatItem prodOrderTargetMatItem,
			ProdOrderTargetMatItemUIModel prodOrderTargetMatItemUIModel, LogonInfo logonInfo) {
		docFlowProxy.convDocMatItemToUI(prodOrderTargetMatItem, prodOrderTargetMatItemUIModel, logonInfo);
		prodOrderTargetMatItemUIModel.setItemStatus(prodOrderTargetMatItem.getItemStatus());
		prodOrderTargetMatItemUIModel.setRefSerialId(prodOrderTargetMatItem.getRefSerialId());
		prodOrderTargetMatItemUIModel.setProcessIndex(prodOrderTargetMatItem.getProcessIndex());
		try {
			prodOrderTargetMatItemUIModel
					.setSplitEnableFlag(splitMatItemProxy.calculateSplitFlag(prodOrderTargetMatItem));
		} catch (MaterialException | ServiceEntityConfigureException e) {
			// just skip
			logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
		}
		if (logonInfo != null) {
			try {
				Map<Integer, String> statusMap = initStatusMap(logonInfo.getLanguageCode());
				prodOrderTargetMatItemUIModel.setItemStatusValue(statusMap.get(prodOrderTargetMatItem.getItemStatus()));
			} catch (ServiceEntityInstallationException e) {
				// log error and continue
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, IDocItemNodeFieldConstant.itemStatus), e);
			}
		}
	}

	public void convUIToProdOrderTargetMatItem(ProdOrderTargetMatItemUIModel prodOrderTargetMatItemUIModel,
			ProdOrderTargetMatItem prodOrderTargetMatItem) {
		docFlowProxy.convUIToDocMatItem(prodOrderTargetMatItemUIModel, prodOrderTargetMatItem);
		if (prodOrderTargetMatItemUIModel.getItemStatus() > 0) {
			prodOrderTargetMatItem.setItemStatus(prodOrderTargetMatItemUIModel.getItemStatus());
		}
		if (!ServiceEntityStringHelper.checkNullString(prodOrderTargetMatItemUIModel.getRefSerialId())) {
			prodOrderTargetMatItem.setRefSerialId(prodOrderTargetMatItemUIModel.getRefSerialId());
		}
	}

	public void convProdOrderTarSubItemToUI(ProdOrderTarSubItem prodOrderTarSubItem,
			ProdOrderTarSubItemUIModel prodOrderTarSubItemUIModel, LogonInfo logonInfo) {
		docFlowProxy.convDocMatItemToUI(prodOrderTarSubItem, prodOrderTarSubItemUIModel, logonInfo);
		prodOrderTarSubItemUIModel.setLayer(prodOrderTarSubItem.getLayer());
		prodOrderTarSubItemUIModel.setRefParentItemUUID(prodOrderTarSubItem.getRefParentItemUUID());
		prodOrderTarSubItemUIModel.setRefBOMItemUUID(prodOrderTarSubItem.getRefBOMItemUUID());
	}

	public void convBillOfOrderItemToUI(BillOfMaterialItem billOfMaterialItem,
			ProdOrderTarSubItemUIModel prodOrderTarSubItemUIModel, LogonInfo logonInfo) {
		if (billOfMaterialItem != null && prodOrderTarSubItemUIModel != null) {
			prodOrderTarSubItemUIModel.setRefBOMItemId(billOfMaterialItem.getId());
		}
	}

	public void convUIToProdOrderTarSubItem(ProdOrderTarSubItemUIModel prodOrderTarSubItemUIModel,
			ProdOrderTarSubItem prodOrderTarSubItem) {
		docFlowProxy.convUIToDocMatItem(prodOrderTarSubItemUIModel, prodOrderTarSubItem);
		prodOrderTarSubItemUIModel.setLayer(prodOrderTarSubItem.getLayer());
		prodOrderTarSubItemUIModel.setRefParentItemUUID(prodOrderTarSubItem.getRefParentItemUUID());
		prodOrderTarSubItemUIModel.setRefBOMItemUUID(prodOrderTarSubItem.getRefBOMItemUUID());
	}

	/**
	 * Wrapper method to generate the Production target material item list batch
	 * as well as clear the previous ones
	 * [Important] Need to update into persistence outside
	 *
	 * @param productionOrderServiceModel
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 * @throws ProductionOrderException
	 * @throws BillOfMaterialException
	 * @throws ServiceModuleProxyException
	 * @throws ProdOrderReportException
	 */
	public List<ProdOrderTargetMatItemServiceModel> newProdTargetMatItemWrapper(
			ProductionOrderServiceModel productionOrderServiceModel,
			ProdPickingRefMaterialItem prodPickingRefMaterialItem, List<ServiceEntityNode> prevDocMatItemList, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, MaterialException, BillOfMaterialException, ProductionOrderException,
            ServiceModuleProxyException, DocActionException {
		/*
		 * [Step1] New Production Order Report
		 */
		ProductionOrder productionOrder = productionOrderServiceModel.getProductionOrder();
		MaterialStockKeepUnit tempMaterialSKU = (MaterialStockKeepUnit) materialStockKeepUnitManager
				.getEntityNodeByKey(productionOrder.getRefMaterialSKUUUID(), IServiceEntityNodeFieldConstant.UUID,
						MaterialStockKeepUnit.NODENAME, productionOrder.getClient(), null);
		// Remove the previous old target mat item list
		if (!ServiceCollectionsHelper.checkNullList(productionOrderServiceModel.getProdOrderTargetMatItemList())){
			productionOrderServiceModel.setProdOrderTargetMatItemList(new ArrayList<>());
		}

		/*
		 * [Step2] Get Material SKU temp serial number
		 */
		MatDecisionValueSetting matDecisionSerialFormat = matDecisionValueSettingManager
				.getDecisionValue(tempMaterialSKU, MatDecisionValueSettingManager.VAUSAGE_SERIALNUM_FORMAT);
		/*
		 * [Step3] Generate Material SKU item list
		 */
		StorageCoreUnit requestCoreUnit = new StorageCoreUnit(productionOrder.getRefMaterialSKUUUID(),
				productionOrder.getRefUnitUUID(), productionOrder.getAmount());
		List<ProdOrderTargetMatItemServiceModel> prodOrderTargetMatItemList = newTargetMatItem(requestCoreUnit,
				productionOrderServiceModel, matDecisionSerialFormat, prevDocMatItemList, tempMaterialSKU,
				serialLogonInfo);
		// Also merge into parent productionOrderServiceModel
		if (!ServiceCollectionsHelper.checkNullList(prodOrderTargetMatItemList)) {
			productionOrderServiceModel.setProdOrderTargetMatItemList(prodOrderTargetMatItemList);
		}
		/*
		 * [Step4] Binding to previous picking material item if possible
		 */
		// Try to find the prev picking material item
		if (prodPickingRefMaterialItem != null){
			bindingToPickingItemWrapper(
					prodOrderTargetMatItemList, prodPickingRefMaterialItem);
		} else {
			prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) prodPickingOrderManager
					.getEntityNodeByKey(productionOrder.getUuid(), "refNextOrderUUID", ProdPickingRefMaterialItem.NODENAME,
							productionOrder.getClient(), null);
			if (prodPickingRefMaterialItem != null){
				bindingToPickingItemWrapper(
						prodOrderTargetMatItemList, prodPickingRefMaterialItem);
			}
		}

		return prodOrderTargetMatItemList;
	}

	/**
	 * Utility method: Logic to binding the newly generated target mat item list
	 * to picking material item
	 *
	 * @param prodOrderTargetMatItemList
	 * @param prodPickingRefMaterialItem
	 */
	public void bindingToPickingItemWrapper(List<ProdOrderTargetMatItemServiceModel> prodOrderTargetMatItemList,
			ProdPickingRefMaterialItem prodPickingRefMaterialItem) {
		if (!ServiceCollectionsHelper.checkNullList(prodOrderTargetMatItemList)) {
			// Point target item prev to picking material item
			for (ProdOrderTargetMatItemServiceModel prodOrderTargetMatItemServiceModel : prodOrderTargetMatItemList) {
				ProdOrderTargetMatItem prodOrderTargetMatItem = prodOrderTargetMatItemServiceModel.getProdOrderTargetMatItem();
				prodOrderTargetMatItem.setPrevDocMatItemUUID(prodPickingRefMaterialItem.getUuid());
				prodOrderTargetMatItem.setPrevDocType(IDefDocumentResource.DOCUMENT_TYPE_PRODPICKINGORDER);
				prodPickingRefMaterialItem.setNextDocMatItemUUID(prodOrderTargetMatItem.getUuid());
				prodPickingRefMaterialItem.setNextDocType(IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER);
				// Also set document info to document type and refUUID
				prodPickingRefMaterialItem.setDocumentType(prodPickingRefMaterialItem.getNextDocType());
				prodPickingRefMaterialItem.setRefUUID(prodPickingRefMaterialItem.getNextDocMatItemUUID());
			}
		}
	}

	private List<ProdOrderTargetMatItemServiceModel> newTargetMatItem(StorageCoreUnit requestUnit,
			ProductionOrderServiceModel productionOrderServiceModel, MatDecisionValueSetting matDecisionSerialFormat,
			List<ServiceEntityNode> prevDocMatItemList, MaterialStockKeepUnit tempMaterialSKU, SerialLogonInfo serialLogonInfo)
            throws MaterialException, ServiceEntityConfigureException, BillOfMaterialException, ProductionOrderException,
            ServiceModuleProxyException, DocActionException {
		// Clone the productionServiceModel, during dispatching resources.
		// amount of prodProposal amount will be changed.
		ProductionOrderServiceModel productionOrderServiceModelBack = (ProductionOrderServiceModel) ServiceModuleCloneService
				.deepCloneServiceModule(productionOrderServiceModel, false);
		if (tempMaterialSKU.getTraceMode() == MaterialStockKeepUnit.TRACEMODE_SINGLE) {
			return newTargetMatItemSingleTrace(requestUnit, productionOrderServiceModelBack, matDecisionSerialFormat,
					prevDocMatItemList, tempMaterialSKU, serialLogonInfo);
		}
		if (tempMaterialSKU.getTraceMode() == MaterialStockKeepUnit.TRACEMODE_BATCH
				|| tempMaterialSKU.getTraceMode() == MaterialStockKeepUnit.TRACEMODE_NONE) {
			return newTargetMatItemBatch(requestUnit, productionOrderServiceModelBack, matDecisionSerialFormat, prevDocMatItemList, tempMaterialSKU,
					serialLogonInfo);
		}
		return null;
	}

	/**
	 * Core Logic to generate [DONE] ProdReport Item for Trace [Single] Material
	 * SKU
	 *
	 * @param requestUnit
	 * @param productionOrderServiceModel
	 * @param tempMaterialSKU
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 * @throws BillOfMaterialException
	 * @throws ProductionOrderException
	 */
	private List<ProdOrderTargetMatItemServiceModel> newTargetMatItemSingleTrace(StorageCoreUnit requestUnit,
			ProductionOrderServiceModel productionOrderServiceModel, MatDecisionValueSetting matDecisionSerialFormat,
			List<ServiceEntityNode> prevDocMatItemList, MaterialStockKeepUnit tempMaterialSKU, SerialLogonInfo serialLogonInfo)
            throws MaterialException, ServiceEntityConfigureException, ProductionOrderException, BillOfMaterialException, DocActionException {

		ProductionOrder productionOrder = productionOrderServiceModel.getProductionOrder();
		int totalStep = 0, index = 0, step = 1;
		StorageCoreUnit storageCoreUnit1 = new StorageCoreUnit(tempMaterialSKU.getUuid(),
				requestUnit.getRefUnitUUID(), requestUnit.getAmount());
		StorageCoreUnit storageCoreUnit2 = new StorageCoreUnit(tempMaterialSKU.getUuid(),
				materialStockKeepUnitManager.getMainUnitUUID(tempMaterialSKU), step);
		double totalAmount = materialStockKeepUnitManager
				.getStorageUnitRatio(storageCoreUnit1, storageCoreUnit2, productionOrder.getClient());
		List<ProdOrderTargetMatItemServiceModel> resultList = new ArrayList<>();

		while (totalStep < totalAmount) {
			totalStep += step;
			ProdOrderTargetMatItemServiceModel prodOrderTargetMatItemServiceModel = genTargetItemCore(index++,
					productionOrderServiceModel, matDecisionSerialFormat, prevDocMatItemList, tempMaterialSKU, step + 0,
					materialStockKeepUnitManager.getMainUnitUUID(tempMaterialSKU), serialLogonInfo);
			resultList.add(prodOrderTargetMatItemServiceModel);
		}
		return resultList;
	}

	/**
	 * Logic for generate the production item proposal including the sub
	 * requirement for out-bound delivery and production or purchase requirement
	 *
	 * @param productionOrderServiceModel
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 * @throws ProductionOrderException
	 */
	public List<ProdOrderTarSubItemServiceModel> dispatchProposalToSubItem(ProductionOrderServiceModel productionOrderServiceModel,
			BillOfMaterialOrder billOfMaterialOrder, ProdOrderTargetMatItem prodOrderTargetMatItem,
			List<ServiceEntityNode> productiveBOMList, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, MaterialException, ProductionOrderException, DocActionException {
		if (productiveBOMList == null || productiveBOMList.size() == 0) {
			return null;
		}
		/*
		 * [Step1] Calculate the base ratio from request to BOM
		 */
		ProductionOrder productionOrder = productionOrderServiceModel.getProductionOrder();
		StorageCoreUnit requestStorageCoreUnit = new StorageCoreUnit();
		requestStorageCoreUnit.setAmount(prodOrderTargetMatItem.getAmount());
		requestStorageCoreUnit.setRefMaterialSKUUUID(prodOrderTargetMatItem.getRefMaterialSKUUUID());
		requestStorageCoreUnit.setRefUnitUUID(prodOrderTargetMatItem.getRefUnitUUID());
		double ratio = productionOrderManager
				.getRatioFromProductionToBOMOrder(requestStorageCoreUnit, productionOrder.getRefBillOfMaterialUUID(),
						billOfMaterialOrder, productiveBOMList, prodOrderTargetMatItem.getClient());
		List<ProdOrderTarSubItemServiceModel> prodOrderTarSubItemList = new ArrayList<>();
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
		prodOrderTarSubItemList = dispatchProposalToSubItemByBOM(productionOrderServiceModel.getProductionOrderItemList(),
				prodOrderTargetMatItem, null, ratio, firstBomLayerList, productiveBOMList, serialLogonInfo);
		return prodOrderTarSubItemList;

	}

	public List<ProdOrderTarSubItemServiceModel> dispatchProposalToSubItemByBOM(
			List<ProductionOrderItemServiceModel> productionOrderItemServiceModelList, ProdOrderTargetMatItem parentTargetMatItem,
			ProdOrderTarSubItem parentTarSubItem, double ratio, List<ServiceEntityNode> curBOMList,
			List<ServiceEntityNode> productiveBOMList, SerialLogonInfo serialLogonInfo)
            throws ProductionOrderException, MaterialException, ServiceEntityConfigureException, DocActionException {
		if (ServiceCollectionsHelper.checkNullList(curBOMList)) {
			return null;
		}
		if (ServiceCollectionsHelper.checkNullList(productionOrderItemServiceModelList)) {
			return null;
		}
		List<ProdOrderTarSubItemServiceModel> prodOrderTarSubItemList = new ArrayList<>();
		for (ServiceEntityNode seNode : curBOMList) {
			ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
			double amount = productiveBOMItem.getAmount() * ratio;
			ProductionOrderItemServiceModel productionOrderItemServiceModel = ProductionOrderItemManager
					.filterProductionOrderItemByBOMItemUUID(productiveBOMItem.getUuid(), productionOrderItemServiceModelList);
			if (productionOrderItemServiceModel == null) {
				throw new ProductionOrderException(ProductionOrderException.PARA2_NO_PRODITEM_BYBOM, productiveBOMItem.getUuid(),
						productiveBOMItem.getRefMaterialSKUUUID());
			}
			// Calculate the amount with loss rate
			double amountWithLossRate = amount / (1 - productiveBOMItem.getTheoLossRate() / 100);
			amountWithLossRate = Math.ceil(amountWithLossRate);
			productiveBOMItem.setAmountWithLossRate(amountWithLossRate);
			List<ProdOrderItemReqProposalServiceModel> prodOrderItemReqProposalList = ProductionOrderItemManager
					.optimizeProposalListByStore(productionOrderItemServiceModel.getProdOrderItemReqProposalList());
			if (ServiceCollectionsHelper.checkNullList(prodOrderItemReqProposalList)) {
				throw new ProductionOrderException(ProductionOrderException.PARA_NO_PROPOSAL,
						productionOrderItemServiceModel.getProductionOrderItem().getUuid());
			}

			for (ProdOrderItemReqProposalServiceModel prodProposalServiceModel : prodOrderItemReqProposalList) {
				/*
				 * [3.1] Generate Sub item instance by calculate resources from
				 * proposal
				 */
				ProdOrderItemReqProposal prodOrderItemReqProposal = prodProposalServiceModel.getProdOrderItemReqProposal();
				if (prodOrderItemReqProposal.getAmount() <= 0) {
					continue;
				}
				ProdOrderTarSubItem prodOrderTarSubItem = genBatchTarSubItemCore(parentTargetMatItem, productiveBOMItem,
						prodOrderItemReqProposal, serialLogonInfo);
				if (parentTarSubItem != null) {
					prodOrderTarSubItem.setRefParentItemUUID(parentTarSubItem.getUuid());
				}
				prodOrderTarSubItem.setLayer(productiveBOMItem.getLayer());
				ProdOrderTarSubItemServiceModel prodOrderTarSubItemServiceModel = new ProdOrderTarSubItemServiceModel();
				prodOrderTarSubItemServiceModel.setProdOrderTarSubItem(prodOrderTarSubItem);
				prodOrderTarSubItemList.add(prodOrderTarSubItemServiceModel);

				/*
				 * [Step3.5] Important! Navigate to sub item
				 */
				List<ServiceEntityNode> subProductiveBOMList = billOfMaterialOrderManager
						.filterAllSubBOMItemList(productiveBOMItem.getUuid(), productiveBOMList);
				if (!ServiceCollectionsHelper.checkNullList(subProductiveBOMList)) {
					StorageCoreUnit requestStorageCoreUnit = new StorageCoreUnit();
					requestStorageCoreUnit.setAmount(prodOrderTarSubItem.getAmount());
					requestStorageCoreUnit.setRefMaterialSKUUUID(prodOrderTarSubItem.getRefMaterialSKUUUID());
					requestStorageCoreUnit.setRefUnitUUID(prodOrderTarSubItem.getRefUnitUUID());
					double nextRatio = productionOrderManager
							.getRatioFromProductionToBOMOrder(requestStorageCoreUnit, productiveBOMItem.getUuid(), null,
									productiveBOMList, prodOrderTarSubItem.getClient());
					List<ProdOrderTarSubItemServiceModel> childTarSubItemList = dispatchProposalToSubItemByBOM(
							prodProposalServiceModel.getProductionOrderItemList(), parentTargetMatItem, prodOrderTarSubItem, nextRatio,
							subProductiveBOMList, productiveBOMList, serialLogonInfo);
					if (!ServiceCollectionsHelper.checkNullList(childTarSubItemList)) {
						prodOrderTarSubItemList.addAll(childTarSubItemList);
					}
				}
				if (prodOrderItemReqProposal.getAmount() > 0) {
					// In case request is already meet from proposal, just break
					break;
				}
				if (productiveBOMItem.getAmountWithLossRate() <= 0) {
					// In case request already been meet
					break;
				}
			}
		}
		return prodOrderTarSubItemList;
	}

	/**
	 * [Internal method] Core Logic to create ProdTarSubItem List by
	 * distribution the resources from proposal, each proposal will equal to at
	 * least one Sub Item.
	 *
	 * @param parentTargetMatItem
	 * @param prodOrderItemReqProposal
	 * @return
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 */
	private ProdOrderTarSubItem genBatchTarSubItemCore(ProdOrderTargetMatItem parentTargetMatItem,
													   ProductiveBOMItem productiveBOMItem, ProdOrderItemReqProposal prodOrderItemReqProposal,
													   SerialLogonInfo serialLogonInfo)
            throws MaterialException, ServiceEntityConfigureException, DocActionException {
		/*
		 * [Step1] Compare current request and provided amount from proposal
		 */
		StorageCoreUnit subRequestStoreUnit = new StorageCoreUnit(productiveBOMItem.getRefMaterialSKUUUID(),
				productiveBOMItem.getRefUnitUUID(), productiveBOMItem.getAmountWithLossRate());
		StorageCoreUnit storeFromProposal = new StorageCoreUnit(prodOrderItemReqProposal.getRefMaterialSKUUUID(),
				prodOrderItemReqProposal.getRefUnitUUID(), prodOrderItemReqProposal.getAmount());
		StorageCoreUnit minusResult = materialStockKeepUnitManager
				.mergeStorageUnitCore(subRequestStoreUnit, storeFromProposal, StorageCoreUnit.OPERATOR_MINUS,
						parentTargetMatItem.getClient());
		List<ServiceEntityNode> resultList = new ArrayList<>();
		if (minusResult.getAmount() <= 0) {
			/*
			 * [Step2] In case proposal has larger provided amount than current
			 * request.
			 */
			ProdOrderTarSubItem prodOrderTarSubItem = (ProdOrderTarSubItem) productionOrderManager
					.newEntityNode(parentTargetMatItem, ProdOrderTarSubItem.NODENAME);
			docFlowProxy.buildItemPrevNextRelationship(prodOrderItemReqProposal,
					 prodOrderTarSubItem, null,serialLogonInfo);
			prodOrderTarSubItem.setAmount(subRequestStoreUnit.getAmount());
			prodOrderTarSubItem.setRefUnitUUID(subRequestStoreUnit.getRefUnitUUID());
			prodOrderTarSubItem.setRefMaterialSKUUUID(prodOrderItemReqProposal.getRefMaterialSKUUUID());
			prodOrderTarSubItem.setRefBOMItemUUID(productiveBOMItem.getUuid());
			prodOrderTarSubItem.setLayer(productiveBOMItem.getLayer());
			resultList.add(prodOrderTarSubItem);
			// minus from proposal and continue
			productiveBOMItem.setAmountWithLossRate(0);
			prodOrderItemReqProposal.setAmount(0 - minusResult.getAmount());
			return prodOrderTarSubItem;

		} else {
			/*
			 * [Step3] In case proposal provided amount can not meet current
			 * request
			 */
			ProdOrderTarSubItem prodOrderTarSubItem = (ProdOrderTarSubItem) productionOrderManager
					.newEntityNode(parentTargetMatItem, ProdOrderTarSubItem.NODENAME);
			docFlowProxy.buildItemPrevNextRelationship(prodOrderItemReqProposal, prodOrderTarSubItem,null,serialLogonInfo);
			prodOrderItemReqProposal.setAmount(0);
			prodOrderTarSubItem.setAmount(storeFromProposal.getAmount());
			prodOrderTarSubItem.setRefUnitUUID(storeFromProposal.getRefUnitUUID());
			productiveBOMItem.setAmountWithLossRate(minusResult.getAmount());
			productiveBOMItem.setRefUnitUUID(minusResult.getRefUnitUUID());
			prodOrderTarSubItem.setRefMaterialSKUUUID(prodOrderItemReqProposal.getRefMaterialSKUUUID());
			prodOrderTarSubItem.setRefBOMItemUUID(productiveBOMItem.getUuid());
			prodOrderTarSubItem.setLayer(productiveBOMItem.getLayer());
			resultList.add(prodOrderTarSubItem);
			return prodOrderTarSubItem;
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
	private List<ProdOrderTargetMatItemServiceModel> newTargetMatItemBatch(StorageCoreUnit requestUnit,
			ProductionOrderServiceModel productionOrderServiceModel, MatDecisionValueSetting matDecisionSerialFormat,
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

		ProductionOrder productionOrder = productionOrderServiceModel.getProductionOrder();
		BillOfMaterialOrder billOfMaterialOrder = billOfMaterialOrderManager
				.getRefBillOfMaterialOrderWrapper(productionOrder.getRefBillOfMaterialUUID(), productionOrder.getClient());
		List<ServiceEntityNode> productiveBOMList = billOfMaterialOrderManager.genProductiveBOMModel(billOfMaterialOrder);
		if (productiveBOMList == null || productiveBOMList.size() == 0) {
			return null;
		}

		List<ProdOrderTargetMatItemServiceModel> resultList = new ArrayList<>();
		ProdOrderTargetMatItemServiceModel prodOrderTargetMatItemServiceModel = new ProdOrderTargetMatItemServiceModel();
		ProdOrderTargetMatItem prodOrderTargetMatItem = (ProdOrderTargetMatItem) productionOrderManager
				.newEntityNode(productionOrder, ProdOrderTargetMatItem.NODENAME);
		// Also set RefMaterialSKUUUID's value in case for batch Material
		prodOrderTargetMatItem.setRefMaterialSKUUUID(tempMaterialSKU.getUuid());
		prodOrderTargetMatItem.setRefUnitUUID(requestUnit.getRefUnitUUID());
		prodOrderTargetMatItem.setAmount(requestUnit.getAmount());
		prodOrderTargetMatItem.setReservedMatItemUUID(productionOrder.getReservedMatItemUUID());
		prodOrderTargetMatItem.setReservedDocType(productionOrder.getReservedDocType());
		initCopyProductionOrderToTargetItem(productionOrder, prodOrderTargetMatItem);
		/*
		 * [Step2] Binding to previous doc mat item
		 */
		if(!ServiceCollectionsHelper.checkNullList(prevDocMatItemList)){
			DocMatItemNode prevMatItemNode = (DocMatItemNode) prevDocMatItemList.get(0);
			docFlowProxy.buildItemPrevNextRelationship(prevMatItemNode, prodOrderTargetMatItem, null,serialLogonInfo);
		}
		/*
		 * [Step3] Generate Trace Material SKU instance
		 */
		prodOrderTargetMatItem.setProcessIndex(index + 1);
		if(matDecisionSerialFormat != null){
			// serial number is not neccessary for batch
			String serialNumber = productionOrderManager
					.genSerialNumber(matDecisionSerialFormat, productionOrder, tempMaterialSKU, index);
			prodOrderTargetMatItem.setRefSerialId(serialNumber);
		}
		/*
		 * [Step3] Generate relative sub item list
		 */
		List<ProdOrderTarSubItemServiceModel> prodOrderTarSubItemList = dispatchProposalToSubItem(productionOrderServiceModel,
				billOfMaterialOrder, prodOrderTargetMatItem, productiveBOMList, serialLogonInfo);
		List<ProdOrderTargetMatItemServiceModel> prodOrderTargetMatItemServiceModelList = new ArrayList<>();

		prodOrderTargetMatItemServiceModel.setProdOrderTargetMatItem(prodOrderTargetMatItem);
		prodOrderTargetMatItemServiceModel.setProdOrderTarSubItemList(prodOrderTarSubItemList);
		resultList.add(prodOrderTargetMatItemServiceModel);
		return resultList;
	}

	/**
	 * [Internal method] init & copy information from production order to report
	 * item
	 */
	private void initCopyProductionOrderToTargetItem(ProductionOrder productionOrder,
			ProdOrderTargetMatItem prodOrderTargetMatItem) {
		if (prodOrderTargetMatItem != null) {
			prodOrderTargetMatItem.setReservedDocType(productionOrder.getReservedDocType());
			prodOrderTargetMatItem.setReservedMatItemUUID(productionOrder.getReservedMatItemUUID());
			prodOrderTargetMatItem.setPrevDocMatItemUUID(productionOrder.getReservedMatItemUUID());
			prodOrderTargetMatItem.setPrevDocType(productionOrder.getReservedDocType());
			prodOrderTargetMatItem.setProductionBatchNumber(productionOrder.getProductionBatchNumber());
			// initially, also set refMaterialSKUUUID as template SKU from order
			prodOrderTargetMatItem.setRefMaterialSKUUUID(productionOrder.getRefMaterialSKUUUID());
		}
	}

	private ProdOrderTargetMatItemServiceModel genTargetItemCore(int processIndex,
			ProductionOrderServiceModel productionOrderServiceModel, MatDecisionValueSetting matDecisionSerialFormat,
			List<ServiceEntityNode> prevDocMatItemList,
			MaterialStockKeepUnit tempMaterialSKU, double amount, String refUnitUUID, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, MaterialException, ProductionOrderException, BillOfMaterialException, DocActionException {
		/*
		 * [Step1] New ProdOrderReportItem
		 */

		ProductionOrder productionOrder = productionOrderServiceModel.getProductionOrder();
		BillOfMaterialOrder billOfMaterialOrder = billOfMaterialOrderManager
				.getRefBillOfMaterialOrderWrapper(productionOrder.getRefBillOfMaterialUUID(), productionOrder.getClient());
		List<ServiceEntityNode> productiveBOMList = billOfMaterialOrderManager.genProductiveBOMModel(billOfMaterialOrder);
		if (productiveBOMList == null || productiveBOMList.size() == 0) {
			return null;
		}
		ProdOrderTargetMatItemServiceModel prodOrderTargetMatItemServiceModel = new ProdOrderTargetMatItemServiceModel();
		ProdOrderTargetMatItem prodOrderTargetMatItem = (ProdOrderTargetMatItem) productionOrderManager
				.newEntityNode(productionOrder, ProdOrderTargetMatItem.NODENAME);
		// TODO default target item internal id
		prodOrderTargetMatItem.setId(productionOrder.getId() + "-" + processIndex);
		prodOrderTargetMatItem.setRefUnitUUID(refUnitUUID);
		prodOrderTargetMatItem.setAmount(amount);
		initCopyProductionOrderToTargetItem(productionOrder, prodOrderTargetMatItem);
		/*
		 * [Step2] Binding to previous doc mat item
		 */
		if(!ServiceCollectionsHelper.checkNullList(prevDocMatItemList)){
			DocMatItemNode prevMatItemNode = (DocMatItemNode) prevDocMatItemList.get(0);
			docFlowProxy.buildItemPrevNextRelationship(prevMatItemNode, prodOrderTargetMatItem, null,serialLogonInfo);
		}
		prodOrderTargetMatItem.setUnitPrice(tempMaterialSKU.getUnitCost());
		StorageCoreUnit storageCoreUnit1 = new StorageCoreUnit();
		storageCoreUnit1.setRefMaterialSKUUUID(tempMaterialSKU.getUuid());
		storageCoreUnit1.setAmount(amount);
		storageCoreUnit1.setRefUnitUUID(refUnitUUID);
		StorageCoreUnit storageCoreUnit2 = new StorageCoreUnit();
		storageCoreUnit2.setRefMaterialSKUUUID(tempMaterialSKU.getUuid());
		storageCoreUnit2.setAmount(1);
		storageCoreUnit2.setRefUnitUUID(materialStockKeepUnitManager.getMainUnitUUID(tempMaterialSKU));
		double itemAmount = materialStockKeepUnitManager
				.getStorageUnitRatio(storageCoreUnit1, storageCoreUnit2, productionOrder.getClient());
		prodOrderTargetMatItem.setItemPrice(itemAmount * tempMaterialSKU.getUnitCost());
		/*
		 * [Step2] Generate Trace Material SKU instance
		 */
		// Initial processIndex is zero
		prodOrderTargetMatItem.setProductionBatchNumber(productionOrder.getProductionBatchNumber());
		prodOrderTargetMatItem.setReservedDocType(productionOrder.getReservedDocType());
		prodOrderTargetMatItem.setReservedMatItemUUID(productionOrder.getReservedMatItemUUID());
		prodOrderTargetMatItem.setProcessIndex(processIndex + 1);
		String serialNumber = processIndex + "";
		if (!ServiceEntityStringHelper.checkNullString(serialNumber) && matDecisionSerialFormat != null) {
			serialNumber = productionOrderManager
					.genSerialNumber(matDecisionSerialFormat, productionOrder, tempMaterialSKU, processIndex);
		}
		prodOrderTargetMatItem.setRefSerialId(serialNumber);
		/*
		 * [Step3] Generate relative sub item list
		 */
		List<ProdOrderTarSubItemServiceModel> prodOrderTarSubItemList = dispatchProposalToSubItem(productionOrderServiceModel,
				billOfMaterialOrder, prodOrderTargetMatItem, productiveBOMList, serialLogonInfo);
		prodOrderTargetMatItemServiceModel.setProdOrderTargetMatItem(prodOrderTargetMatItem);
		prodOrderTargetMatItemServiceModel.setProdOrderTarSubItemList(prodOrderTarSubItemList);
		return prodOrderTargetMatItemServiceModel;
	}

	public ServiceDocumentExtendUIModel convProdOrderTargetMatItemToDocExtUIModel(
			ProdOrderTargetMatItemUIModel prodOrderTargetMatItemUIModel, LogonInfo logonInfo) throws ServiceEntityInstallationException {
		ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
		docFlowProxy.convDocMatItemUIToDocExtUIModel(prodOrderTargetMatItemUIModel, serviceDocumentExtendUIModel, logonInfo,
				IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER);
		serviceDocumentExtendUIModel.setRefUIModel(prodOrderTargetMatItemUIModel);
		serviceDocumentExtendUIModel.setUuid(prodOrderTargetMatItemUIModel.getUuid());
		serviceDocumentExtendUIModel.setParentNodeUUID(prodOrderTargetMatItemUIModel.getParentNodeUUID());
		serviceDocumentExtendUIModel.setRootNodeUUID(prodOrderTargetMatItemUIModel.getRootNodeUUID());
		serviceDocumentExtendUIModel.setId(prodOrderTargetMatItemUIModel.getId());
		if(ServiceEntityStringHelper.checkNullString(prodOrderTargetMatItemUIModel.getId())){
			serviceDocumentExtendUIModel.setId(prodOrderTargetMatItemUIModel.getParentDocId());
		}
		serviceDocumentExtendUIModel.setName(prodOrderTargetMatItemUIModel.getRefMaterialSKUName());
		serviceDocumentExtendUIModel.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER);
		if(logonInfo != null){
			serviceDocumentExtendUIModel
					.setDocumentTypeValue(serviceDocumentComProxy
							.getDocumentTypeValue(IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER,
									logonInfo.getLanguageCode()));
		}
		serviceDocumentExtendUIModel.setRefMaterialSKUUUID(prodOrderTargetMatItemUIModel.getRefMaterialSKUUUID());
		serviceDocumentExtendUIModel.setRefMaterialSKUId(prodOrderTargetMatItemUIModel.getRefMaterialSKUId());
		serviceDocumentExtendUIModel.setRefMaterialSKUName(prodOrderTargetMatItemUIModel.getRefMaterialSKUName());
		String referenceDate = prodOrderTargetMatItemUIModel.getCreatedDate();
		serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
		return serviceDocumentExtendUIModel;
	}

	public ServiceDocumentExtendUIModel convToDocumentExtendUIModel(ServiceEntityNode seNode, LogonInfo logonInfo) {
		if (seNode == null) {
			return null;
		}
		if (ProdOrderTargetMatItem.NODENAME.equals(seNode.getNodeName())) {
			ProdOrderTargetMatItem prodOrderTargetMatItem = (ProdOrderTargetMatItem) seNode;
			try {
				ProdOrderTargetMatItemUIModel prodOrderTargetMatItemUIModel = (ProdOrderTargetMatItemUIModel) productionOrderManager
						.genUIModelFromUIModelExtension(ProdOrderTargetMatItemUIModel.class,
								prodOrderTargetMatItemServiceUIModelExtension.genUIModelExtensionUnion().get(0), prodOrderTargetMatItem,
								logonInfo, null);
				ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = convProdOrderTargetMatItemToDocExtUIModel(
						prodOrderTargetMatItemUIModel, logonInfo);
				return serviceDocumentExtendUIModel;
			} catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ProductionOrderItem.NODENAME));
			}
		}
		return null;

	}

}
