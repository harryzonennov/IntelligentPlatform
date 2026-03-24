package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.model.BillOfMaterialItem;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;


public class BillOfMaterialItemServiceModel extends ServiceModule {

    @IServiceModuleFieldConfig(nodeName = BillOfMaterialItem.NODENAME, nodeInstId = BillOfMaterialItem.NODENAME,
            docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
    protected BillOfMaterialItem billOfMaterialItem;

    public BillOfMaterialItem getBillOfMaterialItem() {
        return billOfMaterialItem;
    }

    public void setBillOfMaterialItem(BillOfMaterialItem billOfMaterialItem) {
        this.billOfMaterialItem = billOfMaterialItem;
    }
}
