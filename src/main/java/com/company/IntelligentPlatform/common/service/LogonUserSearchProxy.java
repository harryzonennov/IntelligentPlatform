package com.company.IntelligentPlatform.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.LogonUserSearchModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.Role;

@Service
public class LogonUserSearchProxy extends ServiceSearchProxy{

	@Autowired
	protected LogonUserManager logonUserManager;
	
	@Autowired
	protected BsearchService bsearchService;

	@Override
	public Class<?> getDocSearchModelCls() {
		return LogonUserSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return IServiceModelConstants.WarehouseStoreItem;
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return null;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		// start node:[LogonUser->LogonUserOrgReference]
		List<BSearchNodeComConfigure> searchNodeConfigList =
				SearchModelConfigHelper.buildParentChildConfigure(LogonUser.class,
						LogonUserOrgReference.class);
		// search node [LogonUserOrgReference->Organization]
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(Organization.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE).baseNodeInstId(LogonUserOrgReference.NODENAME).build());
		// search node3: [LogonUser->UserRole->Role]
		searchNodeConfigList.addAll(SearchModelConfigHelper.buildReference(UserRole.class, Role.class, null, null,
				SearchModelConfigHelper.genBuilder().baseNodeInstId(LogonUser.SENAME).toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_ROOT)));
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				LogonUserActionNode.class, LogonUserActionNode.NODEINST_ACTION_SUBMIT,
				LogonUserActionNode.DOC_ACTION_SUBMIT,
				null, LogonUser.SENAME
		));
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				LogonUserActionNode.class, LogonUserActionNode.NODEINST_ACTION_APPROVE,
				LogonUserActionNode.DOC_ACTION_APPROVE,
				null, LogonUser.SENAME
		));
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				LogonUserActionNode.class, LogonUserActionNode.NODEINST_ACTION_ACTIVE,
				LogonUserActionNode.DOC_ACTION_ACTIVE,
				null, LogonUser.SENAME
		));
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				LogonUserActionNode.class, LogonUserActionNode.NODEINST_ACTION_REINIT,
				LogonUserActionNode.DOC_ACTION_REINIT,
				null, LogonUser.SENAME
		));
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				LogonUserActionNode.class, LogonUserActionNode.NODEINST_ACTION_ARCHIVE,
				LogonUserActionNode.DOC_ACTION_ARCHIVE,
				null, LogonUser.SENAME
		));
		searchNodeConfigList.addAll(SearchModelConfigHelper.buildResUserOrgConfigure(LogonUser.class, null));
		return searchNodeConfigList;
	}


}
