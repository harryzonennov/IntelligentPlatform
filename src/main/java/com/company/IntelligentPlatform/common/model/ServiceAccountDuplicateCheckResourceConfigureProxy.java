package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceAccountDuplicateCheckResource;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity
 * [ServiceAccountDuplicateCheckResource]
 *
 * @author
 * @date Thu May 14 14:44:51 CST 2015
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class ServiceAccountDuplicateCheckResourceConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of ServiceAccountDuplicateCheckResource [ROOT]
		// node
		ServiceEntityConfigureMap serviceAccountDuplicateCheckResourceConfigureMap = new ServiceEntityConfigureMap();
		serviceAccountDuplicateCheckResourceConfigureMap.setParentNodeName(" ");
		serviceAccountDuplicateCheckResourceConfigureMap
				.setNodeName(ServiceAccountDuplicateCheckResource.NODENAME);
		serviceAccountDuplicateCheckResourceConfigureMap
				.setNodeType(ServiceAccountDuplicateCheckResource.class);
		serviceAccountDuplicateCheckResourceConfigureMap
				.setTableName(ServiceAccountDuplicateCheckResource.SENAME);
		serviceAccountDuplicateCheckResourceConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		serviceAccountDuplicateCheckResourceConfigureMap.addNodeFieldMap(
				"refAccountType", int.class);
		serviceAccountDuplicateCheckResourceConfigureMap.addNodeFieldMap(
				"executedOrder", int.class);
		serviceAccountDuplicateCheckResourceConfigureMap.addNodeFieldMap(
				"switchFlag", int.class);
		serviceAccountDuplicateCheckResourceConfigureMap.addNodeFieldMap(
				"implementClassName", java.lang.String.class);
		serviceAccountDuplicateCheckResourceConfigureMap.addNodeFieldMap(
				"implementType", int.class);
		serviceAccountDuplicateCheckResourceConfigureMap.addNodeFieldMap(
				"logicRelationship", int.class);
		seConfigureMapList
				.add(serviceAccountDuplicateCheckResourceConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
