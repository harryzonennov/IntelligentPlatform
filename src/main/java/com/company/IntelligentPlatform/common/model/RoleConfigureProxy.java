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
 * Configure Proxy CLASS FOR Service Entity [Role]
 * 
 * @author
 * @date Thu Jan 23 15:06:44 CST 2014
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class RoleConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of Role [ROOT] node
		ServiceEntityConfigureMap roleConfigureMap = new ServiceEntityConfigureMap();
		roleConfigureMap.setParentNodeName(" ");
		roleConfigureMap.setNodeName("ROOT");
		roleConfigureMap.setNodeType(Role.class);
		roleConfigureMap.setTableName("Role");
		roleConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		roleConfigureMap.addNodeFieldMap("enableFlag", boolean.class);
		roleConfigureMap.addNodeFieldMap("defaultPageUrl", String.class);
		seConfigureMapList.add(roleConfigureMap);
		// Init configuration of Role [RoleAuthorization] node
		ServiceEntityConfigureMap roleAuthorizationConfigureMap = new ServiceEntityConfigureMap();
		roleAuthorizationConfigureMap.setParentNodeName("ROOT");
		roleAuthorizationConfigureMap.setNodeName("RoleAuthorization");
		roleAuthorizationConfigureMap.setNodeType(RoleAuthorization.class);
		roleAuthorizationConfigureMap.setTableName("RoleAuthorization");
		roleAuthorizationConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		roleAuthorizationConfigureMap.addNodeFieldMap(
				"authorizationObjectType", int.class);
		roleAuthorizationConfigureMap.addNodeFieldMap("actionCodeArray", String.class);
		roleAuthorizationConfigureMap.addNodeFieldMap("processType", int.class);
		seConfigureMapList.add(roleAuthorizationConfigureMap);

		// Init configuration of Role [RoleAuthorization] node
		ServiceEntityConfigureMap roleSubAuthorizationConfigureMap = new ServiceEntityConfigureMap();
		roleSubAuthorizationConfigureMap.setParentNodeName(RoleAuthorization.NODENAME);
		roleSubAuthorizationConfigureMap.setNodeName(IServiceModelConstants.RoleSubAuthorization);
		roleSubAuthorizationConfigureMap.setNodeType(RoleSubAuthorization.class);
		roleSubAuthorizationConfigureMap.setTableName(IServiceModelConstants.RoleSubAuthorization);
		roleSubAuthorizationConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		roleSubAuthorizationConfigureMap.addNodeFieldMap(
				"authorizationObjectType", int.class);
		roleSubAuthorizationConfigureMap.addNodeFieldMap("actionCodeArray", String.class);
		roleSubAuthorizationConfigureMap.addNodeFieldMap("processType", int.class);
		seConfigureMapList.add(roleSubAuthorizationConfigureMap);
		// Init configuration of Role [RoleAuthorizationActionCode] node
		ServiceEntityConfigureMap roleAuthorizationActionCodeConfigureMap = new ServiceEntityConfigureMap();
		roleAuthorizationActionCodeConfigureMap
				.setParentNodeName(RoleAuthorization.NODENAME);
		roleAuthorizationActionCodeConfigureMap
				.setNodeName(RoleAuthorizationActionCode.NODENAME);
		roleAuthorizationActionCodeConfigureMap
				.setNodeType(RoleAuthorizationActionCode.class);
		roleAuthorizationActionCodeConfigureMap
				.setTableName("RoleAuthorizationActionCode");
		roleAuthorizationActionCodeConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		seConfigureMapList.add(roleAuthorizationActionCodeConfigureMap);
		// Init configuration of Role [RoleMessageCategory] node
		ServiceEntityConfigureMap roleMessageCategoryConfigureMap = new ServiceEntityConfigureMap();
		roleMessageCategoryConfigureMap.setParentNodeName("ROOT");
		roleMessageCategoryConfigureMap.setNodeName("RoleMessageCategory");
		roleMessageCategoryConfigureMap.setNodeType(RoleMessageCategory.class);
		roleMessageCategoryConfigureMap.setTableName("RoleMessageCategory");
		roleMessageCategoryConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		roleMessageCategoryConfigureMap.addNodeFieldMap("messageCategory",
				int.class);
		seConfigureMapList.add(roleMessageCategoryConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
