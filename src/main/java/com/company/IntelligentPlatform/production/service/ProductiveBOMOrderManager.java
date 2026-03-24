package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.production.dto.ProductiveBOMOrderUIModel;
import com.company.IntelligentPlatform.production.dto.ProductiveBOMItemUIModel;
import com.company.IntelligentPlatform.production.model.ProductiveBOMItem;
import com.company.IntelligentPlatform.production.model.ProductiveBOMOrder;
import com.company.IntelligentPlatform.production.model.ProcessRouteOrder;
import com.company.IntelligentPlatform.production.model.ProcessRouteProcessItem;
import com.company.IntelligentPlatform.production.model.ProdProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class ProductiveBOMOrderManager extends ServiceEntityManager {

	public static final String METHOD_ConvProductiveBOMOrderToUI = "convProductiveBOMOrderToUI";

	public static final String METHOD_ConvUIToProductiveBOMOrder = "convUIToProductiveBOMOrder";

	public static final String METHOD_ConvMaterialStockKeepUnitToUI = "convMaterialStockKeepUnitToUI";

	public static final String METHOD_ConvProcessRouteOrderToUI = "convProcessRouteOrderToUI";

	public static final String METHOD_ConvProdProcessToUI = "convProdProcessToUI";
	
	protected Logger logger = LoggerFactory.getLogger(ProductiveBOMOrderManager.class);

	@Autowired
	protected ProductiveBOMItemManager productiveBOMItemManager;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	public ServiceModule convertToProductiveBOMServiceModel(
			ProductiveBOMOrder productiveBOMOrder,
			List<ServiceEntityNode> productiveBOMList) {
		if (ServiceCollectionsHelper.checkNullList(productiveBOMList)) {
			return null;
		}
		ProductiveBOMOrderServiceModel productiveBOMOrderServiceModel = new ProductiveBOMOrderServiceModel();
		productiveBOMOrderServiceModel
				.setProductiveBOMOrder(productiveBOMOrder);
		List<ProductiveBOMItemServiceModel> subProductiveBOMItemList = generateSubProdutiveBOMItem(
				productiveBOMOrder.getUuid(), productiveBOMList);
		if (!ServiceCollectionsHelper.checkNullList(subProductiveBOMItemList)) {
			productiveBOMOrderServiceModel
					.setProductiveBOMItemList(subProductiveBOMItemList);
		}
		return productiveBOMOrderServiceModel;
	}

	protected List<ProductiveBOMItemServiceModel> generateSubProdutiveBOMItem(
			String parentItemUUID, List<ServiceEntityNode> productiveBOMList) {
		List<ProductiveBOMItemServiceModel> result = new ArrayList<>();
		List<ServiceEntityNode> subBomLayerList = this.filterSubBOMItemList(
				parentItemUUID, productiveBOMList);
		if (!ServiceCollectionsHelper.checkNullList(subBomLayerList)) {
			for (ServiceEntityNode seNode : subBomLayerList) {
				ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
				ProductiveBOMItemServiceModel productiveBOMItemServiceModel = new ProductiveBOMItemServiceModel();
				productiveBOMItemServiceModel
						.setProductiveBOMItem(productiveBOMItem);
				List<ProductiveBOMItemServiceModel> subProductiveBOMItemList = generateSubProdutiveBOMItem(
						productiveBOMItem.getUuid(), productiveBOMList);
				if (!ServiceCollectionsHelper
						.checkNullList(subProductiveBOMItemList)) {
					productiveBOMItemServiceModel
							.setSubProductiveBOMItemList(subProductiveBOMItemList);
				}
				result.add(productiveBOMItemServiceModel);
			}
			return result;
		} else {
			return null;
		}
	}

	public void convProductiveBOMOrderToUI(
			ProductiveBOMOrder productiveBOMOrder,
			ProductiveBOMOrderUIModel productiveBOMOrderUIModel) throws ServiceEntityInstallationException, ServiceEntityConfigureException {
		convProductiveBOMOrderToUI(productiveBOMOrder, productiveBOMOrderUIModel, null);
	}

	public void convProductiveBOMOrderToUI(
			ProductiveBOMOrder productiveBOMOrder,
			ProductiveBOMOrderUIModel productiveBOMOrderUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		if (productiveBOMOrder != null) {
			if (!ServiceEntityStringHelper.checkNullString(productiveBOMOrder
					.getUuid())) {
				productiveBOMOrderUIModel.setUuid(productiveBOMOrder.getUuid());
			}
			if (!ServiceEntityStringHelper.checkNullString(productiveBOMOrder
					.getParentNodeUUID())) {
				productiveBOMOrderUIModel.setParentNodeUUID(productiveBOMOrder
						.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(productiveBOMOrder
					.getRootNodeUUID())) {
				productiveBOMOrderUIModel.setRootNodeUUID(productiveBOMOrder
						.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(productiveBOMOrder
					.getClient())) {
				productiveBOMOrderUIModel.setClient(productiveBOMOrder
						.getClient());
			}
			productiveBOMOrderUIModel.setId(productiveBOMOrder.getId());
			productiveBOMOrderUIModel.setNote(productiveBOMOrder.getNote());
			productiveBOMOrderUIModel.setItemCategory(productiveBOMOrder
					.getItemCategory());
			productiveBOMOrderUIModel.setLeadTimeCalMode(productiveBOMOrder
					.getLeadTimeCalMode());
			if(logonInfo != null){
				Map<Integer, String> statusMap = billOfMaterialOrderManager
						.initStatusMap(logonInfo.getLanguageCode());
				productiveBOMOrderUIModel.setStatusValue(statusMap
						.get(productiveBOMOrder.getStatus()));
			}
			productiveBOMOrderUIModel.setStatus(productiveBOMOrder.getStatus());
			productiveBOMOrderUIModel.setRefMaterialSKUUUID(productiveBOMOrder
					.getRefMaterialSKUUUID());
			productiveBOMOrderUIModel.setAmount(productiveBOMOrder.getAmount());
			productiveBOMOrderUIModel.setAmountWithLossRate(productiveBOMOrder
					.getAmountWithLossRate());
			productiveBOMOrderUIModel.setRefUnitUUID(productiveBOMOrder
					.getRefUnitUUID());
			productiveBOMOrderUIModel.setRefUnitName(productiveBOMOrder
					.getRefUnitUUID());
			try {
				String amountLabel = materialStockKeepUnitManager
						.getAmountLabel(
								productiveBOMOrder.getRefMaterialSKUUUID(),
								productiveBOMOrder.getRefUnitUUID(),
								productiveBOMOrder.getAmount(),
								productiveBOMOrder.getClient());
				String amountLossRateLabel = materialStockKeepUnitManager
						.getAmountLabel(
								productiveBOMOrder.getRefMaterialSKUUUID(),
								productiveBOMOrder.getRefUnitUUID(),
								productiveBOMOrder.getAmountWithLossRate(),
								productiveBOMOrder.getClient());
				productiveBOMOrderUIModel.setAmountLabel(amountLabel);
				productiveBOMOrderUIModel
						.setAmountLossRateLabel(amountLossRateLabel);
			} catch (MaterialException e) {
				// just skip
			} catch (ServiceEntityConfigureException e) {
				// just skip
			}
		}
	}

	public void convUIToProductiveBOMOrder(
			ProductiveBOMOrderUIModel productiveBOMOrderUIModel,
			ProductiveBOMOrder rawEntity) {
		if (!ServiceEntityStringHelper
				.checkNullString(productiveBOMOrderUIModel.getUuid())) {
			rawEntity.setUuid(productiveBOMOrderUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(productiveBOMOrderUIModel.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(productiveBOMOrderUIModel
					.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(productiveBOMOrderUIModel.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(productiveBOMOrderUIModel
					.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(productiveBOMOrderUIModel.getClient())) {
			rawEntity.setClient(productiveBOMOrderUIModel.getClient());
		}
		rawEntity.setId(productiveBOMOrderUIModel.getId());
		rawEntity.setNote(productiveBOMOrderUIModel.getNote());
		rawEntity.setRefMaterialSKUUUID(productiveBOMOrderUIModel
				.getRefMaterialSKUUUID());
		rawEntity.setId(productiveBOMOrderUIModel.getId());
		rawEntity.setNote(productiveBOMOrderUIModel.getNote());
		rawEntity.setAmount(productiveBOMOrderUIModel.getAmount());
		rawEntity.setAmountWithLossRate(productiveBOMOrderUIModel
				.getAmountWithLossRate());
		rawEntity.setRefUnitUUID(productiveBOMOrderUIModel.getRefUnitUUID());
		rawEntity.setLeadTimeCalMode(productiveBOMOrderUIModel
				.getLeadTimeCalMode());
		rawEntity.setRefRouteOrderUUID(productiveBOMOrderUIModel
				.getRefRouteOrderUUID());
	}
	
	public void convMaterialStockKeepUnitToUI(
			MaterialStockKeepUnit materialStockKeepUnit,
			ProductiveBOMOrderUIModel productiveBOMOrderUIModel) {
		convMaterialStockKeepUnitToUI(materialStockKeepUnit, productiveBOMOrderUIModel, null);
	}

	public void convMaterialStockKeepUnitToUI(
			MaterialStockKeepUnit materialStockKeepUnit,
			ProductiveBOMOrderUIModel productiveBOMOrderUIModel, LogonInfo logonInfo) {
		if (materialStockKeepUnit != null) {
			productiveBOMOrderUIModel.setRefMaterialSKUId(materialStockKeepUnit
					.getId());
			productiveBOMOrderUIModel
					.setRefMaterialSKUName(materialStockKeepUnit.getName());
			productiveBOMOrderUIModel.setSupplyType(materialStockKeepUnit
					.getSupplyType());
			productiveBOMOrderUIModel.setPackageStandard(materialStockKeepUnit
					.getPackageStandard());
			if(logonInfo != null){
				try {
					Map<Integer, String> supplyTypeMap = materialStockKeepUnitManager
							.initSupplyTypeMap(logonInfo.getLanguageCode());
					productiveBOMOrderUIModel.setSupplyTypeValue(supplyTypeMap
							.get(materialStockKeepUnit.getSupplyType()));
				} catch (ServiceEntityInstallationException e) {
					// log error and continue
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "supplyType"));
				}
			}	
			
		}
	}

	public void convProcessRouteOrderToUI(ProcessRouteOrder processRouteOrder,
			ProductiveBOMOrderUIModel productiveBOMOrderUIModel) {
		if (processRouteOrder != null) {
			productiveBOMOrderUIModel.setRefRouteOrderUUID(processRouteOrder
					.getUuid());
			productiveBOMOrderUIModel.setRefRouteOrderId(processRouteOrder
					.getId());
			productiveBOMOrderUIModel.setRefRouteOrderName(processRouteOrder
					.getId());
		}
	}


	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convProcessRouteProcessItemToUI(
			ProcessRouteProcessItem processRouteProcessItem,
			ProductiveBOMItemUIModel productiveBOMItemUIModel) {
		if (processRouteProcessItem != null) {
			productiveBOMItemManager.convProcessRouteProcessItemToUI(
					processRouteProcessItem, productiveBOMItemUIModel);
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convProdProcessToUI(ProdProcess prodProcess,
			ProductiveBOMItemUIModel productiveBOMItemUIModel) {
		if (prodProcess != null) {
			productiveBOMItemManager.convProdProcessToUI(prodProcess,
					productiveBOMItemUIModel);
		}
	}

	/**
	 * Get the Buttom BOM item layer
	 * 
	 * @param rawBomItemList
	 * @return
	 */
	public int getFooterBOMItemLayer(List<ServiceEntityNode> rawBomItemList) {
		if (rawBomItemList == null || rawBomItemList.size() == 0) {
			return 0;
		}
		int buttomLayer = 1;
		for (ServiceEntityNode seNode : rawBomItemList) {
			ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
			if (productiveBOMItem.getLayer() > buttomLayer) {
				buttomLayer = productiveBOMItem.getLayer();
			}
		}
		return buttomLayer;
	}

	/**
	 * Filter the online BOM list by next layer
	 * 
	 * @param parentItemUUID
	 * @param rawBomItemList
	 * @return
	 */
	public List<ServiceEntityNode> filterSubBOMItemList(String parentItemUUID,
			List<ServiceEntityNode> rawBomItemList) {
		if (rawBomItemList == null || rawBomItemList.size() == 0) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
		for (ServiceEntityNode seNode : rawBomItemList) {
			ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
			if (productiveBOMItem.getRefParentItemUUID().equals(parentItemUUID)) {
				resultList.add(productiveBOMItem);
			}
		}
		return resultList;
	}

	/**
	 * Filter ALL the sub BOM item list until the button
	 * 
	 * @param parentItemUUID
	 * @param rawBomItemList
	 * @return
	 */
	public List<ServiceEntityNode> filterAllSubBOMItemList(
			String parentItemUUID, List<ServiceEntityNode> rawBomItemList) {
		List<ServiceEntityNode> directSubBomItemList = filterSubBOMItemList(
				parentItemUUID, rawBomItemList);
		if (directSubBomItemList == null || directSubBomItemList.size() == 0) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
		resultList.addAll(directSubBomItemList);
		for (ServiceEntityNode seNode : directSubBomItemList) {
			List<ServiceEntityNode> subBomItemList = filterAllSubBOMItemList(
					seNode.getUuid(), rawBomItemList);
			if (subBomItemList != null && subBomItemList.size() > 0) {
				resultList.addAll(subBomItemList);
			}
		}
		return resultList;
	}

	/**
	 * Filter the online BOM list by layer
	 * 
	 * @param layer
	 * @param rawBomItemList
	 * @return
	 */
	public List<ServiceEntityNode> filterBOMItemListByLayer(int layer,
			List<ServiceEntityNode> rawBomItemList) {
		if (rawBomItemList == null || rawBomItemList.size() == 0) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
		for (ServiceEntityNode seNode : rawBomItemList) {
			ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
			if (productiveBOMItem.getLayer() == layer) {
				resultList.add(productiveBOMItem);
			}
		}
		return resultList;
	}

	/**
	 * [Internal method] Get the list of buttom BOM item list, these item is raw
	 * material which doesn't has sub BOM sub material or has other BOM model
	 * related
	 * 
	 * @param rawBomItemList
	 *            : Raw, all of the BOM Item list, [pay attention]: these BOM
	 *            list should already finished BOM list merge job
	 * @return
	 */
	public List<ServiceEntityNode> getFooterBomItemList(
			List<ServiceEntityNode> rawBomItemList) {
		if (rawBomItemList == null || rawBomItemList.size() == 0) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
		for (ServiceEntityNode seNode : rawBomItemList) {
			ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
			List<ServiceEntityNode> directSubBomItemList = filterSubBOMItemList(
					productiveBOMItem.getUuid(), rawBomItemList);
			if (directSubBomItemList == null
					|| directSubBomItemList.size() == 0) {
				resultList.add(productiveBOMItem);
			}
		}
		return resultList;
	}

	/**
	 * Filter the online BOM Item by UUID
	 * 
	 * @param uuid
	 * @param rawBomItemList
	 * @return
	 */
	public ProductiveBOMItem filterBOMItemByUUID(String uuid,
			List<ServiceEntityNode> rawBomItemList) {
		if (ServiceCollectionsHelper.checkNullList(rawBomItemList)) {
			return null;
		}
		for (ServiceEntityNode seNode : rawBomItemList) {
			ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
			if (uuid.equals(productiveBOMItem.getUuid())) {
				return productiveBOMItem;
			}
		}
		return null;
	}

	/**
	 * Filter out all the upstream BOM item list in one path until the first
	 * layer
	 * 
	 * @param productiveBOMItem
	 * @param rawBomItemList
	 * @return
	 */
	public List<ServiceEntityNode> filterUpstreamBOMItemList(
			ProductiveBOMItem productiveBOMItem,
			List<ServiceEntityNode> rawBomItemList) {
		if (rawBomItemList == null || rawBomItemList.size() == 0) {
			return null;
		}
		if (productiveBOMItem.getLayer() <= 1) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
		ProductiveBOMItem parentItem = filterBOMItemByUUID(
				productiveBOMItem.getRefParentItemUUID(), rawBomItemList);
		if (parentItem != null) {
			resultList.add(parentItem);
			List<ServiceEntityNode> parentItemList = filterUpstreamBOMItemList(
					parentItem, rawBomItemList);
			if (parentItemList != null && parentItemList.size() > 0) {
				resultList.addAll(parentItemList);
			}
		}
		return resultList;
	}

	public List<ServiceEntityNode> filterFirstLayerSubItemListOnline(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityConfigureException {
		if (rawList == null || rawList.size() == 0) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
		for (ServiceEntityNode seNode : rawList) {
			ProductiveBOMItem productiveBOMItem = (ProductiveBOMItem) seNode;
			if (productiveBOMItem != null && productiveBOMItem.getLayer() == 1) {
				resultList.add(productiveBOMItem);
			}
		}
		return resultList;
	}

}
