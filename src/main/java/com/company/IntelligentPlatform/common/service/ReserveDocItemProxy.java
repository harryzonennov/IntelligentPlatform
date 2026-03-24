package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.StandardDocFlowDirectionProxy;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReserveDocItemProxy {

    //public final static int RESERVED_STATUS_UNKNOWN = 0;

    public final static int RESERVED_STATUS_FREE = 1;

    public final static int RESERVED_STATUS_PARTLY_FREE = 2;

    public final static int RESERVED_STATUS_OWN = 3;

    public final static int RESERVED_STATUS_SHARED = 4;

    public final static int RESERVED_STATUS_OTHER = 5;

    @Autowired
    protected DocActionExecutionProxyFactory docActionExecutionProxyFactory;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    @Autowired
    protected DocFlowContextProxy docFlowContextProxy;

    public static final String PROPERTIES_RESOURCE = "ReserveDocItem_reservedStatus";

    protected Logger logger = LoggerFactory.getLogger(ReserveDocItemProxy.class);

    protected Map<String, Map<Integer, String>> reservedStatusMapLan;

    public Map<Integer, String> getReservedStatusMap(String languageCode)
            throws ServiceEntityInstallationException {
        if(this.reservedStatusMapLan == null){
            this.reservedStatusMapLan = new HashMap<>();
        }
        return ServiceLanHelper.initDefaultLanguageMap(languageCode, this.reservedStatusMapLan, lanCode->{
            try {
                String path = this.getClass().getResource("").getPath();
                Map<Integer, String> tempStatusMap = serviceDropdownListHelper
                        .getDropDownMap(path + PROPERTIES_RESOURCE, languageCode);
                return tempStatusMap;
            } catch (IOException e) {
                return null;
            }
        });
    }

    /**
     * Reserves a store item by updating its reserved material item-related information.
     *
     * This method locks the target store item, determines its reserved status, and, if eligible,
     * updates its reservation-related metadata to reflect the changes. It ensures the proper
     * synchronization and management of reserved and target document information.
     *
     * @param reservedMatItemUUID The UUID of the reserved material item that will be used for reservation.
     * @param reservedDocType The type of the document that refers to the reserved material item.
     * @param reserveTargetDocType The type of the document for the target reservation.
     * @param baseUUID The base UUID of the store item to be reserved.
     * @param serialLogonInfo The object containing user and organizational context information for this operation.
     * @return {@code true} if the reservation operation was successful; {@code false} otherwise.
     * @throws DocActionException If an error occurs while updating the document or during the reservation process.
     */
    public boolean reserveStoreItem(String reservedMatItemUUID, int reservedDocType, int reserveTargetDocType,
                                    String baseUUID, SerialLogonInfo serialLogonInfo)
            throws DocActionException {
        try {
            // Acquire a write lock on the store item using the base UUID and user reference information.
            ServiceConcurrentProxy.writeLock(baseUUID, serialLogonInfo.getRefUserUUID());
            // Retrieve the retrieve target material item instance and check the reserved status
            DocumentContentSpecifier reserveSourceContentSpecifier =
                    docActionExecutionProxyFactory.getSpecifierByDocType(reserveTargetDocType);
            DocMatItemNode reserveSourceDocMatItemNode =
                    (DocMatItemNode) reserveSourceContentSpecifier.getDocMatItem(baseUUID, serialLogonInfo.getClient());
            int reservedStatus = getReservedStatus(reservedMatItemUUID, reservedDocType, reserveSourceDocMatItemNode);
            // Check if the item is free or partially free to proceed with the reservation.
            if (checkFreeFlag(reservedStatus)) {
                // Update reserved material item information to reserved target information.
                DocFlowContextProxy.updateDocContextUUID(reserveSourceDocMatItemNode, reservedMatItemUUID, reservedDocType,
                        StandardDocFlowDirectionProxy.DOCFLOW_TO_RESERVED_BY);
                reserveSourceContentSpecifier.getDocumentManager()
                        .updateSENode(reserveSourceDocMatItemNode, serialLogonInfo.getRefUserUUID(),
                                serialLogonInfo.getResOrgUUID());
                // Update reserved doc information
                DocumentContentSpecifier<?, ?, ?> reservedContentSpecifier =
                        docActionExecutionProxyFactory.getSpecifierByDocType(reservedDocType);
                DocMatItemNode reservedMatItemNode =
                        (DocMatItemNode) reservedContentSpecifier.getDocMatItem(reservedMatItemUUID,
                                serialLogonInfo.getClient());
                DocFlowContextProxy.updateDocContextUUID(reservedMatItemNode, reserveSourceDocMatItemNode.getUuid(),
                        reserveSourceDocMatItemNode.getHomeDocumentType(), StandardDocFlowDirectionProxy.DOCFLOW_TO_RESERVE_TARGET);
                reservedContentSpecifier.getDocumentManager()
                        .updateSENode(reservedMatItemNode, serialLogonInfo.getRefUserUUID(),
                                serialLogonInfo.getResOrgUUID());
                return true;
            }
            return false;
        } catch (ServiceEntityConfigureException | MaterialException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        } finally {
            ServiceConcurrentProxy.unLockWrite(baseUUID);
        }
    }

    /**
     * Method to reserve document material item by reserved mat item information, target item is online.
     *
     * @param reservedMatItemNode
     * @param reserveSourceDocType
     * @param reserveSourceUUID
     * @param serialLogonInfo
     * @return
     * @throws ServiceEntityConfigureException
     */
    public boolean reserveDocItemOnlineReserved(DocMatItemNode reservedMatItemNode, int reserveSourceDocType,
                                    String reserveSourceUUID, SerialLogonInfo serialLogonInfo)
            throws DocActionException {
        try {
            ServiceConcurrentProxy.writeLock(reserveSourceUUID, serialLogonInfo.getRefUserUUID());
            DocumentContentSpecifier reserveSourceContentSpecifier =
                    docActionExecutionProxyFactory.getSpecifierByDocType(reserveSourceDocType);
            DocMatItemNode reserveSourceDocMatItemNode =
                    (DocMatItemNode) reserveSourceContentSpecifier.getDocMatItem(reserveSourceUUID, serialLogonInfo.getClient());
            String reservedMatItemUUID = reservedMatItemNode.getUuid();
            int reservedDocType = reservedMatItemNode.getHomeDocumentType();
            int reservedStatus = getReservedStatus(reservedMatItemUUID, reservedDocType, reserveSourceDocMatItemNode);
            if (checkFreeFlag(reservedStatus)) {
                // Update reserve target information
                DocFlowContextProxy.updateDocContextUUID(reserveSourceDocMatItemNode, reservedMatItemUUID, reservedDocType,
                        StandardDocFlowDirectionProxy.DOCFLOW_TO_RESERVED_BY);
                reserveSourceContentSpecifier.getDocumentManager()
                        .updateSENode(reserveSourceDocMatItemNode, serialLogonInfo.getRefUserUUID(),
                                serialLogonInfo.getResOrgUUID());
                // Update reserved doc information
                DocFlowContextProxy.updateDocContextUUID(reservedMatItemNode, reserveSourceDocMatItemNode.getUuid(),
                        reserveSourceDocMatItemNode.getHomeDocumentType(), StandardDocFlowDirectionProxy.DOCFLOW_TO_RESERVE_TARGET);
                return true;
            }
            return false;
        } catch (ServiceEntityConfigureException | MaterialException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        } finally {
            ServiceConcurrentProxy.unLockWrite(reserveSourceUUID);
        }
    }

    /**
     * Method to reserve store item by reserved mat item information, target item is online, and don't need lock
     *
     * @param reserveTargetMatItemNode
     * @param reservedDocUUID
     * @param serialLogonInfo
     * @return
     * @throws ServiceEntityConfigureException
     */
    public boolean reserveDocItemOnlineReserveTar(DocMatItemNode reserveTargetMatItemNode, int reservedDocType,
                                                String reservedDocUUID, SerialLogonInfo serialLogonInfo)
            throws DocActionException {
        try {
            ServiceConcurrentProxy.writeLock(reservedDocUUID, serialLogonInfo.getRefUserUUID());
            DocumentContentSpecifier reservedContentSpecifier =
                    docActionExecutionProxyFactory.getSpecifierByDocType(reservedDocType);
            DocMatItemNode reservedDocMatItemNode =
                    (DocMatItemNode) reservedContentSpecifier.getDocMatItem(reservedDocUUID, serialLogonInfo.getClient());
            String reserveTargetMatItemUUID = reserveTargetMatItemNode.getUuid();
            int reserveTargetDocType = reserveTargetMatItemNode.getHomeDocumentType();
            DocFlowContextProxy.updateDocContextUUID(reservedDocMatItemNode, reserveTargetMatItemUUID, reserveTargetDocType,
                    StandardDocFlowDirectionProxy.DOCFLOW_TO_RESERVE_TARGET);
            reservedContentSpecifier.getDocumentManager()
                    .updateSENode(reservedDocMatItemNode, serialLogonInfo.getRefUserUUID(),
                            serialLogonInfo.getResOrgUUID());
            // Update reserved doc information
            DocFlowContextProxy.updateDocContextUUID(reserveTargetMatItemNode, reservedDocMatItemNode.getUuid(),
                    reservedDocMatItemNode.getHomeDocumentType(), StandardDocFlowDirectionProxy.DOCFLOW_TO_RESERVED_BY);
            return true;
        } catch (ServiceEntityConfigureException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        } finally {
            ServiceConcurrentProxy.unLockWrite(reservedDocUUID);
        }
    }

    public static boolean checkFreeFlag(int reservedStatus) {
        return reservedStatus == RESERVED_STATUS_FREE || reservedStatus == RESERVED_STATUS_PARTLY_FREE;
    }

    /**
     * API to free reserve relationship
     *
     * @param baseUUID
     * @param serialLogonInfo
     * @return
     * @throws ServiceEntityConfigureException
     */
    public boolean freeReserve(String baseUUID, int reserveTargetDocType, String reservedMatItemUUID,
                                 int reservedDocType, SerialLogonInfo serialLogonInfo) throws DocActionException {
        // clean next doc info on prev doc item firstly
        docFlowContextProxy.clearDocItemNodeUnit(baseUUID, reservedMatItemUUID, reserveTargetDocType,
                StandardDocFlowDirectionProxy.DOCFLOW_TO_RESERVED_BY, serialLogonInfo);
        // Update next doc information
        docFlowContextProxy.clearDocItemNodeUnit(reservedMatItemUUID, baseUUID, reservedDocType,
                StandardDocFlowDirectionProxy.DOCFLOW_TO_RESERVE_TARGET,
                serialLogonInfo);
        return true;
    }

    /**
     * Determines the reserved status of a material item by examining information
     * from the reserved target material item instance.
     *
     * This method evaluates whether the reserved target material item instance is totally free, partially free,
     * already shared, already own or already reserved, based on its UUID, document type and the specified target material item node.
     *
     *
     * @param reservedMatItemUUID The UUID of the reserved material item.
     * @param reservedDocType The type of the reserved document.
     * @param reserveSourceDocMatItemNode The target node representing the material item in the document.
     * @return An integer representing the reserved status, which could be one of the following:
     *         - {@code RESERVED_STATUS_FREE}: The reserve target material item is completely free to reserve.
     *         - {@code RESERVED_STATUS_OWN}: The reserve target material item is already owned by the current reserved material item.
     *         - {@code RESERVED_STATUS_SHARED}: The material item is currently shared by another document and the current reserved material item.
     *         - {@code RESERVED_STATUS_PARTLY_FREE}: The material item is partially available to reserve.
     *         - {@code RESERVED_STATUS_OTHER}: The material item is under other unknown conditions.
     * @throws DocActionException If an error occurs during document action processing.
     * @throws ServiceEntityConfigureException If there is an issue with service entity configuration.
     * @throws MaterialException If a material-specific exception occurs.
     */
    public int getReservedStatus(String reservedMatItemUUID, int reservedDocType,
                                 DocMatItemNode reserveSourceDocMatItemNode)
            throws DocActionException, ServiceEntityConfigureException, MaterialException {
        // If the main reserved material item UUID is empty on the reserved material item instance, the material item is completely free.
        if (ServiceEntityStringHelper.checkNullString(reserveSourceDocMatItemNode.getReservedMatItemUUID())) {
            return RESERVED_STATUS_FREE;
        }
        // Check if the reserved target material item instance is already reserved, and determine whether the status is `own` or `shared`.
        int preStatus = getRerStSharedOrOwn(reservedMatItemUUID, reservedDocType, reserveSourceDocMatItemNode);
        if(preStatus > 0){
            return preStatus;
        }
        // Retrieving the existing document flow context unit if available.
        DocFlowContextUnit existedDocFlowContextUnit =
                DocFlowContextProxy.getContextUnit(reserveSourceDocMatItemNode, StandardDocFlowDirectionProxy.DOCFLOW_TO_RESERVED_BY,
                        reservedMatItemUUID);
        if (existedDocFlowContextUnit != null) {
            // If the current document has already occupied the material item, return "shared" status.
            return RESERVED_STATUS_SHARED;
        } else {
            // If the material item is not currently occupied, check its availability.
            DocumentContentSpecifier<?, ?, ?> reserveSourceContentSpecifier =
                    docActionExecutionProxyFactory.getSpecifierByDocType(
                            reserveSourceDocMatItemNode.getHomeDocumentType());
            DocumentContentSpecifier<?, ?, ?> reservedContentSpecifier =
                    docActionExecutionProxyFactory.getSpecifierByDocType(reservedDocType);
            boolean checkRequests = reserveSourceContentSpecifier.checkReserveRequest(reserveSourceDocMatItemNode,
                    reservedContentSpecifier.getDocMatItemRequestAmount(reservedMatItemUUID,
                            reserveSourceDocMatItemNode.getClient()));
            if (checkRequests) {
                return RESERVED_STATUS_PARTLY_FREE;
            } else {
                return RESERVED_STATUS_OTHER;
            }
        }
    }

    private int getRerStSharedOrOwn(String reservedMatItemUUID, int reservedDocType,
                                  DocMatItemNode reserveSourceDocMatItemNode) throws DocActionException {
        List<DocFlowContextUnit> docFlowContextUnitList =
                DocFlowContextProxy.getDocContextUUIDValue(reserveSourceDocMatItemNode,
                        StandardDocFlowDirectionProxy.DOCFLOW_TO_RESERVED_BY);
        if (reserveSourceDocMatItemNode.getReservedMatItemUUID().equals(reservedMatItemUUID) &&
                reserveSourceDocMatItemNode.getReservedDocType() == reservedDocType) {
            if (ServiceCollectionsHelper.checkNullList(docFlowContextUnitList) || docFlowContextUnitList.size() == 0) {
                return RESERVED_STATUS_OWN;
            } else {
                if(docFlowContextUnitList.size() == 1 && docFlowContextUnitList.get(0).getRefUUID().equals(reservedMatItemUUID)){
                    return RESERVED_STATUS_OWN;
                }
                return RESERVED_STATUS_SHARED;
            }
        }
        return 0;
    }

    public StorageCoreUnit calculateReservedAmount(DocMatItemNode reserveSourceDocMatItemNode)
            throws DocActionException, ServiceEntityConfigureException, MaterialException {
        List<DocFlowContextUnit> docFlowContextUnitList =
                DocFlowContextProxy.getDocContextUUIDValue(reserveSourceDocMatItemNode,
                        StandardDocFlowDirectionProxy.DOCFLOW_TO_RESERVED_BY);
        if (ServiceCollectionsHelper.checkNullList(docFlowContextUnitList)) {
            return new StorageCoreUnit(reserveSourceDocMatItemNode.getRefMaterialSKUUUID(),
                    reserveSourceDocMatItemNode.getRefUnitUUID(), 0);
        }
        List<StorageCoreUnit> storageCoreUnitList = new ArrayList<>();
        for (DocFlowContextUnit docFlowContextUnit : docFlowContextUnitList) {
            DocumentContentSpecifier<?, ?, ?> documentContentSpecifier =
                    docActionExecutionProxyFactory.getSpecifierByDocType(docFlowContextUnit.getDocumentType());
            storageCoreUnitList.add(documentContentSpecifier.getDocMatItemRequestAmount(docFlowContextUnit.getRefUUID(),
                    reserveSourceDocMatItemNode.getClient()));
        }
        return materialStockKeepUnitManager.mergeStorageUnitCore(storageCoreUnitList,
                reserveSourceDocMatItemNode.getClient());
    }


}
