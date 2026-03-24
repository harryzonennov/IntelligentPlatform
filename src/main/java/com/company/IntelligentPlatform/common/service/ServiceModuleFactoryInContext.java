package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class ServiceModuleFactoryInContext {
	
	@Autowired
	protected SpringContextBeanService springContextBeanService;
	
	public static final String warehouseStoreItemLogisticsServiceUIModelExtension = "warehouseStoreItemLogisticsServiceUIModelExtension";
	
	@Qualifier(warehouseStoreItemLogisticsServiceUIModelExtension)
	@Autowired(required=false)
	protected ServiceUIModelExtension warehouseStoreItemLogisticsServiceUIModelExtensionImp;
	/**
	 * Generate or get the ServiceUIModel extension from Spring context
	 * @param extensionName: first choice extensionName
	 * @return
	 */
	public ServiceUIModelExtension getServiceUIModuleExtensionByName(String extensionName){
		if(ServiceEntityStringHelper.checkNullString(extensionName)){
			return null;
		}
		if(extensionName.equals(warehouseStoreItemLogisticsServiceUIModelExtension)){
			return this.warehouseStoreItemLogisticsServiceUIModelExtensionImp;
			// return (ServiceEntityManager)springContextBeanService.getBean(transSiteManager);
		}
		return null;
	}

}
