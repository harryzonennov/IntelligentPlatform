package com.company.IntelligentPlatform.production.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.production.dto.ProdPickingOrderSearchModel;
import com.company.IntelligentPlatform.production.dto.ProdPickingOrderServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProdPickingOrderUIModel;
import com.company.IntelligentPlatform.production.service.ProdPickingOrderManager;
import com.company.IntelligentPlatform.production.model.ProdPickingOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNodeLastUpdateTimeCompare;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "prodReturnOrderListController")
@RequestMapping(value = "/prodReturnOrder")
public class ProdReturnOrderListController extends SEListController {

	public static final String AOID_RESOURCE = ProdPickingOrder.SENAME;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected ProdPickingOrderManager prodPickingOrderManager;
	
	@Autowired
	protected ProdPickingOrderServiceUIModelExtension prodPickingOrderServiceUIModelExtension;

	protected List<ProdPickingOrderUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, ServiceModuleProxyException {
		return serviceBasicUtilityController.convUIModuleList(ProdPickingOrderUIModel.class, rawList,
				prodPickingOrderManager, prodPickingOrderServiceUIModelExtension);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String result = this
					.searchTableCore(
							request,
							logonUser.getClient(),
							new ServiceEntityNodeLastUpdateTimeCompare(),
							ProdPickingOrderSearchModel.class,
							searchModel -> {
								ProdPickingOrderSearchModel prodPickingOrderSearchModel = (ProdPickingOrderSearchModel) searchModel;
								try {
									List<ServiceEntityNode> rawList = prodPickingOrderManager
											.searchProdReturnOrder(
													prodPickingOrderSearchModel,
													logonUser.getClient());
									rawList = serviceBasicUtilityController.sortSEListFilterAccess(rawList, AOID_RESOURCE);
									return rawList;
								} catch (SearchConfigureException | ServiceEntityInstallationException e) {
									throw e;
								} catch (NodeNotFoundException | ServiceEntityConfigureException e) {
									throw new SearchConfigureException(SearchConfigureException.PARA_SYSTEM_ERROR, e.getMessage());
								}
							},
							this::getModuleListCore);

			return result;
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException | IOException | ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch ( ServiceModuleProxyException | SearchConfigureException  e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		} catch (DocActionException e) {
            throw new RuntimeException(e);
        }
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
			ProdPickingOrderSearchModel prodPickingOrderSearchModel = new ProdPickingOrderSearchModel();
			List<ServiceEntityNode> rawList = prodPickingOrderManager
					.searchProdReturnOrder(prodPickingOrderSearchModel,
							logonUser.getClient());
			rawList = serviceBasicUtilityController
					.sortSEListFilterAccess(rawList, AOID_RESOURCE);
			List<ProdPickingOrderUIModel> prodPickingOrderUIModelList = getModuleListCore(rawList);
			return ServiceJSONParser
					.genDefOKJSONArray(prodPickingOrderUIModelList);
		} catch (SearchConfigureException | ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException | NodeNotFoundException | ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		try {
			JSONObject jsonObject = JSONObject.fromObject(request);
			ProdPickingOrderSearchModel prodPickingOrderSearchModel = (ProdPickingOrderSearchModel) JSONObject
					.toBean(jsonObject, ProdPickingOrderSearchModel.class);
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = prodPickingOrderManager
					.searchProdReturnOrder(prodPickingOrderSearchModel,
							logonUser.getClient());
			rawList = serviceBasicUtilityController.sortSEListFilterAccess(rawList, AOID_RESOURCE);
			List<ProdPickingOrderUIModel> prodPickingOrderUIModelList = getModuleListCore(rawList);
			return ServiceJSONParser
					.genDefOKJSONArray(prodPickingOrderUIModelList);
		} catch (SearchConfigureException | ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException | NodeNotFoundException | ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

}
