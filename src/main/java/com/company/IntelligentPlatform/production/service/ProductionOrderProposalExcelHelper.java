package com.company.IntelligentPlatform.production.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.company.IntelligentPlatform.production.dto.ProdOrderHeaderExcelProposal;
import com.company.IntelligentPlatform.production.dto.ProdOrderItemExcelProposal;
import com.company.IntelligentPlatform.production.dto.ProductionOrderItemUIModel;
import com.company.IntelligentPlatform.production.dto.ProductionOrderUIModel;
import com.company.IntelligentPlatform.production.model.ProdOrderItemReqProposal;
import com.company.IntelligentPlatform.production.model.ProductionOrder;
import com.company.IntelligentPlatform.production.model.ProductionOrderItem;
import com.company.IntelligentPlatform.production.model.ProductionPlanItem;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.MaterialSKUUnitReference;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportConfig;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.ServiceExcelConfigException;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportProxy;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Compound helper class for production order to generate proposal excel
 * 
 * @author Zhang,Hang
 *
 */
@Service
public class ProductionOrderProposalExcelHelper {

	@Autowired
	protected ProdOrderItemProposalExcelProxy prodOrderItemProposalExcelProxy;

	@Autowired
	protected ProdOrderHeaderProposalExcelProxy prodOrderHeaderProposalExcelProxy;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	protected String getPreWarnMsg(String key, Map<String, String> preWarnMap) {
		return preWarnMap.get(key);
	}

	protected Map<String, String> getPreWarnMap() throws IOException {
		Locale locale = Locale.getDefault();
		String path = ProductionOrderUIModel.class.getResource("").getPath();
		String resFileName = ProductionOrder.SENAME;
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap;
	}

	protected Map<String, String> getItemPreWarnMap() throws IOException {
		Locale locale = Locale.getDefault();
		String path = ProductionOrderItemUIModel.class.getResource("")
				.getPath();
		String resFileName = ProductionOrderItem.NODENAME;
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap;
	}

	/**
	 * Convert the production order item list to excel proposal list
	 * 
	 * @param productionOrderItemList
	 * @return
	 * @throws ServiceEntityInstallationException
	 * @throws ServiceEntityConfigureException
	 * @throws IOException
	 */
	public List<SEUIComModel> convProdOrderItemExcelProposalList(
			List<ServiceEntityNode> productionOrderItemList,
			List<ServiceEntityNode> rawItemList) throws IOException,
			ServiceEntityConfigureException, ServiceEntityInstallationException {
		int itemIndex = 1;
		List<SEUIComModel> prodOrderItemExcelProposalList = new ArrayList<SEUIComModel>();
		if (productionOrderItemList != null
				&& productionOrderItemList.size() > 0) {
			for (ServiceEntityNode seNode : productionOrderItemList) {
				ProductionOrderItem productionOrderItem = (ProductionOrderItem) seNode;
				ProdOrderItemExcelProposal prodOrderItemExcelProposal = new ProdOrderItemExcelProposal();
				convProductionItemToExcelProposal(productionOrderItem,
						prodOrderItemExcelProposal, itemIndex + "");
				List<ServiceEntityNode> subProdItemReqList = productionOrderManager
						.filterSubProdItemReqList(productionOrderItem,
								rawItemList);
				int itemStatus = calculateProductionOrderItemStatus(subProdItemReqList);
				prodOrderItemExcelProposal.setItemStatus(itemStatus);
				prodOrderItemExcelProposalList.add(prodOrderItemExcelProposal);
				if (subProdItemReqList != null && subProdItemReqList.size() > 0) {
					int innerIndex = 1;
					for (ServiceEntityNode tmpSENode : subProdItemReqList) {
						String reqIndex = itemIndex + "-" + innerIndex;
						ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) tmpSENode;
						if (prodOrderItemReqProposal.getDocumentType() == IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY) {
							// Process as outbound delivery proposal
							ProdOrderItemExcelProposal outboundItemExcelProposal = new ProdOrderItemExcelProposal();
							convOutboundItemToExcelProposal(
									prodOrderItemReqProposal,
									outboundItemExcelProposal, reqIndex);
							prodOrderItemExcelProposalList
									.add(outboundItemExcelProposal);
						}
						if (prodOrderItemReqProposal.getDocumentType() == IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER) {
							// Process as purchase proposal
							ProdOrderItemExcelProposal purchaseItemExcelProposal = new ProdOrderItemExcelProposal();
							convPurchaseItemToExcelProposal(
									prodOrderItemReqProposal,
									purchaseItemExcelProposal, reqIndex);
							prodOrderItemExcelProposalList
									.add(purchaseItemExcelProposal);
						}
						if (prodOrderItemReqProposal.getDocumentType() == IDefDocumentResource.DOCUMENT_TYPE_PURCHASE) {
							// Process as purchase proposal
							ProdOrderItemExcelProposal purchaseItemExcelProposal = new ProdOrderItemExcelProposal();
							convPurchaseItemToExcelProposal(
									prodOrderItemReqProposal,
									purchaseItemExcelProposal, reqIndex);
							prodOrderItemExcelProposalList
									.add(purchaseItemExcelProposal);
						}
						innerIndex++;
					}
				}
				// Add blank
				prodOrderItemExcelProposalList
						.add(new ProdOrderItemExcelProposal());
				itemIndex++;
			}
		}
		return prodOrderItemExcelProposalList;
	}

	/**
	 * [Internal method] Calculate the current production order item status by
	 * checking the sub requirement for output on excel
	 * 
	 * @param subProdItemReqList
	 * @return indicate the if the production order item could be available
	 *         immediately
	 */
	protected int calculateProductionOrderItemStatus(
			List<ServiceEntityNode> subProdItemReqList) {
		if (subProdItemReqList != null && subProdItemReqList.size() > 0) {
			for (ServiceEntityNode tmpSENode : subProdItemReqList) {
				ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) tmpSENode;
				if (prodOrderItemReqProposal.getDocumentType() == IDefDocumentResource.DOCUMENT_TYPE_PURCHASE) {
					// In case there is sub purchase order, means this item not
					// avaiable
					return StandardSwitchProxy.SWITCH_OFF;
				}
				if (prodOrderItemReqProposal.getDocumentType() == IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER) {
					// In case there is sub purchase order, means this item not
					// avaiable
					return StandardSwitchProxy.SWITCH_OFF;
				}
			}
		}
		return StandardSwitchProxy.SWITCH_ON;
	}

	protected void convProductionItemToExcelProposal(
			ProductionOrderItem productionOrderItem,
			ProdOrderItemExcelProposal prodOrderItemExcelProposal,
			String itemIndex) throws IOException,
			ServiceEntityConfigureException, ServiceEntityInstallationException {
		Map<String, String> itemMap = getItemPreWarnMap();
		if (prodOrderItemExcelProposal != null && productionOrderItem != null) {
			MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
					.getEntityNodeByKey(
							productionOrderItem.getRefMaterialSKUUUID(),
							"uuid", MaterialStockKeepUnit.NODENAME, null);
			List<ServiceEntityNode> materialSKUUnitList = materialStockKeepUnitManager
					.getEntityNodeListByKey(materialStockKeepUnit.getUuid(),
							IServiceEntityNodeFieldConstant.ROOTNODEUUID,
							MaterialSKUUnitReference.NODENAME,
							materialStockKeepUnit.getClient(), null);
			Map<String, String> materialUnitMap = materialStockKeepUnitManager
					.getAllUnitMapFromSKU(materialStockKeepUnit,
							materialSKUUnitList);
			String prodItemTitleLabel = getPreWarnMsg(
					"section.productionOrderItem", itemMap);
			prodOrderItemExcelProposal.setRefDocumentType(0);
			prodOrderItemExcelProposal
					.setItemCategory(ProdOrderItemExcelProposal.ITEM_CATE_PRODITEM);
			prodOrderItemExcelProposal.setItemTitleLabel(prodItemTitleLabel);
			prodOrderItemExcelProposal.setIndex("[" + itemIndex + "]");
			prodOrderItemExcelProposal.setPrefix(itemIndex);
			String actualAmountLabel = getPreWarnMsg("actualAmount", itemMap);
			prodOrderItemExcelProposal.setActualAmountLabel(actualAmountLabel);
			String actualAmount = productionOrderItem.getActualAmount() + "";
			if (productionOrderItem.getActualAmount() != 0) {
				actualAmount = actualAmount
						+ materialUnitMap.get(productionOrderItem
								.getRefUnitUUID());
			}
			prodOrderItemExcelProposal.setActualAmount(actualAmount);
			String amountLabel = getPreWarnMsg("amount", itemMap);
			prodOrderItemExcelProposal.setAmountLabel(amountLabel);
			String amount = productionOrderItem.getAmount() + "";
			if (productionOrderItem.getAmount() != 0) {
				amount = amount
						+ materialUnitMap.get(productionOrderItem
								.getRefUnitUUID());
			}
			if (productionOrderItem.getPlanStartDate() != null) {
				String planStartDate = DefaultDateFormatConstant.DATE_MIN_FORMAT
						.format(productionOrderItem.getPlanStartDate());
				planStartDate = getPreWarnMsg("planStartDate", itemMap) + ": "
						+ planStartDate;
				prodOrderItemExcelProposal
				.setPlanStartDateStr(planStartDate);
				prodOrderItemExcelProposal.setPlanStartDate(productionOrderItem.getPlanStartDate());
			}
			if (productionOrderItem.getPlanStartPrepareDate() != null) {
				String planStartPrepareStr = DefaultDateFormatConstant.DATE_MIN_FORMAT
						.format(productionOrderItem.getPlanStartPrepareDate());
				planStartPrepareStr = getPreWarnMsg("planStartPrepareDate",
						itemMap) + ": " + planStartPrepareStr;
				prodOrderItemExcelProposal
						.setPlanStartPrepareDateStr(planStartPrepareStr);
				prodOrderItemExcelProposal.setPlanStartPrepareDate(productionOrderItem
								.getPlanStartPrepareDate());
			}
			if(productionOrderItem.getPlanEndDate() != null){
				String planEndDate = DefaultDateFormatConstant.DATE_MIN_FORMAT
						.format(productionOrderItem
								.getPlanEndDate());
				planEndDate = getPreWarnMsg("planEndDate",
						itemMap) + ": " + planEndDate;
				prodOrderItemExcelProposal
						.setPlanEndDateStr(planEndDate);
				prodOrderItemExcelProposal.setPlanEndDate(productionOrderItem
								.getPlanEndDate());
			}
			prodOrderItemExcelProposal.setAmount(amount);
			String outMaterialSKULabel = getPreWarnMsg("refMaterialSKUID",
					itemMap);
			prodOrderItemExcelProposal
					.setOutMaterialSKULabel(outMaterialSKULabel);
			String outMaterialSKUID = materialStockKeepUnit.getId() + "-"
					+ materialStockKeepUnit.getName();
			prodOrderItemExcelProposal.setOutMaterialSKUID(outMaterialSKUID);
			String itemStatusLabel = getPreWarnMsg("itemStatus", itemMap);
			prodOrderItemExcelProposal.setItemStatusLabel(itemStatusLabel);
			Map<Integer, String> itemStatusMap = serviceDropdownListHelper
					.getUIDropDownMap(ProductionOrderItemUIModel.class,
							"itemStatus");
			prodOrderItemExcelProposal.setItemStatus(productionOrderItem
					.getItemStatus());
			prodOrderItemExcelProposal.setItemStatusValue(itemStatusMap
					.get(productionOrderItem.getItemStatus()));
		}
	}

	protected void convOutboundItemToExcelProposal(
			ProdOrderItemReqProposal prodOrderItemReqProposal,
			ProdOrderItemExcelProposal prodOrderItemExcelProposal,
			String itemIndex) throws IOException,
			ServiceEntityConfigureException, ServiceEntityInstallationException {
		Map<String, String> itemMap = getItemPreWarnMap();
		if (prodOrderItemExcelProposal != null
				&& prodOrderItemReqProposal != null) {
			MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
					.getEntityNodeByKey(
							prodOrderItemReqProposal.getRefMaterialSKUUUID(),
							"uuid", MaterialStockKeepUnit.NODENAME, null);
			List<ServiceEntityNode> materialSKUUnitList = materialStockKeepUnitManager
					.getEntityNodeListByKey(materialStockKeepUnit.getUuid(),
							IServiceEntityNodeFieldConstant.ROOTNODEUUID,
							MaterialSKUUnitReference.NODENAME,
							materialStockKeepUnit.getClient(), null);
			Map<String, String> materialUnitMap = materialStockKeepUnitManager
					.getAllUnitMapFromSKU(materialStockKeepUnit,
							materialSKUUnitList);
			String prodItemTitleLabel = getPreWarnMsg("outboundProposal",
					itemMap);
			prodOrderItemExcelProposal
					.setRefDocumentType(prodOrderItemReqProposal
							.getDocumentType());
			prodOrderItemExcelProposal
					.setItemCategory(ProdOrderItemExcelProposal.ITEM_CATE_OUTBOUND);
			prodOrderItemExcelProposal.setItemTitleLabel(prodItemTitleLabel);
			prodOrderItemExcelProposal.setIndex("[" + itemIndex + "]");
			String amountLabel = getPreWarnMsg("outAmount", itemMap);
			prodOrderItemExcelProposal.setAmountLabel(amountLabel);
			String amount = prodOrderItemReqProposal.getAmount() + "";
			if (prodOrderItemReqProposal.getAmount() != 0) {
				amount = amount
						+ materialUnitMap.get(prodOrderItemReqProposal
								.getRefUnitUUID());
			}
			prodOrderItemExcelProposal.setAmount(amount);
			String outMaterialSKULabel = getPreWarnMsg("refMaterialSKUID",
					itemMap);
			prodOrderItemExcelProposal
					.setOutMaterialSKULabel(outMaterialSKULabel);
			String outMaterialSKUID = materialStockKeepUnit.getId() + "-"
					+ materialStockKeepUnit.getName();
			prodOrderItemExcelProposal.setOutMaterialSKUID(outMaterialSKUID);
			String itemStatusLabel = getPreWarnMsg("itemStatus", itemMap);
			prodOrderItemExcelProposal.setItemStatusLabel(itemStatusLabel);
			int tempItemStatus = prodOrderItemReqProposal.getItemStatus();
			if (tempItemStatus == 0) {
				tempItemStatus = ProductionPlanItem.STATUS_INITIAL;
			}
			Map<Integer, String> itemStatusMap = serviceDropdownListHelper
					.getUIDropDownMap(ProductionOrderItemUIModel.class,
							"itemStatus");
			prodOrderItemExcelProposal.setItemStatus(tempItemStatus);
			prodOrderItemExcelProposal.setItemStatusValue(itemStatusMap
					.get(tempItemStatus));
			if (prodOrderItemReqProposal.getPlanStartDate() != null) {
				String planStartDate = DefaultDateFormatConstant.DATE_MIN_FORMAT
						.format(prodOrderItemReqProposal.getPlanStartDate());
				planStartDate = getPreWarnMsg("planStartDate", itemMap) + ": "
						+ planStartDate;
				prodOrderItemExcelProposal
				.setPlanStartDateStr(planStartDate);
				prodOrderItemExcelProposal.setPlanStartDate(prodOrderItemReqProposal.getPlanStartDate());
			}
			if (prodOrderItemReqProposal.getPlanStartPrepareDate() != null) {
				String planStartPrepareStr = DefaultDateFormatConstant.DATE_MIN_FORMAT
						.format(prodOrderItemReqProposal.getPlanStartPrepareDate());
				planStartPrepareStr = getPreWarnMsg("planStartPrepareDate",
						itemMap) + ": " + planStartPrepareStr;
				prodOrderItemExcelProposal
						.setPlanStartPrepareDateStr(planStartPrepareStr);
				prodOrderItemExcelProposal.setPlanStartPrepareDate(prodOrderItemReqProposal
								.getPlanStartPrepareDate());
			}
			if(prodOrderItemReqProposal.getPlanEndDate() != null){
				String planEndDate = DefaultDateFormatConstant.DATE_MIN_FORMAT
						.format(prodOrderItemReqProposal
								.getPlanEndDate());
				planEndDate = getPreWarnMsg("planEndDate",
						itemMap) + ": " + planEndDate;
				prodOrderItemExcelProposal
						.setPlanEndDateStr(planEndDate);
				prodOrderItemExcelProposal.setPlanEndDate(prodOrderItemReqProposal
								.getPlanEndDate());
			}
			String outFromWarehouse = getPreWarnMsg("outFromWarehouse", itemMap);
			prodOrderItemExcelProposal.setWarehouseTitle(outFromWarehouse);
			Warehouse warehouse = (Warehouse) warehouseManager
					.getEntityNodeByKey(
							prodOrderItemReqProposal.getRefWarehouseUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							Warehouse.NODENAME,
							materialStockKeepUnit.getClient(), null);
			if (warehouse != null) {
				String warehouseStoreAmount = prodOrderItemReqProposal
						.getStoreAmount()
						+ materialUnitMap.get(prodOrderItemReqProposal
								.getStoreUnitUUID());
				prodOrderItemExcelProposal
						.setWarehouseStoreAmount(warehouseStoreAmount);
				prodOrderItemExcelProposal.setWarehouseID(warehouse.getId());
			}
			String warehouseStoreAmountLabel = getPreWarnMsg("storeAmount",
					itemMap);
			prodOrderItemExcelProposal
					.setWarehouseStoreAmountLabel(warehouseStoreAmountLabel);
		}
	}

	protected void convPurchaseItemToExcelProposal(
			ProdOrderItemReqProposal prodOrderItemReqProposal,
			ProdOrderItemExcelProposal prodOrderItemExcelProposal,
			String itemIndex) throws IOException,
			ServiceEntityConfigureException, ServiceEntityInstallationException {
		Map<String, String> itemMap = getItemPreWarnMap();
		if (prodOrderItemExcelProposal != null
				&& prodOrderItemReqProposal != null) {
			MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
					.getEntityNodeByKey(
							prodOrderItemReqProposal.getRefMaterialSKUUUID(),
							"uuid", MaterialStockKeepUnit.NODENAME, null);
			List<ServiceEntityNode> materialSKUUnitList = materialStockKeepUnitManager
					.getEntityNodeListByKey(materialStockKeepUnit.getUuid(),
							IServiceEntityNodeFieldConstant.ROOTNODEUUID,
							MaterialSKUUnitReference.NODENAME,
							materialStockKeepUnit.getClient(), null);
			Map<String, String> materialUnitMap = materialStockKeepUnitManager
					.getAllUnitMapFromSKU(materialStockKeepUnit,
							materialSKUUnitList);
			String prodItemTitleLabel = ServiceEntityStringHelper.EMPTYSTRING;
			if (prodOrderItemReqProposal.getDocumentType() == IDefDocumentResource.DOCUMENT_TYPE_PURCHASE) {
				prodItemTitleLabel = getPreWarnMsg("purchaseProposal", itemMap);
			}
			if (prodOrderItemReqProposal.getDocumentType() == IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER) {
				prodItemTitleLabel = getPreWarnMsg("productionProposal",
						itemMap);
			}
			prodOrderItemExcelProposal
					.setItemCategory(ProdOrderItemExcelProposal.ITEM_CATE_PURCHASE);
			prodOrderItemExcelProposal.setItemTitleLabel(prodItemTitleLabel);
			prodOrderItemExcelProposal
					.setRefDocumentType(prodOrderItemReqProposal
							.getDocumentType());
			prodOrderItemExcelProposal.setIndex("[" + itemIndex + "]");
			String amountLabel = ServiceEntityStringHelper.EMPTYSTRING;
			if (prodOrderItemReqProposal.getDocumentType() == IDefDocumentResource.DOCUMENT_TYPE_PURCHASE) {
				amountLabel = getPreWarnMsg("purchasePropAmount", itemMap);
			}
			if (prodOrderItemReqProposal.getDocumentType() == IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER) {
				amountLabel = getPreWarnMsg("productPropAmount", itemMap);
			}
			prodOrderItemExcelProposal.setAmountLabel(amountLabel);
			String amount = prodOrderItemReqProposal.getAmount() + "";
			if (prodOrderItemReqProposal.getAmount() != 0) {
				amount = amount
						+ materialUnitMap.get(prodOrderItemReqProposal
								.getRefUnitUUID());
			}
			if (prodOrderItemReqProposal.getPlanStartDate() != null) {
				String planStartDate = DefaultDateFormatConstant.DATE_MIN_FORMAT
						.format(prodOrderItemReqProposal.getPlanStartDate());
				planStartDate = getPreWarnMsg("planStartDate", itemMap) + ": "
						+ planStartDate;
				prodOrderItemExcelProposal.setPlanStartDate(prodOrderItemReqProposal.getPlanStartDate());
				prodOrderItemExcelProposal.setPlanStartDateStr(planStartDate);
			}
			if( prodOrderItemReqProposal.getPlanStartPrepareDate() != null) {
				String planStartPrepareDate = DefaultDateFormatConstant.DATE_MIN_FORMAT
						.format(prodOrderItemReqProposal
								.getPlanStartPrepareDate());
				planStartPrepareDate = getPreWarnMsg("planStartPrepareDate",
						itemMap) + ": " + planStartPrepareDate;
				prodOrderItemExcelProposal
						.setPlanStartPrepareDateStr(planStartPrepareDate);
				prodOrderItemExcelProposal.setPlanStartPrepareDate(prodOrderItemReqProposal
								.getPlanStartPrepareDate());
			}
			if(prodOrderItemReqProposal.getPlanEndDate() != null){
				String planEndDate = DefaultDateFormatConstant.DATE_MIN_FORMAT
						.format(prodOrderItemReqProposal
								.getPlanEndDate());
				planEndDate = getPreWarnMsg("planEndDate",
						itemMap) + ": " + planEndDate;
				prodOrderItemExcelProposal
						.setPlanEndDateStr(planEndDate);
				prodOrderItemExcelProposal.setPlanEndDate(prodOrderItemReqProposal
								.getPlanEndDate());
			}
			
			prodOrderItemExcelProposal.setAmount(amount);
			String outMaterialSKULabel = getPreWarnMsg("refMaterialSKUID",
					itemMap);
			prodOrderItemExcelProposal
					.setOutMaterialSKULabel(outMaterialSKULabel);
			String outMaterialSKUID = materialStockKeepUnit.getId() + "-"
					+ materialStockKeepUnit.getName();
			prodOrderItemExcelProposal.setPlanStartDate(prodOrderItemReqProposal.getPlanStartDate());
			prodOrderItemExcelProposal.setPlanStartPrepareDate(prodOrderItemReqProposal.getPlanStartPrepareDate());
			prodOrderItemExcelProposal.setOutMaterialSKUID(outMaterialSKUID);
			String itemStatusLabel = getPreWarnMsg("itemStatus", itemMap);
			prodOrderItemExcelProposal.setItemStatusLabel(itemStatusLabel);
			Map<Integer, String> itemStatusMap = serviceDropdownListHelper
					.getUIDropDownMap(ProductionOrderItemUIModel.class,
							"itemStatus");
			prodOrderItemExcelProposal.setItemStatus(prodOrderItemReqProposal
					.getItemStatus());
			prodOrderItemExcelProposal.setItemStatusValue(itemStatusMap
					.get(prodOrderItemReqProposal.getItemStatus()));

		}
	}

	/**
	 * Temporary method to print
	 * 
	 * @param productionOrderItem
	 * @param index
	 * @throws ServiceEntityConfigureException
	 */
	protected void printProductionOrderItem(
			ProductionOrderItem productionOrderItem, int index)
			throws ServiceEntityConfigureException {
		MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
				.getEntityNodeByKey(
						productionOrderItem.getRefMaterialSKUUUID(), "uuid",
						MaterialStockKeepUnit.NODENAME, null);
		List<ServiceEntityNode> materialSKUUnitList = materialStockKeepUnitManager
				.getEntityNodeListByKey(materialStockKeepUnit.getUuid(),
						IServiceEntityNodeFieldConstant.ROOTNODEUUID,
						MaterialSKUUnitReference.NODENAME,
						materialStockKeepUnit.getClient(), null);
		Map<String, String> materialUnitMap = materialStockKeepUnitManager
				.getAllUnitMapFromSKU(materialStockKeepUnit,
						materialSKUUnitList);
		System.out.print("---Item:[" + index + "], out material:["
				+ materialStockKeepUnit.getId() + "-"
				+ materialStockKeepUnit.getName() + "] Amount:"
				+ +productionOrderItem.getAmount()
				+ materialUnitMap.get(productionOrderItem.getRefUnitUUID())
				+ " Actual amount:" + productionOrderItem.getActualAmount()
				+ materialUnitMap.get(productionOrderItem.getRefUnitUUID()));
		System.out.println(" Status:[" + productionOrderItem.getItemStatus() + "]");
	}

	protected void printProdOrderItemReq(
			ProdOrderItemReqProposal prodOrderItemReqProposal, String index)
			throws ServiceEntityConfigureException,
			ServiceEntityInstallationException {
		MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
				.getEntityNodeByKey(
						prodOrderItemReqProposal.getRefMaterialSKUUUID(),
						"uuid", MaterialStockKeepUnit.NODENAME, null);
		List<ServiceEntityNode> materialSKUUnitList = materialStockKeepUnitManager
				.getEntityNodeListByKey(materialStockKeepUnit.getUuid(),
						IServiceEntityNodeFieldConstant.ROOTNODEUUID,
						MaterialSKUUnitReference.NODENAME,
						materialStockKeepUnit.getClient(), null);
		Map<String, String> materialUnitMap = materialStockKeepUnitManager
				.getAllUnitMapFromSKU(materialStockKeepUnit,
						materialSKUUnitList);
		Map<Integer, String> documentTypeMap = serviceDocumentComProxy
				.getSearchDocumentTypeMap();
		String documentTypeValue = documentTypeMap.get(prodOrderItemReqProposal
				.getDocumentType());
		if (prodOrderItemReqProposal.getDocumentType() == IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY) {
			// In case requirement for warehouse store
			String outputContent = "-----Outbound proposal:["
					+ index
					+ "]"
					+ documentTypeValue
					+ "] material:["
					+ materialStockKeepUnit.getId()
					+ "-"
					+ materialStockKeepUnit.getName()
					+ "]:"
					+ prodOrderItemReqProposal.getAmount()
					+ materialUnitMap.get(prodOrderItemReqProposal
							.getRefUnitUUID());
			Warehouse warehouse = (Warehouse) warehouseManager
					.getEntityNodeByKey(
							prodOrderItemReqProposal.getRefWarehouseUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							Warehouse.NODENAME,
							materialStockKeepUnit.getClient(), null);
			if (warehouse != null) {
				outputContent = outputContent
						+ "; From Warehouse:["
						+ warehouse.getId()
						+ "], current availbale storage:["
						+ prodOrderItemReqProposal.getStoreAmount()
						+ materialUnitMap.get(prodOrderItemReqProposal
								.getStoreUnitUUID()) + "]";
			}
			System.out.println(outputContent);

		} else {
			// In case purchase or production order
			String outputContent = "-----itemReq proposal:["
					+ index
					+ "]"
					+ documentTypeValue
					+ "] material:["
					+ materialStockKeepUnit.getId()
					+ "-"
					+ materialStockKeepUnit.getName()
					+ "]:"
					+ prodOrderItemReqProposal.getAmount()
					+ materialUnitMap.get(prodOrderItemReqProposal
							.getRefUnitUUID());
			System.out.println(outputContent);

		}
	}

}
