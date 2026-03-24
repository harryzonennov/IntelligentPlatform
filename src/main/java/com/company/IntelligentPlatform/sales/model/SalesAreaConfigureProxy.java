package com.company.IntelligentPlatform.sales.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [SalesArea]
 *
 * @author
 * @date Fri Feb 26 11:20:47 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class SalesAreaConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("net.thorstein.logistics");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<ServiceEntityConfigureMap>());
		// Init configuration of SalesArea [ROOT] node
		ServiceEntityConfigureMap salesAreaConfigureMap = new ServiceEntityConfigureMap();
		salesAreaConfigureMap.setParentNodeName(" ");
		salesAreaConfigureMap.setNodeName(SalesArea.NODENAME);
		salesAreaConfigureMap.setNodeType(SalesArea.class);
		salesAreaConfigureMap.setTableName(SalesArea.SENAME);
		salesAreaConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		salesAreaConfigureMap.addNodeFieldMap("parentAreaUUID",
				java.lang.String.class);
		salesAreaConfigureMap.addNodeFieldMap("rootAreaUUID",
				java.lang.String.class);
		salesAreaConfigureMap.addNodeFieldMap("level", int.class);
		salesAreaConfigureMap.addNodeFieldMap("convLabelType", int.class);
		seConfigureMapList.add(salesAreaConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
