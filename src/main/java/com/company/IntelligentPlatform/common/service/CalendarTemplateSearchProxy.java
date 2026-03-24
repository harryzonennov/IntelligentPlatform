package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.CalendarTemplateSearchModel;
import com.company.IntelligentPlatform.common.model.CalendarTempWorkSchedule;
import com.company.IntelligentPlatform.common.model.CalendarTemplate;
import com.company.IntelligentPlatform.common.model.CalendarTemplateItem;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CalendarTemplateSearchProxy extends ServiceSearchProxy {

    @Autowired
    protected CalendarTemplateManager calendarTemplateManager;

    @Override
    public Class<?> getDocSearchModelCls() {
        return CalendarTemplateSearchModel.class;
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

    @Override
    public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
        List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
        // start node:[CalendarTemplate]
        searchNodeConfigList.add(new BSearchNodeComConfigureBuilder().modelClass(CalendarTemplate.class).startNodeFlag(true).build());
        searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(CalendarTempWorkSchedule.class).
                toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT).
                baseNodeInstId(CalendarTemplate.SENAME).build());
        searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(CalendarTemplateItem.class).
                toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT).
                baseNodeInstId(CalendarTemplate.SENAME).build());
        return searchNodeConfigList;
    }

}
