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
 * Configure Proxy CLASS FOR Service Entity [SystemExecutorSetting]
 *
 * @author
 * @date Thu Jan 03 10:44:07 CST 2019
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class SystemExecutorSettingConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of SystemExecutorSetting [ROOT] node
		ServiceEntityConfigureMap systemExecutorSettingConfigureMap = new ServiceEntityConfigureMap();
		systemExecutorSettingConfigureMap.setParentNodeName(" ");
		systemExecutorSettingConfigureMap
				.setNodeName(SystemExecutorSetting.NODENAME);
		systemExecutorSettingConfigureMap
				.setNodeType(SystemExecutorSetting.class);
		systemExecutorSettingConfigureMap
				.setTableName(SystemExecutorSetting.SENAME);
		systemExecutorSettingConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		systemExecutorSettingConfigureMap.addNodeFieldMap("executionType",
				int.class);
		systemExecutorSettingConfigureMap.addNodeFieldMap("proxyName",
				java.lang.String.class);
		systemExecutorSettingConfigureMap.addNodeFieldMap(
				"refPreExecuteSettingUUID", java.lang.String.class);
		systemExecutorSettingConfigureMap.addNodeFieldMap(
				"refExecutorId", java.lang.String.class);
		systemExecutorSettingConfigureMap.addNodeFieldMap("executeBatchNumber",
				int.class);
		systemExecutorSettingConfigureMap.addNodeFieldMap("refAOUUID",
				java.lang.String.class);
		systemExecutorSettingConfigureMap.addNodeFieldMap("refActionCode",
				java.lang.String.class);
		systemExecutorSettingConfigureMap.addNodeFieldMap("requestContent",
				java.lang.String.class);
		seConfigureMapList.add(systemExecutorSettingConfigureMap);
		// Init configuration of SystemExecutorSetting [SystemExecutorLog] node
		ServiceEntityConfigureMap systemExecutorLogConfigureMap = new ServiceEntityConfigureMap();
		systemExecutorLogConfigureMap
				.setParentNodeName(SystemExecutorSetting.NODENAME);
		systemExecutorLogConfigureMap.setNodeName(SystemExecutorLog.NODENAME);
		systemExecutorLogConfigureMap.setNodeType(SystemExecutorLog.class);
		systemExecutorLogConfigureMap.setTableName(SystemExecutorLog.NODENAME);
		systemExecutorLogConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		systemExecutorLogConfigureMap.addNodeFieldMap("result", int.class);
		seConfigureMapList.add(systemExecutorLogConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
