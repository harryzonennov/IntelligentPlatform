package com.company.IntelligentPlatform.finance.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.finance.controller.FinAccountLogServiceUIModelExtension;
import com.company.IntelligentPlatform.finance.service.FinAccountManager;
import com.company.IntelligentPlatform.finance.service.FinAccountTitleManager;
import com.company.IntelligentPlatform.finance.model.FinAccDocRef;
import com.company.IntelligentPlatform.finance.model.FinAccount;
import com.company.IntelligentPlatform.finance.model.FinAccountObjectRef;
import com.company.IntelligentPlatform.finance.model.FinAccountPrerequirement;
import com.company.IntelligentPlatform.finance.model.FinAccountTitle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.DocumentContentManager;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.AccountManager;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.Account;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.DocumentContent;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Service
public class FinAccountServiceUIModelExtension extends ServiceUIModelExtension {

	@Autowired
	protected FinAccountLogServiceUIModelExtension finAccountLogServiceUIModelExtension;
	
	@Autowired
	protected FinAccountAttachmentServiceUIModelExtension finAccountAttachmentServiceUIModelExtension;
	
	@Autowired
	protected FinAccountMaterialItemServiceUIModelExtension finAccountMaterialItemServiceUIModelExtension;

	@Autowired
	protected FinAccountManager finAccountManager;

	@Autowired
	protected AccountManager accountManager;

	@Autowired
	protected DocumentContentManager documentContentManager;

	@Autowired
	protected LogonUserManager logonUserManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;
	
	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected FinAccountTitleManager finAccountTitleManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		resultList.add(finAccountLogServiceUIModelExtension);
		resultList.add(finAccountAttachmentServiceUIModelExtension);
		resultList.add(finAccountMaterialItemServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<UIModelNodeMapConfigure>();
		ServiceUIModelExtensionUnion finAccountExtensionUnion = new ServiceUIModelExtensionUnion();
		finAccountExtensionUnion.setNodeInstId(FinAccount.SENAME);
		finAccountExtensionUnion.setNodeName(FinAccount.NODENAME);

		// UI Model Configure of node:[FinAccount]
		UIModelNodeMapConfigure finAccountMap = new UIModelNodeMapConfigure();
		finAccountMap.setSeName(FinAccount.SENAME);
		finAccountMap.setNodeName(FinAccount.NODENAME);
		finAccountMap.setNodeInstID(FinAccount.SENAME);
		finAccountMap.setHostNodeFlag(true);
		Class<?>[] finAccountConvToUIParas = { FinAccount.class,
				FinAccountUIModel.class };
		finAccountMap.setConvToUIMethodParas(finAccountConvToUIParas);
		finAccountMap
				.setConvToUIMethod(FinAccountManager.METHOD_ConvFinAccountToUI);
		Class<?>[] FinAccountConvUIToParas = { FinAccountUIModel.class,
				FinAccount.class };
		finAccountMap.setConvUIToMethodParas(FinAccountConvUIToParas);
		finAccountMap
				.setConvUIToMethod(FinAccountManager.METHOD_ConvUIToFinAccount);
		uiModelNodeMapList.add(finAccountMap);
		
		uiModelNodeMapList.addAll(docFlowProxy
				.getDocDefCreateUpdateNodeMapConfigureList(FinAccount.SENAME));

		// UI Model Configure of node:[FinAccountObjectRef]
		UIModelNodeMapConfigure finAccountObjectRefMap = new UIModelNodeMapConfigure();
		finAccountObjectRefMap.setSeName(FinAccountObjectRef.SENAME);
		finAccountObjectRefMap.setNodeName(FinAccountObjectRef.NODENAME);
		finAccountObjectRefMap.setNodeInstID(FinAccountObjectRef.NODENAME);
		finAccountObjectRefMap.setBaseNodeInstID(FinAccount.SENAME);
		finAccountObjectRefMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_PARENT);
		finAccountObjectRefMap.setServiceEntityManager(finAccountManager);
		Class<?>[] finAccountObjectRefConvToUIParas = {
				FinAccountObjectRef.class, FinAccountUIModel.class };
		finAccountObjectRefMap
				.setConvToUIMethodParas(finAccountObjectRefConvToUIParas);
		finAccountObjectRefMap
				.setConvToUIMethod(FinAccountManager.METHOD_ConvFinAccountObjectRefToUI);
		Class<?>[] FinAccountObjectRefConvUIToParas = {
				FinAccountUIModel.class, FinAccountObjectRef.class };
		finAccountObjectRefMap
				.setConvUIToMethodParas(FinAccountObjectRefConvUIToParas);
		finAccountObjectRefMap
				.setConvUIToMethod(FinAccountManager.METHOD_ConvUIToFinAccountObjectRef);
		uiModelNodeMapList.add(finAccountObjectRefMap);

		// UI Model Configure of node:[FinAccountObject]
		UIModelNodeMapConfigure finAccountObjectMap = new UIModelNodeMapConfigure();
		finAccountObjectMap.setSeName(Account.SENAME);
		finAccountObjectMap.setNodeName(Account.NODENAME);
		finAccountObjectMap.setNodeInstID("FinAccountObject");
		finAccountObjectMap.setBaseNodeInstID(FinAccountObjectRef.NODENAME);
		finAccountObjectMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		finAccountObjectMap.setServiceEntityManager(accountManager);
		Class<?>[] finAccountObjectConvToUIParas = { Account.class,
				FinAccountUIModel.class };
		finAccountObjectMap
				.setConvToUIMethodParas(finAccountObjectConvToUIParas);
		finAccountObjectMap
				.setConvToUIMethod(FinAccountManager.METHOD_ConvFinAccountObjectToUI);
		uiModelNodeMapList.add(finAccountObjectMap);

		// UI Model Configure of node:[FinAccDocRef]
		UIModelNodeMapConfigure finAccDocRefMap = new UIModelNodeMapConfigure();
		finAccDocRefMap.setSeName(FinAccDocRef.SENAME);
		finAccDocRefMap.setNodeName(FinAccDocRef.NODENAME);
		finAccDocRefMap.setNodeInstID(FinAccDocRef.NODENAME);
		finAccDocRefMap.setBaseNodeInstID(FinAccount.SENAME);
		finAccDocRefMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_PARENT);
		finAccDocRefMap.setServiceEntityManager(finAccountManager);
		Class<?>[] finAccDocRefConvToUIParas = { FinAccDocRef.class,
				FinAccountUIModel.class };
		finAccDocRefMap.setConvToUIMethodParas(finAccDocRefConvToUIParas);
		finAccDocRefMap
				.setConvToUIMethod(FinAccountManager.METHOD_ConvFinAccDocRefToUI);
		Class<?>[] FinAccDocRefConvUIToParas = { FinAccountUIModel.class,
				FinAccDocRef.class };
		finAccDocRefMap.setConvUIToMethodParas(FinAccDocRefConvUIToParas);
		finAccDocRefMap
				.setConvUIToMethod(FinAccountManager.METHOD_ConvUIToFinAccDocRef);
		uiModelNodeMapList.add(finAccDocRefMap);

		// UI Model Configure of node:[FinAccountObject]
		UIModelNodeMapConfigure finDocumentMap = new UIModelNodeMapConfigure();
		finDocumentMap.setSeName(DocumentContent.SENAME);
		finDocumentMap.setGetSENodeCallback(rawSENode -> {
			FinAccDocRef finAccDocRef = (FinAccDocRef) rawSENode;			
			ServiceEntityNode documentContent = null;
			try {
				if(finAccDocRef != null){
					documentContent = serviceDocumentComProxy.getDocumentByReferenceNode(finAccDocRef);
					return documentContent;
				}else{
					return null;
				}
			} catch (ServiceEntityConfigureException e) {
				return null;
			}
		});
		finDocumentMap.setNodeName(DocumentContent.NODENAME);
		finDocumentMap.setNodeInstID("FinDocumentContent");
		finDocumentMap.setBaseNodeInstID(FinAccDocRef.NODENAME);
		finDocumentMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		finDocumentMap.setServiceEntityManager(documentContentManager);
		Class<?>[] finDocumentConvToUIParas = { ServiceEntityNode.class,
				FinAccountUIModel.class };
		finDocumentMap.setConvToUIMethodParas(finDocumentConvToUIParas);
		finDocumentMap
				.setConvToUIMethod(FinAccountManager.METHOD_ConvFinAccountDocumentToUI);
		uiModelNodeMapList.add(finDocumentMap);

		// UI Model Configure of node:[FinAccountPrerequirement]
		UIModelNodeMapConfigure finAccountPrerequirementMap = new UIModelNodeMapConfigure();
		finAccountPrerequirementMap.setSeName(FinAccountPrerequirement.SENAME);
		finAccountPrerequirementMap
				.setNodeName(FinAccountPrerequirement.NODENAME);
		finAccountPrerequirementMap
				.setNodeInstID(FinAccountPrerequirement.NODENAME);
		finAccountPrerequirementMap.setBaseNodeInstID(FinAccount.SENAME);
		finAccountPrerequirementMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_PARENT);
		finAccountPrerequirementMap.setServiceEntityManager(finAccountManager);
		Class<?>[] finAccountPrerequirementConvToUIParas = {
				FinAccountPrerequirement.class, FinAccountUIModel.class };
		finAccountPrerequirementMap
				.setConvToUIMethodParas(finAccountPrerequirementConvToUIParas);
		finAccountPrerequirementMap
				.setConvToUIMethod(FinAccountManager.METHOD_ConvFinAccountPrerequirementToUI);
		Class<?>[] FinAccountPrerequirementConvUIToParas = {
				FinAccountUIModel.class, FinAccountPrerequirement.class };
		finAccountPrerequirementMap
				.setConvUIToMethodParas(FinAccountPrerequirementConvUIToParas);
		finAccountPrerequirementMap
				.setConvUIToMethod(FinAccountManager.METHOD_ConvUIToFinAccountPrerequirement);
		uiModelNodeMapList.add(finAccountPrerequirementMap);

		// UI Model Configure of node:[Cashier]
		UIModelNodeMapConfigure cashierMap = new UIModelNodeMapConfigure();
		cashierMap.setSeName(LogonUser.SENAME);
		cashierMap.setNodeName(LogonUser.NODENAME);
		cashierMap.setNodeInstID("Cashier");
		cashierMap.setBaseNodeInstID(FinAccount.SENAME);
		cashierMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		cashierMap.setServiceEntityManager(logonUserManager);
		List<SearchConfigConnectCondition> cashierConditionList = new ArrayList<>();
		SearchConfigConnectCondition cashierCondition0 = new SearchConfigConnectCondition();
		cashierCondition0.setSourceFieldName("cashierUUID");
		cashierCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		cashierConditionList.add(cashierCondition0);
		cashierMap.setConnectionConditions(cashierConditionList);
		Class<?>[] cashierConvToUIParas = { LogonUser.class,
				FinAccountUIModel.class };
		cashierMap.setConvToUIMethodParas(cashierConvToUIParas);
		cashierMap.setConvToUIMethod(FinAccountManager.METHOD_ConvCashierToUI);
		uiModelNodeMapList.add(cashierMap);

		// UI Model Configure of node:[VerifyBy]
		UIModelNodeMapConfigure verifyByMap = new UIModelNodeMapConfigure();
		verifyByMap.setSeName(LogonUser.SENAME);
		verifyByMap.setNodeName(LogonUser.NODENAME);
		verifyByMap.setNodeInstID("VerifyBy");
		verifyByMap.setBaseNodeInstID(FinAccount.SENAME);
		verifyByMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		verifyByMap.setServiceEntityManager(logonUserManager);
		List<SearchConfigConnectCondition> verifyByConditionList = new ArrayList<>();
		SearchConfigConnectCondition verifyByCondition0 = new SearchConfigConnectCondition();
		verifyByCondition0.setSourceFieldName("verifyBy");
		verifyByCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		verifyByConditionList.add(verifyByCondition0);
		verifyByMap.setConnectionConditions(verifyByConditionList);
		Class<?>[] verifyByConvToUIParas = { LogonUser.class,
				FinAccountUIModel.class };
		verifyByMap.setConvToUIMethodParas(verifyByConvToUIParas);
		verifyByMap
				.setConvToUIMethod(FinAccountManager.METHOD_ConvVerifyByToUI);
		uiModelNodeMapList.add(verifyByMap);

		// UI Model Configure of node:[AuditBy]
		UIModelNodeMapConfigure auditByMap = new UIModelNodeMapConfigure();
		auditByMap.setSeName(LogonUser.SENAME);
		auditByMap.setNodeName(LogonUser.NODENAME);
		auditByMap.setNodeInstID("AuditBy");
		auditByMap.setBaseNodeInstID(FinAccount.SENAME);
		auditByMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		auditByMap.setServiceEntityManager(logonUserManager);
		List<SearchConfigConnectCondition> auditByConditionList = new ArrayList<>();
		SearchConfigConnectCondition auditByCondition0 = new SearchConfigConnectCondition();
		auditByCondition0.setSourceFieldName("auditBy");
		auditByCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		auditByConditionList.add(auditByCondition0);
		auditByMap.setConnectionConditions(auditByConditionList);
		Class<?>[] auditByConvToUIParas = { LogonUser.class,
				FinAccountUIModel.class };
		auditByMap.setConvToUIMethodParas(auditByConvToUIParas);
		auditByMap.setConvToUIMethod(FinAccountManager.METHOD_ConvAuditByToUI);
		uiModelNodeMapList.add(auditByMap);

		// UI Model Configure of node:[recordBy]
		UIModelNodeMapConfigure recordByMap = new UIModelNodeMapConfigure();
		recordByMap.setSeName(LogonUser.SENAME);
		recordByMap.setNodeName(LogonUser.NODENAME);
		recordByMap.setNodeInstID("recordBy");
		recordByMap.setBaseNodeInstID(FinAccount.SENAME);
		recordByMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		recordByMap.setServiceEntityManager(logonUserManager);
		List<SearchConfigConnectCondition> recordByConditionList = new ArrayList<>();
		SearchConfigConnectCondition recordByCondition0 = new SearchConfigConnectCondition();
		recordByCondition0.setSourceFieldName("recordBy");
		recordByCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		recordByConditionList.add(recordByCondition0);
		recordByMap.setConnectionConditions(recordByConditionList);
		Class<?>[] recordByConvToUIParas = { LogonUser.class,
				FinAccountUIModel.class };
		recordByMap.setConvToUIMethodParas(recordByConvToUIParas);
		recordByMap
				.setConvToUIMethod(FinAccountManager.METHOD_ConvRecordByToUI);
		uiModelNodeMapList.add(recordByMap);

		// UI Model Configure of node:[FinAccountTitle]
		UIModelNodeMapConfigure finAccountTitleMap = new UIModelNodeMapConfigure();
		finAccountTitleMap.setSeName(FinAccountTitle.SENAME);
		finAccountTitleMap.setNodeName(FinAccountTitle.NODENAME);
		finAccountTitleMap.setNodeInstID(FinAccountTitle.SENAME);
		finAccountTitleMap.setBaseNodeInstID(FinAccount.SENAME);
		finAccountTitleMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		finAccountTitleMap.setServiceEntityManager(finAccountTitleManager);
		List<SearchConfigConnectCondition> finAccountTitleConditionList = new ArrayList<>();
		SearchConfigConnectCondition finAccountTitleCondition0 = new SearchConfigConnectCondition();
		finAccountTitleCondition0.setSourceFieldName("accountTitleUUID");
		finAccountTitleCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		finAccountTitleConditionList.add(finAccountTitleCondition0);
		finAccountTitleMap
				.setConnectionConditions(finAccountTitleConditionList);
		Class<?>[] finAccountTitleConvToUIParas = { FinAccountTitle.class,
				FinAccountUIModel.class };
		finAccountTitleMap.setConvToUIMethodParas(finAccountTitleConvToUIParas);
		finAccountTitleMap
				.setConvToUIMethod(FinAccountManager.METHOD_ConvFinAccountTitleToUI);
		uiModelNodeMapList.add(finAccountTitleMap);

		// UI Model Configure of node:[Accountant]
		UIModelNodeMapConfigure accountantMap = new UIModelNodeMapConfigure();
		accountantMap.setSeName(LogonUser.SENAME);
		accountantMap.setNodeName(LogonUser.NODENAME);
		accountantMap.setNodeInstID("Accountant");
		accountantMap.setBaseNodeInstID(FinAccount.SENAME);
		accountantMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		accountantMap.setServiceEntityManager(logonUserManager);
		List<SearchConfigConnectCondition> accountantConditionList = new ArrayList<>();
		SearchConfigConnectCondition accountantCondition0 = new SearchConfigConnectCondition();
		accountantCondition0.setSourceFieldName("accountantUUID");
		accountantCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		accountantConditionList.add(accountantCondition0);
		accountantMap.setConnectionConditions(accountantConditionList);
		Class<?>[] accountantConvToUIParas = { LogonUser.class,
				FinAccountUIModel.class };
		accountantMap.setConvToUIMethodParas(accountantConvToUIParas);
		accountantMap
				.setConvToUIMethod(FinAccountManager.METHOD_ConvAccountantToUI);
		uiModelNodeMapList.add(accountantMap);
		finAccountExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(finAccountExtensionUnion);
		return resultList;
	}

}
