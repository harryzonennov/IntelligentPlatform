package com.company.IntelligentPlatform.common.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import net.sf.json.JSONArray;

// TODO-LEGACY: import org.apache.commons.httpclient.HttpStatus; // replaced by local HttpStatus stub
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
// TODO-DAO: import platform.foundation.DAO.HibernateDefaultImpDAO;
// TODO-DAO: import ...ServiceEntityDAO;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.ServiceModelExtensionHelper;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.ServiceExtensionException;
import com.company.IntelligentPlatform.common.service.ServiceExtensionManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.ServiceJSONRequest;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.AttachmentConstantHelper;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.SelectMapModel;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityPersistenceHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Basic Service Entity Manager
 *
 * @author ZhangHang
 * @date Nov 7, 2012
 */
@Transactional
@Service
public class ServiceEntityManager {

    protected ServiceEntityDAO serviceEntityDAO; // TODO-DAO: was HibernateDefaultImpDAO

    protected ServiceEntityConfigureProxy seConfigureProxy;

    @Autowired
    protected ServiceModuleProxy serviceModuleProxy;

    @Autowired
    protected ServiceUIModuleProxy serviceUIModuleProxy;

    @Autowired
    protected ReferenceService referenceService;

    @Autowired
    protected ServiceExtensionManager serviceExtensionManager;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    protected Logger managerLogger = LoggerFactory.getLogger(ServiceEntityManager.class);

    public ServiceEntityManager() {

    }

    public ServiceEntityConfigureProxy getSeConfigureProxy() {
        return seConfigureProxy;
    }

    public void setSeConfigureProxy(ServiceEntityConfigureProxy seConfigureProxy) {
        this.seConfigureProxy = seConfigureProxy;
    }

    public ServiceEntityDAO getServiceEntityDAO() {
        return serviceEntityDAO;
    }

    public void setServiceEntityDAO(ServiceEntityDAO serviceEntityDAO) { // TODO-DAO: was HibernateDefaultImpDAO
        this.serviceEntityDAO = serviceEntityDAO;
    }

    /**
     * Create New Root Service Entity node instance
     *
     * @return Service Entity root node instance
     * @throws ServiceEntityConfigureException
     */
    public ServiceEntityNode newRootEntityNode(String client)
            throws ServiceEntityConfigureException {
        ServiceEntityNode rootNode = newRootEntityNode();
        // Check if it is cross client
        boolean crossClientFlag = ServiceEntityPersistenceHelper
                .checkTableCrossClient(rootNode.getServiceEntityName());
        if (!crossClientFlag) {
            rootNode.setClient(client);
        }
        return rootNode;
    }

    /**
     * Create New Root Service Entity node instance
     *
     * @return Service Entity root node instance
     * @throws ServiceEntityConfigureException
     */
    @SuppressWarnings("unchecked")
    public ServiceEntityNode newRootEntityNode()
            throws ServiceEntityConfigureException {
        for (int i = 0; i < seConfigureProxy.getSeConfigMapList().size(); i++) {
            ServiceEntityConfigureMap seConfigureMap = seConfigureProxy
                    .getSeConfigMapList().get(i);
            if (seConfigureMap.getNodeName().equals(
                    ServiceEntityNode.NODENAME_ROOT)) {
                Class<ServiceEntityNode> nodeCls = seConfigureMap.getNodeType();
                try {
                    ServiceEntityNode rootNode = nodeCls.newInstance();
                    rootNode.setNodeLevel(ServiceEntityNode.NODELEVEL_ROOT);
                    rootNode.setNodeName(ServiceEntityNode.NODENAME_ROOT);
                    // For root node set parent uuid & root uuid as itself
                    rootNode.setParentNodeUUID(rootNode.getUuid());
                    rootNode.setRootNodeUUID(rootNode.getUuid());
                    return rootNode;
                } catch (InstantiationException ex) {
                    throw new ServiceEntityConfigureException(
                            ServiceEntityConfigureException.PARA_INIT_SE);
                } catch (IllegalAccessException ex) {
                    throw new ServiceEntityConfigureException(
                            ServiceEntityConfigureException.PARA_INIT_SE);
                }
            }
        }
        throw new ServiceEntityConfigureException(
                ServiceEntityConfigureException.PARA_NON_NODE,
                ServiceEntityNode.NODENAME_ROOT);
    }

    /**
     * Create New Service Entity node instance
     *
     * @param parentNode :parent Service Entity node instance
     * @param nodeName   :current node name
     * @return New Service Entity node instance
     * @throws ServiceEntityConfigureException
     */
    @SuppressWarnings("unchecked")
    public ServiceEntityNode newEntityNode(ServiceEntityNode parentNode,
                                           String nodeName) throws ServiceEntityConfigureException {
        for (int i = 0; i < seConfigureProxy.getSeConfigMapList().size(); i++) {
            ServiceEntityConfigureMap seConfigureMap = seConfigureProxy
                    .getSeConfigMapList().get(i);
            if (seConfigureMap.getNodeName().equals(nodeName)) {
                Class<ServiceEntityNode> nodeCls = seConfigureMap.getNodeType();
                try {
                    ServiceEntityNode seNode = nodeCls.newInstance();
                    seNode.setParentNodeUUID(parentNode.getUuid());
                    // Check if this node is cross client
                    boolean crossClientFlag = ServiceEntityPersistenceHelper
                            .checkTableCrossClient(seNode.getNodeName());
                    if (!crossClientFlag) {
                        seNode.setClient(parentNode.getClient());
                    }
                    if (parentNode.getNodeLevel() == ServiceEntityNode.NODELEVEL_ROOT) {
                        seNode.setRootNodeUUID(parentNode.getUuid());
                    } else {
                        parentNode
                                .setNodeLevel(ServiceEntityNode.NODELEVEL_NODE);
                        seNode.setRootNodeUUID(parentNode.getRootNodeUUID());
                    }
                    seNode.setNodeLevel(ServiceEntityNode.NODELEVEL_LEAVE);
                    return seNode;
                } catch (InstantiationException | IllegalAccessException ex) {
                    throw new ServiceEntityConfigureException(
                            ServiceEntityConfigureException.PARA_INIT_SE);
                }
            }
        }
        throw new ServiceEntityConfigureException(
                ServiceEntityConfigureException.PARA_NON_NODE, nodeName);
    }

    public void changeParent(ServiceEntityNode seNode,
                             ServiceEntityNode newParentNode) {
        seNode.setParentNodeUUID(newParentNode.getUuid());
        seNode.setRootNodeUUID(newParentNode.getRootNodeUUID());
        seNode.setClient(newParentNode.getClient());
    }

    /**
     * normal set admin data by user UUID
     *
     * @param seNode
     * @param processMode
     * @param userUUID
     */
    protected void setAdminData(ServiceEntityNode seNode, int processMode,
                                String userUUID, String organizationUUID) {
        if (processMode == ServiceEntityBindModel.PROCESSMODE_CREATE) {
            seNode.setCreatedTime(LocalDateTime.now());
            seNode.setCreatedBy(userUUID);
            seNode.setLastUpdateTime(LocalDateTime.now());
            seNode.setLastUpdateBy(userUUID);
            seNode.setResEmployeeUUID(userUUID);
            seNode.setResOrgUUID(organizationUUID);
        }
        if (processMode == ServiceEntityBindModel.PROCESSMODE_UPDATE) {
            seNode.setLastUpdateTime(LocalDateTime.now());
            seNode.setLastUpdateBy(userUUID);
            if (seNode.getResEmployeeUUID() == null
                    || seNode.getResEmployeeUUID().equals(
                    ServiceEntityStringHelper.EMPTYSTRING)) {
                seNode.setResEmployeeUUID(userUUID);
            }
            if (seNode.getResOrgUUID() == null
                    || seNode.getResOrgUUID().equals(
                    ServiceEntityStringHelper.EMPTYSTRING)) {
                seNode.setResOrgUUID(organizationUUID);
            }
        }
    }

    public String getParentNodeName(String nodeName) throws ServiceEntityConfigureException {
        if(ServiceEntityStringHelper.checkNullString(nodeName)){
            return null;
        }
        if(nodeName.equals(ServiceEntityNode.NODENAME_ROOT)){
            return null;
        }
        ServiceEntityConfigureMap seConfigMap = this.getSeConfigureProxy().getConfigureByNodeName(nodeName);
        if(seConfigMap == null){
            throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_NON_NODE);
        }
        return seConfigMap.getParentNodeName();
    }

    public ServiceEntityNode getParentEntityNode(ServiceEntityNode serviceEntityNode)
            throws ServiceEntityConfigureException {
        String parentNodeName = this.getParentNodeName(serviceEntityNode.getNodeName());
        if(ServiceEntityStringHelper.checkNullString(parentNodeName)){
            return null;
        }
        return this.getEntityNodeByKey(serviceEntityNode.getParentNodeUUID(), IServiceEntityNodeFieldConstant.UUID,
                parentNodeName, serviceEntityNode.getClient(), null);
    }

    /**
     * Check If such id already existed in persistence
     *
     * @param nodeName
     * @return true:duplicate ID exist, false: no duplicate ID
     * @throws ServiceEntityConfigureException
     */
    public boolean checkIDDuplicate(String uuid, String id, String nodeName,
                                    String client) throws ServiceEntityConfigureException {
        ServiceEntityNode seBack = this.getEntityNodeByKey(id,
                IServiceEntityNodeFieldConstant.ID, nodeName, client, null,
                true);
        if (seBack == null) {
            return false;
        } else {
            return !seBack.getUuid().equals(uuid);
        }
    }

    /**
     * update & synchronize the SE list with persistence, check whether to
     * insert, update, or delete by new SE bind list and backup SE bind list all
     * the update action will be restricted to one SE instance, by rootNodeUUID
     * meanwhile, the action will be logged by logonUser
     *
     * @param seBindList     comparative param1
     * @param seBindListBack comparative param2
     * @param logonUserUUID  : user UUID to update this SE node instance
     * @throws ServiceEntityConfigureException
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSEBindList(List<ServiceEntityBindModel> seBindList,
                                 List<ServiceEntityBindModel> seBindListBack, String logonUserUUID,
                                 String organizationUUID) throws ServiceEntityConfigureException {
        List<ServiceEntityNode> createList = new ArrayList<>();
        List<ServiceEntityNode> updateList = new ArrayList<>();
        List<ServiceEntityNode> deleteList = new ArrayList<>();
        // should implement log function here
        List<ServiceEntityBindModel> seBindListRefined = refineUpdateSEBindList(
                seBindList, seBindListBack);
        for (ServiceEntityBindModel seBind : seBindListRefined) {
            if (seBind.getProcessMode() == ServiceEntityBindModel.PROCESSMODE_CREATE) {
                setAdminData(seBind.getSeNode(),
                        ServiceEntityBindModel.PROCESSMODE_CREATE,
                        logonUserUUID, organizationUUID);
                createList.add(seBind.getSeNode());
            }
            if (seBind.getProcessMode() == ServiceEntityBindModel.PROCESSMODE_UPDATE) {
                setAdminData(seBind.getSeNode(),
                        ServiceEntityBindModel.PROCESSMODE_UPDATE,
                        logonUserUUID, organizationUUID);
                updateList.add(seBind.getSeNode());
            }
            if (seBind.getProcessMode() == ServiceEntityBindModel.PROCESSMODE_DELETE) {
                setAdminData(seBind.getSeNode(),
                        ServiceEntityBindModel.PROCESSMODE_DELETE,
                        logonUserUUID, organizationUUID);
                deleteList.add(seBind.getSeNode());
            }
        }
        if (createList.size() > 0) {
            this.serviceEntityDAO.insertEntity(createList);
        }
        if (updateList.size() > 0) {
            this.serviceEntityDAO.updateEntity(updateList);
        }
        if (deleteList.size() > 0) {
            this.serviceEntityDAO.deleteEntityNodeList(deleteList);
        }
    }

    /**
     * insert single SE node instance to persistence
     *
     * @param seNode
     * @param logonUserUUID
     * @param organizationUUID
     */
    public void insertSENode(ServiceEntityNode seNode, String logonUserUUID,
                             String organizationUUID) {
        setAdminData(seNode, ServiceEntityBindModel.PROCESSMODE_CREATE,
                logonUserUUID, organizationUUID);
        this.serviceEntityDAO.insertEntity(seNode);
    }

    /**
     * insert SE node list to persistence
     *
     * @param seNodeList
     * @param logonUserUUID
     * @param organizationUUID
     */
    public void insertSENodeList(List<ServiceEntityNode> seNodeList,
                                 String logonUserUUID, String organizationUUID) {
        for (ServiceEntityNode seNode : seNodeList) {
            setAdminData(seNode, ServiceEntityBindModel.PROCESSMODE_CREATE,
                    logonUserUUID, organizationUUID);
        }
        this.serviceEntityDAO.insertEntity(seNodeList);
    }

    /**
     * update single SE node instance to persistence
     *
     * @param seNode
     * @param logonUserUUID
     * @param organizationUUID
     */
    public void updateSENode(ServiceEntityNode seNode, String logonUserUUID,
                             String organizationUUID) {
        if (ServiceEntityStringHelper.checkNullString(seNode.getCreatedBy())) {
            setAdminData(seNode, ServiceEntityBindModel.PROCESSMODE_CREATE,
                    logonUserUUID, organizationUUID);
        }
        setAdminData(seNode, ServiceEntityBindModel.PROCESSMODE_UPDATE,
                logonUserUUID, organizationUUID);
        this.serviceEntityDAO.updateEntity(seNode);
    }

    /**
     * update single SE node instance to persistence
     *
     * @param seNode
     * @param logonUserUUID
     * @param organizationUUID
     */
    public void updateSENode(ServiceEntityNode seNode,
                             ServiceEntityNode backSENode, String logonUserUUID,
                             String organizationUUID) {
        if (ServiceEntityStringHelper.checkNullString(seNode.getCreatedBy())) {
            setAdminData(seNode, ServiceEntityBindModel.PROCESSMODE_CREATE,
                    logonUserUUID, organizationUUID);
        }
        setAdminData(seNode, ServiceEntityBindModel.PROCESSMODE_UPDATE,
                logonUserUUID, organizationUUID);
        this.serviceEntityDAO.updateEntity(seNode);
    }

    /**
     * update SE node list to persistence
     *
     * @param seNodeList
     * @param logonUserUUID
     * @param organizationUUID
     */
    public void updateSENodeList(List<ServiceEntityNode> seNodeList,
                                 String logonUserUUID, String organizationUUID) {
        for (ServiceEntityNode seNode : seNodeList) {
            if (ServiceEntityStringHelper.checkNullString(logonUserUUID)) {
                setAdminData(seNode, ServiceEntityBindModel.PROCESSMODE_UPDATE,
                        seNode.getCreatedBy(), seNode.getResOrgUUID());
            } else {
                if (ServiceEntityStringHelper.checkNullString(seNode.getCreatedBy())) {
                    setAdminData(seNode, ServiceEntityBindModel.PROCESSMODE_CREATE,
                            logonUserUUID, organizationUUID);
                }
                setAdminData(seNode, ServiceEntityBindModel.PROCESSMODE_UPDATE,
                        logonUserUUID, organizationUUID);
            }
        }
        this.serviceEntityDAO.updateEntity(seNodeList);
    }

    /**
     * delete single SE node instance from persistence
     *
     * @param seNode
     * @param logonUserUUID
     * @param organizationUUID
     */
    public void deleteSENode(ServiceEntityNode seNode, String logonUserUUID,
                             String organizationUUID) {
        // setAdminData(seNode, ServiceEntityBindModel.PROCESSMODE_UPDATE,
        // logonUserUUID, organizationUUID);
        List<ServiceEntityNode> deleteList = new ArrayList<>();
        deleteList.add(seNode);
        this.serviceEntityDAO.deleteEntityNodeList(deleteList);
    }

    /**
     * delete SE node instance list from persistence
     *
     * @param deleteList:      List of service entity node instance to be deleted
     * @param logonUserUUID
     * @param organizationUUID
     */
    public void deleteSENode(List<ServiceEntityNode> deleteList,
                             String logonUserUUID, String organizationUUID) {
        this.serviceEntityDAO.deleteEntityNodeList(deleteList);
    }

    /**
     * update & synchronize the SE list with persistence, check whether to
     * insert, update, or delete by new SE bind list and backup SE bind list
     *
     * @param seBindList
     * @param seBindListBack
     * @throws ServiceEntityConfigureException
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSEBindList(List<ServiceEntityBindModel> seBindList,
                                 List<ServiceEntityBindModel> seBindListBack)
            throws ServiceEntityConfigureException {
        List<ServiceEntityNode> createList = new ArrayList<>();
        List<ServiceEntityNode> updateList = new ArrayList<>();
        List<ServiceEntityNode> deleteList = new ArrayList<>();
        // should implement log function here
        List<ServiceEntityBindModel> seBindListRefined = refineUpdateSEBindList(
                seBindList, seBindListBack);
        for (ServiceEntityBindModel seBind : seBindListRefined) {
            if (seBind.getProcessMode() == ServiceEntityBindModel.PROCESSMODE_CREATE) {
                createList.add(seBind.getSeNode());
            }
            if (seBind.getProcessMode() == ServiceEntityBindModel.PROCESSMODE_UPDATE) {
                updateList.add(seBind.getSeNode());
            }
            if (seBind.getProcessMode() == ServiceEntityBindModel.PROCESSMODE_DELETE) {
                deleteList.add(seBind.getSeNode());
            }
        }
        if (createList.size() > 0) {
            this.serviceEntityDAO.insertEntity(createList);
        }
        if (updateList.size() > 0) {
            this.serviceEntityDAO.updateEntity(updateList);
        }
        if (deleteList.size() > 0) {
            // how to delete by SE instance
        }
    }

    /**
     * [Internal method] refine the
     *
     * @param seBindList
     * @param seBindListBack
     * @return
     */
    protected List<ServiceEntityBindModel> refineUpdateSEBindList(
            List<ServiceEntityBindModel> seBindList,
            List<ServiceEntityBindModel> seBindListBack) {
        List<ServiceEntityBindModel> seBindRefinedList = new ArrayList<ServiceEntityBindModel>();
        List<ServiceEntityBindModel> seBindDeleteList = new ArrayList<ServiceEntityBindModel>();
        if (seBindListBack != null && seBindList.size() > 0) {
            // Edit model on UI
            for (ServiceEntityBindModel seBindModel : seBindList) {
                boolean existFlag = false;
                for (ServiceEntityBindModel seBindModelBack : seBindListBack) {
                    if (seBindModelBack != null
                            && seBindModelBack.getSeNode() != null) {
                        if (seBindModel.getSeNode().getUuid()
                                .equals(seBindModelBack.getSeNode().getUuid())) {
                            // In case same model found in backup data
                            existFlag = true;
                            if (seBindModel.getSeNode().equals(
                                    seBindModelBack.getSeNode())) {
                                seBindModel
                                        .setProcessMode(ServiceEntityBindModel.PROCESSMODE_UPDATE);
                            } else {
                                seBindModel
                                        .setProcessMode(ServiceEntityBindModel.PROCESSMODE_UPDATE);
                            }
                        }
                    }
                }
                if (!existFlag) {
                    // In case same model not found in backup data, a new data
                    // should be insert
                    seBindModel
                            .setProcessMode(ServiceEntityBindModel.PROCESSMODE_CREATE);
                }
            }
            // Find the item to be deleted
            for (ServiceEntityBindModel seBindModelBack : seBindListBack) {
                boolean existFlag = false;
                for (ServiceEntityBindModel seBindModel : seBindList) {
                    if (seBindModelBack != null
                            && seBindModelBack.getSeNode() != null) {
                        if (seBindModel.getSeNode().getUuid()
                                .equals(seBindModelBack.getSeNode().getUuid())) {
                            // In case same model found in backup data
                            existFlag = true;
                        }
                    }
                }
                if (!existFlag) {
                    if (seBindModelBack != null
                            && seBindModelBack.getSeNode() != null) {
                        seBindModelBack
                                .setProcessMode(ServiceEntityBindModel.PROCESSMODE_DELETE);
                        seBindDeleteList.add(seBindModelBack);
                    }
                }
            }
            // Add deleted item
            if (seBindDeleteList != null && seBindDeleteList.size() > 0) {
                seBindRefinedList = seBindList;
                seBindRefinedList.addAll(seBindDeleteList);
                return seBindRefinedList;
            }
            return seBindList;
        } else {// In case insert all of the data, creation model on UI
            for (ServiceEntityBindModel seBindModel : seBindList) {
                seBindModel
                        .setProcessMode(ServiceEntityBindModel.PROCESSMODE_CREATE);
            }
        }
        return seBindList;
    }

    /**
     * Check whether data has been changed by new binding data list and backup
     * binding data list.
     *
     * @param seBindList     : new binding data list
     * @param seBindListBack : backup binding data list
     * @return
     */
    public boolean checkChangedFlag(List<ServiceEntityBindModel> seBindList,
                                    List<ServiceEntityBindModel> seBindListBack) {
        List<ServiceEntityBindModel> seBindListRefined = refineUpdateSEBindList(
                seBindList, seBindListBack);
        for (ServiceEntityBindModel seBind : seBindListRefined) {
            if (seBind.getProcessMode() == ServiceEntityBindModel.PROCESSMODE_CREATE) {
                return true;
            }
            if (seBind.getProcessMode() == ServiceEntityBindModel.PROCESSMODE_UPDATE) {
                return true;
            }
            if (seBind.getProcessMode() == ServiceEntityBindModel.PROCESSMODE_DELETE) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generate Json array for selection from simple string array.
     *
     * @param rawList
     * @return
     */
    public String getDefaultStrSelectMap(List<String> rawList) {
        if (ServiceCollectionsHelper.checkNullList(rawList)) {
            return null;
        }
        List<SelectMapModel> resultList = new ArrayList<>();
        for (String tmpStr : rawList) {
            SelectMapModel selectMapModel = new SelectMapModel();
            selectMapModel.setId(tmpStr);
            selectMapModel.setText(tmpStr);
            resultList.add(selectMapModel);
        }
        JSONArray jsonArray = JSONArray.fromObject(resultList);
        return jsonArray.toString();
    }

    public String getDefaultStrSelectMap(Map<String, String> resourceMap,
                                         boolean includeKey) {
        if(resourceMap == null){
            return ServiceJSONParser.genSimpleOKResponse();
        }
        Iterator<String> iterator = resourceMap.keySet().iterator();
        List<SelectMapModel> resultList = new ArrayList<>();
        while (iterator.hasNext()) {
            String key = iterator.next();
            SelectMapModel selectMapModel = new SelectMapModel();
            selectMapModel.setId(key);
            if (includeKey) {
                selectMapModel.setText(key + "-" + resourceMap.get(key));
            } else {
                selectMapModel.setText(resourceMap.get(key));
            }
            resultList.add(selectMapModel);
        }
        JSONArray jsonArray = JSONArray.fromObject(resultList);
        return jsonArray.toString();
    }

    public String getDefaultSelectMap(Map<Integer, String> resourceMap) {
        if (resourceMap == null) {
            return null;
        }
        Iterator<Integer> iterator = resourceMap.keySet().iterator();
        List<SelectMapModel> resultList = new ArrayList<SelectMapModel>();
        while (iterator.hasNext()) {
            Integer key = iterator.next();
            SelectMapModel selectMapModel = new SelectMapModel();
            selectMapModel.setId(key);
            selectMapModel.setText(resourceMap.get(key));
            resultList.add(selectMapModel);
        }
        JSONArray jsonArray = JSONArray.fromObject(resultList);
        return jsonArray.toString();
    }

    public String getDefaultSelectMap(Map<Integer, String> resourceMap,
                                      boolean includeKey) {
        return getSelectMap(resourceMap, includeKey);
    }

    public static <T> String getSelectMap(Map<T, String> resourceMap,
                                      boolean includeKey) {
        if (resourceMap == null) {
            return JSONArray.fromObject(null).toString();
        }
        Iterator<T> iterator = resourceMap.keySet().iterator();
        List<SelectMapModel> resultList = new ArrayList<>();
        while (iterator.hasNext()) {
            T key = iterator.next();
            SelectMapModel selectMapModel = new SelectMapModel();
            selectMapModel.setId(key);
            if (includeKey) {
                selectMapModel.setText(key + "-" + resourceMap.get(key));
            } else {
                selectMapModel.setText(resourceMap.get(key));
            }
            resultList.add(selectMapModel);
        }
        JSONArray jsonArray = JSONArray.fromObject(resultList);
        return jsonArray.toString();
    }

    public String getSelectStringTypeMap(Map<String, String> resourceMap,
                                         boolean includeKey) {
        if (resourceMap == null) {
            return null;
        }
        Iterator<String> iterator = resourceMap.keySet().iterator();
        List<SelectMapModel> resultList = new ArrayList<>();
        while (iterator.hasNext()) {
            String key = iterator.next();
            SelectMapModel selectMapModel = new SelectMapModel();
            selectMapModel.setId(key);
            if (includeKey) {
                selectMapModel.setText(key + "-" + resourceMap.get(key));
            } else {
                selectMapModel.setText(resourceMap.get(key));
            }
            resultList.add(selectMapModel);
        }
        JSONArray jsonArray = JSONArray.fromObject(resultList);
        return jsonArray.toString();
    }

    /**
     * Get Service Entity Node List by Key information and from each client With
     * Default fuzzy search function
     *
     * @param keyValue
     * @param keyName
     * @param nodeName
     * @param rawSEList On line raw data list <code>Optional = true</code> if value is
     *                  null, then retrieve raw data from persistence, otherwise,
     *                  using this online raw data list
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<ServiceEntityNode> getEntityNodeListByKey(Object keyValue,
                                                          String keyName, String nodeName, String client,
                                                          List<ServiceEntityNode> rawSEList)
            throws ServiceEntityConfigureException {
        return getEntityNodeListByKey(keyValue, keyName, nodeName, client,
                rawSEList, false);
    }

    /**
     * Get Service Entity Node List by Key information and from each client With
     * Default fuzzy search function
     *
     * @param nodeName
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<ServiceEntityNode> getEntityNodeListByParentUUID(String parentNodeUUID, String nodeName, String client)
            throws ServiceEntityConfigureException {
        return getEntityNodeListByKey(parentNodeUUID, IServiceEntityNodeFieldConstant.PARENTNODEUUID, nodeName, client,
                null, true);
    }

    /**
     * Get Service Entity Node ID List by Key information and from each client
     *
     * @param keyValue
     * @param keyName
     * @param nodeName
     * @param rawSEList On line raw data list <code>Optional = true</code> if value is
     *                  null, then retrieve raw data from persistence, otherwise,
     *                  using this online raw data list
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<String> getEntityIDListByKey(Object keyValue, String keyName,
                                             String nodeName, String client, List<String> rawSEList,
                                             boolean preciseMatchFlag) throws ServiceEntityConfigureException {
        if (client == null) {
            return null;
        }
        List<ServiceBasicKeyStructure> keyList = new ArrayList<ServiceBasicKeyStructure>();
        ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
        key1.setKeyName(IServiceEntityNodeFieldConstant.CLIENT);
        key1.setKeyValue(client);
        keyList.add(key1);
        if (keyValue != null && keyName != null) {
            ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure();
            key2.setKeyName(keyName);
            key2.setKeyValue(keyValue);
            keyList.add(key2);
        }
        // Persistence mode: retrieve data from persistence
        return this.serviceEntityDAO.getEntityFieldListByKeyList(keyList,
                nodeName, IServiceEntityNodeFieldConstant.ID, preciseMatchFlag);
    }

    /**
     * Get Service Entity Node ID List by Key information and from each client
     *
     * @param keyValue
     * @param keyName
     * @param nodeName
     * @param rawSEList On line raw data list <code>Optional = true</code> if value is
     *                  null, then retrieve raw data from persistence, otherwise,
     *                  using this online raw data list
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<String> getEntityUUIDListByKey(Object keyValue, String keyName,
                                               String nodeName, String client, List<String> rawSEList,
                                               boolean preciseMatchFlag) throws ServiceEntityConfigureException {
        if (client == null) {
            return null;
        }
        List<ServiceBasicKeyStructure> keyList = new ArrayList<ServiceBasicKeyStructure>();
        ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
        key1.setKeyName(IServiceEntityNodeFieldConstant.CLIENT);
        key1.setKeyValue(client);
        keyList.add(key1);
        if (keyValue != null && keyName != null) {
            ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure();
            key2.setKeyName(keyName);
            key2.setKeyValue(keyValue);
            keyList.add(key2);
        }
        // Persistence mode: retrieve data from persistence
        return this.serviceEntityDAO.getEntityFieldListByKeyList(keyList,
                nodeName, IServiceEntityNodeFieldConstant.UUID,
                preciseMatchFlag);
    }

    /**
     * Get Service Entity Node List by Key information and from each client
     *
     * @param keyValue
     * @param keyName
     * @param nodeName
     * @param rawSEList On line raw data list <code>Optional = true</code> if value is
     *                  null, then retrieve raw data from persistence, otherwise,
     *                  using this online raw data list
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<ServiceEntityNode> getEntityNodeListByKey(Object keyValue,
                                                          String keyName, String nodeName, String client,
                                                          List<ServiceEntityNode> rawSEList, boolean preciseMatchFlag)
            throws ServiceEntityConfigureException {
        if (client == null) {
            return null;
        }
        if (rawSEList == null || rawSEList.size() <= 0) {
            List<ServiceBasicKeyStructure> keyList = new ArrayList<ServiceBasicKeyStructure>();
            ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
            key1.setKeyName(IServiceEntityNodeFieldConstant.CLIENT);
            key1.setKeyValue(client);
            keyList.add(key1);
            if (keyValue != null && keyName != null) {
                ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure();
                key2.setKeyName(keyName);
                key2.setKeyValue(keyValue);
                keyList.add(key2);
            }
            // Persistence mode: retrieve data from persistence
            return this.serviceEntityDAO.getEntityNodeListByKeyList(keyList,
                    nodeName, preciseMatchFlag);

        } else {
            List<ServiceEntityNode> resultList = new ArrayList<>();
            List<ServiceEntityNode> rawList = getEntityNodeListByKey(keyValue,
                    keyName, nodeName, rawSEList);
            if (rawList != null && rawList.size() > 0) {
                for (ServiceEntityNode seNode : rawList) {
                    if (client.equals(seNode.getClient())) {
                        resultList.add(seNode);
                    }
                }
                return resultList;
            } else {
                return null;
            }
        }
    }

    /**
     * Get Service Entity Node List by Key information with default False
     * precise matching
     *
     * @param keyValue
     * @param keyName
     * @param nodeName
     * @param rawSEList On line raw data list <code>Optional = true</code> if value is
     *                  null, then retrieve raw data from persistence, otherwise,
     *                  using this online raw data list
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<ServiceEntityNode> getEntityNodeListByKey(Object keyValue,
                                                          String keyName, String nodeName,
                                                          List<ServiceEntityNode> rawSEList)
            throws ServiceEntityConfigureException {
        return getEntityNodeListByKey(keyValue, keyName, nodeName, rawSEList,
                false);
    }

    /**
     * Traverse node and sub node list
     * @param keyValue
     * @param keyName
     * @param nodeName
     * @throws ServiceEntityConfigureException
     */
    public void traverseNodeListRecursive(Object keyValue, String keyName,
                                     String nodeName, ServiceCollectionsHelper.IExecutorInTraverse<ServiceEntityNode> executorInTraverse) throws ServiceEntityConfigureException, DocActionException {
        boolean condFlag = true; // TODO-DAO: was HibernateDefaultImpDAO.checkCondition(keyValue, keyName);
        if (!condFlag) {
            String errorReason = "Condition should be defined!";
            throw new ServiceEntityConfigureException(
                    ServiceEntityConfigureException.PARA_CANNOT_DELETE, errorReason);
        }
        List<ServiceEntityNode> rawSEList = serviceEntityDAO.getEntityNodeListByKey(keyValue, keyName, nodeName);
        ServiceCollectionsHelper.traverseListInterrupt(rawSEList, executorInTraverse);
    }

    public void admDeleteModule(Object keyValue, String keyName,
                                String nodeName, SerialLogonInfo serialLogonInfo) throws DocActionException, ServiceEntityConfigureException {
        traverseNodeListRecursive(keyValue, keyName, nodeName, item -> {
            if (item instanceof DocMatItemNode) {
                // Special process of DocMatItemNode instance: clean all the relationship document
                docFlowProxy.clearDocMatItemRelationship((DocMatItemNode) item,
                        serialLogonInfo);
                serviceEntityDAO.deleteEntityNode(item);
            } else {
                serviceEntityDAO.deleteEntityNode(item);
            }
            return true;
        });
    }

    public void archiveModule(Object keyValue, String keyName,
                                String nodeName, SerialLogonInfo serialLogonInfo) throws DocActionException, ServiceEntityConfigureException {
        traverseNodeListRecursive(keyValue, keyName, nodeName, item -> {
            if (item instanceof DocMatItemNode) {
                // Special process of DocMatItemNode instance: clean all the relationship document
                docFlowProxy.clearDocMatItemRelationship((DocMatItemNode) item,
                        serialLogonInfo);
                archiveModuleCore(item);
            } else {
                archiveModuleCore(item);
            }
            return true;
        });
    }

    public void restoreArchiveModule(Object keyValue, String keyName,
                              String nodeName) throws DocActionException, ServiceEntityConfigureException {
        traverseNodeListRecursive(keyValue, keyName, nodeName, item -> {
            if (item instanceof DocMatItemNode) {
                // TODO Special process of DocMatItemNode instance
                // Check if DocFlowContextProxy.clearDocItemNodeUnit can solve it

                restoreArchiveModuleCore(item);
            } else {
                restoreArchiveModuleCore(item);
            }
            return true;
        });
    }

    public void archiveModuleCore(ServiceEntityNode serviceEntityNode) {
        String client = serviceEntityNode.getClient();
        if (client.matches("^[A-Z0-9]{3}$")) {
            serviceEntityNode.setClient("-" + client);
        } else {
            // Should raise exception here ?
        }
        serviceEntityDAO.updateEntity(serviceEntityNode);
    }

    public void restoreArchiveModuleCore(ServiceEntityNode serviceEntityNode) {
        String client = serviceEntityNode.getClient();
        if (client.matches("^-[A-Z0-9]{3}$")) {
            serviceEntityNode.setClient("-" + client);
        } else {
            // Should raise exception here ?
        }
        serviceEntityDAO.updateEntity(serviceEntityNode);
    }

    public void admDeleteEntityByKey(Object keyValue, String keyName,
                                     String nodeName) throws ServiceEntityConfigureException {
        if (this.serviceEntityDAO != null) {
            this.serviceEntityDAO.admDeleteEntityByKey(keyValue.toString(),
                    keyName, nodeName);
        }
    }

    public void archiveDeleteEntityByKey(Object keyValue, String keyName,
                                         String client, String nodeName, String logonUserUUID,
                                         String resOrgUUID) throws ServiceEntityConfigureException {
        List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
        ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure(client,
                IServiceEntityNodeFieldConstant.CLIENT);
        keyList.add(key1);
        ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure(keyValue,
                keyName);
        keyList.add(key2);
        List<ServiceEntityNode> rawList = this.serviceEntityDAO
                .getEntityNodeListByKeyList(keyList, nodeName, false);
        if (!ServiceCollectionsHelper.checkNullList(rawList)) {
            for (ServiceEntityNode seNode : rawList) {
                this.serviceEntityDAO.archiveDeleteEntityByKey(
                        seNode.getUuid(), IServiceEntityNodeFieldConstant.UUID,
                        nodeName, logonUserUUID, resOrgUUID);
            }
        }
    }

    public void recoverEntityByKey(Object keyValue, String keyName,
                                   String client, String nodeName, String logonUserUUID,
                                   String resOrgUUID) throws ServiceEntityConfigureException {
        List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
        ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure(client,
                IServiceEntityNodeFieldConstant.CLIENT);
        keyList.add(key1);
        ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure(keyValue,
                keyName);
        keyList.add(key2);
        List<ServiceEntityNode> rawList = this.serviceEntityDAO
                .getEntityNodeListByKeyList(keyList, nodeName, false);
        if (!ServiceCollectionsHelper.checkNullList(rawList)) {
            for (ServiceEntityNode seNode : rawList) {
                this.serviceEntityDAO.recoverEntityByKey(seNode.getUuid(),
                        IServiceEntityNodeFieldConstant.UUID, nodeName,
                        logonUserUUID, resOrgUUID);
            }
        }
    }

    /**
     * Get Service Entity Node List by Key information
     *
     * @param keyValue
     * @param keyName
     * @param nodeName
     * @param rawSEList On line raw data list <code>Optional = true</code> if value is
     *                  null, then retrieve raw data from persistence, otherwise,
     *                  using this online raw data list
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<ServiceEntityNode> getEntityNodeListByKey(Object keyValue,
                                                          String keyName, String nodeName,
                                                          List<ServiceEntityNode> rawSEList,
                                                          boolean preciseMatchFlag) throws ServiceEntityConfigureException {
        if (rawSEList == null || rawSEList.size() <= 0) {
            // Persistence mode: retrieve data from persistence
            return this.serviceEntityDAO.getEntityNodeListByKey(keyValue,
                    keyName, nodeName, preciseMatchFlag);
        } else {
            // on line mode, retrieve data from online raw data list
            if (keyValue != null
                    && keyValue != ServiceEntityStringHelper.EMPTYSTRING
                    && keyName != null) {
                List<ServiceEntityNode> resultList = new ArrayList<>();
                for (ServiceEntityNode seNode : rawSEList) {
                    // Check stationary key name
                    if (keyName.equals(IServiceEntityNodeFieldConstant.UUID)) {
                        if (seNode.getUuid().equals(keyValue)) {
                            resultList.add(seNode);
                            // Go to the next circle, skip the following code
                            continue;
                        }
                    }
                    if (keyName.equals(IServiceEntityNodeFieldConstant.ID)) {
                        if (seNode.getId().equals(keyValue)) {
                            resultList.add(seNode);
                            // Go to the next circle, skip the following code
                            continue;
                        }
                    }
                    if (keyName.equals(IServiceEntityNodeFieldConstant.NAME)) {
                        if (seNode.getName().equals(keyValue)) {
                            resultList.add(seNode);
                            // Go to the next circle, skip the following code
                            continue;
                        }
                    }
                    if (keyName
                            .equals(IServiceEntityNodeFieldConstant.PARENTNODEUUID)) {
                        if (seNode.getParentNodeUUID().equals(keyValue)) {
                            resultList.add(seNode);
                            // Go to the next circle, skip the following code
                            continue;
                        }
                    }
                    if (keyName
                            .equals(IServiceEntityNodeFieldConstant.ROOTNODEUUID)) {
                        if (seNode.getRootNodeUUID().equals(keyValue)) {
                            resultList.add(seNode);
                            // Go to the next circle, skip the following code
                            continue;
                        }
                    }
                    // If cannot hit the above stationary key, then have to
                    // check
                    // dynamic
                }
                return resultList;
            } else {
                // In case search condition not defined, return raw list
                // directly
                return rawSEList;
            }
        }
    }

    /**
     * Delete service entity node instance list by key information, this
     * deletion is for single SE node
     *
     * @param keyValue
     * @param keyName
     * @param nodeName
     * @throws ServiceEntityConfigureException
     */
    public void deleteEntityNodeListByKey(String keyValue, String keyName,
                                          String nodeName, String client, boolean preciseMatchFlag)
            throws ServiceEntityConfigureException {
        boolean condFlag = keyValue != null
                && keyValue != ServiceEntityStringHelper.EMPTYSTRING
                && keyName != null
                && keyName != ServiceEntityStringHelper.EMPTYSTRING;
        List<ServiceBasicKeyStructure> refinedKeyList = new ArrayList<ServiceBasicKeyStructure>();
        String tableName = this.getSeConfigureProxy()
                .getConfigureByNodeName(nodeName).getTableName();
        if (!condFlag) {
            String errorReason = "Deletion condition should be defined!";
            throw new ServiceEntityConfigureException(
                    ServiceEntityConfigureException.PARA_CANNOT_DELETE, errorReason);
        } else {
            ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
            key1.setKeyName(keyName);
            key1.setKeyValue(keyValue);
            refinedKeyList.add(key1);
        }
        boolean crossClientFlag = ServiceEntityPersistenceHelper
                .checkTableCrossClient(tableName);
        if (!crossClientFlag) {
            ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
            key1.setKeyName(IServiceEntityNodeFieldConstant.CLIENT);
            key1.setKeyValue(client);
            refinedKeyList.add(key1);
        }
        List<ServiceEntityNode> deList = this.getEntityNodeListByKeyList(
                refinedKeyList, nodeName, null);
        // for hibenate template, only link DB for once and commit, no big
        // performance issue
        for (ServiceEntityNode seNode : deList) {
            serviceEntityDAO.deleteEntityNode(seNode);
        }
    }

    public List<ServiceEntityNode> getEntityNodeListByKeyList(
            List<ServiceBasicKeyStructure> keyList, String nodeName,
            String client, List<ServiceEntityNode> rawSEList,
            boolean preciseMatchFlag, boolean disableCache) throws ServiceEntityConfigureException {
        List<ServiceBasicKeyStructure> refinedKeyList = new ArrayList<>();
        String tableName = this.getSeConfigureProxy()
                .getConfigureByNodeName(nodeName).getTableName();
        boolean crossClientFlag = ServiceEntityPersistenceHelper
                .checkTableCrossClient(tableName);
        if (!crossClientFlag) {
            ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
            key1.setKeyName(IServiceEntityNodeFieldConstant.CLIENT);
            key1.setKeyValue(client);
            refinedKeyList.add(key1);
        }
        if (keyList != null && keyList.size() > 0) {
            refinedKeyList.addAll(keyList);
        }
        if (rawSEList == null || rawSEList.size() <= 0) {
            // Persistence mode: retrieve data from persistence
            return this.serviceEntityDAO.getEntityNodeListByKeyList(
                    refinedKeyList, nodeName, preciseMatchFlag, disableCache);
        } else {
            // currently no implementation of on-line mode
            return this.serviceEntityDAO.getEntityNodeListByKeyList(
                    refinedKeyList, nodeName, preciseMatchFlag, disableCache);
        }
    }

    public List<ServiceEntityNode> getEntityNodeListByMultipleKey(
            List<?> keyValueList, String keyName, String nodeName,
            String client, List<ServiceEntityNode> rawSEList)
            throws ServiceEntityConfigureException {
        if (ServiceCollectionsHelper.checkNullList(keyValueList)) {
            return null;
        }
        List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
        ServiceBasicKeyStructure key = new ServiceBasicKeyStructure();
        key.setKeyName(keyName);
        key.setMultipleValueList(keyValueList);
        keyList.add(key);
        return this.getEntityNodeListByKeyList(keyList, nodeName, client,
                rawSEList);
    }

    public List<ServiceEntityNode> getEntityNodeListByKeyList(
            List<ServiceBasicKeyStructure> keyList, String nodeName,
            String client, List<ServiceEntityNode> rawSEList)
            throws ServiceEntityConfigureException {
        return getEntityNodeListByKeyList(keyList, nodeName, client, rawSEList,
                false, false);
    }

    public List<ServiceEntityNode> getEntityNodeListByKeyList(
            List<ServiceBasicKeyStructure> keyList, String nodeName,
            List<ServiceEntityNode> rawSEList)
            throws ServiceEntityConfigureException {
        return getEntityNodeListByKeyList(keyList, nodeName, rawSEList, false);
    }

    public List<ServiceEntityNode> getEntityNodeListByKeyList(
            List<ServiceBasicKeyStructure> keyList, String nodeName,
            List<ServiceEntityNode> rawSEList, boolean preciseMatchFlag)
            throws ServiceEntityConfigureException {
        if (rawSEList == null || rawSEList.size() <= 0) {
            // Persistence mode: retrieve data from persistence
            return this.serviceEntityDAO.getEntityNodeListByKeyList(keyList,
                    nodeName, preciseMatchFlag);
        } else {
            // currently no implementation of on-line mode
            return this.serviceEntityDAO.getEntityNodeListByKeyList(keyList,
                    nodeName, preciseMatchFlag);
        }
    }

    /**
     * Get Service Entity Node List by key information and default by descending
     * last update time from persistence
     *
     * @param keyValue
     * @param keyName
     * @param nodeName
     * @param startIndex
     * @param limitNumber
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<ServiceEntityNode> getEntityNodeListByKeyLastUpdate(
            Object keyValue, String keyName, String nodeName, int startIndex,
            int limitNumber, String client)
            throws ServiceEntityConfigureException {
        return getEntityNodeListByKeyLastUpdate(keyValue, keyName, nodeName,
                startIndex, limitNumber, client, false);
    }

    public List<ServiceEntityNode> getEntityNodeListByKeyLastUpdate(
            Object keyValue, String keyName, String nodeName, int startIndex,
            int limitNumber, String client, boolean preciseMatchFlag)
            throws ServiceEntityConfigureException {
        return this.serviceEntityDAO.getEntityNodeListByKey(keyValue, keyName,
                nodeName, startIndex, limitNumber,
                IServiceEntityNodeFieldConstant.LASTUPDATETIME,
                ServiceEntityDAO.ORDER_DESC, client, preciseMatchFlag);
    }

    /**
     * Return the number of record from persistence
     *
     * @param keyValue
     * @param keyName
     * @param nodeName
     * @return
     * @throws ServiceEntityConfigureException
     */
    public int getRecordNum(Object keyValue, String keyName, String nodeName,
                            String Client) throws ServiceEntityConfigureException {
        return getRecordNum(keyValue, keyName, nodeName, Client, false);
    }

    public int getRecordNum(Object keyValue, String keyName, String nodeName,
                            String client, boolean preciseMatchFlag)
            throws ServiceEntityConfigureException {
        return this.serviceEntityDAO.getRecordNum(keyValue, keyName, nodeName,
                client, preciseMatchFlag);
    }

    public int getRecordNum(List<ServiceBasicKeyStructure> keyList,
                            String client, String nodeName)
            throws ServiceEntityConfigureException {
        return getRecordNum(keyList, nodeName, client, false);
    }

    public int getRecordNum(List<ServiceBasicKeyStructure> keyList,
                            String nodeName, String client, boolean preciseMatchFlag)
            throws ServiceEntityConfigureException {
        List<ServiceBasicKeyStructure> resultKeyList = new ArrayList<ServiceBasicKeyStructure>();
        ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
        key1.setKeyName(IServiceEntityNodeFieldConstant.CLIENT);
        key1.setKeyValue(client);
        resultKeyList.add(key1);
        if (keyList != null && keyList.size() > 0) {
            resultKeyList.addAll(keyList);
        }
        return this.serviceEntityDAO.getRecordNum(resultKeyList, nodeName,
                preciseMatchFlag);
    }

    protected boolean checkHitDynamicField(ServiceEntityNode seNode,
                                           String keyName, Object keyValue)
            throws ServiceEntityConfigureException {
        try {
            Field field = seNode.getClass().getDeclaredField(keyName);
            field.setAccessible(true);
            Object rawValue = field.get(seNode);
            return rawValue != null && rawValue.equals(keyValue);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_SYSTEM_WRONG,
                    e.getMessage());
        }
    }

    /**
     * Get the only one service entity node instance, if more than one SE nodes
     * match search condition, then return the first one
     *
     * @param keyValue
     * @param keyName
     * @param nodeName
     * @param rawSEList On line raw data list <code>Optional = true</code> if value is
     *                  null, then retrieve raw data from persistence, otherwise,
     *                  using this online raw data list
     * @return
     * @throws ServiceEntityConfigureException
     */
    public ServiceEntityNode getEntityNodeByKey(Object keyValue,
                                                String keyName, String nodeName, String client,
                                                List<ServiceEntityNode> rawSEList)
            throws ServiceEntityConfigureException {
        return getEntityNodeByKey(keyValue, keyName, nodeName, client,
                rawSEList, false);
    }

    /**
     * Get service entity node instance by UUID
     *
     * @param uuid
     * @param nodeName
     * @param client
     * @return
     * @throws ServiceEntityConfigureException
     */
    public ServiceEntityNode getEntityNodeByUUID(String uuid, String nodeName,
                                                 String client) throws ServiceEntityConfigureException {
        return getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID,
                nodeName, client, null, false);
    }

    /**
     * Retrieves a service entity node instance by its UUID from the database,
     * bypassing the Hibernate cache to ensure fresh data is loaded.
     *
     * @param uuid the unique identifier of the service entity node
     * @param nodeName the name of the node type
     * @param client the client identifier
     * @return the service entity node instance matching the UUID, or null if not found
     * @throws ServiceEntityConfigureException if configuration or retrieval fails
     */
    public ServiceEntityNode getDBEntityNodeByUUID(String uuid, String nodeName,
                                                 String client) throws ServiceEntityConfigureException {
        return getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID,
                nodeName, client, null, true, true);
    }

    /**
     * Update the updated value to buffer, This method should be implemented in
     * sub class.
     *
     * @param serviceEntityNode
     */
    public void updateBuffer(ServiceEntityNode serviceEntityNode) throws ServiceComExecuteException {
    }

    public void updateBuffer(List<ServiceEntityNode> seNodeList) throws ServiceComExecuteException {
    }

    /**
     * Get the only one service entity node instance, if more than one SE nodes
     * match search condition, then return the first one
     *
     * @param keyValue
     * @param keyName
     * @param nodeName
     * @param rawSEList On line raw data list <code>Optional = true</code> if value is
     *                  null, then retrieve raw data from persistence, otherwise,
     *                  using this online raw data list
     * @return
     * @throws ServiceEntityConfigureException
     */
    public ServiceEntityNode getEntityNodeByKey(Object keyValue,
                                                String keyName, String nodeName, String client,
                                                List<ServiceEntityNode> rawSEList, boolean preciseMatchFlag) throws ServiceEntityConfigureException {
        return getEntityNodeByKey(keyValue, keyName, nodeName, client,
                rawSEList, preciseMatchFlag, false);
    }

    /**
     * Get the only one service entity node instance, if more than one SE nodes
     * match search condition, then return the first one
     *
     * @param keyValue
     * @param keyName
     * @param nodeName
     * @param rawSEList On line raw data list <code>Optional = true</code> if value is
     *                  null, then retrieve raw data from persistence, otherwise,
     *                  using this online raw data list
     * @return
     * @throws ServiceEntityConfigureException
     */
    public ServiceEntityNode getEntityNodeByKey(Object keyValue,
                                                String keyName, String nodeName, String client,
                                                List<ServiceEntityNode> rawSEList, boolean preciseMatchFlag, boolean cacheDisable)
            throws ServiceEntityConfigureException {
        if (keyValue == null
                || keyValue.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
            return null;
        }
        List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
        ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
        key1.setKeyName(keyName);
        key1.setKeyValue(keyValue);
        keyList.add(key1);
        List<ServiceEntityNode> resultList = getEntityNodeListByKeyList(
                keyList, nodeName, client, rawSEList, preciseMatchFlag, cacheDisable);
        if (resultList != null && resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    /**
     * Get the only one service entity node instance, if more than one SE nodes
     * match search condition, then return the first one
     *
     * @param keyValue
     * @param keyName
     * @param nodeName
     * @param rawSEList On line raw data list <code>Optional = true</code> if value is
     *                  null, then retrieve raw data from persistence, otherwise,
     *                  using this online raw data list
     * @return
     * @throws ServiceEntityConfigureException
     */
    public ServiceEntityNode getEntityNodeByKey(String keyValue,
                                                String keyName, String nodeName, List<ServiceEntityNode> rawSEList)
            throws ServiceEntityConfigureException {
        if (keyValue == null
                || keyValue.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
            return null;
        }
        List<ServiceEntityNode> resultList = getEntityNodeListByKey(keyValue,
                keyName, nodeName, rawSEList);
        if (resultList != null && resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    public void setAttachmentHttpHeaders(HttpHeaders headers, String fileType,
                                         String fileName) {
        // Special handling for PDF file
        if (AttachmentConstantHelper.TYPE_PDF.equals(fileType)) {
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            String filename = fileName;
            if (filename == null) {
                filename = DefaultDateFormatConstant.DATE_MIN_FORMAT
                        .format(new Date()) + ".pdf";
            }
            if (!filename.contains("pdf")) {
                filename = filename + ".pdf";
            }
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        }
        if (AttachmentConstantHelper.TYPE_DOC.equals(fileType)) {
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentType(MediaType
                    .parseMediaType("application/msword"));
            if (fileName == null) {
                fileName = DefaultDateFormatConstant.DATE_MIN_FORMAT
                        .format(new Date()) + ".doc";
            }
            if (!fileName.contains("doc")) {
                fileName = fileName + ".doc";
            }
            headers.setContentDispositionFormData(fileName, fileName);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        }
        if (AttachmentConstantHelper.TYPE_DOCX.equals(fileType)) {
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentType(MediaType
                    .parseMediaType("application/msword"));
            String filename = fileName;
            if (filename == null) {
                filename = DefaultDateFormatConstant.DATE_MIN_FORMAT
                        .format(new Date()) + ".docx";
            }
            if (!filename.contains("docx")) {
                filename = filename + ".docx";
            }
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        }
        if (AttachmentConstantHelper.TYPE_PNG.equals(fileType)) {
            headers.setContentType(MediaType.IMAGE_PNG);
        }
        if (AttachmentConstantHelper.TYPE_JPEG.equals(fileType)) {
            headers.setContentType(MediaType.IMAGE_JPEG);
        }
        if (AttachmentConstantHelper.TYPE_JPG.equals(fileType)) {
            headers.setContentType(MediaType.IMAGE_JPEG);
        }
        if (AttachmentConstantHelper.TYPE_JRXML.equals(fileType)) {
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentType(MediaType
                    .parseMediaType(MediaType.APPLICATION_XML_VALUE));
            String filename = fileName;
            if (!filename.contains(AttachmentConstantHelper.TYPE_JRXML)) {
                filename = filename + "." + AttachmentConstantHelper.TYPE_JRXML;
            }
        }
        if (AttachmentConstantHelper.TYPE_XLSX.equals(fileType)) {
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentType(new MediaType("application",
                    "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            String filename = fileName;
            if (!filename.contains(AttachmentConstantHelper.TYPE_XLSX)) {
                filename = filename + "." + AttachmentConstantHelper.TYPE_XLSX;
            }
        }
        if (AttachmentConstantHelper.TYPE_XLSX.equals(fileType)) {
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentType(new MediaType("application",
                    "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            String filename = fileName;
            if (!filename.contains(AttachmentConstantHelper.TYPE_XLS)) {
                filename = filename + "." + AttachmentConstantHelper.TYPE_XLS;
            }
        }
        if (AttachmentConstantHelper.TYPE_XLS.equals(fileType)) {
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            String filename = fileName;
            if (!filename.contains(AttachmentConstantHelper.TYPE_XLS)) {
                filename = filename + "." + AttachmentConstantHelper.TYPE_XLS;
            }
        }

    }

    public ServiceEntityNode getEntityNodeByKey(String keyValue,
                                                String keyName, String nodeName, List<ServiceEntityNode> rawSEList,
                                                boolean preciseMatchFlag) throws ServiceEntityConfigureException {
        if (keyValue == null
                || keyValue.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
            return null;
        }
        List<ServiceEntityNode> resultList = getEntityNodeListByKey(keyValue,
                keyName, nodeName, rawSEList, preciseMatchFlag);
        if (resultList != null && resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    public ServiceEntityNode getEntityNodeByKeyList(
            List<ServiceBasicKeyStructure> keyList, String nodeName,
            String client, List<ServiceEntityNode> rawSEList, boolean preciseMatchFlag)
            throws ServiceEntityConfigureException {
        List<ServiceBasicKeyStructure> refinedkeyList = keyList;
        ServiceBasicKeyStructure clientKey = new ServiceBasicKeyStructure();
        clientKey.setKeyName(IServiceEntityNodeFieldConstant.CLIENT);
        clientKey.setKeyValue(client);
        refinedkeyList.add(clientKey);
        List<ServiceEntityNode> resultList = getEntityNodeListByKeyList(
                refinedkeyList, nodeName, rawSEList, preciseMatchFlag);
        if (resultList != null && resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    public ServiceEntityNode getEntityNodeByKeyList(
            List<ServiceBasicKeyStructure> keyList, String nodeName,
            String client, List<ServiceEntityNode> rawSEList)
            throws ServiceEntityConfigureException {
        List<ServiceBasicKeyStructure> refinedkeyList = keyList;
        ServiceBasicKeyStructure clientKey = new ServiceBasicKeyStructure();
        clientKey.setKeyName(IServiceEntityNodeFieldConstant.CLIENT);
        clientKey.setKeyValue(client);
        refinedkeyList.add(clientKey);
        List<ServiceEntityNode> resultList = getEntityNodeListByKeyList(
                refinedkeyList, nodeName, rawSEList);
        if (resultList != null && resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    public ServiceEntityNode getEntityNodeByKeyList(
            List<ServiceBasicKeyStructure> keyList, String nodeName,
            List<ServiceEntityNode> rawSEList)
            throws ServiceEntityConfigureException {
        List<ServiceEntityNode> resultList = getEntityNodeListByKeyList(
                keyList, nodeName, rawSEList);
        if (resultList != null && resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    /**
     * Build the reference information from target node to reference node
     *
     * @param target
     * @param refNode
     * @param refPackageName optional = "true", if value is empty, package information will
     *                       be retrieved from persistency
     * @throws ServiceEntityConfigureException
     */
    public void buildReferenceNode(ServiceEntityNode target,
                                   ReferenceNode refNode, String refPackageName)
            throws ServiceEntityConfigureException {
        referenceService.buildReferenceNode(target, refNode, refPackageName);
    }

    /**
     * Main entrance to load necessary data from persistence and convert into
     * service module
     *
     * @param serviceModuleType
     * @param serviceEntityNode
     * @return
     * @throws ServiceModuleProxyException
     * @throws ServiceEntityConfigureException
     */
    public ServiceModule loadServiceModule(Class<?> serviceModuleType,
                                           ServiceEntityNode serviceEntityNode)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        return serviceModuleProxy.loadServiceModule(serviceModuleType, this,
                serviceEntityNode);
    }

    /**
     * Main entrance to load necessary data from persistence and convert into
     * service module
     *
     * @param serviceModuleType
     * @param serviceEntityNode
     * @return
     * @throws ServiceModuleProxyException
     * @throws ServiceEntityConfigureException
     */
    public ServiceModule loadServiceModule(Class<?> serviceModuleType,
                                           ServiceEntityNode serviceEntityNode,
                                           ServiceUIModelExtension serviceUIModelExtension)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        ServiceUIModelExtensionUnion serviceUIModelExtensionUnion = serviceUIModelExtension
                .genUIModelExtensionUnion().get(0);
        return serviceModuleProxy.loadServiceModule(serviceModuleType, this,
                serviceEntityNode, serviceUIModelExtension, serviceUIModelExtensionUnion.getNodeInstId());
    }

    /**
     * Main entrance to load necessary data from persistence and convert into
     * service module
     *
     * @param serviceModuleType
     * @param serviceEntityNode
     * @return
     * @throws ServiceModuleProxyException
     * @throws ServiceEntityConfigureException
     */
    public ServiceModule loadServiceModuleWithFlatNodes(Class<?> serviceModuleType,
                                                        ServiceEntityNode serviceEntityNode,
                                                        ServiceUIModelExtension serviceUIModelExtension)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        ServiceUIModelExtensionUnion serviceUIModelExtensionUnion = serviceUIModelExtension
                .genUIModelExtensionUnion().get(0);
        return serviceModuleProxy.loadServiceModuleCoreWithFlatNodes(serviceModuleType,
                this, serviceEntityNode, serviceUIModelExtension, serviceUIModelExtensionUnion.getNodeInstId());
    }

    /**
     * Main entrance to load necessary data from persistence and convert into
     * service module
     *
     * @param serviceModuleType
     * @param serviceEntityNode
     * @return
     * @throws ServiceModuleProxyException
     * @throws ServiceEntityConfigureException
     */
    public ServiceModule loadServiceModule(Class<?> serviceModuleType,
                                           ServiceEntityNode serviceEntityNode,
                                           ServiceUIModelExtension serviceUIModelExtension, String nodeInstId)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        return serviceModuleProxy.loadServiceModule(serviceModuleType, this,
                serviceEntityNode, serviceUIModelExtension, nodeInstId);
    }

    /**
     * Main entrance to update/insert service module data into persistence
     *
     * @param serviceModuleType
     * @param serviceModule
     * @param logonUserUUID
     * @param organizationUUID
     * @throws ServiceModuleProxyException
     * @throws ServiceEntityConfigureException
     */
    public void updateServiceModule(Class<?> serviceModuleType,
                                    ServiceModule serviceModule, String logonUserUUID,
                                    String organizationUUID) throws ServiceModuleProxyException,
            ServiceEntityConfigureException {
        serviceModuleProxy.updateServiceModule(serviceModuleType, this,
                serviceModule, logonUserUUID, organizationUUID, false);
    }

    /**
     * Main entrance to update/insert service module data into persistence
     *
     * @param serviceModuleType
     * @param serviceModule
     * @param logonUserUUID
     * @param organizationUUID
     * @throws ServiceModuleProxyException
     * @throws ServiceEntityConfigureException
     */
    public void updateServiceModuleWithDelete(Class<?> serviceModuleType,
                                              ServiceModule serviceModule, String logonUserUUID,
                                              String organizationUUID, String nodeInstId,
                                              ServiceUIModelExtension serviceUIModelExtension)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        serviceModuleProxy.updateServiceModule(serviceModuleType, this,
                serviceModule, logonUserUUID, organizationUUID, true,
                nodeInstId, serviceUIModelExtension);
    }

    /**
     * Main Entrance to refresh to service node entity from UI, log action and
     * return updated UI model back
     *
     * @param seUIComModel
     * @param seNodeClass
     * @param serviceNodeUpdatedCallback
     * @param serviceUIModelExtensionUnion
     * @param logonUserUUID
     * @param organizationUUID
     * @return
     * @throws ServiceModuleProxyException
     * @throws ServiceEntityConfigureException
     */
    public SEUIComModel updateServiceEntityWrapper(
            SEUIComModel seUIComModel,
            Class<?> seNodeClass,
            Function<ServiceEntityNode, ServiceEntityNode> serviceNodeUpdatedCallback,
            Function<ServiceEntityNode, String[]> setLogIdNameCallBack,
            ServiceUIModelExtensionUnion serviceUIModelExtensionUnion,
            String logonUserUUID, String organizationUUID)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        return serviceUIModuleProxy.updateServiceEntityWrapper(seUIComModel,
                seNodeClass, this, serviceNodeUpdatedCallback,
                setLogIdNameCallBack, serviceUIModelExtensionUnion,
                logonUserUUID, organizationUUID);
    }

    /**
     * Main Entrance to refresh to service module from UI, log action and return
     * updated UI model back
     *
     * @param serviceModuleType
     * @param serviceUIModule
     * @param serviceUIModelExtension
     * @param serviceUIModelExtensionUnion
     * @param serviceModuleUpdatedCallback
     * @param logonInfo
     * @throws ServiceModuleProxyException
     * @throws ServiceUIModuleProxyException
     * @throws ServiceEntityConfigureException
     */
    @Transactional
    public ServiceUIModule updateServiceModuleDeleteWrapper(
            Class<?> serviceModuleType,
            ServiceUIModule serviceUIModule,
            ServiceUIModelExtension serviceUIModelExtension,
            ServiceUIModelExtensionUnion serviceUIModelExtensionUnion,
            Function<ServiceModule, ServiceModule> serviceModuleUpdatedCallback,
            Function<ServiceEntityNode, String[]> setLogIdNameCallBack,
            LogonInfo logonInfo) throws ServiceModuleProxyException,
            ServiceUIModuleProxyException, ServiceEntityConfigureException {
        return serviceUIModuleProxy.updateServiceModuleWrapper(
                serviceModuleType, this, serviceUIModule,
                serviceUIModelExtension, serviceUIModelExtensionUnion,
                serviceModuleUpdatedCallback, setLogIdNameCallBack, logonInfo,
                true);
    }

    /**
     * Main entrance to update/insert service module data into persistence
     *
     * @param serviceModuleType
     * @param serviceUIModule
     * @param logonUserUUID
     * @param organizationUUID
     * @throws ServiceModuleProxyException
     * @throws ServiceEntityConfigureException
     * @throws ServiceUIModuleProxyException
     */
    @Transactional
    public ServiceModule updateModelWithDelete(Class<?> serviceModuleType,
                                               ServiceUIModule serviceUIModule, String logonUserUUID,
                                               ServiceUIModelExtension serviceUIModelExtension,
                                               String organizationUUID, String client) throws ServiceModuleProxyException,
            ServiceEntityConfigureException, ServiceUIModuleProxyException {
        ServiceModule serviceModule = genServiceModuleFromServiceUIModel(
                serviceModuleType, serviceUIModule.getClass(), serviceUIModule,
                serviceUIModelExtension);
        updateServiceModuleWithDelete(serviceModuleType,
                serviceModule, logonUserUUID, organizationUUID);
        try {
            serviceExtensionManager.updateToStoreModelWrapper(
                    serviceUIModule, client,
                    logonUserUUID, organizationUUID);
        } catch (IllegalArgumentException
                | IllegalAccessException e) {
            throw new ServiceUIModuleProxyException(ServiceUIModuleProxyException.PARA_SYSTEM_WRONG, e.getMessage());
        } catch (ServiceExtensionException e) {
            managerLogger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "updateModelWithDelete"));
            throw new ServiceUIModuleProxyException(ServiceUIModuleProxyException.PARA_SYSTEM_WRONG, e.getMessage());
        }
        return serviceModule;
    }

    /**
     * Main entrance to update/insert service module data into persistence
     *
     * @param serviceModuleType
     * @param serviceModule
     * @param logonUserUUID
     * @param organizationUUID
     * @throws ServiceModuleProxyException
     * @throws ServiceEntityConfigureException
     */
    @Transactional
    public void updateServiceModuleWithDelete(Class<?> serviceModuleType,
                                              ServiceModule serviceModule, String logonUserUUID,
                                              String organizationUUID) throws ServiceModuleProxyException,
            ServiceEntityConfigureException {
        serviceModuleProxy.updateServiceModule(serviceModuleType, this,
                serviceModule, logonUserUUID, organizationUUID, true);
    }

    public void updateServiceModuleWithPost(Class<?> serviceModuleType,
                                            ServiceModule serviceModule, String logonUserUUID,
                                            String organizationUUID) throws ServiceModuleProxyException,
            ServiceEntityConfigureException {
        serviceModuleProxy.updateServiceModule(serviceModuleType, this,
                serviceModule, logonUserUUID, organizationUUID, true);
        this.postUpdateServiceModel(serviceModule, logonUserUUID,
                organizationUUID);
    }

    public void updateServiceModuleWithPostLog(Class<?> serviceModuleType,
                                               ServiceModule serviceModule, ServiceModule serviceModuleBack,
                                               ServiceUIModelExtension serviceUIModelExtension,
                                               ServiceUIModelExtensionUnion serviceUIModelExtensionUnion,
                                               Function<ServiceEntityNode, String[]> setLogIdNameCallBack,
                                               String logonUserUUID, String organizationUUID,
                                               boolean deleteSubListItemFlag) throws ServiceModuleProxyException,
            ServiceEntityConfigureException {
        serviceModuleProxy.updateServiceModule(serviceModuleType, this,
                serviceModule, logonUserUUID, organizationUUID, true);
        serviceUIModuleProxy.updateServiceModuleWithLog(serviceModuleType,
                this, serviceModule, serviceModuleBack,
                serviceUIModelExtension, serviceUIModelExtensionUnion,
                tmpServiceModel -> {
                    try {
                        this.postUpdateServiceModel(tmpServiceModel,
                                logonUserUUID, organizationUUID);
                    } catch (Exception e) {
                        managerLogger.error("Failed during post-update service model processing", e);
                    }
                    return tmpServiceModel;
                }, setLogIdNameCallBack, logonUserUUID, organizationUUID,
                deleteSubListItemFlag);

    }

    /**
     * Main entrance to generate Service UI Model instance from service model
     *
     * @param serviceUIModuleType
     * @param serviceModuleType
     * @param serviceModule
     * @throws ServiceModuleProxyException
     * @throws ServiceUIModuleProxyException
     * @throws ServiceEntityConfigureException
     */
    public ServiceUIModule genServiceUIModuleFromServiceModel(
            Class<?> serviceUIModuleType, Class<?> serviceModuleType,
            ServiceModule serviceModule,
            ServiceUIModelExtension serviceUIModelExtension, LogonInfo logonInfo)
            throws ServiceModuleProxyException, ServiceUIModuleProxyException,
            ServiceEntityConfigureException {
        return genServiceUIModuleFromServiceModel(serviceUIModuleType, serviceModuleType, serviceModule,
                serviceUIModelExtension, null, logonInfo);
    }

    /**
     * Main entrance to generate Service UI Model instance from service model
     *
     * @param serviceUIModuleType
     * @param serviceModuleType
     * @param serviceModule
     * @throws ServiceModuleProxyException
     * @throws ServiceUIModuleProxyException
     * @throws ServiceEntityConfigureException
     */
    public ServiceUIModule genServiceUIModuleFromServiceModel(
            Class<?> serviceUIModuleType, Class<?> serviceModuleType,
            ServiceModule serviceModule,
            ServiceUIModelExtension serviceUIModelExtension, List<ServiceModuleConvertPara> additionalConvertParaList,
            LogonInfo logonInfo)
            throws ServiceModuleProxyException, ServiceUIModuleProxyException,
            ServiceEntityConfigureException {
        // Check Extension union
        if (ServiceCollectionsHelper.checkNullList(serviceUIModelExtension
                .genUIModelExtensionUnion())) {
            throw new ServiceUIModuleProxyException(
                    ServiceUIModuleProxyException.TYPE_SYSTEM_WRONG);
        }
        // Get the default first extension union.
        ServiceUIModelExtensionUnion serviceUIModelExtensionUnion = serviceUIModelExtension
                .genUIModelExtensionUnion().get(0);
        if(additionalConvertParaList == null){
            additionalConvertParaList = new ArrayList<>();
        }
        // Initial the Additional convert parameters.
        ServiceUIModule serviceUIModule = serviceUIModuleProxy
                .genServiceUIModuleFromServiceModelCore(serviceUIModuleType,
                        serviceModuleType, serviceUIModelExtension,
                        serviceUIModelExtensionUnion, this, serviceModule,
                        logonInfo, additionalConvertParaList);
        return serviceUIModule;
    }

    /**
     * Main entrance to generate Service UI Model instance from service model
     *
     * @param serviceUIModuleType
     * @param serviceModuleType
     * @param serviceModule
     * @throws ServiceModuleProxyException
     * @throws ServiceUIModuleProxyException
     * @throws ServiceEntityConfigureException
     */
    public ServiceUIModule genServiceUIModuleWithFlatNode(
            Class<?> serviceUIModuleType, Class<?> serviceModuleType,
            ServiceModule serviceModule,
            ServiceUIModelExtension serviceUIModelExtension, List<ServiceModuleConvertPara> additionalConvertParaList, LogonInfo logonInfo)
            throws ServiceModuleProxyException, ServiceUIModuleProxyException,
            ServiceEntityConfigureException {
        // Check Extension union
        if (ServiceCollectionsHelper.checkNullList(serviceUIModelExtension
                .genUIModelExtensionUnion())) {
            throw new ServiceUIModuleProxyException(
                    ServiceUIModuleProxyException.TYPE_SYSTEM_WRONG);
        }
        // Get the default first extension union.
        ServiceUIModelExtensionUnion serviceUIModelExtensionUnion = serviceUIModelExtension
                .genUIModelExtensionUnion().get(0);
        // Initial the Additional convert parameters.
        ServiceUIModule serviceUIModule = serviceUIModuleProxy
                .genServiceUIModuleFromServiceModelCore(serviceUIModuleType,
                        serviceModuleType, serviceUIModelExtension,
                        serviceUIModelExtensionUnion, this, serviceModule,
                        logonInfo, additionalConvertParaList);
        return serviceUIModule;
    }

    /**
     * Main entrance to generate Service UI Model instance from service model
     *
     * @param serviceUIModuleType
     * @param serviceModuleType
     * @param serviceModule
     * @throws ServiceModuleProxyException
     * @throws ServiceUIModuleProxyException
     * @throws ServiceEntityConfigureException
     */
    @Deprecated
    public ServiceUIModule genServiceUIModuleFromServiceModel(
            Class<?> serviceUIModuleType, Class<?> serviceModuleType,
            ServiceModule serviceModule,
            ServiceUIModelExtension serviceUIModelExtension)
            throws ServiceModuleProxyException, ServiceUIModuleProxyException,
            ServiceEntityConfigureException {
        // Check Extension union
        if (ServiceCollectionsHelper.checkNullList(serviceUIModelExtension
                .genUIModelExtensionUnion())) {
            throw new ServiceUIModuleProxyException(
                    ServiceUIModuleProxyException.TYPE_SYSTEM_WRONG);
        }
        // Get the default first extension union.
        ServiceUIModelExtensionUnion serviceUIModelExtensionUnion = serviceUIModelExtension
                .genUIModelExtensionUnion().get(0);

        // Initial the Additional convert parameters.
        ServiceUIModule serviceUIModule = serviceUIModuleProxy
                .genServiceUIModuleFromServiceModelCore(serviceUIModuleType,
                        serviceModuleType, serviceUIModelExtension,
                        serviceUIModelExtensionUnion, this, serviceModule,
                        null, new ArrayList<>());
        return serviceUIModule;
    }

    /**
     * Generate the UIModel dynamically from UI Model Extension
     *
     * @param uiModelClass
     * @param serviceUIModelExtensionUnion
     * @param rawSENodeValue
     * @param additionalConvertParaList
     * @return
     * @throws ServiceEntityConfigureException
     * @throws ServiceUIModuleProxyException
     * @throws ServiceModuleProxyException
     */
    public SEUIComModel genUIModelFromUIModelExtension(Class<?> uiModelClass,
                                                       ServiceUIModelExtensionUnion serviceUIModelExtensionUnion,
                                                       ServiceEntityNode rawSENodeValue, LogonInfo logonInfo,
                                                       List<ServiceModuleConvertPara> additionalConvertParaList)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        SEUIComModel uiModelInstance = serviceUIModuleProxy
                .genUIModuleInExtensionUnion(uiModelClass,
                        serviceUIModelExtensionUnion, this, rawSENodeValue,
                        logonInfo, additionalConvertParaList);
        return uiModelInstance;
    }

    /**
     * Get the back-end service entity node list or create new instance, and
     * converting the new values from UI model in dynamic way.
     *
     * @param serviceUIModelExtensionUnion
     * @param uiModelValue
     * @return
     * @throws ServiceModuleProxyException
     */
    public List<ServiceEntityNode> genSeNodeListInExtensionUnion(
            ServiceUIModelExtensionUnion serviceUIModelExtensionUnion,
            Class<?> baseNodeType, SEUIComModel uiModelValue)
            throws ServiceModuleProxyException {
        try {
            /**
             * [Step1] Try to find the base seNode by UIModel 'UUID' & 'Client'
             */
            List<UIModelNodeMapConfigure> uiModelNodeMapConfigureList = serviceUIModelExtensionUnion
                    .getUiModelNodeMapList();
            UIModelNodeMapConfigure startNode = ServiceModelExtensionHelper
                    .getFirstNodeMapConfigure(uiModelNodeMapConfigureList);
            ServiceEntityNode baseEntityNode = getEntityNodeByKey(
                    uiModelValue.getUuid(),
                    IServiceEntityNodeFieldConstant.UUID,
                    startNode.getNodeName(), uiModelValue.getClient(), null);
            if (baseEntityNode == null) {
                // New Core seNode instance
                baseEntityNode = (ServiceEntityNode) baseNodeType.getDeclaredConstructor().newInstance();
                if (ServiceEntityNode.NODENAME_ROOT.equals(baseEntityNode
                        .getNodeName())) {
                    baseEntityNode = newRootEntityNode(uiModelValue
                            .getClient());
                }
            }
            Class<?>[] parameterTypes = {uiModelValue.getClass(), baseNodeType};
            if (startNode.getConvUIToMethod() != null) {
                Object logicManager = this;
                if (startNode.getLogicManager() != null) {
                    logicManager = startNode.getLogicManager();
                }
                Method convUIToMethod = logicManager.getClass().getMethod(
                        startNode.getConvUIToMethod(), parameterTypes);
                convUIToMethod.invoke(logicManager, uiModelValue, baseEntityNode);
            }
            List<ServiceEntityNode> resultList = serviceUIModuleProxy
                    .genSeNodeListInExtensionUnion(
                            serviceUIModelExtensionUnion, this, baseEntityNode,
                            null, uiModelValue);
            if (resultList == null) {
                resultList = new ArrayList<>();
            }
            resultList.add(0, baseEntityNode);
            return resultList;
        } catch (NoSuchFieldException | SecurityException
                | IllegalArgumentException | IllegalAccessException
                | NoSuchMethodException | InvocationTargetException
                | InstantiationException | ServiceEntityConfigureException e) {
            throw new ServiceModuleProxyException(
                    ServiceModuleProxyException.PARA_SYSTEM_WRONG,
                    e.getMessage());
        }
    }

    /**
     * Update or insert the service node list.
     *
     * @param seNodeList
     * @param logonUserUUID
     * @param organizationUUID
     * @throws ServiceEntityConfigureException
     */
    public void updateSeNodeListWrapper(List<ServiceEntityNode> seNodeList,
                                        String logonUserUUID, String organizationUUID)
            throws ServiceEntityConfigureException {
        if (ServiceCollectionsHelper.checkNullList(seNodeList)) {
            return;
        }
        for (ServiceEntityNode seNode : seNodeList) {
            ServiceEntityNode backNode = this.getEntityNodeByKey(
                    seNode.getUuid(), IServiceEntityNodeFieldConstant.UUID,
                    seNode.getNodeName(), null);
            if (backNode != null) {
                // In case update model
                this.updateSENode(seNode, backNode, logonUserUUID,
                        organizationUUID);
            } else {
                // In case new model
                this.insertSENode(seNode, logonUserUUID, organizationUUID);
            }
        }
    }

    /**
     * Main entrance to generate Service UI Model instance from service model
     *
     * @param serviceUIModuleType
     * @param serviceModuleType
     * @param serviceModule
     * @throws ServiceModuleProxyException
     * @throws ServiceUIModuleProxyException
     * @throws ServiceEntityConfigureException
     */
    @Deprecated
    public ServiceUIModule genServiceUIModuleFromServiceModel(
            Class<?> serviceUIModuleType, Class<?> serviceModuleType,
            ServiceModule serviceModule) throws ServiceModuleProxyException,
            ServiceUIModuleProxyException, ServiceEntityConfigureException {

        ServiceUIModule serviceUIModule = serviceUIModuleProxy
                .genServiceUIModuleFromServiceModel(serviceUIModuleType,
                        serviceModuleType, this, serviceModule, null,
                        null);
        return serviceUIModule;
    }

    /**
     * Main entrance to generate Service UI Model instance from service model
     *
     * @param serviceUIModuleType
     * @param serviceModuleType
     * @param serviceModule
     * @throws ServiceModuleProxyException
     * @throws ServiceUIModuleProxyException
     * @throws ServiceEntityConfigureException
     */
    @Deprecated
    public ServiceUIModule genServiceUIModuleFromServiceModel(
            Class<?> serviceUIModuleType, Class<?> serviceModuleType,
            ServiceModule serviceModule, LogonInfo logonInfo)
            throws ServiceModuleProxyException, ServiceUIModuleProxyException,
            ServiceEntityConfigureException {

        ServiceUIModule serviceUIModule = serviceUIModuleProxy
                .genServiceUIModuleFromServiceModel(serviceUIModuleType,
                        serviceModuleType, this, serviceModule, logonInfo,
                        null);
        return serviceUIModule;
    }

    /**
     * Core Logic to check if this model is duplicate compared with existed
     * data.
     *
     * @param checkConditions
     * @param rawObjectInstance
     * @param uuid
     * @param nodeName
     * @param client
     * @return
     * @throws ServiceEntityConfigureException
     */
    public String checkDuplicateCore(String[] checkConditions,
                                     Object rawObjectInstance, String uuid, String nodeName,
                                     String client) throws ServiceEntityConfigureException {
        List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
        if (checkConditions != null) {
            for (String fieldName : checkConditions) {
                try {
                    ServiceBasicKeyStructure key = new ServiceBasicKeyStructure();
                    Object keyValue = ServiceEntityFieldsHelper
                            .getObjectFieldValue(rawObjectInstance, fieldName);
                    key.setKeyName(fieldName);
                    key.setKeyValue(keyValue);
                    keyList.add(key);
                } catch (NoSuchFieldException e) {
                    // Do nothing, just continue;
                } catch (SecurityException e) {
                    managerLogger.warn("Security exception during field access, skipping field", e);
                } catch (IllegalArgumentException e) {
                    managerLogger.warn("Illegal argument during field access, skipping field", e);
                } catch (IllegalAccessException e) {
                    managerLogger.warn("Illegal access during field access, skipping field", e);
                }
            }
        }
        List<ServiceEntityNode> rawList = null;
        if (!ServiceEntityStringHelper.checkNullString(client)) {
            rawList = this.getEntityNodeListByKeyList(keyList, nodeName,
                    client, null, true, true);
        } else {
            rawList = this.getEntityNodeListByKeyList(keyList, nodeName, null,
                    true);
        }
        if (!ServiceCollectionsHelper.checkNullList(rawList)) {
            for (ServiceEntityNode seNode : rawList) {
                if (seNode.getUuid().equals(uuid)) {
                    // Ignore the same object
                    continue;
                }
                return ServiceJSONParser
                        .genSimpleErrorCodeResponse(HttpStatus.SC_CONFLICT);
            }
            return ServiceJSONParser
                    .genSimpleErrorCodeResponse(HttpStatus.SC_OK);
        }
        return ServiceJSONParser.genSimpleErrorCodeResponse(HttpStatus.SC_OK);
    }

    /**
     * Main entrance to generate Service Model instance from service UI model
     *
     * @param serviceUIModuleType
     * @param serviceModuleType
     * @param serviceUIModule
     * @throws ServiceModuleProxyException
     * @throws ServiceUIModuleProxyException
     * @throws ServiceEntityConfigureException
     */
    public ServiceModule genServiceModuleFromServiceUIModel(
            Class<?> serviceModuleType, Class<?> serviceUIModuleType,
            ServiceUIModule serviceUIModule,
            ServiceUIModelExtension serviceUIModelExtension)
            throws ServiceModuleProxyException, ServiceUIModuleProxyException,
            ServiceEntityConfigureException {
        if (serviceUIModelExtension == null) {
            return serviceUIModuleProxy.genServiceModuleFromServiceUIModule(
                    serviceModuleType, serviceUIModuleType, this,
                    serviceUIModule, null, null);
        }
        // Check Extension union
        if (ServiceCollectionsHelper.checkNullList(serviceUIModelExtension
                .genUIModelExtensionUnion())) {
            throw new ServiceUIModuleProxyException(
                    ServiceUIModuleProxyException.TYPE_SYSTEM_WRONG);
        }
        // Get the default first extension union.
        ServiceUIModelExtensionUnion serviceUIModelExtensionUnion = serviceUIModelExtension
                .genUIModelExtensionUnion().get(0);
        return serviceUIModuleProxy.genServiceModuleFromServiceUIModule(
                serviceModuleType, serviceUIModuleType, this, serviceUIModule,
                serviceUIModelExtension, serviceUIModelExtensionUnion);
    }

    /**
     * Handler method entry, when source reserved document update, and inform
     * the target document item, Should implement in each sub manager.
     *
     * @param sourceDocType
     * @param sourceDocument
     * @param docMaterialItem
     * @param targetMatItemUUID
     */
    public void reservedDocUpdateToMatItem(int sourceDocType,
                                           DocumentContent sourceDocument, ServiceEntityNode docMaterialItem,
                                           String targetMatItemUUID) {
        // Should implement in each sub manager by different document type
    }

    /**
     * Core Logic for handling after update service model is done
     *
     * @param serviceModule
     * @param logonUserUUID
     * @param organizationUUID
     * @return
     */
    public ServiceModule postUpdateServiceModel(ServiceModule serviceModule,
                                                String logonUserUUID, String organizationUUID)
            throws ServiceEntityConfigureException {
        // Should implement in each sub manager
        return serviceModule;
    }

    /**
     * General interface for conversion from basic seNode to
     * DocumentExtendUIModel instance
     *
     * @param seNode
     * @return
     */
    public ServiceDocumentExtendUIModel convToDocumentExtendUIModel(
            ServiceEntityNode seNode, LogonInfo logonInfo) {
        // Should implement in each sub manager
        return null;
    }

    public ServiceSearchProxy getSearchProxy() {
        // Should implement in each sub manager
        return null;
    }

    public String getAuthorizationResource(){
        // Should implement in each sub manager
        return null;
    }

    public void exeFlowActionEnd(int documentType, String uuid, int actionCode,
                                 ServiceJSONRequest serviceJSONRequest, SerialLogonInfo serialLogonInfo){

    }

}
