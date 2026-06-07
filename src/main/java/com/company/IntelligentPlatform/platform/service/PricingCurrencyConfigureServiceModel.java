package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceModule;
import com.company.IntelligentPlatform.platform.model.PricingCurrencyConfigure;

public class PricingCurrencyConfigureServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = PricingCurrencyConfigure.NODENAME, nodeInstId = PricingCurrencyConfigure.NODENAME)
	protected PricingCurrencyConfigure pricingCurrencyConfigure;

	public PricingCurrencyConfigure getPricingCurrencyConfigure() {
		return pricingCurrencyConfigure;
	}

	public void setPricingCurrencyConfigure(PricingCurrencyConfigure pricingCurrencyConfigure) {
		this.pricingCurrencyConfigure = pricingCurrencyConfigure;
	}

}
