package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.SystemExecutorLogUIModel;
import com.company.IntelligentPlatform.common.dto.SystemExecutorSettingUIModel;
import com.company.IntelligentPlatform.common.dto.SystemExecutorSettingSearchModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.SystemExecutorSettingRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.AuthorizationObjectManager;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.SpringContextBeanService;
import com.company.IntelligentPlatform.common.service.AuthorizationManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityBindModel;
import com.company.IntelligentPlatform.common.model.GenericServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.IServiceJSONBasicErrorCode;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SystemExecutorLog;
import com.company.IntelligentPlatform.common.model.SystemExecutorSetting;
import com.company.IntelligentPlatform.common.model.SystemExecutorSettingConfigureProxy;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Logic Manager CLASS FOR Service Entity [SystemExecutorSetting]
 *
 * @author
 * @date Thu Jan 03 10:44:07 CST 2019
 * <p>
 * This class is generated automatically by platform automation register
 * tool
 */
@Service
@Transactional
public class SystemExecutorSettingManager extends ServiceEntityManager {

	public static final String METHOD_ConvSystemExecutorSettingToUI = "convSystemExecutorSettingToUI";

	public static final String METHOD_ConvUIToSystemExecutorSetting = "convUIToSystemExecutorSetting";

	public static final String METHOD_ConvAuthorizationObjectToUI = "convAuthorizationObjectToUI";

	public static final String METHOD_ConvSystemExecutorLogToUI = "convSystemExecutorLogToUI";

	public static final String METHOD_ConvLogonUserToLogUI = "convLogonUserToLogUI";

	public static final String EXECUTOR_systemRegularUpgrade = "systemRegularUpgradeExecutor";

	@Autowired
	protected BsearchService bsearchService;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected SystemExecutorSettingRepository systemExecutorSettingDAO;
	@Autowired
	protected AuthorizationObjectManager authorizationObjectManager;

	@Autowired
	protected SystemExecutorSettingConfigureProxy systemExecutorSettingConfigureProxy;

	@Autowired
	protected SystemExecutorRepository systemExecutorRepository;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected SystemConfigureResourceManager systemConfigureResourceManager;

	@Autowired
	protected SpringContextBeanService springContextBeanService;

	@Autowired
	protected SystemExecutorSearchSearchProxy systemExecutorSearchSearchProxy;

	@Autowired
	protected AuthorizationManager authorizationManager;

	protected Logger logger = LoggerFactory.getLogger(SystemExecutorSettingManager.class);

	private Map<String, Map<Integer, String>> resultMapLan = new HashMap<>();

	private Map<String, Map<Integer, String>> executionTypeMapLan = new HashMap<>();

	private Map<String, Map<String, String>> systemExecutorMapLan = new HashMap<>();

	public Map<Integer, String> initResultMap(String languageCode) throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode, this.resultMapLan, SystemExecutorLogUIModel.class, "result");
	}

	public Map<Integer, String> initExecutionTypeMap(String languageCode) throws ServiceEntityInstallationException {
		return ServiceLanHelper
				.initDefLanguageMapUIModel(languageCode, this.executionTypeMapLan, SystemExecutorSettingUIModel.class, "executionType");
	}

	public Map<String, String> initSystemExecutorMap(String languageCode) throws ServiceEntityInstallationException {
		String resourcePath = SystemExecutorRepository.class.getResource("").getPath() + "SystemExecutorRepository";
		return ServiceLanHelper.initDefLanguageStrMapResource(languageCode,
				this.systemExecutorMapLan, resourcePath);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convSystemExecutorSettingToUI(SystemExecutorSetting systemExecutorSetting,
			SystemExecutorSettingUIModel systemExecutorSettingUIModel) throws ServiceEntityInstallationException {
		convSystemExecutorSettingToUI(systemExecutorSetting, systemExecutorSettingUIModel, null);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convSystemExecutorSettingToUI(SystemExecutorSetting systemExecutorSetting,
			SystemExecutorSettingUIModel systemExecutorSettingUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		if (systemExecutorSetting != null) {
			if (!ServiceEntityStringHelper.checkNullString(systemExecutorSetting.getUuid())) {
				systemExecutorSettingUIModel.setUuid(systemExecutorSetting.getUuid());
			}
			if (!ServiceEntityStringHelper.checkNullString(systemExecutorSetting.getParentNodeUUID())) {
				systemExecutorSettingUIModel.setParentNodeUUID(systemExecutorSetting.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(systemExecutorSetting.getRootNodeUUID())) {
				systemExecutorSettingUIModel.setRootNodeUUID(systemExecutorSetting.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(systemExecutorSetting.getClient())) {
				systemExecutorSettingUIModel.setClient(systemExecutorSetting.getClient());
			}
			systemExecutorSettingUIModel.setNote(systemExecutorSetting.getNote());
			systemExecutorSettingUIModel.setRefAOUUID(systemExecutorSetting.getRefAOUUID());
			systemExecutorSettingUIModel.setRefActionCode(systemExecutorSetting.getRefActionCode());
			systemExecutorSettingUIModel.setId(systemExecutorSetting.getId());
			systemExecutorSettingUIModel.setExecuteBatchNumber(systemExecutorSetting.getExecuteBatchNumber());
			systemExecutorSettingUIModel.setExecutionType(systemExecutorSetting.getExecutionType());
			systemExecutorSettingUIModel.setRefExecutorId(systemExecutorSetting.getRefExecutorId());
			systemExecutorSettingUIModel.setRequestContent(systemExecutorSetting.getRequestContent());
			try {
				ServiceEntityNode systemExecutorTemplate = this.getExecutorTemplateById(systemExecutorSetting.getRefExecutorId());
				if (systemExecutorTemplate != null) {
					systemExecutorSettingUIModel.setRefExecutorName(systemExecutorTemplate.getName());
					systemExecutorSettingUIModel.setRefExecutorDescription(systemExecutorTemplate.getNote());
				}
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "getExecutorTemplateById"));
			}
			if (logonInfo != null) {
				Map<Integer, String> executionTypeMap = this.initExecutionTypeMap(logonInfo.getLanguageCode());
				systemExecutorSettingUIModel.setExecutionTypeValue(executionTypeMap.get(systemExecutorSetting.getExecutionType()));
			}
			systemExecutorSettingUIModel.setProxyName(systemExecutorSetting.getProxyName());
			systemExecutorSettingUIModel.setRefPreExecuteSettingUUID(systemExecutorSetting.getRefPreExecuteSettingUUID());
			systemExecutorSettingUIModel.setName(systemExecutorSetting.getName());
		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:systemExecutorSetting
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToSystemExecutorSetting(SystemExecutorSettingUIModel systemExecutorSettingUIModel,
			SystemExecutorSetting rawEntity) {
		if (!ServiceEntityStringHelper.checkNullString(systemExecutorSettingUIModel.getUuid())) {
			rawEntity.setUuid(systemExecutorSettingUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper.checkNullString(systemExecutorSettingUIModel.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(systemExecutorSettingUIModel.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(systemExecutorSettingUIModel.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(systemExecutorSettingUIModel.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(systemExecutorSettingUIModel.getClient())) {
			rawEntity.setClient(systemExecutorSettingUIModel.getClient());
		}
		if (!ServiceEntityStringHelper.checkNullString(systemExecutorSettingUIModel.getRefExecutorId())) {
			rawEntity.setRefExecutorId(systemExecutorSettingUIModel.getRefExecutorId());
		}
		rawEntity.setRequestContent(systemExecutorSettingUIModel.getRequestContent());
		rawEntity.setNote(systemExecutorSettingUIModel.getNote());
		rawEntity.setRefAOUUID(systemExecutorSettingUIModel.getRefAOUUID());
		rawEntity.setRefActionCode(systemExecutorSettingUIModel.getRefActionCode());
		rawEntity.setId(systemExecutorSettingUIModel.getId());
		rawEntity.setExecuteBatchNumber(systemExecutorSettingUIModel.getExecuteBatchNumber());
		rawEntity.setExecutionType(systemExecutorSettingUIModel.getExecutionType());
		rawEntity.setProxyName(systemExecutorSettingUIModel.getProxyName());
		rawEntity.setClient(systemExecutorSettingUIModel.getClient());
		rawEntity.setRefPreExecuteSettingUUID(systemExecutorSettingUIModel.getRefPreExecuteSettingUUID());
		rawEntity.setName(systemExecutorSettingUIModel.getName());
		rawEntity.setUuid(systemExecutorSettingUIModel.getUuid());
	}

	public List<ServiceEntityNode> searchInternal(SystemExecutorSettingSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException, NodeNotFoundException,
			ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[systemExecutorSetting]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(SystemExecutorSetting.SENAME);
		searchNodeConfig0.setNodeName(SystemExecutorSetting.NODENAME);
		searchNodeConfig0.setNodeInstID(SystemExecutorSetting.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[systemExecutorLog]
		BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
		searchNodeConfig2.setSeName(SystemExecutorLog.SENAME);
		searchNodeConfig2.setNodeName(SystemExecutorLog.NODENAME);
		searchNodeConfig2.setNodeInstID(SystemExecutorLog.NODENAME);
		searchNodeConfig2.setStartNodeFlag(false);
		searchNodeConfig2.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig2.setBaseNodeInstID(SystemExecutorSetting.SENAME);
		searchNodeConfigList.add(searchNodeConfig2);
		// Search node:[authorizationObject]
		BSearchNodeComConfigure searchNodeConfig4 = new BSearchNodeComConfigure();
		searchNodeConfig4.setSeName(AuthorizationObject.SENAME);
		searchNodeConfig4.setNodeName(AuthorizationObject.NODENAME);
		searchNodeConfig4.setNodeInstID(AuthorizationObject.SENAME);
		searchNodeConfig4.setStartNodeFlag(false);
		searchNodeConfig4.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig4.setMapBaseFieldName("refAOUUID");
		searchNodeConfig4.setMapSourceFieldName("uuid");
		searchNodeConfig4.setBaseNodeInstID(SystemExecutorSetting.SENAME);
		searchNodeConfigList.add(searchNodeConfig4);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(searchModel, searchNodeConfigList, client, true);
		return resultList;
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, systemExecutorSettingDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(systemExecutorSettingConfigureProxy);
	}

	public ServiceEntityNode getExecutorTemplateById(String refExecutorId)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
		return packageSystemExecutorTemplate(systemExecutorRepository.getExecutorTemplateById(refExecutorId));
	}

	public List<ServiceEntityNode> getAllExecutorList(int status, int executionType)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
		List<ServiceEntityNode> resultList = new ArrayList<>();
		List<SystemExecutorTemplate> rawSystemExecutorTemplateList = systemExecutorRepository
				.getAllExecutorList(status, executionType);
		if (ServiceCollectionsHelper.checkNullList(rawSystemExecutorTemplateList)) {
			return new ArrayList<>();
		} else {
			for (SystemExecutorTemplate systemExecutorTemplate : rawSystemExecutorTemplateList) {
				resultList.add(packageSystemExecutorTemplate(systemExecutorTemplate));
			}
		}
		return resultList;
	}

	private ServiceEntityNode packageSystemExecutorTemplate(SystemExecutorTemplate systemExecutorTemplate) {
		ServiceEntityNode seNode = new GenericServiceEntityNode();
		seNode.setId(systemExecutorTemplate.getId());
		seNode.setName(systemExecutorTemplate.getName());
		seNode.setNote(systemExecutorTemplate.getDescription());
		return seNode;
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convSystemExecutorLogToUI(SystemExecutorLog systemExecutorLog, SystemExecutorLogUIModel systemExecutorLogUIModel)
			throws ServiceEntityInstallationException {
		convSystemExecutorLogToUI(systemExecutorLog, systemExecutorLogUIModel, null);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convSystemExecutorLogToUI(SystemExecutorLog systemExecutorLog, SystemExecutorLogUIModel systemExecutorLogUIModel,
			LogonInfo logonInfo) throws ServiceEntityInstallationException {
		if (systemExecutorLog != null) {
			if (!ServiceEntityStringHelper.checkNullString(systemExecutorLog.getUuid())) {
				systemExecutorLogUIModel.setUuid(systemExecutorLog.getUuid());
			}
			if (!ServiceEntityStringHelper.checkNullString(systemExecutorLog.getParentNodeUUID())) {
				systemExecutorLogUIModel.setParentNodeUUID(systemExecutorLog.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(systemExecutorLog.getRootNodeUUID())) {
				systemExecutorLogUIModel.setRootNodeUUID(systemExecutorLog.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(systemExecutorLog.getClient())) {
				systemExecutorLogUIModel.setClient(systemExecutorLog.getClient());
			}
			systemExecutorLogUIModel.setResult(systemExecutorLog.getResult());
			systemExecutorLogUIModel.setName(systemExecutorLog.getName());
			systemExecutorLogUIModel.setNote(systemExecutorLog.getNote());
			systemExecutorLogUIModel.setId(systemExecutorLog.getId());
			if (systemExecutorLog.getLastUpdateTime() != null) {
				systemExecutorLogUIModel
						.setExecutedDate(DefaultDateFormatConstant.DATE_MIN_FORMAT.format(systemExecutorLog.getLastUpdateTime()));
			}
			if (logonInfo != null) {
				Map<Integer, String> resultMap = this.initResultMap(logonInfo.getLanguageCode());
				systemExecutorLogUIModel.setResultValue(resultMap.get(systemExecutorLog.getResult()));
			}
			systemExecutorLogUIModel.setCreatedBy(systemExecutorLog.getCreatedBy());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convLogonUserToLogUI(LogonUser logonUser, SystemExecutorLogUIModel systemExecutorLogUIModel) {
		if (logonUser != null) {
			systemExecutorLogUIModel.setRefLogonUserId(logonUser.getId());
			systemExecutorLogUIModel.setRefLogonUserName(logonUser.getName());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convAuthorizationObjectToUI(AuthorizationObject authorizationObject,
			SystemExecutorSettingUIModel systemExecutorSettingUIModel) {
		if (authorizationObject != null) {
			if (!ServiceEntityStringHelper.checkNullString(authorizationObject.getClient())) {
				systemExecutorSettingUIModel.setClient(authorizationObject.getClient());
			}
			systemExecutorSettingUIModel.setAuthorizationObjectId(authorizationObject.getId());
			systemExecutorSettingUIModel.setAuthorizationObjectName(authorizationObject.getName());
		}
	}

	public String executeDailyUpdate(LogonInfo logonInfo) throws ServiceComExecuteException,
			SystemConfigureResourceException {
		String client = logonInfo.getClient();
		String organizationUUID = logonInfo.getResOrgUUID();
		try {
			SystemExecutorSetting systemExecutorSetting = (SystemExecutorSetting) this
					.getEntityNodeByKey(EXECUTOR_systemRegularUpgrade, IServiceEntityNodeFieldConstant.ID, SystemExecutorSetting.NODENAME, client, null);
			if (systemExecutorSetting != null){
				// excute directly
				return executeCore(systemExecutorSetting, logonInfo, logonInfo.getResOrgUUID());
			} else {
				// Insert into DB and execute
				systemExecutorSetting = this.updateSystemExecutorSettingWrapper(EXECUTOR_systemRegularUpgrade, "系统日常更新",  ISystemExecutorAttr.EXECUTIONTYPE_INITIALIZE, logonInfo, false);
				return executeCore(systemExecutorSetting, logonInfo, logonInfo.getResOrgUUID());
			}
		} catch (ServiceEntityConfigureException e) {
			logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "systemRegularUpgrade"));
			throw new ServiceComExecuteException(ServiceComExecuteException.PARA_SYSTEM_ERROR, e.getMessage());
		}
	}

	public void executeWrapper(String baseUUID, LogonInfo logonInfo) throws DocActionException {
        SystemExecutorSetting systemExecutorSetting = null;
        try {
            systemExecutorSetting = (SystemExecutorSetting) this
                    .getEntityNodeByUUID(baseUUID,
                            SystemExecutorSetting.NODENAME,
                            logonInfo.getClient());
			if (!ServiceEntityStringHelper
					.checkNullString(systemExecutorSetting.getRefAOUUID())) {
				AuthorizationObject authorizationObject = (AuthorizationObject) authorizationObjectManager
						.getEntityNodeByUUID(
								systemExecutorSetting.getRefAOUUID(),
								AuthorizationObject.NODENAME, logonInfo.getClient());
				if (authorizationObject != null) {
					authorizationManager.checkAuthorizationWrapper(logonInfo,
							authorizationObject.getId(),
							ISystemActionCode.ACID_EDIT);
				}
			}
			executeCore(
					systemExecutorSetting, logonInfo, logonInfo.getResOrgUUID());
        } catch (ServiceEntityConfigureException | AuthorizationException | SystemConfigureResourceException |
                 LogonInfoException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
    }

	public SystemExecutorSetting updateSystemExecutorSettingWrapper(String id, String name,
																	int executionType, LogonInfo logonInfo, boolean override)
			throws ServiceEntityConfigureException {
		return updateSystemExecutorSettingWrapper(id, id, name, executionType, logonInfo, override);
	}

	public SystemExecutorSetting updateSystemExecutorSettingWrapper(String executorId, String id, String name,
			int executionType, LogonInfo logonInfo, boolean override)
			throws ServiceEntityConfigureException {
		SystemExecutorSetting systemExecutorSetting = (SystemExecutorSetting) getEntityNodeByKey(id, IServiceEntityNodeFieldConstant.ID,
						SystemExecutorSetting.NODENAME, logonInfo.getClient(),
						null, true);
		if (systemExecutorSetting != null) {
			if (!override) {
				return systemExecutorSetting;
			}
		}
		if (systemExecutorSetting == null) {
			systemExecutorSetting = (SystemExecutorSetting) newRootEntityNode(logonInfo.getClient());
		}
		systemExecutorSetting.setId(id);
		if(ServiceEntityStringHelper.checkNullString(name)){
			// Get default name from repository resources
			try {
				Map<String, String> executorMap = this.initSystemExecutorMap(logonInfo.getLanguageCode());
				String localName = executorMap.get(executorId);
				systemExecutorSetting.setName(localName);
			} catch (ServiceEntityInstallationException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
			}
		} else {
			systemExecutorSetting.setName(name);
		}
		systemExecutorSetting.setRefExecutorId(id);
		systemExecutorSetting.setExecutionType(executionType);
		updateSENode(systemExecutorSetting,
				logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
		return systemExecutorSetting;
	}

	/**
	 * Core Logic to get executor service and execute
	 *
	 * @param systemExecutorSetting
	 * @return
	 * @throws SystemConfigureResourceException
	 */
	public String executeCore(SystemExecutorSetting systemExecutorSetting, LogonInfo logonInfo, String organizationUUID)
			throws SystemConfigureResourceException {
		if (ServiceEntityStringHelper.checkNullString(systemExecutorSetting.getRefExecutorId())) {
			throw new SystemConfigureResourceException(SystemConfigureResourceException.PARA_WRG_TARGET_CLASS,
					systemExecutorSetting.getRefExecutorId());
		}
		SystemExecutorTemplate systemExecutorTemplate;
		try {
			systemExecutorTemplate = systemExecutorRepository.getExecutorTemplateById(systemExecutorSetting.getRefExecutorId());
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException e2) {
			throw new SystemConfigureResourceException(SystemConfigureResourceException.PARA_WRG_TARGET_CLASS,
					systemExecutorSetting.getRefExecutorId());
		}
		if (systemExecutorTemplate == null) {
			throw new SystemConfigureResourceException(SystemConfigureResourceException.PARA_WRG_TARGET_CLASS,
					systemExecutorSetting.getRefExecutorId());
		}
		/*
		 * [Step2] Execution Core
		 */
		try {
			String result = systemExecutorTemplate.execute(logonInfo);
			@SuppressWarnings("rawtypes")
			Map<String, Class> classMap = new HashMap<>();
			JSONObject jsonObject = JSONObject.fromObject(result);
			ServiceJSONResponseObject serviceJSONResponseObject = (ServiceJSONResponseObject) JSONObject
					.toBean(jsonObject, ServiceJSONResponseObject.class, classMap);
			int checkResult = systemExecutorTemplate.checkResult(logonInfo);
			if (serviceJSONResponseObject.getErrorCode() == IServiceJSONBasicErrorCode.DEF_OK
					&& checkResult == IServiceJSONBasicErrorCode.DEF_OK) {
				recordExecutorLog(systemExecutorSetting, SystemCheckResultProxy.CHECKRESULT_OK, logonInfo.getLogonUser(),
						organizationUUID);
			} else {
				recordExecutorLog(systemExecutorSetting, SystemCheckResultProxy.CHECKRESULT_ERROR, logonInfo.getLogonUser(),
						organizationUUID);
			}
			return result;
		} catch (Exception e) {
			try {
				recordExecutorLog(systemExecutorSetting, SystemCheckResultProxy.CHECKRESULT_ERROR, logonInfo.getLogonUser(), organizationUUID);
			} catch (ServiceEntityConfigureException e1) {
				// do nothing
			}
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	private void recordExecutorLog(SystemExecutorSetting systemExecutorSetting, int result, LogonUser logonUser,
			String organizationUUID) throws ServiceEntityConfigureException {
		SystemExecutorLog systemExecutorLog = (SystemExecutorLog) newEntityNode(systemExecutorSetting, SystemExecutorLog.NODENAME);
		systemExecutorLog.setResult(result);
		setAdminData(systemExecutorLog, ServiceEntityBindModel.PROCESSMODE_CREATE, logonUser.getUuid(), organizationUUID);
		insertSENode(systemExecutorLog, logonUser.getUuid(), organizationUUID);
	}

	public void checkProxyClassValid(String proxyName) throws ClassNotFoundException, SystemConfigureResourceException {
		Class<?> proxyClass = Class.forName(proxyName);
		// Check if this inherit from super class
		if (proxyClass == null) {

		}
		boolean checkSuper = systemConfigureResourceManager
				.checkClassExtendSuperClass(proxyClass, SystemExecutorTemplate.class.getSimpleName());
		if (!checkSuper) {
			throw new SystemConfigureResourceException(SystemConfigureResourceException.PARA2_WRG_SUPER_CLASS,
					SystemExecutorTemplate.class.getSimpleName(), SystemExecutorTemplate.class.getSimpleName());
		}
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return systemExecutorSearchSearchProxy;
	}

}
