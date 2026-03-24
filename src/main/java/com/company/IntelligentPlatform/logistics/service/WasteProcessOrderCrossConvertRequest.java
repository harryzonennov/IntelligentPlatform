package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.logistics.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.common.service.CrossDocConvertRequest;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.PricingSettingManager;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;

@Service
public class WasteProcessOrderCrossConvertRequest extends
        CrossDocConvertRequest<WasteProcessOrderServiceModel, WasteProcessMaterialItem,
                WasteProcessMaterialItemServiceModel> {

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected WasteProcessOrderManager wasteProcessOrderManager;

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected WasteProcessOrderSpecifier wasteProcessOrderSpecifier;

    @Autowired
    protected DocInvolvePartyProxy docInvolvePartyProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected WarehouseStoreManager warehouseStoreManager;

    @Autowired
    protected PricingSettingManager pricingSettingManager;

    protected Logger logger = LoggerFactory.getLogger(WasteProcessOrderCrossConvertRequest.class);

    public WasteProcessOrderCrossConvertRequest(){
        this.setTargetDocType(IDefDocumentResource.DOCUMENT_TYPE_WASTEPROCESSORDER);
    }


}
