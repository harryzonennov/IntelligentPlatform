package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.MatConfigExtPropertySettingSearchModel;
import com.company.IntelligentPlatform.common.dto.MaterialConfigureTemplateSearchModel;
import com.company.IntelligentPlatform.common.model.MatConfigExtPropertySetting;
import com.company.IntelligentPlatform.common.model.MatConfigHeaderCondition;
import com.company.IntelligentPlatform.common.model.MatDecisionValueSetting;
import com.company.IntelligentPlatform.common.model.MaterialConfigureTemplate;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MaterialConfigureTemplateSearchProxy extends ServiceSearchProxy {

    @Autowired
    protected MaterialConfigureTemplateManager materialConfigureTemplateManager;

    @Override
    public Class<?> getDocSearchModelCls() {
        return MaterialConfigureTemplateSearchModel.class;
    }

    @Override
    public Class<?> getMatItemSearchModelCls() {
        return MatConfigExtPropertySettingSearchModel.class;
    }

    @Override
    public String getAuthorizationResource() {
        return "";
    }

    @Override
    public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
        return Map.of();
    }

    @Override
    public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
        List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
        // start node:[MaterialConfigureTemplate]
        searchNodeConfigList.add(new BSearchNodeComConfigureBuilder().modelClass(MaterialConfigureTemplate.class).startNodeFlag(true).build());
        searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(MatDecisionValueSetting.class).
                toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT).
                baseNodeInstId(MaterialConfigureTemplate.SENAME).build());
        searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(MatConfigHeaderCondition.class).
                toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT).
                baseNodeInstId(MaterialConfigureTemplate.SENAME).build());
        searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(MatConfigExtPropertySetting.class).
                toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT).
                baseNodeInstId(MaterialConfigureTemplate.SENAME).build());
        return searchNodeConfigList;
    }

    @Override
    public List<BSearchNodeComConfigure> getBasicItemSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
        // search node [Corporate contact person->Individual Customer]
        return SearchModelConfigHelper.buildChildParentConfigure(MatConfigExtPropertySetting.class,
                MaterialConfigureTemplate.class);
    }

}
