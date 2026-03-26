package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import jakarta.persistence.*;
import java.time.LocalDate;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinProduction - ProductionOrderItem (extends DocMatItemNode)
 * Table: ProductionOrderItem (schema: production)
 */
@Entity
@Table(name = "ProductionOrderItem", catalog = "production")
public class ProductionOrderItem extends DocMatItemNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.ProductionOrderItem;

	public static final int STATUS_INITIAL      = 1;

	public static final int STATUS_INPROCESS    = 2;

	public static final int STATUS_FINISHED     = 3;

	public static final int STATUS_AVAILABLE    = 4;

	public static final int STATUS_PROCESSDONE  = 100;

	public static final int ITEM_CATEGORY_SEMIFINISHED = 1;

	public static final int ITEM_CATEGORY_COMPONENT    = 2;

	public static final int ITEM_CATEGORY_BYPRODUCT    = 3;

	public static final int STATUS_PART_DONE       = 5;

	public static final int STATUS_PART_AVAILABLE  = 6;

	public static final int STATUS_LACKINPLAN      = 7;

	public static final int STATUS_ALL_DONE        = 100;

	@Column(name = "itemCategory")
	protected int itemCategory;

	@Column(name = "itemStatus")
	protected int itemStatus;

	@Column(name = "refUnitUUID")
	protected String refUnitUUID;

	@Column(name = "planAmount")
	protected double planAmount;

	@Column(name = "actualAmount")
	protected double actualAmount;

	@Column(name = "scrapAmount")
	protected double scrapAmount;

	@Column(name = "pickAmount")
	protected double pickAmount;

	@Column(name = "supplyAmount")
	protected double supplyAmount;

	@Column(name = "unitCost")
	protected double unitCost;

	@Column(name = "grossCost")
	protected double grossCost;

	@Column(name = "itemCostLossRate")
	protected double itemCostLossRate;

	@Column(name = "itemCostActual")
	protected double itemCostActual;

	@Column(name = "amountWithLossRate")
	protected double amountWithLossRate;

	@Column(name = "availableAmount")
	protected double availableAmount;

	@Column(name = "inStockAmount")
	protected double inStockAmount;

	@Column(name = "toPickAmount")
	protected double toPickAmount;

	@Column(name = "inProcessAmount")
	protected double inProcessAmount;

	@Column(name = "selfLeadTime")
	protected double selfLeadTime;

	@Column(name = "comLeadTime")
	protected double comLeadTime;

	@Column(name = "planStartDate")
	protected java.time.LocalDateTime planStartDate;

	@Column(name = "planStartPrepareDate")
	protected java.time.LocalDateTime planStartPrepareDate;

	@Column(name = "planEndDate")
	protected java.time.LocalDateTime planEndDate;

	@Column(name = "refBOMItemUUID")
	protected String refBOMItemUUID;

	@Column(name = "refRouteProcessItemUUID")
	protected String refRouteProcessItemUUID;

	@Column(name = "refWarehouseUUID")
	protected String refWarehouseUUID;

	@Column(name = "refWarehouseAreaUUID")
	protected String refWarehouseAreaUUID;

	@Column(name = "productionBatchNumber")
	protected String productionBatchNumber;

	@Column(name = "productionDate")
	protected LocalDate productionDate;

	@Column(name = "requireDate")
	protected LocalDate requireDate;

	public int getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(int itemCategory) {
		this.itemCategory = itemCategory;
	}

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

	public double getScrapAmount() {
		return scrapAmount;
	}

	public void setScrapAmount(double scrapAmount) {
		this.scrapAmount = scrapAmount;
	}

	public double getPickAmount() {
		return pickAmount;
	}

	public void setPickAmount(double pickAmount) {
		this.pickAmount = pickAmount;
	}

	public double getSupplyAmount() {
		return supplyAmount;
	}

	public void setSupplyAmount(double supplyAmount) {
		this.supplyAmount = supplyAmount;
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

	public double getItemCostLossRate() {
		return itemCostLossRate;
	}

	public void setItemCostLossRate(double itemCostLossRate) {
		this.itemCostLossRate = itemCostLossRate;
	}

	public double getItemCostActual() {
		return itemCostActual;
	}

	public void setItemCostActual(double itemCostActual) {
		this.itemCostActual = itemCostActual;
	}

	public double getAmountWithLossRate() {
		return amountWithLossRate;
	}

	public void setAmountWithLossRate(double amountWithLossRate) {
		this.amountWithLossRate = amountWithLossRate;
	}

	public String getRefBOMItemUUID() {
		return refBOMItemUUID;
	}

	public void setRefBOMItemUUID(String refBOMItemUUID) {
		this.refBOMItemUUID = refBOMItemUUID;
	}

	public String getRefRouteProcessItemUUID() {
		return refRouteProcessItemUUID;
	}

	public void setRefRouteProcessItemUUID(String refRouteProcessItemUUID) {
		this.refRouteProcessItemUUID = refRouteProcessItemUUID;
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

	public LocalDate getRequireDate() {
		return requireDate;
	}

	public void setRequireDate(LocalDate requireDate) {
		this.requireDate = requireDate;
	}

	public double getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(double availableAmount) {
		this.availableAmount = availableAmount;
	}

	public double getInStockAmount() {
		return inStockAmount;
	}

	public void setInStockAmount(double inStockAmount) {
		this.inStockAmount = inStockAmount;
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

	public double getSelfLeadTime() {
		return selfLeadTime;
	}

	public void setSelfLeadTime(double selfLeadTime) {
		this.selfLeadTime = selfLeadTime;
	}

	public double getComLeadTime() {
		return comLeadTime;
	}

	public void setComLeadTime(double comLeadTime) {
		this.comLeadTime = comLeadTime;
	}

	public java.time.LocalDateTime getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(java.time.LocalDateTime planStartDate) {
		this.planStartDate = planStartDate;
	}

	public java.time.LocalDateTime getPlanStartPrepareDate() {
		return planStartPrepareDate;
	}

	public void setPlanStartPrepareDate(java.time.LocalDateTime planStartPrepareDate) {
		this.planStartPrepareDate = planStartPrepareDate;
	}

	public java.time.LocalDateTime getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(java.time.LocalDateTime planEndDate) {
		this.planEndDate = planEndDate;
	}

	@Column(name = "suppliedAmount")
	protected double suppliedAmount;

	@Column(name = "lackInPlanAmount")
	protected double lackInPlanAmount;

	@Column(name = "pickedAmount")
	protected double pickedAmount;

	@Column(name = "pickStatus")
	protected int pickStatus;

	@Column(name = "actualStartDate")
	protected java.time.LocalDateTime actualStartDate;

	@Column(name = "actualEndDate")
	protected java.time.LocalDateTime actualEndDate;

	@Column(name = "actualStartPrepareDate")
	protected java.time.LocalDateTime actualStartPrepareDate;

	public double getSuppliedAmount() {
		return suppliedAmount;
	}

	public void setSuppliedAmount(double suppliedAmount) {
		this.suppliedAmount = suppliedAmount;
	}

	public double getLackInPlanAmount() {
		return lackInPlanAmount;
	}

	public void setLackInPlanAmount(double lackInPlanAmount) {
		this.lackInPlanAmount = lackInPlanAmount;
	}

	public double getPickedAmount() {
		return pickedAmount;
	}

	public void setPickedAmount(double pickedAmount) {
		this.pickedAmount = pickedAmount;
	}

	public int getPickStatus() {
		return pickStatus;
	}

	public void setPickStatus(int pickStatus) {
		this.pickStatus = pickStatus;
	}

	public java.time.LocalDateTime getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(java.time.LocalDateTime actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public java.time.LocalDateTime getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(java.time.LocalDateTime actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public java.time.LocalDateTime getActualStartPrepareDate() {
		return actualStartPrepareDate;
	}

	public void setActualStartPrepareDate(java.time.LocalDateTime actualStartPrepareDate) {
		this.actualStartPrepareDate = actualStartPrepareDate;
	}

	@Column(name = "itemCostNoTax")
	protected double itemCostNoTax;

	@Column(name = "unitCostNoTax")
	protected double unitCostNoTax;

	@Column(name = "taxRate")
	protected double taxRate;

	public double getItemCostNoTax() {
		return itemCostNoTax;
	}

	public void setItemCostNoTax(double itemCostNoTax) {
		this.itemCostNoTax = itemCostNoTax;
	}

	public double getUnitCostNoTax() {
		return unitCostNoTax;
	}

	public void setUnitCostNoTax(double unitCostNoTax) {
		this.unitCostNoTax = unitCostNoTax;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

}
