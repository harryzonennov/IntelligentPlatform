package com.company.IntelligentPlatform.production.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [ProdJobOrder]
 *
 * @author
 * @date Mon Apr 11 10:53:41 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class ProdJobOrderConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("net.thorstein.production");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<ServiceEntityConfigureMap>());
		// Init configuration of ProdJobOrder [ROOT] node
		ServiceEntityConfigureMap prodJobOrderConfigureMap = new ServiceEntityConfigureMap();
		prodJobOrderConfigureMap.setParentNodeName(" ");
		prodJobOrderConfigureMap.setNodeName(ProdJobOrder.NODENAME);
		prodJobOrderConfigureMap.setNodeType(ProdJobOrder.class);
		prodJobOrderConfigureMap.setTableName(ProdJobOrder.SENAME);
		prodJobOrderConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		prodJobOrderConfigureMap.addNodeFieldMap("refProductionOrderUUID",
				java.lang.String.class);
		prodJobOrderConfigureMap.addNodeFieldMap("refProdRouteProcessItemUUID",
				java.lang.String.class);
		prodJobOrderConfigureMap.addNodeFieldMap("refWorkCenterUUID",
				java.lang.String.class);
		prodJobOrderConfigureMap.addNodeFieldMap("category", int.class);
		prodJobOrderConfigureMap.addNodeFieldMap("startDate",
				java.util.Date.class);
		prodJobOrderConfigureMap.addNodeFieldMap("planStartDate",
				java.util.Date.class);
		prodJobOrderConfigureMap.addNodeFieldMap("endDate",
				java.util.Date.class);
		prodJobOrderConfigureMap.addNodeFieldMap("planEndDate",
				java.util.Date.class);
		prodJobOrderConfigureMap.addNodeFieldMap("status", int.class);
		prodJobOrderConfigureMap.addNodeFieldMap("planNeedTime", double.class);
		seConfigureMapList.add(prodJobOrderConfigureMap);
		// Init configuration of ProdJobOrder [ProdJobMaterialItem] node
		ServiceEntityConfigureMap prodJobMaterialItemConfigureMap = new ServiceEntityConfigureMap();
		prodJobMaterialItemConfigureMap
				.setParentNodeName(ProdJobOrder.NODENAME);
		prodJobMaterialItemConfigureMap
				.setNodeName(ProdJobMaterialItem.NODENAME);
		prodJobMaterialItemConfigureMap.setNodeType(ProdJobMaterialItem.class);
		prodJobMaterialItemConfigureMap
				.setTableName(ProdJobMaterialItem.NODENAME);
		prodJobMaterialItemConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		prodJobMaterialItemConfigureMap.addNodeFieldMap("refMaterialSKUUUID",
				java.lang.String.class);
		prodJobMaterialItemConfigureMap.addNodeFieldMap("amount", double.class);
		prodJobMaterialItemConfigureMap.addNodeFieldMap("refUnitUUID",
				java.lang.String.class);
		seConfigureMapList.add(prodJobMaterialItemConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
