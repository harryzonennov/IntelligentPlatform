package com.company.IntelligentPlatform.finance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.finance.dto.FinAccountTitleSearchModel;
import com.company.IntelligentPlatform.finance.dto.FinAccountTitleUIModel;
import com.company.IntelligentPlatform.finance.repository.FinAccountTitleRepository;
import com.company.IntelligentPlatform.finance.model.FinAccount;
import com.company.IntelligentPlatform.finance.model.FinAccountTitle;
import com.company.IntelligentPlatform.finance.model.FinAccountTitleConfigureProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchNodeMapping;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityBindModel;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Logic Manager CLASS FOR Service Entity [AccountTitle]
 * 
 * @author Dylan Yang
 * @date Thu Apr 25 17:36:36 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class FinAccountTitleManager extends ServiceEntityManager {

	@Autowired
	FinAccountTitleRepository finAccountTitleDAO;

	@Autowired
	FinAccountTitleConfigureProxy finAccountTitleConfigureProxy;

	// AccountTitle Id Generator
	FinAccountTitleIdGenerator finAccountTitleIdGenerator;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected BsearchService bsearchService;

	public static final String METHOD_ConvFinAccountTitleToUI = "convFinAccountTitleToUI";

	public static final String METHOD_ConvUIToFinAccountTitle = "convUIToFinAccountTitle";

	public static final String METHOD_ConvParentFinAccountTitleToUI = "convParentFinAccountTitleToUI";

	public static final String METHOD_ConvRootFinAccountTitleToUI = "convRootFinAccountTitleToUI";

	private Map<Integer, String> settleTypeMap;

	private Map<Integer, String> originalTypeMap;

	private Map<Integer, String> finAccountTypeMap;

	private Map<Integer, String> generateTypeMap;

	private Map<Integer, String> categoryMap;

	// Sub AccountTitle Id array
	public int[] subAccountTitleIdArray;
	// Parent AccountTitle Id
	public int parentAccountTitleId;

	public FinAccountTitleManager() {
		super.seConfigureProxy = new FinAccountTitleConfigureProxy();
		// TODO-DAO: super.serviceEntityDAO = new FinAccountTitleDAO();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		// TODO-DAO: super.setServiceEntityDAO(finAccountTitleDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(finAccountTitleConfigureProxy);
	}

	public Map<Integer, String> initSettleTypeMap()
			throws ServiceEntityInstallationException {
		if (this.settleTypeMap == null) {
			this.settleTypeMap = serviceDropdownListHelper.getUIDropDownMap(
					FinAccountTitleUIModel.class, "settleType");
		}
		return this.settleTypeMap;
	}

	public Map<Integer, String> initOriginalTypeMap()
			throws ServiceEntityInstallationException {
		if (this.originalTypeMap == null) {
			this.originalTypeMap = serviceDropdownListHelper.getUIDropDownMap(
					FinAccountTitleUIModel.class, "originalType");
		}
		return this.originalTypeMap;
	}

	public Map<Integer, String> initFinAccountTypeMap()
			throws ServiceEntityInstallationException {
		if (this.finAccountTypeMap == null) {
			this.finAccountTypeMap = serviceDropdownListHelper
					.getUIDropDownMap(FinAccountTitleUIModel.class,
							"finAccountType");
		}
		return this.finAccountTypeMap;
	}

	public Map<Integer, String> initGenerateTypeMap()
			throws ServiceEntityInstallationException {
		if (this.generateTypeMap == null) {
			this.generateTypeMap = serviceDropdownListHelper.getUIDropDownMap(
					FinAccountTitleUIModel.class, "generateType");
		}
		return this.generateTypeMap;
	}

	public Map<Integer, String> initCategoryMap()
			throws ServiceEntityInstallationException {
		if (this.categoryMap == null) {
			this.categoryMap = serviceDropdownListHelper.getUIDropDownMap(
					FinAccountTitleUIModel.class, "category");
		}
		return this.categoryMap;
	}

	public void archiveDeleteTitle(FinAccountTitle finAccountTitle,
			String client, String logonUserUUID, String resOrgUUID)
			throws ServiceEntityConfigureException, FinAccountTitleException {
		// Try to find possible children finance title
		List<ServiceEntityNode> subFinAccTitleList = this
				.getEntityNodeListByKey(finAccountTitle.getUuid(),
						IServiceEntityNodeFieldConstant.PARENTNODEUUID,
						FinAccountTitle.NODENAME, client, null);
		if (!ServiceCollectionsHelper.checkNullList(subFinAccTitleList)) {
			if (subFinAccTitleList.size() > 1) {
				throw new FinAccountTitleException(
						FinAccountTitleException.TYPE_CANNOT_DEL_SUB_TITLE,
						finAccountTitle.getId());
			} else {
				// only one instance found
				FinAccountTitle tmpFinAccTitle = (FinAccountTitle) subFinAccTitleList
						.get(0);
				if (!finAccountTitle.getUuid().equals(tmpFinAccTitle.getUuid())) {
					throw new FinAccountTitleException(
							FinAccountTitleException.TYPE_CANNOT_DEL_SUB_TITLE,
							finAccountTitle.getId());
				}
			}
		}
		// Call super class method
		super.archiveDeleteEntityByKey(finAccountTitle.getUuid(),
				IServiceEntityNodeFieldConstant.UUID, client,
				FinAccountTitle.NODENAME, logonUserUUID, resOrgUUID);
	}

	public void convFinAccountTitleToUI(FinAccountTitle finAccountTitle,
			FinAccountTitleUIModel finAccountTitleUIModel)
			throws ServiceEntityInstallationException {
		if (finAccountTitle != null) {
			if (!ServiceEntityStringHelper.checkNullString(finAccountTitle
					.getUuid())) {
				finAccountTitleUIModel.setUuid(finAccountTitle.getUuid());
			}
			if (!ServiceEntityStringHelper.checkNullString(finAccountTitle
					.getParentNodeUUID())) {
				finAccountTitleUIModel.setParentNodeUUID(finAccountTitle
						.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(finAccountTitle
					.getRootNodeUUID())) {
				finAccountTitleUIModel.setRootNodeUUID(finAccountTitle
						.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(finAccountTitle
					.getClient())) {
				finAccountTitleUIModel.setClient(finAccountTitle.getClient());
			}
			finAccountTitleUIModel.setSettleType(finAccountTitle
					.getSettleType());
			finAccountTitleUIModel.setOriginalType(finAccountTitle
					.getOriginalType());
			finAccountTitleUIModel.setFinAccountType(finAccountTitle
					.getFinAccountType());
			initFinAccountTypeMap();
			finAccountTitleUIModel
					.setFinAccountTypeValue(this.finAccountTypeMap
							.get(finAccountTitle.getFinAccountType()));
			initCategoryMap();
			finAccountTitleUIModel.setCategoryValue(this.categoryMap
					.get(finAccountTitle.getCategory()));
			finAccountTitleUIModel.setNote(finAccountTitle.getNote());
			finAccountTitleUIModel.setId(finAccountTitle.getId());
			finAccountTitleUIModel.setName(finAccountTitle.getName());
			finAccountTitleUIModel.setNote(finAccountTitle.getNote());
			finAccountTitleUIModel.setCategory(finAccountTitle.getCategory());
			finAccountTitleUIModel.setParentAccountTitleUUID(finAccountTitle
					.getParentAccountTitleUUID());
			finAccountTitleUIModel.setRootAccountTitleUUID(finAccountTitle
					.getRootAccountTitleUUID());
			finAccountTitleUIModel.setOriginalType(finAccountTitle
					.getOriginalType());
			finAccountTitleUIModel.setSettleType(finAccountTitle
					.getSettleType());
		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:finAccountTitle
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToFinAccountTitle(
			FinAccountTitleUIModel finAccountTitleUIModel,
			FinAccountTitle rawEntity) {
		if (!ServiceEntityStringHelper.checkNullString(finAccountTitleUIModel
				.getUuid())) {
			rawEntity.setUuid(finAccountTitleUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper.checkNullString(finAccountTitleUIModel
				.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(finAccountTitleUIModel
					.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(finAccountTitleUIModel
				.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(finAccountTitleUIModel.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(finAccountTitleUIModel
				.getClient())) {
			rawEntity.setClient(finAccountTitleUIModel.getClient());
		}
		if (!ServiceEntityStringHelper.checkNullString(finAccountTitleUIModel
				.getId())) {
			rawEntity.setId(finAccountTitleUIModel.getId());
		}
		int tmpFinAccountType = finAccountTitleUIModel.getFinAccountType();
		if (tmpFinAccountType == FinAccountTitle.FIN_ACCOUNTTYPE_CREDIT
				|| tmpFinAccountType == FinAccountTitle.FIN_ACCOUNTTYPE_DEBIT) {
			rawEntity.setFinAccountType(finAccountTitleUIModel
					.getFinAccountType());
		}
		rawEntity.setCategory(finAccountTitleUIModel.getCategory());
		rawEntity.setParentAccountTitleUUID(finAccountTitleUIModel
				.getParentAccountTitleUUID());
		rawEntity.setSettleType(finAccountTitleUIModel.getSettleType());
		rawEntity.setRootAccountTitleUUID(finAccountTitleUIModel
				.getRootAccountTitleUUID());
		rawEntity.setSettleType(finAccountTitleUIModel.getSettleType());
		rawEntity.setOriginalType(finAccountTitleUIModel.getOriginalType());
		rawEntity.setUuid(finAccountTitleUIModel.getUuid());
		rawEntity.setRootAccountTitleUUID(finAccountTitleUIModel
				.getRootAccountTitleUUID());
		rawEntity.setClient(finAccountTitleUIModel.getClient());
		rawEntity.setNote(finAccountTitleUIModel.getNote());
		rawEntity.setCategory(finAccountTitleUIModel.getCategory());
		rawEntity.setName(finAccountTitleUIModel.getName());
		rawEntity.setParentAccountTitleUUID(finAccountTitleUIModel
				.getParentAccountTitleUUID());
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convParentFinAccountTitleToUI(
			FinAccountTitle parentAccountTitle,
			FinAccountTitleUIModel finAccountTitleUIModel) {
		if (parentAccountTitle != null) {
			finAccountTitleUIModel.setParentAccountTitleId(parentAccountTitle
					.getId());
			finAccountTitleUIModel.setParentAccountTitleName(parentAccountTitle
					.getName());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convRootFinAccountTitleToUI(FinAccountTitle rootAccountTitle,
			FinAccountTitleUIModel finAccountTitleUIModel) {
		if (rootAccountTitle != null) {
			finAccountTitleUIModel.setRootAccountTitleName(rootAccountTitle
					.getName());
			finAccountTitleUIModel.setRootAccountTitleId(rootAccountTitle
					.getId());
		}
	}

	public List<ServiceEntityNode> searchInternal(
			FinAccountTitleSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[finAccountTitle]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(FinAccountTitle.SENAME);
		searchNodeConfig0.setNodeName(FinAccountTitle.NODENAME);
		searchNodeConfig0.setNodeInstID(FinAccountTitle.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[parentAccountTitle]
		BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
		searchNodeConfig2.setSeName(FinAccountTitle.SENAME);
		searchNodeConfig2.setNodeName(FinAccountTitle.NODENAME);
		searchNodeConfig2.setNodeInstID("parentAccountTitle");
		searchNodeConfig2.setStartNodeFlag(false);
		searchNodeConfig2
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig2.setMapBaseFieldName("parentAccountTitleUUID");
		searchNodeConfig2.setMapSourceFieldName("uuid");
		searchNodeConfig2.setBaseNodeInstID(FinAccountTitle.SENAME);
		searchNodeConfigList.add(searchNodeConfig2);
		// Search node:[rootAccountTitle]
		BSearchNodeComConfigure searchNodeConfig4 = new BSearchNodeComConfigure();
		searchNodeConfig4.setSeName(FinAccountTitle.SENAME);
		searchNodeConfig4.setNodeName(FinAccountTitle.NODENAME);
		searchNodeConfig4.setNodeInstID("rootAccountTitle");
		searchNodeConfig4.setStartNodeFlag(false);
		searchNodeConfig4
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig4.setMapBaseFieldName("rootAccountTitleUUID");
		searchNodeConfig4.setMapSourceFieldName("uuid");
		searchNodeConfig4.setBaseNodeInstID(FinAccountTitle.SENAME);
		searchNodeConfigList.add(searchNodeConfig4);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}

	public void createAccTitle(LogonUser logonUser, FinAccountTitle acctTitle,
			String addAccName) throws ServiceEntityConfigureException {

	}

	/**
	 * Add None General Account Title
	 * 
	 * @param logonUser
	 * @param parentAccountTitle
	 *            [Parent AccountTitle reference]
	 * @param subAccountTitleName
	 *            [Sub accountTitle name]
	 * @param debitCredit
	 *            [debit or credit]
	 * @param genType
	 *            [generation type]
	 * @param category
	 * @throws ServiceEntityConfigureException
	 * @throws FinAccountTitleException
	 */
	public void addNonGeneralAccountTitle(FinAccountTitle parentAccountTitle,
			String subAccountTitleName, int finAccountType, int generateType,
			int category, String logonUserUUID, String orgUUID, String client)
			throws ServiceEntityConfigureException, FinAccountTitleException {
		String subAccountTitleId;
		// Judge accountTitle existence
		// subAccountTitleName dosen't equal to parentAccountTitleName
		if (parentAccountTitle != null) {
			// Generate AccountTitle Id
			subAccountTitleId = generateAccountTitleId(
					parentAccountTitle.getId(), subAccountTitleName, client,
					category);
			// Get subAccountTitle
			FinAccountTitle subAccountTitle = getAccountTitle(
					subAccountTitleId, client);
			if (subAccountTitle == null) {
				// IF accountTitle.category is SUBSIDIARY, it will throw
				// Exception
				if (parentAccountTitle.getCategory() != FinAccountTitle.CATEGORY_SUBSIDIARY) {
					// if parent AccountTitle Categaory is Secondary,it can only
					// generate Subsidiary category
					if (parentAccountTitle.getCategory() == FinAccountTitle.CATEGORY_SECONDARY) {
						category = FinAccountTitle.CATEGORY_SUBSIDIARY;
					}
					List<ServiceEntityBindModel> seBindList = new ArrayList<ServiceEntityBindModel>();
					List<ServiceEntityBindModel> seBindListBack = new ArrayList<ServiceEntityBindModel>();
					// create a new accounttitle
					FinAccountTitle accountTitle = (FinAccountTitle) newRootEntityNode(client);
					accountTitle.setName(subAccountTitleName);
					accountTitle.setId(subAccountTitleId);
					accountTitle.setFinAccountType(finAccountType);
					accountTitle.setGenerateType(generateType);
					accountTitle.setCategory(category);
					accountTitle.setParentAccountTitleUUID(parentAccountTitle
							.getUuid());
					accountTitle
							.setRootAccountTitleUUID(accountTitle.getUuid());
					seBindList.add(new ServiceEntityBindModel(accountTitle,
							ServiceEntityBindModel.PROCESSMODE_CREATE));
					// update to persistence
					updateSEBindList(seBindList, seBindListBack, logonUserUUID,
							orgUUID);
				} else {
					// throw creation exception
					throw new FinAccountTitleException(
							FinAccountTitleException.TYPE_SYSTEM_WRONG,
							logonUserUUID);
				}
			} else {
				// Dupicate subAccountTitle Creation Exsitence
				throw new FinAccountTitleException(
						FinAccountTitleException.TYPE_DUPLICATE_TITLE_ID,
						subAccountTitleId);
			}
		} else {
			throw new FinAccountTitleException(
			// No Parent AccountTitle Existence Exception
					FinAccountTitleException.TYPE_NO_PARENT_TITLE);
		}
	}

	/**
	 * Filter out the fin account title online
	 * 
	 * @param allFinTitleList
	 * @param titleUUID
	 * @return
	 */
	public FinAccountTitle getFinAccountTitleOnline(
			List<ServiceEntityNode> allFinTitleList, String titleUUID) {
		if (allFinTitleList == null || allFinTitleList.size() == 0) {
			return null;
		}
		for (ServiceEntityNode seNode : allFinTitleList) {
			if (seNode.getUuid().equals(titleUUID)) {
				return (FinAccountTitle) seNode;
			}
		}
		return null;
	}

	/**
	 * Add General AccountTitle [Hard AcountTitle creation]
	 * 
	 * @param logonUser
	 * @param accountTitleId
	 * @param accountName
	 * @param debitCredit
	 *            [debit or credit]
	 * @param genType
	 *            [data generation type]
	 * @throws ServiceEntityConfigureException
	 * @throws FinAccountTitleException
	 */
	public void addGeneralAccountTitle(String accountTitleId,
			String accountName, int finAccountType, int genType,
			String userUUID, String orgUUID, String client)
			throws ServiceEntityConfigureException, FinAccountTitleException {
		// Check Duplicate AccountTitle
		if (checkDuplicateAccountTitle(accountTitleId, client)) {
			List<ServiceEntityBindModel> seBindList = new ArrayList<ServiceEntityBindModel>();
			List<ServiceEntityBindModel> seBindListBack = new ArrayList<ServiceEntityBindModel>();
			// create a new account-title
			FinAccountTitle accountTitle = (FinAccountTitle) newRootEntityNode(client);
			accountTitle.setName(accountName);
			accountTitle.setId(accountTitleId);
			accountTitle.setFinAccountType(finAccountType);
			accountTitle.setGenerateType(genType);
			accountTitle.setCategory(FinAccountTitle.CATEGORY_GENERAL);
			accountTitle.setRootAccountTitleUUID(accountTitle.getUuid());
			seBindList.add(new ServiceEntityBindModel(accountTitle,
					ServiceEntityBindModel.PROCESSMODE_CREATE));
			// update to persistence
			updateSEBindList(seBindList, seBindListBack, userUUID, orgUUID);
		} else {
			// throw duplicate exception
			throw new FinAccountTitleException(
					FinAccountTitleException.TYPE_DUPLICATE_TITLE_ID,
					accountTitleId);
		}
	}

	public void delNonGneralAccTitle(LogonUser logonUser)
			throws FinAccountTitleException {
		if (!logonUser.getName().equals(LogonUserManager.USERID_SUPER)) {

		} else {
			throw new FinAccountTitleException(
					FinAccountTitleException.TYPE_SYSTEM_WRONG,
					logonUser.getId());
		}
	}

	/**
	 * Delete AccountTitle [Hard AccountTitle Deletion]
	 * 
	 * @param logonUser
	 * @param accountTitle
	 * @throws AccountTitleCreationDeletionException
	 * @throws ServiceEntityConfigureException
	 */
	public void delGeneralAccountTtile(LogonUser logonUser,
			FinAccountTitle accountTitle) throws FinAccountTitleException,
			ServiceEntityConfigureException {
		if (logonUser.getName().equals(LogonUserManager.USERID_SUPER)) {
			List<ServiceEntityBindModel> seBindList = new ArrayList<ServiceEntityBindModel>();
			List<ServiceEntityBindModel> seBindListBack = new ArrayList<ServiceEntityBindModel>();
			seBindList.add(new ServiceEntityBindModel(accountTitle,
					ServiceEntityBindModel.PROCESSMODE_DELETE));
			seBindListBack.add(new ServiceEntityBindModel(accountTitle,
					ServiceEntityBindModel.PROCESSMODE_DELETE));
			accountTitleRegister(seBindList, seBindListBack, accountTitle);
		} else {
			throw new FinAccountTitleException(
					FinAccountTitleException.TYPE_SYSTEM_WRONG);
		}
	}

	/**
	 * Generate AccountTitle Id
	 * 
	 * @param accountTitleId
	 * @param genCategaory
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws FinAccountTitleException
	 */
	public String generateAccountTitleId(String parentAccountTitleId,
			String subAccountTitleName, String client, int genCategaory)
			throws ServiceEntityConfigureException, FinAccountTitleException {
		String generatedAccountId = null;
		// check generation category matching with accountTitle category
		if (checkGenerationIdMatching(parentAccountTitleId, genCategaory,
				client)) {
			FinAccountTitle subAccountTitle = getAccountTitle(
					parentAccountTitleId, client);
			List<ServiceEntityNode> subAccountTitleList;
			int parentAccountTitleCategory;
			String parentAccountTitleUUID;
			// Get accountTitle by parentAccountTitleId
			FinAccountTitle parentAccountTitle = getAccountTitle(
					parentAccountTitleId, client);
			// Get accountTitle category
			parentAccountTitleCategory = parentAccountTitle.getCategory();
			// Get parent accountTitle Id
			parentAccountTitleId = parentAccountTitle.getId();
			// Get parent account UUID
			parentAccountTitleUUID = parentAccountTitle
					.getRootAccountTitleUUID();
			// change parentAccountTitleId to int
			int parentAccountTitleIdInt = Integer.parseInt(parentAccountTitleId
					.trim());
			// Get subAccountTitle List
			subAccountTitleList = getSubAccountTitleList(
					parentAccountTitleUUID, client);
			// accountTitleCategory node Categaroy = 1
			if (parentAccountTitleCategory == FinAccountTitle.CATEGORY_GENERAL) {
				// To judge subAccountTitleList Null
				if (subAccountTitleList.size() > 0) {
					int i = 0;
					// Generate accountTitle Id by generation Categaory type
					if (genCategaory == FinAccountTitle.CATEGORY_SECONDARY) {
						// Initiate subAccountTitleIdArray
						subAccountTitleIdArray = new int[getaccountTileListTotalCount(
								subAccountTitleList, genCategaory, client)];
						// Induce ergodic sub AccountTitle List
						for (ServiceEntityNode accountTileList : subAccountTitleList) {
							FinAccountTitle subAccountTitleObject = getAccountTitle(
									accountTileList.getId(), client);
							// Secondary category filter
							if (subAccountTitleObject.getCategory() == FinAccountTitle.CATEGORY_SECONDARY) {
								// check duplicate insert
								if (!(subAccountTitleObject.getName()
										.equals(subAccountTitleName))) {
									// Get sub accountTitle Object
									subAccountTitle = getAccountTitle(
											accountTileList.getId(), client);
									// Get accountTitle Id
									subAccountTitleIdArray[i] = Integer
											.parseInt(subAccountTitle.getId()
													.trim());
									i++;
								} else {
									// throw duplicate exception
									throw new FinAccountTitleException(
											FinAccountTitleException.TYPE_DUPLICATE_TITLE_ID,
											subAccountTitleName);
								}
							}
						}
						finAccountTitleIdGenerator = new FinAccountTitleIdGenerator();
						// Invoking Id Generator By Secondary
						generatedAccountId = (Integer
								.toString(finAccountTitleIdGenerator
										.accountTitleIdGenerator(
												subAccountTitleIdArray,
												parentAccountTitleIdInt,
												genCategaory)));
					} else if (genCategaory == FinAccountTitle.CATEGORY_SUBSIDIARY) {
						// Initiate subAccountTitleIdArray
						subAccountTitleIdArray = new int[getaccountTileListTotalCount(
								subAccountTitleList, genCategaory, client)];
						// Induce ergodic sub AccountTitle List
						for (ServiceEntityNode accountTileList : subAccountTitleList) {
							FinAccountTitle subAccountTitleObject = getAccountTitle(
									accountTileList.getId(), client);
							// Subsidiary category Filter
							if (subAccountTitleObject.getCategory() == FinAccountTitle.CATEGORY_SUBSIDIARY) {
								// check duplicate insert
								if (!(subAccountTitleObject.getName()
										.equals(subAccountTitleName))) {
									// Get sub accountTitle Object
									subAccountTitle = getAccountTitle(
											accountTileList.getId(), client);
									// Get accountTitle Id
									subAccountTitleIdArray[i] = Integer
											.parseInt(subAccountTitle.getId()
													.trim());
									i++;
								} else {
									// throw duplicate exception
									throw new FinAccountTitleException(
											FinAccountTitleException.TYPE_DUPLICATE_TITLE_ID,
											subAccountTitleName);
								}
							}
						}
						finAccountTitleIdGenerator = new FinAccountTitleIdGenerator();
						// Invoking Id Generator By Secondary
						generatedAccountId = (Integer
								.toString(finAccountTitleIdGenerator
										.accountTitleIdGenerator(
												subAccountTitleIdArray,
												parentAccountTitleIdInt,
												genCategaory)));
					}
				} else {
					// empty subAccontTitleId generation
					if (genCategaory == FinAccountTitle.CATEGORY_SECONDARY) {
						// check duplicate insert
						if (!(subAccountTitle.getName()
								.equals(subAccountTitleName))) {
							String s1;
							int id;
							s1 = subAccountTitle.getId().trim();
							id = Integer.parseInt(s1.trim());
							generatedAccountId = id + "01" + "00";
						} else {
							// throw duplicate exception
							throw new FinAccountTitleException(
									FinAccountTitleException.TYPE_DUPLICATE_TITLE_ID,
									subAccountTitleName);
						}
					} else if (genCategaory == FinAccountTitle.CATEGORY_SUBSIDIARY) {
						if (!(subAccountTitle.getName()
								.equals(subAccountTitleName))) {
							String s1;
							int id;
							s1 = subAccountTitle.getId().trim();
							id = Integer.parseInt(s1.trim());
							generatedAccountId = id + "00" + "01";
						} else {
							// throw duplicate exception
							throw new FinAccountTitleException(
									FinAccountTitleException.TYPE_DUPLICATE_TITLE_ID,
									subAccountTitleName);
						}
					}
				}
				// accountTitleCategory node Categaroy = 2
			} else if (parentAccountTitleCategory == FinAccountTitle.CATEGORY_SECONDARY) {
				// To judge subAccountTitleList Null
				if (subAccountTitleList.size() > 0) {
					int i = 0;
					// Initiate subAccountTitleIdArray
					subAccountTitleIdArray = new int[subAccountTitleList.size()];
					// Induce ergodic sub AccountTitle List
					for (ServiceEntityNode accountTileList : subAccountTitleList) {
						// Get sub accountTitle Object
						subAccountTitle = getAccountTitle(
								accountTileList.getId(), client);
						// check duplicate insert
						if (!(subAccountTitle.getName()
								.equals(subAccountTitleName))) {
							// Get accountTitle Id
							subAccountTitleIdArray[i] = Integer
									.parseInt(subAccountTitle.getId().trim());
							i++;
						} else {
							throw new FinAccountTitleException(
									FinAccountTitleException.TYPE_DUPLICATE_TITLE_ID,
									subAccountTitleName);
						}
					}
					finAccountTitleIdGenerator = new FinAccountTitleIdGenerator();
					// Invoking Id Generator By Secondary
					generatedAccountId = (Integer
							.toString(finAccountTitleIdGenerator
									.accountTitleIdGenerator(
											subAccountTitleIdArray,
											parentAccountTitleIdInt,
											genCategaory)));
				} else {
					// empty subAccontTitleId generation
					// check duplicate insert
					if (!(subAccountTitle.getName().equals(subAccountTitleName))) {
						String s1;
						s1 = subAccountTitle.getId();
						s1 = s1.substring(0, 6);
						generatedAccountId = s1 + "01";
					} else {
						throw new FinAccountTitleException(
								FinAccountTitleException.TYPE_DUPLICATE_TITLE_ID,
								subAccountTitleName);
					}
				}
			}
		} else {
			// No matching generation category Exception
			FinAccountTitle accountTitle = getAccountTitle(
					parentAccountTitleId, client);
			throw new FinAccountTitleException(
					FinAccountTitleException.TYPE_DUPLICATE_TITLE_ID,
					accountTitle.getId());
		}
		return generatedAccountId;
	}

	/**
	 * Get All Sub AccountTitle List by current Account UUID
	 * 
	 * @param parentAccountTitleUUID
	 *            :parent Account UUID
	 * @return subAccountTitleList
	 * @throws ServiceEntityConfigureException
	 */
	protected List<ServiceEntityNode> getSubAccountTitleList(
			String parentAccountTitleUUID, String client)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> subAccountTitleList = (List<ServiceEntityNode>) getEntityNodeListByKey(
				parentAccountTitleUUID,
				FinAccountTitle.FIELD_PARENTACCOUTTITLEUUID,
				FinAccountTitle.NODENAME, client, null);
		return subAccountTitleList;
	}

	/**
	 * Getting parent account title by accountTitleId from persistence
	 * 
	 * @param accountName
	 * @param accountTitleId
	 * @return accountTitle
	 * @throws ServiceEntityConfigureException
	 */
	protected FinAccountTitle getAccountTitle(String accountTitleId,
			String client) throws ServiceEntityConfigureException {
		FinAccountTitle subAccountTitle = (FinAccountTitle) getEntityNodeByKey(
				accountTitleId, IServiceEntityNodeFieldConstant.ID,
				FinAccountTitle.NODENAME, client, null, true);
		return subAccountTitle;
	}

	/**
	 * Get Parent AccountTitle
	 * 
	 * @param parentUUID
	 * @return parentAccountTitle
	 * @throws ServiceEntityConfigureException
	 */
	public FinAccountTitle getParentAccountTitle(String parentUUID,
			String client) throws ServiceEntityConfigureException {
		FinAccountTitle parentAccountTitle = (FinAccountTitle) getEntityNodeByKey(
				parentUUID, FinAccountTitle.FIELD_PARENTNODEUUID,
				FinAccountTitle.NODENAME, client, null);
		return parentAccountTitle;
	}

	/**
	 * Core Logic to generate new FinAccounTitle
	 * 
	 * @return
	 * @throws FinAccountTitleException
	 */
	public FinAccountTitle newAccountTitle(String parentUUID,
			String accountTitleName, String client)
			throws ServiceEntityConfigureException, FinAccountTitleException {
		FinAccountTitle parentAccountTitle = (FinAccountTitle) getEntityNodeByKey(
				parentUUID, FinAccountTitle.FIELD_PARENTNODEUUID,
				FinAccountTitle.NODENAME, client, null);
		FinAccountTitle finAccountTitle = (FinAccountTitle) newRootEntityNode(client);
		if (parentAccountTitle != null) {
			finAccountTitle.setParentAccountTitleUUID(parentUUID);
			finAccountTitle.setFinAccountType(parentAccountTitle
					.getFinAccountType());
			FinAccountTitle rootAccountTitle = (FinAccountTitle) getRootAccountTitle(parentAccountTitle);
			if (rootAccountTitle != null) {
				finAccountTitle.setRootAccountTitleUUID(rootAccountTitle
						.getUuid());
			}
			int category = calculateFinAccountTitleCategory(parentAccountTitle);
			String subAccountTitleId = generateAccountTitleId(
					parentAccountTitle.getId(), accountTitleName, client,
					category);
			finAccountTitle.setCategory(category);
			finAccountTitle.setId(subAccountTitleId);
		} else {
			// In case root FinAccountTitle
			finAccountTitle.setCategory(calculateFinAccountTitleCategory(null));
		}
		return finAccountTitle;
	}

	/**
	 * Core Logic to calculate the category from parent account title instance
	 * 
	 * @param parentAccountTitle
	 * @return
	 */
	public int calculateFinAccountTitleCategory(
			FinAccountTitle parentAccountTitle) {
		if (parentAccountTitle == null) {
			return FinAccountTitle.CATEGORY_GENERAL;
		}
		if (parentAccountTitle.getCategory() == FinAccountTitle.CATEGORY_GENERAL) {
			return FinAccountTitle.CATEGORY_SECONDARY;
		}
		if (parentAccountTitle.getCategory() == FinAccountTitle.CATEGORY_SECONDARY) {
			return FinAccountTitle.CATEGORY_SUBSIDIARY;
		}
		if (parentAccountTitle.getCategory() == FinAccountTitle.CATEGORY_SUBSIDIARY) {
			return FinAccountTitle.CATEGORY_FOURTH;
		}
		if (parentAccountTitle.getCategory() == FinAccountTitle.CATEGORY_FOURTH) {
			return FinAccountTitle.CATEGORY_FIFTH;
		}
		return FinAccountTitle.CATEGORY_SUBSIDIARY;
	}

	/**
	 * Check Generation Id Matching
	 * 
	 * @param accountTitleId
	 * @param generationCategory
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public boolean checkGenerationIdMatching(String accountTitleId,
			int generationCategory, String client)
			throws ServiceEntityConfigureException {
		FinAccountTitle parentAccountTitle = getAccountTitle(accountTitleId,
				client);
		// if generate Secondary,the parent category must be General
		if (generationCategory == FinAccountTitle.CATEGORY_SECONDARY) {
			if (parentAccountTitle.getCategory() == FinAccountTitle.CATEGORY_GENERAL) {
				return true;
			}
			// if generate Subsidiary,the parent category must be Secondary
		} else if (generationCategory == FinAccountTitle.CATEGORY_SUBSIDIARY
				&& parentAccountTitle.getCategory() == FinAccountTitle.CATEGORY_SECONDARY) {
			return true;
		}
		// if generate Subsidiary,the parent category must be General
		else if (generationCategory == FinAccountTitle.CATEGORY_SUBSIDIARY
				&& parentAccountTitle.getCategory() == FinAccountTitle.CATEGORY_GENERAL) {
			return true;
		}
		return false;
	}

	/**
	 * Get AccountTile List Total Count
	 * 
	 * @param subAccountTitleList
	 * @param accountTitlecategory
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public int getaccountTileListTotalCount(
			List<ServiceEntityNode> subAccountTitleList,
			int genAccountTitleCategory, String client)
			throws ServiceEntityConfigureException {
		int count = 0;
		// Induce ergodic sub AccountTitle List
		for (ServiceEntityNode accountTileList : subAccountTitleList) {
			FinAccountTitle accountTitle = getAccountTitle(
					accountTileList.getId(), client);
			if (accountTitle.getCategory() == genAccountTitleCategory) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Check Duplicate AccountTitle
	 * 
	 * @param accountTitleId
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public boolean checkDuplicateAccountTitle(String accountTitleId,
			String client) throws ServiceEntityConfigureException {
		// Get accountTitle by accountTitle Id
		FinAccountTitle accountTitle = (FinAccountTitle) getEntityNodeByKey(
				accountTitleId, IServiceEntityNodeFieldConstant.ID,
				FinAccountTitle.NODENAME_ROOT, client, null);
		if (accountTitle == null) {
			return true;
		}
		return false;
	}

	public List<FinAccountTitle> getSubsidiaryAccountTitle() {
		return null;
	}

	/**
	 * Register AccountTitle
	 * 
	 * @param seList
	 *            [se list]
	 * @param bkList
	 *            [se list back]
	 * @param accTitle
	 *            [accountTitle]
	 * @throws ServiceEntityConfigureException
	 */
	public void accountTitleRegister(List<ServiceEntityBindModel> seList,
			List<ServiceEntityBindModel> bkList, FinAccountTitle accTitle)
			throws ServiceEntityConfigureException {
		updateSEBindList(seList, bkList, accTitle.getUuid(), null);
	}

	/**
	 * Get All Subsidary Node
	 * 
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getAllSubsidiaryNode(String client)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> subsidiaryList = getEntityNodeListByKey(
				FinAccountTitle.CATEGORY_SUBSIDIARY,
				FinAccountTitle.FIELD_CATEGORY, FinAccountTitle.NODENAME_ROOT,
				client, null);
		return subsidiaryList;
	}

	/**
	 * Get AccountTitle Name by Account
	 * 
	 * @param account
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public String getAccountTitleName(FinAccount account)
			throws ServiceEntityConfigureException {
		String accountTitleName;
		FinAccountTitle accountTitle = (FinAccountTitle) getEntityNodeByKey(
				account.getAccountTitleUUID(),
				IServiceEntityNodeFieldConstant.PARENTNODEUUID,
				FinAccountTitle.NODENAME_ROOT, account.getClient(), null);
		accountTitleName = accountTitle.getName();
		return accountTitleName;
	}

	/**
	 * Logic to delete Customerized Finance Account title
	 * 
	 * @param finAccount
	 * @throws ServiceEntityConfigureException
	 * @throws FinAccountTitleException
	 */
	public void deleteFinAccountTitle(String finAccountTitleUUID,
			LogonUser logonUser, String organizationUUID)
			throws ServiceEntityConfigureException, FinAccountTitleException {
		FinAccountTitle finAccountTitle = (FinAccountTitle) getEntityNodeByKey(
				finAccountTitleUUID, IServiceEntityNodeFieldConstant.UUID,
				FinAccountTitle.NODENAME, logonUser.getClient(), null);
		if (finAccountTitle == null) {
			throw new FinAccountTitleException(
					FinAccountTitleException.TYPE_NO_TITLE_EXIST);
		}
		// Check weather this fin Account title is system standard
		if (finAccountTitle.getOriginalType() == FinAccountTitle.ORIGINALTYPE_STANDARD) {
			throw new FinAccountTitleException(
					FinAccountTitleException.TYPE_CANNOT_DEL_SYSSTANDARD);
		}
		// Search weather sub fin Account title exist
		List<ServiceEntityNode> subAccTitleList = this.getEntityNodeListByKey(
				finAccountTitleUUID,
				FinAccountTitle.FIELD_PARENTACCOUTTITLEUUID,
				FinAccountTitle.NODENAME, logonUser.getClient(), null);
		if (subAccTitleList != null && subAccTitleList.size() > 0) {
			throw new FinAccountTitleException(
					FinAccountTitleException.TYPE_CANNOT_DEL_SUB_TITLE);
		}
		// process of deletion
		this.deleteSENode(finAccountTitle, logonUser.getUuid(),
				organizationUUID);
	}

	/**
	 * Logic to get the root Account title by field [rootAccountTitleUUID] if
	 * not success in this way, then get get from recursive retrieve from parent
	 * account title
	 * 
	 * @param finAccountTitle
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public FinAccountTitle getRootAccountTitle(FinAccountTitle finAccountTitle)
			throws ServiceEntityConfigureException {
		FinAccountTitle rootTitle = (FinAccountTitle) this.getEntityNodeByKey(
				finAccountTitle.getRootAccountTitleUUID(),
				IServiceEntityNodeFieldConstant.UUID, FinAccountTitle.NODENAME,
				finAccountTitle.getClient(), null);
		if (rootTitle != null) {
			return rootTitle;
		} else {
			return getRootAccountTitleRecByParent(finAccountTitle);
		}
	}

	/**
	 * [Internal method] get root account title recursive method by keep
	 * retrieving from parent account title
	 * 
	 * @param finAccountTitle
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	protected FinAccountTitle getRootAccountTitleRecByParent(
			FinAccountTitle finAccountTitle)
			throws ServiceEntityConfigureException {
		FinAccountTitle parentTitle = (FinAccountTitle) this
				.getEntityNodeByKey(
						finAccountTitle.getParentAccountTitleUUID(),
						IServiceEntityNodeFieldConstant.UUID,
						FinAccountTitle.NODENAME, finAccountTitle.getClient(),
						null);
		if (parentTitle == null) {
			return finAccountTitle;
		} else {
			return getRootAccountTitleRecByParent(parentTitle);
		}
	}

}
