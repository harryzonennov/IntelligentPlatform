package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.PricingCurrencyConfigureUIModel;
import com.company.IntelligentPlatform.common.service.PricingSettingManager;
import com.company.IntelligentPlatform.common.model.PricingCurrencyConfigure;

@Service
public class PricingCurrencyConfigureServiceUIModelExtension extends
		ServiceUIModelExtension {

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion pricingCurrencyConfigureExtensionUnion = new ServiceUIModelExtensionUnion();
		pricingCurrencyConfigureExtensionUnion
				.setNodeInstId(PricingCurrencyConfigure.NODENAME);
		pricingCurrencyConfigureExtensionUnion
				.setNodeName(PricingCurrencyConfigure.NODENAME);

		// UI Model Configure of node:[PricingCurrencyConfigure]
		UIModelNodeMapConfigure pricingCurrencyConfigureMap = new UIModelNodeMapConfigure();
		pricingCurrencyConfigureMap.setSeName(PricingCurrencyConfigure.SENAME);
		pricingCurrencyConfigureMap
				.setNodeName(PricingCurrencyConfigure.NODENAME);
		pricingCurrencyConfigureMap
				.setNodeInstID(PricingCurrencyConfigure.NODENAME);
		pricingCurrencyConfigureMap.setHostNodeFlag(true);
		Class<?>[] pricingCurrencyConfigureConvToUIParas = {
				PricingCurrencyConfigure.class,
				PricingCurrencyConfigureUIModel.class };
		pricingCurrencyConfigureMap
				.setConvToUIMethodParas(pricingCurrencyConfigureConvToUIParas);
		pricingCurrencyConfigureMap
				.setConvToUIMethod(PricingSettingManager.METHOD_ConvPricingCurrencyConfigureToUI);
		Class<?>[] PricingCurrencyConfigureConvUIToParas = {
				PricingCurrencyConfigureUIModel.class,
				PricingCurrencyConfigure.class };
		pricingCurrencyConfigureMap
				.setConvUIToMethodParas(PricingCurrencyConfigureConvUIToParas);
		pricingCurrencyConfigureMap
				.setConvUIToMethod(PricingSettingManager.METHOD_ConvUIToPricingCurrencyConfigure);
		uiModelNodeMapList.add(pricingCurrencyConfigureMap);
		pricingCurrencyConfigureExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(pricingCurrencyConfigureExtensionUnion);
		return resultList;
	}

}
