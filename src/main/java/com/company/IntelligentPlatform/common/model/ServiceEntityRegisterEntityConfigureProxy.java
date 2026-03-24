package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

@Repository
public class ServiceEntityRegisterEntityConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of ServiceEntityRegisterEntity [ROOT] node
		ServiceEntityConfigureMap serviceEntityRegisterEntityConfigureMap = new ServiceEntityConfigureMap();
		serviceEntityRegisterEntityConfigureMap.setParentNodeName(" ");
		serviceEntityRegisterEntityConfigureMap.setNodeName("ROOT");
		serviceEntityRegisterEntityConfigureMap
				.setNodeType(ServiceEntityRegisterEntity.class);
		serviceEntityRegisterEntityConfigureMap
				.setTableName("ServiceEntityRegisterEntity");
		serviceEntityRegisterEntityConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		serviceEntityRegisterEntityConfigureMap.addNodeFieldMap(
				"seManagerType", java.lang.String.class);
		serviceEntityRegisterEntityConfigureMap.addNodeFieldMap("seDAOType",
				java.lang.String.class);
		serviceEntityRegisterEntityConfigureMap.addNodeFieldMap("seProxyType",
				java.lang.String.class);
		serviceEntityRegisterEntityConfigureMap.addNodeFieldMap("seModuleType",
				java.lang.String.class);
		serviceEntityRegisterEntityConfigureMap.addNodeFieldMap("packageID",
				java.lang.String.class);
		seConfigureMapList.add(serviceEntityRegisterEntityConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
