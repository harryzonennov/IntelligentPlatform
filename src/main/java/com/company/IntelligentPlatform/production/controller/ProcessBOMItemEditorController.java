package com.company.IntelligentPlatform.production.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.company.IntelligentPlatform.production.dto.ProcessBOMItemUIModel;
import com.company.IntelligentPlatform.production.dto.ProcessBOMMaterialItemUIModel;
import com.company.IntelligentPlatform.production.service.ProcessBOMItemManager;
import com.company.IntelligentPlatform.production.service.ProcessBOMOrderManager;
import com.company.IntelligentPlatform.production.service.ProcessRouteOrderManager;
import com.company.IntelligentPlatform.production.service.ProdProcessManager;
import com.company.IntelligentPlatform.production.service.ProdWorkCenterManager;
import com.company.IntelligentPlatform.production.model.ProcessBOMItem;
import com.company.IntelligentPlatform.production.model.ProcessBOMMaterialItem;
import com.company.IntelligentPlatform.production.model.ProcessBOMOrder;
import com.company.IntelligentPlatform.production.model.ProcessRouteProcessItem;
import com.company.IntelligentPlatform.production.model.ProdProcess;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "processBOMItemEditorController")
@RequestMapping(value = "/processBOMItem")
public class ProcessBOMItemEditorController extends SEEditorController {

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
	protected ProcessBOMItemManager processBOMItemManager;

	@Autowired
	protected ProcessRouteOrderManager processRouteOrderManager;

	@Autowired
	protected ProdWorkCenterManager prodWorkCenterManager;

	@Autowired
	protected ProdProcessManager prodProcessManager;

	@Autowired
	protected ProcessBOMMaterialItemListController processBOMMaterialItemListController;
	
	@Autowired
	protected StandardKeyFlagProxy standardKeyFlagProxy;

	protected String getPreWarnMsg(String key, Map<String, String> preWarnMap) {
		return preWarnMap.get(key);
	}

	protected Map<String, String> getPreWarnMap() throws IOException {
		Locale locale = Locale.getDefault();
		String path = ProcessBOMItemUIModel.class.getResource("").getPath();
		String resFileName = ProcessBOMItem.NODENAME;
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap;
	}


	protected void saveInternal(ProcessBOMItemUIModel processBOMItemUIModel)
			throws ServiceEntityConfigureException, LogonInfoException {
		String baseUUID = processBOMItemUIModel.getUuid();
		ProcessBOMItem processBOMItem = (ProcessBOMItem) getServiceEntityNodeFromBuffer(
				ProcessBOMItem.NODENAME, baseUUID);
		processBOMItemManager.convUIToProcessBOMItem(processBOMItem,
				processBOMItemUIModel);
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
			ProcessBOMItem processBOMItem = (ProcessBOMItem) processBOMOrderManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProcessBOMItem.NODENAME, logonUser.getClient(),
							null);
			String baseUUID = processBOMItem.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<ServiceEntityNode>();
			lockSEList.add(processBOMItem);
			ProcessBOMMaterialItem processBOMMaterialItem = (ProcessBOMMaterialItem) processBOMOrderManager
					.getEntityNodeByKey(processBOMItem.getUuid(),
							IServiceEntityNodeFieldConstant.PARENTNODEUUID,
							ProcessBOMMaterialItem.NODENAME, null);
			lockSEList.add(processBOMMaterialItem);
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					processBOMItem.getName(), processBOMItem.getId(), baseUUID);

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

	@RequestMapping(value = "chooseProcessRouteProcessItemToProcessBOMOrder", produces = "text/html;charset=UTF-8")
	public @ResponseBody String chooseProcessRouteProcessItemToProcessBOMOrder(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			String baseUUID = request.getBaseUUID();
			String uuid = request.getUuid();
			ProcessRouteProcessItem processRouteProcessItem = (ProcessRouteProcessItem) processRouteOrderManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProcessRouteProcessItem.NODENAME, null);
			if (processRouteProcessItem == null) {
				// [!!Manual Action Required] should raise exception here !!

			}
			ProcessBOMItem processBOMItem = (ProcessBOMItem) getServiceEntityNodeFromBuffer(
					ProcessBOMItem.NODENAME, baseUUID);
			processBOMItem
					.setRefProssRouteProcessItemUUID(processRouteProcessItem
							.getUuid());
			String responseData = ServiceJSONParser
					.genDefOKJSONObject(processRouteProcessItem);
			return responseData;
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

}
