package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.model.ServiceJSONRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;

/**
 * Basic Service UI Module
 * @author Zhang,hang
 *
 */
public class ServiceUIModule {

    /**
     * UI Meta data
     */
    protected ServiceUIMeta serviceUIMeta;

    protected ServiceJSONRequest serviceJSONRequest;

    public ServiceUIMeta getServiceUIMeta() {
        return serviceUIMeta;
    }

    public void setServiceUIMeta(ServiceUIMeta serviceUIMeta) {
        this.serviceUIMeta = serviceUIMeta;
    }

    public ServiceJSONRequest getServiceJSONRequest() {
        return serviceJSONRequest;
    }

    public void setServiceJSONRequest(ServiceJSONRequest serviceJSONRequest) {
        this.serviceJSONRequest = serviceJSONRequest;
    }
}
