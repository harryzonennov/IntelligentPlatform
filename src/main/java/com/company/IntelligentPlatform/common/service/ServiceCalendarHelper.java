package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class ServiceCalendarHelper {

    public static final int UNIT_YEAR = Calendar.YEAR;

    public static final int UNIT_MONTH = Calendar.MONTH;

    public static final int UNIT_WEEK = Calendar.WEEK_OF_YEAR;

    public static final int UNIT_DAY = Calendar.DATE;

    public static final int UNIT_HOUR = Calendar.HOUR;

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    public static final String RES_CALENDAR_FILENAME = "Calendar_unit";

    public Map<Integer, String> getCalendarUnitMap()
            throws ServiceEntityInstallationException, IOException {
        Locale locale = ServiceLanHelper.getDefault();
        String path = ServiceChartHelper.class.getResource("").getPath();
        Map<Integer, String> unitMap = serviceDropdownListHelper
                .getDropDownMapInteger(path, RES_CALENDAR_FILENAME, locale);
        return unitMap;
    }

    public Map<Integer, String> getSearchUnitMap()
            throws ServiceEntityInstallationException, IOException {
        Map<Integer, String> unitMap = getCalendarUnitMap();
        unitMap.put(0, ServiceEntityStringHelper.EMPTYSTRING);
        return unitMap;
    }

}
