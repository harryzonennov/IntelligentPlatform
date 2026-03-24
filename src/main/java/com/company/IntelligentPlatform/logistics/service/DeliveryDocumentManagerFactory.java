package com.company.IntelligentPlatform.logistics.service;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceEntityManagerFactoryInContext;

/**
 * Factory class for managing the different types of Delivery Document Manager
 * @author Zhang,Hang
 *
 */
@Service
public class DeliveryDocumentManagerFactory {
	
	public static final String bookingNoteManager = "bookingNoteManager";
	
	public static final String salesOrderManager = "salesOrderManager";
	
	public static final String vehicleRunOrderManager = "vehicleRunOrderManager";
	
	public static final String transSiteStoreItemManager = "transSiteStoreItemManager";
	
	@Autowired
	protected ServiceEntityManagerFactoryInContext serviceEntityManagerFactoryInContext;
	
	public DeliveryDocumentManager getDeliveryDocumentManager(String seManagerName){
		try{
			Object rawManager = serviceEntityManagerFactoryInContext.getManagerByManagerName(seManagerName);
			if(rawManager == null){
				return null;
			}
			return (DeliveryDocumentManager)rawManager;
		}catch(NoSuchBeanDefinitionException ex){
			return null;
		}
	}

}
