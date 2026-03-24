package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [SystemAuthorizationObject]
 * 
 * @author
 * @date Tue Jun 11 18:39:21 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class SystemAuthorizationObjectConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of SystemAuthorizationObject [ROOT] node
		ServiceEntityConfigureMap systemAuthorizationObjectConfigureMap = new ServiceEntityConfigureMap();
		systemAuthorizationObjectConfigureMap.setParentNodeName(" ");
		systemAuthorizationObjectConfigureMap.setNodeName("ROOT");
		systemAuthorizationObjectConfigureMap
				.setNodeType(SystemAuthorizationObject.class);
		systemAuthorizationObjectConfigureMap
				.setTableName("SystemAuthorizationObject");
		systemAuthorizationObjectConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		systemAuthorizationObjectConfigureMap.addNodeFieldMap("enableFlag",
				boolean.class);
		systemAuthorizationObjectConfigureMap.addNodeFieldMap(
				"authorizationObjectType", int.class);
		systemAuthorizationObjectConfigureMap.addNodeFieldMap("deterMineName",
				java.lang.String.class);
		systemAuthorizationObjectConfigureMap.addNodeFieldMap("deterMineType",
				java.lang.String.class);
		systemAuthorizationObjectConfigureMap.addNodeFieldMap("simObjectArray",
				String.class);	
		seConfigureMapList.add(systemAuthorizationObjectConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
