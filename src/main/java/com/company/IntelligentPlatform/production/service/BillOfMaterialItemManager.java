package com.company.IntelligentPlatform.production.service;

import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.production.dto.BillOfMaterialItemSearchModel;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialItemUIModel;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialOrderUIModel;
import com.company.IntelligentPlatform.production.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.MaterialUIModel;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.DocumentContentSpecifier;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.ServiceCommonDataFormatter;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class BillOfMaterialItemManager {

	public static final String METHOD_ConvProdWorkCenterToItemUI = "convProdWorkCenterToItemUI";

	public static final String METHOD_ConvBillOfMaterialItemToUI = "convBillOfMaterialItemToUI";

	public static final String METHOD_ConvUIToBillOfMaterialItem = "convUIToBillOfMaterialItem";

	public static final String METHOD_ConvSubBomOrderToItemUI = "convSubBOMToItemUI";

	public static final String METHOD_ConvItemMaterialToUI = "convItemMaterialToUI";

	public static final String MEMTHOD_ConvParentBOMOrderToUI = "convParentBOMOrderToUI";

	public static final String METHOD_ConvProdProcessToUI = "convProdProcessToUI";

	public static final String METHOD_ConvProcessRouteProcessItemToUI = "convProcessRouteProcessItemToUI";

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected ProcessRouteOrderManager processRouteOrderManager;

	@Autowired
	protected ProdProcessManager prodProcessManager;
	
	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;
	
	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	@Autowired
	protected BillOfMaterialOrderSpecifier billOfMaterialOrderSpecifier;
	
	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;
	
	protected Logger logger = LoggerFactory.getLogger(BillOfMaterialItemManager.class);


	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), BillOfMaterialOrder.NODENAME,
						request.getUuid(), BillOfMaterialItem.NODENAME, billOfMaterialOrderManager);
		docPageHeaderInputPara.setGenBaseModelList(new DocPageHeaderModelProxy.GenBaseModelList<BillOfMaterialOrder>() {
			@Override
			public List<PageHeaderModel> execute(BillOfMaterialOrder billOfMaterialOrder) throws ServiceEntityConfigureException {
				// How to get the base page header model list
                return docPageHeaderModelProxy.getDocPageHeaderModelList(billOfMaterialOrder, null);
			}
		});
		docPageHeaderInputPara.setGenHomePageModel((DocPageHeaderModelProxy.GenHomePageModel<BillOfMaterialItem>) (billOfMaterialItem, pageHeaderModel) ->
				docPageHeaderModelProxy.getDefDocMaterialItemPageHeaderModel(billOfMaterialItem, pageHeaderModel));
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}



	public BillOfMaterialItem newBOMTemplateItemFromParent(String baseUUID, String client) throws ServiceEntityConfigureException, DocActionException {
		return billOfMaterialOrderSpecifier.newEntityNodeFromBaseUUID(new DocumentContentSpecifier.
				InitSubServiceEntityRequest<>(BillOfMaterialTemplate.NODENAME, BillOfMaterialItem.NODENAME, baseUUID, (parentNode, parentItem, newItem) -> {
			BillOfMaterialTemplate billOfMaterialTemplate = (BillOfMaterialTemplate) parentNode;
			int layer = 1;
			String refParentItemUUID = ServiceEntityStringHelper.EMPTYSTRING;
			if (parentItem != null) {
				layer = parentItem.getLayer() + 1;
				refParentItemUUID = parentItem.getUuid();
			}
			String defId = billOfMaterialOrderManager
					.newBomItemId(billOfMaterialTemplate);
			newItem.setId(defId);
			newItem.setRefParentItemUUID(refParentItemUUID);
			newItem.setLayer(layer);
			return newItem;
		}), client);
	}

	public void convBOMUIModelToItemSearchModel(
			BillOfMaterialItemSearchModel itemSearchModel,
			BillOfMaterialOrderUIModel billOfMaterialOrderUIModel) {
		if (billOfMaterialOrderUIModel != null && itemSearchModel != null) {
			itemSearchModel.setRefMaterialSKUID(billOfMaterialOrderUIModel
					.getSearchItemSKUID());
			itemSearchModel.setRefMaterialSKUName(billOfMaterialOrderUIModel
					.getSearchItemSKUName());
			itemSearchModel.setId(billOfMaterialOrderUIModel.getSearchItemId());
		}
	}
	
	public void convBillOfMaterialItemToUI(
			BillOfMaterialItem billOfMaterialItem,
			BillOfMaterialItemUIModel billOfMaterialItemUIModel)
			throws ServiceEntityInstallationException {
		convBillOfMaterialItemToUI(billOfMaterialItem, billOfMaterialItemUIModel, null);
	}

	public void convBillOfMaterialItemToUI(
			BillOfMaterialItem billOfMaterialItem,
			BillOfMaterialItemUIModel billOfMaterialItemUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		if (billOfMaterialItem != null) {
			docFlowProxy.convDocMatItemToUI(billOfMaterialItem, billOfMaterialItemUIModel, logonInfo);
			billOfMaterialItemUIModel.setId(billOfMaterialItem.getId());
			billOfMaterialItemUIModel.setNote(billOfMaterialItem.getNote());
			try {
				String refUnitName = materialStockKeepUnitManager
						.getRefUnitName(
								billOfMaterialItem.getRefMaterialSKUUUID(),
								billOfMaterialItem.getRefUnitUUID(),
								billOfMaterialItem.getClient());
				billOfMaterialItemUIModel.setRefUnitName(refUnitName);
			} catch (MaterialException | ServiceEntityConfigureException e) {
				// just skip
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "refUnitName"));
			}
			if(logonInfo != null){
				Map<Integer, String> statusMap = billOfMaterialOrderManager.initStatusMap(logonInfo.getLanguageCode());
				billOfMaterialItemUIModel.setItemStatusValue(statusMap
						.get(billOfMaterialItem.getItemStatus()));
			}
			billOfMaterialItemUIModel.setLayer(billOfMaterialItem.getLayer());
			billOfMaterialItemUIModel.setRefParentItemUUID(billOfMaterialItem
					.getRefParentItemUUID());
			billOfMaterialItemUIModel.setItemCategory(billOfMaterialItem
					.getItemCategory());
			billOfMaterialItemUIModel.setLeadTimeOffset(billOfMaterialItem
					.getLeadTimeOffset());
			billOfMaterialItemUIModel.setTheoLossRate(billOfMaterialItem
					.getTheoLossRate());
			String theoLossRateValue = ServiceCommonDataFormatter
					.formatPercentageValue(billOfMaterialItem
							.getTheoLossRate()/100);
			billOfMaterialItemUIModel.setTheoLossRateValue(theoLossRateValue);
			billOfMaterialItemUIModel.setRefSubBOMUUID(billOfMaterialItem
					.getRefSubBOMUUID());
			billOfMaterialItemUIModel.setRefWocUUID(billOfMaterialItem.getRefWocUUID());
			Map<Integer, String> categoryMap = serviceDropdownListHelper
					.getUIDropDownMap(MaterialUIModel.class, "materialCategory");
			billOfMaterialItemUIModel.setItemCategoryValue(categoryMap
					.get(billOfMaterialItem.getItemCategory()));
		}
	}

	public void convUIToBillOfMaterialItem(BillOfMaterialItemUIModel billOfMaterialItemUIModel, BillOfMaterialItem rawEntity
			) {
		docFlowProxy.convUIToDocMatItem(billOfMaterialItemUIModel, rawEntity);
		rawEntity.setId(billOfMaterialItemUIModel.getId());
		rawEntity.setNote(billOfMaterialItemUIModel.getNote());
		if (!ServiceEntityStringHelper
				.checkNullString(billOfMaterialItemUIModel.getRefMaterialSKUUUID())) {
			rawEntity.setRefMaterialSKUUUID(billOfMaterialItemUIModel.getRefMaterialSKUUUID());
		}
		rawEntity.setLeadTimeOffset(billOfMaterialItemUIModel
				.getLeadTimeOffset());
		// Check loss rate is valid
		rawEntity.setTheoLossRate(calculateValidLossRate(billOfMaterialItemUIModel.getTheoLossRate()));
		rawEntity
				.setRefSubBOMUUID(billOfMaterialItemUIModel.getRefSubBOMUUID());
		rawEntity.setRefWocUUID(billOfMaterialItemUIModel.getRefWocUUID());
		rawEntity.setRefRouteProcessItemUUID(billOfMaterialItemUIModel.getRefRouteProcessItemUUID());
	}
	
	/**
	 * Calculate valid loss rate from raw loss rate
	 * @param lossRate
	 * @return
	 */
	public static double calculateValidLossRate(double lossRate){
		if(lossRate < 0){
			return 0;
		}
		if(lossRate > 100){
			return 100;
		}
		return lossRate;
	}


	public void convParentItemToUI(BillOfMaterialItem parentItem,
			BillOfMaterialItemUIModel billOfMaterialItemUIModel,
			Map<String, String> materialUnitMap) {
		if (parentItem != null) {
			billOfMaterialItemUIModel.setParentItemId(parentItem.getId());
			String amountLabel = parentItem.getAmount() + "";
			if (parentItem.getRefUnitUUID() != null
					&& !parentItem.getRefUnitUUID().equals(
							ServiceEntityStringHelper.EMPTYSTRING)) {
				billOfMaterialItemUIModel.setAmountLabel(parentItem.getAmount()
						+ materialUnitMap.get(parentItem.getRefUnitUUID()));
			} else {
				billOfMaterialItemUIModel.setAmountLabel(amountLabel);
			}
		}
	}

	public void convSubBOMToItemUI(BillOfMaterialOrder subBOM,
			BillOfMaterialItemUIModel billOfMaterialItemUIModel) {
		if (subBOM != null) {
			billOfMaterialItemUIModel.setRefSubBOMId(subBOM.getId());
		}		
	}

	public void convProcessRouteProcessItemToUI(
			ProcessRouteProcessItem processRouteProcessItem,
			BillOfMaterialItemUIModel billOfMaterialItemUIModel) {
		if (processRouteProcessItem != null) {
			billOfMaterialItemUIModel
					.setRefRouteProcessIndex(processRouteProcessItem
							.getProcessIndex());
			billOfMaterialItemUIModel
					.setRefRouteProcessItemUUID(processRouteProcessItem
							.getUuid());
		}
	}

	public void convProdProcessToUI(ProdProcess prodProcess,
			BillOfMaterialItemUIModel billOfMaterialItemUIModel) {
		if (prodProcess != null) {
			billOfMaterialItemUIModel.setRefProdProcessId(prodProcess.getId());
			billOfMaterialItemUIModel.setRefProdProcessName(prodProcess
					.getName());
		}
	}

	/**
	 * Compound convert bill of material item to UI Model
	 * 
	 * @param billOfMaterialItem
	 * @param billOfMaterialItemUIModel
	 * @throws ServiceEntityConfigureException
	 */
	public void convComProcessItemToUI(BillOfMaterialItem billOfMaterialItem,
			BillOfMaterialItemUIModel billOfMaterialItemUIModel)
			throws ServiceEntityConfigureException {
		if (billOfMaterialItem != null && billOfMaterialItemUIModel != null) {
			if (billOfMaterialItem.getRefRouteProcessItemUUID() == null
					|| billOfMaterialItem.getRefRouteProcessItemUUID().equals(
							ServiceEntityStringHelper.EMPTYSTRING)) {
				return;
			}
			ProcessRouteProcessItem processRouteProcessItem = (ProcessRouteProcessItem) processRouteOrderManager
					.getEntityNodeByKey(
							billOfMaterialItem.getRefRouteProcessItemUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							ProcessRouteProcessItem.NODENAME,
							billOfMaterialItem.getClient(), null);
			ProdProcess prodProcess = (ProdProcess) prodProcessManager
					.getEntityNodeByKey(processRouteProcessItem.getRefUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							ProdProcess.NODENAME,
							billOfMaterialItem.getClient(), null);
			convProcessRouteProcessItemToUI(processRouteProcessItem,
					billOfMaterialItemUIModel);
			convProdProcessToUI(prodProcess, billOfMaterialItemUIModel);
		}
	}

	public void convParentBOMOrderToUI(BillOfMaterialOrder billOfMaterialOrder,
									   BillOfMaterialItemUIModel billOfMaterialItemUIModel) {

	}


	public void convParentBOMOrderToUI(BillOfMaterialOrder billOfMaterialOrder,
			BillOfMaterialItemUIModel billOfMaterialItemUIModel, LogonInfo logonInfo) {
		if (billOfMaterialOrder != null) {
			billOfMaterialItemUIModel.setParentItemId(billOfMaterialOrder
					.getId());
			// TODO remove this field status
			billOfMaterialItemUIModel.setItemStatus(billOfMaterialOrder.getStatus());
			billOfMaterialItemUIModel.setParentDocStatus(billOfMaterialOrder.getStatus());
			if(logonInfo != null){
				try {
					Map<Integer, String> statusMap = billOfMaterialOrderManager.initStatusMap(logonInfo.getLanguageCode());
					// TODO remove this field status
					billOfMaterialItemUIModel.setItemStatusValue(statusMap.get(billOfMaterialOrder.getStatus()));
					billOfMaterialItemUIModel.setParentDocStatusValue(statusMap.get(billOfMaterialOrder.getStatus()));
				} catch (ServiceEntityInstallationException e) {
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "status"));
				}
			}
			try {
				String amountLabel = materialStockKeepUnitManager
						.getAmountLabel(
								billOfMaterialOrder.getRefMaterialSKUUUID(),
								billOfMaterialOrder.getRefUnitUUID(),
								billOfMaterialOrder.getAmount(),
								billOfMaterialOrder.getClient());
				billOfMaterialItemUIModel.setParentItemAmountLabel(amountLabel);
			} catch (MaterialException | ServiceEntityConfigureException e) {
				// just skip
			}	
		}
	}

	public void convItemMaterialToUI(
			MaterialStockKeepUnit materialStockKeepUnit,
			BillOfMaterialItemUIModel billOfMaterialItemUIModel) {
		convItemMaterialToUI(materialStockKeepUnit, billOfMaterialItemUIModel, null);
	}

	public void convItemMaterialToUI(
			MaterialStockKeepUnit materialStockKeepUnit,
			BillOfMaterialItemUIModel billOfMaterialItemUIModel, LogonInfo logonInfo) {
		if (materialStockKeepUnit != null) {
			docFlowProxy.convMaterialSKUToItemUI(materialStockKeepUnit, billOfMaterialItemUIModel);
			billOfMaterialItemUIModel.setFixLeadTime(materialStockKeepUnit
					.getFixLeadTime());
			billOfMaterialItemUIModel.setSupplyType(materialStockKeepUnit.getSupplyType());
			if(logonInfo != null){
				try {
					Map<Integer, String> supplyTypeMap = materialStockKeepUnitManager.initSupplyTypeMap(logonInfo.getLanguageCode());
					billOfMaterialItemUIModel.setSupplyTypeValue(supplyTypeMap.get(materialStockKeepUnit.getSupplyType()));
				} catch (ServiceEntityInstallationException e) {
					// Log error and continue
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "supplyType"));
				}
			}
		}
	}


	public void convProdWorkCenterToItemUI(ProdWorkCenter prodWorkCenter,
										   BillOfMaterialItemUIModel billOfMaterialItemUIModel) {
		if (prodWorkCenter != null) {
			billOfMaterialItemUIModel.setRefWocName(prodWorkCenter.getName());
			billOfMaterialItemUIModel.setRefWocId(prodWorkCenter.getId());
		}
	}

	public void convParentItemMaterialToUI(
			MaterialStockKeepUnit parentItemMaterial,
			BillOfMaterialItemUIModel billOfMaterialItemUIModel) {
		if (parentItemMaterial != null) {
			billOfMaterialItemUIModel
					.setParentItemMaterialName(parentItemMaterial.getName());
			billOfMaterialItemUIModel
					.setParentItemMaterialSKUId(parentItemMaterial.getId());
			billOfMaterialItemUIModel
					.setParentItemMaterialSKUName(parentItemMaterial.getName());

		}
	}

	public void convUIToParentItemMaterial(MaterialStockKeepUnit rawEntity,
			BillOfMaterialItemUIModel billOfMaterialItemUIModel) {
		rawEntity
				.setName(billOfMaterialItemUIModel.getParentItemMaterialName());
		rawEntity.setId(billOfMaterialItemUIModel.getParentItemMaterialSKUId());
	}
	

}
