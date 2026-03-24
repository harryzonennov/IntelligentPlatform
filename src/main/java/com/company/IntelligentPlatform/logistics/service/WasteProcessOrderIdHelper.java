package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.model.WasteProcessOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceDefaultIdGenerateHelper;
import com.company.IntelligentPlatform.common.service.SerialNumberSettingManager;

@Service
public class WasteProcessOrderIdHelper extends ServiceDefaultIdGenerateHelper {

	public static final String ID_PREFIX = "SA";

	public static final String TABLE_NAME = WasteProcessOrder.SENAME;

	public static final int INDEX_LENGTH = 4;
	
	@Autowired
	protected SerialNumberSettingManager serialNumberSettingManager;

	protected Logger logger = LoggerFactory.getLogger(WasteProcessOrderIdHelper.class);

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
