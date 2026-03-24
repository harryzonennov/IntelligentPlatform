package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigPreCondition;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Logic to generate default item level 
 *
 * @author Zhang, Hang
 */
@Service
public class ServiceItemIdGenerator extends ServiceDefaultIdGenerateHelper {

    public static final String DEF_SEPERATOR = "-";

    @Autowired
    protected InstallServiceCommonTool installServiceCommonTool;

    /**
     *  Generates an Item level node ID from its parent node UUID in standard creation case. The generated Item ID should be in the format
     *  `{headerId}-{nextMiddleId}`, Example: `SA20250117002-5`.
     *
     * @param tableName the name of the item level node database table
     * @param idFieldName the name of the field containing the ID values, by default: ID
     * @param headerId The prefix for the generated item ID, typically representing a root Document ID.
     *  *               Example: `SA20250117002`.
     * @param parentNodeUUID parent node UUID
     * @param offset an integer offset added to the maximum middle ID to generate the next ID.

     * @return the newly generated item node ID in the format {headerId}-{nextMiddleId}.
     * @throws SearchConfigureException if there is an error during database access
     *         or query execution.
     */
    public String genItemIdParentUUID(String tableName, String idFieldName, String headerId,
                                      String parentNodeUUID, int offset, String client)
            throws SearchConfigureException {
        List<SearchConfigPreCondition> preConditionList = new ArrayList<>();
        SearchConfigPreCondition condition1 = new SearchConfigPreCondition();
        condition1.setFieldName(IServiceEntityNodeFieldConstant.PARENTNODEUUID);
        condition1.setFieldValue(parentNodeUUID);
        preConditionList.add(condition1);
        return genItemId(tableName, idFieldName, headerId, preConditionList, offset,
                client);
    }

    /**
     *  Generates an Item level node ID from its parent node UUID with postfix usually for Item split case.
     *  The generated Item ID should be in the format `{headerId}-{nextMiddleId}-{postfix}` Example: `SA20250117002-5-1`
     *
     * @param tableName the name of the item level node database table
     * @param idFieldName the name of the field containing the ID values, by default: ID
     * @param idPrefix The `{headerId}-{nextMiddleId}` format item ID, typically representing an existed Item ID.
     *  *               Example: `SA20250117002-3`.
     * @param parentNodeUUID parent node UUID
     * @param offset an integer offset added to the maximum middle ID to generate the next ID.

     * @return the newly generated item node ID in the format {headerId}-{nextMiddleId}.
     * @throws SearchConfigureException if there is an error during database access
     *         or query execution.
     */
    public String genItemIdParentUUIDWithPost(String tableName, String idFieldName, String idPrefix,
                                             String parentNodeUUID, int offset, String client)
            throws SearchConfigureException {
        List<SearchConfigPreCondition> preConditionList = new ArrayList<>();
        SearchConfigPreCondition condition1 = new SearchConfigPreCondition();
        condition1.setFieldName(IServiceEntityNodeFieldConstant.PARENTNODEUUID);
        condition1.setFieldValue(parentNodeUUID);
        preConditionList.add(condition1);
        return genItemIdWithPost(tableName, idFieldName, idPrefix, preConditionList, offset,
                client);
    }

    /**
     *  Generates an Item level node ID by querying the database for existing IDs, The generated Item ID should be in the format
     *  `{headerId}-{nextMiddleId}-{postfix}` Example: `SA20250117002-5`.
     *
     * @param tableName the name of the item level node database table
     * @param idFieldName the name of the field containing the ID values, by default: ID
     * @param headerId The prefix for the generated item ID, typically representing a root Document ID.
     *  Example: `SA20250117002`.
     * @param keyList a list of search preconditions to filter the query results.
     * @param offset an integer to add to the maximum middle ID to generate the next ID.
     *
     * @return the newly generated item ID in the format {headerId}-{nextMiddleId}.
     * @throws SearchConfigureException if there is an error during database access
     *         or query execution.
     */
    private String genItemId(String tableName, String idFieldName, String headerId,
                            List<SearchConfigPreCondition> keyList, int offset, String client)
            throws SearchConfigureException {
        List<String> idList = getIdList(tableName, idFieldName,
                keyList, client);
        // Generate the new item ID based on the header, retrieved IDs, offset
        return genItemIdOnline(headerId, idList, offset);
    }

    private List<String> getIdList(String tableName, String idFieldName,
                                   List<SearchConfigPreCondition> keyList, String client) throws SearchConfigureException {
        String varName = ServiceEntityStringHelper.headerToLowerCase(tableName);
        String statement = "select " + idFieldName + " from " + tableName + " "
                + ServiceEntityStringHelper.headerToLowerCase(tableName);
        List<SearchConfigPreCondition> preConditionList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(keyList)) {
            preConditionList.addAll(keyList);
        }
        if (!ServiceEntityStringHelper.checkNullString(client)) {
            preConditionList.add(new SearchConfigPreCondition(client, IServiceEntityNodeFieldConstant.CLIENT, null ));
        }
        if (!ServiceCollectionsHelper.checkNullList(preConditionList)) {
            ServiceEntityStringHelper.mergeStrWhenNotNull(statement, BsearchService.generateEachPreConditionList(
                    preConditionList, varName, true));
        }
        return hibernateDefaultImpDAO
                .getStringListBySQLCommand(statement);
    }

    /**
     *  Generates an Item level node ID by querying the database for existing IDs, The generated Item ID should be in the format
     *  `{headerId}-{nextMiddleId}-{postfix}` Example: `SA20250117002-5-1`.
     *
     * @param tableName the name of the item level node database table
     * @param idFieldName the name of the field containing the ID values, by default: ID
     * @param idPrefix The `{headerId}-{nextMiddleId}` format item ID, typically representing an existed Item ID.
     *             Example: `SA20250117002-3`.
     * @param keyList a list of search preconditions to filter the query results.
     * @param offset an integer to add to the maximum middle ID to generate the next ID.
     *
     * @return the newly generated item ID in the format {headerId}-{nextMiddleId}.
     * @throws SearchConfigureException if there is an error during database access
     *         or query execution.
     */
    private String genItemIdWithPost(String tableName, String idFieldName, String idPrefix,
                               List<SearchConfigPreCondition> keyList, int offset, String client)
            throws SearchConfigureException {
        List<SearchConfigPreCondition> preConditionList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(keyList)) {
            preConditionList.addAll(keyList);
        }
        if (!ServiceEntityStringHelper.checkNullString(idPrefix)) {
            preConditionList.add(new SearchConfigPreCondition(idPrefix, idFieldName, null ));
        }
        List<String> idList = getIdList(tableName, idFieldName,
                preConditionList, client);
        // Logic to set default idPrefix
        if (ServiceEntityStringHelper.checkNullString(idPrefix)) {
            idPrefix = Integer.toString(0);
        }
        return genItemIdPostOnline(idPrefix, idList, offset);
    }

    /**
     * Generates a new item node ID based on the provided header ID, an existing list of IDs,an offset value.
     * The new ID is constructed in the format {headerId}-{nextMiddleId}, where {nextMiddleId} is calculated by adding the offset
     * to the maximum middle ID found in the provided ID list.
     *
     * @param headerId the prefix for the generated ID; typically represents the header portion.
     * @param idList the list of existing IDs, each in the format {header}-{middle}-{postfix}.
     * @param offset an integer offset added to the maximum middle ID to generate a new middle ID.
     * @return the generated item node ID, combining the header ID, the separator,
     *         and the next middle ID.
     */
    public String genItemIdOnline(String headerId,
                                  List<String> idList, int offset) {
        int maxMiddleId = parseMaxIdMiddle(idList);
        return headerId + DEF_SEPERATOR + genNextIdMiddle(maxMiddleId + offset);
    }

    public String genItemIdPostOnline(String idPrefix,
                                      List<String> idList, int offset) {
        int maxIdPost = parseMaxIdPost(idList);
        return idPrefix + DEF_SEPERATOR + genNextIdPost(maxIdPost + offset);
    }

    /**
     * Parses a list of ID strings and determines the maximum numeric value from the middle portion of the IDs.
     * Each ID in the list is expected to follow the format `{header}-{middle}-{postfix}`, e.g. SA202500234-3-1
     * where the middle part should contain a numeric value.
     *
     * @param idList a list of strings representing IDs in the format `{header}-{middle}-{postfix}`
     * @return the maximum numeric value found in the middle portion of the IDs, or 0 if none is found
     */
    public static int parseMaxIdMiddle(List<String> idList) {
        int maxValue = 0;
        if (ServiceCollectionsHelper.checkNullList(idList)) {
            return maxValue;
        }
        // Iterate through each ID in the list
        for (String rawId : idList) {
            if (rawId == null || rawId.isEmpty()) {
                continue; // Skip null or empty strings
            }
            // Extract middle part of the ID
            String middleId = getIdMiddle(rawId);
            if (middleId != null) {
                try {
                    // Parse middleId as integer and update maxValue if larger
                    int number = Integer.parseInt(middleId);
                    maxValue = Math.max(maxValue, number);
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        }
        return maxValue; // Return the determined maximum value
    }
    
    /**
     * Parses a list of ID strings and determines the maximum numeric value from the postfix portion of the IDs.
     * Each ID in the list is expected to follow the format `{header}-{middle}-{postfix}`, e.g. SA202500234-3-1
     * where the middle part should contain a numeric value.
     *
     * @param idList a list of strings representing IDs in the format `{header}-{middle}-{postfix}`
     * @return the maximum numeric value found in the middle portion of the IDs, or 0 if none is found
     */
    public static int parseMaxIdPost(List<String> idList) {
        int maxValue = 0;
        if (ServiceCollectionsHelper.checkNullList(idList)) {
            return maxValue;
        }
        for (String rawId : idList) {
            String prefix = getIdPostfix(rawId);
            if (prefix != null) {
                try {
                    int number = Integer.parseInt(prefix);
                    maxValue = Math.max(maxValue, number);
                } catch (NumberFormatException e) {
                    // just continue;
                }
            }
        }
        return maxValue;
    }

    /**
     * Extract the middle portion of an Item ID string formatted as `{header}-{middle}-{postfix}` or `{header}-{middle}`
     * This method assumes the ID string is separated by a defined delimiter (DEF_SEPARATOR).
     *
     * @param id the full Item ID string in the format `{header}-{middle}-{postfix}` or `{header}-{middle}`
     * @return the middle portion of the ID string, or an empty string if input is invalid
     */
    public static String getIdMiddle(String id) {
        if (ServiceEntityStringHelper.checkNullString(id)) {
            return ServiceEntityStringHelper.EMPTYSTRING;
        }
        String[] array = id.split(DEF_SEPERATOR);
        if (!ServiceCollectionsHelper.checkNullArray(array) && array.length >= 2) {
            return array[1];
        }
        return ServiceEntityStringHelper.EMPTYSTRING;
    }

    /**
     * Extract the post portion of an Item ID string formatted as `{header}-{middle}-{postfix}`
     * This method assumes the ID string is separated by a defined delimiter (DEF_SEPARATOR).
     *
     * @param id the full Item ID string in the format {header}-{middle}-{postfix}
     * @return the post portion of the ID string, or an empty string if input is invalid
     */
    public static String getIdPostfix(String id) {
        if (ServiceEntityStringHelper.checkNullString(id)) {
            return ServiceEntityStringHelper.EMPTYSTRING;
        }
        String[] array = id.split(DEF_SEPERATOR);
        if (!ServiceCollectionsHelper.checkNullArray(array) && array.length >= 2) {
            return array[2];
        }
        return ServiceEntityStringHelper.EMPTYSTRING;
    }

    /**
     * Generate Next ID middle portion
     */
    public String genNextIdMiddle(int rawValue) {
        return Integer.valueOf(rawValue + indexStep).toString();
    }

    /**
     * Generate Next ID postfix
     */
    public String genNextIdPost(int rawSubId) {
        return Integer.valueOf(rawSubId + indexStep).toString();
    }

    /**
     * TODO check if this is needed anymore
     * This method is used to batch update document item id by default rule
     *
     * @param seManager
     * @param parentNodeName
     * @param itemNodeName
     * @throws ServiceEntityConfigureException
     */
    public void genItemIdBatchTemplate(ServiceEntityManager seManager,
                                       String parentNodeName, String itemNodeName,
                                       LogonInfo logonInfo)
            throws ServiceEntityConfigureException {
        installServiceCommonTool.batchUpdateAllItemListFromParent(seManager, parentNodeName, itemNodeName, (parentEntityNode, itemEntityNode) -> {
            if (ServiceEntityStringHelper.checkNullString(itemEntityNode
                    .getId())) {
                try {
                    String itemId = genItemIdParentUUID(itemNodeName,
                            IServiceEntityNodeFieldConstant.ID, parentEntityNode.getId(),
                            parentEntityNode.getUuid(), 0, logonInfo.getClient());
                    itemEntityNode.setId(itemId);
                    return itemEntityNode;
                } catch (SearchConfigureException e) {
                    throw new RuntimeException(new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_SYSTEM_WRONG, e.getErrorMessage()));
                }
            }
            return null;
        }, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));

    }

    @Override
    public String getMainTableName() {
        return "";
    }

    @Override
    public String getIdPrefix() {
        return "";
    }

    @Override
    public int getIdPrefixLength() {
        return 0;
    }

    @Override
    public boolean isTimeStampNeed() {
        return false;
    }
}
