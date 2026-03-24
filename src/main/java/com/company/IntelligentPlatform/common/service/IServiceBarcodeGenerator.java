package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceBarcodeException;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.SerialNumberSetting;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

public interface IServiceBarcodeGenerator {
	
	public String genBarcode(SerialNumberSetting serialNumberSetting,
			String tableName) throws ServiceEntityConfigureException,
			SearchConfigureException, ServiceBarcodeException;

}
