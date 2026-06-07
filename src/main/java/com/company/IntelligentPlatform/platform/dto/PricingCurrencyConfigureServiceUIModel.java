package com.company.IntelligentPlatform.platform.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.service.PricingSettingManager;
import com.company.IntelligentPlatform.platform.model.PricingCurrencyConfigure;
import com.company.IntelligentPlatform.platform.model.PricingSetting;

import java.util.ArrayList;
import java.util.List;

@Component
public class PricingCurrencyConfigureServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = PricingCurrencyConfigure.NODENAME, nodeInstId = PricingCurrencyConfigure.NODENAME)
	protected PricingCurrencyConfigureUIModel pricingCurrencyConfigureUIModel;

	public PricingCurrencyConfigureUIModel getPricingCurrencyConfigureUIModel() {
		return pricingCurrencyConfigureUIModel;
	}

	public void setPricingCurrencyConfigureUIModel(PricingCurrencyConfigureUIModel pricingCurrencyConfigureUIModel) {
		this.pricingCurrencyConfigureUIModel = pricingCurrencyConfigureUIModel;
	}
}
