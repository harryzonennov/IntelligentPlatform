package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceDefaultIdGenerateHelper;

@Service
public class WarehouseStoreIdHelper extends ServiceDefaultIdGenerateHelper {

	public static final String ID_PREFIX = "WEM";

	public static final String TABLE_NAME = WarehouseStoreItem.NODENAME;

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
		return true;
	}

}
