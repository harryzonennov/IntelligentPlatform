package com.company.IntelligentPlatform.common.service;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.company.IntelligentPlatform.common.controller.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.FlowRouterRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;

import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.SystemSerialParallelProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Logic Manager CLASS FOR Service Entity [FlowRouter]
 *
 * @author
 * @date Tue May 11 00:42:59 CST 2021
 * <p>
 * This class is generated automatically by platform automation register tool
 */
@Service
@Transactional
public class FlowRouterManager extends ServiceEntityManager {

    public static final String METHOD_ConvFlowRouterToUI = "convFlowRouterToUI";

    public static final String METHOD_ConvUIToFlowRouter = "convUIToFlowRouter";
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected FlowRouterRepository flowRouterDAO;
    @Autowired
    protected FlowRouterConfigureProxy flowRouterConfigureProxy;

    @Autowired
    protected SystemSerialParallelProxy systemSerialParallelProxy;

    @Autowired
    protected FlowRouterServiceUIModelExtension flowRouterServiceUIModelExtension;

    @Autowired
    protected FlowRouterExtendClassManager flowRouterExtendClassManager;

    @Autowired
    protected FlowRouterSearchProxy flowRouterSearchProxy;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public void postLoadServiceUIModel(FlowRouterServiceUIModel flowRouterServiceUIModel, SerialLogonInfo serialLogonInfo){
        List<FlowRouterExtendClassUIModel> flowRouterExtendClassUIModelList =
                flowRouterServiceUIModel.getFlowRouterExtendClassUIModelList().stream().
                        map(FlowRouterExtendClassServiceUIModel::getFlowRouterExtendClassUIModel).collect(Collectors.toList());
        if(!ServiceCollectionsHelper.checkNullList(flowRouterExtendClassUIModelList)){
            for(FlowRouterExtendClassUIModel flowRouterExtendClassUIModel: flowRouterExtendClassUIModelList){
                flowRouterExtendClassManager.postLoadUIModel(flowRouterExtendClassUIModel, serialLogonInfo);
            }
            flowRouterServiceUIModel.getFlowRouterExtendClassUIModelList().stream().map(FlowRouterExtendClassServiceUIModel::getFlowRouterExtendClassUIModel).
                    collect(Collectors.toList()).sort((o1, o2) -> {
                Integer processIndex1 = o1.getProcessIndex();
                Integer processIndex2 = o2.getProcessIndex();
                return processIndex1.compareTo(processIndex2);
            });
        }
    }

    /**
     * Logic to calculate target logon user list from Flow Router
     * @param flowRouter
     * @param serialLogonInfo
     * @return
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     */
    public List<String> calulateTargetUserUUIDList(FlowRouter flowRouter, SerialLogonInfo serialLogonInfo) throws ServiceEntityConfigureException,
            ServiceModuleProxyException, ServiceFlowException {
        FlowRouterServiceModel flowRouterServiceModel = (FlowRouterServiceModel) this.loadServiceModule(FlowRouterServiceModel.class,
                flowRouter, flowRouterServiceUIModelExtension);
        List<ServiceEntityNode> flowRouterExtendClassList = flowRouterServiceModel.getFlowRouterExtendClassList().stream().
                map(FlowRouterExtendClassServiceModel::getFlowRouterExtendClass).collect(Collectors.toList());
        List<String> userUUIDList = new ArrayList<>();
        if(ServiceCollectionsHelper.checkNullList(flowRouterExtendClassList)){
            throw new ServiceFlowException(ServiceFlowException.PARA_NO_TARGET_USER, flowRouter.getId());
        }
        for(ServiceEntityNode serviceEntityNode: flowRouterExtendClassList){
            FlowRouterExtendClass flowRouterExtendClass = (FlowRouterExtendClass) serviceEntityNode;
            String userUUID = flowRouterExtendClassManager.calculateTargetUserUUID(flowRouter, flowRouterExtendClass,
                    serialLogonInfo);
            if(!ServiceEntityStringHelper.checkNullString(userUUID)){
                ServiceCollectionsHelper.mergeToList(userUUIDList, userUUID);
            }
        }
        return userUUIDList;
    }

    public void convFlowRouterToUI(FlowRouter flowRouter, FlowRouterUIModel flowRouterUIModel)
            throws ServiceEntityInstallationException {
        convFlowRouterToUI(flowRouter, flowRouterUIModel, null);
    }

    /**
     *[Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convFlowRouterToUI(FlowRouter flowRouter, FlowRouterUIModel flowRouterUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        if(flowRouter!= null){
            DocFlowProxy.convServiceEntityNodeToUIModel(flowRouter, flowRouterUIModel);
            flowRouterUIModel.setSerialFlag(flowRouter.getSerialFlag());
            if(logonInfo != null){
                Map<Integer, String> serialParallelMap = initSerialParallelMap(logonInfo.getLanguageCode());
                flowRouterUIModel.setSerialFlagValue(serialParallelMap.get(flowRouter.getSerialFlag()));
            }
            flowRouterUIModel.setId(flowRouter.getId( ));
            flowRouterUIModel.setName(flowRouter.getName( ));
            flowRouterUIModel.setNote(flowRouter.getNote( ));
        }
    }

    /**
     *[Internal method] Convert from UI model to se model:flowRouter
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToFlowRouter(FlowRouterUIModel flowRouterUIModel,FlowRouter rawEntity ) {
        DocFlowProxy.convUIToServiceEntityNode(flowRouterUIModel, rawEntity);
        rawEntity.setSerialFlag(flowRouterUIModel.getSerialFlag());
        rawEntity.setRootNodeUUID(flowRouterUIModel.getRootNodeUUID( ));
        rawEntity.setParentNodeUUID(flowRouterUIModel.getParentNodeUUID( ));
    }

    @Override
    public ServiceSearchProxy getSearchProxy() {
        return flowRouterSearchProxy;
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, flowRouterDAO));
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(flowRouterConfigureProxy);
    }

    public Map<Integer, String> initSerialParallelMap(String languageCode) throws ServiceEntityInstallationException {
        return systemSerialParallelProxy.getSerialParallelMap(languageCode);
    }

}
