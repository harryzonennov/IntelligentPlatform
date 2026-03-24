package com.company.IntelligentPlatform.production.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.production.dto.ProcessRouteOrderServiceUIModel;
import com.company.IntelligentPlatform.production.dto.ProcessRouteOrderServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProcessRouteOrderUIModel;
import com.company.IntelligentPlatform.production.dto.ProcessRouteProcessItemUIModel;
import com.company.IntelligentPlatform.production.service.ProcessRouteOrderManager;
import com.company.IntelligentPlatform.production.service.ProcessRouteOrderServiceModel;
import com.company.IntelligentPlatform.production.service.ProdProcessManager;
import com.company.IntelligentPlatform.production.service.ProductionDataException;
import com.company.IntelligentPlatform.production.model.ProcessRouteOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.StandardKeyFlagProxy;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "processRouteOrderEditorController")
@RequestMapping(value = "/processRouteOrder")
public class ProcessRouteOrderEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = ProdWorkCenterEditorController.AOID_RESOURCE;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ProcessRouteOrderManager processRouteOrderManager;

	@Autowired
	protected ProdProcessManager prodProcessManager;

	@Autowired
	protected ProcessRouteProcessItemListController processRouteProcessItemListController;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ProcessRouteOrderServiceUIModelExtension processRouteOrderServiceUIModelExtension;

	@Autowired
	protected StandardKeyFlagProxy standardKeyFlagProxy;

	@RequestMapping(value = "/getStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getStatus() {
		try {
			Map<Integer, String> statusMap = processRouteOrderManager
					.initStatusMap();
			return processRouteOrderManager.getDefaultSelectMap(statusMap);
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}

	}

	@RequestMapping(value = "/getKeyFlagMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getKeyFlagMap() {
		try {
			Map<Integer, String> keyFlagMap = processRouteOrderManager
					.initKeyFlagMap();
			return processRouteOrderManager.getDefaultSelectMap(keyFlagMap);
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}

	}

	@RequestMapping(value = "/getRouteTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getRouteTypeMap() {
		try {
			Map<Integer, String> routeTypeMap = processRouteOrderManager
					.initRouteTypeMap();
			return processRouteOrderManager.getDefaultSelectMap(routeTypeMap);
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}

	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String response) {
		JSONObject jsonObject = JSONObject.fromObject(response);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("processRouteProcessItemUIModelList",
				ProcessRouteProcessItemUIModel.class);
		ProcessRouteOrderServiceUIModel processRouteOrderServiceUIModel = (ProcessRouteOrderServiceUIModel) JSONObject
				.toBean(jsonObject, ProcessRouteOrderServiceUIModel.class,
						classMap);
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
			ProcessRouteOrderServiceModel processRouteOrderServiceModel = (ProcessRouteOrderServiceModel) processRouteOrderManager
					.genServiceModuleFromServiceUIModel(
							ProcessRouteOrderServiceModel.class,
							ProcessRouteOrderServiceUIModel.class,
							processRouteOrderServiceUIModel,
							processRouteOrderServiceUIModelExtension);
			processRouteOrderManager.updateServiceModuleWithDelete(
					ProcessRouteOrderServiceModel.class,
					processRouteOrderServiceModel, logonUser.getUuid(),
					organizationUUID);
			// Refresh service model
			ProcessRouteOrder processRouteOrder = (ProcessRouteOrder) processRouteOrderManager
					.getEntityNodeByKey(processRouteOrderServiceModel
							.getProcessRouteOrder().getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							ProcessRouteOrder.NODENAME, logonUser.getClient(),
							null);
			processRouteOrderServiceModel = (ProcessRouteOrderServiceModel) processRouteOrderManager
					.loadServiceModule(ProcessRouteOrderServiceModel.class,
							processRouteOrder);
			processRouteOrderServiceUIModel = (ProcessRouteOrderServiceUIModel) processRouteOrderManager
					.genServiceUIModuleFromServiceModel(
							ProcessRouteOrderServiceUIModel.class,
							ProcessRouteOrderServiceModel.class,
							processRouteOrderServiceModel,
							processRouteOrderServiceUIModelExtension,
							logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(processRouteOrderServiceUIModel);
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService() {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ProcessRouteOrder processRouteOrder = (ProcessRouteOrder) processRouteOrderManager
					.newRootEntityNode(logonUser.getClient());
			ProcessRouteOrderServiceModel processRouteOrderServiceModel = new ProcessRouteOrderServiceModel();
			processRouteOrderServiceModel
					.setProcessRouteOrder(processRouteOrder);
			ProcessRouteOrderServiceUIModel processRouteOrderServiceUIModel = (ProcessRouteOrderServiceUIModel) processRouteOrderManager
					.genServiceUIModuleFromServiceModel(
							ProcessRouteOrderServiceUIModel.class,
							ProcessRouteOrderServiceModel.class,
							processRouteOrderServiceModel,
							processRouteOrderServiceUIModelExtension,
							logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(processRouteOrderServiceUIModel);
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
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
			ProcessRouteOrder processRouteOrder = (ProcessRouteOrder) processRouteOrderManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProcessRouteOrder.NODENAME, logonUser.getClient(),
							null);
			String baseUUID = processRouteOrder.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<ServiceEntityNode>();
			lockSEList.add(processRouteOrder);
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					processRouteOrder.getName(), processRouteOrder.getId(),
					baseUUID);

		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e
					.getErrorMessage());
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
			ProcessRouteOrder processRouteOrder = (ProcessRouteOrder) processRouteOrderManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProcessRouteOrder.NODENAME, logonUser.getClient(),
							null);
			return ServiceJSONParser.genDefOKJSONObject(processRouteOrder);
		} catch (AuthorizationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex
					.getErrorMessage());
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());

		}
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleEditService(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> lockSEList = new ArrayList<ServiceEntityNode>();
			ProcessRouteOrder processRouteOrder = (ProcessRouteOrder) processRouteOrderManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProcessRouteOrder.NODENAME, logonUser.getClient(),
							null);
			lockSEList.add(processRouteOrder);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));
			ProcessRouteOrderServiceModel processRouteOrderServiceModel = (ProcessRouteOrderServiceModel) processRouteOrderManager
					.loadServiceModule(ProcessRouteOrderServiceModel.class,
							processRouteOrder);
			ProcessRouteOrderServiceUIModel processRouteOrderServiceUIModel = (ProcessRouteOrderServiceUIModel) processRouteOrderManager
					.genServiceUIModuleFromServiceModel(
							ProcessRouteOrderServiceUIModel.class,
							ProcessRouteOrderServiceModel.class,
							processRouteOrderServiceModel,
							processRouteOrderServiceUIModelExtension,
							logonActionController.getLogonInfo());
			if (!ServiceCollectionsHelper
					.checkNullList(processRouteOrderServiceUIModel
							.getProcessRouteProcessItemUIModelList())) {
				Collections.sort(processRouteOrderServiceUIModel
						.getProcessRouteProcessItemUIModelList());
			}
			return ServiceJSONParser
					.genDefOKJSONObject(processRouteOrderServiceUIModel);
		} catch (AuthorizationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex
					.getErrorMessage());
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LockObjectFailureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}


	protected String getPreWarnMsg(String key, Map<String, String> preWarnMap) {
		return preWarnMap.get(key);
	}

	protected Map<String, String> getPreWarnMap() throws IOException {
		Locale locale = Locale.getDefault();
		String path = ProcessRouteOrderUIModel.class.getResource("").getPath();
		String resFileName = ProcessRouteOrder.SENAME;
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap;
	}


	protected void saveInternal(
			ProcessRouteOrderUIModel processRouteOrderUIModel)
			throws ServiceEntityConfigureException, LogonInfoException {
		String baseUUID = processRouteOrderUIModel.getUuid();
		ProcessRouteOrder processRouteOrder = (ProcessRouteOrder) getServiceEntityNodeFromBuffer(
				ProcessRouteOrder.NODENAME, baseUUID);
		processRouteOrderManager.convUIToProcessRouteOrder(
				processRouteOrderUIModel, processRouteOrder);

		LogonUser logonUser = logonActionController.getLogonUser();
		if (logonUser == null) {
			throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
		}
		Organization organization = logonActionController
				.getOrganizationByUser(logonUser.getUuid());
		this.save(baseUUID, processRouteOrderManager, logonUser, organization);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				processRouteOrderManager);
	}



	@RequestMapping(value = "getProcessRouteOrder", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getProcessRouteOrder(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			String uuid = request.getUuid();
			ProcessRouteOrder processRouteOrder = (ProcessRouteOrder) processRouteOrderManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProcessRouteOrder.NODENAME, null);
			if (processRouteOrder == null) {
				throw new ProductionDataException(
						ProductionDataException.TYPE_SYSTEM_WRONG);
			}
			String responseData = ServiceJSONParser
					.genDefOKJSONObject(processRouteOrder);
			return responseData;
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ProductionDataException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

}
