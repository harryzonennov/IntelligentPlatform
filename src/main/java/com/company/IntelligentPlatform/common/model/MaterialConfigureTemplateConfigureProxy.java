package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;
import com.company.IntelligentPlatform.common.model.MaterialConfigureTemplate;
import com.company.IntelligentPlatform.common.model.MatConfigExtPropertySetting;
import com.company.IntelligentPlatform.common.model.MatDecisionValueSetting;
import com.company.IntelligentPlatform.common.model.MatConfigHeaderCondition;

/**
 * Configure Proxy CLASS FOR Service Entity [MaterialConfigureTemplate]
 *
 * @author
 * @date Tue Jul 02 15:41:30 CST 2019
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class MaterialConfigureTemplateConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.coreFunction");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of MaterialConfigureTemplate [ROOT] node
		ServiceEntityConfigureMap materialConfigureTemplateConfigureMap = new ServiceEntityConfigureMap();
		materialConfigureTemplateConfigureMap.setParentNodeName(" ");
		materialConfigureTemplateConfigureMap
				.setNodeName(MaterialConfigureTemplate.NODENAME);
		materialConfigureTemplateConfigureMap
				.setNodeType(MaterialConfigureTemplate.class);
		materialConfigureTemplateConfigureMap
				.setTableName(MaterialConfigureTemplate.SENAME);
		materialConfigureTemplateConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		seConfigureMapList.add(materialConfigureTemplateConfigureMap);
		// Init configuration of MaterialConfigureTemplate
		// [MatConfigExtPropertySetting] node
		ServiceEntityConfigureMap matConfigExtPropertySettingConfigureMap = new ServiceEntityConfigureMap();
		matConfigExtPropertySettingConfigureMap
				.setParentNodeName(MaterialConfigureTemplate.NODENAME);
		matConfigExtPropertySettingConfigureMap
				.setNodeName(MatConfigExtPropertySetting.NODENAME);
		matConfigExtPropertySettingConfigureMap
				.setNodeType(MatConfigExtPropertySetting.class);
		matConfigExtPropertySettingConfigureMap
				.setTableName(MatConfigExtPropertySetting.NODENAME);
		matConfigExtPropertySettingConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		matConfigExtPropertySettingConfigureMap.addNodeFieldMap("fieldType",
				java.lang.String.class);
		matConfigExtPropertySettingConfigureMap.addNodeFieldMap("qualityInspectFlag",
				int.class);
		matConfigExtPropertySettingConfigureMap.addNodeFieldMap("refUnitUUID",
				java.lang.String.class);
		matConfigExtPropertySettingConfigureMap.addNodeFieldMap("measureFlag",
				int.class);
		seConfigureMapList.add(matConfigExtPropertySettingConfigureMap);
		// Init configuration of MaterialConfigureTemplate
		// [MatDecisionValueSetting] node
		ServiceEntityConfigureMap matDecisionValueSettingConfigureMap = new ServiceEntityConfigureMap();
		matDecisionValueSettingConfigureMap
				.setParentNodeName(MaterialConfigureTemplate.NODENAME);
		matDecisionValueSettingConfigureMap
				.setNodeName(MatDecisionValueSetting.NODENAME);
		matDecisionValueSettingConfigureMap
				.setNodeType(MatDecisionValueSetting.class);
		matDecisionValueSettingConfigureMap
				.setTableName(MatDecisionValueSetting.NODENAME);
		matDecisionValueSettingConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		matDecisionValueSettingConfigureMap.addNodeFieldMap("valueUsage",
				int.class);
		matDecisionValueSettingConfigureMap.addNodeFieldMap("rawValue",
				java.lang.String.class);
		seConfigureMapList.add(matDecisionValueSettingConfigureMap);
		// Init configuration of MaterialConfigureTemplate
		// [MatConfigHeaderCondition] node
		ServiceEntityConfigureMap matConfigHeaderConditionConfigureMap = new ServiceEntityConfigureMap();
		matConfigHeaderConditionConfigureMap
				.setParentNodeName(MaterialConfigureTemplate.NODENAME);
		matConfigHeaderConditionConfigureMap
				.setNodeName(MatConfigHeaderCondition.NODENAME);
		matConfigHeaderConditionConfigureMap
				.setNodeType(MatConfigHeaderCondition.class);
		matConfigHeaderConditionConfigureMap
				.setTableName(MatConfigHeaderCondition.NODENAME);
		matConfigHeaderConditionConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		matConfigHeaderConditionConfigureMap.addNodeFieldMap("refNodeInstId",
				java.lang.String.class);
		matConfigHeaderConditionConfigureMap.addNodeFieldMap("fieldName",
				java.lang.String.class);
		matConfigHeaderConditionConfigureMap.addNodeFieldMap("fieldValue",
				java.lang.String.class);
		matConfigHeaderConditionConfigureMap.addNodeFieldMap("logicOperator",
				int.class);
		seConfigureMapList.add(matConfigHeaderConditionConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
