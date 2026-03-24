package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityManagerFactoryInContext;
import com.company.IntelligentPlatform.common.service.AuthorizationManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.SystemConfigureCategoryManager;
import com.company.IntelligentPlatform.common.model.IServiceEntityCommonFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ReferenceNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ActionCode;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SystemConfigureCategory;

/**
 * Common service document helper proxy class
 *
 * @author Zhang, Hang
 */
@Service
public class ServiceDocumentComProxy {

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    @Autowired
    protected SystemConfigureCategoryManager systemConfigureCategoryManager;

    @Autowired
    protected ServiceEntityManagerFactoryInContext serviceEntityManagerFactoryInContext;

    @Autowired
    protected AuthorizationManager authorizationManager;

    private Map<Integer, String> documentTypeKeyMap;

    private Map<Integer, String> documentTypeItemMap;

    private Map<Integer, String> documentTypeMap;

    private Map<String, Integer> documentModelNameMap;

    private final Map<String, Map<Integer, String>> documentTypeMapLan = new HashMap<>();

    private Map<String, Map<String, String>> extendDocumentTypeMapLan = new HashMap<>();

    /**
     * Initialize the mapping Document Type to root model name
     *
     * @return
     */
    public Map<Integer, String> initDocumentTypeKeyMap() {
        if (documentTypeKeyMap == null || documentTypeKeyMap.size() == 0) {
            documentTypeKeyMap = new HashMap<>();
            documentTypeKeyMap.put(IDefDocumentResource.DOCUMENT_TYPE_VOUCHER,
                    IServiceModelConstants.FinAccount);
            documentTypeKeyMap.put(IDefDocumentResource.DOCUMENT_TYPE_RECEIPT,
                    IServiceModelConstants.FinAccount);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_BOOKINGNOTE,
                    IServiceModelConstants.BookingNote);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_VEHICLERUNORDER,
                    IServiceModelConstants.VehicleRunOrder);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_VEHICLERUNORDERCONTRACT,
                    IServiceModelConstants.VehicleRunOrderContract);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_TRANSITORDER,
                    IServiceModelConstants.TransitOrder);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY,
                    IServiceModelConstants.InboundDelivery);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY,
                    IServiceModelConstants.OutboundDelivery);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_CHECKORDER,
                    IServiceModelConstants.InventoryCheckOrder);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_TRANSFER,
                    IServiceModelConstants.InventoryTransferOrder);
            documentTypeKeyMap.put(IDefDocumentResource.DOCUMENT_TYPE_PURCHASE,
                    IServiceModelConstants.PurchaseOrder);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_SALESORDER,
                    IServiceModelConstants.SalesOrder);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER,
                    IServiceModelConstants.ProductionOrder);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_SALESRETURNORDER,
                    IServiceModelConstants.SalesReturnOrder);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT,
                    IServiceModelConstants.SalesContract);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT,
                    IServiceModelConstants.PurchaseContract);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_BIDDINGINVITATIONORDER,
                    IServiceModelConstants.BidInvitationOrder);
            documentTypeKeyMap.put(IDefDocumentResource.DOCUMENT_TYPE_INQUIRY,
                    IServiceModelConstants.Inquiry);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_PRODORDERREPORT,
                    IServiceModelConstants.ProdOrderReport);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM,
                    IServiceModelConstants.WarehouseStore);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_PRODPICKINGORDER,
                    IServiceModelConstants.ProdPickingOrder);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_PRODRETURNORDER,
                    IServiceModelConstants.ProdPickingOrder);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONPLAN,
                    IServiceModelConstants.ProductionPlan);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER,
                    IServiceModelConstants.QualityInspectOrder);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_PRODUCTORDERITEM,
                    IServiceModelConstants.ProductionOrderItem);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_PRODUCTPLANITEM,
                    IServiceModelConstants.ProductionPlanItem);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST,
                    IServiceModelConstants.SalesForcast);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_PURCHASEREQUEST,
                    IServiceModelConstants.PurchaseRequest);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER,
                    IServiceModelConstants.PurchaseReturnOrder);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_BILLOFMATERIALORDER,
                    IServiceModelConstants.BillOfMaterialOrder);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_BILLOFMATERIALTEMPLATE,
                    IServiceModelConstants.BillOfMaterialTemplate);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_WASTEPROCESSORDER,
                    IServiceModelConstants.WasteProcessOrder);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_REPAIRPRODORDER,
                    IServiceModelConstants.RepairProdOrder);
            documentTypeKeyMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_REPAIRPRODORDERITEM,
                    IServiceModelConstants.RepairProdOrderItem);
        }
        return this.documentTypeKeyMap;
    }

    /**
     * Initialize the mapping Document Type to doc item node name
     *
     * @return
     */
    public Map<Integer, String> initDocumentTypeItemMap() {
        if (this.documentTypeItemMap == null
                || this.documentTypeItemMap.size() == 0) {
            this.documentTypeItemMap = new HashMap<>();
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_BOOKINGNOTE,
                    IServiceModelConstants.BookingNoteSKUItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_VEHICLERUNORDER,
                    IServiceModelConstants.VehicleRunOrderLoadingItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_TRANSFER,
                    IServiceModelConstants.InventoryTransferItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY,
                    IServiceModelConstants.InboundItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY,
                    IServiceModelConstants.OutboundItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_CHECKORDER,
                    IServiceModelConstants.InventoryCheckItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_PURCHASE,
                    IServiceModelConstants.PurchaseOrderItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_SALESORDER,
                    IServiceModelConstants.SalesOrderItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER,
                    IServiceModelConstants.ProdOrderTargetMatItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_SALESRETURNORDER,
                    IServiceModelConstants.SalesReturnMaterialItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT,
                    IServiceModelConstants.SalesContractMaterialItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT,
                    IServiceModelConstants.PurchaseContractMaterialItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_BIDDINGINVITATIONORDER,
                    IServiceModelConstants.BidMaterialItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_INQUIRY,
                    IServiceModelConstants.InquiryMaterialItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONPLAN,
                    IServiceModelConstants.ProdPlanTargetMatItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_PRODPICKINGORDER,
                    IServiceModelConstants.ProdPickingRefMaterialItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_PRODRETURNORDER,
                    IServiceModelConstants.ProdPickingRefMaterialItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER,
                    IServiceModelConstants.QualityInspectMatItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_PRODORDERREPORT,
                    IServiceModelConstants.ProdOrderReportItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM,
                    IServiceModelConstants.WarehouseStoreItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_PRODUCTORDERITEM,
                    IServiceModelConstants.ProdOrderItemReqProposal);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_PRODUCTPLANITEM,
                    IServiceModelConstants.ProdPlanItemReqProposal);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST,
                    IServiceModelConstants.SalesForcastMaterialItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_PURCHASEREQUEST,
                    IServiceModelConstants.PurchaseRequestMaterialItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER,
                    IServiceModelConstants.PurchaseReturnMaterialItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_BILLOFMATERIALORDER,
                    IServiceModelConstants.BillOfMaterialItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_BILLOFMATERIALTEMPLATE,
                    IServiceModelConstants.BillOfMaterialTemplateItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_WASTEPROCESSORDER,
                    IServiceModelConstants.WasteProcessMaterialItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_REPAIRPRODORDER,
                    IServiceModelConstants.RepairProdTargetMatItem);
            this.documentTypeItemMap.put(
                    IDefDocumentResource.DOCUMENT_TYPE_REPAIRPRODORDERITEM,
                    IServiceModelConstants.RepairProdItemReqProposal);
        }
        return this.documentTypeItemMap;
    }

    /**
     * Utility method to get Model Name
     *
     * @param seNode
     * @return
     */
    public static String getServiceEntityModelName(ServiceEntityNode seNode) {
        if (seNode == null) {
            return null;
        }
        return ServiceEntityStringHelper.getDefModelId(seNode.getServiceEntityName(), seNode.getNodeName());
    }

    public Map<String, Integer> initDocumentModelNameMap() {
        if (this.documentModelNameMap != null && this.documentModelNameMap.size() > 0) {
            return this.documentModelNameMap;
        }
        this.documentModelNameMap = new HashMap<String, Integer>();
        /*
         * [Step1] Merge document type and root node name
         */
        this.initDocumentTypeKeyMap();
        Set<Integer> keySet = this.documentTypeKeyMap.keySet();
        Iterator<Integer> it = keySet.iterator();
        while (it.hasNext()) {
            int documentType = it.next();
            String modelName = this.documentTypeKeyMap.get(documentType);
            this.documentModelNameMap.put(modelName, documentType);
        }
        /*
         * [Step2] Merge document type and mat item node name
         */
        this.initDocumentTypeItemMap();
        Set<Integer> itemKeySet = this.documentTypeItemMap.keySet();
        Iterator<Integer> itItem = itemKeySet.iterator();
        while (itItem.hasNext()) {
            int documentType = itItem.next();
            String itemNodeName = this.documentTypeItemMap.get(documentType);
            this.documentModelNameMap.put(itemNodeName, documentType);
        }
        return this.documentModelNameMap;
    }

    /**
     * Constants method: Some Fixed doc type
     *
     * @return
     */
    public static int getFixedPrevDocType(int documentType) {
        // if (documentType ==
        // IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY) {
        // return IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY;
        // }
        return 0;
    }

    /**
     * Constants method: Some Fixed doc type
     *
     * @return
     */
    public static int getFixedNextDocType(int documentType) {
        // if (documentType ==
        // IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY) {
        // return IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY;
        // }
        return 0;
    }

    /**
     * Mapping Logic between document type and ServiceEntityName
     *
     * @return
     * @doumentType: int, Document type
     */
    public String getServiceEntityNameByDocumentType(int documentType) {
        this.initDocumentTypeKeyMap();
        return this.documentTypeKeyMap.get(documentType);
    }

    public int getDocumentTypeBySEName(String seName) {
        this.initDocumentTypeKeyMap();
        if (documentTypeKeyMap.containsValue(seName)) {
            for (Map.Entry<Integer, String> entry : this.documentTypeKeyMap
                    .entrySet()) {
                if (seName.equals(entry.getValue())) {
                    return entry.getKey();
                }
            }
        }
        return 0;
    }

    public int getDocumentTypeByModelName(String modelName) {
        this.initDocumentModelNameMap();
        return this.documentModelNameMap.get(modelName);
    }

    /**
     * Constant Logic to return standard document node name
     *
     * @param documentType
     * @return
     */
    @Deprecated
    public String getDocumentNodeName(int documentType) {
        if (documentType == IDefDocumentResource.DOCUMENT_TYPE_PRODUCTORDERITEM) {
            return IServiceModelConstants.ProductionOrderItem;
        }
        if (documentType == IDefDocumentResource.DOCUMENT_TYPE_PRODUCTPLANITEM) {
            return IServiceModelConstants.ProductionPlanItem;
        }
        return ServiceEntityNode.NODENAME_ROOT;
    }


    @Deprecated
    public ServiceEntityNode getDocumentByItemNode(ServiceEntityNode itemNode,
                                                   int documentType) throws ServiceEntityConfigureException {
        if (itemNode == null) {
            return null;
        }
        if (IServiceModelConstants.WarehouseStoreItem.equals(itemNode
                .getNodeName())) {
            return itemNode;
        }

        String documentUUID = itemNode.getParentNodeUUID();
        if (IServiceModelConstants.ProdPickingRefMaterialItem.equals(itemNode
                .getNodeName())) {
            documentUUID = itemNode.getRootNodeUUID();
        }
        return getDocument(documentUUID, documentType, itemNode.getClient());
    }

    /**
     * Logic mapping for document type to reference material item Node name.
     *
     * @param documentType
     * @return
     */
    public String getDocumentMaterialItemNodeName(int documentType) {
        this.initDocumentTypeItemMap();
        return this.documentTypeItemMap.get(documentType);
    }

    /**
     * Get Document manager instance by documentType
     *
     * @param documentType
     * @return
     */
    public ServiceEntityManager getDocumentManager(int documentType) {
        String serviceEntityName = getServiceEntityNameByDocumentType(documentType);
        return serviceEntityManagerFactoryInContext
                .getManagerBySEName(serviceEntityName);
    }

    /**
     * Get document instance from persistence by document UUID and document
     * type. In case need to enhance new document in the future, should also
     * extend this method
     *
     * @param uuid
     * @param documentType
     * @return
     * @throws ServiceEntityConfigureException
     */
    public ServiceEntityNode getDocument(String uuid, int documentType,
                                         String client) throws ServiceEntityConfigureException {
        ServiceEntityManager serviceEntityManager = getDocumentManager(documentType);
        String documentNodeName = getDocumentNodeName(documentType);
        return serviceEntityManager.getEntityNodeByKey(uuid,
                IServiceEntityNodeFieldConstant.UUID, documentNodeName, client,
                null);
    }

    /**
     * @param itemUUID
     * @param documentType
     * @param client
     * @return
     */
    public ServiceEntityNode getDocItemNode(String itemUUID, int documentType,
                                            String client) {
        String targetNodeName = getDocumentMaterialItemNodeName(documentType);
        ServiceEntityManager refDocumentManager = getDocumentManager(documentType);
        if (refDocumentManager == null) {
            return null;
        }
        ServiceEntityNode seNode;
        try {
            seNode = refDocumentManager.getEntityNodeByUUID(itemUUID,
                    targetNodeName, client);
            return seNode;
        } catch (ServiceEntityConfigureException e) {
            return null;
        }
    }

    /**
     * Get document instance from persistence by document UUID and document
     * type. In case need to enhance new document in the future, should also
     * extend this method
     *
     * @param itemUUID
     * @param documentType
     * @return
     * @throws ServiceEntityConfigureException
     */
    @Deprecated
    public ServiceEntityNode getDocumentByItemUUID(String itemUUID,
                                                   int documentType, String client)
            throws ServiceEntityConfigureException {
        ServiceEntityNode itemNode = getDocItemNode(itemUUID, documentType,
                client);
        if (itemNode == null) {
            return null;
        }
        return getDocumentByItemNode(itemNode, documentType);
    }

    /**
     * Get document instance from persistence by document UUID and document
     * type. In case need to enhance new document in the future, should also
     * extend this method
     *
     * @param referenceNode
     * @return
     * @throws ServiceEntityConfigureException
     */
    public ServiceEntityNode getDocumentByReferenceNode(
            ReferenceNode referenceNode) throws ServiceEntityConfigureException {
        ServiceEntityManager serviceEntityManager = null;
        serviceEntityManager = serviceEntityManagerFactoryInContext
                .getManagerBySEName(referenceNode.getRefSEName());
        if (serviceEntityManager != null) {
            return serviceEntityManager.getEntityNodeByKey(
                    referenceNode.getRefUUID(),
                    IServiceEntityNodeFieldConstant.UUID,
                    ServiceEntityNode.NODENAME_ROOT, referenceNode.getClient(),
                    null);
        }

        return null;
    }

    public Map<Integer, String> getSearchDocumentTypeMap(
            List<ServiceEntityNode> rawCategoryList, String client)
            throws ServiceEntityInstallationException,
            ServiceEntityConfigureException {
        Map<Integer, String> documentTypeMap = getDocumentTypeMap(
                rawCategoryList, client);
        documentTypeMap.put(0, ServiceEntityStringHelper.EMPTYSTRING);
        return documentTypeMap;
    }

    public Map<Integer, String> getDocumentTypeMap(
            List<ServiceEntityNode> rawCategoryList, String client)
            throws ServiceEntityInstallationException,
            ServiceEntityConfigureException {
        if (ServiceCollectionsHelper.checkNullList(rawCategoryList)) {
            rawCategoryList = systemConfigureCategoryManager
                    .getEntityNodeListByKey(null, null,
                            SystemConfigureCategory.NODENAME, client, null);
        }
        if (this.documentTypeMap == null) {
            this.documentTypeMap = serviceDropdownListHelper.getUIDropDownMap(
                    ServiceDocumentUIModel.class, "documentType");
        }
        return this.documentTypeMap;
    }

    /**
     * Core method to provide system level document map list
     *
     * @param filterFlag : weather need to filter by system models installation
     * @return
     * @throws ServiceEntityInstallationException
     */
    public Map<Integer, String> getDocumentTypeMap(boolean filterFlag)
            throws ServiceEntityInstallationException {
        if (this.documentTypeMap == null) {
            this.documentTypeMap = serviceDropdownListHelper.getUIDropDownMap(
                    ServiceDocumentUIModel.class, "documentType");
        }
        if (filterFlag) {
            return filterBySystemInstallation(this.documentTypeMap);
        } else {
            return this.documentTypeMap;
        }
    }

    /**
     * Core method to provide system level document map list
     *
     * @param filterFlag : weather need to filter by system models installation
     * @return
     * @throws ServiceEntityInstallationException
     */
    public Map<Integer, String> getDocumentTypeMap(boolean filterFlag,
                                                   String languageCode) throws ServiceEntityInstallationException {
        Map<Integer, String>  documentTypeMap = ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.documentTypeMapLan, ServiceDocumentUIModel.class, "documentType");
        if (filterFlag) {
            return filterBySystemInstallation(documentTypeMap);
        } else {
            return documentTypeMap;
        }

    }

    /**
     * Core method to provide system level document & dummy document map list
     *
     * @param languageCode : weather need to filter by system models installation
     * @return
     * @throws ServiceEntityInstallationException
     */
    public Map<String, String> getExtendDocumentTypeMap(String languageCode)
            throws ServiceEntityInstallationException {
        if (this.extendDocumentTypeMapLan == null) {
            this.extendDocumentTypeMapLan = new HashMap<>();
        }
        return ServiceLanHelper
                .initDefaultLanguageStrMap(
                        languageCode,
                        this.extendDocumentTypeMapLan,
                        lanCode -> {
                            try {
                                Map<String, String> documentTypeMap = ServiceDropdownListHelper
                                        .getStaticUIStrDropDownMap(
                                                ServiceDocumentUIModel.class,
                                                "documentType", lanCode);
                                Map<String, String> dummyDocumentTypeMap = ServiceDropdownListHelper
                                        .getStaticUIStrDropDownMap(
                                                ServiceDocumentUIModel.class,
                                                "dummyDocumentType", lanCode);
                                documentTypeMap.putAll(dummyDocumentTypeMap);
                                return documentTypeMap;
                            } catch (ServiceEntityInstallationException e) {
                                return null;
                            }
                        });
    }
    
    public String getExtendDocumentTypeValue(String key, String languageCode)
            throws ServiceEntityInstallationException {
        Map<String, String> documentTypeMap = getExtendDocumentTypeMap(languageCode);
        if(documentTypeMap == null){
            return null;
        }
        return documentTypeMap.get(key);
    }

    protected Map<Integer, String> filterBySystemInstallation(
            Map<Integer, String> rawMap) {
        if (rawMap == null) {
            return null;
        }
        Set<Integer> keySet = rawMap.keySet();
        Iterator<Integer> it = keySet.iterator();
        Map<Integer, String> resultMap = new HashMap<>();
        while (it.hasNext()) {
            Integer documentType = it.next();
            // TODO temporary special case
            if (documentType == IDefDocumentResource.DOCUMENT_TYPE_SALESORDER) {
                continue;
            }
            if (documentType == IDefDocumentResource.DOCUMENT_TYPE_SALESRETURNORDER) {
                continue;
            }
            if (documentType == IDefDocumentResource.DOCUMENT_TYPE_PURCHASE) {
                continue;
            }
            ServiceEntityManager serviceEntityManager = getDocumentManager(documentType);
            if (serviceEntityManager != null) {
                resultMap.put(documentType, rawMap.get(documentType));
            }
        }
        return resultMap;
    }

    public Map<Integer, String> getSearchDocumentTypeMap()
            throws ServiceEntityInstallationException {
        Map<Integer, String> documentTypeMap = serviceDropdownListHelper
                .getUIDropDownMap(ServiceDocumentUIModel.class, "documentType");
        documentTypeMap.put(0, ServiceEntityStringHelper.EMPTYSTRING);
        return documentTypeMap;
    }

    public String getDocumentTypeValue(int key)
            throws ServiceEntityInstallationException {
        Map<Integer, String> documentTypeMap = getDocumentTypeMap(false);
        return documentTypeMap.get(key);
    }

    public String getDocumentTypeValue(int key, String languageCode)
            throws ServiceEntityInstallationException {
        Map<Integer, String> documentTypeMap = getDocumentTypeMap(false,
                languageCode);
        return documentTypeMap.get(key);
    }


    /**
     * Get Document instance by reference material SKU UUID
     *
     * @param refMaterialSKUUUID
     * @param documentType
     * @param fieldName          : [optional] field name to store material SKU UUID, such as
     *                           'refUUID' or 'refMaterialSKUUUID'
     * @param client
     * @return
     */
    public ServiceEntityNode getItemNodeByMaterial(String refMaterialSKUUUID,
                                                   int documentType, String fieldName, String client) {
        try {
            ServiceEntityManager refDocumentManager = getDocumentManager(documentType);
            String targetNodeName = getDocumentMaterialItemNodeName(documentType);
            ServiceEntityNode itemNode = null;
            if (!ServiceEntityStringHelper.checkNullString(fieldName)) {
                itemNode = refDocumentManager.getEntityNodeByKey(
                        refMaterialSKUUUID, fieldName, targetNodeName, client,
                        null);
            } else {
                itemNode = _getItemNodeByMaterial(refDocumentManager,
                        refMaterialSKUUUID, targetNodeName, documentType,
                        client);
            }
            return itemNode;
        } catch (ServiceEntityConfigureException e) {
            return null;
        }
    }

    /**
     * Trying to get Item node instance by Material UUID by default field name.
     *
     * @param refDocumentManager
     * @param refMaterialSKUUUID
     * @param targetNodeName
     * @param documentType
     * @param client
     * @return
     */
    protected ServiceEntityNode _getItemNodeByMaterial(
            ServiceEntityManager refDocumentManager, String refMaterialSKUUUID,
            String targetNodeName, int documentType, String client) {
        List<String> possibleFieldNameList = ServiceCollectionsHelper.asList(
                IServiceEntityCommonFieldConstant.REFMATERIALSKUUUID);
        ServiceEntityNode serviceEntityNode = _getItemNodeByFieldNameList(
                refDocumentManager, refMaterialSKUUUID, possibleFieldNameList,
                targetNodeName, documentType, client);
        return serviceEntityNode;
    }

    protected ServiceEntityNode _getItemNodeByFieldNameList(
            ServiceEntityManager refDocumentManager, String value,
            List<String> possibleFieldNameList, String targetNodeName,
            int documentType, String client) {
        if (ServiceCollectionsHelper.checkNullList(possibleFieldNameList)) {
            return null;
        }
        for (String fieldName : possibleFieldNameList) {
            try {
                ServiceEntityNode itemNode = refDocumentManager
                        .getEntityNodeByKey(value, fieldName, targetNodeName,
                                client, null);
                if (itemNode != null) {
                    return itemNode;
                }
            } catch (ServiceEntityConfigureException e) {
                // Skip and continue to next
                continue;
            }
        }
        return null;

    }

    /**
     * Get Reference document flow list by reference Material SKU UUID
     *
     * @param refMaterialSKUUUID
     * @param docTypeList
     * @param client
     * @return
     */
    public List<ServiceDocumentExtendUIModel> getDocFlowListByMaterial(
            String refMaterialSKUUUID, List<Integer> docTypeList,
            String client, LogonInfo logonInfo) {
        if (ServiceCollectionsHelper.checkNullList(docTypeList)) {
            return null;
        }
        List<ServiceDocumentExtendUIModel> resultList = new ArrayList<>();
        int index = 1;
        for (int i = 0; i < docTypeList.size(); i++) {
            ServiceEntityManager refDocumentManager = getDocumentManager(docTypeList
                    .get(i));
            if (refDocumentManager == null) {
                continue;
            }
            ServiceEntityNode seNode = getItemNodeByMaterial(
                    refMaterialSKUUUID, docTypeList.get(i), null, client);
            if (seNode == null) {
                continue;
            }
            ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = refDocumentManager
                    .convToDocumentExtendUIModel(seNode, logonInfo);
            serviceDocumentExtendUIModel.setProcessIndex(index++);
            if (serviceDocumentExtendUIModel != null) {
                resultList.add(serviceDocumentExtendUIModel);
            }
        }
        return resultList;
    }

    /**
     * Get All Document flow list Wrapper from current seNode
     *
     * @param homeSENode
     * @param homeSEManager
     * @param acId
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<ServiceDocumentExtendUIModel>  getDocFlowList(
            ServiceEntityNode homeSENode, ServiceEntityManager homeSEManager,
            String acId, LogonInfo logonInfo)
            throws ServiceEntityConfigureException {
        int processIndex = 0;
        List<ServiceDocumentExtendUIModel> resultList = new ArrayList<>();
        if(homeSENode == null){
            return resultList;
        }
        Map<AuthorizationObject, List<ActionCode>> authorizationActionCodeMap = authorizationManager
                .getAuthorizationACListMap(logonInfo.getLogonUser());
        String localAcId = acId;
        // default AC ID
        if (ServiceEntityStringHelper.checkNullString(localAcId)) {
            localAcId = ISystemActionCode.ACID_VIEW;
        }
        List<ServiceDocumentExtendUIModel> prevDocFlowList = this
                .getPrevDocFlowList(homeSENode, processIndex, null, logonInfo,
                        localAcId, authorizationActionCodeMap);
        List<ServiceDocumentExtendUIModel> nextDocFlowList = this
                .getNextDocFlowList(homeSENode, processIndex, null, logonInfo,
                        localAcId, authorizationActionCodeMap);

        if (!ServiceCollectionsHelper.checkNullList(prevDocFlowList)) {
            resultList.addAll(prevDocFlowList);
        }
        if (!ServiceCollectionsHelper.checkNullList(nextDocFlowList)) {
            resultList.addAll(nextDocFlowList);
        }
        if (homeSEManager != null) {
            ServiceDocumentExtendUIModel homeExtendUIModel = homeSEManager
                    .convToDocumentExtendUIModel(homeSENode, logonInfo);
            if (homeExtendUIModel != null) {
                resultList.add(homeExtendUIModel);
            }
        }
        if (ServiceCollectionsHelper.checkNullList(resultList)) {
            return null;
        }
        /*
         * [Step2] Compare & sort resultList
         */
        Collections.sort(resultList,
                new Comparator<ServiceDocumentExtendUIModel>() {
                    public int compare(ServiceDocumentExtendUIModel o1,
                                       ServiceDocumentExtendUIModel o2) {
                        Integer processIndex1 = o1.getProcessIndex();
                        Integer processIndex2 = o2.getProcessIndex();
                        return processIndex1.compareTo(processIndex2);
                    }
                });
        // update new processIndex
        for (int i = 0; i < resultList.size(); i++) {
            ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = resultList
                    .get(i);
            serviceDocumentExtendUIModel.setProcessIndex(i + 1);
        }
        return resultList;
    }

    /**
     * Core Logic to get Previous Document type from SE instance
     *
     * @param homeSENode
     * @return
     */
    public int getPrevDocType(ServiceEntityNode homeSENode) {
        int prevDocType = ServiceEntityFieldsHelper
                .getIntServiceFieldValueWrapper(homeSENode,
                        IServiceEntityCommonFieldConstant.PREVDOCTYPE);
        if (prevDocType == 0) {
            // Then trying to get previous doc in reflective way
            prevDocType = ServiceEntityFieldsHelper
                    .getIntServiceFieldValueWrapper(homeSENode,
                            IServiceEntityCommonFieldConstant.REFPREVORDERTYPE);
        }
        return prevDocType;
    }

    public String getPrevMatItemUUID(ServiceEntityNode homeSENode) {
        String prevDocMatItemUUID = ServiceEntityFieldsHelper
                .getStrServiceFieldValueWrapper(homeSENode,
                        IServiceEntityCommonFieldConstant.PREVDOCMATITEMUUID);
        if (ServiceEntityStringHelper.checkNullString(prevDocMatItemUUID)) {
            prevDocMatItemUUID = ServiceEntityFieldsHelper
                    .getStrServiceFieldValueWrapper(
                            homeSENode,
                            IServiceEntityCommonFieldConstant.REFPREVMATITEMUUID);
        }
        return prevDocMatItemUUID;
    }

    public String getNextMatItemUUID(ServiceEntityNode homeSENode) {
        String prevDocMatItemUUID = ServiceEntityFieldsHelper
                .getStrServiceFieldValueWrapper(homeSENode,
                        IServiceEntityCommonFieldConstant.NEXTDOCMATITEMUUID);
        if (ServiceEntityStringHelper.checkNullString(prevDocMatItemUUID)) {
            prevDocMatItemUUID = ServiceEntityFieldsHelper
                    .getStrServiceFieldValueWrapper(
                            homeSENode,
                            IServiceEntityCommonFieldConstant.REFNEXTMATITEMUUID);
        }
        return prevDocMatItemUUID;
    }

    public double getAmount(ServiceEntityNode homeSENode) {
        double amount = ServiceEntityFieldsHelper
                .getDoubleServiceFieldValueWrapper(homeSENode,
                        IServiceEntityCommonFieldConstant.AMOUNT);
        if (amount == 0) {
            amount = ServiceEntityFieldsHelper
                    .getDoubleServiceFieldValueWrapper(homeSENode,
                            IServiceEntityCommonFieldConstant.REQUIREAMOUNT);
        }
        return amount;
    }

    /**
     * Core Logic to get previous Doc Material Item instance by checking field
     * dynamic
     *
     * @param homeSENode
     * @return
     */
    public ServiceEntityNode getPrevDocItem(ServiceEntityNode homeSENode) {
        /*
         * Check fixed prev doc type firstly
         */
        int prevDocType = getPrevDocType(homeSENode);
        if (prevDocType == 0) {
            return null;
        }
        String prevDocMatItemUUID = getPrevMatItemUUID(homeSENode);
        ServiceEntityManager refDocumentManager = getDocumentManager(prevDocType);
        if (refDocumentManager == null) {
            return null;
        }
        String targetNodeName = getDocumentMaterialItemNodeName(prevDocType);
        ServiceEntityNode seNode;
        try {
            seNode = refDocumentManager.getEntityNodeByUUID(prevDocMatItemUUID,
                    targetNodeName, homeSENode.getClient());
        } catch (ServiceEntityConfigureException e) {
            return null;
        }
        return seNode;
    }

    /**
     * Core Logic to get Next Document type from SE instance
     *
     * @param homeSENode
     * @return
     */
    public int getNextDocType(ServiceEntityNode homeSENode) {
        int documentType = getDocumentTypeBySEName(homeSENode
                .getServiceEntityName());
        int nextDocType = getFixedNextDocType(documentType);
        if (nextDocType == 0) {
            // Then trying to get next doc in reflective way
            nextDocType = ServiceEntityFieldsHelper
                    .getIntServiceFieldValueWrapper(homeSENode,
                            IServiceEntityCommonFieldConstant.NEXTDOCTYPE);
        }
        if (nextDocType == 0) {
            // Then trying to get next doc in reflective way
            nextDocType = ServiceEntityFieldsHelper
                    .getIntServiceFieldValueWrapper(homeSENode,
                            IServiceEntityCommonFieldConstant.REFNEXTORDERTYPE);
        }
        // Speical Case
        if (homeSENode.getNodeName().equals(
                IServiceModelConstants.InboundItem)) {
            nextDocType = IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM;
        }
        return nextDocType;
    }

    /**
     * Core Logic to get next Doc Material Item instance by checking field
     * dynamic
     *
     * @param homeSENode
     * @return
     */
    public ServiceEntityNode getNextDocItem(ServiceEntityNode homeSENode) {
        /*
         * Check fixed next doc type firstly
         */
        int nextDocType = getNextDocType(homeSENode);
        if (nextDocType == 0) {
            return null;
        }
        String nextDocMatItemUUID = getNextMatItemUUID(homeSENode);
        ServiceEntityManager refDocumentManager = getDocumentManager(nextDocType);
        if (refDocumentManager == null) {
            return null;
        }
        String targetNodeName = getDocumentMaterialItemNodeName(nextDocType);
        ServiceEntityNode seNode;
        try {
            seNode = refDocumentManager.getEntityNodeByUUID(nextDocMatItemUUID,
                    targetNodeName, homeSENode.getClient());
        } catch (ServiceEntityConfigureException e) {
            return null;
        }
        return seNode;
    }

    /**
     * Get All Previous Document flow list by calling recursively
     *
     * @param homeSENode
     * @param processIndex
     * @return
     */
    public List<ServiceDocumentExtendUIModel> getPrevDocFlowList(
            ServiceEntityNode homeSENode,
            int processIndex,
            List<String> uuidCache,
            LogonInfo logonInfo,
            String acId,
            Map<AuthorizationObject, List<ActionCode>> authorizationActionCodeMap) {
        if (uuidCache == null) {
            uuidCache = new ArrayList<>();
        }
        if (uuidCache.contains(homeSENode.getUuid())) {
            // In case duplicate
            return null;
        }
        uuidCache.add(homeSENode.getUuid());
        ServiceEntityNode seNode = getPrevDocItem(homeSENode);
        if (seNode == null) {
            return null;
        }
        int prevDocType = getPrevDocType(homeSENode);
        if (prevDocType == 0) {
            return null;
        }
        ServiceEntityManager refDocumentManager = getDocumentManager(prevDocType);
        if (refDocumentManager == null) {
            return null;
        }
        ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = refDocumentManager
                .convToDocumentExtendUIModel(seNode, logonInfo);
        if (serviceDocumentExtendUIModel == null) {
            return null;
        }
        processIndex--;
        serviceDocumentExtendUIModel.setProcessIndex(processIndex);
        List<ServiceDocumentExtendUIModel> prevDocFlowList = getPrevDocFlowList(
                seNode, processIndex, uuidCache, logonInfo, acId,
                authorizationActionCodeMap);
        List<ServiceDocumentExtendUIModel> resultList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(prevDocFlowList)) {
            resultList.addAll(prevDocFlowList);
        }
        if (serviceDocumentExtendUIModel != null) {
            // Check if current user has access to this data
            try {
                boolean accessFlag = authorizationManager
                        .checkDataAccessBySystemAuthorization(
                                logonInfo.getLogonUser(), homeSENode, acId,
                                logonInfo.getHomeOrganization(),
                                logonInfo.getOrganizationList(),
                                authorizationActionCodeMap);
                if (accessFlag) {
                    resultList.add(serviceDocumentExtendUIModel);
                }
            } catch (LogonInfoException | ServiceEntityConfigureException e) {
                // log error and continue
                e.printStackTrace();
            }
        }
        return resultList;
    }

    /**
     * Get All Next Document flow list by calling recursively
     *
     * @param homeSENode
     * @param processIndex
     * @return
     */
    public List<ServiceDocumentExtendUIModel> getNextDocFlowList(
            ServiceEntityNode homeSENode,
            int processIndex,
            List<String> uuidCache,
            LogonInfo logonInfo,
            String acId,
            Map<AuthorizationObject, List<ActionCode>> authorizationActionCodeMap) {
        if (uuidCache == null) {
            uuidCache = new ArrayList<>();
        }
        if (uuidCache.contains(homeSENode.getUuid())) {
            // In case duplicate
            return null;
        }
        uuidCache.add(homeSENode.getUuid());
        ServiceEntityNode seNode = getNextDocItem(homeSENode);
        if (seNode == null) {
            return null;
        }
        int nextDocType = getNextDocType(homeSENode);
        if (nextDocType == 0) {
            return null;
        }
        ServiceEntityManager refDocumentManager = getDocumentManager(nextDocType);
        ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = refDocumentManager
                .convToDocumentExtendUIModel(seNode, logonInfo);
        if (serviceDocumentExtendUIModel == null) {
            return null;
        }
        processIndex++;
        serviceDocumentExtendUIModel.setProcessIndex(processIndex);
        List<ServiceDocumentExtendUIModel> nextDocFlowList = getNextDocFlowList(
                seNode, processIndex, uuidCache, logonInfo, acId,
                authorizationActionCodeMap);
        List<ServiceDocumentExtendUIModel> resultList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(nextDocFlowList)) {
            resultList.addAll(nextDocFlowList);
        }
        if (serviceDocumentExtendUIModel != null) {
            // Check if current user has access to this data
            try {
                boolean accessFlag = authorizationManager
                        .checkDataAccessBySystemAuthorization(
                                logonInfo.getLogonUser(), homeSENode, acId,
                                logonInfo.getHomeOrganization(),
                                logonInfo.getOrganizationList(),
                                authorizationActionCodeMap);
                if (accessFlag) {
                    resultList.add(serviceDocumentExtendUIModel);
                }
            } catch (LogonInfoException | ServiceEntityConfigureException e) {
                // log error and continue
                e.printStackTrace();
            }
        }
        return resultList;
    }
}
