package com.company.IntelligentPlatform.production.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.company.IntelligentPlatform.production.dto.ProdJobOrderSearchModel;
import com.company.IntelligentPlatform.production.dto.ProdJobOrderUIModel;
import com.company.IntelligentPlatform.production.service.ProcessRouteOrderManager;
import com.company.IntelligentPlatform.production.service.ProdJobOrderManager;
import com.company.IntelligentPlatform.production.service.ProdProcessManager;
import com.company.IntelligentPlatform.production.service.ProdWorkCenterManager;
import com.company.IntelligentPlatform.production.service.ProductionOrderManager;
import com.company.IntelligentPlatform.production.model.ProcessRouteOrder;
import com.company.IntelligentPlatform.production.model.ProcessRouteProcessItem;
import com.company.IntelligentPlatform.production.model.ProdJobMaterialItem;
import com.company.IntelligentPlatform.production.model.ProdJobOrder;
import com.company.IntelligentPlatform.production.model.ProdProcess;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;
import com.company.IntelligentPlatform.production.model.ProductionOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
// TODO-DAO: import platform.foundation.DAO.PageSplitHelper;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.INavigationElementConstants;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchNodeMapping;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "prodJobOrderListController")
@RequestMapping(value = "/prodJobOrder")
public class ProdJobOrderListController extends SEListController {

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
	protected ProdJobOrderManager prodJobOrderManager;

	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected ProcessRouteOrderManager processRouteOrderManager;

	@Autowired
	protected ProdProcessManager prodProcessManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ProdWorkCenterManager prodWorkCenterManager;


	protected List<ServiceEntityNode> searchInternal(
			ProdJobOrderSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[prodJobOrder]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(ProdJobOrder.SENAME);
		searchNodeConfig0.setNodeName(ProdJobOrder.NODENAME);
		searchNodeConfig0.setNodeInstID(ProdJobOrder.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[prodJobMaterialItem]
		BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
		searchNodeConfig1.setSeName(ProdJobMaterialItem.SENAME);
		searchNodeConfig1.setNodeName(ProdJobMaterialItem.NODENAME);
		searchNodeConfig1.setNodeInstID(ProdJobMaterialItem.NODENAME);
		searchNodeConfig1.setStartNodeFlag(false);
		searchNodeConfig1
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_ROOT);
		searchNodeConfig1.setBaseNodeInstID(ProdJobOrder.SENAME);
		searchNodeConfigList.add(searchNodeConfig1);
		// Search node:[productionOrder]
		BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
		searchNodeConfig2.setSeName(ProductionOrder.SENAME);
		searchNodeConfig2.setNodeName(ProductionOrder.NODENAME);
		searchNodeConfig2.setNodeInstID(ProductionOrder.SENAME);
		searchNodeConfig2.setStartNodeFlag(false);
		searchNodeConfig2
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig2.setMapBaseFieldName("refProdRouteProcessItemUUID");
		searchNodeConfig2.setMapSourceFieldName("uuid");
		searchNodeConfig2.setBaseNodeInstID(ProdJobOrder.SENAME);
		searchNodeConfigList.add(searchNodeConfig2);
		// Search node:[processRouteOrder]
		BSearchNodeComConfigure searchNodeConfig3 = new BSearchNodeComConfigure();
		searchNodeConfig3.setSeName(ProcessRouteOrder.SENAME);
		searchNodeConfig3.setNodeName(ProcessRouteOrder.NODENAME);
		searchNodeConfig3.setNodeInstID(ProcessRouteOrder.SENAME);
		searchNodeConfig3.setStartNodeFlag(false);
		searchNodeConfig3
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_CHILD);
		searchNodeConfig3.setBaseNodeInstID(ProcessRouteProcessItem.NODENAME);
		searchNodeConfigList.add(searchNodeConfig3);
		// Search node:[prodProcess]
		BSearchNodeComConfigure searchNodeConfig4 = new BSearchNodeComConfigure();
		searchNodeConfig4.setSeName(ProdProcess.SENAME);
		searchNodeConfig4.setNodeName(ProdProcess.NODENAME);
		searchNodeConfig4.setNodeInstID(ProdProcess.SENAME);
		searchNodeConfig4.setStartNodeFlag(false);
		searchNodeConfig4
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig4.setMapBaseFieldName("refProductionOrderUUID");
		searchNodeConfig4.setMapSourceFieldName("uuid");
		searchNodeConfig4.setBaseNodeInstID(ProcessRouteProcessItem.NODENAME);
		searchNodeConfigList.add(searchNodeConfig4);
		// Search node:[materialStockKeepUnit]
		BSearchNodeComConfigure searchNodeConfig5 = new BSearchNodeComConfigure();
		searchNodeConfig5.setSeName(MaterialStockKeepUnit.SENAME);
		searchNodeConfig5.setNodeName(MaterialStockKeepUnit.NODENAME);
		searchNodeConfig5.setNodeInstID(MaterialStockKeepUnit.SENAME);
		searchNodeConfig5.setStartNodeFlag(false);
		searchNodeConfig5
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig5.setMapBaseFieldName("refMaterialSKUUUID");
		searchNodeConfig5.setMapSourceFieldName("uuid");
		searchNodeConfig5.setBaseNodeInstID(ProdJobOrder.SENAME);
		searchNodeConfigList.add(searchNodeConfig5);
		// Search node:[prodWorkCenter]
		BSearchNodeComConfigure searchNodeConfig6 = new BSearchNodeComConfigure();
		searchNodeConfig6.setSeName(ProdWorkCenter.SENAME);
		searchNodeConfig6.setNodeName(ProdWorkCenter.NODENAME);
		searchNodeConfig6.setNodeInstID(ProdWorkCenter.SENAME);
		searchNodeConfig6.setStartNodeFlag(false);
		searchNodeConfig6
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig6.setMapBaseFieldName("refWorkCenterUUID");
		searchNodeConfig6.setMapSourceFieldName("uuid");
		searchNodeConfigList.add(searchNodeConfig6);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}


}
