package com.company.IntelligentPlatform.production.controller;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.production.dto.RepairProdItemReqProposalServiceUIModel;
import com.company.IntelligentPlatform.production.dto.RepairProdItemReqProposalServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProductionOrderItemServiceUIModel;
import com.company.IntelligentPlatform.production.service.*;
import com.company.IntelligentPlatform.production.model.RepairProdItemReqProposal;
import com.company.IntelligentPlatform.production.model.ProductionOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Scope("session")
@Controller(value = "repairProdItemReqProposalEditorController")
@RequestMapping(value = "/repairProdItemReqProposal")
public class RepairProdItemReqProposalEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IServiceModelConstants.ProductionOrder;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected RepairProdItemReqProposalServiceUIModelExtension repairProdItemReqProposalServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected RepairProdItemReqProposalManager repairProdItemReqProposalManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	private RepairProdItemReqProposalServiceUIModel parseToServiceUIModel(@RequestBody String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		classMap.put("productionOrderItemUIModelList", ProductionOrderItemServiceUIModel.class);
		return (RepairProdItemReqProposalServiceUIModel) JSONObject
				.toBean(jsonObject, RepairProdItemReqProposalServiceUIModel.class, classMap);
	}

	private RepairProdItemReqProposalServiceModel updateServiceUIModelCore(
			RepairProdItemReqProposalServiceUIModel repairProdItemReqProposalServiceUIModel, String logonUserUUID,
			String organizationUUID)
			throws ServiceModuleProxyException, ServiceEntityConfigureException, ServiceUIModuleProxyException {
		RepairProdItemReqProposalServiceModel repairProdItemReqProposalServiceModel = (RepairProdItemReqProposalServiceModel) productionOrderManager
				.genServiceModuleFromServiceUIModel(RepairProdItemReqProposalServiceModel.class,
						RepairProdItemReqProposalServiceUIModel.class, repairProdItemReqProposalServiceUIModel,
						repairProdItemReqProposalServiceUIModelExtension);
		productionOrderManager
				.updateServiceModuleWithDelete(RepairProdItemReqProposalServiceModel.class, repairProdItemReqProposalServiceModel,
						logonUserUUID, organizationUUID);
		return repairProdItemReqProposalServiceModel;
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String saveModuleService(@RequestBody String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		classMap.put("productionOrderItemUIModelList", ProductionOrderItemServiceUIModel.class);
		RepairProdItemReqProposalServiceUIModel repairProdItemReqProposalServiceUIModel = parseToServiceUIModel(request);
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
			Organization organization = logonActionController.getOrganizationByUser(logonUser.getUuid());
			if (organization != null) {
				organizationUUID = organization.getUuid();
			}
			RepairProdItemReqProposalServiceModel repairProdItemReqProposalServiceModel = updateServiceUIModelCore(
					repairProdItemReqProposalServiceUIModel, logonUser.getUuid(), organizationUUID);
			// Refresh service model
			repairProdItemReqProposalServiceUIModel = refreshLoadServiceUIModel(
					repairProdItemReqProposalServiceModel.getRepairProdItemReqProposal().getUuid(), ISystemActionCode.ACID_EDIT,
					logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(repairProdItemReqProposalServiceUIModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException | MaterialException | ServiceComExecuteException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			RepairProdItemReqProposal repairProdItemReqProposal = (RepairProdItemReqProposal) productionOrderManager
					.getEntityNodeByKey(request.getUuid(), IServiceEntityNodeFieldConstant.UUID, RepairProdItemReqProposal.NODENAME,
							logonUser.getClient(), null);
			List<PageHeaderModel> pageHeaderModelList = repairProdItemReqProposalManager
					.getPageHeaderModelList(repairProdItemReqProposal, logonUser.getClient());
			String result = ServiceJSONParser.genDefOKJSONArray(pageHeaderModelList);
			return result;
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/getInProcessDocMatItemList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getInProcessDocMatItemList(@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			RepairProdItemReqProposal repairProdItemReqProposal = (RepairProdItemReqProposal) productionOrderManager
					.getEntityNodeByKey(request.getUuid(), IServiceEntityNodeFieldConstant.UUID, RepairProdItemReqProposal.NODENAME,
							logonUser.getClient(), null);
			List<ServiceEntityNode> inProcessDocMatItemList = repairProdItemReqProposalManager
					.getInprocessDocMatItemList(repairProdItemReqProposal);
            return ServiceJSONParser.genDefOKJSONArray(inProcessDocMatItemList);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (MaterialException | DocActionException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/getAllEndDocMatItemList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getAllEndDocMatItemList(@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			RepairProdItemReqProposal repairProdItemReqProposal = (RepairProdItemReqProposal) productionOrderManager
					.getEntityNodeByKey(request.getUuid(), IServiceEntityNodeFieldConstant.UUID, RepairProdItemReqProposal.NODENAME,
							logonUser.getClient(), null);
			List<ServiceDocumentExtendUIModel> endDocumentExtendUIModelList = repairProdItemReqProposalManager
					.getAllEndDocMatItemUIModelList(repairProdItemReqProposal, logonActionController.getLogonInfo(), false);
			return ServiceJSONParser.genDefOKJSONArray(endDocumentExtendUIModelList);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (MaterialException | DocActionException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	public RepairProdItemReqProposalServiceUIModel refreshLoadServiceUIModel(String uuid, String acId, LogonInfo logonInfo)
			throws ServiceModuleProxyException, ServiceEntityConfigureException, ServiceUIModuleProxyException,
			MaterialException, LogonInfoException, AuthorizationException, ServiceComExecuteException {
		RepairProdItemReqProposal repairProdItemReqProposal = (RepairProdItemReqProposal) productionOrderManager
				.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, RepairProdItemReqProposal.NODENAME,
						logonInfo.getClient(), null);
		if (!ServiceEntityStringHelper.checkNullString(acId)) {
			boolean checkAuthor = serviceBasicUtilityController
					.checkTargetDataAccess(logonInfo.getLogonUser(), repairProdItemReqProposal, acId,
							logonInfo.getAuthorizationActionCodeMap());
			if (!checkAuthor) {
				throw new AuthorizationException(AuthorizationException.TYPE_NO_AUTHORIZATION);
			}
		}
		RepairProdItemReqProposalServiceModel repairProdItemReqProposalServiceModel = (RepairProdItemReqProposalServiceModel) productionOrderManager
				.loadServiceModule(RepairProdItemReqProposalServiceModel.class, repairProdItemReqProposal);
		repairProdItemReqProposalManager.refreshItemStatus(repairProdItemReqProposal, null);
		RepairProdItemReqProposalServiceUIModel repairProdItemReqProposalServiceUIModel = (RepairProdItemReqProposalServiceUIModel) productionOrderManager
				.genServiceUIModuleFromServiceModel(RepairProdItemReqProposalServiceUIModel.class,
						RepairProdItemReqProposalServiceModel.class, repairProdItemReqProposalServiceModel,
						repairProdItemReqProposalServiceUIModelExtension, logonActionController.getLogonInfo());
		return repairProdItemReqProposalServiceUIModel;
	}

	@RequestMapping(value = "/getItemStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getItemStatus() {
		try {
			Map<Integer, String> itemStatusMap = productionOrderManager.initItemStatusMap(logonActionController.getLanguageCode());
			return productionOrderManager.getDefaultSelectMap(itemStatusMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String newModuleService(@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ProductionOrder productionOrder = (ProductionOrder) productionOrderManager
					.getEntityNodeByKey(request.getBaseUUID(), IServiceEntityNodeFieldConstant.UUID, ProductionOrder.NODENAME,
							logonUser.getClient(), null);
			RepairProdItemReqProposal repairProdItemReqProposal = (RepairProdItemReqProposal) productionOrderManager
					.newEntityNode(productionOrder, RepairProdItemReqProposal.NODENAME);
			RepairProdItemReqProposalServiceModel repairProdItemReqProposalServiceModel = new RepairProdItemReqProposalServiceModel();
			repairProdItemReqProposalServiceModel.setRepairProdItemReqProposal(repairProdItemReqProposal);
			RepairProdItemReqProposalServiceUIModel repairProdItemReqProposalServiceUIModel = (RepairProdItemReqProposalServiceUIModel) productionOrderManager
					.genServiceUIModuleFromServiceModel(RepairProdItemReqProposalServiceUIModel.class,
							RepairProdItemReqProposalServiceModel.class, repairProdItemReqProposalServiceModel,
							repairProdItemReqProposalServiceUIModelExtension, logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(repairProdItemReqProposalServiceUIModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String checkDuplicateID(@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest, productionOrderManager);
	}

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String preLock(String uuid) {
		try {
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			RepairProdItemReqProposal repairProdItemReqProposal = (RepairProdItemReqProposal) productionOrderManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, RepairProdItemReqProposal.NODENAME,
							logonUser.getClient(), null);
			String baseUUID = repairProdItemReqProposal.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<ServiceEntityNode>();
			lockSEList.add(repairProdItemReqProposal);
			List<ServiceEntityNode> lockResult = lockObjectManager.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager
					.genJSONLockCheckResult(lockResult, repairProdItemReqProposal.getName(), repairProdItemReqProposal.getId(),
							baseUUID);

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
	public @ResponseBody
	String preLockService(@RequestBody SimpleSEJSONRequest request) {
		return preLock(request.getUuid());
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModule(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			RepairProdItemReqProposal repairProdItemReqProposal = (RepairProdItemReqProposal) productionOrderManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, RepairProdItemReqProposal.NODENAME,
							logonUser.getClient(), null);
			return ServiceJSONParser.genDefOKJSONObject(repairProdItemReqProposal);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());

		}
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleEditService(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			RepairProdItemReqProposalServiceUIModel repairProdItemReqProposalServiceUIModel = refreshLoadServiceUIModel(uuid,
					ISystemActionCode.ACID_EDIT, logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(repairProdItemReqProposalServiceUIModel);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LockObjectFailureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		} catch (ServiceModuleProxyException | MaterialException | ServiceUIModuleProxyException | ServiceComExecuteException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleViewService(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			RepairProdItemReqProposalServiceUIModel repairProdItemReqProposalServiceUIModel = refreshLoadServiceUIModel(uuid,
					ISystemActionCode.ACID_VIEW, logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(repairProdItemReqProposalServiceUIModel);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LockObjectFailureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		} catch (ServiceModuleProxyException | MaterialException | ServiceUIModuleProxyException | ServiceComExecuteException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/setPickingFinishService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String setPickingFinishService(@RequestBody String request) {
		try {
			RepairProdItemReqProposalServiceUIModel repairProdItemReqProposalServiceUIModel = parseToServiceUIModel(request);
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
			Organization organization = logonActionController.getOrganizationByUser(logonUser.getUuid());
			if (organization != null) {
				organizationUUID = organization.getUuid();
			}
			RepairProdItemReqProposalServiceModel repairProdItemReqProposalServiceModel = updateServiceUIModelCore(
					repairProdItemReqProposalServiceUIModel, logonUser.getUuid(), organizationUUID);
			// Refresh service model
			RepairProdItemReqProposal repairProdItemReqProposal = repairProdItemReqProposalServiceModel.getRepairProdItemReqProposal();
			repairProdItemReqProposalManager
					.setFinishPickingRefMaterialItem(repairProdItemReqProposal, logonUser.getUuid(), organizationUUID);
			repairProdItemReqProposalServiceUIModel = refreshLoadServiceUIModel(
					repairProdItemReqProposalServiceModel.getRepairProdItemReqProposal().getUuid(), ISystemActionCode.ACID_EDIT,
					logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(repairProdItemReqProposalServiceUIModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ProductionOrderException | MaterialException | ServiceUIModuleProxyException | ServiceComExecuteException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String exitEditor(@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "/getStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getStatus() {
		try {
			Map<Integer, String> statusMap = productionOrderManager.initStatusMap(logonActionController.getLanguageCode());
			return productionOrderManager.getDefaultSelectMap(statusMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}
	}

	@RequestMapping(value = "/getDocFlowList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getDocFlowList(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			RepairProdItemReqProposal repairProdItemReqProposal = (RepairProdItemReqProposal) productionOrderManager
					.getEntityNodeByUUID(uuid, RepairProdItemReqProposal.NODENAME, logonUser.getClient());
			List<ServiceDocumentExtendUIModel> docFlowList = serviceDocumentComProxy
					.getDocFlowList(repairProdItemReqProposal, productionOrderManager, ISystemActionCode.ACID_VIEW,
							logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONArray(docFlowList);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

}
