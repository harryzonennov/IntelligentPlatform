package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.service.PricingSettingManager;
import com.company.IntelligentPlatform.common.model.PricingCurrencyConfigure;
import com.company.IntelligentPlatform.common.model.PricingSetting;

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
