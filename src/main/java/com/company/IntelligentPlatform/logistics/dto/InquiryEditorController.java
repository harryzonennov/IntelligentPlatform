package com.company.IntelligentPlatform.logistics.dto;


import jakarta.servlet.http.HttpServletRequest;

import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
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
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Scope("session")
@Controller(value = "inquiryEditorController")
@RequestMapping(value = "/inquiry")
public class InquiryEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IServiceModelConstants.Inquiry;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected InquiryServiceUIModelExtension inquiryServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected InquiryManager inquiryManager;

	@Autowired
	protected InquiryMaterialItemManager inquiryMaterialItemManager;

	@Autowired
	protected InquiryMaterialItemExcelHelper inquiryMaterialItemExcelHelper;

	@Autowired
	protected InquirySpecifier inquirySpecifier;

	@Autowired
	protected InquiryActionExecutionProxy inquiryActionExecutionProxy;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;
	
	Logger logger = LoggerFactory.getLogger(InquiryEditorController.class);

	public ServiceBasicUtilityController.DocUIModelWithActionRequest getDocUIModelRequest() {
		return new ServiceBasicUtilityController.DocUIModelWithActionRequest(
				InquiryServiceUIModel.class,
				InquiryServiceModel.class, AOID_RESOURCE,
				Inquiry.NODENAME, Inquiry.SENAME,
				inquiryManager, InquiryActionNode.NODENAME, inquiryActionExecutionProxy
		);
	}

	private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
		return new DocAttachmentProxy.DocAttachmentProcessPara(inquiryManager,
				InquiryAttachment.NODENAME, Inquiry.NODENAME, null, null, null);
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

	@RequestMapping(value = "/getStatusMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getStatusMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> inquiryManager.initStatus(lanCode));
	}

	@RequestMapping(value = "/getPriorityMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPriorityMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> inquiryManager.initPriorityCode(lanCode));
	}

	@RequestMapping(value = "/getItemStatusMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getItemStatusMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> inquiryManager.initStatus(lanCode));
	}

	public @RequestMapping(value = "/loadProperTargetDocListBatchGen", produces = "text/html;"
			+ "charset=UTF-8") @ResponseBody String loadProperTargetDocListBatchGen(
			@RequestBody String request) {
        return serviceBasicUtilityController.loadProperTargetDocListBatchGen(request,
				IDefDocumentResource.DOCUMENT_TYPE_INQUIRY, DeliveryMatItemBatchGenRequest.class, AOID_RESOURCE);
	}

	public @RequestMapping(value = "/genDefNextDocBatchFromPrevProf", produces = "text/html;"
			+ "charset=UTF-8") @ResponseBody String genDefNextDocBatchFromPrevProf(
			@RequestBody String request) {
        return serviceBasicUtilityController.genDefNextDocBatchFromPrevProf(request,
				IDefDocumentResource.DOCUMENT_TYPE_INQUIRY, AOID_RESOURCE, DeliveryMatItemBatchGenRequest.class,
				null);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.saveModuleService(request, getDocUIModelRequest(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocActionConfigureList() {
		return serviceBasicUtilityController.getDocActionConfigureListCore(this.inquiryActionExecutionProxy);
	}

	@RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
	public @ResponseBody String executeDocAction(@RequestBody String request) {
		return serviceBasicUtilityController.executeDocActionFramework(request,
				(DocActionNodeProxy.IActionExecutor<InquiryServiceModel>) (inquiryServiceModel, actionCode, logonInfo) -> {
					try {
						inquiryActionExecutionProxy.executeActionCore(inquiryServiceModel,
								actionCode, logonActionController.getSerialLogonInfo());
					} catch (DocActionException e) {
						throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
					}
				}, getDocUIModelRequest());
	}

	@RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocActionNodeList(String uuid, String actionCode) {
		return serviceBasicUtilityController.getDocActionNodeList(uuid, actionCode, getDocUIModelRequest());
	}

	@RequestMapping(value = "/uploadItemExcel", consumes = "multipart/form-data", method = RequestMethod.POST)
	public @ResponseBody String uploadItemExcel(
			HttpServletRequest request) {
		return serviceBasicUtilityController.uploadExcelWrapper(new ServiceBasicUtilityController.ExcelUploadRequest(request,
				this.inquiryMaterialItemExcelHelper, AOID_RESOURCE, ISystemActionCode.ACID_EDIT,
				(serviceExcelReportResponseModel, baseUUID) -> {
					try {
						inquiryMaterialItemExcelHelper.updateItemExcel(serviceExcelReportResponseModel, baseUUID,
								(docMatItemUIModel, docMatItem, serialLogonInfo) -> {
									inquiryMaterialItemManager.convUIToInquiryMaterialItem((InquiryMaterialItemUIModel) docMatItemUIModel,
											(InquiryMaterialItem) docMatItem);
								}, false, logonActionController.getSerialLogonInfo());
					} catch (ServiceEntityConfigureException e) {
						logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
						throw new ServiceComExecuteException(ServiceComExecuteException.PARA_SYSTEM_ERROR, e.getMessage());
					}
				}));
	}

	@RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String deleteModule(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.deleteDocMatItem(request.getUuid(), ISystemActionCode.ACID_EDIT,
				getDocUIModelRequest());
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService() {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getDocUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(Inquiry.SENAME, Inquiry.NODENAME, null,
						null, inquirySpecifier, null),
				ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		simpleRequest.setClient(logonActionController.getClient());
		return super.checkDuplicateIDCore(simpleRequest, inquiryManager);
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
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, true,
				getDocUIModelRequest());
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
