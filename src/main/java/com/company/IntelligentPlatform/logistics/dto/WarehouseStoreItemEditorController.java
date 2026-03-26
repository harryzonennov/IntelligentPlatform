package com.company.IntelligentPlatform.logistics.dto;

import java.util.*;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.logistics.dto.DeliveryRequestAmountRequest;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreItemManager;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreItemServiceModel;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreSpecifier;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItemAttachment;
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
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import jakarta.servlet.http.HttpServletRequest;

@Scope("session")
@Controller(value = "warehouseStoreItemEditorController")
@RequestMapping(value = "/warehouseStoreItem")
public class WarehouseStoreItemEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_WAREHOUSESTORE;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected WarehouseStoreManager warehouseStoreManager;

	@Autowired
	protected WarehouseStoreItemServiceUIModelExtension warehouseStoreItemServiceUIModelExtension;

	@Autowired
	protected ServiceModuleFactoryInContext serviceModuleFactoryInContext;

	@Autowired
	protected WarehouseStoreCheckProxy warehouseStoreCheckProxy;

	@Autowired
	protected WarehouseStoreItemManager warehouseStoreItemManager;

	@Autowired
	protected WarehouseStoreSpecifier warehouseStoreSpecifier;

	@Autowired
	protected ReserveDocItemProxy reserveDocItemProxy;

	@Autowired
	protected LogonInfoManager logonInfoManager;

	private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
		return new DocAttachmentProxy.DocAttachmentProcessPara(warehouseStoreManager,
				WarehouseStoreItemAttachment.NODENAME, WarehouseStoreItem.NODENAME, null, null, null);
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

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				WarehouseStoreItemServiceUIModel.class,
				WarehouseStoreItemServiceModel.class, AOID_RESOURCE,
				WarehouseStoreItem.NODENAME, WarehouseStoreItem.SENAME, warehouseStoreSpecifier,
				warehouseStoreManager
		);
	}

	@RequestMapping(value = "/getStoreItemStatusMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getStoreItemStatusMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> warehouseStoreItemManager.initItemStatus(lanCode));
	}

	@RequestMapping(value = "/getStoreCheckStatusMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getStoreCheckStatusMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> warehouseStoreCheckProxy.getCheckStatusMap());
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
				(request1, client) -> warehouseStoreItemManager.getPageHeaderModelList(request1, client));
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

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		return super.checkDuplicateIDCore(simpleRequest, warehouseStoreManager);
	}

	public DeliveryRequestAmountRequest parseToRequestAmountRequest(String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes") Map<String, Class> classMap = new HashMap<>();
		classMap.put("uuidList", String.class);
		DeliveryRequestAmountRequest genRequest =
				(DeliveryRequestAmountRequest) JSONObject.toBean(jsonObject, DeliveryRequestAmountRequest.class,
						classMap);
		return genRequest;
	}

	@RequestMapping(value = "/getReservedStatusMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getReservedStatusMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> warehouseStoreItemManager.initReservedStatus(lanCode));
	}

	@RequestMapping(value = "/reserveStoreItem", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String reserveStoreItem(@RequestBody String request) {
		DeliveryRequestAmountRequest deliveryRequestAmountRequest = parseToRequestAmountRequest(request);
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			boolean updateFlag =
					reserveDocItemProxy.reserveStoreItem(deliveryRequestAmountRequest.getReservedMatItemUUID(),
							deliveryRequestAmountRequest.getReservedDocType(),
							IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM,
							deliveryRequestAmountRequest.getBaseUUID(),  logonActionController.getSerialLogonInfo());
			if (updateFlag) {
				return ServiceJSONParser.genSimpleOKResponse();
			}else{
				return ServiceJSONParser.genNotModifyResponse();
			}
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (DocActionException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/freeStoreItem", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String freeStoreItem(@RequestBody String request) {
		DeliveryRequestAmountRequest deliveryRequestAmountRequest = parseToRequestAmountRequest(request);
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			boolean updateFlag =
					reserveDocItemProxy.freeReserve(
							deliveryRequestAmountRequest.getBaseUUID(),
							IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM,
							deliveryRequestAmountRequest.getReservedMatItemUUID(),
							deliveryRequestAmountRequest.getReservedDocType(), logonActionController.getSerialLogonInfo());
			if (updateFlag) {
				return ServiceJSONParser.genSimpleOKResponse();
			}else{
				return ServiceJSONParser.genNotModifyResponse();
			}
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (DocActionException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
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

}
