package com.company.IntelligentPlatform.production.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.production.dto.ProcessBOMItemUIModel;
import com.company.IntelligentPlatform.production.dto.ProcessBOMOrderUIModel;
import com.company.IntelligentPlatform.production.service.BillOfMaterialOrderManager;
import com.company.IntelligentPlatform.production.service.ProcessBOMOrderException;
import com.company.IntelligentPlatform.production.service.ProcessBOMOrderManager;
import com.company.IntelligentPlatform.production.service.ProcessRouteOrderManager;
import com.company.IntelligentPlatform.production.model.BillOfMaterialOrder;
import com.company.IntelligentPlatform.production.model.ProcessBOMItem;
import com.company.IntelligentPlatform.production.model.ProcessBOMOrder;
import com.company.IntelligentPlatform.production.model.ProcessRouteOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.INavigationElementConstants;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.StandardKeyFlagProxy;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "processBOMOrderEditorController")
@RequestMapping(value = "/processBOMOrder")
public class ProcessBOMOrderEditorController extends SEEditorController {

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

	@Autowired
	protected ProcessBOMItemListController processBOMItemListController;
	
	@Autowired
	protected StandardKeyFlagProxy standardKeyFlagProxy;


	protected String getPreWarnMsg(String key, Map<String, String> preWarnMap) {
		return preWarnMap.get(key);
	}

	protected Map<String, String> getPreWarnMap() throws IOException {
		Locale locale = Locale.getDefault();
		String path = ProcessBOMOrderUIModel.class.getResource("").getPath();
		String resFileName = ProcessBOMOrder.SENAME;
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap;
	}


	protected void saveInternal(ProcessBOMOrderUIModel processBOMOrderUIModel)
			throws ServiceEntityConfigureException, LogonInfoException {
		String baseUUID = processBOMOrderUIModel.getUuid();
		ProcessBOMOrder processBOMOrder = (ProcessBOMOrder) getServiceEntityNodeFromBuffer(
				ProcessBOMOrder.NODENAME, baseUUID);
		processBOMOrderManager.convUIToProcessBOMOrder(processBOMOrder,
				processBOMOrderUIModel);
		LogonUser logonUser = logonActionController.getLogonUser();
		if (logonUser == null) {
			throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
		}
		Organization organization = logonActionController
				.getOrganizationByUser(logonUser.getUuid());
		this.save(baseUUID, processBOMOrderManager, logonUser, organization);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super
				.checkDuplicateIDCore(simpleRequest, processBOMOrderManager);
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

	@RequestMapping(value = "chooseBillOfMaterialOrderToProcessBOMOrder", produces = "text/html;charset=UTF-8")
	public @ResponseBody String chooseBillOfMaterialOrderToProcessBOMOrder(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			String baseUUID = request.getBaseUUID();
			String uuid = request.getUuid();
			BillOfMaterialOrder billOfMaterialOrder = (BillOfMaterialOrder) billOfMaterialOrderManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							BillOfMaterialOrder.NODENAME, null);
			if (billOfMaterialOrder == null) {
				// [!!Manual Action Required] should raise exception here !!

			}
			ProcessBOMOrder processBOMOrder = (ProcessBOMOrder) getServiceEntityNodeFromBuffer(
					ProcessBOMOrder.NODENAME, baseUUID);
			processBOMOrder.setRefBOMUUID(billOfMaterialOrder.getUuid());
			String responseData = ServiceJSONParser
					.genDefOKJSONObject(billOfMaterialOrder);
			return responseData;
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "chooseMaterialStockKeepUnitToProcessBOMOrder", produces = "text/html;charset=UTF-8")
	public @ResponseBody String chooseMaterialStockKeepUnitToProcessBOMOrder(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			String baseUUID = request.getBaseUUID();
			String uuid = request.getUuid();
			MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							MaterialStockKeepUnit.NODENAME, null);
			if (materialStockKeepUnit == null) {
				// [!!Manual Action Required] should raise exception here !!

			}
			ProcessBOMOrder processBOMOrder = (ProcessBOMOrder) getServiceEntityNodeFromBuffer(
					ProcessBOMOrder.NODENAME, baseUUID);
			processBOMOrder.setRefMaterialSKUUUID(materialStockKeepUnit
					.getUuid());
			String responseData = ServiceJSONParser
					.genDefOKJSONObject(materialStockKeepUnit);
			return responseData;
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "chooseProcessRouteOrderToProcessBOMOrder", produces = "text/html;charset=UTF-8")
	public @ResponseBody String chooseProcessRouteOrderToProcessBOMOrder(
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
