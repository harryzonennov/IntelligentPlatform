package com.company.IntelligentPlatform.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.platform.service.DocumentBarCodeService;

@Service
public class ServiceBarcodeGeneratorFactory {
	
	@Autowired
	protected EAN13BarcodeGenerator ean13BarcodeGenerator;
	
	public IServiceBarcodeGenerator getBarcodeGeneartorInstance(String encodeType){
		if(DocumentBarCodeService.EAN13.equals(encodeType)){
			return ean13BarcodeGenerator;
		}
		return null;
	}

}
