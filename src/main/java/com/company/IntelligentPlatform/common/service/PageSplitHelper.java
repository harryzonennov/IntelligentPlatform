package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

// TODO-DAO: stub for legacy Hibernate pagination helper
public class PageSplitHelper {

    public static int caculateAllPageNum(PageSplitModel model) {
        if (model.getRecordInPageSize() <= 0) return 1;
        int total = model.getAllRecordAmount();
        int size = model.getRecordInPageSize();
        return Math.max(1, (total + size - 1) / size);
    }

    public static int caculateStartRecordIndex(PageSplitModel model) {
        int page = Math.max(1, model.getCurrentPage());
        return (page - 1) * model.getRecordInPageSize();
    }

    public static int caculateEndRecordIndex(PageSplitModel model) {
        int start = caculateStartRecordIndex(model);
        int end = start + model.getRecordInPageSize();
        return Math.min(end, model.getAllRecordAmount());
    }

    public static List<Integer> getViewPageArray(PageSplitModel model) {
        int allPageNum = model.getAllPageNum();
        int current = model.getCurrentPage();
        int viewSize = model.getViewPageSize() > 0 ? model.getViewPageSize() : 10;
        int half = viewSize / 2;
        int startPage = Math.max(1, current - half);
        int endPage = Math.min(allPageNum, startPage + viewSize - 1);
        startPage = Math.max(1, endPage - viewSize + 1);
        List<Integer> result = new ArrayList<>();
        for (int i = startPage; i <= endPage; i++) {
            result.add(i);
        }
        return result;
    }
}
