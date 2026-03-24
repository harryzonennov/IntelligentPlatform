package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinProduction - ProdPickingRefMaterialItem (extends DocMatItemNode)
 * Table: ProdPickingRefMaterialItem (schema: production)
 *
 * Tracks picking of reference materials for a production picking order.
 */
@Entity
@Table(name = "ProdPickingRefMaterialItem", schema = "production")
public class ProdPickingRefMaterialItem extends DocMatItemNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.ProdPickingRefMaterialItem;

	public static final int ITEM_STATUS_INITIAL    = 1;

	public static final int ITEM_STATUS_INPROCESS  = 2;

	public static final int ITEM_STATUS_FINISHED   = 3;

	public static final int ITEMSTATUS_APPROVED    = 4;

	public static final int ITEMSTATUS_FINISHED    = 3;

	public static final int ITEMSTATUS_INITIAL     = 1;

	public static final String FIELD_PRODORDER_ITEMUUID = "refProdOrderItemUUID";

	public static final String FIELD_ITEMPROCESSTYPE = "itemProcessType";

	@Column(name = "itemStatus")
	protected int itemStatus;

	@Column(name = "refUnitUUID")
	protected String refUnitUUID;

	@Column(name = "planAmount")
	protected double planAmount;

	@Column(name = "actualAmount")
	protected double actualAmount;

	@Column(name = "returnAmount")
	protected double returnAmount;

	@Column(name = "remainAmount")
	protected double remainAmount;

	@Column(name = "scrapAmount")
	protected double scrapAmount;

	@Column(name = "unitCost")
	protected double unitCost;

	@Column(name = "grossCost")
	protected double grossCost;

	@Column(name = "refWarehouseUUID")
	protected String refWarehouseUUID;

	@Column(name = "refWarehouseAreaUUID")
	protected String refWarehouseAreaUUID;

	@Column(name = "refStoreItemUUID")
	protected String refStoreItemUUID;

	@Column(name = "refProdOrderItemUUID")
	protected String refProdOrderItemUUID;

	@Column(name = "itemProcessType")
	protected int itemProcessType;

	@Column(name = "productionBatchNumber")
	protected String productionBatchNumber;

	@Column(name = "productionDate")
	protected LocalDate productionDate;

	@Column(name = "unitPriceNoTax")
	protected double unitPriceNoTax;

	@Column(name = "refBillOfMaterialUUID")
	protected String refBillOfMaterialUUID;

	@Column(name = "planStartPrepareDate")
	protected LocalDateTime planStartPrepareDate;

	@Column(name = "planStartTime")
	protected LocalDateTime planStartTime;

	@Column(name = "planEndTime")
	protected LocalDateTime planEndTime;

	@Column(name = "refOutboundItemUUID")
	protected String refOutboundItemUUID;

	@Column(name = "itemPriceNoTax")
	protected double itemPriceNoTax;

	@Column(name = "taxRate")
	protected double taxRate;

	@Column(name = "refNextOrderUUID")
	protected String refNextOrderUUID;

	@Column(name = "refPrevOrderUUID")
	protected String refPrevOrderUUID;

	@Column(name = "refInboundItemUUID")
	protected String refInboundItemUUID;

	@Column(name = "toPickAmount")
	protected double toPickAmount;

	@Column(name = "inProcessAmount")
	protected double inProcessAmount;

	@Column(name = "inStockAmount")
	protected double inStockAmount;

	@Column(name = "availableAmount")
	protected double availableAmount;

	@Column(name = "suppliedAmount")
	protected double suppliedAmount;

	@Column(name = "pickedAmount")
	protected double pickedAmount;

	public int getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(int itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getRefUnitUUID() {
		return refUnitUUID;
	}

	public void setRefUnitUUID(String refUnitUUID) {
		this.refUnitUUID = refUnitUUID;
	}

	public double getPlanAmount() {
		return planAmount;
	}

	public void setPlanAmount(double planAmount) {
		this.planAmount = planAmount;
	}

	public double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public double getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(double returnAmount) {
		this.returnAmount = returnAmount;
	}

	public double getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(double remainAmount) {
		this.remainAmount = remainAmount;
	}

	public double getScrapAmount() {
		return scrapAmount;
	}

	public void setScrapAmount(double scrapAmount) {
		this.scrapAmount = scrapAmount;
	}

	public double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(double unitCost) {
		this.unitCost = unitCost;
	}

	public double getGrossCost() {
		return grossCost;
	}

	public void setGrossCost(double grossCost) {
		this.grossCost = grossCost;
	}

	public String getRefWarehouseUUID() {
		return refWarehouseUUID;
	}

	public void setRefWarehouseUUID(String refWarehouseUUID) {
		this.refWarehouseUUID = refWarehouseUUID;
	}

	public String getRefWarehouseAreaUUID() {
		return refWarehouseAreaUUID;
	}

	public void setRefWarehouseAreaUUID(String refWarehouseAreaUUID) {
		this.refWarehouseAreaUUID = refWarehouseAreaUUID;
	}

	public String getRefStoreItemUUID() {
		return refStoreItemUUID;
	}

	public void setRefStoreItemUUID(String refStoreItemUUID) {
		this.refStoreItemUUID = refStoreItemUUID;
	}

	public String getRefProdOrderItemUUID() {
		return refProdOrderItemUUID;
	}

	public void setRefProdOrderItemUUID(String refProdOrderItemUUID) {
		this.refProdOrderItemUUID = refProdOrderItemUUID;
	}

	public int getItemProcessType() {
		return itemProcessType;
	}

	public void setItemProcessType(int itemProcessType) {
		this.itemProcessType = itemProcessType;
	}

	public String getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
	}

	public LocalDate getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(LocalDate productionDate) {
		this.productionDate = productionDate;
	}

	public double getUnitPriceNoTax() {
		return unitPriceNoTax;
	}

	public void setUnitPriceNoTax(double unitPriceNoTax) {
		this.unitPriceNoTax = unitPriceNoTax;
	}

	public String getRefBillOfMaterialUUID() {
		return refBillOfMaterialUUID;
	}

	public void setRefBillOfMaterialUUID(String refBillOfMaterialUUID) {
		this.refBillOfMaterialUUID = refBillOfMaterialUUID;
	}

	public LocalDateTime getPlanStartPrepareDate() {
		return planStartPrepareDate;
	}

	public void setPlanStartPrepareDate(LocalDateTime planStartPrepareDate) {
		this.planStartPrepareDate = planStartPrepareDate;
	}

	public LocalDateTime getPlanStartTime() {
		return planStartTime;
	}

	public void setPlanStartTime(LocalDateTime planStartTime) {
		this.planStartTime = planStartTime;
	}

	public LocalDateTime getPlanEndTime() {
		return planEndTime;
	}

	public void setPlanEndTime(LocalDateTime planEndTime) {
		this.planEndTime = planEndTime;
	}

	public String getRefOutboundItemUUID() {
		return refOutboundItemUUID;
	}

	public void setRefOutboundItemUUID(String refOutboundItemUUID) {
		this.refOutboundItemUUID = refOutboundItemUUID;
	}

	public double getItemPriceNoTax() {
		return itemPriceNoTax;
	}

	public void setItemPriceNoTax(double itemPriceNoTax) {
		this.itemPriceNoTax = itemPriceNoTax;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public String getRefNextOrderUUID() {
		return refNextOrderUUID;
	}

	public void setRefNextOrderUUID(String refNextOrderUUID) {
		this.refNextOrderUUID = refNextOrderUUID;
	}

	public String getRefPrevOrderUUID() {
		return refPrevOrderUUID;
	}

	public void setRefPrevOrderUUID(String refPrevOrderUUID) {
		this.refPrevOrderUUID = refPrevOrderUUID;
	}

	public String getRefInboundItemUUID() {
		return refInboundItemUUID;
	}

	public void setRefInboundItemUUID(String refInboundItemUUID) {
		this.refInboundItemUUID = refInboundItemUUID;
	}

	public double getToPickAmount() {
		return toPickAmount;
	}

	public void setToPickAmount(double toPickAmount) {
		this.toPickAmount = toPickAmount;
	}

	public double getInProcessAmount() {
		return inProcessAmount;
	}

	public void setInProcessAmount(double inProcessAmount) {
		this.inProcessAmount = inProcessAmount;
	}

	public double getInStockAmount() {
		return inStockAmount;
	}

	public void setInStockAmount(double inStockAmount) {
		this.inStockAmount = inStockAmount;
	}

	public double getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(double availableAmount) {
		this.availableAmount = availableAmount;
	}

	public double getSuppliedAmount() {
		return suppliedAmount;
	}

	public void setSuppliedAmount(double suppliedAmount) {
		this.suppliedAmount = suppliedAmount;
	}

	public double getPickedAmount() {
		return pickedAmount;
	}

	public void setPickedAmount(double pickedAmount) {
		this.pickedAmount = pickedAmount;
	}

}
