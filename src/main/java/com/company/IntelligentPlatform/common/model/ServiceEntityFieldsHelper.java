package com.company.IntelligentPlatform.common.model;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.company.IntelligentPlatform.common.service.ServiceReflectiveHelper;
import com.company.IntelligentPlatform.common.service.ServiceBasicFieldValueUnion;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Helper to process Fields information of Service Entity
 * 
 * @author ZhangHang
 * @date Nov 6, 2012
 * 
 */
public class ServiceEntityFieldsHelper {

	public static final int PROPERTY_SETTER = 1;

	public static final int PROPERTY_GETTER = 2;

	/**
	 * Get Service Entity Node field list ranged from basic ServiceEntityNode to
	 * final implementation class
	 * 
	 * @param seClass
	 * @return
	 */

	public static List<Field> getFieldsList(Class<?> seClass) {
		// Synchronize the Array List
		List<Class<?>> clsList = ServiceReflectiveHelper.getSEInheritanceList(seClass);
		List<Field> fieldList = Collections
				.synchronizedList(new ArrayList<>());
		for (Class<?> clsType : clsList) {
			Field[] fieldArray = clsType.getDeclaredFields();
			for (Field field : fieldArray) {
				// Filter out the constant fields
				if (Modifier.isStatic(field.getModifiers()))
					continue;
				// Check if the field access type is [Protected]
				if (Modifier.isProtected(field.getModifiers())) {
					fieldList.add(field);
				}
			}
		}
		return fieldList;
	}

	/**
	 * Get all the instance list by parsing fields from repo Class
	 * @param repoInstance
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> getDefRepoInstList(Class<T> InstanceType, Object repoInstance){
		List<Field> allFieldList = ServiceEntityFieldsHelper.getFieldsList(repoInstance.getClass());
		if(ServiceCollectionsHelper.checkNullList(allFieldList)){
			return null;
		}
		List<Field> resultFieldList = new ArrayList<>();
		for(Field field:allFieldList){
			field.setAccessible(true);
			if(!InstanceType.getSimpleName().equals(field.getType().getSimpleName())){
				continue;
			}
			resultFieldList.add(field);
		}
		if(ServiceCollectionsHelper.checkNullList(resultFieldList)){
			return null;
		}
		List<T> resultList = new ArrayList<>();
		for(Field field:resultFieldList){
			field.setAccessible(true);
			try {
				T instance = (T) field.get(repoInstance);
				if(instance != null){
					resultList.add(instance);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// log the issue and configure
			}
		}
		return resultList;

	}

	public static List<Method> getMethodsList(Class<?> seClass) {
		// Synchronize the Array List
		List<Class<?>> clsList = ServiceReflectiveHelper.getSEInheritanceList(seClass);
		List<Method> methodList = Collections
				.synchronizedList(new ArrayList<Method>());
		for (Class<?> clsType : clsList) {
			Method[] methodArray = clsType.getDeclaredMethods();
            Collections.addAll(methodList, methodArray);
		}
		return methodList;
	}

	public static List<Method> getSelfDefinedMethodsList(Class<?> seClass) {
		// Synchronize the Array List
		List<Method> methodList = Collections
				.synchronizedList(new ArrayList<Method>());
		Method[] methodArray = seClass.getDeclaredMethods();
        Collections.addAll(methodList, methodArray);
		return methodList;
	}

	public static List<Field> getStaticFieldsList(Class<?> seClass) {
		// Synchronize the Array List
		List<Class<?>> clsList = ServiceReflectiveHelper.getSEInheritanceList(seClass);
		List<Field> fieldList = Collections
				.synchronizedList(new ArrayList<Field>());
		for (Class<?> clsType : clsList) {
			Field[] fieldArray = clsType.getDeclaredFields();
			for (Field field : fieldArray) {
				// Filter out the constant fields
				if (Modifier.isStatic(field.getModifiers())) {
					fieldList.add(field);
				}
			}
		}
		return fieldList;
	}
	
	public static Object getStaticFieldValue(Class<?> classInstance, String fieldName)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {		
		Field field = classInstance.getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(classInstance);
	}

	/**
	 * Get Service entity node self defined field list (including the
	 * self-defined field from super class) but exclude the very basic feild
	 * list according to different base class. Fields in base class is NOT
	 * included in the result
	 *
	 * @param seClass
	 * @param baseClass
	 * @return
	 */
	public static List<Field> getServiceSelfDefinedFieldsList(Class<?> seClass,
			Class<?> baseClass, boolean excludeBaseFlag) {
		// Synchronize the Array List
		String baseClassName = baseClass.getSimpleName();
		List<Class<?>> clsList = getServiceInheritanceList(seClass,
				baseClassName, excludeBaseFlag);
		List<Field> fieldList = Collections
				.synchronizedList(new ArrayList<Field>());
		for (Class<?> clsType : clsList) {
			// should skip the first class same with base class name
			Field[] fieldArray = clsType.getDeclaredFields();
			for (Field field : fieldArray) {
				// Filter out the constant fields
				if (Modifier.isStatic(field.getModifiers()))
					continue;
				// Check if the field access type is [Protected]
				if (Modifier.isProtected(field.getModifiers())) {
					fieldList.add(field);
				}
			}
		}
		return fieldList;
	}
	
	/**
	 * Get Service entity node self defined field list (including the
	 * self-defined field from super class) but exclude the very basic feild
	 * list according to different base class. Fields in base class is NOT
	 * included in the result
	 *
	 * @param seClass
	 * @param baseClass
	 * @return
	 */
	public static List<Field> getServiceSelfDefinedFieldsList(Class<?> seClass,
			Class<?> baseClass) {
		// Synchronize the Array List
		return getServiceSelfDefinedFieldsList(seClass, baseClass, false);
	}

	/**
	 * Get Service entity node self defined field list (including the
	 * self-defined field from super class) but exclude the very basic feild
	 * list according to different node specify type
	 * 
	 * @param seClass
	 * @param nodeSpecifyType
	 * @return
	 */
	public static List<Field> getServiceSelfDefinedFieldsList(Class<?> seClass,
			int nodeSpecifyType) {
		// Synchronize the Array List
		String baseClassName = ServiceEntityStringHelper.EMPTYSTRING;
		List<String> excludeClsList = getExcludeClassNameList(nodeSpecifyType);
		List<Class<?>> clsList = getServiceInheritanceList(seClass,
				baseClassName);
		List<Field> fieldList = Collections
				.synchronizedList(new ArrayList<Field>());
		boolean skipFlag = false;
		for (Class<?> clsType : clsList) {
			// should skip the first class same with base class name
			skipFlag = false;
			if (excludeClsList != null && excludeClsList.size() > 0) {
				for (String exClsName : excludeClsList) {
                    if (clsType.getSimpleName().equals(exClsName)) {
                        skipFlag = true;
                        break;
                    }
				}
			}
			if (!skipFlag) {
				Field[] fieldArray = clsType.getDeclaredFields();
				for (Field field : fieldArray) {
					// Filter out the constant fields
					if (Modifier.isStatic(field.getModifiers()))
						continue;
					// Check if the field access type is [Protected]
					if (Modifier.isProtected(field.getModifiers())) {
						fieldList.add(field);
					}
				}
			}
		}
		return fieldList;
	}

	protected static List<String> getExcludeClassNameList(int nodeSpecifyType) {
		List<String> resultList = new ArrayList<String>();
		if (nodeSpecifyType == ServiceEntityNode.NODESPECIFYTYPE_STANDARD) {
			resultList.add(ServiceEntityNode.class.getSimpleName());
		}
		if (nodeSpecifyType == ServiceEntityNode.NODESPECIFYTYPE_REFERENCE
				|| nodeSpecifyType == ServiceEntityNode.NODESPECIFYTYPE_PROJECTION) {
			resultList.add(ServiceEntityNode.class.getSimpleName());
			resultList.add(ReferenceNode.class.getSimpleName());
		}
		if (nodeSpecifyType == ServiceEntityNode.NODESPECIFYTYPE_DOCUMENT) {
			resultList.add(ServiceEntityNode.class.getSimpleName());
			resultList.add(DocumentContent.class.getSimpleName());
		}
		return resultList;
	}

	/**
	 * Get the standard field list from reference node
	 * 
	 * @return
	 */
	public static List<Field> getReferenceStandardFieldList() {
		return getSelfDefinedFieldsList(ReferenceNode.class);
	}

	/**
	 * Generate the reflective field list by field name list
	 * 
	 * @param seClass
	 * @param fieldNameList
	 * @return
	 */
	public static List<Field> generateFieldsList(Class<?> seClass,
			List<String> fieldNameList) {
		// Synchronize the Array List
		List<Field> fieldList = Collections
				.synchronizedList(new ArrayList<Field>());
		if (ServiceCollectionsHelper.checkNullList(fieldNameList)) {
			return null;
		}
		List<Field> allFieldList = getFieldsList(seClass);
		if (ServiceCollectionsHelper.checkNullList(allFieldList)) {
			return null;
		}
		for (String fieldName : fieldNameList) {
			try {
				Field field = filterFieldByNameOnline(allFieldList, fieldName);
				if (field != null) {
					fieldList.add(field);
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fieldList;
	}

	public static Field filterFieldByNameOnline(List<Field> allFieldList,
			String fieldName) {
		if (ServiceCollectionsHelper.checkNullList(allFieldList)) {
			return null;
		}
		for (Field field : allFieldList) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		return null;
	}

	public static Object getObjectFieldValue(Object object, String fieldName)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		Class<?> classInstance = object.getClass();
		List<Field> fieldList = ServiceEntityFieldsHelper.getFieldsList(classInstance);
		Field field = filterFieldByNameOnline(fieldList, fieldName);
		if(field == null){
			return null;
		}
		field.setAccessible(true);
		return field.get(object);
	}

	public static boolean checkListTypeByValue(Object rawValue){
		if(rawValue == null){
			return false;
		}
		if(rawValue.getClass().isAssignableFrom(java.util.List.class)){
			return true;
		}
		if(rawValue.getClass().getSimpleName().equals(ArrayList.class.getSimpleName())){
			return true;
		}
        return rawValue.getClass().getSimpleName().equals(List.class.getSimpleName());
    }


	public static ServiceBasicFieldValueUnion getObjectFieldValueUnion(Object object, String fieldName)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		Class<?> classInstance = object.getClass();
		List<Field> fieldList = ServiceEntityFieldsHelper.getFieldsList(classInstance);
		Field field = filterFieldByNameOnline(fieldList, fieldName);
		if(field == null){
			return null;
		}
		field.setAccessible(true);
		return new ServiceBasicFieldValueUnion(field, field.get(object));
	}


	public static Object getClsConstantValue(Class<?> constantClass, String fieldName)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		Field field = constantClass.getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(constantClass);
	}

	public static List<Field> getSelfDefinedFieldsList(Class<?> seClass) {
		// Synchronize the Array List
		List<Field> fieldList = Collections
				.synchronizedList(new ArrayList<Field>());
		Field[] fieldArray = seClass.getDeclaredFields();
		for (Field field : fieldArray) {
			// Filter out the constant fields
			if (Modifier.isStatic(field.getModifiers()))
				continue;
			// Check if the field access type is [Protected]
			if (Modifier.isProtected(field.getModifiers())) {
				fieldList.add(field);
			}
		}
		return fieldList;
	}
	
	public static boolean checkFieldContains(String fieldName, Class<?> seClass){
		List<Field> fieldList = getFieldsList(seClass);
		if (fieldList == null || fieldList.size() == 0) {
			return false;
		}
		for (Field field : fieldList) {
			if (fieldName.equals(field.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check weather this field is the self-defined field in model class
	 * 
	 * @param fieldName
	 * @param seClass
	 * @return
	 */
	public static boolean checkSelfDefinedField(String fieldName,
			Class<?> seClass) {
		List<Field> fieldList = getSelfDefinedFieldsList(seClass);
		if (fieldList == null || fieldList.size() == 0) {
			return false;
		}
		for (Field field : fieldList) {
			if (fieldName.equals(field.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if the current node is reference node or not
	 * 
	 * @param seClass
	 * @return
	 */
	public static boolean isReferenceNode(Class<?> seClass) {
		List<Class<?>> clsList = ServiceReflectiveHelper.getSEInheritanceList(seClass);
		for (Class<?> cls : clsList) {
			if (cls.getSimpleName().equals(ReferenceNode.class.getSimpleName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if the current node is document node or not
	 * 
	 * @param seClass
	 * @return
	 */
	public static boolean isDocumentNode(Class<?> seClass) {
		List<Class<?>> clsList = ServiceReflectiveHelper.getSEInheritanceList(seClass);
		for (Class<?> cls : clsList) {
			if (cls.getSimpleName().equals(
					DocumentContent.class.getSimpleName())) {
				return true;
			}
		}
		return false;
	}



	/**
	 * Get Service Entity Node Inheritance class list until the specified basic
	 * class name
	 * 
	 * ServiceEntityNode class
	 * 
	 * @param seType
	 * @param baseClassName
	 * @return
	 */
	public static List<Class<?>> getServiceInheritanceList(Class<?> seType,
			String baseClassName) {
		return getServiceInheritanceList(seType, baseClassName, false);
	}

	/**
	 * Get Service Entity Node Inheritance class list until the specified basic
	 * class name
	 *
	 * ServiceEntityNode class
	 *
	 * @param seType
	 * @param baseClassName
	 * @return
	 */
	public static List<Class<?>> getServiceInheritanceList(Class<?> seType,
			String baseClassName, boolean excludeBaseFlag) {
		List<Class<?>> clsList = Collections
				.synchronizedList(new ArrayList<Class<?>>());
		if (seType.getSimpleName().equals(baseClassName)
				|| seType.getSimpleName().equals(Object.class.getSimpleName())) {
			if (!excludeBaseFlag) {
				clsList.add(seType);
			}
		} else {
			List<Class<?>> tmpResultList = getServiceInheritanceList(
					seType.getSuperclass(), baseClassName, excludeBaseFlag);
			if (!ServiceCollectionsHelper.checkNullList(tmpResultList)) {
				clsList.addAll(tmpResultList);
			}
			clsList.add(seType);
		}
		return clsList;
	}

	/**
	 * Check wethear current class extends the defined super class
	 * 
	 * @param seType
	 * @param baseClassName
	 * @return
	 */
	public static boolean checkSuperClassExtends(Class<?> seType,
			String baseClassName) {
		if (seType == null) {
			return false;
		}
		if (baseClassName.equals(seType.getSimpleName())) {
			return true;
		}
		if (Object.class.getSimpleName().equals(seType.getSimpleName())) {
			return false;
		}
		return checkSuperClassExtends(seType.getSuperclass(), baseClassName);
	}

	/**
	 * New Service Entity Node instance in reflective way by node type
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static ServiceEntityNode newInstanceEntityNode(Class<?> seNodeClass,
			String client) throws InstantiationException,
			IllegalAccessException {
		ServiceEntityNode serviceEntityNode = (ServiceEntityNode) seNodeClass
				.newInstance();
		String uuid = serviceEntityNode.getUuid();
		if (ServiceEntityStringHelper.checkNullString(uuid)) {
			UUID uuidObject = UUID.randomUUID();
			uuid = uuidObject.toString();
			serviceEntityNode.setUuid(uuid);
		}
		if (ServiceEntityStringHelper.checkNullString(serviceEntityNode
				.getParentNodeUUID())) {
			serviceEntityNode.setParentNodeUUID(serviceEntityNode.getUuid());
		}
		if (ServiceEntityStringHelper.checkNullString(serviceEntityNode
				.getRootNodeUUID())) {
			serviceEntityNode.setRootNodeUUID(serviceEntityNode.getUuid());
		}
		if (ServiceEntityStringHelper.checkNullString(serviceEntityNode
				.getClient())) {
			serviceEntityNode.setClient(client);
		}
		return serviceEntityNode;
	}

	/**
	 * New Service Entity Node instance in reflective way by node type, this
	 * instance is used for Update by other instance, uuid, parentNodeUUID is
	 * set to be null.
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static ServiceEntityNode newInstanceEntityNodeForUpdate(
			Class<?> seNodeClass, String client) throws InstantiationException,
			IllegalAccessException {
		ServiceEntityNode serviceEntityNode = (ServiceEntityNode) seNodeClass
				.newInstance();

		return serviceEntityNode;
	}

	/**
	 * Return the Parameterized type from field
	 * 
	 * @param field
	 * @return
	 */
	public static Class<?> getListSubType(Field field) {
		if (!field.getType().isAssignableFrom(List.class)) {
			return null;
		}
		ParameterizedType paraListType = (ParameterizedType) field
				.getGenericType();
		Class<?> subClass = (Class<?>) paraListType.getActualTypeArguments()[0];
		return subClass;
	}


	/**
	 * Get Service field by field name
	 * 
	 * @param seNode
	 * @param fieldName
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public static Field getServiceField(ServiceEntityNode seNode,
			String fieldName) throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException {
		List<Field> fieldList = getFieldsList(seNode.getClass());
		for (Field field : fieldList) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		throw new NoSuchFieldException();
	}

	/**
	 * Get Service field by field name
	 * 
	 * @param seClass
	 * @param fieldName
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public static Field getServiceField(Class<?> seClass, String fieldName)
			throws IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException {
		List<Field> fieldList = getFieldsList(seClass);
		for (Field field : fieldList) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		return null;
	}
	

	/**
	 * Get Service field by field name
	 * 
	 * @param seClass
	 * @param fieldName
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public static Field getSelfServiceField(Class<?> seClass, String fieldName)
			throws IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException {
		List<Field> fieldList = getSelfDefinedFieldsList(seClass);
		for (Field field : fieldList) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		throw new NoSuchFieldException();
	}

	/**
	 * Get Service field by field name
	 * 
	 * @param seClass
	 * @param fieldName
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public static Field getStaticField(Class<?> seClass, String fieldName)
			throws IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException {
		List<Field> fieldList = getStaticFieldsList(seClass);
		for (Field field : fieldList) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		throw new NoSuchFieldException();
	}

	/**
	 * Get Method by method name
	 * 
	 * @param seClass
	 * @param methodName
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public static Method getMethod(Class<?> seClass, String methodName)
			throws IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException {
		List<Method> methodList = getMethodsList(seClass);
		for (Method method : methodList) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}
		return null;
	}

	/**
	 * Get Method by method name
	 * 
	 * @param seClass
	 * @param methodName
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public static Method getSelfDefinedMethod(Class<?> seClass,
			String methodName) throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException {
		List<Method> methodList = getSelfDefinedMethodsList(seClass);
		for (Method method : methodList) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}
		return null;
	}

	/**
	 * Get relective field value from SE Node by field name & SE instance
	 * 
	 * @param seNode
	 * @param fieldName
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public static Object getServiceFieldValue(ServiceEntityNode seNode,
			String fieldName) throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException {
		List<Field> fieldList = getFieldsList(seNode.getClass());
		for (Field field : fieldList) {
			if (field.getName().equals(fieldName)) {
				field.setAccessible(true);
				return field.get(seNode);
			}
		}
		throw new NoSuchFieldException();
	}
	
	
	/**
	 * Get reflective field value from SE Node by field name & SE instance with pre-check null value function
	 * 
	 * @param homeSENode
	 * @param fieldName
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public static Object getServiceFieldValueWrapper(ServiceEntityNode homeSENode, String fieldName) {
		if (!ServiceEntityFieldsHelper.checkFieldContains(fieldName,
				homeSENode.getClass())) {
			return null;
		}
		Object rawValue;
		try {
			rawValue = ServiceEntityFieldsHelper.getServiceFieldValue(
					homeSENode, fieldName);
		} catch (IllegalArgumentException | IllegalAccessException
				| NoSuchFieldException e) {
			return null;
		}
		return rawValue;
	}

	public static int getIntServiceFieldValueWrapper(ServiceEntityNode homeSENode, String fieldName) {
		Object rawValue = getServiceFieldValueWrapper(homeSENode,
				fieldName);
		if (rawValue == null) {
			return 0;
		}
		int intValue = Integer.parseInt(rawValue.toString());
		return intValue;
	}
	
	public static double getDoubleServiceFieldValueWrapper(ServiceEntityNode homeSENode, String fieldName) {
		Object rawValue = getServiceFieldValueWrapper(homeSENode,
				fieldName);
		if (rawValue == null) {
			return 0;
		}
		double doubleValue = Double.parseDouble(rawValue.toString());
		return doubleValue;
	}
	
	public static String getStrServiceFieldValueWrapper(ServiceEntityNode homeSENode, String fieldName) {
		Object rawValue = getServiceFieldValueWrapper(homeSENode,
				fieldName);
		if(rawValue == null){
			return null;
		}
		return rawValue.toString();
	}


	/**
	 * Get relective field value from Object Node by field name & SE instance
	 * 
	 * @param object
	 * @param fieldName
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public static Object getReflectiveObjectValue(Object object,
			String fieldName) throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException {
		List<Field> fieldList = getFieldsList(object.getClass());
		return getReflectiveObjectValue(object, fieldList, fieldName);
	}

	/**
	 * Get relective field value from Object Node by field name & SE instance
	 * 
	 * @param object
	 * @param fieldName
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public static Object getReflectiveObjectValue(Object object,
			List<Field> fieldList, String fieldName)
			throws IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException {
		if (fieldList == null || fieldList.size() == 0) {
			throw new NoSuchFieldException();
		}
		for (Field field : fieldList) {
			if (field.getName().equals(fieldName)) {
				field.setAccessible(true);
				return field.get(object);
			}
		}
		throw new NoSuchFieldException();
	}

	/**
	 * Get SE model package name by service entity instance
	 * 
	 * @param seNode
	 *            :service entity node instance
	 * @return
	 */
	public static String getPackage(ServiceEntityNode seNode) {
		return seNode.getClass().getPackage().getName();
	}

	/**
	 * Get SE model package name by Root Service Entity Node Class
	 * 
	 * @param seCls
	 *            :service entity node instance
	 * @return
	 */
	public static String getPackage(Class<?> seCls) {
		return seCls.getPackage().getName();
	}

	/**
	 * Get Common package by service entity instance, means package name without
	 * "Model"
	 * 
	 * @param seNode
	 * @return
	 */
	public static String getCommonPackage(ServiceEntityNode seNode) {
		String modelPackage = getPackage(seNode);
		String subPath = "Model";
		return modelPackage.substring(0, modelPackage.lastIndexOf(subPath) - 1);
	}

	/**
	 * Get Common package by service entity instance, means package name without
	 * "Model"
	 * 
	 * @param seCls
	 * @return
	 */
	public static String getCommonPackage(Class<?> seCls) {
		String modelPackage = getPackage(seCls);
		String subPath = "Model";
		return modelPackage.substring(0, modelPackage.lastIndexOf(subPath) - 1);
	}

	/**
	 * Get the setter and getter method by field and model class information
	 * 
	 * @param modelType
	 * @param field
	 * @param propertyDirection
	 * @return
	 */
	public static String getPropertyMethod(Class<?> modelType, Field field,
			int propertyDirection, boolean selfDefinedFlag) {
		try {
			String prefix = "get";
			if (propertyDirection == PROPERTY_SETTER) {
				prefix = "set";
			}
			String defMethodName = prefix
					+ ServiceEntityStringHelper.headerToUpperCase(field
							.getName());
			Method method = ServiceEntityFieldsHelper.getSelfDefinedMethod(
					modelType, defMethodName);
			if (!selfDefinedFlag) {
				method = ServiceEntityFieldsHelper.getMethod(modelType,
						defMethodName);
			}
			if (method == null && propertyDirection == PROPERTY_GETTER) {
				defMethodName = "is"
						+ ServiceEntityStringHelper.headerToUpperCase(field
								.getName());
				method = ServiceEntityFieldsHelper.getSelfDefinedMethod(
						modelType, defMethodName);
				if (!selfDefinedFlag) {
					method = ServiceEntityFieldsHelper.getMethod(modelType,
							defMethodName);
				}
			}
			if (method == null) {
				return null;
			}
			return defMethodName;
		} catch (IllegalAccessException e) {
			return null;
		} catch (IllegalArgumentException e) {
			return null;
		} catch (NoSuchFieldException e) {
			return null;
		}
	}

	public static String getClassSimpleNameByFullName(String fullName) {
		String simpleName = ServiceEntityStringHelper.EMPTYSTRING;
		if (fullName == null) {
			return simpleName;
		}
		String[] stringArray = fullName.split("\\.");
		if (stringArray == null || stringArray.length == 0) {
			return simpleName;
		}
		simpleName = stringArray[stringArray.length - 1];
		return simpleName;
	}

}
