package com.company.IntelligentPlatform.common.dto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.MaterialSKUUnitReference;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.WarehouseStoreSetting;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.INavigationElementConstants;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "warehouseStoreSettingListController")
@RequestMapping(value = "/warehouseStoreSetting")
public class WarehouseStoreSettingListController extends SEListController {

	public static final String AOID_RESOURCE = WarehouseEditorController.AOID_RESOURCE;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	public List<WarehouseStoreSettingUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<WarehouseStoreSettingUIModel> warehouseStoreSettingList = new ArrayList<WarehouseStoreSettingUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			WarehouseStoreSettingUIModel warehouseStoreSettingUIModel = new WarehouseStoreSettingUIModel();
			WarehouseStoreSetting warehouseStoreSetting = (WarehouseStoreSetting) rawNode;
			convToStoreSettingComUIModel(warehouseStoreSetting,
					warehouseStoreSettingUIModel);
			warehouseStoreSettingList.add(warehouseStoreSettingUIModel);
		}
		return warehouseStoreSettingList;
	}

	public void convToStoreSettingComUIModel(
			WarehouseStoreSetting warehouseStoreSetting,
			WarehouseStoreSettingUIModel warehouseStoreSettingUIModel)
			throws ServiceEntityConfigureException, ServiceEntityInstallationException {
		warehouseManager.convWarehouseStoreSettingToUI(warehouseStoreSetting,
				warehouseStoreSettingUIModel);
		if (warehouseStoreSetting.getSafeStoreCalculateCategory() == WarehouseStoreSetting.STORE_CATE_RATIO) {
			double maxSafeStoreAmount = warehouseStoreSetting
					.getMaxStoreRatio()
					/ 100
					* warehouseStoreSetting.getTargetAverageStoreAmount();
			double minSafeStoreAmount = warehouseStoreSetting
					.getMinStoreRatio()
					/ 100
					* warehouseStoreSetting.getTargetAverageStoreAmount();
			warehouseStoreSettingUIModel
					.setMaxSafeStoreAmount(maxSafeStoreAmount);
			warehouseStoreSettingUIModel
					.setMinSafeStoreAmount(minSafeStoreAmount);
			warehouseStoreSettingUIModel
					.setMaxSafeStoreUnitUUID(warehouseStoreSetting
							.getTargetAverageStoreUnitUUID());
			warehouseStoreSettingUIModel
					.setMinSafeStoreUnitUUID(warehouseStoreSetting
							.getTargetAverageStoreUnitUUID());
		}
		MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
				.getEntityNodeByKey(
						warehouseStoreSetting.getRefMaterialSKUUUID(), "uuid",
						MaterialStockKeepUnit.NODENAME, null);
		if(materialStockKeepUnit != null){
			List<ServiceEntityNode> materialSKUUnitList = materialStockKeepUnitManager
					.getEntityNodeListByKey(materialStockKeepUnit.getUuid(),
							IServiceEntityNodeFieldConstant.ROOTNODEUUID,
							MaterialSKUUnitReference.NODENAME, materialStockKeepUnit.getClient(), null);
			Map<String, String> materialUnitMap = materialStockKeepUnitManager
					.getAllUnitMapFromSKU(materialStockKeepUnit,
							materialSKUUnitList);
			if(warehouseStoreSetting.getSafeStoreCalculateCategory() == WarehouseStoreSetting.STORE_CATE_RATIO){
				warehouseStoreSettingUIModel.setMaxSafeStoreUnitName(materialUnitMap
						.get(warehouseStoreSettingUIModel.getMaxSafeStoreUnitUUID()));
				warehouseStoreSettingUIModel.setMinSafeStoreUnitName(materialUnitMap
						.get(warehouseStoreSettingUIModel.getMinSafeStoreUnitUUID()));
			}else{
				warehouseStoreSettingUIModel.setMaxSafeStoreUnitName(materialUnitMap
						.get(warehouseStoreSetting.getMaxSafeStoreUnitUUID()));
				warehouseStoreSettingUIModel.setMinSafeStoreUnitName(materialUnitMap
						.get(warehouseStoreSetting.getMinSafeStoreUnitUUID()));
			}
			
			warehouseManager.convMaterialSKUToSettingUI(materialStockKeepUnit,
					warehouseStoreSettingUIModel);
		}

	}

	
	

}
