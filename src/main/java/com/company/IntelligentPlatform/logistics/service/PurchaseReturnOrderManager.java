package com.company.IntelligentPlatform.logistics.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.finance.service.SystemResourceFinanceAccountProxy;
import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.company.IntelligentPlatform.logistics.repository.PurchaseReturnOrderRepository;

import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.logistics.model.PurchaseReturnOrderConfigureProxy;
import com.company.IntelligentPlatform.common.service.ServiceFlowRuntimeEngine;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.util.*;
import java.util.stream.Collectors;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Logic Manager CLASS FOR Service Entity [PurchaseReturnOrder]
 *
 * @author
 * @date Mon Apr 05 11:56:35 CST 2021
 * <p>
 * This class is generated automatically by platform automation register tool
 */
@Service
@Transactional
public class PurchaseReturnOrderManager extends ServiceEntityManager {

    public static final String METHOD_ConvPurchaseReturnOrderToUI = "convPurchaseReturnOrderToUI";

    public static final String METHOD_ConvUIToPurchaseReturnOrder = "convUIToPurchaseReturnOrder";
    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    protected PurchaseReturnOrderRepository purchaseReturnOrderDAO;

    @Autowired
    protected PurchaseReturnOrderConfigureProxy purchaseReturnOrderConfigureProxy;

    @Autowired
    protected PurchaseReturnOrderIdHelper purchaseReturnOrderIdHelper;

    @Autowired
    protected SystemResourceFinanceAccountProxy systemResourceFinanceAccountProxy;

    @Autowired
    protected StandardPriorityProxy standardPriorityProxy;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected PurchaseReturnMaterialItemServiceUIModelExtension purchaseReturnMaterialItemServiceUIModelExtension;

    @Autowired
    protected PurchaseReturnOrderServiceUIModelExtension purchaseReturnOrderServiceUIModelExtension;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected PurchaseReturnOrderSearchProxy purchaseReturnOrderSearchProxy;

    @Autowired
    protected ServiceUIMetaProxy serviceUIMetaProxy;

    @Autowired
    protected ServiceFlowRuntimeEngine serviceFlowRuntimeEngine;

    @Autowired
    protected PurchaseReturnOrderActionExecutionProxy purchaseReturnOrderActionExecutionProxy;

    protected Logger logger = LoggerFactory.getLogger(PurchaseReturnOrderManager.class);

    private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

    private Map<String, Map<Integer, String>> itemStatusMapLan = new HashMap<>();

    public Map<Integer, String> initPriorityCode(String languageCode)
            throws ServiceEntityInstallationException {
        return standardPriorityProxy.getPriorityMap(languageCode);
    }

    public Map<Integer, String> initStatus(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.statusMapLan, PurchaseReturnOrderUIModel.class, IDocumentNodeFieldConstant.STATUS);
    }

    public Map<Integer, String> initItemStatus(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.itemStatusMapLan, PurchaseReturnOrderUIModel.class,
                IDocumentNodeFieldConstant.STATUS);
    }

    public PurchaseReturnOrderManager() {
        super.seConfigureProxy = new PurchaseReturnOrderConfigureProxy();
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, purchaseReturnOrderDAO));
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(purchaseReturnOrderConfigureProxy);
    }

    @Override
    public ServiceEntityNode newRootEntityNode(String client)
            throws ServiceEntityConfigureException {
        PurchaseReturnOrder purchaseReturnOrder = (PurchaseReturnOrder) super
                .newRootEntityNode(client);
        String purchaseReturnOrderId = purchaseReturnOrderIdHelper
                .genDefaultId(client);
        purchaseReturnOrder.setId(purchaseReturnOrderId);
        return purchaseReturnOrder;
    }

    public void preUpdateServiceModule(PurchaseReturnOrderServiceModel purchaseReturnOrderServiceModel) {
        setGrossData(purchaseReturnOrderServiceModel);
    }

    public void setGrossData(PurchaseReturnOrderServiceModel purchaseReturnOrderServiceModel) {
        List<PurchaseReturnMaterialItemServiceModel> purchaseReturnMaterialItemServiceModelList =
                purchaseReturnOrderServiceModel.getPurchaseReturnMaterialItemList();
        if (ServiceCollectionsHelper.checkNullList(purchaseReturnMaterialItemServiceModelList)) {
            return;
        }
        PurchaseReturnOrder purchaseReturnOrder = purchaseReturnOrderServiceModel.getPurchaseReturnOrder();
        List<ServiceEntityNode> purchaseReturnMaterialItemList =
                purchaseReturnMaterialItemServiceModelList.stream().map(PurchaseReturnMaterialItemServiceModel::getPurchaseReturnMaterialItem).collect(Collectors.toList());
        DocFlowProxy.GrossPricePair grossPricePair = DocFlowProxy.calculateGrossPrice(purchaseReturnMaterialItemList);
        purchaseReturnOrder.setGrossPrice(grossPricePair.getGrossPrice());
        purchaseReturnOrder.setGrossPriceDisplay(grossPricePair.getGrossPriceDisplay());
    }

    public void convPurchaseReturnOrderToUI(PurchaseReturnOrder purchaseReturnOrder,
                                            PurchaseReturnOrderUIModel purchaseReturnOrderUIModel)
            throws ServiceEntityInstallationException {
        convPurchaseReturnOrderToUI(purchaseReturnOrder, purchaseReturnOrderUIModel,
                null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convPurchaseReturnOrderToUI(PurchaseReturnOrder purchaseReturnOrder,
                                            PurchaseReturnOrderUIModel purchaseReturnOrderUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        if (purchaseReturnOrder != null) {
            docFlowProxy.convDocumentToUI(purchaseReturnOrder,
                    purchaseReturnOrderUIModel, logonInfo);
            purchaseReturnOrderUIModel.setId(purchaseReturnOrder.getId());
            purchaseReturnOrderUIModel.setName(purchaseReturnOrder.getName());
            purchaseReturnOrderUIModel.setPriorityCode(purchaseReturnOrder
                    .getPriorityCode());
            purchaseReturnOrderUIModel.setProductionBatchNumber(purchaseReturnOrder.getProductionBatchNumber());
            if (logonInfo != null) {
                Map<Integer, String> priorityCodeMap = initPriorityCode(logonInfo
                        .getLanguageCode());
                purchaseReturnOrderUIModel.setPriorityCodeValue(priorityCodeMap
                        .get(purchaseReturnOrder.getPriorityCode()));
            }
            purchaseReturnOrderUIModel.setStatus(purchaseReturnOrder.getStatus());
            if (logonInfo != null) {
                Map<Integer, String> statusMap = initStatus(logonInfo
                        .getLanguageCode());
                purchaseReturnOrderUIModel.setStatusValue(statusMap
                        .get(purchaseReturnOrder.getStatus()));
            }
            purchaseReturnOrderUIModel.setTaxRate(purchaseReturnOrder.getTaxRate());
            purchaseReturnOrderUIModel.setNote(purchaseReturnOrder.getNote());
            purchaseReturnOrderUIModel.setGrossPrice(purchaseReturnOrder
                    .getGrossPrice());
            purchaseReturnOrderUIModel.setGrossPriceDisplay(purchaseReturnOrder
                    .getGrossPriceDisplay());
        }
    }

    /**
     * [Internal method] Convert from UI model to se model:purchaseReturnOrder
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToPurchaseReturnOrder(
            PurchaseReturnOrderUIModel purchaseReturnOrderUIModel,
            PurchaseReturnOrder rawEntity) {
        docFlowProxy.convUIToDocument(purchaseReturnOrderUIModel, rawEntity);
        rawEntity.setPriorityCode(purchaseReturnOrderUIModel.getPriorityCode());
        rawEntity.setTaxRate(purchaseReturnOrderUIModel.getTaxRate());
        rawEntity.setNote(purchaseReturnOrderUIModel.getNote());
        rawEntity.setGrossPrice(purchaseReturnOrderUIModel.getGrossPrice());
        rawEntity.setGrossPriceDisplay(purchaseReturnOrderUIModel.getGrossPriceDisplay());
    }

    public ServiceDocumentExtendUIModel convPurchaseReturnOrderToDocExtUIModel(
            PurchaseReturnOrderUIModel purchaseReturnOrderUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
        serviceDocumentExtendUIModel.setRefUIModel(purchaseReturnOrderUIModel);
        serviceDocumentExtendUIModel.setUuid(purchaseReturnOrderUIModel.getUuid());
        serviceDocumentExtendUIModel.setParentNodeUUID(purchaseReturnOrderUIModel
                .getParentNodeUUID());
        serviceDocumentExtendUIModel.setRootNodeUUID(purchaseReturnOrderUIModel
                .getRootNodeUUID());
        serviceDocumentExtendUIModel.setId(purchaseReturnOrderUIModel.getId());
        serviceDocumentExtendUIModel.setStatus(purchaseReturnOrderUIModel
                .getStatus());
        serviceDocumentExtendUIModel.setStatusValue(purchaseReturnOrderUIModel
                .getStatusValue());
        serviceDocumentExtendUIModel
                .setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER);
        if(logonInfo != null){
            serviceDocumentExtendUIModel
                    .setDocumentTypeValue(serviceDocumentComProxy
                            .getDocumentTypeValue(IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER,
                                    logonInfo.getLanguageCode()));
        }
        // Logic of reference Date
        String referenceDate = purchaseReturnOrderUIModel.getCreatedDate();
        if (ServiceEntityStringHelper.checkNullString(referenceDate)) {
            referenceDate = purchaseReturnOrderUIModel.getCreatedDate();
        }
        serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
        return serviceDocumentExtendUIModel;
    }

    public ServiceDocumentExtendUIModel convPurchaseReturnMatItemToDocExtUIModel(
            PurchaseReturnMaterialItemUIModel purchaseReturnMaterialItemUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
        serviceDocumentExtendUIModel
                .setRefUIModel(purchaseReturnMaterialItemUIModel);
        docFlowProxy.convDocMatItemUIToDocExtUIModel(purchaseReturnMaterialItemUIModel,
                serviceDocumentExtendUIModel, logonInfo,
                IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER);
        serviceDocumentExtendUIModel.setId(purchaseReturnMaterialItemUIModel
                .getParentDocId());
        serviceDocumentExtendUIModel
                .setStatus(purchaseReturnMaterialItemUIModel.getParentDocStatus());
        serviceDocumentExtendUIModel
                .setStatusValue(purchaseReturnMaterialItemUIModel
                        .getParentDocStatusValue());
        // Logic of reference Date
        String referenceDate = purchaseReturnMaterialItemUIModel
                .getCreatedDate();
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
            PurchaseReturnOrder purchaseReturnOrder = (PurchaseReturnOrder) seNode;
            try {
                PurchaseReturnOrderUIModel purchaseReturnOrderUIModel =
                        (PurchaseReturnOrderUIModel) genUIModelFromUIModelExtension(
                                PurchaseReturnOrderUIModel.class,
                                purchaseReturnOrderServiceUIModelExtension
                                        .genUIModelExtensionUnion().get(0),
                                purchaseReturnOrder, logonInfo, null);
                return convPurchaseReturnOrderToDocExtUIModel(purchaseReturnOrderUIModel, logonInfo);
            } catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
                        e, PurchaseReturnOrder.SENAME));
            }
        }
        if (PurchaseReturnMaterialItem.NODENAME.equals(seNode.getNodeName())) {
            PurchaseReturnMaterialItem purchaseReturnMaterialItem = (PurchaseReturnMaterialItem) seNode;
            try {
                PurchaseReturnMaterialItemUIModel purchaseReturnMaterialItemUIModel =
                        (PurchaseReturnMaterialItemUIModel) genUIModelFromUIModelExtension(
                                PurchaseReturnMaterialItemUIModel.class,
                                purchaseReturnMaterialItemServiceUIModelExtension
                                        .genUIModelExtensionUnion().get(0),
                                purchaseReturnMaterialItem, logonInfo, null);
                return convPurchaseReturnMatItemToDocExtUIModel(purchaseReturnMaterialItemUIModel, logonInfo);
            } catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
                        e, PurchaseReturnMaterialItem.NODENAME));
            }
        }
        return null;
    }

    @Override
    public String getAuthorizationResource() {
        return IServiceModelConstants.PurchaseReturnOrder;
    }

    @Override
    public ServiceSearchProxy getSearchProxy() {
        return purchaseReturnOrderSearchProxy;
    }

    public boolean checkBlockExecutionByDocflow(int actionCode, String uuid, ServiceJSONRequest serviceJSONRequest,
                                                SerialLogonInfo serialLogonInfo){
        if(actionCode == PurchaseReturnOrderActionNode.DOC_ACTION_APPROVE){
            return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(
                    IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER, uuid, serviceJSONRequest, serialLogonInfo,
                    actionCode);
        }
        if(actionCode == PurchaseReturnOrderActionNode.DOC_ACTION_REJECT_APPROVE){
            return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(
                    IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER, uuid,serviceJSONRequest, serialLogonInfo,
                    actionCode);
        }
        if(actionCode == PurchaseReturnOrderActionNode.DOC_ACTION_REVOKE_SUBMIT){
            serviceFlowRuntimeEngine.clearInvolveProcessIns(
                    IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER,uuid);
            return true;
        }
        return true;
    }

    @Override
    public void exeFlowActionEnd(int documentType, String uuid, int actionCode, ServiceJSONRequest serviceJSONRequest
            , SerialLogonInfo serialLogonInfo){
        try {
            PurchaseReturnOrder purchaseReturnOrder = (PurchaseReturnOrder) getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID,
                    PurchaseReturnOrder.NODENAME, serialLogonInfo.getClient(), null);
            PurchaseReturnOrderServiceModel purchaseReturnOrderServiceModel = (PurchaseReturnOrderServiceModel) loadServiceModule(PurchaseReturnOrderServiceModel.class,
                    purchaseReturnOrder, purchaseReturnOrderServiceUIModelExtension);
            purchaseReturnOrderServiceModel.setServiceJSONRequest(serviceJSONRequest);
            if(actionCode == SystemDefDocActionCodeProxy.DOC_ACTION_APPROVE){
                purchaseReturnOrderActionExecutionProxy.executeActionCore(purchaseReturnOrderServiceModel, actionCode,
                        serialLogonInfo);
            }
            if(actionCode == SystemDefDocActionCodeProxy.DOC_ACTION_REJECT_APPROVE){
                purchaseReturnOrderActionExecutionProxy.executeActionCore(purchaseReturnOrderServiceModel, actionCode,
                        serialLogonInfo);
            }
        } catch (ServiceEntityConfigureException | ServiceModuleProxyException | DocActionException e) {
           logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "exeFlowActionEnd"));
        }
    }
}
