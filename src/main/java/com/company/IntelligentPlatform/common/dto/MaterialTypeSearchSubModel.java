package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class MaterialTypeSearchSubModel extends SEUIComModel {

    protected String id;

    protected String name;

    protected String uuid;

    protected int status;

    protected String parentTypeId;

    protected String parentTypeUUID;

    protected String rootTypeUUID;

    protected String parentTypeName;

    protected String rootTypeId;

    protected String rootTypeName;

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getParentTypeId() {
        return parentTypeId;
    }

    public void setParentTypeId(String parentTypeId) {
        this.parentTypeId = parentTypeId;
    }

    public String getParentTypeUUID() {
        return parentTypeUUID;
    }

    public void setParentTypeUUID(String parentTypeUUID) {
        this.parentTypeUUID = parentTypeUUID;
    }

    public String getRootTypeUUID() {
        return rootTypeUUID;
    }

    public void setRootTypeUUID(String rootTypeUUID) {
        this.rootTypeUUID = rootTypeUUID;
    }

    public String getParentTypeName() {
        return parentTypeName;
    }

    public void setParentTypeName(String parentTypeName) {
        this.parentTypeName = parentTypeName;
    }

    public String getRootTypeId() {
        return rootTypeId;
    }

    public void setRootTypeId(String rootTypeId) {
        this.rootTypeId = rootTypeId;
    }

    public String getRootTypeName() {
        return rootTypeName;
    }

    public void setRootTypeName(String rootTypeName) {
        this.rootTypeName = rootTypeName;
    }
}
