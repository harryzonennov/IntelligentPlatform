package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.platform.model.DocMatItemNode;
import com.company.IntelligentPlatform.platform.model.DocumentContent;
import com.company.IntelligentPlatform.platform.model.IDefDocumentResource;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

/**
 * Migrated from: ThorsteinLogistics - PurchaseContractMaterialItem (extends DocMatItemNode)
 * Table: PurchaseContractMaterialItem (schema: logistics)
 */
@Entity
@Table(name = "PurchaseContractMaterialItem", catalog = "logistics")
public class PurchaseContractMaterialItem extends DocMatItemNode {

	public static final String NODENAME = IServiceModelConstants.PurchaseContractMaterialItem;

	public static final String SENAME = IServiceModelConstants.PurchaseContract;

	@Column(name = "shippingPoint")
	protected String shippingPoint;

	@Column(name = "requireShippingTime")
	protected LocalDateTime requireShippingTime;

	@Column(name = "itemStatus")
	protected int itemStatus;

	@Column(name = "refUnitName")
	protected String refUnitName;

	@Column(name = "currencyCode")
	protected String currencyCode;

	@Column(name = "refFinAccountUUID")
	protected String refFinAccountUUID;

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

	public int getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(int itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getRefUnitName() {
		return refUnitName;
	}

	public void setRefUnitName(String refUnitName) {
		this.refUnitName = refUnitName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getRefFinAccountUUID() {
		return refFinAccountUUID;
	}

	public void setRefFinAccountUUID(String refFinAccountUUID) {
		this.refFinAccountUUID = refFinAccountUUID;
	}

	public PurchaseContractMaterialItem() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.itemStatus = DocumentContent.STATUS_INITIAL;
		this.homeDocumentType = IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT;
	}

}
