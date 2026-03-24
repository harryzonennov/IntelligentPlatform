package com.company.IntelligentPlatform.common.service;

import java.util.List;

public class SearchHeaderContext {

    private List<String> fieldNameList;

    private boolean flagUuidHeader;

    private boolean totalCountHeader;

    public List<String> getFieldNameList() {
        return fieldNameList;
    }

    public void setFieldNameList(List<String> fieldNameList) {
        this.fieldNameList = fieldNameList;
    }

    public boolean isFlagUuidHeader() {
        return flagUuidHeader;
    }

    public void setFlagUuidHeader(boolean flagUuidHeader) {
        this.flagUuidHeader = flagUuidHeader;
    }

    public boolean isTotalCountHeader() {
        return totalCountHeader;
    }

    public void setTotalCountHeader(boolean totalCountHeader) {
        this.totalCountHeader = totalCountHeader;
    }
}
