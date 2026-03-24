package com.company.IntelligentPlatform.common.dto;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;

import jakarta.servlet.http.HttpServletRequest;

@Scope("session")
@Controller(value = "materialSKUUnitEditorController")
@RequestMapping(value = "/materialSKUUnit")
public class MaterialSKUUnitEditorController extends SEEditorController {

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected MaterialSKUUnitManager materialSKUUnitManager;
	
	@Autowired
	protected MaterialSKUUnitServiceUIModelExtension materialSKUUnitServiceUIModelExtension;

	public static final String AOID_RESOURCE = MaterialEditorController.AOID_RESOURCE;
    @Autowired
    private MaterialStockKeepUnitSpecifier materialStockKeepUnitSpecifier;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				MaterialSKUUnitServiceUIModel.class,
				MaterialSKUUnitServiceModel.class, AOID_RESOURCE,
				MaterialSKUUnitReference.NODENAME,
				MaterialSKUUnitReference.SENAME, materialSKUUnitServiceUIModelExtension,
				materialStockKeepUnitManager
		);
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
				(request1, client) -> materialSKUUnitManager.getPageHeaderModelList(request1, client));
	}

	private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
		return new DocAttachmentProxy.DocAttachmentProcessPara(materialStockKeepUnitManager,
				MaterialSKUUnitAttachment.NODENAME, MaterialSKUUnitReference.NODENAME, null, null, null);
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


	@RequestMapping(value = "deleteModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String deleteModule(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.deleteModule(request.getUuid(), ISystemActionCode.ACID_EDIT,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						MaterialSKUUnitReference.SENAME, MaterialSKUUnitReference.NODENAME, MaterialStockKeepUnit.NODENAME,
						request.getBaseUUID(),
						materialStockKeepUnitSpecifier), ISystemActionCode.ACID_EDIT);
	}
	
	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModule(String uuid) {
		return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleEditService(String uuid){
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, true,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleViewService(String uuid){
		return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_EDIT,
				getServiceUIModelRequest());
	}

	private MaterialSKUUnitServiceUIModel parseToServiceUIModel(@RequestBody String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		Map<String, Class<?>> classMap = new HashMap<>();
		classMap.put("materialSKUUnitAttachmentUIModelList", MaterialSKUUnitAttachmentUIModel.class);
		return (MaterialSKUUnitServiceUIModel) JSONObject
				.toBean(jsonObject, MaterialSKUUnitServiceUIModel.class, classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		MaterialSKUUnitServiceUIModel materialSKUUnitServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				materialSKUUnitServiceUIModel,
				materialSKUUnitServiceUIModel.getMaterialSKUUnitUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

}
