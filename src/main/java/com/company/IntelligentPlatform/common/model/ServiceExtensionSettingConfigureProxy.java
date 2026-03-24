package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [ServiceExtensionSetting]
 *
 * @author
 * @date Thu Apr 02 22:50:32 CST 2020
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class ServiceExtensionSettingConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of ServiceExtensionSetting [ROOT] node
		ServiceEntityConfigureMap serviceExtensionSettingConfigureMap = new ServiceEntityConfigureMap();
		serviceExtensionSettingConfigureMap.setParentNodeName(" ");
		serviceExtensionSettingConfigureMap
				.setNodeName(ServiceExtensionSetting.NODENAME);
		serviceExtensionSettingConfigureMap
				.setNodeType(ServiceExtensionSetting.class);
		serviceExtensionSettingConfigureMap
				.setTableName(ServiceExtensionSetting.SENAME);
		serviceExtensionSettingConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		serviceExtensionSettingConfigureMap.addNodeFieldMap("refSEName",
				java.lang.String.class);
		serviceExtensionSettingConfigureMap.addNodeFieldMap("refNodeName",
				java.lang.String.class);
		serviceExtensionSettingConfigureMap.addNodeFieldMap("modelId",
				java.lang.String.class);
		serviceExtensionSettingConfigureMap.addNodeFieldMap("switchFlag",
				int.class);
		seConfigureMapList.add(serviceExtensionSettingConfigureMap);
		// Init configuration of ServiceExtensionSetting
		// [ServiceExtendFieldSetting] node
		ServiceEntityConfigureMap serviceExtendFieldSettingConfigureMap = new ServiceEntityConfigureMap();
		serviceExtendFieldSettingConfigureMap
				.setParentNodeName(ServiceExtensionSetting.NODENAME);
		serviceExtendFieldSettingConfigureMap
				.setNodeName(ServiceExtendFieldSetting.NODENAME);
		serviceExtendFieldSettingConfigureMap
				.setNodeType(ServiceExtendFieldSetting.class);
		serviceExtendFieldSettingConfigureMap
				.setTableName(ServiceExtendFieldSetting.NODENAME);
		serviceExtendFieldSettingConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap("fieldName",
				java.lang.String.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap("fieldType",
				java.lang.String.class);
		serviceExtensionSettingConfigureMap.addNodeFieldMap("fieldLabel",
				java.lang.String.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap("fieldMaxLength",
				int.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap("storeModelName",
				java.lang.String.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap("searchFlag",
				boolean.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap("hideInEditor",
				boolean.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap("hideInList",
				boolean.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap(
				"hideInSearchPanel", boolean.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap(
				"extendedFieldFlag", boolean.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap("customI18nFlag",
				boolean.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap(
				"customI18nSwitch", int.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap("systemCategory",
				int.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap("activeSwitch",
				int.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap("initialValue",
				java.lang.String.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap(
				"inputControlType", java.lang.String.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap("getMetaDataUrl",
				java.lang.String.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap(
				"refMetaCodeUUID", java.lang.String.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap("visibleSwitch",
				int.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap(
				"visibleExpression", java.lang.String.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap(
				"visibleActionCode", java.lang.String.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap("enableSwitch",
				int.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap(
				"enableExpression", java.lang.String.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap(
				"enableActionCode", java.lang.String.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap("defaultValue",
				java.lang.String.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap(
				"defaultValueExpression", java.lang.String.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap(
				"formatSelectMethod", java.lang.String.class);
		serviceExtendFieldSettingConfigureMap.addNodeFieldMap(
				"initialPrevModelName", java.lang.String.class);
		seConfigureMapList.add(serviceExtendFieldSettingConfigureMap);
		// Init configuration of ServiceExtensionSetting
		// [SerExtendPageMetadata] node
		ServiceEntityConfigureMap serExtendPageMetadataConfigureMap = new ServiceEntityConfigureMap();
		serExtendPageMetadataConfigureMap
				.setParentNodeName(SerExtendPageSetting.NODENAME);
		serExtendPageMetadataConfigureMap
				.setNodeName(SerExtendPageMetadata.NODENAME);
		serExtendPageMetadataConfigureMap
				.setNodeType(SerExtendPageMetadata.class);
		serExtendPageMetadataConfigureMap
				.setTableName(SerExtendPageMetadata.NODENAME);
		serExtendPageMetadataConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		serExtendPageMetadataConfigureMap.addNodeFieldMap("pageMeta",
				java.lang.String.class);
		serExtendPageMetadataConfigureMap.addNodeFieldMap("itemStatus",
				int.class);
		serExtendPageMetadataConfigureMap.addNodeFieldMap("systemCategory",
				int.class);
		seConfigureMapList.add(serExtendPageMetadataConfigureMap);


		// Init configuration of Material [SerExtendPageSettingActionNode] node
		ServiceEntityConfigureMap serExtendPageSettingActionNodeConfigureMap = new ServiceEntityConfigureMap();
		serExtendPageSettingActionNodeConfigureMap.setParentNodeName(SerExtendPageMetadata.NODENAME);
		serExtendPageSettingActionNodeConfigureMap.setNodeName(SerExtendPageSettingActionNode.NODENAME);
		serExtendPageSettingActionNodeConfigureMap.setNodeType(SerExtendPageSettingActionNode.class);
		serExtendPageSettingActionNodeConfigureMap
				.setTableName(SerExtendPageSettingActionNode.NODENAME);
		serExtendPageSettingActionNodeConfigureMap.setFieldList(super
				.getBasicActionCodeNodeMap());
		seConfigureMapList.add(serExtendPageSettingActionNodeConfigureMap);

		// [SerExtendPageMetadata] node
		ServiceEntityConfigureMap serExtendPageI18nConfigureMap = new ServiceEntityConfigureMap();
		serExtendPageI18nConfigureMap
				.setParentNodeName(ServiceExtensionSetting.NODENAME);
		serExtendPageI18nConfigureMap
				.setNodeName(SerExtendPageI18n.NODENAME);
		serExtendPageI18nConfigureMap
				.setNodeType(SerExtendPageI18n.class);
		serExtendPageI18nConfigureMap
				.setTableName(SerExtendPageI18n.NODENAME);
		serExtendPageI18nConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		serExtendPageI18nConfigureMap.addNodeFieldMap("lanCode",
				java.lang.String.class);
		serExtendPageI18nConfigureMap.addNodeFieldMap("propertyContent",
				java.lang.String.class);
		seConfigureMapList.add(serExtendPageI18nConfigureMap);

		// [ServiceExtendFieldI18nSetting] node
		ServiceEntityConfigureMap serviceExtendFieldI18nSettingConfigureMap = new ServiceEntityConfigureMap();
		serviceExtendFieldI18nSettingConfigureMap
				.setParentNodeName(ServiceExtendFieldSetting.NODENAME);
		serviceExtendFieldI18nSettingConfigureMap
				.setNodeName(ServiceExtendFieldI18nSetting.NODENAME);
		serviceExtendFieldI18nSettingConfigureMap
				.setNodeType(ServiceExtendFieldI18nSetting.class);
		serviceExtendFieldI18nSettingConfigureMap
				.setTableName(ServiceExtendFieldI18nSetting.NODENAME);
		serviceExtendFieldI18nSettingConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		serviceExtendFieldI18nSettingConfigureMap.addNodeFieldMap("lanKey",
				java.lang.String.class);
		serviceExtendFieldI18nSettingConfigureMap.addNodeFieldMap("labelValue",
				java.lang.String.class);
		serviceExtendFieldI18nSettingConfigureMap.addNodeFieldMap("activeFlag",
				boolean.class);
		seConfigureMapList.add(serviceExtendFieldI18nSettingConfigureMap);
		// Init configuration of ServiceExtensionSetting [SerExtendStr100Field]
		// node
		ServiceEntityConfigureMap serExtendStr100FieldConfigureMap = new ServiceEntityConfigureMap();
		serExtendStr100FieldConfigureMap
				.setParentNodeName(ServiceExtendFieldSetting.NODENAME);
		serExtendStr100FieldConfigureMap
				.setNodeName(SerExtendStr100Field.NODENAME);
		serExtendStr100FieldConfigureMap
				.setNodeType(SerExtendStr100Field.class);
		serExtendStr100FieldConfigureMap
				.setTableName(SerExtendStr100Field.NODENAME);
		serExtendStr100FieldConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		serExtendStr100FieldConfigureMap.addNodeFieldMap("fieldName",
				java.lang.String.class);
		serExtendStr100FieldConfigureMap.addNodeFieldMap("fieldValue",
				java.lang.String.class);
		seConfigureMapList.add(serExtendStr100FieldConfigureMap);
		// Init configuration of ServiceExtensionSetting [SerExtendStr800Field]
		// node
		ServiceEntityConfigureMap serExtendStr800FieldConfigureMap = new ServiceEntityConfigureMap();
		serExtendStr800FieldConfigureMap
				.setParentNodeName(ServiceExtendFieldSetting.NODENAME);
		serExtendStr800FieldConfigureMap
				.setNodeName(SerExtendStr800Field.NODENAME);
		serExtendStr800FieldConfigureMap
				.setNodeType(SerExtendStr800Field.class);
		serExtendStr800FieldConfigureMap
				.setTableName(SerExtendStr800Field.NODENAME);
		serExtendStr800FieldConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		serExtendStr800FieldConfigureMap.addNodeFieldMap("fieldName",
				java.lang.String.class);
		serExtendStr800FieldConfigureMap.addNodeFieldMap("fieldValue",
				java.lang.String.class);
		seConfigureMapList.add(serExtendStr800FieldConfigureMap);
		// Init configuration of ServiceExtensionSetting [SerExtendStr3000Field]
		// node
		ServiceEntityConfigureMap serExtendStr3000FieldConfigureMap = new ServiceEntityConfigureMap();
		serExtendStr3000FieldConfigureMap
				.setParentNodeName(ServiceExtendFieldSetting.NODENAME);
		serExtendStr3000FieldConfigureMap
				.setNodeName(SerExtendStr3000Field.NODENAME);
		serExtendStr3000FieldConfigureMap
				.setNodeType(SerExtendStr3000Field.class);
		serExtendStr3000FieldConfigureMap
				.setTableName(SerExtendStr3000Field.NODENAME);
		serExtendStr3000FieldConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		serExtendStr3000FieldConfigureMap.addNodeFieldMap("fieldName",
				java.lang.String.class);
		serExtendStr3000FieldConfigureMap.addNodeFieldMap("fieldValue",
				java.lang.String.class);
		seConfigureMapList.add(serExtendStr3000FieldConfigureMap);
		// Init configuration of ServiceExtensionSetting [SerExtendDoubleField]
		// node
		ServiceEntityConfigureMap serExtendDoubleFieldConfigureMap = new ServiceEntityConfigureMap();
		serExtendDoubleFieldConfigureMap
				.setParentNodeName(ServiceExtendFieldSetting.NODENAME);
		serExtendDoubleFieldConfigureMap
				.setNodeName(SerExtendDoubleField.NODENAME);
		serExtendDoubleFieldConfigureMap
				.setNodeType(SerExtendDoubleField.class);
		serExtendDoubleFieldConfigureMap
				.setTableName(SerExtendDoubleField.NODENAME);
		serExtendDoubleFieldConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		serExtendDoubleFieldConfigureMap.addNodeFieldMap("fieldName",
				java.lang.String.class);
		serExtendDoubleFieldConfigureMap.addNodeFieldMap("fieldValue",
				double.class);
		seConfigureMapList.add(serExtendDoubleFieldConfigureMap);
		// Init configuration of ServiceExtensionSetting [SerExtendIntField]
		// node
		ServiceEntityConfigureMap serExtendIntFieldConfigureMap = new ServiceEntityConfigureMap();
		serExtendIntFieldConfigureMap
				.setParentNodeName(ServiceExtendFieldSetting.NODENAME);
		serExtendIntFieldConfigureMap.setNodeName(SerExtendIntField.NODENAME);
		serExtendIntFieldConfigureMap.setNodeType(SerExtendIntField.class);
		serExtendIntFieldConfigureMap.setTableName(SerExtendIntField.NODENAME);
		serExtendIntFieldConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		serExtendIntFieldConfigureMap.addNodeFieldMap("fieldName",
				java.lang.String.class);
		serExtendIntFieldConfigureMap.addNodeFieldMap("fieldValue", int.class);
		seConfigureMapList.add(serExtendIntFieldConfigureMap);
		// Init configuration of ServiceExtensionSetting [SerExtendUIControlSet]
		// node
		ServiceEntityConfigureMap serExtendUIControlSetConfigureMap = new ServiceEntityConfigureMap();
		serExtendUIControlSetConfigureMap
				.setParentNodeName(SerExtendPageSection.NODENAME);
		serExtendUIControlSetConfigureMap
				.setNodeName(SerExtendUIControlSet.NODENAME);
		serExtendUIControlSetConfigureMap
				.setNodeType(SerExtendUIControlSet.class);
		serExtendUIControlSetConfigureMap
				.setTableName(SerExtendUIControlSet.NODENAME);
		serExtendUIControlSetConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		serExtendUIControlSetConfigureMap.addNodeFieldMap("sectionId",
				java.lang.String.class);
		serExtendUIControlSetConfigureMap.addNodeFieldMap("screenId",
				java.lang.String.class);
		serExtendUIControlSetConfigureMap.addNodeFieldMap("displayIndex",
				int.class);
		serExtendUIControlSetConfigureMap.addNodeFieldMap("inputControlType",
				java.lang.String.class);
		serExtendUIControlSetConfigureMap.addNodeFieldMap("controlDomId",
				java.lang.String.class);
		serExtendUIControlSetConfigureMap.addNodeFieldMap("getMetaDataUrl",
				java.lang.String.class);
		serExtendUIControlSetConfigureMap.addNodeFieldMap("refMetaCodeUUID",
				java.lang.String.class);
		serExtendUIControlSetConfigureMap.addNodeFieldMap("visibleSwitch",
				int.class);
		serExtendUIControlSetConfigureMap.addNodeFieldMap("visibleExpression",
				java.lang.String.class);
		serExtendUIControlSetConfigureMap.addNodeFieldMap("visibleActionCode",
				java.lang.String.class);
		serExtendUIControlSetConfigureMap.addNodeFieldMap("enableSwitch",
				int.class);
		serExtendUIControlSetConfigureMap.addNodeFieldMap("enableExpression",
				java.lang.String.class);
		serExtendUIControlSetConfigureMap.addNodeFieldMap("enableActionCode",
				java.lang.String.class);
		serExtendUIControlSetConfigureMap.addNodeFieldMap("refFieldUUID",
				java.lang.String.class);
		serExtendUIControlSetConfigureMap.addNodeFieldMap("rowNumber",
				int.class);
		serExtendUIControlSetConfigureMap.addNodeFieldMap("defaultValue",
				java.lang.String.class);
		serExtendUIControlSetConfigureMap.addNodeFieldMap(
				"defaultValueExpression", java.lang.String.class);
		serExtendUIControlSetConfigureMap.addNodeFieldMap("formatSelectMethod",
				java.lang.String.class);
		seConfigureMapList.add(serExtendUIControlSetConfigureMap);
		// Init configuration of ServiceExtensionSetting [SerExtendPageSetting]
		// node
		ServiceEntityConfigureMap serExtendPageSettingConfigureMap = new ServiceEntityConfigureMap();
		serExtendPageSettingConfigureMap
				.setParentNodeName(ServiceExtensionSetting.NODENAME);
		serExtendPageSettingConfigureMap
				.setNodeName(SerExtendPageSetting.NODENAME);
		serExtendPageSettingConfigureMap
				.setNodeType(SerExtendPageSetting.class);
		serExtendPageSettingConfigureMap
				.setTableName(SerExtendPageSetting.NODENAME);
		serExtendPageSettingConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		serExtendPageSettingConfigureMap.addNodeFieldMap("navigationId",
				java.lang.String.class);
		serExtendPageSettingConfigureMap.addNodeFieldMap("tabArray",
				java.lang.String.class);
		serExtendPageSettingConfigureMap.addNodeFieldMap("accessResourceId",
				java.lang.String.class);
		serExtendPageSettingConfigureMap.addNodeFieldMap("accessPageUrl",
				java.lang.String.class);
		serExtendPageSettingConfigureMap.addNodeFieldMap("pageCategory",
				int.class);
		serExtendPageSettingConfigureMap.addNodeFieldMap("status",
				int.class);
		serExtendPageSettingConfigureMap.addNodeFieldMap("activeSwitch",
				int.class);
		serExtendPageSettingConfigureMap.addNodeFieldMap("systemCategory",
				int.class);
		seConfigureMapList.add(serExtendPageSettingConfigureMap);
		// Init configuration of ServiceExtensionSetting [SerExtendPageSection]
		// node
		ServiceEntityConfigureMap serExtendPageSectionConfigureMap = new ServiceEntityConfigureMap();
		serExtendPageSectionConfigureMap
				.setParentNodeName(ServiceExtensionSetting.NODENAME);
		serExtendPageSectionConfigureMap
				.setNodeName(SerExtendPageSection.NODENAME);
		serExtendPageSectionConfigureMap
				.setNodeType(SerExtendPageSection.class);
		serExtendPageSectionConfigureMap
				.setTableName(SerExtendPageSection.NODENAME);
		serExtendPageSectionConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		serExtendPageSectionConfigureMap.addNodeFieldMap("tabId",
				java.lang.String.class);
		serExtendPageSectionConfigureMap.addNodeFieldMap("processIndex",
				int.class);
		serExtendPageSectionConfigureMap.addNodeFieldMap("sectionCategory",
				int.class);
		serExtendPageSectionConfigureMap.addNodeFieldMap("visibleExpression",
				java.lang.String.class);
		serExtendPageSectionConfigureMap.addNodeFieldMap("visibleSwitch",
				int.class);
		serExtendPageSectionConfigureMap.addNodeFieldMap("visibleActionCode",
				java.lang.String.class);
		serExtendPageSectionConfigureMap.addNodeFieldMap("refDomId",
				java.lang.String.class);
		seConfigureMapList.add(serExtendPageSectionConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
