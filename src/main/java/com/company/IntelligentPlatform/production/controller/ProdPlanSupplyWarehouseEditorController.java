package com.company.IntelligentPlatform.production.controller;

import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.production.dto.*;
import com.company.IntelligentPlatform.production.service.BillOfMaterialOrderManager;
import com.company.IntelligentPlatform.production.service.ProductionPlanManager;
import com.company.IntelligentPlatform.production.model.ProdPlanSupplyWarehouse;

import com.company.IntelligentPlatform.production.model.ProductionPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "prodPlanSupplyWarehouseEditorController")
@RequestMapping(value = "/prodPlanSupplyWarehouse")
public class ProdPlanSupplyWarehouseEditorController extends
		SEEditorController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_PRODUCTIONORDER;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected ProdPlanSupplyWarehouseServiceUIModelExtension prodPlanSupplyWarehouseServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ProductionPlanManager productionPlanManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	@Autowired
	protected WarehouseManager warehouseManager;

	protected ProdPlanSupplyWarehouseUIModel parseToUIModel(@RequestBody String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		return (ProdPlanSupplyWarehouseUIModel) JSONObject
				.toBean(jsonObject, ProdPlanSupplyWarehouseUIModel.class,
						classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			ProdPlanSupplyWarehouseUIModel prodPlanSupplyWarehouseUIModel = parseToUIModel(request);
			List<ServiceEntityNode> rawSeNodeList = productionPlanManager
					.genSeNodeListInExtensionUnion(
							prodPlanSupplyWarehouseServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							ProdPlanSupplyWarehouse.class,
							prodPlanSupplyWarehouseUIModel);
			productionPlanManager.updateSeNodeListWrapper(rawSeNodeList,
					logonActionController.getResUserUUID(), logonActionController.getResOrgUUID());
			prodPlanSupplyWarehouseUIModel =
					refreshLoadServiceUIModel(prodPlanSupplyWarehouseUIModel.getUuid());
			return ServiceJSONParser
					.genDefOKJSONObject(prodPlanSupplyWarehouseUIModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	protected ProdPlanSupplyWarehouseUIModel refreshLoadServiceUIModel(String uuid)
			throws ServiceEntityConfigureException, ServiceModuleProxyException {
		ProdPlanSupplyWarehouse prodPlanSupplyWarehouse = (ProdPlanSupplyWarehouse) productionPlanManager
				.getEntityNodeByKey(
						uuid,
						IServiceEntityNodeFieldConstant.UUID,
						ProdPlanSupplyWarehouse.NODENAME,
						logonActionController.getClient(), null);
		return (ProdPlanSupplyWarehouseUIModel) productionPlanManager
				.genUIModelFromUIModelExtension(
						ProdPlanSupplyWarehouseUIModel.class,
						prodPlanSupplyWarehouseServiceUIModelExtension
								.genUIModelExtensionUnion().get(0),
						prodPlanSupplyWarehouse, logonActionController.getLogonInfo(),null);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			ProductionPlan productionPlan = (ProductionPlan) productionPlanManager
					.getEntityNodeByKey(request.getBaseUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							ProductionPlan.NODENAME, logonActionController.getClient(),
							null);
			ProdPlanSupplyWarehouse prodPlanSupplyWarehouse = (ProdPlanSupplyWarehouse) productionPlanManager
					.newEntityNode(productionPlan,
							ProdPlanSupplyWarehouse.NODENAME);
			ProdPlanSupplyWarehouseUIModel prodPlanSupplyWarehouseUIModel = new ProdPlanSupplyWarehouseUIModel();
			productionPlanManager.convProdPlanSupplyWarehouseToUI(
					prodPlanSupplyWarehouse, prodPlanSupplyWarehouseUIModel);
			return ServiceJSONParser
					.genDefOKJSONObject(prodPlanSupplyWarehouseUIModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super
				.checkDuplicateIDCore(simpleRequest, productionPlanManager);
	}

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preLock(String uuid) {
		try {
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ProdPlanSupplyWarehouse prodPlanSupplyWarehouse = (ProdPlanSupplyWarehouse) productionPlanManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProdPlanSupplyWarehouse.NODENAME,
							logonUser.getClient(), null);
			String baseUUID = prodPlanSupplyWarehouse.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			lockSEList.add(prodPlanSupplyWarehouse);
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					prodPlanSupplyWarehouse.getName(),
					prodPlanSupplyWarehouse.getId(), baseUUID);
		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		}
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
	public @ResponseBody String loadModule(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			ProdPlanSupplyWarehouse prodPlanSupplyWarehouse = (ProdPlanSupplyWarehouse) productionPlanManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProdPlanSupplyWarehouse.NODENAME,
							logonActionController.getClient(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(prodPlanSupplyWarehouse);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());

		}
	}

	@RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String deleteModule(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			productionPlanManager.admDeleteEntityByKey(uuid,
					IServiceEntityNodeFieldConstant.UUID, ProdPlanSupplyWarehouse.NODENAME);
			return ServiceJSONParser.genDeleteOKResponse();
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LockObjectFailureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleEditService(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			ProdPlanSupplyWarehouse prodPlanSupplyWarehouse = (ProdPlanSupplyWarehouse) productionPlanManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProdPlanSupplyWarehouse.NODENAME,
							logonActionController.getClient(), null);
			lockSEList.add(prodPlanSupplyWarehouse);
			lockObjectManager.lockServiceEntityList(lockSEList, logonActionController.getLogonUser(),
					logonActionController.getOrganizationByUser(logonActionController.getResUserUUID()));
			ProdPlanSupplyWarehouseUIModel prodPlanSupplyWarehouseUIModel = refreshLoadServiceUIModel(uuid);
			return ServiceJSONParser
					.genDefOKJSONObject(prodPlanSupplyWarehouseUIModel);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LockObjectFailureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
