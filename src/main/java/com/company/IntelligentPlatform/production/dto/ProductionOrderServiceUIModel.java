package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.model.*;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class ProductionOrderServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ProductionOrderItem.NODENAME, nodeInstId = ProductionOrderItem.NODENAME)
	protected List<ProductionOrderItemServiceUIModel> productionOrderItemUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = ProdOrderSupplyWarehouse.NODENAME, nodeInstId = ProdOrderSupplyWarehouse.NODENAME)
	protected List<ProdOrderSupplyWarehouseUIModel> prodOrderSupplyWarehouseUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = ProductionOrderAttachment.NODENAME, nodeInstId = ProductionOrderAttachment.NODENAME)
	protected List<ProductionOrderAttachmentUIModel> productionOrderAttachmentList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = ProdOrderTargetMatItem.NODENAME, nodeInstId = ProdOrderTargetMatItem.NODENAME)
	protected List<ProdOrderTargetMatItemServiceUIModel> prodOrderTargetMatItemList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = ProductionOrderActionNode.NODENAME, nodeInstId =
			ProductionOrderActionNode.NODEINST_ACTION_APPROVE)
	protected ProductionOrderActionNodeUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = ProductionOrderActionNode.NODENAME, nodeInstId = ProductionOrderActionNode.NODEINST_ACTION_COUNTAPPROVE)
	protected ProductionOrderActionNodeUIModel countApprovedBy;

	@IServiceUIModuleFieldConfig(nodeName = ProductionOrderActionNode.NODENAME, nodeInstId =
			ProductionOrderActionNode.NODEINST_ACTION_SUBMIT)
	protected ProductionOrderActionNodeUIModel submittedBy;

	@IServiceUIModuleFieldConfig(nodeName = ProductionOrderActionNode.NODENAME, nodeInstId =
			ProductionOrderActionNode.NODEINST_ACTION_REVOKE_SUBMIT)
	protected ProductionOrderActionNodeUIModel revokeSubmittedBy;

	@IServiceUIModuleFieldConfig(nodeName = ProductionOrderActionNode.NODENAME, nodeInstId =
			ProductionOrderActionNode.NODEINST_ACTION_REJECT_APPROVE)
	protected ProductionOrderActionNodeUIModel rejectApprovedBy;

	@IServiceUIModuleFieldConfig(nodeName = ProductionOrderActionNode.NODENAME, nodeInstId =
			ProductionOrderActionNode.NODEINST_ACTION_INPRODUCTION)
	protected ProductionOrderActionNodeUIModel inProductionBy;

	@IServiceUIModuleFieldConfig(nodeName = ProductionOrderActionNode.NODENAME, nodeInstId =
			ProductionOrderActionNode.NODEINST_ACTION_FINISHED)
	protected ProductionOrderActionNodeUIModel finishedBy;

	@IServiceUIModuleFieldConfig(nodeName = ProductionOrder.NODENAME, nodeInstId = ProductionOrder.SENAME)
	protected ProductionOrderUIModel productionOrderUIModel;

	public List<ProductionOrderItemServiceUIModel> getProductionOrderItemUIModelList() {
		return productionOrderItemUIModelList;
	}

	public void setProductionOrderItemUIModelList(
			List<ProductionOrderItemServiceUIModel> productionOrderItemUIModelList) {
		this.productionOrderItemUIModelList = productionOrderItemUIModelList;
	}

	public List<ProdOrderSupplyWarehouseUIModel> getProdOrderSupplyWarehouseUIModelList() {
		return prodOrderSupplyWarehouseUIModelList;
	}

	public void setProdOrderSupplyWarehouseUIModelList(
			List<ProdOrderSupplyWarehouseUIModel> prodOrderSupplyWarehouseUIModelList) {
		this.prodOrderSupplyWarehouseUIModelList = prodOrderSupplyWarehouseUIModelList;
	}

	public ProductionOrderUIModel getProductionOrderUIModel() {
		return productionOrderUIModel;
	}

	public void setProductionOrderUIModel(
			ProductionOrderUIModel productionOrderUIModel) {
		this.productionOrderUIModel = productionOrderUIModel;
	}

	public List<ProductionOrderAttachmentUIModel> getProductionOrderAttachmentList() {
		return productionOrderAttachmentList;
	}

	public void setProductionOrderAttachmentList(
			List<ProductionOrderAttachmentUIModel> productionOrderAttachmentList) {
		this.productionOrderAttachmentList = productionOrderAttachmentList;
	}

	public List<ProdOrderTargetMatItemServiceUIModel> getProdOrderTargetMatItemList() {
		return prodOrderTargetMatItemList;
	}

	public void setProdOrderTargetMatItemList(List<ProdOrderTargetMatItemServiceUIModel> prodOrderTargetMatItemList) {
		this.prodOrderTargetMatItemList = prodOrderTargetMatItemList;
	}

	public ProductionOrderActionNodeUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(ProductionOrderActionNodeUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public ProductionOrderActionNodeUIModel getCountApprovedBy() {
		return countApprovedBy;
	}

	public void setCountApprovedBy(ProductionOrderActionNodeUIModel countApprovedBy) {
		this.countApprovedBy = countApprovedBy;
	}

	public ProductionOrderActionNodeUIModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(ProductionOrderActionNodeUIModel submittedBy) {
		this.submittedBy = submittedBy;
	}

	public ProductionOrderActionNodeUIModel getRevokeSubmittedBy() {
		return revokeSubmittedBy;
	}

	public void setRevokeSubmittedBy(ProductionOrderActionNodeUIModel revokeSubmittedBy) {
		this.revokeSubmittedBy = revokeSubmittedBy;
	}

	public ProductionOrderActionNodeUIModel getRejectApprovedBy() {
		return rejectApprovedBy;
	}

	public void setRejectApprovedBy(ProductionOrderActionNodeUIModel rejectApprovedBy) {
		this.rejectApprovedBy = rejectApprovedBy;
	}

	public ProductionOrderActionNodeUIModel getInProductionBy() {
		return inProductionBy;
	}

	public void setInProductionBy(ProductionOrderActionNodeUIModel inProductionBy) {
		this.inProductionBy = inProductionBy;
	}

	public ProductionOrderActionNodeUIModel getFinishedBy() {
		return finishedBy;
	}

	public void setFinishedBy(ProductionOrderActionNodeUIModel finishedBy) {
		this.finishedBy = finishedBy;
	}
}
