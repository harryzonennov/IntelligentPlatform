package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [ServiceVersionRecord]
 * 
 * @author
 * @date Mon Sep 09 16:54:06 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class ServiceVersionRecordConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of ServiceVersionRecord [ROOT] node
		ServiceEntityConfigureMap serviceVersionRecordConfigureMap = new ServiceEntityConfigureMap();
		serviceVersionRecordConfigureMap.setParentNodeName(" ");
		serviceVersionRecordConfigureMap.setNodeName("ROOT");
		serviceVersionRecordConfigureMap
				.setNodeType(ServiceVersionRecord.class);
		serviceVersionRecordConfigureMap.setTableName("ServiceVersionRecord");
		serviceVersionRecordConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		serviceVersionRecordConfigureMap.addNodeFieldMap("version", int.class);
		serviceVersionRecordConfigureMap.addNodeFieldMap("startVersion",
				int.class);
		serviceVersionRecordConfigureMap.addNodeFieldMap("versionStep",
				int.class);
		serviceVersionRecordConfigureMap.addNodeFieldMap("sp", int.class);
		serviceVersionRecordConfigureMap.addNodeFieldMap("spStep", int.class);
		serviceVersionRecordConfigureMap.addNodeFieldMap("startSP", int.class);
		seConfigureMapList.add(serviceVersionRecordConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
