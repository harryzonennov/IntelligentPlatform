package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * UI Model Meta
 * @author Zhang,hang
 *
 */
public class ServiceUIMeta {

    protected List<ServiceBasicKeyStructure> keyValueList = new ArrayList<>();

    public ServiceUIMeta() {
    }

    public ServiceUIMeta(List<ServiceBasicKeyStructure> keyValueList) {
        this.keyValueList = keyValueList;
    }

    public List<ServiceBasicKeyStructure> getKeyValueList() {
        return keyValueList;
    }

    public void setKeyValueList(List<ServiceBasicKeyStructure> keyValueList) {
        this.keyValueList = keyValueList;
    }
}
