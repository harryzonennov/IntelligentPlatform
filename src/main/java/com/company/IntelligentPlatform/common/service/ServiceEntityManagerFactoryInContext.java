package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Service Entity Manager factory to get Manager instance in Spring Context
 * @author Zhang,Hang
 *
 */
@Service
public class ServiceEntityManagerFactoryInContext {
	
	@Autowired
	protected SpringContextBeanService springContextBeanService;
	
	public static final String transSiteManager = "transSiteManager";
	
	public static final String hostCompanyManager = "hostCompanyManager";

	public static final String organizationManager = "organizationManager";

	public static final String employeeManager = "employeeManager";

	public static final String logonUserManager = "logonUserManager";

	public static final String roleManager = "roleManager";

	public static final String authorizationObjectManager = "authorizationObjectManager";

	public static final String individualCustomerManager = "individualCustomerManager";
	
	public static final String bookingNoteManager = "bookingNoteManager";
	
	public static final String vehicleRunOrderManager = "vehicleRunOrderManager";

	public static final String vehicleRunOrderContractManager = "vehicleRunOrderContractManager";

	public static final String transitOrderManager = "transitOrderManager";

	public static final String inboundDeliveryManager = "inboundDeliveryManager";

	public static final String outboundDeliveryManager = "outboundDeliveryManager";

	public static final String inventoryCheckOrderManager = "inventoryCheckOrderManager";

	public static final String inventoryTransferOrderManager = "inventoryTransferOrderManager";

	public static final String salesOrderManager = "salesOrderManager";

	public static final String salesReturnOrderManager = "salesReturnOrderManager";

	public static final String purchaseOrderManager = "purchaseOrderManager";

	public static final String purchaseContractManager = "purchaseContractManager";

	public static final String purchaseRequestManager = "purchaseRequestManager";

	public static final String purchaseReturnOrderManager = "purchaseReturnOrderManager";

	public static final String salesContractManager = "salesContractManager";

	public static final String salesForcastManager = "salesForcastManager";

	public static final String inquiryManager = "inquiryManager";

	public static final String purchaseSettleManager = "purchaseSettleManager";

	public static final String productionOrderManager = "productionOrderManager";
	
	public static final String productionResourceUnionManager = "productionResourceUnionManager";
	
	public static final String prodWorkCenterManager = "prodWorkCenterManager";

	public static final String productionPlanManager = "productionPlanManager";

	public static final String prodPickingOrderManager = "prodPickingOrderManager";
	
	public static final String radioFreqReaderManager = "radioFreqReaderManager";
	
	public static final String finAccountManager = "finAccountManager";
	
	public static final String finAccountTitleManager = "finAccountTitleManager";
	
	public static final String externalDriverManager = "externalDriverManager";
	
	public static final String transitPartnerManager = "transitPartnerManager";
	
	public static final String salesAreaManager = "salesAreaManager";
	
	public static final String warehouseManager = "warehouseManager";

	public static final String warehouseStoreManager = "warehouseStoreManager";
	
	public static final String materialManager = "materialManager";
	
	public static final String materialStockKeepUnitManager = "materialStockKeepUnitManager";
	
	public static final String materialTypeManager = "materialTypeManager";
	
	public static final String qualityInspectOrderManager = "qualityInspectOrderManager";
	
	public static final String serialNumberSettingManager = "serialNumberSettingManager";
	
	public static final String corporateCustomerManager = "corporateCustomerManager";
	
	public static final String corporateSupplierManager = "corporateSupplierManager";
	
	public static final String registeredProductManager = "registeredProductManager";
	
	public static final String billOfMaterialOrderManager = "billOfMaterialOrderManager";

	public static final String billOfMaterialTemplateManager = "billOfMaterialTemplateManager";

	public static final String wasteProcessOrderManager = "wasteProcessOrderManager";

	public static final String repairProdOrderManager = "repairProdOrderManager";
	
	@Qualifier(transSiteManager)
	@Autowired(required=false)
	protected ServiceEntityManager transSiteManagerImp;	

	@Qualifier(hostCompanyManager)
	@Autowired(required=false)
	protected ServiceEntityManager hostCompanyManagerImp;
	
	@Qualifier(organizationManager)
	@Autowired(required=false)
	protected ServiceEntityManager organizationManagerImp;	

	@Qualifier(employeeManager)
	@Autowired(required=false)
	protected ServiceEntityManager employeeManagerImp;
	
	@Qualifier(individualCustomerManager)
	@Autowired(required=false)
	protected ServiceEntityManager individualCustomerManagerImp;
	
	@Qualifier(bookingNoteManager)
	@Autowired(required=false)
	protected ServiceEntityManager bookingNoteManagerImp;
	
	@Qualifier(vehicleRunOrderManager)
	@Autowired(required=false)
	protected ServiceEntityManager vehicleRunOrderManagerImp;
	
	@Qualifier(vehicleRunOrderContractManager)
	@Autowired(required=false)
	protected ServiceEntityManager vehicleRunOrderContractManagerImp;
	
	@Qualifier(transitOrderManager)
	@Autowired(required=false)
	protected ServiceEntityManager transitOrderManagerImp;
	
	@Qualifier(inboundDeliveryManager)
	@Autowired(required=false)
	protected ServiceEntityManager inboundDeliveryManagerImp;
	
	@Qualifier(outboundDeliveryManager)
	@Autowired(required=false)
	protected ServiceEntityManager outboundDeliveryManagerImp;
	
	@Qualifier(inventoryCheckOrderManager)
	@Autowired(required=false)
	protected ServiceEntityManager inventoryCheckOrderManagerImp;
	
	@Qualifier(inventoryTransferOrderManager)
	@Autowired(required=false)
	protected ServiceEntityManager inventoryTransferOrderManagerImp;
	
	@Qualifier(salesOrderManager)
	@Autowired(required=false)
	protected ServiceEntityManager salesOrderManagerImp;

	@Qualifier(salesReturnOrderManager)
	@Autowired(required=false)
	protected ServiceEntityManager salesReturnOrderManagerImp;

	@Qualifier(salesForcastManager)
	@Autowired(required=false)
	protected ServiceEntityManager salesForcastManagerImp;
	
	@Qualifier(purchaseOrderManager)
	@Autowired(required=false)
	protected ServiceEntityManager purchaseOrderManagerImp;
	
	@Qualifier(purchaseContractManager)
	@Autowired(required=false)
	protected ServiceEntityManager purchaseContractManagerImp;

	@Qualifier(purchaseRequestManager)
	@Autowired(required=false)
	protected ServiceEntityManager purchaseRequestManagerImp;

	@Qualifier(purchaseReturnOrderManager)
	@Autowired(required=false)
	protected ServiceEntityManager purchaseReturnOrderManagerImp;
	
	@Qualifier(salesContractManager)
	@Autowired(required=false)
	protected ServiceEntityManager salesContractManagerImp;

	@Qualifier(inquiryManager)
	@Autowired(required=false)
	protected ServiceEntityManager inquiryManagerImp;

	@Qualifier(purchaseSettleManager)
	@Autowired(required=false)
	protected ServiceEntityManager purchaseSettleManagerImp;

	@Qualifier(productionOrderManager)
	@Autowired(required=false)
	protected ServiceEntityManager productionOrderManagerImp;

	@Qualifier(productionPlanManager)
	@Autowired(required=false)
	protected ServiceEntityManager productionPlanManagerImp;

	@Qualifier(prodPickingOrderManager)
	@Autowired(required=false)
	protected ServiceEntityManager prodPickingOrderManagerImp;

	
	protected ServiceEntityManager radioFreqReaderManagerImp;	

	@Qualifier(finAccountManager)
	@Autowired(required=false)
	protected ServiceEntityManager finAccountManagerImp;

	@Qualifier(externalDriverManager)
	@Autowired(required=false)
	protected ServiceEntityManager externalDriverManagerImp;

	@Qualifier(salesAreaManager)
	@Autowired(required=false)
	protected ServiceEntityManager salesAreaManagerImp;

	@Qualifier(warehouseManager)
	@Autowired(required=false)
	protected ServiceEntityManager warehouseManagerImp;

	@Qualifier(warehouseStoreManager)
	@Autowired(required=false)
	protected ServiceEntityManager warehouseStoreManagerImp;
	
	@Qualifier(qualityInspectOrderManager)
	@Autowired(required=false)
	protected ServiceEntityManager qualityInspectOrderManagerImp;

	@Qualifier(transitPartnerManager)
	@Autowired(required=false)
	protected ServiceEntityManager transitPartnerManagerImp;
	
	@Qualifier(materialManager)
	@Autowired(required=false)
	protected ServiceEntityManager materialManagerImp;
	
	@Qualifier(materialStockKeepUnitManager)
	@Autowired(required=false)
	protected ServiceEntityManager materialStockKeepUnitManagerImp;

	@Qualifier(materialTypeManager)
	@Autowired(required=false)
	protected ServiceEntityManager materialTypeManagerImp;

	@Qualifier(serialNumberSettingManager)
	@Autowired(required=false)
	protected ServiceEntityManager serialNumberSettingManagerImp;
	
	@Qualifier(corporateCustomerManager)
	@Autowired(required=false)
	protected ServiceEntityManager corporateCustomerManagerImp;
	
	@Qualifier(corporateSupplierManager)
	@Autowired(required=false)
	protected ServiceEntityManager corporateSupplierManagerImp;
	
	@Qualifier(billOfMaterialOrderManager)
	@Autowired(required=false)
	protected ServiceEntityManager billOfMaterialOrderManagerImp;

	@Qualifier(billOfMaterialTemplateManager)
	@Autowired(required=false)
	protected ServiceEntityManager billOfMaterialTemplateManagerImp;

	@Qualifier(wasteProcessOrderManager)
	@Autowired(required=false)
	protected ServiceEntityManager wasteProcessOrderManagerImp;

	@Qualifier(repairProdOrderManager)
	@Autowired(required=false)
	protected ServiceEntityManager repairProdOrderManagerImp;

	@Qualifier(registeredProductManager)
	@Autowired(required=false)
	protected ServiceEntityManager registeredProductManagerImp;
	
	public ServiceEntityManager getManagerByManagerName(String seManagerName){
		if(ServiceEntityStringHelper.checkNullString(seManagerName)){
			return null;
		}
		if(seManagerName.equals(transitOrderManager)){
			return this.transitOrderManagerImp;
		}
		if(seManagerName.equals(organizationManager)){
			return this.organizationManagerImp;
		}
		if(seManagerName.equals(employeeManager)){
			return this.employeeManagerImp;
		}
		if(seManagerName.equals(individualCustomerManager)){
			return this.individualCustomerManagerImp;
		}
		if(seManagerName.equals(hostCompanyManager)){
			return this.hostCompanyManagerImp;
		}
		if(seManagerName.equals(bookingNoteManager)){
			return this.bookingNoteManagerImp;
		}
		if(seManagerName.equals(vehicleRunOrderManager)){
			return this.vehicleRunOrderManagerImp;
		}
		if(seManagerName.equals(vehicleRunOrderContractManager)){
			return this.vehicleRunOrderContractManagerImp;
		}
		if(seManagerName.equals(inboundDeliveryManager)){
			return this.inboundDeliveryManagerImp;
		}
		if(seManagerName.equals(outboundDeliveryManager)){
			return this.outboundDeliveryManagerImp;
		}
		if(seManagerName.equals(inventoryCheckOrderManager)){
			return this.inventoryCheckOrderManagerImp;
		}
		if(seManagerName.equals(inventoryTransferOrderManager)){
			return this.inventoryTransferOrderManagerImp;
		}
		if(seManagerName.equals(salesOrderManager)){
			return this.salesOrderManagerImp;
		}
		if(seManagerName.equals(salesContractManager)){
			return this.salesContractManagerImp;
		}
		if(seManagerName.equals(salesReturnOrderManager)){
			return this.salesReturnOrderManagerImp;
		}
		if(seManagerName.equals(purchaseContractManager)){
			return this.purchaseContractManagerImp;
		}
		if(seManagerName.equals(purchaseReturnOrderManager)){
			return this.purchaseReturnOrderManagerImp;
		}
		if(seManagerName.equals(purchaseRequestManager)){
			return this.purchaseRequestManagerImp;
		}
		if(seManagerName.equals(purchaseSettleManager)){
			return this.purchaseSettleManagerImp;
		}
		if(seManagerName.equals(purchaseOrderManager)){
			return this.purchaseOrderManagerImp;
		}
		if(seManagerName.equals(salesForcastManager)){
			return this.salesForcastManagerImp;
		}
		if(seManagerName.equals(prodPickingOrderManager)){
			return this.prodPickingOrderManagerImp;
		}
		if(seManagerName.equals(radioFreqReaderManager)){
			return this.radioFreqReaderManagerImp;
		}
		if(seManagerName.equals(finAccountManager)){
			return this.finAccountManagerImp;
		}
		if(seManagerName.equals(externalDriverManager)){
			return externalDriverManagerImp;
		}
		if(seManagerName.equals(transitPartnerManager)){
			return transitOrderManagerImp;
		}
		if(seManagerName.equals(salesAreaManager)){
			return this.salesAreaManagerImp;
		}
		if(seManagerName.equals(inquiryManager)){
			return this.inquiryManagerImp;
		}
		if(seManagerName.equals(warehouseManager)){
			return this.warehouseManagerImp;
		}
		if(seManagerName.equals(qualityInspectOrderManager)){
			return this.qualityInspectOrderManagerImp;
		}
		if(seManagerName.equals(productionOrderManager)){
			return this.productionOrderManagerImp;
		}
		if(seManagerName.equals(serialNumberSettingManager)){
			return this.serialNumberSettingManagerImp;
		}
		if(seManagerName.equals(corporateCustomerManager)){
			return this.corporateCustomerManagerImp;
		}
		if(seManagerName.equals(billOfMaterialTemplateManager)){
			return this.billOfMaterialTemplateManagerImp;
		}
		if(seManagerName.equals(billOfMaterialOrderManager)){
			return this.billOfMaterialOrderManagerImp;
		}
		if(seManagerName.equals(wasteProcessOrderManager)){
			return this.wasteProcessOrderManagerImp;
		}
		if(seManagerName.equals(registeredProductManager)){
			return this.registeredProductManagerImp;
		}
		// Finally return by xml configuration (NOT recommended)
		return (ServiceEntityManager)springContextBeanService.getBean(seManagerName);
	}

	
	/**
	 * Get Service Entity Manager instance from Spring Context
	 * @param serviceEntityName
	 * @return
	 */
	public ServiceEntityManager getManagerBySEName(String serviceEntityName){
		if(ServiceEntityStringHelper.checkNullString(serviceEntityName)){
			return null;
		}
		if(serviceEntityName.equals(IServiceModelConstants.TransSite)){
			return this.transSiteManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(transSiteManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.Organization)){
			return this.organizationManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(organizationManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.Employee)){
			return this.organizationManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(organizationManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.HostCompany)){
			return this.hostCompanyManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(hostCompanyManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.BookingNote)){
			return this.bookingNoteManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(bookingNoteManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.VehicleRunOrder)){
			return this.vehicleRunOrderManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(vehicleRunOrderManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.VehicleRunOrderContract)){
			return this.vehicleRunOrderContractManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(vehicleRunOrderContractManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.TransitOrder)){
			return this.transitOrderManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(transitOrderManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.InboundDelivery)){
			return this.inboundDeliveryManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(inboundDeliveryManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.OutboundDelivery)){
			return this.outboundDeliveryManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(outboundDeliveryManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.InventoryCheckOrder)){
			return this.inventoryCheckOrderManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(inventoryCheckOrderManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.InventoryTransferOrder)){
			return this.inventoryTransferOrderManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(inventoryTransferOrderManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.SalesOrder)){
			return this.salesOrderManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(salesOrderManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.SalesContract)){
			return this.salesContractManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(salesContractManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.SalesReturnOrder)){
			return this.salesReturnOrderManagerImp;
		}
		if(serviceEntityName.equals(IServiceModelConstants.SalesForcast)){
			return this.salesForcastManagerImp;
		}
		if(serviceEntityName.equals(IServiceModelConstants.PurchaseContract)){
			return this.purchaseContractManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(purchaseContractManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.PurchaseRequest)){
			return this.purchaseRequestManagerImp;
		}
		if(serviceEntityName.equals(IServiceModelConstants.PurchaseReturnOrder)){
			return this.purchaseReturnOrderManagerImp;
		}
		if(serviceEntityName.equals(IServiceModelConstants.PurchaseOrder)){
			return this.purchaseOrderManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(purchaseOrderManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.ProductionOrder)){
			return this.productionOrderManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(productionOrderManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.ProductionPlan)){
			return this.productionPlanManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(productionPlanManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.ProdPickingOrder)){
			return this.prodPickingOrderManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(prodPickingOrderManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.RadioFreqReader)){
			return this.radioFreqReaderManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(radioFreqReaderManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.FinAccount)){
			return this.finAccountManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(finAccountManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.ExternalDriver)){
			return externalDriverManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(externalDriverManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.TransitPartner)){
			return transitPartnerManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(transitPartnerManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.SalesArea)){
			return this.salesAreaManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(salesAreaManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.Inquiry)){
			return this.inquiryManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(inquiryManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.BillOfMaterialOrder)){
			return this.billOfMaterialOrderManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(salesContractManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.BillOfMaterialTemplate)){
			return this.billOfMaterialTemplateManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(purchaseContractManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.WasteProcessOrder)){
			return this.wasteProcessOrderManagerImp;
		}		
		if(serviceEntityName.equals(IServiceModelConstants.Warehouse)){
			return this.warehouseManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(warehouseManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.QualityInspectOrder)){
			return this.qualityInspectOrderManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(qualityInspectOrderManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.ProdOrderReport)){
			return this.productionOrderManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(productionOrderManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.SerialNumberSetting)){
			return this.serialNumberSettingManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(serialNumberSettingManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.CorporateCustomer)){
			return this.corporateCustomerManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(corporateCustomerManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.IndividualCustomer)){
			return this.individualCustomerManagerImp;
		}
		if(serviceEntityName.equals(IServiceModelConstants.WarehouseStoreItem)){
			return this.warehouseStoreManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(warehouseManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.WarehouseStore)){
			return this.warehouseStoreManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(warehouseManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.ProductionOrderItem)){
			return this.productionOrderManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(warehouseManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.ProductionPlanItem)){
			return this.productionPlanManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(warehouseManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.RegisteredProduct)){
			return this.registeredProductManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(registeredProductManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.RepairProdOrder)){
			return this.repairProdOrderManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(registeredProductManager);
		}
		if(serviceEntityName.equals(IServiceModelConstants.RepairProdOrderItem)){
			return this.repairProdOrderManagerImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(registeredProductManager);
		}
		return null;
	}

}
