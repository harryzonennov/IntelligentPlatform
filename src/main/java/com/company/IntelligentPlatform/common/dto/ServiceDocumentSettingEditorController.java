package com.company.IntelligentPlatform.common.dto;

import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

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
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.ServiceDocumentSettingManager;
import com.company.IntelligentPlatform.common.service.ServiceDocumentSettingServiceModel;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.*;

@Scope("session")
@Controller(value = "serviceDocumentSettingEditorController")
@RequestMapping(value = "/serviceDocumentSetting")
public class ServiceDocumentSettingEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceDocumentSettingServiceUIModelExtension serviceDocumentSettingServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected ServiceDocumentSettingManager serviceDocumentSettingManager;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				ServiceDocumentSettingServiceUIModel.class,
				ServiceDocumentSettingServiceModel.class, AOID_RESOURCE,
				ServiceDocumentSetting.NODENAME,
				ServiceDocumentSetting.SENAME, serviceDocumentSettingServiceUIModelExtension,
				serviceDocumentSettingManager
		);
	}

	@RequestMapping(value = "/getDocumentTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocumentTypeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> serviceDocumentComProxy.getExtendDocumentTypeMap(lanCode));
	}

	@RequestMapping(value = "/getStandardDocumentTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getStandardDocumentTypeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> serviceDocumentComProxy.getDocumentTypeMap(true, lanCode));
		
	}

	private DocAttachmentProxy.DocAttachmentProcessPara genAttachmentProcessPara() {
		return new DocAttachmentProxy.DocAttachmentProcessPara(serviceDocumentSettingManager,
				ServiceDocumentReportTemplate.NODENAME, ServiceDocumentSetting.NODENAME, null, null, null);
	}

	/**
	 * load the attachment content to consumer.
	 */
	@RequestMapping(value = "/loadAttachment")
	public ResponseEntity<byte[]> loadAttachment(String uuid) {
		return serviceBasicUtilityController.loadAttachment(uuid, AOID_RESOURCE,
				genAttachmentProcessPara());
	}

	/**
	 * Delete attachment
	 */
	@RequestMapping(value = "/deleteAttachment", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String deleteAttachment(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.deleteAttachment(request, AOID_RESOURCE,
				genAttachmentProcessPara());
	}

	/**
	 * Upload the attachment content information.
	 */
	@RequestMapping(value = "/uploadAttachment", consumes = "multipart/form-data", method = RequestMethod.POST)
	public @ResponseBody
	String uploadAttachment(HttpServletRequest request) {
		return serviceBasicUtilityController.uploadAttachment(request, AOID_RESOURCE,
				genAttachmentProcessPara());
	}

	/**
	 * Upload the attachment text information.
	 */
	@RequestMapping(value = "/uploadAttachmentText", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String uploadAttachmentText(
			@RequestBody FileAttachmentTextRequest request) {
		return serviceBasicUtilityController.uploadAttachmentText(request, AOID_RESOURCE,
				genAttachmentProcessPara());
	}

	private DocAttachmentProxy.DocAttachmentProcessPara genExcelUploadTemplateProcessPara() {
		return new DocAttachmentProxy.DocAttachmentProcessPara(serviceDocumentSettingManager,
				ServiceDocExcelUploadTemplate.NODENAME, ServiceDocumentSetting.NODENAME, null, null, null);
	}

	/**
	 * load the attachment content to consumer.
	 */
	@RequestMapping(value = "/loadExcelUploadTemplate")
	public ResponseEntity<byte[]> loadExcelUploadTemplate(String uuid) {
		return serviceBasicUtilityController.loadAttachment(uuid, AOID_RESOURCE,
				genExcelUploadTemplateProcessPara());
	}

	/**
	 * Delete attachment
	 */
	@RequestMapping(value = "/deleteExcelUploadTemplate", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String deleteExcelUploadTemplate(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.deleteAttachment(request, AOID_RESOURCE,
				genExcelUploadTemplateProcessPara());
	}

	/**
	 * Upload the attachment content information.
	 */
	@RequestMapping(value = "/uploadExcelUpload", consumes = "multipart/form-data", method = RequestMethod.POST)
	public @ResponseBody
	String uploadExcelUpload(HttpServletRequest request) {
		return serviceBasicUtilityController.uploadAttachment(request, AOID_RESOURCE,
				genExcelUploadTemplateProcessPara());
	}

	/**
	 * Upload the attachment text information.
	 */
	@RequestMapping(value = "/uploadExcelUploadText", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String uploadExcelUploadText(
			@RequestBody FileAttachmentTextRequest request) {
		return serviceBasicUtilityController.uploadAttachmentText(request, AOID_RESOURCE,
				genExcelUploadTemplateProcessPara());
	}

	private DocAttachmentProxy.DocAttachmentProcessPara genExcelDownloadTemplateProcessPara() {
		return new DocAttachmentProxy.DocAttachmentProcessPara(serviceDocumentSettingManager,
				ServiceDocExcelDownloadTemplate.NODENAME, ServiceDocumentSetting.NODENAME, null, null, null);
	}

	/**
	 * load the attachment content to consumer.
	 */
	@RequestMapping(value = "/loadExcelDownloadTemplate")
	public ResponseEntity<byte[]> loadExcelDownloadTemplate(String uuid) {
		return serviceBasicUtilityController.loadAttachment(uuid, AOID_RESOURCE,
				genExcelDownloadTemplateProcessPara());
	}

	/**
	 * Delete attachment
	 */
	@RequestMapping(value = "/deleteExcelDownloadTemplate", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String deleteExcelDownloadTemplate(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.deleteAttachment(request, AOID_RESOURCE,
				genExcelDownloadTemplateProcessPara());
	}

	/**
	 * Upload the attachment content information.
	 */
	@RequestMapping(value = "/uploadExcelDownload", consumes = "multipart/form-data", method = RequestMethod.POST)
	public @ResponseBody
	String uploadExcelDownload(HttpServletRequest request) {
		return serviceBasicUtilityController.uploadAttachment(request, AOID_RESOURCE,
				genExcelDownloadTemplateProcessPara());
	}

	/**
	 * Upload the attachment text information.
	 */
	@RequestMapping(value = "/uploadExcelDownloadText", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String uploadExcelDownloadText(
			@RequestBody FileAttachmentTextRequest request) {
		return serviceBasicUtilityController.uploadAttachmentText(request, AOID_RESOURCE,
				genExcelDownloadTemplateProcessPara());
	}

	private ServiceDocumentSettingServiceUIModel parseToServiceUIModel(
			String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		classMap.put("serviceDocumentReportTemplateUIModelList",
				ServiceDocumentReportTemplateUIModel.class);
		classMap.put("serviceDocExcelUploadTemplateUIModelList",
				ServiceDocExcelUploadTemplateUIModel.class);
		classMap.put("serviceDocExcelDownloadTemplateUIModelList",
				ServiceDocExcelDownloadTemplateUIModel.class);
		classMap.put("serviceCrossDocConfigureServiceUIModelList",
				ServiceCrossDocConfigureServiceUIModel.class);
		classMap.put("serviceDocActionConfigureUIModelList",
				ServiceDocActionConfigureServiceUIModel.class);
		classMap.put("serviceDocActConfigureItemUIModelList",
				ServiceDocActConfigureItemUIModel.class);
		classMap.put("serviceCrossDocEventMonitorUIModelList",
				ServiceCrossDocEventMonitorUIModel.class);
        return (ServiceDocumentSettingServiceUIModel) JSONObject
				.toBean(jsonObject, ServiceDocumentSettingServiceUIModel.class,
						classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		ServiceDocumentSettingServiceUIModel serviceDocumentSettingServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				serviceDocumentSettingServiceUIModel,
				serviceDocumentSettingServiceUIModel.getServiceDocumentSettingUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService() {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						ServiceDocumentSetting.SENAME, ServiceDocumentSetting.NODENAME,
						null), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				serviceDocumentSettingManager);
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

}
