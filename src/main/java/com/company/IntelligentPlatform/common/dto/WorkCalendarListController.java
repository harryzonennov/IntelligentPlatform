package com.company.IntelligentPlatform.common.dto;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.service.CalendarTemplateManager;
import com.company.IntelligentPlatform.common.service.WorkCalendarManager;
import com.company.IntelligentPlatform.common.model.CalendarTemplate;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNodeLastUpdateTimeCompare;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "workCalendarListController")
@RequestMapping(value = "/workCalendar")
public class WorkCalendarListController extends SEListController {

	public static final String AOID_RESOURCE = IServiceModelConstants.WorkCalendar;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected WorkCalendarManager workCalendarManager;

	@Autowired
	protected CalendarTemplateManager calendarTemplateManager;

	@Autowired
	protected WorkCalendarServiceUIModelExtension workCalendarServiceUIModelExtension;

	protected List<WorkCalendarUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, ServiceModuleProxyException {
		return serviceBasicUtilityController.convUIModuleList(WorkCalendarUIModel.class, rawList,
				workCalendarManager, workCalendarServiceUIModelExtension);
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
			WorkCalendarSearchModel workCalendarSearchModel = new WorkCalendarSearchModel();
			List<ServiceEntityNode> rawList = workCalendarManager
					.searchInternal(workCalendarSearchModel,
							logonUser.getClient());
			List<WorkCalendarUIModel> workCalendarUIModelList = getModuleListCore(rawList);
			return ServiceJSONParser
					.genDefOKJSONArray(workCalendarUIModelList);
		} catch (SearchConfigureException | ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException | ServiceEntityInstallationException | NodeNotFoundException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		try {
			JSONObject jsonObject = JSONObject.fromObject(request);
			WorkCalendarSearchModel workCalendarSearchModel = (WorkCalendarSearchModel) JSONObject
					.toBean(jsonObject, WorkCalendarSearchModel.class);
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = workCalendarManager
					.searchInternal(workCalendarSearchModel,
							logonUser.getClient());
			List<WorkCalendarUIModel> workCalendarUIModelList = getModuleListCore(rawList);
			return ServiceJSONParser
					.genDefOKJSONArray(workCalendarUIModelList);
		} catch (SearchConfigureException | ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException | ServiceEntityInstallationException | NodeNotFoundException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}



	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String result = this
					.searchTableCore(
							request,
							logonUser.getClient(),
							new ServiceEntityNodeLastUpdateTimeCompare(),
							WorkCalendarSearchModel.class,
							searchModel -> {
								WorkCalendarSearchModel workCalendarSearchModel = (WorkCalendarSearchModel) searchModel;
								try {
									List<ServiceEntityNode> rawList = workCalendarManager
											.searchInternal(
													workCalendarSearchModel,
													logonUser.getClient());
									return rawList;
								} catch (SearchConfigureException | ServiceEntityInstallationException e) {
									throw e;
								} catch (NodeNotFoundException | ServiceEntityConfigureException e) {
									throw new SearchConfigureException(SearchConfigureException.PARA_SYSTEM_ERROR, e.getMessage());
								}
							}, this::getModuleListCore);
			return result;
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException | ServiceEntityInstallationException | IOException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch ( SearchConfigureException | ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		} catch (DocActionException e) {
            throw new RuntimeException(e);
        }
    }


	@RequestMapping(value = "/loadCalendarTemplateSelectList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadCalendarTemplateSelectList() {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = calendarTemplateManager
					.getEntityNodeListByKey(null, null,
							CalendarTemplate.NODENAME, null);
			return ServiceJSONParser.genDefOKJSONArray(rawList);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

}
