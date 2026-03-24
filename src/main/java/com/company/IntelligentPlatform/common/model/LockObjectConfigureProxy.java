package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [LockObject]
 * 
 * @author
 * @date Mon Jan 07 22:44:10 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class LockObjectConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of LockObject [ROOT] node
		ServiceEntityConfigureMap lockObjectConfigureMap = new ServiceEntityConfigureMap();
		lockObjectConfigureMap.setParentNodeName(" ");
		lockObjectConfigureMap.setNodeName("ROOT");
		lockObjectConfigureMap.setNodeType(LockObject.class);
		lockObjectConfigureMap.setTableName("LockObject");
		lockObjectConfigureMap.setFieldList(super.getBasicReferenceFieldMap());
		lockObjectConfigureMap.addNodeFieldMap("lockTimeDate",
				java.util.Date.class);
		seConfigureMapList.add(lockObjectConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
