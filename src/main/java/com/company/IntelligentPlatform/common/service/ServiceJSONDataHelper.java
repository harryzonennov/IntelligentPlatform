package com.company.IntelligentPlatform.common.service;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.SEUIModelFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.IServiceJSONBasicErrorCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

public class ServiceJSONDataHelper {

    public static final String DATA_LIST = "DataList";

    public static String genJSONList(List<ServiceEntityNode> seNodeList)
            throws ServiceJSONDataException {
        String content = ServiceEntityStringHelper.EMPTYSTRING;
        int index = 0;
        for (ServiceEntityNode seNode : seNodeList) {
            if (index < seNodeList.size()) {
                content = content + genJSONUnit(seNode) + ",\n";
            } else {
                content = content + genJSONUnit(seNode);
            }
        }
        return "{\"" + DATA_LIST + "\":[" + content + "]}";
    }

    public static String genJSONList(List<ServiceEntityNode> seNodeList,
                                     List<String> fieldNameList) throws ServiceJSONDataException {
        String content = ServiceEntityStringHelper.EMPTYSTRING;
        int index = 0;
        for (ServiceEntityNode seNode : seNodeList) {
            index++;
            if (index < seNodeList.size()) {
                content = content + genJSONUnit(seNode, fieldNameList) + ",\n";
            } else {
                content = content + genJSONUnit(seNode, fieldNameList);
            }
        }
        return "{\"" + DATA_LIST + "\":[" + content + "]}";
    }

    public static String genJSONList(List<Object> objList, String label)
            throws ServiceJSONDataException {
        String content = ServiceEntityStringHelper.EMPTYSTRING;
        int index = 0;
        for (Object object : objList) {
            index++;
            if (index < objList.size()) {
                content = content + genJSONUnit(object) + ",\n";
            } else {
                content = content + genJSONUnit(object);
            }
        }
        return "{\"" + label + "\":[" + content + "]}";
    }

    public static String genSimpleJSONList(List<Object> objList, String label)
            throws ServiceJSONDataException {
        String content = ServiceEntityStringHelper.EMPTYSTRING;
        int index = 0;
        for (Object object : objList) {
            index++;
            if (index < objList.size()) {
                content = content + genJSONUnit(object) + ",\n";
            } else {
                content = content + genJSONUnit(object);
            }
        }
        return "\"" + label + "\":[" + content + "]";
    }

    public static String genJSONUIModelList(List<SEUIComModel> uiModelList,
                                            String header, SimpleDateFormat dateFormat)
            throws ServiceJSONDataException {
        String content = ServiceEntityStringHelper.EMPTYSTRING;
        int index = 0;
        for (SEUIComModel uiModel : uiModelList) {
            if (index < uiModelList.size() - 1) {
                //content = content + genJSONUIModel(uiModel, dateFormat) + ",\n";
                content = content + JSONObject.fromObject(uiModel) + ",\n";
            } else {
                content = content + JSONObject.fromObject(uiModel);
            }
            index++;
        }
        return "\"" + header + "\":[" + content + "]";
    }

    public static String genJSONUIModelList(List<SEUIComModel> uiModelList,
                                            List<String> fieldNameList, String header,
                                            SimpleDateFormat dateFormat) throws ServiceJSONDataException {
        String content = ServiceEntityStringHelper.EMPTYSTRING;
        int index = 0;
        for (SEUIComModel uiModel : uiModelList) {
            index++;
            if (index < uiModelList.size()) {
                content = content
                        + genJSONUIModel(uiModel, fieldNameList, dateFormat)
                        + ",\n";
            } else {
                content = content
                        + genJSONUIModel(uiModel, fieldNameList, dateFormat);
            }
        }
        return "{\"" + header + "\":[" + content + "]}";
    }

    /**
     * Generate JSON data by specified value fields from service entity node
     *
     * @param seNode
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static String genJSONUnit(ServiceEntityNode seNode,
                                     List<String> fieldNameList) throws ServiceJSONDataException {
        String content = ServiceEntityStringHelper.EMPTYSTRING;
        int index = 0;
        for (String fieldName : fieldNameList) {
            index++;
            Field field;
            try {
                field = ServiceEntityFieldsHelper.getServiceField(seNode,
                        fieldName);
                if (index < fieldNameList.size()) {
                    content = content + genJSONField(field, seNode) + ",";
                } else {
                    content = content + genJSONField(field, seNode);
                }
            } catch (NoSuchFieldException e) {
                throw new ServiceJSONDataException(
                        ServiceJSONDataException.TYPE_CANNOT_FIND_FIELD,
                        fieldName, seNode.getServiceEntityName());
            } catch (SecurityException e) {
                throw new ServiceJSONDataException(
                        ServiceJSONDataException.TYPE_CANNOT_ILLEGAL_FIELD,
                        fieldName, seNode.getServiceEntityName());
            } catch (IllegalArgumentException e) {
                throw new ServiceJSONDataException(
                        ServiceJSONDataException.TYPE_CANNOT_ILLEGAL_FIELD,
                        fieldName, seNode.getServiceEntityName());
            } catch (IllegalAccessException e) {
                throw new ServiceJSONDataException(
                        ServiceJSONDataException.TYPE_CANNOT_ILLEGAL_FIELD,
                        fieldName, seNode.getServiceEntityName());
            }
        }
        return "{" + content + "}";
    }

    /**
     * Generate JSON data by all value fields from service entity node
     *
     * @param seNode
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static String genJSONUnit(ServiceEntityNode seNode)
            throws ServiceJSONDataException {
        String content = ServiceEntityStringHelper.EMPTYSTRING;
        List<Field> fieldList = ServiceEntityFieldsHelper.getFieldsList(seNode
                .getClass());
        int index = 0;
        for (Field field : fieldList) {
            index++;
            try {
                if (index < fieldList.size()) {
                    content = content + genJSONField(field, seNode) + ",";
                } else {
                    content = content + genJSONField(field, seNode);
                }
            } catch (IllegalArgumentException e) {
                throw new ServiceJSONDataException(
                        ServiceJSONDataException.TYPE_CANNOT_ILLEGAL_FIELD,
                        field.getName(), seNode.getServiceEntityName());
            } catch (IllegalAccessException e) {
                throw new ServiceJSONDataException(
                        ServiceJSONDataException.TYPE_CANNOT_ILLEGAL_FIELD,
                        field.getName(), seNode.getServiceEntityName());
            }
        }
        return "{" + content + "}";
    }

    /**
     * Generate JSON data by all value fields from service entity node
     *
     * @param seNode
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static String genJSONUnit(Object object)
            throws ServiceJSONDataException {
        String content = ServiceEntityStringHelper.EMPTYSTRING;
        List<Field> fieldList = ServiceEntityFieldsHelper.getSelfDefinedFieldsList(object
                .getClass());
        int index = 0;
        for (Field field : fieldList) {
            index++;
            try {
                if (index < fieldList.size()) {
                    content = content + genJSONField(field, object) + ",";
                } else {
                    content = content + genJSONField(field, object);
                }
            } catch (IllegalArgumentException e) {
                throw new ServiceJSONDataException(
                        ServiceJSONDataException.TYPE_CANNOT_ILLEGAL_FIELD,
                        field.getName(), object.getClass().getName());
            } catch (IllegalAccessException e) {
                throw new ServiceJSONDataException(
                        ServiceJSONDataException.TYPE_CANNOT_ILLEGAL_FIELD,
                        field.getName(), object.getClass().getName());
            }
        }
        return "{" + content + "}";
    }

    /**
     * Generate JSON data by all value fields from service entity node
     *
     * @param seNode
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static String genSimpleJSONUnit(Object object)
            throws ServiceJSONDataException {
        String content = ServiceEntityStringHelper.EMPTYSTRING;
        List<Field> fieldList = ServiceEntityFieldsHelper.getSelfDefinedFieldsList(object
                .getClass());
        int index = 0;
        for (Field field : fieldList) {
            index++;
            try {
                if (index < fieldList.size()) {
                    content = content + genJSONField(field, object) + ",";
                } else {
                    content = content + genJSONField(field, object);
                }
            } catch (IllegalArgumentException e) {
                throw new ServiceJSONDataException(
                        ServiceJSONDataException.TYPE_CANNOT_ILLEGAL_FIELD,
                        field.getName(), object.getClass().getName());
            } catch (IllegalAccessException e) {
                throw new ServiceJSONDataException(
                        ServiceJSONDataException.TYPE_CANNOT_ILLEGAL_FIELD,
                        field.getName(), object.getClass().getName());
            }
        }
        return content;
    }

    /**
     * Generate JSON data by specified value fields from service entity node
     *
     * @param seNode
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static String genJSONUIModel(SEUIComModel uiModel,
                                        List<String> fieldNameList, SimpleDateFormat dateFormat)
            throws ServiceJSONDataException {
        String content = ServiceEntityStringHelper.EMPTYSTRING;
        int index = 0;
        for (String fieldName : fieldNameList) {
            index++;
            Field field;
            try {
                field = SEUIModelFieldsHelper.getServiceField(
                        uiModel.getClass(), fieldName);
                if (index < fieldNameList.size()) {
                    content = content
                            + genJSONField(field, uiModel, dateFormat) + ",";
                } else {
                    content = content
                            + genJSONField(field, uiModel, dateFormat);
                }
            } catch (NoSuchFieldException e) {
                throw new ServiceJSONDataException(
                        ServiceJSONDataException.TYPE_CANNOT_FIND_FIELD,
                        fieldName, uiModel.getClass().getSimpleName());
            } catch (SecurityException e) {
                throw new ServiceJSONDataException(
                        ServiceJSONDataException.TYPE_CANNOT_ILLEGAL_FIELD,
                        fieldName, uiModel.getClass().getSimpleName());
            } catch (IllegalArgumentException e) {
                throw new ServiceJSONDataException(
                        ServiceJSONDataException.TYPE_CANNOT_ILLEGAL_FIELD,
                        fieldName, uiModel.getClass().getSimpleName());
            } catch (IllegalAccessException e) {
                throw new ServiceJSONDataException(
                        ServiceJSONDataException.TYPE_CANNOT_ILLEGAL_FIELD,
                        fieldName, uiModel.getClass().getSimpleName());
            }
        }
        return "{" + content + "}";
    }

    /**
     * Generate JSON data by all value fields from UI model
     *
     * @param seNode
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static String genJSONUIModel(SEUIComModel uiModel,
                                        SimpleDateFormat dateFormat) throws ServiceJSONDataException {
        String content = ServiceEntityStringHelper.EMPTYSTRING;
        List<Field> fieldList = SEUIModelFieldsHelper.getFieldsList(uiModel
                .getClass());
        int index = 0;
        for (Field field : fieldList) {
            index++;
            try {
                if (index < fieldList.size()) {
                    content = content
                            + genJSONField(field, uiModel, dateFormat) + ",";
                } else {
                    content = content
                            + genJSONField(field, uiModel, dateFormat);
                }
            } catch (IllegalArgumentException e) {
                throw new ServiceJSONDataException(
                        ServiceJSONDataException.TYPE_CANNOT_ILLEGAL_FIELD,
                        field.getName(), uiModel.getClass().getSimpleName());
            } catch (IllegalAccessException e) {
                throw new ServiceJSONDataException(
                        ServiceJSONDataException.TYPE_CANNOT_ILLEGAL_FIELD,
                        field.getName(), uiModel.getClass().getSimpleName());
            }
        }
        return "{" + content + "}";
    }

    /**
     * [Internal method] Get JSON field pair by by field from SE node instance
     *
     * @param field
     * @param seNode
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    protected static String genJSONField(Field field, ServiceEntityNode seNode)
            throws IllegalArgumentException, IllegalAccessException {
        field.setAccessible(true);
        boolean stringTypeFlag = false;
        String stringTypeName = String.class.getSimpleName();
        String dateTypeName = Date.class.getSimpleName();
        String fieldTypeName = field.getType().getSimpleName();
        String content = "\"" + field.getName() + "\":";
        if (fieldTypeName.equals(stringTypeName)) {
            // Indicate this is "String" type field, need quotation later
            stringTypeFlag = true;
        }
        if (fieldTypeName.equals(dateTypeName)) {
            // Indicate this is "String" type field, need quotation later
            stringTypeFlag = true;
        }
        Object value = field.get(seNode);
        if (value == null)
            return content = content + "\"" + "\"";
        String valueField = field.get(seNode).toString();
        if (stringTypeFlag) {
            // In case a string type field, need to add quotation marks
            content = content + "\"" + valueField + "\"";
        } else {
            content = content + valueField;
        }
        return content;
    }

    /**
     * [Internal method] Get JSON field pair by by field from SE node instance
     *
     * @param field
     * @param seNode
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    protected static String genJSONField(Field field, Object object)
            throws IllegalArgumentException, IllegalAccessException {
        field.setAccessible(true);
        boolean stringTypeFlag = false;
        String stringTypeName = String.class.getSimpleName();
        String dateTypeName = Date.class.getSimpleName();
        String fieldTypeName = field.getType().getSimpleName();
        String content = "\"" + field.getName() + "\":";
        if (fieldTypeName.equals(stringTypeName)) {
            // Indicate this is "String" type field, need quotation later
            stringTypeFlag = true;
        }
        if (fieldTypeName.equals(dateTypeName)) {
            // Indicate this is "String" type field, need quotation later
            stringTypeFlag = true;
        }
        Object value = field.get(object);
        if (value == null)
            return content = content + "\"" + "\"";
        String valueField = field.get(object).toString();
        if (stringTypeFlag) {
            // In case a string type field, need to add quotation marks
            content = content + "\"" + valueField + "\"";
        } else {
            content = content + valueField;
        }
        return content;
    }

    /**
     * [Internal method] Get JSON field pair by by field from UI Model
     *
     * @param field
     * @param uiModel
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    protected static String genJSONField(Field field, SEUIComModel uiModel,
                                         SimpleDateFormat dateFormat) throws IllegalArgumentException,
            IllegalAccessException {
        field.setAccessible(true);
        boolean stringTypeFlag = false;
        String stringTypeName = String.class.getSimpleName();
        String dateTypeName = Date.class.getSimpleName();
        String fieldTypeName = field.getType().getSimpleName();
        String content = "\"" + field.getName() + "\":";
        Object value = field.get(uiModel);
        if (value == null)
            return content = content + "\"" + "\"";
        String valueField = field.get(uiModel).toString();
        if (fieldTypeName.equals(stringTypeName)) {
            // Indicate this is "String" type field, need quotation later
            stringTypeFlag = true;
        }
        if (fieldTypeName.equals(dateTypeName)) {
            // Indicate this is "String" type field, need quotation later
            stringTypeFlag = true;
            // also need convertor
            Date dateValue = (Date) field.get(uiModel);
            if (dateFormat == null) {
                // default dateFormat
                valueField = DefaultDateFormatConstant.DATE_TIME_FORMAT
                        .format(dateValue);
            } else {
                valueField = dateFormat.format(dateValue);
            }

        }

        if (stringTypeFlag) {
            // In case a string type field, need to add quotation marks
            content = content + "\"" + valueField + "\"";
        } else {
            content = content + valueField;
        }
        return content;
    }

    /**
     * parse JSON data to UI com model by JSON　data string
     *
     * @param jsonData
     * @param rawUIModel
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws ParseException
     */
    public static void parseUIModelJSON(String jsonData,
                                        SEUIComModel rawUIModel, SimpleDateFormat dateFormat)
            throws IllegalArgumentException, IllegalAccessException,
            ParseException {
        Field[] fieldArray = rawUIModel.getClass().getDeclaredFields();
        for (Field field : fieldArray) {
            // find the field
            JSONObject jsonObj = JSONObject.fromObject(jsonData);
            Object value = jsonObj.get(field.getName());
            if (!ServiceReflectiveHelper.checkNullValue(value, field.getType()
                    .getSimpleName())) {
                ServiceReflectiveHelper.reflectSetValue(field, rawUIModel,
                        value, dateFormat);
            }
        }
    }

    /**
     * API to generate default simple JSON response, both for success and error
     * response
     *
     * @param errorCode
     * @param errorMessage
     * @return
     */
    public static String genSimpleSEResponse(int errorCode, String errorMessage) {
        if (errorCode == IServiceJSONBasicErrorCode.DEF_OK) {
            // In case OK case
            String content = "{\"" + ServiceJSONDataConstants.ELE_ERROR_CODE
                    + "\":\"" + IServiceJSONBasicErrorCode.DEF_OK + "\"}";
            return content;
        } else {
            String content = "{\"" + ServiceJSONDataConstants.ELE_ERROR_CODE
                    + "\":\"" + IServiceJSONBasicErrorCode.DEF_OK + "\", \""
                    + ServiceJSONDataConstants.ELE_ERROR_MSG + "\":\""
                    + errorMessage + "\"}";
            return content;
        }
    }

}
