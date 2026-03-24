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
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [SystemConfigureCategory]
 *
 * @author
 * @date Sat Dec 22 22:25:26 CST 2018
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class SystemConfigureCategoryConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of SystemConfigureCategory [ROOT] node
		ServiceEntityConfigureMap systemConfigureCategoryConfigureMap = new ServiceEntityConfigureMap();
		systemConfigureCategoryConfigureMap.setParentNodeName(" ");
		systemConfigureCategoryConfigureMap
				.setNodeName(SystemConfigureCategory.NODENAME);
		systemConfigureCategoryConfigureMap
				.setNodeType(SystemConfigureCategory.class);
		systemConfigureCategoryConfigureMap
				.setTableName(SystemConfigureCategory.SENAME);
		systemConfigureCategoryConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		systemConfigureCategoryConfigureMap.addNodeFieldMap("scenarioMode",
				int.class);
		systemConfigureCategoryConfigureMap.addNodeFieldMap(
				"standardSystemCategory", int.class);
		seConfigureMapList.add(systemConfigureCategoryConfigureMap);
		// Init configuration of SystemConfigureCategory
		// [SystemConfigureResource] node
		ServiceEntityConfigureMap systemConfigureResourceConfigureMap = new ServiceEntityConfigureMap();
		systemConfigureResourceConfigureMap
				.setParentNodeName(SystemConfigureCategory.NODENAME);
		systemConfigureResourceConfigureMap
				.setNodeName(SystemConfigureResource.NODENAME);
		systemConfigureResourceConfigureMap
				.setNodeType(SystemConfigureResource.class);
		systemConfigureResourceConfigureMap
				.setTableName(SystemConfigureResource.NODENAME);
		systemConfigureResourceConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		systemConfigureResourceConfigureMap.addNodeFieldMap("scenarioMode",
				int.class);
		systemConfigureResourceConfigureMap.addNodeFieldMap(
				"standardSystemCategory", int.class);
		seConfigureMapList.add(systemConfigureResourceConfigureMap);
		// Init configuration of SystemConfigureCategory
		// [SystemConfigureElement] node
		ServiceEntityConfigureMap systemConfigureElementConfigureMap = new ServiceEntityConfigureMap();
		systemConfigureElementConfigureMap
				.setParentNodeName(SystemConfigureResource.NODENAME);
		systemConfigureElementConfigureMap
				.setNodeName(SystemConfigureElement.NODENAME);
		systemConfigureElementConfigureMap
				.setNodeType(SystemConfigureElement.class);
		systemConfigureElementConfigureMap
				.setTableName(SystemConfigureElement.NODENAME);
		systemConfigureElementConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		systemConfigureElementConfigureMap.addNodeFieldMap("scenarioMode",
				int.class);
		systemConfigureElementConfigureMap.addNodeFieldMap("elementType",
				int.class);
		systemConfigureElementConfigureMap.addNodeFieldMap(
				"standardSystemCategory", int.class);
		systemConfigureElementConfigureMap.addNodeFieldMap("subScenarioMode",
				int.class);
		systemConfigureElementConfigureMap.addNodeFieldMap(
				"scenarioModeSwitchProxy", java.lang.String.class);
		systemConfigureElementConfigureMap.addNodeFieldMap(
				"subScenarioModeSwitchProxy", java.lang.String.class);
		seConfigureMapList.add(systemConfigureElementConfigureMap);
		// Init configuration of SystemConfigureCategory
		// [SystemConfigureUIField] node
		ServiceEntityConfigureMap systemConfigureUIFieldConfigureMap = new ServiceEntityConfigureMap();
		systemConfigureUIFieldConfigureMap
				.setParentNodeName(SystemConfigureElement.NODENAME);
		systemConfigureUIFieldConfigureMap
				.setNodeName(SystemConfigureUIField.NODENAME);
		systemConfigureUIFieldConfigureMap
				.setNodeType(SystemConfigureUIField.class);
		systemConfigureUIFieldConfigureMap
				.setTableName(SystemConfigureUIField.NODENAME);
		systemConfigureUIFieldConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		systemConfigureUIFieldConfigureMap.addNodeFieldMap("showFieldFlag",
				boolean.class);
		systemConfigureUIFieldConfigureMap.addNodeFieldMap(
				"internationl18Content", java.lang.String.class);
		systemConfigureUIFieldConfigureMap.addNodeFieldMap("setI18NFlag",
				boolean.class);
		seConfigureMapList.add(systemConfigureUIFieldConfigureMap);
		// Init configuration of SystemConfigureCategory
		// [SystemConfigureExtensionUnion] node
		ServiceEntityConfigureMap systemConfigureExtensionUnionConfigureMap = new ServiceEntityConfigureMap();
		systemConfigureExtensionUnionConfigureMap
				.setParentNodeName(SystemConfigureResource.NODENAME);
		systemConfigureExtensionUnionConfigureMap
				.setNodeName(SystemConfigureExtensionUnion.NODENAME);
		systemConfigureExtensionUnionConfigureMap
				.setNodeType(SystemConfigureExtensionUnion.class);
		systemConfigureExtensionUnionConfigureMap
				.setTableName(SystemConfigureExtensionUnion.NODENAME);
		systemConfigureExtensionUnionConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		systemConfigureExtensionUnionConfigureMap.addNodeFieldMap(
				"configureValueName", java.lang.String.class);
		systemConfigureExtensionUnionConfigureMap.addNodeFieldMap(
				"configureValueId", java.lang.String.class);
		systemConfigureExtensionUnionConfigureMap.addNodeFieldMap(
				"configureValue", java.lang.String.class);
		systemConfigureExtensionUnionConfigureMap.addNodeFieldMap(
				"refCodeValueUUID", java.lang.String.class);
		systemConfigureExtensionUnionConfigureMap.addNodeFieldMap(
				"configureSwitchProxy", java.lang.String.class);
		seConfigureMapList.add(systemConfigureExtensionUnionConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
