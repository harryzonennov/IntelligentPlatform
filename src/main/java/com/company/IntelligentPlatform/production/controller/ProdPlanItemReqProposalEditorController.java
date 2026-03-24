package com.company.IntelligentPlatform.production.controller;

import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.production.dto.*;
import com.company.IntelligentPlatform.production.dto.ProdPlanItemReqProposalServiceUIModel;
import com.company.IntelligentPlatform.production.service.*;
import com.company.IntelligentPlatform.production.model.*;
import com.company.IntelligentPlatform.production.model.ProdPlanItemReqProposal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;

@Scope("session")
@Controller(value = "prodPlanItemReqProposalEditorController")
@RequestMapping(value = "/prodPlanItemReqProposal")
public class ProdPlanItemReqProposalEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ProdPlanItemReqProposalServiceUIModelExtension prodPlanItemReqProposalServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ProductionPlanManager productionPlanManager;

	@Autowired
	protected ProdPlanItemReqProposalManager prodPlanItemReqProposalManager;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				ProdPlanItemReqProposalServiceUIModel.class,
				ProdPlanItemReqProposalServiceModel.class, AOID_RESOURCE,
				ProdPlanItemReqProposal.NODENAME,
				ProdPlanItemReqProposal.SENAME, prodPlanItemReqProposalServiceUIModelExtension,
				productionPlanManager
		);
	}
	
	private ProdPlanItemReqProposalServiceUIModel parseToServiceUIModel(
			String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		classMap.put("productionPlanItemUIModelList",
				ProductionPlanItemServiceUIModel.class);
		return (ProdPlanItemReqProposalServiceUIModel) JSONObject
				.toBean(jsonObject,
						ProdPlanItemReqProposalServiceUIModel.class, classMap);
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
				(request1, client) -> prodPlanItemReqProposalManager.getPageHeaderModelList(request1, client));
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		ProdPlanItemReqProposalServiceUIModel prodPlanItemReqProposalServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				prodPlanItemReqProposalServiceUIModel,
				prodPlanItemReqProposalServiceUIModel.getProdPlanItemReqProposalUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/getInProcessDocMatItemList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getInProcessDocMatItemList(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getListMeta(() -> {
			try {
				ProdPlanItemReqProposal prodPlanItemReqProposal = (ProdPlanItemReqProposal) productionPlanManager
						.getEntityNodeByUUID(request.getUuid(),
								ProdPlanItemReqProposal.NODENAME,
								logonActionController.getClient());
				return prodPlanItemReqProposalManager
						.getInprocessDocMatItemList(prodPlanItemReqProposal);
			} catch (MaterialException e) {
				throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
			}
		});
	}

	@RequestMapping(value = "/getAllEndDocMatItemList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getAllEndDocMatItemList(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getListMeta(() -> {
            try {
				ProdPlanItemReqProposal prodPlanItemReqProposal = (ProdPlanItemReqProposal) productionPlanManager
						.getEntityNodeByUUID(request.getUuid(),
								ProdPlanItemReqProposal.NODENAME,
								logonActionController.getClient());
                return prodPlanItemReqProposalManager
                    .getAllEndDocMatItemUIModelList(prodPlanItemReqProposal,
                            logonActionController.getLogonInfo(), false);
            } catch (MaterialException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        });
	}

	@RequestMapping(value = "/getItemStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getItemStatus() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> productionPlanManager.initItemStatusMap(lanCode));
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(ProdPlanItemReqProposal.SENAME, ProdPlanItemReqProposal.NODENAME,
						null, ProductionPlanItem.NODENAME, request.getBaseUUID(), null, null, request, null),
				ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest, productionPlanManager);
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModule(String uuid) {
		return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleEditService(String uuid) {
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, false,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleViewService(String uuid) {
		return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "/getStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getStatus() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> productionPlanManager.initStatusMap(lanCode));
	}

	@RequestMapping(value = "/getDocFlowList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocFlowList(String uuid) {
		return serviceBasicUtilityController.getDocFlowList(getServiceUIModelRequest(), uuid,
				ISystemActionCode.ACID_VIEW);
	}

}
