package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.common.service.DocumentContentSpecifier;
import com.company.IntelligentPlatform.common.service.CrossDocConvertRequest;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.PricingSettingManager;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

@Service
public class PurchaseReturnOrderCrossConvertRequest extends
        CrossDocConvertRequest<PurchaseReturnOrderServiceModel, PurchaseReturnMaterialItem,
                PurchaseReturnMaterialItemServiceModel> {

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected PurchaseReturnOrderManager purchaseReturnOrderManager;

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected PurchaseReturnOrderSpecifier purchaseReturnOrderSpecifier;

    @Autowired
    protected DocInvolvePartyProxy docInvolvePartyProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected WarehouseStoreManager warehouseStoreManager;

    @Autowired
    protected PurchaseContractManager purchaseContractManager;

    @Autowired
    protected PricingSettingManager pricingSettingManager;

    protected Logger logger = LoggerFactory.getLogger(PurchaseReturnOrderCrossConvertRequest.class);

    public PurchaseReturnOrderCrossConvertRequest(){
        this.setTargetDocType(IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER);
    }

    @Override
    public void setDefConvertToTargetDoc() {
        super.setConvertToTargetDoc((convertDocumentContext) -> {
            ServiceModule serviceModule = convertDocumentContext.getSourceServiceModel();
            DocumentContentSpecifier sourceDocSpecifier = convertDocumentContext.getSourceDocSpecifier();
            ServiceEntityNode targetDocument = convertDocumentContext.getTargetDocument();
            ServiceEntityNode sourceDoc = sourceDocSpecifier.getCoreEntity(serviceModule);
            PurchaseReturnOrder purchaseReturnOrder =
                    (PurchaseReturnOrder) targetDocument;
            if(sourceDoc instanceof PurchaseContract){
                purchaseReturnOrder.setPrevProfDocUUID(sourceDoc.getUuid());
                purchaseReturnOrder.setPrevProfDocType(IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT);
                return purchaseReturnOrder;
            }
            return targetDocument;
        });
    }

    @Override
    protected void setDefCovertToTargetItem() {
        super.setCovertToTargetItem((convertItemContext) -> {
            DocMatItemNode targetMatItemNode = convertItemContext.getTargetMatItemNode();
            DocMatItemNode sourceMatItemNode = convertItemContext.getSourceMatItemNode();
            if(sourceMatItemNode instanceof PurchaseContractMaterialItem){
                try {
                    return generateTransferItemFromWarehouseStoreItemCore((PurchaseContractMaterialItem) sourceMatItemNode, (PurchaseReturnMaterialItem) targetMatItemNode);
                } catch (MaterialException e) {
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                } catch (ServiceEntityConfigureException e) {
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
                }
            }
            return null;
        });
    }

    private PurchaseReturnMaterialItem generateTransferItemFromWarehouseStoreItemCore(PurchaseContractMaterialItem purchaseContractMaterialItem,
                                                                                      PurchaseReturnMaterialItem purchaseReturnMaterialItem)
            throws MaterialException, ServiceEntityConfigureException, DocActionException {
        DocMatItemNode endDocItemNode =
                (DocMatItemNode) purchaseContractManager.getEndDocItem(purchaseContractMaterialItem);
        /*
         * [Step2] Set relationship to purchase request
         */
        purchaseReturnMaterialItem.setRefDocItemType(IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT);
        purchaseReturnMaterialItem.setRefDocItemUUID(purchaseContractMaterialItem.getUuid());
        /*
         * [Step3] In case end doc is store item, then set store item
         */
        if (IServiceModelConstants.WarehouseStoreItem.equals(endDocItemNode.getNodeName())) {
            WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) endDocItemNode;
            purchaseReturnMaterialItem.setRefStoreItemUUID(endDocItemNode.getUuid());
            // reserve store item
            warehouseStoreItem.setReservedDocType(IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER);
            warehouseStoreItem.setReservedMatItemUUID(purchaseReturnMaterialItem.getRefStoreItemUUID());
            warehouseStoreManager.updateSENode(warehouseStoreItem, warehouseStoreItem.getResEmployeeUUID(),
                    warehouseStoreItem.getResOrgUUID());
        }
        return purchaseReturnMaterialItem;
    }

}
