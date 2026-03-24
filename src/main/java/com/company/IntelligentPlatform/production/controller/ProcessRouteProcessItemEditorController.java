package com.company.IntelligentPlatform.production.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.production.dto.ProcessRouteProcessItemServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProcessRouteProcessItemUIModel;
import com.company.IntelligentPlatform.production.service.ProcessRouteOrderManager;
import com.company.IntelligentPlatform.production.service.ProcessRouteProcessItemManager;
import com.company.IntelligentPlatform.production.service.ProdProcessManager;
import com.company.IntelligentPlatform.production.service.ProdWorkCenterManager;
import com.company.IntelligentPlatform.production.service.ProductionDataException;
import com.company.IntelligentPlatform.production.model.ProcessRouteOrder;
import com.company.IntelligentPlatform.production.model.ProcessRouteProcessItem;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
@Controller(value = "processRouteProcessItemEditorController")
@RequestMapping(value = "/processRouteProcessItem")
public class ProcessRouteProcessItemEditorController extends SEEditorController {

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
	protected ProcessRouteProcessItemManager processRouteProcessItemManager;

	@Autowired
	protected ProdProcessManager prodProcessManager;
	
	@Autowired
	protected ProdWorkCenterManager prodWorkCenterManager;
	
	@Autowired
	protected ProcessRouteProcessItemServiceUIModelExtension processRouteProcessItemServiceUIModelExtension;
	
	@Autowired
	protected StandardKeyFlagProxy standardKeyFlagProxy;
	
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
			ProcessRouteProcessItemUIModel processRouteProcessItemUIModel = (ProcessRouteProcessItemUIModel) JSONObject
					.toBean(jsonObject, ProcessRouteProcessItemUIModel.class,
							classMap);
			List<ServiceEntityNode> rawSeNodeList = processRouteOrderManager
					.genSeNodeListInExtensionUnion(
							processRouteProcessItemServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							ProcessRouteProcessItem.class,
							processRouteProcessItemUIModel);
			processRouteOrderManager.updateSeNodeListWrapper(rawSeNodeList,
					logonUser.getUuid(), organizationUUID);
			ProcessRouteProcessItem processRouteProcessItem = (ProcessRouteProcessItem) processRouteOrderManager
					.getEntityNodeByKey(
							processRouteProcessItemUIModel.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							ProcessRouteProcessItem.NODENAME,
							logonUser.getClient(), null);
			processRouteProcessItemUIModel = (ProcessRouteProcessItemUIModel) processRouteOrderManager
					.genUIModelFromUIModelExtension(
							ProcessRouteProcessItemUIModel.class,
							processRouteProcessItemServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							processRouteProcessItem, 
							logonActionController.getLogonInfo(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(processRouteProcessItemUIModel);
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
			ProcessRouteOrder processRouteOrder = (ProcessRouteOrder) processRouteOrderManager
					.getEntityNodeByKey(request.getBaseUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							ProcessRouteOrder.NODENAME, logonUser.getClient(),
							null);
			ProcessRouteProcessItem processRouteProcessItem = (ProcessRouteProcessItem) processRouteOrderManager
					.newEntityNode(processRouteOrder,
							ProcessRouteProcessItem.NODENAME);
			ProcessRouteProcessItemUIModel processRouteProcessItemUIModel = new ProcessRouteProcessItemUIModel();
			processRouteOrderManager.convProcessRouteProcessItemToUI(
					processRouteProcessItem, processRouteProcessItemUIModel);
			return ServiceJSONParser
					.genDefOKJSONObject(processRouteProcessItemUIModel);
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
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
			ProcessRouteProcessItem processRouteProcessItem = (ProcessRouteProcessItem) processRouteOrderManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProcessRouteProcessItem.NODENAME,
							logonUser.getClient(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(processRouteProcessItem);
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
			ProcessRouteProcessItem processRouteProcessItem = (ProcessRouteProcessItem) processRouteOrderManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProcessRouteProcessItem.NODENAME,
							logonUser.getClient(), null);
			lockSEList.add(processRouteProcessItem);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));
			List<ServiceModuleConvertPara> addtionalConvertParaList = new ArrayList<ServiceModuleConvertPara>();
			ProcessRouteProcessItemUIModel processRouteProcessItemUIModel = (ProcessRouteProcessItemUIModel) processRouteOrderManager
					.genUIModelFromUIModelExtension(
							ProcessRouteProcessItemUIModel.class,
							processRouteProcessItemServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),									
							processRouteProcessItem, logonActionController.getLogonInfo(), addtionalConvertParaList);
			return ServiceJSONParser
					.genDefOKJSONObject(processRouteProcessItemUIModel);
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



	protected void saveInternal(
			ProcessRouteProcessItemUIModel processRouteProcessItemUIModel)
			throws ServiceEntityConfigureException, LogonInfoException {
		String baseUUID = processRouteProcessItemUIModel.getUuid();
		ProcessRouteProcessItem processRouteProcessItem = (ProcessRouteProcessItem) getServiceEntityNodeFromBuffer(
				ProcessRouteProcessItem.NODENAME, baseUUID);
		processRouteProcessItemManager.convUIToProcessRouteProcessItem(
				processRouteProcessItem, processRouteProcessItemUIModel);
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
			ProcessRouteProcessItem processRouteProcessItem = (ProcessRouteProcessItem) processRouteOrderManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProcessRouteProcessItem.NODENAME,
							logonUser.getClient(), null);
			String baseUUID = processRouteProcessItem.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<ServiceEntityNode>();
			lockSEList.add(processRouteProcessItem);
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					processRouteProcessItem.getName(),
					processRouteProcessItem.getId(), baseUUID);

		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e
					.getErrorMessage());
		}
	}
	
	@RequestMapping(value = "/getProcessItem", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getProcessItem(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser curLogonUser = logonActionController.getLogonUser();
			if (curLogonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String itemUUID = request.getUuid();
			ProcessRouteProcessItem processRouteProcessItem = (ProcessRouteProcessItem)processRouteOrderManager.getEntityNodeByKey(itemUUID,
					IServiceEntityNodeFieldConstant.UUID,
					ProcessRouteProcessItem.NODENAME, curLogonUser.getClient(), null);
			if (processRouteProcessItem == null) {
				// Should raise exception
				throw new ProductionDataException(
						ProductionDataException.TYPE_SYSTEM_WRONG);
			}
			ProcessRouteProcessItemUIModel processRouteProcessItemUIModel = new ProcessRouteProcessItemUIModel();
			processRouteProcessItemManager.convComProcessItemToUI(processRouteProcessItem, processRouteProcessItemUIModel);
			return ServiceJSONParser.genDefOKJSONObject(processRouteProcessItemUIModel);
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
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getMessage());
		}
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
							ProdWorkCenter.NODENAME, curLogonUser.getClient(), null);
			if (prodWorkCenter == null) {
				// Should raise exception
				throw new ProductionDataException(
						ProductionDataException.PARA_NO_COSTCENTER, workCenterUUID);
			}
			String baseUUID = request.getBaseUUID();
			ProcessRouteProcessItem processRouteProcessItem = (ProcessRouteProcessItem) getServiceEntityNodeFromBuffer(
					ProcessRouteProcessItem.NODENAME, baseUUID);
			if (processRouteProcessItem == null) {
				// Should raise exception
				throw new ProductionDataException(
						ProductionDataException.TYPE_SYSTEM_WRONG);
			}
			processRouteProcessItem.setRefWorkCenterUUID(prodWorkCenter.getUuid());
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


	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
