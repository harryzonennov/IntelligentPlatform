package com.company.IntelligentPlatform.logistics.service;

import java.util.List;
import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreItemLogUIModel;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItemLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.model.ServiceJSONRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
@Transactional
public class WarehouseStoreItemLogManager {

	public static final String METHOD_ConvWarehouseStoreItemLogToUI = "convWarehouseStoreItemLogToUI";

	public static final String METHOD_ConvDocumentToStoreItemLogUI = "convDocumentToStoreItemLogUI";

	public static final String METHOD_ConvMaterialStockKeepUnitToUI = "convMaterialStockKeepUnitToUI";

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected WarehouseStoreManager warehouseStoreManager;

	@Autowired
	protected WarehouseStoreItemManager warehouseStoreItemManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), WarehouseStoreItem.NODENAME,
						request.getUuid(), WarehouseStoreItemLog.NODENAME, warehouseStoreManager);
		docPageHeaderInputPara.setGenBaseModelList(
				(DocPageHeaderModelProxy.GenBaseModelList<WarehouseStoreItem>) warehouseStoreItem -> {
					// How to get the base page header model list
					return warehouseStoreItemManager.getPageHeaderModelList(new ServiceJSONRequest(warehouseStoreItem.getUuid(), warehouseStoreItem.getParentNodeUUID()), client);
				});
		docPageHeaderInputPara.setGenHomePageModel(
				(DocPageHeaderModelProxy.GenHomePageModel<WarehouseStoreItemLog>) (warehouseStoreItemLog, pageHeaderModel) -> {
					pageHeaderModel.setHeaderName(warehouseStoreItemLog.getRefMaterialSKUName());
					return pageHeaderModel;
				});
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}

	public void convWarehouseStoreItemLogToUI(
			WarehouseStoreItemLog warehouseStoreItemLog,
			WarehouseStoreItemLogUIModel warehouseStoreItemLogUIModel) throws ServiceEntityInstallationException, ServiceEntityConfigureException {
		convWarehouseStoreItemLogToUI(warehouseStoreItemLog, warehouseStoreItemLogUIModel, null);
	}

	public void convWarehouseStoreItemLogToUI(
			WarehouseStoreItemLog warehouseStoreItemLog,
			WarehouseStoreItemLogUIModel warehouseStoreItemLogUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		if (warehouseStoreItemLog != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(warehouseStoreItemLog, warehouseStoreItemLogUIModel);
			warehouseStoreItemLogUIModel.setDocumentType(warehouseStoreItemLog
					.getDocumentType());
			warehouseStoreItemLogUIModel.setDocumentUUID(warehouseStoreItemLog
					.getDocumentUUID());
			warehouseStoreItemLogUIModel.setRefUnitUUID(warehouseStoreItemLog
					.getRefUnitUUID());
			warehouseStoreItemLogUIModel.setRefUnitName(warehouseStoreItemLog
					.getRefUnitName());
			warehouseStoreItemLogUIModel.setUpdatedVolume(warehouseStoreItemLog
					.getUpdatedVolume());
			warehouseStoreItemLogUIModel.setUpdatedWeight(warehouseStoreItemLog
					.getUpdatedWeight());
			warehouseStoreItemLogUIModel.setUpdatedAmount(warehouseStoreItemLog
					.getUpdatedAmount());
			warehouseStoreItemLogUIModel
					.setUpdatedAmountLabel(warehouseStoreItemLog
							.getUpdatedAmount() + "");
			warehouseStoreItemLogUIModel.setAmount(warehouseStoreItemLog
					.getAmount());
			if (!ServiceEntityStringHelper
					.checkNullString(warehouseStoreItemLog.getRefUnitName())) {
				warehouseStoreItemLogUIModel
						.setAmountLabel(warehouseStoreItemLog.getAmount()
								+ warehouseStoreItemLog.getRefUnitName());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(warehouseStoreItemLog
							.getUpdatedRefUnitName())) {
				warehouseStoreItemLogUIModel
						.setUpdatedAmountLabel(warehouseStoreItemLog
								.getUpdatedAmount()
								+ warehouseStoreItemLog.getUpdatedRefUnitName());
			}
			warehouseStoreItemLogUIModel
					.setUpdatedRefUnitUUID(warehouseStoreItemLog
							.getUpdatedRefUnitUUID());
			warehouseStoreItemLogUIModel
					.setUpdatedRefUnitName(warehouseStoreItemLog
							.getUpdatedRefUnitName());
			warehouseStoreItemLogUIModel.setDeclaredValue(warehouseStoreItemLog
					.getDeclaredValue());
			warehouseStoreItemLogUIModel
					.setUpdatedDeclaredValue(warehouseStoreItemLog
							.getUpdatedDeclaredValue());
			warehouseStoreItemLogUIModel
					.setRefMaterialSKUUUID(warehouseStoreItemLog
							.getRefMaterialSKUUUID());
			if(logonInfo != null){
				String documentTypeValue = serviceDocumentComProxy
						.getDocumentTypeValue(warehouseStoreItemLog
								.getDocumentType(), logonInfo.getLanguageCode());
				warehouseStoreItemLogUIModel
						.setDocumentTypeValue(documentTypeValue);
			}
			StorageCoreUnit oldStoreUnit = new StorageCoreUnit(
					warehouseStoreItemLog.getRefMaterialSKUUUID(),
					warehouseStoreItemLog.getRefUnitUUID(),
					warehouseStoreItemLog.getAmount());
			StorageCoreUnit updatedStoreUnit = new StorageCoreUnit(
					warehouseStoreItemLog.getRefMaterialSKUUUID(),
					warehouseStoreItemLog.getUpdatedRefUnitUUID(),
					warehouseStoreItemLog.getUpdatedAmount());
			
			try {
				String updatedAmountLabel = materialStockKeepUnitManager
						.getAmountLabel(
								warehouseStoreItemLog.getRefMaterialSKUUUID(),
								warehouseStoreItemLog.getUpdatedRefUnitUUID(),
								warehouseStoreItemLog.getUpdatedAmount(),
								warehouseStoreItemLog.getClient());
				String amountLabel = materialStockKeepUnitManager
						.getAmountLabel(
								warehouseStoreItemLog.getRefMaterialSKUUUID(),
								warehouseStoreItemLog.getRefUnitUUID(),
								warehouseStoreItemLog.getAmount(),
								warehouseStoreItemLog.getClient());
				if (!ServiceEntityStringHelper
						.checkNullString(warehouseStoreItemLog
								.getUpdatedRefUnitUUID())) {
					warehouseStoreItemLogUIModel
							.setUpdatedAmountLabel(updatedAmountLabel);
				}
				if (!ServiceEntityStringHelper
						.checkNullString(warehouseStoreItemLog.getRefUnitUUID())) {
					warehouseStoreItemLogUIModel.setAmountLabel(amountLabel);
				}
			} catch (MaterialException e) {
				// just skip
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e,
						warehouseStoreItemLog.getUuid()));
			}
			try {
				StorageCoreUnit changeStoreUnit = materialStockKeepUnitManager
						.mergeStorageUnitCore(updatedStoreUnit, oldStoreUnit,
								StorageCoreUnit.OPERATOR_MINUS, warehouseStoreItemLog.getClient());
				warehouseStoreItemLogUIModel.setChangeAmount(changeStoreUnit.getAmount());
				warehouseStoreItemLogUIModel.setChangeRefUnitUUID(changeStoreUnit.getRefUnitUUID());
				String changeAmountLabel = materialStockKeepUnitManager
						.getAmountLabel(
								warehouseStoreItemLog.getRefMaterialSKUUUID(),
								changeStoreUnit.getRefUnitUUID(),
								changeStoreUnit.getAmount(),
								warehouseStoreItemLog.getClient());
				warehouseStoreItemLogUIModel.setChangeAmountLabel(changeAmountLabel);
			} catch (MaterialException ex) {
				// just skip
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(ex,
						warehouseStoreItemLog.getUuid()));
			}
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 * @throws ServiceEntityConfigureException
	 */
	public void convDocumentToStoreItemLogUI(
			ServiceEntityNode serviceEntityNode,
			WarehouseStoreItemLogUIModel warehouseStoreItemLogUIModel)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		if (serviceEntityNode != null) {
			warehouseStoreItemLogUIModel.setDocumentId(serviceEntityNode
					.getId());
			warehouseStoreItemLogUIModel.setDocumentName(serviceEntityNode
					.getName());
		}
	}

	public void convWarehouseToUI(Warehouse warehouse,
			WarehouseStoreItemLogUIModel warehouseStoreItemLogUIModel) {
		if (warehouse != null) {
			warehouseStoreItemLogUIModel.setWarehouseName(warehouse.getName());
			warehouseStoreItemLogUIModel.setWarehouseId(warehouse.getId());
		}
	}

	public void convUIToWarehouse(Warehouse rawEntity,
			WarehouseStoreItemLogUIModel warehouseStoreItemLogUIModel) {
		rawEntity.setName(warehouseStoreItemLogUIModel.getWarehouseName());
		rawEntity.setId(warehouseStoreItemLogUIModel.getWarehouseId());
	}

	public void convMaterialStockKeepUnitToUI(
			MaterialStockKeepUnit materialStockKeepUnit,
			WarehouseStoreItemLogUIModel warehouseStoreItemLogUIModel) {
		if (materialStockKeepUnit != null) {
			warehouseStoreItemLogUIModel
					.setRefMaterialSKUName(materialStockKeepUnit.getName());
			warehouseStoreItemLogUIModel
					.setRefMaterialSKUId(materialStockKeepUnit.getId());
		}
	}

}
