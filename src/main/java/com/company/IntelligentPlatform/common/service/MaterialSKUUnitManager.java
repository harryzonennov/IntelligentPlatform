package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.MaterialSKUUnitUIModel;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.util.List;
import java.util.Map;

@Service
public class MaterialSKUUnitManager {

    public static final String METHOD_ConvMaterialSKUUnitToUI = "convMaterialSKUUnitToUI";

    public static final String METHOD_ConvUIToMaterialSKUUnit = "convUIToMaterialSKUUnit";

    public static final String METHOD_ConvMaterialSKUToUnitUI = "convMaterialSKUToUnitUI";

    public static final String METHOD_ConvStandardMaterialUnitToUI = "convStandardMaterialUnitToUI";
    
    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected MaterialManager materialManager;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    @Autowired
    protected StandardMaterialUnitManager standardMaterialUnitManager;

    protected Logger logger = LoggerFactory.getLogger(MaterialSKUUnitManager.class);

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), MaterialStockKeepUnit.NODENAME,
                        request.getUuid(), MaterialSKUUnitReference.NODENAME, materialStockKeepUnitManager);
        docPageHeaderInputPara.setGenBaseModelList(
                (DocPageHeaderModelProxy.GenBaseModelList<MaterialStockKeepUnit>) materialStockKeepUnit -> {
                    // How to get the base page header model list
                    List<PageHeaderModel> pageHeaderModelList =
                            docPageHeaderModelProxy.getDocPageHeaderModelList(materialStockKeepUnit, null);
                    return pageHeaderModelList;
                });
        docPageHeaderInputPara.setGenHomePageModel(
                (DocPageHeaderModelProxy.GenHomePageModel<MaterialSKUUnitReference>) (materialSKUUnit, pageHeaderModel) -> {
                    // How to render current page header
                    pageHeaderModel.setHeaderName(materialSKUUnit.getName());
                    return pageHeaderModel;
                });
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
    }

    public void convMaterialSKUUnitToUI(
            MaterialSKUUnitReference materialSKUUnitReference,
            MaterialSKUUnitUIModel materialSKUUnitUIModel)
            throws ServiceEntityConfigureException {
        if (materialSKUUnitReference != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(materialSKUUnitReference, materialSKUUnitUIModel);
            materialSKUUnitUIModel.setRefUUID(materialSKUUnitReference.getRefUUID());
            materialSKUUnitUIModel.setRatioToStandard(materialSKUUnitReference
                    .getRatioToStandard());
            materialSKUUnitUIModel.setBarcode(materialSKUUnitReference.getBarcode());
            materialSKUUnitUIModel.setBaseUUID(materialSKUUnitReference
                    .getRootNodeUUID());
            materialSKUUnitUIModel.setLength(materialSKUUnitReference.getLength());
            materialSKUUnitUIModel.setWidth(materialSKUUnitReference.getWidth());
            materialSKUUnitUIModel.setHeight(materialSKUUnitReference.getHeight());
            materialSKUUnitUIModel.setVolume(materialSKUUnitReference.getVolume());
            materialSKUUnitUIModel.setInboundDeliveryPrice(materialSKUUnitReference
                    .getInboundDeliveryPrice());
            materialSKUUnitUIModel.setOutboundDeliveryPrice(materialSKUUnitReference
                    .getOutboundDeliveryPrice());
            materialSKUUnitUIModel.setRetailPrice(materialSKUUnitReference
                    .getRetailPrice());
            materialSKUUnitUIModel.setMemberSalePrice(materialSKUUnitReference
                    .getMemberSalePrice());
            materialSKUUnitUIModel.setPurchasePrice(materialSKUUnitReference
                    .getPurchasePrice());
            materialSKUUnitUIModel.setWholeSalePrice(materialSKUUnitReference
                    .getWholeSalePrice());
            materialSKUUnitUIModel.setGrossWeight(materialSKUUnitReference
                    .getGrossWeight());
            materialSKUUnitUIModel.setNetWeight(materialSKUUnitReference
                    .getNetWeight());
            materialSKUUnitUIModel.setOutPackageMaterialType(materialSKUUnitReference
                    .getOutPackageMaterialType());
            try {
                Map<Integer, String> packageTypeMap = materialManager.initOutPackageMaterialTypeMap();
                if(packageTypeMap != null){
                    materialSKUUnitUIModel
                            .setOutPackageMaterialTypeValue(packageTypeMap
                                    .get(materialSKUUnitReference
                                            .getOutPackageMaterialType()));
                }
            } catch (ServiceEntityInstallationException e) {
                // do nothing;
            }
            materialSKUUnitUIModel
                    .setUnitType(materialSKUUnitReference.getUnitType());
            materialSKUUnitUIModel
                    .setUnitCost(materialSKUUnitReference.getUnitCost());
            materialSKUUnitUIModel.setRefLengthUnit(materialSKUUnitReference
                    .getRefLengthUnit());
            materialSKUUnitUIModel.setRefVolumeUnit(materialSKUUnitReference
                    .getRefVolumeUnit());
            materialSKUUnitUIModel.setRefWeightUnit(materialSKUUnitReference
                    .getRefWeightUnit());
        }
    }

    public void convMaterialSKUToUnitUI(MaterialStockKeepUnit materialStockKeepUnit,
                                     MaterialSKUUnitUIModel materialSKUUnitUIModel)
            throws ServiceEntityConfigureException {
        if (materialStockKeepUnit != null) {
            materialSKUUnitUIModel.setMaterialId(materialStockKeepUnit.getId());
            materialSKUUnitUIModel.setMaterialName(materialStockKeepUnit.getName());
            materialSKUUnitUIModel.setBaseUUID(materialStockKeepUnit.getUuid());
            Map<String, String> standardUnitMap = standardMaterialUnitManager
                    .getStandardUnitMap(materialStockKeepUnit.getClient());
            materialSKUUnitUIModel.setMainUnitName(standardUnitMap.get(materialStockKeepUnit
                    .getMainMaterialUnit()));
            materialSKUUnitUIModel.setMainInboundDeliveryPrice(materialStockKeepUnit
                    .getInboundDeliveryPrice());
            materialSKUUnitUIModel.setMainOutboundDeliveryPrice(materialStockKeepUnit
                    .getOutboundDeliveryPrice());
            materialSKUUnitUIModel.setMainRetailPrice(materialStockKeepUnit.getRetailPrice());
            materialSKUUnitUIModel.setMainPurchasePrice(materialStockKeepUnit
                    .getPurchasePrice());
            materialSKUUnitUIModel.setMainMemberSalePrice(materialStockKeepUnit
                    .getMemberSalePrice());
            materialSKUUnitUIModel.setMainWholeSalePrice(materialStockKeepUnit
                    .getWholeSalePrice());
        }
    }

    public void convStandardMaterialUnitToUI(
            StandardMaterialUnit standardMaterialUnit,
            MaterialSKUUnitUIModel materialSKUUnitUIModel) {
        if (standardMaterialUnit != null) {
            // materialSKUUnitUIModel.setUnitName(standardMaterialUnit.getName());
        }
    }

    public void convUIToMaterialSKUUnit(MaterialSKUUnitUIModel materialSKUUnitUIModel,
                                     MaterialSKUUnitReference materialSKUUnitReference) {
        if (materialSKUUnitReference != null) {
            DocFlowProxy.convUIToServiceEntityNode(materialSKUUnitUIModel, materialSKUUnitReference);
            if (!ServiceEntityStringHelper.checkNullString(materialSKUUnitUIModel
                    .getUnitName())) {
                materialSKUUnitReference
                        .setName(materialSKUUnitUIModel.getUnitName());
            }
            materialSKUUnitReference.setRefUUID(materialSKUUnitUIModel.getRefUUID());
            materialSKUUnitReference.setRatioToStandard(materialSKUUnitUIModel
                    .getRatioToStandard());
            materialSKUUnitReference.setBarcode(materialSKUUnitUIModel.getBarcode());
            materialSKUUnitReference.setLength(materialSKUUnitUIModel.getLength());
            materialSKUUnitReference.setWidth(materialSKUUnitUIModel.getWidth());
            materialSKUUnitReference.setHeight(materialSKUUnitUIModel.getHeight());
            materialSKUUnitReference.setVolume(materialSKUUnitUIModel.getVolume());
            materialSKUUnitReference.setInboundDeliveryPrice(materialSKUUnitUIModel
                    .getInboundDeliveryPrice());
            materialSKUUnitReference.setOutboundDeliveryPrice(materialSKUUnitUIModel
                    .getOutboundDeliveryPrice());
            materialSKUUnitReference.setRetailPrice(materialSKUUnitUIModel
                    .getRetailPrice());
            materialSKUUnitReference.setMemberSalePrice(materialSKUUnitUIModel
                    .getMemberSalePrice());
            materialSKUUnitReference.setPurchasePrice(materialSKUUnitUIModel
                    .getPurchasePrice());
            materialSKUUnitReference.setWholeSalePrice(materialSKUUnitUIModel
                    .getWholeSalePrice());
            materialSKUUnitReference.setGrossWeight(materialSKUUnitUIModel
                    .getGrossWeight());
            materialSKUUnitReference.setNetWeight(materialSKUUnitUIModel
                    .getNetWeight());
            materialSKUUnitReference.setOutPackageMaterialType(materialSKUUnitUIModel
                    .getOutPackageMaterialType());
            materialSKUUnitReference
                    .setUnitType(materialSKUUnitUIModel.getUnitType());
            materialSKUUnitReference
                    .setUnitCost(materialSKUUnitUIModel.getUnitCost());
            materialSKUUnitReference.setRefLengthUnit(materialSKUUnitUIModel
                    .getRefLengthUnit());
            materialSKUUnitReference.setRefVolumeUnit(materialSKUUnitUIModel
                    .getRefVolumeUnit());
            materialSKUUnitReference.setRefWeightUnit(materialSKUUnitUIModel
                    .getRefWeightUnit());
        }
    }

}
