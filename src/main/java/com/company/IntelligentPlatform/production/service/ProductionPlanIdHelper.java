package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.model.ProductionPlan;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.platform.service.ServiceDefaultIdGenerateHelper;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.SearchConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;

@Service
public class ProductionPlanIdHelper extends ServiceDefaultIdGenerateHelper {

	public static final String ID_PREFIX = "MPS";

	public static final String TABLE_NAME = ProductionPlan.SENAME;

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
