package com.company.IntelligentPlatform.common.controller;

import java.util.List;


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
import com.company.IntelligentPlatform.common.service.FlowRouterManager;
import com.company.IntelligentPlatform.common.service.FlowRouterServiceModel;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;


@Scope("session")
@Controller(value = "flowRouterListController")
@RequestMapping(value = "/flowRouter")
public class FlowRouterListController extends SEListController {

    public static final String AOID_RESOURCE = IServiceModelConstants.FlowRouter;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected FlowRouterManager flowRouterManager;

    @Autowired
    protected LogonUserManager logonUserManager;

    // TODO-LEGACY: @Autowired

    // TODO-LEGACY:     protected FlowRouterServiceUIModelExtension flowRouterServiceUIModelExtension;
    
    protected List<FlowRouterServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException {
        return serviceBasicUtilityController.convServiceUIModuleList(FlowRouterServiceUIModel.class,
                FlowRouterServiceModel.class, rawList,
                flowRouterManager, null /* TODO-LEGACY: flowRouterServiceUIModelExtension */, logonActionController.getLogonInfo());
    }

    @RequestMapping(value = "/loadLogonUserSelectList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String loadLogonUserSelectList() {
        return serviceBasicUtilityController.getListMeta(() -> {
            try {
                return logonUserManager.getEntityNodeListByKey(null, null, LogonUser.NODENAME, null);
            } catch (ServiceEntityConfigureException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        }, AOID_RESOURCE, ISystemActionCode.ACID_LIST);
    }

    @RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String searchTableService(@RequestBody String request) {
        return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
                this, FlowRouterSearchModel.class, searchContext -> flowRouterManager
                        .getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
    }

    @RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String loadModuleListService() {
        return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
                ServiceEntityStringHelper.EMPTYSTRING,
                FlowRouterSearchModel.class, searchContext -> flowRouterManager
                        .getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
    }

    @RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String searchModuleService(@RequestBody String request) {
        return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
                FlowRouterSearchModel.class, searchContext -> flowRouterManager
                        .getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
    }

}
