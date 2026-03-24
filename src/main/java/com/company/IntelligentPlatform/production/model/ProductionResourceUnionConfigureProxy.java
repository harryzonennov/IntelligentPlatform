package com.company.IntelligentPlatform.production.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [ProductionResourceUnion]
 *
 * @author
 * @date Wed Mar 23 23:23:54 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class ProductionResourceUnionConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("net.thorstein.production");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<ServiceEntityConfigureMap>());
		// Init configuration of ProductionResourceUnion [ROOT] node
		ServiceEntityConfigureMap productionResourceUnionConfigureMap = new ServiceEntityConfigureMap();
		productionResourceUnionConfigureMap.setParentNodeName(" ");
		productionResourceUnionConfigureMap
				.setNodeName(ProductionResourceUnion.NODENAME);
		productionResourceUnionConfigureMap
				.setNodeType(ProductionResourceUnion.class);
		productionResourceUnionConfigureMap
				.setTableName(ProductionResourceUnion.SENAME);
		productionResourceUnionConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		productionResourceUnionConfigureMap.addNodeFieldMap("utilizationRate",
				double.class);
		productionResourceUnionConfigureMap.addNodeFieldMap("efficiency",
				double.class);
		productionResourceUnionConfigureMap.addNodeFieldMap("keyResourceFlag",
				int.class);
		productionResourceUnionConfigureMap.addNodeFieldMap("resourceType",
				int.class);
		productionResourceUnionConfigureMap.addNodeFieldMap(
				"refCostCenterUUID", java.lang.String.class);
		productionResourceUnionConfigureMap.addNodeFieldMap("dailyShift",
				int.class);
		productionResourceUnionConfigureMap.addNodeFieldMap("costInhour",
				double.class);
		productionResourceUnionConfigureMap.addNodeFieldMap("workHoursInShift",
				double.class);
		seConfigureMapList.add(productionResourceUnionConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
