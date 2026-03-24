package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.dto.CrossCopyInvolvePartyUIModel;
import com.company.IntelligentPlatform.common.service.*;
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
import com.company.IntelligentPlatform.common.model.CrossCopyDocConfigure;
import com.company.IntelligentPlatform.common.model.CrossCopyInvolveParty;
import com.company.IntelligentPlatform.common.model.ServiceDocumentSetting;

import java.util.List;
import java.util.Map;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class CrossCopyInvolvePartyManager {

    public static final String METHOD_ConvCrossCopyInvolvePartyToUI = "convCrossCopyInvolvePartyToUI";

    public static final String METHOD_ConvUIToCrossCopyInvolveParty = "convUIToCrossCopyInvolveParty";

    public static final String METHOD_ConvCrossCopyDocConfigureToEventUI = "convCrossCopyDocConfigureToEventUI";

    public static final String METHOD_ConvHomeDocumentToInvolvePartyUI = "convHomeDocumentToInvolvePartyUI";
    
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
    protected CrossCopyDocConfigureManager serviceCrossDocConfigureManager;

    @Autowired
    protected DocInvolvePartyProxy docInvolvePartyProxy;

    @Autowired
    protected StandardSwitchProxy standardSwitchProxy;

    protected Logger logger = LoggerFactory.getLogger(CrossCopyInvolvePartyManager.class);

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), CrossCopyDocConfigure.NODENAME,
                        request.getUuid(), CrossCopyInvolveParty.NODENAME, serviceDocumentSettingManager);
        docPageHeaderInputPara.setGenBaseModelList(
                (DocPageHeaderModelProxy.GenBaseModelList<CrossCopyDocConfigure>) serviceCrossDocConfigure -> {
                    SimpleSEJSONRequest baseRequest = new SimpleSEJSONRequest();
                    baseRequest.setUuid(serviceCrossDocConfigure.getUuid());
                    baseRequest.setBaseUUID(serviceCrossDocConfigure.getParentNodeUUID());
                    List<PageHeaderModel> pageHeaderModelList =
                            serviceCrossDocConfigureManager.getPageHeaderModelList(baseRequest, serialLogonInfo);
                    return pageHeaderModelList;
                });
        docPageHeaderInputPara.setGenHomePageModel(
                (DocPageHeaderModelProxy.GenHomePageModel<CrossCopyInvolveParty>) (crossCopyInvolveParty, pageHeaderModel) -> {
                    try {
                        String headerId = getHeaderId(crossCopyInvolveParty, serialLogonInfo);
                        pageHeaderModel.setHeaderName(headerId);
                        return pageHeaderModel;
                    } catch (ServiceEntityInstallationException | DocActionException e) {
                        logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    }
                    return pageHeaderModel;
                });
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, serialLogonInfo.getClient());
    }

    private String getHeaderId(CrossCopyInvolveParty crossCopyInvolveParty, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, ServiceEntityInstallationException, DocActionException {
        CrossCopyDocConfigure serviceCrossDocConfigure = getCrossCopyDocConfigure(crossCopyInvolveParty);
        ServiceDocumentSetting serviceDocumentSetting = getServiceDocumentSetting(crossCopyInvolveParty);
        Map<Integer, String> sourceInvolvePartyMap =
                docInvolvePartyProxy.getDocInvolvePartyMapByDocType(Integer.parseInt(serviceDocumentSetting.getDocumentType()),
                        serialLogonInfo.getLanguageCode());
        Map<Integer, String> targetInvolvePartyMap =
                docInvolvePartyProxy.getDocInvolvePartyMapByDocType(serviceCrossDocConfigure.getTargetDocType(),
                        serialLogonInfo.getLanguageCode());
        String headerId = ServiceEntityStringHelper.EMPTYSTRING;
        if(sourceInvolvePartyMap != null){
            headerId = headerId + sourceInvolvePartyMap.get(crossCopyInvolveParty.getSourcePartyRole());
            if(targetInvolvePartyMap != null){
                headerId = headerId + " ";
            }
        }
        if(targetInvolvePartyMap != null){
            headerId = headerId + sourceInvolvePartyMap.get(crossCopyInvolveParty.getTargetPartyRole());
        }
        return headerId;
    }

    private CrossCopyDocConfigure getCrossCopyDocConfigure(CrossCopyInvolveParty crossCopyInvolveParty)
            throws ServiceEntityConfigureException {
        CrossCopyDocConfigure serviceCrossDocConfigure =
                (CrossCopyDocConfigure) serviceDocumentSettingManager.getEntityNodeByUUID(crossCopyInvolveParty.getParentNodeUUID(),
                        CrossCopyDocConfigure.NODENAME, crossCopyInvolveParty.getClient());
        return serviceCrossDocConfigure;
    }

    private ServiceDocumentSetting getServiceDocumentSetting(CrossCopyInvolveParty crossCopyInvolveParty)
            throws ServiceEntityConfigureException {
        ServiceDocumentSetting serviceDocumentSetting =
                (ServiceDocumentSetting) serviceDocumentSettingManager.getEntityNodeByUUID(crossCopyInvolveParty.getRootNodeUUID(),
                        ServiceDocumentSetting.NODENAME, crossCopyInvolveParty.getClient());
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

    public void convCrossCopyDocConfigureToEventUI(
            CrossCopyDocConfigure crossCopyDocConfigure,
            CrossCopyInvolvePartyUIModel crossCopyInvolvePartyUIModel, LogonInfo logonInfo){
        crossCopyInvolvePartyUIModel.setTargetDocType(crossCopyDocConfigure.getTargetDocType());
        if (logonInfo != null) {
            try {
                String targetDocTypeValue = serviceDocumentSettingManager
                        .getDocumentTypeValue(Integer.toString(crossCopyDocConfigure.getTargetDocType()),
                                logonInfo.getLanguageCode());
                crossCopyInvolvePartyUIModel.setTargetDocTypeValue(targetDocTypeValue);
                Map<Integer, String> involvePartyMap =
                        docInvolvePartyProxy.getDocInvolvePartyMapByDocType(crossCopyDocConfigure.getTargetDocType(),
                                logonInfo.getLanguageCode());
                if(involvePartyMap != null){
                    crossCopyInvolvePartyUIModel.setTargetPartyRoleValue(
                            involvePartyMap.get(crossCopyInvolvePartyUIModel.getTargetPartyRole()));
                }
            } catch (ServiceEntityInstallationException | DocActionException e) {
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
    public void convCrossCopyInvolvePartyToUI(
            CrossCopyInvolveParty crossCopyInvolveParty,
            CrossCopyInvolvePartyUIModel crossCopyInvolvePartyUIModel,
            LogonInfo logonInfo)  {
        if (crossCopyInvolveParty != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(crossCopyInvolveParty, crossCopyInvolvePartyUIModel);
            crossCopyInvolvePartyUIModel.setTargetPartyRole(crossCopyInvolveParty.getTargetPartyRole());
            crossCopyInvolvePartyUIModel.setSourcePartyRole(crossCopyInvolveParty.getSourcePartyRole());
            crossCopyInvolvePartyUIModel.setLogonPartyFlag(crossCopyInvolveParty.getLogonPartyFlag());
            if (logonInfo != null) {
                try {
                    String logonPartyFlagValue =
                            standardSwitchProxy.getSwitchValue(crossCopyInvolveParty.getLogonPartyFlag(),logonInfo.getLanguageCode());
                    crossCopyInvolvePartyUIModel.setLogonPartyFlagValue(logonPartyFlagValue);
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                }
            }
        }
    }

    public void convUIToCrossCopyInvolveParty(CrossCopyInvolvePartyUIModel crossCopyInvolvePartyUIModel, CrossCopyInvolveParty rawEntity) {
        if(crossCopyInvolvePartyUIModel != null && rawEntity != null){
            DocFlowProxy.convUIToServiceEntityNode(crossCopyInvolvePartyUIModel, rawEntity);
            rawEntity.setLogonPartyFlag(crossCopyInvolvePartyUIModel.getLogonPartyFlag());
            rawEntity.setSourcePartyRole(crossCopyInvolvePartyUIModel.getSourcePartyRole());
            rawEntity.setTargetPartyRole(crossCopyInvolvePartyUIModel.getTargetPartyRole());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     * @throws ServiceEntityConfigureException
     */
    public void convHomeDocumentToInvolvePartyUI(
            ServiceDocumentSetting serviceDocumentSetting,
            CrossCopyInvolvePartyUIModel crossCopyInvolvePartyUIModel,
            LogonInfo logonInfo)  {
        if (serviceDocumentSetting != null) {
            crossCopyInvolvePartyUIModel.setSourceDocType(Integer.parseInt(serviceDocumentSetting.getDocumentType()));
            if (logonInfo != null) {
                try {
                    String homeDocTypeValue =
                            serviceDocumentSettingManager.getDocumentTypeValue(serviceDocumentSetting.getDocumentType(), logonInfo.getLanguageCode());
                    crossCopyInvolvePartyUIModel.setSourceDocTypeValue(homeDocTypeValue);
                    Map<Integer, String> sourcePartyRoleMap =
                            getPartyRoleMap(Integer.parseInt(serviceDocumentSetting.getDocumentType()),
                                    logonInfo.getLanguageCode());
                    if(sourcePartyRoleMap != null){
                        crossCopyInvolvePartyUIModel.setSourcePartyRoleValue(
                                sourcePartyRoleMap.get(crossCopyInvolvePartyUIModel.getSourcePartyRole()));
                    }
                } catch (ServiceEntityInstallationException | DocActionException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                }
            }
        }
    }


}
