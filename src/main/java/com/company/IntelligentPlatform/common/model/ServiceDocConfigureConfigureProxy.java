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
 * Configure Proxy CLASS FOR Service Entity [ServiceDocConfigure]
 *
 * @author
 * @date Sun May 08 18:31:48 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class ServiceDocConfigureConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of ServiceDocConfigure [ROOT] node
		ServiceEntityConfigureMap serviceDocConfigureConfigureMap = new ServiceEntityConfigureMap();
		serviceDocConfigureConfigureMap.setParentNodeName(" ");
		serviceDocConfigureConfigureMap
				.setNodeName(ServiceDocConfigure.NODENAME);
		serviceDocConfigureConfigureMap.setNodeType(ServiceDocConfigure.class);
		serviceDocConfigureConfigureMap
				.setTableName(ServiceDocConfigure.SENAME);
		serviceDocConfigureConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		serviceDocConfigureConfigureMap.addNodeFieldMap("consumerUnionUUID",
				java.lang.String.class);
		serviceDocConfigureConfigureMap.addNodeFieldMap("resourceDocType",
				int.class);
		serviceDocConfigureConfigureMap.addNodeFieldMap("resourceID",
				java.lang.String.class);
		serviceDocConfigureConfigureMap.addNodeFieldMap("inputUnionUUID",
				java.lang.String.class);
		serviceDocConfigureConfigureMap.addNodeFieldMap("refSearchProxyUUID",
				java.lang.String.class);
		serviceDocConfigureConfigureMap
				.addNodeFieldMap("switchFlag", int.class);
		seConfigureMapList.add(serviceDocConfigureConfigureMap);
		// Init configuration of ServiceDocConfigure [ServiceDocConfigurePara]
		// node
		ServiceEntityConfigureMap serviceDocConfigureParaConfigureMap = new ServiceEntityConfigureMap();
		serviceDocConfigureParaConfigureMap
				.setParentNodeName(ServiceDocConfigure.NODENAME);
		serviceDocConfigureParaConfigureMap
				.setNodeName(ServiceDocConfigurePara.NODENAME);
		serviceDocConfigureParaConfigureMap
				.setNodeType(ServiceDocConfigurePara.class);
		serviceDocConfigureParaConfigureMap
				.setTableName(ServiceDocConfigurePara.NODENAME);
		serviceDocConfigureParaConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		serviceDocConfigureParaConfigureMap.addNodeFieldMap(
				"consumerFieldName", java.lang.String.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap(
				"resourceFieldName", java.lang.String.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap(
				"resourceFieldLabel", java.lang.String.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap("refGroupUUID",
				java.lang.String.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap(
				"consumerValueMode", int.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap("switchFlag",
				int.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap("fixValue",
				java.lang.String.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap("fixValueOperator",
				int.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap("fixValueDouble",
				double.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap("fixValueInt",
				int.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap("fixValueDate",
				java.util.Date.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap("fixValueHigh",
				java.lang.String.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap(
				"fixValueDoubleHigh", double.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap("fixValueIntHigh",
				int.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap("fixValueDateHigh",
				java.util.Date.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap("paraDirection",
				int.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap("logicOperator",
				int.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap(
				"dataOffsetDirection", int.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap("dataOffsetValue",
				double.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap("dataOffsetUnit",
				java.lang.String.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap(
				"dataOffsetUnitInt", int.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap(
				"dataSourceProviderID", java.lang.String.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap(
				"dataOffsetDirectionHigh", int.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap("dataOffsetValueHigh",
				double.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap("dataOffsetUnitHigh",
				java.lang.String.class);
		serviceDocConfigureParaConfigureMap.addNodeFieldMap(
				"dataOffsetUnitIntHigh", int.class);
		seConfigureMapList.add(serviceDocConfigureParaConfigureMap);
		// Init configuration of ServiceDocConfigure
		// [ServiceDocConfigureParaGroup] node
		ServiceEntityConfigureMap serviceDocConfigureParaGroupConfigureMap = new ServiceEntityConfigureMap();
		serviceDocConfigureParaGroupConfigureMap
				.setParentNodeName(ServiceDocConfigure.NODENAME);
		serviceDocConfigureParaGroupConfigureMap
				.setNodeName(ServiceDocConfigureParaGroup.NODENAME);
		serviceDocConfigureParaGroupConfigureMap
				.setNodeType(ServiceDocConfigureParaGroup.class);
		serviceDocConfigureParaGroupConfigureMap
				.setTableName(ServiceDocConfigureParaGroup.NODENAME);
		serviceDocConfigureParaGroupConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		serviceDocConfigureParaGroupConfigureMap.addNodeFieldMap(
				"logicOperator", int.class);
		serviceDocConfigureParaGroupConfigureMap.addNodeFieldMap("layer",
				int.class);
		serviceDocConfigureParaGroupConfigureMap.addNodeFieldMap(
				"refParentGroupUUID", java.lang.String.class);
		seConfigureMapList.add(serviceDocConfigureParaGroupConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
