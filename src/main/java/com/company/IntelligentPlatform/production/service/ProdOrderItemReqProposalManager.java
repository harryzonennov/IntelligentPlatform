package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryManager;
import com.company.IntelligentPlatform.production.model.ProdItemRequestUnionTemplate;
import com.company.IntelligentPlatform.production.model.ProdOrderItemReqProposal;
import com.company.IntelligentPlatform.production.model.ProdPickingOrder;
import com.company.IntelligentPlatform.production.model.ProdPickingRefMaterialItem;
import com.company.IntelligentPlatform.production.model.ProductionOrderItem;
import com.company.IntelligentPlatform.production.service.ProdPickingExtendAmountModel;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.IServiceEntityCommonFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;


@Service
public class ProdOrderItemReqProposalManager {

	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected ProductionOrderItemManager productionOrderItemManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ProdPickingOrderManager prodPickingOrderManager;

	@Autowired
	protected ProdPickingRefMaterialItemManager prodPickingRefMaterialItemManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	protected Logger logger = LoggerFactory.getLogger(ProdOrderItemReqProposalManager.class);

	public List<PageHeaderModel> getPageHeaderModelList(ProdOrderItemReqProposal prodOrderItemReqProposal, String client)
			throws ServiceEntityConfigureException {
		ProductionOrderItem productionOrderItem = (ProductionOrderItem) productionOrderManager
				.getEntityNodeByKey(prodOrderItemReqProposal.getParentNodeUUID(), IServiceEntityNodeFieldConstant.UUID,
						ProductionOrderItem.NODENAME, client, null);
		int index = 0;
		List<PageHeaderModel> resultList = new ArrayList<PageHeaderModel>();
		if (productionOrderItem != null) {
			List<PageHeaderModel> pageHeaderModelList = productionOrderItemManager
					.getPageHeaderModelList(productionOrderItem, client);
			if (!ServiceCollectionsHelper.checkNullList(pageHeaderModelList)) {
				resultList.addAll(pageHeaderModelList);
				index = pageHeaderModelList.size();
			}
			PageHeaderModel itemHeaderModel = getPageHeaderModel(prodOrderItemReqProposal, index);
			if (itemHeaderModel != null) {
				resultList.add(itemHeaderModel);
			}
		}
		return resultList;
	}

	protected PageHeaderModel getPageHeaderModel(ProdOrderItemReqProposal prodOrderItemReqProposal, int index)
			throws ServiceEntityConfigureException {
		if (prodOrderItemReqProposal == null) {
			return null;
		}
		PageHeaderModel pageHeaderModel = new PageHeaderModel();
		pageHeaderModel.setPageTitle("prodOrderItemReqProposalTitle");
		pageHeaderModel.setNodeInstId(ProdOrderItemReqProposal.NODENAME);
		pageHeaderModel.setUuid(prodOrderItemReqProposal.getUuid());
		MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
				.getEntityNodeByKey(prodOrderItemReqProposal.getRefMaterialSKUUUID(), IServiceEntityNodeFieldConstant.UUID,
						MaterialStockKeepUnit.NODENAME, prodOrderItemReqProposal.getClient(), null);
		if (materialStockKeepUnit != null) {
			pageHeaderModel.setHeaderName(materialStockKeepUnit.getName());
		}
		pageHeaderModel.setIndex(index);
		return pageHeaderModel;
	}

	/**
	 * Update proposal reference doc information logic
	 *
	 * @param prodOrderItemReqProposal
	 * @param targetDocMatItemNode
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 */
	@Deprecated
	public void updateProposalRefDocInfo(ProdOrderItemReqProposal prodOrderItemReqProposal, DocMatItemNode targetDocMatItemNode)
            throws DocActionException {
		if (targetDocMatItemNode == null) {
			List<ServiceEntityNode> docMatItemList = getAllEndDocMatItemList(prodOrderItemReqProposal, true);
			if (ServiceCollectionsHelper.checkNullList(docMatItemList)) {
				return;
			}
			targetDocMatItemNode = prodPickingRefMaterialItemManager.calculateTargetEndDocListByWeight(docMatItemList, true);
			if (targetDocMatItemNode == null) {
				return;
			}
		}
		int documentType = serviceDocumentComProxy.getDocumentTypeByModelName(targetDocMatItemNode.getNodeName());
		prodOrderItemReqProposal.setRefUUID(targetDocMatItemNode.getUuid());
		prodOrderItemReqProposal.setDocumentType(documentType);
	}


	public List<ServiceEntityNode> getInprocessDocMatItemList(ProdOrderItemReqProposal prodOrderItemReqProposal)
            throws MaterialException, ServiceEntityConfigureException, DocActionException {
		if (IDefDocumentResource.DOCUMENT_TYPE_PRODPICKINGORDER != prodOrderItemReqProposal.getNextDocType()) {
			return null;
		}
		ProdPickingRefMaterialItem nextPickingRefMaterialItem = (ProdPickingRefMaterialItem) docFlowProxy
				.getNextDocItemNode(prodOrderItemReqProposal);
		if (nextPickingRefMaterialItem == null) {
			return null;
		}
		return prodPickingRefMaterialItemManager.getInProcessDocMatItemList(nextPickingRefMaterialItem);
	}

	public List<ServiceEntityNode> getAllEndDocMatItemList(ProdOrderItemReqProposal prodOrderItemReqProposal,
			boolean checkMaterialStatus) throws DocActionException {
		if (IDefDocumentResource.DOCUMENT_TYPE_PRODPICKINGORDER != prodOrderItemReqProposal.getNextDocType()) {
			return null;
		}
		ProdPickingRefMaterialItem nextPickingRefMaterialItem = (ProdPickingRefMaterialItem) docFlowProxy
				.getNextDocItemNode(prodOrderItemReqProposal);
		if (nextPickingRefMaterialItem == null) {
			return null;
		}
		return prodPickingRefMaterialItemManager.getAllEndDocMatItemList(nextPickingRefMaterialItem, checkMaterialStatus);
	}

	public List<ServiceDocumentExtendUIModel> getAllEndDocMatItemUIModelList(ProdOrderItemReqProposal prodOrderItemReqProposal,
			LogonInfo logonInfo, boolean checkMaterialStatus)
			throws MaterialException, ServiceEntityConfigureException, DocActionException {
		List<ServiceEntityNode> endDocMatItemList = getAllEndDocMatItemList(prodOrderItemReqProposal, checkMaterialStatus);
		if (ServiceCollectionsHelper.checkNullList(endDocMatItemList)) {
			return null;
		}
		List<ServiceDocumentExtendUIModel> endDocumentExtendUIModelList = new ArrayList<ServiceDocumentExtendUIModel>();
		for (ServiceEntityNode seNode : endDocMatItemList) {
			ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = docFlowProxy.convToDocumentExtendUIModel(seNode, logonInfo);
			endDocumentExtendUIModelList.add(serviceDocumentExtendUIModel);
		}
		return endDocumentExtendUIModelList;
	}



	/**
	 * Logic to Set Finish to next picking ref Material item instance
	 *
	 * @param prodOrderItemReqProposal
	 */
	public void setFinishPickingRefMaterialItem(ProdOrderItemReqProposal prodOrderItemReqProposal, String logonUserUUID,
			String organizationUUID) throws ProductionOrderException, ServiceEntityConfigureException {
		ProdPickingRefMaterialItem nextPickingRefMaterialItem = (ProdPickingRefMaterialItem) docFlowProxy
				.getNextDocItemNode(prodOrderItemReqProposal);
		if (nextPickingRefMaterialItem == null) {
			throw new ProductionOrderException(ProductionOrderException.PARA_NO_PROPOSAL, prodOrderItemReqProposal.getUuid());
		}
		ProdPickingOrder prodPickingOrder = (ProdPickingOrder) prodPickingOrderManager
				.getEntityNodeByKey(nextPickingRefMaterialItem.getRootNodeUUID(), IServiceEntityNodeFieldConstant.UUID,
						ProdPickingOrder.NODENAME, nextPickingRefMaterialItem.getClient(), null);
		prodPickingOrderManager.setFinishPickingRefMaterialItem(nextPickingRefMaterialItem, prodPickingOrder, false, logonUserUUID,
				organizationUUID);
	}

	/**
	 * Logic to get all the possible next picking material item from this proposal
	 *
	 * @param prodOrderItemReqProposal
	 * @return
	 */
	public List<ServiceEntityNode> getNextPickingMatItemList(ProdOrderItemReqProposal prodOrderItemReqProposal) {
		List<ServiceEntityNode> resultList = new ArrayList<>();
		ProdPickingRefMaterialItem nextPickingRefMaterialItem = (ProdPickingRefMaterialItem) docFlowProxy
				.getNextDocItemNode(prodOrderItemReqProposal);
		if (nextPickingRefMaterialItem != null) {
			resultList.add(nextPickingRefMaterialItem);
		}
		List<ServiceEntityNode> nextPickingMatItemList = docFlowProxy
				.getDefDocItemNodeList(IDefDocumentResource.DOCUMENT_TYPE_PRODPICKINGORDER, prodOrderItemReqProposal.getUuid(),
						IServiceEntityCommonFieldConstant.PREVDOCMATITEMUUID, prodOrderItemReqProposal.getClient());
		ServiceCollectionsHelper.mergeToList(resultList, nextPickingMatItemList);
		return resultList;
	}

	/**
	 * Core Logic to calculate each kind of amount and refresh to Proposal UI
	 * Model
	 *
	 * @param prodOrderItemReqProposal
	 * @param rawPickingExtendAmountModelList
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public void refreshItemStatus(ProdOrderItemReqProposal prodOrderItemReqProposal,
			List<ProdPickingExtendAmountModel> rawPickingExtendAmountModelList)
			throws ServiceEntityConfigureException, MaterialException, ServiceComExecuteException {
		/*
		 * [Step1] Pre-check exceptions
		 */
		List<ServiceEntityNode> nextPickingMatItemList = getNextPickingMatItemList(prodOrderItemReqProposal);
		if (ServiceCollectionsHelper.checkNullList(nextPickingMatItemList)) {
			return;
		}
		List<ProdPickingExtendAmountModel> filteredPickingExtendAmountModelList = prodPickingRefMaterialItemManager
				.filterPickingExtendModelListOnline(prodOrderItemReqProposal.getNextDocMatItemUUID(), tempPickingRefMaterial->{
					return tempPickingRefMaterial.getUuid();
				}, rawPickingExtendAmountModelList);
		if (ServiceCollectionsHelper.checkNullList(filteredPickingExtendAmountModelList)) {
			logger.error("empty picking under this proposal:" + prodOrderItemReqProposal.getUuid());
			return;
		}
		ProdItemRequestUnionTemplate prodItemRequestUnionTemplate = new ProdItemRequestUnionTemplate();
		prodItemRequestUnionTemplate = productionOrderItemManager
				.copyOrderItemReqProposalToUnionTemplate(prodOrderItemReqProposal, prodItemRequestUnionTemplate);
		/*
		 * [Step2] Call logic to refresh amount / status key information by all sub picking material item
		 */
		productionOrderItemManager.refreshItemInfoByPickingItemCore(prodItemRequestUnionTemplate, filteredPickingExtendAmountModelList,
				nextPickingMatItemList);
		productionOrderItemManager.copyUnionTemplateToOrderItemReqProposal(prodItemRequestUnionTemplate, prodOrderItemReqProposal);
		// [Step 2.1] Update proposal amount by all supplied amount
		prodOrderItemReqProposal.setAmount(prodOrderItemReqProposal.getSuppliedAmount());

		/*
		 * [Step3] Update end document information
		 */
		updateEndDocInfo(prodOrderItemReqProposal, filteredPickingExtendAmountModelList);
		/*
		 * [Step4] Calculate: Update item status
		 */
		updateItemStatus(prodOrderItemReqProposal);
	}

	private void updateEndDocInfo(ProdOrderItemReqProposal prodOrderItemReqProposal,
			List<ProdPickingExtendAmountModel> subPickingExtendAmountModelList) {
		if (ServiceCollectionsHelper.checkNullList(subPickingExtendAmountModelList)) {
			return;
		}
		List<ServiceEntityNode> endMatItemList = new ArrayList<>();
		for (ProdPickingExtendAmountModel prodPickingExtendAmountModel : subPickingExtendAmountModelList) {
			List<ServiceEntityNode> docMatItemList = prodPickingExtendAmountModel.getEndDocMatItemList();
			if (!ServiceCollectionsHelper.checkNullList(docMatItemList)) {
				endMatItemList.addAll(docMatItemList);
			}
		}
		if (ServiceCollectionsHelper.checkNullList(endMatItemList)) {
			return;
		}
		DocMatItemNode targetDocMatItemNode = prodPickingRefMaterialItemManager
				.calculateTargetEndDocListByWeight(endMatItemList, true);
		if (targetDocMatItemNode == null) {
			return;
		}
		int documentType = targetDocMatItemNode.getHomeDocumentType();
		if (documentType == 0) {
			documentType = serviceDocumentComProxy.getDocumentTypeByModelName(targetDocMatItemNode.getNodeName());
		}
		prodOrderItemReqProposal.setRefUUID(targetDocMatItemNode.getUuid());
		prodOrderItemReqProposal.setDocumentType(documentType);
	}

	/**
	 * Core Logic to calculate each kind of amount and refresh to Proposal UI
	 * Model
	 *
	 * @param prodOrderItemReqProposal
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	@Deprecated
	public void refreshItemStatus(ProdOrderItemReqProposal prodOrderItemReqProposal)
            throws ServiceEntityConfigureException, MaterialException, DocActionException {
		/*
		 * [Step1] Pre-check exceptions
		 */
		if (IDefDocumentResource.DOCUMENT_TYPE_PRODPICKINGORDER != prodOrderItemReqProposal.getNextDocType()) {
			return;
		}
		ProdPickingRefMaterialItem nextPickingRefMaterialItem = (ProdPickingRefMaterialItem) docFlowProxy
				.getNextDocItemNode(prodOrderItemReqProposal);
		if (nextPickingRefMaterialItem == null) {
			return;
		}
		/*
		 * [Step2] In case reference picking has been finished
		 */
		// Copy pick status
		prodOrderItemReqProposal.setPickStatus(nextPickingRefMaterialItem.getItemStatus());
		if (nextPickingRefMaterialItem.getItemStatus() == ProdPickingRefMaterialItem.ITEMSTATUS_FINISHED) {
			// Set supplied amount = picked amount, to pick amount, in process amount is zero.
			prodOrderItemReqProposal.setItemStatus(ProductionOrderItem.STATUS_ALL_DONE);
			prodOrderItemReqProposal.setSuppliedAmount(nextPickingRefMaterialItem.getAmount());
			prodOrderItemReqProposal.setPickedAmount(nextPickingRefMaterialItem.getAmount());
			prodOrderItemReqProposal.setInStockAmount(0);
			prodOrderItemReqProposal.setToPickAmount(0);
			prodOrderItemReqProposal.setInProcessAmount(0);
			return;
		}

		//List<StorageCoreUnit> allSuppliedStorageList = new ArrayList<StorageCoreUnit>();
		/*
		 * [Step3] Calculate: all in stock amount, exclude the amount for existed out bound
		 */
		StorageCoreUnit reservedInStockStorageCoreUnit = prodPickingRefMaterialItemManager
				.getReservedStoreAmount(nextPickingRefMaterialItem);
		if (reservedInStockStorageCoreUnit != null && reservedInStockStorageCoreUnit.getAmount() != 0) {
			//allSuppliedStorageList.add(reservedInStockStorageCoreUnit);
			prodOrderItemReqProposal.setInStockAmount(reservedInStockStorageCoreUnit.getAmount());
		} else {
			prodOrderItemReqProposal.setInStockAmount(0);
		}
		/*
		 * [Step4] Calculate: all in process amount
		 */
		StorageCoreUnit inProcessStorageCoreUnit = prodPickingRefMaterialItemManager.getInProcessAmount(nextPickingRefMaterialItem);
		if (inProcessStorageCoreUnit != null && inProcessStorageCoreUnit.getAmount() != 0) {
			//allSuppliedStorageList.add(inProcessStorageCoreUnit);
			prodOrderItemReqProposal.setInProcessAmount(inProcessStorageCoreUnit.getAmount());
		} else {
			prodOrderItemReqProposal.setInProcessAmount(0);
		}
		/*
		 * [Step5] Calculate: all to pick amount is equal to none-picked out bound
		 */
		StorageCoreUnit outStorageUnit = prodPickingRefMaterialItemManager.getReservedOutboundAmount(nextPickingRefMaterialItem, true);
		if (outStorageUnit != null) {
			// allSuppliedStorageList.add(outStorageUnit);
			// temporary conversion to same refUnitUUID as production order item
			outStorageUnit = materialStockKeepUnitManager
					.convertUnit(outStorageUnit, prodOrderItemReqProposal.getRefUnitUUID(), prodOrderItemReqProposal.getClient());
			prodOrderItemReqProposal.setToPickAmount(outStorageUnit.getAmount());
		} else {
			prodOrderItemReqProposal.setToPickAmount(0);
		}
		StorageCoreUnit suppliedStorageCoreUnit = prodPickingRefMaterialItemManager
				.getSuppliedStoreAmount(nextPickingRefMaterialItem);
		if (suppliedStorageCoreUnit != null) {
			prodOrderItemReqProposal.setSuppliedAmount(suppliedStorageCoreUnit.getAmount());
		} else {
			prodOrderItemReqProposal.setSuppliedAmount(0);
		}
		/*
		 * [Step6] Calculate: Update item status
		 */
		updateItemStatus(prodOrderItemReqProposal);
	}

	/**
	 * Logic to calculate not in-plan amount, this method should be invoked
	 * after the availableAmount and in-process amount is calculated
	 *
	 * @param prodOrderItemReqProposal
	 * @return
	 */
	public static void updateItemStatus(ProdOrderItemReqProposal prodOrderItemReqProposal) {
		/*
		 * [Case1] In Process Amount != 0: default in process
		 */
		if (prodOrderItemReqProposal.getInProcessAmount() != 0) {
			prodOrderItemReqProposal.setItemStatus(ProductionOrderItem.STATUS_INPROCESS);
			/*
			 * [Case1.1] If there is already some picked, set part done
			 */
			if (prodOrderItemReqProposal.getPickedAmount() != 0) {
				prodOrderItemReqProposal.setItemStatus(ProductionOrderItem.STATUS_PART_DONE);
			}
			/*
			 * [Case1.2] If there is some available in stock at the same time, set part available
			 * available is higher priority than picked
			 */
			if (prodOrderItemReqProposal.getInStockAmount() != 0 || prodOrderItemReqProposal.getToPickAmount() != 0) {
				prodOrderItemReqProposal.setItemStatus(ProductionOrderItem.STATUS_PART_AVAILABLE);
			}
			return;
		}
		/*
		 * [Case2] In Stock Or To Pick != 0: default in available
		 */
		if (prodOrderItemReqProposal.getInStockAmount() != 0 || prodOrderItemReqProposal.getToPickAmount() != 0) {
			// Default Available: avilable status is with highest priority
			prodOrderItemReqProposal.setItemStatus(ProductionOrderItem.STATUS_AVAILABLE);
			// [Case2.2] Check if not in plan amount
			return;
		}
		/*
		 * [Case3] Picked Amount != 0
		 */
		if (prodOrderItemReqProposal.getPickedAmount() != 0) {
			if (prodOrderItemReqProposal.getInProcessAmount() == 0 && prodOrderItemReqProposal.getInStockAmount() == 0
					&& prodOrderItemReqProposal.getToPickAmount() == 0) {
				prodOrderItemReqProposal.setItemStatus(ProductionOrderItem.STATUS_ALL_DONE);
				return;
			}
			/*
			 * [Case3.2] If there is some in process, set as part done
			 * picked and done is higher priority than in process
			 */
			if (prodOrderItemReqProposal.getInProcessAmount() != 0) {
				prodOrderItemReqProposal.setItemStatus(ProductionOrderItem.STATUS_PART_DONE);
			}
			/*
			 * [Case3.2] If there is some available in stock at the same time, set part available
			 * available is higher priority than picked/done
			 */
			if (prodOrderItemReqProposal.getInStockAmount() != 0 || prodOrderItemReqProposal.getToPickAmount() != 0) {
				// Default Available
				prodOrderItemReqProposal.setItemStatus(ProductionOrderItem.STATUS_AVAILABLE);
			}
		}
	}


}
