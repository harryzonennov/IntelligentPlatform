package com.company.IntelligentPlatform.common.service;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.company.IntelligentPlatform.common.dto.CargoUIModel;
import com.company.IntelligentPlatform.common.dto.MaterialSKUUnitUIModel;
import com.company.IntelligentPlatform.common.dto.MaterialStockKeepUnitUIModel;
import com.company.IntelligentPlatform.common.dto.MaterialUIModel;
import com.company.IntelligentPlatform.common.dto.MaterialSKUExtendPropertyUIModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.MaterialStockKeepUnitRepository;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.SystemCodeValueCollectionManager;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialNumberSetting;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityDoubleHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Logic Manager CLASS FOR Service Entity [MaterialStockKeepUnit]
 *
 * @author
 * @date Fri Sep 04 17:15:08 CST 2015
 * <p>
 * This class is generated automatically by platform automation register
 * tool
 */
@Service
public class MaterialStockKeepUnitManager extends ServiceEntityManager {

    public static final String METHOD_ConvStandardMaterialUnitToPropertyUI = "convStandardMaterialUnitToPropertyUI";

    public static final String METHOD_ConvMaterialSKUToUI = "convMaterialSKUToUI";

    public static final String METHOD_ConvUIToMaterialSKU = "convUIToMaterialSKU";

    public static final String METHOD_ConvCorporateSupplierToUI = "convCorporateSupplierToUI";

    public static final String METHOD_ConvMaterialToUI = "convMaterialToUI";

    public static final String METHOD_ConvMaterialTypeToUI = "convMaterialTypeToUI";

    public static final String METHOD_ConvMaterialSKUExtendPropertyToUI = "convMaterialSKUExtendPropertyToUI";

    public static final String METHOD_ConvUIToMaterialSKUExtendProperty = "convUIToMaterialSKUExtendProperty";

    final Logger logger = LoggerFactory.getLogger(MaterialStockKeepUnitManager.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected MaterialStockKeepUnitRepository materialStockKeepUnitDAO;

    @Autowired
    protected MaterialStockKeepUnitConfigureProxy materialStockKeepUnitConfigureProxy;

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    @Autowired
    protected SerialNumberSettingManager serialNumberSettingManager;

    @Autowired
    protected MaterialManager materialManager;

    @Autowired
    protected StandardSwitchProxy standardSwitchProxy;

    @Autowired
    protected StandardMaterialUnitManager standardMaterialUnitManager;

    @Autowired
    protected MaterialTypeManager materialTypeManager;

    @Autowired
    protected MaterialStockKeepUnitIdHelper materialStockKeepUnitIdHelper;

    @Autowired
    protected MaterialConfigureTemplateManager materialConfigureTemplateManager;

    @Autowired
    protected MatDecisionValueSettingManager matDecisionValueSettingManager;

    @Autowired
    protected SystemCodeValueCollectionManager systemCodeValueCollectionManager;

    @Autowired
    protected MaterialIdHelper materialIdHelper;

    @Autowired
    protected MaterialStockKeepUnitCache materialStockKeepUnitCache;

    @Autowired
    protected MaterialSKUActionExecutionProxy materialSKUActionExecutionProxy;

    private Map<String, Map<Integer, String>> materialCategoryMapLan = new HashMap<>();

    private Map<String, Map<Integer, String>> supplyTypeMapLan = new HashMap<>();

    private Map<String, Map<Integer, String>> traceModeMapLan = new HashMap<>();

    private Map<String, Map<Integer, String>> traceLevelMapLan = new HashMap<>();

    private Map<String, Map<Integer, String>> traceStatusMapLan = new HashMap<>();

    private Map<Integer, String> cargoTypeMap;

    private Map<Integer, String> measureFlagMap;

    protected Map<String, Map<String, String>> materialUnitHashMap = new HashMap<>();

    protected Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

    protected Map<String, MaterialStockKeepUnit> materialStockKeepUnitMap = new HashMap<>();

    protected Map<String, MaterialSKUUnitReference> materialSKUUnitReferenceMap = new HashMap<>();

    @Autowired
    protected MaterialStockKeepUnitSearchProxy materialStockKeepUnitSearchProxy;

    public MaterialStockKeepUnitManager() {
        super.seConfigureProxy = new MaterialStockKeepUnitConfigureProxy();
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, materialStockKeepUnitDAO));
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(materialStockKeepUnitConfigureProxy);
    }

    @Override
    public ServiceEntityNode newRootEntityNode(String client)
            throws ServiceEntityConfigureException {
        MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) super
                .newRootEntityNode(client);
        String MaterialSKUId = materialStockKeepUnitIdHelper
                .genDefaultId(client);
        materialStockKeepUnit.setId(MaterialSKUId);
        return materialStockKeepUnit;
    }

    public Map<Integer, String> initTraceModeMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.traceModeMapLan, MaterialStockKeepUnitUIModel.class,
                "traceMode");
    }

    public Map<Integer, String> initTraceLevelMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.traceLevelMapLan, MaterialStockKeepUnitUIModel.class,
                "traceLevel");
    }

    public Map<Integer, String> initTraceStatusMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.traceStatusMapLan, MaterialStockKeepUnitUIModel.class,
                "traceStatus");
    }

    public Map<Integer, String> initMaterialCategoryMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.materialCategoryMapLan, MaterialUIModel.class,
                "materialCategory");
    }

    public Map<Integer, String> initQualityInspectMap(String languageCode)
            throws ServiceEntityInstallationException {
        return materialConfigureTemplateManager
                .initQualityInspectMap(languageCode);
    }

    public Map<Integer, String> initStatusMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.statusMapLan, MaterialUIModel.class,
                "status");
    }

    public Map<Integer, String> initMeasureFlagMap(String lanCode)
            throws ServiceEntityInstallationException {
        if (this.measureFlagMap == null) {
            this.measureFlagMap = materialConfigureTemplateManager
                    .initMeasureFlagMap(lanCode);
        }
        return this.measureFlagMap;
    }

    public Map<Integer, String> initSupplyTypeMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.supplyTypeMapLan, MaterialUIModel.class, "supplyType");
    }

    public Map<Integer, String> initCargoTypeMap()
            throws ServiceEntityInstallationException {
        if (this.cargoTypeMap == null) {
            this.cargoTypeMap = serviceDropdownListHelper.getUIDropDownMap(
                    CargoUIModel.class, "cargoType");
        }
        return this.cargoTypeMap;
    }

    public Map<Integer, String> initSwitchMap(String lanCode)
            throws ServiceEntityInstallationException {
        return standardSwitchProxy.getSimpleSwitchMap(lanCode);
    }

    public Map<Integer, String> initPackageMaterialType(String client,
                                                        boolean refreshFlag) throws ServiceEntityInstallationException {
        List<ServiceEntityNode> systemCodeValueUnionList = materialManager
                .initRawPackageTypeMap(client, refreshFlag);
        return systemCodeValueCollectionManager
                .convertIntCodeValueUnionMap(systemCodeValueUnionList);
    }

    public Map<Integer, String> initMaterialQualityInspectFlagMap(
            String languageCode) throws ServiceEntityInstallationException {
        return materialConfigureTemplateManager
                .initQualityInspectMap(languageCode);
    }

    /**
     * Core Logic to generate Material SKU id when creation, considering base
     * material
     *
     * @param materialStockKeepUnit
     * @param material
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    public String genMaterialSKUId(MaterialStockKeepUnit materialStockKeepUnit,
                                   Material material, boolean copyMaterialId)
            throws MaterialException, ServiceEntityConfigureException {
        MaterialStockKeepUnit initialMaterialSKU = getInitialSKUFromMaterial(
                material, copyMaterialId);
        return this.genMaterialSKUId(materialStockKeepUnit, initialMaterialSKU,
                material, copyMaterialId);
    }

    /**
     * Core Logic to generate Material SKU id when creation, considering base
     * material, as well as the configuration defined in MatDecisionValueSetting
     *
     * @param materialStockKeepUnit
     * @param material
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    public String genMaterialSKUId(MaterialStockKeepUnit materialStockKeepUnit,
                                   MaterialStockKeepUnit initialMaterialSKU, Material material,
                                   boolean copyMaterialId) throws MaterialException,
            ServiceEntityConfigureException {
        if (initialMaterialSKU == null && copyMaterialId) {
            boolean duplicateResult = this.checkIDDuplicate(
                    materialStockKeepUnit.getUuid(), material.getId(),
                    MaterialStockKeepUnit.NODENAME, material.getUuid());
            if (!duplicateResult) {
                // In case create as initial Material
                return material.getId();
            }
        }
        MatDecisionValueSetting matDecisionSerialFormat = matDecisionValueSettingManager
                .getDecisionValue(material,
                        MatDecisionValueSettingManager.VAUSAGE_SUBID_FORMAT);
        if (matDecisionSerialFormat != null) {
            SerialNumberSetting serialNumberSetting = (SerialNumberSetting) serialNumberSettingManager
                    .getEntityNodeByKey(matDecisionSerialFormat.getRawValue(),
                            IServiceEntityNodeFieldConstant.UUID,
                            SerialNumberSetting.NODENAME, material.getClient(),
                            null, true);
            if (serialNumberSetting != null) {
                return this.materialIdHelper.genDefaultId(serialNumberSetting);
            }
        }
        // in case no decision
        return this.materialStockKeepUnitIdHelper.genDefaultId(material
                .getClient());
    }

    /**
     * Wrapper interface method to create new back end SKU from material
     *
     * @param material
     * @throws MaterialException
     */
    public MaterialStockKeepUnitServiceModel createNewSKUOnlineFromMaterial(
            Material material, List<ServiceEntityNode> materialUnitList,
            boolean copyMaterialId,
            List<ServiceEntityNode> materialAttachmentList)
            throws ServiceEntityConfigureException, MaterialException {
        MaterialStockKeepUnitServiceModel materialStockKeepUnitServiceModel = new MaterialStockKeepUnitServiceModel();
        MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) newRootEntityNode(material
                .getClient());
        initDefaultConvertMaterialToSKU(materialStockKeepUnit, material);
        /*
         * [Step2] Logic to decide the id format for material SKU.
         */
        String materialSKUId = this.genMaterialSKUId(materialStockKeepUnit,
                material, copyMaterialId);
        materialStockKeepUnit.setId(materialSKUId);
        materialStockKeepUnit.setProductionDate(new Date());
        materialStockKeepUnitServiceModel
                .setMaterialStockKeepUnit(materialStockKeepUnit);
        /*
         * [Step2] update the data from Material Unit into SKU unit
         */
        List<MaterialSKUUnitServiceModel> materialSKUUnitList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(materialUnitList)) {
            for (ServiceEntityNode seNode : materialUnitList) {
                MaterialUnitReference materialUnitReference = (MaterialUnitReference) seNode;
                MaterialSKUUnitReference materialSKUUnitReference = (MaterialSKUUnitReference) newEntityNode(
                        materialStockKeepUnit,
                        MaterialSKUUnitReference.NODENAME);
                initDefaultConvertMaterialUnitToSKUUnit(materialUnitReference,
                        materialSKUUnitReference);
                MaterialSKUUnitServiceModel materialSKUUnitServiceModel = new MaterialSKUUnitServiceModel();
                materialSKUUnitServiceModel.setMaterialSKUUnitReference(materialSKUUnitReference);
                materialSKUUnitList.add(materialSKUUnitServiceModel);
            }
            materialStockKeepUnitServiceModel
                    .setMaterialSKUUnitList(materialSKUUnitList);
        }
        List<ServiceEntityNode> materialSKUAttachmentList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(materialAttachmentList)) {
            for (ServiceEntityNode seNode : materialAttachmentList) {
                MaterialAttachment materialAttachment = (MaterialAttachment) seNode;
                MaterialSKUAttachment materialSKUAttachment = (MaterialSKUAttachment) newEntityNode(
                        materialStockKeepUnit, MaterialSKUAttachment.NODENAME);
                initDefaultConvertMaterialAttachmentToSKUAttachment(
                        materialAttachment, materialSKUAttachment);
                materialSKUAttachmentList.add(materialSKUAttachment);
            }
            materialStockKeepUnitServiceModel
                    .setMaterialSKUAttachment(materialSKUAttachmentList);
        }
        return materialStockKeepUnitServiceModel;
    }

    /**
     * Get the ratio comparing from storeUnit1 to storeUnit2 even with different
     * unit
     *
     * @param storageCoreUnit1
     * @param storageCoreUnit2
     * @param client
     * @return
     * @throws MaterialException
     * @throws ServiceEntityConfigureException
     */
    public double getStorageUnitRatio(StorageCoreUnit storageCoreUnit1,
                                      StorageCoreUnit storageCoreUnit2, String client)
            throws MaterialException, ServiceEntityConfigureException {
        if (storageCoreUnit2.getAmount() == 0) {
            return 0;
        }
        if (storageCoreUnit1.getAmount() == 0) {
            return 0;
        }
        if (storageCoreUnit1.getRefMaterialSKUUUID() == null) {
            // should raise exception
            throw new MaterialException(
                    MaterialException.PARA_NON_FOUND_MATERIAL,
                    storageCoreUnit1.getRefMaterialSKUUUID());
        }
        if (!storageCoreUnit1.getRefMaterialSKUUUID().equals(
                storageCoreUnit2.getRefMaterialSKUUUID())) {
            // should raise exception
            throw new MaterialException(
                    MaterialException.TYPE_NOTSAME_MATERIAL_COMPARE);
        }
        // Special handling: In case refUnit 2 is empty, make 2 unit same
        if (ServiceEntityStringHelper.checkNullString(storageCoreUnit2
                .getRefUnitUUID())) {
            storageCoreUnit2.setRefUnitUUID(storageCoreUnit1.getRefUnitUUID());
        }
        /*
         * Converting storageCoreUnit1 unit to be the same as storageCoreUnit2
         * unit by add 10 unit
         */
        StorageCoreUnit tmpStorageCoreUnit1 = new StorageCoreUnit();
        tmpStorageCoreUnit1.setRefMaterialSKUUUID(storageCoreUnit2
                .getRefMaterialSKUUUID());
        tmpStorageCoreUnit1.setAmount(10);

        tmpStorageCoreUnit1.setRefUnitUUID(storageCoreUnit2.getRefUnitUUID());
        storageCoreUnit1 = mergeStorageUnitCore(storageCoreUnit1,
                tmpStorageCoreUnit1, StorageCoreUnit.OPERATOR_ADD, client);
        /*
         * then minus 10 unit to keep the original value
         */
        storageCoreUnit1 = mergeStorageUnitCore(storageCoreUnit1,
                tmpStorageCoreUnit1, StorageCoreUnit.OPERATOR_MINUS, client);
        return storageCoreUnit1.getAmount() / storageCoreUnit2.getAmount();
    }

    public static StorageCoreUnit copyStorageCoreUnit(StorageCoreUnit storageCoreUnit1,
                                                      StorageCoreUnit storageCoreUnit2) {
        if (storageCoreUnit2 == null) {
            return storageCoreUnit1;
        }
        if (storageCoreUnit1 == null) {
            return storageCoreUnit2;
        }
        storageCoreUnit2.setAmount(storageCoreUnit1.getAmount());
        storageCoreUnit2.setRefUnitUUID(storageCoreUnit1.getRefUnitUUID());
        storageCoreUnit2.setRefMaterialSKUUUID(storageCoreUnit1.getRefMaterialSKUUUID());
        return storageCoreUnit2;
    }

    /**
     * Merge and Add list of storage unit list all together
     *
     * @param storageCoreUnitList
     * @param client
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    public StorageCoreUnit mergeStorageUnitCore(
            List<StorageCoreUnit> storageCoreUnitList, String client)
            throws ServiceEntityConfigureException, MaterialException {
        if (ServiceCollectionsHelper.checkNullList(storageCoreUnitList)) {
            return null;
        }
        StorageCoreUnit resultUnit = storageCoreUnitList.get(0);
        for (int i = 1; i < storageCoreUnitList.size(); i++) {
            StorageCoreUnit storageCoreUnit = storageCoreUnitList.get(i);
            resultUnit = mergeStorageUnitCore(resultUnit, storageCoreUnit,
                    StorageCoreUnit.OPERATOR_ADD, client);
        }
        return resultUnit;
    }

    public StorageCoreUnit mergeStorageUnitCore(
            StorageCoreUnit storageCoreUnit1,
            List<StorageCoreUnit> storageCoreUnitList, int operator,
            String client) throws ServiceEntityConfigureException,
            MaterialException {
        if (ServiceCollectionsHelper.checkNullList(storageCoreUnitList)) {
            return storageCoreUnit1;
        }
        StorageCoreUnit resultUnit = storageCoreUnit1;
        for (StorageCoreUnit storageCoreUnit : storageCoreUnitList) {
            resultUnit = mergeStorageUnitCore(storageCoreUnit1,
                    storageCoreUnit, operator, client);
        }
        return resultUnit;
    }

    public Map<String, String> initMaterialUnitMap(String refMaterialSKUUUID,
                                                   String client) throws ServiceEntityConfigureException,
            MaterialException {
        Map<String, String> materialUnitMap;
        if (this.materialUnitHashMap.containsKey(refMaterialSKUUUID)) {
            materialUnitMap = this.materialUnitHashMap.get(refMaterialSKUUUID);
        } else {
            MaterialStockKeepUnit materialStockKeepUnit = getRefTemplateMaterialSKU(
                    refMaterialSKUUUID, client);
            if (materialStockKeepUnit == null) {
                logger.info("Material already deleted:" + refMaterialSKUUUID);
                throw new MaterialException(
                        MaterialException.PARA_NON_FOUND_MATERIAL,
                        refMaterialSKUUUID);
            }
            List<ServiceEntityNode> materialSKUUnitList = getEntityNodeListByKey(
                    materialStockKeepUnit.getUuid(),
                    IServiceEntityNodeFieldConstant.ROOTNODEUUID,
                    MaterialSKUUnitReference.NODENAME,
                    materialStockKeepUnit.getClient(), null);
            materialUnitMap = getAllUnitMapFromSKU(materialStockKeepUnit,
                    materialSKUUnitList);
            this.materialUnitHashMap.put(refMaterialSKUUUID, materialUnitMap);
        }
        return materialUnitMap;
    }

    /**
     * Get the reference template Material SKU for the given Material SKU Or Registered Product UUID.
     * <p>
     * If the supplied SKU UUID is for a registered product instance, this method looks up and returns its referenced template Material
     * SKU If the supplied SKU is already a template SKU, the same instance is returned.
     *
     * @param refMaterialSKUUUID: the Material SKU or Registered Product UUID to normalize to its template; must not be null
     * @param client:             client ID
     * @return the corresponding template Material SKU
     */
    public MaterialStockKeepUnit getRefTemplateMaterialSKU(
            String refMaterialSKUUUID, String client)
            throws ServiceEntityConfigureException {
        MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) getEntityNodeByKey(
                refMaterialSKUUUID, IServiceEntityNodeFieldConstant.UUID,
                MaterialStockKeepUnit.NODENAME, client, null);
        if (materialStockKeepUnit == null) {
            return null;
        }
        return getRefTemplateMaterialSKU(materialStockKeepUnit);
    }

    /**
     * Get the reference template Material SKU for the given Material SKU Or Registered Product instance.
     * <p>
     * If the supplied SKU is a registered product instance, this method looks up and returns its referenced template Material
     * SKU using the same client context. If the supplied SKU is already a template SKU, the same instance is returned.
     *
     * @param materialStockKeepUnit the Material SKU or Registered Product instance to normalize to its template; must not be null
     * @return the corresponding template Material SKU
     */
    public MaterialStockKeepUnit getRefTemplateMaterialSKU(
            MaterialStockKeepUnit materialStockKeepUnit)
            throws ServiceEntityConfigureException {
        if (RegisteredProductManager.checkRegisteredProduct(materialStockKeepUnit)) {
            // In case registered product instance
            RegisteredProduct registeredProduct = (RegisteredProduct) materialStockKeepUnit;
            return (MaterialStockKeepUnit) getEntityNodeByKey(
                    registeredProduct.getRefMaterialSKUUUID(),
                    IServiceEntityNodeFieldConstant.UUID,
                    MaterialStockKeepUnit.NODENAME,
                    registeredProduct.getClient(), null);
        }
        return materialStockKeepUnit;
    }

    public static class AmountUnion {

        private double amount;

        private String amountLabel;

        private String refUnitName;

        public AmountUnion(double amount, String amountLabel, String refUnitName) {
            this.amount = amount;
            this.amountLabel = amountLabel;
            this.refUnitName = refUnitName;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getAmountLabel() {
            return amountLabel;
        }

        public void setAmountLabel(String amountLabel) {
            this.amountLabel = amountLabel;
        }

        public String getRefUnitName() {
            return refUnitName;
        }

        public void setRefUnitName(String refUnitName) {
            this.refUnitName = refUnitName;
        }

    }

    /**
     * Generates a formatted label for the specified amount and unit, based on the given Material SKU and unit UUID.
     * <p>
     * This method retrieves the unit name and constructs a label combining the amount and unit name,
     * or returns the amount as a string if the unit name is not available.
     * </p>
     *
     * @param refMaterialSKUUUID the UUID of the reference Material SKU
     * @param refUnitUUID        the UUID of the reference unit
     * @param amount             the numeric amount to be labeled
     * @param client             the client identifier
     * @return a string label representing the amount and its unit
     * @throws ServiceEntityConfigureException if configuration lookup fails
     * @throws MaterialException               if material/unit information is invalid or missing
     */
    public String getAmountLabel(String refMaterialSKUUUID, String refUnitUUID,
                                 double amount, String client)
            throws ServiceEntityConfigureException, MaterialException {
        AmountUnion amountUnion = getAmountUnion(refMaterialSKUUUID, refUnitUUID, amount, client);
        return amountUnion.getAmountLabel();
    }

    /**
     * Constructs an {@link AmountUnion} object containing the amount, its formatted label, and the unit name.
     * <p>
     * The label is generated by combining the amount and unit name if available, otherwise just the amount.
     * </p>
     *
     * @param refMaterialSKUUUID the UUID of the reference Material SKU
     * @param refUnitUUID        the UUID of the reference unit
     * @param amount             the numeric amount
     * @param client             the client identifier
     * @return an {@link AmountUnion} object with amount, label, and unit name
     * @throws ServiceEntityConfigureException if configuration lookup fails
     * @throws MaterialException               if material/unit information is invalid or missing
     */
    public AmountUnion getAmountUnion(String refMaterialSKUUUID, String refUnitUUID,
                                      double amount, String client)
            throws ServiceEntityConfigureException, MaterialException {
        String amountLabel = ServiceEntityDoubleHelper
                .trancateDoubleScale2(amount) + "";
        if (ServiceEntityStringHelper.checkNullString(refMaterialSKUUUID)) {
            return new AmountUnion(amount, amountLabel, "");
        }
        if (ServiceEntityStringHelper.checkNullString(refUnitUUID)) {
            return new AmountUnion(amount, amountLabel, "");
        }
        String refUnitName = getRefUnitName(refMaterialSKUUUID, refUnitUUID,
                client);
        if (!ServiceEntityStringHelper.checkNullString(refUnitName)) {
            amountLabel = amount + refUnitName;
        } else {
            amountLabel = amount + "";
        }
        return new AmountUnion(amount, amountLabel, refUnitName);
    }

    /**
     * Retrieves the display name of a unit for a given Material SKU and unit UUID.
     * <p>
     * This method looks up the unit name from the material's unit map using the provided Material SKU UUID and unit UUID.
     * If the unit UUID is not found or is null/empty, an empty string is returned.
     * </p>
     *
     * @param refMaterialSKUUUID the UUID of the reference Material SKU
     * @param refUnitUUID        the UUID of the unit to look up
     * @param client             the client identifier
     * @return the display name of the unit, or an empty string if not found
     * @throws MaterialException               if material/unit information is invalid or missing
     * @throws ServiceEntityConfigureException if configuration lookup fails
     */
    public String getRefUnitName(String refMaterialSKUUUID, String refUnitUUID,
                                 String client) throws MaterialException,
            ServiceEntityConfigureException {
        String refUnitName = ServiceEntityStringHelper.EMPTYSTRING;
        Map<String, String> materialUnitMap = initMaterialUnitMap(
                refMaterialSKUUUID, client);
        if (!ServiceEntityStringHelper.checkNullString(refUnitUUID)
                && materialUnitMap != null) {
            if (materialUnitMap.get(refUnitUUID) != null) {
                refUnitName = materialUnitMap.get(refUnitUUID);
            }
        }
        return refUnitName;
    }

    @Override
    public void updateBuffer(ServiceEntityNode serviceEntityNode) throws ServiceComExecuteException {
        materialStockKeepUnitCache.updateServiceEntityNodeToCache(serviceEntityNode);
    }

    @Override
    public void updateBuffer(List<ServiceEntityNode> seNodeList) throws ServiceComExecuteException {
//		this.materialStockKeepUnitMap = new HashMap<String, MaterialStockKeepUnit>();
        if (!ServiceCollectionsHelper.checkNullList(seNodeList)) {
            for (ServiceEntityNode seNode : seNodeList) {
                updateBuffer(seNode);
            }
        }
    }

    /**
     * Merges two {@link StorageCoreUnit} instances by performing the specified operation (addition or subtraction) on their amounts.
     * <p>
     * Both units must refer to the same Material SKU (or normalize to the same template SKU). If not, a {@link MaterialException} is thrown.
     * The resulting unit will have the same Material SKU and unit UUID as the operands.
     * </p>
     *
     * @param storageCoreUnit1 the first storage unit operand
     * @param storageCoreUnit2 the second storage unit operand
     * @param operator         the operation to perform: {@link StorageCoreUnit#OPERATOR_ADD} or {@link StorageCoreUnit#OPERATOR_MINUS}
     * @param client           the client identifier
     * @return a new {@link StorageCoreUnit} representing the merged result
     * @throws ServiceEntityConfigureException if configuration lookup fails
     * @throws MaterialException               if the Material SKU is missing or the units are not compatible
     */
    public StorageCoreUnit mergeStorageUnitCore1(
            StorageCoreUnit storageCoreUnit1, StorageCoreUnit storageCoreUnit2,
            int operator, String client)
            throws ServiceEntityConfigureException, MaterialException {
        // Validate input
        if (storageCoreUnit1 == null || storageCoreUnit2 == null) {
            throw new MaterialException(MaterialException.TYPE_SYSTEM_ERROR);
        }
        if (ServiceEntityStringHelper.checkNullString(storageCoreUnit1.getRefMaterialSKUUUID())) {
            throw new MaterialException(MaterialException.PARA_NON_FOUND_MATERIAL, storageCoreUnit1.getRefMaterialSKUUUID());
        }
        if (ServiceEntityStringHelper.checkNullString(storageCoreUnit2.getRefMaterialSKUUUID())) {
            throw new MaterialException(MaterialException.PARA_NON_FOUND_MATERIAL, storageCoreUnit2.getRefMaterialSKUUUID());
        }

        // Normalize to template SKU if needed
        String templateSKU1 = getRefTemplateMaterialSKU(storageCoreUnit1.getRefMaterialSKUUUID(), client).getUuid();
        String templateSKU2 = getRefTemplateMaterialSKU(storageCoreUnit2.getRefMaterialSKUUUID(), client).getUuid();
        if (!templateSKU1.equals(templateSKU2)) {
            throw new MaterialException(MaterialException.TYPE_NOTSAME_MATERIAL_COMPARE);
        }

        // Prepare result unit
        StorageCoreUnit resultUnit = new StorageCoreUnit();
        resultUnit.setRefMaterialSKUUUID(templateSKU1);

        // Use unit UUID from the first operand by default
        String refUnitUUID = storageCoreUnit1.getRefUnitUUID();
        if (ServiceEntityStringHelper.checkNullString(refUnitUUID)) {
            refUnitUUID = storageCoreUnit2.getRefUnitUUID();
        }
        resultUnit.setRefUnitUUID(refUnitUUID);

        // Perform operation
        double amount1 = storageCoreUnit1.getAmount();
        double amount2 = storageCoreUnit2.getAmount();
        double resultAmount;
        if (operator == StorageCoreUnit.OPERATOR_ADD) {
            resultAmount = amount1 + amount2;
        } else if (operator == StorageCoreUnit.OPERATOR_MINUS) {
            resultAmount = amount1 - amount2;
        } else {
            throw new MaterialException(MaterialException.PARA_SYSTEM_ERROR, "Invalid operator: " + operator);
        }
        resultUnit.setAmount(resultAmount);

        return resultUnit;
    }

    /**
     * Merges two {@link StorageCoreUnit} instances by performing the specified operation (addition or subtraction) on their amounts.
     * <p>
     * Both units must refer to the same Material SKU (or normalize to the same template SKU). If not, a {@link MaterialException} is thrown.
     * The resulting unit will have the same Material SKU and unit UUID as the operands.
     * </p>
     *
     * @param storageCoreUnit1 the first storage unit operand
     * @param storageCoreUnit2 the second storage unit operand
     * @param operator         the operation to perform: {@link StorageCoreUnit#OPERATOR_ADD} or {@link StorageCoreUnit#OPERATOR_MINUS}
     * @param client           the client identifier
     * @return a new {@link StorageCoreUnit} representing the merged result
     * @throws ServiceEntityConfigureException if configuration lookup fails
     * @throws MaterialException               if the Material SKU is missing or the units are not compatible
     */
    public StorageCoreUnit mergeStorageUnitCore(
            StorageCoreUnit storageCoreUnit1, StorageCoreUnit storageCoreUnit2,
            int operator, String client)
            throws ServiceEntityConfigureException, MaterialException {
        StorageCoreUnit sumStorageUnit = new StorageCoreUnit();
        if (ServiceEntityStringHelper.checkNullString(storageCoreUnit1
                .getRefMaterialSKUUUID())) {
            // should raise exception
            throw new MaterialException(
                    MaterialException.PARA_NON_FOUND_MATERIAL,
                    storageCoreUnit1.getRefMaterialSKUUUID());
        }
        if (!storageCoreUnit1.getRefMaterialSKUUUID().equals(
                storageCoreUnit2.getRefMaterialSKUUUID())) {
            MaterialStockKeepUnit templateSKU1 = getRefTemplateMaterialSKU(
                    storageCoreUnit1.getRefMaterialSKUUUID(), client);
            MaterialStockKeepUnit templateSKU2 = getRefTemplateMaterialSKU(
                    storageCoreUnit2.getRefMaterialSKUUUID(), client);
            if (templateSKU1.getUuid().equals(templateSKU2.getUuid())) {
                storageCoreUnit1.setRefMaterialSKUUUID(templateSKU1.getUuid());
                storageCoreUnit2.setRefMaterialSKUUUID(templateSKU1.getUuid());
            } else {
                // should raise exception
                throw new MaterialException(
                        MaterialException.TYPE_NOTSAME_MATERIAL_COMPARE);
            }
        }
        if (operator != StorageCoreUnit.OPERATOR_ADD
                && operator != StorageCoreUnit.OPERATOR_MINUS) {
            return null;
        }
        // Special process for minus and zero variable
        if (storageCoreUnit2.getAmount() == 0) {
            return storageCoreUnit1;
        }
        if (storageCoreUnit1.getAmount() == 0
                && operator == StorageCoreUnit.OPERATOR_ADD) {
            return storageCoreUnit2;
        }
        if (storageCoreUnit1.getAmount() == 0
                && operator == StorageCoreUnit.OPERATOR_MINUS) {
            storageCoreUnit2.setAmount(0 - storageCoreUnit2.getAmount());
            return storageCoreUnit2;
        }
        /*
         * [Step1] In case the required unit uuid matches the store item unit
         * uuid, then just check the amount
         */
        if (storageCoreUnit1.getRefUnitUUID().equals(
                storageCoreUnit2.getRefUnitUUID())) {
            if (operator == StorageCoreUnit.OPERATOR_ADD) {
                sumStorageUnit.setAmount(storageCoreUnit1.getAmount()
                        + storageCoreUnit2.getAmount());
            } else {
                sumStorageUnit.setAmount(storageCoreUnit1.getAmount()
                        - storageCoreUnit2.getAmount());
            }
            sumStorageUnit.setRefUnitUUID(storageCoreUnit1.getRefUnitUUID());
            sumStorageUnit.setRefMaterialSKUUUID(storageCoreUnit1
                    .getRefMaterialSKUUUID());
            return sumStorageUnit;
        }
        /*
         * [Step2] In case required unit uuid doesn't match the store item unit
         * uuid, then have to convert both unit to standard unit
         */
        MaterialStockKeepUnit materialStockKeepUnit = null;
        if (this.materialStockKeepUnitMap.containsKey(storageCoreUnit1
                .getRefMaterialSKUUUID())) {
            // Get material SKU from cache
            materialStockKeepUnit = this.materialStockKeepUnitMap
                    .get(storageCoreUnit1.getRefMaterialSKUUUID());
        } else {
            materialStockKeepUnit = (MaterialStockKeepUnit) getEntityNodeByKey(
                    storageCoreUnit1.getRefMaterialSKUUUID(),
                    IServiceEntityNodeFieldConstant.UUID,
                    MaterialStockKeepUnit.NODENAME, client, null);
            if (materialStockKeepUnit != null) {
                this.materialStockKeepUnitMap.put(
                        storageCoreUnit1.getRefMaterialSKUUUID(),
                        materialStockKeepUnit);
            }
        }
        if (materialStockKeepUnit == null) {
            // Should raise exception
        }
        /*
         * [Step 2.1] In case storage1 unit is the standard unit, then just
         * convert store unit
         */
        if (storageCoreUnit1.getRefUnitUUID().equals(
                getMainUnitUUID(materialStockKeepUnit))) {
            MaterialSKUUnitReference storeUnitRef2 = getMaterialSKUUnitWrapper(
                    storageCoreUnit2.getRefUnitUUID(), client);

            double refStoreAmount2 = storageCoreUnit2.getAmount();
            if (storeUnitRef2 != null) {
                // Convert storage2 to standard unit
                refStoreAmount2 = storeUnitRef2.getRatioToStandard()
                        * refStoreAmount2;
                if (operator == StorageCoreUnit.OPERATOR_ADD) {
                    sumStorageUnit.setAmount(storageCoreUnit1.getAmount()
                            + refStoreAmount2);
                } else {
                    sumStorageUnit.setAmount(storageCoreUnit1.getAmount()
                            - refStoreAmount2);
                }
                sumStorageUnit
                        .setRefUnitUUID(storageCoreUnit1.getRefUnitUUID());
                sumStorageUnit.setRefMaterialSKUUUID(storageCoreUnit1
                        .getRefMaterialSKUUUID());
                return sumStorageUnit;
            }
        }
        /*
         * [Step 2.2] In case storage2 is the standard unit, then just convert
         * required unit
         */
        if (storageCoreUnit2.getRefUnitUUID().equals(
                getMainUnitUUID(materialStockKeepUnit))) {
            MaterialSKUUnitReference storeUnitRef1 = getMaterialSKUUnitWrapper(
                    storageCoreUnit1.getRefUnitUUID(), client);
            double refStoreAmount1 = storageCoreUnit1.getAmount();
            if (storeUnitRef1 != null) {
                // Convert storage1 to standard unit
                refStoreAmount1 = storeUnitRef1.getRatioToStandard()
                        * refStoreAmount1;
                if (operator == StorageCoreUnit.OPERATOR_ADD) {
                    sumStorageUnit.setAmount(refStoreAmount1
                            + storageCoreUnit2.getAmount());
                } else {
                    sumStorageUnit.setAmount(refStoreAmount1
                            - storageCoreUnit2.getAmount());
                }
                sumStorageUnit
                        .setRefUnitUUID(storageCoreUnit2.getRefUnitUUID());
                sumStorageUnit.setRefMaterialSKUUUID(storageCoreUnit2
                        .getRefMaterialSKUUUID());
                return sumStorageUnit;
            }
        }
        /*
         * [Step 2.3] In case neither store Unit and require unit is standard
         */
        MaterialSKUUnitReference storeUnitRef1 = getMaterialSKUUnitWrapper(
                storageCoreUnit1.getRefUnitUUID(), client);
        MaterialSKUUnitReference storeUnitRef2 = getMaterialSKUUnitWrapper(
                storageCoreUnit2.getRefUnitUUID(), client);
        double refStoreAmount1 = storageCoreUnit1.getAmount();
        double refStoreAmount2 = storageCoreUnit2.getAmount();
        if (storeUnitRef1 != null && storeUnitRef2 != null) {
            refStoreAmount1 = refStoreAmount1
                    * storeUnitRef1.getRatioToStandard();
            refStoreAmount2 = refStoreAmount2
                    * storeUnitRef1.getRatioToStandard();
            refStoreAmount1 = storeUnitRef1.getRatioToStandard()
                    * refStoreAmount1;
            if (operator == StorageCoreUnit.OPERATOR_ADD) {
                sumStorageUnit.setAmount(refStoreAmount1 + refStoreAmount2);
            } else {
                sumStorageUnit.setAmount(refStoreAmount1 - refStoreAmount2);
            }
            sumStorageUnit
                    .setRefUnitUUID(getMainUnitUUID(materialStockKeepUnit));
            sumStorageUnit.setRefMaterialSKUUUID(materialStockKeepUnit
                    .getUuid());
            return sumStorageUnit;
        }
        return storageCoreUnit1;
    }

    /**
     * Convert Unit to target
     *
     * @param requestCoreUnit
     * @param targetRefUnitUUID : target unit uuid
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    public StorageCoreUnit convertUnit(StorageCoreUnit requestCoreUnit,
                                       String targetRefUnitUUID, String client) throws MaterialException,
            ServiceEntityConfigureException {
        if (requestCoreUnit.getRefUnitUUID().equals(targetRefUnitUUID)) {
            return requestCoreUnit;
        }
        String mainUnitUUID = this.getMainUnitUUID(requestCoreUnit
                .getRefMaterialSKUUUID());
        // In case target unit is the main unit
        if (mainUnitUUID.equals(targetRefUnitUUID)) {
            StorageCoreUnit requestCoreUnitBack = (StorageCoreUnit) requestCoreUnit
                    .clone();
            StorageCoreUnit toTargetCoreUnit = new StorageCoreUnit(
                    requestCoreUnitBack.getRefMaterialSKUUUID(),
                    targetRefUnitUUID, 1);
            requestCoreUnitBack = mergeStorageUnitCore(requestCoreUnitBack,
                    toTargetCoreUnit, StorageCoreUnit.OPERATOR_ADD, client);
            requestCoreUnitBack = mergeStorageUnitCore(requestCoreUnitBack,
                    toTargetCoreUnit, StorageCoreUnit.OPERATOR_MINUS, client);
            return requestCoreUnitBack;
        }
        // In case neither of them are main unit
        StorageCoreUnit requestCoreUnitBack = (StorageCoreUnit) requestCoreUnit
                .clone();
        if (!mainUnitUUID.equals(requestCoreUnit.getRefUnitUUID())
                && !mainUnitUUID.equals(targetRefUnitUUID)) {
            StorageCoreUnit toMainCoreUnit = new StorageCoreUnit(
                    requestCoreUnitBack.getRefMaterialSKUUUID(), mainUnitUUID,
                    1);
            requestCoreUnitBack = mergeStorageUnitCore(requestCoreUnitBack,
                    toMainCoreUnit, StorageCoreUnit.OPERATOR_ADD, client);
            requestCoreUnitBack = mergeStorageUnitCore(requestCoreUnitBack,
                    toMainCoreUnit, StorageCoreUnit.OPERATOR_MINUS, client);
        }
        MaterialSKUUnitReference targetUnitRef = getMaterialSKUUnitWrapper(
                targetRefUnitUUID, client);
        double targetAmount = requestCoreUnitBack.getAmount()
                / targetUnitRef.getRatioToStandard();
        StorageCoreUnit toTargetCoreUnit = new StorageCoreUnit(
                requestCoreUnit.getRefMaterialSKUUUID(), targetRefUnitUUID,
                targetAmount);
        return toTargetCoreUnit;
    }

    /**
     * Core Logic to get initial Material SKU from Material
     *
     * @param material       : source material instance
     * @param copyMaterialId : whether initial SKU id should be copied from source material
     * @return
     * @throws ServiceEntityConfigureException
     */
    public MaterialStockKeepUnit getInitialSKUFromMaterial(Material material,
                                                           boolean copyMaterialId) throws ServiceEntityConfigureException {
        List<ServiceBasicKeyStructure> keyList = ServiceCollectionsHelper.asList(new ServiceBasicKeyStructure(material.getUuid(),
				MaterialStockKeepUnit.FIELD_REF_MATERIALUUID, ServiceBasicKeyStructure.OPERATOR_OR));
        if (copyMaterialId) {
            keyList.add(new ServiceBasicKeyStructure(
					material.getId(), IServiceEntityNodeFieldConstant.ID, ServiceBasicKeyStructure.OPERATOR_ADD));
        }
        return (MaterialStockKeepUnit) getEntityNodeByKeyList(
                keyList, MaterialStockKeepUnit.NODENAME, material.getClient(),
                null, true);
    }

    /**
     * Wrapper interface method to initial update back-end SKU from material
     *
     * @param material
     * @throws MaterialException
     */
    public void updateInitialSKUBackendFromMaterial(
            Material material,
            boolean copyMaterialId,
            List<ServiceEntityNode> materialUnitList,
            final Function<MaterialStockKeepUnit, MaterialStockKeepUnit> updateMaterialSKUCallBack,
            SerialLogonInfo serialLogonInfo, boolean overwriteFlag)
            throws ServiceEntityConfigureException, MaterialException, DocActionException, ServiceModuleProxyException {

        /*
         * [Step1] update the data from Material to SKU root if overwriteFlag
         */
        MaterialStockKeepUnit materialStockKeepUnit = getInitialSKUFromMaterial(
                material, copyMaterialId);
        if (materialStockKeepUnit != null) {
            if (overwriteFlag) {
                initDefaultConvertMaterialToSKU(materialStockKeepUnit, material);
                updateSENode(materialStockKeepUnit, serialLogonInfo.getRefUserUUID(),
                        serialLogonInfo.getResOrgUUID());
                if (updateMaterialSKUCallBack != null) {
                    updateMaterialSKUCallBack.apply(materialStockKeepUnit);
                }
            }
        } else {
            // In case create a new Material SKU.
            materialStockKeepUnit = newSKUFromParentMaterialCore(material, copyMaterialId, serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
            if (updateMaterialSKUCallBack != null) {
                updateMaterialSKUCallBack.apply(materialStockKeepUnit);
            }
        }
        /*
         * [Step2] update the data from Material Unit into SKU unit
         */
        updateMaterialUnitToSKU(materialStockKeepUnit, materialUnitList, overwriteFlag, serialLogonInfo);
        /*
         * [Step 3] Try to active material SKU
         */
        MaterialStockKeepUnitSpecifier materialStockKeepUnitSpecifier =
                (MaterialStockKeepUnitSpecifier) materialSKUActionExecutionProxy.getDocumentContentSpecifier();
        MaterialStockKeepUnitServiceModel materialStockKeepUnitServiceModel =
                materialStockKeepUnitSpecifier.quickCreateServiceModel(materialStockKeepUnit, null);
        materialSKUActionExecutionProxy.executeActionCore(materialStockKeepUnitServiceModel,
                MaterialSKUActionLog.DOC_ACTION_ACTIVE, serialLogonInfo);
    }

    /**
     * Wrapper interface method to initial update back-end SKU from material
     *
     * @param material
     * @throws MaterialException
     */
    public void updateSKUBatchFromRefMaterial(Material material,
                                              boolean copyMaterialId, List<ServiceEntityNode> materialUnitList,
                                              SerialLogonInfo serialLogonInfo,
                                              boolean overwriteFlag) throws ServiceEntityConfigureException,
            MaterialException, DocActionException {
        List<ServiceBasicKeyStructure> keyList = ServiceCollectionsHelper.asList(new ServiceBasicKeyStructure(material.getUuid(),
                MaterialStockKeepUnit.FIELD_REF_MATERIALUUID));
        List<ServiceEntityNode> materialSKUList = getEntityNodeListByKeyList(
                keyList, MaterialStockKeepUnit.NODENAME, material.getClient(),
                null);
        ServiceCollectionsHelper.traverseListInterrupt(materialSKUList, serviceEntityNode -> {
            MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) serviceEntityNode;
            if (overwriteFlag) {
                initDefaultConvertMaterialToSKU(materialStockKeepUnit, material);
            }
            try {
                updateMaterialUnitToSKU(materialStockKeepUnit, materialUnitList, overwriteFlag, serialLogonInfo);
            } catch (ServiceEntityConfigureException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
            return true;
        });
        if (overwriteFlag && !ServiceCollectionsHelper.checkNullList(materialSKUList)) {
            updateSENodeList(materialSKUList, serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
        }
        if (ServiceCollectionsHelper.checkNullList(materialSKUList)) {
            MaterialStockKeepUnit materialStockKeepUnit = newSKUFromParentMaterialCore(material, copyMaterialId, serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
            updateMaterialUnitToSKU(materialStockKeepUnit, materialUnitList, overwriteFlag, serialLogonInfo);
        }
    }

    public void updateMaterialUnitToSKU(MaterialStockKeepUnit materialStockKeepUnit, List<ServiceEntityNode> materialUnitList,
                                        boolean overwriteFlag, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException {
        if (ServiceCollectionsHelper.checkNullList(materialUnitList)) {
            return;
        }
        for (ServiceEntityNode seNode : materialUnitList) {
            MaterialUnitReference materialUnitReference = (MaterialUnitReference) seNode;
            MaterialSKUUnitReference materialSKUUnitReference = getSKUUnitFromMaterial(
                    materialUnitReference, materialStockKeepUnit);
            if (materialSKUUnitReference != null) {
                if (overwriteFlag) {
                    initDefaultConvertMaterialUnitToSKUUnit(
                            materialUnitReference, materialSKUUnitReference);
                    updateSENode(materialSKUUnitReference, serialLogonInfo.getRefUserUUID(),
                            serialLogonInfo.getResOrgUUID());
                }
            } else {
                materialSKUUnitReference = (MaterialSKUUnitReference) newEntityNode(
                        materialStockKeepUnit,
                        MaterialSKUUnitReference.NODENAME);
                initDefaultConvertMaterialUnitToSKUUnit(
                        materialUnitReference, materialSKUUnitReference);
                insertSENode(materialSKUUnitReference, serialLogonInfo.getRefUserUUID(),
                        serialLogonInfo.getResOrgUUID());
            }
        }
    }

    public MaterialStockKeepUnit newSKUFromParentMaterialCore(Material material, boolean copyMaterialId, String logonUserUUID, String organizationUUID)
            throws ServiceEntityConfigureException, MaterialException {
        // In case create a new Material SKU.
        MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) newRootEntityNode(material
                .getClient());
        initDefaultConvertMaterialToSKU(materialStockKeepUnit, material);
        String materialSKUId = this.genMaterialSKUId(materialStockKeepUnit,
                null, material, copyMaterialId);
        materialStockKeepUnit.setId(materialSKUId);
        materialStockKeepUnit.setProductionDate(new Date());
        insertSENode(materialStockKeepUnit, logonUserUUID, organizationUUID);
        return materialStockKeepUnit;
    }

    public int getMaterialCategory(String baseUUID)
            throws ServiceEntityConfigureException, MaterialException {
        MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) getEntityNodeByKey(
                baseUUID, IServiceEntityNodeFieldConstant.UUID,
                MaterialStockKeepUnit.NODENAME, null);
        Material material = (Material) materialManager.getEntityNodeByKey(
                materialStockKeepUnit.getRefMaterialUUID(),
                IServiceEntityNodeFieldConstant.UUID, Material.NODENAME, null);
        if (material == null) {
            throw new MaterialException(
                    MaterialException.PARA_NON_FOUND_MATERIAL,
                    materialStockKeepUnit.getRefMaterialUUID());
        }
        return material.getMaterialCategory();
    }

    public List<ServiceEntityNode> getRecursiveMaterialType(String baseUUID, String client) throws ServiceEntityConfigureException, MaterialException, ServiceComExecuteException {
        MaterialStockKeepUnit materialStockKeepUnit = this
                .getMaterialSKUWrapper(baseUUID, client, null);
        Material material = materialManager.getMaterialWrapper(
                materialStockKeepUnit.getRefMaterialUUID(), client, null);
        if (material == null) {
            throw new MaterialException(
                    MaterialException.PARA_NON_FOUND_MATERIAL,
                    materialStockKeepUnit.getRefMaterialUUID());
        }
        return materialTypeManager.getRecursiveMaterialTypeWrapper(material.getRefMaterialType(), client, null);
    }

    /**
     * Logic to get the relative MaterialSKUUnitReference by comparing with
     * material Unit and Material SKU.
     *
     * @param materialUnitReference
     * @param materialStockKeepUnit
     * @return
     * @throws ServiceEntityConfigureException
     */
    protected MaterialSKUUnitReference getSKUUnitFromMaterial(
            MaterialUnitReference materialUnitReference,
            MaterialStockKeepUnit materialStockKeepUnit)
            throws ServiceEntityConfigureException {
        List<ServiceBasicKeyStructure> keyList = ServiceCollectionsHelper.asList(new ServiceBasicKeyStructure(materialUnitReference.getName(), IServiceEntityNodeFieldConstant.NAME));
        keyList.add(new ServiceBasicKeyStructure(materialStockKeepUnit.getUuid(), IServiceEntityNodeFieldConstant.ROOTNODEUUID));
        return (MaterialSKUUnitReference) getEntityNodeByKeyList(
                keyList, MaterialSKUUnitReference.NODENAME, null);
    }

    /**
     * Logic to init converting initial SKU from material
     *
     * @param materialStockKeepUnit
     * @param material
     */
    public void initDefaultConvertMaterialToSKU(
            MaterialStockKeepUnit materialStockKeepUnit, Material material) {
        if (material != null && materialStockKeepUnit != null) {
            materialStockKeepUnit.setBarcode(material.getBarcode());
            DocFlowProxy.copyServiceEntityNodeMutual(material, materialStockKeepUnit, false);
            materialStockKeepUnit.setSwitchFlag(material.getSwitchFlag());
            materialStockKeepUnit.setInboundDeliveryPrice(material
                    .getInboundDeliveryPrice());
            materialStockKeepUnit.setOutboundDeliveryPrice(material
                    .getOutboundDeliveryPrice());
            materialStockKeepUnit.setProductionPlace(material
                    .getMainProductionPlace());
            materialStockKeepUnit.setMainMaterialUnit(material
                    .getMainMaterialUnit());
            materialStockKeepUnit.setPackageStandard(material
                    .getPackageStandard());
            materialStockKeepUnit.setRefMaterialUUID(material.getUuid());
            materialStockKeepUnit.setRetailPrice(material.getRetailPrice());
            materialStockKeepUnit.setRefSupplierUUID(material
                    .getRefMainSupplierUUID());
            materialStockKeepUnit.setMemberSalePrice(material
                    .getMemberSalePrice());
            materialStockKeepUnit.setWholeSalePrice(material
                    .getWholeSalePrice());
            materialStockKeepUnit.setPurchasePrice(material.getPurchasePrice());
            materialStockKeepUnit.setPackageMaterialType(material
                    .getPackageMaterialType());
            materialStockKeepUnit.setHeight(material.getHeight());
            materialStockKeepUnit.setWidth(material.getWidth());
            materialStockKeepUnit.setLength(material.getLength());
            materialStockKeepUnit.setVolume(material.getVolume());
            materialStockKeepUnit.setGrossWeight(material.getGrossWeight());
            materialStockKeepUnit.setNetWeight(material.getNetWeight());
            materialStockKeepUnit.setFixLeadTime(material.getFixLeadTime());
            materialStockKeepUnit.setVariableLeadTime(material
                    .getVariableLeadTime());
            materialStockKeepUnit.setAmountForVarLeadTime(material
                    .getAmountForVarLeadTime());
            materialStockKeepUnit.setUnitCost(material.getUnitCost());
            materialStockKeepUnit.setRefLengthUnit(material.getRefLengthUnit());
            materialStockKeepUnit.setRefVolumeUnit(material.getRefVolumeUnit());
            materialStockKeepUnit.setRefWeightUnit(material.getRefWeightUnit());
            materialStockKeepUnit.setSupplyType(material.getSupplyType());
            materialStockKeepUnit.setQualityInspectFlag(material
                    .getQualityInspectFlag());
            materialStockKeepUnit.setStatus(material.getStatus());
            materialStockKeepUnit.setOperationMode(material.getOperationMode());
        }
    }

    public void initDefaultConvertMaterialAttachmentToSKUAttachment(
            MaterialAttachment materialAttachment,
            MaterialSKUAttachment materialSKUAttachment) {
        if (materialAttachment != null && materialSKUAttachment != null) {
            materialSKUAttachment.setContent(materialAttachment.getContent());
            materialSKUAttachment.setName(materialAttachment.getName());
            materialSKUAttachment.setFileType(materialAttachment.getFileType());
            materialSKUAttachment.setNote(materialAttachment.getNote());
        }
    }

    /**
     * Logic to init converting initial SKU from material
     *
     * @param materialUnitReference
     * @param materialSKUUnitReference
     */
    public void initDefaultConvertMaterialUnitToSKUUnit(
            MaterialUnitReference materialUnitReference,
            MaterialSKUUnitReference materialSKUUnitReference) {
        if (materialUnitReference != null && materialSKUUnitReference != null) {
            DocFlowProxy.copyServiceEntityNodeMutual(materialUnitReference, materialSKUUnitReference, false);
            materialSKUUnitReference.setBarcode(materialUnitReference
                    .getBarcode());
            materialSKUUnitReference.setGrossWeight(materialUnitReference
                    .getGrossWeight());
            materialSKUUnitReference.setHeight(materialUnitReference
                    .getHeight());
            materialSKUUnitReference
                    .setInboundDeliveryPrice(materialUnitReference
                            .getInboundDeliveryPrice());
            materialSKUUnitReference
                    .setOutboundDeliveryPrice(materialUnitReference
                            .getOutboundDeliveryPrice());
            materialSKUUnitReference.setLength(materialUnitReference
                    .getLength());
            materialSKUUnitReference.setMemberSalePrice(materialUnitReference
                    .getMemberSalePrice());
            materialSKUUnitReference.setNetWeight(materialUnitReference
                    .getNetWeight());
            materialSKUUnitReference
                    .setOutPackageMaterialType(materialUnitReference
                            .getOutPackageMaterialType());
            materialSKUUnitReference.setPurchasePrice(materialUnitReference
                    .getPurchasePrice());
            materialSKUUnitReference.setRatioToStandard(materialUnitReference
                    .getRatioToStandard());
            materialSKUUnitReference.setRefUUID(materialUnitReference
                    .getRefUUID());
            materialSKUUnitReference.setRefNodeName(materialUnitReference
                    .getRefNodeName());
            materialSKUUnitReference.setRefSEName(materialUnitReference
                    .getRefSEName());
            materialSKUUnitReference.setRefPackageName(materialUnitReference
                    .getRefPackageName());
            materialSKUUnitReference.setRetailPrice(materialUnitReference
                    .getRetailPrice());
            materialSKUUnitReference.setVolume(materialUnitReference
                    .getVolume());
            materialSKUUnitReference.setUnitCost(materialUnitReference
                    .getUnitCost());
            materialSKUUnitReference.setRefLengthUnit(materialUnitReference
                    .getRefLengthUnit());
            materialSKUUnitReference.setRefVolumeUnit(materialUnitReference
                    .getRefVolumeUnit());
            materialSKUUnitReference.setRefWeightUnit(materialUnitReference
                    .getRefWeightUnit());
            materialSKUUnitReference.setUnitType(materialUnitReference
                    .getUnitType());
            materialSKUUnitReference.setWholeSalePrice(materialUnitReference
                    .getWholeSalePrice());
            materialSKUUnitReference.setWidth(materialUnitReference.getWidth());
        }
    }

    /**
     * Logic to get ref template UUID from Material SKU
     *
     * @param materialStockKeepUnit
     * @return
     */
    public static String getRefTemplateUUID(
            MaterialStockKeepUnit materialStockKeepUnit) {
        if (RegisteredProductManager.checkRegisteredProduct(materialStockKeepUnit)) {
            RegisteredProduct registeredProduct = (RegisteredProduct) materialStockKeepUnit;
            return registeredProduct.getRefMaterialSKUUUID();
        }
        return materialStockKeepUnit.getUuid();
    }

    /**
     * Logic to provide Material / Registered product identifier.
     *
     * @param materialStockKeepUnit
     * @param longFlag
     * @return
     */
    public static String getMaterialIdentifier(
            MaterialStockKeepUnit materialStockKeepUnit, boolean longFlag) {
        String materialIdentify = materialStockKeepUnit.getName();
        if (longFlag) {
            materialIdentify = materialStockKeepUnit.getId() + "-"
                    + materialStockKeepUnit.getName();
        }
        if (RegisteredProductManager.checkRegisteredProduct(materialStockKeepUnit)) {
            RegisteredProduct registeredProduct = (RegisteredProduct) materialStockKeepUnit;
            String serialId = registeredProduct.getSerialId();
            if (ServiceEntityStringHelper.checkNullString(serialId)) {
                return materialIdentify;
            } else {
                return serialId;
            }
        } else {
            return materialIdentify;
        }
    }

    /**
     * Get all material unit which belong to one specific material SKU in the format of Hashmap.
     *
     * @param materialStockKeepUnit: One specific material SKU
     * @param materialSKUUnitList    : material unit list which belong to one specific material SKU
     * @return
     * @throws ServiceEntityConfigureException
     */
    public Map<String, String> getAllUnitMapFromSKU(
            MaterialStockKeepUnit materialStockKeepUnit,
            List<ServiceEntityNode> materialSKUUnitList)
            throws ServiceEntityConfigureException {
        Map<String, String> unitMap = new HashMap<>();
        StandardMaterialUnit mainUnit = getMaterialStandardMaterialUnit(materialStockKeepUnit);
        if (mainUnit != null) {
            unitMap.put(getMainUnitUUID(materialStockKeepUnit),
                    mainUnit.getName());
        }
        if (!ServiceCollectionsHelper.checkNullList(materialSKUUnitList)) {
            for (ServiceEntityNode seNode : materialSKUUnitList) {
                MaterialSKUUnitReference materialSKUUnitReference = (MaterialSKUUnitReference) seNode;
                if (materialSKUUnitReference.getName() != null
                        && !materialSKUUnitReference.getName().equals(
                        ServiceEntityStringHelper.EMPTYSTRING)) {
                    unitMap.put(materialSKUUnitReference.getUuid(),
                            materialSKUUnitReference.getName());
                } else {
                    StandardMaterialUnit standardUnit = (StandardMaterialUnit) standardMaterialUnitManager
                            .getEntityNodeByKey(
                                    materialSKUUnitReference.getRefUUID(),
                                    IServiceEntityNodeFieldConstant.UUID,
                                    StandardMaterialUnit.NODENAME,
                                    materialStockKeepUnit.getClient(), null);
                    if (standardUnit != null) {
                        unitMap.put(materialSKUUnitReference.getUuid(),
                                standardUnit.getName());
                    }
                }
            }
        }
        return unitMap;
    }

    public StandardMaterialUnit getMaterialStandardMaterialUnit(
            MaterialStockKeepUnit materialStockKeepUnit)
            throws ServiceEntityConfigureException {
        StandardMaterialUnit mainUnit = (StandardMaterialUnit) standardMaterialUnitManager
                .getEntityNodeByKey(
                        materialStockKeepUnit.getMainMaterialUnit(),
                        IServiceEntityNodeFieldConstant.UUID,
                        StandardMaterialUnit.NODENAME,
                        materialStockKeepUnit.getClient(), null);
        return mainUnit;
    }

    public String getRefUnitUUIDByName(
            MaterialStockKeepUnit materialStockKeepUnit, String unitName)
            throws ServiceEntityConfigureException {
        if (ServiceEntityStringHelper.checkNullString(unitName)) {
            return null;
        }
        List<ServiceEntityNode> materialSKUUnitList = getEntityNodeListByKey(
                materialStockKeepUnit.getUuid(),
                IServiceEntityNodeFieldConstant.ROOTNODEUUID,
                MaterialSKUUnitReference.NODENAME,
                materialStockKeepUnit.getClient(), null);
        Map<String, String> unitMap = getAllUnitMapFromSKU(
                materialStockKeepUnit, materialSKUUnitList);
        Set<String> keySet = unitMap.keySet();
        for (String key : keySet) {
            String tmpUnitName = unitMap.get(key);
            if (unitName.equals(tmpUnitName)) {
                return key;
            }
        }
        return null;
    }

    /**
     * Logic to get main material Main Unit uuid
     *
     * @param materialStockKeepUnit
     * @return
     */
    public String getMainUnitUUID(MaterialStockKeepUnit materialStockKeepUnit) {
        if (RegisteredProductManager.checkRegisteredProduct(materialStockKeepUnit)) {
            RegisteredProduct registeredProduct = (RegisteredProduct) materialStockKeepUnit;
            return registeredProduct.getRefMaterialSKUUUID();
        }
        return materialStockKeepUnit.getUuid();
    }

    /**
     * Calculate ratio/amount to standard unit
     *
     * @return
     */
    public double ratioToMainUnit(StorageCoreUnit requestCoreUnit, MaterialStockKeepUnit templateMaterialSKU) throws ServiceEntityConfigureException, MaterialException {
        int step = 1;
        String standardRefUnit = getMainUnitUUID(templateMaterialSKU);
        StorageCoreUnit storageCoreUnit = new StorageCoreUnit(requestCoreUnit.getRefMaterialSKUUUID(),
                requestCoreUnit.getRefUnitUUID(), requestCoreUnit.getAmount());
        StorageCoreUnit standardStorageUnit = new StorageCoreUnit(templateMaterialSKU.getUuid(),
                standardRefUnit, step);
        return getStorageUnitRatio(storageCoreUnit, standardStorageUnit, templateMaterialSKU.getClient());
    }

    /**
     * Logic to get main material Main Unit uuid
     *
     * @param skuUUID
     * @return
     */
    public String getMainUnitUUID(String skuUUID) {
        return skuUUID;
    }

    /**
     * Get Material SKU instance online, from cache, or finally from DB
     * persistence
     *
     * @param uuid
     * @param client
     * @param rawMaterialList
     * @return
     * @throws ServiceEntityConfigureException
     */
    public MaterialStockKeepUnit getMaterialSKUWrapper(String uuid,
                                                       String client, List<ServiceEntityNode> rawMaterialList) throws ServiceEntityConfigureException, ServiceComExecuteException {
        return (MaterialStockKeepUnit) materialStockKeepUnitCache.loadServiceEntity(uuid,
                MaterialStockKeepUnit.NODENAME, client, false);
    }

    /**
     * Get Material SKU Unit reference instance from cache, or finally from DB
     * persistence
     *
     * @param uuid
     * @param client
     * @return
     * @throws ServiceEntityConfigureException
     */
    public MaterialSKUUnitReference getMaterialSKUUnitWrapper(String uuid,
                                                              String client) throws ServiceEntityConfigureException {

        // In case not find, trying to find from DB
        if (this.materialSKUUnitReferenceMap.containsKey(uuid)) {
            return this.materialSKUUnitReferenceMap.get(uuid);
        }
        // In case not find, then find from persistence
        MaterialSKUUnitReference materialSKUUnitReference = (MaterialSKUUnitReference) getEntityNodeByKey(
                uuid, IServiceEntityNodeFieldConstant.UUID,
                MaterialSKUUnitReference.NODENAME, client, null);
        this.materialSKUUnitReferenceMap.put(uuid, materialSKUUnitReference);
        return materialSKUUnitReference;
    }

    /**
     * Logic to calculate material SKU cost based on SKU's unit cost, and
     * request store core unit
     *
     * @param storageCoreUnit
     * @param materialStockKeepUnit
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    public double calculatePrice(StorageCoreUnit storageCoreUnit,
                                 MaterialStockKeepUnit materialStockKeepUnit, double unitPrice)
            throws MaterialException, ServiceEntityConfigureException {
        String mainUnitUUID = this.getMainUnitUUID(materialStockKeepUnit);
        StorageCoreUnit tmpCoreUnit = new StorageCoreUnit(
                materialStockKeepUnit.getUuid(), mainUnitUUID, 1);
        // Convert unit to main unit
        storageCoreUnit = mergeStorageUnitCore(storageCoreUnit, tmpCoreUnit,
                StorageCoreUnit.OPERATOR_ADD, materialStockKeepUnit.getClient());
        storageCoreUnit = mergeStorageUnitCore(storageCoreUnit, tmpCoreUnit,
                StorageCoreUnit.OPERATOR_MINUS,
                materialStockKeepUnit.getClient());
        return ServiceEntityDoubleHelper.trancateDoubleScale2(storageCoreUnit.getAmount() * unitPrice);
    }

    /**
     * Utility to compare 2 Material SKU request amount
     *
     * @param storageCoreUnit1
     * @param storageCoreUnit2
     * @param client
     * @return 1: if request1 > request 2; 0: if request1 = request 2; -1: if
     * request1 < request 2;
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    public int compareSKURequestsAmount(StorageCoreUnit storageCoreUnit1,
                                        StorageCoreUnit storageCoreUnit2, String client)
            throws MaterialException, ServiceEntityConfigureException {
        StorageCoreUnit tmpCoreUnit = (StorageCoreUnit) storageCoreUnit1
                .clone();
        tmpCoreUnit = mergeStorageUnitCore(tmpCoreUnit, storageCoreUnit2,
                StorageCoreUnit.OPERATOR_MINUS, client);
        if (tmpCoreUnit.getAmount() < 0) {
            return -1;
        }
        if (tmpCoreUnit.getAmount() > 0) {
            return 1;
        }
        return 0;
    }

    public void convMaterialSKUToUI(
            MaterialStockKeepUnit materialStockKeepUnit,
            MaterialStockKeepUnitUIModel materialStockKeepUnitUIModel) {
        convMaterialSKUToUI(materialStockKeepUnit,
                materialStockKeepUnitUIModel, null);
    }

    public void convMaterialSKUToUI(
            MaterialStockKeepUnit materialStockKeepUnit,
            MaterialStockKeepUnitUIModel materialStockKeepUnitUIModel, LogonInfo logonInfo) {
        convMaterialSKUToUI(materialStockKeepUnit,
                materialStockKeepUnitUIModel, null, logonInfo);
    }

    public void convMaterialSKUToUI(
            MaterialStockKeepUnit materialStockKeepUnit,
            MaterialStockKeepUnitUIModel materialStockKeepUnitUIModel, List<ServiceEntityNode> standardMaterialUnitList,
            LogonInfo logonInfo) {
        if (materialStockKeepUnit != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(materialStockKeepUnit, materialStockKeepUnitUIModel);
            materialStockKeepUnitUIModel.setStatus(materialStockKeepUnit
                    .getStatus());
            materialStockKeepUnitUIModel
                    .setProductionPlace(materialStockKeepUnit
                            .getProductionPlace());
            if (!ServiceCollectionsHelper.checkNullList(standardMaterialUnitList)) {
                try {
                    materialManager.setStandardUnitValueToUIModel(standardMaterialUnitList, materialStockKeepUnit, materialStockKeepUnitUIModel);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    logger.error(ServiceEntityStringHelper
                            .genDefaultErrorMessage(e, ""));
                }
            }
            materialStockKeepUnitUIModel.setSwitchFlag(materialStockKeepUnit
                    .getSwitchFlag());
            materialStockKeepUnitUIModel.setBarcode(materialStockKeepUnit
                    .getBarcode());
            materialStockKeepUnitUIModel
                    .setSwitchFlagShow(materialStockKeepUnit.getSwitchFlag());
            materialStockKeepUnitUIModel
                    .setMainMaterialUnit(materialStockKeepUnit
                            .getMainMaterialUnit());
            materialStockKeepUnitUIModel.setLength(materialStockKeepUnit
                    .getLength());
            materialStockKeepUnitUIModel.setWidth(materialStockKeepUnit
                    .getWidth());
            materialStockKeepUnitUIModel.setHeight(materialStockKeepUnit
                    .getHeight());
            materialStockKeepUnitUIModel.setNetWeight(materialStockKeepUnit
                    .getNetWeight());
            materialStockKeepUnitUIModel.setGrossWeight(materialStockKeepUnit
                    .getGrossWeight());
            materialStockKeepUnitUIModel.setVolume(materialStockKeepUnit
                    .getVolume());
            materialStockKeepUnitUIModel
                    .setRefMaterialUUID(materialStockKeepUnit
                            .getRefMaterialUUID());
            materialStockKeepUnitUIModel
                    .setProductionDate(materialStockKeepUnit
                            .getProductionDate());
            materialStockKeepUnitUIModel
                    .setInboundDeliveryPrice(materialStockKeepUnit
                            .getInboundDeliveryPrice());
            materialStockKeepUnitUIModel
                    .setOutboundDeliveryPrice(materialStockKeepUnit
                            .getOutboundDeliveryPrice());
            materialStockKeepUnitUIModel
                    .setRefSupplierUUID(materialStockKeepUnit
                            .getRefSupplierUUID());
            materialStockKeepUnitUIModel
                    .setPackageStandard(materialStockKeepUnit
                            .getPackageStandard());
            if (logonInfo != null) {
                try {
                    Map<Integer, String> packageMaterialTypeMap = this
                            .initPackageMaterialType(
                                    materialStockKeepUnit.getClient(), false);
                    materialStockKeepUnitUIModel
                            .setPackageMaterialTypeValue(packageMaterialTypeMap
                                    .get(materialStockKeepUnit
                                            .getPackageMaterialType()));
                    Map<Integer, String> traceLevelMap = this
                            .initTraceLevelMap(logonInfo.getLanguageCode());
                    materialStockKeepUnitUIModel
                            .setTraceLevelValue(traceLevelMap
                                    .get(materialStockKeepUnit.getTraceLevel()));
                    Map<Integer, String> traceModeMap = this
                            .initTraceModeMap(logonInfo.getLanguageCode());
                    materialStockKeepUnitUIModel.setTraceModeValue(traceModeMap
                            .get(materialStockKeepUnit.getTraceMode()));
                    Map<Integer, String> traceStatusMap = this
                            .initTraceStatusMap(logonInfo.getLanguageCode());
                    materialStockKeepUnitUIModel
                            .setTraceStatusValue(traceStatusMap
                                    .get(materialStockKeepUnit.getTraceStatus()));
                    Map<Integer, String> qualityInspectFlagMap = initMaterialQualityInspectFlagMap(logonInfo
                            .getLanguageCode());
                    materialStockKeepUnitUIModel
                            .setQualityInspectValue(qualityInspectFlagMap
                                    .get(materialStockKeepUnit
                                            .getQualityInspectFlag()));
                    Map<Integer, String> statusMap = materialManager.initStatusMap(logonInfo
                            .getLanguageCode());
                    materialStockKeepUnitUIModel
                            .setStatusValue(statusMap
                                    .get(materialStockKeepUnit
                                            .getStatus()));
                    Map<Integer, String> supplyTypeMap = initSupplyTypeMap(logonInfo
                            .getLanguageCode());
                    materialStockKeepUnitUIModel
                            .setSupplyTypeValue(supplyTypeMap
                                    .get(materialStockKeepUnit.getSupplyType()));
                    Map<Integer, String> switchMap = this
                            .initSwitchMap(logonInfo.getLanguageCode());
                    materialStockKeepUnitUIModel.setSwitchFlagValue(switchMap
                            .get(materialStockKeepUnit.getSwitchFlag()));
                } catch (ServiceEntityInstallationException e) {
                    // do nothing
                    logger.error(ServiceEntityStringHelper
                            .genDefaultErrorMessage(e, "packageMaterialType"));
                }
            }
            materialStockKeepUnitUIModel
                    .setQualityInspectFlag(materialStockKeepUnit
                            .getQualityInspectFlag());
            materialStockKeepUnitUIModel
                    .setPackageStandard(materialStockKeepUnit
                            .getPackageStandard());
            materialStockKeepUnitUIModel.setRetailPrice(materialStockKeepUnit
                    .getRetailPrice());
            materialStockKeepUnitUIModel
                    .setMemberSalePrice(materialStockKeepUnit
                            .getMemberSalePrice());
            materialStockKeepUnitUIModel.setPurchasePrice(materialStockKeepUnit
                    .getPurchasePrice());
            materialStockKeepUnitUIModel
                    .setWholeSalePrice(materialStockKeepUnit
                            .getWholeSalePrice());
            materialStockKeepUnitUIModel
                    .setPackageMaterialType(materialStockKeepUnit
                            .getPackageMaterialType());
            materialStockKeepUnitUIModel
                    .setMinStoreNumber(materialStockKeepUnit
                            .getMinStoreNumber());
            materialStockKeepUnitUIModel
                    .setProductionBatchNumber(materialStockKeepUnit
                            .getProductionBatchNumber());
            materialStockKeepUnitUIModel.setCargoType(materialStockKeepUnit
                    .getCargoType());
            materialStockKeepUnitUIModel.setFixLeadTime(materialStockKeepUnit
                    .getFixLeadTime());
            materialStockKeepUnitUIModel
                    .setVariableLeadTime(materialStockKeepUnit
                            .getVariableLeadTime());
            materialStockKeepUnitUIModel
                    .setAmountForVarLeadTime(materialStockKeepUnit
                            .getAmountForVarLeadTime());
            materialStockKeepUnitUIModel.setSupplyType(materialStockKeepUnit
                    .getSupplyType());
            materialStockKeepUnitUIModel.setUnitCost(materialStockKeepUnit
                    .getUnitCost());
            materialStockKeepUnitUIModel.setPurchasePriceDisplay(materialStockKeepUnit
                    .getPurchasePriceDisplay());
            materialStockKeepUnitUIModel.setRetailPriceDisplay(materialStockKeepUnit
                    .getRetailPriceDisplay());
            materialStockKeepUnitUIModel.setUnitCostDisplay(materialStockKeepUnit
                    .getUnitCostDisplay());
            materialStockKeepUnitUIModel.setRefLengthUnit(materialStockKeepUnit
                    .getRefLengthUnit());
            materialStockKeepUnitUIModel.setRefVolumeUnit(materialStockKeepUnit
                    .getRefVolumeUnit());
            materialStockKeepUnitUIModel.setRefWeightUnit(materialStockKeepUnit
                    .getRefWeightUnit());
            materialStockKeepUnitUIModel.setTraceLevel(materialStockKeepUnit
                    .getTraceLevel());
            materialStockKeepUnitUIModel.setTraceStatus(materialStockKeepUnit
                    .getTraceStatus());
            materialStockKeepUnitUIModel
                    .setRefTemplateUUID(materialStockKeepUnit
                            .getRefTemplateUUID());
            materialStockKeepUnitUIModel.setTraceMode(materialStockKeepUnit
                    .getTraceMode());
            materialStockKeepUnitUIModel.setOperationMode(materialStockKeepUnit
                    .getOperationMode());
        }
    }

    public void convUIToMaterialSKU(
            MaterialStockKeepUnitUIModel materialStockKeepUnitUIModel,
            MaterialStockKeepUnit rawEntity) {
        DocFlowProxy.convUIToServiceEntityNode(materialStockKeepUnitUIModel, rawEntity);
        if (!ServiceEntityStringHelper
                .checkNullString(materialStockKeepUnitUIModel
                        .getRefMaterialUUID())) {
            rawEntity.setRefMaterialUUID(materialStockKeepUnitUIModel
                    .getRefMaterialUUID());
        }
        rawEntity.setProductionPlace(materialStockKeepUnitUIModel
                .getProductionPlace());
        rawEntity.setProductionBatchNumber(materialStockKeepUnitUIModel
                .getProductionBatchNumber());
        rawEntity.setBarcode(materialStockKeepUnitUIModel.getBarcode());
        rawEntity.setMainMaterialUnit(materialStockKeepUnitUIModel
                .getMainMaterialUnit());
        rawEntity.setLength(materialStockKeepUnitUIModel.getLength());
        rawEntity.setWidth(materialStockKeepUnitUIModel.getWidth());
        rawEntity.setHeight(materialStockKeepUnitUIModel.getHeight());
        rawEntity.setNetWeight(materialStockKeepUnitUIModel.getNetWeight());
        rawEntity.setGrossWeight(materialStockKeepUnitUIModel.getGrossWeight());
        rawEntity.setVolume(materialStockKeepUnitUIModel.getVolume());
        rawEntity.setInboundDeliveryPrice(materialStockKeepUnitUIModel
                .getInboundDeliveryPrice());
        rawEntity.setOutboundDeliveryPrice(materialStockKeepUnitUIModel
                .getOutboundDeliveryPrice());
        rawEntity.setRefSupplierUUID(materialStockKeepUnitUIModel
                .getRefSupplierUUID());
        rawEntity.setPackageStandard(materialStockKeepUnitUIModel
                .getPackageStandard());
        rawEntity.setPackageStandard(materialStockKeepUnitUIModel
                .getPackageStandard());
        rawEntity.setRetailPrice(materialStockKeepUnitUIModel.getRetailPrice());
        rawEntity.setMemberSalePrice(materialStockKeepUnitUIModel
                .getMemberSalePrice());
        rawEntity.setPurchasePrice(materialStockKeepUnitUIModel
                .getPurchasePrice());
        rawEntity.setPurchasePriceDisplay(materialStockKeepUnitUIModel
                .getPurchasePriceDisplay());
        rawEntity.setRetailPriceDisplay(materialStockKeepUnitUIModel
                .getRetailPriceDisplay());
        rawEntity.setUnitCostDisplay(materialStockKeepUnitUIModel
                .getUnitCostDisplay());
        rawEntity.setWholeSalePrice(materialStockKeepUnitUIModel
                .getWholeSalePrice());
        rawEntity.setPackageMaterialType(materialStockKeepUnitUIModel
                .getPackageMaterialType());
        rawEntity.setMinStoreNumber(materialStockKeepUnitUIModel
                .getMinStoreNumber());
        rawEntity.setCargoType(materialStockKeepUnitUIModel.getCargoType());
        rawEntity.setFixLeadTime(materialStockKeepUnitUIModel.getFixLeadTime());
        rawEntity.setVariableLeadTime(materialStockKeepUnitUIModel
                .getVariableLeadTime());
        rawEntity.setAmountForVarLeadTime(materialStockKeepUnitUIModel
                .getAmountForVarLeadTime());
        rawEntity.setUnitCost(materialStockKeepUnitUIModel.getUnitCost());
        if (!ServiceEntityStringHelper
                .checkNullString(materialStockKeepUnitUIModel
                        .getRefLengthUnit())) {
            rawEntity.setRefLengthUnit(materialStockKeepUnitUIModel
                    .getRefLengthUnit());
        }
        if (!ServiceEntityStringHelper
                .checkNullString(materialStockKeepUnitUIModel
                        .getRefVolumeUnit())) {
            rawEntity.setRefVolumeUnit(materialStockKeepUnitUIModel
                    .getRefVolumeUnit());
        }
        if (!ServiceEntityStringHelper
                .checkNullString(materialStockKeepUnitUIModel
                        .getRefWeightUnit())) {
            rawEntity.setRefWeightUnit(materialStockKeepUnitUIModel
                    .getRefWeightUnit());
        }
        if (materialStockKeepUnitUIModel.getSupplyType() != 0) {
            rawEntity.setSupplyType(materialStockKeepUnitUIModel
                    .getSupplyType());
        }
        if (materialStockKeepUnitUIModel.getTraceLevel() != 0) {
            rawEntity.setTraceLevel(materialStockKeepUnitUIModel
                    .getTraceLevel());
        }
        if (materialStockKeepUnitUIModel.getTraceStatus() != 0) {
            rawEntity.setTraceStatus(materialStockKeepUnitUIModel
                    .getTraceStatus());
        }
        if (materialStockKeepUnitUIModel.getQualityInspectFlag() != 0) {
            rawEntity.setQualityInspectFlag(materialStockKeepUnitUIModel
                    .getQualityInspectFlag());
        }
        if (materialStockKeepUnitUIModel.getTraceMode() != 0) {
            rawEntity.setTraceMode(materialStockKeepUnitUIModel.getTraceMode());
        }
        if (!ServiceEntityStringHelper
                .checkNullString(materialStockKeepUnitUIModel
                        .getRefTemplateUUID())) {
            rawEntity.setRefTemplateUUID(materialStockKeepUnitUIModel
                    .getRefTemplateUUID());
        }
    }

    public void convMaterialToUI(Material material,
                                 MaterialStockKeepUnitUIModel materialStockKeepUnitUIModel)
            throws ServiceEntityInstallationException {
        convMaterialToUI(material, materialStockKeepUnitUIModel, null);
    }

    public void convMaterialToUI(Material material,
                                 MaterialStockKeepUnitUIModel materialStockKeepUnitUIModel,
                                 LogonInfo logonInfo) throws ServiceEntityInstallationException {
        if (material != null) {
            materialStockKeepUnitUIModel.setRefMaterialType(material
                    .getRefMaterialType());
            if (logonInfo != null) {
                Map<Integer, String> materialCateogryMap = initMaterialCategoryMap(logonInfo
                        .getLanguageCode());
                materialStockKeepUnitUIModel
                        .setMaterialCategoryValue(materialCateogryMap
                                .get(material.getMaterialCategory()));
            }
            this.initCargoTypeMap();
            materialStockKeepUnitUIModel.setCargoType(material.getCargoType());
            materialStockKeepUnitUIModel.setCargoTypeValue(this.cargoTypeMap
                    .get(material.getCargoType()));
            materialStockKeepUnitUIModel.setRefMaterialName(material.getName());
            materialStockKeepUnitUIModel.setRefMaterialId(material.getId());
            materialStockKeepUnitUIModel.setMaterialCategory(material
                    .getMaterialCategory());
        }
    }

    public void convCorporateSupplierToUI(CorporateCustomer corporateCustomer,
                                          MaterialStockKeepUnitUIModel materialStockKeepUnitUIModel) {
        if (corporateCustomer != null) {
            materialStockKeepUnitUIModel.setSupplierName(corporateCustomer
                    .getName());
        }
    }

    public void convMaterialTypeToUI(MaterialType materialType,
                                     MaterialStockKeepUnitUIModel materialStockKeepUnitUIModel)
            throws ServiceEntityConfigureException {
        if (materialType != null) {

            materialStockKeepUnitUIModel
                    .setMaterialTypeId(materialType.getId());
            materialStockKeepUnitUIModel.setMaterialTypeName(materialType
                    .getName());
            String systemMatTypeID = materialManager
                    .getSystemMaterialTypeID(materialType);
            materialStockKeepUnitUIModel.setSystemMatTypeID(systemMatTypeID);
        }
    }

    public void convStandardMaterialUnitToUI(
            StandardMaterialUnit standardMaterialUnit,
            MaterialSKUUnitUIModel materialSKUUnitUIModel) {
        if (standardMaterialUnit != null) {
            // materialSKUUnitUIModel.setUnitName(standardMaterialUnit.getName());
        }
    }

    public void convStandardMaterialUnitToPropertyUI(
            StandardMaterialUnit standardMaterialUnit,
            MaterialSKUExtendPropertyUIModel materialSKUExtendPropertyUIModel) {
        if (standardMaterialUnit != null) {
            materialSKUExtendPropertyUIModel
                    .setRefUnitValue(standardMaterialUnit.getName());
        }
    }

    public void convMaterialSKUExtendPropertyToUI(
            MaterialSKUExtendProperty materialSKUExtendProperty,
            MaterialSKUExtendPropertyUIModel materialSKUExtendPropertyUIModel)
            throws ServiceEntityInstallationException {
        convMaterialSKUExtendPropertyToUI(materialSKUExtendProperty,
                materialSKUExtendPropertyUIModel, null);
    }

    public void convMaterialSKUExtendPropertyToUI(
            MaterialSKUExtendProperty materialSKUExtendProperty,
            MaterialSKUExtendPropertyUIModel materialSKUExtendPropertyUIModel,
            LogonInfo logonInfo) throws ServiceEntityInstallationException {
        if (materialSKUExtendProperty != null) {
            materialSKUExtendPropertyUIModel
                    .setRefValueSettingUUID(materialSKUExtendProperty
                            .getRefValueSettingUUID());
            materialSKUExtendPropertyUIModel.setId(materialSKUExtendProperty
                    .getId());
            materialSKUExtendPropertyUIModel.setName(materialSKUExtendProperty
                    .getName());
            materialSKUExtendPropertyUIModel
                    .setDoubleValue(materialSKUExtendProperty.getDoubleValue());
            materialSKUExtendPropertyUIModel
                    .setIntValue(materialSKUExtendProperty.getIntValue());
            materialSKUExtendPropertyUIModel
                    .setQualityInspectFlag(materialSKUExtendProperty
                            .getQualityInspectFlag());

            materialSKUExtendPropertyUIModel
                    .setMeasureFlag(materialSKUExtendProperty.getMeasureFlag());
            if (logonInfo != null) {
                Map<Integer, String> qualityInspectMap = this
                        .initQualityInspectMap(logonInfo.getLanguageCode());
                materialSKUExtendPropertyUIModel
                        .setQualityInspectFlagValue(qualityInspectMap
                                .get(materialSKUExtendProperty
                                        .getQualityInspectFlag()));
                Map<Integer, String> measureFlagMap = this
                        .initMeasureFlagMap(logonInfo.getLanguageCode());
                materialSKUExtendPropertyUIModel
                        .setMeasureFlagValue(measureFlagMap
                                .get(materialSKUExtendProperty.getMeasureFlag()));
            }
            materialSKUExtendPropertyUIModel
                    .setRefUnitUUID(materialSKUExtendProperty.getRefUnitUUID());

            materialSKUExtendPropertyUIModel
                    .setStringValue(materialSKUExtendProperty.getStringValue());
        }
    }

    public void convUIToMaterialSKUExtendProperty(
            MaterialSKUExtendPropertyUIModel materialSKUExtendPropertyUIModel,
            MaterialSKUExtendProperty rawEntity) {
        if (rawEntity != null) {
            rawEntity.setRefValueSettingUUID(materialSKUExtendPropertyUIModel
                    .getRefValueSettingUUID());
            rawEntity.setDoubleValue(materialSKUExtendPropertyUIModel
                    .getDoubleValue());
            rawEntity.setIntValue(materialSKUExtendPropertyUIModel
                    .getIntValue());
            rawEntity.setQualityInspectFlag(materialSKUExtendPropertyUIModel
                    .getQualityInspectFlag());
            rawEntity.setMeasureFlag(materialSKUExtendPropertyUIModel
                    .getMeasureFlag());
            rawEntity.setRefUnitUUID(materialSKUExtendPropertyUIModel
                    .getRefUnitUUID());
            rawEntity.setStringValue(materialSKUExtendPropertyUIModel
                    .getStringValue());
        }
    }

    public void admDeleteComMaterialSKUUnion(
            MaterialStockKeepUnit materialStockKeepUnit)
            throws ServiceEntityConfigureException {
        /**
         * Find material SE model and
         */
        List<ServiceEntityNode> materialSKUUnitList = getEntityNodeListByKey(
                materialStockKeepUnit.getUuid(),
                IServiceEntityNodeFieldConstant.ROOTNODEUUID,
                MaterialSKUUnitReference.NODENAME,
                materialStockKeepUnit.getClient(), null);
        if (materialSKUUnitList != null && materialSKUUnitList.size() > 0) {
            deleteSENode(materialSKUUnitList,
                    materialStockKeepUnit.getResEmployeeUUID(), null);
        }
        deleteSENode(materialStockKeepUnit,
                materialStockKeepUnit.getResEmployeeUUID(), null);
    }

    /**
     * Default Logic to calculate if current material request could be split
     * able by checking if amount is larger than 1 standard unit.
     *
     * @param storageCoreUnit
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    public int calculateSplitFlag(StorageCoreUnit storageCoreUnit, String client)
            throws MaterialException, ServiceEntityConfigureException {
        String refUnitUUID = storageCoreUnit.getRefUnitUUID();
        MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) getEntityNodeByKey(
                storageCoreUnit.getRefMaterialSKUUUID(),
                IServiceEntityNodeFieldConstant.UUID,
                MaterialStockKeepUnit.NODENAME, client, null);
        if (materialStockKeepUnit == null) {
            // In case new status item
            return StandardSwitchProxy.SWITCH_OFF;
        }
        String mainUnitUUID = getMainUnitUUID(materialStockKeepUnit);
        if (ServiceEntityStringHelper.checkNullString(refUnitUUID)) {
            refUnitUUID = mainUnitUUID;
        }
        /*
         * [Step2] In case Main Unit
         */
        if (refUnitUUID.equals(mainUnitUUID)) {
            if (storageCoreUnit.getAmount() > 1) {
                return StandardSwitchProxy.SWITCH_ON;
            } else {
                return StandardSwitchProxy.SWITCH_OFF;
            }
        }
        /*
         * [Step3] In case other Unit, compare with 1 standard Unit
         */
        StorageCoreUnit itemUnit = new StorageCoreUnit(
                storageCoreUnit.getRefMaterialSKUUUID(), refUnitUUID,
                storageCoreUnit.getAmount());
        StorageCoreUnit standardUnit = new StorageCoreUnit(
                storageCoreUnit.getRefMaterialSKUUUID(), mainUnitUUID, 1);
        int compareResult = compareSKURequestsAmount(itemUnit, standardUnit,
                client);
        if (compareResult > 0) {
            return StandardSwitchProxy.SWITCH_ON;
        } else {
            return StandardSwitchProxy.SWITCH_OFF;
        }
    }

    @Override
    public ServiceSearchProxy getSearchProxy() {
        return materialStockKeepUnitSearchProxy;
    }

}
