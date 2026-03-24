package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.dto.ServiceDocDeletionSettingSearchModel;
import com.company.IntelligentPlatform.common.dto.ServiceDocDeletionSettingServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.ServiceDocDeletionSettingUIModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.DocActionExecutionProxyFactory;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchContextBuilder;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceDocDeletionSetting;
import com.company.IntelligentPlatform.common.model.ServiceDocumentSetting;

import java.util.*;

@Service
public class ServiceDocDeletionSettingManager {

    public static final String METHOD_ConvServiceDocDeletionSettingToUI = "convServiceDocDeletionSettingToUI";

    public static final String METHOD_ConvUIToServiceDocDeletionSetting = "convUIToServiceDocDeletionSetting";
    
    @Autowired
    protected ServiceDocumentSettingManager serviceDocumentSettingManager;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected StandardSwitchProxy standardSwitchProxy;

    @Autowired
    protected DocActionExecutionProxyFactory docActionExecutionProxyFactory;

    @Autowired
    protected StandardSystemVariableProxy standardSystemVariableProxy;

    @Autowired
    protected ServiceDocDeletionSettingSearchProxy serviceDocDeletionSettingSearchProxy;

    protected Map<String, Map<Integer, String>> deletionStrategyMapLan;

    @Autowired
    protected ServiceDocDeletionSettingServiceUIModelExtension serviceDocDeletionSettingServiceUIModelExtension;

    protected Logger logger = LoggerFactory.getLogger(ServiceDocDeletionSettingManager.class);

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), ServiceDocumentSetting.NODENAME,
                        request.getUuid(), ServiceDocDeletionSetting.NODENAME, serviceDocumentSettingManager);
        docPageHeaderInputPara.setGenBaseModelList(
                (DocPageHeaderModelProxy.GenBaseModelList<ServiceDocumentSetting>) serviceDocumentSetting -> {
                    // How to get the base page header model list
                    List<PageHeaderModel> pageHeaderModelList =
                            docPageHeaderModelProxy.getDocPageHeaderModelList(serviceDocumentSetting,
                                    IServiceEntityNodeFieldConstant.NAME);
                    return pageHeaderModelList;
                });
        docPageHeaderInputPara.setGenHomePageModel(
                (DocPageHeaderModelProxy.GenHomePageModel<ServiceDocDeletionSetting>) (serviceDocDeletionSetting, pageHeaderModel) -> {
                    // How to render current page header
                    pageHeaderModel.setHeaderName(serviceDocDeletionSetting.getUuid());
                    return pageHeaderModel;
                });
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, serialLogonInfo.getClient());
    }

    /**
     * API to get the service entity initial process configuration.
     * @param serviceEntityName
     * @param nodeName
     * @return
     */
    public ServiceDocDeletionSetting getServiceDocDeletionSetting(String serviceEntityName, String nodeName,
                                                            String deletionSettingId, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, ServiceEntityInstallationException,
            SearchConfigureException, LogonInfoException, AuthorizationException {
        String defNodeInstId = ServiceEntityStringHelper.getDefModelId(serviceEntityName, nodeName);
        ServiceDocDeletionSettingSearchModel serviceDocDeletionSettingSearchModel = new ServiceDocDeletionSettingSearchModel();
        serviceDocDeletionSettingSearchModel.setRefServiceEntityName(serviceEntityName);
        serviceDocDeletionSettingSearchModel.setRefNodeName(nodeName);
        serviceDocDeletionSettingSearchModel.setDeleteSettingId(deletionSettingId);
        SearchContextBuilder searchContextBuilder = SearchContextBuilder.genBuilder(logonInfo).searchModel(serviceDocDeletionSettingSearchModel);
        List<ServiceEntityNode> serviceDocDeletionSettingList =
                serviceDocDeletionSettingSearchProxy.searchDocList(searchContextBuilder.build()).getResultList();
        if (ServiceCollectionsHelper.checkNullList(serviceDocDeletionSettingList)) {
            return null;
        }
        ServiceDocDeletionSetting serviceDocDeletionSetting = null;
        if (ServiceEntityStringHelper.checkNullString(deletionSettingId) ||
                deletionSettingId.equals(defNodeInstId)) {
            // In case using the default init configure template
            serviceDocDeletionSetting =
                    (ServiceDocDeletionSetting) ServiceCollectionsHelper.filterOnline(serviceDocDeletionSettingList, serviceEntityNode->
                            ServiceEntityStringHelper.checkNullString(serviceEntityNode.getId()) || defNodeInstId.equals(serviceEntityNode.getId()));
        } else {
            serviceDocDeletionSetting = (ServiceDocDeletionSetting) serviceDocDeletionSettingList.get(0);
        }
        if (serviceDocDeletionSetting == null) {
            return null;
        }
        return serviceDocDeletionSetting;
    }

    public static List<Integer> parseToAdmDeleteStatus(String admDeleteStatusStr) throws DocActionException {
        List<String> strStatusList = ServiceEntityStringHelper.convStringToStrList(admDeleteStatusStr);
        List<Integer> admDeleteStatusList = new ArrayList<>();
        ServiceCollectionsHelper.traverseListInterrupt(strStatusList, item -> {
            admDeleteStatusList.add(Integer.parseInt(item));
            return true;
        });
        return admDeleteStatusList;
    }


    public Map<Integer, String> initDeletionStrategy(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.deletionStrategyMapLan, ServiceDocDeletionSettingUIModel.class,
                "deletionStrategy");
    }

    public void convServiceDocDeletionSettingToUI(
            ServiceDocDeletionSetting serviceDocDeletionSetting,
            ServiceDocDeletionSettingUIModel serviceDocDeletionSettingUIModel,
            LogonInfo logonInfo) throws ServiceEntityInstallationException {
        if (serviceDocDeletionSetting != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(serviceDocDeletionSetting, serviceDocDeletionSettingUIModel);
            serviceDocDeletionSettingUIModel.setRefNodeName(serviceDocDeletionSetting.getRefNodeName());
            serviceDocDeletionSettingUIModel.setRefServiceEntityName(serviceDocDeletionSetting.getRefServiceEntityName());
            serviceDocDeletionSettingUIModel.setNodeInstId(serviceDocDeletionSetting.getNodeInstId());
            serviceDocDeletionSettingUIModel.setDeletionStrategy(serviceDocDeletionSetting.getDeletionStrategy());
            serviceDocDeletionSettingUIModel.setAdmDeleteStatus(serviceDocDeletionSetting.getAdmDeleteStatus());
            if (logonInfo != null) {
                Map<Integer, String> deletionStrategyMap = this.initDeletionStrategy(logonInfo.getLanguageCode());
                serviceDocDeletionSettingUIModel.setDeletionStrategyValue(deletionStrategyMap.get(serviceDocDeletionSetting.getDeletionStrategy()));
            }
        }
    }


    public void convUIToServiceDocDeletionSetting(ServiceDocDeletionSettingUIModel serviceDocDeletionSettingUIModel, ServiceDocDeletionSetting rawEntity) {
        if(serviceDocDeletionSettingUIModel != null && rawEntity != null){
            DocFlowProxy.convUIToServiceEntityNode(serviceDocDeletionSettingUIModel, rawEntity);
            rawEntity.setRefNodeName(serviceDocDeletionSettingUIModel.getRefNodeName());
            rawEntity.setRefServiceEntityName(serviceDocDeletionSettingUIModel.getRefServiceEntityName());
            rawEntity.setNodeInstId(serviceDocDeletionSettingUIModel.getNodeInstId());
            rawEntity.setDeletionStrategy(serviceDocDeletionSettingUIModel.getDeletionStrategy());
            rawEntity.setAdmDeleteStatus(serviceDocDeletionSettingUIModel.getAdmDeleteStatus());
        }
    }


}
