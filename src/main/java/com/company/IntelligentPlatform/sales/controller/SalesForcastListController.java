package com.company.IntelligentPlatform.sales.controller;

import com.company.IntelligentPlatform.sales.dto.*;
import com.company.IntelligentPlatform.sales.service.SalesForcastManager;
import com.company.IntelligentPlatform.sales.service.SalesForcastServiceModel;
import com.company.IntelligentPlatform.sales.service.SalesForcastSpecifier;
import com.company.IntelligentPlatform.sales.model.SalesForcast;
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
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.*;

@Scope("session")
@Controller(value = "salesForcastListController")
@RequestMapping(value = "/salesForcast")
public class SalesForcastListController extends SEListController {

	public static final String AOID_RESOURCE = IServiceModelConstants.SalesForcast;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected SalesForcastManager salesForcastManager;

	@Autowired
	protected SalesForcastServiceUIModelExtension salesForcastServiceUIModelExtension;

	@Autowired
	protected SalesForcastSpecifier salesForcastSpecifier;

	protected Logger logger = LoggerFactory.getLogger(SalesForcastListController.class);

	protected List<SalesForcastServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException, DocActionException {
		return serviceBasicUtilityController.convServiceUIModuleList(SalesForcastServiceUIModel.class,
				SalesForcastServiceModel.class, rawList,
				salesForcastManager, SalesForcast.SENAME, salesForcastSpecifier, logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				SalesForcastSearchModel.class, searchContext -> salesForcastManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				SalesForcastSearchModel.class, searchContext -> salesForcastManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, SalesForcastSearchModel.class, searchContext -> salesForcastManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}
	
}