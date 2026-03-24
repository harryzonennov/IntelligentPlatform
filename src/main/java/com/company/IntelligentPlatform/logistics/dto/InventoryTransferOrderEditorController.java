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

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

@Scope("session")
@Controller(value = "inventoryTransferOrderEditorController")
@RequestMapping(value = "/inventoryTransferOrder")
public class InventoryTransferOrderEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IServiceModelConstants.InventoryTransferOrder;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected InventoryTransferOrderManager inventoryTransferOrderManager;

	@Autowired
	protected LogonUserManager logonUserManager;

	@Autowired
	protected InventoryTransferOrderActionExecutionProxy inventoryTransferOrderActionExecutionProxy;

	@Autowired
	protected InventoryTransferOrderSpecifier inventoryTransferOrderSpecifier;

	@Autowired
	protected InventoryTransferOrderServiceUIModelExtension inventoryTransferOrderServiceUIModelExtension;

	protected Logger logger = LoggerFactory.getLogger(InventoryTransferOrderEditorController.class);
	
	public ServiceBasicUtilityController.DocUIModelWithActionRequest getDocUIModelRequest() {
		return new ServiceBasicUtilityController.DocUIModelWithActionRequest(
				InventoryTransferOrderServiceUIModel.class,
				InventoryTransferOrderServiceModel.class, AOID_RESOURCE,
				InventoryTransferOrder.NODENAME, InventoryTransferOrder.SENAME, inventoryTransferOrderServiceUIModelExtension,
				inventoryTransferOrderManager, InventoryTransferOrderActionNode.NODENAME, inventoryTransferOrderActionExecutionProxy
		);
	}

	@RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocActionNodeList(String uuid, String actionCode) {
		return serviceBasicUtilityController.getDocActionNodeList(uuid, actionCode, getDocUIModelRequest());
	}

	@RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocActionConfigureList() {
		return serviceBasicUtilityController.getDocActionConfigureListCore(this.inventoryTransferOrderActionExecutionProxy);
	}

	@RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
	public @ResponseBody String executeDocAction(@RequestBody String request) {
		return serviceBasicUtilityController.executeDocActionFramework(request,
				(DocActionNodeProxy.IActionExecutor<InventoryTransferOrderServiceModel>) (inventoryTransferOrderServiceModel, actionCode, logonInfo) -> {
					try {
						inventoryTransferOrderActionExecutionProxy.executeActionCore(inventoryTransferOrderServiceModel,
								actionCode, logonActionController.getSerialLogonInfo());
					} catch (DocActionException e) {
						throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
					}
				}, getDocUIModelRequest());
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String saveModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.saveModuleService(request, getDocUIModelRequest(), ISystemActionCode.ACID_EDIT);
	}

	@Deprecated
	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String newModuleService(InventoryTransferMatItemBatchGenRequest inventoryTransferMatItemBatchGenRequest) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getDocUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						InventoryTransferOrder.SENAME, InventoryTransferOrder.NODENAME,
						inventoryTransferOrderSpecifier), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModule(String uuid) {
		return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
				getDocUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleEditService(String uuid) {
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT,
				true, getDocUIModelRequest());
	}

	public @RequestMapping(value = "/generateNextDocBatch", produces = "text/html;"
			+ "charset=UTF-8") @ResponseBody String generateNextDocBatch(
			@RequestBody String request) {
		return serviceBasicUtilityController.genDefNextDocBatchWrapper(request,
				IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_TRANSFER, AOID_RESOURCE, InventoryTransferMatItemBatchGenRequest.class,
				null);
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleViewService(String uuid) {
		return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_VIEW,
				getDocUIModelRequest());
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String checkDuplicateID(@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest, inventoryTransferOrderManager);
	}

	private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
		return new DocAttachmentProxy.DocAttachmentProcessPara(inventoryTransferOrderManager,
				InventoryTransferOrderAttachment.NODENAME, InventoryTransferOrder.NODENAME, null, null, null);
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
	public @ResponseBody
	String preLockService(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.preLock(request.getUuid(), ISystemActionCode.ACID_EDIT, getDocUIModelRequest());
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String exitEditor(@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "/getStatusMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getStatusMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> inventoryTransferOrderManager.initStatusMap(lanCode));
	}

	@RequestMapping(value = "/getPriorityMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPriorityMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> inventoryTransferOrderManager.initPriorityCode(lanCode));
	}

	@RequestMapping(value = "/getActionCodeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getActionCodeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> inventoryTransferOrderActionExecutionProxy.getActionCodeMap(lanCode));
	}

	@RequestMapping(value = "/getDocumentTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getDocumentTypeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> inventoryTransferOrderManager.getDocumentTypeMap(lanCode));
	}

}
