package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [LogonInfo]
 * 
 * @author
 * @date Wed Feb 20 18:51:40 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class LogonInfoConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of LogonInfo [ROOT] node
		ServiceEntityConfigureMap logonInfoConfigureMap = new ServiceEntityConfigureMap();
		logonInfoConfigureMap.setParentNodeName(" ");
		logonInfoConfigureMap.setNodeName("ROOT");
		logonInfoConfigureMap.setNodeType(LogonInfo.class);
		logonInfoConfigureMap.setTableName("LogonInfo");
		logonInfoConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		logonInfoConfigureMap.addNodeFieldMap("refUserUUID",
				java.lang.String.class);
		logonInfoConfigureMap.addNodeFieldMap("languageCode",
				java.lang.String.class);
		logonInfoConfigureMap.addNodeFieldMap("logonType", int.class);
		logonInfoConfigureMap.addNodeFieldMap("status", int.class);
		logonInfoConfigureMap.addNodeFieldMap("startLogonTime",
				java.util.Date.class);
		logonInfoConfigureMap.addNodeFieldMap("logOffTime",
				java.util.Date.class);
		logonInfoConfigureMap.addNodeFieldMap("logonTryTimes", int.class);
		seConfigureMapList.add(logonInfoConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
