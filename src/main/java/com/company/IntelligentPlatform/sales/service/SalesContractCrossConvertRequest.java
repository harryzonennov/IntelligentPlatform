package com.company.IntelligentPlatform.sales.service;

import com.company.IntelligentPlatform.sales.model.SalesContract;
import com.company.IntelligentPlatform.sales.model.SalesContractMaterialItem;
import com.company.IntelligentPlatform.sales.model.SalesContractParty;
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
public class SalesContractCrossConvertRequest extends
        CrossDocConvertRequest<SalesContractServiceModel, SalesContractMaterialItem, SalesContractMaterialItemServiceModel> {

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected SalesContractManager salesContractManager;

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected SalesContractSpecifier salesContractSpecifier;

    @Autowired
    protected DocInvolvePartyProxy docInvolvePartyProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected PricingSettingManager pricingSettingManager;

    protected Logger logger = LoggerFactory.getLogger(SalesContractCrossConvertRequest.class);

    public SalesContractCrossConvertRequest(){
        this.setTargetDocType(IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT);
    }

    /**
     * Set Default Callback: Logic to filter target doc for reused
     */
    @Override
    protected void setDefFilterTargetDoc() {
        this.setFilterTargetDoc((targetDoc, documentMatItemBatchGenRequest) -> {
            SalesContract salesContract = (SalesContract) targetDoc;
            if(salesContract.getStatus() != SalesContract.STATUS_INITIAL){
                return false;
            };
            boolean checkParty = docInvolvePartyProxy.defCheckPartyForFilter(documentMatItemBatchGenRequest,
                    IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT, salesContract,
                    SalesContractParty.ROLE_SOLD_TO_PARTY);
            return checkParty;
        });
    }

}