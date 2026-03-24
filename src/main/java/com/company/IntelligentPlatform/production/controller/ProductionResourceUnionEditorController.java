package com.company.IntelligentPlatform.production.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.production.dto.ProductionResourceUnionServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProductionResourceUnionUIModel;
import com.company.IntelligentPlatform.production.service.ProductionDataException;
import com.company.IntelligentPlatform.production.service.ProductionResourceUnionManager;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;
import com.company.IntelligentPlatform.production.model.ProductionResourceUnion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.INavigationElementConstants;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.StandardKeyFlagProxy;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceModuleConvertPara;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "productionResourceUnionEditorController")
@RequestMapping(value = "/productionResourceUnion")
public class ProductionResourceUnionEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = ProdWorkCenter.SENAME;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ProductionResourceUnionManager productionResourceUnionManager;

	@Autowired
	protected OrganizationManager organizationManager;
	
	@Autowired
	protected StandardKeyFlagProxy standardKeyFlagProxy;
	
	@Autowired
	protected ProductionResourceUnionServiceUIModelExtension productionResourceUnionServiceUIModelExtension;


	@RequestMapping(value = "/getResourceTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getResourceTypeMap() {
		try {
			Map<Integer, String> resourceTypeMap = productionResourceUnionManager.initResourceTypeMap();
			return productionResourceUnionManager.getDefaultSelectMap(
					resourceTypeMap, false);
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/getKeyResourceFlagMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getKeyResourceFlagMap() {
		try {
			Map<Integer, String> keyResourceFlagMap = productionResourceUnionManager.initKeyResourceFlagMap();
			return productionResourceUnionManager.getDefaultSelectMap(
					keyResourceFlagMap, false);
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}
	

	protected String getPreWarnMsg(String key, Map<String, String> preWarnMap) {
		return preWarnMap.get(key);
	}

	protected Map<String, String> getPreWarnMap() throws IOException {
		Locale locale = Locale.getDefault();
		String path = ProductionResourceUnionUIModel.class.getResource("")
				.getPath();
		String resFileName = ProductionResourceUnion.SENAME;
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap;
	}

	protected void saveInternal(
			ProductionResourceUnionUIModel productionResourceUnionUIModel)
			throws ServiceEntityConfigureException, LogonInfoException {
		String baseUUID = productionResourceUnionUIModel.getUuid();
		ProductionResourceUnion productionResourceUnion = (ProductionResourceUnion) getServiceEntityNodeFromBuffer(
				ProductionResourceUnion.NODENAME, baseUUID);
		productionResourceUnionManager.convUIToProductionResourceUnion( productionResourceUnionUIModel,
				productionResourceUnion);
		LogonUser logonUser = logonActionController.getLogonUser();
		if (logonUser == null) {
			throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
		}
		Organization organization = logonActionController
				.getOrganizationByUser(logonUser.getUuid());
		this.save(baseUUID, productionResourceUnionManager, logonUser,
				organization);
	}
	
	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String response) {
		JSONObject jsonObject = JSONObject.fromObject(response);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<String, Class>();
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
			ProductionResourceUnionUIModel productionResourceUnionUIModel = (ProductionResourceUnionUIModel) JSONObject
					.toBean(jsonObject, ProductionResourceUnionUIModel.class,
							classMap);
			List<ServiceEntityNode> rawSeNodeList = productionResourceUnionManager
					.genSeNodeListInExtensionUnion(
							productionResourceUnionServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							ProductionResourceUnion.class,
							productionResourceUnionUIModel);
			productionResourceUnionManager.updateSeNodeListWrapper(rawSeNodeList,
					logonUser.getUuid(), organizationUUID);
			ProductionResourceUnion productionResourceUnion = (ProductionResourceUnion) productionResourceUnionManager
					.getEntityNodeByKey(
							productionResourceUnionUIModel.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							ProductionResourceUnion.NODENAME,
							logonUser.getClient(), null);
			productionResourceUnionUIModel = (ProductionResourceUnionUIModel) productionResourceUnionManager
					.genUIModelFromUIModelExtension(
							ProductionResourceUnionUIModel.class,
							productionResourceUnionServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							productionResourceUnion, logonActionController.getLogonInfo(),null);
			return ServiceJSONParser
					.genDefOKJSONObject(productionResourceUnionUIModel);
		} catch (LogonInfoException | AuthorizationException | ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService() {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}			
			ProductionResourceUnion productionResourceUnion = (ProductionResourceUnion) productionResourceUnionManager
					.newRootEntityNode();
			ProductionResourceUnionUIModel productionResourceUnionUIModel = new ProductionResourceUnionUIModel();
			productionResourceUnionManager.convProductionResourceUnionToUI(
					productionResourceUnion, productionResourceUnionUIModel);
			return ServiceJSONParser
					.genDefOKJSONObject(productionResourceUnionUIModel);
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				productionResourceUnionManager);
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
			ProductionResourceUnion productionResourceUnion = (ProductionResourceUnion) productionResourceUnionManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProductionResourceUnion.NODENAME,
							logonUser.getClient(), null);
			String baseUUID = productionResourceUnion.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<ServiceEntityNode>();
			lockSEList.add(productionResourceUnion);
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					productionResourceUnion.getName(),
					productionResourceUnion.getId(), baseUUID);

		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e
					.getErrorMessage());
		}
	}

	
	@RequestMapping(value = "/chooseCostCenterToResource", produces = "text/html;charset=UTF-8")
	public @ResponseBody String chooseCostCenterToResource(
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
							Organization.NODENAME, curLogonUser.getClient(), null);
			if (organization == null) {
				// Should raise exception
				throw new ProductionDataException(
						ProductionDataException.PARA_NO_COSTCENTER, costCenterUUID);
			}
			String baseUUID = request.getBaseUUID();
			ProductionResourceUnion productionResourceUnion = (ProductionResourceUnion) getServiceEntityNodeFromBuffer(
					ProductionResourceUnion.NODENAME, baseUUID);
			if (productionResourceUnion == null) {
				// Should raise exception
				throw new ProductionDataException(
						ProductionDataException.TYPE_SYSTEM_WRONG);
			}
			productionResourceUnion.setRefCostCenterUUID(organization.getUuid());
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
			List<ServiceEntityNode> lockSEList = new ArrayList<ServiceEntityNode>();
			ProductionResourceUnion productionResourceUnion = (ProductionResourceUnion) productionResourceUnionManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ProductionResourceUnion.NODENAME,
							logonUser.getClient(), null);
			lockSEList.add(productionResourceUnion);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));
			List<ServiceModuleConvertPara> addtionalConvertParaList = new ArrayList<ServiceModuleConvertPara>();
			ProductionResourceUnionUIModel productionResourceUnionUIModel = (ProductionResourceUnionUIModel) productionResourceUnionManager
					.genUIModelFromUIModelExtension(
							ProductionResourceUnionUIModel.class,
							productionResourceUnionServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							productionResourceUnion,  logonActionController.getLogonInfo(),addtionalConvertParaList);
			return ServiceJSONParser
					.genDefOKJSONObject(productionResourceUnionUIModel);
		} catch (AuthorizationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex
					.getErrorMessage());
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LockObjectFailureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

}
