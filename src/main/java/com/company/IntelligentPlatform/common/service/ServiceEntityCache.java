package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IComSimExecutor;
import com.company.IntelligentPlatform.common.service.ServiceAsyncExecuteProxy;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceEntityExceptionContainer;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Basic class for service entity application layer cache:
 * Only used for configuration data and master data
 */
public class ServiceEntityCache {

    private final Map<String, List<ServiceEntityNode>> cacheMap = new HashMap<>();

    private ServiceEntityManager serviceEntityManager;

    public ServiceEntityManager getServiceEntityManager() {
        return serviceEntityManager;
    }

    public void setServiceEntityManager(ServiceEntityManager serviceEntityManager) {
        this.serviceEntityManager = serviceEntityManager;
    }

    /**
     * API to get all serviceEntityNode List from cache with update from DB
     *
     * @param nodeName
     * @param client
     * @return
     * @throws ServiceEntityConfigureException
     * @throws ServiceComExecuteException
     */
    public List<ServiceEntityNode> loadAllServiceEntityList(String nodeName, String client, boolean updateFlag)
            throws ServiceEntityConfigureException, ServiceComExecuteException {
        String cacheMapKey = genCacheMapKey(nodeName, client);
        if (cacheMap.containsKey(cacheMapKey)) {
            // trigger load async way and update to cache
            if (updateFlag) {
                updateAllListToCache(nodeName, client);
            }
            return cacheMap.get(cacheMapKey);
        } else {
            List<ServiceEntityNode> allSEList = getAllSEListFromDB(nodeName, client);
            // update to cache
            cacheMap.put(cacheMapKey, allSEList);
            return allSEList;
        }
    }

    /**
     * API to get specified serviceEntityNode List from cache with update from DB
     *
     * @param nodeName
     * @param client
     * @return
     * @throws ServiceEntityConfigureException
     * @throws ServiceComExecuteException
     */
    public List<ServiceEntityNode> loadServiceEntityList(Function<ServiceEntityNode, Boolean> filterCallback,
                                                         String nodeName, String client, boolean updateFlag)
            throws ServiceEntityConfigureException, ServiceComExecuteException {
        return loadServiceEntityListCore(filterCallback, false, nodeName, client, updateFlag);
    }

    /**
     * API to get serviceEntityNode instance from Cache with update from DB
     *
     * @param nodeName
     * @param client
     * @return
     * @throws ServiceEntityConfigureException
     * @throws ServiceComExecuteException
     */
    public ServiceEntityNode loadServiceEntity(Function<ServiceEntityNode, Boolean> filterCallback, String nodeName,
                                               String client, boolean updateFlag)
            throws ServiceEntityConfigureException, ServiceComExecuteException {
        List<ServiceEntityNode> tempList = loadServiceEntityListCore(filterCallback, true, nodeName, client, updateFlag);
        if (ServiceCollectionsHelper.checkNullList(tempList)) {
            return null;
        } else {
            return tempList.get(0);
        }
    }

    /**
     * API to get serviceEntityNode instance from Cache with update from DB
     *
     * @param nodeName
     * @param client
     * @return
     * @throws ServiceEntityConfigureException
     * @throws ServiceComExecuteException
     */
    public ServiceEntityNode loadServiceEntity(String uuid, String nodeName, String client, boolean updateFlag)
            throws ServiceEntityConfigureException, ServiceComExecuteException {
        List<ServiceEntityNode> allSEList = this.loadAllServiceEntityList(nodeName, client, updateFlag);
        if (ServiceCollectionsHelper.checkNullList(allSEList)) {
            return null;
        }
        List<ServiceEntityNode> tempList = ServiceCollectionsHelper.filterListOnline(allSEList, serviceEntityNode -> {
            return serviceEntityNode.getUuid().equals(uuid);
        }, true);
        if (ServiceCollectionsHelper.checkNullList(tempList)) {
            // in case didn't hit in cache
            return this.getServiceEntityManager()
                    .getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID, nodeName, client, null);
        }
        return tempList.get(0);
    }


    /**
     * [Internal method] Core API to load service entity list, if can't hit in cache, then retrieve from DB
     *
     * @param filterCallback
     * @param fastSkip
     * @param nodeName
     * @param client
     * @return
     * @throws ServiceEntityConfigureException
     * @throws ServiceComExecuteException
     */
    private List<ServiceEntityNode> loadServiceEntityListCore(Function<ServiceEntityNode, Boolean> filterCallback,
                                                              boolean fastSkip, String nodeName, String client,
                                                              boolean updateFlag)
            throws ServiceEntityConfigureException, ServiceComExecuteException {
        List<ServiceEntityNode> allSEList = this.loadAllServiceEntityList(nodeName, client, updateFlag);
        if (ServiceCollectionsHelper.checkNullList(allSEList)) {
            return null;
        }
        List<ServiceEntityNode> resultSEList =
                ServiceCollectionsHelper.filterListOnline(allSEList, filterCallback, false);
        if (!ServiceCollectionsHelper.checkNullList(resultSEList)) {
            // in case hit result
            return resultSEList;
        } else {
            // in case didn't hit, just get from DB
            allSEList = getAllSEListFromDB(nodeName, client);
            updateAllListToCache(nodeName, client);
            return ServiceCollectionsHelper.filterListOnline(allSEList, filterCallback, fastSkip);
        }
    }


    /**
     * API to update serviceEntityNode into DB and cache
     *
     * @param serviceEntityNode
     * @param logonUserUUID
     * @param organizationUUID
     */
    public void updateServiceEntityNode(ServiceEntityNode serviceEntityNode, String logonUserUUID,
                                        String organizationUUID) throws ServiceComExecuteException {
        // update to DB
        this.getServiceEntityManager().updateSENode(serviceEntityNode, logonUserUUID, organizationUUID);
        // update to cache
        updateServiceEntityNodeToCache(serviceEntityNode);
    }

    private void updateAllListToCache(String nodeName, String client) throws ServiceComExecuteException {
        try {
            ServiceAsyncExecuteProxy.executeAsyncWrapper(null,
                    (IComSimExecutor<ServiceEntityNode, List<ServiceEntityNode>>) (seNode, logonInfo) -> {
                        try {
                            String cacheMapKey = genCacheMapKey(nodeName, client);
                            try {
                                List<ServiceEntityNode> seListDB = getAllSEListFromDB(nodeName, client);
                                if (seNode != null) {
                                    ServiceCollectionsHelper.mergeToList(seListDB, seNode);
                                }
                                cacheMap.put(cacheMapKey, seListDB);
                                return seListDB;
                            } catch (ServiceEntityConfigureException e) {
                                throw new ServiceComExecuteException(ServiceComExecuteException.PARA_SYSTEM_ERROR,
                                        e.getMessage());
                            }
                        } catch (ServiceComExecuteException e) {
                            throw new ServiceEntityExceptionContainer(e);
                        }
                    }, null);
        } catch (ServiceEntityExceptionContainer serviceEntityExceptionContainer) {
            ServiceEntityException coreException = serviceEntityExceptionContainer.getCoreException();
            if (coreException != null) {
                if (coreException instanceof ServiceComExecuteException) {
                    throw (ServiceComExecuteException) coreException;
                }
                throw new ServiceComExecuteException(ServiceComExecuteException.PARA_SYSTEM_ERROR,
                        coreException.getErrorMessage());
            } else {
                throw new ServiceComExecuteException(ServiceComExecuteException.TYPE_SYSTEM_WRONG);
            }
        }
    }

    public void updateServiceEntityNodeToCache(ServiceEntityNode serviceEntityNode) throws ServiceComExecuteException {
        try {
            ServiceAsyncExecuteProxy.executeAsyncWrapper(serviceEntityNode,
                    (seNode, logonInfo) -> {
                        try {
                            String nodeName = seNode.getNodeName();
                            String client = seNode.getClient();
                            String cacheMapKey = genCacheMapKey(nodeName, client);
                            if (cacheMap.containsKey(cacheMapKey)) {
                                // In case update cache directly
                                List<ServiceEntityNode> seListCache = cacheMap.get(cacheMapKey);
                                ServiceCollectionsHelper.mergeToList(seListCache, seNode);
                            } else {
                                // trying to load all SE list from DB and set to cache
                                try {
                                    List<ServiceEntityNode> seListDB =
                                            getAllSEListFromDB(seNode.getNodeName(), logonInfo.getClient());
                                    ServiceCollectionsHelper.mergeToList(seListDB, seNode);
                                    cacheMap.put(cacheMapKey, seListDB);
                                } catch (ServiceEntityConfigureException e) {
                                    throw new ServiceComExecuteException(ServiceComExecuteException.PARA_SYSTEM_ERROR,
                                            e.getMessage());
                                }
                            }
                            return seNode;
                        } catch (ServiceComExecuteException e) {
                            throw new ServiceEntityExceptionContainer(e);
                        }
                    }, null);
        } catch (ServiceEntityExceptionContainer serviceEntityExceptionContainer) {
            ServiceEntityException coreException = serviceEntityExceptionContainer.getCoreException();
            if (coreException != null) {
                if (coreException instanceof ServiceComExecuteException) {
                    throw (ServiceComExecuteException) coreException;
                }
                throw new ServiceComExecuteException(ServiceComExecuteException.PARA_SYSTEM_ERROR,
                        coreException.getErrorMessage());
            } else {
                throw new ServiceComExecuteException(ServiceComExecuteException.TYPE_SYSTEM_WRONG);
            }
        }
    }

    /**
     * Get All Data from DB
     *
     * @param nodeName
     * @param client
     * @return
     * @throws ServiceEntityConfigureException
     */
    protected List<ServiceEntityNode> getAllSEListFromDB(String nodeName, String client)
            throws ServiceEntityConfigureException {
        return this.getServiceEntityManager().getEntityNodeListByKeyList(null, nodeName, client, null);
    }

    protected static String genCacheMapKey(String nodeName, String client) {
        return client + "," + nodeName;
    }
}
