package com.company.IntelligentPlatform.salesDistribution.service;

import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.salesDistribution.model.SalesForcast;
import com.company.IntelligentPlatform.salesDistribution.model.SalesForcastMaterialItem;
import com.company.IntelligentPlatform.salesDistribution.model.SalesForcastParty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.platform.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.platform.service.RegisteredProductManager;
import com.company.IntelligentPlatform.platform.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.platform.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.platform.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.platform.service.CrossDocConvertRequest;
import com.company.IntelligentPlatform.platform.service.DocFlowProxy;
import com.company.IntelligentPlatform.platform.service.PricingSettingManager;
import com.company.IntelligentPlatform.platform.model.IDefDocumentResource;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;

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