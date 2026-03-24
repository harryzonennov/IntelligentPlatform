package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;

/**
 * The constant interface to store all kinds of resource object (general the SE
 * model) especially which could have relationship to finance
 * 
 * @author Zhang,Hang
 * 
 */
public interface IDefResourceObject {

String ID_BOOKINGNOTE = IServiceModelConstants.BookingNote;
	
	String ID_VEHRUNORDER = IServiceModelConstants.VehicleRunOrder;
	
	String ID_TRANSITEORDER = IServiceModelConstants.TransitOrder;
	
	String ID_VEHICLERUNORDERCONTRACT = IServiceModelConstants.VehicleRunOrderContract;
	
	String ID_RECEIVERNOTE = IServiceModelConstants.ReceiverNote;
	
	String ID_EXTERNALEXPRESSORDER = IServiceModelConstants.ExternalExpressOrder;
	
	String ID_INBOUND_DELIVERY = IServiceModelConstants.InboundDelivery;
	
	String ID_OUTBOUND_DELIVERY = IServiceModelConstants.OutboundDelivery;
	
	String ID_INVENTORYCHECK = IServiceModelConstants.InventoryCheckOrder;
	
	String ID_SALESORDER = IServiceModelConstants.SalesOrder;
	
	String ID_PURCHASEORDER = IServiceModelConstants.PurchaseOrder;
	
	String ID_SALESRETURNORDER = IServiceModelConstants.SalesReturnOrder;

}
