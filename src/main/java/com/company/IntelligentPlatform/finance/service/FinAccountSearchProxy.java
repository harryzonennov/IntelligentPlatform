package com.company.IntelligentPlatform.finance.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.finance.dto.FinAccountMaterialItemServiceUIModelExtension;
import com.company.IntelligentPlatform.finance.dto.FinAccountMaterialItemUIModel;
import com.company.IntelligentPlatform.finance.dto.FinAccountSearchModel;
import com.company.IntelligentPlatform.finance.model.FinAccDocRef;
import com.company.IntelligentPlatform.finance.model.FinAccount;
import com.company.IntelligentPlatform.finance.model.FinAccountObjectRef;
import com.company.IntelligentPlatform.finance.model.FinAccountTitle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.DocumentContent;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.Account;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class FinAccountSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected FinAccountManager finAccountManager;

	@Autowired
	protected FinAccountMaterialItemServiceUIModelExtension finAccountMaterialItemServiceUIModelExtension;

	@Override
	public Class<?> getDocSearchModelCls() {
		return FinAccountSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return finAccountManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return finAccountManager.initStatusMap(languageCode);
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
		// search node0 [finAccount root]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(FinAccount.SENAME);
		searchNodeConfig0.setNodeName(FinAccount.NODENAME);
		searchNodeConfig0.setNodeInstID(FinAccount.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		// search node1 [finAccount Object ref]
		BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
		searchNodeConfig1.setSeName(FinAccountObjectRef.SENAME);
		searchNodeConfig1.setNodeName(FinAccountObjectRef.NODENAME);
		searchNodeConfig1.setNodeInstID(FinAccountSearchModel.ID_ACCOBJECT_REF);
		searchNodeConfig1
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig1.setBaseNodeInstID(FinAccount.SENAME);
		searchNodeConfigList.add(searchNodeConfig1);
		// search node3 [finAccount Title ]
		BSearchNodeComConfigure searchNodeConfig3 = new BSearchNodeComConfigure();
		searchNodeConfig3.setSeName(FinAccountTitle.SENAME);
		searchNodeConfig3.setNodeName(FinAccountTitle.NODENAME);
		searchNodeConfig3.setNodeInstID(FinAccountTitle.SENAME);
		searchNodeConfig3
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig3.setMapBaseFieldName("accountTitleUUID");
		searchNodeConfig3.setMapSourceFieldName("uuid");
		searchNodeConfig3.setBaseNodeInstID(FinAccount.SENAME);
		searchNodeConfigList.add(searchNodeConfig3);
		// search node4 [finAccountDocument reference]
		BSearchNodeComConfigure searchNodeConfig4 = new BSearchNodeComConfigure();
		searchNodeConfig4.setSeName(FinAccDocRef.SENAME);
		searchNodeConfig4.setNodeName(FinAccDocRef.NODENAME);
		searchNodeConfig4.setNodeInstID(FinAccountSearchModel.ID_ACCDOC_REF);
		searchNodeConfig4
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig4.setBaseNodeInstID(FinAccount.SENAME);
		searchNodeConfigList.add(searchNodeConfig4);
		// search node5 [finAccount document]
		BSearchNodeComConfigure searchNodeConfig5 = new BSearchNodeComConfigure();
		searchNodeConfig5.setNodeInstID(FinAccountSearchModel.ID_ACCDOC);
		searchNodeConfig5
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE);
		searchNodeConfig5
				.setBaseNodeInstID(FinAccountSearchModel.ID_ACCDOC_REF);
		searchNodeConfig5.setTablename(DocumentContent.SENAME);
		searchNodeConfigList.add(searchNodeConfig5);
		// search node6:[accountant]
		BSearchNodeComConfigure searchNodeConfig6 = new BSearchNodeComConfigure();
		searchNodeConfig6.setSeName(LogonUser.SENAME);
		searchNodeConfig6.setNodeName(LogonUser.NODENAME);
		searchNodeConfig6.setNodeInstID(FinAccountSearchModel.ID_ACCOUNTANT);
		searchNodeConfig6
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig6.setMapBaseFieldName("accountantUUID");
		searchNodeConfig6.setMapSourceFieldName("uuid");
		searchNodeConfig6.setBaseNodeInstID(FinAccount.SENAME);
		searchNodeConfigList.add(searchNodeConfig6);
		// search node7:[accountant]
		BSearchNodeComConfigure searchNodeConfig7 = new BSearchNodeComConfigure();
		searchNodeConfig7.setSeName(LogonUser.SENAME);
		searchNodeConfig7.setNodeName(LogonUser.NODENAME);
		searchNodeConfig7.setNodeInstID(FinAccountSearchModel.ID_CASHIER);
		searchNodeConfig7
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig7.setMapBaseFieldName("cashierUUID");
		searchNodeConfig7.setMapSourceFieldName("uuid");
		searchNodeConfig7.setBaseNodeInstID(FinAccount.SENAME);
		searchNodeConfigList.add(searchNodeConfig7);
		// search node8:[LogonUser]
		BSearchNodeComConfigure searchNodeConfig8 = new BSearchNodeComConfigure();
		searchNodeConfig8.setSeName(LogonUser.SENAME);
		searchNodeConfig8.setNodeName(LogonUser.NODENAME);
		searchNodeConfig8.setNodeInstID(LogonUser.SENAME);
		searchNodeConfig8
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig8.setMapBaseFieldName("createdBy");
		searchNodeConfig8.setMapSourceFieldName("uuid");
		searchNodeConfig8.setBaseNodeInstID(FinAccount.SENAME);
		searchNodeConfigList.add(searchNodeConfig8);
		// search node9 [finAccount Object]
		BSearchNodeComConfigure searchNodeConfig9 = new BSearchNodeComConfigure();
		searchNodeConfig9.setSeName(Account.SENAME);
		searchNodeConfig9.setNodeName(Account.NODENAME);
		searchNodeConfig9.setTablename(Account.SENAME);
		searchNodeConfig9.setNodeInstID(FinAccountSearchModel.ID_ACCOBJECT);
		searchNodeConfig9
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE);
		searchNodeConfig9
				.setBaseNodeInstID(FinAccountSearchModel.ID_ACCOBJECT_REF);
		searchNodeConfigList.add(searchNodeConfig9);
		return searchNodeConfigList;
	}

}
