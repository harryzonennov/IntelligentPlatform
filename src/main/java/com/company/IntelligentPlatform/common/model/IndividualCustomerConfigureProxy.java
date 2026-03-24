package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [IndividualCustomer]
 * 
 * @author
 * @date Fri Aug 01 17:19:43 CST 2014
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class IndividualCustomerConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of IndividualCustomer [ROOT] node
		ServiceEntityConfigureMap individualCustomerConfigureMap = new ServiceEntityConfigureMap();
		individualCustomerConfigureMap.setParentNodeName(" ");
		individualCustomerConfigureMap.setNodeName("ROOT");
		individualCustomerConfigureMap.setNodeType(IndividualCustomer.class);
		individualCustomerConfigureMap.setTableName("IndividualCustomer");
		individualCustomerConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		individualCustomerConfigureMap
				.addNodeFieldMap("accountType", int.class);
		individualCustomerConfigureMap.addNodeFieldMap("address",
				java.lang.String.class);
		individualCustomerConfigureMap.addNodeFieldMap("telephone",
				java.lang.String.class);
		individualCustomerConfigureMap.addNodeFieldMap("fax",
				java.lang.String.class);
		individualCustomerConfigureMap.addNodeFieldMap("email",
				java.lang.String.class);
		individualCustomerConfigureMap.addNodeFieldMap("webPage",
				java.lang.String.class);
		individualCustomerConfigureMap.addNodeFieldMap("postcode",
				java.lang.String.class);
		individualCustomerConfigureMap.addNodeFieldMap("stateName",
				java.lang.String.class);
		individualCustomerConfigureMap.addNodeFieldMap("countryName",
				java.lang.String.class);
		individualCustomerConfigureMap.addNodeFieldMap("cityName",
				java.lang.String.class);
		individualCustomerConfigureMap.addNodeFieldMap("refCityUUID",
				java.lang.String.class);
		individualCustomerConfigureMap.addNodeFieldMap("townZone",
				java.lang.String.class);
		individualCustomerConfigureMap.addNodeFieldMap("subArea",
				java.lang.String.class);
		individualCustomerConfigureMap.addNodeFieldMap("streetName",
				java.lang.String.class);
		individualCustomerConfigureMap.addNodeFieldMap("houseNumber",
				java.lang.String.class);
		individualCustomerConfigureMap.addNodeFieldMap("mobile",
				java.lang.String.class);
		individualCustomerConfigureMap.addNodeFieldMap("customerType",
				int.class);
		individualCustomerConfigureMap.addNodeFieldMap("qqNumber",
				java.lang.String.class);
		individualCustomerConfigureMap.addNodeFieldMap("weiboID",
				java.lang.String.class);
		individualCustomerConfigureMap.addNodeFieldMap("weiXinID",
				java.lang.String.class);
		individualCustomerConfigureMap.addNodeFieldMap("faceBookID",
				java.lang.String.class);
		individualCustomerConfigureMap
				.addNodeFieldMap("regularType", int.class);
		individualCustomerConfigureMap.addNodeFieldMap("taxNumber",
				java.lang.String.class);
		individualCustomerConfigureMap.addNodeFieldMap("bankAccount",
				java.lang.String.class);
		individualCustomerConfigureMap.addNodeFieldMap("depositBank",
				java.lang.String.class);
		individualCustomerConfigureMap.addNodeFieldMap("baseCityUUID",
				java.lang.String.class);
		seConfigureMapList.add(individualCustomerConfigureMap);
		
		// Init configuration of IndividualCustomer [individualCustomerAttachment] node
				ServiceEntityConfigureMap individualCustomerAttachmentConfigureMap = new ServiceEntityConfigureMap();
				individualCustomerAttachmentConfigureMap
						.setParentNodeName(IndividualCustomer.NODENAME);
				individualCustomerAttachmentConfigureMap
						.setNodeName(IndividualCustomerAttachment.NODENAME);
				individualCustomerAttachmentConfigureMap
						.setNodeType(IndividualCustomerAttachment.class);
				individualCustomerAttachmentConfigureMap
						.setTableName(IndividualCustomerAttachment.NODENAME);
				individualCustomerAttachmentConfigureMap.setFieldList(super
						.getBasicSENodeFieldMap());
				individualCustomerAttachmentConfigureMap.addNodeFieldMap("content",
						byte[].class);
				individualCustomerAttachmentConfigureMap.addNodeFieldMap("fileType",
						java.lang.String.class);
				seConfigureMapList.add(individualCustomerAttachmentConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
