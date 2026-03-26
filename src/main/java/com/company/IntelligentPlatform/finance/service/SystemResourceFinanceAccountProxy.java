package com.company.IntelligentPlatform.finance.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.finance.model.FinAccount;
import com.company.IntelligentPlatform.finance.model.FinAccountMaterialItem;
import com.company.IntelligentPlatform.finance.model.FinAccountTitle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.IFinanceControllerResource;
import com.company.IntelligentPlatform.common.service.SpringContextBeanService;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ResFinAccountFieldSetting;
import com.company.IntelligentPlatform.common.model.ResFinAccountProcessCode;
import com.company.IntelligentPlatform.common.model.ResFinAccountSetting;
import com.company.IntelligentPlatform.common.model.SystemResource;

/**
 * Proxy Service class for update document finance information into finance
 * account
 * 
 * @author Zhang,Hang
 * 
 */
@Service
public class SystemResourceFinanceAccountProxy {

	@Autowired
	protected SystemResourceManager systemResourceManager;

	@Autowired
	protected SpringContextBeanService springContextBeanService;

	@Autowired
	protected FinAccountTitleManager finAccountTitleManager;

	@Autowired
	protected FinAccountManager finAccountManager;

	public List<FinAccount> updateFinAccByResourceID(
			ServiceEntityNode document, String resourceID, String baseUUID,
			int processCode, String logonUserUUID, String resOrgUUID,
			String execOrgUUID, String client)
			throws ServiceEntityConfigureException, SystemResourceException,
			FinanceAccountValueProxyException {
		/**
		 * [Step1] Find the system resource by resource ID, if system resource
		 * not existed, then just return
		 */
		SystemResource systemResource = (SystemResource) systemResourceManager
				.getEntityNodeByKey(resourceID,
						IServiceEntityNodeFieldConstant.ID,
						SystemResource.NODENAME, client, null, true);
		if (systemResource == null) {
			return null;
		}
		/**
		 * [Step2] Find the resource finance account setting list, update by
		 * each finance account setting
		 */
		List<ServiceEntityNode> resFinAccSettingList = systemResourceManager
				.getEntityNodeListByKey(systemResource.getUuid(),
						IServiceEntityNodeFieldConstant.ROOTNODEUUID,
						ResFinAccountSetting.NODENAME, client, null);
		if (resFinAccSettingList == null || resFinAccSettingList.size() == 0) {
			return null;
		}
		List<FinAccount> resultList = new ArrayList<FinAccount>();
		for (ServiceEntityNode seNode : resFinAccSettingList) {
			ResFinAccountSetting resFinAccountSetting = (ResFinAccountSetting) seNode;
			List<ServiceEntityNode> finAccList = updateFinAccByAccSet(document,
					systemResource, processCode, resFinAccountSetting,
					baseUUID, logonUserUUID, resOrgUUID, execOrgUUID);
			if (!ServiceCollectionsHelper.checkNullList(finAccList)) {
				for(ServiceEntityNode seNodeFinAcc: finAccList){
					resultList.add((FinAccount)seNodeFinAcc);
				}
			}
		}
		return resultList;
	}

	public List<ServiceEntityNode> updateFinAccByResourceId(
			ServiceEntityNode document, List<ServiceEntityNode> docMatItemList,
			String resourceId, String baseUUID, int processCode,
			String logonUserUUID, String resOrgUUID, String execOrgUUID,
			String client) throws ServiceEntityConfigureException,
			SystemResourceException, FinanceAccountValueProxyException {
		/**
		 * [Step1] Find the system resource by resource ID, if system resource
		 * not existed, then just return
		 */
		SystemResource systemResource = (SystemResource) systemResourceManager
				.getEntityNodeByKey(resourceId,
						IServiceEntityNodeFieldConstant.ID,
						SystemResource.NODENAME, client, null, true);
		if (systemResource == null) {
			return null;
		}
		/**
		 * [Step2] Find the resource finance account setting list, update by
		 * each finance account setting
		 */
		List<ServiceEntityNode> resFinAccSettingList = systemResourceManager
				.getEntityNodeListByKey(systemResource.getUuid(),
						IServiceEntityNodeFieldConstant.ROOTNODEUUID,
						ResFinAccountSetting.NODENAME, client, null);
		if (resFinAccSettingList == null || resFinAccSettingList.size() == 0) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
		for (ServiceEntityNode seNode : resFinAccSettingList) {
			ResFinAccountSetting resFinAccountSetting = (ResFinAccountSetting) seNode;
			List<ServiceEntityNode> finAccList = updateFinAccByAccSet(document, docMatItemList,
					systemResource, processCode, resFinAccountSetting,
					baseUUID, logonUserUUID, resOrgUUID, execOrgUUID);
			if (!ServiceCollectionsHelper.checkNullList(finAccList)) {
				resultList.addAll(finAccList);
			}
		}
		return resultList;
	}

	public List<ServiceEntityNode> updateFinAccByAccSet(ServiceEntityNode document,
			SystemResource systemResource, int processCode,
			ResFinAccountSetting resFinAccountSetting, String baseUUID,
			String logonUserUUID, String resOrgUUID, String execOrgUUID)
			throws SystemResourceException, FinanceAccountValueProxyException {
		try {
			/**
			 * Checking and get resFinAccountFieldSetting list
			 */
			List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
			if (resFinAccountSetting.getSwitchFlag() == ResFinAccountSetting.SWITCH_OFF) {
				return null;
			}
			List<ServiceEntityNode> resFinAccFieldSettingList = systemResourceManager
					.getEntityNodeListByKey(resFinAccountSetting.getUuid(),
							IServiceEntityNodeFieldConstant.PARENTNODEUUID,
							ResFinAccountFieldSetting.NODENAME,
							systemResource.getClient(), null);
			if (resFinAccFieldSettingList == null
					|| resFinAccFieldSettingList.size() == 0) {
				return null;
			}
			List<ServiceEntityNode> resFinAccProcessCodeList = systemResourceManager
					.getEntityNodeListByKey(resFinAccountSetting.getUuid(),
							IServiceEntityNodeFieldConstant.PARENTNODEUUID,
							ResFinAccountProcessCode.NODENAME,
							systemResource.getClient(), null);
			if (resFinAccProcessCodeList == null
					|| resFinAccProcessCodeList.size() == 0) {
				return null;
			}
			boolean hitFlag = false;
			for (ServiceEntityNode seNode : resFinAccProcessCodeList) {
				ResFinAccountProcessCode resFinAccountProcessCode = (ResFinAccountProcessCode) seNode;
				if (resFinAccountProcessCode.getProcessCode() == processCode) {
					hitFlag = true;
					break;
				}
			}
			if (!hitFlag) {
				return null;
			}
			/**
			 * Get the instance of fin object
			 */
			Class<?> controllerClass = Class.forName(systemResource
					.getControllerClassName());
			String controllerClsSimName = controllerClass.getSimpleName();
			IFinanceControllerResource finResController = (IFinanceControllerResource) springContextBeanService
					.getBean(ServiceEntityStringHelper
							.headerToLowerCase(controllerClsSimName));
			ServiceEntityNode finAccObject = getFinAccObjectCore(
					resFinAccountSetting, finResController, baseUUID);
			if (finAccObject == null) {
				// In case no finance object found, then just return
				return null;
			}
			int documentType = finResController.getDocumentType();
			/**
			 * Get the finance account title
			 */
			FinAccountTitle finAccountTitle = (FinAccountTitle) finAccountTitleManager
					.getEntityNodeByKey(resFinAccountSetting.getRefUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							FinAccountTitle.NODENAME, null);
			List<ServiceEntityNode> rawFinAccountList = finAccountManager
					.getRawFinAccountListByDocument(document.getUuid(),
							documentType, finAccountTitle.getClient());
			for (ServiceEntityNode seNode : resFinAccFieldSettingList) {
				ResFinAccountFieldSetting resFinAccountFieldSetting = (ResFinAccountFieldSetting) seNode;
				FinAccount finAccount = updateFinAccByValueProxyUnion(
						resFinAccountFieldSetting, rawFinAccountList,
						finAccObject, finAccountTitle, document, documentType,
						baseUUID, logonUserUUID, resOrgUUID, execOrgUUID);
				resultList.add(finAccount);
			}
			return resultList;
		} catch (ClassNotFoundException e) {
			throw new SystemResourceException(
					SystemResourceException.PARA_WRG_CONTROLLER_CLASS,
					systemResource.getControllerClassName());
		} catch (ServiceEntityConfigureException e) {
			throw new SystemResourceException(
					SystemResourceException.PARA_SYSTEM_WRONG, e.getMessage());
		} catch (FinanceAccountObjectProxyException e) {
			throw new SystemResourceException(
					SystemResourceException.PARA_SYSTEM_WRONG,
					e.getErrorMessage());
		}
	}
	
	public List<ServiceEntityNode> updateFinAccByAccSet(ServiceEntityNode document, List<ServiceEntityNode> docMatItemList,
			SystemResource systemResource, int processCode,
			ResFinAccountSetting resFinAccountSetting, String baseUUID,
			String logonUserUUID, String resOrgUUID, String execOrgUUID)
			throws SystemResourceException, FinanceAccountValueProxyException {
		try {
			/**
			 * Checking and get resFinAccountFieldSetting list
			 */
			List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
			if (resFinAccountSetting.getSwitchFlag() == ResFinAccountSetting.SWITCH_OFF) {
				return null;
			}
			List<ServiceEntityNode> resFinAccFieldSettingList = systemResourceManager
					.getEntityNodeListByKey(resFinAccountSetting.getUuid(),
							IServiceEntityNodeFieldConstant.PARENTNODEUUID,
							ResFinAccountFieldSetting.NODENAME,
							systemResource.getClient(), null);
			if (resFinAccFieldSettingList == null
					|| resFinAccFieldSettingList.size() == 0) {
				return null;
			}
			List<ServiceEntityNode> resFinAccProcessCodeList = systemResourceManager
					.getEntityNodeListByKey(resFinAccountSetting.getUuid(),
							IServiceEntityNodeFieldConstant.PARENTNODEUUID,
							ResFinAccountProcessCode.NODENAME,
							systemResource.getClient(), null);
			if (resFinAccProcessCodeList == null
					|| resFinAccProcessCodeList.size() == 0) {
				return null;
			}
			boolean hitFlag = false;
			for (ServiceEntityNode seNode : resFinAccProcessCodeList) {
				ResFinAccountProcessCode resFinAccountProcessCode = (ResFinAccountProcessCode) seNode;
				if (resFinAccountProcessCode.getProcessCode() == processCode) {
					hitFlag = true;
					break;
				}
			}
			if (!hitFlag) {
				return null;
			}
			/**
			 * Get the instance of fin object
			 */
			Class<?> controllerClass = Class.forName(systemResource
					.getControllerClassName());
			String controllerClsSimName = controllerClass.getSimpleName();
			IFinanceControllerResource finResController = (IFinanceControllerResource) springContextBeanService
					.getBean(ServiceEntityStringHelper
							.headerToLowerCase(controllerClsSimName));
			ServiceEntityNode finAccObject = getFinAccObjectCore(
					resFinAccountSetting, finResController, baseUUID);
			if (finAccObject == null) {
				// In case no finance object found, then just return
				return null;
			}
			int documentType = finResController.getDocumentType();
			/**
			 * Get the finance account title
			 */
			FinAccountTitle finAccountTitle = (FinAccountTitle) finAccountTitleManager
					.getEntityNodeByKey(resFinAccountSetting.getRefUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							FinAccountTitle.NODENAME, null);
			List<ServiceEntityNode> rawFinAccountList = finAccountManager
					.getRawFinAccountListByDocument(document.getUuid(),
							documentType, finAccountTitle.getClient());
			for (ServiceEntityNode seNode : resFinAccFieldSettingList) {
				ResFinAccountFieldSetting resFinAccountFieldSetting = (ResFinAccountFieldSetting) seNode;
				List<ServiceEntityNode> resultFinAccList = updateFinAccByValueProxyUnion(resFinAccountFieldSetting,
						rawFinAccountList, finAccObject, finAccountTitle,
						document, docMatItemList, documentType, baseUUID, logonUserUUID,
						resOrgUUID, execOrgUUID);
				if(!ServiceCollectionsHelper.checkNullList(resultFinAccList)){
					resultList.addAll(resultFinAccList);
				}
			}
			return resultList;
		} catch (ClassNotFoundException e) {
			throw new SystemResourceException(
					SystemResourceException.PARA_WRG_CONTROLLER_CLASS,
					systemResource.getControllerClassName());
		} catch (ServiceEntityConfigureException e) {
			throw new SystemResourceException(
					SystemResourceException.PARA_SYSTEM_WRONG, e.getMessage());
		} catch (FinanceAccountObjectProxyException e) {
			throw new SystemResourceException(
					SystemResourceException.PARA_SYSTEM_WRONG,
					e.getErrorMessage());
		}
	}

	/**
	 * Logic to get the finance account object
	 * 
	 * @param resFinAccountSetting
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws FinanceAccountObjectProxyException
	 */
	public ServiceEntityNode getFinAccObjectCore(
			ResFinAccountSetting resFinAccountSetting,
			IFinanceControllerResource finResController, String baseUUID)
			throws ServiceEntityConfigureException,
			FinanceAccountObjectProxyException {
		if (resFinAccountSetting.getRefFinAccObjectProxyClass() == null
				|| resFinAccountSetting.getRefFinAccObjectProxyClass().equals(
						ServiceEntityStringHelper.EMPTYSTRING)) {
			return finResController.getFinAccObjectByKey(baseUUID,
					resFinAccountSetting.getClient(),
					resFinAccountSetting.getRefFinAccObjectKey());
		}
		try {
			Class<?> financeObjectProxyClass = Class
					.forName(resFinAccountSetting
							.getRefFinAccObjectProxyClass());
			String finAccObjName = financeObjectProxyClass.getSimpleName();
			FinanceAccountObjectProxyManager financeAccountObjectProxy = (FinanceAccountObjectProxyManager) springContextBeanService
					.getBean(ServiceEntityStringHelper
							.headerToLowerCase(finAccObjName));
			return financeAccountObjectProxy.getFinAccObject(baseUUID);
		} catch (ClassNotFoundException e) {
			throw new FinanceAccountObjectProxyException(
					FinanceAccountObjectProxyException.PARA_SYSTEM_WRONG,
					e.getMessage());
		}
	}

	/**
	 * Get Fin Account instance by resource and document information
	 * 
	 * @param settledFieldName
	 * @param resourceID
	 * @param documentUUID
	 * @param documentType
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws SearchConfigureException
	 * @throws NodeNotFoundException
	 * @throws ServiceEntityInstallationException
	 */
	public FinAccount getFinAccountByDocumentFieldName(String fieldName,
			String resourceID, String documentUUID, int documentType,
			String client) throws ServiceEntityConfigureException,
			SearchConfigureException, NodeNotFoundException,
			ServiceEntityInstallationException {
		FinAccountTitle finAccountTitle = getFinAccountTitleByResourceFieldName(
				fieldName, resourceID, client);
		if (finAccountTitle == null) {
			return null;
		}
		FinAccount finAccount = finAccountManager.getAccountByDocTitleUUID(
				documentType, documentUUID, finAccountTitle.getUuid(),
				finAccountTitle.getClient(), null, null);
		return finAccount;
	}

	/**
	 * Get fin account title instance by settledFieldName and resource ID
	 * 
	 * @param fieldName
	 * @param resourceID
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public FinAccountTitle getFinAccountTitleByResourceFieldName(
			String fieldName, String resourceID, String client)
			throws ServiceEntityConfigureException {
		SystemResource systemResource = (SystemResource) systemResourceManager
				.getEntityNodeByKey(resourceID,
						IServiceEntityNodeFieldConstant.ID,
						SystemResource.NODENAME, client, null, true);
		if (systemResource == null) {
			return null;
		}
		/**
		 * [Step2] Find the resource finance account setting list, update by
		 * each finance account setting
		 */
		List<ServiceEntityNode> resFinAccFieldList = systemResourceManager
				.getEntityNodeListByKey(systemResource.getUuid(),
						IServiceEntityNodeFieldConstant.ROOTNODEUUID,
						ResFinAccountFieldSetting.NODENAME,
						systemResource.getClient(), null);
		if (resFinAccFieldList == null || resFinAccFieldList.size() == 0) {
			return null;
		}
		for (ServiceEntityNode seNode : resFinAccFieldList) {
			ResFinAccountFieldSetting resFinAccountFieldSetting = (ResFinAccountFieldSetting) seNode;
			if (fieldName.equals(resFinAccountFieldSetting.getFieldName())) {
				ResFinAccountSetting resFinAccountSetting = (ResFinAccountSetting) systemResourceManager
						.getEntityNodeByKey(
								resFinAccountFieldSetting.getParentNodeUUID(),
								IServiceEntityNodeFieldConstant.UUID,
								ResFinAccountSetting.NODENAME, null);
				FinAccountTitle finAccountTitle = (FinAccountTitle) finAccountTitleManager
						.getEntityNodeByKey(resFinAccountSetting.getRefUUID(),
								IServiceEntityNodeFieldConstant.UUID,
								FinAccountTitle.NODENAME, null);
				return finAccountTitle;
			}
		}
		return null;
	}

	public FinAccount updateFinAccByValueProxyUnion(
			ResFinAccountFieldSetting resFinAccountFieldSetting,
			List<ServiceEntityNode> rawFinAccountList,
			ServiceEntityNode finAccObject, FinAccountTitle finAccountTitle,
			ServiceEntityNode document, int documentType, String baseUUID,
			String logonUserUUID, String resOrgUUID, String execOrgUUID)
			throws SystemResourceException, FinanceAccountValueProxyException {
		List<ServiceEntityNode> resultFinAccList = this
				.updateFinAccByValueProxyUnion(resFinAccountFieldSetting,
						rawFinAccountList, finAccObject, finAccountTitle,
						document, null, documentType, baseUUID, logonUserUUID,
						resOrgUUID, execOrgUUID);
		if (ServiceCollectionsHelper.checkNullList(resultFinAccList)) {
			return null;
		} else {
			return (FinAccount) resultFinAccList.get(0);
		}
	}

	public List<ServiceEntityNode> updateFinAccByValueProxyUnion(
			ResFinAccountFieldSetting resFinAccountFieldSetting,
			List<ServiceEntityNode> rawFinAccountList,
			ServiceEntityNode finAccObject, FinAccountTitle finAccountTitle,
			ServiceEntityNode document,
			List<ServiceEntityNode> rawDocMatItemNodeList, int documentType,
			String baseUUID, String logonUserUUID, String resOrgUUID,
			String execOrgUUID) throws SystemResourceException,
			FinanceAccountValueProxyException {
		try {
			List<ServiceEntityNode> resultFinAccList = new ArrayList<>();
			Class<?> finValueProxyClass = Class
					.forName(resFinAccountFieldSetting
							.getFinAccProxyClassName());
			String finValueProxySimName = finValueProxyClass.getSimpleName();
			IFinanceAccountValueProxy finAccValueProxy = (IFinanceAccountValueProxy) springContextBeanService
					.getBean(ServiceEntityStringHelper
							.headerToLowerCase(finValueProxySimName));
			if (finAccValueProxy == null) {
				throw new SystemResourceException(
						SystemResourceException.PARA2_WRG_CONTROL_INTERFACE,
						resFinAccountFieldSetting.getFinAccProxyClassName(),
						IFinanceAccountValueProxy.class.getSimpleName());
			}
			double curValue = finAccValueProxy.getAccountValue(baseUUID,
					finAccountTitle.getClient())
					* resFinAccountFieldSetting.getWeightFactor();
			if (finAccObject != null) {
				FinAccount finAccount = finAccountManager
						.updateStandardFinAccountByDocument(curValue,
								documentType, document, rawFinAccountList,
								finAccObject, execOrgUUID, resOrgUUID,
								logonUserUUID, finAccountTitle, false, false,
								false, finAccObject.getClient());
				resultFinAccList.add(finAccount);
				List<ServiceEntityNode> rawFinAccMaterialItemList = finAccountManager
						.getEntityNodeListByKey(finAccount.getUuid(),
								IServiceEntityNodeFieldConstant.ROOTNODEUUID,
								FinAccountMaterialItem.NODENAME,
								document.getClient(), null);
				rawFinAccMaterialItemList = updateFinAccMaterialItemList(rawDocMatItemNodeList, finAccount,
						rawFinAccMaterialItemList, documentType, logonUserUUID,
						resOrgUUID, execOrgUUID);
				if(!ServiceCollectionsHelper.checkNullList(rawFinAccMaterialItemList)){
					resultFinAccList.addAll(rawFinAccMaterialItemList);
				}
			}
			return null;
		} catch (ClassNotFoundException e) {
			throw new SystemResourceException(
					SystemResourceException.PARA_WRG_FINACCPROXY_CLASS,
					resFinAccountFieldSetting.getFinAccProxyClassName());
		} catch (SearchConfigureException e) {
			throw new SystemResourceException(
					SystemResourceException.PARA_SYSTEM_WRONG,
					e.getErrorMessage());
		} catch (FinAccountException e) {
			throw new SystemResourceException(
					SystemResourceException.PARA_SYSTEM_WRONG,
					e.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			throw new SystemResourceException(
					SystemResourceException.PARA_SYSTEM_WRONG, e.getMessage());
		} catch (NodeNotFoundException e) {
			throw new SystemResourceException(
					SystemResourceException.PARA_SYSTEM_WRONG, e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			throw new SystemResourceException(
					SystemResourceException.PARA_SYSTEM_WRONG, e.getMessage());
		}
	}

	/**
	 * Core Logic to update fin account material item
	 * 
	 * @param rawDocMatItemNodeList
	 * @param rawFinAccMaterialItemList
	 * @param logonUserUUID
	 * @param resOrgUUID
	 * @param execOrgUUID
	 * @return
	 * @throws SystemResourceException
	 * @throws FinanceAccountValueProxyException
	 */
	private List<ServiceEntityNode> updateFinAccMaterialItemList(
			List<ServiceEntityNode> rawDocMatItemNodeList,
			FinAccount finAccount,
			List<ServiceEntityNode> rawFinAccMaterialItemList,
			int documentType, String logonUserUUID, String resOrgUUID,
			String execOrgUUID) throws SystemResourceException,
			FinanceAccountValueProxyException {
		if (ServiceCollectionsHelper.checkNullList(rawDocMatItemNodeList)) {
			return rawFinAccMaterialItemList;
		}
		List<ServiceEntityNode> resultFinAccMaterialItemList = new ArrayList<>();
		for (ServiceEntityNode rawDocItem : rawDocMatItemNodeList) {
			DocMatItemNode docMatItemNode = (DocMatItemNode) rawDocItem;
			FinAccountMaterialItem finAccountMaterialItem = (FinAccountMaterialItem) ServiceCollectionsHelper
					.filterSENodeOnline(docMatItemNode.getRefFinMatItemUUID(),
							rawFinAccMaterialItemList);
			if (finAccountMaterialItem != null) {
				// In case update
				convertDocItemToFinItem(docMatItemNode, documentType,
						finAccountMaterialItem);
				finAccountManager.updateSENode(finAccountMaterialItem, logonUserUUID, resOrgUUID);
			} else {
				try {
					finAccountMaterialItem = (FinAccountMaterialItem) finAccountManager
							.newEntityNode(finAccount,
									FinAccountMaterialItem.NODENAME);
					convertDocItemToFinItem(docMatItemNode, documentType,
							finAccountMaterialItem);
					finAccountManager.updateSENode(finAccountMaterialItem, logonUserUUID, resOrgUUID);
				} catch (Exception e) {
					// could be skipped
				}
			}
			resultFinAccMaterialItemList.add(finAccountMaterialItem);
		}
		return resultFinAccMaterialItemList;
	}

	/**
	 * [Internal method] convert necessary information from document item node
	 * to fin item node
	 * 
	 * @param finAccountMaterialItem
	 * @param docMatItemNode
	 * @param documentType
	 */
	private void convertDocItemToFinItem(DocMatItemNode docMatItemNode,
			int documentType, FinAccountMaterialItem finAccountMaterialItem) {
		if (docMatItemNode != null && finAccountMaterialItem != null) {
			finAccountMaterialItem.setPrevDocMatItemUUID(docMatItemNode
					.getUuid());
			finAccountMaterialItem.setPrevDocType(documentType);
			finAccountMaterialItem.setCurrencyCode(docMatItemNode
					.getCurrencyCode());
			finAccountMaterialItem.setAmount(docMatItemNode.getAmount());
			finAccountMaterialItem.setRefMaterialSKUUUID(docMatItemNode
					.getRefMaterialSKUUUID());
			finAccountMaterialItem.setRefUnitUUID(docMatItemNode
					.getRefUnitUUID());
			finAccountMaterialItem.setRefUUID(docMatItemNode.getRefUnitUUID());
			finAccountMaterialItem.setUnitPrice(docMatItemNode.getUnitPrice());
			finAccountMaterialItem.setItemPrice(docMatItemNode.getItemPrice());
		}

	}
}
