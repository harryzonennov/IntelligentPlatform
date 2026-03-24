package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.model.MaterialType;
import com.company.IntelligentPlatform.common.service.ServiceDefaultIdGenerateHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class MaterialTypeIdHelper extends ServiceDefaultIdGenerateHelper {

	public static final String ID_PREFIX = "MT";

	public static final String TABLE_NAME = MaterialType.SENAME;

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
