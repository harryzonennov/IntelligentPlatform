package com.company.IntelligentPlatform.platform.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.platform.service.CalendarTemplateManager;
import com.company.IntelligentPlatform.platform.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.platform.controller.LogonActionController;
import com.company.IntelligentPlatform.platform.controller.SEListController;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.BsearchService;
import com.company.IntelligentPlatform.platform.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.platform.model.ISystemActionCode;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "calendarTemplateListController")
@RequestMapping(value = "/calendarTemplate")
public class CalendarTemplateListController extends SEListController {

	public static final String AOID_RESOURCE = IServiceModelConstants.CalendarTemplate;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected CalendarTemplateManager calendarTemplateManager;

	@Autowired
	protected CalendarTemplateServiceUIModelExtension calendarTemplateServiceUIModelExtension;

	protected List<CalendarTemplateUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, ServiceModuleProxyException {
		return serviceBasicUtilityController.convUIModuleList(CalendarTemplateUIModel.class, rawList,
				calendarTemplateManager, calendarTemplateServiceUIModelExtension);
	}
	
	@RequestMapping(value = "/loadLeanModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadLeanModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				CalendarTemplateSearchModel.class, searchContext -> calendarTemplateManager
						.getSearchProxy().searchDocList(searchContext),  null);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				CalendarTemplateSearchModel.class, searchContext -> calendarTemplateManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				request,
				CalendarTemplateSearchModel.class, searchContext -> calendarTemplateManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, CalendarTemplateSearchModel.class, searchContext -> calendarTemplateManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

}
