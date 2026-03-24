package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.model.ProdOrderTargetItemAttachment;
import com.company.IntelligentPlatform.production.model.ProdOrderTargetMatItem;
import com.company.IntelligentPlatform.production.model.ProdOrderTarSubItem;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class ProdOrderTargetMatItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ProdOrderTargetMatItem.NODENAME, nodeInstId = ProdOrderTargetMatItem.NODENAME)
	protected ProdOrderTargetMatItemUIModel prodOrderTargetMatItemUIModel;
	
	@IServiceUIModuleFieldConfig(nodeName = ProdOrderTarSubItem.NODENAME, nodeInstId = ProdOrderTarSubItem.NODENAME)
	protected List<ProdOrderTarSubItemServiceUIModel> prodOrderTarSubItemUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = ProdOrderTargetItemAttachment.NODENAME, nodeInstId = ProdOrderTargetItemAttachment.NODENAME)
	protected List<ProdOrderTargetItemAttachmentUIModel> prodOrderTargetItemAttachmentUIModelList = new ArrayList<>();

	public ProdOrderTargetMatItemUIModel getProdOrderTargetMatItemUIModel() {
		return this.prodOrderTargetMatItemUIModel;
	}

	public void setProdOrderTargetMatItemUIModel(
			ProdOrderTargetMatItemUIModel prodOrderTargetMatItemUIModel) {
		this.prodOrderTargetMatItemUIModel = prodOrderTargetMatItemUIModel;
	}

	public List<ProdOrderTarSubItemServiceUIModel> getProdOrderTarSubItemUIModelList() {
		return prodOrderTarSubItemUIModelList;
	}

	public void setProdOrderTarSubItemUIModelList(
			List<ProdOrderTarSubItemServiceUIModel> prodOrderTarSubItemUIModelList) {
		this.prodOrderTarSubItemUIModelList = prodOrderTarSubItemUIModelList;
	}

	public List<ProdOrderTargetItemAttachmentUIModel> getProdOrderTargetItemAttachmentUIModelList() {
		return prodOrderTargetItemAttachmentUIModelList;
	}

	public void setProdOrderTargetItemAttachmentUIModelList(List<ProdOrderTargetItemAttachmentUIModel> prodOrderTargetItemAttachmentUIModelList) {
		this.prodOrderTargetItemAttachmentUIModelList = prodOrderTargetItemAttachmentUIModelList;
	}
}
