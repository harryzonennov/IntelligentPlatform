package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.PricingSettingUIModel;
import com.company.IntelligentPlatform.common.service.PricingSettingManager;
import com.company.IntelligentPlatform.common.model.PricingSetting;

@Service
public class PricingSettingServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected PricingCurrencyConfigureServiceUIModelExtension pricingCurrencyConfigureServiceUIModelExtension;

	@Autowired
	protected PricingSettingManager pricingSettingManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(pricingCurrencyConfigureServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion pricingSettingExtensionUnion = new ServiceUIModelExtensionUnion();
		pricingSettingExtensionUnion.setNodeInstId(PricingSetting.SENAME);
		pricingSettingExtensionUnion.setNodeName(PricingSetting.NODENAME);

		// UI Model Configure of node:[PricingSetting]
		UIModelNodeMapConfigure pricingSettingMap = new UIModelNodeMapConfigure();
		pricingSettingMap.setSeName(PricingSetting.SENAME);
		pricingSettingMap.setNodeName(PricingSetting.NODENAME);
		pricingSettingMap.setNodeInstID(PricingSetting.SENAME);
		pricingSettingMap.setHostNodeFlag(true);
		Class<?>[] pricingSettingConvToUIParas = { PricingSetting.class,
				PricingSettingUIModel.class };
		pricingSettingMap.setConvToUIMethodParas(pricingSettingConvToUIParas);
		pricingSettingMap
				.setConvToUIMethod(PricingSettingManager.METHOD_ConvPricingSettingToUI);
		Class<?>[] PricingSettingConvUIToParas = { PricingSettingUIModel.class,
				PricingSetting.class };
		pricingSettingMap.setConvUIToMethodParas(PricingSettingConvUIToParas);
		pricingSettingMap
				.setConvUIToMethod(PricingSettingManager.METHOD_ConvUIToPricingSetting);
		uiModelNodeMapList.add(pricingSettingMap);
		pricingSettingExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(pricingSettingExtensionUnion);
		return resultList;
	}

}
