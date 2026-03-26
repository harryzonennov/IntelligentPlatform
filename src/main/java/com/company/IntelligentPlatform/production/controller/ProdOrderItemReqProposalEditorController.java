package com.company.IntelligentPlatform.production.controller;

import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.production.dto.ProdOrderItemReqProposalServiceUIModel;
import com.company.IntelligentPlatform.production.dto.ProdOrderItemReqProposalServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProductionOrderItemServiceUIModel;
import com.company.IntelligentPlatform.production.service.BillOfMaterialOrderManager;
import com.company.IntelligentPlatform.production.service.ProdOrderItemReqProposalManager;
import com.company.IntelligentPlatform.production.service.ProdOrderItemReqProposalServiceModel;
import com.company.IntelligentPlatform.production.service.ProductionOrderException;
import com.company.IntelligentPlatform.production.service.ProductionOrderManager;
import com.company.IntelligentPlatform.production.model.ProductionOrder;
import com.company.IntelligentPlatform.production.model.ProdOrderItemReqProposal;

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

@Scope("session")
@Controller(value = "prodOrderItemReqProposalEditorController")
@RequestMapping(value = "/prodOrderItemReqProposal")
public class ProdOrderItemReqProposalEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IServiceModelConstants.ProductionOrder;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected ProdOrderItemReqProposalServiceUIModelExtension prodOrderItemReqProposalServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected ProdOrderItemReqProposalManager prodOrderItemReqProposalManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	private ProdOrderItemReqProposalServiceUIModel parseToServiceUIModel(@RequestBody String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		classMap.put("productionOrderItemUIModelList", ProductionOrderItemServiceUIModel.class);
		return (ProdOrderItemReqProposalServiceUIModel) JSONObject
				.toBean(jsonObject, ProdOrderItemReqProposalServiceUIModel.class, classMap);
	}

	private ProdOrderItemReqProposalServiceModel updateServiceUIModelCore(
			ProdOrderItemReqProposalServiceUIModel prodOrderItemReqProposalServiceUIModel, String logonUserUUID,
			String organizationUUID)
			throws ServiceModuleProxyException, ServiceEntityConfigureException, ServiceUIModuleProxyException {
		ProdOrderItemReqProposalServiceModel prodOrderItemReqProposalServiceModel = (ProdOrderItemReqProposalServiceModel) productionOrderManager
				.genServiceModuleFromServiceUIModel(ProdOrderItemReqProposalServiceModel.class,
						ProdOrderItemReqProposalServiceUIModel.class, prodOrderItemReqProposalServiceUIModel,
						prodOrderItemReqProposalServiceUIModelExtension);
		productionOrderManager
				.updateServiceModuleWithDelete(ProdOrderItemReqProposalServiceModel.class, prodOrderItemReqProposalServiceModel,
						logonUserUUID, organizationUUID);
		return prodOrderItemReqProposalServiceModel;
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String saveModuleService(@RequestBody String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		classMap.put("productionOrderItemUIModelList", ProductionOrderItemServiceUIModel.class);
		ProdOrderItemReqProposalServiceUIModel prodOrderItemReqProposalServiceUIModel = parseToServiceUIModel(request);
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
			ProdOrderItemReqProposalServiceModel prodOrderItemReqProposalServiceModel = updateServiceUIModelCore(
					prodOrderItemReqProposalServiceUIModel, logonUser.getUuid(), organizationUUID);
			// Refresh service model
			prodOrderItemReqProposalServiceUIModel = refreshLoadServiceUIModel(
					prodOrderItemReqProposalServiceModel.getProdOrderItemReqProposal().getUuid(), ISystemActionCode.ACID_EDIT,
					logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(prodOrderItemReqProposalServiceUIModel);
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
			ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) productionOrderManager
					.getEntityNodeByKey(request.getUuid(), IServiceEntityNodeFieldConstant.UUID, ProdOrderItemReqProposal.NODENAME,
							logonUser.getClient(), null);
			List<PageHeaderModel> pageHeaderModelList = prodOrderItemReqProposalManager
					.getPageHeaderModelList(prodOrderItemReqProposal, logonUser.getClient());
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
			ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) productionOrderManager
					.getEntityNodeByKey(request.getUuid(), IServiceEntityNodeFieldConstant.UUID, ProdOrderItemReqProposal.NODENAME,
							logonUser.getClient(), null);
			List<ServiceEntityNode> inProcessDocMatItemList = prodOrderItemReqProposalManager
					.getInprocessDocMatItemList(prodOrderItemReqProposal);
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
			ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) productionOrderManager
					.getEntityNodeByKey(request.getUuid(), IServiceEntityNodeFieldConstant.UUID, ProdOrderItemReqProposal.NODENAME,
							logonUser.getClient(), null);
			List<ServiceDocumentExtendUIModel> endDocumentExtendUIModelList = prodOrderItemReqProposalManager
					.getAllEndDocMatItemUIModelList(prodOrderItemReqProposal, logonActionController.getLogonInfo(), false);
			return ServiceJSONParser.genDefOKJSONArray(endDocumentExtendUIModelList);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (MaterialException | DocActionException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	public ProdOrderItemReqProposalServiceUIModel refreshLoadServiceUIModel(String uuid, String acId, LogonInfo logonInfo)
			throws ServiceModuleProxyException, ServiceEntityConfigureException, ServiceUIModuleProxyException,
			MaterialException, LogonInfoException, AuthorizationException, ServiceComExecuteException {
		ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) productionOrderManager
				.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, ProdOrderItemReqProposal.NODENAME,
						logonInfo.getClient(), null);
		if (!ServiceEntityStringHelper.checkNullString(acId)) {
			boolean checkAuthor = serviceBasicUtilityController
					.checkTargetDataAccess(logonInfo.getLogonUser(), prodOrderItemReqProposal, acId,
							logonInfo.getAuthorizationActionCodeMap());
			if (!checkAuthor) {
				throw new AuthorizationException(AuthorizationException.TYPE_NO_AUTHORIZATION);
			}
		}
		ProdOrderItemReqProposalServiceModel prodOrderItemReqProposalServiceModel = (ProdOrderItemReqProposalServiceModel) productionOrderManager
				.loadServiceModule(ProdOrderItemReqProposalServiceModel.class, prodOrderItemReqProposal);
		prodOrderItemReqProposalManager.refreshItemStatus(prodOrderItemReqProposal, null);
		ProdOrderItemReqProposalServiceUIModel prodOrderItemReqProposalServiceUIModel = (ProdOrderItemReqProposalServiceUIModel) productionOrderManager
				.genServiceUIModuleFromServiceModel(ProdOrderItemReqProposalServiceUIModel.class,
						ProdOrderItemReqProposalServiceModel.class, prodOrderItemReqProposalServiceModel,
						prodOrderItemReqProposalServiceUIModelExtension, logonActionController.getLogonInfo());
		return prodOrderItemReqProposalServiceUIModel;
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
			ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) productionOrderManager
					.newEntityNode(productionOrder, ProdOrderItemReqProposal.NODENAME);
			ProdOrderItemReqProposalServiceModel prodOrderItemReqProposalServiceModel = new ProdOrderItemReqProposalServiceModel();
			prodOrderItemReqProposalServiceModel.setProdOrderItemReqProposal(prodOrderItemReqProposal);
			ProdOrderItemReqProposalServiceUIModel prodOrderItemReqProposalServiceUIModel = (ProdOrderItemReqProposalServiceUIModel) productionOrderManager
					.genServiceUIModuleFromServiceModel(ProdOrderItemReqProposalServiceUIModel.class,
							ProdOrderItemReqProposalServiceModel.class, prodOrderItemReqProposalServiceModel,
							prodOrderItemReqProposalServiceUIModelExtension, logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(prodOrderItemReqProposalServiceUIModel);
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
			ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) productionOrderManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, ProdOrderItemReqProposal.NODENAME,
							logonUser.getClient(), null);
			String baseUUID = prodOrderItemReqProposal.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<ServiceEntityNode>();
			lockSEList.add(prodOrderItemReqProposal);
			List<ServiceEntityNode> lockResult = lockObjectManager.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager
					.genJSONLockCheckResult(lockResult, prodOrderItemReqProposal.getName(), prodOrderItemReqProposal.getId(),
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
			ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) productionOrderManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, ProdOrderItemReqProposal.NODENAME,
							logonUser.getClient(), null);
			return ServiceJSONParser.genDefOKJSONObject(prodOrderItemReqProposal);
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
			ProdOrderItemReqProposalServiceUIModel prodOrderItemReqProposalServiceUIModel = refreshLoadServiceUIModel(uuid,
					ISystemActionCode.ACID_EDIT, logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(prodOrderItemReqProposalServiceUIModel);
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
			ProdOrderItemReqProposalServiceUIModel prodOrderItemReqProposalServiceUIModel = refreshLoadServiceUIModel(uuid,
					ISystemActionCode.ACID_VIEW, logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(prodOrderItemReqProposalServiceUIModel);
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
			ProdOrderItemReqProposalServiceUIModel prodOrderItemReqProposalServiceUIModel = parseToServiceUIModel(request);
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
			ProdOrderItemReqProposalServiceModel prodOrderItemReqProposalServiceModel = updateServiceUIModelCore(
					prodOrderItemReqProposalServiceUIModel, logonUser.getUuid(), organizationUUID);
			// Refresh service model
			ProdOrderItemReqProposal prodOrderItemReqProposal = prodOrderItemReqProposalServiceModel.getProdOrderItemReqProposal();
			prodOrderItemReqProposalManager
					.setFinishPickingRefMaterialItem(prodOrderItemReqProposal, logonUser.getUuid(), organizationUUID);
			prodOrderItemReqProposalServiceUIModel = refreshLoadServiceUIModel(
					prodOrderItemReqProposalServiceModel.getProdOrderItemReqProposal().getUuid(), ISystemActionCode.ACID_EDIT,
					logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(prodOrderItemReqProposalServiceUIModel);
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
			ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) productionOrderManager
					.getEntityNodeByUUID(uuid, ProdOrderItemReqProposal.NODENAME, logonUser.getClient());
			List<ServiceDocumentExtendUIModel> docFlowList = serviceDocumentComProxy
					.getDocFlowList(prodOrderItemReqProposal, productionOrderManager, ISystemActionCode.ACID_VIEW,
							logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONArray(docFlowList);
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

}
