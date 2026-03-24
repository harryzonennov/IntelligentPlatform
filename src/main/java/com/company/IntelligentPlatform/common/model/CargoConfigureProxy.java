package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [Cargo]
 *
 * @author
 * @date Thu Feb 14 15:51:27 CST 2013
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class CargoConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of Cargo [ROOT] node
		ServiceEntityConfigureMap cargoConfigureMap = new ServiceEntityConfigureMap();
		cargoConfigureMap.setParentNodeName(" ");
		cargoConfigureMap.setNodeName("ROOT");
		cargoConfigureMap.setNodeType(Cargo.class);
		cargoConfigureMap.setTableName("Cargo");
		cargoConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		cargoConfigureMap.addNodeFieldMap("packageType", int.class);
		cargoConfigureMap.addNodeFieldMap("cargoType", int.class);
		cargoConfigureMap
				.addNodeFieldMap("packageInfo", java.lang.String.class);
		cargoConfigureMap.addNodeFieldMap("weight", double.class);
		cargoConfigureMap.addNodeFieldMap("volume", double.class);
		cargoConfigureMap.addNodeFieldMap("externalID", java.lang.String.class);
		cargoConfigureMap.addNodeFieldMap("regularType", int.class);
		cargoConfigureMap.addNodeFieldMap("skuNumber", java.lang.String.class);
		seConfigureMapList.add(cargoConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
