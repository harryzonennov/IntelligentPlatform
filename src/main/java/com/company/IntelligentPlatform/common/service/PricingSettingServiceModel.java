package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.PricingCurrencyConfigure;
import com.company.IntelligentPlatform.common.model.PricingSetting;

public class PricingSettingServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = PricingSetting.NODENAME, nodeInstId = PricingSetting.SENAME)
	protected PricingSetting pricingSetting;

	@IServiceModuleFieldConfig(nodeName = PricingCurrencyConfigure.NODENAME, nodeInstId = PricingCurrencyConfigure.NODENAME)
	protected List<PricingCurrencyConfigureServiceModel> pricingCurrencyConfigureList = new ArrayList<>();

	public PricingSetting getPricingSetting() {
		return pricingSetting;
	}

	public void setPricingSetting(PricingSetting pricingSetting) {
		this.pricingSetting = pricingSetting;
	}

	public List<PricingCurrencyConfigureServiceModel> getPricingCurrencyConfigureList() {
		return pricingCurrencyConfigureList;
	}

	public void setPricingCurrencyConfigureList(List<PricingCurrencyConfigureServiceModel> pricingCurrencyConfigureList) {
		this.pricingCurrencyConfigureList = pricingCurrencyConfigureList;
	}

}
