package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [NavigationGroupResource]
 * 
 * @author
 * @date Fri Oct 03 18:00:24 CST 2014
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class NavigationGroupResourceConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of NavigationGroupResource [ROOT] node
		ServiceEntityConfigureMap navigationGroupResourceConfigureMap = new ServiceEntityConfigureMap();
		navigationGroupResourceConfigureMap.setParentNodeName(" ");
		navigationGroupResourceConfigureMap
				.setNodeName(NavigationGroupResource.NODENAME);
		navigationGroupResourceConfigureMap
				.setNodeType(NavigationGroupResource.class);
		navigationGroupResourceConfigureMap
				.setTableName(NavigationGroupResource.SENAME);
		navigationGroupResourceConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		navigationGroupResourceConfigureMap.addNodeFieldMap(
				"defStartElementID", java.lang.String.class);
		navigationGroupResourceConfigureMap.addNodeFieldMap("instSwitch",
				int.class);
		navigationGroupResourceConfigureMap.addNodeFieldMap("displaySwitch",
				int.class);
		navigationGroupResourceConfigureMap.addNodeFieldMap("displayIndex",
				int.class);
		navigationGroupResourceConfigureMap.addNodeFieldMap("groupTitle",
				String.class);
		seConfigureMapList.add(navigationGroupResourceConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
