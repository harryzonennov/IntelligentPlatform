package com.company.IntelligentPlatform.common.dto;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;

@Scope("session")
@Controller(value = "materialEditorController")
@RequestMapping(value = "/material")
public class MaterialEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected MaterialManager materialManager;

	@Autowired
	protected StandardMaterialUnitManager standardMaterialUnitManager;

	@Autowired
	protected CargoManager cargoManager;

	@Autowired
	protected MaterialTypeManager materialTypeManager;

	@Autowired
	protected MaterialSpecifier materialSpecifier;

	@Autowired
	protected MaterialServiceUIModelExtension materialServiceUIModelExtension;

	@Autowired
	protected MaterialActionExecutionProxy materialActionExecutionProxy;

	protected Logger logger = LoggerFactory.getLogger(MaterialEditorController.class);

	public ServiceBasicUtilityController.DocUIModelWithActionRequest getDocUIModelRequest() {
		return new ServiceBasicUtilityController.DocUIModelWithActionRequest(
				MaterialServiceUIModel.class,
				MaterialServiceModel.class, AOID_RESOURCE,
				Material.NODENAME, Material.SENAME, materialServiceUIModelExtension,
				materialManager, MaterialActionLog.NODENAME, materialActionExecutionProxy
		);
	}

	@RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocActionNodeList(String uuid, String actionCode) {
		return serviceBasicUtilityController.getDocActionNodeList(uuid, actionCode, getDocUIModelRequest());
	}

	@RequestMapping(value = "/getSwitchFlag", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getSwitchFlag() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> materialManager.initSwitchMap(lanCode));
	}

	@RequestMapping(value = "/getCargoType", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getCargoType() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> cargoManager.getCargoTypeMap());
	}

	@RequestMapping(value = "/getStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getStatus() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> materialManager.initStatusMap(lanCode));
	}

	@RequestMapping(value = "/getPackageMaterialType", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPackageMaterialType() {
		return serviceBasicUtilityController.getListMeta(() -> materialManager
                        .initRawPackageTypeMap(logonActionController.getClient(), true));
	}

	@RequestMapping(value = "/getSupplyType", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getSupplyType() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> materialManager.initSupplyTypeMap(lanCode));
	}

	@RequestMapping(value = "/getMaterialCategory", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getMaterialCategory() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> materialManager.initMaterialCategoryMap(lanCode));
	}

	@RequestMapping(value = "/getOperationMode", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getOperationMode() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> materialManager.initOperationModeMap(lanCode));
	}

	@RequestMapping(value = "/getStandardUnit", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getStandardUnit() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> standardMaterialUnitManager.getStandardUnitMap(logonActionController.getClient()));
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.saveModuleService(request, getDocUIModelRequest(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService() {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getDocUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						Material.SENAME, Material.NODENAME,
						materialSpecifier), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleFromType", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleFromType(@RequestBody SimpleSEJSONRequest simpleRequest) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getDocUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(Material.SENAME, Material.NODENAME, null,
						MaterialSpecifier.INIT_CONFIGID_newModuleFromType, materialSpecifier, simpleRequest,
						serviceEntityNode -> {
							try {
								return materialManager.processNewMaterialFromType((Material) serviceEntityNode);
							} catch (MaterialException e) {
								throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
							}
						}), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
	public @ResponseBody String executeDocAction(@RequestBody String request) {
		return serviceBasicUtilityController.executeDocActionFramework(request,
				(DocActionNodeProxy.IActionExecutor<MaterialServiceModel>) (materialServiceModel, actionCode, logonInfo) -> {
					try {
						materialActionExecutionProxy.executeActionCore(materialServiceModel,
								actionCode, logonActionController.getSerialLogonInfo());
					} catch (DocActionException e) {
						throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
					}
				}, getDocUIModelRequest());
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest, materialManager);
	}

	private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
		return new DocAttachmentProxy.DocAttachmentProcessPara(materialManager,
				MaterialAttachment.NODENAME, Material.NODENAME, null, null, null);
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

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preLock(String uuid) {
		return serviceBasicUtilityController.preLock(uuid, ISystemActionCode.ACID_EDIT, getDocUIModelRequest());
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
		return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
				getDocUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleViewService(String uuid) {
		return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_VIEW,
				getDocUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleEditService(String uuid) {
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, true,
				getDocUIModelRequest());
	}

	@RequestMapping(value = "/getActionCodeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getActionCodeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> materialActionExecutionProxy.getActionCodeMap(lanCode));
	}

	@RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocActionConfigureList() {
		return serviceBasicUtilityController.getDocActionConfigureListCore(this.materialActionExecutionProxy);
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
