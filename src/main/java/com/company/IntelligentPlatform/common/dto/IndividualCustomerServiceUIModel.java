package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.service.IndividualCustomerManager;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.CorporateContactPerson;
import com.company.IntelligentPlatform.common.model.IndividualCustomerAttachment;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;

@Component
public class IndividualCustomerServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = IndividualCustomer.NODENAME, nodeInstId = IndividualCustomer.SENAME, convToUIMethod = IndividualCustomerManager.METHOD_ConvIndividualCustomerToUI, convUIToMethod = IndividualCustomerManager.METHOD_ConvUIToIndividualCustomer)
	protected IndividualCustomerUIModel individualCustomerUIModel;
	
	@IServiceUIModuleFieldConfig(nodeName = CorporateContactPerson.NODENAME, nodeInstId = CorporateContactPerson.NODENAME, convToUIMethod = IndividualCustomerManager.METHOD_ConvIndividualCustomerToUI, convUIToMethod = IndividualCustomerManager.METHOD_ConvUIToIndividualCustomer)
	protected List<CorporateContactPersonUIModel> corporateContactPersonUIModelList = new ArrayList<CorporateContactPersonUIModel>();
	
	@IServiceUIModuleFieldConfig(nodeName = IndividualCustomerAttachment.NODENAME, nodeInstId = IndividualCustomerAttachment.NODENAME, convToUIMethod = IndividualCustomerManager.METHOD_ConvIndividualCustomerAttachmentToUI)
	protected List<IndividualCustomerAttachmentUIModel> individualCustomerAttachmentUIModelList = new ArrayList<IndividualCustomerAttachmentUIModel>();
	

	public IndividualCustomerUIModel getIndividualCustomerUIModel() {
		return this.individualCustomerUIModel;
	}

	public void setIndividualCustomerUIModel(
			IndividualCustomerUIModel individualCustomerUIModel) {
		this.individualCustomerUIModel = individualCustomerUIModel;
	}

	public List<CorporateContactPersonUIModel> getCorporateContactPersonUIModelList() {
		return this.corporateContactPersonUIModelList;
	}

	public void setCorporateContactPersonUIModelList(
			List<CorporateContactPersonUIModel> corporateContactPersonUIModelList) {
		this.corporateContactPersonUIModelList = corporateContactPersonUIModelList;
	}

	public List<IndividualCustomerAttachmentUIModel> getIndividualCustomerAttachmentUIModelList() {
		return individualCustomerAttachmentUIModelList;
	}

	public void setIndividualCustomerAttachmentUIModelList(
			List<IndividualCustomerAttachmentUIModel> individualCustomerAttachmentUIModelList) {
		this.individualCustomerAttachmentUIModelList = individualCustomerAttachmentUIModelList;
	}

}
