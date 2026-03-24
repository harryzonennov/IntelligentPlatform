package com.company.IntelligentPlatform.common.dto;

import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.common.service.IndividualCustomerManager;
import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialSKUExtendProperty;
import com.company.IntelligentPlatform.common.model.RegisteredProduct;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;


@Scope("session")
@Controller(value = "materialSKUExtendPropertyEditorController")
@RequestMapping(value = "/materialSKUExtendProperty")
public class MaterialSKUExtendPropertyEditorController extends
		SEEditorController {

	public static final String AOID_RESOURCE = IServiceModelConstants.RegisteredProduct;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected MaterialSKUExtendPropertyServiceUIModelExtension materialSKUExtendPropertyServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected LogonUserManager logonUserManager;

	@Autowired
	protected OrganizationManager organizationManager;

	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;

	@Autowired
	protected IndividualCustomerManager individualCustomerManager;



	@RequestMapping(value = "/getMeasureFlagMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getMeasureFlagMap(String lanCode) {
		try {
			Map<Integer, String> measureFlagMap = materialStockKeepUnitManager
					.initMeasureFlagMap(lanCode);
			return materialStockKeepUnitManager.getDefaultSelectMap(measureFlagMap,
					false);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}
	}

	@RequestMapping(value = "/getQualityInspectMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getQualityInspectMap(String lanCode) {
		try {
			Map<Integer, String> qualityInspectMap = materialStockKeepUnitManager
					.initQualityInspectMap(lanCode);
			return materialStockKeepUnitManager.getDefaultSelectMap(
					qualityInspectMap, false);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}
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
			MaterialSKUExtendPropertyUIModel materialSKUExtendPropertyUIModel = (MaterialSKUExtendPropertyUIModel) JSONObject
					.toBean(jsonObject,
							MaterialSKUExtendPropertyUIModel.class,
							classMap);
			List<ServiceEntityNode> rawSeNodeList = materialStockKeepUnitManager
					.genSeNodeListInExtensionUnion(
							materialSKUExtendPropertyServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							MaterialSKUExtendProperty.class,
							materialSKUExtendPropertyUIModel);
			materialStockKeepUnitManager.updateSeNodeListWrapper(rawSeNodeList,
					logonUser.getUuid(), organizationUUID);
			MaterialSKUExtendProperty materialSKUExtendProperty = (MaterialSKUExtendProperty) materialStockKeepUnitManager
					.getEntityNodeByKey(
							materialSKUExtendPropertyUIModel.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							MaterialSKUExtendProperty.NODENAME,
							logonUser.getClient(), null);
			materialSKUExtendPropertyUIModel = (MaterialSKUExtendPropertyUIModel) materialStockKeepUnitManager
					.genUIModelFromUIModelExtension(
							MaterialSKUExtendPropertyUIModel.class,
							materialSKUExtendPropertyServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							materialSKUExtendProperty, logonActionController.getLogonInfo(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(materialSKUExtendPropertyUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceModuleProxyException e) {
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
			RegisteredProduct registeredProduct = (RegisteredProduct) materialStockKeepUnitManager
					.getEntityNodeByKey(request.getBaseUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							RegisteredProduct.NODENAME, logonUser.getClient(),
							null);
			MaterialSKUExtendProperty materialSKUExtendProperty = (MaterialSKUExtendProperty) materialStockKeepUnitManager
					.newEntityNode(registeredProduct,
							MaterialSKUExtendProperty.NODENAME);
			MaterialSKUExtendPropertyUIModel materialSKUExtendPropertyUIModel = new MaterialSKUExtendPropertyUIModel();
			materialStockKeepUnitManager.convMaterialSKUExtendPropertyToUI(
					materialSKUExtendProperty,
					materialSKUExtendPropertyUIModel);
			return ServiceJSONParser
					.genDefOKJSONObject(materialSKUExtendPropertyUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
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
				materialStockKeepUnitManager);
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
			MaterialSKUExtendProperty materialSKUExtendProperty = (MaterialSKUExtendProperty) materialStockKeepUnitManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							MaterialSKUExtendProperty.NODENAME,
							logonUser.getClient(), null);
			String baseUUID = materialSKUExtendProperty.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			lockSEList.add(materialSKUExtendProperty);
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					materialSKUExtendProperty.getName(),
					materialSKUExtendProperty.getId(), baseUUID);

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
			MaterialSKUExtendProperty materialSKUExtendProperty = (MaterialSKUExtendProperty) materialStockKeepUnitManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							MaterialSKUExtendProperty.NODENAME,
							logonUser.getClient(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(materialSKUExtendProperty);
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());

		}
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleEditService(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			MaterialSKUExtendProperty materialSKUExtendProperty = (MaterialSKUExtendProperty) materialStockKeepUnitManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							MaterialSKUExtendProperty.NODENAME,
							logonUser.getClient(), null);
			lockSEList.add(materialSKUExtendProperty);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));
			MaterialSKUExtendPropertyUIModel materialSKUExtendPropertyUIModel = (MaterialSKUExtendPropertyUIModel) materialStockKeepUnitManager
					.genUIModelFromUIModelExtension(
							MaterialSKUExtendPropertyUIModel.class,
							materialSKUExtendPropertyServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
							materialSKUExtendProperty, logonActionController.getLogonInfo(), 
							null);
			return ServiceJSONParser
					.genDefOKJSONObject(materialSKUExtendPropertyUIModel);
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
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

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
