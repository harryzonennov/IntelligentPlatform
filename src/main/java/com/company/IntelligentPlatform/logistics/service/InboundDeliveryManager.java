package com.company.IntelligentPlatform.logistics.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.finance.service.FinanceAccountValueProxyException;
import com.company.IntelligentPlatform.finance.service.SystemResourceException;
import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.repository.InboundDeliveryRepository;
import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.model.InboundDeliveryConfigureProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreItemUIModel;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.ServiceExtensionSettingManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityDoubleHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import static com.company.IntelligentPlatform.common.service.CrossDocBatchConvertProxy.KEY_NEWTARGET;
import java.time.ZoneId;
import java.time.LocalDateTime;

/**
 * Logic Manager CLASS FOR Service Entity [InboundDelivery]
 *
 * @author
 * @date Fri Dec 27 18:35:56 CST 2013
 * <p>
 * This class is generated automatically by platform automation register
 * tool
 */
@Service
@Transactional
public class InboundDeliveryManager extends ServiceEntityManager {

    public static final String METHOD_ConvInboundDeliveryToUI = "convInboundDeliveryToUI";

    public static final String METHOD_ConvUIToInboundDelivery = "convUIToInboundDelivery";

    public static final String METHOD_ConvWarehouseToUI = "convWarehouseToUI";

    public static final String METHOD_ConvWarehouseAreaToUI = "convWarehouseAreaToUI";
    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    protected InboundDeliveryRepository inboundDeliveryDAO;

    @Autowired
    protected InboundDeliveryConfigureProxy inboundDeliveryConfigureProxy;

    @Autowired
    protected InboundDeliveryIdHelper inboundDeliveryIdHelper;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected OutboundDeliveryManager outboundDeliveryManager;

    @Autowired
    protected ServiceExtensionSettingManager serviceExtensionSettingManager;

    @Autowired
    protected InboundDeliveryServiceUIModelExtension inboundDeliveryServiceUIModelExtension;

    @Autowired
    protected InboundItemServiceUIModelExtension inboundItemServiceUIModelExtension;

    @Autowired
    protected InboundDeliveryActionExecutionProxy inboundDeliveryActionExecutionProxy;

    @Autowired
    protected InboundDeliverySearchProxy inboundDeliverySearchProxy;

    @Autowired
    protected SplitMatItemProxy splitMatItemProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected StandardPriorityProxy standardPriorityProxy;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

    protected Map<String, Map<Integer, String>> planCategoryMapLan = new HashMap<>();

    protected Map<String, Map<Integer, String>> freightChargeTypeMapLan = new HashMap<>();
    @Autowired
    private SerialIdDocumentProxy serialIdDocumentProxy;

    public InboundDeliveryManager() {
        super.seConfigureProxy = new InboundDeliveryConfigureProxy();
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, inboundDeliveryDAO));
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(inboundDeliveryConfigureProxy);
    }

    public Map<Integer, String> initFreightChargeType(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.freightChargeTypeMapLan, InboundDeliveryUIModel.class,
                "freightChargeType");
    }

    public Map<Integer, String> initPlanCategoryMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.planCategoryMapLan, InboundDeliveryUIModel.class,
                "planCategory");
    }

    public Map<Integer, String> initStatusMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.statusMapLan, InboundDeliveryUIModel.class, "status");
    }

    public Map<Integer, String> initPriorityCode(String languageCode)
            throws ServiceEntityInstallationException {
        return standardPriorityProxy.getPriorityMap(languageCode);
    }

    public Map<Integer, String> initDocumentTypeMap(boolean filterFlag, String languageCode)
            throws ServiceEntityInstallationException {
        return serviceDocumentComProxy
                .getDocumentTypeMap(filterFlag, languageCode);
    }

    public void postUpdateServiceUIModel(InboundDeliveryServiceUIModel inboundDeliveryServiceUIModel,
                                         InboundDeliveryServiceModel inboundDeliveryServiceModel) throws ServiceEntityConfigureException {
        List<InboundItemUIModel> inboundItemUIModelList =
                ServiceCollectionsHelper.parseToUINodeList(inboundDeliveryServiceUIModel.getInboundItemUIModelList(),
                        InboundItemServiceUIModel::getInboundItemUIModel);
        List<ServiceEntityNode> inboundItemList = ServiceCollectionsHelper.parseToSENodeList(inboundDeliveryServiceModel.getInboundItemList(),
                InboundItemServiceModel::getInboundItem);
        serialIdDocumentProxy.updateSerialIdMetaToDocumentWrapper(inboundItemUIModelList, inboundItemList);
    }

    @Override
    public ServiceEntityNode newRootEntityNode(String client)
            throws ServiceEntityConfigureException {
        String id = inboundDeliveryIdHelper.genDefaultId(client);
        InboundDelivery inboundDelivery = (InboundDelivery) super
                .newRootEntityNode(client);
        inboundDelivery.setId(id);
        serviceExtensionSettingManager.setInitValue(inboundDelivery,
                InboundDelivery.SENAME);
        return inboundDelivery;
    }

    public synchronized void recordStore(InboundDeliveryServiceModel inboundDeliveryServiceModel,
                                                  List<ServiceEntityNode> inboundItemList,
                                                  LogonInfo logonInfo)
            throws ServiceEntityConfigureException, InboundDeliveryException, SystemResourceException,
            FinanceAccountValueProxyException, SearchConfigureException, ServiceEntityInstallationException,
            DocActionException, ServiceModuleProxyException {
        DeliveryMatItemBatchGenRequest genRequest = new DeliveryMatItemBatchGenRequest();
        genRequest.setTargetDocType(IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM);
        genRequest.setTargetUUID(KEY_NEWTARGET);
        genRequest.setBaseUUID(inboundDeliveryServiceModel.getInboundDelivery().getUuid());
        InboundDelivery inboundDelivery = inboundDeliveryServiceModel.getInboundDelivery();
        genRequest.setRefWarehouseUUID(inboundDelivery.getRefWarehouseUUID());
        genRequest.setRefWarehouseAreaUUID(inboundDelivery.getRefWarehouseAreaUUID());
        inboundDeliveryActionExecutionProxy.crossCreateDocumentBatch(inboundDeliveryServiceModel,
                inboundItemList, genRequest, logonInfo);
    }

    public void convInboundDeliveryToUI(InboundDelivery inboundDelivery,
                                        InboundDeliveryUIModel inboundDeliveryUIModel)
            throws ServiceEntityInstallationException {
        convInboundDeliveryToUI(inboundDelivery, inboundDeliveryUIModel, null);
    }

    public void convInboundDeliveryToUI(InboundDelivery inboundDelivery,
                                        InboundDeliveryUIModel inboundDeliveryUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        if (inboundDelivery != null) {
            docFlowProxy.convDocumentToUI(inboundDelivery,
                    inboundDeliveryUIModel, logonInfo);
            inboundDeliveryUIModel.setNote(inboundDelivery.getNote());
            inboundDeliveryUIModel.setRefWarehouseUUID(inboundDelivery
                    .getRefWarehouseUUID());
            inboundDeliveryUIModel.setStatus(inboundDelivery.getStatus());
            if (logonInfo != null) {
                Map<Integer, String> statusMap = this.initStatusMap(logonInfo
                        .getLanguageCode());
                inboundDeliveryUIModel.setStatusValue(statusMap
                        .get(inboundDelivery.getStatus()));
            }
            inboundDeliveryUIModel.setRefWarehouseAreaUUID(inboundDelivery
                    .getRefWarehouseAreaUUID());
            inboundDeliveryUIModel.setPlanCategory(inboundDelivery
                    .getPlanCategory());
            inboundDeliveryUIModel.setProductionBatchNumber(inboundDelivery
                    .getProductionBatchNumber());
            inboundDeliveryUIModel.setPurchaseBatchNumber(inboundDelivery
                    .getPurchaseBatchNumber());
            if (logonInfo != null) {
                Map<Integer, String> planCategoryMap = this
                        .initPlanCategoryMap(logonInfo.getLanguageCode());
                inboundDeliveryUIModel.setPlanCategoryValue(planCategoryMap
                        .get(inboundDelivery.getPlanCategory()));
            }
            if (inboundDelivery.getPlanExecuteDate() != null) {
                inboundDeliveryUIModel
                        .setPlanExecuteDate(DefaultDateFormatConstant.DATE_MIN_FORMAT
                                .format(inboundDelivery.getPlanExecuteDate()));
            }
            if (logonInfo != null) {
                Map<Integer, String> planCategoryMap = this
                        .initPlanCategoryMap(logonInfo.getLanguageCode());
                inboundDeliveryUIModel.setPlanCategoryValue(planCategoryMap
                        .get(inboundDelivery.getPlanCategory()));
            }
            inboundDeliveryUIModel.setGrossPrice(inboundDelivery
                    .getGrossPrice());
            if (inboundDelivery.getShippingTime() != null) {
                inboundDeliveryUIModel
                        .setShippingTime(DefaultDateFormatConstant.DATE_FORMAT
                                .format(inboundDelivery.getShippingTime()));
            }
            inboundDeliveryUIModel.setShippingPoint(inboundDelivery
                    .getShippingPoint());
            if (logonInfo != null) {
                Map<Integer, String> freightChargeTypeMap = initFreightChargeType(logonInfo
                        .getLanguageCode());
                inboundDeliveryUIModel
                        .setFreightChargeTypeValue(freightChargeTypeMap
                                .get(inboundDelivery.getFreightChargeType()));
            }
            inboundDeliveryUIModel.setFreightChargeType(inboundDelivery
                    .getFreightChargeType());
        }
    }

    public void convWarehouseAreaToUI(WarehouseArea warehouseArea,
                                      InboundDeliveryUIModel inboundDeliveryUIModel) {
        if (warehouseArea != null) {
            inboundDeliveryUIModel.setRefWarehouseAreaUUID(warehouseArea
                    .getUuid());
            inboundDeliveryUIModel.setRefWarehouseAreaId(warehouseArea.getId());
            inboundDeliveryUIModel.setRefWarehouseAreaName(warehouseArea
                    .getName());
        }
    }

    public void convWarehouseToUI(Warehouse warehouse,
                                  InboundDeliveryUIModel inboundDeliveryUIModel) {
        if (warehouse != null) {
            inboundDeliveryUIModel.setRefWarehouseId(warehouse.getId());
            inboundDeliveryUIModel.setRefWarehouseName(warehouse.getName());
        }
    }

    public void setWarehouseStoreInboundInfo(
            WarehouseStoreItemUIModel warehouseStoreItemUIModel,
            String inboundItemUUID) throws ServiceEntityConfigureException {
        InboundItem inboundItem = (InboundItem) getEntityNodeByKey(
                inboundItemUUID, IServiceEntityNodeFieldConstant.UUID,
                InboundItem.NODENAME, null);
        if (inboundItem == null) {
            return;
        }
        convInboundItemToStoreItemUI(inboundItem,
                warehouseStoreItemUIModel);
        InboundDelivery inboundDelivery = (InboundDelivery) getEntityNodeByUUID(
                inboundItem.getParentNodeUUID(),
                 InboundDelivery.NODENAME,
                inboundItem.getClient());
        convInboundDeliveryToStoreItemUI(inboundDelivery,
                warehouseStoreItemUIModel);
        OutboundDelivery outboundDelivery = outboundDeliveryManager
                .getRefOutboundDeliveryFromStoreItem(warehouseStoreItemUIModel
                        .getUuid());
    }

    public void convInboundItemToStoreItemUI(
            InboundItem inboundItem,
            WarehouseStoreItemUIModel warehouseStoreItemUIModel)
            throws ServiceEntityConfigureException {
        if (inboundItem != null) {
            warehouseStoreItemUIModel.setInitAmount(inboundItem
                    .getAmount());
            warehouseStoreItemUIModel.setInboundFee(inboundItem
                    .getInboundFee());
            warehouseStoreItemUIModel
                    .setInboundFeeNoTax(ServiceEntityDoubleHelper
                            .trancateDoubleScale2(inboundItem
                                    .getItemPriceNoTax()));
            warehouseStoreItemUIModel
                    .setInboundItemPrice(ServiceEntityDoubleHelper
                            .trancateDoubleScale2(inboundItem
                                    .getItemPrice()));
            warehouseStoreItemUIModel
                    .setInboundItemPriceNoTax(ServiceEntityDoubleHelper
                            .trancateDoubleScale2(inboundItem
                                    .getItemPriceNoTax()));
            warehouseStoreItemUIModel
                    .setInboundUnitPrice(ServiceEntityDoubleHelper
                            .trancateDoubleScale2(inboundItem
                                    .getUnitPrice()));
            warehouseStoreItemUIModel
                    .setInboundUnitPriceNoTax(ServiceEntityDoubleHelper
                            .trancateDoubleScale2(inboundItem
                                    .getUnitPriceNoTax()));
            warehouseStoreItemUIModel.setRefInitUnitUUID(inboundItem
                    .getRefUnitUUID());
            if (!ServiceEntityStringHelper.checkNullString(inboundItem
                    .getRefUnitName())) {
                warehouseStoreItemUIModel
                        .setRefInitUnitName(inboundItem
                                .getRefUnitName());
            } else {
                try {
                    Map<String, String> materialUnitMap = materialStockKeepUnitManager
                            .initMaterialUnitMap(inboundItem
                                            .getRefMaterialSKUUUID(),
                                    inboundItem.getClient());
                    if (!ServiceEntityStringHelper
                            .checkNullString(inboundItem
                                    .getRefMaterialSKUUUID())
                            && materialUnitMap != null) {
                        warehouseStoreItemUIModel
                                .setRefInitUnitName(materialUnitMap
                                        .get(inboundItem
                                                .getRefUnitUUID()));
                    }
                } catch (MaterialException e) {
                    // just skip
                }
            }
            warehouseStoreItemUIModel
                    .setRefWarehouseAreaUUID(inboundItem.getName());
        }
    }

    public void convInboundDeliveryToStoreItemUI(
            InboundDelivery inboundDelivery,
            WarehouseStoreItemUIModel warehouseStoreItemUIModel) {
        if (inboundDelivery != null) {
            warehouseStoreItemUIModel.setInboundDeliveryId(inboundDelivery
                    .getId());
        }
    }

    public void convUIToInboundDelivery(
            InboundDeliveryUIModel inboundDeliveryUIModel,
            InboundDelivery rawEntity) {
        docFlowProxy.convUIToDocument(inboundDeliveryUIModel, rawEntity);
        if (!ServiceEntityStringHelper.checkNullString(inboundDeliveryUIModel
                .getRefWarehouseUUID())) {
            rawEntity.setRefWarehouseUUID(inboundDeliveryUIModel
                    .getRefWarehouseUUID());
        }
        if (!ServiceEntityStringHelper.checkNullString(inboundDeliveryUIModel
                .getRefWarehouseAreaUUID())) {
            rawEntity.setRefWarehouseAreaUUID(inboundDeliveryUIModel
                    .getRefWarehouseAreaUUID());
        }
        if (!ServiceEntityStringHelper.checkNullString(inboundDeliveryUIModel
                .getProductionBatchNumber())) {
            rawEntity.setProductionBatchNumber(inboundDeliveryUIModel
                    .getProductionBatchNumber());
        }
        if (!ServiceEntityStringHelper.checkNullString(inboundDeliveryUIModel
                .getPurchaseBatchNumber())) {
            rawEntity.setPurchaseBatchNumber(inboundDeliveryUIModel
                    .getPurchaseBatchNumber());
        }
        if (inboundDeliveryUIModel.getPlanCategory() != 0) {
            rawEntity.setPlanCategory(inboundDeliveryUIModel.getPlanCategory());
        }
        if (!ServiceEntityStringHelper.checkNullString(inboundDeliveryUIModel
                .getPlanExecuteDate())) {
            try {
                rawEntity
                        .setPlanExecuteDate(DefaultDateFormatConstant.DATE_FORMAT.parse(inboundDeliveryUIModel
                                        .getPlanExecuteDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
        rawEntity.setGrossPrice(inboundDeliveryUIModel.getGrossPrice());
        if (!ServiceEntityStringHelper.checkNullString(inboundDeliveryUIModel
                .getShippingTime())) {
            try {
                rawEntity.setShippingTime(DefaultDateFormatConstant.DATE_FORMAT.parse(inboundDeliveryUIModel.getShippingTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
        rawEntity.setShippingPoint(inboundDeliveryUIModel.getShippingPoint());
        rawEntity.setFreightCharge(inboundDeliveryUIModel.getFreightCharge());
        rawEntity.setFreightChargeType(inboundDeliveryUIModel
                .getFreightChargeType());
    }

    public ServiceDocumentExtendUIModel convInboundDeliveryToDocExtUIModel(
            InboundDeliveryUIModel inboundDeliveryUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
        serviceDocumentExtendUIModel.setRefUIModel(inboundDeliveryUIModel);
        serviceDocumentExtendUIModel.setUuid(inboundDeliveryUIModel.getUuid());
        serviceDocumentExtendUIModel.setParentNodeUUID(inboundDeliveryUIModel
                .getParentNodeUUID());
        serviceDocumentExtendUIModel.setRootNodeUUID(inboundDeliveryUIModel
                .getRootNodeUUID());
        serviceDocumentExtendUIModel.setId(inboundDeliveryUIModel.getId());
        serviceDocumentExtendUIModel.setStatus(inboundDeliveryUIModel
                .getStatus());
        serviceDocumentExtendUIModel.setStatusValue(inboundDeliveryUIModel
                .getStatusValue());
        serviceDocumentExtendUIModel
                .setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY);
        if(logonInfo != null){
            serviceDocumentExtendUIModel
                    .setDocumentTypeValue(serviceDocumentComProxy
                            .getDocumentTypeValue(IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY,
                                    logonInfo.getLanguageCode()));
        }
        // Logic of reference Date
        String referenceDate = inboundDeliveryUIModel.getUpdatedDate();
        if (ServiceEntityStringHelper.checkNullString(referenceDate)) {
            referenceDate = inboundDeliveryUIModel.getUpdatedDate();
        }
        if (ServiceEntityStringHelper.checkNullString(referenceDate)) {
            referenceDate = inboundDeliveryUIModel.getCreatedDate();
        }
        serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
        return serviceDocumentExtendUIModel;
    }

    public ServiceDocumentExtendUIModel convInboundItemToDocExtUIModel(
            InboundItemUIModel inboundItemUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
        docFlowProxy.convDocMatItemUIToDocExtUIModel(
                inboundItemUIModel, serviceDocumentExtendUIModel,
                logonInfo, IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY);
        serviceDocumentExtendUIModel.setRefUIModel(inboundItemUIModel);
        serviceDocumentExtendUIModel.setId(inboundItemUIModel
                .getParentDocId());
        serviceDocumentExtendUIModel.setStatus(inboundItemUIModel
                .getParentDocStatus());
        serviceDocumentExtendUIModel.setStatusValue(inboundItemUIModel
                .getParentDocStatusValue());
        // Logic of reference Date
        String referenceDate = inboundItemUIModel.getUpdatedDate();
        if (ServiceEntityStringHelper.checkNullString(referenceDate)) {
            referenceDate = inboundItemUIModel.getUpdatedDate();
        }
        if (ServiceEntityStringHelper.checkNullString(referenceDate)) {
            referenceDate = inboundItemUIModel.getCreatedDate();
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
            InboundDelivery inboundDelivery = (InboundDelivery) seNode;
            try {
                InboundDeliveryUIModel inboundDeliveryUIModel = (InboundDeliveryUIModel) genUIModelFromUIModelExtension(
                        InboundDeliveryUIModel.class,
                        inboundDeliveryServiceUIModelExtension
                                .genUIModelExtensionUnion().get(0),
                        inboundDelivery, logonInfo, null);
                return convInboundDeliveryToDocExtUIModel(inboundDeliveryUIModel, logonInfo);
            } catch (ServiceModuleProxyException | ServiceEntityConfigureException |
                     ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
                        e, InboundDelivery.SENAME));
            }
        }
        if (InboundItem.NODENAME.equals(seNode.getNodeName())) {
            InboundItem inboundItem = (InboundItem) seNode;
            try {
                InboundItemUIModel inboundItemUIModel = (InboundItemUIModel) genUIModelFromUIModelExtension(
                        InboundItemUIModel.class,
                        inboundItemServiceUIModelExtension
                                .genUIModelExtensionUnion().get(0),
                        inboundItem, logonInfo, null);
                return convInboundItemToDocExtUIModel(inboundItemUIModel, logonInfo);
            } catch (ServiceModuleProxyException | ServiceEntityConfigureException |
                     ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
                        e, InboundItem.NODENAME));
            }
        }
        return null;
    }

    @Override
    public String getAuthorizationResource() {
        return IServiceModelConstants.InboundDelivery;
    }

    @Override
    public ServiceSearchProxy getSearchProxy() {
        return inboundDeliverySearchProxy;
    }

}
