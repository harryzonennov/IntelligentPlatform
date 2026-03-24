package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.CrossDocConvertRequest;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.PricingSettingManager;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class PurchaseContractCrossConvertRequest extends
        CrossDocConvertRequest<PurchaseContractServiceModel, PurchaseContractMaterialItem,
                PurchaseContractMaterialItemServiceModel> {

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected PurchaseContractManager purchaseContractManager;

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected PurchaseRequestSpecifier purchaseRequestSpecifier;

    @Autowired
    protected PurchaseContractSpecifier purchaseContractSpecifier;

    @Autowired
    protected DocInvolvePartyProxy docInvolvePartyProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected PricingSettingManager pricingSettingManager;

    protected Logger logger = LoggerFactory.getLogger(PurchaseContractCrossConvertRequest.class);

    public PurchaseContractCrossConvertRequest(){
        this.setTargetDocType(IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT);
    }

    /**
     * Set Default Callback: Logic to filter target doc for reused
     */
    @Override
    protected void setDefFilterTargetDoc() {
        this.setFilterTargetDoc((targetDoc, documentMatItemBatchGenRequest) -> {
            PurchaseContract purchaseContract = (PurchaseContract) targetDoc;
            if(purchaseContract.getStatus() != PurchaseContract.STATUS_INITIAL){
                return false;
            };
            boolean checkParty = docInvolvePartyProxy.defCheckPartyForFilter(documentMatItemBatchGenRequest,
                    IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT, purchaseContract, PurchaseContractParty.ROLE_PARTYB);
            return checkParty;
        });
    }

    /**
     * Set Default Callback: Logic to parse information from genRequest to
     */
    @Override
    public void setDefParseBatchGenRequest() {
        super.setParseBatchGenRequest((genRequest, targetServiceModule) -> {
            DeliveryMatItemBatchGenRequest deliveryMatItemBatchGenRequest =
                    (DeliveryMatItemBatchGenRequest) genRequest;
            PurchaseContract purchaseContract = targetServiceModule.getPurchaseContract();
        });
    }


}
