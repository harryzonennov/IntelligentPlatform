package com.company.IntelligentPlatform.production.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.production.model.ProdOrderItemReqProposal;
import com.company.IntelligentPlatform.production.model.ProdPlanItemReqProposal;
import com.company.IntelligentPlatform.production.model.ProductionPlanItem;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.IServiceEntityCommonFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class ProdPlanItemReqProposalManager {
	
	@Autowired
	protected ProductionPlanManager productionPlanManager;
	
	@Autowired
	protected ProductionPlanItemManager productionPlanItemManager;
	
	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;
	
	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected ProdOrderItemReqProposalManager prodOrderItemReqProposalManager;
	
	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;
	
	protected Logger logger = LoggerFactory.getLogger(ProdPlanItemReqProposalManager.class);


	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), ProductionPlanItem.NODENAME,
						request.getUuid(), ProdPlanItemReqProposal.NODENAME, productionPlanManager);
		docPageHeaderInputPara.setGenBaseModelList(
				(DocPageHeaderModelProxy.GenBaseModelList<ProductionPlanItem>) productionPlanItem -> {
					// How to get the base page header model list
					return productionPlanItemManager.getPageHeaderModelList(request, client);
				});
		docPageHeaderInputPara.setGenHomePageModel(
				(DocPageHeaderModelProxy.GenHomePageModel<ProdPlanItemReqProposal>) (prodPlanItemReqProposal,
																					 pageHeaderModel) -> {
					// How to render current page header
					MaterialStockKeepUnit materialStockKeepUnit = null;
					try {
						materialStockKeepUnit = materialStockKeepUnitManager
								.getMaterialSKUWrapper(
										prodPlanItemReqProposal.getRefMaterialSKUUUID(),
										prodPlanItemReqProposal.getClient(), null);
					} catch (ServiceComExecuteException e) {
						logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
					}
					if (materialStockKeepUnit != null) {
						pageHeaderModel.setHeaderName(materialStockKeepUnit.getName());
					}
					return pageHeaderModel;
				});
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}

	/**
	 * Logic to get one relative downstream production order item instance
	 * 
	 * @param prodPlanItemReqProposal
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public ProdOrderItemReqProposal getRelativeOrderItemProposal(
			ProdPlanItemReqProposal prodPlanItemReqProposal)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> orderItemProposalList = getRelativeOrderItemProposalList(prodPlanItemReqProposal);
		if (ServiceCollectionsHelper.checkNullList(orderItemProposalList)) {
			return null;
		} else {
			return (ProdOrderItemReqProposal) orderItemProposalList.get(0);
		}
	}

	/**
	 * Logic to get all possible downstream production order item list
	 * 
	 * @param prodPlanItemReqProposal
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getRelativeOrderItemProposalList(
			ProdPlanItemReqProposal prodPlanItemReqProposal)
			throws ServiceEntityConfigureException {
		if (prodPlanItemReqProposal == null) {
			logger.error("No Plan Item proposal");
			return null;
		}
		List<ServiceEntityNode> orderItemProposalList = productionOrderManager
				.getEntityNodeListByKey(prodPlanItemReqProposal.getUuid(),
						IServiceEntityCommonFieldConstant.PREVDOCMATITEMUUID,
						ProdOrderItemReqProposal.NODENAME,
						prodPlanItemReqProposal.getClient(), null);
		if (orderItemProposalList == null) {
			logger.error("No Order Item proposal by:"
					+ prodPlanItemReqProposal.getUuid());
			return null;
		}
		return orderItemProposalList;
	}
	
	public List<ServiceEntityNode> getInprocessDocMatItemList(
			ProdPlanItemReqProposal prodPlanItemReqProposal)
            throws MaterialException, ServiceEntityConfigureException, DocActionException {
		ProdOrderItemReqProposal prodOrderItemReqProposal = getRelativeOrderItemProposal(prodPlanItemReqProposal);
		return prodOrderItemReqProposalManager.getInprocessDocMatItemList(prodOrderItemReqProposal);
	}
	
	public List<ServiceEntityNode> getAllEndDocMatItemList(
			ProdPlanItemReqProposal prodPlanItemReqProposal, boolean checkMaterialStatus)
            throws ServiceEntityConfigureException, DocActionException {
		ProdOrderItemReqProposal prodOrderItemReqProposal = getRelativeOrderItemProposal(prodPlanItemReqProposal);
		return prodOrderItemReqProposalManager.getAllEndDocMatItemList(prodOrderItemReqProposal, checkMaterialStatus);
	}

	public List<ServiceDocumentExtendUIModel> getAllEndDocMatItemUIModelList(
			ProdPlanItemReqProposal prodPlanItemReqProposal,
			LogonInfo logonInfo, boolean checkMaterialStatus)
			throws MaterialException, ServiceEntityConfigureException, DocActionException {
		ProdOrderItemReqProposal prodOrderItemReqProposal = getRelativeOrderItemProposal(prodPlanItemReqProposal);
		return prodOrderItemReqProposalManager.getAllEndDocMatItemUIModelList(prodOrderItemReqProposal, logonInfo, checkMaterialStatus);
	}
	
	/**
	 * Core Logic to calculate each kind of amount and refresh to Proposal UI
	 * Model
	 * 
	 * @param prodPlanItemReqProposal
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public void refreshItemStatus(
			ProdPlanItemReqProposal prodPlanItemReqProposal)
            throws ServiceEntityConfigureException, MaterialException, DocActionException {
		ProdOrderItemReqProposal prodOrderItemReqProposal = getRelativeOrderItemProposal(prodPlanItemReqProposal);
		if(prodOrderItemReqProposal == null){
			return;
		}
		prodOrderItemReqProposalManager.refreshItemStatus(prodOrderItemReqProposal);
		updateOrderItemToPlanItem(prodOrderItemReqProposal, prodPlanItemReqProposal);
	}
	
	public void updateOrderItemToPlanItem(
			ProdOrderItemReqProposal prodOrderItemReqProposal,
			ProdPlanItemReqProposal prodPlanItemReqProposal) {
		prodPlanItemReqProposal.setPickedAmount(prodOrderItemReqProposal
				.getPickedAmount());
		prodPlanItemReqProposal.setPickStatus(prodOrderItemReqProposal
				.getPickStatus());
		prodPlanItemReqProposal.setToPickAmount(prodOrderItemReqProposal
				.getToPickAmount());
		prodPlanItemReqProposal.setInProcessAmount(prodOrderItemReqProposal
				.getInProcessAmount());
		prodPlanItemReqProposal.setSuppliedAmount(prodOrderItemReqProposal
				.getSuppliedAmount());
		prodPlanItemReqProposal.setInStockAmount(prodOrderItemReqProposal
				.getInStockAmount());
		prodPlanItemReqProposal.setLackInPlanAmount(prodOrderItemReqProposal
				.getLackInPlanAmount());
		
		prodPlanItemReqProposal.setRefUUID(prodOrderItemReqProposal.getRefUUID());
		prodPlanItemReqProposal.setDocumentType(prodOrderItemReqProposal.getDocumentType());
	}
		

}
