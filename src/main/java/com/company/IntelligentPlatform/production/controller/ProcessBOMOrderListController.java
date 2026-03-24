package com.company.IntelligentPlatform.production.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.company.IntelligentPlatform.production.dto.ProcessBOMOrderSearchModel;
import com.company.IntelligentPlatform.production.dto.ProcessBOMOrderUIModel;
import com.company.IntelligentPlatform.production.service.BillOfMaterialOrderManager;
import com.company.IntelligentPlatform.production.service.ProcessBOMOrderManager;
import com.company.IntelligentPlatform.production.service.ProcessRouteOrderManager;
import com.company.IntelligentPlatform.production.model.BillOfMaterialOrder;
import com.company.IntelligentPlatform.production.model.ProcessBOMOrder;
import com.company.IntelligentPlatform.production.model.ProcessRouteOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;

import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.StandardKeyFlagProxy;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchNodeMapping;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "processBOMOrderListController")
@RequestMapping(value = "/processBOMOrder")
public class ProcessBOMOrderListController extends SEListController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_PROD_WORKCENTER;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected ProcessBOMOrderManager processBOMOrderManager;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ProcessRouteOrderManager processRouteOrderManager;
	
	@Autowired
	protected StandardKeyFlagProxy standardKeyFlagProxy;

	protected String getPreWarnMsg(String key, Map<String, String> preWarnMap) {
		return preWarnMap.get(key);
	}

	protected Map<String, String> getPreWarnMap() throws IOException {
		Locale locale = Locale.getDefault();
		String path = ProcessBOMOrderUIModel.class.getResource("").getPath();
		String resFileName = ProcessBOMOrder.SENAME;
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap;
	}

	protected List<ProcessBOMOrderUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<ProcessBOMOrderUIModel> processBOMOrderList = new ArrayList<ProcessBOMOrderUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			ProcessBOMOrderUIModel processBOMOrderUIModel = new ProcessBOMOrderUIModel();
			ProcessBOMOrder processBOMOrder = (ProcessBOMOrder) rawNode;
			processBOMOrderManager.convProcessBOMOrderToUI(processBOMOrder,
					processBOMOrderUIModel);
			BillOfMaterialOrder billOfMaterialOrder = (BillOfMaterialOrder) billOfMaterialOrderManager
					.getEntityNodeByKey(processBOMOrder.getRefBOMUUID(),
							"uuid", BillOfMaterialOrder.NODENAME, null);
			MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
					.getEntityNodeByKey(
							processBOMOrder.getRefMaterialSKUUUID(), "uuid",
							MaterialStockKeepUnit.NODENAME, null);
			processBOMOrderManager.convMaterialStockKeepUnitToUI(
					materialStockKeepUnit, processBOMOrderUIModel);
			ProcessRouteOrder processRouteOrder = (ProcessRouteOrder) processRouteOrderManager
					.getEntityNodeByKey(
							processBOMOrder.getRefProcessRouteUUID(), "uuid",
							ProcessRouteOrder.NODENAME, null);
			processBOMOrderManager.convProcessRouteOrderToUI(processRouteOrder,
					processBOMOrderUIModel);
			processBOMOrderManager.convBillOfMaterialToUI(billOfMaterialOrder,
					processBOMOrderUIModel);
			processBOMOrderList.add(processBOMOrderUIModel);
		}
		return processBOMOrderList;
	}

	protected List<ServiceEntityNode> searchInternal(
			ProcessBOMOrderSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[processBOMOrder]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(ProcessBOMOrder.SENAME);
		searchNodeConfig0.setNodeName(ProcessBOMOrder.NODENAME);
		searchNodeConfig0.setNodeInstID(ProcessBOMOrder.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[materialStockKeepUnit]
		BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
		searchNodeConfig1.setSeName(MaterialStockKeepUnit.SENAME);
		searchNodeConfig1.setNodeName(MaterialStockKeepUnit.NODENAME);
		searchNodeConfig1.setNodeInstID(MaterialStockKeepUnit.SENAME);
		searchNodeConfig1.setStartNodeFlag(false);
		searchNodeConfig1
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig1.setMapBaseFieldName("refMaterialSKUUUID");
		searchNodeConfig1.setMapSourceFieldName("uuid");
		searchNodeConfig1.setBaseNodeInstID(ProcessBOMOrder.SENAME);
		searchNodeConfigList.add(searchNodeConfig1);
		// Search node:[processRouteOrder]
		BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
		searchNodeConfig2.setSeName(ProcessRouteOrder.SENAME);
		searchNodeConfig2.setNodeName(ProcessRouteOrder.NODENAME);
		searchNodeConfig2.setNodeInstID(ProcessRouteOrder.SENAME);
		searchNodeConfig2.setStartNodeFlag(false);
		searchNodeConfig2
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig2.setMapBaseFieldName("refProcessRouteUUID");
		searchNodeConfig2.setMapSourceFieldName("uuid");
		searchNodeConfig2.setBaseNodeInstID(ProcessBOMOrder.SENAME);
		searchNodeConfigList.add(searchNodeConfig2);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}


}
