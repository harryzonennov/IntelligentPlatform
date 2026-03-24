package com.company.IntelligentPlatform.finance.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;


import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.finance.dto.FinAccountAttachmentUIModel;
import com.company.IntelligentPlatform.finance.dto.FinAccountMatItemAttachmentUIModel;
import com.company.IntelligentPlatform.finance.dto.FinAccountMaterialItemServiceUIModel;
import com.company.IntelligentPlatform.finance.dto.FinAccountObjectJSONModel;
import com.company.IntelligentPlatform.finance.dto.FinAccountReportService;
import com.company.IntelligentPlatform.finance.dto.FinAccountServiceUIModel;
import com.company.IntelligentPlatform.finance.dto.FinAccountServiceUIModelExtension;
import com.company.IntelligentPlatform.finance.dto.FinAccountUIModel;
import com.company.IntelligentPlatform.finance.service.FinAccountException;
import com.company.IntelligentPlatform.finance.service.FinAccountManager;
import com.company.IntelligentPlatform.finance.service.FinAccountMessageHelper;
import com.company.IntelligentPlatform.finance.service.FinAccountServiceModel;
import com.company.IntelligentPlatform.finance.service.FinAccountTitleManager;
import com.company.IntelligentPlatform.finance.service.FinanceDocumentManager;
import com.company.IntelligentPlatform.finance.service.FinanceAccountObjectProxyManager;
import com.company.IntelligentPlatform.finance.model.FinAccount;
import com.company.IntelligentPlatform.finance.model.FinAccountAttachment;
import com.company.IntelligentPlatform.finance.model.FinAccountObjectRef;
import com.company.IntelligentPlatform.finance.model.FinAccountTitle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.company.IntelligentPlatform.common.controller.CorporateCustomerListController;
import com.company.IntelligentPlatform.common.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.common.service.EmployeeManager;
import com.company.IntelligentPlatform.common.service.IndividualCustomerManager;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.service.AccountManager;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.AuthorizationManager;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.HostCompanyManager;
import com.company.IntelligentPlatform.common.service.INavigationElementConstants;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceDefaultExceptionRecordHelper;
import com.company.IntelligentPlatform.common.service.ServiceJSONDataConstants;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.StandardPriorityProxy;
import com.company.IntelligentPlatform.common.service.SystemConfigureResourceProxy;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityBindModel;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.Account;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.IFinanceActionCode;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.AttachmentConstantHelper;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.HostCompany;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SystemConfigureElement;

@Scope("session")
@Controller(value = "financeAccountEditorController")
@RequestMapping(value = "/finAccount")
public class FinanceAccountEditorController extends SEEditorController {

	@Autowired
	protected FinAccountTitleManager finAccountTitleManager;

	@Autowired
	protected FinAccountManager finAccountManager;

	@Autowired
	protected AccountManager accountManager;

	@Autowired
	protected FinanceDocumentManager financeDocumentManager;

	@Autowired
	protected IndividualCustomerManager individualCustomerManager;

	@Autowired
	protected FinAccountReportService finAccountReportService;

	@Autowired
	protected HostCompanyManager hostCompanyManager;

	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;

	@Autowired
	protected LogonUserManager logonUserManager;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected EmployeeManager employeeManager;

	// TODO-LEGACY: @Autowired

	// TODO-LEGACY: 	protected FinancePackageProxy financePackageProxy;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	// buffer to store customer information
	protected List<ServiceEntityBindModel> seBindListCustomer = new ArrayList<ServiceEntityBindModel>();

	// buffer to store customer information backup
	protected List<ServiceEntityBindModel> seBindListCustomerBack = new ArrayList<ServiceEntityBindModel>();

	@Autowired
	protected CorporateCustomerListController corporateCustomerListController;

	@Autowired
	protected ServiceDefaultExceptionRecordHelper serviceDefaultExceptionRecordHelper;

	@Autowired
	protected FinAccountMessageHelper finAccountMessageHelper;

	@Autowired
	protected FinanceAccountMessageHelper financeAccountMessageHelper;

	@Autowired
	protected FinAccountTitleTreeController finAccountTitleTreeController;

	@Autowired
	protected AuthorizationManager authorizationManager;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected SystemConfigureResourceProxy systemConfigureResourceProxy;

	@Autowired
	protected FinAccountServiceUIModelExtension finAccountServiceUIModelExtension;

	@Autowired
	protected StandardPriorityProxy standardPriorityProxy;

	protected Date date;

	public static final int ERROR_CODE_FINERROR = 2;

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_FINACCOUNT;

	FinanceAccountEditorController() {
	}

	@RequestMapping(value = "/getAdjustDirectionMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getAdjustDirectionMap() {
		try {
			Map<Integer, String> adjustDirectionMap = finAccountManager
					.initAdjustDirectionMap(logonActionController
							.getLanguageCode());
			return finAccountManager.getDefaultSelectMap(adjustDirectionMap,
					false);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}

	}

	@RequestMapping(value = "/getPriorityCode", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPriorityCode(String languageCode) {
		try {
			Map<Integer, String> priorityCodeMap = finAccountManager
					.initPriorityCodeMap(logonActionController.getLanguageCode());
			return finAccountManager
					.getDefaultSelectMap(priorityCodeMap, false);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}
	}

	@RequestMapping(value = "/getDocumentType", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocumentType() {
		try {
			Map<Integer, String> documentTypeMap = finAccountManager
					.initDocumentTypeMap(logonActionController.getLanguageCode());
			return finAccountManager.getDefaultSelectMap(documentTypeMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}
	}

	@RequestMapping(value = "/getAuditStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getAuditStatus() {
		try {
			Map<Integer, String> auditStatusMap = finAccountManager
					.initAuditStatusMap(logonActionController.getLanguageCode());
			return finAccountManager.getDefaultSelectMap(auditStatusMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}
	}

	@RequestMapping(value = "/getStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getStatus() {
		try {
			Map<Integer, String> statusMap = finAccountManager
					.initStatusMap(logonActionController.getLanguageCode());
			return finAccountManager.getDefaultSelectMap(statusMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}

	}

	@RequestMapping(value = "/getVerifyStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getVerifyStatus() {
		try {
			Map<Integer, String> verifyStatusMap = finAccountManager
					.initVerifyStatusMap(logonActionController.getLanguageCode());
			return finAccountManager.getDefaultSelectMap(verifyStatusMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}

	}

	@RequestMapping(value = "/getPaymentType", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPaymentType() {
		try {
			Map<Integer, String> paymentTypeMap = finAccountManager
					.initPaymentTypeMap(logonActionController.getLanguageCode());
			return finAccountManager.getDefaultSelectMap(paymentTypeMap, false);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}

	}

	@RequestMapping(value = "/getRecordStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getRecordStatus() {
		try {
			Map<Integer, String> recordStatusMap = finAccountManager
					.initRecordStatusMap(logonActionController.getLanguageCode());
			return finAccountManager.getDefaultSelectMap(recordStatusMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}

	}

	@RequestMapping(value = "/getFinAccountType", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getFinAccountType() {
		try {
			Map<Integer, String> finAccountTypeMap = finAccountManager
					.initFinAccountTypeMap(logonActionController
							.getLanguageCode());
			return finAccountManager.getDefaultSelectMap(finAccountTypeMap,
					false);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}

	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		FinAccountServiceUIModel finAccountServiceUIModel = parseToServiceUIModel(request);
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
			Organization organization = logonActionController
					.getOrganizationByUser(logonUser.getUuid());
			if (organization != null) {
				organizationUUID = organization.getUuid();
			}
			FinAccountServiceModel finAccountServiceModel = (FinAccountServiceModel) finAccountManager
					.genServiceModuleFromServiceUIModel(
							FinAccountServiceModel.class,
							FinAccountServiceUIModel.class,
							finAccountServiceUIModel,
							finAccountServiceUIModelExtension);
			finAccountManager.updateServiceModuleWithDelete(
					FinAccountServiceModel.class, finAccountServiceModel,
					logonUser.getUuid(), organizationUUID, FinAccount.SENAME,
					finAccountServiceUIModelExtension);
			// Refresh service UI model
			finAccountServiceUIModel = this.refreshLoadServiceUIModel(
					finAccountServiceModel.getFinAccount().getUuid(),
					ISystemActionCode.ACID_EDIT,
					logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(finAccountServiceUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(@RequestBody String response) {
		JSONObject jsonObject = JSONObject.fromObject(response);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<String, Class>();
		FinAccount initModel = (FinAccount) JSONObject.toBean(jsonObject,
				FinAccount.class, classMap);
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			FinAccount finAccount = (FinAccount) finAccountManager
					.newRootEntityNode(logonUser.getClient());
			if (initModel.getDocumentType() != 0) {
				finAccount.setDocumentType(initModel.getDocumentType());
			}
			FinAccountServiceModel finAccountServiceModel = new FinAccountServiceModel();
			finAccountServiceModel.setFinAccount(finAccount);
			FinAccountServiceUIModel finAccountServiceUIModel = (FinAccountServiceUIModel) finAccountManager
					.genServiceUIModuleFromServiceModel(
							FinAccountServiceUIModel.class,
							FinAccountServiceModel.class,
							finAccountServiceModel, finAccountServiceUIModelExtension,
							logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(finAccountServiceUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleViewService(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			FinAccountServiceUIModel finAccountServiceUIModel = this
					.refreshLoadServiceUIModel(uuid,
							ISystemActionCode.ACID_VIEW,
							logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(finAccountServiceUIModel);
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	/**
	 * Refresh and load service UI model from back-end
	 * 
	 * @param uuid
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceModuleProxyException
	 * @throws ServiceUIModuleProxyException
	 * @throws LogonInfoException
	 */
	private FinAccountServiceUIModel refreshLoadServiceUIModel(String uuid,
			String acId, LogonInfo logonInfo)
			throws ServiceEntityConfigureException,
			ServiceModuleProxyException, ServiceUIModuleProxyException,
			LogonInfoException, AuthorizationException {
		FinAccount finAccount = (FinAccount) finAccountManager
				.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID,
						FinAccount.NODENAME, logonInfo.getClient(), null);
		if (!ServiceEntityStringHelper.checkNullString(acId)) {
			boolean checkAuthor = serviceBasicUtilityController
					.checkTargetDataAccess(logonInfo.getLogonUser(),
							finAccount, acId,
							logonInfo.getAuthorizationActionCodeMap());
			if (!checkAuthor) {
				throw new AuthorizationException(
						AuthorizationException.TYPE_NO_AUTHORIZATION);
			}
		}
		FinAccountServiceModel finAccountServiceModel = (FinAccountServiceModel) finAccountManager
				.loadServiceModule(FinAccountServiceModel.class, finAccount);
		FinAccountServiceUIModel finAccountServiceUIModel = (FinAccountServiceUIModel) finAccountManager
				.genServiceUIModuleFromServiceModel(
						FinAccountServiceUIModel.class,
						FinAccountServiceModel.class, finAccountServiceModel,
						finAccountServiceUIModelExtension, logonInfo);
		return finAccountServiceUIModel;
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleEditService(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> lockSEList = new ArrayList<ServiceEntityNode>();
			FinAccount finAccount = (FinAccount) finAccountManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							FinAccount.NODENAME, logonUser.getClient(), null);
			lockSEList.add(finAccount);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));
			FinAccountServiceUIModel finAccountServiceUIModel = refreshLoadServiceUIModel(
					uuid, ISystemActionCode.ACID_EDIT,
					logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(finAccountServiceUIModel);
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LockObjectFailureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModule(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			FinAccount finAccount = (FinAccount) finAccountManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							FinAccount.NODENAME, logonUser.getClient(), null);
			LogonInfo logonInfo = logonActionController.getLogonInfo();
			boolean checkAuthor = serviceBasicUtilityController
					.checkTargetDataAccess(logonInfo.getLogonUser(),
							finAccount, ISystemActionCode.ACID_VIEW,
							logonInfo.getAuthorizationActionCodeMap());
			if (checkAuthor == false) {
				throw new AuthorizationException(
						AuthorizationException.TYPE_NO_AUTHORIZATION);
			}
			return ServiceJSONParser.genDefOKJSONObject(finAccount);
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());

		}
	}

	@RequestMapping(value = "/deleteAttachment", produces = "text/html;charset=UTF-8")
	public @ResponseBody String deleteAttachment(
			@RequestBody SimpleSEJSONRequest request) {
		return deleteAttachmentCore(request.getUuid());
	}

	@RequestMapping(value = "/uploadAttachmentText", produces = "text/html;charset=UTF-8")
	public @ResponseBody String uploadAttachmentText(
			@RequestBody FileAttachmentTextRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			FinAccount finAccount = (FinAccount) finAccountManager
					.getEntityNodeByKey(request.getBaseUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							FinAccount.NODENAME, null);
			FinAccountAttachment finAccountAttachment = (FinAccountAttachment) finAccountManager
					.getEntityNodeByKey(request.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							FinAccountAttachment.NODENAME, null);
			if (finAccountAttachment == null
					|| ServiceEntityStringHelper.checkNullString(request
							.getUuid())) {
				finAccountAttachment = (FinAccountAttachment) finAccountManager
						.newEntityNode(finAccount,
								FinAccountAttachment.NODENAME);
			}
			FinAccountAttachment finAccountAttachmentBack = (FinAccountAttachment) finAccountAttachment
					.clone();
			finAccountAttachment.setName(request.getAttachmentTitle());
			finAccountAttachment.setNote(request.getAttachmentDescription());
			String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
			Organization organization = logonActionController
					.getOrganizationByUser(logonUser.getUuid());
			if (organization != null) {
				organizationUUID = organization.getUuid();
			}
			finAccountManager.updateSENode(finAccountAttachment,
					finAccountAttachmentBack, logonUser.getUuid(),
					organizationUUID);
			return ServiceJSONParser.genDefOKJSONObject(finAccountAttachment);
		} catch (ServiceEntityConfigureException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		} catch (LogonInfoException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex
					.getErrorMessage());
		} catch (AuthorizationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/deleteAttachmentCore", produces = "text/html;charset=UTF-8")
	public @ResponseBody String deleteAttachmentCore(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			FinAccountAttachment finAccountAttachment = (FinAccountAttachment) finAccountManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							FinAccountAttachment.NODENAME, null);
			String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
			Organization organization = logonActionController
					.getOrganizationByUser(logonUser.getUuid());
			if (organization != null) {
				organizationUUID = organization.getUuid();
			}
			if (finAccountAttachment != null) {
				finAccountManager.deleteSENode(finAccountAttachment,
						logonUser.getUuid(), organizationUUID);
			}
			return ServiceJSONParser.genSimpleOKResponse();
		} catch (ServiceEntityConfigureException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		} catch (LogonInfoException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex
					.getErrorMessage());
		} catch (AuthorizationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/uploadAttachment", consumes = "multipart/form-data", method = RequestMethod.POST)
	public @ResponseBody String uploadAttachment(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
			MultipartHttpServletRequest muti = (MultipartHttpServletRequest) request;
			// System.out.println(muti.getMultiFileMap().size());
			MultiValueMap<String, MultipartFile> map = muti.getMultiFileMap();
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			for (Map.Entry<String, List<MultipartFile>> entry : map.entrySet()) {
				List<MultipartFile> list = entry.getValue();
				for (MultipartFile multipartFile : list) {
					try {
						byte[] bytes = multipartFile.getBytes();
						String fileType = AttachmentConstantHelper
								.getFileTypeFromPostFix(multipartFile);
						String uuid = request
								.getParameter(SimpleSEJSONRequest.UUID);
						String baseUUID = request
								.getParameter(SimpleSEJSONRequest.BASEUUID);
						FinAccountAttachment finAccountAttachment = (FinAccountAttachment) finAccountManager
								.getEntityNodeByKey(uuid,
										IServiceEntityNodeFieldConstant.UUID,
										FinAccountAttachment.NODENAME, null);
						if (finAccountAttachment == null) {
							FinAccount finAccount = (FinAccount) finAccountManager
									.getEntityNodeByKey(
											baseUUID,
											IServiceEntityNodeFieldConstant.UUID,
											FinAccount.NODENAME, null);
							finAccountAttachment = (FinAccountAttachment) finAccountManager
									.newEntityNode(finAccount,
											FinAccountAttachment.NODENAME);
						}
						FinAccountAttachment finAccountAttachmentBack = (FinAccountAttachment) finAccountAttachment
								.clone();
						if (ServiceEntityStringHelper
								.checkNullString(finAccountAttachment.getName())) {
							finAccountAttachment.setName(multipartFile
									.getOriginalFilename());
						}
						finAccountAttachment.setContent(bytes);
						finAccountAttachment.setFileType(fileType);
						finAccountAttachment.setId(multipartFile
								.getOriginalFilename());
						String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
						Organization organization = logonActionController
								.getOrganizationByUser(logonUser.getUuid());
						if (organization != null) {
							organizationUUID = organization.getUuid();
						}
						finAccountManager.updateSENode(finAccountAttachment,
								finAccountAttachmentBack, logonUser.getUuid(),
								organizationUUID);
					} catch (IllegalStateException | IOException e) {
						e.printStackTrace();
					}
				}
			}
			return ServiceJSONParser.genSimpleOKResponse();
		} catch (ServiceEntityConfigureException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (UnsupportedEncodingException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		}
	}

	@RequestMapping(value = "/loadAttachment")
	public ResponseEntity<byte[]> loadAttachment(String uuid)
			throws IOException {
		final HttpHeaders headers = new HttpHeaders();
		try {
			FinAccountAttachment finAccountAttachment = (FinAccountAttachment) finAccountManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							FinAccountAttachment.NODENAME, null);
			if (finAccountAttachment != null) {
				finAccountManager.setAttachmentHttpHeaders(headers,
						finAccountAttachment.getFileType(),
						finAccountAttachment.getName());
				return new ResponseEntity<byte[]>(
						finAccountAttachment.getContent(), headers,
						HttpStatus.CREATED);
			}
		} catch (ServiceEntityConfigureException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String genFINErrorResponse(String msg) {
		String content = "{\"" + ServiceJSONDataConstants.ELE_ERROR_CODE
				+ "\":\"" + ERROR_CODE_FINERROR + "\"," + "\""
				+ ServiceJSONDataConstants.ELE_ERROR_MSG + "\":\"" + msg
				+ "\"}";
		return content;
	}

	public String genFINDONERespone(String msg) {
		String content = "{\"" + ServiceJSONDataConstants.ELE_ERROR_CODE
				+ "\":\"" + ServiceJSONDataConstants.ERROR_CODE_OK + "\","
				+ "\"" + ServiceJSONDataConstants.ELE_ERROR_MSG + "\":\"" + msg
				+ "\"}";
		return content;
	}

	/**
	 * Get all the possible finAccount object instance
	 * 
	 * @param request
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	@RequestMapping(value = "/getAccountObject", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getAccountObject(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String accountObjectUUID = request.getUuid();
			ServiceEntityNode accountObject;
			accountObject = finAccountManager.getAllPossibleAccountObject(
					accountObjectUUID, logonUser.getClient());
			if (accountObject == null) {
				return ServiceJSONDataConstants.DEF_UNKONWON_ERROR_JSON;
			} else {
				FinAccountObjectJSONModel finAccountObjectJSONModel = new FinAccountObjectJSONModel();
				convAccountObjectJSONModle(finAccountObjectJSONModel,
						accountObject);
				return ServiceJSONParser
						.genDefOKJSONObject(finAccountObjectJSONModel);
			}
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONDataConstants.DEF_UNKONWON_ERROR_JSON;
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	/**
	 * Get all the possible finAccount object instance
	 * 
	 * @param request
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	@RequestMapping(value = "/getAccountObjectByType", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getAccountObjectByType(
			@RequestBody FinAccountObjectJSONModel request) {
		try {
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String accountObjectUUID = request.getUuid();
			int accountType = request.getAccountType();
			ServiceEntityNode accountObject;
			accountObject = finAccountManager.getAllPossibleAccountObject(
					accountObjectUUID, accountType, logonUser.getClient());
			if (accountObject == null) {
				return ServiceJSONDataConstants.DEF_UNKONWON_ERROR_JSON;
			} else {
				FinAccountObjectJSONModel finAccountObjectJSONModel = new FinAccountObjectJSONModel();
				convAccountObjectJSONModle(finAccountObjectJSONModel,
						accountObject);
				return ServiceJSONParser
						.genDefOKJSONObject(finAccountObjectJSONModel);
			}
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	public void convAccountObjectJSONModle(
			FinAccountObjectJSONModel finAccountObjectJSONModel,
			ServiceEntityNode accountObject) {
		if (accountObject != null) {
			finAccountObjectJSONModel.setUuid(accountObject.getUuid());
			finAccountObjectJSONModel.setName(accountObject.getName());
			finAccountObjectJSONModel.setId(accountObject.getId());
			finAccountObjectJSONModel.setAccountType(accountManager
					.getAccountType(accountObject));
		}
	}

	@RequestMapping(value = "/verifyFrame", produces = "text/html;charset=UTF-8")
	public @ResponseBody String verifyAccountFrame(
			@RequestBody SimpleSEJSONRequest request) {
		String baseUUID = request.getUuid();
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, IFinanceActionCode.ACID_VERIFY);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			FinAccount finAccount = (FinAccount) finAccountManager
					.getEntityNodeByKey(baseUUID,
							IServiceEntityNodeFieldConstant.UUID,
							FinAccount.NODENAME, logonUser.getClient(), null);
			boolean verifyEnableFlag = finAccountManager.getVerifyEnableFlag(
					finAccount, logonUser);
			if (verifyEnableFlag) {
				Organization organization = logonActionController
						.getOrganizationByUser(logonUser.getUuid());
				String orgUUID = ServiceEntityStringHelper.EMPTYSTRING;
				if (organization != null) {
					orgUUID = organization.getUuid();
				}
				finAccountManager.verifyAccount(finAccount,
						logonUser.getUuid(), orgUUID);
				String msg = finAccountMessageHelper
						.getMessage(FinAccountMessageHelper.MSG_VERIFY_DONE);
				return genFINDONERespone(msg);
			}
			String errorMessage = ServiceExceptionHelper.getErrorMessage(
					FinAccountException.class,
					FinAccountException.TYPE_CANNOT_VERIFY);
			return genFINDONERespone(errorMessage);
		} catch (FinAccountException e) {
			return genFINDONERespone(e.getErrorMessage());
		} catch (LogonInfoException e) {
			return genFINErrorResponse(e.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return genFINErrorResponse(e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			return genFINErrorResponse(e.getMessage());
		} catch (AuthorizationException e) {
			return genFINErrorResponse(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/preVerify", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preVerify(
			@RequestBody FinAccountUIModel finAccountUIModel) {
		String baseUUID = finAccountUIModel.getUuid();
		try {
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, IFinanceActionCode.ACID_VERIFY);
			FinAccount finAccount = (FinAccount) getServiceEntityNodeFromBuffer(
					FinAccount.NODENAME, baseUUID);
			boolean verfiyEnableFlag = finAccountManager.getVerifyEnableFlag(
					finAccount, logonUser);
			if (verfiyEnableFlag) {
				return ServiceJSONDataConstants.DEF_SIMPLE_SUCCESS_JSON;
			} else {
				return ServiceJSONDataConstants.DEF_UNKONWON_ERROR_JSON;
			}
		} catch (FinAccountException e) {
			return genFINErrorResponse(e.getErrorMessage());
		} catch (LogonInfoException e) {
			return genFINErrorResponse(e.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return genFINErrorResponse(e.getMessage());
		} catch (AuthorizationException e) {
			return genFINErrorResponse(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/verifyContentService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String verifyContentService(@RequestBody String request) {
		FinAccountServiceUIModel finAccountServiceUIModel = parseToServiceUIModel(request);
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, IFinanceActionCode.ACID_RECORD);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
			Organization organization = logonActionController
					.getOrganizationByUser(logonUser.getUuid());
			if (organization != null) {
				organizationUUID = organization.getUuid();
			}
			FinAccountServiceModel finAccountServiceModel = (FinAccountServiceModel) finAccountManager
					.genServiceModuleFromServiceUIModel(
							FinAccountServiceModel.class,
							FinAccountServiceUIModel.class,
							finAccountServiceUIModel,
							finAccountServiceUIModelExtension);
			// Refresh service model
			finAccountManager.updateServiceModuleWithDelete(
					FinAccountServiceModel.class, finAccountServiceModel,
					logonUser.getUuid(), organizationUUID);
			FinAccount finAccount = (FinAccount) finAccountManager
					.getEntityNodeByKey(finAccountServiceModel.getFinAccount()
							.getUuid(), IServiceEntityNodeFieldConstant.UUID,
							FinAccount.NODENAME, logonUser.getClient(), null);
			finAccountManager.verifyAccount(finAccount, logonUser.getUuid(),
					organizationUUID);
			finAccountServiceUIModel = refreshLoadServiceUIModel(
					finAccount.getUuid(), IFinanceActionCode.ACID_RECORD,
					logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(finAccountServiceUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/verifyService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String verifyService(
			@RequestBody SimpleSEJSONRequest simpleSEJSONRequest) {
		String baseUUID = simpleSEJSONRequest.getUuid();
		try {
			// Check Authorization for [FinAccount] Chart
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, IFinanceActionCode.ACID_VERIFY);
			FinAccount finAccount = (FinAccount) getServiceEntityNodeFromBuffer(
					FinAccount.NODENAME, baseUUID);
			boolean verfiyEnableFlag = finAccountManager.getVerifyEnableFlag(
					finAccount, logonUser);
			if (verfiyEnableFlag) {
				// Update the content in UI model and save to persistence soon
				String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
				Organization organization = logonActionController
						.getOrganizationByUser(logonUser.getUuid());
				if (organization != null) {
					organizationUUID = organization.getUuid();
				}
				// Also update the content in buffer, to make sure the
				// Consistency to UI
				finAccountManager.verifyAccount(finAccount,
						logonUser.getUuid(), organizationUUID);
			}
			return ServiceJSONParser.genSimpleOKResponse();
		} catch (FinAccountException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		}
	}

	public FinAccountServiceUIModel parseToServiceUIModel(String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("finAccountLogUIModelList", FinAccountLogUIModel.class);
		classMap.put("finAccountAttachmentUIModelList",
				FinAccountAttachmentUIModel.class);
		classMap.put("finAccountMaterialItemUIModelList",
				FinAccountMaterialItemServiceUIModel.class);
		classMap.put("finAccountMatItemAttachmentUIModelList",
				FinAccountMatItemAttachmentUIModel.class);
		FinAccountServiceUIModel finAccountServiceUIModel = (FinAccountServiceUIModel) JSONObject
				.toBean(jsonObject, FinAccountServiceUIModel.class, classMap);
		return finAccountServiceUIModel;
	}

	@RequestMapping(value = "/auditContentService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String auditContentService(@RequestBody String request) {
		FinAccountServiceUIModel finAccountServiceUIModel = parseToServiceUIModel(request);
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, IFinanceActionCode.ACID_RECORD);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
			Organization organization = logonActionController
					.getOrganizationByUser(logonUser.getUuid());
			if (organization != null) {
				organizationUUID = organization.getUuid();
			}
			FinAccountServiceModel finAccountServiceModel = (FinAccountServiceModel) finAccountManager
					.genServiceModuleFromServiceUIModel(
							FinAccountServiceModel.class,
							FinAccountServiceUIModel.class,
							finAccountServiceUIModel,
							finAccountServiceUIModelExtension);
			FinAccount finAccount = finAccountServiceModel.getFinAccount();
			finAccountManager.auditAccountCore(finAccount, logonUser.getUuid(),
					organizationUUID);
			finAccountManager.updateServiceModuleWithDelete(
					FinAccountServiceModel.class, finAccountServiceModel,
					logonUser.getUuid(), organizationUUID);
			finAccountServiceUIModel = refreshLoadServiceUIModel(
					finAccount.getUuid(), IFinanceActionCode.ACID_RECORD,
					logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(finAccountServiceUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/auditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String auditService(
			@RequestBody SimpleSEJSONRequest simpleSEJSONRequest) {
		String baseUUID = simpleSEJSONRequest.getUuid();
		try {
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, IFinanceActionCode.ACID_AUDIT);
			FinAccount finAccount = (FinAccount) finAccountManager
					.getEntityNodeByKey(baseUUID,
							IServiceEntityNodeFieldConstant.UUID,
							FinAccount.NODENAME, null);

			String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
			Organization organization = logonActionController
					.getOrganizationByUser(logonUser.getUuid());
			if (organization != null) {
				organizationUUID = organization.getUuid();
			}
			finAccountManager.auditAccount(finAccount, logonUser.getUuid(),
					organizationUUID);
			return ServiceJSONParser.genSimpleOKResponse();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		}
	}

	@RequestMapping(value = "/recordContentService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String recordContentService(@RequestBody String request) {
		FinAccountServiceUIModel finAccountServiceUIModel = parseToServiceUIModel(request);
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, IFinanceActionCode.ACID_RECORD);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
			Organization organization = logonActionController
					.getOrganizationByUser(logonUser.getUuid());
			if (organization != null) {
				organizationUUID = organization.getUuid();
			}
			FinAccountServiceModel finAccountServiceModel = (FinAccountServiceModel) finAccountManager
					.genServiceModuleFromServiceUIModel(
							FinAccountServiceModel.class,
							FinAccountServiceUIModel.class,
							finAccountServiceUIModel,
							finAccountServiceUIModelExtension);
			finAccountManager.updateServiceModuleWithDelete(
					FinAccountServiceModel.class, finAccountServiceModel,
					logonUser.getUuid(), organizationUUID);
			// Refresh service model
			FinAccount finAccount = (FinAccount) finAccountManager
					.getEntityNodeByKey(finAccountServiceModel.getFinAccount()
							.getUuid(), IServiceEntityNodeFieldConstant.UUID,
							FinAccount.NODENAME, logonUser.getClient(), null);
			boolean recordEnableFlag = finAccountManager.getRecordEnableFlag(
					finAccount, logonUser);
			if (recordEnableFlag) {
				finAccount.setRecordTime(java.time.LocalDateTime.now());
				finAccount.setRecordBy(logonUser.getUuid());
			}
			finAccountManager.recordAccount(finAccount, logonUser,
					organizationUUID);
			finAccountServiceUIModel = refreshLoadServiceUIModel(
					finAccount.getUuid(), IFinanceActionCode.ACID_RECORD,
					logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(finAccountServiceUIModel);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (FinAccountException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/recordService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String recordService(
			@RequestBody SimpleSEJSONRequest simpleSEJSONRequest) {
		String uuid = simpleSEJSONRequest.getUuid();
		try {
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, IFinanceActionCode.ACID_RECORD);
			// Update the content in UI model and save to persistence soon
			FinAccount finAccount = (FinAccount) finAccountManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							FinAccount.NODENAME, logonUser.getClient(), null);
			boolean recordEnableFlag = finAccountManager.getRecordEnableFlag(
					finAccount, logonUser);
			if (recordEnableFlag) {
				finAccount.setRecordTime(java.time.LocalDateTime.now());
				finAccount.setRecordBy(logonUser.getUuid());
			}
			String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
			Organization organization = logonActionController
					.getOrganizationByUser(logonUser.getUuid());
			if (organization != null) {
				organizationUUID = organization.getUuid();
			}
			finAccountManager.recordAccount(finAccount, logonUser,
					organizationUUID);
			return ServiceJSONParser.genSimpleOKResponse();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (FinAccountException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		}
	}
	
//	@RequestMapping(value = "/printReport", produces = "text/html;charset=UTF-8")
//	public @ResponseBody String printReport(
//			@RequestBody SimpleSEJSONRequest simpleSEJSONRequest) {
//		try {
//			serviceBasicUtilityController.preCheckResourceAccessCore(
//					AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
//			LogonUser logonUser = logonActionController.getLogonUser();
//			if (logonUser == null) {
//				throw new LogonInfoException(
//						LogonInfoException.TYPE_NO_LOGON_USER);
//			}
//			String uuid = simpleSEJSONRequest.getUuid();
//			FinAccount finAccount = (FinAccount) getServiceEntityNodeFromBuffer(
//					FinAccount.NODENAME, uuid);
//			FinAccountObjectRef finAccountObjectRef = (FinAccountObjectRef) getServiceEntityNodeFromBuffer(
//					FinAccountObjectRef.NODENAME, uuid);
//			FinAccountTitle finAccountTitle = (FinAccountTitle) finAccountTitleManager
//					.getEntityNodeByKey(finAccount.getAccountTitleUUID(),
//							IServiceEntityNodeFieldConstant.UUID,
//							FinAccountTitle.NODENAME, logonUser.getClient(),
//							null);
//			FinAccountUIModel finAccountUIModel = new FinAccountUIModel();
//			// Merge the information from UI model to service entity model
//			convFinAccountToUI(finAccount, finAccountUIModel,
//					logonUser.getClient());
//			convAccTitleToUI(finAccountTitle, finAccountUIModel);
//			if (finAccountObjectRef != null) {
//				Account accountObject = finAccountManager
//						.getAllPossibleAccountObject(finAccountObjectRef);
//				this.convAccountObjectToUI(accountObject, finAccountUIModel);
//			}
//			String currentSiteName = ServiceEntityStringHelper.EMPTYSTRING;
//			HostCompany hostCompany = hostCompanyManager
//					.getCurHostCompany(logonUser.getClient());
//			if (hostCompany == null) {
//				throw new LogonInfoException(
//						LogonInfoException.TYPE_NO_ORGNIZATION);
//			}
//			LogonUser currentUser = logonActionController.getLogonUser();
//			if (currentUser == null) {
//				throw new LogonInfoException(
//						LogonInfoException.TYPE_NO_LOGON_USER);
//			}
//			Organization organization = logonActionController
//					.getOrganizationByUser(currentUser.getUuid());
//			if (organization != null) {
//				currentSiteName = organization.getName();
//			}
//			if (finAccount.getAccountantUUID() != null
//					&& !finAccount.getAccountantUUID().equals(
//							ServiceEntityStringHelper.EMPTYSTRING)) {
//				LogonUser accountant = (LogonUser) logonUserManager
//						.getEntityNodeByKey(finAccount.getAccountantUUID(),
//								IServiceEntityNodeFieldConstant.UUID,
//								LogonUser.NODENAME, logonUser.getClient(), null);
//				convertAccountantToUI(accountant, finAccountUIModel);
//			}
//			if (finAccount.getCashierUUID() != null
//					&& !finAccount.getCashierUUID().equals(
//							ServiceEntityStringHelper.EMPTYSTRING)) {
//				LogonUser cashier = (LogonUser) logonUserManager
//						.getEntityNodeByKey(finAccount.getCashierUUID(),
//								IServiceEntityNodeFieldConstant.UUID,
//								LogonUser.NODENAME, logonUser.getClient(), null);
//				convertCashierToUI(cashier, finAccountUIModel);
//			}
//			String resFileFullName = finAccountReportService
//					.getTemplateResouceURL(finAccountTitle.getFinAccountType());
//			// get report file and then load into jasperDesign
//			JasperDesign jasperDesign = JRXmlLoader.load(resFileFullName);
//			// compile the jasperDesign
//			JasperReport jasperReport = JasperCompileManager
//					.compileReport(jasperDesign);
//			String currentDate = DefaultDateFormatConstant.DATE_MIN_FORMAT
//					.format(new Date());
//			List<String> tmpArray = new ArrayList<String>();
//			tmpArray.add("value1");
//			tmpArray.add("value2");
//			// fill the ready report with data and parameter
//			JasperPrint jasperPrint = JasperFillManager.fillReport(
//					jasperReport, finAccountReportService.buildReportMap(
//							finAccountUIModel, hostCompany, currentUser,
//							currentDate, currentSiteName),
//					new JRBeanCollectionDataSource(tmpArray));
//			// view the report using JasperViewer
//			// JasperViewer.viewReport(jasperPrint);
//			JasperViewer view = new JasperViewer(jasperPrint, false);
//			view.pack();
//			view.setVisible(true);
//			return ServiceJSONParser.genSimpleOKResponse();
//		} catch (ServiceEntityConfigureException e) {
//			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
//		} catch (LogonInfoException e) {
//			return ServiceJSONParser.generateSimpleErrorJSON(e
//					.getErrorMessage());
//		} catch (Exception e) {
//			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
//		}
//	}

	@RequestMapping(value = "/auditFrame", produces = "text/html;charset=UTF-8")
	public @ResponseBody String auditAccountFrame(
			@RequestBody SimpleSEJSONRequest request) {
		String baseUUID = request.getUuid();
		try {

			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			FinAccount finAccount = (FinAccount) finAccountManager
					.getEntityNodeByKey(baseUUID,
							IServiceEntityNodeFieldConstant.UUID,
							FinAccount.NODENAME, logonUser.getClient(), null);
			boolean auditEnableFlag = finAccountManager.getAuditEnableFlag(
					finAccount, logonUser);
			if (auditEnableFlag) {
				finAccount.setAuditStatus(FinAccount.AUDIT_DONE);
				finAccount.setAuditTime(java.time.LocalDateTime.now());
				finAccount.setAuditBy(logonUser.getUuid());
				Organization organization = logonActionController
						.getOrganizationByUser(logonUser.getUuid());
				String orgUUID = ServiceEntityStringHelper.EMPTYSTRING;
				if (organization != null) {
					orgUUID = organization.getUuid();
				}
				finAccountManager.updateSENode(finAccount, logonUser.getUuid(),
						orgUUID);
				String msg = finAccountMessageHelper
						.getMessage(FinAccountMessageHelper.MSG_AUDIT_DONE);
				return genFINDONERespone(msg);
			}
			String errorMessage = ServiceExceptionHelper.getErrorMessage(
					FinAccountException.class,
					FinAccountException.TYPE_CANNOT_AUDIT);
			return genFINDONERespone(errorMessage);
		} catch (FinAccountException e) {
			return genFINDONERespone(e.getErrorMessage());
		} catch (LogonInfoException e) {
			return genFINErrorResponse(e.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return genFINErrorResponse(e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			return genFINErrorResponse(e.getMessage());
		}
	}

	@RequestMapping(value = "/preAudit", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preAudit(
			@RequestBody FinAccountUIModel finAccountUIModel) {
		String baseUUID = finAccountUIModel.getUuid();
		try {
			FinAccount finAccount = (FinAccount) getServiceEntityNodeFromBuffer(
					FinAccount.NODENAME, baseUUID);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			boolean auditEnableFlag = finAccountManager.getAuditEnableFlag(
					finAccount, logonUser);
			if (auditEnableFlag) {
				return ServiceJSONDataConstants.DEF_SIMPLE_SUCCESS_JSON;
			} else {
				return ServiceJSONDataConstants.DEF_UNKONWON_ERROR_JSON;
			}
		} catch (FinAccountException e) {
			return genFINErrorResponse(e.getErrorMessage());
		} catch (LogonInfoException e) {
			return genFINErrorResponse(e.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return genFINErrorResponse(e.getMessage());
		}
	}

	@RequestMapping(value = "/clearRcordAccountFrame", produces = "text/html;charset=UTF-8")
	public @ResponseBody String clearRcordAccountFrame(
			@RequestBody SimpleSEJSONRequest request) {
		String baseUUID = request.getUuid();
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, IFinanceActionCode.ACID_RECORD);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			FinAccount finAccount = (FinAccount) finAccountManager
					.getEntityNodeByKey(baseUUID,
							IServiceEntityNodeFieldConstant.UUID,
							FinAccount.NODENAME, logonUser.getClient(), null);
			boolean recordEnableFlag = finAccountManager.getRecordEnableFlag(
					finAccount, logonUser);
			if (recordEnableFlag) {

				Organization organization = logonActionController
						.getOrganizationByUser(logonUser.getUuid());
				String orgUUID = ServiceEntityStringHelper.EMPTYSTRING;
				if (organization != null) {
					orgUUID = organization.getUuid();
				}
				finAccountManager.clearRecordAccount(finAccount, logonUser,
						orgUUID);
				finAccountManager.updateSENode(finAccount, logonUser.getUuid(),
						orgUUID);
				String msg = finAccountMessageHelper
						.getMessage(FinAccountMessageHelper.MSG_RECORD_DONE);
				return genFINDONERespone(msg);
			}
			String errorMessage = ServiceExceptionHelper.getErrorMessage(
					FinAccountException.class,
					FinAccountException.TYPE_CANNOT_RECORD);
			return genFINDONERespone(errorMessage);
		} catch (FinAccountException e) {
			return genFINDONERespone(e.getErrorMessage());
		} catch (LogonInfoException e) {
			return genFINErrorResponse(e.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return genFINErrorResponse(e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			return genFINErrorResponse(e.getMessage());
		} catch (AuthorizationException e) {
			return genFINErrorResponse(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/preRecord", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preRecord(
			@RequestBody FinAccountUIModel finAccountUIModel) {
		String baseUUID = finAccountUIModel.getUuid();
		try {
			FinAccount finAccount = (FinAccount) getServiceEntityNodeFromBuffer(
					FinAccount.NODENAME, baseUUID);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			boolean recordEnableFlag = finAccountManager.getRecordEnableFlag(
					finAccount, logonUser);
			if (recordEnableFlag) {
				return ServiceJSONDataConstants.DEF_SIMPLE_SUCCESS_JSON;
			} else {
				return ServiceJSONDataConstants.DEF_UNKONWON_ERROR_JSON;
			}
		} catch (FinAccountException e) {
			return genFINErrorResponse(e.getErrorMessage());
		} catch (LogonInfoException e) {
			return genFINErrorResponse(e.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return genFINErrorResponse(e.getMessage());
		}
	}

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLockService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preLockService(
			@RequestBody SimpleSEJSONRequest request) {
		return preLock(request.getUuid());
	}

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preLock(String uuid) {
		try {
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			FinAccount finAccount = (FinAccount) finAccountManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							FinAccount.NODENAME, logonUser.getClient(), null);
			String baseUUID = finAccount.getUuid();

			FinAccountObjectRef finAccountObjectRef = (FinAccountObjectRef) finAccountManager
					.getEntityNodeByKey(baseUUID,
							IServiceEntityNodeFieldConstant.ROOTNODEUUID,
							FinAccountObjectRef.NODENAME,
							logonUser.getClient(), null);
			List<ServiceEntityNode> lockSEList = new ArrayList<ServiceEntityNode>();
			lockSEList.add(finAccount);
			lockSEList.add(finAccountObjectRef);
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					finAccount.getId(), finAccount.getId(), baseUUID);
		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e
					.getErrorMessage());
		}
	}


	/**
	 * Save Internal method for post-finance actions
	 * 
	 * @param finAccountUIModel
	 * @throws ServiceEntityConfigureException
	 * @throws LogonInfoException
	 */
	protected void saveInternalFinAction(FinAccountUIModel finAccountUIModel)
			throws ServiceEntityConfigureException, LogonInfoException {
		String baseUUID = finAccountUIModel.getUuid();
		FinAccount finAccount = (FinAccount) getServiceEntityNodeFromBuffer(
				FinAccount.NODENAME, baseUUID);
		// conv UI fields to model
		finAccount.setAuditNote(finAccountUIModel.getAuditNote());
		finAccount.setVerifyNote(finAccountUIModel.getVerifyNote());
		finAccount.setRecordNote(finAccountUIModel.getRecordNote());
		finAccount.setToRecordAmount(finAccountUIModel.getToRecordAmount());
		finAccount.setRecordedAmount(finAccountUIModel.getRecordedAmount());
		finAccount.setAdjustAmount(finAccountUIModel.getAdjustAmount());
		finAccount.setAdjustDirection(finAccountUIModel.getAdjustDirection());
		finAccountManager.setRecordStatusByCheckAmount(finAccount);
		this.save(baseUUID, finAccountManager,
				logonActionController.getLogonUser(),
				logonActionController.getOrganization());
	}


	/**
	 * [Internal method] the common API in this class to record service entity
	 * exception
	 * 
	 * @param ex
	 * @throws LogonInfoException
	 * @throws ServiceEntityConfigureException
	 */
	protected void autoRecordException(ServiceEntityException ex)
			throws LogonInfoException, ServiceEntityConfigureException {
		String source = this.getClass().getName();
		serviceDefaultExceptionRecordHelper.autoRecordServiceException(ex,
				null, source, logonActionController.getLogonUser(),
				logonActionController.getOrganization());
	}

	/**
	 * [Internal method] Get cargo from this controller buffer, only one cargo
	 * instance should be retrieved
	 * 
	 * @param customerUUID
	 *            :Customer uuid
	 * @return
	 */
	protected Account getCustomerFromBuffer(String customerUUID) {
		if (this.seBindListCustomer == null
				|| this.seBindListCustomer.size() == 0) {
			return null;
		} else {
			if (customerUUID == null
					|| customerUUID
							.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
				ServiceEntityBindModel seBind0 = seBindListCustomer.get(0);
				return (Account) seBind0.getSeNode();
			} else {
				for (ServiceEntityBindModel seBind : seBindListCustomer) {
					if (seBind.getSeNode().getUuid().equals(customerUUID))
						return (Account) seBind.getSeNode();
				}
				return null;
			}
		}
	}

	/**
	 * [Internal method] Get customer list from this controller buffer, only one
	 * customer instance should be retrieved
	 * 
	 * @param baseUUID
	 *            :base UUID
	 * @return
	 */
	protected List<ServiceEntityNode> getCustomerListFromBufferByBaseUUID(
			String baseUUID) {
		List<ServiceEntityNode> custList = new ArrayList<ServiceEntityNode>();
		if (this.seBindListCustomer == null
				|| this.seBindListCustomer.size() == 0) {
			return null;
		} else {
			if (baseUUID == null
					|| baseUUID.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
				return null;
			} else {
				for (ServiceEntityBindModel seBind : seBindListCustomer) {
					if (seBind.getBaseUUID().equals(baseUUID))
						custList.add(seBind.getSeNode());
				}
				return custList;
			}
		}
	}

	/**
	 * update the customer backup buffer, make the data in backup buffer the
	 * same as the buffer and return the buffer, only backup the buffer
	 * identified with [baseUUID]
	 * 
	 * @param baseUUID
	 * @return
	 */
	protected void backupCustomer(String baseUUID) {
		for (ServiceEntityBindModel seCustomer : this.seBindListCustomer) {
			// Fiter with current base uuid
			if (!baseUUID.equals(seCustomer.getBaseUUID()))
				continue;
			seCustomer
					.setProcessMode(ServiceEntityBindModel.PROCESSMODE_STANDBY);
			boolean existFlag = false;
			for (ServiceEntityBindModel customerBack : seBindListCustomerBack) {
				if (customerBack.getSeNode().getUuid()
						.equals(seCustomer.getSeNode().getUuid())) {
					customerBack.setSeNode((ServiceEntityNode) seCustomer
							.getSeNode().clone());
					existFlag = true;
				}
			}
			if (!existFlag) {
				ServiceEntityNode seNode = (ServiceEntityNode) seCustomer
						.getSeNode().clone();
				ServiceEntityBindModel seCustomerBack = new ServiceEntityBindModel(
						seNode, ServiceEntityBindModel.PROCESSMODE_STANDBY,
						baseUUID);
				this.seBindListCustomerBack.add(seCustomerBack);
			}
		}
	}


	/**
	 * convert accountant to UI
	 * 
	 * @param accountant
	 * @param finAccountUIModel
	 */
	public void convertAccountantToUI(LogonUser accountant,
			FinAccountUIModel finAccountUIModel) {
		if (accountant != null) {
			finAccountUIModel.setAccountantName(accountant.getName());
		}
	}

	/**
	 * convert accountant to UI
	 * 
	 * @param cashier
	 * @param finAccountUIModel
	 */
	public void convertCashierToUI(LogonUser cashier,
			FinAccountUIModel finAccountUIModel) {
		if (cashier != null) {
			finAccountUIModel.setCashierName(cashier.getName());
		}
	}



	/**
	 * Convert AccountUIModel to temp customer
	 * 
	 * @param finAccountUIModel
	 * @param indivdualCustomer
	 */
	public void convUIToCustomer(FinAccountUIModel finAccountUIModel,
			IndividualCustomer indivdualCustomer) {
		if (indivdualCustomer != null) {
			if (finAccountUIModel.getAccountObjectName() != null
					&& !finAccountUIModel.getAccountObjectName().equals(
							ServiceEntityStringHelper.EMPTYSTRING)) {
				// In order to avoid empty AO name set to model
				indivdualCustomer.setName(finAccountUIModel
						.getAccountObjectName());
			}
		}
	}

	/**
	 * Convert Account to AccountUIModel
	 * 
	 * @param finaccount
	 * @param finAccountUIModel
	 * @throws ServiceEntityConfigureException
	 * @throws IOException
	 * @throws ServiceEntityInstallationException
	 */
	public void convFinAccountToUI(FinAccount finaccount,
			FinAccountUIModel finAccountUIModel, String client)
			throws ServiceEntityConfigureException,
			ServiceEntityInstallationException, IOException {
		finAccountUIModel.setUuid(finaccount.getUuid());
		finAccountUIModel.setId(finaccount.getId());
		// finAccountUIModel.setAccountObjectName(finaccount.getAccountObject());
		finAccountUIModel.setDocumentType(finaccount.getDocumentType());
		finAccountUIModel.setPaymentType(finaccount.getPaymentType());
		finAccountUIModel.setAmount(finaccount.getAmount());
		finAccountUIModel.setAmountShow(finaccount.getAmount());
		finAccountUIModel.setAccountTitleUUID(finaccount.getAccountTitleUUID());
		finAccountUIModel.setNote(finaccount.getNote());
		finAccountUIModel.setAuditStatus(finaccount.getAuditStatus());
		finAccountUIModel.setRecordStatus(finaccount.getRecordStatus());
		finAccountUIModel.setVerifyStatus(finaccount.getVerifyStatus());
		finAccountUIModel.setVerifyBy(finaccount.getVerifyBy());
		finAccountUIModel.setAuditBy(finaccount.getAuditBy());
		finAccountUIModel.setRecordBy(finaccount.getRecordBy());
		finAccountUIModel.setAdjustAmount(finaccount.getAdjustAmount());
		finAccountUIModel.setAdjustDirection(finaccount.getAdjustDirection());
		if (finaccount.getCreatedTime() != null
				&& !finaccount.getCreatedTime().equals(
						ServiceEntityStringHelper.EMPTYSTRING)) {
			finAccountUIModel
					.setDateStr(DefaultDateFormatConstant.DATE_MIN_FORMAT
							.format(finaccount.getCreatedTime()));
		}
		LogonUser createdUser = (LogonUser) logonUserManager
				.getEntityNodeByKey(finaccount.getCreatedBy(),
						IServiceEntityNodeFieldConstant.UUID,
						LogonUser.NODENAME, client, null);
		if (createdUser != null) {
			finAccountUIModel.setCashierName(createdUser.getName());
		}
		finAccountUIModel.setToRecordAmount(finaccount.getToRecordAmount());
		finAccountUIModel.setRecordedAmount(finaccount.getRecordedAmount());
		LogonUser verifyByUser = (LogonUser) logonUserManager
				.getEntityNodeByKey(finaccount.getVerifyBy(),
						IServiceEntityNodeFieldConstant.UUID,
						LogonUser.NODENAME, client, null);
		if (verifyByUser != null) {
			finAccountUIModel.setVerifyByName(verifyByUser.getName());
		}
		finAccountUIModel.setVerifyNote(finaccount.getVerifyNote());
		finAccountUIModel
				.setVerifyTime(DefaultDateFormatConstant.DATE_MIN_FORMAT
						.format(finaccount.getVerifyTime()));
		LogonUser auditByUser = (LogonUser) logonUserManager
				.getEntityNodeByKey(finaccount.getAuditBy(),
						IServiceEntityNodeFieldConstant.UUID,
						LogonUser.NODENAME, client, null);
		if (auditByUser != null) {
			finAccountUIModel.setAuditByName(auditByUser.getName());
		}
		finAccountUIModel.setAuditNote(finaccount.getAuditNote());
		finAccountUIModel
				.setAuditTime(DefaultDateFormatConstant.DATE_MIN_FORMAT
						.format(finaccount.getAuditTime()));
		LogonUser recordByUser = (LogonUser) logonUserManager
				.getEntityNodeByKey(finaccount.getRecordBy(),
						IServiceEntityNodeFieldConstant.UUID,
						LogonUser.NODENAME, client, null);
		if (recordByUser != null) {
			finAccountUIModel.setRecordByName(recordByUser.getName());
		}
		finAccountUIModel.setRecordNote(finaccount.getRecordNote());
		finAccountUIModel
				.setRecordTime(DefaultDateFormatConstant.DATE_MIN_FORMAT
						.format(finaccount.getRecordTime()));

		// Logical to set Lock MSG
		if (finaccount.getAuditLockMSG() == null
				|| finaccount.getAuditLockMSG().equals(
						ServiceEntityStringHelper.EMPTYSTRING)) {
			String labelAudit = financeAccountMessageHelper
					.getMessage(FinanceAccountMessageHelper.LABEL_AUDIT);
			finAccountUIModel.setAuditLockMSG(labelAudit);
		} else {
			finAccountUIModel.setAuditLockMSG(finaccount.getAuditLockMSG());
		}
		if (finaccount.getVerifyLockMSG() == null
				|| finaccount.getVerifyLockMSG().equals(
						ServiceEntityStringHelper.EMPTYSTRING)) {
			String labelVerify = financeAccountMessageHelper
					.getMessage(FinanceAccountMessageHelper.LABEL_VERIFY);
			finAccountUIModel.setVerifyLockMSG(labelVerify);
		} else {
			finAccountUIModel.setVerifyLockMSG(finaccount.getVerifyLockMSG());
		}
		if (finaccount.getRecordLockMSG() == null
				|| finaccount.getRecordLockMSG().equals(
						ServiceEntityStringHelper.EMPTYSTRING)) {
			String labelRecord = financeAccountMessageHelper
					.getMessage(FinanceAccountMessageHelper.LABEL_RECORD);
			finAccountUIModel.setRecordLockMSG(labelRecord);
		} else {
			finAccountUIModel.setRecordLockMSG(finaccount.getRecordLockMSG());
		}
	}

	public void convAccTitleToUI(FinAccountTitle finAccountTitle,
			FinAccountUIModel finAccountUIModel) {
		if (finAccountTitle != null) {
			String uiName = finAccountTitleTreeController
					.getAccountTitleUIName(finAccountTitle);
			finAccountUIModel.setAccountTitleName(uiName);
			finAccountUIModel.setAccountTitleUUID(finAccountTitle.getUuid());
			finAccountUIModel.setFinAccountType(finAccountTitle
					.getFinAccountType());
		}
	}

	/**
	 * Convert Account Object to AccountUIModel
	 * 
	 * @param accountObject
	 * @param finAccountUIModel
	 */
	public void convAccountObjectToUI(Account accountObject,
			FinAccountUIModel finAccountUIModel) {
		if (accountObject != null) {
			finAccountUIModel.setRefAccountObjectUUID(accountObject.getUuid());
			finAccountUIModel.setAccountObjectName(accountObject.getName());
			int accountObjectType = accountManager
					.getAccountType(accountObject);
			finAccountUIModel.setAccountObjectType(accountObjectType);
		}
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest, finAccountManager);
	}

	@RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String deleteModule(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		try {
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_DELETE);
			String uuid = serviceExitLockJSONModule.getUuid();
			finAccountManager.admDeleteFinAccountUnion(uuid);
			return ServiceJSONParser.genSimpleOKResponse();
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
