package com.company.IntelligentPlatform.common.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.DocFlowNodeSearchModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.AuthorizationManager;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.StandardDocFlowDirectionProxy;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Super class for service search proxy, which manage and execute the search
 * action
 *
 * @author Zhang, Hang
 */
@Service
public abstract class ServiceSearchProxy {

    @Autowired
    protected BsearchService bsearchService;

    @Autowired
    protected AuthorizationManager authorizationManager;

    @Autowired
    protected SearchDocConfigHelper searchDocConfigHelper;

    protected Logger logger = LoggerFactory.getLogger(ServiceSearchProxy.class);

    public abstract Class<?> getDocSearchModelCls();

    public abstract Class<?> getMatItemSearchModelCls();

    public abstract String getAuthorizationResource();

    public abstract Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException;

    public List<ServiceEntityNode> searchDocList(SEUIComModel searchModel,
                                                 LogonInfo logonInfo) throws SearchConfigureException,
            LogonInfoException {
        return null;
        //TODO check if the following commented out logic already covered by current search framework.
//        try {
//            LogonUser logonUser = logonInfo.getLogonUser();
//            List<ServiceEntityNode> organizationList = logonInfo
//                    .getOrganizationList();
//            Organization homeOrganization = logonInfo.getHomeOrganization();
//            boolean accessFlag = authorizationManager.checkAuthorization(logonUser,
//                    AuthorizationObject.AUTH_TYPE_RESOURCE, getAuthorizationResource(), ISystemActionCode.ACID_LIST,
//                    null,
//                    homeOrganization, organizationList);
//            if (!accessFlag) {
//                return null;
//            }
//            List<ServiceEntityNode> rawList = this.searchDocList(searchModel,
//                    logonInfo.getClient());
//            return authorizationManager.filterDataAccessBySystemAuthor(rawList, ISystemActionCode.ACID_LIST,
//                    logonUser, homeOrganization, organizationList);
//        } catch (ServiceEntityConfigureException | ServiceEntityInstallationException e) {
//            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "searchDocList"));
//            throw new SearchConfigureException(SearchConfigureException.PARA_SYSTEM_ERROR, e.getMessage());
//        }
    }

    public String[] getDocFieldNameArray() {
        return SearchDocConfigHelper.genDefaultMaterialFieldNameArray();
    }

    public BSearchResponse searchDocList(SearchContext searchContext) throws SearchConfigureException,
            ServiceEntityConfigureException, ServiceEntityInstallationException, AuthorizationException,
            LogonInfoException {
        List<BSearchNodeComConfigure> searchNodeConfigList = getBasicSearchNodeConfigureList(searchContext);
        searchContext.setFieldNameArray(getDocFieldNameArray());
        searchContext.setFuzzyFlag(true);
        return bsearchService.doSearchWithContext(
                searchContext, searchNodeConfigList);
    }

    public BSearchResponse searchUuidList(SearchContext searchContext) throws SearchConfigureException,
            ServiceEntityConfigureException, ServiceEntityInstallationException,
            LogonInfoException {
        List<BSearchNodeComConfigure> searchNodeConfigList = getBasicSearchNodeConfigureList(searchContext);
        searchContext.setFieldNameArray(getDocFieldNameArray());
        searchContext.setFuzzyFlag(true);
        SearchHeaderContext searchHeaderContext = new SearchHeaderContext();
        searchHeaderContext.setFlagUuidHeader(true);
        searchContext.setSearchHeaderContext(searchHeaderContext);
        return bsearchService.doSearchWithContext(
                searchContext, searchNodeConfigList);
    }

    public String[] getItemFieldNameArray() {
        return SearchDocConfigHelper.genDefaultMaterialFieldNameArray();
    }

    public BSearchResponse searchItemList(SearchContext searchContext) throws SearchConfigureException,
            ServiceEntityConfigureException,
            ServiceEntityInstallationException, AuthorizationException, LogonInfoException {
        List<BSearchNodeComConfigure> searchNodeConfigList = getBasicItemSearchNodeConfigureList(searchContext);
        searchContext.setFieldNameArray(getItemFieldNameArray());
        searchContext.setFuzzyFlag(true);
        return bsearchService.doSearchWithContext(
                searchContext, searchNodeConfigList);
    }

    /**
     * Logic to merge two search response
     * @param searchResponse1
     * @param searchResponse2
     * @return
     */
    public static BSearchResponse mergeSearchResponse(BSearchResponse searchResponse1, BSearchResponse searchResponse2) {
        List<ServiceEntityNode> resultList1 = searchResponse1.getResultList();
        List<ServiceEntityNode> resultList2 = searchResponse2.getResultList();
        ServiceCollectionsHelper.mergeToList(resultList1, resultList2);
        ServiceCollectionsHelper.mergeToList(searchResponse1.getUuidList(), searchResponse2.getUuidList(),  uuid -> uuid);
        searchResponse1.setRecordsTotal(resultList1.size());
        return searchResponse1;
    }

    public List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> getDocSearchConfigureListTemplate() throws SearchConfigureException {
        return null;
    }

    protected void addDefaultDocFlowConfigureList(List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> searchConfigureTemplateNodeList) throws SearchConfigureException {
        searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeInstId(SearchDocConfigHelper.NODE_ID_PREV_PROF_DOC).
                nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_DOCFLOW).nodeInstCode(StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV_PROF).build());
        searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeInstId(SearchDocConfigHelper.NODE_ID_PREV_DOC).
                nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_DOCFLOW).nodeInstCode(StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV).build());
        searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeInstId(SearchDocConfigHelper.NODE_ID_NEXT_PROF_DOC).
                nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_DOCFLOW).nodeInstCode(StandardDocFlowDirectionProxy.DOCFLOW_DIREC_NEXT_PROF).build());
        searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeInstId(SearchDocConfigHelper.NODE_ID_NEXT_DOC).
                nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_DOCFLOW).nodeInstCode(StandardDocFlowDirectionProxy.DOCFLOW_DIREC_NEXT).build());
        searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeInstId(SearchDocConfigHelper.NODE_ID_RESERVED_BY_DOC).
                nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_DOCFLOW).nodeInstCode(StandardDocFlowDirectionProxy.DOCFLOW_TO_RESERVED_BY).build());
        searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeInstId(SearchDocConfigHelper.NODE_ID_RESERVED_TARGET_DOC).
                nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_DOCFLOW).nodeInstCode(StandardDocFlowDirectionProxy.DOCFLOW_TO_RESERVE_TARGET).build());
    }

    protected void addDefaultDeliveryDocFlowConfigureList(List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> searchConfigureTemplateNodeList) throws SearchConfigureException {
        searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeInstId(SearchDocConfigHelper.NODE_ID_PREV_DOC).
                nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_DOCFLOW).nodeInstCode(StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV).build());
        searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeInstId(SearchDocConfigHelper.NODE_ID_NEXT_DOC).
                nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_DOCFLOW).nodeInstCode(StandardDocFlowDirectionProxy.DOCFLOW_DIREC_NEXT).build());
        searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeInstId(SearchDocConfigHelper.NODE_ID_RESERVED_BY_DOC).
                nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_DOCFLOW).nodeInstCode(StandardDocFlowDirectionProxy.DOCFLOW_TO_RESERVED_BY).build());
        searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeInstId(SearchDocConfigHelper.NODE_ID_RESERVED_TARGET_DOC).
                nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_DOCFLOW).nodeInstCode(StandardDocFlowDirectionProxy.DOCFLOW_TO_RESERVE_TARGET).build());
    }

    public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
        List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> searchConfigureTemplateNodeList = this.getDocSearchConfigureListTemplate();
        if (!ServiceCollectionsHelper.checkNullList(searchConfigureTemplateNodeList)) {
            try {
                // Node: 1 add Doc Header part
                SearchConfigureTemplateBuilder.SearchConfigureTemplateNode docHeaderNode =
                        SearchConfigureTemplateBuilder.filterTemplateByNodeCategory(searchConfigureTemplateNodeList, IServiceModuleFieldConfig.DOCNODE_CATE_DOC);
                if (docHeaderNode == null) {
                    throw new SearchConfigureException(SearchConfigureException.TYPE_MISSING_NODE, "docHeader");
                }
                String docNodeInstId = !ServiceEntityStringHelper.checkNullString(docHeaderNode.getNodeInstId()) ?
                        docHeaderNode.getNodeInstId() : BSearchNodeComConfigureBuilder.getDefaultNodeInstId(docHeaderNode.getNodeClass());
                List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>(SearchModelConfigHelper.genDefServiceEntitySearchHeader(docHeaderNode.getNodeClass(), docNodeInstId));

                // Node 2: Add involve party
                addSearchInvolvePartyNodeConfigureList(searchConfigureTemplateNodeList, searchNodeConfigList, docNodeInstId);

                // Node 3: Add involve party
                addSearchActionNodeConfigureList(searchConfigureTemplateNodeList, searchNodeConfigList, docNodeInstId);

                // Node 4: Add Material node
                SearchConfigureTemplateBuilder.SearchConfigureTemplateNode matItemNode =
                        SearchConfigureTemplateBuilder.filterTemplateByNodeCategory(searchConfigureTemplateNodeList,
                                IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM);
                searchNodeConfigList.addAll(SearchDocConfigHelper.genDocMatItemSearchNodeConfigureList(
                        matItemNode.getNodeClass(), docNodeInstId));
                // Node 5: Add Doc flow node
                addDocFlowNodeConfigureList(searchConfigureTemplateNodeList, searchNodeConfigList, matItemNode.getNodeInstId(), searchContext);
				// Node 6: Add other children node
				this.addChildNodeConfigureList(searchConfigureTemplateNodeList, searchNodeConfigList, docNodeInstId);
				this.addChildNodeConfigureList(searchConfigureTemplateNodeList, searchNodeConfigList, matItemNode.getNodeInstId());
                return searchNodeConfigList;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new SearchConfigureException(SearchConfigureException.PARA_SYSTEM_ERROR, e.getMessage());
            } catch (DocActionException e) {
                throw new SearchConfigureException(SearchConfigureException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        }
        return null;
    }

    public List<BSearchNodeComConfigure> getBasicItemSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
        List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> searchConfigureTemplateNodeList = this.getDocSearchConfigureListTemplate();
        if (!ServiceCollectionsHelper.checkNullList(searchConfigureTemplateNodeList)) {
            try {
                List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
                // Node: 1 add Doc Header part
                // start node:[root]
                SearchConfigureTemplateBuilder.SearchConfigureTemplateNode matItemHeaderNode =
                        SearchConfigureTemplateBuilder.filterTemplateByNodeCategory(searchConfigureTemplateNodeList, IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM);
                if (matItemHeaderNode == null) {
                    throw new SearchConfigureException(SearchConfigureException.TYPE_MISSING_NODE, "matItemHeader");
                }
                String matItemNodeInstId = !ServiceEntityStringHelper.checkNullString(matItemHeaderNode.getNodeInstId()) ?
                        matItemHeaderNode.getNodeInstId() : BSearchNodeComConfigureBuilder.getDefaultNodeInstId(matItemHeaderNode.getNodeClass());
                searchNodeConfigList.addAll(SearchModelConfigHelper.genDefServiceEntitySearchHeader(matItemHeaderNode.getNodeClass(), matItemNodeInstId));
                SearchConfigureTemplateBuilder.SearchConfigureTemplateNode docHeaderNode =
                        SearchConfigureTemplateBuilder.filterTemplateByNodeCategory(searchConfigureTemplateNodeList, IServiceModuleFieldConfig.DOCNODE_CATE_DOC);
                if (docHeaderNode == null) {
                    throw new SearchConfigureException(SearchConfigureException.TYPE_MISSING_NODE, "docHeader");
                }
                String docNodeInstId = !ServiceEntityStringHelper.checkNullString(docHeaderNode.getNodeInstId()) ?
                        docHeaderNode.getNodeInstId() : BSearchNodeComConfigureBuilder.getDefaultNodeInstId(docHeaderNode.getNodeClass());
                searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().
                        baseNodeInstId(matItemNodeInstId).modelClass(docHeaderNode.getNodeClass()).
                        toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_CHILD).build());

                // Node 2: Add involve party
                addSearchInvolvePartyNodeConfigureList(searchConfigureTemplateNodeList, searchNodeConfigList, docNodeInstId);

                // Node 3: Add involve party
                addSearchActionNodeConfigureList(searchConfigureTemplateNodeList, searchNodeConfigList, docNodeInstId);

                // Node 4: Add Material node
                SearchConfigureTemplateBuilder.SearchConfigureTemplateNode matItemNode =
                        SearchConfigureTemplateBuilder.filterTemplateByNodeCategory(searchConfigureTemplateNodeList,
                                IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM);
                searchNodeConfigList.addAll(SearchDocConfigHelper.genDocMatItemSearchNodeConfigureList(
                        matItemNode.getNodeClass(), docNodeInstId));

                // Node 5: Add other children node
                this.addChildNodeConfigureList(searchConfigureTemplateNodeList, searchNodeConfigList, docNodeInstId);
				this.addChildNodeConfigureList(searchConfigureTemplateNodeList, searchNodeConfigList, matItemNodeInstId);

                return searchNodeConfigList;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new SearchConfigureException(SearchConfigureException.PARA_SYSTEM_ERROR, e.getMessage());
            } catch (DocActionException e) {
                throw new SearchConfigureException(SearchConfigureException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        }
        return null;
    }

    private void addSearchActionNodeConfigureList(
            List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> searchConfigureTemplateNodeList,
            List<BSearchNodeComConfigure> baseSearchNodeConfigList, String baseNodeId) throws DocActionException {
        List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
        List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> docActionNodeList =
                SearchConfigureTemplateBuilder.filterTemplateByNodeCategoryList(searchConfigureTemplateNodeList,
                        IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE);
        ServiceCollectionsHelper.traverseListInterrupt(docActionNodeList, searchConfigureTemplateNode -> {
            try {
                searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
                        searchConfigureTemplateNode.getNodeClass(), searchConfigureTemplateNode.getNodeInstId(),
                        searchConfigureTemplateNode.getNodeInstCode(),
                        null, baseNodeId
                ));
            } catch (SearchConfigureException ex) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, ex.getErrorMessage());
            }
            return true;
        });
        if (!ServiceCollectionsHelper.checkNullList(searchNodeConfigList)) {
            baseSearchNodeConfigList.addAll(searchNodeConfigList);
        }
    }

    private DocFlowNodeSearchModel getDocFlowSearchModelByDirection(int docFlowDirection, SEUIComModel searchModel) throws IllegalAccessException {
        String targetDocId = SearchDocConfigHelper.getTargetDocFlowSearchNodeId(docFlowDirection);
        Field field = null;
        try {
            field = ServiceEntityFieldsHelper.getServiceField(searchModel.getClass(), targetDocId);
            field.setAccessible(true);
            return (DocFlowNodeSearchModel) field.get(searchModel);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    private void addDocFlowNodeConfigureList(
            List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> searchConfigureTemplateNodeList,
            List<BSearchNodeComConfigure> baseSearchNodeConfigList, String baseNodeId, SearchContext searchContext) throws DocActionException {
        List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
        List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> docFlowNodeList =
                SearchConfigureTemplateBuilder.filterTemplateByNodeCategoryList(searchConfigureTemplateNodeList,
                        IServiceModuleFieldConfig.DOCNODE_CATE_DOCFLOW);
        SEUIComModel searchModel = searchContext.getSearchModel();
        ServiceCollectionsHelper.traverseListInterrupt(docFlowNodeList, searchConfigureTemplateNode -> {
            try {
                DocFlowNodeSearchModel docFlowNodeSearchModel = getDocFlowSearchModelByDirection(searchConfigureTemplateNode.getNodeInstCode(), searchModel);
                List<BSearchNodeComConfigure> docFlowConfigureList = searchDocConfigHelper.genDocFlowNodeBySearchNodeConfigureList(baseNodeId
                        , searchConfigureTemplateNode.getNodeInstCode(), docFlowNodeSearchModel);
                if (!ServiceCollectionsHelper.checkNullList(docFlowConfigureList)) {
                    searchNodeConfigList.addAll(docFlowConfigureList);
                }
            } catch (SearchConfigureException ex) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, ex.getErrorMessage());
            } catch (IllegalAccessException ex) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, ex.getMessage());
            }
            return true;
        });
        if (!ServiceCollectionsHelper.checkNullList(searchNodeConfigList)) {
            baseSearchNodeConfigList.addAll(searchNodeConfigList);
        }
    }

    private void addSearchInvolvePartyNodeConfigureList(
            List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> searchConfigureTemplateNodeList,
            List<BSearchNodeComConfigure> baseSearchNodeConfigList, String baseNodeId) throws DocActionException {
        List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
        List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> involvePartyNodeList =
                SearchConfigureTemplateBuilder.filterTemplateByNodeCategoryList(searchConfigureTemplateNodeList, IServiceModuleFieldConfig.DOCNODE_CATE_PARTY);
        ServiceCollectionsHelper.traverseListInterrupt(involvePartyNodeList, searchConfigureTemplateNode -> {
            try {
                InvolvePartySearchConfigTemplateBuilder.InvolvePartySearchConfigTemplate involvePartySearchConfigTemplate =
                        (InvolvePartySearchConfigTemplateBuilder.InvolvePartySearchConfigTemplate) searchConfigureTemplateNode;
                searchNodeConfigList.addAll(SearchDocConfigHelper.genInvolvePartySearchNodeConfigureList(
                        involvePartySearchConfigTemplate.getNodeClass(), involvePartySearchConfigTemplate.getTargetPartyClass(),
                        involvePartySearchConfigTemplate.getTargetContactClass(), involvePartySearchConfigTemplate.getNodeInstCode(), involvePartySearchConfigTemplate.getNodeInstId(),
                        baseNodeId
                ));
            } catch (SearchConfigureException ex) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, ex.getErrorMessage());
            }
            return true;
        });
        if (!ServiceCollectionsHelper.checkNullList(searchNodeConfigList)) {
            baseSearchNodeConfigList.addAll(searchNodeConfigList);
        }
    }

    private void addChildNodeConfigureList(List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> searchConfigureTemplateNodeList,
                                           List<BSearchNodeComConfigure> baseSearchNodeConfigList, String nodeInstId) throws DocActionException {
        List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> childrenItemNodeList =
                SearchConfigureTemplateBuilder.filterTemplateByBaseNodeId(searchConfigureTemplateNodeList,
                        nodeInstId);
        ServiceCollectionsHelper.traverseListInterrupt(childrenItemNodeList, searchConfigureTemplateNode -> {
            try {
                baseSearchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(searchConfigureTemplateNode.nodeClass).
                        toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT).baseNodeInstId(nodeInstId).nodeInstId(searchConfigureTemplateNode.getBaseNodeInstId()).build());
            } catch (SearchConfigureException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
            return true;
        });
    }

    public BSearchResponse searchOnline(SearchContext searchContext)
            throws SearchConfigureException {
        return null;
    }

    public static Map<String, List<?>> mergeToMultipleValueMap(List<ServiceBasicKeyStructure> keyStructureList,
                                                               Map<String,
                                                                       List<?>> multiValueMap) {
        if (ServiceCollectionsHelper.checkNullList(keyStructureList)) {
            return multiValueMap;
        }
        if (multiValueMap == null) {
            multiValueMap = new HashMap<>();
        }
        for (ServiceBasicKeyStructure serviceBasicKeyStructure : keyStructureList) {
            if (multiValueMap.containsKey(serviceBasicKeyStructure.getKeyName())) {
                ServiceCollectionsHelper.mergeToList(multiValueMap.get(serviceBasicKeyStructure.getKeyName()),
                        serviceBasicKeyStructure.getMultipleValueList(), value -> {
                            return value;
                        });
            } else {
                multiValueMap.put(serviceBasicKeyStructure.getKeyName(),
                        serviceBasicKeyStructure.getMultipleValueList());
            }
        }
        return multiValueMap;
    }

    public static List<SearchConfigPreCondition> mergeIntoPreConditionList(List<ServiceBasicKeyStructure> keyStructureList,
                                                                           List<SearchConfigPreCondition> preConditionList) {
        if (ServiceCollectionsHelper.checkNullList(keyStructureList)) {
            return preConditionList;
        }
        if (preConditionList == null) {
            preConditionList = new ArrayList<>();
        }
        for (ServiceBasicKeyStructure serviceBasicKeyStructure : keyStructureList) {
            SearchConfigPreCondition searchConfigPreCondition = new SearchConfigPreCondition();
            searchConfigPreCondition.setFieldName(serviceBasicKeyStructure.getKeyName());
            searchConfigPreCondition.setOperator(serviceBasicKeyStructure.getOperator());
            searchConfigPreCondition.setFieldValue(serviceBasicKeyStructure.getKeyValue());
            searchConfigPreCondition.setMultipleValueList(serviceBasicKeyStructure.getMultipleValueList());
            SearchConfigPreCondition existPreCondition = ServiceCollectionsHelper.filterOnline(preConditionList,
                    tmpPreCondition -> tmpPreCondition.getFieldName().equals(serviceBasicKeyStructure.getKeyName()));
            if (existPreCondition != null) {
                // In case pre condition already exist
                existPreCondition.setFieldValue(searchConfigPreCondition.getFieldValue());
                existPreCondition.setOperator(searchConfigPreCondition.getOperator());
                if (!ServiceCollectionsHelper.checkNullList(existPreCondition.getMultipleValueList())) {
                    // merge multiple value
                    ServiceCollectionsHelper.mergeToList(existPreCondition.getMultipleValueList(),
                            searchConfigPreCondition.getMultipleValueList(), value -> {
                                return value;
                            });
                } else {
                    existPreCondition.setMultipleValueList(searchConfigPreCondition.getMultipleValueList());
                }
            } else {
                preConditionList.add(searchConfigPreCondition);
            }
        }
        return preConditionList;
    }

    public void setPreConditionsToConfigure(
            List<SearchConfigPreCondition> preConditionList,
            List<BSearchNodeComConfigure> searchNodeConfigList) {
        Map<String, List<SearchConfigPreCondition>> preConditionMap = new HashMap<>();
        /*
         * [Step1] Merge into pre-condition map
         */
        preConditionList
                .forEach(preCondition -> {
                    if (!ServiceEntityStringHelper.checkNullString(preCondition
                            .getNodeInstId())) {
                        if (preConditionMap.containsKey(preCondition
                                .getNodeInstId())) {
                            List<SearchConfigPreCondition> tempPreConditionList = preConditionMap
                                    .get(preCondition.getNodeInstId());
                            tempPreConditionList.add(preCondition);
                        } else {
                            List<SearchConfigPreCondition> tempPreConditionList = new ArrayList<>();
                            tempPreConditionList.add(preCondition);
                            preConditionMap.put(preCondition.getNodeInstId(),
                                    tempPreConditionList);
                        }
                    }
                });

        /*
         * [Step2]
         */
        Set<String> keySet = preConditionMap.keySet();
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
            String nodeInstId = it.next();
            List<SearchConfigPreCondition> tempPreConditionList = preConditionMap
                    .get(nodeInstId);
            BSearchNodeComConfigure searchNodeComConfigure = bsearchService.filterSearchNodeConfigOnline(searchNodeConfigList, nodeInstId);
            if (searchNodeComConfigure != null) {
                searchNodeComConfigure.setPreConditions(tempPreConditionList);
            }
        }
    }

}
