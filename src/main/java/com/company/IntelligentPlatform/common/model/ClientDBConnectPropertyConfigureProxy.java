package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [ClientDBConnectProperty]
 * 
 * @author
 * @date Sun Nov 25 20:47:02 CST 2012
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class ClientDBConnectPropertyConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of ClientDBConnectProperty [ROOT] node
		ServiceEntityConfigureMap clientDBConnectPropertyConfigureMap = new ServiceEntityConfigureMap();
		clientDBConnectPropertyConfigureMap.setParentNodeName(" ");
		clientDBConnectPropertyConfigureMap.setNodeName("ROOT");
		clientDBConnectPropertyConfigureMap
				.setNodeType(ClientDBConnectProperty.class);
		clientDBConnectPropertyConfigureMap
				.setTableName("ClientDBConnectProperty");
		clientDBConnectPropertyConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		clientDBConnectPropertyConfigureMap.addNodeFieldMap("clientID",
				java.lang.String.class);
		clientDBConnectPropertyConfigureMap.addNodeFieldMap("dbUserName",
				java.lang.String.class);
		clientDBConnectPropertyConfigureMap.addNodeFieldMap("dbPassword",
				java.lang.String.class);
		clientDBConnectPropertyConfigureMap.addNodeFieldMap("schemaID",
				java.lang.String.class);
		seConfigureMapList.add(clientDBConnectPropertyConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
