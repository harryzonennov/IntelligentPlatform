package com.company.IntelligentPlatform.production.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [ProcessRouteOrder]
 *
 * @author
 * @date Thu Mar 31 11:53:48 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class ProcessRouteOrderConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("net.thorstein.production");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<ServiceEntityConfigureMap>());
		// Init configuration of ProcessRouteOrder [ROOT] node
		ServiceEntityConfigureMap processRouteOrderConfigureMap = new ServiceEntityConfigureMap();
		processRouteOrderConfigureMap.setParentNodeName(" ");
		processRouteOrderConfigureMap.setNodeName(ProcessRouteOrder.NODENAME);
		processRouteOrderConfigureMap.setNodeType(ProcessRouteOrder.class);
		processRouteOrderConfigureMap.setTableName(ProcessRouteOrder.SENAME);
		processRouteOrderConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		processRouteOrderConfigureMap.addNodeFieldMap("keyRouteFlag",
				int.class);
		processRouteOrderConfigureMap.addNodeFieldMap("status", int.class);
		processRouteOrderConfigureMap.addNodeFieldMap(
				"refParentProcessRouteUUID", java.lang.String.class);
		processRouteOrderConfigureMap.addNodeFieldMap(
				"refTemplateProcessRouteUUID", java.lang.String.class);
		processRouteOrderConfigureMap.addNodeFieldMap("refMaterialSKUUUID",
				java.lang.String.class);
		processRouteOrderConfigureMap.addNodeFieldMap("routeType", int.class);
		processRouteOrderConfigureMap.addNodeFieldMap("refUnitUUID",
				java.lang.String.class);
		seConfigureMapList.add(processRouteOrderConfigureMap);
		// Init configuration of ProcessRouteOrder [ProcessRouteProcessItem]
		// node
		ServiceEntityConfigureMap processRouteProcessItemConfigureMap = new ServiceEntityConfigureMap();
		processRouteProcessItemConfigureMap
				.setParentNodeName(ProcessRouteOrder.NODENAME);
		processRouteProcessItemConfigureMap
				.setNodeName(ProcessRouteProcessItem.NODENAME);
		processRouteProcessItemConfigureMap
				.setNodeType(ProcessRouteProcessItem.class);
		processRouteProcessItemConfigureMap
				.setTableName(ProcessRouteProcessItem.NODENAME);
		processRouteProcessItemConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		processRouteProcessItemConfigureMap.addNodeFieldMap("keyProcessFlag",
				int.class);
		processRouteProcessItemConfigureMap
				.addNodeFieldMap("status", int.class);
		processRouteProcessItemConfigureMap
		.addNodeFieldMap("processIndex", int.class);
		processRouteProcessItemConfigureMap.addNodeFieldMap(
				"productionBatchSize", double.class);
		processRouteProcessItemConfigureMap.addNodeFieldMap("moveBatchSize",
				double.class);
		processRouteProcessItemConfigureMap.addNodeFieldMap("varExecutionTime",
				double.class);
		processRouteProcessItemConfigureMap.addNodeFieldMap("fixedExecutionTime",
				double.class);
		processRouteProcessItemConfigureMap.addNodeFieldMap("prepareTime",
				double.class);
		processRouteProcessItemConfigureMap.addNodeFieldMap("queueTime",
				double.class);
		processRouteProcessItemConfigureMap.addNodeFieldMap("varMoveTime",
				double.class);
		processRouteProcessItemConfigureMap.addNodeFieldMap("fixedMoveTime",
				double.class);
		seConfigureMapList.add(processRouteProcessItemConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
