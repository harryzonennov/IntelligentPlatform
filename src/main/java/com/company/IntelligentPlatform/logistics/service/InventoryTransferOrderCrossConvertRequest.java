package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.InventoryTransferMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.model.StoreAvailableStoreItemRequest;
import com.company.IntelligentPlatform.logistics.model.WarehouseStore;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.DocumentContentSpecifier;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.CrossDocConvertRequest;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

@Service
public class InventoryTransferOrderCrossConvertRequest extends
        CrossDocConvertRequest<InventoryTransferOrderServiceModel, InventoryTransferItem, InventoryTransferItemServiceModel> {

    private String refOutboundWarehouseUUID;

    private String refInboundWarehouseUUID;

    @Autowired
    protected InventoryTransferOrderManager inventoryTransferOrderManager;

    @Autowired
    protected InventoryTransferOrderSpecifier inventoryTransferOrderSpecifier;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    protected Logger logger = LoggerFactory.getLogger(InventoryTransferOrderCrossConvertRequest.class);
    @Autowired
    private OutboundDeliveryWarehouseItemManager outboundDeliveryWarehouseItemManager;

    public InventoryTransferOrderCrossConvertRequest() {
        this.setTargetDocType(IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_TRANSFER);
    }

    public InventoryTransferOrderCrossConvertRequest(String refOutboundWarehouseUUID, String refInboundWarehouseUUID) {
        this.refOutboundWarehouseUUID = refOutboundWarehouseUUID;
        this.refInboundWarehouseUUID = refInboundWarehouseUUID;
    }

    /**
     * Set Default Callback: Logic to filter target doc for reused
     */
    @Override
    protected void setDefFilterTargetDoc() {
        this.setFilterTargetDoc((targetDoc, documentMatItemBatchGenRequest) -> {
            InventoryTransferOrder inventoryTransferOrder = (InventoryTransferOrder) targetDoc;
            if (inventoryTransferOrder.getStatus() != InventoryTransferOrder.STATUS_INITIAL) {
                return false;
            }
            return true;
        });
    }

    @Override
    public void setDefConvertToTargetDoc() {
        super.setConvertToTargetDoc((convertDocumentContext) -> {
            ServiceModule serviceModule = convertDocumentContext.getSourceServiceModel();
            DocumentContentSpecifier sourceDocSpecifier = convertDocumentContext.getSourceDocSpecifier();
            ServiceEntityNode sourceDoc = sourceDocSpecifier.getCoreEntity(serviceModule);
            InventoryTransferOrder inventoryTransferOrder =
                    (InventoryTransferOrder) convertDocumentContext.getTargetDocument();
            inventoryTransferOrder.setPrevDocType(sourceDocSpecifier.getDocumentType());
            inventoryTransferOrder.setPrevDocUUID(sourceDoc.getUuid());
            if (sourceDoc instanceof WarehouseStore) {
                WarehouseStore warehouseStore = (WarehouseStore) sourceDoc;
                inventoryTransferOrder.setRefWarehouseUUID(warehouseStore.getRefWarehouseUUID());
            }
            return inventoryTransferOrder;
        });
    }

    /**
     * Set Default Callback of the property: <code>covertToTargetItem</code>
     */
    @Override
    protected void setDefCovertToTargetItem() {
        this.setCovertToTargetItem((convertItemContext) -> {
            DocMatItemNode targetMatItemNode = convertItemContext.getTargetMatItemNode();
            DocMatItemNode sourceMatItemNode = convertItemContext.getSourceMatItemNode();
            InventoryTransferItem inventoryTransferItem = (InventoryTransferItem) targetMatItemNode;
            inventoryTransferItem.setActualAmount(sourceMatItemNode.getAmount());
            inventoryTransferItem.setDeclaredValue(sourceMatItemNode.getItemPrice());
            if (sourceMatItemNode instanceof WarehouseStoreItem) {
                try {
                    InventoryTransferMatItemBatchGenRequest inventoryTransferMatItemBatchGenRequest = (InventoryTransferMatItemBatchGenRequest) convertItemContext.getDocumentMatItemBatchGenRequest();
                    return generateTransferItemFromWarehouseStoreItemCore((WarehouseStoreItem) sourceMatItemNode,
                            inventoryTransferItem, inventoryTransferMatItemBatchGenRequest);
                } catch (MaterialException | ServiceEntityConfigureException e) {
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                }
            }
            if (sourceMatItemNode instanceof OutboundItem) {
                // This logic is not needed, the field `refOutboundItemUUID` is not used any more on UI.
                OutboundItem outboundItem = (OutboundItem) sourceMatItemNode;
                inventoryTransferItem.setRefOutboundItemUUID(outboundItem.getUuid());
            }
            return targetMatItemNode;
        });
    }

    /**
     * Set Default Callback of the property: <code>covertToSourceItem</code>
     */
    @Override
    protected void setDefCovertToSourceItem() {
        this.setCovertToSourceItem((convertItemContext) -> {
            DocMatItemNode targetMatItemNode = convertItemContext.getTargetMatItemNode();
            DocMatItemNode sourceMatItemNode = convertItemContext.getSourceMatItemNode();
            InventoryTransferItem inventoryTransferItem = (InventoryTransferItem) sourceMatItemNode;
            if (targetMatItemNode instanceof InboundItem) {
                // This logic is not needed, the field `refInboundItemUUID` is not used any more on UI.
                InboundItem inboundItem = (InboundItem) targetMatItemNode;
                inventoryTransferItem.setRefInboundItemUUID(inboundItem.getUuid());
            }
            return targetMatItemNode;
        });
    }


    @Override
    public void setFilterSelectedMatItem(IFilterSelectedMatItem filterSelectedMatItem) {
        super.setFilterSelectedMatItem((docMatItemNode, materialStockKeepUnit, targetServiceModel) -> {
            InventoryTransferOrderServiceModel inventoryTransferOrderServiceModel =
                    (InventoryTransferOrderServiceModel) targetServiceModel;
            InventoryTransferOrder inventoryTransferOrder =
                    inventoryTransferOrderServiceModel.getInventoryTransferOrder();
            if (docMatItemNode instanceof WarehouseStoreItem) {
                WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) docMatItemNode;
                return warehouseStoreItem.getRefWarehouseUUID().equals(inventoryTransferOrder.getRefWarehouseUUID());
            }
            return false;
        });
    }

    private InventoryTransferItem generateTransferItemFromWarehouseStoreItemCore(
            WarehouseStoreItem warehouseStoreItem, InventoryTransferItem targetMatItemNode,
            InventoryTransferMatItemBatchGenRequest inventoryTransferMatItemBatchGenRequest)
            throws MaterialException, ServiceEntityConfigureException {
        if (inventoryTransferMatItemBatchGenRequest != null && inventoryTransferMatItemBatchGenRequest.getExcludeReserved() == StandardSwitchProxy.SWITCH_ON) {
            StorageCoreUnit availableStoreUnit = outboundDeliveryWarehouseItemManager.getAvailableStoreItemAmountUnion(
                    new StoreAvailableStoreItemRequest(warehouseStoreItem, null, false));
            if (availableStoreUnit.getAmount() <= 0) {
                return null;
            }
            targetMatItemNode.setAmount(availableStoreUnit.getAmount());
            targetMatItemNode.setRefUnitUUID(availableStoreUnit.getRefUnitUUID());
        } else {
            targetMatItemNode.setAmount(warehouseStoreItem.getAmount());
            targetMatItemNode.setRefUnitUUID(warehouseStoreItem.getRefUnitUUID());
        }
        // Important, has to be reset amount as current available amount
        targetMatItemNode.setRefStoreItemUUID(warehouseStoreItem.getUuid());
        targetMatItemNode.setVolume(warehouseStoreItem.getVolume());
        targetMatItemNode.setRefShelfNumberID(warehouseStoreItem.getRefShelfNumberId());
        targetMatItemNode.setRefWarehouseAreaUUID(warehouseStoreItem.getRefWarehouseAreaUUID());
        targetMatItemNode.setWeight(warehouseStoreItem.getWeight());
        targetMatItemNode.setDeclaredValue(warehouseStoreItem.getDeclaredValue());
        return targetMatItemNode;
    }

    /**
     * Set Default Callback: Logic to parse information from genRequest to
     */
    @Override
    public void setDefParseBatchGenRequest() {
        super.setParseBatchGenRequest((genRequest, targetServiceModel) -> {
            InventoryTransferMatItemBatchGenRequest inventoryTransferMatItemBatchGenRequest =
                    (InventoryTransferMatItemBatchGenRequest) genRequest;
            InventoryTransferOrder inventoryTransferOrder = targetServiceModel.getInventoryTransferOrder();
            try {
                copyRequestToInventoryTransferOrder(inventoryTransferMatItemBatchGenRequest,
                        inventoryTransferOrder);
            } catch (InventoryTransferOrderException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        });
    }

    /**
     * Core Logic to copy infomation from init model to newly created inventory transfer order
     * @param inventoryTransferMatItemBatchGenRequest
     * @param inventoryTransferOrder
     * @throws InventoryTransferOrderException
     */
    public void copyRequestToInventoryTransferOrder(InventoryTransferMatItemBatchGenRequest inventoryTransferMatItemBatchGenRequest,
                                                    InventoryTransferOrder inventoryTransferOrder)
            throws InventoryTransferOrderException {
        if (ServiceEntityStringHelper
                .checkNullString(inventoryTransferMatItemBatchGenRequest
                        .getRefInboundWarehouseUUID())) {
            throw new InventoryTransferOrderException(
                    InventoryTransferOrderException.PARA_NOTFOUND_INWAREHOUSE,
                    "");
        }
        if (ServiceEntityStringHelper
                .checkNullString(inventoryTransferMatItemBatchGenRequest
                        .getRefOutboundWarehouseUUID())) {
            throw new InventoryTransferOrderException(
                    InventoryTransferOrderException.PARA_NOTFOUND_OUTWAREHOUSE,
                    "");
        }
        if (ServiceCollectionsHelper
                .checkNullList(inventoryTransferMatItemBatchGenRequest.getUuidList())) {
            throw new InventoryTransferOrderException(
                    InventoryTransferOrderException.PARA_NOITEM_TOTRANSFER, "");
        }
        inventoryTransferOrder
                .setRefWarehouseUUID(inventoryTransferMatItemBatchGenRequest
                        .getRefOutboundWarehouseUUID());
        inventoryTransferOrder
                .setRefWarehouseAreaUUID(inventoryTransferMatItemBatchGenRequest
                        .getRefOutboundWarehouseAreaUUID());
        inventoryTransferOrder
                .setRefInboundWarehouseUUID(inventoryTransferMatItemBatchGenRequest
                        .getRefInboundWarehouseUUID());
        inventoryTransferOrder
                .setRefInboundWarehouseAreaUUID(inventoryTransferMatItemBatchGenRequest
                        .getRefInboundWarehouseAreaUUID());
    }

}
