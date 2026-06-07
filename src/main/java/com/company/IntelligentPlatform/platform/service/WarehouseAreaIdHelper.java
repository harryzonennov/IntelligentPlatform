package com.company.IntelligentPlatform.platform.service;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.platform.model.WarehouseArea;
import com.company.IntelligentPlatform.platform.service.ServiceDefaultIdGenerateHelper;

@Service
public class WarehouseAreaIdHelper extends ServiceDefaultIdGenerateHelper {

	public static final String ID_PREFIX = "";

	public static final String TABLE_NAME = WarehouseArea.NODENAME;

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
