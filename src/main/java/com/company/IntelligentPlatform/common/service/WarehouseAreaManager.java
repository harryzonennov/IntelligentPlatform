package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.WarehouseAreaUIModel;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import java.util.List;

@Service
public class WarehouseAreaManager {
    
    @Autowired
    protected WarehouseManager warehouseManager;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    public static final String METHOD_ConvWarehouseAreaToUI = "convWarehouseAreaToUI";

    public static final String METHOD_ConvUIToWarehouseArea = "convUIToWarehouseArea";

    public static final String METHOD_ConvWarehouseToAreaUI = "convWarehouseToAreaUI";

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), Warehouse.NODENAME,
                        request.getUuid(), WarehouseArea.NODENAME, warehouseManager);
        docPageHeaderInputPara.setGenBaseModelList(
                (DocPageHeaderModelProxy.GenBaseModelList<Warehouse>) warehouse -> {
                    // How to get the base page header model list
                    return docPageHeaderModelProxy.getDocPageHeaderModelList(warehouse,  null);
                });
        docPageHeaderInputPara.setGenHomePageModel(
                (DocPageHeaderModelProxy.GenHomePageModel<WarehouseArea>) (warehouseArea, pageHeaderModel) -> {
                    // How to render current page header
                    pageHeaderModel.setHeaderName(warehouseArea.getId());
                    return pageHeaderModel;
                });
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
    }

    public void convWarehouseAreaToUI(WarehouseArea warehouseArea,
                                      WarehouseAreaUIModel warehouseAreaUIModel) {
        if (warehouseArea != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(warehouseArea, warehouseAreaUIModel);
            warehouseAreaUIModel.setId(warehouseArea.getId());
            warehouseAreaUIModel.setName(warehouseArea.getName());
            warehouseAreaUIModel.setNote(warehouseArea.getNote());
            warehouseAreaUIModel.setSpace(warehouseArea.getSpace());
            warehouseAreaUIModel.setGrossWeight(warehouseArea.getGrossWeight());
            warehouseAreaUIModel.setSwitchFlag(warehouseArea.getSwitchFlag());
            warehouseAreaUIModel.setLength(warehouseArea.getLength());
            warehouseAreaUIModel.setWidth(warehouseArea.getWidth());
            warehouseAreaUIModel.setArea(warehouseArea.getArea());
            warehouseAreaUIModel.setHeight(warehouseArea.getHeight());
            warehouseAreaUIModel.setVolume(warehouseArea.getVolume());
            warehouseAreaUIModel.setRestrictedGoodsFlag(warehouseArea
                    .getRestrictedGoodsFlag());
            warehouseAreaUIModel.setForbiddenGoodsFlag(warehouseArea
                    .getForbiddenGoodsFlag());
            warehouseAreaUIModel.setOperationMode(warehouseArea
                    .getOperationMode());
        }
    }

    public void convWarehouseToAreaUI(Warehouse warehouse,
                                      WarehouseAreaUIModel warehouseAreaUIModel) {
        if (warehouse != null) {
            warehouseAreaUIModel.setWarehouseId(warehouse.getId());
        }
    }

    public void convUIToWarehouseArea(
            WarehouseAreaUIModel warehouseAreaUIModel, WarehouseArea rawEntity) {
        DocFlowProxy.convUIToServiceEntityNode(warehouseAreaUIModel, rawEntity);
        rawEntity.setSpace(warehouseAreaUIModel.getSpace());
        rawEntity.setGrossWeight(warehouseAreaUIModel.getGrossWeight());
        rawEntity.setSwitchFlag(warehouseAreaUIModel.getSwitchFlag());
        rawEntity.setLength(warehouseAreaUIModel.getLength());
        rawEntity.setWidth(warehouseAreaUIModel.getWidth());
        rawEntity.setArea(warehouseAreaUIModel.getArea());
        rawEntity.setHeight(warehouseAreaUIModel.getHeight());
        rawEntity.setVolume(warehouseAreaUIModel.getVolume());
        rawEntity.setRestrictedGoodsFlag(warehouseAreaUIModel
                .getRestrictedGoodsFlag());
        rawEntity.setForbiddenGoodsFlag(warehouseAreaUIModel
                .getForbiddenGoodsFlag());
        rawEntity.setOperationMode(warehouseAreaUIModel.getOperationMode());
    }

}
