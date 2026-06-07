package com.company.IntelligentPlatform.platform.service;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.platform.model.Warehouse;
import com.company.IntelligentPlatform.platform.service.ServiceDefaultIdGenerateHelper;

@Service
public class WarehouseIdHelper extends ServiceDefaultIdGenerateHelper {

	public static final String ID_PREFIX = "WH";

	public static final String TABLE_NAME = Warehouse.SENAME;

	public static final int INDEX_LENGTH = 2;

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
