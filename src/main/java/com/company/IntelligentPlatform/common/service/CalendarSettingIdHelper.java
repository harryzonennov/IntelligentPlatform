package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceDefaultIdGenerateHelper;

@Service
public class CalendarSettingIdHelper extends ServiceDefaultIdGenerateHelper {

    public static final String ID_PREFIX = "CA";

    public static final String TABLE_NAME = "CalendarSetting";

    public static final int INDEX_LENGTH = 4;

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
