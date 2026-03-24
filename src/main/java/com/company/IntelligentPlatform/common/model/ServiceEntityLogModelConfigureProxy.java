package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [ServiceEntityLogModel]
 *
 * @author
 * @date Mon Dec 24 22:37:26 CST 2018
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class ServiceEntityLogModelConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of ServiceEntityLogModel [ROOT] node
		ServiceEntityConfigureMap serviceEntityLogModelConfigureMap = new ServiceEntityConfigureMap();
		serviceEntityLogModelConfigureMap.setParentNodeName(" ");
		serviceEntityLogModelConfigureMap
				.setNodeName(ServiceEntityLogModel.NODENAME);
		serviceEntityLogModelConfigureMap
				.setNodeType(ServiceEntityLogModel.class);
		serviceEntityLogModelConfigureMap
				.setTableName(ServiceEntityLogModel.SENAME);
		serviceEntityLogModelConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		serviceEntityLogModelConfigureMap.addNodeFieldMap("processMode",
				int.class);
		serviceEntityLogModelConfigureMap.addNodeFieldMap("messageType",
				int.class);
		seConfigureMapList.add(serviceEntityLogModelConfigureMap);
		// Init configuration of ServiceEntityLogModel [ServiceEntityLogItem]
		// node
		ServiceEntityConfigureMap serviceEntityLogItemConfigureMap = new ServiceEntityConfigureMap();
		serviceEntityLogItemConfigureMap
				.setParentNodeName(ServiceEntityLogModel.NODENAME);
		serviceEntityLogItemConfigureMap
				.setNodeName(ServiceEntityLogItem.NODENAME);
		serviceEntityLogItemConfigureMap
				.setNodeType(ServiceEntityLogItem.class);
		serviceEntityLogItemConfigureMap
				.setTableName(ServiceEntityLogItem.NODENAME);
		serviceEntityLogItemConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		serviceEntityLogItemConfigureMap.addNodeFieldMap("oldValue",
				java.lang.String.class);
		serviceEntityLogItemConfigureMap.addNodeFieldMap("newValue",
				java.lang.String.class);
		serviceEntityLogItemConfigureMap.addNodeFieldMap("fieldType",
				java.lang.String.class);
		seConfigureMapList.add(serviceEntityLogItemConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
