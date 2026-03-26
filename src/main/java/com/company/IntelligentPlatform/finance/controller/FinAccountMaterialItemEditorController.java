package com.company.IntelligentPlatform.finance.controller;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.finance.dto.FinAccountMatItemAttachmentUIModel;
import com.company.IntelligentPlatform.finance.dto.FinAccountMaterialItemServiceUIModel;
import com.company.IntelligentPlatform.finance.dto.FinAccountMaterialItemServiceUIModelExtension;
import com.company.IntelligentPlatform.finance.service.FinAccountManager;
import com.company.IntelligentPlatform.finance.service.FinAccountMaterialItemServiceModel;
import com.company.IntelligentPlatform.finance.model.FinAccount;
import com.company.IntelligentPlatform.finance.model.FinAccountMatItemAttachment;
import com.company.IntelligentPlatform.finance.model.FinAccountMaterialItem;

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

import com.company.IntelligentPlatform.common.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.common.service.IndividualCustomerManager;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.AttachmentConstantHelper;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Scope("session")
@Controller(value = "finAccountMaterialItemEditorController")
@RequestMapping(value = "/finAccountMaterialItem")
public class FinAccountMaterialItemEditorController extends SEEditorController {

	private static final Logger logger = LoggerFactory.getLogger(FinAccountMaterialItemEditorController.class);

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected FinAccountMaterialItemServiceUIModelExtension finAccountMaterialItemServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected FinAccountManager finAccountManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected IndividualCustomerManager individualCustomerManager;

	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected LogonInfoManager logonInfoManager;

	/**
	 * load the attachment content to consumer.
	 */
	@RequestMapping(value = "/loadAttachment")
	public ResponseEntity<byte[]> loadAttachment(String uuid) {
		final HttpHeaders headers = new HttpHeaders();
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			FinAccountMatItemAttachment finAccountMatItemAttachment = (FinAccountMatItemAttachment) finAccountManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							FinAccountMatItemAttachment.NODENAME, null);
			if (finAccountMatItemAttachment != null) {
				finAccountManager.setAttachmentHttpHeaders(headers,
						finAccountMatItemAttachment.getFileType(),
						finAccountMatItemAttachment.getName());
				return new ResponseEntity<byte[]>(
						finAccountMatItemAttachment.getContent(), headers,
						HttpStatus.CREATED);
			}
		} catch (ServiceEntityConfigureException e) {
			logger.error("Failed to load attachment", e);
		} catch (LogonInfoException e) {
			logger.error("Logon error loading attachment", e);
		} catch (AuthorizationException e) {
			logger.error("Authorization error loading attachment", e);
		}
		return null;
	}

	/**
	 * Delete attachment
	 */
	@RequestMapping(value = "/deleteAttachment", produces = "text/html;charset=UTF-8")
	public @ResponseBody String deleteAttachment(
			@RequestBody SimpleSEJSONRequest request) {
		return deleteAttachmentCore(request.getUuid());
	}

	/**
	 * Delete Attachment core method
	 */
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
			FinAccountMatItemAttachment finAccountMatItemAttachment = (FinAccountMatItemAttachment) finAccountManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							FinAccountMatItemAttachment.NODENAME, null);
			String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
			Organization organization = logonActionController
					.getOrganizationByUser(logonUser.getUuid());
			if (organization != null) {
				organizationUUID = organization.getUuid();
			}
			if (finAccountMatItemAttachment != null) {
				finAccountManager.deleteSENode(finAccountMatItemAttachment,
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
	};

	/**
	 * Upload the attachment content information.
	 */
	@RequestMapping(value = "/uploadAttachment", consumes = "multipart/form-data", method = RequestMethod.POST)
	public @ResponseBody String uploadAttachment(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			request.setCharacterEncoding("UTF-8");
			MultipartHttpServletRequest muti = (MultipartHttpServletRequest) request;
			MultiValueMap<String, MultipartFile> map = muti.getMultiFileMap();
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
						FinAccountMaterialItem finAccountMaterialItem = (FinAccountMaterialItem) finAccountManager
								.getEntityNodeByKey(baseUUID,
										IServiceEntityNodeFieldConstant.UUID,
										FinAccountMaterialItem.NODENAME, null);
						FinAccountMatItemAttachment finAccountMatItemAttachment = (FinAccountMatItemAttachment) finAccountManager
								.getEntityNodeByKey(uuid,
										IServiceEntityNodeFieldConstant.UUID,
										FinAccountMatItemAttachment.NODENAME,
										null);
						if (finAccountMatItemAttachment == null) {
							finAccountMatItemAttachment = (FinAccountMatItemAttachment) finAccountManager
									.newEntityNode(
											finAccountMaterialItem,
											FinAccountMatItemAttachment.NODENAME);
						}
						if (ServiceEntityStringHelper
								.checkNullString(finAccountMatItemAttachment
										.getName())) {
							finAccountMatItemAttachment.setName(multipartFile
									.getOriginalFilename());
						}
						finAccountMatItemAttachment.setContent(bytes);
						finAccountMatItemAttachment.setFileType(fileType);
						finAccountMatItemAttachment.setId(multipartFile
								.getOriginalFilename());
						String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
						Organization organization = logonActionController
								.getOrganizationByUser(logonUser.getUuid());
						if (organization != null) {
							organizationUUID = organization.getUuid();
						}
						finAccountManager.updateSENode(
								finAccountMatItemAttachment,
								logonUser.getUuid(), organizationUUID);
					} catch (IllegalStateException | IOException e) {
						logger.error("Failed to read uploaded file bytes", e);
					}
				}
			}
			return ServiceJSONParser.genSimpleOKResponse();
		} catch (ServiceEntityConfigureException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		} catch (LogonInfoException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex
					.getErrorMessage());
		} catch (UnsupportedEncodingException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		} catch (AuthorizationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex
					.getErrorMessage());
		}
	};

	/**
	 * Upload the attachment text information.
	 */
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
			FinAccountMaterialItem finAccountMaterialItem = (FinAccountMaterialItem) finAccountManager
					.getEntityNodeByKey(request.getBaseUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							FinAccountMaterialItem.NODENAME, null);
			FinAccountMatItemAttachment finAccountMatItemAttachment = (FinAccountMatItemAttachment) finAccountManager
					.getEntityNodeByKey(request.getBaseUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							FinAccountMatItemAttachment.NODENAME, null);
			if (finAccountMatItemAttachment == null) {
				finAccountMatItemAttachment = (FinAccountMatItemAttachment) finAccountManager
						.newEntityNode(finAccountMaterialItem,
								FinAccountMatItemAttachment.NODENAME);
			}
			finAccountMatItemAttachment.setName(request.getAttachmentTitle());
			finAccountMatItemAttachment.setNote(request
					.getAttachmentDescription());
			String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
			Organization organization = logonActionController
					.getOrganizationByUser(logonUser.getUuid());
			if (organization != null) {
				organizationUUID = organization.getUuid();
			}
			finAccountManager.updateSENode(finAccountMatItemAttachment,
					logonUser.getUuid(), organizationUUID);
			return ServiceJSONParser
					.genDefOKJSONObject(finAccountMatItemAttachment);
		} catch (ServiceEntityConfigureException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		} catch (LogonInfoException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex
					.getErrorMessage());
		} catch (AuthorizationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex
					.getErrorMessage());
		}
	};

	private FinAccountMaterialItemServiceUIModel parseToServiceUIModel(
			String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("finAccountMatItemAttachmentUIModelList",
				FinAccountMatItemAttachmentUIModel.class);
		FinAccountMaterialItemServiceUIModel finAccountMaterialItemServiceUIModel = (FinAccountMaterialItemServiceUIModel) JSONObject
				.toBean(jsonObject, FinAccountMaterialItemServiceUIModel.class,
						classMap);
		return finAccountMaterialItemServiceUIModel;
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		FinAccountMaterialItemServiceUIModel finAccountMaterialItemServiceUIModel = parseToServiceUIModel(request);
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
			FinAccountMaterialItemServiceModel finAccountMaterialItemServiceModel = (FinAccountMaterialItemServiceModel) finAccountManager
					.genServiceModuleFromServiceUIModel(
							FinAccountMaterialItemServiceModel.class,
							FinAccountMaterialItemServiceUIModel.class,
							finAccountMaterialItemServiceUIModel,
							finAccountMaterialItemServiceUIModelExtension);
			finAccountManager.updateServiceModuleWithDelete(
					FinAccountMaterialItemServiceModel.class,
					finAccountMaterialItemServiceModel, logonUser.getUuid(),
					organizationUUID);
			// Refresh service model			
			finAccountMaterialItemServiceUIModel = refreshLoadServiceUIModel(
					finAccountMaterialItemServiceModel
					.getFinAccountMaterialItem().getUuid(), ISystemActionCode.ACID_EDIT,
					logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(finAccountMaterialItemServiceUIModel);
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
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

	@RequestMapping(value = "deleteModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String deleteModule(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			FinAccountMaterialItem finAccountMaterialItem = (FinAccountMaterialItem) finAccountManager
					.getEntityNodeByKey(request.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							FinAccountMaterialItem.NODENAME,
							logonUser.getClient(), null, true);
			if (finAccountMaterialItem != null) {
				finAccountManager.deleteSENode(finAccountMaterialItem,
						logonUser.getUuid(), null);
			}
			return ServiceJSONParser.genSimpleOKResponse();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
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
							FinAccount.NODENAME, logonUser.getClient(), null);
			FinAccountMaterialItem finAccountMaterialItem = (FinAccountMaterialItem) finAccountManager
					.newEntityNode(finAccount, FinAccountMaterialItem.NODENAME);
			FinAccountMaterialItemServiceModel finAccountMaterialItemServiceModel = new FinAccountMaterialItemServiceModel();
			finAccountMaterialItemServiceModel
					.setFinAccountMaterialItem(finAccountMaterialItem);
			FinAccountMaterialItemServiceUIModel finAccountMaterialItemServiceUIModel = (FinAccountMaterialItemServiceUIModel) finAccountManager
					.genServiceUIModuleFromServiceModel(
							FinAccountMaterialItemServiceUIModel.class,
							FinAccountMaterialItemServiceModel.class,
							finAccountMaterialItemServiceModel,
							finAccountMaterialItemServiceUIModelExtension,
							logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(finAccountMaterialItemServiceUIModel);
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
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

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest, finAccountManager);
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
			FinAccountMaterialItem finAccountMaterialItem = (FinAccountMaterialItem) finAccountManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							FinAccountMaterialItem.NODENAME,
							logonUser.getClient(), null);
			String baseUUID = finAccountMaterialItem.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<ServiceEntityNode>();
			lockSEList.add(finAccountMaterialItem);
			List<ServiceEntityNode> lockResult = lockObjectManager
					.preLockServiceEntityList(lockSEList, logonUser.getUuid());
			return lockObjectManager.genJSONLockCheckResult(lockResult,
					finAccountMaterialItem.getName(),
					finAccountMaterialItem.getId(), baseUUID);
		} catch (ServiceEntityConfigureException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
		} catch (LogonInfoException e) {
			return lockObjectManager.genJSONLockCheckOtherIssue(e
					.getErrorMessage());
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
			FinAccountMaterialItem finAccountMaterialItem = (FinAccountMaterialItem) finAccountManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							FinAccountMaterialItem.NODENAME,
							logonUser.getClient(), null);
			LogonInfo logonInfo = logonActionController.getLogonInfo();
			boolean checkAuthor = serviceBasicUtilityController
					.checkTargetDataAccess(logonInfo.getLogonUser(),
							finAccountMaterialItem, ISystemActionCode.ACID_VIEW,
							logonInfo.getAuthorizationActionCodeMap());
			if (checkAuthor == false) {
				throw new AuthorizationException(
						AuthorizationException.TYPE_NO_AUTHORIZATION);
			}
			return ServiceJSONParser.genDefOKJSONObject(finAccountMaterialItem);
		} catch (AuthorizationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex
					.getErrorMessage());
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	private FinAccountMaterialItemServiceUIModel refreshLoadServiceUIModel(
			String uuid, String acId, LogonInfo logonInfo)
			throws ServiceModuleProxyException,
			ServiceEntityConfigureException, ServiceUIModuleProxyException,
			LogonInfoException, AuthorizationException {
		FinAccountMaterialItem finAccountMaterialItem = (FinAccountMaterialItem) finAccountManager
				.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID,
						FinAccountMaterialItem.NODENAME, logonInfo.getClient(),
						null);
		if (!ServiceEntityStringHelper.checkNullString(acId)) {
			boolean checkAuthor = serviceBasicUtilityController
					.checkTargetDataAccess(logonInfo.getLogonUser(),
							finAccountMaterialItem, acId,
							logonInfo.getAuthorizationActionCodeMap());
			if (!checkAuthor) {
				throw new AuthorizationException(
						AuthorizationException.TYPE_NO_AUTHORIZATION);
			}
		}
		FinAccountMaterialItemServiceModel finAccountMaterialItemServiceModel = (FinAccountMaterialItemServiceModel) finAccountManager
				.loadServiceModule(FinAccountMaterialItemServiceModel.class,
						finAccountMaterialItem);
		FinAccountMaterialItemServiceUIModel finAccountMaterialItemServiceUIModel = (FinAccountMaterialItemServiceUIModel) finAccountManager
				.genServiceUIModuleFromServiceModel(
						FinAccountMaterialItemServiceUIModel.class,
						FinAccountMaterialItemServiceModel.class,
						finAccountMaterialItemServiceModel,
						finAccountMaterialItemServiceUIModelExtension,
						logonInfoManager.setInfoSwitch(logonInfo, false));
		return finAccountMaterialItemServiceUIModel;
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
			FinAccountMaterialItem finAccountMaterialItem = (FinAccountMaterialItem) finAccountManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							FinAccountMaterialItem.NODENAME,
							logonUser.getClient(), null);
			lockSEList.add(finAccountMaterialItem);
			lockObjectManager.lockServiceEntityList(lockSEList, logonUser,
					logonActionController.getOrganizationByUser(logonUser
							.getUuid()));
			FinAccountMaterialItemServiceUIModel finAccountMaterialItemServiceUIModel = refreshLoadServiceUIModel(
					uuid, ISystemActionCode.ACID_EDIT,
					logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(finAccountMaterialItemServiceUIModel);
		} catch (AuthorizationException | LockObjectFailureException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex
					.getErrorMessage());
		} catch (LogonInfoException | ServiceUIModuleProxyException | ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
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
			FinAccountMaterialItemServiceUIModel finAccountMaterialItemServiceUIModel = refreshLoadServiceUIModel(
					uuid, ISystemActionCode.ACID_VIEW,
					logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(finAccountMaterialItemServiceUIModel);
		} catch (AuthorizationException ex) {
			return ex.generateSimpleErrorJSON();
		}  catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch ( ServiceUIModuleProxyException | ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LockObjectFailureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "/getDocFlowList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocFlowList(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			FinAccountMaterialItem finAccountMaterialItem = (FinAccountMaterialItem) finAccountManager
					.getEntityNodeByUUID(uuid, FinAccountMaterialItem.NODENAME,
							logonUser.getClient());
			List<ServiceDocumentExtendUIModel> docFlowList = serviceDocumentComProxy
					.getDocFlowList(finAccountMaterialItem, finAccountManager,
							ISystemActionCode.ACID_VIEW,
							logonActionController.getLogonInfo());
			return ServiceJSONParser.genDefOKJSONArray(docFlowList);
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

}
