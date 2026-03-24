package com.company.IntelligentPlatform.logistics.dto;

import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
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
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Scope("session")
@Controller(value = "qualityInspectOrderEditorController")
@RequestMapping(value = "/qualityInspectOrder")
public class QualityInspectOrderEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IServiceModelConstants.QualityInspectOrder;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected QualityInspectOrderServiceUIModelExtension qualityInspectOrderServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected QualityInspectOrderManager qualityInspectOrderManager;
	
	@Autowired
	protected LogonInfoManager logonInfoManager;
	
	@Autowired
	protected QualityInspectOrderActionExecutionProxy qualityInspectOrderActionExecutionProxy;
	
	protected Logger logger = LoggerFactory.getLogger(QualityInspectOrderEditorController.class);
    @Autowired
    private QualityInspectOrderSpecifier qualityInspectOrderSpecifier;

	private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
		return new DocAttachmentProxy.DocAttachmentProcessPara(qualityInspectOrderManager,
				QualityInsOrderAttachment.NODENAME, QualityInspectOrder.NODENAME, null, null, null);
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

	@RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocActionConfigureList() {
		return serviceBasicUtilityController.getDocActionConfigureListCore(this.qualityInspectOrderActionExecutionProxy);
	}

	protected QualityInspectOrderServiceUIModel parseToServiceUIModel(
			@RequestBody String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		classMap.put("qualityInsOrderAttachmentUIModelList",
				QualityInsOrderAttachmentUIModel.class);
		classMap.put("qualityInspectMatItemUIModelList",
				QualityInspectMatItemServiceUIModel.class);
		classMap.put("qualityInspectPropertyItemUIModelList",
				QualityInspectPropertyItemServiceUIModel.class);
		return (QualityInspectOrderServiceUIModel) JSONObject
				.toBean(jsonObject, QualityInspectOrderServiceUIModel.class,
						classMap);
	}

	private QualityInspectOrderServiceModel parseToServiceModel(String request)
			throws ServiceModuleProxyException, ServiceEntityConfigureException, ServiceUIModuleProxyException {
		QualityInspectOrderServiceUIModel qualityInspectOrderServiceUIModel = this.parseToServiceUIModel(request);
		return
				(QualityInspectOrderServiceModel) qualityInspectOrderManager
						.genServiceModuleFromServiceUIModel(
								QualityInspectOrderServiceModel.class,
								QualityInspectOrderServiceUIModel.class,
								qualityInspectOrderServiceUIModel,
								qualityInspectOrderServiceUIModelExtension);
	}

	public ServiceBasicUtilityController.DocUIModelWithActionRequest getDocUIModelRequest() {
		return new ServiceBasicUtilityController.DocUIModelWithActionRequest(
				QualityInspectOrderServiceUIModel.class,
				QualityInspectOrderServiceModel.class, AOID_RESOURCE,
				QualityInspectOrder.NODENAME, QualityInspectOrder.SENAME,
				qualityInspectOrderManager, QualityInsOrderActionNode.NODENAME, qualityInspectOrderActionExecutionProxy
		);
	}

	@RequestMapping(value = "/getActionCodeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getActionCodeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> qualityInspectOrderActionExecutionProxy.getActionCodeMap(lanCode));
	}

	@RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocActionNodeList(String uuid, String actionCode) {
		return serviceBasicUtilityController.getDocActionNodeList(uuid, actionCode, getDocUIModelRequest());
	}

	@RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
	public @ResponseBody String executeDocAction(@RequestBody String request) {
		return serviceBasicUtilityController.executeDocActionFramework(request,
				(DocActionNodeProxy.IActionExecutor<QualityInspectOrderServiceModel>) (qualityInspectOrderServiceModel, actionCode, logonInfo) -> {
					try {
						qualityInspectOrderActionExecutionProxy.executeActionCore(qualityInspectOrderServiceModel,
								actionCode, logonActionController.getSerialLogonInfo());
					} catch (DocActionException e) {
						throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
					}
				}, getDocUIModelRequest());
	}


	public @RequestMapping(value = "/generateNextDocBatch", produces = "text/html;"
			+ "charset=UTF-8") @ResponseBody String generateNextDocBatch(
			@RequestBody String request) {
		return serviceBasicUtilityController.genDefNextDocBatchWrapper(request,
				IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER, AOID_RESOURCE, DeliveryMatItemBatchGenRequest.class,
				null);
	}


	@RequestMapping(value = "/preCheckSetComplete", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preCheckSetComplete(@RequestBody String request) {
		try {
			return serviceBasicUtilityController.defPreCheckSetStatus(
					new ServiceBasicUtilityController.ExecuteActionRequest(request, AOID_RESOURCE, ISystemActionCode.ACID_EDIT, QualityInspectOrder.SENAME, qualityInspectOrderManager,
							qualityInspectOrderServiceUIModelExtension),
					new ServiceBasicUtilityController.ICheckMessageExecutor<QualityInspectOrderServiceModel, QualityInspectException>() {
						@Override
						public QualityInspectOrderServiceModel parseToServiceModule(String request)
								throws ServiceModuleProxyException, ServiceEntityConfigureException,
								ServiceUIModuleProxyException {
							return parseToServiceModel(request);
						}

						@Override
						public List<SimpleSEMessageResponse> getMessage(QualityInspectOrderServiceModel qualityInspectOrderServiceModel)
								throws DocActionException, QualityInspectException, ServiceModuleProxyException,
								ServiceEntityConfigureException {
							return  qualityInspectOrderManager
									.preCheckSetComplete(qualityInspectOrderServiceModel);
						}

						@Override
						public void throwExceptionCallback(List<SimpleSEMessageResponse> errorMessageList)
								throws QualityInspectException {
							throw new QualityInspectException(
									QualityInspectException.PARA_CHECKITEM_INIT,
									errorMessageList);
						}
					});
		} catch (ServiceEntityException e) {
			if(e instanceof QualityInspectException){
				return ServiceJSONParser.generateErrorJSONMessageArray(e
						.getErrorMessageList());
			}
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.saveModuleService(request, getDocUIModelRequest(), ISystemActionCode.ACID_EDIT);
	}

	ServiceBasicUtilityController.IServiceUIModuleExecutor<QualityInspectOrderServiceUIModel> getServiceUIModuleExecutor() {
		return (qualityInspectOrderServiceUIModel, serviceModule) -> {
			qualityInspectOrderManager.postUpdateServiceUIModel(qualityInspectOrderServiceUIModel, (QualityInspectOrderServiceModel) serviceModule);
			return qualityInspectOrderServiceUIModel;
		};
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

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getDocUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(QualityInspectOrder.SENAME, QualityInspectOrder.NODENAME,
						null, QualityInspectOrder.NODENAME, null, null, qualityInspectOrderSpecifier, request, null),
				ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				qualityInspectOrderManager);
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
				getServiceUIModuleExecutor(), getDocUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleEditService(String uuid) {
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_VIEW,
				true, getServiceUIModuleExecutor(), getDocUIModelRequest());
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "/getCheckStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getCheckStatus() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> qualityInspectOrderManager.initCheckStatusMap(lanCode));
	}

	@RequestMapping(value = "/getCategory", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getCategory() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> qualityInspectOrderManager.initCategoryMap(lanCode));
	}

	@RequestMapping(value = "/getInspectType", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getInspectType() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> qualityInspectOrderManager.initInspectTypeMap(lanCode));
	}

	@RequestMapping(value = "/getStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getStatus() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> qualityInspectOrderManager.initStatusMap(lanCode));
	}

	@RequestMapping(value = "/getPriorityCode", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPriorityCode() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> qualityInspectOrderManager.initPriorityCodeMap(lanCode));
	}

}
