package com.company.IntelligentPlatform.logistics.dto;

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
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import jakarta.servlet.http.HttpServletRequest;

import java.util.*;

@Scope("session")
@Controller(value = "purchaseContractMaterialItemEditorController")
@RequestMapping(value = "/purchaseContractMaterialItem")
public class PurchaseContractMaterialItemEditorController extends
		SEEditorController {

	public static final String AOID_RESOURCE = IServiceModelConstants.PurchaseContract;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected PurchaseContractManager purchaseContractManager;

	@Autowired
	protected SplitMatItemProxy splitMatItemProxy;

	@Autowired
	protected PurchaseContractMaterialItemManager purchaseContractMaterialItemManager;

	@Autowired
	protected PurchaseContractSpecifier purchaseContractSpecifier;

	private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara(){
		return new DocAttachmentProxy.DocAttachmentProcessPara(purchaseContractManager,
				PurchaseContractMaterialItemAttachment.NODENAME, PurchaseContractMaterialItem.NODENAME, null, null,
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
	
	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				PurchaseContractMaterialItemServiceUIModel.class,
				PurchaseContractMaterialItemServiceModel.class, AOID_RESOURCE,
				PurchaseContractMaterialItem.NODENAME,
				PurchaseContractMaterialItem.SENAME, purchaseContractSpecifier,
				purchaseContractManager
		);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.saveModuleService(request, getServiceUIModelRequest(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
				(request1, client) -> purchaseContractMaterialItemManager.getPageHeaderModelList(request1, client));
	}

	public @RequestMapping(value = "/initSplitItemService", produces = "text/html;"
			+ "charset=UTF-8") @ResponseBody String initSplitItemService(
			@RequestBody SimpleSEJSONRequest simpleSEJSONRequest) {
		return serviceBasicUtilityController.getObjectMeta(() -> {
			try {
				PurchaseContractMaterialItem purchaseContractMaterialItem =
						loadDataByCheckAccess(simpleSEJSONRequest.getBaseUUID(), false, ISystemActionCode.ACID_EDIT);
                return splitMatItemProxy
						.initSplitModel(purchaseContractMaterialItem, logonActionController.getLogonInfo());
			} catch (MaterialException | ServiceModuleProxyException e) {
				return ServiceJSONParser.generateSimpleErrorJSON(e
						.getErrorMessage());
			}
        }, AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
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
		return serviceBasicUtilityController.getObjectMeta(() -> {
            try {
				SplitMatItemModel splitMatItemModel = parseToSplitMatItemModelRequest(request);
				PurchaseContractMaterialItem purchaseContractMaterialItem = loadDataByCheckAccess(splitMatItemModel.getItemUUID(), false, ISystemActionCode.ACID_EDIT);
                return splitMatItemProxy
						.calculateLeftAmount(splitMatItemModel,
								purchaseContractMaterialItem);
            } catch (SplitMatItemException | MaterialException e) {
				return ServiceJSONParser.generateSimpleErrorJSON(e
						.getErrorMessage());
            }
        }, AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						PurchaseContractMaterialItem.SENAME, PurchaseContractMaterialItem.NODENAME,
						PurchaseContract.NODENAME, request.getBaseUUID(), null,
						(baseUUID, parentNodeName, client) -> purchaseContractManager.newItemFromMaterialSKU(baseUUID, client)), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				purchaseContractManager);
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
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, false,
				getServiceUIModelRequest());
	}

	private PurchaseContractMaterialItem loadDataByCheckAccess(String uuid, boolean lockFlag, String acId)
			throws AuthorizationException, ServiceEntityConfigureException, LogonInfoException {
		return (PurchaseContractMaterialItem) serviceBasicUtilityController.loadDataByCheckAccess(uuid, getServiceUIModelRequest(), acId, lockFlag,
				logonActionController.getLogonInfo().getAuthorizationACUnionList());
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

	@RequestMapping(value = "/getDocFlowList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocFlowList(String uuid) {
		return serviceBasicUtilityController.getDocFlowList(getServiceUIModelRequest(), uuid,
				ISystemActionCode.ACID_VIEW);
	}

}
