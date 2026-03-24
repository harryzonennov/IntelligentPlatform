package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.service.*;
import com.company.IntelligentPlatform.logistics.model.*;

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

import jakarta.servlet.http.HttpServletRequest;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Scope("session")
@Controller(value = "inboundDeliveryEditorController")
@RequestMapping(value = "/inboundDelivery")
public class InboundDeliveryEditorController extends SEEditorController {

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected InboundDeliveryManager inboundDeliveryManager;

	@Autowired
	protected InboundDeliveryActionExecutionProxy inboundDeliveryActionExecutionProxy;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected InboundItemManager inboundItemManager;

	@Autowired
	protected InboundItemExcelHelper inboundItemExcelHelper;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * constant of authorization object ID inside this editor controller
	 */
	public static final String AOID_RESOURCE = IServiceModelConstants.InboundDelivery;

    @Autowired
    private InboundDeliverySpecifier inboundDeliverySpecifier;

	public ServiceBasicUtilityController.DocUIModelWithActionRequest getDocUIModelRequest() {
		return new ServiceBasicUtilityController.DocUIModelWithActionRequest(
				InboundDeliveryServiceUIModel.class,
				InboundDeliveryServiceModel.class, AOID_RESOURCE,
				InboundDelivery.NODENAME, InboundDelivery.SENAME,
				inboundDeliveryManager, InboundDeliveryActionNode.NODENAME, inboundDeliveryActionExecutionProxy
		);
	}

	@RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocActionNodeList(String uuid, String actionCode) {
		return serviceBasicUtilityController.getDocActionNodeList(uuid, actionCode, getDocUIModelRequest());
	}

	@RequestMapping(value = "/getFreightChargeTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getFreightChargeTypeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> inboundDeliveryManager.initFreightChargeType(lanCode));
	}

	@RequestMapping(value = "/getStatusMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getStatusMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> inboundDeliveryManager.initStatusMap(lanCode));
	}

	@RequestMapping(value = "/getPriorityMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPriorityMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> inboundDeliveryManager.initPriorityCode(lanCode));
	}

	@RequestMapping(value = "/getDocumentTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocumentTypeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> inboundDeliveryManager.initDocumentTypeMap(true,lanCode));
	}

	@RequestMapping(value = "/getPlanCategoryMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPlanCategoryMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> inboundDeliveryManager.initPlanCategoryMap(lanCode));
	}

	@RequestMapping(value = "/uploadItemExcel", consumes = "multipart/form-data", method = RequestMethod.POST)
	public @ResponseBody String uploadItemExcel(
			HttpServletRequest request) {
		return serviceBasicUtilityController.uploadExcelWrapper(new ServiceBasicUtilityController.ExcelUploadRequest(request,
				inboundItemExcelHelper, AOID_RESOURCE, ISystemActionCode.ACID_EDIT,
				(serviceExcelReportResponseModel, baseUUID) -> {
					try {
						inboundItemExcelHelper.updateItemExcel(serviceExcelReportResponseModel, baseUUID,
								(docMatItemUIModel, docMatItem, serialLogonInfo) -> {
									inboundItemManager.convUIToInboundItem((InboundItemUIModel) docMatItemUIModel, (InboundItem) docMatItem);
								}, false, logonActionController.getSerialLogonInfo());
					} catch (ServiceEntityConfigureException e) {
						logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
						throw new ServiceComExecuteException(ServiceComExecuteException.PARA_SYSTEM_ERROR, e.getMessage());
					}
				}));
	}

	@RequestMapping(value = "/initSerialInputBatchModel", produces = "text/html;charset=UTF-8")
	public @ResponseBody String initSerialInputBatchModel(String uuid) {
		return serviceBasicUtilityController.initSerialInputBatchModel(uuid, getDocUIModelRequest(), AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/updateSerialIdToRegProduct", produces = "text/html;charset=UTF-8")
	public @ResponseBody String updateSerialIdToRegProduct(
			@RequestBody String request) {
		return serviceBasicUtilityController.updateSerialIdToRegProduct(request, getDocUIModelRequest(), AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.saveModuleService(request, getDocUIModelRequest(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/getActionCodeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getActionCodeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> inboundDeliveryActionExecutionProxy.getActionCodeMap(lanCode));
	}

	@RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocActionConfigureList() {
		return serviceBasicUtilityController.getDocActionConfigureListCore(this.inboundDeliveryActionExecutionProxy);
	}

	@RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
	public @ResponseBody String executeDocAction(@RequestBody String request) {
		return serviceBasicUtilityController.executeDocActionFramework(request,
				(DocActionNodeProxy.IActionExecutor<InboundDeliveryServiceModel>) (inboundDeliveryServiceModel, actionCode, logonInfo) -> {
					try {
						inboundDeliveryActionExecutionProxy.executeActionCore(inboundDeliveryServiceModel,
								actionCode, logonActionController.getSerialLogonInfo());
					} catch (DocActionException e) {
						throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
					}
				}, getDocUIModelRequest());
	}

	public @RequestMapping(value = "/loadProperTargetDocListBatchGen", produces = "text/html;"
			+ "charset=UTF-8") @ResponseBody String loadProperTargetDocListBatchGen(
			@RequestBody String request) {
		return serviceBasicUtilityController.loadProperTargetDocListBatchGen(request,
				IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY, DeliveryMatItemBatchGenRequest.class, AOID_RESOURCE);
	}

	public @RequestMapping(value = "/generateNextDocBatch", produces = "text/html;"
			+ "charset=UTF-8") @ResponseBody String generateNextDocBatch(
			@RequestBody String request) {
		return serviceBasicUtilityController.genDefNextDocBatchWrapper(request,
				IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY, AOID_RESOURCE, DeliveryMatItemBatchGenRequest.class,
				null);
	}

	private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
		return new DocAttachmentProxy.DocAttachmentProcessPara(inboundDeliveryManager,
				InboundDeliveryAttachment.NODENAME, InboundDelivery.NODENAME, null, null, null);
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


	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getDocUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						InboundDelivery.SENAME, InboundDelivery.NODENAME,
						inboundDeliverySpecifier), ISystemActionCode.ACID_EDIT);
	}

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLockService", produces = "text/html;charset=UTF-8")
	String preLockService(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.preLock(request.getUuid(), ISystemActionCode.ACID_EDIT, getDocUIModelRequest());
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModule(String uuid) {
		return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
				getDocUIModelRequest());
	}

	ServiceBasicUtilityController.IServiceUIModuleExecutor<InboundDeliveryServiceUIModel> getServiceUIModuleExecutor() {
		return (inboundDeliveryServiceUIModel, serviceModule) -> {
			inboundDeliveryManager.postUpdateServiceUIModel(inboundDeliveryServiceUIModel, (InboundDeliveryServiceModel) serviceModule);
			return inboundDeliveryServiceUIModel;
		};
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleViewService(String uuid) {
		return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModuleExecutor(), getDocUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleEditService(String uuid) {
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT,
				true, getServiceUIModuleExecutor(), getDocUIModelRequest());
	}

	@RequestMapping(value = "/exitEditor")
	public String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
