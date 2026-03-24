package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.dto.ProdOrderReportItemUIModel;
import com.company.IntelligentPlatform.production.dto.ProdOrderReportUIModel;
import com.company.IntelligentPlatform.production.service.ProductionOrderManager;
import com.company.IntelligentPlatform.production.model.ProdOrderReport;
import com.company.IntelligentPlatform.production.model.ProdOrderReportAttachment;
import com.company.IntelligentPlatform.production.model.ProdOrderReportItem;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class ProdOrderReportServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ProdOrderReport.NODENAME, nodeInstId = ProdOrderReport.NODENAME)
	protected ProdOrderReportUIModel prodOrderReportUIModel;

	@IServiceUIModuleFieldConfig(nodeName = ProdOrderReportItem.NODENAME, nodeInstId = ProdOrderReportItem.NODENAME)
	protected List<ProdOrderReportItemUIModel> prodOrderReportItemUIModelList = new ArrayList<ProdOrderReportItemUIModel>();

	@IServiceUIModuleFieldConfig(nodeName = ProdOrderReportAttachment.NODENAME, nodeInstId = ProdOrderReportAttachment.NODENAME)
	protected List<ProdOrderReportAttachmentUIModel> prodOrderReportAttachmentUIModelList = new ArrayList<ProdOrderReportAttachmentUIModel>();

	public ProdOrderReportUIModel getProdOrderReportUIModel() {
		return this.prodOrderReportUIModel;
	}

	public void setProdOrderReportUIModel(
			ProdOrderReportUIModel prodOrderReportUIModel) {
		this.prodOrderReportUIModel = prodOrderReportUIModel;
	}

	public List<ProdOrderReportItemUIModel> getProdOrderReportItemUIModelList() {
		return this.prodOrderReportItemUIModelList;
	}

	public void setProdOrderReportItemUIModelList(
			List<ProdOrderReportItemUIModel> prodOrderReportItemUIModelList) {
		this.prodOrderReportItemUIModelList = prodOrderReportItemUIModelList;
	}

	public List<ProdOrderReportAttachmentUIModel> getProdOrderReportAttachmentUIModelList() {
		return prodOrderReportAttachmentUIModelList;
	}

	public void setProdOrderReportAttachmentUIModelList(
			List<ProdOrderReportAttachmentUIModel> prodOrderReportAttachmentUIModelList) {
		this.prodOrderReportAttachmentUIModelList = prodOrderReportAttachmentUIModelList;
	}

}
