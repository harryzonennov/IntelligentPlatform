package com.company.IntelligentPlatform.platform.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.service.PricingSettingManager;
import com.company.IntelligentPlatform.platform.model.PricingCurrencyConfigure;
import com.company.IntelligentPlatform.platform.model.PricingSetting;

@Component
public class PricingSettingServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = PricingSetting.NODENAME, nodeInstId = PricingSetting.SENAME, convToUIMethod = PricingSettingManager.METHOD_ConvPricingSettingToUI, convUIToMethod = PricingSettingManager.METHOD_ConvUIToPricingSetting)
	protected PricingSettingUIModel pricingSettingUIModel;

	@IServiceUIModuleFieldConfig(nodeName = PricingCurrencyConfigure.NODENAME, nodeInstId = PricingCurrencyConfigure.NODENAME, convToUIMethod = PricingSettingManager.METHOD_ConvPricingCurrencyConfigureToUI, convUIToMethod = PricingSettingManager.METHOD_ConvUIToPricingCurrencyConfigure)
	protected List<PricingCurrencyConfigureServiceUIModel> pricingCurrencyConfigureUIModelList = new ArrayList<>();

	public PricingSettingUIModel getPricingSettingUIModel() {
		return this.pricingSettingUIModel;
	}

	public void setPricingSettingUIModel(
			PricingSettingUIModel pricingSettingUIModel) {
		this.pricingSettingUIModel = pricingSettingUIModel;
	}

	public List<PricingCurrencyConfigureServiceUIModel> getPricingCurrencyConfigureUIModelList() {
		return pricingCurrencyConfigureUIModelList;
	}

	public void setPricingCurrencyConfigureUIModelList(List<PricingCurrencyConfigureServiceUIModel> pricingCurrencyConfigureUIModelList) {
		this.pricingCurrencyConfigureUIModelList = pricingCurrencyConfigureUIModelList;
	}
}
