package com.company.IntelligentPlatform.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.dto.*;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.MessageTemplateException;
import com.company.IntelligentPlatform.common.service.MessageTemplateManager;
import com.company.IntelligentPlatform.common.model.RoleMessageCategory;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.dto.RoleServiceUIModel;
import com.company.IntelligentPlatform.common.dto.RoleServiceUIModelExtension;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.Role;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;

@Scope("session")
@Controller(value = "logonUserEditorController")
@RequestMapping(value = "/logonUser")
public class LogonUserEditorController extends SEEditorController {

	@Autowired
	protected LogonUserOrgManager logonUserOrgManager;

	@Autowired
	protected LogonUserManager logonUserManager;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LogonInfoManager logonInfoManager;

	@Autowired
	protected OrganizationManager organizationManager;

	@Autowired
	protected RoleManager roleManager;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected LogonUserServiceUIModelExtension logonUserServiceUIModelExtension;

	@Autowired
	protected RoleServiceUIModelExtension roleServiceUIModelExtension;

	@Autowired
	protected MessageTemplateManager messageTemplateManager;

	@Autowired
	protected LogonUserActionExecutionProxy logonUserActionExecutionProxy;

	protected Logger logger = LoggerFactory.getLogger(LogonUserEditorController.class);

	public final static String AOID_RESOURCE = IServiceModelConstants.LogonUser;
    @Autowired
    private LogonUserSpecifier logonUserSpecifier;

	public ServiceBasicUtilityController.DocUIModelWithActionRequest getDocUIModelRequest() {
		return new ServiceBasicUtilityController.DocUIModelWithActionRequest(
				LogonUserServiceUIModel.class,
				LogonUserServiceModel.class, AOID_RESOURCE,
				LogonUser.NODENAME, LogonUser.SENAME, logonUserServiceUIModelExtension,
				logonUserManager, LogonUserActionNode.NODENAME, logonUserActionExecutionProxy
		);
	}

	@RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getDocActionNodeList(String uuid, String actionCode) {
		return serviceBasicUtilityController.getDocActionNodeList(uuid, actionCode, getDocUIModelRequest());
	}

	@RequestMapping(value = "/getUserTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getUserTypeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> logonUserManager.initUserTypeMap(lanCode));
	}

	@RequestMapping(value = "/getStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getStatus() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> logonUserManager.initStatusMap(lanCode));
	}
	@RequestMapping(value = "/getLockUserFlagMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getLockUserFlagMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> logonUserManager.initLockUserFlagMap(lanCode));
	}
	@RequestMapping(value = "/getPasswordInitMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPasswordInitMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> logonUserManager.initPasswordInitMap(lanCode));
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.saveModuleService(request, getDocUIModelRequest(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest, logonUserManager);
	}

	@RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String deleteModule(
			String uuid) {
		return serviceBasicUtilityController.deleteModule(uuid, ISystemActionCode.ACID_EDIT,
				getDocUIModelRequest());
	}

	@RequestMapping(value = "/newModuleService")
	public @ResponseBody String newModuleService() {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getDocUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						LogonUser.SENAME, LogonUser.NODENAME,
						logonUserSpecifier), ISystemActionCode.ACID_EDIT);
	}

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLockService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String preLockService(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.preLock(request.getUuid(), ISystemActionCode.ACID_EDIT, getDocUIModelRequest());
	}

	@RequestMapping(value = "/loadUserRoleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadUserRoleService() {
		return serviceBasicUtilityController.getListMeta(() -> {
			try {List<Role> roleList = logonUserManager.getRoleList(
					logonActionController.getResUserUUID(), logonActionController.getClient());
				List<RoleServiceUIModel> roleServiceUIModelList = new ArrayList<>();
				if (!ServiceCollectionsHelper.checkNullList(roleList)) {
					for (Role role : roleList) {
						RoleServiceModel roleServiceModel = (RoleServiceModel) roleManager
								.loadServiceModule(RoleServiceModel.class, role);
						RoleServiceUIModel roleServiceUIModel = (RoleServiceUIModel) roleManager
								.genServiceUIModuleFromServiceModel(
										RoleServiceUIModel.class,
										RoleServiceModel.class, roleServiceModel,
										roleServiceUIModelExtension, logonActionController.getLogonInfo());
						roleServiceUIModelList.add(roleServiceUIModel);
					}
				}
				return roleServiceUIModelList;
			} catch (ServiceModuleProxyException | ServiceUIModuleProxyException e) {
				throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
			}
		});
	}

	@RequestMapping(value = "/getAuthorizationACUnionList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getAuthorizationACUnionList() {
		return serviceBasicUtilityController.getListMeta(() -> {
			List<AuthorizationManager.AuthorizationACUnion> authorizationACUnionList =
					logonActionController.getLogonInfo().getAuthorizationACUnionList();
			if (ServiceCollectionsHelper.checkNullList(authorizationACUnionList)) {
				return null;
			}
			List<AuthorizationManager.AuthorizationACUnion> resultList = new ArrayList<>();
			for(AuthorizationManager.AuthorizationACUnion authorizationACUnion:authorizationACUnionList) {
				AuthorizationManager.AuthorizationACUnion copiedAuthorACUnion =
						new AuthorizationManager.AuthorizationACUnion(authorizationACUnion.getAuthorizationObject(),
								authorizationACUnion.getActionCodeList(), null);
				resultList.add(copiedAuthorACUnion);
			}
			return resultList;
		});
	}

	@RequestMapping(value = "/loadUserMessageService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadUserMessageService() {
		return serviceBasicUtilityController.getListMeta(() -> {
			try {
				List<Role> roleList = logonUserManager.getRoleList(
						logonActionController.getResUserUUID(), logonActionController.getClient());
				List<String> roleUUIDList = new ArrayList<>();
				ServiceCollectionsHelper.forEach(roleList, role -> {
					roleUUIDList.add(role.getUuid());
					return role;
				});
				List<ServiceEntityNode> roleMessageCategoryList = roleManager.getEntityNodeListByMultipleKey(roleUUIDList
						, IServiceEntityNodeFieldConstant.ROOTNODEUUID, RoleMessageCategory.NODENAME,
						logonActionController.getClient(), null);
				if (ServiceCollectionsHelper.checkNullList(roleMessageCategoryList)) {
					return null;
				}
				List<String> uuidList = roleMessageCategoryList.stream().map(serviceEntityNode -> {
					RoleMessageCategory roleMessageCategory = (RoleMessageCategory) serviceEntityNode;
					return roleMessageCategory.getRefUUID();
				}).collect(Collectors.toList());
				return messageTemplateManager.executeSearchBatch(uuidList, logonActionController.getLogonInfo());
			} catch (MessageTemplateException e) {
				throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
			}
		});
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModule(String uuid) {
		return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
				getDocUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleViewService(String uuid) {
		return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_VIEW,
				getDocUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleEditService(String uuid) {
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, true,
				getDocUIModelRequest());
	}

	@RequestMapping(value = "/getWorkRoleMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getWorkRoleMap() {
		return serviceBasicUtilityController.getMapMeta(lanCode -> logonUserOrgManager
                .initWorkRoleMap(logonActionController.getLanguageCode(), logonActionController.getClient(), true));
	}

	private LogonUserPasswordSetUIModel parseToPasswordSetUIModel(String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		return (LogonUserPasswordSetUIModel) JSONObject
				.toBean(jsonObject, LogonUserPasswordSetUIModel.class, new HashMap());
	}

	@RequestMapping(value = "/setPasswordService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String setPasswordService(@RequestBody String request) {
		return serviceBasicUtilityController.voidExecuteWrapper(() -> {
			LogonUserPasswordSetUIModel logonUserPasswordSetUIModel = parseToPasswordSetUIModel(request);
			try {
				LogonUser logonUser = (LogonUser) logonUserManager
						.getEntityNodeByUUID(
								logonUserPasswordSetUIModel.getBaseUUID(),
								LogonUser.NODENAME, logonActionController.getClient());
				logonUserManager.resetPassword(logonUser,
						logonUserPasswordSetUIModel.getNewPassword(),
						logonActionController.getResUserUUID(), logonActionController.getResOrgUUID(), false);
			} catch (ServiceEntityConfigureException e) {
				throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
			} catch (ServiceEncodeException e) {
				throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
			}
		}, AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
	public @ResponseBody String executeDocAction(@RequestBody String request) {
		return serviceBasicUtilityController.executeDocActionFramework(request,
				(DocActionNodeProxy.IActionExecutor<LogonUserServiceModel>) (logonUserServiceModel, actionCode, logonInfo) -> {
					try {
						logonUserActionExecutionProxy.executeActionCore(logonUserServiceModel,
								actionCode, logonActionController.getSerialLogonInfo());
					} catch (DocActionException e) {
						throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
					}
				}, getDocUIModelRequest());
	}

	@RequestMapping(value = "/getActionCodeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getActionCodeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> logonUserActionExecutionProxy.getActionCodeMap(lanCode));
	}

	@RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocActionConfigureList() {
		return serviceBasicUtilityController.getDocActionConfigureListCore(this.logonUserActionExecutionProxy);
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
