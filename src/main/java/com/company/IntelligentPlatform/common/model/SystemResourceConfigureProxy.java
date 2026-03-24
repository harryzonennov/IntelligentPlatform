package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [SystemResource]
 * 
 * @author
 * @date Mon Jul 21 17:59:13 CST 2014
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class SystemResourceConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of SystemResource [ROOT] node
		ServiceEntityConfigureMap systemResourceConfigureMap = new ServiceEntityConfigureMap();
		systemResourceConfigureMap.setParentNodeName(" ");
		systemResourceConfigureMap.setNodeName("ROOT");
		systemResourceConfigureMap.setNodeType(SystemResource.class);
		systemResourceConfigureMap.setTableName("SystemResource");
		systemResourceConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		systemResourceConfigureMap.addNodeFieldMap("url",
				java.lang.String.class);
		systemResourceConfigureMap.addNodeFieldMap("absURL",
				java.lang.String.class);
		systemResourceConfigureMap.addNodeFieldMap("regSEName",
				java.lang.String.class);
		systemResourceConfigureMap.addNodeFieldMap("regNodeName",
				java.lang.String.class);
		systemResourceConfigureMap.addNodeFieldMap("viewType", int.class);
		systemResourceConfigureMap.addNodeFieldMap("uiModelClassName",
				java.lang.String.class);
		systemResourceConfigureMap.addNodeFieldMap("controllerClassName",
				java.lang.String.class);
		systemResourceConfigureMap.addNodeFieldMap("refSimAuthorObjectUUID",
				java.lang.String.class);
		seConfigureMapList.add(systemResourceConfigureMap);
		// Init configuration of SystemResource [ResFinAccountSetting] node
		ServiceEntityConfigureMap resFinAccountSettingConfigureMap = new ServiceEntityConfigureMap();
		resFinAccountSettingConfigureMap.setParentNodeName("ROOT");
		resFinAccountSettingConfigureMap.setNodeName("ResFinAccountSetting");
		resFinAccountSettingConfigureMap
				.setNodeType(ResFinAccountSetting.class);
		resFinAccountSettingConfigureMap.setTableName("ResFinAccountSetting");
		resFinAccountSettingConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		resFinAccountSettingConfigureMap.addNodeFieldMap("refFinAccObjectKey",
				int.class);
		resFinAccountSettingConfigureMap.addNodeFieldMap("refFinAccObjectKey",
				int.class);
		resFinAccountSettingConfigureMap.addNodeFieldMap("refFinAccObjectProxyClass",
				java.lang.String.class);
		resFinAccountSettingConfigureMap.addNodeFieldMap("coreSettleID",
				java.lang.String.class);
		resFinAccountSettingConfigureMap.addNodeFieldMap("settleUIModelName",
				java.lang.String.class);
		resFinAccountSettingConfigureMap.addNodeFieldMap("allAmountFieldName",
				java.lang.String.class);
		resFinAccountSettingConfigureMap.addNodeFieldMap("toSettleFieldName",
				java.lang.String.class);
		resFinAccountSettingConfigureMap.addNodeFieldMap("settledFieldName",
				java.lang.String.class);
		seConfigureMapList.add(resFinAccountSettingConfigureMap);
		// Init configuration of SystemResource [ResourceAuthorization] node
		ServiceEntityConfigureMap resourceAuthorizationConfigureMap = new ServiceEntityConfigureMap();
		resourceAuthorizationConfigureMap.setParentNodeName("ROOT");
		resourceAuthorizationConfigureMap.setNodeName("ResourceAuthorization");
		resourceAuthorizationConfigureMap
				.setNodeType(ResourceAuthorization.class);
		resourceAuthorizationConfigureMap.setTableName("ResourceAuthorization");
		resourceAuthorizationConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		seConfigureMapList.add(resourceAuthorizationConfigureMap);
		// Init configuration of SystemResource [ResFinAccountFieldSetting] node
		ServiceEntityConfigureMap resFinAccountFieldSettingConfigureMap = new ServiceEntityConfigureMap();
		resFinAccountFieldSettingConfigureMap
				.setParentNodeName("ResFinAccountSetting");
		resFinAccountFieldSettingConfigureMap
				.setNodeName("ResFinAccountFieldSetting");
		resFinAccountFieldSettingConfigureMap
				.setNodeType(ResFinAccountFieldSetting.class);
		resFinAccountFieldSettingConfigureMap
				.setTableName("ResFinAccountFieldSetting");
		resFinAccountFieldSettingConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		resFinAccountFieldSettingConfigureMap.addNodeFieldMap("fieldName",
				java.lang.String.class);
		resFinAccountFieldSettingConfigureMap.addNodeFieldMap("weightFactor",
				int.class);
		resFinAccountFieldSettingConfigureMap.addNodeFieldMap(
				"finAccProxyClassName", java.lang.String.class);
		resFinAccountFieldSettingConfigureMap.addNodeFieldMap(
				"finAccProxyMethodName", java.lang.String.class);
		seConfigureMapList.add(resFinAccountFieldSettingConfigureMap);
		// Init configuration of SystemResource [ResFinAccountProcessCode] node
		ServiceEntityConfigureMap resFinAccountProcessCodeConfigureMap = new ServiceEntityConfigureMap();
		resFinAccountProcessCodeConfigureMap
				.setParentNodeName("ResFinAccountSetting");
		resFinAccountProcessCodeConfigureMap
				.setNodeName("ResFinAccountProcessCode");
		resFinAccountProcessCodeConfigureMap
				.setNodeType(ResFinAccountProcessCode.class);
		resFinAccountProcessCodeConfigureMap
				.setTableName("ResFinAccountProcessCode");
		resFinAccountProcessCodeConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		resFinAccountProcessCodeConfigureMap.addNodeFieldMap("processCode",
				int.class);
		seConfigureMapList.add(resFinAccountProcessCodeConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
