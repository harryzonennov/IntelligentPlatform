package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.dto.ServiceCrossDocEventMonitorUIModel;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.DocActionExecutionProxyFactory;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.StandardDocFlowDirectionProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceCrossDocConfigure;
import com.company.IntelligentPlatform.common.model.ServiceCrossDocEventMonitor;
import com.company.IntelligentPlatform.common.model.ServiceDocumentSetting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class ServiceCrossDocEventMonitorManager {

    public static final String METHOD_ConvServiceCrossDocEventMonitorToUI = "convServiceCrossDocEventMonitorToUI";

    public static final String METHOD_ConvUIToServiceCrossDocEventMonitor = "convUIToServiceCrossDocEventMonitor";

    public static final String METHOD_ConvServiceCrossDocConfigureToEventUI = "convServiceCrossDocConfigureToEventUI";

    public static final String METHOD_ConvHomeDocumentToEventUI = "convHomeDocumentToEventUI";
    
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
    protected ServiceCrossDocConfigureManager serviceCrossDocConfigureManager;

    protected Logger logger = LoggerFactory.getLogger(ServiceCrossDocEventMonitorManager.class);

    protected Map<String, Map<Integer, String>> triggerDocActionScenarioMapLan = new HashMap<>();

    protected Map<String, Map<Integer, String>> triggerParentMapLan = new HashMap<>();

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), ServiceCrossDocConfigure.NODENAME,
                        request.getUuid(), ServiceCrossDocEventMonitor.NODENAME, serviceDocumentSettingManager);
        docPageHeaderInputPara.setGenBaseModelList(
                (DocPageHeaderModelProxy.GenBaseModelList<ServiceCrossDocConfigure>) serviceCrossDocConfigure -> {
                    SimpleSEJSONRequest baseRequest = new SimpleSEJSONRequest();
                    baseRequest.setUuid(serviceCrossDocConfigure.getUuid());
                    baseRequest.setBaseUUID(serviceCrossDocConfigure.getParentNodeUUID());
                    List<PageHeaderModel> pageHeaderModelList =
                            serviceCrossDocConfigureManager.getPageHeaderModelList(baseRequest, serialLogonInfo);
                    return pageHeaderModelList;
                });
        docPageHeaderInputPara.setGenHomePageModel(
                (DocPageHeaderModelProxy.GenHomePageModel<ServiceCrossDocEventMonitor>) (serviceCrossDocEventMonitor, pageHeaderModel) -> {
                    try {
                        String headerId = getHeaderId(serviceCrossDocEventMonitor, serialLogonInfo);
                        pageHeaderModel.setHeaderName(headerId);
                        return pageHeaderModel;
                    } catch (ServiceEntityInstallationException e) {
                        logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    }
                    return pageHeaderModel;
                });
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, serialLogonInfo.getClient());
    }

    private String getHeaderId(ServiceCrossDocEventMonitor serviceCrossDocEventMonitor, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, ServiceEntityInstallationException {
        ServiceCrossDocConfigure serviceCrossDocConfigure = getServiceCrossDocConfigure(serviceCrossDocEventMonitor);
        ServiceDocumentSetting serviceDocumentSetting = getServiceDocumentSetting(serviceCrossDocEventMonitor);
        Map<Integer, String> homeDocActionMap =
                docActionExecutionProxyFactory.getDocActionConfigureMapByDocType(Integer.parseInt(serviceDocumentSetting.getDocumentType()),
                        serialLogonInfo.getLanguageCode());
        Map<Integer, String> targetDocActionMap =
                docActionExecutionProxyFactory.getDocActionConfigureMapByDocType(Integer.parseInt(serviceCrossDocConfigure.getTargetDocType()),
                        serialLogonInfo.getLanguageCode());
        String headerId = ServiceEntityStringHelper.EMPTYSTRING;
        if (homeDocActionMap != null) {
            headerId = headerId + homeDocActionMap.get(serviceCrossDocEventMonitor.getTriggerHomeActionCode());
            if (targetDocActionMap != null) {
                headerId = headerId + " ";
            }
        }
        if (targetDocActionMap != null) {
            headerId = headerId + targetDocActionMap.get(serviceCrossDocEventMonitor.getTargetActionCode());
        }
        return headerId;
    }

    private ServiceCrossDocConfigure getServiceCrossDocConfigure(ServiceCrossDocEventMonitor serviceCrossDocEventMonitor)
            throws ServiceEntityConfigureException {
        ServiceCrossDocConfigure serviceCrossDocConfigure =
                (ServiceCrossDocConfigure) serviceDocumentSettingManager.getEntityNodeByUUID(serviceCrossDocEventMonitor.getParentNodeUUID(),
                        ServiceCrossDocConfigure.NODENAME, serviceCrossDocEventMonitor.getClient());
        return serviceCrossDocConfigure;
    }

    private ServiceDocumentSetting getServiceDocumentSetting(ServiceCrossDocEventMonitor serviceCrossDocEventMonitor)
            throws ServiceEntityConfigureException {
        ServiceDocumentSetting serviceDocumentSetting =
                (ServiceDocumentSetting) serviceDocumentSettingManager.getEntityNodeByUUID(serviceCrossDocEventMonitor.getRootNodeUUID(),
                        ServiceDocumentSetting.NODENAME, serviceCrossDocEventMonitor.getClient());
        return serviceDocumentSetting;
    }

    public Map<Integer, String> initTriggerDocActionScenarioMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.triggerDocActionScenarioMapLan, ServiceCrossDocEventMonitorUIModel.class, "triggerDocActionScenario");
    }

    private String getTriggerDocActionScenarioValue(int key, String languageCode)
            throws ServiceEntityInstallationException {
        Map<Integer, String> triggerDocActionScenarioMap = initTriggerDocActionScenarioMap(languageCode);
        return triggerDocActionScenarioMap.get(key);
    }

    public Map<Integer, String> initTriggerParentModeMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.triggerParentMapLan, ServiceCrossDocEventMonitorUIModel.class, "triggerParentMode");
    }

    private String getTriggerParentModeValue(int key, String languageCode)
            throws ServiceEntityInstallationException {
        Map<Integer, String> triggerParentModeMap = initTriggerParentModeMap(languageCode);
        return triggerParentModeMap.get(key);
    }

    public Map<Integer, String> getDocActionMap(int documentType, String languageCode)
            throws DocActionException, ServiceEntityInstallationException {
        return docActionExecutionProxyFactory.getDocActionMapByDocType(documentType, languageCode);
    }

    public void convServiceCrossDocConfigureToEventUI(
            ServiceCrossDocConfigure serviceCrossDocConfigure,
            ServiceCrossDocEventMonitorUIModel serviceCrossDocEventMonitorUIModel, LogonInfo logonInfo) {
        serviceCrossDocEventMonitorUIModel.setTargetDocType(serviceCrossDocConfigure.getTargetDocType());
        serviceCrossDocEventMonitorUIModel.setCrossDocRelationType(serviceCrossDocConfigure.getCrossDocRelationType());
        if (logonInfo != null) {
            try {
                String targetDocTypeValue = serviceDocumentSettingManager
                        .getDocumentTypeValue(serviceCrossDocConfigure.getTargetDocType(), logonInfo.getLanguageCode());
                serviceCrossDocEventMonitorUIModel.setTargetDocTypeValue(targetDocTypeValue);
                String crossDocRelationTypeValue =
                        standardDocFlowDirectionProxy.getDocFlowDirectValue(serviceCrossDocConfigure.getCrossDocRelationType(),
                                logonInfo.getLanguageCode());
                serviceCrossDocEventMonitorUIModel.setCrossDocRelationTypeValue(crossDocRelationTypeValue);
                Map<Integer, String> targetDocActionMap =
                        docActionExecutionProxyFactory.getDocActionConfigureMapByDocType(Integer.parseInt(serviceCrossDocConfigure.getTargetDocType()),
                                logonInfo.getLanguageCode());
                if (targetDocActionMap != null) {
                    serviceCrossDocEventMonitorUIModel.setTargetActionCodeValue(
                            targetDocActionMap.get(serviceCrossDocEventMonitorUIModel.getTargetActionCode()));
                }
            } catch (ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            }
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     * @throws ServiceEntityConfigureException
     */
    public void convServiceCrossDocEventMonitorToUI(
            ServiceCrossDocEventMonitor serviceCrossDocEventMonitor,
            ServiceCrossDocEventMonitorUIModel serviceCrossDocEventMonitorUIModel,
            LogonInfo logonInfo)  {
        if (serviceCrossDocEventMonitor != null) {
            if (!ServiceEntityStringHelper.checkNullString(serviceCrossDocEventMonitor
                    .getUuid())) {
                serviceCrossDocEventMonitorUIModel.setUuid(serviceCrossDocEventMonitor
                        .getUuid());
            }
            if (!ServiceEntityStringHelper.checkNullString(serviceCrossDocEventMonitor
                    .getParentNodeUUID())) {
                serviceCrossDocEventMonitorUIModel
                        .setParentNodeUUID(serviceCrossDocEventMonitor
                                .getParentNodeUUID());
            }
            if (!ServiceEntityStringHelper.checkNullString(serviceCrossDocEventMonitor
                    .getRootNodeUUID())) {
                serviceCrossDocEventMonitorUIModel
                        .setRootNodeUUID(serviceCrossDocEventMonitor.getRootNodeUUID());
            }
            if (!ServiceEntityStringHelper.checkNullString(serviceCrossDocEventMonitor
                    .getClient())) {
                serviceCrossDocEventMonitorUIModel.setClient(serviceCrossDocEventMonitor
                        .getClient());
            }
            serviceCrossDocEventMonitorUIModel.setTargetActionCode(serviceCrossDocEventMonitor.getTargetActionCode());
            serviceCrossDocEventMonitorUIModel.setTriggerDocActionScenario(serviceCrossDocEventMonitor.getTriggerDocActionScenario());
            serviceCrossDocEventMonitorUIModel.setTriggerHomeActionCode(serviceCrossDocEventMonitor.getTriggerHomeActionCode());
            if (logonInfo != null) {
                try {
                    String triggerDocActionScenarioValue =
                            getTriggerDocActionScenarioValue(serviceCrossDocEventMonitor.getTriggerDocActionScenario()
                                    , logonInfo.getLanguageCode());
                    serviceCrossDocEventMonitorUIModel.setTriggerDocActionScenarioValue(triggerDocActionScenarioValue);
                    String triggerParentModeValue =
                            getTriggerParentModeValue(serviceCrossDocEventMonitor.getTriggerParentMode(),
                                    logonInfo.getLanguageCode());
                    serviceCrossDocEventMonitorUIModel.setTriggerParentModeValue(triggerParentModeValue);
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                }
            }
        }
    }

    public void convUIToServiceCrossDocEventMonitor(ServiceCrossDocEventMonitorUIModel serviceCrossDocEventMonitorUIModel, ServiceCrossDocEventMonitor rawEntity) {
        if (serviceCrossDocEventMonitorUIModel != null && rawEntity != null) {
            if (!ServiceEntityStringHelper
                    .checkNullString(serviceCrossDocEventMonitorUIModel.getUuid())) {
                rawEntity.setUuid(serviceCrossDocEventMonitorUIModel.getUuid());
            }
            if (!ServiceEntityStringHelper
                    .checkNullString(serviceCrossDocEventMonitorUIModel
                            .getParentNodeUUID())) {
                rawEntity.setParentNodeUUID(serviceCrossDocEventMonitorUIModel
                        .getParentNodeUUID());
            }
            if (!ServiceEntityStringHelper
                    .checkNullString(serviceCrossDocEventMonitorUIModel.getRootNodeUUID())) {
                rawEntity.setRootNodeUUID(serviceCrossDocEventMonitorUIModel
                        .getRootNodeUUID());
            }
            if (!ServiceEntityStringHelper
                    .checkNullString(serviceCrossDocEventMonitorUIModel.getClient())) {
                rawEntity.setClient(serviceCrossDocEventMonitorUIModel.getClient());
            }
            rawEntity.setTriggerParentMode(serviceCrossDocEventMonitorUIModel.getTriggerParentMode());
            rawEntity.setTargetActionCode(serviceCrossDocEventMonitorUIModel.getTargetActionCode());
            rawEntity.setTriggerDocActionScenario(serviceCrossDocEventMonitorUIModel.getTriggerDocActionScenario());
            rawEntity.setTriggerHomeActionCode(serviceCrossDocEventMonitorUIModel.getTriggerHomeActionCode());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     * @throws ServiceEntityConfigureException
     */
    public void convHomeDocumentToEventUI(
            ServiceDocumentSetting serviceDocumentSetting,
            ServiceCrossDocEventMonitorUIModel serviceCrossDocEventMonitorUIModel,
            LogonInfo logonInfo)  {
        if (serviceDocumentSetting != null) {
            serviceCrossDocEventMonitorUIModel.setHomeDocType(serviceDocumentSetting.getDocumentType());
            if (logonInfo != null) {
                try {
                    String homeDocTypeValue =
                            serviceDocumentSettingManager.getDocumentTypeValue(serviceDocumentSetting.getDocumentType(), logonInfo.getLanguageCode());
                    serviceCrossDocEventMonitorUIModel.setHomeDocTypeValue(homeDocTypeValue);
                    Map<Integer, String> homeDocActionMap =
                            docActionExecutionProxyFactory.getDocActionConfigureMapByDocType(Integer.parseInt(serviceDocumentSetting.getDocumentType()),
                                    logonInfo.getLanguageCode());
                    if (homeDocActionMap != null) {
                        serviceCrossDocEventMonitorUIModel.setTriggerHomeActionCodeValue(
                                homeDocActionMap.get(serviceCrossDocEventMonitorUIModel.getTriggerHomeActionCode()));
                    }
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                }
            }
        }
    }

}
