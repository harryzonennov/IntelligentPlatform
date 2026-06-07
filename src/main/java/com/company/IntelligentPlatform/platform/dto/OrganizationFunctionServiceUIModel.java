package com.company.IntelligentPlatform.platform.dto;

import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;
import com.company.IntelligentPlatform.platform.model.OrganizationFunction;

public class OrganizationFunctionServiceUIModel extends ServiceUIModule {

    @IServiceUIModuleFieldConfig(nodeName = OrganizationFunction.NODENAME, nodeInstId = OrganizationFunction.NODENAME)
    protected OrganizationFunctionUIModel organizationFunctionUIModel;

    public OrganizationFunctionUIModel getOrganizationFunctionUIModel() {
        return organizationFunctionUIModel;
    }

    public void setOrganizationFunctionUIModel(OrganizationFunctionUIModel organizationFunctionUIModel) {
        this.organizationFunctionUIModel = organizationFunctionUIModel;
    }
}
