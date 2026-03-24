package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.company.IntelligentPlatform.common.service.CorporateCustomerException;
import com.company.IntelligentPlatform.common.service.MaterialException;
// TODO-LEGACY: import platform.foundation.Administration.InstallService.Configure.IServiceExcelConfigureConstants;
import com.company.IntelligentPlatform.common.service.ServiceExcelCellConfig;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportConfig;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.ServiceUIMeta;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.dto.ServiceExcelReportErrorLogUnion;
import com.company.IntelligentPlatform.common.dto.ServiceExcelReportResponseModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceDocumentSettingManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.AttachmentConstantHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNodeLastUpdateTimeCompare;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceDocExcelDownloadTemplate;
import com.company.IntelligentPlatform.common.model.ServiceDocExcelUploadTemplate;
import com.company.IntelligentPlatform.common.model.ServiceExtendFieldSetting;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.*;

/**
 * General Service Helper for excel upload & download
 */
@Service
public abstract class ServiceExcelHandlerProxy{

    @Autowired
    protected ServiceExcelReportProxy serviceExcelReportProxy;

    @Autowired
    protected ServiceDocumentSettingManager serviceDocumentSettingManager;

    @Autowired
    protected ServiceUIMetaProxy serviceUIMetaProxy;

    public static final int DATA_START_ROWINDEX = 1;

    public static final String EXCEL_TYPE_XLS = "xls";

    public static final String EXCEL_TYPE_XLSX = "xlsx";

    public static final String POST_XLS = "xls";

    public static final String POST_XLSX = "xlsx";

    public static final String METAKEY_EXCELTYPE = "excelType";

    public static final int DEF_EXCEL_LIMIT = 300;

    protected Logger logger = LoggerFactory.getLogger(ServiceExcelHandlerProxy.class);

    /**
     * API method to return the excel type
     * @return
     */
    public String getExcelType(){
        String customExcelType = getCustomExcelType();
        if(!ServiceEntityStringHelper.checkNullString(customExcelType)){
            return customExcelType;
        }
        return getDefExcelType();
    }

    public int getDefExcelLimit(){
        return DEF_EXCEL_LIMIT;
    }

    public abstract List<FieldNameUnit> getDefFieldNameList();

    public String getDefExcelType(){
        return EXCEL_TYPE_XLSX;
    }

    public abstract Class<?> getExcelModelClass();

    /**
     * API to return custom configured excel type
     * @return
     */
    public String getCustomExcelType(){
        return null;
    }

    public <ExcelUIModel> List<ExcelUIModel> getExcelModelListWrapper(List<ServiceEntityNode> rawList,
                                                                      Class<ExcelUIModel> excelUIModelClass,
                                                                      ServiceUIModelExtension serviceUIModelExtension,
                                                                      LogonInfo logonInfo)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        return getExcelModelListWrapper(rawList, excelUIModelClass, serviceUIModelExtension, null, logonInfo);
    }

    public <ExcelUIModel> List<ExcelUIModel> getExcelModelListWrapper(List<ServiceEntityNode> rawList,
                                                                      Class<ExcelUIModel> excelUIModelClass,
                                                                      ServiceUIModelExtension serviceUIModelExtension
            , List<ServiceModuleConvertPara> additionalConvertParaList,LogonInfo logonInfo)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        if(ServiceCollectionsHelper.checkNullList(rawList)){
            return null;
        }

        int exportLimit = this.getDefExcelLimit();
        List<ServiceEntityNode> subList = rawList;
        if(exportLimit > 0 && exportLimit < rawList.size()){
            subList = rawList.subList(0, exportLimit);
        }
        ServiceEntityManager serviceEntityManager = this.getDocumentSpecifier().getDocumentManager();
        subList.sort(new ServiceEntityNodeLastUpdateTimeCompare());
        List<ExcelUIModel> resultUIModelList = new ArrayList<>();
        for (ServiceEntityNode rawNode : subList) {
            ExcelUIModel uiModel = (ExcelUIModel) serviceEntityManager
                    .genUIModelFromUIModelExtension(excelUIModelClass,
                            serviceUIModelExtension
                                    .genUIModelExtensionUnion().get(0),
                            rawNode, logonInfo, additionalConvertParaList);
            resultUIModelList.add(uiModel);
        }
        return resultUIModelList;
    }

    public ServiceUIMeta genServiceUIMeta(){
        return new ServiceUIMeta(ServiceCollectionsHelper.asList(new ServiceBasicKeyStructure(getExcelType(), METAKEY_EXCELTYPE)));
    }

    public static String getExcelFilePostfixCore(String excelType) throws ServiceExcelConfigException {
        String postFix = initFileTypePostMap().get(excelType);
        if(ServiceEntityStringHelper.checkNullString(postFix)){
            return postFix;
        }
        throw new ServiceExcelConfigException(ServiceExcelConfigException.PARA_SYSTEM_WRONG,
                "invalid exceltype:" + excelType);
    }

    public static String getExcelTypeByPostfix(String postfix){
        Map<String, String> fileTypePostMap = initFileTypePostMap();
        Collection<String> excelTypeList = fileTypePostMap.keySet();
        for(String excelType: excelTypeList){
            String tmpPostfix = fileTypePostMap.get(excelType);
            if(tmpPostfix.equals(postfix)){
                return excelType;
            }
        }
        return null;
    }

    /**
     * Constant method to define excel type and post fix
     * @return
     */
    private static Map<String, String> initFileTypePostMap(){
        Map<String, String> fileTypePostMap = new HashMap<>();
        fileTypePostMap.put(EXCEL_TYPE_XLS, POST_XLS);
        fileTypePostMap.put(EXCEL_TYPE_XLSX, POST_XLSX);
        return fileTypePostMap;
    }

    public String getExcelFilePostfix() throws ServiceExcelConfigException {
        String excelType =  getExcelType();
        if(ServiceEntityStringHelper.checkNullString(excelType)){
            excelType = getDefExcelType();
        }
        return getExcelFilePostfixCore(excelType);
    }


    /**
     * Should be implemented in child class
     * @return
     */
    public abstract ServiceExcelReportConfig getDefExcelConfigure(SerialLogonInfo serialLogonInfo) throws ServiceExcelConfigException;


    public ServiceExcelReportConfig getDefExcelConfigureTemplate(String lanCode, String propertyPath) throws ServiceExcelConfigException {
        try {
            Map<String, String> labelMap = ServiceDropdownListHelper.getStrStaticDropDownMap(propertyPath, lanCode);
            List<ServiceExcelCellConfig> serviceExcelCellConfigList =
                    ServiceExcelHandlerProxy.buildDefServiceCellConfigureList(getDefFieldNameList(), labelMap);
            return buildDefReportConfig(serviceExcelCellConfigList, labelMap.get("modelTitle"));
        } catch (IOException e) {
            throw new ServiceExcelConfigException(ServiceExcelConfigException.PARA_SYSTEM_WRONG, e.getMessage());
        }
    }


    public ServiceExcelReportConfig getDefExcelConfigureTemplate(SerialLogonInfo serialLogonInfo) throws ServiceExcelConfigException {
        try {
            List<ServiceEntityNode> serviceExtendFieldSettingList =
                    this.getDocumentSpecifier().getFieldSettingList(getConfigureName(), serialLogonInfo);
            List<ServiceExcelCellConfig> serviceExcelCellConfigList =
                    ServiceExcelHandlerProxy.buildDefServiceCellConfigureList(getDefFieldNameList(), serviceExtendFieldSettingList);
            return buildDefReportConfig(serviceExcelCellConfigList, getConfigureName());
        } catch (ServiceEntityConfigureException e) {
            throw new ServiceExcelConfigException(ServiceExcelConfigException.PARA_SYSTEM_WRONG, e.getMessage());
        }
    }

    public abstract DocumentContentSpecifier getDocumentSpecifier();

    public abstract String getConfigureName();

    public ServiceExcelReportConfig buildDefReportConfig(List<ServiceExcelCellConfig> serviceExcelCellConfigList,
                                                         String modelTitle){
        ServiceExcelReportConfig serviceExcelReportConfig = new ServiceExcelReportConfig();
        modelTitle = ServiceEntityStringHelper.checkNullString(modelTitle)?
                this.getConfigureName(): modelTitle;
        serviceExcelReportConfig.setReportName(modelTitle);
        serviceExcelReportConfig.setTimeStampFlag(ServiceExcelReportConfig.TIMESTAMP_FLAG_NEED);
        serviceExcelReportConfig.setServiceExcelCellConfigs(serviceExcelCellConfigList);
        return serviceExcelReportConfig;
    }


    /**
     * Generate default service cell configure list by field name list
     * @param fieldList
     * @param labelMap
     * @return
     */
    public static List<ServiceExcelCellConfig> buildDefServiceCellConfigureList(List<FieldNameUnit> fieldList,
                                                                                      Map<String,
            String> labelMap ){
        if(ServiceCollectionsHelper.checkNullList(fieldList) || labelMap == null){
            return null;
        }
        List<ServiceExcelCellConfig> resultList = new ArrayList<>();
        for(int i = 0; i < fieldList.size(); i++ ){
            ServiceExcelCellConfig serviceExcelCellConfig = new ServiceExcelCellConfig();
            String fieldName = fieldList.get(i).getFieldName();
            serviceExcelCellConfig.setFieldName(fieldName);
            String fieldLabel = labelMap.get(fieldName);
            if(ServiceEntityStringHelper.checkNullString(fieldLabel)){
                String labelKey = fieldList.get(i).getLabelKey();
                fieldLabel = labelMap.get(labelKey);
            }
            fieldLabel = ServiceEntityStringHelper.checkNullString(fieldLabel)? fieldName: fieldLabel;
            serviceExcelCellConfig.setFieldLabel(fieldLabel);
            serviceExcelCellConfig.setColIndex(i);
            resultList.add(serviceExcelCellConfig);
        }
        return resultList;
    }

    public static List<ServiceExcelCellConfig> buildDefServiceCellConfigureList(List<FieldNameUnit> fieldList,
                                                                            List<ServiceEntityNode> serviceFieldList){
        if(ServiceCollectionsHelper.checkNullList(fieldList) || ServiceCollectionsHelper.checkNullList(serviceFieldList)){
            return null;
        }
        List<ServiceExcelCellConfig> resultList = new ArrayList<>();
        for(int i = 0; i < fieldList.size(); i++ ){
            ServiceExcelCellConfig serviceExcelCellConfig = new ServiceExcelCellConfig();
            String fieldName = fieldList.get(i).getFieldName();
            String fieldKey = fieldList.get(i).getFieldKey();
            if(ServiceEntityStringHelper.checkNullString(fieldName)){
                continue;
            }
            serviceExcelCellConfig.setFieldName(fieldName);
            ServiceExtendFieldSetting serviceExtendFieldSetting =
                    (ServiceExtendFieldSetting) ServiceCollectionsHelper.filterOnline(serviceFieldList, seNode->{
                        ServiceExtendFieldSetting tmpField = (ServiceExtendFieldSetting) seNode;
                        return fieldName.equals(tmpField.getFieldName()) || tmpField.getFieldName().equals(fieldKey);
                    });
            String fieldLabel = fieldName;
            if(serviceExtendFieldSetting != null){
                fieldLabel = serviceExtendFieldSetting.getFieldLabel();
            }
            serviceExcelCellConfig.setFieldLabel(fieldLabel);
            serviceExcelCellConfig.setColIndex(i);
            resultList.add(serviceExcelCellConfig);
        }
        return resultList;
    }

    public ServiceExcelReportConfig getDefUploadExcelConfigure(SerialLogonInfo serialLogonInfo) throws ServiceExcelConfigException {
        return getDefExcelConfigure(serialLogonInfo);
    }

    public ServiceExcelReportConfig getDefDownloadExcelConfigure(SerialLogonInfo serialLogonInfo) throws ServiceExcelConfigException {
        return getDefExcelConfigure(serialLogonInfo);
    }

    public ServiceExcelReportResponseModel readDataFromSheet(MultipartFile multipartFile, SerialLogonInfo serialLogonInfo) throws ServiceExcelConfigException {
        String postFix = AttachmentConstantHelper
                .getFileTypeFromPostFix(multipartFile);
        String excelType = getExcelTypeByPostfix(postFix);
        if(ServiceEntityStringHelper.checkNullString(excelType)){
            throw new ServiceExcelConfigException(ServiceExcelConfigException.PARA_NO_CONFIGURE, postFix);
        }
        try {
            Workbook workbook = createWorkbook(excelType, multipartFile.getInputStream());
            return readDataArrayFromSheet(workbook,
                    serialLogonInfo);
        } catch (IOException | DocumentException | ServiceEntityConfigureException e) {
            throw new ServiceExcelConfigException(ServiceExcelConfigException.PARA_SYSTEM_WRONG, e.getMessage());
        }
    }


    private ServiceExcelReportResponseModel readDataArrayFromSheet(
            Workbook workbook, SerialLogonInfo serialLogonInfo)
            throws ServiceExcelConfigException, DocumentException,
            ServiceEntityConfigureException {
        ServiceExcelReportConfig serviceExcelReportConfig = getUploadExcelConfigure(
                serialLogonInfo);
        if (serviceExcelReportConfig == null) {
            throw new ServiceExcelConfigException(
                    ServiceExcelConfigException.PARA_NO_CONFIGURE,
                    this.getConfigureName());
        }
        return parseIntoExcelModelCore(workbook, serviceExcelReportConfig);
    }

    /**
     * Logic to Get the id column index from service exel configure list
     *
     * @return
     */
    public int getIdColIndex(List<ServiceExcelCellConfig> cellConfigList) {
        if (cellConfigList != null && cellConfigList.size() > 0) {
            for (ServiceExcelCellConfig cellConfig : cellConfigList) {
                if (cellConfig.isIdFlag()) {
                    return cellConfig.getColIndex();
                }
            }
        }
        /*
         * Or else, return the default value
         */
        return 1;
    }

    /**
     * Provide key list for duplicate module check when trying to upload excel, Here is default logic, using id to
     * check.
     * Sub module should override this method if specified duplicate
     * @param seUIModel
     * @param <U>
     * @return
     */
    public <U extends SEUIComModel> List<ServiceBasicKeyStructure> getDuplicateCheckKeyList(U seUIModel){
        String id = (String) ServiceReflectiveHelper.getFieldValue(seUIModel, IServiceEntityNodeFieldConstant.ID);
        if(ServiceEntityStringHelper.checkNullString(id)){
            return null;
        }
        return ServiceCollectionsHelper.asList(new ServiceBasicKeyStructure(id, IServiceEntityNodeFieldConstant.ID));
    }

    /**
     * API method to provide default logic to update excel to batch
     * @param serviceExcelReportResponseModel
     * @param convertDocumentModel
     * @param overwiteFlag
     * @param serialLogonInfo
     * @throws ServiceComExecuteException
     * @throws ServiceExcelConfigException
     * @throws ServiceEntityConfigureException
     */
    public <U extends SEUIComModel, R extends ServiceModule> void updateDefExcelBatch(
            ServiceExcelReportResponseModel serviceExcelReportResponseModel,
            IConvertDocumentModel<U, R> convertDocumentModel,
            boolean overwiteFlag,
            SerialLogonInfo serialLogonInfo)
            throws ServiceComExecuteException, ServiceExcelConfigException, ServiceEntityConfigureException {
        Map<Integer, SEUIComModel> dataMap = serviceExcelReportResponseModel.getDataMap();
        if (dataMap == null || dataMap.isEmpty()) {
            return;
        }
        DocumentContentSpecifier documentContentSpecifier = this.getDocumentSpecifier();
        ServiceEntityManager serviceEntityManager = documentContentSpecifier.getDocumentManager();
        /*
         * [Step 1] Check and trigger initialization of each field meta
         */
        List<MetaModelConfigure<SEUIComModel>> metaModelConfigList = this.getMetaModelConfigure(serialLogonInfo);
        if(!ServiceCollectionsHelper.checkNullList(metaModelConfigList)){
            for(MetaModelConfigure<SEUIComModel> metaModelConfigure: metaModelConfigList) {
                initEachMetaModel(metaModelConfigure, serialLogonInfo);
            }
        }
        /*
         * [Step 2] retrieve raw data list
         */
        Set<Integer> keySet = dataMap.keySet();
        List<ServiceModule> serviceModuleList = new ArrayList<>();
        for (int row : keySet) {
            SEUIComModel seUIModel =  dataMap.get(row);
            try {
                List<ServiceBasicKeyStructure> keylist = getDuplicateCheckKeyList(seUIModel);
                if(keylist == null){
                    // in case can't find key info from this item, should skip it.
                    continue;
                }
                ServiceModule serviceModule = updateExcelModel(row, seUIModel, keylist, metaModelConfigList,
                        convertDocumentModel,
                        documentContentSpecifier,
                        overwiteFlag,
                        serialLogonInfo);
                if(serviceModule != null){
                    serviceModuleList.add(serviceModule);
                }
            } catch (MaterialException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, row + ""));
                throw new ServiceComExecuteException(ServiceComExecuteException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            } catch (ServiceEntityInstallationException | ServiceEntityConfigureException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, row + ""));
                throw new ServiceComExecuteException(ServiceComExecuteException.PARA_SYSTEM_ERROR, e.getMessage());
            } catch (DocActionException | ServiceModuleProxyException e) {
                throw new ServiceComExecuteException(ServiceComExecuteException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        }
        // Final step: update into persistence
        if(!ServiceCollectionsHelper.checkNullList(serviceModuleList)){
           ServiceCollectionsHelper.forEach(serviceModuleList, serviceModule -> {
               try {
                   serviceEntityManager.updateServiceModule(serviceModule.getClass(), serviceModule,
                           serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
               } catch (ServiceModuleProxyException  e) {
                   logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
               } catch (ServiceEntityConfigureException e) {
                   logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
               }
               return serviceModule;
           });
        }
    }

    private void initEachMetaModel(MetaModelConfigure<SEUIComModel> metaModelConfigure, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException {
        if(metaModelConfigure.getInitModelMetaList() != null){
            List<?> fieldMetaModelList = metaModelConfigure.getInitModelMetaList().execute(serialLogonInfo);
            if(ServiceCollectionsHelper.checkNullList(fieldMetaModelList)){
                return;
            }
            List<FieldMeta<SEUIComModel>> fieldMetaList = metaModelConfigure.getFieldMetaList();
            if(!ServiceCollectionsHelper.checkNullList(fieldMetaList)){
                for(FieldMeta<SEUIComModel> fieldMeta: fieldMetaList) {
                    fieldMeta.setFieldMetaModelList(fieldMetaModelList);
                }
            }
        }
    }

    private ServiceModule loadServiceModuleWrapper(SEUIComModel seUIModel,
                                                   List<ServiceBasicKeyStructure> keylist,
                                                   DocumentContentSpecifier documentContentSpecifier, String client)
            throws ServiceEntityConfigureException, ServiceModuleProxyException {
        ServiceEntityManager serviceEntityManager = documentContentSpecifier.getDocumentManager();
        ServiceEntityNode serviceEntityNode = serviceEntityManager.getEntityNodeByKeyList(keylist,
                documentContentSpecifier.getDocNodeName(), client, null );
        if(serviceEntityNode != null){
            return documentContentSpecifier.loadServiceModule(serviceEntityNode.getUuid(), client);
        }
        return null;
    }


    /**
     * Core method to update excel upload content into persistence
     *
     * @throws ServiceEntityConfigureException
     * @throws ServiceEntityInstallationException
     * @throws MaterialException
     * @throws CorporateCustomerException
     */
    private ServiceModule updateExcelModel(int rowIndex,
                                 SEUIComModel seUIModel,
                                 List<ServiceBasicKeyStructure> keylist,
                                 List<MetaModelConfigure<SEUIComModel>> metaModelConfigList,
                                 IConvertDocumentModel convertDocumentModel,
                                 DocumentContentSpecifier documentContentSpecifier, boolean overwriteFlag,
                                 SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, ServiceEntityInstallationException, MaterialException,
            DocActionException, ServiceModuleProxyException {
        /*
         * [Step 1] Try to find existed data
         */
        ServiceModule serviceModule  = loadServiceModuleWrapper(seUIModel, keylist, documentContentSpecifier,
                serialLogonInfo.getClient());
        if (serviceModule != null && !overwriteFlag) {
            return serviceModule;
        }
        if(serviceModule == null){
            // in case need to create serviceEntityNode
            serviceModule = documentContentSpecifier.createDocServiceModule(serialLogonInfo.getClient(), null);
        }
        /*
         * [Step2] Process each field meta
         */
        List<FieldMeta<SEUIComModel>> fieldMetaList = this.getFieldMetaList(serialLogonInfo);
        traverseFieldMetaBatch(fieldMetaList, seUIModel);
        if(!ServiceCollectionsHelper.checkNullList(metaModelConfigList)){
            for(MetaModelConfigure<SEUIComModel> metaModelConfigure: metaModelConfigList) {
                traverseFieldMetaBatch(metaModelConfigure.getFieldMetaList(), seUIModel);
            }
        }
        /*
         * [Step3] Conversion from SE UI Model
         */
        convertDocumentModel.execute(seUIModel, serviceModule, serialLogonInfo);
        return serviceModule;
    }

    private void traverseFieldMetaBatch(List<FieldMeta<SEUIComModel>> fieldMetaList, SEUIComModel seUIModel){
        if(!ServiceCollectionsHelper.checkNullList(fieldMetaList)){
            for(FieldMeta<SEUIComModel> fieldMeta: fieldMetaList) {
                traverseEachFieldMeta(fieldMeta, seUIModel);
            }
        }
    }

    private void traverseEachFieldMeta(FieldMeta<SEUIComModel> fieldMeta, SEUIComModel seUIModel){
        try {
            Field keyField = ServiceEntityFieldsHelper.getServiceField(seUIModel.getClass(),
                    fieldMeta.getFieldKey());
            String fieldValue =
                    (String) ServiceReflectiveHelper.getFieldValue(seUIModel, fieldMeta.getFieldName());
            Map<?, String> fieldMetaMap =  fieldMeta.getFieldMetaMap();
            if(fieldMetaMap != null){
                // In case process from meta map
                int basicType = ServiceReflectiveHelper.getBasicType(keyField.getType());
                if(basicType == ServiceReflectiveHelper.BASIC_TYPE_NUMBER){
                    Map<Integer, String> fieldMetaMapInt = (Map<Integer, String>) fieldMetaMap;
                    int keyInt = ServiceCollectionsHelper.getKeyByValue(fieldMetaMapInt, fieldValue);
                    keyField.setAccessible(true);
                    if(keyInt > 0){
                        keyField.set(seUIModel, keyInt);
                    } else {
                        keyField.set(seUIModel, fieldMeta.getDefMetaValue());
                    }
                } else {
                    Map<String, String> fieldMetaMapStr = (Map<String, String>) fieldMetaMap;
                    String keyStr = ServiceCollectionsHelper.getStringKeyByValue(fieldMetaMapStr, fieldValue);
                    keyField.setAccessible(true);
                    if(!ServiceEntityStringHelper.checkNullString(keyStr)){
                        keyField.set(seUIModel, keyStr);
                    } else {
                        keyField.set(seUIModel, fieldMeta.getDefMetaValue());
                    }
                }
            }
            UpdateUIModelHook<SEUIComModel> updateUIModelHook = fieldMeta.getUpdateUIModelHook();
            if(updateUIModelHook != null){
                // in case need to update uimodel in custom way
                updateUIModelHook.execute(seUIModel);
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, fieldMeta.getFieldKey()));
        }
    }

    /**
     * Utiltiy method:  to set value to UIModel in reflective way, by get key Value by parsing value from metaModel
     * after filter from metaModelList
     * @param seUIModel
     * @param keyList
     * @param fieldMetaModelList
     * @param keyField: field name for meta model which contains value
     * @param uiFieldName: field name from UI model, value should be set to this field from UI Model
     */
    public void setValueToUIModelByFieldKey(SEUIComModel seUIModel, List<ServiceBasicKeyStructure> keyList,
                                            List<?> fieldMetaModelList,
                                            String keyField, String uiFieldName){
        /*
         * [Step1]
         */
        Object metaModel = ServiceCollectionsHelper.filterSENodeListOnline(keyList, fieldMetaModelList, true);
        if(metaModel == null){
            return;
        }
        Object value = ServiceReflectiveHelper.getFieldValue(metaModel, keyField);
        if(value != null){
            try {
                ServiceReflectiveHelper.reflectSetValue(uiFieldName, seUIModel, value);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Each sub class should provide for defining how the model type meta data is initialized, and will be set to
     * field fieldMetaModelList for each field
     * @param serialLogonInfo
     * @return
     */
    protected abstract <U extends SEUIComModel> List<MetaModelConfigure<U>> getMetaModelConfigure(SerialLogonInfo serialLogonInfo);

    /**
     * Each sub class should provide field meta list,
     * for defining how to set value from meta data for each Excel UIModel instance
     *
     * @param serialLogonInfo
     * @return
     * @throws ServiceEntityInstallationException
     */
    protected abstract <U extends SEUIComModel> List<FieldMeta<U>> getFieldMetaList(SerialLogonInfo serialLogonInfo)
            throws ServiceEntityInstallationException;

    protected ServiceExcelReportResponseModel parseIntoExcelModelCore(
            Workbook workbook,
            ServiceExcelReportConfig serviceExcelReportConfig) {
        ServiceExcelReportResponseModel serviceExcelReportResponseModel = new ServiceExcelReportResponseModel();
        List<SEUIComModel> seDataList = new ArrayList<>();
        List<ServiceExcelReportErrorLogUnion> errorLogList = new ArrayList<>();
        List<Field> fieldList = ServiceEntityFieldsHelper.getServiceSelfDefinedFieldsList(this.getExcelModelClass(),
                SEUIComModel.class);
        int locIDColIndex = getIdColIndex(serviceExcelReportConfig
                .getServiceExcelCellConfigs());
        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
            Sheet sheet = workbook.getSheetAt(numSheet);
            ServiceExcelReportResponseModel tmpExcelReportResponseModel = readDataArrayFromSheet(
                    sheet, fieldList,
                    serviceExcelReportConfig.getServiceExcelCellConfigs(),
                    locIDColIndex);
            if (tmpExcelReportResponseModel.getDataList() != null
                    && tmpExcelReportResponseModel.getDataList().size() > 0) {
                seDataList.addAll(tmpExcelReportResponseModel.getDataList());
            }
            if (tmpExcelReportResponseModel.getErrorLogList() != null
                    && tmpExcelReportResponseModel.getErrorLogList().size() > 0) {
                errorLogList.addAll(tmpExcelReportResponseModel
                        .getErrorLogList());
            }
            if (!tmpExcelReportResponseModel.getDataMap().isEmpty()) {
                serviceExcelReportResponseModel
                        .setDataMap(tmpExcelReportResponseModel.getDataMap());
            }
        }
        serviceExcelReportResponseModel.setDataList(seDataList);
        serviceExcelReportResponseModel.setErrorLogList(errorLogList);
        return serviceExcelReportResponseModel;
    }


    public ServiceExcelReportResponseModel readDataArrayFromSheet(
            Sheet sheet, List<Field> fieldList,
            List<ServiceExcelCellConfig> configList, int idColIndex) {
        ServiceExcelReportResponseModel serviceExcelReportResponseModel = new ServiceExcelReportResponseModel();
        List<SEUIComModel> seDataList = new ArrayList<>();
        List<ServiceExcelReportErrorLogUnion> errorLogList = new ArrayList<>();
        Map<Integer, SEUIComModel> dataMap = serviceExcelReportResponseModel
                .getDataMap();
        Class<?> excelUIModelCls = this.getExcelModelClass();
        for (int rowIndex = DATA_START_ROWINDEX; rowIndex <= sheet
                .getLastRowNum(); rowIndex++) {
            try {
                SEUIComModel seData = (SEUIComModel) excelUIModelCls
                        .getDeclaredConstructor().newInstance();
                readRow(sheet, seData, fieldList, configList, rowIndex);
                seDataList.add(seData);
                dataMap.put(rowIndex, seData);
            } catch (IllegalArgumentException | IllegalAccessException | ParseException | InstantiationException |
                    NoSuchMethodException | InvocationTargetException e) {
                ServiceExcelReportErrorLogUnion serviceExcelReportErrorLogUnion = recordExcelReadErrorLog(
                        sheet, rowIndex, e.getMessage(), idColIndex);
                errorLogList.add(serviceExcelReportErrorLogUnion);
            }
        }
        serviceExcelReportResponseModel.setErrorLogList(errorLogList);
        serviceExcelReportResponseModel.setDataList(seDataList);
        return serviceExcelReportResponseModel;
    }


    public static ServiceExcelReportErrorLogUnion recordExcelReadErrorLog(
            Sheet sheet, int rowIndex, String errorMessage,
            int idColIndex) {
        ServiceExcelReportErrorLogUnion serviceExcelReportErrorLogUnion = new ServiceExcelReportErrorLogUnion();
        serviceExcelReportErrorLogUnion.setErrorMessage(errorMessage);
        serviceExcelReportErrorLogUnion.setSheetName(sheet.getSheetName());
        serviceExcelReportErrorLogUnion.setRowIndex(rowIndex + "");
        serviceExcelReportErrorLogUnion
                .setLocationType(ServiceExcelReportErrorLogUnion.LOCATION_READEXCEL);
        serviceExcelReportErrorLogUnion
                .setErrorCategory(ServiceExcelReportErrorLogUnion.CATEGORY_ERROR);
        String id = readIDFromExcel(sheet, rowIndex, idColIndex);
        serviceExcelReportErrorLogUnion.setId(id);
        return serviceExcelReportErrorLogUnion;
    }

    public static String readIDFromExcel(Sheet sheet, int rowIndex,
                                         int idColIndex) {
        Row row = sheet.getRow(rowIndex);
        Cell cell = row.getCell(idColIndex);
        return cell.getStringCellValue();
    }

    private void readRow(Sheet curSheet, SEUIComModel seData,
                        List<Field> fieldList, List<ServiceExcelCellConfig> configList,
                        int rowIndex) throws IllegalArgumentException,
            IllegalAccessException, ParseException {
        Row row = curSheet.getRow(rowIndex);
        if (row == null) {
            return;
        }
        if (ServiceCollectionsHelper.checkNullList(configList)) {
            return;
        }
        for (ServiceExcelCellConfig cellConfig : configList) {
            Field field = ServiceEntityFieldsHelper.filterFieldByNameOnline(
                    fieldList, cellConfig.getFieldName());
            if (field == null) {
                continue;
            }
            Cell cell = row.getCell(cellConfig.getColIndex());
            if (cell != null) {
                try {
                    cell.setCellType(org.apache.poi.ss.usermodel.CellType.STRING);
                    ServiceReflectiveHelper.reflectSetValue(field, seData,
                            getValue(cell), null);
                } catch (NumberFormatException e) {
                    // In case Number format exception, just ignore it.
                }
            }
        }
    }


    public static String getValue(Cell cell) {
        if (cell.getCellType() == org.apache.poi.ss.usermodel.CellType.BOOLEAN) {
            // return the boolean value
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == org.apache.poi.ss.usermodel.CellType.NUMERIC) {
            // return the numeric value
            return String.valueOf(cell.getNumericCellValue());
        } else {
            // return string type value
            return String.valueOf(cell.getStringCellValue());
        }
    }

    public ServiceExcelReportConfig getUploadExcelConfigure(SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, ServiceExcelConfigException {
        boolean customFlag = this.checkCustomUploadExcel(getConfigureName(), serialLogonInfo.getClient());
        if(customFlag){
            try {
                return getCustomUploadExcelConfigure(getConfigureName(), serialLogonInfo.getClient(),
                        serialLogonInfo.getLanguageCode());
            } catch (DocumentException | ServiceExcelConfigException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                throw new ServiceExcelConfigException(ServiceExcelConfigException.PARA_SYSTEM_WRONG, e.getMessage());
            }
        } else {
            // in case get default
            return getDefUploadExcelConfigure(serialLogonInfo);
        }
    }

    public ServiceExcelReportConfig getDownloadExcelConfigure(SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, ServiceExcelConfigException {
        boolean customFlag = this.checkCustomDownloadExcel(getConfigureName(), serialLogonInfo.getClient());
        if(customFlag){
            try {
                return getCustomDownloadExcelConfigure(getConfigureName(), serialLogonInfo.getClient(),
                        serialLogonInfo.getLanguageCode());
            } catch (DocumentException | ServiceExcelConfigException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                throw new ServiceExcelConfigException(ServiceExcelConfigException.PARA_SYSTEM_WRONG, e.getMessage());
            }
        } else {
            // in case get default
            return getDefDownloadExcelConfigure(serialLogonInfo);
        }
    }

    public ServiceExcelReportConfig getCustomDownloadExcelConfigure(String configureName, String client, String languageCode)
            throws DocumentException, ServiceExcelConfigException {
        ServiceExcelReportConfig serviceExcelReportConfig = serviceExcelReportProxy.getDownloadExcelReportConfigure(
                configureName, client);
        return serviceExcelReportConfig;
    }

    public ServiceExcelReportConfig getCustomUploadExcelConfigure(String configureName, String client,
                                                                 String languageCode)
            throws DocumentException, ServiceExcelConfigException {
        ServiceExcelReportConfig serviceExcelReportConfig = serviceExcelReportProxy.getUploadExcelReportConfigure(
                configureName, client, languageCode);
        return serviceExcelReportConfig;
    }

    /**
     * Check if using the custom download excel configure
     * @param configureName
     * @param client
     * @return
     * @throws ServiceEntityConfigureException
     */
    public boolean checkCustomDownloadExcel(String configureName, String client)
            throws ServiceEntityConfigureException {
        ServiceDocExcelDownloadTemplate serviceDocExcelDownloadTemplate = serviceDocumentSettingManager
                .getDownloadExcelTemplate(configureName,
                        AttachmentConstantHelper.TYPE_XML, client);
        return serviceDocExcelDownloadTemplate != null;
    }

    /**
     * Check if using the custom download excel configure
     * @param configureName
     * @param client
     * @return
     * @throws ServiceEntityConfigureException
     */
    public boolean checkCustomUploadExcel(String configureName, String client)
            throws ServiceEntityConfigureException {
        ServiceDocExcelUploadTemplate serviceDocExcelUploadTemplate = serviceDocumentSettingManager
                .getUploadExcelTemplate(configureName,
                        AttachmentConstantHelper.TYPE_XML, client);
        return serviceDocExcelUploadTemplate != null;
    }

    public byte[] buildExcelDocument(List<SEUIComModel> seUIModelList, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, ServiceExcelConfigException {
        try{
            ServiceExcelReportConfig serviceExcelReportConfig =
                    this.getDownloadExcelConfigure(serialLogonInfo);
            String excelType = this.getDefExcelType();
            Workbook workbook = createWorkbook(excelType, null);
            buildExcelDocument(serviceExcelReportConfig, seUIModelList, workbook);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            byte[] byteArray = os.toByteArray();
            os.close();
            return byteArray;
        }catch(IOException | IllegalAccessException e){
            throw new ServiceExcelConfigException(ServiceExcelConfigException.PARA_SYSTEM_WRONG, e.getMessage());
        }
    }

    public static Workbook createWorkbook(String excelType, InputStream inputStream) throws IOException {
        if(excelType.equals(ServiceExcelHandlerProxy.EXCEL_TYPE_XLS)){
            if(inputStream != null){
                return new HSSFWorkbook(inputStream);
            } else {
                return new HSSFWorkbook();
            }
        }
        if(excelType.equals(ServiceExcelHandlerProxy.EXCEL_TYPE_XLSX)){
            if(inputStream != null){
                return new XSSFWorkbook(inputStream);
            } else {
                return new XSSFWorkbook();
            }
        }
        return null;
    }


    public void buildExcelDocument(
            ServiceExcelReportConfig serviceExcelReportConfig, List<SEUIComModel> seUIModelList,
            Workbook workbook) throws ServiceExcelConfigException, IllegalAccessException {
        int rowIndex = 0;
        List<ServiceExcelCellConfig> cellConfigList = serviceExcelReportConfig
                .getServiceExcelCellConfigs();
        Sheet curSheet = workbook
                .getSheet(IServiceExcelConfigureConstants.DEF_EXCEL_SHEETNAME);
        if (curSheet == null) {
            curSheet = workbook
                    .createSheet(IServiceExcelConfigureConstants.DEF_EXCEL_SHEETNAME);
        }
        if(workbook instanceof XSSFWorkbook){
            XSSFWorkbook xWorkbook = (XSSFWorkbook) workbook;
            XSSFSheet xCurSheet = (XSSFSheet) curSheet;
            ServiceExcelReportProxy.insertTitle(xWorkbook, xCurSheet,
                    cellConfigList, rowIndex);
            rowIndex++;
            if(!ServiceCollectionsHelper.checkNullList(seUIModelList)){
                for (SEUIComModel seData : seUIModelList) {
                    List<Field> fieldList = ServiceEntityFieldsHelper.getFieldsList(seUIModelList.get(0).getClass());
                    ServiceExcelReportProxy.insertRow(xWorkbook, xCurSheet,
                            seData, fieldList, cellConfigList, rowIndex);
                    rowIndex++;
                }
                ServiceExcelReportProxy.insertBlankRow(xCurSheet, rowIndex);
            }
        }
        if(workbook instanceof HSSFWorkbook){
            HSSFWorkbook hWorkbook = (HSSFWorkbook) workbook;
            HSSFSheet hCurSheet = (HSSFSheet) curSheet;
            ServiceExcelReportProxy.insertTitle(hWorkbook, hCurSheet,
                    cellConfigList, rowIndex);
            rowIndex++;
            if(!ServiceCollectionsHelper.checkNullList(seUIModelList)){
                for (SEUIComModel seData : seUIModelList) {
                    List<Field> fieldList = ServiceEntityFieldsHelper.getFieldsList(seUIModelList.get(0).getClass());
                    ServiceExcelReportProxy.insertRow(hWorkbook, hCurSheet,
                            seData, fieldList, cellConfigList, rowIndex);
                    rowIndex++;
                }
                ServiceExcelReportProxy.insertBlankRow(hCurSheet, rowIndex);
            }
        }
    }


    public interface IConvertDocumentModel<U extends SEUIComModel, R extends ServiceModule>{

        void execute(U uiModel, R serviceModule, SerialLogonInfo serialLogonInfo);

    }

    public interface UpdateUIModelHook<U extends SEUIComModel>{

        void execute(U seUIModel);

    }


    public interface InitModelMetaList{

        List<?> execute(SerialLogonInfo serialLogonInfo) throws ServiceEntityConfigureException;

    }

    /**
     * Used for model type meta configuration
     */
    public static class MetaModelConfigure<U extends SEUIComModel>{

        /**
         * Initialize logic to get model meta
         */
        private InitModelMetaList initModelMetaList;

        /**
         * field Meta list which will be injected with model meta list
         */
        private List<FieldMeta<U>> fieldMetaList;

        public MetaModelConfigure(){

        }

        public MetaModelConfigure(InitModelMetaList initModelMetaList, List<FieldMeta<U>> fieldMetaList) {
            this.initModelMetaList = initModelMetaList;
            this.fieldMetaList = fieldMetaList;
        }

        public InitModelMetaList getInitModelMetaList() {
            return initModelMetaList;
        }

        public void setInitModelMetaList(InitModelMetaList initModelMetaList) {
            this.initModelMetaList = initModelMetaList;
        }

        public List<FieldMeta<U>> getFieldMetaList() {
            return fieldMetaList;
        }

        public void setFieldMetaList(List<FieldMeta<U>> fieldMetaList) {
            this.fieldMetaList = fieldMetaList;
        }
    }

    public static class FieldNameUnit{

        private String fieldName;

        private String fieldKey;

        private String labelKey;

        public FieldNameUnit(){

        }

        public FieldNameUnit(String fieldName) {
            this.fieldName = fieldName;
        }

        public FieldNameUnit(String fieldName, String fieldKey, String labelKey) {
            this.fieldName = fieldName;
            this.fieldKey = fieldKey;
            this.labelKey = labelKey;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldKey() {
            return fieldKey;
        }

        public void setFieldKey(String fieldKey) {
            this.fieldKey = fieldKey;
        }

        public String getLabelKey() {
            return labelKey;
        }

        public void setLabelKey(String labelKey) {
            this.labelKey = labelKey;
        }
    }


    public static class FieldMeta<U extends SEUIComModel>{

        private String fieldName;

        private String fieldKey;

        private Map<?, String> fieldMetaMap;

        private Object defMetaValue;

        private List<?> fieldMetaModelList;

        private UpdateUIModelHook<U> updateUIModelHook;

        public FieldMeta(){

        }

        public FieldMeta(String fieldName, String fieldKey) {
            this.fieldName = fieldName;
            this.fieldKey = fieldKey;
        }

        public FieldMeta(String fieldName, String fieldKey, Map<?, String> fieldMetaMap, Object defMetaValue) {
            this.fieldName = fieldName;
            this.fieldKey = fieldKey;
            this.fieldMetaMap = fieldMetaMap;
            this.defMetaValue = defMetaValue;
        }

        public FieldMeta(String fieldName, String fieldKey, Map<?, String> fieldMetaMap, List<?> fieldMetaModelList,
                         UpdateUIModelHook<U> updateUIModelHook) {
            this.fieldName = fieldName;
            this.fieldKey = fieldKey;
            this.fieldMetaMap = fieldMetaMap;
            this.fieldMetaModelList = fieldMetaModelList;
            this.updateUIModelHook = updateUIModelHook;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldKey() {
            return fieldKey;
        }

        public void setFieldKey(String fieldKey) {
            this.fieldKey = fieldKey;
        }

        public Map<?, String> getFieldMetaMap() {
            return fieldMetaMap;
        }

        public void setFieldMetaMap(Map<?, String> fieldMetaMap) {
            this.fieldMetaMap = fieldMetaMap;
        }

        public List<?> getFieldMetaModelList() {
            return fieldMetaModelList;
        }

        public void setFieldMetaModelList(List<?> fieldMetaModelList) {
            this.fieldMetaModelList = fieldMetaModelList;
        }

        public UpdateUIModelHook<U> getUpdateUIModelHook() {
            return updateUIModelHook;
        }

        public void setUpdateUIModelHook(UpdateUIModelHook<U> updateUIModelHook) {
            this.updateUIModelHook = updateUIModelHook;
        }

        public Object getDefMetaValue() {
            return defMetaValue;
        }

        public void setDefMetaValue(Object defMetaValue) {
            this.defMetaValue = defMetaValue;
        }
    }


}
