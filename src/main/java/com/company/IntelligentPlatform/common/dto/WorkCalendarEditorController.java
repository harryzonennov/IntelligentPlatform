package com.company.IntelligentPlatform.common.dto;

import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.dto.WorkCalendarServiceUIModel;
import com.company.IntelligentPlatform.common.dto.WorkCalendarServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.MatConfigHeaderConditionUIModel;
import com.company.IntelligentPlatform.common.service.CalendarTemplateManager;
import com.company.IntelligentPlatform.common.service.WorkCalendarException;
import com.company.IntelligentPlatform.common.service.WorkCalendarManager;
import com.company.IntelligentPlatform.common.service.WorkCalendarServiceModel;
import com.company.IntelligentPlatform.common.model.CalendarTemplate;
import com.company.IntelligentPlatform.common.model.CalendarTemplateItem;
import com.company.IntelligentPlatform.common.model.WorkCalendar;
import com.company.IntelligentPlatform.common.model.MatConfigHeaderCondition;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "workCalendarEditorController")
@RequestMapping(value = "/workCalendar")
public class WorkCalendarEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IServiceModelConstants.WorkCalendar;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected WorkCalendarServiceUIModelExtension workCalendarServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected WorkCalendarManager workCalendarManager;

	@Autowired
	protected CalendarTemplateManager calendarTemplateManager;

	WorkCalendarServiceUIModel parseToServiceUIModel(String request){
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		classMap.put("workCalendarDayItemUIModelList",
				WorkCalendarDayItemUIModel.class);
		WorkCalendarServiceUIModel workCalendarServiceUIModel = (WorkCalendarServiceUIModel) JSONObject
				.toBean(jsonObject, WorkCalendarServiceUIModel.class, classMap);
		return workCalendarServiceUIModel;
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		try {
			WorkCalendarServiceUIModel workCalendarServiceUIModel = parseToServiceUIModel(request);
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
			Organization organization = logonActionController
					.getOrganizationByUser(logonUser.getUuid());
			if (organization != null) {
				organizationUUID = organization.getUuid();
			}
			WorkCalendarServiceModel workCalendarServiceModel = (WorkCalendarServiceModel) workCalendarManager
					.genServiceModuleFromServiceUIModel(
							WorkCalendarServiceModel.class,
							WorkCalendarServiceUIModel.class,
							workCalendarServiceUIModel,
							workCalendarServiceUIModelExtension);
			workCalendarManager.updateServiceModuleWithDelete(
					WorkCalendarServiceModel.class, workCalendarServiceModel,
					logonUser.getUuid(), organizationUUID);
			workCalendarServiceUIModel = refreshLoadServiceUIModel(workCalendarServiceModel
					.getWorkCalendar().getUuid(), logonUser.getClient());
			return ServiceJSONParser
					.genDefOKJSONObject(workCalendarServiceUIModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			if (ServiceEntityStringHelper
					.checkNullString(request.getBaseUUID())) {
				// should raise exception
				throw new WorkCalendarException(
						WorkCalendarException.PARA_NO_TEMPLATE,
						request.getBaseUUID());
			}
			if (ServiceEntityStringHelper.checkNullString(request.getContent())) {
				// should raise exception
				throw new WorkCalendarException(
						WorkCalendarException.PARA_NO_YEAR,
						request.getContent());
			}
			CalendarTemplate calendarTemplate = (CalendarTemplate) calendarTemplateManager
					.getEntityNodeByKey(request.getBaseUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							CalendarTemplate.NODENAME, logonUser.getClient(),
							null);
			if (calendarTemplate == null) {
				throw new WorkCalendarException(
						WorkCalendarException.PARA_NO_TEMPLATE,
						request.getBaseUUID());
			}
			List<ServiceEntityNode> calendarTemplateList = calendarTemplateManager
					.getEntityNodeListByKey(request.getBaseUUID(),
							IServiceEntityNodeFieldConstant.ROOTNODEUUID,
							CalendarTemplateItem.NODENAME,
							logonUser.getClient(), null);
			WorkCalendar workCalendar = (WorkCalendar) workCalendarManager
					.newRootEntityNode(logonUser.getClient());
			workCalendar.setYear(calendarTemplate.getYear());
			List<ServiceEntityNode> workCalendarDayItemList = workCalendarManager
					.initCalendarDayItemFromTemplateItem(workCalendar,
							calendarTemplateList);
			workCalendar.setRefTemplateUUID(calendarTemplate.getUuid());
			WorkCalendarServiceModel workCalendarServiceModel = new WorkCalendarServiceModel();
			workCalendarServiceModel.setWorkCalendar(workCalendar);
			workCalendarServiceModel
					.setWorkCalendarDayItemList(workCalendarDayItemList);
			WorkCalendarServiceUIModel workCalendarServiceUIModel = (WorkCalendarServiceUIModel) workCalendarManager
					.genServiceUIModuleFromServiceModel(
							WorkCalendarServiceUIModel.class,
							WorkCalendarServiceModel.class,
							workCalendarServiceModel,
							workCalendarServiceUIModelExtension, logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(workCalendarServiceUIModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException | WorkCalendarException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest, workCalendarManager);
	}

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preLock(String uuid) {
		try {
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			WorkCalendar workCalendar = (WorkCalendar) workCalendarManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							WorkCalendar.NODENAME, logonUser.getClient(), null);
			String baseUUID = workCalendar.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			lockSEList.add(workCalendar);
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					workCalendar.getName(), workCalendar.getId(), baseUUID);

		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		}
	}

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLockService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preLockService(
			@RequestBody SimpleSEJSONRequest request) {
		return preLock(request.getUuid());
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModule(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			WorkCalendar workCalendar = (WorkCalendar) workCalendarManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							WorkCalendar.NODENAME, logonUser.getClient(), null);
			return ServiceJSONParser.genDefOKJSONObject(workCalendar);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	private WorkCalendarServiceUIModel refreshLoadServiceUIModel(String uuid, String client)
			throws ServiceEntityConfigureException, ServiceModuleProxyException, ServiceUIModuleProxyException {
		WorkCalendar workCalendar = (WorkCalendar) workCalendarManager
				.getEntityNodeByKey(uuid,
						IServiceEntityNodeFieldConstant.UUID,
						WorkCalendar.NODENAME, client, null);
		WorkCalendarServiceModel workCalendarServiceModel = (WorkCalendarServiceModel) workCalendarManager
				.loadServiceModule(WorkCalendarServiceModel.class,
						workCalendar);
		WorkCalendarServiceUIModel workCalendarServiceUIModel = (WorkCalendarServiceUIModel) workCalendarManager
				.genServiceUIModuleFromServiceModel(
						WorkCalendarServiceUIModel.class,
						WorkCalendarServiceModel.class,
						workCalendarServiceModel,
						workCalendarServiceUIModelExtension, logonActionController.getLogonInfo());
		return workCalendarServiceUIModel;
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleEditService(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			WorkCalendar workCalendar = (WorkCalendar) workCalendarManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							WorkCalendar.NODENAME, logonUser.getClient(), null);
			lockSEList.add(workCalendar);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));
			WorkCalendarServiceUIModel workCalendarServiceUIModel = refreshLoadServiceUIModel(uuid,
					logonUser.getClient());
			return ServiceJSONParser
					.genDefOKJSONObject(workCalendarServiceUIModel);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LockObjectFailureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}


	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleViewService(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			WorkCalendarServiceUIModel workCalendarServiceUIModel = refreshLoadServiceUIModel(uuid,
					logonUser.getClient());
			return ServiceJSONParser
					.genDefOKJSONObject(workCalendarServiceUIModel);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LockObjectFailureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "/getDefaultFlag", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDefaultFlag() {
		try {
			Map<Integer, String> defaultFlagMap = workCalendarManager
					.initDefaultFlagMap();
			return workCalendarManager.getDefaultSelectMap(defaultFlagMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}

	}

	@RequestMapping(value = "/getStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getStatus() {
		try {
			Map<Integer, String> statusMap = workCalendarManager
					.initStatusMap();
			return workCalendarManager.getDefaultSelectMap(statusMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}
	}

}
