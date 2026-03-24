package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;


/**
 * Configure Proxy CLASS FOR Service Entity [NavigationSystemSetting]
 *
 * @author
 * @date Thu Aug 06 23:43:51 CST 2020
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class NavigationSystemSettingConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of NavigationSystemSetting [ROOT] node
		ServiceEntityConfigureMap navigationSystemSettingConfigureMap = new ServiceEntityConfigureMap();
		navigationSystemSettingConfigureMap.setParentNodeName(" ");
		navigationSystemSettingConfigureMap
				.setNodeName(NavigationSystemSetting.NODENAME);
		navigationSystemSettingConfigureMap
				.setNodeType(NavigationSystemSetting.class);
		navigationSystemSettingConfigureMap
				.setTableName(NavigationSystemSetting.SENAME);
		navigationSystemSettingConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		navigationSystemSettingConfigureMap.addNodeFieldMap("status",
				int.class);
		navigationSystemSettingConfigureMap.addNodeFieldMap("applicationLevel",
				int.class);
		navigationSystemSettingConfigureMap.addNodeFieldMap("systemCategory",
				int.class);
		seConfigureMapList.add(navigationSystemSettingConfigureMap);
		// Init configuration of NavigationSystemSetting
		// [NavigationGroupSetting] node
		ServiceEntityConfigureMap navigationGroupSettingConfigureMap = new ServiceEntityConfigureMap();
		navigationGroupSettingConfigureMap
				.setParentNodeName(NavigationSystemSetting.NODENAME);
		navigationGroupSettingConfigureMap
				.setNodeName(NavigationGroupSetting.NODENAME);
		navigationGroupSettingConfigureMap
				.setNodeType(NavigationGroupSetting.class);
		navigationGroupSettingConfigureMap
				.setTableName(NavigationGroupSetting.NODENAME);
		navigationGroupSettingConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		navigationGroupSettingConfigureMap.addNodeFieldMap("refDefItemUUID",
				java.lang.String.class);
		navigationGroupSettingConfigureMap.addNodeFieldMap("refSourceUUID",
				java.lang.String.class);
		navigationGroupSettingConfigureMap.addNodeFieldMap(
				"refSimAuthorObjectUUID", java.lang.String.class);
		navigationGroupSettingConfigureMap.addNodeFieldMap(
				"refAuthorActionCodeUUID", java.lang.String.class);
		navigationGroupSettingConfigureMap.addNodeFieldMap("parentElementUUID",
				java.lang.String.class);
		navigationGroupSettingConfigureMap.addNodeFieldMap("elementIcon",
				java.lang.String.class);
		navigationGroupSettingConfigureMap.addNodeFieldMap("displayIndex",
				int.class);
		seConfigureMapList.add(navigationGroupSettingConfigureMap);
		// Init configuration of NavigationSystemSetting [NavigationItemSetting]
		// node
		ServiceEntityConfigureMap navigationItemSettingConfigureMap = new ServiceEntityConfigureMap();
		navigationItemSettingConfigureMap
				.setParentNodeName(NavigationGroupSetting.NODENAME);
		navigationItemSettingConfigureMap
				.setNodeName(NavigationItemSetting.NODENAME);
		navigationItemSettingConfigureMap
				.setNodeType(NavigationItemSetting.class);
		navigationItemSettingConfigureMap
				.setTableName(NavigationItemSetting.NODENAME);
		navigationItemSettingConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		navigationItemSettingConfigureMap.addNodeFieldMap("refDefItemUUID",
				java.lang.String.class);
		navigationItemSettingConfigureMap.addNodeFieldMap("refSourceUUID",
				java.lang.String.class);
		navigationItemSettingConfigureMap.addNodeFieldMap(
				"refSimAuthorObjectUUID", java.lang.String.class);
		navigationItemSettingConfigureMap.addNodeFieldMap(
				"refAuthorActionCodeUUID", java.lang.String.class);
		navigationItemSettingConfigureMap.addNodeFieldMap("parentElementUUID",
				java.lang.String.class);
		navigationItemSettingConfigureMap.addNodeFieldMap("elementIcon",
				java.lang.String.class);
		navigationItemSettingConfigureMap.addNodeFieldMap("keywords",
				java.lang.String.class);
		navigationItemSettingConfigureMap.addNodeFieldMap("targetUrl",
				java.lang.String.class);
		navigationItemSettingConfigureMap.addNodeFieldMap("displayIndex",
				int.class);
		navigationItemSettingConfigureMap.addNodeFieldMap("layer", int.class);
		navigationItemSettingConfigureMap.addNodeFieldMap("displayFlag",
				int.class);
		seConfigureMapList.add(navigationItemSettingConfigureMap);
		// End

		// Init configuration of NavigationSystemSetting [NavigationSystemSettingActionNode] node
		ServiceEntityConfigureMap navigationSystemSettingActionNodeConfigureMap = new ServiceEntityConfigureMap();
		navigationSystemSettingActionNodeConfigureMap.setParentNodeName(NavigationSystemSetting.NODENAME);
		navigationSystemSettingActionNodeConfigureMap.setNodeName(NavigationSystemSettingActionNode.NODENAME);
		navigationSystemSettingActionNodeConfigureMap.setNodeType(NavigationSystemSettingActionNode.class);
		navigationSystemSettingActionNodeConfigureMap
				.setTableName(NavigationSystemSettingActionNode.NODENAME);
		navigationSystemSettingActionNodeConfigureMap.setFieldList(super
				.getBasicActionCodeNodeMap());
		seConfigureMapList.add(navigationSystemSettingActionNodeConfigureMap);
		super.setSeConfigMapList(seConfigureMapList);
	}

}
