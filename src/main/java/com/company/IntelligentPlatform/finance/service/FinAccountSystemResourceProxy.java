package com.company.IntelligentPlatform.finance.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.company.IntelligentPlatform.finance.dto.FinAccDocRefSearchModel;
import com.company.IntelligentPlatform.finance.dto.FinAccountSearchModel;
import com.company.IntelligentPlatform.finance.model.FinAccDocRef;
import com.company.IntelligentPlatform.finance.model.FinAccount;
import com.company.IntelligentPlatform.finance.model.FinAccountTitle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.dto.IFinSettleConfigure;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigPreCondition;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchNodeMapping;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityDateHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SEFieldSearchConfig;
import com.company.IntelligentPlatform.common.model.ResFinAccountSetting;
import com.company.IntelligentPlatform.common.model.SystemResource;

@Service
public class FinAccountSystemResourceProxy {

	@Autowired
	protected FinAccountManager finAccountManager;

	@Autowired
	protected FinAccountTitleManager finAccountTitleManager;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected SystemResourceManager systemResourceManager;

	public List<ServiceEntityNode> searchNotSettledDoc(
			List<BSearchNodeComConfigure> modelSearchConfigList,
			String resourceID, int documentType, String resSearchNodeID, String client,
			SEUIComModel searchModel) throws ServiceEntityConfigureException,
			SearchConfigureException, ServiceEntityInstallationException,
			NodeNotFoundException {
		List<ServiceEntityNode> finAccountTitleList = getONFinAccTitleListByResource(resourceID, client);
		if (finAccountTitleList == null || finAccountTitleList.size() == 0) {
			return null;
		}
		List<ServiceEntityNode> result = new ArrayList<ServiceEntityNode>();
		List<String> finAccTitleIDList = new ArrayList<String>();
		for (ServiceEntityNode finAccountTitleNode : finAccountTitleList) {
			finAccTitleIDList.add(finAccountTitleNode.getId());
		}
		result = searchNotSettledDocCore(modelSearchConfigList,
				resSearchNodeID, finAccTitleIDList, searchModel, client);
		return result;
	}

	public List<ServiceEntityNode> getFinAccountRawListByDocList(
			List<ServiceEntityNode> docList, int documentType, String client)
			throws SearchConfigureException,
			ServiceEntityInstallationException,
			ServiceEntityConfigureException, NodeNotFoundException {
		Date earlistDate = getEarlistCreateDateByDocList(docList);
		Date latestDate = getLatestlistCreateDateByDocList(docList);
		FinAccountSearchModel finAccountSearchModel = new FinAccountSearchModel();
		finAccountSearchModel.setCreatedTimeLow(earlistDate);
		finAccountSearchModel.setCreatedTimeHigh(latestDate);
		finAccountSearchModel.setDocumentType(documentType);
		return finAccountManager.searchFinRootCore(finAccountSearchModel, client);
	}

	public List<ServiceEntityNode> getFinAccDocRefRawListByDocList(
			List<ServiceEntityNode> docList, int documentType, String client)
			throws SearchConfigureException,
			ServiceEntityInstallationException,
			ServiceEntityConfigureException, NodeNotFoundException {
		Date earlistDate = getEarlistCreateDateByDocList(docList);
		Date latestDate = getLatestlistCreateDateByDocList(docList);
		FinAccDocRefSearchModel searchModel = new FinAccDocRefSearchModel();
		searchModel.setCreatedTimeHigh(latestDate);
		searchModel.setCreatedTimeLow(earlistDate);
		return finAccountManager.searchFinDocRefCore(searchModel, client);
	}

	public static Date getEarlistCreateDateByDocList(
			List<ServiceEntityNode> docList) {
		List<Date> createDateList = new ArrayList<Date>();
		if (docList == null || docList.size() == 0) {
			return null;
		}
		for (ServiceEntityNode seNode : docList) {
			createDateList.add(java.util.Date.from(seNode.getCreatedTime().atZone(java.time.ZoneId.systemDefault()).toInstant()));
		}
		Date rawEarliestDate = ServiceEntityDateHelper
				.getEarliestDate(createDateList);
		// Set one hour off set
		Date earlistDate = ServiceEntityDateHelper.adjustDays(rawEarliestDate,
				-20);
		return earlistDate;
	}

	public static Date getLatestlistCreateDateByDocList(
			List<ServiceEntityNode> docList) {
		List<Date> createDateList = new ArrayList<Date>();
		if (docList == null || docList.size() == 0) {
			return null;
		}
		for (ServiceEntityNode seNode : docList) {
			createDateList.add(java.util.Date.from(seNode.getCreatedTime().atZone(java.time.ZoneId.systemDefault()).toInstant()));
		}
		Date rawLatestDate = ServiceEntityDateHelper
				.getLatestDate(createDateList);
		// Set one hour off set
		Date latestDate = ServiceEntityDateHelper.adjustDays(rawLatestDate, 20);
		return latestDate;
	}

	/**
	 * get the real useful, that means relative to the document finance account
	 * list by filter on-line raw finance account list and raw fin document
	 * reference list
	 * 
	 * @param rawFinAccRootList
	 *            :raw finance account root node list
	 * @param rawFinAccDocRefList
	 *            :raw finance document reference list
	 * @param docUUID
	 *            :document uuid
	 */
	public List<ServiceEntityNode> getOnlineFinAccountListByDocRef(
			List<ServiceEntityNode> rawFinAccRootList,
			List<ServiceEntityNode> rawFinAccDocRefList, String docUUID) {
		List<ServiceEntityNode> result = new ArrayList<ServiceEntityNode>();
		if (rawFinAccDocRefList == null || rawFinAccDocRefList.size() == 0) {
			return null;
		}
		if (rawFinAccRootList == null || rawFinAccRootList.size() == 0) {
			return null;
		}
		String rootNodeUUID = ServiceEntityStringHelper.EMPTYSTRING;
		for (ServiceEntityNode docRefNode : rawFinAccDocRefList) {
			FinAccDocRef finAccDocRef = (FinAccDocRef) docRefNode;
			if (docUUID.equals(finAccDocRef.getRefUUID())) {
				rootNodeUUID = finAccDocRef.getRootNodeUUID();
				for (ServiceEntityNode finAccNode : rawFinAccRootList) {
					if (rootNodeUUID.equals(finAccNode.getUuid())) {
						result.add(finAccNode);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Get all relative fin account title list by resource finance setting with
	 * [ON] status
	 * 
	 * @throws ServiceEntityConfigureException
	 */
	public List<ResFinAccountSetting> getONResFinSettingListByResource(
			String resourceUUID, String client) throws ServiceEntityConfigureException {
		List<ResFinAccountSetting> resultSettingList = new ArrayList<ResFinAccountSetting>();
		List<ServiceEntityNode> resFinSettingList = systemResourceManager
				.getEntityNodeListByKey(resourceUUID,
						IServiceEntityNodeFieldConstant.PARENTNODEUUID,
						ResFinAccountSetting.NODENAME, client, null);
		if (resFinSettingList == null || resFinSettingList.size() == 0) {
			return null;
		}
		for (ServiceEntityNode seNode : resFinSettingList) {
			ResFinAccountSetting resFinAccountSetting = (ResFinAccountSetting) seNode;
			if (resFinAccountSetting.getSwitchFlag() == ResFinAccountSetting.SWITCH_OFF) {
				continue;
			}
			resultSettingList.add(resFinAccountSetting);
		}
		return resultSettingList;
	}

	/**
	 * Get all relative fin account title list by resource finance setting with
	 * [ON] status
	 * 
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getONFinAccTitleListByResource(
			String resourceID, String client) throws ServiceEntityConfigureException {
		List<ServiceEntityNode> resultTitleList = new ArrayList<ServiceEntityNode>();
		SystemResource systemResource = (SystemResource) systemResourceManager
				.getEntityNodeByKey(resourceID,
						IServiceEntityNodeFieldConstant.ID,
						SystemResource.NODENAME, client, null, true);
		if (systemResource == null) {
			return null;
		}
		List<ServiceEntityNode> resFinSettingList = systemResourceManager
				.getEntityNodeListByKey(systemResource.getUuid(),
						IServiceEntityNodeFieldConstant.PARENTNODEUUID,
						ResFinAccountSetting.NODENAME, client, null);
		if (resFinSettingList == null || resFinSettingList.size() == 0) {
			return null;
		}
		for (ServiceEntityNode seNode : resFinSettingList) {
			ResFinAccountSetting resFinAccountSetting = (ResFinAccountSetting) seNode;
			if (resFinAccountSetting.getSwitchFlag() == ResFinAccountSetting.SWITCH_OFF) {
				continue;
			}
			FinAccountTitle finAccountTitle = (FinAccountTitle) finAccountTitleManager
					.getEntityNodeByKey(resFinAccountSetting.getRefUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							FinAccountTitle.NODENAME, client, null);
			if (finAccountTitle == null) {
				continue;
			}
			resultTitleList.add(finAccountTitle);
		}
		return resultTitleList;
	}

	public List<ServiceEntityNode> searchNotSettledDocCore(
			List<BSearchNodeComConfigure> modelSearchConfigList,
			String resSearchNodeID, List<String> finAccTitleIDList,
			SEUIComModel seUIComModel, String client) throws SearchConfigureException,
			ServiceEntityInstallationException,
			ServiceEntityConfigureException, NodeNotFoundException {
		BSearchNodeComConfigure searchNodeConfig5 = new BSearchNodeComConfigure();
		searchNodeConfig5.setSeName(FinAccDocRef.SENAME);
		searchNodeConfig5.setNodeName(FinAccDocRef.NODENAME);
		searchNodeConfig5.setNodeInstID(FinAccDocRef.NODENAME);
		searchNodeConfig5.setStartNodeFlag(false);
		searchNodeConfig5
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_TARGET);
		searchNodeConfig5.setBaseNodeInstID(resSearchNodeID);
		modelSearchConfigList.add(searchNodeConfig5);
		BSearchNodeComConfigure searchNodeConfig6 = new BSearchNodeComConfigure();
		searchNodeConfig6.setSeName(FinAccount.SENAME);
		searchNodeConfig6.setNodeName(FinAccount.NODENAME);
		searchNodeConfig6.setNodeInstID(FinAccount.SENAME);
		searchNodeConfig6.setStartNodeFlag(false);
		searchNodeConfig6
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_CHILD);
		searchNodeConfig6.setBaseNodeInstID(FinAccDocRef.NODENAME);
		List<SearchConfigPreCondition> preConditions = new ArrayList<SearchConfigPreCondition>();
		SearchConfigPreCondition preCondition1 = new SearchConfigPreCondition();
		preCondition1.setFieldName("recordStatus");
		preCondition1.setFieldValue(FinAccount.RECORDED_UNDONE);
		preConditions.add(preCondition1);
		searchNodeConfig6.setPreConditions(preConditions);
		modelSearchConfigList.add(searchNodeConfig6);
		BSearchNodeComConfigure searchNodeConfig7 = new BSearchNodeComConfigure();
		searchNodeConfig7.setSeName(FinAccountTitle.SENAME);
		searchNodeConfig7.setNodeName(FinAccountTitle.NODENAME);
		searchNodeConfig7.setNodeInstID(FinAccountTitle.SENAME);
		searchNodeConfig7.setStartNodeFlag(false);
		searchNodeConfig7
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig7.setMapBaseFieldName("accountTitleUUID");
		searchNodeConfig7.setMapSourceFieldName("uuid");
		searchNodeConfig7.setBaseNodeInstID(FinAccount.SENAME);
		List<SearchConfigPreCondition> preConditionsB = new ArrayList<SearchConfigPreCondition>();
		for (String finAccTitleID : finAccTitleIDList) {
			SearchConfigPreCondition preCondition2 = new SearchConfigPreCondition();
			preCondition2.setFieldName("id");
			preCondition2.setFieldValue(finAccTitleID);
			preCondition2
					.setLogicCondOperator(SEFieldSearchConfig.LOGIC_OPERATOR_OR);
			preConditionsB.add(preCondition2);
		}
		searchNodeConfig7.setPreConditions(preConditionsB);
		modelSearchConfigList.add(searchNodeConfig7);
		return bsearchService.doSearch(seUIComModel, modelSearchConfigList,
				client, true);
	}

	public List<ServiceEntityNode> getONFinAccountListByResource(
			String resourceID, int documentType, String baseUUID, String client)
			throws ServiceEntityConfigureException, SearchConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		return null;
//		FinAccountSearchModel searchModel = new FinAccountSearchModel();
//		searchModel.setRefDocumentUUID(baseUUID);
//		searchModel.setDocumentType(documentType);
//		List<ServiceEntityNode> result = finAccountManager.getSearchProxy().searchDocList(searchModel, client);
//		return result;
	}

	/**
	 * Get the allAmount, recordedAmount, toRecordAmount by special document
	 * 
	 * @param baseUUID
	 * @param documentType
	 * @param resourceID
	 * @param fieldType
	 *            : all the relative finance account
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws SearchConfigureException
	 * @throws NodeNotFoundException
	 * @throws ServiceEntityInstallationException
	 */
	public double getAmountForDocument(String baseUUID, int documentType,
			String resourceID, int fieldType,String client,
			List<ServiceEntityNode> finAccountList,
			List<ServiceEntityNode> finAccountTitleList)
			throws ServiceEntityConfigureException, SearchConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		double ovarallAmount = 0;
		for (ServiceEntityNode finAccountSeNode : finAccountList) {
			if (finAccountSeNode == null) {
				continue;
			}
			FinAccount finAccount = (FinAccount) finAccountSeNode;
			FinAccountTitle finAccountTitle = (FinAccountTitle) finAccountTitleManager
					.getEntityNodeByKey(finAccount.getAccountTitleUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							FinAccountTitle.NODENAME, client, finAccountTitleList);
			if (finAccountTitle == null) {
				continue;
			}
			if (fieldType == IFinSettleConfigure.FIELDTYPE_ALLAMOUNT) {
				if (finAccountTitle.getFinAccountType() == FinAccountTitle.FIN_ACCOUNTTYPE_DEBIT) {
					ovarallAmount = ovarallAmount + finAccount.getAmount();
				} else {
					ovarallAmount = ovarallAmount - finAccount.getAmount();
				}
			}
			if (fieldType == IFinSettleConfigure.FIELDTYPE_RECORDED) {
				if (finAccountTitle.getFinAccountType() == FinAccountTitle.FIN_ACCOUNTTYPE_DEBIT) {
					ovarallAmount = ovarallAmount
							+ finAccount.getRecordedAmount();
				} else {
					ovarallAmount = ovarallAmount
							- finAccount.getRecordedAmount();
				}
			}
			if (fieldType == IFinSettleConfigure.FIELDTYPE_TORECORD) {
				if (finAccountTitle.getFinAccountType() == FinAccountTitle.FIN_ACCOUNTTYPE_DEBIT) {
					ovarallAmount = ovarallAmount
							+ finAccount.getToRecordAmount();
				} else {
					ovarallAmount = ovarallAmount
							- finAccount.getToRecordAmount();
				}
			}
		}
		return ovarallAmount;
	}

	/**
	 * During set overall settle amount for each document, the same finAccount
	 * should be used only once
	 * 
	 * @param finAccList
	 * @param finAccount
	 * @return
	 */
	protected boolean checkFinAccountDuplicate(
			List<ServiceEntityNode> finAccList, FinAccount finAccount) {
		if (finAccList == null || finAccList.size() == 0) {
			return false;
		}
		for (ServiceEntityNode seNode : finAccList) {
			if (finAccount.getUuid().equals(seNode.getUuid())) {
				return true;
			}
		}
		return false;
	}

	public void setAmountForSettleUIModel(List<ServiceEntityNode> docList,
			String resourceID, int documentType, String client, SEUIComModel settleUIModel,
			List<ServiceEntityNode> rawAccRootList,
			List<ServiceEntityNode> rawFinRefDocList)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException,
			IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException {
		SystemResource systemResource = (SystemResource) systemResourceManager
				.getEntityNodeByKey(resourceID,
						IServiceEntityNodeFieldConstant.ID,
						SystemResource.NODENAME, client, null, true);
		if (systemResource == null) {
			return;
		}
		List<ResFinAccountSetting> resFinSettingList = getONResFinSettingListByResource(systemResource
				.getUuid(), client);
		List<ServiceEntityNode> finAccList = new ArrayList<ServiceEntityNode>();
		// Firstly set the header amount
		double ovarallAmount = 0;
		double overallRecordedAmount = 0;
		double overalltoRecordAmount = 0;
		for (ResFinAccountSetting resFinAccountSetting : resFinSettingList) {
			// Traverse all Resource Fin Account with [ON] switch
			FinAccountTitle finAccountTitle = (FinAccountTitle) finAccountTitleManager
					.getEntityNodeByKey(resFinAccountSetting.getRefUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							FinAccountTitle.NODENAME, client, null);
			if (finAccountTitle == null) {
				continue;
			}
			// [Important logic] check [all amount field] configured for this
			// res Fin Account, if not don't need to calculate into overall
			// amount
			Field allAmountField = getSettleFeild(settleUIModel.getClass(),
					resFinAccountSetting.getCoreSettleID(),
					IFinSettleConfigure.FIELDTYPE_ALLAMOUNT);
			if (allAmountField == null) {
				// Skip process this resFinSetting if can not find the settle field
				continue;
			}
			Field toRecordAmountField = getSettleFeild(
					settleUIModel.getClass(),
					resFinAccountSetting.getCoreSettleID(),
					IFinSettleConfigure.FIELDTYPE_TORECORD);
			Field recordedAmountField = getSettleFeild(
					settleUIModel.getClass(),
					resFinAccountSetting.getCoreSettleID(),
					IFinSettleConfigure.FIELDTYPE_RECORDED);
			// Traverse each document and calculate the settle amount and store
			// into local variable
			double tmpAmount = 0;
			double tmpRecordedAmount = 0;
			double tmpToRecordAmount = 0;
			for (ServiceEntityNode seNode : docList) {
				FinAccount finAccount = finAccountManager
						.getAccountByDocTitleUUID(documentType,
								seNode.getUuid(), finAccountTitle.getUuid(),
								client, rawAccRootList, rawFinRefDocList);
				if (finAccount != null) {
					// add the finAccount into buffer for duplicate use
					boolean duplicateFlag = checkFinAccountDuplicate(
							finAccList, finAccount);
					if (duplicateFlag) {
						continue;
					}
					finAccList.add(finAccount);
					if (finAccountTitle.getFinAccountType() == FinAccountTitle.FIN_ACCOUNTTYPE_DEBIT) {
						tmpAmount = tmpAmount + finAccount.getAmount();
						tmpRecordedAmount = tmpRecordedAmount
								+ finAccount.getRecordedAmount();
						double finToRecordAmount = FinAccountManager
								.caculateToRecordAmount(finAccount);
						tmpToRecordAmount = tmpToRecordAmount
								+ finToRecordAmount;
					} else {
						tmpAmount = tmpAmount - finAccount.getAmount();
						tmpRecordedAmount = tmpRecordedAmount
								- finAccount.getRecordedAmount();
						double finToRecordAmount = FinAccountManager
								.caculateToRecordAmount(finAccount);
						tmpToRecordAmount = tmpToRecordAmount
								- finToRecordAmount;
					}
				}
			}
			ovarallAmount = ovarallAmount + tmpAmount;
			overallRecordedAmount = overallRecordedAmount + tmpRecordedAmount;
			overalltoRecordAmount = overalltoRecordAmount + tmpToRecordAmount;
			// Store the amount from local variable to Settle UI Model by
			// reflective			
			if (allAmountField != null) {
				allAmountField.setAccessible(true);
				allAmountField.set(settleUIModel, tmpAmount);
			}			
			if (toRecordAmountField != null) {
				toRecordAmountField.setAccessible(true);
				toRecordAmountField.set(settleUIModel, tmpToRecordAmount);
			}			
			if (recordedAmountField != null) {
				recordedAmountField.setAccessible(true);
				recordedAmountField.set(settleUIModel, tmpRecordedAmount);
			}
		}
		Field ovearllAmountField = settleUIModel.getClass().getDeclaredField(
				IFinanceSettleCenterConstants.FIELD_OVERALLAMOUNT);
		if (ovearllAmountField != null) {
			ovearllAmountField.setAccessible(true);
			ovearllAmountField.set(settleUIModel, ovarallAmount);
		}
		Field ovearllRecordedAmountField = settleUIModel
				.getClass()
				.getDeclaredField(
						IFinanceSettleCenterConstants.FIELD_OVERALL_RECORDEDAMOUNT);
		if (ovearllRecordedAmountField != null) {
			ovearllRecordedAmountField.setAccessible(true);
			ovearllRecordedAmountField
					.set(settleUIModel, overallRecordedAmount);
		}
		Field ovearllToRecordAmountField = settleUIModel
				.getClass()
				.getDeclaredField(
						IFinanceSettleCenterConstants.FIELD_OVERALL_TORECORDAMOUNT);
		if (ovearllToRecordAmountField != null) {
			ovearllToRecordAmountField.setAccessible(true);
			ovearllToRecordAmountField
					.set(settleUIModel, overalltoRecordAmount);
		}
	}

	public void verifyDocumentCore(String resourceID, String baseUUID,
			int documentType, String client, String logonUserUUID, String organizationUUID)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<ServiceEntityNode> finAccountList = getONFinAccountListByResource(
				resourceID, documentType, baseUUID, client);
		for (ServiceEntityNode finAccountSeNode : finAccountList) {
			FinAccount finAccount = (FinAccount) finAccountSeNode;
			finAccount.setVerifyStatus(FinAccount.VERIFIED_DONE);
			finAccount.setVerifyBy(logonUserUUID);
			finAccount.setVerifyTime(java.time.LocalDateTime.now());
			finAccountManager.updateSENode(finAccount, logonUserUUID,
					organizationUUID);
		}
	}

	public void auditDocumentCore(String resourceID, String baseUUID,
			int documentType, String client, String logonUserUUID, String organizationUUID)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<ServiceEntityNode> finAccountList = getONFinAccountListByResource(
				resourceID, documentType, baseUUID, client);
		for (ServiceEntityNode finAccountSeNode : finAccountList) {
			FinAccount finAccount = (FinAccount) finAccountSeNode;
			finAccount.setAuditStatus(FinAccount.AUDIT_DONE);
			finAccount.setAuditBy(logonUserUUID);
			finAccount.setAuditTime(java.time.LocalDateTime.now());
			finAccountManager.updateSENode(finAccount, logonUserUUID,
					organizationUUID);
		}
	}

	public void clearRecordDocumentCore(String resourceID, String baseUUID,
			int documentType, String client, String logonUserUUID, String organizationUUID)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<ServiceEntityNode> finAccountList = getONFinAccountListByResource(
				resourceID, documentType, baseUUID, client);
		for (ServiceEntityNode finAccountSeNode : finAccountList) {
			FinAccount finAccount = (FinAccount) finAccountSeNode;
			finAccount.setRecordBy(logonUserUUID);
			finAccount.setRecordedAmount(finAccount.getAmount());
			finAccount.setToRecordAmount(0);
			finAccount.setRecordTime(java.time.LocalDateTime.now());
			finAccount.setRecordStatus(FinAccount.RECORDED_DONE);
			finAccountManager.updateSENode(finAccount, logonUserUUID,
					organizationUUID);
		}
	}

	public Field getSettleFeild(Class<?> settleModuleClass,
			String coreSettleID, int fieldType) {
		List<Field> fieldList = ServiceEntityFieldsHelper
				.getSelfDefinedFieldsList(settleModuleClass);
		for (Field field : fieldList) {
			IFinSettleConfigure fieldSettleConfigure = field
					.getAnnotation(IFinSettleConfigure.class);
			if (fieldSettleConfigure != null) {
				if (fieldSettleConfigure.coreSettleID().equals(coreSettleID)
						&& fieldSettleConfigure.fieldType() == fieldType) {
					return field;
				}
			}
		}
		return null;
	}

}
