package com.company.IntelligentPlatform.sales.service;

import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.sales.model.SalesForcast;
import com.company.IntelligentPlatform.sales.model.SalesForcastMaterialItem;
import com.company.IntelligentPlatform.sales.model.SalesForcastParty;
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
public class SalesForcastCrossConvertRequest extends
        CrossDocConvertRequest<SalesForcastServiceModel, SalesForcastMaterialItem, SalesForcastMaterialItemServiceModel> {

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected SalesForcastManager salesForcastManager;

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected SalesForcastSpecifier salesForcastSpecifier;

    @Autowired
    protected DocInvolvePartyProxy docInvolvePartyProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected PricingSettingManager pricingSettingManager;

    protected Logger logger = LoggerFactory.getLogger(SalesForcastCrossConvertRequest.class);

    public SalesForcastCrossConvertRequest(){
        this.setTargetDocType(IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST);
    }

    /**
     * Set Default Callback: Logic to filter target doc for reused
     */
    @Override
    protected void setDefFilterTargetDoc() {
        this.setFilterTargetDoc((targetDoc, documentMatItemBatchGenRequest) -> {
            SalesForcast salesForcast = (SalesForcast) targetDoc;
            if(salesForcast.getStatus() != SalesForcast.STATUS_INITIAL){
                return false;
            };
            boolean checkParty = docInvolvePartyProxy.defCheckPartyForFilter(documentMatItemBatchGenRequest,
                    IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST, salesForcast,
                    SalesForcastParty.ROLE_SOLD_TO_PARTY);
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
            SalesForcast salesForcast = targetServiceModule.getSalesForcast();
        });
    }


}