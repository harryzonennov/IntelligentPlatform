package com.company.IntelligentPlatform.production.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.production.dto.ProductiveBOMItemUIModel;
import com.company.IntelligentPlatform.production.model.ProcessRouteProcessItem;
import com.company.IntelligentPlatform.production.model.ProdProcess;
import com.company.IntelligentPlatform.production.model.ProductiveBOMItem;
import com.company.IntelligentPlatform.production.model.ProductiveBOMOrder;
import com.company.IntelligentPlatform.common.dto.MaterialUIModel;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class ProductiveBOMItemManager {

	public static final String METHOD_ConvProductiveBOMItemToUI = "convProductiveBOMItemToUI";

	public static final String METHOD_ConvMaterialStockKeepUnitToUI = "convMaterialStockKeepUnitToUI";

	public static final String METHOD_ConvProcessRouteProcessItemToUI = "convProcessRouteProcessItemToUI";
	
	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;
	
	protected Logger logger = LoggerFactory.getLogger(ProductiveBOMItemManager.class);
	
	protected Map<Integer, String> materialCategoryMap;
	
	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;
	
	public Map<Integer, String> initMaterialCategoryMap() throws ServiceEntityInstallationException{
		if (this.materialCategoryMap == null) {
			this.materialCategoryMap = serviceDropdownListHelper
					.getUIDropDownMap(MaterialUIModel.class, "materialCategory");
		}
		return this.materialCategoryMap;
	}
	
	public void convProductiveBOMItemToUI(
			ProductiveBOMItem productiveBOMItem,
			ProductiveBOMItemUIModel productiveBOMItemUIModel)
			throws ServiceEntityInstallationException {
		if (productiveBOMItem != null) {
			if (!ServiceEntityStringHelper.checkNullString(productiveBOMItem
					.getUuid())) {
				productiveBOMItemUIModel.setUuid(productiveBOMItem.getUuid());
			}
			if (!ServiceEntityStringHelper.checkNullString(productiveBOMItem
					.getParentNodeUUID())) {
				productiveBOMItemUIModel.setParentNodeUUID(productiveBOMItem
						.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(productiveBOMItem
					.getRootNodeUUID())) {
				productiveBOMItemUIModel.setRootNodeUUID(productiveBOMItem
						.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(productiveBOMItem
					.getClient())) {
				productiveBOMItemUIModel.setClient(productiveBOMItem
						.getClient());
			}
			productiveBOMItemUIModel.setId(productiveBOMItem.getId());
			productiveBOMItemUIModel.setNote(productiveBOMItem.getNote());
			productiveBOMItemUIModel.setRefMaterialSKUUUID(productiveBOMItem
					.getRefMaterialSKUUUID());
			productiveBOMItemUIModel.setAmount(productiveBOMItem.getAmount());
			productiveBOMItemUIModel.setAmountWithLossRate(productiveBOMItem.getAmountWithLossRate());
			productiveBOMItemUIModel.setRefUnitUUID(productiveBOMItem
					.getRefUnitUUID());
			productiveBOMItemUIModel.setRefUnitName(productiveBOMItem
					.getRefUnitUUID());
			productiveBOMItemUIModel.setLayer(productiveBOMItem.getLayer());
			productiveBOMItemUIModel.setRefParentItemUUID(productiveBOMItem
					.getRefParentItemUUID());
			productiveBOMItemUIModel.setItemCategory(productiveBOMItem
					.getItemCategory());
			productiveBOMItemUIModel.setLeadTimeOffset(productiveBOMItem
					.getLeadTimeOffset());
			productiveBOMItemUIModel.setTheoLossRate(productiveBOMItem
					.getTheoLossRate());
			productiveBOMItemUIModel.setRefSubBOMUUID(productiveBOMItem
					.getRefSubBOMUUID());
			this.initMaterialCategoryMap();
			productiveBOMItemUIModel.setItemCategoryValue(this.materialCategoryMap
					.get(productiveBOMItem.getItemCategory()));
			try {
				String amountLabel = materialStockKeepUnitManager
						.getAmountLabel(productiveBOMItem.getRefMaterialSKUUUID(), productiveBOMItem.getRefUnitUUID(),
								productiveBOMItem
								.getAmount(), productiveBOMItem
								.getClient());
				String amountLossRateLabel = materialStockKeepUnitManager
						.getAmountLabel(productiveBOMItem.getRefMaterialSKUUUID(), productiveBOMItem.getRefUnitUUID(),
								productiveBOMItem
								.getAmountWithLossRate(), productiveBOMItem
								.getClient());
				productiveBOMItemUIModel.setAmountLabel(amountLabel);
				productiveBOMItemUIModel.setAmountLossRateLabel(amountLossRateLabel);
			} catch (MaterialException e) {
				// just skip
			} catch (ServiceEntityConfigureException e) {
				// just skip
			}
		}
	}

	public void convParentItemToUI(ProductiveBOMItem parentItem,
			ProductiveBOMItemUIModel productiveBOMItemUIModel,
			Map<String, String> materialUnitMap) {
		if (parentItem != null) {
			productiveBOMItemUIModel.setParentItemId(parentItem.getId());
			String amountLabel = parentItem.getAmount() + "";
			if (parentItem.getRefUnitUUID() != null
					&& !parentItem.getRefUnitUUID().equals(
							ServiceEntityStringHelper.EMPTYSTRING)) {
				productiveBOMItemUIModel.setAmountLabel(parentItem.getAmount()
						+ materialUnitMap.get(parentItem.getRefUnitUUID()));
			} else {
				productiveBOMItemUIModel.setAmountLabel(amountLabel);
			}
		}
	}

	public void convSubBOMToItemUI(ProductiveBOMOrder subBOM,
			ProductiveBOMItemUIModel productiveBOMItemUIModel) {
		if (subBOM != null) {
			productiveBOMItemUIModel.setRefSubBOMId(subBOM.getId());
		}		
	}
	
	public void convProcessRouteProcessItemToUI(
			ProcessRouteProcessItem processRouteProcessItem,
			ProductiveBOMItemUIModel productiveBOMItemUIModel) {
		if (processRouteProcessItem != null) {
			productiveBOMItemUIModel
					.setRefRouteProcessIndex(processRouteProcessItem
							.getProcessIndex());
			productiveBOMItemUIModel
					.setRefRouteProcessItemUUID(processRouteProcessItem
							.getUuid());
		}
	}
	
	public void convProdProcessToUI(ProdProcess prodProcess,
			ProductiveBOMItemUIModel productiveBOMItemUIModel) {
		if (prodProcess != null) {
			productiveBOMItemUIModel.setRefProdProcessId(prodProcess.getId());
			productiveBOMItemUIModel.setRefProdProcessName(prodProcess
					.getName());
		}
	}
	
	public void convMaterialStockKeepUnitToUI(
			MaterialStockKeepUnit materialStockKeepUnit,
			ProductiveBOMItemUIModel productiveBOMItemUIModel){
		convMaterialStockKeepUnitToUI(materialStockKeepUnit, productiveBOMItemUIModel, null);
	}
	
	public void convMaterialStockKeepUnitToUI(
			MaterialStockKeepUnit materialStockKeepUnit,
			ProductiveBOMItemUIModel productiveBOMItemUIModel, LogonInfo logonInfo) {
		if (materialStockKeepUnit != null) {
			productiveBOMItemUIModel.setRefMaterialSKUId(materialStockKeepUnit
					.getId());
			productiveBOMItemUIModel
					.setRefMaterialSKUName(materialStockKeepUnit.getName());
			productiveBOMItemUIModel.setPackageStandard(materialStockKeepUnit.getPackageStandard());
			productiveBOMItemUIModel.setSupplyType(materialStockKeepUnit.getSupplyType());
			if(logonInfo != null){
				try {
					Map<Integer, String> supplyTypeMap = materialStockKeepUnitManager
							.initSupplyTypeMap(logonInfo.getLanguageCode());
					productiveBOMItemUIModel.setSupplyTypeValue(supplyTypeMap
							.get(materialStockKeepUnit.getSupplyType()));
				} catch (ServiceEntityInstallationException e) {
					// log error and continue
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "supplyType"));
				}
			}			
			productiveBOMItemUIModel.setRefMaterialSKUId(materialStockKeepUnit
					.getId());
			productiveBOMItemUIModel.setFixLeadTime(materialStockKeepUnit
					.getFixLeadTime());
		}
	}

	public void convParentItemMaterialToUI(
			MaterialStockKeepUnit parentItemMaterial,
			ProductiveBOMItemUIModel productiveBOMItemUIModel) {
		if (parentItemMaterial != null) {
			productiveBOMItemUIModel
					.setParentItemMaterialName(parentItemMaterial.getName());
			productiveBOMItemUIModel
					.setParentItemMaterialSKUId(parentItemMaterial.getId());
			productiveBOMItemUIModel
					.setParentItemMaterialSKUName(parentItemMaterial.getName());

		}
	}


}
