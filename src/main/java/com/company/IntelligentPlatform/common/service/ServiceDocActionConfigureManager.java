package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.dto.ServiceDocActionConfigureUIModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.StandardDocFlowDirectionProxy;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceDocActionConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocumentSetting;

import java.util.List;
import java.util.Map;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class ServiceDocActionConfigureManager {

    public static final String METHOD_ConvServiceDocActionConfigureToUI = "convServiceDocActionConfigureToUI";

    public static final String METHOD_ConvUIToServiceDocActionConfigure = "convUIToServiceDocActionConfigure";

    public static final String METHOD_ConvDocumentToActionConfigureUI = "convDocumentToActionConfigureUI";
    
    @Autowired
    protected ServiceDocumentSettingManager serviceDocumentSettingManager;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected StandardDocFlowDirectionProxy standardDocFlowDirectionProxy;

    @Autowired
    protected StandardSwitchProxy standardSwitchProxy;

    protected Logger logger = LoggerFactory.getLogger(ServiceDocActionConfigureManager.class);

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), ServiceDocumentSetting.NODENAME,
                        request.getUuid(), ServiceDocActionConfigure.NODENAME, serviceDocumentSettingManager);
        docPageHeaderInputPara.setGenBaseModelList(new DocPageHeaderModelProxy.GenBaseModelList<ServiceDocumentSetting>() {
            @Override
            public List<PageHeaderModel> execute(ServiceDocumentSetting serviceDocumentSetting) throws ServiceEntityConfigureException {
                // How to get the base page header model list
                List<PageHeaderModel> pageHeaderModelList =
                        docPageHeaderModelProxy.getDocPageHeaderModelList(serviceDocumentSetting,
                                null);
                return pageHeaderModelList;
            }
        });
        docPageHeaderInputPara.setGenHomePageModel(
                (DocPageHeaderModelProxy.GenHomePageModel<ServiceDocActionConfigure>) (serviceDocActionConfigure, pageHeaderModel) -> {
                    // How to render current page header
                    try {
                        String customSwitchValue =
                                standardSwitchProxy.getSwitchValue(serviceDocActionConfigure.getCustomSwitch(),
                                        serialLogonInfo.getLanguageCode());
                        pageHeaderModel.setHeaderName(customSwitchValue);
                    } catch (ServiceEntityInstallationException e) {
                        logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    }
                    return pageHeaderModel;
                });
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, serialLogonInfo.getClient());
    }

    public Map<Integer, String> initCustomSwitchMap(String languageCode)
            throws ServiceEntityInstallationException {
        return standardSwitchProxy.getSwitchMap(languageCode);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     * @throws ServiceEntityConfigureException
     */
    public void convServiceDocActionConfigureToUI(
            ServiceDocActionConfigure serviceDocActionConfigure,
            ServiceDocActionConfigureUIModel serviceDocActionConfigureUIModel,
            LogonInfo logonInfo)  {
        if (serviceDocActionConfigure != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(serviceDocActionConfigure, serviceDocActionConfigureUIModel);
            serviceDocActionConfigureUIModel.setJsonContent(serviceDocActionConfigure.getJsonContent());
            serviceDocActionConfigureUIModel.setCustomSwitch(serviceDocActionConfigure.getCustomSwitch());
            if(logonInfo != null){
                try {
                    Map<Integer, String> switchFlagMap = initCustomSwitchMap(logonInfo.getLanguageCode());
                    serviceDocActionConfigureUIModel.setCustomSwitchValue(switchFlagMap.get(serviceDocActionConfigure.getCustomSwitch()));
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                }
            }
        }
    }

    public void convUIToServiceDocActionConfigure(ServiceDocActionConfigureUIModel serviceDocActionConfigureUIModel, ServiceDocActionConfigure rawEntity) {
        if(serviceDocActionConfigureUIModel != null && rawEntity != null){
            DocFlowProxy.convUIToServiceEntityNode(serviceDocActionConfigureUIModel, rawEntity);
            rawEntity.setJsonContent(serviceDocActionConfigureUIModel.getJsonContent());
            rawEntity.setCustomSwitch(serviceDocActionConfigureUIModel.getCustomSwitch());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     * @throws ServiceEntityConfigureException
     */
    public void convDocumentToActionConfigureUI(ServiceDocumentSetting serviceDocumentSetting,
                                     ServiceDocActionConfigureUIModel serviceDocActionConfigureUIModel,
                                     LogonInfo logonInfo) {
        if (serviceDocumentSetting != null) {
            serviceDocActionConfigureUIModel.setHomeDocType(Integer.parseInt(serviceDocumentSetting.getDocumentType()));
            if (logonInfo != null) {
                try {
                    String homeDocTypeValue =
                            serviceDocumentSettingManager.getDocumentTypeValue(serviceDocumentSetting.getDocumentType(),
                                    logonInfo.getLanguageCode());
                    serviceDocActionConfigureUIModel.setHomeDocTypeValue(homeDocTypeValue);
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                }
            }
        }
    }

}
