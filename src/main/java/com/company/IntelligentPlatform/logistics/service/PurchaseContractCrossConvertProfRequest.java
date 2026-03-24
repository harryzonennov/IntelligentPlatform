package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.logistics.model.*;
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
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;


@Service
public class PurchaseContractCrossConvertProfRequest extends
        CrossDocConvertProfRequest<PurchaseContractServiceModel, PurchaseContractMaterialItem, PurchaseContractMaterialItemServiceModel> {

    @Autowired
    protected PurchaseContractManager purchaseContractManager;

    @Autowired
    protected WarehouseStoreManager warehouseStoreManager;

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected QualityInspectOrderActionExecutionProxy qualityInspectOrderActionExecutionProxy;

    @Autowired
    protected PurchaseContractCrossConvertRequest purchaseContractCrossConvertRequest;

    @Autowired
    protected PurchaseContractSpecifier purchaseContractSpecifier;

    @Autowired
    protected InquirySpecifier inquirySpecifier;

    @Autowired
    protected DocInvolvePartyProxy docInvolvePartyProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    protected Logger logger = LoggerFactory.getLogger(PurchaseContractCrossConvertProfRequest.class);

    public PurchaseContractCrossConvertProfRequest() {
        this.setTargetDocType(IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT);
    }

    @Override
    protected void setDefFilterRootDocContextPrevProf() {
        this.setFilterRootDocContextPrevProf((docContentCreateContext, prevProfMatItemNode, sourceMatItemNode) -> {
            PurchaseContractServiceModel purchaseContractServiceModel =
                    (PurchaseContractServiceModel) docContentCreateContext.getTargetRootDocument();
            if (purchaseContractServiceModel == null) {
                return false;
            }
            if (prevProfMatItemNode == null) {
                return false;
            }
            if (prevProfMatItemNode instanceof InquiryMaterialItem) {
                InquiryMaterialItem inquiryMaterialItem =
                        (InquiryMaterialItem) prevProfMatItemNode;
                InquiryParty sourceInquiryParty = null;
                try {
                    sourceInquiryParty =
                            (InquiryParty) docInvolvePartyProxy.getSourceInvolveParty(
                                    IDefDocumentResource.DOCUMENT_TYPE_INQUIRY,
                                    IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT,
                                    PurchaseContractParty.PARTY_ROLE_SUPPLIER,
                                    inquiryMaterialItem.getParentNodeUUID(),
                                    inquiryMaterialItem.getClient());
                } catch (DocActionException | ServiceEntityInstallationException | ServiceEntityConfigureException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    return false;
                }
                if (sourceInquiryParty == null) {
                    return false;
                }
                PurchaseContractParty targetSupplierParty =
                        (PurchaseContractParty) purchaseContractSpecifier.getDocInvolveParty(
                                PurchaseContractParty.PARTY_ROLE_SUPPLIER, purchaseContractServiceModel);
                return targetSupplierParty.getRefUUID()
                        .equals(sourceInquiryParty.getRefUUID());
            }
            if (prevProfMatItemNode instanceof PurchaseRequestMaterialItem) {
                PurchaseRequestMaterialItem purchaseRequestMaterialItem =
                        (PurchaseRequestMaterialItem) prevProfMatItemNode;
                PurchaseRequestParty sourceRequestParty = null;
                try {
                    sourceRequestParty =
                            (PurchaseRequestParty) docInvolvePartyProxy.getSourceInvolveParty(
                                    IDefDocumentResource.DOCUMENT_TYPE_PURCHASEREQUEST,
                                    IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT,
                                    PurchaseContractParty.ROLE_PARTYB,
                                    purchaseRequestMaterialItem.getParentNodeUUID(),
                                    purchaseRequestMaterialItem.getClient());
                } catch (DocActionException | ServiceEntityInstallationException | ServiceEntityConfigureException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    return false;
                }
                if (sourceRequestParty == null) {
                    return true;
                }
                PurchaseContractParty targetSupplierParty =
                        (PurchaseContractParty) purchaseContractSpecifier.getDocInvolveParty(
                                PurchaseContractParty.ROLE_PARTYB, purchaseContractServiceModel);
                return targetSupplierParty.getRefUUID()
                        .equals(sourceRequestParty.getRefUUID());
            }
            return true;
        });
    }

    @Override
    public CrossDocConvertRequest getCrossDocConvertRequest() {
        return purchaseContractCrossConvertRequest;
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

}
