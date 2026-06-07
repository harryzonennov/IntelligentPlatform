package com.company.IntelligentPlatform.salesDistribution.controller;

import java.util.List;

import com.company.IntelligentPlatform.salesDistribution.dto.SalesContractSearchModel;
import com.company.IntelligentPlatform.salesDistribution.dto.SalesContractServiceUIModel;
import com.company.IntelligentPlatform.salesDistribution.service.SalesContractManager;
import com.company.IntelligentPlatform.salesDistribution.service.SalesContractServiceModel;

import com.company.IntelligentPlatform.salesDistribution.service.SalesContractSpecifier;
import com.company.IntelligentPlatform.salesDistribution.model.SalesContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.platform.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.platform.controller.LogonActionController;
import com.company.IntelligentPlatform.platform.controller.SEListController;
import com.company.IntelligentPlatform.platform.service.DocActionException;
import com.company.IntelligentPlatform.platform.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.platform.model.ISystemActionCode;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "salesContractListController")
@RequestMapping(value = "/salesContract")
public class SalesContractListController extends SEListController {

    public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected SalesContractManager salesContractManager;

    @Autowired
    protected SalesContractSpecifier salesContractSpecifier;

    protected List<SalesContractServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException, DocActionException {
        return serviceBasicUtilityController.convServiceUIModuleList(SalesContractServiceUIModel.class,
                SalesContractServiceModel.class, rawList,
                salesContractManager, SalesContract.SENAME, salesContractSpecifier, logonActionController.getLogonInfo());
    }

    @RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String loadModuleListService() {
        return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
                ServiceEntityStringHelper.EMPTYSTRING,
                SalesContractSearchModel.class, searchContext -> salesContractManager
                        .getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
    }

    @RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String searchModuleService(@RequestBody String request) {
        return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
                SalesContractSearchModel.class, searchContext -> salesContractManager
                        .getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
    }

    @RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String searchTableService(@RequestBody String request) {
        return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
                this, SalesContractSearchModel.class, searchContext -> salesContractManager
                        .getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
    }

}