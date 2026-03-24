package com.company.IntelligentPlatform.common.service;

import java.lang.reflect.Field;
import java.util.*;

import jakarta.annotation.PostConstruct;

import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.SearchFieldConfigUIModel;
import com.company.IntelligentPlatform.common.dto.SearchProxyConfigUIModel;
import com.company.IntelligentPlatform.common.controller.SEUIModelFieldsHelper;
// TODO-DAO: import ...SearchProxyConfigDAO;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.StandardCheckStatusProxy;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.ServiceSimpleDataProviderTemplate;
import com.company.IntelligentPlatform.common.service.SimpleDataProviderFactory;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SearchProxyConfigConfigureProxy;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SearchFieldConfig;
import com.company.IntelligentPlatform.common.model.SearchProxyConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
@Transactional
public class SearchProxyConfigManager extends ServiceEntityManager {

    public static final String METHOD_ConvSearchProxyConfigToUI = "convSearchProxyConfigToUI";

    public static final String METHOD_ConvUIToSearchProxyConfig = "convUIToSearchProxyConfig";

    public static final String METHOD_ConvProxyToFieldUI = "convProxyToFieldUI";

    public static final String METHOD_ConvSearchFieldConfigToUI = "convSearchFieldConfigToUI";

    public static final String METHOD_ConvUIToSearchFieldConfig = "convUIToSearchFieldConfig";

    private Map<Integer, String> documentTypeMap;

    private Map<Integer, String> proxyTypeMap;

    private Map<Integer, String> refSelectTypeMap;

    private Map<Integer, String> categoryMap;

    private Map<Integer, String> configureSearchLogicFlagMap;

    @Autowired
    protected SearchProxyConfigSearchProxy searchProxyConfigSearchProxy;

    // TODO-DAO: @Autowired

    // TODO-DAO:     protected SearchProxyConfigDAO searchProxyConfigDAO;

    @Autowired
    protected SearchProxyConfigConfigureProxy searchProxyConfigConfigureProxy;

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected StandardSwitchProxy standardSwitchProxy;

    @Autowired
    protected SpringContextBeanService springContextBeanService;

    @Autowired
    protected StandardCheckStatusProxy standardCheckStatusProxy;

    @Autowired
    protected SimpleDataProviderFactory simpleDataProviderFactory;

    @Autowired
    protected ServiceSearchProxyRepository serviceSearchProxyRepository;

    protected Logger logger = LoggerFactory.getLogger(SearchProxyConfigManager.class);

    public static class SearchModelProxyUnion{

        private String modelId;

        private String modelName;

        private String proxyId;

        private String proxyNme;

        public SearchModelProxyUnion() {
        }

        public SearchModelProxyUnion(String modelId, String modelName, String proxyId, String proxyNme) {
            this.modelId = modelId;
            this.modelName = modelName;
            this.proxyId = proxyId;
            this.proxyNme = proxyNme;
        }

        public String getModelId() {
            return modelId;
        }

        public void setModelId(String modelId) {
            this.modelId = modelId;
        }

        public String getModelName() {
            return modelName;
        }

        public void setModelName(String modelName) {
            this.modelName = modelName;
        }

        public String getProxyId() {
            return proxyId;
        }

        public void setProxyId(String proxyId) {
            this.proxyId = proxyId;
        }

        public String getProxyNme() {
            return proxyNme;
        }

        public void setProxyNme(String proxyNme) {
            this.proxyNme = proxyNme;
        }
    }

    public static class SearchModelNameUnion {

        private String lanKey;

        private String name;

        public SearchModelNameUnion(String lanKey, String name) {
            this.lanKey = lanKey;
            this.name = name;
        }

        public String getLanKey() {
            return lanKey;
        }

        public void setLanKey(String lanKey) {
            this.lanKey = lanKey;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * Online class to manage search model name array
     */
    public static class SearchModelNameMap {

        private String modelName;

        private final List<SearchModelNameUnion> searchModelNameUnionList;

        public SearchModelNameMap(String modelName, List<SearchModelNameUnion> searchModelNameUnionList) {
            this.modelName = modelName;
            this.searchModelNameUnionList = searchModelNameUnionList;
        }

        public String getModelName() {
            return modelName;
        }

        public void setModelName(String modelName) {
            this.modelName = modelName;
        }

    }

    /**
     * Online class to manage raw search fields
     */
    public static class SearchFieldUnion {

        private String fieldName;

        private Field searchField;

        private String searchModelName;

        public SearchFieldUnion() {

        }

        public SearchFieldUnion(String fieldName, Field searchField, String searchModelName) {
            this.fieldName = fieldName;
            this.searchField = searchField;
            this.searchModelName = searchModelName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public Field getSearchField() {
            return searchField;
        }

        public void setSearchField(Field searchField) {
            this.searchField = searchField;
        }

        public String getSearchModelName() {
            return searchModelName;
        }

        public void setSearchModelName(String searchModelName) {
            this.searchModelName = searchModelName;
        }
    }

    public void preUpdate(SearchProxyConfigServiceModel searchProxyConfigServiceModel, LogonInfo logonInfo) throws IllegalAccessException, NoSuchFieldException, SearchProxyConfigureException {
        /*
         * [Step1] initial model Name Json if value is null
         */
        SearchProxyConfig searchProxyConfig = searchProxyConfigServiceModel.getSearchProxyConfig();
        if (ServiceEntityStringHelper.checkNullString(searchProxyConfig.getModelNameJson())) {
            try {
                Map<String, Map<String, String>> allModelNameMap = new HashMap<>();
                allModelNameMap.put(ServiceLanHelper.formatLanCode(logonInfo.getLanguageCode()),
                        serviceSearchProxyRepository.loadServiceModelMap(logonInfo.getLanguageCode()));
                List<SearchModelNameMap> searchModelNameMapList = this.initSearchModelNameMap(searchProxyConfig, allModelNameMap);
                String modelNameJson = parseArray(searchModelNameMapList);
                searchProxyConfig.setModelNameJson(modelNameJson);
            } catch (ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "preUpdate"));
            }
        }
    }

    public String parseArray(Object object) {
        JSONArray jsonArray = JSONArray.fromObject(object);
        return jsonArray.toString();
    }

    /**
     * initialize search field union model from newly searchProxyConfigure
     *
     * @param searchProxyConfig
     * @return
     */
    public List<SearchModelNameMap> initSearchModelNameMap(SearchProxyConfig searchProxyConfig,
                                                           Map<String, Map<String, String>> allSearchModelNameMap) throws SearchProxyConfigureException {
        try {
            ServiceSearchProxy serviceSearchProxy =
                    serviceSearchProxyRepository.getSearchProxyById(searchProxyConfig.getSearchProxyName());
            if (serviceSearchProxy == null) {
                throw new SearchProxyConfigureException(SearchProxyConfigureException.PARA_NOFOUND_PROXY,
                        searchProxyConfig.getSearchProxyName());
            }
            List<SearchModelNameMap> resultList = new ArrayList<>();
            SearchModelNameMap searchModelNameMap = genSearchModelNameMap(serviceSearchProxy.getDocSearchModelCls(),
                    allSearchModelNameMap);
            if (searchModelNameMap != null) {
                resultList.add(searchModelNameMap);
            }
            SearchModelNameMap searchItemModelNameMap =
                    genSearchModelNameMap(serviceSearchProxy.getMatItemSearchModelCls(), allSearchModelNameMap);
            if (searchItemModelNameMap != null) {
                resultList.add(searchItemModelNameMap);
            }
            return resultList;
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new SearchProxyConfigureException(SearchProxyConfigureException.PARA_SYSTEM_ERROR, e.getMessage());
        }
    }

    private SearchModelNameMap genSearchModelNameMap(Class<?> searchModel,
                                                     Map<String, Map<String, String>> allSearchModelNameMap) {
        if (searchModel != null) {
            List<SearchModelNameUnion> searchModelNameUnionList = new ArrayList<>();
            List<Locale> localeList = ServiceLanHelper.getDefLanList();
            for (Locale locale : localeList) {
                String searchModelName = getNameByKey(allSearchModelNameMap, locale, searchModel.getSimpleName());
                searchModelNameUnionList.add(new SearchModelNameUnion(ServiceLanHelper.getLocaleKeyStr(locale),
                        searchModel.getSimpleName()));
            }
            return new SearchModelNameMap(searchModel.getSimpleName(),
                    searchModelNameUnionList);
        } else {
            return null;
        }
    }

    private String getNameByKey(Map<String, Map<String, String>> allSearchModelNameMap, Locale locale, String key) {
        String lanCode = ServiceLanHelper.getLocaleKeyStr(locale);
        if (allSearchModelNameMap.containsKey(lanCode)) {
            Map<String, String> searchModelNameMap = allSearchModelNameMap.get(lanCode);
            return searchModelNameMap.getOrDefault(key, key);
        } else {
            return null;
        }
    }

    public void checkBeanValid(String beanName)
            throws NoSuchBeanDefinitionException {
        springContextBeanService.checkBeanValid(beanName);
    }

    /**
     * Logic to provide possible data provider instance list
     * @param searchFieldConfig
     * @param searchModelCls
     * @return
     * @throws SearchProxyConfigureException
     */
    public List<ServiceSimpleDataProviderTemplate> getDataProvider(SearchFieldConfig searchFieldConfig,
                                                                   Class<?> searchModelCls) throws SearchProxyConfigureException{
        Field field = getSearchField(searchFieldConfig, searchModelCls);
        if(field == null){
            throw new SearchProxyConfigureException(SearchProxyConfigureException.PARA_NOFOUND_PROXY,
                    searchFieldConfig.getFieldName());
        }
        BSearchFieldConfig iSearchConfigure = field
                .getAnnotation(BSearchFieldConfig.class);
        return simpleDataProviderFactory.getDefSimpleDataProvider(new SimpleDataProviderFactory.DataProviderInput(field.getType(), field.getName(), null));
    }

    public Field getSearchField(SearchFieldConfig searchFieldConfig, Class<?> searchModelCls) throws SearchProxyConfigureException {
        String fieldName = searchFieldConfig.getFieldName();
        String[] stringArray = fieldName.split("\\.");
        try {
            if(stringArray.length == 1){
                // in case standard search field
                return getSearchFieldByFieldName(fieldName, searchModelCls);
            } else {
                // Trying to get in group field
                String firstFieldName = stringArray[0];
                Field groupField = ServiceEntityFieldsHelper.getServiceField(searchModelCls, firstFieldName);
                BSearchGroupConfig bSearchGroupConfig = groupField.getAnnotation(BSearchGroupConfig.class);
                if (bSearchGroupConfig != null) {
                    String subFieldName = stringArray[1];
                    return getSearchFieldByFieldName(subFieldName, groupField.getType());
                }
                return null;
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new SearchProxyConfigureException(SearchProxyConfigureException.PARA_NOFOUND_PROXY, fieldName);
        }
    }

    /**
     * Internal method: logic to get field by field name from search model or sub embedded search model
     * @param fieldName
     * @param modelCls
     * @return
     */
    public Field getSearchFieldByFieldName(String fieldName, Class<?> modelCls) throws SearchProxyConfigureException {
        try {
            Field field = ServiceEntityFieldsHelper.getServiceField(modelCls, fieldName);
            field.setAccessible(true);
            BSearchFieldConfig iSearchConfigure = field
                    .getAnnotation(BSearchFieldConfig.class);
            if (iSearchConfigure == null) {
                return null;
            } else {
                return field;
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new SearchProxyConfigureException(SearchProxyConfigureException.PARA_NOFOUND_SEARCHFIELD, fieldName);
        }
    }

    public void checkSearchFieldValidBatch(SearchProxyConfig searchProxyConfig,
                                           List<SearchFieldConfigUIModel> searchFieldConfigUIModelLists,
                                           LogonInfo logonInfo) throws SearchProxyConfigureException {
        List<SearchProxyConfigManager.SearchFieldUnion> searchFieldUnionList = getRawSearchFieldList(searchProxyConfig, null);
        if(ServiceCollectionsHelper.checkNullList(searchFieldConfigUIModelLists)){
            return;
        }
        for(SearchFieldConfigUIModel searchFieldConfigUIModel: searchFieldConfigUIModelLists){
            List<SearchFieldUnion> tmpFieldUnionList =
                    ServiceCollectionsHelper.filterListOnline(searchFieldUnionList, tmpFieldUnion->{
                        return searchFieldConfigUIModel.getFieldName().equals(tmpFieldUnion.getFieldName());
                    }, false);
            if(ServiceCollectionsHelper.checkNullList(tmpFieldUnionList)){
                searchFieldConfigUIModel.setCheckFieldStatus(StandardCheckStatusProxy.ERROR);
            } else {
                searchFieldConfigUIModel.setCheckFieldStatus(StandardCheckStatusProxy.OK);
            }
            if(logonInfo != null){
                try {
                    Map<Integer, String> checkStatusMap =
                            standardCheckStatusProxy.getCheckStatusMap(logonInfo.getLanguageCode());
                    searchFieldConfigUIModel.setCheckFieldStatusValue(checkStatusMap.get(searchFieldConfigUIModel.getCheckFieldStatus()));
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "checkStatus"));
                }
            }
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convSearchProxyConfigToUI(SearchProxyConfig searchProxyConfig,
                                          SearchProxyConfigUIModel searchProxyConfigUIModel) {
        if (searchProxyConfig != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(searchProxyConfig, searchProxyConfigUIModel);
            searchProxyConfigUIModel.setSearchModelName(searchProxyConfig
                    .getSearchModelName());
            searchProxyConfigUIModel.setSearchProxyName(searchProxyConfig
                    .getSearchProxyName());
            searchProxyConfigUIModel.setSearchItemMethodName(searchProxyConfig
                    .getSearchItemMethodName());
            searchProxyConfigUIModel.setSearchDocMethodName(searchProxyConfig
                    .getSearchDocMethodName());
            searchProxyConfigUIModel.setServiceManagerName(searchProxyConfig
                    .getServiceManagerName());
            try {
                this.initDocumentTypeMap();
                searchProxyConfigUIModel
                        .setDocumentTypeValue(this.documentTypeMap
                                .get(searchProxyConfig.getDocumentType()));
            } catch (ServiceEntityInstallationException e) {
                // continue;
            }
            searchProxyConfigUIModel.setDocumentType(searchProxyConfig
                    .getDocumentType());
            try {
                this.initProxyTypeMap();
                searchProxyConfigUIModel.setProxyTypeValue(this.proxyTypeMap
                        .get(searchProxyConfig.getProxyType()));
            } catch (ServiceEntityInstallationException e) {

            }
            try {
                initConfigureSearchLogicFlagMap();
                searchProxyConfigUIModel
                        .setConfigureSearchLogicFlagValue(this.proxyTypeMap
                                .get(searchProxyConfig
                                        .getConfigureSearchLogicFlag()));
            } catch (ServiceEntityInstallationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            searchProxyConfigUIModel.setProxyType(searchProxyConfig
                    .getProxyType());

        }
    }

    /**
     * [Internal method] Convert from UI model to se model:searchProxyConfig
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToSearchProxyConfig(
            SearchProxyConfigUIModel searchProxyConfigUIModel,
            SearchProxyConfig rawEntity) {
        DocFlowProxy.convUIToServiceEntityNode(searchProxyConfigUIModel, rawEntity);
        rawEntity.setSearchModelName(searchProxyConfigUIModel
                .getSearchModelName());
        rawEntity.setUuid(searchProxyConfigUIModel.getUuid());
        rawEntity.setSearchProxyName(searchProxyConfigUIModel
                .getSearchProxyName());
        rawEntity.setSearchItemMethodName(searchProxyConfigUIModel
                .getSearchItemMethodName());
        rawEntity.setId(searchProxyConfigUIModel.getId());
        rawEntity.setSearchDocMethodName(searchProxyConfigUIModel
                .getSearchDocMethodName());
        rawEntity.setClient(searchProxyConfigUIModel.getClient());
        rawEntity.setServiceManagerName(searchProxyConfigUIModel
                .getServiceManagerName());
        rawEntity.setName(searchProxyConfigUIModel.getName());
        rawEntity.setConfigureSearchLogicFlag(searchProxyConfigUIModel
                .getConfigureSearchLogicFlag());
        rawEntity.setNote(searchProxyConfigUIModel.getNote());
        rawEntity.setDocumentType(searchProxyConfigUIModel.getDocumentType());
        rawEntity.setProxyType(searchProxyConfigUIModel.getProxyType());
    }

    public Map<Integer, String> initConfigureSearchLogicFlagMap()
            throws ServiceEntityInstallationException {
        if (this.configureSearchLogicFlagMap == null) {
            this.configureSearchLogicFlagMap = standardSwitchProxy
                    .getSimpleSwitchMap();
        }
        return this.configureSearchLogicFlagMap;
    }

    public Map<Integer, String> initDocumentTypeMap()
            throws ServiceEntityInstallationException {
        if (this.documentTypeMap == null) {
            this.documentTypeMap = serviceDocumentComProxy
                    .getSearchDocumentTypeMap();
        }
        return this.documentTypeMap;
    }

    public Map<Integer, String> initProxyTypeMap()
            throws ServiceEntityInstallationException {
        if (this.proxyTypeMap == null) {
            this.proxyTypeMap = serviceDropdownListHelper.getUIDropDownMap(
                    SearchProxyConfigUIModel.class, "proxyType");
        }
        return this.proxyTypeMap;
    }

    public Map<Integer, String> initRefSelectTypeMap()
            throws ServiceEntityInstallationException {
        if (this.refSelectTypeMap == null) {
            this.refSelectTypeMap = serviceDropdownListHelper.getUIDropDownMap(
                    SearchFieldConfigUIModel.class, "refSelectType");
        }
        return this.refSelectTypeMap;
    }

    public Map<Integer, String> initCategoryMap()
            throws ServiceEntityInstallationException {
        if (this.categoryMap == null) {
            this.categoryMap = serviceDropdownListHelper.getUIDropDownMap(
                    SearchFieldConfigUIModel.class, "category");
        }
        return this.categoryMap;
    }


    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convSearchFieldConfigToUI(SearchFieldConfig searchFieldConfig,
                                          SearchFieldConfigUIModel searchFieldConfigUIModel) {
        if (searchFieldConfig != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(searchFieldConfig, searchFieldConfigUIModel);
            searchFieldConfigUIModel.setFieldName(searchFieldConfig
                    .getFieldName());
            searchFieldConfigUIModel.setRefSelectURL(searchFieldConfig
                    .getRefSelectURL());
            searchFieldConfigUIModel.setRefSelectType(searchFieldConfig
                    .getRefSelectType());
            try {
                this.initRefSelectTypeMap();
                searchFieldConfigUIModel
                        .setRefSelectTypeValue(this.refSelectTypeMap
                                .get(searchFieldConfig.getRefSelectType()));
            } catch (ServiceEntityInstallationException e) {
                // continue;
            }
            searchFieldConfigUIModel.setCategory(searchFieldConfig
                    .getCategory());
            try {
                this.initCategoryMap();
                searchFieldConfigUIModel.setCategoryValue(this.categoryMap
                        .get(searchFieldConfig.getCategory()));
            } catch (ServiceEntityInstallationException e) {
                // continue;
            }
        }
    }

    public void convProxyToFieldUI(SearchProxyConfig searchProxyConfig,
                                   SearchFieldConfigUIModel searchFieldConfigUIModel) {
        if (searchProxyConfig != null) {
            searchFieldConfigUIModel.setProxyId(searchProxyConfig.getId());
        }

    }

    /**
     * [Internal method] Convert from UI model to se model:searchFieldConfig
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToSearchFieldConfig(
            SearchFieldConfigUIModel searchFieldConfigUIModel,
            SearchFieldConfig rawEntity) {
        DocFlowProxy.convUIToServiceEntityNode(searchFieldConfigUIModel, rawEntity);
        rawEntity.setFieldName(searchFieldConfigUIModel.getFieldName());
        rawEntity.setRefSelectURL(searchFieldConfigUIModel.getRefSelectURL());
        rawEntity.setRefSelectType(searchFieldConfigUIModel.getRefSelectType());
        rawEntity.setCategory(searchFieldConfigUIModel.getCategory());
    }

    /**
     * Get all the possible search model list as well as proxy information
     * @param logonInfo
     * @return
     * @throws SearchProxyConfigureException
     */
    public List<SearchModelProxyUnion> getAllSearchModelList(LogonInfo logonInfo) throws SearchProxyConfigureException {
        try {
            Map<String, String> serviceProxyConfigMap = serviceSearchProxyRepository.loadServiceProxyMap(logonInfo.getLanguageCode());
            Map<String, String> searchModelNameMap = serviceSearchProxyRepository.loadServiceModelMap(logonInfo.getLanguageCode());
            List<ServiceSearchProxy> allSearchProxyList = serviceSearchProxyRepository.getAllSearchProxyList();
            if(ServiceCollectionsHelper.checkNullList(allSearchProxyList)){
                return null;
            }
            List<SearchModelProxyUnion> resultList = new ArrayList<>();
            for(ServiceSearchProxy serviceSearchProxy: allSearchProxyList){
                List<Class<?>> rawSearchModelClsList = getAllSearchModelCls(serviceSearchProxy);
                if(ServiceCollectionsHelper.checkNullList(rawSearchModelClsList)){
                    continue;
                }
                for(Class<?> searchModelCls: rawSearchModelClsList){
                    String modelId = ServiceEntityStringHelper.headerToLowerCase(searchModelCls.getSimpleName());
                    String proxyId = ServiceEntityStringHelper.headerToLowerCase(serviceSearchProxy.getClass().getSimpleName());
                    SearchModelProxyUnion searchModelProxyUnion =
                            new SearchModelProxyUnion(modelId, searchModelNameMap.get(modelId), proxyId,
                                    serviceProxyConfigMap.get(proxyId));
                    resultList.add(searchModelProxyUnion);
                }
            }
            return resultList;
        } catch (ServiceEntityInstallationException e) {
           throw new SearchProxyConfigureException(SearchProxyConfigureException.PARA_SYSTEM_ERROR, e.getMessage());
        }
    }

    /**
     * Get Search model class by serviceSearchProxy id and search model name
     * @param serviceSearchProxyId
     * @param modelId
     * @return
     */
    public Class<?> getSearchModelCls(String serviceSearchProxyId, String modelId) throws SearchProxyConfigureException {
        try {
            ServiceSearchProxy serviceSearchProxy = serviceSearchProxyRepository.getSearchProxyById(serviceSearchProxyId);
            List<Class<?>> rawSearchModelClsList = getAllSearchModelCls(serviceSearchProxy);
            if(ServiceCollectionsHelper.checkNullList(rawSearchModelClsList)){
                return null;
            }
            for(Class<?> searchModelCls: rawSearchModelClsList){
                String tempModelId = ServiceEntityStringHelper.headerToLowerCase(searchModelCls.getSimpleName());
                if(modelId.equals(tempModelId)){
                    return searchModelCls;
                }
            }
            return null;
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new SearchProxyConfigureException(SearchProxyConfigureException.PARA_NOFOUND_PROXY,
                    serviceSearchProxyId);
        }
    }

    /**
     * Provide all the available search model under current search Proxy configure
     * @param searchProxyConfig
     * @return
     */
    public List<Class<?>> getRawSearchModelList(SearchProxyConfig searchProxyConfig) throws SearchProxyConfigureException {
        try {
            ServiceSearchProxy serviceSearchProxy =
                    serviceSearchProxyRepository.getSearchProxyById(searchProxyConfig.getSearchProxyName());
            if (serviceSearchProxy == null) {
                throw new SearchProxyConfigureException(SearchProxyConfigureException.PARA_NOFOUND_PROXY,
                        searchProxyConfig.getSearchProxyName());
            }
            return getAllSearchModelCls(serviceSearchProxy);
        } catch (IllegalAccessException | NoSuchFieldException e) {
           throw new SearchProxyConfigureException(SearchProxyConfigureException.PARA_SYSTEM_ERROR, e.getMessage());
        }
    }

    private List<Class<?>> getAllSearchModelCls(ServiceSearchProxy serviceSearchProxy){
        List<Class<?>> rawSearchModelClsList = new ArrayList<>();
        if(serviceSearchProxy.getDocSearchModelCls() != null){
            rawSearchModelClsList.add(serviceSearchProxy.getDocSearchModelCls());
        }
        if(serviceSearchProxy.getMatItemSearchModelCls() != null){
            rawSearchModelClsList.add(serviceSearchProxy.getMatItemSearchModelCls());
        }
        return rawSearchModelClsList;
    }


    /**
     * Logic to provide raw search fields list for special search model
     * @param searchProxyConfig
     * @return
     */
    public List<SearchFieldUnion> getRawSearchFieldList(
            SearchProxyConfig searchProxyConfig, String searchModelName) throws SearchProxyConfigureException {
        List<Class<?>> rawSearchModelClsList = getRawSearchModelList(searchProxyConfig);
        if(ServiceCollectionsHelper.checkNullList(rawSearchModelClsList)){
            return null;
        }
        List<SearchFieldUnion> searchFieldUnionList = new ArrayList<>();
        for(Class<?> searchModelCls: rawSearchModelClsList){
            if(ServiceEntityStringHelper.checkNullString(searchModelName)){
                // in case need to filer with search model name
                if(!searchModelName.equals(ServiceEntityStringHelper.headerToLowerCase(searchModelCls.getSimpleName()))){
                    continue;
                }
            }
            List<SearchFieldUnion> tmpFieldUnionList = getRawSearchFieldList(searchModelCls);
            if(!ServiceCollectionsHelper.checkNullList(tmpFieldUnionList)){
                searchFieldUnionList.addAll(tmpFieldUnionList);
            }
        }
        return searchFieldUnionList;
    }


    /**
     * Logic to provide raw search fields list for special search model
     * @param serviceSearchProxy
     * @return
     */
    public List<SearchFieldUnion> getRawSearchFieldList(
            ServiceSearchProxy serviceSearchProxy, String searchModelName) throws SearchProxyConfigureException {
        List<Class<?>> rawSearchModelClsList = this.getAllSearchModelCls(serviceSearchProxy);
        if(ServiceCollectionsHelper.checkNullList(rawSearchModelClsList)){
            return null;
        }
        List<SearchFieldUnion> searchFieldUnionList = new ArrayList<>();
        for(Class<?> searchModelCls: rawSearchModelClsList){
            if(ServiceEntityStringHelper.checkNullString(searchModelName)){
                // in case need to filer with search model name
                if(!searchModelName.equals(ServiceEntityStringHelper.headerToLowerCase(searchModelCls.getSimpleName()))){
                    continue;
                }
            }
            List<SearchFieldUnion> tmpFieldUnionList = getRawSearchFieldList(searchModelCls);
            if(!ServiceCollectionsHelper.checkNullList(tmpFieldUnionList)){
                searchFieldUnionList.addAll(tmpFieldUnionList);
            }
        }
        return searchFieldUnionList;
    }


    /**
     * Logic to get search field list
     *
     * @param searchModelCls
     * @return
     */
    public List<SearchFieldUnion> getRawSearchFieldList(Class<?> searchModelCls) {
        List<Field> searchFields = SEUIModelFieldsHelper
                .getFieldsList(searchModelCls);
        /*
         * [Step1] Traverse each field from search UI model
         */
        List<SearchFieldUnion> resultList = new ArrayList<>();
        for (Field field : searchFields) {
            field.setAccessible(true);
            BSearchFieldConfig bSearchFieldConfig = field
                    .getAnnotation(BSearchFieldConfig.class);
            if (bSearchFieldConfig == null) {
                // in case search group
                BSearchGroupConfig bSearchGroupConfig = field.getAnnotation(BSearchGroupConfig.class);
                if (bSearchGroupConfig != null) {
                    // Process all sub fields in bSearchGroupConfig
                    List<SearchFieldUnion> groupFieldList = getGroupSearchFields(field, searchModelCls);
                    if (!ServiceCollectionsHelper.checkNullList(groupFieldList)) {
                        resultList.addAll(groupFieldList);
                    }
                }
                continue;
            } else {
                // In case standard search field
                SearchFieldUnion searchFieldUnion = new SearchFieldUnion(field.getName(), field,
                        searchModelCls.getSimpleName());
                resultList.add(searchFieldUnion);
            }
        }
        return resultList;
    }

    /**
     * Logic to get all sub search fields from group field
     *
     * @param groupField
     * @param searchModelCls
     * @return
     */
    private List<SearchFieldUnion> getGroupSearchFields(Field groupField, Class<?> searchModelCls) {
        groupField.setAccessible(true);
        List<Field> searchFields = SEUIModelFieldsHelper
                .getFieldsList(groupField.getType());
        if (ServiceCollectionsHelper.checkNullList(searchFields)) {
            return null;
        }
        /*
         * [Step1] Traverse each field from search UI model
         */
        List<SearchFieldUnion> resultList = new ArrayList<>();
        for (Field field : searchFields) {
            BSearchFieldConfig bSearchFieldConfig = field
                    .getAnnotation(BSearchFieldConfig.class);
            if (bSearchFieldConfig == null) {
                continue;
            }
            field.setAccessible(true);
            String fieldName = genGroupFieldName(groupField, field);
            SearchFieldUnion searchFieldUnion = new SearchFieldUnion(fieldName, field, searchModelCls.getSimpleName());
            resultList.add(searchFieldUnion);
        }
        return resultList;
    }

    /**
     * Rule to generate sub search field name in search group
     *
     * @param groupField
     * @param subField
     * @return
     */
    public static String genGroupFieldName(Field groupField, Field subField) {
        return groupField.getName() + "." + subField.getName();
    }

    public void genDefSearchFieldConfigureBatch(
            SearchProxyConfigServiceModel searchProxyConfigServiceModel,
            boolean overwriteFlag, String logonUserUUID, String organizationUUID)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        SearchProxyConfig searchProxyConfig = searchProxyConfigServiceModel
                .getSearchProxyConfig();
        Object rawBean = springContextBeanService.getBean(searchProxyConfig
                .getSearchModelName());
        if (rawBean == null) {
            // should raise exception
        }
        List<Field> fieldList = ServiceEntityFieldsHelper.getFieldsList(rawBean
                .getClass());
        if (ServiceCollectionsHelper.checkNullList(fieldList)) {
            // return directly if empty field list
            return;
        }
        for (Field field : fieldList) {
            SearchFieldConfig searchFieldConfig = filterFieldConfigByFieldName(
                    searchProxyConfigServiceModel.getSearchFieldConfigList(),
                    field.getName());
            if (searchFieldConfig != null) {
                if (!overwriteFlag) {
                    continue;
                }
                // In case need to cover existed field configure instance
            } else {
                // In case need to create new field configure instance
                // to implement this in future
            }
            try {
                SearchFieldConfig newFieldConfig = newSearchFieldConfig(
                        searchProxyConfig, field);
                if (searchProxyConfigServiceModel.getSearchFieldConfigList() == null) {
                    searchProxyConfigServiceModel
                            .setSearchFieldConfigList(new ArrayList<>());
                }
                searchProxyConfigServiceModel.getSearchFieldConfigList().add(
                        newFieldConfig);
            } catch (ServiceEntityConfigureException e) {
                // Just continue;
            }
        }
        // Update to DB
        updateServiceModuleWithDelete(SearchProxyConfigServiceModel.class,
                searchProxyConfigServiceModel, logonUserUUID, organizationUUID);
    }

    public SearchFieldConfig filterFieldConfigByFieldName(
            List<ServiceEntityNode> searchFieldConfigList, String fieldName) {
        if (ServiceCollectionsHelper.checkNullList(searchFieldConfigList)) {
            return null;
        }
        if (ServiceEntityStringHelper.checkNullString(fieldName)) {
            return null;
        }
        for (ServiceEntityNode seNode : searchFieldConfigList) {
            SearchFieldConfig searchFieldConfig = (SearchFieldConfig) seNode;
            if (fieldName.equals(searchFieldConfig.getFieldName())) {
                return searchFieldConfig;
            }
        }
        return null;
    }

    /**
     * Logic to new Search Field Configure model from field
     *
     * @param field
     * @return
     * @throws ServiceEntityConfigureException
     */
    public SearchFieldConfig newSearchFieldConfig(
            SearchProxyConfig searchProxyConfig, Field field)
            throws ServiceEntityConfigureException {
        if (field == null) {
            return null;
        }
        SearchFieldConfig searchFieldConfig = (SearchFieldConfig) newEntityNode(
                searchProxyConfig, SearchFieldConfig.NODENAME);
        searchFieldConfig.setFieldName(field.getName());
        searchFieldConfig.setId(field.getName());
        return searchFieldConfig;
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        // TODO-DAO: super.setServiceEntityDAO(searchProxyConfigDAO);
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(searchProxyConfigConfigureProxy);
    }

    @Override
    public ServiceSearchProxy getSearchProxy() {
        return searchProxyConfigSearchProxy;
    }

}
