package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.WarehouseSafetyWarnMessageUIModel;
import com.company.IntelligentPlatform.common.dto.WarehouseStoreSettingUIModel;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.model.MaterialSKUUnitReference;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseSafetyWarn;
import com.company.IntelligentPlatform.common.model.WarehouseStoreSetting;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigureResourceManager;
import com.company.IntelligentPlatform.common.service.ServiceSimpleDataProviderException;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
@Transactional
public class WarehouseSafetyWarnManager {

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;
	
	@Autowired
	protected WarehouseManager warehouseManager;
	
	@Autowired
	protected LogonUserManager logonUserManager;
	
	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;
	
	@Autowired
	protected ServiceDocConfigureResourceManager serviceDocConfigureResourceManager;
	
	/**
	 * Get the Warehouse safety warn message UI Model list by raw safety warn message list.
	 * @param rawList
	 * @return
	 * @throws ServiceEntityInstallationException
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 * @throws ServiceDocConfigureException
	 * @throws ServiceSimpleDataProviderException
	 * @throws IOException
	 */
	public List<WarehouseSafetyWarnMessageUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList, LogonInfo logonInfo)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, MaterialException, ServiceDocConfigureException, ServiceSimpleDataProviderException, IOException {
		List<WarehouseSafetyWarnMessageUIModel> warehouseStoreSettingList = new ArrayList<WarehouseSafetyWarnMessageUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			WarehouseSafetyWarnMessageUIModel warehouseSafetyWarnMessageUIModel = new WarehouseSafetyWarnMessageUIModel();
			WarehouseSafetyWarn warehouseSafetyWarn = (WarehouseSafetyWarn) rawNode;
			convStoreSettingToMessageUI(
					warehouseSafetyWarn, warehouseSafetyWarnMessageUIModel);
			Warehouse warehouse = (Warehouse) warehouseManager
					.getEntityNodeByKey(
							warehouseSafetyWarn.getRootNodeUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							Warehouse.NODENAME, null);
			convWarehouseToUI(warehouse, warehouseSafetyWarnMessageUIModel);
			MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
					.getEntityNodeByKey(
							warehouseSafetyWarn.getRefMaterialSKUUUID(),
							"uuid", MaterialStockKeepUnit.NODENAME, null);
			if(warehouse != null){
				LogonUser logonUser = (LogonUser) logonUserManager.getEntityNodeByKey(
						warehouseSafetyWarn.getResEmployeeUUID(),
						"uuid", LogonUser.NODENAME, null);
				convLogonUserToUI(logonUser, warehouseSafetyWarnMessageUIModel);
			}
			if (materialStockKeepUnit != null) {
				List<ServiceEntityNode> materialSKUUnitList = materialStockKeepUnitManager
						.getEntityNodeListByKey(
								materialStockKeepUnit.getUuid(),
								IServiceEntityNodeFieldConstant.ROOTNODEUUID,
								MaterialSKUUnitReference.NODENAME, materialStockKeepUnit.getClient(), null);
				Map<String, String> materialUnitMap = materialStockKeepUnitManager
						.getAllUnitMapFromSKU(materialStockKeepUnit,
								materialSKUUnitList);
				convMaterialStockKeepUnitToUI(
						materialStockKeepUnit,
						warehouseSafetyWarnMessageUIModel);
				if(warehouseSafetyWarn.getSafeStoreCalculateCategory() == WarehouseStoreSetting.STORE_CATE_RATIO){
					if(warehouseSafetyWarn.getDataSourceType() == WarehouseStoreSetting.DATASOURCE_TYPE_MANUALSET){
						double maxSafeStoreAmount = warehouseSafetyWarn
								.getMaxStoreRatio()
								/ 100
								* warehouseSafetyWarn.getTargetAverageStoreAmount();
						double minSafeStoreAmount = warehouseSafetyWarn
								.getMinStoreRatio()
								/ 100
								* warehouseSafetyWarn.getTargetAverageStoreAmount(); 
						String maxSafeStoreAmountStr = maxSafeStoreAmount
								+ materialUnitMap.get(warehouseSafetyWarn
										.getTargetAverageStoreUnitUUID());
						warehouseSafetyWarnMessageUIModel
								.setMaxSafeStoreAmount(maxSafeStoreAmountStr);
						String minSafeStoreAmountStr = minSafeStoreAmount
								+ materialUnitMap.get(warehouseSafetyWarn
										.getTargetAverageStoreUnitUUID());
						warehouseSafetyWarnMessageUIModel
								.setMinSafeStoreAmount(minSafeStoreAmountStr);
					}else{
						// In case need to process the data source
						WarehouseStoreSettingUIModel warehouseStoreSettingUIModel = new WarehouseStoreSettingUIModel();
						warehouseManager.convWarehouseStoreSettingToUI(warehouseSafetyWarn,
								warehouseStoreSettingUIModel);
						serviceDocConfigureResourceManager.enterProcessForDocMaterial(
								warehouseStoreSettingUIModel,
								warehouseSafetyWarn.getRefUUID(),
								logonInfo,
								warehouseStoreSettingUIModel.getRefMaterialSKUUUID());
						double amount = warehouseStoreSettingUIModel
								.getTargetAverageStoreAmount();
						double maxSafeStoreAmount = warehouseSafetyWarn
								.getMaxStoreRatio()
								/ 100
								* amount;
						double minSafeStoreAmount = warehouseSafetyWarn
								.getMinStoreRatio()
								/ 100
								* amount; 
						String maxSafeStoreAmountStr = maxSafeStoreAmount
								+ materialUnitMap.get(warehouseStoreSettingUIModel
										.getTargetAverageStoreUnitUUID());
						warehouseSafetyWarnMessageUIModel
								.setMaxSafeStoreAmount(maxSafeStoreAmountStr);
						String minSafeStoreAmountStr = minSafeStoreAmount
								+ materialUnitMap.get(warehouseStoreSettingUIModel
										.getTargetAverageStoreUnitUUID());
						warehouseSafetyWarnMessageUIModel
								.setMinSafeStoreAmount(minSafeStoreAmountStr);
					}
				}else{
					String maxSafeStoreAmount = warehouseSafetyWarn
							.getMaxSafeStoreAmount()
							+ materialUnitMap.get(warehouseSafetyWarn
									.getMaxSafeStoreUnitUUID());
					warehouseSafetyWarnMessageUIModel
							.setMaxSafeStoreAmount(maxSafeStoreAmount);
					String minSafeStoreAmount = warehouseSafetyWarn
							.getMinSafeStoreAmount()
							+ materialUnitMap.get(warehouseSafetyWarn
									.getMinSafeStoreUnitUUID());
					warehouseSafetyWarnMessageUIModel
							.setMinSafeStoreAmount(minSafeStoreAmount);
				}
				// TODO get summary storage
				StorageCoreUnit storageCoreUnit = new StorageCoreUnit();
				String actualUnitName = materialUnitMap.get(storageCoreUnit
						.getRefUnitUUID());
				String actualStoreAmount = storageCoreUnit.getAmount() + "";
				if (actualUnitName != null
						&& !actualUnitName
								.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
					actualStoreAmount = actualStoreAmount + actualUnitName;
				}
				warehouseSafetyWarnMessageUIModel
						.setActualStoreAmount(actualStoreAmount);
			}
			warehouseStoreSettingList.add(warehouseSafetyWarnMessageUIModel);
		}
		return warehouseStoreSettingList;
	}


	public void convSafetyWarnToMessageUI(
			WarehouseSafetyWarn warehouseSafetyWarn,
			WarehouseSafetyWarnMessageUIModel warehouseSafetyWarnMessageUIModel)
			throws ServiceEntityInstallationException {
		if (warehouseSafetyWarn != null) {
			warehouseSafetyWarnMessageUIModel.setBaseUUID(warehouseSafetyWarn
					.getParentNodeUUID());
			warehouseSafetyWarnMessageUIModel.setUuid(warehouseSafetyWarn
					.getUuid());
			warehouseSafetyWarnMessageUIModel
					.setRefMaterialSKUUUID(warehouseSafetyWarn
							.getRefMaterialSKUUUID());
			warehouseSafetyWarnMessageUIModel
					.setMaxSafeStoreUnitUUID(warehouseSafetyWarn
							.getMaxSafeStoreUnitUUID());
			warehouseSafetyWarnMessageUIModel
					.setMinSafeStoreUnitUUID(warehouseSafetyWarn
							.getMinSafeStoreUnitUUID());
			if (warehouseSafetyWarn.getSafeStoreCalculateCategory() == WarehouseStoreSetting.STORE_CATE_RATIO) {
				warehouseSafetyWarnMessageUIModel
						.setMaxSafeStoreUnitUUID(warehouseSafetyWarn
								.getTargetAverageStoreUnitUUID());
				warehouseSafetyWarnMessageUIModel
						.setMinSafeStoreUnitUUID(warehouseSafetyWarn
								.getTargetAverageStoreUnitUUID());
			}

			warehouseSafetyWarnMessageUIModel
					.setErrorTypeValue(getErrorTypeValue(warehouseSafetyWarn
							.getErrorType()));
			warehouseSafetyWarnMessageUIModel.setErrorType(warehouseSafetyWarn
					.getErrorType());
			warehouseSafetyWarnMessageUIModel.setGapAmount(warehouseSafetyWarn
					.getGapAmount());
			warehouseSafetyWarnMessageUIModel
					.setGapUnitUUID(warehouseSafetyWarn.getGapUnitUUID());
		}
	}

	public String getErrorTypeValue(int errorType)
			throws ServiceEntityInstallationException {
		Map<Integer, String> errorTypeMap = serviceDropdownListHelper
				.getUIDropDownMap(WarehouseSafetyWarnMessageUIModel.class,
						"errorType");
		return errorTypeMap.get(errorType);
	}

	public void convStoreSettingToSafetyWarn(
			WarehouseStoreSetting warehouseStoreSetting,
			WarehouseSafetyWarn warehouseSafetyWarn) {
		if (warehouseStoreSetting != null && warehouseSafetyWarn != null) {
			warehouseSafetyWarn.setUuid(warehouseStoreSetting.getUuid());
			warehouseSafetyWarn.setParentNodeUUID(warehouseStoreSetting
					.getParentNodeUUID());
			warehouseSafetyWarn.setRootNodeUUID(warehouseStoreSetting
					.getRootNodeUUID());
			warehouseSafetyWarn.setClient(warehouseStoreSetting.getClient());
			warehouseSafetyWarn.setServiceEntityName(warehouseStoreSetting
					.getServiceEntityName());
			warehouseSafetyWarn
					.setNodeName(warehouseStoreSetting.getNodeName());
			warehouseSafetyWarn.setRefMaterialSKUUUID(warehouseStoreSetting
					.getRefMaterialSKUUUID());
			warehouseSafetyWarn.setUuid(warehouseStoreSetting.getUuid());

			warehouseSafetyWarn.setMaxSafeStoreAmount(warehouseStoreSetting
					.getMaxSafeStoreAmount());
			warehouseSafetyWarn.setMaxSafeStoreUnitUUID(warehouseStoreSetting
					.getMaxSafeStoreUnitUUID());
			warehouseSafetyWarn.setMaxStoreRatio(warehouseStoreSetting
					.getMaxStoreRatio());

			warehouseSafetyWarn.setMinSafeStoreUnitUUID(warehouseStoreSetting
					.getMinSafeStoreUnitUUID());
			warehouseSafetyWarn.setMinSafeStoreAmount(warehouseStoreSetting
					.getMinSafeStoreAmount());
			warehouseSafetyWarn.setMinStoreRatio(warehouseStoreSetting
					.getMinStoreRatio());			
			warehouseSafetyWarn
					.setSafeStoreCalculateCategory(warehouseStoreSetting
							.getSafeStoreCalculateCategory());
			warehouseSafetyWarn
					.setTargetAverageStoreUnitUUID(warehouseStoreSetting
							.getTargetAverageStoreUnitUUID());
			warehouseSafetyWarn
					.setTargetAverageStoreAmount(warehouseStoreSetting
							.getTargetAverageStoreAmount());
			warehouseSafetyWarn.setErrorType(warehouseStoreSetting
					.getErrorType());
			warehouseSafetyWarn.setRefUUID(warehouseStoreSetting.getRefUUID());
			warehouseSafetyWarn.setDataSourceType(warehouseStoreSetting
					.getDataSourceType());
		}
	}

	public void convStoreSettingToMessageUI(
			WarehouseStoreSetting warehouseStoreSetting,
			WarehouseSafetyWarnMessageUIModel warehouseSafetyWarnMessageUIModel)
			throws ServiceEntityInstallationException {
		if (warehouseStoreSetting != null) {
			warehouseSafetyWarnMessageUIModel.setBaseUUID(warehouseStoreSetting
					.getParentNodeUUID());
			warehouseSafetyWarnMessageUIModel.setUuid(warehouseStoreSetting
					.getUuid());
			warehouseSafetyWarnMessageUIModel
					.setRefMaterialSKUUUID(warehouseStoreSetting
							.getRefMaterialSKUUUID());
			warehouseSafetyWarnMessageUIModel
					.setMaxSafeStoreUnitUUID(warehouseStoreSetting
							.getMaxSafeStoreUnitUUID());
			warehouseSafetyWarnMessageUIModel
					.setMinSafeStoreUnitUUID(warehouseStoreSetting
							.getMinSafeStoreUnitUUID());
			if (warehouseStoreSetting.getSafeStoreCalculateCategory() == WarehouseStoreSetting.STORE_CATE_RATIO) {
				warehouseSafetyWarnMessageUIModel
						.setMaxSafeStoreUnitUUID(warehouseStoreSetting
								.getTargetAverageStoreUnitUUID());
				warehouseSafetyWarnMessageUIModel
						.setMinSafeStoreUnitUUID(warehouseStoreSetting
								.getTargetAverageStoreUnitUUID());
			}
			Map<Integer, String> errorTypeMap = serviceDropdownListHelper
					.getUIDropDownMap(WarehouseSafetyWarnMessageUIModel.class,
							"errorType");
			warehouseSafetyWarnMessageUIModel.setErrorTypeValue(errorTypeMap
					.get(warehouseStoreSetting.getErrorType()));
			warehouseSafetyWarnMessageUIModel
					.setErrorType(warehouseStoreSetting.getErrorType());
		}
	}

	public void convWarehouseToUI(Warehouse warehouse,
			WarehouseSafetyWarnMessageUIModel warehouseSafetyWarnMessageUIModel) {
		if (warehouse != null) {
			warehouseSafetyWarnMessageUIModel.setWarehouseId(warehouse.getId());
			warehouseSafetyWarnMessageUIModel.setWarehouseName(warehouse
					.getName());
		}
	}

	public void convUIToWarehouse(Warehouse rawEntity,
			WarehouseSafetyWarnMessageUIModel warehouseSafetyWarnMessageUIModel) {
		rawEntity.setId(warehouseSafetyWarnMessageUIModel.getWarehouseId());
		rawEntity.setName(warehouseSafetyWarnMessageUIModel.getWarehouseName());
	}

	public void convMaterialStockKeepUnitToUI(
			MaterialStockKeepUnit materialStockKeepUnit,
			WarehouseSafetyWarnMessageUIModel warehouseSafetyWarnMessageUIModel)
			throws ServiceEntityInstallationException {
		if (materialStockKeepUnit != null) {
			warehouseSafetyWarnMessageUIModel
					.setRefMaterialSKUID(materialStockKeepUnit.getId());
			warehouseSafetyWarnMessageUIModel
					.setRefMaterialSKUName(materialStockKeepUnit.getName());
		}
	}

	public void convLogonUserToUI(LogonUser logonUser,
			WarehouseSafetyWarnMessageUIModel warehouseSafetyWarnMessageUIModel) {
		if (logonUser != null) {
			warehouseSafetyWarnMessageUIModel.setResUserName(logonUser
					.getName());
			warehouseSafetyWarnMessageUIModel.setResUserID(logonUser.getId());
		}
	}

}
