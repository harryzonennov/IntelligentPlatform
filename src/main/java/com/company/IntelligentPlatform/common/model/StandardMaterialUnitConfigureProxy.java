package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [StandardMaterialUnit]
 *
 * @author
 * @date Tue Aug 11 16:57:07 CST 2015
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class StandardMaterialUnitConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of StandardMaterialUnit [ROOT] node
		ServiceEntityConfigureMap standardMaterialUnitConfigureMap = new ServiceEntityConfigureMap();
		standardMaterialUnitConfigureMap.setParentNodeName(" ");
		standardMaterialUnitConfigureMap
				.setNodeName(StandardMaterialUnit.NODENAME);
		standardMaterialUnitConfigureMap
				.setNodeType(StandardMaterialUnit.class);
		standardMaterialUnitConfigureMap
				.setTableName(StandardMaterialUnit.SENAME);
		standardMaterialUnitConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		standardMaterialUnitConfigureMap.addNodeFieldMap("unitType",
				int.class);
		standardMaterialUnitConfigureMap.addNodeFieldMap("languageCode",
				String.class);
		standardMaterialUnitConfigureMap.addNodeFieldMap("unitCategory",
				int.class);
		standardMaterialUnitConfigureMap.addNodeFieldMap("toReferUnitFactor",
				double.class);
		standardMaterialUnitConfigureMap.addNodeFieldMap("toStandardRatio",
				double.class);
		standardMaterialUnitConfigureMap.addNodeFieldMap("referUnitUUID",
				String.class);
		standardMaterialUnitConfigureMap.addNodeFieldMap("toReferUnitOffset",
				double.class);
		standardMaterialUnitConfigureMap.addNodeFieldMap("systemCategory",
				int.class);
		seConfigureMapList.add(standardMaterialUnitConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
