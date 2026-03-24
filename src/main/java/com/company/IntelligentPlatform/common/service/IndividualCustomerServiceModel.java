package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.CorporateContactPerson;
import com.company.IntelligentPlatform.common.model.IndividualCustomerAttachment;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class IndividualCustomerServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = IndividualCustomer.NODENAME, nodeInstId = IndividualCustomer.SENAME)
	protected IndividualCustomer individualCustomer;

	@IServiceModuleFieldConfig(nodeName = CorporateContactPerson.NODENAME, nodeInstId = CorporateContactPerson.NODENAME)
	protected List<ServiceEntityNode> corporateContactPersonList = new ArrayList<>();
	
	@IServiceModuleFieldConfig(nodeName = IndividualCustomerAttachment.NODENAME , nodeInstId = IndividualCustomerAttachment.NODENAME)
	protected List<ServiceEntityNode> individualCustomerAttachmentList = new ArrayList<>();


	public List<ServiceEntityNode> getCorporateContactPersonList() {
		return this.corporateContactPersonList;
	}

	public void setCorporateContactPersonList(
			List<ServiceEntityNode> corporateContactPersonList) {
		this.corporateContactPersonList = corporateContactPersonList;
	}


	public List<ServiceEntityNode> getIndividualCustomerAttachmentList() {
		return individualCustomerAttachmentList;
	}

	public void setIndividualCustomerAttachmentList(
			List<ServiceEntityNode> individualCustomerAttachmentList) {
		this.individualCustomerAttachmentList = individualCustomerAttachmentList;
	}

	public IndividualCustomer getIndividualCustomer() {
		return individualCustomer;
	}

	public void setIndividualCustomer(IndividualCustomer individualCustomer) {
		this.individualCustomer = individualCustomer;
	}

}
