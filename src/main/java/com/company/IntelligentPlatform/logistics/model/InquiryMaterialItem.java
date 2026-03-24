package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinLogistics - InquiryMaterialItem (extends DocMatItemNode)
 * Table: InquiryMaterialItem (schema: logistics)
 */
@Entity
@Table(name = "InquiryMaterialItem", schema = "logistics")
public class InquiryMaterialItem extends DocMatItemNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.InquiryMaterialItem;

	@Column(name = "shippingPoint")
	protected String shippingPoint;

	@Column(name = "requireShippingTime")
	protected LocalDateTime requireShippingTime;

	@Column(name = "itemStatus")
	protected int itemStatus;

	@Column(name = "refUnitName")
	protected String refUnitName;

	@Column(name = "firstUnitPrice")
	protected double firstUnitPrice;

	@Column(name = "firstItemPrice")
	protected double firstItemPrice;

	@Column(name = "currencyCode")
	protected String currencyCode;

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

	public double getFirstUnitPrice() {
		return firstUnitPrice;
	}

	public void setFirstUnitPrice(double firstUnitPrice) {
		this.firstUnitPrice = firstUnitPrice;
	}

	public double getFirstItemPrice() {
		return firstItemPrice;
	}

	public void setFirstItemPrice(double firstItemPrice) {
		this.firstItemPrice = firstItemPrice;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

}
