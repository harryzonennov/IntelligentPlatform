package com.company.IntelligentPlatform.finance.service;

import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.finance.controller.FinAccountLogUIModel;
import com.company.IntelligentPlatform.finance.controller.FinanceAccountMessageHelper;
import com.company.IntelligentPlatform.finance.dto.FinAccDocRefSearchModel;
import com.company.IntelligentPlatform.finance.dto.FinAccountAttachmentUIModel;
import com.company.IntelligentPlatform.finance.dto.FinAccountMatItemAttachmentUIModel;
import com.company.IntelligentPlatform.finance.dto.FinAccountMaterialItemServiceUIModelExtension;
import com.company.IntelligentPlatform.finance.dto.FinAccountMaterialItemUIModel;
import com.company.IntelligentPlatform.finance.dto.FinAccountSearchModel;
import com.company.IntelligentPlatform.finance.dto.FinAccountServiceUIModelExtension;
import com.company.IntelligentPlatform.finance.dto.FinAccountSettleCenterUIModel;
import com.company.IntelligentPlatform.finance.dto.FinAccountUIModel;
import com.company.IntelligentPlatform.finance.repository.FinAccountRepository;
import com.company.IntelligentPlatform.finance.model.FinAccDocRef;
import com.company.IntelligentPlatform.finance.model.FinAccount;
import com.company.IntelligentPlatform.finance.model.FinAccountAttachment;
import com.company.IntelligentPlatform.finance.model.FinAccountLog;
import com.company.IntelligentPlatform.finance.model.FinAccountMatItemAttachment;
import com.company.IntelligentPlatform.finance.model.FinAccountMaterialItem;
import com.company.IntelligentPlatform.finance.model.FinAccountObjectRef;
import com.company.IntelligentPlatform.finance.model.FinAccountPrerequirement;
import com.company.IntelligentPlatform.finance.model.FinAccountSettleItem;
import com.company.IntelligentPlatform.finance.model.FinAccountTitle;
import com.company.IntelligentPlatform.finance.model.FinAccountConfigureProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.common.service.EmployeeManager;
import com.company.IntelligentPlatform.common.service.IndividualCustomerManager;
import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.AccountManager;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.AuthorizationManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.StandardPriorityProxy;
import com.company.IntelligentPlatform.common.service.ServiceChartDataSeries;
import com.company.IntelligentPlatform.common.service.ServiceChartHelper;
import com.company.IntelligentPlatform.common.service.ServiceChartTimeSlot;
import com.company.IntelligentPlatform.common.service.ServiceComChartModel;
import com.company.IntelligentPlatform.common.service.FinAccountManagerProxy;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchNodeMapping;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.model.DocumentContent;
import com.company.IntelligentPlatform.common.model.IReferenceNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityBindModel;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.Account;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Logic Manager CLASS FOR Service Entity [Account]
 * 
 * @author
 * @date Mon May 06 15:34:36 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service("finAccountManager")
@Transactional
public class FinAccountManager extends FinAccountManagerProxy {

	public static final String METHOD_ConvFinAccountLogToUI = "convFinAccountLogToUI";

	public static final String METHOD_ConvUIToFinAccountLog = "convUIToFinAccountLog";

	public static final String METHOD_ConvFinAccountToUI = "convFinAccountToUI";

	public static final String METHOD_ConvUIToFinAccount = "convUIToFinAccount";

	public static final String METHOD_ConvFinAccountPrerequirementToUI = "convFinAccountPrerequirementToUI";

	public static final String METHOD_ConvUIToFinAccountPrerequirement = "convUIToFinAccountPrerequirement";

	public static final String METHOD_ConvFinAccDocRefToUI = "convFinAccDocRefToUI";

	public static final String METHOD_ConvUIToFinAccDocRef = "convUIToFinAccDocRef";

	public static final String METHOD_ConvCashierToUI = "convCashierToUI";

	public static final String METHOD_ConvVerifyByToUI = "convVerifyByToUI";

	public static final String METHOD_ConvAuditByToUI = "convAuditByToUI";

	public static final String METHOD_ConvRecordByToUI = "convRecordByToUI";

	public static final String METHOD_ConvFinAccountTitleToUI = "convFinAccountTitleToUI";

	public static final String METHOD_ConvAccountantToUI = "convAccountantToUI";

	public static final String METHOD_ConvFinAccountObjectRefToUI = "convFinAccountObjectRefToUI";

	public static final String METHOD_ConvUIToFinAccountObjectRef = "convUIToFinAccountObjectRef";

	public static final String METHOD_ConvFinAccountObjectToUI = "convFinAccountObjectToUI";

	public static final String METHOD_ConvFinAccountDocumentToUI = "convFinAccountDocumentToUI";

	public static final String METHOD_ConvFinAccountMaterialItemToUI = "convFinAccountMaterialItemToUI";

	public static final String METHOD_ConvUIToFinAccountMaterialItem = "convUIToFinAccountMaterialItem";

	public static final String METHOD_ConvFinAccountAttachmentToUI = "convFinAccountAttachmentToUI";

	public static final String METHOD_ConvMaterialSKUToItemUI = "convMaterialSKUToItemUI";

	public static final String METHOD_ConvFinAccountToItemUI = "convFinAccountToItemUI";

	public static final String METHOD_ConvFinAccountMatItemAttachmentToUI = "convFinAccountMatItemAttachmentToUI";
    @PersistenceContext
    private EntityManager entityManager;


	@Autowired
	protected FinAccountRepository finAccountDAO;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected FinAccountConfigureProxy finAccountConfigureProxy;

	@Autowired
	protected FinAccountTitleManager finAccountTitleManager;

	@Autowired
	protected OrganizationManager organizationManager;

	@Autowired
	protected AuthorizationManager authorizationManager;

	@Autowired
	protected LogonInfoManager logonInfoManager;

	@Autowired
	protected LogonUserManager logonUserManager;

	@Autowired
	protected FinAccountIdHelper finAccountIdHelper;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected IndividualCustomerManager individualCustomerManager;

	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;

	@Autowired
	protected EmployeeManager employeeManager;

	@Autowired
	protected FinanceAccountMessageHelper financeAccountMessageHelper;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected AccountManager accountManager;

	@Autowired
	protected FinAccountSearchProxy finAccountSearchProxy;

	@Autowired
	protected FinAccountMaterialItemServiceUIModelExtension finAccountMaterialItemServiceUIModelExtension;

	@Autowired
	protected FinAccountServiceUIModelExtension finAccountServiceUIModelExtension;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected StandardPriorityProxy standardPriorityProxy;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public final static String FIELD_DOC_UUID = "documentUUID";

	public final static String FIELD_DOC_TYPE = "documentType";

	public final static String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_FINACCOUNT;

	private Map<String, Map<Integer, String>> auditStatusMapLan = new HashMap<>();

	private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

	private Map<String, Map<Integer, String>> recordStatusMapLan = new HashMap<>();

	private Map<String, Map<Integer, String>> verifyStatusMapLan = new HashMap<>();

	private Map<String, Map<Integer, String>> paymentTypeMapLan = new HashMap<>();

	private Map<String, Map<Integer, String>> finAccountTypeMapLan = new HashMap<>();

	private Map<String, Map<Integer, String>> adjustDirectionMapLan = new HashMap<>();

	public FinAccountManager() {
		super.seConfigureProxy = new FinAccountConfigureProxy();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, finAccountDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(finAccountConfigureProxy);
	}

	public Map<Integer, String> initPriorityCodeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return standardPriorityProxy.getPriorityMap(languageCode);
	}

	public Map<Integer, String> initDocumentTypeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return serviceDocumentComProxy.getDocumentTypeMap(true, languageCode);
	}

	public Map<Integer, String> initAdjustDirectionMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.adjustDirectionMapLan, FinAccountUIModel.class,
				"auditStatus");
	}

	public Map<Integer, String> initAuditStatusMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.auditStatusMapLan, FinAccountUIModel.class, "auditStatus");
	}

	public Map<Integer, String> initStatusMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.statusMapLan, FinAccountUIModel.class, "status");
	}

	public Map<Integer, String> initVerifyStatusMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.verifyStatusMapLan, FinAccountUIModel.class,
				"verifyStatus");
	}

	public Map<Integer, String> initPaymentTypeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.paymentTypeMapLan, FinAccountUIModel.class, "paymentType");
	}

	public Map<Integer, String> initRecordStatusMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.recordStatusMapLan, FinAccountUIModel.class,
				"recordStatus");
	}

	public Map<Integer, String> initFinAccountTypeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.finAccountTypeMapLan, FinAccountUIModel.class,
				"finAccountType");
	}

	public void convFinAccountToUI(FinAccount finAccount,
			FinAccountUIModel finAccountUIModel)
			throws ServiceEntityInstallationException {
		convFinAccountToUI(finAccount, finAccountUIModel, null);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convFinAccountToUI(FinAccount finAccount,
			FinAccountUIModel finAccountUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		if (finAccount != null) {
			docFlowProxy.convDocumentToUI(finAccount, finAccountUIModel,
					logonInfo);
			finAccountUIModel.setAuditBy(finAccount.getAuditBy());
			finAccountUIModel.setAdjustAmount(finAccount.getAdjustAmount());
			finAccountUIModel.setAuditLockMSG(finAccount.getAuditLockMSG());
			finAccountUIModel.setAccountTitleUUID(finAccount
					.getAccountTitleUUID());
			finAccountUIModel.setCashierUUID(finAccount.getCashierUUID());
			if (finAccount.getFinanceTime() != null) {
				finAccountUIModel
						.setFinanceTime(DefaultDateFormatConstant.DATE_FORMAT
								.format(finAccount.getFinanceTime()));
			}
			finAccountUIModel.setId(finAccount.getId());
			finAccountUIModel.setName(finAccount.getName());
			finAccountUIModel.setRecordLock(finAccount.getRecordLock());
			finAccountUIModel.setAccountObjectName(finAccount
					.getAccountObject());
			finAccountUIModel.setAmount(finAccount.getAmount());
			finAccountUIModel.setToRecordAmount(finAccount.getToRecordAmount());
			finAccountUIModel.setRecordedAmount(finAccount.getRecordedAmount());
			finAccountUIModel.setNote(finAccount.getNote());
			finAccountUIModel.setAuditLock(finAccount.getAuditLock());
			finAccountUIModel.setPriorityCode(finAccount.getPriorityCode());
			finAccountUIModel.setDocumentType(finAccount.getDocumentType());
			if (logonInfo != null) {
				Map<Integer, String> documentTypeMap = initDocumentTypeMap(logonInfo
						.getLanguageCode());
				finAccountUIModel.setDocumentTypeValue(documentTypeMap
						.get(finAccount.getDocumentType()));
			}
			if (finAccount.getRecordTime() != null) {
				finAccountUIModel
						.setRecordTime(DefaultDateFormatConstant.DATE_FORMAT
								.format(finAccount.getRecordTime()));
			}
			finAccountUIModel.setAuditStatus(finAccount.getAuditStatus());
			if (logonInfo != null) {
				try {
					Map<Integer, String> auditStatusMap = this
							.initAuditStatusMap(logonInfo.getLanguageCode());
					finAccountUIModel.setAuditStatusValue(auditStatusMap
							.get(finAccount.getAuditStatus()));
				} catch (ServiceEntityInstallationException e) {
					// just skip
					logger.error(ServiceEntityStringHelper
							.genDefaultErrorMessage(e, "auditStatus"));
				}
			}
			finAccountUIModel.setRecordBy(finAccount.getRecordBy());
			finAccountUIModel.setRecordNote(finAccount.getRecordNote());
			finAccountUIModel.setRecordLockMSG(finAccount.getRecordLockMSG());
			finAccountUIModel.setVerifyNote(finAccount.getVerifyNote());
			finAccountUIModel.setVerifyBy(finAccount.getVerifyBy());
			finAccountUIModel.setAccountantUUID(finAccount.getAccountantUUID());
			if (finAccount.getAuditTime() != null) {
				finAccountUIModel
						.setAuditTime(DefaultDateFormatConstant.DATE_FORMAT
								.format(finAccount.getAuditTime()));
			}
			finAccountUIModel.setVerifyLock(finAccount.getVerifyLock());
			finAccountUIModel.setVerifyLockMSG(finAccount.getVerifyLockMSG());
			finAccountUIModel.setStatus(finAccount.getStatus());
			finAccountUIModel.setAdjustDirection(finAccount
					.getAdjustDirection());
			finAccountUIModel.setVerifyStatus(finAccount.getVerifyStatus());
			if (logonInfo != null) {
				try {
					Map<Integer, String> verifyStatusMap = this
							.initVerifyStatusMap(logonInfo.getLanguageCode());
					finAccountUIModel.setVerifyStatusValue(verifyStatusMap
							.get(finAccount.getAuditStatus()));
				} catch (ServiceEntityInstallationException e) {
					// just skip
					logger.error(ServiceEntityStringHelper
							.genDefaultErrorMessage(e, "verifyStatus"));
				}
			}
			if (logonInfo != null) {
				try {
					Map<Integer, String> paymentTypeMap = this
							.initPaymentTypeMap(logonInfo.getLanguageCode());
					finAccountUIModel.setPaymentTypeValue(paymentTypeMap
							.get(finAccount.getPaymentType()));
				} catch (ServiceEntityInstallationException e) {
					// just skip
					logger.error(ServiceEntityStringHelper
							.genDefaultErrorMessage(e, "paymentType"));
				}
			}
			finAccountUIModel.setPaymentType(finAccount.getPaymentType());
			finAccountUIModel.setAuditNote(finAccount.getAuditNote());
			finAccountUIModel.setRecordStatus(finAccount.getRecordStatus());
			if (logonInfo != null) {
				try {
					Map<Integer, String> recordStatusMap = this
							.initRecordStatusMap(logonInfo.getLanguageCode());
					finAccountUIModel.setRecordStatusValue(recordStatusMap
							.get(finAccount.getRecordStatus()));
				} catch (ServiceEntityInstallationException e) {
					// just skip
					logger.error(ServiceEntityStringHelper
							.genDefaultErrorMessage(e, "recordStatus"));
				}
			}
			finAccountUIModel.setCurrencyCode(finAccount.getCurrencyCode());
			finAccountUIModel.setRefDocumentUUID(finAccount
					.getRefDocumentUUID());
			finAccountUIModel.setExchangeRate(finAccount.getExchangeRate());
			finAccountUIModel.setAmountInSetCurrency(finAccount
					.getAmountInSetCurrency());
			finAccountUIModel.setToRecordAmountInSetCurrency(finAccount
					.getToRecordAmountInSetCurrency());
			finAccountUIModel.setRecordedAmountInSetCurrency(finAccount
					.getRecordedAmountInSetCurrency());
			finAccountUIModel.setRefDocumentUUID(finAccount
					.getRefDocumentUUID());
			if (finAccount.getVerifyTime() != null) {
				finAccountUIModel
						.setVerifyTime(DefaultDateFormatConstant.DATE_FORMAT
								.format(finAccount.getVerifyTime()));
			}
		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:finAccount
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToFinAccount(FinAccountUIModel finAccountUIModel,
			FinAccount rawEntity) {
		if (!ServiceEntityStringHelper.checkNullString(finAccountUIModel
				.getUuid())) {
			rawEntity.setUuid(finAccountUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper.checkNullString(finAccountUIModel
				.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(finAccountUIModel.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(finAccountUIModel
				.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(finAccountUIModel.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(finAccountUIModel
				.getClient())) {
			rawEntity.setClient(finAccountUIModel.getClient());
		}
		rawEntity.setUuid(finAccountUIModel.getUuid());
		rawEntity.setAuditBy(finAccountUIModel.getAuditBy());
		rawEntity.setAdjustAmount(finAccountUIModel.getAdjustAmount());
		rawEntity.setAuditLockMSG(finAccountUIModel.getAuditLockMSG());
		rawEntity.setAccountTitleUUID(finAccountUIModel.getAccountTitleUUID());
		rawEntity.setCashierUUID(finAccountUIModel.getCashierUUID());
		if (!ServiceEntityStringHelper.checkNullString(finAccountUIModel
				.getFinanceTime())) {
			try {
				rawEntity.setFinanceTime(DefaultDateFormatConstant.DATE_FORMAT
						.parse(finAccountUIModel.getFinanceTime()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setId(finAccountUIModel.getId());
		rawEntity.setName(finAccountUIModel.getName());
		rawEntity.setRecordLock(finAccountUIModel.getRecordLock());
		rawEntity.setAccountObject(finAccountUIModel.getAccountObjectName());
		rawEntity.setAmount(finAccountUIModel.getAmount());
		rawEntity.setToRecordAmount(finAccountUIModel.getToRecordAmount());
		rawEntity.setRecordedAmount(finAccountUIModel.getRecordedAmount());
		rawEntity.setNote(finAccountUIModel.getNote());
		rawEntity.setAuditLock(finAccountUIModel.getAuditLock());
		rawEntity.setPriorityCode(finAccountUIModel.getPriorityCode());
		rawEntity.setDocumentType(finAccountUIModel.getDocumentType());
		if (!ServiceEntityStringHelper.checkNullString(finAccountUIModel
				.getRecordTime())) {
			try {
				rawEntity.setRecordTime(DefaultDateFormatConstant.DATE_FORMAT
						.parse(finAccountUIModel.getRecordTime()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setAuditStatus(finAccountUIModel.getAuditStatus());
		rawEntity.setRecordBy(finAccountUIModel.getRecordBy());
		rawEntity.setClient(finAccountUIModel.getClient());
		rawEntity.setRecordNote(finAccountUIModel.getRecordNote());
		rawEntity.setRecordLockMSG(finAccountUIModel.getRecordLockMSG());
		rawEntity.setVerifyNote(finAccountUIModel.getVerifyNote());
		rawEntity.setVerifyBy(finAccountUIModel.getVerifyBy());
		rawEntity.setAccountantUUID(finAccountUIModel.getAccountantUUID());
		if (!ServiceEntityStringHelper.checkNullString(finAccountUIModel
				.getAuditTime())) {
			try {
				rawEntity.setAuditTime(DefaultDateFormatConstant.DATE_FORMAT
						.parse(finAccountUIModel.getAuditTime()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setVerifyLock(finAccountUIModel.getVerifyLock());
		rawEntity.setVerifyLockMSG(finAccountUIModel.getVerifyLockMSG());
		rawEntity.setAdjustDirection(finAccountUIModel.getAdjustDirection());
		rawEntity.setVerifyStatus(finAccountUIModel.getVerifyStatus());
		rawEntity.setPaymentType(finAccountUIModel.getPaymentType());
		rawEntity.setAuditNote(finAccountUIModel.getAuditNote());
		rawEntity.setRecordStatus(finAccountUIModel.getRecordStatus());
		rawEntity.setCurrencyCode(finAccountUIModel.getCurrencyCode());
		rawEntity
				.setRefAccountObjectUUID(finAccountUIModel.getAccountantUUID());
		rawEntity.setRefDocumentUUID(finAccountUIModel.getRefDocumentUUID());
		rawEntity.setExchangeRate(finAccountUIModel.getExchangeRate());
		rawEntity.setAmountInSetCurrency(finAccountUIModel
				.getAmountInSetCurrency());
		rawEntity.setToRecordAmountInSetCurrency(finAccountUIModel
				.getToRecordAmountInSetCurrency());
		rawEntity.setRecordedAmountInSetCurrency(finAccountUIModel
				.getRecordedAmountInSetCurrency());
		if (!ServiceEntityStringHelper.checkNullString(finAccountUIModel
				.getVerifyTime())) {
			try {
				rawEntity.setVerifyTime(DefaultDateFormatConstant.DATE_FORMAT
						.parse(finAccountUIModel.getVerifyTime()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convFinAccountLogToUI(FinAccountLog finAccountLog,
			FinAccountLogUIModel finAccountLogUIModel) {
		if (finAccountLog != null) {
			if (!ServiceEntityStringHelper.checkNullString(finAccountLog
					.getUuid())) {
				finAccountLogUIModel.setUuid(finAccountLog.getUuid());
			}
			if (!ServiceEntityStringHelper.checkNullString(finAccountLog
					.getParentNodeUUID())) {
				finAccountLogUIModel.setParentNodeUUID(finAccountLog
						.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(finAccountLog
					.getRootNodeUUID())) {
				finAccountLogUIModel.setRootNodeUUID(finAccountLog
						.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(finAccountLog
					.getClient())) {
				finAccountLogUIModel.setClient(finAccountLog.getClient());
			}
			finAccountLogUIModel.setAuditStatus(finAccountLog.getAuditStatus());
			finAccountLogUIModel.setPreviousAmount(finAccountLog
					.getPreviousAmount());
			finAccountLogUIModel.setId(finAccountLog.getId());
			finAccountLogUIModel.setCurrentAmount(finAccountLog
					.getCurrentAmount());
			finAccountLogUIModel.setName(finAccountLog.getName());
			if (finAccountLog.getFinanceDate() != null) {
				finAccountLogUIModel
						.setFinanceDate(finAccountLog.getFinanceDate() != null ? DefaultDateFormatConstant.DATE_FORMAT.format(java.util.Date.from(finAccountLog.getFinanceDate().atZone(java.time.ZoneId.systemDefault()).toInstant())) : null);
				finAccountLogUIModel.setNote(finAccountLog.getNote());
				finAccountLogUIModel.setActionCode(finAccountLog
						.getActionCode());
			}
		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:finAccountLog
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToFinAccountLog(
			FinAccountLogUIModel finAccountLogUIModel, FinAccountLog rawEntity) {
		if (!ServiceEntityStringHelper.checkNullString(finAccountLogUIModel
				.getUuid())) {
			rawEntity.setUuid(finAccountLogUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper.checkNullString(finAccountLogUIModel
				.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(finAccountLogUIModel
					.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(finAccountLogUIModel
				.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(finAccountLogUIModel.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(finAccountLogUIModel
				.getClient())) {
			rawEntity.setClient(finAccountLogUIModel.getClient());
		}
		rawEntity.setAuditStatus(finAccountLogUIModel.getAuditStatus());
		rawEntity.setPreviousAmount(finAccountLogUIModel.getPreviousAmount());
		rawEntity.setId(finAccountLogUIModel.getId());
		rawEntity.setCurrentAmount(finAccountLogUIModel.getCurrentAmount());
		rawEntity.setUuid(finAccountLogUIModel.getUuid());
		rawEntity.setName(finAccountLogUIModel.getName());
		rawEntity.setClient(finAccountLogUIModel.getClient());
		if (!ServiceEntityStringHelper.checkNullString(finAccountLogUIModel
				.getFinanceDate())) {
			try {
				rawEntity.setFinanceDate(DefaultDateFormatConstant.DATE_FORMAT
						.parse(finAccountLogUIModel.getFinanceDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setNote(finAccountLogUIModel.getNote());
		rawEntity.setActionCode(finAccountLogUIModel.getActionCode());
	}

	public void convFinAccountToItemUI(FinAccount finAccount,
			FinAccountMaterialItemUIModel finAccountMaterialItemUIModel,
			LogonInfo logonInfo) {
		finAccountMaterialItemUIModel.setFinAccountId(finAccount.getId());
		finAccountMaterialItemUIModel.setVerifyStatus(finAccount
				.getVerifyStatus());
		if (logonInfo != null) {
			try {
				Map<Integer, String> auditStatusMap = this
						.initAuditStatusMap(logonInfo.getLanguageCode());
				finAccountMaterialItemUIModel
						.setAuditStatusValue(auditStatusMap.get(finAccount
								.getAuditStatus()));
			} catch (ServiceEntityInstallationException e) {
				// just skip
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, "auditStatus"));
			}
			try {
				Map<Integer, String> verifyStatusMap = this
						.initVerifyStatusMap(logonInfo.getLanguageCode());
				finAccountMaterialItemUIModel
						.setVerifyStatusValue(verifyStatusMap.get(finAccount
								.getAuditStatus()));
			} catch (ServiceEntityInstallationException e) {
				// just skip
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, "verifyStatus"));
			}
			try {
				Map<Integer, String> recordStatusMap = this
						.initRecordStatusMap(logonInfo.getLanguageCode());
				finAccountMaterialItemUIModel
						.setRecordStatusValue(recordStatusMap.get(finAccount
								.getRecordStatus()));
			} catch (ServiceEntityInstallationException e) {
				// just skip
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, "recordStatus"));
			}
		}
	}

	public void convFinAccountMaterialItemToUI(
			FinAccountMaterialItem finAccountMaterialItem,
			FinAccountMaterialItemUIModel finAccountMaterialItemUIModel) {
		convFinAccountMaterialItemToUI(finAccountMaterialItem,
				finAccountMaterialItemUIModel, null);
	}

	public void convFinAccountMaterialItemToUI(
			FinAccountMaterialItem finAccountMaterialItem,
			FinAccountMaterialItemUIModel finAccountMaterialItemUIModel,
			LogonInfo logonInfo) {
		if (finAccountMaterialItem != null
				&& finAccountMaterialItemUIModel != null) {
			docFlowProxy.convDocMatItemToUI(finAccountMaterialItem,
					finAccountMaterialItemUIModel, logonInfo);
		}
	}

	public void convUIToFinAccountMaterialItem(
			FinAccountMaterialItemUIModel finAccountMaterialItemUIModel,
			FinAccountMaterialItem finAccountMaterialItem) {
		convUIToFinAccountMaterialItem(finAccountMaterialItemUIModel,
				finAccountMaterialItem, null);
	}

	public void convUIToFinAccountMaterialItem(
			FinAccountMaterialItemUIModel finAccountMaterialItemUIModel,
			FinAccountMaterialItem finAccountMaterialItem, LogonInfo logonInfo) {
		if (finAccountMaterialItem != null
				&& finAccountMaterialItemUIModel != null) {
			docFlowProxy.convUIToDocMatItem(finAccountMaterialItemUIModel,
					finAccountMaterialItem);
		}
	}

	public void convMaterialSKUToItemUI(
			MaterialStockKeepUnit materialStockKeepUnit,
			FinAccountMaterialItemUIModel finAccountMaterialItemUIModel) {
		if (materialStockKeepUnit != null) {
			docFlowProxy.convMaterialSKUToItemUI(materialStockKeepUnit,
					finAccountMaterialItemUIModel);
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convFinAccountPrerequirementToUI(
			FinAccountPrerequirement finAccountPrerequirement,
			FinAccountUIModel finAccountUIModel) {
		if (finAccountPrerequirement != null) {
			// Don't know logic
		}
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:finAccountPrerequirement
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToFinAccountPrerequirement(
			FinAccountUIModel finAccountUIModel,
			FinAccountPrerequirement rawEntity) {
		if (!ServiceEntityStringHelper.checkNullString(finAccountUIModel
				.getClient())) {
			// Don't know logic
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convFinAccountObjectRefToUI(
			FinAccountObjectRef finAccountObjectRef,
			FinAccountUIModel finAccountUIModel) {
		if (finAccountObjectRef != null) {
			finAccountUIModel.setRefAccountObjectUUID(finAccountObjectRef
					.getRefUUID());
		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:finAccountObjectRef
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToFinAccountObjectRef(
			FinAccountUIModel finAccountUIModel, FinAccountObjectRef rawEntity) {
		if (rawEntity != null) {
			String refSEName = accountManager
					.getAllPossibleAccountObjectSEName(finAccountUIModel
							.getAccountObjectType());
			rawEntity.setRefSEName(refSEName);
			rawEntity.setRefUUID(finAccountUIModel.getRefAccountObjectUUID());
		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:finAccDocRef
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToFinAccDocRef(FinAccountUIModel finAccountUIModel,
			FinAccDocRef rawEntity) {
		if (rawEntity != null) {
			rawEntity.setRefUUID(finAccountUIModel.getRefDocumentUUID());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convFinAccDocRefToUI(FinAccDocRef finAccDocRef,
			FinAccountUIModel finAccountUIModel) {
		if (finAccDocRef != null) {
			finAccountUIModel.setRefDocumentUUID(finAccDocRef.getRefUUID());
		}
	}

	public void convFinAccountObjectToUI(Account finAccountObject,
			FinAccountUIModel finAccountUIModel) {
		convFinAccountObjectToUI(finAccountObject, finAccountUIModel, null);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convFinAccountObjectToUI(Account finAccountObject,
			FinAccountUIModel finAccountUIModel, LogonInfo logonInfo) {
		if (finAccountObject != null) {
			finAccountUIModel.setAccountObjectType(finAccountObject
					.getAccountType());
			if (logonInfo != null) {
				try {
					Map<Integer, String> accountTypeMap = accountManager
							.getAccountTypeMap(logonInfo.getLanguageCode(),
									false);
					if (accountTypeMap != null) {
						finAccountUIModel
								.setAccountObjectTypeValue(accountTypeMap
										.get(finAccountObject.getAccountType()));
					}
				} catch (ServiceEntityInstallationException e) {
					logger.error(ServiceEntityStringHelper
							.genDefaultErrorMessage(e,
									finAccountObject.getName()));
					// just skip
				}
			}
			finAccountUIModel.setAccountObjectName(finAccountObject.getName());
			finAccountUIModel.setAccountObjectId(finAccountObject.getId());
		}
	}

	public void convFinAccountDocumentToUI(ServiceEntityNode documentContent,
			FinAccountUIModel finAccountUIModel) {
		if (documentContent != null) {
			finAccountUIModel.setDocumentId(documentContent.getId());
			finAccountUIModel.setRefDocumentUUID(documentContent.getUuid());
		}
	}

	public void convFinAccountAttachmentToUI(
			FinAccountAttachment finAccountAttachment,
			FinAccountAttachmentUIModel finAccountAttachmentUIModel) {
		if (finAccountAttachment != null) {
			finAccountAttachmentUIModel.setUuid(finAccountAttachment.getUuid());
			finAccountAttachmentUIModel.setAttachmentTitle(finAccountAttachment
					.getName());
			finAccountAttachmentUIModel
					.setAttachmentDescription(finAccountAttachment.getNote());
			finAccountAttachmentUIModel.setFileType(finAccountAttachment
					.getFileType());
		}
	}

	public void convFinAccountMatItemAttachmentToUI(
			FinAccountMatItemAttachment finAccountMatItemAttachment,
			FinAccountMatItemAttachmentUIModel finAccountMatItemAttachmentUIModel) {
		if (finAccountMatItemAttachment != null) {
			finAccountMatItemAttachmentUIModel
					.setUuid(finAccountMatItemAttachment.getUuid());
			finAccountMatItemAttachmentUIModel
					.setAttachmentTitle(finAccountMatItemAttachment.getName());
			finAccountMatItemAttachmentUIModel
					.setAttachmentDescription(finAccountMatItemAttachment
							.getNote());
			finAccountMatItemAttachmentUIModel
					.setFileType(finAccountMatItemAttachment.getFileType());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convCashierToUI(LogonUser cashier,
			FinAccountUIModel finAccountUIModel) {
		if (cashier != null) {
			finAccountUIModel.setCashierName(cashier.getName());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convVerifyByToUI(LogonUser verifyBy,
			FinAccountUIModel finAccountUIModel) {
		if (verifyBy != null) {
			finAccountUIModel.setVerifyByName(verifyBy.getName());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convAuditByToUI(LogonUser auditBy,
			FinAccountUIModel finAccountUIModel) {
		if (auditBy != null) {
			finAccountUIModel.setAuditByName(auditBy.getName());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convRecordByToUI(LogonUser recordBy,
			FinAccountUIModel finAccountUIModel) {
		if (recordBy != null) {
			finAccountUIModel.setRecordByName(recordBy.getName());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convFinAccountTitleToUI(FinAccountTitle finAccountTitle,
			FinAccountUIModel finAccountUIModel)
			throws ServiceEntityInstallationException {
		if (finAccountTitle != null) {

			finAccountUIModel.setFinAccountTitleName(finAccountTitle.getName());
			finAccountUIModel.setFinAccountType(finAccountTitle
					.getFinAccountType());
			Map<Integer, String> finAccountTitleMap = finAccountTitleManager
					.initFinAccountTypeMap();
			finAccountUIModel.setFinAccountTypeValue(finAccountTitleMap
					.get(finAccountTitle.getFinAccountType()));
			finAccountUIModel.setFinAccountTitleId(finAccountTitle.getId());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convAccountantToUI(LogonUser accountant,
			FinAccountUIModel finAccountUIModel) {
		if (accountant != null) {
			finAccountUIModel.setAccountantName(accountant.getName());
		}
	}

	@Override
	public ServiceEntityNode newRootEntityNode(String client)
			throws ServiceEntityConfigureException {
		FinAccount finAccount = (FinAccount) super.newRootEntityNode(client);
		String finAccountID = finAccountIdHelper.genDefaultId(client);
		finAccount.setId(finAccountID);
		finAccount.setExecOrgUUID(finAccount.getResOrgUUID());
		finAccount.setCreatedTime(java.time.LocalDateTime.now());
		return finAccount;
	}

	/**
	 * Logic of setting recorded status by checking the account amount
	 * relationship will be invoked during saving the account module
	 * 
	 * @param finAccount
	 */
	public void setRecordStatusByCheckAmount(FinAccount finAccount) {
		double realAdjustAmount = finAccount.getAdjustAmount();
		if (finAccount.getAdjustDirection() == FinAccount.ADJUST_DISCOUNT) {
			realAdjustAmount = 0 - realAdjustAmount;
		}
		if (finAccount.getAmount() + realAdjustAmount == finAccount
				.getRecordedAmount()) {
			finAccount.setRecordStatus(FinAccount.RECORDED_DONE);
		} else {
			finAccount.setRecordStatus(FinAccount.RECORDED_UNDONE);
		}
	}

	/**
	 * Logic to calculate the record status
	 * 
	 * @return
	 */
	public static int caculateRecordedStatus(FinAccount finAccount) {
		double toRecordAmount = caculateToRecordAmount(finAccount);
		if (toRecordAmount == 0) {
			return FinAccount.RECORDED_DONE;
		} else {
			return FinAccount.RECORDED_UNDONE;
		}
	}

	/**
	 * Core logic to caculate the to record amount by FinAccount model
	 * 
	 * @param finAccount
	 * @return
	 */
	public static double caculateToRecordAmount(FinAccount finAccount) {
		double realAdjustAmount = finAccount.getAdjustAmount();
		if (finAccount.getAdjustDirection() == FinAccount.ADJUST_DISCOUNT) {
			realAdjustAmount = 0 - realAdjustAmount;
		}
		return caculateToRecordAmount(finAccount.getAmount(),
				finAccount.getRecordedAmount(), realAdjustAmount);
	}

	/**
	 * Logic to calculate the To record amount by 3 amount
	 * 
	 * @param amount
	 * @param recordedAmount
	 * @return
	 */
	public static double caculateToRecordAmount(double amount,
			double recordedAmount, double realAdjustAmount) {
		double toRecordAmount = amount + realAdjustAmount - recordedAmount;
		if (toRecordAmount < 0) {
			return 0;
		}
		return toRecordAmount;
	}

	/**
	 * Get the account list by the document information
	 * 
	 * @param documentType
	 * @param documentUUID
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceEntityInstallationException
	 * @throws NodeNotFoundException
	 * @throws SearchConfigureException
	 */
	public List<ServiceEntityNode> getAccountListByDocument(int documentType,
			String documentUUID, String client)
			throws ServiceEntityConfigureException, NodeNotFoundException,
			ServiceEntityInstallationException {
		List<ServiceEntityNode> rawFinDocReflist = getEntityNodeListByKey(
				documentUUID, IReferenceNodeFieldConstant.REFUUID,
				FinAccDocRef.NODENAME, client, null);
		if (ServiceCollectionsHelper.checkNullList(rawFinDocReflist)) {
			return null;
		}
		List<ServiceEntityNode> result = new ArrayList<ServiceEntityNode>();
		for (ServiceEntityNode seNode : rawFinDocReflist) {
			FinAccount finAccount = (FinAccount) getEntityNodeByKey(
					seNode.getRootNodeUUID(),
					IServiceEntityNodeFieldConstant.UUID, FinAccount.NODENAME,
					client, null);
			if (finAccount != null) {
				result.add(finAccount);
			}
		}
		return result;
	}

	/**
	 * Get the Account from Document information from backend, OR filter from
	 * online list
	 * 
	 * @param documentType
	 * @param documentUUID
	 * @param accountTitleID
	 * @param rawAccList
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceEntityInstallationException
	 * @throws NodeNotFoundException
	 * @throws SearchConfigureException
	 */
	// public FinAccount getAccountByDocument(int documentType,
	// String documentUUID, String accountTitleID,
	// List<ServiceEntityNode> rawAccList)
	// throws ServiceEntityConfigureException, SearchConfigureException,
	// NodeNotFoundException, ServiceEntityInstallationException {
	// List<ServiceEntityNode> accountBackList = new
	// ArrayList<ServiceEntityNode>();
	// if (rawAccList == null || rawAccList.size() == 0) {
	// accountBackList = getAccountListByDocument(documentType,
	// documentUUID);
	// } else {
	// accountBackList = rawAccList;
	// }
	// FinAccountTitle accountTitle = (FinAccountTitle) finAccountTitleManager
	// .getEntityNodeByKey(accountTitleID,
	// IServiceEntityNodeFieldConstant.ID,
	// FinAccountTitle.NODENAME, null);
	// if (accountTitle == null) {
	//
	// }
	// for (ServiceEntityNode seNode : accountBackList) {
	// FinAccount account = (FinAccount) seNode;
	// if (accountTitle.getUuid().equals(account.getAccountTitleUUID())) {
	// // In case account title matches
	// return account;
	// }
	// }
	// return null;
	// }

	public FinAccount getAccountByDocTitleUUID(int documentType,
			String documentUUID, String accountTitleUUID, String client,
			List<ServiceEntityNode> rawAccList,
			List<ServiceEntityNode> rawFinRefDocList)
			throws ServiceEntityConfigureException, SearchConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<ServiceEntityNode> accountBackList = new ArrayList<ServiceEntityNode>();
		boolean backEndFlag = rawAccList == null || rawFinRefDocList == null;
		if (backEndFlag) {
			accountBackList = getAccountListByDocument(documentType,
					documentUUID, client);
		} else {
			accountBackList = getOnlineFinAccListByDocument(rawAccList,
					rawFinRefDocList, documentUUID);
		}
		if (accountBackList == null || accountBackList.size() == 0) {
			return null;
		}
		for (ServiceEntityNode seNode : accountBackList) {
			FinAccount finAccount = (FinAccount) seNode;
			if (accountTitleUUID.equals(finAccount.getAccountTitleUUID())) {
				return finAccount;
			}
		}
		return null;
	}

	protected List<ServiceEntityNode> getOnlineFinAccListByDocument(
			List<ServiceEntityNode> rawAccList,
			List<ServiceEntityNode> rawFinRefDocList, String documentUUID) {
		List<ServiceEntityNode> accountBackList = new ArrayList<ServiceEntityNode>();
		for (ServiceEntityNode refDocNode : rawFinRefDocList) {
			FinAccDocRef finAccDocRef = (FinAccDocRef) refDocNode;
			if (documentUUID.equals(finAccDocRef.getRefUUID())) {
				FinAccount finAccount = getOnlineFinAccount(rawAccList,
						finAccDocRef.getRootNodeUUID());
				if (finAccount != null) {
					accountBackList.add(finAccount);
				}
			}
		}
		if (accountBackList.size() == 0) {
			return null;
		}
		return accountBackList;
	}

	protected FinAccount getOnlineFinAccount(
			List<ServiceEntityNode> rawAccList, String uuid) {
		if (rawAccList == null || rawAccList.size() == 0) {
			return null;
		}
		for (ServiceEntityNode seNode : rawAccList) {
			if (uuid.equals(seNode.getUuid())) {
				return (FinAccount) seNode;
			}
		}
		return null;
	}

	public ServiceComChartModel genComFinChartModel(
			List<String> accTitleUUIDList, int unit,
			List<String> accObjectUUIDList, String client, Date startDate,
			Date endDate) throws FinAccountException, SearchConfigureException,
			ServiceEntityConfigureException, NodeNotFoundException,
			ServiceEntityInstallationException, IOException {
		ServiceComChartModel serviceComChartModel = new ServiceComChartModel();
		serviceComChartModel.setyAxisTitle(financeAccountMessageHelper
				.getMessage(FinanceAccountMessageHelper.YAIXSLABLE_GEN_FIN));
		List<String> categories = new ArrayList<String>();
		List<ServiceChartDataSeries> dataSeries = new ArrayList<ServiceChartDataSeries>();

		/**
		 * [Step1] build empty data series list
		 */
		for (String objectUUID : accObjectUUIDList) {
			ServiceChartDataSeries serviceChartDataSeries = new ServiceChartDataSeries();
			// get the object reference
			ServiceEntityNode accObject = getAllPossibleAccountObject(
					objectUUID, client);
			if (accObject != null) {
				serviceChartDataSeries.setObjectName(accObject.getName());
				serviceChartDataSeries.setObjectUUID(objectUUID);
				dataSeries.add(serviceChartDataSeries);
			}
		}
		/**
		 * [Step2] get all the finance raw data
		 */
		if (accObjectUUIDList == null || accObjectUUIDList.size() == 0) {
			return null;
		}
		FinAccountTitle finAccountTitle = (FinAccountTitle) finAccountTitleManager
				.getEntityNodeByKey(accObjectUUIDList.get(0),
						IServiceEntityNodeFieldConstant.UUID,
						FinAccountTitle.NODENAME, client, null);
		for (String finAccountObjectUUID : accObjectUUIDList) {
			for (String accountTitleUUID : accTitleUUIDList) {
				FinAccountTitle tmpAccountTitle = (FinAccountTitle) finAccountTitleManager
						.getEntityNodeByKey(accountTitleUUID,
								IServiceEntityNodeFieldConstant.UUID,
								FinAccountTitle.NODENAME, client, null);
				boolean positiveFlag = true;
				if (tmpAccountTitle.getFinAccountType() != finAccountTitle
						.getFinAccountType()) {
					positiveFlag = false;
				}
				processEachTitleObjectUnion(finAccountTitle, positiveFlag,
						finAccountObjectUUID, unit, startDate, endDate, client,
						categories, dataSeries);
			}
		}
		if (finAccountTitle != null) {
			String comTitleName = finAccountTitle.getId() + "-"
					+ finAccountTitle.getName();
			serviceComChartModel.setTitle(comTitleName);
		}
		serviceComChartModel.setCategories(categories);
		serviceComChartModel.setDataSeries(dataSeries);
		return serviceComChartModel;
	}

	/**
	 * [Internal method] process each fin Account object and each main account
	 * title union
	 * 
	 * @param finAccountTitle
	 * @param finAccountObjectUUID
	 * @param unit
	 * @param startDate
	 * @param endDate
	 * @throws SearchConfigureException
	 * @throws ServiceEntityConfigureException
	 * @throws NodeNotFoundException
	 * @throws ServiceEntityInstallationException
	 */
	protected void processEachTitleObjectUnion(FinAccountTitle finAccountTitle,
			boolean positiveFlag, String finAccountObjectUUID, int unit,
			Date startDate, Date endDate, String client,
			List<String> categories, List<ServiceChartDataSeries> dataSeries)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<ServiceChartTimeSlot> tsList = ServiceChartHelper
				.genChartTimeSlots(unit, startDate, endDate);
		/**
		 * [Step1] get the all the raw finAccountTitle
		 */
		FinAccountSearchModel searchModel = new FinAccountSearchModel();
		searchModel.setRefObjUUID(finAccountObjectUUID);
		searchModel.setFinAccountTitleId(finAccountTitle.getId());
		searchModel.setCreatedTimeLow(startDate);
		searchModel.setCreatedTimeHigh(endDate);
		List<ServiceEntityNode> tmpResult = null;
		// Replace this search logic
//		List<ServiceEntityNode> tmpResult = this.getSearchProxy()
//				.searchDocList(searchModel, finAccountTitle.getClient());
		// Check and search the sub fin account title
		List<ServiceEntityNode> subAccTitleList = finAccountTitleManager
				.getSubAccountTitleList(finAccountTitle.getUuid(), client);
		if (subAccTitleList != null && subAccTitleList.size() > 0) {
			for (ServiceEntityNode seNode : subAccTitleList) {
				FinAccountTitle subTitle = (FinAccountTitle) seNode;
				List<ServiceEntityNode> subResult = searchSubFinAccountListRecursive(
						searchModel, subTitle, client);
				bsearchService.mergeToRawList(tmpResult, subResult);
			}
		}
		/**
		 * [Step2] For each time slot allocate the raw list into each object and
		 * timeslot
		 */
		for (ServiceChartTimeSlot tsSlot : tsList) {
			Map<String, Double> result = new HashMap<String, Double>();
			List<ServiceEntityNode> filterFinAccountList = filterFinAccountListByTimeSlot(
					tmpResult, tsSlot.getStartDate(), tsSlot.getEndDate());
			categories.add(tsSlot.getxTimeSlotLabel());
			double sumValue = 0;
			for (ServiceEntityNode tmpNode : filterFinAccountList) {
				FinAccount finAccount = (FinAccount) tmpNode;
				if (positiveFlag) {
					sumValue = sumValue + finAccount.getAmount();
				} else {
					sumValue = sumValue - finAccount.getAmount();
				}
			}
			result.put(finAccountObjectUUID, sumValue);
			Iterator<Entry<String, Double>> iter = result.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, Double> entry = (Map.Entry<String, Double>) iter
						.next();
				double value = entry.getValue();
				String key = entry.getKey();
				this.mergeDataSeries(dataSeries, key, value);
			}
		}
	}

	public ServiceComChartModel genComFinChartModel(String accTitleUUID,
			int unit, List<String> accObjectUUIDList, String client,
			Date startDate, Date endDate) throws FinAccountException,
			SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException,
			IOException {
		ServiceComChartModel serviceComChartModel = new ServiceComChartModel();
		serviceComChartModel.setyAxisTitle(financeAccountMessageHelper
				.getMessage(FinanceAccountMessageHelper.YAIXSLABLE_GEN_FIN));
		List<String> categories = new ArrayList<String>();
		List<ServiceChartDataSeries> dataSeries = new ArrayList<ServiceChartDataSeries>();
		/**
		 * [Step1] build empty data series list
		 */
		for (String objectUUID : accObjectUUIDList) {
			ServiceChartDataSeries serviceChartDataSeries = new ServiceChartDataSeries();
			// get the object reference
			ServiceEntityNode accObject = getAllPossibleAccountObject(
					objectUUID, client);
			if (accObject != null) {
				serviceChartDataSeries.setObjectName(accObject.getName());
				serviceChartDataSeries.setObjectUUID(objectUUID);
				dataSeries.add(serviceChartDataSeries);
			}
		}
		/**
		 * [Step2] get all the finance raw data
		 */
		FinAccountTitle finAccountTitle = (FinAccountTitle) finAccountTitleManager
				.getEntityNodeByKey(accTitleUUID,
						IServiceEntityNodeFieldConstant.UUID,
						FinAccountTitle.NODENAME, client, null);
		if (finAccountTitle == null) {
			// should raise exception for null account title
		}
		for (String finAccountObjectUUID : accObjectUUIDList) {
			processEachTitleObjectUnion(finAccountTitle, true,
					finAccountObjectUUID, unit, startDate, endDate, client,
					categories, dataSeries);
		}
		if (finAccountTitle != null) {
			String comTitleName = finAccountTitle.getId() + "-"
					+ finAccountTitle.getName();
			serviceComChartModel.setTitle(comTitleName);
		}
		serviceComChartModel.setCategories(categories);
		serviceComChartModel.setDataSeries(dataSeries);
		return serviceComChartModel;
	}

	/**
	 * [Internal method] only used for fin account chart, Recursive method to
	 * get all finAccount list
	 * 
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceEntityInstallationException
	 * @throws NodeNotFoundException
	 * @throws SearchConfigureException
	 */
	protected List<ServiceEntityNode> searchSubFinAccountListRecursive(
			FinAccountSearchModel searchModel, FinAccountTitle finAccountTitle,
			String client) throws ServiceEntityConfigureException,
			SearchConfigureException, NodeNotFoundException,
			ServiceEntityInstallationException {
		searchModel.setFinAccountTitleId(finAccountTitle.getId());
		//TODO to replace this logic
		List<ServiceEntityNode> curResultList = null;
//		List<ServiceEntityNode> curResultList = this.getSearchProxy()
//				.searchDocList(searchModel, client);
		if (curResultList == null) {
			curResultList = new ArrayList<ServiceEntityNode>();
		}
		// Check the sub fin account title
		List<ServiceEntityNode> subAccTitleList = finAccountTitleManager
				.getSubAccountTitleList(finAccountTitle.getUuid(), client);
		if (subAccTitleList != null && subAccTitleList.size() > 0) {
			for (ServiceEntityNode seNode : subAccTitleList) {
				FinAccountTitle subTitle = (FinAccountTitle) seNode;
				bsearchService.mergeToRawList(
						curResultList,
						searchSubFinAccountListRecursive(searchModel, subTitle,
								client));
			}
		}
		return curResultList;
	}

	/**
	 * [Internal method] only used for fin account chart, for filter out the
	 * finAccount list by each time slot
	 * 
	 * @return
	 */
	protected List<ServiceEntityNode> filterFinAccountListByTimeSlot(
			List<ServiceEntityNode> rawFinAccountList, Date startDate,
			Date endDate) {
		List<ServiceEntityNode> result = new ArrayList<ServiceEntityNode>();
		for (ServiceEntityNode seNode : rawFinAccountList) {
			long toStartDiff = startDate.getTime()
					- seNode.getCreatedTime().atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
			long toEndDiff = seNode.getCreatedTime().atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli()
					- endDate.getTime();
			if (toStartDiff > 0 || toEndDiff > 0) {
				continue;
			}
			result.add(seNode);
		}
		return result;
	}

	/**
	 * Get all the possible account object instance
	 * 
	 * @param accountObjectUUID
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public Account getAllPossibleAccountObject(String accountObjectUUID,
			String client) throws ServiceEntityConfigureException {
		FinAccountObjectRef finAccountObjectRef = (FinAccountObjectRef) this
				.getEntityNodeByKey(accountObjectUUID,
						IReferenceNodeFieldConstant.REFUUID,
						FinAccountObjectRef.NODENAME, client, null);
		if (finAccountObjectRef != null) {
			return getAllPossibleAccountObject(finAccountObjectRef);
		}
		return null;
	}

	/**
	 * Get all the possible account object instance
	 * 
	 * @param accountObjectUUID
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public Account getAllPossibleAccountObject(String accountObjectUUID,
			int accountType, String client)
			throws ServiceEntityConfigureException {
		return accountManager.getAllPossibleAccountObject(accountObjectUUID,
				accountType, client);
	}

	/**
	 * Get all the possible account object instance
	 * 
	 * @param accountObjectUUID
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public Account getAllPossibleAccountObject(
			FinAccountObjectRef finAccountObjectRef)
			throws ServiceEntityConfigureException {
		return accountManager.getAllPossibleAccountObject(finAccountObjectRef);
	}

	/**
	 * [Internal method] Merge value to data series list by checking existence
	 * of object UUID
	 * 
	 * @param dataSeries
	 * @param objectUUID
	 * @param value
	 */
	protected void mergeDataSeries(List<ServiceChartDataSeries> dataSeries,
			String objectUUID, Double value) {
		for (ServiceChartDataSeries serviceChartDataSeries : dataSeries) {
			if (objectUUID.equals(serviceChartDataSeries.getObjectUUID())) {
				serviceChartDataSeries.getValueList().add(value);
			}
		}
	}

	/**
	 * create new compound finance Account Entity, in case need to create a new
	 * Finance account, including the root node instance, the creation type
	 * node, and the reference to account object
	 * 
	 * @param finAccountObject
	 * @param amount
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> newComAccountEntity(
			Account finAccountObject, String client, double amount)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
		FinAccount finAccount = (FinAccount) this.newRootEntityNode(client);
		finAccount.setAmount(amount);
		resultList.add(finAccount);
		FinAccountLog finAccountLog = (FinAccountLog) this.newEntityNode(
				finAccount, FinAccountLog.NODENAME);
		finAccountLog.setPreviousAmount(0);
		finAccountLog.setCurrentAmount(amount);
		finAccountLog.setActionCode(ServiceEntityBindModel.PROCESSMODE_CREATE);
		resultList.add(finAccountLog);
		FinAccountObjectRef finAccountObjectRef = (FinAccountObjectRef) this
				.newEntityNode(finAccount, FinAccountObjectRef.NODENAME);
		this.buildReferenceNode(finAccountObject, finAccountObjectRef,
				ServiceEntityFieldsHelper.getCommonPackage(finAccountObject
						.getClass()));
		resultList.add(finAccountObjectRef);
		return resultList;
	}

	/**
	 * compound method to [insert/update] from exterier fee settle item to
	 * generate account information
	 * 
	 * @param finAccountUUID
	 *            : relative finance Account UUID, if this is null value, then
	 *            have to create new finance Account compound entity
	 * @param accountType
	 *            :[credit] or [debit]
	 * @param documentType
	 *            :the source of this Account
	 * @param finAccountObject
	 * @param accountTitleID
	 * @param curAmount
	 * @param preAmount
	 * @param logonUserUUID
	 * @param organizationUUID
	 * @throws ServiceEntityConfigureException
	 */
	public FinAccount updateToAccountComInfo(String finAccountUUID,
			int finAccountType, int documentType, DocumentContent refDocument,
			Account finAccountObject, String accountTitleID, double curAmount,
			double preAmount, String logonUserUUID, String organizationUUID,
			String client) throws ServiceEntityConfigureException {
		FinAccount finAccount = new FinAccount();
		int processType = 0;
		// decide the processType
		if (finAccountUUID == null) {
			processType = ServiceEntityBindModel.PROCESSMODE_CREATE;
			finAccount = (FinAccount) newRootEntityNode(client);
		} else {
			FinAccount tmpAccount = (FinAccount) getEntityNodeByKey(
					finAccountUUID, IServiceEntityNodeFieldConstant.UUID,
					FinAccount.NODENAME, client, null);
			if (tmpAccount == null) {
				processType = ServiceEntityBindModel.PROCESSMODE_CREATE;
				finAccount = (FinAccount) newRootEntityNode(client);
			} else {
				processType = ServiceEntityBindModel.PROCESSMODE_UPDATE;
				finAccount = tmpAccount;
			}
		}
		// Get Account Title instance
		FinAccountTitle accountTitle = (FinAccountTitle) this.finAccountTitleManager
				.getEntityNodeByKey(accountTitleID,
						IServiceEntityNodeFieldConstant.ID,
						FinAccountTitle.NODENAME, client, null, true);
		// Update
		finAccount.setDocumentType(documentType);
		finAccount.setAccountTitleUUID(accountTitle.getUuid());
		if (processType == ServiceEntityBindModel.PROCESSMODE_CREATE) {
			insertComAccountEntity(finAccount, finAccountObject, refDocument,
					curAmount, logonUserUUID, organizationUUID);
		} else {
			// In case the account already exist, then just update compound
			// account entity, including log generation and update account
			// object reference
			updateComAccountEntity(finAccount, finAccountObject, refDocument,
					curAmount, preAmount, logonUserUUID, organizationUUID,
					client);
		}
		return finAccount;
	}

	/**
	 * Insert the Finance Account compound information, including insert new Fin
	 * Account log and build the Account object reference
	 * 
	 * @param finAccount
	 * @param finAccountObject
	 * @param curAmount
	 * @param preAmount
	 * @param logonUserUUID
	 * @param organizationUUID
	 * @throws ServiceEntityConfigureException
	 */
	public void insertComAccountEntity(FinAccount finAccount,
			ServiceEntityNode finAccountObject, ServiceEntityNode refDocument,
			double curAmount, String logonUserUUID, String organizationUUID)
			throws ServiceEntityConfigureException {
		finAccount.setAmount(curAmount);
		if (finAccountObject != null) {
			finAccount.setRefAccountObjectUUID(finAccountObject.getUuid());
		}
		if (refDocument != null) {
			finAccount.setRefDocumentUUID(refDocument.getUuid());
		}
		this.insertSENode(finAccount, logonUserUUID, organizationUUID);
		// this.insertSENode(finAccount, logonUserUUID, organizationUUID);
		FinAccountLog finAccountLog = (FinAccountLog) this.newEntityNode(
				finAccount, FinAccountLog.NODENAME);
		finAccountLog.setPreviousAmount(0);
		finAccountLog.setCurrentAmount(curAmount);
		finAccountLog.setActionCode(ServiceEntityBindModel.PROCESSMODE_CREATE);
		this.insertSENode(finAccountLog, logonUserUUID, organizationUUID);
		if (finAccountObject != null) {
			FinAccountObjectRef finAccountObjectRef = (FinAccountObjectRef) this
					.newEntityNode(finAccount, FinAccountObjectRef.NODENAME);
			this.buildReferenceNode(finAccountObject, finAccountObjectRef,
					ServiceEntityFieldsHelper.getCommonPackage(finAccountObject
							.getClass()));
			this.insertSENode(finAccountObjectRef, logonUserUUID,
					organizationUUID);
		}
		if (refDocument != null) {
			FinAccDocRef finAccDocRef = (FinAccDocRef) this.newEntityNode(
					finAccount, FinAccDocRef.NODENAME);
			this.buildReferenceNode(refDocument, finAccDocRef,
					ServiceEntityFieldsHelper.getCommonPackage(refDocument
							.getClass()));
			this.insertSENode(finAccDocRef, logonUserUUID, organizationUUID);
		}
	}

	/**
	 * Delete all relative fin account by document
	 * 
	 * @param documentType
	 * @param documentUUID
	 * @throws SearchConfigureException
	 * @throws ServiceEntityConfigureException
	 * @throws NodeNotFoundException
	 * @throws ServiceEntityInstallationException
	 */
	public void deleteFinAccountByDocument(int documentType,
			String documentUUID, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		FinAccountSearchModel finAccountSearchModel = new FinAccountSearchModel();
		finAccountSearchModel.setDocumentType(documentType);
		finAccountSearchModel.setRefDocumentUUID(documentUUID);
		List<ServiceEntityNode> searchResult = this.getAccountListByDocument(
				documentType, documentUUID, client);
		if (searchResult == null || searchResult.size() == 0) {
			return;
		}
		for (ServiceEntityNode seNode : searchResult) {
			admDeleteFinAccountUnion(seNode.getUuid());
		}
	}

	public List<ServiceEntityNode> getRawFinAccountListByDocument(
			String documentUUID, int documentType, String client)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> rawFinAccDocRefList = getEntityNodeListByKey(
				documentUUID, IReferenceNodeFieldConstant.REFUUID,
				FinAccDocRef.NODENAME, client, null);
		List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
		if (ServiceCollectionsHelper.checkNullList(rawFinAccDocRefList)) {
			for (ServiceEntityNode tmpSENode : rawFinAccDocRefList) {
				FinAccount finAccount = (FinAccount) getEntityNodeByKey(
						tmpSENode.getRootNodeUUID(),
						IServiceEntityNodeFieldConstant.UUID,
						FinAccount.NODENAME, client, null);
				if (finAccount != null
						&& finAccount.getDocumentType() == documentType) {
					resultList.add(finAccount);
				}
			}
		}
		return null;
	}

	public FinAccount filterFinAccountByDocument(
			List<ServiceEntityNode> rawFinAccountList,
			String finAccountTitleUUID, String accountObjectUUID)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
		if (!ServiceCollectionsHelper.checkNullList(rawFinAccountList)) {
			for (ServiceEntityNode seNode : rawFinAccountList) {
				FinAccount finAccount = (FinAccount) seNode;
				if (finAccountTitleUUID
						.equals(finAccount.getAccountTitleUUID())) {
					resultList.add(finAccount);
				}
			}
		}
		if (resultList.size() == 0) {
			return null;
		}
		if (resultList.size() == 1) {
			return (FinAccount) resultList.get(0);
		}
		if (ServiceEntityStringHelper.checkNullString(accountObjectUUID)) {
			return (FinAccount) resultList.get(0);
		}
		for (ServiceEntityNode seNode : resultList) {
			// In case more than one finaccount match finAccount title, then
			// need to analyze account object
			FinAccountObjectRef finAccountObjectRef = (FinAccountObjectRef) getEntityNodeByKey(
					seNode.getUuid(),
					IServiceEntityNodeFieldConstant.ROOTNODEUUID,
					FinAccount.NODENAME, seNode.getClient(), null);
			if (finAccountObjectRef.getRefUUID().equals(accountObjectUUID)) {
				return (FinAccount) seNode;
			}
		}
		return null;
	}

	/**
	 * The general API to update or insert new Fin Account instance by situation
	 * from reference document
	 * 
	 * @param curValue
	 * @param documentType
	 * @param document
	 * @param finAccountObject
	 * @param execOrgUUID
	 * @param resOrgUUID
	 * @param logonUserUUID
	 * @param finAccountTitleID
	 * @throws ServiceEntityInstallationException
	 * @throws NodeNotFoundException
	 * @throws ServiceEntityConfigureException
	 * @throws SearchConfigureException
	 * @throws FinAccountException
	 */
	public FinAccount updateStandardFinAccountByDocument(double curAmount,
			int documentType, ServiceEntityNode document,
			List<ServiceEntityNode> rawFinAccountList,
			ServiceEntityNode finAccountObject, String execOrgUUID,
			String resOrgUUID, String logonUserUUID,
			FinAccountTitle finAccountTitle, boolean auditLock,
			boolean verifyLock, boolean recordLock, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException,
			FinAccountException {
		/*
		 * [Step1] Get the back Fin Account by information from document
		 */
		FinAccount finAccountBack = null;
		if (finAccountObject != null) {
			finAccountBack = filterFinAccountByDocument(rawFinAccountList,
					finAccountTitle.getUuid(), finAccountObject.getUuid());
		} else {
			finAccountBack = filterFinAccountByDocument(rawFinAccountList,
					finAccountTitle.getUuid(), null);
		}
		int processMode = ServiceEntityBindModel.PROCESSMODE_UPDATE;
		if (finAccountBack == null) {
			if (curAmount == 0) {
				return finAccountBack;
			}
			processMode = ServiceEntityBindModel.PROCESSMODE_CREATE;
		}
		/*
		 * [Step2] Set Relative Accountant, Cashier
		 */
		String accountantUUID = ServiceEntityStringHelper.EMPTYSTRING;
		String cashierUUID = ServiceEntityStringHelper.EMPTYSTRING;
		LogonUser accountant = organizationManager.getOrganizationAccountant(
				resOrgUUID, document.getClient());
		LogonUser cashier = organizationManager.getOrganizationCashier(
				resOrgUUID, document.getClient());
		if (accountant != null) {
			accountantUUID = accountant.getUuid();
		}
		if (cashier != null) {
			cashierUUID = cashier.getUuid();
		}
		if (processMode == ServiceEntityBindModel.PROCESSMODE_CREATE) {
			FinAccount finAccount = (FinAccount) newRootEntityNode(document
					.getClient());
			finAccount.setAccountTitleUUID(finAccountTitle.getUuid());
			finAccount.setAccountObject(finAccountObject.getUuid());
			finAccount.setPaymentType(FinAccount.PAYMENT_BANK);
			finAccount.setDocumentType(documentType);
			finAccount.setAuditLock(auditLock);
			finAccount.setVerifyLock(verifyLock);
			finAccount.setRecordLock(recordLock);
			finAccount.setAccountantUUID(accountantUUID);
			finAccount.setCashierUUID(cashierUUID);
			finAccount.setExecOrgUUID(execOrgUUID);
			finAccount.setRecordedAmount(0);
			// In case the Fin Account update from document, the recorded amount
			// is set to 0
			double toRecordAmount = FinAccountManager.caculateToRecordAmount(
					curAmount, 0, 0);
			finAccount.setToRecordAmount(toRecordAmount);
			/*
			 * [Step3] Core Execution to insert new Fin Account
			 */
			insertComAccountEntity(finAccount, finAccountObject, document,
					curAmount, logonUserUUID, resOrgUUID);
			return finAccount;
		}
		if (processMode == ServiceEntityBindModel.PROCESSMODE_UPDATE) {
			double preAmount = finAccountBack.getAmount();
			FinAccount finAccount = finAccountBack;
			finAccount.setAccountTitleUUID(finAccountTitle.getUuid());
			finAccount.setAccountObject(document.getUuid());
			finAccount.setDocumentType(documentType);
			finAccount.setAuditLock(auditLock);
			finAccount.setVerifyLock(verifyLock);
			finAccount.setRecordLock(recordLock);
			finAccount.setAccountantUUID(accountantUUID);
			finAccount.setCashierUUID(cashierUUID);
			finAccount.setExecOrgUUID(execOrgUUID);
			updateFinAccStandardLOCK(finAccount);
			// Set To Record Amount and recorded status
			double toRecordAmount = FinAccountManager
					.caculateToRecordAmount(finAccount);
			finAccount.setToRecordAmount(toRecordAmount);
			// Special process for checking the recorded status
			setRecordStatusByCheckAmount(finAccount);
			if (curAmount == 0) {
				finAccount.setRecordStatus(FinAccount.RECORDED_DONE);
			}
			/*
			 * [Step3] Core Execution to update Fin Account
			 */
			updateComAccountEntity(finAccount, finAccountObject, document,
					curAmount, preAmount, logonUserUUID, resOrgUUID, client);
			return finAccount;
		}
		return finAccountBack;
	}

	protected void updateFinAccStandardLOCK(FinAccount finAccount) {
		// Case 1: if audit is done then unlock the verify lock
		if (finAccount.getAuditStatus() == FinAccount.AUDIT_DONE) {
			finAccount.setVerifyLock(false);
		}
		// Case 2: if verify is done then unlock the record lock
		if (finAccount.getVerifyStatus() == FinAccount.VERIFIED_DONE) {
			finAccount.setRecordLock(false);
		}
		if (finAccount.getVerifyLock()) {
			// finAccount.setVerifyLockMSG(verifyLockMSG);
		}
		if (finAccount.getRecordLock()) {
			// finAccount.setRecordLockMSG(verifyLockMSG);
		}
	}

	/**
	 * update the Finance Account compound information, including insert new Fin
	 * Account log and update the Account object
	 * 
	 * @param finAccount
	 * @param finAccountObject
	 *            : the updated finance object, if value is null, don't need to
	 *            update account object
	 * @param curAmount
	 *            :the current updated Account value
	 * @param preAmount
	 *            :the previous settle Account value
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public void updateComAccountEntity(FinAccount finAccount,
			ServiceEntityNode finAccountObject, ServiceEntityNode refDocument,
			double curAmount, double preAmount, String logonUserUUID,
			String organizationUUID, String client)
			throws ServiceEntityConfigureException {
		finAccount.setAmount(curAmount);
		/*
		 * [step1] insert fin account log
		 */
		if (curAmount != preAmount) {
			// In case finance amount changes, then create new finance Account
			// log
			FinAccountLog finAccountLog = (FinAccountLog) this.newEntityNode(
					finAccount, FinAccountLog.NODENAME);
			finAccountLog.setPreviousAmount(preAmount);
			finAccountLog.setCurrentAmount(curAmount);
			finAccountLog
					.setActionCode(ServiceEntityBindModel.PROCESSMODE_UPDATE);
			// update fin account into persistence
			this.insertSENode(finAccountLog, logonUserUUID, organizationUUID);
		}
		if (finAccountObject != null) {
			FinAccountObjectRef finAccountObjectRef = new FinAccountObjectRef();
			FinAccountObjectRef tmpAccountObjectRef = (FinAccountObjectRef) this
					.getEntityNodeByKey(finAccount.getUuid(),
							IServiceEntityNodeFieldConstant.ROOTNODEUUID,
							FinAccountObjectRef.NODENAME, client, null);
			if (tmpAccountObjectRef == null) {
				// In case have to insert new fin account object reference
				// instance
				finAccountObjectRef = (FinAccountObjectRef) this.newEntityNode(
						finAccount, FinAccountObjectRef.NODENAME);
				this.buildReferenceNode(finAccountObject, finAccountObjectRef,
						ServiceEntityFieldsHelper
								.getCommonPackage(finAccountObject.getClass()));
				// insert into persistence
				this.insertSENode(finAccountObjectRef, logonUserUUID,
						organizationUUID);
			} else {
				finAccountObjectRef = tmpAccountObjectRef;
				// Update into persistence
				this.buildReferenceNode(finAccountObject, finAccountObjectRef,
						ServiceEntityFieldsHelper
								.getCommonPackage(finAccountObject.getClass()));
				this.updateSENode(finAccountObjectRef, logonUserUUID,
						organizationUUID);
			}
		}
		if (refDocument != null) {
			FinAccDocRef finAccDocRef = new FinAccDocRef();
			FinAccDocRef tmpAccDocRef = (FinAccDocRef) this.getEntityNodeByKey(
					finAccount.getUuid(),
					IServiceEntityNodeFieldConstant.ROOTNODEUUID,
					FinAccDocRef.NODENAME, client, null);
			if (tmpAccDocRef == null) {
				// In case have to insert new fin account object reference
				// instance
				finAccDocRef = (FinAccDocRef) this.newEntityNode(finAccount,
						FinAccDocRef.NODENAME);
				this.buildReferenceNode(refDocument, finAccDocRef,
						ServiceEntityFieldsHelper.getCommonPackage(refDocument
								.getClass()));
				// insert into persistence
				this.insertSENode(finAccDocRef, logonUserUUID, organizationUUID);
			} else {
				finAccDocRef = tmpAccDocRef;
				// Update into persistence
				this.buildReferenceNode(refDocument, finAccDocRef,
						ServiceEntityFieldsHelper.getCommonPackage(refDocument
								.getClass()));
				this.updateSENode(finAccDocRef, logonUserUUID, organizationUUID);
			}
		}
		// filter with default accountant and cashier
		if (finAccount.getAccountantUUID() == null
				|| finAccount.getAccountantUUID().endsWith(
						ServiceEntityStringHelper.EMPTYSTRING)) {
			this.filterWithAccountant(finAccount, organizationUUID);
		}
		// filter with default accountant and cashier
		if (finAccount.getCashierUUID() == null
				|| finAccount.getCashierUUID().endsWith(
						ServiceEntityStringHelper.EMPTYSTRING)) {
			this.filterWithCashiser(finAccount, organizationUUID);
		}
		filterWithDefAccCashier(finAccount, organizationUUID);
		this.updateSENode(finAccount, logonUserUUID, organizationUUID);
	}

	/**
	 * Fast search only by the very core search condition
	 * 
	 * @param searchModel
	 * @return
	 * @throws SearchConfigureException
	 * @throws ServiceEntityInstallationException
	 * @throws ServiceEntityConfigureException
	 * @throws NodeNotFoundException
	 */
	public List<ServiceEntityNode> searchFinRootCore(
			FinAccountSearchModel searchModel, String client)
			throws SearchConfigureException,
			ServiceEntityInstallationException,
			ServiceEntityConfigureException, NodeNotFoundException {
		List<BSearchNodeComConfigure> searchNodeConfigList = getFinRootSearchComConfigure();
		return bsearchService.doSearch(searchModel, searchNodeConfigList,
				client, true);
	}

	/**
	 * Only search the [Finance Doc ref] node instance by basic search condition
	 * 
	 * @param searchModel
	 * @return
	 * @throws SearchConfigureException
	 * @throws ServiceEntityInstallationException
	 * @throws ServiceEntityConfigureException
	 * @throws NodeNotFoundException
	 */
	public List<ServiceEntityNode> searchFinDocRefCore(
			FinAccDocRefSearchModel searchModel, String client)
			throws SearchConfigureException,
			ServiceEntityInstallationException,
			ServiceEntityConfigureException, NodeNotFoundException {
		List<BSearchNodeComConfigure> searchNodeConfigList = getFinDocRefSearchComConfigure();
		return bsearchService.doSearch(searchModel, searchNodeConfigList,
				client, true);
	}

	protected List<BSearchNodeComConfigure> getFinDocRefSearchComConfigure() {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// search node0 [finAccount root]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(FinAccDocRef.SENAME);
		searchNodeConfig0.setNodeName(FinAccDocRef.NODENAME);
		searchNodeConfig0.setNodeInstID(FinAccDocRef.NODENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		return searchNodeConfigList;
	}

	protected List<BSearchNodeComConfigure> getFinRootSearchComConfigure() {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
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
		return searchNodeConfigList;
	}

	/**
	 * [Internal method] Can only be invoked by [setDocumentSearchConfigure],
	 * convert the search condition from vehicle run order to run order contract
	 * when document type is run order
	 */
	protected void shiftVehicleRunOrderToContractProxy(
			List<BSearchNodeComConfigure> searchConfigureList,
			BSearchNodeComConfigure searchNodeConfig,
			FinAccountSearchModel searchModel) {
		searchNodeConfig
				.setSeName(IServiceModelConstants.VehicleRunOrderContract);
		searchNodeConfig.setNodeName(ServiceEntityNode.NODENAME_ROOT);
		/**
		 * [key point]: reset the node instance id from to
		 * [VehicleRunOrderContract], make this appoint to ACC DOC ref
		 */
		searchNodeConfig
				.setNodeInstID(IServiceModelConstants.VehicleRunOrderContract);
		searchNodeConfig.setBaseNodeInstID(FinAccountSearchModel.ID_ACCDOC_REF);
		BSearchNodeComConfigure searchNodeComConfigure1 = new BSearchNodeComConfigure();
		searchNodeComConfigure1
				.setSeName(IServiceModelConstants.VehicleRunOrderContract);
		searchNodeComConfigure1
				.setNodeName(IServiceModelConstants.VehicleRunOrderVehReference);
		searchNodeComConfigure1
				.setNodeInstID(IServiceModelConstants.VehicleRunOrderVehReference);
		searchNodeComConfigure1
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeComConfigure1
				.setBaseNodeInstID(IServiceModelConstants.VehicleRunOrderContract);
		searchConfigureList.add(searchNodeComConfigure1);
		BSearchNodeComConfigure searchNodeComConfigure2 = new BSearchNodeComConfigure();
		searchNodeComConfigure2
				.setSeName(IServiceModelConstants.VehicleRunOrder);
		searchNodeComConfigure2.setNodeName(ServiceEntityNode.NODENAME_ROOT);
		searchNodeComConfigure2.setNodeInstID(FinAccountSearchModel.ID_ACCDOC);
		searchNodeComConfigure2
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE);
		searchNodeComConfigure2
				.setBaseNodeInstID(IServiceModelConstants.VehicleRunOrderVehReference);
		searchConfigureList.add(searchNodeComConfigure2);
	}

	@Override
	public void updateSEBindList(List<ServiceEntityBindModel> seBindList,
			List<ServiceEntityBindModel> seBindListBack, String logonUserUUID,
			String organizationUUID) throws ServiceEntityConfigureException {
		// set the default accountant and cashier
		for (ServiceEntityBindModel seBindModel : seBindList) {
			if (seBindModel.getSeNode().getServiceEntityName()
					.endsWith(FinAccount.SENAME)
					&& seBindModel.getSeNode().getNodeName()
							.equals(FinAccount.NODENAME)) {
				FinAccount finAccount = (FinAccount) seBindModel.getSeNode();
				if (finAccount.getAccountantUUID() == null
						|| finAccount.getAccountantUUID().equals(
								ServiceEntityStringHelper.EMPTYSTRING)) {
					filterWithAccountant(finAccount, organizationUUID);
				}
				if (finAccount.getCashierUUID() == null
						|| finAccount.getCashierUUID().equals(
								ServiceEntityStringHelper.EMPTYSTRING)) {
					filterWithCashiser(finAccount, organizationUUID);
				}
			}
		}
		super.updateSEBindList(seBindList, seBindListBack, logonUserUUID,
				organizationUUID);
	}

	/**
	 * Logic to delete whole Fin Account object, including the document
	 * reference, object reference and log
	 * 
	 * @param uuid
	 * @throws ServiceEntityConfigureException
	 */
	@Override
	public void admDeleteFinAccountUnion(String uuid)
			throws ServiceEntityConfigureException {
		FinAccount finAccount = (FinAccount) getEntityNodeByUUID(uuid, FinAccount.NODENAME, null);
		if (finAccount == null) {
			return;
		}

		admDeleteEntityByKey(uuid,
				IServiceEntityNodeFieldConstant.UUID, FinAccount.NODENAME);
		admDeleteEntityByKey(uuid,
				IServiceEntityNodeFieldConstant.ROOTNODEUUID,
				FinAccountObjectRef.NODENAME);
		admDeleteEntityByKey(uuid,
				IServiceEntityNodeFieldConstant.ROOTNODEUUID,
				FinAccountLog.NODENAME);
		admDeleteEntityByKey(uuid,
				IServiceEntityNodeFieldConstant.ROOTNODEUUID,
				FinAccDocRef.NODENAME);
	}

	/**
	 * Get relative Finance Account list from Account
	 * 
	 * @param accountUUID
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	@Override
	public List<ServiceEntityNode> getFinAccountListFromAccount(
			String accountUUID, String client)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> finAccObjectRefList = getEntityNodeListByKey(
				accountUUID, IReferenceNodeFieldConstant.REFUUID,
				FinAccount.NODENAME, client, null);
		if (finAccObjectRefList == null || finAccObjectRefList.size() == 0) {
			return null;
		}
		List<ServiceEntityNode> finAccList = new ArrayList<ServiceEntityNode>();
		for (ServiceEntityNode seNode : finAccObjectRefList) {
			FinAccount finAccount = (FinAccount) getEntityNodeByKey(
					seNode.getRootNodeUUID(),
					IServiceEntityNodeFieldConstant.UUID, FinAccount.NODENAME,
					null);
			finAccList.add(finAccount);
		}
		return finAccList;
	}

	public void filterWithDefAccCashier(FinAccount finAccount, String orgUUID)
			throws ServiceEntityConfigureException {
		// filter with default accountant and cashier
		if (finAccount.getAccountantUUID() == null
				|| finAccount.getAccountantUUID().endsWith(
						ServiceEntityStringHelper.EMPTYSTRING)) {
			this.filterWithAccountant(finAccount, orgUUID);
		}
		// filter with default accountant and cashier
		if (finAccount.getCashierUUID() == null
				|| finAccount.getCashierUUID().endsWith(
						ServiceEntityStringHelper.EMPTYSTRING)) {
			this.filterWithCashiser(finAccount, orgUUID);
		}
	}

	/**
	 * Set the FinAccount with Accountant
	 * 
	 * @param finAccount
	 * @throws ServiceEntityConfigureException
	 */
	public void filterWithAccountant(FinAccount finAccount, String orgUUID)
			throws ServiceEntityConfigureException {
		LogonUser accountant = organizationManager.getOrganizationAccountant(
				orgUUID, finAccount.getClient());
		if (accountant != null) {
			finAccount.setAccountantUUID(accountant.getUuid());
		}

	}

	/**
	 * Set the FinAccount with Accountant
	 * 
	 * @param finAccount
	 * @throws ServiceEntityConfigureException
	 */
	public void filterWithCashiser(FinAccount finAccount, String orgUUID)
			throws ServiceEntityConfigureException {
		LogonUser cashier = organizationManager.getOrganizationCashier(orgUUID,
				finAccount.getClient());
		if (cashier != null) {
			finAccount.setCashierUUID(cashier.getUuid());
		}
	}

	/**
	 * Logic to check whether this Account could be verified by current user
	 * 
	 * @param finAccount
	 * @return
	 * @throws LogonInfoException
	 * @throws FinAccountException
	 * @throws ServiceEntityConfigureException
	 * @throws AuthorizationException
	 */
	public boolean getVerifyEnableFlag(FinAccount finAccount,
			LogonUser logonUser) throws LogonInfoException,
			FinAccountException, ServiceEntityConfigureException {
		/**
		 * [Step1]: check whether the logon user is the Accountant for this
		 * FinAccount
		 */
		if (logonUser == null) {
			throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
		}
		// Check whether logon User belongs to same organization
		Organization organization = logonInfoManager
				.getOrganizationByUserBackend(logonUser.getUuid());
		if (organization == null) {
			return false;
		}
		if (!organization.getUuid().equals(finAccount.getResOrgUUID())) {
			return false;
		}
		/**
		 * [Step2]: check whether the account has finished audit
		 */
		if (finAccount.getAuditStatus() != FinAccount.AUDIT_DONE) {
			throw new FinAccountException(
					FinAccountException.TYPE_NO_VERIFY_NOT_AUDIT);
		}
		/**
		 * [Step3]: check whether the account has verify lock
		 */
		if (finAccount.getVerifyLock()) {
			throw new FinAccountException(
					FinAccountException.TYPE_NO_VERIFY_LOCK);
		}
		return true;
	}

	/**
	 * Logic to check whether this Fin settlement could be verified by current
	 * user
	 * 
	 * @param settlement
	 * @return
	 * @throws LogonInfoException
	 * @throws ServiceEntityConfigureException
	 */
	public boolean getVerifyEnableForSettle(FinAccountSettleItem settlement,
			LogonUser logonUser) throws LogonInfoException,
			ServiceEntityConfigureException {
		if (logonUser == null) {
			throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
		}
		if (settlement.getResOrgUUID() == null) {
			return false;
		}
		// Check whether logon User belongs to same organization
		Organization organization = logonInfoManager
				.getOrganizationByUserBackend(logonUser.getUuid());
		if (organization == null) {
			return false;
		}
		if (!organization.getUuid().equals(settlement.getResOrgUUID())) {
			return false;
		}
		return true;
	}

	/**
	 * Logic to check whether this Account could be verified by current user
	 * 
	 * @param finAccount
	 * @return
	 * @throws LogonInfoException
	 * @throws FinAccountException
	 * @throws ServiceEntityConfigureException
	 */
	public boolean getAuditEnableFlag(FinAccount finAccount, LogonUser logonUser)
			throws LogonInfoException, FinAccountException,
			ServiceEntityConfigureException {
		/**
		 * [Step1]: check whether the logon user is the Accountant for this
		 * FinAccount
		 */
		if (logonUser == null) {
			throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
		}
		// Check whether logon User belongs to same organization
		Organization organization = logonInfoManager
				.getOrganizationByUserBackend(logonUser.getUuid());
		if (organization == null) {
			return false;
		}
		if (!organization.getUuid().equals(finAccount.getResOrgUUID())) {
			return false;
		}
		/**
		 * [Step3]: check whether the account has verify lock
		 */
		if (finAccount.getAuditLock()) {
			throw new FinAccountException(
					FinAccountException.TYPE_NO_VERIFY_LOCK);
		}
		return true;
	}

	/**
	 * Logic to check whether this Account could be verified by current user
	 * 
	 * @param finAccount
	 * @return
	 * @throws LogonInfoException
	 * @throws FinAccountException
	 * @throws ServiceEntityConfigureException
	 */
	public boolean getRecordEnableFlag(FinAccount finAccount,
			LogonUser logonUser) throws LogonInfoException,
			FinAccountException, ServiceEntityConfigureException {
		/**
		 * [Step1]: check whether the logon user has the authorization to record
		 * FinAccount
		 */
		if (logonUser == null) {
			throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
		}
		// Check whether the logonUser belongs to this Organization
		Organization organization = logonInfoManager
				.getOrganizationByUserBackend(logonUser.getUuid());
		if (organization == null) {
			throw new LogonInfoException(LogonInfoException.TYPE_NO_ORGNIZATION);
		}
		if (!organization.getUuid().equals(finAccount.getResOrgUUID())) {
			return false;
		}
		/**
		 * [Step2]: check whether the account has finished audit
		 */
		if (finAccount.getVerifyStatus() != FinAccount.VERIFIED_DONE) {
			throw new FinAccountException(
					FinAccountException.TYPE_NO_RECORD_NOT_CASHIER);
		}
		/**
		 * [Step3]: check whether the account has verify lock
		 */
		if (finAccount.getRecordLock()) {
			throw new FinAccountException(
					FinAccountException.TYPE_NO_RECORD_LOCK);
		}
		return true;
	}

	public void auditAccount(FinAccount finAccount, String logonUserUUID,
			String organizationUUID) {
		auditAccountCore(finAccount, logonUserUUID, organizationUUID);
		finAccount.setAuditStatus(FinAccount.AUDIT_DONE);
		this.updateSENode(finAccount, logonUserUUID, organizationUUID);
	}

	public void auditAccountCore(FinAccount finAccount, String logonUserUUID,
			String organizationUUID) {
		finAccount.setAuditLock(false);
		finAccount.setAuditLockMSG(ServiceEntityStringHelper.EMPTYSTRING);
		finAccount.setAuditBy(logonUserUUID);
		finAccount.setAuditTime(java.time.LocalDateTime.now());
	}

	public void verifyAccount(FinAccount finAccount, String logonUserUUID,
			String organizationUUID) {
		verifyAccountCore(finAccount, logonUserUUID, organizationUUID);
		updateSENode(finAccount, logonUserUUID, organizationUUID);
	}

	public void verifyAccountCore(FinAccount finAccount, String logonUserUUID,
			String organizationUUID) {
		finAccount.setVerifyStatus(FinAccount.VERIFIED_DONE);
		finAccount.setVerifyLock(false);
		finAccount.setVerifyLockMSG(ServiceEntityStringHelper.EMPTYSTRING);
		finAccount.setVerifyBy(logonUserUUID);
		finAccount.setVerifyTime(java.time.LocalDateTime.now());
	}

	/**
	 * Logic for verify batch Account by list of account uuid list
	 * 
	 * @param finAccUUIDList
	 * @param logonUserUUID
	 * @param orgUUID
	 * @throws ServiceEntityConfigureException
	 */
	public void verifyAccountBatch(List<String> finAccUUIDList,
			LogonUser logonUser, String orgUUID)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> finAccList = new ArrayList<ServiceEntityNode>();
		for (String finAccUUID : finAccUUIDList) {
			FinAccount finAccount = (FinAccount) this.getEntityNodeByKey(
					finAccUUID, IServiceEntityNodeFieldConstant.UUID,
					FinAccount.NODENAME, logonUser.getClient(), null);
			if (finAccount == null) {
				continue;
			}
			finAccount.setVerifyStatus(FinAccount.VERIFIED_DONE);
			finAccount.setVerifyLock(false);
			finAccount.setVerifyLockMSG(ServiceEntityStringHelper.EMPTYSTRING);
			finAccount.setVerifyTime(java.time.LocalDateTime.now());
			finAccount.setVerifyBy(logonUser.getUuid());
			finAccList.add(finAccount);
		}
		this.updateSENodeList(finAccList, logonUser.getUuid(), orgUUID);
	}

	/**
	 * Logic for clear all the record amount ( make recorded amount is fully
	 * match the required amount )
	 * 
	 * @param finAccount
	 * @param logonUserUUID
	 * @param orgUUID
	 * @throws ServiceEntityConfigureException
	 */
	public void clearRecordAccount(FinAccount finAccount, LogonUser logonUser,
			String orgUUID) throws ServiceEntityConfigureException {
		double recordedAmount = finAccount.getRecordedAmount();
		if (recordedAmount < finAccount.getAmount()) {
			finAccount.setRecordedAmount(finAccount.getAmount());
		}
		finAccount.setToRecordAmount(0);
		// Clear audit status
		if (finAccount.getAuditStatus() != FinAccount.AUDIT_DONE) {
			auditAccountCore(finAccount, logonUser.getUuid(), orgUUID);
		}
		if (finAccount.getVerifyStatus() != FinAccount.VERIFIED_DONE) {
			verifyAccountCore(finAccount, logonUser.getUuid(), orgUUID);
		}
		// Action of recorded this amount
		recordAccount(finAccount, logonUser, orgUUID);
	}

	/**
	 * Logic for record the current account, save into persistence and other
	 * post-process
	 * 
	 * @param finAccount
	 * @throws ServiceEntityConfigureException
	 */
	public void recordAccount(FinAccount finAccount, LogonUser logonUser,
			String orgUUID) throws ServiceEntityConfigureException {
		// Set the normal status and update into persistence
		setRecordStatusByCheckAmount(finAccount);
		finAccount.setRecordTime(java.time.LocalDateTime.now());
		double toRecordAmount = FinAccountManager
				.caculateToRecordAmount(finAccount);
		finAccount.setToRecordAmount(toRecordAmount);
		finAccount.setRecordBy(logonUser.getUuid());
		finAccount.setRecordLockMSG(ServiceEntityStringHelper.EMPTYSTRING);
		finAccount.setRecordLock(false);
		updateSENode(finAccount, logonUser.getUuid(), orgUUID);
		// Update the pre-requirement if necessary
		List<ServiceEntityNode> preReqList = this.getEntityNodeListByKey(
				finAccount.getUuid(), IReferenceNodeFieldConstant.REFUUID,
				FinAccountPrerequirement.NODENAME, logonUser.getClient(), null);
		for (ServiceEntityNode seNode : preReqList) {
			FinAccountPrerequirement finAccountPrerequirement = (FinAccountPrerequirement) seNode;
			if (finAccountPrerequirement.getRequireType() == FinAccountPrerequirement.RQTYPE_UNLOCK_RECORD) {
				// In case have to unlock the relied Fin account
				FinAccount reliedFinAccount = (FinAccount) this
						.getEntityNodeByKey(finAccountPrerequirement.getUuid(),
								IServiceEntityNodeFieldConstant.UUID,
								FinAccount.NODENAME, logonUser.getClient(),
								null);
				if (reliedFinAccount != null) {
					reliedFinAccount.setVerifyLock(false);
					reliedFinAccount
							.setVerifyLockMSG(ServiceEntityStringHelper.EMPTYSTRING);
					reliedFinAccount.setRecordLock(false);
					reliedFinAccount
							.setRecordLockMSG(ServiceEntityStringHelper.EMPTYSTRING);
					this.updateSENode(reliedFinAccount, logonUser.getUuid(),
							orgUUID);
				}
			}
		}
	}

	/**
	 * Compound method to conv Finaccount to UI model
	 * 
	 * @param rawNode
	 * @param logonUser
	 * @param allFinTitleList
	 * @param mergeFlag
	 *            : wethear need to
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws LogonInfoException
	 * @throws ServiceEntityInstallationException
	 * @throws FinAccountException
	 */
	public FinAccountUIModel convToUIModelUnion(ServiceEntityNode rawNode,
			LogonUser logonUser, List<ServiceEntityNode> allFinTitleList,
			boolean mergeFlag) throws ServiceEntityConfigureException,
			LogonInfoException, ServiceEntityInstallationException,
			FinAccountException {
		FinAccount accountRaw = (FinAccount) rawNode;
		FinAccountUIModel finAccountUIModel = new FinAccountUIModel();
		convFinAccountToUIAndUser(accountRaw, finAccountUIModel, logonUser);
		FinAccountObjectRef finAccountObjectRef = (FinAccountObjectRef) getEntityNodeByKey(
				accountRaw.getUuid(),
				IServiceEntityNodeFieldConstant.ROOTNODEUUID,
				FinAccountObjectRef.NODENAME, logonUser.getClient(), null);
		Account accountObject = getAllPossibleAccountObject(finAccountObjectRef);
		FinAccountTitle finAccountTitle = null;
		if (ServiceCollectionsHelper.checkNullList(allFinTitleList)) {
			finAccountTitle = finAccountTitleManager.getFinAccountTitleOnline(
					allFinTitleList, accountRaw.getAccountTitleUUID());
		} else {
			// In case need to retrieve fin account title from persistence
			finAccountTitle = (FinAccountTitle) finAccountTitleManager
					.getEntityNodeByKey(accountRaw.getAccountTitleUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							FinAccountTitle.NODENAME, logonUser.getClient(),
							null);
		}
		if (finAccountTitle == null) {
			throw new FinAccountException(
					FinAccountException.TYPE_NO_ACCOUNT_TITLE);
		} else {
			if (mergeFlag) {
				if (finAccountTitle != null
						&& finAccountTitle.getFinAccountType() == FinAccountTitle.FIN_ACCOUNTTYPE_CREDIT) {
					// Set negative value shown for credit type fin account
					finAccountUIModel.setAmount(0 - finAccountUIModel
							.getAmount());
					finAccountUIModel.setToRecordAmount(0 - finAccountUIModel
							.getToRecordAmount());
					finAccountUIModel.setRecordedAmount(0 - finAccountUIModel
							.getRecordedAmount());
				}
			}
		}
		FinAccDocRef finAccDocRef = (FinAccDocRef) getEntityNodeByKey(
				accountRaw.getUuid(),
				IServiceEntityNodeFieldConstant.ROOTNODEUUID,
				FinAccDocRef.NODENAME, logonUser.getClient(), null);
		if (accountRaw.getAccountantUUID() != null
				&& !accountRaw.getAccountantUUID().equals(
						ServiceEntityStringHelper.EMPTYSTRING)) {
			LogonUser accountant = (LogonUser) logonUserManager
					.getEntityNodeByKey(accountRaw.getAccountantUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							LogonUser.NODENAME, logonUser.getClient(), null);
			convAccountantToUI(accountant, finAccountUIModel);
		}
		if (accountRaw.getCashierUUID() != null
				&& !accountRaw.getCashierUUID().equals(
						ServiceEntityStringHelper.EMPTYSTRING)) {
			LogonUser cashier = (LogonUser) logonUserManager
					.getEntityNodeByKey(accountRaw.getCashierUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							LogonUser.NODENAME, logonUser.getClient(), null);
			convCashierToUI(cashier, finAccountUIModel);
		}

		convAccountTitleToUI(finAccountTitle, finAccountUIModel);
		// This is how to handle the temporary account object
		if (accountObject != null) {
			convAccountObjectToUI(accountObject, finAccountUIModel);
		} else {
			finAccountUIModel.setAccountObjectName(accountRaw
					.getAccountObject());
		}
		if (finAccDocRef != null) {
			ServiceEntityNode documentContent = serviceDocumentComProxy
					.getDocumentByReferenceNode(finAccDocRef);
			convDocumentRefToUI(documentContent, finAccountUIModel);
		}
		return finAccountUIModel;
	}

	/**
	 * Convert Account to AccountUIModel
	 * 
	 * @param account
	 * @param accountUIModel
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceEntityInstallationException
	 * @throws LogonInfoException
	 */
	public void convFinAccountToUIAndUser(FinAccount finAccount,
			FinAccountUIModel finAccountUIModel, LogonUser logonUser)
			throws ServiceEntityConfigureException,
			ServiceEntityInstallationException, LogonInfoException {
		finAccountUIModel.setUuid(finAccount.getUuid());
		finAccountUIModel.setId(finAccount.getId());
		finAccountUIModel.setDocumentType(finAccount.getDocumentType());
		Map<Integer, String> documentTypeMap = serviceDocumentComProxy
				.getDocumentTypeMap(false, null);
		finAccountUIModel.setDocumentTypeValue(documentTypeMap.get(finAccount
				.getDocumentType()));
		finAccountUIModel.setPaymentType(finAccount.getPaymentType());
		Map<Integer, String> paymentTypeMap = serviceDropdownListHelper
				.getUIDropDownMap(FinAccountUIModel.class, "paymentType");
		finAccountUIModel.setPaymentTypeValue(paymentTypeMap.get(finAccount
				.getPaymentType()));
		finAccountUIModel.setAmount(finAccount.getAmount());
		finAccountUIModel.setAccountTitleUUID(finAccount.getAccountTitleUUID());
		finAccountUIModel.setNote(finAccount.getNote());
		LogonUser createdUser = (LogonUser) logonUserManager
				.getEntityNodeByKey(finAccount.getCreatedBy(),
						IServiceEntityNodeFieldConstant.UUID,
						LogonUser.NODENAME, logonUser.getClient(), null);
		if (createdUser != null) {
			finAccountUIModel.setCashierName(createdUser.getName());
		}
		double tmpToRecordedAmount = FinAccountManager
				.caculateToRecordAmount(finAccount);
		finAccountUIModel.setToRecordAmount(tmpToRecordedAmount);
		finAccountUIModel.setRecordedAmount(finAccount.getRecordedAmount());
		Map<Integer, String> verifyStatusMap = serviceDropdownListHelper
				.getUIDropDownMap(FinAccountUIModel.class, "verifyStatus");
		finAccountUIModel.setVerifyStatusValue(verifyStatusMap.get(finAccount
				.getVerifyStatus()));
		Map<Integer, String> auditStatusMap = serviceDropdownListHelper
				.getUIDropDownMap(FinAccountUIModel.class, "auditStatus");
		finAccountUIModel.setAuditStatusValue(auditStatusMap.get(finAccount
				.getAuditStatus()));
		Map<Integer, String> recordStatusMap = serviceDropdownListHelper
				.getUIDropDownMap(FinAccountUIModel.class, "recordStatus");
		finAccountUIModel.setRecordStatusValue(recordStatusMap.get(finAccount
				.getRecordStatus()));
		boolean auditEnableFlag = false;
		try {
			auditEnableFlag = getAuditEnableFlag(finAccount, logonUser);
		} catch (FinAccountException finAccountException) {
			auditEnableFlag = false;
		}
		finAccountUIModel.setAuditEnableFlag(auditEnableFlag);
		boolean verfiyEnableFlag = false;
		try {
			verfiyEnableFlag = getVerifyEnableFlag(finAccount, logonUser);
		} catch (FinAccountException finAccountException) {
			verfiyEnableFlag = false;
		}
		finAccountUIModel.setVerifyEnableFlag(verfiyEnableFlag);
		boolean recordEnableFlag = false;
		try {
			recordEnableFlag = getRecordEnableFlag(finAccount, logonUser);
		} catch (FinAccountException finAccountException) {
			recordEnableFlag = false;
		}
		LogonUser createdBy = (LogonUser) logonUserManager.getEntityNodeByKey(
				finAccount.getCreatedBy(),
				IServiceEntityNodeFieldConstant.UUID, LogonUser.NODENAME,
				logonUser.getClient(), null);
		if (createdBy != null) {
			finAccountUIModel.setCreatedByName(createdBy.getName());
		}
		finAccountUIModel.setRecordEnableFlag(recordEnableFlag);

	}

	/**
	 * Convert Account title to AccountUIModel
	 * 
	 * @param account
	 * @param accountUIModel
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceEntityInstallationException
	 */
	public void convAccountTitleToUI(FinAccountTitle finAccountTitle,
			FinAccountUIModel accountUIModel)
			throws ServiceEntityConfigureException,
			ServiceEntityInstallationException {
		if (finAccountTitle != null) {
			accountUIModel.setFinAccountType(finAccountTitle
					.getFinAccountType());
			Map<Integer, String> finAccountTypeMap = serviceDropdownListHelper
					.getUIDropDownMap(FinAccountUIModel.class, "finAccountType");
			accountUIModel.setFinAccountTypeValue(finAccountTypeMap
					.get(finAccountTitle.getFinAccountType()));
			accountUIModel.setAccountTitleName(finAccountTitle.getName());
		}
	}

	/**
	 * Convert Account Object to AccountUIModel
	 * 
	 * @param account
	 * @param accountUIModel
	 * @throws ServiceEntityConfigureException
	 */
	public void convAccountObjectToUI(Account accountObject,
			FinAccountUIModel accountUIModel)
			throws ServiceEntityConfigureException {
		if (accountObject != null) {
			accountUIModel.setAccountObjectName(accountObject.getName());
		}
	}

	/**
	 * Convert Account Object to AccountUIModel
	 * 
	 * @param docContent
	 * @param accountUIModel
	 * @throws ServiceEntityConfigureException
	 */
	public void convDocumentRefToUI(ServiceEntityNode docContent,
			FinAccountUIModel accountUIModel)
			throws ServiceEntityConfigureException {
		if (docContent != null) {
			accountUIModel.setDocumentId(docContent.getId());
			accountUIModel.setRefDocumentUUID(docContent.getUuid());
		}
	}

	/**
	 * Generate the fin account settle center UI Model by calculating and
	 * analyze raw fin account list
	 * 
	 * @param finAccountList
	 * @param mergeFlag
	 *            : whether need to merge fin account amount by analyzing
	 *            positive and negative affect by different types of finaccount
	 *            title
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public FinAccountSettleCenterUIModel genFinAccountSettleCenterUIModel(
			List<ServiceEntityNode> finAccountList, boolean mergeFlag,
			String client, List<ServiceEntityNode> allFinTitleList)
			throws ServiceEntityConfigureException {
		FinAccountSettleCenterUIModel finAccountSettleCenterUIModel = new FinAccountSettleCenterUIModel();
		if (finAccountList == null || finAccountList.size() == 0) {
			return finAccountSettleCenterUIModel;
		}
		for (ServiceEntityNode seNode : finAccountList) {
			FinAccount finAccount = (FinAccount) seNode;
			/**
			 * Process all amount
			 */
			if (mergeFlag) {
				FinAccountTitle finAccountTitle = finAccountTitleManager
						.getFinAccountTitleOnline(allFinTitleList,
								finAccount.getAccountTitleUUID());
				double allAmount = finAccountSettleCenterUIModel.getAllAmount();
				if (finAccountTitle != null
						&& finAccountTitle.getFinAccountType() == FinAccountTitle.FIN_ACCOUNTTYPE_CREDIT) {
					finAccountSettleCenterUIModel.setAllAmount(allAmount
							- finAccount.getAmount());
				} else {
					finAccountSettleCenterUIModel.setAllAmount(allAmount
							+ finAccount.getAmount());
				}
			} else {
				double allAmount = finAccountSettleCenterUIModel.getAllAmount();
				finAccountSettleCenterUIModel.setAllAmount(allAmount
						+ finAccount.getAmount());
			}
			/**
			 * Process all recorded amount
			 */
			if (mergeFlag) {
				FinAccountTitle finAccountTitle = finAccountTitleManager
						.getFinAccountTitleOnline(allFinTitleList,
								finAccount.getAccountTitleUUID());
				double allRecordedAmount = finAccountSettleCenterUIModel
						.getAllRecordedAmount();
				if (finAccountTitle != null
						&& finAccountTitle.getFinAccountType() == FinAccountTitle.FIN_ACCOUNTTYPE_CREDIT) {
					finAccountSettleCenterUIModel
							.setAllRecordedAmount(allRecordedAmount
									- finAccount.getRecordedAmount());
				} else {
					finAccountSettleCenterUIModel
							.setAllRecordedAmount(allRecordedAmount
									+ finAccount.getRecordedAmount());
				}
			} else {
				double allRecordedAmount = finAccountSettleCenterUIModel
						.getAllRecordedAmount();
				finAccountSettleCenterUIModel
						.setAllRecordedAmount(allRecordedAmount
								+ finAccount.getRecordedAmount());
			}
			/**
			 * Process all to record amount
			 */
			if (mergeFlag) {
				FinAccountTitle finAccountTitle = finAccountTitleManager
						.getFinAccountTitleOnline(allFinTitleList,
								finAccount.getAccountTitleUUID());
				if (finAccountTitle != null
						&& finAccountTitle.getFinAccountType() == FinAccountTitle.FIN_ACCOUNTTYPE_CREDIT) {
					double allToRecordAmount = finAccountSettleCenterUIModel
							.getAllToRecordAmount();
					double tmpToRecordAmount = FinAccountManager
							.caculateToRecordAmount(finAccount);
					finAccountSettleCenterUIModel
							.setAllToRecordAmount(allToRecordAmount
									- tmpToRecordAmount);
				} else {
					double allToRecordAmount = finAccountSettleCenterUIModel
							.getAllToRecordAmount();
					double tmpToRecordAmount = FinAccountManager
							.caculateToRecordAmount(finAccount);
					finAccountSettleCenterUIModel
							.setAllToRecordAmount(allToRecordAmount
									+ tmpToRecordAmount);
				}

			} else {
				double allToRecordAmount = finAccountSettleCenterUIModel
						.getAllToRecordAmount();
				double tmpToRecordAmount = FinAccountManager
						.caculateToRecordAmount(finAccount);
				finAccountSettleCenterUIModel
						.setAllToRecordAmount(allToRecordAmount
								+ tmpToRecordAmount);
			}

		}
		finAccountSettleCenterUIModel.setAllDocumentAmount(finAccountList
				.size());
		return finAccountSettleCenterUIModel;
	}

	public ServiceDocumentExtendUIModel convFinAccountToDocExtUIModel(
			FinAccountUIModel finAccountUIModel)
			throws ServiceEntityInstallationException {
		ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
		serviceDocumentExtendUIModel.setRefUIModel(finAccountUIModel);
		serviceDocumentExtendUIModel.setUuid(finAccountUIModel.getUuid());
		serviceDocumentExtendUIModel.setParentNodeUUID(finAccountUIModel
				.getParentNodeUUID());
		serviceDocumentExtendUIModel.setRootNodeUUID(finAccountUIModel
				.getRootNodeUUID());
		serviceDocumentExtendUIModel.setId(finAccountUIModel.getId());
		serviceDocumentExtendUIModel.setStatus(finAccountUIModel.getStatus());
		serviceDocumentExtendUIModel.setStatusValue(finAccountUIModel
				.getStatusValue());
		serviceDocumentExtendUIModel
				.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_FINANCE);
		serviceDocumentExtendUIModel
				.setDocumentTypeValue(serviceDocumentComProxy
						.getDocumentTypeValue(IDefDocumentResource.DOCUMENT_TYPE_FINANCE, null));
		// Logic of reference Date
		String referenceDate = finAccountUIModel.getRecordTime();
		if (ServiceEntityStringHelper.checkNullString(referenceDate)) {
			referenceDate = finAccountUIModel.getUpdatedDate();
		}
		serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
		return serviceDocumentExtendUIModel;
	}

	public ServiceDocumentExtendUIModel convPurchaseMatItemToDocExtUIModel(
			FinAccountMaterialItemUIModel finAccountMaterialItemUIModel,
			LogonInfo logonInfo) throws ServiceEntityInstallationException {
		ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
		docFlowProxy.convDocMatItemUIToDocExtUIModel(
				finAccountMaterialItemUIModel, serviceDocumentExtendUIModel,
				logonInfo, IDefDocumentResource.DOCUMENT_TYPE_FINANCE);
		serviceDocumentExtendUIModel
				.setRefUIModel(finAccountMaterialItemUIModel);
		serviceDocumentExtendUIModel.setId(finAccountMaterialItemUIModel
				.getFinAccountId());
		serviceDocumentExtendUIModel.setStatus(finAccountMaterialItemUIModel
				.getRecordStatus());
		if (logonInfo != null) {
			Map<Integer, String> recordStatusMap = this
					.initRecordStatusMap(logonInfo.getLanguageCode());
			serviceDocumentExtendUIModel.setStatusValue(recordStatusMap
					.get(finAccountMaterialItemUIModel.getRecordStatus()));
		}

		// Logic of reference Date
		String referenceDate = finAccountMaterialItemUIModel.getRecordTime();
		if (ServiceEntityStringHelper.checkNullString(referenceDate)) {
			referenceDate = finAccountMaterialItemUIModel.getUpdatedDate();
		}
		if (ServiceEntityStringHelper.checkNullString(referenceDate)) {
			referenceDate = finAccountMaterialItemUIModel.getCreatedDate();
		}
		serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
		return serviceDocumentExtendUIModel;
	}

	@Override
	public ServiceDocumentExtendUIModel convToDocumentExtendUIModel(
			ServiceEntityNode seNode, LogonInfo logonInfo) {
		if (seNode == null) {
			return null;
		}
		if (ServiceEntityNode.NODENAME_ROOT.equals(seNode.getNodeName())) {
			FinAccount finAccount = (FinAccount) seNode;
			try {
				FinAccountUIModel finAccountUIModel = (FinAccountUIModel) genUIModelFromUIModelExtension(
						FinAccountUIModel.class,
						finAccountServiceUIModelExtension
								.genUIModelExtensionUnion().get(0), finAccount,
						logonInfo, null);
				ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = convFinAccountToDocExtUIModel(finAccountUIModel);
				return serviceDocumentExtendUIModel;
			} catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, FinAccount.SENAME));
			}
		}
		if (FinAccountMaterialItem.NODENAME.equals(seNode.getNodeName())) {
			FinAccountMaterialItem finAccountMaterialItem = (FinAccountMaterialItem) seNode;
			try {
				FinAccountMaterialItemUIModel finAccountMaterialItemUIModel = (FinAccountMaterialItemUIModel) genUIModelFromUIModelExtension(
						FinAccountMaterialItemUIModel.class,
						finAccountMaterialItemServiceUIModelExtension
								.genUIModelExtensionUnion().get(0),
						finAccountMaterialItem, logonInfo, null);
				ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = convPurchaseMatItemToDocExtUIModel(
						finAccountMaterialItemUIModel, logonInfo);
				return serviceDocumentExtendUIModel;
			} catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, FinAccountMaterialItem.NODENAME));
			}
		}
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return IServiceModelConstants.FinAccount;
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return finAccountSearchProxy;
	}

}
