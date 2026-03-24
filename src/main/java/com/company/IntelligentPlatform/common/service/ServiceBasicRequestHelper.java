package com.company.IntelligentPlatform.common.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.DocumentMatItemBatchGenRequest;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility Class for handling, parsing external request from front-end
 */
@Service
public class ServiceBasicRequestHelper {
    /**
     * Utlity method to parse String format request into DocumentMatItemBatchGenRequest instance
     * @param request
     * @param GenRequestType
     * @param <T>
     * @return
     */
    public static <T extends DocumentMatItemBatchGenRequest>  DocumentMatItemBatchGenRequest parseToDocumentMatItemBatchGenRequest(String request, int documentType,
                                                                                                                                   Class<T> GenRequestType){
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes") Map<String, Class> classMap = new HashMap<>();
        classMap.put("uuidList", String.class);
        classMap.put("involvePartyGenRequestList", DocumentMatItemBatchGenRequest.InvolvePartyGenRequest.class);
        DocumentMatItemBatchGenRequest genRequest = null;
        if(GenRequestType != null){
            genRequest = (DocumentMatItemBatchGenRequest) JSONObject
                    .toBean(jsonObject, GenRequestType,
                            classMap);
        } else {
            genRequest = (DocumentMatItemBatchGenRequest) JSONObject
                    .toBean(jsonObject, DocumentMatItemBatchGenRequest.class,
                            classMap);
        }
        if (documentType > 0 && genRequest.getTargetDocType() == 0) {
            genRequest.setTargetDocType(documentType);
        }
        return genRequest;
    }

    public static DocumentMatItemBatchGenRequest.InvolvePartyGenRequest parseInvolvePartyRequest(DocumentMatItemBatchGenRequest documentMatItemBatchGenRequest, int partyRole){
        List<DocumentMatItemBatchGenRequest.InvolvePartyGenRequest> involvePartyGenRequestList =
                documentMatItemBatchGenRequest.getInvolvePartyGenRequestList();
        if(ServiceCollectionsHelper.checkNullList(involvePartyGenRequestList)){
            return null;
        }
        List<DocumentMatItemBatchGenRequest.InvolvePartyGenRequest> filterList =
                involvePartyGenRequestList.stream().filter(involvePartyGenRequest -> {
                    return involvePartyGenRequest.getPartyRole() == partyRole;
                }).collect(Collectors.toList());
        if(ServiceCollectionsHelper.checkNullList(filterList)){
            return null;
        }
        return filterList.get(0);
    }
}
