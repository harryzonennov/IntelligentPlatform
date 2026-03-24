package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class FlowRouterExtendClass extends ServiceEntityNode{

    public static final String NODENAME = IServiceModelConstants.FlowRouterExtendClass;

    public static final String SENAME = IServiceModelConstants.FlowRouter;

    public FlowRouterExtendClass(){
        this.nodeName = NODENAME;
        this.serviceEntityName = SENAME;
    }

    @ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_800)
    protected String extendClassFullName;

    protected String extendClassId;

    protected int processIndex;

    protected String refDirectAssigneeUUID;

    public String getExtendClassFullName() {
        return extendClassFullName;
    }

    public void setExtendClassFullName(String extendClassFullName) {
        this.extendClassFullName = extendClassFullName;
    }

    public String getExtendClassId() {
        return extendClassId;
    }

    public void setExtendClassId(String extendClassId) {
        this.extendClassId = extendClassId;
    }

    public int getProcessIndex() {
        return processIndex;
    }

    public void setProcessIndex(int processIndex) {
        this.processIndex = processIndex;
    }

    public String getRefDirectAssigneeUUID() {
        return refDirectAssigneeUUID;
    }

    public void setRefDirectAssigneeUUID(String refDirectAssigneeUUID) {
        this.refDirectAssigneeUUID = refDirectAssigneeUUID;
    }
}
