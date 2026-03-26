package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.DocUIModelExtensionBuilder;
import com.company.IntelligentPlatform.common.controller.DocUIModelExtensionFactory;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.IPackageDefI18n;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceDocInitConfigureManager;
import com.company.IntelligentPlatform.common.service.ServiceExtensionManager;
import com.company.IntelligentPlatform.common.service.ServiceExtensionSimModel;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.Account;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
public abstract class DocumentContentSpecifier<R extends ServiceModule, T extends ServiceEntityNode,
        Item extends ServiceEntityNode> {

    @Autowired
    protected ServiceItemIdGenerator serviceItemIdGenerator;

    @Autowired
    protected ServiceExtensionManager serviceExtensionManager;

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    @Autowired
    protected DocInvolvePartyProxy docInvolvePartyProxy;

    @Autowired
    protected ServiceModuleProxy serviceModuleProxy;

    @Autowired
    protected ServiceExcelHandlerProxyFactory serviceExcelHandlerProxyFactory;

    @Autowired
    protected ReserveDocItemProxy reserveDocItemProxy;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected SplitMatItemProxy splitMatItemProxy;

    @Autowired
    protected RegisteredProductManager registeredProductManager;

    @Autowired
    protected DocUIModelExtensionFactory docUIModelExtensionFactory;

    protected Logger logger = LoggerFactory.getLogger(DocumentContentSpecifier.class);

    private final Map<String, Map<Integer, String>> involvePartyLan = new HashMap<>();

    public abstract int getDocumentType();

    public abstract Integer getDocumentStatus(T serviceEntityNode);

    public abstract ServiceEntityManager getDocumentManager();

    public abstract T setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus);

    public R loadServiceModule(String uuid, String client)
            throws ServiceEntityConfigureException, ServiceModuleProxyException {
        DocMetadata docMetadata = getDocMetadata();
        ServiceEntityNode docNode = getDocumentManager()
                .getEntityNodeByUUID(uuid,
                        getDocNodeName(), client);
        try {
            return (R) getDocumentManager()
                    .loadServiceModule(docMetadata.getDocServiceModuleClass(),
                            docNode, getDocServiceUIModelExtension());
        } catch (DocActionException e) {
            throw new ServiceModuleProxyException(ServiceModuleProxyException.PARA_SYSTEM_WRONG, e.getErrorMessage());
        }
    }

    //TODO remove this in sub node
    public ServiceModule loadItemServiceModule(Item item)
            throws ServiceEntityConfigureException, ServiceModuleProxyException {
        DocMetadata docMetadata = getDocMetadata();
        try {
            return getDocumentManager()
                    .loadServiceModule(docMetadata.getItemServiceModuleClass(),
                            item, getItemServiceUIModelExtension());
        } catch (DocActionException e) {
            throw new ServiceModuleProxyException(ServiceModuleProxyException.PARA_SYSTEM_WRONG, e.getErrorMessage());
        }
    }

    public Integer getItemStatus(Item item) {
        DocMatItemNode docMatItemNode = (DocMatItemNode) item;
        return docMatItemNode.getItemStatus();
    }

    public Item setItemStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        DocMatItemNode docMatItemNode = (DocMatItemNode) serviceEntityTargetStatus.getServiceEntityNode();
        docMatItemNode.setItemStatus(serviceEntityTargetStatus.getTargetStatus());
        return (Item) docMatItemNode;
    }

    public T getCoreEntity(R serviceModule) {
        try {
            return (T) ServiceModuleProxy.getCoreServiceEntityNode(serviceModule, getDocNodeInstId());
        } catch (ServiceModuleProxyException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the status which could be allowed for admin deletion
     *
     * @return List status
     */
    public List<Integer> getAdmDeleteStatus() {
        return ServiceCollectionsHelper.asList(DocumentContent.STATUS_INITIAL, DocumentContent.STATUS_REVOKE_SUBMIT,
                DocumentContent.STATUS_ARCHIVED, DocumentContent.STATUS_CANCELED);
    }

    public abstract ServiceDefaultIdGenerateHelper getIdGenerateHelper();

    /**
     * Default way to create document
     *
     * @param client
     * @return
     * @throws DocActionException
     */
    public T createDoc(String client, ServiceEntityNode parentNode) throws DocActionException {
        try {
            T document;
            if (parentNode == null) {
                document = (T) this.getDocumentManager()
                        .newRootEntityNode(client);
            } else {
                document = (T) this.getDocumentManager()
                        .newEntityNode(parentNode, client);
            }
            return document;
        } catch (ServiceEntityConfigureException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        }
    }

    /**
     * Default way to create document with offset
     *
     * @param client
     * @return
     * @throws DocActionException
     */
    public T createDoc(String client, ServiceEntityNode parentNode, int offset) throws DocActionException {
        T document = this.createDoc(client, parentNode);
        ServiceDefaultIdGenerateHelper idGenerateHelper = getIdGenerateHelper();
        if (idGenerateHelper != null && offset > 0) {
            String idWithOffset = idGenerateHelper.genDefaultId(client, offset);
            document.setId(idWithOffset);
        }
        return document;
    }

    public R createDocServiceModule(String client, ServiceEntityNode parentNode) throws DocActionException {
        T document = this.createDoc(client, parentNode);
        return createDocServiceModuleCore(document);
    }

    public R createDocServiceModuleCore(T document) throws DocActionException {
        Class<?> docServiceModuleType = getDocMetadata().getDocServiceModuleClass();
        if (document == null) {
            return null;
        }
        try {
            R docServiceModule =
                    (R) docServiceModuleType.getDeclaredConstructor().newInstance();
            ServiceModuleProxy.setServiceModelFieldValue(docServiceModule,
                    ServiceEntityStringHelper.headerToUpperCase(document.getNodeName()),
                    document);
            return docServiceModule;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        }
    }

    public R createDocServiceModule(String client, ServiceEntityNode parentNode, int offset) throws DocActionException {
        T document = this.createDoc(client, parentNode, offset);
        return createDocServiceModuleCore(document);
    }

    public DocUIModelExtensionBuilder getDocUIModelExtensionBuilder() throws DocActionException {
        return null;
    }

    public Item createItem(T parentDoc) throws DocActionException {
        return createItem(parentDoc, 0);
    }

    public Item createItem(T parentDoc, int offset) throws DocActionException {
        try {
            String itemNodeName = ServiceEntityStringHelper.headerToUpperCase(this.getMatItemNodeInstId());
            Item docMatItem = (Item) this.getDocumentManager()
                    .newEntityNode(parentDoc, itemNodeName);
            String itemId = serviceItemIdGenerator.genItemIdParentUUID(itemNodeName,
                    IServiceEntityNodeFieldConstant.ID, parentDoc.getId(), parentDoc.getUuid(), offset,
                    parentDoc.getClient());
            docMatItem.setId(itemId);
            return docMatItem;
        } catch (SearchConfigureException | ServiceEntityConfigureException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        }
    }

    public ServiceModule createItemServiceModule(R parentServiceModule) throws DocActionException {
        T parentDoc = this.getCoreEntity(parentServiceModule);
        Item itemNode = this.createItem(parentDoc);
        Class<?> itemServiceModuleType = this.getItemServiceModelClass();
        try {
            ServiceModule itemServiceModule =
                    (ServiceModule) itemServiceModuleType.getDeclaredConstructor().newInstance();
            ServiceModuleProxy.setServiceModelFieldValue(itemServiceModule,
                    ServiceEntityStringHelper.headerToUpperCase(this.getMatItemNodeInstId()),
                    itemNode);
            // initial copy parent
            initDefCopyParentDocToItemServiceModule(parentServiceModule, itemServiceModule);
            return itemServiceModule;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        }
    }

    /**
     * Initialize one service entity node instance from parent service entity node or from the parent item in the same entity node
     *
     * @param client: client info
     * @return
     * @throws ServiceEntityConfigureException
     */
    public <T extends ServiceEntityNode> T newEntityNodeFromBaseUUID(InitSubServiceEntityRequest<T> initSubServiceEntityRequest, String client) throws ServiceEntityConfigureException, DocActionException {
        // Try to use base UUID to get instance of parent service entity node
        ServiceEntityManager serviceEntityManager = initSubServiceEntityRequest.getServiceEntityManager() != null ? initSubServiceEntityRequest.getServiceEntityManager() : this.getDocumentManager();
        String baseUUID = initSubServiceEntityRequest.getBaseUUID();
        String parentNodeName = initSubServiceEntityRequest.getParentNodeName();
        String nodeName = initSubServiceEntityRequest.getNodeName();
        // Try to use base UUID to get instance of parent service entity node
        ServiceEntityNode parentNode = serviceEntityManager.getEntityNodeByUUID(baseUUID, parentNodeName, client);
        T parentItem = null;
        if (parentNode == null) {
            // In case the base UUID is for the parent item in the same entity node
            parentItem = (T) serviceEntityManager.getEntityNodeByUUID(baseUUID, nodeName, client);
            if (parentItem == null) {
                // should raise exception
                throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_NO_PARENT_NODE, baseUUID);
            }
            parentNode = serviceEntityManager.getEntityNodeByUUID(parentItem.getParentNodeUUID(), parentNodeName, client);
            if (parentNode == null) {
                // should raise exception
                throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_NO_PARENT_NODE, parentItem.getParentNodeUUID());
            }
        }
        T subEntityNode = (T) serviceEntityManager.newEntityNode(parentNode, initSubServiceEntityRequest.getNodeName());
        if (initSubServiceEntityRequest.getProcessInitSubServiceEntityNode() != null) {
            subEntityNode = initSubServiceEntityRequest.getProcessInitSubServiceEntityNode().execute(parentNode, parentItem, subEntityNode);
        }
        return subEntityNode;
    }

    public static class InitSubServiceEntityRequest<T extends ServiceEntityNode> {

        protected String parentNodeName;

        protected String baseUUID;

        protected String nodeName;

        protected ServiceEntityManager serviceEntityManager;

        protected IProcessInitSubServiceEntityNode<T> processInitSubServiceEntityNode;

        public InitSubServiceEntityRequest() {
        }

        public InitSubServiceEntityRequest(String parentNodeName, String nodeName, String baseUUID, ServiceEntityManager serviceEntityManager) {
            this.parentNodeName = parentNodeName;
            this.baseUUID = baseUUID;
            this.nodeName = nodeName;
            this.serviceEntityManager = serviceEntityManager;
        }

        public InitSubServiceEntityRequest(String parentNodeName, String nodeName, String baseUUID,
                                           IProcessInitSubServiceEntityNode<T> processInitSubServiceEntityNode) {
            this.parentNodeName = parentNodeName;
            this.baseUUID = baseUUID;
            this.nodeName = nodeName;
            this.processInitSubServiceEntityNode = processInitSubServiceEntityNode;
        }

        public InitSubServiceEntityRequest(String parentNodeName, String nodeName, String baseUUID,
                                           ServiceEntityManager serviceEntityManager, IProcessInitSubServiceEntityNode<T> processInitSubServiceEntityNode) {
            this.parentNodeName = parentNodeName;
            this.baseUUID = baseUUID;
            this.nodeName = nodeName;
            this.serviceEntityManager = serviceEntityManager;
            this.processInitSubServiceEntityNode = processInitSubServiceEntityNode;
        }

        public String getParentNodeName() {
            return parentNodeName;
        }

        public void setParentNodeName(String parentNodeName) {
            this.parentNodeName = parentNodeName;
        }

        public String getBaseUUID() {
            return baseUUID;
        }

        public void setBaseUUID(String baseUUID) {
            this.baseUUID = baseUUID;
        }

        public String getNodeName() {
            return nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }

        public ServiceEntityManager getServiceEntityManager() {
            return serviceEntityManager;
        }

        public void setServiceEntityManager(ServiceEntityManager serviceEntityManager) {
            this.serviceEntityManager = serviceEntityManager;
        }

        public IProcessInitSubServiceEntityNode<T> getProcessInitSubServiceEntityNode() {
            return processInitSubServiceEntityNode;
        }

        public void setProcessInitSubServiceEntityNode(IProcessInitSubServiceEntityNode<T> processInitSubServiceEntityNode) {
            this.processInitSubServiceEntityNode = processInitSubServiceEntityNode;
        }
    }

    public interface IProcessInitSubServiceEntityNode<T extends ServiceEntityNode> {

        T execute(ServiceEntityNode parentNode, T parentItem, T newItem)
                throws DocActionException, ServiceEntityConfigureException;

    }

    /**
     * Processes and splits a list of document material items based on their associated material SKUs.
     *
     * @param documentMatItemList A list of `ServiceEntityNode` objects representing the document material items.
     * @param involvePartyMap     A map that specifies involved parties for creating registered product instances.
     * @param serialLogonInfo     Information related to the user's login and session.
     * @return A list of `ServiceEntityNode` objects representing the processed and potentially split document material items.
     * @throws ServiceEntityConfigureException If there is an error during the service entity configuration process.
     * @throws MaterialException               If an issue occurs during material processing.
     */
    public List<ServiceEntityNode> checkAndSplitDocMatItemListForRegProduct(List<ServiceEntityNode> documentMatItemList, Map<Integer, Account> involvePartyMap,
                                                                            SerialLogonInfo serialLogonInfo)
            throws MaterialException, ServiceEntityConfigureException {
        /*
         * [Step 1] Determine if splitting is required by checking the system configuration.
         */
        int checkSplitMan = splitMatItemProxy.getSplitRegProdMandatory(serialLogonInfo.getClient());
        if (checkSplitMan != StandardSwitchProxy.SWITCH_ON) {
            return documentMatItemList;
        }
        /*
         * [Step 2] Split the document material item list based on the relative material SKUs.
         */
        List<ServiceEntityNode> renewDocumentMatItemList =
                splitMatItemProxy.splitDocMatForMaterial(documentMatItemList, involvePartyMap,
                        serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
        // If the updated list is not empty, update the list in the database.
        if (!ServiceCollectionsHelper.checkNullList(renewDocumentMatItemList)) {
            this.getDocumentManager().updateSENodeList(renewDocumentMatItemList, serialLogonInfo.getRefUserUUID(),
                    serialLogonInfo.getResOrgUUID());
        } else {
            return documentMatItemList;
        }
        return renewDocumentMatItemList;
    }

    /**
     * Recursively deletes a list of sub-service entity items, from the current node down to the leaf nodes,
     * based on the specified top parent item key value and the parent item key field name. All items in the
     * list should belong to the same type of service entity, identified by the given node name.
     *
     * @param parentItemUUID:      The UUID of the top parent item used to initiate the search
     * @param parentItemFieldName: The name of the field, which stores the parent item UUID value, and point to the parent item.
     * @param nodeName:            the node name to identify the type of service entity
     * @param logonInfo:           login info
     */
    public void deleteSubItemListRecursive(String parentItemUUID, String parentItemFieldName,
                                           String nodeName, LogonInfo logonInfo) throws DocActionException {
        try {
            List<ServiceEntityNode> subItemListRecursive = getSubItemListRecursive(parentItemUUID, parentItemFieldName, nodeName, logonInfo.getClient());
            if (!ServiceCollectionsHelper.checkNullList(subItemListRecursive)) {
                for (ServiceEntityNode seNode : subItemListRecursive) {
                    this.getDocumentManager().deleteSENode(seNode,
                            logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException | ServiceEntityConfigureException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        }
    }

    /**
     * Recursively retrieves a list of sub-service entity items, from the current node down to the leaf nodes,
     * based on the specified top parent item key value and the parent item key field name. All items in the
     * list should belong to the same type of service entity, identified by the given node name.
     *
     * @param parentItemUUID:      The UUID of the top parent item used to initiate the search
     * @param parentItemFieldName: The name of the field, which stores the parent item UUID value, and point to the parent item.
     * @param nodeName:            the node name to identify the type of service entity
     * @param client:              login client
     * @return A list of ServiceEntityNode objects representing the sub-items of the specified parent item.
     * @throws NoSuchFieldException:            If the specified field does not exist in the parent item
     * @throws IllegalAccessException:          If the field access is not permitted due to accessibility restrictions.
     * @throws ServiceEntityConfigureException: If there is a configuration issue with the service entity.
     */
    public List<ServiceEntityNode> getSubItemListRecursive(String parentItemUUID, String parentItemFieldName,
                                                           String nodeName, String client) throws NoSuchFieldException, IllegalAccessException, ServiceEntityConfigureException {
        List<ServiceEntityNode> rawSubItemList = this.getDocumentManager().getEntityNodeListByKey(null, null, nodeName, client, null);
        return filterSubItemListRecursive(parentItemUUID, parentItemFieldName, rawSubItemList);
    }

    /**
     * Filters a list of sub-service entity items, based on the specified top parent item key value, parent item key field name, and a list of raw items.
     *
     * @param parentItemUUID: UUID of the top parent item used to initiate the search for its sub-items.
     * @param rawSubItemList: A list of potential raw sub-service entity items to be filtered based on the specified criteria.
     * @return A list of ServiceEntityNode objects representing the filtered sub-items of the specified parent item.
     */
    public List<ServiceEntityNode> filterSubItemList(String parentItemUUID, String parentItemFieldName,
                                                     List<ServiceEntityNode> rawSubItemList) throws NoSuchFieldException, IllegalAccessException {
        if (ServiceCollectionsHelper.checkNullList(rawSubItemList)) {
            return null;
        }
        List<ServiceEntityNode> resultList = new ArrayList<>();
        for (ServiceEntityNode seNode : rawSubItemList) {
            String parentItemUUIDValue = ServiceEntityFieldsHelper.getReflectiveObjectValue(seNode, parentItemFieldName).toString();
            if (parentItemUUID
                    .equals(parentItemUUIDValue)) {
                resultList.add(seNode);
            }
        }
        return resultList;
    }

    /**
     * Recursively filters a list of sub-service entity items, from the current node down to the leaf nodes,
     * based on the specified top parent item key value, parent item key field name, and a list of raw items.
     * All items in the resulting list should belong to the same type of service entity, identified by the given node name.
     *
     * @param parentItemUUID      UUID of the top parent item used to initiate the search for its sub-items.
     * @param parentItemFieldName The name of the field containing the parent item UUID value, which points to
     *                            the parent item and facilitates the recursive filtering.
     * @param rawSubItemList      A list of potential raw sub-service entity items to be filtered based on the
     *                            specified criteria.
     * @return A list of ServiceEntityNode objects representing the filtered sub-items of the specified
     * parent item.
     * @throws NoSuchFieldException   If the specified field does not exist in a sub-item.
     * @throws IllegalAccessException If access to the field is restricted, preventing filtering.
     */
    public List<ServiceEntityNode> filterSubItemListRecursive(
            String parentItemUUID, String parentItemFieldName, List<ServiceEntityNode> rawSubItemList) throws NoSuchFieldException, IllegalAccessException {
        List<ServiceEntityNode> directSubItemList = filterSubItemList(
                parentItemUUID, parentItemFieldName, rawSubItemList);
        if (ServiceCollectionsHelper.checkNullList(rawSubItemList)) {
            return null;
        }
        List<ServiceEntityNode> resultList = new ArrayList<>(directSubItemList);
        for (ServiceEntityNode seNode : directSubItemList) {
            List<ServiceEntityNode> subItemList = filterSubItemListRecursive(
                    seNode.getUuid(), parentItemFieldName, rawSubItemList);
            if (!ServiceCollectionsHelper.checkNullList(subItemList)) {
                resultList.addAll(subItemList);
            }
        }
        return resultList;
    }

    /**
     * Get Parent Root Doc UUID by current mat item instance, default is the parent node UUID
     *
     * @param docMatItem
     * @return
     */
    public String getParentDocUUIDByMatItem(DocMatItemNode docMatItem) {
        return docMatItem.getParentNodeUUID();
    }

    /**
     * Provide default logic to copy parent doc information to sub material item service module
     *
     * @param parentServiceModule
     * @param itemServiceModule
     * @return
     */
    public void initDefCopyParentDocToItemServiceModule(R parentServiceModule,
                                                        ServiceModule itemServiceModule)
            throws DocActionException {
        /*
         * [Step1] copy party
         */
        List<Field> partyFieldList = ServiceModuleProxy.getFieldListByDocNodeCategory(parentServiceModule.getClass(),
                IServiceModuleFieldConfig.DOCNODE_CATE_PARTY);
        List<Field> itemPartyFieldList =
                ServiceModuleProxy.getFieldListByDocNodeCategory(itemServiceModule.getClass(),
                        IServiceModuleFieldConfig.DOCNODE_CATE_PARTY);
        if (ServiceCollectionsHelper.checkNullList(partyFieldList) || ServiceCollectionsHelper.checkNullList(itemPartyFieldList)) {
            return;
        }
        for (Field partyField : partyFieldList) {
            partyField.setAccessible(true);
            try {
                DocInvolveParty docInvolveParty = (DocInvolveParty) partyField.get(parentServiceModule);
                if (docInvolveParty == null) {
                    continue;
                }
                DocMatItemNode docMatItemNode = getDocMatItemNodeFromItemServiceModule(itemServiceModule);
                String itemPartyNodeName =
                        ServiceModuleProxy.getNodeNameByFieldDocNodeCategory(itemServiceModule.getClass(),
                                IServiceModuleFieldConfig.DOCNODE_CATE_PARTY);
                DocInvolveParty itemInvolveParty = docInvolvePartyProxy.initGenInvolveParty(docMatItemNode,
                        docInvolveParty.getPartyRole(), itemPartyNodeName, this.getDocumentManager());
                setDocItemInvolveParty(itemServiceModule, itemInvolveParty);
            } catch (IllegalAccessException | ServiceEntityConfigureException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
            }
        }
    }

    public static Field getDocMatItemField(ServiceModule serviceModule) {
        if (serviceModule == null) {
            return null;
        }
        List<Field> itemFieldList =
                ServiceModuleProxy.getFieldListByDocNodeCategory(serviceModule.getClass(),
                        IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM);
        if (ServiceCollectionsHelper.checkNullList(itemFieldList)) {
            return null;
        }
        return itemFieldList.get(0);
    }

    public static DocMatItemNode getDocMatItemNodeFromItemServiceModule(ServiceModule itemServiceModule)
            throws IllegalAccessException {
        Field itemField = getDocMatItemField(itemServiceModule);
        if (itemField == null) {
            return null;
        }
        itemField.setAccessible(true);
        return (DocMatItemNode) itemField.get(itemServiceModule);
    }

    public static List<?> getDocMatItemListFromDocServiceModule(ServiceModule serviceModule)
            throws IllegalAccessException {
        Field itemField = getDocMatItemField(serviceModule);
        if (itemField == null) {
            return null;
        }
        itemField.setAccessible(true);
        return (List<?>) itemField.get(serviceModule);
    }

    public abstract DocInvolveParty getDocInvolveParty(int partyRole, R serviceModule);

    public abstract DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule itemServiceModule);

    public abstract void setDocInvolveParty(R serviceModule, DocInvolveParty docInvolveParty);

    public abstract void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty);

    public List<ServiceEntityNode> getDocMatItemList(R serviceModule) {
        try {
            List<?> docMatItemServiceModuleList = getDocMatItemListFromDocServiceModule(serviceModule);
            if (ServiceCollectionsHelper.checkNullList(docMatItemServiceModuleList)) {
                return null;
            }
            List<ServiceEntityNode> docMatItemList = new ArrayList<>();
            for (Object objItem : docMatItemServiceModuleList) {
                if (objItem instanceof ServiceModule) {
                    docMatItemList.add(getDocMatItemNodeFromItemServiceModule((ServiceModule) objItem));
                }
                if (objItem instanceof ServiceEntityNode) {
                    docMatItemList.add((ServiceEntityNode) objItem);
                }
            }
            return docMatItemList;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public List<?> getDocMatItemServiceModuleList(R serviceModule) {
        try {
            return getDocMatItemListFromDocServiceModule(serviceModule);
        } catch (IllegalAccessException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "getDocMatItemServiceModuleList"));
            return null;
        }
    }

    public abstract Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException;

    public abstract Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException;

    public String getDocNodeInstId() {
        return getDefNodeInstIdFromModelClass(getDocMetadata().getDocModelClass());
    }

    public static String getDefNodeInstIdFromModelClass(Class<? extends ServiceEntityNode> modelClass) {
        try {
            String nodeName = (String) ServiceEntityFieldsHelper.getStaticFieldValue(
                    modelClass, IServiceEntityNodeFieldConstant.STA_NODENAME);
            if (ServiceEntityNode.NODENAME_ROOT.equals(nodeName)) {
                return (String) ServiceEntityFieldsHelper.getStaticFieldValue(
                        modelClass, IServiceEntityNodeFieldConstant.STA_SENAME);
            } else {
                return nodeName;
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract DocMetadata getDocMetadata();

    public String getDocNodeName() {
        try {
            return (String) ServiceEntityFieldsHelper.getStaticFieldValue(
                    getDocMetadata().getDocModelClass(), IServiceEntityNodeFieldConstant.STA_NODENAME);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public String getInvolvePartyNodeName() {
        return ServiceModuleProxy.getNodeNameByFieldDocNodeCategory(getDocMetadata().getDocServiceModuleClass(),
                IServiceModuleFieldConfig.DOCNODE_CATE_PARTY);
    }

    public String getMatItemNodeInstId() {
        return getDefNodeInstIdFromModelClass(getDocMetadata().getItemModelClass());
    }

    public String getDocActionNodeInstId() {
        return getDefNodeInstIdFromModelClass(getDocMetadata().getDocActionNodeClass());
    }

    public ServiceUIModelExtension getDocServiceUIModelExtension() throws DocActionException {
        DocUIModelExtensionBuilder docUIModelExtensionBuilder = getDocUIModelExtensionBuilder();
        return docUIModelExtensionBuilder.buildDoc();
    }

    public ServiceUIModelExtension getItemServiceUIModelExtension() throws DocActionException {
        DocUIModelExtensionBuilder docUIModelExtensionBuilder = getDocUIModelExtensionBuilder();
        return docUIModelExtensionBuilder.buildDocItem();
    }

    public Class<?> getItemServiceModelClass() {
        return getDocMetadata().getItemServiceModuleClass();
    }

    public abstract void traverseMatItemNode(R serviceModule,
                                             DocActionExecutionProxy.DocItemActionExecution<Item> docItemActionCallback,
                                             SerialLogonInfo serialLogonInfo) throws DocActionException;

    public abstract List<UIModelClassMap> getUIModelClassMap();

    public abstract List<PropertyMap> getDefFieldProperPathMap();

    public List<ServiceExtensionSimModel> getServiceDocumentMeta(String modelId,
                                                                 String coreModelId, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException {
        // Step 1 get model meta list
        List<ServiceExtensionSimModel> serviceExtensionSimModelList = getExtensionFieldModelList(serialLogonInfo);
        // Step 2 get other meta
        ServiceExcelHandlerProxy serviceExcelHandlerProxy =
                serviceExcelHandlerProxyFactory.getServiceExcelHandler(coreModelId);
        if(serviceExcelHandlerProxy != null && !ServiceCollectionsHelper.checkNullList(serviceExtensionSimModelList)){
            ServiceExtensionSimModel coreExtensionSimModel =
                    ServiceCollectionsHelper.filterOnline(serviceExtensionSimModelList, simModel -> {
                        return simModel.getModelId().equals(coreModelId);
                    });
            if(coreExtensionSimModel != null){
                coreExtensionSimModel.setServiceUIMeta(serviceExcelHandlerProxy.genServiceUIMeta());
            }
        }
        return serviceExtensionSimModelList;
    }

    public List<ServiceExtensionSimModel> getExtensionFieldModelList(SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException {
        // Step 1 get model meta list
        return serviceExtensionManager.parseToDefExtensionSimModelList(getUIModelClassMap(),
                getDefFieldProperPathMap()
                , serialLogonInfo);
    }

    public List<ServiceEntityNode> getFieldSettingList(String modelId, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException {
        return serviceExtensionManager.getComFieldSettingList(getUIModelClassMap(), getDefFieldProperPathMap(),
                modelId, serialLogonInfo);
    }

    public Item getDocMatItem(String uuid, String client)
            throws ServiceEntityConfigureException {
        return (Item) getDocumentManager().getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID,
                getMatItemNodeInstId(), client, null, true);
    }

    /**
     * Get each doc mat item's request amount, this is default way for all document
     * @param uuid
     * @param client
     * @return
     * @throws ServiceEntityConfigureException
     */
    public StorageCoreUnit getDocMatItemRequestAmount(String uuid, String client)
            throws ServiceEntityConfigureException {
        Item docMatItem = getDocMatItem(uuid, client);
        return getDocMatItemRequestAmount((DocMatItemNode) docMatItem);
    }

    public StorageCoreUnit getDocMatItemRequestAmount(DocMatItemNode docMatItemNode)
            throws ServiceEntityConfigureException {
        return new StorageCoreUnit(docMatItemNode.getRefMaterialSKUUUID(), docMatItemNode.getRefUnitUUID(),
                docMatItemNode.getAmount());
    }

    /**
     * Get each doc mat item's amount still free to reserve, this is default way for all document
     * @param uuid
     * @param client
     * @return
     * @throws ServiceEntityConfigureException
     */
    public StorageCoreUnit getFreeToReserveAmount(String uuid, String client)
            throws ServiceEntityConfigureException, DocActionException, MaterialException {
        Item docMatItem = getDocMatItem(uuid, client);
        DocMatItemNode docMatItemNode = (DocMatItemNode) docMatItem;
        StorageCoreUnit fullAmount = getDocMatItemRequestAmount(docMatItemNode);
        return getFreeToReserveAmount(docMatItemNode);
    }

    /**
     * Get each doc mat item's amount still free to reserve, this is default way for all document
     * @param reserveTargetMatItemNode
     * @return
     * @throws ServiceEntityConfigureException
     */
    public StorageCoreUnit getFreeToReserveAmount(DocMatItemNode reserveTargetMatItemNode)
            throws ServiceEntityConfigureException, DocActionException, MaterialException {
        StorageCoreUnit fullAmount = getDocMatItemRequestAmount(reserveTargetMatItemNode);
        StorageCoreUnit reservedAmount = reserveDocItemProxy.calculateReservedAmount(reserveTargetMatItemNode);
        StorageCoreUnit freeToReserveAmount = materialStockKeepUnitManager.mergeStorageUnitCore(fullAmount,
                reservedAmount, StorageCoreUnit.OPERATOR_MINUS, reserveTargetMatItemNode.getClient());
        if(freeToReserveAmount.getAmount() < 0){
            // should record exception
        }
        return freeToReserveAmount;
    }
    /**
     * Get each doc mat item's amount still free to reserve, this is default way for all document
     * @param reserveTargetMatItemNode
     * @return
     * @throws ServiceEntityConfigureException
     */
    public boolean checkReserveRequest(DocMatItemNode reserveTargetMatItemNode, StorageCoreUnit storageCoreUnit)
            throws ServiceEntityConfigureException, DocActionException, MaterialException {
        StorageCoreUnit fullAmount = getDocMatItemRequestAmount(reserveTargetMatItemNode);
        StorageCoreUnit freeToReserveAmount = getFreeToReserveAmount(reserveTargetMatItemNode);
        if(freeToReserveAmount.getAmount() < 0){
            // should record exception
            return false;
        }
        StorageCoreUnit leftAmount = materialStockKeepUnitManager.mergeStorageUnitCore(freeToReserveAmount,
                storageCoreUnit, StorageCoreUnit.OPERATOR_MINUS, reserveTargetMatItemNode.getClient());
        // should record exception
        return !(leftAmount.getAmount() < 0);
    }

    public Map<Integer, String> getInvolvePartyMapWrapper(String lanCode, Class<?> uiModelClass, String propertyName)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefaultLanguageMap(lanCode, this.involvePartyLan, languageCode -> {
            try {
                return serviceDropdownListHelper
                        .getDropDownMap(
                                uiModelClass.getResource("").getPath() + propertyName, languageCode);
            } catch (IOException e) {
                return null;
            }
        });
    }

    /**
     * Quick Create Service Model for Root Doc Service Model, and material item list
     *
     * @return service model
     */
    public R quickCreateServiceModel(
            T serviceEntityNode,
            List<ServiceEntityNode> matItemList) {
        try {
            ServiceUIModelExtensionUnion serviceUIModelExtensionUnion = getDocServiceUIModelExtension().genUIModelExtensionUnion().get(0);
            return (R) serviceModuleProxy.quickCreateServiceModel(getDocMetadata().getDocServiceModuleClass(), serviceEntityNode, matItemList, serviceUIModelExtensionUnion);
        } catch (ServiceEntityConfigureException | DocActionException | ServiceModuleProxyException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<PropertyMap> getCorePropertyMap(String modelId, String customPath){
        List<PropertyMap> propertyMapList = new ArrayList<>();
        propertyMapList.add(new PropertyMap(modelId, getCommonPropertyPath()));
        propertyMapList.add(new PropertyMap(modelId, customPath));
        return propertyMapList;
    }

    public static PropertyMap getActionNodePropertyMap(String modelId){
        String actionNodePropertyPath = getActionNodePropertyPath();
        return new PropertyMap(modelId, actionNodePropertyPath);
    }

    public static List<PropertyMap> getInvolvePartyPropertyMap(String modelId, String customPath){
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String partyPropertyPath = getInvolvePartyPath();
        propertyMapList.add(new PropertyMap(modelId, partyPropertyPath));
        propertyMapList.add(new PropertyMap(modelId, customPath));
        return propertyMapList;
    }

    public static List<PropertyMap> getDocMatItemPropertyMap(String modelId, String customPath){
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String partyPropertyPath = getDocMatItemNodePath();
        propertyMapList.add(new PropertyMap(modelId, getCommonPropertyPath()));
        propertyMapList.add(new PropertyMap(modelId, partyPropertyPath));
        propertyMapList.add(new PropertyMap(modelId, customPath));
        return propertyMapList;
    }

    /**
     * API to get configured init doc configuration id list
     * @return
     */
    public List<String> getConfiguredInitIdList() {
        return null;
    }

    /**
     * API to get default init configure map, should be implemented in subclasses
     * @return
     */
    public Map<String, List<ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta>> getDefAllInitConfigureMap() {
        return null;
    }

    /**
     * Get default document node initial configure
     * @param initConfigureId
     * @return
     */
    public List<ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta> getDefInitConfigureList(String initConfigureId) {
        Map<String, List<ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta>> defAllInitConfigureMap =
                this.getDefAllInitConfigureMap();
        if (defAllInitConfigureMap == null) {
            return null;
        }
        return defAllInitConfigureMap.get(initConfigureId);
    }

    public static String getCommonPropertyPath(){
        return IPackageDefI18n.class.getResource("").getPath() + "ComElements";
    }

    public static String getActionNodePropertyPath(){
        return IPackageDefI18n.class.getResource("").getPath() + "DocActionNode";
    }

    public static String getInvolvePartyPath(){
        return IPackageDefI18n.class.getResource("").getPath() + "DocInvolveParty";
    }

    public static String getDocMatItemNodePath(){
        return IPackageDefI18n.class.getResource("").getPath() + "DocMatItemNode";
    }

    public static class PropertyMap{

        private String modelId;

        private String propertyPath;

        public PropertyMap(String modelId, String propertyPath) {
            this.modelId = modelId;
            this.propertyPath = propertyPath;
        }

        public String getModelId() {
            return modelId;
        }

        public void setModelId(String modelId) {
            this.modelId = modelId;
        }

        public String getPropertyPath() {
            return propertyPath;
        }

        public void setPropertyPath(String propertyPath) {
            this.propertyPath = propertyPath;
        }
    }

    public static class UIModelClassMap{

        private String modelId;

        private Class<?> uiModelClass;

        public UIModelClassMap(String modelId, Class<?> uiModelClass) {
            this.modelId = modelId;
            this.uiModelClass = uiModelClass;
        }

        public String getModelId() {
            return modelId;
        }

        public void setModelId(String modelId) {
            this.modelId = modelId;
        }

        public Class<?> getUiModelClass() {
            return uiModelClass;
        }

        public void setUiModelClass(Class<?> uiModelClass) {
            this.uiModelClass = uiModelClass;
        }

    }

    public static class DocMetadata {

        private Class<? extends ServiceEntityNode> docModelClass;

        private Class<? extends ServiceModule> docServiceModuleClass;

        private Class<? extends DocActionNode> docActionNodeClass;

        private Class<? extends ServiceEntityNode> itemModelClass;

        private Class<? extends ServiceModule> itemServiceModuleClass;

        private Class<? extends DocActionNode> itemActionNodeClass;

        public DocMetadata(Class<? extends ServiceEntityNode> docModelClass,
                           Class<? extends ServiceModule> docServiceModuleClass,
                           Class<? extends DocActionNode> docActionNodeClass,
                           Class<? extends ServiceEntityNode> itemModelClass,
                           Class<? extends ServiceModule> itemServiceModuleClass,
                           Class<? extends DocActionNode> itemActionNodeClass) {
            this.docModelClass = docModelClass;
            this.docServiceModuleClass = docServiceModuleClass;
            this.docActionNodeClass = docActionNodeClass;
            this.itemModelClass = itemModelClass;
            this.itemServiceModuleClass = itemServiceModuleClass;
            this.itemActionNodeClass = itemActionNodeClass;
        }

        public Class<? extends ServiceEntityNode> getDocModelClass() {
            return docModelClass;
        }

        public void setDocModelClass(Class<? extends ServiceEntityNode> docModelClass) {
            this.docModelClass = docModelClass;
        }

        public Class<? extends ServiceModule> getDocServiceModuleClass() {
            return docServiceModuleClass;
        }

        public void setDocServiceModuleClass(Class<? extends ServiceModule> docServiceModuleClass) {
            this.docServiceModuleClass = docServiceModuleClass;
        }

        public Class<? extends ServiceEntityNode> getItemModelClass() {
            return itemModelClass;
        }

        public void setItemModelClass(Class<? extends ServiceEntityNode> itemModelClass) {
            this.itemModelClass = itemModelClass;
        }

        public Class<? extends ServiceModule> getItemServiceModuleClass() {
            return itemServiceModuleClass;
        }

        public void setItemServiceModuleClass(Class<? extends ServiceModule> itemServiceModuleClass) {
            this.itemServiceModuleClass = itemServiceModuleClass;
        }

        public Class<? extends DocActionNode> getDocActionNodeClass() {
            return docActionNodeClass;
        }

        public void setDocActionNodeClass(Class<? extends DocActionNode> docActionNodeClass) {
            this.docActionNodeClass = docActionNodeClass;
        }

        public Class<? extends DocActionNode> getItemActionNodeClass() {
            return itemActionNodeClass;
        }

        public void setItemActionNodeClass(Class<? extends DocActionNode> itemActionNodeClass) {
            this.itemActionNodeClass = itemActionNodeClass;
        }
    }

}
