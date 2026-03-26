package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.production.model.BillOfMaterialItem;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

public class BillOfMaterialItemServiceUIModel extends ServiceUIModule {

    @IServiceUIModuleFieldConfig(nodeName = BillOfMaterialItem.NODENAME, nodeInstId = BillOfMaterialItem.NODENAME)
    protected BillOfMaterialItemUIModel billOfMaterialItemUIModel;

    public BillOfMaterialItemUIModel getBillOfMaterialItemUIModel() {
        return billOfMaterialItemUIModel;
    }

    public void setBillOfMaterialItemUIModel(BillOfMaterialItemUIModel billOfMaterialItemUIModel) {
        this.billOfMaterialItemUIModel = billOfMaterialItemUIModel;
    }
}
