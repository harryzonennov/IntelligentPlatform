package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.OrganizationFunction;

public class OrganizationFunctionServiceUIModel extends ServiceUIModule {


    @IServiceUIModuleFieldConfig(nodeName = OrganizationFunction.NODENAME, nodeInstId = OrganizationFunction.SENAME)
    protected OrganizationFunctionUIModel organizationFunctionUIModel;

    public OrganizationFunctionUIModel getOrganizationFunctionUIModel() {
        return organizationFunctionUIModel;
    }

    public void setOrganizationFunctionUIModel(OrganizationFunctionUIModel organizationFunctionUIModel) {
        this.organizationFunctionUIModel = organizationFunctionUIModel;
    }
}
