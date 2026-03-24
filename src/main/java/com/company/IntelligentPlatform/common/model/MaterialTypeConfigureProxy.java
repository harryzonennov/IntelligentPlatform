package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [MaterialType]
 *
 * @author
 * @date Tue Aug 11 16:49:53 CST 2015
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class MaterialTypeConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of MaterialType [ROOT] node
		ServiceEntityConfigureMap materialTypeConfigureMap = new ServiceEntityConfigureMap();
		materialTypeConfigureMap.setParentNodeName(" ");
		materialTypeConfigureMap.setNodeName(MaterialType.NODENAME);
		materialTypeConfigureMap.setNodeType(MaterialType.class);
		materialTypeConfigureMap.setTableName(MaterialType.SENAME);
		materialTypeConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		materialTypeConfigureMap.addNodeFieldMap("parentTypeUUID",
				java.lang.String.class);
		materialTypeConfigureMap.addNodeFieldMap("rootTypeUUID",
				java.lang.String.class);
		materialTypeConfigureMap.addNodeFieldMap("status",
				int.class);
		materialTypeConfigureMap.addNodeFieldMap("systemStandardCategory",
				int.class);
		seConfigureMapList.add(materialTypeConfigureMap);

		// Init configuration of Material [MaterialTypeAttachment] node
		ServiceEntityConfigureMap materialTypeAttachmentConfigureMap = new ServiceEntityConfigureMap();
		materialTypeAttachmentConfigureMap.setParentNodeName(MaterialType.NODENAME);
		materialTypeAttachmentConfigureMap.setNodeName(MaterialTypeAttachment.NODENAME);
		materialTypeAttachmentConfigureMap.setNodeType(MaterialTypeAttachment.class);
		materialTypeAttachmentConfigureMap
				.setTableName(MaterialTypeAttachment.NODENAME);
		materialTypeAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		materialTypeAttachmentConfigureMap.addNodeFieldMap("content", byte[].class);
		materialTypeAttachmentConfigureMap
				.addNodeFieldMap("fileType", String.class);
		seConfigureMapList.add(materialTypeAttachmentConfigureMap);

		// Init configuration of Material [MaterialTypeActionNode] node
		ServiceEntityConfigureMap materialTypeActionNodeConfigureMap = new ServiceEntityConfigureMap();
		materialTypeActionNodeConfigureMap.setParentNodeName(MaterialType.NODENAME);
		materialTypeActionNodeConfigureMap.setNodeName(MaterialTypeActionNode.NODENAME);
		materialTypeActionNodeConfigureMap.setNodeType(MaterialTypeActionNode.class);
		materialTypeActionNodeConfigureMap
				.setTableName(MaterialTypeActionNode.NODENAME);
		materialTypeActionNodeConfigureMap.setFieldList(super
				.getBasicActionCodeNodeMap());
		seConfigureMapList.add(materialTypeActionNodeConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
