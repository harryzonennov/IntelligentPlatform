package com.company.IntelligentPlatform.logistics.dto;

import jakarta.servlet.http.HttpServletRequest;

import com.company.IntelligentPlatform.logistics.service.*;
import com.company.IntelligentPlatform.logistics.model.*;

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

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;

@Scope("session")
@Controller(value = "inventoryCheckOrderEditorController")
@RequestMapping(value = "/inventoryCheckOrder")
public class InventoryCheckOrderEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IServiceModelConstants.InventoryCheckOrder;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected InventoryCheckOrderServiceUIModelExtension inventoryCheckOrderServiceUIModelExtension;

	@Autowired
	protected InventoryCheckOrderManager inventoryCheckOrderManager;

	@Autowired
	protected InventoryCheckOrderActionExecutionProxy inventoryCheckOrderActionExecutionProxy;

	@Autowired
	protected InventoryCheckOrderSpecifier inventoryCheckOrderSpecifier;

	protected Logger logger = LoggerFactory.getLogger(InventoryCheckOrderEditorController.class);

	public ServiceBasicUtilityController.DocUIModelWithActionRequest getDocUIModelRequest() {
		return new ServiceBasicUtilityController.DocUIModelWithActionRequest(
				InventoryCheckOrderServiceUIModel.class,
				InventoryCheckOrderServiceModel.class, AOID_RESOURCE,
				InventoryCheckOrder.NODENAME, InventoryCheckOrder.SENAME, inventoryCheckOrderServiceUIModelExtension,
				inventoryCheckOrderManager, InventoryCheckOrderActionNode.NODENAME, inventoryCheckOrderActionExecutionProxy
		);
	}

	@RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocActionNodeList(String uuid, String actionCode) {
		return serviceBasicUtilityController.getDocActionNodeList(uuid, actionCode, getDocUIModelRequest());
	}

	@RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocActionConfigureList() {
		return serviceBasicUtilityController.getDocActionConfigureListCore(this.inventoryCheckOrderActionExecutionProxy);
	}

	@RequestMapping(value = "/getActionCodeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getActionCodeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> inventoryCheckOrderActionExecutionProxy.getActionCodeMap(lanCode));
	}

	@RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
	public @ResponseBody String executeDocAction(@RequestBody String request) {
		return serviceBasicUtilityController.executeDocActionFramework(request,
				(DocActionNodeProxy.IActionExecutor<InventoryCheckOrderServiceModel>) (inventoryCheckOrderServiceModel, actionCode, logonInfo) -> {
					try {
						inventoryCheckOrderActionExecutionProxy.executeActionCore(inventoryCheckOrderServiceModel,
								actionCode, logonActionController.getSerialLogonInfo());
					} catch (DocActionException e) {
						throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
					}
				}, getDocUIModelRequest());
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.saveModuleService(request, getDocUIModelRequest(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/initInventoryCheckService", produces = "text/html;charset=UTF-8")
	public String initInventoryCheckService() {
		return serviceBasicUtilityController.getObjectMeta(InitInventoryCheckModel::new, AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody InitInventoryCheckModel initInventoryCheckModel) {
		return serviceBasicUtilityController.newModuleService(getDocUIModelRequest(), () -> {
			try {
                return inventoryCheckOrderManager
						.initInventoryCheckOrder(initInventoryCheckModel,
								logonActionController.getLogonInfo());
			} catch (InventoryCheckException | SearchConfigureException | MaterialException |
					 ServiceEntityInstallationException | AuthorizationException | LogonInfoException |
					 NodeNotFoundException e) {
				throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
			}
		}, ISystemActionCode.ACID_EDIT);
	}


	private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
		return new DocAttachmentProxy.DocAttachmentProcessPara(inventoryCheckOrderManager,
				InventoryCheckAttachment.NODENAME, InventoryCheckOrder.NODENAME, null, null, null);
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
	@RequestMapping(value = "/preLockService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preLockService(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.preLock(request.getUuid(), ISystemActionCode.ACID_EDIT, getDocUIModelRequest());
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
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT,
				true, getDocUIModelRequest());
	}

	@RequestMapping(value = "/getGrossCheckResult", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getGrossCheckResult() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> inventoryCheckOrderManager.initInventoryCheckResultMap(lanCode));
	}

	@RequestMapping(value = "/getPriorityCode", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPriorityCode() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> inventoryCheckOrderManager.initPriorityCodeMap(lanCode));
	}

	@RequestMapping(value = "/getStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getStatus() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> inventoryCheckOrderManager.initStatusMap(lanCode));
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		return super.checkDuplicateIDCore(simpleRequest,
				inventoryCheckOrderManager);
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
