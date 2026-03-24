package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.MaterialUIModel;
import com.company.IntelligentPlatform.common.model.Material;
import com.company.IntelligentPlatform.common.model.MaterialType;
import com.company.IntelligentPlatform.common.model.StandardMaterialUnit;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportConfig;
import com.company.IntelligentPlatform.common.service.DocumentContentSpecifier;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceExcelConfigException;
import com.company.IntelligentPlatform.common.service.ServiceExcelHandlerProxy;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialListExcelHandler extends ServiceExcelHandlerProxy {

    @Autowired
    protected MaterialSpecifier materialSpecifier;

    @Autowired
    protected MaterialManager materialManager;

    @Autowired
    protected MaterialTypeManager materialTypeManager;

    @Autowired
    protected StandardMaterialUnitManager standardMaterialUnitManager;

    @Override
    public List<FieldNameUnit> getDefFieldNameList() {
        List<FieldNameUnit> fieldNameList = new ArrayList<>();
        fieldNameList.add(new FieldNameUnit("id"));
        fieldNameList.add(new FieldNameUnit("name"));
        fieldNameList.addAll(genCommonMaterialFieldNameList());
        return fieldNameList;
    }

    public static List<FieldNameUnit> genCommonMaterialFieldNameList(){
        List<FieldNameUnit> fieldNameList = new ArrayList<>();
        fieldNameList.add(new FieldNameUnit("materialTypeName"));
        fieldNameList.add(new FieldNameUnit("packageStandard"));
        fieldNameList.add(new FieldNameUnit("supplyTypeValue", "supplyType", "supplyType"));
        fieldNameList.add(new FieldNameUnit("qualityInspectFlagValue", "qualityInspectFlag", "qualityInspectFlag"));
        fieldNameList.add(new FieldNameUnit("materialCategoryValue", "materialCategory", "materialCategory"));
        fieldNameList.add(new FieldNameUnit("length"));
        fieldNameList.add(new FieldNameUnit("width"));
        fieldNameList.add(new FieldNameUnit("height"));
        fieldNameList.add(new FieldNameUnit("refLengthUnitValue","refLengthUnit", "refLengthUnit"));
        fieldNameList.add(new FieldNameUnit("volume"));
        fieldNameList.add(new FieldNameUnit("refVolumeUnitValue","refVolumeUnit", "refVolumeUnit"));
        fieldNameList.add(new FieldNameUnit("netWeight"));
        fieldNameList.add(new FieldNameUnit("refWeightUnitValue","refWeightUnit", "refWeightUnit"));
        return fieldNameList;
    }

    @Override
    public boolean checkCustomUploadExcel(String configureName, String client) throws ServiceEntityConfigureException {
        return false;
    }

    @Override
    public boolean checkCustomDownloadExcel(String configureName, String client)
            throws ServiceEntityConfigureException {
        return false;
    }

    @Override
    public Class<?> getExcelModelClass() {
        return MaterialUIModel.class;
    }

    @Override
    public DocumentContentSpecifier getDocumentSpecifier() {
        return materialSpecifier;
    }

    @Override
    public ServiceExcelReportConfig getDefExcelConfigure(SerialLogonInfo serialLogonInfo) throws ServiceExcelConfigException {
        return this.getDefExcelConfigureTemplate(serialLogonInfo);
    }

    @Override
    public String getConfigureName() {
        return Material.SENAME;
    }

    @Override
    protected List<MetaModelConfigure<MaterialUIModel>> getMetaModelConfigure(SerialLogonInfo serialLogonInfo) {
        List<FieldMeta<MaterialUIModel>> standardUnitFieldMetaList = new ArrayList<>();
        FieldMeta<MaterialUIModel> lengthFieldMeta = new FieldMeta<>("refLengthUnitValue", "refLengthUnit");
        // this callback will be invoked for parsing each UI model
        lengthFieldMeta.setUpdateUIModelHook(seUIModel -> MaterialListExcelHandler.super.setValueToUIModelByFieldKey(seUIModel,
                ServiceCollectionsHelper.asList(new ServiceBasicKeyStructure(seUIModel.getRefLengthUnitValue(),
                        IServiceEntityNodeFieldConstant.NAME)),
                lengthFieldMeta.getFieldMetaModelList(), IServiceEntityNodeFieldConstant.UUID, "refLengthUnit"));
        standardUnitFieldMetaList.add(lengthFieldMeta);

        FieldMeta<MaterialUIModel> weightFieldMeta = new FieldMeta<>("refWeightUnitValue", "refWeightUnit");
        // this callback will be invoked for parsing each UI model
        weightFieldMeta.setUpdateUIModelHook(seUIModel -> MaterialListExcelHandler.super.setValueToUIModelByFieldKey(seUIModel,
                ServiceCollectionsHelper.asList(new ServiceBasicKeyStructure(seUIModel.getRefWeightUnitValue(),
                        IServiceEntityNodeFieldConstant.NAME)),
                weightFieldMeta.getFieldMetaModelList(), IServiceEntityNodeFieldConstant.UUID, "refWeightUnit"));
        standardUnitFieldMetaList.add(weightFieldMeta);


        FieldMeta<MaterialUIModel> volumeFieldMeta = new FieldMeta<>("refVolumeUnitValue", "refVolumeUnit");
        // this callback will be invoked for parsing each UI model
        volumeFieldMeta.setUpdateUIModelHook(seUIModel -> MaterialListExcelHandler.super.setValueToUIModelByFieldKey(seUIModel,
                ServiceCollectionsHelper.asList(new ServiceBasicKeyStructure(seUIModel.getRefVolumeUnitValue(),
                        IServiceEntityNodeFieldConstant.NAME)),
                volumeFieldMeta.getFieldMetaModelList(), IServiceEntityNodeFieldConstant.UUID, "refVolumeUnit"));
        standardUnitFieldMetaList.add(volumeFieldMeta);

        MetaModelConfigure standardUnitModelConfigure =
                new MetaModelConfigure<MaterialUIModel>(serialLogonInfoTmp -> standardMaterialUnitManager
                .getEntityNodeListByKey(null, null,
                        StandardMaterialUnit.NODENAME, serialLogonInfoTmp.getClient(), null), standardUnitFieldMetaList);

        List<FieldMeta<MaterialUIModel>> materialTypeFieldMetaList = new ArrayList<>();
        FieldMeta<MaterialUIModel> refMaterialTypeFieldMeta = new FieldMeta<>("materialTypeName", "refMaterialType");
        // this callback will be invoked for parsing each UI model
        refMaterialTypeFieldMeta.setUpdateUIModelHook(seUIModel -> MaterialListExcelHandler.super.setValueToUIModelByFieldKey(seUIModel,
                ServiceCollectionsHelper.asList(new ServiceBasicKeyStructure(seUIModel.getMaterialTypeName(),
                        IServiceEntityNodeFieldConstant.NAME)),
                refMaterialTypeFieldMeta.getFieldMetaModelList(), IServiceEntityNodeFieldConstant.UUID, "refMaterialType"));
        materialTypeFieldMetaList.add(refMaterialTypeFieldMeta);

        MetaModelConfigure materialTypeModelConfigure =
                new MetaModelConfigure<MaterialUIModel>(serialLogonInfoTmp -> materialTypeManager
                        .getEntityNodeListByKey(null, null,
                                MaterialType.NODENAME, serialLogonInfoTmp.getClient(), null), materialTypeFieldMetaList);

        return ServiceCollectionsHelper.asList(standardUnitModelConfigure, materialTypeModelConfigure);
    }


    @Override
    public List<FieldMeta<MaterialUIModel>> getFieldMetaList(SerialLogonInfo serialLogonInfo) throws ServiceEntityInstallationException {
        List<FieldMeta<MaterialUIModel>> fieldMetaList = new ArrayList<>();
        fieldMetaList.add(new FieldMeta<>("qualityInspectFlagValue", "qualityInspectFlag",
                materialManager.initQualityInspectMap(serialLogonInfo.getLanguageCode()), StandardSwitchProxy.SWITCH_ON));
        fieldMetaList.add(new FieldMeta<>("supplyTypeValue", "supplyType",
                materialManager.initSupplyTypeMap(serialLogonInfo.getLanguageCode()), StandardSwitchProxy.SWITCH_ON));
        fieldMetaList.add(new FieldMeta<>("materialCategoryValue", "materialCategory",
                materialManager.initMaterialCategoryMap(serialLogonInfo.getLanguageCode()), StandardSwitchProxy.SWITCH_ON));
        fieldMetaList.add(new FieldMeta<>("operationModeValue", "operationMode",
                materialManager.initOperationModeMap(serialLogonInfo.getLanguageCode()), StandardSwitchProxy.SWITCH_ON));
        return fieldMetaList;
    }

}
