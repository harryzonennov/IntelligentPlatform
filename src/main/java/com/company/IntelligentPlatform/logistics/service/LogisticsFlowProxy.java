package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogisticsFlowProxy {

    public final String MAP_FIELD_WAREHOUSE = "refWarehouseUUID";

    public final String MAP_FIELD_WAREHOUSEAREA = "refWarehouseAreaUUID";

    @Autowired
    protected WarehouseManager warehouseManager;

    public static class WarehouseNodeMapRequest {

        protected String baseNodeId;

        protected String convToUIMethod;

        protected Object logicManager;

        protected Class<?>[] convToUIMethodParas;

        protected String convToAreaUIMethod;

        protected Class<?>[] convToAreaUIMethodParas;

        protected String prefix;

        protected String mapFieldWarehouse;

        protected String mapFieldWarehouseArea;

        public WarehouseNodeMapRequest() {
        }

        public WarehouseNodeMapRequest(String baseNodeId, Object logicManager, String convToUIMethod,
                                       Class<?>[] convToUIMethodParas, String convToAreaUIMethod,
                                       Class<?>[] convToAreaUIMethodParas) {
            this.baseNodeId = baseNodeId;
            this.convToUIMethod = convToUIMethod;
            this.logicManager = logicManager;
            this.convToUIMethodParas = convToUIMethodParas;
            this.convToAreaUIMethod = convToAreaUIMethod;
            this.convToAreaUIMethodParas = convToAreaUIMethodParas;
        }

        public WarehouseNodeMapRequest(String prefix, String baseNodeId, Object logicManager,
                                       String mapFieldWarehouse, String mapFieldWarehouseArea, String convToUIMethod,
                                       Class<?>[] convToUIMethodParas, String convToAreaUIMethod,
                                       Class<?>[] convToAreaUIMethodParas) {
            this.prefix = prefix;
            this.baseNodeId = baseNodeId;
            this.convToUIMethod = convToUIMethod;
            this.logicManager = logicManager;
            this.mapFieldWarehouse = mapFieldWarehouse;
            this.mapFieldWarehouseArea = mapFieldWarehouseArea;
            this.convToUIMethodParas = convToUIMethodParas;
            this.convToAreaUIMethod = convToAreaUIMethod;
            this.convToAreaUIMethodParas = convToAreaUIMethodParas;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getBaseNodeId() {
            return baseNodeId;
        }

        public void setBaseNodeId(String baseNodeId) {
            this.baseNodeId = baseNodeId;
        }

        public String getConvToUIMethod() {
            return convToUIMethod;
        }

        public void setConvToUIMethod(String convToUIMethod) {
            this.convToUIMethod = convToUIMethod;
        }

        public Object getLogicManager() {
            return logicManager;
        }

        public void setLogicManager(Object logicManager) {
            this.logicManager = logicManager;
        }

        public Class<?>[] getConvToUIMethodParas() {
            return convToUIMethodParas;
        }

        public void setConvToUIMethodParas(Class<?>[] convToUIMethodParas) {
            this.convToUIMethodParas = convToUIMethodParas;
        }

        public String getConvToAreaUIMethod() {
            return convToAreaUIMethod;
        }

        public void setConvToAreaUIMethod(String convToAreaUIMethod) {
            this.convToAreaUIMethod = convToAreaUIMethod;
        }

        public Class<?>[] getConvToAreaUIMethodParas() {
            return convToAreaUIMethodParas;
        }

        public void setConvToAreaUIMethodParas(Class<?>[] convToAreaUIMethodParas) {
            this.convToAreaUIMethodParas = convToAreaUIMethodParas;
        }

        public String getMapFieldWarehouse() {
            return mapFieldWarehouse;
        }

        public void setMapFieldWarehouse(String mapFieldWarehouse) {
            this.mapFieldWarehouse = mapFieldWarehouse;
        }

        public String getMapFieldWarehouseArea() {
            return mapFieldWarehouseArea;
        }

        public void setMapFieldWarehouseArea(String mapFieldWarehouseArea) {
            this.mapFieldWarehouseArea = mapFieldWarehouseArea;
        }
    }

    public List<UIModelNodeMapConfigure> getDefWarehouseMapConfigureList(
            WarehouseNodeMapRequest warehouseNodeMapRequest) {
        List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
        UIModelNodeMapConfigure warehouseMap = new UIModelNodeMapConfigure();
        warehouseMap.setSeName(Warehouse.SENAME);
        warehouseMap.setNodeName(Warehouse.NODENAME);
        String nodeInstId = ServiceEntityStringHelper.checkNullString(warehouseNodeMapRequest.getPrefix()) ?
                Warehouse.SENAME: warehouseNodeMapRequest.getPrefix() + Warehouse.SENAME;
        warehouseMap.setNodeInstID(nodeInstId);
        warehouseMap.setBaseNodeInstID(warehouseNodeMapRequest.getBaseNodeId());
        warehouseMap
                .setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
        List<SearchConfigConnectCondition> warehouseConditionList = new ArrayList<>();
        SearchConfigConnectCondition warehouseCondition0 = new SearchConfigConnectCondition();
        String mapFieldWarehouse = ServiceEntityStringHelper.checkNullString(warehouseNodeMapRequest.getMapFieldWarehouse()) ?
                MAP_FIELD_WAREHOUSE: warehouseNodeMapRequest.getMapFieldWarehouse();
        warehouseCondition0.setSourceFieldName(mapFieldWarehouse);
        warehouseCondition0
                .setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
        warehouseConditionList.add(warehouseCondition0);
        warehouseMap.setConnectionConditions(warehouseConditionList);
        warehouseMap.setConvToUIMethodParas(warehouseNodeMapRequest.convToUIMethodParas);
        warehouseMap.setServiceEntityManager(warehouseManager);
        warehouseMap.setLogicManager(warehouseNodeMapRequest.getLogicManager());
        warehouseMap
                .setConvToUIMethod(warehouseNodeMapRequest.getConvToUIMethod());
        uiModelNodeMapList.add(warehouseMap);

        UIModelNodeMapConfigure warehouseAreaMap = new UIModelNodeMapConfigure();
        warehouseAreaMap.setSeName(WarehouseArea.SENAME);
        warehouseAreaMap.setNodeName(WarehouseArea.NODENAME);
        warehouseAreaMap.setNodeInstID(WarehouseArea.NODENAME);
        warehouseAreaMap.setBaseNodeInstID(warehouseNodeMapRequest.getBaseNodeId());
        warehouseAreaMap
                .setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
        List<SearchConfigConnectCondition> warehouseAreaConditionList = new ArrayList<>();
        SearchConfigConnectCondition warehouseAreaCondition0 = new SearchConfigConnectCondition();

        String mapFieldWarehouseArea =
                ServiceEntityStringHelper.checkNullString(warehouseNodeMapRequest.getMapFieldWarehouseArea()) ?
                        MAP_FIELD_WAREHOUSEAREA: warehouseNodeMapRequest.getMapFieldWarehouseArea();
        warehouseAreaCondition0.setSourceFieldName(mapFieldWarehouseArea);
        warehouseAreaCondition0
                .setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
        warehouseAreaConditionList.add(warehouseAreaCondition0);
        warehouseAreaMap.setConnectionConditions(warehouseAreaConditionList);
        warehouseAreaMap.setLogicManager(warehouseNodeMapRequest.getLogicManager());
        warehouseAreaMap.setServiceEntityManager(warehouseManager);
        warehouseAreaMap.setConvToUIMethodParas(warehouseNodeMapRequest.getConvToAreaUIMethodParas());
        warehouseAreaMap
                .setConvToUIMethod(warehouseNodeMapRequest.getConvToAreaUIMethod());
        uiModelNodeMapList.add(warehouseAreaMap);
        return uiModelNodeMapList;
    }
}
