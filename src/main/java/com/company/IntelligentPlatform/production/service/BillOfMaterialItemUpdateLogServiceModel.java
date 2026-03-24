package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.model.BillOfMaterialItemUpdateLog;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class BillOfMaterialItemUpdateLogServiceModel extends ServiceModule {

    @IServiceModuleFieldConfig(nodeName = BillOfMaterialItemUpdateLog.NODENAME, nodeInstId = BillOfMaterialItemUpdateLog.NODENAME)
    protected BillOfMaterialItemUpdateLog billOfMaterialItemUpdateLog;

    public BillOfMaterialItemUpdateLog getBillOfMaterialItemUpdateLog() {
        return billOfMaterialItemUpdateLog;
    }

    public void setBillOfMaterialItemUpdateLog(BillOfMaterialItemUpdateLog billOfMaterialItemUpdateLog) {
        this.billOfMaterialItemUpdateLog = billOfMaterialItemUpdateLog;
    }
}
