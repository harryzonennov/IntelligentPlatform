package com.company.IntelligentPlatform.platform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.platform.controller.SEListController;
import com.company.IntelligentPlatform.platform.controller.LogonActionController;
import com.company.IntelligentPlatform.platform.service.ActionCodeManager;
import com.company.IntelligentPlatform.platform.service.LogonInfoException;
import com.company.IntelligentPlatform.platform.service.ServiceJSONParser;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.ActionCode;
import com.company.IntelligentPlatform.platform.model.LogonUser;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;

@Scope("session")
@Controller(value = "actionCodeListController")
@RequestMapping(value = "/actionCode")
public class ActionCodeListController extends SEListController {

	@Autowired
	protected ActionCodeManager actionCodeManager;

	@Autowired
	protected LogonActionController logonActionController;

	
	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		try {
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = actionCodeManager
					.getEntityNodeListByKey(null, null,
							ActionCode.NODENAME, null);
			return ServiceJSONParser.genDefOKJSONArray(rawList);
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		}
	}

}
