package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.MaterialTypeSearchModel;
import com.company.IntelligentPlatform.common.dto.MaterialTypeSearchSubModel;
import com.company.IntelligentPlatform.common.dto.MaterialTypeUIModel;
import com.company.IntelligentPlatform.common.model.MaterialType;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MaterialTypeSearchProxy extends ServiceSearchProxy {

    @Autowired
    protected MaterialTypeManager materialTypeManager;

    @Override
    public Class<?> getDocSearchModelCls() {
        return MaterialTypeSearchModel.class;
    }

    @Override
    public Class<?> getMatItemSearchModelCls() {
        return null;
    }

    @Override
    public String getAuthorizationResource() {
        return "";
    }

    @Override
    public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
        return Map.of();
    }

    public String getAllSubTypeUUIDMultiValue(
            MaterialTypeSearchSubModel materialTypeSearchSubModel, SearchAuthorizationContext searchAuthorizationContext, String client)
            throws ServiceEntityConfigureException, ServiceEntityInstallationException, SearchConfigureException, LogonInfoException {
        List<String> typeUUIDList = getAllSubTypeUUID(materialTypeSearchSubModel, searchAuthorizationContext, client);
        return ServiceEntityStringHelper.convStringListIntoMultiStringValue(typeUUIDList);
    }

    /**
     *
     * @param materialTypeSearchSubModel
     * @return
     */
    public boolean checkSearchMaterialTypeHit(MaterialTypeSearchSubModel materialTypeSearchSubModel) {
        if (materialTypeSearchSubModel == null) {
            return false;
        }
        return !ServiceEntityStringHelper.checkNullString(materialTypeSearchSubModel.getId()) || !ServiceEntityStringHelper.checkNullString(materialTypeSearchSubModel.getName())
                || !ServiceEntityStringHelper.checkNullString(materialTypeSearchSubModel.getParentTypeId());
    }

    public List<String> getAllSubTypeUUID(
            MaterialTypeSearchSubModel materialTypeSearchSubModel, SearchAuthorizationContext searchAuthorizationContext, String client)
            throws ServiceEntityConfigureException, ServiceEntityInstallationException, SearchConfigureException, LogonInfoException {
        SearchContext searchContext = getSearchContext(materialTypeSearchSubModel, searchAuthorizationContext, client);
        BSearchResponse bSearchResponse = searchUuidList(searchContext);
        List<String> uuidList = bSearchResponse.getUuidList();
        List<ServiceEntityNode> allSubTypeList = materialTypeManager.getAllSubTypeByKeyList(ServiceCollectionsHelper.asList(
                new ServiceBasicKeyStructure(uuidList, IServiceEntityNodeFieldConstant.UUID)), client);
        List<String> typeUUIDList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(allSubTypeList)) {
            typeUUIDList = allSubTypeList
                    .stream()
                    .map(ServiceEntityNode::getUuid)
                    .collect(Collectors.toList());
        }
        return typeUUIDList;
    }

    @Override
    public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
        List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
        // start node:[MaterialType]
        searchNodeConfigList.add(new BSearchNodeComConfigureBuilder().modelClass(MaterialType.class).startNodeFlag(true).build());
        searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(MaterialType.class).nodeInstId(MaterialTypeUIModel.SEC_PARENTTYPE).
                toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID("parentTypeUUID").
                baseNodeInstId(MaterialType.SENAME).build());
        searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(MaterialType.class).nodeInstId(MaterialTypeUIModel.SEC_ROOTTYPE).
                toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID("rootTypeUUID").
                baseNodeInstId(MaterialType.SENAME).build());
        return searchNodeConfigList;
    }

    private static SearchContext getSearchContext(MaterialTypeSearchSubModel materialTypeSearchSubModel, SearchAuthorizationContext searchAuthorizationContext, String client) {
        MaterialTypeSearchModel materialTypeSearchModel = new MaterialTypeSearchModel();
        materialTypeSearchModel.setId(materialTypeSearchSubModel.getId());
        materialTypeSearchModel.setName(materialTypeSearchSubModel.getName());
        materialTypeSearchModel.setUuid(materialTypeSearchSubModel.getUuid());
        SearchContext searchContext = new SearchContext();
        searchContext.setClient(client);
        searchContext.setSearchAuthorizationContext(searchAuthorizationContext);
        searchContext.setSearchModel(materialTypeSearchModel);
        return searchContext;
    }

}
