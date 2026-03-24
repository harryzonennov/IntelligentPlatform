package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.production.model.RepairProdTarSubItem;
import com.company.IntelligentPlatform.production.model.RepairProdTargetItemAttachment;
import com.company.IntelligentPlatform.production.model.RepairProdTargetMatItem;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

import java.util.ArrayList;
import java.util.List;

@Component
public class RepairProdTargetMatItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = RepairProdTargetMatItem.NODENAME, nodeInstId = RepairProdTargetMatItem.NODENAME)
	protected RepairProdTargetMatItemUIModel repairProdTargetMatItemUIModel;
	
	@IServiceUIModuleFieldConfig(nodeName = RepairProdTarSubItem.NODENAME, nodeInstId = RepairProdTarSubItem.NODENAME)
	protected List<RepairProdTarSubItemUIModel> repairProdTarSubItemUIModelList = new ArrayList<RepairProdTarSubItemUIModel>();

	@IServiceUIModuleFieldConfig(nodeName = RepairProdTargetItemAttachment.NODENAME, nodeInstId = RepairProdTargetItemAttachment.NODENAME)
	protected List<RepairProdTargetItemAttachmentUIModel> repairProdTargetItemAttachmentUIModelList = new ArrayList<>();

	public RepairProdTargetMatItemUIModel getRepairProdTargetMatItemUIModel() {
		return this.repairProdTargetMatItemUIModel;
	}

	public void setRepairProdTargetMatItemUIModel(
			RepairProdTargetMatItemUIModel repairProdTargetMatItemUIModel) {
		this.repairProdTargetMatItemUIModel = repairProdTargetMatItemUIModel;
	}

	public List<RepairProdTarSubItemUIModel> getRepairProdTarSubItemUIModelList() {
		return repairProdTarSubItemUIModelList;
	}

	public void setRepairProdTarSubItemUIModelList(
			List<RepairProdTarSubItemUIModel> repairProdTarSubItemUIModelList) {
		this.repairProdTarSubItemUIModelList = repairProdTarSubItemUIModelList;
	}

	public List<RepairProdTargetItemAttachmentUIModel> getRepairProdTargetItemAttachmentUIModelList() {
		return repairProdTargetItemAttachmentUIModelList;
	}

	public void setRepairProdTargetItemAttachmentUIModelList(List<RepairProdTargetItemAttachmentUIModel> repairProdTargetItemAttachmentUIModelList) {
		this.repairProdTargetItemAttachmentUIModelList = repairProdTargetItemAttachmentUIModelList;
	}
}
