package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.ServiceBasicPerformHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.List;

/**
 * A Search Context Model class for passing the context information when executing a search.
 * This context information may include parameters such as user login, client, search headers.....
 */
public class SearchContext {

    private String client;

    private SearchHeaderContext searchHeaderContext;

    private String[] fieldNameArray;

    private boolean fuzzyFlag;

    private int start = 0;

    private int length = 0;

    private SEUIComModel searchModel;

    private SearchAuthorizationContext searchAuthorizationContext;

    private List<ServiceBasicPerformHelper.RecordPointTime> recordPointTimeList;

    private List<ServiceEntityNode> rawSEList;

    public SearchContext(){

    }

    public SearchContext(String client) {
        this.client = client;
    }

    public SearchContext(SearchAuthorizationContext searchAuthorizationContext) {
        this.searchAuthorizationContext = searchAuthorizationContext;
        this.client = searchAuthorizationContext.getLogonUser().getClient();
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public SearchAuthorizationContext getSearchAuthorizationContext() {
        return searchAuthorizationContext;
    }

    public void setSearchAuthorizationContext(SearchAuthorizationContext searchAuthorizationContext) {
        this.searchAuthorizationContext = searchAuthorizationContext;
    }

    public SearchHeaderContext getSearchHeaderContext() {
        return searchHeaderContext;
    }

    public void setSearchHeaderContext(SearchHeaderContext searchHeaderContext) {
        this.searchHeaderContext = searchHeaderContext;
    }

    public String[] getFieldNameArray() {
        return fieldNameArray;
    }

    public void setFieldNameArray(String[] fieldNameArray) {
        this.fieldNameArray = fieldNameArray;
    }

    public boolean getFuzzyFlag() {
        return fuzzyFlag;
    }

    public void setFuzzyFlag(boolean fuzzyFlag) {
        this.fuzzyFlag = fuzzyFlag;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public SEUIComModel getSearchModel() {
        return searchModel;
    }

    public void setSearchModel(SEUIComModel searchModel) {
        this.searchModel = searchModel;
    }

    public boolean isFuzzyFlag() {
        return fuzzyFlag;
    }

    public List<ServiceBasicPerformHelper.RecordPointTime> getRecordPointTimeList() {
        return recordPointTimeList;
    }

    public void setRecordPointTimeList(List<ServiceBasicPerformHelper.RecordPointTime> recordPointTimeList) {
        this.recordPointTimeList = recordPointTimeList;
    }

    public List<ServiceEntityNode> getRawSEList() {
        return rawSEList;
    }

    public void setRawSEList(List<ServiceEntityNode> rawSEList) {
        this.rawSEList = rawSEList;
    }
}
