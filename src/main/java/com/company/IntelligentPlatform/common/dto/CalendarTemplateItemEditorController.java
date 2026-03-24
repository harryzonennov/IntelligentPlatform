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

import com.company.IntelligentPlatform.common.dto.CalendarTemplateItemUIModel;
import com.company.IntelligentPlatform.common.service.CalendarTemplateManager;
import com.company.IntelligentPlatform.common.model.CalendarTemplate;
import com.company.IntelligentPlatform.common.model.CalendarTemplateItem;
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
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;


@Scope("session")
@Controller(value = "calendarTemplateItemEditorController")
@RequestMapping(value = "/calendarTemplateItem")
public class CalendarTemplateItemEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IServiceModelConstants.CalendarTemplate;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected CalendarTemplateItemServiceUIModelExtension calendarTemplateItemServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected CalendarTemplateManager calendarTemplateManager;

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String response) {
		JSONObject jsonObject = JSONObject.fromObject(response);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<String, Class>();
		try {
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
			CalendarTemplateItemUIModel calendarTemplateItemUIModel = (CalendarTemplateItemUIModel) JSONObject
					.toBean(jsonObject, CalendarTemplateItemUIModel.class,
							classMap);
			List<ServiceEntityNode> rawSeNodeList = calendarTemplateManager
					.genSeNodeListInExtensionUnion(
							calendarTemplateItemServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							CalendarTemplateItem.class,
							calendarTemplateItemUIModel);
			calendarTemplateManager.updateSeNodeListWrapper(rawSeNodeList,
					logonUser.getUuid(), organizationUUID);
			CalendarTemplateItem calendarTemplateItem = (CalendarTemplateItem) calendarTemplateManager
					.getEntityNodeByKey(calendarTemplateItemUIModel.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							CalendarTemplateItem.NODENAME,
							logonUser.getClient(), null);
			calendarTemplateItemUIModel = (CalendarTemplateItemUIModel) calendarTemplateManager
					.genUIModelFromUIModelExtension(
							CalendarTemplateItemUIModel.class,
							calendarTemplateItemServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							calendarTemplateItem, logonActionController.getLogonInfo(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(calendarTemplateItemUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
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
			CalendarTemplate calendarTemplate = (CalendarTemplate) calendarTemplateManager
					.getEntityNodeByKey(request.getBaseUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							CalendarTemplate.NODENAME, logonUser.getClient(),
							null);
			CalendarTemplateItem calendarTemplateItem = (CalendarTemplateItem) calendarTemplateManager
					.newEntityNode(calendarTemplate,
							CalendarTemplateItem.NODENAME);
			CalendarTemplateItemUIModel calendarTemplateItemUIModel = new CalendarTemplateItemUIModel();
			calendarTemplateManager.convCalendarTemplateItemToUI(
					calendarTemplateItem, calendarTemplateItemUIModel);
			return ServiceJSONParser
					.genDefOKJSONObject(calendarTemplateItemUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				calendarTemplateManager);
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
			CalendarTemplateItem calendarTemplateItem = (CalendarTemplateItem) calendarTemplateManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							CalendarTemplateItem.NODENAME,
							logonUser.getClient(), null);
			String baseUUID = calendarTemplateItem.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			lockSEList.add(calendarTemplateItem);
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					calendarTemplateItem.getName(),
					calendarTemplateItem.getId(), baseUUID);

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
			CalendarTemplateItem calendarTemplateItem = (CalendarTemplateItem) calendarTemplateManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							CalendarTemplateItem.NODENAME,
							logonUser.getClient(), null);
			return ServiceJSONParser.genDefOKJSONObject(calendarTemplateItem);
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());

		}
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
			CalendarTemplateItem calendarTemplateItem = (CalendarTemplateItem) calendarTemplateManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							CalendarTemplateItem.NODENAME,
							logonUser.getClient(), null);
			lockSEList.add(calendarTemplateItem);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));

			CalendarTemplateItemUIModel calendarTemplateItemUIModel = (CalendarTemplateItemUIModel) calendarTemplateManager
					.genUIModelFromUIModelExtension(
							CalendarTemplateItemUIModel.class,
							calendarTemplateItemServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							calendarTemplateItem, logonActionController.getLogonInfo(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(calendarTemplateItemUIModel);
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LockObjectFailureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "/getPeriodType", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPeriodType() {
		try {
			Map<Integer, String> periodTypeMap = calendarTemplateManager
					.initPeriodTypeMap();
			return calendarTemplateManager.getDefaultSelectMap(periodTypeMap, false);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}

	}

	@RequestMapping(value = "/getVocationType", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getVocationType() {
		try {
			Map<Integer, String> vocationTypeMap = calendarTemplateManager
					.initVocationTypeMap();
			return calendarTemplateManager.getDefaultSelectMap(vocationTypeMap, false);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}

	}

	@RequestMapping(value = "/getDayStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDayStatus() {
		try {
			Map<Integer, String> dayStatusMap = calendarTemplateManager
					.initDayStatusMap();
			return calendarTemplateManager.getDefaultSelectMap(dayStatusMap, false);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}

	}

}
