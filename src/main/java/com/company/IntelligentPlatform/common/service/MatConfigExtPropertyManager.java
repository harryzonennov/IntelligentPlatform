package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.MatConfigExtPropertySettingUIModel;
import com.company.IntelligentPlatform.common.model.MatConfigExtPropertySetting;
import com.company.IntelligentPlatform.common.model.MaterialConfigureTemplate;
import com.company.IntelligentPlatform.common.model.StandardMaterialUnit;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.util.List;
import java.util.Map;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class MatConfigExtPropertyManager {

    public static final String METHOD_ConvConfigureTemplateToExtPropertyUI = "convConfigureTemplateToExtPropertyUI";

    public static final String METHOD_ConvMatConfigExtPropertySettingToUI = "convMatConfigExtPropertySettingToUI";

    public static final String METHOD_ConvUIToMatConfigExtPropertySetting = "convUIToMatConfigExtPropertySetting";

    @Autowired
    protected StandardMaterialUnitManager standardMaterialUnitManager;

    @Autowired
    protected StandardSwitchProxy standardSwitchProxy;

    @Autowired
    protected MaterialConfigureTemplateManager materialConfigureTemplateManager;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), MaterialConfigureTemplate.NODENAME,
                        request.getUuid(), MatConfigExtPropertySetting.NODENAME, materialConfigureTemplateManager);
        docPageHeaderInputPara.setGenBaseModelList(
                (DocPageHeaderModelProxy.GenBaseModelList<MaterialConfigureTemplate>) materialConfigureTemplate -> {
                    // How to get the base page header model list
                    List<PageHeaderModel> pageHeaderModelList =
                            docPageHeaderModelProxy.getDocPageHeaderModelList(materialConfigureTemplate, null);
                    return pageHeaderModelList;
                });
        docPageHeaderInputPara.setGenHomePageModel(
                (DocPageHeaderModelProxy.GenHomePageModel<MatConfigExtPropertySetting>) (matConfigExtProperty,
                                                                                     pageHeaderModel) -> {
                    // How to render current page header
                    pageHeaderModel.setHeaderName(matConfigExtProperty.getId());
                    return pageHeaderModel;
                });
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
    }


    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     * @throws ServiceEntityConfigureException
     */
    public void convMatConfigExtPropertySettingToUI(
            MatConfigExtPropertySetting matConfigExtPropertySetting,
            MatConfigExtPropertySettingUIModel matConfigExtPropertySettingUIModel)
            throws ServiceEntityInstallationException,
            ServiceEntityConfigureException {
        convMatConfigExtPropertySettingToUI(matConfigExtPropertySetting,
                matConfigExtPropertySettingUIModel, null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     * @throws ServiceEntityConfigureException
     */
    public void convMatConfigExtPropertySettingToUI(
            MatConfigExtPropertySetting matConfigExtPropertySetting,
            MatConfigExtPropertySettingUIModel matConfigExtPropertySettingUIModel,
            LogonInfo logonInfo) throws ServiceEntityInstallationException,
            ServiceEntityConfigureException {
        if (matConfigExtPropertySetting != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(matConfigExtPropertySetting, matConfigExtPropertySettingUIModel);
            matConfigExtPropertySettingUIModel
                    .setFieldType(matConfigExtPropertySetting.getFieldType());
            matConfigExtPropertySettingUIModel
                    .setRefUnitUUID(matConfigExtPropertySetting
                            .getRefUnitUUID());
            StandardMaterialUnit standardUnit = (StandardMaterialUnit) standardMaterialUnitManager
                    .getEntityNodeByKey(
                            matConfigExtPropertySetting.getRefUnitUUID(),
                            IServiceEntityNodeFieldConstant.UUID,
                            StandardMaterialUnit.NODENAME,
                            matConfigExtPropertySetting.getClient(), null);
            if (standardUnit != null) {
                matConfigExtPropertySettingUIModel.setRefUnitValue(standardUnit
                        .getName());
            }
            matConfigExtPropertySettingUIModel
                    .setQualityInspectFlag(matConfigExtPropertySetting
                            .getQualityInspectFlag());
            if (logonInfo != null) {
                Map<Integer, String> measureFlagMap = materialConfigureTemplateManager
                        .initMeasureFlagMap(logonInfo.getLanguageCode());
                matConfigExtPropertySettingUIModel
                        .setMeasureFlagValue(measureFlagMap
                                .get(matConfigExtPropertySetting
                                        .getMeasureFlag()));
                Map<Integer, String> qualityInspectMap = materialConfigureTemplateManager
                        .initQualityInspectMap(logonInfo.getLanguageCode());
                matConfigExtPropertySettingUIModel
                        .setQualityInspectValue(qualityInspectMap
                                .get(matConfigExtPropertySetting
                                        .getQualityInspectFlag()));

            }
            matConfigExtPropertySettingUIModel
                    .setMeasureFlag(matConfigExtPropertySetting
                            .getMeasureFlag());
            matConfigExtPropertySettingUIModel
                    .setNote(matConfigExtPropertySetting.getNote());
        }
    }

    /**
     * [Internal method] Convert from UI model to se
     * model:matConfigExtPropertySetting
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToMatConfigExtPropertySetting(
            MatConfigExtPropertySettingUIModel matConfigExtPropertySettingUIModel,
            MatConfigExtPropertySetting rawEntity) {
        DocFlowProxy.convUIToServiceEntityNode(matConfigExtPropertySettingUIModel, rawEntity);
        rawEntity.setFieldType(matConfigExtPropertySettingUIModel
                .getFieldType());
        rawEntity.setQualityInspectFlag(matConfigExtPropertySettingUIModel
                .getQualityInspectFlag());
        rawEntity.setRefUnitUUID(matConfigExtPropertySettingUIModel
                .getRefUnitUUID());
        rawEntity.setMeasureFlag(matConfigExtPropertySettingUIModel
                .getMeasureFlag());
        rawEntity.setNote(matConfigExtPropertySettingUIModel.getNote());
    }


    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     */
    public void convConfigureTemplateToExtPropertyUI(
            MaterialConfigureTemplate materialConfigureTemplate,
            MatConfigExtPropertySettingUIModel matConfigExtPropertySettingUIModel) {
        matConfigExtPropertySettingUIModel
                .setTemplateId(materialConfigureTemplate.getId());
        matConfigExtPropertySettingUIModel
                .setTemplateName(materialConfigureTemplate.getName());
    }



}
