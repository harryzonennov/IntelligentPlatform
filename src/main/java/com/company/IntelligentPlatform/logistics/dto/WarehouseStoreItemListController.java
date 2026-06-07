package com.company.IntelligentPlatform.logistics.dto;

import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.logistics.dto.InventoryTransferItemSearchModel;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryWarehouseItemManager;
import com.company.IntelligentPlatform.logistics.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.platform.service.MaterialException;
import com.company.IntelligentPlatform.platform.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.platform.controller.LogonActionController;
import com.company.IntelligentPlatform.platform.controller.SEListController;
import com.company.IntelligentPlatform.platform.service.*;
import com.company.IntelligentPlatform.platform.service.DocActionException;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.ServiceJSONParser;
import com.company.IntelligentPlatform.platform.service.BsearchService;
import com.company.IntelligentPlatform.platform.service.SearchConfigureException;
import com.company.IntelligentPlatform.platform.service.SearchContext;
import com.company.IntelligentPlatform.platform.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.platform.model.ISystemActionCode;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "warehouseStoreItemListController")
@RequestMapping(value = "/warehouseStoreItem")
public class WarehouseStoreItemListController extends SEListController {

    public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_WAREHOUSESTORE;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;
    
    @Autowired
    protected WarehouseStoreManager warehouseStoreManager;

    @Autowired
    protected WarehouseStoreSpecifier warehouseStoreSpecifier;

    @Autowired
    protected WarehouseStoreItemManager warehouseStoreItemManager;

    @Autowired
    protected WarehouseStoreItemExcelHelper warehouseStoreItemExcelHelper;

    @Autowired
    protected OutboundDeliveryWarehouseItemManager outboundDeliveryWarehouseItemManager;

    @Autowired
    protected WarehouseStoreItemServiceUIModelExtension warehouseStoreItemServiceUIModelExtension;

    @RequestMapping(value = "/getBatchModeMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getBatchModeMap() {
        try {
            Map<Integer, String> batchModeMap = warehouseStoreManager.getBatchModeMap();
            return warehouseStoreManager.getDefaultSelectMap(batchModeMap, false);
        } catch (ServiceEntityInstallationException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/downloadExcel")
    public ResponseEntity<byte[]> downloadExcel(@RequestBody String request) throws ServiceComExecuteException,
            ServiceEntityExceptionContainer {
        ServiceBasicUtilityController.ExcelDownloadRequest excelDownloadRequest =
                new ServiceBasicUtilityController.ExcelDownloadRequest(InventoryTransferItemSearchModel.class,
                        request,
                        AOID_RESOURCE, ISystemActionCode.ACID_EXCEL, IServiceModelConstants.WarehouseStoreItem,
                        warehouseStoreItemExcelHelper, searchContext -> warehouseStoreManager.getSearchProxy().searchItemList(searchContext),
                        null, warehouseStoreItemServiceUIModelExtension);
        return serviceBasicUtilityController.defaultDownloadExcelTemplate(excelDownloadRequest);
    }

    @RequestMapping(value = "/searchAvailableStoreService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String searchAvailableStoreService(@RequestBody String request) {
        return serviceBasicUtilityController.searchModuleTemplate(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
                request,
                WarehouseStoreItemSearchModel.class, searchContext -> {
                    WarehouseStoreItemSearchModel warehouseStoreItemSearchModel = (WarehouseStoreItemSearchModel) searchContext.getSearchModel();
                    try {
                        List<ServiceEntityNode> rawList = warehouseStoreManager.searchStoreItemInternal(warehouseStoreItemSearchModel, logonActionController.getLogonInfo());
                        return BsearchService.genSearchResponse(rawList, 0);
                    } catch (MaterialException e) {
                        throw new SearchConfigureException(SearchConfigureException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                    }
                }, this::getServiceModuleListCore);
    }

    @RequestMapping(value = "/getAvailableStoreItemList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getAvailableStoreItemList(String uuid, int reservedDocType) {
        return serviceBasicUtilityController.getListMeta(() -> {
            try {
                return  outboundDeliveryWarehouseItemManager.getAvailableStoreItemList(uuid,reservedDocType,
                        logonActionController.getLogonInfo());
            } catch (ServiceModuleProxyException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        },  AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
    }

    protected List<WarehouseStoreItemUIModel> getServiceModuleListCore(SearchContext searchContext) throws ServiceEntityConfigureException {
        WarehouseStoreItemSearchModel warehouseStoreItemSearchModel = (WarehouseStoreItemSearchModel) searchContext.getSearchModel();
        return warehouseStoreItemManager.getStoreModuleListCore(searchContext.getRawSEList(), logonActionController.getLogonInfo(),
                warehouseStoreItemSearchModel.getBatchMode());
    }

    @RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String loadModuleListService() {
        return serviceBasicUtilityController.searchModuleTemplate(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
                ServiceEntityStringHelper.EMPTYSTRING,
                WarehouseStoreItemSearchModel.class, searchContext -> warehouseStoreManager
                        .getSearchProxy().searchItemList(searchContext), this::getServiceModuleListCore);
    }

    @RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String searchModuleService(@RequestBody String request) {
        return serviceBasicUtilityController.searchModuleTemplate(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
                request,
                WarehouseStoreItemSearchModel.class, searchContext -> warehouseStoreManager
                        .getSearchProxy().searchItemList(searchContext), this::getServiceModuleListCore);
    }

    @RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String searchTableService(@RequestBody String request) {
        return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
                this, WarehouseStoreItemSearchModel.class, searchContext -> warehouseStoreManager
                        .getSearchProxy().searchItemList(searchContext),  this::getServiceModuleListCore);
    }

    protected List<WarehouseStoreItemServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException {
        return serviceBasicUtilityController.convServiceUIModuleList(WarehouseStoreItemServiceUIModel.class,
                WarehouseStoreItemServiceModel.class, rawList,
                warehouseStoreManager, warehouseStoreItemServiceUIModelExtension, logonActionController.getLogonInfo());
    }

}
