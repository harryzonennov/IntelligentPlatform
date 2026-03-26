package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreItemSearchModel;
import com.company.IntelligentPlatform.logistics.service.InboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.QualityInspectOrderActionExecutionProxy;
import com.company.IntelligentPlatform.logistics.service.QualityInspectOrderManager;
import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.model.WarehouseStore;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItemLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.RegisteredProduct;
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
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

@Service
public class WarehouseStoreCrossConvertRequest extends
        CrossDocConvertRequest<WarehouseStoreServiceModel, WarehouseStoreItem, WarehouseStoreItemServiceModel> {

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected WarehouseStoreManager warehouseStoreManager;

    @Autowired
    protected InboundDeliveryManager inboundDeliveryManager;

    @Autowired
    protected WarehouseStoreSpecifier warehouseStoreSpecifier;

    @Autowired
    protected QualityInspectOrderManager qualityInspectOrderManager;

    @Autowired
    protected QualityInspectOrderActionExecutionProxy qualityInspectOrderActionExecutionProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    protected Logger logger = LoggerFactory.getLogger(WarehouseStoreCrossConvertRequest.class);

    public WarehouseStoreCrossConvertRequest(){
        this.setTargetDocType(IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM);
    }

    /**
     * Set Default Callback: Logic to filter target doc for reused
     */
    @Override
    protected void setDefFilterTargetDoc() {
        this.setFilterTargetDoc((targetDoc, documentMatItemBatchGenRequest) -> {
            WarehouseStore warehouseStore = (WarehouseStore) targetDoc;
            if(warehouseStore.getStatus() != WarehouseStore.STATUS_INITIAL){
                return false;
            };
            if(documentMatItemBatchGenRequest instanceof DeliveryMatItemBatchGenRequest){
                DeliveryMatItemBatchGenRequest deliveryMatItemBatchGenRequest =
                        (DeliveryMatItemBatchGenRequest) documentMatItemBatchGenRequest;
                String requestWarehouseUUID = deliveryMatItemBatchGenRequest.getRefWarehouseUUID();
                String curWarehouseUUID = warehouseStore.getRefWarehouseUUID();
                if(!ServiceEntityStringHelper.checkNullString(requestWarehouseUUID) && ServiceEntityStringHelper.checkNullString(curWarehouseUUID)){
                    return requestWarehouseUUID.equals(curWarehouseUUID);
                }
            }
            return true;
        });
    }

    /**
     * Set Default Callback: Logic to post convert target item service model
     */
    @Override
    public void setDefConvertToTarItemServiceModel(){
        super.setConvertToTarItemServiceModel(
                (sourceMatItemNode, warehouseStoreServiceModel, warehouseStoreItemServiceModel) -> {
                    try {
                        if(sourceMatItemNode instanceof InboundItem){
                            WarehouseStoreItem warehouseStoreItem = warehouseStoreItemServiceModel.getWarehouseStoreItem();
                            InboundItem inboundItem = (InboundItem) sourceMatItemNode;
                            // The warehouse store item back should be retrieved before update to DB
                            WarehouseStoreItem warehouseStoreItemBack = (WarehouseStoreItem) warehouseStoreManager.getDBEntityNodeByUUID(warehouseStoreItem.getUuid(),
                                    WarehouseStoreItem.NODENAME, warehouseStoreItem.getClient());
                            WarehouseStoreItemLog warehouseStoreItemLog = warehouseStoreManager.updateStoreItemLogFromDocItem(
                                    inboundItem, warehouseStoreItem,
                                    warehouseStoreItemBack);
                            WarehouseStoreItemLogServiceModel warehouseStoreItemLogServiceModel = new WarehouseStoreItemLogServiceModel();
                            warehouseStoreItemLogServiceModel.setWarehouseStoreItemLog(warehouseStoreItemLog);
                            if(warehouseStoreItemLog != null){
                                warehouseStoreItemServiceModel.setWarehouseStoreItemLogList(ServiceCollectionsHelper.asList(warehouseStoreItemLogServiceModel));
                            }
                        }
                    } catch (ServiceEntityConfigureException  e) {
                        throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
                    } catch (MaterialException e) {
                        throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                    }
                    return warehouseStoreItemServiceModel;
                });
    }

    /**
     * Set Default Callback: Logic Copy from source mat item to target mat item
     */
    @Override
    protected void setDefCovertToTargetItem() {
        this.setCovertToTargetItem((convertItemContext) -> {
            DocMatItemNode sourceMatItemNode = convertItemContext.getSourceMatItemNode();
            WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) convertItemContext.getTargetMatItemNode();
            warehouseStoreItem
                    .setDeclaredValue(sourceMatItemNode.getItemPrice());
            if(sourceMatItemNode instanceof InboundItem) {
                InboundItem inboundItem = (InboundItem) sourceMatItemNode;
                warehouseStoreItem.setVolume(inboundItem.getVolume());
                warehouseStoreItem.setWeight(inboundItem.getWeight());
                warehouseStoreItem.setDeclaredValue(inboundItem
                        .getDeclaredValue());
                warehouseStoreItem.setRefWarehouseAreaUUID(inboundItem.getRefWarehouseAreaUUID());
                warehouseStoreItem.setRefShelfNumberId(inboundItem
                        .getRefShelfNumberID());
                warehouseStoreItem.setProductionPlace(inboundItem
                        .getProducerName());
                warehouseStoreItem.setRefUnitName(inboundItem
                        .getRefUnitName());
            }
            try {
                MaterialStockKeepUnit materialStockKeepUnit = docFlowProxy.getMaterialSKUFromDocMatItem(sourceMatItemNode);
                convMaterialStockKeepUnitToWarehouseStoreItem(materialStockKeepUnit, warehouseStoreItem);
            } catch (ServiceEntityConfigureException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
            return warehouseStoreItem;
        });
    }

    public void convMaterialStockKeepUnitToWarehouseStoreItem(
            MaterialStockKeepUnit materialStockKeepUnit,
            WarehouseStoreItem warehouseStoreItem) {
        if (materialStockKeepUnit != null) {
            warehouseStoreItem.setRefMaterialSKUUUID(materialStockKeepUnit.getUuid());
            warehouseStoreItem.setRefMaterialSKUName(materialStockKeepUnit.getName());
            warehouseStoreItem.setRefMaterialSKUId(materialStockKeepUnit.getId());
            warehouseStoreItem.setRefMaterialTemplateUUID(materialStockKeepUnit.getUuid());
            if (RegisteredProductManager.checkRegisteredProduct(materialStockKeepUnit)) {
                RegisteredProduct registeredProduct = (RegisteredProduct) materialStockKeepUnit;
                warehouseStoreItem.setRefMaterialTemplateUUID(registeredProduct.getRefMaterialSKUUUID());
            }
        }
    }

    /**
     * Set Default Callback: Logic to get available target doc item list
     */
    @Override
    protected void setDefGetTargetDocItemList() {
        this.setGetTargetDocItemList((documentMatItemRawSearchRequest, logonInfo) -> {
            try {
                WarehouseStoreItemSearchModel warehouseStoreItemSearchModel = new WarehouseStoreItemSearchModel();
                String inboundUUIDArray = ServiceEntityStringHelper
                        .convStringListIntoMultiStringValue(documentMatItemRawSearchRequest.getUuidList());
                warehouseStoreItemSearchModel.setUuid(inboundUUIDArray);
                DocumentMatItemBatchGenRequest documentMatItemBatchGenRequest = documentMatItemRawSearchRequest.getGenRequest();
                if (documentMatItemBatchGenRequest instanceof DeliveryMatItemBatchGenRequest) {
                    DeliveryMatItemBatchGenRequest deliveryMatItemBatchGenRequest = (DeliveryMatItemBatchGenRequest) documentMatItemBatchGenRequest;
                    warehouseStoreItemSearchModel.setRefWarehouse(new WarehouseSubSearchModel());
                    warehouseStoreItemSearchModel.getRefWarehouse().setRefWarehouseUUID(deliveryMatItemBatchGenRequest.getRefWarehouseUUID());
                    warehouseStoreItemSearchModel.getRefWarehouse().setRefWarehouseAreaUUID(deliveryMatItemBatchGenRequest.getRefWarehouseAreaUUID());
                }
                SearchContextBuilder searchContextBuilder = SearchContextBuilder.genBuilder(logonInfo).searchModel(warehouseStoreItemSearchModel);
                return warehouseStoreManager.getSearchProxy()
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
            ServiceEntityNode targetDocument = convertDocumentContext.getTargetDocument();
            ServiceEntityNode sourceDoc = sourceDocSpecifier.getCoreEntity(serviceModule);
            if(sourceDoc instanceof InboundDelivery){
                InboundDelivery inboundDelivery = (InboundDelivery) sourceDoc;
                WarehouseStore warehouseStore =
                        (WarehouseStore) warehouseStoreManager.newRootEntityNode(sourceDoc.getClient());
                warehouseStore.setRefWarehouseUUID(inboundDelivery.getRefWarehouseUUID());
                warehouseStore.setRefWarehouseAreaUUID(inboundDelivery.getRefWarehouseAreaUUID());
                return warehouseStore;
            }
            return targetDocument;
        });
    }

    /**
     * Set Default Callback: Logic to parse information from genRequest to
     */
    @Override
    public void setDefParseBatchGenRequest() {
        super.setParseBatchGenRequest((genRequest, targetServiceModel) -> {
            DeliveryMatItemBatchGenRequest deliveryMatItemBatchGenRequest =
                    (DeliveryMatItemBatchGenRequest) genRequest;
            WarehouseStore warehouseStore = targetServiceModel.getWarehouseStore();
            warehouseStore.setRefWarehouseUUID(deliveryMatItemBatchGenRequest.getRefWarehouseUUID());
            warehouseStore.setRefWarehouseAreaUUID(deliveryMatItemBatchGenRequest.getRefWarehouseAreaUUID());
        });
    }

}
