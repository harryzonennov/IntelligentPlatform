package com.company.IntelligentPlatform.logistics.dto;

import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.dto.RegisteredProductBatchGenRequest;
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

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Scope("session")
@Controller(value = "purchaseContractEditorController")
@RequestMapping(value = "/purchaseContract")
public class PurchaseContractEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IServiceModelConstants.PurchaseContract;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected PurchaseContractManager purchaseContractManager;

	@Autowired
	protected PurchaseContractMaterialItemManager purchaseContractMaterialItemManager;

	@Autowired
	protected PurchaseContractMaterialItemExcelHelper purchaseContractMaterialItemExcelHelper;

	@Autowired
	protected PurchaseContractActionExecutionProxy purchaseContractActionExecutionProxy;

	protected Logger logger = LoggerFactory.getLogger(PurchaseContractEditorController.class);

    @Autowired
    private PurchaseContractSpecifier purchaseContractSpecifier;

	@RequestMapping(value = "/getStatusMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getStatusMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> purchaseContractManager.initStatus(lanCode));
	}

	@RequestMapping(value = "/getPriorityMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPriorityMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> purchaseContractManager.initPriorityCode(lanCode));
	}

	@RequestMapping(value = "/getItemStatusMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getItemStatusMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> purchaseContractManager.initItemStatus(lanCode));
	}

	public ServiceBasicUtilityController.DocUIModelWithActionRequest getDocUIModelRequest() {
		return new ServiceBasicUtilityController.DocUIModelWithActionRequest(
				PurchaseContractServiceUIModel.class,
				PurchaseContractServiceModel.class, AOID_RESOURCE,
				PurchaseContract.NODENAME, PurchaseContract.SENAME,
				purchaseContractManager, PurchaseContractActionNode.NODENAME, purchaseContractActionExecutionProxy
		);
	}

	@RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getDocActionNodeList(String uuid, String actionCode) {
		return serviceBasicUtilityController.getDocActionNodeList(uuid, actionCode, getDocUIModelRequest());
	}

	private SplitMatItemModel parseToSplitMatItemModelRequest(String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		return (SplitMatItemModel) JSONObject.toBean(
				jsonObject, SplitMatItemModel.class, classMap);
	}

	public @RequestMapping(value = "/splitItemService", produces = "text/html;"
			+ "charset=UTF-8") @ResponseBody String splitItemService(
			@RequestBody String request) {
		return serviceBasicUtilityController.getObjectMeta(() -> {
			try {
				SplitMatItemModel splitMatItemModel = parseToSplitMatItemModelRequest(request);
				PurchaseContractMaterialItem purchaseContractMaterialItem = (PurchaseContractMaterialItem) purchaseContractManager
						.getEntityNodeByUUID(splitMatItemModel.getItemUUID(),
								PurchaseContractMaterialItem.NODENAME,
								logonActionController.getClient());
				purchaseContractManager.splitItemService(splitMatItemModel,
						purchaseContractMaterialItem, logonActionController.getResUserUUID(),
						logonActionController.getResOrgUUID());
				PurchaseContract purchaseContract = (PurchaseContract) purchaseContractManager.getEntityNodeByUUID(purchaseContractMaterialItem.getParentNodeUUID(),
						PurchaseContract.NODENAME, logonActionController.getClient());
				return refreshLoadServiceUIModel(
						purchaseContract, ISystemActionCode.ACID_EDIT);
			} catch (MaterialException | PurchaseContractException | SplitMatItemException | ServiceUIModuleProxyException | AuthorizationException | ServiceModuleProxyException | LogonInfoException e) {
				throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
			}
		}, AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String saveModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.saveModuleService(request, getDocUIModelRequest(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocActionConfigureList() {
		return serviceBasicUtilityController.getDocActionConfigureListCore(this.purchaseContractActionExecutionProxy);
	}

	@RequestMapping(value = "/getActionCodeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getActionCodeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> purchaseContractActionExecutionProxy.getActionCodeMap(lanCode));
	}

	public @RequestMapping(value = "/genDefNextDocBatchFromPrevProf", produces = "text/html;"
			+ "charset=UTF-8") @ResponseBody String genDefNextDocBatchFromPrevProf(
			@RequestBody String request) {
		return serviceBasicUtilityController.genDefNextDocBatchFromPrevProf(request,
				IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT, AOID_RESOURCE, DeliveryMatItemBatchGenRequest.class,
				null);
	}

	@RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
	public @ResponseBody String executeDocAction(@RequestBody String request) {
		return serviceBasicUtilityController.executeDocActionFramework(request,
				(DocActionNodeProxy.IActionExecutor<PurchaseContractServiceModel>) (purchaseContractServiceModel, actionCode, logonInfo) -> {
					try {
						purchaseContractActionExecutionProxy.executeActionCore(purchaseContractServiceModel,
								actionCode, logonActionController.getSerialLogonInfo());
					} catch (DocActionException e) {
						throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
					}
				}, getDocUIModelRequest());
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String newModuleService() {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getDocUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(PurchaseContract.SENAME, PurchaseContract.NODENAME, null,
						null, purchaseContractSpecifier, null),
				ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newRegProdItemService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newRegProdItemService(
			@RequestBody String request) {
		return serviceBasicUtilityController.voidExecuteWrapper(() -> {
			try {
				RegisteredProductBatchGenRequest registeredProductBatchGenRequest = ServiceBasicUtilityController.parseRequestWrapper(request, RegisteredProductBatchGenRequest.class);
				purchaseContractManager.newRegProdItemWrapper(registeredProductBatchGenRequest,
						registeredProductBatchGenRequest.getRefMaterialSKUUUID(),
						registeredProductBatchGenRequest.getSerialIdList(),
						logonActionController
								.getOrganization(),
						logonActionController.getSerialLogonInfo());
			} catch (MaterialException | PurchaseContractException e) {
				throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
			}
        }, AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/uploadItemExcel", consumes = "multipart/form-data", method = RequestMethod.POST)
	public @ResponseBody String uploadItemExcel(
			HttpServletRequest request) {
		return serviceBasicUtilityController.uploadExcelWrapper(new ServiceBasicUtilityController.ExcelUploadRequest(request,
				this.purchaseContractMaterialItemExcelHelper, AOID_RESOURCE, ISystemActionCode.ACID_EXCEL,
				(serviceExcelReportResponseModel, baseUUID) -> {
					try {
						purchaseContractMaterialItemExcelHelper.updateItemExcel(serviceExcelReportResponseModel, baseUUID,
								(docMatItemUIModel, docMatItem, serialLogonInfo) -> {
									purchaseContractMaterialItemManager.convUIToPurchaseContractMaterialItem((PurchaseContractMaterialItemUIModel) docMatItemUIModel,
											(PurchaseContractMaterialItem) docMatItem);
								}, false, logonActionController.getSerialLogonInfo());
					} catch (ServiceEntityConfigureException e) {
						logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
						throw new ServiceComExecuteException(ServiceComExecuteException.PARA_SYSTEM_ERROR, e.getMessage());
					}
				}));
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		simpleRequest.setClient(logonActionController.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				purchaseContractManager);
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

	private PurchaseContractServiceUIModel refreshLoadServiceUIModel(
			PurchaseContract purchaseContract, String acId)
            throws ServiceEntityConfigureException,
            ServiceModuleProxyException, ServiceUIModuleProxyException,
            LogonInfoException, AuthorizationException, DocActionException {
		return (PurchaseContractServiceUIModel) serviceBasicUtilityController.refreshLoadServiceUIModel(
				getDocUIModelRequest(),
				purchaseContract, acId);
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

	public @RequestMapping(value = "/loadProperTargetDocListBatchGen", produces = "text/html;"
			+ "charset=UTF-8") @ResponseBody String loadProperTargetDocListBatchGen(
			@RequestBody String request) {
		return serviceBasicUtilityController.loadProperTargetDocListBatchGen(request,
				IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT, DeliveryMatItemBatchGenRequest.class, AOID_RESOURCE);
	}

	public @RequestMapping(value = "/loadProperTargetDocListBatchGenFromPrevDoc", produces = "text/html;"
			+ "charset=UTF-8") @ResponseBody String loadProperTargetDocListBatchGenFromPrevDoc(
			@RequestBody String request) {
		return serviceBasicUtilityController.loadProperTargetDocListBatchGenFromPrevDoc(request,
				IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT, DeliveryMatItemBatchGenRequest.class, AOID_RESOURCE);
	}

	public @RequestMapping(value = "/generateNextDocBatch", produces = "text/html;"
			+ "charset=UTF-8") @ResponseBody String generateNextDocBatch(
			@RequestBody String request) {
		return serviceBasicUtilityController.genDefNextDocBatchWrapper(request,
			IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT, AOID_RESOURCE, DeliveryMatItemBatchGenRequest.class,
				null);
	}

	public @RequestMapping(value = "/mergeDocBatch", produces = "text/html;"
			+ "charset=UTF-8") @ResponseBody String mergeDocBatch(
			@RequestBody String request) {
		return serviceBasicUtilityController.mergeDocBatchWrapper(request,
				IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT,
				AOID_RESOURCE, DocumentMatItemBatchGenRequest.class, null);
	}

	private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
		return new DocAttachmentProxy.DocAttachmentProcessPara(purchaseContractManager,
				PurchaseContractAttachment.NODENAME, PurchaseContract.NODENAME, null, null, null);
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

}
