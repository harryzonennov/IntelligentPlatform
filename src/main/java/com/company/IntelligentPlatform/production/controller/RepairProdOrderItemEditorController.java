package com.company.IntelligentPlatform.production.controller;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemGenRequestHelper;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryException;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryServiceModel;
import com.company.IntelligentPlatform.production.dto.*;
import com.company.IntelligentPlatform.production.service.*;
import com.company.IntelligentPlatform.production.model.RepairProdOrderItem;
import com.company.IntelligentPlatform.production.model.RepairProdOrder;
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
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Scope("session")
@Controller(value = "repairProdOrderItemEditorController")
@RequestMapping(value = "/repairProdOrderItem")
public class RepairProdOrderItemEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = RepairProdOrderEditorController.AOID_RESOURCE;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected RepairProdOrderManager repairProdOrderManager;

	@Autowired
	protected RepairProdOrderItemManager repairProdOrderItemManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected RepairProdOrderToCrossOutboundProxy repairProdOrderToCrossOutboundProxy;

	@Autowired
	protected RepairProdOrderItemServiceUIModelExtension repairProdOrderItemServiceUIModelExtension;

	private RepairProdOrderItemServiceUIModel parseToServiceUIModel(String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		classMap.put("prodOrderItemReqProposalUIModelList", ProdOrderItemReqProposalServiceUIModel.class);
		RepairProdOrderItemServiceUIModel repairProdOrderItemServiceUIModel = (RepairProdOrderItemServiceUIModel) JSONObject
				.toBean(jsonObject, RepairProdOrderItemServiceUIModel.class, classMap);
		return repairProdOrderItemServiceUIModel;
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String saveModuleService(@RequestBody String request) {
		RepairProdOrderItemServiceUIModel repairProdOrderItemServiceUIModel = parseToServiceUIModel(request);
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
			RepairProdOrderItemServiceModel repairProdOrderItemServiceModel = (RepairProdOrderItemServiceModel) repairProdOrderManager
					.genServiceModuleFromServiceUIModel(RepairProdOrderItemServiceModel.class, RepairProdOrderItemServiceUIModel.class,
							repairProdOrderItemServiceUIModel, repairProdOrderItemServiceUIModelExtension);
			repairProdOrderItemManager
					.postUpdateRepairProdOrderItemAsyncWrapper(repairProdOrderItemServiceModel, logonUser.getUuid(), organizationUUID);
			repairProdOrderManager
					.updateServiceModuleWithDelete(RepairProdOrderItemServiceModel.class, repairProdOrderItemServiceModel,
							logonUser.getUuid(), organizationUUID);
			// Refresh service model			
			repairProdOrderItemServiceUIModel = refreshLoadSeviceUIModel(
					repairProdOrderItemServiceModel.getRepairProdOrderItem().getUuid(), ISystemActionCode.ACID_EDIT, false,
					logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(repairProdOrderItemServiceUIModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException | MaterialException | ServiceComExecuteException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
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
			RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
					.getEntityNodeByKey(request.getBaseUUID(), IServiceEntityNodeFieldConstant.UUID, RepairProdOrder.NODENAME,
							logonUser.getClient(), null);
			RepairProdOrderItem repairProdOrderItem = (RepairProdOrderItem) repairProdOrderManager
					.newEntityNode(repairProdOrder, RepairProdOrderItem.NODENAME);
			RepairProdOrderItemServiceModel repairProdOrderItemServiceModel = new RepairProdOrderItemServiceModel();
			repairProdOrderItemServiceModel.setRepairProdOrderItem(repairProdOrderItem);
			RepairProdOrderItemServiceUIModel repairProdOrderItemServiceUIModel = (RepairProdOrderItemServiceUIModel) repairProdOrderManager
					.genServiceUIModuleFromServiceModel(RepairProdOrderItemServiceUIModel.class, RepairProdOrderItemServiceModel.class,
							repairProdOrderItemServiceModel, repairProdOrderItemServiceUIModelExtension,
							logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(repairProdOrderItemServiceUIModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/initNewModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String initNewModule(@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
					.getEntityNodeByKey(request.getBaseUUID(), IServiceEntityNodeFieldConstant.UUID, RepairProdOrder.NODENAME,
							logonUser.getClient(), null);
			ProdOrderItemInitModel prodOrderItemInitModel = new ProdOrderItemInitModel();
			prodOrderItemInitModel.setParentNodeUUID(repairProdOrder.getUuid());
			prodOrderItemInitModel.setUuid(repairProdOrder.getRefBillOfMaterialUUID());
			return ServiceJSONParser.genDefOKJSONObject(prodOrderItemInitModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/newModuleSerInit", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String newModuleSerInit(@RequestBody String request) {
		try {
			ProdOrderItemInitModel prodOrderItemInitModel = DocFlowProxy.parseToDocItemInitModel(request,
					ProdOrderItemInitModel.class);
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			RepairProdOrderItemServiceModel repairProdOrderItemServiceModel =
					repairProdOrderItemManager.newRepairProdItemWrapper(prodOrderItemInitModel, logonActionController.getLogonInfo());
			RepairProdOrderItemServiceUIModel repairProdOrderItemServiceUIModel = (RepairProdOrderItemServiceUIModel) repairProdOrderManager
					.genServiceUIModuleFromServiceModel(RepairProdOrderItemServiceUIModel.class, RepairProdOrderItemServiceModel.class,
							repairProdOrderItemServiceModel, repairProdOrderItemServiceUIModelExtension,
							logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(repairProdOrderItemServiceUIModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException | ProductionOrderException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String deleteModule(@RequestBody String request) {
		try {
			JSONObject jsonObject = JSONObject.fromObject(request);
			@SuppressWarnings("rawtypes")
			Map<String, Class> classMap = new HashMap<>();
			SimpleSEJSONRequest requestJSON = (SimpleSEJSONRequest) JSONObject
					.toBean(jsonObject, SimpleSEJSONRequest.class, classMap);
			if (ServiceEntityStringHelper.checkNullString(requestJSON.getUuid())) {
				// UUID should not be null
				throw new ServiceModuleProxyException(ServiceModuleProxyException.TYPE_SYSTEM_WRONG);
			}
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			RepairProdOrderItem repairProdOrderItem = (RepairProdOrderItem) repairProdOrderManager
					.getEntityNodeByKey(requestJSON.getUuid(), IServiceEntityNodeFieldConstant.UUID, RepairProdOrderItem.NODENAME,
							logonUser.getClient(), null);
			if (repairProdOrderItem != null) {
				repairProdOrderManager.admDeleteEntityByKey(requestJSON.getUuid(),
						IServiceEntityNodeFieldConstant.UUID, RepairProdOrderItem.NODENAME);
			}
			return ServiceJSONParser.genSimpleOKResponse();
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/getItemStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getItemStatus() {
		try {
			Map<Integer, String> itemStatusMap = repairProdOrderManager.initItemStatusMap(logonActionController.getLanguageCode());
			return repairProdOrderManager.getDefaultSelectMap(itemStatusMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}
	}

	@RequestMapping(value = "/loadLeanViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadLeanViewService(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			RepairProdOrderItem repairProdOrderItem = (RepairProdOrderItem) repairProdOrderManager
					.getEntityNodeByUUID(uuid, RepairProdOrderItem.NODENAME, logonUser.getClient());
			if (repairProdOrderItem == null) {
				throw new ProductionOrderException(ProductionOrderException.PARA_NO_PRODORDER, uuid);
			}

			RepairProdOrderItemUIModel repairProdOrderItemUIModel = (RepairProdOrderItemUIModel) repairProdOrderManager
					.genUIModelFromUIModelExtension(RepairProdOrderUIModel.class,
							repairProdOrderItemServiceUIModelExtension.genUIModelExtensionUnion().get(0), repairProdOrderItem,
							logonActionController.getLogonInfo(), null);
			return ServiceJSONParser.genDefOKJSONObject(repairProdOrderItemUIModel);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ProductionOrderException e) {
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
			RepairProdOrderItem repairProdOrderItem = (RepairProdOrderItem) repairProdOrderManager
					.getEntityNodeByKey(request.getUuid(), IServiceEntityNodeFieldConstant.UUID, RepairProdOrderItem.NODENAME,
							logonUser.getClient(), null);
			List<PageHeaderModel> pageHeaderModelList = repairProdOrderItemManager
					.getPageHeaderModelList(repairProdOrderItem, logonUser.getClient());
			return ServiceJSONParser.genDefOKJSONArray(pageHeaderModelList);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	public @RequestMapping(value = "/newOutboundBatchBackMode", produces = "text/html;" + "charset=UTF-8")
	@ResponseBody
	String newOutboundBatchBackMode(@RequestBody String request) {
		DeliveryMatItemBatchGenRequest genRequest = DeliveryMatItemGenRequestHelper.parseToGenRequest(request);
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			Organization organization = logonActionController.getOrganizationByUser(logonUser.getUuid());
			String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
			if (organization != null) {
				organizationUUID = organization.getUuid();
			}
			RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
					.getEntityNodeByKey(genRequest.getBaseUUID(), IServiceEntityNodeFieldConstant.UUID, RepairProdOrder.NODENAME,
							logonUser.getClient(), null);
			List<OutboundDeliveryServiceModel> resultList = repairProdOrderToCrossOutboundProxy
					.genOutboundFromStoreList(genRequest, logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONArray(resultList);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException | ServiceEntityInstallationException | NodeNotFoundException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (SearchConfigureException | MaterialException | OutboundDeliveryException |
                 ServiceModuleProxyException | DocActionException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
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
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			RepairProdOrderItem repairProdOrderItem = (RepairProdOrderItem) repairProdOrderManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, RepairProdOrderItem.NODENAME,
							logonUser.getClient(), null);
			return ServiceJSONParser.genDefOKJSONObject(repairProdOrderItem);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	private RepairProdOrderItemServiceUIModel refreshLoadSeviceUIModel(String uuid, String acId, boolean postUpdateFlag,
			LogonInfo logonInfo)
			throws ServiceEntityConfigureException, ServiceModuleProxyException, ServiceUIModuleProxyException,
			LogonInfoException, MaterialException, AuthorizationException, ServiceComExecuteException {
		RepairProdOrderItem repairProdOrderItem = (RepairProdOrderItem) repairProdOrderManager
				.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, RepairProdOrderItem.NODENAME, logonInfo.getClient(),
						null);
		if (!ServiceEntityStringHelper.checkNullString(acId)) {
			boolean checkAuthor = serviceBasicUtilityController
					.checkTargetDataAccess(logonInfo.getLogonUser(), repairProdOrderItem, acId,
							logonInfo.getAuthorizationActionCodeMap());
			if (!checkAuthor) {
				throw new AuthorizationException(AuthorizationException.TYPE_NO_AUTHORIZATION);
			}
		}
		RepairProdOrderItemServiceModel repairProdOrderItemServiceModel = (RepairProdOrderItemServiceModel) repairProdOrderManager
				.loadServiceModule(RepairProdOrderItemServiceModel.class, repairProdOrderItem);
		if (postUpdateFlag) {
			// Refresh and update production order's information into DB
            try {
                repairProdOrderItemManager.postUpdateRepairProdOrderItem(repairProdOrderItemServiceModel, logonInfo.getRefUserUUID(),
                        logonInfo.getResOrgUUID());
            } catch (DocActionException e) {
                throw new RuntimeException(e);
            }
        }
		RepairProdOrderItemServiceUIModel repairProdOrderItemServiceUIModel = (RepairProdOrderItemServiceUIModel) repairProdOrderManager
				.genServiceUIModuleFromServiceModel(RepairProdOrderItemServiceUIModel.class, RepairProdOrderItemServiceModel.class,
						repairProdOrderItemServiceModel, repairProdOrderItemServiceUIModelExtension,
						logonActionController.getLogonInfo());
		return repairProdOrderItemServiceUIModel;
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
			RepairProdOrderItem repairProdOrderItem = (RepairProdOrderItem) repairProdOrderManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, RepairProdOrderItem.NODENAME,
							logonUser.getClient(), null);
			RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
					.getEntityNodeByKey(repairProdOrderItem.getRootNodeUUID(), IServiceEntityNodeFieldConstant.UUID,
							RepairProdOrder.NODENAME, logonUser.getClient(), null);
			RepairProdOrderItemServiceModel repairProdOrderItemServiceModel = (RepairProdOrderItemServiceModel) repairProdOrderManager
					.loadServiceModule(RepairProdOrderItemServiceModel.class, repairProdOrderItem);
			List<RepairProdOrderItemServiceModel> requestList = ServiceCollectionsHelper.asList(repairProdOrderItemServiceModel);
			RepairProdOrderItemServiceUIModel repairProdOrderItemServiceUIModel = refreshLoadSeviceUIModel(
					repairProdOrderItemServiceModel.getRepairProdOrderItem().getUuid(), ISystemActionCode.ACID_EDIT, true,
					logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(repairProdOrderItemServiceUIModel);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException | MaterialException | ServiceComExecuteException e) {
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
			RepairProdOrderItem repairProdOrderItem = (RepairProdOrderItem) repairProdOrderManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, RepairProdOrderItem.NODENAME,
							logonUser.getClient(), null);
			RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
					.getEntityNodeByKey(repairProdOrderItem.getRootNodeUUID(), IServiceEntityNodeFieldConstant.UUID,
							RepairProdOrder.NODENAME, logonUser.getClient(), null);
			RepairProdOrderItemServiceModel repairProdOrderItemServiceModel = (RepairProdOrderItemServiceModel) repairProdOrderManager
					.loadServiceModule(RepairProdOrderItemServiceModel.class, repairProdOrderItem);
			List<RepairProdOrderItemServiceModel> requestList = ServiceCollectionsHelper.asList(repairProdOrderItemServiceModel);
			RepairProdOrderItemServiceUIModel repairProdOrderItemServiceUIModel = refreshLoadSeviceUIModel(
					repairProdOrderItemServiceModel.getRepairProdOrderItem().getUuid(), ISystemActionCode.ACID_EDIT, true,
					logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(repairProdOrderItemServiceUIModel);
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException | MaterialException | ServiceComExecuteException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/getStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getStatus() {
		try {
			Map<Integer, String> statusMap = repairProdOrderManager.initStatusMap(logonActionController.getLanguageCode());
			return repairProdOrderManager.getDefaultSelectMap(statusMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String checkDuplicateID(@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest, repairProdOrderManager);
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
			RepairProdOrderItem repairProdOrderItem = (RepairProdOrderItem) repairProdOrderManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, RepairProdOrderItem.NODENAME,
							logonUser.getClient(), null);
			String baseUUID = repairProdOrderItem.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<ServiceEntityNode>();
			lockSEList.add(repairProdOrderItem);
			List<ServiceEntityNode> lockResult = lockObjectManager.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager
					.genJSONLockCheckResult(lockResult, repairProdOrderItem.getName(), repairProdOrderItem.getId(), baseUUID);

		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String exitEditor(@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
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
			RepairProdOrderItem repairProdOrderItem = (RepairProdOrderItem) repairProdOrderManager
					.getEntityNodeByUUID(uuid, RepairProdOrderItem.NODENAME, logonUser.getClient());
			List<ServiceDocumentExtendUIModel> docFlowList = serviceDocumentComProxy
					.getDocFlowList(repairProdOrderItem, repairProdOrderManager, ISystemActionCode.ACID_VIEW,
							logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONArray(docFlowList);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

}
