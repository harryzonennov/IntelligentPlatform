package com.company.IntelligentPlatform.sales.service;

import jakarta.annotation.PostConstruct;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import com.company.IntelligentPlatform.sales.dto.*;
import com.company.IntelligentPlatform.sales.model.*;
import com.company.IntelligentPlatform.sales.model.SalesForcast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.company.IntelligentPlatform.sales.repository.SalesForcastRepository;

import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.StandardPriorityProxy;
import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.service.ServiceFlowRuntimeEngine;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.SystemConfigureCategoryManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.ServiceJSONRequest;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceFlowRuntimeException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.text.ParseException;
import java.util.*;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Logic Manager CLASS FOR Service Entity [SalesForcast]
 *
 * @author
 * @date Thu Jan 28 21:38:52 CST 2021
 * <p>
 * This class is generated automatically by platform automation register tool
 */
@Service
@Transactional
public class SalesForcastManager extends ServiceEntityManager {
    
    public static final String METHOD_ConvSalesForcastToUI = "convSalesForcastToUI";

    public static final String METHOD_ConvUIToSalesForcast = "convUIToSalesForcast";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected SalesForcastRepository salesForcastDAO;

    @Autowired
    protected SalesForcastConfigureProxy salesForcastConfigureProxy;

    @Autowired
    protected SalesForcastIdHelper salesForcastIdHelper;

    @Autowired
    protected StandardPriorityProxy standardPriorityProxy;

    @Autowired
    protected SalesForcastSearchProxy salesForcastSearchProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected DocActionNodeProxy docActionNodeProxy;

    @Autowired
    protected DocInvolvePartyProxy docInvolvePartyProxy;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    protected Logger logger = LoggerFactory.getLogger(SalesForcastManager.class);

    @Autowired
    protected SystemConfigureCategoryManager systemConfigureCategoryManager;

    @Autowired
    protected ServiceUIMetaProxy serviceUIMetaProxy;

    @Autowired
    protected SalesForcastServiceUIModelExtension salesForcastServiceUIModelExtension;

    @Autowired
    protected SalesForcastMaterialItemServiceUIModelExtension salesForcastMaterialItemServiceUIModelExtension;

    @Autowired
    protected SalesForcastActionExecutionProxy salesForcastActionExecutionProxy;

    @Autowired
    protected ServiceFlowRuntimeEngine serviceFlowRuntimeEngine;
    
    private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

    public Map<Integer, String> initPriorityCode(String languageCode)
            throws ServiceEntityInstallationException {
        return standardPriorityProxy.getPriorityMap(languageCode);
    }

    public Map<Integer, String> initStatus(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.statusMapLan, SalesForcastUIModel.class, IDocumentNodeFieldConstant.STATUS);
    }

    public SalesForcastManager() {
        super.seConfigureProxy = new SalesForcastConfigureProxy();
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, salesForcastDAO));
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(salesForcastConfigureProxy);
    }

    @Override
    public ServiceEntityNode newRootEntityNode(String client)
            throws ServiceEntityConfigureException {
        SalesForcast salesForcast = (SalesForcast) super
                .newRootEntityNode(client);
        String salesForcastId = salesForcastIdHelper
                .genDefaultId(client);
        salesForcast.setId(salesForcastId);
        return salesForcast;
    }

    public ServiceModule quickCreateServiceModel(
            SalesForcast salesForcast,
            List<ServiceEntityNode> salesForcastMatItemList) {
        SalesForcastServiceModel salesForcastrServiceModel = new SalesForcastServiceModel();
        salesForcastrServiceModel
                .setSalesForcast(salesForcast);
        List<SalesForcastMaterialItemServiceModel> salesForcastMaterialItemServiceModelList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(salesForcastMatItemList)) {
            salesForcastMatItemList
                    .forEach(rawSENode -> {
                        SalesForcastMaterialItemServiceModel salesForcastMaterialItemServiceModel = new SalesForcastMaterialItemServiceModel();
                        salesForcastMaterialItemServiceModel
                                .setSalesForcastMaterialItem((SalesForcastMaterialItem)rawSENode);
                        salesForcastMaterialItemServiceModelList
                                .add(salesForcastMaterialItemServiceModel);
                    });
        }
        salesForcastrServiceModel
                .setSalesForcastMaterialItemList(salesForcastMaterialItemServiceModelList);
        return salesForcastrServiceModel;
    }

    public void convSalesForcastToUI(SalesForcast salesForcast,
                                         SalesForcastUIModel salesForcastUIModel)
            throws ServiceEntityInstallationException {
        convSalesForcastToUI(salesForcast, salesForcastUIModel,
                null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convSalesForcastToUI(SalesForcast salesForcast,
                                         SalesForcastUIModel salesForcastUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        if (salesForcast != null) {
            docFlowProxy.convDocumentToUI(salesForcast,
                    salesForcastUIModel, logonInfo);
            salesForcastUIModel.setId(salesForcast.getId());
            salesForcastUIModel.setName(salesForcast.getName());
            salesForcastUIModel.setPriorityCode(salesForcast
                    .getPriorityCode());
            salesForcastUIModel.setProductionBatchNumber(salesForcast.getProductionBatchNumber());
            if (logonInfo != null) {
                Map<Integer, String> priorityCodeMap = initPriorityCode(logonInfo
                        .getLanguageCode());
                salesForcastUIModel.setPriorityCodeValue(priorityCodeMap
                        .get(salesForcast.getPriorityCode()));
            }
            salesForcastUIModel.setStatus(salesForcast.getStatus());
            if (logonInfo != null) {
                Map<Integer, String> statusMap = initStatus(logonInfo
                        .getLanguageCode());
                salesForcastUIModel.setStatusValue(statusMap
                        .get(salesForcast.getStatus()));
            }
            if (salesForcast.getPlanExecutionDate() != null) {
                salesForcastUIModel
                        .setPlanExecutionDate(DefaultDateFormatConstant.DATE_FORMAT
                                .format(salesForcast.getPlanExecutionDate()));
            }
            salesForcastUIModel.setGrossPrice(salesForcast.getGrossPrice());
            salesForcastUIModel.setGrossPriceDisplay(salesForcast.getGrossPriceDisplay());
            salesForcastUIModel.setNote(salesForcast.getNote());
        }
    }

    /**
     * [Internal method] Convert from UI model to se model:salesForcast
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToSalesForcast(
            SalesForcastUIModel salesForcastUIModel,
            SalesForcast rawEntity) {
        docFlowProxy.convUIToDocument(salesForcastUIModel, rawEntity);
        rawEntity.setClient(salesForcastUIModel.getClient());
        rawEntity.setId(salesForcastUIModel.getId());
        rawEntity.setName(salesForcastUIModel.getName());
        rawEntity.setPriorityCode(salesForcastUIModel.getPriorityCode());
        rawEntity.setGrossPrice(salesForcastUIModel.getGrossPrice());
        rawEntity.setGrossPriceDisplay(salesForcastUIModel.getGrossPriceDisplay());
        if (!ServiceEntityStringHelper.checkNullString(salesForcastUIModel
                .getPlanExecutionDate())) {
            try {
                rawEntity.setPlanExecutionDate(DefaultDateFormatConstant.DATE_FORMAT
                        .parse(salesForcastUIModel.getPlanExecutionDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
            } catch (ParseException e) {
                // do nothing
            }
        }
        rawEntity.setNote(salesForcastUIModel.getNote());
    }

    public ServiceDocumentExtendUIModel convSalesForcastToDocExtUIModel(
            SalesForcastUIModel salesForcastUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
        serviceDocumentExtendUIModel.setRefUIModel(salesForcastUIModel);
        serviceDocumentExtendUIModel.setUuid(salesForcastUIModel.getUuid());
        serviceDocumentExtendUIModel.setParentNodeUUID(salesForcastUIModel
                .getParentNodeUUID());
        serviceDocumentExtendUIModel.setRootNodeUUID(salesForcastUIModel
                .getRootNodeUUID());
        serviceDocumentExtendUIModel.setId(salesForcastUIModel.getId());
        serviceDocumentExtendUIModel
                .setStatus(salesForcastUIModel.getStatus());
        serviceDocumentExtendUIModel.setStatusValue(salesForcastUIModel
                .getStatusValue());
        serviceDocumentExtendUIModel
                .setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST);
        if(logonInfo != null){
            serviceDocumentExtendUIModel
                    .setDocumentTypeValue(serviceDocumentComProxy
                            .getDocumentTypeValue(IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST,
                                    logonInfo.getLanguageCode()));
        }
        // Logic of reference Date
        String referenceDate = salesForcastUIModel.getCreatedDate();
        serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
        return serviceDocumentExtendUIModel;
    }

    public ServiceDocumentExtendUIModel convSalesForcastMaterialItemToDocExtUIModel(
            SalesForcastMaterialItemUIModel salesForcastMaterialItemUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
        docFlowProxy.convDocMatItemUIToDocExtUIModel(salesForcastMaterialItemUIModel,
                serviceDocumentExtendUIModel, logonInfo,
                IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST);
        serviceDocumentExtendUIModel
                .setRefUIModel(salesForcastMaterialItemUIModel);
        serviceDocumentExtendUIModel.setId(salesForcastMaterialItemUIModel
                .getParentDocId());
        serviceDocumentExtendUIModel.setStatus(salesForcastMaterialItemUIModel
                .getParentDocStatus());
        serviceDocumentExtendUIModel
                .setStatusValue(salesForcastMaterialItemUIModel
                        .getParentDocStatusValue());

        // Logic of reference Date
        String referenceDate = salesForcastMaterialItemUIModel
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
            SalesForcast salesForcast = (SalesForcast) seNode;
            try {
                SalesForcastUIModel salesForcastUIModel = (SalesForcastUIModel) genUIModelFromUIModelExtension(
                        SalesForcastUIModel.class,
                        salesForcastServiceUIModelExtension
                                .genUIModelExtensionUnion().get(0),
                        salesForcast, logonInfo, null);
                return convSalesForcastToDocExtUIModel(salesForcastUIModel, logonInfo);
            } catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
                        e, SalesForcast.SENAME));
            }
        }
        if (SalesForcastMaterialItem.NODENAME.equals(seNode.getNodeName())) {
            SalesForcastMaterialItem salesForcastMaterialItem = (SalesForcastMaterialItem) seNode;
            try {
                SalesForcastMaterialItemUIModel salesForcastMaterialItemUIModel = (SalesForcastMaterialItemUIModel) genUIModelFromUIModelExtension(
                        SalesForcastMaterialItemUIModel.class,
                        salesForcastMaterialItemServiceUIModelExtension
                                .genUIModelExtensionUnion().get(0),
                        salesForcastMaterialItem, logonInfo, null);
                return convSalesForcastMaterialItemToDocExtUIModel(salesForcastMaterialItemUIModel, logonInfo);
            } catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
                        e, SalesForcastMaterialItem.NODENAME));
            }
        }
        return null;
    }

    @Override
    public String getAuthorizationResource() {
        return IServiceModelConstants.SalesForcast;
    }

    @Override
    public ServiceSearchProxy getSearchProxy() {
        return salesForcastSearchProxy;
    }

    public boolean checkBlockExecutionByDocflow(int actionCode, String uuid, ServiceJSONRequest serviceJSONRequest,
                                                SerialLogonInfo serialLogonInfo){
        if(actionCode == SalesForcastActionNode.DOC_ACTION_APPROVE){
            return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(
                    IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST, uuid, serviceJSONRequest, serialLogonInfo,
                    actionCode);
        }
        if(actionCode == SalesForcastActionNode.DOC_ACTION_REJECT_APPROVE){
            return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(
                    IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST, uuid,serviceJSONRequest, serialLogonInfo,
                    actionCode);
        }
        if(actionCode == SalesForcastActionNode.DOC_ACTION_REVOKE_SUBMIT){
            serviceFlowRuntimeEngine.clearInvolveProcessIns(
                    IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST,uuid);
            return true;
        }
        return true;
    }

    public void submitFlow(SalesForcastServiceUIModel salesForcastServiceUIModel, SerialLogonInfo serialLogonInfo) throws ServiceFlowRuntimeException {
        String uuid = salesForcastServiceUIModel.getSalesForcastUIModel().getUuid();
        ServiceFlowRuntimeEngine.ServiceFlowInputPara serviceFlowInputPara =
                new ServiceFlowRuntimeEngine.ServiceFlowInputPara(salesForcastServiceUIModel,
                        IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST, uuid,
                        SalesForcastActionNode.DOC_ACTION_APPROVE, serialLogonInfo);
        serviceFlowRuntimeEngine.submitFlow(serviceFlowInputPara);
    }

    @Override
    public void exeFlowActionEnd(int documentType, String uuid, int actionCode,
                                 ServiceJSONRequest serviceJSONRequest, SerialLogonInfo serialLogonInfo){
        try {
            SalesForcast salesForcast = (SalesForcast) getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID,
                    SalesForcast.NODENAME, serialLogonInfo.getClient(), null);
            SalesForcastServiceModel salesForcastServiceModel = (SalesForcastServiceModel) loadServiceModule(SalesForcastServiceModel.class,
                    salesForcast, salesForcastServiceUIModelExtension);
            salesForcastServiceModel.setServiceJSONRequest(serviceJSONRequest);
            if(actionCode == SystemDefDocActionCodeProxy.DOC_ACTION_APPROVE){
                salesForcastActionExecutionProxy.executeActionCore(salesForcastServiceModel,
                        actionCode, serialLogonInfo);
            }
            if(actionCode == SystemDefDocActionCodeProxy.DOC_ACTION_REJECT_APPROVE){
                salesForcastActionExecutionProxy.executeActionCore(salesForcastServiceModel,
                        actionCode, serialLogonInfo);
            }
        } catch (ServiceEntityConfigureException | ServiceModuleProxyException | DocActionException e) {
           logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
        }
    }

}