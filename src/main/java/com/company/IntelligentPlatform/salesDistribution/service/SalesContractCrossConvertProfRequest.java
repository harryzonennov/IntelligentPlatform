package com.company.IntelligentPlatform.salesDistribution.service;

import com.company.IntelligentPlatform.logistics.service.*;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.salesDistribution.model.SalesContractMaterialItem;
import com.company.IntelligentPlatform.salesDistribution.model.SalesContractParty;
import com.company.IntelligentPlatform.salesDistribution.model.SalesForcastMaterialItem;
import com.company.IntelligentPlatform.salesDistribution.model.SalesForcastParty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.platform.service.RegisteredProductManager;
import com.company.IntelligentPlatform.platform.service.DocActionException;
import com.company.IntelligentPlatform.platform.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.CrossDocConvertProfRequest;
import com.company.IntelligentPlatform.platform.service.CrossDocConvertRequest;
import com.company.IntelligentPlatform.platform.service.DocFlowProxy;
import com.company.IntelligentPlatform.platform.model.IDefDocumentResource;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;

@Service
public class SalesContractCrossConvertProfRequest extends
        CrossDocConvertProfRequest<SalesContractServiceModel, SalesContractMaterialItem, SalesContractMaterialItemServiceModel> {

    @Autowired
    protected SalesContractManager salesContractManager;

    @Autowired
    protected WarehouseStoreManager warehouseStoreManager;

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected QualityInspectOrderActionExecutionProxy qualityInspectOrderActionExecutionProxy;

    @Autowired
    protected SalesContractCrossConvertRequest salesContractCrossConvertRequest;

    @Autowired
    protected SalesContractSpecifier salesContractSpecifier;

    @Autowired
    protected InquirySpecifier inquirySpecifier;

    @Autowired
    protected DocInvolvePartyProxy docInvolvePartyProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    protected Logger logger = LoggerFactory.getLogger(SalesContractCrossConvertProfRequest.class);

    public SalesContractCrossConvertProfRequest() {
        this.setTargetDocType(IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT);
    }

    @Override
    protected void setDefFilterRootDocContextPrevProf() {
        this.setFilterRootDocContextPrevProf((docContentCreateContext, prevProfMatItemNode, sourceMatItemNode) -> {
            SalesContractServiceModel salesContractServiceModel =
                    (SalesContractServiceModel) docContentCreateContext.getTargetRootDocument();
            if (salesContractServiceModel == null) {
                return false;
            }
            if (prevProfMatItemNode == null) {
                return false;
            }
            
            if (prevProfMatItemNode instanceof SalesForcastMaterialItem) {
                SalesForcastMaterialItem salesForcastMaterialItem =
                        (SalesForcastMaterialItem) prevProfMatItemNode;
                SalesForcastParty sourceRequestParty = null;
                try {
                    sourceRequestParty =
                            (SalesForcastParty) docInvolvePartyProxy.getSourceInvolveParty(
                                    IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST,
                                    IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT,
                                    SalesContractParty.ROLE_SOLD_TO_PARTY,
                                    salesForcastMaterialItem.getParentNodeUUID(),
                                    salesForcastMaterialItem.getClient());
                } catch (DocActionException | ServiceEntityInstallationException | ServiceEntityConfigureException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    return false;
                }
                if (sourceRequestParty == null) {
                    return true;
                }
                SalesContractParty targetSoldToParty =
                        (SalesContractParty) salesContractSpecifier.getDocInvolveParty(
                                SalesContractParty.ROLE_SOLD_TO_PARTY, salesContractServiceModel);
                return targetSoldToParty.getRefUUID()
                        .equals(sourceRequestParty.getRefUUID());
            }
            return true;
        });
    }

    @Override
    public CrossDocConvertRequest getCrossDocConvertRequest() {
        return salesContractCrossConvertRequest;
    }

}