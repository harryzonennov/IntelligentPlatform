package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.model.RepairProdOrder;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceDefaultIdGenerateHelper;

@Service
public class RepairProdOrderIdHelper extends ServiceDefaultIdGenerateHelper {

	public static final String ID_PREFIX = "REPR";

	public static final String TABLE_NAME = RepairProdOrder.SENAME;

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

}
