package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceDefaultIdGenerateHelper;

@Service
public class CorporateCustomerIdHelper extends ServiceDefaultIdGenerateHelper {

	public static final String ID_PREFIX = "CC";

	public static final String TABLE_NAME = "CorporateCustomer";

	public static final int INDEX_LENGTH = 5;

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
		return false;
	}

}
