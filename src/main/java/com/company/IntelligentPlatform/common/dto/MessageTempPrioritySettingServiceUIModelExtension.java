package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.MessageTempPrioritySettingUIModel;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.service.MessageTempPrioritySettingManager;
import com.company.IntelligentPlatform.common.service.MessageTemplateManager;
import com.company.IntelligentPlatform.common.service.SystemCodeValueCollectionManager;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.MessageTempPrioritySetting;
import com.company.IntelligentPlatform.common.model.MessageTemplate;
import com.company.IntelligentPlatform.common.model.SystemCodeValueCollection;

@Service
public class MessageTempPrioritySettingServiceUIModelExtension extends
        ServiceUIModelExtension {

    @Autowired
    protected SystemCodeValueCollectionManager systemCodeValueCollectionManager;

    @Autowired
    protected MessageTempPrioritySettingManager messageTempPrioritySettingManager;

    public List<ServiceUIModelExtension> getChildUIModelExtensions() {
        List<ServiceUIModelExtension> resultList = new ArrayList<>();
        return resultList;
    }

    @Override
    public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
        List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
        List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
        ServiceUIModelExtensionUnion messageTempPrioritySettingExtensionUnion = new ServiceUIModelExtensionUnion();
        messageTempPrioritySettingExtensionUnion
                .setNodeInstId(MessageTempPrioritySetting.NODENAME);
        messageTempPrioritySettingExtensionUnion
                .setNodeName(MessageTempPrioritySetting.NODENAME);

        // UI Model Configure of node:[MessageTempPrioritySetting]
        UIModelNodeMapConfigure messageTempPrioritySettingMap = new UIModelNodeMapConfigure();
        messageTempPrioritySettingMap
                .setSeName(MessageTempPrioritySetting.SENAME);
        messageTempPrioritySettingMap
                .setNodeName(MessageTempPrioritySetting.NODENAME);
        messageTempPrioritySettingMap
                .setNodeInstID(MessageTempPrioritySetting.NODENAME);
        messageTempPrioritySettingMap.setHostNodeFlag(true);
        Class<?>[] messageTempPrioritySettingConvToUIParas = {
                MessageTempPrioritySetting.class,
                MessageTempPrioritySettingUIModel.class};
        messageTempPrioritySettingMap
                .setConvToUIMethodParas(messageTempPrioritySettingConvToUIParas);
        messageTempPrioritySettingMap.setLogicManager(messageTempPrioritySettingManager);
        messageTempPrioritySettingMap
                .setConvToUIMethod(MessageTempPrioritySettingManager.METHOD_ConvMessageTempPrioritySettingToUI);
        Class<?>[] MessageTempPrioritySettingConvUIToParas = {
                MessageTempPrioritySettingUIModel.class,
                MessageTempPrioritySetting.class};
        messageTempPrioritySettingMap
                .setConvUIToMethodParas(MessageTempPrioritySettingConvUIToParas);
        messageTempPrioritySettingMap
                .setConvUIToMethod(MessageTempPrioritySettingManager.METHOD_ConvUIToMessageTempPrioritySetting);
        uiModelNodeMapList.add(messageTempPrioritySettingMap);

        // UI Model Configure of node:[SystemCodeValueCollection]
        UIModelNodeMapConfigure systemCodeValueCollectionMap = new UIModelNodeMapConfigure();
        systemCodeValueCollectionMap
                .setSeName(SystemCodeValueCollection.SENAME);
        systemCodeValueCollectionMap
                .setNodeName(SystemCodeValueCollection.NODENAME);
        systemCodeValueCollectionMap
                .setNodeInstID(SystemCodeValueCollection.SENAME);
        systemCodeValueCollectionMap
                .setBaseNodeInstID(MessageTempPrioritySetting.NODENAME);
        systemCodeValueCollectionMap
                .setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
        systemCodeValueCollectionMap
                .setServiceEntityManager(systemCodeValueCollectionManager);
        List<SearchConfigConnectCondition> systemCodeValueCollectionConditionList =
                new ArrayList<>();
        SearchConfigConnectCondition systemCodeValueCollectionCondition0 = new SearchConfigConnectCondition();
        systemCodeValueCollectionCondition0
                .setSourceFieldName("refPrioritySettingUUID");
        systemCodeValueCollectionCondition0
                .setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
        systemCodeValueCollectionConditionList
                .add(systemCodeValueCollectionCondition0);
        systemCodeValueCollectionMap
                .setConnectionConditions(systemCodeValueCollectionConditionList);
        Class<?>[] systemCodeValueCollectionConvToUIParas = {
                SystemCodeValueCollection.class,
                MessageTempPrioritySettingUIModel.class};
        systemCodeValueCollectionMap
                .setConvToUIMethodParas(systemCodeValueCollectionConvToUIParas);
        systemCodeValueCollectionMap.setLogicManager(messageTempPrioritySettingManager);
        systemCodeValueCollectionMap
                .setConvToUIMethod(MessageTempPrioritySettingManager.METHOD_ConvSystemCodeValueCollectionToUI);
        uiModelNodeMapList.add(systemCodeValueCollectionMap);

        // UI Model Configure of node:[MessageTempSearchCondition]
        UIModelNodeMapConfigure messageTemplateMap = new UIModelNodeMapConfigure();
        messageTemplateMap.setSeName(MessageTemplate.SENAME);
        messageTemplateMap.setNodeName(MessageTemplate.NODENAME);
        messageTemplateMap.setNodeInstID(MessageTemplate.SENAME);
        messageTemplateMap.setHostNodeFlag(false);
        messageTemplateMap
                .setBaseNodeInstID(MessageTempPrioritySetting.NODENAME);
        messageTemplateMap.setLogicManager(messageTempPrioritySettingManager);
        messageTemplateMap
                .setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
        Class<?>[] messageTemplateConvToUIParas = {MessageTemplate.class,
                MessageTempPrioritySettingUIModel.class};
        messageTemplateMap.setConvToUIMethodParas(messageTemplateConvToUIParas);
        messageTemplateMap
                .setConvToUIMethod(MessageTempPrioritySettingManager.METHOD_ConvMessageTemplateToPriorityUI);
        uiModelNodeMapList.add(messageTemplateMap);
        messageTempPrioritySettingExtensionUnion
                .setUiModelNodeMapList(uiModelNodeMapList);
        resultList.add(messageTempPrioritySettingExtensionUnion);
        return resultList;
    }

}
