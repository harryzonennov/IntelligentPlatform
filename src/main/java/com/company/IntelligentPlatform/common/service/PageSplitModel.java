package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

// TODO-DAO: stub for legacy Hibernate pagination model
public class PageSplitModel {
    protected int currentPage = 1;
    protected int viewPageSize = 20;
    protected int recordInPageSize = 20;
    protected int allRecordAmount = 0;
    protected int allPageNum = 1;

    public int getCurrentPage() { return currentPage; }
    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }
    public int getViewPageSize() { return viewPageSize; }
    public void setViewPageSize(int viewPageSize) { this.viewPageSize = viewPageSize; }
    public int getRecordInPageSize() { return recordInPageSize; }
    public void setRecordInPageSize(int recordInPageSize) { this.recordInPageSize = recordInPageSize; }
    public int getAllRecordAmount() { return allRecordAmount; }
    public void setAllRecordAmount(int allRecordAmount) { this.allRecordAmount = allRecordAmount; }
    public int getAllPageNum() { return allPageNum; }
    public void setAllPageNum(int allPageNum) { this.allPageNum = allPageNum; }
}
