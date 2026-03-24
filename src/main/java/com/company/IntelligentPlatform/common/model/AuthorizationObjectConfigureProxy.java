package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [Authorization]
 * 
 * @author
 * @date Sun Dec 23 00:11:35 CST 2012
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class AuthorizationObjectConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of Authorization [ROOT] node
		ServiceEntityConfigureMap authorizationConfigureMap = new ServiceEntityConfigureMap();
		authorizationConfigureMap.setParentNodeName(" ");
		authorizationConfigureMap.setNodeName("ROOT");
		authorizationConfigureMap.setNodeType(AuthorizationObject.class);
		authorizationConfigureMap.setTableName("AuthorizationObject");
		authorizationConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		authorizationConfigureMap.addNodeFieldMap("enableFlag", boolean.class);
		authorizationConfigureMap.addNodeFieldMap("authorizationObjectType",
				int.class);
		authorizationConfigureMap.addNodeFieldMap("authorizationObjectType",
				int.class);
		authorizationConfigureMap.addNodeFieldMap("systemAuthorCheck",
				int.class);
		authorizationConfigureMap.addNodeFieldMap("subSystemAuthorNeed",
				int.class);
		authorizationConfigureMap.addNodeFieldMap("simObjectArray",
				String.class);		
		seConfigureMapList.add(authorizationConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
