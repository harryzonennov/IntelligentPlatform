package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.SystemConfigureCategorySearchModel;
import com.company.IntelligentPlatform.common.dto.SystemConfigureCategoryUIModel;
import com.company.IntelligentPlatform.common.dto.SystemConfigureElementSearchModel;
import com.company.IntelligentPlatform.common.dto.SystemConfigureElementUIModel;
import com.company.IntelligentPlatform.common.dto.SystemConfigureUIFieldUIModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.SystemConfigureCategoryRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.StandardSystemCategoryProxy;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchNodeMapping;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SystemConfigureCategory;
import com.company.IntelligentPlatform.common.model.SystemConfigureCategoryConfigureProxy;
import com.company.IntelligentPlatform.common.model.SystemConfigureElement;
import com.company.IntelligentPlatform.common.model.SystemConfigureExtensionUnion;
import com.company.IntelligentPlatform.common.model.SystemConfigureResource;
import com.company.IntelligentPlatform.common.model.SystemConfigureUIField;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
@Transactional
public class SystemConfigureCategoryManager extends ServiceEntityManager {

	public static final String METHOD_ConvSystemConfigureCategoryToUI = "convSystemConfigureCategoryToUI";

	public static final String METHOD_ConvUIToSystemConfigureCategory = "convUIToSystemConfigureCategory";

	public static final String METHOD_ConvSystemConfigureUIFieldToUI = "convSystemConfigureUIFieldToUI";

	public static final String METHOD_ConvUIToSystemConfigureUIField = "convUIToSystemConfigureUIField";
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected SystemConfigureCategoryRepository systemConfigureCategoryDAO;
	@Autowired
	protected SystemConfigureCategoryConfigureProxy systemConfigureCategoryConfigureProxy;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected StandardSystemCategoryProxy standardSystemCategoryProxy;

	@Autowired
	protected BsearchService bsearchService;

	private Map<String, Map<Integer, String>> scenarioModeMapLan = new HashMap<String, Map<Integer, String>>();

	private Map<String, Map<Integer, String>> subScenarioModeMapLan = new HashMap<String, Map<Integer, String>>();

	private Map<String, Map<Integer, String>> elementTypeMapLan = new HashMap<String, Map<Integer, String>>();

	public Map<Integer, String> initSystemStandardCategoryMap(String languageCode)
			throws ServiceEntityInstallationException {
		return standardSystemCategoryProxy
				.getSystemCategoryMap(languageCode);
	}

	public Map<Integer, String> initScenarioModeMap(String languageCode) throws ServiceEntityInstallationException {
		return ServiceLanHelper
				.initDefLanguageMapUIModel(languageCode, this.scenarioModeMapLan, SystemConfigureCategoryUIModel.class, "scenarioMode");
	}

	public Map<Integer, String> initSubScenarioModeMap(String languageCode) throws ServiceEntityInstallationException {
		return ServiceLanHelper
				.initDefLanguageMapUIModel(languageCode, this.subScenarioModeMapLan, SystemConfigureElementUIModel.class, "subScenarioMode");
	}

	public Map<Integer, String> initElementTypeMap(String languageCode) throws ServiceEntityInstallationException {
		return ServiceLanHelper
				.initDefLanguageMapUIModel(languageCode, this.elementTypeMapLan, SystemConfigureElementUIModel.class, "elementType");
	}

	/**
	 * Check if the extension union value hit the target value(id)
	 * 
	 * @param extensionId: extension id
	 * @param parentResourceId: [optional] parent resource id
	 * @param configureValueId: Target value id for compare
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public boolean checkExtensionUnionValueIdHit(String extensionId,
			String parentResourceId, String configureValueId, String client)
			throws ServiceEntityConfigureException {
		SystemConfigureExtensionUnion systemConfigureExtensionUnion = this
				.getExtensionUnionResource(extensionId, parentResourceId,
						client);
		if (systemConfigureExtensionUnion == null
				|| ServiceEntityStringHelper.checkNullString(configureValueId)) {
			return false;
		} else {
			if (configureValueId.equals(systemConfigureExtensionUnion
					.getConfigureValueId())) {
				return true;
			}
		}
		return false;
	}

	public SystemConfigureExtensionUnion getExtensionUnionResource(
			String extensionId, String parentResourceId, String client)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> rawSENodeList = this.getEntityNodeListByKey(
				extensionId, IServiceEntityNodeFieldConstant.ID,
				SystemConfigureExtensionUnion.NODENAME, client, null, true);
		if (ServiceCollectionsHelper.checkNullList(rawSENodeList)) {
			return null;
		}
		if (ServiceEntityStringHelper.checkNullString(parentResourceId)) {
			return (SystemConfigureExtensionUnion) rawSENodeList.get(0);
		}
		for (ServiceEntityNode seNode : rawSENodeList) {
			SystemConfigureExtensionUnion rawExtensionUnion = (SystemConfigureExtensionUnion) seNode;
			ServiceEntityNode parentResource = getSystemResourceTemplate(
					rawExtensionUnion.getParentNodeUUID(),
					IServiceEntityNodeFieldConstant.UUID, null, client);
			if (parentResource != null
					&& parentResourceId.equals(parentResource.getId())) {
				return rawExtensionUnion;
			}
		}
		return null;
	}

	public void updateExtensionUnion(String extensionId,
									 String parentResourceId, String configureValueId, String client) throws ServiceEntityConfigureException, SystemConfigureResourceException {
		ServiceEntityNode parentResource = getSystemResourceTemplate(
				parentResourceId,
				IServiceEntityNodeFieldConstant.ID, null, client);
		if (parentResource == null) {
			throw new SystemConfigureResourceException(SystemConfigureResourceException.PARA_SYSTEM_WRONG,
					parentResourceId);
		}
		SystemConfigureExtensionUnion rawExtensionUnion =
				(SystemConfigureExtensionUnion)this.newEntityNode(parentResource,
						SystemConfigureExtensionUnion.NODENAME);

	}

	public ServiceEntityNode getSystemResourceTemplate(String keyValue,
			String keyName, String nodeName, String client)
			throws ServiceEntityConfigureException {
		if (ServiceEntityStringHelper.checkNullString(nodeName)) {
			SystemConfigureElement targetElement = (SystemConfigureElement) getSystemResourceTemplateByNodeName(
					keyValue, keyName, SystemConfigureElement.NODENAME, client);
			if (targetElement != null) {
				return targetElement;
			}
			SystemConfigureResource targetResource = (SystemConfigureResource) getSystemResourceTemplateByNodeName(
					keyValue, keyName, SystemConfigureResource.NODENAME, client);
			if (targetResource != null) {
				return targetResource;
			}
			SystemConfigureCategory targetCategory = (SystemConfigureCategory) getSystemResourceTemplateByNodeName(
					keyValue, keyName, SystemConfigureCategory.NODENAME, client);
			if (targetCategory != null) {
				return targetCategory;
			}
			return null;
		} else {
			return getSystemResourceTemplateByNodeName(keyValue, keyName,
					nodeName, client);
		}
	}

	private ServiceEntityNode getSystemResourceTemplateByNodeName(
			String keyValue, String keyName, String nodeName, String client)
			throws ServiceEntityConfigureException {
		if (SystemConfigureCategory.NODENAME.equals(nodeName)) {
			SystemConfigureCategory targetCategory = (SystemConfigureCategory) getEntityNodeByKey(
					keyValue, keyName, SystemConfigureElement.NODENAME, null);
			if (targetCategory != null) {
				return targetCategory;
			}
			return null;
		}
		if (SystemConfigureResource.NODENAME.equals(nodeName)) {
			SystemConfigureResource targetResource = (SystemConfigureResource) getEntityNodeByKey(
					keyValue, keyName, SystemConfigureResource.NODENAME, null);
			if (targetResource != null) {
				return targetResource;
			}
			return null;
		}
		if (SystemConfigureElement.NODENAME.equals(nodeName)) {
			SystemConfigureElement targetElement = (SystemConfigureElement) getEntityNodeByKey(
					keyValue, keyName, SystemConfigureElement.NODENAME, null);
			if (targetElement != null) {
				return targetElement;
			}
			return null;
		}
		return null;
	}

	/**
	 * Get all [switch-on] navigation element type configure element
	 * 
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<SystemConfigureElement> getAllSwitchOnNavigationElement(
			LogonUser logonUser) throws ServiceEntityConfigureException {
		List<ServiceEntityNode> rawElementList = getEntityNodeListByKey(
				SystemConfigureElement.ELETYPE_NAVI_ELEMENT,
				SystemConfigureElement.FIELD_ELETYPE,
				SystemConfigureElement.NODENAME, logonUser.getClient(), null);
		if (rawElementList == null || rawElementList.size() == 0) {
			return null;
		}
		List<ServiceEntityNode> rawResourceList = getEntityNodeListByKey(null,
				null, SystemConfigureResource.NODENAME, logonUser.getClient(),
				null);
		List<ServiceEntityNode> rawCategoryList = getEntityNodeListByKey(null,
				null, SystemConfigureCategory.NODENAME, logonUser.getClient(),
				null);
		List<SystemConfigureElement> resultList = new ArrayList<SystemConfigureElement>();
		for (ServiceEntityNode seNode : rawElementList) {
			SystemConfigureElement systemConfigureElement = (SystemConfigureElement) seNode;
			if (checkElementSwitchOn(systemConfigureElement, rawResourceList,
					rawCategoryList)) {
				resultList.add(systemConfigureElement);
			}
		}
		return resultList;
	}

	/**
	 * Core logic to check if this configureElement is switch on or not
	 * 
	 * @param systemConfigureElement
	 * @param rawResourceList
	 *            : could be null value
	 * @param rawCategoryList
	 *            : could be null value
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public boolean checkElementSwitchOn(
			SystemConfigureElement systemConfigureElement,
			List<ServiceEntityNode> rawResourceList,
			List<ServiceEntityNode> rawCategoryList)
			throws ServiceEntityConfigureException {
		/**
		 * [Step1] Check element itself, if element self is switch off, then
		 * return false value
		 */
		if (systemConfigureElement.getScenarioMode() == SystemConfigureCategory.SCENARIO_MODE_OFF) {
			return false;
		}
		/**
		 * [Step2] Check parent resource exist or not, if parent element is
		 * switch off, then return false value
		 */
		// Get resource on line
		SystemConfigureResource configureResource = (SystemConfigureResource) getEntityNodeByKey(
				systemConfigureElement.getParentNodeUUID(),
				IServiceEntityNodeFieldConstant.UUID,
				SystemConfigureResource.NODENAME, rawResourceList);
		if (configureResource != null
				&& configureResource.getScenarioMode() == SystemConfigureCategory.SCENARIO_MODE_OFF) {
			return false;
		}
		if (configureResource == null) {
			/**
			 * [Step 2.5] In case there might be parent element exist
			 */
			SystemConfigureElement parentElement = (SystemConfigureElement) getEntityNodeByKey(
					systemConfigureElement.getParentNodeUUID(),
					IServiceEntityNodeFieldConstant.UUID,
					SystemConfigureElement.NODENAME, null);
			if (parentElement != null) {
				// recursive call this method
				checkElementSwitchOn(parentElement, rawResourceList,
						rawCategoryList);
			} else {
				// In case invalid element
				return false;
			}
		}
		/**
		 * [Step3] Check parent category exist or not, if parent element is
		 * switch off, then return false value
		 */
		SystemConfigureCategory configureCategory = (SystemConfigureCategory) getEntityNodeByKey(
				systemConfigureElement.getRootNodeUUID(),
				IServiceEntityNodeFieldConstant.UUID,
				SystemConfigureCategory.NODENAME, rawCategoryList);
		return checkConfigureCategorySwitchOn(configureCategory);
	}

	public boolean checkConfigureCategorySwitchOn(
			SystemConfigureCategory configureCategory) {
		if (configureCategory != null
				&& configureCategory.getScenarioMode() == SystemConfigureCategory.SCENARIO_MODE_OFF) {
			return false;
		}
		return true;
	}

	public List<ServiceEntityNode> searchConfigureElementInternal(
			SystemConfigureElementSearchModel searchModel, String client)
			throws SearchConfigureException,
			ServiceEntityInstallationException,
			ServiceEntityConfigureException, NodeNotFoundException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[systemConfigureElement]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(SystemConfigureElement.SENAME);
		searchNodeConfig0.setNodeName(SystemConfigureElement.NODENAME);
		searchNodeConfig0.setNodeInstID(SystemConfigureElement.NODENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
		searchNodeConfig1.setSeName(SystemConfigureResource.SENAME);
		searchNodeConfig1.setNodeName(SystemConfigureResource.NODENAME);
		searchNodeConfig1.setNodeInstID(SystemConfigureResource.NODENAME);
		searchNodeConfig1.setBaseNodeInstID(SystemConfigureElement.NODENAME);
		searchNodeConfig1
				.setToBaseNodeType(BSearchNodeComConfigure.TOBASENODE_TO_CHILD);
		searchNodeConfigList.add(searchNodeConfig1);
		BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
		searchNodeConfig2.setSeName(SystemConfigureCategory.SENAME);
		searchNodeConfig2.setNodeName(SystemConfigureCategory.NODENAME);
		searchNodeConfig2.setNodeInstID(SystemConfigureCategory.SENAME);
		searchNodeConfig2.setBaseNodeInstID(SystemConfigureResource.NODENAME);
		searchNodeConfig2
				.setToBaseNodeType(BSearchNodeComConfigure.TOBASENODE_TO_CHILD);
		searchNodeConfigList.add(searchNodeConfig2);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}

	public void convUIToSystemConfigureUIField(
			SystemConfigureUIFieldUIModel systemConfigureUIFieldUIModel,
			SystemConfigureUIField rawEnity)
			throws ServiceEntityInstallationException {
		if (systemConfigureUIFieldUIModel != null) {
			if (!ServiceEntityStringHelper
					.checkNullString(systemConfigureUIFieldUIModel.getUuid())) {
				rawEnity.setUuid(systemConfigureUIFieldUIModel.getUuid());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(systemConfigureUIFieldUIModel
							.getParentNodeUUID())) {
				rawEnity.setParentNodeUUID(systemConfigureUIFieldUIModel
						.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(systemConfigureUIFieldUIModel
							.getRootNodeUUID())) {
				rawEnity.setRootNodeUUID(systemConfigureUIFieldUIModel
						.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(systemConfigureUIFieldUIModel.getClient())) {
				rawEnity.setClient(systemConfigureUIFieldUIModel.getClient());
			}
			rawEnity.setId(systemConfigureUIFieldUIModel.getId());
			rawEnity.setName(systemConfigureUIFieldUIModel.getName());
			rawEnity.setNote(systemConfigureUIFieldUIModel.getNote());
			rawEnity.setInternationl18Content(systemConfigureUIFieldUIModel
					.getInternationl18Content());
			rawEnity.setSetI18NFlag(systemConfigureUIFieldUIModel
					.getSetI18NFlag());
			rawEnity.setShowFieldFlag(systemConfigureUIFieldUIModel
					.getShowFieldFlag());

		}
	}

	public void convSystemConfigureUIFieldToUI(
			SystemConfigureUIField systemConfigureUIField,
			SystemConfigureUIFieldUIModel systemConfigureUIFieldUIModel)
			throws ServiceEntityInstallationException {
		if (systemConfigureUIFieldUIModel != null) {
			systemConfigureUIFieldUIModel.setUuid(systemConfigureUIField
					.getUuid());
			systemConfigureUIFieldUIModel
					.setParentNodeUUID(systemConfigureUIField
							.getParentNodeUUID());
			systemConfigureUIFieldUIModel
					.setRootNodeUUID(systemConfigureUIField.getRootNodeUUID());
			systemConfigureUIFieldUIModel.setClient(systemConfigureUIField
					.getClient());
			systemConfigureUIFieldUIModel.setId(systemConfigureUIField.getId());
			systemConfigureUIFieldUIModel.setName(systemConfigureUIField
					.getName());
			systemConfigureUIFieldUIModel.setNote(systemConfigureUIField
					.getNote());
			systemConfigureUIFieldUIModel
					.setInternationl18Content(systemConfigureUIField
							.getInternationl18Content());
			systemConfigureUIFieldUIModel.setSetI18NFlag(systemConfigureUIField
					.getSetI18NFlag());
			systemConfigureUIFieldUIModel
					.setShowFieldFlag(systemConfigureUIField.getShowFieldFlag());

		}
	}

	public void convSystemConfigureCategoryToUI(
			SystemConfigureCategory systemConfigureCategory,
			SystemConfigureCategoryUIModel systemConfigureCategoryUIModel)
			throws ServiceEntityInstallationException {
		convSystemConfigureCategoryToUI(systemConfigureCategory, systemConfigureCategoryUIModel, null);
	}

	public void convSystemConfigureCategoryToUI(
			SystemConfigureCategory systemConfigureCategory,
			SystemConfigureCategoryUIModel systemConfigureCategoryUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		if (systemConfigureCategory != null) {
			if (!ServiceEntityStringHelper
					.checkNullString(systemConfigureCategory.getUuid())) {
				systemConfigureCategoryUIModel.setUuid(systemConfigureCategory
						.getUuid());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(systemConfigureCategory
							.getParentNodeUUID())) {
				systemConfigureCategoryUIModel
						.setParentNodeUUID(systemConfigureCategory
								.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(systemConfigureCategory.getRootNodeUUID())) {
				systemConfigureCategoryUIModel
						.setRootNodeUUID(systemConfigureCategory
								.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(systemConfigureCategory.getClient())) {
				systemConfigureCategoryUIModel
						.setClient(systemConfigureCategory.getClient());
			}
			systemConfigureCategoryUIModel.setId(systemConfigureCategory
					.getId());
			systemConfigureCategoryUIModel.setName(systemConfigureCategory
					.getName());
			systemConfigureCategoryUIModel.setNote(systemConfigureCategory
					.getNote());
			systemConfigureCategoryUIModel
					.setParentNodeUUID(systemConfigureCategory
							.getParentNodeUUID());
			systemConfigureCategoryUIModel
					.setScenarioMode(systemConfigureCategory.getScenarioMode());
			systemConfigureCategoryUIModel
					.setStandardSystemCategory(systemConfigureCategory
							.getStandardSystemCategory());
			if(logonInfo != null){
				Map<Integer, String> scenarioModeMap = this.initScenarioModeMap(logonInfo.getLanguageCode());
				systemConfigureCategoryUIModel
						.setScenarioModeValue(scenarioModeMap
								.get(systemConfigureCategory.getScenarioMode()));
				Map<Integer, String> systemStandardCategoryMap = initSystemStandardCategoryMap(logonInfo.getLanguageCode());
				String systemStandardCategoryValue = systemStandardCategoryMap
						.get(systemConfigureCategory.getStandardSystemCategory());
				systemConfigureCategoryUIModel
						.setStandardSystemCategoryValue(systemStandardCategoryValue);
			}
		}
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, systemConfigureCategoryDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(systemConfigureCategoryConfigureProxy);
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:systemConfigureCategory
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToSystemConfigureCategory(
			SystemConfigureCategoryUIModel systemConfigureCategoryUIModel,
			SystemConfigureCategory rawEntity) {
		if (!ServiceEntityStringHelper
				.checkNullString(systemConfigureCategoryUIModel.getUuid())) {
			rawEntity.setUuid(systemConfigureCategoryUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(systemConfigureCategoryUIModel
						.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(systemConfigureCategoryUIModel
					.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(systemConfigureCategoryUIModel
						.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(systemConfigureCategoryUIModel
					.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(systemConfigureCategoryUIModel.getClient())) {
			rawEntity.setClient(systemConfigureCategoryUIModel.getClient());
		}
		rawEntity.setId(systemConfigureCategoryUIModel.getId());
		rawEntity.setClient(systemConfigureCategoryUIModel.getClient());
		rawEntity.setNote(systemConfigureCategoryUIModel.getNote());
		rawEntity.setStandardSystemCategory(systemConfigureCategoryUIModel
				.getStandardSystemCategory());
		rawEntity.setScenarioMode(systemConfigureCategoryUIModel
				.getScenarioMode());
		rawEntity.setUuid(systemConfigureCategoryUIModel.getUuid());
		rawEntity.setName(systemConfigureCategoryUIModel.getName());
	}

	public List<ServiceEntityNode> searchInternal(
			SystemConfigureCategorySearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[systemConfigureCategory]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(SystemConfigureCategory.SENAME);
		searchNodeConfig0.setNodeName(SystemConfigureCategory.NODENAME);
		searchNodeConfig0.setNodeInstID(SystemConfigureCategory.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[systemConfigureResource]
		BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
		searchNodeConfig1.setSeName(SystemConfigureResource.SENAME);
		searchNodeConfig1.setNodeName(SystemConfigureResource.NODENAME);
		searchNodeConfig1.setNodeInstID(SystemConfigureResource.NODENAME);
		searchNodeConfig1.setStartNodeFlag(false);
		searchNodeConfig1
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig1.setBaseNodeInstID(SystemConfigureCategory.SENAME);
		searchNodeConfigList.add(searchNodeConfig1);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}

}
