package com.company.IntelligentPlatform.production.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemGenRequestHelper;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryException;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryServiceModel;
import com.company.IntelligentPlatform.production.dto.ProdOrderItemReqProposalServiceUIModel;
import com.company.IntelligentPlatform.production.dto.ProductionOrderItemServiceUIModel;
import com.company.IntelligentPlatform.production.dto.ProductionOrderItemServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProductionOrderItemUIModel;
import com.company.IntelligentPlatform.production.dto.ProductionOrderUIModel;
import com.company.IntelligentPlatform.production.service.ProductionOrderException;
import com.company.IntelligentPlatform.production.service.ProductionOrderItemManager;
import com.company.IntelligentPlatform.production.service.ProductionOrderItemServiceModel;
import com.company.IntelligentPlatform.production.service.ProductionOrderManager;
import com.company.IntelligentPlatform.production.service.ProductionOrderToCrossOutboundProxy;
import com.company.IntelligentPlatform.production.model.ProductionOrder;
import com.company.IntelligentPlatform.production.model.ProductionOrderItem;

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
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
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


@Scope("session")
@Controller(value = "productionOrderItemEditorController")
@RequestMapping(value = "/productionOrderItem")
public class ProductionOrderItemEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = ProductionOrderEditorController.AOID_RESOURCE;

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
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected ProductionOrderItemManager productionOrderItemManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected ProductionOrderToCrossOutboundProxy productionOrderToCrossOutboundProxy;

	@Autowired
	protected ProductionOrderItemServiceUIModelExtension productionOrderItemServiceUIModelExtension;

	private ProductionOrderItemServiceUIModel parseToServiceUIModel(String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		classMap.put("prodOrderItemReqProposalUIModelList", ProdOrderItemReqProposalServiceUIModel.class);
		ProductionOrderItemServiceUIModel productionOrderItemServiceUIModel = (ProductionOrderItemServiceUIModel) JSONObject
				.toBean(jsonObject, ProductionOrderItemServiceUIModel.class, classMap);
		return productionOrderItemServiceUIModel;
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String saveModuleService(@RequestBody String request) {
		ProductionOrderItemServiceUIModel productionOrderItemServiceUIModel = parseToServiceUIModel(request);
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
			ProductionOrderItemServiceModel productionOrderItemServiceModel = (ProductionOrderItemServiceModel) productionOrderManager
					.genServiceModuleFromServiceUIModel(ProductionOrderItemServiceModel.class, ProductionOrderItemServiceUIModel.class,
							productionOrderItemServiceUIModel, productionOrderItemServiceUIModelExtension);
			productionOrderItemManager
					.postUpdateProductionOrderItemAsyncWrapper(productionOrderItemServiceModel, logonUser.getUuid(), organizationUUID);
			productionOrderManager
					.updateServiceModuleWithDelete(ProductionOrderItemServiceModel.class, productionOrderItemServiceModel,
							logonUser.getUuid(), organizationUUID);
			// Refresh service model			
			productionOrderItemServiceUIModel = refreshLoadSeviceUIModel(
					productionOrderItemServiceModel.getProductionOrderItem().getUuid(), ISystemActionCode.ACID_EDIT, false,
					logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(productionOrderItemServiceUIModel);
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
			ProductionOrder productionOrder = (ProductionOrder) productionOrderManager
					.getEntityNodeByKey(request.getBaseUUID(), IServiceEntityNodeFieldConstant.UUID, ProductionOrder.NODENAME,
							logonUser.getClient(), null);
			ProductionOrderItem productionOrderItem = (ProductionOrderItem) productionOrderManager
					.newEntityNode(productionOrder, ProductionOrderItem.NODENAME);
			ProductionOrderItemServiceModel productionOrderItemServiceModel = new ProductionOrderItemServiceModel();
			productionOrderItemServiceModel.setProductionOrderItem(productionOrderItem);
			ProductionOrderItemServiceUIModel productionOrderItemServiceUIModel = (ProductionOrderItemServiceUIModel) productionOrderManager
					.genServiceUIModuleFromServiceModel(ProductionOrderItemServiceUIModel.class, ProductionOrderItemServiceModel.class,
							productionOrderItemServiceModel, productionOrderItemServiceUIModelExtension,
							logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(productionOrderItemServiceUIModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
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

	@RequestMapping(value = "/loadLeanViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadLeanViewService(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ProductionOrderItem productionOrderItem = (ProductionOrderItem) productionOrderManager
					.getEntityNodeByUUID(uuid, ProductionOrderItem.NODENAME, logonUser.getClient());
			if (productionOrderItem == null) {
				throw new ProductionOrderException(ProductionOrderException.PARA_NO_PRODORDER, uuid);
			}
			ProductionOrderItemUIModel productionOrderItemUIModel = (ProductionOrderItemUIModel) productionOrderManager
					.genUIModelFromUIModelExtension(ProductionOrderUIModel.class,
							productionOrderItemServiceUIModelExtension.genUIModelExtensionUnion().get(0), productionOrderItem,
							logonActionController.getLogonInfo(), null);
			return ServiceJSONParser.genDefOKJSONObject(productionOrderItemUIModel);
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
			ProductionOrderItem productionOrderItem = (ProductionOrderItem) productionOrderManager
					.getEntityNodeByKey(request.getUuid(), IServiceEntityNodeFieldConstant.UUID, ProductionOrderItem.NODENAME,
							logonUser.getClient(), null);
			List<PageHeaderModel> pageHeaderModelList = productionOrderItemManager
					.getPageHeaderModelList(productionOrderItem, logonUser.getClient());
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
			List<OutboundDeliveryServiceModel> resultList = productionOrderToCrossOutboundProxy
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
			ProductionOrderItem productionOrderItem = (ProductionOrderItem) productionOrderManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, ProductionOrderItem.NODENAME,
							logonUser.getClient(), null);
			return ServiceJSONParser.genDefOKJSONObject(productionOrderItem);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	private ProductionOrderItemServiceUIModel refreshLoadSeviceUIModel(String uuid, String acId, boolean postUpdateFlag,
			LogonInfo logonInfo) throws ServiceEntityConfigureException, ServiceModuleProxyException, ServiceUIModuleProxyException,
			LogonInfoException, MaterialException, AuthorizationException, ServiceComExecuteException {
		ProductionOrderItem productionOrderItem = (ProductionOrderItem) productionOrderManager
				.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, ProductionOrderItem.NODENAME, logonInfo.getClient(),
						null);
		if (!ServiceEntityStringHelper.checkNullString(acId)) {
			boolean checkAuthor = serviceBasicUtilityController
					.checkTargetDataAccess(logonInfo.getLogonUser(), productionOrderItem, acId,
							logonInfo.getAuthorizationActionCodeMap());
			if (!checkAuthor) {
				throw new AuthorizationException(AuthorizationException.TYPE_NO_AUTHORIZATION);
			}
		}
		ProductionOrderItemServiceModel productionOrderItemServiceModel = (ProductionOrderItemServiceModel) productionOrderManager
				.loadServiceModule(ProductionOrderItemServiceModel.class, productionOrderItem);
		if (postUpdateFlag) {
			// Refresh and update production order's information into DB
            try {
                productionOrderItemManager.postUpdateProductionOrderItem(productionOrderItemServiceModel, logonInfo.getRefUserUUID(),
                        logonInfo.getResOrgUUID());
            } catch (DocActionException e) {
                throw new RuntimeException(e);
            }
        }
		ProductionOrderItemServiceUIModel productionOrderItemServiceUIModel = (ProductionOrderItemServiceUIModel) productionOrderManager
				.genServiceUIModuleFromServiceModel(ProductionOrderItemServiceUIModel.class, ProductionOrderItemServiceModel.class,
						productionOrderItemServiceModel, productionOrderItemServiceUIModelExtension,
						logonActionController.getLogonInfo());
		return productionOrderItemServiceUIModel;
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
			ProductionOrderItem productionOrderItem = (ProductionOrderItem) productionOrderManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, ProductionOrderItem.NODENAME,
							logonUser.getClient(), null);
			ProductionOrder productionOrder = (ProductionOrder) productionOrderManager
					.getEntityNodeByKey(productionOrderItem.getRootNodeUUID(), IServiceEntityNodeFieldConstant.UUID,
							ProductionOrder.NODENAME, logonUser.getClient(), null);
			ProductionOrderItemServiceModel productionOrderItemServiceModel = (ProductionOrderItemServiceModel) productionOrderManager
					.loadServiceModule(ProductionOrderItemServiceModel.class, productionOrderItem);
			List<ProductionOrderItemServiceModel> requestList = ServiceCollectionsHelper.asList(productionOrderItemServiceModel);
			ProductionOrderItemServiceUIModel productionOrderItemServiceUIModel = refreshLoadSeviceUIModel(
					productionOrderItemServiceModel.getProductionOrderItem().getUuid(), ISystemActionCode.ACID_EDIT, true,
					logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(productionOrderItemServiceUIModel);
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
			ProductionOrderItem productionOrderItem = (ProductionOrderItem) productionOrderManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, ProductionOrderItem.NODENAME,
							logonUser.getClient(), null);
			ProductionOrder productionOrder = (ProductionOrder) productionOrderManager
					.getEntityNodeByKey(productionOrderItem.getRootNodeUUID(), IServiceEntityNodeFieldConstant.UUID,
							ProductionOrder.NODENAME, logonUser.getClient(), null);
			ProductionOrderItemServiceModel productionOrderItemServiceModel = (ProductionOrderItemServiceModel) productionOrderManager
					.loadServiceModule(ProductionOrderItemServiceModel.class, productionOrderItem);
			List<ProductionOrderItemServiceModel> requestList = ServiceCollectionsHelper.asList(productionOrderItemServiceModel);
			ProductionOrderItemServiceUIModel productionOrderItemServiceUIModel = refreshLoadSeviceUIModel(
					productionOrderItemServiceModel.getProductionOrderItem().getUuid(), ISystemActionCode.ACID_EDIT, true,
					logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(productionOrderItemServiceUIModel);
		} catch (AuthorizationException | LogonInfoException e) {
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
			Map<Integer, String> statusMap = productionOrderManager.initStatusMap(logonActionController.getLanguageCode());
			return productionOrderManager.getDefaultSelectMap(statusMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
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
			ProductionOrderItem productionOrderItem = (ProductionOrderItem) productionOrderManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, ProductionOrderItem.NODENAME,
							logonUser.getClient(), null);
			String baseUUID = productionOrderItem.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<ServiceEntityNode>();
			lockSEList.add(productionOrderItem);
			List<ServiceEntityNode> lockResult = lockObjectManager.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager
					.genJSONLockCheckResult(lockResult, productionOrderItem.getName(), productionOrderItem.getId(), baseUUID);

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
			ProductionOrderItem productionOrderItem = (ProductionOrderItem) productionOrderManager
					.getEntityNodeByUUID(uuid, ProductionOrderItem.NODENAME, logonUser.getClient());
			List<ServiceDocumentExtendUIModel> docFlowList = serviceDocumentComProxy
					.getDocFlowList(productionOrderItem, productionOrderManager, ISystemActionCode.ACID_VIEW,
							logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONArray(docFlowList);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

}
