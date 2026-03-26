package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.dto.InventoryTransferMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.RegisteredProductExtendProperty;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.DocumentContentSpecifier;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.CrossDocConvertRequest;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.util.ArrayList;
import java.util.List;

@Service
public class QualityInspectCrossConvertRequest extends
        CrossDocConvertRequest<QualityInspectOrderServiceModel, QualityInspectMatItem, QualityInspectMatItemServiceModel> {

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected QualityInspectOrderManager qualityInspectOrderManager;

    @Autowired
    protected QualityInspectOrderSpecifier qualityInspectOrderSpecifier;

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    protected Logger logger = LoggerFactory.getLogger(QualityInspectCrossConvertRequest.class);

    public QualityInspectCrossConvertRequest(){
        this.setTargetDocType(IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER);
    }

    /**
     * Set Default Callback: Logic to filter target doc for reused
     */
    @Override
    protected void setDefFilterTargetDoc() {
        this.setFilterTargetDoc((targetDoc, documentMatItemBatchGenRequest) -> {
            QualityInspectOrder qualityInspectOrder = (QualityInspectOrder) targetDoc;
            if(qualityInspectOrder.getStatus() != QualityInspectOrder.STATUS_INITIAL){
                return false;
            };
            if(documentMatItemBatchGenRequest instanceof DeliveryMatItemBatchGenRequest){
                DeliveryMatItemBatchGenRequest deliveryMatItemBatchGenRequest =
                        (DeliveryMatItemBatchGenRequest) documentMatItemBatchGenRequest;
                String requestWarehouseUUID = deliveryMatItemBatchGenRequest.getRefWarehouseUUID();
                String curWarehouseUUID = qualityInspectOrder.getRefWarehouseUUID();
                if(!ServiceEntityStringHelper.checkNullString(requestWarehouseUUID) && ServiceEntityStringHelper.checkNullString(curWarehouseUUID)){
                    return requestWarehouseUUID.equals(curWarehouseUUID);
                }
            }
            return true;
        });
    }

    @Override
    public void setDefConvertToTargetDoc() {
        super.setConvertToTargetDoc((convertDocumentContext) -> {
            ServiceModule serviceModule = convertDocumentContext.getSourceServiceModel();
            DocumentContentSpecifier sourceDocSpecifier = convertDocumentContext.getSourceDocSpecifier();
            ServiceEntityNode targetDocument = convertDocumentContext.getTargetDocument();
            DocumentContent sourceDoc = (DocumentContent) sourceDocSpecifier.getCoreEntity(serviceModule);
            QualityInspectOrder qualityInspectOrder = (QualityInspectOrder) targetDocument;
            int sourceDocType = sourceDocSpecifier.getDocumentType();
            if(sourceDocType == IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_TRANSFER){
                InventoryTransferOrder inventoryTransferOrder = (InventoryTransferOrder) sourceDoc;
                qualityInspectOrder.setRefWarehouseUUID(inventoryTransferOrder.getRefWarehouseUUID());
                return qualityInspectOrder;
            }
            boolean isSourceBusinessDoc = DocFlowProxy.checkBusinessDocType(sourceDocSpecifier.getDocumentType());
            if (isSourceBusinessDoc){
                qualityInspectOrder.setPrevProfDocType(sourceDocSpecifier.getDocumentType());
                qualityInspectOrder.setPrevProfDocUUID(sourceDoc.getUuid());
            } else {
                qualityInspectOrder.setPrevProfDocType(sourceDoc.getPrevProfDocType());
                qualityInspectOrder.setPrevProfDocUUID(sourceDoc.getPrevProfDocUUID());
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
            QualityInspectOrder qualityInspectOrder = targetServiceModel.getQualityInspectOrder();
            qualityInspectOrder.setRefWarehouseUUID(deliveryMatItemBatchGenRequest.getRefWarehouseUUID());
            qualityInspectOrder.setRefWarehouseAreaUUID(deliveryMatItemBatchGenRequest.getRefWarehouseAreaUUID());
        });
    }

    /**
     * Set Default Callback: Logic to post convert target item service model
     */
    @Override
    public void setDefConvertToTarItemServiceModel(){
        super.setConvertToTarItemServiceModel(
                (sourceMatItemNode, qualityInspectOrderServiceModel, qualityInspectMatItemServiceModel) -> {
                    try {
                        QualityInspectOrder qualityInspectOrder =
                                qualityInspectOrderServiceModel.getQualityInspectOrder();
                        QualityInspectMatItem qualityInspectMatItem =
                                qualityInspectMatItemServiceModel.getQualityInspectMatItem();
                        qualityInspectMatItem.setSampleAmount(sourceMatItemNode
                                .getAmount());
                        qualityInspectMatItem.setRefWarehouseAreaUUID(qualityInspectOrder
                                .getRefWarehouseAreaUUID());
                        genQualityInspectProperties(sourceMatItemNode,
                                qualityInspectMatItemServiceModel);
                    } catch (ServiceEntityConfigureException e) {
                        throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
                    }
               return qualityInspectMatItemServiceModel;
        });
    }

    /**
     * Set Default Callback: Logic to filter validate mat item
     */
    @Override
    public void setDefFilterSelectedMatItem() {
        super.setFilterSelectedMatItem((docMatItemNode, materialStockKeepUnit, targetServiceModel) -> {
            MaterialStockKeepUnit templateMaterial = materialStockKeepUnit;
            try {
                templateMaterial = materialStockKeepUnitManager.getRefTemplateMaterialSKU(materialStockKeepUnit);
                // Quality inspect flag only for template material
                if (templateMaterial.getQualityInspectFlag() == StandardSwitchProxy.SWITCH_OFF) {
                    return false;
                }
            } catch (ServiceEntityConfigureException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                return false;
            }
            return true;
        });
    }

    private int getCategoryBySourceDocType(int sourceDocType){
        if(sourceDocType == IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER || sourceDocType == IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER){
            return QualityInspectOrder.CATEGORY_PRODUCTION;
        }
        return QualityInspectOrder.CATEGORY_INBOUND;
    }

    private QualityInspectMatItemServiceModel genQualityInspectProperties(DocMatItemNode sourceDocMatItemNode,
           QualityInspectMatItemServiceModel qualityInspectMatItemServiceModel) throws ServiceEntityConfigureException {
        QualityInspectMatItem qualityInspectMatItem = qualityInspectMatItemServiceModel.getQualityInspectMatItem();
        List<QualityInspectPropertyItemServiceModel> qualityInspectPropertyItemList = new ArrayList<>();
        List<ServiceEntityNode> extendPropertyList = registeredProductManager
                .getEntityNodeListByKey(
                        sourceDocMatItemNode.getRefMaterialSKUUUID(),
                        IServiceEntityNodeFieldConstant.ROOTNODEUUID,
                        RegisteredProductExtendProperty.NODENAME, null);
        if (!ServiceCollectionsHelper.checkNullList(extendPropertyList)) {
            for (ServiceEntityNode seNode : extendPropertyList) {
                RegisteredProductExtendProperty registeredProductExtendProperty = (RegisteredProductExtendProperty) seNode;
                if (registeredProductExtendProperty.getQualityInspectFlag() != StandardSwitchProxy.SWITCH_ON) {
                    continue;
                }
                QualityInspectPropertyItem qualityInspectPropertyItem = (QualityInspectPropertyItem) qualityInspectOrderManager
                        .newEntityNode(qualityInspectMatItem,
                                QualityInspectPropertyItem.NODENAME);
                qualityInspectPropertyItem
                        .setRefPropertyUUID(registeredProductExtendProperty
                                .getUuid());
                qualityInspectPropertyItem
                        .setFieldName(registeredProductExtendProperty.getName());
                qualityInspectPropertyItem
                        .setName(registeredProductExtendProperty.getName());
                qualityInspectPropertyItem
                        .setId(registeredProductExtendProperty.getId());
                qualityInspectPropertyItem
                        .setActualValueDouble(registeredProductExtendProperty
                                .getDoubleValue());
                qualityInspectPropertyItem
                        .setRefUnitUUID(registeredProductExtendProperty
                                .getRefUnitUUID());
                QualityInspectPropertyItemServiceModel qualityInspectPropertyItemServiceModel = new QualityInspectPropertyItemServiceModel();
                qualityInspectPropertyItemServiceModel.setQualityInspectMatItem(qualityInspectPropertyItem);
                qualityInspectPropertyItemList.add(qualityInspectPropertyItemServiceModel);
            }
        }
        qualityInspectMatItemServiceModel
                .setQualityInspectPropertyItemList(qualityInspectPropertyItemList);
        return qualityInspectMatItemServiceModel;
    }

}
