package com.company.IntelligentPlatform.common.dto;

import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.SystemCodeValueCollectionServiceModel;
import com.company.IntelligentPlatform.common.service.SystemCodeValueCollectionManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SystemCodeValueCollection;

@Scope("session")
@Controller(value = "systemCodeValueCollectionEditorController")
@RequestMapping(value = "/systemCodeValueCollection")
public class SystemCodeValueCollectionEditorController extends
		SEEditorController {

	public static final String AOID_RESOURCE = IServiceModelConstants.SystemCodeValueCollection;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected SystemCodeValueCollectionServiceUIModelExtension systemCodeValueCollectionServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected SystemCodeValueCollectionManager systemCodeValueCollectionManager;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				SystemCodeValueCollectionServiceUIModel.class,
				SystemCodeValueCollectionServiceModel.class, AOID_RESOURCE,
				SystemCodeValueCollection.NODENAME,
				SystemCodeValueCollection.SENAME, systemCodeValueCollectionServiceUIModelExtension,
				systemCodeValueCollectionManager
		);
	}

	private SystemCodeValueCollectionServiceUIModel parseToServiceUIModel(String request){
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		classMap.put("systemCodeValueUnionUIModelList",
				SystemCodeValueUnionUIModel.class);
        return (SystemCodeValueCollectionServiceUIModel) JSONObject
				.toBean(jsonObject,
						SystemCodeValueCollectionServiceUIModel.class, classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		SystemCodeValueCollectionServiceUIModel systemCodeValueCollectionServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				systemCodeValueCollectionServiceUIModel,
				systemCodeValueCollectionServiceUIModel.getSystemCodeValueCollectionUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService() {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				ServiceBasicUtilityController.InitServiceEntityRequestBuilder.getBuilder(SystemCodeValueCollection.SENAME, SystemCodeValueCollection.NODENAME)
						.processServiceEntityNode(serviceEntityNode -> {
                            SystemCodeValueCollection systemCodeValueCollection = (SystemCodeValueCollection) serviceEntityNode;
                            systemCodeValueCollection.setCollectionType(SystemCodeValueCollection.COLTYPE_CUSTOM);
                            return systemCodeValueCollection;
                        }).build(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				systemCodeValueCollectionManager);
	}

	//TODO migrate this to sub node
	@RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String deleteModule(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			if (ServiceEntityStringHelper.checkNullString(request.getUuid())) {
				// UUID should not be null
				throw new ServiceModuleProxyException(
						ServiceModuleProxyException.TYPE_SYSTEM_WRONG);
			}
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_DELETE);
			LogonUser curUser = logonActionController.getLogonUser();
			if (curUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			SystemCodeValueCollection systemCodeValueCollection = (SystemCodeValueCollection) systemCodeValueCollectionManager
					.getEntityNodeByKey(request.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							SystemCodeValueCollection.NODENAME,
							curUser.getClient(), null);
			if (systemCodeValueCollection != null) {
				systemCodeValueCollectionManager.admDeleteEntityByKey(
						systemCodeValueCollection.getUuid(),
						IServiceEntityNodeFieldConstant.UUID,
						LogonUser.NODENAME);
			}
			return ServiceJSONParser.genDeleteOKResponse();
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
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
		return serviceBasicUtilityController.preLock(uuid, ISystemActionCode.ACID_EDIT, getServiceUIModelRequest());
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

	@RequestMapping(value = "/getCodeValueService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getCodeValueService(String id) {
		return serviceBasicUtilityController.getListMeta(() -> {
			try {
				return systemCodeValueCollectionManager
						.loadRawCodeValueUnionList(id, logonActionController.getClient());
			} catch (ServiceModuleProxyException e) {
				throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
			}
		}, AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/getCodeValueCollection", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getCodeValueCollection(String uuid) {
		return serviceBasicUtilityController.getListMeta(() -> {
			try {
				SystemCodeValueCollection systemCodeValueCollection = (SystemCodeValueCollection) systemCodeValueCollectionManager
						.getEntityNodeByUUID(uuid,
								SystemCodeValueCollection.NODENAME,
								logonActionController.getClient());
                return systemCodeValueCollectionManager.loadRawCodeValueUnionList(systemCodeValueCollection);
			} catch (ServiceModuleProxyException e) {
				throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
			}
        }, AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
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

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "/getCollectionCategory", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getCollectionCategory() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> systemCodeValueCollectionManager.initCollectionCategoryMap(lanCode));
	}

	@RequestMapping(value = "/getCollectionTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getCollectionTypeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> systemCodeValueCollectionManager.initCollectionTypeMap(lanCode));
	}

}
