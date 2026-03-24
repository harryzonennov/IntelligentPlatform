package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.dto.*;
import com.company.IntelligentPlatform.production.model.BillOfMaterialItem;
import com.company.IntelligentPlatform.production.model.BillOfMaterialOrder;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BillOfMaterialOrderSearchProxy extends ServiceSearchProxy {

    @Autowired
    protected BillOfMaterialOrderManager billOfMaterialOrderManager;

    @Autowired
    protected BillOfMaterialItemServiceUIModelExtension billOfMaterialItemServiceUIModelExtension;

    @Override
    public Class<?> getDocSearchModelCls() {
        return BillOfMaterialOrderSearchModel.class;
    }

    @Override
    public Class<?> getMatItemSearchModelCls() {
        return BillOfMaterialItemSearchModel.class;
    }

    @Override
    public String getAuthorizationResource() {
        return IServiceModelConstants.BillOfMaterialOrder;
    }

    @Override
    public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
        return billOfMaterialOrderManager.initStatusMap(languageCode);
    }



    @Override
    public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
        List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
        // Search node:[billOfMaterialItem]
        BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
        searchNodeConfig0.setSeName(BillOfMaterialItem.SENAME);
        searchNodeConfig0.setNodeName(BillOfMaterialItem.NODENAME);
        searchNodeConfig0.setNodeInstID(BillOfMaterialItem.NODENAME);
        searchNodeConfig0.setStartNodeFlag(true);
        searchNodeConfigList.add(searchNodeConfig0);
        // Search node:[materialStockKeepUnit]
        BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
        searchNodeConfig2.setSeName(MaterialStockKeepUnit.SENAME);
        searchNodeConfig2.setNodeName(MaterialStockKeepUnit.NODENAME);
        searchNodeConfig2.setNodeInstID(MaterialStockKeepUnit.SENAME);
        searchNodeConfig2.setStartNodeFlag(false);
        searchNodeConfig2
                .setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
        searchNodeConfig2.setMapBaseFieldName("refMaterialSKUUUID");
        searchNodeConfig2.setMapSourceFieldName("uuid");
        searchNodeConfig2.setBaseNodeInstID(BillOfMaterialItem.NODENAME);
        searchNodeConfigList.add(searchNodeConfig2);
        // Search node:[sub billOfMaterialItem]
        BSearchNodeComConfigure searchNodeConfig3 = new BSearchNodeComConfigure();
        searchNodeConfig3.setSeName(BillOfMaterialOrder.SENAME);
        searchNodeConfig3.setNodeName(BillOfMaterialOrder.NODENAME);
        searchNodeConfig3.setNodeInstID(BillOfMaterialOrder.SENAME);
        searchNodeConfig3.setStartNodeFlag(false);
        searchNodeConfig3
                .setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_CHILD);
        searchNodeConfig3.setBaseNodeInstID(BillOfMaterialItem.NODENAME);
        searchNodeConfigList.add(searchNodeConfig3);
        return searchNodeConfigList;
    }

    @Override
    public List<BSearchNodeComConfigure> getBasicItemSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
        List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
        // Search node:[billOfMaterialOrder]
        BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
        searchNodeConfig0.setSeName(BillOfMaterialOrder.SENAME);
        searchNodeConfig0.setNodeName(BillOfMaterialOrder.NODENAME);
        searchNodeConfig0.setNodeInstID(BillOfMaterialOrder.SENAME);
        searchNodeConfig0.setStartNodeFlag(true);
        searchNodeConfigList.add(searchNodeConfig0);
        // Search node:[materialStockKeepUnit]
        BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
        searchNodeConfig2.setSeName(MaterialStockKeepUnit.SENAME);
        searchNodeConfig2.setNodeName(MaterialStockKeepUnit.NODENAME);
        searchNodeConfig2.setNodeInstID(MaterialStockKeepUnit.SENAME);
        searchNodeConfig2.setStartNodeFlag(false);
        searchNodeConfig2
                .setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
        searchNodeConfig2.setMapBaseFieldName("refMaterialSKUUUID");
        searchNodeConfig2.setMapSourceFieldName("uuid");
        searchNodeConfig2.setBaseNodeInstID(BillOfMaterialOrder.SENAME);
        searchNodeConfigList.add(searchNodeConfig2);
        // Search node:[sub billOfMaterialItem]
        BSearchNodeComConfigure searchNodeConfig3 = new BSearchNodeComConfigure();
        searchNodeConfig3.setSeName(BillOfMaterialItem.SENAME);
        searchNodeConfig3.setNodeName(BillOfMaterialItem.NODENAME);
        searchNodeConfig3.setNodeInstID(BillOfMaterialItem.NODENAME);
        searchNodeConfig3.setStartNodeFlag(false);
        searchNodeConfig3
                .setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_ROOT);
        searchNodeConfig3.setBaseNodeInstID(BillOfMaterialOrder.SENAME);
        searchNodeConfigList.add(searchNodeConfig3);
        // Search node:[sub item material]
        BSearchNodeComConfigure searchNodeConfig4 = new BSearchNodeComConfigure();
        searchNodeConfig4.setSeName(MaterialStockKeepUnit.SENAME);
        searchNodeConfig4.setNodeName(MaterialStockKeepUnit.NODENAME);
        searchNodeConfig4
                .setNodeInstID(BillOfMaterialOrderSearchModel.NODEID_SUBITEM_MATERIAL);
        searchNodeConfig4.setStartNodeFlag(false);
        searchNodeConfig4
                .setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
        searchNodeConfig4.setMapBaseFieldName("refMaterialSKUUUID");
        searchNodeConfig4.setMapSourceFieldName("uuid");
        searchNodeConfig4.setBaseNodeInstID(BillOfMaterialItem.NODENAME);
        searchNodeConfigList.add(searchNodeConfig4);

        // Search node:[ProdWorkCenter]
        BSearchNodeComConfigure searchNodeConfig5 = new BSearchNodeComConfigure();
        searchNodeConfig5.setSeName(ProdWorkCenter.SENAME);
        searchNodeConfig5.setNodeName(ProdWorkCenter.NODENAME);
        searchNodeConfig5.setNodeInstID(ProdWorkCenter.SENAME);
        searchNodeConfig5.setStartNodeFlag(false);
        searchNodeConfig5
                .setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
        searchNodeConfig5.setMapBaseFieldName("refWocUUID");
        searchNodeConfig5.setMapSourceFieldName("uuid");
        searchNodeConfig5.setBaseNodeInstID(BillOfMaterialOrder.SENAME);
        searchNodeConfigList.add(searchNodeConfig5);
        return searchNodeConfigList;
    }


}
