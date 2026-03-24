package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.model.RepairProdTarSubItem;
import com.company.IntelligentPlatform.production.model.RepairProdTargetItemAttachment;
import com.company.IntelligentPlatform.production.model.RepairProdTargetMatItem;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class RepairProdTargetMatItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = RepairProdTargetMatItem.NODENAME, nodeInstId = RepairProdTargetMatItem.NODENAME)
	protected RepairProdTargetMatItem repairProdTargetMatItem;

	@IServiceModuleFieldConfig(nodeName = RepairProdTarSubItem.NODENAME, nodeInstId = RepairProdTarSubItem.NODENAME)
	protected List<ServiceEntityNode> repairProdTarSubItemList = new ArrayList<ServiceEntityNode>();

	@IServiceModuleFieldConfig(nodeName = RepairProdTargetItemAttachment.NODENAME, nodeInstId = RepairProdTargetItemAttachment.NODENAME)
	protected List<ServiceEntityNode> repairProdTargetItemAttachmentList = new ArrayList<ServiceEntityNode>();

	public RepairProdTargetMatItem getRepairProdTargetMatItem() {
		return repairProdTargetMatItem;
	}

	public void setRepairProdTargetMatItem(RepairProdTargetMatItem repairProdTargetMatItem) {
		this.repairProdTargetMatItem = repairProdTargetMatItem;
	}

	public List<ServiceEntityNode> getRepairProdTarSubItemList() {
		return repairProdTarSubItemList;
	}

	public void setRepairProdTarSubItemList(
			List<ServiceEntityNode> repairProdTarSubItemList) {
		this.repairProdTarSubItemList = repairProdTarSubItemList;
	}

	public List<ServiceEntityNode> getRepairProdTargetItemAttachmentList() {
		return repairProdTargetItemAttachmentList;
	}

	public void setRepairProdTargetItemAttachmentList(List<ServiceEntityNode> repairProdTargetItemAttachmentList) {
		this.repairProdTargetItemAttachmentList = repairProdTargetItemAttachmentList;
	}
}
