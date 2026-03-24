package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [Organization]
 * 
 * @author
 * @dateSun Nov 25 17:42:54 CST 2012
 * 
 *          This class is generated automatically by platform automation
 *          register tool
 */
@Repository
public class OrganizationConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of Organization [ROOT] node
		ServiceEntityConfigureMap organizationConfigureMap = new ServiceEntityConfigureMap();
		organizationConfigureMap.setParentNodeName(" ");
		organizationConfigureMap.setNodeName("ROOT");
		organizationConfigureMap.setNodeType(Organization.class);
		organizationConfigureMap.setTableName("Organization");
		organizationConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		organizationConfigureMap.addNodeFieldMap("clientID",
				java.lang.String.class);
		organizationConfigureMap.addNodeFieldMap("parentOrganizationUUID",
				java.lang.String.class);
		organizationConfigureMap.addNodeFieldMap("organType", int.class);
		organizationConfigureMap.addNodeFieldMap("organizationFunction",
				int.class);
		organizationConfigureMap.addNodeFieldMap("refOrganizationFunction",
				String.class);
		organizationConfigureMap.addNodeFieldMap("refFinOrgUUID",
				java.lang.String.class);
		organizationConfigureMap.addNodeFieldMap("telephone",
				java.lang.String.class);
		organizationConfigureMap.addNodeFieldMap("mobile",
				java.lang.String.class);
		organizationConfigureMap.addNodeFieldMap("address",
				java.lang.String.class);
		organizationConfigureMap.addNodeFieldMap("postcode",
				java.lang.String.class);
		organizationConfigureMap.addNodeFieldMap("stateName",
				java.lang.String.class);
		organizationConfigureMap.addNodeFieldMap("countryName",
				java.lang.String.class);
		organizationConfigureMap.addNodeFieldMap("cityName",
				java.lang.String.class);
		organizationConfigureMap.addNodeFieldMap("refCityUUID",
				java.lang.String.class);
		organizationConfigureMap.addNodeFieldMap("townZone",
				java.lang.String.class);
		organizationConfigureMap.addNodeFieldMap("subArea",
				java.lang.String.class);
		organizationConfigureMap.addNodeFieldMap("streetName",
				java.lang.String.class);
		organizationConfigureMap.addNodeFieldMap("houseNumber",
				java.lang.String.class);
		organizationConfigureMap.addNodeFieldMap("addressOnMap",
				java.lang.String.class);
		organizationConfigureMap.addNodeFieldMap("fullName",
				java.lang.String.class);
		organizationConfigureMap.addNodeFieldMap("longitude",
				java.lang.String.class);
		organizationConfigureMap.addNodeFieldMap("latitude",
				java.lang.String.class);
		organizationConfigureMap.addNodeFieldMap("fax", java.lang.String.class);
		organizationConfigureMap.addNodeFieldMap("contactMobileNumber",
				java.lang.String.class);
		organizationConfigureMap.addNodeFieldMap("organLevel", int.class);
		organizationConfigureMap.addNodeFieldMap("mainContactUUID", int.class);
		organizationConfigureMap.addNodeFieldMap("refCashierUUID", int.class);
		organizationConfigureMap.addNodeFieldMap("refAccountantUUID",
				java.lang.String.class);
		organizationConfigureMap
				.addNodeFieldMap("tags", java.lang.String.class);
		organizationConfigureMap.addNodeFieldMap("taxNumber",
				java.lang.String.class);
		organizationConfigureMap.addNodeFieldMap("bankAccount",
				java.lang.String.class);
		organizationConfigureMap.addNodeFieldMap("depositBank",
				java.lang.String.class);
		seConfigureMapList.add(organizationConfigureMap);

		// Init configuration of Material [OrganLogonUserRole] node
		ServiceEntityConfigureMap organLogonUserRoleMap = new ServiceEntityConfigureMap();
		organLogonUserRoleMap
				.setParentNodeName(Organization.NODENAME);
		organLogonUserRoleMap
				.setNodeName(OrganLogonUserRole.NODENAME);
		organLogonUserRoleMap
				.setNodeType(OrganLogonUserRole.class);
		organLogonUserRoleMap
				.setTableName(OrganLogonUserRole.NODENAME);
		organLogonUserRoleMap.setFieldList(super
				.getBasicReferenceFieldMap());
		organLogonUserRoleMap.addNodeFieldMap("logonUserRole",
				int.class);
		seConfigureMapList.add(organLogonUserRoleMap);

		// Init configuration of Material [OrganizationAttachment] node
		ServiceEntityConfigureMap organizationAttachmentConfigureMap = new ServiceEntityConfigureMap();
		organizationAttachmentConfigureMap
				.setParentNodeName(Organization.NODENAME);
		organizationAttachmentConfigureMap
				.setNodeName(OrganizationAttachment.NODENAME);
		organizationAttachmentConfigureMap
				.setNodeType(OrganizationAttachment.class);
		organizationAttachmentConfigureMap
				.setTableName(OrganizationAttachment.NODENAME);
		organizationAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		organizationAttachmentConfigureMap.addNodeFieldMap("fileType",
				String.class);
		organizationAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		seConfigureMapList.add(organizationAttachmentConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
