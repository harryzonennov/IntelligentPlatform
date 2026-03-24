package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.model.PurchaseRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceDefaultIdGenerateHelper;

@Service
public class PurchaseRequestIdHelper extends ServiceDefaultIdGenerateHelper{
	
	public static final String ID_PREFIX = "PUQ";

	public static final String TABLE_NAME = PurchaseRequest.SENAME;

	public static final int INDEX_LENGTH = 3;

	protected Logger logger = LoggerFactory.getLogger(PurchaseRequestIdHelper.class);

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

}
