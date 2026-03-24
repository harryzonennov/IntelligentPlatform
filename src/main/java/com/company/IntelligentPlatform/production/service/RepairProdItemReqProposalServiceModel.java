package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.model.RepairProdItemReqProposal;
import com.company.IntelligentPlatform.production.model.RepairProdOrderItem;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.List;

public class RepairProdItemReqProposalServiceModel extends ServiceModule {
	
	@IServiceModuleFieldConfig(nodeName = RepairProdOrderItem.NODENAME, nodeInstId = RepairProdOrderItem.NODENAME)
	protected List<RepairProdOrderItemServiceModel> repairProdOrderItemList;
	
	@IServiceModuleFieldConfig(nodeName = RepairProdItemReqProposal.NODENAME, nodeInstId = RepairProdItemReqProposal.NODENAME)
	protected RepairProdItemReqProposal repairProdItemReqProposal;


	public List<RepairProdOrderItemServiceModel> getRepairProdOrderItemList() {
		return repairProdOrderItemList;
	}

	public void setRepairProdOrderItemList(
			List<RepairProdOrderItemServiceModel> repairProdOrderItemList) {
		this.repairProdOrderItemList = repairProdOrderItemList;
	}

	public RepairProdItemReqProposal getRepairProdItemReqProposal() {
		return repairProdItemReqProposal;
	}

	public void setRepairProdItemReqProposal(
			RepairProdItemReqProposal repairProdItemReqProposal) {
		this.repairProdItemReqProposal = repairProdItemReqProposal;
	}


}
