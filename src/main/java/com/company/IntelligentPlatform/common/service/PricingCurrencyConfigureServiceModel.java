package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.PricingCurrencyConfigure;

public class PricingCurrencyConfigureServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = PricingCurrencyConfigure.NODENAME, nodeInstId = PricingCurrencyConfigure.SENAME)
	protected PricingCurrencyConfigure pricingCurrencyConfigure;

	public PricingCurrencyConfigure getPricingCurrencyConfigure() {
		return pricingCurrencyConfigure;
	}

	public void setPricingCurrencyConfigure(PricingCurrencyConfigure pricingCurrencyConfigure) {
		this.pricingCurrencyConfigure = pricingCurrencyConfigure;
	}

}
