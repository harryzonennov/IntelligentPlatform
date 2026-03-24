package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.service.QualityInspectOrderActionExecutionProxy;
import com.company.IntelligentPlatform.logistics.service.QualityInspectOrderManager;
import com.company.IntelligentPlatform.production.model.ProdOrderTargetMatItem;
import com.company.IntelligentPlatform.production.model.ProductionOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.CrossDocBatchConvertProxy;
import com.company.IntelligentPlatform.common.service.CrossDocConvertRequest;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Used for Batch creation Production Order form other sources docs, like Sales Farcast and Sales Order
 */
@Service
public class ProductionOrderCrossConvertRequest extends
        CrossDocConvertRequest<ProductionOrderServiceModel, ProdOrderTargetMatItem, ProdOrderTargetMatItemServiceModel> {

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected ProductionOrderManager productionOrderManager;

    @Autowired
    protected ProductionOrderSpecifier productionOrderSpecifier;

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected QualityInspectOrderManager qualityInspectOrderManager;

    @Autowired
    protected QualityInspectOrderActionExecutionProxy qualityInspectOrderActionExecutionProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    protected Logger logger = LoggerFactory.getLogger(ProductionOrderCrossConvertRequest.class);

    public ProductionOrderCrossConvertRequest(){
        this.setTargetDocType(IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER);
    }

    /**
     * Set Default Callback: Logic Copy from source mat item to target mat item
     */
    @Override
    protected void setDefCovertToTargetItem() {
        this.setCovertToTargetItem(CrossDocConvertRequest.ConvertItemContext::getTargetMatItemNode);
    }

    /**
     * Set Default Callback: Logic to parse information from genRequest to
     */
    @Override
    public void setDefParseBatchGenRequest() {
        super.setParseBatchGenRequest((genRequest, targetServiceModel) -> {
            DeliveryMatItemBatchGenRequest deliveryMatItemBatchGenRequest =
                    (DeliveryMatItemBatchGenRequest) genRequest;
            ProductionOrder productionOrder = targetServiceModel.getProductionOrder();
        });
    }


}
