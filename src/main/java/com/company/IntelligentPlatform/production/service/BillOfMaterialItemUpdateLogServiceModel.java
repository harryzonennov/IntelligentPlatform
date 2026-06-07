package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.model.BillOfMaterialItemUpdateLog;
import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

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
