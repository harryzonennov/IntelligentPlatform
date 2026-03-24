package com.company.IntelligentPlatform.logistics.dto;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.logistics.service.*;
import com.company.IntelligentPlatform.logistics.model.PurchaseReturnMaterialItem;
import com.company.IntelligentPlatform.logistics.model.PurchaseReturnMaterialItemAttachment;
import com.company.IntelligentPlatform.logistics.model.PurchaseReturnOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreItemException;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Scope("session")
@Controller(value = "purchaseReturnMaterialItemEditorController")
@RequestMapping(value = "/purchaseReturnMaterialItem")
public class PurchaseReturnMaterialItemEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IServiceModelConstants.PurchaseReturnOrder;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected PurchaseReturnMaterialItemServiceUIModelExtension purchaseReturnMaterialItemServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected PurchaseReturnOrderManager purchaseReturnOrderManager;

	@Autowired
	protected PurchaseReturnMaterialItemManager purchaseReturnMaterialItemManager;

	@Autowired
	protected PurchaseReturnOrderSpecifier purchaseReturnOrderSpecifier;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				PurchaseReturnMaterialItemServiceUIModel.class,
				PurchaseReturnMaterialItemServiceModel.class, AOID_RESOURCE,
				PurchaseReturnMaterialItem.NODENAME,
				PurchaseReturnMaterialItem.SENAME, purchaseReturnOrderSpecifier,
				purchaseReturnOrderManager
		);
	}

	private PurchaseReturnMaterialItemServiceUIModel parseToServiceUIModel(
			String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		classMap.put("purchaseReturnMaterialItemAttachmentUIModelList",
				PurchaseReturnMatItemAttachmentUIModel.class);
		return (PurchaseReturnMaterialItemServiceUIModel) JSONObject
				.toBean(jsonObject, PurchaseReturnMaterialItemServiceUIModel.class,
						classMap);
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
				(request1, client) -> purchaseReturnMaterialItemManager.getPageHeaderModelList(request1, client));
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		PurchaseReturnMaterialItemServiceUIModel purchaseReturnMaterialItemServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				purchaseReturnMaterialItemServiceUIModel,
				purchaseReturnMaterialItemServiceUIModel.getPurchaseReturnMaterialItemUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}
	
	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(PurchaseReturnMaterialItem.SENAME, PurchaseReturnMaterialItem.NODENAME,
						null, PurchaseReturnOrder.NODENAME, request.getBaseUUID(), null, purchaseReturnOrderSpecifier, request, null),
				ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		simpleRequest.setClient(logonActionController.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				purchaseReturnOrderManager);
	}

	@RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String deleteModule(String uuid) {
		return serviceBasicUtilityController.deleteDocMatItem(uuid, ISystemActionCode.ACID_EDIT,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModule(String uuid) {
		return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleEditService(String uuid) {
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, true,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleViewService(String uuid) {
		return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "/checkReturnAmountRequest", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkReturnAmountRequest(
			@RequestBody DocItemRequestJSONModel request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			return docFlowProxy.checkOveramountRequest(request, new DocFlowProxy.OveramountRequest(
					IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER,
					PurchaseReturnOrderException.class,
					PurchaseReturnOrderException.PARA_OVERAMOUNT
			));
		} catch (ServiceEntityConfigureException | ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (MaterialException | DocActionException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (WarehouseStoreItemException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara(){
		return new DocAttachmentProxy.DocAttachmentProcessPara(purchaseReturnOrderManager,
				PurchaseReturnMaterialItemAttachment.NODENAME, PurchaseReturnMaterialItem.NODENAME, null, null,
				null);
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
	public @ResponseBody String deleteAttachment(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.deleteAttachment(request, AOID_RESOURCE,
				genDocAttachmentProcessPara());
	}

	/**
	 * Upload the attachment content information.
	 */
	@RequestMapping(value = "/uploadAttachment", consumes = "multipart/form-data", method = RequestMethod.POST)
	public @ResponseBody String uploadAttachment(HttpServletRequest request) {
		return serviceBasicUtilityController.uploadAttachment(request, AOID_RESOURCE,
				genDocAttachmentProcessPara());
	}

	/**
	 * Upload the attachment text information.
	 */
	@RequestMapping(value = "/uploadAttachmentText", produces = "text/html;charset=UTF-8")
	public @ResponseBody String uploadAttachmentText(
			@RequestBody FileAttachmentTextRequest request) {
		return serviceBasicUtilityController.uploadAttachmentText(request, AOID_RESOURCE,
				genDocAttachmentProcessPara());
	}

	@RequestMapping(value = "/getDocFlowList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocFlowList(String uuid) {
		return serviceBasicUtilityController.getDocFlowList(getServiceUIModelRequest(), uuid,
				ISystemActionCode.ACID_VIEW);
	}

}
