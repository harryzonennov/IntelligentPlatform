package com.company.IntelligentPlatform.platform.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.platform.controller.PageHeaderModel;
import com.company.IntelligentPlatform.platform.dto.ServiceExtensionSettingUIModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.platform.repository.ServiceExtensionSettingRepository;
import com.company.IntelligentPlatform.platform.service.JpaServiceEntityDAO;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.ServiceLanHelper;
import com.company.IntelligentPlatform.platform.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.platform.service.DocFlowProxy;
import com.company.IntelligentPlatform.platform.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.platform.service.ServiceEntityManager;
import com.company.IntelligentPlatform.platform.model.LogonInfo;
import com.company.IntelligentPlatform.platform.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.platform.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.ServiceExtendFieldSetting;
import com.company.IntelligentPlatform.platform.model.ServiceExtensionSetting;
import com.company.IntelligentPlatform.platform.model.ServiceExtensionSettingConfigureProxy;
import com.company.IntelligentPlatform.platform.controller.SEUIComModel;

@Service
@Transactional
public class ServiceExtensionSettingManager extends ServiceEntityManager {

	public static final String METHOD_ConvServiceExtensionSettingToUI = "convServiceExtensionSettingToUI";

	public static final String METHOD_ConvUIToServiceExtensionSetting = "convUIToServiceExtensionSetting";
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected ServiceExtensionSettingRepository serviceExtensionSettingDAO;
	@Autowired
	protected ServiceExtensionSettingConfigureProxy serviceExtensionSettingConfigureProxy;

	@Autowired
	protected StandardSwitchProxy standardSwitchProxy;

	@Autowired
	protected ServiceLanHelper serviceLanHelper;

	@Autowired
	protected SerExtendUIControlSetManager serExtendUIControlSetManager;

	@Autowired
	protected SerExtendPageSettingManager serExtendPageSettingManager;

	@Autowired
	protected SerExtendPageSectionManager serExtendPageSectionManager;

	@Autowired
	protected ServiceExtendFieldSettingManager serviceExtendFieldSettingManager;

	@Autowired
	protected ServiceExtensionSettingSearchProxy serviceExtensionSettingSearchProxy;

	private Map<Integer, String> switchFlagMap;

	private Map<String, String> lanKeyMap;
	
	public List<PageHeaderModel> getPageHeaderModelList(
			ServiceExtensionSetting serviceExtensionSetting)
			throws ServiceEntityConfigureException {
		List<PageHeaderModel> resultList = new ArrayList<>();
		int index = 0;
		PageHeaderModel itemHeaderModel = getPageHeaderModel(
				serviceExtensionSetting, index);
		if (itemHeaderModel != null) {
			resultList.add(itemHeaderModel);
		}
		return resultList;
	}
	

	protected PageHeaderModel getPageHeaderModel(
			ServiceExtensionSetting serviceExtensionSetting, int index)
			throws ServiceEntityConfigureException {
		if (serviceExtensionSetting == null) {
			return null;
		}
		PageHeaderModel pageHeaderModel = new PageHeaderModel();
		pageHeaderModel.setPageTitle("serviceExtensionSettingPageTitle");
		pageHeaderModel.setNodeInstId(ServiceExtensionSetting.SENAME);
		pageHeaderModel.setUuid(serviceExtensionSetting.getUuid());
		pageHeaderModel.setHeaderName(serviceExtensionSetting.getId());
		pageHeaderModel.setIndex(index);
		return pageHeaderModel;
	}

	public ServiceExtensionSetting newDummyServiceExtensionSetting(String refSEName, String refNodeName,
																   String refNodeInstId, String client)
			throws ServiceEntityConfigureException {
		ServiceExtensionSetting serviceExtensionSetting = (ServiceExtensionSetting) newRootEntityNode(client);
		serviceExtensionSetting.setRefNodeName(refNodeName);
		String nodeInsId = ServiceEntityStringHelper.getDefModelId(refSEName, refNodeName);
		return serviceExtensionSetting;
	}

	/**
	 * Set initial value from system setting
	 * 
	 * @param resourceId
	 */
	public void setInitValue(Object rawObject, String resourceId) {
		try {
			ServiceExtensionSetting serviceExtensionSetting = (ServiceExtensionSetting) getEntityNodeByKey(
					resourceId, IServiceEntityNodeFieldConstant.ID,
					ServiceExtensionSetting.NODENAME, null, true);
			if (serviceExtensionSetting == null) {
				return;
			}
			List<ServiceEntityNode> serviceExtendFieldList = getEntityNodeListByKey(
					serviceExtensionSetting.getUuid(),
					IServiceEntityNodeFieldConstant.PARENTNODEUUID,
					ServiceExtendFieldSetting.NODENAME, null);
			if (ServiceCollectionsHelper.checkNullList(serviceExtendFieldList)) {
				return;
			}
			for (ServiceEntityNode seNode : serviceExtendFieldList) {
				ServiceExtendFieldSetting serviceExtendFieldSetting = (ServiceExtendFieldSetting) seNode;
				if (serviceExtendFieldSetting.getInitialValue() != null) {
					Field targetField;
					try {
						targetField = ServiceEntityFieldsHelper
								.getServiceField(rawObject.getClass(),
										serviceExtendFieldSetting
												.getFieldName());
						if (targetField != null) {
							targetField.setAccessible(true);
							targetField
									.set(rawObject, serviceExtendFieldSetting
											.getInitialValue());
						}
					} catch (IllegalArgumentException | IllegalAccessException
							| NoSuchFieldException e) {
						// Do nothing, just skip
					}
				}
			}
		} catch (ServiceEntityConfigureException e) {
			// Do nothing, just skip
		}
	}

	public List<ServiceEntityNode> getFieldSettingList(String nodeInstId,
			String client) throws ServiceEntityConfigureException {
		ServiceEntityNode serviceExtensionSetting = this.getEntityNodeByKey(
				nodeInstId, IServiceEntityNodeFieldConstant.ID,
				ServiceExtensionSetting.NODENAME, client, null, true);
		if (serviceExtensionSetting == null) {
			return null;
		}
		return this
				.getEntityNodeListByKey(serviceExtensionSetting.getUuid(),
						IServiceEntityNodeFieldConstant.ROOTNODEUUID, ServiceExtendFieldSetting.NODENAME, null);
	}

	public void convServiceExtensionSettingToUI(
			ServiceExtensionSetting serviceExtensionSetting,
			ServiceExtensionSettingUIModel serviceExtensionSettingUIModel) {
		convServiceExtensionSettingToUI(serviceExtensionSetting, serviceExtensionSettingUIModel, null);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convServiceExtensionSettingToUI(
			ServiceExtensionSetting serviceExtensionSetting,
			ServiceExtensionSettingUIModel serviceExtensionSettingUIModel, LogonInfo logonInfo) {
		if (serviceExtensionSetting != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(serviceExtensionSetting, serviceExtensionSettingUIModel);
			serviceExtensionSettingUIModel.setId(serviceExtensionSetting
					.getId());
			serviceExtensionSettingUIModel.setModelId(serviceExtensionSetting.getModelId());
			serviceExtensionSettingUIModel
					.setRefNodeName(serviceExtensionSetting.getRefNodeName());
			serviceExtensionSettingUIModel.setNote(serviceExtensionSetting
					.getNote());
			serviceExtensionSettingUIModel.setRefSEName(serviceExtensionSetting
					.getRefSEName());
			serviceExtensionSettingUIModel
					.setSwitchFlag(serviceExtensionSetting.getSwitchFlag());
		}
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:serviceExtensionSetting
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToServiceExtensionSetting(
			ServiceExtensionSettingUIModel serviceExtensionSettingUIModel,
			ServiceExtensionSetting rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(serviceExtensionSettingUIModel, rawEntity);
		rawEntity.setModelId(serviceExtensionSettingUIModel.getModelId());
		rawEntity.setRefNodeName(serviceExtensionSettingUIModel
				.getRefNodeName());
		rawEntity.setRefSEName(serviceExtensionSettingUIModel.getRefSEName());
		rawEntity.setUuid(serviceExtensionSettingUIModel.getUuid());
		rawEntity.setSwitchFlag(serviceExtensionSettingUIModel.getSwitchFlag());
	}

	public Map<Integer, String> initSwitchFlagMap(String languageCode)
			throws ServiceEntityInstallationException {
		return standardSwitchProxy.getSimpleSwitchMap(languageCode);
	}

	public Map<String, String> initLanKeyMap()
			throws ServiceEntityInstallationException {
		return serviceLanHelper.getLanMap();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setSeConfigureProxy(serviceExtensionSettingConfigureProxy);
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, serviceExtensionSettingDAO, this.getSeConfigureProxy()));
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return serviceExtensionSettingSearchProxy;
	}
}
