package com.company.IntelligentPlatform.logistics.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.repository.PurchaseRequestRepository;
import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.model.PurchaseRequestConfigureProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceFlowRuntimeEngine;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.SystemConfigureCategoryManager;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.ServiceJSONRequest;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.ServiceFlowRuntimeException;
import com.company.IntelligentPlatform.common.model.IDocumentNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import jakarta.annotation.PostConstruct;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import java.time.ZoneId;
import java.time.LocalDate;

/**
 * Logic Manager CLASS FOR Service Entity [PurchaseRequest]
 *
 * @author Zhang,Hang
 * @date Mon Apr 05 00:45:51 CST 2021
 * <p>
 * This class is generated automatically by platform automation register tool
 */
@Service
@Transactional
public class PurchaseRequestManager extends ServiceEntityManager {

    public static final String METHOD_ConvPurchaseRequestToUI = "convPurchaseRequestToUI";

    public static final String METHOD_ConvUIToPurchaseRequest = "convUIToPurchaseRequest";
    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    protected PurchaseRequestRepository purchaseRequestDAO;

    @Autowired
    protected PurchaseRequestConfigureProxy purchaseRequestConfigureProxy;

    @Autowired
    protected PurchaseRequestIdHelper purchaseRequestIdHelper;

    @Autowired
    protected StandardPriorityProxy standardPriorityProxy;

    @Autowired
    protected PurchaseRequestSearchProxy purchaseRequestSearchProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected DocInvolvePartyProxy docInvolvePartyProxy;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected ServiceFlowRuntimeEngine serviceFlowRuntimeEngine;

    protected Logger logger = LoggerFactory.getLogger(PurchaseRequestManager.class);

    @Autowired
    protected SystemConfigureCategoryManager systemConfigureCategoryManager;

    @Autowired
    protected ServiceUIMetaProxy serviceUIMetaProxy;

    @Autowired
    protected PurchaseRequestServiceUIModelExtension purchaseRequestServiceUIModelExtension;

    @Autowired
    protected PurchaseRequestActionExecutionProxy purchaseRequestActionExecutionProxy;

    @Autowired
    protected PurchaseRequestMaterialItemServiceUIModelExtension purchaseRequestMaterialItemServiceUIModelExtension;

    private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

    public Map<Integer, String> initPriorityCode(String languageCode)
            throws ServiceEntityInstallationException {
        return standardPriorityProxy.getPriorityMap(languageCode);
    }

    public Map<Integer, String> initStatus(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.statusMapLan, PurchaseRequestUIModel.class, IDocumentNodeFieldConstant.STATUS);
    }

    public PurchaseRequestManager() {
        super.seConfigureProxy = new PurchaseRequestConfigureProxy();
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, purchaseRequestDAO));
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(purchaseRequestConfigureProxy);
    }

    @Override
    public ServiceEntityNode newRootEntityNode(String client)
            throws ServiceEntityConfigureException {
        PurchaseRequest purchaseRequest = (PurchaseRequest) super
                .newRootEntityNode(client);
        String purchaseRequestId = purchaseRequestIdHelper
                .genDefaultId(client);
        purchaseRequest.setId(purchaseRequestId);
        return purchaseRequest;
    }

    public void setGrossData(PurchaseRequestServiceModel purchaseRequestServiceModel) {
        List<PurchaseRequestMaterialItemServiceModel> purchaseRequestMaterialItemServieModelList =
                purchaseRequestServiceModel.getPurchaseRequestMaterialItemList();
        if (ServiceCollectionsHelper.checkNullList(purchaseRequestMaterialItemServieModelList)) {
            return;
        }
        PurchaseRequest purchaseRequest = purchaseRequestServiceModel.getPurchaseRequest();
        List<ServiceEntityNode> purchaseRequestMaterialItemList =
                purchaseRequestMaterialItemServieModelList.stream().map(PurchaseRequestMaterialItemServiceModel::getPurchaseRequestMaterialItem).collect(Collectors.toList());
        DocFlowProxy.GrossPricePair grossPricePair = DocFlowProxy.calculateGrossPrice(purchaseRequestMaterialItemList);
        purchaseRequest.setGrossPrice(grossPricePair.getGrossPrice());
        purchaseRequest.setGrossPriceDisplay(grossPricePair.getGrossPriceDisplay());
    }

    public void convPurchaseRequestToUI(PurchaseRequest purchaseRequest,
                                        PurchaseRequestUIModel purchaseRequestUIModel)
            throws ServiceEntityInstallationException {
        convPurchaseRequestToUI(purchaseRequest, purchaseRequestUIModel,
                null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convPurchaseRequestToUI(PurchaseRequest purchaseRequest,
                                        PurchaseRequestUIModel purchaseRequestUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        if (purchaseRequest != null) {
            docFlowProxy.convDocumentToUI(purchaseRequest,
                    purchaseRequestUIModel, logonInfo);
            purchaseRequestUIModel.setId(purchaseRequest.getId());
            purchaseRequestUIModel.setName(purchaseRequest.getName());
            purchaseRequestUIModel.setPriorityCode(purchaseRequest
                    .getPriorityCode());
            purchaseRequestUIModel.setProductionBatchNumber(purchaseRequest.getProductionBatchNumber());
            if (logonInfo != null) {
                Map<Integer, String> priorityCodeMap = initPriorityCode(logonInfo
                        .getLanguageCode());
                purchaseRequestUIModel.setPriorityCodeValue(priorityCodeMap
                        .get(purchaseRequest.getPriorityCode()));
            }
            purchaseRequestUIModel.setStatus(purchaseRequest.getStatus());
            if (logonInfo != null) {
                Map<Integer, String> statusMap = initStatus(logonInfo
                        .getLanguageCode());
                purchaseRequestUIModel.setStatusValue(statusMap
                        .get(purchaseRequest.getStatus()));
            }
            if (purchaseRequest.getPlanExecutionDate() != null) {
                purchaseRequestUIModel
                        .setPlanExecutionDate(DefaultDateFormatConstant.DATE_FORMAT
                                .format(purchaseRequest.getPlanExecutionDate()));
            }
            purchaseRequestUIModel.setGrossPrice(purchaseRequest.getGrossPrice());
            purchaseRequestUIModel.setGrossPriceDisplay(purchaseRequest.getGrossPriceDisplay());
            purchaseRequestUIModel.setNote(purchaseRequest.getNote());
        }
    }

    /**
     * [Internal method] Convert from UI model to se model:purchaseRequest
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToPurchaseRequest(
            PurchaseRequestUIModel purchaseRequestUIModel,
            PurchaseRequest rawEntity) {
        docFlowProxy.convUIToDocument(purchaseRequestUIModel, rawEntity);
        rawEntity.setPriorityCode(purchaseRequestUIModel.getPriorityCode());
        rawEntity.setGrossPrice(purchaseRequestUIModel.getGrossPrice());
        rawEntity.setGrossPriceDisplay(purchaseRequestUIModel.getGrossPriceDisplay());
        if (!ServiceEntityStringHelper.checkNullString(purchaseRequestUIModel
                .getPlanExecutionDate())) {
            try {
                rawEntity.setPlanExecutionDate(DefaultDateFormatConstant.DATE_FORMAT.parse(purchaseRequestUIModel.getPlanExecutionDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } catch (ParseException e) {
                // do nothing
            }
        }
        rawEntity.setNote(purchaseRequestUIModel.getNote());
    }

    public ServiceDocumentExtendUIModel convPurchaseRequestToDocExtUIModel(
            PurchaseRequestUIModel purchaseRequestUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
        serviceDocumentExtendUIModel.setRefUIModel(purchaseRequestUIModel);
        serviceDocumentExtendUIModel.setUuid(purchaseRequestUIModel.getUuid());
        serviceDocumentExtendUIModel.setParentNodeUUID(purchaseRequestUIModel
                .getParentNodeUUID());
        serviceDocumentExtendUIModel.setRootNodeUUID(purchaseRequestUIModel
                .getRootNodeUUID());
        serviceDocumentExtendUIModel.setId(purchaseRequestUIModel.getId());
        serviceDocumentExtendUIModel
                .setStatus(purchaseRequestUIModel.getStatus());
        serviceDocumentExtendUIModel.setStatusValue(purchaseRequestUIModel
                .getStatusValue());
        serviceDocumentExtendUIModel
                .setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_PURCHASEREQUEST);
        if(logonInfo != null){
            serviceDocumentExtendUIModel
                    .setDocumentTypeValue(serviceDocumentComProxy
                            .getDocumentTypeValue(IDefDocumentResource.DOCUMENT_TYPE_PURCHASEREQUEST,
                                    logonInfo.getLanguageCode()));
        }
        // Logic of reference Date
        String referenceDate = purchaseRequestUIModel.getCreatedDate();
        serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
        return serviceDocumentExtendUIModel;
    }

    public ServiceDocumentExtendUIModel convPurchaseRequestMaterialItemToDocExtUIModel(
            PurchaseRequestMaterialItemUIModel purchaseRequestMaterialItemUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
        docFlowProxy.convDocMatItemUIToDocExtUIModel(purchaseRequestMaterialItemUIModel,
                serviceDocumentExtendUIModel, logonInfo,
                IDefDocumentResource.DOCUMENT_TYPE_PURCHASEREQUEST);
        serviceDocumentExtendUIModel
                .setRefUIModel(purchaseRequestMaterialItemUIModel);
        serviceDocumentExtendUIModel.setId(purchaseRequestMaterialItemUIModel
                .getParentDocId());
        serviceDocumentExtendUIModel.setStatus(purchaseRequestMaterialItemUIModel
                .getParentDocStatus());
        serviceDocumentExtendUIModel
                .setStatusValue(purchaseRequestMaterialItemUIModel
                        .getParentDocStatusValue());
        // Logic of reference Date
        String referenceDate = purchaseRequestMaterialItemUIModel
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
            PurchaseRequest purchaseRequest = (PurchaseRequest) seNode;
            try {
                PurchaseRequestUIModel purchaseRequestUIModel = (PurchaseRequestUIModel) genUIModelFromUIModelExtension(
                        PurchaseRequestUIModel.class,
                        purchaseRequestServiceUIModelExtension
                                .genUIModelExtensionUnion().get(0),
                        purchaseRequest, logonInfo, null);
                return convPurchaseRequestToDocExtUIModel(purchaseRequestUIModel, logonInfo);
            } catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
                        e, PurchaseRequest.SENAME));
            }
        }
        if (PurchaseRequestMaterialItem.NODENAME.equals(seNode.getNodeName())) {
            PurchaseRequestMaterialItem purchaseRequestMaterialItem = (PurchaseRequestMaterialItem) seNode;
            try {
                PurchaseRequestMaterialItemUIModel purchaseRequestMaterialItemUIModel =
                        (PurchaseRequestMaterialItemUIModel) genUIModelFromUIModelExtension(
                        PurchaseRequestMaterialItemUIModel.class,
                        purchaseRequestMaterialItemServiceUIModelExtension
                                .genUIModelExtensionUnion().get(0),
                        purchaseRequestMaterialItem, logonInfo, null);
                return convPurchaseRequestMaterialItemToDocExtUIModel(purchaseRequestMaterialItemUIModel, logonInfo);
            } catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
                        e, PurchaseRequestMaterialItem.NODENAME));
            }
        }
        return null;
    }

    @Override
    public String getAuthorizationResource() {
        return IServiceModelConstants.PurchaseRequest;
    }

    @Override
    public ServiceSearchProxy getSearchProxy() {
        return purchaseRequestSearchProxy;
    }

    public boolean checkBlockExecutionByDocflow(int actionCode, String uuid, ServiceJSONRequest serviceJSONRequest,
                                                SerialLogonInfo serialLogonInfo){
        if(actionCode == PurchaseRequestActionNode.DOC_ACTION_APPROVE){
            return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(
                    IDefDocumentResource.DOCUMENT_TYPE_PURCHASEREQUEST, uuid, serviceJSONRequest, serialLogonInfo,
                    actionCode);
        }
        if(actionCode == PurchaseRequestActionNode.DOC_ACTION_REJECT_APPROVE){
            return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(
                    IDefDocumentResource.DOCUMENT_TYPE_PURCHASEREQUEST, uuid,serviceJSONRequest, serialLogonInfo,
                    actionCode);
        }
        if(actionCode == PurchaseRequestActionNode.DOC_ACTION_REVOKE_SUBMIT){
            serviceFlowRuntimeEngine.clearInvolveProcessIns(
                    IDefDocumentResource.DOCUMENT_TYPE_PURCHASEREQUEST,uuid);
            return true;
        }
        return true;
    }

    public void submitFlow(PurchaseRequestServiceUIModel purchaseRequestServiceUIModel,
                           SerialLogonInfo serialLogonInfo) throws ServiceFlowRuntimeException {
        String uuid = purchaseRequestServiceUIModel.getPurchaseRequestUIModel().getUuid();
        ServiceFlowRuntimeEngine.ServiceFlowInputPara serviceFlowInputPara =
                new ServiceFlowRuntimeEngine.ServiceFlowInputPara(purchaseRequestServiceUIModel,
                        IDefDocumentResource.DOCUMENT_TYPE_PURCHASEREQUEST, uuid,
                        PurchaseRequestActionNode.DOC_ACTION_APPROVE, serialLogonInfo);
        serviceFlowRuntimeEngine.submitFlow(serviceFlowInputPara);
    }

}
