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

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.service.WarehouseSafetyWarnManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseStoreSetting;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchNodeMapping;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "warehouseSafetyWarnMessageListController")
@RequestMapping(value = "/warehouseSafetyWarnMessage")
public class WarehouseSafetyWarnMessageListController extends SEListController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_WAREHOUSESTORE;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected WarehouseSafetyWarnManager warehouseSafetyWarnManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected LogonUserManager logonUserManager;

	@Autowired
	protected BsearchService bsearchService;	
	
	protected List<ServiceEntityNode> warehouseSafetyWarnMessageList = new ArrayList<>();



	protected String getPreWarnMsg(String key) throws IOException {
		Locale locale = ServiceLanHelper.getDefault();
		String path = WarehouseSafetyWarnMessageUIModel.class.getResource("")
				.getPath();
		String resFileName = WarehouseStoreSetting.NODENAME;
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap.get(key);
	}

	protected String getListViewName(int uiFlag) {
		if (uiFlag == UIFLAG_STANDARD) {
			return "WarehouseStoreSettingList";
		}
		if (uiFlag == UIFLAG_CHOOSER) {
			return "WarehouseStoreSettingChooser";
		}
		return "WarehouseStoreSettingList";
	}
	
	
	public List<ServiceEntityNode> getWarehouseSafetyWarnMessageList() {
		return warehouseSafetyWarnMessageList;
	}

	public void setWarehouseSafetyWarnMessageList(
			List<ServiceEntityNode> warehouseSafetyWarnMessageList) {
		this.warehouseSafetyWarnMessageList = warehouseSafetyWarnMessageList;
	}

	/**
	 * Process of register the warehouse safety message to buffer
	 * @param rawList
	 */
	public void registerWarehouseSafetyMessage(List<ServiceEntityNode> rawList){
		setWarehouseSafetyWarnMessageList(rawList);
	}



	protected List<ServiceEntityNode> searchInternal(
			WarehouseSafetyWarnMessageSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[warehouseStoreSetting]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(WarehouseStoreSetting.SENAME);
		searchNodeConfig0.setNodeName(WarehouseStoreSetting.NODENAME);
		searchNodeConfig0.setNodeInstID(WarehouseStoreSetting.NODENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[warehouse]
		BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
		searchNodeConfig1.setSeName(Warehouse.SENAME);
		searchNodeConfig1.setNodeName(Warehouse.NODENAME);
		searchNodeConfig1.setNodeInstID(Warehouse.SENAME);
		searchNodeConfig1.setStartNodeFlag(false);
		searchNodeConfig1
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_CHILD);
		searchNodeConfigList.add(searchNodeConfig1);
		// Search node:[materialStockKeepUnit]
		BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
		searchNodeConfig2.setSeName(MaterialStockKeepUnit.SENAME);
		searchNodeConfig2.setNodeName(MaterialStockKeepUnit.NODENAME);
		searchNodeConfig2.setNodeInstID(MaterialStockKeepUnit.SENAME);
		searchNodeConfig2.setStartNodeFlag(false);
		searchNodeConfig2
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig2.setMapBaseFieldName("refMaterialSKUUUID");
		searchNodeConfig2.setMapSourceFieldName("uuid");
		searchNodeConfig2.setBaseNodeInstID(WarehouseStoreSetting.NODENAME);
		searchNodeConfigList.add(searchNodeConfig2);
		// Search node:[logonUser]
		BSearchNodeComConfigure searchNodeConfig3 = new BSearchNodeComConfigure();
		searchNodeConfig3.setSeName(LogonUser.SENAME);
		searchNodeConfig3.setNodeName(LogonUser.NODENAME);
		searchNodeConfig3.setNodeInstID(LogonUser.SENAME);
		searchNodeConfig3.setStartNodeFlag(false);
		searchNodeConfig3
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig3.setMapBaseFieldName("resEmployeeUUID");
		searchNodeConfig3.setMapSourceFieldName("uuid");
		searchNodeConfig3.setBaseNodeInstID(Warehouse.SENAME);
		searchNodeConfigList.add(searchNodeConfig3);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}


}
