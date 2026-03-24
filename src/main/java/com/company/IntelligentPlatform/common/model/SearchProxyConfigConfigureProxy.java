package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [SearchProxyConfig]
 *
 * @author
 * @date Wed Nov 13 20:54:12 CST 2019
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class SearchProxyConfigConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of SearchProxyConfig [ROOT] node
		ServiceEntityConfigureMap searchProxyConfigConfigureMap = new ServiceEntityConfigureMap();
		searchProxyConfigConfigureMap.setParentNodeName(" ");
		searchProxyConfigConfigureMap.setNodeName(SearchProxyConfig.NODENAME);
		searchProxyConfigConfigureMap.setNodeType(SearchProxyConfig.class);
		searchProxyConfigConfigureMap.setTableName(SearchProxyConfig.SENAME);
		searchProxyConfigConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		searchProxyConfigConfigureMap.addNodeFieldMap("searchModelName",
				java.lang.String.class);
		searchProxyConfigConfigureMap.addNodeFieldMap("searchProxyName",
				java.lang.String.class);
		searchProxyConfigConfigureMap
				.addNodeFieldMap("documentType", int.class);
		searchProxyConfigConfigureMap.addNodeFieldMap("proxyType", int.class);
		searchProxyConfigConfigureMap.addNodeFieldMap(
				"configureSearchLogicFlag", int.class);
		searchProxyConfigConfigureMap.addNodeFieldMap("serviceManagerName",
				java.lang.String.class);
		searchProxyConfigConfigureMap.addNodeFieldMap("searchDocMethodName",
				java.lang.String.class);
		searchProxyConfigConfigureMap.addNodeFieldMap("searchItemMethodName",
				java.lang.String.class);
		searchProxyConfigConfigureMap.addNodeFieldMap("modelNameJson",
				java.lang.String.class);
		seConfigureMapList.add(searchProxyConfigConfigureMap);
		// Init configuration of SearchProxyConfig [SearchFieldConfig] node
		ServiceEntityConfigureMap searchFieldConfigConfigureMap = new ServiceEntityConfigureMap();
		searchFieldConfigConfigureMap
				.setParentNodeName(SearchProxyConfig.NODENAME);
		searchFieldConfigConfigureMap.setNodeName(SearchFieldConfig.NODENAME);
		searchFieldConfigConfigureMap.setNodeType(SearchFieldConfig.class);
		searchFieldConfigConfigureMap.setTableName(SearchFieldConfig.NODENAME);
		searchFieldConfigConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		searchFieldConfigConfigureMap.addNodeFieldMap("category", int.class);
		searchFieldConfigConfigureMap.addNodeFieldMap("fieldName",
				java.lang.String.class);
		searchFieldConfigConfigureMap.addNodeFieldMap("refSelectType",
				int.class);
		searchFieldConfigConfigureMap.addNodeFieldMap("refSelectURL",
				java.lang.String.class);
		searchFieldConfigConfigureMap.addNodeFieldMap("switchFlag",
				int.class);
		seConfigureMapList.add(searchFieldConfigConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
