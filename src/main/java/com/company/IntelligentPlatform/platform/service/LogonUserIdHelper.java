package com.company.IntelligentPlatform.platform.service;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.platform.service.ServiceDefaultIdGenerateHelper;
import com.company.IntelligentPlatform.platform.model.LogonUser;

@Service
public class LogonUserIdHelper extends ServiceDefaultIdGenerateHelper {

	public static final String ID_PREFIX = "i00";

	public static final String TABLE_NAME = LogonUser.SENAME;

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
