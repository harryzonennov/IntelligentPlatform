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
 * Configure Proxy CLASS FOR Service Entity [ServiceDocConsumerUnion]
 *
 * @author
 * @date Fri May 06 22:49:44 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class ServiceDocConsumerUnionConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of ServiceDocConsumerUnion [ROOT] node
		ServiceEntityConfigureMap serviceDocConsumerUnionConfigureMap = new ServiceEntityConfigureMap();
		serviceDocConsumerUnionConfigureMap.setParentNodeName(" ");
		serviceDocConsumerUnionConfigureMap
				.setNodeName(ServiceDocConsumerUnion.NODENAME);
		serviceDocConsumerUnionConfigureMap
				.setNodeType(ServiceDocConsumerUnion.class);
		serviceDocConsumerUnionConfigureMap
				.setTableName(ServiceDocConsumerUnion.SENAME);
		serviceDocConsumerUnionConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		serviceDocConsumerUnionConfigureMap.addNodeFieldMap("uiModelType",
				java.lang.String.class);
		serviceDocConsumerUnionConfigureMap.addNodeFieldMap(
				"uiModelTypeFullName", java.lang.String.class);
		serviceDocConsumerUnionConfigureMap.addNodeFieldMap(
				"i18nFullPath", java.lang.String.class);
		seConfigureMapList.add(serviceDocConsumerUnionConfigureMap);
		// Init configuration of ServiceDocConsumerUnion
		// [ServiceDocConsumerFieldUnion] node
		ServiceEntityConfigureMap serviceDocConsumerFieldUnionConfigureMap = new ServiceEntityConfigureMap();
		serviceDocConsumerFieldUnionConfigureMap
				.setParentNodeName(ServiceDocConsumerUnion.NODENAME);
		serviceDocConsumerFieldUnionConfigureMap
				.setNodeName(ServiceDocConsumerFieldUnion.NODENAME);
		serviceDocConsumerFieldUnionConfigureMap
				.setNodeType(ServiceDocConsumerFieldUnion.class);
		serviceDocConsumerFieldUnionConfigureMap
				.setTableName(ServiceDocConsumerFieldUnion.NODENAME);
		serviceDocConsumerFieldUnionConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		serviceDocConsumerFieldUnionConfigureMap.addNodeFieldMap("fieldLabel",
				java.lang.String.class);
		seConfigureMapList.add(serviceDocConsumerFieldUnionConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
