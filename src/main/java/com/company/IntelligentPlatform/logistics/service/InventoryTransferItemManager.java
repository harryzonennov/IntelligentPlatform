package com.company.IntelligentPlatform.logistics.service;

import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.logistics.dto.InventoryTransferItemUIModel;
import com.company.IntelligentPlatform.logistics.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.logistics.model.StoreAvailableStoreItemRequest;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class InventoryTransferItemManager {

	public static final String METHOD_ConvInventoryTransferItemToUI = "convInventoryTransferItemToUI";

	public static final String METHOD_ConvUIToInventoryTransferItem = "convUIToInventoryTransferItem";

	public static final String METHOD_ConvMaterialSKUToItemUI = "convMaterialSKUToItemUI";

	public static final String METHOD_ConvOutboundDeliveryToItemUI = "convOutboundDeliveryToItemUI";

	public static final String METHOD_ConvOutboundWarehouseToItemUI = "convOutboundWarehouseToItemUI";

	public static final String METHOD_ConvInboundWarehouseToItemUI = "convInboundWarehouseToItemUI";

	public static final String METHOD_ConvOutboundWarehouseAreaToItemUI = "convOutboundWarehouseAreaToItemUI";

	public static final String METHOD_ConvInboundWarehouseAreaToItemUI = "convInboundWarehouseAreaToItemUI";

	public static final String METHOD_ConvInboundDeliveryToItemUI = "convInboundDeliveryToItemUI";

	public static final String METHOD_ConvWarehouseStoreItemToUI = "convWarehouseStoreItemToUI";

	public static final String METHOD_ConvParentDocToItemUI = "convParentDocToItemUI";


	@Autowired
	protected InboundDeliveryManager inboundDeliveryManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected InventoryTransferOrderManager inventoryTransferOrderManager;

	@Autowired
	protected OutboundDeliveryWarehouseItemManager outboundDeliveryWarehouseItemManager;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;
	
	@Autowired
	protected DocFlowProxy docFlowProxy;

	protected Logger logger = LoggerFactory.getLogger(InventoryTransferItemManager.class);

	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), InventoryTransferOrder.NODENAME,
						request.getUuid(), InventoryTransferItem.NODENAME, inventoryTransferOrderManager);
		docPageHeaderInputPara.setGenBaseModelList(
				(DocPageHeaderModelProxy.GenBaseModelList<InventoryTransferOrder>) inventoryTransferItem -> {
					// How to get the base page header model list
                    return docPageHeaderModelProxy.getDocPageHeaderModelList(inventoryTransferItem, null);
				});
		docPageHeaderInputPara.setGenHomePageModel(
				(DocPageHeaderModelProxy.GenHomePageModel<InventoryTransferItem>) (inventoryTransferItem, pageHeaderModel) ->
						docPageHeaderModelProxy.getDefDocMaterialItemPageHeaderModel(inventoryTransferItem, pageHeaderModel));
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}



	public void convOutWarehouseToItemUI(Warehouse warehouse,
			InventoryTransferItemUIModel inventoryTransferItemUIModel) {
		if (warehouse != null) {
			inventoryTransferItemUIModel.setRefOutboundWarehouseId(warehouse
					.getId());
			inventoryTransferItemUIModel.setRefOutboundWarehouseName(warehouse
					.getName());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convInventoryTransferItemToUI(
			InventoryTransferItem inventoryTransferItem,
			InventoryTransferItemUIModel inventoryTransferItemUIModel, LogonInfo logonInfo) {
		if (inventoryTransferItem != null) {
			docFlowProxy.convDocMatItemToUI(inventoryTransferItem, inventoryTransferItemUIModel, logonInfo);
			inventoryTransferItemUIModel
					.setStorageFee(inventoryTransferItem
							.getStorageFee());			
			inventoryTransferItemUIModel
					.setUnitPriceNoTax(inventoryTransferItem
							.getUnitPriceNoTax());			
			inventoryTransferItemUIModel
					.setProducerName(inventoryTransferItem
							.getProducerName());
			inventoryTransferItemUIModel
					.setRefInboundItemUUID(inventoryTransferItem
							.getRefInboundItemUUID());
			inventoryTransferItemUIModel
					.setWeight(inventoryTransferItem.getWeight());
			inventoryTransferItemUIModel
					.setRefStoreItemUUID(inventoryTransferItem
							.getRefStoreItemUUID());
			inventoryTransferItemUIModel.setItemStatus(inventoryTransferItem.getItemStatus());
			inventoryTransferItemUIModel
					.setRefUnitName(inventoryTransferItem
							.getRefUnitName());
			inventoryTransferItemUIModel
					.setStoreDay(inventoryTransferItem.getStoreDay());
			inventoryTransferItemUIModel
					.setRefOutboundItemUUID(inventoryTransferItem
							.getRefOutboundItemUUID());
			inventoryTransferItemUIModel
					.setOutboundFee(inventoryTransferItem
							.getOutboundFee());
			inventoryTransferItemUIModel.setItemStatus(inventoryTransferItem.getItemStatus());
			if(logonInfo != null){
				Map<Integer, String> itemStatusMap = null;
				try {
					itemStatusMap = inventoryTransferOrderManager.initStatusMap(logonInfo.getLanguageCode());
					if(itemStatusMap != null){
						inventoryTransferItemUIModel.setItemStatusValue(itemStatusMap.get(inventoryTransferItem.getItemStatus()));
					}
				} catch (ServiceEntityInstallationException e) {
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "itemStatus"));
				}
			}
			inventoryTransferItemUIModel
					.setItemPriceNoTax(inventoryTransferItem
							.getItemPriceNoTax());
			inventoryTransferItemUIModel
					.setDeclaredValue(inventoryTransferItem
							.getDeclaredValue());
			inventoryTransferItemUIModel
					.setTaxRate(inventoryTransferItem.getTaxRate());
			inventoryTransferItemUIModel
					.setProductionBatchNumber(inventoryTransferItem
							.getProductionBatchNumber());			
			inventoryTransferItemUIModel
					.setRefOutboundWarehouseAreaUUID(inventoryTransferItem
							.getRefWarehouseAreaUUID());
			inventoryTransferItemUIModel
					.setRefInboundWarehouseAreaUUID(inventoryTransferItem
							.getRefWarehouseAreaUUID());			
			inventoryTransferItemUIModel
					.setVolume(inventoryTransferItem.getVolume());
			
		}
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:inventoryTransferItem
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToInventoryTransferItem(
			InventoryTransferItemUIModel inventoryTransferItemUIModel,
			InventoryTransferItem rawEntity) {
		docFlowProxy.convUIToDocMatItem(inventoryTransferItemUIModel, rawEntity);		
		rawEntity.setStorageFee(inventoryTransferItemUIModel.getStorageFee());		
		rawEntity.setUnitPriceNoTax(inventoryTransferItemUIModel
				.getUnitPriceNoTax());		
		rawEntity.setProducerName(inventoryTransferItemUIModel
				.getProducerName());
		if (!ServiceEntityStringHelper
				.checkNullString(inventoryTransferItemUIModel
						.getRefInboundItemUUID())) {
			rawEntity.setRefInboundItemUUID(inventoryTransferItemUIModel
					.getRefInboundItemUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(inventoryTransferItemUIModel
						.getRefStoreItemUUID())) {
			rawEntity.setRefStoreItemUUID(inventoryTransferItemUIModel
					.getRefStoreItemUUID());
		}
		rawEntity.setWeight(inventoryTransferItemUIModel.getWeight());
		rawEntity.setStoreDay(inventoryTransferItemUIModel.getStoreDay());
		rawEntity.setRefOutboundItemUUID(inventoryTransferItemUIModel
				.getRefOutboundItemUUID());
		rawEntity.setOutboundFee(inventoryTransferItemUIModel.getOutboundFee());
		rawEntity.setItemPriceNoTax(inventoryTransferItemUIModel
				.getItemPriceNoTax());
		rawEntity.setDeclaredValue(inventoryTransferItemUIModel
				.getDeclaredValue());
		rawEntity.setTaxRate(inventoryTransferItemUIModel.getTaxRate());		
		rawEntity.setVolume(inventoryTransferItemUIModel.getVolume());		
	}

	public void convMaterialSKUToItemUI(
			MaterialStockKeepUnit materialStockKeepUnit,
			InventoryTransferItemUIModel inventoryTransferItemUIModel) {
		if (materialStockKeepUnit != null) {
			docFlowProxy.convMaterialSKUToItemUI(materialStockKeepUnit, inventoryTransferItemUIModel);
			if (materialStockKeepUnit.getProductionDate() != null) {
				inventoryTransferItemUIModel
						.setProductionDate(DefaultDateFormatConstant.DATE_FORMAT
								.format(materialStockKeepUnit.getProductionDate()));
			}
			inventoryTransferItemUIModel.setUnitValue(materialStockKeepUnit
					.getRetailPrice());
			// In case Registered Product
		}
	}


	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convInboundWarehouseAreaToItemUI(
			WarehouseArea inboundWarehouseArea,
			InventoryTransferItemUIModel inventoryTransferItemUIModel) {
		if (inboundWarehouseArea != null) {
			inventoryTransferItemUIModel
					.setRefInboundWarehouseAreaUUID(inboundWarehouseArea
							.getUuid());
			inventoryTransferItemUIModel
					.setRefInboundWarehouseAreaName(inboundWarehouseArea
							.getName());
			inventoryTransferItemUIModel
					.setRefInboundWarehouseAreaId(inboundWarehouseArea.getId());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convOutboundWarehouseAreaToItemUI(
			WarehouseArea outboundWarehouseArea,
			InventoryTransferItemUIModel inventoryTransferItemUIModel) {
		if (outboundWarehouseArea != null) {
			inventoryTransferItemUIModel
					.setRefOutboundWarehouseAreaUUID(outboundWarehouseArea
							.getUuid());
			inventoryTransferItemUIModel
					.setRefOutboundWarehouseAreaId(outboundWarehouseArea
							.getId());
			inventoryTransferItemUIModel
					.setRefOutboundWarehouseAreaName(outboundWarehouseArea
							.getName());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convInboundWarehouseToItemUI(Warehouse inboundWarehouse,
			InventoryTransferItemUIModel inventoryTransferItemUIModel) {
		if (inboundWarehouse != null) {
			inventoryTransferItemUIModel
					.setRefInboundWarehouseUUID(inboundWarehouse.getUuid());
			inventoryTransferItemUIModel
					.setRefInboundWarehouseName(inboundWarehouse.getName());
			inventoryTransferItemUIModel
					.setRefInboundWarehouseId(inboundWarehouse.getId());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convOutboundWarehouseToItemUI(Warehouse outboundWarehouse,
			InventoryTransferItemUIModel inventoryTransferItemUIModel) {
		if (outboundWarehouse != null) {
			inventoryTransferItemUIModel
					.setRefOutboundWarehouseUUID(outboundWarehouse.getUuid());
			inventoryTransferItemUIModel
					.setRefOutboundWarehouseId(outboundWarehouse.getId());
			inventoryTransferItemUIModel
					.setRefOutboundWarehouseName(outboundWarehouse.getName());
		}
	}

	public void convWarehouseStoreItemToUI(
			WarehouseStoreItem warehouseStoreItem,
			InventoryTransferItemUIModel inventoryTransferItemUIModel) {
		if (warehouseStoreItem != null) {
			StoreAvailableStoreItemRequest storeAvailableStoreItemRequest = new StoreAvailableStoreItemRequest(warehouseStoreItem,
					inventoryTransferItemUIModel.getUuid(), false);
			try {
				StorageCoreUnit availableStoreCoreUnit = outboundDeliveryWarehouseItemManager
						.getAvailableStoreItemAmountUnion(storeAvailableStoreItemRequest);
				inventoryTransferItemUIModel.setAvailableAmount(availableStoreCoreUnit.getAmount());
				String amountLabel = materialStockKeepUnitManager
						.getAmountLabel(availableStoreCoreUnit.getRefMaterialSKUUUID(), availableStoreCoreUnit.getRefUnitUUID(),
								availableStoreCoreUnit.getAmount(), warehouseStoreItem.getClient());
				inventoryTransferItemUIModel.setAvailableAmountLabel(amountLabel);
				inventoryTransferItemUIModel.setAvailableRefUnitUUID(availableStoreCoreUnit.getRefUnitUUID());
			} catch (ServiceEntityConfigureException | MaterialException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "convWarehouseItemToItemUI"));
			}
        }
	}

	public void convParentDocToItemUI(
			InventoryTransferOrder inventoryTransferOrder,
			InventoryTransferItemUIModel inventoryTransferItemUIModel,
			LogonInfo logonInfo) {
		if (inventoryTransferOrder != null) {
			docFlowProxy.convParentDocToItemUI(inventoryTransferOrder, inventoryTransferItemUIModel, logonInfo);
		}
	}

	public void convOutboundDeliveryToItemUI(OutboundDelivery outboundDelivery,
			InventoryTransferItemUIModel inventoryTransferItemUIModel) {
		if (outboundDelivery != null) {
			inventoryTransferItemUIModel
					.setRefOutboundDeliveryId(outboundDelivery.getId());
			inventoryTransferItemUIModel
					.setRefOutboundDeliveryStatus(outboundDelivery.getStatus());
		}
	}


	public void convInboundDeliveryToItemUI(InboundDelivery inboundDelivery,
											 InventoryTransferItemUIModel inventoryTransferItemUIModel) {
		if (inboundDelivery != null) {
			inventoryTransferItemUIModel
					.setRefInboundDeliveryId(inboundDelivery.getId());
			inventoryTransferItemUIModel
					.setRefInboundDeliveryStatus(inboundDelivery.getStatus());
		}
	}



}
