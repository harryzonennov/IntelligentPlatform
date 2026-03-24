package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

@Repository
public class PackageRegisterationEntityConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of PackageRegisterationEntity [ROOT] node
		ServiceEntityConfigureMap packageRegisterationEntityConfigureMap = new ServiceEntityConfigureMap();
		packageRegisterationEntityConfigureMap.setParentNodeName(" ");
		packageRegisterationEntityConfigureMap.setNodeName("ROOT");
		packageRegisterationEntityConfigureMap
				.setNodeType(PackageRegisterationEntity.class);
		packageRegisterationEntityConfigureMap
				.setTableName("PackageRegisterationEntity");
		packageRegisterationEntityConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		packageRegisterationEntityConfigureMap.addNodeFieldMap(
				"seRegisterDAOType", java.lang.String.class);
		packageRegisterationEntityConfigureMap.addNodeFieldMap(
				"databaseSubName", java.lang.String.class);
		seConfigureMapList.add(packageRegisterationEntityConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
