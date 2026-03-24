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
import com.company.IntelligentPlatform.common.service.ServiceFlowModelManager;
import com.company.IntelligentPlatform.common.service.ServiceFlowModelServiceModel;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;


@Scope("session")
@Controller(value = "serviceFlowModelListController")
@RequestMapping(value = "/serviceFlowModel")
public class ServiceFlowModelListController extends SEListController {

    public static final String AOID_RESOURCE = IServiceModelConstants.ServiceFlowModel;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected ServiceFlowModelManager serviceFlowModelManager;

    @Autowired
    protected ServiceFlowModelServiceUIModelExtension serviceFlowModelServiceUIModelExtension;

    protected List<ServiceFlowModelServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException {
        return serviceBasicUtilityController.convServiceUIModuleList(ServiceFlowModelServiceUIModel.class,
                ServiceFlowModelServiceModel.class, rawList,
                serviceFlowModelManager, serviceFlowModelServiceUIModelExtension, logonActionController.getLogonInfo());
    }

    @RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String searchTableService(@RequestBody String request) {
        return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
                this, ServiceFlowModelSearchModel.class, searchContext -> serviceFlowModelManager
                        .getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
    }

    @RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String loadModuleListService() {
        return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
                ServiceEntityStringHelper.EMPTYSTRING,
                ServiceFlowModelSearchModel.class, searchContext -> serviceFlowModelManager
                        .getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
    }

    @RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String searchModuleService(@RequestBody String request) {
        return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
                ServiceFlowModelSearchModel.class, searchContext -> serviceFlowModelManager
                        .getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
    }

}
