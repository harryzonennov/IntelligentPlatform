package com.company.IntelligentPlatform.production.dto;


import com.company.IntelligentPlatform.production.model.RepairProdItemReqProposal;
import com.company.IntelligentPlatform.production.model.RepairProdOrderItem;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

import java.util.ArrayList;
import java.util.List;

@Component
public class RepairProdOrderItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = RepairProdOrderItem.NODENAME, nodeInstId = RepairProdOrderItem.NODENAME)
	protected RepairProdOrderItemUIModel repairProdOrderItemUIModel;

	@IServiceUIModuleFieldConfig(nodeName = RepairProdItemReqProposal.NODENAME, nodeInstId = RepairProdItemReqProposal.NODENAME)
	protected List<RepairProdItemReqProposalServiceUIModel> repairProdItemReqProposalUIModelList = new ArrayList<RepairProdItemReqProposalServiceUIModel>();

	public RepairProdOrderItemUIModel getRepairProdOrderItemUIModel() {
		return this.repairProdOrderItemUIModel;
	}

	public void setRepairProdOrderItemUIModel(
			RepairProdOrderItemUIModel repairProdOrderItemUIModel) {
		this.repairProdOrderItemUIModel = repairProdOrderItemUIModel;
	}

	public List<RepairProdItemReqProposalServiceUIModel> getRepairProdItemReqProposalUIModelList() {
		return repairProdItemReqProposalUIModelList;
	}

	public void setRepairProdItemReqProposalUIModelList(
			List<RepairProdItemReqProposalServiceUIModel> repairProdItemReqProposalUIModelList) {
		this.repairProdItemReqProposalUIModelList = repairProdItemReqProposalUIModelList;
	}

}
