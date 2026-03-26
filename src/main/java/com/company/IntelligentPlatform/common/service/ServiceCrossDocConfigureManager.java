package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.dto.ServiceCrossDocConfigureUIModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.StandardDocFlowDirectionProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceCrossDocConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocumentSetting;

import java.util.List;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class ServiceCrossDocConfigureManager {

    public static final String METHOD_ConvServiceCrossDocConfigureToUI = "convServiceCrossDocConfigureToUI";

    public static final String METHOD_ConvUIToServiceCrossDocConfigure = "convUIToServiceCrossDocConfigure";

    public static final String METHOD_ConvHomeDocumentToConfigureUI = "convHomeDocumentToConfigureUI";
    
    @Autowired
    protected ServiceDocumentSettingManager serviceDocumentSettingManager;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected StandardDocFlowDirectionProxy standardDocFlowDirectionProxy;

    protected Logger logger = LoggerFactory.getLogger(ServiceCrossDocConfigureManager.class);

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), ServiceDocumentSetting.NODENAME,
                        request.getUuid(), ServiceCrossDocConfigure.NODENAME, serviceDocumentSettingManager);
        docPageHeaderInputPara.setGenBaseModelList(
                (DocPageHeaderModelProxy.GenBaseModelList<ServiceDocumentSetting>) serviceDocumentSetting -> {
                    // How to get the base page header model list
                    List<PageHeaderModel> pageHeaderModelList =
                            docPageHeaderModelProxy.getDocPageHeaderModelList(serviceDocumentSetting,
                                    null);
                    return pageHeaderModelList;
                });
        docPageHeaderInputPara.setGenHomePageModel(
                (DocPageHeaderModelProxy.GenHomePageModel<ServiceCrossDocConfigure>) (serviceCrossDocConfigure, pageHeaderModel) -> {
                    // How to render current page header
                    String targetDocTypeValue = null;
                    try {
                        targetDocTypeValue =
                                serviceDocumentSettingManager.getDocumentTypeValue(serviceCrossDocConfigure.getTargetDocType(), serialLogonInfo.getLanguageCode());
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
    public void convServiceCrossDocConfigureToUI(
            ServiceCrossDocConfigure serviceCrossDocConfigure,
            ServiceCrossDocConfigureUIModel serviceCrossDocConfigureUIModel,
            LogonInfo logonInfo)  {
        if (serviceCrossDocConfigure != null) {
            if (!ServiceEntityStringHelper.checkNullString(serviceCrossDocConfigure
                    .getUuid())) {
                serviceCrossDocConfigureUIModel.setUuid(serviceCrossDocConfigure
                        .getUuid());
            }
            if (!ServiceEntityStringHelper.checkNullString(serviceCrossDocConfigure
                    .getParentNodeUUID())) {
                serviceCrossDocConfigureUIModel
                        .setParentNodeUUID(serviceCrossDocConfigure
                                .getParentNodeUUID());
            }
            if (!ServiceEntityStringHelper.checkNullString(serviceCrossDocConfigure
                    .getRootNodeUUID())) {
                serviceCrossDocConfigureUIModel
                        .setRootNodeUUID(serviceCrossDocConfigure.getRootNodeUUID());
            }
            if (!ServiceEntityStringHelper.checkNullString(serviceCrossDocConfigure
                    .getClient())) {
                serviceCrossDocConfigureUIModel.setClient(serviceCrossDocConfigure
                        .getClient());
            }
            serviceCrossDocConfigureUIModel.setTargetDocType(serviceCrossDocConfigure.getTargetDocType());
            serviceCrossDocConfigureUIModel.setCrossDocRelationType(serviceCrossDocConfigure.getCrossDocRelationType());
            if (logonInfo != null) {
                try {
                    String targetDocTypeValue =
                            serviceDocumentSettingManager.getDocumentTypeValue(serviceCrossDocConfigure.getTargetDocType(), logonInfo.getLanguageCode());
                    serviceCrossDocConfigureUIModel.setTargetDocTypeValue(targetDocTypeValue);
                    String crossDocRelationTypeValue =
                            standardDocFlowDirectionProxy.getDocFlowDirectValue(serviceCrossDocConfigure.getCrossDocRelationType(),
                                    logonInfo.getLanguageCode());
                    serviceCrossDocConfigureUIModel.setCrossDocRelationTypeValue(crossDocRelationTypeValue);
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                }
            }
        }
    }

    public void convUIToServiceCrossDocConfigure(ServiceCrossDocConfigureUIModel serviceCrossDocConfigureUIModel, ServiceCrossDocConfigure rawEntity) {
        if(serviceCrossDocConfigureUIModel != null && rawEntity != null){
            if (!ServiceEntityStringHelper
                    .checkNullString(serviceCrossDocConfigureUIModel.getUuid())) {
                rawEntity.setUuid(serviceCrossDocConfigureUIModel.getUuid());
            }
            if (!ServiceEntityStringHelper
                    .checkNullString(serviceCrossDocConfigureUIModel
                            .getParentNodeUUID())) {
                rawEntity.setParentNodeUUID(serviceCrossDocConfigureUIModel
                        .getParentNodeUUID());
            }
            if (!ServiceEntityStringHelper
                    .checkNullString(serviceCrossDocConfigureUIModel.getRootNodeUUID())) {
                rawEntity.setRootNodeUUID(serviceCrossDocConfigureUIModel
                        .getRootNodeUUID());
            }
            if (!ServiceEntityStringHelper
                    .checkNullString(serviceCrossDocConfigureUIModel.getClient())) {
                rawEntity.setClient(serviceCrossDocConfigureUIModel.getClient());
            }
            rawEntity.setCrossDocRelationType(serviceCrossDocConfigureUIModel.getCrossDocRelationType());
            rawEntity.setTargetDocType(serviceCrossDocConfigureUIModel.getTargetDocType());
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
            ServiceCrossDocConfigureUIModel serviceCrossDocConfigureUIModel,
            LogonInfo logonInfo)  {
        if (serviceDocumentSetting != null) {

            serviceCrossDocConfigureUIModel.setHomeDocType(serviceDocumentSetting.getDocumentType());
            serviceCrossDocConfigureUIModel.setParentDocId(serviceDocumentSetting.getId());
            serviceCrossDocConfigureUIModel.setParentDocName(serviceDocumentSetting.getName());
            if (logonInfo != null) {
                try {
                    String homeDocTypeValue =
                            serviceDocumentSettingManager.getDocumentTypeValue(serviceDocumentSetting.getDocumentType(), logonInfo.getLanguageCode());
                    serviceCrossDocConfigureUIModel.setHomeDocTypeValue(homeDocTypeValue);
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                }
            }
        }
    }

}
