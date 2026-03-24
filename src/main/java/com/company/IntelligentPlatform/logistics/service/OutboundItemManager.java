package com.company.IntelligentPlatform.logistics.service;

import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.logistics.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.logistics.dto.OutboundItemUIModel;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.logistics.model.StoreAvailableStoreItemRequest;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.SplitMatItemProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityDoubleHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import java.time.ZoneId;
import java.time.LocalDate;

@Service
public class OutboundItemManager {

	public static final String METHOD_ConvOutboundItemToUI = "convOutboundItemToUI";

	public static final String METHOD_ConvUIToOutboundItem = "convUIToOutboundItem";

	public static final String METHOD_ConvMaterialStockKeepUnitToUI = "convMaterialStockKeepUnitToUI";

	public static final String METHOD_ConvWarehouseToItemUI = "convWarehouseToItemUI";

	public static final String METHOD_ConvWarehouseItemToItemUI = "convWarehouseItemToItemUI";

	public static final String METHOD_ConvWarehouseAreaToItemUI = "convWarehouseAreaToItemUI";

	public static final String METHOD_ConvParentDocToItemUI = "convParentDocToItemUI";

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected OutboundDeliveryWarehouseItemManager outboundDeliveryWarehouseItemManager;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	@Autowired
	protected SplitMatItemProxy splitMatItemProxy;

	protected Logger logger = LoggerFactory.getLogger(OutboundItemManager.class);
	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), OutboundDelivery.NODENAME,
						request.getUuid(), OutboundItem.NODENAME, outboundDeliveryManager);
		docPageHeaderInputPara.setGenBaseModelList(
				(DocPageHeaderModelProxy.GenBaseModelList<OutboundDelivery>) outboundDelivery -> {
					// How to get the base page header model list
                    return docPageHeaderModelProxy.getDocPageHeaderModelList(outboundDelivery, null);
				});
		docPageHeaderInputPara.setGenHomePageModel(
				(DocPageHeaderModelProxy.GenHomePageModel<OutboundItem>) (outboundItem, pageHeaderModel) ->
						docPageHeaderModelProxy.getDefDocMaterialItemPageHeaderModel(outboundItem, pageHeaderModel));
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}

	public void convOutboundItemToUI(OutboundItem outboundItem, OutboundItemUIModel outboundItemUIModel)
			throws ServiceEntityInstallationException, ServiceEntityConfigureException {
		convOutboundItemToUI(outboundItem, outboundItemUIModel, null);
	}

	public void convOutboundItemToUI(OutboundItem outboundItem, OutboundItemUIModel outboundItemUIModel,
                                              LogonInfo logonInfo) throws ServiceEntityInstallationException, ServiceEntityConfigureException {
		if (outboundItem != null) {
			docFlowProxy.convDocMatItemToUI(outboundItem, outboundItemUIModel, logonInfo);
			if(logonInfo != null){
				Map<Integer, String> itemStatusMap = outboundDeliveryManager.initStatusMap(logonInfo.getLanguageCode());
				outboundItemUIModel.setItemStatusValue(itemStatusMap.get(outboundItem.getItemStatus()));
			}
			outboundItemUIModel.setVolume(ServiceEntityDoubleHelper.trancateDoubleScale4(outboundItem.getVolume()));
			outboundItemUIModel.setWeight(ServiceEntityDoubleHelper.trancateDoubleScale4(outboundItem.getWeight()));
			outboundItemUIModel.setDeclaredValue(outboundItem.getDeclaredValue());
			outboundItemUIModel.setProductionDate(outboundItem.getProductionDate() != null ? java.util.Date.from(outboundItem.getProductionDate().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()) : null);
			outboundItemUIModel.setRefWarehouseAreaUUID(outboundItem.getRefWarehouseAreaUUID());
			outboundItemUIModel.setRefShelfNumberId(outboundItem.getRefShelfNumberID());
			outboundItemUIModel.setProducerName(outboundItem.getProducerName());
			outboundItemUIModel.setOutboundFee(outboundItem.getOutboundFee());
			outboundItemUIModel.setStoreDay(outboundItem.getStoreDay());
			outboundItemUIModel.setStorageFee(outboundItem.getStorageFee());
			outboundItemUIModel.setRefStoreItemUUID(outboundItem.getRefStoreItemUUID());
			outboundItemUIModel.setTaxRate(outboundItem.getTaxRate());
			outboundItemUIModel.setCurrencyCode(outboundItem.getCurrencyCode());
			outboundItemUIModel
					.setItemPriceNoTax(ServiceEntityDoubleHelper.trancateDoubleScale2(outboundItem.getItemPriceNoTax()));
			outboundItemUIModel
					.setUnitPriceNoTax(ServiceEntityDoubleHelper.trancateDoubleScale2(outboundItem.getUnitPriceNoTax()));
			if (outboundItem.getCreatedTime() != null) {
				outboundItemUIModel
						.setCreatedDate(DefaultDateFormatConstant.DATE_FORMAT.format(outboundItem.getCreatedTime()));
			}
		}
	}

	public void convUIToOutboundItem(OutboundItemUIModel outboundItemUIModel, OutboundItem rawEntity) {
		docFlowProxy.convUIToDocMatItem(outboundItemUIModel, rawEntity);
		rawEntity.setVolume(ServiceEntityDoubleHelper.trancateDoubleScale4(outboundItemUIModel.getVolume()));
		rawEntity.setWeight(ServiceEntityDoubleHelper.trancateDoubleScale4(outboundItemUIModel.getWeight()));
		rawEntity.setDeclaredValue(outboundItemUIModel.getDeclaredValue());
		rawEntity.setOutboundFee(outboundItemUIModel.getOutboundFee());
		rawEntity.setStorageFee(outboundItemUIModel.getStorageFee());
		rawEntity.setRefUnitName(outboundItemUIModel.getRefUnitName());
		rawEntity.setPackageStandard(outboundItemUIModel.getPackageStandard());
		rawEntity.setRefMaterialSKUId(outboundItemUIModel.getRefMaterialSKUId());
		rawEntity.setRefMaterialSKUName(outboundItemUIModel.getRefMaterialSKUName());
		rawEntity.setTaxRate(outboundItemUIModel.getTaxRate());
		rawEntity.setCurrencyCode(outboundItemUIModel.getCurrencyCode());
		rawEntity.setItemPriceNoTax(ServiceEntityDoubleHelper.trancateDoubleScale2(outboundItemUIModel.getItemPriceNoTax()));
		rawEntity.setUnitPriceNoTax(ServiceEntityDoubleHelper.trancateDoubleScale2(outboundItemUIModel.getUnitPriceNoTax()));

	}

	public void convMaterialStockKeepUnitToUI(MaterialStockKeepUnit materialStockKeepUnit,
			OutboundItemUIModel outboundItemUIModel) {
		if (materialStockKeepUnit != null && outboundItemUIModel != null) {
			docFlowProxy.convMaterialSKUToItemUI(materialStockKeepUnit, outboundItemUIModel);
			outboundItemUIModel.setUnitValue(materialStockKeepUnit.getRetailPrice());
		}
	}

	public void convWarehouseToItemUI(Warehouse warehouse, OutboundItemUIModel outboundItemUIModel) {
		if (warehouse != null) {
			outboundItemUIModel.setRefWarehouseId(warehouse.getId());
			outboundItemUIModel.setRefWarehouseName(warehouse.getName());
			outboundItemUIModel.setRefWarehouseUUID(warehouse.getUuid());
		}
	}

	public void convWarehouseItemToItemUI(WarehouseStoreItem warehouseStoreItem, OutboundItemUIModel outboundItemUIModel) {
		if (warehouseStoreItem != null) {
			StoreAvailableStoreItemRequest storeAvailableStoreItemRequest = new StoreAvailableStoreItemRequest(warehouseStoreItem,
					outboundItemUIModel.getUuid(), false);
			try {
				StorageCoreUnit availableStoreCoreUnit = outboundDeliveryWarehouseItemManager
						.getAvailableStoreItemAmountUnion(storeAvailableStoreItemRequest);
				outboundItemUIModel.setAvailableAmount(availableStoreCoreUnit.getAmount());
				String amountLabel = materialStockKeepUnitManager
						.getAmountLabel(availableStoreCoreUnit.getRefMaterialSKUUUID(), availableStoreCoreUnit.getRefUnitUUID(),
								availableStoreCoreUnit.getAmount(), warehouseStoreItem.getClient());
				outboundItemUIModel.setAvailableAmountLabel(amountLabel);
				outboundItemUIModel.setAvailableRefUnitUUID(availableStoreCoreUnit.getRefUnitUUID());
			} catch (ServiceEntityConfigureException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "convWarehouseItemToItemUI"));
			} catch (MaterialException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "convWarehouseItemToItemUI"));
			}
		}
	}

	public void convParentDocToItemUI(OutboundDelivery outboundDelivery, OutboundItemUIModel outboundItemUIModel)
			throws ServiceEntityInstallationException {
		convParentDocToItemUI(outboundDelivery, outboundItemUIModel, null);
	}

	public void convParentDocToItemUI(OutboundDelivery outboundDelivery, OutboundItemUIModel outboundItemUIModel,
			LogonInfo logonInfo) throws ServiceEntityInstallationException {
		if (outboundDelivery != null) {
			docFlowProxy.convParentDocToItemUI(outboundDelivery, outboundItemUIModel, logonInfo);
			outboundItemUIModel.setRefWarehouseUUID(outboundDelivery.getRefWarehouseUUID());
			outboundItemUIModel.setRefWarehouseAreaUUID(outboundDelivery.getRefWarehouseAreaUUID());
		}
	}

	public void convWarehouseAreaToItemUI(WarehouseArea warehouseArea, OutboundItemUIModel outboundItemUIModel) {
		if (warehouseArea != null) {
			outboundItemUIModel.setRefWarehouseAreaId(warehouseArea.getId());
			outboundItemUIModel.setRefWarehouseAreaName(warehouseArea.getName());
		}
	}

}
