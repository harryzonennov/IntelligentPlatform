package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.model.ProdOrderTarSubItem;
import com.company.IntelligentPlatform.production.model.ProdOrderTargetItemAttachment;
import com.company.IntelligentPlatform.production.model.ProdOrderTargetMatItem;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class ProdOrderTargetMatItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ProdOrderTargetMatItem.NODENAME, nodeInstId = ProdOrderTargetMatItem.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected ProdOrderTargetMatItem prodOrderTargetMatItem;

	@IServiceModuleFieldConfig(nodeName = ProdOrderTarSubItem.NODENAME, nodeInstId = ProdOrderTarSubItem.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected List<ProdOrderTarSubItemServiceModel> prodOrderTarSubItemList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = ProdOrderTargetItemAttachment.NODENAME, nodeInstId = ProdOrderTargetItemAttachment.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> prodOrderTargetItemAttachmentList = new ArrayList<>();

	public ProdOrderTargetMatItem getProdOrderTargetMatItem() {
		return prodOrderTargetMatItem;
	}

	public void setProdOrderTargetMatItem(ProdOrderTargetMatItem prodOrderTargetMatItem) {
		this.prodOrderTargetMatItem = prodOrderTargetMatItem;
	}

	public List<ProdOrderTarSubItemServiceModel> getProdOrderTarSubItemList() {
		return prodOrderTarSubItemList;
	}

	public void setProdOrderTarSubItemList(
			List<ProdOrderTarSubItemServiceModel> prodOrderTarSubItemList) {
		this.prodOrderTarSubItemList = prodOrderTarSubItemList;
	}

	public List<ServiceEntityNode> getProdOrderTargetItemAttachmentList() {
		return prodOrderTargetItemAttachmentList;
	}

	public void setProdOrderTargetItemAttachmentList(List<ServiceEntityNode> prodOrderTargetItemAttachmentList) {
		this.prodOrderTargetItemAttachmentList = prodOrderTargetItemAttachmentList;
	}
}
