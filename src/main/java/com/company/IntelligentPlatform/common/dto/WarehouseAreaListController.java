package com.company.IntelligentPlatform.common.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.controller.SEListController;

@Scope("session")
@Controller(value = "warehouseAreaListController")
@RequestMapping(value = "/warehouseArea")
public class WarehouseAreaListController extends SEListController {

    @Autowired
    protected WarehouseManager warehouseManager;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    public static final String AOID_RESOURCE = WarehouseEditorController.AOID_RESOURCE;

    /**
     * Used for Warehouse Area Selection
     * @param request
     * @return
     **/
    @RequestMapping(value = "/searchLeanModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String searchLeanModuleService(@RequestBody String request) {
        return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
                WarehouseAreaSearchModel.class, searchContext -> warehouseManager
                        .getSearchProxy().searchItemList(searchContext),  null);
    }

}