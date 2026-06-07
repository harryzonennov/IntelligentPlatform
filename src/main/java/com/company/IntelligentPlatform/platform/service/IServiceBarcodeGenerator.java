package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.ServiceBarcodeException;
import com.company.IntelligentPlatform.platform.service.SearchConfigureException;
import com.company.IntelligentPlatform.platform.model.SerialNumberSetting;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;

public interface IServiceBarcodeGenerator {
	
	public String genBarcode(SerialNumberSetting serialNumberSetting,
			String tableName) throws ServiceEntityConfigureException,
			SearchConfigureException, ServiceBarcodeException;

}
