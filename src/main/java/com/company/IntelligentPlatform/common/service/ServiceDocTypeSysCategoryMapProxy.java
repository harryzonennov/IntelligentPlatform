package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.dto.ISystemConfigureResourceConstants;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;

/**
 * Union model for mapping from service document type to system resource category
 * @author zhang,hang
 *
 */
public class ServiceDocTypeSysCategoryMapProxy {
	
	/**
	 * Main entrance to get all service docment type to system category map list
	 * @return
	 */
	public static List<ServiceDocTypeSysCategoryMapUnion> getDocTypeSysCategoryList(){
		List<ServiceDocTypeSysCategoryMapUnion> result = new ArrayList<ServiceDocTypeSysCategoryMapUnion>();
		result.addAll(getSalesOrderToSysCategoryList());
		result.addAll(getSalesReturnOrderToSysCategoryList());
		result.addAll(getTransitOrderToSysCategoryList());
		result.addAll(getPurchaseOrderToSysCategoryList());
		result.addAll(getProductionOrderToSysCategoryList());
		result.addAll(getBookingNoteToSysCategoryList());
		result.addAll(getVehicleRunOrderContractToSysCategoryList());
		result.addAll(getVehicleRunOrderToSysCategoryList());
		result.addAll(getReceiptToSysCategoryList());
		return result;
	}
	
	/**
	 * Return the [sales order] mapping to system category list
	 * @return
	 */
	public static List<ServiceDocTypeSysCategoryMapUnion> getSalesOrderToSysCategoryList(){
		List<ServiceDocTypeSysCategoryMapUnion> result = new ArrayList<ServiceDocTypeSysCategoryMapUnion>();
		ServiceDocTypeSysCategoryMapUnion sdUnion = new ServiceDocTypeSysCategoryMapUnion();
		sdUnion.setCategoryID(ISystemConfigureResourceConstants.ID_CATE_SalesDistribution);
		sdUnion.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_SALESORDER);
		result.add(sdUnion);		
		return result;
	}
	
	/**
	 * Return the [sales return  order] mapping to system category list
	 * @return
	 */
	public static List<ServiceDocTypeSysCategoryMapUnion> getSalesReturnOrderToSysCategoryList(){
		List<ServiceDocTypeSysCategoryMapUnion> result = new ArrayList<ServiceDocTypeSysCategoryMapUnion>();
		ServiceDocTypeSysCategoryMapUnion sdUnion = new ServiceDocTypeSysCategoryMapUnion();
		sdUnion.setCategoryID(ISystemConfigureResourceConstants.ID_CATE_SalesDistribution);
		sdUnion.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_SALESRETURNORDER);
		result.add(sdUnion);		
		return result;
	}
	
	/**
	 * Return the [purchase order] mapping to system category list
	 * @return
	 */
	public static List<ServiceDocTypeSysCategoryMapUnion> getPurchaseOrderToSysCategoryList(){
		List<ServiceDocTypeSysCategoryMapUnion> result = new ArrayList<ServiceDocTypeSysCategoryMapUnion>();
		ServiceDocTypeSysCategoryMapUnion sdUnion = new ServiceDocTypeSysCategoryMapUnion();
		sdUnion.setCategoryID(ISystemConfigureResourceConstants.ID_CATE_SalesDistribution);
		sdUnion.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_PURCHASE);
		result.add(sdUnion);
		ServiceDocTypeSysCategoryMapUnion wareUnion = new ServiceDocTypeSysCategoryMapUnion();
		wareUnion.setCategoryID(ISystemConfigureResourceConstants.ID_CATE_WarehouseManage);
		wareUnion.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_PURCHASE);
		result.add(wareUnion);
		return result;
	}
	
	/**
	 * Return the [production order] mapping to system category list
	 * @return
	 */
	public static List<ServiceDocTypeSysCategoryMapUnion> getProductionOrderToSysCategoryList(){
		List<ServiceDocTypeSysCategoryMapUnion> result = new ArrayList<ServiceDocTypeSysCategoryMapUnion>();
		ServiceDocTypeSysCategoryMapUnion pdUnion = new ServiceDocTypeSysCategoryMapUnion();
		pdUnion.setCategoryID(ISystemConfigureResourceConstants.ID_CATE_Production);
		pdUnion.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER);
		result.add(pdUnion);		
		return result;
	}
	
	/**
	 * Return the [booking note] mapping to system category list
	 * @return
	 */
	public static List<ServiceDocTypeSysCategoryMapUnion> getBookingNoteToSysCategoryList(){
		List<ServiceDocTypeSysCategoryMapUnion> result = new ArrayList<ServiceDocTypeSysCategoryMapUnion>();
		ServiceDocTypeSysCategoryMapUnion logUnion = new ServiceDocTypeSysCategoryMapUnion();
		logUnion.setCategoryID(ISystemConfigureResourceConstants.ID_CATE_LogisticsManage);
		logUnion.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_BOOKINGNOTE);
		result.add(logUnion);
		ServiceDocTypeSysCategoryMapUnion logResUnion = new ServiceDocTypeSysCategoryMapUnion();
		logResUnion.setCategoryID(ISystemConfigureResourceConstants.ID_CATE_LogisticsResource);
		logResUnion.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_BOOKINGNOTE);
		result.add(logResUnion);
		return result;
	}
	
	/**
	 * Return the [transit order] mapping to system category list
	 * @return
	 */
	public static List<ServiceDocTypeSysCategoryMapUnion> getTransitOrderToSysCategoryList(){
		List<ServiceDocTypeSysCategoryMapUnion> result = new ArrayList<ServiceDocTypeSysCategoryMapUnion>();
		ServiceDocTypeSysCategoryMapUnion logUnion = new ServiceDocTypeSysCategoryMapUnion();
		logUnion.setCategoryID(ISystemConfigureResourceConstants.ID_CATE_LogisticsManage);
		logUnion.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_TRANSITORDER);
		result.add(logUnion);
		ServiceDocTypeSysCategoryMapUnion logResUnion = new ServiceDocTypeSysCategoryMapUnion();
		logResUnion.setCategoryID(ISystemConfigureResourceConstants.ID_CATE_LogisticsResource);
		logResUnion.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_TRANSITORDER);
		result.add(logResUnion);
		return result;
	}
	
	/**
	 * Return the [vr run order] mapping to system category list
	 * @return
	 */
	public static List<ServiceDocTypeSysCategoryMapUnion> getVehicleRunOrderToSysCategoryList(){
		List<ServiceDocTypeSysCategoryMapUnion> result = new ArrayList<ServiceDocTypeSysCategoryMapUnion>();
		ServiceDocTypeSysCategoryMapUnion logUnion = new ServiceDocTypeSysCategoryMapUnion();
		logUnion.setCategoryID(ISystemConfigureResourceConstants.ID_CATE_LogisticsManage);
		logUnion.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_VEHICLERUNORDER);
		result.add(logUnion);
		ServiceDocTypeSysCategoryMapUnion logResUnion = new ServiceDocTypeSysCategoryMapUnion();
		logResUnion.setCategoryID(ISystemConfigureResourceConstants.ID_CATE_LogisticsResource);
		logResUnion.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_VEHICLERUNORDER);
		result.add(logResUnion);
		return result;
	}
	
	/**
	 * Return the [vr contract] mapping to system category list
	 * @return
	 */
	public static List<ServiceDocTypeSysCategoryMapUnion> getVehicleRunOrderContractToSysCategoryList(){
		List<ServiceDocTypeSysCategoryMapUnion> result = new ArrayList<ServiceDocTypeSysCategoryMapUnion>();
		ServiceDocTypeSysCategoryMapUnion logUnion = new ServiceDocTypeSysCategoryMapUnion();
		logUnion.setCategoryID(ISystemConfigureResourceConstants.ID_CATE_LogisticsManage);
		logUnion.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_VEHICLERUNORDERCONTRACT);
		result.add(logUnion);
		ServiceDocTypeSysCategoryMapUnion logResUnion = new ServiceDocTypeSysCategoryMapUnion();
		logResUnion.setCategoryID(ISystemConfigureResourceConstants.ID_CATE_LogisticsResource);
		logResUnion.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_VEHICLERUNORDERCONTRACT);
		result.add(logResUnion);
		return result;
	}
	
	/**
	 * Return the [receipt] mapping to system category list
	 * @return
	 */
	public static List<ServiceDocTypeSysCategoryMapUnion> getReceiptToSysCategoryList(){
		List<ServiceDocTypeSysCategoryMapUnion> result = new ArrayList<ServiceDocTypeSysCategoryMapUnion>();
		ServiceDocTypeSysCategoryMapUnion logUnion = new ServiceDocTypeSysCategoryMapUnion();
		logUnion.setCategoryID(ISystemConfigureResourceConstants.ID_CATE_LogisticsManage);
		logUnion.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_RECEIPT);
		result.add(logUnion);
		ServiceDocTypeSysCategoryMapUnion logResUnion = new ServiceDocTypeSysCategoryMapUnion();
		logResUnion.setCategoryID(ISystemConfigureResourceConstants.ID_CATE_LogisticsResource);
		logResUnion.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_RECEIPT);
		result.add(logResUnion);
		return result;
	}

}
