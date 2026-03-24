package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.AuthorizationManager;
import com.company.IntelligentPlatform.common.service.ServiceBasicPerformHelper;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.List;

public class SearchContextBuilder {

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

    private LogonUser logonUser;

    private String aoId;

    private String acId;

    private Organization homeOrganization;

    private List<ServiceEntityNode> organizationList;

    private List<AuthorizationManager.AuthorizationACUnion> authorizationACUnionList;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
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

    public boolean isFuzzyFlag() {
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

    public SearchAuthorizationContext getSearchAuthorizationContext() {
        return searchAuthorizationContext;
    }

    public void setSearchAuthorizationContext(SearchAuthorizationContext searchAuthorizationContext) {
        this.searchAuthorizationContext = searchAuthorizationContext;
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

    public LogonUser getLogonUser() {
        return logonUser;
    }

    public void setLogonUser(LogonUser logonUser) {
        this.logonUser = logonUser;
    }

    public String getAoId() {
        return aoId;
    }

    public void setAoId(String aoId) {
        this.aoId = aoId;
    }

    public String getAcId() {
        return acId;
    }

    public void setAcId(String acId) {
        this.acId = acId;
    }

    public Organization getHomeOrganization() {
        return homeOrganization;
    }

    public void setHomeOrganization(Organization homeOrganization) {
        this.homeOrganization = homeOrganization;
    }

    public List<ServiceEntityNode> getOrganizationList() {
        return organizationList;
    }

    public void setOrganizationList(List<ServiceEntityNode> organizationList) {
        this.organizationList = organizationList;
    }

    public List<AuthorizationManager.AuthorizationACUnion> getAuthorizationACUnionList() {
        return authorizationACUnionList;
    }

    public void setAuthorizationACUnionList(List<AuthorizationManager.AuthorizationACUnion> authorizationACUnionList) {
        this.authorizationACUnionList = authorizationACUnionList;
    }

    public static SearchContextBuilder genBuilder() {
        return new SearchContextBuilder();
    }

    public static SearchContextBuilder genBuilder(SEUIComModel searchModel) {
        SearchContextBuilder searchContextBuilder = new SearchContextBuilder();
        searchContextBuilder.searchModel = searchModel;
        return searchContextBuilder;
    }

    public static SearchContextBuilder genBuilder(LogonInfo logonInfo) {
        SearchContextBuilder searchContextBuilder = new SearchContextBuilder();
        searchContextBuilder.logonUser(logonInfo.getLogonUser()).homeOrganization(logonInfo.getHomeOrganization()).
                organizationList(logonInfo.getOrganizationList()).authorizationACUnionList(logonInfo.getAuthorizationACUnionList());
        return searchContextBuilder;
    }

    public SearchContextBuilder fuzzyFlag(boolean fuzzyFlag) {
        this.fuzzyFlag = fuzzyFlag;
        return this;
    }

    public SearchContextBuilder start(int start) {
        this.start = start;
        return this;
    }

    public SearchContextBuilder length(int length) {
        this.length = length;
        return this;
    }

    public SearchContextBuilder searchModel(SEUIComModel searchModel) {
        this.searchModel = searchModel;
        return this;
    }

    public SearchContextBuilder searchAuthorizationContext(SearchAuthorizationContext searchAuthorizationContext) {
        this.searchAuthorizationContext = searchAuthorizationContext;
        return this;
    }

    public SearchContextBuilder rawSEList(List<ServiceEntityNode> rawSEList) {
        this.rawSEList = rawSEList;
        return this;
    }

    public SearchContextBuilder logonUser(LogonUser logonUser) {
        this.logonUser = logonUser;
        return this;
    }

    public SearchContextBuilder aoId(String aoId) {
        this.aoId = aoId;
        return this;
    }

    public SearchContextBuilder acId(String acId) {
        this.acId = acId;
        return this;
    }

    public SearchContextBuilder client(String client) {
        this.client = client;
        return this;
    }

    public SearchContextBuilder homeOrganization(Organization homeOrganization) {
        this.homeOrganization = homeOrganization;
        return this;
    }

    public SearchContextBuilder organizationList(List<ServiceEntityNode> organizationList) {
        this.organizationList = organizationList;
        return this;
    }

    public SearchContextBuilder authorizationACUnionList(List<AuthorizationManager.AuthorizationACUnion> authorizationACUnionList) {
        this.authorizationACUnionList = authorizationACUnionList;
        return this;
    }

    public SearchContext build() throws SearchConfigureException {
        SearchContext searchContext = new SearchContext();
        if (!ServiceEntityStringHelper.checkNullString(this.getClient())){
            searchContext.setClient(this.getClient());
        }
        if (this.getSearchAuthorizationContext() != null){
            searchContext.setSearchAuthorizationContext(this.getSearchAuthorizationContext());
        } else {
            searchContext.setSearchAuthorizationContext(new SearchAuthorizationContext());
        }
        if (!ServiceEntityStringHelper.checkNullString(this.getAoId())){
            searchContext.getSearchAuthorizationContext().setAcId(this.getAoId());
        }
        if (!ServiceEntityStringHelper.checkNullString(this.getAcId())){
            searchContext.getSearchAuthorizationContext().setAcId(this.getAcId());
        }
        if (this.getLogonUser() != null){
            searchContext.getSearchAuthorizationContext().setLogonUser(this.getLogonUser());
        }
        if (this.getHomeOrganization() != null){
            searchContext.getSearchAuthorizationContext().setHomeOrganization(this.getHomeOrganization());
        }
        if (this.getOrganizationList() != null){
            searchContext.getSearchAuthorizationContext().setOrganizationList(this.getOrganizationList());
        }
        if (this.getSearchModel() != null){
            searchContext.setSearchModel(this.getSearchModel());
        }
        if (this.getSearchAuthorizationContext() != null){
            searchContext.setSearchAuthorizationContext(this.getSearchAuthorizationContext());
        }
        if (this.getRawSEList() != null){
            searchContext.setRawSEList(this.getRawSEList());
        }
        return searchContext;
    }

}
