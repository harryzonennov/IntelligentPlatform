package com.company.IntelligentPlatform.production.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.production.dto.ProductionResourceUnionSearchModel;
import com.company.IntelligentPlatform.production.dto.ProductionResourceUnionUIModel;
import com.company.IntelligentPlatform.production.service.ProductionResourceUnionManager;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;
import com.company.IntelligentPlatform.production.model.ProductionResourceUnion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
// TODO-DAO: import platform.foundation.DAO.PageSplitHelper;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.INavigationElementConstants;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.StandardKeyFlagProxy;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchNodeMapping;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "productionResourceUnionListController")
@RequestMapping(value = "/productionResourceUnion")
public class ProductionResourceUnionListController extends SEListController {

	public static final String AOID_RESOURCE = ProdWorkCenter.SENAME;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected ProductionResourceUnionManager productionResourceUnionManager;

	@Autowired
	protected OrganizationManager organizationManager;

	@Autowired
	protected StandardKeyFlagProxy standardKeyFlagProxy;


	protected List<ProductionResourceUnionUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<ProductionResourceUnionUIModel> productionResourceUnionList = new ArrayList<ProductionResourceUnionUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			ProductionResourceUnionUIModel productionResourceUnionUIModel = new ProductionResourceUnionUIModel();
			ProductionResourceUnion productionResourceUnion = (ProductionResourceUnion) rawNode;
			productionResourceUnionManager.convProductionResourceUnionToUI(
					productionResourceUnion, productionResourceUnionUIModel);
			Organization organization = (Organization) organizationManager
					.getEntityNodeByKey(
							productionResourceUnion.getRefCostCenterUUID(),
							"uuid", Organization.NODENAME, null);
			productionResourceUnionManager.convCostCenterToUI(organization,
					productionResourceUnionUIModel);
			productionResourceUnionList.add(productionResourceUnionUIModel);
		}
		return productionResourceUnionList;
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ProductionResourceUnionSearchModel productionResourceUnionSearchModel = new ProductionResourceUnionSearchModel();
			List<ServiceEntityNode> rawList = searchInternal(
					productionResourceUnionSearchModel, logonUser.getClient());
			List<ProductionResourceUnionUIModel> productionResourceUnionUIModelList = getModuleListCore(rawList);
			return ServiceJSONParser
					.genDefOKJSONArray(productionResourceUnionUIModelList);
		} catch (SearchConfigureException | AuthorizationException | LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException | NodeNotFoundException | ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String response) {
		try {
			JSONObject jsonObject = JSONObject.fromObject(response);
			ProductionResourceUnionSearchModel productionResourceUnionSearchModel = (ProductionResourceUnionSearchModel) JSONObject
					.toBean(jsonObject, ProductionResourceUnionSearchModel.class);
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = searchInternal(
					productionResourceUnionSearchModel, logonUser.getClient());
			List<ProductionResourceUnionUIModel> productionResourceUnionUIModelList = getModuleListCore(rawList);
			return ServiceJSONParser
					.genDefOKJSONArray(productionResourceUnionUIModelList);
		} catch (SearchConfigureException | AuthorizationException | LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException | NodeNotFoundException | ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	protected List<ServiceEntityNode> searchInternal(
			ProductionResourceUnionSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[productionResourceUnion]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(ProductionResourceUnion.SENAME);
		searchNodeConfig0.setNodeName(ProductionResourceUnion.NODENAME);
		searchNodeConfig0.setNodeInstID(ProductionResourceUnion.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[organization]
		BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
		searchNodeConfig1.setSeName(Organization.SENAME);
		searchNodeConfig1.setNodeName(Organization.NODENAME);
		searchNodeConfig1.setNodeInstID(Organization.SENAME);
		searchNodeConfig1.setStartNodeFlag(false);
		searchNodeConfig1
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig1.setMapBaseFieldName("refCostCenterUUID");
		searchNodeConfig1.setMapSourceFieldName("uuid");
		searchNodeConfig1.setBaseNodeInstID(ProductionResourceUnion.SENAME);
		searchNodeConfigList.add(searchNodeConfig1);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}

}
