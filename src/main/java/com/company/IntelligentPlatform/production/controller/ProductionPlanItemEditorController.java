package com.company.IntelligentPlatform.production.controller;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.production.dto.ProdPlanItemReqProposalServiceUIModel;
import com.company.IntelligentPlatform.production.dto.ProductionOrderUIModel;
import com.company.IntelligentPlatform.production.dto.ProductionPlanItemServiceUIModel;
import com.company.IntelligentPlatform.production.dto.ProductionPlanItemServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProductionPlanItemUIModel;
import com.company.IntelligentPlatform.production.service.*;
import com.company.IntelligentPlatform.production.model.ProductionPlan;
import com.company.IntelligentPlatform.production.model.ProductionPlanItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Scope("session")
@Controller(value = "productionPlanItemEditorController")
@RequestMapping(value = "/productionPlanItem")
public class ProductionPlanItemEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = ProductionPlanEditorController.AOID_RESOURCE;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ProductionPlanManager productionPlanManager;

	@Autowired
	protected ProductionPlanItemManager productionPlanItemManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ProductionPlanItemServiceUIModelExtension productionPlanItemServiceUIModelExtension;

	private ProductionPlanItemServiceUIModel parseToServiceUIModel(
			String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		classMap.put("prodPlanItemReqProposalUIModelList",
				ProdPlanItemReqProposalServiceUIModel.class);
		return (ProductionPlanItemServiceUIModel) JSONObject
				.toBean(jsonObject, ProductionPlanItemServiceUIModel.class,
						classMap);
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
				(request1, client) -> productionPlanItemManager.getPageHeaderModelList(request1, client));
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		ProductionPlanItemServiceUIModel productionPlanItemServiceUIModel = parseToServiceUIModel(request);
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			ProductionPlanItemServiceModel productionPlanItemServiceModel = (ProductionPlanItemServiceModel) productionPlanManager
					.genServiceModuleFromServiceUIModel(
							ProductionPlanItemServiceModel.class,
							ProductionPlanItemServiceUIModel.class,
							productionPlanItemServiceUIModel,
							productionPlanItemServiceUIModelExtension);
			productionPlanItemManager.postUpdateProductionPlanItem(productionPlanItemServiceModel);
			productionPlanManager.updateServiceModuleWithDelete(
					ProductionPlanItemServiceModel.class,
					productionPlanItemServiceModel, logonUser.getUuid(),
					logonActionController.getResOrgUUID());
			// Refresh service model
			productionPlanItemServiceUIModel = refreshLoadServiceUIModel(
					productionPlanItemServiceModel.getProductionPlanItem()
							.getUuid(), ISystemActionCode.ACID_EDIT,
					logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(productionPlanItemServiceUIModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException | MaterialException | DocActionException e) {
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
			ProductionPlan productionPlan = (ProductionPlan) productionPlanManager
					.getEntityNodeByKey(request.getBaseUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							ProductionPlan.NODENAME, logonUser.getClient(),
							null);
			ProductionPlanItem productionPlanItem = (ProductionPlanItem) productionPlanManager
					.newEntityNode(productionPlan, ProductionPlanItem.NODENAME);
			ProductionPlanItemServiceModel productionPlanItemServiceModel = new ProductionPlanItemServiceModel();
			productionPlanItemServiceModel
					.setProductionPlanItem(productionPlanItem);
			ProductionPlanItemServiceUIModel productionPlanItemServiceUIModel = (ProductionPlanItemServiceUIModel) productionPlanManager
					.genServiceUIModuleFromServiceModel(
							ProductionPlanItemServiceUIModel.class,
							ProductionPlanItemServiceModel.class,
							productionPlanItemServiceModel, productionPlanItemServiceUIModelExtension,
							logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(productionPlanItemServiceUIModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/getItemStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getItemStatus() {
		try {
			Map<Integer, String> itemStatusMap = productionPlanManager
					.initItemStatusMap(logonActionController.getLanguageCode());
			return productionPlanManager.getDefaultSelectMap(itemStatusMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}
	}
	
	@RequestMapping(value = "/loadLeanViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadLeanViewService(String uuid) {
		try {
			ProductionPlanItem productionPlanItem = loadDataByCheckAccess(uuid, ISystemActionCode.ACID_VIEW);
			ProductionPlanItemUIModel productionPlanItemUIModel = (ProductionPlanItemUIModel) productionPlanManager
					.genUIModelFromUIModelExtension(
							ProductionOrderUIModel.class,
							productionPlanItemServiceUIModelExtension
									.genUIModelExtensionUnion().get(0),
									productionPlanItem, logonActionController.getLogonInfo(), null);
			return ServiceJSONParser
					.genDefOKJSONObject(productionPlanItemUIModel);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceModuleProxyException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	private ProductionPlanItem loadDataByCheckAccess(String uuid, String acId)
			throws AuthorizationException, ServiceEntityConfigureException, LogonInfoException{
		return (ProductionPlanItem) serviceBasicUtilityController.loadDataByCheckAccess(uuid, productionPlanManager,
						ProductionPlanItem.NODENAME, AOID_RESOURCE, acId, false,
						logonActionController.getLogonInfo().getAuthorizationACUnionList());
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModule(String uuid) {
		try {
			ProductionPlanItem productionPlanItem = loadDataByCheckAccess(uuid, ISystemActionCode.ACID_VIEW);
			return ServiceJSONParser.genDefOKJSONObject(productionPlanItem);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	private ProductionPlanItemServiceUIModel refreshLoadServiceUIModel(
			String uuid, String acId, LogonInfo logonInfo)
			throws ServiceModuleProxyException, ServiceUIModuleProxyException,
			ServiceEntityConfigureException, LogonInfoException, MaterialException, AuthorizationException {
		ProductionPlanItem productionPlanItem = (ProductionPlanItem) productionPlanManager
				.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID,
						ProductionPlanItem.NODENAME, logonInfo.getClient(),
						null);
		return refreshLoadServiceUIModel(productionPlanItem, acId, logonInfo);
	}

	private ProductionPlanItemServiceUIModel refreshLoadServiceUIModel(
			ProductionPlanItem productionPlanItem, String acId, LogonInfo logonInfo)
			throws ServiceModuleProxyException, ServiceUIModuleProxyException,
			ServiceEntityConfigureException, LogonInfoException, MaterialException, AuthorizationException {
		if (!ServiceEntityStringHelper.checkNullString(acId)) {
			boolean checkAuthor = serviceBasicUtilityController
					.checkTargetDataAccess(logonInfo.getLogonUser(),
							productionPlanItem, acId,
							logonInfo.getAuthorizationActionCodeMap());
			if (!checkAuthor) {
				throw new AuthorizationException(
						AuthorizationException.TYPE_NO_AUTHORIZATION);
			}
		}
		ProductionPlanItemServiceModel productionPlanItemServiceModel = (ProductionPlanItemServiceModel) productionPlanManager
				.loadServiceModule(ProductionPlanItemServiceModel.class,
						productionPlanItem);
        try {
            productionPlanItemManager.postUpdateProductionPlanItem(productionPlanItemServiceModel);
        } catch (DocActionException e) {
            throw new RuntimeException(e);
        }
        return (ProductionPlanItemServiceUIModel) productionPlanManager
				.genServiceUIModuleFromServiceModel(
						ProductionPlanItemServiceUIModel.class,
						ProductionPlanItemServiceModel.class,
						productionPlanItemServiceModel,
						productionPlanItemServiceUIModelExtension,
						logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleEditService(String uuid) {
		try {
			ProductionPlanItem productionPlanItem = loadDataByCheckAccess(uuid, ISystemActionCode.ACID_EDIT);
			ProductionPlanItemServiceUIModel productionPlanItemServiceUIModel = refreshLoadServiceUIModel(
					productionPlanItem, ISystemActionCode.ACID_EDIT,
					logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(productionPlanItemServiceUIModel);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LockObjectFailureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException | MaterialException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}
	
	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleViewService(String uuid) {
		try {
			ProductionPlanItem productionPlanItem = loadDataByCheckAccess(uuid, ISystemActionCode.ACID_VIEW);
			ProductionPlanItemServiceUIModel productionPlanItemServiceUIModel = refreshLoadServiceUIModel(
					productionPlanItem, ISystemActionCode.ACID_VIEW,
					logonActionController.getLogonInfo());
			return ServiceJSONParser
					.genDefOKJSONObject(productionPlanItemServiceUIModel);
		} catch (AuthorizationException | LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LockObjectFailureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceModuleProxyException | ServiceUIModuleProxyException | MaterialException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/getStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getStatus() {
		try {
			Map<Integer, String> statusMap = productionPlanManager
					.initStatusMap(logonActionController.getLanguageCode());
			return productionPlanManager.getDefaultSelectMap(statusMap);
		} catch (ServiceEntityInstallationException ex) {
			return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
		}

	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest, productionPlanManager);
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
