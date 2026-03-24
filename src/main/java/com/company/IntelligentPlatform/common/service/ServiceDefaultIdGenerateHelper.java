package com.company.IntelligentPlatform.common.service;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

// TODO-LEGACY: import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// TODO-DAO: import platform.foundation.DAO.HibernateDefaultImpDAO;
import com.company.IntelligentPlatform.common.service.SerialNumberSettingManager;
import com.company.IntelligentPlatform.common.service.ServiceBarcodeException;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.ServiceReflectiveHelper;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigPreCondition;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.IServiceBarcodeGenerator;
import com.company.IntelligentPlatform.common.service.ServiceBarcodeGeneratorFactory;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.SerNumberSettingJSONItem;
import com.company.IntelligentPlatform.common.model.SerialNumberSetting;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityPersistenceHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class ServiceDefaultIdGenerateHelper {

    // TODO-LEGACY: @Autowired
    @Autowired
    protected HibernateDefaultImpDAO hibernateDefaultImpDAO; // TODO-DAO: stub, replace with JPA

    @Autowired
    protected SerialNumberSettingManager serialNumberSettingManager;

    @Autowired
    protected ServiceBarcodeGeneratorFactory serviceBarcodeGeneratorFactory;

    public static final String FIELD_ID = "id";

    public static final String FIELD_LAST_UPDATE = "lastUpdateTime";

    public static final String FIELD_CREATED = "createdTime";

    public static final int DEF_INDEX_STEP = 1;

    public static final int DEF_INDEX_START_VALUE = 1;

    protected int indexStep = DEF_INDEX_STEP;

    protected int indexStartValue = DEF_INDEX_START_VALUE;

    protected Logger logger = LoggerFactory.getLogger(ServiceDefaultIdGenerateHelper.class);

    public String getMainTableName() {
        return null;
    }

    public String getIdPrefix() {
        return null;
    }

    public int getIdPrefixLength() {
        return 0;
    }

    public boolean isTimeStampNeed() {
        return false;
    }

    /**
     * Get today's biggest index
     *
     * @param tableName
     * @return
     * @throws SearchConfigureException
     */
    public int getLastIDIndexToday(String client, String tableName,
                                   int indexLength) throws SearchConfigureException {
        String yesterday = getYesterdaySqlString();
        String varName = ServiceEntityStringHelper.headerToLowerCase(tableName);
        String sqlCommand = "from " + tableName + " " + varName + " where "
                + varName + "." + FIELD_LAST_UPDATE + " > '" + yesterday + "'";
        if (client != null
                && !client.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
            boolean crossFlag = ServiceEntityPersistenceHelper
                    .checkTableCrossClient(tableName);
            if (!crossFlag) {
                sqlCommand = sqlCommand + " and client like '%" + client + "%'";
            }
        }
        List<ServiceEntityNode> seNodeList = hibernateDefaultImpDAO
                .getEntityNodeListBySQLCommand(sqlCommand);
        int biggestValue = 0;
        for (ServiceEntityNode seNode : seNodeList) {
            int index = parseIndexFromID(seNode.getId(), indexLength);
            if (index > biggestValue) {
                biggestValue = index;
            }
        }
        return biggestValue;
    }

    /**
     * Get today's biggest index by extended id field name
     *
     * @param tableName
     * @return
     * @throws SearchConfigureException
     */
    public int getLastIndexToday(String client, String tableName,
                                 String idFieldName, int indexLength)
            throws SearchConfigureException {
        String yesterday = getYesterdaySqlString();
        String varName = ServiceEntityStringHelper.headerToLowerCase(tableName);
        String sqlCommand = "from " + tableName + " " + varName + " where "
                + varName + "." + FIELD_CREATED + " > '" + yesterday + "' or "
                + FIELD_LAST_UPDATE + " > '" + yesterday + "'";
        if (client != null
                && !client.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
            boolean crossFlag = ServiceEntityPersistenceHelper
                    .checkTableCrossClient(tableName);
            if (!crossFlag) {
                sqlCommand = sqlCommand + " and client like '%" + client + "%'";
            }
        }
        List<ServiceEntityNode> seNodeList = hibernateDefaultImpDAO
                .getEntityNodeListBySQLCommand(sqlCommand);
        int biggestValue = 0;
        for (ServiceEntityNode seNode : seNodeList) {
            Object rawValue = ServiceReflectiveHelper.getFieldValue(seNode,
                    idFieldName);
            String extendedId = (rawValue != null) ? rawValue.toString() : null;
            int index = parseIndexFromID(extendedId, indexLength);
            if (index > biggestValue) {
                biggestValue = index;
            }
        }
        return biggestValue;
    }

    /**
     * Get ID's biggest index
     *
     * @param tableName
     * @param indexLength
     * @return
     * @throws SearchConfigureException
     */
    public int getLastIDIndex(String client, String tableName, int indexLength)
            throws SearchConfigureException {
        return this.getLastIdIndex(tableName, indexLength, null, client);
    }

    /**
     * Get ID's biggest index
     *
     * @param tableName
     * @param indexLength
     * @return
     * @throws SearchConfigureException
     */
    public int getLastIdIndex(String tableName, int indexLength,
                              String idPrefix, String client) throws SearchConfigureException {
        int biggestValue = getLastIndex(tableName, indexLength, idPrefix,
                IServiceEntityNodeFieldConstant.ID, client);
        return biggestValue;
    }

    /**
     * Get ID's biggest index, by specialized id field name;
     *
     * @param tableName
     * @param idFieldName : ID field name
     * @param indexLength
     * @return
     * @throws SearchConfigureException
     */
    public int getLastIndex(String tableName, int indexLength, String idPrefix,
                            String idFieldName, String client) throws SearchConfigureException {
        String varName = ServiceEntityStringHelper.headerToLowerCase(tableName);
        String statement = "select " + idFieldName + " from " + tableName + " "
                + varName;
        List<SearchConfigPreCondition> preConditionList = new ArrayList<SearchConfigPreCondition>();
        if (!ServiceEntityStringHelper.checkNullString(idPrefix)) {
            SearchConfigPreCondition preCondition = new SearchConfigPreCondition();
            preCondition.setFieldName(idFieldName);
            preCondition.setFieldValue(idPrefix);
            preConditionList.add(preCondition);
        }
        if (!ServiceEntityStringHelper.checkNullString(client)) {
            SearchConfigPreCondition preCondition2 = new SearchConfigPreCondition();
            preCondition2.setFieldName(IServiceEntityNodeFieldConstant.CLIENT);
            preCondition2.setFieldValue(client);
            preConditionList.add(preCondition2);
        }
        // check the pre condition
        if (!ServiceCollectionsHelper.checkNullList(preConditionList)) {
            ServiceEntityStringHelper.mergeStrWhenNotNull(statement, BsearchService.generateEachPreConditionList(
                    preConditionList, varName, true));
        }
        List<String> idList = hibernateDefaultImpDAO
                .getStringListBySQLCommand(statement);
        int biggestValue = 0;
        if (idList == null || idList.size() == 0) {
            return biggestValue;
        }
        for (String seID : idList) {
            int index = parseIndexFromID(seID, indexLength);
            if (index > biggestValue) {
                biggestValue = index;
            }
        }
        return biggestValue;
    }

    /**
     * Get seNode List biggest id index online
     *
     * @param seNodeList
     * @param indexLength
     * @return
     * @throws SearchConfigureException
     */
    public int getLastIdIndexOnline(String client,
                                    List<ServiceEntityNode> seNodeList, int indexLength)
            throws SearchConfigureException {
        int biggestValue = 0;
        if (ServiceCollectionsHelper.checkNullList(seNodeList)) {
            return biggestValue;
        }
        for (ServiceEntityNode serviceEntityNode : seNodeList) {
            int index = parseIndexFromID(serviceEntityNode.getId(), indexLength);
            if (index > biggestValue) {
                biggestValue = index;
            }
        }
        return biggestValue;
    }

    /**
     * Get seNode List biggest index online
     *
     * @param seNodeList
     * @param indexLength
     * @return
     * @throws SearchConfigureException
     */
    public int getLastIndexOnline(List<ServiceEntityNode> seNodeList,
                                  Function<ServiceEntityNode, String> indexCallback, int indexLength) {
        int biggestValue = 0;
        if (ServiceCollectionsHelper.checkNullList(seNodeList)) {
            return biggestValue;
        }
        for (ServiceEntityNode serviceEntityNode : seNodeList) {
            int index = parseIndexFromID(
                    indexCallback.apply(serviceEntityNode), indexLength);
            if (index > biggestValue) {
                biggestValue = index;
            }
        }
        return biggestValue;
    }

    public String getDefaultBarcode(String resourceID, String tableName,
                                    String client) throws ServiceEntityConfigureException,
            SearchConfigureException, ServiceBarcodeException {
        SerialNumberSetting serialNumberSetting = (SerialNumberSetting) serialNumberSettingManager
                .getEntityNodeByKey(resourceID,
                        IServiceEntityNodeFieldConstant.ID,
                        SerialNumberSetting.NODENAME, client, null, true);
        IServiceBarcodeGenerator barcodeGenerator = serviceBarcodeGeneratorFactory
                .getBarcodeGeneartorInstance(serialNumberSetting
                        .getBarcodeType());
        return barcodeGenerator.genBarcode(serialNumberSetting, tableName);
    }

    public String getDefaultUniqueBarcode(String resourceID, String tableName,
                                          String client) throws ServiceEntityConfigureException,
            SearchConfigureException, ServiceBarcodeException {
        String barcode = getDefaultBarcode(resourceID, tableName, client);
        List<String> barcodeBackList = getBarcodeListFromDB(client, tableName);
        int tryIndex = 0;
        if (barcodeBackList != null && barcodeBackList.size() > 0) {
            if (barcodeBackList.contains(barcode)) {
                // In case duplicate barcode
                barcode = getDefaultBarcode(resourceID, tableName, client);
                if (!barcodeBackList.contains(barcode)) {
                    return barcode;
                }
                tryIndex++;
                if (tryIndex > 10) {
                    // In order to avoid dead lock
                    barcode = barcode + RandomStringUtils.randomNumeric(3)
                            + tryIndex;
                    return barcode;
                }
            }
        }
        return barcode;
    }

    /**
     * Get Barcode's biggest index
     *
     * @param tableName
     * @param indexLength
     * @return
     * @throws SearchConfigureException
     */
    public int getLastBarcodeIndex(String client, String tableName,
                                   int indexLength) throws SearchConfigureException {
        String varName = ServiceEntityStringHelper.headerToLowerCase(tableName);
        String sqlCommand = "select barcode from " + tableName + " " + varName;
        if (client != null
                && !client.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
            boolean crossFlag = ServiceEntityPersistenceHelper
                    .checkTableCrossClient(tableName);
            if (!crossFlag) {
                sqlCommand = sqlCommand + " where client like '%" + client
                        + "%'";
            }
        }
        List<String> idList = hibernateDefaultImpDAO
                .getStringListBySQLCommand(sqlCommand);
        return parseBiggestIntIdPostFix(idList, indexLength);
    }

    public static int parseBiggestIntIdPostFix(List<String> idList,
                                               int indexLength) {
        int biggestValue = 0;
        if (idList == null || idList.size() == 0) {
            return biggestValue;
        }
        for (String seID : idList) {
            int index = parseIndexFromID(seID, indexLength);
            if (index > biggestValue) {
                biggestValue = index;
            }
        }
        return biggestValue;
    }

    public List<String> getBarcodeListFromDB(String client, String tableName)
            throws SearchConfigureException {
        String varName = ServiceEntityStringHelper.headerToLowerCase(tableName);
        String sqlCommand = "select barcode from " + tableName + " " + varName;
        if (client != null
                && !client.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
            boolean crossFlag = ServiceEntityPersistenceHelper
                    .checkTableCrossClient(tableName);
            if (!crossFlag) {
                sqlCommand = sqlCommand + " where client like '%" + client
                        + "%'";
            }
        }
        List<String> idList = hibernateDefaultImpDAO
                .getStringListBySQLCommand(sqlCommand);
        return idList;
    }

    public static int parseIndexFromID(String id, int indexLength) {
        if (ServiceEntityStringHelper.checkNullString(id)) {
            return 0;
        }
        int endIndex = id.length();
        int beginIndex = endIndex - indexLength;
        if (beginIndex < 0) {
            return 0;
        }
        String indexString = id.substring(beginIndex, endIndex);
        try {
            int index = Integer.parseInt(indexString);
            return index;
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    public String genDefaultId(String client) {
        // should implement in sub class
        return genDefaultId(client, 0);
    }

    public String genDefaultId(String client, int offset) {
        
        String tableName = getMainTableName();
        String idPrefix = getIdPrefix();
        int idPrefixLength = getIdPrefixLength();
        boolean isTimeStampNeed = isTimeStampNeed();
        try {
            String serialNumberId = getSerialNumberId(tableName, tableName, offset, client);
            if (ServiceEntityStringHelper.checkNullString(serialNumberId)) {
                if (isTimeStampNeed) {
                    String todayStamp = genDefaultTimeStamp();
                    return genDefaultId(client, idPrefix, todayStamp, offset,
                            tableName, idPrefixLength);
                } else {
                    return genDefIDNoTimeStamp(client, idPrefix, "",
                            tableName, idPrefixLength);
                }
            }else{
                return serialNumberId;
            }
        } catch (SearchConfigureException e) {
            return null;
        } catch (ServiceEntityInstallationException e) {
            return null;
        } catch (ServiceEntityConfigureException e) {
            return null;
        }
    }

    public String genDefaultId(SerialNumberSetting serialNumberSetting) {
        String tableName = getMainTableName();
        String idPrefix = getIdPrefix();
        int idPrefixLength = getIdPrefixLength();
        try {
            String serialNumberId = getSerialNumberId(serialNumberSetting, tableName);
            if (serialNumberId == null || serialNumberId.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
                return genDefIDNoTimeStamp(serialNumberSetting.getClient(), idPrefix, null, tableName,
                        idPrefixLength);
            }else{
                return serialNumberId;
            }
        } catch (SearchConfigureException e) {
            return null;
        } catch (ServiceEntityInstallationException e) {
            return null;
        } catch (ServiceEntityConfigureException e) {
            return null;
        }
    }

    public String genDefaultIdOnline(List<ServiceEntityNode> seNodeList,
                                     String client) {
        // should implement in sub class
        return null;
    }

    public String genDefaultBarcode(String client) {
        // should implement in sub class
        return null;
    }

    public String getDefaultUniqueBarcode(String client) {
        // should implement in sub class
        return null;
    }

    public String genNextID(String rawID, int indexLength) {
        int index = parseIndexFromID(rawID, indexLength);
        index = index + indexStep;
        String prefix = rawID.substring(0, rawID.length() - indexLength);
        return prefix + index;
    }

    public String getSerialNumberId(String resourceId, String tableName,
                                    String client) throws SearchConfigureException,
            ServiceEntityInstallationException, ServiceEntityConfigureException {
        SerialNumberSetting serialNumberSetting = (SerialNumberSetting) serialNumberSettingManager
                .getEntityNodeByKey(resourceId,
                        IServiceEntityNodeFieldConstant.ID,
                        SerialNumberSetting.NODENAME, client, null, true);
        if (serialNumberSetting != null) {
            int lastIndex = serialNumberSetting.getCoreStartNumber();
            if (serialNumberSetting.getTimeCodeFormat() == DefaultDateFormatConstant.FORT_NONE) {
                lastIndex = getLastIDIndex(client, tableName,
                        serialNumberSetting.getCoreNumberLength());
            } else {
                lastIndex = getLastIDIndexToday(client, tableName,
                        serialNumberSetting.getCoreNumberLength());
            }
            return genSerialNumberIdCore(lastIndex,
                    serialNumberSetting.getPrefixCode1(),
                    serialNumberSetting.getSeperator1(),
                    serialNumberSetting.getPrefixTimeCode(),
                    serialNumberSetting.getTimeCodeFormat(),
                    serialNumberSetting.getPostfixTimeCode(),
                    serialNumberSetting.getSeperator2(),
                    serialNumberSetting.getCoreNumberLength(),
                    serialNumberSetting.getCoreStartNumber(),
                    serialNumberSetting.getCoreStepSize());
        }
        return null;
    }

    public String getSerialNumberId(String resourceId, String tableName, List<ServiceEntityNode> rawSEList,
                                    String client) throws SearchConfigureException,
            ServiceEntityInstallationException, ServiceEntityConfigureException {
        SerialNumberSetting serialNumberSetting = (SerialNumberSetting) serialNumberSettingManager
                .getEntityNodeByKey(resourceId,
                        IServiceEntityNodeFieldConstant.ID,
                        SerialNumberSetting.NODENAME, client, null, true);
        if (serialNumberSetting != null) {
            int lastIndex = serialNumberSetting.getCoreStartNumber();
            if (serialNumberSetting.getTimeCodeFormat() == DefaultDateFormatConstant.FORT_NONE) {
                lastIndex = getLastIDIndex(client, tableName,
                        serialNumberSetting.getCoreNumberLength());
            } else {
                lastIndex = getLastIDIndexToday(client, tableName,
                        serialNumberSetting.getCoreNumberLength());
            }
            return genSerialNumberIdCore(lastIndex,
                    serialNumberSetting.getPrefixCode1(),
                    serialNumberSetting.getSeperator1(),
                    serialNumberSetting.getSeperator1Json(),
                    serialNumberSetting.getPrefixTimeCode(),
                    serialNumberSetting.getTimeCodeFormat(),
                    serialNumberSetting.getPostfixTimeCode(),
                    serialNumberSetting.getSeperator2(),
                    serialNumberSetting.getSeperator2Json(),
                    serialNumberSetting.getCoreNumberLength(),
                    serialNumberSetting.getCoreStartNumber(),
                    serialNumberSetting.getCoreStepSize(),
                    rawSEList);
        }
        return null;
    }

    public String getSerialNumberId(SerialNumberSetting serialNumberSetting,
                                    String tableName) throws SearchConfigureException,
            ServiceEntityInstallationException, ServiceEntityConfigureException {

        if (serialNumberSetting != null) {
            int lastIndex = serialNumberSetting.getCoreStartNumber();
            String idPrefix = ServiceEntityStringHelper.EMPTYSTRING;
            if (serialNumberSetting.getTimeCodeFormat() == DefaultDateFormatConstant.FORT_NONE) {
                idPrefix = getPrefix(serialNumberSetting);
                lastIndex = getLastIdIndex(tableName,
                        serialNumberSetting.getCoreNumberLength(), idPrefix,
                        serialNumberSetting.getClient());
            } else {
                lastIndex = getLastIDIndexToday(
                        serialNumberSetting.getClient(), tableName,
                        serialNumberSetting.getCoreNumberLength());
            }
            return genSerialNumberIdCore(lastIndex, serialNumberSetting);
        }
        return null;
    }

    public String getSerialNumberId(String resourceID, String tableName,
                                    int offset, String client) throws SearchConfigureException,
            ServiceEntityInstallationException, ServiceEntityConfigureException {
        SerialNumberSetting serialNumberSetting = (SerialNumberSetting) serialNumberSettingManager
                .getEntityNodeByKey(resourceID,
                        IServiceEntityNodeFieldConstant.ID,
                        SerialNumberSetting.NODENAME, client, null, true);
        if (serialNumberSetting != null) {
            int lastIndex = serialNumberSetting.getCoreStartNumber();
            if (serialNumberSetting.getTimeCodeFormat() == DefaultDateFormatConstant.FORT_NONE) {
                lastIndex = getLastIDIndex(client, tableName,
                        serialNumberSetting.getCoreNumberLength());
            } else {
                lastIndex = getLastIDIndexToday(client, tableName,
                        serialNumberSetting.getCoreNumberLength());
            }
            lastIndex = lastIndex + offset;
            return genSerialNumberIdCore(lastIndex,
                    serialNumberSetting.getPrefixCode1(),
                    serialNumberSetting.getSeperator1(),
                    serialNumberSetting.getPrefixTimeCode(),
                    serialNumberSetting.getTimeCodeFormat(),
                    serialNumberSetting.getPostfixTimeCode(),
                    serialNumberSetting.getSeperator2(),
                    serialNumberSetting.getCoreNumberLength(),
                    serialNumberSetting.getCoreStartNumber(),
                    serialNumberSetting.getCoreStepSize());
        }
        return null;
    }

    public String getSerialNumberIdOnline(String resourceID,
                                          List<ServiceEntityNode> seNodeList, String client)
            throws SearchConfigureException,
            ServiceEntityInstallationException, ServiceEntityConfigureException {
        SerialNumberSetting serialNumberSetting = (SerialNumberSetting) serialNumberSettingManager
                .getEntityNodeByKey(resourceID,
                        IServiceEntityNodeFieldConstant.ID,
                        SerialNumberSetting.NODENAME, client, null, true);
        if (serialNumberSetting != null) {
            int lastIndex = serialNumberSetting.getCoreStartNumber();
            if (serialNumberSetting.getTimeCodeFormat() == DefaultDateFormatConstant.FORT_NONE) {
                lastIndex = getLastIdIndexOnline(client, seNodeList,
                        serialNumberSetting.getCoreNumberLength());
            } else {
                // Currently no special handling for online time-stamp number
                lastIndex = getLastIdIndexOnline(client, seNodeList,
                        serialNumberSetting.getCoreNumberLength());
            }
            return genSerialNumberIdCore(lastIndex,
                    serialNumberSetting.getPrefixCode1(),
                    serialNumberSetting.getSeperator1(),
                    serialNumberSetting.getPrefixTimeCode(),
                    serialNumberSetting.getTimeCodeFormat(),
                    serialNumberSetting.getPostfixTimeCode(),
                    serialNumberSetting.getSeperator2(),
                    serialNumberSetting.getCoreNumberLength(),
                    serialNumberSetting.getCoreStartNumber(),
                    serialNumberSetting.getCoreStepSize());
        }
        return null;
    }

    @Deprecated
    private void parseJSONExtendValue(SerialNumberSetting serialNumberSetting,
                                      Map<String, ServiceEntityNode> valueMap) {
        if (serialNumberSetting == null) {
            return;
        }
        List<Field> fieldList = ServiceEntityFieldsHelper
                .getFieldsList(SerialNumberSetting.class);
        if (ServiceCollectionsHelper.checkNullList(fieldList)) {
            return;
        }
        for (Field field : fieldList) {
            // Only parse String type field
            if (!String.class.getSimpleName().equals(
                    field.getType().getSimpleName())) {
                continue;
            }
            field.setAccessible(true);
            String parsedValue = parseJSONValueWrapper(field,
                    serialNumberSetting, valueMap);
            if (parsedValue != null) {
                // set value back
                try {
                    field.set(serialNumberSetting, parsedValue);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    // just continue;
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, field.getName()));
                }
            }
        }
    }

    private String parseJSONValueWrapper(Field field,
                                         SerialNumberSetting serialNumberSetting,
                                         Map<String, ServiceEntityNode> valueMap) {
        field.setAccessible(true);
        Object rawValue;
        try {
            rawValue = field.get(serialNumberSetting);
            if (rawValue == null) {
                return null;
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            return null;
        }
        if (valueMap == null || valueMap.size() == 0) {
            return rawValue.toString();
        }
        Gson gson = new GsonBuilder().disableHtmlEscaping().setLenient()
                .serializeNulls().create();
        try {
            SerNumberSettingJSONItem jsonItem = gson.fromJson(
                    rawValue.toString(), SerNumberSettingJSONItem.class);
            if (jsonItem != null) {
                ServiceEntityNode seInstance = valueMap.get(jsonItem
                        .getModelName());
                if (seInstance == null) {
                    return null;
                }
                Object rawSEValue = ServiceEntityFieldsHelper
                        .getServiceFieldValue(seInstance,
                                jsonItem.getFieldName());
                if (rawSEValue == null) {
                    return null;
                }
                return rawSEValue.toString();
            }
        } catch (Exception ex) {
            return rawValue.toString();
        }
        return rawValue.toString();
    }

    /**
     * Core Logic to generate the Prefix, exclude the zeros and run-time index
     *
     * @param prefixCode1
     * @param seperator1
     * @param prefixTimeCode
     * @param timeCodeFormat
     * @param postfixTimeCode
     * @param seperator2
     * @param coreNumberLength
     * @param coreStartNumber
     * @param coreStepSize
     * @return
     * @throws SearchConfigureException
     * @throws ServiceEntityInstallationException
     */
    public String getPrefix(String prefixCode1, int seperator1,
                            String prefixTimeCode, int timeCodeFormat, String postfixTimeCode,
                            int seperator2, int coreNumberLength, int coreStartNumber,
                            int coreStepSize) throws ServiceEntityInstallationException {
        String seperator1Value = serialNumberSettingManager
                .getSeperatorValue(seperator1);
        String seperator2Value = serialNumberSettingManager
                .getSeperatorValue(seperator2);
        String timeFormatValue = serialNumberSettingManager.getTimeFormat(
                timeCodeFormat, new Date());
        StringBuffer contentBuffer = new StringBuffer(prefixCode1);
        if (!ServiceEntityStringHelper.checkNullString(seperator1Value)) {
            contentBuffer.append(seperator1Value);
        }
        if (!ServiceEntityStringHelper.checkNullString(prefixTimeCode)) {
            contentBuffer.append(prefixTimeCode);
        }
        if (!ServiceEntityStringHelper.checkNullString(timeFormatValue)) {
            contentBuffer.append(timeFormatValue);
        }
        if (!ServiceEntityStringHelper.checkNullString(postfixTimeCode)) {
            contentBuffer.append(postfixTimeCode);
        }
        if (!ServiceEntityStringHelper.checkNullString(seperator2Value)) {
            contentBuffer.append(seperator2Value);
        }
        return contentBuffer.toString();

    }

    /**
     * Core Logic to generate the Prefix, exclude the zeros and run-time index
     *
     * @param prefixCode1
     * @param seperator1
     * @param prefixTimeCode
     * @param timeCodeFormat
     * @param postfixTimeCode
     * @param seperator2
     * @param coreNumberLength
     * @param coreStartNumber
     * @param coreStepSize
     * @return
     * @throws SearchConfigureException
     * @throws ServiceEntityInstallationException
     */
    public String getPrefix(String prefixCode1, int seperator1, String seperator1Json,
                            String prefixTimeCode, int timeCodeFormat, String postfixTimeCode,
                            int seperator2, String seperator2Json, int coreNumberLength, int coreStartNumber,
                            int coreStepSize, List<ServiceEntityNode> rawSEList) throws ServiceEntityInstallationException {
        String seperator1Value = ServiceEntityStringHelper.EMPTYSTRING;
        if (!ServiceEntityStringHelper.checkNullString(seperator1Value)) {
            seperator1Value = serialNumberSettingManager
                    .getSeperatorValue(seperator1);
        }
        if (!ServiceEntityStringHelper.checkNullString(seperator1Json)) {
            seperator1Value = serialNumberSettingManager.getSeperatorValueFromJSON(seperator1Json, rawSEList);
        }
        String seperator2Value = ServiceEntityStringHelper.EMPTYSTRING;
        if (!ServiceEntityStringHelper.checkNullString(seperator2Value)) {
            seperator2Value = serialNumberSettingManager
                    .getSeperatorValue(seperator2);
        }
        if (!ServiceEntityStringHelper.checkNullString(seperator2Json)) {
            seperator2Value = serialNumberSettingManager.getSeperatorValueFromJSON(seperator2Json, rawSEList);
        }
        String timeFormatValue = serialNumberSettingManager.getTimeFormat(
                timeCodeFormat, new Date());
        StringBuffer contentBuffer = new StringBuffer(prefixCode1);
        if (!ServiceEntityStringHelper.checkNullString(seperator1Value)) {
            contentBuffer.append(seperator1Value);
        }
        if (!ServiceEntityStringHelper.checkNullString(prefixTimeCode)) {
            contentBuffer.append(prefixTimeCode);
        }
        if (!ServiceEntityStringHelper.checkNullString(timeFormatValue)) {
            contentBuffer.append(timeFormatValue);
        }
        if (!ServiceEntityStringHelper.checkNullString(postfixTimeCode)) {
            contentBuffer.append(postfixTimeCode);
        }
        if (!ServiceEntityStringHelper.checkNullString(seperator2Value)) {
            contentBuffer.append(seperator2Value);
        }
        return contentBuffer.toString();

    }


    /**
     * Core Logic to generate the Prefix, exclude the zeros and run-time index
     *
     * @param serialNumberSetting
     * @return
     * @throws ServiceEntityInstallationException
     */
    public String getPrefix(SerialNumberSetting serialNumberSetting)
            throws ServiceEntityInstallationException {
        return getPrefix(serialNumberSetting.getPrefixCode1(),
                serialNumberSetting.getSeperator1(),
                serialNumberSetting.getPrefixTimeCode(),
                serialNumberSetting.getTimeCodeFormat(),
                serialNumberSetting.getPostfixTimeCode(),
                serialNumberSetting.getSeperator2(),
                serialNumberSetting.getCoreNumberLength(),
                serialNumberSetting.getCoreStartNumber(),
                serialNumberSetting.getCoreStepSize());
    }

    public String genSerialNumberIdCore(int lastIndex, String prefixCode1,
                                        int seperator1, String prefixTimeCode, int timeCodeFormat,
                                        String postfixTimeCode, int seperator2, int coreNumberLength,
                                        int coreStartNumber, int coreStepSize)
            throws ServiceEntityInstallationException {
        // current core index in run-time
        int index = lastIndex + coreStepSize;
        String rawString = Integer.valueOf(index).toString();
        String indexString = ServiceEntityStringHelper.EMPTYSTRING;
        // calculate prefix
        String prefix = getPrefix(prefixCode1, seperator1, prefixTimeCode,
                timeCodeFormat, postfixTimeCode, seperator2, coreNumberLength,
                coreStartNumber, coreStepSize);
        // calculate number of zeros
        int zeroLenght = coreNumberLength - rawString.length();
        if (zeroLenght > 0) {
            for (int i = 0; i < zeroLenght; i++) {
                indexString = indexString + "0";
            }
            indexString = indexString.concat(rawString);
        } else {
            indexString = rawString;
        }
        return prefix + indexString;
    }


    public String genSerialNumberIdCore(int lastIndex, String prefixCode1,
                                        int seperator1, String seperator1Json, String prefixTimeCode,
                                        int timeCodeFormat,
                                        String postfixTimeCode, int seperator2, String seperator2Json,
                                        int coreNumberLength,
                                        int coreStartNumber, int coreStepSize, List<ServiceEntityNode> rawSEList)
            throws ServiceEntityInstallationException {
        // current core index in run-time
        int index = lastIndex + coreStepSize;
        String rawString = Integer.valueOf(index).toString();
        String indexString = ServiceEntityStringHelper.EMPTYSTRING;
        // calculate prefix
        String prefix = getPrefix(prefixCode1, seperator1, seperator1Json, prefixTimeCode,
                timeCodeFormat, postfixTimeCode, seperator2, seperator2Json, coreNumberLength,
                coreStartNumber, coreStepSize, rawSEList);
        // calculate number of zeros
        int zeroLenght = coreNumberLength - rawString.length();
        if (zeroLenght > 0) {
            for (int i = 0; i < zeroLenght; i++) {
                indexString = indexString + "0";
            }
            indexString = indexString.concat(rawString);
        } else {
            indexString = rawString;
        }
        return prefix + indexString;
    }

    /**
     * Generate Serial Number With runtime value list
     *
     * @param lastIndex
     * @param serialNumberSetting
     * @return
     * @throws SearchConfigureException
     * @throws ServiceEntityInstallationException
     */
    public String genSerialNumberIdCore(int lastIndex,
                                        SerialNumberSetting serialNumberSetting,
                                        Map<String, ServiceEntityNode> valueMap)
            throws SearchConfigureException, ServiceEntityInstallationException {
        // current core index in run-time
        parseJSONExtendValue(serialNumberSetting, valueMap);
        return genSerialNumberIdCore(lastIndex,
                serialNumberSetting.getPrefixCode1(),
                serialNumberSetting.getSeperator1(),
                serialNumberSetting.getPrefixTimeCode(),
                serialNumberSetting.getTimeCodeFormat(),
                serialNumberSetting.getPostfixTimeCode(),
                serialNumberSetting.getSeperator2(),
                serialNumberSetting.getCoreNumberLength(),
                serialNumberSetting.getCoreStartNumber(),
                serialNumberSetting.getCoreStepSize());
    }

    public static Map<String, ServiceEntityNode> registerValueMap(
            ServiceEntityNode seNode, Map<String, ServiceEntityNode> rawValueMap) {
        String modelName = ServiceEntityStringHelper.headerToLowerCase(ServiceDocumentComProxy.getServiceEntityModelName(seNode));
        return registerValueMap(modelName, seNode, rawValueMap);
    }

    public static Map<String, ServiceEntityNode> registerValueMap(
            String objectName, ServiceEntityNode seNode,
            Map<String, ServiceEntityNode> rawValueMap) {
        if (rawValueMap == null) {
            rawValueMap = new HashMap<String, ServiceEntityNode>();
        }
        rawValueMap.put(objectName, seNode);
        return rawValueMap;
    }

    public String genSerialNumberIdCore(int lastIndex,
                                        SerialNumberSetting serialNumberSetting)
            throws SearchConfigureException, ServiceEntityInstallationException {
        // current core index in run-time
        return genSerialNumberIdCore(lastIndex,
                serialNumberSetting.getPrefixCode1(),
                serialNumberSetting.getSeperator1(),
                serialNumberSetting.getPrefixTimeCode(),
                serialNumberSetting.getTimeCodeFormat(),
                serialNumberSetting.getPostfixTimeCode(),
                serialNumberSetting.getSeperator2(),
                serialNumberSetting.getCoreNumberLength(),
                serialNumberSetting.getCoreStartNumber(),
                serialNumberSetting.getCoreStepSize());
    }


    public String genSerialNumberIdCore(int lastIndex,
                                        SerialNumberSetting serialNumberSetting, List<ServiceEntityNode> rawSEList)
            throws SearchConfigureException, ServiceEntityInstallationException {
        // current core index in run-time
        return genSerialNumberIdCore(lastIndex,
                serialNumberSetting.getPrefixCode1(),
                serialNumberSetting.getSeperator1(),
                serialNumberSetting.getSeperator1Json(),
                serialNumberSetting.getPrefixTimeCode(),
                serialNumberSetting.getTimeCodeFormat(),
                serialNumberSetting.getPostfixTimeCode(),
                serialNumberSetting.getSeperator2(),
                serialNumberSetting.getSeperator2Json(),
                serialNumberSetting.getCoreNumberLength(),
                serialNumberSetting.getCoreStartNumber(),
                serialNumberSetting.getCoreStepSize(),
                rawSEList);
    }

    public String genDefaultId(String client, String prefix, String mid,
                               String tableName, int indexLength) throws SearchConfigureException {
        int lastIndex = getLastIDIndexToday(client, tableName, indexLength);
        int index = lastIndex;
        if (lastIndex == 0) {
            index = indexStartValue;
        } else {
            index = index + indexStep;
        }
        String rawString = Integer.valueOf(index).toString();
        String indexString = ServiceEntityStringHelper.EMPTYSTRING;
        int zeroLenght = indexLength - rawString.length();
        if (zeroLenght > 0) {
            for (int i = 0; i < zeroLenght; i++) {
                indexString = indexString + "0";
            }
            indexString = indexString.concat(rawString);
            if (mid != null) {
                return prefix + mid + indexString;
            } else {
                return prefix + indexString;
            }
        }
        return null;
    }

    public String genDefaultId(String client, String prefix, String mid,
                               int offset, String tableName, int indexLength)
            throws SearchConfigureException {
        int lastIndex = getLastIDIndexToday(client, tableName, indexLength);
        int index = lastIndex;
        if (lastIndex == 0) {
            index = indexStartValue;
        } else {
            index = index + indexStep;
        }
        index = index + offset;
        String rawString = Integer.valueOf(index).toString();
        String indexString = ServiceEntityStringHelper.EMPTYSTRING;
        int zeroLenght = indexLength - rawString.length();
        if (zeroLenght > 0) {
            for (int i = 0; i < zeroLenght; i++) {
                indexString = indexString + "0";
            }
            indexString = indexString.concat(rawString);
            if (mid != null) {
                return prefix + mid + indexString;
            } else {
                return prefix + indexString;
            }
        }
        return null;
    }

    public String genDefIDNoTimeStamp(String client, String prefix,
                                      String midName, String tableName, int indexLength)
            throws SearchConfigureException {
        int lastIndex = getLastIDIndex(client, tableName, indexLength);
        int index = lastIndex;
        if (lastIndex == 0) {
            index = indexStartValue;
        } else {
            index = index + indexStep;
        }
        String rawString = Integer.valueOf(index).toString();
        String indexString = ServiceEntityStringHelper.EMPTYSTRING;
        int zeroLenght = indexLength - rawString.length();
        if (zeroLenght >= 0) {
            for (int i = 0; i < zeroLenght; i++) {
                indexString = indexString + "0";
            }
            indexString = indexString.concat(rawString);
            String localMidName = midName;
            if (midName == null) {
                localMidName = ServiceEntityStringHelper.EMPTYSTRING;
            }
            return prefix + localMidName + indexString;
        }
        return null;
    }

    public String genDefIdNoTimeStampOnline(String client, String prefix,
                                            String midName, List<ServiceEntityNode> seNodeList, int indexLength)
            throws SearchConfigureException {
        int lastIndex = getLastIdIndexOnline(client, seNodeList, indexLength);
        int index = lastIndex;
        if (lastIndex == 0) {
            index = indexStartValue;
        } else {
            index = index + indexStep;
        }
        String rawString = Integer.valueOf(index).toString();
        String indexString = ServiceEntityStringHelper.EMPTYSTRING;
        int zeroLenght = indexLength - rawString.length();
        if (zeroLenght >= 0) {
            for (int i = 0; i < zeroLenght; i++) {
                indexString = indexString + "0";
            }
            indexString = indexString.concat(rawString);
            String localMidName = midName;
            if (midName == null) {
                localMidName = ServiceEntityStringHelper.EMPTYSTRING;
            }
            return prefix + localMidName + indexString;
        }
        return null;
    }

    public String genDefaultTimeStamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateString = dateFormat.format(new Date());
        return dateString;
    }

    public String getTodaySqlString() {
        Calendar calendar = new GregorianCalendar();
        Date date = new Date();
        calendar.setTime(date);
        return DefaultDateFormatConstant.DATE_FORMAT.format(date);
    }

    public String getYesterdaySqlString() {
        Calendar calendar = new GregorianCalendar();
        Date date = new Date();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        return DefaultDateFormatConstant.DATE_FORMAT.format(calendar.getTime());
    }

    public int getIndexStep() {
        return indexStep;
    }

    public void setIndexStep(int indexStep) {
        this.indexStep = indexStep;
    }

    public int getIndexStartValue() {
        return indexStartValue;
    }

    public void setIndexStartValue(int indexStartValue) {
        this.indexStartValue = indexStartValue;
    }

}
