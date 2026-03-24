package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.service.QualityInspectOrderActionExecutionProxy;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.logistics.model.OutboundItem;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;


@Service
public class OutboundDeliveryCrossConvertReservedRequest extends
        CrossDocConvertReservedRequest<OutboundDeliveryServiceModel, OutboundItem, OutboundItemServiceModel> {

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected OutboundDeliveryManager outboundDeliveryManager;

    @Autowired
    protected WarehouseStoreManager warehouseStoreManager;

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected QualityInspectOrderActionExecutionProxy qualityInspectOrderActionExecutionProxy;

    @Autowired
    protected OutboundDeliveryWarehouseItemManager outboundDeliveryWarehouseItemManager;

    @Autowired
    protected OutboundDeliveryCrossConvertRequest outboundDeliveryCrossConvertRequest;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    protected Logger logger = LoggerFactory.getLogger(OutboundDeliveryCrossConvertReservedRequest.class);

    public OutboundDeliveryCrossConvertReservedRequest(){
        this.setTargetDocType(IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY);
    }

    @Override
    protected void setDefFilterRootDocContextReserved() {
        this.setFilterRootDocContextReserved((docContentCreateContext, reservedMatItemNode, sourceMatItemNode) -> {
            OutboundDeliveryServiceModel outboundDeliveryServiceModel =
                    (OutboundDeliveryServiceModel) docContentCreateContext.getTargetRootDocument();
            if(outboundDeliveryServiceModel == null){
                return false;
            }
            WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) sourceMatItemNode;
            return warehouseStoreItem.getRefWarehouseUUID().equals(outboundDeliveryServiceModel.getOutboundDelivery().getRefWarehouseUUID());
        });
    }

    @Override
    public CrossDocConvertRequest<OutboundDeliveryServiceModel, OutboundItem, OutboundItemServiceModel> getCrossDocConvertRequest() {
        return outboundDeliveryCrossConvertRequest;
    }

    /**
     * Set Default Callback:Logic to get all the source mat item list by selected reserved mat item list
     */
    @Override
    protected void setDefGetALLSourceMatItemListBySelectedReserved() {
        this.setGetALLSourceMatItemListBySelectedReserved((selectedReservedMatItemList, reservedDocType) -> {
            if(reservedDocType == IDefDocumentResource.DOCUMENT_TYPE_WASTEPROCESSORDER){
                //TODO to configure warehouse configuration in custom configuration
                return warehouseStoreManager.getStoreItemListByDocMatItemList(0, selectedReservedMatItemList);
            }
            return warehouseStoreManager.getStoreItemListByDocMatItemList(Warehouse.REFMAT_CATEGORY_NORMAL, selectedReservedMatItemList);
        });
    }

    /**
     * Set Default Callback: Logic Copy from source doc to target doc
     */
    @Override
    protected void setDefConvertToTargetItemReserved() {
        this.setConvertToTargetItemReserved((docMatItemCreateContext, reservedMatItemNode,
                                        storageCoreUnitContext) -> {
            DocMatItemNode sourceMatItemNode = docMatItemCreateContext.getSourceDocMatItemNode();
            if(sourceMatItemNode.getHomeDocumentType() == IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM){
                StorageCoreUnit requestCoreUnitBack = (StorageCoreUnit) storageCoreUnitContext.clone();
                try {
                    OutboundDeliveryWarehouseItemManager.OutboundReqToStoreResult outboundReqToStoreResult =
                            outboundDeliveryWarehouseItemManager.genRequestToStoreItem(reservedMatItemNode.getUuid(),
                            (WarehouseStoreItem) sourceMatItemNode,
                            requestCoreUnitBack);
                    if(outboundReqToStoreResult == null){
                        // in case didn't output any result, just return null result
                        return null;
                    }
                    OutboundItem outboundItem =
                            (OutboundItem) docMatItemCreateContext.getTargetDocMatItemNode();
                    if(outboundReqToStoreResult.getLeftStoreUnit() == null){
                        // In case meet requirement: here request Core unit should be ZERO, and break traverse
                        outboundItem.setAmount(storageCoreUnitContext.getAmount());
                        outboundItem.setActualAmount(storageCoreUnitContext.getAmount());
                        outboundItem.setRefUnitUUID(storageCoreUnitContext.getRefUnitUUID());
                        storageCoreUnitContext.setAmount(0);
                        docMatItemCreateContext.setBreakFlag(true); // break
                    }else{
                        // In case not meet requirement, continue to next
                        outboundItem.setAmount(outboundReqToStoreResult.getAvailableStoreUnit().getAmount());
                        outboundItem.setActualAmount(outboundReqToStoreResult.getAvailableStoreUnit().getAmount());
                        outboundItem.setRefUnitUUID(outboundReqToStoreResult.getAvailableStoreUnit().getRefUnitUUID());
                        storageCoreUnitContext =
                                MaterialStockKeepUnitManager.copyStorageCoreUnit(outboundReqToStoreResult.getLeftStoreUnit(), storageCoreUnitContext);
                    }
                } catch (MaterialException | ServiceEntityConfigureException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, reservedMatItemNode.getUuid()));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                }
            }
            return docMatItemCreateContext;
        });
    }

    @Override
    public void setDefLoadSourceItemReserved() {
        setLoadSourceItemReserved((reservedMatItemNode, sourceMatItemNode, storageCoreUnitContext) -> {
            if(sourceMatItemNode.getHomeDocumentType() == IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM){
                StorageCoreUnit requestCoreUnitBack = (StorageCoreUnit) storageCoreUnitContext.clone();
                try {
                    OutboundDeliveryWarehouseItemManager.OutboundReqToStoreResult outboundReqToStoreResult =
                            outboundDeliveryWarehouseItemManager.genRequestToStoreItem(reservedMatItemNode.getUuid(),
                                    (WarehouseStoreItem) sourceMatItemNode,
                                    requestCoreUnitBack);
                    if(outboundReqToStoreResult == null){
                        // in case didn't output any result, just return null result
                        return null;
                    }
                    if(outboundReqToStoreResult.getLeftStoreUnit() == null){
                        // In case meet requirement: here request Core unit should be ZERO, and break traverse
                        StorageCoreUnit outCoreUnitBack = (StorageCoreUnit) storageCoreUnitContext.clone();
                        storageCoreUnitContext.setAmount(0);
                        return new CrossDocBatchConvertReservedProxy.ReseveredDocItemContext(sourceMatItemNode,
                                outCoreUnitBack);
                    }else{
                        // In case not meet requirement, continue to next
                        storageCoreUnitContext =
                                MaterialStockKeepUnitManager.copyStorageCoreUnit(outboundReqToStoreResult.getLeftStoreUnit(), storageCoreUnitContext);
                        return new CrossDocBatchConvertReservedProxy.ReseveredDocItemContext(sourceMatItemNode,
                                outboundReqToStoreResult.getAvailableStoreUnit());
                    }
                } catch (MaterialException | ServiceEntityConfigureException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, reservedMatItemNode.getUuid()));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                }
            }
            return null;
        });
    }

}
