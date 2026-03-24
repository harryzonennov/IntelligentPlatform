package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.model.Material;
import com.company.IntelligentPlatform.common.service.ServiceDefaultIdGenerateHelper;

@Service
public class MaterialIdHelper extends ServiceDefaultIdGenerateHelper {

	public static final String ID_PREFIX = "MA";

	public static final String TABLE_NAME = Material.SENAME;

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
		return false;
	}

}
