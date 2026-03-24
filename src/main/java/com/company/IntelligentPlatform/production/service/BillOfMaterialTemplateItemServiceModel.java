package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.model.BillOfMaterialTemplateItem;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;


public class BillOfMaterialTemplateItemServiceModel extends ServiceModule {

    @IServiceModuleFieldConfig(nodeName = BillOfMaterialTemplateItem.NODENAME, nodeInstId = BillOfMaterialTemplateItem.NODENAME,
            docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
    protected BillOfMaterialTemplateItem billOfMaterialTemplateItem;

    public BillOfMaterialTemplateItem getBillOfMaterialTemplateItem() {
        return billOfMaterialTemplateItem;
    }

    public void setBillOfMaterialTemplateItem(BillOfMaterialTemplateItem billOfMaterialTemplateItem) {
        this.billOfMaterialTemplateItem = billOfMaterialTemplateItem;
    }
}
