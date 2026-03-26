package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.model.RepairProdItemReqProposal;
import com.company.IntelligentPlatform.production.model.RepairProdOrderItem;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class RepairProdOrderItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = RepairProdOrderItem.NODENAME, nodeInstId = RepairProdOrderItem.NODENAME)
	protected RepairProdOrderItem repairProdOrderItem;
	
	@IServiceModuleFieldConfig(nodeName = RepairProdItemReqProposal.NODENAME, nodeInstId = RepairProdItemReqProposal.NODENAME)
	protected List<RepairProdItemReqProposalServiceModel> repairProdItemReqProposalList = new ArrayList<RepairProdItemReqProposalServiceModel>();

	public RepairProdOrderItem getRepairProdOrderItem() {
		return this.repairProdOrderItem;
	}

	public void setRepairProdOrderItem(RepairProdOrderItem repairProdOrderItem) {
		this.repairProdOrderItem = repairProdOrderItem;
	}

	public List<RepairProdItemReqProposalServiceModel> getRepairProdItemReqProposalList() {
		return repairProdItemReqProposalList;
	}

	public void setRepairProdItemReqProposalList(
			List<RepairProdItemReqProposalServiceModel> repairProdItemReqProposalList) {
		this.repairProdItemReqProposalList = repairProdItemReqProposalList;
	}

}
