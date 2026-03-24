package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.CorporateDealerSearchModel;
import com.company.IntelligentPlatform.common.dto.CorporateDistributorSearchModel;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.SearchNodeMapping;
import com.company.IntelligentPlatform.common.service.SystemConfigureResourceProxy;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.CorporateContactPerson;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.CustomerParentOrgReference;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.City;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

/**
 * This class is service for corporate customer complicated search configure
 * 
 * @author Zhang,Hang
 * 
 */
@Service
public class CorporateCustomerSearchHelpProxy {

	@Autowired
	protected SystemConfigureResourceProxy systemConfigureResourceProxy;

	public List<BSearchNodeComConfigure> getCorporateCustomerCoreNodeConfigure(
			String client) throws ServiceEntityConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// start node:[Corporate customer root node]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(CorporateCustomer.SENAME);
		searchNodeConfig0.setNodeName(CorporateCustomer.NODENAME);
		searchNodeConfig0.setNodeInstID(CorporateCustomer.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		// search node [Corporate customer->corporate contact person]
		BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
		searchNodeConfig1.setSeName(CorporateContactPerson.SENAME);
		searchNodeConfig1.setNodeName(CorporateContactPerson.NODENAME);
		searchNodeConfig1.setNodeInstID(CorporateContactPerson.NODENAME);
		searchNodeConfig1
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig1.setBaseNodeInstID(CorporateCustomer.SENAME);
		searchNodeConfigList.add(searchNodeConfig1);
		// search node [Corporate contact person->Individual Customer]
		BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
		searchNodeConfig2.setSeName(IndividualCustomer.SENAME);
		searchNodeConfig2.setNodeName(IndividualCustomer.NODENAME);
		searchNodeConfig2.setNodeInstID(IndividualCustomer.SENAME);
		searchNodeConfig2
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE);
		searchNodeConfig2.setBaseNodeInstID(CorporateContactPerson.NODENAME);
		searchNodeConfigList.add(searchNodeConfig2);
		// search node [City]
		BSearchNodeComConfigure searchNodeConfig3 = new BSearchNodeComConfigure();
		searchNodeConfig3.setSeName(City.SENAME);
		searchNodeConfig3.setNodeName(City.NODENAME);
		searchNodeConfig3.setNodeInstID(City.SENAME);
		searchNodeConfig3
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig3.setMapBaseFieldName("cityName");
		searchNodeConfig3
				.setMapSourceFieldName(IServiceEntityNodeFieldConstant.NAME);
		searchNodeConfig3.setBaseNodeInstID(CorporateCustomer.SENAME);
		searchNodeConfigList.add(searchNodeConfig3);

		BSearchNodeComConfigure searchNodeConfig4 = new BSearchNodeComConfigure();
		searchNodeConfig4.setSeName(LogonUser.SENAME);
		searchNodeConfig4.setNodeName(LogonUser.NODENAME);
		searchNodeConfig4.setNodeInstID(LogonUser.SENAME);
		searchNodeConfig4
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig4.setMapBaseFieldName("resEmployeeUUID");
		searchNodeConfig4
				.setMapSourceFieldName(IServiceEntityNodeFieldConstant.UUID);
		searchNodeConfig4.setBaseNodeInstID(CorporateCustomer.SENAME);
		searchNodeConfigList.add(searchNodeConfig4);
		// search node [Corporate customer-->res Organization]
		BSearchNodeComConfigure searchNodeConfig5 = new BSearchNodeComConfigure();
		searchNodeConfig5.setSeName(Organization.SENAME);
		searchNodeConfig5.setNodeName(Organization.NODENAME);
		searchNodeConfig5.setNodeInstID(Organization.SENAME);
		searchNodeConfig5
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig5.setMapBaseFieldName("resOrgUUID");
		searchNodeConfig5
				.setMapSourceFieldName(IServiceEntityNodeFieldConstant.UUID);
		searchNodeConfig5.setBaseNodeInstID(CorporateCustomer.SENAME);
		searchNodeConfigList.add(searchNodeConfig5);
		// search node [SalesArea]
		BSearchNodeComConfigure searchNodeConfig6 = new BSearchNodeComConfigure();
		searchNodeConfig6.setSeName(IServiceModelConstants.SalesArea);
		searchNodeConfig6.setNodeName(ServiceEntityNode.NODENAME_ROOT);
		searchNodeConfig6.setNodeInstID(IServiceModelConstants.SalesArea);
		searchNodeConfig6
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig6.setMapBaseFieldName("refSalesAreaUUID");
		searchNodeConfig6
				.setMapSourceFieldName(IServiceEntityNodeFieldConstant.UUID);
		searchNodeConfig6.setBaseNodeInstID(CorporateCustomer.SENAME);
		searchNodeConfigList.add(searchNodeConfig6);
		return searchNodeConfigList;
	}

	public List<BSearchNodeComConfigure> getCorporateDealerNodeConfigure(
			String client) throws ServiceEntityConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = getCorporateCustomerCoreNodeConfigure(client);
		// search node for parent distributor
		BSearchNodeComConfigure searchNodeConfig6 = new BSearchNodeComConfigure();
		searchNodeConfig6.setSeName(CustomerParentOrgReference.SENAME);
		searchNodeConfig6.setNodeName(CustomerParentOrgReference.NODENAME);
		searchNodeConfig6.setNodeInstID(CustomerParentOrgReference.NODENAME);
		searchNodeConfig6
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig6.setBaseNodeInstID(CorporateCustomer.SENAME);
		searchNodeConfigList.add(searchNodeConfig6);
		// search node for parent distributor
		BSearchNodeComConfigure searchNodeConfig7 = new BSearchNodeComConfigure();
		searchNodeConfig7.setSeName(CorporateCustomer.SENAME);
		searchNodeConfig7.setNodeName(CorporateCustomer.NODENAME);
		searchNodeConfig7
				.setNodeInstID(CorporateDealerSearchModel.NODE_PARENT_DISTRIBUTOR);
		searchNodeConfig7
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE);
		searchNodeConfig7
				.setBaseNodeInstID(CustomerParentOrgReference.NODENAME);
		searchNodeConfigList.add(searchNodeConfig7);

		return searchNodeConfigList;
	}

	public List<BSearchNodeComConfigure> getCorporateDistributorNodeConfigure(
			String client) throws ServiceEntityConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = getCorporateCustomerCoreNodeConfigure(client);
		// search node for parent distributor
		BSearchNodeComConfigure searchNodeConfig6 = new BSearchNodeComConfigure();
		searchNodeConfig6.setSeName(CustomerParentOrgReference.SENAME);
		searchNodeConfig6.setNodeName(CustomerParentOrgReference.NODENAME);
		searchNodeConfig6.setNodeInstID(CustomerParentOrgReference.NODENAME);
		searchNodeConfig6
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_TARGET);
		searchNodeConfig6.setBaseNodeInstID(CorporateCustomer.SENAME);
		searchNodeConfigList.add(searchNodeConfig6);
		// search node for
		BSearchNodeComConfigure searchNodeConfig7 = new BSearchNodeComConfigure();
		searchNodeConfig7.setSeName(CorporateCustomer.SENAME);
		searchNodeConfig7.setNodeName(CorporateCustomer.NODENAME);
		searchNodeConfig7
				.setNodeInstID(CorporateDistributorSearchModel.NODE_SUB_DEALER);
		searchNodeConfig7
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_CHILD);
		searchNodeConfig7
				.setBaseNodeInstID(CustomerParentOrgReference.NODENAME);
		searchNodeConfigList.add(searchNodeConfig7);
		return searchNodeConfigList;
	}

}
