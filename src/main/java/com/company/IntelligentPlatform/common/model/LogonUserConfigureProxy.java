package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [LogonUser]
 * 
 * @author
 * @date Mon Jul 15 15:47:32 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class LogonUserConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of LogonUser [ROOT] node
		ServiceEntityConfigureMap logonUserConfigureMap = new ServiceEntityConfigureMap();
		logonUserConfigureMap.setParentNodeName(" ");
		logonUserConfigureMap.setNodeName("ROOT");
		logonUserConfigureMap.setNodeType(LogonUser.class);
		logonUserConfigureMap.setTableName("LogonUser");
		logonUserConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		logonUserConfigureMap.addNodeFieldMap("password",
				java.lang.String.class);
		logonUserConfigureMap.addNodeFieldMap("lockUserFlag", int.class);
		logonUserConfigureMap.addNodeFieldMap("tryFailedTimes", int.class);
		logonUserConfigureMap
				.addNodeFieldMap("passwordInitFlag", int.class);
		logonUserConfigureMap
				.addNodeFieldMap("logonTime", java.util.Date.class);
		logonUserConfigureMap.addNodeFieldMap("userType", int.class);
		logonUserConfigureMap.addNodeFieldMap("initPassword",
				java.lang.String.class);
		logonUserConfigureMap.addNodeFieldMap("status", int.class);
		seConfigureMapList.add(logonUserConfigureMap);
		// Init configuration of LogonUser [LogonUserOrgReference] node
		ServiceEntityConfigureMap logonUserOrgReferenceConfigureMap = new ServiceEntityConfigureMap();
		logonUserOrgReferenceConfigureMap.setParentNodeName("ROOT");
		logonUserOrgReferenceConfigureMap.setNodeName("LogonUserOrgReference");
		logonUserOrgReferenceConfigureMap
				.setNodeType(LogonUserOrgReference.class);
		logonUserOrgReferenceConfigureMap.setTableName("LogonUserOrgReference");
		logonUserOrgReferenceConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		logonUserOrgReferenceConfigureMap.addNodeFieldMap("workRole", int.class);
		seConfigureMapList.add(logonUserOrgReferenceConfigureMap);
		// Init configuration of LogonUser [UserRole] node
		ServiceEntityConfigureMap userRoleConfigureMap = new ServiceEntityConfigureMap();
		userRoleConfigureMap.setParentNodeName("ROOT");
		userRoleConfigureMap.setNodeName("UserRole");
		userRoleConfigureMap.setNodeType(UserRole.class);
		userRoleConfigureMap.setTableName("UserRole");
		userRoleConfigureMap.setFieldList(super.getBasicReferenceFieldMap());
		userRoleConfigureMap.addNodeFieldMap("workRole", int.class);
		userRoleConfigureMap.addNodeFieldMap("passwordNeedFlag", int.class);
		seConfigureMapList.add(userRoleConfigureMap);
		// Init configuration of LogonUser [LogonEquipmentReference] node
		ServiceEntityConfigureMap logonEquipmentReferenceConfigureMap = new ServiceEntityConfigureMap();
		logonEquipmentReferenceConfigureMap.setParentNodeName("ROOT");
		logonEquipmentReferenceConfigureMap
				.setNodeName("LogonEquipmentReference");
		logonEquipmentReferenceConfigureMap
				.setNodeType(LogonEquipmentReference.class);
		logonEquipmentReferenceConfigureMap
				.setTableName("LogonEquipmentReference");
		logonEquipmentReferenceConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		seConfigureMapList.add(logonEquipmentReferenceConfigureMap);

		// Init configuration of Material [LogonUserActionNode] node
		ServiceEntityConfigureMap logonUserActionNodeConfigureMap = new ServiceEntityConfigureMap();
		logonUserActionNodeConfigureMap.setParentNodeName(LogonUser.NODENAME);
		logonUserActionNodeConfigureMap.setNodeName(LogonUserActionNode.NODENAME);
		logonUserActionNodeConfigureMap.setNodeType(LogonUserActionNode.class);
		logonUserActionNodeConfigureMap
				.setTableName(LogonUserActionNode.NODENAME);
		logonUserActionNodeConfigureMap.setFieldList(super
				.getBasicActionCodeNodeMap());
		seConfigureMapList.add(logonUserActionNodeConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
