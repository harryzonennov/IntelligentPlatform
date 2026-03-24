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

/**
 * Configure Proxy CLASS FOR Service Entity [MessageTemplate]
 *
 * @author
 * @date Tue Jul 30 17:12:23 CST 2019
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class MessageTemplateConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of MessageTemplate [ROOT] node
		ServiceEntityConfigureMap messageTemplateConfigureMap = new ServiceEntityConfigureMap();
		messageTemplateConfigureMap.setParentNodeName(" ");
		messageTemplateConfigureMap.setNodeName(MessageTemplate.NODENAME);
		messageTemplateConfigureMap.setNodeType(MessageTemplate.class);
		messageTemplateConfigureMap.setTableName(MessageTemplate.SENAME);
		messageTemplateConfigureMap
				.setFieldList(super.getBasicSENodeFieldMap());
		messageTemplateConfigureMap.addNodeFieldMap("searchModelClass",
				java.lang.String.class);
		messageTemplateConfigureMap.addNodeFieldMap("searchModelName",
				java.lang.String.class);
		messageTemplateConfigureMap.addNodeFieldMap("searchDataUrl",
				java.lang.String.class);
		messageTemplateConfigureMap.addNodeFieldMap("handlerClass",
				java.lang.String.class);
		messageTemplateConfigureMap.addNodeFieldMap("processIndex", int.class);
		messageTemplateConfigureMap.addNodeFieldMap("referenceModelName",
				java.lang.String.class);
		messageTemplateConfigureMap.addNodeFieldMap("navigationSourceId",
				java.lang.String.class);
		messageTemplateConfigureMap.addNodeFieldMap("searchProxyName",
				java.lang.String.class);
		messageTemplateConfigureMap.addNodeFieldMap("refProxyConfigUUID",
				java.lang.String.class);
		messageTemplateConfigureMap.addNodeFieldMap("messageTitle",
				java.lang.String.class);
		messageTemplateConfigureMap.addNodeFieldMap("messageContent",
				java.lang.String.class);
		messageTemplateConfigureMap.addNodeFieldMap("actionCodeArray",
				java.lang.String.class);
		seConfigureMapList.add(messageTemplateConfigureMap);
		// Init configuration of MessageTemplate [MessageTempSearchCondition]
		// node
		ServiceEntityConfigureMap messageTempSearchConditionConfigureMap = new ServiceEntityConfigureMap();
		messageTempSearchConditionConfigureMap
				.setParentNodeName(MessageTemplate.NODENAME);
		messageTempSearchConditionConfigureMap
				.setNodeName(MessageTempSearchCondition.NODENAME);
		messageTempSearchConditionConfigureMap
				.setNodeType(MessageTempSearchCondition.class);
		messageTempSearchConditionConfigureMap
				.setTableName(MessageTempSearchCondition.NODENAME);
		messageTempSearchConditionConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		messageTempSearchConditionConfigureMap.addNodeFieldMap("fieldName",
				java.lang.String.class);
		messageTempSearchConditionConfigureMap.addNodeFieldMap("fieldValue",
				java.lang.String.class);
		messageTempSearchConditionConfigureMap.addNodeFieldMap("dataSourceProviderId",
				java.lang.String.class);
		messageTempSearchConditionConfigureMap.addNodeFieldMap("dataOffsetDirection",
				int.class);
		messageTempSearchConditionConfigureMap.addNodeFieldMap("dataOffsetValue",
				double.class);
		messageTempSearchConditionConfigureMap.addNodeFieldMap("dataOffsetUnit",
				java.lang.String.class);
		messageTempSearchConditionConfigureMap.addNodeFieldMap(
				"searchOperator", int.class);
		messageTempSearchConditionConfigureMap.addNodeFieldMap("logicOperator",
				int.class);
		seConfigureMapList.add(messageTempSearchConditionConfigureMap);
		// Init configuration of MessageTemplate [MessageTempPrioritySetting]
		// node
		ServiceEntityConfigureMap messageTempPrioritySettingConfigureMap = new ServiceEntityConfigureMap();
		messageTempPrioritySettingConfigureMap
				.setParentNodeName(MessageTemplate.NODENAME);
		messageTempPrioritySettingConfigureMap
				.setNodeName(MessageTempPrioritySetting.NODENAME);
		messageTempPrioritySettingConfigureMap
				.setNodeType(MessageTempPrioritySetting.class);
		messageTempPrioritySettingConfigureMap
				.setTableName(MessageTempPrioritySetting.NODENAME);
		messageTempPrioritySettingConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		messageTempPrioritySettingConfigureMap.addNodeFieldMap("priorityCode",
				int.class);
		messageTempPrioritySettingConfigureMap.addNodeFieldMap("messageLevelCode",
				int.class);
		messageTempPrioritySettingConfigureMap.addNodeFieldMap("startValue",
				double.class);
		messageTempPrioritySettingConfigureMap.addNodeFieldMap("endValue",
				double.class);
		messageTempPrioritySettingConfigureMap.addNodeFieldMap("colorStyle",
				java.lang.String.class);
		messageTempPrioritySettingConfigureMap.addNodeFieldMap("iconStyle",
				java.lang.String.class);
		messageTempPrioritySettingConfigureMap.addNodeFieldMap("actionCode",
				java.lang.String.class);
		messageTempPrioritySettingConfigureMap.addNodeFieldMap("fieldName",
				java.lang.String.class);
		messageTempPrioritySettingConfigureMap.addNodeFieldMap("fieldValue",
				java.lang.String.class);
		messageTempPrioritySettingConfigureMap.addNodeFieldMap(
				"refPrioritySettingUUID", java.lang.String.class);
		messageTempPrioritySettingConfigureMap.addNodeFieldMap("dataSourceProviderId",
				java.lang.String.class);
		messageTempPrioritySettingConfigureMap.addNodeFieldMap("dataOffsetDirection",
				int.class);
		messageTempPrioritySettingConfigureMap.addNodeFieldMap("dataOffsetValue",
				double.class);
		messageTempPrioritySettingConfigureMap.addNodeFieldMap("dataOffsetUnit",
				java.lang.String.class);
		messageTempPrioritySettingConfigureMap.addNodeFieldMap("messageTitle",
				java.lang.String.class);
		messageTempPrioritySettingConfigureMap.addNodeFieldMap("messageContent",
				java.lang.String.class);
		seConfigureMapList.add(messageTempPrioritySettingConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
