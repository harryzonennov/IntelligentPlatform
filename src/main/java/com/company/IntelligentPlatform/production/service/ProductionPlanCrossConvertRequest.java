package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.logistics.service.QualityInspectOrderActionExecutionProxy;
import com.company.IntelligentPlatform.logistics.service.QualityInspectOrderManager;
import com.company.IntelligentPlatform.production.model.ProdPlanTargetMatItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.service.CrossDocConvertRequest;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;

/**
 * Used for Batch creation Production Plan form other sources docs, like Sales Farcast and Sales Order
 */
@Service
public class ProductionPlanCrossConvertRequest extends
        CrossDocConvertRequest<ProductionPlanServiceModel, ProdPlanTargetMatItem, ProdPlanTargetMatItemServiceModel> {

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected ProductionPlanManager productionPlanManager;

    @Autowired
    protected ProductionPlanSpecifier productionPlanSpecifier;

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected QualityInspectOrderManager qualityInspectOrderManager;

    @Autowired
    protected QualityInspectOrderActionExecutionProxy qualityInspectOrderActionExecutionProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    protected Logger logger = LoggerFactory.getLogger(ProductionPlanCrossConvertRequest.class);

    public ProductionPlanCrossConvertRequest(){
        this.setTargetDocType(IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONPLAN);
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
        });
    }

}
