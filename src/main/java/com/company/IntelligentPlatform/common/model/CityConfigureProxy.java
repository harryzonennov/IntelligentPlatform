package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [City]
 * 
 * @author
 * @date Sun Feb 10 14:06:07 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class CityConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of City [ROOT] node
		ServiceEntityConfigureMap cityConfigureMap = new ServiceEntityConfigureMap();
		cityConfigureMap.setParentNodeName(" ");
		cityConfigureMap.setNodeName("ROOT");
		cityConfigureMap.setNodeType(City.class);
		cityConfigureMap.setTableName("City");
		cityConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		cityConfigureMap
				.addNodeFieldMap("teleAreaCode", java.lang.String.class);
		cityConfigureMap.addNodeFieldMap("postcode", java.lang.String.class);
		cityConfigureMap.addNodeFieldMap("cityLevel", int.class);
		seConfigureMapList.add(cityConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
