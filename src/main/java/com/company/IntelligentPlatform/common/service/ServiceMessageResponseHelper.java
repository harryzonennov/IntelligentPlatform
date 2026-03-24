package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.model.SimpleSEMessageResponse;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;

@Service
public class ServiceMessageResponseHelper {

    public static List<SimpleSEMessageResponse> filerSEMessageResponseByLevel(
            int messageLevel, List<SimpleSEMessageResponse> rawMessageList) {
        List<SimpleSEMessageResponse> resultList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(rawMessageList)) {
            for (SimpleSEMessageResponse simpleSEMessageResponse : rawMessageList) {
                if (simpleSEMessageResponse.getMessageLevel() == messageLevel) {
                    resultList.add(simpleSEMessageResponse);
                }
            }
        }
        return resultList;
    }

    public static String[] mergeMessageParaList(List<SimpleSEMessageResponse> errorMessageList) {
        List<String> paraList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(errorMessageList)) {
            for (SimpleSEMessageResponse simpleSEMessageResponse : errorMessageList) {
                if (!ServiceCollectionsHelper.checkNullArray(simpleSEMessageResponse.getErrorParas())){
                    paraList.addAll(ServiceCollectionsHelper.mergeStringArrayToList(simpleSEMessageResponse.getErrorParas()));
                }
            }
        }
        if (!ServiceCollectionsHelper.checkNullList(paraList)) {
            return (String[]) paraList.toArray();
        }
        return null;
    }

}
