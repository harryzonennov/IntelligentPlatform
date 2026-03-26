package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryManager;
import com.company.IntelligentPlatform.production.service.ProdPickingExtendAmountModel;
import com.company.IntelligentPlatform.production.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.ServiceReflectiveHelper;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepairProdItemReqProposalManager {

	@Autowired
	protected RepairProdOrderManager repairProdOrderManager;

	@Autowired
	protected RepairProdOrderItemManager repairProdOrderItemManager;

	@Autowired
	protected ProdOrderItemReqProposalManager prodOrderItemReqProposalManager;

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

	protected Logger logger = LoggerFactory.getLogger(RepairProdItemReqProposalManager.class);

	public List<PageHeaderModel> getPageHeaderModelList(RepairProdItemReqProposal repairProdItemReqProposal, String client)
			throws ServiceEntityConfigureException {
		RepairProdOrderItem repairProdOrderItem = (RepairProdOrderItem) repairProdOrderManager
				.getEntityNodeByKey(repairProdItemReqProposal.getParentNodeUUID(), IServiceEntityNodeFieldConstant.UUID,
						RepairProdOrderItem.NODENAME, client, null);
		int index = 0;
		List<PageHeaderModel> resultList = new ArrayList<PageHeaderModel>();
		if (repairProdOrderItem != null) {
			List<PageHeaderModel> pageHeaderModelList = repairProdOrderItemManager
					.getPageHeaderModelList(repairProdOrderItem, client);
			if (!ServiceCollectionsHelper.checkNullList(pageHeaderModelList)) {
				resultList.addAll(pageHeaderModelList);
				index = pageHeaderModelList.size();
			}
			PageHeaderModel itemHeaderModel = getPageHeaderModel(repairProdItemReqProposal, index);
			if (itemHeaderModel != null) {
				resultList.add(itemHeaderModel);
			}
		}
		return resultList;
	}

	protected PageHeaderModel getPageHeaderModel(RepairProdItemReqProposal repairProdItemReqProposal, int index)
			throws ServiceEntityConfigureException {
		if (repairProdItemReqProposal == null) {
			return null;
		}
		PageHeaderModel pageHeaderModel = new PageHeaderModel();
		pageHeaderModel.setPageTitle("repairProdItemReqProposalTitle");
		pageHeaderModel.setNodeInstId(RepairProdItemReqProposal.NODENAME);
		pageHeaderModel.setUuid(repairProdItemReqProposal.getUuid());
		MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
				.getEntityNodeByKey(repairProdItemReqProposal.getRefMaterialSKUUUID(), IServiceEntityNodeFieldConstant.UUID,
						MaterialStockKeepUnit.NODENAME, repairProdItemReqProposal.getClient(), null);
		if (materialStockKeepUnit != null) {
			pageHeaderModel.setHeaderName(materialStockKeepUnit.getName());
		}
		pageHeaderModel.setIndex(index);
		return pageHeaderModel;
	}

	public static ProdOrderItemReqProposalServiceModel copyToProductionServiceModel(RepairProdItemReqProposalServiceModel repairProdItemReqProposalServiceModel){
		ProdOrderItemReqProposalServiceModel prodOrderItemReqProposalServiceModel =
				new ProdOrderItemReqProposalServiceModel();
		prodOrderItemReqProposalServiceModel.setProdOrderItemReqProposal(repairProdItemReqProposalServiceModel.getRepairProdItemReqProposal());
		if(!ServiceCollectionsHelper.checkNullList(repairProdItemReqProposalServiceModel.getRepairProdOrderItemList())){
			List<ProductionOrderItemServiceModel> productionOrderItemServiceModelList = new ArrayList<>();
			for(RepairProdOrderItemServiceModel repairProdOrderItemServiceModel:
					repairProdItemReqProposalServiceModel.getRepairProdOrderItemList()){
				ProductionOrderItemServiceModel productionOrderItemServiceModel =
						RepairProdOrderItemManager.copyToProductionServiceModel(repairProdOrderItemServiceModel);
				productionOrderItemServiceModelList.add(productionOrderItemServiceModel);
			}
			prodOrderItemReqProposalServiceModel.setProductionOrderItemList(productionOrderItemServiceModelList);
		}
		return prodOrderItemReqProposalServiceModel;
	}

	public static RepairProdItemReqProposalServiceModel copyToRepairServiceModel(ProdOrderItemReqProposalServiceModel prodOrderItemReqProposalServiceModel) throws InstantiationException, IllegalAccessException {
		RepairProdItemReqProposalServiceModel repairProdItemReqProposalServiceModel =
				new RepairProdItemReqProposalServiceModel();
		repairProdItemReqProposalServiceModel.setRepairProdItemReqProposal(ServiceReflectiveHelper.castCopyModel(prodOrderItemReqProposalServiceModel.getProdOrderItemReqProposal(), RepairProdItemReqProposal.class));
		if(!ServiceCollectionsHelper.checkNullList(prodOrderItemReqProposalServiceModel.getProductionOrderItemList())){
			List<RepairProdOrderItemServiceModel> repairProdOrderItemServiceModelList = new ArrayList<>();
			for(ProductionOrderItemServiceModel productionOrderItemServiceModel:
					prodOrderItemReqProposalServiceModel.getProductionOrderItemList()){
				RepairProdOrderItemServiceModel repairProdOrderItemServiceModel =
						RepairProdOrderItemManager.copyToRepairServiceModel(productionOrderItemServiceModel);
				repairProdOrderItemServiceModelList.add(repairProdOrderItemServiceModel);
			}
			repairProdItemReqProposalServiceModel.setRepairProdOrderItemList(repairProdOrderItemServiceModelList);
		}
		return repairProdItemReqProposalServiceModel;
	}

	public List<ServiceEntityNode> getInprocessDocMatItemList(RepairProdItemReqProposal repairProdItemReqProposal)
            throws MaterialException, ServiceEntityConfigureException, DocActionException {
		return prodOrderItemReqProposalManager.getInprocessDocMatItemList(repairProdItemReqProposal);
	}

	public List<ServiceEntityNode> getAllEndDocMatItemList(RepairProdItemReqProposal repairProdItemReqProposal,
			boolean checkMaterialStatus) throws DocActionException {
		return prodOrderItemReqProposalManager.getAllEndDocMatItemList(repairProdItemReqProposal, checkMaterialStatus);
	}

	public List<ServiceDocumentExtendUIModel> getAllEndDocMatItemUIModelList(RepairProdItemReqProposal repairProdItemReqProposal,
			LogonInfo logonInfo, boolean checkMaterialStatus)
			throws MaterialException, ServiceEntityConfigureException, DocActionException {
		return prodOrderItemReqProposalManager.getAllEndDocMatItemUIModelList(repairProdItemReqProposal, logonInfo,
				checkMaterialStatus);
	}

	/**
	 * Logic to Set Finish to next picking ref Material item instance
	 *
	 * @param repairProdItemReqProposal
	 */
	public void setFinishPickingRefMaterialItem(RepairProdItemReqProposal repairProdItemReqProposal, String logonUserUUID,
			String organizationUUID) throws ProductionOrderException, ServiceEntityConfigureException {
		prodOrderItemReqProposalManager.setFinishPickingRefMaterialItem(repairProdItemReqProposal, logonUserUUID, organizationUUID);
	}

	/**
	 * Logic to get all the possible next picking material item from this proposal
	 *
	 * @param repairProdItemReqProposal
	 * @return
	 */
	public List<ServiceEntityNode> getNextPickingMatItemList(RepairProdItemReqProposal repairProdItemReqProposal) {
		return prodOrderItemReqProposalManager.getNextPickingMatItemList(repairProdItemReqProposal);
	}

	/**
	 * Core Logic to calculate each kind of amount and refresh to Proposal UI
	 * Model
	 *
	 * @param repairProdItemReqProposal
	 * @param rawPickingExtendAmountModelList
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public void refreshItemStatus(RepairProdItemReqProposal repairProdItemReqProposal,
			List<ProdPickingExtendAmountModel> rawPickingExtendAmountModelList)
			throws ServiceEntityConfigureException, MaterialException, ServiceComExecuteException {
		prodOrderItemReqProposalManager.refreshItemStatus(repairProdItemReqProposal, rawPickingExtendAmountModelList);
	}

	/**
	 * Logic to calculate not in-plan amount, this method should be invoked
	 * after the availableAmount and in-process amount is calculated
	 *
	 * @param repairProdItemReqProposal
	 * @return
	 */
	public static void updateItemStatus(RepairProdItemReqProposal repairProdItemReqProposal) {
		ProdOrderItemReqProposalManager.updateItemStatus(repairProdItemReqProposal);
	}

}
