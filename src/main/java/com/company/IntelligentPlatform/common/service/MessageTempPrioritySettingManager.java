package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.dto.MessageTempPrioritySettingUIModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.MessageTempPrioritySetting;
import com.company.IntelligentPlatform.common.model.MessageTemplate;
import com.company.IntelligentPlatform.common.model.SystemCodeValueCollection;

import java.util.List;
import java.util.Map;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class MessageTempPrioritySettingManager {

    public static final String METHOD_ConvMessageTempPrioritySettingToUI = "convMessageTempPrioritySettingToUI";

    public static final String METHOD_ConvMessageTemplateToPriorityUI = "convMessageTemplateToPriorityUI";

    public static final String METHOD_ConvUIToMessageTempPrioritySetting = "convUIToMessageTempPrioritySetting";

    public static final String METHOD_ConvSystemCodeValueCollectionToUI = "convSystemCodeValueCollectionToUI";

    @Autowired
    protected MessageTemplateManager messageTemplateManager;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    @Autowired
    protected SystemDefDocActionCodeProxy systemDefDocActionCodeProxy;

    protected Logger logger = LoggerFactory.getLogger(MessageTempPrioritySettingManager.class);

    /**
     * Get Action Code Map as well as extended action code map
     * @param languageCode
     * @return
     * @throws ServiceEntityInstallationException
     */
    public Map<String, String> getExtActionCodeMap(String languageCode)
            throws ServiceEntityInstallationException {
        return systemDefDocActionCodeProxy.getExtActionCodeMap(languageCode);
    }
    
    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), MessageTemplate.NODENAME,
                        request.getUuid(), MessageTempPrioritySetting.NODENAME, messageTemplateManager);
        docPageHeaderInputPara.setGenBaseModelList(new DocPageHeaderModelProxy.GenBaseModelList<MessageTemplate>() {
            @Override
            public List<com.company.IntelligentPlatform.common.controller.PageHeaderModel> execute(MessageTemplate messageTemplate) throws ServiceEntityConfigureException {
                // How to get the base page header model list
                return docPageHeaderModelProxy.getDocPageHeaderModelList(messageTemplate, null);
            }
        });
        docPageHeaderInputPara.setGenHomePageModel(new DocPageHeaderModelProxy.GenHomePageModel<MessageTempPrioritySetting>() {
            @Override
            public com.company.IntelligentPlatform.common.controller.PageHeaderModel execute(MessageTempPrioritySetting messageTemplateMaterialItem, com.company.IntelligentPlatform.common.controller.PageHeaderModel pageHeaderModel) throws ServiceEntityConfigureException {
                // How to render current page header
                pageHeaderModel.setHeaderName(messageTemplateMaterialItem.getFieldName());
                return pageHeaderModel;
            }
        });
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
    }
    
    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convMessageTemplateToPriorityUI(MessageTemplate messageTemplate,
                                               MessageTempPrioritySettingUIModel messageTempPrioritySettingUIModel) {
        if (messageTemplate != null) {
            messageTempPrioritySettingUIModel.setParentNodeId(messageTemplate
                    .getId());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convMessageTempPrioritySettingToUI(
            MessageTempPrioritySetting messageTempPrioritySetting,
            MessageTempPrioritySettingUIModel messageTempPrioritySettingUIModel) {
        convMessageTempPrioritySettingToUI(messageTempPrioritySetting, messageTempPrioritySettingUIModel, null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convMessageTempPrioritySettingToUI(
            MessageTempPrioritySetting messageTempPrioritySetting,
            MessageTempPrioritySettingUIModel messageTempPrioritySettingUIModel, LogonInfo logonInfo) {
        if (messageTempPrioritySetting != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(messageTempPrioritySetting, messageTempPrioritySettingUIModel);
            messageTempPrioritySettingUIModel
                    .setIconStyle(messageTempPrioritySetting.getIconStyle());
            messageTempPrioritySettingUIModel
                    .setEndValue(messageTempPrioritySetting.getEndValue());
            messageTempPrioritySettingUIModel
                    .setRefPrioritySettingUUID(messageTempPrioritySetting
                            .getRefPrioritySettingUUID());
            messageTempPrioritySettingUIModel
                    .setColorStyle(messageTempPrioritySetting.getColorStyle());
            messageTempPrioritySettingUIModel
                    .setStartValue(messageTempPrioritySetting.getStartValue());
            messageTempPrioritySettingUIModel
                    .setActionCode(messageTempPrioritySetting.getActionCode());
            messageTempPrioritySettingUIModel
                    .setPriorityCode(messageTempPrioritySetting
                            .getPriorityCode());
            messageTempPrioritySettingUIModel.setMessageLevelCode(messageTempPrioritySetting.getMessageLevelCode());
            if(logonInfo != null){
                try {
                    Map<Integer, String> priorityMap = messageTemplateManager.initPriorityCodeMap(logonInfo.getLanguageCode());
                    messageTempPrioritySettingUIModel.setPriorityCodeValue(priorityMap.get(messageTempPrioritySetting.getPriorityCode()));
                    Map<Integer, String> messageLevelMap = messageTemplateManager.initMessageLevelCodeMap(logonInfo.getLanguageCode());
                    messageTempPrioritySettingUIModel.setMessageLevelCodeValue(messageLevelMap.get(messageTempPrioritySetting.getMessageLevelCode()));
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                }
            }
            messageTempPrioritySettingUIModel.setDataSourceProviderId(messageTempPrioritySetting.getDataSourceProviderId());
            messageTempPrioritySettingUIModel.setDataOffsetDirection(messageTempPrioritySetting.getDataOffsetDirection());
            messageTempPrioritySettingUIModel.setDataOffsetValue(messageTempPrioritySetting.getDataOffsetValue());
            messageTempPrioritySettingUIModel.setDataOffsetUnit(messageTempPrioritySetting.getDataOffsetUnit());
            messageTempPrioritySettingUIModel.setFieldName(messageTempPrioritySetting.getFieldName());
            messageTempPrioritySettingUIModel.setSwitchFlag(messageTempPrioritySetting.getSwitchFlag());
            messageTempPrioritySettingUIModel.setMessageTitle(messageTempPrioritySetting.getMessageTitle());
            messageTempPrioritySettingUIModel.setMessageContent(messageTempPrioritySetting.getMessageContent());
            messageTempPrioritySettingUIModel.setFieldValue(messageTempPrioritySetting.getFieldValue());
        }
    }

    /**
     * [Internal method] Convert from UI model to se
     * model:messageTempPrioritySetting
     *
     */
    public void convUIToMessageTempPrioritySetting(
            MessageTempPrioritySettingUIModel messageTempPrioritySettingUIModel,
            MessageTempPrioritySetting rawEntity) {
        DocFlowProxy.convUIToServiceEntityNode(messageTempPrioritySettingUIModel, rawEntity);
        rawEntity.setIconStyle(messageTempPrioritySettingUIModel.getIconStyle());
        rawEntity.setEndValue(messageTempPrioritySettingUIModel.getEndValue());
        rawEntity.setRefPrioritySettingUUID(messageTempPrioritySettingUIModel
                .getRefPrioritySettingUUID());
        rawEntity.setColorStyle(messageTempPrioritySettingUIModel
                .getColorStyle());
        rawEntity.setStartValue(messageTempPrioritySettingUIModel
                .getStartValue());
        rawEntity.setActionCode(messageTempPrioritySettingUIModel
                .getActionCode());
        rawEntity.setPriorityCode(messageTempPrioritySettingUIModel
                .getPriorityCode());
        rawEntity.setMessageLevelCode(messageTempPrioritySettingUIModel.getMessageLevelCode());
        rawEntity.setDataSourceProviderId(messageTempPrioritySettingUIModel.getDataSourceProviderId());
        rawEntity.setDataOffsetDirection(messageTempPrioritySettingUIModel.getDataOffsetDirection());
        rawEntity.setDataOffsetValue(messageTempPrioritySettingUIModel.getDataOffsetValue());
        rawEntity.setDataOffsetUnit(messageTempPrioritySettingUIModel.getDataOffsetUnit());
        rawEntity.setFieldName(messageTempPrioritySettingUIModel.getFieldName());
        rawEntity.setSwitchFlag(messageTempPrioritySettingUIModel.getSwitchFlag());
        rawEntity.setMessageTitle(messageTempPrioritySettingUIModel.getMessageTitle());
        rawEntity.setMessageContent(messageTempPrioritySettingUIModel.getMessageContent());
        rawEntity.setFieldValue(messageTempPrioritySettingUIModel.getFieldValue());
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     */
    public void convSystemCodeValueCollectionToUI(
            SystemCodeValueCollection systemCodeValueCollection,
            MessageTempPrioritySettingUIModel messageTempPrioritySettingUIModel) {
        if (systemCodeValueCollection != null) {
            messageTempPrioritySettingUIModel.setNote(systemCodeValueCollection
                    .getNote());
            messageTempPrioritySettingUIModel
                    .setCollectionCategory(systemCodeValueCollection
                            .getCollectionCategory());
            messageTempPrioritySettingUIModel.setName(systemCodeValueCollection
                    .getName());
            messageTempPrioritySettingUIModel.setId(systemCodeValueCollection
                    .getId());
        }
    }

}
