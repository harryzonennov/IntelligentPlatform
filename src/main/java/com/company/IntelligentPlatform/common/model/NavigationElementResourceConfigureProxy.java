package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [NavigationElementResource]
 * 
 * @author
 * @date Fri Oct 03 17:57:30 CST 2014
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class NavigationElementResourceConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of NavigationElementResource [ROOT] node
		ServiceEntityConfigureMap navigationElementResourceConfigureMap = new ServiceEntityConfigureMap();
		navigationElementResourceConfigureMap.setParentNodeName(" ");
		navigationElementResourceConfigureMap
				.setNodeName(NavigationElementResource.NODENAME);
		navigationElementResourceConfigureMap
				.setNodeType(NavigationElementResource.class);
		navigationElementResourceConfigureMap
				.setTableName(NavigationElementResource.SENAME);
		navigationElementResourceConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		navigationElementResourceConfigureMap.addNodeFieldMap("indexInGroup",
				int.class);
		navigationElementResourceConfigureMap.addNodeFieldMap("linkURL",
				java.lang.String.class);
		navigationElementResourceConfigureMap.addNodeFieldMap("instSwitch",
				int.class);
		navigationElementResourceConfigureMap.addNodeFieldMap("displaySwitch",
				int.class);
		navigationElementResourceConfigureMap.addNodeFieldMap(
				"refSimAuthorObjectUUID", java.lang.String.class);
		navigationElementResourceConfigureMap.addNodeFieldMap(
				"refAuthorActionCodeUUID", java.lang.String.class);
		navigationElementResourceConfigureMap.addNodeFieldMap(
				"refNavigationGroupUUID", java.lang.String.class);
		navigationElementResourceConfigureMap.addNodeFieldMap("elementTitle",
				java.lang.String.class);
		navigationElementResourceConfigureMap.addNodeFieldMap(
				"parentElementUUID", java.lang.String.class);		
		navigationElementResourceConfigureMap.addNodeFieldMap("elementIcon",
				java.lang.String.class);
		navigationElementResourceConfigureMap.addNodeFieldMap(
				"tabs", java.lang.String.class);
		navigationElementResourceConfigureMap.addNodeFieldMap("targetPageLink",
				java.lang.String.class);
		seConfigureMapList.add(navigationElementResourceConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
