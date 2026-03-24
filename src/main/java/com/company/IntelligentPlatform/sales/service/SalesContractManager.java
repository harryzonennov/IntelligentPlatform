package com.company.IntelligentPlatform.sales.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryWarehouseItemManager;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.sales.dto.*;
import com.company.IntelligentPlatform.sales.repository.SalesContractRepository;
import com.company.IntelligentPlatform.sales.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreItemException;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreItemManager;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceFlowRuntimeEngine;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.ServiceDocumentSettingManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
@Transactional
public class SalesContractManager extends ServiceEntityManager {

    public static final String METHOD_ConvSalesContractToUI = "convSalesContractToUI";

    public static final String METHOD_ConvUIToSalesContract = "convUIToSalesContract";

    @Autowired
    protected WarehouseStoreItemManager warehouseStoreItemManager;

    @Autowired
    protected SalesContractRepository salesContractDAO;

    @Autowired
    protected SalesContractConfigureProxy salesContractConfigureProxy;

    @Autowired
    protected ServiceItemIdGenerator serviceItemIdGenerator;

    @Autowired
    protected SalesContractIdHelper salesContractIdHelper;

    @Autowired
    protected ServiceDocumentSettingManager serviceDocumentSettingManager;

    @Autowired
    protected WarehouseManager warehouseManager;

    @Autowired
    protected WarehouseStoreManager warehouseStoreManager;

    @Autowired
    protected OutboundDeliveryWarehouseItemManager outboundDeliveryWarehouseItemManager;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected SalesContractServiceUIModelExtension salesContractServiceUIModelExtension;

    @Autowired
    protected SalesContractMaterialItemServiceUIModelExtension salesContractMaterialItemServiceUIModelExtension;

    @Autowired
    protected SalesContractSearchProxy salesContractSearchProxy;

    @Autowired
    protected StandardPriorityProxy standardPriorityProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected DocActionNodeProxy docActionNodeProxy;

    @Autowired
    protected SalesContractActionExecutionProxy salesContractActionExecutionProxy;

    @Autowired
    protected ServiceFlowRuntimeEngine serviceFlowRuntimeEngine;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

    public Map<Integer, String> initPriorityCode(String languageCode)
            throws ServiceEntityInstallationException {
        return standardPriorityProxy.getPriorityMap(languageCode);
    }

    public Map<Integer, String> initStatus(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.statusMapLan, SalesContractUIModel.class, IDocumentNodeFieldConstant.STATUS);
    }

    @Override
    public ServiceEntityNode newRootEntityNode(String client)
            throws ServiceEntityConfigureException {
        SalesContract salesContract = (SalesContract) super
                .newRootEntityNode(client);
        String salesContractId = salesContractIdHelper.genDefaultId(client);
        salesContract.setId(salesContractId);
        return salesContract;
    }

    /**
     * Get Sales Contract Party by base UUID and partyRole
     *
     * @param baseUUID
     * @param client
     * @return
     */
    public SalesContractParty getSalesContractParty(String baseUUID, int partyRole, String client) throws ServiceEntityConfigureException {
        ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure(baseUUID,
                IServiceEntityNodeFieldConstant.ROOTNODEUUID);
        ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure(partyRole,
                DocInvolveParty.FIELD_PARTYROLE);
        return (SalesContractParty) this.getEntityNodeByKeyList(ServiceCollectionsHelper.asList(key1,
                key2), SalesContractParty.NODENAME, client, null);
    }

    public ServiceModule quickCreateServiceModel(
            SalesContract salesContract,
            List<ServiceEntityNode> salesContractMatItemList) {
        SalesContractServiceModel salesContractServiceModel = new SalesContractServiceModel();
        salesContractServiceModel
                .setSalesContract(salesContract);
        List<SalesContractMaterialItemServiceModel> salesContractMaterialItemServiceModelList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(salesContractMatItemList)) {
            salesContractMatItemList
                    .forEach(rawSENode -> {
                        SalesContractMaterialItemServiceModel salesContractMaterialItemServiceModel = new SalesContractMaterialItemServiceModel();
                        salesContractMaterialItemServiceModel
                                .setSalesContractMaterialItem((SalesContractMaterialItem) rawSENode);
                        salesContractMaterialItemServiceModelList
                                .add(salesContractMaterialItemServiceModel);
                    });
        }
        salesContractServiceModel
                .setSalesContractMaterialItemList(salesContractMaterialItemServiceModelList);
        return salesContractServiceModel;
    }


    /**
     * Logic to get all possible warehouse store item list from sales contract
     *
     * @return
     */
    public List<ServiceEntityNode> getWarehouseStoreItemList(SalesContract salesContract,
                                                             List<ServiceEntityNode> salesContractMaterialItemList)
            throws ServiceEntityConfigureException {
        /*
         * [Step 1] Get Warehouse UUID list, get raw warehouse storeItem UUIDList
         */
        if(ServiceCollectionsHelper.checkNullList(salesContractMaterialItemList)){
            return null;
        }
        if(salesContract == null){
            salesContract = (SalesContract) this.getEntityNodeByUUID(salesContractMaterialItemList.get(0).getUuid(),
                    SalesContract.NODENAME, salesContractMaterialItemList.get(0).getClient());
        }
        List<String> warehouseUUIDList = getWarehouseUUIDList(salesContract);
        /*
         * [Step 2] Get Warehouse UUID list, get raw warehouse storeItem UUIDList
         */
        List<String> refMaterialSKUUUIDList =
                salesContractMaterialItemList.stream().map(serviceEntityNode -> {
                    SalesContractMaterialItem salesContractMaterialItem = (SalesContractMaterialItem) serviceEntityNode;
                    return salesContractMaterialItem.getRefMaterialSKUUUID();
                }).collect(Collectors.toList());
        List<ServiceEntityNode> rawWarehouseStoreItemList = null;
        try {
            rawWarehouseStoreItemList = warehouseStoreManager.getStoreItemList(refMaterialSKUUUIDList,
                    warehouseUUIDList, true);
        } catch (ServiceEntityConfigureException | MaterialException e) {
            throw new WarehouseStoreItemException(WarehouseStoreItemException.PARA_SYSTEM_WRONG, e.getMessage());
        }
        return rawWarehouseStoreItemList;
    }

    /**
     * Logic to get all possible outbound warehouse list from sales contract
     *
     * @param salesContract
     * @return
     */
    public List<String> getWarehouseUUIDList(SalesContract salesContract) {
        List<String> warehouseUUIDList = new ArrayList<>();
        // TODO: in the future, get warehouse list from salesContract
        try {
            warehouseUUIDList = warehouseManager.getWarehouseUUIDList(Warehouse.REFMAT_CATEGORY_NORMAL,
                    salesContract.getClient());
        } catch (ServiceEntityConfigureException | NoSuchFieldException e) {
            throw new WarehouseStoreItemException(WarehouseStoreItemException.PARA_SYSTEM_WRONG, e.getMessage());
        }
        return warehouseUUIDList;
    }

    /**
     * Update list of sales contract material list with check store status
     *
     * @param salesContract
     * @param salesContractMaterialItemList
     * @throws MaterialException
     * @throws SearchConfigureException
     */
    private void updateStoreCheckStatusBatch(SalesContract salesContract,
                                             List<ServiceEntityNode> salesContractMaterialItemList) throws WarehouseStoreItemException, MaterialException, SearchConfigureException, ServiceEntityConfigureException {
        if (ServiceCollectionsHelper.checkNullList(salesContractMaterialItemList)) {
            return;
        }
        List<ServiceEntityNode> rawWarehouseStoreItemList = getWarehouseStoreItemList(salesContract,
                salesContractMaterialItemList);
        List<String> warehouseUUIDList = getWarehouseUUIDList(salesContract);
        /*
         * [Step 3] Process of update check store status
         */
        for (ServiceEntityNode serviceEntityNode : salesContractMaterialItemList) {
            SalesContractMaterialItem salesContractMaterialItem = (SalesContractMaterialItem) serviceEntityNode;
            int storeCheckStatus =
                    outboundDeliveryWarehouseItemManager.checkStoreStatusService(salesContractMaterialItem,
                            rawWarehouseStoreItemList, Warehouse.REFMAT_CATEGORY_NORMAL, warehouseUUIDList, null,
                            true, true,
                            salesContractMaterialItem.getClient());
            salesContractMaterialItem.setStoreCheckStatus(storeCheckStatus);
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     */
    public void convSalesContractToUI(SalesContract salesContract,
                                      SalesContractUIModel salesContractUIModel)
            throws ServiceEntityInstallationException {
        convSalesContractToUI(salesContract, salesContractUIModel, null);
    }


    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     */
    public void convSalesContractToUI(SalesContract salesContract,
                                      SalesContractUIModel salesContractUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        if (salesContract != null) {
            docFlowProxy.convDocumentToUI(salesContract, salesContractUIModel, logonInfo);
            salesContractUIModel.setNote(salesContract.getNote());
            salesContractUIModel.setPriorityCode(salesContract
                    .getPriorityCode());
            if (logonInfo != null) {
                Map<Integer, String> statusMap = this.initStatus(logonInfo.getLanguageCode());
                salesContractUIModel.setStatusValue(statusMap
                        .get(salesContract.getStatus()));
                Map<Integer, String> priorityCodeMap = this.initPriorityCode(logonInfo.getLanguageCode());
                salesContractUIModel.setPriorityCodeValue(priorityCodeMap
                        .get(salesContract.getPriorityCode()));
            }
            salesContractUIModel.setCurrencyCode(salesContract
                    .getCurrencyCode());
            salesContractUIModel.setProductionBatchNumber(salesContract
                    .getProductionBatchNumber());
            if (salesContract.getSignDate() != null) {
                salesContractUIModel
                        .setSignDate(DefaultDateFormatConstant.DATE_FORMAT
                                .format(salesContract.getSignDate()));
            }
            if (salesContract.getRequireExecutionDate() != null) {
                salesContractUIModel
                        .setRequireExecutionDate(DefaultDateFormatConstant.DATE_FORMAT
                                .format(salesContract.getRequireExecutionDate()));
            }
            if (salesContract.getPlanExecutionDate() != null) {
                salesContractUIModel
                        .setPlanExecutionDate(DefaultDateFormatConstant.DATE_FORMAT
                                .format(salesContract.getPlanExecutionDate()));
            }
            salesContractUIModel.setContractDetails(salesContract
                    .getContractDetails());
            salesContractUIModel.setGrossPrice(salesContract.getGrossPrice());
            salesContractUIModel.setGrossPriceDisplay(salesContract.getGrossPriceDisplay());
            salesContractUIModel.setRefFinAccountUUID(salesContract
                    .getRefFinAccountUUID());
        }
    }

    /**
     * [Internal method] Convert from UI model to se model:salesContract
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToSalesContract(
            SalesContractUIModel salesContractUIModel, SalesContract rawEntity) {
        docFlowProxy.convUIToDocument(salesContractUIModel, rawEntity);
        rawEntity.setName(salesContractUIModel.getName());
        rawEntity.setNote(salesContractUIModel.getNote());
        rawEntity.setId(salesContractUIModel.getId());
        rawEntity.setCurrencyCode(salesContractUIModel.getCurrencyCode());
        if (!ServiceEntityStringHelper.checkNullString(salesContractUIModel
                .getSignDate())) {
            try {
                rawEntity.setSignDate(DefaultDateFormatConstant.DATE_FORMAT
                        .parse(salesContractUIModel.getSignDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
            } catch (ParseException e) {
                // do nothing
            }
        }
        if (!ServiceEntityStringHelper.checkNullString(salesContractUIModel
                .getRequireExecutionDate())) {
            try {
                rawEntity.setRequireExecutionDate(DefaultDateFormatConstant.DATE_FORMAT
                        .parse(salesContractUIModel.getRequireExecutionDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
            } catch (ParseException e) {
                // do nothing
            }
        }
        if (!ServiceEntityStringHelper.checkNullString(salesContractUIModel
                .getPlanExecutionDate())) {
            try {
                rawEntity.setPlanExecutionDate(DefaultDateFormatConstant.DATE_FORMAT
                        .parse(salesContractUIModel.getPlanExecutionDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
            } catch (ParseException e) {
                // do nothing
            }
        }
        rawEntity.setStatus(salesContractUIModel.getStatus());
        rawEntity.setPriorityCode(salesContractUIModel.getPriorityCode());
        rawEntity.setGrossPrice(salesContractUIModel.getGrossPrice());
        rawEntity.setGrossPriceDisplay(salesContractUIModel.getGrossPriceDisplay());
        rawEntity.setContractDetails(salesContractUIModel.getContractDetails());
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        // TODO-DAO: super.setServiceEntityDAO(salesContractDAO);
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(salesContractConfigureProxy);
    }

    public ServiceDocumentExtendUIModel convSalesContractToDocExtUIModel(
            SalesContractUIModel salesContractUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
        serviceDocumentExtendUIModel.setRefUIModel(salesContractUIModel);
        serviceDocumentExtendUIModel.setUuid(salesContractUIModel.getUuid());
        serviceDocumentExtendUIModel.setParentNodeUUID(salesContractUIModel
                .getParentNodeUUID());
        serviceDocumentExtendUIModel.setRootNodeUUID(salesContractUIModel
                .getRootNodeUUID());
        serviceDocumentExtendUIModel.setId(salesContractUIModel.getId());
        serviceDocumentExtendUIModel
                .setStatus(salesContractUIModel.getStatus());
        serviceDocumentExtendUIModel.setStatusValue(salesContractUIModel
                .getStatusValue());
        serviceDocumentExtendUIModel
                .setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT);
        if(logonInfo != null){
            serviceDocumentExtendUIModel
                    .setDocumentTypeValue(serviceDocumentComProxy
                            .getDocumentTypeValue(IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT,
                                    logonInfo.getLanguageCode()));
        }
        // Logic of reference Date
        String referenceDate = salesContractUIModel.getRequireExecutionDate();
        if (ServiceEntityStringHelper.checkNullString(referenceDate)) {
            referenceDate = salesContractUIModel.getSignDate();
        }
        serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
        return serviceDocumentExtendUIModel;
    }

    public ServiceDocumentExtendUIModel convSalesContractMaterialItemToDocExtUIModel(
            SalesContractMaterialItemUIModel salesContractMaterialItemUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
        docFlowProxy.convDocMatItemUIToDocExtUIModel(salesContractMaterialItemUIModel,
                serviceDocumentExtendUIModel, logonInfo,
                IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT);
        serviceDocumentExtendUIModel
                .setRefUIModel(salesContractMaterialItemUIModel);
        serviceDocumentExtendUIModel.setId(salesContractMaterialItemUIModel
                .getParentDocId());
        serviceDocumentExtendUIModel.setStatus(salesContractMaterialItemUIModel
                .getParentDocStatus());
        serviceDocumentExtendUIModel
                .setStatusValue(salesContractMaterialItemUIModel
                        .getParentDocStatusValue());
        // Logic of reference Date
        String referenceDate = salesContractMaterialItemUIModel
                .getRequireExecutionDate();
        if (!ServiceEntityStringHelper.checkNullString(salesContractMaterialItemUIModel
                .getPlanExecutionDate())) {
            referenceDate = salesContractMaterialItemUIModel.getPlanExecutionDate();
        }
        if (!ServiceEntityStringHelper.checkNullString(salesContractMaterialItemUIModel
                .getRequireExecutionDate())) {
            referenceDate = salesContractMaterialItemUIModel.getRequireExecutionDate();
        }
        if (!ServiceEntityStringHelper.checkNullString(salesContractMaterialItemUIModel
                .getUpdatedDate())) {
            referenceDate = salesContractMaterialItemUIModel.getUpdatedDate();
        }
        serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
        return serviceDocumentExtendUIModel;
    }

    @Override
    public ServiceDocumentExtendUIModel convToDocumentExtendUIModel(
            ServiceEntityNode seNode, LogonInfo logonInfo) {
        if (seNode == null) {
            return null;
        }
        if (ServiceEntityNode.NODENAME_ROOT.equals(seNode.getNodeName())) {
            SalesContract salesContract = (SalesContract) seNode;
            try {
                SalesContractUIModel salesContractUIModel = (SalesContractUIModel) genUIModelFromUIModelExtension(
                        SalesContractUIModel.class,
                        salesContractServiceUIModelExtension
                                .genUIModelExtensionUnion().get(0),
                        salesContract, logonInfo, null);
                return convSalesContractToDocExtUIModel(salesContractUIModel, logonInfo);
            } catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
                        e, SalesContract.SENAME));
            }
        }
        if (SalesContractMaterialItem.NODENAME.equals(seNode.getNodeName())) {
            SalesContractMaterialItem salesContractMaterialItem = (SalesContractMaterialItem) seNode;
            try {
                SalesContractMaterialItemUIModel salesContractMaterialItemUIModel =
                        (SalesContractMaterialItemUIModel) genUIModelFromUIModelExtension(
                                SalesContractMaterialItemUIModel.class,
                                salesContractMaterialItemServiceUIModelExtension
                                        .genUIModelExtensionUnion().get(0),
                                salesContractMaterialItem, logonInfo, null);
                return convSalesContractMaterialItemToDocExtUIModel(salesContractMaterialItemUIModel, logonInfo);
            } catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
                        e, SalesContractMaterialItem.NODENAME));
            }
        }
        return null;
    }

    @Override
    public String getAuthorizationResource() {
        return IServiceModelConstants.SalesContract;
    }

    @Override
    public ServiceSearchProxy getSearchProxy() {
        return salesContractSearchProxy;
    }

    public boolean checkBlockExecutionByDocflow(int actionCode, String uuid,
                                                ServiceJSONRequest serviceJSONRequest, SerialLogonInfo serialLogonInfo){
        if(actionCode == SalesContractActionNode.DOC_ACTION_APPROVE){
            return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(
                    IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT, uuid, serviceJSONRequest, serialLogonInfo,
                    actionCode);
        }
        if(actionCode == SalesContractActionNode.DOC_ACTION_REJECT_APPROVE){
            return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(
                    IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT, uuid,serviceJSONRequest, serialLogonInfo,
                    actionCode);
        }
        if(actionCode == SalesContractActionNode.DOC_ACTION_REVOKE_SUBMIT){
            serviceFlowRuntimeEngine.clearInvolveProcessIns(
                    IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT,uuid);
            return true;
        }
        return true;
    }

    public void submitFlow(SalesContractServiceUIModel salesContractServiceUIModel, SerialLogonInfo serialLogonInfo){
        String uuid = salesContractServiceUIModel.getSalesContractUIModel().getUuid();
        ServiceFlowRuntimeEngine.ServiceFlowInputPara serviceFlowInputPara =
                new ServiceFlowRuntimeEngine.ServiceFlowInputPara(salesContractServiceUIModel,
                        IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT, uuid,
                        SalesContractActionNode.DOC_ACTION_APPROVE, serialLogonInfo);
        serviceFlowRuntimeEngine.submitFlow(serviceFlowInputPara);
    }


    @Override
    public void exeFlowActionEnd(int documentType, String uuid, int actionCode, ServiceJSONRequest serviceJSONRequest
            ,  SerialLogonInfo serialLogonInfo){
        try {
            SalesContract salesContract = (SalesContract) getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID,
                    SalesContract.NODENAME, serialLogonInfo.getClient(), null);
            SalesContractServiceModel salesContractServiceModel = (SalesContractServiceModel) loadServiceModule(SalesContractServiceModel.class,
                    salesContract, salesContractServiceUIModelExtension);
            salesContractServiceModel.setServiceJSONRequest(serviceJSONRequest);
            if(actionCode == SystemDefDocActionCodeProxy.DOC_ACTION_APPROVE){
                salesContractActionExecutionProxy.executeActionCore(salesContractServiceModel,
                        actionCode, serialLogonInfo);
            }
            if(actionCode == SystemDefDocActionCodeProxy.DOC_ACTION_REJECT_APPROVE){
                salesContractActionExecutionProxy.executeActionCore(salesContractServiceModel,
                        actionCode, serialLogonInfo);
            }
        } catch (ServiceEntityConfigureException | ServiceModuleProxyException | DocActionException e) {
            e.printStackTrace();
        }
    }

}