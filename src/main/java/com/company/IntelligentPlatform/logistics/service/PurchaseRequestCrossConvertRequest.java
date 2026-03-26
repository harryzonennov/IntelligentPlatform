package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.model.PurchaseContract;
import com.company.IntelligentPlatform.logistics.model.PurchaseRequest;
import com.company.IntelligentPlatform.logistics.model.PurchaseRequestMaterialItem;
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

@Service
public class PurchaseRequestCrossConvertRequest extends
        CrossDocConvertRequest<PurchaseRequestServiceModel, PurchaseRequestMaterialItem,
                PurchaseRequestMaterialItemServiceModel> {

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected PurchaseRequestManager purchaseRequestManager;

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected PurchaseRequestSpecifier purchaseRequestSpecifier;

    @Autowired
    protected DocInvolvePartyProxy docInvolvePartyProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected PricingSettingManager pricingSettingManager;

    protected Logger logger = LoggerFactory.getLogger(PurchaseRequestCrossConvertRequest.class);

    public PurchaseRequestCrossConvertRequest(){

    }

    /**
     * Set Default Callback: Logic to filter target doc for reused
     */
    @Override
    protected void setDefFilterTargetDoc() {
        this.setFilterTargetDoc((targetDoc, documentMatItemBatchGenRequest) -> {
            PurchaseRequest purchaseRequest = (PurchaseRequest) targetDoc;
            if(purchaseRequest.getStatus() != PurchaseRequest.STATUS_INITIAL){
                return false;
            };
            return true;
        });
    }

}
