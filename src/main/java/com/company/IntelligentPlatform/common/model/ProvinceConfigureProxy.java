package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [Province]
 * 
 * @author
 * @date Sun Feb 10 14:02:18 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class ProvinceConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of Province [ROOT] node
		ServiceEntityConfigureMap provinceConfigureMap = new ServiceEntityConfigureMap();
		provinceConfigureMap.setParentNodeName(" ");
		provinceConfigureMap.setNodeName("ROOT");
		provinceConfigureMap.setNodeType(Province.class);
		provinceConfigureMap.setTableName("Province");
		provinceConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		seConfigureMapList.add(provinceConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
