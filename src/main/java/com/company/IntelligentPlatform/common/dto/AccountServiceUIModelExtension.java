package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.model.Account;

import com.company.IntelligentPlatform.common.service.AccountManager;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceUIModelExtension extends ServiceUIModelExtension {

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion accountExtensionUnion = new ServiceUIModelExtensionUnion();
		accountExtensionUnion.setNodeInstId(Account.SENAME);
		accountExtensionUnion.setNodeName(Account.NODENAME);

		// UI Model Configure of node:[Account]
		UIModelNodeMapConfigure accountMap = new UIModelNodeMapConfigure();
		accountMap.setSeName(Account.SENAME);
		accountMap.setNodeName(Account.NODENAME);
		accountMap.setNodeInstID(Account.SENAME);
		accountMap.setHostNodeFlag(true);
		Class<?>[] accountConvToUIParas = { Account.class,
				AccountUIModel.class };
		accountMap.setConvToUIMethodParas(accountConvToUIParas);
		accountMap
				.setConvToUIMethod(AccountManager.METHOD_ConvAccountToUI);
		Class<?>[] AccountConvUIToParas = { AccountUIModel.class,
				Account.class };
		accountMap.setConvUIToMethodParas(AccountConvUIToParas);
		accountMap
				.setConvUIToMethod(AccountManager.METHOD_ConvUIToAccount);
		uiModelNodeMapList.add(accountMap);
		accountExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(accountExtensionUnion);
		return resultList;
	}

}
