package com.company.IntelligentPlatform.production.controller;

import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreItemSearchModel;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreItemManager;
import com.company.IntelligentPlatform.production.dto.*;
import com.company.IntelligentPlatform.production.service.*;
import com.company.IntelligentPlatform.production.model.ProdPickingOrder;
import com.company.IntelligentPlatform.production.model.ProdPickingRefOrderItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreItemUIModel;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNodeLastUpdateTimeCompare;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "prodPickingRefOrderItemEditorController")
@RequestMapping(value = "/prodPickingRefOrderItem")
public class ProdPickingRefOrderItemEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = ProdPickingOrder.SENAME;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected WarehouseStoreItemManager warehouseStoreItemManager;

	@Autowired
	protected ProdPickingRefOrderItemServiceUIModelExtension prodPickingRefOrderItemServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ProdPickingOrderManager prodPickingOrderManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected ProdPickingRefOrderltemManager prodPickingRefOrderltemManager;

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
		try {
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ProdPickingRefOrderItem prodPickingRefOrderItem = (ProdPickingRefOrderItem) prodPickingOrderManager
					.getEntityNodeByKey(request.getUuid(), IServiceEntityNodeFieldConstant.UUID, ProdPickingRefOrderItem.NODENAME,
							logonUser.getClient(), null);
			List<PageHeaderModel> pageHeaderModelList = prodPickingRefOrderltemManager
					.getPageHeaderModelList(prodPickingRefOrderItem, logonUser.getClient());
			return ServiceJSONParser.genDefOKJSONArray(pageHeaderModelList);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	ProdPickingRefOrderItemServiceUIModel parseToServiceUIModel(String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		classMap.put("prodPickingRefMaterialItemUIModelList", ProdPickingRefMaterialItemServiceUIModel.class);
		return (ProdPickingRefOrderItemServiceUIModel) JSONObject
				.toBean(jsonObject, ProdPickingRefOrderItemServiceUIModel.class, classMap);
	}

	@RequestMapping(value = "/getInStockItemList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getInStockItemList(String baseUUID) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = prodPickingOrderManager.getReservedInStockStoreItemList(baseUUID,
					IServiceEntityNodeFieldConstant.PARENTNODEUUID,
					logonUser.getClient());
			rawList.sort(new ServiceEntityNodeLastUpdateTimeCompare());
			List<WarehouseStoreItemUIModel> warehouseStoreItemUIModelList = warehouseStoreItemManager.
					getStoreModuleListCore(rawList, logonActionController.getLogonInfo(),
					WarehouseStoreItemSearchModel.BATCH_MODE_DISPLAY);
			return ServiceJSONParser.genDefOKJSONArray(warehouseStoreItemUIModelList);
		} catch (AuthorizationException | LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	private ProdPickingRefOrderItemServiceUIModel refreshLoadServiceUIModel(String uuid, String acId,
																			boolean postFlag, LogonInfo logonInfo)
			throws ServiceEntityConfigureException, ServiceModuleProxyException, AuthorizationException, ServiceUIModuleProxyException, LogonInfoException, MaterialException {
		ProdPickingRefOrderItem prodPickingRefOrderItem = (ProdPickingRefOrderItem) prodPickingOrderManager
				.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, ProdPickingRefOrderItem.NODENAME,
						logonInfo.getClient(), null);
		if (!ServiceEntityStringHelper.checkNullString(acId)) {
			boolean checkAuthor = serviceBasicUtilityController
					.checkTargetDataAccess(logonInfo.getLogonUser(), prodPickingRefOrderItem, acId,
							logonInfo.getAuthorizationActionCodeMap());
			if (!checkAuthor) {
				throw new AuthorizationException(AuthorizationException.TYPE_NO_AUTHORIZATION);
			}
		}
		/* Refresh service model */
		ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel = (ProdPickingRefOrderItemServiceModel) prodPickingOrderManager
				.loadServiceModule(ProdPickingRefOrderItemServiceModel.class, prodPickingRefOrderItem);
		prodPickingOrderManager.setGrossOrderCost(prodPickingRefOrderItemServiceModel);
		if(postFlag){
            try {
                prodPickingRefOrderltemManager.postUpdateRefOrderItem(prodPickingRefOrderItemServiceModel);
            } catch (DocActionException e) {
                throw new RuntimeException(e);
            }
        }
		ProdPickingRefOrderItemServiceUIModel prodPickingRefOrderItemServiceUIModel = (ProdPickingRefOrderItemServiceUIModel) prodPickingOrderManager
				.genServiceUIModuleFromServiceModel(ProdPickingRefOrderItemServiceUIModel.class,
						ProdPickingRefOrderItemServiceModel.class, prodPickingRefOrderItemServiceModel,
						prodPickingRefOrderItemServiceUIModelExtension, logonInfo);
		return prodPickingRefOrderItemServiceUIModel;
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String saveModuleService(@RequestBody String request) {
		ProdPickingRefOrderItemServiceUIModel prodPickingRefOrderItemServiceUIModel = parseToServiceUIModel(request);
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
			ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel = (ProdPickingRefOrderItemServiceModel) prodPickingOrderManager
					.genServiceModuleFromServiceUIModel(ProdPickingRefOrderItemServiceModel.class,
							ProdPickingRefOrderItemServiceUIModel.class, prodPickingRefOrderItemServiceUIModel,
							prodPickingRefOrderItemServiceUIModelExtension);
			// update report item list
			prodPickingOrderManager
					.updateServiceModuleWithDelete(ProdPickingRefOrderItemServiceModel.class, prodPickingRefOrderItemServiceModel,
							logonUser.getUuid(), organizationUUID);
			prodPickingRefOrderltemManager
					.postUpdatePickingRefOrderItemAsyncWrapper(prodPickingRefOrderItemServiceModel, logonUser.getUuid(),
							organizationUUID);
			prodPickingRefOrderItemServiceUIModel = refreshLoadServiceUIModel(
					prodPickingRefOrderItemServiceModel.getProdPickingRefOrderItem().getUuid(),
					ISystemActionCode.ACID_EDIT, true,
					logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(prodPickingRefOrderItemServiceUIModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException | MaterialException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
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
			ProdPickingOrder prodPickingOrder = (ProdPickingOrder) prodPickingOrderManager
					.getEntityNodeByKey(request.getBaseUUID(), IServiceEntityNodeFieldConstant.UUID, ProdPickingOrder.NODENAME,
							logonUser.getClient(), null);
			ProdPickingRefOrderItem prodPickingRefOrderItem = (ProdPickingRefOrderItem) prodPickingOrderManager
					.newEntityNode(prodPickingOrder, ProdPickingRefOrderItem.NODENAME);
			ProdPickingRefOrderItemUIModel prodPickingRefOrderItemUIModel = new ProdPickingRefOrderItemUIModel();
			prodPickingOrderManager.convProdPickingRefOrderItemToUI(prodPickingRefOrderItem, prodPickingRefOrderItemUIModel);
			return ServiceJSONParser.genDefOKJSONObject(prodPickingRefOrderItemUIModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String checkDuplicateID(@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest, prodPickingOrderManager);
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
			ProdPickingRefOrderItem prodPickingRefOrderItem = (ProdPickingRefOrderItem) prodPickingOrderManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, ProdPickingRefOrderItem.NODENAME,
							logonUser.getClient(), null);
			String baseUUID = prodPickingRefOrderItem.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			lockSEList.add(prodPickingRefOrderItem);
			List<ServiceEntityNode> lockResult = lockObjectManager.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager
					.genJSONLockCheckResult(lockResult, prodPickingRefOrderItem.getName(), prodPickingRefOrderItem.getId(), baseUUID);

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
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ProdPickingRefOrderItem prodPickingRefOrderItem = (ProdPickingRefOrderItem) prodPickingOrderManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, ProdPickingRefOrderItem.NODENAME,
							logonUser.getClient(), null);
			LogonInfo logonInfo = logonActionController.getLogonInfo();
			boolean checkAuthor = serviceBasicUtilityController
					.checkTargetDataAccess(logonInfo.getLogonUser(), prodPickingRefOrderItem, ISystemActionCode.ACID_EDIT,
							logonInfo.getAuthorizationActionCodeMap());
			if (!checkAuthor) {
				throw new AuthorizationException(AuthorizationException.TYPE_NO_AUTHORIZATION);
			}
			return ServiceJSONParser.genDefOKJSONObject(prodPickingRefOrderItem);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/loadFromProdOrderService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadFromProdOrderService(String baseUUID) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> prodPickingRefOrderItemList = prodPickingOrderManager
					.getEntityNodeListByKey(baseUUID, "refProdOrderUUID", ProdPickingRefOrderItem.NODENAME, logonUser.getClient(),
							null);
			List<ProdPickingRefOrderItemServiceUIModel> resultList = new ArrayList<>();
			if (!ServiceCollectionsHelper.checkNullList(prodPickingRefOrderItemList)) {
				for (ServiceEntityNode seNode : prodPickingRefOrderItemList) {
					ProdPickingRefOrderItem prodPickingRefOrderItem = (ProdPickingRefOrderItem) seNode;
					ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel = (ProdPickingRefOrderItemServiceModel) prodPickingOrderManager
							.loadServiceModule(ProdPickingRefOrderItemServiceModel.class, prodPickingRefOrderItem);
					ProdPickingRefOrderItemServiceUIModel prodPickingRefOrderItemServiceUIModel = (ProdPickingRefOrderItemServiceUIModel) prodPickingOrderManager
							.genServiceUIModuleFromServiceModel(ProdPickingRefOrderItemServiceUIModel.class,
									ProdPickingRefOrderItemServiceModel.class, prodPickingRefOrderItemServiceModel,
									prodPickingRefOrderItemServiceUIModelExtension, logonActionController.getLogonInfo());
					resultList.add(prodPickingRefOrderItemServiceUIModel);
				}
			}
			return ServiceJSONParser.genDefOKJSONArray(resultList);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
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
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			ProdPickingRefOrderItem prodPickingRefOrderItem = (ProdPickingRefOrderItem) prodPickingOrderManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, ProdPickingRefOrderItem.NODENAME,
							logonUser.getClient(), null);
			lockSEList.add(prodPickingRefOrderItem);
			lockObjectManager
					.lockServiceEntityList(lockSEList, logonUser, logonActionController.getOrganizationByUser(logonUser.getUuid()));
			ProdPickingRefOrderItemServiceUIModel prodPickingRefOrderItemServiceUIModel = refreshLoadServiceUIModel(uuid,
					ISystemActionCode.ACID_EDIT, false, logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(prodPickingRefOrderItemServiceUIModel);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LockObjectFailureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException | MaterialException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/loadModuleWithPostService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleWithPostService(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ProdPickingRefOrderItem prodPickingRefOrderItem = (ProdPickingRefOrderItem) prodPickingOrderManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, ProdPickingRefOrderItem.NODENAME,
							logonUser.getClient(), null);
			ProdPickingRefOrderItemServiceUIModel prodPickingRefOrderItemServiceUIModel = refreshLoadServiceUIModel(uuid,
					ISystemActionCode.ACID_EDIT, true, logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(prodPickingRefOrderItemServiceUIModel);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LockObjectFailureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException | MaterialException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/loadModuleWithPostUpdateService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleWithPostUpdateService(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ProdPickingRefOrderItem prodPickingRefOrderItem = (ProdPickingRefOrderItem) prodPickingOrderManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, ProdPickingRefOrderItem.NODENAME,
							logonUser.getClient(), null);
			ProdPickingRefOrderItemServiceUIModel prodPickingRefOrderItemServiceUIModel = refreshLoadServiceUIModel(uuid,
					ISystemActionCode.ACID_EDIT, true, logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(prodPickingRefOrderItemServiceUIModel);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LockObjectFailureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException | MaterialException e) {
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
			ProdPickingRefOrderItem prodPickingRefOrderItem = (ProdPickingRefOrderItem) prodPickingOrderManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, ProdPickingRefOrderItem.NODENAME,
							logonUser.getClient(), null);
			ProdPickingRefOrderItemServiceUIModel prodPickingRefOrderItemServiceUIModel = refreshLoadServiceUIModel(uuid,
					ISystemActionCode.ACID_EDIT, false, logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(prodPickingRefOrderItemServiceUIModel);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LockObjectFailureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException | MaterialException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String exitEditor(@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
