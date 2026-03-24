package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.MatConfigHeaderConditionUIModel;
import com.company.IntelligentPlatform.common.model.MatConfigHeaderCondition;
import com.company.IntelligentPlatform.common.model.MaterialConfigureTemplate;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.StandardLogicOperatorProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class MatConfigHeaderConditionManager {

    public static final String METHOD_ConvMatConfigHeaderConditionToUI = "convMatConfigHeaderConditionToUI";

    public static final String METHOD_ConvUIToMatConfigHeaderCondition = "convUIToMatConfigHeaderCondition";

    public static final String METHOD_ConvConfigureTemplateToHeaderConditionUI = "convConfigureTemplateToHeaderConditionUI";
    
    @Autowired
    protected MaterialConfigureTemplateManager materialConfigureTemplateManager;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    @Autowired
    protected MatDecisionValueSettingManager matDecisionValueSettingManager;

    @Autowired
    protected StandardLogicOperatorProxy standardLogicOperatorProxy;

    private Map<String, Map<String, String>> refNodeInstIdMapLan = new HashMap<>();
    
    
    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), MaterialConfigureTemplate.NODENAME,
                        request.getUuid(), MatConfigHeaderCondition.NODENAME, materialConfigureTemplateManager);
        docPageHeaderInputPara.setGenBaseModelList(
                (DocPageHeaderModelProxy.GenBaseModelList<MaterialConfigureTemplate>) materialConfigureTemplate -> {
                    // How to get the base page header model list
                    List<PageHeaderModel> pageHeaderModelList =
                            docPageHeaderModelProxy.getDocPageHeaderModelList(materialConfigureTemplate, null);
                    return pageHeaderModelList;
                });
        docPageHeaderInputPara.setGenHomePageModel(
                (DocPageHeaderModelProxy.GenHomePageModel<MatConfigHeaderCondition>) (matConfigHeaderCondition,
                                                                                     pageHeaderModel) -> {
                    // How to render current page header
                    pageHeaderModel.setHeaderName(matConfigHeaderCondition.getFieldName());
                    return pageHeaderModel;
                });
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
    }

    public void convMatConfigHeaderConditionToUI(
            MatConfigHeaderCondition matConfigHeaderCondition,
            MatConfigHeaderConditionUIModel matConfigHeaderConditionUIModel)
            throws ServiceEntityInstallationException,
            ServiceEntityConfigureException {
        convMatConfigHeaderConditionToUI(matConfigHeaderCondition,
                matConfigHeaderConditionUIModel, null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     * @throws ServiceEntityConfigureException
     */
    public void convMatConfigHeaderConditionToUI(
            MatConfigHeaderCondition matConfigHeaderCondition,
            MatConfigHeaderConditionUIModel matConfigHeaderConditionUIModel,
            LogonInfo logoInfo) throws ServiceEntityInstallationException,
            ServiceEntityConfigureException {
        if (matConfigHeaderCondition != null) {
            if (!ServiceEntityStringHelper
                    .checkNullString(matConfigHeaderCondition.getUuid())) {
                matConfigHeaderConditionUIModel
                        .setUuid(matConfigHeaderCondition.getUuid());
            }
            if (!ServiceEntityStringHelper
                    .checkNullString(matConfigHeaderCondition
                            .getParentNodeUUID())) {
                matConfigHeaderConditionUIModel
                        .setParentNodeUUID(matConfigHeaderCondition
                                .getParentNodeUUID());
            }
            if (!ServiceEntityStringHelper
                    .checkNullString(matConfigHeaderCondition.getRootNodeUUID())) {
                matConfigHeaderConditionUIModel
                        .setRootNodeUUID(matConfigHeaderCondition
                                .getRootNodeUUID());
            }
            if (!ServiceEntityStringHelper
                    .checkNullString(matConfigHeaderCondition.getClient())) {
                matConfigHeaderConditionUIModel
                        .setClient(matConfigHeaderCondition.getClient());
            }
            matConfigHeaderConditionUIModel.setName(matConfigHeaderCondition
                    .getName());
            matConfigHeaderConditionUIModel.setNote(matConfigHeaderCondition
                    .getNote());
            matConfigHeaderConditionUIModel
                    .setFieldName(matConfigHeaderCondition.getFieldName());
            matConfigHeaderConditionUIModel
                    .setRefNodeInstId(matConfigHeaderCondition
                            .getRefNodeInstId());
            if (logoInfo != null) {
                Map<String, String> refNodeInstIdMap = initRefNodeInstIdMap(logoInfo.getLanguageCode());
                matConfigHeaderConditionUIModel
                        .setRefNodeInstValue(refNodeInstIdMap
                                .get(matConfigHeaderCondition
                                        .getRefNodeInstId()));
            }
            matConfigHeaderConditionUIModel
                    .setFieldValue(matConfigHeaderCondition.getFieldValue());
            matConfigHeaderConditionUIModel
                    .setLogicOperator(matConfigHeaderCondition
                            .getLogicOperator());
            if (logoInfo != null) {
                Map<Integer, String> logicOperatorMap = materialConfigureTemplateManager.initLogicOperatorMap(logoInfo
                        .getLanguageCode());
                matConfigHeaderConditionUIModel
                        .setLogicOperatorValue(logicOperatorMap
                                .get(matConfigHeaderCondition
                                        .getLogicOperator()));
            }
            matConfigHeaderConditionUIModel.setId(matConfigHeaderCondition
                    .getId());
            this.setValueIdNameLogic(matConfigHeaderCondition,
                    matConfigHeaderConditionUIModel);
        }
    }

    protected void setValueIdNameLogic(
            MatConfigHeaderCondition matConfigHeaderCondition,
            MatConfigHeaderConditionUIModel matConfigHeaderConditionUIModel)
            throws ServiceEntityConfigureException {
        ServiceEntityNode targetDecisionNode = matDecisionValueSettingManager
                .getHeaderConditionSENode(
                        matConfigHeaderCondition.getRefNodeInstId(),
                        matConfigHeaderCondition.getFieldName(),
                        matConfigHeaderCondition.getFieldValue(),
                        matConfigHeaderCondition.getClient());
        if (targetDecisionNode != null) {
            matConfigHeaderConditionUIModel.setValueId(targetDecisionNode
                    .getId());
            matConfigHeaderConditionUIModel.setValueName(targetDecisionNode
                    .getName());
        } else {
            if (IServiceEntityNodeFieldConstant.ID
                    .equals(matConfigHeaderCondition.getFieldName())) {
                matConfigHeaderConditionUIModel
                        .setValueId(matConfigHeaderCondition.getFieldValue());
            }
            if (IServiceEntityNodeFieldConstant.NAME
                    .equals(matConfigHeaderCondition.getFieldName())) {
                matConfigHeaderConditionUIModel
                        .setValueName(matConfigHeaderCondition.getFieldValue());
            }
        }
    }

    /**
     * [Internal method] Convert from UI model to se
     * model:matConfigHeaderCondition
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToMatConfigHeaderCondition(
            MatConfigHeaderConditionUIModel matConfigHeaderConditionUIModel,
            MatConfigHeaderCondition rawEntity) {
        DocFlowProxy.convUIToServiceEntityNode(matConfigHeaderConditionUIModel, rawEntity);
        rawEntity.setFieldName(matConfigHeaderConditionUIModel.getFieldName());
        rawEntity.setRefNodeInstId(matConfigHeaderConditionUIModel
                .getRefNodeInstId());
        rawEntity
                .setFieldValue(matConfigHeaderConditionUIModel.getFieldValue());
        rawEntity.setLogicOperator(matConfigHeaderConditionUIModel
                .getLogicOperator());
        rawEntity.setId(matConfigHeaderConditionUIModel.getId());
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     */
    public void convConfigureTemplateToHeaderConditionUI(
            MaterialConfigureTemplate materialConfigureTemplate,
            MatConfigHeaderConditionUIModel matConfigHeaderConditionUIModel) {
        matConfigHeaderConditionUIModel.setTemplateId(materialConfigureTemplate
                .getId());
        matConfigHeaderConditionUIModel
                .setTemplateName(materialConfigureTemplate.getName());
    }

    public Map<String, String> initRefNodeInstIdMap(String languageCode)
            throws ServiceEntityInstallationException {
        String path = this.getClass().getResource("")
                .getPath() + "MatConfigHeaderCondition_RefNodeInstId";
        return ServiceLanHelper.initDefLanguageStrMapResource(languageCode,
                this.refNodeInstIdMapLan, path);
    }


}
