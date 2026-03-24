package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.model.QualityInspectOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceDefaultIdGenerateHelper;

@Service
public class QualityInspectOrderIdHelper extends ServiceDefaultIdGenerateHelper{
	
	public static final String ID_PREFIX = "QIN";

	public static final String TABLE_NAME = QualityInspectOrder.SENAME;

	public static final int INDEX_LENGTH = 3;

	protected Logger logger = LoggerFactory.getLogger(QualityInspectOrderIdHelper.class);

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
