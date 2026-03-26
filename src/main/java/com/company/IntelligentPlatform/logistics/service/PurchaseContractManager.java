package com.company.IntelligentPlatform.logistics.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.text.ParseException;
import java.util.*;

import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.logistics.dto.RegisteredProductBatchGenRequest;
import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.repository.PurchaseContractRepository;
import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.model.PurchaseContractConfigureProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.common.service.IndividualCustomerManager;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductException;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceFlowRuntimeEngine;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.ServiceFlowRuntimeException;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Service
@Transactional
public class PurchaseContractManager extends ServiceEntityManager {

    public static final String METHOD_ConvPurchaseContractToUI = "convPurchaseContractToUI";

    public static final String METHOD_ConvUIToPurchaseContract = "convUIToPurchaseContract";
    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    protected PurchaseContractRepository purchaseContractDAO;

    @Autowired
    protected PurchaseContractConfigureProxy purchaseContractConfigureProxy;

    @Autowired
    protected PurchaseContractIdHelper purchaseContractIdHelper;

    @Autowired
    protected CorporateCustomerManager corporateCustomerManager;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected SplitMatItemProxy splitMatItemProxy;

    @Autowired
    protected PurchaseContractServiceUIModelExtension purchaseContractServiceUIModelExtension;

    @Autowired
    protected PurchaseContractMaterialItemServiceUIModelExtension purchaseContractMaterialItemServiceUIModelExtension;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected PurchaseContractSearchProxy purchaseContractSearchProxy;

    @Autowired
    protected IndividualCustomerManager individualCustomerManager;

    @Autowired
    protected PurchaseContractActionExecutionProxy purchaseContractActionExecutionProxy;

    @Autowired
    protected SerialIdDocumentProxy serialIdDocumentProxy;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected StandardPriorityProxy standardPriorityProxy;

    @Autowired
    protected ServiceFlowRuntimeEngine serviceFlowRuntimeEngine;

    private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

    private Map<String, Map<Integer, String>> itemStatusMapLan = new HashMap<>();

    private Map<Integer, String> nextDocTypeMap;

    private Map<Integer, String> prevDocTypeMap;

    @Autowired
    private PurchaseContractSpecifier purchaseContractSpecifier;

    public Map<Integer, String> initPriorityCode(String languageCode) throws ServiceEntityInstallationException {
        return standardPriorityProxy.getPriorityMap(languageCode);
    }

    public Map<Integer, String> initStatus(String languageCode) throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode, this.statusMapLan, PurchaseContractUIModel.class,
                IDocumentNodeFieldConstant.STATUS);
    }

    public Map<Integer, String> initItemStatus(String languageCode) throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode, this.itemStatusMapLan,
                PurchaseContractUIModel.class, IDocumentNodeFieldConstant.STATUS);
    }

    @Override
    public ServiceEntityNode newRootEntityNode(String client) throws ServiceEntityConfigureException {
        PurchaseContract purchaseContract = (PurchaseContract) super.newRootEntityNode(client);
        String purchaseContractID = purchaseContractIdHelper.genDefaultId(client);
        purchaseContract.setId(purchaseContractID);
        return purchaseContract;
    }

    public CorporateCustomer getSupplier(String baseUUID, String client) throws ServiceEntityConfigureException {
        PurchaseContract purchaseContract =
                (PurchaseContract) this.getEntityNodeByKey(baseUUID, IServiceEntityNodeFieldConstant.UUID,
                        PurchaseContract.NODENAME, client, null);
        if (purchaseContract != null) {
            PurchaseContractParty purchaseContractPartyB =
                    getPurchaseContractParty(purchaseContract.getUuid(), PurchaseContractParty.ROLE_PARTYB,
                            purchaseContract.getClient());
            if (purchaseContractPartyB == null) {
                return null;
            }
            CorporateCustomer corporateSupplier =
                    (CorporateCustomer) corporateCustomerManager.getEntityNodeByKey(purchaseContractPartyB.getRefUUID(),
                            IServiceEntityNodeFieldConstant.UUID, CorporateCustomer.NODENAME, client, null);
            return corporateSupplier;
        } else {
            return null;
        }
    }

    /**
     * Get Purchase Contract Party by base UUID and partyRole
     *
     * @param baseUUID
     * @param client
     * @return
     */
    public PurchaseContractParty getPurchaseContractParty(String baseUUID, int partyRole, String client)
            throws ServiceEntityConfigureException {
        ServiceBasicKeyStructure key1 =
                new ServiceBasicKeyStructure(baseUUID, IServiceEntityNodeFieldConstant.ROOTNODEUUID);
        ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure(partyRole, DocInvolveParty.FIELD_PARTYROLE);
        return (PurchaseContractParty) this.getEntityNodeByKeyList(ServiceCollectionsHelper.asList(key1, key2),
                PurchaseContractParty.NODENAME, client, null);
    }

    @Transactional
    public List<ServiceEntityNode> splitItemService(SplitMatItemModel splitMatItemModel,
                                                    PurchaseContractMaterialItem purchaseContractMaterialItem,
                                                    String logonUserUUID, String organizationUUID)
            throws MaterialException, PurchaseContractException, ServiceEntityConfigureException,
            SplitMatItemException {
        /*
         * [Step1] Calculate the left amount and update to existed item
         */
        SplitMatItemProxy.SplitResult splitResult =
                splitMatItemProxy.splitDefMatItemService(splitMatItemModel, purchaseContractMaterialItem);
        List<ServiceEntityNode> resultList = splitResult.getMergeResult();
        /*
         * [Step2] update into persistence
         */
        this.updateSENodeList(resultList, logonUserUUID, organizationUUID);
        return resultList;
    }

    public List<ServiceEntityNode> getEndDocItemList(PurchaseContractMaterialItem purchaseContractMaterialItem) throws DocActionException {
        return docFlowProxy
                .findEndDocMatItemListTillNext(purchaseContractMaterialItem, true);
    }

    public ServiceEntityNode getEndDocItem(PurchaseContractMaterialItem purchaseContractMaterialItem) throws DocActionException {
        List<ServiceEntityNode> allNextMatItemList = getEndDocItemList(purchaseContractMaterialItem);
        if(!ServiceCollectionsHelper.checkNullList(allNextMatItemList)){
            return allNextMatItemList.get(0);
        }
        return null;
    }

    @Transactional
    public void newRegProdItemWrapper(RegisteredProductBatchGenRequest registeredProductBatchGenRequest,
                                                         String refTempMaterialSKUUUID, List<String> serialIdList,
                                                         Organization purchaseOrganization,
                                                         SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, RegisteredProductException, PurchaseContractException {
        PurchaseContract purchaseContract = (PurchaseContract) getEntityNodeByUUID(
                        registeredProductBatchGenRequest.getBaseUUID(),
                        PurchaseContract.NODENAME, serialLogonInfo.getClient());
        PurchaseContractParty purchaseContractPartyB =
                getPurchaseContractParty(
                        purchaseContract.getUuid(), PurchaseContractParty.ROLE_PARTYB, purchaseContract.getClient());
        CorporateCustomer corporateSupplier = getSupplier(purchaseContract.getUuid(), serialLogonInfo.getClient());
        IndividualCustomer supplierContact = (IndividualCustomer) individualCustomerManager.getEntityNodeByKey(
                purchaseContractPartyB.getRefContactUUID(), IServiceEntityNodeFieldConstant.UUID,
                CorporateCustomer.NODENAME, serialLogonInfo.getClient(), null);
        RegisteredProductManager.RegisteredProductInvolvePartyMatrix registeredProductInvolvePartyMatrix = new RegisteredProductManager.RegisteredProductInvolvePartyMatrix();
        registeredProductInvolvePartyMatrix.purchaseOrganization(purchaseOrganization).corporateSupplier(corporateSupplier).supplierContact(supplierContact);
        serialIdDocumentProxy.newRegProductDocMatItem(purchaseContract, refTempMaterialSKUUUID, serialIdList,
                purchaseContractSpecifier, registeredProductInvolvePartyMatrix, (documentContent, docMatItemNode, offset)
                        -> initialCopyPurchaseContractToMaterialItem(purchaseContract, purchaseContractPartyB,
                        (PurchaseContractMaterialItem) docMatItemNode, offset), serialLogonInfo);
    }

    public void convPurchaseContractToUI(PurchaseContract purchaseContract,
                                         PurchaseContractUIModel purchaseContractUIModel)
            throws ServiceEntityInstallationException {
        convPurchaseContractToUI(purchaseContract, purchaseContractUIModel, null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     */
    public void convPurchaseContractToUI(PurchaseContract purchaseContract,
                                         PurchaseContractUIModel purchaseContractUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        if (purchaseContract != null) {
            docFlowProxy.convDocumentToUI(purchaseContract, purchaseContractUIModel, logonInfo);
            purchaseContractUIModel.setNote(purchaseContract.getNote());
            purchaseContractUIModel.setStatus(purchaseContract.getStatus());
            purchaseContractUIModel.setProductionBatchNumber(purchaseContract.getProductionBatchNumber());
            purchaseContractUIModel.setPurchaseBatchNumber(purchaseContract.getPurchaseBatchNumber());
            if (logonInfo != null) {
                Map<Integer, String> statusMap = this.initStatus(logonInfo.getLanguageCode());
                purchaseContractUIModel.setStatusValue(statusMap.get(purchaseContract.getStatus()));
                Map<Integer, String> priorityCodMap = initPriorityCode(logonInfo.getLanguageCode());
                purchaseContractUIModel.setPriorityCodeValue(priorityCodMap.get(purchaseContract.getPriorityCode()));
            }
            purchaseContractUIModel.setPriorityCode(purchaseContract.getPriorityCode());
            purchaseContractUIModel.setCurrencyCode(purchaseContract.getCurrencyCode());
            if (purchaseContract.getCreatedTime() != null) {
                purchaseContractUIModel.setCreatedDate(
                        DefaultDateFormatConstant.DATE_FORMAT.format(purchaseContract.getCreatedTime()));
            }
            if (purchaseContract.getSignDate() != null) {
                purchaseContractUIModel.setSignDate(
                        DefaultDateFormatConstant.DATE_FORMAT.format(purchaseContract.getSignDate()));
            }
            if (purchaseContract.getRequireExecutionDate() != null) {
                purchaseContractUIModel.setRequireExecutionDate(
                        DefaultDateFormatConstant.DATE_FORMAT.format(purchaseContract.getRequireExecutionDate()));
            }
            purchaseContractUIModel.setContractDetails(purchaseContract.getContractDetails());
            purchaseContractUIModel.setGrossPrice(purchaseContract.getGrossPrice());
            purchaseContractUIModel.setGrossPriceDisplay(purchaseContract.getGrossPriceDisplay());
            purchaseContractUIModel.setRefFinAccountUUID(purchaseContract.getRefFinAccountUUID());
        }
    }

    /**
     * [Internal method] Convert from UI model to se model:purchaseContract
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToPurchaseContract(PurchaseContractUIModel purchaseContractUIModel, PurchaseContract rawEntity) {
        docFlowProxy.convUIToDocument(purchaseContractUIModel, rawEntity);
        rawEntity.setNote(purchaseContractUIModel.getNote());
        rawEntity.setCurrencyCode(purchaseContractUIModel.getCurrencyCode());
        rawEntity.setPurchaseBatchNumber(purchaseContractUIModel.getPurchaseBatchNumber());
        rawEntity.setProductionBatchNumber(purchaseContractUIModel.getProductionBatchNumber());
        if (!ServiceEntityStringHelper.checkNullString(purchaseContractUIModel.getSignDate())) {
            try {
                rawEntity.setSignDate(DefaultDateFormatConstant.DATE_FORMAT.parse(purchaseContractUIModel.getSignDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } catch (ParseException e) {
                // do nothing
            }
        }
        if (!ServiceEntityStringHelper.checkNullString(purchaseContractUIModel.getRequireExecutionDate())) {
            try {
                rawEntity.setRequireExecutionDate(DefaultDateFormatConstant.DATE_FORMAT.parse(purchaseContractUIModel.getRequireExecutionDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } catch (ParseException e) {
                // do nothing
            }
        }
        rawEntity.setPriorityCode(purchaseContractUIModel.getPriorityCode());
        rawEntity.setGrossPrice(purchaseContractUIModel.getGrossPrice());
        rawEntity.setGrossPriceDisplay(purchaseContractUIModel.getGrossPriceDisplay());
        rawEntity.setContractDetails(purchaseContractUIModel.getContractDetails());
    }

    /**
     * Fetch the Purchase Contract Material item instance from the down stream item
     *
     * @param docMatItemNode
     * @return
     */
    public PurchaseContractMaterialItem getPrevEndPurchaseItem(DocMatItemNode docMatItemNode) throws DocActionException {
        ServiceEntityNode rawPrevMatItem = docFlowProxy.findTargetDocMatItemTillPrev(docMatItemNode, ServiceCollectionsHelper.asList(IServiceModelConstants.PurchaseContract));
        if (rawPrevMatItem instanceof PurchaseContractMaterialItem){
            return (PurchaseContractMaterialItem) rawPrevMatItem;
        }
        return null;
    }

    public static class PurchaseContractPartyItemPair {

        private PurchaseContractParty purchaseContractParty;

        private PurchaseContractMaterialItem purchaseContractMaterialItem;

        public PurchaseContractPartyItemPair() {
        }

        public PurchaseContractPartyItemPair(PurchaseContractParty purchaseContractParty,
                                             PurchaseContractMaterialItem purchaseContractMaterialItem) {
            this.purchaseContractParty = purchaseContractParty;
            this.purchaseContractMaterialItem = purchaseContractMaterialItem;
        }

        public PurchaseContractParty getPurchaseContractParty() {
            return purchaseContractParty;
        }

        public void setPurchaseContractParty(PurchaseContractParty purchaseContractParty) {
            this.purchaseContractParty = purchaseContractParty;
        }

        public PurchaseContractMaterialItem getPurchaseContractMaterialItem() {
            return purchaseContractMaterialItem;
        }

        public void setPurchaseContractMaterialItem(PurchaseContractMaterialItem purchaseContractMaterialItem) {
            this.purchaseContractMaterialItem = purchaseContractMaterialItem;
        }
    }

    /**
     * Fetch the Purchase Contract Material item instance from the down stream item
     *
     * @param docMatItemNode
     * @return
     */
    public PurchaseContractPartyItemPair getPrevEndSupplier(DocMatItemNode docMatItemNode)
            throws ServiceEntityConfigureException, DocActionException {
        PurchaseContractMaterialItem purchaseContractMaterialItem = getPrevEndPurchaseItem(docMatItemNode);
        if (purchaseContractMaterialItem == null) {
            return null;
        }
        PurchaseContractParty purchaseContractParty =
                getPurchaseContractParty(purchaseContractMaterialItem.getRootNodeUUID(),
                        PurchaseContractParty.ROLE_PARTYB, docMatItemNode.getClient());
        return new PurchaseContractPartyItemPair(purchaseContractParty, purchaseContractMaterialItem);
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, purchaseContractDAO));
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(purchaseContractConfigureProxy);
    }

    /**
     * Logic to new instance of Purchase Contract Material Item, from baseUUID
     * and Material SKU UUID
     *
     * @param baseUUID
     * @throws ServiceEntityConfigureException
     */
    public PurchaseContractMaterialItem newItemFromMaterialSKU(String baseUUID,
                                                               String client)
            throws ServiceEntityConfigureException {
        PurchaseContract purchaseContract = (PurchaseContract) this.getEntityNodeByUUID(baseUUID,
                PurchaseContract.NODENAME, client);
        PurchaseContractParty purchaseContractPartyB = getPurchaseContractParty(purchaseContract.getUuid(),
                PurchaseContractParty.ROLE_PARTYB, client);
        return newItemFromMaterialSKU(purchaseContract, purchaseContractPartyB, null, 0);
    }

    /**
     * Logic to new instance of Purchase Contract Material Item, from baseUUID
     * and Material SKU UUID
     *
     * @param purchaseContract
     * @throws ServiceEntityConfigureException
     */
    public PurchaseContractMaterialItem newItemFromMaterialSKU(PurchaseContract purchaseContract,
                                                               PurchaseContractParty purchaseContractPartyB,
                                                               MaterialStockKeepUnit materialStockKeepUnit, int offset)
            throws ServiceEntityConfigureException {
        PurchaseContractMaterialItem purchaseContractMaterialItem =
                (PurchaseContractMaterialItem) newEntityNode(purchaseContract, PurchaseContractMaterialItem.NODENAME);
        initialCopyPurchaseContractToMaterialItem(purchaseContract, purchaseContractPartyB,
                purchaseContractMaterialItem, offset);
        if (materialStockKeepUnit != null) {
            purchaseContractMaterialItem.setUnitPrice(materialStockKeepUnit.getUnitCost());
        }
        return purchaseContractMaterialItem;
    }

    /**
     * Logic to initial create purchase contract material item from sales contract
     *
     * @param purchaseContractMaterialItem
     * @param purchaseContract
     */
    public void initialCopyPurchaseContractToMaterialItem(PurchaseContract purchaseContract,
                                                          PurchaseContractParty purchaseContractPartyB,
                                                          PurchaseContractMaterialItem purchaseContractMaterialItem,
                                                          int offset) {
        if (purchaseContractMaterialItem != null && purchaseContract != null) {
            // Logic to set initial item id
            purchaseContractMaterialItem.setPurchaseBatchNumber(purchaseContract.getPurchaseBatchNumber());
            purchaseContractMaterialItem.setProductionBatchNumber(purchaseContract.getProductionBatchNumber());
            purchaseContractMaterialItem.setRequireShippingTime(purchaseContract.getRequireExecutionDate() != null ? purchaseContract.getRequireExecutionDate().atStartOfDay() : null);
            purchaseContractMaterialItem.setCurrencyCode(purchaseContract.getCurrencyCode());
        }
    }

    public ServiceDocumentExtendUIModel convPurchaseContractToDocExtUIModel(
            PurchaseContractUIModel purchaseContractUIModel, LogonInfo logonInfo) throws ServiceEntityInstallationException {
        ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
        serviceDocumentExtendUIModel.setRefUIModel(purchaseContractUIModel);
        serviceDocumentExtendUIModel.setUuid(purchaseContractUIModel.getUuid());
        serviceDocumentExtendUIModel.setParentNodeUUID(purchaseContractUIModel.getParentNodeUUID());
        serviceDocumentExtendUIModel.setRootNodeUUID(purchaseContractUIModel.getRootNodeUUID());
        serviceDocumentExtendUIModel.setId(purchaseContractUIModel.getId());
        serviceDocumentExtendUIModel.setStatus(purchaseContractUIModel.getStatus());
        serviceDocumentExtendUIModel.setStatusValue(purchaseContractUIModel.getStatusValue());
        serviceDocumentExtendUIModel.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT);
        if(logonInfo != null){
            serviceDocumentExtendUIModel
                    .setDocumentTypeValue(serviceDocumentComProxy
                            .getDocumentTypeValue(IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT,
                                    logonInfo.getLanguageCode()));
        }
        // Logic of reference Date
        String referenceDate = purchaseContractUIModel.getSignDate();
        if (ServiceEntityStringHelper.checkNullString(referenceDate)) {
            referenceDate = purchaseContractUIModel.getCreatedDate();
        }
        serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
        return serviceDocumentExtendUIModel;
    }

    public ServiceDocumentExtendUIModel convPurchaseMatItemToDocExtUIModel(
            PurchaseContractMaterialItemUIModel purchaseContractMaterialItemUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
        serviceDocumentExtendUIModel.setRefUIModel(purchaseContractMaterialItemUIModel);
        docFlowProxy.convDocMatItemUIToDocExtUIModel(purchaseContractMaterialItemUIModel, serviceDocumentExtendUIModel,
                logonInfo, IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT);
        serviceDocumentExtendUIModel.setId(purchaseContractMaterialItemUIModel.getParentDocId());
        serviceDocumentExtendUIModel.setStatus(purchaseContractMaterialItemUIModel.getParentDocStatus());
        serviceDocumentExtendUIModel.setStatusValue(purchaseContractMaterialItemUIModel.getParentDocStatusValue());
        // Logic of reference Date
        String referenceDate = purchaseContractMaterialItemUIModel.getSignDate();
        if (ServiceEntityStringHelper.checkNullString(referenceDate)) {
            referenceDate = purchaseContractMaterialItemUIModel.getCreatedDate();
        }
        serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
        return serviceDocumentExtendUIModel;
    }

    @Override
    public ServiceDocumentExtendUIModel convToDocumentExtendUIModel(ServiceEntityNode seNode, LogonInfo logonInfo) {
        if (seNode == null) {
            return null;
        }
        if (ServiceEntityNode.NODENAME_ROOT.equals(seNode.getNodeName())) {
            PurchaseContract purchaseContract = (PurchaseContract) seNode;
            try {
                PurchaseContractUIModel purchaseContractUIModel =
                        (PurchaseContractUIModel) genUIModelFromUIModelExtension(PurchaseContractUIModel.class,
                                purchaseContractServiceUIModelExtension.genUIModelExtensionUnion().get(0),
                                purchaseContract, logonInfo, null);
                ServiceDocumentExtendUIModel serviceDocumentExtendUIModel =
                        convPurchaseContractToDocExtUIModel(purchaseContractUIModel, logonInfo);
                return serviceDocumentExtendUIModel;
            } catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, PurchaseContract.SENAME));
            }
        }
        if (PurchaseContractMaterialItem.NODENAME.equals(seNode.getNodeName())) {
            PurchaseContractMaterialItem purchaseContractMaterialItem = (PurchaseContractMaterialItem) seNode;
            try {
                PurchaseContractMaterialItemUIModel purchaseContractMaterialItemUIModel =
                        (PurchaseContractMaterialItemUIModel) genUIModelFromUIModelExtension(
                                PurchaseContractMaterialItemUIModel.class,
                                purchaseContractMaterialItemServiceUIModelExtension.genUIModelExtensionUnion().get(0),
                                purchaseContractMaterialItem, logonInfo, null);
                ServiceDocumentExtendUIModel serviceDocumentExtendUIModel =
                        convPurchaseMatItemToDocExtUIModel(purchaseContractMaterialItemUIModel, logonInfo);
                return serviceDocumentExtendUIModel;
            } catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
                logger.error(
                        ServiceEntityStringHelper.genDefaultErrorMessage(e, PurchaseContractMaterialItem.NODENAME));
            }
        }
        return null;
    }

    @Override
    public String getAuthorizationResource() {
        return IServiceModelConstants.PurchaseContract;
    }

    @Override
    public ServiceSearchProxy getSearchProxy() {
        return purchaseContractSearchProxy;
    }

    public boolean checkBlockExecutionByDocflow(int actionCode, String uuid, ServiceJSONRequest serviceJSONRequest,
                                                SerialLogonInfo serialLogonInfo) {
        if (actionCode == PurchaseContractActionNode.DOC_ACTION_APPROVE) {
            return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(
                    IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT, uuid, serviceJSONRequest, serialLogonInfo,
                    actionCode);
        }
        if (actionCode == PurchaseContractActionNode.DOC_ACTION_REJECT_APPROVE) {
            return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(
                    IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT, uuid, serviceJSONRequest, serialLogonInfo,
                    actionCode);
        }
        if (actionCode == PurchaseContractActionNode.DOC_ACTION_REVOKE_SUBMIT) {
            serviceFlowRuntimeEngine.clearInvolveProcessIns(IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT, uuid);
            return true;
        }
        return true;
    }

    public void submitFlow(PurchaseContractServiceUIModel purchaseContractServiceUIModel,
                           SerialLogonInfo serialLogonInfo) throws ServiceFlowRuntimeException {
        String uuid = purchaseContractServiceUIModel.getPurchaseContractUIModel().getUuid();
        ServiceFlowRuntimeEngine.ServiceFlowInputPara serviceFlowInputPara =
                new ServiceFlowRuntimeEngine.ServiceFlowInputPara(purchaseContractServiceUIModel,
                        IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT, uuid,
                        PurchaseContractActionNode.DOC_ACTION_APPROVE, serialLogonInfo);
        serviceFlowRuntimeEngine.submitFlow(serviceFlowInputPara);
    }

    @Override
    public void exeFlowActionEnd(int documentType, String uuid, int actionCode, ServiceJSONRequest serviceJSONRequest,
                                 SerialLogonInfo serialLogonInfo) {
        try {
            PurchaseContract purchaseContract =
                    (PurchaseContract) getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID,
                            PurchaseContract.NODENAME, serialLogonInfo.getClient(), null);
            PurchaseContractServiceModel purchaseContractServiceModel =
                    (PurchaseContractServiceModel) loadServiceModule(PurchaseContractServiceModel.class,
                            purchaseContract, purchaseContractServiceUIModelExtension);
            purchaseContractServiceModel.setServiceJSONRequest(serviceJSONRequest);
            purchaseContractActionExecutionProxy.executeActionCore(purchaseContractServiceModel, actionCode,
                    serialLogonInfo);
        } catch (ServiceEntityConfigureException | ServiceModuleProxyException | DocActionException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, String.valueOf(actionCode)));
        }
    }

}
