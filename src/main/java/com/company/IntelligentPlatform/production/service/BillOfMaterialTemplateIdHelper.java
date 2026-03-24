package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.model.BillOfMaterialTemplate;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceDefaultIdGenerateHelper;

@Service
public class BillOfMaterialTemplateIdHelper extends ServiceDefaultIdGenerateHelper {

	public static final String ID_PREFIX = "BOM";

	public static final String TABLE_NAME = BillOfMaterialTemplate.SENAME;

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
