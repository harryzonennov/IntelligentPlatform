package com.company.IntelligentPlatform.production.controller;

import java.util.List;

import com.company.IntelligentPlatform.production.dto.BillOfMaterialItemSearchModel;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialItemServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialItemUIModel;
import com.company.IntelligentPlatform.production.service.BillOfMaterialOrderManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "billOfMaterialItemListController")
@RequestMapping(value = "/billOfMaterialItem")
public class BillOfMaterialItemListController extends SEListController {

	public static final String AOID_RESOURCE = BillOfMaterialOrderEditorController.AOID_RESOURCE;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	@Autowired
	protected BillOfMaterialItemServiceUIModelExtension billOfMaterialItemServiceUIModelExtension;

	protected List<BillOfMaterialItemUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, ServiceModuleProxyException {
		return serviceBasicUtilityController.convUIModuleList(BillOfMaterialItemUIModel.class, rawList,
				billOfMaterialOrderManager, billOfMaterialItemServiceUIModelExtension);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, BillOfMaterialItemSearchModel.class, searchContext -> billOfMaterialOrderManager
						.getSearchProxy().searchItemList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/loadLeanModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadLeanModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				null,
				BillOfMaterialItemSearchModel.class, searchContext -> billOfMaterialOrderManager
						.getSearchProxy().searchItemList(searchContext),  null);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				null,
				BillOfMaterialItemSearchModel.class, searchContext -> billOfMaterialOrderManager
						.getSearchProxy().searchItemList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				request,
				BillOfMaterialItemSearchModel.class, searchContext -> billOfMaterialOrderManager
						.getSearchProxy().searchItemList(searchContext),  this::getModuleListCore);
	}

}
