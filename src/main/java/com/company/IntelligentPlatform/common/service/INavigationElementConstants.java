package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.ISystemAuthorizationObject;

public class INavigationElementConstants {
	
	/**
	 * Navigation Group ID area
	 */
	public static final String ID_Group_WorkCenter = "x_menu_ms";
	
	public static final String ID_Group_Logistics = "x_menu_wl";
	
	public static final String ID_Group_VehicleRun = "x_menu_vr";
	
	public static final String ID_Group_TransResources = "x_menu_yl";
	
	public static final String ID_Group_Warehouse = "x_menu_wa";
	
	public static final String ID_Group_Finance = "x_menu_cw";
	
	public static final String ID_Group_SalesDistribution = "x_menu_sd";
	
	public static final String ID_Group_Production = "x_menu_pd";
	
	public static final String ID_Group_Office = "x_menu_bg";
	
	public static final String ID_Group_System = "x_menu_xt";
	/**
	 * Navigation Element ID area
	 */
	// [Group] Logistics management
	public static final String ID_userMessageCenter = "userMessageCenter";
	
	public static final String ID_languageCommandCenter = "LanguageCommandCenter";
	
	public static final String ID_LogisctisMap = "LogisctisMap";
	
	public static final String ID_TransSiteStoreItem = "TransSiteStoreItem";

	public static final String ID_VehicleRunOrderBranch = "VehicleRunOrderBranch";

	public static final String ID_VehicleRunOrderTrunk = "VehicleRunOrderTrunk";
	
	public static final String ID_VehicleDeliveryOrder = "VehicleDeliveryOrder";

	public static final String ID_VehicleRunOrder = "VehicleRunOrder";

	public static final String ID_BookingNote = "BookingNote";
	
	public static final String ID_BookingNoteStandard = "BookingNoteStandard";

	public static final String ID_VehicleRunOrderContract = "VehicleRunOrderContract";
	
	public static final String ID_VehicleRunOrderLoadPlanInArrival = "VehicleRunOrderLoadPlanInArrival";
	
	public static final String ID_DocumentGrant = ISystemAuthorizationObject.AOID_RES_DOCUMENT_GRANT;
	
	public static final String ID_TransitOrder = "TransitOrder";

	public static final String ID_SignPickup = "SignPickup";
	
	public static final String ID_ReceiverNote = "ReceiverNote";
	
	public static final String ID_ExternalExpressOrder = "ExternalExpressOrder";

	public static final String ID_TransitPartner = "TransitPartner";
	
	public static final String ID_TransSite = "TransSite";
	
	public static final String ID_WarehouseStore = "WarehouseStore";
	
	public static final String ID_InboundDelivery = "InboundDelivery";
	
	public static final String ID_OutboundDelivery = "OutboundDelivery";
	
	public static final String ID_InventoryCheckOrder = "InventoryCheckOrder";
	
	public static final String ID_InventoryTransferOrder = "InventoryTransferOrder";
	
	public static final String ID_WarehouseStoreChart = "WarehouseStoreChart";
	
	public static final String ID_SKUFlowRateChart = "SKUFlowRateChart";
	
	public static final String ID_SalesOrder = IServiceModelConstants.SalesOrder;
	
	public static final String ID_SalesReturnOrder = IServiceModelConstants.SalesReturnOrder;
	
	public static final String ID_PurchaseOrder = IServiceModelConstants.PurchaseOrder;

	public static final String ID_VehicleInfo = "VehicleInfo";
	
	public static final String ID_ExternalDriver = "ExternalDriver";
	
	public static final String ID_MaterialType = "MaterialType";
	
	public static final String ID_Supplier = CorporateCustomer.ID_RESOURCE_SUPPLIER;
	
	public static final String ID_Material = "Material";
	
	public static final String ID_MaterialStockKeepUnit = "MaterialStockKeepUnit";
	
	public static final String ID_BillOfMaterial = IServiceModelConstants.BillOfMaterialOrder;
	
	public static final String ID_ProcessBOMOrder = IServiceModelConstants.ProcessBOMOrder;	
	
	public static final String ID_ProductionOrder = IServiceModelConstants.ProductionOrder;	
	
	public static final String ID_ProdJobOrder = IServiceModelConstants.ProdJobOrder;
	
	public static final String ID_ProdWorkCenter = IServiceModelConstants.ProdWorkCenter;
	
	public static final String ID_Warehouse = "Warehouse";
	
	public static final String ID_WarehouseChannel = "WarehouseChannel";
	
	public static final String ID_SerialNumberSetting = IServiceModelConstants.SerialNumberSetting;

	public static final String ID_Equipment = "Equipment";

	public static final String ID_IndividualCustomer = "IndividualCustomer";

	public static final String ID_CorporateCustomer = "CorporateCustomer";
	
	public static final String ID_Distributor = "Distributor";
	
	public static final String ID_Dealer = "Dealer";
	
	public static final String ID_Employee = "Employee";

	public static final String ID_Organization = "Organization";
	
	public static final String ID_HostCompany = "HostCompany";
	
	public static final String ID_CalendarSetting = "CalendarSetting";
	
	public static final String ID_FinReceipt = "FinReceipt";
	
	public static final String ID_FinVoucher = "FinVoucher";	
	
	public static final String ID_VehRunContractCenter = "VehRunContractCenter";	
	
	public static final String ID_AccountSearch = "AccountSearch";	
	
	public static final String ID_AccountRecord = "AccountRecord";	
	
	public static final String ID_AccountPayReceive = "AccountPayReceive";
	
	public static final String ID_FinanceReport = "FinanceReport";	
	
	public static final String ID_AccountTitle = "AccountTitle";
	
	public static final String ID_DocumentSettle = "DocumentSettle";
	
    public static final String ID_ServiceExceptionRecord = "ServiceExceptionRecord";	
	
	public static final String ID_LogonUser = "LogonUser";	
	
	public static final String ID_Role = "Role";
	
	public static final String ID_SystemResource = "SystemResource";
	
	public static final String ID_SystemConfigureResource = "SystemConfigureResource";

	public static final String ID_LanguageCommandTarget = "LanguageCommandTarget";
	
	public static final String ID_AccountSetting = "AccountSetting";
	
	public static final String ID_SalesArea = "SalesArea";
	
	/**
	 * Link Constant area
	 */
	// [Group] Logistics management
	public static final String LINK_userMessageCenter = "../logonUserMessage/loadModuleList.html";
	
	public static final String LINK_languageCommandCenter = "../languageCommandCenter/startChat.html";
	
	public static final String LINK_LogisctisMap = "../logistics/getLogisctisMap.html";
	
	public static final String LINK_TransSiteStoreItem = "../transSiteStoreItem/defaultTransStoreList.html";

	public static final String LINK_VehicleRunOrderBranch = "../vehicleRunOrder/searchBranchService.html";

	public static final String LINK_VehicleRunOrderTrunk = "../vehicleRunOrder/searchTrunkService.html";
	
	public static final String LINK_VehicleDeliveryOrder = "../vehicleRunOrder/searchDeliveryService.html";

	public static final String LINK_VehicleRunOrder = "../vehicleRunOrder/loadModuleList.html";

	public static final String LINK_BookingNote = "../bookingNoteCom/loadModuleList.html";
	
	public static final String LINK_BookingNoteStandard = "../bookingNoteCom/loadModuleListStandard.html";

	public static final String LINK_TransitOrder = "../transitOrder/loadModuleList.html";

	public static final String LINK_SignPickup = "../bookingNoteCom/loadModuleListArrived.html";
	
	public static final String LINK_ReceiverNote = "../receiverNote/loadStoreListDeparture.html";
	
	public static final String LINK_ExternalExpressOrder = "../externalExpressOrder/loadModuleList.html";

	public static final String LINK_VehicleRunOrderContract = "../vehicleRunOrderContract/loadModuleList.html";
	
	public static final String LINK_VehicleRunOrderLoadPlanInArrival = "../vehicleRunOrderLoadingPlan/searchPlanInArrival.html";
	
	public static final String LINK_DocumentGrant = "../vehicleRunOrderContract/loadModuleListGrant.html";
	
	public static final String LINK_TransitPartner = "../transitPartner/loadModuleList.html";
	
	public static final String LINK_TransSite = "../transSite/loadModuleList.html";

	public static final String LINK_VehicleInfo = "../vehicleRecord/loadModuleList.html";
	
	public static final String LINK_ExternalDriver = "../externalDriver/loadModuleList.html";
    
    public static final String LINK_Supplier = "../corporateSupplier/loadModuleList.html";
	
	public static final String LINK_SalesAreaTree = "../salesArea/salesAreaTreeList.html";
	
	public static final String LINK_MaterialType = "../materialType/loadModuleList.html";
	
	public static final String LINK_MaterialTypeTree = "../materialType/materialTypeTreeList.html";
	
	public static final String LINK_Material = "../material/loadModuleList.html";
	
	public static final String LINK_InboundDelivery = "../inboundDelivery/loadModuleListManual.html";
	
	public static final String LINK_WarehouseStore = "../warehouseStoreItem/loadModuleList.html";
	
	public static final String LINK_OutboundDelivery = "../outboundDelivery/loadModuleListManual.html";
	
	public static final String LINK_InventoryCheckOrder = "../inventoryCheckOrder/loadModuleList.html";
	
	public static final String LINK_InventoryTransferOrder = "../inventoryTransferOrder/loadModuleList.html";
	
	public static final String LINK_WarehouseStoreChart = "../warehouseStoreItemLog/initStoreLogGenReport.html";
	
	public static final String LINK_SKUFlowRateChart = "../materialStockKeepUnit/initFlowRateReport.html";
	
	public static final String LINK_MaterialStockKeepUnit = "../materialStockKeepUnit/loadModuleList.html";
	
	public static final String LINK_SalesOrder = "../salesOrder/loadModuleList.html";
	
	public static final String LINK_SalesReturnOrder = "../salesReturnOrder/loadModuleList.html";
	
	public static final String LINK_PurchaseOrder = "../purchaseOrder/loadModuleList.html";
	
	public static final String LINK_Warehouse = "../warehouse/loadModuleList.html";
	
	public static final String LINK_WarehouseChannel = "../warehouse/loadModuleChannelList.html";

	public static final String LINK_BillOfMaterial = "../billOfMaterialOrder/loadModuleList.html";

	public static final String LINK_BillOfMaterialTemplate = "../billOfMaterialTemplate/loadModuleList.html";
	
	public static final String LINK_ProdWorkCenter = "../prodWorkCenter/loadModuleList.html";
	
	public static final String LINK_ProductionOrder = "../productionOrder/loadModuleList.html";
	
	public static final String LINK_ProcessBOMOrder = "../processBOMOrder/loadModuleList.html";
	
	public static final String LINK_SerialNumberSetting = "../serialNumberSetting/loadModuleList.html";

	public static final String LINK_Equipment = "../radioFreqReader/loadModuleList.html";

	public static final String LINK_IndividualCustomer = "../individualCustomer/loadModuleList.html";

	public static final String LINK_CorporateCustomer = "../corporateCustomer/loadModuleList.html";
	
	public static final String LINK_Distributor = "../corporateDistributor/loadDistributorList.html";
	
	public static final String LINK_Dealer = "../corporateDealer/loadDealerList.html";
	
	public static final String LINK_Employee = "../employee/loadModuleList.html";
	
	public static final String LINK_Organization = "../organization/loadModuleList.html";
	
	public static final String LINK_OrganizationTree = "../organization/loadModuleTreeList.html";
	
	public static final String LINK_CalendarSetting = "../calendarSetting/loadModuleList.html";

	public static final String LINK_HostCompany = "../hostCompany/loadModuleList.html";	

	public static final String LINK_FinReceipt = "../finAccount/loadReceiptList.html";	
	
	public static final String LINK_FinVoucher = "../finAccount/loadVoucherList.html";
	
	public static final String LINK_AccountPayReceive = "../account/loadPayReceiveList.html";
	
	public static final String LINK_VehRunContractCenter = "../vehicleRunOrderContract/searchToSettleCenter.html";	
	
	public static final String LINK_AccountSearch = "../finAccount/loadModuleList.html";	
	
	public static final String LINK_AccountRecord = "../finAccount/loadModuleListRecord.html";	
	
	public static final String LINK_FinanceReport = "../finAccount/initGenReport.html";	
	
	public static final String LINK_AccountTitle = "../finAccountTitle/finAccountTreeList.html";
	
	public static final String LINK_DocumentSettle = "../bookingNoteCom/searchToSettleCenter.html";
	
    public static final String LINK_ServiceExceptionRecord = "../serviceExceptionRecord/loadModuleList.html";	
	
	public static final String LINK_LogonUser = "../logonUser/loadModuleList.html";	
	
	public static final String LINK_Role = "../role/loadModuleList.html";
	
	public static final String LINK_SystemResource = "../systemResource/loadModuleList.html";
	
	public static final String LINK_SystemConfigureResourceList = "../systemConfigureCategory/loadModuleList.html";
	
	public static final String LINK_SystemConfigureResourceTree = "../systemConfigureCategory/systemConfigureTreeList.html";

	public static final String LINK_LanguageCommandTarget = "../languageCommandTarget/loadModuleList.html";
	
	public static final String LINK_AccountDuplicateCheck = "../serviceAccountDuplicateCheckResource/loadModuleList.html";
	 

}
