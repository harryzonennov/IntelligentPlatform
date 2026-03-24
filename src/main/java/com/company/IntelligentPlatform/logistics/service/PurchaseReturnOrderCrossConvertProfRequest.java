package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.logistics.model.PurchaseContractMaterialItem;
import com.company.IntelligentPlatform.logistics.model.PurchaseContractParty;
import com.company.IntelligentPlatform.logistics.model.PurchaseReturnMaterialItem;
import com.company.IntelligentPlatform.logistics.model.PurchaseReturnOrderParty;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.CrossDocConvertProfRequest;
import com.company.IntelligentPlatform.common.service.CrossDocConvertRequest;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;


@Service
public class PurchaseReturnOrderCrossConvertProfRequest extends
        CrossDocConvertProfRequest<PurchaseReturnOrderServiceModel, PurchaseReturnMaterialItem, PurchaseReturnMaterialItemServiceModel> {

    @Autowired
    protected PurchaseReturnOrderManager purchaseReturnOrderManager;

    @Autowired
    protected WarehouseStoreManager warehouseStoreManager;

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected PurchaseContractManager purchaseContractManager;

    @Autowired
    protected QualityInspectOrderActionExecutionProxy qualityInspectOrderActionExecutionProxy;

    @Autowired
    protected PurchaseReturnOrderCrossConvertRequest purchaseReturnOrderCrossConvertRequest;

    @Autowired
    protected PurchaseContractSpecifier purchaseContractSpecifier;

    @Autowired
    protected PurchaseReturnOrderSpecifier purchaseReturnOrderSpecifier;

    @Autowired
    protected DocInvolvePartyProxy docInvolvePartyProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    protected Logger logger = LoggerFactory.getLogger(PurchaseReturnOrderCrossConvertProfRequest.class);

    public PurchaseReturnOrderCrossConvertProfRequest() {
        this.setTargetDocType(IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER);
    }

    @Override
    protected void setDefFilterRootDocContextPrevProf() {
        this.setFilterRootDocContextPrevProf((docContentCreateContext, prevProfMatItemNode, sourceMatItemNode) -> {
            PurchaseReturnOrderServiceModel purchaseReturnOrderServiceModel =
                    (PurchaseReturnOrderServiceModel) docContentCreateContext.getTargetRootDocument();
            if (purchaseReturnOrderServiceModel == null) {
                return false;
            }
            if(prevProfMatItemNode == null){
                return false;
            }
            PurchaseContractMaterialItem purchaseContractMaterialItem =
                    (PurchaseContractMaterialItem) prevProfMatItemNode;
            PurchaseContractParty sourcePurchaseContractParty = null;
            try {
                sourcePurchaseContractParty =
                        (PurchaseContractParty) docInvolvePartyProxy.getSourceInvolveParty(
                                IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT,
                                IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER,
                                PurchaseReturnOrderParty.PARTY_ROLE_SUPPLIER,
                                purchaseContractMaterialItem.getRootNodeUUID(),
                                purchaseContractMaterialItem.getClient());
            } catch (DocActionException | ServiceEntityInstallationException | ServiceEntityConfigureException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                return false;
            }
            if(sourcePurchaseContractParty == null){
                return false;
            }
            PurchaseReturnOrderParty targetSupplierParty =
                    (PurchaseReturnOrderParty) purchaseReturnOrderSpecifier.getDocInvolveParty(
                            PurchaseReturnOrderParty.PARTY_ROLE_SUPPLIER, purchaseReturnOrderServiceModel);
            return targetSupplierParty.getRefUUID()
                    .equals(sourcePurchaseContractParty.getRefUUID());
        });
    }

    @Override
    public CrossDocConvertRequest getCrossDocConvertRequest() {
        return purchaseReturnOrderCrossConvertRequest;
    }

    @Override
    protected void setDefGetPrevProfMatItemBySource() {
        this.setGetPrevProfMatItemBySource((selectedSourceMatItem) -> {
            if (selectedSourceMatItem instanceof WarehouseStoreItem) {
                try {
                    return purchaseContractManager.getPrevEndPurchaseItem(selectedSourceMatItem);
                } catch (DocActionException e) {
                    throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_SYSTEM_WRONG, e.getErrorMessage());
                }
            }
            return null;
        });
    }

    /**
     * Set Default Callback: Logic Copy from source doc to target doc
     */
    @Override
    protected void setDefConvertToTargetItemProf() {

        this.setConvertToTargetItemProf((docMatItemCreateContext) -> {
            DocMatItemNode sourceMatItemNode = docMatItemCreateContext.getSourceDocMatItemNode();
            if (sourceMatItemNode.getHomeDocumentType() == IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM) {
                WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) sourceMatItemNode;
                PurchaseReturnMaterialItem purchaseReturnMaterialItem =
                        (PurchaseReturnMaterialItem) docMatItemCreateContext.getTargetDocMatItemNode();
                purchaseReturnMaterialItem.setRefStoreItemUUID(sourceMatItemNode.getUuid());
                warehouseStoreItem.setReservedDocType(IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER);
                warehouseStoreItem.setReservedMatItemUUID(purchaseReturnMaterialItem.getUuid());
            }
            return docMatItemCreateContext;
        });
    }

}
