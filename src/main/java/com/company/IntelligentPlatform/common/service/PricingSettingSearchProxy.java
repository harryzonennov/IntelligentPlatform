package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.PricingCurrencyConfigureSearchModel;
import com.company.IntelligentPlatform.common.dto.PricingSettingSearchModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.PricingCurrencyConfigure;
import com.company.IntelligentPlatform.common.model.PricingSetting;

import java.util.List;
import java.util.Map;

@Service
public class PricingSettingSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected PricingSettingManager pricingSettingManager;
	
	@Override
	public Class<?> getDocSearchModelCls() {
		return PricingSettingSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return PricingCurrencyConfigureSearchModel.class;
	}

	@Override
	public String getAuthorizationResource() {
		return pricingSettingManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return null;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList =
				SearchModelConfigHelper.buildParentChildConfigure(PricingSetting.class,
						PricingCurrencyConfigure.class);
		searchNodeConfigList.addAll(SearchModelConfigHelper.buildResUserOrgConfigure(PricingSetting.class, null));
		return searchNodeConfigList;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicItemSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		// search node [Corporate contact person->Individual Customer]
        return SearchModelConfigHelper.buildChildParentConfigure(PricingCurrencyConfigure.class,
				PricingSetting.class);
	}
}
