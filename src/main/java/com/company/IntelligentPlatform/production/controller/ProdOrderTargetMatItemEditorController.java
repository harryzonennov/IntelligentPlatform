package com.company.IntelligentPlatform.production.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.model.InboundDelivery;
import com.company.IntelligentPlatform.production.dto.*;
import com.company.IntelligentPlatform.production.service.*;
import com.company.IntelligentPlatform.production.model.ProdOrderTargetItemAttachment;
import com.company.IntelligentPlatform.production.model.ProductionOrder;
import com.company.IntelligentPlatform.production.model.ProdOrderTargetMatItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.RegisteredProductException;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchContextBuilder;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
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

@Scope("session")
@Controller(value = "prodOrderTargetMatItemEditorController")
@RequestMapping(value = "/prodOrderTargetMatItem")
public class ProdOrderTargetMatItemEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = ProductionOrderEditorController.AOID_RESOURCE;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected ProdOrderTargetMatItemManager prodOrderTargetMatItemManager;

	@Autowired
	protected ProdOrderTargetMatItemServiceUIModelExtension prodOrderTargetMatItemServiceUIModelExtension;

	// Deprecated.
	@Autowired
	protected ProdOrderTargetItemToCrossInboundProxy prodOrderTargetItemToCrossInboundProxy;

	@Autowired
	protected SplitMatItemProxy splitMatItemProxy;

	@Autowired
	protected ProductionOrderSpecifier productionOrderSpecifier;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				ProdOrderTargetMatItemServiceUIModel.class,
				ProdOrderTargetMatItemServiceModel.class, AOID_RESOURCE,
				ProdOrderTargetMatItem.NODENAME, ProdOrderTargetMatItem.SENAME, prodOrderTargetMatItemServiceUIModelExtension,
				productionOrderManager
		);
	}

	private ProdOrderTargetMatItemServiceUIModel parseToServiceUIModel(String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		classMap.put("prodOrderTarSubItemUIModelList", ProdOrderTarSubItemUIModel.class);
		classMap.put("prodOrderTargetItemAttachmentUIModelList", ProdOrderTargetItemAttachmentUIModel.class);
		ProdOrderTargetMatItemServiceUIModel prodOrderTargetMatItemServiceUIModel = (ProdOrderTargetMatItemServiceUIModel) JSONObject
				.toBean(jsonObject, ProdOrderTargetMatItemServiceUIModel.class, classMap);
		return prodOrderTargetMatItemServiceUIModel;
	}

	private ProdOrderTargetMatItemServiceModel updateServiceUIModelCore(
			ProdOrderTargetMatItemServiceUIModel prodOrderTargetMatItemServiceUIModel, String logonUserUUID, String organizationUUID)
			throws ServiceModuleProxyException, ServiceEntityConfigureException, ServiceUIModuleProxyException {
		ProdOrderTargetMatItemServiceModel prodOrderTargetMatItemServiceModel = (ProdOrderTargetMatItemServiceModel) productionOrderManager
				.genServiceModuleFromServiceUIModel(ProdOrderTargetMatItemServiceModel.class,
						ProdOrderTargetMatItemServiceUIModel.class, prodOrderTargetMatItemServiceUIModel,
						prodOrderTargetMatItemServiceUIModelExtension);
		productionOrderManager
				.updateServiceModuleWithDelete(ProdOrderTargetMatItemServiceModel.class, prodOrderTargetMatItemServiceModel,
						logonUserUUID, organizationUUID);
		return prodOrderTargetMatItemServiceModel;
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String saveModuleService(@RequestBody String request) {
		ProdOrderTargetMatItemServiceUIModel prodOrderTargetMatItemServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				prodOrderTargetMatItemServiceUIModel, serviceModule -> {
                    ProdOrderTargetMatItemServiceModel prodOrderTargetMatItemServiceModel = (ProdOrderTargetMatItemServiceModel) serviceModule;
					try {
						prodOrderTargetMatItemManager.preCheckUpdate(prodOrderTargetMatItemServiceModel.getProdOrderTargetMatItem());
					} catch (ProdOrderTargetItemException | RegisteredProductException e) {
						throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
					}
                    return  prodOrderTargetMatItemServiceModel;}, null, null,
				prodOrderTargetMatItemServiceUIModel.getProdOrderTargetMatItemUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String checkDuplicateID(@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest, productionOrderManager);
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModule(String uuid) {
		return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/setCancelService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String setCancelService(@RequestBody String request) {
		try {
			ProdOrderTargetMatItemServiceUIModel prodOrderTargetMatItemServiceUIModel = parseToServiceUIModel(request);
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
			ProdOrderTargetMatItemServiceModel prodOrderTargetMatItemServiceModel = updateServiceUIModelCore(
					prodOrderTargetMatItemServiceUIModel, logonUser.getUuid(), organizationUUID);
			// Refresh service model
			ProdOrderTargetMatItem prodOrderTargetMatItem = prodOrderTargetMatItemServiceModel.getProdOrderTargetMatItem();
			prodOrderTargetMatItemManager
					.setCancelStatus(prodOrderTargetMatItem,logonUser.getUuid(), organizationUUID);
			prodOrderTargetMatItemServiceUIModel = refreshLoadSeviceUIModel(
					prodOrderTargetMatItemServiceModel.getProdOrderTargetMatItem().getUuid(), ISystemActionCode.ACID_EDIT,
					logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(prodOrderTargetMatItemServiceUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | MaterialException | ServiceUIModuleProxyException | ProdOrderTargetItemException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/setProductionDoneService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String setProductionDoneService(@RequestBody String request) {
		try {
			ProdOrderTargetMatItemServiceUIModel prodOrderTargetMatItemServiceUIModel = parseToServiceUIModel(request);
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
			ProdOrderTargetMatItemServiceModel prodOrderTargetMatItemServiceModel = updateServiceUIModelCore(
					prodOrderTargetMatItemServiceUIModel, logonUser.getUuid(), organizationUUID);
			// Refresh service model
			ProdOrderTargetMatItem prodOrderTargetMatItem = prodOrderTargetMatItemServiceModel.getProdOrderTargetMatItem();
			prodOrderTargetMatItemManager
					.setProductionDone(prodOrderTargetMatItem, logonUser.getUuid(), organizationUUID);
			prodOrderTargetMatItemServiceUIModel = refreshLoadSeviceUIModel(
					prodOrderTargetMatItemServiceModel.getProdOrderTargetMatItem().getUuid(), ISystemActionCode.ACID_EDIT,
					logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(prodOrderTargetMatItemServiceUIModel);
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
			ProdOrderTargetMatItem prodOrderTargetMatItem = (ProdOrderTargetMatItem) productionOrderManager
					.getEntityNodeByKey(splitMatItemModel.getItemUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							ProdOrderTargetMatItem.NODENAME,
							logonUser.getClient(), null);
			ProdOrderTargetMatItem newDoneTargetMatItem = prodOrderTargetMatItemManager.splitProductionDoneService(splitMatItemModel,
					prodOrderTargetMatItem, logonUser.getUuid(),
					organizationUUID);
			ProdOrderTargetMatItemServiceUIModel prodOrderTargetMatItemServiceUIModel = refreshLoadSeviceUIModel(
					newDoneTargetMatItem.getUuid(), ISystemActionCode.ACID_EDIT,
					logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONObject(prodOrderTargetMatItemServiceUIModel);
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
			ProdOrderTargetMatItem prodOrderTargetMatItem = (ProdOrderTargetMatItem) productionOrderManager
					.getEntityNodeByKey(simpleSEJSONRequest.getBaseUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							ProdOrderTargetMatItem.NODENAME,
							logonUser.getClient(), null);
			SplitMatItemModel splitMatItemModel = splitMatItemProxy
					.initSplitModel(prodOrderTargetMatItem, logonActionController.getLogonInfo());
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
			ProdOrderTargetMatItem prodOrderTargetMatItem = (ProdOrderTargetMatItem) productionOrderManager
					.getEntityNodeByKey(splitMatItemModel.getItemUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							ProdOrderTargetMatItem.NODENAME,
							logonUser.getClient(), null);
			StorageCoreUnit resultUnit = splitMatItemProxy
					.calculateLeftAmount(splitMatItemModel,
							prodOrderTargetMatItem);
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
	 * // Should be replaced by loadProperTargetDocListBatchGen
	 * @param uuid
	 * @return
	 */
	@Deprecated
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
			ProductionOrder productionOrder = (ProductionOrder) productionOrderManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProductionOrder.NODENAME, logonUser.getClient(),
							null);
			List<ServiceEntityNode> rawProductionOrderMatItemList = productionOrderManager
					.getEntityNodeListByKey(productionOrder.getUuid(),
							IServiceEntityNodeFieldConstant.ROOTNODEUUID,
							ProdOrderTargetMatItem.NODENAME, null);
			List<ServiceEntityNode> rawInboundDeliveryList = prodOrderTargetItemToCrossInboundProxy
					.getExistInboundDeliveryForCreationBatch(productionOrder,
							rawProductionOrderMatItemList);
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
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	// Should be replaced by generateNextDocBatch
	@Deprecated
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
			ProdOrderTargetMatItemSearchModel searchModel = new ProdOrderTargetMatItemSearchModel();
			searchModel.setUuid(ServiceEntityStringHelper
					.convStringListIntoMultiStringValue(genRequest
							.getUuidList()));
			SearchContextBuilder searchContextBuilder = SearchContextBuilder.genBuilder(logonActionController.getLogonInfo()).searchModel(searchModel);
			List<ServiceEntityNode> ProdOrderTargetMatItemList = productionOrderManager
					.getSearchProxy().searchItemList(searchContextBuilder.build()).getResultList();
			if (!ServiceEntityStringHelper.checkNullString(genRequest
					.getBaseUUID())) {
				ProductionOrder productionOrder = (ProductionOrder) productionOrderManager
						.getEntityNodeByKey(genRequest.getBaseUUID(),
								IServiceEntityNodeFieldConstant.UUID,
								ProductionOrder.NODENAME,
								logonUser.getClient(), null);
				ProductionOrderServiceModel productionOrderServiceModel = (ProductionOrderServiceModel) productionOrderManager
						.loadServiceModule(ProductionOrderServiceModel.class,
								productionOrder);
				prodOrderTargetMatItemManager.createInboundDeliveryBatch(
						productionOrderServiceModel,
						ProdOrderTargetMatItemList, genRequest,
						logonActionController.getLogonInfo());
				return ServiceJSONParser.genSimpleOKResponse();
			}
			return null;
		} catch (LogonInfoException | SearchConfigureException | ServiceModuleProxyException | ProductionOrderException | AuthorizationException | DocActionException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException | NodeNotFoundException | ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
    }

	public @RequestMapping(value = "/loadProperTargetDocListBatchGen", produces = "text/html;"
			+ "charset=UTF-8") @ResponseBody String loadProperTargetDocListBatchGen(
			@RequestBody String request) {
		return serviceBasicUtilityController.loadProperTargetDocListBatchGen(request,
				IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER, DeliveryMatItemBatchGenRequest.class, AOID_RESOURCE);
	}

	public @RequestMapping(value = "/generateNextDocBatch", produces = "text/html;"
			+ "charset=UTF-8") @ResponseBody String generateNextDocBatch(
			@RequestBody String request) {
		return serviceBasicUtilityController.genDefNextDocBatchWrapper(request,
				IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER, AOID_RESOURCE, DeliveryMatItemBatchGenRequest.class,
				null);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String newModuleService(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newDocMatItemServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitDocMatItemRequest(
						ProdOrderTargetMatItem.SENAME, ProdOrderTargetMatItem.NODENAME, null,
						ProductionOrder.NODENAME, request.getBaseUUID(), ProdOrderTargetMatItem.NODENAME, ProductionOrderServiceModel.class,
						productionOrderSpecifier, request, null), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
				(request1, client) -> prodOrderTargetMatItemManager.getPageHeaderModelList(request1, client));
	}

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLockService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preLockService(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.preLock(request.getUuid(), ISystemActionCode.ACID_EDIT, getServiceUIModelRequest());
	}

	private ProdOrderTargetMatItemServiceUIModel refreshLoadSeviceUIModel(String uuid, String acId, LogonInfo logonInfo)
			throws ServiceEntityConfigureException, ServiceModuleProxyException, ServiceUIModuleProxyException, LogonInfoException, AuthorizationException {
		ProdOrderTargetMatItem prodOrderTargetMatItem = (ProdOrderTargetMatItem) productionOrderManager
				.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, ProdOrderTargetMatItem.NODENAME,
						logonInfo.getClient(), null);
		if (!ServiceEntityStringHelper.checkNullString(acId)) {
			boolean checkAuthor = serviceBasicUtilityController
					.checkTargetDataAccess(logonInfo.getLogonUser(), prodOrderTargetMatItem, acId,
							logonInfo.getAuthorizationActionCodeMap());
			if (!checkAuthor) {
				throw new AuthorizationException(AuthorizationException.TYPE_NO_AUTHORIZATION);
			}
		}
		ProdOrderTargetMatItemServiceModel prodOrderTargetMatItemServiceModel = (ProdOrderTargetMatItemServiceModel) productionOrderManager
				.loadServiceModule(ProdOrderTargetMatItemServiceModel.class, prodOrderTargetMatItem);
		ProdOrderTargetMatItemServiceUIModel prodOrderTargetMatItemServiceUIModel = (ProdOrderTargetMatItemServiceUIModel) productionOrderManager
				.genServiceUIModuleFromServiceModel(ProdOrderTargetMatItemServiceUIModel.class,
						ProdOrderTargetMatItemServiceModel.class, prodOrderTargetMatItemServiceModel,
						prodOrderTargetMatItemServiceUIModelExtension, logonActionController.getLogonInfo());
		return prodOrderTargetMatItemServiceUIModel;
	}

	@RequestMapping(value = "/getStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getStatus() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> prodOrderTargetMatItemManager.initStatusMap(lanCode));
	}

	@RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String deleteModule(String uuid) {
		return serviceBasicUtilityController.deleteDocMatItem(uuid, ISystemActionCode.ACID_EDIT,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleViewService(String uuid) {
		return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleEditService(String uuid) {
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, false,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String exitEditor(@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "/getDocFlowList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getDocFlowList(String uuid) {
		return serviceBasicUtilityController.getDocFlowList(getServiceUIModelRequest(), uuid,
				ISystemActionCode.ACID_VIEW);
	}

	private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara(){
		return new DocAttachmentProxy.DocAttachmentProcessPara(productionOrderManager,
				ProdOrderTargetItemAttachment.NODENAME, ProdOrderTargetMatItem.NODENAME, null, null,
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
