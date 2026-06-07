package com.company.IntelligentPlatform.platform.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.platform.controller.PageHeaderModel;
import com.company.IntelligentPlatform.platform.dto.ServiceDocInitInvolvePartyUIModel;
import com.company.IntelligentPlatform.platform.service.DocActionException;
import com.company.IntelligentPlatform.platform.service.DocActionExecutionProxyFactory;
import com.company.IntelligentPlatform.platform.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.platform.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.platform.service.StandardDocFlowDirectionProxy;
import com.company.IntelligentPlatform.platform.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.platform.service.DocFlowProxy;
import com.company.IntelligentPlatform.platform.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.platform.model.LogonInfo;
import com.company.IntelligentPlatform.platform.model.SerialLogonInfo;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.platform.model.ServiceDocInitConfigure;
import com.company.IntelligentPlatform.platform.model.ServiceDocInitInvolveParty;
import com.company.IntelligentPlatform.platform.model.ServiceDocumentSetting;

import java.util.List;
import java.util.Map;
import com.company.IntelligentPlatform.platform.controller.SEUIComModel;

@Service
public class ServiceDocInitInvolvePartyManager {

    public static final String METHOD_ConvServiceDocInitInvolvePartyToUI = "convServiceDocInitInvolvePartyToUI";

    public static final String METHOD_ConvUIToServiceDocInitInvolveParty = "convUIToServiceDocInitInvolveParty";
    
    @Autowired
    protected ServiceDocumentSettingManager serviceDocumentSettingManager;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected StandardDocFlowDirectionProxy standardDocFlowDirectionProxy;

    @Autowired
    protected DocActionExecutionProxyFactory docActionExecutionProxyFactory;

    @Autowired
    protected ServiceDocInitConfigureManager serviceCrossDocConfigureManager;

    @Autowired
    protected DocInvolvePartyProxy docInvolvePartyProxy;

    @Autowired
    protected StandardSwitchProxy standardSwitchProxy;

    protected Logger logger = LoggerFactory.getLogger(ServiceDocInitInvolvePartyManager.class);

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), ServiceDocInitConfigure.NODENAME,
                        request.getUuid(), ServiceDocInitInvolveParty.NODENAME, serviceDocumentSettingManager);
        docPageHeaderInputPara.setGenBaseModelList(
                (DocPageHeaderModelProxy.GenBaseModelList<ServiceDocInitConfigure>) serviceCrossDocConfigure -> {
                    SimpleSEJSONRequest baseRequest = new SimpleSEJSONRequest();
                    baseRequest.setUuid(serviceCrossDocConfigure.getUuid());
                    baseRequest.setBaseUUID(serviceCrossDocConfigure.getParentNodeUUID());
                    List<PageHeaderModel> pageHeaderModelList =
                            serviceCrossDocConfigureManager.getPageHeaderModelList(baseRequest, serialLogonInfo);
                    return pageHeaderModelList;
                });
        docPageHeaderInputPara.setGenHomePageModel(
                (DocPageHeaderModelProxy.GenHomePageModel<ServiceDocInitInvolveParty>) (serviceDocInitInvolveParty, pageHeaderModel) -> {
                    pageHeaderModel.setHeaderName(serviceDocInitInvolveParty.getUuid());
                    return pageHeaderModel;
                });
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, serialLogonInfo.getClient());
    }

    private ServiceDocInitConfigure getServiceDocInitConfigure(ServiceDocInitInvolveParty serviceDocInitInvolveParty)
            throws ServiceEntityConfigureException {
        ServiceDocInitConfigure serviceCrossDocConfigure =
                (ServiceDocInitConfigure) serviceDocumentSettingManager.getEntityNodeByUUID(serviceDocInitInvolveParty.getParentNodeUUID(),
                        ServiceDocInitConfigure.NODENAME, serviceDocInitInvolveParty.getClient());
        return serviceCrossDocConfigure;
    }

    private ServiceDocumentSetting getServiceDocumentSetting(ServiceDocInitInvolveParty serviceDocInitInvolveParty)
            throws ServiceEntityConfigureException {
        ServiceDocumentSetting serviceDocumentSetting =
                (ServiceDocumentSetting) serviceDocumentSettingManager.getEntityNodeByUUID(serviceDocInitInvolveParty.getRootNodeUUID(),
                        ServiceDocumentSetting.NODENAME, serviceDocInitInvolveParty.getClient());
        return serviceDocumentSetting;
    }

    public Map<Integer, String> getPartyRoleMap(int documentType, String languageCode)
            throws DocActionException, ServiceEntityInstallationException {
        Map<Integer, String> involvePartyMap =
                docInvolvePartyProxy.getDocInvolvePartyMapByDocType(documentType,
                        languageCode);
        return involvePartyMap;
    }

    public Map<Integer, String> getLogonPartyFlagMap(String languageCode)
            throws ServiceEntityInstallationException {
        Map<Integer, String> logonPartyFlagMap =
                standardSwitchProxy.getSwitchMap(languageCode);
        return logonPartyFlagMap;
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     * @throws ServiceEntityConfigureException
     */
    public void convServiceDocInitInvolvePartyToUI(
            ServiceDocInitInvolveParty serviceDocInitInvolveParty,
            ServiceDocInitInvolvePartyUIModel serviceDocInitInvolvePartyUIModel,
            LogonInfo logonInfo)  {
        if (serviceDocInitInvolveParty != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(serviceDocInitInvolveParty, serviceDocInitInvolvePartyUIModel);
            serviceDocInitInvolvePartyUIModel.setPartyRole(serviceDocInitInvolveParty.getPartyRole());
            serviceDocInitInvolvePartyUIModel.setLogonPartyFlag(serviceDocInitInvolveParty.getLogonPartyFlag());
            if (logonInfo != null) {
                try {
                    String logonPartyFlagValue =
                            standardSwitchProxy.getSwitchValue(serviceDocInitInvolveParty.getLogonPartyFlag(),logonInfo.getLanguageCode());
                    serviceDocInitInvolvePartyUIModel.setLogonPartyFlagValue(logonPartyFlagValue);
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                }
            }
        }
    }

    public void convUIToServiceDocInitInvolveParty(ServiceDocInitInvolvePartyUIModel serviceDocInitInvolvePartyUIModel, ServiceDocInitInvolveParty rawEntity) {
        if(serviceDocInitInvolvePartyUIModel != null && rawEntity != null){
            DocFlowProxy.convUIToServiceEntityNode(serviceDocInitInvolvePartyUIModel, rawEntity);
            rawEntity.setLogonPartyFlag(serviceDocInitInvolvePartyUIModel.getLogonPartyFlag());
        }
    }

}
