package com.company.IntelligentPlatform.common.controller;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.SystemCheckResultProxy;

public class FlowRouterExtendClassUIModel extends SEUIComModel {

    protected int processIndex;

    protected String extendClassId;

    protected String extendClassName;

    protected String extendClassFullName;

    protected String refDirectAssigneeUUID;

    protected String refDirectAssigneeId;

    protected String refDirectAssigneeName;

    protected String rootDocId;

    protected String rootDocName;

    protected int checkResult;

    protected String checkResultValue;

    protected String checkResultComment;

    public FlowRouterExtendClassUIModel() {
        this.checkResult = SystemCheckResultProxy.CHECKRESULT_INIT;
    }

    public int getProcessIndex() {
        return this.processIndex;
    }

    public void setProcessIndex(int processIndex) {
        this.processIndex = processIndex;
    }

    public String getExtendClassId() {
        return this.extendClassId;
    }

    public void setExtendClassId(String extendClassId) {
        this.extendClassId = extendClassId;
    }

    public String getExtendClassName() {
        return extendClassName;
    }

    public void setExtendClassName(String extendClassName) {
        this.extendClassName = extendClassName;
    }

    public String getExtendClassFullName() {
        return this.extendClassFullName;
    }

    public void setExtendClassFullName(String extendClassFullName) {
        this.extendClassFullName = extendClassFullName;
    }

    public String getRefDirectAssigneeUUID() {
        return refDirectAssigneeUUID;
    }

    public void setRefDirectAssigneeUUID(String refDirectAssigneeUUID) {
        this.refDirectAssigneeUUID = refDirectAssigneeUUID;
    }

    public String getRefDirectAssigneeId() {
        return refDirectAssigneeId;
    }

    public void setRefDirectAssigneeId(String refDirectAssigneeId) {
        this.refDirectAssigneeId = refDirectAssigneeId;
    }

    public String getRefDirectAssigneeName() {
        return refDirectAssigneeName;
    }

    public void setRefDirectAssigneeName(String refDirectAssigneeName) {
        this.refDirectAssigneeName = refDirectAssigneeName;
    }

    public String getRootDocId() {
        return rootDocId;
    }

    public void setRootDocId(String rootDocId) {
        this.rootDocId = rootDocId;
    }

    public String getRootDocName() {
        return rootDocName;
    }

    public void setRootDocName(String rootDocName) {
        this.rootDocName = rootDocName;
    }

    public int getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(int checkResult) {
        this.checkResult = checkResult;
    }

    public String getCheckResultValue() {
        return checkResultValue;
    }

    public void setCheckResultValue(String checkResultValue) {
        this.checkResultValue = checkResultValue;
    }

    public String getCheckResultComment() {
        return checkResultComment;
    }

    public void setCheckResultComment(String checkResultComment) {
        this.checkResultComment = checkResultComment;
    }
}
