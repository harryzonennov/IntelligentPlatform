package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.model.InboundDelivery;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceDefaultIdGenerateHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceBarcodeException;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class InboundDeliveryIdHelper extends ServiceDefaultIdGenerateHelper {

	public static final String ID_PREFIX = "IND";

	public static final String TABLE_NAME = InboundDelivery.SENAME;

	public static final int INDEX_LENGTH = 3;

	@Override
	public String getMainTableName() {
		return TABLE_NAME;
	}

	@Override
	public String getIdPrefix() {
		return ID_PREFIX;
	}

	@Override
	public int getIdPrefixLength() {
		return INDEX_LENGTH;
	}

	@Override
	public boolean isTimeStampNeed() {
		return true;
	}
	
	@Override
	public String genDefaultBarcode(String client){
		try {
			String barcode = getDefaultBarcode(TABLE_NAME, TABLE_NAME, client);
			return barcode;
		} catch (SearchConfigureException e) {
			return null;
		} catch (ServiceEntityConfigureException e) {
			return null;
		} catch (ServiceBarcodeException e) {
			return null;
		}		
	}
	
	@Override
	public String getDefaultUniqueBarcode(String client){
		try {
			String barcode = getDefaultUniqueBarcode(TABLE_NAME, TABLE_NAME, client);
			return barcode;
		} catch (SearchConfigureException e) {
			return null;
		} catch (ServiceEntityConfigureException e) {
			return null;
		} catch (ServiceBarcodeException e) {
			return null;
		}		
	}

}
