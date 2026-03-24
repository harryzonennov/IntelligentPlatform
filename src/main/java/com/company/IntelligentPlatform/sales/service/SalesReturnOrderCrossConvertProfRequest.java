package com.company.IntelligentPlatform.sales.service;

import com.company.IntelligentPlatform.logistics.service.*;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.sales.model.SalesContractMaterialItem;
import com.company.IntelligentPlatform.sales.model.SalesContractParty;
import com.company.IntelligentPlatform.sales.model.SalesReturnMaterialItem;
import com.company.IntelligentPlatform.sales.model.SalesReturnOrderParty;
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
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;


@Service
public class SalesReturnOrderCrossConvertProfRequest extends
        CrossDocConvertProfRequest<SalesReturnOrderServiceModel, SalesReturnMaterialItem, SalesReturnMaterialItemServiceModel> {

    @Autowired
    protected SalesReturnOrderManager salesReturnOrderManager;

    @Autowired
    protected WarehouseStoreManager warehouseStoreManager;

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected SalesContractManager salesContractManager;

    @Autowired
    protected QualityInspectOrderActionExecutionProxy qualityInspectOrderActionExecutionProxy;

    @Autowired
    protected SalesReturnOrderCrossConvertRequest salesReturnOrderCrossConvertRequest;

    @Autowired
    protected SalesContractSpecifier salesContractSpecifier;

    @Autowired
    protected SalesReturnOrderSpecifier salesReturnOrderSpecifier;

    @Autowired
    protected DocInvolvePartyProxy docInvolvePartyProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    protected Logger logger = LoggerFactory.getLogger(SalesReturnOrderCrossConvertProfRequest.class);

    public SalesReturnOrderCrossConvertProfRequest() {
        this.setTargetDocType(IDefDocumentResource.DOCUMENT_TYPE_SALESRETURNORDER);
    }

    @Override
    protected void setDefFilterRootDocContextPrevProf() {
        this.setFilterRootDocContextPrevProf((docContentCreateContext, prevProfMatItemNode, sourceMatItemNode) -> {
            SalesReturnOrderServiceModel salesReturnOrderServiceModel =
                    (SalesReturnOrderServiceModel) docContentCreateContext.getTargetRootDocument();
            if (salesReturnOrderServiceModel == null) {
                return false;
            }
            if(prevProfMatItemNode == null){
                return false;
            }
            SalesContractMaterialItem salesContractMaterialItem =
                    (SalesContractMaterialItem) prevProfMatItemNode;
            SalesContractParty sourceSalesContractParty = null;
            try {
                sourceSalesContractParty =
                        (SalesContractParty) docInvolvePartyProxy.getSourceInvolveParty(
                                IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT,
                                IDefDocumentResource.DOCUMENT_TYPE_SALESRETURNORDER,
                                SalesReturnOrderParty.PARTY_ROLE_SUPPLIER,
                                salesContractMaterialItem.getRootNodeUUID(),
                                salesContractMaterialItem.getClient());
            } catch (DocActionException | ServiceEntityInstallationException | ServiceEntityConfigureException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                return false;
            }
            if(sourceSalesContractParty == null){
                return false;
            }
            SalesReturnOrderParty targetCustomerParty =
                    (SalesReturnOrderParty) salesReturnOrderSpecifier.getDocInvolveParty(
                            SalesReturnOrderParty.PARTY_ROLE_CUSTOMER, salesReturnOrderServiceModel);
            return targetCustomerParty.getRefUUID()
                    .equals(sourceSalesContractParty.getRefUUID());
        });
    }

    @Override
    public CrossDocConvertRequest getCrossDocConvertRequest() {
        return salesReturnOrderCrossConvertRequest;
    }

    @Override
    protected void setDefGetPrevProfMatItemBySource() {
        this.setGetPrevProfMatItemBySource((selectedSourceMatItem) -> {
            ServiceEntityNode
                    rawPrevMatItem = null;
            try {
                rawPrevMatItem = docFlowProxy.findTargetDocMatItemTillPrev(selectedSourceMatItem,
                ServiceCollectionsHelper.asList(IServiceModelConstants.SalesContract));
            } catch (DocActionException e) {
                throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_SYSTEM_WRONG, e.getErrorMessage());
            }
            if (rawPrevMatItem instanceof SalesContractMaterialItem){
                return (SalesContractMaterialItem) rawPrevMatItem;
            }
            return null;
        });
    }


}