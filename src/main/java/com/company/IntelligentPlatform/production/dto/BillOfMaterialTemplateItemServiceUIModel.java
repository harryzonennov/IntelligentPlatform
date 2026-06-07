package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.production.model.BillOfMaterialTemplateItem;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;

public class BillOfMaterialTemplateItemServiceUIModel extends ServiceUIModule {

    @IServiceUIModuleFieldConfig(nodeName = BillOfMaterialTemplateItem.NODENAME, nodeInstId = BillOfMaterialTemplateItem.NODENAME)
    protected BillOfMaterialTemplateItemUIModel billOfMaterialTemplateItemUIModel;

    public BillOfMaterialTemplateItemUIModel getBillOfMaterialTemplateItemUIModel() {
        return billOfMaterialTemplateItemUIModel;
    }

    public void setBillOfMaterialTemplateItemUIModel(BillOfMaterialTemplateItemUIModel billOfMaterialTemplateItemUIModel) {
        this.billOfMaterialTemplateItemUIModel = billOfMaterialTemplateItemUIModel;
    }
}
