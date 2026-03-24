package com.company.IntelligentPlatform.common.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.ServiceJSONDataHelper;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.ServiceMessageHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityBindModel;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.IServiceJSONBasicErrorCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Basic ServiceEntity Editor Controller class
 * 
 * @author Zhang,Hang
 * 
 * @date Dec 16, 2012
 */
public class SEEditorController extends SEBasicController {

	protected int processMode;

	//TODO deprecated
	protected List<ServiceEntityBindModel> seBindList = new ArrayList<>();

	//TODO deprecated
	protected List<ServiceEntityBindModel> seBindListBack = new ArrayList<>();

	@Autowired
	LockObjectManager lockObjectManager;

	@Autowired
	LogonInfoManager logonInfoManager;

	@Autowired
	ServiceMessageHelper serviceMessageHelper;
	
	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	/**
	 * Get SE node instance from Controller buffer by nodeName & baseUUID, if
	 * can not find any SE node instance, then return null
	 * 
	 * @param nodeName
	 * @param baseUUID
	 * @return
	 */
	@Deprecated
	public ServiceEntityNode getServiceEntityNodeFromBuffer(String nodeName,
			String baseUUID) {
		if (seBindList == null) {
			System.out.println("empty list");
			return null;
		} else {
			System.out.println(seBindList.size());
		}
		for (ServiceEntityBindModel seBind : seBindList) {
			if (baseUUID.equals(seBind.getBaseUUID())
					&& seBind.getSeNode().getNodeName().equals(nodeName)) {
				return seBind.getSeNode();
			}
		}
		return null;
	}


	/**
	 * Get SE node List from Controller buffer by nodeName & baseUUID, if can
	 * not find any SE node instance, then return null
	 * 
	 * @param nodeName
	 * @param baseUUID
	 * @return
	 */
	@Deprecated
	public List<ServiceEntityNode> getSEListNodeFromBuffer(String nodeName,
			String baseUUID) {
		List<ServiceEntityNode> seList = new ArrayList<>();
		if (seBindList == null) {
			System.out.println("empty list: for Node:[" + nodeName
					+ "] baseUUID:[" + baseUUID + "]");
		} else {
			System.out.println(seBindList.size());
		}
		if (this.seBindList == null || this.seBindList.size() == 0) {
			return null;
		}
		for (ServiceEntityBindModel seBind : seBindList) {
			if (baseUUID.equals(seBind.getBaseUUID())
					&& seBind.getSeNode().getNodeName().equals(nodeName)) {
				seList.add(seBind.getSeNode());
			}
		}
		return seList;
	}



	/**
	 * save the data from buffer to persistency
	 * 
	 * @param baseUUID
	 *            : uuid to indicate the current process instance, in order to
	 *            avoid the impact the data from other session
	 * @throws ServiceEntityConfigureException
	 * @throws LogonInfoException
	 */
	@Deprecated
	public void save(String baseUUID, ServiceEntityManager seManager, LogonUser logonUser, Organization organization)
			throws ServiceEntityConfigureException, LogonInfoException {
		List<ServiceEntityBindModel> tmpSEBindList = new ArrayList<ServiceEntityBindModel>();
		List<ServiceEntityBindModel> tmpSEBindBakList = new ArrayList<ServiceEntityBindModel>();
		if (seBindList != null && seBindList.size() > 0) {
			for (ServiceEntityBindModel seBind : this.seBindList) {
				if (baseUUID.equals(seBind.getBaseUUID())) {
					tmpSEBindList.add(seBind);
				}
			}
		}
		if (seBindListBack != null && seBindListBack.size() > 0) {
			for (ServiceEntityBindModel seBind : this.seBindListBack) {
				if (baseUUID.equals(seBind.getBaseUUID())) {
					tmpSEBindBakList.add(seBind);
				}
			}
		}
		// If it is the new module, after [save action] process mode should
		// convert into [update] module and add locks to these edit objects
		if (seBindListBack == null || seBindListBack.size() == 0) {
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			for (ServiceEntityBindModel seBind : tmpSEBindList) {
				lockSEList.add(seBind.getSeNode());
			}
			try {
				lockObjectManager.lockServiceEntityList(lockSEList, logonUser, organization);
			} catch (LockObjectFailureException e) {
				// this could not happen
				e.printStackTrace();
			}
		}
		// get logon information
		if (logonUser == null) {
			throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
		}
		String orgUUID = ServiceEntityStringHelper.EMPTYSTRING;
		if (organization != null) {
			orgUUID = organization.getUuid();
		}
		seManager.updateSEBindList(tmpSEBindList, tmpSEBindBakList,
				logonUser.getUuid(), orgUUID);

	}

    @Deprecated
	public void clearBuffer(String baseUUID) {
		List<ServiceEntityBindModel> deleteSEList = new ArrayList<ServiceEntityBindModel>();
		List<ServiceEntityBindModel> deleteSEBackList = new ArrayList<ServiceEntityBindModel>();
		if (seBindList != null && seBindList.size() > 0) {
			for (ServiceEntityBindModel seBind : this.seBindList) {
				if (baseUUID.equals(seBind.getBaseUUID())) {
					deleteSEList.add(seBind);
				}
			}
		}
		if (seBindListBack != null && seBindListBack.size() > 0) {
			for (ServiceEntityBindModel seBind : this.seBindListBack) {
				if (baseUUID.equals(seBind.getBaseUUID())) {
					deleteSEBackList.add(seBind);
				}
			}
		}
		// Do the deletion from buffer
		if (deleteSEList.size() > 0) {
			for (ServiceEntityBindModel seBind : deleteSEList) {
				seBindList.remove(seBind);
			}
		}
		if (seBindListBack.size() > 0) {
			for (ServiceEntityBindModel seBind : deleteSEBackList) {
				seBindListBack.remove(seBind);
			}
		}
	}


	/**
	 * Core method of check duplicate ID from back end and return the standard
	 * JSON response, Checking the duplicate ID including the checking the null
	 * ID
	 * 
	 * @param simpleRequest
	 * @param seManager
	 * @return
	 */
	public String checkDuplicateIDCore(SimpleSEJSONRequest simpleRequest,
			ServiceEntityManager seManager) {
		try {
			String id = simpleRequest.getId();
			String uuid = simpleRequest.getUuid();
			String nodeName = simpleRequest.getNodeName();
			String client = simpleRequest.getClient();
			if (id == null || id.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
				String msg = serviceMessageHelper
						.getMessage(IServiceJSONBasicErrorCode.NULL_ID);
				String content = serviceMessageHelper.genSimpleRespone(msg,
						IServiceJSONBasicErrorCode.NULL_ID);
				return content;
			}
			if (nodeName == null
					|| nodeName.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
				nodeName = ServiceEntityNode.NODENAME_ROOT;
			}
			boolean duplicateResult = seManager.checkIDDuplicate(uuid, id,
					nodeName, client);
			if (duplicateResult) {
				String msg = serviceMessageHelper
						.getMessage(IServiceJSONBasicErrorCode.DUPLICATE_ID);
				String content = serviceMessageHelper.genSimpleRespone(msg,
						IServiceJSONBasicErrorCode.DUPLICATE_ID);
				return content;
			} else {
				String msg = serviceMessageHelper
						.getMessage(IServiceJSONBasicErrorCode.DEF_OK);
				String content = serviceMessageHelper.genSimpleDoneRespone(msg);
				return content;
			}
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (IOException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	/**
	 * Public API for JSON exit editor page process core
	 * 
	 * @param serviceExitLockJSONModule
	 * @return
	 */
	@Deprecated
	public String exitEditorCore(SimpleSEJSONRequest serviceExitLockJSONModule) {
		try {
			String baseUUID = serviceExitLockJSONModule.getUuid();
			List<ServiceEntityNode> lockSEList = new ArrayList<>();
			List<ServiceEntityNode> seLockList = null;
			if (seLockList != null && seLockList.size() > 0) {
				lockSEList.addAll(seLockList);
				lockObjectManager.unLockServiceEntityList(lockSEList);
			}
			this.clearBuffer(baseUUID);
			return ServiceJSONDataHelper.genSimpleSEResponse(
					IServiceJSONBasicErrorCode.DEF_OK, null);
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONDataHelper.genSimpleSEResponse(
					IServiceJSONBasicErrorCode.UNKNOWN_SYS_ERROR,
					e.getMessage());
		}
	}
	

}
