package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.model.Delivery;
import com.company.IntelligentPlatform.logistics.model.DeliveryItem;
import com.company.IntelligentPlatform.logistics.model.InboundDelivery;
import com.company.IntelligentPlatform.logistics.model.OutboundDelivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.SpringContextBeanService;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;

/**
 * Central Factory for get the instance of DeliverableDocumentProxy
 * @author Zhang,Hang
 *
 */
@Service
public class DeliverableDocumentFactory {
	
	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;
	
	@Autowired
	protected SpringContextBeanService springContextBeanService;
	
	/**
	 * Constants for each DeliverableDocumentProxy, Keep its value the same as in run-time.
	 */
    public static final String SALESCONTRACT_INBOUNDDELIVERY_PROXY = "salesContractForInboundDeliveryProxy";
    
    public static final String SalesContractItemForOutboundItemProxy = "salesContractItemForOutboundItemProxy";
	
	public IDeliverableDocumentProxy getDeliverableDocumentProxy(int documentType, Delivery delivery){
		if(documentType == IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT){
			if(delivery.getServiceEntityName().equals(InboundDelivery.SENAME)){
				return (IDeliverableDocumentProxy)springContextBeanService.getBean(SALESCONTRACT_INBOUNDDELIVERY_PROXY);
			}
		}
		return null;
	}
	
	public IDeliverableDocumentProxy getDeliverableDocumentItemProxy(int documentType, DeliveryItem deliveryItem){
		if(documentType == IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT){
			if(deliveryItem.getServiceEntityName().equals(OutboundDelivery.SENAME)){
				return (IDeliverableDocumentProxy)springContextBeanService.getBean(SalesContractItemForOutboundItemProxy);
			}
		}
		return null;
	}

}
