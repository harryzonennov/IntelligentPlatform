package com.company.IntelligentPlatform.logistics.service;

import java.text.ParseException;
import java.util.*;

import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreItemSearchModel;
import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreItemServiceUIModelExtension;
import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreItemUIModel;
import com.company.IntelligentPlatform.logistics.service.InboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.model.StoreAvailableStoreItemRequest;
import com.company.IntelligentPlatform.logistics.model.WarehouseStore;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.common.service.IndividualCustomerManager;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialManager;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StandardMaterialUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ReserveDocItemProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNodeLastUpdateTimeCompare;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * Super class for handling warehouse store item.
 *
 * @author Zhang, Hang
 */
@Service
public class WarehouseStoreItemManager {

    public static final String METHOD_ConvWarehouseStoreItemToUI = "convWarehouseStoreItemToUI";

    public static final String METHOD_ConvWarehouseAreaToStoreItemUI = "convWarehouseAreaToStoreItemUI";

    public static final String METHOD_ConvUIToWarehouseStoreItem = "convUIToWarehouseStoreItem";

    public static final String METHOD_ConvWarehouseToStoreItemUI = "convWarehouseToStoreItemUI";

    public static final String METHOD_ConvInboundDeliveryToStoreItemUI = "convInboundDeliveryToStoreItemUI";

    public static final String METHOD_ConvOutboundItemToStoreItemUI = "convOutboundItemToStoreItemUI";

    public static final String METHOD_ConvInboundItemToStoreItemUI = "convInboundItemToStoreItemUI";

    public static final String METHOD_ConvOutboundDeliveryToStoreItemUI = "convOutboundDeliveryToStoreItemUI";

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected MaterialManager materialManager;

    @Autowired
    protected WarehouseStoreManager warehouseStoreManager;

    @Autowired
    protected WarehouseManager warehouseManager;

    @Autowired
    protected CorporateCustomerManager corporateCustomerManager;

    @Autowired
    protected IndividualCustomerManager individualCustomerManager;

    @Autowired
    protected StandardMaterialUnitManager standardMaterialUnitManager;

    @Autowired
    protected InboundDeliveryManager inboundDeliveryManager;

    @Autowired
    protected OutboundDeliveryManager outboundDeliveryManager;

    @Autowired
    protected WarehouseStoreItemServiceUIModelExtension warehouseStoreItemServiceUIModelExtension;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected ReserveDocItemProxy reserveDocItemProxy;

    private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

    private Map<String, Map<Integer, String>> reservedStatusMapLan = new HashMap<>();

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), WarehouseStore.NODENAME,
                        request.getUuid(), WarehouseStoreItem.NODENAME, warehouseStoreManager);
        docPageHeaderInputPara.setGenBaseModelList(
                (DocPageHeaderModelProxy.GenBaseModelList<WarehouseStore>) warehouseStore -> {
                    // How to get the base page header model list
                    return docPageHeaderModelProxy.getDocPageHeaderModelList(warehouseStore, null);
                });
        docPageHeaderInputPara.setGenHomePageModel(
                (DocPageHeaderModelProxy.GenHomePageModel<WarehouseStoreItem>) (warehouseStoreItem, pageHeaderModel) ->
                        docPageHeaderModelProxy.getDefDocMaterialItemPageHeaderModel(warehouseStoreItem, pageHeaderModel));
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
    }

    public Map<Integer, String> initItemStatus(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.statusMapLan, WarehouseStoreItemUIModel.class,
                IDocItemNodeFieldConstant.itemStatus);
    }

    public Map<Integer, String> initReservedStatus(String languageCode)
            throws ServiceEntityInstallationException {
        return reserveDocItemProxy.getReservedStatusMap(languageCode);
    }

    /**
     * Utility method to group store item list by warehouse
     *
     * @param warehouseStoreItemList
     * @return
     */
    public static Map<String, List<ServiceEntityNode>> groupStoreItemByWarehouse(List<ServiceEntityNode> warehouseStoreItemList) {
        if (ServiceCollectionsHelper.checkNullList(warehouseStoreItemList)) {
            return null;
        }
        Map<String, List<ServiceEntityNode>> resultMap = new HashMap<>();
        for (ServiceEntityNode seNode : warehouseStoreItemList) {
            WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) seNode;
            if (resultMap.containsKey(warehouseStoreItem.getRefWarehouseUUID())) {
                List<ServiceEntityNode> tempStoreList = resultMap.get(warehouseStoreItem.getRefWarehouseUUID());
                tempStoreList = tempStoreList == null ? new ArrayList<>() : tempStoreList;
                ServiceCollectionsHelper.mergeToList(tempStoreList, warehouseStoreItem);
            } else {
                List<ServiceEntityNode> tempStoreList = new ArrayList<>();
                tempStoreList.add(warehouseStoreItem);
                ServiceCollectionsHelper.mergeToList(tempStoreList, warehouseStoreItem);
                resultMap.put(warehouseStoreItem.getRefWarehouseUUID(), tempStoreList);
            }
        }
        return resultMap;
    }

    /**
     * Core algorithm to check weather it is possible to update warehouse store
     * item amount, if not meet the update request,
     * <code>WarehouseStoreItemException</code> will raise. Meanwhile, the
     * amount and unit information from warehouseStoreItem will also be updated,
     * and the warehouse store item update is online, not updated to
     * persistence.
     *
     * @param requestUpdateCoreUnit : Request Unit, including the amount and unit information to
     *                              update warehouse store item
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    public StorageCoreUnit checkWarehouseStoreItemAvailableCore(StorageCoreUnit requestUpdateCoreUnit,
                                                                WarehouseStoreItem warehouseStoreItem,
             StorageCoreUnit availableStoreUnit)
            throws WarehouseStoreItemException, ServiceEntityConfigureException, MaterialException {
        /*
         * [Step1] Gen local available Store unit
         */
        StorageCoreUnit availableStoreUnitLocal = (StorageCoreUnit) availableStoreUnit.clone();
        if (availableStoreUnitLocal == null) {
            availableStoreUnitLocal = new StorageCoreUnit(warehouseStoreItem.getRefMaterialSKUUUID(),
                    warehouseStoreItem.getRefUnitUUID(), warehouseStoreItem.getAmount());
        }
        /*
         * [Step2] Compare request and available
         */
        StorageCoreUnit compareResult = materialStockKeepUnitManager.mergeStorageUnitCore(requestUpdateCoreUnit,
                availableStoreUnitLocal, StorageCoreUnit.OPERATOR_MINUS, warehouseStoreItem.getClient());
        if (compareResult.getAmount() > 0) {
            // in case request is larger than available update, return left amount
            return compareResult;
        } else {
            // in case request can be meet, reduce warehouse store item, always be zero
            minusWarehouseStoreItemLoc(warehouseStoreItem, requestUpdateCoreUnit);
            requestUpdateCoreUnit.setAmount(0);
            return requestUpdateCoreUnit;
        }
    }

        /**
         * Core algorithm to check weather it is possible to update warehouse store
         * item amount, if not meet the update request,
         * <code>WarehouseStoreItemException</code> will raise. Meanwhile, the
         * amount and unit information from warehouseStoreItem will also be updated,
         * and the warehouse store item update is online, not updated to
         * persistence.
         *
         * @param requestUpdateCoreUnit : Request Unit, including the amount and unit information to
         *                              update warehouse store item
         * @param checkOutboundFlag     : Flag about weather to check out pending out-bound delivery
         *                              orders.
         * @throws ServiceEntityConfigureException
         * @throws MaterialException
         */
    public void checkAndUpdateWarehouseStoreItemAmountCore(StorageCoreUnit requestUpdateCoreUnit,
                                                           boolean checkOutboundFlag,
                                                           StoreAvailableStoreItemRequest storeAvailableStoreItemRequest, StorageCoreUnit availableStoreUnit)
            throws WarehouseStoreItemException, ServiceEntityConfigureException, MaterialException {
        /*
         * [Step1] Gen local available Store unit
         */
        WarehouseStoreItem warehouseStoreItem = storeAvailableStoreItemRequest.getWarehouseStoreItem();
        StorageCoreUnit availableStoreUnitLocal = availableStoreUnit;
        if (availableStoreUnitLocal == null) {
            availableStoreUnitLocal = new StorageCoreUnit(warehouseStoreItem.getRefMaterialSKUUUID(),
                    warehouseStoreItem.getRefUnitUUID(), warehouseStoreItem.getAmount());
        }
        /*
         * [Step2] Compare request and avaliable
         */
        StorageCoreUnit compareResult = materialStockKeepUnitManager.mergeStorageUnitCore(requestUpdateCoreUnit,
                availableStoreUnitLocal, StorageCoreUnit.OPERATOR_MINUS, warehouseStoreItem.getClient());
        if (compareResult.getAmount() > 0) {
            // in case request is larger than available
            requestUpdateCoreUnit = compareResult;
            String resStoreAmountLabel =
                    materialStockKeepUnitManager.getAmountLabel(availableStoreUnitLocal.getRefMaterialSKUUUID(),
                            availableStoreUnitLocal.getRefUnitUUID(), availableStoreUnitLocal.getAmount(),
                            warehouseStoreItem.getClient());
            throw new WarehouseStoreItemException(WarehouseStoreItemException.PARA_OVER_AMOUNT, resStoreAmountLabel);
        } else {
            // in case request can be meet
            minusWarehouseStoreItemLoc(warehouseStoreItem, requestUpdateCoreUnit);
            requestUpdateCoreUnit.setAmount(0);
        }
    }

    public void minusWarehouseStoreItemLoc(WarehouseStoreItem warehouseStoreItem, StorageCoreUnit requestStoreUnit) throws MaterialException, ServiceEntityConfigureException {
        StorageCoreUnit warehouseStoreRequestUnit = new StorageCoreUnit(warehouseStoreItem.getRefMaterialSKUUUID(),
                warehouseStoreItem.getRefUnitUUID(), warehouseStoreItem.getAmount());
        StorageCoreUnit result = materialStockKeepUnitManager.mergeStorageUnitCore(warehouseStoreRequestUnit,
                requestStoreUnit, StorageCoreUnit.OPERATOR_MINUS, warehouseStoreItem.getClient());
        warehouseStoreItem.setAmount(result.getAmount());
        warehouseStoreItem.setRefUnitUUID(result.getRefUnitUUID());
    }

    /**
     * Core logic to check weather it is possible to update warehouse store item
     * amount and update the refreshed amount and unit into warehouseStoreItem
     *
     * @param warehouseStoreItem : warehouse store item, Attention: this item should be cloned
     *                           before use!!
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    public StorageCoreUnit convertWarehouseStoreToStorageUnit(WarehouseStoreItem warehouseStoreItem) {
        StorageCoreUnit storageCoreUnit = new StorageCoreUnit();
        if (warehouseStoreItem != null) {
            storageCoreUnit.setRefMaterialSKUUUID(warehouseStoreItem.getRefMaterialTemplateUUID());
            storageCoreUnit.setAmount(warehouseStoreItem.getAmount());
            storageCoreUnit.setRefUnitUUID(warehouseStoreItem.getRefUnitUUID());
        }
        return storageCoreUnit;
    }

    /**
     * Logic to get all the [In Stock] status store item list
     *
     * @param warehouseUUIDList
     * @param client
     * @return
     */
    public List<ServiceEntityNode> getInStockStoreItemList(List<String> warehouseUUIDList, String client) throws ServiceEntityConfigureException {
        ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
        key1.setKeyName(WarehouseStore.FIELD_REF_WAREHOUSE_UUID);
        key1.setMultipleValueList(warehouseUUIDList);
        ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure(WarehouseStoreItem.STATUS_INSTOCK, IDocItemNodeFieldConstant.itemStatus);
        List<ServiceBasicKeyStructure> keyList = ServiceCollectionsHelper.asList(key1, key2);
        return warehouseStoreManager.getEntityNodeListByKeyList(keyList, WarehouseStoreItem.NODENAME, client, null);
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
    public List<WarehouseStoreItem> getInStockStoreItemBySKUWarehouse(String skuUUID,
                                                                      String baseUUID) throws ServiceEntityConfigureException {
        return getInStockStoreItemBySKUWarehouse(skuUUID, baseUUID, null);
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
    public List<WarehouseStoreItem> getInStockStoreItemBySKUWarehouse(String skuUUID,
                                                                      String baseUUID, String refWarehouseAreaUUID) throws ServiceEntityConfigureException {
        List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
        ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure(skuUUID, "refMaterialTemplateUUID");
        keyList.add(key1);
        ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure(baseUUID,
                WarehouseStore.FIELD_REF_WAREHOUSE_UUID);
        keyList.add(key2);
        ServiceBasicKeyStructure key3 = new ServiceBasicKeyStructure(WarehouseStoreItem.STATUS_INSTOCK, IDocItemNodeFieldConstant.itemStatus);
        keyList.add(key3);
        if (!ServiceEntityStringHelper.checkNullString(refWarehouseAreaUUID)) {
            ServiceBasicKeyStructure key4 = new ServiceBasicKeyStructure(refWarehouseAreaUUID, "refWarehouseAreaUUIDs");
            keyList.add(key4);
        }
        List<ServiceEntityNode> result = warehouseStoreManager.getEntityNodeListByKeyList(keyList,
                WarehouseStoreItem.NODENAME, null);
        List<WarehouseStoreItem> storeItemList = new ArrayList<>();
        for (ServiceEntityNode seNode : result) {
            storeItemList.add((WarehouseStoreItem) seNode);
        }
        storeItemList.sort(new ServiceEntityNodeLastUpdateTimeCompare());
        return storeItemList;
    }

    public void convWarehouseStoreItemToUI(
            WarehouseStoreItem warehouseStoreItem,
            WarehouseStoreItemUIModel warehouseStoreItemUIModel)
            throws ServiceEntityInstallationException {
        convWarehouseStoreItemToUI(warehouseStoreItem, warehouseStoreItemUIModel, null, null);
    }

    /**
     * Utlity method to batch convert list of warehouse store list into UIModel list
     * @param rawList
     * @param logonInfo
     * @param batchModel
     * @return
     */
    public List<WarehouseStoreItemUIModel> getStoreModuleListCore(List<ServiceEntityNode> rawList,
                                                                   LogonInfo logonInfo, int batchModel) {
        return getStoreModuleListCore(rawList, logonInfo, batchModel, null, 0);
    }

    public List<WarehouseStoreItemUIModel> getStoreModuleListCore(List<ServiceEntityNode> rawList,
                                                                  LogonInfo logonInfo, int batchModel,
                                                                  String reservedMatItemUUID, int reservedDocType) {
        List<WarehouseStoreItemUIModel> warehouseStoreItemList = new ArrayList<>();
        if (ServiceCollectionsHelper.checkNullList(rawList)){
            return null;
        }
        for (ServiceEntityNode rawNode : rawList) {
            WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) rawNode;
            try {
                WarehouseStoreItemUIModel.ConvertMeta convertMeta = new WarehouseStoreItemUIModel.ConvertMeta(true,
                        batchModel);
                if(!ServiceEntityStringHelper.checkNullString(reservedMatItemUUID)){
                    convertMeta.setReservedMatItemUUID(reservedMatItemUUID);
                    convertMeta.setReservedDocType(reservedDocType);
                }
                List<ServiceModuleConvertPara> addtionalConvertParaList =
                        ServiceUIModuleProxy.genSimpleConvertPara(WarehouseStoreItem.NODENAME, convertMeta);
                WarehouseStoreItemUIModel warehouseStoreItemUIModel =
                        (WarehouseStoreItemUIModel) warehouseStoreManager.genUIModelFromUIModelExtension(
                                WarehouseStoreItemUIModel.class,
                                warehouseStoreItemServiceUIModelExtension
                                        .genUIModelExtensionUnion().get(0),
                                warehouseStoreItem, logonInfo, addtionalConvertParaList);
                warehouseStoreItemList.add(warehouseStoreItemUIModel);
            } catch (Exception e) {
                // Do nothing, Just continue
            }
        }
        return warehouseStoreItemList;
    }

    public void convWarehouseStoreToItemUI(WarehouseStore warehouseStore,
                                           WarehouseStoreItemUIModel warehouseStoreItemUIModel){
        warehouseStoreItemUIModel.setRefWarehouseUUID(warehouseStore.getRefWarehouseUUID());
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     */
    public void convWarehouseStoreItemToUI(
            WarehouseStoreItem warehouseStoreItem,
            WarehouseStoreItemUIModel warehouseStoreItemUIModel,
            WarehouseStoreItemUIModel.ConvertMeta convertMeta, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        try {
            convWarehouseStoreItemToUICore(warehouseStoreItem,
                    warehouseStoreItemUIModel, logonInfo);
            if(convertMeta != null && !ServiceEntityStringHelper.checkNullString(convertMeta.getReservedMatItemUUID())) {
                int reservedStatus = reserveDocItemProxy.getReservedStatus(convertMeta.getReservedMatItemUUID(),
                        convertMeta.getReservedDocType(),warehouseStoreItem);
                warehouseStoreItemUIModel.setReservedStatus(reservedStatus);
                if(logonInfo != null){
                    Map<Integer, String> reservedStatusMap = initReservedStatus(logonInfo.getLanguageCode());
                    warehouseStoreItemUIModel.setReservedStatusValue(reservedStatusMap.get(reservedStatus));
                }
            }
            try {
                if(convertMeta != null && convertMeta.getBatchMode() == WarehouseStoreItemSearchModel.BATCH_MODE_MERGE) {
                    StorageCoreUnit availableStoreCoreUnit = new StorageCoreUnit();
                    List<ServiceEntityNode> storeItemList = warehouseStoreManager.getStoreItemList(
                            warehouseStoreItem.getRefMaterialTemplateUUID(),
                            warehouseStoreItem.getRefWarehouseUUID(), null, false);
                    List<WarehouseStoreItem> warehouseStoreItemList =
                            warehouseStoreManager.convStoreItemListFormat(storeItemList);
                    availableStoreCoreUnit = warehouseStoreManager.getAvailableStoreItemAmountUnion(
                            warehouseStoreItemList, null);
                    if (availableStoreCoreUnit != null) {
                        warehouseStoreItemUIModel
                                .setAvailableAmount(availableStoreCoreUnit
                                        .getAmount());
                        String availableAmountLabel = materialStockKeepUnitManager
                                .getAmountLabel(
                                        warehouseStoreItem
                                                .getRefMaterialSKUUUID(),
                                        availableStoreCoreUnit.getRefUnitUUID(),
                                        availableStoreCoreUnit.getAmount(),
                                        warehouseStoreItem.getClient());
                        warehouseStoreItemUIModel
                                .setAvailableAmountLabel(availableAmountLabel);
                    } else {
                        warehouseStoreItemUIModel.setAvailableAmount(0);
                    }
                }else{
                    StorageCoreUnit availableStoreCoreUnit = outboundDeliveryManager.getAvailableStoreItemAmountUnion(new StoreAvailableStoreItemRequest(
                                    warehouseStoreItem, null, false));
                    if (availableStoreCoreUnit != null) {
                        warehouseStoreItemUIModel
                                .setAvailableAmount(availableStoreCoreUnit
                                        .getAmount());
                        String availableAmountLabel = materialStockKeepUnitManager
                                .getAmountLabel(availableStoreCoreUnit.getRefMaterialSKUUUID(), availableStoreCoreUnit.getRefUnitUUID(),
                                        availableStoreCoreUnit.getAmount(), warehouseStoreItem.getClient());
                        warehouseStoreItemUIModel.setAvailableAmountLabel(availableAmountLabel);
                    } else {
                        warehouseStoreItemUIModel.setAvailableAmount(0);
                    }
                }
            } catch (MaterialException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, null));
            }
        } catch (ServiceEntityConfigureException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, null));
        } catch (DocActionException | MaterialException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, null));
        }
    }

    public void convWarehouseStoreItemToUICore(
            WarehouseStoreItem warehouseStoreItem,
            WarehouseStoreItemUIModel warehouseStoreItemUIModel,
            LogonInfo logonInfo) throws ServiceEntityInstallationException,
            ServiceEntityConfigureException {
        if (warehouseStoreItem != null) {
            docFlowProxy.convDocMatItemToUI(warehouseStoreItem,
                    warehouseStoreItemUIModel, logonInfo);
            warehouseStoreItemUIModel.setId(warehouseStoreItem.getId());
            warehouseStoreItemUIModel.setVolume(warehouseStoreItem.getVolume());
            warehouseStoreItemUIModel.setWeight(warehouseStoreItem.getWeight());
            warehouseStoreItemUIModel.setDeclaredValue(warehouseStoreItem
                    .getDeclaredValue());
            if (!ServiceEntityStringHelper.checkNullString(warehouseStoreItem
                    .getRefMaterialSKUId())) {
                warehouseStoreItemUIModel
                        .setRefMaterialSKUId(warehouseStoreItem
                                .getRefMaterialSKUId());
            }
            if (!ServiceEntityStringHelper.checkNullString(warehouseStoreItem
                    .getRefMaterialSKUName())) {
                warehouseStoreItemUIModel
                        .setRefMaterialSKUName(warehouseStoreItem
                                .getRefMaterialSKUName());
            }
            if (!ServiceEntityStringHelper.checkNullString(warehouseStoreItem
                    .getPackageStandard())) {
                warehouseStoreItemUIModel.setPackageStandard(warehouseStoreItem
                        .getPackageStandard());
            }
            if (!ServiceEntityStringHelper.checkNullString(warehouseStoreItem
                    .getRefUnitName())) {
                // In case manual mode
                warehouseStoreItemUIModel.setRefUnitName(warehouseStoreItem
                        .getRefUnitName());
                warehouseStoreItemUIModel.setAmountLabel(warehouseStoreItem
                        .getAmount() + warehouseStoreItem.getRefUnitName());
            } else {
                try {
                    String amountLabel = materialStockKeepUnitManager
                            .getAmountLabel(
                                    warehouseStoreItem.getRefMaterialSKUUUID(),
                                    warehouseStoreItem.getRefUnitUUID(),
                                    warehouseStoreItem.getAmount(),
                                    warehouseStoreItem.getClient());
                    warehouseStoreItemUIModel.setAmountLabel(amountLabel);
                } catch (MaterialException e) {
                    // skip this
                    logger.error(ServiceEntityStringHelper
                            .genDefaultErrorMessage(e, null));
                }
            }
            if (warehouseStoreItem.getProductionDate() != null) {
                warehouseStoreItemUIModel
                        .setProductionDate(DefaultDateFormatConstant.DATE_FORMAT
                                .format(warehouseStoreItem.getProductionDate()));
            }
            if(logonInfo != null){
                Map<Integer, String> storeItemSatusMap = initItemStatus(logonInfo.getLanguageCode());
                warehouseStoreItemUIModel.setItemStatusValue(storeItemSatusMap
                        .get(warehouseStoreItem.getItemStatus()));
            }
            warehouseStoreItemUIModel.setRefWarehouseUUID(warehouseStoreItem.getRefWarehouseUUID());
            warehouseStoreItemUIModel.setRefMaterialSKUId(warehouseStoreItem
                    .getRefMaterialSKUId());
            warehouseStoreItemUIModel.setRefMaterialSKUName(warehouseStoreItem
                    .getRefMaterialSKUName());
            warehouseStoreItemUIModel.setRefShelfNumberId(warehouseStoreItem
                    .getRefShelfNumberId());
            warehouseStoreItemUIModel.setProductionPlace(warehouseStoreItem
                    .getProductionPlace());
            if (warehouseStoreItem.getLastUpdateTime() != null) {
                warehouseStoreItemUIModel
                        .setLastUpdateTime(DefaultDateFormatConstant.DATE_FORMAT
                                .format(warehouseStoreItem.getLastUpdateTime()));
            }
            // calculate store date
            int storeDay = getWarehouseStoreDay(warehouseStoreItem);
            warehouseStoreItemUIModel.setStoreDay(storeDay);
            inboundDeliveryManager.setWarehouseStoreInboundInfo(
                    warehouseStoreItemUIModel,
                    warehouseStoreItem.getPrevDocMatItemUUID());
            outboundDeliveryManager
                    .setWarehouseStoreOutboundInfo(
                            warehouseStoreItemUIModel,
                            warehouseStoreItem.getUuid());

        }
    }

    /**
     * Logic to calculate warehouse store item storeDay
     *
     * @return
     */
    public int getWarehouseStoreDay(WarehouseStoreItem warehouseStoreItem) {
        // TODO To refine this logic to get more accurate result
        return (int) ServiceEntityDateHelper.getDiffDays(
                warehouseStoreItem.getCreatedTime(), warehouseStoreItem.getLastUpdateTime());

    }

    public void convWarehouseAreaToStoreItemUI(WarehouseArea warehouseArea,
                                               WarehouseStoreItemUIModel warehouseStoreItemUIModel) {
        if (warehouseArea != null) {
            warehouseStoreItemUIModel.setRefWarehouseAreaId(warehouseArea
                    .getId());
        }
    }

    public void convUIToWarehouseStoreItem(
            WarehouseStoreItemUIModel warehouseStoreItemUIModel,
            WarehouseStoreItem rawEntity) {
        docFlowProxy.convUIToDocMatItem(warehouseStoreItemUIModel, rawEntity);
        rawEntity.setVolume(warehouseStoreItemUIModel.getVolume());
        rawEntity.setWeight(warehouseStoreItemUIModel.getWeight());
        rawEntity.setAmount(warehouseStoreItemUIModel.getAmount());
        rawEntity
                .setDeclaredValue(warehouseStoreItemUIModel.getDeclaredValue());
        rawEntity.setProductionBatchNumber(warehouseStoreItemUIModel
                .getProductionBatchNumber());
        if (!ServiceEntityStringHelper
                .checkNullString(warehouseStoreItemUIModel.getProductionDate())) {
            try {
                rawEntity
                        .setProductionDate(DefaultDateFormatConstant.DATE_FORMAT.parse(warehouseStoreItemUIModel
                                        .getProductionDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } catch (ParseException e) {
                // do nothing
            }
        }
        if (!ServiceEntityStringHelper
                .checkNullString(warehouseStoreItemUIModel
                        .getRefMaterialSKUId())) {
            rawEntity.setRefMaterialSKUId(warehouseStoreItemUIModel
                    .getRefMaterialSKUId());
        }
        if (!ServiceEntityStringHelper
                .checkNullString(warehouseStoreItemUIModel
                        .getRefMaterialSKUName())) {
            rawEntity.setRefMaterialSKUName(warehouseStoreItemUIModel
                    .getRefMaterialSKUName());
        }
        if (!ServiceEntityStringHelper
                .checkNullString(warehouseStoreItemUIModel.getPackageStandard())) {
            rawEntity.setPackageStandard(warehouseStoreItemUIModel
                    .getPackageStandard());
        }
        if (!ServiceEntityStringHelper
                .checkNullString(warehouseStoreItemUIModel.getRefUnitName())) {
            rawEntity
                    .setRefUnitName(warehouseStoreItemUIModel.getRefUnitName());
        }
        rawEntity.setId(warehouseStoreItemUIModel.getRefWarehouseAreaId());
        rawEntity.setRefShelfNumberId(warehouseStoreItemUIModel
                .getRefShelfNumberId());
        rawEntity.setProductionPlace(warehouseStoreItemUIModel
                .getProductionPlace());
    }

    public void convWarehouseToStoreItemUI(Warehouse warehouse,
                                           WarehouseStoreItemUIModel warehouseStoreItemUIModel) {
        if (warehouse != null) {
            warehouseStoreItemUIModel.setRefWarehouseId(warehouse.getId());
            warehouseStoreItemUIModel.setRefWarehouseName(warehouse.getName());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convInboundDeliveryToStoreItemUI(
            InboundDelivery inboundDelivery,
            WarehouseStoreItemUIModel warehouseStoreItemUIModel) {
        if (inboundDelivery != null) {
            warehouseStoreItemUIModel.setInboundDeliveryId(inboundDelivery
                    .getId());
        }
    }

    public void convOutboundItemToStoreItemUI(
            OutboundItem outboundItem,
            WarehouseStoreItemUIModel warehouseStoreItemUIModel) {
        if (outboundItem != null && warehouseStoreItemUIModel != null) {
            warehouseStoreItemUIModel.setOutboundItemPrice(outboundItem.getItemPrice());
            warehouseStoreItemUIModel.setOutboundItemPriceNoTax(outboundItem.getItemPriceNoTax());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convOutboundDeliveryToStoreItemUI(
            OutboundDelivery outboundDelivery,
            WarehouseStoreItemUIModel warehouseStoreItemUIModel) {
        if (outboundDelivery != null) {
            warehouseStoreItemUIModel.setOutboundDeliveryId(outboundDelivery
                    .getId());
        }
    }

    public void convInboundItemToStoreItemUI(
            InboundItem inboundItem,
            WarehouseStoreItemUIModel warehouseStoreItemUIModel)
            throws ServiceEntityInstallationException,
            ServiceEntityConfigureException {
        convInboundItemToStoreItemUI(inboundItem, warehouseStoreItemUIModel, null);
    }

    public void convInboundItemToStoreItemUI(
            InboundItem inboundItem,
            WarehouseStoreItemUIModel warehouseStoreItemUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException,
            ServiceEntityConfigureException {
        if (inboundItem != null && warehouseStoreItemUIModel != null) {
            warehouseStoreItemUIModel
                    .setInboundUnitPrice(ServiceEntityDoubleHelper
                            .trancateDoubleScale2(inboundItem
                                    .getUnitPrice()));
            warehouseStoreItemUIModel
                    .setInboundUnitPriceNoTax(ServiceEntityDoubleHelper
                            .trancateDoubleScale2(inboundItem
                                    .getUnitPriceNoTax()));
            warehouseStoreItemUIModel
                    .setInboundItemPrice(ServiceEntityDoubleHelper
                            .trancateDoubleScale2(inboundItem
                                    .getItemPrice()));
            warehouseStoreItemUIModel
                    .setInboundItemPriceNoTax(ServiceEntityDoubleHelper
                            .trancateDoubleScale2(inboundItem
                                    .getItemPriceNoTax()));
            warehouseStoreItemUIModel.setInitAmount(inboundItem
                    .getAmount());
            if (!ServiceEntityStringHelper.checkNullString(inboundItem
                    .getRefUnitName())) {
                warehouseStoreItemUIModel
                        .setRefInitUnitName(inboundItem
                                .getRefUnitName());
            } else {
                try {
                    Map<String, String> materialUnitMap = materialStockKeepUnitManager
                            .initMaterialUnitMap(
                                    inboundItem.getRefMaterialSKUUUID(),
                                    inboundItem.getClient());
                    if (!ServiceEntityStringHelper
                            .checkNullString(inboundItem.getRefMaterialSKUUUID())
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
            warehouseStoreItemUIModel.setRefInitUnitUUID(inboundItem
                    .getRefUnitUUID());
        }
    }

}
