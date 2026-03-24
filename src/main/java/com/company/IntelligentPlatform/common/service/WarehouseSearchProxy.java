package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.WarehouseAreaSearchModel;
import com.company.IntelligentPlatform.common.dto.WarehouseSearchModel;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.Organization;

import java.util.List;
import java.util.Map;

@Service
public class WarehouseSearchProxy extends ServiceSearchProxy{

	@Autowired
	protected WarehouseManager warehouseManager;

	@Override
	public Class<?> getDocSearchModelCls() {
		return WarehouseSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return WarehouseAreaSearchModel.class;
	}

	@Override
	public String getAuthorizationResource() {
		return warehouseManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return null;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList =
				SearchModelConfigHelper.buildParentChildConfigure(Warehouse.class,
						WarehouseArea.class);
		// Search node:[Warehouse->Employee]
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(Employee.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID("mainContactUUID").baseNodeInstId(Warehouse.SENAME).build());
		// Search node:[Warehouse->Organization]
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(Organization.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID(Organization.PARENT_ORG_UUID).baseNodeInstId(Warehouse.SENAME).build());
		return searchNodeConfigList;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicItemSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		return SearchModelConfigHelper.buildChildParentConfigure(WarehouseArea.class,
						Warehouse.class);
	}

}
