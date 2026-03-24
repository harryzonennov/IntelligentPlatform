package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.dto.InboundItemSearchModel;
import com.company.IntelligentPlatform.logistics.service.QualityInspectOrderActionExecutionProxy;
import com.company.IntelligentPlatform.logistics.service.QualityInspectOrderManager;
import com.company.IntelligentPlatform.logistics.model.InboundItem;
import com.company.IntelligentPlatform.logistics.model.InboundDelivery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
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
import com.company.IntelligentPlatform.common.model.DocumentContent;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

@Service
public class InboundDeliveryCrossConvertRequest extends
        CrossDocConvertRequest<InboundDeliveryServiceModel, InboundItem, InboundItemServiceModel> {

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected InboundDeliveryManager inboundDeliveryManager;

    @Autowired
    protected InboundDeliverySpecifier inboundDeliverySpecifier;

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected QualityInspectOrderManager qualityInspectOrderManager;

    @Autowired
    protected QualityInspectOrderActionExecutionProxy qualityInspectOrderActionExecutionProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    protected Logger logger = LoggerFactory.getLogger(InboundDeliveryCrossConvertRequest.class);

    public InboundDeliveryCrossConvertRequest(){
        this.setTargetDocType(IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY);
    }


    /**
     * Set Default Callback: Logic to filter target doc for reused
     */
    @Override
    protected void setDefFilterTargetDoc() {
        this.setFilterTargetDoc((targetDoc, documentMatItemBatchGenRequest) -> {
            InboundDelivery inboundDelivery = (InboundDelivery) targetDoc;
            if(inboundDelivery.getStatus() != InboundDelivery.STATUS_INITIAL){
                return false;
            };
            if(documentMatItemBatchGenRequest instanceof DeliveryMatItemBatchGenRequest){
                DeliveryMatItemBatchGenRequest deliveryMatItemBatchGenRequest =
                        (DeliveryMatItemBatchGenRequest) documentMatItemBatchGenRequest;
                String requestWarehouseUUID = deliveryMatItemBatchGenRequest.getRefWarehouseUUID();
                String curWarehouseUUID = inboundDelivery.getRefWarehouseUUID();
                if(!ServiceEntityStringHelper.checkNullString(requestWarehouseUUID) && ServiceEntityStringHelper.checkNullString(curWarehouseUUID)){
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
        this.setGetTargetDocItemList((documentMatItemRawSearchRequest, logonInfo) -> {
            try {
                InboundItemSearchModel inboundItemSearchModel = new InboundItemSearchModel();
                inboundItemSearchModel.setRefWarehouse(new WarehouseSubSearchModel());
                String inboundUUIDArray = ServiceEntityStringHelper
                        .convStringListIntoMultiStringValue(documentMatItemRawSearchRequest.getUuidList());
                inboundItemSearchModel.setUuid(inboundUUIDArray);
                DocumentMatItemBatchGenRequest documentMatItemBatchGenRequest = documentMatItemRawSearchRequest.getGenRequest();
                if (documentMatItemBatchGenRequest instanceof DeliveryMatItemBatchGenRequest) {
                    DeliveryMatItemBatchGenRequest deliveryMatItemBatchGenRequest = (DeliveryMatItemBatchGenRequest) documentMatItemBatchGenRequest;
                    inboundItemSearchModel.setRefWarehouse(new WarehouseSubSearchModel());
                    inboundItemSearchModel.getRefWarehouse().setRefWarehouseUUID(deliveryMatItemBatchGenRequest.getRefWarehouseUUID());
                    inboundItemSearchModel.getRefWarehouse().setRefWarehouseAreaUUID(deliveryMatItemBatchGenRequest.getRefWarehouseAreaUUID());
                }
                SearchContextBuilder searchContextBuilder = SearchContextBuilder.genBuilder(logonInfo).searchModel(inboundItemSearchModel);
                return inboundDeliveryManager.getSearchProxy()
                        .searchItemList(searchContextBuilder.build()).getResultList();
            } catch (ServiceEntityConfigureException | ServiceEntityInstallationException | LogonInfoException |
                     AuthorizationException e) {
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
            DocumentContent sourceDoc = (DocumentContent) sourceDocSpecifier.getCoreEntity(serviceModule);
            InboundDelivery inboundDelivery =
                    (InboundDelivery) convertDocumentContext.getTargetDocument();
            boolean isSourceBusinessDoc = DocFlowProxy.checkBusinessDocType(sourceDocSpecifier.getDocumentType());
            if (isSourceBusinessDoc){
                inboundDelivery.setPrevProfDocType(sourceDocSpecifier.getDocumentType());
                inboundDelivery.setPrevProfDocUUID(sourceDoc.getUuid());
            } else {
                inboundDelivery.setPrevProfDocType(sourceDoc.getPrevProfDocType());
                inboundDelivery.setPrevProfDocUUID(sourceDoc.getPrevProfDocUUID());
            }
            return inboundDelivery;
        });
    }

    /**
     * Check if source document type is delivery document type or business document type
     * @param sourceDocType
     * @return
     */
    public static boolean checkSourceDocTypeDeliveryType(int sourceDocType) {
        if (sourceDocType == IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER) {
            return true;
        }
        if (sourceDocType == IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY) {
            return true;
        }
        if (sourceDocType == IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY) {
            return true;
        }
        if (sourceDocType == IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_TRANSFER) {
            return true;
        }
        if (sourceDocType == IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_CHECKORDER) {
            return true;
        }
        return false;
    }

    /**
     * Set Default Callback: Logic Copy from source mat item to target mat item
     */
    @Override
    protected void setDefCovertToTargetItem() {
        this.setCovertToTargetItem((convertItemContext) -> {
            DocMatItemNode sourceMatItemNode = convertItemContext.getSourceMatItemNode();
            InboundItem inboundItem = (InboundItem) convertItemContext.getTargetMatItemNode();
            inboundItem.setActualAmount(sourceMatItemNode.getAmount());
            inboundItem
                    .setDeclaredValue(sourceMatItemNode.getItemPrice());
            return inboundItem;
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
            InboundDelivery inboundDelivery = targetServiceModel.getInboundDelivery();
            inboundDelivery.setRefWarehouseUUID(deliveryMatItemBatchGenRequest.getRefWarehouseUUID());
            inboundDelivery.setRefWarehouseAreaUUID(deliveryMatItemBatchGenRequest.getRefWarehouseAreaUUID());
        });
    }

    /**
     * Set Default Callback: Logic to filter validate mat item
     */
    @Override
    public void setDefFilterSelectedMatItem() {
        super.setFilterSelectedMatItem((docMatItemNode, materialStockKeepUnit, serviceModule) -> {
            MaterialStockKeepUnit templateMaterial;
//            try {
//                templateMaterial = materialStockKeepUnitManager.getRefTemplateMaterialSKU(materialStockKeepUnit);
//                // Quality inspect flag only for template material
////                if (templateMaterial.getQualityInspectFlag() == StandardSwitchProxy.SWITCH_ON) {
////                    return false;
////                }
//            } catch (ServiceEntityConfigureException e) {
//                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
//                return false;
//            }
            return true;
        });
    }

}
