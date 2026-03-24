package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.WarehouseSearchModel;
import com.company.IntelligentPlatform.common.dto.WarehouseStoreSettingUIModel;
import com.company.IntelligentPlatform.common.dto.WarehouseUIModel;
// TODO-DAO: import ...WarehouseDAO;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Logic Manager CLASS FOR Service Entity [Warehouse]
 *
 * @author
 * @date Sat Nov 23 23:19:06 CST 2013
 * <p>
 * This class is generated automatically by platform automation register
 * tool
 */
@Service
@Transactional
public class WarehouseManager extends ServiceEntityManager {

    public static final String METHOD_ConvWarehouseToStoreSettingUI = "convWarehouseToStoreSettingUI";

    public static final String METHOD_ConvMaterialSKUToStoreSettingUI = "convMaterialSKUToStoreSettingUI";

    public static final String METHOD_ConvUIToWarehouse = "convUIToWarehouse";

    public static final String METHOD_ConvWarehouseToUI = "convWarehouseToUI";

    public static final String METHOD_ConvWarehouseStoreSettingToUI = "convWarehouseStoreSettingToUI";

    public static final String METHOD_ConvUIToWarehouseStoreSetting = "convUIToWarehouseStoreSetting";

    public static final String METHOD_ConvParentOrganizationToUI = "convParentOrganizationToUI";

    // TODO-DAO: @Autowired

    // TODO-DAO:     protected WarehouseDAO warehouseDAO;

    @Autowired
    protected WarehouseConfigureProxy warehouseConfigureProxy;

    @Autowired
    protected StandardSwitchProxy standardSwitchProxy;

    @Autowired
    protected StandardSystemDefaultProxy standardSystemDefaultProxy;

    @Autowired
    protected WarehouseIdHelper warehouseIdHelper;

    @Autowired
    protected WarehouseAreaIdHelper warehouseAreaIdHelper;

    @Autowired
    protected WarehouseSearchProxy warehouseSearchProxy;

    final Logger logger = LoggerFactory.getLogger(WarehouseManager.class);

    private final Map<String, Map<Integer, String>> operationModeMapLan = new HashMap<>();

    private final Map<String, Map<Integer, String>> storeCalculateMapLan = new HashMap<>();

    private final Map<String, Map<Integer, String>> dataSourceTypeMapLan = new HashMap<>();

    private final Map<String, Map<Integer, String>> systemDefaultMapLan = new HashMap<>();

    protected Map<String, Map<Integer, String>> refMaterialCategoryMapLan;

    public Map<Integer, String> initRefMaterialCategory(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.refMaterialCategoryMapLan, WarehouseUIModel.class,
                "refMaterialCategory");
    }

    public Map<Integer, String> initOperationModeMap(String languageCode) throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.operationModeMapLan, WarehouseUIModel.class,
                "operationMode");
    }

    public Map<Integer, String> initSafeStoreCalculateMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.storeCalculateMapLan, WarehouseUIModel.class,
                "safeStoreCalculateCategory");
    }

    public Map<Integer, String> initSwitchFlagMap(String languageCode)
            throws ServiceEntityInstallationException {
        return standardSwitchProxy.getSwitchMap(languageCode);
    }

    public Map<Integer, String> initSystemDefaultMap(String languageCode)
            throws ServiceEntityInstallationException {
        return standardSystemDefaultProxy.getSystemDefaultMap(languageCode);
    }

    public Map<Integer, String> initDataSourceTypeMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.dataSourceTypeMapLan, WarehouseStoreSettingUIModel.class,
                "dataSourceType");
    }


    public WarehouseManager() {
        super.seConfigureProxy = new WarehouseConfigureProxy();
        // TODO-DAO: super.serviceEntityDAO = new WarehouseDAO();
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        // TODO-DAO: super.setServiceEntityDAO(warehouseDAO);
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(warehouseConfigureProxy);
    }


    @Override
    public ServiceEntityNode newRootEntityNode(String client)
            throws ServiceEntityConfigureException {
        Warehouse warehouse = (Warehouse) super.newRootEntityNode(client);
        String warehouseId = warehouseIdHelper.genDefaultId(client);
        warehouse.setId(warehouseId);
        return warehouse;
    }

    @Override
    public ServiceEntityNode newEntityNode(ServiceEntityNode parentNode,
                                           String nodeName) throws ServiceEntityConfigureException {
        if (WarehouseArea.NODENAME.equals(nodeName)) {
            WarehouseArea warehouseArea = (WarehouseArea) super.newEntityNode(
                    parentNode, nodeName);
            String warehouseAreaId = warehouseAreaIdHelper
                    .genDefaultId(parentNode.getClient());
            warehouseArea.setId(warehouseAreaId);
            return warehouseArea;
        }
        return super.newEntityNode(parentNode, nodeName);
    }

    public String getDefWarehouseAndAreaLabel(String warehouseUUID,
                                              String warehouseAreaUUID, String client)
            throws ServiceEntityConfigureException {
        String label = ServiceEntityStringHelper.EMPTYSTRING;
        Warehouse warehouse = (Warehouse) getEntityNodeByKey(warehouseUUID,
                IServiceEntityNodeFieldConstant.UUID, Warehouse.NODENAME,
                client, null);
        if (warehouse == null) {
            return null;
        } else {
            label = warehouse.getName();
        }
        WarehouseArea warehouseArea = (WarehouseArea) getEntityNodeByKey(
                warehouseAreaUUID, IServiceEntityNodeFieldConstant.UUID,
                WarehouseArea.NODENAME, client, null);
        if (warehouseArea != null) {
            return label + "-" + warehouseArea.getId();
        } else {
            return label;
        }
    }

    /**
     * Logic to get the system default warehouse
     *
     * @return
     * @throws SearchConfigureException
     * @throws ServiceEntityConfigureException
     * @throws NodeNotFoundException
     * @throws ServiceEntityInstallationException
     */
    public Warehouse getDefaultWarehouse(LogonInfo logonInfo)
            throws SearchConfigureException, ServiceEntityConfigureException, ServiceEntityInstallationException, AuthorizationException, LogonInfoException {
        WarehouseSearchModel warehouseSearchModel = new WarehouseSearchModel();
        SearchContextBuilder searchContextBuilder = SearchContextBuilder.genBuilder(logonInfo).searchModel(warehouseSearchModel);
        List<ServiceEntityNode> rawWarehouseList = this.getSearchProxy().searchDocList(searchContextBuilder.build()).getResultList();
        if (!ServiceCollectionsHelper.checkNullList(rawWarehouseList)) {
            return (Warehouse) rawWarehouseList.get(0);
        }
        return null;
    }

    /**
     * Logic to delete ware house instance
     *
     * @param warehouseUUID
     * @throws ServiceEntityConfigureException
     * @throws WarehouseSettingException
     */
    public void deleteWarehouse(String warehouseUUID, String logonUserUUID,
                                String organizationUUID, String client)
            throws ServiceEntityConfigureException, WarehouseSettingException {
        /**
         * [Step1] Pre-check before delete
         */
        Warehouse warehouse = (Warehouse) this.getEntityNodeByKey(
                warehouseUUID, IServiceEntityNodeFieldConstant.UUID,
                Warehouse.NODENAME, client, null);
        if (warehouse == null) {
            throw new WarehouseSettingException(
                    WarehouseSettingException.TYPE_SYSTEM_WRONG);
        }
        /*
         * [Step3] delete all the sub warehouse area
         */
        deleteEntityNodeListByKey(warehouseUUID,
                IServiceEntityNodeFieldConstant.PARENTNODEUUID,
                WarehouseArea.NODENAME, warehouse.getClient(), true);
        /*
         * [Step4] delete this warehouse instance
         */
        this.deleteSENode(warehouse, logonUserUUID, organizationUUID);
    }

    public void convMaterialSKUToSettingUI(
            MaterialStockKeepUnit materialStockKeepUnit,
            WarehouseStoreSettingUIModel warehouseStoreSettingUIModel) {
        if (materialStockKeepUnit != null) {
            warehouseStoreSettingUIModel
                    .setRefMaterialSKUName(materialStockKeepUnit.getName());
            warehouseStoreSettingUIModel
                    .setRefMaterialSKUId(materialStockKeepUnit.getId());
            warehouseStoreSettingUIModel
                    .setRefMaterialSKUUUID(materialStockKeepUnit.getUuid());
        }
    }

    public void convWarehouseStoreSettingToUI(
            WarehouseStoreSetting warehouseStoreSetting,
            WarehouseStoreSettingUIModel warehouseStoreSettingUIModel)
            throws ServiceEntityInstallationException {
        convWarehouseStoreSettingToUI(warehouseStoreSetting, warehouseStoreSettingUIModel, null);
    }

    public void convWarehouseStoreSettingToUI(
            WarehouseStoreSetting warehouseStoreSetting,
            WarehouseStoreSettingUIModel warehouseStoreSettingUIModel, LogonInfo logonInfo) {
        if (warehouseStoreSetting != null) {
            warehouseStoreSettingUIModel.setBaseUUID(warehouseStoreSetting
                    .getParentNodeUUID());
            DocFlowProxy.convServiceEntityNodeToUIModel(warehouseStoreSetting,
                    warehouseStoreSettingUIModel);
            warehouseStoreSettingUIModel
                    .setRefMaterialSKUUUID(warehouseStoreSetting
                            .getRefMaterialSKUUUID());
            warehouseStoreSettingUIModel
                    .setMaxSafeStoreAmount(warehouseStoreSetting
                            .getMaxSafeStoreAmount());
            warehouseStoreSettingUIModel
                    .setMaxSafeStoreUnitUUID(warehouseStoreSetting
                            .getMaxSafeStoreUnitUUID());
            warehouseStoreSettingUIModel
                    .setMinSafeStoreAmount(warehouseStoreSetting
                            .getMinSafeStoreAmount());
            warehouseStoreSettingUIModel
                    .setMinSafeStoreUnitUUID(warehouseStoreSetting
                            .getMinSafeStoreUnitUUID());
            warehouseStoreSettingUIModel
                    .setSafeStoreCalculateCategory(warehouseStoreSetting
                            .getSafeStoreCalculateCategory());
            if (logonInfo != null) {
                try {
                    Map<Integer, String> storeCalculateMap = this
                            .initSafeStoreCalculateMap(logonInfo.getLanguageCode());
                    warehouseStoreSettingUIModel
                            .setSafeStoreCalculateCategoryValue(storeCalculateMap
                                    .get(warehouseStoreSetting
                                            .getSafeStoreCalculateCategory()));
                    Map<Integer, String> dataSourceTypeMap = this
                            .initDataSourceTypeMap(logonInfo.getLanguageCode());
                    warehouseStoreSettingUIModel
                            .setDataSourceTypeValue(dataSourceTypeMap
                                    .get(warehouseStoreSetting.getDataSourceType()));
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper
                            .genDefaultErrorMessage(e, "storeCalculateMap"));
                }
            }
            warehouseStoreSettingUIModel.setMaxStoreRatio(warehouseStoreSetting
                    .getMaxStoreRatio());
            warehouseStoreSettingUIModel.setMinStoreRatio(warehouseStoreSetting
                    .getMinStoreRatio());
            warehouseStoreSettingUIModel
                    .setTargetAverageStoreAmount(warehouseStoreSetting
                            .getTargetAverageStoreAmount());
            warehouseStoreSettingUIModel
                    .setTargetAverageStoreUnitUUID(warehouseStoreSetting
                            .getTargetAverageStoreUnitUUID());
            warehouseStoreSettingUIModel
                    .setDataSourceType(warehouseStoreSetting
                            .getDataSourceType());
            warehouseStoreSettingUIModel.setRefUUID(warehouseStoreSetting
                    .getRefUUID());
        }
    }

    public void convWarehouseToStoreSettingUI(Warehouse warehouse,
                                              WarehouseStoreSettingUIModel warehouseStoreSettingUIModel) {
        if (warehouseStoreSettingUIModel != null && warehouse != null) {
            warehouseStoreSettingUIModel.setRefWarehouseId(warehouse.getId());
        }
    }

    public void convUIToWarehouseStoreSetting(
            WarehouseStoreSettingUIModel warehouseStoreSettingUIModel,
            WarehouseStoreSetting rawEntity) {
        DocFlowProxy.convUIToServiceEntityNode(warehouseStoreSettingUIModel,
                rawEntity);
        rawEntity.setRefMaterialSKUUUID(warehouseStoreSettingUIModel
                .getRefMaterialSKUUUID());
        rawEntity.setMaxSafeStoreAmount(warehouseStoreSettingUIModel
                .getMaxSafeStoreAmount());
        rawEntity.setMaxSafeStoreUnitUUID(warehouseStoreSettingUIModel
                .getMaxSafeStoreUnitUUID());
        rawEntity.setMinSafeStoreAmount(warehouseStoreSettingUIModel
                .getMinSafeStoreAmount());
        rawEntity.setMinSafeStoreUnitUUID(warehouseStoreSettingUIModel
                .getMinSafeStoreUnitUUID());
        rawEntity.setSafeStoreCalculateCategory(warehouseStoreSettingUIModel
                .getSafeStoreCalculateCategory());
        rawEntity.setMaxStoreRatio(warehouseStoreSettingUIModel
                .getMaxStoreRatio());
        rawEntity.setMinStoreRatio(warehouseStoreSettingUIModel
                .getMinStoreRatio());
        if (rawEntity.getDataSourceType() != WarehouseStoreSetting.DATASOURCE_TYPE_SERDOC) {
            rawEntity.setTargetAverageStoreAmount(warehouseStoreSettingUIModel
                    .getTargetAverageStoreAmount());
        }
        rawEntity.setTargetAverageStoreUnitUUID(warehouseStoreSettingUIModel
                .getTargetAverageStoreUnitUUID());
        rawEntity.setDataSourceType(warehouseStoreSettingUIModel
                .getDataSourceType());
        rawEntity.setRefUUID(warehouseStoreSettingUIModel.getRefUUID());
    }


    @Override
    public String getAuthorizationResource() {
        return IServiceModelConstants.Warehouse;
    }


    public void convParentOrganizationToUI(
            ServiceEntityNode parentOrganization,
            WarehouseUIModel warehouseUIModel)
            throws ServiceEntityConfigureException {
        if (parentOrganization != null) {
            warehouseUIModel
                    .setParentOrganizationName(parentOrganization.getName());
            warehouseUIModel.setParentOrganizationId(parentOrganization.getId());
            warehouseUIModel.setParentOrganizationUUID(parentOrganization
                    .getUuid());
        }
    }


    public void convWarehouseToUI(Warehouse warehouse,
                                  WarehouseUIModel warehouseUIModel)
            throws ServiceEntityInstallationException {
        convWarehouseToUI(warehouse, warehouseUIModel, null);
    }

    public void convWarehouseToUI(Warehouse warehouse,
                                  WarehouseUIModel warehouseUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        if (warehouse != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(warehouse, warehouseUIModel);
            warehouseUIModel.setTelephone(warehouse
                    .getTelephone());
            warehouseUIModel.setAddress(warehouse.getAddress());
            warehouseUIModel.setMainContactUUID(warehouse.getMainContactUUID());
            warehouseUIModel.setSwitchFlag(warehouse.getSwitchFlag());
            warehouseUIModel.setPostcode(warehouse.getPostcode());
            warehouseUIModel.setCityName(warehouse.getCityName());
            warehouseUIModel.setRefCityUUID(warehouse.getRefCityUUID());
            warehouseUIModel.setTownZone(warehouse.getTownZone());
            warehouseUIModel.setSubArea(warehouse.getSubArea());
            warehouseUIModel.setStreetName(warehouse.getStreetName());
            warehouseUIModel.setHouseNumber(warehouse.getHouseNumber());
            warehouseUIModel.setLength(warehouse.getLength());
            warehouseUIModel.setWidth(warehouse.getWidth());
            warehouseUIModel.setArea(warehouse.getArea());
            warehouseUIModel.setVolume(warehouse.getVolume());
            warehouseUIModel.setHeight(warehouse.getHeight());
            warehouseUIModel.setAddressOnMap(warehouse.getAddressOnMap());
            warehouseUIModel.setFullName(warehouse.getFullName());
            warehouseUIModel.setHasFootStep(warehouse.getHasFootStep());
            warehouseUIModel.setRestrictedGoodsFlag(warehouse
                    .getRestrictedGoodsFlag());
            warehouseUIModel.setForbiddenGoodsFlag(warehouse
                    .getForbiddenGoodsFlag());
            warehouseUIModel.setOperationMode(warehouse.getOperationMode());
            warehouseUIModel.setSystemDefault(warehouse.getSystemDefault());
            warehouseUIModel.setRefMaterialCategory(warehouse
                    .getRefMaterialCategory());
            if (logonInfo != null) {
                try {
                    Map<Integer, String> refMaterialCategoryMap = this
                            .initRefMaterialCategory(logonInfo.getLanguageCode());
                    warehouseUIModel
                            .setRefMaterialCategoryValue(refMaterialCategoryMap
                                    .get(warehouse.getRefMaterialCategory()));
                    Map<Integer, String> systemDefaultMap = this
                            .initSystemDefaultMap(logonInfo.getLanguageCode());
                    warehouseUIModel
                            .setSystemDefaultValue(systemDefaultMap
                                    .get(warehouse.getSystemDefault()));
                    Map<Integer, String> operationModeMap = this
                            .initOperationModeMap(logonInfo.getLanguageCode());
                    warehouseUIModel
                            .setOperationModeValue(operationModeMap
                                    .get(warehouse.getOperationMode()));
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper
                            .genDefaultErrorMessage(e, "refMaterialCategory"));
                }
            }
        }
    }

    public void convEmployeeToUI(Employee employee,
                                 WarehouseUIModel warehouseUIModel) {
        if (employee != null) {
            warehouseUIModel.setContactEmployeeTelephone(employee
                    .getTelephone());
            warehouseUIModel.setContactEmployeeName(employee.getName());
            warehouseUIModel.setContactEmployeeID(employee.getId());
            warehouseUIModel.setContactMobileNumber(employee.getMobile());
        }
    }


    public void convUIToWarehouse(WarehouseUIModel warehouseUIModel,
                                  Warehouse rawEntity) {
        DocFlowProxy.convUIToServiceEntityNode(warehouseUIModel, rawEntity);
        rawEntity.setTelephone(warehouseUIModel.getTelephone());
        rawEntity.setAddress(warehouseUIModel.getAddress());
        rawEntity.setMainContactUUID(warehouseUIModel.getMainContactUUID());
        rawEntity.setSwitchFlag(warehouseUIModel.getSwitchFlag());
        rawEntity.setPostcode(warehouseUIModel.getPostcode());
        rawEntity.setCityName(warehouseUIModel.getCityName());
        rawEntity.setRefCityUUID(warehouseUIModel.getRefCityUUID());
        rawEntity.setTownZone(warehouseUIModel.getTownZone());
        rawEntity.setSubArea(warehouseUIModel.getSubArea());
        rawEntity.setStreetName(warehouseUIModel.getStreetName());
        rawEntity.setHouseNumber(warehouseUIModel.getHouseNumber());
        rawEntity.setLength(warehouseUIModel.getLength());
        rawEntity.setWidth(warehouseUIModel.getWidth());
        rawEntity.setVolume(warehouseUIModel.getVolume());
        rawEntity.setHeight(warehouseUIModel.getHeight());
        rawEntity.setArea(warehouseUIModel.getArea());
        rawEntity.setAddressOnMap(warehouseUIModel.getAddressOnMap());
        rawEntity.setFullName(warehouseUIModel.getFullName());
        rawEntity.setHasFootStep(warehouseUIModel.getHasFootStep());
        rawEntity.setRestrictedGoodsFlag(warehouseUIModel
                .getRestrictedGoodsFlag());
        rawEntity.setForbiddenGoodsFlag(warehouseUIModel
                .getForbiddenGoodsFlag());
        rawEntity.setSystemDefault(warehouseUIModel.getSystemDefault());
        if (warehouseUIModel.getRefMaterialCategory() > 0) {
            rawEntity.setRefMaterialCategory(warehouseUIModel
                    .getRefMaterialCategory());
        }
        rawEntity.setOperationMode(warehouseUIModel.getOperationMode());
    }


    public void initSetAreaFromWarehouse(Warehouse warehouse,
                                         WarehouseArea warehouseArea) {
        if (warehouse != null && warehouseArea != null) {
            warehouseArea.setLength(warehouse.getLength());
            warehouseArea.setWidth(warehouse.getWidth());
            warehouseArea.setHeight(warehouse.getHeight());
        }
    }
    public List<String> getWarehouseUUIDList(int refCategory, String client) throws ServiceEntityConfigureException, NoSuchFieldException {
        List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
        ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure(refCategory, Warehouse.FIELD_refMaterialCategory);
        if (refCategory != 0) {
            keyList.add(key1);
        }
        List<ServiceEntityNode> warehouseList = this.getEntityNodeListByKeyList(keyList, Warehouse.NODENAME, client,
                null);
        return ServiceCollectionsHelper.pluckList(warehouseList, IServiceEntityNodeFieldConstant.UUID);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convMaterialSKUToStoreSettingUI(
            MaterialStockKeepUnit materialStockKeepUnit,
            WarehouseStoreSettingUIModel warehouseStoreSettingUIModel) {
        if (materialStockKeepUnit != null) {
            warehouseStoreSettingUIModel
                    .setRefMaterialSKUName(materialStockKeepUnit.getName());
            warehouseStoreSettingUIModel
                    .setRefMaterialSKUId(materialStockKeepUnit.getId());
        }
    }

    @Override
    public ServiceSearchProxy getSearchProxy() {
        return warehouseSearchProxy;
    }

}
