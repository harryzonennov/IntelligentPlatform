package com.company.IntelligentPlatform.production.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.production.dto.ProdProcessServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProdProcessUIModel;
import com.company.IntelligentPlatform.production.service.ProdProcessManager;
import com.company.IntelligentPlatform.production.service.ProdWorkCenterManager;
import com.company.IntelligentPlatform.production.service.ProductionDataException;
import com.company.IntelligentPlatform.production.model.ProdProcess;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
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
import com.company.IntelligentPlatform.common.model.ServiceModuleConvertPara;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "prodProcessEditorController")
@RequestMapping(value = "/prodProcess")
public class ProdProcessEditorController extends SEEditorController {

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
	protected ProdProcessManager prodProcessManager;

	@Autowired
	protected ProdWorkCenterManager prodWorkCenterManager;

	@Autowired
	protected OrganizationManager organizationManager;

	@Autowired
	protected StandardKeyFlagProxy standardKeyFlagProxy;

	@Autowired
	protected ProdProcessServiceUIModelExtension prodProcessServiceUIModelExtension;

	protected String getPreWarnMsg(String key, Map<String, String> preWarnMap) {
		return preWarnMap.get(key);
	}

	protected Map<String, String> getPreWarnMap() throws IOException {
		Locale locale = Locale.getDefault();
		String path = ProdProcessUIModel.class.getResource("").getPath();
		String resFileName = ProdProcess.SENAME;
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap;
	}


	protected void saveInternal(ProdProcessUIModel prodProcessUIModel)
			throws ServiceEntityConfigureException, LogonInfoException {
		String baseUUID = prodProcessUIModel.getUuid();
		ProdProcess prodProcess = (ProdProcess) getServiceEntityNodeFromBuffer(
				ProdProcess.NODENAME, baseUUID);
		prodProcessManager.convUIToProdProcess(prodProcessUIModel, prodProcess);
		LogonUser logonUser = logonActionController.getLogonUser();
		if (logonUser == null) {
			throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
		}
		Organization organization = logonActionController
				.getOrganizationByUser(logonUser.getUuid());
		this.save(baseUUID, prodProcessManager, logonUser, organization);
	}

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
			ProdProcessUIModel prodProcessUIModel = (ProdProcessUIModel) JSONObject
					.toBean(jsonObject, ProdProcessUIModel.class, classMap);
			List<ServiceEntityNode> rawSeNodeList = prodProcessManager
					.genSeNodeListInExtensionUnion(
							prodProcessServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							ProdProcess.class, prodProcessUIModel);
			prodProcessManager.updateSeNodeListWrapper(rawSeNodeList,
					logonUser.getUuid(), organizationUUID);
			ProdProcess prodProcess = (ProdProcess) prodProcessManager
					.getEntityNodeByKey(prodProcessUIModel.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							ProdProcess.NODENAME, logonUser.getClient(), null);
			prodProcessUIModel = (ProdProcessUIModel) prodProcessManager
					.genUIModelFromUIModelExtension(ProdProcessUIModel.class,
							prodProcessServiceUIModelExtension
									.genUIModelExtensionUnion().get(0), 
							prodProcess, logonActionController.getLogonInfo(), null);
			return ServiceJSONParser.genDefOKJSONObject(prodProcessUIModel);
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
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
			ProdProcess prodProcess = (ProdProcess) prodProcessManager
					.newRootEntityNode(logonUser.getClient());
			ProdProcessUIModel prodProcessUIModel = new ProdProcessUIModel();
			prodProcessManager.convProdProcessToUI(prodProcess,
					prodProcessUIModel);
			return ServiceJSONParser.genDefOKJSONObject(prodProcessUIModel);
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
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
		return super.checkDuplicateIDCore(simpleRequest, prodProcessManager);
	}
	
	@RequestMapping(value = "/getStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getStatus() {
		try {
			Map<Integer, String> statusMap = prodProcessManager
					.initStatusMap();
			return prodProcessManager.getDefaultSelectMap(statusMap);
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getMessage());
		}

	}
	
	@RequestMapping(value = "/getKeyProcessMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getKeyFlagMap() {
		try {
			Map<Integer, String> keyFlagMap = prodProcessManager
					.initKeyProcessMap();
			return prodProcessManager.getDefaultSelectMap(keyFlagMap);
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getMessage());
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
			ProdProcess prodProcess = (ProdProcess) prodProcessManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProdProcess.NODENAME, logonUser.getClient(), null);
			lockSEList.add(prodProcess);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));
			List<ServiceModuleConvertPara> addtionalConvertParaList = new ArrayList<ServiceModuleConvertPara>();
			ProdProcessUIModel prodProcessUIModel = (ProdProcessUIModel) prodProcessManager
					.genUIModelFromUIModelExtension(ProdProcessUIModel.class,
							prodProcessServiceUIModelExtension
									.genUIModelExtensionUnion().get(0), 									
							prodProcess, logonActionController.getLogonInfo(),addtionalConvertParaList);
			return ServiceJSONParser.genDefOKJSONObject(prodProcessUIModel);
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
			ProdProcess prodProcess = (ProdProcess) prodProcessManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProdProcess.NODENAME, logonUser.getClient(), null);
			String baseUUID = prodProcess.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<ServiceEntityNode>();
			lockSEList.add(prodProcess);
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					prodProcess.getName(), prodProcess.getId(), baseUUID);

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


	@RequestMapping(value = "/chooseWorkCenterToProcess", produces = "text/html;charset=UTF-8")
	public @ResponseBody String chooseWorkCenterToProcess(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser curLogonUser = logonActionController.getLogonUser();
			if (curLogonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String workCenterUUID = request.getUuid();
			ProdWorkCenter prodWorkCenter = (ProdWorkCenter) prodWorkCenterManager
					.getEntityNodeByKey(workCenterUUID,
							IServiceEntityNodeFieldConstant.UUID,
							ProdWorkCenter.NODENAME, curLogonUser.getClient(),
							null);
			if (prodWorkCenter == null) {
				// Should raise exception
				throw new ProductionDataException(
						ProductionDataException.PARA_NO_COSTCENTER,
						workCenterUUID);
			}
			String baseUUID = request.getBaseUUID();
			ProdProcess prodProcess = (ProdProcess) getServiceEntityNodeFromBuffer(
					ProdProcess.NODENAME, baseUUID);
			if (prodProcess == null) {
				// Should raise exception
				throw new ProductionDataException(
						ProductionDataException.PARA_NO_PRODPROCSS, baseUUID);
			}
			prodProcess.setRefWorkCenterUUID(prodWorkCenter.getUuid());
			return ServiceJSONParser.genDefOKJSONObject(prodWorkCenter);
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ProductionDataException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "getMaterialStockKeepUnit", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getMaterialStockKeepUnit(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			String uuid = request.getUuid();
			ProdProcess prodProcess = (ProdProcess) prodProcessManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProdProcess.NODENAME, null);
			if (prodProcess == null) {
				throw new ProductionDataException(
						ProductionDataException.TYPE_SYSTEM_WRONG);
			}
			String responseData = ServiceJSONParser
					.genDefOKJSONObject(prodProcess);
			return responseData;
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ProductionDataException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
