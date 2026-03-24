package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.service.WasteProcessOrderManager;
import com.company.IntelligentPlatform.logistics.service.WasteProcessOrderServiceModel;
import com.company.IntelligentPlatform.logistics.service.WasteProcessOrderSpecifier;
import com.company.IntelligentPlatform.logistics.model.WasteProcessOrder;
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
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.List;

@Scope("session")
@Controller(value = "wasteProcessOrderListController")
@RequestMapping(value = "/wasteProcessOrder")
public class WasteProcessOrderListController extends SEListController {

    public static final String AOID_RESOURCE = IServiceModelConstants.WasteProcessOrder;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected WasteProcessOrderManager wasteProcessOrderManager;

    @Autowired
    protected WasteProcessOrderSpecifier wasteProcessOrderSpecifier;

    @Autowired
    protected WasteProcessOrderServiceUIModelExtension wasteProcessOrderServiceUIModelExtension;

    protected List<WasteProcessOrderServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException, DocActionException {
        return serviceBasicUtilityController.convServiceUIModuleList(WasteProcessOrderServiceUIModel.class,
                WasteProcessOrderServiceModel.class, rawList,
                wasteProcessOrderManager, WasteProcessOrder.SENAME, wasteProcessOrderSpecifier, logonActionController.getLogonInfo());
    }

    @RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String loadModuleListService() {
        return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
                ServiceEntityStringHelper.EMPTYSTRING,
                WasteProcessOrderSearchModel.class, searchContext -> wasteProcessOrderManager
                        .getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
    }

    @RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String searchModuleService(@RequestBody String request) {
        return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
                WasteProcessOrderSearchModel.class, searchContext -> wasteProcessOrderManager
                        .getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
    }

    @RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String searchTableService(@RequestBody String request) {
        return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
                this, WasteProcessOrderSearchModel.class, searchContext -> wasteProcessOrderManager
                        .getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
    }
}
