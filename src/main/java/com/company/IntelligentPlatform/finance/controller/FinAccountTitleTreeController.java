package com.company.IntelligentPlatform.finance.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.company.IntelligentPlatform.finance.dto.FinAccountTitleTreeUIModel;
import com.company.IntelligentPlatform.finance.dto.FinAccountTitleUIModel;
import com.company.IntelligentPlatform.finance.service.FinAccountManager;
import com.company.IntelligentPlatform.finance.service.FinAccountTitleManager;
import com.company.IntelligentPlatform.finance.model.FinAccount;
import com.company.IntelligentPlatform.finance.model.FinAccountTitle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.service.AuthorizationManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.IMobileErrorCodeConstant;
import com.company.IntelligentPlatform.common.model.IMobileJSONConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Scope("session")
@Controller(value = "finAccountTitleTreeController")
@RequestMapping(value = "/finAccountTitle")
public class FinAccountTitleTreeController extends SEListController {

	protected final static String UNKNOWN_SYS_ERROR_JSON = "{\""
			+ IMobileJSONConstant.ELE_ERROR_CODE + "\":\""
			+ IMobileErrorCodeConstant.UNKNOWN_SYS_ERROR + "\"}";

	@Autowired
	FinAccountTitleManager finAccountTitleManager;

	@Autowired
	FinAccountManager finAccountManager;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;
	
	@Autowired
	protected AuthorizationManager authorizationManager;
	
	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;
	
	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_FINACCOUNT;


	public List<SEUIComModel> convertAccTitleList(
			List<FinAccountTitleTreeUIModel> finAccTitleUIList) {
		List<SEUIComModel> resultList = new ArrayList<SEUIComModel>();
		for (FinAccountTitleTreeUIModel finAccountTitleTreeUIModel : finAccTitleUIList) {
			resultList.add(finAccountTitleTreeUIModel);
		}
		return resultList;
	}

	public void convFinAccountTitleToTreeUI(FinAccountTitle finAccountTitle,
			FinAccountTitleTreeUIModel finAccountTitleTreeUIModel,
			String client) throws ServiceEntityConfigureException {
		finAccountTitleTreeUIModel.setUuid(finAccountTitle.getUuid());
		finAccountTitleTreeUIModel.setParentAccountTitleUUID(finAccountTitle
				.getParentAccountTitleUUID());
		String nodeUIName = getAccountTitleUIName(finAccountTitle);
		finAccountTitleTreeUIModel.setNodeUIName(nodeUIName);
		finAccountTitleTreeUIModel.setId(finAccountTitle.getId());
		finAccountTitleTreeUIModel.setTitle(nodeUIName);
		finAccountTitleTreeUIModel.setOriginalType(finAccountTitle
				.getOriginalType());
		List<ServiceEntityNode> finAccList = finAccountManager
				.getEntityNodeListByKey(finAccountTitle.getUuid(),
						FinAccount.FIELD_ACCTITLE_UUID, FinAccount.NODENAME, client,
						 null);
		if (finAccList == null || finAccList.size() == 0) {
			finAccountTitleTreeUIModel
					.setContainsDataFlag(FinAccountTitleUIModel.CONTAINS_DATA_FLAG_NO);
		} else {
			finAccountTitleTreeUIModel
					.setContainsDataFlag(FinAccountTitleUIModel.CONTAINS_DATA_FLAG_YES);
		}
	}

	/**
	 * public logic to generate Account title UI name
	 * 
	 * @param finAccountTitle
	 * @return
	 */
	public String getAccountTitleUIName(FinAccountTitle finAccountTitle) {
		if (finAccountTitle != null) {
			String uiName = finAccountTitle.getId() + "-"
					+ finAccountTitle.getName();
			return uiName;
		}
		return null;
	}

}
