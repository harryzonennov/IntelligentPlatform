package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.MessageTemplateSearchModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.*;

import java.util.List;
import java.util.Map;

@Service
public class MessageTemplateSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected MessageTemplateManager messageTemplateManager;
	
	@Override
	public Class<?> getDocSearchModelCls() {
		return MessageTemplateSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return messageTemplateManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return null;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList =
				SearchModelConfigHelper.buildParentChildConfigure(MessageTemplate.class,
						MessageTempSearchCondition.class);
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(MessageTempPrioritySetting.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT).
				baseNodeInstId(MessageTemplate.SENAME).build());
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(SystemCodeValueCollection.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).
				baseNodeInstId(MessageTempPrioritySetting.NODENAME).
				mapField("refPrioritySettingUUID", "uuid").build());
		searchNodeConfigList.addAll(SearchModelConfigHelper.buildResUserOrgConfigure(MessageTemplate.class, null));
		return searchNodeConfigList;
	}
}
