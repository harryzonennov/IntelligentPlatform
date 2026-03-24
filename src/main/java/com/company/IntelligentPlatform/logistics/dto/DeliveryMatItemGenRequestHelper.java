package com.company.IntelligentPlatform.logistics.dto;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DeliveryMatItemGenRequestHelper {

    public static DeliveryMatItemBatchGenRequest parseToGenRequest(String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        classMap.put("uuidList", String.class);
        DeliveryMatItemBatchGenRequest genRequest = (DeliveryMatItemBatchGenRequest) JSONObject
                .toBean(jsonObject, DeliveryMatItemBatchGenRequest.class,
                        classMap);
        return genRequest;
    }
}
