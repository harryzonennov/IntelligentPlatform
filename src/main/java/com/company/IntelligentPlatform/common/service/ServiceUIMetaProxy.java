package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.ServiceUIMeta;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;

import java.util.List;

@Service
public class ServiceUIMetaProxy {

    public void updateUIMeta(ServiceUIModule serviceUIModule, ServiceBasicKeyStructure keyStructure) {
        if (serviceUIModule == null) {
            return;
        }
        if (serviceUIModule.getServiceUIMeta() == null) {
            ServiceUIMeta serviceUIMeta = new ServiceUIMeta(ServiceCollectionsHelper.asList(keyStructure));
            serviceUIModule.setServiceUIMeta(serviceUIMeta);
            return;
        }
        List<ServiceBasicKeyStructure> keyValueList = serviceUIModule.getServiceUIMeta().getKeyValueList();
        if (ServiceCollectionsHelper.checkNullList(keyValueList)) {
            keyValueList = ServiceCollectionsHelper.asList(keyStructure);
            serviceUIModule.getServiceUIMeta().setKeyValueList(keyValueList);
        } else {
            ServiceBasicKeyStructure existedKey = ServiceCollectionsHelper.filterOnline(keyValueList,
                    serviceKeyStructure -> {
                        return serviceKeyStructure.getKeyName().equals(keyStructure.getKeyName());
                    });
            if(existedKey == null){
                keyValueList.add(keyStructure);
            } else {
                // in case update
                existedKey.setKeyValue(keyStructure.getKeyValue());
            }
        }
    }

}
