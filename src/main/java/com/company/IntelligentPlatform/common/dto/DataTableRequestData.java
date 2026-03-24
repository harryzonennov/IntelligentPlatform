package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class DataTableRequestData {

    private SEUIComModel searchModel;

    private int start; // start index in current page

    private int length;

    private int pageEnd; // end index in current page

    private int recordsTotal;

    public DataTableRequestData(SEUIComModel searchModel, int start, int length, int pageEnd, int recordsTotal) {
        this.searchModel = searchModel;
        this.start = start;
        this.length = length;
        this.pageEnd = pageEnd;
        this.recordsTotal = recordsTotal;
    }

    public SEUIComModel getSearchModel() {
        return searchModel;
    }

    public void setSearchModel(SEUIComModel searchModel) {
        this.searchModel = searchModel;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getPageEnd() {
        return pageEnd;
    }

    public void setPageEnd(int pageEnd) {
        this.pageEnd = pageEnd;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
