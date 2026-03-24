package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.OutboundItemRequestJSONModel;
import com.company.IntelligentPlatform.logistics.dto.WasteProcessMaterialItemUIModel;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryWarehouseItemManager;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.logistics.model.WasteProcessMaterialItem;
import com.company.IntelligentPlatform.logistics.model.WasteProcessOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.logistics.model.StoreAvailableStoreItemRequest;
import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreCheckProxy;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.util.List;
import java.util.Map;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class WasteProcessMaterialItemManager {

	public static final String METHOD_ConvWasteProcessMaterialItemToUI = "convWasteProcessMaterialItemToUI";

	public static final String METHOD_ConvUIToWasteProcessMaterialItem = "convUIToWasteProcessMaterialItem";

	public static final String METHOD_ConvParentDocToItemUI = "convParentDocToItemUI";

	@Autowired
	protected WasteProcessOrderManager wasteProcessOrderManager;

	@Autowired
	protected WarehouseStoreManager warehouseStoreManager;

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	@Autowired
	protected WarehouseStoreCheckProxy warehouseStoreCheckProxy;

	@Autowired
	protected OutboundDeliveryWarehouseItemManager outboundDeliveryWarehouseItemManager;

	protected Logger logger = LoggerFactory.getLogger(WasteProcessMaterialItemManager.class);

	public void convWasteProcessMaterialItemToUI(
			WasteProcessMaterialItem wasteProcessMaterialItem,
			WasteProcessMaterialItemUIModel wasteProcessMaterialItemUIModel)
			throws ServiceEntityConfigureException {
		convWasteProcessMaterialItemToUI(wasteProcessMaterialItem, wasteProcessMaterialItemUIModel, null);
	}

	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), WasteProcessOrder.NODENAME,
						request.getUuid(), WasteProcessMaterialItem.NODENAME, wasteProcessOrderManager);
		docPageHeaderInputPara.setGenBaseModelList((DocPageHeaderModelProxy.GenBaseModelList<WasteProcessOrder>) wasteProcessOrder -> {
            // How to get the base page header model list
			return docPageHeaderModelProxy.getDocPageHeaderModelList(wasteProcessOrder, null);
        });
		docPageHeaderInputPara.setGenHomePageModel((DocPageHeaderModelProxy.GenHomePageModel<WasteProcessMaterialItem>) (wasteProcessMaterialItem, pageHeaderModel) ->
				docPageHeaderModelProxy.getDefDocMaterialItemPageHeaderModel(wasteProcessMaterialItem, pageHeaderModel));
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityConfigureException
	 */
	public void convWasteProcessMaterialItemToUI(
			WasteProcessMaterialItem wasteProcessMaterialItem,
			WasteProcessMaterialItemUIModel wasteProcessMaterialItemUIModel, LogonInfo logonInfo)
			throws ServiceEntityConfigureException {
		if (wasteProcessMaterialItem != null) {
			docFlowProxy.convDocMatItemToUI(wasteProcessMaterialItem, wasteProcessMaterialItemUIModel, logonInfo);
			wasteProcessMaterialItemUIModel
					.setRefUnitUUID(wasteProcessMaterialItem.getRefUnitUUID());
			wasteProcessMaterialItemUIModel
					.setItemStatus(wasteProcessMaterialItem.getItemStatus());
			if(logonInfo != null){
				try {
					Map<Integer, String> statusMap = wasteProcessOrderManager.initStatus(logonInfo.getLanguageCode());
					wasteProcessMaterialItemUIModel
							.setItemStatusValue(statusMap
									.get(wasteProcessMaterialItem.getItemStatus()));
				} catch (ServiceEntityInstallationException e) {
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "convWasteProcessMaterialItemToUI"));
				}
			}
			wasteProcessMaterialItemUIModel.setId(wasteProcessMaterialItem
					.getId());

			wasteProcessMaterialItemUIModel
					.setAmount(wasteProcessMaterialItem.getAmount());
			wasteProcessMaterialItemUIModel.setStoreCheckStatus(wasteProcessMaterialItem.getStoreCheckStatus());
			if(logonInfo != null){
				Map<Integer, String> storeCheckStatusMap = null;
				try {
					storeCheckStatusMap = warehouseStoreCheckProxy.getCheckStatusMap(logonInfo.getLanguageCode());
					wasteProcessMaterialItemUIModel.setStoreCheckStatusValue(storeCheckStatusMap.get(wasteProcessMaterialItem.getStoreCheckStatus()));
				} catch (ServiceEntityInstallationException e) {
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "storeCheckStatus"));
				}
			}
		}
	}

	public void convParentDocToItemUI(WasteProcessOrder wasteProcessOrder,
									  WasteProcessMaterialItemUIModel wasteProcessMaterialItemUIModel){
		convParentDocToItemUI(wasteProcessOrder, wasteProcessMaterialItemUIModel, null);
	}

	public void convParentDocToItemUI(WasteProcessOrder wasteProcessOrder,
									  WasteProcessMaterialItemUIModel wasteProcessMaterialItemUIModel, LogonInfo logonInfo){
		docFlowProxy.convParentDocToItemUI(wasteProcessOrder, wasteProcessMaterialItemUIModel, logonInfo);
	}

	public void checkWasteAmountRequest(
			OutboundItemRequestJSONModel request, String client) throws ServiceEntityConfigureException,
			MaterialException, WasteProcessOrderException {
		WasteProcessMaterialItem wasteProcessMaterialItem = (WasteProcessMaterialItem) wasteProcessOrderManager
				.getEntityNodeByKey(request.getUuid(),
						IServiceEntityNodeFieldConstant.UUID,
						WasteProcessMaterialItem.NODENAME, client,null);
		WarehouseStoreItem warehouseStoreItem =
				(WarehouseStoreItem) warehouseStoreManager.getEntityNodeByKey(wasteProcessMaterialItem.getRefStoreItemUUID(),
						IServiceEntityNodeFieldConstant.UUID, WarehouseStoreItem.NODENAME,
						wasteProcessMaterialItem.getClient(), null);
		// in case no binding contract item
		if (warehouseStoreItem == null) {
			return ;
		}
		StorageCoreUnit availableStoreUnit = outboundDeliveryWarehouseItemManager
				.getAvailableStoreItemAmountUnion(new StoreAvailableStoreItemRequest(
						warehouseStoreItem, null, true, wasteProcessMaterialItem.getUuid()));
		if (availableStoreUnit.getAmount() == 0) {
			throw new WasteProcessOrderException(WasteProcessOrderException.PARA_ERROR_STOCKSHOT, 0);
		}
		String amountString = request.getAmount();
		Double amountDouble = new Double(amountString);
		StorageCoreUnit requestUnit = new StorageCoreUnit(wasteProcessMaterialItem.getRefMaterialSKUUUID(),
				request.getRefUnitUUID(), amountDouble);

		StorageCoreUnit compareResult = materialStockKeepUnitManager.mergeStorageUnitCore(requestUnit,
				availableStoreUnit,
				StorageCoreUnit.OPERATOR_MINUS, wasteProcessMaterialItem.getClient());
		if (compareResult.getAmount() > 0) {
			String wasteAmountLabel =
					materialStockKeepUnitManager.getAmountLabel(wasteProcessMaterialItem.getRefMaterialSKUUUID()
							, wasteProcessMaterialItem.getRefUnitUUID(), wasteProcessMaterialItem.getAmount(),
							wasteProcessMaterialItem.getClient());
			throw new WasteProcessOrderException(WasteProcessOrderException.PARA_ERROR_STOCKSHOT, wasteAmountLabel);
		}
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:wasteProcessMaterialItem
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToWasteProcessMaterialItem(
			WasteProcessMaterialItemUIModel wasteProcessMaterialItemUIModel,
			WasteProcessMaterialItem rawEntity) {
		docFlowProxy.convUIToDocMatItem(wasteProcessMaterialItemUIModel, rawEntity);
		if (!ServiceEntityStringHelper
				.checkNullString(wasteProcessMaterialItemUIModel.getId())) {
			rawEntity.setId(wasteProcessMaterialItemUIModel.getId());
		}
		if(wasteProcessMaterialItemUIModel.getItemStatus() > 0){
			rawEntity.setItemStatus(wasteProcessMaterialItemUIModel
					.getItemStatus());
		}
	}


}
