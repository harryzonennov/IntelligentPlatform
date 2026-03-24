package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.ServiceExtensionSettingSearchModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.SerExtendPageSetting;
import com.company.IntelligentPlatform.common.model.ServiceExtensionSetting;

import java.util.List;
import java.util.Map;

@Service
public class ServiceExtensionSettingSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected ServiceExtensionSettingManager serviceExtensionSettingManager;
	
	@Override
	public Class<?> getDocSearchModelCls() {
		return ServiceExtensionSettingSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return serviceExtensionSettingManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return null;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList =
				SearchModelConfigHelper.buildParentChildConfigure(ServiceExtensionSetting.class,
						SerExtendPageSetting.class);
		return searchNodeConfigList;
	}

	//TODO check this search logic later
	public List<BSearchNodeComConfigure> getPageSettingSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList =
				SearchModelConfigHelper.buildParentChildConfigure(ServiceExtensionSetting.class,
						SerExtendPageSetting.class);
		return searchNodeConfigList;
	}

	public BSearchResponse searchPageSettingList(SearchContext searchContext) throws SearchConfigureException,
			ServiceEntityConfigureException, ServiceEntityInstallationException, AuthorizationException,
			LogonInfoException {
		List<BSearchNodeComConfigure> searchNodeConfigList = getPageSettingSearchNodeConfigureList(searchContext);
		searchContext.setFieldNameArray(getDocFieldNameArray());
		searchContext.setFuzzyFlag(true);
		return bsearchService.doSearchWithContext(
				searchContext, searchNodeConfigList);
	}
}
