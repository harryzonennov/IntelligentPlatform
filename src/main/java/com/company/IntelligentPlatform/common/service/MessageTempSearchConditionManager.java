package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.dto.MessageTempSearchConditionUIModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.MessageTempSearchCondition;
import com.company.IntelligentPlatform.common.model.MessageTemplate;

import java.util.List;
import java.util.Map;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class MessageTempSearchConditionManager {

    public static final String METHOD_ConvMessageTempSearchConditionToUI = "convMessageTempSearchConditionToUI";

    public static final String METHOD_ConvMessageTemplateToSearchUI = "convMessageTemplateToSearchUI";

    public static final String METHOD_ConvUIToMessageTempSearchCondition = "convUIToMessageTempSearchCondition";

    @Autowired
    protected MessageTemplateManager messageTemplateManager;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    protected Logger logger = LoggerFactory.getLogger(MessageTempSearchConditionManager.class);

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), MessageTemplate.NODENAME,
                        request.getUuid(), MessageTempSearchCondition.NODENAME, messageTemplateManager);
        docPageHeaderInputPara.setGenBaseModelList((DocPageHeaderModelProxy.GenBaseModelList<MessageTemplate>) messageTemplate -> {
            // How to get the base page header model list
            return docPageHeaderModelProxy.getDocPageHeaderModelList(messageTemplate, null);
        });
        docPageHeaderInputPara.setGenHomePageModel((DocPageHeaderModelProxy.GenHomePageModel<MessageTempSearchCondition>) (messageTempSearchCondition, pageHeaderModel) -> {
            // How to render current page header
            pageHeaderModel.setHeaderName(messageTempSearchCondition.getFieldName());
            return pageHeaderModel;
        });
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convMessageTemplateToSearchUI(MessageTemplate messageTemplate,
                                              MessageTempSearchConditionUIModel messageTempSearchConditionUIModel) {
        if (messageTemplate != null) {
            messageTempSearchConditionUIModel.setParentNodeId(messageTemplate
                    .getId());
            messageTempSearchConditionUIModel
                    .setSearchModelName(messageTemplate.getSearchModelClass());
        }
    }

    public void convMessageTempSearchConditionToUI(
            MessageTempSearchCondition messageTempSearchCondition,
            MessageTempSearchConditionUIModel messageTempSearchConditionUIModel)
            throws ServiceEntityInstallationException {
        this.convMessageTempSearchConditionToUI(messageTempSearchCondition, messageTempSearchConditionUIModel, null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     */
    public void convMessageTempSearchConditionToUI(
            MessageTempSearchCondition messageTempSearchCondition,
            MessageTempSearchConditionUIModel messageTempSearchConditionUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        if (messageTempSearchCondition != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(messageTempSearchCondition, messageTempSearchConditionUIModel);
            messageTempSearchConditionUIModel
                    .setFieldName(messageTempSearchCondition.getFieldName());
            messageTempSearchConditionUIModel
                    .setLogicOperator(messageTempSearchCondition
                            .getLogicOperator());
            if(logonInfo != null){
                Map<Integer, String> logicOperatorMap = messageTemplateManager.initLogicOperatorMap(logonInfo.getLanguageCode());
                messageTempSearchConditionUIModel
                        .setLogicOperatorValue(logicOperatorMap
                                .get(messageTempSearchCondition.getLogicOperator()));
            }
            messageTempSearchConditionUIModel
                    .setSearchOperator(messageTempSearchCondition
                            .getSearchOperator());
            messageTempSearchConditionUIModel
                    .setFieldValue(messageTempSearchCondition.getFieldValue());
            messageTempSearchConditionUIModel.setFieldName(messageTempSearchCondition.getFieldName());
            messageTempSearchConditionUIModel.setDataSourceProviderId(messageTempSearchCondition.getDataSourceProviderId());
            messageTempSearchConditionUIModel.setDataOffsetDirection(messageTempSearchCondition.getDataOffsetDirection());
            messageTempSearchConditionUIModel.setDataOffsetValue(messageTempSearchCondition.getDataOffsetValue());
            messageTempSearchConditionUIModel.setDataOffsetUnit(messageTempSearchCondition.getDataOffsetUnit());
            messageTempSearchConditionUIModel.setFieldName(messageTempSearchCondition.getFieldName());
            messageTempSearchConditionUIModel.setSwitchFlag(messageTempSearchCondition.getSwitchFlag());
        }
    }

    /**
     * [Internal method] Convert from UI model to se
     * model:messageTempSearchCondition
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToMessageTempSearchCondition(
            MessageTempSearchConditionUIModel messageTempSearchConditionUIModel,
            MessageTempSearchCondition rawEntity) {
        DocFlowProxy.convUIToServiceEntityNode(messageTempSearchConditionUIModel, rawEntity);
        rawEntity
                .setFieldName(messageTempSearchConditionUIModel.getFieldName());
        rawEntity.setLogicOperator(messageTempSearchConditionUIModel
                .getLogicOperator());
        rawEntity.setSearchOperator(messageTempSearchConditionUIModel
                .getSearchOperator());
        rawEntity.setFieldValue(messageTempSearchConditionUIModel
                .getFieldValue());
        rawEntity.setFieldName(messageTempSearchConditionUIModel.getFieldName());
        rawEntity.setDataSourceProviderId(messageTempSearchConditionUIModel.getDataSourceProviderId());
        rawEntity.setDataOffsetDirection(messageTempSearchConditionUIModel.getDataOffsetDirection());
        rawEntity.setDataOffsetValue(messageTempSearchConditionUIModel.getDataOffsetValue());
        rawEntity.setDataOffsetUnit(messageTempSearchConditionUIModel.getDataOffsetUnit());
        rawEntity.setFieldName(messageTempSearchConditionUIModel.getFieldName());
        rawEntity.setSwitchFlag(messageTempSearchConditionUIModel.getSwitchFlag());
    }

}
