package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [PricingSetting]
 *
 * @author
 * @date Thu Jul 26 21:59:28 CST 2018
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class PricingSettingConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of PricingSetting [ROOT] node
		ServiceEntityConfigureMap pricingSettingConfigureMap = new ServiceEntityConfigureMap();
		pricingSettingConfigureMap.setParentNodeName(" ");
		pricingSettingConfigureMap.setNodeName(PricingSetting.NODENAME);
		pricingSettingConfigureMap.setNodeType(PricingSetting.class);
		pricingSettingConfigureMap.setTableName(PricingSetting.SENAME);
		pricingSettingConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		pricingSettingConfigureMap.addNodeFieldMap("multiCurrencyFlag",
				boolean.class);
		pricingSettingConfigureMap.addNodeFieldMap("defaultTaxRate",
				double.class);
		pricingSettingConfigureMap.addNodeFieldMap("defCurrencyCode",
				java.lang.String.class);
		seConfigureMapList.add(pricingSettingConfigureMap);
		// Init configuration of PricingSetting [PricingCurrencyConfigure] node
		ServiceEntityConfigureMap pricingCurrencyConfigureConfigureMap = new ServiceEntityConfigureMap();
		pricingCurrencyConfigureConfigureMap
				.setParentNodeName(PricingSetting.NODENAME);
		pricingCurrencyConfigureConfigureMap
				.setNodeName(PricingCurrencyConfigure.NODENAME);
		pricingCurrencyConfigureConfigureMap
				.setNodeType(PricingCurrencyConfigure.class);
		pricingCurrencyConfigureConfigureMap
				.setTableName(PricingCurrencyConfigure.NODENAME);
		pricingCurrencyConfigureConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		pricingCurrencyConfigureConfigureMap.addNodeFieldMap("currencyCode",
				java.lang.String.class);
		pricingCurrencyConfigureConfigureMap.addNodeFieldMap("exchangeRate",
				double.class);
		pricingCurrencyConfigureConfigureMap.addNodeFieldMap("defaultCurrency",
				boolean.class);
		pricingCurrencyConfigureConfigureMap.addNodeFieldMap("activeFlag",
				boolean.class);
		seConfigureMapList.add(pricingCurrencyConfigureConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
