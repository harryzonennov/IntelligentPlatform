package com.company.IntelligentPlatform.common.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class SEUIModelFieldsHelper {

	/**
	 * Get all value fields from SE UI model including the fields inherited from
	 * super class
	 * 
	 * @param seUIClass
	 * @return
	 */
	public static List<Field> getFieldsList(Class<?> seUIClass) {
		// Synchronize the Array List
		List<Class<?>> clsList = getSEInheritanceList(seUIClass);
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

	public static List<Field> getSelfDefinedFieldsList(Class<?> seUIClass) {
		// Synchronize the Array List
		List<Field> fieldList = Collections
				.synchronizedList(new ArrayList<Field>());
		Field[] fieldArray = seUIClass.getDeclaredFields();
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

	/**
	 * Get Service field by field name
	 * 
	 * @param seUIClass
	 * @param fieldName
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public static Field getServiceField(Class<?> seUIClass, String fieldName)
			throws IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException {
		List<Field> fieldList = getFieldsList(seUIClass);
		for (Field field : fieldList) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		throw new NoSuchFieldException();
	}

	/**
	 * Get SE UI Model Inheritance class list until the basic <code></code>
	 * class
	 * 
	 * @param seType
	 * @return
	 */
	public static List<Class<?>> getSEInheritanceList(Class<?> seType) {
		List<Class<?>> clsList = Collections
				.synchronizedList(new ArrayList<>());
		if (seType.getSimpleName().equals(SEUIComModel.class.getSimpleName())) {
			clsList.add(seType);
		} else {
			clsList.addAll(getSEInheritanceList(seType.getSuperclass()));
			clsList.add(seType);
		}
		return clsList;
	}

}
