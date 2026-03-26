package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.repository.WarehouseStoreRepository;
import com.company.IntelligentPlatform.logistics.model.InboundDelivery;
import com.company.IntelligentPlatform.logistics.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.service.WarehouseSafetyWarnManager;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseStoreSetting;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchContextBuilder;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.ServiceEntityNodeLastUpdateTimeCompare;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WarehouseStoreManager extends ServiceEntityManager{

    public static String METHOD_ConvWarehouseStoreToUI = "convWarehouseStoreToUI";

    public static String METHOD_ConvUIToWarehouseStore = "convUIToWarehouseStore";

    public static String METHOD_ConvWarehouseToUI = "convWarehouseToUI";

    public static String METHOD_ConvInboundDeliveryToUI = "convInboundDeliveryToUI";

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected WarehouseManager warehouseManager;

    @Autowired
    protected WarehouseStoreIdHelper warehouseStoreIdHelper;

    @Autowired
    protected WarehouseStoreItemManager warehouseStoreItemManager;

    @Autowired
    protected WarehouseStoreSearchProxy warehouseStoreSearchProxy;

    @Autowired
    protected OutboundDeliveryManager outboundDeliveryManager;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected WarehouseStoreItemServiceUIModelExtension warehouseStoreItemServiceUIModelExtension;

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;
    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    protected WarehouseStoreRepository warehouseStoreDAO;

    @Autowired
    protected WarehouseStoreConfigureProxy warehouseStoreConfigureProxy;

    @Autowired
    protected WarehouseSafetyWarnManager warehouseSafetyWarnManager;

    protected Map<Integer, String> batchModeMap;

    protected Logger logger = LoggerFactory.getLogger(WarehouseStoreManager.class);
    @Autowired
    private OutboundDeliveryWarehouseItemManager outboundDeliveryWarehouseItemManager;

    @PostConstruct
    public void setServiceEntityDAO() {
        super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, warehouseStoreDAO));
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(warehouseStoreConfigureProxy);
    }

    public Map<Integer, String> getBatchModeMap()
            throws ServiceEntityInstallationException {
        if (this.batchModeMap == null) {
            this.batchModeMap = serviceDropdownListHelper.getUIDropDownMap(
                    WarehouseStoreItemSearchModel.class, "batchMode");
        }
        return this.batchModeMap;
    }

    @Override
    public ServiceEntityNode newRootEntityNode(String client) throws ServiceEntityConfigureException {
        WarehouseStore warehouseStore = (WarehouseStore) super
                .newRootEntityNode(client);
        String warehouseStoreId = warehouseStoreIdHelper
                .genDefaultId(client);
        warehouseStore.setId(warehouseStoreId);
        return warehouseStore;
    }

    @Override
    public ServiceEntityNode newEntityNode(ServiceEntityNode parentNode, String nodeName)
            throws ServiceEntityConfigureException {
        if(WarehouseStoreItem.NODENAME.equals(nodeName)){
            WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) super
                    .newEntityNode(parentNode, WarehouseStoreItem.NODENAME);
            String warehouseStoreItemId = warehouseStoreIdHelper
                    .genDefaultId(parentNode.getClient());
            warehouseStoreItem.setId(warehouseStoreItemId);
            // Copy ref warehouse uuid from parent to this node
            WarehouseStore warehouseStore = (WarehouseStore) parentNode;
            warehouseStoreItem.setRefWarehouseUUID(warehouseStore.getRefWarehouseUUID());
            return warehouseStoreItem;
        }
        return super.newEntityNode(parentNode, nodeName);
    }

    /**
     * Get store item by material SKU UUID and base warehouse UUID, And sort by
     * inboundDate
     *
     * @param skuUUID
     * @param baseUUID
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<WarehouseStoreItem> getStoreItemBySKUWarehouseOnline(
            String skuUUID, String baseUUID,
            List<ServiceEntityNode> rawStoreItemList)
            throws ServiceEntityConfigureException {
        if (ServiceCollectionsHelper.checkNullList(rawStoreItemList)) {
            return null;
        }
        List<WarehouseStoreItem> storeItemList = new ArrayList<>();
        for (ServiceEntityNode seNode : rawStoreItemList) {
            WarehouseStoreItem storeItem = (WarehouseStoreItem) seNode;
            if (!ServiceEntityStringHelper.checkNullString(baseUUID)) {
                if (!baseUUID.equals(storeItem.getRefWarehouseUUID())) {
                    continue;
                }
            }
            if (!ServiceEntityStringHelper.checkNullString(skuUUID)) {
                if (skuUUID.equals(storeItem.getRefMaterialSKUUUID())) {
                    storeItemList.add(storeItem);
                    continue;
                }
                if (skuUUID.equals(storeItem.getRefMaterialTemplateUUID())) {
                    storeItemList.add(storeItem);
                }
            }
        }
        storeItemList.sort(new ServiceEntityNodeLastUpdateTimeCompare());
        return storeItemList;
    }

    /**
     * Logic to get all possible warehouse store item list from sales contract
     *
     * @return
     */
    public List<ServiceEntityNode> getStoreItemListByDocMatItemList(int refWarehouseCategory,
                                                             List<ServiceEntityNode> docMatItemList)
            throws ServiceEntityConfigureException {
        /*
         * [Step 1] Get Warehouse UUID list, get raw warehouse storeItem UUIDList
         */
        if(ServiceCollectionsHelper.checkNullList(docMatItemList)){
            return null;
        }
        String client = docMatItemList.get(0).getClient();
        /*
         * [Step 2] Get Warehouse UUID list, get raw warehouse storeItem UUIDList
         */
        try {
            List<String> warehouseUUIDList = warehouseManager.getWarehouseUUIDList(refWarehouseCategory,
                    client);
            List<String> refMaterialSKUUUIDList =
                    docMatItemList.stream().map(serviceEntityNode -> {
                        DocMatItemNode docMatItemNode = (DocMatItemNode) serviceEntityNode;
                        return docMatItemNode.getRefMaterialSKUUUID();
                    }).collect(Collectors.toList());
            return getStoreItemList(refMaterialSKUUUIDList,
                    warehouseUUIDList, true);
        } catch (ServiceEntityConfigureException | MaterialException | NoSuchFieldException e) {
            throw new WarehouseStoreItemException(WarehouseStoreItemException.PARA_SYSTEM_WRONG, e.getMessage());
        }
    }

    /**
     * Core Method to calculate the available warehouse storage item amount by
     * deduce the pending out-bound delivery
     *
     * @param warehouseStoreItemList
     * @param homeOutItemUUID        : Home out-bound delivery item UUID, in case to exclude
     *                               current out-bound delivery, could be null
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    public StorageCoreUnit getAvailableStoreItemAmountUnion(
            List<WarehouseStoreItem> warehouseStoreItemList,
            String homeOutItemUUID) throws ServiceEntityConfigureException,
            MaterialException {
        if (warehouseStoreItemList == null
                || warehouseStoreItemList.size() == 0) {
            return null;
        }
        StorageCoreUnit storageCoreUnit1 = outboundDeliveryWarehouseItemManager
                .getAvailableStoreItemAmountUnion(new StoreAvailableStoreItemRequest(
                        (WarehouseStoreItem) warehouseStoreItemList.get(0),
                        homeOutItemUUID, false));
        if (warehouseStoreItemList.size() == 1) {
            return storageCoreUnit1;
        }
        for (int i = 1; i < warehouseStoreItemList.size(); i++) {
            StorageCoreUnit tempStorageUnit = outboundDeliveryWarehouseItemManager
                    .getAvailableStoreItemAmountUnion(new StoreAvailableStoreItemRequest(
                            (WarehouseStoreItem) warehouseStoreItemList.get(i),
                            homeOutItemUUID, false));
            storageCoreUnit1 = materialStockKeepUnitManager
                    .mergeStorageUnitCore(storageCoreUnit1, tempStorageUnit,
                            StorageCoreUnit.OPERATOR_ADD,
                            warehouseStoreItemList.get(i).getClient());
        }
        return storageCoreUnit1;
    }

    protected StorageCoreUnit getMergedStoreItem(
            WarehouseStoreSetting warehouseStoreSetting)
            throws ServiceEntityConfigureException, MaterialException {
        List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
        ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
        key1.setKeyValue(warehouseStoreSetting.getRootNodeUUID());
        key1.setKeyName(IServiceEntityNodeFieldConstant.ROOTNODEUUID);
        keyList.add(key1);
        ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure();
        key2.setKeyValue(warehouseStoreSetting.getRefMaterialSKUUUID());
        key2.setKeyName("refMaterialSKUUUID");
        keyList.add(key2);
        List<ServiceEntityNode> warehouseStoreItemList = getEntityNodeListByKeyList(
                keyList, WarehouseStoreItem.NODENAME, null);
        StorageCoreUnit storageCoreUnit = new StorageCoreUnit();
        storageCoreUnit.setRefMaterialSKUUUID(warehouseStoreSetting
                .getRefMaterialSKUUUID());
        if (warehouseStoreItemList != null && warehouseStoreItemList.size() > 0) {
            for (ServiceEntityNode seNode : warehouseStoreItemList) {
                WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) seNode;
                StorageCoreUnit tmpStorageCoreUnit = convertWarehouseStoreToStorageUnit(warehouseStoreItem);
                storageCoreUnit = materialStockKeepUnitManager
                        .mergeStorageUnitCore(storageCoreUnit,
                                tmpStorageCoreUnit,
                                StorageCoreUnit.OPERATOR_ADD,
                                warehouseStoreSetting.getClient());
            }
        }
        return storageCoreUnit;
    }

    @Override
    public ServiceSearchProxy getSearchProxy() {
        return warehouseStoreSearchProxy;
    }

    public List<ServiceEntityNode> searchStoreItemInternal(
            WarehouseStoreItemSearchModel searchModel, LogonInfo logonInfo)
            throws SearchConfigureException, ServiceEntityConfigureException, NodeNotFoundException, ServiceEntityInstallationException,
            MaterialException, AuthorizationException, LogonInfoException {
        SearchContextBuilder searchContextBuilder = SearchContextBuilder.genBuilder(logonInfo).searchModel(searchModel);
        return warehouseStoreSearchProxy.searchItemList(searchContextBuilder.build()).getResultList();
    }

    public StorageCoreUnit convertWarehouseStoreToStorageUnit(
            WarehouseStoreItem warehouseStoreItem) {
        StorageCoreUnit storageCoreUnit = new StorageCoreUnit();
        if (warehouseStoreItem != null) {
            storageCoreUnit.setRefMaterialSKUUUID(warehouseStoreItem
                    .getRefMaterialSKUUUID());
            storageCoreUnit.setAmount(warehouseStoreItem.getAmount());
            storageCoreUnit.setRefUnitUUID(warehouseStoreItem.getRefUnitUUID());
        }
        return storageCoreUnit;
    }

    public void convWarehouseStoreToUI(WarehouseStore warehouseStore,
                                        WarehouseStoreUIModel warehouseStoreUIModel)
            throws ServiceEntityInstallationException {
        convWarehouseStoreToUI(warehouseStore, warehouseStoreUIModel,
                null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convWarehouseStoreToUI(WarehouseStore warehouseStore,
                                       WarehouseStoreUIModel warehouseStoreUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        if (warehouseStore != null) {
            docFlowProxy.convDocumentToUI(warehouseStore,
                    warehouseStoreUIModel, logonInfo);
            warehouseStoreUIModel.setId(warehouseStore.getId());
            warehouseStoreUIModel.setName(warehouseStore.getName());
            warehouseStoreUIModel.setStatus(warehouseStore.getStatus());
            if (logonInfo != null) {
                Map<Integer, String> statusMap = warehouseStoreItemManager.initItemStatus(logonInfo
                        .getLanguageCode());
                warehouseStoreUIModel.setStatusValue(statusMap
                        .get(warehouseStore.getStatus()));
            }
            warehouseStoreUIModel.setRefWarehouseUUID(warehouseStore.getRefWarehouseUUID());
            warehouseStoreUIModel.setRefWarehouseAreaUUID(warehouseStore.getRefWarehouseAreaUUID());
            warehouseStoreUIModel.setGrossPrice(warehouseStore.getGrossPrice());
            warehouseStoreUIModel.setGrossPriceDisplay(warehouseStore.getGrossPriceDisplay());
            warehouseStoreUIModel.setNote(warehouseStore.getNote());
        }
    }

    public void convWarehouseToUI(Warehouse warehouse,
                                       WarehouseStoreUIModel warehouseStoreUIModel)
            throws ServiceEntityInstallationException {
        warehouseStoreUIModel.setRefWarehouseId(warehouse.getId());
        warehouseStoreUIModel.setRefWarehouseName(warehouse.getName());
    }

    public void convInboundDeliveryToUI(InboundDelivery inboundDelivery,
                                        WarehouseStoreUIModel warehouseStoreUIModel)
            throws ServiceEntityInstallationException {
        warehouseStoreUIModel.setRefInboundDeliveryId(inboundDelivery.getId());
    }

    /**
     * [Internal method] Convert from UI model to se model:warehouseStore
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToWarehouseStore(
            WarehouseStoreUIModel warehouseStoreUIModel,
            WarehouseStore rawEntity) {
        docFlowProxy.convUIToDocument(warehouseStoreUIModel, rawEntity);
        rawEntity.setGrossPrice(warehouseStoreUIModel.getGrossPrice());
        rawEntity.setGrossPriceDisplay(warehouseStoreUIModel.getGrossPriceDisplay());
        if(!ServiceEntityStringHelper.checkNullString(warehouseStoreUIModel.getRefWarehouseUUID())){
            rawEntity.setRefWarehouseUUID(warehouseStoreUIModel.getRefWarehouseUUID());
        }
        if(!ServiceEntityStringHelper.checkNullString(warehouseStoreUIModel.getRefWarehouseAreaUUID())){
            rawEntity.setRefWarehouseAreaUUID(warehouseStoreUIModel.getRefWarehouseAreaUUID());
        }
        rawEntity.setNote(warehouseStoreUIModel.getNote());
    }

    /**
     * Method to get the unique warehouse store item log from persistence.
     *
     * @param documentType
     * @param documentUUID
     * @param refMaterialSKUUUID
     * @return
     * @throws ServiceEntityConfigureException
     */
    public WarehouseStoreItemLog getStoreItemLog(int documentType,
                                                 String documentUUID, String refMaterialSKUUUID, String storeItemUUID)
            throws ServiceEntityConfigureException {
        List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
        ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
        key1.setKeyName("documentUUID");
        key1.setKeyValue(documentUUID);
        keyList.add(key1);
        ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure();
        key2.setKeyName("documentType");
        key2.setKeyValue(documentType);
        keyList.add(key2);
        ServiceBasicKeyStructure key3 = new ServiceBasicKeyStructure();
        key3.setKeyName("refMaterialSKUUUID");
        key3.setKeyValue(refMaterialSKUUUID);
        keyList.add(key3);
        ServiceBasicKeyStructure key4 = new ServiceBasicKeyStructure();
        key4.setKeyName(IServiceEntityNodeFieldConstant.PARENTNODEUUID);
        key4.setKeyValue(storeItemUUID);
        keyList.add(key4);
        return (WarehouseStoreItemLog) getEntityNodeByKeyList(keyList,
                WarehouseStoreItemLog.NODENAME, null);
    }

    /**
     * General method to update / generate store item log when store item is updated by other doc material item.
     * @param docMatItemNode
     * @param warehouseStoreItem
     * @param warehouseStoreItemBack
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    public WarehouseStoreItemLog updateStoreItemLogFromDocItem(
            DocMatItemNode docMatItemNode,
            WarehouseStoreItem warehouseStoreItem,
            WarehouseStoreItem warehouseStoreItemBack) throws ServiceEntityConfigureException,
            MaterialException {
        /*
         * [Step1] get all the warehouse store item list from this warehouse and
         * this material
         */
        WarehouseStoreItemLog warehouseStoreItemLog = getStoreItemLog(
                docMatItemNode.getHomeDocumentType(),
                docMatItemNode.getRootNodeUUID(),
                docMatItemNode.getRefMaterialSKUUUID(),
                        warehouseStoreItem.getUuid());
        if (warehouseStoreItemLog == null) {
            warehouseStoreItemLog = (WarehouseStoreItemLog) newEntityNode(warehouseStoreItem,
                            WarehouseStoreItemLog.NODENAME);
        }
        warehouseStoreItemLog.setRefUnitUUID(docMatItemNode
                .getRefUnitUUID());
        if (!ServiceEntityStringHelper.checkNullString(docMatItemNode
                .getRefUUID())) {
            warehouseStoreItemLog.setRefUUID(docMatItemNode.getRefUUID());
            warehouseStoreItemLog.setRefMaterialSKUUUID(docMatItemNode
                    .getRefUUID());
        }
        if (!ServiceEntityStringHelper.checkNullString(docMatItemNode
                .getRefMaterialSKUUUID())) {
            warehouseStoreItemLog.setRefUUID(docMatItemNode.getRefUUID());
            warehouseStoreItemLog.setRefMaterialSKUUUID(docMatItemNode
                    .getRefUUID());
        }
        warehouseStoreItemLog
                .setDocumentType(docMatItemNode.getHomeDocumentType());
        warehouseStoreItemLog.setDocumentUUID(docMatItemNode.getUuid());
        // Record the old data
        if (warehouseStoreItemBack == null){
            warehouseStoreItemLog.setAmount(0);
            warehouseStoreItemLog.setRefUnitUUID(docMatItemNode
                    .getRefUnitUUID());
        } else {
            warehouseStoreItemLog.setAmount(warehouseStoreItemBack.getAmount());
            warehouseStoreItemLog.setRefUnitUUID(docMatItemNode
                    .getRefUnitUUID());
            if (ServiceEntityStringHelper.checkNullString(warehouseStoreItemBack
                    .getRefUnitUUID())) {
                warehouseStoreItemLog.setRefUnitUUID(warehouseStoreItem
                        .getRefUnitUUID());
            }
            warehouseStoreItemLog.setWeight(warehouseStoreItemBack.getWeight());
            warehouseStoreItemLog.setVolume(warehouseStoreItemBack.getVolume());
        }
        warehouseStoreItemLog.setDeclaredValue(warehouseStoreItem
                .getDeclaredValue());
        // Record the updated data
        warehouseStoreItemLog.setUpdatedAmount(warehouseStoreItem.getAmount());
        warehouseStoreItemLog.setUpdatedRefUnitUUID(warehouseStoreItem
                .getRefUnitUUID());
        warehouseStoreItemLog.setUpdatedWeight(warehouseStoreItem.getWeight());
        warehouseStoreItemLog.setUpdatedVolume(warehouseStoreItem.getVolume());
        warehouseStoreItemLog.setUpdatedDeclaredValue(warehouseStoreItem
                .getDeclaredValue());
        return warehouseStoreItemLog;
    }

    /**
     * Short way to get store item list by template SKU UUID and refWarehouse
     * UUID or
     *
     * @param refTemplateSKUUUID
     * @param refWarehouseUUID
     * @param refWarehouseUUIDArray
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    public List<ServiceEntityNode> getStoreItemList(String refTemplateSKUUUID,
                                                    String refWarehouseUUID, List<String> refWarehouseUUIDArray, boolean inStockFlag)
            throws ServiceEntityConfigureException, MaterialException {
        List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
        ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
        key1.setKeyValue(refTemplateSKUUUID);
        key1.setKeyName("refMaterialTemplateUUID");
        keyList.add(key1);
        ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure();
        key2.setKeyValue(refWarehouseUUID);
        key2.setKeyName(WarehouseStore.FIELD_REF_WAREHOUSE_UUID);
        key2.setMultipleValueList(refWarehouseUUIDArray);
        keyList.add(key2);
        if (inStockFlag) {
            ServiceBasicKeyStructure key3 = new ServiceBasicKeyStructure(WarehouseStoreItem.STATUS_INSTOCK,
                    IDocItemNodeFieldConstant.itemStatus);
            keyList.add(key3);
        }
        return getEntityNodeListByKeyList(
                keyList, WarehouseStoreItem.NODENAME, null);
    }

    /**
     * Short way to get store item list by template SKU UUID and refwarehouse
     * UUID or
     *
     * @param refMaterialSKUUUIDList
     * @param warehouseUUIDList
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    public List<ServiceEntityNode> getStoreItemList(List<String> refMaterialSKUUUIDList,
                                                    List<String> warehouseUUIDList,
                                                    boolean inStockFlag)
            throws ServiceEntityConfigureException, MaterialException {
        List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
        ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
        key1.setKeyName("refMaterialTemplateUUID");
        key1.setMultipleValueList(refMaterialSKUUUIDList);
        keyList.add(key1);
        ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure();
        key2.setKeyName(WarehouseStore.FIELD_REF_WAREHOUSE_UUID);
        key2.setMultipleValueList(warehouseUUIDList);
        keyList.add(key2);
        if (inStockFlag) {
            ServiceBasicKeyStructure key3 = new ServiceBasicKeyStructure(WarehouseStoreItem.STATUS_INSTOCK, IDocItemNodeFieldConstant.itemStatus);
            keyList.add(key3);
        }
        return getEntityNodeListByKeyList(
                keyList, WarehouseStoreItem.NODENAME, null);
    }

    public List<WarehouseStoreItem> convStoreItemListFormat(
            List<ServiceEntityNode> storeItemList) {
        if (ServiceCollectionsHelper.checkNullList(storeItemList)) {
            return null;
        }
        List<WarehouseStoreItem> warehouseStoreItemList = new ArrayList<WarehouseStoreItem>();
        for (ServiceEntityNode seNode : storeItemList) {
            warehouseStoreItemList.add((WarehouseStoreItem) seNode);
        }
        return warehouseStoreItemList;
    }

    public ServiceDocumentExtendUIModel convWarehouseStoreItemToDocExtUIModel(
            WarehouseStoreItemUIModel warehouseStoreItemUIModel,
            LogonInfo logonInfo) throws ServiceEntityInstallationException {
        ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
        docFlowProxy.convDocMatItemUIToDocExtUIModel(warehouseStoreItemUIModel,
                serviceDocumentExtendUIModel, logonInfo,
                IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM);
        serviceDocumentExtendUIModel.setRefUIModel(warehouseStoreItemUIModel);
        serviceDocumentExtendUIModel.setId(warehouseStoreItemUIModel
                .getRefWarehouseId());
        // Logic of reference Date
        String referenceDate = warehouseStoreItemUIModel.getRecordStoreDate();
        serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
        return serviceDocumentExtendUIModel;
    }

    @Override
    public ServiceDocumentExtendUIModel convToDocumentExtendUIModel(
            ServiceEntityNode seNode, LogonInfo logonInfo) {
        if (seNode == null) {
            return null;
        }
        if (WarehouseStoreItem.NODENAME.equals(seNode.getNodeName())) {
            WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) seNode;
            try {
                WarehouseStoreItemUIModel warehouseStoreItemUIModel = (WarehouseStoreItemUIModel) genUIModelFromUIModelExtension(
                        WarehouseStoreItemUIModel.class,
                        warehouseStoreItemServiceUIModelExtension
                                .genUIModelExtensionUnion().get(0),
                        warehouseStoreItem, logonInfo, null);
                ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = convWarehouseStoreItemToDocExtUIModel(warehouseStoreItemUIModel, logonInfo);
                return serviceDocumentExtendUIModel;
            } catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
                        e, WarehouseStoreItem.NODENAME));
            }
        }
        return null;
    }

}
