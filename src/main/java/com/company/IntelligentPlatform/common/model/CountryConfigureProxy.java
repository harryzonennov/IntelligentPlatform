package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [Country]
 * 
 * @author
 * @date Sun Feb 10 13:23:44 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class CountryConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of Country [ROOT] node
		ServiceEntityConfigureMap countryConfigureMap = new ServiceEntityConfigureMap();
		countryConfigureMap.setParentNodeName(" ");
		countryConfigureMap.setNodeName("ROOT");
		countryConfigureMap.setNodeType(Country.class);
		countryConfigureMap.setTableName("Country");
		countryConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		countryConfigureMap.addNodeFieldMap("countryCode",
				java.lang.String.class);
		countryConfigureMap.addNodeFieldMap("languageCode",
				java.lang.String.class);
		countryConfigureMap.addNodeFieldMap("countryTeleCode",
				java.lang.String.class);
		seConfigureMapList.add(countryConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
