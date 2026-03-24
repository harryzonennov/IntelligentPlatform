package com.company.IntelligentPlatform.production.controller;

import java.util.List;

import com.company.IntelligentPlatform.production.dto.*;
import com.company.IntelligentPlatform.production.service.ProdPickingOrderManager;
import com.company.IntelligentPlatform.production.service.ProdPickingRefMaterialItemManager;
import com.company.IntelligentPlatform.production.model.ProdPickingOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "prodReturnRefMaterialItemListController")
@RequestMapping(value = "/prodReturnRefMaterialItem")
public class ProdReturnRefMaterialItemListController extends SEListController {

	public static final String AOID_RESOURCE = ProdPickingOrder.SENAME;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ProdPickingOrderManager prodPickingOrderManager;

	@Autowired
	protected ProdPickingRefMaterialItemManager prodPickingRefMaterialItemManager;

	@Autowired
	protected ProdPickingRefMaterialItemServiceUIModelExtension prodPickingRefMaterialItemServiceUIModelExtension;
	
	protected Logger logger = LoggerFactory.getLogger(ProdReturnRefMaterialItemListController.class);

	protected List<ProdPickingRefMaterialItemUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, ServiceModuleProxyException {
		return serviceBasicUtilityController.convUIModuleList(ProdPickingRefMaterialItemUIModel.class, rawList,
				prodPickingOrderManager, prodPickingRefMaterialItemServiceUIModelExtension);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, ProdPickingRefMaterialItemSearchModel.class, searchContext -> prodPickingOrderManager
						.getSearchProxy().searchItemList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/loadLeanModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadLeanModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				null,
				ProdPickingRefMaterialItemSearchModel.class, searchContext -> prodPickingOrderManager
						.getSearchProxy().searchItemList(searchContext),  null);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				null,
				ProdPickingRefMaterialItemSearchModel.class, searchContext -> prodPickingOrderManager
						.getSearchProxy().searchItemList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				request,
				ProdPickingRefMaterialItemSearchModel.class, searchContext -> prodPickingOrderManager
						.getSearchProxy().searchItemList(searchContext),  this::getModuleListCore);
	}

}
