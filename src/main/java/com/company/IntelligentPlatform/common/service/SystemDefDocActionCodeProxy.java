package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.DocumentContent;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SystemDefDocActionCodeProxy {
	
    public static final int DOC_ACTION_UPDATE = 1;
	
    public static final int DOC_ACTION_APPROVE = 2;
    
    public static final int DOC_ACTION_COUNTAPPROVE = 3;

	public static final int DOC_ACTION_DELIVERY_DONE = 5;

	public static final int DOC_ACTION_PROCESS_DONE = 6;

	public static final int DOC_ACTION_ACTIVE = DocumentContent.STATUS_ACTIVE;

	public static final int DOC_ACTION_INPROCESS = 310;

	public static final int DOC_ACTION_REINIT = 350;

	public static final int DOC_ACTION_DRY_RUN = 810;

	public static final int DOC_ACTION_SUBMIT = DocumentContent.STATUS_SUBMITTED;

	public static final int DOC_ACTION_REVOKE_SUBMIT = DocumentContent.STATUS_REVOKE_SUBMIT;

	public static final int DOC_ACTION_REJECT_APPROVE = DocumentContent.STATUS_REJECT_APPROVAL;

	public static final int DOC_ACTION_ARCHIVE = DocumentContent.STATUS_ARCHIVED;

	public static final int DOC_ACTION_CANCEL = DocumentContent.STATUS_CANCELED;

	public static final int DOC_ACTION_DELETE = DocumentContent.STATUS_DELETED;

	public static final String NODEINST_ACTION_UPDATE = "updatedBy";

	public static final String NODEINST_ACTION_APPROVE = "approvedBy";

	public static final String NODEINST_ACTION_COUNTAPPROVE  = "countApprovedBy";

	public static final String NODEINST_ACTION_SUBMIT = "submittedBy";

	public static final String NODEINST_ACTION_INPROCESS = "inProcessBy";

	public static final String NODEINST_ACTION_ACTIVE = "active";

	public static final String NODEINST_ACTION_REINIT = "reInit";

	public static final String NODEINST_ACTION_REVOKE_SUBMIT = "revokeSubmittedBy";

	public static final String NODEINST_ACTION_REJECT_APPROVE = "rejectApprovedBy";

	public static final String NODEINST_ACTION_ARCHIVE = "archivedBy";

	public static final String NODEINST_ACTION_CANCEL = "canceledBy";

	public static final String NODEINST_ACTION_DELIVERY_DONE  = "deliveryDoneBy";

	public static final String NODEINST_ACTION_PROCESS_DONE = "processDoneBy";

	public static final String NODEINST_ACTION_DELETE = "deleteBy";
    
    public static final String PROPERTIES_RESOURCE = "SystemDefDocActionCode";
	
	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected SystemDefExtServiceActionCode systemDefExtServiceActionCode;
    
    protected Map<String, Map<Integer, String>> actionCodeMapLan = new HashMap<>();

	protected Map<String, Map<String, String>> extActionCodeMapLan = new HashMap<>();

	/**
	 * Generated custom action code map by overwrite standard action code properties
	 * @param languageCode
	 * @param customPath
	 * @param customPropertyFile
	 * @return
	 * @throws ServiceEntityInstallationException
	 */
	public Map<Integer, String> getActionCodeMapCustom(String languageCode, String customPath, String customPropertyFile)
			throws ServiceEntityInstallationException {
		return getActionCodeMapCustom(languageCode, customPath, customPropertyFile,
				ServiceCollectionsHelper.asList(SystemDefDocActionCodeProxy.DOC_ACTION_ACTIVE));
	}

	/**
	 * Generated custom action code map by overwrite standard action code properties
	 * @param languageCode
	 * @param customPath
	 * @param customPropertyFile
	 * @return
	 * @throws ServiceEntityInstallationException
	 */
	public Map<Integer, String> getActionCodeMapCustom(String languageCode, String customPath,
													   String customPropertyFile, List<Integer> excludeKeyList)
			throws ServiceEntityInstallationException {
		try {
			Map<Integer, String> actionCodeMap = this.getFullActionCodeMap(languageCode);
			if(ServiceEntityStringHelper.checkNullString(customPath) || ServiceEntityStringHelper.checkNullString(customPropertyFile)){
				return actionCodeMap;
			}
			Map<Integer, String> customActionCodeMap = serviceDropdownListHelper
					.getDropDownMap(customPath + customPropertyFile, languageCode);
			if(customActionCodeMap == null){
				return actionCodeMap;
			}
			ServiceCollectionsHelper.mergeIntMap(actionCodeMap,
					customActionCodeMap,
					true);
			if( ServiceCollectionsHelper.checkNullList(excludeKeyList)){
				return actionCodeMap;
			}
			for(Integer key: excludeKeyList){
                actionCodeMap.remove(key);
			}
			return actionCodeMap;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Generated custom action code map by overwrite standard action code properties
	 * @param languageCode
	 * @param excludeKeyList
	 * @return
	 * @throws ServiceEntityInstallationException
	 */
	public Map<Integer, String> getActionCodeMapCustom(String languageCode, List<Integer> excludeKeyList)
			throws ServiceEntityInstallationException {
		Map<Integer, String> actionCodeMap = this.getFullActionCodeMap(languageCode);
		if( ServiceCollectionsHelper.checkNullList(excludeKeyList)){
			return actionCodeMap;
		}
		for(Integer key: excludeKeyList){
            actionCodeMap.remove(key);
		}
		return actionCodeMap;
	}

	/**
	 * Provide standard document action code map
	 * @param languageCode
	 * @return
	 * @throws ServiceEntityInstallationException
	 */
	public Map<Integer, String> getActionCodeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return getActionCodeMapCustom(languageCode, null);
	}

	public Map<Integer, String> getFullActionCodeMap(String languageCode)
			throws ServiceEntityInstallationException {	
		if(this.actionCodeMapLan == null){
			this.actionCodeMapLan = new HashMap<>();
		}
		return ServiceLanHelper.initDefaultLanguageMap(languageCode, this.actionCodeMapLan, lanCode->{
			try {
				String path = this.getClass().getResource("").getPath();
				Map<Integer, String> tempActionCodeMap = serviceDropdownListHelper
						.getDropDownMap(path + PROPERTIES_RESOURCE, languageCode);
				return tempActionCodeMap;
			} catch (IOException e) {
				return null;
			}
		});
	}

	public Map<Integer, String> getActionCodeMergeWithPrivateMap(String languageCode, String filePath, Map<String,
			Map<Integer, String>> privateActionCodeMapLan)
			throws ServiceEntityInstallationException {
		Map<Integer, String> commActionCodeMap = getActionCodeMap(languageCode);
		Map<Integer, String> privateActionCodeMap = getPriviateActionCodeMap(languageCode, filePath,
				privateActionCodeMapLan);
		ServiceCollectionsHelper.mergeIntMap(commActionCodeMap, privateActionCodeMap, true);
		return commActionCodeMap;
	}

	private Map<Integer, String> getPriviateActionCodeMap(String languageCode,
														  String filePath, Map<String,
														  Map<Integer, String>> privateActionCodeMapLan)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefaultLanguageMap(languageCode, privateActionCodeMapLan, lanCode->{
			try {
				String path = this.getClass().getResource("").getPath();
				Map<Integer, String> tempActionCodeMap = serviceDropdownListHelper
						.getDropDownMap(filePath, languageCode);
				return tempActionCodeMap;
			} catch (IOException e) {
				return null;
			}
		});
	}

	/**
	 * Get Action Code Map as well as extended action code map
	 * @param languageCode
	 * @return
	 * @throws ServiceEntityInstallationException
	 */
	public Map<String, String> getExtActionCodeMap(String languageCode)
			throws ServiceEntityInstallationException {
		if(this.extActionCodeMapLan == null){
			this.extActionCodeMapLan = new HashMap<>();
		}
		return ServiceLanHelper.initDefaultLanguageStrMap(languageCode, this.extActionCodeMapLan, lanCode->{
			try {
				String path = this.getClass().getResource("").getPath();
				Map<String, String> tempActionCodeMap = ServiceDropdownListHelper
						.getStrStaticDropDownMap(path + PROPERTIES_RESOURCE, languageCode);
				Map<String, String> extActionCodeMap = systemDefExtServiceActionCode.getActionCodeMap(languageCode);
				ServiceCollectionsHelper.mergeMap(tempActionCodeMap, extActionCodeMap);
				return tempActionCodeMap;
			} catch (IOException | ServiceEntityInstallationException e) {
				return null;
			}
		});
	}

}
