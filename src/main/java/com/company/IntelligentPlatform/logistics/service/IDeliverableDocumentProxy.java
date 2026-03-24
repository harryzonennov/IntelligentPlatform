package com.company.IntelligentPlatform.logistics.service;

import java.util.List;

import com.company.IntelligentPlatform.logistics.model.Delivery;
import com.company.IntelligentPlatform.logistics.model.DeliveryItem;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Proxy on Delivery life-cycle events and handler on Deliverable Document
 * 
 * @author Zhang, Hang
 *
 */
public interface IDeliverableDocumentProxy {

	public void onDeliveryCreated(ServiceEntityNode sourceDocument,
			Delivery delivery, List<ServiceEntityNode> deliveryItemList);
	
	public void onDeliveryItemCreated(ServiceEntityNode sourceDocument,
			DeliveryItem deliveryItem);
	
	public void onDeliveryApproved(ServiceEntityNode sourceDocument,
			Delivery delivery, List<ServiceEntityNode> deliveryItemList);
	
	public void onDeliveryRejected(ServiceEntityNode sourceDocument,
			Delivery delivery, List<ServiceEntityNode> deliveryItemList);
	
	public void onDeliveryRecorded(ServiceEntityNode sourceDocument,
			Delivery delivery, List<ServiceEntityNode> deliveryItemList);
	
	

}
