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
 * Configure Proxy CLASS FOR Service Entity [SystemCodeValueCollection]
 *
 * @author
 * @date Tue Nov 20 17:49:38 CST 2018
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class SystemCodeValueCollectionConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of SystemCodeValueCollection [ROOT] node
		ServiceEntityConfigureMap systemCodeValueCollectionConfigureMap = new ServiceEntityConfigureMap();
		systemCodeValueCollectionConfigureMap.setParentNodeName(" ");
		systemCodeValueCollectionConfigureMap
				.setNodeName(SystemCodeValueCollection.NODENAME);
		systemCodeValueCollectionConfigureMap
				.setNodeType(SystemCodeValueCollection.class);
		systemCodeValueCollectionConfigureMap
				.setTableName(SystemCodeValueCollection.SENAME);
		systemCodeValueCollectionConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		systemCodeValueCollectionConfigureMap.addNodeFieldMap(
				"collectionCategory", int.class);
		systemCodeValueCollectionConfigureMap.addNodeFieldMap(
				"keyType", int.class);
		systemCodeValueCollectionConfigureMap.addNodeFieldMap(
				"collectionType", int.class);
		systemCodeValueCollectionConfigureMap.addNodeFieldMap(
				"unionType", int.class);
		seConfigureMapList.add(systemCodeValueCollectionConfigureMap);
		// Init configuration of SystemCodeValueCollection
		// [SystemCodeValueUnion] node
		ServiceEntityConfigureMap systemCodeValueUnionConfigureMap = new ServiceEntityConfigureMap();
		systemCodeValueUnionConfigureMap
				.setParentNodeName(SystemCodeValueCollection.NODENAME);
		systemCodeValueUnionConfigureMap
				.setNodeName(SystemCodeValueUnion.NODENAME);
		systemCodeValueUnionConfigureMap
				.setNodeType(SystemCodeValueUnion.class);
		systemCodeValueUnionConfigureMap
				.setTableName(SystemCodeValueUnion.NODENAME);
		systemCodeValueUnionConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		systemCodeValueUnionConfigureMap.addNodeFieldMap("keyType", int.class);
		systemCodeValueUnionConfigureMap.addNodeFieldMap("iconClass",
				String.class);
		systemCodeValueUnionConfigureMap.addNodeFieldMap("colorStyle",
				String.class);
		systemCodeValueUnionConfigureMap.addNodeFieldMap("hideFlag",
				int.class);
		systemCodeValueUnionConfigureMap.addNodeFieldMap("lanKey",
				String.class);
		seConfigureMapList.add(systemCodeValueUnionConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
