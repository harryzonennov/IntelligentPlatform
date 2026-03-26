package com.company.IntelligentPlatform.common.model;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

// TODO-LEGACY: import platform.foundation.Controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceReflectiveHelper;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Tool helper class for Collections
 *
 * @author Zhang, hang
 */
public class ServiceCollectionsHelper {

    /**
     * Check weather the list variable is null value
     *
     * @param rawList
     * @return
     */
    public static boolean checkNullList(List<?> rawList) {
        return rawList == null || rawList.size() == 0;
    }

    public static <T> boolean checkNullArray(T[] rawArray) {
        return rawArray == null || rawArray.length == 0;
    }

    public static boolean checkNullMap(Map<?, ?> rawMap) {
        return rawMap == null || rawMap.size() == 0;
    }

    public static <T> List<T> asList(T... arrays) {
        List<T> resultList = new ArrayList<>();
        if (arrays != null) {
            Collections.addAll(resultList, arrays);
        }
        return resultList;
    }

    public static <T> T[] addToPriArray(T[] rawArray, T newMember) {
        List<T> dataList = new ArrayList<>();
        if (rawArray != null && rawArray.length > 0) {
            dataList = convArrayToList(rawArray);
        }
        dataList.add(newMember);
        return convListToArray(dataList);
    }

    /**
     * Clone Service Entity Node List
     *
     * @param seNodeList
     * @return
     */
    public static List<ServiceEntityNode> cloneSEList(List<ServiceEntityNode> seNodeList) {
        if (ServiceCollectionsHelper.checkNullList(seNodeList)) {
            return null;
        }
        List<ServiceEntityNode> resultList = new ArrayList<>();
        for (ServiceEntityNode seNode : seNodeList) {
            if (seNode == null) {
                continue;
            }
            resultList.add((ServiceEntityNode) seNode.clone());
        }
        return resultList;
    }

    /**
     * Merge String array to string list.
     *
     * @param dataList
     * @return
     */
    public static <T> T[] convListToArray(List<T> dataList) {
        if (ServiceCollectionsHelper.checkNullList(dataList)) {
            return null;
        }
        T[] arr = (T[]) Array.newInstance(dataList.get(0).getClass(), dataList.size());
        for (int i = 0; i < dataList.size(); i++) {
            arr[i] = dataList.get(i);
        }
        return arr;
    }

    /**
     * Merge String array to string list.
     *
     * @param array
     * @return
     */
    public static <T> List<T> convArrayToList(T[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        List<T> resultList = new ArrayList<>();
        Collections.addAll(resultList, array);
        return resultList;
    }

    /**
     * Merge String array to string list.
     *
     * @param array
     * @return
     */
    public static List<String> mergeStringArrayToList(String[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        List<String> resultList = new ArrayList<>();
        Collections.addAll(resultList, array);
        return resultList;
    }

    public static String convStringListToStringSpace(List<String> stringList) {
        return convStringListToString(stringList, " ");
    }

    public static String convStringListToStringSpaceComa(List<String> stringList) {
        return convStringListToString(stringList, ",");
    }

    public static String convStringListToString(List<String> stringList, String regex) {
        if (ServiceCollectionsHelper.checkNullList(stringList)) {
            return ServiceEntityStringHelper.EMPTYSTRING;
        }
        StringBuffer buffer = new StringBuffer();
        for (String stringUnion : stringList) {
            if (!ServiceEntityStringHelper.checkNullString(stringUnion)) {
                buffer.append(stringUnion);
                if (!ServiceEntityStringHelper.checkNullString(regex)) {
                    buffer.append(regex);
                }
            }
        }
        return buffer.toString();
    }

    public static String convStringArrayToString(String[] array) {
        return convStringArrayToString(array, ",");
    }

    public static String[] convListToStringArray(List<String> stringList) {
        return stringList.toArray(new String[0]);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List<?> removeDuplicatesList(List<?> arrayList) {
        HashSet set = new HashSet(arrayList);
        arrayList.clear();
        arrayList.addAll(set);
        return arrayList;
    }

    /**
     * Pluck raw array list to String list by get member's field value in
     * reflective way
     *
     * @param arrayList
     * @param fieldName
     * @return
     * @throws NoSuchFieldException
     */
    public static List<String> pluckList(List<?> arrayList, String fieldName)
            throws NoSuchFieldException {
        if (ServiceCollectionsHelper.checkNullList(arrayList)) {
            return null;
        }
        List<String> result = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            Object object = arrayList.get(i);
            try {
                Field field = ServiceEntityFieldsHelper.getServiceField(
                        object.getClass(), fieldName);
                if (field == null) {
                    continue;
                }
                field.setAccessible(true);
                Object fieldValue = field.get(object);
                result.add(fieldValue.toString());
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // continue
            }
        }
        return result;
    }

    /**
     * Pluck Service entity node list to String list by UUID value
     *
     * @param seNodeList
     * @return
     * @throws NoSuchFieldException
     */
    public static List<String> pluckListByUUID(
            List<ServiceEntityNode> seNodeList) throws NoSuchFieldException {
        if (ServiceCollectionsHelper.checkNullList(seNodeList)) {
            return null;
        }
        List<String> result = new ArrayList<>();
        for (ServiceEntityNode seNode : seNodeList) {
            result.add(seNode.getUuid());
        }
        return result;
    }

    /**
     * Join service entity list into long String by join field name value
     * together.
     *
     * @param seNodeList
     * @param fieldName
     * @param seperator
     * @return
     */
    public static String joinServiceEntityList(
            List<ServiceEntityNode> seNodeList, String fieldName,
            String seperator) {
        if (ServiceCollectionsHelper.checkNullList(seNodeList)) {
            return ServiceEntityStringHelper.EMPTYSTRING;
        }
        StringBuffer stringBuffer = new StringBuffer();
        String localSeperate = (seperator == null) ? " " : seperator;
        int index = 0, length = seNodeList.size();
        for (ServiceEntityNode seNode : seNodeList) {
            String tempValue = ServiceEntityFieldsHelper
                    .getStrServiceFieldValueWrapper(seNode, fieldName);
            if (index < length - 1) {
                stringBuffer.append(tempValue + localSeperate);
            } else {
                stringBuffer.append(tempValue);
            }
            index++;
        }
        return stringBuffer.toString();
    }

    public static String convStringArrayToString(String[] array, String regex) {
        if (array == null || array.length == 0) {
            return null;
        }
        String result = ServiceEntityStringHelper.EMPTYSTRING;
        for (int i = 0; i < array.length; i++) {
            if (i < array.length - 1) {
                result = result + array[i] + regex;
            } else {
                result = result + array[i];
            }
        }
        return result;
    }

    public static String[] convertStringToArray(String rawString) {
        return convertStringToArray(rawString, ",");
    }

    public static String[] convertStringToArray(String rawString, String regex) {
        if (ServiceEntityStringHelper.checkNullString(rawString)) {
            return null;
        }
        return rawString.split(regex);
    }

    /**
     * Filter SENode from List by dynamic field name
     *
     * @param keyValue
     * @param rawList
     * @return
     */
    public static ServiceEntityNode filterSENodeOnline(Object keyValue,
                                                       String keyName, List<ServiceEntityNode> rawList) {
        List<ServiceEntityNode> resultList = filterSENodeListOnline(keyValue,
                keyName, rawList, true);
        if (!ServiceCollectionsHelper.checkNullList(resultList)) {
            return resultList.get(0);
        }
        return null;
    }

    public static Object filterOnline(Object keyValue,
                                      Function<Object, Object> keyValueCallback, List<?> rawList) {
        List<?> resultList = filterListOnline(keyValue, keyValueCallback,
                rawList, true);
        if (!ServiceCollectionsHelper.checkNullList(resultList)) {
            return resultList.get(0);
        }
        return null;
    }

    public static <T, U extends SEUIComModel> List<U> parseToUINodeList(List<T> rawList,
                                                                        Function<T, U> callback) {
        if (ServiceCollectionsHelper.checkNullList(rawList)) {
            return null;
        }
        List<U> resultList = new ArrayList<>();
        for (T t : rawList) {
            U seUIModel = callback.apply(t);
            if (seUIModel != null) {
                resultList.add(seUIModel);
            }
        }
        return resultList;
    }

    public static <T> List<ServiceEntityNode> parseToSENodeList(List<T> rawList,
                                                                Function<T, ServiceEntityNode> callback) {
        if (ServiceCollectionsHelper.checkNullList(rawList)) {
            return null;
        }
        List<ServiceEntityNode> resultList = new ArrayList<>();
        for (T t : rawList) {
            ServiceEntityNode serviceEntityNode = callback.apply(t);
            if (serviceEntityNode != null) {
                ServiceCollectionsHelper.mergeToList(resultList, serviceEntityNode);
            }
        }
        return resultList;
    }

    public static <T> List<T> buildServiceModelList(List<ServiceEntityNode> rawSEList,
                                                    Function<ServiceEntityNode, T> callback) {
        if (ServiceCollectionsHelper.checkNullList(rawSEList)) {
            return null;
        }
        List<T> resultList = new ArrayList<>();
        for (ServiceEntityNode serviceEntityNode : rawSEList) {
            T serviceModel = callback.apply(serviceEntityNode);
            if (serviceModel != null) {
                resultList.add(serviceModel);
            }
        }
        return resultList;
    }

    public static <T> void forEach(List<T> rawList, Function<T, T> callback) {
        if (checkNullList(rawList)) {
            return;
        }
        for (T item : rawList) {
            callback.apply(item);
        }
    }

    public static <T> void forEachArray(T[] rawArray, Function<T, T> callback) {
        if (checkNullArray(rawArray)) {
            return;
        }
        for (T item : rawArray) {
            callback.apply(item);
        }
    }

    public static <T> void traverseListInterrupt(List<T> rawList, IExecutorInTraverse<T> executorInTraverse)
            throws DocActionException {
        if (checkNullList(rawList)) {
            return;
        }
        for (T item : rawList) {
            Boolean result = executorInTraverse.execute(item);
            if (!result) {
                break;
            }
        }
    }

    public interface IExecutorInTraverse<T> {
        Boolean execute(T item) throws DocActionException;
    }

    /**
     * Filter SENode List by dynamic field name
     *
     * @param keyValue
     * @param keyValueCallback
     * @param rawList
     * @param fastSkip         : only one member in array matches the condition
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List<?> filterListOnline(Object keyValue,
                                           Function<Object, Object> keyValueCallback, List<?> rawList,
                                           boolean fastSkip) {
        if (checkNullList(rawList)) {
            return null;
        }
        if (keyValue == null) {
            return null;
        }
        if (ServiceEntityStringHelper.checkNullString(keyValue.toString())) {
            return null;
        }
        if (keyValueCallback == null) {
            return null;
        }
        List resultList = new ArrayList<>();
        for (Object item : rawList) {
            try {
                Object fieldValue = keyValueCallback.apply(item);
                if (keyValue.equals(fieldValue)) {
                    resultList.add(item);
                    if (fastSkip) {
                        return resultList;
                    }
                }
            } catch (SecurityException e) {
                // do othing
            } catch (IllegalArgumentException e) {
                // do nothing
            }
        }
        return resultList;
    }

    /**
     * Filter SENode List by dynamic field name
     *
     * @param filterCallback
     * @param rawList        : only one member in array matches the condition
     * @return
     */
    public static <T> T filterOnline(List<T> rawList,
                                     Function<T, Boolean> filterCallback) {
        if (checkNullList(rawList)) {
            return null;
        }
        if (filterCallback == null) {
            return null;
        }
        List<T> resultList = ServiceCollectionsHelper.filterListOnline(rawList, filterCallback, true);
        if (ServiceCollectionsHelper.checkNullList(resultList)) {
            return null;
        } else {
            return resultList.get(0);
        }
    }

    /**
     * Filter SENode List by dynamic field name
     *
     * @param filterCallback
     * @param rawList
     * @param fastSkip       : only one member in array matches the condition
     * @return
     */
    public static <T> List<T> filterListOnline(List<T> rawList,
                                               Function<T, Boolean> filterCallback, boolean fastSkip) {
        if (checkNullList(rawList)) {
            return null;
        }
        if (filterCallback == null) {
            return null;
        }
        List<T> resultList = new ArrayList<T>();
        for (T item : rawList) {
            try {
                Boolean filterResult = filterCallback.apply(item);
                if (filterResult) {
                    resultList.add(item);
                    if (fastSkip) {
                        return resultList;
                    }
                }
            } catch (SecurityException e) {
                // do othing
            } catch (IllegalArgumentException e) {
                // do nothing
            }
        }
        return resultList;
    }

    /**
     * Filter SENode List by dynamic field name
     *
     * @param keyValue
     * @param keyName
     * @param rawList
     * @param fastSkip : only one member in array matches the condition
     * @return
     */
    public static <T> List<T> filterSENodeListOnline(
            Object keyValue, String keyName, List<T> rawList,
            boolean fastSkip) {
        if (checkNullList(rawList)) {
            return null;
        }
        if (keyValue == null) {
            return null;
        }
        if (ServiceEntityStringHelper.checkNullString(keyValue.toString())) {
            return null;
        }
        if (ServiceEntityStringHelper.checkNullString(keyName)) {
            return null;
        }
        return filterSENodeListOnline(asList(new ServiceBasicKeyStructure(keyValue, keyName)), rawList, fastSkip);
    }

    /**
     * Filter SENode List by dynamic field name
     *
     * @param keyList
     * @param rawList
     * @param fastSkip : only one member in array matches the condition
     * @return
     */
    public static <T> List<T> filterSENodeListOnline(
            List<ServiceBasicKeyStructure> keyList, List<T> rawList,
            boolean fastSkip) {
        if (checkNullList(rawList)) {
            return null;
        }
        if (checkNullList(keyList)) {
            return null;
        }
        List<T> resultList = new ArrayList<>();
        for (T seNode : rawList) {
            boolean matchFlag = true;
            for (ServiceBasicKeyStructure keyStructure : keyList) {
                try {
                    matchFlag = ServiceReflectiveHelper.checkValueEqual(keyStructure.getKeyName(), seNode,
                            keyStructure.getKeyValue());
                    if (!matchFlag) {
                        break;
                    }
                } catch (NoSuchFieldException | SecurityException | IllegalArgumentException |
                         IllegalAccessException e) {
                    matchFlag = false;
                    break;
                }
            }
            if (matchFlag) {
                resultList.add(seNode);
                if (fastSkip) {
                    return resultList;
                }
            }
        }
        return resultList;
    }

    /**
     * Filter SENode List by dynamic field name
     *
     * @param keyValue
     * @param rawList
     * @param fastSkip : only one member in array matches the condition
     * @return
     */
    public static List<ServiceEntityNode> filterSENodeListOnline(
            Object keyValue,
            Function<ServiceEntityNode, Object> keyValueCallback,
            List<ServiceEntityNode> rawList, boolean fastSkip) {
        if (checkNullList(rawList)) {
            return null;
        }
        if (keyValue == null) {
            return null;
        }
        if (ServiceEntityStringHelper.checkNullString(keyValue.toString())) {
            return null;
        }
        if (keyValueCallback == null) {
            return null;
        }
        List<ServiceEntityNode> resultList = new ArrayList<>();
        for (ServiceEntityNode seNode : rawList) {
            try {
                Object fieldValue = keyValueCallback.apply(seNode);
                if (ServiceReflectiveHelper.checkValueEqual(fieldValue, keyValue)) {
                    resultList.add(seNode);
                    if (fastSkip) {
                        return resultList;
                    }
                }
            } catch (SecurityException | IllegalArgumentException e) {
                // do nothing, just continue
            }
        }
        return resultList;
    }

    /**
     * Filter SENode List by dynamic field name and check callback function
     *
     * @param rawList
     * @param fastSkip : only one member in array matches the condition
     * @return
     */
    public static List<ServiceEntityNode> filterSENodeListOnline(
            Function<ServiceEntityNode, Boolean> keyValueCallback,
            List<ServiceEntityNode> rawList, boolean fastSkip) {
        if (checkNullList(rawList)) {
            return null;
        }
        if (keyValueCallback == null) {
            return null;
        }
        List<ServiceEntityNode> resultList = new ArrayList<>();
        for (ServiceEntityNode seNode : rawList) {
            try {
                if (keyValueCallback.apply(seNode)) {
                    resultList.add(seNode);
                    if (fastSkip) {
                        return resultList;
                    }
                }
            } catch (SecurityException | IllegalArgumentException e) {
                // do nothing, just continue
            }
        }
        return resultList;
    }

    /**
     * Filter out the service entity node by uuid
     *
     * @param uuid
     * @param rawList
     * @return
     */
    public static ServiceEntityNode filterSENodeOnline(String uuid,
                                                       List<ServiceEntityNode> rawList) {
        if (checkNullList(rawList)) {
            return null;
        }
        if (ServiceEntityStringHelper.checkNullString(uuid)) {
            return null;
        }
        for (ServiceEntityNode seNode : rawList) {
            if (uuid.equals(seNode.getUuid())) {
                return seNode;
            }
        }
        return null;
    }

    /**
     * Filter out the service entity node by key and keyValueCallback
     *
     * @param key
     * @param rawList
     * @return
     */
    public static ServiceEntityNode filterSENodeOnline(Object key,
                                                       List<ServiceEntityNode> rawList,
                                                       Function<ServiceEntityNode, Object> keyValueCallback) {
        List<ServiceEntityNode> resultList = filterSENodeListOnline(key,
                keyValueCallback, rawList, true);
        if (ServiceCollectionsHelper.checkNullList(resultList)) {
            return null;
        }
        return resultList.get(0);
    }

    /**
     * Filter out one single type SE node list from raw list by checking name
     *
     * @param name
     * @param rawList
     * @return
     */
    public static ServiceEntityNode filterSENodeByName(String name,
                                                       List<ServiceEntityNode> rawList) {
        if (ServiceCollectionsHelper.checkNullList(rawList)) {
            return null;
        }
        if (ServiceEntityStringHelper.checkNullString(name)) {
            return null;
        }
        for (ServiceEntityNode seNode : rawList) {
            if (name.equals(seNode.getName())) {
                return seNode;
            }
        }
        return null;
    }

    /**
     * Filter out one single type SE node list from raw list by checking name
     *
     * @param id
     * @param rawList
     * @return
     */
    public static ServiceEntityNode filterSENodeById(String id,
                                                     List<ServiceEntityNode> rawList) {
        if (ServiceCollectionsHelper.checkNullList(rawList)) {
            return null;
        }
        if (ServiceEntityStringHelper.checkNullString(id)) {
            return null;
        }
        for (ServiceEntityNode seNode : rawList) {
            if (id.equals(seNode.getId())) {
                return seNode;
            }
        }
        return null;
    }

    /**
     * Filter out one single type SE node list from raw list by checking service
     * entity name
     *
     * @param serviceEntityName
     * @param rawList
     * @return
     */
    public static List<ServiceEntityNode> filterSENodeListByServiceEnityName(
            String serviceEntityName, List<ServiceEntityNode> rawList) {
        if (ServiceCollectionsHelper.checkNullList(rawList)) {
            return null;
        }
        if (ServiceEntityStringHelper.checkNullString(serviceEntityName)) {
            return null;
        }
        List<ServiceEntityNode> resultList = new ArrayList<>();
        for (ServiceEntityNode seNode : rawList) {
            if (serviceEntityName.equals(seNode.getServiceEntityName())) {
                resultList.add(seNode);
            }
        }
        return resultList;
    }

    /**
     * Filter out the service entity node list by parent node uuid
     *
     * @param parentUUID
     * @param rawList
     * @return
     */
    public static List<ServiceEntityNode> filterSENodeByParentUUID(
            String parentUUID, List<ServiceEntityNode> rawList) {
        if (ServiceCollectionsHelper.checkNullList(rawList)) {
            return null;
        }
        if (ServiceEntityStringHelper.checkNullString(parentUUID)) {
            return null;
        }
        List<ServiceEntityNode> resultList = new ArrayList<>();
        for (ServiceEntityNode seNode : rawList) {
            if (parentUUID.equals(seNode.getParentNodeUUID())) {
                resultList.add(seNode);
            }
        }
        return resultList;
    }

    /**
     * Filter out the service entity node by root node uuid
     *
     * @param rootUUID
     * @param rawList
     * @return
     */
    public static List<ServiceEntityNode> filterSENodeByRootUUID(
            String rootUUID, List<ServiceEntityNode> rawList) {
        if (ServiceCollectionsHelper.checkNullList(rawList)) {
            return null;
        }
        if (ServiceEntityStringHelper.checkNullString(rootUUID)) {
            return null;
        }
        List<ServiceEntityNode> resultList = new ArrayList<>();
        for (ServiceEntityNode seNode : rawList) {
            if (rootUUID.equals(seNode.getRootNodeUUID())) {
                resultList.add(seNode);
            }
        }
        return resultList;
    }

    /**
     * Merges two lists into a single list by directly concatenating their elements, preserving the original order.
     * <p>
     * This method is null- and empty-safe. If {@code list1} or {@code list2} is {@code null} or empty,
     * those lists contribute no elements to the result. The order of elements in {@code list1} appears
     * first, followed by the order of elements in {@code list2}.
     * </p>
     *
     * @param list1 the first list to merge; may be {@code null} or empty
     * @param list2 the second list to merge; may be {@code null} or empty
     * @return a new {@link java.util.ArrayList} containing the combined elements of {@code list1}
     *         and {@code list2}. If both lists are {@code null} or empty, the returned list will be empty.
     */
    public  static <T> List<T> directMergeLists(List<T> list1, List<T> list2) {
        List<T> resultList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(list1)) {
            resultList.addAll(list1);
        }
        if (!ServiceCollectionsHelper.checkNullList(list1)) {
            resultList.addAll(list2);
        }
        return resultList;
    }

    /**
     * Merge to Service Entity List with unique UUID
     *
     * @param rawSEList
     * @param seNode
     */
    public static List<ServiceEntityNode> mergeToList(List<ServiceEntityNode> rawSEList,
                                                      ServiceEntityNode seNode) {
        if (rawSEList == null) {
            rawSEList = new ArrayList<>();
            rawSEList.add(seNode);
            return rawSEList;
        }
        if (rawSEList.size() == 0) {
            rawSEList.add(seNode);
            return rawSEList;
        }
        for (ServiceEntityNode tmpSENode : rawSEList) {
            if (seNode.getUuid().equals(tmpSENode.getUuid())) {
                // replace existed one
                rawSEList.remove(tmpSENode);
                rawSEList.add(seNode);
                return rawSEList;
            }
        }
        rawSEList.add(seNode);
        return rawSEList;
    }

    public static class ContainsSEList {

        public static final int NONE_CONTAINS = 0;

        public static final int PART_CONTAINS = 1;

        public static final int FULL_CONTAINS = 2;

        public static final int EMPTY_SUB = 3;
    }

    public static int checkContainsSubList(List<ServiceEntityNode> fullSEList, List<ServiceEntityNode> subSEList) {
        if (ServiceCollectionsHelper.checkNullList(fullSEList)) {
            return ContainsSEList.NONE_CONTAINS;
        }
        if (ServiceCollectionsHelper.checkNullList(subSEList)) {
            return ContainsSEList.EMPTY_SUB;
        }
        List<String> subUUIDList = subSEList.stream().map(ServiceEntityNode::getUuid).collect(Collectors.toList());
        boolean containsFlag = false;
        boolean notContainsFlag = false;
        for (String subUUID : subUUIDList) {
            List<ServiceEntityNode> tempSEList = fullSEList.stream().filter(serviceEntityNode -> {
                return serviceEntityNode.getUuid().equals(subUUID);
            }).collect(Collectors.toList());
            if (!ServiceCollectionsHelper.checkNullList(tempSEList)) {
                // in case contains for this subUUID
                containsFlag = true;
            } else {
                notContainsFlag = true;
            }
        }
        if (containsFlag) {
            if (notContainsFlag) {
                return ContainsSEList.PART_CONTAINS;
            } else {
                return ContainsSEList.FULL_CONTAINS;
            }
        } else {
            if (notContainsFlag) {
                return ContainsSEList.NONE_CONTAINS;
            } else {
                return ContainsSEList.NONE_CONTAINS;
            }
        }
    }

    public static <T> List<T> subList(List<T> rawList, int start, int length) {
        if (ServiceCollectionsHelper.checkNullList(rawList)) {
            return null;
        }
        List<T> tempList = rawList;
        int recordsTotal = rawList.size();
        int pageEnd = start + length; // Page End might be larger than total size
        if (length > 0) {
            if (recordsTotal > pageEnd) {
                // In case Page End smaller than total size, means not the end page
                tempList = new ArrayList<T>(rawList.subList(start,
                        pageEnd));
            } else {
                // In case Page End larger than total size, means the end page
                tempList = new ArrayList<T>(rawList.subList(start,
                        recordsTotal));
            }
            return tempList;
        }
        return null;
    }

    /**
     * Merge to Service Entity List with unique UUID
     *
     * @param rawRawList
     * @param stringValue
     */
    public static void mergeToList(List<String> rawRawList, String stringValue) {
        if (rawRawList == null) {
            return;
        }
        if (rawRawList.size() == 0) {
            rawRawList.add(stringValue);
        }
        for (String tmpString : rawRawList) {
            if (stringValue.equals(tmpString)) {
                return;
            }
        }
        rawRawList.add(stringValue);
    }

    /**
     * Merge to Service Entity List with unique UUID
     *
     * @param rawList
     * @param addedList
     */
    public static void mergeToList(List<ServiceEntityNode> rawList,
                                   List<ServiceEntityNode> addedList) {
        if (rawList == null) {
            return;
        }
        if (addedList == null || addedList.size() == 0) {
            return;
        }
        for (ServiceEntityNode tmpSENode : addedList) {
            mergeToList(rawList, tmpSENode);
        }
    }

    @SuppressWarnings({"rawtypes"})
    public static List<?> mergeToTreeListCore(List<?> rawList,
                                              List<?> allList,
                                              Function<Object, Object> parentNodeValueCallback,
                                              Function<Object, Object> keyValueCallback) {
        if (ServiceCollectionsHelper.checkNullList(rawList)) {
            return null;
        }
        List<?> resultList = new ArrayList<>();
        mergeToList(resultList, rawList, keyValueCallback);
        for (int i = 0; i < rawList.size(); i++) {
            Object keyValue = parentNodeValueCallback.apply(rawList.get(i));
            Object parentNode = filterOnline(keyValue,
                    keyValueCallback, allList);
            List parentList = recurGetParentNodeList(parentNode,
                    parentNodeValueCallback, keyValueCallback, allList);
            if (!ServiceCollectionsHelper.checkNullList(parentList)) {
                mergeToList(resultList, parentList, keyValueCallback);
            }
        }
        return resultList;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static List recurGetParentNodeList(Object currentNode,
                                               Function<Object, Object> parentNodeValueCallback,
                                               Function<Object, Object> keyValueCallback, List allList) {
        List resultList = new ArrayList<>();
        if (currentNode == null) {
            return resultList;
        }
        resultList.add(currentNode);
        Object keyValue = parentNodeValueCallback.apply(currentNode);
        Object parentNode = filterOnline(keyValue,
                keyValueCallback, allList);
        if (parentNode != null) {
            // In case retrieve parent
            List<?> parentList = recurGetParentNodeList(parentNode,
                    parentNodeValueCallback, keyValueCallback, allList);
            if (!ServiceCollectionsHelper.checkNullList(parentList)) {
                mergeToList(resultList, parentList, keyValueCallback);
            }
        }
        return resultList;
    }

    /**
     * Merge to Service Entity List with unique UUID
     *
     * @param rawList
     * @param obj
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void mergeToListUnit(List rawList, Object obj,
                                       Function<Object, Object> keyValueCallback) {
        if (obj == null) {
            return;
        }
        Object keyValue = keyValueCallback.apply(obj);
        if (keyValue == null) {
            // In case null value, skip
            return;
        }
        if (ServiceCollectionsHelper.checkNullList(rawList)) {
            // In case null rawList, just return obj as list
            List<Object> resultList = new ArrayList<>();
            resultList.add(obj);
        }
        for (Object tmpObject : rawList) {
            // In case duplicate value founded, just return
            if (keyValue.equals(keyValueCallback.apply(tmpObject))) {
                return;
            }
        }
        rawList.add(obj);
    }

    /**
     * Merge to Service Entity List with unique UUID
     *
     * @param rawList
     * @param addedList
     */
    public static void mergeToList(List<?> rawList,
                                   List<?> addedList, Function<Object, Object> keyValueCallback) {
        if (rawList == null) {
            return;
        }
        if (addedList == null || addedList.size() == 0) {
            return;
        }
        for (Object tempNode : addedList) {
            mergeToListUnit(rawList, tempNode, keyValueCallback);
        }
    }

    /**
     * Direct add one list to another without duplicate check
     *
     * @param rawList
     * @param addedList
     * @param <T>
     */
    public static <T> void directAddToList(List<T> rawList, List<T> addedList) {
        if (rawList == null) {
            return;
        }
        if (addedList == null || addedList.size() == 0) {
            return;
        }
        for (T tempNode : addedList) {
            rawList.add(tempNode);
        }
    }

    /**
     * Compared with 2 se node list, new list and old list, get the list to be
     * deleted. If the union in old list doesn't exist in new list, then it
     * should be deleted.
     *
     * @param newSeNodeList
     * @param oldSeNodeList
     * @return
     */
    public static List<ServiceEntityNode> getToDeleteServiceNodeList(
            List<ServiceEntityNode> newSeNodeList,
            List<ServiceEntityNode> oldSeNodeList) {
        if (checkNullList(oldSeNodeList)) {
            return null;
        }
        if (checkNullList(newSeNodeList)) {
            return oldSeNodeList;
        }
        List<ServiceEntityNode> result = new ArrayList<>();
        for (ServiceEntityNode seNode : oldSeNodeList) {
            ServiceEntityNode seNodeInNewList = filterSENodeOnline(
                    seNode.getUuid(), oldSeNodeList);
            if (seNodeInNewList == null) {
                result.add(seNode);
            }
        }
        return result;
    }

    /**
     * Get the differentiate item list in the `allItemList` which is not presented in the `selectedItemList`.
     * @param selectedItemList the list of selected items
     * @param allItemList the complete list of items to diff against
     * @return a list of items from allItemList whose UUID is not present in selectedItemList
     * @throws DocActionException
     */
    public static List<ServiceEntityNode> getDiffItemList(List<ServiceEntityNode> allItemList, List<ServiceEntityNode> selectedItemList) throws DocActionException {
        try {
            List<String> selectedUuids = ServiceCollectionsHelper.pluckList(selectedItemList, IServiceEntityNodeFieldConstant.UUID);
            return allItemList.stream()
                    .filter(item -> item != null && !selectedUuids.contains(item.getUuid()))
                    .collect(Collectors.toList());
        } catch (NoSuchFieldException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        }
    }

    /**
     * Merge 2 maps to one
     *
     * @param targetMap : The map to be merge as one com map as output result
     * @param addMap1
     */
    public static void mergeMap(Map<String, String> targetMap,
                                Map<String, String> addMap1) {
        if (targetMap == null) {
            return;
        }
        if (addMap1 == null || addMap1.size() == 0) {
            return;
        }
        Set<String> keys = addMap1.keySet();
        for (String key : keys) {
            if (targetMap.containsKey(key)) {
                continue;
            }
            String value = addMap1.get(key);
            if (value == null
                    || value.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
                continue;
            }
            targetMap.put(key, value);
        }
    }

    public static <T> void mergeToStringListMap(T key, String value, Map<T, List<String>> targetMap) {
        if (key == null || ServiceEntityStringHelper.checkNullString(value) || targetMap == null) {
            return;
        }
        targetMap.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    }

    /**
     * Merge 2 maps to one with overwrite function
     *
     * @param targetMap : The map to be merge as one com map as output result
     * @param addMap1
     */
    public static void mergeIntMap(Map<Integer, String> targetMap,
                                   Map<Integer, String> addMap1, boolean overwriteFlag) {
        if (targetMap == null) {
            return;
        }
        if (addMap1 == null || addMap1.size() == 0) {
            return;
        }
        Set<Integer> keys = addMap1.keySet();
        for (Integer key : keys) {
            if (targetMap.containsKey(key) && !overwriteFlag) {
                continue;
            }
            String value = addMap1.get(key);
            if (ServiceEntityStringHelper.checkNullString(value)) {
                continue;
            }
            targetMap.put(key, value);
        }
    }

    /**
     * Get Map key by String value, return 0 when can not found value matches
     *
     * @param targetMap
     * @param value
     * @return
     */
    public static Integer getKeyByValue(Map<Integer, String> targetMap,
                                        String value) {
        if (targetMap == null) {
            return 0;
        }
        Set<Integer> keySet = targetMap.keySet();
        Iterator<Integer> it = keySet.iterator();
        while (it.hasNext()) {
            int key = it.next();
            if (targetMap.get(key).equals(value)) {
                return key;
            }
        }
        return 0;
    }

    public static Map<String, String> convertToStringMap(Map<?, ?> rawMap) {
        Map<String, String> resultMap = new HashMap<>();
        Set<?> keySets = rawMap.keySet();
        for (Object key : keySets) {
            resultMap.put(key.toString(), rawMap.get(key).toString());
        }
        return resultMap;
    }

    /**
     * Get Map key by String value, return null when can not found value matches
     *
     * @param targetMap
     * @param value
     * @return
     */
    public static String getStringKeyByValue(Map<String, String> targetMap,
                                             String value) {
        if (targetMap == null) {
            return null;
        }
        Set<String> keySet = targetMap.keySet();
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
            String key = it.next();
            if (targetMap.get(key).equals(value)) {
                return key;
            }
        }
        return null;
    }

}
