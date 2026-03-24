package com.company.IntelligentPlatform.logistics.dto;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.logistics.service.InventoryCheckItemServiceModel;
import com.company.IntelligentPlatform.logistics.service.InventoryCheckException;
import com.company.IntelligentPlatform.logistics.service.InventoryCheckItemManager;
import com.company.IntelligentPlatform.logistics.service.InventoryCheckOrderManager;
import com.company.IntelligentPlatform.logistics.model.InboundDelivery;
import com.company.IntelligentPlatform.logistics.model.InventoryCheckItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;

@Scope("session")
@Controller(value = "inventoryCheckItemEditorController")
@RequestMapping(value = "/inventoryCheckItem")
public class InventoryCheckItemEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = InventoryCheckOrderEditorController.AOID_RESOURCE;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected InventoryCheckOrderManager inventoryCheckOrderManager;

	@Autowired
	protected InventoryCheckItemManager inventoryCheckItemManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected InventoryCheckItemServiceUIModelExtension inventoryCheckItemServiceUIModelExtension;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				InventoryCheckItemServiceUIModel.class,
				InventoryCheckItemServiceModel.class, AOID_RESOURCE,
				InventoryCheckItem.NODENAME, InventoryCheckItem.SENAME, inventoryCheckItemServiceUIModelExtension,
				inventoryCheckOrderManager
		);
	}
	
	@RequestMapping(value = "/getCheckResult", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getCheckResult() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> inventoryCheckOrderManager.initInventoryCheckResultMap(lanCode));
	}

	@RequestMapping(value = "/refreshUpdateAmount", produces = "text/html;charset=UTF-8")
	public @ResponseBody String refreshUpdateAmount(@RequestBody String request) {
		InventoryCheckItemServiceUIModel inventoryCheckItemServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				inventoryCheckItemServiceUIModel, null,
				(ServiceBasicUtilityController.IGetServiceModuleExecutor<InventoryCheckItemServiceModel>) inventoryCheckItemServiceModel -> {
                    try {
						InventoryCheckItem inventoryCheckItem = inventoryCheckItemServiceModel.getInventoryCheckItem();
						StorageCoreUnit storageCoreUnit1 = new StorageCoreUnit(
								inventoryCheckItem.getRefMaterialSKUUUID(),
								inventoryCheckItem.getResultUnitUUID(),
								inventoryCheckItem.getResultAmount());
						StorageCoreUnit storageCoreUnit2 = new StorageCoreUnit(
								inventoryCheckItem.getRefMaterialSKUUUID(),
								inventoryCheckItem.getRefUnitUUID(),
								inventoryCheckItem.getAmount());
						double updateDeclaredValue = inventoryCheckItem
								.getResultDeclaredValue()
								- inventoryCheckItem.getDeclaredValue();
						StorageCoreUnit updateUnit = materialStockKeepUnitManager
                                .mergeStorageUnitCore(storageCoreUnit1, storageCoreUnit2,
                                        StorageCoreUnit.OPERATOR_MINUS,
                                        logonActionController.getClient());
						inventoryCheckItem.setUpdateAmount(updateUnit.getAmount());
						inventoryCheckItem.setUpdateUnitUUID(updateUnit.getRefUnitUUID());
						inventoryCheckItem.setUpdateDeclaredValue(updateDeclaredValue);
						inventoryCheckOrderManager
								.adjustComItemCheckStatus(inventoryCheckItem);
						return inventoryCheckItemServiceModel;
                    } catch (MaterialException e) {
                        throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                    }
				}, null,
				inventoryCheckItemServiceUIModel.getInventoryCheckItemUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}


	private InventoryCheckItemServiceUIModel parseToServiceUIModel(String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes") Map<String, Class> classMap = new HashMap<>();
		classMap.put("inventoryCheckItemAttachmentUIModelList", InventoryCheckItemAttachmentUIModel.class);
		return (InventoryCheckItemServiceUIModel) JSONObject.toBean(jsonObject,
				InventoryCheckItemServiceUIModel.class, classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		InventoryCheckItemServiceUIModel inventoryCheckItemServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				inventoryCheckItemServiceUIModel,
				inventoryCheckItemServiceUIModel.getInventoryCheckItemUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						InventoryCheckItem.SENAME, InventoryCheckItem.NODENAME, InboundDelivery.NODENAME,
						request.getBaseUUID(),
						null), ISystemActionCode.ACID_EDIT);
	}


	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
				(request1, client) -> inventoryCheckItemManager.getPageHeaderModelList(request1, client));
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

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		return super.checkDuplicateIDCore(simpleRequest,
				inventoryCheckOrderManager);
	}

	@RequestMapping(value = "/updateCheckSimModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String updateCheckSimModule(
			@RequestBody InventoryCheckItemSimModel inventoryCheckItemSimModel) {
		return serviceBasicUtilityController.voidExecuteWrapper(() -> {
			try {
				InventoryCheckItem inventoryCheckItem = (InventoryCheckItem) inventoryCheckOrderManager
						.getEntityNodeByUUID(
								inventoryCheckItemSimModel.getItemUUID(),
								InventoryCheckItem.NODENAME, logonActionController.getClient());
				if (inventoryCheckItem != null) {
					InventoryCheckItem inventoryCheckItemBack = (InventoryCheckItem) inventoryCheckItem
							.clone();
					inventoryCheckItem.setResultAmount(inventoryCheckItemSimModel
							.getResultAmount());
					inventoryCheckItem
							.setResultDeclaredValue(inventoryCheckItemSimModel
									.getResultDeclaredValue());
					inventoryCheckItem.setResultUnitUUID(inventoryCheckItemSimModel
							.getResultUnitUUID());
					// Adjust update value and item check status
					inventoryCheckOrderManager
							.adjustComItemCheckStatus(inventoryCheckItem);
					inventoryCheckOrderManager.updateSENode(inventoryCheckItem,
							inventoryCheckItemBack, logonActionController.getResUserUUID(),
							logonActionController.getClient());
				} else {
					throw new InventoryCheckException(
							InventoryCheckException.PARA_SYSTEM_ERROR,
							"no check item");
				}
			} catch (MaterialException | InventoryCheckException e) {
				throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
			}
        }, AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
	}

}
