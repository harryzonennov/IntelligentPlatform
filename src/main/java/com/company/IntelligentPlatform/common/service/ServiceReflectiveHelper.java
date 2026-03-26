package com.company.IntelligentPlatform.common.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Basic helper class based on reflection technology
 * 
 * @author Zhang,Hang
 * 
 */
public class ServiceReflectiveHelper {

	public final static int EQUATOR_TYPE_DIGITAL = 1;

	public final static int EQUATOR_TYPE_OBJECT = 2;

	public final static int BASIC_TYPE_STR = 1;

	public final static int BASIC_TYPE_NUMBER = 2;

	public final static int BASIC_TYPE_DATE = 3;

	public final static int BASIC_TYPE_BOOLEAN = 4;

	public final static int BASIC_TYPE_OBJ = 5;

	/**
	 * Utility method to get one field name in reflective way
	 * 
	 * @param seNode
	 * @param fieldName
	 * @return
	 */
	public static Object getFieldValue(ServiceEntityNode seNode, String fieldName) {
		try {
			Field field = ServiceEntityFieldsHelper.getServiceField(seNode, fieldName);
			field.setAccessible(true);
			return field.get(seNode);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			return null;
		}
	}

	/**
	 * Utility method to get one field name in reflective way
	 *
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static Object getFieldValue(Object object, String fieldName) {
		try {
			Field field = ServiceEntityFieldsHelper.getServiceField(object.getClass(), fieldName);
			field.setAccessible(true);
			return field.get(object);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			return null;
		}
	}

	/**
	 * Utility method: set value to field only when old value is empty
	 * @param fieldName
	 * @param instance
	 * @param value
	 * @param dateFormat
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	public static void setValueToNullField(String fieldName, Object instance,
										   Object value, SimpleDateFormat dateFormat) throws NoSuchFieldException,
			IllegalAccessException {
		Field field = ServiceEntityFieldsHelper.getServiceField(instance.getClass(), fieldName);
		field.setAccessible(true);
		Object oldValue = field.get(instance);
		boolean nullValue = ServiceReflectiveHelper.checkNullValue(oldValue, field.getType().getSimpleName());
		if(nullValue){
			ServiceReflectiveHelper.reflectSetValue(field, instance, value, dateFormat);
		}
	}

	public static void reflectSetValue(String fieldName, Object instance,
									   Object value)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
		reflectSetValue(fieldName, instance, value, null);
	}

	public static void reflectSetValue(String fieldName, Object instance,
									   Object value, SimpleDateFormat dateFormat)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
		Field field = ServiceEntityFieldsHelper.getServiceField(instance.getClass(), fieldName);
		if(field == null){
			return;
		}
		field.setAccessible(true);
		reflectSetValue(field, instance, value, dateFormat);
	}

	/**
	 * using reflection tech to set value to Object
	 * 
	 * @param field
	 * @param instance
	 * @param value
	 * @throws NumberFormatException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws ParseException
	 */
	public static void reflectSetValue(Field field, Object instance,
			Object value, SimpleDateFormat dateFormat)
			throws NumberFormatException, IllegalArgumentException,
			IllegalAccessException {
		if (value == null) {
			return;
		}
		String dateTypeName = Date.class.getSimpleName();
		String intTypeName = int.class.getSimpleName();
		String floatTypeName = float.class.getSimpleName();
		String integarTypeName = Integer.class.getSimpleName();
		String doubleTypeName = double.class.getSimpleName();
		String doubleUpTypeName = Double.class.getSimpleName();
		String floatUpTypeName = Float.class.getSimpleName();
		field.setAccessible(true);
		String fieldType = field.getType().getSimpleName();
		if (doubleUpTypeName.equals(fieldType)
				|| doubleTypeName.equals(fieldType)) {
			field.setDouble(instance, Double.parseDouble(value.toString()));
			return;
		}
		if (floatTypeName.equals(fieldType)
				|| floatUpTypeName.equals(fieldType)) {
			field.setFloat(instance, Float.parseFloat(value.toString()));
			return;
		}
		if (integarTypeName.equals(fieldType) || intTypeName.equals(fieldType)) {
			field.setInt(instance, Integer.parseInt(value.toString()));
			return;
		}
		if (dateTypeName.equals(fieldType)) {
			try {
				if (dateFormat == null) {
					if(String.class.getSimpleName().equals(value.getClass().getSimpleName())){
						// using the default Date format
						Date dateValue = DefaultDateFormatConstant.DATE_TIME_FORMAT
								.parse(value.toString());
						field.set(instance, dateValue);
					}
					if(Date.class.getSimpleName().equals(value.getClass().getSimpleName())){
						// set date value directly
						field.set(instance, value);
					}
				} else {
					Date dateValue = dateFormat.parse(value.toString());
					field.set(instance, dateValue);
				}
			} catch (ParseException ex) {
				// Continue when parse date format error
			}
			return;
		}
		field.set(instance, value);
	}

	public static int getBasicType(Class<?> runtimeClass) {
		return getBasicType(runtimeClass.getSimpleName());
	}

	/**
	 * Logic to get basic field type: String, number, date
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public static int getBasicType(String fieldTypeName){
		String stringTypeName = String.class.getSimpleName();
		String dateTypeName = Date.class.getSimpleName();
		String intTypeName = int.class.getSimpleName();
		String floatTypeName = float.class.getSimpleName();
		String integarTypeName = Integer.class.getSimpleName();
		String doubleTypeName = double.class.getSimpleName();
		String doubleUpTypeName = Double.class.getSimpleName();
		String floatUpTypeName = Float.class.getSimpleName();
		String booleanTypeName = boolean.class.getSimpleName();
		String booleanUpTypeName = Boolean.class.getSimpleName();
		if (fieldTypeName.equals(stringTypeName)) {
			return BASIC_TYPE_STR;
		}
		if (fieldTypeName.equals(dateTypeName)) {
			return BASIC_TYPE_DATE;
		}
		if (fieldTypeName.equals(floatTypeName)) {
			return BASIC_TYPE_NUMBER;
		}
		if (fieldTypeName.equals(intTypeName)) {
			return BASIC_TYPE_NUMBER;
		}
		if (fieldTypeName.equals(doubleTypeName)) {
			return BASIC_TYPE_NUMBER;
		}
		if (fieldTypeName.equals(floatUpTypeName)) {
			return BASIC_TYPE_NUMBER;
		}
		if (fieldTypeName.equals(doubleUpTypeName)) {
			return BASIC_TYPE_NUMBER;
		}
		if (fieldTypeName.equals(integarTypeName)) {
			return BASIC_TYPE_NUMBER;
		}
		if (fieldTypeName.equals(booleanTypeName)) {
			return BASIC_TYPE_BOOLEAN;
		}
		if (fieldTypeName.equals(booleanUpTypeName)) {
			return BASIC_TYPE_BOOLEAN;
		}
		return BASIC_TYPE_OBJ;
	}

	public static int getEquatorType(Class<?> seClass, String fieldName)
			throws IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException {
		Field field = ServiceEntityFieldsHelper.getServiceField(seClass,
				fieldName);
		int basicType = getBasicType(field.getType());
		if (basicType == BASIC_TYPE_STR || basicType == BASIC_TYPE_OBJ ) {
			return EQUATOR_TYPE_OBJECT;
		}
		if (basicType == BASIC_TYPE_DATE || basicType == BASIC_TYPE_BOOLEAN) {
			return EQUATOR_TYPE_DIGITAL;
		}
		if (basicType == BASIC_TYPE_NUMBER) {
			return EQUATOR_TYPE_DIGITAL;
		}
		return 0;
	}

	/**
	 * Check and the default rule to decide, if current value is useless
	 * 
	 * @param value
	 * @param fieldTypeName
	 * @return
	 */
	public static boolean checkNullValue(Object value, String fieldTypeName) {
		int basicType = getBasicType(fieldTypeName);
		if ( basicType == BASIC_TYPE_BOOLEAN ) {
			return !((boolean) value);
		}
		// In case field type is [String]
		if (basicType == BASIC_TYPE_STR) {
			if (value == null)
				return true;
			return value.equals(ServiceEntityStringHelper.EMPTYSTRING);
		}
		if (basicType == BASIC_TYPE_DATE) {
			if (value == null)
				return true;
			return value.equals(ServiceEntityStringHelper.EMPTYSTRING);
		}
		if(basicType == BASIC_TYPE_NUMBER){
			if (value == null)
				return true;
			if (value.equals(0))
				return true;
			if (value.equals((float) 0))
				return true;
            return value.equals((double) 0);
        }
		return true;
	}

	/**
	 * Check Value equal dynamically
	 * @param fieldName
	 * @param instance
	 * @param targetValue
	 * @return
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	public static boolean checkValueEqual(String fieldName, Object instance,
										   Object targetValue) throws NoSuchFieldException,
			IllegalAccessException {
		Field field = ServiceEntityFieldsHelper.getServiceField(instance.getClass(), fieldName);
		field.setAccessible(true);
		Object curValue = field.get(instance);
		return checkValueEqual(curValue, targetValue, field.getType().getSimpleName());
	}

	/**
	 * Core Logic to check if value equals
	 * @param curValue
	 * @param targetValue
	 * @return
	 */
	public static boolean checkValueEqual(Object curValue, Object targetValue) {
//		if(curValue == null && targetValue == null){
//			// in case 2 are both null
//			return true;
//		}
		if(curValue == null || targetValue == null){
			// equal should not for null object
			return false;
		}
		return checkValueEqual(curValue, targetValue, targetValue.getClass().getSimpleName());
	}

	/**
	 * Core Logic to check if value equals
	 * @param curValue
	 * @param targetValue
	 * @param fieldTypeName
	 * @return
	 */
	public static boolean checkValueEqual(Object curValue, Object targetValue, String fieldTypeName) {
		int basicType = getBasicType(fieldTypeName);
		if ( basicType == BASIC_TYPE_BOOLEAN ) {
			return ((boolean) targetValue);
		}
		// In case field type is [String]
		if (basicType == BASIC_TYPE_STR) {
			return curValue.equals(targetValue);
		}
		if (basicType == BASIC_TYPE_DATE) {
			if (curValue == null)
				return false;
			return curValue.equals(targetValue);
		}
		if(basicType == BASIC_TYPE_NUMBER){
			if (curValue == null)
				return false;
			return curValue.toString().equals(targetValue.toString());
		}
		return false;
	}

	public static Method genGetMethod(String fieldName, Class<?> instanceType) {
		String getMethodHeader = genGetMethodHeader(fieldName);	
		try {
			Method getMethod = instanceType.getMethod(getMethodHeader);
			getMethod.setAccessible(true);
			return getMethod;
		} catch (NoSuchMethodException | SecurityException e) {
			return null;
		}
	}
	
	public static Method genSetMethod(Field field, Class<?> instanceType) {
		String setMethodHeader = genSetMethodHeader(field.getName());	
		try {
			Class<?>[] setMethodParas = { field.getType() };
			Method setMethod = instanceType.getMethod(setMethodHeader, setMethodParas);
			setMethod.setAccessible(true);
			return setMethod;
		} catch (NoSuchMethodException | SecurityException e) {
			return null;
		}
	}

	public static String genGetMethodHeader(String fieldName) {
		String getMethodHeader = "get"
				+ ServiceEntityStringHelper.headerToUpperCase(fieldName);
		return getMethodHeader;
	}

	public static String genSetMethodHeader(String fieldName) {
		String getMethodHeader = "set"
				+ ServiceEntityStringHelper.headerToUpperCase(fieldName);
		return getMethodHeader;
	}

	/**
	 * Utility method convert SENode to SE UI model in reflective way
	 * 
	 * @param serviceEntityNode
	 * @param seUIComModel
	 */
	public static void convToUIModelReflect(ServiceEntityNode serviceEntityNode, SEUIComModel seUIComModel){
		if(serviceEntityNode == null || seUIComModel == null){
			return;
		}
		seUIComModel.setUuid(serviceEntityNode.getUuid());
		seUIComModel.setParentNodeUUID(serviceEntityNode.getParentNodeUUID());
		seUIComModel.setRootNodeUUID(serviceEntityNode.getRootNodeUUID());
		seUIComModel.setClient(serviceEntityNode.getClient());
		if (serviceEntityNode.getCreatedTime() != null) {
			seUIComModel
					.setCreatedDate(DefaultDateFormatConstant.DATE_FORMAT
							.format(serviceEntityNode.getCreatedTime()));
		}
		List<Field> fieldList = ServiceEntityFieldsHelper
				.getServiceSelfDefinedFieldsList(serviceEntityNode.getClass(),
						ServiceEntityNode.class);
		Logger logger = LoggerFactory.getLogger(ServiceReflectiveHelper.class);
		if(!ServiceCollectionsHelper.checkNullList(fieldList)){
			for(Field field: fieldList){
				if(field.getName().equals(IServiceEntityNodeFieldConstant.UUID)){
					continue;
				}
				if(field.getName().equals(IServiceEntityNodeFieldConstant.PARENTNODEUUID)){
					continue;
				}
				if(field.getName().equals(IServiceEntityNodeFieldConstant.ROOTNODEUUID)){
					continue;
				}
				if(field.getName().equals(IServiceEntityNodeFieldConstant.CLIENT)){
					continue;
				}
				if(field.getName().equals(IServiceEntityNodeFieldConstant.CREATEDTIME)){
					continue;
				}
				Method getMethod = genGetMethod(field.getName(), serviceEntityNode.getClass());				
				Method setMethod = genSetMethod(field, seUIComModel.getClass());
				if(getMethod == null){
					logger.error("No Get Method for:" + field.getName() + " in " + serviceEntityNode.getClass().getSimpleName());
					continue;
				}
				if(setMethod == null){
					logger.error("No Set Method for:" + field.getName() + " in " + seUIComModel.getClass().getSimpleName());
					continue;
				}
				try {
					setMethod.invoke(seUIComModel, getMethod.invoke(serviceEntityNode));
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					logger.error("invoke error in :" + field.getName() + " in " + e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Utility method convert SENode to SE UI model in reflective way
	 * 
	 * @param seUIComModel
	 * @param rawEntity
	 */
	public static void convUIToModelReflect(SEUIComModel seUIComModel, ServiceEntityNode rawEntity){
		if(rawEntity == null || seUIComModel == null){
			return;
		}
		if(!ServiceEntityStringHelper.checkNullString(seUIComModel.getUuid())){
			rawEntity.setUuid(seUIComModel.getUuid());
		}
		if(!ServiceEntityStringHelper.checkNullString(seUIComModel.getParentNodeUUID())){
			rawEntity.setParentNodeUUID(seUIComModel.getParentNodeUUID());
		}
		if(!ServiceEntityStringHelper.checkNullString(seUIComModel.getRootNodeUUID())){
			rawEntity.setRootNodeUUID(seUIComModel.getRootNodeUUID());
		}
		if(!ServiceEntityStringHelper.checkNullString(seUIComModel.getClient())){
			rawEntity.setClient(seUIComModel.getClient());
		}
		List<Field> fieldList = ServiceEntityFieldsHelper
				.getServiceSelfDefinedFieldsList(rawEntity.getClass(),
						ServiceEntityNode.class);
		Logger logger = LoggerFactory.getLogger(ServiceReflectiveHelper.class);
		if(!ServiceCollectionsHelper.checkNullList(fieldList)){
			for(Field field: fieldList){
				if(field.getName().equals(IServiceEntityNodeFieldConstant.UUID)){
					continue;
				}
				if(field.getName().equals(IServiceEntityNodeFieldConstant.PARENTNODEUUID)){
					continue;
				}
				if(field.getName().equals(IServiceEntityNodeFieldConstant.ROOTNODEUUID)){
					continue;
				}
				if(field.getName().equals(IServiceEntityNodeFieldConstant.CLIENT)){
					continue;
				}
				if(field.getName().equals(IServiceEntityNodeFieldConstant.CREATEDTIME)){
					continue;
				}
				if(field.getName().equals(IServiceEntityNodeFieldConstant.CREATEDBY)){
					continue;
				}
				if(field.getName().equals(IServiceEntityNodeFieldConstant.LASTUPDATETIME)){
					continue;
				}
				if(field.getName().equals(IServiceEntityNodeFieldConstant.LASTUPDATEBY)){
					continue;
				}
				Method getMethod = genGetMethod(field.getName(), seUIComModel.getClass());				
				Method setMethod = genSetMethod(field, rawEntity.getClass());
				if(getMethod == null){
					logger.error("No Get Method for:" + field.getName() + " in " + seUIComModel.getClass().getSimpleName());
					continue;
				}
				if(setMethod == null){
					logger.error("No Set Method for:" + field.getName() + " in " + rawEntity.getClass().getSimpleName());
					continue;
				}
				try {
					setMethod.invoke(rawEntity, getMethod.invoke(seUIComModel));
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					logger.error("invoke error in :" + field.getName() + " in " + e.getMessage());
				}
			}
		}
	}

	public static <T> T castCopyModel(Object sourceModel, Class<T> targetClass) throws IllegalAccessException,
			InstantiationException {
		if(targetClass.equals(sourceModel.getClass())){
			return (T) sourceModel;
		}
		T targetModel = targetClass.newInstance();
		// Pay attention, in case copy service entity node, nodeName & serviceEntityName should not be copedi
		convComModelReflect(sourceModel, targetModel,
				ServiceCollectionsHelper.asList(IServiceEntityNodeFieldConstant.SERVICEENTITYNAME,
				IServiceEntityNodeFieldConstant.NODENAME));
		return targetModel;
	}
	
	/**
	 * Utility method convert SENode to SE UI model in reflective way
	 * 
	 * @param sourceModel
	 * @param targetModel
	 */
	public static void convComModelReflect(Object sourceModel, Object targetModel, List<String> skipFieldList){
		if(sourceModel == null || targetModel == null){
			return;
		}
		List<Field> fieldList = ServiceEntityFieldsHelper
				.getServiceSelfDefinedFieldsList(targetModel.getClass(),
						Object.class, true);
		Logger logger = LoggerFactory.getLogger(ServiceReflectiveHelper.class);
		if(!ServiceCollectionsHelper.checkNullList(fieldList)){
			for(Field field: fieldList){
				Object existSkipName = ServiceCollectionsHelper.filterOnline(field.getName(), fieldName->{
					return fieldName;
				}, skipFieldList);
				if(existSkipName != null){
					continue;
				}
				Method getMethod = genGetMethod(field.getName(), sourceModel.getClass());				
				Method setMethod = genSetMethod(field, targetModel.getClass());
				if(getMethod == null){
					logger.error("No Get Method for:" + field.getName() + " in " + sourceModel.getClass().getSimpleName());
					continue;
				}
				if(setMethod == null){
					logger.error("No Set Method for:" + field.getName() + " in " + targetModel.getClass().getSimpleName());
					continue;
				}
				try {
					setMethod.invoke(targetModel, getMethod.invoke(sourceModel));
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					logger.error("invoke error in :" + field.getName() + " in " + e.getMessage());
				}
			}
		}
	}

	/**
	 * Get Service Entity Node Inheritance class list until the basic
	 * ServiceEntityNode class
	 *
	 * @param seType
	 * @return
	 */
	public static List<Class<?>> getSEInheritanceList(Class<?> seType) {
		List<Class<?>> clsList = Collections
				.synchronizedList(new ArrayList<>());
		if (seType == null) {
			return null;
		}
		if (seType.getSimpleName().equals(
				ServiceEntityNode.class.getSimpleName())
				|| seType.getSimpleName().equals(
				SEUIComModel.class.getSimpleName())
				|| seType.getSimpleName().equals(Object.class.getSimpleName())) {
			clsList.add(seType);
		} else {
			List<Class<?>> tmpClsList = getSEInheritanceList(seType
					.getSuperclass());
			if (!ServiceCollectionsHelper.checkNullList(tmpClsList)) {
				clsList.addAll(tmpClsList);
			}
			clsList.add(seType);
		}
		return clsList;
	}

	/**
	 * Check if current Service Entity node extends special basic class
	 * class
	 *
	 * @param seType
	 * @return
	 */
	public static boolean checkSEExtends(Class<?> seType, Class<?> referenceSeType){
		List<Class<?>> superClassList = getSEInheritanceList(seType);
		if(!ServiceCollectionsHelper.checkNullList(superClassList)){
			return false;
		}
		for(Class<?> tmpClass: superClassList){
			if(tmpClass.getName().equals(referenceSeType.getName())){
				return true;
			}
		}
		return false;
	}
	
}
