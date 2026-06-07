package com.company.IntelligentPlatform.salesDistribution.controller;

import java.util.List;

import com.company.IntelligentPlatform.salesDistribution.dto.*;
import com.company.IntelligentPlatform.salesDistribution.service.SalesReturnOrderManager;

import com.company.IntelligentPlatform.salesDistribution.service.SalesReturnOrderServiceModel;
import com.company.IntelligentPlatform.salesDistribution.service.SalesReturnOrderSpecifier;
import com.company.IntelligentPlatform.salesDistribution.model.SalesReturnOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.platform.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.platform.controller.LogonActionController;
import com.company.IntelligentPlatform.platform.controller.SEListController;
import com.company.IntelligentPlatform.platform.service.DocActionException;
import com.company.IntelligentPlatform.platform.model.ISystemActionCode;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "salesReturnOrderListController")
@RequestMapping(value = "/salesReturnOrder")
public class SalesReturnOrderListController extends SEListController {

	public static final String AOID_RESOURCE = IServiceModelConstants.SalesReturnOrder;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected SalesReturnOrderManager salesReturnOrderManager;

	@Autowired
	protected SalesReturnOrderSpecifier salesReturnOrderSpecifier;

	@Autowired
	protected SalesReturnOrderServiceUIModelExtension salesReturnOrderServiceUIModelExtension;

	protected Logger logger = LoggerFactory.getLogger(SalesReturnOrderListController.class);

	protected List<SalesReturnOrderServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException, DocActionException {
		return serviceBasicUtilityController.convServiceUIModuleList(SalesReturnOrderServiceUIModel.class,
				SalesReturnOrderServiceModel.class, rawList,
				salesReturnOrderManager, SalesReturnOrder.SENAME,
				salesReturnOrderSpecifier, logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				SalesReturnOrderSearchModel.class, searchContext -> salesReturnOrderManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				SalesReturnOrderSearchModel.class, searchContext -> salesReturnOrderManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, SalesReturnOrderSearchModel.class, searchContext -> salesReturnOrderManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	
}