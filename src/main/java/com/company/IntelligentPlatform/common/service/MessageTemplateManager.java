package com.company.IntelligentPlatform.common.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.MessageTemplateUIModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.repository.MessageTemplateRepository;
import com.company.IntelligentPlatform.common.service.MessageTemplateHandlerRepository;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceFlowRuntimeEngine;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceSimpleDataProviderException;
import com.company.IntelligentPlatform.common.service.ServiceSimpleDataProviderTemplate;
import com.company.IntelligentPlatform.common.service.SimpleDataOffsetUnion;
import com.company.IntelligentPlatform.common.service.SimpleDataProviderFactory;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.SearchFieldConfig;
import com.company.IntelligentPlatform.common.model.GenericServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.MessageTempPrioritySetting;
import com.company.IntelligentPlatform.common.model.MessageTempSearchCondition;
import com.company.IntelligentPlatform.common.model.MessageTemplate;
import com.company.IntelligentPlatform.common.model.MessageTemplateConfigureProxy;

@Service
@Transactional
public class MessageTemplateManager extends ServiceEntityManager {

    public static final String METHOD_ConvMessageTemplateToUI = "convMessageTemplateToUI";

    public static final String METHOD_ConvUIToMessageTemplate = "convUIToMessageTemplate";
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected MessageTemplateRepository messageTemplateDAO;
    @Autowired
    protected MessageTemplateConfigureProxy messageTemplateConfigureProxy;

    @Autowired
    protected MessageTemplateIdHelper messageTemplateIdHelper;

    @Autowired
    protected StandardLogicOperatorProxy standardLogicOperatorProxy;

    @Autowired
    protected SearchProxyConfigManager searchProxyConfigManager;

    @Autowired
    protected SimpleDataProviderFactory simpleDataProviderFactory;

    @Autowired
    protected ServiceSearchProxyRepository serviceSearchProxyRepository;

    @Autowired
    protected StandardPriorityProxy standardPriorityProxy;

    @Autowired
    protected ServiceFlowRuntimeEngine serviceFlowRuntimeEngine;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected MessageLevelCodeProxy messageLevelCodeProxy;

    @Autowired
    protected MessageTemplateHandlerRepository messageTemplateHandlerRepository;

    @Autowired
    protected MessageTemplateSearchProxy messageTemplateSearchProxy;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    protected Logger logger = LoggerFactory.getLogger(MessageTemplateManager.class);

    @Override
    public ServiceEntityNode newRootEntityNode(String client)
            throws ServiceEntityConfigureException {
        MessageTemplate messageTemplate = (MessageTemplate) super
                .newRootEntityNode(client);
        String messageTemplateId = messageTemplateIdHelper.genDefaultId(client);
        messageTemplate.setId(messageTemplateId);
        return messageTemplate;
    }

    public void convMessageTemplateToUI(MessageTemplate messageTemplate,
                                        MessageTemplateUIModel messageTemplateUIModel) {
        convMessageTemplateToUI(messageTemplate, messageTemplateUIModel, null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convMessageTemplateToUI(MessageTemplate messageTemplate,
                                        MessageTemplateUIModel messageTemplateUIModel, LogonInfo logonInfo) {
        if (messageTemplate != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(messageTemplate, messageTemplateUIModel);
            messageTemplateUIModel.setSearchModelName(messageTemplate.getSearchModelName());
            messageTemplateUIModel.setSearchDataUrl(messageTemplate
                    .getSearchDataUrl());
            messageTemplateUIModel.setProcessIndex(messageTemplate
                    .getProcessIndex());
            messageTemplateUIModel.setHandlerClass(messageTemplate
                    .getHandlerClass());
            messageTemplateUIModel.setSearchModelClass(messageTemplate
                    .getSearchModelClass());
            if(logonInfo != null){
                try {
                    Map<String, String> serviceProxyConfigMap = serviceSearchProxyRepository.loadServiceProxyMap(logonInfo.getLanguageCode());
                    if(serviceProxyConfigMap != null){
                        messageTemplateUIModel.setSearchProxyLabel(serviceProxyConfigMap.get(messageTemplate.getSearchProxyName()));
                    }
                    Map<String, String> searchModelNameMap = serviceSearchProxyRepository.loadServiceModelMap(logonInfo.getLanguageCode());
                    if(searchModelNameMap != null){
                        messageTemplateUIModel.setSearchModelLabel(searchModelNameMap.get(messageTemplate.getSearchModelName()));
                    }

                } catch (ServiceEntityInstallationException e) {
                    logger.error("Failed to load message template", e);
                }
            }
            messageTemplateUIModel.setNavigationSourceId(messageTemplate.getNavigationSourceId());
            messageTemplateUIModel.setReferenceModelName(messageTemplate.getReferenceModelName());
            messageTemplateUIModel.setRefProxyConfigUUID(messageTemplate.getRefProxyConfigUUID());
            messageTemplateUIModel.setSearchProxyName(messageTemplate.getSearchProxyName());
            messageTemplateUIModel.setMessageTitle(messageTemplate.getMessageTitle());
            messageTemplateUIModel.setMessageContent(messageTemplate.getMessageContent());
            messageTemplateUIModel.setActionCodeArray(messageTemplate.getActionCodeArray());
        }
    }

    public Map<String, String> getProviderOffsetDirectionMap(String providerId) throws ServiceSimpleDataProviderException {
        ServiceSimpleDataProviderTemplate serviceSimpleDataProviderTemplate =
                simpleDataProviderFactory.getSimpleDataProvider(providerId);
        if (serviceSimpleDataProviderTemplate != null) {
            Map<?, ?> rawMap = serviceSimpleDataProviderTemplate.getOffsetDirectionDropdown();
            Map<String, String> resultMap = new HashMap<>();
            Set<?> keySets = rawMap.keySet();
            for (Object key : keySets) {
                resultMap.put(key.toString(), rawMap.get(key).toString());
            }
            return resultMap;
        }
        return null;
    }

    public Map<String, String> getProviderOffsetUnitMap(String providerId) throws ServiceSimpleDataProviderException {
        ServiceSimpleDataProviderTemplate serviceSimpleDataProviderTemplate =
                simpleDataProviderFactory.getSimpleDataProvider(providerId);
        if (serviceSimpleDataProviderTemplate != null) {
            Map<?, ?> rawMap = serviceSimpleDataProviderTemplate.getOffsetUnitDropdown();
            Map<String, String> resultMap = new HashMap<>();
            Set<?> keySets = rawMap.keySet();
            for (Object key : keySets) {
                resultMap.put(key.toString(), rawMap.get(key).toString());
            }
            return resultMap;
        }
        return null;
    }

    public Map<String, String> getProviderOffsetDirectionTemplate(String providerId) throws ServiceSimpleDataProviderException {
        ServiceSimpleDataProviderTemplate serviceSimpleDataProviderTemplate =
                simpleDataProviderFactory.getSimpleDataProvider(providerId);
        if (serviceSimpleDataProviderTemplate != null) {
            return ServiceCollectionsHelper.convertToStringMap(serviceSimpleDataProviderTemplate.getOffsetDirectionTemplate());
        }
        return null;
    }

    public Map<String, String> getProviderOffsetUnitTemplate(String providerId) throws ServiceSimpleDataProviderException {
        ServiceSimpleDataProviderTemplate serviceSimpleDataProviderTemplate =
                simpleDataProviderFactory.getSimpleDataProvider(providerId);
        if (serviceSimpleDataProviderTemplate != null) {
            return ServiceCollectionsHelper.convertToStringMap(serviceSimpleDataProviderTemplate.getOffsetUnitTemplate());
        }
        return null;
    }

    public List<ServiceEntityNode> getAllHandlerList()
            throws MessageTemplateException {
        List<ServiceEntityNode> resultList = new ArrayList<>();
        List<MessageTemplateHandler> rawMessageHandlerList = messageTemplateHandlerRepository.getAllHandlerList();
        if (ServiceCollectionsHelper.checkNullList(rawMessageHandlerList)) {
            return new ArrayList<>();
        } else {
            for (MessageTemplateHandler messageTemplateHandler : rawMessageHandlerList) {
                resultList.add(packageHandler(messageTemplateHandler));
            }
        }
        return resultList;
    }

    private ServiceEntityNode packageHandler(MessageTemplateHandler messageTemplateHandler) {
        ServiceEntityNode seNode = new GenericServiceEntityNode();
        seNode.setId(messageTemplateHandler.getId());
        seNode.setName(messageTemplateHandler.getName());
        seNode.setNote(messageTemplateHandler.getDescription());
        return seNode;
    }

    /**
     * [Internal method] Convert from UI model to se model:messageTemplate
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToMessageTemplate(
            MessageTemplateUIModel messageTemplateUIModel,
            MessageTemplate rawEntity) {
        DocFlowProxy.convUIToServiceEntityNode(messageTemplateUIModel, rawEntity);
        rawEntity.setSearchDataUrl(messageTemplateUIModel.getSearchDataUrl());
        rawEntity.setProcessIndex(messageTemplateUIModel.getProcessIndex());
        rawEntity.setHandlerClass(messageTemplateUIModel.getHandlerClass());
        rawEntity.setSearchModelClass(messageTemplateUIModel
                .getSearchModelClass());
        rawEntity.setSearchModelName(messageTemplateUIModel.getSearchModelName());
        rawEntity.setNavigationSourceId(messageTemplateUIModel.getNavigationSourceId());
        rawEntity.setReferenceModelName(messageTemplateUIModel.getReferenceModelName());
        rawEntity.setRefProxyConfigUUID(messageTemplateUIModel.getRefProxyConfigUUID());
        rawEntity.setSearchProxyName(messageTemplateUIModel.getSearchProxyName());
        rawEntity.setMessageTitle(messageTemplateUIModel.getMessageTitle());
        rawEntity.setMessageContent(messageTemplateUIModel.getMessageContent());
        rawEntity.setActionCodeArray(messageTemplateUIModel.getActionCodeArray());
    }

    public void postConvMessageTemplateToUI(MessageTemplate messageTemplate,
                                            List<ServiceEntityNode> messageTempPrioritySettingList,
                                            MessageTemplateUIModel messageTemplateUIModel, LogonInfo logonInfo) {
        try {
            List<ServiceEntityNode> homeMessageTempPrioritySettingList = null;
            if(ServiceCollectionsHelper.checkNullList(messageTempPrioritySettingList)){
                homeMessageTempPrioritySettingList =
                        messageTempPrioritySettingList.stream().filter(serviceEntityNode -> {
                            return messageTemplate.getUuid().equals(serviceEntityNode.getRootNodeUUID());
                        }).collect(Collectors.toList());
            }
            MessageTempPrioritySetting messageTempPrioritySetting =
                    getHighestPrioritySetting(messageTemplate.getUuid(), homeMessageTempPrioritySettingList,
                            logonInfo.getClient());
            if (messageTempPrioritySetting != null) {
                messageTemplateUIModel.setMessageTitle(messageTempPrioritySetting.getMessageTitle());
                messageTemplateUIModel.setMessageContent(messageTempPrioritySetting.getMessageContent());
                messageTemplateUIModel.setMessageLevelCode(messageTempPrioritySetting.getMessageLevelCode());
                Map<Integer, String> messageLevelCodeMap =
                        initMessageLevelCodeMap(logonInfo.getLanguageCode());
                messageTemplateUIModel.setMessageLevelCodeValue(messageLevelCodeMap.get(messageTempPrioritySetting.getMessageLevelCode()));
            }
        } catch (ServiceEntityConfigureException | ServiceEntityInstallationException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "convMessageTemplateToUI"));
        }
    }

    /**
     * Get Message Priority setting with highest message level
     *
     * @param baseUUID
     * @param client
     * @return
     * @throws ServiceEntityConfigureException
     */
    public MessageTempPrioritySetting getHighestPrioritySetting(String baseUUID,
                                                                List<ServiceEntityNode> prioritySettingList,
                                                                String client) throws ServiceEntityConfigureException {
        if(ServiceCollectionsHelper.checkNullList(prioritySettingList)){
            prioritySettingList = this.getEntityNodeListByKey(baseUUID,
                    IServiceEntityNodeFieldConstant.PARENTNODEUUID, MessageTempPrioritySetting.NODENAME, client, null);
        }
        if (ServiceEntityStringHelper.checkNullString(baseUUID)) {
            return null;
        }
        if (ServiceCollectionsHelper.checkNullList(prioritySettingList)) {
            return null;
        }
        MessageTempPrioritySetting maxPrioritySetting = (MessageTempPrioritySetting) prioritySettingList.get(0);
        for (ServiceEntityNode seNode : prioritySettingList) {
            MessageTempPrioritySetting messageTempPrioritySetting = (MessageTempPrioritySetting) seNode;
            if (messageTempPrioritySetting.getMessageLevelCode() >= maxPrioritySetting.getMessageLevelCode()) {
                maxPrioritySetting = messageTempPrioritySetting;
            }
        }
        return maxPrioritySetting;
    }

    /**
     * Get Message Priority setting with highest message level
     *
     * @param messageLevel
     * @param prioritySettingList
     * @return
     * @throws ServiceEntityConfigureException
     */
    public MessageTempPrioritySetting getPrioritySettingByLevel(int messageLevel,
                                                                List<ServiceEntityNode> prioritySettingList) throws ServiceEntityConfigureException {

        if (ServiceCollectionsHelper.checkNullList(prioritySettingList)) {
            return null;
        }
        for (ServiceEntityNode seNode : prioritySettingList) {
            MessageTempPrioritySetting messageTempPrioritySetting = (MessageTempPrioritySetting) seNode;
            if (messageTempPrioritySetting.getMessageLevelCode() == messageLevel) {
                return messageTempPrioritySetting;
            }
        }
        return null;
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, messageTemplateDAO));
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(messageTemplateConfigureProxy);
    }

    public static class FieldUnion {

        private String fieldName;

        private String searchModelName;

        public FieldUnion() {
        }

        public FieldUnion(String fieldName, String searchModelName) {
            this.fieldName = fieldName;
            this.searchModelName = searchModelName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getSearchModelName() {
            return searchModelName;
        }

        public void setSearchModelName(String searchModelName) {
            this.searchModelName = searchModelName;
        }
    }

    public List<FieldUnion> getRawSearchFieldList(MessageTemplate messageTemplate) throws ServiceEntityConfigureException, MessageTemplateException {
        try {
            ServiceSearchProxy serviceSearchProxy =
                    serviceSearchProxyRepository.getSearchProxyById(messageTemplate.getSearchProxyName());
            if (serviceSearchProxy == null) {
                throw new MessageTemplateException(MessageTemplateException.PARA_NOFOUND_SEARCHPROXY,
                        messageTemplate.getSearchProxyName());
            }
            List<SearchProxyConfigManager.SearchFieldUnion> rawFieldUnions =
                    searchProxyConfigManager.getRawSearchFieldList(serviceSearchProxy,
                            messageTemplate.getSearchModelName());
            if (ServiceCollectionsHelper.checkNullList(rawFieldUnions)) {
                return null;
            } else {
                List<FieldUnion> fieldUnionList = rawFieldUnions.stream().map(rawFieldUnion -> {
                    FieldUnion fieldUnion = new FieldUnion(rawFieldUnion.getFieldName(),
                            rawFieldUnion.getSearchModelName());
                    return fieldUnion;
                }).collect(Collectors.toList());
                return fieldUnionList;
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new MessageTemplateException(MessageTemplateException.PARA_SYSTEM_ERROR, e.getMessage());
        } catch (SearchProxyConfigureException e) {
            throw new MessageTemplateException(MessageTemplateException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
    }

    public Map<Integer, String> initLogicOperatorMap(String languageCode)
            throws ServiceEntityInstallationException {
        return standardLogicOperatorProxy
                .getLogicOperatorMap(languageCode);
    }

    public Map<Integer, String> initPriorityCodeMap(String languageCode)
            throws ServiceEntityInstallationException {
        return standardPriorityProxy.getPriorityMap(languageCode);
    }

    public Map<Integer, String> initMessageLevelCodeMap(String languageCode)
            throws ServiceEntityInstallationException {
        return messageLevelCodeProxy.getMessageLevelMap(languageCode);
    }

    /**
     * Core Logic to execute search and return message template response list
     *
     * @param uuidList
     * @param logonInfo
     * @return
     * @throws SearchProxyConfigureException
     * @throws MessageTemplateException
     */
    public List<MessageTemplateResponse> executeSearchBatch(List<String> uuidList,
                                                            LogonInfo logonInfo) throws MessageTemplateException {
        try {
            List<ServiceEntityNode> messageTemplateList = this
                    .getEntityNodeListByMultipleKey(uuidList,
                            IServiceEntityNodeFieldConstant.UUID,
                            MessageTemplate.NODENAME, logonInfo.getClient(),
                            null);
            List<ServiceEntityNode> allMessageTempSearchConditionList = this
                    .getEntityNodeListByMultipleKey(uuidList,
                            IServiceEntityNodeFieldConstant.PARENTNODEUUID,
                            MessageTempSearchCondition.NODENAME, logonInfo.getClient(),
                            null);
            List<ServiceEntityNode> allMessageTempPrioritySettingList = this
                    .getEntityNodeListByMultipleKey(uuidList,
                            IServiceEntityNodeFieldConstant.PARENTNODEUUID,
                            MessageTempPrioritySetting.NODENAME, logonInfo.getClient(),
                            null);
            List<MessageTemplateServiceModel> messageTemplateServiceModelList =
                    buildServiceModelOnline(messageTemplateList, allMessageTempSearchConditionList,
                            allMessageTempPrioritySettingList);
            if (ServiceCollectionsHelper.checkNullList(messageTemplateServiceModelList)) {
                return null;
            }
            List<MessageTemplateResponse> resultList = new ArrayList<>();
            for (MessageTemplateServiceModel messageTemplateServiceModel : messageTemplateServiceModelList) {
                Map<Integer, List<ServiceEntityNode>> rawSearchResult;
                MessageTemplate messageTemplate = messageTemplateServiceModel.getMessageTemplate();
                try {
                    rawSearchResult = this.executeSearch(messageTemplateServiceModel,
                            logonInfo);
                } catch (SearchProxyConfigureException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, messageTemplate.getSearchProxyName()));
                    continue;
                }
                if (rawSearchResult == null) {
                    return null;
                }
                Set<Integer> keySets = rawSearchResult.keySet();
                for (Integer key : keySets) {
                    MessageTempPrioritySetting messageTempPrioritySetting = getPrioritySettingByLevel(key,
                            messageTemplateServiceModel.getMessageTempPrioritySettingList().stream()
                                    .map(MessageTempPrioritySettingServiceModel::getMessageTempPrioritySetting).collect(Collectors.toList()));
                    List<ServiceEntityNode> rawSEList = rawSearchResult.get(key);
                    if (ServiceCollectionsHelper.checkNullList(rawSEList)) {
                        continue;
                    }
                    MessageTemplateResponse messageTemplateResponse =
                            new MessageTemplateResponse(messageTemplate.getUuid(), messageTemplate.getId(),
                                    messageTemplate.getName(), messageTemplate.getNavigationSourceId());
                    messageTemplateResponse.setMessageLevelCode(key);
                    int docType = calculateDocType(rawSEList);
                    messageTemplateResponse.setDocumentType(docType);
                    try {
                        Map<Integer, String> docMap = serviceDocumentComProxy
                                .getDocumentTypeMap(false, logonInfo.getLanguageCode());
                        messageTemplateResponse.setDocumentTypeValue(docMap.get(docType));
                    } catch (ServiceEntityInstallationException e) {
                        logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    }
                    messageTemplateResponse.setMessageTitle(messageTemplate.getMessageTitle());
                    messageTemplateResponse.setMessageContent(messageTemplate.getMessageContent());
                    if (messageTempPrioritySetting != null) {
                        if (!ServiceEntityStringHelper.checkNullString(messageTempPrioritySetting.getMessageTitle())) {
                            messageTemplateResponse.setMessageTitle(messageTempPrioritySetting.getMessageTitle());
                        }
                        if (!ServiceEntityStringHelper.checkNullString(messageTempPrioritySetting.getMessageContent())) {
                            messageTemplateResponse.setMessageContent(messageTempPrioritySetting.getMessageContent());
                        }
                        messageTemplateResponse.setActionCode(messageTempPrioritySetting.getActionCode());
                    }
                    try {
                        Map<Integer, String> messageLevelCodeMap =
                                this.initMessageLevelCodeMap(logonInfo.getLanguageCode());
                        if (messageLevelCodeMap != null) {
                            messageTemplateResponse.setMessageLevelCodeValue(messageLevelCodeMap.get(key));
                        }
                    } catch (ServiceEntityInstallationException e) {
                        logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    }
                    messageTemplateResponse.setRawSEList(rawSEList);
                    messageTemplateResponse.setDataNum(ServiceCollectionsHelper.checkNullList(rawSEList) ? 0 :
                            rawSEList.size());
                    resultList.add(messageTemplateResponse);
                }
            }
            return resultList;
        } catch (ServiceEntityConfigureException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new MessageTemplateException(MessageTemplateException.PARA_SYSTEM_ERROR, e.getMessage());
        }
    }

    private List<MessageTemplateServiceModel> buildServiceModelOnline(List<ServiceEntityNode> messageTemplateList,
                                                                      List<ServiceEntityNode> allMessageTempSearchConditionList,
                                                                      List<ServiceEntityNode> allMessageTempPrioritySettingList) {
        List<MessageTemplateServiceModel> messageTemplateServiceModelList = new ArrayList<>();
        if (ServiceCollectionsHelper.checkNullList(messageTemplateList)) {
            return null;
        }
        for (ServiceEntityNode rootNode : messageTemplateList) {
            MessageTemplate messageTemplate = (MessageTemplate) rootNode;
            MessageTemplateServiceModel messageTemplateServiceModel = new MessageTemplateServiceModel();
            messageTemplateServiceModel.setMessageTemplate(messageTemplate);
            if (!ServiceCollectionsHelper.checkNullList(allMessageTempSearchConditionList)) {
                List<ServiceEntityNode> messageTempSearchConditionList =
                        ServiceCollectionsHelper.filterListOnline(allMessageTempSearchConditionList, se -> messageTemplate.getUuid().equals(se.getParentNodeUUID()), false);
                if (!ServiceCollectionsHelper.checkNullList(messageTempSearchConditionList)) {
                    messageTemplateServiceModel.setMessageTempSearchConditionList(messageTempSearchConditionList.stream().map(serviceEntityNode -> {
                        MessageTempSearchConditionServiceModel messageTempSearchConditionServiceModel = new MessageTempSearchConditionServiceModel();
                        messageTempSearchConditionServiceModel.setMessageTempSearchCondition((MessageTempSearchCondition)serviceEntityNode);
                        return messageTempSearchConditionServiceModel;
                    }).collect(Collectors.toList()));
                }
            }
            if (!ServiceCollectionsHelper.checkNullList(allMessageTempPrioritySettingList)) {
                List<ServiceEntityNode> messageTempPrioritySettingList =
                        ServiceCollectionsHelper.filterListOnline(allMessageTempPrioritySettingList, se -> {
                            return messageTemplate.getUuid().equals(se.getParentNodeUUID());
                        }, false);
                if (!ServiceCollectionsHelper.checkNullList(messageTempPrioritySettingList)) {
                    messageTemplateServiceModel.setMessageTempPrioritySettingList(messageTempPrioritySettingList.stream().map(serviceEntityNode -> {
                        MessageTempPrioritySettingServiceModel messageTempPrioritySettingServiceModel = new MessageTempPrioritySettingServiceModel();
                        messageTempPrioritySettingServiceModel.setMessageTempPrioritySetting((MessageTempPrioritySetting)serviceEntityNode);
                        return messageTempPrioritySettingServiceModel;
                    }).collect(Collectors.toList()));
                }
            }
            messageTemplateServiceModelList.add(messageTemplateServiceModel);
        }
        return messageTemplateServiceModelList;
    }

    public Map<Integer, List<ServiceEntityNode>> executeSearch(MessageTemplateServiceModel messageTemplateServiceModel,
                                                               LogonInfo logonInfo) throws SearchProxyConfigureException,
            MessageTemplateException {
        try {
            MessageTemplate messageTemplate = messageTemplateServiceModel.getMessageTemplate();
            ServiceSearchProxy serviceSearchProxy =
                    serviceSearchProxyRepository.getSearchProxyById(messageTemplate.getSearchProxyName());
            if (serviceSearchProxy == null) {
                throw new MessageTemplateException(MessageTemplateException.PARA_NOFOUND_SEARCHPROXY,
                        messageTemplate.getSearchProxyName());
            }
            Map<Integer, List<ServiceEntityNode>> messageListMap = new HashMap<>();
            /*
             * [Step1] Parsing to the search model map with priority
             */
            Map<Integer, SEUIComModel> searchModelMap = parseToSearchModelMap(messageTemplateServiceModel, logonInfo);
            if (searchModelMap != null) {
                for (Integer priorityCode : searchModelMap.keySet()) {
                    SEUIComModel searchModel = searchModelMap.get(priorityCode);
                    if (searchModel == null) {
                        continue;
                    }
                    if (serviceSearchProxy.getDocSearchModelCls() != null) {
                        if (ServiceEntityStringHelper.headerToLowerCase(serviceSearchProxy.getDocSearchModelCls().getSimpleName()).equals(messageTemplate.getSearchModelName())) {
                            try {
                                SearchContextBuilder searchContextBuilder = SearchContextBuilder.genBuilder(logonInfo).searchModel(searchModel);
                                List<ServiceEntityNode> resultList = serviceSearchProxy.searchDocList(searchContextBuilder.build()).getResultList();
                                resultList = postProcessListWrapper(messageTemplateServiceModel, resultList, logonInfo);
                                if (!ServiceCollectionsHelper.checkNullList(resultList)) {
                                    messageListMap.put(priorityCode, resultList);
                                }
                            } catch (SearchConfigureException | AuthorizationException |
                                     ServiceEntityInstallationException | ServiceEntityConfigureException e) {
                                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e,
                                        messageTemplate.getSearchModelName()));
                                throw new MessageTemplateException(MessageTemplateException.PARA_SYSTEM_ERROR,
                                        e.getErrorMessage());
                            } catch (LogonInfoException e) {
                                throw new MessageTemplateException(MessageTemplateException.PARA_SYSTEM_ERROR,
                                        e.getMessage());
                            }
                        }
                    }

                    if (serviceSearchProxy.getMatItemSearchModelCls() != null) {
                        if (ServiceEntityStringHelper.headerToLowerCase(serviceSearchProxy.getMatItemSearchModelCls().getSimpleName()).equals(messageTemplate.getSearchModelName())) {
                            try {

                                SearchContextBuilder searchContextBuilder = SearchContextBuilder.genBuilder(logonInfo).searchModel(searchModel);
                                List<ServiceEntityNode> resultList = serviceSearchProxy.searchItemList(searchContextBuilder.build()).getResultList();
                                resultList = postProcessListWrapper(messageTemplateServiceModel, resultList, logonInfo);
                                if (!ServiceCollectionsHelper.checkNullList(resultList)) {
                                    messageListMap.put(priorityCode, resultList);
                                }
                            } catch (SearchConfigureException | ServiceEntityConfigureException |
                                     ServiceEntityInstallationException | AuthorizationException | LogonInfoException e) {
                                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e,
                                        messageTemplate.getSearchModelName()));
                                throw new MessageTemplateException(MessageTemplateException.PARA_SYSTEM_ERROR,
                                        e.getErrorMessage());
                            }
                        }
                    }
                }
            }
            return messageListMap;
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new MessageTemplateException(MessageTemplateException.PARA_SYSTEM_ERROR,
                    e.getMessage());
        }
    }

    private int calculateDocType(List<ServiceEntityNode> rawSEList){
        ServiceEntityNode serviceEntityNode = rawSEList.get(0);
        return docFlowProxy.getDocumentType(serviceEntityNode);
    }

    private List<ServiceEntityNode> postProcessListWrapper(MessageTemplateServiceModel messageTemplateServiceModel,
                                                           List<ServiceEntityNode> rawSEList, LogonInfo logonInfo) throws MessageTemplateException {
        if(ServiceCollectionsHelper.checkNullList(rawSEList)){
            return null;
        }
        MessageTemplate messageTemplate = messageTemplateServiceModel.getMessageTemplate();
        // filter out by doc flow
        List<ServiceEntityNode> resultList = rawSEList.stream().filter(serviceEntityNode -> {
            int documentType = docFlowProxy.getDocumentType(serviceEntityNode);
            String businessKey = ServiceFlowRuntimeEngine.encodeBusinessKey(documentType, serviceEntityNode.getUuid());
            ServiceFlowRuntimeEngine.InvolveTaskResult involveTaskResult =
                    serviceFlowRuntimeEngine.checkInvolveTaskResult(businessKey, logonInfo.getRefUserUUID());
            return !(involveTaskResult.getInvolveTaskStatus() == ServiceFlowInvolveTaskProxy.STATUS_BLOCK_OTHER);
        }).collect(Collectors.toList());
        if(ServiceCollectionsHelper.checkNullList(resultList)){
            return null;
        }
        if(ServiceEntityStringHelper.checkNullString(messageTemplate.getHandlerClass())){
            return resultList;
        }
        MessageTemplateHandler messageTemplateHandler =
                null;
        try {
            messageTemplateHandler = messageTemplateHandlerRepository.getHandlerById(messageTemplate.getHandlerClass());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, messageTemplate.getHandlerClass()));
            throw new MessageTemplateException(MessageTemplateException.PARA_SYSTEM_ERROR,
                    ServiceEntityStringHelper.genDefaultErrorMessage(e, messageTemplate.getHandlerClass()));
        }
        if(messageTemplateHandler == null){
            return resultList;
        }
        return messageTemplateHandler.postProcessSEDataList(messageTemplateServiceModel, resultList, logonInfo);
    }

    /**
     * Logic to parse template into each Search Model
     *
     * @param messageTemplateServiceModel
     * @return
     */
    public Map<Integer, SEUIComModel> parseToSearchModelMap(MessageTemplateServiceModel messageTemplateServiceModel,
                                                            LogonInfo logonInfo) throws SearchProxyConfigureException,
            MessageTemplateException {
        /*
         * [Step1] Get & initial instance of search model
         */
        MessageTemplate messageTemplate = messageTemplateServiceModel.getMessageTemplate();
        Class<?> searchModelCls = searchProxyConfigManager.getSearchModelCls(messageTemplate.getSearchProxyName(),
                messageTemplate.getSearchModelName());
        if (searchModelCls == null) {
            throw new MessageTemplateException(MessageTemplateException.PARA_NOFOUND_SEARCHMODEL,
                    messageTemplate.getSearchModelName());
        }
        SEUIComModel searchModel = null;
        try {
            searchModel = (SEUIComModel) searchModelCls.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new MessageTemplateException(MessageTemplateException.PARA_SYSTEM_ERROR,
                    e.getMessage());
        }
        /*
         * [Step2] Build Map according to priority
         */
        List<MessageTempPrioritySettingServiceModel> messagePrioritySettingList =
                messageTemplateServiceModel.getMessageTempPrioritySettingList();
        if (ServiceCollectionsHelper.checkNullList(messagePrioritySettingList)) {
            return null;
        }
        Map<Integer, List<ServiceEntityNode>> messageLevelCodeMap = new HashMap<>();
        for (MessageTempPrioritySettingServiceModel messageTempPrioritySettingServiceModel : messagePrioritySettingList) {
            MessageTempPrioritySetting messageTempPrioritySetting = messageTempPrioritySettingServiceModel.getMessageTempPrioritySetting();
            if (messageLevelCodeMap.containsKey(messageTempPrioritySetting.getMessageLevelCode())) {
                List<ServiceEntityNode> tempPriorityList =
                        messageLevelCodeMap.get(messageTempPrioritySetting.getMessageLevelCode());
                tempPriorityList.add(messageTempPrioritySetting);
                messageLevelCodeMap.put(messageTempPrioritySetting.getMessageLevelCode(),
                        tempPriorityList);
            } else {
                messageLevelCodeMap.put(messageTempPrioritySetting.getMessageLevelCode(),
                        ServiceCollectionsHelper.asList(messageTempPrioritySetting));
            }
        }

        /*
         * [Step3] Parse and update common search condition to search model
         */
        List<MessageTempSearchConditionServiceModel> messageTempSearchConditionList =
                messageTemplateServiceModel.getMessageTempSearchConditionList();
        if (!ServiceCollectionsHelper.checkNullList(messageTempSearchConditionList)) {
            for (MessageTempSearchConditionServiceModel messageTempSearchConditionServiceModel : messageTempSearchConditionList) {
                try {
                    updateSearchModelWithSearchCondition(searchModel, messageTempSearchConditionServiceModel.getMessageTempSearchCondition(), logonInfo);
                } catch (ServiceSimpleDataProviderException e) {
                    throw new SearchProxyConfigureException(SearchProxyConfigureException.PARA_SYSTEM_ERROR,
                            e.getErrorMessage());
                }
            }
        }
        /*
         * [Step4] Parse and each priority setting to search model
         */
        Set<Integer> keySet = messageLevelCodeMap.keySet();
        Iterator<Integer> it = keySet.iterator();
        Map<Integer, SEUIComModel> searchModelMap = new HashMap<>();
        while (it.hasNext()) {
            Integer messageLevelCode = it.next();
            List<ServiceEntityNode> tempPriorityList =
                    messageLevelCodeMap.get(messageLevelCode);
            SEUIComModel searchModelCopy = (SEUIComModel) searchModel.clone();
            if (!ServiceCollectionsHelper.checkNullList(tempPriorityList)) {
                for (ServiceEntityNode seNode : tempPriorityList) {
                    try {
                        updateSearchModelWithSearchPriority(searchModelCopy, (MessageTempPrioritySetting) seNode,
                                logonInfo);
                    } catch (ServiceSimpleDataProviderException e) {
                        throw new SearchProxyConfigureException(SearchProxyConfigureException.PARA_SYSTEM_ERROR,
                                e.getErrorMessage());
                    }
                }
            }
            searchModelMap.put(messageLevelCode, searchModelCopy);
        }
        return searchModelMap;
    }

    public void updateSearchModelWithSearchCondition(SEUIComModel searchModel,
                                                     MessageTempSearchCondition messageTempSearchCondition,
                                                     LogonInfo logonInfo) throws SearchProxyConfigureException,
            ServiceSimpleDataProviderException, MessageTemplateException {
        SearchFieldConfig searchFieldConfig = new SearchFieldConfig();
        String fieldName = messageTempSearchCondition.getFieldName();
        searchFieldConfig.setFieldName(fieldName);
        Field field = searchProxyConfigManager.getSearchField(searchFieldConfig, searchModel.getClass());
        if (field == null) {
            return;
        }
        field.setAccessible(true);
        if (!ServiceEntityStringHelper.checkNullString(messageTempSearchCondition.getDataSourceProviderId())) {
            ServiceSimpleDataProviderTemplate dataProviderTemplate = simpleDataProviderFactory
                    .getSimpleDataProvider(messageTempSearchCondition.getDataSourceProviderId());
            if (dataProviderTemplate == null) {
                // should raise exception
                throw new ServiceSimpleDataProviderException(ServiceSimpleDataProviderException.PARA_NOFOUND_DATAPROVIDER_BYID,
                        messageTempSearchCondition.getDataSourceProviderId());
            }
            SimpleDataOffsetUnion simpleDataOffsetUnion = new SimpleDataOffsetUnion();
            simpleDataOffsetUnion
                    .setOffsetDirection(messageTempSearchCondition
                            .getDataOffsetDirection());
            simpleDataOffsetUnion.setOffsetUnit(messageTempSearchCondition
                    .getDataOffsetUnit());
            simpleDataOffsetUnion.setOffsetValue(messageTempSearchCondition
                    .getDataOffsetValue());
            Object value = dataProviderTemplate.getResultData(simpleDataOffsetUnion, logonInfo);
            try {
                reflectiveSetValue(searchModel, field, fieldName, value,null);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException |
                     InstantiationException | NoSuchFieldException e) {
                throw new MessageTemplateException(MessageTemplateException.PARA_SYSTEM_ERROR, e.getMessage());
            }
        } else {
            if (messageTempSearchCondition.getFieldValue() != null) {
                try {
                    reflectiveSetValue(searchModel, field, fieldName, messageTempSearchCondition.getFieldValue(),null);
                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException |
                         InstantiationException | NoSuchFieldException e) {
                    throw new MessageTemplateException(MessageTemplateException.PARA_SYSTEM_ERROR, e.getMessage());
                }
            }
        }
    }

    private void reflectiveSetValue(SEUIComModel searchModel, Field field, String fieldName, Object value, SimpleDateFormat dateFormat)
            throws IllegalAccessException, SearchProxyConfigureException, NoSuchMethodException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        String parentFieldName = ServiceEntityStringHelper.getParentSubStringByDot(fieldName);
        if (ServiceEntityStringHelper.checkNullString(parentFieldName)) {
            // In case the field is on the root node of the search Model
            ServiceReflectiveHelper.reflectSetValue(field, searchModel,
                    value, dateFormat);
        } else {
            // In case the field is on the sub node of the search Model
            Field parentModelField = ServiceEntityFieldsHelper.getServiceField(searchModel.getClass(), parentFieldName);
            if (parentModelField != null) {
                parentModelField.setAccessible(true);
                Object parentModelIns = parentModelField.get(searchModel);
                if (parentModelIns == null) {
                    // in case the parent model is not initialized
                    parentModelIns = parentModelField.getType().getDeclaredConstructor().newInstance();
                    parentModelField.set(searchModel, parentModelIns);
                }
                // In case the field is on the root node of the search Model
                ServiceReflectiveHelper.reflectSetValue(field, parentModelIns,
                        value, dateFormat);
            }
        }
    }

    public void updateSearchModelWithSearchPriority(SEUIComModel searchModel,
                                                    MessageTempPrioritySetting messageTempPrioritySetting,
                                                    LogonInfo logonInfo) throws SearchProxyConfigureException,
            ServiceSimpleDataProviderException, MessageTemplateException {
        SearchFieldConfig searchFieldConfig = new SearchFieldConfig();
        String fieldName = messageTempPrioritySetting.getFieldName();
        searchFieldConfig.setFieldName(fieldName);
        Field field = searchProxyConfigManager.getSearchField(searchFieldConfig, searchModel.getClass());
        if (field == null) {
            return;
        }
        field.setAccessible(true);
        if (!ServiceEntityStringHelper.checkNullString(messageTempPrioritySetting.getDataSourceProviderId())) {
            ServiceSimpleDataProviderTemplate dataProviderTemplate = simpleDataProviderFactory
                    .getSimpleDataProvider(messageTempPrioritySetting.getDataSourceProviderId());
            if (dataProviderTemplate == null) {
                // should raise exception
                throw new ServiceSimpleDataProviderException(ServiceSimpleDataProviderException.PARA_NOFOUND_DATAPROVIDER_BYID,
                        messageTempPrioritySetting.getDataSourceProviderId());
            }
            SimpleDataOffsetUnion simpleDataOffsetUnion = new SimpleDataOffsetUnion();
            simpleDataOffsetUnion
                    .setOffsetDirection(messageTempPrioritySetting
                            .getDataOffsetDirection());
            simpleDataOffsetUnion.setOffsetUnit(messageTempPrioritySetting
                    .getDataOffsetUnit());
            simpleDataOffsetUnion.setOffsetValue(messageTempPrioritySetting
                    .getDataOffsetValue());
            Object value = dataProviderTemplate.getResultData(simpleDataOffsetUnion, logonInfo);
            try {
                reflectiveSetValue(searchModel, field, fieldName, value,null);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException |
                     InstantiationException | NoSuchFieldException e) {
                throw new MessageTemplateException(MessageTemplateException.PARA_SYSTEM_ERROR, e.getMessage());
            }
        } else {
            // In case direct value
            if (messageTempPrioritySetting.getFieldValue() != null) {
                try {
                    reflectiveSetValue(searchModel, field, fieldName, messageTempPrioritySetting.getFieldValue(),null);
                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException |
                         InstantiationException | NoSuchFieldException e) {
                    throw new MessageTemplateException(MessageTemplateException.PARA_SYSTEM_ERROR, e.getMessage());
                }
            }
        }
    }

    @Override
    public ServiceSearchProxy getSearchProxy() {
        return messageTemplateSearchProxy;
    }
}
