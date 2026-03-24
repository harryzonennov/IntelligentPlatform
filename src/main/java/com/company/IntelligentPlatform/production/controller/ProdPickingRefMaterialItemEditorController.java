package com.company.IntelligentPlatform.production.controller;

import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemGenRequestHelper;
import com.company.IntelligentPlatform.production.dto.ProdPickingRefMaterialItemServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProdPickingRefMaterialItemUIModel;
import com.company.IntelligentPlatform.production.service.*;
import com.company.IntelligentPlatform.production.model.ProdPickingOrder;
import com.company.IntelligentPlatform.production.model.ProdPickingRefMaterialItem;

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
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
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
import com.company.IntelligentPlatform.common.model.ServiceModuleConvertPara;

@Scope("session")
@Controller(value = "prodPickingRefMaterialItemEditorController")
@RequestMapping(value = "/prodPickingRefMaterialItem")
public class ProdPickingRefMaterialItemEditorController extends
		SEEditorController {

	public static final String AOID_RESOURCE = ProdPickingOrder.SENAME;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected ProdPickingRefMaterialItemServiceUIModelExtension prodPickingRefMaterialItemServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ProdPickingOrderManager prodPickingOrderManager;

	@Autowired
	protected ProdPickingRefMaterialItemManager prodPickingRefMaterialItemManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;


	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPageHeaderModelList(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) prodPickingOrderManager
					.getEntityNodeByKey(request.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							ProdPickingRefMaterialItem.NODENAME,
							logonUser.getClient(), null);
			List<PageHeaderModel> pageHeaderModelList = prodPickingRefMaterialItemManager
					.getPageHeaderModelList(prodPickingRefMaterialItem,
							logonUser.getClient());
			return ServiceJSONParser
					.genDefOKJSONArray(pageHeaderModelList);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/loadFromProdOrderItemService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadFromProdOrderItemService(String baseUUID) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> prodPickingRefMaterialItemList = prodPickingRefMaterialItemManager
					.getSubRefMaterialItemListByOrderItem(baseUUID,
							logonUser.getClient());
			List<ProdPickingRefMaterialItemUIModel> resultList = new ArrayList<>();
			List<ServiceModuleConvertPara> addtionalConvertParaList = new ArrayList<ServiceModuleConvertPara>();
			if (!ServiceCollectionsHelper
					.checkNullList(prodPickingRefMaterialItemList)) {
				for (ServiceEntityNode seNode : prodPickingRefMaterialItemList) {
					ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) seNode;
					ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel = (ProdPickingRefMaterialItemUIModel) prodPickingOrderManager
							.genUIModelFromUIModelExtension(
									ProdPickingRefMaterialItemUIModel.class,
									prodPickingRefMaterialItemServiceUIModelExtension
											.genUIModelExtensionUnion().get(0),
									prodPickingRefMaterialItem, logonActionController.getLogonInfo(),
									addtionalConvertParaList);
					resultList.add(prodPickingRefMaterialItemUIModel);
				}
			}
			return ServiceJSONParser.genDefOKJSONArray(resultList);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/setFinishService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String setFinishService(@RequestBody String request) {
		ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel = parseToUIModel(request);
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
			Organization organization = logonActionController
					.getOrganizationByUser(logonUser.getUuid());
			if (organization != null) {
				organizationUUID = organization.getUuid();
			}
			List<ServiceEntityNode> rawSeNodeList = prodPickingOrderManager
					.genSeNodeListInExtensionUnion(
							prodPickingRefMaterialItemServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							ProdPickingRefMaterialItem.class,
							prodPickingRefMaterialItemUIModel);
			if(!ServiceCollectionsHelper.checkNullList(rawSeNodeList)){
				for(ServiceEntityNode seNode: rawSeNodeList){
					ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) seNode;
					prodPickingRefMaterialItemManager.refreshPickingRefMaterialItem(prodPickingRefMaterialItem, null);
				}
			}
			prodPickingOrderManager.updateSeNodeListWrapper(rawSeNodeList,
					logonUser.getUuid(), organizationUUID);
			String baseUUID = prodPickingRefMaterialItemUIModel.getUuid();
			ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) prodPickingOrderManager
					.getEntityNodeByKey(baseUUID,
							IServiceEntityNodeFieldConstant.UUID,
							ProdPickingRefMaterialItem.NODENAME,
							logonUser.getClient(), null);
			ProdPickingOrder prodPickingOrder = (ProdPickingOrder) prodPickingOrderManager
					.getEntityNodeByKey(
							prodPickingRefMaterialItem.getRootNodeUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							ProdPickingOrder.NODENAME, logonUser.getClient(),
							null);
			prodPickingOrderManager.setFinishPickingRefMaterialItem(
					prodPickingRefMaterialItem, prodPickingOrder, false,
					logonUser.getUuid(), organizationUUID);
			prodPickingRefMaterialItemUIModel = refreshLoadUIModel(
					prodPickingRefMaterialItemUIModel.getUuid(),
					ISystemActionCode.ACID_EDIT, false, logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(prodPickingRefMaterialItemUIModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | MaterialException | DocActionException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	public @RequestMapping(value = "/batchSetItemFinish", produces = "text/html;" + "charset=UTF-8")
	@ResponseBody
	String batchSetItemFinish(@RequestBody String request) {
		DeliveryMatItemBatchGenRequest genRequest = DeliveryMatItemGenRequestHelper.parseToGenRequest(request);
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> resultList = prodPickingOrderManager.batchSetItemFinishList(genRequest,
					logonUser.getClient(), logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONArray(resultList);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException | ServiceEntityInstallationException | NodeNotFoundException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (SearchConfigureException | ProductionOrderException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	public ProdPickingRefMaterialItemUIModel parseToUIModel(String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel = (ProdPickingRefMaterialItemUIModel) JSONObject
				.toBean(jsonObject, ProdPickingRefMaterialItemUIModel.class,
						classMap);
		return prodPickingRefMaterialItemUIModel;
	}

	private ProdPickingRefMaterialItemUIModel refreshLoadUIModel(String uuid,
			String acId, boolean postUpdateFlag, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, LogonInfoException, MaterialException, AuthorizationException, DocActionException {
		ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) prodPickingOrderManager
				.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID,
						ProdPickingRefMaterialItem.NODENAME, logonInfo.getClient(), null);
		if (!ServiceEntityStringHelper.checkNullString(acId)) {
			boolean checkAuthor = serviceBasicUtilityController
					.checkTargetDataAccess(logonInfo.getLogonUser(),
							prodPickingRefMaterialItem, acId,
							logonInfo.getAuthorizationActionCodeMap());
			if (!checkAuthor) {
				throw new AuthorizationException(
						AuthorizationException.TYPE_NO_AUTHORIZATION);
			}
		}
		if(postUpdateFlag){
			prodPickingRefMaterialItemManager.updateRefMaterialItem(prodPickingRefMaterialItem);
		}
		ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel = (ProdPickingRefMaterialItemUIModel) prodPickingOrderManager
				.genUIModelFromUIModelExtension(
						ProdPickingRefMaterialItemUIModel.class,
						prodPickingRefMaterialItemServiceUIModelExtension
								.genUIModelExtensionUnion().get(0),
						prodPickingRefMaterialItem, logonActionController
								.getLogonInfo(), null);
		return prodPickingRefMaterialItemUIModel;
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel = parseToUIModel(request);
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
			Organization organization = logonActionController
					.getOrganizationByUser(logonUser.getUuid());
			if (organization != null) {
				organizationUUID = organization.getUuid();
			}
			List<ServiceEntityNode> rawSeNodeList = prodPickingOrderManager
					.genSeNodeListInExtensionUnion(
							prodPickingRefMaterialItemServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							ProdPickingRefMaterialItem.class,
							prodPickingRefMaterialItemUIModel);
			if(!ServiceCollectionsHelper.checkNullList(rawSeNodeList)){
				for(ServiceEntityNode seNode: rawSeNodeList){
					ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) seNode;
					prodPickingRefMaterialItemManager.refreshPickingRefMaterialItem(prodPickingRefMaterialItem, null);
				}
			}
			prodPickingOrderManager.updateSeNodeListWrapper(rawSeNodeList,
					logonUser.getUuid(), organizationUUID);
			prodPickingRefMaterialItemUIModel = refreshLoadUIModel(
					prodPickingRefMaterialItemUIModel.getUuid(),
					ISystemActionCode.ACID_EDIT, false, logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(prodPickingRefMaterialItemUIModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceModuleProxyException | MaterialException | DocActionException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ProdPickingOrder prodPickingOrder = (ProdPickingOrder) prodPickingOrderManager
					.getEntityNodeByKey(request.getBaseUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							ProdPickingOrder.NODENAME, logonUser.getClient(),
							null);
			ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) prodPickingOrderManager
					.newEntityNode(prodPickingOrder,
							ProdPickingRefMaterialItem.NODENAME);
			ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel = new ProdPickingRefMaterialItemUIModel();
			prodPickingRefMaterialItemManager.convProdPickingRefMaterialItemToUI(
					prodPickingRefMaterialItem,
					prodPickingRefMaterialItemUIModel, logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(prodPickingRefMaterialItemUIModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException | ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				prodPickingOrderManager);
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
			ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) prodPickingOrderManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProdPickingRefMaterialItem.NODENAME,
							logonUser.getClient(), null);
			String baseUUID = prodPickingRefMaterialItem.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<ServiceEntityNode>();
			lockSEList.add(prodPickingRefMaterialItem);
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					prodPickingRefMaterialItem.getName(),
					prodPickingRefMaterialItem.getId(), baseUUID);

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
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) prodPickingOrderManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProdPickingRefMaterialItem.NODENAME,
							logonUser.getClient(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(prodPickingRefMaterialItem);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());

		}
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleEditService(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) prodPickingOrderManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProdPickingRefMaterialItem.NODENAME,
							logonUser.getClient(), null);
			if (prodPickingRefMaterialItem == null) {
				throw new ProductionDataException(
						ProductionDataException.PARA_NO_DATA, uuid);
			}
			ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel = refreshLoadUIModel(
					uuid,
					ISystemActionCode.ACID_EDIT, false, logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(prodPickingRefMaterialItemUIModel);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ProductionDataException | MaterialException | DocActionException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}
	@RequestMapping(value = "/loadModuleWithPostUpdateService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleWithPostUpdateService(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) prodPickingOrderManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProdPickingRefMaterialItem.NODENAME,
							logonUser.getClient(), null);
			if (prodPickingRefMaterialItem == null) {
				throw new ProductionDataException(
						ProductionDataException.PARA_NO_DATA, uuid);
			}
			ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel = refreshLoadUIModel(
					uuid,
					ISystemActionCode.ACID_EDIT, true, logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(prodPickingRefMaterialItemUIModel);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ProductionDataException | MaterialException | DocActionException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}
	
	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleViewService(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) prodPickingOrderManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProdPickingRefMaterialItem.NODENAME,
							logonUser.getClient(), null);
			if (prodPickingRefMaterialItem == null) {
				throw new ProductionDataException(
						ProductionDataException.PARA_NO_DATA, uuid);
			}
			ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel = refreshLoadUIModel(
					uuid,
					ISystemActionCode.ACID_VIEW, false, logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(prodPickingRefMaterialItemUIModel);
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | MaterialException | ProductionDataException | DocActionException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
    }


	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "/getRefNextOrderType", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getRefNextOrderType() {
		try {
			Map<Integer, String> refNextOrderTypeMap = prodPickingOrderManager
					.initDocumentTypeMap(logonActionController.getLanguageCode());
			return prodPickingOrderManager
					.getDefaultSelectMap(refNextOrderTypeMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}
	}

	@RequestMapping(value = "/getItemStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getItemStatus() {
		try {
			Map<Integer, String> itemStatusMap = prodPickingOrderManager
					.initItemStatusMap(logonActionController.getLanguageCode());
			return prodPickingOrderManager.getDefaultSelectMap(itemStatusMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}
	}

	@RequestMapping(value = "/getDocFlowList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocFlowList(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) prodPickingOrderManager
					.getEntityNodeByUUID(uuid,
							ProdPickingRefMaterialItem.NODENAME,
							logonUser.getClient());
			List<ServiceDocumentExtendUIModel> docFlowList = serviceDocumentComProxy
					.getDocFlowList(prodPickingRefMaterialItem,
							prodPickingOrderManager, ISystemActionCode.ACID_VIEW,
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
