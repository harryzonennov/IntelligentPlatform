package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.PricingCurrencyConfigure;
@Component
public class PricingCurrencyConfigureSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "name", nodeName = PricingCurrencyConfigure.NODENAME, seName = PricingCurrencyConfigure.SENAME, 
			nodeInstID = PricingCurrencyConfigure.NODENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "id", nodeName = PricingCurrencyConfigure.NODENAME, seName = PricingCurrencyConfigure.SENAME, 
			nodeInstID = PricingCurrencyConfigure.NODENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "activeFlag", nodeName = PricingCurrencyConfigure.NODENAME, seName = PricingCurrencyConfigure.SENAME, 
			nodeInstID = PricingCurrencyConfigure.NODENAME)
	protected boolean activeFlag;
	
	
	@BSearchFieldConfig(fieldName = "defaultCurrency", nodeName = PricingCurrencyConfigure.NODENAME, seName = PricingCurrencyConfigure.SENAME, 
			nodeInstID = PricingCurrencyConfigure.NODENAME)
	protected boolean defaultCurrency;
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public boolean isDefaultCurrency() {
		return defaultCurrency;
	}

	public void setDefaultCurrency(boolean defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}

	

}
