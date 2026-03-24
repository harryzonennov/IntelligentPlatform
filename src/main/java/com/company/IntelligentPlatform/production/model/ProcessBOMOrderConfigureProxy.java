package com.company.IntelligentPlatform.production.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [ProcessBOMOrder]
 *
 * @author
 * @date Sun Apr 03 22:52:20 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class ProcessBOMOrderConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("net.thorstein.production");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<ServiceEntityConfigureMap>());
		// Init configuration of ProcessBOMOrder [ROOT] node
		ServiceEntityConfigureMap processBOMOrderConfigureMap = new ServiceEntityConfigureMap();
		processBOMOrderConfigureMap.setParentNodeName(" ");
		processBOMOrderConfigureMap.setNodeName(ProcessBOMOrder.NODENAME);
		processBOMOrderConfigureMap.setNodeType(ProcessBOMOrder.class);
		processBOMOrderConfigureMap.setTableName(ProcessBOMOrder.SENAME);
		processBOMOrderConfigureMap
				.setFieldList(super.getBasicSENodeFieldMap());
		processBOMOrderConfigureMap.addNodeFieldMap("refMaterialSKUUUID",
				java.lang.String.class);
		processBOMOrderConfigureMap.addNodeFieldMap("refBOMUUID",
				java.lang.String.class);
		processBOMOrderConfigureMap.addNodeFieldMap("amount", double.class);
		processBOMOrderConfigureMap.addNodeFieldMap("refUnitUUID",
				java.lang.String.class);
		processBOMOrderConfigureMap.addNodeFieldMap("status", int.class);
		processBOMOrderConfigureMap.addNodeFieldMap("itemCategory", int.class);
		processBOMOrderConfigureMap.addNodeFieldMap("refProcessRouteUUID",
				java.lang.String.class);
		seConfigureMapList.add(processBOMOrderConfigureMap);
		// Init configuration of ProcessBOMOrder [ProcessBOMItem] node
		ServiceEntityConfigureMap processBOMItemConfigureMap = new ServiceEntityConfigureMap();
		processBOMItemConfigureMap.setParentNodeName(ProcessBOMOrder.NODENAME);
		processBOMItemConfigureMap.setNodeName(ProcessBOMItem.NODENAME);
		processBOMItemConfigureMap.setNodeType(ProcessBOMItem.class);
		processBOMItemConfigureMap.setTableName(ProcessBOMItem.NODENAME);
		processBOMItemConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		processBOMItemConfigureMap.addNodeFieldMap(
				"refProssRouteProcessItemUUID", java.lang.String.class);
		processBOMItemConfigureMap.addNodeFieldMap("layer", int.class);
		processBOMItemConfigureMap.addNodeFieldMap("refParentItemUUID",
				java.lang.String.class);
		processBOMItemConfigureMap.addNodeFieldMap("itemCategory", int.class);
		seConfigureMapList.add(processBOMItemConfigureMap);
		// Init configuration of BillOfMaterialOrder [ProcessBOMMaterialItem]
		// node
		ServiceEntityConfigureMap processBOMMaterialItemConfigureMap = new ServiceEntityConfigureMap();
		processBOMMaterialItemConfigureMap
				.setParentNodeName(ProcessBOMItem.NODENAME);
		processBOMMaterialItemConfigureMap
				.setNodeName(ProcessBOMMaterialItem.NODENAME);
		processBOMMaterialItemConfigureMap
				.setNodeType(ProcessBOMMaterialItem.class);
		processBOMMaterialItemConfigureMap
				.setTableName(ProcessBOMMaterialItem.NODENAME);
		processBOMMaterialItemConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		processBOMMaterialItemConfigureMap.addNodeFieldMap(
				"refMaterialSKUUUID", java.lang.String.class);
		processBOMMaterialItemConfigureMap.addNodeFieldMap("refUnitUUID",
				java.lang.String.class);
		processBOMMaterialItemConfigureMap.addNodeFieldMap("amount",
				double.class);
		seConfigureMapList.add(processBOMMaterialItemConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
