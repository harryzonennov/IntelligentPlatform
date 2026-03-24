package com.company.IntelligentPlatform.production.controller;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.dto.ProcessBOMOrderInitialModel;
import com.company.IntelligentPlatform.production.service.BillOfMaterialOrderManager;
import com.company.IntelligentPlatform.production.service.ProcessBOMOrderManager;
import com.company.IntelligentPlatform.production.service.ProcessRouteOrderManager;
import com.company.IntelligentPlatform.production.service.ProductionDataException;
import com.company.IntelligentPlatform.production.model.BillOfMaterialOrder;
import com.company.IntelligentPlatform.production.model.ProcessBOMOrder;
import com.company.IntelligentPlatform.production.model.ProcessRouteOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "processBOMOrderInitController")
@RequestMapping(value = "/processBOMInit")
public class ProcessBOMOrderInitController extends SEEditorController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_PROD_WORKCENTER;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ProcessBOMOrderManager processBOMOrderManager;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ProcessRouteOrderManager processRouteOrderManager;


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
			ProcessBOMOrder processBOMOrder = (ProcessBOMOrder) processBOMOrderManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProcessBOMOrder.NODENAME, logonUser.getClient(),
							null);
			String baseUUID = processBOMOrder.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<ServiceEntityNode>();
			lockSEList.add(processBOMOrder);
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					processBOMOrder.getName(), processBOMOrder.getId(),
					baseUUID);

		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "chooseMaterialSKU", produces = "text/html;charset=UTF-8")
	public @ResponseBody String chooseMaterialSKU(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String uuid = request.getUuid();
			MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							MaterialStockKeepUnit.NODENAME, null);
			if (materialStockKeepUnit == null) {
				throw new ProductionDataException(
						ProductionDataException.TYPE_SYSTEM_WRONG);
			}
			ProcessBOMOrderInitialModel responseModel = new ProcessBOMOrderInitialModel();
			responseModel
					.setRefMaterialSKUUUID(materialStockKeepUnit.getUuid());
			responseModel.setRefMaterialSKUID(materialStockKeepUnit.getId());
			responseModel
					.setRefMaterialSKUName(materialStockKeepUnit.getName());
			BillOfMaterialOrder billOfMaterialOrder = billOfMaterialOrderManager
					.getDefaultBOMBySKU(materialStockKeepUnit.getUuid(),
							logonUser.getClient());
			if (billOfMaterialOrder != null) {
				responseModel.setRefBOMOrderID(billOfMaterialOrder.getId());
				responseModel.setRefBOMUUID(billOfMaterialOrder.getUuid());
			}
			ProcessRouteOrder processRouteOrder = processRouteOrderManager
					.getDefaultProcessRouteBySKU(
							materialStockKeepUnit.getUuid(),
							logonUser.getClient());
			if (processRouteOrder != null) {
				responseModel.setRefProcessRouteID(processRouteOrder.getId());
				responseModel.setRefProcessRouteUUID(processRouteOrder
						.getUuid());
				responseModel.setRouteStatus(processRouteOrder.getStatus());
				responseModel.setRouteType(processRouteOrder.getRouteType());
			}
			String responseData = ServiceJSONParser
					.genDefOKJSONObject(responseModel);
			return responseData;
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ProductionDataException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "chooseProcessRouteOrder", produces = "text/html;charset=UTF-8")
	public @ResponseBody String chooseProcessRouteOrder(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			String baseUUID = request.getBaseUUID();
			String uuid = request.getUuid();
			ProcessRouteOrder processRouteOrder = (ProcessRouteOrder) processRouteOrderManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProcessRouteOrder.NODENAME, null);
			if (processRouteOrder == null) {
				// [!!Manual Action Required] should raise exception here !!

			}
			ProcessBOMOrder processBOMOrder = (ProcessBOMOrder) getServiceEntityNodeFromBuffer(
					ProcessBOMOrder.NODENAME, baseUUID);
			processBOMOrder.setRefProcessRouteUUID(processRouteOrder.getUuid());
			String responseData = ServiceJSONParser
					.genDefOKJSONObject(processRouteOrder);
			return responseData;
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

}
