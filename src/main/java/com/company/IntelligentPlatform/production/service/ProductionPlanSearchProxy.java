package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.dto.ProdPlanTargetMatItemServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProdPlanTargetMatItemUIModel;
import com.company.IntelligentPlatform.production.dto.ProductionPlanSearchModel;
import com.company.IntelligentPlatform.production.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductionPlanSearchProxy extends ServiceSearchProxy {

    @Autowired
    protected ProductionPlanManager productionPlanManager;

    @Autowired
    protected ProdPlanTargetMatItemServiceUIModelExtension prodPlanTargetMatItemServiceUIModelExtension;

    @Override
    public Class<?> getDocSearchModelCls() {
        return ProductionPlanSearchModel.class;
    }

    @Override
    public Class<?> getMatItemSearchModelCls() {
        return null;
    }

    @Override
    public String getAuthorizationResource() {
        return productionPlanManager.getAuthorizationResource();
    }

    @Override
    public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
        return productionPlanManager.initStatusMap(languageCode);
    }

    @Override
    public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
        List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
        // Search node:[productionPlanItem]
        BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
        searchNodeConfig0.setSeName(ProductionPlanItem.SENAME);
        searchNodeConfig0.setNodeName(ProductionPlanItem.NODENAME);
        searchNodeConfig0.setNodeInstID(ProductionPlanItem.NODENAME);
        searchNodeConfig0.setStartNodeFlag(false);
        searchNodeConfig0.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
        searchNodeConfig0.setBaseNodeInstID(ProductionPlan.SENAME);
        searchNodeConfigList.add(searchNodeConfig0);
        // Search node:[prodPlanSupplyWarehouse]
        BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
        searchNodeConfig2.setSeName(ProdPlanSupplyWarehouse.SENAME);
        searchNodeConfig2.setNodeName(ProdPlanSupplyWarehouse.NODENAME);
        searchNodeConfig2.setNodeInstID(ProdPlanSupplyWarehouse.NODENAME);
        searchNodeConfig2.setStartNodeFlag(false);
        searchNodeConfig2.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
        searchNodeConfig2.setBaseNodeInstID(ProductionPlan.SENAME);
        searchNodeConfigList.add(searchNodeConfig2);
        // Search node:[productionPlan]
        BSearchNodeComConfigure searchNodeConfig4 = new BSearchNodeComConfigure();
        searchNodeConfig4.setSeName(ProductionPlan.SENAME);
        searchNodeConfig4.setNodeName(ProductionPlan.NODENAME);
        searchNodeConfig4.setNodeInstID(ProductionPlan.SENAME);
        searchNodeConfig4.setStartNodeFlag(true);
        searchNodeConfigList.add(searchNodeConfig4);
        // Search node:[itemMaterialSKU]
        BSearchNodeComConfigure searchNodeConfig8 = new BSearchNodeComConfigure();
        searchNodeConfig8.setSeName(MaterialStockKeepUnit.SENAME);
        searchNodeConfig8.setNodeName(MaterialStockKeepUnit.NODENAME);
        searchNodeConfig8.setNodeInstID(ProductionPlanSearchModel.NODE_ItemMaterialStockKeepUnit);
        searchNodeConfig8.setStartNodeFlag(false);
        searchNodeConfig8.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
        searchNodeConfig8.setBaseNodeInstID(ProductionPlanItem.NODENAME);
        searchNodeConfigList.add(searchNodeConfig8);
        // Search node:[outBillOfMaterialOrder]
        BSearchNodeComConfigure searchNodeConfig10 = new BSearchNodeComConfigure();
        searchNodeConfig10.setSeName(BillOfMaterialOrder.SENAME);
        searchNodeConfig10.setNodeName(BillOfMaterialOrder.NODENAME);
        searchNodeConfig10.setNodeInstID(BillOfMaterialOrder.SENAME);
        searchNodeConfig10.setStartNodeFlag(false);
        searchNodeConfig10.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
        searchNodeConfig10.setMapBaseFieldName("refBillOfMaterialUUID");
        searchNodeConfig10.setMapSourceFieldName("uuid");
        searchNodeConfig10.setBaseNodeInstID(ProductionPlan.SENAME);
        searchNodeConfigList.add(searchNodeConfig10);
        // Search node:[itemBillOfMaterialItem]
        BSearchNodeComConfigure searchNodeConfig12 = new BSearchNodeComConfigure();
        searchNodeConfig12.setSeName(BillOfMaterialItem.SENAME);
        searchNodeConfig12.setNodeName(BillOfMaterialItem.NODENAME);
        searchNodeConfig12.setNodeInstID("itemBillOfMaterialItem");
        searchNodeConfig12.setStartNodeFlag(false);
        searchNodeConfig12.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
        searchNodeConfig12.setMapBaseFieldName("refBOMItemUUID");
        searchNodeConfig12.setMapSourceFieldName("uuid");
        searchNodeConfig12.setBaseNodeInstID(ProductionPlanItem.NODENAME);
        searchNodeConfigList.add(searchNodeConfig12);
        // Search node:[warehouse]
        BSearchNodeComConfigure searchNodeConfig14 = new BSearchNodeComConfigure();
        searchNodeConfig14.setSeName(Warehouse.SENAME);
        searchNodeConfig14.setNodeName(Warehouse.NODENAME);
        searchNodeConfig14.setNodeInstID(Warehouse.SENAME);
        searchNodeConfig14.setStartNodeFlag(false);
        searchNodeConfig14.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
        searchNodeConfig14.setBaseNodeInstID(ProdPlanSupplyWarehouse.NODENAME);
        searchNodeConfigList.add(searchNodeConfig14);
        // Search node:[outMaterialStockKeepUnit]
        BSearchNodeComConfigure searchNodeConfig16 = new BSearchNodeComConfigure();
        searchNodeConfig16.setSeName(MaterialStockKeepUnit.SENAME);
        searchNodeConfig16.setNodeName(MaterialStockKeepUnit.NODENAME);
        searchNodeConfig16.setNodeInstID(ProductionPlanSearchModel.NODE_OutMaterialStockKeepUnit);
        searchNodeConfig16.setStartNodeFlag(false);
        searchNodeConfig16.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
        searchNodeConfig16.setMapBaseFieldName("refMaterialSKUUUID");
        searchNodeConfig16.setMapSourceFieldName("uuid");
        searchNodeConfig16.setBaseNodeInstID(ProductionPlan.SENAME);
        searchNodeConfigList.add(searchNodeConfig16);
        return searchNodeConfigList;
    }

}
