package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// TODO-LEGACY: import platform.foundation.Administration.InstallService.ServicePackageProxy;
import com.company.IntelligentPlatform.common.dto.ServiceEntityRegisterEntitySearchModel;
import com.company.IntelligentPlatform.common.dto.ServiceEntityRegisterEntityUIModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.ServiceEntityRegisterEntityRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityRegisterEntityConfigureProxy;
import com.company.IntelligentPlatform.common.model.ServiceEntityRegisterEntity;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
@Transactional
public class ServiceEntityRegisterEntityManager extends ServiceEntityManager {

    public static final String METHOD_ConvServiceEntityRegisterEntityToUI = "convServiceEntityRegisterEntityToUI";

    public static final String METHOD_ConvUIToServiceEntityRegisterEntity = "convUIToServiceEntityRegisterEntity";
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected ServiceEntityRegisterEntityRepository serviceEntityRegisterEntityDAO;
    @Autowired
    ServiceEntityRegisterEntityConfigureProxy serviceEntityRegisterEntityConfigureProxy;

    // TODO-LEGACY: @Autowired

    // TODO-LEGACY:     protected ServicePackageProxy servicePackageProxy;

    @Autowired
    protected BsearchService bsearchService;

    public ServiceEntityRegisterEntityManager() {

    }

    @PostConstruct
    public void setServiceEntityDAO() {
        super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, serviceEntityRegisterEntityDAO));
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(serviceEntityRegisterEntityConfigureProxy);
    }

    /**
     * Logic to check if class Type existed in project
     *
     * @param classType
     * @throws ClassNotFoundException
     */
    public void checkClassTypeExist(String classType) throws ClassNotFoundException {
        Class.forName(classType);
    }

    /**
     * Get ServiceEntityConfigureProxy instance reflectively by SE name from get
     * data configuration from ServiceEntityRegisterEntity
     *
     * @param seName
     * @return
     * @throws ServiceEntityConfigureException
     * @throws ServiceEntityRegisterException
     */
    public ServiceEntityConfigureProxy getServiceConfigureReflective(
            String seName) throws ServiceEntityConfigureException,
            ServiceEntityRegisterException {
        ServiceEntityRegisterEntity seRegEntity = (ServiceEntityRegisterEntity) getEntityNodeByKey(
                seName, IServiceEntityNodeFieldConstant.ID,
                ServiceEntityNode.NODENAME_ROOT, null, true);
        if (seRegEntity == null) {
            throw new ServiceEntityRegisterException(
                    ServiceEntityRegisterException.PARA_NO_SE_REGISTERED,
                    seName);
        }
        try {
            Class<?> proxyClassName = Class.forName(seRegEntity
                    .getSeProxyType());
            ServiceEntityConfigureProxy seConfigProxy = (ServiceEntityConfigureProxy) proxyClassName
                    .newInstance();
            return seConfigProxy;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new ServiceEntityRegisterException(
                    ServiceEntityRegisterException.PARA_WRG_SEPROXYCLS,
                    seRegEntity.getSeProxyType());
        }
    }

    /**
     * Get ServiceEntityConfigureProxy instance reflectively by SE name from get
     * data configuratiion from ServiceEntityRegisterEntity
     *
     * @param seName
     * @return
     * @throws ServiceEntityConfigureException
     * @throws ServiceEntityRegisterException
     */
    public ServiceEntityConfigureProxy getServiceConfigureReflective(
            String seName, ApplicationContext context)
            throws ServiceEntityConfigureException,
            ServiceEntityRegisterException {
        ServiceEntityRegisterEntity seRegEntity = (ServiceEntityRegisterEntity) getEntityNodeByKey(
                seName, IServiceEntityNodeFieldConstant.ID,
                ServiceEntityNode.NODENAME_ROOT, null, true);
        if (seRegEntity == null) {
            throw new ServiceEntityRegisterException(
                    ServiceEntityRegisterException.PARA_NO_SE_REGISTERED,
                    seName);
        }
        try {
            String seProxyId = ServiceEntityStringHelper
                    .headerToLowerCase(seRegEntity.getSeProxyName());
            Object rawSeProxy = context.getBean(seProxyId);
            if (rawSeProxy == null) {
                throw new ClassNotFoundException();
            }
            ServiceEntityConfigureProxy seConfigProxy = (ServiceEntityConfigureProxy) rawSeProxy;
            return seConfigProxy;
        } catch (ClassNotFoundException e) {
            throw new ServiceEntityRegisterException(
                    ServiceEntityRegisterException.PARA_WRG_SEPROXYCLS,
                    seRegEntity.getSeProxyType());
        }
    }

    public ServiceEntityConfigureMap getNodeConfigureMap(String seName,
                                                         String nodeName) throws ServiceEntityRegisterException,
            ServiceEntityConfigureException {
        ServiceEntityConfigureProxy seConfigureProxy = getServiceConfigureReflective(seName);
        List<ServiceEntityConfigureMap> seConfigMapList = seConfigureProxy
                .getSeConfigMapList();
        if (!ServiceCollectionsHelper.checkNullList(seConfigMapList)) {
            for (ServiceEntityConfigureMap seConfigureMap : seConfigMapList) {
                if (ServiceEntityStringHelper.checkNullString(nodeName)) {
                    if (ServiceEntityNode.NODENAME_ROOT.equals(seConfigureMap
                            .getNodeName())) {
                        return seConfigureMap;
                    }
                }
                if (nodeName.equals(seConfigureMap.getNodeName())) {
                    return seConfigureMap;
                }
            }
        }
        return null;
    }

    public List<String> getNodeNameListReflective(String seName)
            throws ServiceEntityRegisterException,
            ServiceEntityConfigureException {
        List<String> result = new ArrayList<String>();
        ServiceEntityConfigureProxy seConfigureProxy = getServiceConfigureReflective(seName);
        List<ServiceEntityConfigureMap> seConfigMapList = seConfigureProxy
                .getSeConfigMapList();
        if (!ServiceCollectionsHelper.checkNullList(seConfigMapList)) {
            for (ServiceEntityConfigureMap seConfigureMap : seConfigMapList) {
                result.add(seConfigureMap.getNodeName());
            }
        }
        return result;

    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convServiceEntityRegisterEntityToUI(
            ServiceEntityRegisterEntity serviceEntityRegisterEntity,
            ServiceEntityRegisterEntityUIModel serviceEntityRegisterEntityUIModel) {
        if (serviceEntityRegisterEntity != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(serviceEntityRegisterEntity, serviceEntityRegisterEntityUIModel);
            serviceEntityRegisterEntityUIModel
                    .setSeModuleType(serviceEntityRegisterEntity
                            .getSeModuleType());
            serviceEntityRegisterEntityUIModel
                    .setSeModuleName(serviceEntityRegisterEntity
                            .getSeModuleName());
            serviceEntityRegisterEntityUIModel
                    .setSeProxyType(serviceEntityRegisterEntity
                            .getSeProxyType());
            serviceEntityRegisterEntityUIModel
                    .setSeManagerName(serviceEntityRegisterEntity
                            .getSeManagerName());
            serviceEntityRegisterEntityUIModel
                    .setSeManagerType(serviceEntityRegisterEntity
                            .getSeManagerType());
            serviceEntityRegisterEntityUIModel
                    .setSeDAOName(serviceEntityRegisterEntity.getSeDAOName());
            serviceEntityRegisterEntityUIModel
                    .setPackageName(serviceEntityRegisterEntity
                            .getPackageName());
            serviceEntityRegisterEntityUIModel
                    .setSeDAOType(serviceEntityRegisterEntity.getSeDAOType());
            serviceEntityRegisterEntityUIModel
                    .setSeProxyName(serviceEntityRegisterEntity
                            .getSeProxyName());
        }
    }

    /**
     * [Internal method] Convert from UI model to se
     * model:serviceEntityRegisterEntity
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToServiceEntityRegisterEntity(
            ServiceEntityRegisterEntityUIModel serviceEntityRegisterEntityUIModel,
            ServiceEntityRegisterEntity rawEntity) {
        DocFlowProxy.convUIToServiceEntityNode(serviceEntityRegisterEntityUIModel, rawEntity);
        rawEntity.setSeModuleType(serviceEntityRegisterEntityUIModel
                .getSeModuleType());
        rawEntity.setSeModuleName(serviceEntityRegisterEntityUIModel
                .getSeModuleName());
        rawEntity.setSeProxyType(serviceEntityRegisterEntityUIModel
                .getSeProxyType());
        rawEntity.setSeManagerName(serviceEntityRegisterEntityUIModel
                .getSeManagerName());
        rawEntity.setSeManagerType(serviceEntityRegisterEntityUIModel
                .getSeManagerType());
        rawEntity.setSeDAOName(serviceEntityRegisterEntityUIModel
                .getSeDAOName());
        rawEntity.setPackageName(serviceEntityRegisterEntityUIModel
                .getPackageName());
        rawEntity.setSeDAOType(serviceEntityRegisterEntityUIModel
                .getSeDAOType());
        rawEntity.setSeProxyName(serviceEntityRegisterEntityUIModel
                .getSeProxyName());
    }

    public List<ServiceEntityNode> searchInternal(
            ServiceEntityRegisterEntitySearchModel searchModel)
            throws SearchConfigureException, ServiceEntityConfigureException,
            NodeNotFoundException, ServiceEntityInstallationException {
        List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
        // Search node:[serviceEntityRegisterEntity]
        BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
        searchNodeConfig0.setSeName(ServiceEntityRegisterEntity.SENAME);
        searchNodeConfig0.setNodeName(ServiceEntityRegisterEntity.NODENAME);
        searchNodeConfig0.setNodeInstID(ServiceEntityRegisterEntity.SENAME);
        searchNodeConfig0.setStartNodeFlag(true);
        searchNodeConfigList.add(searchNodeConfig0);
        List<ServiceEntityNode> resultList = bsearchService.doSearch(
                searchModel, searchNodeConfigList, null, true);
        return resultList;
    }

}