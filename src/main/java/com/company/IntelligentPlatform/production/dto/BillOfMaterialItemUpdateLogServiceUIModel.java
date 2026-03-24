package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.production.model.BillOfMaterialItemUpdateLog;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;


public class BillOfMaterialItemUpdateLogServiceUIModel extends ServiceUIModule {

    @IServiceUIModuleFieldConfig(nodeName = BillOfMaterialItemUpdateLog.NODENAME, nodeInstId = BillOfMaterialItemUpdateLog.NODENAME)
    protected BillOfMaterialItemUpdateLogUIModel billOfMaterialItemUpdateLogUIModel;

    public BillOfMaterialItemUpdateLogUIModel getBillOfMaterialItemUpdateLogUIModel() {
        return billOfMaterialItemUpdateLogUIModel;
    }

    public void setBillOfMaterialItemUpdateLogUIModel(BillOfMaterialItemUpdateLogUIModel billOfMaterialItemUpdateLogUIModel) {
        this.billOfMaterialItemUpdateLogUIModel = billOfMaterialItemUpdateLogUIModel;
    }
}
