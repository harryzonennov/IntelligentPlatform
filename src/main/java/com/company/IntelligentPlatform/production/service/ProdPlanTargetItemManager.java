package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.production.dto.ProdPlanTarSubItemUIModel;
import com.company.IntelligentPlatform.production.dto.ProdPlanTargetMatItemUIModel;
import com.company.IntelligentPlatform.production.model.BillOfMaterialItem;
import com.company.IntelligentPlatform.production.model.ProdPlanTarSubItem;
import com.company.IntelligentPlatform.production.model.ProdPlanTargetMatItem;
import com.company.IntelligentPlatform.production.model.ProductionPlan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.model.IDocItemNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class ProdPlanTargetItemManager {

	@Autowired
	protected ProductionPlanManager productionPlanManager;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

	protected Logger logger = LoggerFactory.getLogger(ProdPlanTargetItemManager.class);

	public static final String METHOD_ConvProdPlanTargetMatItemToUI = "convProdPlanTargetMatItemToUI";

	public static final String METHOD_ConvUIToProdPlanTargetMatItem = "convUIToProdPlanTargetMatItem";

	public static final String METHOD_ConvProductionPlanToTargetItemUI = "convProductionPlanToTargetItemUI";

	public static final String METHOD_ConvProdPlanTarSubItemToUI = "convProdPlanTarSubItemToUI";

	public static final String METHOD_ConvUIToProdPlanTarSubItem = "convUIToProdPlanTarSubItem";

	public static final String METHOD_ConvBillOfOrderItemToUI = "convBillOfOrderItemToUI";

	public List<PageHeaderModel> getPageHeaderModelList(
			ProdPlanTargetMatItem prodPlanTargetMatItem, String client)
			throws ServiceEntityConfigureException {
		ProductionPlan productionPlan = (ProductionPlan) productionPlanManager
				.getEntityNodeByKey(prodPlanTargetMatItem.getParentNodeUUID(),
						IServiceEntityNodeFieldConstant.UUID,
						ProductionPlan.NODENAME, client, null);
		int index = 0;
		List<PageHeaderModel> resultList = new ArrayList<PageHeaderModel>();
		if (prodPlanTargetMatItem != null) {
			List<PageHeaderModel> pageHeaderModelList = productionPlanManager
					.getPageHeaderModelList(productionPlan, client);
			if (!ServiceCollectionsHelper.checkNullList(pageHeaderModelList)) {
				resultList.addAll(pageHeaderModelList);
				index = pageHeaderModelList.size();
			}
			PageHeaderModel itemHeaderModel = null;
			try {
				itemHeaderModel = getPageHeaderModel(
						prodPlanTargetMatItem, index);
			} catch (ServiceComExecuteException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
			}
			if (itemHeaderModel != null) {
				resultList.add(itemHeaderModel);
			}
		}
		return resultList;
	}

	protected PageHeaderModel getPageHeaderModel(
			ProdPlanTargetMatItem prodPlanTargetMatItem, int index) throws ServiceEntityConfigureException, ServiceComExecuteException {
		if (prodPlanTargetMatItem == null) {
			return null;
		}
		PageHeaderModel pageHeaderModel = new PageHeaderModel();
		pageHeaderModel.setPageTitle("prodPlanTargetMatItemPageTitle");
		pageHeaderModel.setNodeInstId(ProdPlanTargetMatItem.NODENAME);
		pageHeaderModel.setUuid(prodPlanTargetMatItem.getUuid());
		MaterialStockKeepUnit materialStockKeepUnit = materialStockKeepUnitManager
				.getMaterialSKUWrapper(
						prodPlanTargetMatItem.getRefMaterialSKUUUID(),
						prodPlanTargetMatItem.getClient(), null);
		if (materialStockKeepUnit != null) {
			pageHeaderModel.setHeaderName(MaterialStockKeepUnitManager
					.getMaterialIdentifier(materialStockKeepUnit, false));
		}
		pageHeaderModel.setIndex(index);
		return pageHeaderModel;
	}

	public Map<Integer, String> initStatusMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.statusMapLan, ProdPlanTargetMatItemUIModel.class, IDocItemNodeFieldConstant.itemStatus);
	}

	public void convProductionPlanToTargetItemUI(
			ProductionPlan productionPlan,
			ProdPlanTargetMatItemUIModel prodPlanTargetMatItemUIModel) {
		convProductionPlanToTargetItemUI(productionPlan,
				prodPlanTargetMatItemUIModel, null);
	}

	public void convProductionPlanToTargetItemUI(
			ProductionPlan productionPlan,
			ProdPlanTargetMatItemUIModel prodPlanTargetMatItemUIModel,
			LogonInfo logonInfo) {
		if (productionPlan != null && prodPlanTargetMatItemUIModel != null) {
			prodPlanTargetMatItemUIModel.setOrderId(productionPlan.getId());
			prodPlanTargetMatItemUIModel.setOrderStatus(productionPlan
					.getStatus());
		}
	}

	/**
	 * @param prodPlanTargetMatItem
	 * @param prodPlanTargetMatItemUIModel
	 * @param logonInfo
	 */
	public void convProdPlanTargetMatItemToUI(
			ProdPlanTargetMatItem prodPlanTargetMatItem,
			ProdPlanTargetMatItemUIModel prodPlanTargetMatItemUIModel,
			LogonInfo logonInfo) {
		docFlowProxy.convDocMatItemToUI(prodPlanTargetMatItem,
				prodPlanTargetMatItemUIModel, logonInfo);
		if (logonInfo != null) {
			try {
				Map<Integer, String> statusMap = initStatusMap(logonInfo
						.getLanguageCode());
				prodPlanTargetMatItemUIModel.setItemStatusValue(statusMap
						.get(prodPlanTargetMatItem.getItemStatus()));
			} catch (ServiceEntityInstallationException e) {
				// log error and continue
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, IDocItemNodeFieldConstant.itemStatus), e);
			}
		}
	}

	public void convUIToProdPlanTargetMatItem(
			ProdPlanTargetMatItemUIModel prodPlanTargetMatItemUIModel,
			ProdPlanTargetMatItem prodPlanTargetMatItem) {
		docFlowProxy.convUIToDocMatItem(prodPlanTargetMatItemUIModel,
				prodPlanTargetMatItem);
		if (prodPlanTargetMatItemUIModel.getItemStatus() > 0) {
			prodPlanTargetMatItem.setItemStatus(prodPlanTargetMatItemUIModel
					.getItemStatus());
		}
	}

	public void convProdPlanTarSubItemToUI(
			ProdPlanTarSubItem prodPlanTarSubItem,
			ProdPlanTarSubItemUIModel prodPlanTarSubItemUIModel,
			LogonInfo logonInfo) {
		docFlowProxy.convDocMatItemToUI(prodPlanTarSubItem,
				prodPlanTarSubItemUIModel, logonInfo);
		prodPlanTarSubItemUIModel.setLayer(prodPlanTarSubItem.getLayer());
		prodPlanTarSubItemUIModel.setRefParentItemUUID(prodPlanTarSubItem
				.getRefParentItemUUID());
		prodPlanTarSubItemUIModel.setRefBOMItemUUID(prodPlanTarSubItem
				.getRefBOMItemUUID());
	}

	public void convBillOfOrderItemToUI(BillOfMaterialItem billOfMaterialItem,
			ProdPlanTarSubItemUIModel prodPlanTarSubItemUIModel,
			LogonInfo logonInfo) {
		if (billOfMaterialItem != null && prodPlanTarSubItemUIModel != null) {
			prodPlanTarSubItemUIModel.setRefBOMItemId(billOfMaterialItem
					.getId());
		}
	}

	public void convUIToProdPlanTarSubItem(
			ProdPlanTarSubItemUIModel prodPlanTarSubItemUIModel,
			ProdPlanTarSubItem prodPlanTarSubItem) {
		docFlowProxy.convUIToDocMatItem(prodPlanTarSubItemUIModel,
				prodPlanTarSubItem);
		prodPlanTarSubItemUIModel.setLayer(prodPlanTarSubItem.getLayer());
		prodPlanTarSubItemUIModel.setRefParentItemUUID(prodPlanTarSubItem
				.getRefParentItemUUID());
		prodPlanTarSubItemUIModel.setRefBOMItemUUID(prodPlanTarSubItem
				.getRefBOMItemUUID());
	}

}
