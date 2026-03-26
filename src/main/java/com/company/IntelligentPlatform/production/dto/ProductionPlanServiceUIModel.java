package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.service.ProductionPlanManager;
import com.company.IntelligentPlatform.production.model.*;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class ProductionPlanServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ProductionPlanItem.NODENAME, nodeInstId = ProductionPlanItem.NODENAME)
	protected List<ProductionPlanItemServiceUIModel> productionPlanItemUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = ProdPlanSupplyWarehouse.NODENAME, nodeInstId = ProdPlanSupplyWarehouse.NODENAME)
	protected List<ProdPlanSupplyWarehouseUIModel> prodPlanSupplyWarehouseUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = ProductionPlanAttachment.NODENAME, nodeInstId = ProductionPlanAttachment.NODENAME)
	protected List<ProductionPlanAttachmentUIModel> productionPlanAttachmentList = new ArrayList<>();
	
	@IServiceUIModuleFieldConfig(nodeName = ProdPlanTargetMatItem.NODENAME, nodeInstId = ProdPlanTargetMatItem.NODENAME)
	protected List<ProdPlanTargetMatItemServiceUIModel> prodPlanTargetMatItemList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = ProductionPlanActionNode.NODENAME, nodeInstId =
			ProductionPlanActionNode.NODEINST_ACTION_APPROVE)
	protected ProductionPlanActionNodeUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = ProductionPlanActionNode.NODENAME, nodeInstId = ProductionPlanActionNode.NODEINST_ACTION_COUNTAPPROVE)
	protected ProductionPlanActionNodeUIModel countApprovedBy;

	@IServiceUIModuleFieldConfig(nodeName = ProductionPlanActionNode.NODENAME, nodeInstId =
			ProductionPlanActionNode.NODEINST_ACTION_SUBMIT)
	protected ProductionPlanActionNodeUIModel submittedBy;

	@IServiceUIModuleFieldConfig(nodeName = ProductionPlanActionNode.NODENAME, nodeInstId =
			ProductionPlanActionNode.NODEINST_ACTION_REVOKE_SUBMIT)
	protected ProductionPlanActionNodeUIModel revokeSubmittedBy;

	@IServiceUIModuleFieldConfig(nodeName = ProductionPlanActionNode.NODENAME, nodeInstId =
			ProductionPlanActionNode.NODEINST_ACTION_REJECT_APPROVE)
	protected ProductionPlanActionNodeUIModel rejectApprovedBy;

	@IServiceUIModuleFieldConfig(nodeName = ProductionPlanActionNode.NODENAME, nodeInstId =
			ProductionPlanActionNode.NODEINST_ACTION_INPRODUCTION)
	protected ProductionPlanActionNodeUIModel inProductionBy;

	@IServiceUIModuleFieldConfig(nodeName = ProductionPlanActionNode.NODENAME, nodeInstId =
			ProductionPlanActionNode.NODEINST_ACTION_FINISHED)
	protected ProductionPlanActionNodeUIModel finishedBy;

	@IServiceUIModuleFieldConfig(nodeName = ProductionPlan.NODENAME, nodeInstId = ProductionPlan.SENAME, convToUIMethod = ProductionPlanManager.METHOD_ConvProductionPlanToUI, convUIToMethod = ProductionPlanManager.METHOD_ConvUIToProductionPlan)
	protected ProductionPlanUIModel productionPlanUIModel;

	@IServiceUIModuleFieldConfig(nodeName = ProductionPlanParty.NODENAME, nodeInstId = ProductionPlanParty.PARTY_NODEINST_PUR_ORG)
	protected ProductionPlanPartyUIModel purchaseOrgParty;

	@IServiceUIModuleFieldConfig(nodeName = ProductionPlanParty.NODENAME, nodeInstId =
			ProductionPlanParty.PARTY_NODEINST_PROD_ORG)
	protected ProductionPlanPartyUIModel productionOrgParty;

	@IServiceUIModuleFieldConfig(nodeName = ProductionPlanParty.NODENAME, nodeInstId =
			ProductionPlanParty.PARTY_NODEINST_SOLD_CUSTOMER)
	protected ProductionPlanPartyUIModel corporateCustomerParty;

	@IServiceUIModuleFieldConfig(nodeName = ProductionPlanParty.NODENAME, nodeInstId =
			ProductionPlanParty.PARTY_NODEINST_SOLD_ORG)
	protected ProductionPlanPartyUIModel salesOrganizationParty;	

	public List<ProductionPlanItemServiceUIModel> getProductionPlanItemUIModelList() {
		return productionPlanItemUIModelList;
	}

	public void setProductionPlanItemUIModelList(
			List<ProductionPlanItemServiceUIModel> productionPlanItemUIModelList) {
		this.productionPlanItemUIModelList = productionPlanItemUIModelList;
	}

	public List<ProdPlanSupplyWarehouseUIModel> getProdPlanSupplyWarehouseUIModelList() {
		return prodPlanSupplyWarehouseUIModelList;
	}

	public void setProdPlanSupplyWarehouseUIModelList(
			List<ProdPlanSupplyWarehouseUIModel> prodPlanSupplyWarehouseUIModelList) {
		this.prodPlanSupplyWarehouseUIModelList = prodPlanSupplyWarehouseUIModelList;
	}

	public ProductionPlanUIModel getProductionPlanUIModel() {
		return productionPlanUIModel;
	}

	public void setProductionPlanUIModel(
			ProductionPlanUIModel productionPlanUIModel) {
		this.productionPlanUIModel = productionPlanUIModel;
	}

	public List<ProductionPlanAttachmentUIModel> getProductionPlanAttachmentList() {
		return productionPlanAttachmentList;
	}

	public void setProductionPlanAttachmentList(
			List<ProductionPlanAttachmentUIModel> productionPlanAttachmentList) {
		this.productionPlanAttachmentList = productionPlanAttachmentList;
	}

	public List<ProdPlanTargetMatItemServiceUIModel> getProdPlanTargetMatItemList() {
		return prodPlanTargetMatItemList;
	}

	public void setProdPlanTargetMatItemList(
			List<ProdPlanTargetMatItemServiceUIModel> prodPlanTargetMatItemList) {
		this.prodPlanTargetMatItemList = prodPlanTargetMatItemList;
	}

	public ProductionPlanActionNodeUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(ProductionPlanActionNodeUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public ProductionPlanActionNodeUIModel getCountApprovedBy() {
		return countApprovedBy;
	}

	public void setCountApprovedBy(ProductionPlanActionNodeUIModel countApprovedBy) {
		this.countApprovedBy = countApprovedBy;
	}

	public ProductionPlanActionNodeUIModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(ProductionPlanActionNodeUIModel submittedBy) {
		this.submittedBy = submittedBy;
	}

	public ProductionPlanActionNodeUIModel getRevokeSubmittedBy() {
		return revokeSubmittedBy;
	}

	public void setRevokeSubmittedBy(ProductionPlanActionNodeUIModel revokeSubmittedBy) {
		this.revokeSubmittedBy = revokeSubmittedBy;
	}

	public ProductionPlanActionNodeUIModel getRejectApprovedBy() {
		return rejectApprovedBy;
	}

	public void setRejectApprovedBy(ProductionPlanActionNodeUIModel rejectApprovedBy) {
		this.rejectApprovedBy = rejectApprovedBy;
	}

	public ProductionPlanActionNodeUIModel getInProductionBy() {
		return inProductionBy;
	}

	public void setInProductionBy(ProductionPlanActionNodeUIModel inProductionBy) {
		this.inProductionBy = inProductionBy;
	}

	public ProductionPlanActionNodeUIModel getFinishedBy() {
		return finishedBy;
	}

	public void setFinishedBy(ProductionPlanActionNodeUIModel finishedBy) {
		this.finishedBy = finishedBy;
	}
}
