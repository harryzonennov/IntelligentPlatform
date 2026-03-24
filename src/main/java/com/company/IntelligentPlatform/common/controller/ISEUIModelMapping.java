package com.company.IntelligentPlatform.common.controller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Retention(RetentionPolicy.RUNTIME)
public @interface ISEUIModelMapping {
	/**
	 * Back end SE Node field name
	 * 
	 * @return
	 */
	String fieldName() default ServiceEntityStringHelper.EMPTYSTRING;

	/**
	 * Back end SE Node name
	 * 
	 * @return
	 */
	String nodeName() default ServiceEntityStringHelper.EMPTYSTRING;

	/**
	 * Back end SE name
	 * 
	 * @return
	 */
	String seName() default ServiceEntityStringHelper.EMPTYSTRING;
	
	
	/**
	 * UI Instance name
	 * 
	 * @return
	 */
	String nodeInstID() default ServiceEntityStringHelper.EMPTYSTRING;

	/**
	 * Indicate whether this field should use text area to display
	 * 
	 * @return
	 */
	boolean textAreaFlag() default false;

	/**
	 * Indicate whether this field should be hidden form on Editor or list UI
	 * 
	 * @return
	 */
	boolean hiddenFlag() default false;

	/**
	 * Indicate whether this field is read only type on Editor UI: default value
	 * is false
	 * 
	 * @return
	 */
	boolean readOnlyFlag() default false;

	/**
	 * Indicate whether this field should be shown on list UI: default value
	 * is:true
	 * 
	 * @return
	 */
	boolean showOnList() default true;

	/**
	 * Indicate whether this field should be shown on editor UI: default value
	 * is true
	 * 
	 * @return
	 */
	boolean showOnEditor() default true;
	
	/**
	 * Indicate whether this field should be shown on editor UI: default value
	 * is true
	 * 
	 * @return
	 */
	boolean searchFlag() default true;

	/**
	 * Only make sence for date type field, the name of UI field to store its
	 * String in order to be shown on List UI
	 * 
	 * @return
	 */
	String dateStringField() default ServiceEntityStringHelper.EMPTYSTRING;

	/**
	 * The TAB ID to control the tab view in editor view
	 * 
	 * @return
	 */
	String tabId() default ServiceEntityStringHelper.EMPTYSTRING;

	/**
	 * The Section ID to be shown section in editor view
	 * 
	 * @return
	 */
	String secId() default ServiceEntityStringHelper.EMPTYSTRING;

	/**
	 * The size of the field shown in editor view
	 * 
	 * @return
	 */
	int size() default 0;

	/**
	 * The width of the field shown in list view, unit is 'px'
	 * 
	 * @return
	 */
	int width() default 0;
	
	/**
	 * Indicate wethear this field could be exported for service doc configure
	 * @return
	 */
	boolean exportParaFlag() default false;
	
	/**
	 * Indicate wethear this field could be exported for service doc configure writting back
	 * 
	 * @return
	 */
	boolean importParaFlag() default false;
}
