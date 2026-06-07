package com.company.IntelligentPlatform.salesDistribution.service;

import com.company.IntelligentPlatform.salesDistribution.model.SalesContract;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.platform.service.ServiceDefaultIdGenerateHelper;

@Service
public class SalesContractIdHelper extends ServiceDefaultIdGenerateHelper {

	public static final String ID_PREFIX = "SA";

	public static final String TABLE_NAME = SalesContract.SENAME;

	public static final int INDEX_LENGTH = 4;

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