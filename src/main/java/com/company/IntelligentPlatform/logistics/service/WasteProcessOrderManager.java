package com.company.IntelligentPlatform.logistics.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.repository.WasteProcessOrderRepository;
import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.model.WasteProcessOrderConfigureProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreItemManager;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceFlowRuntimeEngine;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.ServiceFlowRuntimeException;

import jakarta.annotation.PostConstruct;
import java.util.*;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
@Transactional
public class WasteProcessOrderManager extends ServiceEntityManager {

    public static final String METHOD_ConvWasteProcessOrderToUI = "convWasteProcessOrderToUI";

    public static final String METHOD_ConvUIToWasteProcessOrder = "convUIToWasteProcessOrder";

    @Autowired
    protected WarehouseStoreItemManager warehouseStoreItemManager;
    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    protected WasteProcessOrderRepository wasteProcessOrderDAO;

    @Autowired
    protected WasteProcessOrderConfigureProxy wasteProcessOrderConfigureProxy;

    @Autowired
    protected WasteProcessOrderIdHelper wasteProcessOrderIdHelper;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected WasteProcessOrderServiceUIModelExtension wasteProcessOrderServiceUIModelExtension;

    @Autowired
    protected WasteProcessMaterialItemServiceUIModelExtension wasteProcessMaterialItemServiceUIModelExtension;

    @Autowired
    protected WasteProcessOrderSearchProxy wasteProcessOrderSearchProxy;

    @Autowired
    protected StandardPriorityProxy standardPriorityProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected ServiceFlowRuntimeEngine serviceFlowRuntimeEngine;

    @Autowired
    protected WasteProcessOrderActionExecutionProxy wasteProcessOrderActionExecutionProxy;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, Map<Integer, String>> processTypeMapLan = new HashMap<>();

    private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

    public Map<Integer, String> initPriorityCode(String languageCode)
            throws ServiceEntityInstallationException {
        return standardPriorityProxy.getPriorityMap(languageCode);
    }

    public Map<Integer, String> initStatus(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.statusMapLan, WasteProcessOrderUIModel.class, IDocumentNodeFieldConstant.STATUS);
    }

    public Map<Integer, String> initItemStatus(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.statusMapLan, WasteProcessOrderUIModel.class,
                IDocumentNodeFieldConstant.STATUS);
    }

    public Map<Integer, String> initProcessType(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.processTypeMapLan, WasteProcessOrderUIModel.class,
                "processType");
    }

    @Override
    public ServiceEntityNode newRootEntityNode(String client)
            throws ServiceEntityConfigureException {
        WasteProcessOrder wasteProcessOrder = (WasteProcessOrder) super
                .newRootEntityNode(client);
        String wasteProcessOrderID = wasteProcessOrderIdHelper.genDefaultId(client);
        wasteProcessOrder.setId(wasteProcessOrderID);
        return wasteProcessOrder;
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     */
    public void convWasteProcessOrderToUI(WasteProcessOrder wasteProcessOrder,
                                      WasteProcessOrderUIModel wasteProcessOrderUIModel)
            throws ServiceEntityInstallationException {
        convWasteProcessOrderToUI(wasteProcessOrder, wasteProcessOrderUIModel, null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     */
    public void convWasteProcessOrderToUI(WasteProcessOrder wasteProcessOrder,
                                      WasteProcessOrderUIModel wasteProcessOrderUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        if (wasteProcessOrder != null) {
            docFlowProxy.convDocumentToUI(wasteProcessOrder,
                    wasteProcessOrderUIModel, logonInfo);
            wasteProcessOrderUIModel.setStatus(wasteProcessOrder.getStatus());
            wasteProcessOrderUIModel.setPriorityCode(wasteProcessOrder
                    .getPriorityCode());
            wasteProcessOrderUIModel.setProcessType(wasteProcessOrder
                    .getProcessType());
            if (logonInfo != null) {
                Map<Integer, String> statusMap = this.initStatus(logonInfo.getLanguageCode());
                wasteProcessOrderUIModel.setStatusValue(statusMap
                        .get(wasteProcessOrder.getStatus()));
                Map<Integer, String> priorityCodeMap = this.initPriorityCode(logonInfo.getLanguageCode());
                wasteProcessOrderUIModel.setPriorityCodeValue(priorityCodeMap
                        .get(wasteProcessOrder.getPriorityCode()));
                Map<Integer, String> processTypeMap = this.initProcessType(logonInfo.getLanguageCode());
                if (processTypeMap != null) {
                    wasteProcessOrderUIModel.setProcessTypeValue(processTypeMap
                            .get(wasteProcessOrder.getProcessType()));
                }
            }
            wasteProcessOrderUIModel.setCurrencyCode(wasteProcessOrder
                    .getCurrencyCode());
            wasteProcessOrderUIModel.setGrossPrice(wasteProcessOrder.getGrossPrice());
            wasteProcessOrderUIModel.setGrossPriceDisplay(wasteProcessOrder.getGrossPriceDisplay());
        }
    }

    /**
     * [Internal method] Convert from UI model to se model:wasteProcessOrder
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToWasteProcessOrder(
            WasteProcessOrderUIModel wasteProcessOrderUIModel, WasteProcessOrder rawEntity) {
        docFlowProxy.convUIToDocument(wasteProcessOrderUIModel, rawEntity);
        rawEntity.setCurrencyCode(wasteProcessOrderUIModel.getCurrencyCode());
        rawEntity.setStatus(wasteProcessOrderUIModel.getStatus());
        rawEntity.setProcessType(wasteProcessOrderUIModel.getProcessType());
        rawEntity.setPriorityCode(wasteProcessOrderUIModel.getPriorityCode());
        rawEntity.setGrossPrice(wasteProcessOrderUIModel.getGrossPrice());
        rawEntity.setGrossPriceDisplay(wasteProcessOrderUIModel.getGrossPriceDisplay());
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, wasteProcessOrderDAO));
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(wasteProcessOrderConfigureProxy);
    }

    public List<PageHeaderModel> getPageHeaderModelList(
            WasteProcessOrder wasteProcessOrder, String client)
            throws ServiceEntityConfigureException {
        List<PageHeaderModel> resultList = new ArrayList<>();
        int index = 0;
        PageHeaderModel itemHeaderModel = getPageHeaderModel(
                wasteProcessOrder, index);
        if (itemHeaderModel != null) {
            resultList.add(itemHeaderModel);
        }
        return resultList;
    }

    protected PageHeaderModel getPageHeaderModel(
            WasteProcessOrder wasteProcessOrder, int index)
            throws ServiceEntityConfigureException {
        if (wasteProcessOrder == null) {
            return null;
        }
        PageHeaderModel pageHeaderModel = new PageHeaderModel();
        pageHeaderModel.setPageTitle("wasteProcessOrderPageTitle");
        pageHeaderModel.setNodeInstId(WasteProcessOrder.SENAME);
        pageHeaderModel.setUuid(wasteProcessOrder.getUuid());
        pageHeaderModel.setHeaderName(wasteProcessOrder.getId());
        pageHeaderModel.setIndex(index);
        return pageHeaderModel;
    }

    public ServiceDocumentExtendUIModel convWasteProcessOrderToDocExtUIModel(
            WasteProcessOrderUIModel wasteProcessOrderUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
        serviceDocumentExtendUIModel.setRefUIModel(wasteProcessOrderUIModel);
        serviceDocumentExtendUIModel.setUuid(wasteProcessOrderUIModel.getUuid());
        serviceDocumentExtendUIModel.setParentNodeUUID(wasteProcessOrderUIModel
                .getParentNodeUUID());
        serviceDocumentExtendUIModel.setRootNodeUUID(wasteProcessOrderUIModel
                .getRootNodeUUID());
        serviceDocumentExtendUIModel.setId(wasteProcessOrderUIModel.getId());
        serviceDocumentExtendUIModel
                .setStatus(wasteProcessOrderUIModel.getStatus());
        serviceDocumentExtendUIModel.setStatusValue(wasteProcessOrderUIModel
                .getStatusValue());
        serviceDocumentExtendUIModel
                .setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_WASTEPROCESSORDER);
        if(logonInfo != null){
            serviceDocumentExtendUIModel
                    .setDocumentTypeValue(serviceDocumentComProxy
                            .getDocumentTypeValue(IDefDocumentResource.DOCUMENT_TYPE_WASTEPROCESSORDER,
                                    logonInfo.getLanguageCode()));
        }
        String referenceDate = wasteProcessOrderUIModel.getUpdatedDate();
        serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
        return serviceDocumentExtendUIModel;
    }

    public ServiceDocumentExtendUIModel convWasteProcessMaterialItemToDocExtUIModel(
            WasteProcessMaterialItemUIModel wasteProcessMaterialItemUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
        docFlowProxy.convDocMatItemUIToDocExtUIModel(wasteProcessMaterialItemUIModel,
                serviceDocumentExtendUIModel, logonInfo,
                IDefDocumentResource.DOCUMENT_TYPE_WASTEPROCESSORDER);
        serviceDocumentExtendUIModel
                .setRefUIModel(wasteProcessMaterialItemUIModel);
        serviceDocumentExtendUIModel.setId(wasteProcessMaterialItemUIModel
                .getParentDocId());
        serviceDocumentExtendUIModel.setStatus(wasteProcessMaterialItemUIModel
                .getParentDocStatus());
        serviceDocumentExtendUIModel
                .setStatusValue(wasteProcessMaterialItemUIModel
                        .getParentDocStatusValue());
        // Logic of reference Date
        String referenceDate = wasteProcessMaterialItemUIModel.getUpdatedDate();
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
            WasteProcessOrder wasteProcessOrder = (WasteProcessOrder) seNode;
            try {
                WasteProcessOrderUIModel wasteProcessOrderUIModel = (WasteProcessOrderUIModel) genUIModelFromUIModelExtension(
                        WasteProcessOrderUIModel.class,
                        wasteProcessOrderServiceUIModelExtension
                                .genUIModelExtensionUnion().get(0),
                        wasteProcessOrder, logonInfo, null);
                return convWasteProcessOrderToDocExtUIModel(wasteProcessOrderUIModel, logonInfo);
            } catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
                        e, WasteProcessOrder.SENAME));
            }
        }
        if (WasteProcessMaterialItem.NODENAME.equals(seNode.getNodeName())) {
            WasteProcessMaterialItem wasteProcessMaterialItem = (WasteProcessMaterialItem) seNode;
            try {
                WasteProcessMaterialItemUIModel wasteProcessMaterialItemUIModel =
                        (WasteProcessMaterialItemUIModel) genUIModelFromUIModelExtension(
                                WasteProcessMaterialItemUIModel.class,
                                wasteProcessMaterialItemServiceUIModelExtension
                                        .genUIModelExtensionUnion().get(0),
                                wasteProcessMaterialItem, logonInfo, null);
                return convWasteProcessMaterialItemToDocExtUIModel(wasteProcessMaterialItemUIModel, logonInfo);
            } catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
                        e, WasteProcessMaterialItem.NODENAME));
            }
        }
        return null;
    }

    @Override
    public String getAuthorizationResource() {
        return IServiceModelConstants.WasteProcessOrder;
    }

    @Override
    public ServiceSearchProxy getSearchProxy() {
        return wasteProcessOrderSearchProxy;
    }

    public boolean checkBlockExecutionByDocflow(int actionCode, String uuid,
                                                ServiceJSONRequest serviceJSONRequest, SerialLogonInfo serialLogonInfo){
        if(actionCode == WasteProcessOrderActionNode.DOC_ACTION_APPROVE){
            return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(
                    IDefDocumentResource.DOCUMENT_TYPE_WASTEPROCESSORDER, uuid, serviceJSONRequest, serialLogonInfo,
                    actionCode);
        }
        if(actionCode == WasteProcessOrderActionNode.DOC_ACTION_REJECT_APPROVE){
            return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(
                    IDefDocumentResource.DOCUMENT_TYPE_WASTEPROCESSORDER, uuid,serviceJSONRequest, serialLogonInfo,
                    actionCode);
        }
        if(actionCode == WasteProcessOrderActionNode.DOC_ACTION_REVOKE_SUBMIT){
            serviceFlowRuntimeEngine.clearInvolveProcessIns(
                    IDefDocumentResource.DOCUMENT_TYPE_WASTEPROCESSORDER,uuid);
            return true;
        }
        return true;
    }

    public void submitFlow(WasteProcessOrderServiceUIModel wasteProcessOrderServiceUIModel,
                           SerialLogonInfo serialLogonInfo) throws ServiceFlowRuntimeException {
        String uuid = wasteProcessOrderServiceUIModel.getWasteProcessOrderUIModel().getUuid();
        ServiceFlowRuntimeEngine.ServiceFlowInputPara serviceFlowInputPara =
                new ServiceFlowRuntimeEngine.ServiceFlowInputPara(wasteProcessOrderServiceUIModel,
                        IDefDocumentResource.DOCUMENT_TYPE_WASTEPROCESSORDER, uuid,
                        WasteProcessOrderActionNode.DOC_ACTION_APPROVE, serialLogonInfo);
        serviceFlowRuntimeEngine.submitFlow(serviceFlowInputPara);
    }

    @Override
    public void exeFlowActionEnd(int documentType, String uuid, int actionCode, ServiceJSONRequest serviceJSONRequest,
                                 SerialLogonInfo serialLogonInfo) {
        try {
            WasteProcessOrder wasteProcessOrder = (WasteProcessOrder) getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID,
                    WasteProcessOrder.NODENAME, serialLogonInfo.getClient(), null);
            WasteProcessOrderServiceModel wasteProcessOrderServiceModel = (WasteProcessOrderServiceModel) loadServiceModule(WasteProcessOrderServiceModel.class,
                    wasteProcessOrder, wasteProcessOrderServiceUIModelExtension);
            wasteProcessOrderServiceModel.setServiceJSONRequest(serviceJSONRequest);
            wasteProcessOrderActionExecutionProxy.executeActionCore(wasteProcessOrderServiceModel, actionCode,
                    serialLogonInfo);
        } catch (ServiceEntityConfigureException | ServiceModuleProxyException | DocActionException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, String.valueOf(actionCode)));
        }
    }

}
