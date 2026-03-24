package com.company.IntelligentPlatform.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.IntelligentPlatform.common.model.WarehouseAttachment;
import com.company.IntelligentPlatform.common.dto.*;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.Role;
import com.company.IntelligentPlatform.common.model.ServiceModuleConvertPara;

import jakarta.servlet.http.HttpServletRequest;

@Scope("session")
@Controller(value = "roleEditorController")
@RequestMapping(value = "/role")
public class RoleEditorController extends SEEditorController {

	@Autowired
	protected AuthorizationObjectManager authorizationObjectManager;

	@Autowired
	protected ActionCodeManager actionCodeManager;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected RoleAuthorizationManager roleAuthorizationManager;

	@Autowired
	protected RoleManager roleManager;

	@Autowired
	protected RoleSpecifier roleSpecifier;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected RoleServiceUIModelExtension roleServiceUIModelExtension;

	public final static String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_ROLE;

	protected Logger logger = LoggerFactory.getLogger(RoleEditorController.class);

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		List<ServiceModuleConvertPara> serviceModuleConvertParaList = null;
		try {
			serviceModuleConvertParaList = roleAuthorizationManager.genActionCodeParaList();
		} catch (ServiceEntityConfigureException e) {
			// do nothing
			logger.error(e.getMessage(), e);
		}
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				RoleServiceUIModel.class,
				RoleServiceModel.class, AOID_RESOURCE,
				Role.NODENAME,
				Role.SENAME, roleServiceUIModelExtension,
				roleManager, serviceModuleConvertParaList
		);
	}

	@RequestMapping(value = "/getProcessTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getProcessTypeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> roleAuthorizationManager.initProcessTypeMap(lanCode));
	}

	@RequestMapping(value = "/getAuthorizationTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getAuthorizationTypeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> authorizationObjectManager.initAuthorizationObjectTypeMap(lanCode));
	}

	private RoleServiceUIModel parseToServiceUIModel(String request){
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap< >();
		classMap.put("roleAuthorizationUIModelList",
				RoleAuthorizationServiceUIModel.class);
		classMap.put("roleSubAuthorizationUIModelList",
				RoleSubAuthorizationUIModel.class);
		classMap.put("roleMessageCategoryUIModelList",
				RoleMessageCategoryServiceUIModel.class);
		return (RoleServiceUIModel) JSONObject
				.toBean(jsonObject, RoleServiceUIModel.class, classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		RoleServiceUIModel roleServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				roleServiceUIModel,
				roleServiceUIModel.getRoleUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService() {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						Role.SENAME, Role.NODENAME,
						roleSpecifier), ISystemActionCode.ACID_EDIT);
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

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preLock(String uuid) {
		return serviceBasicUtilityController.preLock(uuid, ISystemActionCode.ACID_EDIT, getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModule(String uuid) {
		return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleEditService(String uuid) {
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, false,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleViewService(String uuid) {
		return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	protected RoleServiceUIModel refreshLoadServiceUIModel(
			String uuid, String acId, LogonInfo logonInfo, boolean postFlag)
            throws ServiceEntityConfigureException,
            ServiceModuleProxyException, ServiceUIModuleProxyException,
            LogonInfoException, AuthorizationException, DocActionException {
		// Refresh service model
		Role role = (Role) roleManager
				.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID,
						Role.NODENAME, logonInfo.getClient(), null);
		return refreshLoadServiceUIModel(role, acId, logonInfo, postFlag);
	}

	protected RoleServiceUIModel refreshLoadServiceUIModel(
			Role role, String acId,  LogonInfo logonInfo, boolean postFlag)
            throws ServiceEntityConfigureException,
            ServiceModuleProxyException, ServiceUIModuleProxyException,
            LogonInfoException, AuthorizationException, DocActionException {
		RoleServiceUIModel roleServiceUIModel = (RoleServiceUIModel) serviceBasicUtilityController.refreshLoadServiceUIModel(
				getServiceUIModelRequest(),
				role, acId);
		if(postFlag){
			roleServiceUIModel = roleManager.postRefreshServiceUIModel(roleServiceUIModel, logonInfo);
		}
		return roleServiceUIModel;
	}

	private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
		return new DocAttachmentProxy.DocAttachmentProcessPara(roleManager,
				WarehouseAttachment.NODENAME, Role.NODENAME, null, null, null);
	}

	/**
	 * load the attachment content to consumer.
	 */
	@RequestMapping(value = "/loadAttachment")
	public ResponseEntity<byte[]> loadAttachment(String uuid) {
		return serviceBasicUtilityController.loadAttachment(uuid, AOID_RESOURCE,
				genDocAttachmentProcessPara());
	}

	/**
	 * Delete attachment
	 */
	@RequestMapping(value = "/deleteAttachment", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String deleteAttachment(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.deleteAttachment(request, AOID_RESOURCE,
				genDocAttachmentProcessPara());
	}

	/**
	 * Upload the attachment content information.
	 */
	@RequestMapping(value = "/uploadAttachment", consumes = "multipart/form-data", method = RequestMethod.POST)
	public @ResponseBody
	String uploadAttachment(HttpServletRequest request) {
		return serviceBasicUtilityController.uploadAttachment(request, AOID_RESOURCE,
				genDocAttachmentProcessPara());
	}

	/**
	 * Upload the attachment text information.
	 */
	@RequestMapping(value = "/uploadAttachmentText", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String uploadAttachmentText(
			@RequestBody FileAttachmentTextRequest request) {
		return serviceBasicUtilityController.uploadAttachmentText(request, AOID_RESOURCE,
				genDocAttachmentProcessPara());
	}

	@RequestMapping(value = "/exitEditor")
	public String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
