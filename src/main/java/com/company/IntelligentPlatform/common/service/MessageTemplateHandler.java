package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.List;

/**
 * Super class for message template handler
 */
@Service
public abstract class MessageTemplateHandler {

    public String getId(){
        return this.getClass().getSimpleName();
    };

    public abstract String getName();

    public abstract String getDescription();

    /**
     * Post process SE Data List
     * @param messageTemplateServiceModel
     * @param rawSEList
     * @param logonInfo
     * @return
     */
    public List<ServiceEntityNode> postProcessSEDataList(MessageTemplateServiceModel messageTemplateServiceModel,
                                                     List<ServiceEntityNode> rawSEList, LogonInfo logonInfo){
        return rawSEList;
    }
}
