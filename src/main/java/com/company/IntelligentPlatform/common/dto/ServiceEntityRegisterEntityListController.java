package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
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
import com.company.IntelligentPlatform.common.dto.ServiceEntityRegisterEntitySearchModel;
import com.company.IntelligentPlatform.common.dto.ServiceEntityRegisterEntityUIModel;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityRegisterEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityRegisterException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.ServiceSelect2Model;
import com.company.IntelligentPlatform.common.service.ServiceSelect2ModelProxy;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceEntityRegisterEntity;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "serviceEntityRegisterEntityListController")
@RequestMapping(value = "/serviceEntityRegisterEntity")
public class ServiceEntityRegisterEntityListController extends SEListController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_SYSTEMCONFIG;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected ServiceEntityRegisterEntityManager serviceEntityRegisterEntityManager;

	protected List<ServiceEntityRegisterEntityUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<ServiceEntityRegisterEntityUIModel> serviceEntityRegisterEntityList = new ArrayList<ServiceEntityRegisterEntityUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			ServiceEntityRegisterEntityUIModel serviceEntityRegisterEntityUIModel = new ServiceEntityRegisterEntityUIModel();
			ServiceEntityRegisterEntity serviceEntityRegisterEntity = (ServiceEntityRegisterEntity) rawNode;
			serviceEntityRegisterEntityManager
					.convServiceEntityRegisterEntityToUI(
							serviceEntityRegisterEntity,
							serviceEntityRegisterEntityUIModel);

			serviceEntityRegisterEntityList
					.add(serviceEntityRegisterEntityUIModel);
		}
		return serviceEntityRegisterEntityList;
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
			ServiceEntityRegisterEntitySearchModel serviceEntityRegisterEntitySearchModel = new ServiceEntityRegisterEntitySearchModel();
			List<ServiceEntityNode> rawList = serviceEntityRegisterEntityManager
					.searchInternal(serviceEntityRegisterEntitySearchModel);
//			Collections.sort(rawList,
//					new ServiceEntityNodeLastUpdateTimeCompare());
			List<ServiceEntityRegisterEntityUIModel> serviceEntityRegisterEntityUIModelList = getModuleListCore(rawList);
			String result = ServiceJSONParser
					.genDefOKJSONArray(serviceEntityRegisterEntityUIModelList);
			return result;
		} catch (SearchConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
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
			ServiceEntityRegisterEntitySearchModel serviceEntityRegisterEntitySearchModel = (ServiceEntityRegisterEntitySearchModel) JSONObject
					.toBean(jsonObject,
							ServiceEntityRegisterEntitySearchModel.class);
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = serviceEntityRegisterEntityManager
					.searchInternal(serviceEntityRegisterEntitySearchModel);
//			Collections.sort(rawList,
//					new ServiceEntityNodeLastUpdateTimeCompare());
			List<ServiceEntityRegisterEntityUIModel> serviceEntityRegisterEntityUIModelList = getModuleListCore(rawList);
			String result = ServiceJSONParser
					.genDefOKJSONArray(serviceEntityRegisterEntityUIModelList);
			return result;
		} catch (SearchConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (NodeNotFoundException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/loadEntitySelectList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadEntitySelectList() {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = serviceEntityRegisterEntityManager
					.getEntityNodeListByKey(null, null,
							ServiceEntityRegisterEntity.NODENAME, null);			
			return ServiceJSONParser.genDefOKJSONArray(rawList);
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/loadNodeSelectList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadNodeSelectList(String baseId) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ServiceEntityRegisterEntity baseEntity = (ServiceEntityRegisterEntity) serviceEntityRegisterEntityManager
					.getEntityNodeByKey(baseId, IServiceEntityNodeFieldConstant.ID,
							ServiceEntityRegisterEntity.NODENAME, null, true);
			List<String> nodeNameList = serviceEntityRegisterEntityManager.getNodeNameListReflective(baseEntity.getId());
			List<ServiceSelect2Model> resultList = ServiceSelect2ModelProxy
					.convStringListToSelect2Model(nodeNameList);
			return ServiceJSONParser.genDefOKJSONArray(resultList);
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceEntityRegisterException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}


}
