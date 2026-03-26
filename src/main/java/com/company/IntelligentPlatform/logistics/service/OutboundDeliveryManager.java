package com.company.IntelligentPlatform.logistics.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.repository.OutboundDeliveryRepository;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.model.OutboundDeliveryConfigureProxy;

import com.company.IntelligentPlatform.logistics.model.StoreAvailableStoreItemRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreItemUIModel;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.RegisteredProduct;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.SerialIdInputModel;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.ServiceExtensionSettingManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityDoubleHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import java.time.ZoneId;
import java.time.LocalDateTime;

/**
 * Logic Manager CLASS FOR Service Entity [OutboundDelivery]
 *
 * @author
 * @date Fri Dec 27 18:43:38 CST 2013
 * <p>
 * This class is generated automatically by platform automation register
 * tool
 */
@Service
@Transactional
public class OutboundDeliveryManager extends ServiceEntityManager {

    public static final String METHOD_ConvOutboundDeliveryToUI = "convOutboundDeliveryToUI";

    public static final String METHOD_ConvUIToOutboundDelivery = "convUIToOutboundDelivery";

    public static final String METHOD_ConvWarehouseToUI = "convWarehouseToUI";

    public static final String METHOD_ConvWarehouseAreaToUI = "convWarehouseAreaToUI";
    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    protected OutboundDeliveryRepository outboundDeliveryDAO;

    @Autowired
    protected OutboundDeliveryConfigureProxy outboundDeliveryConfigureProxy;

    @Autowired
    protected OutboundDeliveryIdHelper outboundDeliveryIdHelper;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected WarehouseStoreManager warehouseStoreManager;

    @Autowired
    protected OutboundDeliveryWarehouseItemManager outboundDeliveryWarehouseItemManager;

    @Autowired
    protected ServiceExtensionSettingManager serviceExtensionSettingManager;

    @Autowired
    protected OutboundDeliveryServiceUIModelExtension outboundDeliveryServiceUIModelExtension;

    @Autowired
    protected OutboundItemServiceUIModelExtension outboundItemServiceUIModelExtension;

    @Autowired
    protected OutboundDeliverySearchProxy outboundDeliverySearchProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected SerialIdDocumentProxy serialIdDocumentProxy;

    @Autowired
    protected StandardPriorityProxy standardPriorityProxy;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

    protected Map<Integer, String> freightChargeTypeMap;

    protected Map<String, Map<Integer, String>> planCategoryMapLan = new HashMap<>();

    public OutboundDeliveryManager() {
        super.seConfigureProxy = new OutboundDeliveryConfigureProxy();
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, outboundDeliveryDAO));
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(outboundDeliveryConfigureProxy);
    }

    public Map<Integer, String> initFreightChargeType() throws ServiceEntityInstallationException {
        if (this.freightChargeTypeMap == null) {
            this.freightChargeTypeMap =
                    serviceDropdownListHelper.getUIDropDownMap(OutboundDeliveryUIModel.class, "freightChargeType");
        }
        return this.freightChargeTypeMap;
    }

    public Map<Integer, String> initPlanCategoryMap(String languageCode) throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode, this.planCategoryMapLan,
                OutboundDeliveryUIModel.class, "planCategory");
    }

    public Map<Integer, String> initStatusMap(String languageCode) throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode, this.statusMapLan, OutboundDeliveryUIModel.class,
                IDocumentNodeFieldConstant.STATUS);
    }

    public Map<Integer, String> initPriorityCode(String languageCode)
            throws ServiceEntityInstallationException {
        return standardPriorityProxy.getPriorityMap(languageCode);
    }

    public Map<Integer, String> initDocumentTypeMap(boolean filterFlag, String languageCode) throws ServiceEntityInstallationException {
        return serviceDocumentComProxy
                .getDocumentTypeMap(filterFlag, languageCode);
    }

    /**
     * Provide default logic to copy source doc material item instance as
     * previous item to target in-bound item
     *
     * @param docMatItemNode
     * @param sourceDocType
     * @return
     */
    @Deprecated
    public OutboundItem copyNextDocMatItemToOutboundItem(DocMatItemNode docMatItemNode,
                                                                  int sourceDocType,
                                                                  OutboundItem outboundItem) {
        if (outboundItem != null && docMatItemNode != null) {
            outboundItem.setActualAmount(docMatItemNode.getAmount());
            outboundItem.setAmount(docMatItemNode.getAmount());
            outboundItem.setRefUnitUUID(docMatItemNode.getRefUnitUUID());
            outboundItem.setNextDocType(sourceDocType);
            outboundItem.setNextDocMatItemUUID(docMatItemNode.getUuid());
            if (outboundItem.getReservedDocType() > 0) {
                docMatItemNode.setReservedDocType(docMatItemNode.getReservedDocType());
                docMatItemNode.setReservedMatItemUUID(docMatItemNode.getReservedMatItemUUID());
            }
            if (docMatItemNode.getReservedDocType() > 0) {
                outboundItem.setReservedDocType(docMatItemNode.getReservedDocType());
                outboundItem.setReservedMatItemUUID(docMatItemNode.getReservedMatItemUUID());
            }
            outboundItem.setRefUUID(docMatItemNode.getRefUUID());
            outboundItem.setRefMaterialSKUUUID(docMatItemNode.getRefMaterialSKUUUID());
            docMatItemNode.setPrevDocType(IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY);
            docMatItemNode.setPrevDocMatItemUUID(outboundItem.getUuid());
        }
        return outboundItem;
    }

    //TODO to migrate into common method
    public List<ServiceEntityNode> getRegisteredProductListForSerialId(OutboundItem outboundItem)
            throws ServiceEntityConfigureException, ServiceModuleProxyException {
        OutboundDelivery outboundDelivery =
                (OutboundDelivery) getEntityNodeByKey(outboundItem.getRootNodeUUID(),
                        IServiceEntityNodeFieldConstant.UUID, OutboundDelivery.NODENAME,
                        outboundItem.getClient(), null);
        OutboundDeliveryServiceModel outboundDeliveryServiceModel =
                (OutboundDeliveryServiceModel) loadServiceModule(OutboundDeliveryServiceModel.class, outboundDelivery);
        MaterialStockKeepUnit materialStockKeepUnit = docFlowProxy.getMaterialSKUFromDocMatItem(outboundItem);
        MaterialStockKeepUnit templateMaterialSKU = materialStockKeepUnit;
        if (RegisteredProductManager.checkRegisteredProduct(materialStockKeepUnit)) {
            templateMaterialSKU = materialStockKeepUnitManager.getRefTemplateMaterialSKU(materialStockKeepUnit);
        }
        if (templateMaterialSKU.getTraceMode() != MaterialStockKeepUnit.TRACEMODE_SINGLE) {
            return null;
        }
        List<ServiceEntityNode> allWarehouseStoreItemList = getAllPossibleStoreItemList(outboundDelivery,
                ServiceCollectionsHelper.parseToSENodeList(outboundDeliveryServiceModel.getOutboundItemList(),
                        OutboundItemServiceModel::getOutboundItem), templateMaterialSKU.getUuid(),
                outboundItem.getClient());
        List<String> refMaterialSKUUUIDList = allWarehouseStoreItemList.stream().map(serviceEntityNode -> {
            WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) serviceEntityNode;
            return warehouseStoreItem.getRefMaterialSKUUUID();
        }).collect(Collectors.toList());
        List<ServiceEntityNode> registeredProductList =
                registeredProductManager.getEntityNodeListByMultipleKey(refMaterialSKUUUIDList,
                        IServiceEntityNodeFieldConstant.UUID, RegisteredProduct.NODENAME, outboundDelivery.getClient(),
                        null);
        return registeredProductList;
    }

    /**
     * Get all possible store item could be used for serial id edit
     *
     * @param outboundDelivery
     * @param allOutboundItemList
     * @param refTemplateSKUUUID
     * @param client
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<ServiceEntityNode> getAllPossibleStoreItemList(OutboundDelivery outboundDelivery,
                                                               List<ServiceEntityNode> allOutboundItemList,
                                                               String refTemplateSKUUUID, String client)
            throws ServiceEntityConfigureException {
        // step1, get raw store item list
        List<WarehouseStoreItem> rawStoreItemList =
                outboundDeliveryWarehouseItemManager.getInStockStoreItemBySKUWarehouse(refTemplateSKUUUID,
                        outboundDelivery.getRefWarehouseUUID(), outboundDelivery.getRefWarehouseAreaUUID());
        if (ServiceCollectionsHelper.checkNullList(rawStoreItemList)) {
            return null;
        }
        /*
         * [step2]Category, get free and other raw pending store item
         */
        List<OutboundDeliveryWarehouseItemManager.WarehouseStoreItemWithReference> warehouseStoreItemWithRefList =
                outboundDeliveryWarehouseItemManager.batchCategoryStoreItemWithReference(rawStoreItemList);
        if (ServiceCollectionsHelper.checkNullList(warehouseStoreItemWithRefList)) {
            return null;
        }
        List<ServiceEntityNode> resultStoreItemList = new ArrayList<>();
        List<ServiceEntityNode> ownStoreItemList = new ArrayList<>();
        List<ServiceEntityNode> freeStoreItemList = new ArrayList<>();
        List<ServiceEntityNode> pendingStoreItemList = new ArrayList<>();
        List<OutboundDeliveryWarehouseItemManager.WarehouseStoreItemWithReference> pendingStoreItemWithRefList =
                new ArrayList<>();
        List<String> otherRootNodeUUIDList = new ArrayList<>();
        for (OutboundDeliveryWarehouseItemManager.WarehouseStoreItemWithReference warehouseStoreItemWithReference : warehouseStoreItemWithRefList) {
            List<ServiceEntityNode> pendingOutboundItemList =
                    warehouseStoreItemWithReference.getRefPendingOutboundItemList();
            if (ServiceCollectionsHelper.checkNullList(pendingOutboundItemList)) {
                freeStoreItemList.add(warehouseStoreItemWithReference.getWarehouseStoreItem());
            }
            int contains = ServiceCollectionsHelper.checkContainsSubList(allOutboundItemList, pendingOutboundItemList);
            if (contains == ServiceCollectionsHelper.ContainsSEList.FULL_CONTAINS) {
                ownStoreItemList.add(warehouseStoreItemWithReference.getWarehouseStoreItem());
            } else {
                pendingStoreItemWithRefList.add(warehouseStoreItemWithReference);
                List<ServiceEntityNode> otherPendOutboundItemList =
                        warehouseStoreItemWithReference.getRefPendingOutboundItemList();
                if (!ServiceCollectionsHelper.checkNullList(otherPendOutboundItemList)) {
                    List<String> tmpRootNodeUUIDList =
                            otherPendOutboundItemList.stream().map(ServiceEntityNode::getRootNodeUUID)
                                    .collect(Collectors.toList());
                    ServiceCollectionsHelper.mergeToList(otherRootNodeUUIDList, tmpRootNodeUUIDList, key -> {
                        return (String) key;
                    });
                }
            }
        }
        if (!ServiceCollectionsHelper.checkNullList(ownStoreItemList)) {
            resultStoreItemList.addAll(ownStoreItemList);
        }
        if (!ServiceCollectionsHelper.checkNullList(freeStoreItemList)) {
            resultStoreItemList.addAll(freeStoreItemList);
        }
        /*
         * [Step3] Check and filter other pending store item
         */
        if (ServiceCollectionsHelper.checkNullList(otherRootNodeUUIDList)) {
            return resultStoreItemList;
        }
        List<ServiceEntityNode> allOtherOutboundDeliveryList =
                this.getEntityNodeListByMultipleKey(otherRootNodeUUIDList, IServiceEntityNodeFieldConstant.UUID,
                        OutboundDelivery.NODENAME, client, null);
        List<ServiceEntityNode> initialOutboundDeliveryList =
                ServiceCollectionsHelper.filterListOnline(allOtherOutboundDeliveryList, serviceEntityNode -> {
                    OutboundDelivery tmpOutboundDelivery = (OutboundDelivery) serviceEntityNode;
                    return tmpOutboundDelivery.getStatus() == OutboundDelivery.STATUS_INITIAL;
                }, false);
        if (ServiceCollectionsHelper.checkNullList(initialOutboundDeliveryList)) {
            return resultStoreItemList;
        }
        List<OutboundDeliveryWarehouseItemManager.WarehouseStoreItemWithReference> initialPendingStoreItemWithRefList =
                filterWarehouseRefListWithInitialOutbound(pendingStoreItemWithRefList, initialOutboundDeliveryList);
        if (ServiceCollectionsHelper.checkNullList(initialPendingStoreItemWithRefList)) {
            return resultStoreItemList;
        }
        List<ServiceEntityNode> filterPendingStoreItemList = initialPendingStoreItemWithRefList.stream()
                .map(OutboundDeliveryWarehouseItemManager.WarehouseStoreItemWithReference::getWarehouseStoreItem)
                .collect(Collectors.toList());
        if (!ServiceCollectionsHelper.checkNullList(filterPendingStoreItemList)) {
            resultStoreItemList.addAll(filterPendingStoreItemList);
        }
        return resultStoreItemList;
    }

    private List<OutboundDeliveryWarehouseItemManager.WarehouseStoreItemWithReference> filterWarehouseRefListWithInitialOutbound(
            List<OutboundDeliveryWarehouseItemManager.WarehouseStoreItemWithReference> allStoreItemWithRefList,
            List<ServiceEntityNode> initialOutboundDeliveryList) {
        if (ServiceCollectionsHelper.checkNullList(allStoreItemWithRefList) ||
                ServiceCollectionsHelper.checkNullList(initialOutboundDeliveryList)) {
            return null;
        }
        List<OutboundDeliveryWarehouseItemManager.WarehouseStoreItemWithReference> resultList = new ArrayList<>();
        for (OutboundDeliveryWarehouseItemManager.WarehouseStoreItemWithReference storeItemWithReference : allStoreItemWithRefList) {
            List<ServiceEntityNode> rawPendingOutboundItemList = storeItemWithReference.getRefPendingOutboundItemList();
            if (ServiceCollectionsHelper.checkNullList(rawPendingOutboundItemList)) {
                resultList.add(storeItemWithReference);
                continue;
            }
            boolean include = true;
            for (ServiceEntityNode tmpSENode : rawPendingOutboundItemList) {
                ServiceEntityNode filteredOutboundDelivery =
                        ServiceCollectionsHelper.filterOnline(initialOutboundDeliveryList, seNode -> {
                            return seNode.getUuid().equals(tmpSENode.getRootNodeUUID());
                        });
                if (filteredOutboundDelivery == null) {
                    // in case this outbound item not be included in initial outbound delivery list
                    include = false;
                    break;
                }
            }
            if (include) {
                resultList.add(storeItemWithReference);
            }
        }
        return resultList;
    }

    public void postUpdateServiceUIModel(OutboundDeliveryServiceUIModel outboundDeliveryServiceUIModel,
                                         OutboundDeliveryServiceModel outboundDeliveryServiceModel) throws ServiceEntityConfigureException {
        List<OutboundItemUIModel> outboundItemUIModelList =
                ServiceCollectionsHelper.parseToUINodeList(outboundDeliveryServiceUIModel.getOutboundItemUIModelList(),
                        OutboundItemServiceUIModel::getOutboundItemUIModel);
        List<ServiceEntityNode> outboundItemList = ServiceCollectionsHelper.parseToSENodeList(outboundDeliveryServiceModel.getOutboundItemList(),
                OutboundItemServiceModel::getOutboundItem);
        serialIdDocumentProxy.updateSerialIdMetaToDocumentWrapper(outboundItemUIModelList, outboundItemList);
    }

    /**
     * This API method updates the serial ID list in the outbound delivery to switch the registered product warehouse store item list to outbound.
     * When the serial ID list is updated, the warehouse's store item list designated for outbound dispatch will be switched.
     *
     * @param serialIdInputBatchModel The batch model containing the serial IDs, represented as an instance of <code>SerialIdInputBatchModel</code>, to be updated.
     * @param client The login client identifier.
     * @param logonUserUUID The UUID of the logged-in user.
     * @param organizationUUID The UUID of the organization group.
     * @throws ServiceEntityConfigureException If there is a configuration issue with the service entity.
     * @throws ServiceModuleProxyException If there is a proxy error in the service module.
     */
    //TODO improve this
    public void switchSerialIdBatch(SerialIdInputBatchModel serialIdInputBatchModel, String client,
                                    String logonUserUUID, String organizationUUID)
            throws ServiceEntityConfigureException, DocActionException {
        try {
            OutboundDelivery outboundDelivery = (OutboundDelivery) getEntityNodeByKey(serialIdInputBatchModel.getBaseUUID(),
                    IServiceEntityNodeFieldConstant.UUID, OutboundDelivery.NODENAME, client, null);
            OutboundDeliveryServiceModel outboundDeliveryServiceModel =
                    (OutboundDeliveryServiceModel) loadServiceModule(OutboundDeliveryServiceModel.class, outboundDelivery);
            List<SerialIdInputModel> serialIdInputModelList = serialIdInputBatchModel.getSerialIdInputModelList();
            if (ServiceCollectionsHelper.checkNullList(serialIdInputModelList)) {
                return;
            }
            List<ServiceEntityNode> docMatNodeItemList = ServiceCollectionsHelper.parseToSENodeList(outboundDeliveryServiceModel.getOutboundItemList(),
                    OutboundItemServiceModel::getOutboundItem);
            if (ServiceCollectionsHelper.checkNullList(docMatNodeItemList)) {
                return;
            }
            // Build existed doc mat item list
            Map<String, List<SerialIdDocumentProxy.DocMatItemMaterialUnion>> docItemUnionListMap =
                    serialIdDocumentProxy.buildDocItemUnionListMap(docMatNodeItemList, false);
            for (SerialIdInputModel serialIdInputModel : serialIdInputModelList) {
                String refTemplateSKUUUID = serialIdInputModel.getRefTemplateMaterialSKUUUID();
                List<SerialIdDocumentProxy.DocMatItemMaterialUnion> rawDocItemUnionList =
                        docItemUnionListMap.get(refTemplateSKUUUID);
                SerialIdDocumentProxy.UpdateDocItemMaterialMatrix updateDocItemMaterialMatrix =
                        serialIdDocumentProxy.buildDocItemMaterialMatrixBySerialIdArray(rawDocItemUnionList,
                                serialIdInputModel.getSerialIdList());
                switchSerialIdUnion(refTemplateSKUUUID, outboundDelivery, updateDocItemMaterialMatrix, logonUserUUID,
                        organizationUUID);
            }
        } catch (ServiceModuleProxyException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e);
        }
    }

    private void switchSerialIdUnion(String refTemplateSKUUUID, OutboundDelivery outboundDelivery,
                                    SerialIdDocumentProxy.UpdateDocItemMaterialMatrix updateDocItemMaterialMatrix,
                                    String logonUserUUID, String organizationUUID)
            throws ServiceEntityConfigureException {
        List<SerialIdDocumentProxy.DocMatItemMaterialUnion> toDeleteList = updateDocItemMaterialMatrix.getToDeleteList();
        List<String> newSerialIdList = updateDocItemMaterialMatrix.getNewSerialIdList();
        if (ServiceCollectionsHelper.checkNullList(toDeleteList) ||
                ServiceCollectionsHelper.checkNullList(newSerialIdList)) {
            return;
        }
        /*
         * step1, get raw store item list
         */
        List<WarehouseStoreItem> rawStoreItemList =
                outboundDeliveryWarehouseItemManager.getInStockStoreItemBySKUWarehouse(refTemplateSKUUUID,
                        outboundDelivery.getRefWarehouseUUID(), outboundDelivery.getRefWarehouseAreaUUID());
        if (ServiceCollectionsHelper.checkNullList(rawStoreItemList)) {
            return;
        }
        List<OutboundDeliveryWarehouseItemManager.WarehouseStoreItemWithReference> warehouseStoreItemWithRefList =
                outboundDeliveryWarehouseItemManager.batchCategoryStoreItemWithReference(rawStoreItemList);
        int newLen = newSerialIdList.size();
        int toDeleteLen = toDeleteList.size();
        for (int i = 0; i < toDeleteLen; i++) {
            SerialIdDocumentProxy.DocMatItemMaterialUnion toDeleteUnion = toDeleteList.get(i);
            if (i >= newLen) {
                break;
            }
            String newSerialId = newSerialIdList.get(i);
            OutboundItem oldOutboundItem = (OutboundItem) toDeleteUnion.getDocMatItemNode();
            String toDeleteSerialId = toDeleteUnion.getRegisteredProduct().getSerialId();
            OutboundDeliveryWarehouseItemManager.WarehouseStoreItemWithReference toDeleteWithRef =
                    filterWarehouseStoreWithReferBySerialId(toDeleteSerialId, warehouseStoreItemWithRefList);
            WarehouseStoreItem oldWarehouseStoreItem = toDeleteWithRef.getWarehouseStoreItem();
            OutboundDeliveryWarehouseItemManager.WarehouseStoreItemWithReference newWithRef =
                    filterWarehouseStoreWithReferBySerialId(newSerialId, warehouseStoreItemWithRefList);
            WarehouseStoreItem newWarehouseStoreItem = newWithRef.getWarehouseStoreItem();
            OutboundItem newOutboundItem =
                    filterOtherOutboundItem(oldOutboundItem.getUuid(), newWithRef);
            switchOutboundItemWithStoreItem(oldOutboundItem, newOutboundItem, oldWarehouseStoreItem,
                    newWarehouseStoreItem, logonUserUUID, organizationUUID);
        }

    }

    private OutboundItem filterOtherOutboundItem(String homeOutboundUUID,
                                                 OutboundDeliveryWarehouseItemManager.WarehouseStoreItemWithReference warehouseStoreItemWithReference) {
        List<ServiceEntityNode> refPendingOutboundItemList =
                warehouseStoreItemWithReference.getRefPendingOutboundItemList();
        if (ServiceCollectionsHelper.checkNullList(refPendingOutboundItemList)) {
            // In case no pending outbound, just free stock
            return null;
        }
        OutboundItem result = null;
        for (ServiceEntityNode seNode : refPendingOutboundItemList) {
            OutboundItem tempOutItem = (OutboundItem) seNode;
            if (!tempOutItem.getUuid().equals(homeOutboundUUID)) {
                result = tempOutItem;
                break;
            }
        }
        return result;
    }

    public OutboundDeliveryWarehouseItemManager.WarehouseStoreItemWithReference filterWarehouseStoreWithReferBySerialId(
            String serialId,
            List<OutboundDeliveryWarehouseItemManager.WarehouseStoreItemWithReference> warehouseStoreItemWithRefList) {
        if (ServiceEntityStringHelper.checkNullString(serialId)) {
            return null;
        }
        OutboundDeliveryWarehouseItemManager.WarehouseStoreItemWithReference warehouseStoreItemWithReference =
                ServiceCollectionsHelper.filterOnline(warehouseStoreItemWithRefList, tempStoreItemWithRefer -> {
                    RegisteredProduct registeredProduct = tempStoreItemWithRefer.getRegisteredProduct();
                    if (registeredProduct == null) {
                        return false;
                    }
                    return serialId.equals(registeredProduct.getSerialId());
                });
        return warehouseStoreItemWithReference;
    }

    /**
     * Core Logic to switch outboundItem pointer to new store item, while old store item original pointer point to
     * new outboundItem.
     * if new outboundItem not existed, then set old store item free as free store.
     *
     * @param outboundItem
     * @param newOutboundItem
     * @param oldStoreItem
     * @param newStoreItem
     * @param logonUserUUID
     * @param organizationUUID
     */
    private void switchOutboundItemWithStoreItem(OutboundItem outboundItem,
                                                 OutboundItem newOutboundItem,
                                                 WarehouseStoreItem oldStoreItem, WarehouseStoreItem newStoreItem,
                                                 String logonUserUUID, String organizationUUID) {
        /*
         * [Step1]Switch store relationship to new store item
         */
        // Switch [prevDocMatItemUUID]
        if (oldStoreItem.getUuid().equals(outboundItem.getPrevDocMatItemUUID())) {
            outboundItem.setPrevDocMatItemUUID(newStoreItem.getUuid());
            if (newOutboundItem != null) {
                newOutboundItem.setPrevDocMatItemUUID(oldStoreItem.getUuid());
            }
        }
        // Switch [RefStoreItemUUID]
        if (oldStoreItem.getUuid().equals(outboundItem.getRefStoreItemUUID())) {
            outboundItem.setRefStoreItemUUID(newStoreItem.getUuid());
            if (newOutboundItem != null) {
                newOutboundItem.setRefStoreItemUUID(oldStoreItem.getUuid());
            }
        }
        if (outboundItem.getUuid().equals(oldStoreItem.getNextDocMatItemUUID())) {
            if (newOutboundItem != null) {
                oldStoreItem.setNextDocMatItemUUID(newOutboundItem.getUuid());
            } else {
                oldStoreItem.setNextDocMatItemUUID(ServiceEntityStringHelper.EMPTYSTRING);
            }
            newStoreItem.setNextDocMatItemUUID(outboundItem.getUuid());
        }

        /*
         * [Step2] Switch ref Material relationship to new material
         */
        outboundItem.setRefMaterialSKUUUID(newStoreItem.getRefMaterialSKUUUID());
        if (newOutboundItem != null) {
            newOutboundItem.setRefMaterialSKUUUID(oldStoreItem.getRefMaterialSKUUUID());
        }
        /*
         * [Step3] update into DB
         */
        this.updateSENode(outboundItem, logonUserUUID, organizationUUID);
        if (newOutboundItem != null) {
            this.updateSENode(newOutboundItem, logonUserUUID, organizationUUID);
        }
        warehouseStoreManager.updateSENode(oldStoreItem, logonUserUUID, organizationUUID);
        warehouseStoreManager.updateSENode(newStoreItem, logonUserUUID, organizationUUID);
        /*
         * [Step3] Clear warehouse log
         */
        //        List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
        //        List<ServiceEntityNode> warehouseStoreLog = warehouseStoreManager.getEntityNodeListByKeyList(keyList,
        //                WarehouseStoreItemLog.NODENAME, outboundItem.getClient(), null);
    }

    @Override
    public ServiceEntityNode newRootEntityNode(String client) throws ServiceEntityConfigureException {
        String id = outboundDeliveryIdHelper.genDefaultId(client);
        OutboundDelivery outboundDelivery = (OutboundDelivery) super.newRootEntityNode(client);
        outboundDelivery.setId(id);
        serviceExtensionSettingManager.setInitValue(outboundDelivery, OutboundDelivery.SENAME);
        return outboundDelivery;
    }

    /**
     * Logic to get other pending outbound item list for this warehouse store item
     *
     * @param warehouseStoreItem
     * @param homeOutItemUUID:   home outbound item UUID, could be mulitple value
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<OutboundItem> getOtherPendingOutboundItem(WarehouseStoreItem warehouseStoreItem,
                                                          String homeOutItemUUID)
            throws ServiceEntityConfigureException {
        List<OutboundItem> resultList = new ArrayList<>();
        List<ServiceEntityNode> rawOutboundItemList =
                getEntityNodeListByKey(warehouseStoreItem.getUuid(), DeliveryItem.FILED_REFSTOREITEMUUID,
                        OutboundItem.NODENAME, warehouseStoreItem.getClient(), null);
        if (ServiceCollectionsHelper.checkNullList(rawOutboundItemList)) {
            return null;
        }
        List<String> outItemUUIDArray = ServiceEntityStringHelper.convStringToStrList(homeOutItemUUID);
        for (ServiceEntityNode seNode : rawOutboundItemList) {
            OutboundItem outboundItem = (OutboundItem) seNode;
            OutboundDelivery outboundDelivery =
                    (OutboundDelivery) getEntityNodeByKey(outboundItem.getRootNodeUUID(),
                            IServiceEntityNodeFieldConstant.UUID, OutboundDelivery.NODENAME,
                            warehouseStoreItem.getClient(), null);
            if (outboundDelivery == null || outboundDelivery.getStatus() == OutboundDelivery.STATUS_PROCESSDONE) {
                // Filter out [done] outbound delivery
                continue;
            }
            if (!ServiceCollectionsHelper.checkNullList(outItemUUIDArray)) {
                Object filterResult = ServiceCollectionsHelper.filterOnline(outboundItem.getUuid(), rawStr -> {
                    return rawStr;
                }, outItemUUIDArray);
                if (filterResult == null) {
                    resultList.add(outboundItem);
                }
            } else {
                // In case don't need to filter home outboundItemUUID
                resultList.add(outboundItem);
            }
        }
        return resultList;
    }

    /**
     * [Internal method]
     *
     * @param outboundItemList
     * @return
     */
    public Map<String, List<String>> groupOutboundByMaterialSKU(List<ServiceEntityNode> outboundItemList) {
        Map<String, List<String>> resultMap = new HashMap<>();
        if (ServiceCollectionsHelper.checkNullList(outboundItemList)) {
            return null;
        }
        for (ServiceEntityNode seNode : outboundItemList) {
            OutboundItem outboundItem = (OutboundItem) seNode;
            if (resultMap.containsKey(outboundItem.getRefMaterialSKUUUID())) {
                List<String> tempOutboundUUIDList = resultMap.get(outboundItem.getRefMaterialSKUUUID());
                ServiceCollectionsHelper.mergeToList(tempOutboundUUIDList, outboundItem.getUuid());
            } else {
                resultMap.put(outboundItem.getRefMaterialSKUUUID(),
                        ServiceCollectionsHelper.asList(outboundItem.getUuid()));
            }
        }
        return resultMap;
    }

    public WarehouseStoreItem getRefWarehouseStoreItem (OutboundItem outboundItem) throws ServiceEntityConfigureException {
        WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) warehouseStoreManager.getEntityNodeByKey(
                outboundItem.getRefStoreItemUUID(), IServiceEntityNodeFieldConstant.UUID,
                WarehouseStoreItem.NODENAME, outboundItem.getClient(), null);
        if (warehouseStoreItem == null) {
            warehouseStoreItem = (WarehouseStoreItem) warehouseStoreManager.getEntityNodeByKey(
                    outboundItem.getPrevDocMatItemUUID(), IServiceEntityNodeFieldConstant.UUID,
                    WarehouseStoreItem.NODENAME, outboundItem.getClient(), null);
        }
        return warehouseStoreItem;
    }

    public StorageCoreUnit getAvailableStoreItemAmountUnion(
            StoreAvailableStoreItemRequest storeAvailableStoreItemRequest)
            throws ServiceEntityConfigureException, MaterialException {
        return outboundDeliveryWarehouseItemManager.getAvailableStoreItemAmountUnion(storeAvailableStoreItemRequest);
    }

    public void convWarehouseToScanBarcodeResponseModelUI(Warehouse warehouse,
                                                          OutboundScanBarcodeResponseModel outboundScanBarcodeResponseModel) {
        if (warehouse != null && outboundScanBarcodeResponseModel != null) {
            outboundScanBarcodeResponseModel.setBaseWarehouseName(warehouse.getName());
            outboundScanBarcodeResponseModel.setBaseWarehouseId(warehouse.getId());
            outboundScanBarcodeResponseModel.setBaseWarehouseUUID(warehouse.getUuid());
        }
    }

    public void convOutboundDeliveryToUI(OutboundDelivery outboundDelivery,
                                         OutboundDeliveryUIModel outboundDeliveryUIModel)
            throws ServiceEntityInstallationException {
        convOutboundDeliveryToUI(outboundDelivery, outboundDeliveryUIModel, null);
    }

    public void convOutboundDeliveryToUI(OutboundDelivery outboundDelivery,
                                         OutboundDeliveryUIModel outboundDeliveryUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        if (outboundDelivery != null) {
            docFlowProxy.convDocumentToUI(outboundDelivery, outboundDeliveryUIModel, logonInfo);
            outboundDeliveryUIModel.setNote(outboundDelivery.getNote());
            outboundDeliveryUIModel.setRefWarehouseUUID(outboundDelivery.getRefWarehouseUUID());
            outboundDeliveryUIModel.setStatus(outboundDelivery.getStatus());
            outboundDeliveryUIModel.setProductionBatchNumber(outboundDelivery.getProductionBatchNumber());
            outboundDeliveryUIModel.setPurchaseBatchNumber(outboundDelivery.getPurchaseBatchNumber());
            if (logonInfo != null) {
                Map<Integer, String> statusMap = this.initStatusMap(logonInfo.getLanguageCode());
                outboundDeliveryUIModel.setStatusValue(statusMap.get(outboundDelivery.getStatus()));
            }
            outboundDeliveryUIModel.setRefWarehouseAreaUUID(outboundDelivery.getRefWarehouseAreaUUID());
            if (logonInfo != null) {
                Map<Integer, String> planCategoryMap = this.initPlanCategoryMap(logonInfo.getLanguageCode());
                outboundDeliveryUIModel.setPlanCategoryValue(planCategoryMap.get(outboundDelivery.getPlanCategory()));
            }
            outboundDeliveryUIModel.setPlanCategory(outboundDelivery.getPlanCategory());
            if (outboundDelivery.getPlanExecuteDate() != null) {
                outboundDeliveryUIModel.setPlanExecuteDate(
                        DefaultDateFormatConstant.DATE_MIN_FORMAT.format(outboundDelivery.getPlanExecuteDate()));
            }
            outboundDeliveryUIModel.setGrossPrice(
                    ServiceEntityDoubleHelper.trancateDoubleScale2(outboundDelivery.getGrossPrice()));
            if (outboundDelivery.getShippingTime() != null) {
                outboundDeliveryUIModel.setShippingTime(
                        DefaultDateFormatConstant.DATE_FORMAT.format(outboundDelivery.getShippingTime()));
            }
            outboundDeliveryUIModel.setShippingPoint(outboundDelivery.getShippingPoint());
            outboundDeliveryUIModel.setFreightCharge(outboundDelivery.getFreightCharge());
            this.initFreightChargeType();
            outboundDeliveryUIModel.setFreightChargeType(outboundDelivery.getFreightChargeType());
            outboundDeliveryUIModel.setFreightChargeTypeValue(
                    this.freightChargeTypeMap.get(outboundDelivery.getFreightChargeType()));
        }
    }

    public void convUIToOutboundDelivery(OutboundDeliveryUIModel outboundDeliveryUIModel, OutboundDelivery rawEntity)
            throws ParseException {
        docFlowProxy.convUIToDocument(outboundDeliveryUIModel, rawEntity);
        if (!ServiceEntityStringHelper.checkNullString(outboundDeliveryUIModel.getRefWarehouseAreaUUID())) {
            rawEntity.setRefWarehouseAreaUUID(outboundDeliveryUIModel.getRefWarehouseAreaUUID());
        }
        if (!ServiceEntityStringHelper.checkNullString(outboundDeliveryUIModel.getRefWarehouseUUID())) {
            rawEntity.setRefWarehouseUUID(outboundDeliveryUIModel.getRefWarehouseUUID());
        }
        if (!ServiceEntityStringHelper.checkNullString(outboundDeliveryUIModel.getNote())) {
            rawEntity.setNote(outboundDeliveryUIModel.getNote());
        }
        if (outboundDeliveryUIModel.getPlanCategory() != 0) {
            rawEntity.setPlanCategory(outboundDeliveryUIModel.getPlanCategory());
        }
        if (!ServiceEntityStringHelper.checkNullString(outboundDeliveryUIModel.getPlanExecuteDate())) {
            try {
                rawEntity.setPlanExecuteDate(
                        DefaultDateFormatConstant.DATE_FORMAT.parse(outboundDeliveryUIModel.getPlanExecuteDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
        if (!ServiceEntityStringHelper.checkNullString(outboundDeliveryUIModel.getProductionBatchNumber())) {
            rawEntity.setProductionBatchNumber(outboundDeliveryUIModel.getProductionBatchNumber());
        }
        if (!ServiceEntityStringHelper.checkNullString(outboundDeliveryUIModel.getPurchaseBatchNumber())) {
            rawEntity.setPurchaseBatchNumber(outboundDeliveryUIModel.getPurchaseBatchNumber());
        }
        rawEntity.setGrossPrice(outboundDeliveryUIModel.getGrossPrice());
        rawEntity.setName(outboundDeliveryUIModel.getName());
        if (!ServiceEntityStringHelper.checkNullString(outboundDeliveryUIModel.getShippingTime())) {
            try {
                rawEntity.setShippingTime(
                        DefaultDateFormatConstant.DATE_FORMAT.parse(outboundDeliveryUIModel.getShippingTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            } catch (ParseException e) {
                // do nothing
            }
        }
        rawEntity.setShippingPoint(outboundDeliveryUIModel.getShippingPoint());
        rawEntity.setFreightCharge(outboundDeliveryUIModel.getFreightCharge());
        rawEntity.setFreightChargeType(outboundDeliveryUIModel.getFreightChargeType());
    }

    public void convWarehouseAreaToUI(WarehouseArea warehouseArea, OutboundDeliveryUIModel outboundDeliveryUIModel) {
        if (warehouseArea != null) {
            outboundDeliveryUIModel.setRefWarehouseAreaUUID(warehouseArea.getUuid());
            outboundDeliveryUIModel.setRefWarehouseAreaId(warehouseArea.getId());
            outboundDeliveryUIModel.setRefWarehouseAreaName(warehouseArea.getName());
        }
    }

    public void convWarehouseToUI(Warehouse warehouse, OutboundDeliveryUIModel outboundDeliveryUIModel) {
        if (warehouse != null) {
            outboundDeliveryUIModel.setRefWarehouseId(warehouse.getId());
            outboundDeliveryUIModel.setRefWarehouseName(warehouse.getName());
        }
    }

    public void convOutboundItemToStoreItemUI(OutboundItem outboundItem,
                                                       WarehouseStoreItemUIModel warehouseStoreItemUIModel) {
        if (outboundItem != null) {
            warehouseStoreItemUIModel.setOutboundFee(
                    ServiceEntityDoubleHelper.trancateDoubleScale2(outboundItem.getOutboundFee()));
            warehouseStoreItemUIModel.setOutboundFeeNoTax(
                    ServiceEntityDoubleHelper.trancateDoubleScale2(outboundItem.getItemPriceNoTax()));
            warehouseStoreItemUIModel.setOutboundItemPrice(
                    ServiceEntityDoubleHelper.trancateDoubleScale2(outboundItem.getItemPrice()));
            warehouseStoreItemUIModel.setOutboundItemPriceNoTax(
                    ServiceEntityDoubleHelper.trancateDoubleScale2(outboundItem.getItemPriceNoTax()));
            warehouseStoreItemUIModel.setOutboundUnitPrice(
                    ServiceEntityDoubleHelper.trancateDoubleScale2(outboundItem.getUnitPrice()));
            warehouseStoreItemUIModel.setOutboundUnitPriceNoTax(
                    ServiceEntityDoubleHelper.trancateDoubleScale2(outboundItem.getUnitPriceNoTax()));
            warehouseStoreItemUIModel.setRefInitUnitUUID(outboundItem.getRefUnitUUID());
            warehouseStoreItemUIModel.setRefWarehouseAreaUUID(outboundItem.getName());
        }
    }

    public OutboundDelivery getRefOutboundDeliveryFromStoreItem(String storeItemUUID)
            throws ServiceEntityConfigureException {
        OutboundItem outboundItem =
                (OutboundItem) getEntityNodeByKey(storeItemUUID, "refStoreItemUUID",
                        OutboundItem.NODENAME, null);
        if (outboundItem == null) {
            return null;
        }
        OutboundDelivery outboundDelivery =
                (OutboundDelivery) getEntityNodeByKey(outboundItem.getParentNodeUUID(),
                        IServiceEntityNodeFieldConstant.UUID, OutboundDelivery.NODENAME, null);
        return outboundDelivery;
    }

    public void setWarehouseStoreOutboundInfo(WarehouseStoreItemUIModel warehouseStoreItemUIModel, String storeItemUUID)
            throws ServiceEntityConfigureException {
        OutboundItem outboundItem =
                (OutboundItem) getEntityNodeByKey(storeItemUUID, "refStoreItemUUID",
                        OutboundItem.NODENAME, null);
        if (outboundItem == null) {
            return;
        }
        convOutboundItemToStoreItemUI(outboundItem, warehouseStoreItemUIModel);
        OutboundDelivery outboundDelivery =
                (OutboundDelivery) getEntityNodeByKey(outboundItem.getParentNodeUUID(),
                        IServiceEntityNodeFieldConstant.UUID, OutboundDelivery.NODENAME, null);
    }

    public ServiceDocumentExtendUIModel convOutboundDeliveryToDocExtUIModel(
            OutboundDeliveryUIModel outboundDeliveryUIModel, LogonInfo logonInfo) throws ServiceEntityInstallationException {
        ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
        serviceDocumentExtendUIModel.setRefUIModel(outboundDeliveryUIModel);
        serviceDocumentExtendUIModel.setUuid(outboundDeliveryUIModel.getUuid());
        serviceDocumentExtendUIModel.setParentNodeUUID(outboundDeliveryUIModel.getParentNodeUUID());
        serviceDocumentExtendUIModel.setRootNodeUUID(outboundDeliveryUIModel.getRootNodeUUID());
        serviceDocumentExtendUIModel.setId(outboundDeliveryUIModel.getId());
        serviceDocumentExtendUIModel.setStatus(outboundDeliveryUIModel.getStatus());
        serviceDocumentExtendUIModel.setStatusValue(outboundDeliveryUIModel.getStatusValue());
        serviceDocumentExtendUIModel.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY);
        if(logonInfo != null){
            serviceDocumentExtendUIModel
                    .setDocumentTypeValue(serviceDocumentComProxy
                            .getDocumentTypeValue(IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY,
                                    logonInfo.getLanguageCode()));
        }
        // Logic of reference Date
        String referenceDate = outboundDeliveryUIModel.getUpdatedDate();
        if (ServiceEntityStringHelper.checkNullString(referenceDate)) {
            referenceDate = outboundDeliveryUIModel.getUpdatedDate();
        }
        if (ServiceEntityStringHelper.checkNullString(referenceDate)) {
            referenceDate = outboundDeliveryUIModel.getCreatedDate();
        }
        serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
        return serviceDocumentExtendUIModel;
    }

    public ServiceDocumentExtendUIModel convOutboundItemToDocExtUIModel(OutboundItemUIModel outboundItemUIModel,
                                                                        LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
        docFlowProxy.convDocMatItemUIToDocExtUIModel(outboundItemUIModel, serviceDocumentExtendUIModel, logonInfo,
                IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY);
        serviceDocumentExtendUIModel.setRefUIModel(outboundItemUIModel);
        serviceDocumentExtendUIModel.setId(outboundItemUIModel.getParentDocId());
        serviceDocumentExtendUIModel.setStatus(outboundItemUIModel.getParentDocStatus());
        serviceDocumentExtendUIModel.setStatusValue(outboundItemUIModel.getParentDocStatusValue());
        // Logic of reference Date
        String referenceDate = outboundItemUIModel.getUpdatedDate();
        if (ServiceEntityStringHelper.checkNullString(referenceDate)) {
            referenceDate = outboundItemUIModel.getUpdatedDate();
        }
        if (ServiceEntityStringHelper.checkNullString(referenceDate)) {
            referenceDate = outboundItemUIModel.getCreatedDate();
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
            OutboundDelivery outboundDelivery = (OutboundDelivery) seNode;
            try {
                OutboundDeliveryUIModel outboundDeliveryUIModel =
                        (OutboundDeliveryUIModel) genUIModelFromUIModelExtension(OutboundDeliveryUIModel.class,
                                outboundDeliveryServiceUIModelExtension.genUIModelExtensionUnion().get(0),
                                outboundDelivery, logonInfo, null);
                ServiceDocumentExtendUIModel serviceDocumentExtendUIModel =
                        convOutboundDeliveryToDocExtUIModel(outboundDeliveryUIModel, logonInfo);
                return serviceDocumentExtendUIModel;
            } catch (ServiceModuleProxyException | ServiceEntityConfigureException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, OutboundDelivery.SENAME));
            } catch (ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, OutboundDelivery.SENAME));
            }
        }
        if (OutboundItem.NODENAME.equals(seNode.getNodeName())) {
            OutboundItem outboundItem = (OutboundItem) seNode;
            try {
                OutboundItemUIModel outboundItemUIModel =
                        (OutboundItemUIModel) genUIModelFromUIModelExtension(OutboundItemUIModel.class,
                                outboundItemServiceUIModelExtension.genUIModelExtensionUnion().get(0),
                                outboundItem, logonInfo, null);
                ServiceDocumentExtendUIModel serviceDocumentExtendUIModel =
                        convOutboundItemToDocExtUIModel(outboundItemUIModel, logonInfo);
                return serviceDocumentExtendUIModel;
            } catch (ServiceModuleProxyException | ServiceEntityConfigureException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, OutboundItem.NODENAME));
            } catch (ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, OutboundItem.NODENAME));
            }
        }
        return null;
    }

    @Override
    public String getAuthorizationResource() {
        return IServiceModelConstants.OutboundDelivery;
    }

    @Override
    public ServiceSearchProxy getSearchProxy() {
        return outboundDeliverySearchProxy;
    }

}
