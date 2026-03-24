package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [SystemCodeValueUnion]
 *
 * @author
 * @date Tue Nov 20 17:44:06 CST 2018
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class SystemCodeValueUnionConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of SystemCodeValueUnion [ROOT] node
		ServiceEntityConfigureMap systemCodeValueUnionConfigureMap = new ServiceEntityConfigureMap();
		systemCodeValueUnionConfigureMap.setParentNodeName(" ");
		systemCodeValueUnionConfigureMap
				.setNodeName(SystemCodeValueCollection.NODENAME);
		systemCodeValueUnionConfigureMap
				.setNodeType(SystemCodeValueCollection.class);
		systemCodeValueUnionConfigureMap
				.setTableName(SystemCodeValueCollection.SENAME);
		systemCodeValueUnionConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		systemCodeValueUnionConfigureMap.addNodeFieldMap("collectionCategory",
				int.class);
		systemCodeValueUnionConfigureMap.addNodeFieldMap("iconClass",
				String.class);
		systemCodeValueUnionConfigureMap.addNodeFieldMap("colorStyle",
				String.class);
		systemCodeValueUnionConfigureMap.addNodeFieldMap("lanKey",
				String.class);
		seConfigureMapList.add(systemCodeValueUnionConfigureMap);
		// Init configuration of SystemCodeValueUnion [SystemCodeValueUnion]
		// node

		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
