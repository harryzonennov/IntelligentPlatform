package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.dto.ServiceEntityLogModelSearchModel;
import com.company.IntelligentPlatform.common.dto.ServiceEntityLogModelUIModel;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceEntityLogModelManager;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNodeLastUpdateTimeCompare;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceEntityLogModel;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "serviceEntityLogModelListController")
@RequestMapping(value = "/serviceEntityLogModel")
public class ServiceEntityLogModelListController extends SEListController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected ServiceEntityLogModelManager serviceEntityLogModelManager;

	@Autowired
	protected LogonUserManager logonUserManager;

	protected List<ServiceEntityLogModelUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<ServiceEntityLogModelUIModel> serviceEntityLogModelList = new ArrayList<ServiceEntityLogModelUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			ServiceEntityLogModelUIModel serviceEntityLogModelUIModel = new ServiceEntityLogModelUIModel();
			ServiceEntityLogModel serviceEntityLogModel = (ServiceEntityLogModel) rawNode;
			serviceEntityLogModelManager.convServiceEntityLogModelToUI(
					serviceEntityLogModel, serviceEntityLogModelUIModel);
			LogonUser logonUser = (LogonUser) logonUserManager
					.getEntityNodeByKey(serviceEntityLogModel.getCreatedBy(),
							"uuid", LogonUser.NODENAME, null);
			serviceEntityLogModelManager.convLogonUserToUI(logonUser,
					serviceEntityLogModelUIModel);
			if (serviceEntityLogModel != null) {
//				BidInvitationOrder bidInvitationOrder = (BidInvitationOrder) bidInvitationOrderManager
//						.getEntityNodeByKey(serviceEntityLogModel.getRefUUID(),
//								IServiceEntityNodeFieldConstant.UUID,
//								BidInvitationOrder.NODENAME, null);
//				serviceEntityLogModelManager.convServiceEntityLogModelToUI(
//						bidInvitationOrder, serviceEntityLogModelUIModel);
			}

			serviceEntityLogModelList.add(serviceEntityLogModelUIModel);
		}
		return serviceEntityLogModelList;
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ServiceEntityLogModelSearchModel serviceEntityLogModelSearchModel = new ServiceEntityLogModelSearchModel();
			List<ServiceEntityNode> rawList = serviceEntityLogModelManager
					.searchInternal(serviceEntityLogModelSearchModel,
							logonUser.getClient());
			Collections.sort(rawList,
					new ServiceEntityNodeLastUpdateTimeCompare());
			List<ServiceEntityLogModelUIModel> serviceEntityLogModelUIModelList = getModuleListCore(rawList);
			String result = ServiceJSONParser
					.genDefOKJSONArray(serviceEntityLogModelUIModelList);
			return result;
		} catch (SearchConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (NodeNotFoundException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		try {
			JSONObject jsonObject = JSONObject.fromObject(request);
			ServiceEntityLogModelSearchModel serviceEntityLogModelSearchModel = (ServiceEntityLogModelSearchModel) JSONObject
					.toBean(jsonObject, ServiceEntityLogModelSearchModel.class);
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = serviceEntityLogModelManager
					.searchInternal(serviceEntityLogModelSearchModel,
							logonUser.getClient());
			Collections.sort(rawList,
					new ServiceEntityNodeLastUpdateTimeCompare());
			List<ServiceEntityLogModelUIModel> serviceEntityLogModelUIModelList = getModuleListCore(rawList);
			String result = ServiceJSONParser
					.genDefOKJSONArray(serviceEntityLogModelUIModelList);
			return result;
		} catch (SearchConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (NodeNotFoundException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	

}
