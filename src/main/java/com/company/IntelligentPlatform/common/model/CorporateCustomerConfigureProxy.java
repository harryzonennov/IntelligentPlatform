package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [CorporateCustomer]
 * 
 * @author
 * @date Wed Nov 04 14:49:12 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class CorporateCustomerConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of CorporateCustomer [ROOT] node
		ServiceEntityConfigureMap corporateCustomerConfigureMap = new ServiceEntityConfigureMap();
		corporateCustomerConfigureMap.setParentNodeName(" ");
		corporateCustomerConfigureMap.setNodeName(CorporateCustomer.NODENAME);
		corporateCustomerConfigureMap.setNodeType(CorporateCustomer.class);
		corporateCustomerConfigureMap.setTableName(CorporateCustomer.SENAME);
		corporateCustomerConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		corporateCustomerConfigureMap.addNodeFieldMap("accountType", int.class);
		corporateCustomerConfigureMap.addNodeFieldMap("address",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("telephone",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("mobile",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("fax",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("email",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("webPage",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("postcode",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("stateName",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("countryName",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("cityName",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("refCityUUID",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("townZone",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("subArea",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("streetName",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("houseNumber",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("regularType", int.class);
		corporateCustomerConfigureMap.addNodeFieldMap("customerLevel", int.class);
		corporateCustomerConfigureMap.addNodeFieldMap("addressOnMap",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("fullName",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("longitude",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("latitude",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("baseCityUUID",
				java.lang.String.class);
		corporateCustomerConfigureMap
				.addNodeFieldMap("customerType", int.class);
		corporateCustomerConfigureMap.addNodeFieldMap("weiboID",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("weiXinID",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("faceBookID",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("status", int.class);
		corporateCustomerConfigureMap.addNodeFieldMap("retireReason",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("retireDate",
				java.util.Date.class);
		corporateCustomerConfigureMap.addNodeFieldMap("launchReason",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("launchDate",
				java.util.Date.class);
		corporateCustomerConfigureMap.addNodeFieldMap("refSalesAreaUUID",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("subDistributorType",
				java.lang.String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("systemDefault",
				boolean.class);
		corporateCustomerConfigureMap.addNodeFieldMap("tags", String.class);
		corporateCustomerConfigureMap
				.addNodeFieldMap("taxNumber", String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("bankAccount",
				String.class);
		corporateCustomerConfigureMap.addNodeFieldMap("depositBank",
				String.class);
		seConfigureMapList.add(corporateCustomerConfigureMap);
		// Init configuration of CorporateCustomer [CorporateContactPerson] node
		ServiceEntityConfigureMap corporateContactPersonConfigureMap = new ServiceEntityConfigureMap();
		corporateContactPersonConfigureMap
				.setParentNodeName(CorporateCustomer.NODENAME);
		corporateContactPersonConfigureMap
				.setNodeName(CorporateContactPerson.NODENAME);
		corporateContactPersonConfigureMap
				.setNodeType(CorporateContactPerson.class);
		corporateContactPersonConfigureMap
				.setTableName(CorporateContactPerson.NODENAME);
		corporateContactPersonConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		corporateContactPersonConfigureMap.addNodeFieldMap("contactRole",
				int.class);
		corporateContactPersonConfigureMap.addNodeFieldMap("contactRoleNote",
				java.lang.String.class);
		corporateContactPersonConfigureMap.addNodeFieldMap("contactPosition",
				int.class);
		corporateContactPersonConfigureMap.addNodeFieldMap(
				"contactPositionNote", java.lang.String.class);
		seConfigureMapList.add(corporateContactPersonConfigureMap);
		// Init configuration of CorporateCustomer [CustomerParentOrgReference] node
		ServiceEntityConfigureMap customerParentOrgReferenceConfigureMap = new ServiceEntityConfigureMap();
		customerParentOrgReferenceConfigureMap
				.setParentNodeName(CorporateCustomer.NODENAME);
		customerParentOrgReferenceConfigureMap
				.setNodeName(CustomerParentOrgReference.NODENAME);
		customerParentOrgReferenceConfigureMap
				.setNodeType(CustomerParentOrgReference.class);
		customerParentOrgReferenceConfigureMap
				.setTableName(CustomerParentOrgReference.NODENAME);
		customerParentOrgReferenceConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		seConfigureMapList.add(customerParentOrgReferenceConfigureMap);


		// Init configuration of CorporateCustomer [customerCustomerAttachment] node
		ServiceEntityConfigureMap customerCustomerAttachmentConfigureMap = new ServiceEntityConfigureMap();
		customerCustomerAttachmentConfigureMap
				.setParentNodeName(CorporateCustomer.NODENAME);
		customerCustomerAttachmentConfigureMap
				.setNodeName(CorporateCustomerAttachment.NODENAME);
		customerCustomerAttachmentConfigureMap
				.setNodeType(CorporateCustomerAttachment.class);
		customerCustomerAttachmentConfigureMap
				.setTableName(CorporateCustomerAttachment.NODENAME);
		customerCustomerAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		customerCustomerAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		customerCustomerAttachmentConfigureMap.addNodeFieldMap("fileType",
				java.lang.String.class);
		seConfigureMapList.add(customerCustomerAttachmentConfigureMap);

		// Init configuration of Material [CorporateCustomerActionNode] node
		ServiceEntityConfigureMap corporateCustomerActionNode = new ServiceEntityConfigureMap();
		corporateCustomerActionNode.setParentNodeName(CorporateCustomer.NODENAME);
		corporateCustomerActionNode.setNodeName(CorporateCustomerActionNode.NODENAME);
		corporateCustomerActionNode.setNodeType(CorporateCustomerActionNode.class);
		corporateCustomerActionNode
				.setTableName(CorporateCustomerActionNode.NODENAME);
		corporateCustomerActionNode.setFieldList(super
				.getBasicActionCodeNodeMap());
		seConfigureMapList.add(corporateCustomerActionNode);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
