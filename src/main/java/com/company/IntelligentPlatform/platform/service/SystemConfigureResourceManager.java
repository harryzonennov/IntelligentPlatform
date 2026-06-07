package com.company.IntelligentPlatform.platform.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.platform.controller.PageHeaderModel;
import com.company.IntelligentPlatform.platform.dto.SystemConfigureResourceUIModel;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.platform.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.platform.model.LogonInfo;
import com.company.IntelligentPlatform.platform.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.platform.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.platform.model.SystemConfigureCategory;
import com.company.IntelligentPlatform.platform.model.SystemConfigureElement;
import com.company.IntelligentPlatform.platform.model.SystemConfigureResource;
import com.company.IntelligentPlatform.platform.controller.SEUIComModel;

@Service
public class SystemConfigureResourceManager {

	public static final String METHOD_ConvSystemConfigureResourceToUI = "convSystemConfigureResourceToUI";

	public static final String METHOD_ConvUIToSystemConfigureResource = "convUIToSystemConfigureResource";

	public static final String METHOD_ConvCategoryToResourceUI = "convCategoryToResourceUI";
	
	@Autowired
	protected SystemConfigureCategoryManager systemConfigureCategoryManager;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	public boolean checkClassExtendSuperClass(Class<?> classType,
			String superClassName) {
		Class<?> superClass = classType.getSuperclass();
		if (superClass == null) {
			return false;
		}
		if (superClassName.equals(superClass.getSimpleName())) {
			return true;
		}
		return false;
	}

	public boolean checkClassImplementInterface(Class<?> classType,
			String targetInterface) throws SystemConfigureException {
		Class<?>[] interfaceArray = classType.getInterfaces();
		if (interfaceArray.length == 0) {
			throw new SystemConfigureException(
					SystemConfigureException.PARA2_WRG_SWITCH_INTERFACE,
					classType.getSimpleName(), targetInterface);
		}
		for (int i = 0; i < interfaceArray.length; i++) {
			if (targetInterface.equals(interfaceArray[i].getSimpleName())) {
				return true;
			}
		}
		return false;
	}

	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(),
						SystemConfigureCategory.NODENAME,
						request.getUuid(), SystemConfigureElement.NODENAME, systemConfigureCategoryManager);
		docPageHeaderInputPara.setGenBaseModelList(new DocPageHeaderModelProxy.GenBaseModelList<SystemConfigureCategory>() {
			@Override
			public List<com.company.IntelligentPlatform.platform.controller.PageHeaderModel> execute(SystemConfigureCategory systemConfigureCategory) throws ServiceEntityConfigureException {
				// How to get the base page header model list
				return docPageHeaderModelProxy.getDocPageHeaderModelList(systemConfigureCategory,
						IServiceEntityNodeFieldConstant.NAME);
			}
		});
		docPageHeaderInputPara.setGenHomePageModel(new DocPageHeaderModelProxy.GenHomePageModel<SystemConfigureResource>() {
			@Override
			public com.company.IntelligentPlatform.platform.controller.PageHeaderModel execute(SystemConfigureResource systemConfigureResource, com.company.IntelligentPlatform.platform.controller.PageHeaderModel pageHeaderModel) throws ServiceEntityConfigureException {
				// How to render current page header
				pageHeaderModel.setHeaderName(systemConfigureResource.getId());
				if(!ServiceEntityStringHelper.checkNullString(systemConfigureResource.getName())){
					pageHeaderModel.setHeaderName(systemConfigureResource.getId() + "-" + systemConfigureResource.getName());
				}
				return pageHeaderModel;
			}
		});
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}

	public void convSystemConfigureResourceToUI(
			SystemConfigureResource systemConfigureResource,
			SystemConfigureResourceUIModel systemConfigureResourceUIModel)
			throws ServiceEntityInstallationException {
		convSystemConfigureResourceToUI(systemConfigureResource, systemConfigureResourceUIModel, null);
	}

	public void convSystemConfigureResourceToUI(
			SystemConfigureResource systemConfigureResource,
			SystemConfigureResourceUIModel systemConfigureResourceUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		if (systemConfigureResource != null) {
			if (!ServiceEntityStringHelper
					.checkNullString(systemConfigureResource.getUuid())) {
				systemConfigureResourceUIModel.setUuid(systemConfigureResource
						.getUuid());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(systemConfigureResource
							.getParentNodeUUID())) {
				systemConfigureResourceUIModel
						.setParentNodeUUID(systemConfigureResource
								.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(systemConfigureResource.getRootNodeUUID())) {
				systemConfigureResourceUIModel
						.setRootNodeUUID(systemConfigureResource
								.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(systemConfigureResource.getClient())) {
				systemConfigureResourceUIModel
						.setClient(systemConfigureResource.getClient());
			}
			systemConfigureResourceUIModel.setId(systemConfigureResource
					.getId());
			systemConfigureResourceUIModel.setName(systemConfigureResource
					.getName());
			systemConfigureResourceUIModel.setNote(systemConfigureResource
					.getNote());
			systemConfigureResourceUIModel
					.setScenarioMode(systemConfigureResource.getScenarioMode());
			systemConfigureResourceUIModel
					.setStandardSystemCategory(systemConfigureResource
							.getStandardSystemCategory());
			if(logonInfo != null){
				Map<Integer, String> scenarioModeMap = systemConfigureCategoryManager.initScenarioModeMap(logonInfo.getLanguageCode());
				systemConfigureResourceUIModel
						.setScenarioModeValue(scenarioModeMap
								.get(systemConfigureResource.getScenarioMode()));
				Map<Integer, String> systemStandardCategoryMap =
						systemConfigureCategoryManager.initSystemStandardCategoryMap(logonInfo.getLanguageCode());
				String systemStandardCategoryValue = systemStandardCategoryMap
						.get(systemConfigureResource.getStandardSystemCategory());
				systemConfigureResourceUIModel
						.setStandardSystemCategoryValue(systemStandardCategoryValue);
			}
		}
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:systemConfigureResource
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToSystemConfigureResource(
			SystemConfigureResourceUIModel systemConfigureResourceUIModel,
			SystemConfigureResource rawEntity) {
		if (!ServiceEntityStringHelper
				.checkNullString(systemConfigureResourceUIModel.getUuid())) {
			rawEntity.setUuid(systemConfigureResourceUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(systemConfigureResourceUIModel
						.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(systemConfigureResourceUIModel
					.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(systemConfigureResourceUIModel
						.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(systemConfigureResourceUIModel
					.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(systemConfigureResourceUIModel.getClient())) {
			rawEntity.setClient(systemConfigureResourceUIModel.getClient());
		}
		rawEntity.setId(systemConfigureResourceUIModel.getId());
		rawEntity.setScenarioMode(systemConfigureResourceUIModel
				.getScenarioMode());
		rawEntity.setNote(systemConfigureResourceUIModel.getNote());
		rawEntity.setName(systemConfigureResourceUIModel.getName());
		rawEntity.setStandardSystemCategory(systemConfigureResourceUIModel
				.getStandardSystemCategory());
	}

	public void convCategoryToResourceUI(
			SystemConfigureCategory systemConfigureCategory,
			SystemConfigureResourceUIModel systemConfigureResourceUIModel)
			throws ServiceEntityInstallationException {
		if (systemConfigureResourceUIModel != null) {
			systemConfigureResourceUIModel
					.setParentNodeId(systemConfigureCategory.getId());
		}
	}

}
