package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.model.QualityInsMatItemAttachment;
import com.company.IntelligentPlatform.logistics.model.QualityInspectMatItem;
import com.company.IntelligentPlatform.logistics.model.QualityInspectMatItemParty;
import com.company.IntelligentPlatform.logistics.model.QualityInspectPropertyItem;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class QualityInspectMatItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = QualityInspectMatItem.NODENAME, nodeInstId = QualityInspectMatItem.NODENAME)
	protected QualityInspectMatItemUIModel qualityInspectMatItemUIModel;

	@IServiceUIModuleFieldConfig(nodeName = QualityInsMatItemAttachment.NODENAME, nodeInstId = QualityInsMatItemAttachment.NODENAME)
	protected List<QualityInsMatItemAttachmentUIModel> qualityInsMatItemAttachmentUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = QualityInspectPropertyItem.NODENAME, nodeInstId = QualityInspectPropertyItem.NODENAME)
	protected List<QualityInspectPropertyItemServiceUIModel> qualityInspectPropertyItemUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = QualityInspectMatItemParty.NODENAME, nodeInstId = QualityInspectMatItemParty.PARTY_NODEINST_PUR_SUPPLIER)
	protected QualityInspectMatItemPartyUIModel corporateSupplierParty;

	@IServiceUIModuleFieldConfig(nodeName = QualityInspectMatItemParty.NODENAME, nodeInstId = QualityInspectMatItemParty.PARTY_NODEINST_PUR_ORG)
	protected QualityInspectMatItemPartyUIModel purchaseOrgParty;

	@IServiceUIModuleFieldConfig(nodeName = QualityInspectMatItemParty.NODENAME, nodeInstId =
			QualityInspectMatItemParty.PARTY_NODEINST_PROD_ORG)
	protected QualityInspectMatItemPartyUIModel productionOrgParty;

	@IServiceUIModuleFieldConfig(nodeName = QualityInspectMatItemParty.NODENAME, nodeInstId =
			QualityInspectMatItemParty.PARTY_NODEINST_SOLD_CUSTOMER)
	protected QualityInspectMatItemPartyUIModel corporateCustomerParty;

	@IServiceUIModuleFieldConfig(nodeName = QualityInspectMatItemParty.NODENAME, nodeInstId =
			QualityInspectMatItemParty.PARTY_NODEINST_SOLD_ORG)
	protected QualityInspectMatItemPartyUIModel salesOrganizationParty;

	public QualityInspectMatItemUIModel getQualityInspectMatItemUIModel() {
		return this.qualityInspectMatItemUIModel;
	}

	public void setQualityInspectMatItemUIModel(
			QualityInspectMatItemUIModel qualityInspectMatItemUIModel) {
		this.qualityInspectMatItemUIModel = qualityInspectMatItemUIModel;
	}

	public List<QualityInsMatItemAttachmentUIModel> getQualityInsMatItemAttachmentUIModelList() {
		return this.qualityInsMatItemAttachmentUIModelList;
	}

	public void setQualityInsMatItemAttachmentUIModelList(
			List<QualityInsMatItemAttachmentUIModel> qualityInsMatItemAttachmentUIModelList) {
		this.qualityInsMatItemAttachmentUIModelList = qualityInsMatItemAttachmentUIModelList;
	}

	public List<QualityInspectPropertyItemServiceUIModel> getQualityInspectPropertyItemUIModelList() {
		return this.qualityInspectPropertyItemUIModelList;
	}

	public void setQualityInspectPropertyItemUIModelList(
			List<QualityInspectPropertyItemServiceUIModel> qualityInspectPropertyItemUIModelList) {
		this.qualityInspectPropertyItemUIModelList = qualityInspectPropertyItemUIModelList;
	}

}
