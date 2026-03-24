package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [HostCompany]
 * 
 * @author
 * @date Sat May 24 00:29:33 CST 2014
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class HostCompanyConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of HostCompany [ROOT] node
		ServiceEntityConfigureMap hostCompanyConfigureMap = new ServiceEntityConfigureMap();
		hostCompanyConfigureMap.setParentNodeName(" ");
		hostCompanyConfigureMap.setNodeName("ROOT");
		hostCompanyConfigureMap.setNodeType(HostCompany.class);
		hostCompanyConfigureMap.setTableName("HostCompany");
		hostCompanyConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		hostCompanyConfigureMap.addNodeFieldMap("parentOrganizationUUID",
				java.lang.String.class);
		hostCompanyConfigureMap.addNodeFieldMap("organType", int.class);
		hostCompanyConfigureMap.addNodeFieldMap("organLevel", int.class);
		hostCompanyConfigureMap.addNodeFieldMap("refOrganizationFunction", String.class);
		hostCompanyConfigureMap.addNodeFieldMap("mainContactUUID",
				java.lang.String.class);
		hostCompanyConfigureMap.addNodeFieldMap("refCashierUUID",
				java.lang.String.class);
		hostCompanyConfigureMap.addNodeFieldMap("refAccountantUUID",
				java.lang.String.class);
		hostCompanyConfigureMap.addNodeFieldMap("refFinOrgUUID",
				java.lang.String.class);
		hostCompanyConfigureMap.addNodeFieldMap("countryName",
				java.lang.String.class);
		hostCompanyConfigureMap.addNodeFieldMap("stateName",
				java.lang.String.class);
		hostCompanyConfigureMap.addNodeFieldMap("cityName",
				java.lang.String.class);
		hostCompanyConfigureMap.addNodeFieldMap("streetName",
				java.lang.String.class);
		hostCompanyConfigureMap.addNodeFieldMap("townzone",
				java.lang.String.class);
		hostCompanyConfigureMap.addNodeFieldMap("address",
				java.lang.String.class);
		hostCompanyConfigureMap.addNodeFieldMap("telephone",
				java.lang.String.class);
		hostCompanyConfigureMap.addNodeFieldMap("fax", java.lang.String.class);
		hostCompanyConfigureMap
				.addNodeFieldMap("email", java.lang.String.class);
		hostCompanyConfigureMap.addNodeFieldMap("webPage",
				java.lang.String.class);
		hostCompanyConfigureMap.addNodeFieldMap("comLogo",
				byte[].class);
		hostCompanyConfigureMap.addNodeFieldMap("fullName",
				java.lang.String.class);
		hostCompanyConfigureMap.addNodeFieldMap("comReportTitle",
				java.lang.String.class);	
		hostCompanyConfigureMap.addNodeFieldMap("tags",
				java.lang.String.class);
		hostCompanyConfigureMap.addNodeFieldMap("taxNumber",
				java.lang.String.class);
		hostCompanyConfigureMap.addNodeFieldMap("bankAccount",
				java.lang.String.class);
		hostCompanyConfigureMap.addNodeFieldMap("depositBank",
				java.lang.String.class);
		seConfigureMapList.add(hostCompanyConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
