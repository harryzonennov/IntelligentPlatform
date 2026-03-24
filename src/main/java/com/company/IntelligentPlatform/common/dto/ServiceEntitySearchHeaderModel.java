package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;

public class ServiceEntitySearchHeaderModel extends SEUIComModel {

    @BSearchFieldConfig(fieldName = "uuid")
    protected String uuid;

    @BSearchFieldConfig(fieldName = "id")
    protected String id;

    @BSearchFieldConfig(fieldName = "name")
    protected String name;

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
}
