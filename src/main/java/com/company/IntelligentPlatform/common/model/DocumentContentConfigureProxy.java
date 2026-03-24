package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [DocumentContent]
 * 
 * @author
 * @date Sat Dec 12 20:58:29 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class DocumentContentConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of DocumentContent [ROOT] node
		ServiceEntityConfigureMap documentContentConfigureMap = new ServiceEntityConfigureMap();
		documentContentConfigureMap.setParentNodeName(" ");
		documentContentConfigureMap.setNodeName(DocumentContent.NODENAME);
		documentContentConfigureMap.setNodeType(DocumentContent.class);
		documentContentConfigureMap.setTableName(DocumentContent.SENAME);
		documentContentConfigureMap.setFieldList(super
				.getBasicDocumentFieldMap());
		seConfigureMapList.add(documentContentConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
