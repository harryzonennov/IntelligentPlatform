package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.dto.BookingStandardSKUChooseTypeSwitch;
import com.company.IntelligentPlatform.common.service.INavigationElementConstants;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.SystemConfigureResource;

public interface ISystemConfigureResourceConstants {
	
	/**
	 * System Configure Category area
	 */
    String ID_CATE_LogisticsManage = "CATE_LCM";
	
	String ID_CATE_LogisticsResource = "CATE_LCR";
	
	String ID_CATE_Finance = "CATE_FIN";
	
	String ID_CATE_WarehouseManage = "CATE_WMA";
	
	String ID_CATE_SystemConfigure = "CATE_SYSTEM";
	
	String ID_CATE_BasicData = "CATE_BASICDATA";
	
	String ID_CATE_CustomerRelationship = "CATE_CRM";
	
	String ID_CATE_Production = "CATE_PRD";
	
	String ID_CATE_HumanResource = "CATE_HCM";
	
	String ID_CATE_SalesDistribution = "CATE_SD";
	
	/**
	 * System Configure Resource area
	 */
	// [Category] user center
    String ID_RES_UserMessageCenter = INavigationElementConstants.ID_userMessageCenter;
		
	// [Category] Logistics management
    String ID_RES_BookingNote = IServiceModelConstants.BookingNote;
	
	String ID_RES_BookingNoteStandard = INavigationElementConstants.ID_BookingNoteStandard;
	
	
	String ID_RES_TransitOrder = IServiceModelConstants.TransitOrder;
	
	String ID_RES_ReceiverNote = IServiceModelConstants.ReceiverNote;
	
	String ID_RES_LCM_DocumentGrant = "LCMDocumentGrant";
	
	String ID_RES_VehicleRunOrder = IServiceModelConstants.VehicleRunOrder;
	
	String ID_RES_VehicleRunOrderContract = IServiceModelConstants.VehicleRunOrderContract;
	
	String ID_RES_ExternalExpressOrder = IServiceModelConstants.ExternalExpressOrder;
	
	// [Category] Logistics Resource
    String ID_RES_TransSite = IServiceModelConstants.TransSite;
	
	String ID_RES_VehicleInfo = IServiceModelConstants.VehicleInfo;
	
	String ID_RES_TransitPartner = IServiceModelConstants.TransitPartner;
	
	String ID_RES_ExternalDriver = IServiceModelConstants.ExternalDriver;

	// [Category] Basic data
    String ID_RES_Material = IServiceModelConstants.Material;
    
    String ID_RES_SerialNumberSetting = IServiceModelConstants.SerialNumberSetting;
	
	String ID_RES_MaterialStockKeepUnit = IServiceModelConstants.MaterialStockKeepUnit;
	
    String ID_RES_MaterialType = IServiceModelConstants.MaterialType;
    
    String ID_RES_Supplier = CorporateCustomer.ID_RESOURCE_SUPPLIER;
    
    String ID_RES_Equipment = INavigationElementConstants.ID_Equipment;
	
	String ID_RES_Warehouse = IServiceModelConstants.Warehouse;
	
	String ID_RES_Organization = IServiceModelConstants.Organization;
	
	String ID_RES_CalendarSetting = IServiceModelConstants.CalendarSetting;
	
	String ID_RES_HostCompany = IServiceModelConstants.HostCompany;
	
	// [Category] Finance Resource
    String ID_RES_FinanceAccount = IServiceModelConstants.FinAccount;
	
	String ID_RES_FinanceAccountTitle = IServiceModelConstants.FinAccountTitle;
	
	String ID_RES_AccountPayReceive = INavigationElementConstants.ID_AccountPayReceive;
	
    String ID_RES_FinanceReport = INavigationElementConstants.ID_FinanceReport;
    
    String ID_RES_DocumentSettle = INavigationElementConstants.ID_DocumentSettle;
	
    // [Category] Warehouse Management
    String ID_RES_PurchaseOrder = INavigationElementConstants.ID_PurchaseOrder;
    
    String ID_RES_InboundDelivery = IServiceModelConstants.InboundDelivery;
    
    String ID_RES_OutboundDelivery = IServiceModelConstants.OutboundDelivery;
    
	String ID_RES_InventoryCheckOrder = IServiceModelConstants.InventoryCheckOrder;
	
	String ID_RES_InventoryTransferOrder = INavigationElementConstants.ID_InventoryTransferOrder;
	
	String ID_RES_WarehouseStoreItem = IServiceModelConstants.WarehouseStoreItem;
	
	String ID_RES_WarehouseStoreChart = INavigationElementConstants.ID_WarehouseStoreChart;
	
	String ID_RES_SKUFlowRateChart = INavigationElementConstants.ID_SKUFlowRateChart;

	String ID_RES_SalesArea = IServiceModelConstants.SalesArea;
	
	String ID_RES_SalesOrder = INavigationElementConstants.ID_SalesOrder;

	String ID_RES_SalesContract = IServiceModelConstants.SalesContract;
	
	String ID_RES_SalesReturnOrder = IServiceModelConstants.SalesReturnOrder;

	String ID_RES_SalesForcast = IServiceModelConstants.SalesForcast;
	
	String ID_RES_Distributor = INavigationElementConstants.ID_Distributor;
	    
	String ID_RES_Dealer = INavigationElementConstants.ID_Dealer;
	
	String ID_RES_WarehouseChannel = INavigationElementConstants.ID_WarehouseChannel;
	
	// [Category] Production
    String ID_RES_BillOfMaterial = INavigationElementConstants.ID_BillOfMaterial;
	
	String ID_RES_ProductionOrder = INavigationElementConstants.ID_ProductionOrder;
	
	String ID_RES_ProdWorkCenter = INavigationElementConstants.ID_ProdWorkCenter;
	
	String ID_RES_ProcessBOMOrder = INavigationElementConstants.ID_ProcessBOMOrder;
	
	String ID_RES_ProductionResourceUnion = IServiceModelConstants.ProductionResourceUnion;
	
	// [Category] HCM
    String ID_RES_Employee = IServiceModelConstants.Employee;
    
    // [Category] Customer Relationship 
    String ID_RES_CorporateCustomer = IServiceModelConstants.CorporateCustomer;
    
	String ID_RES_IndividualCustomer = IServiceModelConstants.IndividualCustomer;
	
	 // [Category] System Configure
     String ID_RES_LogonUser = IServiceModelConstants.LogonUser;
    
    String ID_RES_ServiceExceptionRecord = INavigationElementConstants.ID_ServiceExceptionRecord;
    
	String ID_RES_Authorization = IServiceModelConstants.Role;
	
	String ID_RES_SystemConfigure = SystemConfigureResource.NODENAME;
	
	
	String ID_RES_SystemResource = IServiceModelConstants.SystemResource;
	
	String ID_RES_LanguageCommandTarget = IServiceModelConstants.LanguageCommandTarget;
	
	String ID_RES_AccountSetting = "AccountSetting";
	
    /**
     * Self defined element ID area
     */
	// configure element for booking note standard SKU Item choose type
    String ID_ELE_BookingStandardItemChoose = BookingStandardSKUChooseTypeSwitch.ELEMENT_ID;
	
	String ID_ELE_InboundApproveProcess = "InboundApproveProcess";
	
	String ID_ELE_OutboundApproveProcess = "OutboundApproveProcess";

	/**
	 * Defined extension ID area
	 */
    String EXTUNION_SalesContractTargetMatFilter = "SalesContractTargetMatFilter";

	String EXTUNION_SalesReturnMatWarehouseFilter = "SalesReturnMatWarehouseFilter";

}
