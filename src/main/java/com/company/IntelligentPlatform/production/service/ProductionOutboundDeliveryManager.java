package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.dto.OutboundDeliveryServiceUIModelExtension;
import com.company.IntelligentPlatform.logistics.service.*;
import com.company.IntelligentPlatform.logistics.service.WarehouseLogisticsManager;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreItemManager;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.model.StoreAvailableStoreItemRequest;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.*;
import java.util.function.Function;

@Service
public class ProductionOutboundDeliveryManager {

    @Autowired
    protected WarehouseManager warehouseManager;

    @Autowired
    protected WarehouseStoreManager warehouseStoreManager;

    @Autowired
    protected OutboundDeliveryManager outboundDeliveryManager;

    @Autowired
    protected InboundDeliveryManager inboundDeliveryManager;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected OutboundDeliveryWarehouseItemManager outboundDeliveryWarehouseItemManager;

    @Autowired
    protected OutboundDeliveryServiceUIModelExtension outboundDeliveryServiceUIModelExtension;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Deprecated
    public List<OutboundDeliveryServiceModel> genOutboundFromStoreList(DeliveryMatItemBatchGenRequest genRequest,
                                                                       List<ServiceEntityNode> warehouseStoreItemList,
                                                                       List<ServiceEntityNode> outboundDeliveryList,
                                                                       LogonInfo logonInfo,
                                                                       List<ServiceEntityNode> inventoryTransferItemList,
                                                                       Function<OutboundItem, OutboundItem> outboundItemCreatedCallback)
            throws MaterialException, OutboundDeliveryException, ServiceModuleProxyException,
            ServiceEntityConfigureException, SearchConfigureException, NodeNotFoundException,
            ServiceEntityInstallationException, AuthorizationException, LogonInfoException, DocActionException {
        if (ServiceCollectionsHelper.checkNullList(warehouseStoreItemList)) {
            throw new OutboundDeliveryException(OutboundDeliveryException.PARA_NO_WARESTORE_ITEM, "");
        }
        Map<String, List<ServiceEntityNode>> storeItemMap =
                WarehouseStoreItemManager.groupStoreItemByWarehouse(warehouseStoreItemList);
        if (storeItemMap == null || storeItemMap.size() == 0) {
            throw new OutboundDeliveryException(OutboundDeliveryException.PARA_NO_WARESTORE_ITEM, "");
        }
        Set<String> keySet = storeItemMap.keySet();
        Iterator<String> it = keySet.iterator();
        List<OutboundDeliveryServiceModel> outboundServiceModelList = new ArrayList<>();
        Map<String, List<ServiceEntityNode>> outboundDeliveryMap =
                groupDeliveryByWarehouse(outboundDeliveryList, OutboundDelivery.STATUS_INITIAL);
        while (it.hasNext()) {
            String warehouseUUID = it.next();
            Warehouse warehouse =
                    (Warehouse) warehouseManager.getEntityNodeByKey(warehouseUUID, IServiceEntityNodeFieldConstant.UUID,
                            Warehouse.NODENAME, logonInfo.getClient(), null);
            if (warehouse == null) {
                // This can not happen, just leave from manually action
                warehouse = warehouseManager.getDefaultWarehouse(logonInfo);
            }
            OutboundDelivery outboundDelivery = null;
            List<ServiceEntityNode> tempOutboundDeliveyList = null;
            if (outboundDeliveryMap != null) {
                tempOutboundDeliveyList = outboundDeliveryMap.get(warehouse.getUuid());
            }
            if (ServiceCollectionsHelper.checkNullList(tempOutboundDeliveyList)) {
                outboundDelivery = (OutboundDelivery) outboundDeliveryManager.newRootEntityNode(logonInfo.getClient());
                tempOutboundDeliveyList = ServiceCollectionsHelper.mergeToList(null, outboundDelivery);
                outboundDeliveryMap.put(warehouseUUID, tempOutboundDeliveyList);
            } else {
                outboundDelivery = (OutboundDelivery) tempOutboundDeliveyList.get(0);
            }
            outboundDelivery.setRefWarehouseUUID(warehouse.getUuid());
            WarehouseLogisticsManager.initCopyWarehouseStoreToDelivery(warehouseStoreItemList, outboundDelivery);
            OutboundDeliveryServiceModel outboundDeliveryServiceModel =
                    newOutboundFromWarehouseStoreList(outboundDelivery, warehouseStoreItemList,
                            inventoryTransferItemList, outboundItemCreatedCallback, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
            outboundServiceModelList.add(outboundDeliveryServiceModel);
        }
        return outboundServiceModelList;
    }

    /**
     * @Deprecated
     * Logic to create out-bound delivery & relative out-bound items from store
     * list, as well as update info to relative in-bound, and update out-bound
     * information to DB, trigger next operation list
     *
     * @param outboundDelivery
     * @param warehouseStoreItemList
     * @param outboundItemCreatedCallback
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     * @throws OutboundDeliveryException
     * @throws ServiceModuleProxyException
     */
    @Deprecated
    public OutboundDeliveryServiceModel newOutboundFromWarehouseStoreList(OutboundDelivery outboundDelivery,
                                                                          List<ServiceEntityNode> warehouseStoreItemList,
                                                                          List<ServiceEntityNode> inventoryTransferItemList,
                                                                          Function<OutboundItem, OutboundItem> outboundItemCreatedCallback,
                                                                          SerialLogonInfo serialLogonInfo)
            throws MaterialException, ServiceEntityConfigureException, OutboundDeliveryException,
            ServiceModuleProxyException, DocActionException {
        List<ServiceEntityNode> outboundItemList =
                newOutboundItemDefFromWarehouseStoreBatch(outboundDelivery, warehouseStoreItemList,
                        inventoryTransferItemList, outboundItemCreatedCallback, serialLogonInfo);
        if (ServiceCollectionsHelper.checkNullList(outboundItemList)) {
            return null;
        }
        OutboundDeliveryServiceModel outboundDeliveryServiceModel = new OutboundDeliveryServiceModel();
        outboundDeliveryServiceModel.setOutboundDelivery(outboundDelivery);
        List<OutboundItemServiceModel> outboundItemServiceModelList =
                ServiceCollectionsHelper.buildServiceModelList(outboundItemList, serviceEntityNode -> {
                    OutboundItem outboundItem = (OutboundItem) serviceEntityNode;
                    OutboundItemServiceModel outboundItemServiceModel = new OutboundItemServiceModel();
                    outboundItemServiceModel.setOutboundItem(outboundItem);
                    return outboundItemServiceModel;
                });
        outboundDeliveryServiceModel.setOutboundItemList(outboundItemServiceModelList);
        String outboundId = outboundDelivery.getId();
        outboundDeliveryManager.updateServiceModuleWithPostLog(OutboundDeliveryServiceModel.class, outboundDeliveryServiceModel, null,
                outboundDeliveryServiceUIModelExtension,
                outboundDeliveryServiceUIModelExtension.genUIModelExtensionUnion().get(0), serviceEntityNode -> {
                    String[] results = genLogIdNameUnion(serviceEntityNode);
                    if (OutboundItem.NODENAME.equals(serviceEntityNode.getNodeName())) {
                        results[0] = outboundId;
                    }
                    return results;
                }, serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID(), true);
        return outboundDeliveryServiceModel;
    }

    public String[] genLogIdNameUnion(ServiceEntityNode serviceEntityNode) {
        if (OutboundDelivery.SENAME.equals(serviceEntityNode.getServiceEntityName()) &&
                OutboundDelivery.NODENAME.equals(serviceEntityNode.getNodeName())) {
            OutboundDelivery outboundDelivery = (OutboundDelivery) serviceEntityNode;
            String logName = outboundDelivery.getNextProfDocUUID() == null ? ServiceEntityStringHelper.EMPTYSTRING :
                    outboundDelivery.getNextProfDocUUID();
            if (!ServiceEntityStringHelper.checkNullString(outboundDelivery.getNextProfDocUUID())) {
                logName = outboundDelivery.getNextProfDocUUID() + "-" + logName;
            }
            String[] result = {outboundDelivery.getId(), logName};
            return result;
        }
        if (OutboundItem.SENAME.equals(serviceEntityNode.getServiceEntityName()) &&
                OutboundItem.NODENAME.equals(serviceEntityNode.getNodeName())) {
            return genLogIdNameUnionForOutboundItem(serviceEntityNode);
        }
        return null;
    }

    public String[] genLogIdNameUnionForOutboundItem(ServiceEntityNode serviceEntityNode) {
        if (OutboundItem.SENAME.equals(serviceEntityNode.getServiceEntityName()) &&
                OutboundItem.NODENAME.equals(serviceEntityNode.getNodeName())) {
            OutboundItem outboundItem = (OutboundItem) serviceEntityNode;
            String logName = outboundItem.getRefMaterialSKUName();
            if (ServiceEntityStringHelper.checkNullString(outboundItem.getRefMaterialSKUName())) {
                try {
                    MaterialStockKeepUnit materialStockKeepUnit = docFlowProxy.getMaterialSKUFromDocMatItem(outboundItem);
                    if (materialStockKeepUnit != null) {
                        logName = materialStockKeepUnit.getName();
                    }
                } catch (ServiceEntityConfigureException e) {
                    // do nothing
                }
            }
            String[] result = {null, logName};
            return result;
        }
        return null;
    }

    /**
     * Group outbound delivery by warehouse UUID, as well as filter out by status
     *
     * @param outboundDeliveryList
     * @param filterStatus:        if 0, then NO filter function
     * @return
     */
    public Map<String, List<ServiceEntityNode>> groupDeliveryByWarehouse(List<ServiceEntityNode> outboundDeliveryList,
                                                                         int filterStatus) {
        Map<String, List<ServiceEntityNode>> outboundDeliveryMap = new HashMap<>();
        if (ServiceCollectionsHelper.checkNullList(outboundDeliveryList)) {
            return outboundDeliveryMap;
        }
        for (Object seNode : outboundDeliveryList) {
            OutboundDelivery outboundDelivery = (OutboundDelivery) seNode;
            // Filter function
            if (filterStatus > 0) {
                if (outboundDelivery.getStatus() != filterStatus) {
                    continue;
                }
                String warehouseUUID = outboundDelivery.getRefWarehouseUUID();
                if (outboundDeliveryMap.containsKey(warehouseUUID)) {
                    List<ServiceEntityNode> tempOutboundList = outboundDeliveryMap.get(warehouseUUID);
                    tempOutboundList = tempOutboundList == null ? new ArrayList<>() : tempOutboundList;
                    ServiceCollectionsHelper.mergeToList(tempOutboundList, outboundDelivery);
                } else {
                    List<ServiceEntityNode> tempOutboundList = new ArrayList<>();
                    ServiceCollectionsHelper.mergeToList(tempOutboundList, outboundDelivery);
                }
            }
        }
        return outboundDeliveryMap;
    }

    /**
     * Deprecated
     * Core Logic to create outbound items from store list, as well as update
     * info to relative inbound
     *
     * @param outboundDelivery
     * @param warehouseStoreItemList
     * @param outboundItemCreatedCallback
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     * @throws OutboundDeliveryException
     */
    //TODO there is still usage in production
    @Deprecated
    public List<ServiceEntityNode> newOutboundItemDefFromWarehouseStoreBatch(OutboundDelivery outboundDelivery,
                                                                             List<ServiceEntityNode> warehouseStoreItemList,
                                                                             List<ServiceEntityNode> inventoryTransferItemList,
                                                                             Function<OutboundItem, OutboundItem> outboundItemCreatedCallback,
                                                                             SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, MaterialException, OutboundDeliveryException, DocActionException {
        if (ServiceCollectionsHelper.checkNullList(warehouseStoreItemList)) {
            return null;
        }
        List<ServiceEntityNode> result = new ArrayList<>();
        List<ServiceEntityNode> updateInboundItemList = new ArrayList<>();
        for (ServiceEntityNode seNode : warehouseStoreItemList) {
            WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) seNode;
            if (!outboundDelivery.getRefWarehouseUUID().equals(warehouseStoreItem.getRootNodeUUID())) {
                continue;
            }
            OutboundItem outboundItem =
                    newOutboundItemDefFromWarehouseStore(outboundDelivery, warehouseStoreItem,
                            inventoryTransferItemList, serialLogonInfo);
            if (outboundItem == null) {
                continue;
            }
            if (outboundItemCreatedCallback != null) {
                outboundItem = outboundItemCreatedCallback.apply(outboundItem);
            }
            /*
             * [Step2] update outbound uuid to inbound
             */
            InboundItem inboundItem =
                    (InboundItem) inboundDeliveryManager.getEntityNodeByKey(
                            warehouseStoreItem.getPrevDocMatItemUUID(), IServiceEntityNodeFieldConstant.UUID,
                            InboundItem.NODENAME, warehouseStoreItem.getClient(), null);
            if (inboundItem != null) {
                updateInboundItemList.add(inboundItem);
            }
            if (outboundItem != null) {
                result.add(outboundItem);
            }
        }

        if (ServiceCollectionsHelper.checkNullList(result)) {
            throw new OutboundDeliveryException(OutboundDeliveryException.PARA_NO_WARESTORE_ITEM, "");
        }
        /*
         * [Step3] Update inbound info to DB
         */
        inboundDeliveryManager.updateSENodeList(updateInboundItemList, serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
        // Update information from warehouse store item
        warehouseStoreManager.updateSENodeList(warehouseStoreItemList, serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
        return result;
    }

    /**
     * Logic to create outbound delivery item from default selected
     * warehouseStoreItem
     *
     * @param outboundDelivery
     * @param warehouseStoreItem
     * @param inventoryTransferItemList : in case need to process inventory transfer item
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     * @throws OutboundDeliveryException
     */
    //TODO there is still usage in production
    @Deprecated
    public OutboundItem newOutboundItemDefFromWarehouseStore(OutboundDelivery outboundDelivery,
                                                             WarehouseStoreItem warehouseStoreItem,
                                                             List<ServiceEntityNode> inventoryTransferItemList, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, MaterialException, DocActionException {
        if (warehouseStoreItem.getItemStatus() == WarehouseStoreItem.STATUS_ARCHIVE) {
            return null;
        }
        // Get the default available amount by inventory transfer item
        String homeOutItemUUID = null;
        StorageCoreUnit requestCoreUnit = null;
        if (!ServiceCollectionsHelper.checkNullList(inventoryTransferItemList)) {
            ServiceEntityNode rawInventoryNode =
                    ServiceCollectionsHelper.filterSENodeOnline(warehouseStoreItem.getUuid(), "refStoreItemUUID",
                            inventoryTransferItemList);
            if (rawInventoryNode != null) {
                InventoryTransferItem inventoryTransferItem =
                        (InventoryTransferItem) rawInventoryNode;
                homeOutItemUUID = rawInventoryNode.getUuid();
                requestCoreUnit = new StorageCoreUnit(inventoryTransferItem.getRefMaterialSKUUUID(),
                        inventoryTransferItem.getRefUnitUUID(), inventoryTransferItem.getAmount());
            }
        }
        // Don't need to consider reserved doc when new out-bound item
        StorageCoreUnit availableStoreUnit = getAvailableStoreItemAmountUnion(
                new StoreAvailableStoreItemRequest(warehouseStoreItem, homeOutItemUUID, false));
        if (availableStoreUnit.getAmount() <= 0) {
            return null;
        }
        if (requestCoreUnit != null) {
            // In case there is additional request, such as request form
            // inventory transfer
            int result = materialStockKeepUnitManager.compareSKURequestsAmount(requestCoreUnit, availableStoreUnit,
                    warehouseStoreItem.getClient());
            if (result < 0) {
                availableStoreUnit.setAmount(requestCoreUnit.getAmount());
                availableStoreUnit.setRefUnitUUID(requestCoreUnit.getRefUnitUUID());
            }
        }
        OutboundItem outboundItem =
                (OutboundItem) outboundDeliveryManager.newEntityNode(outboundDelivery, OutboundItem.NODENAME);
        MaterialStockKeepUnit materialStockKeepUnit =
                (MaterialStockKeepUnit) materialStockKeepUnitManager.getEntityNodeByKey(
                        warehouseStoreItem.getRefMaterialSKUUUID(), IServiceEntityNodeFieldConstant.UUID,
                        MaterialStockKeepUnit.NODENAME, outboundItem.getClient(), null);
        docFlowProxy.buildItemPrevNextRelationship(warehouseStoreItem,
                 outboundItem, null,serialLogonInfo);
        // Important, has to be reset amount as current available amount
        outboundItem.setAmount(availableStoreUnit.getAmount());
        outboundItem.setRefUnitUUID(availableStoreUnit.getRefUnitUUID());
        outboundItem.setRefMaterialSKUName(warehouseStoreItem.getRefMaterialSKUName());
        outboundItem.setPackageStandard(warehouseStoreItem.getPackageStandard());
        outboundItem.setRefMaterialSKUId(warehouseStoreItem.getRefMaterialSKUId());
        if (!ServiceEntityStringHelper.checkNullString(warehouseStoreItem.getReservedMatItemUUID())) {
            outboundItem.setReservedMatItemUUID(warehouseStoreItem.getReservedMatItemUUID());
            outboundItem.setReservedDocType(warehouseStoreItem.getReservedDocType());
            // When Out-bound item instance created, then inform the source doc
            // material item instance about this change.
            ServiceEntityManager targetDocumentManager =
                    serviceDocumentComProxy.getDocumentManager(warehouseStoreItem.getReservedDocType());
            if (targetDocumentManager != null) {
                targetDocumentManager.reservedDocUpdateToMatItem(IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY,
                        outboundDelivery, outboundItem, warehouseStoreItem.getReservedMatItemUUID());
            }
        }
        outboundItem.setRefUnitName(warehouseStoreItem.getRefUnitName());
        if (materialStockKeepUnit != null) {
            outboundItem.setOutboundFee(materialStockKeepUnit.getOutboundDeliveryPrice());
            outboundItem.setProducerName(materialStockKeepUnit.getProductionPlace());
        }

        outboundItem.setProductionDate(warehouseStoreItem.getProductionDate());
        outboundItem.setVolume(warehouseStoreItem.getVolume());
        outboundItem.setRefShelfNumberID(warehouseStoreItem.getRefShelfNumberId());
        outboundItem.setRefWarehouseAreaUUID(warehouseStoreItem.getRefWarehouseAreaUUID());
        outboundItem.setWeight(warehouseStoreItem.getWeight());
        outboundItem.setDeclaredValue(warehouseStoreItem.getDeclaredValue());
        // build binding relationship from warehouse store to
        bindWarehouseStoreItemToOutbound(warehouseStoreItem, outboundItem);
        // Calculate the store day
        InboundItem inboundItem = (InboundItem) inboundDeliveryManager.getEntityNodeByKey(
                warehouseStoreItem.getPrevDocMatItemUUID(), IServiceEntityNodeFieldConstant.UUID,
                InboundItem.NODENAME, null);
        if (inboundItem != null) {
            /*
             * [Post-step1] also set inboundItem ref-outbound item
             */
            InboundItem inboundItemBack = (InboundItem) inboundItem.clone();
            inboundDeliveryManager.updateSENode(inboundItem, inboundItemBack,
                    outboundItem.getResEmployeeUUID(), inboundItem.getResOrgUUID());
            InboundDelivery inboundDelivery =
                    (InboundDelivery) inboundDeliveryManager.getEntityNodeByKey(inboundItem.getRootNodeUUID(),
                            IServiceEntityNodeFieldConstant.UUID, InboundDelivery.NODENAME, null);

        }
        return outboundItem;
    }

    public StorageCoreUnit getAvailableStoreItemAmountUnion(
            StoreAvailableStoreItemRequest storeAvailableStoreItemRequest)
            throws ServiceEntityConfigureException, MaterialException {
        return outboundDeliveryWarehouseItemManager.getAvailableStoreItemAmountUnion(storeAvailableStoreItemRequest);
    }

    /**
     * Build the binding relationship from warehouse store item to outbound
     */
    public void bindWarehouseStoreItemToOutbound(WarehouseStoreItem warehouseStoreItem,
                                                 OutboundItem outboundItem) {
        if (outboundItem == null) {
            return;
        }
        if (warehouseStoreItem == null) {
            return;
        }
        outboundItem.setRefStoreItemUUID(warehouseStoreItem.getUuid());
        warehouseStoreItem.setNextDocType(IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY);
        warehouseStoreItem.setNextDocMatItemUUID(outboundItem.getUuid());
        outboundItem.setPrevDocMatItemUUID(warehouseStoreItem.getUuid());
        outboundItem.setPrevDocType(IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM);
    }

}
