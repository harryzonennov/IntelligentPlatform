package com.company.IntelligentPlatform.logistics.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.logistics.model.InboundItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.logistics.dto.InboundItemUIModel;
import com.company.IntelligentPlatform.logistics.model.InboundDelivery;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.SerialIdDocumentProxy;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.SplitMatItemProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityDoubleHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import java.time.ZoneId;
import java.time.LocalDate;


@Service
public class InboundItemManager {

    public static final String METHOD_ConvInboundItemToUI = "convInboundItemToUI";

    public static final String METHOD_ConvUIToInboundItem = "convUIToInboundItem";

    public static final String METHOD_ConvMaterialStockKeepUnitToUI = "convMaterialStockKeepUnitToUI";

    public static final String METHOD_ConvParentDocToItemUI = "convParentDocToItemUI";

    public static final String METHOD_ConvWarehouseToItemUI = "convWarehouseToItemUI";

    public static final String METHOD_ConvWarehouseAreaToItemUI = "convWarehouseAreaToItemUI";

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected InboundDeliveryManager inboundDeliveryManager;

    @Autowired
    protected OutboundDeliveryManager outboundDeliveryManager;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected SplitMatItemProxy splitMatItemProxy;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    @Autowired
    protected SerialIdDocumentProxy serialIdDocumentProxy;

    protected Logger logger = LoggerFactory.getLogger(OutboundItemManager.class);

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), InboundDelivery.NODENAME,
                        request.getUuid(), InboundItem.NODENAME, inboundDeliveryManager);
        docPageHeaderInputPara.setGenBaseModelList(
                (DocPageHeaderModelProxy.GenBaseModelList<InboundDelivery>) purchaseRequest -> {
                    // How to get the base page header model list
                    return docPageHeaderModelProxy.getDocPageHeaderModelList(purchaseRequest, null);
                });
        docPageHeaderInputPara.setGenHomePageModel(
                (DocPageHeaderModelProxy.GenHomePageModel<InboundItem>) (inboundItem, pageHeaderModel) -> docPageHeaderModelProxy.getDefDocMaterialItemPageHeaderModel(inboundItem, pageHeaderModel));
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
    }

    public void convInboundItemToUI(
            InboundItem inboundItem,
            InboundItemUIModel inboundItemUIModel)
            throws ServiceEntityInstallationException, ServiceEntityConfigureException {
        convInboundItemToUI(inboundItem, inboundItemUIModel,
                null);
    }

    public void convInboundItemToUI(
            InboundItem inboundItem,
            InboundItemUIModel inboundItemUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException,
            ServiceEntityConfigureException {
        if (inboundItem != null) {
            docFlowProxy.convDocMatItemToUI(inboundItem,
                    inboundItemUIModel, logonInfo);
            inboundItemUIModel.setVolume(ServiceEntityDoubleHelper
                    .trancateDoubleScale4(inboundItem.getVolume()));
            inboundItemUIModel.setWeight(ServiceEntityDoubleHelper
                    .trancateDoubleScale4(inboundItem.getWeight()));
            inboundItemUIModel.setRefUnitName(inboundItem
                    .getRefUnitName());
            inboundItemUIModel.setDeclaredValue(inboundItem
                    .getDeclaredValue());
            inboundItemUIModel.setProductionBatchNumber(inboundItem
                    .getProductionBatchNumber());
            if (inboundItem.getProductionDate() != null) {
                inboundItemUIModel
                        .setProductionDate(DefaultDateFormatConstant.DATE_FORMAT
                                .format(inboundItem
                                        .getProductionDate()));
            }
            inboundItemUIModel.setRefWarehouseAreaUUID(inboundItem
                    .getRefWarehouseAreaUUID());
            inboundItemUIModel.setRefShelfNumberId(inboundItem
                    .getRefShelfNumberID());
            inboundItemUIModel.setProducerName(inboundItem
                    .getProducerName());
            inboundItemUIModel.setInboundFee(inboundItem
                    .getInboundFee());
            if (logonInfo != null) {
                Map<Integer, String> itemStatusMap = outboundDeliveryManager.initStatusMap(logonInfo.getLanguageCode());
                inboundItemUIModel.setItemStatusValue(itemStatusMap.get(inboundItemUIModel.getItemStatus()));
            }
            inboundItemUIModel.setTaxRate(inboundItem.getTaxRate());
            inboundItemUIModel.setItemPriceNoTax(ServiceEntityDoubleHelper
                    .trancateDoubleScale2(inboundItem
                            .getItemPriceNoTax()));
            inboundItemUIModel.setUnitPriceNoTax(ServiceEntityDoubleHelper
                    .trancateDoubleScale2(inboundItem
                            .getUnitPriceNoTax()));
            inboundItemUIModel.setCurrencyCode(inboundItem
                    .getCurrencyCode());
        }
    }

    public void convUIToInboundItem(
            InboundItemUIModel inboundItemUIModel,
            InboundItem rawEntity) {
        docFlowProxy.convUIToDocMatItem(inboundItemUIModel, rawEntity);
        rawEntity.setVolume(ServiceEntityDoubleHelper
                .trancateDoubleScale4(inboundItemUIModel.getVolume()));
        rawEntity.setWeight(ServiceEntityDoubleHelper
                .trancateDoubleScale4(inboundItemUIModel.getWeight()));
        rawEntity.setDeclaredValue(inboundItemUIModel.getDeclaredValue());
        rawEntity.setProductionBatchNumber(inboundItemUIModel
                .getProductionBatchNumber());
        rawEntity.setRefUnitName(inboundItemUIModel.getRefUnitName());
        if (inboundItemUIModel.getProductionDate() != null
                && !inboundItemUIModel.getProductionDate().equals(
                ServiceEntityStringHelper.EMPTYSTRING)) {
            try {
                rawEntity
                        .setProductionDate(DefaultDateFormatConstant.DATE_FORMAT.parse(inboundItemUIModel.getProductionDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } catch (ParseException e) {
                // do nothing
            }
        }
        rawEntity.setRefShelfNumberID(inboundItemUIModel.getRefShelfNumberId());
        rawEntity.setProducerName(inboundItemUIModel.getProducerName());
        rawEntity.setInboundFee(inboundItemUIModel.getInboundFee());
        rawEntity.setRefWarehouseAreaUUID(inboundItemUIModel
                .getRefWarehouseAreaUUID());
        rawEntity.setPackageStandard(inboundItemUIModel.getPackageStandard());
        rawEntity.setRefMaterialSKUId(inboundItemUIModel.getRefMaterialSKUId());
        rawEntity.setRefMaterialSKUName(inboundItemUIModel
                .getRefMaterialSKUName());
        rawEntity.setRefUnitName(inboundItemUIModel.getRefUnitName());
        rawEntity.setTaxRate(inboundItemUIModel.getTaxRate());
        rawEntity.setItemPriceNoTax(ServiceEntityDoubleHelper
                .trancateDoubleScale2(inboundItemUIModel.getItemPriceNoTax()));
        rawEntity.setCurrencyCode(inboundItemUIModel.getCurrencyCode());
        rawEntity.setUnitPriceNoTax(ServiceEntityDoubleHelper
                .trancateDoubleScale2(inboundItemUIModel.getUnitPriceNoTax()));
    }

    public void convMaterialStockKeepUnitToUI(
            MaterialStockKeepUnit materialStockKeepUnit,
            InboundItemUIModel inboundItemUIModel) {
        if (materialStockKeepUnit != null && inboundItemUIModel != null) {
            docFlowProxy.convMaterialSKUToItemUI(materialStockKeepUnit,
                    inboundItemUIModel);
            inboundItemUIModel.setUnitValue(ServiceEntityDoubleHelper
                    .trancateDoubleScale2(materialStockKeepUnit
                            .getRetailPrice()));
        }
    }

    public void convParentDocToItemUI(InboundDelivery inboundDelivery,
                                            InboundItemUIModel inboundItemUIModel)
            throws ServiceEntityInstallationException {
        convParentDocToItemUI(inboundDelivery, inboundItemUIModel, null);
    }

    public void convParentDocToItemUI(InboundDelivery inboundDelivery,
                                            InboundItemUIModel inboundItemUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        if (inboundDelivery != null) {
            docFlowProxy.convParentDocToItemUI(inboundDelivery, inboundItemUIModel, logonInfo);
            inboundItemUIModel.setRefWarehouseUUID(inboundDelivery
                    .getRefWarehouseUUID());
            inboundItemUIModel.setRefWarehouseUUID(inboundDelivery
                    .getRefWarehouseUUID());
        }
    }

    public void convWarehouseToItemUI(Warehouse warehouse,
                                      InboundItemUIModel inboundItemUIModel) {
        if (warehouse != null) {
            inboundItemUIModel.setRefWarehouseId(warehouse.getId());
            inboundItemUIModel.setRefWarehouseName(warehouse.getName());
        }
    }

    public void convWarehouseAreaToItemUI(WarehouseArea warehouseArea,
                                          InboundItemUIModel inboundItemUIModel) {
        if (warehouseArea != null) {
            inboundItemUIModel.setRefWarehouseAreaId(warehouseArea.getId());
            inboundItemUIModel.setRefWarehouseAreaName(warehouseArea.getName());
        }
    }

}
