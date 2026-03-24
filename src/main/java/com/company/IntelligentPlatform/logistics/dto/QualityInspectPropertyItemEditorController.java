package com.company.IntelligentPlatform.logistics.dto;

import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.logistics.service.QualityInspectOrderManager;
import com.company.IntelligentPlatform.logistics.service.QualityInspectOrderSpecifier;
import com.company.IntelligentPlatform.logistics.service.QualityInspectPropertyItemManager;
import com.company.IntelligentPlatform.logistics.service.QualityInspectPropertyItemServiceModel;
import com.company.IntelligentPlatform.logistics.model.*;

import com.company.IntelligentPlatform.logistics.model.QualityInspectPropertyItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;

@Scope("session")
@Controller(value = "qualityInspectPropertyItemEditorController")
@RequestMapping(value = "/qualityInspectPropertyItem")
public class QualityInspectPropertyItemEditorController extends
		SEEditorController {

	public static final String AOID_RESOURCE = QualityInspectOrderEditorController.AOID_RESOURCE;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected QualityInspectPropertyItemServiceUIModelExtension qualityInspectPropertyItemServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected QualityInspectOrderManager qualityInspectOrderManager;

	@Autowired
	protected QualityInspectOrderSpecifier qualityInspectOrderSpecifier;

	@Autowired
	protected QualityInspectPropertyItemManager qualityInspectPropertyItemManager;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				QualityInspectPropertyItemServiceUIModel.class,
				QualityInspectPropertyItemServiceModel.class, AOID_RESOURCE,
				QualityInspectPropertyItem.NODENAME,
				QualityInspectPropertyItem.SENAME, qualityInspectOrderSpecifier,
				qualityInspectOrderManager
		);
	}

	private QualityInspectPropertyItemServiceUIModel parseToServiceUIModel(String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes") Map<String, Class> classMap = new HashMap<>();
		return (QualityInspectPropertyItemServiceUIModel) JSONObject.toBean(jsonObject,
				QualityInspectPropertyItemServiceUIModel.class, classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		QualityInspectPropertyItemServiceUIModel qualityInspectPropertyItemServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				qualityInspectPropertyItemServiceUIModel,
				qualityInspectPropertyItemServiceUIModel.getQualityInspectPropertyItemUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						QualityInspectPropertyItem.SENAME, QualityInspectPropertyItem.NODENAME, QualityInspectMatItem.NODENAME,
						request.getBaseUUID(),
						null), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
                (request1, client) -> qualityInspectPropertyItemManager.getPageHeaderModelList(request1, client));
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModule(String uuid) {
		return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
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


}
