package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.LogonInfo;

import java.util.Map;

@Service
public class CurrentLogonUserDataProvider extends ServiceSimpleDataProviderTemplate{

    @Override
    public Object getStandardData(LogonInfo logonInfo) throws ServiceSimpleDataProviderException {
        return logonInfo.getLogonUser();
    }

    @Override
    public String getStandardDataToString(Object data) throws ServiceSimpleDataProviderException {
        return null;
    }

    @Override
    public Object getResultData(SimpleDataOffsetUnion simpleDataOffsetUnion, LogonInfo logonInfo) throws ServiceSimpleDataProviderException {
        return this.getStandardData(logonInfo);
    }

    @Override
    public String getResultDataToString(Object data) throws ServiceSimpleDataProviderException {
        return null;
    }

    @Override
    public Map<?, ?> getOffsetDirectionDropdown() throws ServiceSimpleDataProviderException {
        return null;
    }

    @Override
    public Map<?, ?> getOffsetUnitDropdown() throws ServiceSimpleDataProviderException {
        return null;
    }

    @Override
    public Map<?, ?> getOffsetDirectionTemplate() throws ServiceSimpleDataProviderException {
        return null;
    }

    @Override
    public Map<?, ?> getOffsetUnitTemplate() throws ServiceSimpleDataProviderException {
        return null;
    }

    @Override
    public String getDataProviderComment() {
        return null;
    }
}
