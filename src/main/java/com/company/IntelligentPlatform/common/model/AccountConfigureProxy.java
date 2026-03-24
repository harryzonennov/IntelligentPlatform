package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [Account]
 * 
 * @author
 * @date Tue Oct 27 23:08:38 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class AccountConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of Account [ROOT] node
		ServiceEntityConfigureMap accountConfigureMap = new ServiceEntityConfigureMap();
		accountConfigureMap.setParentNodeName(" ");
		accountConfigureMap.setNodeName(Account.NODENAME);
		accountConfigureMap.setNodeType(Account.class);
		accountConfigureMap.setTableName(Account.SENAME);
		accountConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		accountConfigureMap.addNodeFieldMap("accountType", int.class);
		accountConfigureMap.addNodeFieldMap("address", java.lang.String.class);
		accountConfigureMap
				.addNodeFieldMap("telephone", java.lang.String.class);
		accountConfigureMap.addNodeFieldMap("fax", java.lang.String.class);
		accountConfigureMap.addNodeFieldMap("email", java.lang.String.class);
		accountConfigureMap.addNodeFieldMap("webPage", java.lang.String.class);
		accountConfigureMap.addNodeFieldMap("postcode", java.lang.String.class);
		accountConfigureMap.addNodeFieldMap("countryName", java.lang.String.class);
		accountConfigureMap.addNodeFieldMap("stateName", java.lang.String.class);
		accountConfigureMap.addNodeFieldMap("cityName", java.lang.String.class);
		accountConfigureMap.addNodeFieldMap("refCityUUID",
				java.lang.String.class);
		accountConfigureMap.addNodeFieldMap("townZone", java.lang.String.class);
		accountConfigureMap.addNodeFieldMap("subArea", java.lang.String.class);
		accountConfigureMap.addNodeFieldMap("streetName",
				java.lang.String.class);
		accountConfigureMap.addNodeFieldMap("houseNumber",
				java.lang.String.class);
		accountConfigureMap.addNodeFieldMap("regularType", int.class);
		accountConfigureMap.addNodeFieldMap("taxNumber",
				java.lang.String.class);
		accountConfigureMap.addNodeFieldMap("bankAccount",
				java.lang.String.class);
		accountConfigureMap.addNodeFieldMap("depositBank",
				java.lang.String.class);
		seConfigureMapList.add(accountConfigureMap);		
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
