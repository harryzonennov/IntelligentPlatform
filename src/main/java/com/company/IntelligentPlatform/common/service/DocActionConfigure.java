package com.company.IntelligentPlatform.common.service;

import java.util.List;

public class DocActionConfigure {

    private int actionCode;

    private int targetStatus;

    private List<Integer> preStatusList;

    private String authorActionCode;

    public DocActionConfigure(){

    }

    public DocActionConfigure(int actionCode, int targetStatus, List<Integer> preStatusList, String authorActionCode) {
        this.actionCode = actionCode;
        this.targetStatus = targetStatus;
        this.preStatusList = preStatusList;
        this.authorActionCode = authorActionCode;
    }

    public int getActionCode() {
        return actionCode;
    }

    public void setActionCode(int actionCode) {
        this.actionCode = actionCode;
    }

    public int getTargetStatus() {
        return targetStatus;
    }

    public void setTargetStatus(int targetStatus) {
        this.targetStatus = targetStatus;
    }

    public List<Integer> getPreStatusList() {
        return preStatusList;
    }

    public void setPreStatusList(List<Integer> preStatusList) {
        this.preStatusList = preStatusList;
    }

    public String getAuthorActionCode() {
        return authorActionCode;
    }

    public void setAuthorActionCode(String authorActionCode) {
        this.authorActionCode = authorActionCode;
    }

}
