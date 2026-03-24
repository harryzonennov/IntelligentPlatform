package com.company.IntelligentPlatform.logistics.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.company.IntelligentPlatform.common.model.RegisteredProduct;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import jakarta.servlet.http.HttpServletRequest;

@Scope("session")
@Controller(value = "outboundItemEditorController")
@RequestMapping(value = "/outboundItem")
public class OutboundItemEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IServiceModelConstants.OutboundDelivery;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected OutboundItemManager outboundItemManager;

	@Autowired
	protected OutboundDeliveryWarehouseItemManager outboundDeliveryWarehouseItemManager;

	@Autowired
	protected LogonInfoManager logonInfoManager;

	@Autowired
	protected OutboundDeliverySpecifier outboundDeliverySpecifier;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				OutboundItemServiceUIModel.class,
				OutboundItemServiceModel.class, AOID_RESOURCE,
				OutboundItem.NODENAME,
				OutboundItem.SENAME, outboundDeliverySpecifier,
				outboundDeliveryManager
		);
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
				(request1, client) -> outboundItemManager.getPageHeaderModelList(request1, client));
	}

	protected OutboundItemServiceUIModel parseToServiceUIModel(
			String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		classMap.put("outboundItemAttachmentUIModelList", OutboundItemAttachmentUIModel.class);
        return (OutboundItemServiceUIModel) JSONObject
				.toBean(jsonObject, OutboundItemServiceUIModel.class,
						classMap);
	}

	private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
		return new DocAttachmentProxy.DocAttachmentProcessPara(outboundDeliveryManager,
				OutboundItemAttachment.NODENAME, OutboundItem.NODENAME, null, null, null);
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

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		OutboundItemServiceUIModel outboundItemServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				outboundItemServiceUIModel,
				outboundItemServiceUIModel.getOutboundItemUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						OutboundItem.SENAME, OutboundItem.NODENAME, InboundDelivery.NODENAME,
						request.getBaseUUID(),
						outboundDeliverySpecifier), ISystemActionCode.ACID_EDIT);
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

	//TODO to migrate this
	@RequestMapping(value = "/loadAllSerialIdList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadAllSerialIdList(String uuid) {
		return serviceBasicUtilityController.getListMeta(() -> {
			try {
				OutboundItem outboundItem = null;
				outboundItem = (OutboundItem) outboundDeliveryManager
						.getEntityNodeByUUID(uuid,
								OutboundItem.NODENAME,
								logonActionController.getClient());
				if (outboundItem == null) {
					throw new OutboundDeliveryException(
							OutboundDeliveryException.PARA_NO_OUTBOUND_DELIVERY,
							uuid);
				}
				List<ServiceEntityNode> allRegisteredProductList =
						outboundDeliveryManager.getRegisteredProductListForSerialId(outboundItem);
				if(ServiceCollectionsHelper.checkNullList(allRegisteredProductList)){
					return null;
				}
				return allRegisteredProductList.stream().map(serviceEntityNode -> {
					RegisteredProduct registeredProduct = (RegisteredProduct) serviceEntityNode;
					return registeredProduct.getSerialId();
				}).collect(Collectors.toList());
			} catch (OutboundDeliveryException | ServiceModuleProxyException e) {
				throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
			}
		},  AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		return super.checkDuplicateIDCore(simpleRequest,
				outboundDeliveryManager);
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "/getDocFlowList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocFlowList(String uuid) {
		return serviceBasicUtilityController.getDocFlowList(getServiceUIModelRequest(), uuid,
				ISystemActionCode.ACID_VIEW);
	}
}
