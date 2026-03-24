package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [OrganizationFunction]
 *
 * @author
 * @date Thu Sep 01 16:55:40 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class OrganizationFunctionConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of OrganizationFunction [ROOT] node
		ServiceEntityConfigureMap organizationFunctionConfigureMap = new ServiceEntityConfigureMap();
		organizationFunctionConfigureMap.setParentNodeName(" ");
		organizationFunctionConfigureMap
				.setNodeName(OrganizationFunction.NODENAME);
		organizationFunctionConfigureMap
				.setNodeType(OrganizationFunction.class);
		organizationFunctionConfigureMap
				.setTableName(OrganizationFunction.SENAME);
		organizationFunctionConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		organizationFunctionConfigureMap.addNodeFieldMap("switchFlag", int.class);
		seConfigureMapList.add(organizationFunctionConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
