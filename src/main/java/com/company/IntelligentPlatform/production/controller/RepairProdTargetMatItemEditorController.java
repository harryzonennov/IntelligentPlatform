package com.company.IntelligentPlatform.production.controller;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.service.PurchaseContractException;
import com.company.IntelligentPlatform.logistics.model.InboundDelivery;
import com.company.IntelligentPlatform.production.dto.*;
import com.company.IntelligentPlatform.production.service.*;
import com.company.IntelligentPlatform.production.model.RepairProdOrderItem;
import com.company.IntelligentPlatform.production.model.RepairProdTargetItemAttachment;
import com.company.IntelligentPlatform.production.model.RepairProdTargetMatItem;
import com.company.IntelligentPlatform.production.model.RepairProdOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductException;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
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
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchContextBuilder;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
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

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Scope("session")
@Controller(value = "repairProdTargetMatItemEditorController")
@RequestMapping(value = "/repairProdTargetMatItem")
public class RepairProdTargetMatItemEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = RepairProdOrderEditorController.AOID_RESOURCE;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected RepairProdOrderManager repairProdOrderManager;

	@Autowired
	protected RepairProdTargetMatItemManager repairProdTargetMatItemManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected RepairProdTargetMatItemServiceUIModelExtension repairProdTargetMatItemServiceUIModelExtension;

	@Autowired
	protected RepairProdTargetItemToCrossInboundProxy repairProdTargetItemToCrossInboundProxy;

	@Autowired
	protected SplitMatItemProxy splitMatItemProxy;

	private RepairProdTargetMatItemServiceUIModel parseToServiceUIModel(String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("repairProdTarSubItemUIModelList", RepairProdTarSubItemUIModel.class);
		classMap.put("repairProdTargetItemAttachmentUIModelList", RepairProdTargetItemAttachmentUIModel.class);
		return (RepairProdTargetMatItemServiceUIModel) JSONObject
				.toBean(jsonObject, RepairProdTargetMatItemServiceUIModel.class, classMap);
	}

	private RepairProdTargetMatItemServiceModel updateServiceUIModelCore(
			RepairProdTargetMatItemServiceUIModel repairProdTargetMatItemServiceUIModel, String logonUserUUID, String organizationUUID)
			throws ServiceModuleProxyException, ServiceEntityConfigureException, ServiceUIModuleProxyException {
		RepairProdTargetMatItemServiceModel repairProdTargetMatItemServiceModel = (RepairProdTargetMatItemServiceModel) repairProdOrderManager
				.genServiceModuleFromServiceUIModel(RepairProdTargetMatItemServiceModel.class,
						RepairProdTargetMatItemServiceUIModel.class, repairProdTargetMatItemServiceUIModel,
						repairProdTargetMatItemServiceUIModelExtension);
		repairProdOrderManager
				.updateServiceModuleWithDelete(RepairProdTargetMatItemServiceModel.class, repairProdTargetMatItemServiceModel,
						logonUserUUID, organizationUUID);
		return repairProdTargetMatItemServiceModel;
	}


	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String saveModuleService(@RequestBody String request) {
		RepairProdTargetMatItemServiceUIModel repairProdTargetMatItemServiceUIModel = parseToServiceUIModel(request);
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
			// Check duplicate firstly
			RepairProdTargetMatItemServiceModel repairProdTargetMatItemServiceModel = (RepairProdTargetMatItemServiceModel) repairProdOrderManager
					.genServiceModuleFromServiceUIModel(RepairProdTargetMatItemServiceModel.class,
							RepairProdTargetMatItemServiceUIModel.class, repairProdTargetMatItemServiceUIModel,
							repairProdTargetMatItemServiceUIModelExtension);
			repairProdTargetMatItemManager.preCheckUpdate(repairProdTargetMatItemServiceModel.getRepairProdTargetMatItem());
			repairProdOrderManager
					.updateServiceModuleWithDelete(RepairProdTargetMatItemServiceModel.class, repairProdTargetMatItemServiceModel,
							logonUser.getUuid(), organizationUUID);
			// Refresh service model			
			repairProdTargetMatItemServiceUIModel = refreshLoadSeviceUIModel(
					repairProdTargetMatItemServiceModel.getRepairProdTargetMatItem().getUuid(), ISystemActionCode.ACID_EDIT,
					logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(repairProdTargetMatItemServiceUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException | ProdOrderTargetItemException | RegisteredProductException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/setCancelService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String setCancelService(@RequestBody String request) {
		try {
			RepairProdTargetMatItemServiceUIModel repairProdTargetMatItemServiceUIModel = parseToServiceUIModel(request);
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
			RepairProdTargetMatItemServiceModel repairProdTargetMatItemServiceModel = updateServiceUIModelCore(
					repairProdTargetMatItemServiceUIModel, logonUser.getUuid(), organizationUUID);
			// Refresh service model
			RepairProdTargetMatItem repairProdTargetMatItem = repairProdTargetMatItemServiceModel.getRepairProdTargetMatItem();
			repairProdTargetMatItemManager
					.setCancelStatus(repairProdTargetMatItem,logonUser.getUuid(), organizationUUID);
			repairProdTargetMatItemServiceUIModel = refreshLoadSeviceUIModel(
					repairProdTargetMatItemServiceModel.getRepairProdTargetMatItem().getUuid(), ISystemActionCode.ACID_EDIT,
					logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(repairProdTargetMatItemServiceUIModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | MaterialException | ServiceUIModuleProxyException | ProdOrderTargetItemException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/setRepairDoneService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String setRepairDoneService(@RequestBody String request) {
		try {
			RepairProdTargetMatItemServiceUIModel repairProdTargetMatItemServiceUIModel = parseToServiceUIModel(request);
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
			RepairProdTargetMatItemServiceModel repairProdTargetMatItemServiceModel = updateServiceUIModelCore(
					repairProdTargetMatItemServiceUIModel, logonUser.getUuid(), organizationUUID);
			// Refresh service model
			RepairProdTargetMatItem repairProdTargetMatItem = repairProdTargetMatItemServiceModel.getRepairProdTargetMatItem();
			repairProdTargetMatItemManager
					.setRepairDone(repairProdTargetMatItem, logonUser.getUuid(), organizationUUID);
			repairProdTargetMatItemServiceUIModel = refreshLoadSeviceUIModel(
					repairProdTargetMatItemServiceModel.getRepairProdTargetMatItem().getUuid(), ISystemActionCode.ACID_EDIT,
					logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(repairProdTargetMatItemServiceUIModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | MaterialException | ServiceUIModuleProxyException | ProdOrderTargetItemException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	private SplitMatItemModel parseToSplitMatItemModelRequest(String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		SplitMatItemModel splitMatItemModel = (SplitMatItemModel) JSONObject.toBean(
				jsonObject, SplitMatItemModel.class, classMap);
		return splitMatItemModel;
	}

	public @RequestMapping(value = "/splitProductionDoneService", produces = "text/html;"
			+ "charset=UTF-8") @ResponseBody String splitProductionDoneService(
			@RequestBody String request) {
		try {
			SplitMatItemModel splitMatItemModel = parseToSplitMatItemModelRequest(request);
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
			RepairProdTargetMatItem repairProdTargetMatItem = (RepairProdTargetMatItem) repairProdOrderManager
					.getEntityNodeByKey(splitMatItemModel.getItemUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							RepairProdTargetMatItem.NODENAME,
							logonUser.getClient(), null);
			RepairProdTargetMatItem newDoneTargetMatItem = repairProdTargetMatItemManager.splitProductionDoneService(splitMatItemModel,
					repairProdTargetMatItem, logonUser.getUuid(),
					organizationUUID);
			RepairProdTargetMatItemServiceUIModel repairProdTargetMatItemServiceUIModel = refreshLoadSeviceUIModel(
					newDoneTargetMatItem.getUuid(), ISystemActionCode.ACID_EDIT,
					logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(repairProdTargetMatItemServiceUIModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (MaterialException | SplitMatItemException | ProdOrderTargetItemException | ServiceUIModuleProxyException | ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	public @RequestMapping(value = "/initSplitItemService", produces = "text/html;"
			+ "charset=UTF-8") @ResponseBody String initSplitItemService(
			@RequestBody SimpleSEJSONRequest simpleSEJSONRequest) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			RepairProdTargetMatItem repairProdTargetMatItem = (RepairProdTargetMatItem) repairProdOrderManager
					.getEntityNodeByKey(simpleSEJSONRequest.getBaseUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							RepairProdTargetMatItem.NODENAME,
							logonUser.getClient(), null);
			SplitMatItemModel splitMatItemModel = splitMatItemProxy
					.initSplitModel(repairProdTargetMatItem, logonActionController.getLogonInfo());
			// Default set all is done
			splitMatItemModel.setLeftAmount(0);
			splitMatItemModel.setSplitAmount(splitMatItemModel.getAllAmount());
			return ServiceJSONParser.genDefOKJSONObject(splitMatItemModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | MaterialException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	public @RequestMapping(value = "/checkSplitItemService", produces = "text/html;"
			+ "charset=UTF-8") @ResponseBody String checkSplitItemService(
			@RequestBody String request) {
		try {
			SplitMatItemModel splitMatItemModel = parseToSplitMatItemModelRequest(request);
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			RepairProdTargetMatItem repairProdTargetMatItem = (RepairProdTargetMatItem) repairProdOrderManager
					.getEntityNodeByKey(splitMatItemModel.getItemUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							RepairProdTargetMatItem.NODENAME,
							logonUser.getClient(), null);
			StorageCoreUnit resultUnit = splitMatItemProxy
					.calculateLeftAmount(splitMatItemModel,
							repairProdTargetMatItem);
			return ServiceJSONParser.genDefOKJSONObject(resultUnit);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (MaterialException | SplitMatItemException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	/**
	 * API to load proper existed inbound delivery
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/loadProperInboundDelivery", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadProperInboundDelivery(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							RepairProdOrder.NODENAME, logonUser.getClient(),
							null);
			List<ServiceEntityNode> rawRepairProdOrderMatItemList = repairProdOrderManager
					.getEntityNodeListByKey(repairProdOrder.getUuid(),
							IServiceEntityNodeFieldConstant.ROOTNODEUUID,
							RepairProdTargetMatItem.NODENAME, null);
			List<ServiceEntityNode> rawInboundDeliveryList = repairProdTargetItemToCrossInboundProxy
					.getExistInboundDeliveryForCreationBatch(repairProdOrder,
							rawRepairProdOrderMatItemList);
			if (ServiceCollectionsHelper.checkNullList(rawInboundDeliveryList)) {
				// In case not exsited inbound delivery found for this purchase
				// contract, then generate a dummy one with empty id
				InboundDelivery inboundDelivery = new InboundDelivery();
				inboundDelivery.setId(ServiceEntityStringHelper.EMPTYSTRING);
				return ServiceJSONParser.genDefOKJSONObject(inboundDelivery);
			} else {
				return ServiceJSONParser
						.genDefOKJSONObject(rawInboundDeliveryList.get(0));
			}
		} catch (LogonInfoException | AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	public @RequestMapping(value = "/generateInboundDeliveryBatch", produces = "text/html;"
			+ "charset=UTF-8") @ResponseBody String generateInboundDeliveryBatch(
			@RequestBody String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		classMap.put("uuidList", String.class);
		DeliveryMatItemBatchGenRequest genRequest = (DeliveryMatItemBatchGenRequest) JSONObject
				.toBean(jsonObject, DeliveryMatItemBatchGenRequest.class,
						classMap);
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			RepairProdTargetMatItemSearchModel searchModel = new RepairProdTargetMatItemSearchModel();
			searchModel.setUuid(ServiceEntityStringHelper
					.convStringListIntoMultiStringValue(genRequest
							.getUuidList()));
			SearchContextBuilder searchContextBuilder = SearchContextBuilder.genBuilder(logonActionController.getLogonInfo()).searchModel(searchModel);
			List<ServiceEntityNode> RepairProdTargetMatItemList = repairProdOrderManager
					.getSearchProxy().searchItemList(searchContextBuilder.build()).getResultList();
			if (!ServiceEntityStringHelper.checkNullString(genRequest
					.getBaseUUID())) {
				RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
						.getEntityNodeByKey(genRequest.getBaseUUID(),
								IServiceEntityNodeFieldConstant.UUID,
								RepairProdOrder.NODENAME,
								logonUser.getClient(), null);
				RepairProdOrderServiceModel repairProdOrderServiceModel = (RepairProdOrderServiceModel) repairProdOrderManager
						.loadServiceModule(RepairProdOrderServiceModel.class,
								repairProdOrder);
				repairProdTargetMatItemManager.createInboundDeliveryBatch(
						repairProdOrderServiceModel,
						RepairProdTargetMatItemList, genRequest,
						logonActionController.getLogonInfo());
				return ServiceJSONParser.genSimpleOKResponse();
			}
			return null;
		} catch (LogonInfoException | SearchConfigureException | ServiceModuleProxyException |
                 ProductionOrderException | AuthorizationException | DocActionException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException | NodeNotFoundException | ServiceEntityInstallationException e) {
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
			RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
					.getEntityNodeByKey(request.getBaseUUID(), IServiceEntityNodeFieldConstant.UUID, RepairProdOrder.NODENAME,
							logonUser.getClient(), null);
			RepairProdTargetMatItem repairProdTargetMatItem = (RepairProdTargetMatItem) repairProdOrderManager
					.newEntityNode(repairProdOrder, RepairProdTargetMatItem.NODENAME);
			RepairProdTargetMatItemServiceModel repairProdTargetMatItemServiceModel = new RepairProdTargetMatItemServiceModel();
			repairProdTargetMatItemServiceModel.setRepairProdTargetMatItem(repairProdTargetMatItem);
			RepairProdTargetMatItemServiceUIModel repairProdTargetMatItemServiceUIModel = (RepairProdTargetMatItemServiceUIModel) repairProdOrderManager
					.genServiceUIModuleFromServiceModel(RepairProdTargetMatItemServiceUIModel.class,
							RepairProdTargetMatItemServiceModel.class, repairProdTargetMatItemServiceModel,
							repairProdTargetMatItemServiceUIModelExtension, logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(repairProdTargetMatItemServiceUIModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}


	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
				new DocPageHeaderModelProxy.GenModelList() {
			@Override
			public List<PageHeaderModel> execute(SimpleSEJSONRequest request, String client) throws ServiceEntityConfigureException {
				return repairProdTargetMatItemManager.getPageHeaderModelList(request, client);
			}
		});
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
			RepairProdTargetMatItem repairProdTargetMatItem = (RepairProdTargetMatItem) repairProdOrderManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, RepairProdTargetMatItem.NODENAME,
							logonUser.getClient(), null);
			return ServiceJSONParser.genDefOKJSONObject(repairProdTargetMatItem);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());

		}
	}

	private RepairProdTargetMatItemServiceUIModel refreshLoadSeviceUIModel(String uuid, String acId, LogonInfo logonInfo)
			throws ServiceEntityConfigureException, ServiceModuleProxyException, ServiceUIModuleProxyException, LogonInfoException, AuthorizationException {
		RepairProdTargetMatItem repairProdTargetMatItem = (RepairProdTargetMatItem) repairProdOrderManager
				.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, RepairProdTargetMatItem.NODENAME,
						logonInfo.getClient(), null);
		if (!ServiceEntityStringHelper.checkNullString(acId)) {
			boolean checkAuthor = serviceBasicUtilityController
					.checkTargetDataAccess(logonInfo.getLogonUser(), repairProdTargetMatItem, acId,
							logonInfo.getAuthorizationActionCodeMap());
			if (!checkAuthor) {
				throw new AuthorizationException(AuthorizationException.TYPE_NO_AUTHORIZATION);
			}
		}
		RepairProdTargetMatItemServiceModel repairProdTargetMatItemServiceModel = (RepairProdTargetMatItemServiceModel) repairProdOrderManager
				.loadServiceModule(RepairProdTargetMatItemServiceModel.class, repairProdTargetMatItem);
		RepairProdTargetMatItemServiceUIModel repairProdTargetMatItemServiceUIModel = (RepairProdTargetMatItemServiceUIModel) repairProdOrderManager
				.genServiceUIModuleFromServiceModel(RepairProdTargetMatItemServiceUIModel.class,
						RepairProdTargetMatItemServiceModel.class, repairProdTargetMatItemServiceModel,
						repairProdTargetMatItemServiceUIModelExtension, logonActionController.getLogonInfo());
		return repairProdTargetMatItemServiceUIModel;
	}

	@RequestMapping(value = "/getStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getStatus() {
		try {
			Map<Integer, String> statusMap = repairProdTargetMatItemManager.initStatusMap(logonActionController.getLanguageCode());
			return repairProdOrderManager.getDefaultSelectMap(statusMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
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
			RepairProdTargetMatItem repairProdTargetMatItem = (RepairProdTargetMatItem) repairProdOrderManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, RepairProdTargetMatItem.NODENAME,
							logonUser.getClient(), null);
			RepairProdTargetMatItemServiceModel repairProdTargetMatItemServiceModel = (RepairProdTargetMatItemServiceModel) repairProdOrderManager
					.loadServiceModule(RepairProdTargetMatItemServiceModel.class, repairProdTargetMatItem);
			RepairProdTargetMatItemServiceUIModel repairProdTargetMatItemServiceUIModel = refreshLoadSeviceUIModel(
					repairProdTargetMatItemServiceModel.getRepairProdTargetMatItem().getUuid(), ISystemActionCode.ACID_EDIT,
					logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(repairProdTargetMatItemServiceUIModel);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException e) {
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
			RepairProdTargetMatItem repairProdTargetMatItem = (RepairProdTargetMatItem) repairProdOrderManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, RepairProdTargetMatItem.NODENAME,
							logonUser.getClient(), null);
			RepairProdTargetMatItemServiceModel repairProdTargetMatItemServiceModel = (RepairProdTargetMatItemServiceModel) repairProdOrderManager
					.loadServiceModule(RepairProdTargetMatItemServiceModel.class, repairProdTargetMatItem);
			RepairProdTargetMatItemServiceUIModel repairProdTargetMatItemServiceUIModel = refreshLoadSeviceUIModel(
					repairProdTargetMatItemServiceModel.getRepairProdTargetMatItem().getUuid(), ISystemActionCode.ACID_EDIT,
					logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(repairProdTargetMatItemServiceUIModel);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException e) {
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
			RepairProdTargetMatItem repairProdTargetMatItem = (RepairProdTargetMatItem) repairProdOrderManager
					.getEntityNodeByKey(requestJSON.getUuid(), IServiceEntityNodeFieldConstant.UUID, RepairProdTargetMatItem.NODENAME,
							logonUser.getClient(), null);
			repairProdTargetMatItemManager.deleteModel(repairProdTargetMatItem,
					logonActionController.getLogonInfo().getRefUserUUID(),
					logonActionController.getLogonInfo().getResOrgUUID());
			if (repairProdTargetMatItem != null) {
				repairProdOrderManager.admDeleteEntityByKey(requestJSON.getUuid(),
						IServiceEntityNodeFieldConstant.UUID, RepairProdTargetMatItem.NODENAME);
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
			RepairProdTargetMatItem repairProdTargetMatItem = (RepairProdTargetMatItem) repairProdOrderManager
					.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, RepairProdTargetMatItem.NODENAME,
							logonUser.getClient(), null);
			String baseUUID = repairProdTargetMatItem.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			lockSEList.add(repairProdTargetMatItem);
			List<ServiceEntityNode> lockResult = lockObjectManager.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager
					.genJSONLockCheckResult(lockResult, repairProdTargetMatItem.getName(), repairProdTargetMatItem.getId(), baseUUID);

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
			RepairProdTargetMatItem repairProdTargetMatItem = (RepairProdTargetMatItem) repairProdOrderManager
					.getEntityNodeByUUID(uuid, RepairProdTargetMatItem.NODENAME, logonUser.getClient());
			List<ServiceDocumentExtendUIModel> docFlowList = serviceDocumentComProxy
					.getDocFlowList(repairProdTargetMatItem, repairProdOrderManager, ISystemActionCode.ACID_VIEW,
							logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONArray(docFlowList);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara(){
		return new DocAttachmentProxy.DocAttachmentProcessPara(repairProdOrderManager,
				RepairProdTargetItemAttachment.NODENAME, RepairProdTargetMatItem.NODENAME, null, null,
				null);
	}
	/**
	 * load the attachment content to consumer.
	 */
	@RequestMapping(value = "/loadAttachment")
	public ResponseEntity<byte[]> loadAttachment(String uuid) {
		return serviceBasicUtilityController.loadAttachment(uuid, AOID_RESOURCE,
				genDocAttachmentProcessPara());
	}

	/**
	 * Delete attachment
	 */
	@RequestMapping(value = "/deleteAttachment", produces = "text/html;charset=UTF-8")
	public @ResponseBody String deleteAttachment(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.deleteAttachment(request, AOID_RESOURCE,
				genDocAttachmentProcessPara());
	}


	/**
	 * Upload the attachment content information.
	 */
	@RequestMapping(value = "/uploadAttachment", consumes = "multipart/form-data", method = RequestMethod.POST)
	public @ResponseBody String uploadAttachment(HttpServletRequest request) {
		return serviceBasicUtilityController.uploadAttachment(request, AOID_RESOURCE,
				genDocAttachmentProcessPara());
	};

	/**
	 * Upload the attachment text information.
	 */
	@RequestMapping(value = "/uploadAttachmentText", produces = "text/html;charset=UTF-8")
	public @ResponseBody String uploadAttachmentText(
			@RequestBody FileAttachmentTextRequest request) {
		return serviceBasicUtilityController.uploadAttachmentText(request, AOID_RESOURCE,
				genDocAttachmentProcessPara());
	};

}
