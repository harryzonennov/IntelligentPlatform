package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.dto.CrossCopyDocConfigureUIModel;
import com.company.IntelligentPlatform.common.service.DocActionExecutionProxyFactory;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.CrossCopyDocConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocumentSetting;

import java.util.List;
import java.util.Map;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class CrossCopyDocConfigureManager {

    public static final String METHOD_ConvCrossCopyDocConfigureToUI = "convCrossCopyDocConfigureToUI";

    public static final String METHOD_ConvUIToCrossCopyDocConfigure = "convUIToCrossCopyDocConfigure";

    public static final String METHOD_ConvHomeDocumentToConfigureUI = "convHomeDocumentToConfigureUI";
    
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

    protected Logger logger = LoggerFactory.getLogger(CrossCopyDocConfigureManager.class);

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), ServiceDocumentSetting.NODENAME,
                        request.getUuid(), CrossCopyDocConfigure.NODENAME, serviceDocumentSettingManager);
        docPageHeaderInputPara.setGenBaseModelList(
                (DocPageHeaderModelProxy.GenBaseModelList<ServiceDocumentSetting>) serviceDocumentSetting -> {
                    // How to get the base page header model list
                    List<PageHeaderModel> pageHeaderModelList =
                            docPageHeaderModelProxy.getDocPageHeaderModelList(serviceDocumentSetting,
                                    IServiceEntityNodeFieldConstant.NAME);
                    return pageHeaderModelList;
                });
        docPageHeaderInputPara.setGenHomePageModel(
                (DocPageHeaderModelProxy.GenHomePageModel<CrossCopyDocConfigure>) (crossCopyDocConfigure, pageHeaderModel) -> {
                    // How to render current page header
                    String targetDocTypeValue = null;
                    try {
                        targetDocTypeValue =
                                serviceDocumentSettingManager.getDocumentTypeValue(Integer.toString(crossCopyDocConfigure.getTargetDocType()), serialLogonInfo.getLanguageCode());
                        pageHeaderModel.setHeaderName(targetDocTypeValue);
                    } catch (ServiceEntityInstallationException e) {
                        logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    }
                    return pageHeaderModel;
                });
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, serialLogonInfo.getClient());
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     * @throws ServiceEntityConfigureException
     */
    public void convCrossCopyDocConfigureToUI(
            CrossCopyDocConfigure crossCopyDocConfigure,
            CrossCopyDocConfigureUIModel crossCopyDocConfigureUIModel,
            LogonInfo logonInfo)  {
        if (crossCopyDocConfigure != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(crossCopyDocConfigure, crossCopyDocConfigureUIModel);
            crossCopyDocConfigureUIModel.setTargetDocType(crossCopyDocConfigure.getTargetDocType());
            crossCopyDocConfigureUIModel.setCloneAttachmentFlag(crossCopyDocConfigure.getCloneAttachmentFlag());
            crossCopyDocConfigureUIModel.setTriggerSourceActionCode(crossCopyDocConfigure.getTriggerSourceActionCode());
            crossCopyDocConfigureUIModel.setActiveCustomSwitch(crossCopyDocConfigure.getActiveCustomSwitch());
            if (logonInfo != null) {
                try {
                    String targetDocTypeValue =
                            serviceDocumentSettingManager.getDocumentTypeValue(Integer.toString(crossCopyDocConfigure.getTargetDocType()), logonInfo.getLanguageCode());
                    crossCopyDocConfigureUIModel.setTargetDocTypeValue(targetDocTypeValue);
                    String cloneAttachmentFlagValue =
                            standardSwitchProxy.getSwitchValue(crossCopyDocConfigure.getCloneAttachmentFlag(),
                                    logonInfo.getLanguageCode());
                    crossCopyDocConfigureUIModel.setCloneAttachmentFlagValue(cloneAttachmentFlagValue);
                    String activeCustomSwitchValue =
                            standardSwitchProxy.getSwitchValue(crossCopyDocConfigure.getActiveCustomSwitch(),
                                    logonInfo.getLanguageCode());
                    crossCopyDocConfigureUIModel.setActiveCustomSwitchValue(activeCustomSwitchValue);
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                }
            }
        }
    }

    public void convUIToCrossCopyDocConfigure(CrossCopyDocConfigureUIModel crossCopyDocConfigureUIModel, CrossCopyDocConfigure rawEntity) {
        if(crossCopyDocConfigureUIModel != null && rawEntity != null){
            DocFlowProxy.convUIToServiceEntityNode(crossCopyDocConfigureUIModel, rawEntity);
            rawEntity.setTriggerSourceActionCode(crossCopyDocConfigureUIModel.getTriggerSourceActionCode());
            rawEntity.setActiveCustomSwitch(crossCopyDocConfigureUIModel.getActiveCustomSwitch());
            rawEntity.setCloneAttachmentFlag(crossCopyDocConfigureUIModel.getCloneAttachmentFlag());
            rawEntity.setTargetDocType(crossCopyDocConfigureUIModel.getTargetDocType());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     * @throws ServiceEntityConfigureException
     */
    public void convHomeDocumentToConfigureUI(
            ServiceDocumentSetting serviceDocumentSetting,
            CrossCopyDocConfigureUIModel crossCopyDocConfigureUIModel,
            LogonInfo logonInfo)  {
        if (serviceDocumentSetting != null) {
            crossCopyDocConfigureUIModel.setSourceDocType(Integer.parseInt(serviceDocumentSetting.getDocumentType()));
            if (logonInfo != null) {
                try {
                    String homeDocTypeValue =
                            serviceDocumentSettingManager.getDocumentTypeValue(serviceDocumentSetting.getDocumentType(), logonInfo.getLanguageCode());
                    crossCopyDocConfigureUIModel.setSourceDocTypeValue(homeDocTypeValue);
                    Map<Integer, String> homeDocActionMap =
                            docActionExecutionProxyFactory.getDocActionConfigureMapByDocType(Integer.parseInt(serviceDocumentSetting.getDocumentType()),
                                    logonInfo.getLanguageCode());
                    if(homeDocActionMap != null){
                        crossCopyDocConfigureUIModel.setTriggerSourceActionCodeValue(homeDocActionMap.get(crossCopyDocConfigureUIModel.getTriggerSourceActionCode()));
                    }
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                }
            }
        }
    }


}
