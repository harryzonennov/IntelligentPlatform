package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.dto.OutboundItemSearchModel;
import com.company.IntelligentPlatform.logistics.model.InboundDelivery;
import com.company.IntelligentPlatform.logistics.model.InboundItem;
import com.company.IntelligentPlatform.logistics.model.OutboundDelivery;
import com.company.IntelligentPlatform.logistics.model.OutboundItem;
import com.company.IntelligentPlatform.logistics.model.StoreAvailableStoreItemRequest;
import com.company.IntelligentPlatform.logistics.model.WarehouseStore;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.dto.WarehouseSubSearchModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.DocumentContentSpecifier;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.CrossDocConvertRequest;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.SearchContextBuilder;
import com.company.IntelligentPlatform.common.model.DocumentMatItemBatchGenRequest;
import com.company.IntelligentPlatform.common.model.DocumentMatItemRawSearchRequest;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

@Service
public class OutboundDeliveryCrossConvertRequest
        extends CrossDocConvertRequest<OutboundDeliveryServiceModel, OutboundItem, OutboundItemServiceModel> {

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected OutboundDeliveryManager outboundDeliveryManager;

    @Autowired
    protected OutboundDeliverySpecifier outboundDeliverySpecifier;

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected OutboundDeliveryWarehouseItemManager outboundDeliveryWarehouseItemManager;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    protected Logger logger = LoggerFactory.getLogger(OutboundDeliveryCrossConvertRequest.class);

    public OutboundDeliveryCrossConvertRequest() {
        this.setTargetDocType(IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY);
    }

    /**
     * Set Default Callback: Logic to filter target doc for reused
     */
    @Override
    protected void setDefFilterTargetDoc() {
        this.setFilterTargetDoc((targetDoc, documentMatItemBatchGenRequest) -> {
            OutboundDelivery outboundDelivery = (OutboundDelivery) targetDoc;
            if (outboundDelivery.getStatus() != OutboundDelivery.STATUS_INITIAL) {
                return false;
            }
            ;
            if (documentMatItemBatchGenRequest instanceof DeliveryMatItemBatchGenRequest) {
                DeliveryMatItemBatchGenRequest deliveryMatItemBatchGenRequest =
                        (DeliveryMatItemBatchGenRequest) documentMatItemBatchGenRequest;
                String requestWarehouseUUID = deliveryMatItemBatchGenRequest.getRefWarehouseUUID();
                String curWarehouseUUID = outboundDelivery.getRefWarehouseUUID();
                if (!ServiceEntityStringHelper.checkNullString(requestWarehouseUUID) &&
                        ServiceEntityStringHelper.checkNullString(curWarehouseUUID)) {
                    return requestWarehouseUUID.equals(curWarehouseUUID);
                }
            }
            return true;
        });
    }

    /**
     * Set Default Callback: Logic to get available target doc item list
     */
    @Override
    protected void setDefGetTargetDocItemList() {
        this.setGetTargetDocItemList((documentMatItemRawSearchRequest, logonInfo)  -> {
            try {
                OutboundItemSearchModel outboundItemSearchModel = new OutboundItemSearchModel();
                outboundItemSearchModel.setRefWarehouse(new WarehouseSubSearchModel());
                String outboundUUIDArray = ServiceEntityStringHelper.convStringListIntoMultiStringValue(
                        documentMatItemRawSearchRequest.getUuidList());
                outboundItemSearchModel.setUuid(outboundUUIDArray);
                DocumentMatItemBatchGenRequest documentMatItemBatchGenRequest = documentMatItemRawSearchRequest.getGenRequest();
                if (documentMatItemBatchGenRequest instanceof DeliveryMatItemBatchGenRequest) {
                    DeliveryMatItemBatchGenRequest deliveryMatItemBatchGenRequest = (DeliveryMatItemBatchGenRequest) documentMatItemBatchGenRequest;
                    outboundItemSearchModel.setRefWarehouse(new WarehouseSubSearchModel());
                    outboundItemSearchModel.getRefWarehouse().setRefWarehouseUUID(deliveryMatItemBatchGenRequest.getRefWarehouseUUID());
                    outboundItemSearchModel.getRefWarehouse().setRefWarehouseAreaUUID(deliveryMatItemBatchGenRequest.getRefWarehouseAreaUUID());
                }
                SearchContextBuilder searchContextBuilder = SearchContextBuilder.genBuilder(logonInfo).searchModel(outboundItemSearchModel);
                return outboundDeliveryManager.getSearchProxy()
                        .searchItemList(searchContextBuilder.build()).getResultList();
            } catch (ServiceEntityConfigureException | ServiceEntityInstallationException | AuthorizationException |
                     LogonInfoException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        });
    }

    @Override
    public void setDefConvertToTargetDoc() {
        super.setConvertToTargetDoc((convertDocumentContext) -> {
            ServiceModule serviceModule = convertDocumentContext.getSourceServiceModel();
            DocumentContentSpecifier sourceDocSpecifier = convertDocumentContext.getSourceDocSpecifier();
            ServiceEntityNode sourceDoc = sourceDocSpecifier.getCoreEntity(serviceModule);
            OutboundDelivery outboundDelivery =
                    (OutboundDelivery) convertDocumentContext.getTargetDocument();
            outboundDelivery.setNextProfDocType(sourceDocSpecifier.getDocumentType());
            outboundDelivery.setNextProfDocUUID(sourceDoc.getUuid());
            if (sourceDoc instanceof WarehouseStore) {
                WarehouseStore warehouseStore = (WarehouseStore) sourceDoc;
                outboundDelivery.setRefWarehouseUUID(warehouseStore.getRefWarehouseUUID());
            }
            return outboundDelivery;
        });
    }

    /**
     * Set Default Callback: Logic Copy from source mat item to target mat item
     */
    @Override
    protected void setDefCovertToTargetItem() {
        this.setCovertToTargetItem((convertItemContext) -> {
            DocMatItemNode sourceMatItemNode = convertItemContext.getSourceMatItemNode();
            OutboundItem outboundItem = (OutboundItem) convertItemContext.getTargetMatItemNode();
            outboundItem.setActualAmount(sourceMatItemNode.getAmount());
            outboundItem.setDeclaredValue(sourceMatItemNode.getItemPrice());
            if (sourceMatItemNode instanceof WarehouseStoreItem) {
                try {
                    return generateOutboundItemFromWarehouseStoreItemCore((WarehouseStoreItem) sourceMatItemNode,
                            outboundItem);
                } catch (MaterialException e) {
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                } catch (ServiceEntityConfigureException e) {
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
                }
            }
            return outboundItem;
        });
    }

    @Override
    public void setFilterSelectedMatItem(IFilterSelectedMatItem filterSelectedMatItem) {
        super.setFilterSelectedMatItem((docMatItemNode, materialStockKeepUnit, targetServiceModel) -> {
            OutboundDeliveryServiceModel outboundDeliveryServiceModel =
                    (OutboundDeliveryServiceModel) targetServiceModel;
            OutboundDelivery outboundDelivery = outboundDeliveryServiceModel.getOutboundDelivery();
            if (docMatItemNode instanceof WarehouseStoreItem) {
                WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) docMatItemNode;
                return warehouseStoreItem.getRefWarehouseUUID().equals(outboundDelivery.getRefWarehouseUUID());
            }
            return false;
        });
    }

    private OutboundItem generateOutboundItemFromWarehouseStoreItemCore(WarehouseStoreItem warehouseStoreItem,
                                                                        OutboundItem targetMatItemNode)
            throws MaterialException, ServiceEntityConfigureException {
        StorageCoreUnit availableStoreUnit = outboundDeliveryWarehouseItemManager.getAvailableStoreItemAmountUnion(
                new StoreAvailableStoreItemRequest(warehouseStoreItem, targetMatItemNode.getUuid(), false));
        if (availableStoreUnit.getAmount() <= 0) {
            return null;
        }
        targetMatItemNode.setRefStoreItemUUID(warehouseStoreItem.getUuid());
        targetMatItemNode.setAmount(availableStoreUnit.getAmount());
        targetMatItemNode.setRefUnitUUID(availableStoreUnit.getRefUnitUUID());
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
            DeliveryMatItemBatchGenRequest deliveryMatItemBatchGenRequest = (DeliveryMatItemBatchGenRequest) genRequest;
            OutboundDelivery outboundDelivery = targetServiceModel.getOutboundDelivery();
            outboundDelivery.setRefWarehouseUUID(deliveryMatItemBatchGenRequest.getRefWarehouseUUID());
            outboundDelivery.setRefWarehouseAreaUUID(deliveryMatItemBatchGenRequest.getRefWarehouseAreaUUID());
        });
    }

}
