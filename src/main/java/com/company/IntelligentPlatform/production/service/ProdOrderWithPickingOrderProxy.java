package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.production.dto.ProdPickingRefMaterialInitialUIModel;
import com.company.IntelligentPlatform.production.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.MaterialStockKeepUnitSearchModel;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.SearchContext;
import com.company.IntelligentPlatform.common.service.SearchContextBuilder;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;


@Service
public class ProdOrderWithPickingOrderProxy {

	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected ProdPickingOrderManager prodPickingOrderManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	/**
	 * [Internal method] To avoid deep sub proposal from production resource
	 * generation, only return the top-level proposal
	 *
	 * @param productionProposalItemList
	 * @param productionOrderItem
	 * @return
	 */
	private List<ServiceEntityNode> filterOutTopProposalList(List<ServiceEntityNode> productionProposalItemList,
			ProductionOrderItem productionOrderItem) {
		List<ServiceEntityNode> resultList = new ArrayList<>();
		if (ServiceCollectionsHelper.checkNullList(productionProposalItemList)) {
			return null;
		}
		for (ServiceEntityNode seNode : productionProposalItemList) {
			if (productionOrderItem.getUuid().equals(seNode.getParentNodeUUID())) {
				resultList.add(seNode);
			}
		}
		return resultList;

	}

	/**
	 * Main entrance to update request for material to picking order persistence
	 * attached to production order item.
	 *
	 * @param requestCoreUnit
	 * @param productionOrderItem
	 * @param updateProposal:     weather need to update proposal list to DB here
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceEntityInstallationException
	 * @throws NodeNotFoundException
	 * @throws SearchConfigureException
	 * @throws BillOfMaterialException
	 * @throws MaterialException
	 * @throws ServiceModuleProxyException
	 */
	public List<ServiceEntityNode> updateRequestToPickingOrderWrapper(StorageCoreUnit requestCoreUnit,
																	  ProductionOrderItem productionOrderItem, List<ServiceEntityNode> productionProposalItemList, int itemProcessType,
																	  boolean updateProposal, LogonInfo logonInfo) throws ServiceEntityConfigureException, MaterialException,
            BillOfMaterialException, SearchConfigureException, NodeNotFoundException, ServiceEntityInstallationException, ServiceModuleProxyException,
            ServiceComExecuteException, AuthorizationException, LogonInfoException, DocActionException {
		/*
		 * [Step1] Trying to get proper ref order item
		 */
		if (requestCoreUnit.getAmount() == 0) {
			return null;
		}
		ProdPickingRefOrderItem extPickingRefOrderItem = genProperRefOrderItemForAddMaterial(productionOrderItem);
		ProductionOrder productionOrder = (ProductionOrder) productionOrderManager
				.getEntityNodeByKey(productionOrderItem.getRootNodeUUID(), IServiceEntityNodeFieldConstant.UUID,
						ProductionOrder.NODENAME, productionOrderItem.getClient(), null);
		/*
		 * [Step2] Call core method to generate proposal list
		 */
		// [Step2.1] Important, should convert refUnit to main unit firstly
		String refUnitUUID = requestCoreUnit.getRefUnitUUID();
		String mainUnitUUID = materialStockKeepUnitManager.getMainUnitUUID(requestCoreUnit.getRefMaterialSKUUUID());
		StorageCoreUnit requestCoreUnitBack = (StorageCoreUnit) requestCoreUnit.clone();
		if (!refUnitUUID.equals(mainUnitUUID)) {
			requestCoreUnitBack = materialStockKeepUnitManager
					.convertUnit(requestCoreUnitBack, mainUnitUUID, productionOrder.getClient());
		}
		// [Step2.2] Create proposal list, if no proposal list input
		if (ServiceCollectionsHelper.checkNullList(productionProposalItemList)) {
			// In case have to generate production proposal list
			productionProposalItemList = genProposalToProdOrderItemWrapper(requestCoreUnitBack, productionOrderItem, logonInfo);
			// To avoid deep sub proposal from production resource generation,
			// only return the top-level proposal
			productionProposalItemList = filterOutTopProposalList(productionProposalItemList, productionOrderItem);

		}
		if (ServiceCollectionsHelper.checkNullList(productionProposalItemList)) {
			return null;
		}
		// [Step2.3] Convert unit back, if necessary
		for (ServiceEntityNode seNode : productionProposalItemList) {
			if (!ProdOrderItemReqProposal.NODENAME.equals(seNode.getNodeName()) && !RepairProdItemReqProposal.NODENAME.equals(seNode.getNodeName())) {
				continue;
			}
			ProdOrderItemReqProposal prodReqProposal = (ProdOrderItemReqProposal) seNode;
			if (!refUnitUUID.equals(prodReqProposal.getRefUnitUUID())) {
				StorageCoreUnit recoverCoreUnit = new StorageCoreUnit(requestCoreUnit.getRefMaterialSKUUUID(),
						prodReqProposal.getRefUnitUUID(), prodReqProposal.getAmount());
				recoverCoreUnit = materialStockKeepUnitManager.convertUnit(recoverCoreUnit, refUnitUUID, productionOrder.getClient());
				if (recoverCoreUnit.getAmount() % 1 == 0) {
					// In case amount is integer value, then convert unit back
					prodReqProposal.setAmount(recoverCoreUnit.getAmount());
					prodReqProposal.setRefUnitUUID(recoverCoreUnit.getRefUnitUUID());
				}
			}
		}
		/*
		 * [Step3] Call core method to generate picking material list
		 */
		if (extPickingRefOrderItem != null) {
			// batch gen ref material item list based on existed
			// PickingRefOrderItem
			List<ServiceEntityNode> newRefMaterialItemList = convPickingRefMaItemListByProposalList(extPickingRefOrderItem,
					itemProcessType, productionProposalItemList, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
			if (ServiceCollectionsHelper.checkNullList(newRefMaterialItemList)) {
				return null;
			}
			// [Step3.2] update into DB
			prodPickingOrderManager.updateSENodeList(newRefMaterialItemList, logonInfo.getRefUserUUID(),
					logonInfo.getResOrgUUID());
			/*
			 * [Step4] Update proposal list into DB, if needed
			 */
			if (updateProposal) {
				productionOrderManager.updateSENodeList(productionProposalItemList, logonInfo.getRefUserUUID(),
						logonInfo.getResOrgUUID());
			}
			/*
			 * [Step5] Approve newly created picking
			 */
			if (updateProposal) {
				//TODO to read system configure in the future
				approvePickingOrderWrapper(extPickingRefOrderItem.getRootNodeUUID(), extPickingRefOrderItem.getClient(),
						logonInfo);
			}
			return newRefMaterialItemList;
		} else {
			// In this new request case, should create picking order with
			// status: 'INITIAL'.
			ProdPickingOrderServiceModel prodPickingOrderServiceModel = prodPickingOrderManager
					.newPickingOrderByProdWrapper(productionOrder, 0, ProdPickingOrder.STATUS_INITIAL, itemProcessType);
			ProdPickingRefOrderItemServiceModel newPickingRefOrderItemServiceModel = ProdPickingOrderManager
					.filterRefOrderItemByOrder(prodPickingOrderServiceModel.getProdPickingRefOrderItemList(),
							productionOrder.getUuid());
			List<ServiceEntityNode> newRefMaterialItemList = convPickingRefMaItemListByProposalList(
					newPickingRefOrderItemServiceModel.getProdPickingRefOrderItem(), itemProcessType, productionProposalItemList, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
			if (ServiceCollectionsHelper.checkNullList(newRefMaterialItemList)) {
				return null;
			}
			List<ProdPickingRefMaterialItemServiceModel> prodPickingRefMaterialItemList = new ArrayList<>();
			ServiceCollectionsHelper.forEach(newRefMaterialItemList, serviceEntityNode -> {
				ProdPickingRefMaterialItemServiceModel prodPickingRefMaterialItemServiceModel =
						new ProdPickingRefMaterialItemServiceModel();
				prodPickingRefMaterialItemServiceModel.setProdPickingRefMaterialItem(
						(ProdPickingRefMaterialItem) serviceEntityNode);
				prodPickingRefMaterialItemList.add(prodPickingRefMaterialItemServiceModel);
				return serviceEntityNode;
			});
			newPickingRefOrderItemServiceModel.setProdPickingRefMaterialItemList(prodPickingRefMaterialItemList);
			// [Step 3.4] update into DB
			prodPickingOrderManager
					.updateServiceModuleWithDelete(ProdPickingOrderServiceModel.class, prodPickingOrderServiceModel, logonInfo.getRefUserUUID(),
							logonInfo.getResOrgUUID());
			/*
			 * [Step4] Update proposal list into DB, if needed
			 */
			if (updateProposal) {
				productionOrderManager.updateSENodeList(productionProposalItemList, logonInfo.getRefUserUUID(),
						logonInfo.getResOrgUUID());
			}
			/*
			 * [Step5] Approve newly created picking
			 */
			if (updateProposal) {
				//TODO to read system configure in the future
				prodPickingOrderManager.approvePickingOrder(prodPickingOrderServiceModel, logonInfo);
			}
			return newRefMaterialItemList;
		}
	}

	/**
	 * [Internal method] Wrapper help to approve picking order
	 *
	 * @param baseUUID:        picking order UUID
	 */
	private void approvePickingOrderWrapper(String baseUUID, String client, LogonInfo logonInfo)
			throws ServiceEntityConfigureException, ServiceModuleProxyException {
		ProdPickingOrder prodPickingOrder = (ProdPickingOrder) prodPickingOrderManager
				.getEntityNodeByKey(baseUUID, IServiceEntityNodeFieldConstant.UUID, client, null);
		if (prodPickingOrder != null && prodPickingOrder.getStatus() == ProdPickingOrder.STATUS_INITIAL) {
			ProdPickingOrderServiceModel prodPickingOrderServiceModel = (ProdPickingOrderServiceModel) prodPickingOrderManager
					.loadServiceModule(ProdPickingOrderServiceModel.class, prodPickingOrder);
			prodPickingOrderManager.approvePickingOrder(prodPickingOrderServiceModel, logonInfo);
		}
	}

	public ProdPickingRefMaterialItem updateRequestToReturnOrderWrapper(StorageCoreUnit requestCoreUnit,
			ProductionOrderItem productionOrderItem, boolean updateProposal, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, MaterialException, BillOfMaterialException,
            SearchConfigureException, NodeNotFoundException, ServiceEntityInstallationException, ServiceModuleProxyException, ServiceComExecuteException, DocActionException {
		/*
		 * [Step1] Trying to get proper ref order item
		 */
		if (requestCoreUnit.getAmount() == 0) {
			return null;
		}
		/*
		 * [Step2] Generate the prod item proposal
		 */
		ProdOrderItemReqProposal prodOrderItemReqProposal = genSimpleProposalForReturn(requestCoreUnit, productionOrderItem);

		ProdPickingRefOrderItem extPickingRefOrderItem = genProperRefOrderItemForReturn(productionOrderItem);
		ProductionOrder productionOrder = (ProductionOrder) productionOrderManager
				.getEntityNodeByKey(productionOrderItem.getRootNodeUUID(), IServiceEntityNodeFieldConstant.UUID,
						BillOfMaterialOrder.NODENAME, productionOrderItem.getClient(), null);
		if (extPickingRefOrderItem != null) {
			// In case parent ref order item already exist
			ProdPickingRefMaterialItem prodReturnRefMaterialItem = prodPickingOrderManager
					.newReturnMaterialItem(prodOrderItemReqProposal, extPickingRefOrderItem, ProdPickingOrder.STATUS_INITIAL, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
			prodReturnRefMaterialItem.setRefProdOrderItemUUID(productionOrderItem.getUuid());
			if (updateProposal) {
				// update into persistence
				prodPickingOrderManager.updateSENode(prodReturnRefMaterialItem, logonInfo.getRefUserUUID(),
						logonInfo.getResOrgUUID());
				productionOrderManager.updateSENode(prodOrderItemReqProposal, logonInfo.getRefUserUUID(),
						logonInfo.getResOrgUUID());
			}
			if (updateProposal) {
				//TODO to read system configure in the future
				approvePickingOrderWrapper(extPickingRefOrderItem.getRootNodeUUID(), extPickingRefOrderItem.getClient(),
						logonInfo);
			}
			return prodReturnRefMaterialItem;
		} else {
			// In this new request case, should create picking order with
			// status: 'INITIAL'.
			ProdPickingOrderServiceModel prodPickingOrderServiceModel = prodPickingOrderManager
					.newPickingOrderByProdWrapper(productionOrder, 0, ProdPickingOrder.STATUS_INITIAL,
							ProdPickingOrder.PROCESSTYPE_RETURN);
			ProdPickingRefOrderItemServiceModel newPickingRefOrderItemServiceModel = ProdPickingOrderManager
					.filterRefOrderItemByOrder(prodPickingOrderServiceModel.getProdPickingRefOrderItemList(),
							productionOrder.getUuid());
			ProdPickingRefMaterialItem prodReturnRefMaterialItem = prodPickingOrderManager
					.newReturnMaterialItem(prodOrderItemReqProposal, newPickingRefOrderItemServiceModel.prodPickingRefOrderItem,
							ProdPickingOrder.STATUS_INITIAL, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
			prodReturnRefMaterialItem.setRefProdOrderItemUUID(productionOrderItem.getUuid());
			List<ProdPickingRefMaterialItemServiceModel> prodPickingRefMaterialItemList = new ArrayList<>();
			ProdPickingRefMaterialItemServiceModel prodPickingRefMaterialItemServiceModel =
					new ProdPickingRefMaterialItemServiceModel();
			prodPickingRefMaterialItemServiceModel.setProdPickingRefMaterialItem(prodReturnRefMaterialItem);
			prodPickingRefMaterialItemList.add(prodPickingRefMaterialItemServiceModel);
			newPickingRefOrderItemServiceModel.setProdPickingRefMaterialItemList(prodPickingRefMaterialItemList);
			if (updateProposal) {
				// update into persistence
				prodPickingOrderManager
						.updateServiceModuleWithDelete(ProdPickingOrderServiceModel.class, prodPickingOrderServiceModel, logonInfo.getRefUserUUID(),
								logonInfo.getResOrgUUID());
				productionOrderManager.updateSENode(prodOrderItemReqProposal, logonInfo.getRefUserUUID(),
						logonInfo.getResOrgUUID());
			}
			if (updateProposal) {
				//TODO to read system configure in the future
				prodPickingOrderManager.approvePickingOrder(prodPickingOrderServiceModel, logonInfo);
			}
			return prodReturnRefMaterialItem;
		}
	}

	public void convProdOrderItemToInitialUIModel(ProductionOrderItem productionOrderItem,
			ProdPickingRefMaterialInitialUIModel prodPickingRefMaterialInitialUIModel) throws ServiceEntityConfigureException {
		if (productionOrderItem != null) {
			prodPickingRefMaterialInitialUIModel.setRefMaterialSKUUUID(productionOrderItem.getRefMaterialSKUUUID());
			prodPickingRefMaterialInitialUIModel.setRefProdOrderUUID(productionOrderItem.getRootNodeUUID());
			ProductionOrder productionOrder = (ProductionOrder) productionOrderManager
					.getEntityNodeByKey(productionOrderItem.getRootNodeUUID(), IServiceEntityNodeFieldConstant.UUID,
							ProductionOrder.NODENAME, productionOrderItem.getClient(), null);
			if (productionOrder != null) {
				prodPickingRefMaterialInitialUIModel.setProdOrderId(productionOrder.getId());
			}
			MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
					.getEntityNodeByKey(productionOrderItem.getRefMaterialSKUUUID(), IServiceEntityNodeFieldConstant.UUID,
							MaterialStockKeepUnit.NODENAME, productionOrderItem.getClient(), null);
			if (materialStockKeepUnit != null) {
				prodPickingRefMaterialInitialUIModel.setRefMaterialSKUId(materialStockKeepUnit.getId());
				prodPickingRefMaterialInitialUIModel.setRefMaterialSKUName(materialStockKeepUnit.getName());
				prodPickingRefMaterialInitialUIModel.setPackageStandard(materialStockKeepUnit.getPackageStandard());
			}
		}
	}

	/**
	 * Get or Generate proper picking ref order item instance when need to
	 * return material requirement
	 *
	 * @param productionOrderItem
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceEntityInstallationException
	 * @throws NodeNotFoundException
	 * @throws SearchConfigureException
	 */
	public ProdPickingRefOrderItem genProperRefOrderItemForReturn(ProductionOrderItem productionOrderItem)
			throws ServiceEntityConfigureException, SearchConfigureException, NodeNotFoundException,
			ServiceEntityInstallationException {
		/*
		 * [Step1] Trying to get NOT-complete ref order item instance
		 */
		List<ServiceEntityNode> prodPickingRefOrderItemList = prodPickingOrderManager
				.getEntityNodeListByKey(productionOrderItem.getRootNodeUUID(), "refProdOrderUUID", ProdPickingRefOrderItem.NODENAME,
						productionOrderItem.getClient(), null);
		List<String> parentNodeUUIDList = new ArrayList<>();
		if (!ServiceCollectionsHelper.checkNullList(prodPickingRefOrderItemList)) {
			for (ServiceEntityNode seNode : prodPickingRefOrderItemList) {
				parentNodeUUIDList.add(seNode.getUuid());
			}
		}
		ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
		key1.setKeyName(IServiceEntityNodeFieldConstant.PARENTNODEUUID);
		key1.setMultipleValueList(parentNodeUUIDList);
		ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure(ProdPickingOrder.PROCESSTYPE_RETURN,
				ProdPickingRefMaterialItem.FIELD_ITEMPROCESSTYPE);
		List<ServiceEntityNode> rawProdPickingRefMaterialItemList =
				prodPickingOrderManager.getEntityNodeListByKeyList(ServiceCollectionsHelper.asList(key1, key2),
				ProdPickingRefMaterialItem.NODENAME, productionOrderItem.getClient(), null);
		List<ServiceEntityNode> resultList = new ArrayList<>();
		if (!ServiceCollectionsHelper.checkNullList(prodPickingRefOrderItemList)) {
			for (ServiceEntityNode seNode : prodPickingRefOrderItemList) {
				List<ServiceEntityNode> subPickingRefMaterialItemList = ServiceCollectionsHelper
						.filterSENodeByParentUUID(seNode.getUuid(), rawProdPickingRefMaterialItemList);
				if (ServiceCollectionsHelper.checkNullList(subPickingRefMaterialItemList)) {
					continue;
				}
				for (ServiceEntityNode seRefMaterial : subPickingRefMaterialItemList) {
					ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) seRefMaterial;
					if (prodPickingRefMaterialItem.getItemStatus() == ProdPickingRefMaterialItem.ITEMSTATUS_INITIAL) {
						// In case there is one 'initial' item, and this ref
						// order item
						resultList.add(seNode);
					}
				}
			}
		}

		if (!ServiceCollectionsHelper.checkNullList(resultList)) {
			/*
			 * [Step2.1] In case there is some ref order item list meet
			 * requirement.
			 */
			return (ProdPickingRefOrderItem) resultList.get(0);
		}
		return null;
	}

	/**
	 * Get or Generate proper picking ref order item instance when adding new
	 * material requirement
	 *
	 * @param productionOrderItem
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceEntityInstallationException
	 * @throws NodeNotFoundException
	 * @throws SearchConfigureException
	 */
	public ProdPickingRefOrderItem genProperRefOrderItemForAddMaterial(ProductionOrderItem productionOrderItem)
			throws ServiceEntityConfigureException, SearchConfigureException, NodeNotFoundException,
			ServiceEntityInstallationException {
		/*
		 * [Step1] Trying to get NOT-complete ref order item instance
		 */
		List<ServiceEntityNode> prodPickingRefOrderItemList = prodPickingOrderManager
				.getEntityNodeListByKey(productionOrderItem.getRootNodeUUID(), "refProdOrderUUID", ProdPickingRefOrderItem.NODENAME,
						productionOrderItem.getClient(), null);
		List<String> parentNodeUUIDList = new ArrayList<>();
		if (!ServiceCollectionsHelper.checkNullList(prodPickingRefOrderItemList)) {
			for (ServiceEntityNode seNode : prodPickingRefOrderItemList) {
				parentNodeUUIDList.add(seNode.getUuid());
			}
		}
		ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
		key1.setKeyName(IServiceEntityNodeFieldConstant.PARENTNODEUUID);
		key1.setMultipleValueList(parentNodeUUIDList);
		ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure();
		key2.setKeyName(ProdPickingRefMaterialItem.FIELD_ITEMPROCESSTYPE);
		key2.setMultipleValueList(ServiceCollectionsHelper.asList(ProdPickingOrder.PROCESSTYPE_INPLAN, ProdPickingOrder.PROCESSTYPE_REPLENISH));
		List<ServiceEntityNode> rawProdPickingRefMaterialItemList =
				prodPickingOrderManager.getEntityNodeListByKeyList(ServiceCollectionsHelper.asList(key1, key2),
						ProdPickingRefMaterialItem.NODENAME, productionOrderItem.getClient(), null);

		List<ServiceEntityNode> resultList = new ArrayList<>();
		if (!ServiceCollectionsHelper.checkNullList(prodPickingRefOrderItemList)) {
			for (ServiceEntityNode seNode : prodPickingRefOrderItemList) {
				List<ServiceEntityNode> subPickingRefMaterialItemList = ServiceCollectionsHelper
						.filterSENodeByParentUUID(seNode.getUuid(), rawProdPickingRefMaterialItemList);
				if (ServiceCollectionsHelper.checkNullList(subPickingRefMaterialItemList)) {
					continue;
				}
				for (ServiceEntityNode seRefMaterial : subPickingRefMaterialItemList) {
					ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) seRefMaterial;
					if (prodPickingRefMaterialItem.getItemStatus() == ProdPickingRefMaterialItem.ITEMSTATUS_INITIAL) {
						// In case there is one 'initial' item, and this ref
						// order item
						resultList.add(seNode);
					}
				}
			}
		}

		if (!ServiceCollectionsHelper.checkNullList(resultList)) {
			/*
			 * [Step2.1] In case there is some ref order item list meet
			 * requirement.
			 */
			return (ProdPickingRefOrderItem) resultList.get(0);
		}
		return null;
	}

	/**
	 * Core Logic to batch convert list of prodOrderItemProposal to list of
	 * ProdPickingRefMaterialItem
	 *
	 * @param prodPickingRefOrderItem
	 * @param itemProcessType
	 * @param productionProposalItemList
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public List<ServiceEntityNode> convPickingRefMaItemListByProposalList(ProdPickingRefOrderItem prodPickingRefOrderItem,
			int itemProcessType, List<ServiceEntityNode> productionProposalItemList, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, MaterialException, ServiceComExecuteException, DocActionException {
		if (!ServiceCollectionsHelper.checkNullList(productionProposalItemList)) {
			List<ServiceEntityNode> resultList = new ArrayList<>();
			for (ServiceEntityNode seNode : productionProposalItemList) {
				// filter out other type of seNode
				if (!ProdOrderItemReqProposal.NODENAME.equals(seNode.getNodeName()) && !RepairProdItemReqProposal.NODENAME.equals(seNode.getNodeName())) {
					continue;
				}
				ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) seNode;
				// item status will be updated in approve order method
				ProdPickingRefMaterialItem prodPickingRefMaterialItem = prodPickingOrderManager
						.newRefMaterialItem(prodOrderItemReqProposal, prodPickingRefOrderItem,
								ProdPickingRefMaterialItem.ITEMSTATUS_INITIAL, serialLogonInfo);
				// TODO special handling for direct out-bound
				if (prodOrderItemReqProposal.getDocumentType() == IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY) {

				}
				// Important!: set next mat doc uuid to null, set next doc type as document type, in order to make release picking order work
				prodPickingRefMaterialItem.setNextDocMatItemUUID(null);
				prodPickingRefMaterialItem.setNextDocType(prodOrderItemReqProposal.getDocumentType());
				prodPickingRefMaterialItem.setItemProcessType(itemProcessType);
				resultList.add(prodPickingRefMaterialItem);
			}
			return resultList;
		}
		return null;
	}

	/**
	 * Core Logic to generate production proposal list for specified production
	 * order item
	 *
	 * @param productionOrderItem
	 * @param requestUnit
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 * @throws BillOfMaterialException
	 * @throws ServiceEntityInstallationException
	 * @throws NodeNotFoundException
	 * @throws SearchConfigureException
	 */
	public List<ServiceEntityNode> genProposalToProdOrderItemWrapper(StorageCoreUnit requestUnit,
																	 ProductionOrderItem productionOrderItem, LogonInfo logonInfo) throws MaterialException, ServiceEntityConfigureException,
            BillOfMaterialException, SearchConfigureException, ServiceComExecuteException, AuthorizationException, ServiceEntityInstallationException, LogonInfoException {
		/*
		 * [Step1] Data prepare: Get Production Order, BOM Order, Productive BOM
		 * Order
		 */
		ProductionOrder productionOrder = (ProductionOrder) productionOrderManager
				.getEntityNodeByKey(productionOrderItem.getRootNodeUUID(), IServiceEntityNodeFieldConstant.UUID,
						BillOfMaterialOrder.NODENAME, productionOrderItem.getClient(), null);
		BillOfMaterialOrder billOfMaterialOrder = billOfMaterialOrderManager
				.getRefBillOfMaterialOrderWrapper(productionOrder.getRefBillOfMaterialUUID(), productionOrder.getClient());
		List<ServiceEntityNode> productiveBOMList = billOfMaterialOrderManager.genProductiveBOMModel(billOfMaterialOrder);
		if (productiveBOMList == null || productiveBOMList.size() == 0) {
			return null;
		}
		/*
		 * [Step2] Data prepare: rawStoreItemList and all the material SKU list
		 */
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
		List<ServiceEntityNode> rawMaterialSKUList = materialStockKeepUnitManager.getSearchProxy().searchDocList(searchContext).getResultList();
		List<ServiceEntityNode> rawStoreItemList = new ArrayList<>();
		List<ServiceEntityNode> prodSupplyWarehouseList = productionOrderManager
				.getEntityNodeListByKey(productionOrder.getUuid(), IServiceEntityNodeFieldConstant.ROOTNODEUUID,
						ProdOrderSupplyWarehouse.NODENAME, productionOrder.getClient(), null);

		/*
		 * [Step3] Calculate the ratio of Productive BOM
		 */
		ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) billOfMaterialOrderManager
				.filterBOMItemByUUID(productionOrderItem.getRefBOMItemUUID(), productiveBOMList);
		if (productiveBOMItem == null) {
			return null;
		}
		StorageCoreUnit tmpBomStorageUnit = new StorageCoreUnit();
		tmpBomStorageUnit.setRefUnitUUID(productiveBOMItem.getRefUnitUUID());
		tmpBomStorageUnit.setAmount(productiveBOMItem.getAmount());
		tmpBomStorageUnit.setRefMaterialSKUUUID(productiveBOMItem.getRefMaterialSKUUUID());

		return productionOrderManager
				.genProposalToProdOrderItemCore(productionOrder, prodSupplyWarehouseList, productiveBOMList, rawStoreItemList,
						rawMaterialSKUList, requestUnit, productionOrderItem);
	}

	/**
	 * Core Logic to generate production proposal list for specified production
	 * order item
	 *
	 * @param productionOrderItem
	 * @param requestUnit
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 * @throws BillOfMaterialException
	 * @throws ServiceEntityInstallationException
	 * @throws NodeNotFoundException
	 * @throws SearchConfigureException
	 */
	public ProdOrderItemReqProposal genSimpleProposalForReturn(StorageCoreUnit requestUnit,
			ProductionOrderItem productionOrderItem)
			throws MaterialException, ServiceEntityConfigureException, BillOfMaterialException, SearchConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		/*
		 * [Step1] Data prepare: Get Production Order, BOM Order, Productive BOM
		 * Order
		 */
		ProductionOrder productionOrder = (ProductionOrder) productionOrderManager
				.getEntityNodeByKey(productionOrderItem.getRootNodeUUID(), IServiceEntityNodeFieldConstant.UUID,
						BillOfMaterialOrder.NODENAME, productionOrderItem.getClient(), null);
		BillOfMaterialOrder billOfMaterialOrder = billOfMaterialOrderManager
				.getRefBillOfMaterialOrderWrapper(productionOrder.getRefBillOfMaterialUUID(), productionOrder.getClient());
		List<ServiceEntityNode> productiveBOMList = billOfMaterialOrderManager.genProductiveBOMModel(billOfMaterialOrder);
		if (productiveBOMList == null || productiveBOMList.size() == 0) {
			throw new BillOfMaterialException(BillOfMaterialException.PARA_NO_BOMOrder, productionOrderItem);
		}
		ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) billOfMaterialOrderManager
				.filterBOMItemByUUID(productionOrderItem.getRefBOMItemUUID(), productiveBOMList);
		if (productiveBOMItem == null) {
			return null;
		}
		StorageCoreUnit tmpBomStorageUnit = new StorageCoreUnit();
		tmpBomStorageUnit.setRefUnitUUID(productiveBOMItem.getRefUnitUUID());
		tmpBomStorageUnit.setAmount(productiveBOMItem.getAmount());
		tmpBomStorageUnit.setRefMaterialSKUUUID(productiveBOMItem.getRefMaterialSKUUUID());

		ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) productionOrderManager
				.newEntityNode(productionOrderItem, ProdOrderItemReqProposal.NODENAME);
		prodOrderItemReqProposal.setAmount(requestUnit.getAmount());
		prodOrderItemReqProposal.setRefUnitUUID(requestUnit.getRefUnitUUID());
		prodOrderItemReqProposal.setRefMaterialSKUUUID(requestUnit.getRefMaterialSKUUUID());
		// prodOrderItemReqProposal.setRefWarehouseUUID(warehouseUUID);
		prodOrderItemReqProposal.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY);
		prodOrderItemReqProposal.setProductionBatchNumber(productionOrder.getProductionBatchNumber());
		// Set this proposal as available status
		prodOrderItemReqProposal.setItemStatus(ProductionPlanItem.STATUS_AVAILABLE);
		prodOrderItemReqProposal.setRefBOMItemUUID(productiveBOMItem.getUuid());
		return prodOrderItemReqProposal;
	}

}
