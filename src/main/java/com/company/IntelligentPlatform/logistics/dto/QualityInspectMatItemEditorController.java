package com.company.IntelligentPlatform.logistics.dto;

import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.logistics.service.*;
import com.company.IntelligentPlatform.logistics.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Scope("session")
@Controller(value = "qualityInspectMatItemEditorController")
@RequestMapping(value = "/qualityInspectMatItem")
public class QualityInspectMatItemEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = QualityInspectOrderEditorController.AOID_RESOURCE;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected QualityInspectMatItemServiceUIModelExtension qualityInspectMatItemServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected QualityInspectOrderManager qualityInspectOrderManager;

	@Autowired
	protected SplitMatItemProxy splitMatItemProxy;

	@Autowired
	protected QualityInspectMatItemManager qualityInspectMatItemManager;

	@Autowired
	protected QualityInspectOrderSpecifier qualityInspectOrderSpecifier;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				QualityInspectMatItemServiceUIModel.class,
				QualityInspectMatItemServiceModel.class, AOID_RESOURCE,
				QualityInspectMatItem.NODENAME, QualityInspectMatItem.SENAME,
				qualityInspectOrderSpecifier,
				qualityInspectOrderManager
		);
	}

	private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
		return new DocAttachmentProxy.DocAttachmentProcessPara(qualityInspectOrderManager,
				QualityInsMatItemAttachment.NODENAME, QualityInspectMatItem.NODENAME, null, null, null);
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

	QualityInspectMatItemServiceUIModel parseToServiceUIModel(String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		classMap.put("qualityInspectPropertyItemUIModelList",
				QualityInspectPropertyItemServiceUIModel.class);
		return (QualityInspectMatItemServiceUIModel) JSONObject
				.toBean(jsonObject, QualityInspectMatItemServiceUIModel.class,
						classMap);
	}
	
	private SplitMatItemModel parseToSplitMatItemModelRequest(String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		return (SplitMatItemModel) JSONObject.toBean(
				jsonObject, SplitMatItemModel.class, classMap);
	}
	
	public @RequestMapping(value = "/checkSplitItemService", produces = "text/html;"
			+ "charset=UTF-8") @ResponseBody String checkSplitItemService(
			@RequestBody String request) {
		try {
			SplitMatItemModel splitMatItemModel = parseToSplitMatItemModelRequest(request);
			QualityInspectMatItem qualityInspectMatItem = loadDataByCheckAccess(splitMatItemModel.getItemUUID(),
					false, ISystemActionCode.ACID_EDIT);
			StorageCoreUnit resultUnit = splitMatItemProxy.calculateLeftAmount(splitMatItemModel, qualityInspectMatItem);
			return ServiceJSONParser.genDefOKJSONObject(resultUnit);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (MaterialException | SplitMatItemException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		QualityInspectMatItemServiceUIModel qualityInspectMatItemServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				qualityInspectMatItemServiceUIModel,
				qualityInspectMatItemServiceUIModel.getQualityInspectMatItemUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(QualityInspectMatItem.SENAME, QualityInspectMatItem.NODENAME,
						null, QualityInspectOrder.NODENAME, request.getBaseUUID(), null, qualityInspectOrderSpecifier, request, null),
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

	private QualityInspectMatItem loadDataByCheckAccess(String uuid, boolean lockFlag, String acId) throws AuthorizationException,
			ServiceEntityConfigureException, LogonInfoException {
		return (QualityInspectMatItem) serviceBasicUtilityController.loadDataByCheckAccess(uuid,
				qualityInspectOrderManager, QualityInspectMatItem.NODENAME, AOID_RESOURCE, acId, lockFlag,
				logonActionController.getLogonInfo().getAuthorizationACUnionList());
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModule(String uuid) {
		return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPageHeaderModelList(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
				(request1, client) -> qualityInspectMatItemManager.getPageHeaderModelList(request1, client));

	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleEditService(String uuid) {
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, false,
				getServiceUIModelRequest());
	}
	
	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleViewService(String uuid) {
		return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/getDocFlowList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocFlowList(String uuid) {
		return serviceBasicUtilityController.getDocFlowList(getServiceUIModelRequest(), uuid,
				ISystemActionCode.ACID_VIEW);
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "/getItemCheckStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getItemCheckStatus() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> qualityInspectOrderManager.initCheckStatusMap(lanCode));
	}

	@RequestMapping(value = "/getItemInspectType", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getItemInspectType() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> qualityInspectOrderManager.initInspectTypeMap(lanCode));
	}

}
