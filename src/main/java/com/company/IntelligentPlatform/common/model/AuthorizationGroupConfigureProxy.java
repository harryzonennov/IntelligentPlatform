package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [AuthorizationGroup]
 * 
 * @author
 * @date Fri Jun 14 10:24:56 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class AuthorizationGroupConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of AuthorizationGroup [ROOT] node
		ServiceEntityConfigureMap authorizationGroupConfigureMap = new ServiceEntityConfigureMap();
		authorizationGroupConfigureMap.setParentNodeName(" ");
		authorizationGroupConfigureMap.setNodeName("ROOT");
		authorizationGroupConfigureMap.setNodeType(AuthorizationGroup.class);
		authorizationGroupConfigureMap.setTableName("AuthorizationGroup");
		authorizationGroupConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		authorizationGroupConfigureMap.addNodeFieldMap("innerProcessType",
				int.class);
		authorizationGroupConfigureMap.addNodeFieldMap("crossGroupProcessType",
				int.class);
		seConfigureMapList.add(authorizationGroupConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
