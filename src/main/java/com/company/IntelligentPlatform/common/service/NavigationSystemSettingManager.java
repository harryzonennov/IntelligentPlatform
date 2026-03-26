package com.company.IntelligentPlatform.common.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.CorporateCustomerUIModel;
import com.company.IntelligentPlatform.common.dto.CorporateSupplierUIModel;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.dto.NavigationGroupSettingUIModel;
import com.company.IntelligentPlatform.common.dto.NavigationSystemSettingSearchModel;
import com.company.IntelligentPlatform.common.dto.NavigationSystemSettingUIModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.NavigationSystemSettingRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ActionCode;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.NavigationSystemSetting;
import com.company.IntelligentPlatform.common.model.NavigationSystemSettingConfigureProxy;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SystemConfigureCategory;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Logic Manager CLASS FOR Service Entity [NavigationSystemSetting]
 *
 * @author
 * @date Thu Aug 06 23:43:51 CST 2020
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class NavigationSystemSettingManager extends ServiceEntityManager {

	public static final String METHOD_ConvGroupActionCodeToUI = "convGroupActionCodeToUI";

	public static final String METHOD_ConvNavigationSystemSettingToUI = "convNavigationSystemSettingToUI";

	public static final String METHOD_ConvUIToNavigationSystemSetting = "convUIToNavigationSystemSetting";
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected NavigationSystemSettingRepository navigationSystemSettingDAO;
	@Autowired
	protected NavigationSystemSettingConfigureProxy navigationSystemSettingConfigureProxy;

	@Autowired
	protected StandardSwitchProxy standardSwitchProxy;

	@Autowired
	protected StandardSystemCategoryProxy standardSystemCategoryProxy;

	@Autowired
	protected NavigationSystemSettingSearchProxy navigationSystemSettingSearchProxy;

	private final Map<String, Map<Integer, String>> applicationLevelMapLan = new HashMap<>();

	private final Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

	@Autowired
	protected BsearchService bsearchService;

	protected Logger logger = LoggerFactory
			.getLogger(NavigationSystemSettingManager.class);

	public NavigationSystemSettingManager() {
		super.seConfigureProxy = new NavigationSystemSettingConfigureProxy();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, navigationSystemSettingDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(navigationSystemSettingConfigureProxy);
	}

	public Map<Integer, String> initStatusMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.statusMapLan, NavigationSystemSettingUIModel.class,
				"status");
	}

	public Map<Integer, String> initApplicationLevelMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.applicationLevelMapLan,
				NavigationSystemSettingUIModel.class, "applicationLevel");
	}

	public Map<Integer, String> initSystemCategoryMap(String languageCode)
			throws ServiceEntityInstallationException {
		return standardSystemCategoryProxy.getSystemCategoryMap(languageCode);
	}

	public List<PageHeaderModel> getPageHeaderModelList(
			NavigationSystemSetting navigationSystemSetting, String client)
			throws ServiceEntityConfigureException {
		int index = 0;
		List<PageHeaderModel> resultList = new ArrayList<>();
		PageHeaderModel itemHeaderModel = getPageHeaderModel(
				navigationSystemSetting, index);
		if (itemHeaderModel != null) {
			resultList.add(itemHeaderModel);
		}
		return resultList;
	}

	protected PageHeaderModel getPageHeaderModel(
			NavigationSystemSetting navigationSystemSetting, int index)
			throws ServiceEntityConfigureException {
		if (navigationSystemSetting == null) {
			return null;
		}
		PageHeaderModel pageHeaderModel = new PageHeaderModel();
		pageHeaderModel.setPageTitle("navigationSystemSettingPageTitle");
		pageHeaderModel.setNodeInstId(NavigationSystemSetting.SENAME);
		pageHeaderModel.setUuid(navigationSystemSetting.getUuid());
		if (navigationSystemSetting != null) {
			pageHeaderModel.setHeaderName(navigationSystemSetting.getId());
		}
		pageHeaderModel.setIndex(index);
		return pageHeaderModel;
	}

	//TODO need to check this logic in more detail
	public void activeSystemSetting(
			NavigationSystemSetting navigationSystemSetting,
			SerialLogonInfo serialLogonInfo)
			throws ServiceEntityConfigureException,
			NavigationSystemSettingException {
		/*
		 * [Step1] Check active status
		 */
		if (navigationSystemSetting.getApplicationLevel() == NavigationSystemSetting.APP_LEVEL_COMPANY) {
			// Check company wide duplicate active system
			NavigationSystemSettingSearchModel navigationSystemSettingSearchModel = new NavigationSystemSettingSearchModel();
			navigationSystemSettingSearchModel
					.setStatus(NavigationSystemSetting.STATUS_ACTIVE);
			try {
				List<ServiceEntityNode> backActiveSystemList = searchInternal(
						navigationSystemSettingSearchModel, serialLogonInfo.getClient());
				if (!ServiceCollectionsHelper
						.checkNullList(backActiveSystemList)) {
					NavigationSystemSetting backActiveSetting = (NavigationSystemSetting) backActiveSystemList
							.get(0);
					throw new NavigationSystemSettingException(
							NavigationSystemSettingException.PARA_ALREADY_COMPANYACTIVE,
							backActiveSetting.getId());
				}
			} catch (SearchConfigureException e) {
				throw new NavigationSystemSettingException(
						NavigationSystemSettingException.PARA_SYSTEM_ERROR,
						e.getErrorMessage());
			} catch (NodeNotFoundException | ServiceEntityInstallationException e) {
				throw new NavigationSystemSettingException(
						NavigationSystemSettingException.PARA_SYSTEM_ERROR,
						e.getMessage());
			}
		}
		/*
		 * [Step2] Update status to approved
		 */
		NavigationSystemSetting navigationSystemSettingBack = (NavigationSystemSetting) navigationSystemSetting
				.clone();
		updateSENode(navigationSystemSetting, navigationSystemSettingBack,
				serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convNavigationSystemSettingToUI(
			NavigationSystemSetting navigationSystemSetting,
			NavigationSystemSettingUIModel navigationSystemSettingUIModel,
			LogonInfo logonInfo) {
		if (navigationSystemSetting != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(navigationSystemSetting, navigationSystemSettingUIModel);
			navigationSystemSettingUIModel
					.setStatus(navigationSystemSetting.getStatus());
			if (logonInfo != null) {
				try {
					Map<Integer, String> statusMap = initStatusMap(logonInfo
							.getLanguageCode());
					navigationSystemSettingUIModel
							.setStatusValue(statusMap
									.get(navigationSystemSetting
											.getStatus()));
				} catch (ServiceEntityInstallationException e) {
					// log error and continue
					logger.error(ServiceEntityStringHelper
							.genDefaultErrorMessage(e, "status"));
				}
			}
			navigationSystemSettingUIModel
					.setResEmployeeUUID(navigationSystemSetting
							.getResEmployeeUUID());
			navigationSystemSettingUIModel
					.setServiceEntityName(navigationSystemSetting
							.getServiceEntityName());
			navigationSystemSettingUIModel
					.setResOrgUUID(navigationSystemSetting.getResOrgUUID());
			navigationSystemSettingUIModel.setId(navigationSystemSetting
					.getId());

			navigationSystemSettingUIModel
					.setApplicationLevel(navigationSystemSetting
							.getApplicationLevel());
			if (logonInfo != null) {
				try {
					Map<Integer, String> applicationLevelMap = this
							.initApplicationLevelMap(logonInfo.getLanguageCode());
					navigationSystemSettingUIModel
							.setApplicationLevelValue(applicationLevelMap
									.get(navigationSystemSetting
											.getApplicationLevel()));
				} catch (ServiceEntityInstallationException e) {
					// log error and continue
					logger.error(ServiceEntityStringHelper
							.genDefaultErrorMessage(e, "applicationLevel"));
				}
			}
			if (navigationSystemSetting.getCreatedTime() != null) {
				navigationSystemSettingUIModel
						.setCreatedTime(DefaultDateFormatConstant.DATE_FORMAT
								.format(navigationSystemSetting
										.getCreatedTime()));
			}
			navigationSystemSettingUIModel.setCreatedBy(navigationSystemSetting
					.getCreatedBy());
			navigationSystemSettingUIModel
					.setLastUpdateBy(navigationSystemSetting.getLastUpdateBy());
			if (navigationSystemSetting.getLastUpdateTime() != null) {
				navigationSystemSettingUIModel
						.setLastUpdateTime(DefaultDateFormatConstant.DATE_FORMAT
								.format(navigationSystemSetting
										.getLastUpdateTime()));
			}
			navigationSystemSettingUIModel.setNote(navigationSystemSetting
					.getNote());
			navigationSystemSettingUIModel
					.setSystemCategory(navigationSystemSetting
							.getSystemCategory());
			if (logonInfo != null) {
				try {
					Map<Integer, String> systemCategoryMap = this
							.initSystemCategoryMap(logonInfo.getLanguageCode());
					navigationSystemSettingUIModel
							.setSystemCategoryValue(systemCategoryMap
									.get(navigationSystemSetting
											.getSystemCategory()));
				} catch (ServiceEntityInstallationException e) {
					// log error and continue
					logger.error(ServiceEntityStringHelper
							.genDefaultErrorMessage(e, "systemCategory"));
				}
			}
		}
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:navigationSystemSetting
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToNavigationSystemSetting(
			NavigationSystemSettingUIModel navigationSystemSettingUIModel,
			NavigationSystemSetting rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(navigationSystemSettingUIModel, rawEntity);
		rawEntity.setStatus(navigationSystemSettingUIModel
				.getStatus());
		rawEntity.setResEmployeeUUID(navigationSystemSettingUIModel
				.getResEmployeeUUID());
		rawEntity.setServiceEntityName(navigationSystemSettingUIModel
				.getServiceEntityName());
		rawEntity.setResOrgUUID(navigationSystemSettingUIModel.getResOrgUUID());
		rawEntity.setApplicationLevel(navigationSystemSettingUIModel
				.getApplicationLevel());
		if (!ServiceEntityStringHelper
				.checkNullString(navigationSystemSettingUIModel
						.getCreatedTime())) {
			try {
				rawEntity
						.setCreatedTime(DefaultDateFormatConstant.DATE_FORMAT
								.parse(navigationSystemSettingUIModel
										.getCreatedTime()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setCreatedBy(navigationSystemSettingUIModel.getCreatedBy());
		rawEntity.setLastUpdateBy(navigationSystemSettingUIModel
				.getLastUpdateBy());
		if (!ServiceEntityStringHelper
				.checkNullString(navigationSystemSettingUIModel
						.getLastUpdateTime())) {
			try {
				rawEntity
						.setLastUpdateTime(DefaultDateFormatConstant.DATE_FORMAT
								.parse(navigationSystemSettingUIModel
										.getLastUpdateTime()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setSystemCategory(navigationSystemSettingUIModel
				.getSystemCategory());
	}

	public List<ServiceEntityNode> searchInternal(
			NavigationSystemSettingSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[navigationGroupSetting]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(NavigationSystemSetting.SENAME);
		searchNodeConfig0.setNodeName(NavigationSystemSetting.NODENAME);
		searchNodeConfig0.setNodeInstID(NavigationSystemSetting.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[defaultChildItemSetting]
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convGroupActionCodeToUI(ActionCode groupActionCode,
			NavigationGroupSettingUIModel navigationGroupSettingUIModel) {
		if (groupActionCode != null) {
			navigationGroupSettingUIModel.setRefActionCodeName(groupActionCode
					.getName());
			navigationGroupSettingUIModel.setRefActionCodeId(groupActionCode
					.getId());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convSystemConfigureCategoryToUI(
			SystemConfigureCategory systemConfigureCategory,
			NavigationGroupSettingUIModel navigationGroupSettingUIModel) {
		if (systemConfigureCategory != null) {
			navigationGroupSettingUIModel
					.setRefConfigureCategoryName(systemConfigureCategory
							.getName());
			navigationGroupSettingUIModel
					.setRefConfigureCategoryId(systemConfigureCategory.getId());
		}
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return navigationSystemSettingSearchProxy;
	}
}
