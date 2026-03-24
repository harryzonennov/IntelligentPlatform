package com.company.IntelligentPlatform.production.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [ProdProcess]
 *
 * @author
 * @date Thu Mar 31 11:46:04 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class ProdProcessConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("net.thorstein.production");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<ServiceEntityConfigureMap>());
		// Init configuration of ProdProcess [ROOT] node
		ServiceEntityConfigureMap prodProcessConfigureMap = new ServiceEntityConfigureMap();
		prodProcessConfigureMap.setParentNodeName(" ");
		prodProcessConfigureMap.setNodeName(ProdProcess.NODENAME);
		prodProcessConfigureMap.setNodeType(ProdProcess.class);
		prodProcessConfigureMap.setTableName(ProdProcess.SENAME);
		prodProcessConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		prodProcessConfigureMap
				.addNodeFieldMap("keyProcessFlag", int.class);
		prodProcessConfigureMap.addNodeFieldMap("status", int.class);
		prodProcessConfigureMap.addNodeFieldMap("refWorkCenterUUID",
				java.lang.String.class);
		prodProcessConfigureMap.addNodeFieldMap("varExecutionTime",
				double.class);
		prodProcessConfigureMap.addNodeFieldMap("fixedExecutionTime",
				double.class);
		prodProcessConfigureMap.addNodeFieldMap("prepareTime",
				double.class);
		prodProcessConfigureMap.addNodeFieldMap("queueTime",
				double.class);
		prodProcessConfigureMap.addNodeFieldMap("varMoveTime",
				double.class);
		prodProcessConfigureMap.addNodeFieldMap("fixedMoveTime",
				double.class);
		seConfigureMapList.add(prodProcessConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
