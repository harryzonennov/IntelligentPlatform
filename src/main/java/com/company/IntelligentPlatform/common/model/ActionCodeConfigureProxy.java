package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [ActionCode]
 * 
 * @author
 * @date Sun Jun 09 14:37:44 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class ActionCodeConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of ActionCode [ROOT] node
		ServiceEntityConfigureMap actionCodeConfigureMap = new ServiceEntityConfigureMap();
		actionCodeConfigureMap.setParentNodeName(" ");
		actionCodeConfigureMap.setNodeName("ROOT");
		actionCodeConfigureMap.setNodeType(ActionCode.class);
		actionCodeConfigureMap.setTableName("ActionCode");
		actionCodeConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		seConfigureMapList.add(actionCodeConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
