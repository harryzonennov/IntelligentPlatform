package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.util.ArrayList;;
import java.util.List;;
import java.util.Map;
import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.controller.ServiceFlowModelSearchModel;
import com.company.IntelligentPlatform.common.controller.ServiceFlowModelUIModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.ServiceFlowModelRepository;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModelRepository;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
@Transactional
public class ServiceFlowModelManager extends ServiceEntityManager {

    public static final String METHOD_ConvServiceFlowModelToUI = "convServiceFlowModelToUI";

    public static final String METHOD_ConvUIToServiceFlowModel = "convUIToServiceFlowModel";

    public static final String METHOD_ConvFlowRouterToUI = "convFlowRouterToUI";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected ServiceFlowModelRepository serviceFlowModelDAO;

    @Autowired
    protected ServiceFlowCondGroupManager serviceFlowCondGroupManager;

    @Autowired
    protected ServiceFlowModelConfigureProxy serviceFlowModelConfigureProxy;

    @Autowired
    protected SystemDefDocActionCodeProxy systemDefDocActionCodeProxy;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected ServiceUIModelRepository serviceUIModelRepository;

    @Autowired
    protected ServiceFlowModelSearchProxy serviceFlowModelSearchProxy;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Get Defined Service Flow Model List by document type, action code
     * @param docType
     * @param actionCode
     * @param client
     * @return
     */
    public List<ServiceFlowModelServiceModel> loadServiceFlowModelList(int docType, int actionCode, String client) throws ServiceEntityConfigureException, ServiceModuleProxyException {
        ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure(docType, "documentType");
        ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure(actionCode, "actionCode");
        List<ServiceEntityNode> rawSEList = this.getEntityNodeListByKeyList(ServiceCollectionsHelper.asList(key1,
                key2), ServiceFlowModel.NODENAME, client, null);
        if(ServiceCollectionsHelper.checkNullList(rawSEList)){
            return null;
        }
        List<ServiceFlowModelServiceModel> resultList = new ArrayList<>();
        for(ServiceEntityNode serviceEntityNode: rawSEList){
            ServiceFlowModelServiceModel serviceFlowModelServiceModel =
                    (ServiceFlowModelServiceModel) this.loadServiceModule(ServiceFlowModelServiceModel.class, serviceEntityNode);
            resultList.add(serviceFlowModelServiceModel);
        }
        return resultList;
    }

    /**
     * Wrapper method and logic to create new PurchaseContract
     *
     * @param logonInfo
     * @return
     * @throws ServiceEntityConfigureException
     */
    public ServiceFlowModelServiceModel initServiceFlowModel(LogonInfo logonInfo) throws ServiceEntityConfigureException, ServiceFlowException {
        ServiceFlowModel serviceFlowModel =
                (ServiceFlowModel) newRootEntityNode(logonInfo.getClient());
        ServiceFlowModelServiceModel serviceFlowModelServiceModel = new ServiceFlowModelServiceModel();
        serviceFlowModelServiceModel.setServiceFlowModel(serviceFlowModel);
        ServiceFlowCondGroupServiceModel defaultFlowCondGroupServiceModel =
                serviceFlowCondGroupManager.initServiceFlowCondGroup(serviceFlowModel);
        // set default id & name
        ServiceFlowCondGroup defFlowCondGroup = defaultFlowCondGroupServiceModel.getServiceFlowCondGroup();
        defFlowCondGroup.setId(ServiceFlowCondGroup.DEF_ID);
        try {
            defFlowCondGroup.setName(serviceFlowCondGroupManager.getDefGroupMetaValue(logonInfo.getLanguageCode(),
                    IServiceEntityNodeFieldConstant.NAME));
            defFlowCondGroup.setNote(serviceFlowCondGroupManager.getDefGroupMetaValue(logonInfo.getLanguageCode(),
                    IServiceEntityNodeFieldConstant.NOTE));
        } catch (IOException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "defaultId"));
            throw new ServiceFlowException(ServiceFlowException.PARA_SYSTEM_ERROR, e.getMessage());
        }
        List<ServiceFlowCondGroupServiceModel> serviceFlowCondGroupList =
                ServiceCollectionsHelper.asList(defaultFlowCondGroupServiceModel);
        serviceFlowModelServiceModel.setServiceFlowCondGroupList(serviceFlowCondGroupList);
        return serviceFlowModelServiceModel;
    }

    public void convServiceFlowModelToUI(ServiceFlowModel serviceFlowModel,
                                         ServiceFlowModelUIModel serviceFlowModelUIModel) {
        convServiceFlowModelToUI(serviceFlowModel, serviceFlowModelUIModel, null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convServiceFlowModelToUI(ServiceFlowModel serviceFlowModel,
                                         ServiceFlowModelUIModel serviceFlowModelUIModel, LogonInfo logonInfo) {
        if (serviceFlowModel != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(serviceFlowModel, serviceFlowModelUIModel);
            serviceFlowModelUIModel.setActionCode(serviceFlowModel.getActionCode());
            if (logonInfo != null) {
                try {
                    Map<Integer, String> actionCodeMap = this.initActionCodeMap(logonInfo.getLanguageCode());
                    serviceFlowModelUIModel.setActionCodeValue(actionCodeMap.get(serviceFlowModel.getActionCode()));
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "actionCode"));
                }
            }
            serviceFlowModelUIModel.setRefRouterUUID(serviceFlowModel.getRefRouterUUID());
            serviceFlowModelUIModel.setServiceUIModelId(serviceFlowModel.getServiceUIModelId());
            serviceFlowModelUIModel.setResOrgUUID(serviceFlowModel.getResOrgUUID());
            serviceFlowModelUIModel.setResEmployeeUUID(serviceFlowModel.getResEmployeeUUID());
            serviceFlowModelUIModel.setLastUpdateBy(serviceFlowModel.getLastUpdateBy());
            serviceFlowModelUIModel.setDocumentType(serviceFlowModel.getDocumentType());
            if (logonInfo != null) {
                try {
                    Map<Integer, String> documentTypeMap = this.initDocumentTypeMap(logonInfo.getLanguageCode());
                    serviceFlowModelUIModel.setDocumentTypeValue(documentTypeMap.get(serviceFlowModel.getDocumentType()));
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "documentType"));
                }
            }
        }
    }

    /**
     * [Internal method] Convert from UI model to se model:serviceFlowModel
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToServiceFlowModel(ServiceFlowModelUIModel serviceFlowModelUIModel, ServiceFlowModel rawEntity) {
        DocFlowProxy.convUIToServiceEntityNode(serviceFlowModelUIModel, rawEntity);
        rawEntity.setActionCode(serviceFlowModelUIModel.getActionCode());
        rawEntity.setRefRouterUUID(serviceFlowModelUIModel.getRefRouterUUID());
        rawEntity.setServiceUIModelId(serviceFlowModelUIModel.getServiceUIModelId());
        rawEntity.setResOrgUUID(serviceFlowModelUIModel.getResOrgUUID());
        rawEntity.setResEmployeeUUID(serviceFlowModelUIModel.getResEmployeeUUID());
        rawEntity.setDocumentType(serviceFlowModelUIModel.getDocumentType());
    }

    public Map<Integer, String> initActionCodeMap(String languageCode) throws ServiceEntityInstallationException {
        return systemDefDocActionCodeProxy.getActionCodeMap(languageCode);
    }

    public Map<String, String> initServiceUIModelIdMap(String languageCode) throws ServiceEntityInstallationException {
        return serviceUIModelRepository.loadServiceUIModelMap(languageCode);
    }

    public Map<Integer, String> initDocumentTypeMap(String languageCode) throws ServiceEntityInstallationException {
        return serviceUIModelRepository.getAccessDocumentType(languageCode);
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, serviceFlowModelDAO));
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(serviceFlowModelConfigureProxy);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convFlowRouterToUI(FlowRouter flowRouter, ServiceFlowModelUIModel serviceFlowModelUIModel) {
        if (flowRouter != null) {
            if (!ServiceEntityStringHelper.checkNullString(flowRouter.getClient())) {
                serviceFlowModelUIModel.setClient(flowRouter.getClient());
            }
            serviceFlowModelUIModel.setRefRouterName(flowRouter.getName());
            serviceFlowModelUIModel.setRefRouterId(flowRouter.getId());
        }
    }

    @Override
    public ServiceSearchProxy getSearchProxy() {
        return serviceFlowModelSearchProxy;
    }
}
