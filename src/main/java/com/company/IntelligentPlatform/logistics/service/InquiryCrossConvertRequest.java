package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.model.Inquiry;
import com.company.IntelligentPlatform.logistics.model.InquiryMaterialItem;
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
public class InquiryCrossConvertRequest extends
        CrossDocConvertRequest<InquiryServiceModel, InquiryMaterialItem,
                InquiryMaterialItemServiceModel> {

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected InquiryManager inquiryManager;

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected InquirySpecifier inquirySpecifier;

    @Autowired
    protected DocInvolvePartyProxy docInvolvePartyProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected PricingSettingManager pricingSettingManager;

    protected Logger logger = LoggerFactory.getLogger(InquiryCrossConvertRequest.class);

    public InquiryCrossConvertRequest(){
        this.setTargetDocType(IDefDocumentResource.DOCUMENT_TYPE_INQUIRY);
    }


    /**
     * Set Default Callback: Logic to filter target doc for reused
     */
    @Override
    protected void setDefFilterTargetDoc() {
        this.setFilterTargetDoc((targetDoc, documentMatItemBatchGenRequest) -> {
            Inquiry inquiry = (Inquiry) targetDoc;
            if(inquiry.getStatus() != Inquiry.STATUS_INITIAL){
                return false;
            };
            return true;
        });
    }


}
