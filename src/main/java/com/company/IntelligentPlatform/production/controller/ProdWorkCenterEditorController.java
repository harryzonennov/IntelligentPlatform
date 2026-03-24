package com.company.IntelligentPlatform.production.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.production.dto.ProdWorkCenterCalendarItemUIModel;
import com.company.IntelligentPlatform.production.dto.ProdWorkCenterResItemUIModel;
import com.company.IntelligentPlatform.production.dto.ProdWorkCenterServiceUIModel;
import com.company.IntelligentPlatform.production.dto.ProdWorkCenterServiceUIModelExtension;
import com.company.IntelligentPlatform.production.service.*;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;
import com.company.IntelligentPlatform.production.model.ProdWorkCenterResItem;
import com.company.IntelligentPlatform.production.model.ProductionResourceUnion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.StandardKeyFlagProxy;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IReferenceNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "prodWorkCenterEditorController")
@RequestMapping(value = "/prodWorkCenter")
public class ProdWorkCenterEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = ProdWorkCenter.SENAME;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ProdWorkCenterManager prodWorkCenterManager;

	@Autowired
	protected ProductionResourceUnionManager productionResourceUnionManager;

	@Autowired
	protected OrganizationManager organizationManager;

	@Autowired
	protected StandardKeyFlagProxy standardKeyFlagProxy;

	@Autowired
	protected ProdWorkCenterSpecifier prodWorkCenterSpecifier;

	@Autowired
	protected ProdWorkCenterServiceUIModelExtension prodWorkCenterServiceUIModelExtension;


	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest, prodWorkCenterManager);
	}

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				ProdWorkCenterServiceUIModel.class,
				ProdWorkCenterServiceModel.class, AOID_RESOURCE,
				ProdWorkCenter.NODENAME, ProdWorkCenter.SENAME, prodWorkCenterServiceUIModelExtension,
				prodWorkCenterManager
		);
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
			ProdWorkCenter prodWorkCenter = (ProdWorkCenter) prodWorkCenterManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProdWorkCenter.NODENAME, logonUser.getClient(),
							null);
			String baseUUID = prodWorkCenter.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<ServiceEntityNode>();
			lockSEList.add(prodWorkCenter);
			ProdWorkCenterResItem prodWorkCenterResItem = (ProdWorkCenterResItem) prodWorkCenterManager
					.getEntityNodeByKey(prodWorkCenter.getUuid(),
							IServiceEntityNodeFieldConstant.ROOTNODEUUID,
							ProdWorkCenterResItem.NODENAME,
							prodWorkCenter.getClient(), null);
			lockSEList.add(prodWorkCenterResItem);
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					prodWorkCenter.getName(), prodWorkCenter.getId(), baseUUID);
		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e
					.getErrorMessage());
		}
	}

	private ProdWorkCenterServiceUIModel parseToServiceUIModel(String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes") Map<String, Class> classMap = new HashMap<>();
		classMap.put("prodWorkCenterResItemUIModelList", ProdWorkCenterResItemUIModel.class);
		classMap.put("prodWorkCenterCalendarItemUIModelList", ProdWorkCenterCalendarItemUIModel.class);
		return (ProdWorkCenterServiceUIModel) JSONObject.toBean(jsonObject,
				ProdWorkCenterServiceUIModel.class, classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String saveModuleService(@RequestBody String request) {
		ProdWorkCenterServiceUIModel prodWorkCenterServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				prodWorkCenterServiceUIModel,
				prodWorkCenterServiceUIModel.getProdWorkCenterUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String newModuleService(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newDocMatItemServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitDocMatItemRequest(
						ProdWorkCenter.SENAME, ProdWorkCenter.NODENAME, null,
						ProdWorkCenter.NODENAME, request.getBaseUUID(), ProdWorkCenter.NODENAME, ProdWorkCenterServiceModel.class,
						prodWorkCenterSpecifier, request, null), ISystemActionCode.ACID_EDIT);
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
	public @ResponseBody
	String loadModule(String uuid) {
		return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}
	
	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleEditService(String uuid) {
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, false,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleViewService(String uuid) {
		return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/getCapacityCalculateType", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getCapacityCalculateType() {
		try {
			Map<Integer, String> capacityCalculateTypeMap = prodWorkCenterManager
					.initCapacityCalculateTypeMap();
			return prodWorkCenterManager
					.getDefaultSelectMap(capacityCalculateTypeMap);
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/getKeyWorkCenterMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getKeyWorkCenterMap() {
		try {
			Map<Integer, String> keyWorkCenterMap = prodWorkCenterManager
					.initKeyWorkCenterMap();
			return prodWorkCenterManager.getDefaultSelectMap(keyWorkCenterMap);
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/getKeyResourceMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getKeyResourceMap() {
		try {
			Map<Integer, String> keyResourceMap = prodWorkCenterManager
					.initKeyResourceMap();
			return prodWorkCenterManager.getDefaultSelectMap(keyResourceMap,
					false);
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/chooseResourceToWorkCenter", produces = "text/html;charset=UTF-8")
	public @ResponseBody String chooseResourceToWorkCenter(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		try {
			String baseUUID = simpleRequest.getBaseUUID();
			String resourceUnionUUID = simpleRequest.getUuid();
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
			Organization organization = logonActionController.getOrganization();
			if (organization != null) {
				organizationUUID = organization.getUuid();
			}
			ProdWorkCenter prodWorkCenter = (ProdWorkCenter) this
					.getServiceEntityNodeFromBuffer(ProdWorkCenter.NODENAME,
							baseUUID);
			ProductionResourceUnion productionResourceUnion = (ProductionResourceUnion) productionResourceUnionManager
					.getEntityNodeByKey(resourceUnionUUID,
							IServiceEntityNodeFieldConstant.UUID,
							ProductionResourceUnion.NODENAME,
							logonUser.getClient(), null);
			if (productionResourceUnion == null) {
				// Should raise exception
			}
			// Check existence from persistence firstly.
			ProdWorkCenterResItem prodWorkCenterResItem = prodWorkCenterManager
					.getWorkCenterResItem(resourceUnionUUID, baseUUID,
							logonUser.getClient());
			if (prodWorkCenterResItem == null) {
				prodWorkCenterResItem = (ProdWorkCenterResItem) prodWorkCenterManager
						.newEntityNode(prodWorkCenter,
								ProdWorkCenterResItem.NODENAME);
				prodWorkCenterManager.buildReferenceNode(
						productionResourceUnion, prodWorkCenterResItem,
						ServiceEntityFieldsHelper
								.getCommonPackage(CorporateCustomer.class));
				prodWorkCenterManager.insertSENode(prodWorkCenterResItem,
						logonUser.getUuid(), organizationUUID);
			}
			return ServiceJSONParser.genSimpleOKResponse();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/deleteResourceFromWorkCenter", produces = "text/html;charset=UTF-8")
	public @ResponseBody String deleteResourceFromWorkCenter(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		try {
			String resourceUnionUUID = simpleRequest.getUuid();
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
			Organization organization = logonActionController.getOrganization();
			if (organization != null) {
				organizationUUID = organization.getUuid();
			}
			// Check existence from persistence firstly.
			ProdWorkCenterResItem prodWorkCenterResItem = (ProdWorkCenterResItem) prodWorkCenterManager
					.getEntityNodeByKey(resourceUnionUUID,
							IReferenceNodeFieldConstant.REFUUID,
							ProdWorkCenterResItem.NODENAME,
							logonUser.getClient(), null);
			if (prodWorkCenterResItem != null) {
				prodWorkCenterManager.deleteSENode(prodWorkCenterResItem,
						logonUser.getUuid(), organizationUUID);
			}
			return ServiceJSONParser.genSimpleOKResponse();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/chooseCostCenterToWorkCenter", produces = "text/html;charset=UTF-8")
	public @ResponseBody String chooseCostCenterToWorkCenter(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser curLogonUser = logonActionController.getLogonUser();
			if (curLogonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String costCenterUUID = request.getUuid();
			Organization organization = (Organization) organizationManager
					.getEntityNodeByKey(costCenterUUID,
							IServiceEntityNodeFieldConstant.UUID,
							Organization.NODENAME, curLogonUser.getClient(),
							null);
			if (organization == null) {
				// Should raise exception
				throw new ProductionDataException(
						ProductionDataException.PARA_NO_COSTCENTER,
						costCenterUUID);
			}
			String baseUUID = request.getBaseUUID();
			ProdWorkCenter prodWorkCenter = (ProdWorkCenter) this
					.getServiceEntityNodeFromBuffer(ProdWorkCenter.NODENAME,
							baseUUID);
			if (prodWorkCenter == null) {
				// Should raise exception
				throw new ProductionDataException(
						ProductionDataException.TYPE_SYSTEM_WRONG);
			}
			prodWorkCenter.setRefCostCenterUUID(organization.getUuid());
			return ServiceJSONParser.genDefOKJSONObject(organization);
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ProductionDataException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
