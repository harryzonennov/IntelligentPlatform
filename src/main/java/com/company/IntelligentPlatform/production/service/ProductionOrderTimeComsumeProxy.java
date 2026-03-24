package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.model.BillOfMaterialOrder;
import com.company.IntelligentPlatform.production.model.ProcessRouteProcessItem;
import com.company.IntelligentPlatform.production.model.ProductionOrder;
import com.company.IntelligentPlatform.production.model.ProductiveBOMItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityDoubleHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Proxy service class to calculate the production time consume
 * 
 * @author Zhang,hang
 *
 */
@Service
public class ProductionOrderTimeComsumeProxy {

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	@Autowired
	protected ProcessRouteOrderManager processRouteOrderManager;

	public void calculateProdBOMItemLeadTime(
			List<ServiceEntityNode> productiveBOMList,
			List<ServiceEntityNode> rawMaterialList) throws ServiceEntityConfigureException, MaterialException, ServiceComExecuteException {
		List<ServiceEntityNode> footerBOMList = getFooterProdBOMItemListNoneNull(productiveBOMList);
		List<ServiceEntityNode> upperLayerBOMList = new ArrayList<ServiceEntityNode>();
		for (ServiceEntityNode seNode : footerBOMList) {
			// Process & Calculate each footer productive BOM Item lead time
			ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
			MaterialStockKeepUnit materialStockKeepUnit = materialStockKeepUnitManager
					.getMaterialSKUWrapper(
							productiveBOMItem.getRefMaterialSKUUUID(),
							productiveBOMItem.getClient(), rawMaterialList);
			if (materialStockKeepUnit == null) {
				continue;
			}
			double ratioToStandardUnit = getRatioToStandardUnit(
					productiveBOMItem, materialStockKeepUnit);
			// Calculate the lead time for this Material, for footer layer,
			// using material info by default
			double leadTime = calculateSelfLeadTime(
					productiveBOMItem.getLeadTimeCalMode(),
					productiveBOMItem.getRefRouteOrderUUID(),
					ratioToStandardUnit, materialStockKeepUnit,
					productiveBOMItem.getAmountWithLossRate(),
					productiveBOMItem.getRefUnitUUID());
			leadTime = leadTime - productiveBOMItem.getLeadTimeOffset();
			productiveBOMItem.setSelfLeadTime(leadTime);
			productiveBOMItem.setComLeadTime(leadTime);
			ProductiveBOMItem parentBOMItem = (ProductiveBOMItem) billOfMaterialOrderManager
					.filterBOMItemByUUID(
							productiveBOMItem.getRefParentItemUUID(),
							productiveBOMList);
			if (parentBOMItem != null) {
				ServiceCollectionsHelper.mergeToList(upperLayerBOMList,
						parentBOMItem);
			}
		}
		// Recursive process the lead time, until the first BOM layer
		processProductiveBOMLeadTime(upperLayerBOMList, productiveBOMList,
				rawMaterialList);
	}

	/**
	 * Logic to calculate material SKU self lead time, including the
	 * imeplementation logic to calculate lead time by process route as well as
	 * the implementation to calculate by material own information
	 * 
	 * @param leadTimeCalMode
	 * @param refRouteOrderUUID
	 * @param ratioToStandardUnit
	 * @param materialStockKeepUnit
	 * @param amount
	 * @param refUnitUUID
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public double calculateSelfLeadTime(int leadTimeCalMode,
			String refRouteOrderUUID, double ratioToStandardUnit,
			MaterialStockKeepUnit materialStockKeepUnit, double amount,
			String refUnitUUID) throws ServiceEntityConfigureException,
			MaterialException {
		double selfLeadTime = 0;
		// In case calculate the self lead time by process union
		if (leadTimeCalMode == BillOfMaterialOrder.LEAD_CAL_MODE_PROCESS
				&& !ServiceEntityStringHelper
						.checkNullString(refRouteOrderUUID)) {			
			List<ServiceEntityNode> processRouteProcessItemList = processRouteOrderManager
					.getEntityNodeListByKey(refRouteOrderUUID,
							IServiceEntityNodeFieldConstant.PARENTNODEUUID,
							ProcessRouteProcessItem.NODENAME,
							materialStockKeepUnit.getClient(), null);
			if (!ServiceCollectionsHelper
					.checkNullList(processRouteProcessItemList)) {
				selfLeadTime = processRouteOrderManager
						.caculateSKUProdTimeByProcess(
								materialStockKeepUnit.getUuid(), amount,
								refUnitUUID, processRouteProcessItemList);

			}
		}
		// By default mode: the self lead time by material information
		if (selfLeadTime == 0) {
			double varLeadTime = 0;
			if (materialStockKeepUnit.getAmountForVarLeadTime() > 0) {
				varLeadTime = materialStockKeepUnit.getVariableLeadTime()
						* ratioToStandardUnit * amount
						/ materialStockKeepUnit.getAmountForVarLeadTime();
				varLeadTime = ServiceEntityDoubleHelper
						.trancateDoubleScale2(varLeadTime);
			}
			selfLeadTime = materialStockKeepUnit.getFixLeadTime() + varLeadTime;
		}
		return selfLeadTime;
	}

	/**
	 * Set the lead time for Production Order
	 * 
	 * @param billOfMaterialOrder
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public void processSetProductionOrderLeadTime(
			BillOfMaterialOrder billOfMaterialOrder,
			ProductionOrder productionOrder,
			List<ServiceEntityNode> productiveBOMList,
			List<ServiceEntityNode> rawMaterialList) throws MaterialException, ServiceEntityConfigureException, ServiceComExecuteException {
		List<ServiceEntityNode> subBOMItemList = billOfMaterialOrderManager
				.filterSubBOMItemList(billOfMaterialOrder.getUuid(),
						productiveBOMList);
		MaterialStockKeepUnit materialStockKeepUnit = materialStockKeepUnitManager
				.getMaterialSKUWrapper(
						productionOrder.getRefMaterialSKUUUID(),
						productionOrder.getClient(), rawMaterialList);
		double ratioToStandardUnit = getRatioToStandardUnit(productionOrder,
				materialStockKeepUnit);
		/**
		 * Calculate the lead time for this Material
		 */
		double selfLeadTime = calculateSelfLeadTime(
				billOfMaterialOrder.getLeadTimeCalMode(),
				billOfMaterialOrder.getRefRouteOrderUUID(),
				ratioToStandardUnit, materialStockKeepUnit,
				productionOrder.getAmount(), productionOrder.getRefUnitUUID());
		double maxSubLeadTime = 0;
		if (subBOMItemList != null && subBOMItemList.size() > 0) {
			for (ServiceEntityNode subSENode : subBOMItemList) {
				ProductiveBOMItem subBOMItem = (ProductiveBOMItem) subSENode;
				if (subBOMItem.getComLeadTime() > maxSubLeadTime) {
					maxSubLeadTime = subBOMItem.getComLeadTime();
				}
			}
		}
		productionOrder.setSelfLeadTime(selfLeadTime);
		productionOrder.setComLeadTime(selfLeadTime + maxSubLeadTime);	
	}

	/**
	 * [Internal method] Recursive calculate and process the current productive
	 * BOM layer's lead time, and recursive call to parent BOM layer until the
	 * top
	 * 
	 * @param curLayerBOMList
	 *            : current productive BOM layer
	 * @param productiveBOMList
	 *            : all productive BOM data list
	 * @param rawMaterialList
	 *            : all raw material list
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	protected void processProductiveBOMLeadTime(
			List<ServiceEntityNode> curLayerBOMList,
			List<ServiceEntityNode> productiveBOMList,
			List<ServiceEntityNode> rawMaterialList) throws ServiceEntityConfigureException, MaterialException, ServiceComExecuteException {
		// Pre-requirement: all the sub BOM item has been processed lead time
		if (ServiceCollectionsHelper.checkNullList(curLayerBOMList)) {
			return;
		}
		List<ServiceEntityNode> upperLayerBOMList = new ArrayList<ServiceEntityNode>();
		for (ServiceEntityNode seNode : curLayerBOMList) {
			ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
			/**
			 * [Step1]Calculate the self lead time
			 */
			List<ServiceEntityNode> subBOMItemList = billOfMaterialOrderManager
					.filterSubBOMItemList(productiveBOMItem.getUuid(),
							productiveBOMList);
			MaterialStockKeepUnit materialStockKeepUnit = materialStockKeepUnitManager
					.getMaterialSKUWrapper(
							productiveBOMItem.getRefMaterialSKUUUID(),
							productiveBOMItem.getClient(), rawMaterialList);
			double ratioToStandardUnit = getRatioToStandardUnit(
					productiveBOMItem, materialStockKeepUnit);
			double selfLeadTime = calculateSelfLeadTime(
					productiveBOMItem.getLeadTimeCalMode(),
					productiveBOMItem.getRefRouteOrderUUID(),
					ratioToStandardUnit, materialStockKeepUnit,
					productiveBOMItem.getAmount(),
					productiveBOMItem.getRefUnitUUID());
			productiveBOMItem.setSelfLeadTime(selfLeadTime);
			double maxSubLeadTime = 0;
			if (subBOMItemList != null && subBOMItemList.size() > 0) {
				for (ServiceEntityNode subSENode : subBOMItemList) {
					ProductiveBOMItem subBOMItem = (ProductiveBOMItem) subSENode;
					if (subBOMItem.getComLeadTime() > maxSubLeadTime) {
						maxSubLeadTime = subBOMItem.getComLeadTime();
					}
				}
			}
			productiveBOMItem.setComLeadTime(selfLeadTime + maxSubLeadTime);
			ProductiveBOMItem parentBOMItem = (ProductiveBOMItem) billOfMaterialOrderManager
					.filterBOMItemByUUID(
							productiveBOMItem.getRefParentItemUUID(),
							productiveBOMList);
			if (parentBOMItem != null) {
				ServiceCollectionsHelper.mergeToList(upperLayerBOMList,
						parentBOMItem);
			}
		}
		processProductiveBOMLeadTime(upperLayerBOMList, productiveBOMList,
				rawMaterialList);
	}

	/**
	 * [Internal method] Calculate the ratio from production BOM item amount
	 * request to standard unit
	 * 
	 * @param productiveBOMItem
	 * @param materialStockKeepUnit
	 * @return
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 */
	protected double getRatioToStandardUnit(
			ProductiveBOMItem productiveBOMItem,
			MaterialStockKeepUnit materialStockKeepUnit)
			throws MaterialException, ServiceEntityConfigureException {
		StorageCoreUnit storageCoreUnitBOM = new StorageCoreUnit();
		storageCoreUnitBOM.setAmount(productiveBOMItem.getAmountWithLossRate());
		storageCoreUnitBOM.setRefMaterialSKUUUID(productiveBOMItem
				.getRefMaterialSKUUUID());
		storageCoreUnitBOM.setRefUnitUUID(productiveBOMItem.getRefUnitUUID());
		StorageCoreUnit storageCoreMaterial = new StorageCoreUnit();
		storageCoreMaterial.setAmount(1);
		storageCoreMaterial.setRefMaterialSKUUUID(materialStockKeepUnit
				.getUuid());
		// Set the material standard unit
		storageCoreMaterial.setRefUnitUUID(materialStockKeepUnitManager
				.getMainUnitUUID(materialStockKeepUnit));
		return materialStockKeepUnitManager.getStorageUnitRatio(
				storageCoreUnitBOM, storageCoreMaterial,
				productiveBOMItem.getClient());
	}

	/**
	 * [Internal method] Calculate the ratio from production order amount
	 * request to standard unit
	 * 
	 * @param productionOrder
	 * @param materialStockKeepUnit
	 * @return
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 */
	protected double getRatioToStandardUnit(ProductionOrder productionOrder,
			MaterialStockKeepUnit materialStockKeepUnit)
			throws MaterialException, ServiceEntityConfigureException {
		StorageCoreUnit storageCoreUnitBOM = new StorageCoreUnit();
		storageCoreUnitBOM.setAmount(productionOrder.getAmount());
		storageCoreUnitBOM.setRefMaterialSKUUUID(productionOrder
				.getRefMaterialSKUUUID());
		storageCoreUnitBOM.setRefUnitUUID(productionOrder.getRefUnitUUID());
		StorageCoreUnit storageCoreMaterial = new StorageCoreUnit();
		storageCoreMaterial.setAmount(1);
		storageCoreMaterial.setRefMaterialSKUUUID(materialStockKeepUnit
				.getUuid());
		// Set the material standard unit
		storageCoreMaterial.setRefUnitUUID(materialStockKeepUnitManager
				.getMainUnitUUID(materialStockKeepUnit));
		return materialStockKeepUnitManager.getStorageUnitRatio(
				storageCoreUnitBOM, storageCoreMaterial,
				materialStockKeepUnit.getClient());
	}

	/**
	 * Get List of Footer Productive BOM List with none null value
	 * 
	 * @param productiveBOMList
	 * @return
	 */
	protected List<ServiceEntityNode> getFooterProdBOMItemListNoneNull(
			List<ServiceEntityNode> productiveBOMList) {
		if (ServiceCollectionsHelper.checkNullList(productiveBOMList)) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
		for (ServiceEntityNode seNode : productiveBOMList) {
			ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
			if (productiveBOMItem.getAmountWithLossRate() <= 0) {
				// Skip the null value firstly
				continue;
			}
			List<ServiceEntityNode> subBOMList = billOfMaterialOrderManager
					.filterAllSubBOMItemList(seNode.getUuid(),
							productiveBOMList);
			if (subBOMList == null || subBOMList.size() == 0) {
				resultList.add(productiveBOMItem);
				continue;
			}
			// Check if all the sub BOM item is with null value
			for (ServiceEntityNode subSENode : subBOMList) {
				ProductiveBOMItem subBOMItem = (ProductiveBOMItem) subSENode;
				if (subBOMItem.getAmount() > 0) {
					continue;
				}
			}
			resultList.add(productiveBOMItem);
		}
		return resultList;
	}

}
