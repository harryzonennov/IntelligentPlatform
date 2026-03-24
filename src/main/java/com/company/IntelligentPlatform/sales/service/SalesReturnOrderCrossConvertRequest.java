package com.company.IntelligentPlatform.sales.service;

import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.sales.model.SalesContract;
import com.company.IntelligentPlatform.sales.model.SalesReturnOrder;
import com.company.IntelligentPlatform.sales.model.SalesReturnMaterialItem;
import com.company.IntelligentPlatform.sales.model.SalesReturnOrderParty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.common.service.DocumentContentSpecifier;
import com.company.IntelligentPlatform.common.service.CrossDocConvertRequest;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.PricingSettingManager;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Service
public class SalesReturnOrderCrossConvertRequest extends
        CrossDocConvertRequest<SalesReturnOrderServiceModel, SalesReturnMaterialItem, SalesReturnMaterialItemServiceModel> {

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected SalesReturnOrderManager salesReturnOrderManager;

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected SalesReturnOrderSpecifier salesReturnOrderSpecifier;

    @Autowired
    protected DocInvolvePartyProxy docInvolvePartyProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected PricingSettingManager pricingSettingManager;

    protected Logger logger = LoggerFactory.getLogger(SalesReturnOrderCrossConvertRequest.class);

    public SalesReturnOrderCrossConvertRequest(){
        this.setTargetDocType(IDefDocumentResource.DOCUMENT_TYPE_SALESRETURNORDER);
    }

    /**
     * Set Default Callback: Logic to filter target doc for reused
     */
    @Override
    protected void setDefFilterTargetDoc() {
        this.setFilterTargetDoc((targetDoc, documentMatItemBatchGenRequest) -> {
            SalesReturnOrder salesReturnOrder = (SalesReturnOrder) targetDoc;
            if(salesReturnOrder.getStatus() != SalesReturnOrder.STATUS_INITIAL){
                return false;
            };
            boolean checkParty = docInvolvePartyProxy.defCheckPartyForFilter(documentMatItemBatchGenRequest,
                    IDefDocumentResource.DOCUMENT_TYPE_SALESRETURNORDER, salesReturnOrder,
                    SalesReturnOrderParty.ROLE_SOLD_TO_PARTY);
            return checkParty;
        });
    }

    @Override
    public void setDefConvertToTargetDoc() {
        super.setConvertToTargetDoc((convertDocumentContext) -> {
            DocumentContentSpecifier sourceDocSpecifier = convertDocumentContext.getSourceDocSpecifier();
            ServiceEntityNode targetDocument = convertDocumentContext.getTargetDocument();
            ServiceEntityNode sourceDoc = sourceDocSpecifier.getCoreEntity(convertDocumentContext.getSourceServiceModel());
            SalesReturnOrder salesReturnOrder =
                    (SalesReturnOrder) targetDocument;
            if (sourceDoc instanceof SalesContract) {
                salesReturnOrder.setPrevDocUUID(sourceDoc.getUuid());
                salesReturnOrder.setPrevDocType(IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT);
                return salesReturnOrder;
            }
            return targetDocument;
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
            SalesReturnOrder salesReturnOrder = targetServiceModule.getSalesReturnOrder();
        });
    }

}