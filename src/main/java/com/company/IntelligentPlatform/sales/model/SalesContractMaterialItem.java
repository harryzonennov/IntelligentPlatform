package com.company.IntelligentPlatform.sales.model;

import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinSalesDistribution - SalesContractMaterialItem.hbm.xml
 * Table: SalesContractMaterialItem (schema: sales)
 *
 * Hierarchy: ServiceEntityNode → ReferenceNode → DocMatItemNode → SalesContractMaterialItem
 *
 * All reference fields (refUUID, refSEName, refNodeName, refPackageName),
 * reservation fields, doc chain item links, and material/price fields
 * are inherited from DocMatItemNode and ReferenceNode.
 *
 * This class contains only SalesContract-item-specific fields.
 *
 * Cross-module refs (UUID only, no FK):
 *   refFinAccountUUID    → finance schema
 *   refStoreItemUUID     → logistics schema (warehouse store item)
 *   refOutboundItemUUID  → logistics schema
 */
@Entity
@Table(name = "SalesContractMaterialItem", schema = "sales")
public class SalesContractMaterialItem extends DocMatItemNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.SalesContractMaterialItem;

	@Column(name = "shippingPoint")
	protected String shippingPoint;

	@Column(name = "requireShippingTime")
	protected LocalDateTime requireShippingTime;

	@Column(name = "requirementNote")
	protected String requirementNote;

	@Column(name = "refTechStandard")
	protected String refTechStandard;

	@Column(name = "refUnitName")
	protected String refUnitName;

	// Cross-module refs — UUID only, no FK
	@Column(name = "refStoreItemUUID")
	protected String refStoreItemUUID;

	@Column(name = "refFinAccountUUID")
	protected String refFinAccountUUID;

	@Column(name = "refOutboundItemUUID")
	protected String refOutboundItemUUID;

	@Column(name = "deliveryDoneBy")
	protected String deliveryDoneBy;

	@Column(name = "deliveryDoneTime")
	protected LocalDateTime deliveryDoneTime;

	@Column(name = "processDoneBy")
	protected String processDoneBy;

	@Column(name = "processDoneTime")
	protected LocalDateTime processDoneTime;

	@Column(name = "storeCheckStatus")
	protected int storeCheckStatus;

	public String getShippingPoint() {
		return shippingPoint;
	}

	public void setShippingPoint(String shippingPoint) {
		this.shippingPoint = shippingPoint;
	}

	public LocalDateTime getRequireShippingTime() {
		return requireShippingTime;
	}

	public void setRequireShippingTime(LocalDateTime requireShippingTime) {
		this.requireShippingTime = requireShippingTime;
	}

	public String getRequirementNote() {
		return requirementNote;
	}

	public void setRequirementNote(String requirementNote) {
		this.requirementNote = requirementNote;
	}

	public String getRefTechStandard() {
		return refTechStandard;
	}

	public void setRefTechStandard(String refTechStandard) {
		this.refTechStandard = refTechStandard;
	}

	public String getRefUnitName() {
		return refUnitName;
	}

	public void setRefUnitName(String refUnitName) {
		this.refUnitName = refUnitName;
	}

	public String getRefStoreItemUUID() {
		return refStoreItemUUID;
	}

	public void setRefStoreItemUUID(String refStoreItemUUID) {
		this.refStoreItemUUID = refStoreItemUUID;
	}

	public String getRefFinAccountUUID() {
		return refFinAccountUUID;
	}

	public void setRefFinAccountUUID(String refFinAccountUUID) {
		this.refFinAccountUUID = refFinAccountUUID;
	}

	public String getRefOutboundItemUUID() {
		return refOutboundItemUUID;
	}

	public void setRefOutboundItemUUID(String refOutboundItemUUID) {
		this.refOutboundItemUUID = refOutboundItemUUID;
	}

	public String getDeliveryDoneBy() {
		return deliveryDoneBy;
	}

	public void setDeliveryDoneBy(String deliveryDoneBy) {
		this.deliveryDoneBy = deliveryDoneBy;
	}

	public LocalDateTime getDeliveryDoneTime() {
		return deliveryDoneTime;
	}

	public void setDeliveryDoneTime(LocalDateTime deliveryDoneTime) {
		this.deliveryDoneTime = deliveryDoneTime;
	}

	public String getProcessDoneBy() {
		return processDoneBy;
	}

	public void setProcessDoneBy(String processDoneBy) {
		this.processDoneBy = processDoneBy;
	}

	public LocalDateTime getProcessDoneTime() {
		return processDoneTime;
	}

	public void setProcessDoneTime(LocalDateTime processDoneTime) {
		this.processDoneTime = processDoneTime;
	}

	public int getStoreCheckStatus() {
		return storeCheckStatus;
	}

	public void setStoreCheckStatus(int storeCheckStatus) {
		this.storeCheckStatus = storeCheckStatus;
	}

}
