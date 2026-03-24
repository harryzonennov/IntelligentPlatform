package com.company.IntelligentPlatform.finance.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.company.IntelligentPlatform.finance.model.FinAccountTitle;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [FinAccountTitle]
 *
 * @author
 * @date Fri May 23 11:00:23 CST 2014
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class FinAccountTitleConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("net.thorstein.finance");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<ServiceEntityConfigureMap>());
		// Init configuration of FinAccountTitle [ROOT] node
		ServiceEntityConfigureMap finAccountTitleConfigureMap = new ServiceEntityConfigureMap();
		finAccountTitleConfigureMap.setParentNodeName(" ");
		finAccountTitleConfigureMap.setNodeName("ROOT");
		finAccountTitleConfigureMap.setNodeType(FinAccountTitle.class);
		finAccountTitleConfigureMap.setTableName("FinAccountTitle");
		finAccountTitleConfigureMap
				.setFieldList(super.getBasicSENodeFieldMap());
		finAccountTitleConfigureMap.addNodeFieldMap("finAccountType", int.class);
		finAccountTitleConfigureMap.addNodeFieldMap("generateType", int.class);
		finAccountTitleConfigureMap.addNodeFieldMap("category", int.class);
		finAccountTitleConfigureMap.addNodeFieldMap("parentAccountTitleUUID",
				java.lang.String.class);
		finAccountTitleConfigureMap.addNodeFieldMap("rootAccountTitleUUID",
				java.lang.String.class);
		finAccountTitleConfigureMap.addNodeFieldMap("originalType", int.class);
		finAccountTitleConfigureMap.addNodeFieldMap("settleType", int.class);
		seConfigureMapList.add(finAccountTitleConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
