package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.MaterialUnitUIModel;
import com.company.IntelligentPlatform.common.model.Material;
import com.company.IntelligentPlatform.common.model.MaterialUnitReference;
import com.company.IntelligentPlatform.common.model.StandardMaterialUnit;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.DocFlowContextProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.util.List;
import java.util.Map;

@Service
public class MaterialUnitManager {

    public static final String METHOD_ConvMaterialUnitToUI = "convMaterialUnitToUI";

    public static final String METHOD_ConvUIToMaterialUnit = "convUIToMaterialUnit";

    public static final String METHOD_ConvMaterialToUnitUI = "convMaterialToUnitUI";

    public static final String METHOD_ConvStandardMaterialUnitToUI = "convStandardMaterialUnitToUI";
    
    @Autowired
    protected MaterialManager materialManager;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    @Autowired
    protected StandardMaterialUnitManager standardMaterialUnitManager;

    protected Logger logger = LoggerFactory.getLogger(MaterialUnitManager.class);

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), Material.NODENAME,
                        request.getUuid(), MaterialUnitReference.NODENAME, materialManager);
        docPageHeaderInputPara.setGenBaseModelList(
                (DocPageHeaderModelProxy.GenBaseModelList<Material>) material -> {
                    // How to get the base page header model list
                    List<PageHeaderModel> pageHeaderModelList =
                            docPageHeaderModelProxy.getDocPageHeaderModelList(material, null);
                    return pageHeaderModelList;
                });
        docPageHeaderInputPara.setGenHomePageModel(
                (DocPageHeaderModelProxy.GenHomePageModel<MaterialUnitReference>) (materialUnit, pageHeaderModel) -> {
                    // How to render current page header
                    pageHeaderModel.setHeaderName(materialUnit.getName());
                    return pageHeaderModel;
                });
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
    }

    public void convMaterialUnitToUI(
            MaterialUnitReference materialUnitReference,
            MaterialUnitUIModel materialUnitUIModel)
            throws ServiceEntityConfigureException {
        if (materialUnitReference != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(materialUnitReference, materialUnitUIModel);
            if (!ServiceEntityStringHelper
                    .checkNullString(materialUnitReference.getName())) {
                materialUnitUIModel
                        .setUnitName(materialUnitReference.getName());
            }
            materialUnitUIModel.setRefUUID(materialUnitReference.getRefUUID());
            materialUnitUIModel.setRatioToStandard(materialUnitReference
                    .getRatioToStandard());
            materialUnitUIModel.setBarcode(materialUnitReference.getBarcode());
            materialUnitUIModel.setBaseUUID(materialUnitReference
                    .getRootNodeUUID());
            materialUnitUIModel.setLength(materialUnitReference.getLength());
            materialUnitUIModel.setWidth(materialUnitReference.getWidth());
            materialUnitUIModel.setHeight(materialUnitReference.getHeight());
            materialUnitUIModel.setVolume(materialUnitReference.getVolume());
            materialUnitUIModel.setInboundDeliveryPrice(materialUnitReference
                    .getInboundDeliveryPrice());
            materialUnitUIModel.setOutboundDeliveryPrice(materialUnitReference
                    .getOutboundDeliveryPrice());
            materialUnitUIModel.setRetailPrice(materialUnitReference
                    .getRetailPrice());
            materialUnitUIModel.setMemberSalePrice(materialUnitReference
                    .getMemberSalePrice());
            materialUnitUIModel.setPurchasePrice(materialUnitReference
                    .getPurchasePrice());
            materialUnitUIModel.setWholeSalePrice(materialUnitReference
                    .getWholeSalePrice());
            materialUnitUIModel.setGrossWeight(materialUnitReference
                    .getGrossWeight());
            materialUnitUIModel.setNetWeight(materialUnitReference
                    .getNetWeight());
            materialUnitUIModel.setOutPackageMaterialType(materialUnitReference
                    .getOutPackageMaterialType());
            try {
                Map<Integer, String> packageTypeMap = materialManager.initOutPackageMaterialTypeMap();
                if(packageTypeMap != null){
                    materialUnitUIModel
                            .setOutPackageMaterialTypeValue(packageTypeMap
                                    .get(materialUnitReference
                                            .getOutPackageMaterialType()));
                }
            } catch (ServiceEntityInstallationException e) {
                // do nothing;
            }
            materialUnitUIModel
                    .setUnitType(materialUnitReference.getUnitType());
            materialUnitUIModel
                    .setUnitCost(materialUnitReference.getUnitCost());
            materialUnitUIModel.setRefLengthUnit(materialUnitReference
                    .getRefLengthUnit());
            materialUnitUIModel.setRefVolumeUnit(materialUnitReference
                    .getRefVolumeUnit());
            materialUnitUIModel.setRefWeightUnit(materialUnitReference
                    .getRefWeightUnit());
        }
    }

    public void convMaterialToUnitUI(Material material,
                                     MaterialUnitUIModel materialUnitUIModel)
            throws ServiceEntityConfigureException {
        if (material != null) {
            materialUnitUIModel.setMaterialId(material.getId());
            materialUnitUIModel.setMaterialName(material.getName());
            materialUnitUIModel.setBaseUUID(material.getUuid());
            Map<String, String> standardUnitMap = standardMaterialUnitManager
                    .getStandardUnitMap(material.getClient());
            materialUnitUIModel.setMainUnitName(standardUnitMap.get(material
                    .getMainMaterialUnit()));
            materialUnitUIModel.setMainInboundDeliveryPrice(material
                    .getInboundDeliveryPrice());
            materialUnitUIModel.setMainOutboundDeliveryPrice(material
                    .getOutboundDeliveryPrice());
            materialUnitUIModel.setMainRetailPrice(material.getRetailPrice());
            materialUnitUIModel.setMainPurchasePrice(material
                    .getPurchasePrice());
            materialUnitUIModel.setMainMemberSalePrice(material
                    .getMemberSalePrice());
            materialUnitUIModel.setMainWholeSalePrice(material
                    .getWholeSalePrice());
        }
    }

    public void convStandardMaterialUnitToUI(
            StandardMaterialUnit standardMaterialUnit,
            MaterialUnitUIModel materialUnitUIModel) {
        if (standardMaterialUnit != null) {
            // materialUnitUIModel.setUnitName(standardMaterialUnit.getName());
        }
    }

    public void convUIToMaterialUnit(MaterialUnitUIModel materialUnitUIModel,
                                     MaterialUnitReference materialUnitReference) {
        if (materialUnitReference != null) {
            DocFlowProxy.convUIToServiceEntityNode(materialUnitUIModel, materialUnitReference);
            if (!ServiceEntityStringHelper.checkNullString(materialUnitUIModel
                    .getUnitName())) {
                materialUnitReference
                        .setName(materialUnitUIModel.getUnitName());
            }
            materialUnitReference.setRefUUID(materialUnitUIModel.getRefUUID());
            materialUnitReference.setRatioToStandard(materialUnitUIModel
                    .getRatioToStandard());
            materialUnitReference.setBarcode(materialUnitUIModel.getBarcode());
            materialUnitReference.setLength(materialUnitUIModel.getLength());
            materialUnitReference.setWidth(materialUnitUIModel.getWidth());
            materialUnitReference.setHeight(materialUnitUIModel.getHeight());
            materialUnitReference.setVolume(materialUnitUIModel.getVolume());
            materialUnitReference.setInboundDeliveryPrice(materialUnitUIModel
                    .getInboundDeliveryPrice());
            materialUnitReference.setOutboundDeliveryPrice(materialUnitUIModel
                    .getOutboundDeliveryPrice());
            materialUnitReference.setRetailPrice(materialUnitUIModel
                    .getRetailPrice());
            materialUnitReference.setMemberSalePrice(materialUnitUIModel
                    .getMemberSalePrice());
            materialUnitReference.setPurchasePrice(materialUnitUIModel
                    .getPurchasePrice());
            materialUnitReference.setWholeSalePrice(materialUnitUIModel
                    .getWholeSalePrice());
            materialUnitReference.setGrossWeight(materialUnitUIModel
                    .getGrossWeight());
            materialUnitReference.setNetWeight(materialUnitUIModel
                    .getNetWeight());
            materialUnitReference.setOutPackageMaterialType(materialUnitUIModel
                    .getOutPackageMaterialType());
            materialUnitReference
                    .setUnitType(materialUnitUIModel.getUnitType());
            materialUnitReference
                    .setUnitCost(materialUnitUIModel.getUnitCost());
            materialUnitReference.setRefLengthUnit(materialUnitUIModel
                    .getRefLengthUnit());
            materialUnitReference.setRefVolumeUnit(materialUnitUIModel
                    .getRefVolumeUnit());
            materialUnitReference.setRefWeightUnit(materialUnitUIModel
                    .getRefWeightUnit());
        }
    }
}
