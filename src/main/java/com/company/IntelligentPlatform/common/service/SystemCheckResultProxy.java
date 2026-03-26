package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class SystemCheckResultProxy {

    public static final int CHECKRESULT_INIT = 1;

    public static final int CHECKRESULT_ERROR = 2;

    public static final int CHECKRESULT_OK = 3;

    public static final String PROPERTIES_RESOURCE = "SystemCheckResult";

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    protected Map<Integer, String> systemCheckResultMap;

    protected Map<String, Map<Integer, String>> systemCheckResultMapLan;

    public Map<Integer, String> getSystemCheckResultMap(String languageCode)
            throws ServiceEntityInstallationException {
        if(this.systemCheckResultMapLan == null){
            this.systemCheckResultMapLan = new HashMap<>();
        }
        return ServiceLanHelper.initDefaultLanguageMap(languageCode, this.systemCheckResultMapLan, lanCode->{
            try {
                String path = this.getClass().getResource("").getPath();
                Map<Integer, String> tempSwitchMap = serviceDropdownListHelper
                        .getDropDownMap(path + PROPERTIES_RESOURCE, languageCode);
                return tempSwitchMap;
            } catch (IOException e) {
                return null;
            }
        });
    }

}
