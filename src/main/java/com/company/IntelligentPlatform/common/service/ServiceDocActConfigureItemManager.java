package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.dto.ServiceDocActConfigureItemUIModel;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.DocActionExecutionProxyFactory;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.StandardDocFlowDirectionProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceDocActionConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocActConfigureItem;
import com.company.IntelligentPlatform.common.model.ServiceDocumentSetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class ServiceDocActConfigureItemManager {

    public static final String METHOD_ConvServiceDocActConfigureItemToUI = "convServiceDocActConfigureItemToUI";

    public static final String METHOD_ConvUIToServiceDocActConfigureItem = "convUIToServiceDocActConfigureItem";

    public static final String METHOD_ConvServiceDocActionConfigureToItemUI = "convServiceDocActionConfigureToItemUI";

    public static final String METHOD_ConvDocumentToItemUI = "convDocumentToItemUI";

    @Autowired
    protected ServiceDocumentSettingManager serviceDocumentSettingManager;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected StandardDocFlowDirectionProxy standardDocFlowDirectionProxy;

    @Autowired
    protected ServiceDocActionConfigureManager serviceDocActionConfigureManager;

    @Autowired
    protected DocActionExecutionProxyFactory docActionExecutionProxyFactory;

    protected Logger logger = LoggerFactory.getLogger(ServiceDocActConfigureItemManager.class);

    protected Map<String, Map<Integer, String>> triggerDocActionScenarioMapLan = new HashMap<>();

    protected Map<String, Map<Integer, String>> triggerParentMapLan = new HashMap<>();

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(),
                        ServiceDocActionConfigure.NODENAME, request.getUuid(), ServiceDocActConfigureItem.NODENAME,
                        serviceDocumentSettingManager);
        docPageHeaderInputPara.setGenBaseModelList(
                (DocPageHeaderModelProxy.GenBaseModelList<ServiceDocActionConfigure>) serviceDocActionConfigure -> {
                    SimpleSEJSONRequest baseRequest = new SimpleSEJSONRequest();
                    baseRequest.setUuid(serviceDocActionConfigure.getUuid());
                    baseRequest.setBaseUUID(serviceDocActionConfigure.getParentNodeUUID());
                    List<PageHeaderModel> pageHeaderModelList =
                            serviceDocActionConfigureManager.getPageHeaderModelList(baseRequest, serialLogonInfo);
                    return pageHeaderModelList;
                });
        docPageHeaderInputPara.setGenHomePageModel(
                (DocPageHeaderModelProxy.GenHomePageModel<ServiceDocActConfigureItem>) (serviceDocActConfigureItem, pageHeaderModel) -> {
                    try {
                        ServiceDocumentSetting serviceDocumentSetting =
                                this.getServiceDocumentSetting(serviceDocActConfigureItem);
                        int documentType = Integer.parseInt(serviceDocumentSetting.getDocumentType());
                        Map<Integer, String> docActionMap =
                                        docActionExecutionProxyFactory.getDocActionConfigureMapByDocType(documentType,
                                serialLogonInfo.getLanguageCode());
                        pageHeaderModel.setHeaderName(docActionMap.get(serviceDocActConfigureItem.getActionCode()));
                        return pageHeaderModel;
                    } catch (ServiceEntityInstallationException e) {
                        logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    }
                    return null;
                });
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, serialLogonInfo.getClient());
    }

    private ServiceDocumentSetting getServiceDocumentSetting(ServiceDocActConfigureItem serviceDocActConfigureItem)
            throws ServiceEntityConfigureException {
        ServiceDocumentSetting serviceDocumentSetting = (ServiceDocumentSetting) serviceDocumentSettingManager.getEntityNodeByUUID(serviceDocActConfigureItem.getRootNodeUUID(),
                ServiceDocumentSetting.NODENAME, serviceDocActConfigureItem.getClient());
        return serviceDocumentSetting;
    }

    public Map<Integer, String> initTriggerDocActionScenarioMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode, this.triggerDocActionScenarioMapLan,
                ServiceDocActConfigureItemUIModel.class, "triggerDocActionScenario");
    }

    private String getTriggerDocActionScenarioValue(int key, String languageCode)
            throws ServiceEntityInstallationException {
        Map<Integer, String> triggerDocActionScenarioMap = initTriggerDocActionScenarioMap(languageCode);
        return triggerDocActionScenarioMap.get(key);
    }

    public Map<Integer, String> initTriggerParentModeMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode, this.triggerParentMapLan,
                ServiceDocActConfigureItemUIModel.class, "triggerParentMode");
    }

    private String getTriggerParentModeValue(int key, String languageCode) throws ServiceEntityInstallationException {
        Map<Integer, String> triggerParentModeMap = initTriggerParentModeMap(languageCode);
        return triggerParentModeMap.get(key);
    }

    public Map<Integer, String> getDocActionMap(int documentType, String languageCode)
            throws DocActionException, ServiceEntityInstallationException {
        return docActionExecutionProxyFactory.getDocActionMapByDocType(documentType, languageCode);
    }


    public void convServiceDocActionConfigureToItemUI(ServiceDocActionConfigure serviceDocActionConfigure,
                                                       ServiceDocActConfigureItemUIModel serviceDocActConfigureItemUIModel,
                                                       LogonInfo logonInfo) {

    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     * @throws ServiceEntityConfigureException
     */
    public void convServiceDocActConfigureItemToUI(ServiceDocActConfigureItem serviceDocActConfigureItem,
                                                   ServiceDocActConfigureItemUIModel serviceDocActConfigureItemUIModel,
                                                   LogonInfo logonInfo) {
        if (serviceDocActConfigureItem != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(serviceDocActConfigureItem, serviceDocActConfigureItemUIModel);
            serviceDocActConfigureItemUIModel.setActionCode(serviceDocActConfigureItem.getActionCode());
            serviceDocActConfigureItemUIModel.setAuthorAction(serviceDocActConfigureItem.getAuthorAction());
            serviceDocActConfigureItemUIModel.setTargetStatus(serviceDocActConfigureItem.getTargetStatus());
            serviceDocActConfigureItemUIModel.setPreStatus(
                    ServiceCollectionsHelper.convertStringToArray(serviceDocActConfigureItem.getPreStatus()));
        }
    }


    public void convUIToServiceDocActConfigureItem(ServiceDocActConfigureItemUIModel serviceDocActConfigureItemUIModel,
                                                   ServiceDocActConfigureItem rawEntity) {
        if (serviceDocActConfigureItemUIModel != null && rawEntity != null) {
            DocFlowProxy.convUIToServiceEntityNode(serviceDocActConfigureItemUIModel, rawEntity);
            rawEntity.setActionCode(serviceDocActConfigureItemUIModel.getActionCode());
            rawEntity.setAuthorAction(serviceDocActConfigureItemUIModel.getAuthorAction());
            rawEntity.setTargetStatus(serviceDocActConfigureItemUIModel.getTargetStatus());
            rawEntity.setPreStatus(
                    ServiceCollectionsHelper.convStringArrayToString(serviceDocActConfigureItemUIModel.getPreStatus()));
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     * @throws ServiceEntityConfigureException
     */
    public void convDocumentToItemUI(ServiceDocumentSetting serviceDocumentSetting,
                                          ServiceDocActConfigureItemUIModel serviceDocActConfigureItemUIModel,
                                          LogonInfo logonInfo) {
        if (serviceDocumentSetting != null) {
            String documentTypeStr = serviceDocumentSetting.getDocumentType();
            int documentType = 0;
            try {
                documentType = Integer.parseInt(documentTypeStr);
            } catch (NumberFormatException e) {
                // do nothing, just continue;
            }
            serviceDocActConfigureItemUIModel.setDocumentType(serviceDocumentSetting.getDocumentType());
            if (logonInfo != null) {
                try {
                    String homeDocTypeValue =
                            serviceDocumentSettingManager.getDocumentTypeValue(serviceDocumentSetting.getDocumentType(),
                                    logonInfo.getLanguageCode());
                    serviceDocActConfigureItemUIModel.setDocumentTypeValue(homeDocTypeValue);
                    if(documentType > 0){
                        Map<Integer, String> docActionMap =
                                docActionExecutionProxyFactory.getDocActionConfigureMapByDocType(documentType,
                                        logonInfo.getLanguageCode());
                        serviceDocActConfigureItemUIModel.setActionCodeValue(docActionMap.get(serviceDocActConfigureItemUIModel.getActionCode()));
                        Map<Integer, String> docStatusMap =
                                docActionExecutionProxyFactory.getStatusMapByDocType(documentType,
                                        logonInfo.getLanguageCode());
                        serviceDocActConfigureItemUIModel.setTargetStatusValue(docStatusMap.get(serviceDocActConfigureItemUIModel.getTargetStatus()));
                        String[] preStatus = serviceDocActConfigureItemUIModel.getPreStatus();
                        if(!ServiceCollectionsHelper.checkNullArray(preStatus)){
                            List<String> preStatusValueList = new ArrayList<>();
                            ServiceCollectionsHelper.forEach(ServiceCollectionsHelper.convArrayToList(preStatus),
                                    tmpStatus->{
                                String statusValue = docStatusMap.get(tmpStatus);
                                if(ServiceEntityStringHelper.checkNullString(statusValue)){
                                    // try convert status into Integer
                                    Integer tmpStatusInt = Integer.parseInt(tmpStatus);
                                    statusValue = docStatusMap.get(tmpStatusInt);
                                }
                                preStatusValueList.add(statusValue);
                                return tmpStatus;
                            });
                            serviceDocActConfigureItemUIModel.setPreStatusValue(ServiceCollectionsHelper.convListToStringArray(preStatusValueList));
                        }
                    }
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                }
            }
        }
    }


}
