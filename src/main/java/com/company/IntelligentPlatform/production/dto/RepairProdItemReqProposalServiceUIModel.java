package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.production.model.RepairProdItemReqProposal;
import com.company.IntelligentPlatform.production.model.RepairProdOrderItem;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

import java.util.List;

@Component
public class RepairProdItemReqProposalServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = RepairProdItemReqProposal.NODENAME, nodeInstId = RepairProdItemReqProposal.NODENAME)
	protected RepairProdItemReqProposalUIModel prodOrderItemReqProposalUIModel;
	
	@IServiceUIModuleFieldConfig(nodeName = RepairProdOrderItem.NODENAME, nodeInstId = RepairProdOrderItem.NODENAME)
	protected List<RepairProdOrderItemServiceUIModel> repairProdOrderItemUIModelList;

	public RepairProdItemReqProposalUIModel getRepairProdItemReqProposalUIModel() {
		return prodOrderItemReqProposalUIModel;
	}

	public void setRepairProdItemReqProposalUIModel(
			RepairProdItemReqProposalUIModel prodOrderItemReqProposalUIModel) {
		this.prodOrderItemReqProposalUIModel = prodOrderItemReqProposalUIModel;
	}

	public List<RepairProdOrderItemServiceUIModel> getRepairProdOrderItemUIModelList() {
		return repairProdOrderItemUIModelList;
	}

	public void setRepairProdOrderItemUIModelList(
			List<RepairProdOrderItemServiceUIModel> repairProdOrderItemUIModelList) {
		this.repairProdOrderItemUIModelList = repairProdOrderItemUIModelList;
	}

}
